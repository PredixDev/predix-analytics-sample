function Out_Str=LocomotiveRegression(DataStr)
% Parse the data and call the regression function
data = JSON.parse(DataStr);


% for educational purposes, contents of "data" have been casted into single
% variables

% training data
loco_sp_tr    = cell2mat(data.train.loco_speed(:));
loco_sp_sq_tr = cell2mat(data.train.loco_speed(:)).^2;
wind_sp_tr = cell2mat(data.train.wind_speed(:));
loco_rtm_tr   = cell2mat(data.train.RTM(:));

feature_columns_tr = [loco_sp_tr, wind_sp_tr, loco_sp_sq_tr, ones(size(loco_rtm_tr))];
label_column_tr  = loco_rtm_tr;

%linear regression parameters
lr_params   = pinv(feature_columns_tr' * feature_columns_tr)* ...
                    feature_columns_tr' * label_column_tr;
%lm          = fitlm(feature_columns_tr(:,1:3), label_column_tr)

% calculate errors and statistical values for train data
fitted_value = feature_columns_tr*lr_params;
err_tr       = loco_rtm_tr - fitted_value;
distance     = loco_rtm_tr - mean(loco_rtm_tr);
R2           = 1 - sum(err_tr.^2)/sum(distance.^2);


% test data
loco_sp_tst = cell2mat(data.test.loco_speed(:));
loco_sp_sq_tst = cell2mat(data.test.loco_speed(:)).^2;
wind_sp_tst = cell2mat(data.test.wind_speed(:));
loco_rtm_tst  = cell2mat(data.test.RTM(:));
feature_columns_tst = [loco_sp_tst, wind_sp_tst, loco_sp_sq_tst, ones(size(loco_rtm_tst))];

%calculate linear model output using test data
fitted_value_tst = feature_columns_tst*lr_params;


%% Create JSON String
Out_Str = ['{"R2": [' ]; %,'],"Prediction": [','%f',']}'];
Out_Str = [Out_Str, num2str(R2)];
Out_Str = [Out_Str,'],"Prediction": ['];
for ii = 1:length(fitted_value_tst)
    Out_Str = [Out_Str, num2str(fitted_value_tst(ii))];
    Out_Str = [Out_Str,','];
end
Out_Str = [Out_Str(1:end-1),']}'];
end