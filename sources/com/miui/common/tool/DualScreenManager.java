package com.miui.common.tool;

import com.market.sdk.reflect.ReflectUtilsForMiui;
import kotlin.Metadata;

/* JADX INFO: compiled from: DualScreenManager.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000\u0014\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0002\b\u0004\bÆ\u0002\u0018\u00002\u00020\u0001B\t\b\u0002¢\u0006\u0004\b\u0002\u0010\u0003R\u001a\u0010\u0004\u001a\u00020\u0005X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0004\u0010\u0006\"\u0004\b\u0007\u0010\b¨\u0006\t"}, d2 = {"Lcom/miui/common/tool/DualScreenManager;", "", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "()V", "isPcActivityLiving", "", "()Z", "setPcActivityLiving", "(Z)V", "app_PhoneGlobalRelease"}, k = 1, mv = {2, 2, 0}, xi = 48)
public final class DualScreenManager {
    public static final DualScreenManager INSTANCE = new DualScreenManager();
    private static volatile boolean isPcActivityLiving;

    private DualScreenManager() {
    }

    public final boolean isPcActivityLiving() {
        return isPcActivityLiving;
    }

    public final void setPcActivityLiving(boolean z) {
        isPcActivityLiving = z;
    }
}
