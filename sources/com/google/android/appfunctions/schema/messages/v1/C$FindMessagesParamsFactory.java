package com.google.android.appfunctions.schema.messages.v1;

import androidx.appfunctions.AppFunctionData;
import androidx.appfunctions.internal.AppFunctionSerializableFactory;
import com.google.android.appfunctions.schema.types.v1.C$DateTimeFactory;
import com.google.android.appfunctions.schema.types.v1.DateTime;
import com.market.sdk.reflect.ReflectUtilsForMiui;
import java.util.List;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import miuix.smartaction.SmartAction;

/* JADX INFO: renamed from: com.google.android.appfunctions.schema.messages.v1.$FindMessagesParamsFactory, reason: invalid class name */
/* JADX INFO: compiled from: com.google.android.appfunctions:appfunctions-schema@@1.0.7 */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000\u0016\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0003\b\u0007\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u0001B\u0007¢\u0006\u0004\b\u0003\u0010\u0004J\u0010\u0010\u0005\u001a\u00020\u00022\u0006\u0010\u0006\u001a\u00020\u0007H\u0016J\u0010\u0010\b\u001a\u00020\u00072\u0006\u0010\t\u001a\u00020\u0002H\u0016¨\u0006\n"}, d2 = {"com/google/android/appfunctions/schema/messages/v1/$FindMessagesParamsFactory", "Landroidx/appfunctions/internal/AppFunctionSerializableFactory;", "Lcom/google/android/appfunctions/schema/messages/v1/FindMessagesParams;", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "()V", "fromAppFunctionData", "appFunctionData", "Landroidx/appfunctions/AppFunctionData;", "toAppFunctionData", "appFunctionSerializable", "java.com.google.android.libraries.llm.appfunctions.appfunctions_schema2.src.com.google.android.appfunctions.schema.messages.v1_v1"}, k = 1, mv = {2, 1, 0}, xi = 48)
public final class C$FindMessagesParamsFactory implements AppFunctionSerializableFactory<FindMessagesParams> {
    /* JADX WARN: Can't rename method to resolve collision */
    @Override // androidx.appfunctions.internal.AppFunctionSerializableFactory
    public FindMessagesParams fromAppFunctionData(AppFunctionData appFunctionData) {
        Intrinsics.checkNotNullParameter(appFunctionData, "appFunctionData");
        AppFunctionData appFunctionDataWithSpec = getAppFunctionDataWithSpec(appFunctionData, "com.google.android.appfunctions.schema.messages.v1.FindMessagesParams");
        C$DateTimeFactory c$DateTimeFactory = new C$DateTimeFactory();
        String stringOrNull = appFunctionDataWithSpec.getStringOrNull("senderId");
        List<String> stringList = appFunctionDataWithSpec.getStringList("recipientsIds");
        if (stringList == null) {
            stringList = CollectionsKt.emptyList();
        }
        List<String> list = stringList;
        AppFunctionData appFunctionData2 = appFunctionDataWithSpec.getAppFunctionData("startTime");
        DateTime dateTimeFromAppFunctionData = appFunctionData2 != null ? c$DateTimeFactory.fromAppFunctionData(appFunctionData2) : null;
        AppFunctionData appFunctionData3 = appFunctionDataWithSpec.getAppFunctionData("endTime");
        DateTime dateTimeFromAppFunctionData2 = appFunctionData3 != null ? c$DateTimeFactory.fromAppFunctionData(appFunctionData3) : null;
        String stringOrNull2 = appFunctionDataWithSpec.getStringOrNull(SmartAction.Feature.QUERY);
        Boolean booleanOrNull = appFunctionDataWithSpec.getBooleanOrNull("read");
        Integer intOrNull = appFunctionDataWithSpec.getIntOrNull("maxCount");
        if (intOrNull != null) {
            return new FindMessagesParams(stringOrNull, list, dateTimeFromAppFunctionData, dateTimeFromAppFunctionData2, stringOrNull2, booleanOrNull, intOrNull.intValue());
        }
        throw new IllegalStateException("Required value was null.");
    }

    @Override // androidx.appfunctions.internal.AppFunctionSerializableFactory
    public AppFunctionData toAppFunctionData(FindMessagesParams appFunctionSerializable) {
        Intrinsics.checkNotNullParameter(appFunctionSerializable, "appFunctionSerializable");
        C$DateTimeFactory c$DateTimeFactory = new C$DateTimeFactory();
        AppFunctionData.Builder appFunctionDataBuilder = getAppFunctionDataBuilder("com.google.android.appfunctions.schema.messages.v1.FindMessagesParams");
        String senderId = appFunctionSerializable.getSenderId();
        if (senderId != null) {
            appFunctionDataBuilder.setString("senderId", senderId);
        }
        appFunctionDataBuilder.setStringList("recipientsIds", appFunctionSerializable.getRecipientsIds());
        DateTime startTime = appFunctionSerializable.getStartTime();
        if (startTime != null) {
            appFunctionDataBuilder.setAppFunctionData("startTime", c$DateTimeFactory.toAppFunctionData(startTime));
        }
        DateTime endTime = appFunctionSerializable.getEndTime();
        if (endTime != null) {
            appFunctionDataBuilder.setAppFunctionData("endTime", c$DateTimeFactory.toAppFunctionData(endTime));
        }
        String query = appFunctionSerializable.getQuery();
        if (query != null) {
            appFunctionDataBuilder.setString(SmartAction.Feature.QUERY, query);
        }
        Boolean read = appFunctionSerializable.getRead();
        if (read != null) {
            appFunctionDataBuilder.setBoolean("read", read.booleanValue());
        }
        appFunctionDataBuilder.setInt("maxCount", appFunctionSerializable.getMaxCount());
        return appFunctionDataBuilder.build();
    }
}
