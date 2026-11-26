package com.miui.pc.feature.notes;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.miui.common.view.IPopMenu;
import com.miui.notes.R;
import com.miui.notes.model.FolderModel;
import miuix.animation.Folme;
import miuix.animation.base.AnimConfig;
import miuix.animation.listener.TransitionListener;
import miuix.animation.property.ViewProperty;

/* JADX INFO: loaded from: classes4.dex */
public class FolderListOptionMenu implements IPopMenu {
    private TextView mCreate;
    private TextView mDelete;
    private long mFolderId;
    private TextView mRename;
    private View mRootView;
    private View.OnClickListener mItemClickListener = new View.OnClickListener() { // from class: com.miui.pc.feature.notes.FolderListOptionMenu.1
        @Override // android.view.View.OnClickListener
        public void onClick(View view) {
            int id = view.getId();
            if (id == R.id.create_folder) {
                if (FolderListOptionMenu.this.mOnMenuListener != null) {
                    FolderListOptionMenu.this.mOnMenuListener.onItemSelected(view, 0);
                }
                FolderListOptionMenu.this.dismiss();
            } else if (id == R.id.delete_folder) {
                if (FolderListOptionMenu.this.mOnMenuListener != null) {
                    FolderListOptionMenu.this.mOnMenuListener.onItemSelected(view, 1);
                }
                FolderListOptionMenu.this.dismiss();
            } else {
                if (id != R.id.rename_folder) {
                    return;
                }
                if (FolderListOptionMenu.this.mOnMenuListener != null) {
                    FolderListOptionMenu.this.mOnMenuListener.onItemSelected(view, 0);
                }
                FolderListOptionMenu.this.dismiss();
            }
        }
    };
    private IPopMenu.OnMenuListener mOnMenuListener = null;
    private TransitionListener mShowListener = new TransitionListener() { // from class: com.miui.pc.feature.notes.FolderListOptionMenu.2
        @Override // miuix.animation.listener.TransitionListener
        public void onComplete(Object obj) {
            super.onComplete(obj);
            if (FolderListOptionMenu.this.mOnMenuListener != null) {
                FolderListOptionMenu.this.mOnMenuListener.onShow();
            }
        }
    };
    private TransitionListener mDismissListener = new TransitionListener() { // from class: com.miui.pc.feature.notes.FolderListOptionMenu.3
        @Override // miuix.animation.listener.TransitionListener
        public void onComplete(Object obj) {
            ViewGroup viewGroup;
            super.onComplete(obj);
            if (FolderListOptionMenu.this.mRootView != null && (viewGroup = (ViewGroup) FolderListOptionMenu.this.mRootView.getParent()) != null) {
                viewGroup.removeView(FolderListOptionMenu.this.mRootView);
            }
            if (FolderListOptionMenu.this.mOnMenuListener != null) {
                FolderListOptionMenu.this.mOnMenuListener.onDismiss();
            }
        }
    };

    public FolderListOptionMenu(Context context) {
        this.mRootView = null;
        View viewInflate = LayoutInflater.from(context).inflate(R.layout.pc_folder_list_option_menu, (ViewGroup) null, false);
        this.mRootView = viewInflate;
        viewInflate.setOnClickListener(new View.OnClickListener() { // from class: com.miui.pc.feature.notes.FolderListOptionMenu.4
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                FolderListOptionMenu.this.dismiss();
            }
        });
        initView(this.mRootView);
    }

    private void initView(View view) {
        TextView textView = (TextView) view.findViewById(R.id.create_folder);
        this.mCreate = textView;
        textView.setOnClickListener(this.mItemClickListener);
        TextView textView2 = (TextView) view.findViewById(R.id.rename_folder);
        this.mRename = textView2;
        textView2.setOnClickListener(this.mItemClickListener);
        TextView textView3 = (TextView) view.findViewById(R.id.delete_folder);
        this.mDelete = textView3;
        textView3.setOnClickListener(this.mItemClickListener);
    }

    public void updateFolderId(long j) {
        this.mFolderId = j;
        if (!FolderModel.isCustomFolder(j)) {
            this.mRename.setEnabled(false);
            this.mDelete.setEnabled(false);
        } else {
            this.mRename.setEnabled(true);
            this.mDelete.setEnabled(true);
        }
    }

    @Override // com.miui.common.view.IPopMenu
    public void show(View view, final int[] iArr) {
        ViewGroup viewGroup = (ViewGroup) view.getRootView();
        this.mRootView.setAlpha(0.0f);
        if (this.mRootView.getParent() != null) {
            ((ViewGroup) this.mRootView.getParent()).removeView(this.mRootView);
        }
        viewGroup.addView(this.mRootView, new ViewGroup.LayoutParams(-1, -1));
        viewGroup.post(new Runnable() { // from class: com.miui.pc.feature.notes.FolderListOptionMenu.5
            @Override // java.lang.Runnable
            public void run() {
                FolderListOptionMenu.this.mRootView.findViewById(R.id.content).setX(iArr[0]);
                FolderListOptionMenu.this.mRootView.findViewById(R.id.content).setY(iArr[1]);
                Folme.useAt(FolderListOptionMenu.this.mRootView).state().to(ViewProperty.ALPHA, Float.valueOf(1.0f), new AnimConfig().addListeners(FolderListOptionMenu.this.mShowListener));
            }
        });
    }

    @Override // com.miui.common.view.IPopMenu
    public void dismiss() {
        if (this.mRootView.getAlpha() != 0.0f) {
            Folme.useAt(this.mRootView).state().to(ViewProperty.ALPHA, Float.valueOf(0.0f), new AnimConfig().addListeners(this.mDismissListener));
            return;
        }
        try {
            ((ViewGroup) this.mRootView.getParent()).removeView(this.mRootView);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override // com.miui.common.view.IPopMenu
    public void setOnMenuListener(IPopMenu.OnMenuListener onMenuListener) {
        this.mOnMenuListener = onMenuListener;
    }
}
