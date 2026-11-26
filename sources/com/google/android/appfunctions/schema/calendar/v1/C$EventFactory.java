package com.google.android.appfunctions.schema.calendar.v1;

import androidx.appfunctions.AppFunctionData;
import androidx.appfunctions.internal.AppFunctionSerializableFactory;
import com.google.android.appfunctions.schema.types.v1.C$DateTimeFactory;
import com.google.android.appfunctions.schema.types.v1.DateTime;
import com.market.sdk.reflect.ReflectUtilsForMiui;
import java.util.List;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: renamed from: com.google.android.appfunctions.schema.calendar.v1.$EventFactory, reason: invalid class name */
/* JADX INFO: compiled from: com.google.android.appfunctions:appfunctions-schema@@1.0.7 */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000\u0016\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0003\b\u0007\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u0001B\u0007¢\u0006\u0004\b\u0003\u0010\u0004J\u0010\u0010\u0005\u001a\u00020\u00022\u0006\u0010\u0006\u001a\u00020\u0007H\u0016J\u0010\u0010\b\u001a\u00020\u00072\u0006\u0010\t\u001a\u00020\u0002H\u0016¨\u0006\n"}, d2 = {"com/google/android/appfunctions/schema/calendar/v1/$EventFactory", "Landroidx/appfunctions/internal/AppFunctionSerializableFactory;", "Lcom/google/android/appfunctions/schema/calendar/v1/Event;", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "()V", "fromAppFunctionData", "appFunctionData", "Landroidx/appfunctions/AppFunctionData;", "toAppFunctionData", "appFunctionSerializable", "java.com.google.android.libraries.llm.appfunctions.appfunctions_schema2.src.com.google.android.appfunctions.schema.calendar.v1_v1"}, k = 1, mv = {2, 1, 0}, xi = 48)
public final class C$EventFactory implements AppFunctionSerializableFactory<Event> {
    /* JADX WARN: Can't rename method to resolve collision */
    @Override // androidx.appfunctions.internal.AppFunctionSerializableFactory
    public Event fromAppFunctionData(AppFunctionData appFunctionData) {
        Intrinsics.checkNotNullParameter(appFunctionData, "appFunctionData");
        AppFunctionData appFunctionDataWithSpec = getAppFunctionDataWithSpec(appFunctionData, "com.google.android.appfunctions.schema.calendar.v1.Event");
        C$DateTimeFactory c$DateTimeFactory = new C$DateTimeFactory();
        String stringOrNull = appFunctionDataWithSpec.getStringOrNull("id");
        if (stringOrNull == null) {
            throw new IllegalStateException("Required value was null.");
        }
        String stringOrNull2 = appFunctionDataWithSpec.getStringOrNull("title");
        if (stringOrNull2 == null) {
            throw new IllegalStateException("Required value was null.");
        }
        String stringOrNull3 = appFunctionDataWithSpec.getStringOrNull("description");
        AppFunctionData appFunctionData2 = appFunctionDataWithSpec.getAppFunctionData("startDate");
        if (appFunctionData2 == null) {
            throw new IllegalStateException("Required value was null.");
        }
        DateTime dateTimeFromAppFunctionData = c$DateTimeFactory.fromAppFunctionData(appFunctionData2);
        AppFunctionData appFunctionData3 = appFunctionDataWithSpec.getAppFunctionData("endDate");
        if (appFunctionData3 == null) {
            throw new IllegalStateException("Required value was null.");
        }
        DateTime dateTimeFromAppFunctionData2 = c$DateTimeFactory.fromAppFunctionData(appFunctionData3);
        List<String> stringList = appFunctionDataWithSpec.getStringList("attendeeIds");
        if (stringList == null) {
            stringList = CollectionsKt.emptyList();
        }
        return new Event(stringOrNull, stringOrNull2, stringOrNull3, dateTimeFromAppFunctionData, dateTimeFromAppFunctionData2, stringList, appFunctionDataWithSpec.getBooleanOrNull("allDay"), appFunctionDataWithSpec.getStringOrNull("location"), appFunctionDataWithSpec.getStringOrNull("recurrenceSchedule"), appFunctionDataWithSpec.getStringOrNull("eventStatus"), appFunctionDataWithSpec.getBooleanOrNull("isReadOnly"), appFunctionDataWithSpec.getBooleanOrNull("isInPublicCalendar"), appFunctionDataWithSpec.getBooleanOrNull("isOrganizer"), appFunctionDataWithSpec.getStringOrNull("selfAttendeeStatus"));
    }

    @Override // androidx.appfunctions.internal.AppFunctionSerializableFactory
    public AppFunctionData toAppFunctionData(Event appFunctionSerializable) {
        Intrinsics.checkNotNullParameter(appFunctionSerializable, "appFunctionSerializable");
        C$DateTimeFactory c$DateTimeFactory = new C$DateTimeFactory();
        AppFunctionData.Builder appFunctionDataBuilder = getAppFunctionDataBuilder("com.google.android.appfunctions.schema.calendar.v1.Event");
        appFunctionDataBuilder.setString("id", appFunctionSerializable.getId());
        appFunctionDataBuilder.setString("title", appFunctionSerializable.getTitle());
        String description = appFunctionSerializable.getDescription();
        if (description != null) {
            appFunctionDataBuilder.setString("description", description);
        }
        appFunctionDataBuilder.setAppFunctionData("startDate", c$DateTimeFactory.toAppFunctionData(appFunctionSerializable.getStartDate()));
        appFunctionDataBuilder.setAppFunctionData("endDate", c$DateTimeFactory.toAppFunctionData(appFunctionSerializable.getEndDate()));
        appFunctionDataBuilder.setStringList("attendeeIds", appFunctionSerializable.getAttendeeIds());
        Boolean allDay = appFunctionSerializable.getAllDay();
        if (allDay != null) {
            appFunctionDataBuilder.setBoolean("allDay", allDay.booleanValue());
        }
        String location = appFunctionSerializable.getLocation();
        if (location != null) {
            appFunctionDataBuilder.setString("location", location);
        }
        String recurrenceSchedule = appFunctionSerializable.getRecurrenceSchedule();
        if (recurrenceSchedule != null) {
            appFunctionDataBuilder.setString("recurrenceSchedule", recurrenceSchedule);
        }
        String eventStatus = appFunctionSerializable.getEventStatus();
        if (eventStatus != null) {
            appFunctionDataBuilder.setString("eventStatus", eventStatus);
        }
        Boolean isReadOnly = appFunctionSerializable.getIsReadOnly();
        if (isReadOnly != null) {
            appFunctionDataBuilder.setBoolean("isReadOnly", isReadOnly.booleanValue());
        }
        Boolean isInPublicCalendar = appFunctionSerializable.getIsInPublicCalendar();
        if (isInPublicCalendar != null) {
            appFunctionDataBuilder.setBoolean("isInPublicCalendar", isInPublicCalendar.booleanValue());
        }
        Boolean isOrganizer = appFunctionSerializable.getIsOrganizer();
        if (isOrganizer != null) {
            appFunctionDataBuilder.setBoolean("isOrganizer", isOrganizer.booleanValue());
        }
        String selfAttendeeStatus = appFunctionSerializable.getSelfAttendeeStatus();
        if (selfAttendeeStatus != null) {
            appFunctionDataBuilder.setString("selfAttendeeStatus", selfAttendeeStatus);
        }
        return appFunctionDataBuilder.build();
    }
}
