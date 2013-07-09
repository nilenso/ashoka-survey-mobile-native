package com.infinitisuite.surveymobile.services;

import com.infinitisuite.surveymobile.handlers.UserLoginHandler;
import com.infinitisuite.surveymobile.models.User;
import com.infinitisuite.surveymobile.util.SurveyWebHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import org.apache.http.client.HttpResponseException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;

import static org.mockito.Mockito.*;

@RunWith(RobolectricTestRunner.class)
public class UserServiceTest {

    private SurveyWebHttpClientStub client;

    @Before
    public void initialize() {
        client = new SurveyWebHttpClientStub();
    }

    @Test
    public void notify_success_if_the_email_and_password_are_correct() {
        client.setSuccess();
        UserLoginHandler userLoginHandler = mock(UserLoginHandler.class);
        User user = new User("foo@bar.com", "bar");
        new UserService(client).login(user, userLoginHandler);
        verify(userLoginHandler, times(1)).notifySuccess();
        verify(userLoginHandler, never()).notifyError("Error");
    }

    @Test
    public void notify_error_if_email_or_password_is_wrong() {
        client.setFailure();
        User user = new User("wrong@example.com", "wrong!");
        UserLoginHandler userLoginHandler = mock(UserLoginHandler.class);
        new UserService(client).login(user, userLoginHandler);
        verify(userLoginHandler, times(1)).notifyError("Unauthorized");
        verify(userLoginHandler, never()).notifySuccess();
    }

    @Test
    public void notify_timeout_if_the_network_call_times_out() throws Exception {
        // TODO: make this test pass. :)
    }

    private class SurveyWebHttpClientStub extends SurveyWebHttpClient {
        private boolean isSuccess;

        public void setSuccess() {
            isSuccess = true;
        }

        public void setFailure() {
            isSuccess = false;
        }

        @Override
        public void post(String url, RequestParams params, AsyncHttpResponseHandler responseHandler) {
            if (isSuccess) {
                responseHandler.onSuccess("Success!");
            }
            else {
                responseHandler.onFailure(new HttpResponseException(401, "Unauthorized"), "Unauthorized");
            }
        }
    }
}
