package com.iknow.module.ui;

import android.app.Dialog;
import android.content.Context;

import androidx.annotation.NonNull;

public class UIBottomMenu extends Dialog {

    public UIBottomMenu(@NonNull Context context) {
        super(context);
        setContentView(R.layout.ui_progress_dialog);
    }

    public UIBottomMenu(@NonNull Context context, int themeResId) {
        super(context, themeResId);
        setContentView(R.layout.ui_progress_dialog);
    }
}
