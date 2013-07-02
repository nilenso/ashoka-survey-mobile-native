package com.infinitisuite.surveymobile.models;

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

    public void login(final UserLoginHandler userLoginHandler) {
        RequestParams params = new RequestParams();
        params.put("username", username);
        params.put("password", password);

        SurveyWebHttpClient.post("/api/login", params, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(String s) {
                userLoginHandler.notifySuccess();
            }

            @Override
            public void onFailure(Throwable throwable, String s) {
                super.onFailure(throwable, s);
            }
        });
    }
}
