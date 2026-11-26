package com.google.android.appfunctions.schema.phone.v1;

import com.market.sdk.reflect.ReflectUtilsForMiui;
import com.xiaomi.mirror.RemoteDeviceInfo;
import java.util.Objects;
import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: com.google.android.appfunctions:appfunctions-schema@@1.0.7 */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000 \n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\u000b\n\u0002\b\t\n\u0002\u0010\b\n\u0002\b\u0002\b\u0007\u0018\u0000 \u00102\u00020\u0001:\u0001\u0010B\u001b\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\n\b\u0002\u0010\u0004\u001a\u0004\u0018\u00010\u0005¢\u0006\u0004\b\u0006\u0010\u0007J\u0013\u0010\f\u001a\u00020\u00052\b\u0010\r\u001a\u0004\u0018\u00010\u0001H\u0096\u0002J\b\u0010\u000e\u001a\u00020\u000fH\u0016R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\b\u0010\tR\u0015\u0010\u0004\u001a\u0004\u0018\u00010\u0005¢\u0006\n\n\u0002\u0010\u000b\u001a\u0004\b\u0004\u0010\n¨\u0006\u0011"}, d2 = {"Lcom/google/android/appfunctions/schema/phone/v1/Call;", "", "contactId", "", "isVideoCall", "", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "(Ljava/lang/String;Ljava/lang/Boolean;)V", "getContactId", "()Ljava/lang/String;", "()Ljava/lang/Boolean;", "Ljava/lang/Boolean;", "equals", RemoteDeviceInfo.MANUFACTURER_OTHER, "hashCode", "", "Companion", "java.com.google.android.libraries.llm.appfunctions.appfunctions_schema2.src.com.google.android.appfunctions.schema.phone.v1_v1"}, k = 1, mv = {2, 1, 0}, xi = 48)
public final class Call {
    public static final String ASSIST_FOREGROUND_TYPE = "com.google.android.appfunctions.schema.phone.v1.CallForeground";
    public static final String CONTACT_TYPE_PERSON = "PERSON";
    public static final String CONTACT_TYPE_PHONE_NUMBER = "PHONE_NUMBER";
    private final String contactId;
    private final Boolean isVideoCall;

    public Call(String contactId, Boolean bool) {
        Intrinsics.checkNotNullParameter(contactId, "contactId");
        this.contactId = contactId;
        this.isVideoCall = bool;
    }

    public /* synthetic */ Call(String str, Boolean bool, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(str, (i & 2) != 0 ? null : bool);
    }

    public boolean equals(Object other) {
        if (!(other instanceof Call)) {
            return false;
        }
        Call call = (Call) other;
        return Intrinsics.areEqual(this.contactId, call.contactId) && Intrinsics.areEqual(this.isVideoCall, call.isVideoCall);
    }

    public final String getContactId() {
        return this.contactId;
    }

    public int hashCode() {
        return Objects.hash(this.contactId, this.isVideoCall);
    }

    /* JADX INFO: renamed from: isVideoCall, reason: from getter */
    public final Boolean getIsVideoCall() {
        return this.isVideoCall;
    }
}
