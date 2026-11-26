package org.jsoup.internal;

import java.util.Locale;
import org.jsoup.nodes.Attribute;
import org.jsoup.nodes.Document;

/* JADX INFO: loaded from: classes6.dex */
public final class Normalizer {
    public static String lowerCase(String str) {
        return str != null ? str.toLowerCase(Locale.ROOT) : "";
    }

    public static String normalize(String str) {
        return lowerCase(str).trim();
    }

    public static String normalize(String str, boolean z) {
        return z ? lowerCase(str) : normalize(str);
    }

    public static String xmlSafeTagName(String str) {
        return Attribute.getValidKey(str, Document.OutputSettings.Syntax.xml);
    }
}
