package com.miui.richeditor;

import android.text.Editable;
import android.text.Layout;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.text.style.AlignmentSpan;
import android.text.style.BulletSpan;
import android.text.style.ImageSpan;
import android.text.style.RelativeSizeSpan;
import android.text.style.StrikethroughSpan;
import android.text.style.StyleSpan;
import android.text.style.TypefaceSpan;
import android.text.style.UnderlineSpan;
import android.view.inputmethod.BaseInputConnection;
import com.google.gson.Gson;
import com.miui.notes.base.utils.Logger;
import com.miui.richeditor.constant.MiuiFontName;
import com.miui.richeditor.style.BaseAudioSpan;
import com.miui.richeditor.style.BaseImageSpan;
import com.miui.richeditor.style.BgHighLightSpan;
import com.miui.richeditor.style.CheckboxSpan;
import com.miui.richeditor.style.EditLineHeightSpan;
import com.miui.richeditor.style.EditorBulletSpan;
import com.miui.richeditor.style.FontLargeSizeSpan;
import com.miui.richeditor.style.FontMidSizeSpan;
import com.miui.richeditor.style.KeywordsSpan;
import com.miui.richeditor.style.NormalImageSpan;
import com.miui.richeditor.util.EditorUtils;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import org.apache.commons.lang3.StringUtils;

/* JADX INFO: loaded from: classes4.dex */
public class RichTextWatcher implements TextWatcher {
    private int mBack;
    private int mChangeEndOffset;
    private int mChangeStartLineStartIndexAfter;
    private int mChangeStartLineStartIndexBefore;
    private int mChangeStartOffset;
    protected boolean mDoDelete;
    protected boolean mDoParse;
    private boolean mDoReplaceCheckbox;
    protected boolean mDoReplaceEditorBullet;
    private boolean mDoReplacing;
    protected boolean mDoSetNull;
    private int mEditCursor;
    private int mFront;
    private boolean mIsInitialing;
    private int mOnChangeBeforeCount;
    private int mOnChangeCount;
    private int mOnChangeStartIndex;
    private int mRangeEndOffset;
    private int mRangeStartOffset;
    private RichEditTextView mRichEditor;
    private final String TAG = "RichTextWatcher";
    private final String MEDIA_LINE_CONTENT = "￼\n";
    private int mLastComposingStart = -1;
    private int mLastComposingEnd = -1;
    Gson mGson = new Gson();

    public RichTextWatcher(RichEditTextView richEditTextView) {
        this.mRichEditor = richEditTextView;
    }

    private RichEditTextView getEditor() {
        return this.mRichEditor;
    }

    @Override // android.text.TextWatcher
    public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {
        Logger.INSTANCE.d("RichTextWatcher", "beforeTextChanged");
        this.mDoDelete = i2 > 0 && i3 == 0;
        if (this.mDoReplacing || checkSkip(charSequence, i, i2, i3)) {
            return;
        }
        computeAffectedRange(charSequence, i, i2, i3);
        if (isUndoManagerSafe()) {
            ((RichEditUndoOperation) getEditor().getUndoManager().getOperation()).track(i, i2, i3);
        }
        if (this.mDoReplaceCheckbox) {
            Editable editable = (Editable) charSequence;
            int i4 = i + i2;
            for (CheckboxSpan checkboxSpan : (CheckboxSpan[]) editable.getSpans(i, i4, CheckboxSpan.class)) {
                int spanStart = editable.getSpanStart(checkboxSpan);
                int spanEnd = editable.getSpanEnd(checkboxSpan);
                if (spanStart >= i && spanEnd <= i4) {
                    editable.removeSpan(checkboxSpan);
                }
            }
            this.mDoReplaceCheckbox = false;
        }
        if (this.mDoReplaceEditorBullet) {
            Editable editable2 = (Editable) charSequence;
            int i5 = i2 + i;
            for (EditorBulletSpan editorBulletSpan : (EditorBulletSpan[]) editable2.getSpans(i, i5, EditorBulletSpan.class)) {
                int spanStart2 = editable2.getSpanStart(editorBulletSpan);
                int spanEnd2 = editable2.getSpanEnd(editorBulletSpan);
                if (spanStart2 >= i && spanEnd2 <= i5) {
                    editable2.removeSpan(editorBulletSpan);
                }
            }
            this.mDoReplaceEditorBullet = false;
        }
    }

    @Override // android.text.TextWatcher
    public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
        Logger.INSTANCE.d("RichTextWatcher", "onTextChanged");
        this.mOnChangeStartIndex = i;
        this.mOnChangeBeforeCount = i2;
        this.mOnChangeCount = i3;
        if ((getEditor().isFirstLoad() || getEditor().isSkipRichText()) && getEditor().isSkipRichText() && this.mDoParse) {
            getEditor().setSizeSpanInRange(i, i3 + i);
        }
    }

    @Override // android.text.TextWatcher
    public void afterTextChanged(Editable editable) {
        boolean z;
        int i;
        int i2;
        Editable editable2;
        Logger.INSTANCE.d("RichTextWatcher", "afterTextChanged mOnChangeStartIndex = " + this.mOnChangeStartIndex + " mOnChangeBeforeCount = " + this.mOnChangeBeforeCount + " mOnChangeCount = " + this.mOnChangeCount + " composStart = " + BaseInputConnection.getComposingSpanStart(editable) + " composEnd = " + BaseInputConnection.getComposingSpanEnd(editable));
        int i3 = this.mFront;
        int length = editable.length() - this.mBack;
        if (BaseInputConnection.getComposingSpanStart(editable) >= 0) {
            this.mLastComposingStart = BaseInputConnection.getComposingSpanStart(editable);
            this.mLastComposingEnd = BaseInputConnection.getComposingSpanEnd(editable);
            ((RichEditUndoOperation) getEditor().getUndoManager().getOperation()).setIsComposing(true);
            z = true;
        } else {
            int i4 = this.mOnChangeCount;
            if (i4 > 0 && (i = this.mOnChangeBeforeCount) > 0 && (i2 = this.mLastComposingStart) > 0) {
                this.mLastComposingEnd = i2 + Math.max(i, i4);
            }
            z = false;
        }
        if (!getEditor().isFirstLoad()) {
            if (this.mLastComposingStart >= 0 && !z) {
                processUncomposingStyle(editable);
            }
            processNewInputModeRichStyle(editable);
            processRichStyleSpanAfterChanged(editable);
        }
        ExternalTextWatcher externalTextWatcher = getEditor().getExternalTextWatcher();
        if (externalTextWatcher != null) {
            externalTextWatcher.afterTextChanged(editable);
        }
        boolean z2 = this.mDoSetNull;
        if (this.mDoReplacing || (this.mIsInitialing && !z2)) {
            this.mDoSetNull = false;
            return;
        }
        this.mDoSetNull = false;
        getEditor().getParent().requestLayout();
        getEditor().invalidateDeeply();
        for (StrikethroughSpan strikethroughSpan : (StrikethroughSpan[]) editable.getSpans(this.mChangeStartOffset, editable.length() - this.mChangeEndOffset, StrikethroughSpan.class)) {
            if (!(strikethroughSpan instanceof CheckboxSpan)) {
                editable.removeSpan(strikethroughSpan);
            }
        }
        for (BulletSpan bulletSpan : (BulletSpan[]) editable.getSpans(this.mChangeStartOffset, editable.length() - this.mChangeEndOffset, BulletSpan.class)) {
            if (!(bulletSpan instanceof EditorBulletSpan)) {
                editable.removeSpan(bulletSpan);
            }
        }
        boolean z3 = ((BaseImageSpan[]) editable.getSpans(i3, length, BaseImageSpan.class)).length > 0;
        boolean z4 = ((CheckboxSpan[]) editable.getSpans(i3, length, CheckboxSpan.class)).length > 0;
        boolean z5 = ((EditorBulletSpan[]) editable.getSpans(i3, length, EditorBulletSpan.class)).length > 0;
        if (z) {
            editable2 = editable;
        } else {
            ((RichEditUndoOperation) getEditor().getUndoManager().getOperation()).setIsComposing(false);
            if (z3 || z4 || z5) {
                editable2 = editable;
                updateImageCheckBoxAndEditorBullet(editable2, i3, length, z3, z4, z5);
                this = this;
            } else {
                trimPlainText(editable);
                editable2 = editable;
            }
        }
        if (((RelativeSizeSpan[]) editable2.getSpans(i3, editable2.length(), RelativeSizeSpan.class)).length == 0) {
            this.getEditor().getStyleFactory().setPlainFontSizeSpan(editable2, i3, editable2.length(), 0, 33);
        }
        if (this.isUndoManagerSafe()) {
            ((RichEditUndoOperation) this.getEditor().getUndoManager().getOperation()).updateRange(this.mRangeStartOffset, this.mRangeEndOffset);
            if (!z2) {
                ((RichEditUndoOperation) this.getEditor().getUndoManager().getOperation()).untrack();
            }
        }
        this.getEditor().getUndoManager().commit();
        this.getEditor().clearRichTextData();
        if (externalTextWatcher != null) {
            externalTextWatcher.afterTextActuallyChanged(editable2);
        }
    }

    private void processUncomposingStyle(Editable editable) {
        for (UnderlineSpan underlineSpan : (UnderlineSpan[]) editable.getSpans(this.mLastComposingStart, this.mLastComposingEnd, UnderlineSpan.class)) {
            editable.getSpanStart(underlineSpan);
            editable.getSpanEnd(underlineSpan);
            if ((editable.getSpanFlags(underlineSpan) & 256) == 256) {
                editable.removeSpan(underlineSpan);
            }
        }
        int i = this.mLastComposingStart;
        if (i - 1 >= 0) {
            for (CheckboxSpan checkboxSpan : (CheckboxSpan[]) editable.getSpans(i - 1, i, CheckboxSpan.class)) {
                int spanStart = editable.getSpanStart(checkboxSpan);
                if (editable.getSpanEnd(checkboxSpan) == this.mLastComposingStart) {
                    editable.setSpan(checkboxSpan, spanStart, this.mLastComposingEnd, 17);
                }
            }
            int i2 = this.mLastComposingStart;
            for (EditorBulletSpan editorBulletSpan : (EditorBulletSpan[]) editable.getSpans(i2 - 1, i2, EditorBulletSpan.class)) {
                int spanStart2 = editable.getSpanStart(editorBulletSpan);
                if (editable.getSpanEnd(editorBulletSpan) == this.mLastComposingStart) {
                    editable.setSpan(editorBulletSpan, spanStart2, this.mLastComposingEnd, 17);
                }
            }
        }
        this.mLastComposingStart = -1;
        this.mLastComposingEnd = -1;
    }

    private void processNewInputModeRichStyle(Editable editable) {
        if (this.mOnChangeCount <= 0 || !getEditor().getInputHelper().hasInputModeRichStyle()) {
            return;
        }
        changeSpanFromInputModeToNormalMode(editable, StyleSpan.class);
        changeSpanFromInputModeToNormalMode(editable, UnderlineSpan.class);
        changeSpanFromInputModeToNormalMode(editable, BgHighLightSpan.class);
        changeFontSizeSpanFromInputModeToNormalMode(editable, RelativeSizeSpan.class);
        changeSpanFromInputModeToNormalMode(editable, EditLineHeightSpan.class);
    }

    private <T> void changeSpanFromInputModeToNormalMode(Editable editable, Class<T> cls) {
        int i = this.mChangeStartOffset;
        Object[] spans = editable.getSpans(i, this.mOnChangeCount + i, cls);
        for (Object obj : spans) {
            int spanFlags = editable.getSpanFlags(obj);
            int spanStart = editable.getSpanStart(obj);
            int spanEnd = editable.getSpanEnd(obj);
            if ((spanFlags & 255) == 18) {
                editable.setSpan(obj, spanStart, spanEnd, 34);
            }
        }
    }

    private <T> void changeFontSizeSpanFromInputModeToNormalMode(Editable editable, Class<T> cls) {
        TypefaceSpan typefaceSpan;
        int i = this.mChangeStartOffset;
        for (Object obj : editable.getSpans(i, this.mOnChangeCount + i, cls)) {
            int spanFlags = editable.getSpanFlags(obj);
            int spanStart = editable.getSpanStart(obj);
            int spanEnd = editable.getSpanEnd(obj);
            if ((spanFlags & 18) == 18) {
                editable.setSpan(obj, spanStart, spanEnd, 34);
                try {
                    typefaceSpan = ((TypefaceSpan[]) editable.getSpans(spanStart, spanEnd, TypefaceSpan.class))[0];
                } catch (Exception e) {
                    Logger.INSTANCE.d("RichTextWatcher", e.toString());
                    typefaceSpan = null;
                }
                editable.setSpan(typefaceSpan, spanStart, spanEnd, 34);
            } else if ((spanFlags & 17) == 17) {
                editable.setSpan(obj, spanStart, spanEnd, 33);
                editable.setSpan(((TypefaceSpan[]) editable.getSpans(spanStart, spanEnd, TypefaceSpan.class))[0], spanStart, spanEnd, 33);
            }
        }
    }

    /* JADX WARN: Code duplicated, block: B:100:0x01c3  */
    /* JADX WARN: Code duplicated, block: B:108:0x01e4  */
    /* JADX WARN: Code duplicated, block: B:110:0x01e8  */
    /* JADX WARN: Code duplicated, block: B:111:0x01ea  */
    /* JADX WARN: Code duplicated, block: B:113:0x01ee  */
    /* JADX WARN: Code duplicated, block: B:114:0x01f0  */
    /* JADX WARN: Code duplicated, block: B:118:0x020c  */
    /* JADX WARN: Code duplicated, block: B:127:0x023f  */
    /* JADX WARN: Code duplicated, block: B:135:0x0267  */
    /* JADX WARN: Code duplicated, block: B:137:0x026c  */
    /* JADX WARN: Code duplicated, block: B:139:0x0271  */
    /* JADX WARN: Code duplicated, block: B:142:0x027a  */
    /* JADX WARN: Code duplicated, block: B:144:0x0282  */
    /* JADX WARN: Code duplicated, block: B:145:0x0287  */
    /* JADX WARN: Code duplicated, block: B:149:0x0295 A[ADDED_TO_REGION] */
    /* JADX WARN: Code duplicated, block: B:151:0x02a2  */
    /* JADX WARN: Code duplicated, block: B:155:0x02aa A[LOOP:6: B:153:0x02a7->B:155:0x02aa, LOOP_END] */
    /* JADX WARN: Code duplicated, block: B:159:0x02b7 A[LOOP:7: B:157:0x02b4->B:159:0x02b7, LOOP_END] */
    /* JADX WARN: Code duplicated, block: B:163:0x02ef  */
    /* JADX WARN: Code duplicated, block: B:168:0x030d  */
    /* JADX WARN: Code duplicated, block: B:173:0x033d  */
    /* JADX WARN: Code duplicated, block: B:174:0x033f  */
    /* JADX WARN: Code duplicated, block: B:177:0x0349  */
    /* JADX WARN: Code duplicated, block: B:180:0x035f  */
    /* JADX WARN: Code duplicated, block: B:182:0x0374  */
    /* JADX WARN: Code duplicated, block: B:183:0x0382  */
    /* JADX WARN: Code duplicated, block: B:185:0x0398  */
    /* JADX WARN: Code duplicated, block: B:189:0x039f  */
    /* JADX WARN: Code duplicated, block: B:197:0x03c0  */
    /* JADX WARN: Code duplicated, block: B:199:0x03d0  */
    /* JADX WARN: Code duplicated, block: B:201:0x03d6  */
    /* JADX WARN: Code duplicated, block: B:203:0x03de  */
    /* JADX WARN: Code duplicated, block: B:205:0x03e9  */
    /* JADX WARN: Code duplicated, block: B:207:0x03ec  */
    /* JADX WARN: Code duplicated, block: B:209:0x03f0  */
    /* JADX WARN: Code duplicated, block: B:216:0x0408 A[DONT_INVERT] */
    /* JADX WARN: Code duplicated, block: B:217:0x040a  */
    /* JADX WARN: Code duplicated, block: B:218:0x041b  */
    /* JADX WARN: Code duplicated, block: B:222:0x043b  */
    /* JADX WARN: Code duplicated, block: B:224:0x0440  */
    /* JADX WARN: Code duplicated, block: B:231:0x0454  */
    /* JADX WARN: Code duplicated, block: B:233:0x0457  */
    /* JADX WARN: Code duplicated, block: B:235:0x045b  */
    /* JADX WARN: Code duplicated, block: B:236:0x045d  */
    /* JADX WARN: Code duplicated, block: B:238:0x0461  */
    /* JADX WARN: Code duplicated, block: B:239:0x0463  */
    /* JADX WARN: Code duplicated, block: B:242:0x0491  */
    /* JADX WARN: Code duplicated, block: B:255:0x04d8  */
    /* JADX WARN: Code duplicated, block: B:259:0x04eb A[LOOP:15: B:258:0x04e9->B:259:0x04eb, LOOP_END] */
    /* JADX WARN: Code duplicated, block: B:262:0x04f8 A[LOOP:16: B:261:0x04f6->B:262:0x04f8, LOOP_END] */
    /* JADX WARN: Code duplicated, block: B:265:0x0504  */
    /* JADX WARN: Code duplicated, block: B:267:0x0512  */
    /* JADX WARN: Code duplicated, block: B:292:0x0595  */
    /* JADX WARN: Code duplicated, block: B:293:0x05aa  */
    /* JADX WARN: Code duplicated, block: B:296:0x05af  */
    /* JADX WARN: Code duplicated, block: B:298:0x05bd  */
    /* JADX WARN: Code duplicated, block: B:311:0x05ee  */
    /* JADX WARN: Code duplicated, block: B:312:0x05fe  */
    /* JADX WARN: Code duplicated, block: B:316:0x060a  */
    /* JADX WARN: Code duplicated, block: B:318:0x061f  */
    /* JADX WARN: Code duplicated, block: B:319:0x0668  */
    /* JADX WARN: Code duplicated, block: B:321:0x0670  */
    /* JADX WARN: Code duplicated, block: B:325:0x0119 A[SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:352:0x0315 A[SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:354:0x030b A[SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:365:0x03e2 A[SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:374:0x0454 A[SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:383:0x0602 A[SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:397:0x0682 A[SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:398:? A[RETURN, SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:39:0x007b  */
    /* JADX WARN: Code duplicated, block: B:41:0x0098 A[DONT_INVERT] */
    /* JADX WARN: Code duplicated, block: B:42:0x009a  */
    /* JADX WARN: Code duplicated, block: B:44:0x009e  */
    /* JADX WARN: Code duplicated, block: B:45:0x00a1  */
    /* JADX WARN: Code duplicated, block: B:48:0x00a8  */
    /* JADX WARN: Code duplicated, block: B:49:0x00ac  */
    /* JADX WARN: Code duplicated, block: B:53:0x00b6  */
    /* JADX WARN: Code duplicated, block: B:60:0x00d5  */
    /* JADX WARN: Code duplicated, block: B:63:0x00d9  */
    /* JADX WARN: Code duplicated, block: B:69:0x0119 A[EDGE_INSN: B:69:0x0119->B:70:0x011c BREAK  A[LOOP:0: B:61:0x00d6->B:68:0x0116]] */
    /* JADX WARN: Code duplicated, block: B:84:0x0170 A[EDGE_INSN: B:84:0x0170->B:85:0x0171 BREAK  A[LOOP:1: B:74:0x012d->B:83:0x016d]] */
    /* JADX WARN: Code duplicated, block: B:91:0x0191  */
    /* JADX WARN: Instruction removed from duplicated block: B:199:0x03d0, please report this as an issue */
    /* JADX WARN: Instruction removed from duplicated block: B:296:0x05af, please report this as an issue */
    protected void processRichStyleSpanAfterChanged(Editable editable) {
        boolean z;
        int i;
        String string;
        ArrayList arrayList;
        int iIndexOf;
        int i2;
        int i3;
        boolean z2;
        int[] iArr;
        int[] iArr2;
        int i4;
        int i5;
        Object[] objArr;
        Object obj;
        int i6;
        Object[] objArr2;
        int length;
        int i7;
        int[] iArr3;
        int i8;
        int i9;
        int i10;
        int i11;
        String string2;
        int i12;
        int i13;
        boolean z3;
        int i14;
        int i15;
        Object obj2;
        int spanStart;
        int i16;
        int length2;
        int i17;
        Object obj3;
        AlignmentSpan[] alignmentSpanArr;
        Layout.Alignment alignment;
        int i18;
        int i19;
        int i20;
        int i21;
        int i22;
        int length3;
        int i23;
        int i24;
        int i25;
        int i26;
        int i27;
        int i28;
        Object[] objArr3;
        int i29;
        Object obj4;
        Object[] objArr4;
        Object obj5;
        int spanStart2;
        Object[] objArr5;
        int i30;
        Object obj6;
        Object[] objArr6;
        int i31;
        int i32;
        int spanEnd;
        Object[] objArr7;
        int i33;
        Object obj7;
        Object[] objArr8;
        int i34;
        Object obj8;
        int i35;
        int i36;
        int iIndexOf2;
        int i37;
        int i38;
        int i39;
        int paragraphStart = getEditor().getParagraphStart(editable.toString(), this.mOnChangeStartIndex);
        int paragraphEnd = getEditor().getParagraphEnd(editable.toString(), this.mOnChangeStartIndex);
        int i40 = this.mOnChangeBeforeCount;
        boolean z4 = i40 > 0 && this.mOnChangeCount == 0;
        int i41 = this.mOnChangeCount;
        boolean z5 = i41 > 0 && i40 == 0;
        boolean z6 = i41 > 0 && i40 > 0;
        if (this.mOnChangeStartIndex < editable.length()) {
            z = editable.charAt(this.mOnChangeStartIndex) == '\n' && this.mOnChangeCount == 1;
            if (z) {
                i = this.mOnChangeStartIndex;
            } else if (z6) {
                int i42 = this.mOnChangeCount;
                int i43 = this.mOnChangeBeforeCount;
                if (i42 - i43 == 1) {
                    z = editable.charAt(this.mOnChangeStartIndex + i43) == '\n';
                    if (z) {
                        i = this.mOnChangeStartIndex + this.mOnChangeBeforeCount;
                    }
                }
            }
            if (z) {
                splitAlignSpanAfterLinebreak(editable, i);
                splitLineStyleSpanAfterLinebreak(editable, i);
                splitRichStyleSpanAfterLinebreak(editable, i, StyleSpan.class);
                splitRichStyleSpanAfterLinebreak(editable, i, UnderlineSpan.class);
                splitRichStyleSpanAfterLinebreak(editable, i, BgHighLightSpan.class);
                getEditor().refreshContainer();
                return;
            }
            if (z4) {
                i26 = this.mOnChangeStartIndex;
                if (i26 > 0) {
                    i27 = i26 - 1;
                } else {
                    i27 = i26;
                }
                if (i26 < editable.length()) {
                    i28 = this.mOnChangeStartIndex + 1;
                } else {
                    i28 = this.mOnChangeStartIndex;
                }
                if (editable.length() == 0) {
                    return;
                }
                if (editable.length() > i27 && editable.charAt(i27) == '\n') {
                    i27 = this.mOnChangeStartIndex;
                }
                objArr3 = (EditLineHeightSpan[]) editable.getSpans(i27, this.mOnChangeStartIndex, EditLineHeightSpan.class);
                if (objArr3.length > 0) {
                    i29 = 0;
                    obj4 = null;
                    break;
                }
                i39 = 0;
                while (true) {
                    if (i39 < objArr3.length) {
                        i29 = 0;
                        obj4 = null;
                        break;
                    }
                    i29 = 0;
                    if (editable.getSpanStart(objArr3[i39]) >= this.mOnChangeStartIndex && editable.getSpanEnd(objArr3[i39]) == this.mOnChangeStartIndex) {
                        obj4 = objArr3[i39];
                        Logger.INSTANCE.d("RichTextWatcher", "isDelete beforeSpan start " + editable.getSpanStart(obj4) + " end " + editable.getSpanEnd(obj4));
                        break;
                    }
                    i39++;
                }
                objArr4 = (EditLineHeightSpan[]) editable.getSpans(this.mOnChangeStartIndex, i28, EditLineHeightSpan.class);
                if (objArr4.length > 0 || obj4 == null) {
                    obj5 = null;
                    break;
                }
                int i44 = i29;
                while (true) {
                    if (i44 >= objArr4.length) {
                        obj5 = null;
                        break;
                    }
                    Object obj9 = objArr4[i44];
                    if (obj9 != obj4 && editable.getSpanStart(obj9) == this.mOnChangeStartIndex && editable.getSpanEnd(objArr4[i44]) >= this.mOnChangeStartIndex) {
                        obj5 = objArr4[i44];
                        Logger.INSTANCE.d("RichTextWatcher", "isDelete afterSpan start " + editable.getSpanStart(obj5) + " end " + editable.getSpanEnd(obj5));
                        break;
                    }
                    i44++;
                }
                if (obj4 != null && obj5 != null && obj4 != obj5) {
                    spanStart2 = editable.getSpanStart(obj4);
                    objArr5 = (RelativeSizeSpan[]) editable.getSpans(editable.getSpanStart(obj4), editable.getSpanEnd(obj4), RelativeSizeSpan.class);
                    obj6 = null;
                    for (i30 = i29; i30 < objArr5.length; i30++) {
                        if (editable.getSpanStart(objArr5[i30]) < editable.getSpanStart(obj4) && editable.getSpanEnd(objArr5[i30]) == editable.getSpanEnd(obj4)) {
                            obj6 = objArr5[i30];
                        }
                    }
                    objArr6 = (TypefaceSpan[]) editable.getSpans(editable.getSpanStart(obj4), editable.getSpanEnd(obj4), TypefaceSpan.class);
                    for (i31 = i29; i31 < objArr6.length; i31++) {
                        if (editable.getSpanStart(objArr6[i31]) < editable.getSpanStart(obj4) && editable.getSpanEnd(objArr6[i31]) == editable.getSpanEnd(obj4)) {
                            Object obj10 = objArr6[i31];
                        }
                    }
                    if (objArr5.length <= 0) {
                        i32 = i29;
                    } else if (obj6 instanceof FontLargeSizeSpan) {
                        i32 = 2;
                    } else if (obj6 instanceof FontMidSizeSpan) {
                        i32 = 1;
                    } else {
                        i32 = i29;
                    }
                    spanEnd = editable.getSpanEnd(obj5);
                    objArr7 = (RelativeSizeSpan[]) editable.getSpans(editable.getSpanStart(obj5), editable.getSpanEnd(obj5), RelativeSizeSpan.class);
                    obj7 = null;
                    for (i33 = i29; i33 < objArr7.length; i33++) {
                        if (editable.getSpanStart(objArr7[i33]) != editable.getSpanStart(obj5) && editable.getSpanEnd(objArr7[i33]) == editable.getSpanEnd(obj5)) {
                            obj7 = objArr7[i33];
                        }
                    }
                    objArr8 = (TypefaceSpan[]) editable.getSpans(editable.getSpanStart(obj5), editable.getSpanEnd(obj5), TypefaceSpan.class);
                    i34 = i29;
                    obj8 = null;
                    while (i34 < objArr8.length) {
                        Object[] objArr9 = objArr8;
                        if (editable.getSpanStart(objArr8[i34]) != editable.getSpanStart(obj5) && editable.getSpanEnd(objArr9[i34]) == editable.getSpanEnd(obj5)) {
                            obj8 = objArr9[i34];
                        }
                        i34++;
                        objArr8 = objArr9;
                    }
                    if (paragraphStart < editable.getSpanStart(obj5)) {
                        editable.removeSpan(obj5);
                        if (obj7 != null) {
                            editable.removeSpan(obj7);
                        }
                        if (obj8 != null) {
                            editable.removeSpan(obj8);
                        }
                        if (spanEnd < editable.length()) {
                            i35 = 10;
                            if (editable.charAt(spanEnd) == '\n') {
                                spanEnd++;
                                i36 = 33;
                            }
                            iIndexOf2 = editable.toString().indexOf(i35, spanStart2);
                            if (iIndexOf2 >= spanEnd && iIndexOf2 >= spanStart2) {
                                editable.setSpan(obj4, spanStart2, iIndexOf2, 33);
                                editable.setSpan(obj5, iIndexOf2, spanEnd, 34);
                            } else {
                                editable.setSpan(obj4, spanStart2, spanEnd, i36);
                            }
                            for (i37 = i29; i37 < objArr7.length; i37++) {
                                editable.removeSpan(objArr7[i37]);
                            }
                            for (i38 = i29; i38 < objArr5.length; i38++) {
                                editable.removeSpan(objArr5[i38]);
                            }
                            getEditor().getStyleFactory().setPlainFontSizeSpan(editable, paragraphStart, paragraphEnd, i32);
                        } else {
                            i35 = 10;
                        }
                        i36 = 34;
                        iIndexOf2 = editable.toString().indexOf(i35, spanStart2);
                        if (iIndexOf2 >= spanEnd) {
                            editable.setSpan(obj4, spanStart2, spanEnd, i36);
                        } else {
                            editable.setSpan(obj4, spanStart2, spanEnd, i36);
                        }
                        while (i37 < objArr7.length) {
                            editable.removeSpan(objArr7[i37]);
                        }
                        while (i38 < objArr5.length) {
                            editable.removeSpan(objArr5[i38]);
                        }
                        getEditor().getStyleFactory().setPlainFontSizeSpan(editable, paragraphStart, paragraphEnd, i32);
                    }
                }
                mergeOrDeleteAlignSpanAfterDelete(editable, this.mOnChangeStartIndex);
                mergeOrDeleteRichStyleSpanAfterDelete(editable, this.mOnChangeStartIndex, StyleSpan.class, 1);
                mergeOrDeleteRichStyleSpanAfterDelete(editable, this.mOnChangeStartIndex, StyleSpan.class, 2);
                mergeOrDeleteRichStyleSpanAfterDelete(editable, this.mOnChangeStartIndex, UnderlineSpan.class, -1);
                mergeOrDeleteRichStyleSpanAfterDelete(editable, this.mOnChangeStartIndex, BgHighLightSpan.class, -1);
                return;
            }
            if (!z5 || z6) {
                int i45 = this.mChangeStartOffset;
                string = editable.subSequence(i45, this.mOnChangeCount + i45).toString();
                arrayList = new ArrayList();
                iIndexOf = 0;
                i2 = 1;
                while (iIndexOf >= 0) {
                    iIndexOf = string.indexOf(10, iIndexOf);
                    if (iIndexOf >= 0) {
                        arrayList.add(Integer.valueOf(this.mChangeStartOffset + iIndexOf));
                        iIndexOf++;
                        i2++;
                    }
                }
                Logger.INSTANCE.d("AlignSpan", " lineCount " + i2);
                i3 = 1;
                if (i2 <= 1) {
                    z2 = true;
                } else {
                    z2 = false;
                }
                iArr = new int[i2];
                iArr2 = new int[i2];
                iArr[0] = paragraphStart;
                if (!z2) {
                    iArr2[0] = ((Integer) arrayList.get(0)).intValue() + 1;
                    i24 = 0;
                    while (i24 < arrayList.size()) {
                        i25 = i24 + 1;
                        iArr[i25] = ((Integer) arrayList.get(i24)).intValue() + i3;
                        if (i25 < arrayList.size()) {
                            iArr2[i25] = ((Integer) arrayList.get(i25)).intValue() + i3;
                        } else {
                            iArr2[i25] = getEditor().getParagraphEnd(editable.toString(), this.mOnChangeStartIndex + this.mOnChangeCount);
                        }
                        i24 = i25;
                        i3 = 1;
                    }
                } else {
                    iArr2[0] = paragraphEnd;
                }
                for (i4 = 0; i4 < i2; i4++) {
                    length3 = editable.length();
                    i23 = iArr[i4];
                    if (length3 <= i23 && editable.charAt(i23) == 8204) {
                        iArr[i4] = iArr[i4] + 1;
                    }
                }
                i5 = iArr[0];
                if (!z2) {
                    alignmentSpanArr = (AlignmentSpan[]) editable.getSpans(paragraphStart, this.mOnChangeStartIndex + this.mOnChangeCount, AlignmentSpan.class);
                    if (alignmentSpanArr.length > 0) {
                        alignment = null;
                        for (AlignmentSpan alignmentSpan : alignmentSpanArr) {
                            if (editable.getSpanStart(alignmentSpan) == i5) {
                                alignment = alignmentSpan.getAlignment();
                            }
                            editable.removeSpan(alignmentSpan);
                        }
                    } else {
                        alignment = null;
                    }
                    if (alignment != null) {
                        for (i18 = 0; i18 < i2; i18++) {
                            i19 = iArr[i18];
                            i20 = iArr2[i18] - 1;
                            if (i19 != 0 || (i19 >= 1 && editable.length() > (i21 = i19 - 1) && editable.charAt(i21) != 8204)) {
                                if (i19 == i20) {
                                    editable.setSpan(new AlignmentSpan.Standard(alignment), iArr[i18], iArr2[i18] - 1, 18);
                                } else {
                                    editable.setSpan(new AlignmentSpan.Standard(alignment), iArr[i18], iArr2[i18] - 1, 17);
                                }
                            }
                        }
                    }
                }
                objArr = (RelativeSizeSpan[]) editable.getSpans(paragraphStart, paragraphEnd, RelativeSizeSpan.class);
                if (objArr.length > 0) {
                    length2 = objArr.length;
                    i17 = 0;
                    while (true) {
                        if (i17 < length2) {
                            obj3 = objArr[i17];
                            if (editable.getSpanStart(obj3) != i5 || editable.getSpanStart(obj3) == paragraphStart) {
                                obj = obj3;
                            } else {
                                i17++;
                            }
                        } else {
                            obj = null;
                        }
                    }
                } else {
                    obj = null;
                }
                if (obj == null) {
                    i6 = 0;
                } else if (obj instanceof FontLargeSizeSpan) {
                    i6 = 2;
                } else if (obj instanceof FontMidSizeSpan) {
                    i6 = 1;
                } else {
                    i6 = 0;
                }
                int i46 = this.mChangeStartOffset;
                objArr2 = (EditLineHeightSpan[]) editable.getSpans(i46, this.mOnChangeCount + i46, EditLineHeightSpan.class);
                int i47 = this.mChangeStartOffset;
                Object[] objArr10 = (RelativeSizeSpan[]) editable.getSpans(i47, this.mOnChangeCount + i47, RelativeSizeSpan.class);
                int i48 = this.mChangeStartOffset;
                Object[] objArr11 = (TypefaceSpan[]) editable.getSpans(i48, this.mOnChangeCount + i48, TypefaceSpan.class);
                length = objArr2.length;
                i7 = 0;
                while (i7 < length) {
                    obj2 = objArr2[i7];
                    spanStart = editable.getSpanStart(obj2);
                    int spanEnd2 = editable.getSpanEnd(obj2);
                    Object[] objArr12 = objArr2;
                    int spanFlags = editable.getSpanFlags(obj2);
                    int[] iArr4 = iArr;
                    i16 = this.mChangeStartOffset;
                    if (spanStart >= i16 && spanEnd2 > i16) {
                        char c = '\n';
                        if (editable.charAt(i16) != '\n') {
                            int i49 = this.mChangeStartOffset;
                            if (i49 >= 1) {
                                c = '\n';
                                if (editable.charAt(i49 - 1) == '\n') {
                                }
                            }
                            editable.removeSpan(obj2);
                        }
                        if (editable.charAt(this.mChangeStartOffset) == c) {
                            editable.setSpan(obj2, spanStart, this.mChangeStartOffset + 1, spanFlags);
                        } else {
                            editable.setSpan(obj2, spanStart, this.mChangeStartOffset, spanFlags);
                        }
                    } else {
                        editable.removeSpan(obj2);
                    }
                    i7++;
                    objArr2 = objArr12;
                    iArr = iArr4;
                }
                iArr3 = iArr;
                for (Object obj11 : objArr10) {
                    editable.removeSpan(obj11);
                }
                for (Object obj12 : objArr11) {
                    editable.removeSpan(obj12);
                }
                for (i10 = 0; i10 < i2; i10++) {
                    i12 = iArr3[i10];
                    i13 = iArr2[i10];
                    if (!getEditor().isNeedEditorLineHeight()) {
                        i15 = i13 - 1;
                        if (editable.length() <= i15 && editable.charAt(i15) == '\n') {
                            Object[] objArr13 = (EditLineHeightSpan[]) editable.getSpans(i12, i13, EditLineHeightSpan.class);
                            if (objArr13.length > 0) {
                                boolean z7 = false;
                                boolean z8 = false;
                                for (int i50 = 0; i50 < objArr13.length; i50++) {
                                    if (editable.getSpanStart(objArr13[i50]) == i13 && editable.getSpanEnd(objArr13[i50]) == i13) {
                                        if (!z8) {
                                            editable.setSpan(objArr13[i50], i12, i13, 33);
                                            z8 = true;
                                        } else {
                                            editable.removeSpan(objArr13[i50]);
                                        }
                                    } else if (!z7) {
                                        editable.setSpan(objArr13[i50], i12, i13, 33);
                                        z7 = true;
                                    } else {
                                        editable.removeSpan(objArr13[i50]);
                                    }
                                }
                            } else if (i13 - i12 <= 1) {
                                editable.setSpan(getEditor().getStyleFactory().createEditLineHeightSpan(i6), i12, i13, 18);
                            } else {
                                editable.setSpan(getEditor().getStyleFactory().createEditLineHeightSpan(i6), i12, i13, 33);
                            }
                        } else {
                            editable.setSpan(getEditor().getStyleFactory().createEditLineHeightSpan(i6), i12, i13, 34);
                        }
                    }
                    if (i6 != 0) {
                        z3 = false;
                        for (Object obj13 : (RelativeSizeSpan[]) editable.getSpans(paragraphStart, paragraphEnd, RelativeSizeSpan.class)) {
                            if (editable.getSpanEnd(obj13) == this.mChangeStartOffset && ((editable.getSpanFlags(obj13) & 255) == 34 || (editable.getSpanFlags(obj13) & 255) == 18)) {
                                z3 = true;
                            }
                        }
                        if (!z3) {
                            getEditor().getStyleFactory().setPlainFontSizeSpan(editable, iArr3[i10], iArr2[i10], i6);
                        }
                    }
                }
                for (i11 = 0; i11 < i2; i11++) {
                    string2 = editable.subSequence(iArr3[i11], iArr2[i11]).toString();
                    if ("￼\n".equals(string2)) {
                        clearRichStyleInMediaLine(editable, iArr3[i11], iArr2[i11], StyleSpan.class);
                        clearRichStyleInMediaLine(editable, iArr3[i11], iArr2[i11], UnderlineSpan.class);
                        clearRichStyleInMediaLine(editable, iArr3[i11], iArr2[i11], BgHighLightSpan.class);
                        clearRichStyleInMediaLine(editable, iArr3[i11], iArr2[i11], EditLineHeightSpan.class);
                        clearRichStyleInMediaLine(editable, iArr3[i11], iArr2[i11], RelativeSizeSpan.class);
                        clearRichStyleInMediaLine(editable, iArr3[i11], iArr2[i11], TypefaceSpan.class);
                        adjustParagraphStyleInMediaLine(editable, iArr3[i11], iArr2[i11], CheckboxSpan.class);
                        adjustParagraphStyleInMediaLine(editable, iArr3[i11], iArr2[i11], EditorBulletSpan.class);
                    } else if (StringUtils.LF.equals(string2)) {
                        adjustParagraphStyleInMediaLine(editable, iArr3[i11], iArr2[i11], CheckboxSpan.class);
                        adjustParagraphStyleInMediaLine(editable, iArr3[i11], iArr2[i11], EditorBulletSpan.class);
                    }
                }
            }
            return;
        }
        z = false;
        i = 0;
        if (z) {
            splitAlignSpanAfterLinebreak(editable, i);
            splitLineStyleSpanAfterLinebreak(editable, i);
            splitRichStyleSpanAfterLinebreak(editable, i, StyleSpan.class);
            splitRichStyleSpanAfterLinebreak(editable, i, UnderlineSpan.class);
            splitRichStyleSpanAfterLinebreak(editable, i, BgHighLightSpan.class);
            getEditor().refreshContainer();
            return;
        }
        if (z4) {
            i26 = this.mOnChangeStartIndex;
            if (i26 > 0) {
                i27 = i26 - 1;
            } else {
                i27 = i26;
            }
            if (i26 < editable.length()) {
                i28 = this.mOnChangeStartIndex + 1;
            } else {
                i28 = this.mOnChangeStartIndex;
            }
            if (editable.length() == 0) {
                return;
            }
            if (editable.length() > i27) {
                i27 = this.mOnChangeStartIndex;
            }
            objArr3 = (EditLineHeightSpan[]) editable.getSpans(i27, this.mOnChangeStartIndex, EditLineHeightSpan.class);
            if (objArr3.length > 0) {
                i29 = 0;
                obj4 = null;
                break;
            }
            i39 = 0;
            while (true) {
                if (i39 < objArr3.length) {
                    i29 = 0;
                    obj4 = null;
                    break;
                } else {
                    i29 = 0;
                    if (editable.getSpanStart(objArr3[i39]) >= this.mOnChangeStartIndex) {
                    }
                    i39++;
                }
            }
            objArr4 = (EditLineHeightSpan[]) editable.getSpans(this.mOnChangeStartIndex, i28, EditLineHeightSpan.class);
            if (objArr4.length > 0) {
                obj5 = null;
                break;
            } else {
                obj5 = null;
                break;
            }
            if (obj4 != null) {
                spanStart2 = editable.getSpanStart(obj4);
                objArr5 = (RelativeSizeSpan[]) editable.getSpans(editable.getSpanStart(obj4), editable.getSpanEnd(obj4), RelativeSizeSpan.class);
                obj6 = null;
                while (i30 < objArr5.length) {
                    if (editable.getSpanStart(objArr5[i30]) < editable.getSpanStart(obj4)) {
                    }
                }
                objArr6 = (TypefaceSpan[]) editable.getSpans(editable.getSpanStart(obj4), editable.getSpanEnd(obj4), TypefaceSpan.class);
                while (i31 < objArr6.length) {
                    if (editable.getSpanStart(objArr6[i31]) < editable.getSpanStart(obj4)) {
                    }
                }
                if (objArr5.length <= 0) {
                    i32 = i29;
                } else if (obj6 instanceof FontLargeSizeSpan) {
                    i32 = 2;
                } else if (obj6 instanceof FontMidSizeSpan) {
                    i32 = 1;
                } else {
                    i32 = i29;
                }
                spanEnd = editable.getSpanEnd(obj5);
                objArr7 = (RelativeSizeSpan[]) editable.getSpans(editable.getSpanStart(obj5), editable.getSpanEnd(obj5), RelativeSizeSpan.class);
                obj7 = null;
                while (i33 < objArr7.length) {
                    if (editable.getSpanStart(objArr7[i33]) != editable.getSpanStart(obj5)) {
                    }
                }
                objArr8 = (TypefaceSpan[]) editable.getSpans(editable.getSpanStart(obj5), editable.getSpanEnd(obj5), TypefaceSpan.class);
                i34 = i29;
                obj8 = null;
                while (i34 < objArr8.length) {
                    Object[] objArr14 = objArr8;
                    if (editable.getSpanStart(objArr8[i34]) != editable.getSpanStart(obj5)) {
                    }
                    i34++;
                    objArr8 = objArr14;
                }
                if (paragraphStart < editable.getSpanStart(obj5)) {
                    editable.removeSpan(obj5);
                    if (obj7 != null) {
                        editable.removeSpan(obj7);
                    }
                    if (obj8 != null) {
                        editable.removeSpan(obj8);
                    }
                    if (spanEnd < editable.length()) {
                        i35 = 10;
                        if (editable.charAt(spanEnd) == '\n') {
                            spanEnd++;
                            i36 = 33;
                        }
                        iIndexOf2 = editable.toString().indexOf(i35, spanStart2);
                        if (iIndexOf2 >= spanEnd) {
                            editable.setSpan(obj4, spanStart2, spanEnd, i36);
                        } else {
                            editable.setSpan(obj4, spanStart2, spanEnd, i36);
                        }
                        while (i37 < objArr7.length) {
                            editable.removeSpan(objArr7[i37]);
                        }
                        while (i38 < objArr5.length) {
                            editable.removeSpan(objArr5[i38]);
                        }
                        getEditor().getStyleFactory().setPlainFontSizeSpan(editable, paragraphStart, paragraphEnd, i32);
                    } else {
                        i35 = 10;
                    }
                    i36 = 34;
                    iIndexOf2 = editable.toString().indexOf(i35, spanStart2);
                    if (iIndexOf2 >= spanEnd) {
                        editable.setSpan(obj4, spanStart2, spanEnd, i36);
                    } else {
                        editable.setSpan(obj4, spanStart2, spanEnd, i36);
                    }
                    while (i37 < objArr7.length) {
                        editable.removeSpan(objArr7[i37]);
                    }
                    while (i38 < objArr5.length) {
                        editable.removeSpan(objArr5[i38]);
                    }
                    getEditor().getStyleFactory().setPlainFontSizeSpan(editable, paragraphStart, paragraphEnd, i32);
                }
            }
            mergeOrDeleteAlignSpanAfterDelete(editable, this.mOnChangeStartIndex);
            mergeOrDeleteRichStyleSpanAfterDelete(editable, this.mOnChangeStartIndex, StyleSpan.class, 1);
            mergeOrDeleteRichStyleSpanAfterDelete(editable, this.mOnChangeStartIndex, StyleSpan.class, 2);
            mergeOrDeleteRichStyleSpanAfterDelete(editable, this.mOnChangeStartIndex, UnderlineSpan.class, -1);
            mergeOrDeleteRichStyleSpanAfterDelete(editable, this.mOnChangeStartIndex, BgHighLightSpan.class, -1);
            return;
        }
        if (z5) {
        }
        int i410 = this.mChangeStartOffset;
        string = editable.subSequence(i410, this.mOnChangeCount + i410).toString();
        arrayList = new ArrayList();
        iIndexOf = 0;
        i2 = 1;
        while (iIndexOf >= 0) {
            iIndexOf = string.indexOf(10, iIndexOf);
            if (iIndexOf >= 0) {
                arrayList.add(Integer.valueOf(this.mChangeStartOffset + iIndexOf));
                iIndexOf++;
                i2++;
            }
        }
        Logger.INSTANCE.d("AlignSpan", " lineCount " + i2);
        i3 = 1;
        if (i2 <= 1) {
            z2 = true;
        } else {
            z2 = false;
        }
        iArr = new int[i2];
        iArr2 = new int[i2];
        iArr[0] = paragraphStart;
        if (!z2) {
            iArr2[0] = ((Integer) arrayList.get(0)).intValue() + 1;
            i24 = 0;
            while (i24 < arrayList.size()) {
                i25 = i24 + 1;
                iArr[i25] = ((Integer) arrayList.get(i24)).intValue() + i3;
                if (i25 < arrayList.size()) {
                    iArr2[i25] = ((Integer) arrayList.get(i25)).intValue() + i3;
                } else {
                    iArr2[i25] = getEditor().getParagraphEnd(editable.toString(), this.mOnChangeStartIndex + this.mOnChangeCount);
                }
                i24 = i25;
                i3 = 1;
            }
        } else {
            iArr2[0] = paragraphEnd;
        }
        while (i4 < i2) {
            length3 = editable.length();
            i23 = iArr[i4];
            if (length3 <= i23) {
            }
        }
        i5 = iArr[0];
        if (!z2) {
            alignmentSpanArr = (AlignmentSpan[]) editable.getSpans(paragraphStart, this.mOnChangeStartIndex + this.mOnChangeCount, AlignmentSpan.class);
            if (alignmentSpanArr.length > 0) {
                alignment = null;
                while (i22 < r12) {
                    if (editable.getSpanStart(alignmentSpan) == i5) {
                        alignment = alignmentSpan.getAlignment();
                    }
                    editable.removeSpan(alignmentSpan);
                }
            } else {
                alignment = null;
            }
            if (alignment != null) {
                while (i18 < i2) {
                    i19 = iArr[i18];
                    i20 = iArr2[i18] - 1;
                    if (i19 != 0) {
                        if (i19 == i20) {
                            editable.setSpan(new AlignmentSpan.Standard(alignment), iArr[i18], iArr2[i18] - 1, 18);
                        } else {
                            editable.setSpan(new AlignmentSpan.Standard(alignment), iArr[i18], iArr2[i18] - 1, 17);
                        }
                    } else if (i19 == i20) {
                        editable.setSpan(new AlignmentSpan.Standard(alignment), iArr[i18], iArr2[i18] - 1, 18);
                    } else {
                        editable.setSpan(new AlignmentSpan.Standard(alignment), iArr[i18], iArr2[i18] - 1, 17);
                    }
                }
            }
        }
        objArr = (RelativeSizeSpan[]) editable.getSpans(paragraphStart, paragraphEnd, RelativeSizeSpan.class);
        if (objArr.length > 0) {
            length2 = objArr.length;
            i17 = 0;
            while (true) {
                if (i17 < length2) {
                    obj3 = objArr[i17];
                    if (editable.getSpanStart(obj3) != i5) {
                    }
                    obj = obj3;
                } else {
                    obj = null;
                }
                i17++;
            }
        } else {
            obj = null;
        }
        if (obj == null) {
            i6 = 0;
        } else if (obj instanceof FontLargeSizeSpan) {
            i6 = 2;
        } else if (obj instanceof FontMidSizeSpan) {
            i6 = 1;
        } else {
            i6 = 0;
        }
        int i411 = this.mChangeStartOffset;
        objArr2 = (EditLineHeightSpan[]) editable.getSpans(i411, this.mOnChangeCount + i411, EditLineHeightSpan.class);
        int i412 = this.mChangeStartOffset;
        Object[] objArr15 = (RelativeSizeSpan[]) editable.getSpans(i412, this.mOnChangeCount + i412, RelativeSizeSpan.class);
        int i413 = this.mChangeStartOffset;
        Object[] objArr16 = (TypefaceSpan[]) editable.getSpans(i413, this.mOnChangeCount + i413, TypefaceSpan.class);
        length = objArr2.length;
        i7 = 0;
        while (i7 < length) {
            obj2 = objArr2[i7];
            spanStart = editable.getSpanStart(obj2);
            int spanEnd3 = editable.getSpanEnd(obj2);
            Object[] objArr17 = objArr2;
            int spanFlags2 = editable.getSpanFlags(obj2);
            int[] iArr5 = iArr;
            i16 = this.mChangeStartOffset;
            if (spanStart >= i16) {
                editable.removeSpan(obj2);
            } else {
                editable.removeSpan(obj2);
            }
            i7++;
            objArr2 = objArr17;
            iArr = iArr5;
        }
        iArr3 = iArr;
        while (i8 < r4) {
            editable.removeSpan(obj11);
        }
        while (i9 < r4) {
            editable.removeSpan(obj12);
        }
        while (i10 < i2) {
            i12 = iArr3[i10];
            i13 = iArr2[i10];
            if (!getEditor().isNeedEditorLineHeight()) {
                i15 = i13 - 1;
                if (editable.length() <= i15) {
                    editable.setSpan(getEditor().getStyleFactory().createEditLineHeightSpan(i6), i12, i13, 34);
                } else {
                    editable.setSpan(getEditor().getStyleFactory().createEditLineHeightSpan(i6), i12, i13, 34);
                }
            }
            if (i6 != 0) {
                z3 = false;
                while (i14 < r5) {
                    if (editable.getSpanEnd(obj13) == this.mChangeStartOffset) {
                        z3 = true;
                    }
                }
                if (!z3) {
                    getEditor().getStyleFactory().setPlainFontSizeSpan(editable, iArr3[i10], iArr2[i10], i6);
                }
            }
        }
        while (i11 < i2) {
            string2 = editable.subSequence(iArr3[i11], iArr2[i11]).toString();
            if ("￼\n".equals(string2)) {
                clearRichStyleInMediaLine(editable, iArr3[i11], iArr2[i11], StyleSpan.class);
                clearRichStyleInMediaLine(editable, iArr3[i11], iArr2[i11], UnderlineSpan.class);
                clearRichStyleInMediaLine(editable, iArr3[i11], iArr2[i11], BgHighLightSpan.class);
                clearRichStyleInMediaLine(editable, iArr3[i11], iArr2[i11], EditLineHeightSpan.class);
                clearRichStyleInMediaLine(editable, iArr3[i11], iArr2[i11], RelativeSizeSpan.class);
                clearRichStyleInMediaLine(editable, iArr3[i11], iArr2[i11], TypefaceSpan.class);
                adjustParagraphStyleInMediaLine(editable, iArr3[i11], iArr2[i11], CheckboxSpan.class);
                adjustParagraphStyleInMediaLine(editable, iArr3[i11], iArr2[i11], EditorBulletSpan.class);
            } else if (StringUtils.LF.equals(string2)) {
                adjustParagraphStyleInMediaLine(editable, iArr3[i11], iArr2[i11], CheckboxSpan.class);
                adjustParagraphStyleInMediaLine(editable, iArr3[i11], iArr2[i11], EditorBulletSpan.class);
            }
        }
    }

    private <T> void clearRichStyleInMediaLine(Editable editable, int i, int i2, Class<T> cls) {
        for (Object obj : editable.getSpans(i, i2, cls)) {
            int spanStart = editable.getSpanStart(obj);
            int spanEnd = editable.getSpanEnd(obj);
            if (spanStart == i && spanEnd == i2) {
                editable.removeSpan(obj);
            }
        }
    }

    private <T> void adjustParagraphStyleInMediaLine(Editable editable, int i, int i2, Class<T> cls) {
        for (Object obj : editable.getSpans(i, i2, cls)) {
            int spanStart = editable.getSpanStart(obj);
            int spanEnd = editable.getSpanEnd(obj);
            int spanFlags = editable.getSpanFlags(obj);
            if (spanStart == i && spanEnd > i2) {
                editable.removeSpan(obj);
                editable.setSpan(obj, i2, spanEnd, spanFlags);
            }
        }
    }

    private boolean isUndoManagerSafe() {
        return (getEditor().getUndoManager() == null || getEditor().getUndoManager().isApplying() || getEditor().getUndoManager().getOperation() == null) ? false : true;
    }

    protected void updateImageCheckBoxAndEditorBullet(Editable editable, int i, int i2, boolean z, boolean z2, boolean z3) {
        int iUpdateEditorBulletSpan;
        RichTextWatcher richTextWatcher;
        Editable editable2;
        int i3;
        int i4;
        Logger.INSTANCE.d("RichTextWatcher", "do updateImageCheckBoxAndEditorBullet  in line with Checkbox or Bullet");
        SpannableStringBuilder buffer = getBuffer(editable, i, i2);
        deleteKeywordsSpan(editable, i, i2);
        if (z) {
            updateImageSpan(buffer);
            getEditor().deleteSpans(editable, i, i2, BaseImageSpan.class);
        }
        if (z2) {
            iUpdateEditorBulletSpan = updateCheckboxSpan(buffer);
            getEditor().deleteSpans(editable, i, i2, CheckboxSpan.class);
        } else {
            iUpdateEditorBulletSpan = 0;
        }
        if (z3) {
            iUpdateEditorBulletSpan = updateEditorBulletSpan(buffer);
            getEditor().deleteSpans(editable, i, i2, EditorBulletSpan.class);
        }
        int i5 = iUpdateEditorBulletSpan;
        this.mDoReplacing = true;
        if (z2 || z3) {
            richTextWatcher = this;
            editable2 = editable;
            i3 = i;
            i4 = i2;
            richTextWatcher.updateText(editable2, buffer, i3, i4, i5);
        } else {
            getEditor().clearTextWatcher();
            editable.replace(i, i2, buffer);
            processRichStyleSpanAfterChanged(editable);
            getEditor().restoreTextWatcher();
            richTextWatcher = this;
            editable2 = editable;
            i3 = i;
            i4 = i2;
        }
        richTextWatcher.mDoReplacing = false;
        richTextWatcher.deleteStrikeSpanOnly(editable2, i3, i4);
        if (richTextWatcher.mEditCursor >= 0) {
            richTextWatcher.getEditor().setSelection(Math.max(editable2.length() - richTextWatcher.mEditCursor, 0));
        }
        if (z2 || z3) {
            richTextWatcher.getEditor().forceUpdateLayout();
        }
    }

    private void debugLineHeightState(Editable editable) {
        EditLineHeightSpan[] editLineHeightSpanArr = (EditLineHeightSpan[]) editable.getSpans(0, editable.length(), EditLineHeightSpan.class);
        Logger.INSTANCE.d("LhTest", "debugLineHeightState spans " + editLineHeightSpanArr.length);
        for (int i = 0; i < editLineHeightSpanArr.length; i++) {
            int spanStart = editable.getSpanStart(editLineHeightSpanArr[i]);
            int spanEnd = editable.getSpanEnd(editLineHeightSpanArr[i]);
            int spanFlags = editable.getSpanFlags(editLineHeightSpanArr[i]) & 255;
            Logger.INSTANCE.d("LhTest", "debugLineHeightState spans child " + i + " text  = " + this.mGson.toJson(editable.subSequence(spanStart, spanEnd).toString()));
            Logger.INSTANCE.d("LhTest", "debugLineHeightState spans child " + i + " start  = " + spanStart + " end = " + spanEnd + " flag = " + spanFlags);
        }
    }

    private void debugSizeSpanState(Editable editable) {
        RelativeSizeSpan[] relativeSizeSpanArr = (RelativeSizeSpan[]) editable.getSpans(0, editable.length(), RelativeSizeSpan.class);
        Logger.INSTANCE.d("LhTest", "debugSizeSpanState spans " + relativeSizeSpanArr.length);
        for (int i = 0; i < relativeSizeSpanArr.length; i++) {
            int spanStart = editable.getSpanStart(relativeSizeSpanArr[i]);
            int spanEnd = editable.getSpanEnd(relativeSizeSpanArr[i]);
            int spanFlags = editable.getSpanFlags(relativeSizeSpanArr[i]) & 255;
            Logger.INSTANCE.d("LhTest", "debugSizeSpanState spans child " + i + " text  = " + this.mGson.toJson(editable.subSequence(spanStart, spanEnd).toString()));
            Logger.INSTANCE.d("LhTest", "debugSizeSpanState spans child " + i + " start  = " + spanStart + " end = " + spanEnd + " flag = " + spanFlags);
        }
    }

    private void debugImageSpanState(Editable editable) {
        NormalImageSpan[] normalImageSpanArr = (NormalImageSpan[]) editable.getSpans(0, editable.length(), NormalImageSpan.class);
        Logger.INSTANCE.d("CpTest", "debugImageSpanState spans " + normalImageSpanArr.length);
        for (int i = 0; i < normalImageSpanArr.length; i++) {
            editable.getSpanStart(normalImageSpanArr[i]);
            editable.getSpanEnd(normalImageSpanArr[i]);
            editable.getSpanFlags(normalImageSpanArr[i]);
            Logger.INSTANCE.d("CpTest", "debugImageSpanState spans child " + i + " file = " + normalImageSpanArr[i].getSrcFileId());
        }
    }

    public void debugListSpanState(Editable editable) {
        CheckboxSpan[] checkboxSpanArr = (CheckboxSpan[]) editable.getSpans(0, editable.length(), CheckboxSpan.class);
        Logger.INSTANCE.d("ListTest", "debugListSpanState check spans " + checkboxSpanArr.length);
        for (int i = 0; i < checkboxSpanArr.length; i++) {
            Logger.INSTANCE.d("ListTest", "debugListSpanState check spans child " + i + " start  = " + editable.getSpanStart(checkboxSpanArr[i]) + " end = " + editable.getSpanEnd(checkboxSpanArr[i]) + " flag = " + (editable.getSpanFlags(checkboxSpanArr[i]) & 255));
        }
        EditorBulletSpan[] editorBulletSpanArr = (EditorBulletSpan[]) editable.getSpans(0, editable.length(), EditorBulletSpan.class);
        Logger.INSTANCE.d("ListTest", "debugListSpanState bullet spans " + editorBulletSpanArr.length);
        for (int i2 = 0; i2 < editorBulletSpanArr.length; i2++) {
            Logger.INSTANCE.d("ListTest", "debugListSpanState bullet spans child " + i2 + " start  = " + editable.getSpanStart(editorBulletSpanArr[i2]) + " end = " + editable.getSpanEnd(editorBulletSpanArr[i2]) + " flag = " + (editable.getSpanFlags(editorBulletSpanArr[i2]) & 255));
        }
    }

    public void debugAlignState(Editable editable) {
        AlignmentSpan[] alignmentSpanArr = (AlignmentSpan[]) editable.getSpans(0, editable.length(), AlignmentSpan.class);
        Logger.INSTANCE.d("AlignTest", "debugAlignState spans " + alignmentSpanArr.length);
        for (int i = 0; i < alignmentSpanArr.length; i++) {
            int spanStart = editable.getSpanStart(alignmentSpanArr[i]);
            int spanEnd = editable.getSpanEnd(alignmentSpanArr[i]);
            int spanFlags = editable.getSpanFlags(alignmentSpanArr[i]) & 255;
            Logger.INSTANCE.d("LhTest", "debugAlignState spans child " + i + " text  = " + this.mGson.toJson(editable.subSequence(spanStart, spanEnd).toString()));
            Logger.INSTANCE.d("AlignTest", "debugAlignState spans child " + i + " style " + alignmentSpanArr[i].getAlignment() + " start  = " + spanStart + " end = " + spanEnd + " flag = " + spanFlags);
        }
    }

    public void debugStyleState(Editable editable) {
        StyleSpan[] styleSpanArr = (StyleSpan[]) editable.getSpans(0, editable.length(), StyleSpan.class);
        Logger.INSTANCE.d("StyleTest", "debugStyleState spans " + styleSpanArr.length);
        for (int i = 0; i < styleSpanArr.length; i++) {
            int spanStart = editable.getSpanStart(styleSpanArr[i]);
            int spanEnd = editable.getSpanEnd(styleSpanArr[i]);
            int spanFlags = editable.getSpanFlags(styleSpanArr[i]) & 255;
            Logger.INSTANCE.d("StyleTest", "debugStyleState spans child " + i + " text  = " + this.mGson.toJson(editable.subSequence(spanStart, spanEnd).toString()));
            Logger.INSTANCE.d("StyleTest", "debugStyleState spans child " + i + " style " + styleSpanArr[i].getStyle() + " start  = " + spanStart + " end = " + spanEnd + " flag = " + spanFlags);
        }
    }

    protected void updateText(Editable editable, SpannableStringBuilder spannableStringBuilder, int i, int i2, int i3) {
        int i4;
        if (i3 == 1) {
            editable.replace(i, i2, spannableStringBuilder);
            return;
        }
        Logger.INSTANCE.d("RichTextWatcher", "do updateText in line with Checkbox or Bullet");
        this.mRichEditor.setSkipRichText(true);
        this.mRichEditor.setSkipSelectionChanged(true);
        this.mRichEditor.clearTextWatcher();
        this.mRichEditor.styleInValid(StyleSpan.class, 1, i, i2);
        this.mRichEditor.styleInValid(StyleSpan.class, 2, i, i2);
        this.mRichEditor.styleInValid(UnderlineSpan.class, 5, i, i2);
        this.mRichEditor.styleInValid(FontLargeSizeSpan.class, 3, i, i2);
        this.mRichEditor.styleInValid(FontMidSizeSpan.class, 10, i, i2);
        this.mRichEditor.styleInValid(BgHighLightSpan.class, 11, i, i2);
        for (RelativeSizeSpan relativeSizeSpan : (RelativeSizeSpan[]) editable.getSpans(i, i2, RelativeSizeSpan.class)) {
            if (editable.getSpanStart(relativeSizeSpan) == i) {
                editable.removeSpan(relativeSizeSpan);
            }
        }
        for (TypefaceSpan typefaceSpan : (TypefaceSpan[]) editable.getSpans(i, i2, TypefaceSpan.class)) {
            if (editable.getSpanStart(typefaceSpan) == i) {
                editable.removeSpan(typefaceSpan);
            }
        }
        for (EditLineHeightSpan editLineHeightSpan : (EditLineHeightSpan[]) editable.getSpans(i, i2, EditLineHeightSpan.class)) {
            editable.removeSpan(editLineHeightSpan);
        }
        editable.replace(i, i2, spannableStringBuilder);
        if (i2 < editable.length() && editable.charAt(i2) == 8204 && (i4 = i2 + 1) < editable.length() && editable.charAt(i4) == '\n') {
            int i5 = i2 + 2;
            RelativeSizeSpan[] relativeSizeSpanArr = (RelativeSizeSpan[]) editable.getSpans(i4, i5, RelativeSizeSpan.class);
            TypefaceSpan[] typefaceSpanArr = (TypefaceSpan[]) editable.getSpans(i4, i5, TypefaceSpan.class);
            if (relativeSizeSpanArr.length > 0) {
                for (RelativeSizeSpan relativeSizeSpan2 : relativeSizeSpanArr) {
                    int spanStart = editable.getSpanStart(relativeSizeSpan2);
                    int spanEnd = editable.getSpanEnd(relativeSizeSpan2);
                    if (spanStart == i4 && spanEnd == i5) {
                        editable.removeSpan(relativeSizeSpan2);
                    }
                }
            }
            if (typefaceSpanArr.length > 0) {
                for (TypefaceSpan typefaceSpan2 : typefaceSpanArr) {
                    int spanStart2 = editable.getSpanStart(typefaceSpan2);
                    int spanEnd2 = editable.getSpanEnd(typefaceSpan2);
                    if (spanStart2 == i4 && spanEnd2 == i5) {
                        editable.removeSpan(typefaceSpan2);
                    }
                }
            }
        }
        int length = spannableStringBuilder.length() + i;
        if (this.mRichEditor.getParagraphStart(editable.toString(), i) == this.mRichEditor.getParagraphStart(editable.toString(), length)) {
            int paragraphStart = this.mRichEditor.getParagraphStart(editable.toString(), i);
            if (paragraphStart < editable.length() && editable.charAt(paragraphStart) == 8204) {
                paragraphStart++;
            }
            int paragraphEnd = this.mRichEditor.getParagraphEnd(editable.toString(), i);
            boolean z = false;
            for (EditLineHeightSpan editLineHeightSpan2 : (EditLineHeightSpan[]) editable.getSpans(i, length, EditLineHeightSpan.class)) {
                if (editable.getSpanStart(editLineHeightSpan2) >= paragraphStart && editable.getSpanEnd(editLineHeightSpan2) <= paragraphEnd) {
                    if (!z) {
                        editable.setSpan(editLineHeightSpan2, paragraphStart, paragraphEnd, editable.getSpanFlags(editLineHeightSpan2));
                        z = true;
                    } else {
                        editable.removeSpan(editLineHeightSpan2);
                    }
                }
            }
        }
        this.mRichEditor.reSetSpan(StyleSpan.class, 1, i, length);
        this.mRichEditor.reSetSpan(StyleSpan.class, 2, i, length);
        this.mRichEditor.reSetSpan(UnderlineSpan.class, 5, i, length);
        this.mRichEditor.restoreTextWatcher();
        this.mRichEditor.setSkipSelectionChanged(false);
        this.mRichEditor.setSkipRichText(false);
    }

    private void trimPlainText(Editable editable) {
        if (getReadableTextCount(editable) > getEditor().getEditorConfig().contentTextLengthMax) {
            int length = editable.length() - this.mChangeEndOffset;
            int iMax = Math.max(this.mChangeStartOffset, length - (editable.length() - getEditor().getEditorConfig().contentTextLengthMax));
            if (length > iMax || length <= 0) {
                this.mDoReplacing = true;
                editable.delete(iMax, length);
                this.mDoReplacing = false;
                getEditor().onSurpassContentLengthLimit();
            }
        }
    }

    private int getReadableTextCount(Editable editable) {
        if (TextUtils.isEmpty(editable)) {
            return 0;
        }
        char cCharAt = EditorUtils.ZERO_WIDTH_STRING.charAt(0);
        int i = 0;
        int i2 = 0;
        int i3 = 0;
        int i4 = 0;
        int i5 = 0;
        for (int i6 = 0; i6 < editable.length(); i6++) {
            char cCharAt2 = editable.charAt(i6);
            int i7 = 1;
            i3 += cCharAt2 == '\n' ? 1 : 0;
            i4 += cCharAt2 == ' ' ? 1 : 0;
            i5 += "/[·~！@￥%…&（）\\-\\+\\*=【】{}、\\|；‘’：“”《》？，。、`~!#\\$%\\^&\\(\\)_\\[\\]\\{\\}|;'':\"\",\\\\\\.\\/<>\\?]/".indexOf(cCharAt2) != -1 ? 1 : 0;
            i += cCharAt2 == cCharAt ? 1 : 0;
            if (cCharAt2 != 65532) {
                i7 = 0;
            }
            i2 += i7;
        }
        int length = editable.length() - ((((i + i2) + i3) + i4) + i5);
        if (length > 0) {
            return length;
        }
        return 0;
    }

    public void setReplaceCheckbox(boolean z) {
        this.mDoReplaceCheckbox = z;
    }

    public void setReplaceParagraph(boolean z) {
        this.mDoReplaceEditorBullet = z;
    }

    public void setReplacing(boolean z) {
        this.mDoReplacing = z;
    }

    private boolean checkSkip(CharSequence charSequence, int i, int i2, int i3) {
        this.mIsInitialing = false;
        if (i2 == 0 && charSequence.length() == 0 && i3 == 0) {
            this.mIsInitialing = true;
        } else {
            this.mEditCursor = -1;
            this.mRangeStartOffset = i;
            this.mChangeStartOffset = i;
            int length = (charSequence.length() - i) - i2;
            this.mRangeEndOffset = length;
            this.mChangeEndOffset = length;
        }
        return this.mIsInitialing;
    }

    private void computeAffectedRange(CharSequence charSequence, int i, int i2, int i3) {
        String string = charSequence.toString();
        this.mFront = getEditor().getLineStart(string, i);
        this.mBack = string.length() - getEditor().getLineEnd(string, i + i2);
    }

    private SpannableStringBuilder getBuffer(Editable editable, int i, int i2) {
        SpannableStringBuilder subSequence = getEditor().getSubSequence(i, i2);
        if (editable.length() > getEditor().getEditorConfig().contentTextLengthMax) {
            int length = (editable.length() - this.mChangeEndOffset) - i;
            int iMax = Math.max(this.mChangeStartOffset - i, length - (editable.length() - getEditor().getEditorConfig().contentTextLengthMax));
            if (length > iMax) {
                subSequence.delete(iMax, length);
                getEditor().onSurpassContentLengthLimit();
            }
        }
        return subSequence;
    }

    private void deleteKeywordsSpan(Editable editable, int i, int i2) {
        for (KeywordsSpan keywordsSpan : (KeywordsSpan[]) editable.getSpans(i, i2, KeywordsSpan.class)) {
            int spanStart = editable.getSpanStart(keywordsSpan);
            int spanEnd = editable.getSpanEnd(keywordsSpan);
            if (Math.max(spanStart, spanEnd) <= i2 && Math.min(spanStart, spanEnd) >= i) {
                editable.removeSpan(keywordsSpan);
            }
        }
    }

    /* JADX WARN: Code duplicated, block: B:26:0x0095  */
    /* JADX WARN: Code duplicated, block: B:37:0x00be A[SYNTHETIC] */
    private void updateImageSpan(SpannableStringBuilder spannableStringBuilder) {
        int i;
        BaseImageSpan[] baseImageSpanArr = (BaseImageSpan[]) sortSpans((BaseImageSpan[]) spannableStringBuilder.getSpans(0, spannableStringBuilder.length(), BaseImageSpan.class), spannableStringBuilder);
        for (int length = baseImageSpanArr.length - 1; length >= 0; length--) {
            BaseImageSpan baseImageSpan = baseImageSpanArr[length];
            int spanStart = spannableStringBuilder.getSpanStart(baseImageSpan);
            if (spanStart >= 0) {
                int spanEnd = spannableStringBuilder.getSpanEnd(baseImageSpan);
                if (spanStart == spanEnd) {
                    spannableStringBuilder.removeSpan(baseImageSpan);
                } else if (spanEnd == spannableStringBuilder.length() || spannableStringBuilder.charAt(spanEnd) != '\n') {
                    if (shouldRemoveImage(spannableStringBuilder, spanStart, spanEnd)) {
                        if ((baseImageSpan instanceof BaseAudioSpan) && baseImageSpan == getEditor().getPlayingAudioSpan()) {
                            getEditor().stopMediaPlayerIfNeed();
                        }
                        spannableStringBuilder.removeSpan(baseImageSpan);
                        this.mRangeStartOffset = Math.min(this.mRangeStartOffset, this.mFront + spanStart);
                        this.mRangeEndOffset = Math.min(this.mRangeEndOffset, (this.mBack + spannableStringBuilder.length()) - spanEnd);
                        spannableStringBuilder.delete(spanStart, spanEnd);
                    } else {
                        if (shouldAddEndBreak(spannableStringBuilder, spanEnd)) {
                            this.mRangeEndOffset = Math.min(this.mRangeEndOffset, (this.mBack + spannableStringBuilder.length()) - spanEnd);
                            spannableStringBuilder.insert(spanEnd, StringUtils.LF);
                        }
                        if (spanStart != 0) {
                            i = spanStart - 1;
                            if (spannableStringBuilder.charAt(i) == '\n' && spannableStringBuilder.charAt(i) != 65532) {
                                this.mRangeStartOffset = Math.min(this.mRangeStartOffset, this.mFront + spanStart);
                                spannableStringBuilder.insert(spanStart, StringUtils.LF);
                                this.mEditCursor = this.mBack + (spannableStringBuilder.length() - spanStart);
                            }
                        }
                    }
                } else if (spanStart != 0) {
                    i = spanStart - 1;
                    if (spannableStringBuilder.charAt(i) == '\n') {
                    }
                }
            }
        }
    }

    private boolean shouldRemoveImage(SpannableStringBuilder spannableStringBuilder, int i, int i2) {
        if (i2 == spannableStringBuilder.length()) {
            return this.mChangeEndOffset == this.mBack && this.mChangeStartOffset > this.mFront + i;
        }
        return spannableStringBuilder.charAt(i2) != '\n' && this.mDoDelete;
    }

    private boolean shouldAddEndBreak(SpannableStringBuilder spannableStringBuilder, int i) {
        if (i == spannableStringBuilder.length()) {
            return i + this.mFront == getEditor().getText().length() || getEditor().getText().charAt(getEditor().getText().length() - this.mBack) != '\n';
        }
        return spannableStringBuilder.charAt(i - 1) != '\n';
    }

    private int updateCheckboxSpan(SpannableStringBuilder spannableStringBuilder) {
        int i = 0;
        for (CheckboxSpan checkboxSpan : (CheckboxSpan[]) sortSpans((CheckboxSpan[]) spannableStringBuilder.getSpans(0, spannableStringBuilder.length(), CheckboxSpan.class), spannableStringBuilder)) {
            Logger.INSTANCE.d("RichTextWatcher", "updateCheckboxSpan loop span = " + checkboxSpan.toString());
            int spanStart = spannableStringBuilder.getSpanStart(checkboxSpan);
            if (spanStart >= 0) {
                int spanEnd = spannableStringBuilder.getSpanEnd(checkboxSpan);
                if (spanStart == spanEnd && (spanStart == 0 || spannableStringBuilder.charAt(spanStart - 1) == '\n')) {
                    Logger.INSTANCE.d("RichTextWatcher", "updateCheckboxSpan 0");
                    spannableStringBuilder.removeSpan(checkboxSpan);
                    i = 1;
                } else {
                    int lineStart = getEditor().getLineStart(spannableStringBuilder.toString(), spanStart);
                    getEditor().deleteSpans(spannableStringBuilder, lineStart, spanEnd, CheckboxSpan.class);
                    Logger.INSTANCE.d("RichTextWatcher", "updateCheckboxSpan 1");
                    int lineEnd = getEditor().getLineEnd(spannableStringBuilder.toString(), lineStart);
                    if (spannableStringBuilder.toString().startsWith(EditorUtils.ZERO_WIDTH_STRING, lineStart)) {
                        Logger.INSTANCE.d("RichTextWatcher", "updateCheckboxSpan 1-1");
                        getEditor().getStyleFactory().setCheckboxSpan(spannableStringBuilder, checkboxSpan, lineStart, lineEnd);
                    }
                    if (lineEnd == spanEnd && lineEnd == spannableStringBuilder.length() - 1) {
                        Logger.INSTANCE.d("RichTextWatcher", "updateCheckboxSpan 1-2");
                        updateRichSpanAfterLineBreakInCheckboxOrBullet(spannableStringBuilder);
                        CheckboxSpan checkboxSpanCreateCheckboxSpan = getEditor().getStyleFactory().createCheckboxSpan(false);
                        this.mRangeEndOffset = Math.min(this.mRangeEndOffset, this.mBack);
                        int length = spannableStringBuilder.length();
                        getEditor().getStyleFactory().setCheckboxSpan(spannableStringBuilder, checkboxSpanCreateCheckboxSpan, length, length);
                    } else {
                        Logger.INSTANCE.d("RichTextWatcher", "updateCheckboxSpan 1-3");
                        while (lineEnd < spanEnd && lineEnd <= spannableStringBuilder.length() - 1) {
                            Logger.INSTANCE.d("RichTextWatcher", "updateCheckboxSpan 1-3 loop nextLineIndex = " + lineEnd);
                            int i2 = lineEnd + 1;
                            int lineEnd2 = getEditor().getLineEnd(spannableStringBuilder.toString(), i2);
                            if (((ImageSpan) getEditor().getSpanEndAt(spannableStringBuilder, BaseImageSpan.class, lineEnd2)) != null) {
                                lineEnd = lineEnd2;
                            } else {
                                CheckboxSpan checkboxSpanCreateCheckboxSpan2 = getEditor().getStyleFactory().createCheckboxSpan(checkboxSpan.getElement().isChecked());
                                while (i2 < spannableStringBuilder.length() && spannableStringBuilder.charAt(i2) == 65532) {
                                    lineEnd2 = getEditor().getLineEnd(spannableStringBuilder.toString(), i2);
                                    i2 = lineEnd2 + 1;
                                }
                                int checkboxSpan2 = getEditor().getStyleFactory().setCheckboxSpan(spannableStringBuilder, checkboxSpanCreateCheckboxSpan2, i2, lineEnd2);
                                adjustLineStyleSpanAfterInsertCheckboxOrBullet(spannableStringBuilder, i2);
                                if (checkboxSpan2 != lineEnd2) {
                                    this.mRangeStartOffset = Math.min(this.mRangeStartOffset, this.mFront + i2);
                                    this.mRangeEndOffset = Math.min(this.mRangeEndOffset, (this.mBack + spannableStringBuilder.length()) - checkboxSpan2);
                                    spanEnd += checkboxSpan2 - lineEnd2;
                                    lineEnd = checkboxSpan2;
                                } else {
                                    lineEnd = lineEnd2;
                                }
                                i = 3;
                            }
                        }
                    }
                }
            }
        }
        for (CheckboxSpan checkboxSpan3 : (CheckboxSpan[]) sortSpans((CheckboxSpan[]) spannableStringBuilder.getSpans(0, spannableStringBuilder.length(), CheckboxSpan.class), spannableStringBuilder)) {
            if (!spannableStringBuilder.toString().startsWith(EditorUtils.ZERO_WIDTH_STRING, spannableStringBuilder.getSpanStart(checkboxSpan3))) {
                spannableStringBuilder.removeSpan(checkboxSpan3);
            }
        }
        if (spannableStringBuilder.length() > 0) {
            int iLastIndexOf = spannableStringBuilder.toString().lastIndexOf(EditorUtils.ZERO_WIDTH_STRING, spannableStringBuilder.length() - 1);
            while (iLastIndexOf >= 0) {
                if (getEditor().getSpanStartAt(spannableStringBuilder, CheckboxSpan.class, iLastIndexOf) == null) {
                    this.mRangeStartOffset = Math.min(this.mRangeStartOffset, this.mFront + iLastIndexOf);
                    this.mRangeEndOffset = Math.min(this.mRangeEndOffset, ((this.mBack + spannableStringBuilder.length()) - iLastIndexOf) - EditorUtils.ZERO_WIDTH_STRING.length());
                    spannableStringBuilder.delete(iLastIndexOf, EditorUtils.ZERO_WIDTH_STRING.length() + iLastIndexOf);
                }
                if (iLastIndexOf <= 0) {
                    break;
                }
                iLastIndexOf = spannableStringBuilder.toString().lastIndexOf(EditorUtils.ZERO_WIDTH_STRING, iLastIndexOf - 1);
            }
        }
        return i;
    }

    private int updateEditorBulletSpan(SpannableStringBuilder spannableStringBuilder) {
        int i = 0;
        for (EditorBulletSpan editorBulletSpan : (EditorBulletSpan[]) sortSpans((EditorBulletSpan[]) spannableStringBuilder.getSpans(0, spannableStringBuilder.length(), EditorBulletSpan.class), spannableStringBuilder)) {
            int spanStart = spannableStringBuilder.getSpanStart(editorBulletSpan);
            if (spanStart >= 0) {
                int spanEnd = spannableStringBuilder.getSpanEnd(editorBulletSpan);
                if (spanStart == spanEnd && (spanStart == 0 || spannableStringBuilder.charAt(spanStart - 1) == '\n')) {
                    spannableStringBuilder.removeSpan(editorBulletSpan);
                    i = 1;
                } else {
                    int lineStart = getEditor().getLineStart(spannableStringBuilder.toString(), spanStart);
                    getEditor().deleteSpans(spannableStringBuilder, lineStart, spanEnd, EditorBulletSpan.class);
                    int lineEnd = getEditor().getLineEnd(spannableStringBuilder.toString(), lineStart);
                    if (spannableStringBuilder.toString().startsWith(EditorUtils.ZERO_WIDTH_STRING, lineStart)) {
                        getEditor().getStyleFactory().setEditorBulletSpan(spannableStringBuilder, editorBulletSpan, lineStart, lineEnd);
                    }
                    if (lineEnd == spanEnd && lineEnd == spannableStringBuilder.length() - 1) {
                        updateRichSpanAfterLineBreakInCheckboxOrBullet(spannableStringBuilder);
                        EditorBulletSpan editorBulletSpanCreateEditorBulletSpan = getEditor().getStyleFactory().createEditorBulletSpan();
                        this.mRangeEndOffset = Math.min(this.mRangeEndOffset, this.mBack);
                        int length = spannableStringBuilder.length();
                        getEditor().getStyleFactory().setEditorBulletSpan(spannableStringBuilder, editorBulletSpanCreateEditorBulletSpan, length, length);
                    } else {
                        while (lineEnd < spanEnd && lineEnd <= spannableStringBuilder.length() - 1) {
                            int i2 = lineEnd + 1;
                            int lineEnd2 = getEditor().getLineEnd(spannableStringBuilder.toString(), i2);
                            if (((ImageSpan) getEditor().getSpanEndAt(spannableStringBuilder, BaseImageSpan.class, lineEnd2)) != null) {
                                lineEnd = lineEnd2;
                            } else {
                                EditorBulletSpan editorBulletSpanCreateEditorBulletSpan2 = getEditor().getStyleFactory().createEditorBulletSpan();
                                while (i2 < spannableStringBuilder.length() && spannableStringBuilder.charAt(i2) == 65532) {
                                    lineEnd2 = getEditor().getLineEnd(spannableStringBuilder.toString(), i2);
                                    i2 = lineEnd2 + 1;
                                }
                                int editorBulletSpan2 = getEditor().getStyleFactory().setEditorBulletSpan(spannableStringBuilder, editorBulletSpanCreateEditorBulletSpan2, i2, lineEnd2);
                                adjustLineStyleSpanAfterInsertCheckboxOrBullet(spannableStringBuilder, i2);
                                if (editorBulletSpan2 != lineEnd2) {
                                    this.mRangeStartOffset = Math.min(this.mRangeStartOffset, this.mFront + i2);
                                    this.mRangeEndOffset = Math.min(this.mRangeEndOffset, (this.mBack + spannableStringBuilder.length()) - editorBulletSpan2);
                                    spanEnd += editorBulletSpan2 - lineEnd2;
                                    lineEnd = editorBulletSpan2;
                                } else {
                                    lineEnd = lineEnd2;
                                }
                                i = 3;
                            }
                        }
                    }
                }
            }
        }
        for (EditorBulletSpan editorBulletSpan3 : (EditorBulletSpan[]) sortSpans((EditorBulletSpan[]) spannableStringBuilder.getSpans(0, spannableStringBuilder.length(), EditorBulletSpan.class), spannableStringBuilder)) {
            if (!spannableStringBuilder.toString().startsWith(EditorUtils.ZERO_WIDTH_STRING, spannableStringBuilder.getSpanStart(editorBulletSpan3))) {
                spannableStringBuilder.removeSpan(editorBulletSpan3);
            }
        }
        if (spannableStringBuilder.length() > 0) {
            int iLastIndexOf = spannableStringBuilder.toString().lastIndexOf(EditorUtils.ZERO_WIDTH_STRING, spannableStringBuilder.length() - 1);
            while (iLastIndexOf >= 0) {
                if (getEditor().getSpanStartAt(spannableStringBuilder, EditorBulletSpan.class, iLastIndexOf) == null) {
                    this.mRangeStartOffset = Math.min(this.mRangeStartOffset, this.mFront + iLastIndexOf);
                    this.mRangeEndOffset = Math.min(this.mRangeEndOffset, ((this.mBack + spannableStringBuilder.length()) - iLastIndexOf) - EditorUtils.ZERO_WIDTH_STRING.length());
                    spannableStringBuilder.delete(iLastIndexOf, EditorUtils.ZERO_WIDTH_STRING.length() + iLastIndexOf);
                }
                if (iLastIndexOf <= 0) {
                    break;
                }
                iLastIndexOf = spannableStringBuilder.toString().lastIndexOf(EditorUtils.ZERO_WIDTH_STRING, iLastIndexOf - 1);
            }
        }
        return i;
    }

    private void adjustStyleSpanAfterInsertCheckboxOrBullet(SpannableStringBuilder spannableStringBuilder, int i) {
        int iIndexOf = spannableStringBuilder.toString().indexOf(EditorUtils.ZERO_WIDTH_STRING, i) + 1;
        for (StyleSpan styleSpan : (StyleSpan[]) spannableStringBuilder.getSpans(iIndexOf, iIndexOf, StyleSpan.class)) {
            int spanStart = spannableStringBuilder.getSpanStart(styleSpan);
            int spanEnd = spannableStringBuilder.getSpanEnd(styleSpan);
            if ((spannableStringBuilder.getSpanFlags(styleSpan) & 255) == 34) {
                spannableStringBuilder.setSpan(styleSpan, spanStart, spanEnd, 18);
            }
        }
        for (UnderlineSpan underlineSpan : (UnderlineSpan[]) spannableStringBuilder.getSpans(iIndexOf, iIndexOf, UnderlineSpan.class)) {
            int spanStart2 = spannableStringBuilder.getSpanStart(underlineSpan);
            int spanEnd2 = spannableStringBuilder.getSpanEnd(underlineSpan);
            if ((spannableStringBuilder.getSpanFlags(underlineSpan) & 255) == 34) {
                spannableStringBuilder.setSpan(underlineSpan, spanStart2, spanEnd2, 18);
            }
        }
        for (BgHighLightSpan bgHighLightSpan : (BgHighLightSpan[]) spannableStringBuilder.getSpans(iIndexOf, iIndexOf, BgHighLightSpan.class)) {
            int spanStart3 = spannableStringBuilder.getSpanStart(bgHighLightSpan);
            int spanEnd3 = spannableStringBuilder.getSpanEnd(bgHighLightSpan);
            if ((spannableStringBuilder.getSpanFlags(bgHighLightSpan) & 255) == 34) {
                spannableStringBuilder.setSpan(bgHighLightSpan, spanStart3, spanEnd3, 18);
            }
        }
    }

    private void adjustLineStyleSpanAfterInsertCheckboxOrBullet(SpannableStringBuilder spannableStringBuilder, int i) {
        int i2;
        int i3;
        int i4;
        int iIndexOf = spannableStringBuilder.toString().indexOf(EditorUtils.ZERO_WIDTH_STRING, i);
        int i5 = iIndexOf + 1;
        RelativeSizeSpan[] relativeSizeSpanArr = (RelativeSizeSpan[]) spannableStringBuilder.getSpans(i5, i5, RelativeSizeSpan.class);
        TypefaceSpan[] typefaceSpanArr = (TypefaceSpan[]) spannableStringBuilder.getSpans(i5, i5, TypefaceSpan.class);
        EditLineHeightSpan[] editLineHeightSpanArr = (EditLineHeightSpan[]) spannableStringBuilder.getSpans(i5, i5, EditLineHeightSpan.class);
        for (RelativeSizeSpan relativeSizeSpan : relativeSizeSpanArr) {
            int spanStart = spannableStringBuilder.getSpanStart(relativeSizeSpan);
            int spanEnd = spannableStringBuilder.getSpanEnd(relativeSizeSpan);
            int spanFlags = spannableStringBuilder.getSpanFlags(relativeSizeSpan);
            if (spanStart == iIndexOf && spanEnd >= i5) {
                int i6 = spanStart + 1;
                if (i6 <= spanEnd) {
                    spanStart = i6;
                }
            } else if (spanStart < iIndexOf && spanEnd == i5 && spanStart <= (i4 = spanEnd - 1)) {
                spanEnd = i4;
            }
            spannableStringBuilder.setSpan(relativeSizeSpan, spanStart, spanEnd, spanFlags);
        }
        for (TypefaceSpan typefaceSpan : typefaceSpanArr) {
            int spanStart2 = spannableStringBuilder.getSpanStart(typefaceSpan);
            int spanEnd2 = spannableStringBuilder.getSpanEnd(typefaceSpan);
            int spanFlags2 = spannableStringBuilder.getSpanFlags(typefaceSpan);
            if (spanStart2 == iIndexOf && spanEnd2 >= i5) {
                int i7 = spanStart2 + 1;
                if (i7 <= spanEnd2) {
                    spanStart2 = i7;
                }
            } else if (spanStart2 < iIndexOf && spanEnd2 == i5 && spanStart2 <= (i3 = spanEnd2 - 1)) {
                spanEnd2 = i3;
            }
            spannableStringBuilder.setSpan(typefaceSpan, spanStart2, spanEnd2, spanFlags2);
        }
        for (EditLineHeightSpan editLineHeightSpan : editLineHeightSpanArr) {
            int spanStart3 = spannableStringBuilder.getSpanStart(editLineHeightSpan);
            int spanEnd3 = spannableStringBuilder.getSpanEnd(editLineHeightSpan);
            int spanFlags3 = spannableStringBuilder.getSpanFlags(editLineHeightSpan);
            if (spanStart3 == iIndexOf && spanEnd3 >= i5) {
                int i8 = spanStart3 + 1;
                if (i8 <= spanEnd3) {
                    spanStart3 = i8;
                }
            } else if (spanStart3 < iIndexOf && spanEnd3 == i5 && spanStart3 <= (i2 = spanEnd3 - 1)) {
                spanEnd3 = i2;
            }
            spannableStringBuilder.setSpan(editLineHeightSpan, spanStart3, spanEnd3, spanFlags3);
        }
    }

    private void updateRichSpanAfterLineBreakInCheckboxOrBullet(SpannableStringBuilder spannableStringBuilder) {
        Logger.INSTANCE.d("RichTextWatcher", "do updateRichSpanAfterLineBreakInCheckboxOrBullet");
        int length = spannableStringBuilder.length();
        int i = length - 1;
        FontLargeSizeSpan[] fontLargeSizeSpanArr = (FontLargeSizeSpan[]) spannableStringBuilder.getSpans(i, i, FontLargeSizeSpan.class);
        if (fontLargeSizeSpanArr.length > 0) {
            spannableStringBuilder.setSpan(fontLargeSizeSpanArr[0], spannableStringBuilder.getSpanStart(fontLargeSizeSpanArr[0]), length, 33);
            Logger.INSTANCE.d("RichTextWatcher", "do updateRichSpanAfterLineBreakInCheckboxOrBullet - setFontLarge");
        }
        FontMidSizeSpan[] fontMidSizeSpanArr = (FontMidSizeSpan[]) spannableStringBuilder.getSpans(i, i, FontMidSizeSpan.class);
        if (fontMidSizeSpanArr.length > 0) {
            spannableStringBuilder.setSpan(fontMidSizeSpanArr[0], spannableStringBuilder.getSpanStart(fontMidSizeSpanArr[0]), length, 33);
            Logger.INSTANCE.d("RichTextWatcher", "do updateRichSpanAfterLineBreakInCheckboxOrBullet - setFontMid");
        }
        TypefaceSpan[] typefaceSpanArr = (TypefaceSpan[]) spannableStringBuilder.getSpans(i, i, TypefaceSpan.class);
        if (typefaceSpanArr.length > 0) {
            spannableStringBuilder.setSpan(typefaceSpanArr[0], spannableStringBuilder.getSpanStart(typefaceSpanArr[0]), length, 33);
            Logger.INSTANCE.d("RichTextWatcher", "do updateRichSpanAfterLineBreakInCheckboxOrBullet - setFontTypeface");
        }
        EditLineHeightSpan[] editLineHeightSpanArr = (EditLineHeightSpan[]) spannableStringBuilder.getSpans(i, i, EditLineHeightSpan.class);
        if (editLineHeightSpanArr.length > 0) {
            int spanStart = spannableStringBuilder.getSpanStart(editLineHeightSpanArr[0]);
            spannableStringBuilder.getSpanEnd(editLineHeightSpanArr[0]);
            spannableStringBuilder.setSpan(editLineHeightSpanArr[0], spanStart, length, 33);
            Logger.INSTANCE.d("RichTextWatcher", "do updateRichSpanAfterLineBreakInCheckboxOrBullet - setLineHeight");
        }
    }

    protected <T> T[] sortSpans(T[] tArr, final Spannable spannable) {
        if (tArr.length < 2) {
            return tArr;
        }
        ArrayList arrayList = new ArrayList(tArr.length);
        Collections.addAll(arrayList, tArr);
        Collections.sort(arrayList, new Comparator<T>() { // from class: com.miui.richeditor.RichTextWatcher.1
            @Override // java.util.Comparator
            public int compare(T t, T t2) {
                return spannable.getSpanStart(t) - spannable.getSpanStart(t2);
            }
        });
        return (T[]) arrayList.toArray(tArr);
    }

    private void deleteStrikeSpanOnly(Spannable spannable, int i, int i2) {
        StrikethroughSpan[] strikethroughSpanArr = (StrikethroughSpan[]) spannable.getSpans(i, i2, StrikethroughSpan.class);
        for (StrikethroughSpan strikethroughSpan : strikethroughSpanArr) {
            if (strikethroughSpan.getClass().equals(StrikethroughSpan.class)) {
                spannable.removeSpan(strikethroughSpan);
            }
        }
    }

    private void splitLineStyleSpanAfterLinebreak(Editable editable, int i) {
        boolean z;
        int i2;
        int i3 = i + 1;
        boolean z2 = i3 == editable.length() || (i3 < editable.length() && editable.charAt(i3) == '\n');
        Object[] objArr = (EditLineHeightSpan[]) editable.getSpans(i3, i3, EditLineHeightSpan.class);
        Object[] objArr2 = (RelativeSizeSpan[]) editable.getSpans(i, i3, RelativeSizeSpan.class);
        TypefaceSpan[] typefaceSpanArr = (TypefaceSpan[]) editable.getSpans(i3, i3, TypefaceSpan.class);
        if (typefaceSpanArr.length > 0) {
            z = false;
            i2 = 0;
            for (TypefaceSpan typefaceSpan : typefaceSpanArr) {
                int spanStart = editable.getSpanStart(typefaceSpan);
                int spanEnd = editable.getSpanEnd(typefaceSpan);
                if (MiuiFontName.SEMIBOLD.equals(typefaceSpan.getFamily())) {
                    i2 = 2;
                } else if (MiuiFontName.NORMAL.equals(typefaceSpan.getFamily())) {
                    i2 = 1;
                }
                if (spanStart < i3 && spanEnd >= i3) {
                    editable.setSpan(typefaceSpan, spanStart, i3, 33);
                    z = true;
                }
            }
        } else {
            z = false;
            i2 = 0;
        }
        if (objArr2.length > 0) {
            for (Object obj : objArr2) {
                int spanStart2 = editable.getSpanStart(obj);
                int spanEnd2 = editable.getSpanEnd(obj);
                if (obj instanceof FontLargeSizeSpan) {
                    i2 = 2;
                } else if (obj instanceof FontMidSizeSpan) {
                    i2 = 1;
                }
                if (spanStart2 < i3 && spanEnd2 >= i3) {
                    if (!z2) {
                        editable.setSpan(obj, spanStart2, i3, 33);
                        getEditor().getStyleFactory().setPlainFontSizeSpan(editable, i3, spanEnd2, i2);
                    } else {
                        editable.setSpan(obj, spanStart2, Math.max(spanStart2, i), 33);
                    }
                    z = true;
                }
            }
        }
        int i4 = i2;
        if (!z) {
            int paragraphStart = getEditor().getParagraphStart(editable.toString(), i);
            if (paragraphStart < editable.length() && editable.charAt(paragraphStart) == 8204) {
                paragraphStart++;
            }
            getEditor().getStyleFactory().setPlainFontSizeSpan(editable, paragraphStart, i3, i4, 33);
        }
        if (objArr.length > 0) {
            for (Object obj2 : objArr) {
                int spanStart3 = editable.getSpanStart(obj2);
                int spanEnd3 = editable.getSpanEnd(obj2);
                if (spanStart3 == i3) {
                    editable.setSpan(obj2, spanStart3, i3, 17);
                    if (z2) {
                        editable.setSpan(getEditor().getStyleFactory().createEditLineHeightSpan(i4), i3, i3, 18);
                    } else if (spanEnd3 >= i3) {
                        int i5 = spanEnd3 - 1;
                        if (editable.length() > i5 && editable.charAt(i5) == '\n') {
                            editable.setSpan(getEditor().getStyleFactory().createEditLineHeightSpan(i4), i3, spanEnd3, 33);
                        } else {
                            editable.setSpan(getEditor().getStyleFactory().createEditLineHeightSpan(i4), i3, spanEnd3, 34);
                        }
                    }
                } else if (spanStart3 < i3 && spanEnd3 >= i3) {
                    editable.setSpan(obj2, spanStart3, i3, 33);
                    if (z2) {
                        editable.setSpan(getEditor().getStyleFactory().createEditLineHeightSpan(i4), i3, i3, 18);
                    } else {
                        int i6 = spanEnd3 - 1;
                        if (editable.length() > i6 && editable.charAt(i6) == '\n') {
                            editable.setSpan(getEditor().getStyleFactory().createEditLineHeightSpan(i4), i3, spanEnd3, 33);
                        } else {
                            editable.setSpan(getEditor().getStyleFactory().createEditLineHeightSpan(i4), i3, spanEnd3, 34);
                        }
                    }
                }
            }
        }
    }

    private <T> void splitRichStyleSpanAfterLinebreak(Editable editable, int i, Class<T> cls) {
        Object[] spans = editable.getSpans(i, i, cls);
        if (spans.length > 0) {
            boolean z = cls == UnderlineSpan.class;
            for (Object obj : spans) {
                int spanStart = editable.getSpanStart(obj);
                int spanEnd = editable.getSpanEnd(obj);
                if (z && spanStart == this.mLastComposingStart && spanEnd == this.mLastComposingEnd + 1) {
                    editable.removeSpan(obj);
                } else if (spanStart < i && spanEnd >= i) {
                    editable.setSpan(obj, spanStart, i, 33);
                    int i2 = i + 1;
                    if (spanEnd >= i2 && i2 < editable.length() && editable.toString().charAt(i2) != '\n') {
                        if (cls == StyleSpan.class) {
                            editable.setSpan(new StyleSpan(((StyleSpan) obj).getStyle()), i2, spanEnd, 18);
                        } else if (z) {
                            editable.setSpan(new UnderlineSpan(), i2, spanEnd, 18);
                        } else if (cls == BgHighLightSpan.class) {
                            editable.setSpan(new BgHighLightSpan(getEditor()), i2, spanEnd, 18);
                        }
                    }
                } else if (spanStart == spanEnd) {
                    editable.removeSpan(obj);
                }
            }
            if (z) {
                this.mLastComposingStart = -1;
                this.mLastComposingEnd = -1;
            }
        }
    }

    private void splitAlignSpanAfterLinebreak(Editable editable, int i) {
        AlignmentSpan[] alignmentSpanArr = (AlignmentSpan[]) editable.getSpans(i, i, AlignmentSpan.class);
        if (alignmentSpanArr.length > 0) {
            for (AlignmentSpan alignmentSpan : alignmentSpanArr) {
                int spanStart = editable.getSpanStart(alignmentSpan);
                int spanEnd = editable.getSpanEnd(alignmentSpan);
                if (spanStart < i && spanEnd >= i) {
                    editable.setSpan(alignmentSpan, getEditor().getParagraphStart(editable.toString(), spanStart), i, 17);
                    int i2 = i + 1;
                    if (spanEnd >= i2) {
                        editable.setSpan(new AlignmentSpan.Standard(alignmentSpan.getAlignment()), i2, spanEnd, 18);
                    } else if (spanEnd == i) {
                        editable.setSpan(new AlignmentSpan.Standard(alignmentSpan.getAlignment()), i2, i2, 18);
                    }
                } else if (spanStart == spanEnd) {
                    editable.removeSpan(alignmentSpan);
                }
            }
        }
    }

    private <T> void mergeOrDeleteRichStyleSpanAfterDelete(Editable editable, int i, Class<T> cls, int i2) {
        boolean z;
        Object obj;
        int i3 = i > 0 ? i - 1 : i;
        int i4 = i < editable.length() ? i + 1 : i;
        if (editable.length() == 0) {
            return;
        }
        if (editable.length() <= i3 || editable.charAt(i3) != '\n') {
            z = false;
        } else {
            i3 = i;
            z = true;
        }
        Object[] spans = editable.getSpans(i3, i, cls);
        if (StyleSpan.class.equals(cls)) {
            getFilterStyleSpans((StyleSpan[]) spans, i2);
        }
        Object obj2 = null;
        if (spans.length > 0) {
            obj = spans[0];
            if (editable.getSpanStart(obj) != editable.getSpanEnd(obj) && spans.length > 1 && z) {
                for (int i5 = 1; i5 < spans.length; i5++) {
                    if (editable.getSpanStart(spans[i5]) != editable.getSpanEnd(spans[i5])) {
                        obj = spans[i5];
                        break;
                    }
                }
            }
        } else {
            obj = null;
        }
        Object[] spans2 = editable.getSpans(i, i4, cls);
        if (StyleSpan.class.equals(cls)) {
            getFilterStyleSpans((StyleSpan[]) spans2, i2);
        }
        if (spans2.length > 0) {
            obj2 = spans2[0];
            if (spans2.length > 1 && obj2 == obj) {
                for (int i6 = 1; i6 < spans2.length; i6++) {
                    Object obj3 = spans2[i6];
                    if (obj3 != obj) {
                        obj2 = obj3;
                        break;
                    }
                }
            }
        }
        if (obj != null && obj2 != null && obj != obj2) {
            int spanStart = editable.getSpanStart(obj);
            int spanEnd = editable.getSpanEnd(obj2);
            editable.removeSpan(obj2);
            editable.setSpan(obj, spanStart, spanEnd, 33);
            return;
        }
        if (obj == obj2) {
            Object[] spans3 = editable.getSpans(i, i, cls);
            if (StyleSpan.class.equals(cls)) {
                getFilterStyleSpans((StyleSpan[]) spans3, i2);
            }
            for (Object obj4 : spans3) {
                if (editable.getSpanStart(obj4) == editable.getSpanEnd(obj4)) {
                    editable.removeSpan(obj4);
                }
            }
        }
    }

    private void getFilterStyleSpans(StyleSpan[] styleSpanArr, int i) {
        ArrayList arrayList = new ArrayList();
        for (StyleSpan styleSpan : styleSpanArr) {
            if (styleSpan.getStyle() == i) {
                arrayList.add(styleSpan);
            }
        }
        arrayList.toArray(styleSpanArr);
    }

    private void mergeOrDeleteAlignSpanAfterDelete(Editable editable, int i) {
        boolean z;
        AlignmentSpan alignmentSpan;
        int i2 = i > 0 ? i - 1 : i;
        int i3 = i < editable.length() ? i + 1 : i;
        if (editable.length() == 0) {
            return;
        }
        if (editable.length() <= i2 || editable.charAt(i2) != '\n') {
            z = false;
        } else {
            i2 = i;
            z = true;
        }
        AlignmentSpan[] alignmentSpanArr = (AlignmentSpan[]) editable.getSpans(i2, i, AlignmentSpan.class);
        AlignmentSpan alignmentSpan2 = null;
        if (alignmentSpanArr.length > 0) {
            alignmentSpan = alignmentSpanArr[0];
            if (editable.getSpanStart(alignmentSpan) != editable.getSpanEnd(alignmentSpan) && alignmentSpanArr.length > 1 && z) {
                for (int i4 = 1; i4 < alignmentSpanArr.length; i4++) {
                    if (editable.getSpanStart(alignmentSpanArr[i4]) != editable.getSpanEnd(alignmentSpanArr[i4])) {
                        alignmentSpan = alignmentSpanArr[i4];
                        break;
                    }
                }
            }
        } else {
            alignmentSpan = null;
        }
        AlignmentSpan[] alignmentSpanArr2 = (AlignmentSpan[]) editable.getSpans(i, i3, AlignmentSpan.class);
        if (alignmentSpanArr2.length > 0) {
            alignmentSpan2 = alignmentSpanArr2[0];
            if (alignmentSpanArr2.length > 1 && alignmentSpan2 == alignmentSpan) {
                for (int i5 = 1; i5 < alignmentSpanArr2.length; i5++) {
                    AlignmentSpan alignmentSpan3 = alignmentSpanArr2[i5];
                    if (alignmentSpan3 != alignmentSpan) {
                        alignmentSpan2 = alignmentSpan3;
                        break;
                    }
                }
            }
        }
        if (alignmentSpan == null && alignmentSpan2 != null) {
            editable.removeSpan(alignmentSpan2);
            return;
        }
        if (alignmentSpan != null && alignmentSpan2 == null) {
            editable.setSpan(alignmentSpan, editable.getSpanStart(alignmentSpan), getEditor().getParagraphEnd(editable.toString(), i), 33);
            return;
        }
        if (alignmentSpan != null && alignmentSpan2 != null && alignmentSpan != alignmentSpan2) {
            int spanStart = editable.getSpanStart(alignmentSpan);
            int spanEnd = editable.getSpanEnd(alignmentSpan2);
            editable.removeSpan(alignmentSpan2);
            editable.setSpan(alignmentSpan, spanStart, spanEnd, 33);
            return;
        }
        if (alignmentSpan == alignmentSpan2) {
            for (AlignmentSpan alignmentSpan4 : (AlignmentSpan[]) editable.getSpans(i, i, AlignmentSpan.class)) {
                if (editable.getSpanStart(alignmentSpan4) == editable.getSpanEnd(alignmentSpan4)) {
                    editable.removeSpan(alignmentSpan4);
                }
            }
        }
    }
}
