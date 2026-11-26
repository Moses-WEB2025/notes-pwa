package com.miui.aiengine.common.utils;

import android.util.Log;
import com.market.sdk.reflect.ReflectUtilsForMiui;
import kotlin.Lazy;
import kotlin.LazyKt;
import kotlin.Metadata;
import kotlin.jvm.functions.Function0;

/* JADX INFO: compiled from: CloudSdkDependencyUtils.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000\u001a\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\b\n\u0002\b\u0006\bÆ\u0002\u0018\u00002\u00020\u0001B\t\b\u0002¢\u0006\u0004\b\u0002\u0010\u0003J\b\u0010\f\u001a\u00020\u0007H\u0002R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082T¢\u0006\u0002\n\u0000R\u001b\u0010\u0006\u001a\u00020\u00078FX\u0086\u0084\u0002¢\u0006\f\n\u0004\b\n\u0010\u000b\u001a\u0004\b\b\u0010\t¨\u0006\r"}, d2 = {"Lcom/miui/aiengine/common/utils/CloudSdkDependencyUtils;", "", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "()V", "TAG", "", "sdkEnvironmentVersion", "", "getSdkEnvironmentVersion", "()I", "sdkEnvironmentVersion$delegate", "Lkotlin/Lazy;", "getSDKEnvironment", "net_release"}, k = 1, mv = {2, 2, 0}, xi = 48)
public final class CloudSdkDependencyUtils {
    public static final CloudSdkDependencyUtils INSTANCE;
    private static final String TAG = "CloudSdkDependencyUtils";

    /* JADX INFO: renamed from: sdkEnvironmentVersion$delegate, reason: from kotlin metadata */
    private static final Lazy sdkEnvironmentVersion;

    private CloudSdkDependencyUtils() {
    }

    static {
        CloudSdkDependencyUtils cloudSdkDependencyUtils = new CloudSdkDependencyUtils();
        INSTANCE = cloudSdkDependencyUtils;
        sdkEnvironmentVersion = LazyKt.lazy(new Function0() { // from class: com.miui.aiengine.common.utils.CloudSdkDependencyUtils$$ExternalSyntheticLambda0
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                return Integer.valueOf(CloudSdkDependencyUtils.sdkEnvironmentVersion_delegate$lambda$0());
            }
        });
        Log.i(TAG, "MiCloudSDK environment: " + cloudSdkDependencyUtils.getSdkEnvironmentVersion());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final int sdkEnvironmentVersion_delegate$lambda$0() {
        return INSTANCE.getSDKEnvironment();
    }

    public final int getSdkEnvironmentVersion() {
        return ((Number) sdkEnvironmentVersion.getValue()).intValue();
    }

    private final int getSDKEnvironment() {
        Class<?> clsLoadClass = ReflectUtils.INSTANCE.loadClass("com.xiaomi.micloudsdk.os.MiCloudSdkVersion");
        if (clsLoadClass != null) {
            return ReflectUtils.INSTANCE.getStaticFieldIntValue(clsLoadClass, "version");
        }
        if (ReflectUtils.INSTANCE.loadClass("miui.cloud.helper.BroadcastIntentHelper") != null) {
            return 25;
        }
        return ReflectUtils.INSTANCE.loadClass("com.xiaomi.micloudsdk.utils.MiCloudRuntimeConstants") != null ? 18 : -1;
    }
}
