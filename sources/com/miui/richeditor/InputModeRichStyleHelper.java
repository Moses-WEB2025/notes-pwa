package com.miui.richeditor;

import android.text.Editable;
import android.text.style.StyleSpan;
import android.text.style.TypefaceSpan;
import android.text.style.UnderlineSpan;
import com.miui.richeditor.style.BgHighLightSpan;

/* JADX INFO: loaded from: classes4.dex */
public class InputModeRichStyleHelper {
    private RichEditTextView mEditor;
    private int mLastInputModeIndex = -1;

    public InputModeRichStyleHelper(RichEditTextView richEditTextView) {
        this.mEditor = richEditTextView;
    }

    public boolean hasInputModeRichStyle() {
        return this.mLastInputModeIndex != -1;
    }

    public void updateInputModeIndex(int i) {
        if (i != this.mLastInputModeIndex) {
            clearInputModeRichStyle(this.mEditor.getText());
            this.mLastInputModeIndex = i;
        }
    }

    public int getInputModeIndex() {
        return this.mLastInputModeIndex;
    }

    public boolean checkStyleInputMode(Editable editable, int i, int i2) {
        int spanStart;
        int spanEnd;
        if (hasStyleSpanOpened(editable, i, i, i2)) {
            for (StyleSpan styleSpan : (StyleSpan[]) editable.getSpans(i, i, StyleSpan.class)) {
                if (styleSpan.getStyle() == i2) {
                    int spanStart2 = editable.getSpanStart(styleSpan);
                    int spanEnd2 = editable.getSpanEnd(styleSpan);
                    if ((editable.getSpanFlags(styleSpan) & 255) == 18 && spanStart2 == spanEnd2) {
                        editable.removeSpan(styleSpan);
                    } else if (spanStart2 < i && i < spanEnd2) {
                        editable.setSpan(styleSpan, spanStart2, i, 33);
                        editable.setSpan(new StyleSpan(styleSpan.getStyle()), i, spanEnd2, 33);
                    } else {
                        closeStyleSpan(editable, i, i, i2);
                    }
                }
            }
            return false;
        }
        StyleSpan[] styleSpanArr = (StyleSpan[]) editable.getSpans(i, i, StyleSpan.class);
        if (styleSpanArr.length > 1) {
            Object obj = null;
            Object obj2 = null;
            for (StyleSpan styleSpan2 : styleSpanArr) {
                if (styleSpan2.getStyle() == i2 && (spanStart = editable.getSpanStart(styleSpan2)) != (spanEnd = editable.getSpanEnd(styleSpan2))) {
                    if (spanEnd == i) {
                        obj = styleSpan2;
                    } else if (spanStart == i) {
                        obj2 = styleSpan2;
                    }
                }
            }
            if (obj != null && obj2 != null && obj != obj2) {
                editable.setSpan(obj, editable.getSpanStart(obj), editable.getSpanEnd(obj2), 33);
                editable.removeSpan(obj2);
                return false;
            }
        }
        updateInputModeIndex(i);
        return true;
    }

    public <T> boolean checkRichStyleInputMode(Editable editable, int i, Class<T> cls) {
        if (hasRichStyleSpanOpened(editable, i, i, cls)) {
            boolean z = false;
            for (Object obj : editable.getSpans(i, i, cls)) {
                int spanStart = editable.getSpanStart(obj);
                int spanEnd = editable.getSpanEnd(obj);
                if (spanStart < i && i < spanEnd) {
                    editable.setSpan(obj, spanStart, i, 33);
                    if (cls == UnderlineSpan.class) {
                        editable.setSpan(new UnderlineSpan(), i, spanEnd, 33);
                    } else if (cls == BgHighLightSpan.class) {
                        editable.setSpan(new BgHighLightSpan(this.mEditor), i, spanEnd, 33);
                    }
                    z = true;
                }
            }
            if (!z) {
                closeRichStyleSpan(editable, i, i, cls);
            }
            return false;
        }
        Object[] spans = editable.getSpans(i, i, cls);
        if (spans.length > 1) {
            Object obj2 = null;
            Object obj3 = null;
            for (Object obj4 : spans) {
                int spanStart2 = editable.getSpanStart(obj4);
                int spanEnd2 = editable.getSpanEnd(obj4);
                if (spanStart2 != spanEnd2) {
                    if (spanEnd2 == i) {
                        obj2 = obj4;
                    } else if (spanStart2 == i) {
                        obj3 = obj4;
                    }
                }
            }
            if (obj2 != null && obj3 != null && obj2 != obj3) {
                editable.setSpan(obj2, editable.getSpanStart(obj2), editable.getSpanEnd(obj3), 33);
                editable.removeSpan(obj3);
                return false;
            }
        }
        updateInputModeIndex(i);
        return true;
    }

    private void clearInputModeRichStyle(Editable editable) {
        int i = this.mLastInputModeIndex;
        if (i < 0) {
            return;
        }
        checkAndClearInputModeSpans(editable, i, StyleSpan.class);
        checkAndClearInputModeSpans(editable, this.mLastInputModeIndex, UnderlineSpan.class);
        checkAndClearInputModeSpans(editable, this.mLastInputModeIndex, BgHighLightSpan.class);
    }

    private <T> void checkAndClearInputModeSpans(Editable editable, int i, Class<T> cls) {
        if (editable == null) {
            return;
        }
        for (Object obj : editable.getSpans(i, i, cls)) {
            if ((editable.getSpanFlags(obj) & 18) == 18) {
                int spanStart = editable.getSpanStart(obj);
                int spanEnd = editable.getSpanEnd(obj);
                if (spanStart == i && spanEnd == i) {
                    editable.removeSpan(obj);
                }
            }
        }
    }

    public boolean hasStyleSpanOpened(Editable editable, int i, int i2, int i3) {
        int spanFlags;
        if (editable == null) {
            return false;
        }
        for (StyleSpan styleSpan : (StyleSpan[]) editable.getSpans(i, i2, StyleSpan.class)) {
            if (styleSpan.getStyle() == i3 && ((spanFlags = editable.getSpanFlags(styleSpan) & 255) == 34 || spanFlags == 18)) {
                return true;
            }
        }
        return false;
    }

    public <T> boolean hasRichStyleSpanOpened(Editable editable, int i, int i2, Class<T> cls) {
        if (editable == null) {
            return false;
        }
        Object[] spans = editable.getSpans(i, i2, cls);
        for (Object obj : spans) {
            int spanFlags = editable.getSpanFlags(obj) & 255;
            if (spanFlags == 34 || spanFlags == 18) {
                return true;
            }
        }
        return false;
    }

    public <T> boolean hasRichStyleSpanClosed(Editable editable, int i, int i2, Class<T> cls) {
        if (editable == null) {
            return false;
        }
        Object[] spans = editable.getSpans(i, i2, cls);
        for (Object obj : spans) {
            if ((editable.getSpanFlags(obj) & 33) == 33) {
                return true;
            }
        }
        return false;
    }

    public <T> void openRichStyleSpan(Editable editable, int i, int i2, Class<T> cls) {
        if (editable == null) {
            return;
        }
        Object[] spans = editable.getSpans(i, i2, cls);
        for (Object obj : spans) {
            if ((editable.getSpanFlags(obj) & 33) == 33) {
                editable.setSpan(obj, editable.getSpanStart(obj), editable.getSpanEnd(obj), 34);
            }
        }
    }

    public void openRichStyleSpanByInfo(Editable editable, SpanInfo spanInfo) {
        if (editable == null) {
            return;
        }
        int spanFlags = editable.getSpanFlags(spanInfo.getSpan());
        if ((spanFlags & 255) == 33 && (spanFlags & 256) == 0 && this.mEditor.getSelectionStart() == this.mEditor.getSelectionEnd() && spanInfo.getEnd() == this.mEditor.getSelectionStart()) {
            editable.setSpan(spanInfo.getSpan(), spanInfo.getStart(), spanInfo.getEnd(), 34);
        }
    }

    public void openRichStyleSpanByInfoOnParaStart(Editable editable, SpanInfo spanInfo) {
        if (editable == null) {
            return;
        }
        int spanFlags = editable.getSpanFlags(spanInfo.getSpan()) & 255;
        if ((spanFlags == 33 || spanFlags == 34) && this.mEditor.getSelectionStart() == this.mEditor.getSelectionEnd() && spanInfo.getEnd() == this.mEditor.getSelectionStart()) {
            editable.setSpan(spanInfo.getSpan(), spanInfo.getStart(), spanInfo.getEnd(), 18);
        }
    }

    public void openFontSizeSpanByInfo(Editable editable, SpanInfo spanInfo) {
        if (editable == null) {
            return;
        }
        int spanFlags = editable.getSpanFlags(spanInfo.getSpan());
        if (this.mEditor.getSelectionStart() == this.mEditor.getSelectionEnd() && spanInfo.getStart() == this.mEditor.getSelectionStart()) {
            int i = spanFlags & 255;
            if (i == 34) {
                TypefaceSpan[] typefaceSpanArr = (TypefaceSpan[]) editable.getSpans(spanInfo.getStart(), spanInfo.getEnd(), TypefaceSpan.class);
                editable.setSpan(spanInfo.getSpan(), spanInfo.getStart(), spanInfo.getEnd(), 18);
                if (typefaceSpanArr.length > 0) {
                    editable.setSpan(typefaceSpanArr[0], spanInfo.getStart(), spanInfo.getEnd(), 18);
                    return;
                }
                return;
            }
            if (i == 33) {
                TypefaceSpan[] typefaceSpanArr2 = (TypefaceSpan[]) editable.getSpans(spanInfo.getStart(), spanInfo.getEnd(), TypefaceSpan.class);
                editable.setSpan(spanInfo.getSpan(), spanInfo.getStart(), spanInfo.getEnd(), 17);
                if (typefaceSpanArr2.length > 0) {
                    editable.setSpan(typefaceSpanArr2[0], spanInfo.getStart(), spanInfo.getEnd(), 17);
                }
            }
        }
    }

    public void closeStyleSpan(Editable editable, int i, int i2, int i3) {
        if (editable == null) {
            return;
        }
        StyleSpan[] styleSpanArr = (StyleSpan[]) editable.getSpans(i, i2, StyleSpan.class);
        for (StyleSpan styleSpan : styleSpanArr) {
            if (styleSpan.getStyle() == i3) {
                int spanFlags = editable.getSpanFlags(styleSpan) & 255;
                if (spanFlags == 34) {
                    editable.setSpan(styleSpan, editable.getSpanStart(styleSpan), editable.getSpanEnd(styleSpan), 33);
                } else if (spanFlags == 18) {
                    int spanStart = editable.getSpanStart(styleSpan);
                    int spanEnd = editable.getSpanEnd(styleSpan);
                    if (spanStart == spanEnd) {
                        editable.removeSpan(styleSpan);
                    } else {
                        editable.setSpan(styleSpan, spanStart, spanEnd, 33);
                    }
                }
            }
        }
    }

    public <T> void closeRichStyleSpan(Editable editable, int i, int i2, Class<T> cls) {
        if (editable == null) {
            return;
        }
        Object[] spans = editable.getSpans(i, i2, cls);
        for (Object obj : spans) {
            int spanFlags = editable.getSpanFlags(obj) & 255;
            if (spanFlags == 34) {
                editable.setSpan(obj, editable.getSpanStart(obj), editable.getSpanEnd(obj), 33);
            } else if (spanFlags == 18) {
                int spanStart = editable.getSpanStart(obj);
                int spanEnd = editable.getSpanEnd(obj);
                if (spanStart == spanEnd) {
                    editable.removeSpan(obj);
                } else if (spanStart < spanEnd) {
                    editable.setSpan(obj, spanStart, spanEnd, 33);
                }
            }
        }
    }
}
