package com.iknow.module.base.view.inter;


import androidx.annotation.MainThread;

/**
 * @author : xiaojinzi
 * desc :
 * time : 2017/12/06
 */
@MainThread
public interface IBaseView extends ITipView,IProgressView {

    /**
     * 在View层销毁的时候会调用
     */
    void destroy();

}
