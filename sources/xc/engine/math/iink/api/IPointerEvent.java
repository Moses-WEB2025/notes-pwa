package xc.engine.math.iink.api;

import com.miui.maml.folme.AnimatedProperty;
import com.xiaomi.verificationsdk.internal.Constants;
import kotlin.Metadata;

/* JADX INFO: compiled from: IInkEngine.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000\"\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u0007\n\u0002\b\b\n\u0002\u0010\t\n\u0002\b\b\n\u0002\u0010\b\n\u0002\b\b\bf\u0018\u00002\u00020\u0001R\u0018\u0010\u0002\u001a\u00020\u0003X¦\u000e¢\u0006\f\u001a\u0004\b\u0004\u0010\u0005\"\u0004\b\u0006\u0010\u0007R\u0018\u0010\b\u001a\u00020\u0003X¦\u000e¢\u0006\f\u001a\u0004\b\t\u0010\u0005\"\u0004\b\n\u0010\u0007R\u0018\u0010\u000b\u001a\u00020\fX¦\u000e¢\u0006\f\u001a\u0004\b\r\u0010\u000e\"\u0004\b\u000f\u0010\u0010R\u0018\u0010\u0011\u001a\u00020\u0003X¦\u000e¢\u0006\f\u001a\u0004\b\u0012\u0010\u0005\"\u0004\b\u0013\u0010\u0007R\u0018\u0010\u0014\u001a\u00020\u0015X¦\u000e¢\u0006\f\u001a\u0004\b\u0016\u0010\u0017\"\u0004\b\u0018\u0010\u0019R\u0018\u0010\u001a\u001a\u00020\u0015X¦\u000e¢\u0006\f\u001a\u0004\b\u001b\u0010\u0017\"\u0004\b\u001c\u0010\u0019ø\u0001\u0000\u0082\u0002\u0006\n\u0004\b!0\u0001¨\u0006\u001dÀ\u0006\u0001"}, d2 = {"Lxc/engine/math/iink/api/IPointerEvent;", "", AnimatedProperty.PROPERTY_NAME_X, "", "getX", "()F", "setX", "(F)V", AnimatedProperty.PROPERTY_NAME_Y, "getY", "setY", Constants.TIMESTAMP, "", "getT", "()J", "setT", "(J)V", "f", "getF", "setF", "eventType", "", "getEventType", "()I", "setEventType", "(I)V", "pointerId", "getPointerId", "setPointerId", "libXCEngine_release"}, k = 1, mv = {2, 2, 0}, xi = 48)
public interface IPointerEvent {
    int getEventType();

    float getF();

    int getPointerId();

    long getT();

    float getX();

    float getY();

    void setEventType(int i);

    void setF(float f);

    void setPointerId(int i);

    void setT(long j);

    void setX(float f);

    void setY(float f);
}
