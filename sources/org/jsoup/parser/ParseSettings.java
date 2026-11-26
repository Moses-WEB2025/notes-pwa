package org.jsoup.parser;

import org.jsoup.internal.Normalizer;
import org.jsoup.nodes.Attributes;

/* JADX INFO: loaded from: classes6.dex */
public class ParseSettings {
    public static final ParseSettings htmlDefault = new ParseSettings(false, false);
    public static final ParseSettings preserveCase = new ParseSettings(true, true);
    private final boolean preserveAttributeCase;
    private final boolean preserveTagCase;

    public boolean preserveTagCase() {
        return this.preserveTagCase;
    }

    public boolean preserveAttributeCase() {
        return this.preserveAttributeCase;
    }

    public ParseSettings(boolean z, boolean z2) {
        this.preserveTagCase = z;
        this.preserveAttributeCase = z2;
    }

    ParseSettings(ParseSettings parseSettings) {
        this(parseSettings.preserveTagCase, parseSettings.preserveAttributeCase);
    }

    public String normalizeTag(String str) {
        String strTrim = str.trim();
        return !this.preserveTagCase ? Normalizer.lowerCase(strTrim) : strTrim;
    }

    public String normalizeAttribute(String str) {
        String strTrim = str.trim();
        return !this.preserveAttributeCase ? Normalizer.lowerCase(strTrim) : strTrim;
    }

    Attributes normalizeAttributes(Attributes attributes) {
        if (attributes != null && !this.preserveAttributeCase) {
            attributes.normalize();
        }
        return attributes;
    }

    static String normalName(String str) {
        return Normalizer.normalize(str);
    }
}
