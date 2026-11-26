package androidx.opengl;

import com.market.sdk.reflect.ReflectUtilsForMiui;
import com.xiaomi.mirror.RemoteDeviceInfo;
import kotlin.Metadata;

/* JADX INFO: compiled from: EGLImageKHR.kt */
/* JADX INFO: loaded from: classes.dex */
@Metadata(d1 = {"\u0000*\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\t\n\u0002\b\u0005\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0000\u0018\u00002\u00020\u0001B\u000f\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0004\b\u0004\u0010\u0005J\u0013\u0010\b\u001a\u00020\t2\b\u0010\n\u001a\u0004\u0018\u00010\u000bH\u0096\u0002J\b\u0010\f\u001a\u00020\rH\u0016J\b\u0010\u000e\u001a\u00020\u000fH\u0016R\u0014\u0010\u0002\u001a\u00020\u0003X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0006\u0010\u0007¨\u0006\u0010"}, d2 = {"Landroidx/opengl/EGLImageKHR;", "Landroidx/opengl/EGLHandle;", "nativeHandle", "", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "(J)V", "getNativeHandle", "()J", "equals", "", RemoteDeviceInfo.MANUFACTURER_OTHER, "", "hashCode", "", "toString", "", "libXCEngine_release"}, k = 1, mv = {2, 2, 0}, xi = 48)
public final class EGLImageKHR implements EGLHandle {
    private final long nativeHandle;

    public EGLImageKHR(long j) {
        this.nativeHandle = j;
    }

    @Override // androidx.opengl.EGLHandle
    public long getNativeHandle() {
        return this.nativeHandle;
    }

    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        return (other instanceof EGLImageKHR) && getNativeHandle() == ((EGLImageKHR) other).getNativeHandle();
    }

    public int hashCode() {
        return Long.hashCode(getNativeHandle());
    }

    public String toString() {
        return "EGLImageKHR(nativeHandle=" + getNativeHandle() + ")";
    }
}
