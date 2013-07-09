package com.infinitisuite.surveymobile;

import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import com.google.inject.Inject;
import com.infinitisuite.surveymobile.presenters.LoginPresenter;
import com.infinitisuite.surveymobile.services.UserService;
import com.infinitisuite.surveymobile.util.SurveyWebHttpClient;
import com.infinitisuite.surveymobile.views.LoginView;
import roboguice.activity.RoboActivity;
import roboguice.inject.InjectView;

/**
 * Activity which displays a login screen to the user, offering registration as
 * well.
 */
public class LoginActivity extends RoboActivity {

    //@Inject LoginPresenter mPresenter;
    @InjectView(R.id.sign_in_button) Button mSignInButtonView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mSignInButtonView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mPresenter.attemptLogin();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.login, menu);
        return true;
    }
}
