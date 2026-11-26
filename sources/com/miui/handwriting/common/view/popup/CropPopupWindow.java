package com.miui.handwriting.common.view.popup;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.View;
import android.widget.PopupWindow;
import android.widget.TextView;
import com.miui.creation.common.R;
import miuix.animation.Folme;
import miuix.animation.ITouchStyle;
import miuix.animation.base.AnimConfig;

/* JADX INFO: loaded from: classes3.dex */
public class CropPopupWindow extends PopupWindow {
    private OnCropClickListener cropClickListener;
    TextView mCancel;
    TextView mFinish;
    private final View view;

    public interface OnCropClickListener {
        void onCancelClick();

        void onFinishClick();
    }

    public CropPopupWindow(Context context) {
        super(context);
        View viewInflate = View.inflate(context, R.layout.creation_crop_pop_menu, null);
        this.view = viewInflate;
        setContentView(viewInflate);
        setOutsideTouchable(false);
        setBackgroundDrawable(new ColorDrawable(0));
        initView();
    }

    private void initView() {
        this.mFinish = (TextView) this.view.findViewById(R.id.creation_crop_select_finish);
        this.mCancel = (TextView) this.view.findViewById(R.id.creation_crop_select_cancel);
        this.mFinish.setOnClickListener(new View.OnClickListener() { // from class: com.miui.handwriting.common.view.popup.CropPopupWindow$$ExternalSyntheticLambda0
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f$0.m756x684e6cba(view);
            }
        });
        this.mCancel.setOnClickListener(new View.OnClickListener() { // from class: com.miui.handwriting.common.view.popup.CropPopupWindow$$ExternalSyntheticLambda1
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f$0.m757x6984bf99(view);
            }
        });
        Folme.useAt(this.mFinish).touch().setScale(1.0f, new ITouchStyle.TouchType[0]).setTintMode(1).handleTouchOf(this.mFinish, new AnimConfig[0]);
        Folme.useAt(this.mCancel).touch().setScale(1.0f, new ITouchStyle.TouchType[0]).setTintMode(1).handleTouchOf(this.mCancel, new AnimConfig[0]);
    }

    /* JADX INFO: renamed from: lambda$initView$0$com-miui-handwriting-common-view-popup-CropPopupWindow, reason: not valid java name */
    /* synthetic */ void m756x684e6cba(View view) {
        OnCropClickListener onCropClickListener = this.cropClickListener;
        if (onCropClickListener != null) {
            onCropClickListener.onFinishClick();
        }
    }

    /* JADX INFO: renamed from: lambda$initView$1$com-miui-handwriting-common-view-popup-CropPopupWindow, reason: not valid java name */
    /* synthetic */ void m757x6984bf99(View view) {
        OnCropClickListener onCropClickListener = this.cropClickListener;
        if (onCropClickListener != null) {
            onCropClickListener.onCancelClick();
        }
    }

    public void show(View view, int i, int i2) {
        showAtLocation(view, 0, i, i2);
    }

    public void setOnCropCallback(OnCropClickListener onCropClickListener) {
        this.cropClickListener = onCropClickListener;
    }
}
