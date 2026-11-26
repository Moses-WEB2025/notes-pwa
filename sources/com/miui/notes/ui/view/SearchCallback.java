package com.miui.notes.ui.view;

import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextWatcher;
import android.view.ActionMode;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.BaseInputConnection;
import android.widget.EditText;
import android.widget.SearchView;
import android.widget.TextView;
import com.miui.common.stat.ToDoStat;
import com.miui.common.tool.Logger;
import com.miui.common.tool.Utils;
import com.miui.notes.R;
import com.miui.notes.base.accessibility.AccessibilityDelegateUtil;
import miuix.view.ActionModeAnimationListener;
import miuix.view.SearchActionMode;
import org.apache.commons.lang3.StringUtils;

/* JADX INFO: loaded from: classes4.dex */
public class SearchCallback implements SearchActionMode.Callback {
    private static final int MAX_SEARCH_LENGTH = 32;
    private static final String MULT_PHONE_NUMBER_SUFFIX = "...";
    private ActionMode mActionMode;
    private Activity mActivity;
    private View mAnchorView;
    private View mAnimView;
    private Context mContext;
    private SearchView.OnQueryTextListener mOnQueryTextListener;
    private final OnSearchListener mOnSearchListener;
    private View mResultView;
    private EditText mSearchInput;
    private View mStickyView;
    private final TextWatcher mSearchTextWatcher = new TextWatcher() { // from class: com.miui.notes.ui.view.SearchCallback.1
        @Override // android.text.TextWatcher
        public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {
        }

        @Override // android.text.TextWatcher
        public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
        }

        @Override // android.text.TextWatcher
        public void afterTextChanged(Editable editable) {
            if (BaseInputConnection.getComposingSpanStart(editable) >= 0) {
                int length = editable.length();
                int composingSpanStart = BaseInputConnection.getComposingSpanStart(editable);
                int composingSpanEnd = BaseInputConnection.getComposingSpanEnd(editable);
                if (composingSpanStart == 0 && composingSpanEnd == length) {
                    SearchCallback.this.mSearchText = editable.toString().replaceAll(StringUtils.SPACE, "");
                } else if (composingSpanStart != 0 && composingSpanEnd != length) {
                    SearchCallback.this.mSearchText = editable.toString().substring(0, composingSpanStart) + editable.toString().substring(composingSpanStart, composingSpanEnd).replace(StringUtils.SPACE, "") + editable.toString().substring(composingSpanEnd, length);
                } else if (composingSpanStart != 0) {
                    SearchCallback.this.mSearchText = editable.toString().substring(0, composingSpanStart) + editable.toString().substring(composingSpanStart, length).replace(StringUtils.SPACE, "");
                } else {
                    SearchCallback.this.mSearchText = editable.toString().substring(0, composingSpanEnd).replace(StringUtils.SPACE, "") + editable.toString().substring(composingSpanEnd, length);
                }
                SearchCallback searchCallback = SearchCallback.this;
                searchCallback.mSearchText = searchCallback.mSearchText.trim();
            } else {
                SearchCallback.this.mSearchText = editable.toString().trim();
            }
            if (SearchCallback.this.mOnQueryTextListener != null) {
                SearchCallback.this.mOnQueryTextListener.onQueryTextChange(SearchCallback.this.mSearchText);
            }
            if (SearchCallback.this.mAnimView != null) {
                SearchCallback.this.mAnimView.setImportantForAccessibility(1);
            }
        }
    };
    private final TextView.OnEditorActionListener mEditorActionListener = new TextView.OnEditorActionListener() { // from class: com.miui.notes.ui.view.SearchCallback$$ExternalSyntheticLambda0
        @Override // android.widget.TextView.OnEditorActionListener
        public final boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
            return SearchCallback.lambda$new$0(textView, i, keyEvent);
        }
    };
    private String mSearchText = "";

    public interface OnSearchListener {
        void onCreateSearchMode(ActionMode actionMode, Menu menu);

        void onDestroySearchMode(ActionMode actionMode);

        void onSearchModeAnimStart(boolean z);

        void onSearchModeAnimStop(boolean z);

        void onSearchModeAnimUpdate(boolean z, float f);
    }

    public interface TextSearch {
        public static final String PHONE_NUMBER_SEARCH_KEY = "com.android.contacts";
        public static final int SEARCH_TYPE_CALL_NOTE = 1;
        public static final int SEARCH_TYPE_NOTE_AIACTION = 3;
        public static final int SEARCH_TYPE_NOTE_CONTENT = 0;
        public static final int SEARCH_TYPE_NOTE_VOICEASSIST = 2;
        public static final String TEXT_SEARCH_KEY = "com.miui.text.search";
        public static final String TEXT_SEARCH_KEY_FROM_AI_ACTION = "com.miui.text.search_from_ai_action";

        void finishIfNeeded();

        void onTextSearch(String str, int i);
    }

    @Override // android.view.ActionMode.Callback
    public boolean onActionItemClicked(ActionMode actionMode, MenuItem menuItem) {
        return false;
    }

    static /* synthetic */ boolean lambda$new$0(TextView textView, int i, KeyEvent keyEvent) {
        Utils.hideSoftInput(textView);
        return false;
    }

    public SearchCallback(Context context, SearchView.OnQueryTextListener onQueryTextListener, OnSearchListener onSearchListener) {
        this.mContext = context;
        this.mOnQueryTextListener = onQueryTextListener;
        this.mOnSearchListener = onSearchListener;
    }

    public SearchCallback(Context context, SearchView.OnQueryTextListener onQueryTextListener, OnSearchListener onSearchListener, Activity activity) {
        this.mContext = context;
        this.mOnQueryTextListener = onQueryTextListener;
        this.mOnSearchListener = onSearchListener;
        this.mActivity = activity;
    }

    public void setup(View view, View view2, View view3) {
        this.mAnchorView = view;
        this.mAnimView = view3;
    }

    public void setResultView(View view) {
        this.mResultView = view;
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
                if (str.length() > 32) {
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

    public boolean finish() {
        ActionMode actionMode = this.mActionMode;
        if (actionMode != null) {
            actionMode.finish();
            Logger.INSTANCE.d("SearchCallback", ToDoStat.FINISHED);
            return true;
        }
        Logger.INSTANCE.i("SearchCallback", "already finished!!");
        return false;
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // android.view.ActionMode.Callback
    public boolean onPrepareActionMode(ActionMode actionMode, Menu menu) {
        ((SearchActionMode) actionMode).addAnimationListener(new ActionModeAnimationListener() { // from class: com.miui.notes.ui.view.SearchCallback.2
            @Override // miuix.view.ActionModeAnimationListener
            public void onStart(boolean z) {
                if (SearchCallback.this.mOnSearchListener != null) {
                    SearchCallback.this.mOnSearchListener.onSearchModeAnimStart(z);
                }
            }

            @Override // miuix.view.ActionModeAnimationListener
            public void onUpdate(boolean z, float f) {
                if (SearchCallback.this.mOnSearchListener != null) {
                    SearchCallback.this.mOnSearchListener.onSearchModeAnimUpdate(z, f);
                }
            }

            @Override // miuix.view.ActionModeAnimationListener
            public void onStop(boolean z) {
                if (SearchCallback.this.mOnSearchListener != null) {
                    SearchCallback.this.mOnSearchListener.onSearchModeAnimStop(z);
                }
            }
        });
        return true;
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // android.view.ActionMode.Callback
    public boolean onCreateActionMode(ActionMode actionMode, Menu menu) {
        this.mActionMode = actionMode;
        Activity activity = this.mActivity;
        if (activity != null) {
            AccessibilityDelegateUtil.setButtonAccessibilityDelegate(activity.getWindow().getDecorView().findViewById(R.id.search_panel).getRootView().findViewById(R.id.search_text_cancel));
        }
        SearchActionMode searchActionMode = (SearchActionMode) actionMode;
        searchActionMode.setAnchorView(this.mAnchorView);
        searchActionMode.setAnimateView(this.mAnimView);
        View view = this.mResultView;
        if (view != null) {
            searchActionMode.setResultView(view);
        }
        EditText searchInput = searchActionMode.getSearchInput();
        this.mSearchInput = searchInput;
        searchInput.setFilters(new InputFilter[]{new InputFilter.LengthFilter(32)});
        this.mSearchInput.setHint(R.string.search_hint);
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
        this.mSearchTextWatcher.afterTextChanged(Editable.Factory.getInstance().newEditable(""));
        this.mSearchInput = null;
        OnSearchListener onSearchListener = this.mOnSearchListener;
        if (onSearchListener != null) {
            onSearchListener.onDestroySearchMode(actionMode);
        }
    }

    private Context getContext() {
        return this.mContext;
    }
}
