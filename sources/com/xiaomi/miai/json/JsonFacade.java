package com.xiaomi.miai.json;

/* JADX INFO: loaded from: classes4.dex */
public abstract class JsonFacade {
    public static final JsBoolean JsFalse;
    public static final JsNull JsNull;
    public static final JsBoolean JsTrue;
    private static JsonFacade instance;

    protected abstract <T> T parseToObject(Class<T> cls, String str);

    protected abstract JsValue parseTree(String str);

    protected abstract String toString(Object obj, boolean z);

    static {
        try {
            instance = (JsonFacade) Thread.currentThread().getContextClassLoader().loadClass("com.xiaomi.miai.json.JsonFacadeImpl").newInstance();
            JsNull = new JsNull();
            JsTrue = new JsBoolean(true);
            JsFalse = new JsBoolean(false);
        } catch (Throwable th) {
            throw new IllegalStateException("Create com.xiaomi.miai.json.JsonFacadeImpl failed.", th);
        }
    }

    public static JsValue parse(String str) {
        return instance.parseTree(str);
    }

    public static <T> T parse(Class<T> cls, String str) {
        return (T) instance.parseToObject(cls, str);
    }

    public static String stringify(Object obj, boolean z) {
        return instance.toString(obj, z);
    }

    public static String stringify(Object obj) {
        return instance.toString(obj, false);
    }
}
