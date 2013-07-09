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

import java.net.UnknownHostException;

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
        client.setResponseStatus(SurveyWebHttpClientStubResponseStatus.SUCCESS);
        UserLoginHandler userLoginHandler = mock(UserLoginHandler.class);
        User user = new User("foo@bar.com", "bar");
        new UserService(client).login(user, userLoginHandler);
        verify(userLoginHandler, times(1)).notifySuccess();
    }

    @Test
    public void notify_error_if_email_or_password_is_wrong() {
        client.setResponseStatus(SurveyWebHttpClientStubResponseStatus.FAILURE);
        User user = new User("wrong@example.com", "wrong!");
        UserLoginHandler userLoginHandler = mock(UserLoginHandler.class);
        new UserService(client).login(user, userLoginHandler);
        verify(userLoginHandler, times(1)).notifyError(anyString());
    }

    @Test
    public void notify_server_unreachable_if_the_network_call_times_out() throws Exception {
        client.setResponseStatus(SurveyWebHttpClientStubResponseStatus.TIMEOUT);
        User user = new User("foo@bar.com", "bar");
        UserLoginHandler userLoginHandler = mock(UserLoginHandler.class);
        new UserService(client).login(user, userLoginHandler);
        verify(userLoginHandler, times(1)).notifyServerUnreachable(anyString());
    }

    @Test
    public void notify_server_unreachable_if_the_host_is_unknown() throws Exception {
        client.setResponseStatus(SurveyWebHttpClientStubResponseStatus.HOST_NOT_FOUND);
        User user = new User("foo@bar.com", "bar");
        UserLoginHandler userLoginHandler = mock(UserLoginHandler.class);
        new UserService(client).login(user, userLoginHandler);
        verify(userLoginHandler, times(1)).notifyServerUnreachable(anyString());
    }

    private enum SurveyWebHttpClientStubResponseStatus { SUCCESS, FAILURE, TIMEOUT, HOST_NOT_FOUND };
    private class SurveyWebHttpClientStub extends SurveyWebHttpClient {
        private SurveyWebHttpClientStubResponseStatus responseStatus;

        private void setResponseStatus(SurveyWebHttpClientStubResponseStatus responseStatus) {
            this.responseStatus = responseStatus;
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
                case HOST_NOT_FOUND:
                    responseHandler.onFailure(new UnknownHostException(), "Unknown Host");
                    break;
            }
        }
    }
}
