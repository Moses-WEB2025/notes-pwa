package com.google.android.appfunctions.schema.calendar.v1;

import com.google.android.appfunctions.schema.types.v1.DateTime;
import com.market.sdk.reflect.ReflectUtilsForMiui;
import com.xiaomi.mirror.RemoteDeviceInfo;
import java.util.List;
import java.util.Objects;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import miuix.smartaction.SmartAction;

/* JADX INFO: compiled from: com.google.android.appfunctions:appfunctions-schema@@1.0.7 */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000.\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010 \n\u0000\n\u0002\u0010\b\n\u0002\b\u0010\n\u0002\u0010\u000b\n\u0002\b\u0003\b\u0007\u0018\u00002\u00020\u0001BO\u0012\n\b\u0002\u0010\u0002\u001a\u0004\u0018\u00010\u0003\u0012\n\b\u0002\u0010\u0004\u001a\u0004\u0018\u00010\u0003\u0012\n\b\u0002\u0010\u0005\u001a\u0004\u0018\u00010\u0006\u0012\u000e\b\u0002\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\u00060\b\u0012\u0006\u0010\t\u001a\u00020\n\u0012\n\b\u0002\u0010\u000b\u001a\u0004\u0018\u00010\u0006¢\u0006\u0004\b\f\u0010\rJ\u0013\u0010\u001a\u001a\u00020\u001b2\b\u0010\u001c\u001a\u0004\u0018\u00010\u0001H\u0096\u0002J\b\u0010\u001d\u001a\u00020\nH\u0016R\u0013\u0010\u0002\u001a\u0004\u0018\u00010\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u000e\u0010\u000fR\u0013\u0010\u0004\u001a\u0004\u0018\u00010\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0010\u0010\u000fR\u0013\u0010\u0005\u001a\u0004\u0018\u00010\u0006¢\u0006\b\n\u0000\u001a\u0004\b\u0011\u0010\u0012R\u0017\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\u00060\b¢\u0006\b\n\u0000\u001a\u0004\b\u0013\u0010\u0014R\u0011\u0010\t\u001a\u00020\n¢\u0006\b\n\u0000\u001a\u0004\b\u0015\u0010\u0016R\u001e\u0010\u000b\u001a\u0004\u0018\u00010\u00068\u0006X\u0087\u0004¢\u0006\u000e\n\u0000\u0012\u0004\b\u0017\u0010\u0018\u001a\u0004\b\u0019\u0010\u0012¨\u0006\u001e"}, d2 = {"Lcom/google/android/appfunctions/schema/calendar/v1/FindEventsParams;", "", "startDate", "Lcom/google/android/appfunctions/schema/types/v1/DateTime;", "endDate", SmartAction.Feature.QUERY, "", "attendeeIds", "", "maxCount", "", "orderBy", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "(Lcom/google/android/appfunctions/schema/types/v1/DateTime;Lcom/google/android/appfunctions/schema/types/v1/DateTime;Ljava/lang/String;Ljava/util/List;ILjava/lang/String;)V", "getStartDate", "()Lcom/google/android/appfunctions/schema/types/v1/DateTime;", "getEndDate", "getQuery", "()Ljava/lang/String;", "getAttendeeIds", "()Ljava/util/List;", "getMaxCount", "()I", "getOrderBy$annotations", "()V", "getOrderBy", "equals", "", RemoteDeviceInfo.MANUFACTURER_OTHER, "hashCode", "java.com.google.android.libraries.llm.appfunctions.appfunctions_schema2.src.com.google.android.appfunctions.schema.calendar.v1_v1"}, k = 1, mv = {2, 1, 0}, xi = 48)
public final class FindEventsParams {
    private final List<String> attendeeIds;
    private final DateTime endDate;
    private final int maxCount;
    private final String orderBy;
    private final String query;
    private final DateTime startDate;

    public FindEventsParams(DateTime dateTime, DateTime dateTime2, String str, List<String> attendeeIds, int i, String str2) {
        Intrinsics.checkNotNullParameter(attendeeIds, "attendeeIds");
        this.startDate = dateTime;
        this.endDate = dateTime2;
        this.query = str;
        this.attendeeIds = attendeeIds;
        this.maxCount = i;
        this.orderBy = str2;
    }

    /* JADX WARN: Illegal instructions before constructor call */
    public /* synthetic */ FindEventsParams(DateTime dateTime, DateTime dateTime2, String str, List list, int i, String str2, int i2, DefaultConstructorMarker defaultConstructorMarker) {
        List listEmptyList = (i2 & 8) != 0 ? CollectionsKt.emptyList() : list;
        int i3 = i2 & 2;
        int i4 = i2 & 1;
        String str3 = (i2 & 4) != 0 ? null : str;
        this(1 == i4 ? null : dateTime, i3 != 0 ? null : dateTime2, str3, listEmptyList, i, (i2 & 32) != 0 ? null : str2);
    }

    public boolean equals(Object other) {
        if (!(other instanceof FindEventsParams)) {
            return false;
        }
        FindEventsParams findEventsParams = (FindEventsParams) other;
        return Intrinsics.areEqual(this.startDate, findEventsParams.startDate) && Intrinsics.areEqual(this.endDate, findEventsParams.endDate) && Intrinsics.areEqual(this.query, findEventsParams.query) && Intrinsics.areEqual(this.attendeeIds, findEventsParams.attendeeIds) && this.maxCount == findEventsParams.maxCount && Intrinsics.areEqual(this.orderBy, findEventsParams.orderBy);
    }

    public final List<String> getAttendeeIds() {
        return this.attendeeIds;
    }

    public final DateTime getEndDate() {
        return this.endDate;
    }

    public final int getMaxCount() {
        return this.maxCount;
    }

    public final String getOrderBy() {
        return this.orderBy;
    }

    public final String getQuery() {
        return this.query;
    }

    public final DateTime getStartDate() {
        return this.startDate;
    }

    public int hashCode() {
        return Objects.hash(this.startDate, this.endDate, this.query, this.attendeeIds, Integer.valueOf(this.maxCount), this.orderBy);
    }
}
