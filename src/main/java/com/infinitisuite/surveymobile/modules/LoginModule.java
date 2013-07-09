package com.infinitisuite.surveymobile.modules;

import com.google.inject.Binder;
import com.google.inject.Module;
import com.infinitisuite.surveymobile.services.IUserService;
import com.infinitisuite.surveymobile.services.UserService;
import com.infinitisuite.surveymobile.views.ILoginView;
import com.infinitisuite.surveymobile.views.LoginView;

public class LoginModule implements Module {
    @Override
    public void configure(Binder binder) {
        binder.bind(ILoginView.class).to(LoginView.class);
        binder.bind(IUserService.class).to(UserService.class);
    }
}
