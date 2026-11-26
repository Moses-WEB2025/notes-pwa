package com.miui.richeditor.base;

import android.content.Context;
import android.util.AttributeSet;
import android.view.ActionMode;
import android.view.View;
import androidx.appcompat.widget.AppCompatEditText;
import java.lang.reflect.Method;

/* JADX INFO: loaded from: classes4.dex */
public class FloatingCompatEditText extends AppCompatEditText {
    private ActionMode.Callback actionCallBack;
    private ActionMode actionMode;
    private Method mActionModeMethod;
    private Object mFloatingActionModeHelper;
    private Class mToolbarHelperClass;

    public FloatingCompatEditText(Context context) {
        super(context);
        this.actionCallBack = null;
        this.actionMode = null;
    }

    public FloatingCompatEditText(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.actionCallBack = null;
        this.actionMode = null;
    }

    public FloatingCompatEditText(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.actionCallBack = null;
        this.actionMode = null;
    }

    @Override // android.view.View
    public ActionMode startActionMode(ActionMode.Callback callback, int i) {
        this.actionCallBack = callback;
        ActionMode actionModeStartActionMode = super.startActionMode(callback, i);
        this.actionMode = actionModeStartActionMode;
        if (actionModeStartActionMode != null) {
            return actionModeStartActionMode;
        }
        if (i == 1 && (callback instanceof ActionMode.Callback2)) {
            try {
                Context contextCreatePackageContext = getContext().getApplicationContext().createPackageContext("com.miuix.editor", 3);
                if (this.mToolbarHelperClass == null) {
                    this.mToolbarHelperClass = contextCreatePackageContext.getClassLoader().loadClass("miuix.toolbar.FloatingActionModeHelper");
                }
                if (this.mFloatingActionModeHelper == null) {
                    this.mFloatingActionModeHelper = this.mToolbarHelperClass.getConstructor(Context.class).newInstance(contextCreatePackageContext);
                }
                if (this.mActionModeMethod == null) {
                    this.mActionModeMethod = this.mToolbarHelperClass.getMethod("startActionMode", View.class, ActionMode.Callback2.class);
                }
                Object objInvoke = this.mActionModeMethod.invoke(this.mFloatingActionModeHelper, this, (ActionMode.Callback2) callback);
                if (objInvoke != null) {
                    ActionMode actionMode = (ActionMode) objInvoke;
                    if (callback != null && callback.onCreateActionMode(actionMode, actionMode.getMenu())) {
                        actionMode.invalidate();
                    }
                    return actionMode;
                }
            } catch (Exception unused) {
            }
        }
        return this.actionMode;
    }

    public void destroyActionMode() {
        ActionMode.Callback callback;
        if (this.actionMode != null || (callback = this.actionCallBack) == null) {
            return;
        }
        callback.onDestroyActionMode(null);
    }
}
