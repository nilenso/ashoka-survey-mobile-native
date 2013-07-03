package com.infinitisuite.surveymobile.test.models;

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
public class UserTest {
    @Before
    public void initialize() {
        SurveyWebHttpClient.mock();
    }

    @Test
    public void notifySuccessIfTheEmailAndPasswordAreCorrect() {
        Robolectric.addPendingHttpResponse(200, "OK");
        UserLoginHandler userLoginHandler = mock(UserLoginHandler.class);
        new User("foo@bar.com", "bar").login(userLoginHandler);
        verify(userLoginHandler, times(1)).notifySuccess();
        verify(userLoginHandler, never()).notifyError("Error");
    }

    @Test
    public void notifyErrorIfEmailOrPasswordIsWrong() {
        Robolectric.addPendingHttpResponse(401, "Unauthorized");
        UserLoginHandler userLoginHandler = mock(UserLoginHandler.class);
        new User("wrong@example.com", "wrong!").login(userLoginHandler);
        verify(userLoginHandler, times(1)).notifyError("Unauthorized");
        verify(userLoginHandler, never()).notifySuccess();
    }
}
