package com.infinitisuite.surveymobile.presenters;

import com.infinitisuite.surveymobile.views.ISimpleDialogView;

public class SimpleDialogPresenter {
    private ISimpleDialogView dialogView;

    public SimpleDialogPresenter(ISimpleDialogView dialogView) {
        this.dialogView = dialogView;
    }

    public void show() {
        dialogView.show();
    }
}
