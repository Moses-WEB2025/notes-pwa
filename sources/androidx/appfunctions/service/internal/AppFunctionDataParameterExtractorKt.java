package androidx.appfunctions.service.internal;

import android.os.Parcelable;
import android.util.Log;
import androidx.appfunctions.AppFunctionData;
import androidx.appfunctions.AppFunctionInvalidArgumentException;
import androidx.appfunctions.internal.Constants;
import androidx.appfunctions.metadata.AppFunctionArrayTypeMetadata;
import androidx.appfunctions.metadata.AppFunctionBooleanTypeMetadata;
import androidx.appfunctions.metadata.AppFunctionBytesTypeMetadata;
import androidx.appfunctions.metadata.AppFunctionDataTypeMetadata;
import androidx.appfunctions.metadata.AppFunctionDoubleTypeMetadata;
import androidx.appfunctions.metadata.AppFunctionFloatTypeMetadata;
import androidx.appfunctions.metadata.AppFunctionIntTypeMetadata;
import androidx.appfunctions.metadata.AppFunctionLongTypeMetadata;
import androidx.appfunctions.metadata.AppFunctionObjectTypeMetadata;
import androidx.appfunctions.metadata.AppFunctionParameterMetadata;
import androidx.appfunctions.metadata.AppFunctionParcelableTypeMetadata;
import androidx.appfunctions.metadata.AppFunctionReferenceTypeMetadata;
import androidx.appfunctions.metadata.AppFunctionStringTypeMetadata;
import java.util.ArrayList;
import java.util.List;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: AppFunctionDataParameterExtractor.kt */
/* JADX INFO: loaded from: classes.dex */
@Metadata(d1 = {"\u00004\n\u0000\n\u0002\u0010\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\u001a\u0016\u0010\u0000\u001a\u0004\u0018\u00010\u0001*\u00020\u00022\u0006\u0010\u0003\u001a\u00020\u0004H\u0001\u001a.\u0010\u0005\u001a\u0004\u0018\u00010\u0001*\u00020\u00022\u0006\u0010\u0006\u001a\u00020\u00072\u0006\u0010\b\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u000b2\u0006\u0010\f\u001a\u00020\u000bH\u0003\u001a\u0016\u0010\r\u001a\b\u0012\u0004\u0012\u00020\u000f0\u000e2\u0006\u0010\u0010\u001a\u00020\u0007H\u0002¨\u0006\u0011"}, d2 = {"unsafeGetParameterValue", "", "Landroidx/appfunctions/AppFunctionData;", "parameterMetadata", "Landroidx/appfunctions/metadata/AppFunctionParameterMetadata;", "getArrayTypeParameterValue", "key", "", "arrayDataTypeMetadata", "Landroidx/appfunctions/metadata/AppFunctionArrayTypeMetadata;", "isNullable", "", "isRequired", "getParcelableClass", "Ljava/lang/Class;", "Landroid/os/Parcelable;", "className", "appfunctions-service"}, k = 2, mv = {2, 1, 0}, xi = 48)
public final class AppFunctionDataParameterExtractorKt {
    /* JADX WARN: Code duplicated, block: B:75:0x011f  */
    public static final Object unsafeGetParameterValue(AppFunctionData appFunctionData, AppFunctionParameterMetadata parameterMetadata) throws AppFunctionInvalidArgumentException {
        Object objDeserialize;
        Intrinsics.checkNotNullParameter(appFunctionData, "<this>");
        Intrinsics.checkNotNullParameter(parameterMetadata, "parameterMetadata");
        try {
            String name = parameterMetadata.getName();
            boolean isRequired = parameterMetadata.getIsRequired();
            boolean isNullable = parameterMetadata.getDataType().getIsNullable();
            AppFunctionDataTypeMetadata dataType = parameterMetadata.getDataType();
            if (dataType instanceof AppFunctionIntTypeMetadata) {
                if (!isRequired && !isNullable) {
                    Integer intOrNull = appFunctionData.getIntOrNull(name);
                    objDeserialize = Integer.valueOf(intOrNull != null ? intOrNull.intValue() : 0);
                } else {
                    objDeserialize = appFunctionData.getIntOrNull(name);
                }
            } else if (dataType instanceof AppFunctionLongTypeMetadata) {
                if (!isRequired && !isNullable) {
                    Long longOrNull = appFunctionData.getLongOrNull(name);
                    objDeserialize = Long.valueOf(longOrNull != null ? longOrNull.longValue() : 0L);
                } else {
                    objDeserialize = appFunctionData.getLongOrNull(name);
                }
            } else if (dataType instanceof AppFunctionFloatTypeMetadata) {
                if (!isRequired && !isNullable) {
                    Float floatOrNull = appFunctionData.getFloatOrNull(name);
                    objDeserialize = Float.valueOf(floatOrNull != null ? floatOrNull.floatValue() : 0.0f);
                } else {
                    objDeserialize = appFunctionData.getFloatOrNull(name);
                }
            } else if (dataType instanceof AppFunctionDoubleTypeMetadata) {
                if (!isRequired && !isNullable) {
                    Double doubleOrNull = appFunctionData.getDoubleOrNull(name);
                    objDeserialize = Double.valueOf(doubleOrNull != null ? doubleOrNull.doubleValue() : 0.0d);
                } else {
                    objDeserialize = appFunctionData.getDoubleOrNull(name);
                }
            } else if (dataType instanceof AppFunctionBooleanTypeMetadata) {
                if (!isRequired && !isNullable) {
                    Boolean booleanOrNull = appFunctionData.getBooleanOrNull(name);
                    objDeserialize = Boolean.valueOf(booleanOrNull != null ? booleanOrNull.booleanValue() : false);
                } else {
                    objDeserialize = appFunctionData.getBooleanOrNull(name);
                }
            } else if (dataType instanceof AppFunctionBytesTypeMetadata) {
                if (!isRequired && !isNullable) {
                    objDeserialize = appFunctionData.getByteArray(name);
                    if (objDeserialize == null) {
                        objDeserialize = new byte[0];
                    }
                } else {
                    objDeserialize = appFunctionData.getByteArray(name);
                }
            } else if (dataType instanceof AppFunctionStringTypeMetadata) {
                objDeserialize = appFunctionData.getString(name);
            } else if (dataType instanceof AppFunctionParcelableTypeMetadata) {
                objDeserialize = appFunctionData.getParcelable(name, getParcelableClass(((AppFunctionParcelableTypeMetadata) dataType).getQualifiedName()));
            } else if (dataType instanceof AppFunctionObjectTypeMetadata) {
                AppFunctionData appFunctionData2 = appFunctionData.getAppFunctionData(name);
                if (appFunctionData2 != null) {
                    String qualifiedName = ((AppFunctionObjectTypeMetadata) dataType).getQualifiedName();
                    if (qualifiedName == null) {
                        throw new IllegalStateException("Required value was null.".toString());
                    }
                    objDeserialize = appFunctionData2.deserialize(qualifiedName);
                } else {
                    objDeserialize = null;
                }
            } else if (dataType instanceof AppFunctionArrayTypeMetadata) {
                objDeserialize = getArrayTypeParameterValue(appFunctionData, parameterMetadata.getName(), (AppFunctionArrayTypeMetadata) dataType, dataType.getIsNullable(), parameterMetadata.getIsRequired());
            } else if (dataType instanceof AppFunctionReferenceTypeMetadata) {
                AppFunctionData appFunctionData3 = appFunctionData.getAppFunctionData(name);
                if (appFunctionData3 != null) {
                    String referenceDataType = ((AppFunctionReferenceTypeMetadata) dataType).getReferenceDataType();
                    if (referenceDataType == null) {
                        throw new IllegalStateException("Required value was null.".toString());
                    }
                    objDeserialize = appFunctionData3.deserialize(referenceDataType);
                } else {
                    objDeserialize = null;
                }
            } else {
                throw new IllegalStateException("Unknown DataTypeMetadata: " + dataType.getClass());
            }
            if (objDeserialize == null && parameterMetadata.getIsRequired() && !parameterMetadata.getDataType().getIsNullable()) {
                Log.d(Constants.APP_FUNCTIONS_TAG, "Parameter " + parameterMetadata.getName() + " is required");
                throw new IllegalArgumentException(("Parameter " + parameterMetadata.getName() + " is required").toString());
            }
            return objDeserialize;
        } catch (IllegalArgumentException e) {
            Log.d(Constants.APP_FUNCTIONS_TAG, "Parameter " + parameterMetadata.getName() + " should be the type of " + parameterMetadata.getDataType(), e);
            throw new AppFunctionInvalidArgumentException("Parameter " + parameterMetadata.getName() + " should be the type of " + parameterMetadata.getDataType());
        }
    }

    private static final Object getArrayTypeParameterValue(AppFunctionData appFunctionData, String str, AppFunctionArrayTypeMetadata appFunctionArrayTypeMetadata, boolean z, boolean z2) {
        AppFunctionDataTypeMetadata itemType = appFunctionArrayTypeMetadata.getItemType();
        if (itemType instanceof AppFunctionIntTypeMetadata) {
            if (!z2 && !z) {
                int[] intArray = appFunctionData.getIntArray(str);
                return intArray == null ? new int[0] : intArray;
            }
            return appFunctionData.getIntArray(str);
        }
        if (itemType instanceof AppFunctionLongTypeMetadata) {
            if (!z2 && !z) {
                long[] longArray = appFunctionData.getLongArray(str);
                return longArray == null ? new long[0] : longArray;
            }
            return appFunctionData.getLongArray(str);
        }
        if (itemType instanceof AppFunctionFloatTypeMetadata) {
            if (!z2 && !z) {
                float[] floatArray = appFunctionData.getFloatArray(str);
                return floatArray == null ? new float[0] : floatArray;
            }
            return appFunctionData.getFloatArray(str);
        }
        if (itemType instanceof AppFunctionDoubleTypeMetadata) {
            if (!z2 && !z) {
                double[] doubleArray = appFunctionData.getDoubleArray(str);
                return doubleArray == null ? new double[0] : doubleArray;
            }
            return appFunctionData.getDoubleArray(str);
        }
        if (itemType instanceof AppFunctionBooleanTypeMetadata) {
            if (!z2 && !z) {
                boolean[] booleanArray = appFunctionData.getBooleanArray(str);
                return booleanArray == null ? new boolean[0] : booleanArray;
            }
            return appFunctionData.getBooleanArray(str);
        }
        if (itemType instanceof AppFunctionBytesTypeMetadata) {
            throw new IllegalStateException("List<ByteArray> is not supported");
        }
        if (itemType instanceof AppFunctionStringTypeMetadata) {
            if (!z2 && !z) {
                List<String> stringList = appFunctionData.getStringList(str);
                return stringList == null ? CollectionsKt.emptyList() : stringList;
            }
            return appFunctionData.getStringList(str);
        }
        if (itemType instanceof AppFunctionParcelableTypeMetadata) {
            Class<Parcelable> parcelableClass = getParcelableClass(((AppFunctionParcelableTypeMetadata) itemType).getQualifiedName());
            if (!z2 && !z) {
                List parcelableList = appFunctionData.getParcelableList(str, parcelableClass);
                return parcelableList == null ? CollectionsKt.emptyList() : parcelableList;
            }
            return appFunctionData.getParcelableList(str, parcelableClass);
        }
        if (itemType instanceof AppFunctionObjectTypeMetadata) {
            if (!z2 && !z) {
                List<AppFunctionData> appFunctionDataList = appFunctionData.getAppFunctionDataList(str);
                if (appFunctionDataList == null) {
                    return CollectionsKt.emptyList();
                }
                List<AppFunctionData> list = appFunctionDataList;
                ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(list, 10));
                for (AppFunctionData appFunctionData2 : list) {
                    String qualifiedName = ((AppFunctionObjectTypeMetadata) itemType).getQualifiedName();
                    if (qualifiedName == null) {
                        throw new IllegalStateException("Required value was null.".toString());
                    }
                    arrayList.add(appFunctionData2.deserialize(qualifiedName));
                }
                return arrayList;
            }
            List<AppFunctionData> appFunctionDataList2 = appFunctionData.getAppFunctionDataList(str);
            if (appFunctionDataList2 == null) {
                return null;
            }
            List<AppFunctionData> list2 = appFunctionDataList2;
            ArrayList arrayList2 = new ArrayList(CollectionsKt.collectionSizeOrDefault(list2, 10));
            for (AppFunctionData appFunctionData3 : list2) {
                String qualifiedName2 = ((AppFunctionObjectTypeMetadata) itemType).getQualifiedName();
                if (qualifiedName2 == null) {
                    throw new IllegalStateException("Required value was null.".toString());
                }
                arrayList2.add(appFunctionData3.deserialize(qualifiedName2));
            }
            return arrayList2;
        }
        if (!(itemType instanceof AppFunctionReferenceTypeMetadata)) {
            throw new IllegalStateException("Unknown item DataTypeMetadata: " + itemType.getClass());
        }
        if (!z2 && !z) {
            List<AppFunctionData> appFunctionDataList3 = appFunctionData.getAppFunctionDataList(str);
            if (appFunctionDataList3 == null) {
                return CollectionsKt.emptyList();
            }
            List<AppFunctionData> list3 = appFunctionDataList3;
            ArrayList arrayList3 = new ArrayList(CollectionsKt.collectionSizeOrDefault(list3, 10));
            for (AppFunctionData appFunctionData4 : list3) {
                String referenceDataType = ((AppFunctionReferenceTypeMetadata) itemType).getReferenceDataType();
                if (referenceDataType == null) {
                    throw new IllegalStateException("Required value was null.".toString());
                }
                arrayList3.add(appFunctionData4.deserialize(referenceDataType));
            }
            return arrayList3;
        }
        List<AppFunctionData> appFunctionDataList4 = appFunctionData.getAppFunctionDataList(str);
        if (appFunctionDataList4 == null) {
            return null;
        }
        List<AppFunctionData> list4 = appFunctionDataList4;
        ArrayList arrayList4 = new ArrayList(CollectionsKt.collectionSizeOrDefault(list4, 10));
        for (AppFunctionData appFunctionData5 : list4) {
            String referenceDataType2 = ((AppFunctionReferenceTypeMetadata) itemType).getReferenceDataType();
            if (referenceDataType2 == null) {
                throw new IllegalStateException("Required value was null.".toString());
            }
            arrayList4.add(appFunctionData5.deserialize(referenceDataType2));
        }
        return arrayList4;
    }

    private static final Class<Parcelable> getParcelableClass(String str) {
        try {
            Class cls = Class.forName(str);
            if (!Parcelable.class.isAssignableFrom(cls)) {
                throw new IllegalStateException("Class '" + str + "' is not a Parcelable.");
            }
            Intrinsics.checkNotNull(cls, "null cannot be cast to non-null type java.lang.Class<android.os.Parcelable>");
            return cls;
        } catch (ClassNotFoundException e) {
            throw new IllegalStateException("Class '" + str + "' could not be found.", e);
        }
    }
}
