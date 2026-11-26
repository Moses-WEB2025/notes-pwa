package kotlin.reflect.jvm.internal.impl.descriptors.runtime.components;

import kotlin.reflect.jvm.internal.impl.name.ClassId;
import kotlin.text.StringsKt;

/* JADX INFO: compiled from: ReflectKotlinClassFinder.kt */
/* JADX INFO: loaded from: classes5.dex */
public final class ReflectKotlinClassFinderKt {
    /* JADX INFO: Access modifiers changed from: private */
    public static final String toRuntimeFqName(ClassId classId) {
        String strReplace$default = StringsKt.replace$default(classId.getRelativeClassName().asString(), '.', '$', false, 4, (Object) null);
        return classId.getPackageFqName().isRoot() ? strReplace$default : classId.getPackageFqName() + '.' + strReplace$default;
    }
}
