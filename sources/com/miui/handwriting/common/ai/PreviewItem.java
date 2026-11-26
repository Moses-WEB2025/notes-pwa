package com.miui.handwriting.common.ai;

import android.app.Activity;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.miui.creation.common.R;
import com.miui.handwriting.common.HandWritingViewApplication;
import com.miui.handwriting.common.tools.constants.Constants;
import com.miui.libhandwritingpage.utils.UIUtils;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import miuix.androidbasewidget.widget.ProgressBar;

/* JADX INFO: loaded from: classes3.dex */
public class PreviewItem extends RecyclerView.ViewHolder {
    private FrameLayout layout;
    private ProgressBar load;
    private PreviewImageView mPreviewImage;
    private TextView textView;

    public PreviewItem(View view, Activity activity) {
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

    public void bind(final String str, boolean z) {
        Drawable drawable = null;
        if (str.equals(Constants.EMPTY_PIC)) {
            this.mPreviewImage.setScaleType(ImageView.ScaleType.CENTER_CROP);
            this.load.setVisibility(0);
            this.textView.setVisibility(0);
            this.mPreviewImage.setImageDir(null);
            this.mPreviewImage.setImageDrawable(HandWritingViewApplication.INSTANCE.getApplication().getDrawable(R.drawable.creation_ai_preview_empty));
        } else if (str.equals(Constants.ERROR_PIC)) {
            this.mPreviewImage.setScaleType(ImageView.ScaleType.CENTER);
            this.mPreviewImage.setImageDir(null);
            this.mPreviewImage.setImageDrawable(HandWritingViewApplication.INSTANCE.getApplication().getDrawable(R.drawable.creation_ai_preview_error));
            this.load.setVisibility(8);
            this.textView.setVisibility(8);
        } else if (this.mPreviewImage.getImageDir() == null || !TextUtils.equals(this.mPreviewImage.getImageDir(), str)) {
            this.mPreviewImage.setScaleType(ImageView.ScaleType.FIT_CENTER);
            new Thread(new Runnable() { // from class: com.miui.handwriting.common.ai.PreviewItem$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.m718lambda$bind$1$commiuihandwritingcommonaiPreviewItem(str);
                }
            }).start();
            this.load.setVisibility(8);
            this.textView.setVisibility(8);
        }
        FrameLayout frameLayout = this.layout;
        if (z && !TextUtils.equals(str, Constants.EMPTY_PIC)) {
            drawable = AppCompatResources.getDrawable(HandWritingViewApplication.INSTANCE.getApplication(), R.drawable.creation_ai_preview_bg);
        }
        frameLayout.setBackground(drawable);
    }

    /* JADX INFO: renamed from: lambda$bind$1$com-miui-handwriting-common-ai-PreviewItem, reason: not valid java name */
    /* synthetic */ void m718lambda$bind$1$commiuihandwritingcommonaiPreviewItem(String str) {
        try {
            HttpURLConnection httpURLConnection = (HttpURLConnection) new URL(str).openConnection();
            httpURLConnection.setDoInput(true);
            httpURLConnection.connect();
            final InputStream inputStream = httpURLConnection.getInputStream();
            new Handler(Looper.getMainLooper()).post(new Runnable() { // from class: com.miui.handwriting.common.ai.PreviewItem$$ExternalSyntheticLambda1
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.m717lambda$bind$0$commiuihandwritingcommonaiPreviewItem(inputStream);
                }
            });
        } catch (IOException unused) {
        }
    }

    /* JADX INFO: renamed from: lambda$bind$0$com-miui-handwriting-common-ai-PreviewItem, reason: not valid java name */
    /* synthetic */ void m717lambda$bind$0$commiuihandwritingcommonaiPreviewItem(InputStream inputStream) {
        Glide.with(this.mPreviewImage).load(BitmapFactory.decodeStream(inputStream)).into(this.mPreviewImage);
    }

    public void setOnClickListener(View.OnClickListener onClickListener) {
        this.itemView.setOnClickListener(onClickListener);
    }

    public String getImageDir() {
        return this.mPreviewImage.getImageDir();
    }
}
