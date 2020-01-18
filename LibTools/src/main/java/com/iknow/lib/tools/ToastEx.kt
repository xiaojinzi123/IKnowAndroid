package com.iknow.lib.tools

import android.widget.Toast

fun toastShort(text: String): Unit{
    Toast.makeText(ToolsConfig.getApp(), text, Toast.LENGTH_SHORT).show()
}