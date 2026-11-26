package com.xiaomi.miai.json;

import ch.qos.logback.core.CoreConstants;
import java.util.Objects;

/* JADX INFO: loaded from: classes4.dex */
public class JsString extends JsValue {
    private final String value;

    @Override // com.xiaomi.miai.json.JsValue
    public boolean isString() {
        return true;
    }

    JsString(String str) {
        this.value = str;
    }

    public String stringValue() {
        return this.value;
    }

    public String toString() {
        return "JsString{" + this.value + CoreConstants.CURLY_RIGHT;
    }

    public boolean equals(Object obj) {
        if (this != obj) {
            return (obj instanceof JsString) && ((JsString) obj).value.equals(this.value);
        }
        return true;
    }

    public int hashCode() {
        return Objects.hash(this.value);
    }
}
