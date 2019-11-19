package com.iknow.module.base.view;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import androidx.annotation.CallSuper;
import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.ViewModelProviders;
import com.iknow.module.base.view.inter.BaseViewImpl;
import com.iknow.module.base.view.inter.IBaseView;
import com.iknow.module.base.vm.BaseViewModel;
import com.xiaojinzi.component.Component;

import io.reactivex.Completable;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;

/**
 * 基础的基类 Activity
 * time   : 2019/04/10
 *
 * @author : xiaojinzi 30212
 */
public abstract class BaseAct<VM extends BaseViewModel> extends AppCompatActivity {

    /**
     * RxJava {@link io.reactivex.disposables.Disposable} 的一个容器,当界面销毁
     * 这个容器中的所有都会被 {@link Disposable#dispose()}
     */
    protected CompositeDisposable disposables = new CompositeDisposable();

    /**
     * 上下文,其实使用的过程中,不可能为空
     * 除非你在销毁了或者没初始化之前就使用了
     */
    protected FragmentActivity mContext;

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

    @Override
    protected final void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = this;
        mView = onCreateBaseView();
        if (getLayoutId() == 0) {
            View view = getLayoutView();
            if (view != null) {
                setContentView(getLayoutView());
            }
        } else {
            setContentView(getLayoutId());
        }
        mViewModel = onCreateViewModel();
        onInit();
    }

    @Nullable
    protected Class<? extends VM> getViewModelClass() {
        return null;
    }

    /**
     * 创建 ViewModel
     * 默认的实现是空的
     */
    @Nullable
    protected VM onCreateViewModel() {
        Class<? extends VM> viewModelClass = getViewModelClass();
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
            subscibeUi(mViewModel.loadingObservable(), isLoading -> {
                if (isLoading && mView != null) {
                    mView.showProgress();
                } else {
                    mView.closeProgress();
                }
            });
            subscibeUi(mViewModel.tipObservable(), tip -> {
                if (mView != null) {
                    mView.tip(tip);
                }
            });
        }
    }

    @Override
    public void setSupportActionBar(@Nullable Toolbar toolbar) {
        super.setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }

    /**
     * 默认点击返回图标是结束 Activity
     *
     * @param item
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                // app icon in action bar clicked; goto parent activity.
                this.finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    /**
     * 获取布局文件的id
     */
    @LayoutRes
    protected int getLayoutId() {
        return 0;
    }

    /**
     * 获取布局文件的id
     */
    protected View getLayoutView() {
        return null;
    }

    /**
     * 实现可以是ButterKnife的注入,上层不定义
     * 控件 id 的注入
     */
    protected void onInjectView() {
    }

    @Override
    public void setContentView(View view) {
        super.setContentView(view);
        onInjectView();
    }

    @Override
    public void setContentView(int layoutResID) {
        super.setContentView(layoutResID);
        onInjectView();
    }

    /**
     * 默认实现 View层接口的实现类
     */
    @NonNull
    protected IBaseView onCreateBaseView() {
        return new BaseViewImpl(mContext);
    }


    protected final <E> void subscibeUi(Observable<E> observable, Consumer<E> consumer) {
        disposables.add(observable
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(consumer));
    }

    protected final <E> void subscibeUi(Completable completable, Action action) {
        disposables.add(completable
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(action));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (disposables != null) {
            disposables.dispose();
        }
        if (mView != null) {
            mView.destroy();
        }
    }
}
