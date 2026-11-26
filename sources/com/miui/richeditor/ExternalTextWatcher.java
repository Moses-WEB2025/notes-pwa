package com.miui.richeditor;

import android.text.Editable;
import android.text.TextWatcher;

/* JADX INFO: loaded from: classes4.dex */
public abstract class ExternalTextWatcher implements TextWatcher {
    public abstract void afterCheckBoxChanged();

    public abstract void afterTextActuallyChanged(Editable editable);

    @Override // android.text.TextWatcher
    public void afterTextChanged(Editable editable) {
    }

    @Override // android.text.TextWatcher
    public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {
    }

    @Override // android.text.TextWatcher
    public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
    }
}
