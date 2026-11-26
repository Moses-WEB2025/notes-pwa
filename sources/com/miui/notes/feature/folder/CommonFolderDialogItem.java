package com.miui.notes.feature.folder;

import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.core.content.ContextCompat;
import com.miui.common.tool.DisplayUtils;
import com.miui.common.tool.UIUtils;
import com.miui.common.tool.Utils;
import com.miui.notes.R;
import com.miui.notes.adapter.BindContext;
import com.miui.notes.base.accessibility.AccessibilityDelegateUtil;
import com.miui.notes.basefeature.folder.BaseFolderDialogItem;
import com.miui.notes.model.FolderModel;
import com.miui.todo.utils.TodoUtils;
import miuix.animation.Folme;
import miuix.animation.IFolme;
import miuix.animation.ITouchStyle;
import miuix.animation.base.AnimConfig;

/* JADX INFO: loaded from: classes4.dex */
public class CommonFolderDialogItem extends BaseFolderDialogItem {
    private final View folderInfoLayout;
    private long id;
    protected IFolme mItemViewFolme;
    protected TextView mNum;
    protected ImageView mTick;
    protected TextView mTitle;
    protected ImageView mTop;
    protected boolean mUncategorized;

    public static CommonFolderDialogItem newInstance(ViewGroup viewGroup) {
        return new CommonFolderDialogItem(UIUtils.inflateView(viewGroup, R.layout.common_folder_dialog_item));
    }

    public CommonFolderDialogItem(View view) {
        super(view);
        this.mItemViewFolme = null;
        this.mUncategorized = false;
        IFolme iFolmeUseAt = Folme.useAt(view);
        this.mItemViewFolme = iFolmeUseAt;
        iFolmeUseAt.touch().setTint(TodoUtils.getColor(R.color.item_fg_pressing_color));
        IFolme iFolme = this.mItemViewFolme;
        if (iFolme != null) {
            iFolme.touch().setScale(0.93f, ITouchStyle.TouchType.DOWN);
            this.mItemViewFolme.touch().handleTouchOf(view, new AnimConfig[0]);
        }
        this.folderInfoLayout = view.findViewById(R.id.folder_dialog_item_layout);
        this.mTick = (ImageView) view.findViewById(R.id.fold_folder_dialog_item_tick);
        TextView textView = (TextView) view.findViewById(R.id.fold_folder_dialog_item_title);
        this.mTitle = textView;
        DisplayUtils.setMiuiRegularTypeFace(textView);
        TextView textView2 = (TextView) view.findViewById(R.id.fold_folder_dialog_item_num);
        this.mNum = textView2;
        DisplayUtils.setMiuiRegularTypeFace(textView2);
        this.mTop = (ImageView) view.findViewById(R.id.fold_folder_dialog_item_top);
    }

    @Override // com.miui.notes.basefeature.folder.BaseFolderDialogItem
    public void bindData(FolderModel folderModel, BindContext bindContext, int i, boolean z) {
        if (this.itemView.getVisibility() != 0) {
            this.itemView.setVisibility(0);
        }
        this.id = 0L;
        this.id = folderModel.getId();
        if (z) {
            DisplayUtils.setMiuiMediumTypeFace(this.mTitle);
            DisplayUtils.setMiuiMediumTypeFace(this.mNum);
            this.mTick.setVisibility(0);
            AccessibilityDelegateUtil.setSelectedAccessibilityDelegate(this.itemView, true);
        } else {
            TextView textView = this.mTitle;
            textView.setTextColor(ContextCompat.getColor(textView.getContext(), R.color.common_folder_dialog_item_text_color));
            this.mNum.setTextColor(ContextCompat.getColor(this.mTitle.getContext(), R.color.common_folder_dialog_item_num_color));
            DisplayUtils.setMiuiRegularTypeFace(this.mTitle);
            DisplayUtils.setMiuiRegularTypeFace(this.mNum);
            this.mTick.setVisibility(4);
        }
        this.mTitle.setText(folderModel.getSubject());
        this.mNum.setText(Utils.obtainLocalInteger(folderModel.getCount()));
        this.mTop.setVisibility(folderModel.getStickAt() > 0 ? 0 : 8);
        if (bindContext.isInActionMode() && !this.mUncategorized) {
            this.mNum.setVisibility(8);
            onUpdateEditable(true, bindContext.isItemIdChecked(this.id));
        } else {
            this.mNum.setVisibility(0);
            onUpdateEditable(false, false);
        }
        this.mTick.setTextDirection(5);
        this.mTitle.setTextDirection(5);
        this.mNum.setTextDirection(5);
    }

    @Override // com.miui.notes.ui.widget.CheckBoxEditableViewHolder, com.miui.notes.ui.widget.ViewHolderEditableCallback
    public boolean onUpdateEditable(boolean z, boolean z2) {
        if (this.mUncategorized) {
            return true;
        }
        if (z2) {
            this.folderInfoLayout.setForeground(ContextCompat.getDrawable(this.folderInfoLayout.getContext(), R.drawable.common_folder_dialog_list_item_checked_fg));
        } else {
            this.folderInfoLayout.setForeground(null);
        }
        return super.onUpdateEditable(z, z2);
    }

    @Override // com.miui.notes.ui.widget.CheckBoxEditableViewHolder, com.miui.notes.ui.widget.ViewHolderEditableCallback
    public void onAnimationStart(boolean z) {
        if (this.mUncategorized) {
            return;
        }
        if (z) {
            this.mNum.setVisibility(8);
        }
        super.onAnimationStart(z);
    }

    @Override // com.miui.notes.ui.widget.CheckBoxEditableViewHolder, com.miui.notes.ui.widget.ViewHolderEditableCallback
    public void onAnimationStop(boolean z) {
        if (this.mUncategorized) {
            return;
        }
        if (!z) {
            this.mNum.setVisibility(0);
        }
        super.onAnimationStop(z);
    }

    @Override // com.miui.notes.basefeature.folder.BaseFolderDialogItem
    public void setOnClickListener(View.OnClickListener onClickListener) {
        this.itemView.setOnClickListener(onClickListener);
    }

    @Override // com.miui.notes.basefeature.folder.BaseFolderDialogItem
    public void setOnLongClickListener(View.OnLongClickListener onLongClickListener) {
        this.itemView.setOnLongClickListener(onLongClickListener);
    }

    public void setUncategorized(boolean z) {
        this.mUncategorized = z;
    }
}
