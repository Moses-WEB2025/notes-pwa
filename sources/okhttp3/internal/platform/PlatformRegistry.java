package okhttp3.internal.platform;

import android.content.Context;
import android.os.Build;
import com.market.sdk.reflect.ReflectUtilsForMiui;
import kotlin.Metadata;
import okhttp3.internal.platform.android.AndroidLog;

/* JADX INFO: compiled from: PlatformRegistry.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000\"\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0006\bÆ\u0002\u0018\u00002\u00020\u0001B\t\b\u0002¢\u0006\u0004\b\u0002\u0010\u0003J\u0006\u0010\u0004\u001a\u00020\u0005R\u0011\u0010\u0006\u001a\u00020\u00078F¢\u0006\u0006\u001a\u0004\b\u0006\u0010\bR(\u0010\u000b\u001a\u0004\u0018\u00010\n2\b\u0010\t\u001a\u0004\u0018\u00010\n8F@FX\u0086\u000e¢\u0006\f\u001a\u0004\b\f\u0010\r\"\u0004\b\u000e\u0010\u000f¨\u0006\u0010"}, d2 = {"Lokhttp3/internal/platform/PlatformRegistry;", "", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "()V", "findPlatform", "Lokhttp3/internal/platform/Platform;", "isAndroid", "", "()Z", "value", "Landroid/content/Context;", "applicationContext", "getApplicationContext", "()Landroid/content/Context;", "setApplicationContext", "(Landroid/content/Context;)V", "okhttp"}, k = 1, mv = {2, 2, 0}, xi = 48)
public final class PlatformRegistry {
    public static final PlatformRegistry INSTANCE = new PlatformRegistry();

    public final boolean isAndroid() {
        return true;
    }

    private PlatformRegistry() {
    }

    public final Platform findPlatform() {
        AndroidLog.INSTANCE.enable();
        Platform platformBuildIfSupported = Android10Platform.INSTANCE.buildIfSupported();
        if (platformBuildIfSupported == null) {
            platformBuildIfSupported = AndroidPlatform.INSTANCE.buildIfSupported();
        }
        if (platformBuildIfSupported != null) {
            return platformBuildIfSupported;
        }
        throw new IllegalStateException("Expected Android API level 21+ but was " + Build.VERSION.SDK_INT);
    }

    public final Context getApplicationContext() {
        Object obj = Platform.INSTANCE.get();
        ContextAwarePlatform contextAwarePlatform = obj instanceof ContextAwarePlatform ? (ContextAwarePlatform) obj : null;
        if (contextAwarePlatform != null) {
            return contextAwarePlatform.getApplicationContext();
        }
        return null;
    }

    public final void setApplicationContext(Context context) {
        Object obj = Platform.INSTANCE.get();
        ContextAwarePlatform contextAwarePlatform = obj instanceof ContextAwarePlatform ? (ContextAwarePlatform) obj : null;
        if (contextAwarePlatform != null) {
            contextAwarePlatform.setApplicationContext(context);
        }
    }
}
