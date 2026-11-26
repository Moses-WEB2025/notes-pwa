package com.google.android.appfunctions.schema.calendar.v1;

import androidx.appfunctions.AppFunctionData;
import androidx.appfunctions.internal.AppFunctionSerializableFactory;
import com.google.android.appfunctions.schema.types.v1.C$DateTimeFactory;
import com.google.android.appfunctions.schema.types.v1.C$SetFieldFactory;
import com.google.android.appfunctions.schema.types.v1.DateTime;
import com.google.android.appfunctions.schema.types.v1.SetField;
import com.market.sdk.reflect.ReflectUtilsForMiui;
import java.util.List;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: renamed from: com.google.android.appfunctions.schema.calendar.v1.$UpdateEventParamsFactory, reason: invalid class name */
/* JADX INFO: compiled from: com.google.android.appfunctions:appfunctions-schema@@1.0.7 */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000\u0016\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0003\b\u0007\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u0001B\u0007¢\u0006\u0004\b\u0003\u0010\u0004J\u0010\u0010\u0005\u001a\u00020\u00022\u0006\u0010\u0006\u001a\u00020\u0007H\u0016J\u0010\u0010\b\u001a\u00020\u00072\u0006\u0010\t\u001a\u00020\u0002H\u0016¨\u0006\n"}, d2 = {"com/google/android/appfunctions/schema/calendar/v1/$UpdateEventParamsFactory", "Landroidx/appfunctions/internal/AppFunctionSerializableFactory;", "Lcom/google/android/appfunctions/schema/calendar/v1/UpdateEventParams;", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "()V", "fromAppFunctionData", "appFunctionData", "Landroidx/appfunctions/AppFunctionData;", "toAppFunctionData", "appFunctionSerializable", "java.com.google.android.libraries.llm.appfunctions.appfunctions_schema2.src.com.google.android.appfunctions.schema.calendar.v1_v1"}, k = 1, mv = {2, 1, 0}, xi = 48)
public final class C$UpdateEventParamsFactory implements AppFunctionSerializableFactory<UpdateEventParams> {
    /* JADX WARN: Can't rename method to resolve collision */
    @Override // androidx.appfunctions.internal.AppFunctionSerializableFactory
    public UpdateEventParams fromAppFunctionData(AppFunctionData appFunctionData) {
        Intrinsics.checkNotNullParameter(appFunctionData, "appFunctionData");
        AppFunctionData appFunctionDataWithSpec = getAppFunctionDataWithSpec(appFunctionData, "com.google.android.appfunctions.schema.calendar.v1.UpdateEventParams");
        C$SetFieldFactory c$SetFieldFactory = new C$SetFieldFactory(new AppFunctionSerializableFactory.TypeParameter.PrimitiveTypeParameter(String.class));
        C$SetFieldFactory c$SetFieldFactory2 = new C$SetFieldFactory(new AppFunctionSerializableFactory.TypeParameter.PrimitiveTypeParameter(String.class));
        C$SetFieldFactory c$SetFieldFactory3 = new C$SetFieldFactory(new AppFunctionSerializableFactory.TypeParameter.SerializableTypeParameter(DateTime.class, new C$DateTimeFactory()));
        C$SetFieldFactory c$SetFieldFactory4 = new C$SetFieldFactory(new AppFunctionSerializableFactory.TypeParameter.PrimitiveListTypeParameter(String.class));
        C$SetFieldFactory c$SetFieldFactory5 = new C$SetFieldFactory(new AppFunctionSerializableFactory.TypeParameter.PrimitiveTypeParameter(Boolean.TYPE));
        String stringOrNull = appFunctionDataWithSpec.getStringOrNull("eventId");
        if (stringOrNull == null) {
            throw new IllegalStateException("Required value was null.");
        }
        AppFunctionData appFunctionData2 = appFunctionDataWithSpec.getAppFunctionData("title");
        SetField setFieldFromAppFunctionData = appFunctionData2 != null ? c$SetFieldFactory.fromAppFunctionData(appFunctionData2) : null;
        AppFunctionData appFunctionData3 = appFunctionDataWithSpec.getAppFunctionData("description");
        SetField setFieldFromAppFunctionData2 = appFunctionData3 != null ? c$SetFieldFactory2.fromAppFunctionData(appFunctionData3) : null;
        AppFunctionData appFunctionData4 = appFunctionDataWithSpec.getAppFunctionData("startDate");
        SetField setFieldFromAppFunctionData3 = appFunctionData4 != null ? c$SetFieldFactory3.fromAppFunctionData(appFunctionData4) : null;
        AppFunctionData appFunctionData5 = appFunctionDataWithSpec.getAppFunctionData("endDate");
        SetField setFieldFromAppFunctionData4 = appFunctionData5 != null ? c$SetFieldFactory3.fromAppFunctionData(appFunctionData5) : null;
        AppFunctionData appFunctionData6 = appFunctionDataWithSpec.getAppFunctionData("attendeeIds");
        SetField setFieldFromAppFunctionData5 = appFunctionData6 != null ? c$SetFieldFactory4.fromAppFunctionData(appFunctionData6) : null;
        AppFunctionData appFunctionData7 = appFunctionDataWithSpec.getAppFunctionData("allDay");
        SetField setFieldFromAppFunctionData6 = appFunctionData7 != null ? c$SetFieldFactory5.fromAppFunctionData(appFunctionData7) : null;
        AppFunctionData appFunctionData8 = appFunctionDataWithSpec.getAppFunctionData("location");
        SetField setFieldFromAppFunctionData7 = appFunctionData8 != null ? c$SetFieldFactory2.fromAppFunctionData(appFunctionData8) : null;
        AppFunctionData appFunctionData9 = appFunctionDataWithSpec.getAppFunctionData("recurrenceSchedule");
        SetField setFieldFromAppFunctionData8 = appFunctionData9 != null ? c$SetFieldFactory2.fromAppFunctionData(appFunctionData9) : null;
        AppFunctionData appFunctionData10 = appFunctionDataWithSpec.getAppFunctionData("eventStatus");
        return new UpdateEventParams(stringOrNull, setFieldFromAppFunctionData, setFieldFromAppFunctionData2, setFieldFromAppFunctionData3, setFieldFromAppFunctionData4, setFieldFromAppFunctionData5, setFieldFromAppFunctionData6, setFieldFromAppFunctionData7, setFieldFromAppFunctionData8, appFunctionData10 != null ? c$SetFieldFactory.fromAppFunctionData(appFunctionData10) : null);
    }

    @Override // androidx.appfunctions.internal.AppFunctionSerializableFactory
    public AppFunctionData toAppFunctionData(UpdateEventParams appFunctionSerializable) {
        Intrinsics.checkNotNullParameter(appFunctionSerializable, "appFunctionSerializable");
        C$SetFieldFactory c$SetFieldFactory = new C$SetFieldFactory(new AppFunctionSerializableFactory.TypeParameter.PrimitiveTypeParameter(String.class));
        C$SetFieldFactory c$SetFieldFactory2 = new C$SetFieldFactory(new AppFunctionSerializableFactory.TypeParameter.PrimitiveTypeParameter(String.class));
        C$SetFieldFactory c$SetFieldFactory3 = new C$SetFieldFactory(new AppFunctionSerializableFactory.TypeParameter.SerializableTypeParameter(DateTime.class, new C$DateTimeFactory()));
        C$SetFieldFactory c$SetFieldFactory4 = new C$SetFieldFactory(new AppFunctionSerializableFactory.TypeParameter.PrimitiveListTypeParameter(String.class));
        C$SetFieldFactory c$SetFieldFactory5 = new C$SetFieldFactory(new AppFunctionSerializableFactory.TypeParameter.PrimitiveTypeParameter(Boolean.TYPE));
        AppFunctionData.Builder appFunctionDataBuilder = getAppFunctionDataBuilder("com.google.android.appfunctions.schema.calendar.v1.UpdateEventParams");
        appFunctionDataBuilder.setString("eventId", appFunctionSerializable.getEventId());
        SetField<String> title = appFunctionSerializable.getTitle();
        if (title != null) {
            appFunctionDataBuilder.setAppFunctionData("title", c$SetFieldFactory.toAppFunctionData((SetField) title));
        }
        SetField<String> description = appFunctionSerializable.getDescription();
        if (description != null) {
            appFunctionDataBuilder.setAppFunctionData("description", c$SetFieldFactory2.toAppFunctionData((SetField) description));
        }
        SetField<DateTime> startDate = appFunctionSerializable.getStartDate();
        if (startDate != null) {
            appFunctionDataBuilder.setAppFunctionData("startDate", c$SetFieldFactory3.toAppFunctionData((SetField) startDate));
        }
        SetField<DateTime> endDate = appFunctionSerializable.getEndDate();
        if (endDate != null) {
            appFunctionDataBuilder.setAppFunctionData("endDate", c$SetFieldFactory3.toAppFunctionData((SetField) endDate));
        }
        SetField<List<String>> attendeeIds = appFunctionSerializable.getAttendeeIds();
        if (attendeeIds != null) {
            appFunctionDataBuilder.setAppFunctionData("attendeeIds", c$SetFieldFactory4.toAppFunctionData((SetField) attendeeIds));
        }
        SetField<Boolean> allDay = appFunctionSerializable.getAllDay();
        if (allDay != null) {
            appFunctionDataBuilder.setAppFunctionData("allDay", c$SetFieldFactory5.toAppFunctionData((SetField) allDay));
        }
        SetField<String> location = appFunctionSerializable.getLocation();
        if (location != null) {
            appFunctionDataBuilder.setAppFunctionData("location", c$SetFieldFactory2.toAppFunctionData((SetField) location));
        }
        SetField<String> recurrenceSchedule = appFunctionSerializable.getRecurrenceSchedule();
        if (recurrenceSchedule != null) {
            appFunctionDataBuilder.setAppFunctionData("recurrenceSchedule", c$SetFieldFactory2.toAppFunctionData((SetField) recurrenceSchedule));
        }
        SetField<String> eventStatus = appFunctionSerializable.getEventStatus();
        if (eventStatus != null) {
            appFunctionDataBuilder.setAppFunctionData("eventStatus", c$SetFieldFactory.toAppFunctionData((SetField) eventStatus));
        }
        return appFunctionDataBuilder.build();
    }
}
