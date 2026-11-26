package org.apache.commons.text.lookup;

import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.OpenOption;
import java.nio.file.Path;
import java.util.Properties;
import org.apache.commons.lang3.StringUtils;

/* JADX INFO: loaded from: classes6.dex */
final class PropertiesStringLookup extends AbstractPathFencedLookup {
    static final PropertiesStringLookup INSTANCE = new PropertiesStringLookup(null);
    static final String SEPARATOR = "::";

    static {
    }

    static String toPropertyKey(String str, String str2) {
        return toLookupKey(str, SEPARATOR, str2);
    }

    PropertiesStringLookup(Path... pathArr) {
        super(pathArr);
    }

    @Override // org.apache.commons.text.lookup.StringLookup
    public String lookup(String str) {
        if (str == null) {
            return null;
        }
        String[] strArrSplit = str.split(SEPARATOR);
        if (strArrSplit.length < 2) {
            throw IllegalArgumentExceptions.format("Bad properties key format [%s]; expected format is %s.", str, toPropertyKey("DocumentPath", "Key"));
        }
        String str2 = strArrSplit[0];
        String strSubstringAfter = StringUtils.substringAfter(str, SEPARATOR);
        try {
            Properties properties = new Properties();
            InputStream inputStreamNewInputStream = Files.newInputStream(getPath(str2), new OpenOption[0]);
            try {
                properties.load(inputStreamNewInputStream);
                if (inputStreamNewInputStream != null) {
                    inputStreamNewInputStream.close();
                }
                return properties.getProperty(strSubstringAfter);
            } catch (Throwable th) {
                if (inputStreamNewInputStream != null) {
                    try {
                        inputStreamNewInputStream.close();
                    } catch (Throwable th2) {
                        th.addSuppressed(th2);
                    }
                }
                throw th;
            }
        } catch (Exception e) {
            throw IllegalArgumentExceptions.format(e, "Error looking up properties [%s] and key [%s].", str2, strSubstringAfter);
        }
    }
}
