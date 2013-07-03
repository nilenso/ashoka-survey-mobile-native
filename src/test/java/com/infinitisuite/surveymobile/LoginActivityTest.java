package com.infinitisuite.surveymobile;

import android.app.Activity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import com.google.inject.Inject;
import com.infinitisuite.surveymobile.LoginActivity;
import com.infinitisuite.surveymobile.util.SurveyWebHttpClient;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;


import org.junit.runners.JUnit4;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.util.ActivityController;
import roboguice.inject.InjectResource;

import static org.fest.assertions.Assertions.assertThat;

@RunWith(RobolectricTestRunner.class)
public class LoginActivityTest {
    private LoginActivity activity;
    private TextView errorView;
    private Button loginButton;
    private EditText email;
    private EditText password;


    @Before
    public void setUp() {
        activity = Robolectric.buildActivity(LoginActivity.class).create().get();
        activity.onCreate(null);
        errorView = (TextView) activity.findViewById(R.id.errorView);
        loginButton = (Button) activity.findViewById(R.id.sign_in_button);
        email = (EditText) activity.findViewById(R.id.email);
        password = (EditText) activity.findViewById(R.id.password);
    }

    @Test
    public void showsErrorMessageIfUsernameAndPasswordAreWrong() throws Exception {
        // Login should return a 401
        Robolectric.addPendingHttpResponse(401, "Unauthorized");
        SurveyWebHttpClient.makeAllOperationsSynchronous();

        email.setText("foo");
        password.setText("bar");
        loginButton.performClick();
        Robolectric.runUiThreadTasksIncludingDelayedTasks();
        assertThat(errorView.getVisibility()).isEqualTo(View.VISIBLE);
    }
}
