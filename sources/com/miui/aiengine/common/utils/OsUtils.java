package com.miui.aiengine.common.utils;

import android.os.Build;
import android.text.TextUtils;
import android.util.Log;
import com.market.sdk.reflect.ReflectUtilsForMiui;
import kotlin.Lazy;
import kotlin.LazyKt;
import kotlin.Metadata;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: OsUtils.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000\u0014\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0002\b\u0006\bÆ\u0002\u0018\u00002\u00020\u0001B\t\b\u0002¢\u0006\u0004\b\u0002\u0010\u0003J\b\u0010\n\u001a\u00020\u0005H\u0002R\u001b\u0010\u0004\u001a\u00020\u00058FX\u0086\u0084\u0002¢\u0006\f\n\u0004\b\b\u0010\t\u001a\u0004\b\u0006\u0010\u0007¨\u0006\u000b"}, d2 = {"Lcom/miui/aiengine/common/utils/OsUtils;", "", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "()V", "UA", "", "getUA", "()Ljava/lang/String;", "UA$delegate", "Lkotlin/Lazy;", "getDeviceUA", "net_release"}, k = 1, mv = {2, 2, 0}, xi = 48)
public final class OsUtils {
    public static final OsUtils INSTANCE = new OsUtils();

    /* JADX INFO: renamed from: UA$delegate, reason: from kotlin metadata */
    private static final Lazy UA = LazyKt.lazy(new Function0() { // from class: com.miui.aiengine.common.utils.OsUtils$$ExternalSyntheticLambda0
        @Override // kotlin.jvm.functions.Function0
        public final Object invoke() {
            return OsUtils.UA_delegate$lambda$0();
        }
    });

    private OsUtils() {
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final String UA_delegate$lambda$0() {
        return INSTANCE.getDeviceUA();
    }

    public final String getUA() {
        return (String) UA.getValue();
    }

    private final String getDeviceUA() {
        StringBuilder sb = new StringBuilder();
        sb.append(Build.MODEL).append("; MIUI/").append(Build.VERSION.INCREMENTAL);
        String str = SystemPropertiesUtils.INSTANCE.get("ro.product.marketname");
        if (!TextUtils.isEmpty(str)) {
            sb.append("; ").append(str);
        }
        try {
            Object obj = Class.forName("miui.os.Build").getField("IS_ALPHA_BUILD").get(null);
            Intrinsics.checkNotNull(obj, "null cannot be cast to non-null type kotlin.Boolean");
            if (((Boolean) obj).booleanValue()) {
                sb.append(' ').append("ALPHA");
            }
        } catch (Exception e) {
            Log.e("OsUtils", "Not in MIUI in getUserAgent " + e.getMessage());
        }
        String string = sb.toString();
        Intrinsics.checkNotNullExpressionValue(string, "toString(...)");
        return string;
    }
}
