package org.jsoup.internal;

import com.miui.richeditor.schema.HtmlParser;
import miuix.smartaction.SmartAction;

/* JADX INFO: loaded from: classes6.dex */
public final class SharedConstants {
    public static final String AttrRangeKey = "jsoup.attrs";
    public static final int DefaultBufferSize = 8192;
    public static final String DummyUri = "https://dummy.example/";
    public static final String EndRangeKey = "jsoup.end";
    public static final String[] FormSubmitTags = {HtmlParser.InputElement.TAG, "keygen", "object", SmartAction.Feature.SELECT, "textarea"};
    public static final String RangeKey = "jsoup.start";
    public static final String UseHttpClient = "jsoup.useHttpClient";
    public static final String UserDataKey = "/jsoup.userdata";
    public static final String XmlnsAttr = "jsoup.xmlns-";

    private SharedConstants() {
    }
}
