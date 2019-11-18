package com.iknow.module.base.vm;

import android.app.Application;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import com.iknow.module.base.view.Tip;
import io.reactivex.Observable;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.subjects.BehaviorSubject;
import io.reactivex.subjects.Subject;

/**
 * The Base ViewModel
 *
 * @author xiaojinzi
 */
public class BaseViewModel extends AndroidViewModel {

    public BaseViewModel(@NonNull Application application) {
        super(application);
    }

    protected CompositeDisposable disposables = new CompositeDisposable();

    /**
     * 表示 loading 状态,false 表示关闭 loading 弹框,true 表示打开
     * 实际是一个 {@link Subject}
     */
    @NonNull
    protected final Subject<Boolean> loadingSubject = BehaviorSubject.createDefault(false);

    /**
     * 提示信息
     * 实际是一个 {@link Subject}
     */
    @NonNull
    protected final Subject<Tip> tipSubject = BehaviorSubject.create();

    @NonNull
    public Observable<Boolean> loadingObservable() {
        return loadingSubject;
    }

    @NonNull
    public Observable<Tip> tipObservable() {
        return tipSubject;
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        disposables.dispose();
    }

}
