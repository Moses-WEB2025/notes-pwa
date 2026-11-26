package com.google.android.appfunctions.schema.phone.v1;

import com.market.sdk.reflect.ReflectUtilsForMiui;
import com.xiaomi.mirror.RemoteDeviceInfo;
import java.util.Objects;
import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: com.google.android.appfunctions:appfunctions-schema@@1.0.7 */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000 \n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u000b\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\b\n\u0000\b\u0007\u0018\u00002\u00020\u0001B'\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\n\b\u0002\u0010\u0004\u001a\u0004\u0018\u00010\u0003\u0012\n\b\u0002\u0010\u0005\u001a\u0004\u0018\u00010\u0003¢\u0006\u0004\b\u0006\u0010\u0007J\u0013\u0010\u000e\u001a\u00020\u000f2\b\u0010\u0010\u001a\u0004\u0018\u00010\u0001H\u0096\u0002J\b\u0010\u0011\u001a\u00020\u0012H\u0016R\u001c\u0010\u0002\u001a\u00020\u00038\u0006X\u0087\u0004¢\u0006\u000e\n\u0000\u0012\u0004\b\b\u0010\t\u001a\u0004\b\n\u0010\u000bR\u0013\u0010\u0004\u001a\u0004\u0018\u00010\u0003¢\u0006\b\n\u0000\u001a\u0004\b\f\u0010\u000bR\u0013\u0010\u0005\u001a\u0004\u0018\u00010\u0003¢\u0006\b\n\u0000\u001a\u0004\b\r\u0010\u000b¨\u0006\u0013"}, d2 = {"Lcom/google/android/appfunctions/schema/phone/v1/AddToBlocklistParams;", "", "type", "", "phoneNumber", "contactId", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)V", "getType$annotations", "()V", "getType", "()Ljava/lang/String;", "getPhoneNumber", "getContactId", "equals", "", RemoteDeviceInfo.MANUFACTURER_OTHER, "hashCode", "", "java.com.google.android.libraries.llm.appfunctions.appfunctions_schema2.src.com.google.android.appfunctions.schema.phone.v1_v1"}, k = 1, mv = {2, 1, 0}, xi = 48)
public final class AddToBlocklistParams {
    private final String contactId;
    private final String phoneNumber;
    private final String type;

    public AddToBlocklistParams(String type, String str, String str2) {
        Intrinsics.checkNotNullParameter(type, "type");
        this.type = type;
        this.phoneNumber = str;
        this.contactId = str2;
    }

    public /* synthetic */ AddToBlocklistParams(String str, String str2, String str3, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(str, (i & 2) != 0 ? null : str2, (i & 4) != 0 ? null : str3);
    }

    public boolean equals(Object other) {
        if (!(other instanceof AddToBlocklistParams)) {
            return false;
        }
        AddToBlocklistParams addToBlocklistParams = (AddToBlocklistParams) other;
        return Intrinsics.areEqual(this.type, addToBlocklistParams.type) && Intrinsics.areEqual(this.phoneNumber, addToBlocklistParams.phoneNumber) && Intrinsics.areEqual(this.contactId, addToBlocklistParams.contactId);
    }

    public final String getContactId() {
        return this.contactId;
    }

    public final String getPhoneNumber() {
        return this.phoneNumber;
    }

    public final String getType() {
        return this.type;
    }

    public int hashCode() {
        return Objects.hash(this.type, this.phoneNumber, this.contactId);
    }
}
