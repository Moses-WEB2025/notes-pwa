package com.xiaomi.miai.json;

import com.xiaomi.miai.utils.Requires;

/* JADX INFO: loaded from: classes4.dex */
public abstract class JsValue {
    public boolean isArray() {
        return false;
    }

    public boolean isBoolean() {
        return false;
    }

    public boolean isNull() {
        return false;
    }

    public boolean isNumber() {
        return false;
    }

    public boolean isObject() {
        return false;
    }

    public boolean isString() {
        return false;
    }

    public JsNull asNull() {
        Requires.isTrue(isNull(), "It's not a JsNull   ");
        return (JsNull) this;
    }

    public JsBoolean asBoolean() {
        Requires.isTrue(isBoolean(), "It's not a JsBoolean");
        return (JsBoolean) this;
    }

    public JsString asString() {
        Requires.isTrue(isString(), "It's not a JsString ");
        return (JsString) this;
    }

    public JsNumber asNumber() {
        Requires.isTrue(isNumber(), "It's not a JsNumber ");
        return (JsNumber) this;
    }

    public JsArray asArray() {
        Requires.isTrue(isArray(), "It's not a JsArray  ");
        return (JsArray) this;
    }

    public JsObject asObject() {
        Requires.isTrue(isObject(), "It's not a JsObject ");
        return (JsObject) this;
    }

    /* JADX WARN: Multi-variable type inference failed */
    protected <T extends JsValue> T as(Class<T> cls, String str) {
        Requires.isTrue(cls.isAssignableFrom(getClass()), str + " is not a " + cls.getSimpleName());
        return this;
    }

    public JsValue get(String str) {
        Requires.isTrue(isObject(), "It's not a JsObject ");
        return null;
    }

    public JsValue get(int i) {
        Requires.isTrue(isArray(), "It's not a JsArray ");
        return null;
    }
}
