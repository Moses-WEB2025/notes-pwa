package androidx.graphics.lowlatency;

import com.market.sdk.reflect.ReflectUtilsForMiui;
import com.miui.doodle.document.Layer;
import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* JADX INFO: compiled from: BufferInfo.kt */
/* JADX INFO: loaded from: classes.dex */
@Metadata(d1 = {"\u0000\u0012\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0002\b\u000e\u0018\u00002\u00020\u0001B%\u0012\b\b\u0002\u0010\u0002\u001a\u00020\u0003\u0012\b\b\u0002\u0010\u0004\u001a\u00020\u0003\u0012\b\b\u0002\u0010\u0005\u001a\u00020\u0003¢\u0006\u0004\b\u0006\u0010\u0007R$\u0010\u0002\u001a\u00020\u00032\u0006\u0010\b\u001a\u00020\u0003@@X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\t\u0010\n\"\u0004\b\u000b\u0010\fR$\u0010\u0004\u001a\u00020\u00032\u0006\u0010\b\u001a\u00020\u0003@@X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\r\u0010\n\"\u0004\b\u000e\u0010\fR$\u0010\u0005\u001a\u00020\u00032\u0006\u0010\b\u001a\u00020\u0003@@X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u000f\u0010\n\"\u0004\b\u0010\u0010\f¨\u0006\u0011"}, d2 = {"Landroidx/graphics/lowlatency/BufferInfo;", "", Layer.KEY_WIDTH, "", Layer.KEY_HEIGHT, "frameBufferId", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "(III)V", "value", "getWidth", "()I", "setWidth$libXCEngine_release", "(I)V", "getHeight", "setHeight$libXCEngine_release", "getFrameBufferId", "setFrameBufferId$libXCEngine_release", "libXCEngine_release"}, k = 1, mv = {2, 2, 0}, xi = 48)
public final class BufferInfo {
    private int frameBufferId;
    private int height;
    private int width;

    public BufferInfo() {
        this(0, 0, 0, 7, null);
    }

    public BufferInfo(int i, int i2, int i3) {
        this.width = i;
        this.height = i2;
        this.frameBufferId = i3;
    }

    public /* synthetic */ BufferInfo(int i, int i2, int i3, int i4, DefaultConstructorMarker defaultConstructorMarker) {
        this((i4 & 1) != 0 ? 0 : i, (i4 & 2) != 0 ? 0 : i2, (i4 & 4) != 0 ? -1 : i3);
    }

    public final int getWidth() {
        return this.width;
    }

    public final void setWidth$libXCEngine_release(int i) {
        this.width = i;
    }

    public final int getHeight() {
        return this.height;
    }

    public final void setHeight$libXCEngine_release(int i) {
        this.height = i;
    }

    public final int getFrameBufferId() {
        return this.frameBufferId;
    }

    public final void setFrameBufferId$libXCEngine_release(int i) {
        this.frameBufferId = i;
    }
}
