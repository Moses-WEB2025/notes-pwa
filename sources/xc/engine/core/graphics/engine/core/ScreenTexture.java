package xc.engine.core.graphics.engine.core;

import com.market.sdk.reflect.ReflectUtilsForMiui;
import com.miui.doodle.document.Layer;
import com.xiaomi.mirror.RemoteDeviceInfo;
import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: BaseEngineStruct.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000(\n\u0002\u0018\u0002\n\u0002\u0010\u000f\n\u0000\n\u0002\u0010\b\n\u0002\b\u000b\n\u0002\u0010\u000b\n\u0002\b\u0007\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0000\b\u0086\b\u0018\u00002\b\u0012\u0004\u0012\u00020\u00000\u0001B%\u0012\b\b\u0002\u0010\u0002\u001a\u00020\u0003\u0012\b\b\u0002\u0010\u0004\u001a\u00020\u0003\u0012\b\b\u0002\u0010\u0005\u001a\u00020\u0003¢\u0006\u0004\b\u0006\u0010\u0007B\u0011\b\u0016\u0012\u0006\u0010\b\u001a\u00020\u0000¢\u0006\u0004\b\u0006\u0010\tJ\u0006\u0010\u000e\u001a\u00020\u000fJ\u0006\u0010\u0010\u001a\u00020\u000fJ\u0011\u0010\u0011\u001a\u00020\u00032\u0006\u0010\b\u001a\u00020\u0000H\u0096\u0002J\t\u0010\u0012\u001a\u00020\u0003HÆ\u0003J\t\u0010\u0013\u001a\u00020\u0003HÆ\u0003J\t\u0010\u0014\u001a\u00020\u0003HÆ\u0003J'\u0010\u0015\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u00032\b\b\u0002\u0010\u0005\u001a\u00020\u0003HÆ\u0001J\u0013\u0010\u0016\u001a\u00020\u000f2\b\u0010\b\u001a\u0004\u0018\u00010\u0017HÖ\u0003J\t\u0010\u0018\u001a\u00020\u0003HÖ\u0001J\t\u0010\u0019\u001a\u00020\u001aHÖ\u0001R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\n\u0010\u000bR\u0011\u0010\u0004\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\f\u0010\u000bR\u0011\u0010\u0005\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\r\u0010\u000b¨\u0006\u001b"}, d2 = {"Lxc/engine/core/graphics/engine/core/ScreenTexture;", "", "texId", "", Layer.KEY_WIDTH, Layer.KEY_HEIGHT, ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "(III)V", RemoteDeviceInfo.MANUFACTURER_OTHER, "(Lxc/engine/core/graphics/engine/core/ScreenTexture;)V", "getTexId", "()I", "getWidth", "getHeight", "isValid", "", "isNotValid", "compareTo", "component1", "component2", "component3", "copy", "equals", "", "hashCode", "toString", "", "libXCEngine_release"}, k = 1, mv = {2, 2, 0}, xi = 48)
public final /* data */ class ScreenTexture implements Comparable<ScreenTexture> {
    private final int height;
    private final int texId;
    private final int width;

    public ScreenTexture() {
        this(0, 0, 0, 7, null);
    }

    public static /* synthetic */ ScreenTexture copy$default(ScreenTexture screenTexture, int i, int i2, int i3, int i4, Object obj) {
        if ((i4 & 1) != 0) {
            i = screenTexture.texId;
        }
        if ((i4 & 2) != 0) {
            i2 = screenTexture.width;
        }
        if ((i4 & 4) != 0) {
            i3 = screenTexture.height;
        }
        return screenTexture.copy(i, i2, i3);
    }

    /* JADX INFO: renamed from: component1, reason: from getter */
    public final int getTexId() {
        return this.texId;
    }

    /* JADX INFO: renamed from: component2, reason: from getter */
    public final int getWidth() {
        return this.width;
    }

    /* JADX INFO: renamed from: component3, reason: from getter */
    public final int getHeight() {
        return this.height;
    }

    public final ScreenTexture copy(int texId, int width, int height) {
        return new ScreenTexture(texId, width, height);
    }

    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof ScreenTexture)) {
            return false;
        }
        ScreenTexture screenTexture = (ScreenTexture) other;
        return this.texId == screenTexture.texId && this.width == screenTexture.width && this.height == screenTexture.height;
    }

    public int hashCode() {
        return (((Integer.hashCode(this.texId) * 31) + Integer.hashCode(this.width)) * 31) + Integer.hashCode(this.height);
    }

    public String toString() {
        return "ScreenTexture(texId=" + this.texId + ", width=" + this.width + ", height=" + this.height + ")";
    }

    public ScreenTexture(int i, int i2, int i3) {
        this.texId = i;
        this.width = i2;
        this.height = i3;
    }

    public /* synthetic */ ScreenTexture(int i, int i2, int i3, int i4, DefaultConstructorMarker defaultConstructorMarker) {
        this((i4 & 1) != 0 ? 0 : i, (i4 & 2) != 0 ? 0 : i2, (i4 & 4) != 0 ? 0 : i3);
    }

    public final int getHeight() {
        return this.height;
    }

    public final int getTexId() {
        return this.texId;
    }

    public final int getWidth() {
        return this.width;
    }

    /* JADX WARN: 'this' call moved to the top of the method (can break code semantics) */
    public ScreenTexture(ScreenTexture other) {
        this(other.texId, other.width, other.height);
        Intrinsics.checkNotNullParameter(other, "other");
    }

    public final boolean isValid() {
        return this.texId > 0 && this.width > 0 && this.height > 0;
    }

    public final boolean isNotValid() {
        return !isValid();
    }

    @Override // java.lang.Comparable
    public int compareTo(ScreenTexture other) {
        Intrinsics.checkNotNullParameter(other, "other");
        return Intrinsics.compare(this.texId, other.texId);
    }
}
