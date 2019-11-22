package com.iknow.module.base.support;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;

public class CompleableObserverAdapter {

    @NonNull
    private final Action mAction;

    @Nullable
    private final Consumer<? super Throwable> mOnError;

    public CompleableObserverAdapter(@NonNull Action action) {
        this.mAction = action;
        this.mOnError = null;
    }

    public CompleableObserverAdapter(@NonNull Action action,
                                     @Nullable Consumer<? super Throwable> mOnError) {
        this.mAction = action;
        this.mOnError = mOnError;
    }

    @NonNull
    public Action getCompleteAction() {
        return mAction;
    }

    @Nullable
    public Consumer<? super Throwable> getErrorConsumer() {
        return mOnError;
    }
}
