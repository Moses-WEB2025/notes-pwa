package xc.engine.math.iink.api;

import com.market.sdk.reflect.ReflectUtilsForMiui;
import com.miui.maml.folme.AnimatedProperty;
import com.xiaomi.verificationsdk.internal.Constants;
import kotlin.Metadata;

/* JADX INFO: compiled from: StubInkEngine.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000$\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0007\n\u0002\b\b\n\u0002\u0010\t\n\u0002\b\b\n\u0002\u0010\b\n\u0002\b\b\u0018\u00002\u00020\u0001B\u0007¢\u0006\u0004\b\u0002\u0010\u0003R\u001a\u0010\u0004\u001a\u00020\u0005X\u0096\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0006\u0010\u0007\"\u0004\b\b\u0010\tR\u001a\u0010\n\u001a\u00020\u0005X\u0096\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u000b\u0010\u0007\"\u0004\b\f\u0010\tR\u001a\u0010\r\u001a\u00020\u000eX\u0096\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u000f\u0010\u0010\"\u0004\b\u0011\u0010\u0012R\u001a\u0010\u0013\u001a\u00020\u0005X\u0096\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0014\u0010\u0007\"\u0004\b\u0015\u0010\tR\u001a\u0010\u0016\u001a\u00020\u0017X\u0096\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0018\u0010\u0019\"\u0004\b\u001a\u0010\u001bR\u001a\u0010\u001c\u001a\u00020\u0017X\u0096\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u001d\u0010\u0019\"\u0004\b\u001e\u0010\u001b¨\u0006\u001f"}, d2 = {"Lxc/engine/math/iink/api/StubPointerEvent;", "Lxc/engine/math/iink/api/IPointerEvent;", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "()V", AnimatedProperty.PROPERTY_NAME_X, "", "getX", "()F", "setX", "(F)V", AnimatedProperty.PROPERTY_NAME_Y, "getY", "setY", Constants.TIMESTAMP, "", "getT", "()J", "setT", "(J)V", "f", "getF", "setF", "eventType", "", "getEventType", "()I", "setEventType", "(I)V", "pointerId", "getPointerId", "setPointerId", "libXCEngine_release"}, k = 1, mv = {2, 2, 0}, xi = 48)
public final class StubPointerEvent implements IPointerEvent {
    private int eventType;
    private float f;
    private int pointerId;
    private long t;
    private float x;
    private float y;

    @Override // xc.engine.math.iink.api.IPointerEvent
    public float getX() {
        return this.x;
    }

    @Override // xc.engine.math.iink.api.IPointerEvent
    public void setX(float f) {
        this.x = f;
    }

    @Override // xc.engine.math.iink.api.IPointerEvent
    public float getY() {
        return this.y;
    }

    @Override // xc.engine.math.iink.api.IPointerEvent
    public void setY(float f) {
        this.y = f;
    }

    @Override // xc.engine.math.iink.api.IPointerEvent
    public long getT() {
        return this.t;
    }

    @Override // xc.engine.math.iink.api.IPointerEvent
    public void setT(long j) {
        this.t = j;
    }

    @Override // xc.engine.math.iink.api.IPointerEvent
    public float getF() {
        return this.f;
    }

    @Override // xc.engine.math.iink.api.IPointerEvent
    public void setF(float f) {
        this.f = f;
    }

    @Override // xc.engine.math.iink.api.IPointerEvent
    public int getEventType() {
        return this.eventType;
    }

    @Override // xc.engine.math.iink.api.IPointerEvent
    public void setEventType(int i) {
        this.eventType = i;
    }

    @Override // xc.engine.math.iink.api.IPointerEvent
    public int getPointerId() {
        return this.pointerId;
    }

    @Override // xc.engine.math.iink.api.IPointerEvent
    public void setPointerId(int i) {
        this.pointerId = i;
    }
}
