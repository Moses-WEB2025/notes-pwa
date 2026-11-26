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
@Metadata(d1 = {"\u00000\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010 \n\u0000\n\u0002\u0010\u000b\n\u0002\b\u001f\n\u0002\u0010\b\n\u0002\b\u0002\b\u0007\u0018\u0000 -2\u00020\u0001:\u0001-B£\u0001\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0003\u0012\n\b\u0002\u0010\u0005\u001a\u0004\u0018\u00010\u0003\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u0012\u0006\u0010\b\u001a\u00020\u0007\u0012\u000e\b\u0002\u0010\t\u001a\b\u0012\u0004\u0012\u00020\u00030\n\u0012\n\b\u0002\u0010\u000b\u001a\u0004\u0018\u00010\f\u0012\n\b\u0002\u0010\r\u001a\u0004\u0018\u00010\u0003\u0012\n\b\u0002\u0010\u000e\u001a\u0004\u0018\u00010\u0003\u0012\n\b\u0002\u0010\u000f\u001a\u0004\u0018\u00010\u0003\u0012\n\b\u0002\u0010\u0010\u001a\u0004\u0018\u00010\f\u0012\n\b\u0002\u0010\u0011\u001a\u0004\u0018\u00010\f\u0012\n\b\u0002\u0010\u0012\u001a\u0004\u0018\u00010\f\u0012\n\b\u0002\u0010\u0013\u001a\u0004\u0018\u00010\u0003¢\u0006\u0004\b\u0014\u0010\u0015J\u0013\u0010)\u001a\u00020\f2\b\u0010*\u001a\u0004\u0018\u00010\u0001H\u0096\u0002J\b\u0010+\u001a\u00020,H\u0016R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0016\u0010\u0017R\u0011\u0010\u0004\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0018\u0010\u0017R\u0013\u0010\u0005\u001a\u0004\u0018\u00010\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0019\u0010\u0017R\u0011\u0010\u0006\u001a\u00020\u0007¢\u0006\b\n\u0000\u001a\u0004\b\u001a\u0010\u001bR\u0011\u0010\b\u001a\u00020\u0007¢\u0006\b\n\u0000\u001a\u0004\b\u001c\u0010\u001bR\u0017\u0010\t\u001a\b\u0012\u0004\u0012\u00020\u00030\n¢\u0006\b\n\u0000\u001a\u0004\b\u001d\u0010\u001eR\u0015\u0010\u000b\u001a\u0004\u0018\u00010\f¢\u0006\n\n\u0002\u0010!\u001a\u0004\b\u001f\u0010 R\u0013\u0010\r\u001a\u0004\u0018\u00010\u0003¢\u0006\b\n\u0000\u001a\u0004\b\"\u0010\u0017R\u0013\u0010\u000e\u001a\u0004\u0018\u00010\u0003¢\u0006\b\n\u0000\u001a\u0004\b#\u0010\u0017R\u001e\u0010\u000f\u001a\u0004\u0018\u00010\u00038\u0006X\u0087\u0004¢\u0006\u000e\n\u0000\u0012\u0004\b$\u0010%\u001a\u0004\b&\u0010\u0017R\u0015\u0010\u0010\u001a\u0004\u0018\u00010\f¢\u0006\n\n\u0002\u0010!\u001a\u0004\b\u0010\u0010 R\u0015\u0010\u0011\u001a\u0004\u0018\u00010\f¢\u0006\n\n\u0002\u0010!\u001a\u0004\b\u0011\u0010 R\u0015\u0010\u0012\u001a\u0004\u0018\u00010\f¢\u0006\n\n\u0002\u0010!\u001a\u0004\b\u0012\u0010 R\u001e\u0010\u0013\u001a\u0004\u0018\u00010\u00038\u0006X\u0087\u0004¢\u0006\u000e\n\u0000\u0012\u0004\b'\u0010%\u001a\u0004\b(\u0010\u0017¨\u0006."}, d2 = {"Lcom/google/android/appfunctions/schema/calendar/v1/Event;", "", "id", "", "title", "description", "startDate", "Lcom/google/android/appfunctions/schema/types/v1/DateTime;", "endDate", "attendeeIds", "", "allDay", "", "location", "recurrenceSchedule", "eventStatus", "isReadOnly", "isInPublicCalendar", "isOrganizer", "selfAttendeeStatus", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Lcom/google/android/appfunctions/schema/types/v1/DateTime;Lcom/google/android/appfunctions/schema/types/v1/DateTime;Ljava/util/List;Ljava/lang/Boolean;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/Boolean;Ljava/lang/Boolean;Ljava/lang/Boolean;Ljava/lang/String;)V", "getId", "()Ljava/lang/String;", "getTitle", "getDescription", "getStartDate", "()Lcom/google/android/appfunctions/schema/types/v1/DateTime;", "getEndDate", "getAttendeeIds", "()Ljava/util/List;", "getAllDay", "()Ljava/lang/Boolean;", "Ljava/lang/Boolean;", "getLocation", "getRecurrenceSchedule", "getEventStatus$annotations", "()V", "getEventStatus", "getSelfAttendeeStatus$annotations", "getSelfAttendeeStatus", "equals", RemoteDeviceInfo.MANUFACTURER_OTHER, "hashCode", "", "Companion", "java.com.google.android.libraries.llm.appfunctions.appfunctions_schema2.src.com.google.android.appfunctions.schema.calendar.v1_v1"}, k = 1, mv = {2, 1, 0}, xi = 48)
public final class Event {
    public static final String ASSIST_FOREGROUND_TYPE = "com.google.android.appfunctions.schema.calendar.v1.EventForeground";
    public static final String ATTENDEE_ACCEPTED = "STATUS_ACCEPTED";
    public static final String ATTENDEE_DECLINED = "STATUS_DECLINED";
    public static final String ATTENDEE_TENTATIVE = "STATUS_TENTATIVE";
    public static final String EVENT_CANCELLED = "EVENT_CANCELLED";
    public static final String EVENT_CONFIRMED = "EVENT_CONFIRMED";
    public static final String EVENT_TENTATIVE = "EVENT_TENTATIVE";
    public static final String EVENT_UNSPECIFIED = "EVENT_UNSPECIFIED";
    public static final String START_TIME_ORDER_ASCENDING = "START_TIME_ASCENDING";
    public static final String START_TIME_ORDER_DESCENDING = "START_TIME_DESCENDING";
    private final Boolean allDay;
    private final List<String> attendeeIds;
    private final String description;
    private final DateTime endDate;
    private final String eventStatus;
    private final String id;
    private final Boolean isInPublicCalendar;
    private final Boolean isOrganizer;
    private final Boolean isReadOnly;
    private final String location;
    private final String recurrenceSchedule;
    private final String selfAttendeeStatus;
    private final DateTime startDate;
    private final String title;

    public Event(String id, String title, String str, DateTime startDate, DateTime endDate, List<String> attendeeIds, Boolean bool, String str2, String str3, String str4, Boolean bool2, Boolean bool3, Boolean bool4, String str5) {
        Intrinsics.checkNotNullParameter(id, "id");
        Intrinsics.checkNotNullParameter(title, "title");
        Intrinsics.checkNotNullParameter(startDate, "startDate");
        Intrinsics.checkNotNullParameter(endDate, "endDate");
        Intrinsics.checkNotNullParameter(attendeeIds, "attendeeIds");
        this.id = id;
        this.title = title;
        this.description = str;
        this.startDate = startDate;
        this.endDate = endDate;
        this.attendeeIds = attendeeIds;
        this.allDay = bool;
        this.location = str2;
        this.recurrenceSchedule = str3;
        this.eventStatus = str4;
        this.isReadOnly = bool2;
        this.isInPublicCalendar = bool3;
        this.isOrganizer = bool4;
        this.selfAttendeeStatus = str5;
    }

    public /* synthetic */ Event(String str, String str2, String str3, DateTime dateTime, DateTime dateTime2, List list, Boolean bool, String str4, String str5, String str6, Boolean bool2, Boolean bool3, Boolean bool4, String str7, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(str, str2, (i & 4) != 0 ? null : str3, dateTime, dateTime2, (i & 32) != 0 ? CollectionsKt.emptyList() : list, (i & 64) != 0 ? null : bool, (i & 128) != 0 ? null : str4, (i & 256) != 0 ? null : str5, (i & 512) != 0 ? null : str6, (i & 1024) != 0 ? null : bool2, (i & 2048) != 0 ? null : bool3, (i & 4096) != 0 ? null : bool4, (i & 8192) != 0 ? null : str7);
    }

    public boolean equals(Object other) {
        if (!(other instanceof Event)) {
            return false;
        }
        Event event = (Event) other;
        return Intrinsics.areEqual(this.id, event.id) && Intrinsics.areEqual(this.title, event.title) && Intrinsics.areEqual(this.description, event.description) && Intrinsics.areEqual(this.startDate, event.startDate) && Intrinsics.areEqual(this.endDate, event.endDate) && Intrinsics.areEqual(this.attendeeIds, event.attendeeIds) && Intrinsics.areEqual(this.allDay, event.allDay) && Intrinsics.areEqual(this.location, event.location) && Intrinsics.areEqual(this.recurrenceSchedule, event.recurrenceSchedule) && Intrinsics.areEqual(this.eventStatus, event.eventStatus) && Intrinsics.areEqual(this.isReadOnly, event.isReadOnly) && Intrinsics.areEqual(this.isInPublicCalendar, event.isInPublicCalendar) && Intrinsics.areEqual(this.isOrganizer, event.isOrganizer) && Intrinsics.areEqual(this.selfAttendeeStatus, event.selfAttendeeStatus);
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

    public final String getId() {
        return this.id;
    }

    public final String getLocation() {
        return this.location;
    }

    public final String getRecurrenceSchedule() {
        return this.recurrenceSchedule;
    }

    public final String getSelfAttendeeStatus() {
        return this.selfAttendeeStatus;
    }

    public final DateTime getStartDate() {
        return this.startDate;
    }

    public final String getTitle() {
        return this.title;
    }

    public int hashCode() {
        return Objects.hash(this.id, this.title, this.description, this.startDate, this.endDate, this.attendeeIds, this.allDay, this.location, this.recurrenceSchedule, this.eventStatus, this.isReadOnly, this.isInPublicCalendar, this.isOrganizer, this.selfAttendeeStatus);
    }

    /* JADX INFO: renamed from: isInPublicCalendar, reason: from getter */
    public final Boolean getIsInPublicCalendar() {
        return this.isInPublicCalendar;
    }

    /* JADX INFO: renamed from: isOrganizer, reason: from getter */
    public final Boolean getIsOrganizer() {
        return this.isOrganizer;
    }

    /* JADX INFO: renamed from: isReadOnly, reason: from getter */
    public final Boolean getIsReadOnly() {
        return this.isReadOnly;
    }
}
