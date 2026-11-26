package com.google.android.appfunctions.schema.persons.v1;

import android.net.Uri;
import com.google.android.appfunctions.schema.types.v1.SetField;
import com.market.sdk.reflect.ReflectUtilsForMiui;
import com.xiaomi.mirror.RemoteDeviceInfo;
import java.util.List;
import java.util.Objects;
import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: com.google.android.appfunctions:appfunctions-schema@@1.0.7 */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000>\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\r\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\b\n\u0000\b\u0007\u0018\u00002\u00020\u0001B¡\u0001\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0010\b\u0002\u0010\u0004\u001a\n\u0012\u0004\u0012\u00020\u0003\u0018\u00010\u0005\u0012\u0012\b\u0002\u0010\u0006\u001a\f\u0012\u0006\u0012\u0004\u0018\u00010\u0003\u0018\u00010\u0005\u0012\u0012\b\u0002\u0010\u0007\u001a\f\u0012\u0006\u0012\u0004\u0018\u00010\u0003\u0018\u00010\u0005\u0012\u0012\b\u0002\u0010\b\u001a\f\u0012\u0006\u0012\u0004\u0018\u00010\u0003\u0018\u00010\u0005\u0012\u0012\b\u0002\u0010\t\u001a\f\u0012\u0006\u0012\u0004\u0018\u00010\n\u0018\u00010\u0005\u0012\u0016\b\u0002\u0010\u000b\u001a\u0010\u0012\n\u0012\b\u0012\u0004\u0012\u00020\r0\f\u0018\u00010\u0005\u0012\u0016\b\u0002\u0010\u000e\u001a\u0010\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u000f0\f\u0018\u00010\u0005¢\u0006\u0004\b\u0010\u0010\u0011J\u0013\u0010\u001c\u001a\u00020\u001d2\b\u0010\u001e\u001a\u0004\u0018\u00010\u0001H\u0096\u0002J\b\u0010\u001f\u001a\u00020 H\u0016R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0012\u0010\u0013R\u0019\u0010\u0004\u001a\n\u0012\u0004\u0012\u00020\u0003\u0018\u00010\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u0014\u0010\u0015R\u001b\u0010\u0006\u001a\f\u0012\u0006\u0012\u0004\u0018\u00010\u0003\u0018\u00010\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u0016\u0010\u0015R\u001b\u0010\u0007\u001a\f\u0012\u0006\u0012\u0004\u0018\u00010\u0003\u0018\u00010\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u0017\u0010\u0015R\u001b\u0010\b\u001a\f\u0012\u0006\u0012\u0004\u0018\u00010\u0003\u0018\u00010\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u0018\u0010\u0015R\u001b\u0010\t\u001a\f\u0012\u0006\u0012\u0004\u0018\u00010\n\u0018\u00010\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u0019\u0010\u0015R\u001f\u0010\u000b\u001a\u0010\u0012\n\u0012\b\u0012\u0004\u0012\u00020\r0\f\u0018\u00010\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u001a\u0010\u0015R\u001f\u0010\u000e\u001a\u0010\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u000f0\f\u0018\u00010\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u001b\u0010\u0015¨\u0006!"}, d2 = {"Lcom/google/android/appfunctions/schema/persons/v1/UpdatePersonParams;", "", "personId", "", "displayName", "Lcom/google/android/appfunctions/schema/types/v1/SetField;", "givenName", "middleName", "familyName", "externalUri", "Landroid/net/Uri;", "phoneNumbers", "", "Lcom/google/android/appfunctions/schema/persons/v1/PersonPhoneNumber;", "emailAddresses", "Lcom/google/android/appfunctions/schema/persons/v1/PersonEmailAddress;", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "(Ljava/lang/String;Lcom/google/android/appfunctions/schema/types/v1/SetField;Lcom/google/android/appfunctions/schema/types/v1/SetField;Lcom/google/android/appfunctions/schema/types/v1/SetField;Lcom/google/android/appfunctions/schema/types/v1/SetField;Lcom/google/android/appfunctions/schema/types/v1/SetField;Lcom/google/android/appfunctions/schema/types/v1/SetField;Lcom/google/android/appfunctions/schema/types/v1/SetField;)V", "getPersonId", "()Ljava/lang/String;", "getDisplayName", "()Lcom/google/android/appfunctions/schema/types/v1/SetField;", "getGivenName", "getMiddleName", "getFamilyName", "getExternalUri", "getPhoneNumbers", "getEmailAddresses", "equals", "", RemoteDeviceInfo.MANUFACTURER_OTHER, "hashCode", "", "java.com.google.android.libraries.llm.appfunctions.appfunctions_schema2.src.com.google.android.appfunctions.schema.persons.v1_v1"}, k = 1, mv = {2, 1, 0}, xi = 48)
public final class UpdatePersonParams {
    private final SetField<String> displayName;
    private final SetField<List<PersonEmailAddress>> emailAddresses;
    private final SetField<Uri> externalUri;
    private final SetField<String> familyName;
    private final SetField<String> givenName;
    private final SetField<String> middleName;
    private final String personId;
    private final SetField<List<PersonPhoneNumber>> phoneNumbers;

    /* JADX WARN: Multi-variable type inference failed */
    public UpdatePersonParams(String personId, SetField<String> setField, SetField<String> setField2, SetField<String> setField3, SetField<String> setField4, SetField<? extends Uri> setField5, SetField<? extends List<PersonPhoneNumber>> setField6, SetField<? extends List<PersonEmailAddress>> setField7) {
        Intrinsics.checkNotNullParameter(personId, "personId");
        this.personId = personId;
        this.displayName = setField;
        this.givenName = setField2;
        this.middleName = setField3;
        this.familyName = setField4;
        this.externalUri = setField5;
        this.phoneNumbers = setField6;
        this.emailAddresses = setField7;
    }

    public /* synthetic */ UpdatePersonParams(String str, SetField setField, SetField setField2, SetField setField3, SetField setField4, SetField setField5, SetField setField6, SetField setField7, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(str, (i & 2) != 0 ? null : setField, (i & 4) != 0 ? null : setField2, (i & 8) != 0 ? null : setField3, (i & 16) != 0 ? null : setField4, (i & 32) != 0 ? null : setField5, (i & 64) != 0 ? null : setField6, (i & 128) != 0 ? null : setField7);
    }

    public boolean equals(Object other) {
        if (!(other instanceof UpdatePersonParams)) {
            return false;
        }
        UpdatePersonParams updatePersonParams = (UpdatePersonParams) other;
        return Intrinsics.areEqual(this.personId, updatePersonParams.personId) && Intrinsics.areEqual(this.displayName, updatePersonParams.displayName) && Intrinsics.areEqual(this.givenName, updatePersonParams.givenName) && Intrinsics.areEqual(this.middleName, updatePersonParams.middleName) && Intrinsics.areEqual(this.familyName, updatePersonParams.familyName) && Intrinsics.areEqual(this.externalUri, updatePersonParams.externalUri) && Intrinsics.areEqual(this.phoneNumbers, updatePersonParams.phoneNumbers) && Intrinsics.areEqual(this.emailAddresses, updatePersonParams.emailAddresses);
    }

    public final SetField<String> getDisplayName() {
        return this.displayName;
    }

    public final SetField<List<PersonEmailAddress>> getEmailAddresses() {
        return this.emailAddresses;
    }

    public final SetField<Uri> getExternalUri() {
        return this.externalUri;
    }

    public final SetField<String> getFamilyName() {
        return this.familyName;
    }

    public final SetField<String> getGivenName() {
        return this.givenName;
    }

    public final SetField<String> getMiddleName() {
        return this.middleName;
    }

    public final String getPersonId() {
        return this.personId;
    }

    public final SetField<List<PersonPhoneNumber>> getPhoneNumbers() {
        return this.phoneNumbers;
    }

    public int hashCode() {
        return Objects.hash(this.personId, this.displayName, this.givenName, this.middleName, this.familyName, this.externalUri, this.phoneNumbers, this.emailAddresses);
    }
}
