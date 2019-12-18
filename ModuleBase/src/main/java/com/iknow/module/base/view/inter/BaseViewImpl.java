package com.iknow.module.base.view.inter;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;

import com.iknow.lib.tools.ResourceUtil;
import com.iknow.lib.tools.ToastUtil;
import com.iknow.module.base.R;
import com.iknow.module.base.view.Tip;

/**
 * time   : 2018/03/14
 *
 * @author : xiaojinzi 30212
 */
public class BaseViewImpl implements IBaseView {

    @Nullable
    private Activity mContext;

    private Dialog dialog;

    public BaseViewImpl(@Nullable Context context) {
        attachContext(context);
    }

    public void attachContext(@Nullable Context context) {
        if (context == null) {
            mContext = null;
        } else {
            if (context instanceof Activity) {
                mContext = (Activity) context;
            }
        }
    }

    public void detachContext() {
        mContext = null;
        destroy();
    }


    @Override
    public void showProgress() {
        if (mContext == null) {
            destroy();
            return;
        }
        if (mContext.isDestroyed() || mContext.isFinishing()) { // 如果已经要销毁了
            return;
        }
        if (null == dialog) {
            View layout = LayoutInflater.from(mContext).inflate(R.layout.ui_progress_dialog, null);
            dialog = new Dialog(mContext, R.style.UiLoadingDialog);
            dialog.setCanceledOnTouchOutside(false);
            dialog.setContentView(layout);
            Window window = dialog.getWindow();
            window.setWindowAnimations(R.style.UiLoadingDialogAnim);
            window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        }
        // 这个不能少
        if (null == dialog) {
            return;
        }
        dialog.setCancelable(false);
        if (dialog.isShowing()) {
            return;
        }
        dialog.show();
    }

    @Override
    public void closeProgress() {
        if (null == dialog) {
            return;
        }
        if (dialog.isShowing()) {
            dialog.dismiss();
        }
    }

    @Override
    public void tip(@NonNull Tip tip) {
        String tipContent = null;
        if (tip.getTipRes() != 0) {
            tipContent = ResourceUtil.getString(tip.getTipRes());
        }else {
            tipContent = tip.getTip();
        }
        if (TextUtils.isEmpty(tipContent)) {
            return;
        }
        switch (tip.getTipEnum()) {
            case Error:
            case Normal:
                ToastUtil.makeText(tipContent);
                break;
            case MsgBox:
                if (mContext != null) {
                    new AlertDialog.Builder(mContext)
                            .setMessage(tipContent)
                            .setPositiveButton("ok", (dialog, which) -> dialog.dismiss())
                            .setCancelable(false)
                            .create()
                            .show();
                }
                break;
        }
    }

    @Override
    public void destroy() {
        closeProgress();
        mContext = null;
        dialog = null;
    }

}
