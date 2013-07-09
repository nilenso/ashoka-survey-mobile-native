package com.infinitisuite.surveymobile.services;

import com.google.inject.Inject;
import com.infinitisuite.surveymobile.handlers.UserLoginHandler;
import com.infinitisuite.surveymobile.models.User;
import com.infinitisuite.surveymobile.util.SurveyWebHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import org.apache.http.client.HttpResponseException;

public class UserService implements IUserService {

    private SurveyWebHttpClient client;

    @Inject
    public UserService(SurveyWebHttpClient client) {
        this.client = client;
    }

    @Override
    public void login(User user, final UserLoginHandler userLoginHandler) {
        RequestParams params = new RequestParams();
        params.put("username", user.getUsername());
        params.put("password", user.getPassword());

        client.post("/api/login", params, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(String s) {
                userLoginHandler.notifySuccess();
            }

            @Override
            public void onFailure(Throwable throwable, String s) {
                if (throwable.getClass().equals(HttpResponseException.class)){
                    userLoginHandler.notifyError(s);
                }
                else {
                    userLoginHandler.notifyServerUnreachable(s);
                }
            }
        });
    }
}
