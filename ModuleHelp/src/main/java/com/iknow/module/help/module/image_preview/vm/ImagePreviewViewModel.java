package com.iknow.module.help.module.image_preview.vm;

import android.app.Application;

import androidx.annotation.NonNull;

import com.iknow.module.base.support.HotObservableAnno;
import com.iknow.module.base.vm.BaseViewModel;

import io.reactivex.Observable;
import io.reactivex.subjects.BehaviorSubject;

public class ImagePreviewViewModel extends BaseViewModel {

    private BehaviorSubject<Integer> imageIndexSubject = BehaviorSubject.createDefault(0);

    public ImagePreviewViewModel(@NonNull Application application) {
        super(application);
    }

    @NonNull
    @HotObservableAnno
    public Observable<Integer> subscribeImageIndexObservable() {
        return imageIndexSubject;
    }

    public void onPageSelect(int position) {
        imageIndexSubject.onNext(position);
    }

}
