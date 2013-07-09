package com.infinitisuite.surveymobile;

import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import com.google.inject.Inject;
import com.infinitisuite.surveymobile.presenters.LoginPresenter;
import roboguice.activity.RoboActivity;
import roboguice.inject.InjectView;

/**
 * Activity which displays a login screen to the user, offering registration as
 * well.
 */
public class LoginActivity extends RoboActivity {

    @Inject LoginPresenter mPresenter;
    @InjectView(R.id.sign_in_button) Button mSignInButtonView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mPresenter.onCreate();

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
