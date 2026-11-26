package com.xiaomi.miai.json;

import ch.qos.logback.core.CoreConstants;
import java.util.Objects;

/* JADX INFO: loaded from: classes4.dex */
public class JsNumber extends JsValue {
    private final Number value;

    @Override // com.xiaomi.miai.json.JsValue
    public boolean isNumber() {
        return true;
    }

    public JsNumber(Number number) {
        this.value = number;
    }

    public Number numberValue() {
        return this.value;
    }

    public int intValue() {
        return this.value.intValue();
    }

    public long longValue() {
        return this.value.longValue();
    }

    public double doubleValue() {
        return this.value.doubleValue();
    }

    public String toString() {
        return "JsNumber{" + this.value + CoreConstants.CURLY_RIGHT;
    }

    public boolean equals(Object obj) {
        if (this != obj) {
            return (obj instanceof JsNumber) && ((JsNumber) obj).value.equals(this.value);
        }
        return true;
    }

    public int hashCode() {
        return Objects.hash(this.value);
    }
}
