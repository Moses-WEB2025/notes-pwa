package com.miui.mindnoteeditor;

import android.R;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.graphics.Insets;
import android.os.Build;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.ActionMode;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowInsets;
import android.webkit.WebView;
import com.google.gson.Gson;
import com.miui.common.tool.Logger;
import com.miui.richeditor.OnKeyDownAndShortCutListener;
import com.miui.richeditor.util.EditorUtils;
import com.miui.todo.utils.MetaReflectUtil;
import miuix.smartaction.SmartAction;
import org.json.JSONException;
import org.json.JSONObject;

/* JADX INFO: loaded from: classes3.dex */
public class MindNoteWebView extends WebView implements SmartAction {
    private ActionMode mActionMode;
    private boolean mCanPhrase;
    private boolean mCanQuery;
    private boolean mCanSelect;
    private boolean mCanTranslate;
    private String mContextData;
    private String mFeatureConfig;
    private JSONObject mJsonObject;
    private OnKeyDownAndShortCutListener mKeyShortCutListener;
    private String mSelectData;

    @Override // miuix.smartaction.SmartAction
    public Object getObjectForClassify() {
        return null;
    }

    @Override // miuix.smartaction.SmartAction
    public View getView() {
        return this;
    }

    @Override // miuix.smartaction.SmartAction
    public void insertContentToView(String str) {
    }

    @Override // miuix.smartaction.SmartAction
    public boolean isPasswordInputType() {
        return false;
    }

    @Override // miuix.smartaction.SmartAction
    public void isSmartAction() {
    }

    @Override // miuix.smartaction.SmartAction
    public boolean selectAllText() {
        return false;
    }

    @Override // miuix.smartaction.SmartAction
    public boolean selectCurrentWord() {
        return false;
    }

    public MindNoteWebView(Context context) {
        super(context);
        this.mJsonObject = null;
        this.mCanSelect = false;
        this.mCanQuery = false;
        this.mCanPhrase = false;
        this.mCanTranslate = false;
        this.mSelectData = null;
        this.mContextData = null;
    }

    public MindNoteWebView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.mJsonObject = null;
        this.mCanSelect = false;
        this.mCanQuery = false;
        this.mCanPhrase = false;
        this.mCanTranslate = false;
        this.mSelectData = null;
        this.mContextData = null;
    }

    public MindNoteWebView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.mJsonObject = null;
        this.mCanSelect = false;
        this.mCanQuery = false;
        this.mCanPhrase = false;
        this.mCanTranslate = false;
        this.mSelectData = null;
        this.mContextData = null;
    }

    @Override // android.webkit.WebView, android.view.View
    public WindowInsets onApplyWindowInsets(WindowInsets windowInsets) {
        if (Build.VERSION.SDK_INT >= 30 && windowInsets != null) {
            try {
                Logger.INSTANCE.d("MindNoteWebView", "change ime insets bottom");
                Insets insets = windowInsets.getInsets(WindowInsets.Type.ime());
                return new WindowInsets.Builder(windowInsets).setInsets(WindowInsets.Type.ime(), Insets.of(insets.left, insets.top, insets.right, 0)).build();
            } catch (Exception e) {
                Logger.INSTANCE.d("MindNoteWebView", "build windowInsets failed, error: " + e);
                return super.onApplyWindowInsets(windowInsets);
            }
        }
        return super.onApplyWindowInsets(windowInsets);
    }

    public void setFeatureConfig(String str) {
        this.mFeatureConfig = str;
        try {
            this.mJsonObject = new JSONObject(this.mFeatureConfig);
        } catch (JSONException e) {
            e.printStackTrace();
            this.mJsonObject = null;
        }
    }

    @Override // miuix.smartaction.SmartAction
    public void update() {
        evaluateJavascript("javascript:(function updateViewState() {var txt;var canSelect;var canQuery;var canPhrase;var canTranslate;var isPasswordInputType;if (window.getSelection) {txt = window.getSelection().toString();} else if (window.document.getSelection) {txt = window.document.getSelection().toString();} else if (window.document.selection) {txt = window.document.selection.createRange().text;}note_interface.onUpdateViewState(canSelect, canQuery, canPhrase,canTranslate, txt, txt);})()", null);
    }

    @Override // miuix.smartaction.SmartAction
    public void clearState() {
        this.mCanSelect = false;
        this.mCanQuery = false;
        this.mCanPhrase = false;
        this.mCanTranslate = false;
        this.mSelectData = null;
        this.mContextData = null;
    }

    @Override // miuix.smartaction.SmartAction
    public boolean isSupportFeature(String str) {
        JSONObject jSONObject = this.mJsonObject;
        if (jSONObject != null && jSONObject.has(str) && this.mJsonObject.optInt(str) > 0) {
            if (SmartAction.Feature.SELECT.equals(str)) {
                return this.mCanSelect;
            }
            if (SmartAction.Feature.QUERY.equals(str)) {
                return this.mCanQuery;
            }
            if (SmartAction.Feature.PHRASE.equals(str)) {
                return this.mCanPhrase;
            }
            if (SmartAction.Feature.TRANSLATE.equals(str)) {
                return this.mCanTranslate;
            }
        }
        return false;
    }

    @Override // miuix.smartaction.SmartAction
    public String getContextData() {
        return this.mContextData;
    }

    @Override // miuix.smartaction.SmartAction
    public String getSelectData() {
        return this.mSelectData;
    }

    public void updateViewState(boolean z, boolean z2, boolean z3, boolean z4, String str, String str2) {
        this.mCanSelect = z;
        this.mCanQuery = z2;
        this.mCanPhrase = z3;
        this.mCanTranslate = z4;
        this.mSelectData = str;
        this.mContextData = str2;
    }

    @Override // android.webkit.WebView, android.view.View
    public boolean onTouchEvent(MotionEvent motionEvent) {
        motionEvent.getAction();
        try {
            Class[] clsArr = {Integer.TYPE};
            MetaReflectUtil.getDeclaredMethod(MotionEvent.class, "setButtonState", clsArr).invoke(motionEvent, 0);
            MetaReflectUtil.getDeclaredMethod(MotionEvent.class, "setActionButton", clsArr).invoke(motionEvent, 0);
        } catch (Exception unused) {
        }
        motionEvent.getAction();
        if (motionEvent.getAction() == 3) {
            try {
                MotionEvent.class.getDeclaredMethod("setAction", Integer.TYPE).invoke(motionEvent, 1);
            } catch (Exception unused2) {
            }
        }
        return super.onTouchEvent(motionEvent);
    }

    @Override // android.view.View
    public boolean onKeyPreIme(int i, KeyEvent keyEvent) {
        OnKeyDownAndShortCutListener onKeyDownAndShortCutListener;
        OnKeyDownAndShortCutListener onKeyDownAndShortCutListener2;
        if (keyEvent.getAction() == 0 && (onKeyDownAndShortCutListener2 = this.mKeyShortCutListener) != null) {
            onKeyDownAndShortCutListener2.onKeyDown(i, keyEvent);
        }
        if (keyEvent.getAction() == 1 && (onKeyDownAndShortCutListener = this.mKeyShortCutListener) != null) {
            onKeyDownAndShortCutListener.onKeyUp(i, keyEvent);
        }
        return super.onKeyPreIme(i, keyEvent);
    }

    @Override // android.view.View
    public boolean onKeyShortcut(int i, KeyEvent keyEvent) {
        return super.onKeyShortcut(i, keyEvent);
    }

    @Override // android.webkit.WebView, android.view.View, android.view.KeyEvent.Callback
    public boolean onKeyDown(int i, KeyEvent keyEvent) {
        return super.onKeyDown(i, keyEvent);
    }

    public void setKeyShortCutListener(OnKeyDownAndShortCutListener onKeyDownAndShortCutListener) {
        this.mKeyShortCutListener = onKeyDownAndShortCutListener;
    }

    @Override // android.view.View
    public ActionMode startActionMode(ActionMode.Callback callback, int i) {
        ActionMode actionModeStartActionMode = super.startActionMode(callback, i);
        this.mActionMode = actionModeStartActionMode;
        resolveActionMode(actionModeStartActionMode);
        return this.mActionMode;
    }

    private void resolveActionMode(ActionMode actionMode) {
        if (actionMode != null) {
            Menu menu = actionMode.getMenu();
            for (int i = 0; i < menu.size(); i++) {
                MenuItem item = menu.getItem(i);
                if (item.getTitle().equals(getContext().getString(R.string.paste))) {
                    item.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() { // from class: com.miui.mindnoteeditor.MindNoteWebView$$ExternalSyntheticLambda0
                        @Override // android.view.MenuItem.OnMenuItemClickListener
                        public final boolean onMenuItemClick(MenuItem menuItem) {
                            return this.f$0.lambda$resolveActionMode$0(menuItem);
                        }
                    });
                }
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ boolean lambda$resolveActionMode$0(MenuItem menuItem) {
        return doPaste();
    }

    private boolean doPaste() {
        Context context = getContext();
        if (context == null) {
            return false;
        }
        ClipData primaryClip = EditorUtils.getPrimaryClip((ClipboardManager) context.getSystemService("clipboard"));
        StringBuilder sb = new StringBuilder();
        if (primaryClip != null) {
            for (int i = 0; i < primaryClip.getItemCount(); i++) {
                CharSequence charSequenceCoerceToText = primaryClip.getItemAt(i).coerceToText(context);
                if (!TextUtils.isEmpty(charSequenceCoerceToText)) {
                    sb.append(charSequenceCoerceToText);
                }
            }
        }
        pastePlainText(sb.toString());
        return true;
    }

    private void pastePlainText(String str) {
        evaluateJavascript("javascript:pasteText(" + new Gson().toJson(str) + ")", null);
    }
}
