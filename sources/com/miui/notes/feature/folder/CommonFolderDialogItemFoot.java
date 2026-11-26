package com.miui.notes.feature.folder;

import android.view.View;
import android.view.ViewGroup;
import com.miui.common.tool.UIUtils;
import com.miui.notes.R;
import com.miui.notes.adapter.BindContext;
import com.miui.notes.basefeature.folder.BaseFolderDialogItem;
import com.miui.notes.model.FolderModel;
import miuix.animation.Folme;
import miuix.animation.IFolme;
import miuix.animation.ITouchStyle;
import miuix.animation.base.AnimConfig;

/* JADX INFO: loaded from: classes4.dex */
public class CommonFolderDialogItemFoot extends BaseFolderDialogItem {
    private final View createFolder;
    protected IFolme mItemViewFolme;

    @Override // com.miui.notes.basefeature.folder.BaseFolderDialogItem
    public void bindData(FolderModel folderModel, BindContext bindContext, int i, boolean z) {
    }

    @Override // com.miui.notes.ui.widget.CheckBoxEditableViewHolder, com.miui.notes.ui.widget.ViewHolderEditableCallback
    public void onAnimationStart(boolean z) {
    }

    @Override // com.miui.notes.ui.widget.CheckBoxEditableViewHolder, com.miui.notes.ui.widget.ViewHolderEditableCallback
    public void onAnimationStop(boolean z) {
    }

    @Override // com.miui.notes.ui.widget.CheckBoxEditableViewHolder, com.miui.notes.ui.widget.ViewHolderEditableCallback
    public boolean onUpdateEditable(boolean z, boolean z2) {
        return true;
    }

    @Override // com.miui.notes.basefeature.folder.BaseFolderDialogItem
    public void setOnLongClickListener(View.OnLongClickListener onLongClickListener) {
    }

    public static CommonFolderDialogItemFoot newInstance(ViewGroup viewGroup) {
        return new CommonFolderDialogItemFoot(UIUtils.inflateView(viewGroup, R.layout.common_folder_dialog_item_foot));
    }

    public CommonFolderDialogItemFoot(View view) {
        super(view);
        this.mItemViewFolme = null;
        IFolme iFolmeUseAt = Folme.useAt(view);
        this.mItemViewFolme = iFolmeUseAt;
        iFolmeUseAt.touch().setTint(0.03f, 0.0f, 0.0f, 0.0f);
        IFolme iFolme = this.mItemViewFolme;
        if (iFolme != null) {
            iFolme.touch().setScale(0.93f, ITouchStyle.TouchType.DOWN);
            this.mItemViewFolme.touch().handleTouchOf(view, new AnimConfig[0]);
        }
        this.createFolder = view.findViewById(R.id.folder_dialog_list_create_folder);
    }

    @Override // com.miui.notes.basefeature.folder.BaseFolderDialogItem
    public void setOnClickListener(View.OnClickListener onClickListener) {
        this.itemView.setOnClickListener(onClickListener);
    }
}
