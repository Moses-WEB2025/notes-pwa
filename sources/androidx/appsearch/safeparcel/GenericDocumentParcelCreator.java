package androidx.appsearch.safeparcel;

import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import java.util.ArrayList;
import java.util.List;

/* JADX INFO: loaded from: classes.dex */
public class GenericDocumentParcelCreator implements Parcelable.Creator<GenericDocumentParcel> {
    private static final String CREATION_TIMESTAMP_MILLIS_FIELD = "creationTimestampMillis";
    private static final String ID_FIELD = "id";
    private static final String NAMESPACE_FIELD = "namespace";
    private static final String PARENT_TYPES_FIELD = "parentTypes";
    private static final String PROPERTIES_FIELD = "properties";
    private static final String SCHEMA_TYPE_FIELD = "schemaType";
    private static final String SCORE_FIELD = "score";
    private static final String TTL_MILLIS_FIELD = "ttlMillis";

    private static GenericDocumentParcel createGenericDocumentParcelFromBundle(Bundle bundle) {
        String string = bundle.getString(NAMESPACE_FIELD);
        String string2 = bundle.getString("id");
        String string3 = bundle.getString(SCHEMA_TYPE_FIELD);
        if (string == null || string2 == null || string3 == null) {
            throw new IllegalArgumentException("GenericDocumentParcel bundle doesn't have namespace, id, or schemaType.");
        }
        GenericDocumentParcel.Builder builder = new GenericDocumentParcel.Builder(string, string2, string3);
        ArrayList<String> stringArrayList = bundle.getStringArrayList(PARENT_TYPES_FIELD);
        if (stringArrayList != null) {
            builder.setParentTypes(stringArrayList);
        }
        builder.setScore(bundle.getInt(SCORE_FIELD));
        builder.setCreationTimestampMillis(bundle.getLong(CREATION_TIMESTAMP_MILLIS_FIELD));
        builder.setTtlMillis(bundle.getLong(TTL_MILLIS_FIELD));
        Bundle bundle2 = bundle.getBundle("properties");
        if (bundle2 != null) {
            for (String str : bundle2.keySet()) {
                builder.putInPropertyMap(str, (PropertyParcel) bundle2.getParcelable(str));
            }
        }
        return builder.build();
    }

    private static Bundle createBundleFromGenericDocumentParcel(GenericDocumentParcel genericDocumentParcel) {
        Bundle bundle = new Bundle();
        bundle.putString(NAMESPACE_FIELD, genericDocumentParcel.getNamespace());
        bundle.putString("id", genericDocumentParcel.getId());
        bundle.putString(SCHEMA_TYPE_FIELD, genericDocumentParcel.getSchemaType());
        bundle.putStringArrayList(PARENT_TYPES_FIELD, (ArrayList) genericDocumentParcel.getParentTypes());
        bundle.putInt(SCORE_FIELD, genericDocumentParcel.getScore());
        bundle.putLong(CREATION_TIMESTAMP_MILLIS_FIELD, genericDocumentParcel.getCreationTimestampMillis());
        bundle.putLong(TTL_MILLIS_FIELD, genericDocumentParcel.getTtlMillis());
        Bundle bundle2 = new Bundle();
        List<PropertyParcel> properties = genericDocumentParcel.getProperties();
        for (int i = 0; i < properties.size(); i++) {
            PropertyParcel propertyParcel = properties.get(i);
            bundle2.putParcelable(propertyParcel.getPropertyName(), propertyParcel);
        }
        bundle.putBundle("properties", bundle2);
        return bundle;
    }

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // android.os.Parcelable.Creator
    public GenericDocumentParcel createFromParcel(Parcel parcel) {
        return createGenericDocumentParcelFromBundle(parcel.readBundle(getClass().getClassLoader()));
    }

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // android.os.Parcelable.Creator
    public GenericDocumentParcel[] newArray(int i) {
        return new GenericDocumentParcel[i];
    }

    public static void writeToParcel(GenericDocumentParcel genericDocumentParcel, Parcel parcel, int i) {
        parcel.writeBundle(createBundleFromGenericDocumentParcel(genericDocumentParcel));
    }
}
