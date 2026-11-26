package androidx.appfunctions.service.internal;

import android.os.Parcelable;
import android.util.Log;
import androidx.appfunctions.AppFunctionAppUnknownException;
import androidx.appfunctions.AppFunctionData;
import androidx.appfunctions.ExecuteAppFunctionResponse;
import androidx.appfunctions.internal.Constants;
import androidx.appfunctions.metadata.AppFunctionAllOfTypeMetadata;
import androidx.appfunctions.metadata.AppFunctionArrayTypeMetadata;
import androidx.appfunctions.metadata.AppFunctionBooleanTypeMetadata;
import androidx.appfunctions.metadata.AppFunctionBytesTypeMetadata;
import androidx.appfunctions.metadata.AppFunctionComponentsMetadata;
import androidx.appfunctions.metadata.AppFunctionDataTypeMetadata;
import androidx.appfunctions.metadata.AppFunctionDoubleTypeMetadata;
import androidx.appfunctions.metadata.AppFunctionFloatTypeMetadata;
import androidx.appfunctions.metadata.AppFunctionIntTypeMetadata;
import androidx.appfunctions.metadata.AppFunctionLongTypeMetadata;
import androidx.appfunctions.metadata.AppFunctionObjectTypeMetadata;
import androidx.appfunctions.metadata.AppFunctionParcelableTypeMetadata;
import androidx.appfunctions.metadata.AppFunctionReferenceTypeMetadata;
import androidx.appfunctions.metadata.AppFunctionResponseMetadata;
import androidx.appfunctions.metadata.AppFunctionStringTypeMetadata;
import androidx.appfunctions.metadata.AppFunctionUnitTypeMetadata;
import java.util.ArrayList;
import java.util.List;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: AppFunctionResponseMetadataReturnValueBuilder.kt */
/* JADX INFO: loaded from: classes.dex */
@Metadata(d1 = {"\u0000(\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\u001a\u001e\u0010\u0000\u001a\u00020\u0001*\u00020\u00022\b\u0010\u0003\u001a\u0004\u0018\u00010\u00042\u0006\u0010\u0005\u001a\u00020\u0006H\u0001\u001a$\u0010\u0000\u001a\u00020\u0001*\u00020\u00072\u0006\u0010\u0003\u001a\u00020\u00042\u0006\u0010\b\u001a\u00020\u00022\u0006\u0010\u0005\u001a\u00020\u0006H\u0003\u001a\u001c\u0010\u0000\u001a\u00020\u0001*\u00020\t2\u0006\u0010\n\u001a\u00020\u000b2\u0006\u0010\u0003\u001a\u00020\u0004H\u0003¨\u0006\f"}, d2 = {"unsafeBuildReturnValue", "Landroidx/appfunctions/AppFunctionData;", "Landroidx/appfunctions/metadata/AppFunctionResponseMetadata;", "result", "", "componentsMetadata", "Landroidx/appfunctions/metadata/AppFunctionComponentsMetadata;", "Landroidx/appfunctions/metadata/AppFunctionDataTypeMetadata;", "responseMetadata", "Landroidx/appfunctions/metadata/AppFunctionArrayTypeMetadata;", "builder", "Landroidx/appfunctions/AppFunctionData$Builder;", "appfunctions-service"}, k = 2, mv = {2, 1, 0}, xi = 48)
public final class AppFunctionResponseMetadataReturnValueBuilderKt {
    public static final AppFunctionData unsafeBuildReturnValue(AppFunctionResponseMetadata appFunctionResponseMetadata, Object obj, AppFunctionComponentsMetadata componentsMetadata) throws AppFunctionAppUnknownException {
        Intrinsics.checkNotNullParameter(appFunctionResponseMetadata, "<this>");
        Intrinsics.checkNotNullParameter(componentsMetadata, "componentsMetadata");
        try {
            if (obj == null) {
                if (!appFunctionResponseMetadata.getValueType().getIsNullable()) {
                    throw new IllegalStateException("Unexpected null for non-null return type".toString());
                }
                return AppFunctionData.EMPTY;
            }
            return unsafeBuildReturnValue(appFunctionResponseMetadata.getValueType(), obj, appFunctionResponseMetadata, componentsMetadata);
        } catch (Exception e) {
            Log.d(Constants.APP_FUNCTIONS_TAG, "Something went wrong when building the return value", e);
            throw new AppFunctionAppUnknownException("Something went wrong when executing an app function");
        }
    }

    private static final AppFunctionData unsafeBuildReturnValue(AppFunctionDataTypeMetadata appFunctionDataTypeMetadata, Object obj, AppFunctionResponseMetadata appFunctionResponseMetadata, AppFunctionComponentsMetadata appFunctionComponentsMetadata) {
        AppFunctionData.Builder builder = new AppFunctionData.Builder(appFunctionResponseMetadata, appFunctionComponentsMetadata);
        if (appFunctionDataTypeMetadata instanceof AppFunctionUnitTypeMetadata) {
            return AppFunctionData.EMPTY;
        }
        if (appFunctionDataTypeMetadata instanceof AppFunctionLongTypeMetadata) {
            Intrinsics.checkNotNull(obj, "null cannot be cast to non-null type kotlin.Long");
            return builder.setLong(ExecuteAppFunctionResponse.Success.PROPERTY_RETURN_VALUE, ((Long) obj).longValue()).build();
        }
        if (appFunctionDataTypeMetadata instanceof AppFunctionIntTypeMetadata) {
            Intrinsics.checkNotNull(obj, "null cannot be cast to non-null type kotlin.Int");
            return builder.setInt(ExecuteAppFunctionResponse.Success.PROPERTY_RETURN_VALUE, ((Integer) obj).intValue()).build();
        }
        if (appFunctionDataTypeMetadata instanceof AppFunctionDoubleTypeMetadata) {
            Intrinsics.checkNotNull(obj, "null cannot be cast to non-null type kotlin.Double");
            return builder.setDouble(ExecuteAppFunctionResponse.Success.PROPERTY_RETURN_VALUE, ((Double) obj).doubleValue()).build();
        }
        if (appFunctionDataTypeMetadata instanceof AppFunctionFloatTypeMetadata) {
            Intrinsics.checkNotNull(obj, "null cannot be cast to non-null type kotlin.Float");
            return builder.setFloat(ExecuteAppFunctionResponse.Success.PROPERTY_RETURN_VALUE, ((Float) obj).floatValue()).build();
        }
        if (appFunctionDataTypeMetadata instanceof AppFunctionBooleanTypeMetadata) {
            Intrinsics.checkNotNull(obj, "null cannot be cast to non-null type kotlin.Boolean");
            return builder.setBoolean(ExecuteAppFunctionResponse.Success.PROPERTY_RETURN_VALUE, ((Boolean) obj).booleanValue()).build();
        }
        if (appFunctionDataTypeMetadata instanceof AppFunctionStringTypeMetadata) {
            Intrinsics.checkNotNull(obj, "null cannot be cast to non-null type kotlin.String");
            return builder.setString(ExecuteAppFunctionResponse.Success.PROPERTY_RETURN_VALUE, (String) obj).build();
        }
        if (appFunctionDataTypeMetadata instanceof AppFunctionBytesTypeMetadata) {
            throw new IllegalStateException("Type of a single byte is not supported");
        }
        if (appFunctionDataTypeMetadata instanceof AppFunctionParcelableTypeMetadata) {
            Intrinsics.checkNotNull(obj, "null cannot be cast to non-null type android.os.Parcelable");
            return builder.setParcelable(ExecuteAppFunctionResponse.Success.PROPERTY_RETURN_VALUE, (Parcelable) obj).build();
        }
        if (appFunctionDataTypeMetadata instanceof AppFunctionObjectTypeMetadata) {
            AppFunctionData.Companion companion = AppFunctionData.INSTANCE;
            String qualifiedName = ((AppFunctionObjectTypeMetadata) appFunctionDataTypeMetadata).getQualifiedName();
            if (qualifiedName == null) {
                throw new IllegalStateException("Required value was null.".toString());
            }
            return builder.setAppFunctionData(ExecuteAppFunctionResponse.Success.PROPERTY_RETURN_VALUE, companion.serialize(obj, qualifiedName)).build();
        }
        if (appFunctionDataTypeMetadata instanceof AppFunctionAllOfTypeMetadata) {
            AppFunctionData.Companion companion2 = AppFunctionData.INSTANCE;
            String qualifiedName2 = ((AppFunctionAllOfTypeMetadata) appFunctionDataTypeMetadata).getQualifiedName();
            if (qualifiedName2 == null) {
                throw new IllegalStateException("Required value was null.".toString());
            }
            return builder.setAppFunctionData(ExecuteAppFunctionResponse.Success.PROPERTY_RETURN_VALUE, companion2.serialize(obj, qualifiedName2)).build();
        }
        if (appFunctionDataTypeMetadata instanceof AppFunctionReferenceTypeMetadata) {
            AppFunctionData.Companion companion3 = AppFunctionData.INSTANCE;
            String referenceDataType = ((AppFunctionReferenceTypeMetadata) appFunctionDataTypeMetadata).getReferenceDataType();
            if (referenceDataType == null) {
                throw new IllegalStateException("Required value was null.".toString());
            }
            return builder.setAppFunctionData(ExecuteAppFunctionResponse.Success.PROPERTY_RETURN_VALUE, companion3.serialize(obj, referenceDataType)).build();
        }
        if (appFunctionDataTypeMetadata instanceof AppFunctionArrayTypeMetadata) {
            return unsafeBuildReturnValue((AppFunctionArrayTypeMetadata) appFunctionDataTypeMetadata, builder, obj);
        }
        throw new IllegalStateException("Unknown DataTypeMetadata: " + appFunctionDataTypeMetadata.getClass());
    }

    private static final AppFunctionData unsafeBuildReturnValue(AppFunctionArrayTypeMetadata appFunctionArrayTypeMetadata, AppFunctionData.Builder builder, Object obj) {
        AppFunctionDataTypeMetadata itemType = appFunctionArrayTypeMetadata.getItemType();
        if (itemType instanceof AppFunctionLongTypeMetadata) {
            Intrinsics.checkNotNull(obj, "null cannot be cast to non-null type kotlin.LongArray");
            return builder.setLongArray(ExecuteAppFunctionResponse.Success.PROPERTY_RETURN_VALUE, (long[]) obj).build();
        }
        if (itemType instanceof AppFunctionIntTypeMetadata) {
            Intrinsics.checkNotNull(obj, "null cannot be cast to non-null type kotlin.IntArray");
            return builder.setIntArray(ExecuteAppFunctionResponse.Success.PROPERTY_RETURN_VALUE, (int[]) obj).build();
        }
        if (itemType instanceof AppFunctionDoubleTypeMetadata) {
            Intrinsics.checkNotNull(obj, "null cannot be cast to non-null type kotlin.DoubleArray");
            return builder.setDoubleArray(ExecuteAppFunctionResponse.Success.PROPERTY_RETURN_VALUE, (double[]) obj).build();
        }
        if (itemType instanceof AppFunctionFloatTypeMetadata) {
            Intrinsics.checkNotNull(obj, "null cannot be cast to non-null type kotlin.FloatArray");
            return builder.setFloatArray(ExecuteAppFunctionResponse.Success.PROPERTY_RETURN_VALUE, (float[]) obj).build();
        }
        if (itemType instanceof AppFunctionBooleanTypeMetadata) {
            Intrinsics.checkNotNull(obj, "null cannot be cast to non-null type kotlin.BooleanArray");
            return builder.setBooleanArray(ExecuteAppFunctionResponse.Success.PROPERTY_RETURN_VALUE, (boolean[]) obj).build();
        }
        if (itemType instanceof AppFunctionStringTypeMetadata) {
            Intrinsics.checkNotNull(obj, "null cannot be cast to non-null type kotlin.collections.List<kotlin.String>");
            return builder.setStringList(ExecuteAppFunctionResponse.Success.PROPERTY_RETURN_VALUE, (List) obj).build();
        }
        if (itemType instanceof AppFunctionBytesTypeMetadata) {
            Intrinsics.checkNotNull(obj, "null cannot be cast to non-null type kotlin.ByteArray");
            return builder.setByteArray(ExecuteAppFunctionResponse.Success.PROPERTY_RETURN_VALUE, (byte[]) obj).build();
        }
        if (itemType instanceof AppFunctionParcelableTypeMetadata) {
            Intrinsics.checkNotNull(obj, "null cannot be cast to non-null type kotlin.collections.List<android.os.Parcelable>");
            return builder.setParcelableList(ExecuteAppFunctionResponse.Success.PROPERTY_RETURN_VALUE, (List) obj, Parcelable.class).build();
        }
        if (itemType instanceof AppFunctionObjectTypeMetadata) {
            Intrinsics.checkNotNull(obj, "null cannot be cast to non-null type kotlin.collections.List<kotlin.Any>");
            List list = (List) obj;
            ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(list, 10));
            for (Object obj2 : list) {
                AppFunctionData.Companion companion = AppFunctionData.INSTANCE;
                String qualifiedName = ((AppFunctionObjectTypeMetadata) itemType).getQualifiedName();
                if (qualifiedName == null) {
                    throw new IllegalStateException("Required value was null.".toString());
                }
                arrayList.add(companion.serialize(obj2, qualifiedName));
            }
            return builder.setAppFunctionDataList(ExecuteAppFunctionResponse.Success.PROPERTY_RETURN_VALUE, arrayList).build();
        }
        if (itemType instanceof AppFunctionAllOfTypeMetadata) {
            Intrinsics.checkNotNull(obj, "null cannot be cast to non-null type kotlin.collections.List<kotlin.Any>");
            List list2 = (List) obj;
            ArrayList arrayList2 = new ArrayList(CollectionsKt.collectionSizeOrDefault(list2, 10));
            for (Object obj3 : list2) {
                AppFunctionData.Companion companion2 = AppFunctionData.INSTANCE;
                String qualifiedName2 = ((AppFunctionAllOfTypeMetadata) itemType).getQualifiedName();
                if (qualifiedName2 == null) {
                    throw new IllegalStateException("Required value was null.".toString());
                }
                arrayList2.add(companion2.serialize(obj3, qualifiedName2));
            }
            return builder.setAppFunctionDataList(ExecuteAppFunctionResponse.Success.PROPERTY_RETURN_VALUE, arrayList2).build();
        }
        if (itemType instanceof AppFunctionReferenceTypeMetadata) {
            Intrinsics.checkNotNull(obj, "null cannot be cast to non-null type kotlin.collections.List<kotlin.Any>");
            List list3 = (List) obj;
            ArrayList arrayList3 = new ArrayList(CollectionsKt.collectionSizeOrDefault(list3, 10));
            for (Object obj4 : list3) {
                AppFunctionData.Companion companion3 = AppFunctionData.INSTANCE;
                String referenceDataType = ((AppFunctionReferenceTypeMetadata) itemType).getReferenceDataType();
                if (referenceDataType == null) {
                    throw new IllegalStateException("Required value was null.".toString());
                }
                arrayList3.add(companion3.serialize(obj4, referenceDataType));
            }
            return builder.setAppFunctionDataList(ExecuteAppFunctionResponse.Success.PROPERTY_RETURN_VALUE, arrayList3).build();
        }
        throw new IllegalStateException("Unknown item DataTypeMetadata: " + itemType.getClass());
    }
}
