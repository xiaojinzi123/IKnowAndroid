package com.iknow.module.base.view.inter

import android.app.Activity
import android.app.Dialog
import android.content.Context
import android.content.DialogInterface
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.text.TextUtils
import android.view.LayoutInflater
import androidx.appcompat.app.AlertDialog
import com.iknow.lib.tools.ResourceUtil
import com.iknow.lib.tools.toastShort
import com.iknow.module.base.R
import com.iknow.module.base.view.Tip
import com.iknow.module.base.view.Tip.TipEnum

/**
 * time   : 2018/03/14
 *
 * @author : xiaojinzi 30212
 */
class BaseViewImpl(context: Context?) : IBaseView {
    private var mContext: Activity? = null
    private var dialog: Dialog? = null
    fun attachContext(context: Context?) {
        if (context == null) {
            mContext = null
        } else {
            if (context is Activity) {
                mContext = context
            }
        }
    }

    fun detachContext() {
        mContext = null
        destroy()
    }

    override fun showProgress() {
        if (mContext == null) {
            destroy()
            return
        }
        if (mContext!!.isDestroyed || mContext!!.isFinishing) { // 如果已经要销毁了
            return
        }
        if (null == dialog) {
            val layout =
                LayoutInflater.from(mContext).inflate(R.layout.ui_progress_dialog, null)
            dialog = Dialog(mContext, R.style.UiLoadingDialog)
            dialog!!.setCanceledOnTouchOutside(false)
            dialog!!.setContentView(layout)
            val window = dialog!!.window
            window.setWindowAnimations(R.style.UiLoadingDialogAnim)
            window.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        }
        // 这个不能少
        if (null == dialog) {
            return
        }
        dialog!!.setCancelable(false)
        if (dialog!!.isShowing) {
            return
        }
        dialog!!.show()
    }

    override fun closeProgress() {
        if (null == dialog) {
            return
        }
        if (dialog!!.isShowing) {
            dialog!!.dismiss()
        }
    }

    override fun tip(tip: Tip) {
        var tipContent: String? = null
        tipContent = if (tip.tipRes != 0) {
            ResourceUtil.getString(tip.tipRes)
        } else {
            tip.tip
        }
        if (TextUtils.isEmpty(tipContent)) {
            return
        }
        when (tip.tipEnum) {
            TipEnum.Error, TipEnum.Normal -> toastShort(tipContent)
            TipEnum.MsgBox -> if (mContext != null) {
                AlertDialog.Builder(mContext!!)
                    .setMessage(tipContent)
                    .setPositiveButton(
                        "ok"
                    ) { dialog: DialogInterface, which: Int -> dialog.dismiss() }
                    .setCancelable(false)
                    .create()
                    .show()
            }
        }
    }

    override fun destroy() {
        closeProgress()
        mContext = null
        dialog = null
    }

    init {
        attachContext(context)
    }
}