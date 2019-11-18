package com.iknow.module.base.view;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.StringRes;

/**
 * 让 View 层提示的实体对象
 */
public class Tip {

    /**
     * 信息提示的类型
     */
    public enum TipEnum {
        // 提示的类型：
        // Normal：普通的提示
        // Error：错误的类型
        // MsgBox 弹框的类型
        Normal, Error, MsgBox
    }

    @StringRes
    private int mTipRes;
    private String mTip;

    private final TipEnum mTipEnum;

    private Tip(@Nullable TipEnum tipEnum, @Nullable @StringRes int tipRes, @Nullable String tip) {
        mTipRes = tipRes;
        mTip = tip;
        mTipEnum = tipEnum;
    }

    public int getTipRes() {
        return mTipRes;
    }

    public String getTip() {
        return mTip;
    }

    public TipEnum getTipEnum() {
        return mTipEnum;
    }

    public static Tip normal(@NonNull String tip) {
        return new Tip(TipEnum.Normal, 0, tip);
    }

    public static Tip normal(@StringRes int tipRes) {
        return new Tip(TipEnum.Normal, tipRes, null);
    }

    public static Tip error(@NonNull String tip) {
        return new Tip(TipEnum.Error, 0, tip);
    }

    public static Tip error(@StringRes int tipRes) {
        return new Tip(TipEnum.Error, tipRes, null);
    }

    public static Tip msgbox(@NonNull String tip) {
        return new Tip(TipEnum.MsgBox, 0, tip);
    }

    public static Tip msgbox(@StringRes int tipRes) {
        return new Tip(TipEnum.MsgBox, tipRes, null);
    }

}
