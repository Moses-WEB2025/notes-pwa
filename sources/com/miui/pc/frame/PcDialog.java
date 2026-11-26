package com.miui.pc.frame;

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
public class PcDialog {
    protected View mBtnCancel;
    protected View mBtnOk;
    protected ActionListener mListener;
    protected View mRootView;
    protected View.OnClickListener mViewClickListener = new View.OnClickListener() { // from class: com.miui.pc.frame.PcDialog.1
        @Override // android.view.View.OnClickListener
        public void onClick(View view) {
            switch (view.getId()) {
                case 16908313:
                    if (PcDialog.this.mListener != null) {
                        PcDialog.this.mListener.onConfirm();
                    }
                    PcDialog.this.dismiss();
                    break;
                case 16908314:
                    if (PcDialog.this.mListener != null) {
                        PcDialog.this.mListener.onCancel();
                    }
                    PcDialog.this.dismiss();
                    break;
            }
        }
    };
    protected TransitionListener mDismissListener = new TransitionListener() { // from class: com.miui.pc.frame.PcDialog.2
        @Override // miuix.animation.listener.TransitionListener
        public void onComplete(Object obj) {
            ViewGroup viewGroup;
            super.onComplete(obj);
            if (PcDialog.this.mRootView == null || (viewGroup = (ViewGroup) PcDialog.this.mRootView.getParent()) == null) {
                return;
            }
            viewGroup.removeView(PcDialog.this.mRootView);
        }
    };

    interface ActionListener {
        void onCancel();

        void onConfirm();
    }

    static /* synthetic */ void lambda$initView$1(View view) {
    }

    public PcDialog(Context context, int i, ActionListener actionListener) {
        View viewInflate = LayoutInflater.from(context).inflate(i, (ViewGroup) null);
        this.mRootView = viewInflate;
        initView(viewInflate);
        this.mListener = actionListener;
    }

    private void initView(View view) {
        view.setOnClickListener(new View.OnClickListener() { // from class: com.miui.pc.frame.PcDialog$$ExternalSyntheticLambda1
            @Override // android.view.View.OnClickListener
            public final void onClick(View view2) {
                this.f$0.lambda$initView$0(view2);
            }
        });
        view.findViewById(R.id.content).setOnClickListener(new View.OnClickListener() { // from class: com.miui.pc.frame.PcDialog$$ExternalSyntheticLambda2
            @Override // android.view.View.OnClickListener
            public final void onClick(View view2) {
                PcDialog.lambda$initView$1(view2);
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

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initView$0(View view) {
        ActionListener actionListener = this.mListener;
        if (actionListener != null) {
            actionListener.onCancel();
        }
        dismiss();
    }

    public void show(View view) {
        ViewGroup viewGroup = (ViewGroup) view.getRootView();
        this.mRootView.setAlpha(0.0f);
        viewGroup.addView(this.mRootView, new ViewGroup.LayoutParams(-1, -1));
        this.mRootView.post(new Runnable() { // from class: com.miui.pc.frame.PcDialog$$ExternalSyntheticLambda3
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.lambda$show$2();
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$show$2() {
        Folme.useAt(this.mRootView).state().to(ViewProperty.ALPHA, Float.valueOf(1.0f));
    }

    public void dismiss() {
        Folme.useAt(this.mRootView).state().to(ViewProperty.ALPHA, Float.valueOf(0.0f), new AnimConfig().addListeners(this.mDismissListener));
    }

    public void setCanceledOnTouchOutside(boolean z) {
        if (z) {
            this.mRootView.setOnClickListener(new View.OnClickListener() { // from class: com.miui.pc.frame.PcDialog$$ExternalSyntheticLambda0
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    this.f$0.lambda$setCanceledOnTouchOutside$3(view);
                }
            });
        } else {
            this.mRootView.setOnClickListener(null);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$setCanceledOnTouchOutside$3(View view) {
        ActionListener actionListener = this.mListener;
        if (actionListener != null) {
            actionListener.onCancel();
        }
        dismiss();
    }

    public View getView() {
        return this.mRootView;
    }
}
