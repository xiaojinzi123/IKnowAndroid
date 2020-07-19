package com.iknow.lib.tools

import android.content.Context
import android.net.ConnectivityManager

fun Context.isNetworkConnected(): Boolean {
    val manager = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    // 获取NetworkInfo对象
    val networkInfo = manager.activeNetworkInfo
    val connectivityManager: ConnectivityManager = getSystemService(
        ConnectivityManager::class.java
    )
    //判断NetworkInfo对象是否为空
    return null != networkInfo && networkInfo.isAvailable
}