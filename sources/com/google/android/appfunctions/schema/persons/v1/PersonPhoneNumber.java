package com.google.android.appfunctions.schema.persons.v1;

import com.market.sdk.reflect.ReflectUtilsForMiui;
import com.xiaomi.mirror.RemoteDeviceInfo;
import java.util.Objects;
import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: com.google.android.appfunctions:appfunctions-schema@@1.0.7 */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000\"\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u000b\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0002\b\u0007\u0018\u0000 \u00132\u00020\u0001:\u0001\u0013B#\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0003\u0012\n\b\u0002\u0010\u0005\u001a\u0004\u0018\u00010\u0003¢\u0006\u0004\b\u0006\u0010\u0007J\u0013\u0010\u000e\u001a\u00020\u000f2\b\u0010\u0010\u001a\u0004\u0018\u00010\u0001H\u0096\u0002J\b\u0010\u0011\u001a\u00020\u0012H\u0016R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\b\u0010\tR\u001c\u0010\u0004\u001a\u00020\u00038\u0006X\u0087\u0004¢\u0006\u000e\n\u0000\u0012\u0004\b\n\u0010\u000b\u001a\u0004\b\f\u0010\tR\u0013\u0010\u0005\u001a\u0004\u0018\u00010\u0003¢\u0006\b\n\u0000\u001a\u0004\b\r\u0010\t¨\u0006\u0014"}, d2 = {"Lcom/google/android/appfunctions/schema/persons/v1/PersonPhoneNumber;", "", "phoneNumber", "", "type", "customType", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)V", "getPhoneNumber", "()Ljava/lang/String;", "getType$annotations", "()V", "getType", "getCustomType", "equals", "", RemoteDeviceInfo.MANUFACTURER_OTHER, "hashCode", "", "Companion", "java.com.google.android.libraries.llm.appfunctions.appfunctions_schema2.src.com.google.android.appfunctions.schema.persons.v1_v1"}, k = 1, mv = {2, 1, 0}, xi = 48)
public final class PersonPhoneNumber {
    public static final String PHONE_NUMBER_TYPE_CUSTOM = "CUSTOM";
    public static final String PHONE_NUMBER_TYPE_HOME = "HOME";
    public static final String PHONE_NUMBER_TYPE_HOME_FAX = "HOME_FAX";
    public static final String PHONE_NUMBER_TYPE_MAIN = "MAIN";
    public static final String PHONE_NUMBER_TYPE_MOBILE = "MOBILE";
    public static final String PHONE_NUMBER_TYPE_OTHER = "OTHER";
    public static final String PHONE_NUMBER_TYPE_PAGER = "PAGER";
    public static final String PHONE_NUMBER_TYPE_WORK = "WORK";
    public static final String PHONE_NUMBER_TYPE_WORK_FAX = "WORK_FAX";
    private final String customType;
    private final String phoneNumber;
    private final String type;

    public PersonPhoneNumber(String phoneNumber, String type, String str) {
        Intrinsics.checkNotNullParameter(phoneNumber, "phoneNumber");
        Intrinsics.checkNotNullParameter(type, "type");
        this.phoneNumber = phoneNumber;
        this.type = type;
        this.customType = str;
    }

    public /* synthetic */ PersonPhoneNumber(String str, String str2, String str3, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(str, str2, (i & 4) != 0 ? null : str3);
    }

    public boolean equals(Object other) {
        if (!(other instanceof PersonPhoneNumber)) {
            return false;
        }
        PersonPhoneNumber personPhoneNumber = (PersonPhoneNumber) other;
        return Intrinsics.areEqual(this.phoneNumber, personPhoneNumber.phoneNumber) && Intrinsics.areEqual(this.type, personPhoneNumber.type) && Intrinsics.areEqual(this.customType, personPhoneNumber.customType);
    }

    public final String getCustomType() {
        return this.customType;
    }

    public final String getPhoneNumber() {
        return this.phoneNumber;
    }

    public final String getType() {
        return this.type;
    }

    public int hashCode() {
        return Objects.hash(this.phoneNumber, this.type, this.customType);
    }
}
