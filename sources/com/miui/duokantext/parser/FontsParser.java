package com.miui.duokantext.parser;

import com.miui.notes.base.utils.Logger;
import java.io.IOException;
import java.util.ArrayList;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

/* JADX INFO: loaded from: classes2.dex */
public class FontsParser extends BaseParser {
    private static final String LOG_TAG = "SystemFontsParser";

    @Override // com.miui.duokantext.parser.BaseParser
    protected void readFamilies(XmlPullParser xmlPullParser) throws XmlPullParserException, IOException {
        xmlPullParser.require(2, null, "familyset");
        while (xmlPullParser.next() != 3) {
            if (xmlPullParser.getEventType() == 2) {
                if (xmlPullParser.getName().equals("family")) {
                    readFamily(xmlPullParser);
                } else {
                    skip(xmlPullParser);
                }
            }
        }
    }

    @Override // com.miui.duokantext.parser.BaseParser
    protected void readFamily(XmlPullParser xmlPullParser) throws XmlPullParserException, IOException {
        while (true) {
            long[] nameSet = null;
            while (xmlPullParser.next() != 3) {
                if (xmlPullParser.getEventType() == 2) {
                    String name = xmlPullParser.getName();
                    if (name.equals("nameset")) {
                        nameSet = readNameSet(xmlPullParser);
                    }
                    if (name.equals("fileset") && nameSet != null) {
                        appendFontsToFontFamilies(nameSet, readFileSet(xmlPullParser));
                    }
                }
            }
            return;
        }
    }

    private long[] readNameSet(XmlPullParser xmlPullParser) throws XmlPullParserException, IOException {
        ArrayList arrayList = new ArrayList();
        while (xmlPullParser.next() != 3) {
            if (xmlPullParser.getEventType() == 2) {
                if (xmlPullParser.getName().equals("name")) {
                    String strNextText = xmlPullParser.nextText();
                    if (strNextText == null || "".equals(strNextText)) {
                        Logger.INSTANCE.e(LOG_TAG, "null or empty family name read from nameset");
                    }
                    arrayList.add(Long.valueOf(createFontFamily(strNextText, getWeightFromName(strNextText), getItalicFromName(strNextText))));
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

    private long[] readFileSet(XmlPullParser xmlPullParser) throws XmlPullParserException, IOException {
        long[] jArr = new long[50];
        int i = 0;
        while (xmlPullParser.next() != 3) {
            if (xmlPullParser.getEventType() == 2) {
                if (xmlPullParser.getName().equals("file")) {
                    String strNextText = xmlPullParser.nextText();
                    if (strNextText == null || "".equals(strNextText)) {
                        Logger.INSTANCE.e(LOG_TAG, "null or empty file path readed from fileset");
                    }
                    jArr[i] = createFont(strNextText, getWeightFromName(strNextText), getItalicFromName(strNextText));
                    i++;
                } else {
                    skip(xmlPullParser);
                }
            }
        }
        long[] jArr2 = new long[i];
        for (int i2 = 0; i2 < i; i2++) {
            jArr2[i2] = jArr[i2];
        }
        return jArr2;
    }
}
