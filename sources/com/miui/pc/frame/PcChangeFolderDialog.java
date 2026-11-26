package com.miui.pc.frame;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.recyclerview.widget.LinearLayoutManager;
import com.miui.notes.R;
import java.util.List;
import miuix.animation.Folme;
import miuix.animation.base.AnimConfig;
import miuix.animation.listener.TransitionListener;
import miuix.animation.property.ViewProperty;
import miuix.recyclerview.widget.RecyclerView;

/* JADX INFO: loaded from: classes4.dex */
public class PcChangeFolderDialog {
    private View mBtnCancel;
    private View mBtnOk;
    private Context mContext;
    private long mCurrentFolderId;
    public List<FolderItem> mItems;
    private ActionListener mListener;
    private RecyclerView mPickerView;
    private View mRootView;
    private long mTargetFolderId = -1;
    private View.OnClickListener mViewClickListener = new View.OnClickListener() { // from class: com.miui.pc.frame.PcChangeFolderDialog.1
        @Override // android.view.View.OnClickListener
        public void onClick(View view) {
            switch (view.getId()) {
                case 16908313:
                    if (PcChangeFolderDialog.this.mListener != null && PcChangeFolderDialog.this.mTargetFolderId != -1) {
                        PcChangeFolderDialog.this.mListener.onConfirm(PcChangeFolderDialog.this.mTargetFolderId);
                    }
                    PcChangeFolderDialog.this.dismiss();
                    break;
                case 16908314:
                    if (PcChangeFolderDialog.this.mListener != null) {
                        PcChangeFolderDialog.this.mListener.onCancel();
                    }
                    PcChangeFolderDialog.this.dismiss();
                    break;
            }
        }
    };
    private TransitionListener mDismissListener = new TransitionListener() { // from class: com.miui.pc.frame.PcChangeFolderDialog.2
        @Override // miuix.animation.listener.TransitionListener
        public void onComplete(Object obj) {
            ViewGroup viewGroup;
            super.onComplete(obj);
            if (PcChangeFolderDialog.this.mRootView == null || (viewGroup = (ViewGroup) PcChangeFolderDialog.this.mRootView.getParent()) == null) {
                return;
            }
            viewGroup.removeView(PcChangeFolderDialog.this.mRootView);
        }
    };

    interface ActionListener {
        void onCancel();

        void onConfirm(long j);
    }

    public PcChangeFolderDialog(Context context, ActionListener actionListener, List<FolderItem> list, long j) {
        this.mContext = context;
        View viewInflate = LayoutInflater.from(context).inflate(R.layout.pc_note_change_folder_dialog, (ViewGroup) null);
        this.mRootView = viewInflate;
        this.mListener = actionListener;
        this.mItems = list;
        this.mCurrentFolderId = j;
        initView(viewInflate);
    }

    private void initView(View view) {
        this.mPickerView = (RecyclerView) view.findViewById(R.id.picker_view);
        final PcFolderPickerListAdapter pcFolderPickerListAdapter = new PcFolderPickerListAdapter(this.mItems, this.mCurrentFolderId);
        pcFolderPickerListAdapter.setOnClickListener(new View.OnClickListener() { // from class: com.miui.pc.frame.PcChangeFolderDialog.3
            @Override // android.view.View.OnClickListener
            public void onClick(View view2) {
                FolderItem item = pcFolderPickerListAdapter.getItem(PcChangeFolderDialog.this.mPickerView.getChildAdapterPosition(view2));
                PcChangeFolderDialog.this.mTargetFolderId = item.getFolderId();
                pcFolderPickerListAdapter.setSelectedFolder(PcChangeFolderDialog.this.mTargetFolderId);
                pcFolderPickerListAdapter.notifyDataSetChanged();
            }
        });
        this.mPickerView.setAdapter(pcFolderPickerListAdapter);
        this.mPickerView.setLayoutManager(new LinearLayoutManager(this.mContext));
        view.setOnClickListener(new View.OnClickListener() { // from class: com.miui.pc.frame.PcChangeFolderDialog.4
            @Override // android.view.View.OnClickListener
            public void onClick(View view2) {
                if (PcChangeFolderDialog.this.mListener != null) {
                    PcChangeFolderDialog.this.mListener.onCancel();
                }
                PcChangeFolderDialog.this.dismiss();
            }
        });
        view.findViewById(R.id.content).setOnClickListener(new View.OnClickListener() { // from class: com.miui.pc.frame.PcChangeFolderDialog.5
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
        this.mRootView.post(new Runnable() { // from class: com.miui.pc.frame.PcChangeFolderDialog$$ExternalSyntheticLambda0
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
            this.mRootView.setOnClickListener(new View.OnClickListener() { // from class: com.miui.pc.frame.PcChangeFolderDialog$$ExternalSyntheticLambda1
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    this.f$0.lambda$setCanceledOnTouchOutside$1(view);
                }
            });
        } else {
            this.mRootView.setOnClickListener(null);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$setCanceledOnTouchOutside$1(View view) {
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
