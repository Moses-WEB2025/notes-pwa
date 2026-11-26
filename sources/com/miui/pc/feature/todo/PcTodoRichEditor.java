package com.miui.pc.feature.todo;

import android.R;
import android.content.Context;
import android.util.AttributeSet;
import com.miui.notes.theme.util.ResourceManager;
import com.miui.pc.frame.MirrorToastManager;
import com.miui.todo.feature.todoedit.TodoRichEditor;

/* JADX INFO: loaded from: classes4.dex */
public class PcTodoRichEditor extends TodoRichEditor {
    public PcTodoRichEditor(Context context) {
        super(context);
    }

    public PcTodoRichEditor(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, R.attr.editTextStyle);
    }

    public PcTodoRichEditor(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.mEditorTheme = ResourceManager.getTheme(10);
    }

    @Override // com.miui.richeditor.RichEditTextView, com.miui.richeditor.IRichEditor
    public void makeTextToToast(Context context, int i, int i2) {
        MirrorToastManager.show(context, i, false);
    }
}
