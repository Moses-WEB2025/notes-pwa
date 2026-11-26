package org.jsoup.helper;

import ch.qos.logback.core.CoreConstants;
import java.io.UnsupportedEncodingException;
import java.net.IDN;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLDecoder;
import java.net.URLEncoder;
import kotlin.text.Typography;
import org.jsoup.Connection;
import org.jsoup.internal.StringUtil;

/* JADX INFO: loaded from: classes6.dex */
final class UrlBuilder {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    private static final String unsafeCharacters = "<>\"{}|\\^[]`";
    StringBuilder q;
    URL u;

    private static boolean isHex(char c) {
        if (c >= '0' && c <= '9') {
            return true;
        }
        if (c < 'A' || c > 'F') {
            return c >= 'a' && c <= 'f';
        }
        return true;
    }

    UrlBuilder(URL url) {
        this.u = url;
        if (url.getQuery() != null) {
            this.q = StringUtil.borrowBuilder().append(this.u.getQuery());
        }
    }

    URL build() {
        try {
            StringBuilder sbAppend = StringUtil.borrowBuilder().append(new URI(this.u.getProtocol(), this.u.getUserInfo(), IDN.toASCII(decodePart(this.u.getHost())), this.u.getPort(), null, null, null).toASCIIString());
            appendToAscii(this.u.getPath(), false, sbAppend);
            if (this.q != null) {
                sbAppend.append('?');
                appendToAscii(StringUtil.releaseBuilder(this.q), true, sbAppend);
            }
            if (this.u.getRef() != null) {
                sbAppend.append('#');
                appendToAscii(this.u.getRef(), false, sbAppend);
            }
            URL url = new URL(StringUtil.releaseBuilder(sbAppend));
            this.u = url;
            return url;
        } catch (UnsupportedEncodingException | MalformedURLException | URISyntaxException unused) {
            return this.u;
        }
    }

    void appendKeyVal(Connection.KeyVal keyVal) throws UnsupportedEncodingException {
        StringBuilder sb = this.q;
        if (sb == null) {
            this.q = StringUtil.borrowBuilder();
        } else {
            sb.append(Typography.amp);
        }
        this.q.append(URLEncoder.encode(keyVal.key(), DataUtil.UTF_8.name())).append('=').append(URLEncoder.encode(keyVal.value(), DataUtil.UTF_8.name()));
    }

    private static String decodePart(String str) {
        try {
            return URLDecoder.decode(str, DataUtil.UTF_8.name());
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
    }

    /* JADX WARN: Code duplicated, block: B:20:0x0058  */
    private static void appendToAscii(String str, boolean z, StringBuilder sb) throws UnsupportedEncodingException {
        int i = 0;
        while (i < str.length()) {
            int iCodePointAt = str.codePointAt(i);
            if (iCodePointAt == 32) {
                sb.append(z ? '+' : "%20");
            } else if (iCodePointAt == 37) {
                if (i < str.length() - 2) {
                    int i2 = i + 1;
                    if (isHex(str.charAt(i2))) {
                        int i3 = i + 2;
                        if (isHex(str.charAt(i3))) {
                            sb.append(CoreConstants.PERCENT_CHAR).append(str.charAt(i2)).append(str.charAt(i3));
                            i = i3;
                        } else {
                            sb.append("%25");
                        }
                    } else {
                        sb.append("%25");
                    }
                } else {
                    sb.append("%25");
                }
            } else if (iCodePointAt > 127 || unsafeCharacters.indexOf(iCodePointAt) != -1) {
                sb.append(URLEncoder.encode(new String(Character.toChars(iCodePointAt)), DataUtil.UTF_8.name()));
                if (Character.charCount(iCodePointAt) == 2) {
                    i++;
                }
            } else {
                sb.append((char) iCodePointAt);
            }
            i++;
        }
    }
}
