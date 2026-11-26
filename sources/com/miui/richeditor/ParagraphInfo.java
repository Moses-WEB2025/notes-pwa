package com.miui.richeditor;

import android.widget.EditText;
import com.miui.richeditor.style.CheckboxSpan;

/* JADX INFO: loaded from: classes4.dex */
class ParagraphInfo extends RegionInfo {
    public ParagraphInfo(int i, int i2) {
        super(i, i2);
    }

    public boolean isCheckbox(EditText editText) {
        return ((CheckboxSpan[]) editText.getText().getSpans(getStart(), getEnd(), CheckboxSpan.class)).length > 0;
    }
}
