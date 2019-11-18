package com.iknow.module.base.view.inter;

import androidx.annotation.MainThread;

/**
 * desc :
 * time : 2018/02/01
 *
 * @author : xiaojinzi 30212
 */
@MainThread
public interface IProgressView {

    /**
     * 弹出加载框,不可取消,因为可取消的那就不要弹出呀
     */
    @MainThread
    void showProgress();

    /**
     * 关闭对话框
     */
    @MainThread
    void closeProgress();

}
