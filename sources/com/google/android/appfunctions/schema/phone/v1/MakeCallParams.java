package com.google.android.appfunctions.schema.phone.v1;

import com.market.sdk.reflect.ReflectUtilsForMiui;
import com.xiaomi.mirror.RemoteDeviceInfo;
import java.util.Objects;
import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: com.google.android.appfunctions:appfunctions-schema@@1.0.7 */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000 \n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0002\b\u0010\n\u0002\u0010\b\n\u0000\b\u0007\u0018\u00002\u00020\u0001BK\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\n\b\u0002\u0010\u0004\u001a\u0004\u0018\u00010\u0003\u0012\n\b\u0002\u0010\u0005\u001a\u0004\u0018\u00010\u0003\u0012\n\b\u0002\u0010\u0006\u001a\u0004\u0018\u00010\u0007\u0012\n\b\u0002\u0010\b\u001a\u0004\u0018\u00010\u0007\u0012\n\b\u0002\u0010\t\u001a\u0004\u0018\u00010\u0007¢\u0006\u0004\b\n\u0010\u000bJ\u0013\u0010\u0015\u001a\u00020\u00072\b\u0010\u0016\u001a\u0004\u0018\u00010\u0001H\u0096\u0002J\b\u0010\u0017\u001a\u00020\u0018H\u0016R\u001c\u0010\u0002\u001a\u00020\u00038\u0006X\u0087\u0004¢\u0006\u000e\n\u0000\u0012\u0004\b\f\u0010\r\u001a\u0004\b\u000e\u0010\u000fR\u0013\u0010\u0004\u001a\u0004\u0018\u00010\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0010\u0010\u000fR\u0013\u0010\u0005\u001a\u0004\u0018\u00010\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0011\u0010\u000fR\u0015\u0010\u0006\u001a\u0004\u0018\u00010\u0007¢\u0006\n\n\u0002\u0010\u0014\u001a\u0004\b\u0012\u0010\u0013R\u0015\u0010\b\u001a\u0004\u0018\u00010\u0007¢\u0006\n\n\u0002\u0010\u0014\u001a\u0004\b\b\u0010\u0013R\u0015\u0010\t\u001a\u0004\u0018\u00010\u0007¢\u0006\n\n\u0002\u0010\u0014\u001a\u0004\b\t\u0010\u0013¨\u0006\u0019"}, d2 = {"Lcom/google/android/appfunctions/schema/phone/v1/MakeCallParams;", "", "type", "", "phoneNumber", "contactId", "shouldRecord", "", "isSpeakerOn", "isVideoCall", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/Boolean;Ljava/lang/Boolean;Ljava/lang/Boolean;)V", "getType$annotations", "()V", "getType", "()Ljava/lang/String;", "getPhoneNumber", "getContactId", "getShouldRecord", "()Ljava/lang/Boolean;", "Ljava/lang/Boolean;", "equals", RemoteDeviceInfo.MANUFACTURER_OTHER, "hashCode", "", "java.com.google.android.libraries.llm.appfunctions.appfunctions_schema2.src.com.google.android.appfunctions.schema.phone.v1_v1"}, k = 1, mv = {2, 1, 0}, xi = 48)
public final class MakeCallParams {
    private final String contactId;
    private final Boolean isSpeakerOn;
    private final Boolean isVideoCall;
    private final String phoneNumber;
    private final Boolean shouldRecord;
    private final String type;

    public MakeCallParams(String type, String str, String str2, Boolean bool, Boolean bool2, Boolean bool3) {
        Intrinsics.checkNotNullParameter(type, "type");
        this.type = type;
        this.phoneNumber = str;
        this.contactId = str2;
        this.shouldRecord = bool;
        this.isSpeakerOn = bool2;
        this.isVideoCall = bool3;
    }

    public /* synthetic */ MakeCallParams(String str, String str2, String str3, Boolean bool, Boolean bool2, Boolean bool3, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(str, (i & 2) != 0 ? null : str2, (i & 4) != 0 ? null : str3, (i & 8) != 0 ? null : bool, (i & 16) != 0 ? null : bool2, (i & 32) != 0 ? null : bool3);
    }

    public boolean equals(Object other) {
        if (!(other instanceof MakeCallParams)) {
            return false;
        }
        MakeCallParams makeCallParams = (MakeCallParams) other;
        return Intrinsics.areEqual(this.type, makeCallParams.type) && Intrinsics.areEqual(this.phoneNumber, makeCallParams.phoneNumber) && Intrinsics.areEqual(this.contactId, makeCallParams.contactId) && Intrinsics.areEqual(this.shouldRecord, makeCallParams.shouldRecord) && Intrinsics.areEqual(this.isSpeakerOn, makeCallParams.isSpeakerOn) && Intrinsics.areEqual(this.isVideoCall, makeCallParams.isVideoCall);
    }

    public final String getContactId() {
        return this.contactId;
    }

    public final String getPhoneNumber() {
        return this.phoneNumber;
    }

    public final Boolean getShouldRecord() {
        return this.shouldRecord;
    }

    public final String getType() {
        return this.type;
    }

    public int hashCode() {
        return Objects.hash(this.type, this.phoneNumber, this.contactId, this.shouldRecord, this.isSpeakerOn, this.isVideoCall);
    }

    /* JADX INFO: renamed from: isSpeakerOn, reason: from getter */
    public final Boolean getIsSpeakerOn() {
        return this.isSpeakerOn;
    }

    /* JADX INFO: renamed from: isVideoCall, reason: from getter */
    public final Boolean getIsVideoCall() {
        return this.isVideoCall;
    }
}
