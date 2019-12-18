package com.iknow.module.help.service

import android.app.Activity
import android.content.Context
import android.net.Uri
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.iknow.module.base.service.help.ImagePickService
import com.sharry.lib.album.ILoaderEngine
import com.sharry.lib.album.MediaMeta
import com.sharry.lib.album.PickerConfig
import com.sharry.lib.album.PickerManager
import com.xiaojinzi.component.anno.ServiceAnno
import io.reactivex.Single
import java.util.stream.Collectors

@ServiceAnno(ImagePickService::class)
open class ImagePickServiceImpl : ImagePickService {

    override fun pick(context: Activity, maxCount: Int): Single<List<Uri>> {
        return pick(context, maxCount, null)
    }

    override fun pick(
        context: Activity, maxCount: Int,
        selectedUris: List<Uri>?
    ): Single<List<Uri>> {
        var tempSelectedUris = selectedUris
        if (tempSelectedUris == null) {
            tempSelectedUris = ArrayList()
        }
        return Single.create { emitter ->
            if (emitter.isDisposed) {
                return@create
            }
            val list = tempSelectedUris
                .stream()
                .map { uri -> MediaMeta.createPicture(uri) }
                .collect(Collectors.toList())
            val arrayList = kotlin.collections.ArrayList(list)
            PickerManager.with(context)
                // 注入配置
                .setPickerConfig(
                    PickerConfig.Builder()
                        // 阈值
                        .setThreshold(maxCount)
                        // 每行展示的数量
                        .setSpanCount(3)
                        .setPickedPictures(arrayList)
                        // 是否选择 GIF 图
                        .isPickGif(false)
                        // 是否选择视频
                        .isPickVideo(false)
                        .build()
                )
                // 加载框架注入
                .setLoaderEngine(
                    object : ILoaderEngine {
                        override fun loadPicture(
                            context: Context,
                            mediaMeta: MediaMeta,
                            imageView: ImageView
                        ) {
                            // Android 10 以后, 需要使用 URI 进行加载
                            Glide.with(context).asBitmap().load(mediaMeta.contentUri)
                                .into(imageView)
                        }

                        override fun loadGif(
                            context: Context,
                            mediaMeta: MediaMeta,
                            imageView: ImageView
                        ) {
                            // Android 10 以后, 需要使用 URI 进行加载
                            Glide.with(context).asGif().load(mediaMeta.contentUri).into(imageView)
                        }

                        override fun loadVideoThumbnails(
                            context: Context,
                            mediaMeta: MediaMeta,
                            imageView: ImageView
                        ) {
                            // Android 10 以后, 需要使用 URI 进行加载
                            Glide.with(context).asBitmap().load(mediaMeta.contentUri)
                                .into(imageView)
                        }
                    }
                )
                .start {
                    try {
                        val resultList = it
                            .stream()
                            .map { it.contentUri }
                            .collect(Collectors.toList())
                        if (emitter.isDisposed) {
                            return@start
                        }
                        emitter.onSuccess(resultList)
                    } catch (e: Exception) {
                        if (emitter.isDisposed) {
                            return@start
                        }
                        emitter.onError(e)
                    }

                }
        }
    }


}