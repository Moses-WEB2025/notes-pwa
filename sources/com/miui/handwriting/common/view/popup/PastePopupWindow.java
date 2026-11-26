package com.miui.handwriting.common.view.popup;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.View;
import android.widget.PopupWindow;
import android.widget.TextView;
import com.miui.creation.common.R;
import com.miui.libhandwritingpage.utils.Utils;
import miuix.animation.Folme;
import miuix.animation.ITouchStyle;
import miuix.animation.base.AnimConfig;

/* JADX INFO: loaded from: classes3.dex */
public class PastePopupWindow extends PopupWindow {
    private float downX;
    private float downY;
    private boolean isContentEmpty;
    TextView mPaste;
    private OnPasteClickListener pasteClickListener;
    private View view;

    public interface OnPasteClickListener {
        void onPasteClick(float f, float f2, boolean z);
    }

    public PastePopupWindow(Context context) {
        super(context);
        this.downX = 0.0f;
        this.downY = 0.0f;
        this.isContentEmpty = false;
        View viewInflate = View.inflate(context, R.layout.creation_paste_pop_menu, null);
        this.view = viewInflate;
        setContentView(viewInflate);
        setOutsideTouchable(true);
        setBackgroundDrawable(new ColorDrawable(0));
        initView();
    }

    private void initView() {
        TextView textView = (TextView) this.view.findViewById(R.id.creation_select_paste);
        this.mPaste = textView;
        textView.setOnClickListener(new View.OnClickListener() { // from class: com.miui.handwriting.common.view.popup.PastePopupWindow$$ExternalSyntheticLambda0
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f$0.m776x33024b3b(view);
            }
        });
        Folme.useAt(this.mPaste).touch().setScale(1.0f, new ITouchStyle.TouchType[0]).setTintMode(1).handleTouchOf(this.mPaste, new AnimConfig[0]);
    }

    /* JADX INFO: renamed from: lambda$initView$0$com-miui-handwriting-common-view-popup-PastePopupWindow, reason: not valid java name */
    /* synthetic */ void m776x33024b3b(View view) {
        if (Utils.isFastClick(R.id.creation_select_paste)) {
            return;
        }
        dismiss();
        OnPasteClickListener onPasteClickListener = this.pasteClickListener;
        if (onPasteClickListener != null) {
            onPasteClickListener.onPasteClick(this.downX, this.downY, this.isContentEmpty);
        }
    }

    public void show(View view, float f, float f2, boolean z) {
        this.downX = f;
        this.downY = f2;
        this.isContentEmpty = z;
        int[] iArr = new int[2];
        view.getLocationInWindow(iArr);
        View contentView = getContentView();
        contentView.measure(0, 0);
        showAtLocation(view, 0, (int) ((iArr[0] + f) - (contentView.getMeasuredWidth() / 2)), (int) ((iArr[1] + f2) - contentView.getMeasuredHeight()));
    }

    public void setOnPasteCallback(OnPasteClickListener onPasteClickListener) {
        this.pasteClickListener = onPasteClickListener;
    }
}
