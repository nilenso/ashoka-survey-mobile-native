package com.infinitisuite.surveymobile.presenters;

import com.infinitisuite.surveymobile.handlers.UserLoginHandler;
import com.infinitisuite.surveymobile.models.User;
import com.infinitisuite.surveymobile.services.IUserService;
import com.infinitisuite.surveymobile.views.ILoginView;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;

import static org.mockito.Mockito.*;

@RunWith(RobolectricTestRunner.class)
public class LoginPresenterTest {

    private LoginPresenter presenter;
    private ILoginView loginViewMock;
    private UserServiceStub userService;

    @Before
    public void setUp() throws Exception {
        loginViewMock = mock(ILoginView.class);
        userService = new UserServiceStub();
        presenter = new LoginPresenter(userService, loginViewMock);
    }

    @Test
    public void shows_a_validation_error_if_password_is_empty() throws Exception {
        doReturn("foo@bar.com").when(loginViewMock).getEmail();
        doReturn(null).when(loginViewMock).getPassword();
        presenter.attemptLogin();
        verify(loginViewMock, times(1)).setPasswordRequiredError();
    }

    @Test
    public void shows_a_validation_error_if_email_is_empty() throws Exception {
        doReturn(null).when(loginViewMock).getEmail();
        doReturn("foo").when(loginViewMock).getPassword();
        presenter.attemptLogin();
        verify(loginViewMock, times(1)).setEmailRequiredError();
    }

    @Test
    public void shows_a_validation_error_if_email_doesnt_have_an_at_sign() throws Exception {
        doReturn("abc.com").when(loginViewMock).getEmail();
        doReturn("foo").when(loginViewMock).getPassword();
        presenter.attemptLogin();
        verify(loginViewMock, times(1)).setEmailInvalidError();
    }

    @Test
    public void shows_error_message_if_username_and_password_are_wrong() throws Exception {
        userService.setFailure();
        doReturn("foo@bar.com").when(loginViewMock).getEmail();
        doReturn("bar").when(loginViewMock).getPassword();
        presenter.attemptLogin();
        verify(loginViewMock, times(1)).showLoginError();
    }

    @Test
    public void shows_spinner_while_logging_in_if_username_and_password_are_valid() throws Exception {
        userService.setSuccess();
        doReturn("foo@bar.com").when(loginViewMock).getEmail();
        doReturn("bar").when(loginViewMock).getPassword();
        presenter.attemptLogin();
        verify(loginViewMock, times(1)).showSigningIn();
    }

    @Test
    public void shows_success_message_if_login_is_successful() throws Exception {
        userService.setSuccess();
        doReturn("foo@bar.com").when(loginViewMock).getEmail();
        doReturn("bar").when(loginViewMock).getPassword();
        presenter.attemptLogin();
        verify(loginViewMock, times(1)).showLoginSuccess();
    }

    @Test
    public void hides_spinner_if_login_is_successful() throws Exception {
        userService.setSuccess();
        doReturn("foo@bar.com").when(loginViewMock).getEmail();
        doReturn("bar").when(loginViewMock).getPassword();
        presenter.attemptLogin();
        verify(loginViewMock, times(1)).hideSigningIn();
    }

    @Test
    public void hides_spinner_if_login_is_unsuccessful() throws Exception {
        userService.setFailure();
        doReturn("foo@bar.com").when(loginViewMock).getEmail();
        doReturn("bar").when(loginViewMock).getPassword();
        presenter.attemptLogin();
        verify(loginViewMock, times(1)).hideSigningIn();
    }

    @Test
    public void hides_spinner_if_login_times_out() throws Exception {
        userService.setTimeout();
        doReturn("foo@bar.com").when(loginViewMock).getEmail();
        doReturn("bar").when(loginViewMock).getPassword();
        presenter.attemptLogin();
        verify(loginViewMock, times(1)).hideSigningIn();
    }

    @Test
    public void shows_timeout_error_when_network_call_times_out() throws Exception {
        userService.setTimeout();
        doReturn("foo@bar.com").when(loginViewMock).getEmail();
        doReturn("foo").when(loginViewMock).getPassword();
        presenter.attemptLogin();
        verify(loginViewMock, times(1)).showTimeoutError();

    }

    public enum UserServiceStubHttpResponse { SUCCESS, FAILURE, TIMEOUT }
    private class UserServiceStub implements IUserService {
        private UserServiceStubHttpResponse httpResponse;

        public void setSuccess() {
            httpResponse = UserServiceStubHttpResponse.SUCCESS;
        }

        public void setFailure() {
            httpResponse = UserServiceStubHttpResponse.FAILURE;
        }

        public void setTimeout() {
            httpResponse = UserServiceStubHttpResponse.TIMEOUT;
        }

        @Override
        public void login(User user, UserLoginHandler userLoginHandler) {
            switch (httpResponse) {
                case SUCCESS:
                    userLoginHandler.notifySuccess();
                    break;
                case FAILURE:
                    userLoginHandler.notifyError("Error!");
                    break;
                case TIMEOUT:
                    userLoginHandler.notifyServerUnreachable("Timeout");
                    break;
            }
        }
    }
}
