package kotlin.reflect.jvm.internal;

import kotlin.Metadata;
import kotlin.jvm.JvmClassMappingKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.reflect.KClass;
import kotlin.reflect.jvm.internal.impl.name.ClassId;
import kotlin.reflect.jvm.internal.impl.name.FqName;
import kotlin.text.StringsKt;

/* JADX INFO: compiled from: MetadataUtil.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000\u001e\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u000e\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\u001a\u0010\u0010\u0000\u001a\u00020\u0001*\u00060\u0002j\u0002`\u0003H\u0000\u001a\u0010\u0010\u0004\u001a\u00020\u0002*\u00060\u0002j\u0002`\u0003H\u0000\u001a\u001e\u0010\u0005\u001a\b\u0012\u0002\b\u0003\u0018\u00010\u0006*\u00020\u00072\n\u0010\b\u001a\u00060\u0002j\u0002`\u0003H\u0000¨\u0006\t"}, d2 = {"toClassId", "Lkotlin/reflect/jvm/internal/impl/name/ClassId;", "", "Lkotlin/reflect/jvm/internal/impl/km/ClassName;", "toNonLocalSimpleName", "loadKClass", "Lkotlin/reflect/KClass;", "Ljava/lang/ClassLoader;", "name", "kotlin-reflection"}, k = 2, mv = {2, 2, 0}, xi = 48)
public final class MetadataUtilKt {
    public static final ClassId toClassId(String str) {
        Intrinsics.checkNotNullParameter(str, "<this>");
        boolean zStartsWith$default = StringsKt.startsWith$default(str, ".", false, 2, (Object) null);
        if (zStartsWith$default) {
            str = str.substring(1);
            Intrinsics.checkNotNullExpressionValue(str, "substring(...)");
        }
        return new ClassId(new FqName(StringsKt.replace$default(StringsKt.substringBeforeLast(str, '/', ""), '/', '.', false, 4, (Object) null)), new FqName(StringsKt.substringAfterLast$default(str, '/', (String) null, 2, (Object) null)), zStartsWith$default);
    }

    public static final String toNonLocalSimpleName(String str) {
        Intrinsics.checkNotNullParameter(str, "<this>");
        if (StringsKt.startsWith$default(str, ".", false, 2, (Object) null)) {
            throw new IllegalArgumentException(("Local class is not supported: " + str).toString());
        }
        return StringsKt.substringAfterLast$default(StringsKt.substringAfterLast$default(str, '/', (String) null, 2, (Object) null), '.', (String) null, 2, (Object) null);
    }

    public static final KClass<?> loadKClass(ClassLoader classLoader, String name) {
        Intrinsics.checkNotNullParameter(classLoader, "<this>");
        Intrinsics.checkNotNullParameter(name, "name");
        Class clsLoadClass$default = UtilKt.loadClass$default(classLoader, toClassId(name), 0, 2, null);
        if (clsLoadClass$default != null) {
            return JvmClassMappingKt.getKotlinClass(clsLoadClass$default);
        }
        return null;
    }
}
