package androidx.graphics.opengl.egl;

import com.market.sdk.reflect.ReflectUtilsForMiui;
import com.xiaomi.mirror.RemoteDeviceInfo;
import kotlin.Metadata;

/* JADX INFO: compiled from: EGLSpec.kt */
/* JADX INFO: loaded from: classes.dex */
@Metadata(d1 = {"\u0000\"\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0002\b\u0007\n\u0002\u0010\u000e\n\u0002\b\u0004\n\u0002\u0010\u000b\n\u0002\b\u0004\b\u0086\b\u0018\u0000 \u00132\u00020\u0001:\u0001\u0013B\u0017\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0003¢\u0006\u0004\b\u0005\u0010\u0006J\b\u0010\n\u001a\u00020\u000bH\u0016J\t\u0010\f\u001a\u00020\u0003HÆ\u0003J\t\u0010\r\u001a\u00020\u0003HÆ\u0003J\u001d\u0010\u000e\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u0003HÆ\u0001J\u0013\u0010\u000f\u001a\u00020\u00102\b\u0010\u0011\u001a\u0004\u0018\u00010\u0001HÖ\u0003J\t\u0010\u0012\u001a\u00020\u0003HÖ\u0001R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\bR\u0011\u0010\u0004\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\b¨\u0006\u0014"}, d2 = {"Landroidx/graphics/opengl/egl/EGLVersion;", "", "major", "", "minor", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "(II)V", "getMajor", "()I", "getMinor", "toString", "", "component1", "component2", "copy", "equals", "", RemoteDeviceInfo.MANUFACTURER_OTHER, "hashCode", "Companion", "libXCEngine_release"}, k = 1, mv = {2, 2, 0}, xi = 48)
public final /* data */ class EGLVersion {
    private final int major;
    private final int minor;
    public static final EGLVersion V14 = new EGLVersion(1, 4);
    public static final EGLVersion V15 = new EGLVersion(1, 5);
    public static final EGLVersion Unknown = new EGLVersion(-1, -1);

    public static /* synthetic */ EGLVersion copy$default(EGLVersion eGLVersion, int i, int i2, int i3, Object obj) {
        if ((i3 & 1) != 0) {
            i = eGLVersion.major;
        }
        if ((i3 & 2) != 0) {
            i2 = eGLVersion.minor;
        }
        return eGLVersion.copy(i, i2);
    }

    /* JADX INFO: renamed from: component1, reason: from getter */
    public final int getMajor() {
        return this.major;
    }

    /* JADX INFO: renamed from: component2, reason: from getter */
    public final int getMinor() {
        return this.minor;
    }

    public final EGLVersion copy(int major, int minor) {
        return new EGLVersion(major, minor);
    }

    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof EGLVersion)) {
            return false;
        }
        EGLVersion eGLVersion = (EGLVersion) other;
        return this.major == eGLVersion.major && this.minor == eGLVersion.minor;
    }

    public int hashCode() {
        return (Integer.hashCode(this.major) * 31) + Integer.hashCode(this.minor);
    }

    public EGLVersion(int i, int i2) {
        this.major = i;
        this.minor = i2;
    }

    public final int getMajor() {
        return this.major;
    }

    public final int getMinor() {
        return this.minor;
    }

    public String toString() {
        return "EGL version " + this.major + "." + this.minor;
    }
}
