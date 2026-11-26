package com.xiaomi.miai.json;

import java.util.Iterator;

/* JADX INFO: loaded from: classes4.dex */
public abstract class JsArray extends JsValue {
    @Override // com.xiaomi.miai.json.JsValue
    public abstract JsValue get(int i);

    @Override // com.xiaomi.miai.json.JsValue
    public boolean isArray() {
        return true;
    }

    public abstract Iterator<JsValue> iterator();

    public abstract String jsPath();

    public abstract int size();

    public String getString(int i) {
        return ((JsString) get(i).as(JsString.class, childPath(i))).stringValue();
    }

    public Boolean getBoolean(int i) {
        return Boolean.valueOf(((JsBoolean) get(i).as(JsBoolean.class, childPath(i))).booleanValue());
    }

    public int getInt(int i) {
        return ((JsNumber) get(i).as(JsNumber.class, childPath(i))).intValue();
    }

    public long getLong(int i) {
        return ((JsNumber) get(i).as(JsNumber.class, childPath(i))).longValue();
    }

    public double getDouble(int i) {
        return ((JsNumber) get(i).as(JsNumber.class, childPath(i))).doubleValue();
    }

    public JsObject getObject(int i) {
        return (JsObject) get(i).as(JsObject.class, childPath(i));
    }

    public JsArray getArray(int i) {
        return (JsArray) get(i).as(JsArray.class, childPath(i));
    }

    protected String childPath(int i) {
        return jsPath() + "[" + i + "]";
    }
}
