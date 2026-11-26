package com.miui.duokantext.parser;

import android.util.Xml;
import com.miui.notes.base.utils.Logger;
import java.io.IOException;
import java.io.InputStream;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

/* JADX INFO: loaded from: classes2.dex */
public class FontCacheMgr {
    private static final String LOG_TAG = "FontCacheMgr";
    private static final String PARSER_NAME = "com.miui.duokantext.parser.FontsParser";
    private BaseParser mFontsParser;

    public void parse(InputStream inputStream) throws XmlPullParserException, IOException {
        try {
            XmlPullParser xmlPullParserNewPullParser = Xml.newPullParser();
            xmlPullParserNewPullParser.setInput(inputStream, null);
            xmlPullParserNewPullParser.nextTag();
            fontsParserInit(xmlPullParserNewPullParser);
            this.mFontsParser.readFamilies(xmlPullParserNewPullParser);
        } finally {
            inputStream.close();
        }
    }

    public void dump() {
        this.mFontsParser.dump();
    }

    private void fontsParserInit(XmlPullParser xmlPullParser) throws XmlPullParserException, IOException {
        boolean zEquals = xmlPullParser.getName().equals("familyset");
        String str = PARSER_NAME;
        if (zEquals) {
            String attributeValue = xmlPullParser.getAttributeValue(null, "version");
            if (attributeValue != null) {
                str = "com.miui.duokantext.parser.FontsParserV" + attributeValue;
            }
        } else {
            Logger.INSTANCE.e(LOG_TAG, "fail to get familyset tagName at the start of parse");
        }
        try {
            this.mFontsParser = (BaseParser) Class.forName(str).newInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
