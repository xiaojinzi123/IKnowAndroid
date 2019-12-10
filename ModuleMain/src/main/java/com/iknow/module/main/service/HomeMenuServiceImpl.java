package com.iknow.module.main.service;

import androidx.annotation.NonNull;

import com.iknow.module.base.service.main.HomeMenuService;
import com.xiaojinzi.component.anno.ServiceAnno;

import io.reactivex.Observable;
import io.reactivex.subjects.BehaviorSubject;

@ServiceAnno(HomeMenuService.class)
public class HomeMenuServiceImpl implements HomeMenuService {

    private BehaviorSubject<Boolean> menuStateSubject = BehaviorSubject.createDefault(false);

    @NonNull
    @Override
    public Observable<Boolean> subscribeMenuState() {
        return menuStateSubject;
    }

    @Override
    public boolean isMenuClosed() {
        return !menuStateSubject.getValue();
    }

    @Override
    public void openMenu() {
        menuStateSubject.onNext(true);
    }

    @Override
    public void closeMenu() {
        menuStateSubject.onNext(false);
    }

}
