#!/bin/bash

# PRODUCTION/VPC UAA DOMAIN:
PREDIX_UAA_DOMAIN="predix-uaa.run.aws-usw02-pr.ice.predix.io"

# --------------------------------------------------------------------------------

checkstatus(){
  if [ $? -ne 0 ]; then
    echo "Previous command did not succeed. Exiting."
    exit -1
  fi
}

# --------------------------------------------------------------------------------

selectUAA () {
  echo ""
  echo "Looking up available UAA instances... "
  local cmd="cf s | grep uaa | cut -d ' ' -f1"
  local UAA_OPTS=$(eval $cmd)
  if [ -z "${UAA_OPTS}" ]; then
    echo ""
    echo "No UAA instances were found.  Have you logged in to cloud foundry?"
    exit -1;
  fi

  echo ""
  echo "Choose a number associated with UAA instance you selected when you created your Analytics UI instance:"
  echo ""
  select UAA in $UAA_OPTS;
  do
    if [ -z "${UAA}" ]; then
      echo "You chose an invalid selection ($REPLY). Please choose one of the numbers from the list"
      continue
    else
      UAA_INSTANCE_ID=`cf service ${UAA} --guid`
      echo "You picked $UAA ($REPLY), this is its GUID: ${UAA_INSTANCE_ID}"
      break
    fi
  done
}

# --------------------------------------------------------------------------------

selectCatalog () {
  echo ""
  echo "Looking up available Analytics Catalog Service instances... "
  local cmd="cf s | grep predix-analytics-catalog | cut -d ' ' -f1"
  local CATALOG_OPTS=$(eval $cmd)
  if [ -z "${CATALOG_OPTS}" ]; then
    echo ""
    echo "No Analytics Catalog instances were found.  Are you in the correct CF org and space?"
    exit -1;
  fi

  echo ""
  echo "Choose a number associated with Analytics Catalog Service instance you selected when you created your Analytics UI instance:"
  echo ""
  select CATALOG in $CATALOG_OPTS;
  do
    if [ -z "${CATALOG}" ]; then
      echo "You chose an invalid selection ($REPLY). Please choose one of the numbers from the list"
      continue
    else
      CATALOG_INSTANCE_ID=`cf service ${CATALOG} --guid`
      echo "You picked $CATALOG ($REPLY), this is its GUID: ${CATALOG_INSTANCE_ID}"
      break
    fi
  done
}

# --------------------------------------------------------------------------------

selectRuntime () {
  echo ""
  echo "Looking up available Analytics Runtime Service instances... "
  local cmd="cf s | grep predix-analytics-runtime | cut -d ' ' -f1"
  local RUNTIME_OPTS=$(eval $cmd)
  if [ -z "${RUNTIME_OPTS}" ]; then
    echo ""
    echo "No Analytics Runtime instances were found.  Are you in the correct CF org and space?"
    exit -1;
  fi

  echo ""
  echo "Choose a number associated with Analytics Runtime Service instance you selected when you created your Analytics UI instance:"
  echo ""
  select RUNTIME in $RUNTIME_OPTS;
  do
    if [ -z "${RUNTIME}" ]; then
      echo "You chose an invalid selection ($REPLY). Please choose one of the numbers from the list"
      continue
    else
      RUNTIME_INSTANCE_ID=`cf service ${RUNTIME} --guid`
      echo "You picked $RUNTIME ($REPLY), this is its GUID: ${RUNTIME_INSTANCE_ID}"
      break
    fi
  done
}

# --------------------------------------------------------------------------------

logInToUAA () {
  askForClientSecret
  while [ "${CLIENT_SECRET_1}" != "${CLIENT_SECRET_2}" ]; do
    echo ""
    echo "Client secrets did not match..."
    askForClientSecret
  done
  ${UAAC} target https://${UAA_INSTANCE_ID}.${PREDIX_UAA_DOMAIN}
  checkstatus
  ${UAAC} token client get admin -s ${CLIENT_SECRET_1}
  checkstatus
}

# --------------------------------------------------------------------------------

configureAdminClientInUAA () {
  local scopeValues="acs.attributes.read \
            acs.attributes.write \
            acs.policies.read \
            acs.policies.write \
            analytics.zones.*.user \
            client.secret \
            clients.admin \
            clients.read \
            clients.write \
            idps.read \
            idps.write \
            openid \
            scim.read \
            scim.write \
            uaa.none \
            uaa.resource \
            zones.${UAA_INSTANCE_ID}.admin"
  local grantTypes="authorization_code \
            client_credentials \
            password \
            refresh_token"

  local clientUpdateResp=`${UAAC} client update admin \
    --scope "${scopeValues}" \
    --authorized_grant_types "${grantTypes}" `

  local clientUpdateStatus=$?
  echo $clientUpdateResp | grep -q -e 'Nothing to update'
  local foundNothing=$?
  if [ $foundNothing -ne 0 ]; then
    echo $clientUpdateResp
    exit -1;
  fi
  checkstatus
}

# --------------------------------------------------------------------------------

enterPassword() {
  local pwNo=$1
  if [ $pwNo -eq 1 ]; then
    local prompt="Please enter a secure password:"
  else
    local prompt="Please re-enter the password:"
  fi
  echo ""
  echo $prompt
  read -s PW_${pwNo}
}

askForPassword() {
  enterPassword 1
  enterPassword 2
}

# --------------------------------------------------------------------------------

enterEmail() {
  local emailNo=$1
  if [ $emailNo -eq 1 ]; then
    local prompt="Please enter the user's e-mail addresss:"
  else
    local prompt="Please re-enter the e-mail address:"
  fi
  echo ""
  echo $prompt
  read EMAIL_${emailNo}
}

askForEmail() {
  enterEmail 1
  enterEmail 2
}

# --------------------------------------------------------------------------------

enterSecret() {
  local secretNo=$1
  if [ $secretNo -eq 1 ]; then
    local prompt="Please enter the UAA Client Secret you used to create your Analytics UI instance:"
  else
    local prompt="Please re-enter the client secret:"
  fi
  echo ""
  echo $prompt
  read -s CLIENT_SECRET_${secretNo}
}

askForClientSecret() {
  enterSecret 1
  enterSecret 2
}

# --------------------------------------------------------------------------------

askForLoginName() {
  echo ""
  echo "Please enter a new account login name (no spaces):"
  read LOGIN_NAME
}

askForFirstName() {
  echo ""
  echo "Please enter the user's given (first) name:"
  read FIRST_NAME
}

askForSurName() {
  echo ""
  echo "Please enter the user's family name (surname):"
  read SUR_NAME
}

getUser() {
  askForLoginName
  askForPassword
  while [ "${PW_1}" != "${PW_2}" ]; do
    echo ""
    echo "Passwords did not match..."
    askForPassword
  done
  askForFirstName
  askForSurName
  askForEmail
  while [ "${EMAIL_1}" != "${EMAIL_2}" ]; do
    echo ""
    echo "E-mail addresses did not match..."
    askForEmail
  done
}

# --------------------------------------------------------------------------------

addCatalogGroup() {
  local catalogGroupResp=`${UAAC} group add analytics.zones.${CATALOG_INSTANCE_ID}.user`
  local catalogGroupStatus=$?
  echo $catalogGroupResp | grep -q -e 'already exists'
  local catalogGroupExists=$?
  if [ $catalogGroupExists -ne 0 ]; then
    echo $catalogGroupResp
    exit -1;
  fi
  checkstatus
}

# --------------------------------------------------------------------------------

addRuntimeGroup() {
  local runtimeGroupResp=`${UAAC} group add analytics.zones.${RUNTIME_INSTANCE_ID}.user`
  local runtimeGroupStatus=$?
  echo $runtimeGroupResp | grep -q -e 'already exists'
  local runtimeGroupExists=$?
  if [ $runtimeGroupExists -ne 0 ]; then
    echo $runtimeGroupResp
    exit -1;
  fi
  checkstatus
}

# --------------------------------------------------------------------------------

doUserAdd() {
  ${UAAC} user add "${LOGIN_NAME}" \
          --given_name "${FIRST_NAME}" \
          --family_name "${SUR_NAME}" \
          --emails "${EMAIL_1}" \
          --password "${PW_1}"
  checkstatus

  addRuntimeGroup

  addCatalogGroup

  ${UAAC} member add analytics.zones.${CATALOG_INSTANCE_ID}.user "${LOGIN_NAME}"
  checkstatus

  ${UAAC} member add analytics.zones.${RUNTIME_INSTANCE_ID}.user "${LOGIN_NAME}"
  checkstatus
}

# --------------------------------------------------------------------------------

systemCheck() {
  UAAC=`which uaac`
  if [ -z "${UAAC}" ]; then
    echo ""
    echo "This script requires the uaac client which was not found on your system."
    echo "A simple source to install uaac and cf can be found at https://github.com/cloudfoundry-community/traveling-cf-admin"
    exit 1;
  fi

  cf target
  if [ $? -ne 0 ]; then
    echo
    echo "You are not logged in to Cloud Foundry.  Please first use 'cf login' to log in to your org and space before using this script."
    exit 1;
  fi


}

function welcome() {
  clear
  echo
  echo "**********************************************************************"
  echo
  echo "This script allows you to add a user to your UAA instance associated with "
  echo "your Predix Analytics UI instance.  It will ask you for necessary "
  echo "information about your environment and allow you to enter new user "
  echo "credentials to create users to use your new Analytics UI."
  echo
  echo "You should have already followed the instructions to create your "
  echo "Analytics UI instance and all dependent service instances as documented"
  echo "here https://www.predix.io/docs#ODbwpgV"
  echo
  echo "****************************************************************************"
  echo "****************************************************************************"
  echo "**                                                                        **"
  echo "**  THIS SCRIPT IS CONTRIBUTED *AS IS* TO MAKE IT EASIER TO CREATE        **"
  echo "**  USERS FOR PREDIX ANALYTICS.  THE SCRIPT WAS DERIVED FROM THE          **"
  echo "**  INSTRUCTIONS FOUND AT https://www.predix.io/docs/?r=248899#RulzoBew   **"
  echo "**                                                                        **"
  echo "**  NO WARRANTIES, NEITHER EXPRESS NOR IMPLIED.                           **"
  echo "**                                                                        **"
  echo "****************************************************************************"
  echo "****************************************************************************"

}
# --------------------------------------------------------------------------------

# DO THE WORK NOW

welcome

systemCheck

selectUAA

selectCatalog

selectRuntime

getUser

logInToUAA

configureAdminClientInUAA

doUserAdd

