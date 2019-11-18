package com.iknow.lib.tools;

import android.app.Application;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Build;
import androidx.annotation.NonNull;

/**
 * 一个关于Android 资源的工具类
 *
 * @author xiaojinzi <a href="xujin.chen@1hai.cn">Contact me.</a>
 * @version 1.2
 * @since 18/8/13 9:10
 */
public class ResourceUtil {

    private ResourceUtil() {
        /* cannot be instantiated */
        throw new UnsupportedOperationException("cannot be instantiated");
    }

    @NonNull
    public static Application getApp() {
        return ToolsConfig.getApp();
    }

    public static int getColor(int rsd) {
        return getColor(getApp().getResources(), rsd);
    }

    public static int getColor(@NonNull Resources resources, int rsd) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            return resources.getColor(rsd, null);
        } else {
            return resources.getColor(rsd);
        }
    }

    public static Drawable getDrawable(int rsd) {
        return getDrawable(getApp().getResources(), rsd);
    }

    public static Drawable getDrawable(@NonNull Resources resources, int rsd) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            return resources.getDrawable(rsd, null);
        } else {
            return resources.getDrawable(rsd);
        }
    }

    public static String getString(int rsd) {
        return getString(getApp().getResources(), rsd);
    }

    public static String getString(@NonNull Resources resources, int rsd) {
        return resources.getString(rsd);
    }

    public static int getDimen(int rsd) {
        return getDimen(getApp().getResources(), rsd);
    }

    public static int getDimen(@NonNull Resources resources, int rsd) {
        return resources.getDimensionPixelSize(rsd);
    }


}
