package com.iknow.module.base;

/**
 * time   : 2019/03/27
 *
 * @author : xiaojinzi 30212
 */
public class ModuleInfo {

    /**
     * 项目的 Scheme,用于跳转项目中的界面
     */
    public static final String APP_SCHEME = "Router";
    public static final String HTTP_SCHEME = "http";
    public static final String HTTPS_SCHEME = "https";

    /**
     * App 模块
     */
    public static class App {

        /**
         * 名称
         */
        public static final String NAME = "app";

    }

    public static class Welcome {

        /**
         * 名称
         */
        public static final String NAME = "welcome";

    }

    public static class User {

        /**
         * 名称
         */
        public static final String NAME = "user";
        // 登陆界面
        public static final String LOGIN = "login";
        /**
         * 注册界面
         */
        public static final String LOGIN_REGISTER = "login_register";

    }

    public static class Main {

        /**
         * Main 模块的名称
         */
        public static final String NAME = "main";

        /**
         * 主界面
         */
        public static final String HOME = "home";


    }

    public static class Datasource {

        /**
         * Main 模块的名称
         */
        public static final String NAME = "datasource";

    }

    public static class Help {
        public static final String NAME = "help";
        public static final String WEB = "web";
    }


}
