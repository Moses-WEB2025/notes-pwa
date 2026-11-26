package com.miui.maml.elements;

import android.content.Intent;
import android.graphics.Canvas;
import android.text.TextUtils;
import android.util.Log;
import android.view.MotionEvent;
import com.miui.common.stat.ToDoStat;
import com.miui.maml.ActionCommand;
import com.miui.maml.CommandTrigger;
import com.miui.maml.ScreenElementRoot;
import com.miui.maml.animation.interpolater.InterpolatorHelper;
import com.miui.maml.data.Expression;
import com.miui.maml.data.IndexedVariable;
import com.miui.maml.data.Variables;
import com.miui.maml.folme.AnimatedProperty;
import com.miui.maml.util.IntentInfo;
import com.miui.maml.util.Task;
import com.miui.maml.util.Utils;
import java.util.ArrayList;
import java.util.Iterator;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

/* JADX INFO: loaded from: classes3.dex */
public class AdvancedSlider extends ElementGroup {
    private static final boolean DEBUG = false;
    private static final int DEFAULT_DRAG_TOLERANCE = 150;
    private static final float FREE_ENDPOINT_DIST = 1.7014117E38f;
    private static final String LOG_TAG = "LockScreen_AdvancedSlider";
    public static final String MOVE_DIST = "move_dist";
    public static final String MOVE_X = "move_x";
    public static final String MOVE_Y = "move_y";
    private static final float NONE_ENDPOINT_DIST = Float.MAX_VALUE;
    public static final int SLIDER_STATE_NORMAL = 0;
    public static final int SLIDER_STATE_PRESSED = 1;
    public static final int SLIDER_STATE_REACHED = 2;
    public static final String STATE = "state";
    public static final String TAG_NAME = "Slider";
    private EndPoint mCurrentEndPoint;
    private ArrayList<EndPoint> mEndPoints;
    protected boolean mIsHaptic;
    private boolean mIsKeepStatusAfterLaunch;
    private IndexedVariable mMoveDistVar;
    private IndexedVariable mMoveXVar;
    private IndexedVariable mMoveYVar;
    private boolean mMoving;
    private OnLaunchListener mOnLaunchListener;
    private ReboundAnimationController mReboundAnimationController;
    private StartPoint mStartPoint;
    private boolean mStartPointPressed;
    private IndexedVariable mStateVar;
    private float mTouchOffsetX;
    private float mTouchOffsetY;

    public interface OnLaunchListener {
        boolean onLaunch(String str);
    }

    private enum State {
        Normal,
        Pressed,
        Reached,
        Invalid
    }

    protected void onCancel() {
    }

    protected void onMove(float f, float f2) {
    }

    private abstract class ReboundAnimationController implements ITicker {
        private int mBounceStartPointIndex;
        private EndPoint mEndPoint;
        private long mPreDistance;
        protected long mStartTime;
        private float mStartX;
        private float mStartY;
        protected double mTotalDistance;

        protected abstract long getDistance(long j);

        protected void onStart() {
        }

        private ReboundAnimationController() {
            this.mStartTime = -1L;
        }

        /* synthetic */ ReboundAnimationController(AdvancedSlider advancedSlider, AnonymousClass1 anonymousClass1) {
            this();
        }

        public void init() {
            this.mStartTime = -1L;
        }

        public void start(EndPoint endPoint) {
            this.mStartTime = 0L;
            this.mEndPoint = endPoint;
            this.mStartX = AdvancedSlider.this.mStartPoint.getOffsetX() + AdvancedSlider.this.mStartPoint.getAnchorX();
            this.mStartY = AdvancedSlider.this.mStartPoint.getOffsetY() + AdvancedSlider.this.mStartPoint.getAnchorY();
            this.mBounceStartPointIndex = -1;
            this.mTotalDistance = 0.0d;
            Utils.Point point = new Utils.Point(this.mStartX, this.mStartY);
            if (endPoint == null || endPoint.mPath == null) {
                this.mTotalDistance = Utils.Dist(new Utils.Point(AdvancedSlider.this.mStartPoint.getAnchorX(), AdvancedSlider.this.mStartPoint.getAnchorY()), point, true);
            } else {
                for (int i = 1; i < endPoint.mPath.size(); i++) {
                    int i2 = i - 1;
                    Position position = (Position) endPoint.mPath.get(i2);
                    Position position2 = (Position) endPoint.mPath.get(i);
                    Utils.Point point2 = new Utils.Point(position.getX(), position.getY());
                    Utils.Point point3 = new Utils.Point(position2.getX(), position2.getY());
                    Utils.Point pointPointProjectionOnSegment = Utils.pointProjectionOnSegment(point2, point3, point, false);
                    if (pointPointProjectionOnSegment != null) {
                        this.mBounceStartPointIndex = i2;
                        this.mTotalDistance += Utils.Dist(point2, pointPointProjectionOnSegment, true);
                        break;
                    }
                    this.mTotalDistance += Utils.Dist(point2, point3, true);
                }
            }
            if (this.mTotalDistance < 3.0d) {
                onStop();
            } else {
                onStart();
                AdvancedSlider.this.requestUpdate();
            }
        }

        protected void onStop() {
            this.mStartTime = -1L;
            AdvancedSlider.this.cancelMoving();
        }

        @Override // com.miui.maml.elements.ITicker
        public void tick(long j) {
            long j2;
            long j3 = this.mStartTime;
            if (j3 < 0) {
                return;
            }
            if (j3 == 0) {
                this.mStartTime = j;
                this.mPreDistance = 0L;
            } else {
                long distance = getDistance(j - j3);
                if (this.mStartTime < 0) {
                    return;
                }
                EndPoint endPoint = this.mEndPoint;
                if (endPoint == null || endPoint.mPath == null) {
                    j2 = distance;
                    Utils.Point point = getPoint(AdvancedSlider.this.mStartPoint.getAnchorX(), AdvancedSlider.this.mStartPoint.getAnchorY(), this.mStartX, this.mStartY, j2);
                    if (point == null) {
                        onStop();
                    } else {
                        onMove((float) point.x, (float) point.y);
                    }
                } else {
                    float offsetX = AdvancedSlider.this.mStartPoint.getOffsetX() + AdvancedSlider.this.mStartPoint.getAnchorX();
                    float offsetY = AdvancedSlider.this.mStartPoint.getOffsetY() + AdvancedSlider.this.mStartPoint.getAnchorY();
                    long j4 = distance - this.mPreDistance;
                    long jDist = j4;
                    float x = offsetX;
                    float y = offsetY;
                    for (int i = this.mBounceStartPointIndex; i >= 0; i--) {
                        Position position = (Position) this.mEndPoint.mPath.get(i);
                        Utils.Point point2 = getPoint(position.getX(), position.getY(), x, y, jDist);
                        if (point2 == null) {
                            if (i == 0) {
                                onStop();
                                break;
                            }
                            jDist = (long) (jDist - Utils.Dist(new Utils.Point(position.getX(), position.getY()), new Utils.Point(x, y), true));
                            x = position.getX();
                            y = position.getY();
                        } else {
                            this.mBounceStartPointIndex = i;
                            onMove((float) point2.x, (float) point2.y);
                            break;
                        }
                    }
                    j2 = distance;
                }
                this.mPreDistance = j2;
            }
            AdvancedSlider.this.requestUpdate();
        }

        protected void onMove(float f, float f2) {
            AdvancedSlider.this.moveStartPoint(f, f2);
        }

        private Utils.Point getPoint(float f, float f2, float f3, float f4, long j) {
            Utils.Point point = new Utils.Point(f, f2);
            Utils.Point point2 = new Utils.Point(f3, f4);
            double dDist = Utils.Dist(point, point2, true);
            double d = j;
            if (d >= dDist) {
                return null;
            }
            double d2 = (dDist - d) / dDist;
            return new Utils.Point(point.x + ((point2.x - point.x) * d2), point.y + ((point2.y - point.y) * d2));
        }

        public boolean isRunning() {
            return this.mStartTime >= 0;
        }

        public void stopRunning() {
            this.mStartTime = -1L;
        }
    }

    private class SpeedAccController extends ReboundAnimationController implements ITicker {
        private int mBounceAccelation;
        private Expression mBounceAccelationExp;
        private int mBounceInitSpeed;
        private Expression mBounceInitSpeedExp;
        private IndexedVariable mBounceProgress;

        public SpeedAccController(Element element) {
            super(AdvancedSlider.this, null);
            this.mBounceInitSpeedExp = Expression.build(AdvancedSlider.this.getVariables(), AdvancedSlider.this.getAttr(element, "bounceInitSpeed"));
            this.mBounceAccelationExp = Expression.build(AdvancedSlider.this.getVariables(), AdvancedSlider.this.getAttr(element, "bounceAcceleration"));
            if (AdvancedSlider.this.mHasName) {
                this.mBounceProgress = new IndexedVariable(AdvancedSlider.this.mName + ".bounce_progress", AdvancedSlider.this.getVariables(), true);
            }
        }

        @Override // com.miui.maml.elements.AdvancedSlider.ReboundAnimationController
        public void init() {
            super.init();
            IndexedVariable indexedVariable = this.mBounceProgress;
            if (indexedVariable != null) {
                indexedVariable.set(1.0d);
            }
        }

        @Override // com.miui.maml.elements.AdvancedSlider.ReboundAnimationController
        public void start(EndPoint endPoint) {
            if (this.mBounceInitSpeedExp == null) {
                onStop();
            } else {
                super.start(endPoint);
            }
        }

        @Override // com.miui.maml.elements.AdvancedSlider.ReboundAnimationController
        protected void onStart() {
            Expression expression = this.mBounceInitSpeedExp;
            if (expression != null) {
                this.mBounceInitSpeed = (int) AdvancedSlider.this.evaluate(expression);
            }
            Expression expression2 = this.mBounceAccelationExp;
            if (expression2 != null) {
                this.mBounceAccelation = (int) AdvancedSlider.this.evaluate(expression2);
            }
            IndexedVariable indexedVariable = this.mBounceProgress;
            if (indexedVariable != null) {
                indexedVariable.set(0.0d);
            }
        }

        @Override // com.miui.maml.elements.AdvancedSlider.ReboundAnimationController
        protected long getDistance(long j) {
            int i = this.mBounceInitSpeed;
            int i2 = this.mBounceAccelation;
            long j2 = ((((long) i) * j) / 1000) + (((((long) i2) * j) * j) / 2000000);
            if (((long) i) + ((((long) i2) * j) / 1000) <= 0) {
                onStop();
                IndexedVariable indexedVariable = this.mBounceProgress;
                if (indexedVariable != null) {
                    indexedVariable.set(1.0d);
                }
            }
            if (this.mTotalDistance > 0.0d) {
                double d = j2 / this.mTotalDistance;
                IndexedVariable indexedVariable2 = this.mBounceProgress;
                if (indexedVariable2 != null) {
                    indexedVariable2.set(d <= 1.0d ? d : 1.0d);
                }
            }
            return j2;
        }
    }

    private class InterpolatorController extends ReboundAnimationController {
        private InterpolatorHelper mInterpolator;
        private long mReboundTime;
        private Expression mReboundTimeExp;

        public InterpolatorController(InterpolatorHelper interpolatorHelper, Expression expression) {
            super(AdvancedSlider.this, null);
            this.mInterpolator = interpolatorHelper;
            this.mReboundTimeExp = expression;
        }

        @Override // com.miui.maml.elements.AdvancedSlider.ReboundAnimationController
        protected void onStart() {
            this.mReboundTime = (long) this.mReboundTimeExp.evaluate();
        }

        @Override // com.miui.maml.elements.AdvancedSlider.ReboundAnimationController
        protected long getDistance(long j) {
            long j2 = this.mReboundTime;
            if (j >= j2) {
                onStop();
                return (long) this.mTotalDistance;
            }
            return (long) (this.mTotalDistance * ((double) this.mInterpolator.get(j / j2)));
        }
    }

    public AdvancedSlider(Element element, ScreenElementRoot screenElementRoot) {
        super(element, screenElementRoot);
        load(element);
    }

    private void load(Element element) {
        if (element == null) {
            return;
        }
        if (this.mHasName) {
            this.mStateVar = new IndexedVariable(this.mName + ".state", getVariables(), true);
            this.mMoveXVar = new IndexedVariable(this.mName + ".move_x", getVariables(), true);
            this.mMoveYVar = new IndexedVariable(this.mName + ".move_y", getVariables(), true);
            this.mMoveDistVar = new IndexedVariable(this.mName + ".move_dist", getVariables(), true);
        }
        StartPoint startPoint = this.mStartPoint;
        if (startPoint != null && startPoint.mReboundController != null) {
            this.mReboundAnimationController = this.mStartPoint.mReboundController;
        } else {
            this.mReboundAnimationController = new SpeedAccController(element);
            this.mRoot.addPreTicker(this.mReboundAnimationController);
        }
        this.mIsHaptic = Boolean.parseBoolean(getAttr(element, "haptic"));
        this.mIsKeepStatusAfterLaunch = Boolean.parseBoolean(getAttr(element, "keepStatusAfterLaunch"));
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.miui.maml.elements.ElementGroup
    public ScreenElement onCreateChild(Element element) {
        String tagName = element.getTagName();
        if (tagName.equalsIgnoreCase(StartPoint.TAG_NAME)) {
            StartPoint startPoint = new StartPoint(element, this.mRoot);
            this.mStartPoint = startPoint;
            return startPoint;
        }
        if (tagName.equalsIgnoreCase(EndPoint.TAG_NAME)) {
            EndPoint endPoint = new EndPoint(element, this.mRoot);
            if (this.mEndPoints == null) {
                this.mEndPoints = new ArrayList<>();
            }
            this.mEndPoints.add(endPoint);
            return endPoint;
        }
        return super.onCreateChild(element);
    }

    public void setOnLaunchListener(OnLaunchListener onLaunchListener) {
        this.mOnLaunchListener = onLaunchListener;
    }

    private class SliderPoint extends ElementGroup {
        private ScreenElement mCurrentStateElements;
        protected boolean mIsAlignChildren;
        protected String mName;
        protected String mNormalSound;
        protected ElementGroup mNormalStateElements;

        @Deprecated
        private CommandTrigger mNormalStateTrigger;
        private IndexedVariable mPointStateVar;
        protected String mPressedSound;
        protected ElementGroup mPressedStateElements;

        @Deprecated
        private CommandTrigger mPressedStateTrigger;
        protected String mReachedSound;
        private ElementGroup mReachedStateElements;

        @Deprecated
        private CommandTrigger mReachedStateTrigger;
        private State mState;

        public SliderPoint(Element element, ScreenElementRoot screenElementRoot, String str) {
            super(element, screenElementRoot);
            this.mState = State.Invalid;
            load(element, str);
        }

        /* JADX INFO: Access modifiers changed from: protected */
        @Override // com.miui.maml.elements.ElementGroup
        public ScreenElement onCreateChild(Element element) {
            String tagName = element.getTagName();
            if (tagName.equalsIgnoreCase("NormalState")) {
                ElementGroup elementGroup = new ElementGroup(element, this.mRoot);
                this.mNormalStateElements = elementGroup;
                return elementGroup;
            }
            if (tagName.equalsIgnoreCase("PressedState")) {
                ElementGroup elementGroup2 = new ElementGroup(element, this.mRoot);
                this.mPressedStateElements = elementGroup2;
                return elementGroup2;
            }
            if (tagName.equalsIgnoreCase("ReachedState")) {
                ElementGroup elementGroup3 = new ElementGroup(element, this.mRoot);
                this.mReachedStateElements = elementGroup3;
                return elementGroup3;
            }
            return super.onCreateChild(element);
        }

        private void load(Element element, String str) {
            this.mName = getAttr(element, "name");
            this.mNormalSound = getAttr(element, "normalSound");
            this.mPressedSound = getAttr(element, "pressedSound");
            this.mReachedSound = getAttr(element, "reachedSound");
            this.mNormalStateTrigger = loadTrigger(element, "NormalState");
            this.mPressedStateTrigger = loadTrigger(element, "PressedState");
            this.mReachedStateTrigger = loadTrigger(element, "ReachedState");
            if (!TextUtils.isEmpty(this.mName)) {
                this.mPointStateVar = new IndexedVariable(this.mName + ".state", getVariables(), true);
            }
            this.mIsAlignChildren = Boolean.parseBoolean(getAttr(element, "alignChildren"));
        }

        private CommandTrigger loadTrigger(Element element, String str) {
            Element child = Utils.getChild(element, str);
            if (child != null) {
                return CommandTrigger.fromParentElement(child, this.mRoot);
            }
            return null;
        }

        @Override // com.miui.maml.elements.ElementGroup, com.miui.maml.elements.AnimatedScreenElement, com.miui.maml.elements.ScreenElement
        public void init() {
            super.init();
            ElementGroup elementGroup = this.mNormalStateElements;
            if (elementGroup != null) {
                elementGroup.show(true);
            }
            ElementGroup elementGroup2 = this.mPressedStateElements;
            if (elementGroup2 != null) {
                elementGroup2.show(false);
            }
            ElementGroup elementGroup3 = this.mReachedStateElements;
            if (elementGroup3 != null) {
                elementGroup3.show(false);
            }
            setState(State.Normal);
            CommandTrigger commandTrigger = this.mNormalStateTrigger;
            if (commandTrigger != null) {
                commandTrigger.init();
            }
            CommandTrigger commandTrigger2 = this.mPressedStateTrigger;
            if (commandTrigger2 != null) {
                commandTrigger2.init();
            }
            CommandTrigger commandTrigger3 = this.mReachedStateTrigger;
            if (commandTrigger3 != null) {
                commandTrigger3.init();
            }
        }

        @Override // com.miui.maml.elements.ElementGroup, com.miui.maml.elements.ScreenElement
        protected void doRender(Canvas canvas) {
            canvas.save();
            if (!this.mIsAlignChildren) {
                canvas.translate(-getLeft(), -getTop());
            }
            super.doRender(canvas);
            canvas.restore();
        }

        @Override // com.miui.maml.elements.ElementGroup
        protected float getParentLeft() {
            return (this.mIsAlignChildren ? getLeft() : 0.0f) + (this.mParent != null ? this.mParent.getParentLeft() : 0.0f);
        }

        @Override // com.miui.maml.elements.ElementGroup
        protected float getParentTop() {
            return (this.mIsAlignChildren ? getTop() : 0.0f) + (this.mParent != null ? this.mParent.getParentTop() : 0.0f);
        }

        public void setState(State state) {
            ElementGroup elementGroup;
            boolean z;
            int i;
            ElementGroup elementGroup2;
            State state2 = this.mState;
            if (state2 == state) {
                return;
            }
            this.mState = state;
            int i2 = AnonymousClass1.$SwitchMap$com$miui$maml$elements$AdvancedSlider$State[state.ordinal()];
            if (i2 == 1) {
                elementGroup = this.mNormalStateElements;
                z = this.mPressedStateElements != null;
                i = 0;
            } else if (i2 == 2) {
                ElementGroup elementGroup3 = this.mPressedStateElements;
                ElementGroup elementGroup4 = elementGroup3 != null ? elementGroup3 : this.mNormalStateElements;
                z = (elementGroup3 == null || AdvancedSlider.this.mStartPointPressed) ? false : true;
                elementGroup = elementGroup4;
                i = 1;
            } else if (i2 != 3) {
                elementGroup = null;
                z = false;
                i = 0;
            } else {
                ElementGroup elementGroup5 = this.mReachedStateElements;
                if (elementGroup5 != null) {
                    elementGroup2 = elementGroup5;
                } else {
                    elementGroup2 = this.mPressedStateElements;
                    if (elementGroup2 == null) {
                        elementGroup2 = this.mNormalStateElements;
                    }
                }
                z = elementGroup5 != null;
                elementGroup = elementGroup2;
                i = 2;
            }
            ScreenElement screenElement = this.mCurrentStateElements;
            if (screenElement != elementGroup) {
                if (screenElement != null) {
                    screenElement.show(false);
                }
                if (elementGroup != null) {
                    elementGroup.show(true);
                }
                this.mCurrentStateElements = elementGroup;
            }
            if (elementGroup != null && z) {
                elementGroup.reset();
            }
            IndexedVariable indexedVariable = this.mPointStateVar;
            if (indexedVariable != null) {
                indexedVariable.set(i);
            }
            onStateChange(state2, this.mState);
        }

        public State getState() {
            return this.mState;
        }

        protected void onStateChange(State state, State state2) {
            int i = AnonymousClass1.$SwitchMap$com$miui$maml$elements$AdvancedSlider$State[state2.ordinal()];
            if (i == 1) {
                CommandTrigger commandTrigger = this.mNormalStateTrigger;
                if (commandTrigger != null) {
                    commandTrigger.perform();
                }
                performAction("normal");
                return;
            }
            if (i != 2) {
                if (i != 3) {
                    return;
                }
                CommandTrigger commandTrigger2 = this.mReachedStateTrigger;
                if (commandTrigger2 != null) {
                    commandTrigger2.perform();
                }
                performAction("reached");
                return;
            }
            CommandTrigger commandTrigger3 = this.mPressedStateTrigger;
            if (commandTrigger3 != null) {
                commandTrigger3.perform();
            }
            performAction("pressed");
            int i2 = AnonymousClass1.$SwitchMap$com$miui$maml$elements$AdvancedSlider$State[state.ordinal()];
            if (i2 == 1) {
                performAction("pressed_normal");
            } else {
                if (i2 != 3) {
                    return;
                }
                performAction("pressed_reached");
            }
        }
    }

    /* JADX INFO: renamed from: com.miui.maml.elements.AdvancedSlider$1, reason: invalid class name */
    static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$com$miui$maml$elements$AdvancedSlider$State;

        static {
            int[] iArr = new int[State.values().length];
            $SwitchMap$com$miui$maml$elements$AdvancedSlider$State = iArr;
            try {
                iArr[State.Normal.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$com$miui$maml$elements$AdvancedSlider$State[State.Pressed.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                $SwitchMap$com$miui$maml$elements$AdvancedSlider$State[State.Reached.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
        }
    }

    private class StartPoint extends SliderPoint {
        public static final String TAG_NAME = "StartPoint";
        private float mAnchorX;
        private float mAnchorY;
        protected float mOffsetX;
        protected float mOffsetY;
        public InterpolatorController mReboundController;

        public StartPoint(Element element, ScreenElementRoot screenElementRoot) {
            super(element, screenElementRoot, TAG_NAME);
            this.mAnchorX = Utils.getAttrAsFloat(element, "anchorX", 0.0f);
            this.mAnchorY = Utils.getAttrAsFloat(element, "anchorY", 0.0f);
            InterpolatorHelper interpolatorHelperCreate = InterpolatorHelper.create(getVariables(), element);
            Expression expressionBuild = Expression.build(getVariables(), element.getAttribute("easeTime"));
            if (interpolatorHelperCreate == null || expressionBuild == null) {
                return;
            }
            this.mReboundController = AdvancedSlider.this.new InterpolatorController(interpolatorHelperCreate, expressionBuild);
        }

        @Override // com.miui.maml.elements.AdvancedSlider.SliderPoint
        protected void onStateChange(State state, State state2) {
            if (state == State.Invalid) {
                return;
            }
            int i = AnonymousClass1.$SwitchMap$com$miui$maml$elements$AdvancedSlider$State[state2.ordinal()];
            if (i == 1) {
                this.mRoot.playSound(this.mNormalSound);
            } else if (i == 2 && !this.mPressed) {
                this.mRoot.playSound(this.mPressedSound);
            }
            super.onStateChange(state, state2);
        }

        @Override // com.miui.maml.elements.AdvancedSlider.SliderPoint, com.miui.maml.elements.ElementGroup, com.miui.maml.elements.AnimatedScreenElement, com.miui.maml.elements.ScreenElement
        public void init() {
            super.init();
            InterpolatorController interpolatorController = this.mReboundController;
            if (interpolatorController != null) {
                interpolatorController.init();
            }
        }

        @Override // com.miui.maml.elements.ElementGroup, com.miui.maml.elements.AnimatedScreenElement, com.miui.maml.elements.ScreenElement
        protected void doTick(long j) {
            super.doTick(j);
            InterpolatorController interpolatorController = this.mReboundController;
            if (interpolatorController != null) {
                interpolatorController.tick(j);
            }
        }

        @Override // com.miui.maml.elements.AdvancedSlider.SliderPoint, com.miui.maml.elements.ElementGroup, com.miui.maml.elements.ScreenElement
        public void doRender(Canvas canvas) {
            int iSave = canvas.save();
            canvas.translate(this.mOffsetX, this.mOffsetY);
            super.doRender(canvas);
            canvas.restoreToCount(iSave);
        }

        public void moveTo(float f, float f2) {
            this.mOffsetX = f;
            this.mOffsetY = f2;
        }

        public float getOffsetX() {
            return this.mOffsetX;
        }

        public float getOffsetY() {
            return this.mOffsetY;
        }

        public float getAnchorX() {
            return getLeft() + this.mAnchorX;
        }

        public float getAnchorY() {
            return getTop() + this.mAnchorY;
        }
    }

    private class Position {
        public static final String TAG_NAME = "Position";
        private Expression mBaseX;
        private Expression mBaseY;
        private Expression mX;
        private Expression mY;

        public Position(Variables variables, Element element, Expression expression, Expression expression2) {
            this.mBaseX = expression;
            this.mBaseY = expression2;
            this.mX = Expression.build(variables, AdvancedSlider.this.getAttr(element, AnimatedProperty.PROPERTY_NAME_X));
            this.mY = Expression.build(variables, AdvancedSlider.this.getAttr(element, AnimatedProperty.PROPERTY_NAME_Y));
        }

        public float getX() {
            AdvancedSlider advancedSlider = AdvancedSlider.this;
            Expression expression = this.mX;
            double dEvaluate = expression == null ? 0.0d : expression.evaluate();
            Expression expression2 = this.mBaseX;
            return advancedSlider.scale(dEvaluate + (expression2 != null ? expression2.evaluate() : 0.0d));
        }

        public float getY() {
            AdvancedSlider advancedSlider = AdvancedSlider.this;
            Expression expression = this.mY;
            double dEvaluate = expression == null ? 0.0d : expression.evaluate();
            Expression expression2 = this.mBaseY;
            return advancedSlider.scale(dEvaluate + (expression2 != null ? expression2.evaluate() : 0.0d));
        }
    }

    private class LaunchAction {
        public ActionCommand mCommand;
        public boolean mConfigTaskLoaded;
        public IntentInfo mIntentInfo;
        public CommandTrigger mTrigger;

        private LaunchAction() {
        }

        /* synthetic */ LaunchAction(AdvancedSlider advancedSlider, AnonymousClass1 anonymousClass1) {
            this();
        }

        public Intent perform() {
            if (this.mIntentInfo != null) {
                return performTask();
            }
            ActionCommand actionCommand = this.mCommand;
            if (actionCommand != null) {
                actionCommand.perform();
                return null;
            }
            CommandTrigger commandTrigger = this.mTrigger;
            if (commandTrigger == null) {
                return null;
            }
            commandTrigger.perform();
            return null;
        }

        private Intent performTask() {
            if (this.mIntentInfo == null) {
                return null;
            }
            if (!this.mConfigTaskLoaded) {
                Task taskFindTask = AdvancedSlider.this.mRoot.findTask(this.mIntentInfo.getId());
                if (taskFindTask != null && !TextUtils.isEmpty(taskFindTask.action)) {
                    this.mIntentInfo.set(taskFindTask);
                }
                this.mConfigTaskLoaded = true;
            }
            if (Utils.isProtectedIntent(this.mIntentInfo.getAction())) {
                return null;
            }
            Intent intent = new Intent();
            this.mIntentInfo.update(intent);
            intent.setFlags(872415232);
            return intent;
        }

        public void finish() {
            ActionCommand actionCommand = this.mCommand;
            if (actionCommand != null) {
                actionCommand.finish();
            }
            CommandTrigger commandTrigger = this.mTrigger;
            if (commandTrigger != null) {
                commandTrigger.finish();
            }
            this.mConfigTaskLoaded = false;
        }

        public void init() {
            ActionCommand actionCommand = this.mCommand;
            if (actionCommand != null) {
                actionCommand.init();
            }
            CommandTrigger commandTrigger = this.mTrigger;
            if (commandTrigger != null) {
                commandTrigger.init();
            }
        }

        public void pause() {
            ActionCommand actionCommand = this.mCommand;
            if (actionCommand != null) {
                actionCommand.pause();
            }
            CommandTrigger commandTrigger = this.mTrigger;
            if (commandTrigger != null) {
                commandTrigger.pause();
            }
        }

        public void resume() {
            ActionCommand actionCommand = this.mCommand;
            if (actionCommand != null) {
                actionCommand.resume();
            }
            CommandTrigger commandTrigger = this.mTrigger;
            if (commandTrigger != null) {
                commandTrigger.resume();
            }
        }
    }

    private class EndPoint extends SliderPoint {
        public static final String TAG_NAME = "EndPoint";
        public LaunchAction mAction;
        private ArrayList<Position> mPath;
        private Expression mPathX;
        private Expression mPathY;
        private int mRawTolerance;
        private float mTolerance;

        public EndPoint(Element element, ScreenElementRoot screenElementRoot) {
            super(element, screenElementRoot, TAG_NAME);
            this.mRawTolerance = 150;
            load(element);
        }

        private void load(Element element) {
            loadTask(element);
            loadPath(element);
        }

        @Override // com.miui.maml.elements.AdvancedSlider.SliderPoint, com.miui.maml.elements.ElementGroup, com.miui.maml.elements.AnimatedScreenElement, com.miui.maml.elements.ScreenElement
        public void init() {
            super.init();
            LaunchAction launchAction = this.mAction;
            if (launchAction != null) {
                launchAction.init();
            }
            this.mTolerance = scale(this.mRawTolerance);
        }

        @Override // com.miui.maml.elements.ElementGroup, com.miui.maml.elements.AnimatedScreenElement, com.miui.maml.elements.ScreenElement
        public void finish() {
            super.finish();
            LaunchAction launchAction = this.mAction;
            if (launchAction != null) {
                launchAction.finish();
            }
        }

        @Override // com.miui.maml.elements.ElementGroup, com.miui.maml.elements.AnimatedScreenElement, com.miui.maml.elements.ScreenElement
        public void pause() {
            super.pause();
            LaunchAction launchAction = this.mAction;
            if (launchAction != null) {
                launchAction.pause();
            }
        }

        @Override // com.miui.maml.elements.ElementGroup, com.miui.maml.elements.ScreenElement
        public void resume() {
            super.resume();
            LaunchAction launchAction = this.mAction;
            if (launchAction != null) {
                launchAction.resume();
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public Utils.Point getNearestPoint(float f, float f2) {
            if (this.mPath == null) {
                return new Utils.Point(f - AdvancedSlider.this.mTouchOffsetX, f2 - AdvancedSlider.this.mTouchOffsetY);
            }
            Utils.Point point = null;
            double d = Double.MAX_VALUE;
            for (int i = 1; i < this.mPath.size(); i++) {
                float f3 = f - AdvancedSlider.this.mTouchOffsetX;
                float f4 = f2 - AdvancedSlider.this.mTouchOffsetY;
                Position position = this.mPath.get(i - 1);
                Position position2 = this.mPath.get(i);
                Utils.Point point2 = new Utils.Point(position.getX(), position.getY());
                Utils.Point point3 = new Utils.Point(position2.getX(), position2.getY());
                Utils.Point point4 = new Utils.Point(f3, f4);
                Utils.Point pointPointProjectionOnSegment = Utils.pointProjectionOnSegment(point2, point3, point4, true);
                double dDist = Utils.Dist(pointPointProjectionOnSegment, point4, false);
                if (dDist < d) {
                    point = pointPointProjectionOnSegment;
                    d = dDist;
                }
            }
            return point;
        }

        public float getTransformedDist(Utils.Point point, float f, float f2) {
            if (this.mPath == null) {
                return AdvancedSlider.FREE_ENDPOINT_DIST;
            }
            if (point == null) {
                return Float.MAX_VALUE;
            }
            float fDist = (float) Utils.Dist(point, new Utils.Point(f - AdvancedSlider.this.mTouchOffsetX, f2 - AdvancedSlider.this.mTouchOffsetY), true);
            if (fDist < this.mTolerance) {
                return fDist;
            }
            return Float.MAX_VALUE;
        }

        private void loadPath(Element element) {
            Element child = Utils.getChild(element, "Path");
            if (child == null) {
                this.mPath = null;
                return;
            }
            this.mRawTolerance = getAttrAsInt(child, "tolerance", 150);
            this.mPath = new ArrayList<>();
            Variables variables = getVariables();
            this.mPathX = Expression.build(variables, child.getAttribute(AnimatedProperty.PROPERTY_NAME_X));
            this.mPathY = Expression.build(variables, child.getAttribute(AnimatedProperty.PROPERTY_NAME_Y));
            NodeList elementsByTagName = child.getElementsByTagName("Position");
            for (int i = 0; i < elementsByTagName.getLength(); i++) {
                this.mPath.add(AdvancedSlider.this.new Position(variables, (Element) elementsByTagName.item(i), this.mPathX, this.mPathY));
            }
        }

        private void loadTask(Element element) {
            Element child = Utils.getChild(element, "Intent");
            Element child2 = Utils.getChild(element, ActionCommand.TAG_NAME);
            Element child3 = Utils.getChild(element, CommandTrigger.TAG_NAME);
            if (child == null && child2 == null && child3 == null) {
                return;
            }
            LaunchAction launchAction = new LaunchAction(AdvancedSlider.this, null);
            this.mAction = launchAction;
            if (child != null) {
                launchAction.mIntentInfo = new IntentInfo(child, getVariables());
                return;
            }
            if (child2 == null) {
                if (child3 != null) {
                    launchAction.mTrigger = new CommandTrigger(child3, this.mRoot);
                }
            } else {
                launchAction.mCommand = ActionCommand.create(child2, this.mRoot);
                if (this.mAction.mCommand == null) {
                    Log.w(AdvancedSlider.LOG_TAG, "invalid Command element: " + child2.toString());
                }
            }
        }

        @Override // com.miui.maml.elements.AdvancedSlider.SliderPoint
        protected void onStateChange(State state, State state2) {
            if (state == State.Invalid) {
                return;
            }
            if (AnonymousClass1.$SwitchMap$com$miui$maml$elements$AdvancedSlider$State[state2.ordinal()] == 3) {
                this.mRoot.playSound(this.mReachedSound);
            }
            super.onStateChange(state, state2);
        }
    }

    @Override // com.miui.maml.elements.ElementGroup, com.miui.maml.elements.AnimatedScreenElement, com.miui.maml.elements.ScreenElement
    public void init() {
        super.init();
        this.mReboundAnimationController.init();
        resetInner();
    }

    @Override // com.miui.maml.elements.ElementGroup, com.miui.maml.elements.AnimatedScreenElement, com.miui.maml.elements.ScreenElement
    public void finish() {
        super.finish();
        resetInner();
    }

    @Override // com.miui.maml.elements.ElementGroup, com.miui.maml.elements.AnimatedScreenElement, com.miui.maml.elements.ScreenElement
    public void pause() {
        super.pause();
        resetInner();
    }

    /* JADX WARN: Code duplicated, block: B:53:0x011c  */
    @Override // com.miui.maml.elements.ElementGroup, com.miui.maml.elements.AnimatedScreenElement, com.miui.maml.elements.ScreenElement
    public boolean onTouch(MotionEvent motionEvent) {
        boolean z;
        boolean zDoLaunch;
        if (!isVisible()) {
            return false;
        }
        float x = motionEvent.getX();
        float y = motionEvent.getY();
        float absoluteLeft = x - getAbsoluteLeft();
        float absoluteTop = y - getAbsoluteTop();
        int actionMasked = motionEvent.getActionMasked();
        if (actionMasked != 0) {
            if (actionMasked != 1) {
                if (actionMasked == 2) {
                    if (this.mMoving) {
                        CheckTouchResult checkTouchResultCheckTouch = checkTouch(absoluteLeft, absoluteTop);
                        if (checkTouchResultCheckTouch != null) {
                            this.mCurrentEndPoint = checkTouchResultCheckTouch.endPoint;
                            onMove(absoluteLeft, absoluteTop);
                        } else {
                            this.mReboundAnimationController.start(this.mCurrentEndPoint);
                            this.mMoving = false;
                            onRelease();
                        }
                        z = true;
                    } else {
                        z = false;
                    }
                } else if (actionMasked == 3 && this.mMoving) {
                    this.mReboundAnimationController.start(null);
                    this.mCurrentEndPoint = null;
                    this.mMoving = false;
                    onRelease();
                    this.mStartPoint.performAction(ToDoStat.CANCEL);
                    z = true;
                } else {
                    z = false;
                }
            } else if (this.mMoving) {
                Log.i(LOG_TAG, "unlock touch up");
                CheckTouchResult checkTouchResultCheckTouch2 = checkTouch(absoluteLeft, absoluteTop);
                if (checkTouchResultCheckTouch2 != null) {
                    if (checkTouchResultCheckTouch2.reached) {
                        zDoLaunch = doLaunch(checkTouchResultCheckTouch2.endPoint);
                    } else {
                        this.mStartPoint.performAction("release");
                        if (checkTouchResultCheckTouch2.endPoint != null) {
                            checkTouchResultCheckTouch2.endPoint.performAction("release");
                        }
                        zDoLaunch = false;
                    }
                    this.mCurrentEndPoint = checkTouchResultCheckTouch2.endPoint;
                } else {
                    zDoLaunch = false;
                }
                this.mMoving = false;
                if (!zDoLaunch) {
                    this.mReboundAnimationController.start(this.mCurrentEndPoint);
                }
                onRelease();
                z = true;
            } else {
                z = false;
            }
        } else if (this.mStartPoint.touched(absoluteLeft, absoluteTop, false)) {
            this.mMoving = true;
            this.mTouchOffsetX = absoluteLeft - this.mStartPoint.getAnchorX();
            this.mTouchOffsetY = absoluteTop - this.mStartPoint.getAnchorY();
            if (this.mReboundAnimationController.isRunning()) {
                this.mReboundAnimationController.stopRunning();
                this.mTouchOffsetX -= this.mStartPoint.getOffsetX();
                this.mTouchOffsetY -= this.mStartPoint.getOffsetY();
            }
            this.mStartPoint.setState(State.Pressed);
            Iterator<EndPoint> it = this.mEndPoints.iterator();
            while (it.hasNext()) {
                it.next().setState(State.Pressed);
            }
            this.mStartPointPressed = true;
            if (this.mHasName) {
                this.mStateVar.set(1.0d);
            }
            this.mReboundAnimationController.init();
            onStart();
            z = true;
        } else {
            z = false;
        }
        return super.onTouch(motionEvent) || (z && this.mInterceptTouch);
    }

    private class CheckTouchResult {
        public EndPoint endPoint;
        public boolean reached;

        private CheckTouchResult() {
        }

        /* synthetic */ CheckTouchResult(AdvancedSlider advancedSlider, AnonymousClass1 anonymousClass1) {
            this();
        }
    }

    private CheckTouchResult checkTouch(float f, float f2) {
        boolean zCheckEndPoint;
        CheckTouchResult checkTouchResult = new CheckTouchResult(this, null);
        Utils.Point point = null;
        float f3 = Float.MAX_VALUE;
        for (EndPoint endPoint : this.mEndPoints) {
            Utils.Point nearestPoint = endPoint.getNearestPoint(f, f2);
            float transformedDist = endPoint.getTransformedDist(nearestPoint, f, f2);
            if (transformedDist < f3) {
                checkTouchResult.endPoint = endPoint;
                point = nearestPoint;
                f3 = transformedDist;
            }
        }
        if (f3 < Float.MAX_VALUE) {
            moveStartPoint((float) point.x, (float) point.y);
            if (f3 < FREE_ENDPOINT_DIST) {
                zCheckEndPoint = checkEndPoint(point, checkTouchResult.endPoint);
            } else {
                boolean zCheckEndPoint2 = false;
                for (EndPoint endPoint2 : this.mEndPoints) {
                    if (endPoint2.mPath == null && (zCheckEndPoint2 = checkEndPoint(point, endPoint2))) {
                        checkTouchResult.endPoint = endPoint2;
                        break;
                    }
                }
                zCheckEndPoint = zCheckEndPoint2;
            }
            this.mStartPoint.setState(zCheckEndPoint ? State.Reached : State.Pressed);
            if (this.mHasName) {
                this.mStateVar.set(zCheckEndPoint ? 2.0d : 1.0d);
            }
            checkTouchResult.reached = zCheckEndPoint;
            return checkTouchResult;
        }
        Log.i(LOG_TAG, "unlock touch canceled due to exceeding tollerance");
        this.mStartPoint.performAction(ToDoStat.CANCEL);
        return null;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void moveStartPoint(float f, float f2) {
        float anchorX = f - this.mStartPoint.getAnchorX();
        float anchorY = f2 - this.mStartPoint.getAnchorY();
        this.mStartPoint.moveTo(anchorX, anchorY);
        if (this.mHasName) {
            double dDescale = descale(anchorX);
            double dDescale2 = descale(anchorY);
            double dSqrt = Math.sqrt((dDescale * dDescale) + (dDescale2 * dDescale2));
            this.mMoveXVar.set(dDescale);
            this.mMoveYVar.set(dDescale2);
            this.mMoveDistVar.set(dSqrt);
        }
    }

    private boolean checkEndPoint(Utils.Point point, EndPoint endPoint) {
        if (endPoint.touched((float) point.x, (float) point.y, false)) {
            if (endPoint.getState() == State.Reached) {
                return true;
            }
            endPoint.setState(State.Reached);
            for (EndPoint endPoint2 : this.mEndPoints) {
                if (endPoint2 != endPoint) {
                    endPoint2.setState(State.Pressed);
                }
            }
            onReach(endPoint.mName);
            return true;
        }
        endPoint.setState(State.Pressed);
        return false;
    }

    private boolean doLaunch(EndPoint endPoint) {
        this.mStartPoint.performAction("launch");
        endPoint.performAction("launch");
        return onLaunch(endPoint.mName, endPoint.mAction != null ? endPoint.mAction.perform() : null);
    }

    protected void onStart() {
        if (this.mIsHaptic) {
            this.mRoot.haptic(1);
        }
    }

    protected void onRelease() {
        if (this.mIsHaptic) {
            this.mRoot.haptic(1);
        }
    }

    protected void onReach(String str) {
        if (this.mIsHaptic) {
            this.mRoot.haptic(0);
        }
    }

    protected boolean onLaunch(String str, Intent intent) {
        OnLaunchListener onLaunchListener = this.mOnLaunchListener;
        if (onLaunchListener != null) {
            return onLaunchListener.onLaunch(str);
        }
        return this.mIsKeepStatusAfterLaunch;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void cancelMoving() {
        resetInner();
        onCancel();
    }

    protected void resetInner() {
        if (this.mStartPointPressed) {
            this.mStartPointPressed = false;
            this.mStartPoint.moveTo(0.0f, 0.0f);
            this.mStartPoint.setState(State.Normal);
            Iterator<EndPoint> it = this.mEndPoints.iterator();
            while (it.hasNext()) {
                it.next().setState(State.Normal);
            }
            if (this.mHasName) {
                this.mMoveXVar.set(0.0d);
                this.mMoveYVar.set(0.0d);
                this.mMoveDistVar.set(0.0d);
                this.mStateVar.set(0.0d);
            }
            this.mMoving = false;
            requestUpdate();
        }
    }

    @Override // com.miui.maml.elements.ElementGroup, com.miui.maml.elements.ScreenElement
    public void reset(long j) {
        super.reset(j);
        resetInner();
    }
}
