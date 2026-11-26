package androidx.appfunctions;

import android.app.appsearch.GenericDocument;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcelable;
import android.os.ext.SdkExtensions;
import android.util.Log;
import androidx.appfunctions.internal.AppFunctionSerializableFactory;
import androidx.appfunctions.internal.Constants;
import androidx.appfunctions.metadata.AppFunctionAllOfTypeMetadata;
import androidx.appfunctions.metadata.AppFunctionComponentsMetadata;
import androidx.appfunctions.metadata.AppFunctionMetadataKt;
import androidx.appfunctions.metadata.AppFunctionObjectTypeMetadata;
import androidx.appfunctions.metadata.AppFunctionParameterMetadata;
import androidx.appfunctions.metadata.AppFunctionResponseMetadata;
import androidx.collection.SieveCacheKt;
import androidx.exifinterface.media.ExifInterface;
import ch.qos.logback.core.CoreConstants;
import com.market.sdk.reflect.ReflectUtilsForMiui;
import com.xiaomi.accounts.secure.SecureDatabaseHelper;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.collections.ArraysKt;
import kotlin.collections.CollectionsKt;
import kotlin.collections.SetsKt;
import kotlin.jvm.JvmStatic;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Reflection;
import kotlin.text.StringsKt;

/* JADX INFO: compiled from: AppFunctionData.kt */
/* JADX INFO: loaded from: classes.dex */
@Metadata(d1 = {"\u0000¶\u0001\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\n\n\u0002\u0010\u000e\n\u0002\b\u0005\n\u0002\u0010\u000b\n\u0002\b\u0006\n\u0002\u0010\u0007\n\u0002\b\u0003\n\u0002\u0010\u0006\n\u0002\b\u0003\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0010\t\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u0018\n\u0000\n\u0002\u0010\u0014\n\u0000\n\u0002\u0010\u0013\n\u0000\n\u0002\u0010\u0015\n\u0000\n\u0002\u0010\u0016\n\u0000\n\u0002\u0010\u0012\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\b\u0010\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\b\u0007\u0018\u0000 f2\u00020\u0001:\u0002efB#\b\u0000\u0012\b\u0010\u0002\u001a\u0004\u0018\u00010\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007¢\u0006\u0004\b\b\u0010\tB\u0019\b\u0010\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007¢\u0006\u0004\b\b\u0010\nJ\u000e\u0010\u0017\u001a\u00020\u00182\u0006\u0010\u0019\u001a\u00020\u0012J\u000e\u0010\u001a\u001a\u00020\u00182\u0006\u0010\u0019\u001a\u00020\u0012J\u0016\u0010\u001a\u001a\u00020\u00182\u0006\u0010\u0019\u001a\u00020\u00122\u0006\u0010\u001b\u001a\u00020\u0018J\u0017\u0010\u001c\u001a\u0004\u0018\u00010\u00182\u0006\u0010\u0019\u001a\u00020\u0012H\u0007¢\u0006\u0002\u0010\u001dJ\u000e\u0010\u001e\u001a\u00020\u001f2\u0006\u0010\u0019\u001a\u00020\u0012J\u0016\u0010\u001e\u001a\u00020\u001f2\u0006\u0010\u0019\u001a\u00020\u00122\u0006\u0010\u001b\u001a\u00020\u001fJ\u0017\u0010 \u001a\u0004\u0018\u00010\u001f2\u0006\u0010\u0019\u001a\u00020\u0012H\u0007¢\u0006\u0002\u0010!J\u000e\u0010\"\u001a\u00020#2\u0006\u0010\u0019\u001a\u00020\u0012J\u0016\u0010\"\u001a\u00020#2\u0006\u0010\u0019\u001a\u00020\u00122\u0006\u0010\u001b\u001a\u00020#J\u0017\u0010$\u001a\u0004\u0018\u00010#2\u0006\u0010\u0019\u001a\u00020\u0012H\u0007¢\u0006\u0002\u0010%J\u000e\u0010&\u001a\u00020'2\u0006\u0010\u0019\u001a\u00020\u0012J\u0016\u0010&\u001a\u00020'2\u0006\u0010\u0019\u001a\u00020\u00122\u0006\u0010\u001b\u001a\u00020'J\u0017\u0010(\u001a\u0004\u0018\u00010'2\u0006\u0010\u0019\u001a\u00020\u0012H\u0007¢\u0006\u0002\u0010)J\u000e\u0010*\u001a\u00020+2\u0006\u0010\u0019\u001a\u00020\u0012J\u0016\u0010*\u001a\u00020+2\u0006\u0010\u0019\u001a\u00020\u00122\u0006\u0010\u001b\u001a\u00020+J\u0017\u0010,\u001a\u0004\u0018\u00010+2\u0006\u0010\u0019\u001a\u00020\u0012H\u0007¢\u0006\u0002\u0010-J\u0010\u0010.\u001a\u0004\u0018\u00010\u00122\u0006\u0010\u0019\u001a\u00020\u0012J\u0012\u0010/\u001a\u0004\u0018\u00010\u00122\u0006\u0010\u0019\u001a\u00020\u0012H\u0007J\u0010\u00100\u001a\u0004\u0018\u00010\u00002\u0006\u0010\u0019\u001a\u00020\u0012J-\u00101\u001a\u0004\u0018\u0001H2\"\b\b\u0000\u00102*\u0002032\u0006\u0010\u0019\u001a\u00020\u00122\f\u00104\u001a\b\u0012\u0004\u0012\u0002H205¢\u0006\u0002\u00106J$\u00101\u001a\u0004\u0018\u0001H2\"\n\b\u0000\u00102\u0018\u0001*\u0002032\u0006\u0010\u0019\u001a\u00020\u0012H\u0086\b¢\u0006\u0002\u00107J/\u00108\u001a\u0004\u0018\u0001H2\"\b\b\u0000\u00102*\u0002032\u0006\u0010\u0019\u001a\u00020\u00122\f\u00104\u001a\b\u0012\u0004\u0012\u0002H205H\u0007¢\u0006\u0002\u00106J\u0010\u00109\u001a\u0004\u0018\u00010:2\u0006\u0010\u0019\u001a\u00020\u0012J\u0010\u0010;\u001a\u0004\u0018\u00010<2\u0006\u0010\u0019\u001a\u00020\u0012J\u0010\u0010=\u001a\u0004\u0018\u00010>2\u0006\u0010\u0019\u001a\u00020\u0012J\u0010\u0010?\u001a\u0004\u0018\u00010@2\u0006\u0010\u0019\u001a\u00020\u0012J\u0010\u0010A\u001a\u0004\u0018\u00010B2\u0006\u0010\u0019\u001a\u00020\u0012J\u0010\u0010C\u001a\u0004\u0018\u00010D2\u0006\u0010\u0019\u001a\u00020\u0012J\u0012\u0010E\u001a\u0004\u0018\u00010D2\u0006\u0010\u0019\u001a\u00020\u0012H\u0007J\u0016\u0010F\u001a\n\u0012\u0004\u0012\u00020\u0012\u0018\u00010G2\u0006\u0010\u0019\u001a\u00020\u0012J\u0016\u0010H\u001a\n\u0012\u0004\u0012\u00020\u0000\u0018\u00010G2\u0006\u0010\u0019\u001a\u00020\u0012J.\u0010I\u001a\n\u0012\u0004\u0012\u0002H2\u0018\u00010G\"\b\b\u0000\u00102*\u0002032\u0006\u0010\u0019\u001a\u00020\u00122\f\u00104\u001a\b\u0012\u0004\u0012\u0002H205J%\u0010I\u001a\n\u0012\u0004\u0012\u0002H2\u0018\u00010G\"\n\b\u0000\u00102\u0018\u0001*\u0002032\u0006\u0010\u0019\u001a\u00020\u0012H\u0086\bJ\b\u0010J\u001a\u00020\u0012H\u0016J\u0010\u0010K\u001a\u00020\u00182\u0006\u0010L\u001a\u00020#H\u0002J\u0010\u0010M\u001a\u00020\u00182\u0006\u0010N\u001a\u00020+H\u0002J#\u0010O\u001a\u0002H2\"\b\b\u0000\u00102*\u00020\u00012\f\u0010P\u001a\b\u0012\u0004\u0012\u0002H205¢\u0006\u0002\u0010QJ\u001f\u0010O\u001a\u0002H2\"\b\b\u0000\u00102*\u00020\u00012\u0006\u0010\u0011\u001a\u00020\u0012H\u0007¢\u0006\u0002\u0010RJ/\u0010S\u001a\u0004\u0018\u0001H2\"\b\b\u0000\u00102*\u00020\u00012\u0006\u0010\u0019\u001a\u00020\u00122\f\u0010T\u001a\b\u0012\u0004\u0012\u0002H205H\u0002¢\u0006\u0002\u0010UJ\u001d\u0010V\u001a\u00020\u00002\u0006\u0010W\u001a\u00020X2\u0006\u0010Y\u001a\u00020ZH\u0000¢\u0006\u0002\b[J\u001d\u0010V\u001a\u00020\u00002\u0006\u0010\\\u001a\u00020]2\u0006\u0010Y\u001a\u00020ZH\u0000¢\u0006\u0002\b[J#\u0010V\u001a\u00020\u00002\f\u0010^\u001a\b\u0012\u0004\u0012\u00020_0G2\u0006\u0010Y\u001a\u00020ZH\u0000¢\u0006\u0002\b[J\u001c\u0010`\u001a\u00020a2\u0012\u0010b\u001a\u000e\u0012\u0004\u0012\u00020d\u0012\u0004\u0012\u00020a0cH\u0007R\u0016\u0010\u0002\u001a\u0004\u0018\u00010\u0003X\u0080\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\fR\u0014\u0010\u0004\u001a\u00020\u0005X\u0080\u0004¢\u0006\b\n\u0000\u001a\u0004\b\r\u0010\u000eR\u0014\u0010\u0006\u001a\u00020\u0007X\u0080\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u000f\u0010\u0010R\u0011\u0010\u0011\u001a\u00020\u00128F¢\u0006\u0006\u001a\u0004\b\u0013\u0010\u0014R\u0013\u0010\u0015\u001a\u00020\u00128\u0007¢\u0006\b\n\u0000\u001a\u0004\b\u0016\u0010\u0014¨\u0006g"}, d2 = {"Landroidx/appfunctions/AppFunctionData;", "", "spec", "Landroidx/appfunctions/AppFunctionDataSpec;", "genericDocument", "Landroid/app/appsearch/GenericDocument;", SecureDatabaseHelper.TABLE_EXTRAS, "Landroid/os/Bundle;", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "(Landroidx/appfunctions/AppFunctionDataSpec;Landroid/app/appsearch/GenericDocument;Landroid/os/Bundle;)V", "(Landroid/app/appsearch/GenericDocument;Landroid/os/Bundle;)V", "getSpec$appfunctions", "()Landroidx/appfunctions/AppFunctionDataSpec;", "getGenericDocument$appfunctions", "()Landroid/app/appsearch/GenericDocument;", "getExtras$appfunctions", "()Landroid/os/Bundle;", "qualifiedName", "", "getQualifiedName", "()Ljava/lang/String;", "id", "getId", "containsKey", "", "key", "getBoolean", "defaultValue", "getBooleanOrNull", "(Ljava/lang/String;)Ljava/lang/Boolean;", "getFloat", "", "getFloatOrNull", "(Ljava/lang/String;)Ljava/lang/Float;", "getDouble", "", "getDoubleOrNull", "(Ljava/lang/String;)Ljava/lang/Double;", "getInt", "", "getIntOrNull", "(Ljava/lang/String;)Ljava/lang/Integer;", "getLong", "", "getLongOrNull", "(Ljava/lang/String;)Ljava/lang/Long;", "getString", "getStringOrNull", "getAppFunctionData", "getParcelable", ExifInterface.GPS_DIRECTION_TRUE, "Landroid/os/Parcelable;", "clazz", "Ljava/lang/Class;", "(Ljava/lang/String;Ljava/lang/Class;)Landroid/os/Parcelable;", "(Ljava/lang/String;)Landroid/os/Parcelable;", "getParcelableOrNull", "getBooleanArray", "", "getFloatArray", "", "getDoubleArray", "", "getIntArray", "", "getLongArray", "", "getByteArray", "", "getByteArrayOrNull", "getStringList", "", "getAppFunctionDataList", "getParcelableList", "toString", "isDoubleWithinFloatRange", "doubleValue", "isLongWithinLongRange", "longValue", "deserialize", "serializableClass", "(Ljava/lang/Class;)Ljava/lang/Object;", "(Ljava/lang/String;)Ljava/lang/Object;", "unsafeGetProperty", "arrayClass", "(Ljava/lang/String;Ljava/lang/Class;)Ljava/lang/Object;", "replaceSpecWith", "responseMetadata", "Landroidx/appfunctions/metadata/AppFunctionResponseMetadata;", "componentMetadata", "Landroidx/appfunctions/metadata/AppFunctionComponentsMetadata;", "replaceSpecWith$appfunctions", "objectTypeMetadata", "Landroidx/appfunctions/metadata/AppFunctionObjectTypeMetadata;", "parameterMetadata", "Landroidx/appfunctions/metadata/AppFunctionParameterMetadata;", "visitAppFunctionUriGrants", "", "visitor", "Lkotlin/Function1;", "Landroidx/appfunctions/AppFunctionUriGrant;", "Builder", "Companion", AppFunctionMetadataKt.APP_FUNCTION_NAMESPACE}, k = 1, mv = {2, 1, 0}, xi = 48)
public final class AppFunctionData {

    /* JADX INFO: renamed from: Companion, reason: from kotlin metadata */
    public static final Companion INSTANCE = new Companion(null);
    private static final boolean DEFAULT_BOOLEAN = false;
    private static final double DEFAULT_DOUBLE = 0.0d;
    private static final float DEFAULT_FLOAT = 0.0f;
    private static final int DEFAULT_INT = 0;
    private static final long DEFAULT_LONG = 0;
    public static final AppFunctionData EMPTY;
    private static final String LEGACY_ID_FIELD_KEY = "id";
    private final Bundle extras;
    private final GenericDocument genericDocument;
    private final String id;
    private final AppFunctionDataSpec spec;

    private final boolean isLongWithinLongRange(long longValue) {
        return longValue >= SieveCacheKt.NodeMetaAndPreviousMask && longValue <= SieveCacheKt.NodeLinkMask;
    }

    @JvmStatic
    public static final <T> AppFunctionData serialize(T t, Class<T> cls) {
        return INSTANCE.serialize(t, cls);
    }

    public AppFunctionData(AppFunctionDataSpec appFunctionDataSpec, GenericDocument genericDocument, Bundle extras) {
        Intrinsics.checkNotNullParameter(genericDocument, "genericDocument");
        Intrinsics.checkNotNullParameter(extras, "extras");
        this.spec = appFunctionDataSpec;
        this.genericDocument = genericDocument;
        this.extras = extras;
        extras.setClassLoader(AppFunctionData.class.getClassLoader());
        String id = genericDocument.getId();
        Intrinsics.checkNotNullExpressionValue(id, "getId(...)");
        this.id = id;
    }

    /* JADX INFO: renamed from: getSpec$appfunctions, reason: from getter */
    public final AppFunctionDataSpec getSpec() {
        return this.spec;
    }

    /* JADX INFO: renamed from: getGenericDocument$appfunctions, reason: from getter */
    public final GenericDocument getGenericDocument() {
        return this.genericDocument;
    }

    /* JADX INFO: renamed from: getExtras$appfunctions, reason: from getter */
    public final Bundle getExtras() {
        return this.extras;
    }

    /* JADX WARN: 'this' call moved to the top of the method (can break code semantics) */
    public AppFunctionData(GenericDocument genericDocument, Bundle extras) {
        this(null, genericDocument, extras);
        Intrinsics.checkNotNullParameter(genericDocument, "genericDocument");
        Intrinsics.checkNotNullParameter(extras, "extras");
    }

    public final String getQualifiedName() {
        String schemaType = this.genericDocument.getSchemaType();
        Intrinsics.checkNotNullExpressionValue(schemaType, "getSchemaType(...)");
        return schemaType;
    }

    public final String getId() {
        return this.id;
    }

    public final boolean containsKey(String key) {
        Intrinsics.checkNotNullParameter(key, "key");
        AppFunctionDataSpec appFunctionDataSpec = this.spec;
        if (appFunctionDataSpec == null || appFunctionDataSpec.containsMetadata(key)) {
            return Intrinsics.areEqual(key, "id") || this.genericDocument.getProperty(key) != null || this.extras.containsKey(key) || this.extras.containsKey(INSTANCE.extrasKey(key));
        }
        throw new IllegalArgumentException("There is no metadata associated with " + key);
    }

    public final boolean getBoolean(String key) {
        Intrinsics.checkNotNullParameter(key, "key");
        return getBoolean(key, false);
    }

    public final boolean getBoolean(String key, boolean defaultValue) {
        Intrinsics.checkNotNullParameter(key, "key");
        Boolean booleanOrNull = getBooleanOrNull(key);
        return booleanOrNull != null ? booleanOrNull.booleanValue() : defaultValue;
    }

    public final Boolean getBooleanOrNull(String key) {
        Intrinsics.checkNotNullParameter(key, "key");
        boolean[] zArr = (boolean[]) unsafeGetProperty(key, boolean[].class);
        Boolean boolValueOf = (zArr == null || zArr.length == 0) ? null : Boolean.valueOf(zArr[0]);
        AppFunctionDataSpec appFunctionDataSpec = this.spec;
        if (appFunctionDataSpec != null) {
            appFunctionDataSpec.validateReadRequest(key, Boolean.TYPE, false, boolValueOf);
        }
        return boolValueOf;
    }

    public final float getFloat(String key) {
        Intrinsics.checkNotNullParameter(key, "key");
        return getFloat(key, 0.0f);
    }

    public final float getFloat(String key, float defaultValue) {
        Intrinsics.checkNotNullParameter(key, "key");
        Float floatOrNull = getFloatOrNull(key);
        return floatOrNull != null ? floatOrNull.floatValue() : defaultValue;
    }

    public final Float getFloatOrNull(String key) {
        Intrinsics.checkNotNullParameter(key, "key");
        double[] dArr = (double[]) unsafeGetProperty(key, double[].class);
        Double dValueOf = (dArr == null || dArr.length == 0) ? null : Double.valueOf(dArr[0]);
        AppFunctionDataSpec appFunctionDataSpec = this.spec;
        if (appFunctionDataSpec != null) {
            appFunctionDataSpec.validateReadRequest(key, Float.TYPE, false, dValueOf);
        }
        if (dValueOf != null && !isDoubleWithinFloatRange(dValueOf.doubleValue())) {
            throw new IllegalStateException("The value associated with " + key + " is not within the range of Float");
        }
        if (dValueOf != null) {
            return Float.valueOf((float) dValueOf.doubleValue());
        }
        return null;
    }

    public final double getDouble(String key) {
        Intrinsics.checkNotNullParameter(key, "key");
        return getDouble(key, DEFAULT_DOUBLE);
    }

    public final double getDouble(String key, double defaultValue) {
        Intrinsics.checkNotNullParameter(key, "key");
        Double doubleOrNull = getDoubleOrNull(key);
        return doubleOrNull != null ? doubleOrNull.doubleValue() : defaultValue;
    }

    public final Double getDoubleOrNull(String key) {
        Intrinsics.checkNotNullParameter(key, "key");
        double[] dArr = (double[]) unsafeGetProperty(key, double[].class);
        Double dValueOf = (dArr == null || dArr.length == 0) ? null : Double.valueOf(dArr[0]);
        AppFunctionDataSpec appFunctionDataSpec = this.spec;
        if (appFunctionDataSpec != null) {
            appFunctionDataSpec.validateReadRequest(key, Double.TYPE, false, dValueOf);
        }
        return dValueOf;
    }

    public final int getInt(String key) {
        Intrinsics.checkNotNullParameter(key, "key");
        return getInt(key, 0);
    }

    public final int getInt(String key, int defaultValue) {
        Intrinsics.checkNotNullParameter(key, "key");
        Integer intOrNull = getIntOrNull(key);
        return intOrNull != null ? intOrNull.intValue() : defaultValue;
    }

    public final Integer getIntOrNull(String key) {
        Intrinsics.checkNotNullParameter(key, "key");
        long[] jArr = (long[]) unsafeGetProperty(key, long[].class);
        Long lValueOf = (jArr == null || jArr.length == 0) ? null : Long.valueOf(jArr[0]);
        if (lValueOf != null && !isLongWithinLongRange(lValueOf.longValue())) {
            throw new IllegalStateException("The value associated with " + key + " is not within the range of Int");
        }
        Integer numValueOf = lValueOf != null ? Integer.valueOf((int) lValueOf.longValue()) : null;
        AppFunctionDataSpec appFunctionDataSpec = this.spec;
        if (appFunctionDataSpec != null) {
            appFunctionDataSpec.validateReadRequest(key, Integer.TYPE, false, numValueOf);
        }
        return numValueOf;
    }

    public final long getLong(String key) {
        Intrinsics.checkNotNullParameter(key, "key");
        return getLong(key, 0L);
    }

    public final long getLong(String key, long defaultValue) {
        Intrinsics.checkNotNullParameter(key, "key");
        Long longOrNull = getLongOrNull(key);
        return longOrNull != null ? longOrNull.longValue() : defaultValue;
    }

    public final Long getLongOrNull(String key) {
        Intrinsics.checkNotNullParameter(key, "key");
        long[] jArr = (long[]) unsafeGetProperty(key, long[].class);
        Long lValueOf = (jArr == null || jArr.length == 0) ? null : Long.valueOf(jArr[0]);
        AppFunctionDataSpec appFunctionDataSpec = this.spec;
        if (appFunctionDataSpec != null) {
            appFunctionDataSpec.validateReadRequest(key, Long.TYPE, false, lValueOf);
        }
        return lValueOf;
    }

    public final String getString(String key) {
        Intrinsics.checkNotNullParameter(key, "key");
        return getStringOrNull(key);
    }

    public final String getStringOrNull(String key) {
        Intrinsics.checkNotNullParameter(key, "key");
        String[] strArr = (String[]) unsafeGetProperty(key, String[].class);
        String str = null;
        if (Intrinsics.areEqual(key, "id")) {
            String str2 = this.id;
            if (str2.length() == 0) {
                if (strArr != null) {
                    if (strArr.length == 0) {
                        strArr = null;
                    }
                    if (strArr != null) {
                        str = strArr[0];
                    }
                }
                str2 = str;
            }
            str = str2;
        } else if (strArr != null && strArr.length != 0) {
            str = strArr[0];
        }
        AppFunctionDataSpec appFunctionDataSpec = this.spec;
        if (appFunctionDataSpec != null) {
            appFunctionDataSpec.validateReadRequest(key, String.class, false, str);
        }
        return str;
    }

    /* JADX WARN: Type inference fix 'apply assigned field type' failed
    java.lang.UnsupportedOperationException: ArgType.getObject(), call class: class jadx.core.dex.instructions.args.ArgType$UnknownArg
    	at jadx.core.dex.instructions.args.ArgType.getObject(ArgType.java:596)
    	at jadx.core.dex.attributes.nodes.ClassTypeVarsAttr.getTypeVarsMapFor(ClassTypeVarsAttr.java:35)
    	at jadx.core.dex.nodes.utils.TypeUtils.replaceClassGenerics(TypeUtils.java:177)
    	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.insertExplicitUseCast(FixTypesVisitor.java:397)
    	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.tryFieldTypeWithNewCasts(FixTypesVisitor.java:359)
    	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.applyFieldType(FixTypesVisitor.java:309)
    	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.visit(FixTypesVisitor.java:94)
     */
    public final AppFunctionData getAppFunctionData(String key) {
        Intrinsics.checkNotNullParameter(key, "key");
        GenericDocument[] genericDocumentArr = (GenericDocument[]) unsafeGetProperty(key, GenericDocument[].class);
        AppFunctionData appFunctionData = null;
        AppFunctionDataSpec propertyObjectSpec = null;
        appFunctionData = null;
        if (genericDocumentArr != null && genericDocumentArr.length != 0) {
            AppFunctionDataSpec appFunctionDataSpec = this.spec;
            if (appFunctionDataSpec != null) {
                String schemaType = genericDocumentArr[0].getSchemaType();
                Intrinsics.checkNotNullExpressionValue(schemaType, "getSchemaType(...)");
                propertyObjectSpec = appFunctionDataSpec.getPropertyObjectSpec(key, schemaType);
            }
            GenericDocument genericDocument = genericDocumentArr[0];
            Bundle bundle = this.extras.getBundle(INSTANCE.extrasKey(key));
            if (bundle == null) {
                bundle = Bundle.EMPTY;
            }
            Intrinsics.checkNotNull(bundle);
            appFunctionData = new AppFunctionData(propertyObjectSpec, genericDocument, bundle);
        }
        AppFunctionDataSpec appFunctionDataSpec2 = this.spec;
        if (appFunctionDataSpec2 != null) {
            appFunctionDataSpec2.validateReadRequest(key, AppFunctionData.class, false, appFunctionData);
        }
        return appFunctionData;
    }

    public final <T extends Parcelable> T getParcelable(String key, Class<T> clazz) {
        Intrinsics.checkNotNullParameter(key, "key");
        Intrinsics.checkNotNullParameter(clazz, "clazz");
        return (T) getParcelableOrNull(key, clazz);
    }

    public final /* synthetic */ <T extends Parcelable> T getParcelable(String key) {
        Intrinsics.checkNotNullParameter(key, "key");
        Intrinsics.reifiedOperationMarker(4, ExifInterface.GPS_DIRECTION_TRUE);
        return (T) getParcelable(key, Parcelable.class);
    }

    /* JADX WARN: Type inference fix 'apply assigned field type' failed
    java.lang.UnsupportedOperationException: ArgType.getObject(), call class: class jadx.core.dex.instructions.args.ArgType$UnknownArg
    	at jadx.core.dex.instructions.args.ArgType.getObject(ArgType.java:596)
    	at jadx.core.dex.attributes.nodes.ClassTypeVarsAttr.getTypeVarsMapFor(ClassTypeVarsAttr.java:35)
    	at jadx.core.dex.nodes.utils.TypeUtils.replaceClassGenerics(TypeUtils.java:177)
    	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.insertExplicitUseCast(FixTypesVisitor.java:397)
    	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.tryFieldTypeWithNewCasts(FixTypesVisitor.java:359)
    	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.applyFieldType(FixTypesVisitor.java:309)
    	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.visit(FixTypesVisitor.java:94)
     */
    public final <T extends Parcelable> T getParcelableOrNull(String key, Class<T> clazz) {
        Intrinsics.checkNotNullParameter(key, "key");
        Intrinsics.checkNotNullParameter(clazz, "clazz");
        T t = (T) this.extras.getParcelable(INSTANCE.extrasKey(key), clazz);
        AppFunctionDataSpec appFunctionDataSpec = this.spec;
        if (appFunctionDataSpec != null) {
            appFunctionDataSpec.validateReadRequest(key, clazz, false, t);
        }
        return t;
    }

    public final boolean[] getBooleanArray(String key) {
        Intrinsics.checkNotNullParameter(key, "key");
        boolean[] zArr = (boolean[]) unsafeGetProperty(key, boolean[].class);
        AppFunctionDataSpec appFunctionDataSpec = this.spec;
        if (appFunctionDataSpec != null) {
            appFunctionDataSpec.validateReadRequest(key, Boolean.TYPE, true, zArr);
        }
        return zArr;
    }

    public final float[] getFloatArray(String key) {
        float[] floatArray;
        Intrinsics.checkNotNullParameter(key, "key");
        double[] dArr = (double[]) unsafeGetProperty(key, double[].class);
        if (dArr != null) {
            ArrayList arrayList = new ArrayList(dArr.length);
            for (double d : dArr) {
                if (!isDoubleWithinFloatRange(d)) {
                    throw new IllegalStateException("One of the value associated with " + key + " is not within the range of Float");
                }
                arrayList.add(Float.valueOf((float) d));
            }
            floatArray = CollectionsKt.toFloatArray(arrayList);
        } else {
            floatArray = null;
        }
        AppFunctionDataSpec appFunctionDataSpec = this.spec;
        if (appFunctionDataSpec != null) {
            appFunctionDataSpec.validateReadRequest(key, Float.TYPE, true, floatArray);
        }
        return floatArray;
    }

    public final double[] getDoubleArray(String key) {
        Intrinsics.checkNotNullParameter(key, "key");
        double[] dArr = (double[]) unsafeGetProperty(key, double[].class);
        AppFunctionDataSpec appFunctionDataSpec = this.spec;
        if (appFunctionDataSpec != null) {
            appFunctionDataSpec.validateReadRequest(key, Double.TYPE, true, dArr);
        }
        return dArr;
    }

    public final int[] getIntArray(String key) {
        int[] intArray;
        Intrinsics.checkNotNullParameter(key, "key");
        long[] jArr = (long[]) unsafeGetProperty(key, long[].class);
        if (jArr != null) {
            ArrayList arrayList = new ArrayList(jArr.length);
            for (long j : jArr) {
                if (!isLongWithinLongRange(j)) {
                    throw new IllegalStateException("One of the value associated with " + key + " is not within the range of Int");
                }
                arrayList.add(Integer.valueOf((int) j));
            }
            intArray = CollectionsKt.toIntArray(arrayList);
        } else {
            intArray = null;
        }
        AppFunctionDataSpec appFunctionDataSpec = this.spec;
        if (appFunctionDataSpec != null) {
            appFunctionDataSpec.validateReadRequest(key, Integer.TYPE, true, intArray);
        }
        return intArray;
    }

    public final long[] getLongArray(String key) {
        Intrinsics.checkNotNullParameter(key, "key");
        long[] jArr = (long[]) unsafeGetProperty(key, long[].class);
        AppFunctionDataSpec appFunctionDataSpec = this.spec;
        if (appFunctionDataSpec != null) {
            appFunctionDataSpec.validateReadRequest(key, Long.TYPE, true, jArr);
        }
        return jArr;
    }

    public final byte[] getByteArray(String key) {
        Intrinsics.checkNotNullParameter(key, "key");
        return getByteArrayOrNull(key);
    }

    public final byte[] getByteArrayOrNull(String key) {
        Intrinsics.checkNotNullParameter(key, "key");
        byte[][] bArr = (byte[][]) unsafeGetProperty(key, byte[][].class);
        byte[] bArr2 = (bArr == null || bArr.length == 0) ? null : bArr[0];
        AppFunctionDataSpec appFunctionDataSpec = this.spec;
        if (appFunctionDataSpec != null) {
            appFunctionDataSpec.validateReadRequest(key, Byte.TYPE, true, bArr2);
        }
        return bArr2;
    }

    public final List<String> getStringList(String key) {
        Intrinsics.checkNotNullParameter(key, "key");
        String[] strArr = (String[]) unsafeGetProperty(key, String[].class);
        List<String> listAsList = strArr != null ? ArraysKt.asList(strArr) : null;
        AppFunctionDataSpec appFunctionDataSpec = this.spec;
        if (appFunctionDataSpec != null) {
            appFunctionDataSpec.validateReadRequest(key, String.class, true, listAsList);
        }
        return listAsList;
    }

    public final List<AppFunctionData> getAppFunctionDataList(String key) {
        AppFunctionDataSpec propertyObjectSpec;
        Intrinsics.checkNotNullParameter(key, "key");
        GenericDocument[] genericDocumentArr = (GenericDocument[]) unsafeGetProperty(key, GenericDocument[].class);
        ArrayList arrayList = null;
        if (genericDocumentArr != null) {
            ArrayList arrayList2 = new ArrayList(genericDocumentArr.length);
            int length = genericDocumentArr.length;
            int i = 0;
            int i2 = 0;
            while (i < length) {
                GenericDocument genericDocument = genericDocumentArr[i];
                int i3 = i2 + 1;
                AppFunctionDataSpec appFunctionDataSpec = this.spec;
                if (appFunctionDataSpec != null) {
                    String schemaType = genericDocument.getSchemaType();
                    Intrinsics.checkNotNullExpressionValue(schemaType, "getSchemaType(...)");
                    propertyObjectSpec = appFunctionDataSpec.getPropertyObjectSpec(key, schemaType);
                } else {
                    propertyObjectSpec = null;
                }
                Bundle bundle = this.extras.getBundle(INSTANCE.extrasKey(key, i2));
                if (bundle == null) {
                    bundle = Bundle.EMPTY;
                }
                Intrinsics.checkNotNull(bundle);
                arrayList2.add(new AppFunctionData(propertyObjectSpec, genericDocument, bundle));
                i++;
                i2 = i3;
            }
            arrayList = arrayList2;
        }
        AppFunctionDataSpec appFunctionDataSpec2 = this.spec;
        if (appFunctionDataSpec2 != null) {
            appFunctionDataSpec2.validateReadRequest(key, AppFunctionData.class, true, arrayList);
        }
        return arrayList;
    }

    public final <T extends Parcelable> List<T> getParcelableList(String key, Class<T> clazz) {
        Intrinsics.checkNotNullParameter(key, "key");
        Intrinsics.checkNotNullParameter(clazz, "clazz");
        this.extras.setClassLoader(clazz.getClassLoader());
        ArrayList parcelableArrayList = this.extras.getParcelableArrayList(INSTANCE.extrasKey(key), clazz);
        AppFunctionDataSpec appFunctionDataSpec = this.spec;
        if (appFunctionDataSpec != null) {
            appFunctionDataSpec.validateReadRequest(key, clazz, true, parcelableArrayList);
        }
        if (parcelableArrayList == null) {
            return null;
        }
        ArrayList arrayList = parcelableArrayList;
        if (!(arrayList instanceof Collection) || !arrayList.isEmpty()) {
            Iterator it = arrayList.iterator();
            while (it.hasNext()) {
                if (!clazz.isInstance((Parcelable) it.next())) {
                    return null;
                }
            }
        }
        return parcelableArrayList;
    }

    public final /* synthetic */ <T extends Parcelable> List<T> getParcelableList(String key) {
        Intrinsics.checkNotNullParameter(key, "key");
        Intrinsics.reifiedOperationMarker(4, ExifInterface.GPS_DIRECTION_TRUE);
        return getParcelableList(key, Parcelable.class);
    }

    public String toString() {
        return "AppFunctionData(genericDocument=" + this.genericDocument + ", extras=" + this.extras + CoreConstants.RIGHT_PARENTHESIS_CHAR;
    }

    private final boolean isDoubleWithinFloatRange(double doubleValue) {
        if (Double.isInfinite(doubleValue) || Double.isNaN(doubleValue)) {
            return true;
        }
        return doubleValue <= 3.4028234663852886E38d && doubleValue >= -3.4028234663852886E38d;
    }

    public final <T> T deserialize(Class<T> serializableClass) {
        Intrinsics.checkNotNullParameter(serializableClass, "serializableClass");
        try {
            return (T) INSTANCE.getSerializableFactory(serializableClass).fromAppFunctionData(this);
        } catch (Exception e) {
            Log.d(Constants.APP_FUNCTIONS_TAG, "Something went wrong while deserialize " + this + " to " + serializableClass, e);
            throw new IllegalArgumentException("Unable to deserialize " + serializableClass + ". Is the class annotated with @AppFunctionSerializable?");
        }
    }

    public final <T> T deserialize(String qualifiedName) {
        Intrinsics.checkNotNullParameter(qualifiedName, "qualifiedName");
        return (T) deserialize(INSTANCE.getSerializableClass(qualifiedName));
    }

    private final <T> T unsafeGetProperty(String key, Class<T> arrayClass) {
        try {
            Object property = this.genericDocument.getProperty(key);
            if (property != null) {
                return arrayClass.cast(property);
            }
            return null;
        } catch (ClassCastException e) {
            throw new IllegalArgumentException("Found the property under [" + key + "] but data type does not match with the request.", e);
        }
    }

    public final AppFunctionData replaceSpecWith$appfunctions(AppFunctionResponseMetadata responseMetadata, AppFunctionComponentsMetadata componentMetadata) {
        Intrinsics.checkNotNullParameter(responseMetadata, "responseMetadata");
        Intrinsics.checkNotNullParameter(componentMetadata, "componentMetadata");
        return new AppFunctionData(AppFunctionDataSpec.INSTANCE.create(responseMetadata, componentMetadata), this.genericDocument, this.extras);
    }

    public final AppFunctionData replaceSpecWith$appfunctions(AppFunctionObjectTypeMetadata objectTypeMetadata, AppFunctionComponentsMetadata componentMetadata) {
        Intrinsics.checkNotNullParameter(objectTypeMetadata, "objectTypeMetadata");
        Intrinsics.checkNotNullParameter(componentMetadata, "componentMetadata");
        return new AppFunctionData(AppFunctionDataSpec.INSTANCE.create(objectTypeMetadata, componentMetadata), this.genericDocument, this.extras);
    }

    public final AppFunctionData replaceSpecWith$appfunctions(List<AppFunctionParameterMetadata> parameterMetadata, AppFunctionComponentsMetadata componentMetadata) {
        Intrinsics.checkNotNullParameter(parameterMetadata, "parameterMetadata");
        Intrinsics.checkNotNullParameter(componentMetadata, "componentMetadata");
        return new AppFunctionData(AppFunctionDataSpec.INSTANCE.create(parameterMetadata, componentMetadata), this.genericDocument, this.extras);
    }

    public final void visitAppFunctionUriGrants(Function1<? super AppFunctionUriGrant, Unit> visitor) {
        AppFunctionUriGrant appFunctionUriGrant;
        Intrinsics.checkNotNullParameter(visitor, "visitor");
        if (Intrinsics.areEqual(getQualifiedName(), AppFunctionUriGrant.class.getCanonicalName())) {
            try {
                appFunctionUriGrant = (AppFunctionUriGrant) deserialize(AppFunctionUriGrant.class);
            } catch (Exception e) {
                Log.d(Constants.APP_FUNCTIONS_TAG, "Unexpected error while visiting AppFunctionUriGrant", e);
                appFunctionUriGrant = null;
            }
            if (appFunctionUriGrant != null) {
                visitor.invoke(appFunctionUriGrant);
            }
        }
        for (String str : this.genericDocument.getPropertyNames()) {
            try {
                Intrinsics.checkNotNull(str);
                AppFunctionData appFunctionData = getAppFunctionData(str);
                if (appFunctionData != null) {
                    appFunctionData.visitAppFunctionUriGrants(visitor);
                }
            } catch (Exception unused) {
            }
            try {
                Intrinsics.checkNotNull(str);
                List<AppFunctionData> appFunctionDataList = getAppFunctionDataList(str);
                if (appFunctionDataList == null) {
                    appFunctionDataList = CollectionsKt.emptyList();
                }
                Iterator<AppFunctionData> it = appFunctionDataList.iterator();
                while (it.hasNext()) {
                    it.next().visitAppFunctionUriGrants(visitor);
                }
            } catch (Exception unused2) {
            }
        }
    }

    /* JADX INFO: compiled from: AppFunctionData.kt */
    @Metadata(d1 = {"\u0000¶\u0001\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0004\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0007\n\u0000\n\u0002\u0010\u0006\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\t\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0018\n\u0000\n\u0002\u0010\u0014\n\u0000\n\u0002\u0010\u0013\n\u0000\n\u0002\u0010\u0015\n\u0000\n\u0002\u0010\u0016\n\u0000\n\u0002\u0010\u0012\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0002\u0018\u00002\u00020\u0001B\u001b\b\u0010\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\b\b\u0002\u0010\u0004\u001a\u00020\u0003¢\u0006\u0004\b\u0005\u0010\u0006B\u001f\b\u0016\u0012\f\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\t0\b\u0012\u0006\u0010\n\u001a\u00020\u000b¢\u0006\u0004\b\u0005\u0010\fB\u0019\b\u0016\u0012\u0006\u0010\r\u001a\u00020\u000e\u0012\u0006\u0010\n\u001a\u00020\u000b¢\u0006\u0004\b\u0005\u0010\u000fB\u0019\b\u0016\u0012\u0006\u0010\u0010\u001a\u00020\u0011\u0012\u0006\u0010\n\u001a\u00020\u000b¢\u0006\u0004\b\u0005\u0010\u0012B\u0019\b\u0016\u0012\u0006\u0010\u0013\u001a\u00020\u0014\u0012\u0006\u0010\n\u001a\u00020\u000b¢\u0006\u0004\b\u0005\u0010\u0015B\u0011\b\u0012\u0012\u0006\u0010\u0016\u001a\u00020\u0017¢\u0006\u0004\b\u0005\u0010\u0018J\u0010\u0010\u001d\u001a\u00020\u001e2\u0006\u0010\u0004\u001a\u00020\u0003H\u0002J\u0018\u0010\u001f\u001a\u00020\u00002\u0006\u0010 \u001a\u00020\u00032\u0006\u0010!\u001a\u00020\"H\u0007J\u0018\u0010#\u001a\u00020\u00002\u0006\u0010 \u001a\u00020\u00032\u0006\u0010!\u001a\u00020$H\u0007J\u0018\u0010%\u001a\u00020\u00002\u0006\u0010 \u001a\u00020\u00032\u0006\u0010!\u001a\u00020&H\u0007J\u0018\u0010'\u001a\u00020\u00002\u0006\u0010 \u001a\u00020\u00032\u0006\u0010!\u001a\u00020(H\u0007J\u0018\u0010)\u001a\u00020\u00002\u0006\u0010 \u001a\u00020\u00032\u0006\u0010!\u001a\u00020*H\u0007J\u0018\u0010+\u001a\u00020\u00002\u0006\u0010 \u001a\u00020\u00032\u0006\u0010!\u001a\u00020\u0003H\u0007J\u0018\u0010,\u001a\u00020\u00002\u0006\u0010 \u001a\u00020\u00032\u0006\u0010!\u001a\u00020-H\u0007J'\u0010.\u001a\u00020\u0000\"\b\b\u0000\u0010/*\u0002002\u0006\u0010 \u001a\u00020\u00032\u0006\u0010!\u001a\u0002H/H\u0007¢\u0006\u0002\u00101J\u0018\u00102\u001a\u00020\u00002\u0006\u0010 \u001a\u00020\u00032\u0006\u0010!\u001a\u000203H\u0007J\u0018\u00104\u001a\u00020\u00002\u0006\u0010 \u001a\u00020\u00032\u0006\u0010!\u001a\u000205H\u0007J\u0018\u00106\u001a\u00020\u00002\u0006\u0010 \u001a\u00020\u00032\u0006\u0010!\u001a\u000207H\u0007J\u0018\u00108\u001a\u00020\u00002\u0006\u0010 \u001a\u00020\u00032\u0006\u0010!\u001a\u000209H\u0007J\u0018\u0010:\u001a\u00020\u00002\u0006\u0010 \u001a\u00020\u00032\u0006\u0010!\u001a\u00020;H\u0007J\u0018\u0010<\u001a\u00020\u00002\u0006\u0010 \u001a\u00020\u00032\u0006\u0010!\u001a\u00020=H\u0007J\u001e\u0010>\u001a\u00020\u00002\u0006\u0010 \u001a\u00020\u00032\f\u0010!\u001a\b\u0012\u0004\u0012\u00020\u00030\bH\u0007J\u001e\u0010?\u001a\u00020\u00002\u0006\u0010 \u001a\u00020\u00032\f\u0010!\u001a\b\u0012\u0004\u0012\u00020-0\bH\u0007J+\u0010@\u001a\u00020\u0000\"\n\b\u0000\u0010/\u0018\u0001*\u0002002\u0006\u0010 \u001a\u00020\u00032\f\u0010!\u001a\b\u0012\u0004\u0012\u0002H/0\bH\u0087\bJ6\u0010@\u001a\u00020\u0000\"\b\b\u0000\u0010/*\u0002002\u0006\u0010 \u001a\u00020\u00032\f\u0010!\u001a\b\u0012\u0004\u0012\u0002H/0\b2\f\u0010A\u001a\b\u0012\u0004\u0012\u0002H/0BH\u0007J\u0006\u0010C\u001a\u00020-R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000R\u0010\u0010\u0016\u001a\u0004\u0018\u00010\u0017X\u0082\u0004¢\u0006\u0002\n\u0000R\u0012\u0010\u0019\u001a\u0006\u0012\u0002\b\u00030\u001aX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u001b\u001a\u00020\u001cX\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006D"}, d2 = {"Landroidx/appfunctions/AppFunctionData$Builder;", "", "qualifiedName", "", "id", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "(Ljava/lang/String;Ljava/lang/String;)V", "parameterMetadataList", "", "Landroidx/appfunctions/metadata/AppFunctionParameterMetadata;", "componentMetadata", "Landroidx/appfunctions/metadata/AppFunctionComponentsMetadata;", "(Ljava/util/List;Landroidx/appfunctions/metadata/AppFunctionComponentsMetadata;)V", "objectTypeMetadata", "Landroidx/appfunctions/metadata/AppFunctionObjectTypeMetadata;", "(Landroidx/appfunctions/metadata/AppFunctionObjectTypeMetadata;Landroidx/appfunctions/metadata/AppFunctionComponentsMetadata;)V", "allOfTypeMetadata", "Landroidx/appfunctions/metadata/AppFunctionAllOfTypeMetadata;", "(Landroidx/appfunctions/metadata/AppFunctionAllOfTypeMetadata;Landroidx/appfunctions/metadata/AppFunctionComponentsMetadata;)V", "responseMetadata", "Landroidx/appfunctions/metadata/AppFunctionResponseMetadata;", "(Landroidx/appfunctions/metadata/AppFunctionResponseMetadata;Landroidx/appfunctions/metadata/AppFunctionComponentsMetadata;)V", "spec", "Landroidx/appfunctions/AppFunctionDataSpec;", "(Landroidx/appfunctions/AppFunctionDataSpec;)V", "genericDocumentBuilder", "Landroid/app/appsearch/GenericDocument$Builder;", "extrasBuilder", "Landroid/os/Bundle;", "setLegacyId", "", "setBoolean", "key", "value", "", "setFloat", "", "setDouble", "", "setInt", "", "setLong", "", "setString", "setAppFunctionData", "Landroidx/appfunctions/AppFunctionData;", "setParcelable", ExifInterface.GPS_DIRECTION_TRUE, "Landroid/os/Parcelable;", "(Ljava/lang/String;Landroid/os/Parcelable;)Landroidx/appfunctions/AppFunctionData$Builder;", "setBooleanArray", "", "setFloatArray", "", "setDoubleArray", "", "setIntArray", "", "setLongArray", "", "setByteArray", "", "setStringList", "setAppFunctionDataList", "setParcelableList", "clazz", "Ljava/lang/Class;", "build", AppFunctionMetadataKt.APP_FUNCTION_NAMESPACE}, k = 1, mv = {2, 1, 0}, xi = 48)
    public static final class Builder {
        private final Bundle extrasBuilder;
        private GenericDocument.Builder<?> genericDocumentBuilder;
        private final String qualifiedName;
        private final AppFunctionDataSpec spec;

        public Builder(String qualifiedName, String id) {
            Intrinsics.checkNotNullParameter(qualifiedName, "qualifiedName");
            Intrinsics.checkNotNullParameter(id, "id");
            this.extrasBuilder = new Bundle();
            this.qualifiedName = qualifiedName;
            this.spec = null;
            this.genericDocumentBuilder = new GenericDocument.Builder<>("", id, qualifiedName);
        }

        public /* synthetic */ Builder(String str, String str2, int i, DefaultConstructorMarker defaultConstructorMarker) {
            this(str, (i & 2) != 0 ? "" : str2);
        }

        /* JADX WARN: 'this' call moved to the top of the method (can break code semantics) */
        public Builder(List<AppFunctionParameterMetadata> parameterMetadataList, AppFunctionComponentsMetadata componentMetadata) {
            this(AppFunctionDataSpec.INSTANCE.create(parameterMetadataList, componentMetadata));
            Intrinsics.checkNotNullParameter(parameterMetadataList, "parameterMetadataList");
            Intrinsics.checkNotNullParameter(componentMetadata, "componentMetadata");
        }

        /* JADX WARN: 'this' call moved to the top of the method (can break code semantics) */
        public Builder(AppFunctionObjectTypeMetadata objectTypeMetadata, AppFunctionComponentsMetadata componentMetadata) {
            this(AppFunctionDataSpec.INSTANCE.create(objectTypeMetadata, componentMetadata));
            Intrinsics.checkNotNullParameter(objectTypeMetadata, "objectTypeMetadata");
            Intrinsics.checkNotNullParameter(componentMetadata, "componentMetadata");
        }

        /* JADX WARN: 'this' call moved to the top of the method (can break code semantics) */
        public Builder(AppFunctionAllOfTypeMetadata allOfTypeMetadata, AppFunctionComponentsMetadata componentMetadata) {
            this(AppFunctionDataSpec.INSTANCE.create(allOfTypeMetadata.getPseudoObjectTypeMetadata$appfunctions(componentMetadata), componentMetadata));
            Intrinsics.checkNotNullParameter(allOfTypeMetadata, "allOfTypeMetadata");
            Intrinsics.checkNotNullParameter(componentMetadata, "componentMetadata");
        }

        /* JADX WARN: 'this' call moved to the top of the method (can break code semantics) */
        public Builder(AppFunctionResponseMetadata responseMetadata, AppFunctionComponentsMetadata componentMetadata) {
            this(AppFunctionDataSpec.INSTANCE.create(responseMetadata, componentMetadata));
            Intrinsics.checkNotNullParameter(responseMetadata, "responseMetadata");
            Intrinsics.checkNotNullParameter(componentMetadata, "componentMetadata");
        }

        private Builder(AppFunctionDataSpec appFunctionDataSpec) {
            this.extrasBuilder = new Bundle();
            this.spec = appFunctionDataSpec;
            this.qualifiedName = appFunctionDataSpec.getObjectQualifiedName();
            this.genericDocumentBuilder = new GenericDocument.Builder<>("", "", appFunctionDataSpec.getObjectQualifiedName());
        }

        private final void setLegacyId(String id) {
            if (SdkExtensions.getExtensionVersion(33) >= 13) {
                this.genericDocumentBuilder.setId(id);
            } else {
                Integer.valueOf(Log.wtf(Constants.APP_FUNCTIONS_TAG, "setId method in GenericDocument isn't supported on the current device."));
            }
        }

        public final Builder setBoolean(String key, boolean value) {
            Intrinsics.checkNotNullParameter(key, "key");
            AppFunctionDataSpec appFunctionDataSpec = this.spec;
            if (appFunctionDataSpec != null) {
                appFunctionDataSpec.validateWriteRequest(key, Boolean.TYPE, false, Boolean.valueOf(value));
            }
            this.genericDocumentBuilder.setPropertyBoolean(key, value);
            return this;
        }

        public final Builder setFloat(String key, float value) {
            Intrinsics.checkNotNullParameter(key, "key");
            AppFunctionDataSpec appFunctionDataSpec = this.spec;
            if (appFunctionDataSpec != null) {
                appFunctionDataSpec.validateWriteRequest(key, Float.TYPE, false, Float.valueOf(value));
            }
            this.genericDocumentBuilder.setPropertyDouble(key, value);
            return this;
        }

        public final Builder setDouble(String key, double value) {
            Intrinsics.checkNotNullParameter(key, "key");
            AppFunctionDataSpec appFunctionDataSpec = this.spec;
            if (appFunctionDataSpec != null) {
                appFunctionDataSpec.validateWriteRequest(key, Double.TYPE, false, Double.valueOf(value));
            }
            this.genericDocumentBuilder.setPropertyDouble(key, value);
            return this;
        }

        public final Builder setInt(String key, int value) {
            Intrinsics.checkNotNullParameter(key, "key");
            AppFunctionDataSpec appFunctionDataSpec = this.spec;
            if (appFunctionDataSpec != null) {
                appFunctionDataSpec.validateWriteRequest(key, Integer.TYPE, false, Integer.valueOf(value));
            }
            this.genericDocumentBuilder.setPropertyLong(key, value);
            return this;
        }

        public final Builder setLong(String key, long value) {
            Intrinsics.checkNotNullParameter(key, "key");
            AppFunctionDataSpec appFunctionDataSpec = this.spec;
            if (appFunctionDataSpec != null) {
                appFunctionDataSpec.validateWriteRequest(key, Long.TYPE, false, Long.valueOf(value));
            }
            this.genericDocumentBuilder.setPropertyLong(key, value);
            return this;
        }

        public final Builder setString(String key, String value) {
            Intrinsics.checkNotNullParameter(key, "key");
            Intrinsics.checkNotNullParameter(value, "value");
            AppFunctionDataSpec appFunctionDataSpec = this.spec;
            if (appFunctionDataSpec != null) {
                appFunctionDataSpec.validateWriteRequest(key, String.class, false, value);
            }
            this.genericDocumentBuilder.setPropertyString(key, value);
            if (Intrinsics.areEqual(key, "id")) {
                setLegacyId(value);
            }
            return this;
        }

        public final Builder setAppFunctionData(String key, AppFunctionData value) {
            AppFunctionDataSpec propertyObjectSpec;
            Intrinsics.checkNotNullParameter(key, "key");
            Intrinsics.checkNotNullParameter(value, "value");
            AppFunctionDataSpec appFunctionDataSpec = this.spec;
            if (appFunctionDataSpec != null) {
                appFunctionDataSpec.validateWriteRequest(key, AppFunctionData.class, false, value);
            }
            AppFunctionDataSpec appFunctionDataSpec2 = this.spec;
            if (appFunctionDataSpec2 != null && (propertyObjectSpec = appFunctionDataSpec2.getPropertyObjectSpec(key, value.getQualifiedName())) != null) {
                propertyObjectSpec.validateDataSpecMatches(value);
            }
            this.genericDocumentBuilder.setPropertyDocument(key, value.getGenericDocument());
            if (!value.getExtras().isEmpty()) {
                this.extrasBuilder.putBundle(AppFunctionData.INSTANCE.extrasKey(key), value.getExtras());
            }
            return this;
        }

        public final <T extends Parcelable> Builder setParcelable(String key, T value) {
            Intrinsics.checkNotNullParameter(key, "key");
            Intrinsics.checkNotNullParameter(value, "value");
            AppFunctionDataSpec appFunctionDataSpec = this.spec;
            if (appFunctionDataSpec != null) {
                appFunctionDataSpec.validateWriteRequest(key, value.getClass(), false, value);
            }
            this.extrasBuilder.putParcelable(AppFunctionData.INSTANCE.extrasKey(key), value);
            return this;
        }

        public final Builder setBooleanArray(String key, boolean[] value) {
            Intrinsics.checkNotNullParameter(key, "key");
            Intrinsics.checkNotNullParameter(value, "value");
            AppFunctionDataSpec appFunctionDataSpec = this.spec;
            if (appFunctionDataSpec != null) {
                appFunctionDataSpec.validateWriteRequest(key, Boolean.TYPE, true, value);
            }
            this.genericDocumentBuilder.setPropertyBoolean(key, Arrays.copyOf(value, value.length));
            return this;
        }

        public final Builder setFloatArray(String key, float[] value) {
            Intrinsics.checkNotNullParameter(key, "key");
            Intrinsics.checkNotNullParameter(value, "value");
            AppFunctionDataSpec appFunctionDataSpec = this.spec;
            if (appFunctionDataSpec != null) {
                appFunctionDataSpec.validateWriteRequest(key, Float.TYPE, true, value);
            }
            GenericDocument.Builder<?> builder = this.genericDocumentBuilder;
            List<Float> listAsList = ArraysKt.asList(value);
            ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(listAsList, 10));
            Iterator<T> it = listAsList.iterator();
            while (it.hasNext()) {
                arrayList.add(Double.valueOf(((Number) it.next()).floatValue()));
            }
            double[] doubleArray = CollectionsKt.toDoubleArray(arrayList);
            builder.setPropertyDouble(key, Arrays.copyOf(doubleArray, doubleArray.length));
            return this;
        }

        public final Builder setDoubleArray(String key, double[] value) {
            Intrinsics.checkNotNullParameter(key, "key");
            Intrinsics.checkNotNullParameter(value, "value");
            AppFunctionDataSpec appFunctionDataSpec = this.spec;
            if (appFunctionDataSpec != null) {
                appFunctionDataSpec.validateWriteRequest(key, Double.TYPE, true, value);
            }
            this.genericDocumentBuilder.setPropertyDouble(key, Arrays.copyOf(value, value.length));
            return this;
        }

        public final Builder setIntArray(String key, int[] value) {
            Intrinsics.checkNotNullParameter(key, "key");
            Intrinsics.checkNotNullParameter(value, "value");
            AppFunctionDataSpec appFunctionDataSpec = this.spec;
            if (appFunctionDataSpec != null) {
                appFunctionDataSpec.validateWriteRequest(key, Integer.TYPE, true, value);
            }
            GenericDocument.Builder<?> builder = this.genericDocumentBuilder;
            List<Integer> listAsList = ArraysKt.asList(value);
            ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(listAsList, 10));
            Iterator<T> it = listAsList.iterator();
            while (it.hasNext()) {
                arrayList.add(Long.valueOf(((Number) it.next()).intValue()));
            }
            long[] longArray = CollectionsKt.toLongArray(arrayList);
            builder.setPropertyLong(key, Arrays.copyOf(longArray, longArray.length));
            return this;
        }

        public final Builder setLongArray(String key, long[] value) {
            Intrinsics.checkNotNullParameter(key, "key");
            Intrinsics.checkNotNullParameter(value, "value");
            AppFunctionDataSpec appFunctionDataSpec = this.spec;
            if (appFunctionDataSpec != null) {
                appFunctionDataSpec.validateWriteRequest(key, Long.TYPE, true, value);
            }
            this.genericDocumentBuilder.setPropertyLong(key, Arrays.copyOf(value, value.length));
            return this;
        }

        public final Builder setByteArray(String key, byte[] value) {
            Intrinsics.checkNotNullParameter(key, "key");
            Intrinsics.checkNotNullParameter(value, "value");
            AppFunctionDataSpec appFunctionDataSpec = this.spec;
            if (appFunctionDataSpec != null) {
                appFunctionDataSpec.validateWriteRequest(key, Byte.TYPE, true, value);
            }
            this.genericDocumentBuilder.setPropertyBytes(key, value);
            return this;
        }

        public final Builder setStringList(String key, List<String> value) {
            Intrinsics.checkNotNullParameter(key, "key");
            Intrinsics.checkNotNullParameter(value, "value");
            AppFunctionDataSpec appFunctionDataSpec = this.spec;
            if (appFunctionDataSpec != null) {
                appFunctionDataSpec.validateWriteRequest(key, new String().getClass(), true, value);
            }
            GenericDocument.Builder<?> builder = this.genericDocumentBuilder;
            String[] strArr = (String[]) value.toArray(new String[0]);
            builder.setPropertyString(key, (String[]) Arrays.copyOf(strArr, strArr.length));
            return this;
        }

        public final Builder setAppFunctionDataList(String key, List<AppFunctionData> value) {
            AppFunctionDataSpec propertyObjectSpec;
            Intrinsics.checkNotNullParameter(key, "key");
            Intrinsics.checkNotNullParameter(value, "value");
            AppFunctionDataSpec appFunctionDataSpec = this.spec;
            if (appFunctionDataSpec != null) {
                appFunctionDataSpec.validateWriteRequest(key, AppFunctionData.class, true, value);
            }
            GenericDocument.Builder<?> builder = this.genericDocumentBuilder;
            List<AppFunctionData> list = value;
            ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(list, 10));
            Iterator<T> it = list.iterator();
            while (it.hasNext()) {
                arrayList.add(((AppFunctionData) it.next()).getGenericDocument());
            }
            int i = 0;
            GenericDocument[] genericDocumentArr = (GenericDocument[]) arrayList.toArray(new GenericDocument[0]);
            builder.setPropertyDocument(key, (GenericDocument[]) Arrays.copyOf(genericDocumentArr, genericDocumentArr.length));
            for (Object obj : list) {
                int i2 = i + 1;
                if (i < 0) {
                    CollectionsKt.throwIndexOverflow();
                }
                AppFunctionData appFunctionData = (AppFunctionData) obj;
                AppFunctionDataSpec appFunctionDataSpec2 = this.spec;
                if (appFunctionDataSpec2 != null && (propertyObjectSpec = appFunctionDataSpec2.getPropertyObjectSpec(key, appFunctionData.getQualifiedName())) != null) {
                    propertyObjectSpec.validateDataSpecMatches(appFunctionData);
                }
                if (!appFunctionData.getExtras().isEmpty()) {
                    this.extrasBuilder.putBundle(AppFunctionData.INSTANCE.extrasKey(key, i), appFunctionData.getExtras());
                }
                i = i2;
            }
            return this;
        }

        public final /* synthetic */ <T extends Parcelable> Builder setParcelableList(String key, List<? extends T> value) {
            Intrinsics.checkNotNullParameter(key, "key");
            Intrinsics.checkNotNullParameter(value, "value");
            Intrinsics.reifiedOperationMarker(4, ExifInterface.GPS_DIRECTION_TRUE);
            return setParcelableList(key, value, Parcelable.class);
        }

        public final <T extends Parcelable> Builder setParcelableList(String key, List<? extends T> value, Class<T> clazz) {
            Intrinsics.checkNotNullParameter(key, "key");
            Intrinsics.checkNotNullParameter(value, "value");
            Intrinsics.checkNotNullParameter(clazz, "clazz");
            AppFunctionDataSpec appFunctionDataSpec = this.spec;
            if (appFunctionDataSpec != null) {
                appFunctionDataSpec.validateWriteRequest(key, clazz, true, value);
            }
            this.extrasBuilder.putParcelableArrayList(AppFunctionData.INSTANCE.extrasKey(key), new ArrayList<>(value));
            return this;
        }

        public final AppFunctionData build() {
            Set<String> allPropertyKeys$appfunctions;
            GenericDocument genericDocumentBuild = this.genericDocumentBuilder.build();
            Intrinsics.checkNotNullExpressionValue(genericDocumentBuild, "build(...)");
            AppFunctionData appFunctionData = new AppFunctionData(this.spec, genericDocumentBuild, this.extrasBuilder);
            AppFunctionDataSpec appFunctionDataSpec = this.spec;
            if (appFunctionDataSpec != null && (allPropertyKeys$appfunctions = appFunctionDataSpec.getAllPropertyKeys$appfunctions()) != null) {
                for (String str : allPropertyKeys$appfunctions) {
                    if (this.spec.isRequired$appfunctions(str) && !appFunctionData.containsKey(str)) {
                        throw new IllegalArgumentException("Missing required property: '" + str + "' for object '" + this.qualifiedName + CoreConstants.SINGLE_QUOTE_CHAR);
                    }
                }
            }
            return appFunctionData;
        }
    }

    /* JADX INFO: compiled from: AppFunctionData.kt */
    @Metadata(d1 = {"\u0000J\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0007\n\u0000\n\u0002\u0010\u0006\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\t\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0005\b\u0086\u0003\u0018\u00002\u00020\u0001B\t\b\u0002¢\u0006\u0004\b\u0002\u0010\u0003J\u0010\u0010\u0010\u001a\u00020\u000f2\u0006\u0010\u0011\u001a\u00020\u000fH\u0002J\u0018\u0010\u0010\u001a\u00020\u000f2\u0006\u0010\u0011\u001a\u00020\u000f2\u0006\u0010\u0012\u001a\u00020\u000bH\u0002J \u0010\u0013\u001a\b\u0012\u0004\u0012\u0002H\u00150\u0014\"\b\b\u0000\u0010\u0015*\u00020\u00012\u0006\u0010\u0016\u001a\u00020\u000fH\u0002J&\u0010\u0017\u001a\b\u0012\u0004\u0012\u0002H\u00150\u0018\"\b\b\u0000\u0010\u0015*\u00020\u00012\f\u0010\u0019\u001a\b\u0012\u0004\u0012\u0002H\u00150\u0014H\u0002J\u0014\u0010\u001a\u001a\u00020\u000f2\n\u0010\u0019\u001a\u0006\u0012\u0002\b\u00030\u0014H\u0002J-\u0010\u001b\u001a\u00020\u001c\"\b\b\u0000\u0010\u0015*\u00020\u00012\u0006\u0010\u001d\u001a\u0002H\u00152\f\u0010\u0019\u001a\b\u0012\u0004\u0012\u0002H\u00150\u0014H\u0007¢\u0006\u0002\u0010\u001eJ'\u0010\u001b\u001a\u00020\u001c\"\b\b\u0000\u0010\u0015*\u00020\u00012\u0006\u0010\u001d\u001a\u0002H\u00152\u0006\u0010\u0016\u001a\u00020\u000fH\u0007¢\u0006\u0002\u0010\u001fR\u000e\u0010\u0004\u001a\u00020\u0005X\u0082T¢\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0007X\u0082T¢\u0006\u0002\n\u0000R\u000e\u0010\b\u001a\u00020\tX\u0082T¢\u0006\u0002\n\u0000R\u000e\u0010\n\u001a\u00020\u000bX\u0082T¢\u0006\u0002\n\u0000R\u000e\u0010\f\u001a\u00020\rX\u0082T¢\u0006\u0002\n\u0000R\u000e\u0010\u000e\u001a\u00020\u000fX\u0082T¢\u0006\u0002\n\u0000R\u0010\u0010 \u001a\u00020\u001c8\u0006X\u0087\u0004¢\u0006\u0002\n\u0000¨\u0006!"}, d2 = {"Landroidx/appfunctions/AppFunctionData$Companion;", "", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "()V", "DEFAULT_BOOLEAN", "", "DEFAULT_FLOAT", "", "DEFAULT_DOUBLE", "", "DEFAULT_INT", "", "DEFAULT_LONG", "", "LEGACY_ID_FIELD_KEY", "", "extrasKey", "key", "index", "getSerializableClass", "Ljava/lang/Class;", ExifInterface.GPS_DIRECTION_TRUE, "qualifiedName", "getSerializableFactory", "Landroidx/appfunctions/internal/AppFunctionSerializableFactory;", "serializableClass", "getPackageName", "serialize", "Landroidx/appfunctions/AppFunctionData;", "serializable", "(Ljava/lang/Object;Ljava/lang/Class;)Landroidx/appfunctions/AppFunctionData;", "(Ljava/lang/Object;Ljava/lang/String;)Landroidx/appfunctions/AppFunctionData;", "EMPTY", AppFunctionMetadataKt.APP_FUNCTION_NAMESPACE}, k = 1, mv = {2, 1, 0}, xi = 48)
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }

        /* JADX INFO: Access modifiers changed from: private */
        public final String extrasKey(String key) {
            return "property/" + key;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public final String extrasKey(String key, int index) {
            return "property/" + key + '[' + index + ']';
        }

        /* JADX INFO: Access modifiers changed from: private */
        public final <T> Class<T> getSerializableClass(String qualifiedName) {
            try {
                Class<T> cls = (Class<T>) Class.forName(qualifiedName);
                Intrinsics.checkNotNull(cls, "null cannot be cast to non-null type java.lang.Class<T of androidx.appfunctions.AppFunctionData.Companion.getSerializableClass>");
                return cls;
            } catch (Exception e) {
                Log.d(Constants.APP_FUNCTIONS_TAG, "Unable to find serializable class " + qualifiedName, e);
                throw new IllegalArgumentException("Unable to find serializable class " + qualifiedName);
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public final <T> AppFunctionSerializableFactory<T> getSerializableFactory(Class<T> serializableClass) {
            String packageName = getPackageName(serializableClass);
            String name = serializableClass.getName();
            Intrinsics.checkNotNullExpressionValue(name, "getName(...)");
            try {
                Object objNewInstance = Class.forName(packageName + '.' + ("$" + StringsKt.substringAfterLast$default(name, '.', (String) null, 2, (Object) null) + "Factory")).getDeclaredConstructor(new Class[0]).newInstance(new Object[0]);
                Intrinsics.checkNotNull(objNewInstance, "null cannot be cast to non-null type androidx.appfunctions.internal.AppFunctionSerializableFactory<T of androidx.appfunctions.AppFunctionData.Companion.getSerializableFactory>");
                return (AppFunctionSerializableFactory) objNewInstance;
            } catch (Exception e) {
                Log.d(Constants.APP_FUNCTIONS_TAG, "Unable to create AppFunctionSerializableFactory for " + serializableClass, e);
                throw new IllegalArgumentException("Unable to create AppFunctionSerializableFactory for " + serializableClass);
            }
        }

        private final String getPackageName(Class<?> serializableClass) {
            if (SetsKt.setOf((Object[]) new String[]{Reflection.getOrCreateKotlinClass(LocalDateTime.class).getSimpleName(), Reflection.getOrCreateKotlinClass(Uri.class).getSimpleName()}).contains(serializableClass.getSimpleName())) {
                return "androidx.appfunctions.internal.serializableproxies";
            }
            String packageName = serializableClass.getPackageName();
            Intrinsics.checkNotNullExpressionValue(packageName, "getPackageName(...)");
            return packageName;
        }

        @JvmStatic
        public final <T> AppFunctionData serialize(T serializable, Class<T> serializableClass) {
            Intrinsics.checkNotNullParameter(serializable, "serializable");
            Intrinsics.checkNotNullParameter(serializableClass, "serializableClass");
            try {
                return getSerializableFactory(serializableClass).toAppFunctionData(serializable);
            } catch (Exception e) {
                Log.d(Constants.APP_FUNCTIONS_TAG, "Something went wrong while serialize " + serializable + " of class " + serializableClass, e);
                throw new IllegalArgumentException("Unable to serialize " + serializableClass + ". Is the class annotated with @AppFunctionSerializable?");
            }
        }

        public final <T> AppFunctionData serialize(T serializable, String qualifiedName) {
            Intrinsics.checkNotNullParameter(serializable, "serializable");
            Intrinsics.checkNotNullParameter(qualifiedName, "qualifiedName");
            return serialize(serializable, getSerializableClass(qualifiedName));
        }
    }

    static {
        GenericDocument genericDocumentBuild = new GenericDocument.Builder("", "", "").build();
        Intrinsics.checkNotNullExpressionValue(genericDocumentBuild, "build(...)");
        Bundle EMPTY2 = Bundle.EMPTY;
        Intrinsics.checkNotNullExpressionValue(EMPTY2, "EMPTY");
        EMPTY = new AppFunctionData(genericDocumentBuild, EMPTY2);
    }
}
