package com.miui.pc.frame;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.miui.common.tool.Utils;
import com.miui.notes.R;
import miuix.animation.Folme;
import miuix.animation.base.AnimConfig;
import miuix.animation.listener.TransitionListener;
import miuix.animation.property.ViewProperty;

/* JADX INFO: loaded from: classes4.dex */
public class PcDeleteFolderDialog {
    private View mBtnCancel;
    private View mBtnOk;
    private ActionListener mListener;
    private View mRootView;
    private View.OnClickListener mViewClickListener = new View.OnClickListener() { // from class: com.miui.pc.frame.PcDeleteFolderDialog.1
        @Override // android.view.View.OnClickListener
        public void onClick(View view) {
            switch (view.getId()) {
                case 16908313:
                    if (PcDeleteFolderDialog.this.mListener != null) {
                        PcDeleteFolderDialog.this.mListener.onConfirm();
                    }
                    PcDeleteFolderDialog.this.dismiss();
                    break;
                case 16908314:
                    if (PcDeleteFolderDialog.this.mListener != null) {
                        PcDeleteFolderDialog.this.mListener.onCancel();
                    }
                    PcDeleteFolderDialog.this.dismiss();
                    break;
            }
        }
    };
    private TransitionListener mDismissListener = new TransitionListener() { // from class: com.miui.pc.frame.PcDeleteFolderDialog.2
        @Override // miuix.animation.listener.TransitionListener
        public void onComplete(Object obj) {
            ViewGroup viewGroup;
            super.onComplete(obj);
            if (PcDeleteFolderDialog.this.mRootView == null || (viewGroup = (ViewGroup) PcDeleteFolderDialog.this.mRootView.getParent()) == null) {
                return;
            }
            viewGroup.removeView(PcDeleteFolderDialog.this.mRootView);
        }
    };

    interface ActionListener {
        void onCancel();

        void onConfirm();
    }

    public PcDeleteFolderDialog(Context context, ActionListener actionListener) {
        View viewInflate = LayoutInflater.from(context).inflate(R.layout.pc_note_delete_folder_dialog, (ViewGroup) null);
        this.mRootView = viewInflate;
        initView(viewInflate);
        this.mListener = actionListener;
    }

    private void initView(View view) {
        view.setOnClickListener(new View.OnClickListener() { // from class: com.miui.pc.frame.PcDeleteFolderDialog.3
            @Override // android.view.View.OnClickListener
            public void onClick(View view2) {
                if (PcDeleteFolderDialog.this.mListener != null) {
                    PcDeleteFolderDialog.this.mListener.onCancel();
                }
                PcDeleteFolderDialog.this.dismiss();
            }
        });
        view.findViewById(R.id.content).setOnClickListener(new View.OnClickListener() { // from class: com.miui.pc.frame.PcDeleteFolderDialog.4
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
        ((TextView) view.findViewById(R.id.message)).setText(view.getContext().getString(R.string.alert_message_delete, Utils.getAllItemTerms(0, 1)));
    }

    public void show(View view) {
        ViewGroup viewGroup = (ViewGroup) view.getRootView();
        this.mRootView.setAlpha(0.0f);
        viewGroup.addView(this.mRootView, new ViewGroup.LayoutParams(-1, -1));
        this.mRootView.post(new Runnable() { // from class: com.miui.pc.frame.PcDeleteFolderDialog$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.lambda$show$0();
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$show$0() {
        Folme.useAt(this.mRootView).state().to(ViewProperty.ALPHA, Float.valueOf(1.0f));
    }

    public void dismiss() {
        Folme.useAt(this.mRootView).state().to(ViewProperty.ALPHA, Float.valueOf(0.0f), new AnimConfig().addListeners(this.mDismissListener));
    }

    public void setCanceledOnTouchOutside(boolean z) {
        if (z) {
            this.mRootView.setOnClickListener(new View.OnClickListener() { // from class: com.miui.pc.frame.PcDeleteFolderDialog.5
                @Override // android.view.View.OnClickListener
                public void onClick(View view) {
                    if (PcDeleteFolderDialog.this.mListener != null) {
                        PcDeleteFolderDialog.this.mListener.onCancel();
                    }
                    PcDeleteFolderDialog.this.dismiss();
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
