package com.infinitisuite.surveymobile.presenters;

import android.text.TextUtils;
import com.infinitisuite.surveymobile.handlers.UserLoginHandler;
import com.infinitisuite.surveymobile.models.User;
import com.infinitisuite.surveymobile.views.ILoginView;

public class LoginPresenter {
    private ILoginView loginView;

    public LoginPresenter(ILoginView loginView) {
        this.loginView = loginView;
    }

    public void attemptLogin() {
        loginView.reset();

        if (validate()) {
            login();
        }
    }

    private void login() {
        // Show a progress spinner, and kick off a background task to
        // perform the user login attempt.
        loginView.showSigningIn();

        new User(loginView.getEmail(), loginView.getPassword()).login(new UserLoginHandler() {
            @Override
            public void notifySuccess() {
                loginView.hideSigningIn();
            }

            @Override
            public void notifyError(String errorMessage) {
                loginView.hideSigningIn();
                loginView.showLoginError();
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
