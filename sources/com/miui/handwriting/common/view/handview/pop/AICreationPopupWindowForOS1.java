package com.miui.handwriting.common.view.handview.pop;

import android.content.Context;
import android.graphics.RectF;
import android.graphics.drawable.ColorDrawable;
import android.view.View;
import android.widget.PopupWindow;
import android.widget.TextView;
import com.miui.creation.common.R;
import com.miui.libhandwritingpage.utils.Utils;

/* JADX INFO: loaded from: classes3.dex */
public class AICreationPopupWindowForOS1 extends PopupWindow {
    private OnAICreationClickListener aiCreationClickListener;
    TextView mAICreation;
    private View view;

    public interface OnAICreationClickListener {
        void onAICreationClick();
    }

    public AICreationPopupWindowForOS1(Context context) {
        super(context);
        View viewInflate = View.inflate(context, R.layout.ai_creation_pop_menu_for_os1, null);
        this.view = viewInflate;
        setContentView(viewInflate);
        setOutsideTouchable(true);
        setBackgroundDrawable(new ColorDrawable(0));
        initView();
    }

    private void initView() {
        TextView textView = (TextView) this.view.findViewById(R.id.ai_creation_select);
        this.mAICreation = textView;
        textView.setOnClickListener(new View.OnClickListener() { // from class: com.miui.handwriting.common.view.handview.pop.AICreationPopupWindowForOS1$$ExternalSyntheticLambda0
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f$0.m750x71f8bed5(view);
            }
        });
    }

    /* JADX INFO: renamed from: lambda$initView$0$com-miui-handwriting-common-view-handview-pop-AICreationPopupWindowForOS1, reason: not valid java name */
    /* synthetic */ void m750x71f8bed5(View view) {
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
        showAtLocation(view, 0, ((int) ((iArr[0] + rectF.left) + ((rectF.width() - contentView.getMeasuredWidth()) / 2.0f))) - Math.min(0, (int) rectF2.left), (int) Math.max(((rectF.top + iArr[1]) + ((rectF.height() - contentView.getMeasuredHeight()) / 2.0f)) - Math.min(0, (int) rectF2.top), rectF2.top + contentView.getMeasuredHeight()));
    }

    public void setOnAICreationCallback(OnAICreationClickListener onAICreationClickListener) {
        this.aiCreationClickListener = onAICreationClickListener;
    }
}
