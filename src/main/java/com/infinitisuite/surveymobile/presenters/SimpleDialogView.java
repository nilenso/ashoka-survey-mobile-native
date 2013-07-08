package com.infinitisuite.surveymobile.presenters;

import android.R;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;

public class SimpleDialogView implements ISimpleDialogView {

    private Activity mActivity;
    private String mTitle;
    private String mMessage;

    public SimpleDialogView(Activity mActivity, String heading, String message) {
        this.mActivity = mActivity;
        this.mTitle = heading;
        this.mMessage = message;
    }

    @Override
    public void show() {
        AlertDialog.Builder builder = new AlertDialog.Builder(mActivity);
        builder.setTitle(mTitle);
        builder.setMessage(mMessage);
        builder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.create().show();
    }
}