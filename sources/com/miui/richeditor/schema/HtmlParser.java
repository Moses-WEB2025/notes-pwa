package com.miui.richeditor.schema;

import android.content.Context;
import android.text.Editable;
import android.text.Layout;
import android.text.SpannableStringBuilder;
import android.text.TextUtils;
import android.text.style.AlignmentSpan;
import android.text.style.BackgroundColorSpan;
import android.text.style.StrikethroughSpan;
import android.text.style.StyleSpan;
import android.text.style.URLSpan;
import android.text.style.UnderlineSpan;
import com.miui.notes.R;
import com.miui.notes.base.utils.Logger;
import com.miui.richeditor.style.LinkCardInfo;
import com.miui.richeditor.style.LinkCardShareSpan;
import com.miui.richeditor.style.LinkCardSnippetSpan;
import com.miui.richeditor.style.LinkCardSpan;
import com.miui.richeditor.style.lite.EditorQuoteLiteSpan;
import com.miui.richeditor.style.lite.FontH3SizeLiteSpan;
import com.miui.richeditor.style.lite.FontLargeSizeLiteSpan;
import com.miui.richeditor.style.lite.FontMidSizeLiteSpan;
import com.miui.richeditor.style.lite.LinkCardLiteSpan;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Stack;
import kotlin.text.Typography;
import org.apache.commons.lang3.StringUtils;
import org.ccil.cowan.tagsoup.Parser;
import org.ccil.cowan.tagsoup.XMLWriter;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;

/* JADX INFO: loaded from: classes4.dex */
public class HtmlParser extends HtmlToSpannedConverter implements EditorHtml.TagHandler {
    public static final char MEDIA_DUMMY_CHAR = 65532;
    public final String TAG;
    protected Context mContext;
    protected Stack<GeneralElement> mElementStack;
    protected IMediaHandler mHandler;

    public interface IElementSpan {
        GeneralElement getElement();
    }

    public interface IMediaHandler {
        IElementSpan handleAudio(SpannableStringBuilder spannableStringBuilder, SoundElement soundElement);

        IElementSpan handleBullet(SpannableStringBuilder spannableStringBuilder, BulletElement bulletElement, int i);

        IElementSpan handleContact(SpannableStringBuilder spannableStringBuilder, ContactElement contactElement);

        @Deprecated
        IElementSpan handleDoodle(SpannableStringBuilder spannableStringBuilder, MiDoodleElement miDoodleElement);

        void handleEndQuote();

        IElementSpan handleImage(SpannableStringBuilder spannableStringBuilder, ImageElement imageElement);

        IElementSpan handleInput(SpannableStringBuilder spannableStringBuilder, InputElement inputElement, int i);

        IElementSpan handleLinkCard(SpannableStringBuilder spannableStringBuilder, LinkCardElement linkCardElement, boolean z);

        IElementSpan handleMiui(SpannableStringBuilder spannableStringBuilder, MiuiElement miuiElement);

        IElementSpan handleOrder(SpannableStringBuilder spannableStringBuilder, OrderElement orderElement, int i, int i2);

        IElementSpan handleQuote(SpannableStringBuilder spannableStringBuilder, QuoteElement quoteElement);

        IElementSpan handleSplitLine(SpannableStringBuilder spannableStringBuilder, SplitLineElement splitLineElement);

        IElementSpan handleTable(SpannableStringBuilder spannableStringBuilder, TableElement tableElement);

        void handleTag(SpannableStringBuilder spannableStringBuilder, String str, boolean z);

        boolean handleText(SpannableStringBuilder spannableStringBuilder, String str);

        void handleUpdateIndent(int i);

        IElementSpan handleVideo(SpannableStringBuilder spannableStringBuilder, VideoElement videoElement);
    }

    public interface RichTextMediaHandler extends IMediaHandler {
        boolean handleRichText(SpannableStringBuilder spannableStringBuilder);
    }

    public HtmlParser(String str, Parser parser, IMediaHandler iMediaHandler, Context context) {
        super(str, null, null, parser, context);
        this.TAG = "HtmlParser";
        this.mContext = context;
        this.mHandler = iMediaHandler;
        this.mTagHandler = this;
    }

    @Override // com.miui.richeditor.schema.HtmlToSpannedConverter, org.xml.sax.ContentHandler
    public void startDocument() throws SAXException {
        this.mElementStack = new Stack<>();
    }

    @Override // com.miui.richeditor.schema.HtmlToSpannedConverter, org.xml.sax.ContentHandler
    public void endDocument() throws SAXException {
        GeneralElement generalElementPop = null;
        while (!this.mElementStack.isEmpty()) {
            generalElementPop = this.mElementStack.pop();
        }
        if (generalElementPop != null) {
            endElement(generalElementPop);
        }
    }

    @Override // com.miui.richeditor.schema.HtmlToSpannedConverter
    protected void handleStartTag(String str, Attributes attributes) {
        if (!this.mElementStack.isEmpty()) {
            GeneralElement generalElementPeek = this.mElementStack.peek();
            GeneralElement generalElementCreateElement = GeneralElement.createElement(str, attributes);
            generalElementPeek.addElement(generalElementCreateElement);
            this.mElementStack.push(generalElementCreateElement);
            return;
        }
        if (str.equalsIgnoreCase("del")) {
            start(this.mSpannableStringBuilder, new Strike());
            return;
        }
        if (str.equalsIgnoreCase(InputElement.TAG)) {
            startInput(attributes);
            return;
        }
        if (str.equalsIgnoreCase(ImageElement.TAG)) {
            startImg(attributes);
            return;
        }
        if (str.equalsIgnoreCase(LinkCardElement.TAG)) {
            startLinkCard(this.mSpannableStringBuilder, attributes);
            return;
        }
        if (str.equalsIgnoreCase(MiDoodleElement.TAG)) {
            startDoodle(attributes);
            return;
        }
        if (str.equalsIgnoreCase(VideoElement.TAG)) {
            startVideo(attributes);
            return;
        }
        if (str.equalsIgnoreCase(SoundElement.TAG)) {
            startAudio(attributes);
            return;
        }
        if (str.equalsIgnoreCase(TableElement.TAG)) {
            startTable(attributes);
            return;
        }
        if (str.equalsIgnoreCase("miui")) {
            startMiui(attributes);
            return;
        }
        if (str.equalsIgnoreCase("bullet")) {
            startBullet(attributes);
            return;
        }
        if (str.equalsIgnoreCase(OrderElement.TAG)) {
            startOrder(attributes);
            return;
        }
        if (str.equalsIgnoreCase("text")) {
            startTextIndent(attributes);
            return;
        }
        if (str.equalsIgnoreCase(QuoteElement.TAG)) {
            startQuote(attributes);
            super.handleStartTag(str, attributes);
        } else if (str.equalsIgnoreCase(SplitLineElement.TAG)) {
            startSplitLine(attributes);
        } else {
            if (str.equalsIgnoreCase("html") || str.equalsIgnoreCase("body")) {
                return;
            }
            super.handleStartTag(str, attributes);
        }
    }

    @Override // com.miui.richeditor.schema.HtmlToSpannedConverter
    protected void handleEndTag(String str) {
        if (!this.mElementStack.isEmpty()) {
            GeneralElement generalElementPeek = this.mElementStack.peek();
            if (generalElementPeek.getTag().equalsIgnoreCase(str)) {
                this.mElementStack.pop();
                if (this.mElementStack.isEmpty()) {
                    endElement(generalElementPeek);
                    return;
                }
                return;
            }
            return;
        }
        if (str.equalsIgnoreCase("del")) {
            end(this.mSpannableStringBuilder, Strike.class, new StrikethroughSpan());
            return;
        }
        if (str.equalsIgnoreCase(LinkCardElement.TAG)) {
            endLinkCard(this.mSpannableStringBuilder);
        } else if (str.equalsIgnoreCase(QuoteElement.TAG)) {
            super.handleEndTag(str);
            endQuote();
        } else {
            super.handleEndTag(str);
        }
    }

    private void endElement(GeneralElement generalElement) {
        if (generalElement instanceof VideoElement) {
            endVideo((VideoElement) generalElement);
            return;
        }
        if (generalElement instanceof SoundElement) {
            endAudio((SoundElement) generalElement);
        } else if (generalElement instanceof TableElement) {
            endTable((TableElement) generalElement);
        } else if (generalElement instanceof MiuiElement) {
            endMiui((MiuiElement) generalElement);
        }
    }

    @Override // com.miui.richeditor.schema.HtmlToSpannedConverter, org.xml.sax.ContentHandler
    public void characters(char[] cArr, int i, int i2) throws SAXException {
        boolean z;
        if (!this.mElementStack.isEmpty()) {
            this.mElementStack.peek().addElement(new TextElement(String.valueOf(cArr, i, i2)));
            return;
        }
        String strValueOf = String.valueOf(cArr, i, i2);
        if (this.mHandler instanceof RichTextMediaHandler) {
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
                if (((RichTextMediaHandler) this.mHandler).handleRichText(spannableStringBuilder)) {
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

    private int getInputNumber(Attributes attributes) {
        if (attributes != null) {
            try {
                if (attributes.getValue("", "inputnumber") != null && !attributes.getValue("", "inputnumber").equals("")) {
                    return Integer.parseInt(attributes.getValue("", "inputnumber"));
                }
            } catch (Exception e) {
                Logger.INSTANCE.d("HtmlParser", "getInputNumber error " + e);
            }
        }
        return 0;
    }

    private int getIndent(Attributes attributes) {
        if (attributes != null) {
            try {
                if (attributes.getValue("", XMLWriter.INDENT) != null && !attributes.getValue("", XMLWriter.INDENT).equals("")) {
                    return Integer.parseInt(attributes.getValue("", XMLWriter.INDENT));
                }
            } catch (Exception e) {
                Logger.INSTANCE.d("HtmlParser", "getIndent error " + e);
            }
        }
        return 1;
    }

    @Override // com.miui.richeditor.schema.EditorHtml.TagHandler
    public void handleTag(boolean z, String str, Editable editable, XMLReader xMLReader) {
        this.mHandler.handleTag(this.mSpannableStringBuilder, str, z);
    }

    protected void startInput(Attributes attributes) {
        appendMedia(this.mHandler.handleInput(this.mSpannableStringBuilder, InputElement.createElement(attributes), getIndent(attributes)));
    }

    protected void startImg(Attributes attributes) {
        appendMedia(this.mHandler.handleImage(this.mSpannableStringBuilder, new ImageElement(attributes)));
    }

    protected void startLinkCard(SpannableStringBuilder spannableStringBuilder, Attributes attributes) {
        IElementSpan iElementSpanHandleLinkCard = this.mHandler.handleLinkCard(this.mSpannableStringBuilder, new LinkCardElement(attributes), true);
        int length = spannableStringBuilder.length();
        spannableStringBuilder.setSpan(iElementSpanHandleLinkCard, length, length, 17);
    }

    protected void endLinkCard(SpannableStringBuilder spannableStringBuilder) {
        LinkCardElement linkCardElement;
        int length = spannableStringBuilder.length();
        Object last = getLast(spannableStringBuilder, LinkCardSpan.class);
        if (last == null) {
            last = getLast(spannableStringBuilder, LinkCardLiteSpan.class);
        }
        if (last == null) {
            last = getLast(spannableStringBuilder, LinkCardShareSpan.class);
        }
        if (last == null) {
            last = getLast(spannableStringBuilder, LinkCardSnippetSpan.class);
        }
        int spanStart = spannableStringBuilder.getSpanStart(last);
        CharSequence charSequenceSubSequence = spannableStringBuilder.subSequence(spanStart, length);
        if (last == null || spanStart > length) {
            return;
        }
        if (last instanceof LinkCardSpan) {
            LinkCardSpan linkCardSpan = (LinkCardSpan) last;
            linkCardSpan.setTitle(charSequenceSubSequence.toString());
            linkCardElement = (LinkCardElement) linkCardSpan.getElement();
        } else {
            linkCardElement = null;
        }
        if (last instanceof LinkCardLiteSpan) {
            LinkCardLiteSpan linkCardLiteSpan = (LinkCardLiteSpan) last;
            linkCardLiteSpan.setTitle(charSequenceSubSequence.toString());
            linkCardElement = (LinkCardElement) linkCardLiteSpan.getElement();
        } else if (last instanceof LinkCardShareSpan) {
            LinkCardShareSpan linkCardShareSpan = (LinkCardShareSpan) last;
            linkCardShareSpan.getShareElement().setTitle(charSequenceSubSequence.toString());
            linkCardElement = (LinkCardElement) linkCardShareSpan.getElement();
        } else if (last instanceof LinkCardSnippetSpan) {
            LinkCardSnippetSpan linkCardSnippetSpan = (LinkCardSnippetSpan) last;
            ((LinkCardElement) linkCardSnippetSpan.getElement()).setTitle(charSequenceSubSequence.toString());
            LinkCardElement linkCardElement2 = (LinkCardElement) linkCardSnippetSpan.getElement();
            spannableStringBuilder.removeSpan(last);
            this.mHandler.handleLinkCard(this.mSpannableStringBuilder, linkCardElement2, false);
            return;
        }
        spannableStringBuilder.removeSpan(last);
        spannableStringBuilder.replace(spanStart, length, "￼");
        spannableStringBuilder.setSpan(last, spanStart, "￼".length() + spanStart, 33);
        this.mHandler.handleLinkCard(this.mSpannableStringBuilder, linkCardElement, false);
    }

    protected void startDoodle(Attributes attributes) {
        appendMedia(this.mHandler.handleDoodle(this.mSpannableStringBuilder, new MiDoodleElement(attributes)));
    }

    protected void startBullet(Attributes attributes) {
        appendMedia(this.mHandler.handleBullet(this.mSpannableStringBuilder, new BulletElement(), getIndent(attributes)));
    }

    protected void startTextIndent(Attributes attributes) {
        this.mHandler.handleUpdateIndent(getIndent(attributes));
    }

    protected void startOrder(Attributes attributes) {
        appendMedia(this.mHandler.handleOrder(this.mSpannableStringBuilder, new OrderElement(), getIndent(attributes), getInputNumber(attributes)));
    }

    protected void startQuote(Attributes attributes) {
        appendMedia(this.mHandler.handleQuote(this.mSpannableStringBuilder, new QuoteElement()));
    }

    protected void endQuote() {
        this.mHandler.handleEndQuote();
    }

    protected void startSplitLine(Attributes attributes) {
        appendMedia(this.mHandler.handleSplitLine(this.mSpannableStringBuilder, new SplitLineElement()));
    }

    protected void startVideo(Attributes attributes) {
        this.mElementStack.push(new VideoElement(attributes));
    }

    protected void endVideo(VideoElement videoElement) {
        appendMedia(this.mHandler.handleVideo(this.mSpannableStringBuilder, videoElement));
    }

    protected void startAudio(Attributes attributes) {
        this.mElementStack.push(new SoundElement(attributes));
    }

    protected void endAudio(SoundElement soundElement) {
        appendMedia(this.mHandler.handleAudio(this.mSpannableStringBuilder, soundElement));
    }

    protected void startTable(Attributes attributes) {
        this.mElementStack.push(new TableElement(attributes));
    }

    protected void endTable(TableElement tableElement) {
        appendMedia(this.mHandler.handleTable(this.mSpannableStringBuilder, tableElement));
    }

    protected void startMiui(Attributes attributes) {
        this.mElementStack.push(MiuiElement.createElement(attributes));
    }

    protected void endMiui(MiuiElement miuiElement) {
        IElementSpan iElementSpanHandleMiui;
        if (miuiElement instanceof ContactElement) {
            iElementSpanHandleMiui = this.mHandler.handleContact(this.mSpannableStringBuilder, (ContactElement) miuiElement);
        } else {
            iElementSpanHandleMiui = this.mHandler.handleMiui(this.mSpannableStringBuilder, miuiElement);
        }
        appendMedia(iElementSpanHandleMiui);
    }

    protected void appendMedia(Object obj) {
        if (obj != null) {
            SpannableStringBuilder spannableStringBuilder = this.mSpannableStringBuilder;
            int length = spannableStringBuilder.length();
            spannableStringBuilder.append(MEDIA_DUMMY_CHAR);
            spannableStringBuilder.setSpan(obj, length, spannableStringBuilder.length(), 33);
        }
    }

    private static class Strike {
        private Strike() {
        }
    }

    public static class RawURLSpan extends URLSpan {
        public RawURLSpan(String str) {
            super(str);
        }
    }

    public static class GeneralElement {
        protected LinkedHashMap<String, String> mAttrs;
        protected ArrayList<GeneralElement> mChildren;
        protected String mTag;

        protected ArrayList<String> getPreOrderedKeys() {
            return null;
        }

        public static GeneralElement createElement(String str, Attributes attributes) {
            if (ImageElement.TAG.equals(str)) {
                return new ImageElement(attributes);
            }
            if (LinkCardElement.TAG.equals(str)) {
                return new LinkCardElement(attributes);
            }
            if (MiDoodleElement.TAG.equals(str)) {
                return new MiDoodleElement(attributes);
            }
            if (VideoElement.TAG.equals(str)) {
                return new VideoElement(attributes);
            }
            if (SoundElement.TAG.equals(str)) {
                return new SoundElement(attributes);
            }
            if (TableElement.TAG.equals(str)) {
                return new TableElement(attributes);
            }
            if (InputElement.TAG.equals(str)) {
                return InputElement.createElement(attributes);
            }
            if ("miui".equals(str)) {
                return MiuiElement.createElement(attributes);
            }
            return new GeneralElement(str, attributes);
        }

        public GeneralElement(String str, Attributes attributes) {
            this.mTag = str;
            int length = attributes == null ? 0 : attributes.getLength();
            if (length > 0) {
                this.mAttrs = new LinkedHashMap<>(length);
                for (int i = 0; i < length; i++) {
                    this.mAttrs.put(attributes.getQName(i), attributes.getValue(i));
                }
            }
        }

        public String getTag() {
            return this.mTag;
        }

        public void setAttribute(String str, boolean z) {
            if (z) {
                setAttribute(str, String.valueOf(z));
            } else {
                removeAttribute(str);
            }
        }

        public void setAttribute(String str, String str2) {
            if (this.mAttrs == null) {
                this.mAttrs = new LinkedHashMap<>();
            }
            this.mAttrs.put(str, str2);
        }

        public String getAttribute(String str) {
            LinkedHashMap<String, String> linkedHashMap = this.mAttrs;
            if (linkedHashMap == null) {
                return null;
            }
            return linkedHashMap.get(str);
        }

        public boolean getBooleanAttribute(String str) {
            String attribute = getAttribute(str);
            return attribute != null && Boolean.parseBoolean(attribute);
        }

        public void removeAttribute(String str) {
            LinkedHashMap<String, String> linkedHashMap = this.mAttrs;
            if (linkedHashMap != null) {
                linkedHashMap.remove(str);
            }
        }

        public void setAttributes(Map<String, String> map) {
            if (map == null || map.isEmpty()) {
                return;
            }
            LinkedHashMap<String, String> linkedHashMap = this.mAttrs;
            if (linkedHashMap == null) {
                this.mAttrs = new LinkedHashMap<>(map);
            } else {
                linkedHashMap.putAll(map);
            }
        }

        public LinkedHashMap<String, String> getAttributes() {
            return this.mAttrs;
        }

        public void addElement(GeneralElement generalElement) {
            if (this.mChildren == null) {
                this.mChildren = new ArrayList<>();
            }
            this.mChildren.add(generalElement);
        }

        public ArrayList<GeneralElement> getElements() {
            return this.mChildren;
        }

        public void toHtml(Appendable appendable) throws IOException {
            appendable.append(Typography.less).append(this.mTag);
            LinkedHashMap<String, String> linkedHashMap = this.mAttrs;
            if (linkedHashMap != null && !linkedHashMap.isEmpty()) {
                ArrayList<String> preOrderedKeys = getPreOrderedKeys();
                if (preOrderedKeys != null) {
                    for (String str : preOrderedKeys) {
                        if (this.mAttrs.containsKey(str)) {
                            appendAttributeToHtml(appendable, str, this.mAttrs.get(str));
                        }
                    }
                }
                for (Map.Entry<String, String> entry : this.mAttrs.entrySet()) {
                    if (preOrderedKeys == null || !preOrderedKeys.contains(entry.getKey())) {
                        appendAttributeToHtml(appendable, entry.getKey(), entry.getValue());
                    }
                }
            }
            ArrayList<GeneralElement> arrayList = this.mChildren;
            if (arrayList != null && arrayList.size() > 0) {
                appendable.append(Typography.greater);
                Iterator<GeneralElement> it = this.mChildren.iterator();
                while (it.hasNext()) {
                    it.next().toHtml(appendable);
                }
                appendable.append("</").append(this.mTag).append(Typography.greater);
                return;
            }
            appendable.append(" />");
        }

        protected void appendAttributeToHtml(Appendable appendable, String str, String str2) throws IOException {
            appendable.append(' ').append(str).append("=\"").append(TextUtils.htmlEncode(str2)).append('\"');
        }
    }

    public static class TextElement extends GeneralElement {
        public static final String TAG = "";
        private String mText;

        public TextElement(String str) {
            super("", null);
            this.mText = str;
        }

        public String getText() {
            return this.mText;
        }

        @Override // com.miui.richeditor.schema.HtmlParser.GeneralElement
        public void toHtml(Appendable appendable) throws IOException {
            appendable.append(this.mText);
        }
    }

    public static class InputElement extends GeneralElement {
        protected static final String KEY_TYPE = "type";
        public static final String TAG = "input";

        public static InputElement createElement(Attributes attributes) {
            if ("checkbox".equalsIgnoreCase(attributes == null ? null : attributes.getValue("type"))) {
                return new CheckBoxElement(attributes);
            }
            return new InputElement(attributes);
        }

        public InputElement(Attributes attributes) {
            super(TAG, attributes);
        }

        public String getType() {
            return getAttribute("type");
        }

        public void setType(String str) {
            setAttribute("type", str);
        }
    }

    public static class CheckBoxElement extends InputElement {
        private static final String KEY_CHECKED = "checked";
        public static final String TYPE = "checkbox";

        public CheckBoxElement(Attributes attributes) {
            super(attributes);
            setType("checkbox");
        }

        public CheckBoxElement(boolean z) {
            this((Attributes) null);
            setChecked(z);
        }

        public boolean isChecked() {
            return getBooleanAttribute(KEY_CHECKED);
        }

        public void setChecked(boolean z) {
            setAttribute(KEY_CHECKED, z);
        }
    }

    public static class BulletElement extends DisplayElement {
        public static final String TAG = "bullet";

        public BulletElement() {
            super("bullet", null);
        }
    }

    public static class OrderElement extends DisplayElement {
        public static final String TAG = "order";

        public OrderElement() {
            super(TAG, null);
        }
    }

    public static class QuoteElement extends DisplayElement {
        public static final String TAG = "quote";

        public QuoteElement() {
            super(TAG, null);
        }
    }

    public static class SplitLineElement extends DisplayElement {
        public static final String TAG = "hr";

        public SplitLineElement() {
            super(TAG, null);
        }
    }

    public static class ImageElement extends DisplayElement {
        public static final String KEY_DES = "imgdes";
        public static final String KEY_IMGSHOW = "imgshow";
        public static final String TAG = "img";

        public ImageElement(Attributes attributes) {
            super(TAG, attributes);
        }

        public ImageElement(String str) {
            this(str, true, "");
        }

        /* JADX WARN: 'this' call moved to the top of the method (can break code semantics) */
        public ImageElement(String str, boolean z, String str2) {
            this((Attributes) null);
            setAttribute("fileid", str);
            setAttribute(KEY_IMGSHOW, z ? "0" : "1");
            setAttribute(KEY_DES, str2);
        }

        public String getImgShow() {
            return getAttribute(KEY_IMGSHOW);
        }

        public String getImgDes() {
            return getAttribute(KEY_DES);
        }
    }

    public static class LinkCardElement extends DisplayElement {
        public static final String KEY_CONTENT = "content";
        public static final String KEY_LINK = "href";
        public static final String TAG = "a";
        private String mTitle;

        public LinkCardElement(Attributes attributes) {
            super(TAG, attributes);
            this.mTitle = "";
        }

        /* JADX WARN: 'this' call moved to the top of the method (can break code semantics) */
        public LinkCardElement(LinkCardInfo linkCardInfo) {
            this((Attributes) null);
            setAttribute(KEY_LINK, linkCardInfo.link);
            if (!TextUtils.isEmpty(linkCardInfo.title)) {
                this.mTitle = linkCardInfo.title;
            } else {
                this.mTitle = "";
            }
            if (!TextUtils.isEmpty(linkCardInfo.content)) {
                setAttribute("content", linkCardInfo.content);
            }
            if (TextUtils.isEmpty(linkCardInfo.webIconId)) {
                return;
            }
            setAttribute("fileid", linkCardInfo.webIconId);
        }

        public LinkCardElement(String str, String str2) {
            this(str, str2, null, null);
        }

        /* JADX WARN: 'this' call moved to the top of the method (can break code semantics) */
        public LinkCardElement(String str, String str2, String str3, String str4) {
            this((Attributes) null);
            setAttribute(KEY_LINK, str);
            this.mTitle = str2;
            if (!TextUtils.isEmpty(str3)) {
                setAttribute("content", str3);
            }
            if (TextUtils.isEmpty(str4)) {
                return;
            }
            setAttribute("fileid", str4);
        }

        public void setTitle(String str) {
            this.mTitle = str;
        }

        public String getTitle() {
            return this.mTitle;
        }

        public String getLink() {
            return getAttribute(KEY_LINK);
        }

        public String getContent() {
            return getAttribute("content");
        }

        @Override // com.miui.richeditor.schema.HtmlParser.GeneralElement
        public void toHtml(Appendable appendable) throws IOException {
            appendable.append(Typography.less).append(this.mTag).append(StringUtils.SPACE).append(KEY_LINK).append("=\"").append(getLink()).append("\"");
            if (!TextUtils.isEmpty(getContent())) {
                appendable.append(StringUtils.SPACE).append("content").append("=\"").append(getContent()).append("\"");
            }
            if (!TextUtils.isEmpty(getFileId())) {
                appendable.append(StringUtils.SPACE).append("fileid").append("=\"").append(getFileId()).append("\"");
            }
            appendable.append(">");
            appendable.append(this.mTitle).append("</").append(this.mTag).append(">");
        }
    }

    public static class MiDoodleElement extends DisplayElement {
        protected static final String KEY_IMGSHOW = "imgshow";
        public static final String TAG = "midoodle";

        public MiDoodleElement(Attributes attributes) {
            super(TAG, attributes);
        }

        public MiDoodleElement(String str) {
            this(str, true);
        }

        /* JADX WARN: 'this' call moved to the top of the method (can break code semantics) */
        public MiDoodleElement(String str, boolean z) {
            this((Attributes) null);
            setAttribute("fileid", str);
            setAttribute("imgshow", z ? "0" : "1");
        }

        public String getImgShow() {
            return getAttribute("imgshow");
        }
    }

    public static class DisplayElement extends GeneralElement {
        protected static final String KEY_FILEID = "fileid";

        public DisplayElement(String str, Attributes attributes) {
            super(str, attributes);
        }

        public String getFileId() {
            return getAttribute(KEY_FILEID);
        }

        public void setAttribute(String str) {
            setAttribute(KEY_FILEID, str);
        }
    }

    public static class VideoElement extends DisplayElement {
        public static final String TAG = "video";

        public VideoElement(Attributes attributes) {
            super(TAG, attributes);
        }

        /* JADX WARN: 'this' call moved to the top of the method (can break code semantics) */
        public VideoElement(String str) {
            this((Attributes) null);
            setAttribute("fileid", str);
        }
    }

    public static class SoundElement extends DisplayElement {
        public static final String TAG = "sound";

        public SoundElement(Attributes attributes) {
            super(TAG, attributes);
        }

        /* JADX WARN: 'this' call moved to the top of the method (can break code semantics) */
        public SoundElement(String str) {
            this((Attributes) null);
            setAttribute(str);
        }
    }

    public static class TableElement extends GeneralElement {
        public static final String TAG = "table";

        public TableElement(Attributes attributes) {
            super(TAG, attributes);
        }
    }

    public static class MiuiElement extends GeneralElement {
        protected static final String KEY_CLASS = "class";
        public static final String TAG = "miui";

        public static MiuiElement createElement(Attributes attributes) {
            if (ContactElement.CLASS.equalsIgnoreCase(attributes == null ? null : attributes.getValue("class"))) {
                return new ContactElement(attributes);
            }
            return new MiuiElement(attributes);
        }

        public MiuiElement(Attributes attributes) {
            super("miui", attributes);
        }

        public String getType() {
            return getAttribute("class");
        }

        public void setType(String str) {
            setAttribute("class", str);
        }
    }

    public static class ContactElement extends MiuiElement {
        public static final String CLASS = "contact";
        private static final String KEY_PHONE = "phone";
        private static final ArrayList<String> PRE_ORDERED_KEYS;

        static {
            ArrayList<String> arrayList = new ArrayList<>(2);
            PRE_ORDERED_KEYS = arrayList;
            arrayList.add("class");
            arrayList.add("phone");
        }

        public ContactElement(Attributes attributes) {
            super(attributes);
            setType(CLASS);
        }

        public ContactElement(String str, String str2) {
            this(null);
            setAttribute("phone", str2);
            addElement(new TextElement(str));
        }

        public String getName() {
            ArrayList<GeneralElement> elements = getElements();
            if (elements != null && elements.size() == 1 && (elements.get(0) instanceof TextElement)) {
                return ((TextElement) elements.get(0)).getText();
            }
            return null;
        }

        public String getPhone() {
            return getAttribute("phone");
        }

        @Override // com.miui.richeditor.schema.HtmlParser.GeneralElement
        protected ArrayList<String> getPreOrderedKeys() {
            return PRE_ORDERED_KEYS;
        }
    }
}
