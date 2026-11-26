package androidx.constraintlayout.core.motion;

import androidx.constraintlayout.core.motion.utils.TypedValues;
import androidx.constraintlayout.core.state.WidgetFrame;
import androidx.constraintlayout.core.widgets.ConstraintWidget;
import java.util.Set;

/* JADX INFO: loaded from: classes.dex */
public class MotionWidget implements TypedValues {
    public static final int FILL_PARENT = -1;
    public static final int GONE_UNSET = Integer.MIN_VALUE;
    private static final int INTERNAL_MATCH_CONSTRAINT = -3;
    private static final int INTERNAL_MATCH_PARENT = -1;
    private static final int INTERNAL_WRAP_CONTENT = -2;
    private static final int INTERNAL_WRAP_CONTENT_CONSTRAINED = -4;
    public static final int INVISIBLE = 0;
    public static final int MATCH_CONSTRAINT = 0;
    public static final int MATCH_CONSTRAINT_WRAP = 1;
    public static final int MATCH_PARENT = -1;
    public static final int PARENT_ID = 0;
    public static final int ROTATE_LEFT_OF_PORTRATE = 4;
    public static final int ROTATE_NONE = 0;
    public static final int ROTATE_PORTRATE_OF_LEFT = 2;
    public static final int ROTATE_PORTRATE_OF_RIGHT = 1;
    public static final int ROTATE_RIGHT_OF_PORTRATE = 3;
    public static final int UNSET = -1;
    public static final int VISIBILITY_MODE_IGNORE = 1;
    public static final int VISIBILITY_MODE_NORMAL = 0;
    public static final int VISIBLE = 4;
    public static final int WRAP_CONTENT = -2;
    Motion mMotion;
    private float mProgress;
    PropertySet mPropertySet;
    float mTransitionPathRotate;
    WidgetFrame mWidgetFrame;

    public static class Motion {
        private static final int INTERPOLATOR_REFERENCE_ID = -2;
        private static final int INTERPOLATOR_UNDEFINED = -3;
        private static final int SPLINE_STRING = -1;
        public String mAnimateRelativeTo = null;
        public int mAnimateCircleAngleTo = 0;
        public String mTransitionEasing = null;
        public int mPathMotionArc = -1;
        public int mDrawPath = 0;
        public float mMotionStagger = Float.NaN;
        public int mPolarRelativeTo = -1;
        public float mPathRotate = Float.NaN;
        public float mQuantizeMotionPhase = Float.NaN;
        public int mQuantizeMotionSteps = -1;
        public String mQuantizeInterpolatorString = null;
        public int mQuantizeInterpolatorType = -3;
        public int mQuantizeInterpolatorID = -1;
    }

    public static class PropertySet {
        public int visibility = 4;
        public int mVisibilityMode = 0;
        public float alpha = 1.0f;
        public float mProgress = Float.NaN;
    }

    public MotionWidget findViewById(int i) {
        return null;
    }

    public MotionWidget getParent() {
        return null;
    }

    @Override // androidx.constraintlayout.core.motion.utils.TypedValues
    public boolean setValue(int i, boolean z) {
        return false;
    }

    public MotionWidget() {
        this.mWidgetFrame = new WidgetFrame();
        this.mMotion = new Motion();
        this.mPropertySet = new PropertySet();
    }

    public void setVisibility(int i) {
        this.mPropertySet.visibility = i;
    }

    public String getName() {
        return this.mWidgetFrame.getId();
    }

    public void layout(int i, int i2, int i3, int i4) {
        setBounds(i, i2, i3, i4);
    }

    public String toString() {
        return this.mWidgetFrame.left + ", " + this.mWidgetFrame.top + ", " + this.mWidgetFrame.right + ", " + this.mWidgetFrame.bottom;
    }

    public void setBounds(int i, int i2, int i3, int i4) {
        if (this.mWidgetFrame == null) {
            this.mWidgetFrame = new WidgetFrame((ConstraintWidget) null);
        }
        this.mWidgetFrame.top = i2;
        this.mWidgetFrame.left = i;
        this.mWidgetFrame.right = i3;
        this.mWidgetFrame.bottom = i4;
    }

    public MotionWidget(WidgetFrame widgetFrame) {
        this.mWidgetFrame = new WidgetFrame();
        this.mMotion = new Motion();
        this.mPropertySet = new PropertySet();
        this.mWidgetFrame = widgetFrame;
    }

    public void updateMotion(TypedValues typedValues) {
        if (this.mWidgetFrame.getMotionProperties() != null) {
            this.mWidgetFrame.getMotionProperties().applyDelta(typedValues);
        }
    }

    @Override // androidx.constraintlayout.core.motion.utils.TypedValues
    public boolean setValue(int i, int i2) {
        if (setValueAttributes(i, i2)) {
            return true;
        }
        return setValueMotion(i, i2);
    }

    @Override // androidx.constraintlayout.core.motion.utils.TypedValues
    public boolean setValue(int i, float f) {
        if (setValueAttributes(i, f)) {
            return true;
        }
        return setValueMotion(i, f);
    }

    @Override // androidx.constraintlayout.core.motion.utils.TypedValues
    public boolean setValue(int i, String str) {
        if (i == 605) {
            this.mMotion.mAnimateRelativeTo = str;
            return true;
        }
        return setValueMotion(i, str);
    }

    public boolean setValueMotion(int i, int i2) {
        switch (i) {
            case 606:
                this.mMotion.mAnimateCircleAngleTo = i2;
                return true;
            case 607:
                this.mMotion.mPathMotionArc = i2;
                return true;
            case 608:
                this.mMotion.mDrawPath = i2;
                return true;
            case 609:
                this.mMotion.mPolarRelativeTo = i2;
                return true;
            case 610:
                this.mMotion.mQuantizeMotionSteps = i2;
                return true;
            case 611:
                this.mMotion.mQuantizeInterpolatorType = i2;
                return true;
            case 612:
                this.mMotion.mQuantizeInterpolatorID = i2;
                return true;
            default:
                return false;
        }
    }

    public boolean setValueMotion(int i, String str) {
        if (i == 603) {
            this.mMotion.mTransitionEasing = str;
            return true;
        }
        if (i != 604) {
            return false;
        }
        this.mMotion.mQuantizeInterpolatorString = str;
        return true;
    }

    public boolean setValueMotion(int i, float f) {
        switch (i) {
            case 600:
                this.mMotion.mMotionStagger = f;
                return true;
            case 601:
                this.mMotion.mPathRotate = f;
                return true;
            case 602:
                this.mMotion.mQuantizeMotionPhase = f;
                return true;
            default:
                return false;
        }
    }

    public boolean setValueAttributes(int i, float f) {
        switch (i) {
            case 303:
                this.mWidgetFrame.alpha = f;
                return true;
            case 304:
                this.mWidgetFrame.translationX = f;
                return true;
            case 305:
                this.mWidgetFrame.translationY = f;
                return true;
            case 306:
                this.mWidgetFrame.translationZ = f;
                return true;
            case 307:
            default:
                return false;
            case 308:
                this.mWidgetFrame.rotationX = f;
                return true;
            case 309:
                this.mWidgetFrame.rotationY = f;
                return true;
            case 310:
                this.mWidgetFrame.rotationZ = f;
                return true;
            case 311:
                this.mWidgetFrame.scaleX = f;
                return true;
            case 312:
                this.mWidgetFrame.scaleY = f;
                return true;
            case 313:
                this.mWidgetFrame.pivotX = f;
                return true;
            case 314:
                this.mWidgetFrame.pivotY = f;
                return true;
            case 315:
                this.mProgress = f;
                return true;
            case 316:
                this.mTransitionPathRotate = f;
                return true;
        }
    }

    public float getValueAttributes(int i) {
        switch (i) {
            case 303:
                return this.mWidgetFrame.alpha;
            case 304:
                return this.mWidgetFrame.translationX;
            case 305:
                return this.mWidgetFrame.translationY;
            case 306:
                return this.mWidgetFrame.translationZ;
            case 307:
            default:
                return Float.NaN;
            case 308:
                return this.mWidgetFrame.rotationX;
            case 309:
                return this.mWidgetFrame.rotationY;
            case 310:
                return this.mWidgetFrame.rotationZ;
            case 311:
                return this.mWidgetFrame.scaleX;
            case 312:
                return this.mWidgetFrame.scaleY;
            case 313:
                return this.mWidgetFrame.pivotX;
            case 314:
                return this.mWidgetFrame.pivotY;
            case 315:
                return this.mProgress;
            case 316:
                return this.mTransitionPathRotate;
        }
    }

    @Override // androidx.constraintlayout.core.motion.utils.TypedValues
    public int getId(String str) {
        int id = TypedValues.AttributesType.getId(str);
        return id != -1 ? id : TypedValues.MotionType.getId(str);
    }

    public int getTop() {
        return this.mWidgetFrame.top;
    }

    public int getLeft() {
        return this.mWidgetFrame.left;
    }

    public int getBottom() {
        return this.mWidgetFrame.bottom;
    }

    public int getRight() {
        return this.mWidgetFrame.right;
    }

    public void setPivotX(float f) {
        this.mWidgetFrame.pivotX = f;
    }

    public void setPivotY(float f) {
        this.mWidgetFrame.pivotY = f;
    }

    public float getRotationX() {
        return this.mWidgetFrame.rotationX;
    }

    public void setRotationX(float f) {
        this.mWidgetFrame.rotationX = f;
    }

    public float getRotationY() {
        return this.mWidgetFrame.rotationY;
    }

    public void setRotationY(float f) {
        this.mWidgetFrame.rotationY = f;
    }

    public float getRotationZ() {
        return this.mWidgetFrame.rotationZ;
    }

    public void setRotationZ(float f) {
        this.mWidgetFrame.rotationZ = f;
    }

    public float getTranslationX() {
        return this.mWidgetFrame.translationX;
    }

    public void setTranslationX(float f) {
        this.mWidgetFrame.translationX = f;
    }

    public float getTranslationY() {
        return this.mWidgetFrame.translationY;
    }

    public void setTranslationY(float f) {
        this.mWidgetFrame.translationY = f;
    }

    public void setTranslationZ(float f) {
        this.mWidgetFrame.translationZ = f;
    }

    public float getTranslationZ() {
        return this.mWidgetFrame.translationZ;
    }

    public float getScaleX() {
        return this.mWidgetFrame.scaleX;
    }

    public void setScaleX(float f) {
        this.mWidgetFrame.scaleX = f;
    }

    public float getScaleY() {
        return this.mWidgetFrame.scaleY;
    }

    public void setScaleY(float f) {
        this.mWidgetFrame.scaleY = f;
    }

    public int getVisibility() {
        return this.mPropertySet.visibility;
    }

    public float getPivotX() {
        return this.mWidgetFrame.pivotX;
    }

    public float getPivotY() {
        return this.mWidgetFrame.pivotY;
    }

    public float getAlpha() {
        return this.mWidgetFrame.alpha;
    }

    public int getX() {
        return this.mWidgetFrame.left;
    }

    public int getY() {
        return this.mWidgetFrame.top;
    }

    public int getWidth() {
        return this.mWidgetFrame.right - this.mWidgetFrame.left;
    }

    public int getHeight() {
        return this.mWidgetFrame.bottom - this.mWidgetFrame.top;
    }

    public WidgetFrame getWidgetFrame() {
        return this.mWidgetFrame;
    }

    public Set<String> getCustomAttributeNames() {
        return this.mWidgetFrame.getCustomAttributeNames();
    }

    public void setCustomAttribute(String str, int i, float f) {
        this.mWidgetFrame.setCustomAttribute(str, i, f);
    }

    public void setCustomAttribute(String str, int i, int i2) {
        this.mWidgetFrame.setCustomAttribute(str, i, i2);
    }

    public void setCustomAttribute(String str, int i, boolean z) {
        this.mWidgetFrame.setCustomAttribute(str, i, z);
    }

    public void setCustomAttribute(String str, int i, String str2) {
        this.mWidgetFrame.setCustomAttribute(str, i, str2);
    }

    public CustomVariable getCustomAttribute(String str) {
        return this.mWidgetFrame.getCustomAttribute(str);
    }

    public void setInterpolatedValue(CustomAttribute customAttribute, float[] fArr) {
        this.mWidgetFrame.setCustomAttribute(customAttribute.mName, 901, fArr[0]);
    }
}
