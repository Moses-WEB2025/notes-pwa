package com.miui.pad.feature.todo.navigation;

import android.content.Context;
import android.os.Build;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.ActionMode;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.SearchView;
import android.widget.TextView;
import com.miui.common.tool.Utils;
import com.miui.notes.R;
import miuix.view.ActionModeAnimationListener;
import miuix.view.SearchActionMode;

/* JADX INFO: loaded from: classes4.dex */
public class PadTodoSearchCallback implements SearchActionMode.Callback {
    private static final int MAX_SEARCH_LENGTH = 32;
    private static final String MULT_PHONE_NUMBER_SUFFIX = "...";
    private ActionMode mActionMode;
    private View mAnchorView;
    private View mAnimView;
    private Context mContext;
    private SearchView.OnQueryTextListener mOnQueryTextListener;
    private OnSearchListener mOnSearchListener;
    private View mResultView;
    private EditText mSearchInput;
    private TextWatcher mSearchTextWatcher = new TextWatcher() { // from class: com.miui.pad.feature.todo.navigation.PadTodoSearchCallback.1
        @Override // android.text.TextWatcher
        public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {
        }

        @Override // android.text.TextWatcher
        public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
        }

        @Override // android.text.TextWatcher
        public void afterTextChanged(Editable editable) {
            PadTodoSearchCallback.this.mSearchText = editable.toString().trim();
            if (PadTodoSearchCallback.this.mOnQueryTextListener != null) {
                PadTodoSearchCallback.this.mOnQueryTextListener.onQueryTextChange(PadTodoSearchCallback.this.mSearchText);
            }
        }
    };
    private TextView.OnEditorActionListener mEditorActionListener = new TextView.OnEditorActionListener() { // from class: com.miui.pad.feature.todo.navigation.PadTodoSearchCallback.2
        @Override // android.widget.TextView.OnEditorActionListener
        public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
            Utils.hideSoftInput(textView);
            return false;
        }
    };
    private String mSearchText = "";

    public interface OnSearchListener {
        void onCreateSearchMode(ActionMode actionMode, Menu menu);

        void onDestroySearchMode(ActionMode actionMode);

        void onSearchModeAnimStart(boolean z);

        void onSearchModeAnimStop(boolean z);

        void onSearchModeAnimUpdate(boolean z, float f);

        void updateExitSearch();
    }

    @Override // android.view.ActionMode.Callback
    public boolean onActionItemClicked(ActionMode actionMode, MenuItem menuItem) {
        return false;
    }

    public PadTodoSearchCallback(Context context, SearchView.OnQueryTextListener onQueryTextListener, OnSearchListener onSearchListener) {
        this.mContext = context;
        this.mOnQueryTextListener = onQueryTextListener;
        this.mOnSearchListener = onSearchListener;
    }

    public void setup(View view, View view2, View view3) {
        this.mAnchorView = view;
        this.mAnimView = view2;
        this.mResultView = view3;
    }

    public boolean isSearchOn() {
        return this.mActionMode != null;
    }

    public String getSearchText() {
        if (isSearchOn()) {
            return this.mSearchText;
        }
        return null;
    }

    public void setSearchText(String str, boolean z, boolean z2) {
        EditText editText = this.mSearchInput;
        if (editText != null) {
            if (!z) {
                editText.setCompoundDrawables(null, null, null, null);
                this.mSearchInput.clearFocus();
                if (!TextUtils.isEmpty(str) && str.length() > 32) {
                    str = str.substring(0, 32 - MULT_PHONE_NUMBER_SUFFIX.length()).trim() + MULT_PHONE_NUMBER_SUFFIX;
                }
                this.mSearchInput.setText(str);
                EditText editText2 = this.mSearchInput;
                editText2.setSelection(editText2.length());
                return;
            }
            editText.setText(str);
        }
    }

    public void removeTextChangedListener() {
        EditText editText = this.mSearchInput;
        if (editText != null) {
            editText.removeTextChangedListener(this.mSearchTextWatcher);
        }
        this.mOnQueryTextListener = null;
    }

    public ActionMode getActionMode() {
        return this.mActionMode;
    }

    public void finish() {
        ActionMode actionMode = this.mActionMode;
        if (actionMode != null) {
            actionMode.finish();
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // android.view.ActionMode.Callback
    public boolean onPrepareActionMode(ActionMode actionMode, Menu menu) {
        ((SearchActionMode) actionMode).addAnimationListener(new ActionModeAnimationListener() { // from class: com.miui.pad.feature.todo.navigation.PadTodoSearchCallback.3
            @Override // miuix.view.ActionModeAnimationListener
            public void onStart(boolean z) {
                if (PadTodoSearchCallback.this.mOnSearchListener != null) {
                    PadTodoSearchCallback.this.mOnSearchListener.onSearchModeAnimStart(z);
                }
            }

            @Override // miuix.view.ActionModeAnimationListener
            public void onUpdate(boolean z, float f) {
                if (PadTodoSearchCallback.this.mOnSearchListener != null) {
                    PadTodoSearchCallback.this.mOnSearchListener.onSearchModeAnimUpdate(z, f);
                }
            }

            @Override // miuix.view.ActionModeAnimationListener
            public void onStop(boolean z) {
                if (PadTodoSearchCallback.this.mOnSearchListener != null) {
                    PadTodoSearchCallback.this.mOnSearchListener.onSearchModeAnimStop(z);
                }
            }
        });
        return true;
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // android.view.ActionMode.Callback
    public boolean onCreateActionMode(ActionMode actionMode, Menu menu) {
        this.mActionMode = actionMode;
        SearchActionMode searchActionMode = (SearchActionMode) actionMode;
        searchActionMode.setAnchorView(this.mAnchorView);
        searchActionMode.setAnimateView(this.mAnimView);
        searchActionMode.setResultView(this.mResultView);
        EditText searchInput = searchActionMode.getSearchInput();
        this.mSearchInput = searchInput;
        searchInput.setFilters(new InputFilter[]{new InputFilter.LengthFilter(32)});
        this.mSearchInput.setHint("");
        this.mSearchInput.setHintTextColor(this.mContext.getResources().getColor(R.color.search_hint_color));
        this.mSearchInput.addTextChangedListener(this.mSearchTextWatcher);
        this.mSearchInput.setOnEditorActionListener(this.mEditorActionListener);
        if (Build.VERSION.SDK_INT >= 35) {
            this.mSearchInput.setLocalePreferredLineHeightForMinimumUsed(false);
        }
        OnSearchListener onSearchListener = this.mOnSearchListener;
        if (onSearchListener != null) {
            onSearchListener.onCreateSearchMode(actionMode, menu);
        }
        return true;
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // android.view.ActionMode.Callback
    public void onDestroyActionMode(ActionMode actionMode) {
        this.mActionMode = null;
        ((SearchActionMode) actionMode).getSearchInput().removeTextChangedListener(this.mSearchTextWatcher);
        OnSearchListener onSearchListener = this.mOnSearchListener;
        if (onSearchListener != null) {
            onSearchListener.updateExitSearch();
        }
        this.mSearchTextWatcher.afterTextChanged(Editable.Factory.getInstance().newEditable(""));
        OnSearchListener onSearchListener2 = this.mOnSearchListener;
        if (onSearchListener2 != null) {
            onSearchListener2.onDestroySearchMode(actionMode);
        }
        this.mSearchText = "";
    }
}
