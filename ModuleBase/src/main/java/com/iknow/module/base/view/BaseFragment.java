package com.iknow.module.base.view;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.CallSuper;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.ViewModelProviders;

import com.iknow.module.base.view.inter.BaseViewImpl;
import com.iknow.module.base.view.inter.IBaseView;
import com.iknow.module.base.vm.BaseViewModel;
import com.xiaojinzi.component.Component;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

/**
 * desc: 基础的基类 Fragment
 * auth: 32052
 * time: 2019/4/28
 */
public abstract class BaseFragment<VM extends BaseViewModel> extends Fragment {

    /**
     * 标记的tag,Fragment的名字
     */
    protected String tag;

    /**
     * RxJava {@link io.reactivex.disposables.Disposable} 的一个容器,当界面销毁
     * 这个容器中的所有都会被 {@link Disposable#dispose()}
     */
    protected CompositeDisposable disposables = new CompositeDisposable();

    /**
     * 上下文,其实使用的过程中,不可能为空
     * 除非你在销毁了或者没初始化之前就使用了
     */
    @Nullable
    protected FragmentActivity mContext;

    @Nullable
    protected Fragment mFragment;

    /**
     * 缓存当前的Fragment的视图
     */
    private View mContentView = null;

    /**
     * IBaseView 的实现类
     */
    @NonNull
    protected IBaseView mView;

    /**
     * 可能为空,具体是界面自己决定
     * 这个界面使用的 {@link BaseViewModel}
     */
    protected VM mViewModel;

    public BaseFragment() {
        tag = this.getClass().getSimpleName();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mFragment = this;
        mContext = getActivity();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        if (mContentView == null) {
            mView = onCreateBaseView();
            mContentView = getLayout(inflater, container, savedInstanceState);
            injectView(mContentView);
            mViewModel = onCreateViewModel();
            onInit();
        }
        return mContentView;
    }

    @Nullable
    protected Class<? extends VM> viewModelClass() {
        return null;
    }

    /**
     * 创建 ViewModel
     * 默认的实现是空的
     *
     * @return
     */
    @Nullable
    protected VM onCreateViewModel() {
        Class<? extends VM> viewModelClass = viewModelClass();
        if (viewModelClass != null) {
            return ViewModelProviders.of(this).get(viewModelClass);
        }
        return null;
    }

    /**
     * 初始化
     */
    @CallSuper
    protected void onInit() {
        Component.inject(this);
        if (mViewModel != null) {
            disposables.add(mViewModel.subscribeLoadingObservable()
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Consumer<Boolean>() {
                        @Override
                        public void accept(Boolean isLoading) throws Exception {
                            if (isLoading && mView != null) {
                                mView.showProgress();
                            } else {
                                mView.closeProgress();
                            }
                        }
                    })
            );
            disposables.add(mViewModel.subscribeTipObservable()
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Consumer<Tip>() {
                        @Override
                        public void accept(Tip tip) throws Exception {
                            if (mView != null) {
                                mView.tip(tip);
                            }
                        }
                    })
            );
        }
    }

    /**
     * 注入视图
     */
    protected void injectView(View contentView) {
    }

    /**
     * 该方法会在{@link #onCreateView(LayoutInflater, ViewGroup, Bundle)}中调用
     *
     * @return 返回布局
     */
    public abstract View getLayout(LayoutInflater inflater,
                                   ViewGroup container,
                                   Bundle savedInstanceState);

    /**
     * 默认实现 View层接口的实现类
     */
    @NonNull
    protected IBaseView onCreateBaseView() {
        return new BaseViewImpl(mContext);
    }

    /**
     * 订阅方法
     */
    protected final <E> void subscibe(Observable<E> observable, Consumer<E> consumer) {
        disposables.add(observable
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(consumer));
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (disposables != null) {
            disposables.dispose();
        }
    }
}
