package com.google.android.appfunctions.schema.phone.v1;

import androidx.appfunctions.AppFunctionData;
import androidx.appfunctions.internal.AppFunctionSerializableFactory;
import com.google.android.appfunctions.schema.types.v1.C$DateTimeFactory;
import com.market.sdk.reflect.ReflectUtilsForMiui;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: renamed from: com.google.android.appfunctions.schema.phone.v1.$CallRecordFactory, reason: invalid class name */
/* JADX INFO: compiled from: com.google.android.appfunctions:appfunctions-schema@@1.0.7 */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000\u0016\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0003\b\u0007\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u0001B\u0007¢\u0006\u0004\b\u0003\u0010\u0004J\u0010\u0010\u0005\u001a\u00020\u00022\u0006\u0010\u0006\u001a\u00020\u0007H\u0016J\u0010\u0010\b\u001a\u00020\u00072\u0006\u0010\t\u001a\u00020\u0002H\u0016¨\u0006\n"}, d2 = {"com/google/android/appfunctions/schema/phone/v1/$CallRecordFactory", "Landroidx/appfunctions/internal/AppFunctionSerializableFactory;", "Lcom/google/android/appfunctions/schema/phone/v1/CallRecord;", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "()V", "fromAppFunctionData", "appFunctionData", "Landroidx/appfunctions/AppFunctionData;", "toAppFunctionData", "appFunctionSerializable", "java.com.google.android.libraries.llm.appfunctions.appfunctions_schema2.src.com.google.android.appfunctions.schema.phone.v1_v1"}, k = 1, mv = {2, 1, 0}, xi = 48)
public final class C$CallRecordFactory implements AppFunctionSerializableFactory<CallRecord> {
    /* JADX WARN: Can't rename method to resolve collision */
    @Override // androidx.appfunctions.internal.AppFunctionSerializableFactory
    public CallRecord fromAppFunctionData(AppFunctionData appFunctionData) {
        Intrinsics.checkNotNullParameter(appFunctionData, "appFunctionData");
        AppFunctionData appFunctionDataWithSpec = getAppFunctionDataWithSpec(appFunctionData, "com.google.android.appfunctions.schema.phone.v1.CallRecord");
        C$DateTimeFactory c$DateTimeFactory = new C$DateTimeFactory();
        String stringOrNull = appFunctionDataWithSpec.getStringOrNull("id");
        if (stringOrNull == null) {
            throw new IllegalStateException("Required value was null.");
        }
        String stringOrNull2 = appFunctionDataWithSpec.getStringOrNull("contactId");
        if (stringOrNull2 == null) {
            throw new IllegalStateException("Required value was null.");
        }
        String stringOrNull3 = appFunctionDataWithSpec.getStringOrNull("callType");
        if (stringOrNull3 == null) {
            throw new IllegalStateException("Required value was null.");
        }
        Boolean booleanOrNull = appFunctionDataWithSpec.getBooleanOrNull("isVideoCall");
        AppFunctionData appFunctionData2 = appFunctionDataWithSpec.getAppFunctionData("startTime");
        if (appFunctionData2 != null) {
            return new CallRecord(stringOrNull, stringOrNull2, stringOrNull3, booleanOrNull, c$DateTimeFactory.fromAppFunctionData(appFunctionData2), appFunctionDataWithSpec.getLongOrNull("durationMillis"), appFunctionDataWithSpec.getStringOrNull("sipAddress"), appFunctionDataWithSpec.getStringOrNull("phoneNumber"));
        }
        throw new IllegalStateException("Required value was null.");
    }

    @Override // androidx.appfunctions.internal.AppFunctionSerializableFactory
    public AppFunctionData toAppFunctionData(CallRecord appFunctionSerializable) {
        Intrinsics.checkNotNullParameter(appFunctionSerializable, "appFunctionSerializable");
        C$DateTimeFactory c$DateTimeFactory = new C$DateTimeFactory();
        AppFunctionData.Builder appFunctionDataBuilder = getAppFunctionDataBuilder("com.google.android.appfunctions.schema.phone.v1.CallRecord");
        appFunctionDataBuilder.setString("id", appFunctionSerializable.getId());
        appFunctionDataBuilder.setString("contactId", appFunctionSerializable.getContactId());
        appFunctionDataBuilder.setString("callType", appFunctionSerializable.getCallType());
        Boolean isVideoCall = appFunctionSerializable.getIsVideoCall();
        if (isVideoCall != null) {
            appFunctionDataBuilder.setBoolean("isVideoCall", isVideoCall.booleanValue());
        }
        appFunctionDataBuilder.setAppFunctionData("startTime", c$DateTimeFactory.toAppFunctionData(appFunctionSerializable.getStartTime()));
        Long durationMillis = appFunctionSerializable.getDurationMillis();
        if (durationMillis != null) {
            appFunctionDataBuilder.setLong("durationMillis", durationMillis.longValue());
        }
        String sipAddress = appFunctionSerializable.getSipAddress();
        if (sipAddress != null) {
            appFunctionDataBuilder.setString("sipAddress", sipAddress);
        }
        String phoneNumber = appFunctionSerializable.getPhoneNumber();
        if (phoneNumber != null) {
            appFunctionDataBuilder.setString("phoneNumber", phoneNumber);
        }
        return appFunctionDataBuilder.build();
    }
}
