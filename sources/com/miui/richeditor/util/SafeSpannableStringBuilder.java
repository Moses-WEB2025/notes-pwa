package com.miui.richeditor.util;

import android.text.SpannableStringBuilder;
import com.miui.notes.base.utils.Logger;

/* JADX INFO: loaded from: classes4.dex */
public class SafeSpannableStringBuilder extends SpannableStringBuilder {
    private static final String TAG = "SafeSpannableStringBuilder";

    public SafeSpannableStringBuilder(CharSequence charSequence) {
        super(charSequence);
    }

    @Override // android.text.SpannableStringBuilder, android.text.Editable
    public SpannableStringBuilder replace(int i, int i2, CharSequence charSequence, int i3, int i4) {
        if (i >= 0 && i2 >= 0 && i <= i2 && i3 >= 0 && i4 >= 0 && i3 <= i4) {
            try {
                return super.replace(i, i2, charSequence, i3, i4);
            } catch (IndexOutOfBoundsException unused) {
                Logger.INSTANCE.e(TAG, "TextView error");
            } catch (Exception unused2) {
                Logger.INSTANCE.e(TAG, "SpannableStringBuilder replace error");
            }
        }
        return this;
    }

    @Override // android.text.SpannableStringBuilder, android.text.GetChars
    public void getChars(int i, int i2, char[] cArr, int i3) {
        int length = length();
        if (i2 > length) {
            i2 = length;
        }
        if (i > i2 || i < 0) {
            return;
        }
        super.getChars(i, i2, cArr, i3);
    }

    @Override // android.text.SpannableStringBuilder, android.text.Spannable
    public void setSpan(Object obj, int i, int i2, int i3) {
        int length = length();
        if (i2 > length) {
            i2 = length;
        }
        if (i > i2 || i2 < 0 || i < 0) {
            return;
        }
        super.setSpan(obj, i, i2, i3);
    }
}
