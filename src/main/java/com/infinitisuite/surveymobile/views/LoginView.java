package com.infinitisuite.surveymobile.views;

import android.app.Activity;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import com.google.inject.Inject;
import com.infinitisuite.surveymobile.R;
import com.infinitisuite.surveymobile.presenters.SimpleDialogPresenter;
import com.infinitisuite.surveymobile.presenters.SimpleDialogView;

public class LoginView implements ILoginView {

    private TextView mLoginStatusMessageView;
    private View mLoginStatusView;
    private View mLoginFormView;
    private EditText mPasswordView;
    private EditText mEmailView;

    @Inject protected Activity mActivity;

    public void onCreate() {
        mEmailView = (EditText) mActivity.findViewById(R.id.email);
        mPasswordView = (EditText) mActivity.findViewById(R.id.password);
        mLoginFormView = mActivity.findViewById(R.id.login_form);
        mLoginStatusView = mActivity.findViewById(R.id.login_status);
        mLoginStatusMessageView = (TextView) mActivity.findViewById(R.id.login_status_message);
    }

    @Override
    public void showTimeoutError() {
        new SimpleDialogPresenter(new SimpleDialogView(mActivity, mActivity.getString(R.string.login_failed_alert_title), mActivity.getString(R.string.login_timed_out_alert_message))).show();
    }

    @Override
    public void reset() {
        mEmailView.setError(null);
        mPasswordView.setError(null);
    }

    @Override
    public String getEmail() {
        return mEmailView.getText().toString();
    }

    @Override
    public String getPassword() {
        return mPasswordView.getText().toString();
    }

    @Override
    public void setPasswordRequiredError() {
        mPasswordView.setError(mActivity.getString(R.string.error_field_required));
        mPasswordView.requestFocus();
    }

    @Override
    public void setEmailRequiredError() {
        mEmailView.setError(mActivity.getString(R.string.error_field_required));
        mEmailView.requestFocus();
    }

    @Override
    public void setEmailInvalidError() {
        mEmailView.setError(mActivity.getString(R.string.error_invalid_email));
        mEmailView.requestFocus();
    }

    @Override
    public void showSigningIn() {
        mLoginStatusMessageView.setText(R.string.login_progress_signing_in); // TODO: this isn't necessary?
        mLoginFormView.setVisibility(View.INVISIBLE);
        mLoginStatusView.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideSigningIn() {
        mLoginFormView.setVisibility(View.VISIBLE);
        mLoginStatusView.setVisibility(View.INVISIBLE);
    }

    @Override
    public void showLoginSuccess() {
        new SimpleDialogPresenter(new SimpleDialogView(mActivity, mActivity.getString(R.string.login_success_alert_title), mActivity.getString(R.string.login_success_alert_message))).show();
    }

    @Override
    public void showLoginError() {
        new SimpleDialogPresenter(new SimpleDialogView(mActivity, mActivity.getString(R.string.login_failed_alert_title), mActivity.getString(R.string.login_failed_alert_message))).show();
    }
}
