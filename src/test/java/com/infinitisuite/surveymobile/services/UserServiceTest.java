package com.infinitisuite.surveymobile.services;

import com.infinitisuite.surveymobile.handlers.UserLoginHandler;
import com.infinitisuite.surveymobile.models.User;
import com.infinitisuite.surveymobile.util.SurveyWebHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import org.apache.http.client.HttpResponseException;
import org.apache.http.conn.ConnectTimeoutException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
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
    }

    @Test
    public void notify_error_if_email_or_password_is_wrong() {
        client.setFailure();
        User user = new User("wrong@example.com", "wrong!");
        UserLoginHandler userLoginHandler = mock(UserLoginHandler.class);
        new UserService(client).login(user, userLoginHandler);
        verify(userLoginHandler, times(1)).notifyError("Unauthorized");
    }

    @Test
    public void notify_timeout_if_the_network_call_times_out() throws Exception {
        client.setTimeout();
        User user = new User("foo@bar.com", "bar");
        UserLoginHandler userLoginHandler = mock(UserLoginHandler.class);
        new UserService(client).login(user, userLoginHandler);
        verify(userLoginHandler, times(1)).notifyTimeout("Timed out");
    }

    private enum SurveyWebHttpClientStubResponseStatus { SUCCESS, FAILURE, TIMEOUT };
    private class SurveyWebHttpClientStub extends SurveyWebHttpClient {
        private SurveyWebHttpClientStubResponseStatus responseStatus;

        public void setSuccess() {
            responseStatus = SurveyWebHttpClientStubResponseStatus.SUCCESS;
        }

        public void setFailure() {
            responseStatus = SurveyWebHttpClientStubResponseStatus.FAILURE;
        }

        public void setTimeout() {
            responseStatus = SurveyWebHttpClientStubResponseStatus.TIMEOUT;
        }

        @Override
        public void post(String url, RequestParams params, AsyncHttpResponseHandler responseHandler) {
            switch (responseStatus) {
                case SUCCESS:
                    responseHandler.onSuccess("Success!");
                    break;
                case FAILURE:
                    responseHandler.onFailure(new HttpResponseException(401, "Unauthorized"), "Unauthorized");
                    break;
                case TIMEOUT:
                    responseHandler.onFailure(new ConnectTimeoutException(), "Timed out");
                    break;
            }
        }
    }
}
