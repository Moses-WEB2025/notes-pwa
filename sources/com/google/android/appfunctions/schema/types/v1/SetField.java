package com.google.android.appfunctions.schema.types.v1;

import androidx.exifinterface.media.ExifInterface;
import com.market.sdk.reflect.ReflectUtilsForMiui;
import com.xiaomi.mirror.RemoteDeviceInfo;
import java.util.Objects;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: com.google.android.appfunctions:appfunctions-schema@@1.0.7 */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000\"\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0000\n\u0002\b\u0007\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0000\b\u0007\u0018\u0000*\u0006\b\u0000\u0010\u0001 \u00012\u00020\u0002B\u0011\u0012\b\b\u0001\u0010\u0003\u001a\u00028\u0000¢\u0006\u0004\b\u0004\u0010\u0005J\u0013\u0010\t\u001a\u00020\n2\b\u0010\u000b\u001a\u0004\u0018\u00010\u0002H\u0096\u0002J\b\u0010\f\u001a\u00020\rH\u0016J\b\u0010\u000e\u001a\u00020\u000fH\u0016R\u0018\u0010\u0003\u001a\u00028\u00008GX\u0087\u0004¢\u0006\n\n\u0002\u0010\b\u001a\u0004\b\u0006\u0010\u0007¨\u0006\u0010"}, d2 = {"Lcom/google/android/appfunctions/schema/types/v1/SetField;", ExifInterface.GPS_DIRECTION_TRUE, "", "value", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "(Ljava/lang/Object;)V", "getValue", "()Ljava/lang/Object;", "Ljava/lang/Object;", "equals", "", RemoteDeviceInfo.MANUFACTURER_OTHER, "hashCode", "", "toString", "", "java.com.google.android.libraries.llm.appfunctions.appfunctions_schema2.src.com.google.android.appfunctions.schema.types.v1_v1"}, k = 1, mv = {2, 1, 0}, xi = 48)
public final class SetField<T> {
    private final T value;

    public SetField(T t) {
        this.value = t;
    }

    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (other instanceof SetField) {
            return Intrinsics.areEqual(this.value, ((SetField) other).value);
        }
        return false;
    }

    public final T getValue() {
        return this.value;
    }

    public int hashCode() {
        return Objects.hash(this.value);
    }

    public String toString() {
        T t = this.value;
        StringBuilder sb = new StringBuilder(String.valueOf(t).length() + 16);
        sb.append("SetField(value=");
        sb.append(t);
        sb.append(")");
        return sb.toString();
    }
}
