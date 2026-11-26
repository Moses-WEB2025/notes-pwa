package xc.engine.operate.edit.select.selectInfo;

import com.market.sdk.reflect.ReflectUtilsForMiui;
import kotlin.Metadata;

/* JADX INFO: compiled from: AbsSelectInfo.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000\u001c\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0003\n\u0002\u0010\u0007\n\u0002\b\u0014\n\u0002\u0010\u0002\n\u0002\b\u0011\b&\u0018\u00002\u00020\u0001B\u0007¢\u0006\u0004\b\u0002\u0010\u0003J\u0016\u0010\u0019\u001a\u00020\u001a2\u0006\u0010\u001b\u001a\u00020\u00052\u0006\u0010\u001c\u001a\u00020\u0005J\u001e\u0010\u001d\u001a\u00020\u001a2\u0006\u0010\u001e\u001a\u00020\u00052\u0006\u0010\u001f\u001a\u00020\u00052\u0006\u0010 \u001a\u00020\u0005J\u001e\u0010!\u001a\u00020\u001a2\u0006\u0010\u001e\u001a\u00020\u00052\u0006\u0010\u001f\u001a\u00020\u00052\u0006\u0010\"\u001a\u00020\u0005J\u0006\u0010#\u001a\u00020\u0005J\u0006\u0010$\u001a\u00020\u0005J\u0006\u0010%\u001a\u00020\u0005J\u0006\u0010&\u001a\u00020\u0005J\u0006\u0010'\u001a\u00020\u0005J\u0006\u0010(\u001a\u00020\u0005J\b\u0010)\u001a\u00020\u001aH&J\b\u0010*\u001a\u00020\u001aH&R\u001a\u0010\u0004\u001a\u00020\u0005X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0006\u0010\u0007\"\u0004\b\b\u0010\tR\u001a\u0010\n\u001a\u00020\u0005X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u000b\u0010\u0007\"\u0004\b\f\u0010\tR\u001a\u0010\r\u001a\u00020\u0005X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u000e\u0010\u0007\"\u0004\b\u000f\u0010\tR\u001a\u0010\u0010\u001a\u00020\u0005X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0011\u0010\u0007\"\u0004\b\u0012\u0010\tR\u001a\u0010\u0013\u001a\u00020\u0005X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0014\u0010\u0007\"\u0004\b\u0015\u0010\tR\u001a\u0010\u0016\u001a\u00020\u0005X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0017\u0010\u0007\"\u0004\b\u0018\u0010\t¨\u0006+"}, d2 = {"Lxc/engine/operate/edit/select/selectInfo/AbsSelectInfo;", "", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "()V", "mCenterX", "", "getMCenterX", "()F", "setMCenterX", "(F)V", "mCenterY", "getMCenterY", "setMCenterY", "mOffsetX", "getMOffsetX", "setMOffsetX", "mOffsetY", "getMOffsetY", "setMOffsetY", "mDegrees", "getMDegrees", "setMDegrees", "mScale", "getMScale", "setMScale", "doMove", "", "offsetX", "offsetY", "doScale", "centerX", "centerY", "scale", "doRotate", "degrees", "getCenterX", "getCenterY", "getOffsetX", "getOffsetY", "getDegrees", "getScale", "applyOffsetToPoint", "reset", "libXCEngine_release"}, k = 1, mv = {2, 2, 0}, xi = 48)
public abstract class AbsSelectInfo {
    private float mCenterX;
    private float mCenterY;
    private float mDegrees;
    private float mOffsetX;
    private float mOffsetY;
    private float mScale = 1.0f;

    public abstract void applyOffsetToPoint();

    public abstract void reset();

    public final float getMCenterX() {
        return this.mCenterX;
    }

    public final void setMCenterX(float f) {
        this.mCenterX = f;
    }

    public final float getMCenterY() {
        return this.mCenterY;
    }

    public final void setMCenterY(float f) {
        this.mCenterY = f;
    }

    public final float getMOffsetX() {
        return this.mOffsetX;
    }

    public final void setMOffsetX(float f) {
        this.mOffsetX = f;
    }

    public final float getMOffsetY() {
        return this.mOffsetY;
    }

    public final void setMOffsetY(float f) {
        this.mOffsetY = f;
    }

    public final float getMDegrees() {
        return this.mDegrees;
    }

    public final void setMDegrees(float f) {
        this.mDegrees = f;
    }

    public final float getMScale() {
        return this.mScale;
    }

    public final void setMScale(float f) {
        this.mScale = f;
    }

    public final void doMove(float offsetX, float offsetY) {
        this.mOffsetX = offsetX;
        this.mOffsetY = offsetY;
    }

    public final void doScale(float centerX, float centerY, float scale) {
        this.mCenterX = centerX;
        this.mCenterY = centerY;
        this.mScale = scale;
    }

    public final void doRotate(float centerX, float centerY, float degrees) {
        this.mCenterX = centerX;
        this.mCenterY = centerY;
        this.mDegrees = degrees;
    }

    /* JADX INFO: renamed from: getCenterX, reason: from getter */
    public final float getMCenterX() {
        return this.mCenterX;
    }

    /* JADX INFO: renamed from: getCenterY, reason: from getter */
    public final float getMCenterY() {
        return this.mCenterY;
    }

    public final float getOffsetX() {
        return this.mOffsetX;
    }

    public final float getOffsetY() {
        return this.mOffsetY;
    }

    /* JADX INFO: renamed from: getDegrees, reason: from getter */
    public final float getMDegrees() {
        return this.mDegrees;
    }

    public final float getScale() {
        return this.mScale;
    }
}
