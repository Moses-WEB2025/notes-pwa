package com.miui.duokantext.parser;

import com.miui.common.stat.EditStat;
import com.miui.notes.base.utils.Logger;
import com.xiaomi.verificationsdk.internal.Constants;
import java.io.IOException;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

/* JADX INFO: loaded from: classes2.dex */
public abstract class BaseParser {
    private static final String LOG_TAG = "BaseParser";

    private static native void nativeAppendFontsToFontFamilies(long[] jArr, long[] jArr2);

    private static native void nativeCreateAlias(String str, String str2, int i);

    private static native long nativeCreateFont(String str, int i, boolean z);

    private static native long nativeCreateFontFamily(String str, int i, boolean z);

    private static native void nativeDebugDump();

    protected abstract void readFamilies(XmlPullParser xmlPullParser) throws XmlPullParserException, IOException;

    protected abstract void readFamily(XmlPullParser xmlPullParser) throws XmlPullParserException, IOException;

    protected int getWeightFromName(String str) {
        if (str == null) {
            Logger.INSTANCE.e(LOG_TAG, "getWeightFromFileName error: name is null");
            return 400;
        }
        return getBaseWeight(str, getWeight(str.toLowerCase(), 400));
    }

    private int getWeight(String str, int i) {
        if (!str.contains("regular")) {
            str.contains(EditStat.ELEMENT_ITALIC);
        }
        if (str.contains("medium")) {
            i += 100;
        }
        if (str.contains("black")) {
            i += 500;
        }
        if (str.contains(EditStat.ELEMENT_BOLD)) {
            i += 300;
        }
        if (str.contains("thin")) {
            i -= 300;
        }
        return str.contains(Constants.LIGHT) ? i - 100 : i;
    }

    private int getBaseWeight(String str, int i) {
        if (i > 900) {
            Logger.INSTANCE.e(LOG_TAG, "getWeightFromFileName error: fail to calculate weight from name[" + str + "]");
            i = 900;
        }
        if (i >= 100) {
            return i;
        }
        Logger.INSTANCE.e(LOG_TAG, "getWeightFromFileName error: fail to calculate weight from name[" + str + "]");
        return 100;
    }

    protected boolean getItalicFromName(String str) {
        if (str == null) {
            Logger.INSTANCE.e(LOG_TAG, "getItalicFromName error: name is null");
            return false;
        }
        return str.toLowerCase().contains(EditStat.ELEMENT_ITALIC);
    }

    public void skip(XmlPullParser xmlPullParser) throws XmlPullParserException, IOException {
        int i = 1;
        while (i > 0) {
            int next = xmlPullParser.next();
            if (next == 2) {
                i++;
            } else if (next == 3) {
                i--;
            }
        }
    }

    void dump() {
        nativeDebugDump();
    }

    long createFontFamily(String str, int i, boolean z) {
        return nativeCreateFontFamily(str, i, z);
    }

    long createFont(String str, int i, boolean z) {
        return nativeCreateFont(str, i, z);
    }

    void createAlias(String str, String str2, int i) {
        nativeCreateAlias(str, str2, i);
    }

    void appendFontsToFontFamilies(long[] jArr, long[] jArr2) {
        nativeAppendFontsToFontFamilies(jArr, jArr2);
    }
}
