package com.infinitisuite.surveymobile.services;

import com.infinitisuite.surveymobile.handlers.UserLoginHandler;
import com.infinitisuite.surveymobile.models.User;
import com.infinitisuite.surveymobile.util.SurveyWebHttpClient;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;

import static org.mockito.Mockito.*;

@RunWith(RobolectricTestRunner.class)
public class UserServiceTest {

    private SurveyWebHttpClient client;

    @Before
    public void initialize() {
        client = new SurveyWebHttpClient();
        client.makeAllOperationsSynchronous();
    }

    @Test
    public void notify_success_if_the_email_and_password_are_correct() {
        Robolectric.addPendingHttpResponse(200, "OK");
        UserLoginHandler userLoginHandler = mock(UserLoginHandler.class);
        User user = new User("foo@bar.com", "bar");
        new UserService(client).login(user, userLoginHandler);
        verify(userLoginHandler, times(1)).notifySuccess();
        verify(userLoginHandler, never()).notifyError("Error");
    }

    @Test
    public void notify_error_if_email_or_password_is_wrong() {
        Robolectric.addPendingHttpResponse(401, "Unauthorized");
        UserLoginHandler userLoginHandler = mock(UserLoginHandler.class);
        User user = new User("wrong@example.com", "wrong!");
        new UserService(client).login(user, userLoginHandler);
        verify(userLoginHandler, times(1)).notifyError("Unauthorized");
        verify(userLoginHandler, never()).notifySuccess();
    }

    @Test
    public void notify_timeout_if_the_network_call_times_out() throws Exception {
        // TODO: make this test pass. :)
    }
}
