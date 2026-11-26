package com.miui.duokantext.parser;

import com.miui.common.stat.EditStat;
import java.io.IOException;
import java.util.ArrayList;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

/* JADX INFO: loaded from: classes2.dex */
public class FontsParserV22 extends BaseParser {
    private static final String LOG_TAG = "FontsParserV22";

    @Override // com.miui.duokantext.parser.BaseParser
    protected void readFamilies(XmlPullParser xmlPullParser) throws XmlPullParserException, IOException {
        xmlPullParser.require(2, null, "familyset");
        while (xmlPullParser.next() != 3) {
            if (xmlPullParser.getEventType() == 2) {
                if (xmlPullParser.getName().equals("family")) {
                    readFamily(xmlPullParser);
                }
                if (xmlPullParser.getName().equals("alias")) {
                    readAlias(xmlPullParser);
                }
                if (!xmlPullParser.getName().equals("alias") && !xmlPullParser.getName().equals("family")) {
                    skip(xmlPullParser);
                }
            }
        }
    }

    @Override // com.miui.duokantext.parser.BaseParser
    protected void readFamily(XmlPullParser xmlPullParser) throws XmlPullParserException, IOException {
        String attributeValue = xmlPullParser.getAttributeValue(null, "name");
        if (attributeValue == null) {
            return;
        }
        appendFontsToFontFamilies(new long[]{createFontFamily(attributeValue, 400, false)}, readFonts(xmlPullParser));
    }

    private void readAlias(XmlPullParser xmlPullParser) throws XmlPullParserException, IOException {
        String attributeValue = xmlPullParser.getAttributeValue(null, "name");
        String attributeValue2 = xmlPullParser.getAttributeValue(null, "to");
        String attributeValue3 = xmlPullParser.getAttributeValue(null, "weight");
        createAlias(attributeValue, attributeValue2, attributeValue3 != null ? Integer.parseInt(attributeValue3) : 400);
        skip(xmlPullParser);
    }

    private long[] readFonts(XmlPullParser xmlPullParser) throws XmlPullParserException, IOException {
        ArrayList arrayList = new ArrayList();
        while (xmlPullParser.next() != 3) {
            if (xmlPullParser.getEventType() == 2) {
                if (xmlPullParser.getName().equals("font")) {
                    String attributeValue = xmlPullParser.getAttributeValue(null, "weight");
                    arrayList.add(Long.valueOf(createFont("/system/fonts/" + xmlPullParser.nextText(), attributeValue == null ? 400 : Integer.parseInt(attributeValue), EditStat.ELEMENT_ITALIC.equals(xmlPullParser.getAttributeValue(null, "style")))));
                } else {
                    skip(xmlPullParser);
                }
            }
        }
        long[] jArr = new long[arrayList.size()];
        for (int i = 0; i < arrayList.size(); i++) {
            jArr[i] = ((Long) arrayList.get(i)).longValue();
        }
        return jArr;
    }
}
