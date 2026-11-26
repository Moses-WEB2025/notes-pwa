package com.miui.notes.tool.util;

import android.text.Editable;

/* JADX INFO: loaded from: classes4.dex */
public class EditableFactory extends Editable.Factory {
    private static Editable.Factory sInstance = new EditableFactory();

    public static Editable.Factory getInstance() {
        return sInstance;
    }

    @Override // android.text.Editable.Factory
    public Editable newEditable(CharSequence charSequence) {
        return new SafeSpannableStringBuilder(charSequence);
    }
}
