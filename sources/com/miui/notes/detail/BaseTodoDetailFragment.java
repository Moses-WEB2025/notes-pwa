package com.miui.notes.detail;

import android.app.Activity;
import android.app.Dialog;
import android.widget.EditText;
import com.miui.common.base.DialogManagerInterface;
import com.miui.common.tool.Utils;
import com.miui.notes.R;
import com.miui.todo.base.todolist.BaseTodoChildItemVh;
import com.miui.todo.data.bean.TodoBaseEntity;
import com.miui.todo.feature.todoedit.BaseTodoEditController;
import miuix.appcompat.app.Fragment;
import miuix.navigator.NavigatorFragmentListener;
import miuix.responsive.map.ScreenSpec;

/* JADX INFO: loaded from: classes4.dex */
public abstract class BaseTodoDetailFragment extends Fragment implements DialogManagerInterface, NavigatorFragmentListener {
    public static final String CAN_ITEM_LONG_CLICK = "can_item_long_click";
    public static final String FORCE_EXIT = "force_exit_search";
    public static final String SEARCH_OPERATION = "search_operation";
    public static final String SEARCH_STRING = "search_string";
    public static final String TODO_LIST_TYPE = "todo_list_type";
    private EditText mEditText;
    protected boolean mInSearchMode;
    private Dialog mManagedDialog;
    protected BaseTodoEditController mTodoEditController;
    protected int mTodoListType = -1;
    protected boolean mIsShow = true;
    protected ScreenSpec mScreenSpec = new ScreenSpec();
    protected int mSplitMode = 0;
    protected int mScreenStatus = 4103;
    protected BaseTodoChildItemVh mEditingTodoVh = null;

    protected abstract BaseTodoEditController createEditController(TodoBaseEntity todoBaseEntity);

    @Override // com.miui.common.base.DialogManagerInterface
    public Fragment getThisFragment() {
        return this;
    }

    protected abstract void refreshTodoData(int i);

    @Override // com.miui.common.base.DialogManagerInterface
    public void manageDialog(Dialog dialog) {
        this.mManagedDialog = dialog;
        this.mEditText = (EditText) dialog.findViewById(R.id.et_folder_name);
    }

    public void releaseDialog(Dialog dialog) {
        if (this.mManagedDialog == dialog) {
            this.mManagedDialog = null;
        }
    }

    @Override // com.miui.common.base.DialogManagerInterface
    public boolean dismissDialog() {
        if (!isDialogShowing()) {
            return false;
        }
        this.mManagedDialog.dismiss();
        this.mManagedDialog = null;
        return true;
    }

    @Override // com.miui.common.base.DialogManagerInterface
    public boolean isDialogShowing() {
        Dialog dialog = this.mManagedDialog;
        return dialog != null && dialog.isShowing();
    }

    @Override // com.miui.common.base.DialogManagerInterface
    public Activity getThisActivity() {
        return getActivity();
    }

    @Override // miuix.appcompat.app.Fragment
    public void onVisibilityChanged(boolean z) {
        Dialog dialog;
        super.onVisibilityChanged(z);
        if (z || (dialog = this.mManagedDialog) == null) {
            return;
        }
        dialog.dismiss();
        this.mManagedDialog = null;
    }

    @Override // androidx.fragment.app.Fragment
    public void onDetach() {
        Dialog dialog = this.mManagedDialog;
        if (dialog != null) {
            dialog.dismiss();
            this.mManagedDialog = null;
        }
        super.onDetach();
    }

    @Override // androidx.fragment.app.Fragment
    public void onPause() {
        EditText editText;
        super.onPause();
        Dialog dialog = this.mManagedDialog;
        if (dialog == null || !dialog.isShowing() || (editText = this.mEditText) == null) {
            return;
        }
        editText.clearFocus();
        Utils.hideSoftInput(this.mEditText);
    }

    public void setInSearchMode(boolean z) {
        this.mInSearchMode = z;
    }
}
