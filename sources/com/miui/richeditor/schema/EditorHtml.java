package com.miui.richeditor.schema;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.text.Editable;
import android.text.Layout;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.AlignmentSpan;
import android.text.style.BackgroundColorSpan;
import android.text.style.CharacterStyle;
import android.text.style.ForegroundColorSpan;
import android.text.style.ImageSpan;
import android.text.style.ParagraphStyle;
import android.text.style.QuoteSpan;
import android.text.style.StrikethroughSpan;
import android.text.style.StyleSpan;
import android.text.style.SubscriptSpan;
import android.text.style.SuperscriptSpan;
import android.text.style.TypefaceSpan;
import android.text.style.URLSpan;
import android.text.style.UnderlineSpan;
import com.miui.richeditor.style.FontLargeSizeSpan;
import com.miui.richeditor.style.FontMidSizeSpan;
import java.text.Bidi;
import org.apache.commons.lang3.StringUtils;
import org.ccil.cowan.tagsoup.HTMLSchema;
import org.ccil.cowan.tagsoup.Parser;
import org.xml.sax.SAXNotRecognizedException;
import org.xml.sax.SAXNotSupportedException;
import org.xml.sax.XMLReader;

/* JADX INFO: loaded from: classes4.dex */
public class EditorHtml {

    public interface ImageGetter {
        Drawable getDrawable(String str);
    }

    public interface TagHandler {
        void handleTag(boolean z, String str, Editable editable, XMLReader xMLReader);
    }

    public static Spanned fromHtml(String str, Context context) {
        return fromHtml(str, null, null, context);
    }

    private static class HtmlParser {
        private static final HTMLSchema schema = new HTMLSchema();

        private HtmlParser() {
        }
    }

    public static Spanned fromHtml(String str, ImageGetter imageGetter, TagHandler tagHandler, Context context) {
        Parser parser = new Parser();
        try {
            parser.setProperty(Parser.schemaProperty, HtmlParser.schema);
            return new HtmlToSpannedConverter(str, imageGetter, tagHandler, parser, context).convert();
        } catch (SAXNotRecognizedException e) {
            throw new RuntimeException(e);
        } catch (SAXNotSupportedException e2) {
            throw new RuntimeException(e2);
        }
    }

    public static String toHtml(Context context, Spanned spanned) {
        StringBuilder sb = new StringBuilder();
        withinHtml(context, sb, spanned);
        return sb.toString();
    }

    public static String escapeHtml(CharSequence charSequence) {
        StringBuilder sb = new StringBuilder();
        withinStyle(sb, charSequence, 0, charSequence.length());
        return sb.toString();
    }

    private static void withinHtml(Context context, StringBuilder sb, Spanned spanned) {
        int length = spanned.length();
        int i = 0;
        while (i < spanned.length()) {
            int iNextSpanTransition = spanned.nextSpanTransition(i, length, ParagraphStyle.class);
            ParagraphStyle[] paragraphStyleArr = (ParagraphStyle[]) spanned.getSpans(i, iNextSpanTransition, ParagraphStyle.class);
            String str = StringUtils.SPACE;
            boolean z = false;
            for (ParagraphStyle paragraphStyle : paragraphStyleArr) {
                if (paragraphStyle instanceof AlignmentSpan) {
                    Layout.Alignment alignment = ((AlignmentSpan) paragraphStyle).getAlignment();
                    if (alignment == Layout.Alignment.ALIGN_CENTER) {
                        str = "align=\"center\" " + str;
                    } else if (alignment == Layout.Alignment.ALIGN_OPPOSITE) {
                        str = "align=\"right\" " + str;
                    } else {
                        str = "align=\"left\" " + str;
                    }
                    z = true;
                }
            }
            if (z) {
                sb.append("<div " + str + ">");
            }
            withinDiv(context, sb, spanned, i, iNextSpanTransition);
            if (z) {
                sb.append("</div>");
            }
            i = iNextSpanTransition;
        }
    }

    private static void withinDiv(Context context, StringBuilder sb, Spanned spanned, int i, int i2) {
        while (i < i2) {
            int iNextSpanTransition = spanned.nextSpanTransition(i, i2, QuoteSpan.class);
            QuoteSpan[] quoteSpanArr = (QuoteSpan[]) spanned.getSpans(i, iNextSpanTransition, QuoteSpan.class);
            for (QuoteSpan quoteSpan : quoteSpanArr) {
                sb.append("<blockquote>");
            }
            withinBlockquote(context, sb, spanned, i, iNextSpanTransition);
            for (QuoteSpan quoteSpan2 : quoteSpanArr) {
                sb.append("</blockquote>\n");
            }
            i = iNextSpanTransition;
        }
    }

    private static String getOpenParaTagWithDirection(Spanned spanned, int i, int i2) {
        if (new Bidi(spanned.subSequence(i, i2).toString(), -2).isRightToLeft()) {
            return "<p dir=rtl>";
        }
        return "<p dir=ltr>";
    }

    private static void withinBlockquote(Context context, StringBuilder sb, Spanned spanned, int i, int i2) {
        sb.append(getOpenParaTagWithDirection(spanned, i, i2));
        int i3 = i;
        while (i3 < i2) {
            int iIndexOf = TextUtils.indexOf((CharSequence) spanned, '\n', i3, i2);
            if (iIndexOf < 0) {
                iIndexOf = i2;
            }
            boolean z = false;
            int i4 = 0;
            while (iIndexOf < i2 && spanned.charAt(iIndexOf) == '\n') {
                i4++;
                iIndexOf++;
            }
            int i5 = iIndexOf - i4;
            if (iIndexOf == i2) {
                z = true;
            }
            StringBuilder sb2 = sb;
            Spanned spanned2 = spanned;
            boolean z2 = z;
            Context context2 = context;
            withinParagraph(context2, sb2, spanned2, i3, i5, i4, z2);
            i3 = iIndexOf;
            context = context2;
            sb = sb2;
            spanned = spanned2;
        }
        sb.append("</p>\n");
    }

    private static void withinParagraph(Context context, StringBuilder sb, Spanned spanned, int i, int i2, int i3, boolean z) {
        int i4 = i;
        while (i4 < i2) {
            int iNextSpanTransition = spanned.nextSpanTransition(i4, i2, CharacterStyle.class);
            Object[] objArr = (CharacterStyle[]) spanned.getSpans(i4, iNextSpanTransition, CharacterStyle.class);
            for (int i5 = 0; i5 < objArr.length; i5++) {
                Object obj = objArr[i5];
                if (obj instanceof StyleSpan) {
                    int style = ((StyleSpan) obj).getStyle();
                    if ((style & 1) != 0) {
                        sb.append("<b>");
                    }
                    if ((style & 2) != 0) {
                        sb.append("<i>");
                    }
                }
                Object obj2 = objArr[i5];
                if ((obj2 instanceof TypefaceSpan) && ((TypefaceSpan) obj2).getFamily().equals("monospace")) {
                    sb.append("<tt>");
                }
                if (objArr[i5] instanceof SuperscriptSpan) {
                    sb.append("<sup>");
                }
                if (objArr[i5] instanceof SubscriptSpan) {
                    sb.append("<sub>");
                }
                if (objArr[i5] instanceof UnderlineSpan) {
                    sb.append("<u>");
                }
                if (objArr[i5] instanceof StrikethroughSpan) {
                    sb.append("<strike>");
                }
                if (objArr[i5] instanceof BackgroundColorSpan) {
                    sb.append("<background color =\"#");
                    String hexString = Integer.toHexString(((BackgroundColorSpan) objArr[i5]).getBackgroundColor() + 16777216);
                    while (hexString.length() < 6) {
                        hexString = "0" + hexString;
                    }
                    sb.append(hexString);
                    sb.append("\">");
                }
                if (objArr[i5] instanceof URLSpan) {
                    sb.append("<a href=\"");
                    sb.append(((URLSpan) objArr[i5]).getURL());
                    sb.append("\">");
                }
                if (objArr[i5] instanceof ImageSpan) {
                    sb.append("<img src=\"");
                    sb.append(((ImageSpan) objArr[i5]).getSource());
                    sb.append("\">");
                    i4 = iNextSpanTransition;
                }
                if (objArr[i5] instanceof FontLargeSizeSpan) {
                    sb.append("<size>");
                }
                if (objArr[i5] instanceof FontMidSizeSpan) {
                    sb.append("<mid-size>");
                }
                Object obj3 = objArr[i5];
                if (obj3 instanceof AbsoluteSizeSpan) {
                    AbsoluteSizeSpan absoluteSizeSpan = (AbsoluteSizeSpan) obj3;
                    float size = absoluteSizeSpan.getSize();
                    if (!absoluteSizeSpan.getDip()) {
                        size /= context.getResources().getDisplayMetrics().density;
                    }
                    sb.append(String.format("<span style=\"font-size:%.0fpx\";>", Float.valueOf(size)));
                }
                Object obj4 = objArr[i5];
                if (obj4 instanceof AlignmentSpan) {
                    Layout.Alignment alignment = ((AlignmentSpan) obj4).getAlignment();
                    if (alignment == Layout.Alignment.ALIGN_CENTER) {
                        sb.append("<center>");
                    } else if (alignment == Layout.Alignment.ALIGN_OPPOSITE) {
                        sb.append("<right>");
                    } else {
                        sb.append("<left>");
                    }
                }
                if (objArr[i5] instanceof ForegroundColorSpan) {
                    sb.append("<font color =\"#");
                    String hexString2 = Integer.toHexString(((ForegroundColorSpan) objArr[i5]).getForegroundColor() + 16777216);
                    while (hexString2.length() < 6) {
                        hexString2 = "0" + hexString2;
                    }
                    sb.append(hexString2);
                    sb.append("\">");
                }
            }
            withinStyle(sb, spanned, i4, iNextSpanTransition);
            for (int length = objArr.length - 1; length >= 0; length--) {
                if (objArr[length] instanceof ForegroundColorSpan) {
                    sb.append("</font>");
                }
                if (objArr[length] instanceof BackgroundColorSpan) {
                    sb.append("</background>");
                }
                if (objArr[length] instanceof FontLargeSizeSpan) {
                    sb.append("<size>");
                }
                if (objArr[length] instanceof FontMidSizeSpan) {
                    sb.append("<mid-size>");
                }
                Object obj5 = objArr[length];
                if (obj5 instanceof AbsoluteSizeSpan) {
                    AbsoluteSizeSpan absoluteSizeSpan2 = (AbsoluteSizeSpan) obj5;
                    float size2 = absoluteSizeSpan2.getSize();
                    if (!absoluteSizeSpan2.getDip()) {
                        size2 /= context.getResources().getDisplayMetrics().density;
                    }
                    sb.append(String.format("<span style=\"font-size:%.0fpx\";>", Float.valueOf(size2)));
                }
                if (objArr[length] instanceof URLSpan) {
                    sb.append("</a>");
                }
                if (objArr[length] instanceof StrikethroughSpan) {
                    sb.append("</strike>");
                }
                if (objArr[length] instanceof UnderlineSpan) {
                    sb.append("</u>");
                }
                if (objArr[length] instanceof SubscriptSpan) {
                    sb.append("</sub>");
                }
                if (objArr[length] instanceof SuperscriptSpan) {
                    sb.append("</sup>");
                }
                Object obj6 = objArr[length];
                if ((obj6 instanceof TypefaceSpan) && ((TypefaceSpan) obj6).getFamily().equals("monospace")) {
                    sb.append("</tt>");
                }
                Object obj7 = objArr[length];
                if (obj7 instanceof StyleSpan) {
                    int style2 = ((StyleSpan) obj7).getStyle();
                    if ((style2 & 1) != 0) {
                        sb.append("</b>");
                    }
                    if ((style2 & 2) != 0) {
                        sb.append("</i>");
                    }
                }
                Object obj8 = objArr[length];
                if (obj8 instanceof AlignmentSpan) {
                    Layout.Alignment alignment2 = ((AlignmentSpan) obj8).getAlignment();
                    if (alignment2 == Layout.Alignment.ALIGN_CENTER) {
                        sb.append("</center>");
                    } else if (alignment2 == Layout.Alignment.ALIGN_OPPOSITE) {
                        sb.append("</right>");
                    } else {
                        sb.append("</left>");
                    }
                }
            }
            i4 = iNextSpanTransition;
        }
        String str = z ? "" : "</p>\n" + getOpenParaTagWithDirection(spanned, i, i2);
        if (i3 == 1) {
            sb.append("<br>\n");
            return;
        }
        if (i3 == 2) {
            sb.append(str);
            return;
        }
        for (int i6 = 2; i6 < i3; i6++) {
            sb.append("<br>");
        }
        sb.append(str);
    }

    private static void withinStyle(StringBuilder sb, CharSequence charSequence, int i, int i2) {
        while (i < i2) {
            char cCharAt = charSequence.charAt(i);
            if (cCharAt == '<') {
                sb.append("&lt;");
            } else if (cCharAt == '>') {
                sb.append("&gt;");
            } else if (cCharAt == '&') {
                sb.append("&amp;");
            } else if (cCharAt > '~' || cCharAt < ' ') {
                sb.append("&#" + ((int) cCharAt) + ";");
            } else if (cCharAt == ' ') {
                while (true) {
                    int i3 = i + 1;
                    if (i3 >= i2 || charSequence.charAt(i3) != ' ') {
                        break;
                    }
                    sb.append("&nbsp;");
                    i = i3;
                }
                sb.append(' ');
            } else {
                sb.append(cCharAt);
            }
            i++;
        }
    }
}
