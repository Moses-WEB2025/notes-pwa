package org.apache.commons.text.lookup;

import org.apache.commons.text.StringEscapeUtils;

/* JADX INFO: loaded from: classes6.dex */
final class XmlEncoderStringLookup extends AbstractStringLookup {
    static final XmlEncoderStringLookup INSTANCE = new XmlEncoderStringLookup();

    XmlEncoderStringLookup() {
    }

    @Override // org.apache.commons.text.lookup.StringLookup
    public String lookup(String str) {
        return StringEscapeUtils.escapeXml10(str);
    }
}
