package com.miui.handwriting.common.ai;

import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.recyclerview.widget.RecyclerView;
import com.miui.creation.common.R;
import com.miui.handwriting.common.HandWritingViewApplication;
import com.miui.libhandwritingpage.utils.UIUtils;
import miuix.androidbasewidget.widget.ProgressBar;

/* JADX INFO: loaded from: classes3.dex */
public class PreviewItemForOS1 extends RecyclerView.ViewHolder {
    private FrameLayout layout;
    private ProgressBar load;
    private PreviewImageView mPreviewImage;
    private TextView textView;

    public PreviewItemForOS1(View view, Activity activity) {
        super(view);
        this.layout = (FrameLayout) view.findViewById(R.id.preview_item_root);
        this.load = (ProgressBar) view.findViewById(R.id.pb_load);
        this.mPreviewImage = (PreviewImageView) view.findViewById(R.id.iv_ai_preview);
        this.textView = (TextView) view.findViewById(R.id.tv_load);
        ViewGroup.LayoutParams layoutParams = this.mPreviewImage.getLayoutParams();
        ViewGroup.LayoutParams layoutParams2 = view.getLayoutParams();
        if (UIUtils.isInMultiWindowMode(activity)) {
            layoutParams2.height = (int) HandWritingViewApplication.INSTANCE.getApplication().getResources().getDimension(R.dimen.creation_ai_small_preview_item_size_small_window);
        }
        view.setLayoutParams(layoutParams2);
        this.mPreviewImage.setLayoutParams(layoutParams);
        float dimension = HandWritingViewApplication.INSTANCE.getApplication().getResources().getDimension(R.dimen.creation_ai_preview_radius);
        this.mPreviewImage.setRoundRadius(dimension, dimension, dimension, dimension);
        this.mPreviewImage.useStroke(true, HandWritingViewApplication.INSTANCE.getApplication().getResources().getDimension(R.dimen.creation_ai_preview_small_stroke_size), HandWritingViewApplication.INSTANCE.getApplication().getResources().getColor(R.color.creation_ai_preview_item_stroke));
    }

    public void bind(String str, boolean z) {
        Drawable drawable = null;
        if (str.equals(CreationAiFragmentForOS1.EMPTY_PIC)) {
            this.mPreviewImage.setScaleType(ImageView.ScaleType.CENTER_CROP);
            this.load.setVisibility(0);
            this.textView.setVisibility(0);
            this.mPreviewImage.setImageDir(null);
            this.mPreviewImage.setImageDrawable(HandWritingViewApplication.INSTANCE.getApplication().getDrawable(R.drawable.creation_ai_preview_empty));
        } else if (str.equals(CreationAiFragmentForOS1.ERROR_PIC)) {
            this.mPreviewImage.setScaleType(ImageView.ScaleType.CENTER);
            this.mPreviewImage.setImageDir(null);
            this.mPreviewImage.setImageDrawable(HandWritingViewApplication.INSTANCE.getApplication().getDrawable(R.drawable.creation_ai_preview_error));
            this.load.setVisibility(8);
            this.textView.setVisibility(8);
        } else {
            this.mPreviewImage.setScaleType(ImageView.ScaleType.FIT_CENTER);
            if (this.mPreviewImage.getImageDir() == null || !TextUtils.equals(this.mPreviewImage.getImageDir(), str)) {
                this.mPreviewImage.setBitmapWithBase64(str, System.currentTimeMillis());
            }
            this.load.setVisibility(8);
            this.textView.setVisibility(8);
        }
        FrameLayout frameLayout = this.layout;
        if (z && !TextUtils.equals(str, CreationAiFragmentForOS1.EMPTY_PIC)) {
            drawable = AppCompatResources.getDrawable(HandWritingViewApplication.INSTANCE.getApplication(), R.drawable.creation_ai_preview_bg);
        }
        frameLayout.setBackground(drawable);
    }

    public void setOnClickListener(View.OnClickListener onClickListener) {
        this.itemView.setOnClickListener(onClickListener);
    }

    public String getImageDir() {
        return this.mPreviewImage.getImageDir();
    }
}
