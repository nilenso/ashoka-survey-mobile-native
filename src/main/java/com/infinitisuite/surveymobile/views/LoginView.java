package com.infinitisuite.surveymobile.views;

import android.app.Activity;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import com.infinitisuite.surveymobile.R;
import com.infinitisuite.surveymobile.presenters.SimpleDialogPresenter;
import com.infinitisuite.surveymobile.presenters.SimpleDialogView;

public class LoginView implements ILoginView {

    private Activity mActivity;
    private final EditText mEmailView;
    private final EditText mPasswordView;
    private final View mLoginFormView;
    private final View mLoginStatusView;
    private final TextView mLoginStatusMessageView;
    private final View mSignInButtonView;
    private final TextView mErrorView;

    // TODO: is there a better way of dealing with the i18n stuff than passing through the Activity? maybe wrap Activity with an Internationalization.getString interface or something
    public LoginView(Activity activity, EditText mEmailView, EditText mPasswordView, View mLoginFormView, View mLoginStatusView, TextView mLoginStatusMessageView, View mSignInButtonView, TextView mErrorView) {
        this.mActivity = activity;
        this.mEmailView = mEmailView;
        this.mPasswordView = mPasswordView;
        this.mLoginFormView = mLoginFormView;
        this.mLoginStatusView = mLoginStatusView;
        this.mLoginStatusMessageView = mLoginStatusMessageView;
        this.mSignInButtonView = mSignInButtonView;
        this.mErrorView = mErrorView;
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
    public void showLoginError() {
        new SimpleDialogPresenter(new SimpleDialogView(mActivity, mActivity.getString(R.string.login_failed_alert_title), mActivity.getString(R.string.login_failed_alert_message))).show();
    }
}
