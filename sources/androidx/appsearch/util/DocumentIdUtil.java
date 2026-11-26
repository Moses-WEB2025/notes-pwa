package androidx.appsearch.util;

import androidx.appsearch.app.GenericDocument;
import androidx.core.util.Preconditions;

/* JADX INFO: loaded from: classes.dex */
public class DocumentIdUtil {
    private static final String BACKSLASH_REPLACEMENT = "\\\\";
    private static final String NAMESPACE_DELIMITER = "#";
    private static final String NAMESPACE_DELIMITER_REPLACEMENT = "\\#";

    private DocumentIdUtil() {
    }

    public static String createQualifiedId(String str, String str2, GenericDocument genericDocument) {
        return createQualifiedId(str, str2, genericDocument.getNamespace(), genericDocument.getId());
    }

    public static String createQualifiedId(String str, String str2, String str3, String str4) {
        Preconditions.checkNotNull(str);
        Preconditions.checkNotNull(str2);
        Preconditions.checkNotNull(str3);
        Preconditions.checkNotNull(str4);
        StringBuilder sb = new StringBuilder(escapeNsDelimiters(str));
        sb.append('$').append(escapeNsDelimiters(str2)).append('/').append(escapeNsDelimiters(str3)).append("#").append(escapeNsDelimiters(str4));
        return sb.toString();
    }

    private static String escapeNsDelimiters(String str) {
        StringBuilder sb = null;
        for (int i = 0; i < str.length(); i++) {
            char cCharAt = str.charAt(i);
            if (cCharAt == '\\') {
                if (sb == null) {
                    sb = new StringBuilder(str.substring(0, i));
                }
                sb.append(BACKSLASH_REPLACEMENT);
            } else if (cCharAt == '#') {
                if (sb == null) {
                    sb = new StringBuilder(str.substring(0, i));
                }
                sb.append(NAMESPACE_DELIMITER_REPLACEMENT);
            } else if (sb != null) {
                sb.append(cCharAt);
            }
        }
        return sb == null ? str : sb.toString();
    }
}
