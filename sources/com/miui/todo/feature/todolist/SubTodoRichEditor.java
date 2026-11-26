package com.miui.todo.feature.todolist;

import android.content.Context;
import android.util.AttributeSet;
import com.miui.common.tool.UIUtils;
import com.miui.richeditor.base.FloatingCompatEditText;

/* JADX INFO: loaded from: classes4.dex */
public class SubTodoRichEditor extends FloatingCompatEditText {
    private boolean isSupportDrag;

    public SubTodoRichEditor(Context context) {
        super(context);
        this.isSupportDrag = true;
        if (UIUtils.isAboveAndroidV()) {
            setLocalePreferredLineHeightForMinimumUsed(false);
        }
    }

    public SubTodoRichEditor(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.isSupportDrag = true;
        if (UIUtils.isAboveAndroidV()) {
            setLocalePreferredLineHeightForMinimumUsed(false);
        }
    }

    public SubTodoRichEditor(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.isSupportDrag = true;
        if (UIUtils.isAboveAndroidV()) {
            setLocalePreferredLineHeightForMinimumUsed(false);
        }
    }

    public boolean isInExtractedMode() {
        return !this.isSupportDrag;
    }

    @Override // android.widget.TextView, android.view.View
    public boolean performLongClick() {
        this.isSupportDrag = false;
        boolean zPerformLongClick = super.performLongClick();
        this.isSupportDrag = true;
        return zPerformLongClick;
    }
}
