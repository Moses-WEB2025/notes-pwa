package com.xiaomi.micloudsdk.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/* JADX INFO: loaded from: classes4.dex */
public class RegexUtils {
    private static final char DOT = '.';
    private static final char HIDDEN_STATE_CHAR = '*';
    private static final String IP_ADDRESS_PATTERN_STRING = "(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)";
    private static final Pattern IP_ADDRESS_PATTERN = Pattern.compile(IP_ADDRESS_PATTERN_STRING);

    public static String hideIP(String str) {
        Matcher matcher = IP_ADDRESS_PATTERN.matcher(str);
        StringBuilder sb = new StringBuilder(str.length());
        int iEnd = 0;
        while (matcher.find()) {
            sb.append(str.subSequence(iEnd, matcher.start()));
            sb.append(convertSensitiveInfo(str, matcher.start(), matcher.end()));
            iEnd = matcher.end();
        }
        if (iEnd < str.length()) {
            sb.append(str.subSequence(iEnd, str.length()));
        }
        return sb.toString();
    }

    private static String convertSensitiveInfo(String str, int i, int i2) {
        StringBuilder sb = new StringBuilder(i2 - i);
        int i3 = i + 2;
        int i4 = i2 - 2;
        while (i < i2) {
            char cCharAt = str.charAt(i);
            if (cCharAt == '.') {
                sb.append('.');
            } else if (i < i3) {
                sb.append(cCharAt);
            } else if (i > i4) {
                sb.append(cCharAt);
            } else {
                sb.append(HIDDEN_STATE_CHAR);
            }
            i++;
        }
        return sb.toString();
    }
}
