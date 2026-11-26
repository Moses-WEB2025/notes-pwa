package com.google.common.xml;

import ch.qos.logback.core.CoreConstants;
import com.google.common.escape.Escaper;
import com.google.common.escape.Escapers;
import kotlin.text.Typography;
import okio.Utf8;
import org.apache.commons.lang3.CharUtils;

/* JADX INFO: loaded from: classes2.dex */
@ElementTypesAreNonnullByDefault
public class XmlEscapers {
    private static final char MAX_ASCII_CONTROL_CHAR = 31;
    private static final char MIN_ASCII_CONTROL_CHAR = 0;
    private static final Escaper XML_ATTRIBUTE_ESCAPER;
    private static final Escaper XML_CONTENT_ESCAPER;
    private static final Escaper XML_ESCAPER;

    private XmlEscapers() {
    }

    public static Escaper xmlContentEscaper() {
        return XML_CONTENT_ESCAPER;
    }

    public static Escaper xmlAttributeEscaper() {
        return XML_ATTRIBUTE_ESCAPER;
    }

    static {
        Escapers.Builder builder = Escapers.builder();
        builder.setSafeRange((char) 0, Utf8.REPLACEMENT_CHARACTER);
        builder.setUnsafeReplacement("�");
        for (char c = 0; c <= 31; c = (char) (c + 1)) {
            if (c != '\t' && c != '\n' && c != '\r') {
                builder.addEscape(c, "�");
            }
        }
        builder.addEscape(Typography.amp, "&amp;");
        builder.addEscape(Typography.less, "&lt;");
        builder.addEscape(Typography.greater, "&gt;");
        XML_CONTENT_ESCAPER = builder.build();
        builder.addEscape(CoreConstants.SINGLE_QUOTE_CHAR, "&apos;");
        builder.addEscape('\"', "&quot;");
        XML_ESCAPER = builder.build();
        builder.addEscape('\t', "&#x9;");
        builder.addEscape('\n', "&#xA;");
        builder.addEscape(CharUtils.CR, "&#xD;");
        XML_ATTRIBUTE_ESCAPER = builder.build();
    }
}
