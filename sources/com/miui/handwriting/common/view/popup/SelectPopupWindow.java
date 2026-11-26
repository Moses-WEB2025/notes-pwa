package com.miui.handwriting.common.view.popup;

import android.content.Context;
import android.content.res.Configuration;
import android.graphics.drawable.ColorDrawable;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import com.miui.creation.common.R;
import miuix.animation.Folme;
import miuix.animation.ITouchStyle;
import miuix.animation.base.AnimConfig;

/* JADX INFO: loaded from: classes3.dex */
public class SelectPopupWindow extends PopupWindow {
    protected OnActionClickListener actionClickListener;
    protected Context mContext;
    protected LinearLayout mSelectCardView;
    protected TextView mSelectCopy;
    protected TextView mSelectCopyAsText;
    protected TextView mSelectCopyPaste;
    protected TextView mSelectCrop;
    protected TextView mSelectCut;
    protected TextView mSelectDelete;
    protected ImageView mSelectMore;
    protected TextView mSelectRecognizeAsText;
    protected TextView mSelectSave;
    protected View view;

    public interface OnActionClickListener {
        void onActionClick(int i);
    }

    public void showSelectResize(boolean z) {
    }

    public SelectPopupWindow(Context context, OnActionClickListener onActionClickListener, int i) {
        super(context);
        this.mContext = context;
        this.actionClickListener = onActionClickListener;
        this.view = View.inflate(context, i, null);
        setAnimationStyle(R.style.window_fade_anim);
        setContentView(this.view);
        setOutsideTouchable(true);
        setBackgroundDrawable(new ColorDrawable(0));
        initView();
    }

    protected void initView() {
        this.mSelectSave = (TextView) this.view.findViewById(R.id.creation_select_save);
        this.mSelectDelete = (TextView) this.view.findViewById(R.id.creation_select_delete);
        this.mSelectCut = (TextView) this.view.findViewById(R.id.creation_select_cut);
        this.mSelectCopy = (TextView) this.view.findViewById(R.id.creation_select_copy);
        this.mSelectCrop = (TextView) this.view.findViewById(R.id.creation_select_crop);
        this.mSelectCopyPaste = (TextView) this.view.findViewById(R.id.creation_select_copy_paste);
        this.mSelectCopyAsText = (TextView) this.view.findViewById(R.id.creation_select_copy_as_text);
        this.mSelectRecognizeAsText = (TextView) this.view.findViewById(R.id.creation_select_recognize_as_text);
        this.mSelectCardView = (LinearLayout) this.view.findViewById(R.id.creation_select_card_view);
        this.mSelectMore = (ImageView) this.view.findViewById(R.id.creation_select_more);
        this.mSelectCut.setOnClickListener(new View.OnClickListener() { // from class: com.miui.handwriting.common.view.popup.SelectPopupWindow$$ExternalSyntheticLambda0
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f$0.m783x5e19c34e(view);
            }
        });
        this.mSelectSave.setOnClickListener(new View.OnClickListener() { // from class: com.miui.handwriting.common.view.popup.SelectPopupWindow.1
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                SelectPopupWindow.this.actionClickListener.onActionClick(524288);
            }
        });
        this.mSelectCopy.setOnClickListener(new View.OnClickListener() { // from class: com.miui.handwriting.common.view.popup.SelectPopupWindow$$ExternalSyntheticLambda1
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f$0.m784xeb06da6d(view);
            }
        });
        this.mSelectCopyPaste.setOnClickListener(new View.OnClickListener() { // from class: com.miui.handwriting.common.view.popup.SelectPopupWindow$$ExternalSyntheticLambda2
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f$0.m785x77f3f18c(view);
            }
        });
        this.mSelectCrop.setOnClickListener(new View.OnClickListener() { // from class: com.miui.handwriting.common.view.popup.SelectPopupWindow$$ExternalSyntheticLambda3
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f$0.m786x4e108ab(view);
            }
        });
        this.mSelectCopyAsText.setOnClickListener(new View.OnClickListener() { // from class: com.miui.handwriting.common.view.popup.SelectPopupWindow$$ExternalSyntheticLambda4
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f$0.m787x91ce1fca(view);
            }
        });
        this.mSelectRecognizeAsText.setOnClickListener(new View.OnClickListener() { // from class: com.miui.handwriting.common.view.popup.SelectPopupWindow$$ExternalSyntheticLambda5
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f$0.m788x1ebb36e9(view);
            }
        });
        this.mSelectDelete.setOnClickListener(new View.OnClickListener() { // from class: com.miui.handwriting.common.view.popup.SelectPopupWindow$$ExternalSyntheticLambda6
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f$0.m789xaba84e08(view);
            }
        });
        this.mSelectMore.setOnClickListener(new View.OnClickListener() { // from class: com.miui.handwriting.common.view.popup.SelectPopupWindow$$ExternalSyntheticLambda7
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f$0.m790x38956527(view);
            }
        });
        Folme.useAt(this.mSelectCut).touch().setScale(1.0f, new ITouchStyle.TouchType[0]).setTintMode(1).handleTouchOf(this.mSelectCut, new AnimConfig[0]);
        Folme.useAt(this.mSelectCopy).touch().setScale(1.0f, new ITouchStyle.TouchType[0]).setTintMode(1).handleTouchOf(this.mSelectCopy, new AnimConfig[0]);
        Folme.useAt(this.mSelectCopyPaste).touch().setScale(1.0f, new ITouchStyle.TouchType[0]).setTintMode(1).handleTouchOf(this.mSelectCopyPaste, new AnimConfig[0]);
        Folme.useAt(this.mSelectCrop).touch().setScale(1.0f, new ITouchStyle.TouchType[0]).setTintMode(1).handleTouchOf(this.mSelectCrop, new AnimConfig[0]);
        Folme.useAt(this.mSelectCopyAsText).touch().setScale(1.0f, new ITouchStyle.TouchType[0]).setTintMode(1).handleTouchOf(this.mSelectCopyAsText, new AnimConfig[0]);
        Folme.useAt(this.mSelectRecognizeAsText).touch().setScale(1.0f, new ITouchStyle.TouchType[0]).setTintMode(1).handleTouchOf(this.mSelectRecognizeAsText, new AnimConfig[0]);
        Folme.useAt(this.mSelectDelete).touch().setScale(1.0f, new ITouchStyle.TouchType[0]).setTintMode(1).handleTouchOf(this.mSelectDelete, new AnimConfig[0]);
        Folme.useAt(this.mSelectMore).touch().setScale(1.0f, new ITouchStyle.TouchType[0]).setTintMode(1).handleTouchOf(this.mSelectMore, new AnimConfig[0]);
    }

    /* JADX INFO: renamed from: lambda$initView$0$com-miui-handwriting-common-view-popup-SelectPopupWindow, reason: not valid java name */
    /* synthetic */ void m783x5e19c34e(View view) {
        this.actionClickListener.onActionClick(16384);
    }

    /* JADX INFO: renamed from: lambda$initView$1$com-miui-handwriting-common-view-popup-SelectPopupWindow, reason: not valid java name */
    /* synthetic */ void m784xeb06da6d(View view) {
        this.actionClickListener.onActionClick(4096);
    }

    /* JADX INFO: renamed from: lambda$initView$2$com-miui-handwriting-common-view-popup-SelectPopupWindow, reason: not valid java name */
    /* synthetic */ void m785x77f3f18c(View view) {
        this.actionClickListener.onActionClick(8192);
    }

    /* JADX INFO: renamed from: lambda$initView$3$com-miui-handwriting-common-view-popup-SelectPopupWindow, reason: not valid java name */
    /* synthetic */ void m786x4e108ab(View view) {
        this.actionClickListener.onActionClick(512);
    }

    /* JADX INFO: renamed from: lambda$initView$4$com-miui-handwriting-common-view-popup-SelectPopupWindow, reason: not valid java name */
    /* synthetic */ void m787x91ce1fca(View view) {
        this.actionClickListener.onActionClick(65536);
    }

    /* JADX INFO: renamed from: lambda$initView$5$com-miui-handwriting-common-view-popup-SelectPopupWindow, reason: not valid java name */
    /* synthetic */ void m788x1ebb36e9(View view) {
        this.actionClickListener.onActionClick(131072);
    }

    /* JADX INFO: renamed from: lambda$initView$6$com-miui-handwriting-common-view-popup-SelectPopupWindow, reason: not valid java name */
    /* synthetic */ void m789xaba84e08(View view) {
        this.actionClickListener.onActionClick(256);
    }

    /* JADX INFO: renamed from: lambda$initView$7$com-miui-handwriting-common-view-popup-SelectPopupWindow, reason: not valid java name */
    /* synthetic */ void m790x38956527(View view) {
        this.actionClickListener.onActionClick(196608);
    }

    public void showSelectCopyOrRecognizeAsText(boolean z) {
        this.mSelectCopyAsText.setVisibility(z ? 0 : 8);
    }

    public void showSelectCrop(boolean z) {
        this.mSelectCrop.setVisibility(z ? 0 : 8);
    }

    public void hideViewInNarrowScreen() {
        this.mSelectCopyPaste.setVisibility(8);
        this.mSelectCopyAsText.setVisibility(8);
        this.mSelectRecognizeAsText.setVisibility(8);
        this.mSelectMore.setVisibility(0);
    }

    public void showSave(boolean z) {
        if (z) {
            this.mSelectSave.setVisibility(0);
        } else {
            this.mSelectSave.setVisibility(8);
        }
    }

    public ImageView getSelectMore() {
        return this.mSelectMore;
    }

    public void onConfigurationChanged(Configuration configuration) {
        initView();
    }
}
