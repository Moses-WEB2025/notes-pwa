package com.miui.pc.feature.notes;

import android.content.Context;
import android.graphics.Rect;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.miui.common.tool.UIUtils;
import com.miui.common.view.IPopMenu;
import com.miui.notes.R;
import com.miui.notes.base.utils.Logger;
import miuix.animation.Folme;
import miuix.animation.base.AnimConfig;
import miuix.animation.listener.TransitionListener;
import miuix.animation.property.ViewProperty;

/* JADX INFO: loaded from: classes4.dex */
public class PcTextSizeChoiceMenu implements IPopMenu {
    private View mRootView;
    private int mSelectPos = 0;
    private View.OnClickListener mItemClickListener = new View.OnClickListener() { // from class: com.miui.pc.feature.notes.PcTextSizeChoiceMenu.1
        @Override // android.view.View.OnClickListener
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.size_h1 /* 2131363468 */:
                    PcTextSizeChoiceMenu.this.setSelectedItem(1);
                    if (PcTextSizeChoiceMenu.this.mOnMenuListener != null) {
                        PcTextSizeChoiceMenu.this.mOnMenuListener.onItemSelected(view, 1);
                    }
                    PcTextSizeChoiceMenu.this.dismiss();
                    break;
                case R.id.size_h2 /* 2131363469 */:
                    PcTextSizeChoiceMenu.this.setSelectedItem(2);
                    if (PcTextSizeChoiceMenu.this.mOnMenuListener != null) {
                        PcTextSizeChoiceMenu.this.mOnMenuListener.onItemSelected(view, 2);
                    }
                    PcTextSizeChoiceMenu.this.dismiss();
                    break;
                case R.id.size_normal /* 2131363470 */:
                    PcTextSizeChoiceMenu.this.setSelectedItem(0);
                    if (PcTextSizeChoiceMenu.this.mOnMenuListener != null) {
                        PcTextSizeChoiceMenu.this.mOnMenuListener.onItemSelected(view, 0);
                    }
                    PcTextSizeChoiceMenu.this.dismiss();
                    break;
            }
        }
    };
    private IPopMenu.OnMenuListener mOnMenuListener = null;
    private TransitionListener mShowListener = new TransitionListener() { // from class: com.miui.pc.feature.notes.PcTextSizeChoiceMenu.2
        @Override // miuix.animation.listener.TransitionListener
        public void onComplete(Object obj) {
            super.onComplete(obj);
            if (PcTextSizeChoiceMenu.this.mOnMenuListener != null) {
                PcTextSizeChoiceMenu.this.mOnMenuListener.onShow();
            }
        }
    };
    private TransitionListener mDismissListener = new TransitionListener() { // from class: com.miui.pc.feature.notes.PcTextSizeChoiceMenu.3
        @Override // miuix.animation.listener.TransitionListener
        public void onComplete(Object obj) {
            ViewGroup viewGroup;
            super.onComplete(obj);
            if (PcTextSizeChoiceMenu.this.mRootView != null && (viewGroup = (ViewGroup) PcTextSizeChoiceMenu.this.mRootView.getParent()) != null) {
                viewGroup.removeView(PcTextSizeChoiceMenu.this.mRootView);
            }
            if (PcTextSizeChoiceMenu.this.mOnMenuListener != null) {
                PcTextSizeChoiceMenu.this.mOnMenuListener.onDismiss();
            }
        }
    };

    public PcTextSizeChoiceMenu(Context context) {
        this.mRootView = null;
        View viewInflate = LayoutInflater.from(context).inflate(R.layout.pc_note_edit_text_size_menu, (ViewGroup) null, false);
        this.mRootView = viewInflate;
        viewInflate.setOnClickListener(new View.OnClickListener() { // from class: com.miui.pc.feature.notes.PcTextSizeChoiceMenu.4
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                PcTextSizeChoiceMenu.this.dismiss();
            }
        });
        initView(this.mRootView);
    }

    private void initView(View view) {
        view.findViewById(R.id.size_normal).setOnClickListener(this.mItemClickListener);
        view.findViewById(R.id.size_h1).setOnClickListener(this.mItemClickListener);
        view.findViewById(R.id.size_h2).setOnClickListener(this.mItemClickListener);
    }

    public void setSelectedItem(int i) {
        activeItem(this.mSelectPos, false);
        this.mSelectPos = i;
        activeItem(i, true);
    }

    @Override // com.miui.common.view.IPopMenu
    public void setOnMenuListener(IPopMenu.OnMenuListener onMenuListener) {
        this.mOnMenuListener = onMenuListener;
    }

    @Override // com.miui.common.view.IPopMenu
    public void show(final View view, int[] iArr) {
        if (this.mRootView.getParent() != null) {
            Logger.INSTANCE.w("PadTextSizeChoiceMenu", "show mRootView has parent, cannot add to new parent");
            return;
        }
        ViewGroup viewGroup = (ViewGroup) view.getRootView();
        this.mRootView.setAlpha(0.0f);
        viewGroup.addView(this.mRootView, new ViewGroup.LayoutParams(-1, -1));
        this.mRootView.post(new Runnable() { // from class: com.miui.pc.feature.notes.PcTextSizeChoiceMenu.5
            @Override // java.lang.Runnable
            public void run() {
                Rect rect = new Rect();
                view.getGlobalVisibleRect(rect);
                int iDip2px = rect.left;
                int width = PcTextSizeChoiceMenu.this.mRootView.findViewById(R.id.content).getWidth();
                int realWidth = UIUtils.getRealWidth(PcTextSizeChoiceMenu.this.mRootView.getContext());
                int i = width + iDip2px;
                if (i > realWidth) {
                    iDip2px = (iDip2px - (i - realWidth)) - UIUtils.dip2px(PcTextSizeChoiceMenu.this.mRootView.getContext(), 10.0f);
                }
                PcTextSizeChoiceMenu.this.mRootView.findViewById(R.id.content).setX(iDip2px);
                PcTextSizeChoiceMenu.this.mRootView.findViewById(R.id.content).setY(rect.bottom + UIUtils.dip2px(PcTextSizeChoiceMenu.this.mRootView.getContext(), 20.0f));
                Folme.useAt(PcTextSizeChoiceMenu.this.mRootView).state().to(ViewProperty.ALPHA, Float.valueOf(1.0f), new AnimConfig().addListeners(PcTextSizeChoiceMenu.this.mShowListener));
            }
        });
    }

    @Override // com.miui.common.view.IPopMenu
    public void dismiss() {
        Folme.useAt(this.mRootView).state().to(ViewProperty.ALPHA, Float.valueOf(0.0f), new AnimConfig().addListeners(this.mDismissListener));
    }

    private void activeItem(int i, boolean z) {
        if (i == 0) {
            this.mRootView.findViewById(R.id.size_normal).setSelected(z);
        } else if (i == 1) {
            this.mRootView.findViewById(R.id.size_h1).setSelected(z);
        } else {
            if (i != 2) {
                return;
            }
            this.mRootView.findViewById(R.id.size_h2).setSelected(z);
        }
    }
}
