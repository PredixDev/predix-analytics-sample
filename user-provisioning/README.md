# Adding users to UAA to use Analytics UI

This [analytics-ui-user-add.sh](./analytics-ui-user-add.sh) script can be used to add users with the
proper credentials to your UAA to allow them to use your new Analytics UI instance.  It is a Bash shell
script that can be used on all Unix flavors as well as cygwin or GitBash if you're on Windows.

##Prerequisites
1. You must first follow the instructions to set up Analytics UI and its dependent services as per [these instructions](https://docs.predix.io/en-US/content/service/analytics_services/analytic_user_interface/get-started)
2. You must have a UAAC client installed as this script uses it.  [This one](https://github.com/cloudfoundry-community/traveling-cf-admin) is an easy one to install.
3. You must be first logged in to your Cloud Foundry org and space on the commandline by `cf login`.

This script is provided to the GE community **AS IS** to enable us to create users more easily, in advance of more
user-friendly GUI tools to do the same.  As such, there are no guarantees or warranties offered with this script.
Pull Requests are welcome.  Questions may be asked in the
[Issues](../../../issues) to the right on this page.

##Usage
1. Copy this [analytics-ui-user-add.sh](./analytics-ui-user-add.sh) to your system,
2. Make it executable with `chmod +x analytics-ui-user-add.sh`
3. Issue `./analytics-ui-user-add.sh` and follow the prompts.

The script initally collects alot of information from you, then it does the UAAC work. If you want abort at
any time, just hit ctrl-c to end the script.

