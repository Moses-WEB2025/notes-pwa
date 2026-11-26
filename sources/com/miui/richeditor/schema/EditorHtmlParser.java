package com.miui.richeditor.schema;

import android.text.Layout;
import android.text.SpannableStringBuilder;
import android.text.style.AlignmentSpan;
import android.text.style.BackgroundColorSpan;
import android.text.style.StrikethroughSpan;
import android.text.style.StyleSpan;
import android.text.style.UnderlineSpan;
import com.miui.notes.R;
import com.miui.richeditor.IEditorContext;
import com.miui.richeditor.style.LinkCardShareSpan;
import com.miui.richeditor.style.lite.EditorQuoteLiteSpan;
import com.miui.richeditor.style.lite.FontH3SizeLiteSpan;
import com.miui.richeditor.style.lite.FontLargeSizeLiteSpan;
import com.miui.richeditor.style.lite.FontMidSizeLiteSpan;
import org.ccil.cowan.tagsoup.Parser;
import org.xml.sax.SAXException;

/* JADX INFO: loaded from: classes4.dex */
public class EditorHtmlParser extends HtmlParser {
    private IEditorContext mEditorContext;

    public EditorHtmlParser(String str, Parser parser, HtmlParser.IMediaHandler iMediaHandler, IEditorContext iEditorContext) {
        super(str, parser, iMediaHandler, iEditorContext.getContext());
        this.mEditorContext = iEditorContext;
    }

    @Override // com.miui.richeditor.schema.HtmlParser, com.miui.richeditor.schema.HtmlToSpannedConverter, org.xml.sax.ContentHandler
    public void characters(char[] cArr, int i, int i2) throws SAXException {
        boolean z;
        if (!this.mElementStack.isEmpty()) {
            this.mElementStack.peek().addElement(new HtmlParser.TextElement(String.valueOf(cArr, i, i2)));
            return;
        }
        String strValueOf = String.valueOf(cArr, i, i2);
        if (this.mHandler instanceof HtmlParser.RichTextMediaHandler) {
            int length = this.mSpannableStringBuilder.length();
            SpannableStringBuilder spannableStringBuilder = new SpannableStringBuilder(strValueOf);
            if (((HtmlToSpannedConverter.Size[]) this.mSpannableStringBuilder.getSpans(length, length, HtmlToSpannedConverter.Size.class)).length > 0) {
                spannableStringBuilder.setSpan(new FontLargeSizeLiteSpan(), 0, spannableStringBuilder.length(), 33);
                z = true;
            } else {
                z = false;
            }
            if (((HtmlToSpannedConverter.MidSize[]) this.mSpannableStringBuilder.getSpans(length, length, HtmlToSpannedConverter.MidSize.class)).length > 0) {
                spannableStringBuilder.setSpan(new FontMidSizeLiteSpan(), 0, spannableStringBuilder.length(), 33);
                z = true;
            }
            if (((HtmlToSpannedConverter.H3Size[]) this.mSpannableStringBuilder.getSpans(length, length, HtmlToSpannedConverter.H3Size.class)).length > 0) {
                spannableStringBuilder.setSpan(new FontH3SizeLiteSpan(), 0, spannableStringBuilder.length(), 33);
                z = true;
            }
            if (((HtmlToSpannedConverter.Quote[]) this.mSpannableStringBuilder.getSpans(length, length, HtmlToSpannedConverter.Quote.class)).length > 0) {
                spannableStringBuilder.setSpan(new EditorQuoteLiteSpan(), 0, spannableStringBuilder.length(), 33);
                z = true;
            }
            if (((HtmlToSpannedConverter.Bold[]) this.mSpannableStringBuilder.getSpans(length, length, HtmlToSpannedConverter.Bold.class)).length > 0) {
                spannableStringBuilder.setSpan(new StyleSpan(1), 0, spannableStringBuilder.length(), 33);
                z = true;
            }
            if (((HtmlToSpannedConverter.Italic[]) this.mSpannableStringBuilder.getSpans(length, length, HtmlToSpannedConverter.Italic.class)).length > 0) {
                spannableStringBuilder.setSpan(new StyleSpan(2), 0, spannableStringBuilder.length(), 33);
                z = true;
            }
            if (((HtmlToSpannedConverter.Strikethrough[]) this.mSpannableStringBuilder.getSpans(length, length, HtmlToSpannedConverter.Strikethrough.class)).length > 0) {
                spannableStringBuilder.setSpan(new StrikethroughSpan(), 0, spannableStringBuilder.length(), 33);
                z = true;
            }
            if (((HtmlToSpannedConverter.Underline[]) this.mSpannableStringBuilder.getSpans(length, length, HtmlToSpannedConverter.Underline.class)).length > 0) {
                spannableStringBuilder.setSpan(new UnderlineSpan(), 0, spannableStringBuilder.length(), 33);
                z = true;
            }
            if (((HtmlToSpannedConverter.Center[]) this.mSpannableStringBuilder.getSpans(length, length, HtmlToSpannedConverter.Center.class)).length > 0) {
                spannableStringBuilder.setSpan(new AlignmentSpan.Standard(Layout.Alignment.ALIGN_CENTER), 0, spannableStringBuilder.length(), 33);
                z = true;
            }
            if (((HtmlToSpannedConverter.Right[]) this.mSpannableStringBuilder.getSpans(length, length, HtmlToSpannedConverter.Right.class)).length > 0) {
                spannableStringBuilder.setSpan(new AlignmentSpan.Standard(Layout.Alignment.ALIGN_OPPOSITE), 0, spannableStringBuilder.length(), 33);
                z = true;
            }
            if (((HtmlToSpannedConverter.HighLight[]) this.mSpannableStringBuilder.getSpans(length, length, HtmlToSpannedConverter.HighLight.class)).length > 0) {
                spannableStringBuilder.setSpan(new BackgroundColorSpan(this.mContext.getResources().getColor(R.color.theme_yellow_cursor_color)), 0, spannableStringBuilder.length(), 33);
                z = true;
            }
            if (((LinkCardShareSpan[]) this.mSpannableStringBuilder.getSpans(length, length, LinkCardShareSpan.class)).length <= 0 ? z : true) {
                if (((HtmlParser.RichTextMediaHandler) this.mHandler).handleRichText(spannableStringBuilder)) {
                    this.mSpannableStringBuilder.append((CharSequence) strValueOf);
                    return;
                }
                return;
            }
        }
        if (this.mHandler.handleText(this.mSpannableStringBuilder, strValueOf)) {
            return;
        }
        this.mSpannableStringBuilder.append((CharSequence) strValueOf);
    }
}
