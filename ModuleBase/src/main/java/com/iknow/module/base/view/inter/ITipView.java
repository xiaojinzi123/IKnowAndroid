package com.iknow.module.base.view.inter;

import androidx.annotation.MainThread;
import androidx.annotation.NonNull;
import androidx.annotation.StringRes;
import com.iknow.module.base.view.Tip;

/**
 * @author : xiaojinzi 30212
 * desc :
 * time : 2018/02/01
 */
@MainThread
public interface ITipView {

    /**
     * 默认提示
     *
     * @param tip 需要提示的对象
     */
    void tip(@NonNull Tip tip);

}
