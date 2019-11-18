package com.iknow.lib.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.RelativeLayout;

public class BannerWidget extends RelativeLayout {

    public BannerWidget(Context context) {
        this(context, null);
    }

    public BannerWidget(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public BannerWidget(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        View.inflate(context, R.layout.widget_banner, this);
    }

}
