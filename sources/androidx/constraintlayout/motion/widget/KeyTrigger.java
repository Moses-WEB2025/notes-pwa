package androidx.constraintlayout.motion.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.Log;
import android.util.SparseIntArray;
import android.view.View;
import android.view.ViewGroup;
import androidx.constraintlayout.core.motion.utils.TypedValues;
import androidx.constraintlayout.motion.utils.ViewSpline;
import androidx.constraintlayout.widget.ConstraintAttribute;
import androidx.constraintlayout.widget.R;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Locale;
import org.apache.commons.lang3.StringUtils;

/* JADX INFO: loaded from: classes.dex */
public class KeyTrigger extends Key {
    public static final String CROSS = "CROSS";
    public static final int KEY_TYPE = 5;
    static final String NAME = "KeyTrigger";
    public static final String NEGATIVE_CROSS = "negativeCross";
    public static final String POSITIVE_CROSS = "positiveCross";
    public static final String POST_LAYOUT = "postLayout";
    private static final String TAG = "KeyTrigger";
    public static final String TRIGGER_COLLISION_ID = "triggerCollisionId";
    public static final String TRIGGER_COLLISION_VIEW = "triggerCollisionView";
    public static final String TRIGGER_ID = "triggerID";
    public static final String TRIGGER_RECEIVER = "triggerReceiver";
    public static final String TRIGGER_SLACK = "triggerSlack";
    public static final String VIEW_TRANSITION_ON_CROSS = "viewTransitionOnCross";
    public static final String VIEW_TRANSITION_ON_NEGATIVE_CROSS = "viewTransitionOnNegativeCross";
    public static final String VIEW_TRANSITION_ON_POSITIVE_CROSS = "viewTransitionOnPositiveCross";
    private float mFireLastPos;
    float mTriggerSlack = 0.1f;
    int mViewTransitionOnNegativeCross = UNSET;
    int mViewTransitionOnPositiveCross = UNSET;
    int mViewTransitionOnCross = UNSET;
    RectF mCollisionRect = new RectF();
    RectF mTargetRect = new RectF();
    HashMap<String, Method> mMethodHashMap = new HashMap<>();
    private int mCurveFit = -1;
    private String mCross = null;
    private int mTriggerReceiver = UNSET;
    private String mNegativeCross = null;
    private String mPositiveCross = null;
    private int mTriggerID = UNSET;
    private int mTriggerCollisionId = UNSET;
    private View mTriggerCollisionView = null;
    private boolean mFireCrossReset = true;
    private boolean mFireNegativeReset = true;
    private boolean mFirePositiveReset = true;
    private float mFireThreshold = Float.NaN;
    private boolean mPostLayout = false;

    @Override // androidx.constraintlayout.motion.widget.Key
    public void addValues(HashMap<String, ViewSpline> map) {
    }

    @Override // androidx.constraintlayout.motion.widget.Key
    public void getAttributeNames(HashSet<String> hashSet) {
    }

    public KeyTrigger() {
        this.mType = 5;
        this.mCustomConstraints = new HashMap<>();
    }

    @Override // androidx.constraintlayout.motion.widget.Key
    public void load(Context context, AttributeSet attributeSet) {
        Loader.read(this, context.obtainStyledAttributes(attributeSet, R.styleable.KeyTrigger), context);
    }

    int getCurveFit() {
        return this.mCurveFit;
    }

    @Override // androidx.constraintlayout.motion.widget.Key
    public void setValue(String str, Object obj) {
        str.hashCode();
        switch (str) {
            case "positiveCross":
                this.mPositiveCross = obj.toString();
                break;
            case "viewTransitionOnPositiveCross":
                this.mViewTransitionOnPositiveCross = toInt(obj);
                break;
            case "triggerCollisionId":
                this.mTriggerCollisionId = toInt(obj);
                break;
            case "triggerID":
                this.mTriggerID = toInt(obj);
                break;
            case "negativeCross":
                this.mNegativeCross = obj.toString();
                break;
            case "triggerCollisionView":
                this.mTriggerCollisionView = (View) obj;
                break;
            case "viewTransitionOnNegativeCross":
                this.mViewTransitionOnNegativeCross = toInt(obj);
                break;
            case "CROSS":
                this.mCross = obj.toString();
                break;
            case "triggerSlack":
                this.mTriggerSlack = toFloat(obj);
                break;
            case "viewTransitionOnCross":
                this.mViewTransitionOnCross = toInt(obj);
                break;
            case "postLayout":
                this.mPostLayout = toBoolean(obj);
                break;
            case "triggerReceiver":
                this.mTriggerReceiver = toInt(obj);
                break;
        }
    }

    private void setUpRect(RectF rectF, View view, boolean z) {
        rectF.top = view.getTop();
        rectF.bottom = view.getBottom();
        rectF.left = view.getLeft();
        rectF.right = view.getRight();
        if (z) {
            view.getMatrix().mapRect(rectF);
        }
    }

    /* JADX WARN: Code duplicated, block: B:38:0x008c  */
    /* JADX WARN: Code duplicated, block: B:43:0x00a0  */
    /* JADX WARN: Code duplicated, block: B:45:0x00ae  */
    /* JADX WARN: Code duplicated, block: B:49:0x00b5  */
    /* JADX WARN: Code duplicated, block: B:51:0x00c1  */
    /* JADX WARN: Code duplicated, block: B:54:0x00c9  */
    /* JADX WARN: Code duplicated, block: B:56:0x00ce  */
    /* JADX WARN: Code duplicated, block: B:58:0x00dc  */
    public void conditionallyFire(float f, View view) {
        boolean z;
        boolean z2;
        boolean z3;
        float f2;
        float f3;
        boolean z4;
        boolean z5;
        float f4;
        float f5;
        boolean z6;
        if (this.mTriggerCollisionId != UNSET) {
            if (this.mTriggerCollisionView == null) {
                this.mTriggerCollisionView = ((ViewGroup) view.getParent()).findViewById(this.mTriggerCollisionId);
            }
            setUpRect(this.mCollisionRect, this.mTriggerCollisionView, this.mPostLayout);
            setUpRect(this.mTargetRect, view, this.mPostLayout);
            if (this.mCollisionRect.intersect(this.mTargetRect)) {
                if (this.mFireCrossReset) {
                    this.mFireCrossReset = false;
                    z = true;
                } else {
                    z = false;
                }
                if (this.mFirePositiveReset) {
                    this.mFirePositiveReset = false;
                    z6 = true;
                } else {
                    z6 = false;
                }
                this.mFireNegativeReset = true;
                z5 = z6;
                z3 = false;
            } else {
                if (this.mFireCrossReset) {
                    z = false;
                } else {
                    this.mFireCrossReset = true;
                    z = true;
                }
                if (this.mFireNegativeReset) {
                    this.mFireNegativeReset = false;
                    z3 = true;
                } else {
                    z3 = false;
                }
                this.mFirePositiveReset = true;
                z5 = false;
            }
        } else {
            if (this.mFireCrossReset) {
                float f6 = this.mFireThreshold;
                if ((f - f6) * (this.mFireLastPos - f6) < 0.0f) {
                    this.mFireCrossReset = false;
                    z = true;
                }
                if (this.mFireNegativeReset) {
                    f4 = this.mFireThreshold;
                    f5 = f - f4;
                    if ((this.mFireLastPos - f4) * f5 >= 0.0f && f5 < 0.0f) {
                        this.mFireNegativeReset = false;
                        z2 = true;
                    }
                    if (this.mFirePositiveReset) {
                        f2 = this.mFireThreshold;
                        f3 = f - f2;
                        if ((this.mFireLastPos - f2) * f3 < 0.0f || f3 <= 0.0f) {
                            z4 = false;
                        } else {
                            this.mFirePositiveReset = false;
                            z4 = true;
                        }
                        boolean z7 = z2;
                        z5 = z4;
                        z3 = z7;
                    } else {
                        if (Math.abs(f - this.mFireThreshold) > this.mTriggerSlack) {
                            this.mFirePositiveReset = true;
                        }
                        z3 = z2;
                        z5 = false;
                    }
                } else if (Math.abs(f - this.mFireThreshold) > this.mTriggerSlack) {
                    this.mFireNegativeReset = true;
                }
                z2 = false;
                if (this.mFirePositiveReset) {
                    f2 = this.mFireThreshold;
                    f3 = f - f2;
                    if ((this.mFireLastPos - f2) * f3 < 0.0f) {
                        z4 = false;
                    } else {
                        z4 = false;
                    }
                    boolean z8 = z2;
                    z5 = z4;
                    z3 = z8;
                } else {
                    if (Math.abs(f - this.mFireThreshold) > this.mTriggerSlack) {
                        this.mFirePositiveReset = true;
                    }
                    z3 = z2;
                    z5 = false;
                }
            } else if (Math.abs(f - this.mFireThreshold) > this.mTriggerSlack) {
                this.mFireCrossReset = true;
            }
            z = false;
            if (this.mFireNegativeReset) {
                f4 = this.mFireThreshold;
                f5 = f - f4;
                if ((this.mFireLastPos - f4) * f5 >= 0.0f) {
                }
                if (this.mFirePositiveReset) {
                    f2 = this.mFireThreshold;
                    f3 = f - f2;
                    if ((this.mFireLastPos - f2) * f3 < 0.0f) {
                        z4 = false;
                    } else {
                        z4 = false;
                    }
                    boolean z9 = z2;
                    z5 = z4;
                    z3 = z9;
                } else {
                    if (Math.abs(f - this.mFireThreshold) > this.mTriggerSlack) {
                        this.mFirePositiveReset = true;
                    }
                    z3 = z2;
                    z5 = false;
                }
            } else if (Math.abs(f - this.mFireThreshold) > this.mTriggerSlack) {
                this.mFireNegativeReset = true;
            }
            z2 = false;
            if (this.mFirePositiveReset) {
                f2 = this.mFireThreshold;
                f3 = f - f2;
                if ((this.mFireLastPos - f2) * f3 < 0.0f) {
                    z4 = false;
                } else {
                    z4 = false;
                }
                boolean z10 = z2;
                z5 = z4;
                z3 = z10;
            } else {
                if (Math.abs(f - this.mFireThreshold) > this.mTriggerSlack) {
                    this.mFirePositiveReset = true;
                }
                z3 = z2;
                z5 = false;
            }
        }
        this.mFireLastPos = f;
        if (z3 || z || z5) {
            ((MotionLayout) view.getParent()).fireTrigger(this.mTriggerID, z5, f);
        }
        View viewFindViewById = this.mTriggerReceiver == UNSET ? view : ((MotionLayout) view.getParent()).findViewById(this.mTriggerReceiver);
        if (z3) {
            String str = this.mNegativeCross;
            if (str != null) {
                fire(str, viewFindViewById);
            }
            if (this.mViewTransitionOnNegativeCross != UNSET) {
                ((MotionLayout) view.getParent()).viewTransition(this.mViewTransitionOnNegativeCross, viewFindViewById);
            }
        }
        if (z5) {
            String str2 = this.mPositiveCross;
            if (str2 != null) {
                fire(str2, viewFindViewById);
            }
            if (this.mViewTransitionOnPositiveCross != UNSET) {
                ((MotionLayout) view.getParent()).viewTransition(this.mViewTransitionOnPositiveCross, viewFindViewById);
            }
        }
        if (z) {
            String str3 = this.mCross;
            if (str3 != null) {
                fire(str3, viewFindViewById);
            }
            if (this.mViewTransitionOnCross != UNSET) {
                ((MotionLayout) view.getParent()).viewTransition(this.mViewTransitionOnCross, viewFindViewById);
            }
        }
    }

    private void fire(String str, View view) {
        Method method;
        if (str == null) {
            return;
        }
        if (str.startsWith(".")) {
            fireCustom(str, view);
            return;
        }
        if (this.mMethodHashMap.containsKey(str)) {
            method = this.mMethodHashMap.get(str);
            if (method == null) {
                return;
            }
        } else {
            method = null;
        }
        if (method == null) {
            try {
                method = view.getClass().getMethod(str, new Class[0]);
                this.mMethodHashMap.put(str, method);
            } catch (NoSuchMethodException unused) {
                this.mMethodHashMap.put(str, null);
                Log.e(TypedValues.TriggerType.NAME, "Could not find method \"" + str + "\"on class " + view.getClass().getSimpleName() + StringUtils.SPACE + Debug.getName(view));
                return;
            }
        }
        try {
            method.invoke(view, new Object[0]);
        } catch (Exception unused2) {
            Log.e(TypedValues.TriggerType.NAME, "Exception in call \"" + this.mCross + "\"on class " + view.getClass().getSimpleName() + StringUtils.SPACE + Debug.getName(view));
        }
    }

    private void fireCustom(String str, View view) {
        boolean z = str.length() == 1;
        if (!z) {
            str = str.substring(1).toLowerCase(Locale.ROOT);
        }
        for (String str2 : this.mCustomConstraints.keySet()) {
            String lowerCase = str2.toLowerCase(Locale.ROOT);
            if (z || lowerCase.matches(str)) {
                ConstraintAttribute constraintAttribute = this.mCustomConstraints.get(str2);
                if (constraintAttribute != null) {
                    constraintAttribute.applyCustom(view);
                }
            }
        }
    }

    @Override // androidx.constraintlayout.motion.widget.Key
    public Key copy(Key key) {
        super.copy(key);
        KeyTrigger keyTrigger = (KeyTrigger) key;
        this.mCurveFit = keyTrigger.mCurveFit;
        this.mCross = keyTrigger.mCross;
        this.mTriggerReceiver = keyTrigger.mTriggerReceiver;
        this.mNegativeCross = keyTrigger.mNegativeCross;
        this.mPositiveCross = keyTrigger.mPositiveCross;
        this.mTriggerID = keyTrigger.mTriggerID;
        this.mTriggerCollisionId = keyTrigger.mTriggerCollisionId;
        this.mTriggerCollisionView = keyTrigger.mTriggerCollisionView;
        this.mTriggerSlack = keyTrigger.mTriggerSlack;
        this.mFireCrossReset = keyTrigger.mFireCrossReset;
        this.mFireNegativeReset = keyTrigger.mFireNegativeReset;
        this.mFirePositiveReset = keyTrigger.mFirePositiveReset;
        this.mFireThreshold = keyTrigger.mFireThreshold;
        this.mFireLastPos = keyTrigger.mFireLastPos;
        this.mPostLayout = keyTrigger.mPostLayout;
        this.mCollisionRect = keyTrigger.mCollisionRect;
        this.mTargetRect = keyTrigger.mTargetRect;
        this.mMethodHashMap = keyTrigger.mMethodHashMap;
        return this;
    }

    @Override // androidx.constraintlayout.motion.widget.Key
    /* JADX INFO: renamed from: clone */
    public Key mo49clone() {
        return new KeyTrigger().copy(this);
    }

    private static class Loader {
        private static final int COLLISION = 9;
        private static final int CROSS = 4;
        private static final int FRAME_POS = 8;
        private static final int NEGATIVE_CROSS = 1;
        private static final int POSITIVE_CROSS = 2;
        private static final int POST_LAYOUT = 10;
        private static final int TARGET_ID = 7;
        private static final int TRIGGER_ID = 6;
        private static final int TRIGGER_RECEIVER = 11;
        private static final int TRIGGER_SLACK = 5;
        private static final int VT_CROSS = 12;
        private static final int VT_NEGATIVE_CROSS = 13;
        private static final int VT_POSITIVE_CROSS = 14;
        private static SparseIntArray sAttrMap;

        private Loader() {
        }

        static {
            SparseIntArray sparseIntArray = new SparseIntArray();
            sAttrMap = sparseIntArray;
            sparseIntArray.append(R.styleable.KeyTrigger_framePosition, 8);
            sAttrMap.append(R.styleable.KeyTrigger_onCross, 4);
            sAttrMap.append(R.styleable.KeyTrigger_onNegativeCross, 1);
            sAttrMap.append(R.styleable.KeyTrigger_onPositiveCross, 2);
            sAttrMap.append(R.styleable.KeyTrigger_motionTarget, 7);
            sAttrMap.append(R.styleable.KeyTrigger_triggerId, 6);
            sAttrMap.append(R.styleable.KeyTrigger_triggerSlack, 5);
            sAttrMap.append(R.styleable.KeyTrigger_motion_triggerOnCollision, 9);
            sAttrMap.append(R.styleable.KeyTrigger_motion_postLayoutCollision, 10);
            sAttrMap.append(R.styleable.KeyTrigger_triggerReceiver, 11);
            sAttrMap.append(R.styleable.KeyTrigger_viewTransitionOnCross, 12);
            sAttrMap.append(R.styleable.KeyTrigger_viewTransitionOnNegativeCross, 13);
            sAttrMap.append(R.styleable.KeyTrigger_viewTransitionOnPositiveCross, 14);
        }

        public static void read(KeyTrigger keyTrigger, TypedArray typedArray, Context context) {
            int indexCount = typedArray.getIndexCount();
            for (int i = 0; i < indexCount; i++) {
                int index = typedArray.getIndex(i);
                switch (sAttrMap.get(index)) {
                    case 1:
                        keyTrigger.mNegativeCross = typedArray.getString(index);
                        break;
                    case 2:
                        keyTrigger.mPositiveCross = typedArray.getString(index);
                        break;
                    case 3:
                    default:
                        Log.e(TypedValues.TriggerType.NAME, "unused attribute 0x" + Integer.toHexString(index) + "   " + sAttrMap.get(index));
                        break;
                    case 4:
                        keyTrigger.mCross = typedArray.getString(index);
                        break;
                    case 5:
                        keyTrigger.mTriggerSlack = typedArray.getFloat(index, keyTrigger.mTriggerSlack);
                        break;
                    case 6:
                        keyTrigger.mTriggerID = typedArray.getResourceId(index, keyTrigger.mTriggerID);
                        break;
                    case 7:
                        if (MotionLayout.IS_IN_EDIT_MODE) {
                            keyTrigger.mTargetId = typedArray.getResourceId(index, keyTrigger.mTargetId);
                            if (keyTrigger.mTargetId == -1) {
                                keyTrigger.mTargetString = typedArray.getString(index);
                            }
                        } else if (typedArray.peekValue(index).type == 3) {
                            keyTrigger.mTargetString = typedArray.getString(index);
                        } else {
                            keyTrigger.mTargetId = typedArray.getResourceId(index, keyTrigger.mTargetId);
                        }
                        break;
                    case 8:
                        keyTrigger.mFramePosition = typedArray.getInteger(index, keyTrigger.mFramePosition);
                        keyTrigger.mFireThreshold = (keyTrigger.mFramePosition + 0.5f) / 100.0f;
                        break;
                    case 9:
                        keyTrigger.mTriggerCollisionId = typedArray.getResourceId(index, keyTrigger.mTriggerCollisionId);
                        break;
                    case 10:
                        keyTrigger.mPostLayout = typedArray.getBoolean(index, keyTrigger.mPostLayout);
                        break;
                    case 11:
                        keyTrigger.mTriggerReceiver = typedArray.getResourceId(index, keyTrigger.mTriggerReceiver);
                        break;
                    case 12:
                        keyTrigger.mViewTransitionOnCross = typedArray.getResourceId(index, keyTrigger.mViewTransitionOnCross);
                        break;
                    case 13:
                        keyTrigger.mViewTransitionOnNegativeCross = typedArray.getResourceId(index, keyTrigger.mViewTransitionOnNegativeCross);
                        break;
                    case 14:
                        keyTrigger.mViewTransitionOnPositiveCross = typedArray.getResourceId(index, keyTrigger.mViewTransitionOnPositiveCross);
                        break;
                }
            }
        }
    }
}
