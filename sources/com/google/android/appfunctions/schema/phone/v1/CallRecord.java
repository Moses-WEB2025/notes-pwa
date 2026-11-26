package com.google.android.appfunctions.schema.phone.v1;

import com.google.android.appfunctions.schema.types.v1.DateTime;
import com.market.sdk.reflect.ReflectUtilsForMiui;
import com.xiaomi.mirror.RemoteDeviceInfo;
import java.util.Objects;
import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: com.google.android.appfunctions:appfunctions-schema@@1.0.7 */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000.\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\t\n\u0002\b\u0016\n\u0002\u0010\b\n\u0002\b\u0002\b\u0007\u0018\u0000 #2\u00020\u0001:\u0001#BW\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0003\u0012\u0006\u0010\u0005\u001a\u00020\u0003\u0012\n\b\u0002\u0010\u0006\u001a\u0004\u0018\u00010\u0007\u0012\u0006\u0010\b\u001a\u00020\t\u0012\n\b\u0002\u0010\n\u001a\u0004\u0018\u00010\u000b\u0012\n\b\u0002\u0010\f\u001a\u0004\u0018\u00010\u0003\u0012\n\b\u0002\u0010\r\u001a\u0004\u0018\u00010\u0003¢\u0006\u0004\b\u000e\u0010\u000fJ\u0013\u0010\u001f\u001a\u00020\u00072\b\u0010 \u001a\u0004\u0018\u00010\u0001H\u0096\u0002J\b\u0010!\u001a\u00020\"H\u0016R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0010\u0010\u0011R\u0011\u0010\u0004\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0012\u0010\u0011R\u001c\u0010\u0005\u001a\u00020\u00038\u0006X\u0087\u0004¢\u0006\u000e\n\u0000\u0012\u0004\b\u0013\u0010\u0014\u001a\u0004\b\u0015\u0010\u0011R\u0015\u0010\u0006\u001a\u0004\u0018\u00010\u0007¢\u0006\n\n\u0002\u0010\u0017\u001a\u0004\b\u0006\u0010\u0016R\u0011\u0010\b\u001a\u00020\t¢\u0006\b\n\u0000\u001a\u0004\b\u0018\u0010\u0019R\u0015\u0010\n\u001a\u0004\u0018\u00010\u000b¢\u0006\n\n\u0002\u0010\u001c\u001a\u0004\b\u001a\u0010\u001bR\u0013\u0010\f\u001a\u0004\u0018\u00010\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u001d\u0010\u0011R\u0013\u0010\r\u001a\u0004\u0018\u00010\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u001e\u0010\u0011¨\u0006$"}, d2 = {"Lcom/google/android/appfunctions/schema/phone/v1/CallRecord;", "", "id", "", "contactId", "callType", "isVideoCall", "", "startTime", "Lcom/google/android/appfunctions/schema/types/v1/DateTime;", "durationMillis", "", "sipAddress", "phoneNumber", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/Boolean;Lcom/google/android/appfunctions/schema/types/v1/DateTime;Ljava/lang/Long;Ljava/lang/String;Ljava/lang/String;)V", "getId", "()Ljava/lang/String;", "getContactId", "getCallType$annotations", "()V", "getCallType", "()Ljava/lang/Boolean;", "Ljava/lang/Boolean;", "getStartTime", "()Lcom/google/android/appfunctions/schema/types/v1/DateTime;", "getDurationMillis", "()Ljava/lang/Long;", "Ljava/lang/Long;", "getSipAddress", "getPhoneNumber", "equals", RemoteDeviceInfo.MANUFACTURER_OTHER, "hashCode", "", "Companion", "java.com.google.android.libraries.llm.appfunctions.appfunctions_schema2.src.com.google.android.appfunctions.schema.phone.v1_v1"}, k = 1, mv = {2, 1, 0}, xi = 48)
public final class CallRecord {
    public static final String ASSIST_FOREGROUND_TYPE = "com.google.android.appfunctions.schema.phone.v1.CallRecordForeground";
    public static final String CALL_TYPE_BLOCKED = "BLOCKED";
    public static final String CALL_TYPE_INCOMING = "INCOMING";
    public static final String CALL_TYPE_MISSED = "MISSED";
    public static final String CALL_TYPE_OUTGOING = "OUTGOING";
    public static final String CALL_TYPE_REJECTED = "REJECTED";
    public static final String CALL_TYPE_VOICEMAIL = "VOICEMAIL";
    private final String callType;
    private final String contactId;
    private final Long durationMillis;
    private final String id;
    private final Boolean isVideoCall;
    private final String phoneNumber;
    private final String sipAddress;
    private final DateTime startTime;

    public CallRecord(String id, String contactId, String callType, Boolean bool, DateTime startTime, Long l, String str, String str2) {
        Intrinsics.checkNotNullParameter(id, "id");
        Intrinsics.checkNotNullParameter(contactId, "contactId");
        Intrinsics.checkNotNullParameter(callType, "callType");
        Intrinsics.checkNotNullParameter(startTime, "startTime");
        this.id = id;
        this.contactId = contactId;
        this.callType = callType;
        this.isVideoCall = bool;
        this.startTime = startTime;
        this.durationMillis = l;
        this.sipAddress = str;
        this.phoneNumber = str2;
    }

    public /* synthetic */ CallRecord(String str, String str2, String str3, Boolean bool, DateTime dateTime, Long l, String str4, String str5, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(str, str2, str3, (i & 8) != 0 ? null : bool, dateTime, (i & 32) != 0 ? null : l, (i & 64) != 0 ? null : str4, (i & 128) != 0 ? null : str5);
    }

    public boolean equals(Object other) {
        if (!(other instanceof CallRecord)) {
            return false;
        }
        CallRecord callRecord = (CallRecord) other;
        return Intrinsics.areEqual(this.id, callRecord.id) && Intrinsics.areEqual(this.contactId, callRecord.contactId) && Intrinsics.areEqual(this.callType, callRecord.callType) && Intrinsics.areEqual(this.isVideoCall, callRecord.isVideoCall) && Intrinsics.areEqual(this.startTime, callRecord.startTime) && Intrinsics.areEqual(this.durationMillis, callRecord.durationMillis) && Intrinsics.areEqual(this.sipAddress, callRecord.sipAddress) && Intrinsics.areEqual(this.phoneNumber, callRecord.phoneNumber);
    }

    public final String getCallType() {
        return this.callType;
    }

    public final String getContactId() {
        return this.contactId;
    }

    public final Long getDurationMillis() {
        return this.durationMillis;
    }

    public final String getId() {
        return this.id;
    }

    public final String getPhoneNumber() {
        return this.phoneNumber;
    }

    public final String getSipAddress() {
        return this.sipAddress;
    }

    public final DateTime getStartTime() {
        return this.startTime;
    }

    public int hashCode() {
        return Objects.hash(this.id, this.contactId, this.callType, this.isVideoCall, this.startTime, this.durationMillis, this.sipAddress, this.phoneNumber);
    }

    /* JADX INFO: renamed from: isVideoCall, reason: from getter */
    public final Boolean getIsVideoCall() {
        return this.isVideoCall;
    }
}
