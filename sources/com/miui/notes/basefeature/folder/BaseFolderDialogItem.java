package com.miui.notes.basefeature.folder;

import android.view.View;
import com.miui.notes.adapter.BindContext;
import com.miui.notes.model.FolderModel;
import com.miui.notes.ui.widget.CheckBoxEditableViewHolder;

/* JADX INFO: loaded from: classes4.dex */
public abstract class BaseFolderDialogItem extends CheckBoxEditableViewHolder {
    public abstract void bindData(FolderModel folderModel, BindContext bindContext, int i, boolean z);

    public abstract void setOnClickListener(View.OnClickListener onClickListener);

    public abstract void setOnLongClickListener(View.OnLongClickListener onLongClickListener);

    public BaseFolderDialogItem(View view) {
        super(view);
    }
}
