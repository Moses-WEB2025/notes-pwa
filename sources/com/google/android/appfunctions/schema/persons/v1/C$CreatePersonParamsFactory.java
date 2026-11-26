package com.google.android.appfunctions.schema.persons.v1;

import android.net.Uri;
import androidx.appfunctions.AppFunctionData;
import androidx.appfunctions.internal.AppFunctionSerializableFactory;
import androidx.appfunctions.internal.serializableproxies.C$UriFactory;
import com.market.sdk.reflect.ReflectUtilsForMiui;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: renamed from: com.google.android.appfunctions.schema.persons.v1.$CreatePersonParamsFactory, reason: invalid class name */
/* JADX INFO: compiled from: com.google.android.appfunctions:appfunctions-schema@@1.0.7 */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000\u0016\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0003\b\u0007\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u0001B\u0007¢\u0006\u0004\b\u0003\u0010\u0004J\u0010\u0010\u0005\u001a\u00020\u00022\u0006\u0010\u0006\u001a\u00020\u0007H\u0016J\u0010\u0010\b\u001a\u00020\u00072\u0006\u0010\t\u001a\u00020\u0002H\u0016¨\u0006\n"}, d2 = {"com/google/android/appfunctions/schema/persons/v1/$CreatePersonParamsFactory", "Landroidx/appfunctions/internal/AppFunctionSerializableFactory;", "Lcom/google/android/appfunctions/schema/persons/v1/CreatePersonParams;", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "()V", "fromAppFunctionData", "appFunctionData", "Landroidx/appfunctions/AppFunctionData;", "toAppFunctionData", "appFunctionSerializable", "java.com.google.android.libraries.llm.appfunctions.appfunctions_schema2.src.com.google.android.appfunctions.schema.persons.v1_v1"}, k = 1, mv = {2, 1, 0}, xi = 48)
public final class C$CreatePersonParamsFactory implements AppFunctionSerializableFactory<CreatePersonParams> {
    /* JADX WARN: Can't rename method to resolve collision */
    @Override // androidx.appfunctions.internal.AppFunctionSerializableFactory
    public CreatePersonParams fromAppFunctionData(AppFunctionData appFunctionData) {
        Intrinsics.checkNotNullParameter(appFunctionData, "appFunctionData");
        AppFunctionData appFunctionDataWithSpec = getAppFunctionDataWithSpec(appFunctionData, "com.google.android.appfunctions.schema.persons.v1.CreatePersonParams");
        C$PersonPhoneNumberFactory c$PersonPhoneNumberFactory = new C$PersonPhoneNumberFactory();
        C$PersonEmailAddressFactory c$PersonEmailAddressFactory = new C$PersonEmailAddressFactory();
        C$UriFactory c$UriFactory = new C$UriFactory();
        String stringOrNull = appFunctionDataWithSpec.getStringOrNull("displayName");
        if (stringOrNull == null) {
            throw new IllegalStateException("Required value was null.");
        }
        String stringOrNull2 = appFunctionDataWithSpec.getStringOrNull("givenName");
        String stringOrNull3 = appFunctionDataWithSpec.getStringOrNull("middleName");
        String stringOrNull4 = appFunctionDataWithSpec.getStringOrNull("familyName");
        AppFunctionData appFunctionData2 = appFunctionDataWithSpec.getAppFunctionData("externalUri");
        Uri uriFromAppFunctionData = appFunctionData2 != null ? c$UriFactory.fromAppFunctionData(appFunctionData2) : null;
        List<AppFunctionData> appFunctionDataList = appFunctionDataWithSpec.getAppFunctionDataList("phoneNumbers");
        if (appFunctionDataList == null) {
            appFunctionDataList = CollectionsKt.emptyList();
        }
        List<AppFunctionData> list = appFunctionDataList;
        ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(list, 10));
        Iterator<T> it = list.iterator();
        while (it.hasNext()) {
            arrayList.add(c$PersonPhoneNumberFactory.fromAppFunctionData((AppFunctionData) it.next()));
        }
        ArrayList arrayList2 = arrayList;
        List<AppFunctionData> appFunctionDataList2 = appFunctionDataWithSpec.getAppFunctionDataList("emailAddresses");
        if (appFunctionDataList2 == null) {
            appFunctionDataList2 = CollectionsKt.emptyList();
        }
        List<AppFunctionData> list2 = appFunctionDataList2;
        ArrayList arrayList3 = new ArrayList(CollectionsKt.collectionSizeOrDefault(list2, 10));
        Iterator<T> it2 = list2.iterator();
        while (it2.hasNext()) {
            arrayList3.add(c$PersonEmailAddressFactory.fromAppFunctionData((AppFunctionData) it2.next()));
        }
        return new CreatePersonParams(stringOrNull, stringOrNull2, stringOrNull3, stringOrNull4, uriFromAppFunctionData, arrayList2, arrayList3);
    }

    @Override // androidx.appfunctions.internal.AppFunctionSerializableFactory
    public AppFunctionData toAppFunctionData(CreatePersonParams appFunctionSerializable) {
        Intrinsics.checkNotNullParameter(appFunctionSerializable, "appFunctionSerializable");
        C$PersonPhoneNumberFactory c$PersonPhoneNumberFactory = new C$PersonPhoneNumberFactory();
        C$PersonEmailAddressFactory c$PersonEmailAddressFactory = new C$PersonEmailAddressFactory();
        C$UriFactory c$UriFactory = new C$UriFactory();
        AppFunctionData.Builder appFunctionDataBuilder = getAppFunctionDataBuilder("com.google.android.appfunctions.schema.persons.v1.CreatePersonParams");
        appFunctionDataBuilder.setString("displayName", appFunctionSerializable.getDisplayName());
        String givenName = appFunctionSerializable.getGivenName();
        if (givenName != null) {
            appFunctionDataBuilder.setString("givenName", givenName);
        }
        String middleName = appFunctionSerializable.getMiddleName();
        if (middleName != null) {
            appFunctionDataBuilder.setString("middleName", middleName);
        }
        String familyName = appFunctionSerializable.getFamilyName();
        if (familyName != null) {
            appFunctionDataBuilder.setString("familyName", familyName);
        }
        Uri externalUri = appFunctionSerializable.getExternalUri();
        if (externalUri != null) {
            appFunctionDataBuilder.setAppFunctionData("externalUri", c$UriFactory.toAppFunctionData(externalUri));
        }
        List<PersonPhoneNumber> phoneNumbers = appFunctionSerializable.getPhoneNumbers();
        ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(phoneNumbers, 10));
        Iterator<T> it = phoneNumbers.iterator();
        while (it.hasNext()) {
            arrayList.add(c$PersonPhoneNumberFactory.toAppFunctionData((PersonPhoneNumber) it.next()));
        }
        appFunctionDataBuilder.setAppFunctionDataList("phoneNumbers", arrayList);
        List<PersonEmailAddress> emailAddresses = appFunctionSerializable.getEmailAddresses();
        ArrayList arrayList2 = new ArrayList(CollectionsKt.collectionSizeOrDefault(emailAddresses, 10));
        Iterator<T> it2 = emailAddresses.iterator();
        while (it2.hasNext()) {
            arrayList2.add(c$PersonEmailAddressFactory.toAppFunctionData((PersonEmailAddress) it2.next()));
        }
        appFunctionDataBuilder.setAppFunctionDataList("emailAddresses", arrayList2);
        return appFunctionDataBuilder.build();
    }
}
