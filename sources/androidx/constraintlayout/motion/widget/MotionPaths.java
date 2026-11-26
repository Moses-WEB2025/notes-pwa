package androidx.constraintlayout.motion.widget;

import android.view.View;
import androidx.constraintlayout.core.motion.utils.Easing;
import androidx.constraintlayout.widget.ConstraintAttribute;
import androidx.constraintlayout.widget.ConstraintSet;
import com.miui.doodle.document.Layer;
import com.miui.maml.folme.AnimatedProperty;
import java.util.Arrays;
import java.util.LinkedHashMap;

/* JADX INFO: loaded from: classes.dex */
class MotionPaths implements Comparable<MotionPaths> {
    static final int CARTESIAN = 0;
    public static final boolean DEBUG = false;
    static final int OFF_HEIGHT = 4;
    static final int OFF_PATH_ROTATE = 5;
    static final int OFF_POSITION = 0;
    static final int OFF_WIDTH = 3;
    static final int OFF_X = 1;
    static final int OFF_Y = 2;
    public static final boolean OLD_WAY = false;
    static final int PERPENDICULAR = 1;
    static final int SCREEN = 2;
    public static final String TAG = "MotionPaths";
    static String[] sNames = {"position", AnimatedProperty.PROPERTY_NAME_X, AnimatedProperty.PROPERTY_NAME_Y, Layer.KEY_WIDTH, Layer.KEY_HEIGHT, "pathRotate"};
    int mAnimateCircleAngleTo;
    int mAnimateRelativeTo;
    LinkedHashMap<String, ConstraintAttribute> mAttributes;
    int mDrawPath;
    float mHeight;
    Easing mKeyFrameEasing;
    int mMode;
    int mPathMotionArc;
    float mPathRotate;
    float mPosition;
    float mProgress;
    float mRelativeAngle;
    MotionController mRelativeToController;
    double[] mTempDelta;
    double[] mTempValue;
    float mTime;
    float mWidth;
    float mX;
    float mY;

    private static float xRotate(float f, float f2, float f3, float f4, float f5, float f6) {
        return (((f5 - f3) * f2) - ((f6 - f4) * f)) + f3;
    }

    private static float yRotate(float f, float f2, float f3, float f4, float f5, float f6) {
        return ((f5 - f3) * f) + ((f6 - f4) * f2) + f4;
    }

    MotionPaths() {
        this.mDrawPath = 0;
        this.mPathRotate = Float.NaN;
        this.mProgress = Float.NaN;
        this.mPathMotionArc = Key.UNSET;
        this.mAnimateRelativeTo = Key.UNSET;
        this.mRelativeAngle = Float.NaN;
        this.mRelativeToController = null;
        this.mAttributes = new LinkedHashMap<>();
        this.mMode = 0;
        this.mTempValue = new double[18];
        this.mTempDelta = new double[18];
    }

    void initCartesian(KeyPosition keyPosition, MotionPaths motionPaths, MotionPaths motionPaths2) {
        float f = keyPosition.mFramePosition / 100.0f;
        this.mTime = f;
        this.mDrawPath = keyPosition.mDrawPath;
        float f2 = Float.isNaN(keyPosition.mPercentWidth) ? f : keyPosition.mPercentWidth;
        float f3 = Float.isNaN(keyPosition.mPercentHeight) ? f : keyPosition.mPercentHeight;
        float f4 = motionPaths2.mWidth;
        float f5 = motionPaths.mWidth;
        float f6 = motionPaths2.mHeight;
        float f7 = motionPaths.mHeight;
        this.mPosition = this.mTime;
        float f8 = motionPaths.mX;
        float f9 = motionPaths.mY;
        float f10 = f;
        float f11 = (motionPaths2.mX + (f4 / 2.0f)) - ((f5 / 2.0f) + f8);
        float f12 = (motionPaths2.mY + (f6 / 2.0f)) - (f9 + (f7 / 2.0f));
        float f13 = (f4 - f5) * f2;
        float f14 = f13 / 2.0f;
        this.mX = (int) ((f8 + (f11 * f10)) - f14);
        float f15 = (f6 - f7) * f3;
        float f16 = f15 / 2.0f;
        this.mY = (int) ((f9 + (f12 * f10)) - f16);
        this.mWidth = (int) (f5 + f13);
        this.mHeight = (int) (f7 + f15);
        float f17 = Float.isNaN(keyPosition.mPercentX) ? f10 : keyPosition.mPercentX;
        float f18 = Float.isNaN(keyPosition.mAltPercentY) ? 0.0f : keyPosition.mAltPercentY;
        if (!Float.isNaN(keyPosition.mPercentY)) {
            f10 = keyPosition.mPercentY;
        }
        float f19 = Float.isNaN(keyPosition.mAltPercentX) ? 0.0f : keyPosition.mAltPercentX;
        this.mMode = 0;
        this.mX = (int) (((motionPaths.mX + (f17 * f11)) + (f19 * f12)) - f14);
        this.mY = (int) (((motionPaths.mY + (f11 * f18)) + (f12 * f10)) - f16);
        this.mKeyFrameEasing = Easing.getInterpolator(keyPosition.mTransitionEasing);
        this.mPathMotionArc = keyPosition.mPathMotionArc;
    }

    void initAxis(KeyPosition keyPosition, MotionPaths motionPaths, MotionPaths motionPaths2) {
        float f = keyPosition.mFramePosition / 100.0f;
        this.mTime = f;
        this.mDrawPath = keyPosition.mDrawPath;
        float f2 = Float.isNaN(keyPosition.mPercentWidth) ? f : keyPosition.mPercentWidth;
        float f3 = Float.isNaN(keyPosition.mPercentHeight) ? f : keyPosition.mPercentHeight;
        float f4 = motionPaths2.mWidth;
        float f5 = motionPaths.mWidth;
        float f6 = f4 - f5;
        float f7 = motionPaths2.mHeight;
        float f8 = motionPaths.mHeight;
        float f9 = f7 - f8;
        this.mPosition = this.mTime;
        float f10 = motionPaths.mX;
        float f11 = (f5 / 2.0f) + f10;
        float f12 = motionPaths.mY;
        float f13 = f12 + (f8 / 2.0f);
        float f14 = f;
        float f15 = motionPaths2.mX + (f4 / 2.0f);
        float f16 = motionPaths2.mY + (f7 / 2.0f);
        if (f11 > f15) {
            f11 = f15;
            f15 = f11;
        }
        if (f13 <= f16) {
            f13 = f16;
            f16 = f13;
        }
        float f17 = f15 - f11;
        float f18 = f13 - f16;
        float f19 = f6 * f2;
        float f20 = f19 / 2.0f;
        this.mX = (int) ((f10 + (f17 * f14)) - f20);
        float f21 = f9 * f3;
        float f22 = f21 / 2.0f;
        this.mY = (int) ((f12 + (f18 * f14)) - f22);
        this.mWidth = (int) (f5 + f19);
        this.mHeight = (int) (f8 + f21);
        float f23 = Float.isNaN(keyPosition.mPercentX) ? f14 : keyPosition.mPercentX;
        float f24 = Float.isNaN(keyPosition.mAltPercentY) ? 0.0f : keyPosition.mAltPercentY;
        if (!Float.isNaN(keyPosition.mPercentY)) {
            f14 = keyPosition.mPercentY;
        }
        float f25 = Float.isNaN(keyPosition.mAltPercentX) ? 0.0f : keyPosition.mAltPercentX;
        this.mMode = 0;
        this.mX = (int) (((motionPaths.mX + (f23 * f17)) + (f25 * f18)) - f20);
        this.mY = (int) (((motionPaths.mY + (f17 * f24)) + (f18 * f14)) - f22);
        this.mKeyFrameEasing = Easing.getInterpolator(keyPosition.mTransitionEasing);
        this.mPathMotionArc = keyPosition.mPathMotionArc;
    }

    MotionPaths(int i, int i2, KeyPosition keyPosition, MotionPaths motionPaths, MotionPaths motionPaths2) {
        this.mDrawPath = 0;
        this.mPathRotate = Float.NaN;
        this.mProgress = Float.NaN;
        this.mPathMotionArc = Key.UNSET;
        this.mAnimateRelativeTo = Key.UNSET;
        this.mRelativeAngle = Float.NaN;
        this.mRelativeToController = null;
        this.mAttributes = new LinkedHashMap<>();
        this.mMode = 0;
        this.mTempValue = new double[18];
        this.mTempDelta = new double[18];
        if (motionPaths.mAnimateRelativeTo != Key.UNSET) {
            initPolar(i, i2, keyPosition, motionPaths, motionPaths2);
            return;
        }
        int i3 = keyPosition.mPositionType;
        if (i3 == 1) {
            initPath(keyPosition, motionPaths, motionPaths2);
            return;
        }
        if (i3 == 2) {
            initScreen(i, i2, keyPosition, motionPaths, motionPaths2);
        } else if (i3 == 3) {
            initAxis(keyPosition, motionPaths, motionPaths2);
        } else {
            initCartesian(keyPosition, motionPaths, motionPaths2);
        }
    }

    void initPolar(int i, int i2, KeyPosition keyPosition, MotionPaths motionPaths, MotionPaths motionPaths2) {
        float fMin;
        float f;
        float f2 = keyPosition.mFramePosition / 100.0f;
        this.mTime = f2;
        this.mDrawPath = keyPosition.mDrawPath;
        this.mMode = keyPosition.mPositionType;
        float f3 = Float.isNaN(keyPosition.mPercentWidth) ? f2 : keyPosition.mPercentWidth;
        float f4 = Float.isNaN(keyPosition.mPercentHeight) ? f2 : keyPosition.mPercentHeight;
        float f5 = motionPaths2.mWidth;
        float f6 = motionPaths.mWidth;
        float f7 = motionPaths2.mHeight;
        float f8 = motionPaths.mHeight;
        this.mPosition = this.mTime;
        this.mWidth = (int) (f6 + ((f5 - f6) * f3));
        this.mHeight = (int) (f8 + ((f7 - f8) * f4));
        if (keyPosition.mPositionType == 2) {
            if (Float.isNaN(keyPosition.mPercentX)) {
                float f9 = motionPaths2.mX;
                float f10 = motionPaths.mX;
                fMin = ((f9 - f10) * f2) + f10;
            } else {
                fMin = Math.min(f4, f3) * keyPosition.mPercentX;
            }
            this.mX = fMin;
            if (Float.isNaN(keyPosition.mPercentY)) {
                float f11 = motionPaths2.mY;
                float f12 = motionPaths.mY;
                f = (f2 * (f11 - f12)) + f12;
            } else {
                f = keyPosition.mPercentY;
            }
            this.mY = f;
        } else {
            float f13 = Float.isNaN(keyPosition.mPercentX) ? f2 : keyPosition.mPercentX;
            float f14 = motionPaths2.mX;
            float f15 = motionPaths.mX;
            this.mX = (f13 * (f14 - f15)) + f15;
            if (!Float.isNaN(keyPosition.mPercentY)) {
                f2 = keyPosition.mPercentY;
            }
            float f16 = motionPaths2.mY;
            float f17 = motionPaths.mY;
            this.mY = (f2 * (f16 - f17)) + f17;
        }
        this.mAnimateRelativeTo = motionPaths.mAnimateRelativeTo;
        this.mKeyFrameEasing = Easing.getInterpolator(keyPosition.mTransitionEasing);
        this.mPathMotionArc = keyPosition.mPathMotionArc;
    }

    public void setupRelative(MotionController motionController, MotionPaths motionPaths) {
        double d = ((this.mX + (this.mWidth / 2.0f)) - motionPaths.mX) - (motionPaths.mWidth / 2.0f);
        double d2 = ((this.mY + (this.mHeight / 2.0f)) - motionPaths.mY) - (motionPaths.mHeight / 2.0f);
        this.mRelativeToController = motionController;
        this.mX = (float) Math.hypot(d2, d);
        if (Float.isNaN(this.mRelativeAngle)) {
            this.mY = (float) (Math.atan2(d2, d) + 1.5707963267948966d);
        } else {
            this.mY = (float) Math.toRadians(this.mRelativeAngle);
        }
    }

    void initScreen(int i, int i2, KeyPosition keyPosition, MotionPaths motionPaths, MotionPaths motionPaths2) {
        float f = keyPosition.mFramePosition / 100.0f;
        this.mTime = f;
        this.mDrawPath = keyPosition.mDrawPath;
        float f2 = Float.isNaN(keyPosition.mPercentWidth) ? f : keyPosition.mPercentWidth;
        float f3 = Float.isNaN(keyPosition.mPercentHeight) ? f : keyPosition.mPercentHeight;
        float f4 = motionPaths2.mWidth;
        float f5 = motionPaths.mWidth;
        float f6 = motionPaths2.mHeight;
        float f7 = motionPaths.mHeight;
        this.mPosition = this.mTime;
        float f8 = motionPaths.mX;
        float f9 = motionPaths.mY;
        float f10 = motionPaths2.mX + (f4 / 2.0f);
        float f11 = motionPaths2.mY + (f6 / 2.0f);
        float f12 = (f4 - f5) * f2;
        this.mX = (int) ((f8 + ((f10 - ((f5 / 2.0f) + f8)) * f)) - (f12 / 2.0f));
        float f13 = (f6 - f7) * f3;
        this.mY = (int) ((f9 + ((f11 - (f9 + (f7 / 2.0f))) * f)) - (f13 / 2.0f));
        this.mWidth = (int) (f5 + f12);
        this.mHeight = (int) (f7 + f13);
        this.mMode = 2;
        if (!Float.isNaN(keyPosition.mPercentX)) {
            this.mX = (int) (keyPosition.mPercentX * (i - ((int) this.mWidth)));
        }
        if (!Float.isNaN(keyPosition.mPercentY)) {
            this.mY = (int) (keyPosition.mPercentY * (i2 - ((int) this.mHeight)));
        }
        this.mAnimateRelativeTo = this.mAnimateRelativeTo;
        this.mKeyFrameEasing = Easing.getInterpolator(keyPosition.mTransitionEasing);
        this.mPathMotionArc = keyPosition.mPathMotionArc;
    }

    void initPath(KeyPosition keyPosition, MotionPaths motionPaths, MotionPaths motionPaths2) {
        float f = keyPosition.mFramePosition / 100.0f;
        this.mTime = f;
        this.mDrawPath = keyPosition.mDrawPath;
        float f2 = Float.isNaN(keyPosition.mPercentWidth) ? f : keyPosition.mPercentWidth;
        float f3 = Float.isNaN(keyPosition.mPercentHeight) ? f : keyPosition.mPercentHeight;
        float f4 = motionPaths2.mWidth - motionPaths.mWidth;
        float f5 = motionPaths2.mHeight - motionPaths.mHeight;
        this.mPosition = this.mTime;
        if (!Float.isNaN(keyPosition.mPercentX)) {
            f = keyPosition.mPercentX;
        }
        float f6 = motionPaths.mX;
        float f7 = motionPaths.mWidth;
        float f8 = motionPaths.mY;
        float f9 = motionPaths.mHeight;
        float f10 = f;
        float f11 = (motionPaths2.mX + (motionPaths2.mWidth / 2.0f)) - ((f7 / 2.0f) + f6);
        float f12 = (motionPaths2.mY + (motionPaths2.mHeight / 2.0f)) - ((f9 / 2.0f) + f8);
        float f13 = f11 * f10;
        float f14 = f4 * f2;
        float f15 = f14 / 2.0f;
        this.mX = (int) ((f6 + f13) - f15);
        float f16 = f12 * f10;
        float f17 = f5 * f3;
        float f18 = f17 / 2.0f;
        this.mY = (int) ((f8 + f16) - f18);
        this.mWidth = (int) (f7 + f14);
        this.mHeight = (int) (f9 + f17);
        float f19 = Float.isNaN(keyPosition.mPercentY) ? 0.0f : keyPosition.mPercentY;
        this.mMode = 1;
        float f20 = (int) ((motionPaths.mX + f13) - f15);
        this.mX = f20;
        float f21 = (int) ((motionPaths.mY + f16) - f18);
        this.mX = f20 + ((-f12) * f19);
        this.mY = f21 + (f11 * f19);
        this.mAnimateRelativeTo = this.mAnimateRelativeTo;
        this.mKeyFrameEasing = Easing.getInterpolator(keyPosition.mTransitionEasing);
        this.mPathMotionArc = keyPosition.mPathMotionArc;
    }

    private boolean diff(float f, float f2) {
        if (Float.isNaN(f) || Float.isNaN(f2)) {
            return Float.isNaN(f) != Float.isNaN(f2);
        }
        return Math.abs(f - f2) > 1.0E-6f;
    }

    void different(MotionPaths motionPaths, boolean[] zArr, String[] strArr, boolean z) {
        boolean zDiff = diff(this.mX, motionPaths.mX);
        boolean zDiff2 = diff(this.mY, motionPaths.mY);
        zArr[0] = zArr[0] | diff(this.mPosition, motionPaths.mPosition);
        boolean z2 = zDiff | zDiff2 | z;
        zArr[1] = zArr[1] | z2;
        zArr[2] = z2 | zArr[2];
        zArr[3] = zArr[3] | diff(this.mWidth, motionPaths.mWidth);
        zArr[4] = diff(this.mHeight, motionPaths.mHeight) | zArr[4];
    }

    void getCenter(double d, int[] iArr, double[] dArr, float[] fArr, int i) {
        float f = this.mX;
        float fCos = this.mY;
        float f2 = this.mWidth;
        float f3 = this.mHeight;
        for (int i2 = 0; i2 < iArr.length; i2++) {
            float f4 = (float) dArr[i2];
            int i3 = iArr[i2];
            if (i3 == 1) {
                f = f4;
            } else if (i3 == 2) {
                fCos = f4;
            } else if (i3 == 3) {
                f2 = f4;
            } else if (i3 == 4) {
                f3 = f4;
            }
        }
        MotionController motionController = this.mRelativeToController;
        if (motionController != null) {
            float[] fArr2 = new float[2];
            motionController.getCenter(d, fArr2, new float[2]);
            float f5 = fArr2[0];
            float f6 = fArr2[1];
            double d2 = f;
            double d3 = fCos;
            float fSin = (float) ((((double) f5) + (Math.sin(d3) * d2)) - ((double) (f2 / 2.0f)));
            fCos = (float) ((((double) f6) - (d2 * Math.cos(d3))) - ((double) (f3 / 2.0f)));
            f = fSin;
        }
        fArr[i] = f + (f2 / 2.0f) + 0.0f;
        fArr[i + 1] = fCos + (f3 / 2.0f) + 0.0f;
    }

    void getCenter(double d, int[] iArr, double[] dArr, float[] fArr, double[] dArr2, float[] fArr2) {
        float f;
        float fSin = this.mX;
        float fCos = this.mY;
        float f2 = this.mWidth;
        float f3 = this.mHeight;
        float f4 = 0.0f;
        float f5 = 0.0f;
        float f6 = 0.0f;
        float f7 = 0.0f;
        for (int i = 0; i < iArr.length; i++) {
            float f8 = (float) dArr[i];
            float f9 = (float) dArr2[i];
            int i2 = iArr[i];
            if (i2 == 1) {
                fSin = f8;
                f4 = f9;
            } else if (i2 == 2) {
                fCos = f8;
                f6 = f9;
            } else if (i2 == 3) {
                f2 = f8;
                f5 = f9;
            } else if (i2 == 4) {
                f3 = f8;
                f7 = f9;
            }
        }
        float f10 = (f5 / 2.0f) + f4;
        float fCos2 = (f7 / 2.0f) + f6;
        MotionController motionController = this.mRelativeToController;
        if (motionController != null) {
            float[] fArr3 = new float[2];
            float[] fArr4 = new float[2];
            motionController.getCenter(d, fArr3, fArr4);
            float f11 = fArr3[0];
            float f12 = fArr3[1];
            float f13 = fArr4[0];
            float f14 = fArr4[1];
            double d2 = f11;
            f = 2.0f;
            double d3 = fSin;
            double d4 = fCos;
            fSin = (float) ((d2 + (Math.sin(d4) * d3)) - ((double) (f2 / 2.0f)));
            fCos = (float) ((((double) f12) - (Math.cos(d4) * d3)) - ((double) (f3 / 2.0f)));
            double d5 = f13;
            double d6 = f4;
            double d7 = f6;
            float fSin2 = (float) (d5 + (Math.sin(d4) * d6) + (Math.cos(d4) * d7));
            fCos2 = (float) ((((double) f14) - (d6 * Math.cos(d4))) + (Math.sin(d4) * d7));
            f10 = fSin2;
        } else {
            f = 2.0f;
        }
        fArr[0] = fSin + (f2 / f) + 0.0f;
        fArr[1] = fCos + (f3 / f) + 0.0f;
        fArr2[0] = f10;
        fArr2[1] = fCos2;
    }

    void getCenterVelocity(double d, int[] iArr, double[] dArr, float[] fArr, int i) {
        float f = this.mX;
        float fCos = this.mY;
        float f2 = this.mWidth;
        float f3 = this.mHeight;
        for (int i2 = 0; i2 < iArr.length; i2++) {
            float f4 = (float) dArr[i2];
            int i3 = iArr[i2];
            if (i3 == 1) {
                f = f4;
            } else if (i3 == 2) {
                fCos = f4;
            } else if (i3 == 3) {
                f2 = f4;
            } else if (i3 == 4) {
                f3 = f4;
            }
        }
        MotionController motionController = this.mRelativeToController;
        if (motionController != null) {
            float[] fArr2 = new float[2];
            motionController.getCenter(d, fArr2, new float[2]);
            float f5 = fArr2[0];
            float f6 = fArr2[1];
            double d2 = f;
            double d3 = fCos;
            float fSin = (float) ((((double) f5) + (Math.sin(d3) * d2)) - ((double) (f2 / 2.0f)));
            fCos = (float) ((((double) f6) - (d2 * Math.cos(d3))) - ((double) (f3 / 2.0f)));
            f = fSin;
        }
        fArr[i] = f + (f2 / 2.0f) + 0.0f;
        fArr[i + 1] = fCos + (f3 / 2.0f) + 0.0f;
    }

    void getBounds(int[] iArr, double[] dArr, float[] fArr, int i) {
        float f = this.mWidth;
        float f2 = this.mHeight;
        for (int i2 = 0; i2 < iArr.length; i2++) {
            float f3 = (float) dArr[i2];
            int i3 = iArr[i2];
            if (i3 == 3) {
                f = f3;
            } else if (i3 == 4) {
                f2 = f3;
            }
        }
        fArr[i] = f;
        fArr[i + 1] = f2;
    }

    /* JADX WARN: Multi-variable type inference failed */
    void setView(float f, View view, int[] iArr, double[] dArr, double[] dArr2, double[] dArr3, boolean z) {
        float f2;
        float f3 = this.mX;
        float f4 = this.mY;
        float f5 = this.mWidth;
        float f6 = this.mHeight;
        if (iArr.length != 0 && this.mTempValue.length <= iArr[iArr.length - 1]) {
            int i = iArr[iArr.length - 1] + 1;
            this.mTempValue = new double[i];
            this.mTempDelta = new double[i];
        }
        Arrays.fill(this.mTempValue, Double.NaN);
        for (int i2 = 0; i2 < iArr.length; i2++) {
            double[] dArr4 = this.mTempValue;
            int i3 = iArr[i2];
            dArr4[i3] = dArr[i2];
            this.mTempDelta[i3] = dArr2[i2];
        }
        float f7 = Float.NaN;
        int i4 = 0;
        float f8 = 0.0f;
        float f9 = 0.0f;
        float f10 = 0.0f;
        float f11 = 0.0f;
        while (true) {
            double[] dArr5 = this.mTempValue;
            if (i4 >= dArr5.length) {
                break;
            }
            if (Double.isNaN(dArr5[i4]) && (dArr3 == null || dArr3[i4] == 0.0d)) {
                f2 = f7;
            } else {
                double d = dArr3 != null ? dArr3[i4] : 0.0d;
                if (!Double.isNaN(this.mTempValue[i4])) {
                    d = this.mTempValue[i4] + d;
                }
                f2 = f7;
                float f12 = (float) d;
                float f13 = (float) this.mTempDelta[i4];
                if (i4 == 1) {
                    f7 = f2;
                    f3 = f12;
                    f8 = f13;
                } else if (i4 == 2) {
                    f7 = f2;
                    f4 = f12;
                    f9 = f13;
                } else if (i4 == 3) {
                    f7 = f2;
                    f5 = f12;
                    f10 = f13;
                } else if (i4 == 4) {
                    f7 = f2;
                    f6 = f12;
                    f11 = f13;
                } else if (i4 == 5) {
                    f7 = f12;
                }
                i4++;
            }
            f7 = f2;
            i4++;
        }
        float f14 = f7;
        MotionController motionController = this.mRelativeToController;
        if (motionController != null) {
            float[] fArr = new float[2];
            float[] fArr2 = new float[2];
            motionController.getCenter(f, fArr, fArr2);
            float f15 = fArr[0];
            float f16 = fArr[1];
            float f17 = fArr2[0];
            float f18 = fArr2[1];
            double d2 = f3;
            double d3 = f4;
            float fSin = (float) ((((double) f15) + (Math.sin(d3) * d2)) - ((double) (f5 / 2.0f)));
            float fCos = (float) ((((double) f16) - (Math.cos(d3) * d2)) - ((double) (f6 / 2.0f)));
            double d4 = f17;
            double d5 = f8;
            double dSin = d4 + (Math.sin(d3) * d5);
            double dCos = Math.cos(d3) * d2;
            double d6 = f9;
            float f19 = (float) (dSin + (dCos * d6));
            float fCos2 = (float) ((((double) f18) - (d5 * Math.cos(d3))) + (Math.sin(d3) * d2 * d6));
            if (dArr2.length >= 2) {
                dArr2[0] = f19;
                dArr2[1] = fCos2;
            }
            if (!Float.isNaN(f14)) {
                view.setRotation((float) (((double) f14) + Math.toDegrees(Math.atan2(fCos2, f19))));
            }
            f3 = fSin;
            f4 = fCos;
        } else if (!Float.isNaN(f14)) {
            view.setRotation(f14 + ((float) Math.toDegrees(Math.atan2(f9 + (f11 / 2.0f), f8 + (f10 / 2.0f)))) + 0.0f);
        }
        if (view instanceof FloatLayout) {
            ((FloatLayout) view).layout(f3, f4, f5 + f3, f6 + f4);
            return;
        }
        float f20 = f3 + 0.5f;
        int i5 = (int) f20;
        float f21 = f4 + 0.5f;
        int i6 = (int) f21;
        int i7 = (int) (f20 + f5);
        int i8 = (int) (f21 + f6);
        int i9 = i7 - i5;
        int i10 = i8 - i6;
        if (i9 != view.getMeasuredWidth() || i10 != view.getMeasuredHeight() || z) {
            view.measure(View.MeasureSpec.makeMeasureSpec(i9, 1073741824), View.MeasureSpec.makeMeasureSpec(i10, 1073741824));
        }
        view.layout(i5, i6, i7, i8);
    }

    void getRect(int[] iArr, double[] dArr, float[] fArr, int i) {
        float f = this.mX;
        float fCos = this.mY;
        float f2 = this.mWidth;
        float f3 = this.mHeight;
        for (int i2 = 0; i2 < iArr.length; i2++) {
            float f4 = (float) dArr[i2];
            int i3 = iArr[i2];
            if (i3 == 1) {
                f = f4;
            } else if (i3 == 2) {
                fCos = f4;
            } else if (i3 == 3) {
                f2 = f4;
            } else if (i3 == 4) {
                f3 = f4;
            }
        }
        MotionController motionController = this.mRelativeToController;
        if (motionController != null) {
            float centerX = motionController.getCenterX();
            float centerY = this.mRelativeToController.getCenterY();
            double d = f;
            double d2 = fCos;
            float fSin = (float) ((((double) centerX) + (Math.sin(d2) * d)) - ((double) (f2 / 2.0f)));
            fCos = (float) ((((double) centerY) - (d * Math.cos(d2))) - ((double) (f3 / 2.0f)));
            f = fSin;
        }
        float f5 = f2 + f;
        float f6 = f3 + fCos;
        Float.isNaN(Float.NaN);
        Float.isNaN(Float.NaN);
        fArr[i] = f + 0.0f;
        fArr[i + 1] = fCos + 0.0f;
        fArr[i + 2] = f5 + 0.0f;
        fArr[i + 3] = fCos + 0.0f;
        fArr[i + 4] = f5 + 0.0f;
        fArr[i + 5] = f6 + 0.0f;
        fArr[i + 6] = f + 0.0f;
        fArr[i + 7] = f6 + 0.0f;
    }

    void setDpDt(float f, float f2, float[] fArr, int[] iArr, double[] dArr, double[] dArr2) {
        float f3 = 0.0f;
        float f4 = 0.0f;
        float f5 = 0.0f;
        float f6 = 0.0f;
        for (int i = 0; i < iArr.length; i++) {
            float f7 = (float) dArr[i];
            double d = dArr2[i];
            int i2 = iArr[i];
            if (i2 == 1) {
                f3 = f7;
            } else if (i2 == 2) {
                f5 = f7;
            } else if (i2 == 3) {
                f4 = f7;
            } else if (i2 == 4) {
                f6 = f7;
            }
        }
        float f8 = f3 - ((0.0f * f4) / 2.0f);
        float f9 = f5 - ((0.0f * f6) / 2.0f);
        fArr[0] = (f8 * (1.0f - f)) + (((f4 * 1.0f) + f8) * f) + 0.0f;
        fArr[1] = (f9 * (1.0f - f2)) + (((f6 * 1.0f) + f9) * f2) + 0.0f;
    }

    void fillStandard(double[] dArr, int[] iArr) {
        float[] fArr = {this.mPosition, this.mX, this.mY, this.mWidth, this.mHeight, this.mPathRotate};
        int i = 0;
        for (int i2 : iArr) {
            if (i2 < 6) {
                dArr[i] = fArr[i2];
                i++;
            }
        }
    }

    boolean hasCustomData(String str) {
        return this.mAttributes.containsKey(str);
    }

    int getCustomDataCount(String str) {
        ConstraintAttribute constraintAttribute = this.mAttributes.get(str);
        if (constraintAttribute == null) {
            return 0;
        }
        return constraintAttribute.numberOfInterpolatedValues();
    }

    int getCustomData(String str, double[] dArr, int i) {
        ConstraintAttribute constraintAttribute = this.mAttributes.get(str);
        int i2 = 0;
        if (constraintAttribute == null) {
            return 0;
        }
        if (constraintAttribute.numberOfInterpolatedValues() == 1) {
            dArr[i] = constraintAttribute.getValueToInterpolate();
            return 1;
        }
        int iNumberOfInterpolatedValues = constraintAttribute.numberOfInterpolatedValues();
        float[] fArr = new float[iNumberOfInterpolatedValues];
        constraintAttribute.getValuesToInterpolate(fArr);
        while (i2 < iNumberOfInterpolatedValues) {
            dArr[i] = fArr[i2];
            i2++;
            i++;
        }
        return iNumberOfInterpolatedValues;
    }

    void setBounds(float f, float f2, float f3, float f4) {
        this.mX = f;
        this.mY = f2;
        this.mWidth = f3;
        this.mHeight = f4;
    }

    @Override // java.lang.Comparable
    public int compareTo(MotionPaths motionPaths) {
        return Float.compare(this.mPosition, motionPaths.mPosition);
    }

    public void applyParameters(ConstraintSet.Constraint constraint) {
        this.mKeyFrameEasing = Easing.getInterpolator(constraint.motion.mTransitionEasing);
        this.mPathMotionArc = constraint.motion.mPathMotionArc;
        this.mAnimateRelativeTo = constraint.motion.mAnimateRelativeTo;
        this.mPathRotate = constraint.motion.mPathRotate;
        this.mDrawPath = constraint.motion.mDrawPath;
        this.mAnimateCircleAngleTo = constraint.motion.mAnimateCircleAngleTo;
        this.mProgress = constraint.propertySet.mProgress;
        this.mRelativeAngle = constraint.layout.circleAngle;
        for (String str : constraint.mCustomConstraints.keySet()) {
            ConstraintAttribute constraintAttribute = constraint.mCustomConstraints.get(str);
            if (constraintAttribute != null && constraintAttribute.isContinuous()) {
                this.mAttributes.put(str, constraintAttribute);
            }
        }
    }

    public void configureRelativeTo(MotionController motionController) {
        motionController.getPos(this.mProgress);
    }
}
