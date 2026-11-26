package com.miui.pc.feature.notes;

import android.content.Context;
import android.graphics.Rect;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.miui.common.tool.UIUtils;
import com.miui.common.view.IPopMenu;
import com.miui.notes.R;
import miuix.animation.Folme;
import miuix.animation.base.AnimConfig;
import miuix.animation.listener.TransitionListener;
import miuix.animation.property.ViewProperty;

/* JADX INFO: loaded from: classes4.dex */
public class PcTitleMoreMenu implements IPopMenu {
    private View mAlarm;
    private View mDelete;
    private View mRootView;
    private View.OnClickListener mItemClickListener = new View.OnClickListener() { // from class: com.miui.pc.feature.notes.PcTitleMoreMenu.1
        @Override // android.view.View.OnClickListener
        public void onClick(View view) {
            int id = view.getId();
            if (id == R.id.delete) {
                if (PcTitleMoreMenu.this.mOnMenuListener != null) {
                    PcTitleMoreMenu.this.mOnMenuListener.onItemSelected(view, 1);
                }
                PcTitleMoreMenu.this.dismiss();
            } else {
                if (id != R.id.set_alarm) {
                    return;
                }
                if (PcTitleMoreMenu.this.mOnMenuListener != null) {
                    PcTitleMoreMenu.this.mOnMenuListener.onItemSelected(view, 0);
                }
                PcTitleMoreMenu.this.dismiss();
            }
        }
    };
    private IPopMenu.OnMenuListener mOnMenuListener = null;
    private TransitionListener mShowListener = new TransitionListener() { // from class: com.miui.pc.feature.notes.PcTitleMoreMenu.2
        @Override // miuix.animation.listener.TransitionListener
        public void onComplete(Object obj) {
            super.onComplete(obj);
            if (PcTitleMoreMenu.this.mOnMenuListener != null) {
                PcTitleMoreMenu.this.mOnMenuListener.onShow();
            }
        }
    };
    private TransitionListener mDismissListener = new TransitionListener() { // from class: com.miui.pc.feature.notes.PcTitleMoreMenu.3
        @Override // miuix.animation.listener.TransitionListener
        public void onComplete(Object obj) {
            ViewGroup viewGroup;
            super.onComplete(obj);
            if (PcTitleMoreMenu.this.mRootView != null && (viewGroup = (ViewGroup) PcTitleMoreMenu.this.mRootView.getParent()) != null) {
                viewGroup.removeView(PcTitleMoreMenu.this.mRootView);
            }
            if (PcTitleMoreMenu.this.mOnMenuListener != null) {
                PcTitleMoreMenu.this.mOnMenuListener.onDismiss();
            }
        }
    };

    public PcTitleMoreMenu(Context context) {
        this.mRootView = null;
        View viewInflate = LayoutInflater.from(context).inflate(R.layout.pc_note_more_menu, (ViewGroup) null, false);
        this.mRootView = viewInflate;
        viewInflate.setOnClickListener(new View.OnClickListener() { // from class: com.miui.pc.feature.notes.PcTitleMoreMenu.4
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                PcTitleMoreMenu.this.dismiss();
            }
        });
        initView(this.mRootView);
    }

    private void initView(View view) {
        View viewFindViewById = view.findViewById(R.id.set_alarm);
        this.mAlarm = viewFindViewById;
        viewFindViewById.setOnClickListener(this.mItemClickListener);
        View viewFindViewById2 = view.findViewById(R.id.delete);
        this.mDelete = viewFindViewById2;
        viewFindViewById2.setOnClickListener(this.mItemClickListener);
    }

    @Override // com.miui.common.view.IPopMenu
    public void show(final View view, int[] iArr) {
        ViewGroup viewGroup = (ViewGroup) view.getRootView();
        this.mRootView.setAlpha(0.0f);
        viewGroup.addView(this.mRootView, new ViewGroup.LayoutParams(-1, -1));
        this.mRootView.post(new Runnable() { // from class: com.miui.pc.feature.notes.PcTitleMoreMenu.5
            @Override // java.lang.Runnable
            public void run() {
                Rect rect = new Rect();
                view.getGlobalVisibleRect(rect);
                int iDip2px = rect.left;
                int width = PcTitleMoreMenu.this.mRootView.findViewById(R.id.content).getWidth();
                int realWidth = UIUtils.getRealWidth(PcTitleMoreMenu.this.mRootView.getContext());
                int i = width + iDip2px;
                if (i > realWidth) {
                    iDip2px = (iDip2px - (i - realWidth)) - UIUtils.dip2px(PcTitleMoreMenu.this.mRootView.getContext(), 10.0f);
                }
                PcTitleMoreMenu.this.mRootView.findViewById(R.id.content).setX(iDip2px);
                PcTitleMoreMenu.this.mRootView.findViewById(R.id.content).setY(rect.bottom + UIUtils.dip2px(PcTitleMoreMenu.this.mRootView.getContext(), 20.0f));
                Folme.useAt(PcTitleMoreMenu.this.mRootView).state().to(ViewProperty.ALPHA, Float.valueOf(1.0f), new AnimConfig().addListeners(PcTitleMoreMenu.this.mShowListener));
            }
        });
    }

    @Override // com.miui.common.view.IPopMenu
    public void dismiss() {
        Folme.useAt(this.mRootView).state().to(ViewProperty.ALPHA, Float.valueOf(0.0f), new AnimConfig().addListeners(this.mDismissListener));
    }

    @Override // com.miui.common.view.IPopMenu
    public void setOnMenuListener(IPopMenu.OnMenuListener onMenuListener) {
        this.mOnMenuListener = onMenuListener;
    }
}
