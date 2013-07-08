package com.infinitisuite.surveymobile.models;

import com.google.inject.Inject;
import com.infinitisuite.surveymobile.handlers.UserLoginHandler;
import com.infinitisuite.surveymobile.util.SurveyWebHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

public class User {
    private final String username;
    private final String password;

    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }
}
