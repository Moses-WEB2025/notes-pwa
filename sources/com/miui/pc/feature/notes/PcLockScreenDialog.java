package com.miui.pc.feature.notes;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.miui.notes.R;
import miuix.animation.Folme;
import miuix.animation.base.AnimConfig;
import miuix.animation.listener.TransitionListener;
import miuix.animation.property.ViewProperty;

/* JADX INFO: loaded from: classes4.dex */
public class PcLockScreenDialog {
    private View mBtnCancel;
    private View mBtnOk;
    private ActionListener mListener;
    private View mRootView;
    private View.OnClickListener mViewClickListener = new View.OnClickListener() { // from class: com.miui.pc.feature.notes.PcLockScreenDialog.1
        @Override // android.view.View.OnClickListener
        public void onClick(View view) {
            switch (view.getId()) {
                case 16908313:
                    if (PcLockScreenDialog.this.mListener != null) {
                        PcLockScreenDialog.this.mListener.onConfirm();
                    }
                    PcLockScreenDialog.this.dismiss();
                    break;
                case 16908314:
                    if (PcLockScreenDialog.this.mListener != null) {
                        PcLockScreenDialog.this.mListener.onCancel();
                    }
                    PcLockScreenDialog.this.dismiss();
                    break;
            }
        }
    };
    private TransitionListener mDismissListener = new TransitionListener() { // from class: com.miui.pc.feature.notes.PcLockScreenDialog.2
        @Override // miuix.animation.listener.TransitionListener
        public void onComplete(Object obj) {
            ViewGroup viewGroup;
            super.onComplete(obj);
            if (PcLockScreenDialog.this.mRootView == null || (viewGroup = (ViewGroup) PcLockScreenDialog.this.mRootView.getParent()) == null) {
                return;
            }
            viewGroup.removeView(PcLockScreenDialog.this.mRootView);
        }
    };

    interface ActionListener {
        void onCancel();

        void onConfirm();
    }

    public PcLockScreenDialog(Context context, int i, ActionListener actionListener) {
        View viewInflate = LayoutInflater.from(context).inflate(i, (ViewGroup) null);
        this.mRootView = viewInflate;
        initView(viewInflate);
        this.mListener = actionListener;
    }

    private void initView(View view) {
        view.setOnClickListener(new View.OnClickListener() { // from class: com.miui.pc.feature.notes.PcLockScreenDialog.3
            @Override // android.view.View.OnClickListener
            public void onClick(View view2) {
                if (PcLockScreenDialog.this.mListener != null) {
                    PcLockScreenDialog.this.mListener.onCancel();
                }
                PcLockScreenDialog.this.dismiss();
            }
        });
        view.findViewById(R.id.content).setOnClickListener(new View.OnClickListener() { // from class: com.miui.pc.feature.notes.PcLockScreenDialog.4
            @Override // android.view.View.OnClickListener
            public void onClick(View view2) {
            }
        });
        View viewFindViewById = view.findViewById(16908313);
        this.mBtnOk = viewFindViewById;
        if (viewFindViewById != null) {
            viewFindViewById.setOnClickListener(this.mViewClickListener);
        }
        View viewFindViewById2 = view.findViewById(16908314);
        this.mBtnCancel = viewFindViewById2;
        if (viewFindViewById2 != null) {
            viewFindViewById2.setOnClickListener(this.mViewClickListener);
        }
    }

    public void show(View view) {
        ViewGroup viewGroup = (ViewGroup) view.getRootView();
        this.mRootView.setAlpha(0.0f);
        viewGroup.addView(this.mRootView, new ViewGroup.LayoutParams(-1, -1));
        this.mRootView.post(new Runnable() { // from class: com.miui.pc.feature.notes.PcLockScreenDialog.5
            @Override // java.lang.Runnable
            public void run() {
                Folme.useAt(PcLockScreenDialog.this.mRootView).state().to(ViewProperty.ALPHA, Float.valueOf(1.0f));
            }
        });
    }

    public void dismiss() {
        Folme.useAt(this.mRootView).state().to(ViewProperty.ALPHA, Float.valueOf(0.0f), new AnimConfig().addListeners(this.mDismissListener));
    }

    public void setCanceledOnTouchOutside(boolean z) {
        if (z) {
            this.mRootView.setOnClickListener(new View.OnClickListener() { // from class: com.miui.pc.feature.notes.PcLockScreenDialog.6
                @Override // android.view.View.OnClickListener
                public void onClick(View view) {
                    if (PcLockScreenDialog.this.mListener != null) {
                        PcLockScreenDialog.this.mListener.onCancel();
                    }
                    PcLockScreenDialog.this.dismiss();
                }
            });
        } else {
            this.mRootView.setOnClickListener(null);
        }
    }

    public View getView() {
        return this.mRootView;
    }
}
