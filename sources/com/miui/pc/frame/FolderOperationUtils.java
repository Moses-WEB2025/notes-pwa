package com.miui.pc.frame;

import android.content.Context;
import ch.qos.logback.core.CoreConstants;
import com.market.sdk.reflect.ReflectUtilsForMiui;
import com.miui.notes.store.FolderStore;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: FolderOperationUtils.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0003\n\u0002\u0010\t\n\u0000\n\u0002\u0018\u0002\n\u0000\bÆ\u0002\u0018\u00002\u00020\u0001B\t\b\u0002¢\u0006\u0004\b\u0002\u0010\u0003J\u000e\u0010\u0004\u001a\u00020\u00052\u0006\u0010\u0006\u001a\u00020\u0007¨\u0006\b"}, d2 = {"Lcom/miui/pc/frame/FolderOperationUtils;", "", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "()V", "addFolder", "", CoreConstants.CONTEXT_SCOPE_VALUE, "Landroid/content/Context;", "app_PhoneGlobalRelease"}, k = 1, mv = {2, 2, 0}, xi = 48)
public final class FolderOperationUtils {
    public static final FolderOperationUtils INSTANCE = new FolderOperationUtils();

    private FolderOperationUtils() {
    }

    public final long addFolder(Context context) {
        Intrinsics.checkNotNullParameter(context, "context");
        String str = LeftFragment.mDefaultFolderName;
        long jAdd$default = FolderStore.add$default(FolderStore.INSTANCE, context, str, 0L, 4, null);
        if (jAdd$default > 0) {
            return jAdd$default;
        }
        Intrinsics.checkNotNull(str);
        return FolderStore.restore(context, str) ? FolderStore.subjectToId(context, str) : jAdd$default;
    }
}
