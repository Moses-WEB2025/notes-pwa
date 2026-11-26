package com.miui.richeditor.schema;

import android.content.Context;
import android.text.Layout;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.style.AlignmentSpan;
import android.text.style.BackgroundColorSpan;
import android.text.style.CharacterStyle;
import android.text.style.ForegroundColorSpan;
import android.text.style.StrikethroughSpan;
import android.text.style.StyleSpan;
import android.text.style.SubscriptSpan;
import android.text.style.SuperscriptSpan;
import android.text.style.TypefaceSpan;
import android.text.style.URLSpan;
import android.text.style.UnderlineSpan;
import com.miui.notes.base.utils.Logger;
import com.miui.richeditor.style.BgHighLightSpan;
import com.miui.richeditor.style.EditLineHeightSpan;
import com.miui.richeditor.style.FontLargeSizeSpan;
import com.miui.richeditor.style.FontMidSizeSpan;
import com.miui.richeditor.style.lite.EditLineHeightLiteSpan;
import com.miui.richeditor.style.lite.LinkCardLiteSpan;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import org.apache.commons.lang3.StringUtils;

/* JADX INFO: loaded from: classes4.dex */
public class HtmlBuilder {
    private static final String TAG = "HtmlBuilder";
    private Context mContext;
    private StringBuilder mOut = new StringBuilder();
    private ArrayList<SpanWrapper> mSpans;
    private Spanned mText;
    private static Comparator<SpanWrapper> SPAN_START_COMPARATOR = new Comparator<SpanWrapper>() { // from class: com.miui.richeditor.schema.HtmlBuilder.1
        @Override // java.util.Comparator
        public int compare(SpanWrapper spanWrapper, SpanWrapper spanWrapper2) {
            return spanWrapper.start - spanWrapper2.start;
        }
    };
    private static Comparator<SpanWrapper> SPAN_END_COMPARATOR = new Comparator<SpanWrapper>() { // from class: com.miui.richeditor.schema.HtmlBuilder.2
        @Override // java.util.Comparator
        public int compare(SpanWrapper spanWrapper, SpanWrapper spanWrapper2) {
            int iCompareTo = spanWrapper2.end - spanWrapper.end;
            if (iCompareTo == 0) {
                if (HtmlParser.IElementSpan.class.isAssignableFrom(spanWrapper.style.getClass())) {
                    if (!HtmlParser.IElementSpan.class.isAssignableFrom(spanWrapper2.style.getClass())) {
                        iCompareTo = 1;
                    }
                } else if (HtmlParser.IElementSpan.class.isAssignableFrom(spanWrapper2.style.getClass())) {
                    iCompareTo = -1;
                }
            }
            if (iCompareTo == 0) {
                iCompareTo = spanWrapper.style.getClass().getSimpleName().compareTo(spanWrapper2.style.getClass().getSimpleName());
            }
            if (iCompareTo != 0) {
                return iCompareTo;
            }
            SpannableStringBuilder spannableStringBuilder = new SpannableStringBuilder(StringUtils.SPACE);
            spannableStringBuilder.setSpan(spanWrapper.style, 0, spannableStringBuilder.length(), 33);
            String strBuild = new HtmlBuilder(spanWrapper.context, spannableStringBuilder).build();
            spannableStringBuilder.removeSpan(spanWrapper.style);
            spannableStringBuilder.setSpan(spanWrapper2.style, 0, spannableStringBuilder.length(), 33);
            return strBuild.compareTo(new HtmlBuilder(spanWrapper.context, spannableStringBuilder).build());
        }
    };

    public interface IIgnorable {
    }

    public static String escapeHtml(Context context, String str) {
        return TextUtils.isEmpty(str) ? str : new HtmlBuilder(context, new SpannableString(str)).build();
    }

    public static String escapeHtml(Context context, SpannableStringBuilder spannableStringBuilder) {
        if (TextUtils.isEmpty(spannableStringBuilder)) {
            return "";
        }
        return new HtmlBuilder(context, spannableStringBuilder).build();
    }

    public HtmlBuilder(Context context, Spanned spanned) {
        this.mContext = context;
        this.mText = spanned;
        cacheSpans(this.mText);
    }

    public String build() {
        withinBlock(0, this.mText.length());
        return this.mOut.toString();
    }

    private void withinBlock(int i, int i2) {
        while (i < i2) {
            int iNextSpanStart = nextSpanStart(i, i2);
            if (i < iNextSpanStart) {
                withText(i, iNextSpanStart);
            }
            i = iNextSpanStart < i2 ? withStyles(iNextSpanStart) : iNextSpanStart;
        }
    }

    private int withStyles(int i) {
        ArrayList<SpanWrapper> spansStartAt = getSpansStartAt(i);
        Collections.sort(spansStartAt, SPAN_END_COMPARATOR);
        withStyle(spansStartAt, 0);
        return spansStartAt.get(0).end;
    }

    private void withStyle(ArrayList<SpanWrapper> arrayList, int i) {
        int iNextSpanStart;
        SpanWrapper spanWrapper = arrayList.get(i);
        Object obj = spanWrapper.style;
        withStyleStart(obj);
        if (!HtmlParser.IElementSpan.class.isAssignableFrom(obj.getClass())) {
            int i2 = spanWrapper.start;
            if (i < arrayList.size() - 1) {
                int i3 = i + 1;
                iNextSpanStart = arrayList.get(i3).end;
                withStyle(arrayList, i3);
            } else {
                iNextSpanStart = nextSpanStart(i2 + 1, spanWrapper.end);
                withText(spanWrapper.start, iNextSpanStart);
            }
            withinBlock(iNextSpanStart, spanWrapper.end);
        }
        withStyleEnd(obj);
    }

    private void withStyleStart(Object obj) {
        if (obj instanceof StyleSpan) {
            int style = ((StyleSpan) obj).getStyle();
            if ((style & 1) != 0) {
                this.mOut.append("<b>");
            }
            if ((style & 2) != 0) {
                this.mOut.append("<i>");
                return;
            }
            return;
        }
        if (obj instanceof TypefaceSpan) {
            if (((TypefaceSpan) obj).getFamily().equals("monospace")) {
                this.mOut.append("<tt>");
                return;
            }
            return;
        }
        if (obj instanceof SuperscriptSpan) {
            this.mOut.append("<sup>");
            return;
        }
        if (obj instanceof SubscriptSpan) {
            this.mOut.append("<sub>");
            return;
        }
        if (obj instanceof UnderlineSpan) {
            this.mOut.append("<u>");
            return;
        }
        if (obj instanceof StrikethroughSpan) {
            this.mOut.append("<del>");
            return;
        }
        if (obj instanceof URLSpan) {
            this.mOut.append("<a href=\"");
            this.mOut.append(((URLSpan) obj).getURL());
            this.mOut.append("\">");
            return;
        }
        if (obj instanceof LinkCardLiteSpan) {
            this.mOut.append("<a href=\"");
            LinkCardLiteSpan linkCardLiteSpan = (LinkCardLiteSpan) obj;
            this.mOut.append(linkCardLiteSpan.getUrl());
            this.mOut.append("\">");
            this.mOut.append(linkCardLiteSpan.getTitle());
            return;
        }
        if (obj instanceof FontLargeSizeSpan) {
            this.mOut.append("<size>");
            return;
        }
        if (obj instanceof FontMidSizeSpan) {
            this.mOut.append("<mid-size>");
            return;
        }
        if (obj instanceof ForegroundColorSpan) {
            this.mOut.append("<font color =\"#");
            String hexString = Integer.toHexString(((ForegroundColorSpan) obj).getForegroundColor() + 16777216);
            while (hexString.length() < 6) {
                hexString = "0" + hexString;
            }
            this.mOut.append(hexString);
            this.mOut.append("\">");
            return;
        }
        if (HtmlParser.IElementSpan.class.isAssignableFrom(obj.getClass())) {
            try {
                ((HtmlParser.IElementSpan) obj).getElement().toHtml(this.mOut);
                return;
            } catch (Exception e) {
                Logger.INSTANCE.e(TAG, "Fail to append html" + e);
                return;
            }
        }
        if (obj instanceof AlignmentSpan) {
            Layout.Alignment alignment = ((AlignmentSpan) obj).getAlignment();
            if (alignment == Layout.Alignment.ALIGN_CENTER) {
                this.mOut.append("<center>");
                return;
            } else {
                if (alignment == Layout.Alignment.ALIGN_OPPOSITE) {
                    this.mOut.append("<right>");
                    return;
                }
                return;
            }
        }
        if (obj.getClass() == BgHighLightSpan.class) {
            this.mOut.append("<background color =\"#");
            String hexString2 = Integer.toHexString(((BackgroundColorSpan) obj).getBackgroundColor() + 16777216);
            while (hexString2.length() < 6) {
                hexString2 = "0" + hexString2;
            }
            this.mOut.append(hexString2);
            this.mOut.append("\">");
        }
    }

    private void withStyleEnd(Object obj) {
        if (obj instanceof ForegroundColorSpan) {
            this.mOut.append("</font>");
            return;
        }
        if (obj instanceof FontLargeSizeSpan) {
            this.mOut.append("</size>");
            return;
        }
        if (obj instanceof FontMidSizeSpan) {
            this.mOut.append("</mid-size>");
            return;
        }
        if (obj instanceof URLSpan) {
            this.mOut.append("</a>");
            return;
        }
        if (obj instanceof LinkCardLiteSpan) {
            this.mOut.append("</a>");
            return;
        }
        if (obj instanceof StrikethroughSpan) {
            this.mOut.append("</del>");
            return;
        }
        if (obj instanceof UnderlineSpan) {
            this.mOut.append("</u>");
            return;
        }
        if (obj instanceof SubscriptSpan) {
            this.mOut.append("</sub>");
            return;
        }
        if (obj instanceof SuperscriptSpan) {
            this.mOut.append("</sup>");
            return;
        }
        if (obj instanceof TypefaceSpan) {
            if (((TypefaceSpan) obj).getFamily().equals("monospace")) {
                this.mOut.append("</tt>");
                return;
            }
            return;
        }
        if (obj instanceof StyleSpan) {
            int style = ((StyleSpan) obj).getStyle();
            if ((style & 2) != 0) {
                this.mOut.append("</i>");
            }
            if ((style & 1) != 0) {
                this.mOut.append("</b>");
                return;
            }
            return;
        }
        if (obj instanceof AlignmentSpan) {
            Layout.Alignment alignment = ((AlignmentSpan) obj).getAlignment();
            if (alignment == Layout.Alignment.ALIGN_CENTER) {
                this.mOut.append("</center>");
                return;
            } else {
                if (alignment == Layout.Alignment.ALIGN_OPPOSITE) {
                    this.mOut.append("</right>");
                    return;
                }
                return;
            }
        }
        if (obj instanceof BgHighLightSpan) {
            this.mOut.append("</background>");
        }
    }

    private void withText(int i, int i2) {
        while (i < i2) {
            char cCharAt = this.mText.charAt(i);
            if (cCharAt == '<') {
                this.mOut.append("&lt;");
            } else if (cCharAt == '>') {
                this.mOut.append("&gt;");
            } else if (cCharAt == '&') {
                this.mOut.append("&amp;");
            } else {
                this.mOut.append(cCharAt);
            }
            i++;
        }
    }

    private void cacheSpans(Spanned spanned) {
        int spanStart;
        int spanEnd;
        CharacterStyle[] characterStyleArr = (CharacterStyle[]) spanned.getSpans(0, spanned.length(), CharacterStyle.class);
        HtmlParser.IElementSpan[] iElementSpanArr = (HtmlParser.IElementSpan[]) spanned.getSpans(0, spanned.length(), HtmlParser.IElementSpan.class);
        AlignmentSpan[] alignmentSpanArr = (AlignmentSpan[]) spanned.getSpans(0, spanned.length(), AlignmentSpan.class);
        HashSet hashSet = new HashSet();
        Collections.addAll(hashSet, characterStyleArr);
        Collections.addAll(hashSet, iElementSpanArr);
        Collections.addAll(hashSet, alignmentSpanArr);
        this.mSpans = new ArrayList<>(hashSet.size());
        for (Object obj : hashSet) {
            if (obj != null && !IIgnorable.class.isAssignableFrom(obj.getClass()) && !obj.getClass().getName().equals(URLSpan.class.getName()) && !obj.getClass().getName().equals(TypefaceSpan.class.getName()) && !obj.getClass().getName().equals(EditLineHeightSpan.class.getName()) && !obj.getClass().getName().equals(EditLineHeightLiteSpan.class.getName()) && (spanStart = spanned.getSpanStart(obj)) != (spanEnd = spanned.getSpanEnd(obj))) {
                this.mSpans.add(new SpanWrapper(this.mContext, spanStart, spanEnd, obj));
            }
        }
        Collections.sort(this.mSpans, SPAN_START_COMPARATOR);
    }

    private int nextSpanStart(int i, int i2) {
        for (SpanWrapper spanWrapper : this.mSpans) {
            if (spanWrapper.start >= i2) {
                break;
            }
            if (spanWrapper.start >= i) {
                return spanWrapper.start;
            }
        }
        return i2;
    }

    private ArrayList<SpanWrapper> getSpansStartAt(int i) {
        ArrayList<SpanWrapper> arrayList = new ArrayList<>();
        for (SpanWrapper spanWrapper : this.mSpans) {
            if (spanWrapper.start > i) {
                break;
            }
            if (spanWrapper.start == i) {
                arrayList.add(spanWrapper);
            }
        }
        return arrayList;
    }

    private static class SpanWrapper {
        Context context;
        int end;
        int start;
        Object style;

        public SpanWrapper(Context context, int i, int i2, Object obj) {
            this.context = context;
            this.start = i;
            this.end = i2;
            this.style = obj;
        }

        public Context getContext() {
            return this.context;
        }
    }
}
