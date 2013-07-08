package com.infinitisuite.surveymobile.presenters;

public class SimpleDialogPresenter {
    private ISimpleDialogView dialogView;

    public SimpleDialogPresenter(ISimpleDialogView dialogView) {
        this.dialogView = dialogView;
    }

    public void show() {
        dialogView.show();
    }
}
