package com.miui.handwriting.common.view.handview.pop;

import android.content.Context;
import android.graphics.RectF;
import android.graphics.drawable.ColorDrawable;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import com.miui.creation.common.R;
import com.miui.libhandwritingpage.utils.Utils;

/* JADX INFO: loaded from: classes3.dex */
public class AICreationPopupWindow extends PopupWindow {
    private OnAICreationClickListener aiCreationClickListener;
    LinearLayout mAICreation;
    LinearLayout mPartionCreation;
    private View view;

    public interface OnAICreationClickListener {
        void onAICreationClick();

        void onJubuCreationClick();
    }

    public AICreationPopupWindow(Context context) {
        super(context);
        View viewInflate = View.inflate(context, R.layout.ai_creation_pop_menu, null);
        this.view = viewInflate;
        setContentView(viewInflate);
        setOutsideTouchable(true);
        setBackgroundDrawable(new ColorDrawable(0));
        initView();
    }

    private void initView() {
        this.mAICreation = (LinearLayout) this.view.findViewById(R.id.ai_creation_select);
        LinearLayout linearLayout = (LinearLayout) this.view.findViewById(R.id.ai_creation_select_jubu);
        this.mPartionCreation = linearLayout;
        linearLayout.setOnClickListener(new View.OnClickListener() { // from class: com.miui.handwriting.common.view.handview.pop.AICreationPopupWindow.1
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                if (Utils.isFastClick(R.id.ai_creation_select_jubu)) {
                    return;
                }
                AICreationPopupWindow.this.dismiss();
                if (AICreationPopupWindow.this.aiCreationClickListener != null) {
                    AICreationPopupWindow.this.aiCreationClickListener.onJubuCreationClick();
                }
            }
        });
        this.mAICreation.setOnClickListener(new View.OnClickListener() { // from class: com.miui.handwriting.common.view.handview.pop.AICreationPopupWindow$$ExternalSyntheticLambda0
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f$0.m749x8f88de11(view);
            }
        });
    }

    /* JADX INFO: renamed from: lambda$initView$0$com-miui-handwriting-common-view-handview-pop-AICreationPopupWindow, reason: not valid java name */
    /* synthetic */ void m749x8f88de11(View view) {
        if (Utils.isFastClick(R.id.ai_creation_select)) {
            return;
        }
        dismiss();
        OnAICreationClickListener onAICreationClickListener = this.aiCreationClickListener;
        if (onAICreationClickListener != null) {
            onAICreationClickListener.onAICreationClick();
        }
    }

    public void show(View view, RectF rectF, RectF rectF2) {
        int[] iArr = new int[2];
        view.getLocationInWindow(iArr);
        View contentView = getContentView();
        contentView.measure(0, 0);
        showAtLocation(view, 0, ((int) ((iArr[0] + rectF.left) + ((rectF.width() - contentView.getMeasuredWidth()) / 2.0f))) - Math.min(0, (int) rectF2.left), ((int) Math.max((rectF.top + iArr[1]) + ((rectF.height() - contentView.getMeasuredHeight()) / 2.0f), rectF2.top + contentView.getMeasuredHeight())) - Math.min(0, (int) rectF2.top));
    }

    public void setOnAICreationCallback(OnAICreationClickListener onAICreationClickListener) {
        this.aiCreationClickListener = onAICreationClickListener;
    }
}
