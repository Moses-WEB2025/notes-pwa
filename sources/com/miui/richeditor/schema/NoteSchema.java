package com.miui.richeditor.schema;

import android.content.Context;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.TextUtils;
import com.google.gson.Gson;
import com.miui.notes.NoteApp;
import com.miui.notes.R;
import com.miui.notes.base.utils.Logger;
import com.miui.notes.model.HandWriteEntity;
import com.miui.notes.model.MindDataHelper;
import com.miui.notes.model.MindEntity;
import com.miui.richeditor.IEditorContext;
import com.miui.richeditor.style.AudioSpan;
import com.miui.richeditor.style.EditLineHeightSpan;
import com.miui.richeditor.style.FontLargeSizeSpan;
import com.miui.richeditor.style.FontMidSizeSpan;
import com.miui.richeditor.style.LinkCardInfo;
import com.miui.richeditor.style.NormalImageSpan;
import com.miui.richeditor.style.lite.EditLineHeightLiteSpan;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.apache.commons.lang3.StringUtils;
import org.ccil.cowan.tagsoup.HTMLSchema;
import org.ccil.cowan.tagsoup.Parser;
import org.xml.sax.SAXNotRecognizedException;
import org.xml.sax.SAXNotSupportedException;

/* JADX INFO: loaded from: classes4.dex */
public class NoteSchema {
    private static final String CORRECTION_TAG_END = "</correction>";
    private static final String CORRECTION_TAG_START = "<correction version='v1'>";
    public static final String IMAGE_SUBSTITUTE = "☺";
    public static final String MIUI_DOODLE_SUBSTITUTE = "☼";
    private static final String REPLACE_ANCIENT_CHECK = "<del>$1</del>";
    private static final String REPLACE_ANCIENT_CONTACT = "<miui class=\"contact\" phone=\"$1\">$2</miui>";
    private static final String REPLACE_ANCIENT_IMAGE = "<img fileid=\"$1\" imgshow=\"$2\" imgdes=\"$3\" />";
    private static final String REPLACE_ANCIENT_IMAGE_OLD = "<img fileid=\"$1\" imgshow=\"0\" imgdes=\"\" />";
    private static final String REPLACE_ANCIENT_MIUI_DOODLE = "<midoodle fileid=\"$1\" imgshow=\"$2\" />";
    private static final String REPLACE_MODERN_CHECK = "√ $1";
    private static final String REPLACE_MODERN_CONTACT = "##$1@$2##";
    private static final String REPLACE_MODERN_IMAGE = "☺ $1<$2/><$3/>";
    private static final String REPLACE_MODERN_IMAGE_OLD = "☺ $1<0/>";
    private static final String REPLACE_MODERN_MIUI_DOODLE = "☼ $1<$2/>";
    private static final String TAG = "NoteSchema";
    private static final Pattern PATTERN_ANCIENT_CHECK = Pattern.compile("^√ (.+?)$", 8);
    private static final Pattern PATTERN_ANCIENT_IMAGE_OLD = Pattern.compile("☺ ([^\r\n\\<]+)", 8);
    private static final Pattern PATTERN_ANCIENT_IMAGE = Pattern.compile("☺ ([^\r\n\\<]+)<([^\r\n\\<]+)/><([^\r\n\\<]*)/>", 8);
    private static final Pattern PATTERN_ANCIENT_MIUI_DOODLE = Pattern.compile("☼ ([^\r\n\\<]+)<([^\r\n\\<]+)/>", 8);
    private static final Pattern PATTERN_ANCIENT_IMAGE_NEW = Pattern.compile("<img\\s+fileid\\s*=\\s*\"[^\"]*\"(\\s+\\w+\\s*=\\s*(\"[^\"]*\"|[^>\\\\s]*))*\\s*/>", 8);
    private static final Pattern PATTERN_ANCIENT_CONTACT = Pattern.compile("##([^\r\n]+?)@([^\r\n]+?)##");
    private static final Pattern PATTERN_MODERN_CHECK = Pattern.compile("^<del>(.*?)</del>$", 8);
    private static final Pattern PATTERN_MODERN_IMAGE_OLD = Pattern.compile("<img fileid=\"([^\"]+?)\" />", 8);
    private static final Pattern PATTERN_MODERN_IMAGE = Pattern.compile("<img fileid=\"([^\"]+?)\" imgshow=\"([^\"]+?)\" imgdes=\"([^\"]*?)\" />", 8);
    private static final Pattern PATTERN_MODERN_IMAGE_SPECIAL = Pattern.compile("<img fileid=\"([^\"]+?)\" imgshow=\"([^\"]+?)\" imgdes=\"([^\"]*?)\n\" />", 8);
    private static final Pattern PATTERN_MODERN_MIUI_DOODLE = Pattern.compile("<midoodle fileid=\"([^\"]+?)\" imgshow=\"([^\"]+?)\" />", 8);
    private static final Pattern PATTERN_MODERN_CONTACT = Pattern.compile("<miui class=\"contact\" phone=\"([^\"]+?)\">([^<]+?)</miui>");
    private static final Pattern PATTERN_MODERN_TAG = Pattern.compile("<[^>]+>");
    private static final Pattern PATTERN_MODERN_TAG_WITH_LINEBREAK = Pattern.compile("<[^>]+>\n");
    private static final Pattern PATTERN_MODERN_TAG_WITHOUT_LINEBREAK = Pattern.compile("<[^>]+>");
    private static final Pattern PATTERN_AI_TRIM = Pattern.compile("<p class=\"pm-size-s1 ltr-element show\" data-indentation=\"1\" style=\"\">[^>]*</p>");
    private static final Pattern CORRECTION_DELETE_TAG = Pattern.compile("<correction [^>]+><delete>((?!<delete>).)*</delete>");
    private static final Pattern CORRECTION_DELETE_STAR = Pattern.compile("<correction [^>]+>");
    private static final Pattern PATTERN_INTERNAL_MEDIA = Pattern.compile("fileid=\"([^\"]+?)\"");
    private static final Pattern PATTERN_INPUT_CHECKBOX = Pattern.compile("<input [^>]*type=\"checkbox\"[^>]*>");

    public static class HTMLSchemaHolder {
        private static final HTMLSchema schema = new HTMLSchema();
    }

    public static String addLinkCardTestInfo(String str) {
        return str;
    }

    public static void testRegex(HtmlParser.IMediaHandler iMediaHandler, Context context) {
        String strReplaceAll = PATTERN_ANCIENT_IMAGE.matcher("☺ 0dd19f0da4c36d24807245e5eb7a7dd8763b4aff<0/></>").replaceAll(REPLACE_ANCIENT_IMAGE);
        String strReplaceAll2 = PATTERN_MODERN_IMAGE.matcher("POS您 <img fileid=\"52c1ef4328c86a39a36c8fbcb9804b8ade778591\" imgshow=\"0\" imgdes=\"\" /> 吐了").replaceAll(REPLACE_MODERN_IMAGE);
        Logger.INSTANCE.i(TAG, "正则测试结果 " + strReplaceAll);
        Logger.INSTANCE.i(TAG, "正则测试结果 modern " + strReplaceAll2);
        Logger.INSTANCE.i(TAG, "正则测试结果 result = " + ((Object) parse(strReplaceAll, iMediaHandler, context)));
    }

    public static Spannable parse(String str, HtmlParser.IMediaHandler iMediaHandler, Context context) {
        Parser parser = new Parser();
        try {
            parser.setProperty(Parser.schemaProperty, HTMLSchemaHolder.schema);
            return new HtmlParser("<body>" + str + "</body>", parser, iMediaHandler, context).convert();
        } catch (SAXNotRecognizedException e) {
            throw new RuntimeException(e);
        } catch (SAXNotSupportedException e2) {
            throw new RuntimeException(e2);
        }
    }

    public static Spannable parseForView(String str, HtmlParser.IMediaHandler iMediaHandler, IEditorContext iEditorContext) {
        Parser parser = new Parser();
        try {
            parser.setProperty(Parser.schemaProperty, HTMLSchemaHolder.schema);
            return new EditorHtmlParser("<body>" + str + "</body>", parser, iMediaHandler, iEditorContext).convert();
        } catch (SAXNotRecognizedException e) {
            throw new RuntimeException(e);
        } catch (SAXNotSupportedException e2) {
            throw new RuntimeException(e2);
        }
    }

    public static String build(Context context, Spanned spanned) {
        return new HtmlBuilder(context, spanned).build();
    }

    public static Spannable parseAncient(String str, HtmlParser.IMediaHandler iMediaHandler, Context context) {
        return parse(convertFromAncient(str), iMediaHandler, context);
    }

    public static Spannable parseAncientForView(String str, HtmlParser.IMediaHandler iMediaHandler, IEditorContext iEditorContext) {
        Spannable forView = parseForView(convertFromAncient(str), iMediaHandler, iEditorContext);
        int i = 0;
        for (int i2 = 0; i2 < forView.length(); i2++) {
            if ((forView.charAt(i2) + "").equals(System.lineSeparator())) {
                updateCommSpan(iEditorContext, forView, i, i2, true);
                i = i2 + 1;
            }
        }
        if (i != forView.length()) {
            updateCommSpan(iEditorContext, forView, i, forView.length(), false);
        }
        return forView;
    }

    private static void updateCommSpan(IEditorContext iEditorContext, Spannable spannable, int i, int i2, boolean z) {
        FontLargeSizeSpan[] fontLargeSizeSpanArr = (FontLargeSizeSpan[]) spannable.getSpans(i, i, FontLargeSizeSpan.class);
        FontMidSizeSpan[] fontMidSizeSpanArr = (FontMidSizeSpan[]) spannable.getSpans(i, i, FontMidSizeSpan.class);
        NormalImageSpan[] normalImageSpanArr = (NormalImageSpan[]) spannable.getSpans(i, i, NormalImageSpan.class);
        AudioSpan[] audioSpanArr = (AudioSpan[]) spannable.getSpans(i, i, AudioSpan.class);
        if (fontLargeSizeSpanArr.length >= 1 || fontMidSizeSpanArr.length >= 1 || normalImageSpanArr.length >= 1 || audioSpanArr.length >= 1) {
            return;
        }
        for (EditLineHeightLiteSpan editLineHeightLiteSpan : (EditLineHeightLiteSpan[]) spannable.getSpans(i, i2, EditLineHeightLiteSpan.class)) {
            if (spannable.getSpanStart(editLineHeightLiteSpan) >= i && spannable.getSpanEnd(editLineHeightLiteSpan) <= i2) {
                return;
            }
        }
        for (EditLineHeightSpan editLineHeightSpan : (EditLineHeightSpan[]) spannable.getSpans(i, i2, EditLineHeightSpan.class)) {
            if (spannable.getSpanStart(editLineHeightSpan) >= i && spannable.getSpanEnd(editLineHeightSpan) <= i2) {
                return;
            }
        }
        if (z) {
            if (iEditorContext.isNeedEditorLineHeight()) {
                if (i < spannable.length() && spannable.charAt(i) == 65532) {
                    int i3 = i + 1;
                    if (i3 >= spannable.length() || spannable.charAt(i3) != '\n') {
                        spannable.setSpan(new EditLineHeightLiteSpan(0), i3, i2 + 1, 33);
                        return;
                    }
                    return;
                }
                int i4 = i2 + 1;
                if (StringUtils.LF.equals(spannable.subSequence(i, i4).toString())) {
                    spannable.setSpan(new EditLineHeightLiteSpan(0), i, i4, 17);
                    return;
                } else {
                    spannable.setSpan(new EditLineHeightLiteSpan(0), i, i4, 33);
                    return;
                }
            }
            return;
        }
        if (iEditorContext.isNeedEditorLineHeight()) {
            if (i < spannable.length() && spannable.charAt(i) == 65532) {
                spannable.setSpan(new EditLineHeightLiteSpan(0), i + 1, i2, 34);
            } else {
                spannable.setSpan(new EditLineHeightLiteSpan(0), i, i2, 34);
            }
        }
    }

    public static String buildAncient(Context context, SpannableStringBuilder spannableStringBuilder) {
        ArrayList arrayList = new ArrayList();
        int i = 0;
        for (int i2 = 0; i2 < spannableStringBuilder.length(); i2++) {
            if ((spannableStringBuilder.charAt(i2) + "").equals(System.lineSeparator())) {
                arrayList.add((SpannableStringBuilder) spannableStringBuilder.subSequence(i, i2));
                i = i2 + 1;
            }
        }
        if (i != spannableStringBuilder.length()) {
            arrayList.add((SpannableStringBuilder) spannableStringBuilder.subSequence(i, spannableStringBuilder.length()));
        }
        StringBuilder sb = new StringBuilder();
        for (int i3 = 0; i3 < arrayList.size(); i3++) {
            sb.append(build(context, (Spanned) arrayList.get(i3)));
            if (i3 < arrayList.size() - 1) {
                sb.append(System.lineSeparator());
            }
        }
        if (spannableStringBuilder.length() > 0 && (spannableStringBuilder.charAt(spannableStringBuilder.length() - 1) + "").equals(System.lineSeparator())) {
            sb.append(System.lineSeparator());
        }
        return convertToAncient(sb.toString());
    }

    public static List<String> retrieveMedias(String str) {
        Matcher matcher = PATTERN_INTERNAL_MEDIA.matcher(str);
        ArrayList arrayList = new ArrayList();
        while (matcher.find()) {
            arrayList.add(matcher.group(1));
        }
        return arrayList;
    }

    public static List<String> retrieveMediasAncient(String str) {
        return retrieveMedias(convertFromAncient(str));
    }

    public static String retrieveMindThumbnail(String str) {
        if (!TextUtils.isEmpty(str) && str.startsWith(MindDataHelper.getMindDataTag())) {
            String str2 = ((MindEntity) new Gson().fromJson(str.substring(15), MindEntity.class)).thumbnail;
            if (!TextUtils.isEmpty(str2)) {
                return str2;
            }
        }
        return null;
    }

    public static String retrieveHandWriteThumbnail(String str) {
        HandWriteEntity handWriteEntity;
        if (TextUtils.isEmpty(str) || (handWriteEntity = HandWriteEntity.toHandWriteEntity(str)) == null) {
            return null;
        }
        String str2 = handWriteEntity.thumbnail;
        if (TextUtils.isEmpty(str2)) {
            return null;
        }
        return str2;
    }

    public static String retrieveFirstImage(String str) {
        Matcher matcher = PATTERN_MODERN_IMAGE.matcher(str);
        if (matcher.find()) {
            return matcher.group(1);
        }
        Matcher matcher2 = PATTERN_MODERN_IMAGE_OLD.matcher(str);
        if (matcher2.find()) {
            return matcher2.group(1);
        }
        Matcher matcher3 = PATTERN_MODERN_MIUI_DOODLE.matcher(str);
        if (matcher3.find()) {
            return matcher3.group(1);
        }
        Matcher matcher4 = PATTERN_ANCIENT_IMAGE.matcher(str);
        if (matcher4.find()) {
            return matcher4.group(1);
        }
        Matcher matcher5 = PATTERN_ANCIENT_IMAGE_OLD.matcher(str);
        if (matcher5.find()) {
            return matcher5.group(1);
        }
        Matcher matcher6 = PATTERN_ANCIENT_MIUI_DOODLE.matcher(str);
        if (matcher6.find()) {
            return matcher6.group(1);
        }
        return null;
    }

    public static String removeImagesTag(String str) {
        return PATTERN_ANCIENT_IMAGE_NEW.matcher(PATTERN_ANCIENT_MIUI_DOODLE.matcher(PATTERN_ANCIENT_IMAGE_OLD.matcher(PATTERN_ANCIENT_IMAGE.matcher(str).replaceAll("")).replaceAll("")).replaceAll("")).replaceAll("");
    }

    private static String convertFromAncient(String str) {
        return PATTERN_ANCIENT_CONTACT.matcher(PATTERN_ANCIENT_MIUI_DOODLE.matcher(PATTERN_ANCIENT_IMAGE_OLD.matcher(PATTERN_ANCIENT_IMAGE.matcher(PATTERN_ANCIENT_CHECK.matcher(str).replaceAll(REPLACE_ANCIENT_CHECK)).replaceAll(REPLACE_ANCIENT_IMAGE)).replaceAll(REPLACE_ANCIENT_IMAGE_OLD)).replaceAll(REPLACE_ANCIENT_MIUI_DOODLE)).replaceAll(REPLACE_ANCIENT_CONTACT);
    }

    public static String convertToAncient(String str) {
        return PATTERN_MODERN_CONTACT.matcher(PATTERN_MODERN_MIUI_DOODLE.matcher(PATTERN_MODERN_IMAGE_OLD.matcher(PATTERN_MODERN_IMAGE.matcher(PATTERN_MODERN_IMAGE_SPECIAL.matcher(PATTERN_MODERN_CHECK.matcher(str).replaceAll(REPLACE_MODERN_CHECK)).replaceAll(REPLACE_ANCIENT_IMAGE)).replaceAll(REPLACE_MODERN_IMAGE)).replaceAll(REPLACE_MODERN_IMAGE_OLD)).replaceAll(REPLACE_MODERN_MIUI_DOODLE)).replaceAll(REPLACE_MODERN_CONTACT);
    }

    public static String imgConvertToAncient(String str) {
        return PATTERN_MODERN_IMAGE_SPECIAL.matcher(str).replaceAll(REPLACE_ANCIENT_IMAGE);
    }

    public static String convertToWeb(String str) {
        if (str == null) {
            str = "";
        }
        return PATTERN_ANCIENT_IMAGE.matcher(str).replaceAll(REPLACE_ANCIENT_IMAGE);
    }

    public static boolean containsSchema(String str) {
        return matches(str, PATTERN_ANCIENT_CHECK) || matches(str, PATTERN_ANCIENT_IMAGE) || matches(str, PATTERN_ANCIENT_IMAGE_OLD) || matches(str, PATTERN_ANCIENT_MIUI_DOODLE) || matches(str, PATTERN_ANCIENT_CONTACT) || str.contains("<img") || str.contains("<input") || str.contains("<center") || str.contains("<b") || str.contains("<i") || str.contains("<size");
    }

    public static String extractPlainText(String str) {
        return PATTERN_MODERN_TAG.matcher(PATTERN_MODERN_TAG_WITH_LINEBREAK.matcher(convertFromAncient(str)).replaceAll("")).replaceAll("");
    }

    public static String extractPlainTextForNotes(String str) {
        return PATTERN_MODERN_TAG.matcher(PATTERN_MODERN_TAG_WITHOUT_LINEBREAK.matcher(convertFromAncient(str)).replaceAll("")).replaceAll("");
    }

    public static String removeCheckboxTag(String str) {
        return PATTERN_INPUT_CHECKBOX.matcher(str).replaceAll("");
    }

    private static boolean matches(CharSequence charSequence, Pattern pattern) {
        return pattern.matcher(charSequence).find();
    }

    public static String trimCorrectionData(String str) {
        return CORRECTION_DELETE_STAR.matcher(CORRECTION_DELETE_TAG.matcher(str).replaceAll(CORRECTION_TAG_START)).replaceAll("").replaceAll(CORRECTION_TAG_END, "");
    }

    public static String trimAiTextForNotes(String str) {
        return PATTERN_AI_TRIM.matcher(str).replaceAll("");
    }

    public static class SimpleBuilder {
        private Context mContext;
        private SpannableStringBuilder mOut = new SpannableStringBuilder();

        public SimpleBuilder(Context context) {
            this.mContext = context;
        }

        public void appendText(SpannableStringBuilder spannableStringBuilder) {
            this.mOut.append((CharSequence) HtmlBuilder.escapeHtml(this.mContext, spannableStringBuilder));
        }

        public void appendText(String str) {
            this.mOut.append((CharSequence) HtmlBuilder.escapeHtml(this.mContext, str));
        }

        public void appendImage(String str) {
            appendImage(str, true, "");
        }

        public void appendImage(String str, boolean z, String str2) {
            appendMedia(new HtmlParser.ImageElement(str, z, str2));
        }

        public void appendLink(String str, String str2) {
            if (TextUtils.isEmpty(str2)) {
                str2 = NoteApp.getInstance().getString(R.string.quick_note_link_default_text);
            }
            appendMedia(new HtmlParser.LinkCardElement(new LinkCardInfo(str, str2)));
        }

        public void appendAudio(String str) {
            if (TextUtils.isEmpty(str)) {
                return;
            }
            appendMedia(new HtmlParser.SoundElement(str));
        }

        private void appendMedia(HtmlParser.GeneralElement generalElement) {
            try {
                generalElement.toHtml(this.mOut);
            } catch (IOException e) {
                Logger.INSTANCE.e(NoteSchema.TAG, "Fail to append media,excpetion:" + e);
            }
        }

        public void appendNewLine() {
            this.mOut.append('\n');
        }

        public boolean isEmpty() {
            return this.mOut.length() == 0;
        }

        public String toHtml() {
            return this.mOut.toString();
        }

        public String toAncient() {
            return NoteSchema.convertToAncient(toHtml());
        }
    }
}
