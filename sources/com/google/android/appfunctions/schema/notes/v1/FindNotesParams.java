package com.google.android.appfunctions.schema.notes.v1;

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
@Metadata(d1 = {"\u0000(\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\n\n\u0002\u0010\u000b\n\u0002\b\u0003\b\u0007\u0018\u00002\u00020\u0001B3\u0012\n\b\u0002\u0010\u0002\u001a\u0004\u0018\u00010\u0003\u0012\n\b\u0002\u0010\u0004\u001a\u0004\u0018\u00010\u0005\u0012\n\b\u0002\u0010\u0006\u001a\u0004\u0018\u00010\u0005\u0012\u0006\u0010\u0007\u001a\u00020\b¢\u0006\u0004\b\t\u0010\nJ\u0013\u0010\u0012\u001a\u00020\u00132\b\u0010\u0014\u001a\u0004\u0018\u00010\u0001H\u0096\u0002J\b\u0010\u0015\u001a\u00020\bH\u0016R\u0013\u0010\u0002\u001a\u0004\u0018\u00010\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\fR\u0013\u0010\u0004\u001a\u0004\u0018\u00010\u0005¢\u0006\b\n\u0000\u001a\u0004\b\r\u0010\u000eR\u0013\u0010\u0006\u001a\u0004\u0018\u00010\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u000f\u0010\u000eR\u0011\u0010\u0007\u001a\u00020\b¢\u0006\b\n\u0000\u001a\u0004\b\u0010\u0010\u0011¨\u0006\u0016"}, d2 = {"Lcom/google/android/appfunctions/schema/notes/v1/FindNotesParams;", "", SmartAction.Feature.QUERY, "", "startDate", "Lcom/google/android/appfunctions/schema/types/v1/DateTime;", "endDate", "maxCount", "", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "(Ljava/lang/String;Lcom/google/android/appfunctions/schema/types/v1/DateTime;Lcom/google/android/appfunctions/schema/types/v1/DateTime;I)V", "getQuery", "()Ljava/lang/String;", "getStartDate", "()Lcom/google/android/appfunctions/schema/types/v1/DateTime;", "getEndDate", "getMaxCount", "()I", "equals", "", RemoteDeviceInfo.MANUFACTURER_OTHER, "hashCode", "java.com.google.android.libraries.llm.appfunctions.appfunctions_schema2.src.com.google.android.appfunctions.schema.notes.v1_v1"}, k = 1, mv = {2, 1, 0}, xi = 48)
public final class FindNotesParams {
    private final DateTime endDate;
    private final int maxCount;
    private final String query;
    private final DateTime startDate;

    public FindNotesParams(String str, DateTime dateTime, DateTime dateTime2, int i) {
        this.query = str;
        this.startDate = dateTime;
        this.endDate = dateTime2;
        this.maxCount = i;
    }

    public /* synthetic */ FindNotesParams(String str, DateTime dateTime, DateTime dateTime2, int i, int i2, DefaultConstructorMarker defaultConstructorMarker) {
        this(1 == (i2 & 1) ? null : str, (i2 & 2) != 0 ? null : dateTime, (i2 & 4) != 0 ? null : dateTime2, i);
    }

    public boolean equals(Object other) {
        if (!(other instanceof FindNotesParams)) {
            return false;
        }
        FindNotesParams findNotesParams = (FindNotesParams) other;
        return Intrinsics.areEqual(this.query, findNotesParams.query) && Intrinsics.areEqual(this.startDate, findNotesParams.startDate) && Intrinsics.areEqual(this.endDate, findNotesParams.endDate) && this.maxCount == findNotesParams.maxCount;
    }

    public final DateTime getEndDate() {
        return this.endDate;
    }

    public final int getMaxCount() {
        return this.maxCount;
    }

    public final String getQuery() {
        return this.query;
    }

    public final DateTime getStartDate() {
        return this.startDate;
    }

    public int hashCode() {
        return Objects.hash(this.query, this.startDate, this.endDate, Integer.valueOf(this.maxCount));
    }
}
