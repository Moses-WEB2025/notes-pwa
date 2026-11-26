package androidx.hardware;

import android.hardware.SyncFence;
import com.market.sdk.reflect.ReflectUtilsForMiui;
import java.time.Duration;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import miuix.navigation.SplitLayout;

/* JADX INFO: compiled from: SyncFenceV33.kt */
/* JADX INFO: loaded from: classes.dex */
@Metadata(d1 = {"\u0000(\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\t\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\u0003\b\u0001\u0018\u00002\u00020\u0001B\u0011\b\u0000\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0004\b\u0004\u0010\u0005J\u0010\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\fH\u0016J\b\u0010\r\u001a\u00020\nH\u0016J\b\u0010\u000e\u001a\u00020\u000fH\u0016J\b\u0010\u0010\u001a\u00020\fH\u0016J\b\u0010\u0011\u001a\u00020\nH\u0016R\u0014\u0010\u0006\u001a\u00020\u0003X\u0080\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\b¨\u0006\u0012"}, d2 = {"Landroidx/hardware/SyncFenceV33;", "Landroidx/hardware/SyncFenceImpl;", "syncFence", "Landroid/hardware/SyncFence;", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "(Landroid/hardware/SyncFence;)V", "mSyncFence", "getMSyncFence$libXCEngine_release", "()Landroid/hardware/SyncFence;", "await", "", "timeoutNanos", "", "awaitForever", SplitLayout.TAG_CLOSE, "", "getSignalTimeNanos", "isValid", "libXCEngine_release"}, k = 1, mv = {2, 2, 0}, xi = 48)
public final class SyncFenceV33 implements SyncFenceImpl {
    private final SyncFence mSyncFence;

    public SyncFenceV33(SyncFence syncFence) {
        Intrinsics.checkNotNullParameter(syncFence, "syncFence");
        this.mSyncFence = syncFence;
    }

    /* JADX INFO: renamed from: getMSyncFence$libXCEngine_release, reason: from getter */
    public final SyncFence getMSyncFence() {
        return this.mSyncFence;
    }

    @Override // androidx.hardware.SyncFenceImpl
    public boolean await(long timeoutNanos) {
        return this.mSyncFence.await(Duration.ofNanos(timeoutNanos));
    }

    @Override // androidx.hardware.SyncFenceImpl
    public boolean awaitForever() {
        return this.mSyncFence.awaitForever();
    }

    @Override // androidx.hardware.SyncFenceImpl
    public void close() {
        this.mSyncFence.close();
    }

    @Override // androidx.hardware.SyncFenceImpl
    public long getSignalTimeNanos() {
        return this.mSyncFence.getSignalTime();
    }

    @Override // androidx.hardware.SyncFenceImpl
    public boolean isValid() {
        return this.mSyncFence.isValid();
    }
}
