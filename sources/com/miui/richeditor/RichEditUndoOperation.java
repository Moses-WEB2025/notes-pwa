package com.miui.richeditor;

import android.text.Editable;
import android.text.Layout;
import android.text.SpannableStringBuilder;
import android.text.style.AlignmentSpan;
import android.text.style.RelativeSizeSpan;
import android.text.style.StyleSpan;
import android.text.style.TypefaceSpan;
import android.text.style.UnderlineSpan;
import com.miui.notes.base.utils.Logger;
import com.miui.richeditor.style.CheckboxSpan;
import com.miui.richeditor.style.EditLineHeightSpan;
import com.miui.richeditor.style.EditorBulletSpan;
import com.miui.richeditor.style.FontLargeSizeSpan;
import com.miui.richeditor.style.FontMidSizeSpan;
import com.miui.richeditor.style.FontNormalSizeSpan;
import com.miui.richeditor.util.EditorUtils;
import com.miui.richeditor.util.SafeSpannableStringBuilder;
import java.util.Objects;

/* JADX INFO: loaded from: classes4.dex */
public class RichEditUndoOperation implements UndoOperation {
    private final String TAG = "RichEditUndoOperation";
    private int mCounter;
    private int mCursorEnd;
    private int mCursorStart;
    protected SpannableStringBuilder mDeltaText;
    private boolean mIsComposing;
    private int mMode;
    private int mRangeEnd;
    private int mRangeStart;
    private RichEditTextView mRichEditor;

    public RichEditUndoOperation(RichEditTextView richEditTextView) {
        this.mRichEditor = richEditTextView;
        getRichEditor().setSkipSelectionChanged(true);
        this.mDeltaText = new SafeSpannableStringBuilder(getRichEditor().getText());
        getRichEditor().setSkipSelectionChanged(false);
        this.mIsComposing = false;
    }

    private RichEditTextView getRichEditor() {
        return this.mRichEditor;
    }

    @Override // com.miui.richeditor.UndoOperation
    public void undo() {
        this.mRichEditor.stopMediaPlayerIfNeed();
        int i = this.mRangeStart;
        int length = ((Editable) Objects.requireNonNull(getRichEditor().getText())).length() - this.mRangeEnd;
        if (i < 0 || length < 0) {
            return;
        }
        if (length < i) {
            Logger.INSTANCE.w("RichEditUndoOperation", "undo error  start = " + i + " end = " + i);
            length = i;
        }
        Logger.INSTANCE.i("RichEditUndoOperation", "undo, index:" + i + "-" + length);
        SpannableStringBuilder subSequence = getRichEditor().getSubSequence(i, length);
        try {
            replace(i, length);
            getRichEditor().setSelection(this.mCursorStart);
            this.mDeltaText = subSequence;
            getRichEditor().setSkipRichText(false);
            getRichEditor().refreshContainer();
        } catch (Exception e) {
            Logger.INSTANCE.w("RichEditUndoOperation", "undo error " + e.toString());
        }
    }

    protected void replace(int i, int i2) {
        char c;
        RichEditUndoOperation richEditUndoOperation;
        int i3;
        getRichEditor().clearTextWatcher();
        getRichEditor().setSkipRichText(true);
        getRichEditor().styleInValid(StyleSpan.class, 1, i, i2);
        getRichEditor().styleInValid(StyleSpan.class, 2, i, i2);
        getRichEditor().styleInValid(UnderlineSpan.class, 5, i, i2);
        if (getRichEditor().isParagraphCentered(i, i2)) {
            c = 1;
        } else {
            c = getRichEditor().isParagraphRighted(i, i2) ? (char) 2 : (char) 0;
        }
        getRichEditor().getText();
        if (getRichEditor().getText() == null) {
            return;
        }
        Logger.INSTANCE.d("RichEditUndoOperation", "debugFontSizeSpan before replace start = " + i + " end = " + i2 + " mDeltaText = " + ((Object) this.mDeltaText));
        getRichEditor().getText().replace(i, i2, this.mDeltaText);
        Editable text = getRichEditor().getText();
        SpannableStringBuilder spannableStringBuilder = this.mDeltaText;
        for (EditLineHeightSpan editLineHeightSpan : (EditLineHeightSpan[]) spannableStringBuilder.getSpans(0, spannableStringBuilder.length(), EditLineHeightSpan.class)) {
            text.setSpan(editLineHeightSpan, this.mDeltaText.getSpanStart(editLineHeightSpan) + i, this.mDeltaText.getSpanEnd(editLineHeightSpan) + i, this.mDeltaText.getSpanFlags(editLineHeightSpan));
        }
        int length = (this.mDeltaText.length() + i2) - (i2 - i);
        if (getRichEditor().getParagraphStart(text.toString(), i) == getRichEditor().getParagraphStart(text.toString(), length)) {
            int paragraphStart = getRichEditor().getParagraphStart(text.toString(), i);
            int paragraphEnd = getRichEditor().getParagraphEnd(text.toString(), i);
            adjustLineHeightSpanInParagraph(text, paragraphStart, paragraphEnd, paragraphStart, paragraphEnd, adjustFontSizeSpanInParagraph(text, paragraphStart, paragraphEnd, paragraphStart, paragraphEnd));
            if (paragraphStart < text.length() && paragraphStart >= 0 && text.charAt(paragraphStart) == 8204 && i != length) {
                getRichEditor().getRichTextWatcher().updateImageCheckBoxAndEditorBullet(text, paragraphStart, paragraphEnd, false, true, false);
            }
            richEditUndoOperation = this;
            i3 = i;
        } else {
            int paragraphStart2 = getRichEditor().getParagraphStart(text.toString(), i);
            int paragraphEnd2 = getRichEditor().getParagraphEnd(text.toString(), length);
            if (paragraphStart2 < text.length() && paragraphStart2 >= 0 && text.charAt(paragraphStart2) == 8204) {
                paragraphStart2++;
            }
            for (EditLineHeightSpan editLineHeightSpan2 : (EditLineHeightSpan[]) text.getSpans(paragraphStart2, paragraphEnd2, EditLineHeightSpan.class)) {
                int spanStart = text.getSpanStart(editLineHeightSpan2);
                int spanEnd = text.getSpanEnd(editLineHeightSpan2);
                text.getSpanFlags(editLineHeightSpan2);
                if (spanStart == paragraphStart2 && spanEnd == paragraphEnd2) {
                    text.removeSpan(editLineHeightSpan2);
                }
            }
            for (RelativeSizeSpan relativeSizeSpan : (RelativeSizeSpan[]) text.getSpans(paragraphStart2, paragraphEnd2, RelativeSizeSpan.class)) {
                int spanStart2 = text.getSpanStart(relativeSizeSpan);
                int spanEnd2 = text.getSpanEnd(relativeSizeSpan);
                text.getSpanFlags(relativeSizeSpan);
                if (spanStart2 == paragraphStart2 && spanEnd2 == paragraphEnd2) {
                    text.removeSpan(relativeSizeSpan);
                }
            }
            for (TypefaceSpan typefaceSpan : (TypefaceSpan[]) text.getSpans(paragraphStart2, paragraphEnd2, TypefaceSpan.class)) {
                int spanStart3 = text.getSpanStart(typefaceSpan);
                int spanEnd3 = text.getSpanEnd(typefaceSpan);
                text.getSpanFlags(typefaceSpan);
                if (spanStart3 == paragraphStart2 && spanEnd3 == paragraphEnd2) {
                    text.removeSpan(typefaceSpan);
                }
            }
            int paragraphStart3 = getRichEditor().getParagraphStart(text.toString(), i);
            int paragraphEnd3 = getRichEditor().getParagraphEnd(text.toString(), i);
            richEditUndoOperation = this;
            richEditUndoOperation.adjustLineHeightSpanInParagraph(text, paragraphStart3, paragraphEnd3, i, paragraphEnd3, richEditUndoOperation.adjustFontSizeSpanInParagraph(text, paragraphStart3, paragraphEnd3, i, paragraphEnd3));
            richEditUndoOperation.adjustListSpanInParagraph(text, paragraphStart3, paragraphEnd3, i, paragraphEnd3);
            richEditUndoOperation.adjustAlignSpanInParagraph(text, paragraphStart3, paragraphEnd3, i, paragraphEnd3);
            i3 = i;
            int paragraphStart4 = richEditUndoOperation.getRichEditor().getParagraphStart(text.toString(), length);
            int paragraphEnd4 = richEditUndoOperation.getRichEditor().getParagraphEnd(text.toString(), length);
            richEditUndoOperation.adjustLineHeightSpanInParagraph(text, paragraphStart4, paragraphEnd4, paragraphStart4, length, richEditUndoOperation.adjustFontSizeSpanInParagraph(text, paragraphStart4, paragraphEnd4, paragraphStart4, length));
            richEditUndoOperation.adjustListSpanInParagraph(text, paragraphStart4, paragraphEnd4, paragraphStart4, length);
            richEditUndoOperation.adjustAlignSpanInParagraph(text, paragraphStart4, paragraphEnd4, paragraphStart4, length);
        }
        int length2 = richEditUndoOperation.mDeltaText.length() + i3;
        if (length2 > 0 && richEditUndoOperation.getRichEditor().getText().length() >= 1) {
            int i4 = length2 - 1;
            if (richEditUndoOperation.getRichEditor().getText().subSequence(i4, length2) != System.lineSeparator()) {
                if (richEditUndoOperation.getRichEditor().isParagraphCentered(i4, length2)) {
                    if (c != 1) {
                        richEditUndoOperation.getRichEditor().centerWithPosition(i4, length2);
                    }
                } else if (richEditUndoOperation.getRichEditor().isParagraphRighted(i4, length2)) {
                    if (c != 2) {
                        richEditUndoOperation.getRichEditor().rightWithPosition(i4, length2);
                    }
                } else if (c != 0) {
                    richEditUndoOperation.getRichEditor().normalWithPosition(i4, length2);
                }
            }
        }
        SpannableStringBuilder spannableStringBuilder2 = richEditUndoOperation.mDeltaText;
        for (StyleSpan styleSpan : (StyleSpan[]) spannableStringBuilder2.getSpans(0, spannableStringBuilder2.length(), StyleSpan.class)) {
            SpanInfo spanInfo = richEditUndoOperation.getRichEditor().getSpanInfo(richEditUndoOperation.mDeltaText, styleSpan);
            richEditUndoOperation.getRichEditor().styleValid(styleSpan, spanInfo.getStart() + i3, spanInfo.getEnd() + i3);
        }
        SpannableStringBuilder spannableStringBuilder3 = richEditUndoOperation.mDeltaText;
        for (UnderlineSpan underlineSpan : (UnderlineSpan[]) spannableStringBuilder3.getSpans(0, spannableStringBuilder3.length(), UnderlineSpan.class)) {
            SpanInfo spanInfo2 = richEditUndoOperation.getRichEditor().getSpanInfo(richEditUndoOperation.mDeltaText, underlineSpan);
            richEditUndoOperation.getRichEditor().styleValid(underlineSpan, spanInfo2.getStart() + i3, spanInfo2.getEnd() + i3);
        }
        richEditUndoOperation.clearInvalidParagraphSpans(richEditUndoOperation.getRichEditor().getText());
        richEditUndoOperation.getRichEditor().setSkipRichText(false);
        richEditUndoOperation.getRichEditor().forceUpdateLayout();
        richEditUndoOperation.getRichEditor().restoreTextWatcher();
        richEditUndoOperation.getRichEditor().refreshExternalTextWatcher();
    }

    private int adjustFontSizeSpanInParagraph(Editable editable, int i, int i2, int i3, int i4) {
        boolean z = i < editable.length() && i >= 0 && editable.charAt(i) == 8204;
        RelativeSizeSpan[] relativeSizeSpanArr = (RelativeSizeSpan[]) editable.getSpans(i3, i4, RelativeSizeSpan.class);
        int i5 = 0;
        boolean z2 = false;
        for (int i6 = 0; i6 < relativeSizeSpanArr.length; i6++) {
            int spanStart = editable.getSpanStart(relativeSizeSpanArr[i6]);
            int spanEnd = editable.getSpanEnd(relativeSizeSpanArr[i6]);
            editable.getSpanFlags(relativeSizeSpanArr[i6]);
            if (spanStart >= i && spanEnd <= i2) {
                RelativeSizeSpan relativeSizeSpan = relativeSizeSpanArr[i6];
                if (relativeSizeSpan instanceof FontLargeSizeSpan) {
                    i5 = 2;
                } else if (relativeSizeSpan instanceof FontMidSizeSpan) {
                    i5 = 1;
                } else if (relativeSizeSpan instanceof FontNormalSizeSpan) {
                    i5 = 0;
                }
                editable.removeSpan(relativeSizeSpan);
                z2 = true;
            }
        }
        TypefaceSpan[] typefaceSpanArr = (TypefaceSpan[]) editable.getSpans(i3, i4, TypefaceSpan.class);
        for (int i7 = 0; i7 < typefaceSpanArr.length; i7++) {
            int spanStart2 = editable.getSpanStart(typefaceSpanArr[i7]);
            int spanEnd2 = editable.getSpanEnd(typefaceSpanArr[i7]);
            editable.getSpanFlags(typefaceSpanArr[i7]);
            if (spanStart2 >= i && spanEnd2 <= i2) {
                editable.removeSpan(typefaceSpanArr[i7]);
            }
        }
        if (z2) {
            if (z) {
                i++;
            }
            int i8 = i2 - 1;
            getRichEditor().getStyleFactory().setPlainFontSizeSpan(editable, i, i2, i5, (i8 >= editable.length() || i8 < 0 || editable.charAt(i8) != '\n') ? 34 : 33);
        }
        return i5;
    }

    private void adjustLineHeightSpanInParagraph(Editable editable, int i, int i2, int i3, int i4, int i5) {
        boolean z = i < editable.length() && i >= 0 && editable.charAt(i) == 8204;
        EditLineHeightSpan[] editLineHeightSpanArr = (EditLineHeightSpan[]) editable.getSpans(i3, i4, EditLineHeightSpan.class);
        for (int i6 = 0; i6 < editLineHeightSpanArr.length; i6++) {
            int spanStart = editable.getSpanStart(editLineHeightSpanArr[i6]);
            int spanEnd = editable.getSpanEnd(editLineHeightSpanArr[i6]);
            editable.getSpanFlags(editLineHeightSpanArr[i6]);
            if (spanStart >= i && spanEnd <= i2) {
                editable.removeSpan(editLineHeightSpanArr[i6]);
            }
        }
        if (z) {
            i++;
        }
        int i7 = i2 - 1;
        editable.setSpan(getRichEditor().getStyleFactory().createEditLineHeightSpan(i5), i, i2, (i7 >= editable.length() || i7 < 0 || editable.charAt(i7) != '\n') ? 34 : 33);
    }

    private void adjustListSpanInParagraph(Editable editable, int i, int i2, int i3, int i4) {
        CheckboxSpan[] checkboxSpanArr = (CheckboxSpan[]) editable.getSpans(i3, i4, CheckboxSpan.class);
        for (int i5 = 0; i5 < checkboxSpanArr.length; i5++) {
            int spanStart = editable.getSpanStart(checkboxSpanArr[i5]);
            int spanEnd = editable.getSpanEnd(checkboxSpanArr[i5]);
            int spanFlags = editable.getSpanFlags(checkboxSpanArr[i5]);
            if ((spanStart <= i && spanEnd > i && spanEnd <= i2) || (spanStart >= i && spanEnd > i2)) {
                editable.setSpan(checkboxSpanArr[i5], i, i2, spanFlags);
            }
        }
        EditorBulletSpan[] editorBulletSpanArr = (EditorBulletSpan[]) editable.getSpans(i3, i4, EditorBulletSpan.class);
        for (int i6 = 0; i6 < editorBulletSpanArr.length; i6++) {
            int spanStart2 = editable.getSpanStart(editorBulletSpanArr[i6]);
            int spanEnd2 = editable.getSpanEnd(editorBulletSpanArr[i6]);
            int spanFlags2 = editable.getSpanFlags(editorBulletSpanArr[i6]);
            if ((spanStart2 <= i && spanEnd2 > i && spanEnd2 <= i2) || (spanStart2 >= i && spanEnd2 > i2)) {
                editable.setSpan(editorBulletSpanArr[i6], i, i2, spanFlags2);
            }
        }
    }

    private void adjustAlignSpanInParagraph(Editable editable, int i, int i2, int i3, int i4) {
        AlignmentSpan[] alignmentSpanArr = (AlignmentSpan[]) editable.getSpans(i, i4, AlignmentSpan.class);
        Layout.Alignment alignment = null;
        if (alignmentSpanArr.length > 0) {
            for (AlignmentSpan alignmentSpan : alignmentSpanArr) {
                if (editable.getSpanStart(alignmentSpan) == i || editable.getSpanStart(alignmentSpan) == i4) {
                    alignment = alignmentSpan.getAlignment();
                }
                editable.removeSpan(alignmentSpan);
            }
        }
        if (alignment != null) {
            if (i2 >= 1) {
                int i5 = i2 - 1;
                if (editable.charAt(i5) == '\n') {
                    i2 = i5;
                }
            }
            if (i == 0 || (i >= 1 && editable.charAt(i - 1) != 8204)) {
                if (i == i2) {
                    editable.setSpan(new AlignmentSpan.Standard(alignment), i, i2, 18);
                } else {
                    editable.setSpan(new AlignmentSpan.Standard(alignment), i, i2, 17);
                }
            }
        }
    }

    @Override // com.miui.richeditor.UndoOperation
    public void redo() {
        this.mRichEditor.stopMediaPlayerIfNeed();
        int i = this.mRangeStart;
        if (getRichEditor().getText() == null) {
            Logger.INSTANCE.e("RichEditUndoOperation", "redo text =null ");
            return;
        }
        int length = getRichEditor().getText().length() - this.mRangeEnd;
        if (i < 0 || length < 0) {
            return;
        }
        if (length < i) {
            Logger.INSTANCE.e("RichEditUndoOperation", "redo error  start = " + i + " end = " + i);
            length = i;
        }
        SpannableStringBuilder subSequence = getRichEditor().getSubSequence(i, length);
        Logger.INSTANCE.i("RichEditUndoOperation", "redo, index:" + i + "-" + length);
        replace(i, length);
        if (this.mCursorEnd > getRichEditor().getText().length()) {
            this.mCursorEnd = getRichEditor().getText().length();
        }
        getRichEditor().setSelection(this.mCursorEnd);
        this.mDeltaText = subSequence;
        getRichEditor().setSkipRichText(false);
        getRichEditor().refreshContainer();
    }

    public void setIsComposing(boolean z) {
        this.mIsComposing = z;
    }

    public void track(int i, int i2, int i3) {
        int i4;
        if (!this.mIsComposing && this.mCounter == 0) {
            if (i2 > 0) {
                i4 = i3 > 0 ? 3 : 2;
            } else {
                i4 = 1;
            }
            int i5 = this.mMode;
            if (i5 == 0) {
                this.mRangeStart = i;
                int i6 = i + i2;
                this.mRangeEnd = getRichEditor().getText().length() - i6;
                this.mCursorStart = i6;
                this.mMode = i4;
                getRichEditor().getUndoManager().notifyStateChanged();
            } else if (i5 != i4 || i4 == 3 || ((i4 == 1 && i != getRichEditor().length() - this.mRangeEnd) || (i4 == 2 && i + i2 != getRichEditor().length() - this.mRangeEnd))) {
                getRichEditor().getUndoManager().commit();
                ((RichEditUndoOperation) getRichEditor().getUndoManager().getOperation()).track(i, i2, i3);
                return;
            } else {
                this.mRangeStart = Math.min(this.mRangeStart, i);
                this.mRangeEnd = Math.min(this.mRangeEnd, getRichEditor().getText().length() - (i + i2));
            }
        } else {
            this.mRangeStart = Math.min(this.mRangeStart, i);
            this.mRangeEnd = Math.min(this.mRangeEnd, getRichEditor().getText().length() - (i + i2));
        }
        this.mCounter++;
    }

    public void updateRange(int i, int i2) {
        this.mRangeStart = Math.min(this.mRangeStart, i);
        this.mRangeEnd = Math.min(this.mRangeEnd, i2);
    }

    public void untrack() {
        this.mCounter--;
    }

    @Override // com.miui.richeditor.UndoOperation
    public boolean hasData() {
        return this.mMode != 0;
    }

    @Override // com.miui.richeditor.UndoOperation
    public void commit() {
        getRichEditor().setSkipSelectionChanged(true);
        int i = this.mRangeStart;
        int length = this.mDeltaText.length() - this.mRangeEnd;
        if (i < 0 || length < 0) {
            return;
        }
        if (length < i) {
            Logger.INSTANCE.e("RichEditUndoOperation", "commit error  start = " + i + " end = " + i);
            length = i;
        }
        this.mDeltaText = (SpannableStringBuilder) this.mDeltaText.subSequence(i, length);
        getRichEditor().setSkipSelectionChanged(false);
        this.mCursorEnd = getRichEditor().getSelectionEnd();
    }

    protected void clearInvalidParagraphSpans(Editable editable) {
        for (EditorBulletSpan editorBulletSpan : (EditorBulletSpan[]) getRichEditor().sortSpans((EditorBulletSpan[]) editable.getSpans(0, editable.length(), EditorBulletSpan.class), editable)) {
            if (!editable.toString().startsWith(EditorUtils.ZERO_WIDTH_STRING, editable.getSpanStart(editorBulletSpan))) {
                editable.removeSpan(editorBulletSpan);
            }
        }
        for (CheckboxSpan checkboxSpan : (CheckboxSpan[]) getRichEditor().sortSpans((CheckboxSpan[]) editable.getSpans(0, editable.length(), CheckboxSpan.class), editable)) {
            if (!editable.toString().startsWith(EditorUtils.ZERO_WIDTH_STRING, editable.getSpanStart(checkboxSpan))) {
                editable.removeSpan(checkboxSpan);
            }
        }
        if (editable.length() > 0) {
            int iLastIndexOf = editable.toString().lastIndexOf(EditorUtils.ZERO_WIDTH_STRING, editable.length() - 1);
            while (iLastIndexOf >= 0) {
                if (getRichEditor().getSpanStartAt(editable, CheckboxSpan.class, iLastIndexOf) == null && getRichEditor().getSpanStartAt(editable, EditorBulletSpan.class, iLastIndexOf) == null) {
                    editable.delete(iLastIndexOf, EditorUtils.ZERO_WIDTH_STRING.length() + iLastIndexOf);
                }
                if (iLastIndexOf <= 0) {
                    return;
                } else {
                    iLastIndexOf = editable.toString().lastIndexOf(EditorUtils.ZERO_WIDTH_STRING, iLastIndexOf - 1);
                }
            }
        }
    }
}
