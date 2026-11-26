package org.apache.commons.text.lookup;

import java.io.BufferedInputStream;
import java.io.InputStreamReader;
import java.io.StringWriter;
import java.net.URL;
import org.apache.commons.lang3.StringUtils;

/* JADX INFO: loaded from: classes6.dex */
final class UrlStringLookup extends AbstractStringLookup {
    static final UrlStringLookup INSTANCE = new UrlStringLookup();

    private UrlStringLookup() {
    }

    @Override // org.apache.commons.text.lookup.StringLookup
    public String lookup(String str) {
        if (str == null) {
            return null;
        }
        String[] strArrSplit = str.split(SPLIT_STR);
        if (strArrSplit.length < 2) {
            throw IllegalArgumentExceptions.format("Bad URL key format [%s]; expected format is DocumentPath:Key.", str);
        }
        String str2 = strArrSplit[0];
        String strSubstringAfter = StringUtils.substringAfter(str, 58);
        try {
            URL url = new URL(strSubstringAfter);
            StringWriter stringWriter = new StringWriter(8192);
            char[] cArr = new char[8192];
            BufferedInputStream bufferedInputStream = new BufferedInputStream(url.openStream());
            try {
                InputStreamReader inputStreamReader = new InputStreamReader(bufferedInputStream, str2);
                while (true) {
                    try {
                        int i = inputStreamReader.read(cArr);
                        if (-1 != i) {
                            stringWriter.write(cArr, 0, i);
                        } else {
                            inputStreamReader.close();
                            bufferedInputStream.close();
                            return stringWriter.toString();
                        }
                    } catch (Throwable th) {
                        try {
                            inputStreamReader.close();
                        } catch (Throwable th2) {
                            th.addSuppressed(th2);
                        }
                        throw th;
                    }
                    try {
                        bufferedInputStream.close();
                    } catch (Throwable th3) {
                        th.addSuppressed(th3);
                    }
                    throw th;
                }
            } catch (Throwable th4) {
                bufferedInputStream.close();
                throw th4;
            }
        } catch (Exception e) {
            throw IllegalArgumentExceptions.format(e, "Error looking up URL [%s] with Charset [%s].", strSubstringAfter, str2);
        }
    }
}
