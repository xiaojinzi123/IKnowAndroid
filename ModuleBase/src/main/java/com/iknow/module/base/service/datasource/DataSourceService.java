package com.iknow.module.base.service.datasource;

import androidx.annotation.NonNull;

import com.iknow.lib.beans.ArticleBean;
import com.iknow.lib.beans.ArticleDetailBean;
import com.iknow.lib.beans.BannerBean;
import com.iknow.lib.beans.LoginBean;
import com.iknow.lib.beans.user.UserInfoBean;

import java.util.List;

import io.reactivex.Single;

/**
 * 整体架构中的 DataSource 层
 */
public interface DataSourceService {

    /**
     * 获取用户信息
     *
     * @param userId 用户ID
     */
    @NonNull
    Single<UserInfoBean> getUser(@NonNull Integer userId);

    /**
     * 登陆
     *
     * @param userName 用户名
     * @param password 密码
     */
    @NonNull
    Single<LoginBean> login(@NonNull String userName, @NonNull String password);

    /**
     * 注册
     *
     * @param phoneNum 手机号码
     * @param userName 用户昵称
     * @return 登录成功
     */
    @NonNull
    Single<LoginBean> register(@NonNull String phoneNum, @NonNull String userName);

    /**
     * 获取banner 的数据
     */
    @NonNull
    Single<List<BannerBean>> bannerList();

    /**
     * 获取文章列表
     *
     * @param pageNumber 第几页
     * @param pageSize   每页的数量
     */
    @NonNull
    Single<List<ArticleBean>> articleList(
            int pageNumber, int pageSize
    );

    /**
     * 获取文章详情
     *
     * @param articleId 文章的ID
     */
    @NonNull
    Single<ArticleDetailBean> articleDetail(
            String articleId
    );

}
