package com.miui.notes.component;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.widget.Toast;
import com.miui.common.tool.Logger;
import com.miui.common.tool.RomUtils;
import com.miui.common.tool.event.LiveDataEventBus;
import com.miui.notes.R;
import com.miui.notes.model.FolderModel;
import com.miui.notes.store.FolderStore;

/* JADX INFO: loaded from: classes4.dex */
public class RenameFolderDialog extends EditTextDialog {
    private boolean mCheckExist;
    private Context mContext;
    private FolderModel mFolderModel;
    private View.OnClickListener mNegativeBtnListener;
    private String mOriginName;
    private View.OnClickListener mPositiveBtnListener;
    private Integer mTitleResId;

    public RenameFolderDialog(Context context, FolderModel folderModel, int i) {
        super(context);
        this.mTitleResId = null;
        this.mNegativeBtnListener = null;
        this.mPositiveBtnListener = null;
        this.mCheckExist = true;
        this.mOriginName = null;
        this.mContext = context;
        this.mFolderModel = folderModel;
        this.mTitleResId = Integer.valueOf(i);
    }

    public RenameFolderDialog(Context context, FolderModel folderModel) {
        super(context);
        this.mTitleResId = null;
        this.mNegativeBtnListener = null;
        this.mPositiveBtnListener = null;
        this.mCheckExist = true;
        this.mOriginName = null;
        this.mContext = context;
        this.mFolderModel = folderModel;
    }

    @Override // com.miui.notes.component.EditTextDialog
    protected void onInitialized() {
        String subject = FolderStore.getSubject(this.mFolderModel.getId());
        if (subject == null) {
            subject = this.mFolderModel.getSubject();
        }
        this.mOriginName = subject;
        this.mEditor.setText(subject);
        this.mEditor.setSelection(this.mEditor.getText().length());
        Context context = this.mContext;
        Integer num = this.mTitleResId;
        setTitle(context.getString(num != null ? num.intValue() : R.string.rename_folder_dialog_tile));
        setCancelable(false);
        setCanceledOnTouchOutside(false);
    }

    @Override // com.miui.notes.component.EditTextDialog
    public void onPositiveButtonClick() {
        String strTrim = this.mEditor.getText().toString().trim();
        Logger.INSTANCE.d("RenameFolderDialog", "subject");
        if (TextUtils.isEmpty(strTrim)) {
            dismiss();
            return;
        }
        Context context = getContext();
        if (this.mCheckExist) {
            if (FolderStore.exist(context, strTrim) && !strTrim.equals(this.mOriginName)) {
                if (this.mPositiveButton != null) {
                    this.mPositiveButton.setEnabled(false);
                }
                Toast.makeText(context, context.getString(R.string.folder_exist), 1).show();
                this.mEditor.setSelection(0, this.mEditor.length());
                return;
            }
            FolderStore.existSameAndDelete(context, strTrim);
            FolderStore.rename(context, this.mFolderModel.getId(), strTrim);
            if (RomUtils.isPadMode()) {
                LiveDataEventBus.with(LiveDataEventBus.BUS_MODIFY_FOLDER_NAME).setValue(Long.valueOf(this.mFolderModel.getId()));
            }
        } else if (!FolderStore.exist(context, strTrim)) {
            FolderStore.rename(context, this.mFolderModel.getId(), strTrim);
        }
        View.OnClickListener onClickListener = this.mPositiveBtnListener;
        if (onClickListener != null) {
            onClickListener.onClick(this.mPositiveButton);
        }
        dismiss();
    }

    @Override // com.miui.notes.component.EditTextDialog
    public void onNegativeButtonClick() {
        View.OnClickListener onClickListener = this.mNegativeBtnListener;
        if (onClickListener != null) {
            onClickListener.onClick(this.mNegativeButton);
        }
    }

    public void setPositiveListener(View.OnClickListener onClickListener) {
        this.mPositiveBtnListener = onClickListener;
    }

    public void setNegativeListener(View.OnClickListener onClickListener) {
        this.mNegativeBtnListener = onClickListener;
    }
}
