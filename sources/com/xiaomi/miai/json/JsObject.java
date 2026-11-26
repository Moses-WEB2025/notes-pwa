package com.xiaomi.miai.json;

import java.util.Set;
import net.lingala.zip4j.util.InternalZipConstants;

/* JADX INFO: loaded from: classes4.dex */
public abstract class JsObject extends JsValue {
    @Override // com.xiaomi.miai.json.JsValue
    public abstract JsValue get(String str);

    public abstract boolean has(String str);

    @Override // com.xiaomi.miai.json.JsValue
    public boolean isObject() {
        return true;
    }

    public abstract String jsPath();

    public abstract Set<String> keys();

    public abstract int size();

    public String getString(String str) {
        return ((JsString) get(str).as(JsString.class, childPath(str))).stringValue();
    }

    public Boolean getBoolean(String str) {
        return Boolean.valueOf(((JsBoolean) get(str).as(JsBoolean.class, childPath(str))).booleanValue());
    }

    public int getInt(String str) {
        return ((JsNumber) get(str).as(JsNumber.class, childPath(str))).intValue();
    }

    public long getLong(String str) {
        return ((JsNumber) get(str).as(JsNumber.class, childPath(str))).longValue();
    }

    public double getDouble(String str) {
        return ((JsNumber) get(str).as(JsNumber.class, childPath(str))).doubleValue();
    }

    public JsObject getObject(String str) {
        return (JsObject) get(str).as(JsObject.class, childPath(str));
    }

    public JsArray getArray(String str) {
        return (JsArray) get(str).as(JsArray.class, childPath(str));
    }

    protected String childPath(String str) {
        return jsPath() + InternalZipConstants.ZIP_FILE_SEPARATOR + str;
    }
}
