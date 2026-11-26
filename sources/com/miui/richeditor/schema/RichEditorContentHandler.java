package com.miui.richeditor.schema;

import android.text.Editable;
import android.text.SpannableStringBuilder;
import android.text.TextUtils;
import com.miui.richeditor.RichEditTextView;
import com.miui.richeditor.style.BaseImageSpan;
import com.miui.richeditor.style.ContactSpan;
import com.miui.richeditor.style.NormalImageSpan;
import com.miui.richeditor.style.SelectableImageSpan;
import com.miui.richeditor.style.UnknownSpan;
import com.miui.richeditor.style.lite.CheckboxLiteSpan;
import com.miui.richeditor.style.lite.EditorBulletLiteSpan;
import com.miui.richeditor.style.lite.EditorQuoteLiteSpan;
import com.miui.richeditor.style.lite.SplitLineSpan;
import java.util.ArrayList;

/* JADX INFO: loaded from: classes4.dex */
public class RichEditorContentHandler implements HtmlParser.IMediaHandler {
    protected RichEditTextView mEditor;

    @Override // com.miui.richeditor.schema.HtmlParser.IMediaHandler
    public HtmlParser.IElementSpan handleDoodle(SpannableStringBuilder spannableStringBuilder, HtmlParser.MiDoodleElement miDoodleElement) {
        return null;
    }

    @Override // com.miui.richeditor.schema.HtmlParser.IMediaHandler
    public void handleEndQuote() {
    }

    @Override // com.miui.richeditor.schema.HtmlParser.IMediaHandler
    public HtmlParser.IElementSpan handleOrder(SpannableStringBuilder spannableStringBuilder, HtmlParser.OrderElement orderElement, int i, int i2) {
        return null;
    }

    @Override // com.miui.richeditor.schema.HtmlParser.IMediaHandler
    public void handleTag(SpannableStringBuilder spannableStringBuilder, String str, boolean z) {
    }

    @Override // com.miui.richeditor.schema.HtmlParser.IMediaHandler
    public void handleUpdateIndent(int i) {
    }

    public RichEditorContentHandler(RichEditTextView richEditTextView) {
        this.mEditor = richEditTextView;
    }

    @Override // com.miui.richeditor.schema.HtmlParser.IMediaHandler
    public HtmlParser.IElementSpan handleInput(SpannableStringBuilder spannableStringBuilder, HtmlParser.InputElement inputElement, int i) {
        if (inputElement instanceof HtmlParser.CheckBoxElement) {
            return new CheckboxLiteSpan((HtmlParser.CheckBoxElement) inputElement);
        }
        return handleUnknown(inputElement);
    }

    @Override // com.miui.richeditor.schema.HtmlParser.IMediaHandler
    public HtmlParser.IElementSpan handleBullet(SpannableStringBuilder spannableStringBuilder, HtmlParser.BulletElement bulletElement, int i) {
        return new EditorBulletLiteSpan(bulletElement);
    }

    @Override // com.miui.richeditor.schema.HtmlParser.IMediaHandler
    public HtmlParser.IElementSpan handleQuote(SpannableStringBuilder spannableStringBuilder, HtmlParser.QuoteElement quoteElement) {
        return new EditorQuoteLiteSpan(quoteElement);
    }

    @Override // com.miui.richeditor.schema.HtmlParser.IMediaHandler
    public HtmlParser.IElementSpan handleSplitLine(SpannableStringBuilder spannableStringBuilder, HtmlParser.SplitLineElement splitLineElement) {
        return new SplitLineSpan(splitLineElement);
    }

    @Override // com.miui.richeditor.schema.HtmlParser.IMediaHandler
    public HtmlParser.IElementSpan handleImage(SpannableStringBuilder spannableStringBuilder, HtmlParser.ImageElement imageElement) {
        BaseImageSpan baseImageSpanCreateImageSpan = this.mEditor.getStyleFactory().createImageSpan(imageElement);
        if (baseImageSpanCreateImageSpan instanceof NormalImageSpan) {
            ((NormalImageSpan) baseImageSpanCreateImageSpan).initByContainer();
        }
        return baseImageSpanCreateImageSpan;
    }

    @Override // com.miui.richeditor.schema.HtmlParser.IMediaHandler
    public HtmlParser.IElementSpan handleLinkCard(SpannableStringBuilder spannableStringBuilder, HtmlParser.LinkCardElement linkCardElement, boolean z) {
        if (z) {
            return this.mEditor.getStyleFactory().createLinkCardSpan(linkCardElement);
        }
        return null;
    }

    @Override // com.miui.richeditor.schema.HtmlParser.IMediaHandler
    public HtmlParser.IElementSpan handleVideo(SpannableStringBuilder spannableStringBuilder, HtmlParser.VideoElement videoElement) {
        return new SelectableImageSpan(this.mEditor, videoElement);
    }

    @Override // com.miui.richeditor.schema.HtmlParser.IMediaHandler
    public HtmlParser.IElementSpan handleAudio(SpannableStringBuilder spannableStringBuilder, HtmlParser.SoundElement soundElement) {
        return this.mEditor.getStyleFactory().createAudioSpan(soundElement);
    }

    @Override // com.miui.richeditor.schema.HtmlParser.IMediaHandler
    public HtmlParser.IElementSpan handleTable(SpannableStringBuilder spannableStringBuilder, HtmlParser.TableElement tableElement) {
        return handleUnknown(tableElement);
    }

    @Override // com.miui.richeditor.schema.HtmlParser.IMediaHandler
    public HtmlParser.IElementSpan handleContact(SpannableStringBuilder spannableStringBuilder, HtmlParser.ContactElement contactElement) {
        return new ContactSpan(this.mEditor, this.mEditor.getImageRender().getContactImage(contactElement.getName()), contactElement);
    }

    @Override // com.miui.richeditor.schema.HtmlParser.IMediaHandler
    public HtmlParser.IElementSpan handleMiui(SpannableStringBuilder spannableStringBuilder, HtmlParser.MiuiElement miuiElement) {
        return handleUnknown(miuiElement);
    }

    @Override // com.miui.richeditor.schema.HtmlParser.IMediaHandler
    public boolean handleText(SpannableStringBuilder spannableStringBuilder, String str) {
        appendAndMarkQueryResult(spannableStringBuilder, str);
        return true;
    }

    private HtmlParser.IElementSpan handleUnknown(HtmlParser.GeneralElement generalElement) {
        return new UnknownSpan(this.mEditor, generalElement);
    }

    private void appendAndMarkQueryResult(Editable editable, CharSequence charSequence) {
        if (TextUtils.isEmpty(charSequence)) {
            return;
        }
        int length = editable.length();
        editable.append(charSequence);
        ArrayList<String> highLightKeyWords = this.mEditor.getHighLightKeyWords();
        if (highLightKeyWords != null && !highLightKeyWords.isEmpty()) {
            for (int i = 0; i < highLightKeyWords.size(); i++) {
                changeHighLight(length, highLightKeyWords.get(i), editable, charSequence);
            }
            return;
        }
        changeHighLight(length, this.mEditor.getQueryContent(), editable, charSequence);
    }

    private void changeHighLight(int i, String str, Editable editable, CharSequence charSequence) {
        if (TextUtils.isEmpty(str)) {
            return;
        }
        String lowerCase = str.toLowerCase();
        String lowerCase2 = charSequence.toString().toLowerCase();
        int length = lowerCase2.length();
        int length2 = lowerCase.length();
        int iIndexOf = 0;
        while (iIndexOf >= 0 && iIndexOf < length) {
            iIndexOf = lowerCase2.indexOf(lowerCase, iIndexOf);
            if (iIndexOf >= 0) {
                int i2 = i + iIndexOf;
                editable.setSpan(this.mEditor.getStyleFactory().createKeywordsSpan(), i2, i2 + length2, 33);
                iIndexOf += length2;
            }
        }
    }
}
