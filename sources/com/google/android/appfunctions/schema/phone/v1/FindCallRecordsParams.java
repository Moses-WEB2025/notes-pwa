package com.google.android.appfunctions.schema.phone.v1;

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
@Metadata(d1 = {"\u0000*\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\b\n\u0002\b\u000e\n\u0002\u0010\u000b\n\u0002\b\u0003\b\u0007\u0018\u00002\u00020\u0001BK\u0012\n\b\u0002\u0010\u0002\u001a\u0004\u0018\u00010\u0003\u0012\n\b\u0002\u0010\u0004\u001a\u0004\u0018\u00010\u0003\u0012\n\b\u0002\u0010\u0005\u001a\u0004\u0018\u00010\u0006\u0012\n\b\u0002\u0010\u0007\u001a\u0004\u0018\u00010\u0006\u0012\n\b\u0002\u0010\b\u001a\u0004\u0018\u00010\u0003\u0012\u0006\u0010\t\u001a\u00020\n¢\u0006\u0004\b\u000b\u0010\fJ\u0013\u0010\u0018\u001a\u00020\u00192\b\u0010\u001a\u001a\u0004\u0018\u00010\u0001H\u0096\u0002J\b\u0010\u001b\u001a\u00020\nH\u0016R\u0013\u0010\u0002\u001a\u0004\u0018\u00010\u0003¢\u0006\b\n\u0000\u001a\u0004\b\r\u0010\u000eR\u0013\u0010\u0004\u001a\u0004\u0018\u00010\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u000f\u0010\u000eR\u0013\u0010\u0005\u001a\u0004\u0018\u00010\u0006¢\u0006\b\n\u0000\u001a\u0004\b\u0010\u0010\u0011R\u0013\u0010\u0007\u001a\u0004\u0018\u00010\u0006¢\u0006\b\n\u0000\u001a\u0004\b\u0012\u0010\u0011R\u001e\u0010\b\u001a\u0004\u0018\u00010\u00038\u0006X\u0087\u0004¢\u0006\u000e\n\u0000\u0012\u0004\b\u0013\u0010\u0014\u001a\u0004\b\u0015\u0010\u000eR\u0011\u0010\t\u001a\u00020\n¢\u0006\b\n\u0000\u001a\u0004\b\u0016\u0010\u0017¨\u0006\u001c"}, d2 = {"Lcom/google/android/appfunctions/schema/phone/v1/FindCallRecordsParams;", "", SmartAction.Feature.QUERY, "", "contactId", "startTime", "Lcom/google/android/appfunctions/schema/types/v1/DateTime;", "endTime", "callType", "maxCount", "", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "(Ljava/lang/String;Ljava/lang/String;Lcom/google/android/appfunctions/schema/types/v1/DateTime;Lcom/google/android/appfunctions/schema/types/v1/DateTime;Ljava/lang/String;I)V", "getQuery", "()Ljava/lang/String;", "getContactId", "getStartTime", "()Lcom/google/android/appfunctions/schema/types/v1/DateTime;", "getEndTime", "getCallType$annotations", "()V", "getCallType", "getMaxCount", "()I", "equals", "", RemoteDeviceInfo.MANUFACTURER_OTHER, "hashCode", "java.com.google.android.libraries.llm.appfunctions.appfunctions_schema2.src.com.google.android.appfunctions.schema.phone.v1_v1"}, k = 1, mv = {2, 1, 0}, xi = 48)
public final class FindCallRecordsParams {
    private final String callType;
    private final String contactId;
    private final DateTime endTime;
    private final int maxCount;
    private final String query;
    private final DateTime startTime;

    public FindCallRecordsParams(String str, String str2, DateTime dateTime, DateTime dateTime2, String str3, int i) {
        this.query = str;
        this.contactId = str2;
        this.startTime = dateTime;
        this.endTime = dateTime2;
        this.callType = str3;
        this.maxCount = i;
    }

    public /* synthetic */ FindCallRecordsParams(String str, String str2, DateTime dateTime, DateTime dateTime2, String str3, int i, int i2, DefaultConstructorMarker defaultConstructorMarker) {
        this(1 == (i2 & 1) ? null : str, (i2 & 2) != 0 ? null : str2, (i2 & 4) != 0 ? null : dateTime, (i2 & 8) != 0 ? null : dateTime2, (i2 & 16) != 0 ? null : str3, i);
    }

    public boolean equals(Object other) {
        if (!(other instanceof FindCallRecordsParams)) {
            return false;
        }
        FindCallRecordsParams findCallRecordsParams = (FindCallRecordsParams) other;
        return Intrinsics.areEqual(this.query, findCallRecordsParams.query) && Intrinsics.areEqual(this.contactId, findCallRecordsParams.contactId) && Intrinsics.areEqual(this.startTime, findCallRecordsParams.startTime) && Intrinsics.areEqual(this.endTime, findCallRecordsParams.endTime) && Intrinsics.areEqual(this.callType, findCallRecordsParams.callType) && this.maxCount == findCallRecordsParams.maxCount;
    }

    public final String getCallType() {
        return this.callType;
    }

    public final String getContactId() {
        return this.contactId;
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
        return Objects.hash(this.query, this.contactId, this.startTime, this.endTime, this.callType, Integer.valueOf(this.maxCount));
    }
}
