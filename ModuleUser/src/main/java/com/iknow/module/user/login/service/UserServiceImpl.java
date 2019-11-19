package com.iknow.module.user.login.service;

import com.iknow.module.base.service.user.UserService;
import com.xiaojinzi.component.anno.ServiceAnno;

@ServiceAnno(UserService.class)
public class UserServiceImpl implements UserService {

    @Override
    public boolean isLogin() {
        return false;
    }

}
