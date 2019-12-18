package com.iknow.module.base.service.help

import android.app.Activity
import android.net.Uri
import io.reactivex.Single

interface ImagePickService {

    fun pick(context: Activity, maxCount: Int): Single<List<Uri>>

    fun pick(
        context: Activity, maxCount: Int,
        selectedUris: List<Uri>?
    ): Single<List<Uri>>

}