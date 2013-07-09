package com.infinitisuite.surveymobile;

import android.app.Application;
import com.infinitisuite.surveymobile.modules.LoginModule;
import roboguice.RoboGuice;

public class SurveyMobileApp extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        RoboGuice.setBaseApplicationInjector(this, RoboGuice.DEFAULT_STAGE, RoboGuice.newDefaultRoboModule(this), new LoginModule());
    }
}
