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
@Metadata(d1 = {"\u0000:\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u000e\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0002\b\u0007\u0018\u0000 !2\u00020\u0001:\u0001!Bg\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0003\u0012\n\b\u0002\u0010\u0005\u001a\u0004\u0018\u00010\u0003\u0012\n\b\u0002\u0010\u0006\u001a\u0004\u0018\u00010\u0003\u0012\n\b\u0002\u0010\u0007\u001a\u0004\u0018\u00010\u0003\u0012\n\b\u0002\u0010\b\u001a\u0004\u0018\u00010\t\u0012\u000e\b\u0002\u0010\n\u001a\b\u0012\u0004\u0012\u00020\f0\u000b\u0012\u000e\b\u0002\u0010\r\u001a\b\u0012\u0004\u0012\u00020\u000e0\u000b¢\u0006\u0004\b\u000f\u0010\u0010J\u0013\u0010\u001c\u001a\u00020\u001d2\b\u0010\u001e\u001a\u0004\u0018\u00010\u0001H\u0096\u0002J\b\u0010\u001f\u001a\u00020 H\u0016R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0011\u0010\u0012R\u0011\u0010\u0004\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0013\u0010\u0012R\u0013\u0010\u0005\u001a\u0004\u0018\u00010\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0014\u0010\u0012R\u0013\u0010\u0006\u001a\u0004\u0018\u00010\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0015\u0010\u0012R\u0013\u0010\u0007\u001a\u0004\u0018\u00010\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0016\u0010\u0012R\u0013\u0010\b\u001a\u0004\u0018\u00010\t¢\u0006\b\n\u0000\u001a\u0004\b\u0017\u0010\u0018R\u0017\u0010\n\u001a\b\u0012\u0004\u0012\u00020\f0\u000b¢\u0006\b\n\u0000\u001a\u0004\b\u0019\u0010\u001aR\u0017\u0010\r\u001a\b\u0012\u0004\u0012\u00020\u000e0\u000b¢\u0006\b\n\u0000\u001a\u0004\b\u001b\u0010\u001a¨\u0006\""}, d2 = {"Lcom/google/android/appfunctions/schema/persons/v1/Person;", "", "id", "", "displayName", "givenName", "middleName", "familyName", "externalUri", "Landroid/net/Uri;", "phoneNumbers", "", "Lcom/google/android/appfunctions/schema/persons/v1/PersonPhoneNumber;", "emailAddresses", "Lcom/google/android/appfunctions/schema/persons/v1/PersonEmailAddress;", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Landroid/net/Uri;Ljava/util/List;Ljava/util/List;)V", "getId", "()Ljava/lang/String;", "getDisplayName", "getGivenName", "getMiddleName", "getFamilyName", "getExternalUri", "()Landroid/net/Uri;", "getPhoneNumbers", "()Ljava/util/List;", "getEmailAddresses", "equals", "", RemoteDeviceInfo.MANUFACTURER_OTHER, "hashCode", "", "Companion", "java.com.google.android.libraries.llm.appfunctions.appfunctions_schema2.src.com.google.android.appfunctions.schema.persons.v1_v1"}, k = 1, mv = {2, 1, 0}, xi = 48)
public final class Person {
    public static final String ASSIST_FOREGROUND_TYPE = "com.google.android.appfunctions.schema.persons.v1.PersonForeground";
    private final String displayName;
    private final List<PersonEmailAddress> emailAddresses;
    private final Uri externalUri;
    private final String familyName;
    private final String givenName;
    private final String id;
    private final String middleName;
    private final List<PersonPhoneNumber> phoneNumbers;

    public Person(String id, String displayName, String str, String str2, String str3, Uri uri, List<PersonPhoneNumber> phoneNumbers, List<PersonEmailAddress> emailAddresses) {
        Intrinsics.checkNotNullParameter(id, "id");
        Intrinsics.checkNotNullParameter(displayName, "displayName");
        Intrinsics.checkNotNullParameter(phoneNumbers, "phoneNumbers");
        Intrinsics.checkNotNullParameter(emailAddresses, "emailAddresses");
        this.id = id;
        this.displayName = displayName;
        this.givenName = str;
        this.middleName = str2;
        this.familyName = str3;
        this.externalUri = uri;
        this.phoneNumbers = phoneNumbers;
        this.emailAddresses = emailAddresses;
    }

    /* JADX WARN: Illegal instructions before constructor call */
    public /* synthetic */ Person(String str, String str2, String str3, String str4, String str5, Uri uri, List list, List list2, int i, DefaultConstructorMarker defaultConstructorMarker) {
        List listEmptyList = (i & 64) != 0 ? CollectionsKt.emptyList() : list;
        List listEmptyList2 = (i & 128) != 0 ? CollectionsKt.emptyList() : list2;
        int i2 = i & 16;
        int i3 = i & 8;
        int i4 = i & 4;
        Uri uri2 = (i & 32) != 0 ? null : uri;
        String str6 = i2 != 0 ? null : str5;
        this(str, str2, i4 != 0 ? null : str3, i3 != 0 ? null : str4, str6, uri2, listEmptyList, listEmptyList2);
    }

    public boolean equals(Object other) {
        if (!(other instanceof Person)) {
            return false;
        }
        Person person = (Person) other;
        return Intrinsics.areEqual(this.id, person.id) && Intrinsics.areEqual(this.displayName, person.displayName) && Intrinsics.areEqual(this.givenName, person.givenName) && Intrinsics.areEqual(this.middleName, person.middleName) && Intrinsics.areEqual(this.familyName, person.familyName) && Intrinsics.areEqual(this.externalUri, person.externalUri) && Intrinsics.areEqual(this.phoneNumbers, person.phoneNumbers) && Intrinsics.areEqual(this.emailAddresses, person.emailAddresses);
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

    public final String getId() {
        return this.id;
    }

    public final String getMiddleName() {
        return this.middleName;
    }

    public final List<PersonPhoneNumber> getPhoneNumbers() {
        return this.phoneNumbers;
    }

    public int hashCode() {
        return Objects.hash(this.id, this.displayName, this.givenName, this.middleName, this.familyName, this.externalUri, this.phoneNumbers, this.emailAddresses);
    }
}
