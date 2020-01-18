package com.iknow.lib.tools;

import android.widget.Toast;
import androidx.annotation.Nullable;

/**
 * time   : 2019/04/10
 *
 * @author : xiaojinzi 30212
 */
public class ToastUtil {

    public static void makeText(@Nullable String msg) {
        if (msg == null || "".equals(msg)) {
            return;
        }
        Toast.makeText(ToolsConfig.getApp(), msg, Toast.LENGTH_SHORT).show();
    }

}
