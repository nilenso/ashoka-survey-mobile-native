package com.infinitisuite.surveymobile.presenters;

import com.infinitisuite.surveymobile.util.SurveyWebHttpClient;
import com.infinitisuite.surveymobile.views.ILoginView;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;

import static org.mockito.Mockito.*;

@RunWith(RobolectricTestRunner.class)
public class LoginPresenterTest {

    private LoginPresenter presenter;
    private ILoginView loginViewMock;

    @Before
    public void setUp() throws Exception {
        loginViewMock = mock(ILoginView.class);
        presenter = new LoginPresenter(loginViewMock);
        SurveyWebHttpClient.makeAllOperationsSynchronous();
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
        Robolectric.addPendingHttpResponse(401, "Unauthorized");
        doReturn("foo@bar.com").when(loginViewMock).getEmail();
        doReturn("bar").when(loginViewMock).getPassword();
        presenter.attemptLogin();
        verify(loginViewMock, times(1)).showLoginError();
    }

    @Test
    public void shows_spinner_while_logging_in_if_username_and_password_are_valid() throws Exception {
        Robolectric.addPendingHttpResponse(200, "OK");
        doReturn("foo@bar.com").when(loginViewMock).getEmail();
        doReturn("bar").when(loginViewMock).getPassword();
        presenter.attemptLogin();
        verify(loginViewMock, times(1)).showSigningIn();
    }

    @Test
    public void hides_spinner_if_login_is_successful() throws Exception {
        Robolectric.addPendingHttpResponse(200, "OK");
        doReturn("foo@bar.com").when(loginViewMock).getEmail();
        doReturn("bar").when(loginViewMock).getPassword();
        presenter.attemptLogin();
        verify(loginViewMock, times(1)).hideSigningIn();
    }

    @Test
    public void hides_spinner_if_login_is_unsuccessful() throws Exception {
        Robolectric.addPendingHttpResponse(401, "Unauthorized");
        doReturn("foo@bar.com").when(loginViewMock).getEmail();
        doReturn("bar").when(loginViewMock).getPassword();
        presenter.attemptLogin();
        verify(loginViewMock, times(1)).hideSigningIn();
    }
}
