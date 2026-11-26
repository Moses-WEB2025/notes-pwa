package com.miui.handwriting.common.view.popup;

import android.content.Context;
import android.view.View;
import android.widget.TextView;
import com.miui.creation.common.R;
import miuix.animation.Folme;
import miuix.animation.ITouchStyle;
import miuix.animation.base.AnimConfig;

/* JADX INFO: loaded from: classes3.dex */
public class SelectPopupWindowInner extends SelectPopupWindow {
    private View mSelectBack;

    public SelectPopupWindowInner(Context context, SelectPopupWindow.OnActionClickListener onActionClickListener, int i) {
        super(context, onActionClickListener, i);
    }

    @Override // com.miui.handwriting.common.view.popup.SelectPopupWindow
    protected void initView() {
        this.mSelectCopyPaste = (TextView) this.view.findViewById(R.id.creation_select_copy_paste);
        this.mSelectCopyAsText = (TextView) this.view.findViewById(R.id.creation_select_copy_as_text);
        this.mSelectRecognizeAsText = (TextView) this.view.findViewById(R.id.creation_select_recognize_as_text);
        this.mSelectBack = this.view.findViewById(R.id.creation_select_back);
        this.mSelectCopyPaste.setOnClickListener(new View.OnClickListener() { // from class: com.miui.handwriting.common.view.popup.SelectPopupWindowInner$$ExternalSyntheticLambda0
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f$0.m791xd3da7ba8(view);
            }
        });
        this.mSelectCopyAsText.setOnClickListener(new View.OnClickListener() { // from class: com.miui.handwriting.common.view.popup.SelectPopupWindowInner$$ExternalSyntheticLambda1
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f$0.m792xc769ffe9(view);
            }
        });
        this.mSelectRecognizeAsText.setOnClickListener(new View.OnClickListener() { // from class: com.miui.handwriting.common.view.popup.SelectPopupWindowInner$$ExternalSyntheticLambda2
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f$0.m793xbaf9842a(view);
            }
        });
        this.mSelectBack.setOnClickListener(new View.OnClickListener() { // from class: com.miui.handwriting.common.view.popup.SelectPopupWindowInner$$ExternalSyntheticLambda3
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f$0.m794xae89086b(view);
            }
        });
        Folme.useAt(this.mSelectCopyPaste).touch().setScale(1.0f, new ITouchStyle.TouchType[0]).setTintMode(1).handleTouchOf(this.mSelectCopyPaste, new AnimConfig[0]);
        Folme.useAt(this.mSelectCopyAsText).touch().setScale(1.0f, new ITouchStyle.TouchType[0]).setTintMode(1).handleTouchOf(this.mSelectCopyAsText, new AnimConfig[0]);
        Folme.useAt(this.mSelectRecognizeAsText).touch().setScale(1.0f, new ITouchStyle.TouchType[0]).setTintMode(1).handleTouchOf(this.mSelectRecognizeAsText, new AnimConfig[0]);
        Folme.useAt(this.mSelectBack).touch().setScale(1.0f, new ITouchStyle.TouchType[0]).setTintMode(1).handleTouchOf(this.mSelectBack, new AnimConfig[0]);
    }

    /* JADX INFO: renamed from: lambda$initView$0$com-miui-handwriting-common-view-popup-SelectPopupWindowInner, reason: not valid java name */
    /* synthetic */ void m791xd3da7ba8(View view) {
        this.actionClickListener.onActionClick(8192);
    }

    /* JADX INFO: renamed from: lambda$initView$1$com-miui-handwriting-common-view-popup-SelectPopupWindowInner, reason: not valid java name */
    /* synthetic */ void m792xc769ffe9(View view) {
        this.actionClickListener.onActionClick(65536);
    }

    /* JADX INFO: renamed from: lambda$initView$2$com-miui-handwriting-common-view-popup-SelectPopupWindowInner, reason: not valid java name */
    /* synthetic */ void m793xbaf9842a(View view) {
        this.actionClickListener.onActionClick(131072);
    }

    /* JADX INFO: renamed from: lambda$initView$3$com-miui-handwriting-common-view-popup-SelectPopupWindowInner, reason: not valid java name */
    /* synthetic */ void m794xae89086b(View view) {
        this.actionClickListener.onActionClick(262144);
    }
}
