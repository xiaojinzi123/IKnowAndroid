package com.iknow.module.base.widget;

import android.text.Editable;
import android.text.TextWatcher;

/**
 * desc: TextWatcher 空实现
 * auth: 32052
 * time: 2019/4/28
 */
public class TextWatcherAdapter implements TextWatcher {
    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
    }

    @Override
    public void afterTextChanged(Editable s) {
    }

}
