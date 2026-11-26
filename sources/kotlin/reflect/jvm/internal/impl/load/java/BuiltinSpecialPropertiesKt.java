package kotlin.reflect.jvm.internal.impl.load.java;

import kotlin.jvm.internal.Intrinsics;
import kotlin.reflect.jvm.internal.impl.name.FqName;
import kotlin.reflect.jvm.internal.impl.name.FqNameUnsafe;
import kotlin.reflect.jvm.internal.impl.name.Name;

/* JADX INFO: compiled from: BuiltinSpecialProperties.kt */
/* JADX INFO: loaded from: classes5.dex */
public final class BuiltinSpecialPropertiesKt {
    /* JADX INFO: Access modifiers changed from: private */
    public static final FqName child(FqName fqName, String str) {
        Name nameIdentifier = Name.identifier(str);
        Intrinsics.checkNotNullExpressionValue(nameIdentifier, "identifier(...)");
        return fqName.child(nameIdentifier);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final FqName childSafe(FqNameUnsafe fqNameUnsafe, String str) {
        Name nameIdentifier = Name.identifier(str);
        Intrinsics.checkNotNullExpressionValue(nameIdentifier, "identifier(...)");
        return fqNameUnsafe.child(nameIdentifier).toSafe();
    }
}
