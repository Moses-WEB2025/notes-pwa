package com.google.android.appfunctions.schema.persons.v1;

import android.net.Uri;
import androidx.appfunctions.AppFunctionData;
import androidx.appfunctions.internal.AppFunctionSerializableFactory;
import androidx.appfunctions.internal.serializableproxies.C$UriFactory;
import com.google.android.appfunctions.schema.types.v1.C$SetFieldFactory;
import com.google.android.appfunctions.schema.types.v1.SetField;
import com.market.sdk.reflect.ReflectUtilsForMiui;
import java.util.List;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: renamed from: com.google.android.appfunctions.schema.persons.v1.$UpdatePersonParamsFactory, reason: invalid class name */
/* JADX INFO: compiled from: com.google.android.appfunctions:appfunctions-schema@@1.0.7 */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000\u0016\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0003\b\u0007\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u0001B\u0007¢\u0006\u0004\b\u0003\u0010\u0004J\u0010\u0010\u0005\u001a\u00020\u00022\u0006\u0010\u0006\u001a\u00020\u0007H\u0016J\u0010\u0010\b\u001a\u00020\u00072\u0006\u0010\t\u001a\u00020\u0002H\u0016¨\u0006\n"}, d2 = {"com/google/android/appfunctions/schema/persons/v1/$UpdatePersonParamsFactory", "Landroidx/appfunctions/internal/AppFunctionSerializableFactory;", "Lcom/google/android/appfunctions/schema/persons/v1/UpdatePersonParams;", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "()V", "fromAppFunctionData", "appFunctionData", "Landroidx/appfunctions/AppFunctionData;", "toAppFunctionData", "appFunctionSerializable", "java.com.google.android.libraries.llm.appfunctions.appfunctions_schema2.src.com.google.android.appfunctions.schema.persons.v1_v1"}, k = 1, mv = {2, 1, 0}, xi = 48)
public final class C$UpdatePersonParamsFactory implements AppFunctionSerializableFactory<UpdatePersonParams> {
    /* JADX WARN: Can't rename method to resolve collision */
    @Override // androidx.appfunctions.internal.AppFunctionSerializableFactory
    public UpdatePersonParams fromAppFunctionData(AppFunctionData appFunctionData) {
        Intrinsics.checkNotNullParameter(appFunctionData, "appFunctionData");
        AppFunctionData appFunctionDataWithSpec = getAppFunctionDataWithSpec(appFunctionData, "com.google.android.appfunctions.schema.persons.v1.UpdatePersonParams");
        C$SetFieldFactory c$SetFieldFactory = new C$SetFieldFactory(new AppFunctionSerializableFactory.TypeParameter.PrimitiveTypeParameter(String.class));
        C$SetFieldFactory c$SetFieldFactory2 = new C$SetFieldFactory(new AppFunctionSerializableFactory.TypeParameter.PrimitiveTypeParameter(String.class));
        C$SetFieldFactory c$SetFieldFactory3 = new C$SetFieldFactory(new AppFunctionSerializableFactory.TypeParameter.SerializableTypeParameter(Uri.class, new C$UriFactory()));
        C$SetFieldFactory c$SetFieldFactory4 = new C$SetFieldFactory(new AppFunctionSerializableFactory.TypeParameter.SerializableListTypeParameter(PersonPhoneNumber.class, new C$PersonPhoneNumberFactory()));
        C$SetFieldFactory c$SetFieldFactory5 = new C$SetFieldFactory(new AppFunctionSerializableFactory.TypeParameter.SerializableListTypeParameter(PersonEmailAddress.class, new C$PersonEmailAddressFactory()));
        String stringOrNull = appFunctionDataWithSpec.getStringOrNull("personId");
        if (stringOrNull == null) {
            throw new IllegalStateException("Required value was null.");
        }
        AppFunctionData appFunctionData2 = appFunctionDataWithSpec.getAppFunctionData("displayName");
        SetField setFieldFromAppFunctionData = appFunctionData2 != null ? c$SetFieldFactory.fromAppFunctionData(appFunctionData2) : null;
        AppFunctionData appFunctionData3 = appFunctionDataWithSpec.getAppFunctionData("givenName");
        SetField setFieldFromAppFunctionData2 = appFunctionData3 != null ? c$SetFieldFactory2.fromAppFunctionData(appFunctionData3) : null;
        AppFunctionData appFunctionData4 = appFunctionDataWithSpec.getAppFunctionData("middleName");
        SetField setFieldFromAppFunctionData3 = appFunctionData4 != null ? c$SetFieldFactory2.fromAppFunctionData(appFunctionData4) : null;
        AppFunctionData appFunctionData5 = appFunctionDataWithSpec.getAppFunctionData("familyName");
        SetField setFieldFromAppFunctionData4 = appFunctionData5 != null ? c$SetFieldFactory2.fromAppFunctionData(appFunctionData5) : null;
        AppFunctionData appFunctionData6 = appFunctionDataWithSpec.getAppFunctionData("externalUri");
        SetField setFieldFromAppFunctionData5 = appFunctionData6 != null ? c$SetFieldFactory3.fromAppFunctionData(appFunctionData6) : null;
        AppFunctionData appFunctionData7 = appFunctionDataWithSpec.getAppFunctionData("phoneNumbers");
        SetField setFieldFromAppFunctionData6 = appFunctionData7 != null ? c$SetFieldFactory4.fromAppFunctionData(appFunctionData7) : null;
        AppFunctionData appFunctionData8 = appFunctionDataWithSpec.getAppFunctionData("emailAddresses");
        return new UpdatePersonParams(stringOrNull, setFieldFromAppFunctionData, setFieldFromAppFunctionData2, setFieldFromAppFunctionData3, setFieldFromAppFunctionData4, setFieldFromAppFunctionData5, setFieldFromAppFunctionData6, appFunctionData8 != null ? c$SetFieldFactory5.fromAppFunctionData(appFunctionData8) : null);
    }

    @Override // androidx.appfunctions.internal.AppFunctionSerializableFactory
    public AppFunctionData toAppFunctionData(UpdatePersonParams appFunctionSerializable) {
        Intrinsics.checkNotNullParameter(appFunctionSerializable, "appFunctionSerializable");
        C$SetFieldFactory c$SetFieldFactory = new C$SetFieldFactory(new AppFunctionSerializableFactory.TypeParameter.PrimitiveTypeParameter(String.class));
        C$SetFieldFactory c$SetFieldFactory2 = new C$SetFieldFactory(new AppFunctionSerializableFactory.TypeParameter.PrimitiveTypeParameter(String.class));
        C$SetFieldFactory c$SetFieldFactory3 = new C$SetFieldFactory(new AppFunctionSerializableFactory.TypeParameter.SerializableTypeParameter(Uri.class, new C$UriFactory()));
        C$SetFieldFactory c$SetFieldFactory4 = new C$SetFieldFactory(new AppFunctionSerializableFactory.TypeParameter.SerializableListTypeParameter(PersonPhoneNumber.class, new C$PersonPhoneNumberFactory()));
        C$SetFieldFactory c$SetFieldFactory5 = new C$SetFieldFactory(new AppFunctionSerializableFactory.TypeParameter.SerializableListTypeParameter(PersonEmailAddress.class, new C$PersonEmailAddressFactory()));
        AppFunctionData.Builder appFunctionDataBuilder = getAppFunctionDataBuilder("com.google.android.appfunctions.schema.persons.v1.UpdatePersonParams");
        appFunctionDataBuilder.setString("personId", appFunctionSerializable.getPersonId());
        SetField<String> displayName = appFunctionSerializable.getDisplayName();
        if (displayName != null) {
            appFunctionDataBuilder.setAppFunctionData("displayName", c$SetFieldFactory.toAppFunctionData((SetField) displayName));
        }
        SetField<String> givenName = appFunctionSerializable.getGivenName();
        if (givenName != null) {
            appFunctionDataBuilder.setAppFunctionData("givenName", c$SetFieldFactory2.toAppFunctionData((SetField) givenName));
        }
        SetField<String> middleName = appFunctionSerializable.getMiddleName();
        if (middleName != null) {
            appFunctionDataBuilder.setAppFunctionData("middleName", c$SetFieldFactory2.toAppFunctionData((SetField) middleName));
        }
        SetField<String> familyName = appFunctionSerializable.getFamilyName();
        if (familyName != null) {
            appFunctionDataBuilder.setAppFunctionData("familyName", c$SetFieldFactory2.toAppFunctionData((SetField) familyName));
        }
        SetField<Uri> externalUri = appFunctionSerializable.getExternalUri();
        if (externalUri != null) {
            appFunctionDataBuilder.setAppFunctionData("externalUri", c$SetFieldFactory3.toAppFunctionData((SetField) externalUri));
        }
        SetField<List<PersonPhoneNumber>> phoneNumbers = appFunctionSerializable.getPhoneNumbers();
        if (phoneNumbers != null) {
            appFunctionDataBuilder.setAppFunctionData("phoneNumbers", c$SetFieldFactory4.toAppFunctionData((SetField) phoneNumbers));
        }
        SetField<List<PersonEmailAddress>> emailAddresses = appFunctionSerializable.getEmailAddresses();
        if (emailAddresses != null) {
            appFunctionDataBuilder.setAppFunctionData("emailAddresses", c$SetFieldFactory5.toAppFunctionData((SetField) emailAddresses));
        }
        return appFunctionDataBuilder.build();
    }
}
