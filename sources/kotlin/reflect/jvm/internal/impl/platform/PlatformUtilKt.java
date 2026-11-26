package kotlin.reflect.jvm.internal.impl.platform;

import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import net.lingala.zip4j.util.InternalZipConstants;

/* JADX INFO: compiled from: platformUtil.kt */
/* JADX INFO: loaded from: classes5.dex */
public final class PlatformUtilKt {
    public static final String getPresentableDescription(TargetPlatform targetPlatform) {
        Intrinsics.checkNotNullParameter(targetPlatform, "<this>");
        return CollectionsKt.joinToString$default(targetPlatform.getComponentPlatforms(), InternalZipConstants.ZIP_FILE_SEPARATOR, null, null, 0, null, null, 62, null);
    }
}
