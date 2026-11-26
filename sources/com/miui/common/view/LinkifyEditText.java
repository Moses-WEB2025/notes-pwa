package com.miui.common.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.TextView;
import androidx.appcompat.widget.AppCompatEditText;
import miuix.text.util.Linkify;

/* JADX INFO: loaded from: classes2.dex */
public class LinkifyEditText extends AppCompatEditText {
    public LinkifyEditText(Context context) {
        super(context);
        init();
    }

    public LinkifyEditText(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        init();
    }

    public LinkifyEditText(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        init();
    }

    private void init() {
        if (getAutoLinkMask() != 0) {
            Linkify.addLinks(this, getAutoLinkMask());
        }
    }

    @Override // android.widget.EditText, android.widget.TextView
    public void setText(CharSequence charSequence, TextView.BufferType bufferType) {
        super.setText(charSequence, bufferType);
        if (getAutoLinkMask() != 0) {
            Linkify.addLinks(this, getAutoLinkMask());
        }
    }

    @Override // android.widget.TextView
    public void append(CharSequence charSequence, int i, int i2) {
        super.append(charSequence, i, i2);
        if (getAutoLinkMask() != 0) {
            Linkify.addLinks(this, getAutoLinkMask());
        }
    }
}
