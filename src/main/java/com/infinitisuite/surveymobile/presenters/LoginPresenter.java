package com.infinitisuite.surveymobile.presenters;

import android.text.TextUtils;
import com.google.inject.Inject;
import com.infinitisuite.surveymobile.handlers.UserLoginHandler;
import com.infinitisuite.surveymobile.models.User;
import com.infinitisuite.surveymobile.services.IUserService;
import com.infinitisuite.surveymobile.views.ILoginView;

public class LoginPresenter {
    private IUserService userService;
    private ILoginView loginView;

    @Inject
    public LoginPresenter(IUserService userService, ILoginView loginView) {
        this.userService = userService;
        this.loginView = loginView;
    }

    public void onCreate() {
        this.loginView.onCreate();
    }

    public void attemptLogin() {
        loginView.reset();

        if (validate()) {
            login();
        }
    }

    private void login() {
        loginView.showSigningIn();
        userService.login(new User(loginView.getEmail(), loginView.getPassword()), new UserLoginHandler() {
            @Override
            public void notifySuccess() {
                loginView.hideSigningIn();
            }

            @Override
            public void notifyError(String errorMessage) {
                loginView.hideSigningIn();
                loginView.showLoginError();
            }

            @Override
            public void notifyTimeout(String s) {
                loginView.hideSigningIn();
                loginView.showTimeoutError();
            }
        });
    }

    private boolean validate() {
        if (TextUtils.isEmpty(loginView.getPassword())) {
            loginView.setPasswordRequiredError();
            return false;
        }
        if (TextUtils.isEmpty(loginView.getEmail())) {
            loginView.setEmailRequiredError();
            return false;
        }
        if (!loginView.getEmail().contains("@")) {
            loginView.setEmailInvalidError();
            return false;
        }
        return true;
    }
}
