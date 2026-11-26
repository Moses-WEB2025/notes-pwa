package androidx.hardware;

import androidx.graphics.utils.JniVisible;
import com.market.sdk.reflect.ReflectUtilsForMiui;
import kotlin.Metadata;
import kotlin.jvm.JvmStatic;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* JADX INFO: compiled from: SyncFenceBindings.kt */
/* JADX INFO: loaded from: classes.dex */
@Metadata(d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0004\b\u0001\u0018\u0000 \u00042\u00020\u0001:\u0001\u0004B\t\b\u0002¢\u0006\u0004\b\u0002\u0010\u0003¨\u0006\u0005"}, d2 = {"Landroidx/hardware/SyncFenceBindings;", "", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "()V", "Companion", "libXCEngine_release"}, k = 1, mv = {2, 2, 0}, xi = 48)
@JniVisible
public final class SyncFenceBindings {

    /* JADX INFO: renamed from: Companion, reason: from kotlin metadata */
    public static final Companion INSTANCE = new Companion(null);

    @JvmStatic
    @JniVisible
    public static final native void nForceClose(int i);

    @JvmStatic
    @JniVisible
    public static final native long nGetSignalTime(int i);

    @JvmStatic
    @JniVisible
    public static final native boolean nResolveSyncFileInfo();

    @JvmStatic
    @JniVisible
    public static final native boolean nResolveSyncFileInfoFree();

    /* JADX INFO: compiled from: SyncFenceBindings.kt */
    @Metadata(d1 = {"\u0000&\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\t\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u0002\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\t\b\u0002¢\u0006\u0004\b\u0002\u0010\u0003J\t\u0010\u0004\u001a\u00020\u0005H\u0087 J\t\u0010\u0006\u001a\u00020\u0005H\u0087 J\u0011\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\nH\u0087 J\u0011\u0010\u000b\u001a\u00020\f2\u0006\u0010\t\u001a\u00020\nH\u0087 ¨\u0006\r"}, d2 = {"Landroidx/hardware/SyncFenceBindings$Companion;", "", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "()V", "nResolveSyncFileInfo", "", "nResolveSyncFileInfoFree", "nGetSignalTime", "", "fd", "", "nForceClose", "", "libXCEngine_release"}, k = 1, mv = {2, 2, 0}, xi = 48)
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        @JvmStatic
        @JniVisible
        public final void nForceClose(int fd) {
            SyncFenceBindings.nForceClose(fd);
        }

        @JvmStatic
        @JniVisible
        public final long nGetSignalTime(int fd) {
            return SyncFenceBindings.nGetSignalTime(fd);
        }

        @JvmStatic
        @JniVisible
        public final boolean nResolveSyncFileInfo() {
            return SyncFenceBindings.nResolveSyncFileInfo();
        }

        @JvmStatic
        @JniVisible
        public final boolean nResolveSyncFileInfoFree() {
            return SyncFenceBindings.nResolveSyncFileInfoFree();
        }

        private Companion() {
        }
    }

    private SyncFenceBindings() {
    }

    static {
        System.loadLibrary("graphics-core");
    }
}
