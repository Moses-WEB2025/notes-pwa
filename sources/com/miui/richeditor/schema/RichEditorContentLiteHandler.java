package com.miui.richeditor.schema;

import android.text.Editable;
import android.text.SpannableStringBuilder;
import android.text.TextUtils;
import com.miui.richeditor.style.lite.AudioLiteSpan;
import com.miui.richeditor.style.lite.BaseImageLiteSpan;
import com.miui.richeditor.style.lite.CheckboxLiteSpan;
import com.miui.richeditor.style.lite.ContactLiteSpan;
import com.miui.richeditor.style.lite.EditorBulletLiteSpan;
import com.miui.richeditor.style.lite.EditorQuoteLiteSpan;
import com.miui.richeditor.style.lite.LinkCardLiteSpan;
import com.miui.richeditor.style.lite.SplitLineSpan;
import com.miui.richeditor.style.lite.UnknownLiteSpan;

/* JADX INFO: loaded from: classes4.dex */
public class RichEditorContentLiteHandler implements HtmlParser.IMediaHandler {
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
        return new BaseImageLiteSpan(imageElement);
    }

    @Override // com.miui.richeditor.schema.HtmlParser.IMediaHandler
    public HtmlParser.IElementSpan handleLinkCard(SpannableStringBuilder spannableStringBuilder, HtmlParser.LinkCardElement linkCardElement, boolean z) {
        if (z) {
            return new LinkCardLiteSpan(linkCardElement);
        }
        return null;
    }

    @Override // com.miui.richeditor.schema.HtmlParser.IMediaHandler
    public HtmlParser.IElementSpan handleVideo(SpannableStringBuilder spannableStringBuilder, HtmlParser.VideoElement videoElement) {
        return new BaseImageLiteSpan(videoElement);
    }

    @Override // com.miui.richeditor.schema.HtmlParser.IMediaHandler
    public HtmlParser.IElementSpan handleAudio(SpannableStringBuilder spannableStringBuilder, HtmlParser.SoundElement soundElement) {
        return new AudioLiteSpan(soundElement);
    }

    @Override // com.miui.richeditor.schema.HtmlParser.IMediaHandler
    public HtmlParser.IElementSpan handleTable(SpannableStringBuilder spannableStringBuilder, HtmlParser.TableElement tableElement) {
        return handleUnknown(tableElement);
    }

    @Override // com.miui.richeditor.schema.HtmlParser.IMediaHandler
    public HtmlParser.IElementSpan handleContact(SpannableStringBuilder spannableStringBuilder, HtmlParser.ContactElement contactElement) {
        return new ContactLiteSpan(contactElement);
    }

    @Override // com.miui.richeditor.schema.HtmlParser.IMediaHandler
    public HtmlParser.IElementSpan handleMiui(SpannableStringBuilder spannableStringBuilder, HtmlParser.MiuiElement miuiElement) {
        return handleUnknown(miuiElement);
    }

    @Override // com.miui.richeditor.schema.HtmlParser.IMediaHandler
    public boolean handleText(SpannableStringBuilder spannableStringBuilder, String str) {
        appendResult(spannableStringBuilder, str);
        return true;
    }

    private HtmlParser.IElementSpan handleUnknown(HtmlParser.GeneralElement generalElement) {
        return new UnknownLiteSpan(generalElement);
    }

    private void appendResult(Editable editable, CharSequence charSequence) {
        if (TextUtils.isEmpty(charSequence)) {
            return;
        }
        editable.append(charSequence);
    }
}
