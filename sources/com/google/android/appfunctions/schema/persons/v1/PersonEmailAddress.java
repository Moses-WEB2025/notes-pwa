package com.google.android.appfunctions.schema.persons.v1;

import com.market.sdk.reflect.ReflectUtilsForMiui;
import com.xiaomi.mirror.RemoteDeviceInfo;
import java.util.Objects;
import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: com.google.android.appfunctions:appfunctions-schema@@1.0.7 */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000\"\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u000b\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0002\b\u0007\u0018\u0000 \u00132\u00020\u0001:\u0001\u0013B#\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0003\u0012\n\b\u0002\u0010\u0005\u001a\u0004\u0018\u00010\u0003¢\u0006\u0004\b\u0006\u0010\u0007J\u0013\u0010\u000e\u001a\u00020\u000f2\b\u0010\u0010\u001a\u0004\u0018\u00010\u0001H\u0096\u0002J\b\u0010\u0011\u001a\u00020\u0012H\u0016R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\b\u0010\tR\u001c\u0010\u0004\u001a\u00020\u00038\u0006X\u0087\u0004¢\u0006\u000e\n\u0000\u0012\u0004\b\n\u0010\u000b\u001a\u0004\b\f\u0010\tR\u0013\u0010\u0005\u001a\u0004\u0018\u00010\u0003¢\u0006\b\n\u0000\u001a\u0004\b\r\u0010\t¨\u0006\u0014"}, d2 = {"Lcom/google/android/appfunctions/schema/persons/v1/PersonEmailAddress;", "", "emailAddress", "", "type", "customType", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)V", "getEmailAddress", "()Ljava/lang/String;", "getType$annotations", "()V", "getType", "getCustomType", "equals", "", RemoteDeviceInfo.MANUFACTURER_OTHER, "hashCode", "", "Companion", "java.com.google.android.libraries.llm.appfunctions.appfunctions_schema2.src.com.google.android.appfunctions.schema.persons.v1_v1"}, k = 1, mv = {2, 1, 0}, xi = 48)
public final class PersonEmailAddress {
    public static final String EMAIL_ADDRESS_TYPE_CUSTOM = "CUSTOM";
    public static final String EMAIL_ADDRESS_TYPE_HOME = "HOME";
    public static final String EMAIL_ADDRESS_TYPE_OTHER = "OTHER";
    public static final String EMAIL_ADDRESS_TYPE_WORK = "WORK";
    private final String customType;
    private final String emailAddress;
    private final String type;

    public PersonEmailAddress(String emailAddress, String type, String str) {
        Intrinsics.checkNotNullParameter(emailAddress, "emailAddress");
        Intrinsics.checkNotNullParameter(type, "type");
        this.emailAddress = emailAddress;
        this.type = type;
        this.customType = str;
    }

    public /* synthetic */ PersonEmailAddress(String str, String str2, String str3, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(str, str2, (i & 4) != 0 ? null : str3);
    }

    public boolean equals(Object other) {
        if (!(other instanceof PersonEmailAddress)) {
            return false;
        }
        PersonEmailAddress personEmailAddress = (PersonEmailAddress) other;
        return Intrinsics.areEqual(this.emailAddress, personEmailAddress.emailAddress) && Intrinsics.areEqual(this.type, personEmailAddress.type) && Intrinsics.areEqual(this.customType, personEmailAddress.customType);
    }

    public final String getCustomType() {
        return this.customType;
    }

    public final String getEmailAddress() {
        return this.emailAddress;
    }

    public final String getType() {
        return this.type;
    }

    public int hashCode() {
        return Objects.hash(this.emailAddress, this.type, this.customType);
    }
}
