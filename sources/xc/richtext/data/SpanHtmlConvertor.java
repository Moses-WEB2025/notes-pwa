package xc.richtext.data;

import android.text.Html;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.ForegroundColorSpan;
import java.util.Locale;
import org.apache.commons.lang3.StringUtils;

/* JADX INFO: loaded from: classes6.dex */
public class SpanHtmlConvertor {
    public static Spanned convertHtmlToSpanned(String str) {
        Spanned spannedFromHtml = Html.fromHtml(str, 1);
        if (spannedFromHtml.toString().endsWith(StringUtils.LF)) {
            spannedFromHtml = (Spanned) spannedFromHtml.subSequence(0, spannedFromHtml.length() - 1);
        }
        int iIndexOf = str.indexOf("<span style=\"font-size:");
        int iIndexOf2 = str.indexOf("px\";>");
        float f = (iIndexOf == -1 || iIndexOf2 == -1) ? 34.0f : Float.parseFloat(str.substring(iIndexOf + 23, iIndexOf2));
        if (spannedFromHtml instanceof SpannableStringBuilder) {
            ((SpannableStringBuilder) spannedFromHtml).setSpan(new AbsoluteSizeSpan((int) f, false), 0, spannedFromHtml.length(), 33);
        }
        return spannedFromHtml;
    }

    public static String convertTextToHtml(String str, int i, float f) {
        SpannableStringBuilder spannableStringBuilder = new SpannableStringBuilder(str);
        spannableStringBuilder.setSpan(new AbsoluteSizeSpan((int) f, false), 0, str.length(), 33);
        spannableStringBuilder.setSpan(new ForegroundColorSpan(i), 0, str.length(), 33);
        String str2 = "<html><body>" + Html.toHtml(spannableStringBuilder, 1) + "</body></html>";
        AbsoluteSizeSpan[] absoluteSizeSpanArr = (AbsoluteSizeSpan[]) spannableStringBuilder.getSpans(0, spannableStringBuilder.length(), AbsoluteSizeSpan.class);
        String str3 = String.format(Locale.ENGLISH, "<span style=\"font-size:%.2fpx\";>", Float.valueOf(absoluteSizeSpanArr.length > 0 ? absoluteSizeSpanArr[0].getSize() : 34.0f));
        int iIndexOf = str2.indexOf("<span style=\"font-size:");
        int iIndexOf2 = str2.indexOf("px\";>");
        return (iIndexOf == -1 || iIndexOf2 == -1) ? str2 : str2.replace(str2.substring(iIndexOf, iIndexOf2 + 5), str3);
    }
}
