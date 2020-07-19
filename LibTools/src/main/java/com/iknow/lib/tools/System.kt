package com.iknow.lib.tools

import android.widget.Toast

/**
 * 短暂的吐司
 */
fun toastShort(text: String): Unit{
    Toast.makeText(ToolsConfig.getApp(), text, Toast.LENGTH_SHORT).show()
}

/**
 * 长时间的吐司
 */
fun toastLong(text: String): Unit{
    Toast.makeText(ToolsConfig.getApp(), text, Toast.LENGTH_LONG).show()
}