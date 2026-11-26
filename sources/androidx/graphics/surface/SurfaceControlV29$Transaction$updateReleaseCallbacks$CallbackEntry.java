package androidx.graphics.surface;

import androidx.hardware.SyncFenceCompat;
import com.market.sdk.reflect.ReflectUtilsForMiui;
import com.xiaomi.mirror.RemoteDeviceInfo;
import com.xiaomi.mirror.synergy.CallMethod;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: SurfaceControlV29.kt */
/* JADX INFO: loaded from: classes.dex */
@Metadata(d1 = {"\u00009\n\u0000\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0002\u0018\u0002\n\u0002\b\u000b\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0000*\u0001\u0000\b\u008a\b\u0018\u00002\u00020\u0001B+\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u001a\u0010\u0004\u001a\u0016\u0012\u0004\u0012\u00020\u0006\u0012\u0004\u0012\u00020\u0007\u0018\u00010\u0005j\u0004\u0018\u0001`\b¢\u0006\u0004\b\t\u0010\nJ\t\u0010\u000f\u001a\u00020\u0003HÆ\u0003J\u001d\u0010\u0010\u001a\u0016\u0012\u0004\u0012\u00020\u0006\u0012\u0004\u0012\u00020\u0007\u0018\u00010\u0005j\u0004\u0018\u0001`\bHÆ\u0003J6\u0010\u0011\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\u001c\b\u0002\u0010\u0004\u001a\u0016\u0012\u0004\u0012\u00020\u0006\u0012\u0004\u0012\u00020\u0007\u0018\u00010\u0005j\u0004\u0018\u0001`\bHÆ\u0001¢\u0006\u0002\u0010\u0012J\u0013\u0010\u0013\u001a\u00020\u00142\b\u0010\u0015\u001a\u0004\u0018\u00010\u0001HÖ\u0003J\t\u0010\u0016\u001a\u00020\u0017HÖ\u0001J\t\u0010\u0018\u001a\u00020\u0019HÖ\u0001R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\fR%\u0010\u0004\u001a\u0016\u0012\u0004\u0012\u00020\u0006\u0012\u0004\u0012\u00020\u0007\u0018\u00010\u0005j\u0004\u0018\u0001`\b¢\u0006\b\n\u0000\u001a\u0004\b\r\u0010\u000e¨\u0006\u001a"}, d2 = {"androidx/graphics/surface/SurfaceControlV29$Transaction$updateReleaseCallbacks$CallbackEntry", "", "surfaceControl", "Landroidx/graphics/surface/SurfaceControlV29;", CallMethod.ARG_CALLBACK, "Lkotlin/Function1;", "Landroidx/hardware/SyncFenceCompat;", "", "Landroidx/graphics/surface/ReleaseCallback;", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "(Landroidx/graphics/surface/SurfaceControlV29;Lkotlin/jvm/functions/Function1;)V", "getSurfaceControl", "()Landroidx/graphics/surface/SurfaceControlV29;", "getCallback", "()Lkotlin/jvm/functions/Function1;", "component1", "component2", "copy", "(Landroidx/graphics/surface/SurfaceControlV29;Lkotlin/jvm/functions/Function1;)Landroidx/graphics/surface/SurfaceControlV29$Transaction$updateReleaseCallbacks$CallbackEntry;", "equals", "", RemoteDeviceInfo.MANUFACTURER_OTHER, "hashCode", "", "toString", "", "libXCEngine_release"}, k = 1, mv = {2, 2, 0}, xi = 48)
public final /* data */ class SurfaceControlV29$Transaction$updateReleaseCallbacks$CallbackEntry {
    private final Function1<SyncFenceCompat, Unit> callback;
    private final SurfaceControlV29 surfaceControl;

    /* JADX WARN: Multi-variable type inference failed */
    public static /* synthetic */ SurfaceControlV29$Transaction$updateReleaseCallbacks$CallbackEntry copy$default(SurfaceControlV29$Transaction$updateReleaseCallbacks$CallbackEntry surfaceControlV29$Transaction$updateReleaseCallbacks$CallbackEntry, SurfaceControlV29 surfaceControlV29, Function1 function1, int i, Object obj) {
        if ((i & 1) != 0) {
            surfaceControlV29 = surfaceControlV29$Transaction$updateReleaseCallbacks$CallbackEntry.surfaceControl;
        }
        if ((i & 2) != 0) {
            function1 = surfaceControlV29$Transaction$updateReleaseCallbacks$CallbackEntry.callback;
        }
        return surfaceControlV29$Transaction$updateReleaseCallbacks$CallbackEntry.copy(surfaceControlV29, function1);
    }

    /* JADX INFO: renamed from: component1, reason: from getter */
    public final SurfaceControlV29 getSurfaceControl() {
        return this.surfaceControl;
    }

    public final Function1<SyncFenceCompat, Unit> component2() {
        return this.callback;
    }

    public final SurfaceControlV29$Transaction$updateReleaseCallbacks$CallbackEntry copy(SurfaceControlV29 surfaceControl, Function1<? super SyncFenceCompat, Unit> callback) {
        Intrinsics.checkNotNullParameter(surfaceControl, "surfaceControl");
        return new SurfaceControlV29$Transaction$updateReleaseCallbacks$CallbackEntry(surfaceControl, callback);
    }

    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof SurfaceControlV29$Transaction$updateReleaseCallbacks$CallbackEntry)) {
            return false;
        }
        SurfaceControlV29$Transaction$updateReleaseCallbacks$CallbackEntry surfaceControlV29$Transaction$updateReleaseCallbacks$CallbackEntry = (SurfaceControlV29$Transaction$updateReleaseCallbacks$CallbackEntry) other;
        return Intrinsics.areEqual(this.surfaceControl, surfaceControlV29$Transaction$updateReleaseCallbacks$CallbackEntry.surfaceControl) && Intrinsics.areEqual(this.callback, surfaceControlV29$Transaction$updateReleaseCallbacks$CallbackEntry.callback);
    }

    public int hashCode() {
        int iHashCode = this.surfaceControl.hashCode() * 31;
        Function1<SyncFenceCompat, Unit> function1 = this.callback;
        return iHashCode + (function1 == null ? 0 : function1.hashCode());
    }

    public String toString() {
        return "CallbackEntry(surfaceControl=" + this.surfaceControl + ", callback=" + this.callback + ")";
    }

    /* JADX WARN: Multi-variable type inference failed */
    public SurfaceControlV29$Transaction$updateReleaseCallbacks$CallbackEntry(SurfaceControlV29 surfaceControl, Function1<? super SyncFenceCompat, Unit> function1) {
        Intrinsics.checkNotNullParameter(surfaceControl, "surfaceControl");
        this.surfaceControl = surfaceControl;
        this.callback = function1;
    }

    public final SurfaceControlV29 getSurfaceControl() {
        return this.surfaceControl;
    }

    public final Function1<SyncFenceCompat, Unit> getCallback() {
        return this.callback;
    }
}
