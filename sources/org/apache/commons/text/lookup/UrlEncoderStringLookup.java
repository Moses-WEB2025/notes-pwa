package org.apache.commons.text.lookup;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

/* JADX INFO: loaded from: classes6.dex */
final class UrlEncoderStringLookup extends AbstractStringLookup {
    static final UrlEncoderStringLookup INSTANCE = new UrlEncoderStringLookup();

    private UrlEncoderStringLookup() {
    }

    String encode(String str, String str2) throws UnsupportedEncodingException {
        return URLEncoder.encode(str, str2);
    }

    @Override // org.apache.commons.text.lookup.StringLookup
    public String lookup(String str) {
        if (str == null) {
            return null;
        }
        String strName = StandardCharsets.UTF_8.name();
        try {
            return encode(str, strName);
        } catch (UnsupportedEncodingException e) {
            throw IllegalArgumentExceptions.format(e, "%s: source=%s, encoding=%s", e, str, strName);
        }
    }
}
