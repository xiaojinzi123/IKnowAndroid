package com.iknow.module.base.support;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import io.reactivex.functions.Consumer;

public class SingleObserverAdapter<T> {

    @NonNull
    private final Consumer<? super T> mOnSuccess;

    @Nullable
    private final Consumer<? super Throwable> mOnError;

    public SingleObserverAdapter(@NonNull Consumer<? super T> mOnSuccess) {
        this.mOnSuccess = mOnSuccess;
        this.mOnError = null;
    }

    public SingleObserverAdapter(@NonNull Consumer<? super T> mOnSuccess,
                                 @Nullable Consumer<? super Throwable> mOnError) {
        this.mOnSuccess = mOnSuccess;
        this.mOnError = mOnError;
    }

    @NonNull
    public Consumer<? super T> getSuccessConsumer() {
        return mOnSuccess;
    }

    @Nullable
    public Consumer<? super Throwable> getErrorConsumer() {
        return mOnError;
    }
}
