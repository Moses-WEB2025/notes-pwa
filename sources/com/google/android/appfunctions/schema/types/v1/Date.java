package com.google.android.appfunctions.schema.types.v1;

import com.market.sdk.reflect.ReflectUtilsForMiui;
import com.miui.maml.data.VariableNames;
import com.xiaomi.mirror.RemoteDeviceInfo;
import java.util.Objects;
import kotlin.Metadata;

/* JADX INFO: compiled from: com.google.android.appfunctions:appfunctions-schema@@1.0.7 */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000\u001a\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0002\b\t\n\u0002\u0010\u000b\n\u0002\b\u0003\b\u0007\u0018\u00002\u00020\u0001B\u001f\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0003\u0012\u0006\u0010\u0005\u001a\u00020\u0003¢\u0006\u0004\b\u0006\u0010\u0007J\u0013\u0010\f\u001a\u00020\r2\b\u0010\u000e\u001a\u0004\u0018\u00010\u0001H\u0096\u0002J\b\u0010\u000f\u001a\u00020\u0003H\u0016R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\b\u0010\tR\u0011\u0010\u0004\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\n\u0010\tR\u0011\u0010\u0005\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\t¨\u0006\u0010"}, d2 = {"Lcom/google/android/appfunctions/schema/types/v1/Date;", "", VariableNames.VAR_YEAR, "", VariableNames.VAR_MONTH, "day", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "(III)V", "getYear", "()I", "getMonth", "getDay", "equals", "", RemoteDeviceInfo.MANUFACTURER_OTHER, "hashCode", "java.com.google.android.libraries.llm.appfunctions.appfunctions_schema2.src.com.google.android.appfunctions.schema.types.v1_v1"}, k = 1, mv = {2, 1, 0}, xi = 48)
public final class Date {
    private final int day;
    private final int month;
    private final int year;

    public Date(int i, int i2, int i3) {
        this.year = i;
        this.month = i2;
        this.day = i3;
    }

    public boolean equals(Object other) {
        if (!(other instanceof Date)) {
            return false;
        }
        Date date = (Date) other;
        return this.year == date.year && this.month == date.month && this.day == date.day;
    }

    public final int getDay() {
        return this.day;
    }

    public final int getMonth() {
        return this.month;
    }

    public final int getYear() {
        return this.year;
    }

    public int hashCode() {
        return Objects.hash(Integer.valueOf(this.year), Integer.valueOf(this.month), Integer.valueOf(this.day));
    }
}
