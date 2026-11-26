package com.google.android.appfunctions.schema.persons.v1;

import android.net.Uri;
import com.market.sdk.reflect.ReflectUtilsForMiui;
import com.xiaomi.mirror.RemoteDeviceInfo;
import java.util.List;
import java.util.Objects;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: com.google.android.appfunctions:appfunctions-schema@@1.0.7 */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u00008\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\r\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\b\n\u0000\b\u0007\u0018\u00002\u00020\u0001B_\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\n\b\u0002\u0010\u0004\u001a\u0004\u0018\u00010\u0003\u0012\n\b\u0002\u0010\u0005\u001a\u0004\u0018\u00010\u0003\u0012\n\b\u0002\u0010\u0006\u001a\u0004\u0018\u00010\u0003\u0012\n\b\u0002\u0010\u0007\u001a\u0004\u0018\u00010\b\u0012\u000e\b\u0002\u0010\t\u001a\b\u0012\u0004\u0012\u00020\u000b0\n\u0012\u000e\b\u0002\u0010\f\u001a\b\u0012\u0004\u0012\u00020\r0\n¢\u0006\u0004\b\u000e\u0010\u000fJ\u0013\u0010\u001a\u001a\u00020\u001b2\b\u0010\u001c\u001a\u0004\u0018\u00010\u0001H\u0096\u0002J\b\u0010\u001d\u001a\u00020\u001eH\u0016R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0010\u0010\u0011R\u0013\u0010\u0004\u001a\u0004\u0018\u00010\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0012\u0010\u0011R\u0013\u0010\u0005\u001a\u0004\u0018\u00010\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0013\u0010\u0011R\u0013\u0010\u0006\u001a\u0004\u0018\u00010\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0014\u0010\u0011R\u0013\u0010\u0007\u001a\u0004\u0018\u00010\b¢\u0006\b\n\u0000\u001a\u0004\b\u0015\u0010\u0016R\u0017\u0010\t\u001a\b\u0012\u0004\u0012\u00020\u000b0\n¢\u0006\b\n\u0000\u001a\u0004\b\u0017\u0010\u0018R\u0017\u0010\f\u001a\b\u0012\u0004\u0012\u00020\r0\n¢\u0006\b\n\u0000\u001a\u0004\b\u0019\u0010\u0018¨\u0006\u001f"}, d2 = {"Lcom/google/android/appfunctions/schema/persons/v1/CreatePersonParams;", "", "displayName", "", "givenName", "middleName", "familyName", "externalUri", "Landroid/net/Uri;", "phoneNumbers", "", "Lcom/google/android/appfunctions/schema/persons/v1/PersonPhoneNumber;", "emailAddresses", "Lcom/google/android/appfunctions/schema/persons/v1/PersonEmailAddress;", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Landroid/net/Uri;Ljava/util/List;Ljava/util/List;)V", "getDisplayName", "()Ljava/lang/String;", "getGivenName", "getMiddleName", "getFamilyName", "getExternalUri", "()Landroid/net/Uri;", "getPhoneNumbers", "()Ljava/util/List;", "getEmailAddresses", "equals", "", RemoteDeviceInfo.MANUFACTURER_OTHER, "hashCode", "", "java.com.google.android.libraries.llm.appfunctions.appfunctions_schema2.src.com.google.android.appfunctions.schema.persons.v1_v1"}, k = 1, mv = {2, 1, 0}, xi = 48)
public final class CreatePersonParams {
    private final String displayName;
    private final List<PersonEmailAddress> emailAddresses;
    private final Uri externalUri;
    private final String familyName;
    private final String givenName;
    private final String middleName;
    private final List<PersonPhoneNumber> phoneNumbers;

    public CreatePersonParams(String displayName, String str, String str2, String str3, Uri uri, List<PersonPhoneNumber> phoneNumbers, List<PersonEmailAddress> emailAddresses) {
        Intrinsics.checkNotNullParameter(displayName, "displayName");
        Intrinsics.checkNotNullParameter(phoneNumbers, "phoneNumbers");
        Intrinsics.checkNotNullParameter(emailAddresses, "emailAddresses");
        this.displayName = displayName;
        this.givenName = str;
        this.middleName = str2;
        this.familyName = str3;
        this.externalUri = uri;
        this.phoneNumbers = phoneNumbers;
        this.emailAddresses = emailAddresses;
    }

    /* JADX WARN: Illegal instructions before constructor call */
    public /* synthetic */ CreatePersonParams(String str, String str2, String str3, String str4, Uri uri, List list, List list2, int i, DefaultConstructorMarker defaultConstructorMarker) {
        List listEmptyList = (i & 32) != 0 ? CollectionsKt.emptyList() : list;
        List listEmptyList2 = (i & 64) != 0 ? CollectionsKt.emptyList() : list2;
        int i2 = i & 8;
        int i3 = i & 4;
        int i4 = i & 2;
        Uri uri2 = (i & 16) != 0 ? null : uri;
        String str5 = i2 != 0 ? null : str4;
        this(str, i4 != 0 ? null : str2, i3 != 0 ? null : str3, str5, uri2, listEmptyList, listEmptyList2);
    }

    public boolean equals(Object other) {
        if (!(other instanceof CreatePersonParams)) {
            return false;
        }
        CreatePersonParams createPersonParams = (CreatePersonParams) other;
        return Intrinsics.areEqual(this.displayName, createPersonParams.displayName) && Intrinsics.areEqual(this.givenName, createPersonParams.givenName) && Intrinsics.areEqual(this.middleName, createPersonParams.middleName) && Intrinsics.areEqual(this.familyName, createPersonParams.familyName) && Intrinsics.areEqual(this.externalUri, createPersonParams.externalUri) && Intrinsics.areEqual(this.phoneNumbers, createPersonParams.phoneNumbers) && Intrinsics.areEqual(this.emailAddresses, createPersonParams.emailAddresses);
    }

    public final String getDisplayName() {
        return this.displayName;
    }

    public final List<PersonEmailAddress> getEmailAddresses() {
        return this.emailAddresses;
    }

    public final Uri getExternalUri() {
        return this.externalUri;
    }

    public final String getFamilyName() {
        return this.familyName;
    }

    public final String getGivenName() {
        return this.givenName;
    }

    public final String getMiddleName() {
        return this.middleName;
    }

    public final List<PersonPhoneNumber> getPhoneNumbers() {
        return this.phoneNumbers;
    }

    public int hashCode() {
        return Objects.hash(this.displayName, this.givenName, this.middleName, this.familyName, this.externalUri, this.phoneNumbers, this.emailAddresses);
    }
}
