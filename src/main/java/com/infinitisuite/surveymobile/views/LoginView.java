package com.infinitisuite.surveymobile.views;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import com.google.inject.Inject;
import com.google.inject.Provider;
import com.infinitisuite.surveymobile.R;
import com.infinitisuite.surveymobile.presenters.SimpleDialogPresenter;
import com.infinitisuite.surveymobile.presenters.SimpleDialogView;
import roboguice.inject.InjectView;

public class LoginView implements ILoginView {
    @Inject protected static Provider<Context> contextProvider;
    protected Context context = contextProvider.get();

    @InjectView(R.id.email) EditText mEmailView;
    @InjectView(R.id.password) EditText mPasswordView;
    @InjectView(R.id.login_form) View mLoginFormView;
    @InjectView(R.id.login_status) View mLoginStatusView;
    @InjectView(R.id.login_status_message) TextView mLoginStatusMessageView;

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
        mPasswordView.setError(context.getString(R.string.error_field_required));
        mPasswordView.requestFocus();
    }

    @Override
    public void setEmailRequiredError() {
        mEmailView.setError(context.getString(R.string.error_field_required));
        mEmailView.requestFocus();
    }

    @Override
    public void setEmailInvalidError() {
        mEmailView.setError(context.getString(R.string.error_invalid_email));
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
        new SimpleDialogPresenter(new SimpleDialogView(context, context.getString(R.string.login_failed_alert_title), context.getString(R.string.login_failed_alert_message))).show();
    }
}
