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

/* JADX INFO: compiled from: com.google.android.appfunctions:appfunctions-schema@@1.0.7 */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000.\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010 \n\u0000\n\u0002\u0010\u000b\n\u0002\b\u001a\n\u0002\u0010\b\n\u0000\b\u0007\u0018\u00002\u00020\u0001Bw\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\n\b\u0002\u0010\u0004\u001a\u0004\u0018\u00010\u0003\u0012\u0006\u0010\u0005\u001a\u00020\u0006\u0012\u0006\u0010\u0007\u001a\u00020\u0006\u0012\u000e\b\u0002\u0010\b\u001a\b\u0012\u0004\u0012\u00020\u00030\t\u0012\n\b\u0002\u0010\n\u001a\u0004\u0018\u00010\u000b\u0012\n\b\u0002\u0010\f\u001a\u0004\u0018\u00010\u0003\u0012\n\b\u0002\u0010\r\u001a\u0004\u0018\u00010\u0003\u0012\n\b\u0002\u0010\u000e\u001a\u0004\u0018\u00010\u0003\u0012\n\b\u0002\u0010\u000f\u001a\u0004\u0018\u00010\u0003¢\u0006\u0004\b\u0010\u0010\u0011J\u0013\u0010#\u001a\u00020\u000b2\b\u0010$\u001a\u0004\u0018\u00010\u0001H\u0096\u0002J\b\u0010%\u001a\u00020&H\u0016R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0012\u0010\u0013R\u0013\u0010\u0004\u001a\u0004\u0018\u00010\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0014\u0010\u0013R\u0011\u0010\u0005\u001a\u00020\u0006¢\u0006\b\n\u0000\u001a\u0004\b\u0015\u0010\u0016R\u0011\u0010\u0007\u001a\u00020\u0006¢\u0006\b\n\u0000\u001a\u0004\b\u0017\u0010\u0016R\u0017\u0010\b\u001a\b\u0012\u0004\u0012\u00020\u00030\t¢\u0006\b\n\u0000\u001a\u0004\b\u0018\u0010\u0019R\u0015\u0010\n\u001a\u0004\u0018\u00010\u000b¢\u0006\n\n\u0002\u0010\u001c\u001a\u0004\b\u001a\u0010\u001bR\u0013\u0010\f\u001a\u0004\u0018\u00010\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u001d\u0010\u0013R\u0013\u0010\r\u001a\u0004\u0018\u00010\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u001e\u0010\u0013R\u001e\u0010\u000e\u001a\u0004\u0018\u00010\u00038\u0006X\u0087\u0004¢\u0006\u000e\n\u0000\u0012\u0004\b\u001f\u0010 \u001a\u0004\b!\u0010\u0013R\u0013\u0010\u000f\u001a\u0004\u0018\u00010\u0003¢\u0006\b\n\u0000\u001a\u0004\b\"\u0010\u0013¨\u0006'"}, d2 = {"Lcom/google/android/appfunctions/schema/calendar/v1/CreateEventParams;", "", "title", "", "description", "startDate", "Lcom/google/android/appfunctions/schema/types/v1/DateTime;", "endDate", "attendeeIds", "", "allDay", "", "location", "recurrenceSchedule", "eventStatus", "externalId", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "(Ljava/lang/String;Ljava/lang/String;Lcom/google/android/appfunctions/schema/types/v1/DateTime;Lcom/google/android/appfunctions/schema/types/v1/DateTime;Ljava/util/List;Ljava/lang/Boolean;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)V", "getTitle", "()Ljava/lang/String;", "getDescription", "getStartDate", "()Lcom/google/android/appfunctions/schema/types/v1/DateTime;", "getEndDate", "getAttendeeIds", "()Ljava/util/List;", "getAllDay", "()Ljava/lang/Boolean;", "Ljava/lang/Boolean;", "getLocation", "getRecurrenceSchedule", "getEventStatus$annotations", "()V", "getEventStatus", "getExternalId", "equals", RemoteDeviceInfo.MANUFACTURER_OTHER, "hashCode", "", "java.com.google.android.libraries.llm.appfunctions.appfunctions_schema2.src.com.google.android.appfunctions.schema.calendar.v1_v1"}, k = 1, mv = {2, 1, 0}, xi = 48)
public final class CreateEventParams {
    private final Boolean allDay;
    private final List<String> attendeeIds;
    private final String description;
    private final DateTime endDate;
    private final String eventStatus;
    private final String externalId;
    private final String location;
    private final String recurrenceSchedule;
    private final DateTime startDate;
    private final String title;

    public CreateEventParams(String title, String str, DateTime startDate, DateTime endDate, List<String> attendeeIds, Boolean bool, String str2, String str3, String str4, String str5) {
        Intrinsics.checkNotNullParameter(title, "title");
        Intrinsics.checkNotNullParameter(startDate, "startDate");
        Intrinsics.checkNotNullParameter(endDate, "endDate");
        Intrinsics.checkNotNullParameter(attendeeIds, "attendeeIds");
        this.title = title;
        this.description = str;
        this.startDate = startDate;
        this.endDate = endDate;
        this.attendeeIds = attendeeIds;
        this.allDay = bool;
        this.location = str2;
        this.recurrenceSchedule = str3;
        this.eventStatus = str4;
        this.externalId = str5;
    }

    public /* synthetic */ CreateEventParams(String str, String str2, DateTime dateTime, DateTime dateTime2, List list, Boolean bool, String str3, String str4, String str5, String str6, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(str, (i & 2) != 0 ? null : str2, dateTime, dateTime2, (i & 16) != 0 ? CollectionsKt.emptyList() : list, (i & 32) != 0 ? null : bool, (i & 64) != 0 ? null : str3, (i & 128) != 0 ? null : str4, (i & 256) != 0 ? null : str5, (i & 512) != 0 ? null : str6);
    }

    public boolean equals(Object other) {
        if (!(other instanceof CreateEventParams)) {
            return false;
        }
        CreateEventParams createEventParams = (CreateEventParams) other;
        return Intrinsics.areEqual(this.title, createEventParams.title) && Intrinsics.areEqual(this.description, createEventParams.description) && Intrinsics.areEqual(this.startDate, createEventParams.startDate) && Intrinsics.areEqual(this.endDate, createEventParams.endDate) && Intrinsics.areEqual(this.attendeeIds, createEventParams.attendeeIds) && Intrinsics.areEqual(this.allDay, createEventParams.allDay) && Intrinsics.areEqual(this.location, createEventParams.location) && Intrinsics.areEqual(this.recurrenceSchedule, createEventParams.recurrenceSchedule) && Intrinsics.areEqual(this.eventStatus, createEventParams.eventStatus) && Intrinsics.areEqual(this.externalId, createEventParams.externalId);
    }

    public final Boolean getAllDay() {
        return this.allDay;
    }

    public final List<String> getAttendeeIds() {
        return this.attendeeIds;
    }

    public final String getDescription() {
        return this.description;
    }

    public final DateTime getEndDate() {
        return this.endDate;
    }

    public final String getEventStatus() {
        return this.eventStatus;
    }

    public final String getExternalId() {
        return this.externalId;
    }

    public final String getLocation() {
        return this.location;
    }

    public final String getRecurrenceSchedule() {
        return this.recurrenceSchedule;
    }

    public final DateTime getStartDate() {
        return this.startDate;
    }

    public final String getTitle() {
        return this.title;
    }

    public int hashCode() {
        return Objects.hash(this.title, this.description, this.startDate, this.endDate, this.attendeeIds, this.allDay, this.location, this.recurrenceSchedule, this.eventStatus, this.externalId);
    }
}
