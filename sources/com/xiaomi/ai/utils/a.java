package com.xiaomi.ai.utils;

import ch.qos.logback.classic.net.SyslogAppender;
import org.apache.commons.lang3.StringUtils;

/* JADX INFO: loaded from: classes4.dex */
public class a {
    private static String a(int i) {
        StringBuffer stringBuffer = new StringBuffer();
        for (int i2 = 0; i2 < i; i2++) {
            stringBuffer.append(SyslogAppender.DEFAULT_STACKTRACE_PATTERN);
        }
        return stringBuffer.toString();
    }

    /* JADX WARN: Code duplicated, block: B:21:0x003f  */
    /* JADX WARN: Code duplicated, block: B:23:0x004f  */
    public static String a(String str) {
        StringBuffer stringBuffer = new StringBuffer();
        int i = 0;
        for (int i2 = 0; i2 < str.length(); i2++) {
            char cCharAt = str.charAt(i2);
            if (i > 0 && '\n' == stringBuffer.charAt(stringBuffer.length() - 1)) {
                stringBuffer.append(a(i));
            }
            if (cCharAt == ',') {
                stringBuffer.append(cCharAt + StringUtils.LF);
            } else if (cCharAt == '[') {
                stringBuffer.append(cCharAt + StringUtils.LF);
                i++;
            } else {
                if (cCharAt == ']') {
                    stringBuffer.append(StringUtils.LF);
                    i--;
                    stringBuffer.append(a(i));
                } else if (cCharAt == '{') {
                    stringBuffer.append(cCharAt + StringUtils.LF);
                    i++;
                } else if (cCharAt == '}') {
                    stringBuffer.append(StringUtils.LF);
                    i--;
                    stringBuffer.append(a(i));
                }
                stringBuffer.append(cCharAt);
            }
        }
        return stringBuffer.toString();
    }
}
