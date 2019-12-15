package com.iknow.module.help.module.image_preview.view;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;

import com.bumptech.glide.Glide;
import com.iknow.module.base.view.BaseFragment;
import com.iknow.module.help.R;
import com.xiaojinzi.component.anno.FieldAutowiredAnno;

public class ImagePreviewFragment extends BaseFragment {

    public static ImagePreviewFragment newInstance(@NonNull String url) {
        Bundle args = new Bundle();
        args.putString("url", url);
        ImagePreviewFragment fragment = new ImagePreviewFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @FieldAutowiredAnno("url")
    String url;

    ImageView iv;

    @Override
    public View getLayout(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = View.inflate(mContext, R.layout.help_image_preview_frag, null);
        return view;
    }

    @Override
    protected void injectView(View contentView) {
        super.injectView(contentView);
        iv = contentView.findViewById(R.id.iv);
    }

    @Override
    protected void onInit() {
        super.onInit();
        Glide.with(mContext)
                .load(url)
                .into(iv);
    }

}
