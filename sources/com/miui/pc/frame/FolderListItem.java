package com.miui.pc.frame;

import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import com.miui.common.tool.CompatUtils;
import com.miui.common.tool.UIUtils;
import com.miui.notes.R;

/* JADX INFO: loaded from: classes4.dex */
public class FolderListItem extends RecyclerView.ViewHolder {
    protected ViewGroup mEditContainer;
    protected EditText mEditTitle;
    protected String mTempNewFolderName;
    protected TextView mTitle;

    public static FolderListItem newInstance(ViewGroup viewGroup) {
        return new FolderListItem(UIUtils.inflateView(viewGroup, R.layout.pc_list_item));
    }

    protected FolderListItem(View view) {
        super(view);
        this.mTempNewFolderName = null;
        this.mTitle = (TextView) view.findViewById(R.id.title);
        this.mEditContainer = (ViewGroup) view.findViewById(R.id.edit_container);
        this.mEditTitle = (EditText) view.findViewById(R.id.edit_title);
        int color = view.getResources().getColor(R.color.v8_text_appearance_list_section_text_color, null);
        CompatUtils.invokeCursorColor(this.mEditTitle, color, color);
    }

    protected void bind(final FolderItem folderItem, final PcFolderListAdapter.FolderOperationCallBack folderOperationCallBack, long j, long j2) {
        if (j2 != folderItem.getFolderId()) {
            this.mTitle.setVisibility(0);
            this.mEditTitle.setVisibility(8);
            this.mEditContainer.setVisibility(8);
            this.mEditTitle.setCursorVisible(false);
            this.mEditTitle.clearFocus();
            this.mEditTitle.setFocusable(false);
            this.mEditTitle.setFocusableInTouchMode(false);
            String str = this.mTempNewFolderName;
            if (str != null && str.equals(folderItem.getFolderName())) {
                this.mTempNewFolderName = null;
            }
            if (this.mTempNewFolderName == null) {
                this.mTitle.setText(folderItem.getFolderName());
            }
            if (folderItem.getFolderId() == j) {
                this.itemView.setBackgroundResource(R.drawable.pc_selected_folder_background);
                return;
            } else {
                this.itemView.setBackground(null);
                return;
            }
        }
        this.mTitle.setVisibility(8);
        this.mEditContainer.setVisibility(0);
        this.itemView.setBackground(null);
        this.mEditTitle.setVisibility(0);
        this.mEditTitle.setFocusable(true);
        this.mEditTitle.setFocusableInTouchMode(true);
        this.mEditTitle.setText(folderItem.getFolderName());
        this.mEditTitle.setCursorVisible(true);
        EditText editText = this.mEditTitle;
        editText.setSelection(0, editText.getText().length());
        this.mEditTitle.requestFocus();
        this.mEditTitle.setOnFocusChangeListener(new View.OnFocusChangeListener() { // from class: com.miui.pc.frame.FolderListItem$$ExternalSyntheticLambda2
            @Override // android.view.View.OnFocusChangeListener
            public final void onFocusChange(View view, boolean z) {
                this.f$0.lambda$bind$1(folderItem, folderOperationCallBack, view, z);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$bind$1(final FolderItem folderItem, final PcFolderListAdapter.FolderOperationCallBack folderOperationCallBack, View view, boolean z) {
        if (z) {
            return;
        }
        this.mEditTitle.post(new Runnable() { // from class: com.miui.pc.frame.FolderListItem$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.lambda$bind$0(folderItem, folderOperationCallBack);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$bind$0(FolderItem folderItem, PcFolderListAdapter.FolderOperationCallBack folderOperationCallBack) {
        if (folderOperationCallBack.nameFolder(folderItem.getFolderId(), folderItem.getFolderName(), this.mEditTitle.getText().toString())) {
            String string = this.mEditTitle.getText().toString();
            this.mTempNewFolderName = string;
            this.mTitle.setText(string);
        }
    }

    public void setOnClickListener(View.OnClickListener onClickListener) {
        this.itemView.setOnClickListener(onClickListener);
        this.itemView.setOnTouchListener(new View.OnTouchListener() { // from class: com.miui.pc.frame.FolderListItem$$ExternalSyntheticLambda1
            @Override // android.view.View.OnTouchListener
            public final boolean onTouch(View view, MotionEvent motionEvent) {
                return FolderListItem.lambda$setOnClickListener$2(view, motionEvent);
            }
        });
    }

    static /* synthetic */ boolean lambda$setOnClickListener$2(View view, MotionEvent motionEvent) {
        view.onTouchEvent(motionEvent);
        return false;
    }

    public void setOnLongClickListener(View.OnLongClickListener onLongClickListener) {
        this.itemView.setOnLongClickListener(onLongClickListener);
    }

    public void exitEditMode() {
        this.mEditTitle.clearFocus();
    }
}
