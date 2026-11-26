package com.miui.richeditor.schema;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.text.Layout;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.style.AlignmentSpan;
import android.text.style.BackgroundColorSpan;
import android.text.style.ForegroundColorSpan;
import android.text.style.ImageSpan;
import android.text.style.ParagraphStyle;
import android.text.style.QuoteSpan;
import android.text.style.RelativeSizeSpan;
import android.text.style.StrikethroughSpan;
import android.text.style.StyleSpan;
import android.text.style.SubscriptSpan;
import android.text.style.SuperscriptSpan;
import android.text.style.TextAppearanceSpan;
import android.text.style.TypefaceSpan;
import android.text.style.UnderlineSpan;
import androidx.appfunctions.internal.AppSearchAppFunctionReader;
import androidx.core.view.MotionEventCompat;
import ch.qos.logback.core.rolling.helper.IntegerTokenConverter;
import com.miui.aiengine.common.result.SSEConst;
import com.miui.common.stat.EditStat;
import com.miui.common.stat.NotesTextStat;
import com.miui.common.stat.ToDoStat;
import com.miui.notes.R;
import com.miui.notes.base.utils.Logger;
import com.miui.richeditor.style.lite.EditorQuoteLiteSpan;
import com.miui.richeditor.style.lite.FontH3SizeLiteSpan;
import com.miui.richeditor.style.lite.FontLargeSizeLiteSpan;
import com.miui.richeditor.style.lite.FontMidSizeLiteSpan;
import com.xiaomi.opensdk.file.utils.FileSDKUtils;
import defpackage.R2;
import java.io.IOException;
import java.io.StringReader;
import java.util.HashMap;
import miui.cloud.CloudPushConstants;
import org.apache.commons.lang3.StringUtils;
import org.ccil.cowan.tagsoup.Parser;
import org.xml.sax.Attributes;
import org.xml.sax.ContentHandler;
import org.xml.sax.InputSource;
import org.xml.sax.Locator;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;

/* JADX INFO: loaded from: classes4.dex */
public class HtmlToSpannedConverter implements ContentHandler {
    protected Context mContext;
    private EditorHtml.ImageGetter mImageGetter;
    private XMLReader mReader;
    private String mSource;
    protected SpannableStringBuilder mSpannableStringBuilder = new SpannableStringBuilder();
    protected EditorHtml.TagHandler mTagHandler;
    private static final float[] HEADER_SIZES = {1.5f, 1.4f, 1.3f, 1.2f, 1.1f, 1.0f};
    private static HashMap<String, Integer> COLORS = buildColorMap();

    @Override // org.xml.sax.ContentHandler
    public void endDocument() throws SAXException {
    }

    @Override // org.xml.sax.ContentHandler
    public void endPrefixMapping(String str) throws SAXException {
    }

    @Override // org.xml.sax.ContentHandler
    public void ignorableWhitespace(char[] cArr, int i, int i2) throws SAXException {
    }

    @Override // org.xml.sax.ContentHandler
    public void processingInstruction(String str, String str2) throws SAXException {
    }

    @Override // org.xml.sax.ContentHandler
    public void setDocumentLocator(Locator locator) {
    }

    @Override // org.xml.sax.ContentHandler
    public void skippedEntity(String str) throws SAXException {
    }

    @Override // org.xml.sax.ContentHandler
    public void startDocument() throws SAXException {
    }

    @Override // org.xml.sax.ContentHandler
    public void startPrefixMapping(String str, String str2) throws SAXException {
    }

    public HtmlToSpannedConverter(String str, EditorHtml.ImageGetter imageGetter, EditorHtml.TagHandler tagHandler, Parser parser, Context context) {
        this.mSource = str;
        this.mImageGetter = imageGetter;
        this.mTagHandler = tagHandler;
        this.mReader = parser;
        this.mContext = context;
    }

    public Spannable convert() {
        this.mReader.setContentHandler(this);
        try {
            this.mReader.parse(new InputSource(new StringReader(this.mSource)));
            SpannableStringBuilder spannableStringBuilder = this.mSpannableStringBuilder;
            for (Object obj : spannableStringBuilder.getSpans(0, spannableStringBuilder.length(), ParagraphStyle.class)) {
                int spanStart = this.mSpannableStringBuilder.getSpanStart(obj);
                int spanEnd = this.mSpannableStringBuilder.getSpanEnd(obj);
                int i = spanEnd - 2;
                if (i >= 0 && this.mSpannableStringBuilder.charAt(spanEnd - 1) == '\n' && this.mSpannableStringBuilder.charAt(i) == '\n') {
                    spanEnd--;
                }
                if (spanEnd == spanStart) {
                    this.mSpannableStringBuilder.removeSpan(obj);
                } else if ((spanStart == 0 || spanStart == this.mSpannableStringBuilder.length() || this.mSpannableStringBuilder.charAt(spanStart - 1) == '\n') && spanEnd != 0 && spanEnd != this.mSpannableStringBuilder.length()) {
                    try {
                        if (this.mSpannableStringBuilder.charAt(spanEnd - 1) == '\n' && spanEnd >= spanStart) {
                            this.mSpannableStringBuilder.setSpan(obj, spanStart, spanEnd, 51);
                        }
                    } catch (Exception e) {
                        Logger.INSTANCE.e("HtmlToSpannedConverter", "start:" + spanStart + " end:" + spanEnd + " stringLen:" + this.mSpannableStringBuilder.length() + " exception:" + e.getMessage());
                    }
                }
            }
            return this.mSpannableStringBuilder;
        } catch (IOException e2) {
            throw new RuntimeException(e2);
        } catch (SAXException e3) {
            throw new RuntimeException(e3);
        }
    }

    protected void handleStartTag(String str, Attributes attributes) {
        if (str.equalsIgnoreCase("br")) {
            return;
        }
        if (str.equalsIgnoreCase(CloudPushConstants.WATERMARK_TYPE.PERSONAL)) {
            handleP(this.mSpannableStringBuilder);
            return;
        }
        if (str.equalsIgnoreCase("div")) {
            handleP(this.mSpannableStringBuilder);
            return;
        }
        if (str.equalsIgnoreCase("strong")) {
            start(this.mSpannableStringBuilder, new Bold());
            return;
        }
        if (str.equalsIgnoreCase("b")) {
            start(this.mSpannableStringBuilder, new Bold());
            return;
        }
        if (str.equalsIgnoreCase("em")) {
            start(this.mSpannableStringBuilder, new Italic());
            return;
        }
        if (str.equalsIgnoreCase("cite")) {
            start(this.mSpannableStringBuilder, new Italic());
            return;
        }
        if (str.equalsIgnoreCase("dfn")) {
            start(this.mSpannableStringBuilder, new Italic());
            return;
        }
        if (str.equalsIgnoreCase(IntegerTokenConverter.CONVERTER_KEY)) {
            start(this.mSpannableStringBuilder, new Italic());
            return;
        }
        if (str.equalsIgnoreCase("big")) {
            start(this.mSpannableStringBuilder, new Big());
            return;
        }
        if (str.equalsIgnoreCase(FileSDKUtils.J_SMALL_THUMB)) {
            start(this.mSpannableStringBuilder, new Small());
            return;
        }
        if (str.equalsIgnoreCase("size")) {
            start(this.mSpannableStringBuilder, new Size());
            return;
        }
        if (str.equalsIgnoreCase("mid-size")) {
            start(this.mSpannableStringBuilder, new MidSize());
            return;
        }
        if (str.equalsIgnoreCase("h3-size")) {
            start(this.mSpannableStringBuilder, new H3Size());
            return;
        }
        if (str.equalsIgnoreCase("blockquote")) {
            handleP(this.mSpannableStringBuilder);
            start(this.mSpannableStringBuilder, new Blockquote());
            return;
        }
        if (str.equalsIgnoreCase("tt")) {
            start(this.mSpannableStringBuilder, new Monospace());
            return;
        }
        if (str.equalsIgnoreCase(HtmlParser.LinkCardElement.TAG)) {
            startA(this.mSpannableStringBuilder, attributes);
            return;
        }
        if (str.equalsIgnoreCase("u")) {
            start(this.mSpannableStringBuilder, new Underline());
            return;
        }
        if (str.equalsIgnoreCase(ToDoStat.DELETE)) {
            start(this.mSpannableStringBuilder, new Strikethrough());
            return;
        }
        if (str.equalsIgnoreCase("sup")) {
            start(this.mSpannableStringBuilder, new Super());
            return;
        }
        if (str.equalsIgnoreCase("sub")) {
            start(this.mSpannableStringBuilder, new Sub());
            return;
        }
        if (str.equalsIgnoreCase(EditStat.ELEMENT_CENTER)) {
            start(this.mSpannableStringBuilder, new Center());
            return;
        }
        if (str.equalsIgnoreCase(EditStat.ELEMENT_RIGHT)) {
            start(this.mSpannableStringBuilder, new Right());
            return;
        }
        if (str.equalsIgnoreCase(HtmlParser.QuoteElement.TAG)) {
            start(this.mSpannableStringBuilder, new Quote());
            return;
        }
        if (str.length() == 2 && Character.toLowerCase(str.charAt(0)) == 'h' && str.charAt(1) >= '1' && str.charAt(1) <= '6') {
            handleP(this.mSpannableStringBuilder);
            start(this.mSpannableStringBuilder, new Header(str.charAt(1) - '1'));
        } else {
            if (str.equalsIgnoreCase(HtmlParser.ImageElement.TAG)) {
                startImg(this.mSpannableStringBuilder, attributes, this.mImageGetter);
                return;
            }
            if (str.equalsIgnoreCase("background")) {
                startBgColor(this.mSpannableStringBuilder, attributes);
                return;
            }
            EditorHtml.TagHandler tagHandler = this.mTagHandler;
            if (tagHandler != null) {
                tagHandler.handleTag(true, str, this.mSpannableStringBuilder, this.mReader);
            }
        }
    }

    protected void handleEndTag(String str) {
        if (str.equalsIgnoreCase("br")) {
            handleBr(this.mSpannableStringBuilder);
            return;
        }
        if (str.equalsIgnoreCase(CloudPushConstants.WATERMARK_TYPE.PERSONAL)) {
            handleP(this.mSpannableStringBuilder);
            return;
        }
        if (str.equalsIgnoreCase("div")) {
            handleP(this.mSpannableStringBuilder);
            return;
        }
        if (str.equalsIgnoreCase("strong")) {
            end(this.mSpannableStringBuilder, Bold.class, new StyleSpan(1));
            return;
        }
        if (str.equalsIgnoreCase("b")) {
            end(this.mSpannableStringBuilder, Bold.class, new StyleSpan(1));
            return;
        }
        if (str.equalsIgnoreCase("em")) {
            end(this.mSpannableStringBuilder, Italic.class, new StyleSpan(2));
            return;
        }
        if (str.equalsIgnoreCase("cite")) {
            end(this.mSpannableStringBuilder, Italic.class, new StyleSpan(2));
            return;
        }
        if (str.equalsIgnoreCase("dfn")) {
            end(this.mSpannableStringBuilder, Italic.class, new StyleSpan(2));
            return;
        }
        if (str.equalsIgnoreCase(IntegerTokenConverter.CONVERTER_KEY)) {
            end(this.mSpannableStringBuilder, Italic.class, new StyleSpan(2));
            return;
        }
        if (str.equalsIgnoreCase("big")) {
            end(this.mSpannableStringBuilder, Big.class, new RelativeSizeSpan(1.25f));
            return;
        }
        if (str.equalsIgnoreCase(FileSDKUtils.J_SMALL_THUMB)) {
            end(this.mSpannableStringBuilder, Small.class, new RelativeSizeSpan(0.8f));
            return;
        }
        if (str.equalsIgnoreCase("size")) {
            end(this.mSpannableStringBuilder, Size.class, new FontLargeSizeLiteSpan());
            return;
        }
        if (str.equalsIgnoreCase("mid-size")) {
            end(this.mSpannableStringBuilder, MidSize.class, new FontMidSizeLiteSpan());
            return;
        }
        if (str.equalsIgnoreCase("h3-size")) {
            end(this.mSpannableStringBuilder, H3Size.class, new FontH3SizeLiteSpan());
            return;
        }
        if (str.equalsIgnoreCase(HtmlParser.QuoteElement.TAG)) {
            end(this.mSpannableStringBuilder, Quote.class, new EditorQuoteLiteSpan());
            return;
        }
        if (str.equalsIgnoreCase("blockquote")) {
            handleP(this.mSpannableStringBuilder);
            end(this.mSpannableStringBuilder, Blockquote.class, new QuoteSpan());
            return;
        }
        if (str.equalsIgnoreCase("tt")) {
            end(this.mSpannableStringBuilder, Monospace.class, new TypefaceSpan("monospace"));
            return;
        }
        if (str.equalsIgnoreCase(HtmlParser.LinkCardElement.TAG)) {
            endA(this.mSpannableStringBuilder);
            return;
        }
        if (str.equalsIgnoreCase("u")) {
            end(this.mSpannableStringBuilder, Underline.class, new UnderlineSpan());
            return;
        }
        if (str.equalsIgnoreCase(ToDoStat.DELETE)) {
            end(this.mSpannableStringBuilder, Strikethrough.class, new StrikethroughSpan());
            return;
        }
        if (str.equalsIgnoreCase("sup")) {
            end(this.mSpannableStringBuilder, Super.class, new SuperscriptSpan());
            return;
        }
        if (str.equalsIgnoreCase("sub")) {
            end(this.mSpannableStringBuilder, Sub.class, new SubscriptSpan());
            return;
        }
        if (str.equalsIgnoreCase(EditStat.ELEMENT_CENTER)) {
            end(this.mSpannableStringBuilder, Center.class, new AlignmentSpan.Standard(Layout.Alignment.ALIGN_CENTER));
            return;
        }
        if (str.equalsIgnoreCase(EditStat.ELEMENT_RIGHT)) {
            end(this.mSpannableStringBuilder, Right.class, new AlignmentSpan.Standard(Layout.Alignment.ALIGN_OPPOSITE));
            return;
        }
        if (str.length() == 2 && Character.toLowerCase(str.charAt(0)) == 'h' && str.charAt(1) >= '1' && str.charAt(1) <= '6') {
            handleP(this.mSpannableStringBuilder);
            endHeader(this.mSpannableStringBuilder);
        } else {
            if (str.equalsIgnoreCase("background")) {
                endBgColor(this.mSpannableStringBuilder);
                return;
            }
            EditorHtml.TagHandler tagHandler = this.mTagHandler;
            if (tagHandler != null) {
                tagHandler.handleTag(false, str, this.mSpannableStringBuilder, this.mReader);
            }
        }
    }

    private static void handleP(SpannableStringBuilder spannableStringBuilder) {
        int length = spannableStringBuilder.length();
        if (length < 1 || spannableStringBuilder.charAt(length - 1) != '\n') {
            if (length != 0) {
                spannableStringBuilder.append(SSEConst.MSG_SPLIT);
            }
        } else if (length < 2 || spannableStringBuilder.charAt(length - 2) != '\n') {
            spannableStringBuilder.append(StringUtils.LF);
        }
    }

    private static void handleBr(SpannableStringBuilder spannableStringBuilder) {
        spannableStringBuilder.append(StringUtils.LF);
    }

    public static Object getLast(Spanned spanned, Class cls) {
        Object[] spans = spanned.getSpans(0, spanned.length(), cls);
        if (spans.length == 0) {
            return null;
        }
        return spans[spans.length - 1];
    }

    protected static void start(SpannableStringBuilder spannableStringBuilder, Object obj) {
        int length = spannableStringBuilder.length();
        spannableStringBuilder.setSpan(obj, length, length, 17);
    }

    protected void end(SpannableStringBuilder spannableStringBuilder, Class cls, Object obj) {
        int length = spannableStringBuilder.length();
        Object last = getLast(spannableStringBuilder, cls);
        int spanStart = spannableStringBuilder.getSpanStart(last);
        spannableStringBuilder.removeSpan(last);
        if (spanStart != length) {
            if (obj instanceof AlignmentSpan.Standard) {
                spannableStringBuilder.setSpan(obj, spannableStringBuilder.toString().lastIndexOf(10, spanStart) + 1, length, 17);
            } else {
                spannableStringBuilder.setSpan(obj, spanStart, length, 33);
            }
        }
    }

    private static void startImg(SpannableStringBuilder spannableStringBuilder, Attributes attributes, EditorHtml.ImageGetter imageGetter) {
        String value = attributes.getValue("", "src");
        Drawable drawable = imageGetter != null ? imageGetter.getDrawable(value) : null;
        if (drawable == null) {
            drawable = Resources.getSystem().getDrawable(R.drawable.unknown_image);
            drawable.setBounds(0, 0, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());
        }
        int length = spannableStringBuilder.length();
        spannableStringBuilder.append("￼");
        spannableStringBuilder.setSpan(new ImageSpan(drawable, value), length, spannableStringBuilder.length(), 33);
    }

    private static void startBgColor(SpannableStringBuilder spannableStringBuilder, Attributes attributes) {
        String value = attributes.getValue("", "color");
        int length = spannableStringBuilder.length();
        spannableStringBuilder.setSpan(new HighLight(value), length, length, 17);
    }

    private void endBgColor(SpannableStringBuilder spannableStringBuilder) {
        HighLight highLight;
        int length = spannableStringBuilder.length();
        Object last = getLast(spannableStringBuilder, HighLight.class);
        int spanStart = spannableStringBuilder.getSpanStart(last);
        spannableStringBuilder.removeSpan(last);
        if (spanStart == length || (highLight = (HighLight) last) == null || TextUtils.isEmpty(highLight.mColor)) {
            return;
        }
        if (highLight.mColor.startsWith("@")) {
            int identifier = Resources.getSystem().getIdentifier(highLight.mColor.substring(1), "color", AppSearchAppFunctionReader.SYSTEM_PACKAGE_NAME);
            if (identifier != 0) {
                spannableStringBuilder.setSpan(new BackgroundColorSpan(identifier), spanStart, length, 33);
                return;
            }
            return;
        }
        int htmlColor = getHtmlColor(highLight.mColor);
        if (htmlColor != -1) {
            spannableStringBuilder.setSpan(new BackgroundColorSpan(htmlColor | (-16777216)), spanStart, length, 33);
        }
    }

    private static void startFont(SpannableStringBuilder spannableStringBuilder, Attributes attributes) {
        String value = attributes.getValue("", "color");
        String value2 = attributes.getValue("", "face");
        int length = spannableStringBuilder.length();
        spannableStringBuilder.setSpan(new Font(value, value2), length, length, 17);
    }

    private static void endFont(SpannableStringBuilder spannableStringBuilder) {
        int length = spannableStringBuilder.length();
        Object last = getLast(spannableStringBuilder, Font.class);
        int spanStart = spannableStringBuilder.getSpanStart(last);
        spannableStringBuilder.removeSpan(last);
        if (spanStart != length) {
            Font font = (Font) last;
            if (!TextUtils.isEmpty(font.mColor)) {
                if (font.mColor.startsWith("@")) {
                    Resources system = Resources.getSystem();
                    int identifier = system.getIdentifier(font.mColor.substring(1), "color", AppSearchAppFunctionReader.SYSTEM_PACKAGE_NAME);
                    if (identifier != 0) {
                        spannableStringBuilder.setSpan(new TextAppearanceSpan(null, 0, 0, system.getColorStateList(identifier), null), spanStart, length, 33);
                    }
                } else {
                    int htmlColor = getHtmlColor(font.mColor);
                    if (htmlColor != -1) {
                        spannableStringBuilder.setSpan(new ForegroundColorSpan(htmlColor | (-16777216)), spanStart, length, 33);
                    }
                }
            }
            if (font.mFace != null) {
                spannableStringBuilder.setSpan(new TypefaceSpan(font.mFace), spanStart, length, 33);
            }
        }
    }

    private static void startA(SpannableStringBuilder spannableStringBuilder, Attributes attributes) {
        String value = attributes.getValue("", HtmlParser.LinkCardElement.KEY_LINK);
        int length = spannableStringBuilder.length();
        spannableStringBuilder.setSpan(new Href(value), length, length, 17);
    }

    private static void endA(SpannableStringBuilder spannableStringBuilder) {
        int length = spannableStringBuilder.length();
        Object last = getLast(spannableStringBuilder, Href.class);
        int spanStart = spannableStringBuilder.getSpanStart(last);
        spannableStringBuilder.removeSpan(last);
        if (spanStart != length) {
            Href href = (Href) last;
            if (href.mHref != null) {
                spannableStringBuilder.setSpan(new HtmlParser.RawURLSpan(href.mHref), spanStart, length, 33);
            }
        }
    }

    private static void endHeader(SpannableStringBuilder spannableStringBuilder) {
        int length = spannableStringBuilder.length();
        Object last = getLast(spannableStringBuilder, Header.class);
        int spanStart = spannableStringBuilder.getSpanStart(last);
        spannableStringBuilder.removeSpan(last);
        while (length > spanStart && spannableStringBuilder.charAt(length - 1) == '\n') {
            length--;
        }
        if (spanStart != length) {
            spannableStringBuilder.setSpan(new RelativeSizeSpan(HEADER_SIZES[((Header) last).mLevel]), spanStart, length, 33);
            spannableStringBuilder.setSpan(new StyleSpan(1), spanStart, length, 33);
        }
    }

    @Override // org.xml.sax.ContentHandler
    public void startElement(String str, String str2, String str3, Attributes attributes) throws SAXException {
        handleStartTag(str2, attributes);
    }

    @Override // org.xml.sax.ContentHandler
    public void endElement(String str, String str2, String str3) throws SAXException {
        handleEndTag(str2);
    }

    @Override // org.xml.sax.ContentHandler
    public void characters(char[] cArr, int i, int i2) throws SAXException {
        char cCharAt;
        StringBuilder sb = new StringBuilder();
        for (int i3 = 0; i3 < i2; i3++) {
            char c = cArr[i3 + i];
            if (c == ' ' || c == '\n') {
                int length = sb.length();
                if (length == 0) {
                    int length2 = this.mSpannableStringBuilder.length();
                    cCharAt = length2 == 0 ? '\n' : this.mSpannableStringBuilder.charAt(length2 - 1);
                } else {
                    cCharAt = sb.charAt(length - 1);
                }
                if (cCharAt != ' ' && cCharAt != '\n') {
                    sb.append(' ');
                }
            } else {
                sb.append(c);
            }
        }
        this.mSpannableStringBuilder.append((CharSequence) sb);
    }

    protected static class Bold {
        protected Bold() {
        }
    }

    protected static class Italic {
        protected Italic() {
        }
    }

    protected static class Underline {
        protected Underline() {
        }
    }

    protected static class Strikethrough {
        protected Strikethrough() {
        }
    }

    private static class Big {
        private Big() {
        }
    }

    private static class Small {
        private Small() {
        }
    }

    private static class Monospace {
        private Monospace() {
        }
    }

    private static class Blockquote {
        private Blockquote() {
        }
    }

    private static class Super {
        private Super() {
        }
    }

    private static class Sub {
        private Sub() {
        }
    }

    protected static class Size {
        protected Size() {
        }
    }

    protected static class MidSize {
        protected MidSize() {
        }
    }

    protected static class H3Size {
        protected H3Size() {
        }
    }

    protected static class Center {
        protected Center() {
        }
    }

    protected static class Right {
        protected Right() {
        }
    }

    protected static class Quote {
        protected Quote() {
        }
    }

    protected static class HighLight {
        public String mColor;

        public HighLight(String str) {
            this.mColor = str;
        }
    }

    private static class Font {
        public String mColor;
        public String mFace;

        public Font(String str, String str2) {
            this.mColor = str;
            this.mFace = str2;
        }
    }

    private static class Href {
        public String mHref;

        public Href(String str) {
            this.mHref = str;
        }
    }

    private static class Header {
        private int mLevel;

        public Header(int i) {
            this.mLevel = i;
        }
    }

    private static HashMap<String, Integer> buildColorMap() {
        HashMap<String, Integer> map = new HashMap<>();
        map.put("aqua", 65535);
        map.put("black", 0);
        map.put(NotesTextStat.BLUE, 255);
        map.put("fuchsia", 16711935);
        map.put(NotesTextStat.GREEN, 32768);
        map.put("grey", 8421504);
        map.put("lime", Integer.valueOf(MotionEventCompat.ACTION_POINTER_INDEX_MASK));
        map.put("maroon", 8388608);
        map.put("navy", 128);
        map.put("olive", 8421376);
        map.put("purple", 8388736);
        map.put(NotesTextStat.RED, 16711680);
        map.put("silver", 12632256);
        map.put("teal", 32896);
        map.put("white", 16777215);
        map.put(NotesTextStat.YELLOW, 16776960);
        return map;
    }

    private static int getHtmlColor(String str) {
        Integer num = COLORS.get(str.toLowerCase());
        if (num != null) {
            return num.intValue();
        }
        try {
            return convertValueToInt(str, -1);
        } catch (NumberFormatException unused) {
            return -1;
        }
    }

    public static final int convertValueToInt(CharSequence charSequence, int i) {
        int i2;
        int i3;
        if (charSequence == null) {
            return i;
        }
        String string = charSequence.toString();
        int length = string.length();
        if ('-' == string.charAt(0)) {
            i3 = -1;
            i2 = 1;
        } else {
            i2 = 0;
            i3 = 1;
        }
        int i4 = 8;
        int i5 = 16;
        if ('0' == string.charAt(i2)) {
            if (i2 == length - 1) {
                return 0;
            }
            int i6 = i2 + 1;
            char cCharAt = string.charAt(i6);
            if ('x' == cCharAt || 'X' == cCharAt) {
                i2 += 2;
                i4 = 16;
            } else {
                i2 = i6;
            }
            i5 = i4;
        } else if ('#' == string.charAt(i2)) {
            int i7 = i2 + 1;
            if (string.substring(i7).length() == 8) {
                int i8 = Integer.parseInt(string.substring(i2 + 3), 16) * i3;
                return Color.argb(R2.array.ai_panel_widget_blend_colors, (16711680 & i8) >> 16, (65280 & i8) >> 8, i8 & 255);
            }
            i2 = i7;
        } else {
            i5 = 10;
        }
        return Integer.parseInt(string.substring(i2), i5) * i3;
    }
}
