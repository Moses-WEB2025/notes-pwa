package androidx.appfunctions.internal;

import androidx.appfunctions.metadata.AppFunctionMetadataKt;
import androidx.exifinterface.media.ExifInterface;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: ClassUtils.kt */
/* JADX INFO: loaded from: classes.dex */
@Metadata(d1 = {"\u0000\u0016\n\u0002\b\u0002\n\u0002\u0010\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0003\u001a1\u0010\u0000\u001a\u0002H\u0001\"\b\b\u0000\u0010\u0001*\u00020\u0002*\b\u0012\u0004\u0012\u0002H\u00010\u00032\u0006\u0010\u0004\u001a\u00020\u00052\u0006\u0010\u0006\u001a\u00020\u0005H\u0007¢\u0006\u0002\u0010\u0007¨\u0006\b"}, d2 = {"findImpl", ExifInterface.GPS_DIRECTION_TRUE, "", "Ljava/lang/Class;", "prefix", "", "suffix", "(Ljava/lang/Class;Ljava/lang/String;Ljava/lang/String;)Ljava/lang/Object;", AppFunctionMetadataKt.APP_FUNCTION_NAMESPACE}, k = 2, mv = {2, 1, 0}, xi = 48)
public final class ClassUtilsKt {
    public static final <T> T findImpl(Class<T> cls, String prefix, String suffix) {
        Intrinsics.checkNotNullParameter(cls, "<this>");
        Intrinsics.checkNotNullParameter(prefix, "prefix");
        Intrinsics.checkNotNullParameter(suffix, "suffix");
        String packageName = cls.getPackageName();
        String canonicalName = cls.getCanonicalName();
        if (canonicalName == null) {
            throw new IllegalArgumentException("Required value was null.".toString());
        }
        String strSubstring = canonicalName.substring(packageName.length() + 1);
        Intrinsics.checkNotNullExpressionValue(strSubstring, "substring(...)");
        String str = prefix + strSubstring + suffix;
        try {
            Class<?> cls2 = Class.forName(packageName + '.' + str, true, cls.getClassLoader());
            Intrinsics.checkNotNull(cls2, "null cannot be cast to non-null type java.lang.Class<T of androidx.appfunctions.internal.ClassUtilsKt.findImpl>");
            T t = (T) cls2.getDeclaredConstructor(new Class[0]).newInstance(new Object[0]);
            Intrinsics.checkNotNull(t);
            return t;
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("Cannot find implementation for " + cls.getCanonicalName() + ". " + str + " does not exist. Is AppFunction annotation processor correctly configured?", e);
        } catch (IllegalAccessException e2) {
            throw new RuntimeException("Cannot access the constructor " + cls.getCanonicalName(), e2);
        } catch (InstantiationException e3) {
            throw new RuntimeException("Failed to create an instance of " + cls.getCanonicalName(), e3);
        }
    }
}
