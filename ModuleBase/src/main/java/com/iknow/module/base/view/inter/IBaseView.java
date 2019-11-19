package com.iknow.module.base.view.inter;


import androidx.annotation.MainThread;
import androidx.annotation.NonNull;

import com.iknow.module.base.view.Tip;

/**
 * @author : xiaojinzi
 * desc :
 * time : 2017/12/06
 */
@MainThread
public interface IBaseView extends ITipView, IProgressView {

    /**
     * 在View层销毁的时候会调用
     */
    void destroy();

    class IBaseViewProxy implements IBaseView {

        private IBaseView target;

        public IBaseViewProxy(IBaseView target) {
            this.target = target;
        }

        @Override
        public void destroy() {
            target.destroy();
        }

        @Override
        public void showProgress() {
            target.showProgress();
        }

        @Override
        public void closeProgress() {
            target.closeProgress();
        }

        @Override
        public void tip(@NonNull Tip tip) {
            target.tip(tip);
        }
    }

}
