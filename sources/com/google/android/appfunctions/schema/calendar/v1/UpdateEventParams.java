package com.google.android.appfunctions.schema.calendar.v1;

import com.google.android.appfunctions.schema.types.v1.DateTime;
import com.google.android.appfunctions.schema.types.v1.SetField;
import com.market.sdk.reflect.ReflectUtilsForMiui;
import com.xiaomi.mirror.RemoteDeviceInfo;
import java.util.List;
import java.util.Objects;
import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: com.google.android.appfunctions:appfunctions-schema@@1.0.7 */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u00004\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010 \n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0016\n\u0002\u0010\b\n\u0000\b\u0007\u0018\u00002\u00020\u0001B½\u0001\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0010\b\u0002\u0010\u0004\u001a\n\u0012\u0004\u0012\u00020\u0003\u0018\u00010\u0005\u0012\u0012\b\u0002\u0010\u0006\u001a\f\u0012\u0006\u0012\u0004\u0018\u00010\u0003\u0018\u00010\u0005\u0012\u0010\b\u0002\u0010\u0007\u001a\n\u0012\u0004\u0012\u00020\b\u0018\u00010\u0005\u0012\u0010\b\u0002\u0010\t\u001a\n\u0012\u0004\u0012\u00020\b\u0018\u00010\u0005\u0012\u0016\b\u0002\u0010\n\u001a\u0010\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00030\u000b\u0018\u00010\u0005\u0012\u0010\b\u0002\u0010\f\u001a\n\u0012\u0004\u0012\u00020\r\u0018\u00010\u0005\u0012\u0012\b\u0002\u0010\u000e\u001a\f\u0012\u0006\u0012\u0004\u0018\u00010\u0003\u0018\u00010\u0005\u0012\u0012\b\u0002\u0010\u000f\u001a\f\u0012\u0006\u0012\u0004\u0018\u00010\u0003\u0018\u00010\u0005\u0012\u0010\b\u0002\u0010\u0010\u001a\n\u0012\u0004\u0012\u00020\u0003\u0018\u00010\u0005¢\u0006\u0004\b\u0011\u0010\u0012J\u0013\u0010!\u001a\u00020\r2\b\u0010\"\u001a\u0004\u0018\u00010\u0001H\u0096\u0002J\b\u0010#\u001a\u00020$H\u0016R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0013\u0010\u0014R\u0019\u0010\u0004\u001a\n\u0012\u0004\u0012\u00020\u0003\u0018\u00010\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u0015\u0010\u0016R\u001b\u0010\u0006\u001a\f\u0012\u0006\u0012\u0004\u0018\u00010\u0003\u0018\u00010\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u0017\u0010\u0016R\u0019\u0010\u0007\u001a\n\u0012\u0004\u0012\u00020\b\u0018\u00010\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u0018\u0010\u0016R\u0019\u0010\t\u001a\n\u0012\u0004\u0012\u00020\b\u0018\u00010\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u0019\u0010\u0016R\u001f\u0010\n\u001a\u0010\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00030\u000b\u0018\u00010\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u001a\u0010\u0016R\u0019\u0010\f\u001a\n\u0012\u0004\u0012\u00020\r\u0018\u00010\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u001b\u0010\u0016R\u001b\u0010\u000e\u001a\f\u0012\u0006\u0012\u0004\u0018\u00010\u0003\u0018\u00010\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u001c\u0010\u0016R\u001b\u0010\u000f\u001a\f\u0012\u0006\u0012\u0004\u0018\u00010\u0003\u0018\u00010\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u001d\u0010\u0016R$\u0010\u0010\u001a\n\u0012\u0004\u0012\u00020\u0003\u0018\u00010\u00058\u0006X\u0087\u0004¢\u0006\u000e\n\u0000\u0012\u0004\b\u001e\u0010\u001f\u001a\u0004\b \u0010\u0016¨\u0006%"}, d2 = {"Lcom/google/android/appfunctions/schema/calendar/v1/UpdateEventParams;", "", "eventId", "", "title", "Lcom/google/android/appfunctions/schema/types/v1/SetField;", "description", "startDate", "Lcom/google/android/appfunctions/schema/types/v1/DateTime;", "endDate", "attendeeIds", "", "allDay", "", "location", "recurrenceSchedule", "eventStatus", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "(Ljava/lang/String;Lcom/google/android/appfunctions/schema/types/v1/SetField;Lcom/google/android/appfunctions/schema/types/v1/SetField;Lcom/google/android/appfunctions/schema/types/v1/SetField;Lcom/google/android/appfunctions/schema/types/v1/SetField;Lcom/google/android/appfunctions/schema/types/v1/SetField;Lcom/google/android/appfunctions/schema/types/v1/SetField;Lcom/google/android/appfunctions/schema/types/v1/SetField;Lcom/google/android/appfunctions/schema/types/v1/SetField;Lcom/google/android/appfunctions/schema/types/v1/SetField;)V", "getEventId", "()Ljava/lang/String;", "getTitle", "()Lcom/google/android/appfunctions/schema/types/v1/SetField;", "getDescription", "getStartDate", "getEndDate", "getAttendeeIds", "getAllDay", "getLocation", "getRecurrenceSchedule", "getEventStatus$annotations", "()V", "getEventStatus", "equals", RemoteDeviceInfo.MANUFACTURER_OTHER, "hashCode", "", "java.com.google.android.libraries.llm.appfunctions.appfunctions_schema2.src.com.google.android.appfunctions.schema.calendar.v1_v1"}, k = 1, mv = {2, 1, 0}, xi = 48)
public final class UpdateEventParams {
    private final SetField<Boolean> allDay;
    private final SetField<List<String>> attendeeIds;
    private final SetField<String> description;
    private final SetField<DateTime> endDate;
    private final String eventId;
    private final SetField<String> eventStatus;
    private final SetField<String> location;
    private final SetField<String> recurrenceSchedule;
    private final SetField<DateTime> startDate;
    private final SetField<String> title;

    /* JADX WARN: Multi-variable type inference failed */
    public UpdateEventParams(String eventId, SetField<String> setField, SetField<String> setField2, SetField<DateTime> setField3, SetField<DateTime> setField4, SetField<? extends List<String>> setField5, SetField<Boolean> setField6, SetField<String> setField7, SetField<String> setField8, SetField<String> setField9) {
        Intrinsics.checkNotNullParameter(eventId, "eventId");
        this.eventId = eventId;
        this.title = setField;
        this.description = setField2;
        this.startDate = setField3;
        this.endDate = setField4;
        this.attendeeIds = setField5;
        this.allDay = setField6;
        this.location = setField7;
        this.recurrenceSchedule = setField8;
        this.eventStatus = setField9;
    }

    public /* synthetic */ UpdateEventParams(String str, SetField setField, SetField setField2, SetField setField3, SetField setField4, SetField setField5, SetField setField6, SetField setField7, SetField setField8, SetField setField9, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(str, (i & 2) != 0 ? null : setField, (i & 4) != 0 ? null : setField2, (i & 8) != 0 ? null : setField3, (i & 16) != 0 ? null : setField4, (i & 32) != 0 ? null : setField5, (i & 64) != 0 ? null : setField6, (i & 128) != 0 ? null : setField7, (i & 256) != 0 ? null : setField8, (i & 512) != 0 ? null : setField9);
    }

    public boolean equals(Object other) {
        if (!(other instanceof UpdateEventParams)) {
            return false;
        }
        UpdateEventParams updateEventParams = (UpdateEventParams) other;
        return Intrinsics.areEqual(this.eventId, updateEventParams.eventId) && Intrinsics.areEqual(this.title, updateEventParams.title) && Intrinsics.areEqual(this.description, updateEventParams.description) && Intrinsics.areEqual(this.startDate, updateEventParams.startDate) && Intrinsics.areEqual(this.endDate, updateEventParams.endDate) && Intrinsics.areEqual(this.attendeeIds, updateEventParams.attendeeIds) && Intrinsics.areEqual(this.allDay, updateEventParams.allDay) && Intrinsics.areEqual(this.location, updateEventParams.location) && Intrinsics.areEqual(this.recurrenceSchedule, updateEventParams.recurrenceSchedule) && Intrinsics.areEqual(this.eventStatus, updateEventParams.eventStatus);
    }

    public final SetField<Boolean> getAllDay() {
        return this.allDay;
    }

    public final SetField<List<String>> getAttendeeIds() {
        return this.attendeeIds;
    }

    public final SetField<String> getDescription() {
        return this.description;
    }

    public final SetField<DateTime> getEndDate() {
        return this.endDate;
    }

    public final String getEventId() {
        return this.eventId;
    }

    public final SetField<String> getEventStatus() {
        return this.eventStatus;
    }

    public final SetField<String> getLocation() {
        return this.location;
    }

    public final SetField<String> getRecurrenceSchedule() {
        return this.recurrenceSchedule;
    }

    public final SetField<DateTime> getStartDate() {
        return this.startDate;
    }

    public final SetField<String> getTitle() {
        return this.title;
    }

    public int hashCode() {
        return Objects.hash(this.eventId, this.title, this.description, this.startDate, this.endDate, this.attendeeIds, this.allDay, this.location, this.recurrenceSchedule, this.eventStatus);
    }
}
