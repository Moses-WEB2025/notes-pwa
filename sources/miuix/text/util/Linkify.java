package miuix.text.util;

import android.telephony.PhoneNumberUtils;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.method.LinkMovementMethod;
import android.text.style.URLSpan;
import android.widget.TextView;
import androidx.core.text.util.LinkifyCompat;
import com.google.i18n.phonenumbers.PhoneNumberMatch;
import com.google.i18n.phonenumbers.PhoneNumberUtil;
import com.miui.richeditor.IRichEditor;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import miuix.util.Patterns;

/* JADX INFO: loaded from: classes6.dex */
public class Linkify {
    public static final int ALL = 16 | 15;
    private static final Comparator<LinkSpec> COMPARATOR = new Comparator<LinkSpec>() { // from class: miuix.text.util.Linkify.1
        @Override // java.util.Comparator
        public int compare(LinkSpec linkSpec, LinkSpec linkSpec2) {
            if (linkSpec.start < linkSpec2.start) {
                return -1;
            }
            if (linkSpec.start <= linkSpec2.start && linkSpec.end >= linkSpec2.end) {
                return linkSpec.end > linkSpec2.end ? -1 : 0;
            }
            return 1;
        }
    };
    public static final int EMAIL_ADDRESSES = 2;
    public static final int MAP_ADDRESSES = 8;
    private static int PATTERNS_FROM_FRAMEWORK = 12;
    public static final int PHONE_NUMBERS = 4;
    public static int TIME_PHRASES = 16;
    public static final int WEB_URLS = 1;

    private Linkify() {
    }

    private static class Super extends android.text.util.Linkify {
        private Super() {
        }
    }

    public static void setPatternsFromFramework(int i) {
        PATTERNS_FROM_FRAMEWORK = i;
    }

    public static boolean addLinks(Spannable spannable, int i) {
        Spannable spannable2;
        if (i == 0) {
            return false;
        }
        URLSpan[] uRLSpanArr = (URLSpan[]) spannable.getSpans(0, spannable.length(), URLSpan.class);
        for (int length = uRLSpanArr.length - 1; length >= 0; length--) {
            spannable.removeSpan(uRLSpanArr[length]);
        }
        LinkifyCompat.addLinks(spannable, PATTERNS_FROM_FRAMEWORK & i);
        int i2 = (~PATTERNS_FROM_FRAMEWORK) & i & ALL;
        ArrayList<LinkSpec> arrayList = new ArrayList();
        if ((i2 & 1) != 0) {
            spannable2 = spannable;
            gatherLinks(arrayList, spannable2, Patterns.WEB_URL, new String[]{"http://", "https://", "rtsp://", "ftp://"}, Super.sUrlMatchFilter, null);
        } else {
            spannable2 = spannable;
        }
        if ((i2 & 2) != 0) {
            gatherLinks(arrayList, spannable2, Patterns.EMAIL_ADDRESS, new String[]{"mailto:"}, null, null);
        }
        if ((TIME_PHRASES & i2) != 0) {
            gatherLinks(arrayList, spannable2, Patterns.CHINESE_TIME_PATTERN, new String[]{"time:"}, null, null);
            gatherLinks(arrayList, spannable2, Patterns.ENGLISH_TIME_PATTERN, new String[]{"time:"}, null, null);
        }
        if ((i & 4) != 0) {
            gatherTelLinks(arrayList, spannable2);
        }
        pruneOverlaps(arrayList, spannable2);
        if (arrayList.isEmpty()) {
            return false;
        }
        for (LinkSpec linkSpec : arrayList) {
            if (linkSpec.url != null) {
                applyLink(linkSpec.url, linkSpec.start, linkSpec.end, spannable2);
            }
        }
        return true;
    }

    public static boolean addLinks(TextView textView, int i) {
        if (i == 0) {
            return false;
        }
        CharSequence text = textView.getText();
        if (text instanceof Spannable) {
            if (!addLinks((Spannable) text, i)) {
                return false;
            }
            addLinkMovementMethod(textView);
            return true;
        }
        SpannableString spannableStringValueOf = SpannableString.valueOf(text);
        if (!addLinks(spannableStringValueOf, i)) {
            return false;
        }
        addLinkMovementMethod(textView);
        textView.setText(spannableStringValueOf);
        return true;
    }

    private static void applyLink(String str, int i, int i2, Spannable spannable) {
        spannable.setSpan(new URLSpan(str), i, i2, 33);
    }

    private static void gatherLinks(ArrayList<LinkSpec> arrayList, Spannable spannable, Pattern pattern, String[] strArr, android.text.util.Linkify.MatchFilter matchFilter, android.text.util.Linkify.TransformFilter transformFilter) {
        Matcher matcher = pattern.matcher(spannable);
        while (matcher.find()) {
            int iStart = matcher.start();
            int iEnd = matcher.end();
            if (matchFilter == null || matchFilter.acceptMatch(spannable, iStart, iEnd)) {
                LinkSpec linkSpec = new LinkSpec();
                String strMakeUrl = makeUrl(matcher.group(0), strArr, matcher, transformFilter);
                if (strMakeUrl != null && strMakeUrl.length() >= 2 && strMakeUrl.charAt(strMakeUrl.length() - 1) == ',') {
                    strMakeUrl = strMakeUrl.substring(0, strMakeUrl.length() - 1);
                    iEnd--;
                }
                linkSpec.url = strMakeUrl;
                linkSpec.start = iStart;
                linkSpec.end = iEnd;
                arrayList.add(linkSpec);
            }
        }
    }

    private static void gatherTelLinks(ArrayList<LinkSpec> arrayList, Spannable spannable) {
        for (PhoneNumberMatch phoneNumberMatch : PhoneNumberUtil.getInstance().findNumbers(spannable, Locale.getDefault().getCountry(), PhoneNumberUtil.Leniency.POSSIBLE, Long.MAX_VALUE)) {
            LinkSpec linkSpec = new LinkSpec();
            linkSpec.url = IRichEditor.SCHEME_TEL + PhoneNumberUtils.normalizeNumber(phoneNumberMatch.rawString());
            linkSpec.start = phoneNumberMatch.start();
            linkSpec.end = phoneNumberMatch.end();
            arrayList.add(linkSpec);
        }
    }

    /* JADX WARN: Code duplicated, block: B:19:0x0053  */
    /* JADX WARN: Code duplicated, block: B:21:0x0067 A[RETURN] */
    private static String makeUrl(String str, String[] strArr, Matcher matcher, android.text.util.Linkify.TransformFilter transformFilter) {
        boolean z;
        if (transformFilter != null) {
            str = transformFilter.transformUrl(matcher, str);
        }
        String str2 = str;
        if (str2 == null) {
            return null;
        }
        for (int i = 0; i < strArr.length; i++) {
            String str3 = strArr[i];
            if (str2.regionMatches(true, 0, str3, 0, str3.length())) {
                String str4 = strArr[i];
                z = true;
                if (!str2.regionMatches(false, 0, str4, 0, str4.length())) {
                    str2 = strArr[i] + str2.substring(strArr[i].length());
                }
                if (z) {
                    return str2;
                }
                return strArr[0] + str2;
            }
        }
        z = false;
        if (z) {
            return strArr[0] + str2;
        }
        return str2;
    }

    private static void pruneOverlaps(ArrayList<LinkSpec> arrayList, Spannable spannable) {
        int i;
        int i2 = 0;
        URLSpan[] uRLSpanArr = (URLSpan[]) spannable.getSpans(0, spannable.length(), URLSpan.class);
        for (int i3 = 0; i3 < uRLSpanArr.length; i3++) {
            LinkSpec linkSpec = new LinkSpec();
            linkSpec.span = uRLSpanArr[i3];
            linkSpec.start = spannable.getSpanStart(uRLSpanArr[i3]);
            linkSpec.end = spannable.getSpanEnd(uRLSpanArr[i3]);
            arrayList.add(linkSpec);
        }
        Collections.sort(arrayList, COMPARATOR);
        int size = arrayList.size();
        while (i2 < size - 1) {
            LinkSpec linkSpec2 = arrayList.get(i2);
            int i4 = i2 + 1;
            LinkSpec linkSpec3 = arrayList.get(i4);
            if (linkSpec2.start <= linkSpec3.start && linkSpec2.end > linkSpec3.start) {
                if (linkSpec3.end > linkSpec2.end && linkSpec2.end - linkSpec2.start <= linkSpec3.end - linkSpec3.start) {
                    i = linkSpec2.end - linkSpec2.start < linkSpec3.end - linkSpec3.start ? i2 : -1;
                } else {
                    i = i4;
                }
                if (i != -1) {
                    Object obj = arrayList.get(i).span;
                    if (obj != null) {
                        spannable.removeSpan(obj);
                    }
                    arrayList.remove(i);
                    size--;
                }
            }
            i2 = i4;
        }
    }

    private static void addLinkMovementMethod(TextView textView) {
        if ((textView.getMovementMethod() instanceof LinkMovementMethod) || !textView.getLinksClickable()) {
            return;
        }
        textView.setMovementMethod(LinkMovementMethod.getInstance());
    }

    private static class LinkSpec {
        int end;
        URLSpan span;
        int start;
        String url;

        private LinkSpec() {
        }
    }
}
