package com.iknow.module.help.module.error.view;

import android.content.Intent;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.widget.Toolbar;

import com.iknow.module.base.view.BaseAct;
import com.iknow.module.help.R;

public class ErrorAct extends BaseAct {

    private TextView tv_error;
    private Toolbar toolbar;
    private Button bt_reboot;

    private String errorMsg;

    @Override
    protected int getLayoutId() {
        return R.layout.help_error_act;
    }

    @Override
    protected void onInjectView() {
        super.onInjectView();
        tv_error = findViewById(R.id.tv_error);
        toolbar = findViewById(R.id.toolbar);
        bt_reboot = findViewById(R.id.bt_reboot);
    }

    @Override
    protected void onInit() {
        super.onInit();
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        errorMsg = getIntent().getStringExtra("errorMsg");
        tv_error.setText(errorMsg);

        bt_reboot.setOnClickListener(v -> {

            Intent intent = new Intent("iknow_main");
            intent.addCategory(Intent.CATEGORY_DEFAULT);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            finish();

        });

    }

}
