package com.infinitisuite.surveymobile.views;

public interface ILoginView {

    void reset();
    String getEmail();
    String getPassword();

    void setPasswordRequiredError();
    void setEmailRequiredError();
    void setEmailInvalidError();

    void showSigningIn();
    void hideSigningIn();
    void showLoginError();
    void showTimeoutError();

    void onCreate();
}
