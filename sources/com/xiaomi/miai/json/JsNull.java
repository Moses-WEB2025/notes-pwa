package com.xiaomi.miai.json;

/* JADX INFO: loaded from: classes4.dex */
public final class JsNull extends JsValue {
    @Override // com.xiaomi.miai.json.JsValue
    public boolean isNull() {
        return true;
    }

    public boolean equals(Object obj) {
        return obj instanceof JsNull;
    }

    public String toString() {
        return "JsNull";
    }
}
