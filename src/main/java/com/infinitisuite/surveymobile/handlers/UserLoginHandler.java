package com.infinitisuite.surveymobile.handlers;

public interface UserLoginHandler {
    void notifySuccess();
    void notifyError(String errorMessage);
    void notifyTimeout(String s);
}
