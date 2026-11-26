package androidx.constraintlayout.core.dsl;

/* JADX INFO: loaded from: classes.dex */
public class OnSwipe {
    public static final int FLAG_DISABLE_POST_SCROLL = 1;
    public static final int FLAG_DISABLE_SCROLL = 2;
    private Mode mAutoCompleteMode;
    private Drag mDragDirection;
    private float mDragScale;
    private float mDragThreshold;
    private String mLimitBoundsTo;
    private float mMaxAcceleration;
    private float mMaxVelocity;
    private TouchUp mOnTouchUp;
    private String mRotationCenterId;
    private Boundary mSpringBoundary;
    private float mSpringDamping;
    private float mSpringMass;
    private float mSpringStiffness;
    private float mSpringStopThreshold;
    private String mTouchAnchorId;
    private Side mTouchAnchorSide;

    public enum Boundary {
        OVERSHOOT,
        BOUNCE_START,
        BOUNCE_END,
        BOUNCE_BOTH
    }

    public enum Drag {
        UP,
        DOWN,
        LEFT,
        RIGHT,
        START,
        END,
        CLOCKWISE,
        ANTICLOCKWISE
    }

    public enum Mode {
        VELOCITY,
        SPRING
    }

    public enum Side {
        TOP,
        LEFT,
        RIGHT,
        BOTTOM,
        MIDDLE,
        START,
        END
    }

    public enum TouchUp {
        AUTOCOMPLETE,
        TO_START,
        NEVER_COMPLETE_END,
        TO_END,
        STOP,
        DECELERATE,
        DECELERATE_COMPLETE,
        NEVER_COMPLETE_START
    }

    public OnSwipe() {
        this.mDragDirection = null;
        this.mTouchAnchorSide = null;
        this.mTouchAnchorId = null;
        this.mLimitBoundsTo = null;
        this.mOnTouchUp = null;
        this.mRotationCenterId = null;
        this.mMaxVelocity = Float.NaN;
        this.mMaxAcceleration = Float.NaN;
        this.mDragScale = Float.NaN;
        this.mDragThreshold = Float.NaN;
        this.mSpringDamping = Float.NaN;
        this.mSpringMass = Float.NaN;
        this.mSpringStiffness = Float.NaN;
        this.mSpringStopThreshold = Float.NaN;
        this.mSpringBoundary = null;
        this.mAutoCompleteMode = null;
    }

    public OnSwipe(String str, Side side, Drag drag) {
        this.mLimitBoundsTo = null;
        this.mOnTouchUp = null;
        this.mRotationCenterId = null;
        this.mMaxVelocity = Float.NaN;
        this.mMaxAcceleration = Float.NaN;
        this.mDragScale = Float.NaN;
        this.mDragThreshold = Float.NaN;
        this.mSpringDamping = Float.NaN;
        this.mSpringMass = Float.NaN;
        this.mSpringStiffness = Float.NaN;
        this.mSpringStopThreshold = Float.NaN;
        this.mSpringBoundary = null;
        this.mAutoCompleteMode = null;
        this.mTouchAnchorId = str;
        this.mTouchAnchorSide = side;
        this.mDragDirection = drag;
    }

    public OnSwipe setTouchAnchorId(String str) {
        this.mTouchAnchorId = str;
        return this;
    }

    public String getTouchAnchorId() {
        return this.mTouchAnchorId;
    }

    public OnSwipe setTouchAnchorSide(Side side) {
        this.mTouchAnchorSide = side;
        return this;
    }

    public Side getTouchAnchorSide() {
        return this.mTouchAnchorSide;
    }

    public OnSwipe setDragDirection(Drag drag) {
        this.mDragDirection = drag;
        return this;
    }

    public Drag getDragDirection() {
        return this.mDragDirection;
    }

    public OnSwipe setMaxVelocity(int i) {
        this.mMaxVelocity = i;
        return this;
    }

    public float getMaxVelocity() {
        return this.mMaxVelocity;
    }

    public OnSwipe setMaxAcceleration(int i) {
        this.mMaxAcceleration = i;
        return this;
    }

    public float getMaxAcceleration() {
        return this.mMaxAcceleration;
    }

    public OnSwipe setDragScale(int i) {
        this.mDragScale = i;
        return this;
    }

    public float getDragScale() {
        return this.mDragScale;
    }

    public OnSwipe setDragThreshold(int i) {
        this.mDragThreshold = i;
        return this;
    }

    public float getDragThreshold() {
        return this.mDragThreshold;
    }

    public OnSwipe setOnTouchUp(TouchUp touchUp) {
        this.mOnTouchUp = touchUp;
        return this;
    }

    public TouchUp getOnTouchUp() {
        return this.mOnTouchUp;
    }

    public OnSwipe setLimitBoundsTo(String str) {
        this.mLimitBoundsTo = str;
        return this;
    }

    public String getLimitBoundsTo() {
        return this.mLimitBoundsTo;
    }

    public OnSwipe setRotateCenter(String str) {
        this.mRotationCenterId = str;
        return this;
    }

    public String getRotationCenterId() {
        return this.mRotationCenterId;
    }

    public float getSpringDamping() {
        return this.mSpringDamping;
    }

    public OnSwipe setSpringDamping(float f) {
        this.mSpringDamping = f;
        return this;
    }

    public float getSpringMass() {
        return this.mSpringMass;
    }

    public OnSwipe setSpringMass(float f) {
        this.mSpringMass = f;
        return this;
    }

    public float getSpringStiffness() {
        return this.mSpringStiffness;
    }

    public OnSwipe setSpringStiffness(float f) {
        this.mSpringStiffness = f;
        return this;
    }

    public float getSpringStopThreshold() {
        return this.mSpringStopThreshold;
    }

    public OnSwipe setSpringStopThreshold(float f) {
        this.mSpringStopThreshold = f;
        return this;
    }

    public Boundary getSpringBoundary() {
        return this.mSpringBoundary;
    }

    public OnSwipe setSpringBoundary(Boundary boundary) {
        this.mSpringBoundary = boundary;
        return this;
    }

    public Mode getAutoCompleteMode() {
        return this.mAutoCompleteMode;
    }

    public void setAutoCompleteMode(Mode mode) {
        this.mAutoCompleteMode = mode;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder("OnSwipe:{\n");
        if (this.mTouchAnchorId != null) {
            sb.append("anchor:'").append(this.mTouchAnchorId).append("',\n");
        }
        if (this.mDragDirection != null) {
            sb.append("direction:'").append(this.mDragDirection.toString().toLowerCase()).append("',\n");
        }
        if (this.mTouchAnchorSide != null) {
            sb.append("side:'").append(this.mTouchAnchorSide.toString().toLowerCase()).append("',\n");
        }
        if (!Float.isNaN(this.mDragScale)) {
            sb.append("scale:'").append(this.mDragScale).append("',\n");
        }
        if (!Float.isNaN(this.mDragThreshold)) {
            sb.append("threshold:'").append(this.mDragThreshold).append("',\n");
        }
        if (!Float.isNaN(this.mMaxVelocity)) {
            sb.append("maxVelocity:'").append(this.mMaxVelocity).append("',\n");
        }
        if (!Float.isNaN(this.mMaxAcceleration)) {
            sb.append("maxAccel:'").append(this.mMaxAcceleration).append("',\n");
        }
        if (this.mLimitBoundsTo != null) {
            sb.append("limitBounds:'").append(this.mLimitBoundsTo).append("',\n");
        }
        if (this.mAutoCompleteMode != null) {
            sb.append("mode:'").append(this.mAutoCompleteMode.toString().toLowerCase()).append("',\n");
        }
        if (this.mOnTouchUp != null) {
            sb.append("touchUp:'").append(this.mOnTouchUp.toString().toLowerCase()).append("',\n");
        }
        if (!Float.isNaN(this.mSpringMass)) {
            sb.append("springMass:'").append(this.mSpringMass).append("',\n");
        }
        if (!Float.isNaN(this.mSpringStiffness)) {
            sb.append("springStiffness:'").append(this.mSpringStiffness).append("',\n");
        }
        if (!Float.isNaN(this.mSpringDamping)) {
            sb.append("springDamping:'").append(this.mSpringDamping).append("',\n");
        }
        if (!Float.isNaN(this.mSpringStopThreshold)) {
            sb.append("stopThreshold:'").append(this.mSpringStopThreshold).append("',\n");
        }
        if (this.mSpringBoundary != null) {
            sb.append("springBoundary:'").append(this.mSpringBoundary).append("',\n");
        }
        if (this.mRotationCenterId != null) {
            sb.append("around:'").append(this.mRotationCenterId).append("',\n");
        }
        sb.append("},\n");
        return sb.toString();
    }
}
