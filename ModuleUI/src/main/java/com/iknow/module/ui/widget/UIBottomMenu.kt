package com.iknow.module.ui.widget

import android.app.Dialog
import android.content.Context
import com.iknow.module.ui.R

/**
 * 这是一个通用的底部菜单
 */
open class UIBottomMenu(context: Context) : Dialog(context) {

    init {
        setContentView(R.layout.ui_bottom_dialog)
    }

}