package com.google.android.appfunctions.schema.devicestate.v1;

import com.market.sdk.reflect.ReflectUtilsForMiui;
import com.xiaomi.mirror.RemoteDeviceInfo;
import java.util.Objects;
import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: com.google.android.appfunctions:appfunctions-schema@@1.0.7 */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000(\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0014\n\u0002\u0010\b\n\u0000\b\u0007\u0018\u00002\u00020\u0001BS\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0003\u0012\n\b\u0002\u0010\u0005\u001a\u0004\u0018\u00010\u0006\u0012\n\b\u0002\u0010\u0007\u001a\u0004\u0018\u00010\u0003\u0012\n\b\u0002\u0010\b\u001a\u0004\u0018\u00010\t\u0012\n\b\u0002\u0010\n\u001a\u0004\u0018\u00010\u0003\u0012\n\b\u0002\u0010\u000b\u001a\u0004\u0018\u00010\u0003¢\u0006\u0004\b\f\u0010\rJ\u0013\u0010\u001b\u001a\u00020\t2\b\u0010\u001c\u001a\u0004\u0018\u00010\u0001H\u0096\u0002J\b\u0010\u001d\u001a\u00020\u001eH\u0016R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u000e\u0010\u000fR\u0011\u0010\u0004\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0010\u0010\u000fR\u0013\u0010\u0005\u001a\u0004\u0018\u00010\u0006¢\u0006\b\n\u0000\u001a\u0004\b\u0011\u0010\u0012R\u001e\u0010\u0007\u001a\u0004\u0018\u00010\u00038\u0006X\u0087\u0004¢\u0006\u000e\n\u0000\u0012\u0004\b\u0013\u0010\u0014\u001a\u0004\b\u0015\u0010\u000fR\u0015\u0010\b\u001a\u0004\u0018\u00010\t¢\u0006\n\n\u0002\u0010\u0018\u001a\u0004\b\u0016\u0010\u0017R\u0013\u0010\n\u001a\u0004\u0018\u00010\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0019\u0010\u000fR\u0013\u0010\u000b\u001a\u0004\u0018\u00010\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u001a\u0010\u000f¨\u0006\u001f"}, d2 = {"Lcom/google/android/appfunctions/schema/devicestate/v1/DeviceStateItemMetadata;", "", "key", "", "purpose", "name", "Lcom/google/android/appfunctions/schema/devicestate/v1/LocalizedString;", "sensitivity", "writable", "", "possibleValues", "hintText", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "(Ljava/lang/String;Ljava/lang/String;Lcom/google/android/appfunctions/schema/devicestate/v1/LocalizedString;Ljava/lang/String;Ljava/lang/Boolean;Ljava/lang/String;Ljava/lang/String;)V", "getKey", "()Ljava/lang/String;", "getPurpose", "getName", "()Lcom/google/android/appfunctions/schema/devicestate/v1/LocalizedString;", "getSensitivity$annotations", "()V", "getSensitivity", "getWritable", "()Ljava/lang/Boolean;", "Ljava/lang/Boolean;", "getPossibleValues", "getHintText", "equals", RemoteDeviceInfo.MANUFACTURER_OTHER, "hashCode", "", "java.com.google.android.libraries.llm.appfunctions.appfunctions_schema2.src.com.google.android.appfunctions.schema.devicestate.v1_v1"}, k = 1, mv = {2, 1, 0}, xi = 48)
public final class DeviceStateItemMetadata {
    private final String hintText;
    private final String key;
    private final LocalizedString name;
    private final String possibleValues;
    private final String purpose;
    private final String sensitivity;
    private final Boolean writable;

    public DeviceStateItemMetadata(String key, String purpose, LocalizedString localizedString, String str, Boolean bool, String str2, String str3) {
        Intrinsics.checkNotNullParameter(key, "key");
        Intrinsics.checkNotNullParameter(purpose, "purpose");
        this.key = key;
        this.purpose = purpose;
        this.name = localizedString;
        this.sensitivity = str;
        this.writable = bool;
        this.possibleValues = str2;
        this.hintText = str3;
    }

    public /* synthetic */ DeviceStateItemMetadata(String str, String str2, LocalizedString localizedString, String str3, Boolean bool, String str4, String str5, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(str, str2, (i & 4) != 0 ? null : localizedString, (i & 8) != 0 ? null : str3, (i & 16) != 0 ? null : bool, (i & 32) != 0 ? null : str4, (i & 64) != 0 ? null : str5);
    }

    public boolean equals(Object other) {
        if (!(other instanceof DeviceStateItemMetadata)) {
            return false;
        }
        DeviceStateItemMetadata deviceStateItemMetadata = (DeviceStateItemMetadata) other;
        return Intrinsics.areEqual(this.key, deviceStateItemMetadata.key) && Intrinsics.areEqual(this.purpose, deviceStateItemMetadata.purpose) && Intrinsics.areEqual(this.name, deviceStateItemMetadata.name) && Intrinsics.areEqual(this.sensitivity, deviceStateItemMetadata.sensitivity) && Intrinsics.areEqual(this.writable, deviceStateItemMetadata.writable) && Intrinsics.areEqual(this.possibleValues, deviceStateItemMetadata.possibleValues) && Intrinsics.areEqual(this.hintText, deviceStateItemMetadata.hintText);
    }

    public final String getHintText() {
        return this.hintText;
    }

    public final String getKey() {
        return this.key;
    }

    public final LocalizedString getName() {
        return this.name;
    }

    public final String getPossibleValues() {
        return this.possibleValues;
    }

    public final String getPurpose() {
        return this.purpose;
    }

    public final String getSensitivity() {
        return this.sensitivity;
    }

    public final Boolean getWritable() {
        return this.writable;
    }

    public int hashCode() {
        return Objects.hash(this.key, this.purpose, this.name, this.sensitivity, this.writable, this.possibleValues, this.hintText);
    }
}
