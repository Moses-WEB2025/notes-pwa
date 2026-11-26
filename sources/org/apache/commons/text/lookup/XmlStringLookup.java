package org.apache.commons.text.lookup;

import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.OpenOption;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import javax.xml.xpath.XPathFactory;
import org.apache.commons.lang3.StringUtils;
import org.xml.sax.InputSource;

/* JADX INFO: loaded from: classes6.dex */
final class XmlStringLookup extends AbstractPathFencedLookup {
    static final Map<String, Boolean> DEFAULT_FEATURES;
    static final XmlStringLookup INSTANCE;
    private final Map<String, Boolean> xPathFactoryFeatures;

    static {
        HashMap map = new HashMap(1);
        DEFAULT_FEATURES = map;
        map.put("http://javax.xml.XMLConstants/feature/secure-processing", Boolean.TRUE);
        INSTANCE = new XmlStringLookup(map, null);
    }

    XmlStringLookup(Map<String, Boolean> map, Path... pathArr) {
        super(pathArr);
        this.xPathFactoryFeatures = (Map) Objects.requireNonNull(map, "xPathFfactoryFeatures");
    }

    @Override // org.apache.commons.text.lookup.StringLookup
    public String lookup(String str) {
        if (str == null) {
            return null;
        }
        String[] strArrSplit = str.split(SPLIT_STR);
        if (strArrSplit.length != 2) {
            throw IllegalArgumentExceptions.format("Bad XML key format [%s]; expected format is DocumentPath:XPath.", str);
        }
        String str2 = strArrSplit[0];
        String strSubstringAfter = StringUtils.substringAfter(str, 58);
        try {
            InputStream inputStreamNewInputStream = Files.newInputStream(getPath(str2), new OpenOption[0]);
            try {
                XPathFactory xPathFactoryNewInstance = XPathFactory.newInstance();
                for (Map.Entry<String, Boolean> entry : this.xPathFactoryFeatures.entrySet()) {
                    xPathFactoryNewInstance.setFeature(entry.getKey(), entry.getValue().booleanValue());
                }
                String strEvaluate = xPathFactoryNewInstance.newXPath().evaluate(strSubstringAfter, new InputSource(inputStreamNewInputStream));
                if (inputStreamNewInputStream != null) {
                    inputStreamNewInputStream.close();
                }
                return strEvaluate;
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
            throw IllegalArgumentExceptions.format(e, "Error looking up XML document [%s] and XPath [%s].", str2, strSubstringAfter);
        }
    }
}
