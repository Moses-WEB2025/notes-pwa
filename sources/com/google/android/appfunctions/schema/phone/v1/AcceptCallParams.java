package com.google.android.appfunctions.schema.phone.v1;

import com.market.sdk.reflect.ReflectUtilsForMiui;
import com.xiaomi.mirror.RemoteDeviceInfo;
import java.util.Objects;
import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: com.google.android.appfunctions:appfunctions-schema@@1.0.7 */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000b\n\u0002\b\n\n\u0002\u0010\b\n\u0000\b\u0007\u0018\u00002\u00020\u0001B+\u0012\n\b\u0002\u0010\u0002\u001a\u0004\u0018\u00010\u0003\u0012\n\b\u0002\u0010\u0004\u001a\u0004\u0018\u00010\u0003\u0012\n\b\u0002\u0010\u0005\u001a\u0004\u0018\u00010\u0003¢\u0006\u0004\b\u0006\u0010\u0007J\u0013\u0010\u000b\u001a\u00020\u00032\b\u0010\f\u001a\u0004\u0018\u00010\u0001H\u0096\u0002J\b\u0010\r\u001a\u00020\u000eH\u0016R\u0015\u0010\u0002\u001a\u0004\u0018\u00010\u0003¢\u0006\n\n\u0002\u0010\n\u001a\u0004\b\b\u0010\tR\u0015\u0010\u0004\u001a\u0004\u0018\u00010\u0003¢\u0006\n\n\u0002\u0010\n\u001a\u0004\b\u0004\u0010\tR\u0015\u0010\u0005\u001a\u0004\u0018\u00010\u0003¢\u0006\n\n\u0002\u0010\n\u001a\u0004\b\u0005\u0010\t¨\u0006\u000f"}, d2 = {"Lcom/google/android/appfunctions/schema/phone/v1/AcceptCallParams;", "", "shouldRecord", "", "isSpeakerOn", "isVideoCall", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "(Ljava/lang/Boolean;Ljava/lang/Boolean;Ljava/lang/Boolean;)V", "getShouldRecord", "()Ljava/lang/Boolean;", "Ljava/lang/Boolean;", "equals", RemoteDeviceInfo.MANUFACTURER_OTHER, "hashCode", "", "java.com.google.android.libraries.llm.appfunctions.appfunctions_schema2.src.com.google.android.appfunctions.schema.phone.v1_v1"}, k = 1, mv = {2, 1, 0}, xi = 48)
public final class AcceptCallParams {
    private final Boolean isSpeakerOn;
    private final Boolean isVideoCall;
    private final Boolean shouldRecord;

    public AcceptCallParams() {
        this(null, null, null, 7, null);
    }

    public AcceptCallParams(Boolean bool, Boolean bool2, Boolean bool3) {
        this.shouldRecord = bool;
        this.isSpeakerOn = bool2;
        this.isVideoCall = bool3;
    }

    public /* synthetic */ AcceptCallParams(Boolean bool, Boolean bool2, Boolean bool3, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(1 == (i & 1) ? null : bool, (i & 2) != 0 ? null : bool2, (i & 4) != 0 ? null : bool3);
    }

    public boolean equals(Object other) {
        if (!(other instanceof AcceptCallParams)) {
            return false;
        }
        AcceptCallParams acceptCallParams = (AcceptCallParams) other;
        return Intrinsics.areEqual(this.shouldRecord, acceptCallParams.shouldRecord) && Intrinsics.areEqual(this.isSpeakerOn, acceptCallParams.isSpeakerOn) && Intrinsics.areEqual(this.isVideoCall, acceptCallParams.isVideoCall);
    }

    public final Boolean getShouldRecord() {
        return this.shouldRecord;
    }

    public int hashCode() {
        return Objects.hash(this.shouldRecord, this.isSpeakerOn, this.isVideoCall);
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
