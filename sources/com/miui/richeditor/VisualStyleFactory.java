package com.miui.richeditor;

import android.content.Context;
import android.text.Editable;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.BackgroundColorSpan;
import android.text.style.StrikethroughSpan;
import com.miui.notes.base.utils.Logger;
import com.miui.richeditor.schema.HtmlParser;
import com.miui.richeditor.style.BaseAudioSpan;
import com.miui.richeditor.style.BaseImageSpan;
import com.miui.richeditor.style.BgHighLightSpan;
import com.miui.richeditor.style.CheckboxSpan;
import com.miui.richeditor.style.EditLineHeightSpan;
import com.miui.richeditor.style.EditorBulletSpan;
import com.miui.richeditor.style.FontLargeSizeSpan;
import com.miui.richeditor.style.FontMidSizeSpan;
import com.miui.richeditor.style.FontNormalSizeSpan;
import com.miui.richeditor.style.KeywordsSpan;
import com.miui.richeditor.style.LinkCardSpan;
import com.miui.richeditor.style.NormalImageSpan;
import com.miui.richeditor.style.lite.CheckboxLiteSpan;
import com.miui.richeditor.style.lite.EditLineHeightLiteSpan;
import com.miui.richeditor.style.lite.EditorBulletLiteSpan;
import com.miui.richeditor.style.lite.FontLargeSizeLiteSpan;
import com.miui.richeditor.style.lite.FontMidSizeLiteSpan;
import com.miui.richeditor.util.EditorUtils;

/* JADX INFO: loaded from: classes4.dex */
public class VisualStyleFactory {
    protected Context mContext;
    protected IAudioEditorContext mEditorContext;

    public void converterViewToData(SpannableStringBuilder spannableStringBuilder) throws InterruptedException {
    }

    public VisualStyleFactory(Context context, IAudioEditorContext iAudioEditorContext) {
        this.mContext = context;
        this.mEditorContext = iAudioEditorContext;
    }

    public void converterDataToView(SpannableStringBuilder spannableStringBuilder) throws InterruptedException {
        converterStrikeSpansToViewBeforeCheckbox(spannableStringBuilder);
        converterCheckboxSpansToView(spannableStringBuilder);
        converterStrikeSpansToViewAfterCheckbox(spannableStringBuilder);
        converterBulletSpansToView(spannableStringBuilder);
        converterBgColorSpansToView(spannableStringBuilder);
        converterFontSizeSpansToView(spannableStringBuilder);
        converterLineHeightSpansToView(spannableStringBuilder);
    }

    private Spannable converterStrikeSpansToViewBeforeCheckbox(SpannableStringBuilder spannableStringBuilder) throws InterruptedException {
        StrikethroughSpan[] strikethroughSpanArr = (StrikethroughSpan[]) spannableStringBuilder.getSpans(0, spannableStringBuilder.length(), StrikethroughSpan.class);
        for (int length = strikethroughSpanArr.length - 1; length >= 0; length--) {
            if (Thread.currentThread().isInterrupted()) {
                throw new InterruptedException();
            }
            int spanStart = spannableStringBuilder.getSpanStart(strikethroughSpanArr[length]);
            int spanEnd = spannableStringBuilder.getSpanEnd(strikethroughSpanArr[length]);
            if (spannableStringBuilder.length() == spanEnd || spannableStringBuilder.charAt(spanEnd) == '\n') {
                spannableStringBuilder.removeSpan(strikethroughSpanArr[length]);
                setCheckboxSpan(spannableStringBuilder, new CheckboxSpan((IEditorContext) this.mEditorContext, true), spanStart, spanEnd);
            }
        }
        return spannableStringBuilder;
    }

    private Spannable converterCheckboxSpansToView(SpannableStringBuilder spannableStringBuilder) throws InterruptedException {
        CheckboxLiteSpan[] checkboxLiteSpanArr = (CheckboxLiteSpan[]) spannableStringBuilder.getSpans(0, spannableStringBuilder.length(), CheckboxLiteSpan.class);
        for (int length = checkboxLiteSpanArr.length - 1; length >= 0; length--) {
            if (Thread.currentThread().isInterrupted()) {
                throw new InterruptedException();
            }
            int spanStart = spannableStringBuilder.getSpanStart(checkboxLiteSpanArr[length]);
            int spanEnd = spannableStringBuilder.getSpanEnd(checkboxLiteSpanArr[length]);
            spannableStringBuilder.removeSpan(checkboxLiteSpanArr[length]);
            spannableStringBuilder.delete(spanStart, spanEnd);
            int iIndexOf = spannableStringBuilder.toString().indexOf(10, spanStart);
            if (iIndexOf < 0) {
                iIndexOf = spannableStringBuilder.length();
            }
            setCheckboxSpan(spannableStringBuilder, createCheckboxSpanByElement(checkboxLiteSpanArr[length].getElement()), spanStart, iIndexOf);
        }
        return spannableStringBuilder;
    }

    private Spannable converterStrikeSpansToViewAfterCheckbox(SpannableStringBuilder spannableStringBuilder) throws InterruptedException {
        for (StrikethroughSpan strikethroughSpan : (StrikethroughSpan[]) spannableStringBuilder.getSpans(0, spannableStringBuilder.length(), StrikethroughSpan.class)) {
            if (Thread.currentThread().isInterrupted()) {
                throw new InterruptedException();
            }
            int spanStart = spannableStringBuilder.getSpanStart(strikethroughSpan);
            int spanEnd = spannableStringBuilder.getSpanEnd(strikethroughSpan);
            if ((spanStart == 0 || spannableStringBuilder.charAt(spanStart - 1) == '\n') && spanEnd == spannableStringBuilder.length() && spannableStringBuilder.charAt(spanEnd - 1) == '\n') {
                spannableStringBuilder.removeSpan(strikethroughSpan);
                setCheckboxSpan(spannableStringBuilder, createCheckboxSpan(true), spanStart, spanEnd);
            }
        }
        return spannableStringBuilder;
    }

    private Spannable converterBulletSpansToView(SpannableStringBuilder spannableStringBuilder) throws InterruptedException {
        EditorBulletLiteSpan[] editorBulletLiteSpanArr = (EditorBulletLiteSpan[]) spannableStringBuilder.getSpans(0, spannableStringBuilder.length(), EditorBulletLiteSpan.class);
        for (int length = editorBulletLiteSpanArr.length - 1; length >= 0; length--) {
            if (Thread.currentThread().isInterrupted()) {
                throw new InterruptedException();
            }
            int spanStart = spannableStringBuilder.getSpanStart(editorBulletLiteSpanArr[length]);
            int spanEnd = spannableStringBuilder.getSpanEnd(editorBulletLiteSpanArr[length]);
            spannableStringBuilder.removeSpan(editorBulletLiteSpanArr[length]);
            spannableStringBuilder.delete(spanStart, spanEnd);
            int iIndexOf = spannableStringBuilder.toString().indexOf(10, spanStart);
            if (iIndexOf < 0) {
                iIndexOf = spannableStringBuilder.length();
            }
            setEditorBulletSpan(spannableStringBuilder, new EditorBulletSpan(this.mEditorContext), spanStart, iIndexOf);
        }
        return spannableStringBuilder;
    }

    private Spannable converterBgColorSpansToView(SpannableStringBuilder spannableStringBuilder) throws InterruptedException {
        for (BackgroundColorSpan backgroundColorSpan : (BackgroundColorSpan[]) spannableStringBuilder.getSpans(0, spannableStringBuilder.length(), BackgroundColorSpan.class)) {
            if (Thread.currentThread().isInterrupted()) {
                throw new InterruptedException();
            }
            int spanStart = spannableStringBuilder.getSpanStart(backgroundColorSpan);
            int spanEnd = spannableStringBuilder.getSpanEnd(backgroundColorSpan);
            spannableStringBuilder.removeSpan(backgroundColorSpan);
            spannableStringBuilder.setSpan(new BgHighLightSpan(this.mEditorContext), spanStart, spanEnd, 33);
        }
        return spannableStringBuilder;
    }

    private Spannable converterFontSizeSpansToView(SpannableStringBuilder spannableStringBuilder) throws InterruptedException {
        for (FontLargeSizeLiteSpan fontLargeSizeLiteSpan : (FontLargeSizeLiteSpan[]) spannableStringBuilder.getSpans(0, spannableStringBuilder.length(), FontLargeSizeLiteSpan.class)) {
            if (Thread.currentThread().isInterrupted()) {
                throw new InterruptedException();
            }
            int spanStart = spannableStringBuilder.getSpanStart(fontLargeSizeLiteSpan);
            int spanEnd = spannableStringBuilder.getSpanEnd(fontLargeSizeLiteSpan);
            spannableStringBuilder.removeSpan(fontLargeSizeLiteSpan);
            if (spanEnd < spannableStringBuilder.length() && spannableStringBuilder.charAt(spanEnd) == '\n') {
                spannableStringBuilder.setSpan(new FontLargeSizeSpan(this.mEditorContext), spanStart, spanEnd + 1, 33);
            } else {
                spannableStringBuilder.setSpan(new FontLargeSizeSpan(this.mEditorContext), spanStart, spanEnd, 34);
            }
        }
        for (FontMidSizeLiteSpan fontMidSizeLiteSpan : (FontMidSizeLiteSpan[]) spannableStringBuilder.getSpans(0, spannableStringBuilder.length(), FontMidSizeLiteSpan.class)) {
            if (Thread.currentThread().isInterrupted()) {
                throw new InterruptedException();
            }
            int spanStart2 = spannableStringBuilder.getSpanStart(fontMidSizeLiteSpan);
            int spanEnd2 = spannableStringBuilder.getSpanEnd(fontMidSizeLiteSpan);
            spannableStringBuilder.removeSpan(fontMidSizeLiteSpan);
            if (spanEnd2 < spannableStringBuilder.length() && spannableStringBuilder.charAt(spanEnd2) == '\n') {
                spannableStringBuilder.setSpan(new FontMidSizeSpan(this.mEditorContext), spanStart2, spanEnd2 + 1, 33);
            } else {
                spannableStringBuilder.setSpan(new FontMidSizeSpan(this.mEditorContext), spanStart2, spanEnd2, 34);
            }
        }
        return spannableStringBuilder;
    }

    protected Spannable converterLineHeightSpansToView(SpannableStringBuilder spannableStringBuilder) throws InterruptedException {
        for (EditLineHeightLiteSpan editLineHeightLiteSpan : (EditLineHeightLiteSpan[]) spannableStringBuilder.getSpans(0, spannableStringBuilder.length(), EditLineHeightLiteSpan.class)) {
            if (Thread.currentThread().isInterrupted()) {
                throw new InterruptedException();
            }
            int spanStart = spannableStringBuilder.getSpanStart(editLineHeightLiteSpan);
            int spanEnd = spannableStringBuilder.getSpanEnd(editLineHeightLiteSpan);
            spannableStringBuilder.removeSpan(editLineHeightLiteSpan);
            if (spannableStringBuilder.length() > spanEnd && (spannableStringBuilder.charAt(spanEnd) + "").equals(System.lineSeparator())) {
                spannableStringBuilder.setSpan(createEditLineHeightSpan(editLineHeightLiteSpan.getFontSizeLevel()), spanStart, spanEnd + 1, 33);
            } else {
                spannableStringBuilder.setSpan(createEditLineHeightSpan(editLineHeightLiteSpan.getFontSizeLevel()), spanStart, spanEnd, 34);
            }
        }
        return spannableStringBuilder;
    }

    public EditLineHeightSpan createEditLineHeightSpan(int i) {
        return new EditLineHeightSpan(this.mEditorContext, i);
    }

    public CheckboxSpan createCheckboxSpanByElement(HtmlParser.CheckBoxElement checkBoxElement) {
        return new CheckboxSpan(this.mEditorContext, checkBoxElement);
    }

    public CheckboxSpan createCheckboxSpan(boolean z) {
        return new CheckboxSpan(this.mEditorContext, z);
    }

    public EditorBulletSpan createEditorBulletSpan() {
        return new EditorBulletSpan(this.mEditorContext);
    }

    public LinkCardSpan createLinkCardSpan(HtmlParser.GeneralElement generalElement) {
        return new LinkCardSpan(this.mEditorContext, generalElement);
    }

    public BaseImageSpan createImageSpan(HtmlParser.ImageElement imageElement) {
        return new NormalImageSpan(this.mEditorContext, imageElement);
    }

    public BaseAudioSpan createAudioSpan(String str) {
        return createAudioSpan(new HtmlParser.SoundElement(str));
    }

    public BaseAudioSpan createAudioSpan(HtmlParser.SoundElement soundElement) {
        return new BaseAudioSpan(this.mEditorContext, soundElement);
    }

    public KeywordsSpan createKeywordsSpan() {
        return new KeywordsSpan(this.mEditorContext);
    }

    public BaseImageSpan copyBaseImageSpan(BaseImageSpan baseImageSpan) {
        if (baseImageSpan instanceof NormalImageSpan) {
            return copyNormalImageSpan((NormalImageSpan) baseImageSpan);
        }
        if (baseImageSpan instanceof LinkCardSpan) {
            return copyLinkCardSpan((LinkCardSpan) baseImageSpan);
        }
        return null;
    }

    public NormalImageSpan copyNormalImageSpan(NormalImageSpan normalImageSpan) {
        return (NormalImageSpan) createImageSpan(normalImageSpan.getDisplayElement());
    }

    public LinkCardSpan copyLinkCardSpan(LinkCardSpan linkCardSpan) {
        return createLinkCardSpan(linkCardSpan.getDisplayElement());
    }

    public int setCheckboxSpan(Editable editable, CheckboxSpan checkboxSpan, int i, int i2) {
        Logger.INSTANCE.d("RichTextWatcher", "setCheckboxSpan ");
        if (!editable.toString().startsWith(EditorUtils.ZERO_WIDTH_STRING, i)) {
            editable.insert(i, EditorUtils.ZERO_WIDTH_STRING);
            i2 += EditorUtils.ZERO_WIDTH_STRING.length();
        }
        editable.setSpan(checkboxSpan, i, i2, 17);
        return i2;
    }

    public int setEditorBulletSpan(Editable editable, EditorBulletSpan editorBulletSpan, int i, int i2) {
        Logger.INSTANCE.d("RichTextWatcher", "setEditorBulletSpan ");
        if (!editable.toString().startsWith(EditorUtils.ZERO_WIDTH_STRING, i)) {
            editable.insert(i, EditorUtils.ZERO_WIDTH_STRING);
            i2 += EditorUtils.ZERO_WIDTH_STRING.length();
        }
        editable.setSpan(editorBulletSpan, i, i2, 17);
        return i2;
    }

    public void setNormalImageSpan(Editable editable, int i, int i2, String str) {
        setNormalImageSpan(editable, i, i2, str, null);
    }

    public void setNormalImageSpan(Editable editable, int i, int i2, String str, NormalImageSpan normalImageSpan) {
        HtmlParser.ImageElement imageElement = new HtmlParser.ImageElement(str);
        if (normalImageSpan != null) {
            imageElement.setAttribute(HtmlParser.ImageElement.KEY_IMGSHOW, normalImageSpan.isShowBig() ? "0" : "1");
            imageElement.setAttribute(HtmlParser.ImageElement.KEY_DES, normalImageSpan.getAnnotation());
        }
        NormalImageSpan normalImageSpan2 = (NormalImageSpan) createImageSpan(imageElement);
        normalImageSpan2.initByContainer();
        editable.setSpan(normalImageSpan2, i, i2, 33);
    }

    public BaseAudioSpan setAudioSpan(Editable editable, int i, int i2, String str) {
        BaseAudioSpan baseAudioSpanCreateAudioSpan = createAudioSpan(str);
        editable.setSpan(baseAudioSpanCreateAudioSpan, i, i2, 33);
        return baseAudioSpanCreateAudioSpan;
    }

    public void setPlainInputFontSizeSpan(Editable editable, int i, int i2) {
        if (i2 == 1) {
            editable.setSpan(new FontMidSizeSpan(this.mEditorContext), i, i, 18);
        } else {
            if (i2 != 2) {
                return;
            }
            editable.setSpan(new FontLargeSizeSpan(this.mEditorContext), i, i, 18);
        }
    }

    public void setPlainFontSizeSpanByInfo(Editable editable, SpanInfo spanInfo, int i) {
        if (i == 1) {
            editable.setSpan(new FontMidSizeSpan(this.mEditorContext), spanInfo.getStart(), spanInfo.getEnd(), spanInfo.getFlag());
        } else {
            if (i != 2) {
                return;
            }
            editable.setSpan(new FontLargeSizeSpan(this.mEditorContext), spanInfo.getStart(), spanInfo.getEnd(), spanInfo.getFlag());
        }
    }

    public void setPlainFontSizeSpan(Editable editable, int i, int i2, int i3) {
        setPlainFontSizeSpan(editable, i, i2, i3, 34);
    }

    public void setPlainFontSizeSpan(Editable editable, int i, int i2, int i3, int i4) {
        if (i3 == 0) {
            editable.setSpan(new FontNormalSizeSpan(this.mEditorContext), i, i2, i4);
        } else if (i3 == 1) {
            editable.setSpan(new FontMidSizeSpan(this.mEditorContext), i, i2, i4);
        } else {
            if (i3 != 2) {
                return;
            }
            editable.setSpan(new FontLargeSizeSpan(this.mEditorContext), i, i2, i4);
        }
    }

    public void setInputFontSizeSpan(Editable editable, int i, int i2) {
        setPlainInputFontSizeSpan(editable, i, i2);
        if (((CheckboxSpan[]) editable.getSpans(i, i, CheckboxSpan.class)).length <= 0 && ((EditorBulletSpan[]) editable.getSpans(i, i, EditorBulletSpan.class)).length <= 0) {
            if (this.mEditorContext.isNeedEditorLineHeight() && ((EditLineHeightSpan[]) editable.getSpans(i, i, EditLineHeightSpan.class)).length == 0) {
                editable.setSpan(new EditLineHeightSpan(this.mEditorContext, i2), i, i, 34);
            }
            this.mEditorContext.refreshContainer();
        }
    }

    public void setFontSizeSpan(Editable editable, int i, int i2, int i3) {
        setPlainFontSizeSpan(editable, i, i2, i3);
        if (((CheckboxSpan[]) editable.getSpans(i, i2, CheckboxSpan.class)).length <= 0 && ((EditorBulletSpan[]) editable.getSpans(i, i2, EditorBulletSpan.class)).length <= 0) {
            if (this.mEditorContext.isNeedEditorLineHeight() && ((EditLineHeightSpan[]) editable.getSpans(i, i2, EditLineHeightSpan.class)).length == 0) {
                editable.setSpan(new EditLineHeightSpan(this.mEditorContext, i3), i, i2, 34);
            }
            this.mEditorContext.refreshContainer();
        }
    }
}
