package com.google.android.appfunctions.schema.browser.v1;

import com.google.android.appfunctions.schema.types.v1.DateTime;
import com.market.sdk.reflect.ReflectUtilsForMiui;
import com.xiaomi.mirror.RemoteDeviceInfo;
import java.util.Objects;
import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import miuix.smartaction.SmartAction;

/* JADX INFO: compiled from: com.google.android.appfunctions:appfunctions-schema@@1.0.7 */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000(\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\b\n\u0002\b\n\n\u0002\u0010\u000b\n\u0002\b\u0003\b\u0007\u0018\u00002\u00020\u0001B3\u0012\n\b\u0002\u0010\u0002\u001a\u0004\u0018\u00010\u0003\u0012\n\b\u0002\u0010\u0004\u001a\u0004\u0018\u00010\u0003\u0012\n\b\u0002\u0010\u0005\u001a\u0004\u0018\u00010\u0006\u0012\u0006\u0010\u0007\u001a\u00020\b¢\u0006\u0004\b\t\u0010\nJ\u0013\u0010\u0012\u001a\u00020\u00132\b\u0010\u0014\u001a\u0004\u0018\u00010\u0001H\u0096\u0002J\b\u0010\u0015\u001a\u00020\bH\u0016R\u0013\u0010\u0002\u001a\u0004\u0018\u00010\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\fR\u0013\u0010\u0004\u001a\u0004\u0018\u00010\u0003¢\u0006\b\n\u0000\u001a\u0004\b\r\u0010\fR\u0013\u0010\u0005\u001a\u0004\u0018\u00010\u0006¢\u0006\b\n\u0000\u001a\u0004\b\u000e\u0010\u000fR\u0011\u0010\u0007\u001a\u00020\b¢\u0006\b\n\u0000\u001a\u0004\b\u0010\u0010\u0011¨\u0006\u0016"}, d2 = {"Lcom/google/android/appfunctions/schema/browser/v1/GetHistoriesParams;", "", "startTime", "Lcom/google/android/appfunctions/schema/types/v1/DateTime;", "endTime", SmartAction.Feature.QUERY, "", "maxCount", "", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "(Lcom/google/android/appfunctions/schema/types/v1/DateTime;Lcom/google/android/appfunctions/schema/types/v1/DateTime;Ljava/lang/String;I)V", "getStartTime", "()Lcom/google/android/appfunctions/schema/types/v1/DateTime;", "getEndTime", "getQuery", "()Ljava/lang/String;", "getMaxCount", "()I", "equals", "", RemoteDeviceInfo.MANUFACTURER_OTHER, "hashCode", "java.com.google.android.libraries.llm.appfunctions.appfunctions_schema2.src.com.google.android.appfunctions.schema.browser.v1_v1"}, k = 1, mv = {2, 1, 0}, xi = 48)
public final class GetHistoriesParams {
    private final DateTime endTime;
    private final int maxCount;
    private final String query;
    private final DateTime startTime;

    public GetHistoriesParams(DateTime dateTime, DateTime dateTime2, String str, int i) {
        this.startTime = dateTime;
        this.endTime = dateTime2;
        this.query = str;
        this.maxCount = i;
    }

    public /* synthetic */ GetHistoriesParams(DateTime dateTime, DateTime dateTime2, String str, int i, int i2, DefaultConstructorMarker defaultConstructorMarker) {
        this(1 == (i2 & 1) ? null : dateTime, (i2 & 2) != 0 ? null : dateTime2, (i2 & 4) != 0 ? null : str, i);
    }

    public boolean equals(Object other) {
        if (!(other instanceof GetHistoriesParams)) {
            return false;
        }
        GetHistoriesParams getHistoriesParams = (GetHistoriesParams) other;
        return Intrinsics.areEqual(this.startTime, getHistoriesParams.startTime) && Intrinsics.areEqual(this.endTime, getHistoriesParams.endTime) && Intrinsics.areEqual(this.query, getHistoriesParams.query) && this.maxCount == getHistoriesParams.maxCount;
    }

    public final DateTime getEndTime() {
        return this.endTime;
    }

    public final int getMaxCount() {
        return this.maxCount;
    }

    public final String getQuery() {
        return this.query;
    }

    public final DateTime getStartTime() {
        return this.startTime;
    }

    public int hashCode() {
        return Objects.hash(this.startTime, this.endTime, this.query, Integer.valueOf(this.maxCount));
    }
}
