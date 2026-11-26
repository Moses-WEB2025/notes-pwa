package androidx.appsearch.safeparcel;

import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import androidx.appsearch.app.EmbeddingVector;
import java.util.ArrayList;
import java.util.Objects;

/* JADX INFO: loaded from: classes.dex */
public class PropertyParcelCreator implements Parcelable.Creator<PropertyParcel> {
    private static final String BOOLEAN_ARRAY_FIELD = "booleanArray";
    private static final String BYTES_ARRAY_FIELD = "bytesArray";
    private static final String BYTE_ARRAY_FIELD = "byteArray";
    private static final String DOC_ARRAY_FIELD = "docArray";
    private static final String DOUBLE_ARRAY_FIELD = "doubleArray";
    private static final String EMBEDDING_ARRAY_FIELD = "embeddingArray";
    private static final String EMBEDDING_MODEL_SIGNATURE_FIELD = "embeddingModelSignature";
    private static final String EMBEDDING_VALUE_FIELD = "embeddingValue";
    private static final String LONG_ARRAY_FIELD = "longArray";
    private static final String PROPERTY_NAME_FIELD = "propertyName";
    private static final String STRING_ARRAY_FIELD = "stringArray";

    private static PropertyParcel createPropertyParcelFromBundle(Bundle bundle) {
        byte[] byteArray;
        Objects.requireNonNull(bundle);
        String string = bundle.getString(PROPERTY_NAME_FIELD);
        Objects.requireNonNull(string);
        PropertyParcel.Builder builder = new PropertyParcel.Builder(string);
        String[] stringArray = bundle.getStringArray(STRING_ARRAY_FIELD);
        long[] longArray = bundle.getLongArray(LONG_ARRAY_FIELD);
        double[] doubleArray = bundle.getDoubleArray(DOUBLE_ARRAY_FIELD);
        boolean[] booleanArray = bundle.getBooleanArray(BOOLEAN_ARRAY_FIELD);
        ArrayList parcelableArrayList = bundle.getParcelableArrayList(BYTES_ARRAY_FIELD);
        Parcelable[] parcelableArray = bundle.getParcelableArray(DOC_ARRAY_FIELD);
        ArrayList parcelableArrayList2 = bundle.getParcelableArrayList(EMBEDDING_ARRAY_FIELD);
        if (stringArray != null) {
            builder.setStringValues(stringArray);
        } else if (longArray != null) {
            builder.setLongValues(longArray);
        } else if (doubleArray != null) {
            builder.setDoubleValues(doubleArray);
        } else if (booleanArray != null) {
            builder.setBooleanValues(booleanArray);
        } else {
            int i = 0;
            if (parcelableArrayList != null) {
                byte[][] bArr = new byte[parcelableArrayList.size()][];
                while (i < parcelableArrayList.size()) {
                    Bundle bundle2 = (Bundle) parcelableArrayList.get(i);
                    if (bundle2 != null && (byteArray = bundle2.getByteArray(BYTE_ARRAY_FIELD)) != null) {
                        bArr[i] = byteArray;
                    }
                    i++;
                }
                builder.setBytesValues(bArr);
            } else if (parcelableArray != null) {
                GenericDocumentParcel[] genericDocumentParcelArr = new GenericDocumentParcel[parcelableArray.length];
                System.arraycopy(parcelableArray, 0, genericDocumentParcelArr, 0, parcelableArray.length);
                builder.setDocumentValues(genericDocumentParcelArr);
            } else if (parcelableArrayList2 != null) {
                EmbeddingVector[] embeddingVectorArr = new EmbeddingVector[parcelableArrayList2.size()];
                while (i < parcelableArrayList2.size()) {
                    Bundle bundle3 = (Bundle) parcelableArrayList2.get(i);
                    if (bundle3 != null) {
                        float[] floatArray = bundle3.getFloatArray(EMBEDDING_VALUE_FIELD);
                        String string2 = bundle3.getString(EMBEDDING_MODEL_SIGNATURE_FIELD);
                        if (floatArray != null && string2 != null) {
                            embeddingVectorArr[i] = new EmbeddingVector(floatArray, string2);
                        }
                    }
                    i++;
                }
                builder.setEmbeddingValues(embeddingVectorArr);
            } else {
                throw new IllegalArgumentException("property bundle passed in doesn't have any value set.");
            }
        }
        return builder.build();
    }

    private static Bundle createBundleFromPropertyParcel(PropertyParcel propertyParcel) {
        Objects.requireNonNull(propertyParcel);
        Bundle bundle = new Bundle();
        bundle.putString(PROPERTY_NAME_FIELD, propertyParcel.getPropertyName());
        String[] stringValues = propertyParcel.getStringValues();
        long[] longValues = propertyParcel.getLongValues();
        double[] doubleValues = propertyParcel.getDoubleValues();
        boolean[] booleanValues = propertyParcel.getBooleanValues();
        byte[][] bytesValues = propertyParcel.getBytesValues();
        GenericDocumentParcel[] documentValues = propertyParcel.getDocumentValues();
        EmbeddingVector[] embeddingValues = propertyParcel.getEmbeddingValues();
        if (stringValues != null) {
            bundle.putStringArray(STRING_ARRAY_FIELD, stringValues);
            return bundle;
        }
        if (longValues != null) {
            bundle.putLongArray(LONG_ARRAY_FIELD, longValues);
            return bundle;
        }
        if (doubleValues != null) {
            bundle.putDoubleArray(DOUBLE_ARRAY_FIELD, doubleValues);
            return bundle;
        }
        if (booleanValues != null) {
            bundle.putBooleanArray(BOOLEAN_ARRAY_FIELD, booleanValues);
            return bundle;
        }
        int i = 0;
        if (bytesValues != null) {
            ArrayList<? extends Parcelable> arrayList = new ArrayList<>(bytesValues.length);
            while (i < bytesValues.length) {
                Bundle bundle2 = new Bundle();
                bundle2.putByteArray(BYTE_ARRAY_FIELD, bytesValues[i]);
                arrayList.add(bundle2);
                i++;
            }
            bundle.putParcelableArrayList(BYTES_ARRAY_FIELD, arrayList);
            return bundle;
        }
        if (documentValues != null) {
            bundle.putParcelableArray(DOC_ARRAY_FIELD, documentValues);
            return bundle;
        }
        if (embeddingValues != null) {
            ArrayList<? extends Parcelable> arrayList2 = new ArrayList<>(embeddingValues.length);
            while (i < embeddingValues.length) {
                Bundle bundle3 = new Bundle();
                bundle3.putFloatArray(EMBEDDING_VALUE_FIELD, embeddingValues[i].getValues());
                bundle3.putString(EMBEDDING_MODEL_SIGNATURE_FIELD, embeddingValues[i].getModelSignature());
                arrayList2.add(bundle3);
                i++;
            }
            bundle.putParcelableArrayList(EMBEDDING_ARRAY_FIELD, arrayList2);
        }
        return bundle;
    }

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // android.os.Parcelable.Creator
    public PropertyParcel createFromParcel(Parcel parcel) {
        return createPropertyParcelFromBundle(parcel.readBundle(getClass().getClassLoader()));
    }

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // android.os.Parcelable.Creator
    public PropertyParcel[] newArray(int i) {
        return new PropertyParcel[i];
    }

    public static void writeToParcel(PropertyParcel propertyParcel, Parcel parcel, int i) {
        parcel.writeBundle(createBundleFromPropertyParcel(propertyParcel));
    }
}
