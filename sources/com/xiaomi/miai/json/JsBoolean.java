package com.xiaomi.miai.json;

import ch.qos.logback.core.CoreConstants;
import java.util.Objects;

/* JADX INFO: loaded from: classes4.dex */
public final class JsBoolean extends JsValue {
    private final boolean value;

    @Override // com.xiaomi.miai.json.JsValue
    public boolean isBoolean() {
        return true;
    }

    JsBoolean(boolean z) {
        this.value = z;
    }

    public boolean booleanValue() {
        return this.value;
    }

    public String toString() {
        return "JsBoolean{" + this.value + CoreConstants.CURLY_RIGHT;
    }

    public boolean equals(Object obj) {
        if (this != obj) {
            return (obj instanceof JsBoolean) && this.value == ((JsBoolean) obj).value;
        }
        return true;
    }

    public int hashCode() {
        return Objects.hash(Boolean.valueOf(this.value));
    }
}
