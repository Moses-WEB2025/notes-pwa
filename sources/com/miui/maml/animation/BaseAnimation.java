package com.miui.maml.animation;

import android.text.TextUtils;
import android.util.Log;
import com.miui.maml.CommandTriggers;
import com.miui.maml.animation.interpolater.InterpolatorHelper;
import com.miui.maml.data.Expression;
import com.miui.maml.data.IndexedVariable;
import com.miui.maml.data.Variables;
import com.miui.maml.elements.ListScreenElement;
import com.miui.maml.elements.ScreenElement;
import com.miui.maml.util.Utils;
import java.util.ArrayList;
import org.w3c.dom.Element;

/* JADX INFO: loaded from: classes3.dex */
public abstract class BaseAnimation {
    private static final long INFINITE_TIME = 1000000000000L;
    private static final String LOG_TAG = "BaseAnimation";
    public static final int PLAY_TO_END = -1;
    private static final String VAR_CURRENT_FRAME = "current_frame";
    private long mAnimEndTime;
    private long mAnimStartTime;
    protected String[] mAttrs;
    private double[] mCurValues;
    private IndexedVariable mCurrentFrame;
    private Expression mDelay;
    private boolean mDisable;
    private long mEndTime;
    private boolean mHasName;
    private boolean mInitPaused;
    private boolean mIsDelay;
    private boolean mIsFirstFrame;
    private boolean mIsFirstReset;
    private boolean mIsLastFrame;
    private boolean mIsLoop;
    private boolean mIsPaused;
    private boolean mIsReverse;
    private boolean mIsTimeInfinite;
    protected ArrayList<AnimationItem> mItems;
    private boolean mLoop;
    private String mName;
    private long mPauseTime;
    private long mPlayTimeRange;
    private long mRealTimeRange;
    private long mResetTime;
    protected ScreenElement mScreenElement;
    private long mStartTime;
    private String mTag;
    private CommandTriggers mTriggers;

    protected double getDefaultValue() {
        return 0.0d;
    }

    public static class AnimationItem {
        private BaseAnimation mAni;
        private double[] mAttrsValue;
        public Expression mDeltaTimeExp;
        public Expression[] mExps;
        public long mInitTime;
        public InterpolatorHelper mInterpolator;
        private String mName;
        private boolean mNeedEvaluate = true;
        public long mTime;

        public AnimationItem(BaseAnimation baseAnimation, Element element) {
            this.mAni = baseAnimation;
            load(element);
        }

        public double get(int i) {
            double[] dArr = this.mAttrsValue;
            if (dArr == null || i < 0 || i >= dArr.length) {
                Log.e(BaseAnimation.LOG_TAG, "fail to get number in AnimationItem:" + i);
                return 0.0d;
            }
            if (this.mNeedEvaluate) {
                reevaluate();
                this.mNeedEvaluate = false;
            }
            return this.mAttrsValue[i];
        }

        public boolean attrExists(int i) {
            Expression[] expressionArr = this.mExps;
            return expressionArr != null && i >= 0 && i < expressionArr.length && expressionArr[i] != null;
        }

        public void changeInterpolator(String str, String str2, String str3) {
            this.mInterpolator = new InterpolatorHelper(this.mAni.getVariables(), str, str3, str2);
        }

        private void load(Element element) {
            Variables variables = this.mAni.getVariables();
            String attribute = element.getAttribute("name");
            this.mName = attribute;
            if (!TextUtils.isEmpty(attribute)) {
                this.mAni.mScreenElement.getRoot().addAnimationItem(this.mName, this);
            }
            this.mInterpolator = InterpolatorHelper.create(variables, element);
            String attribute2 = element.getAttribute("time");
            if (!TextUtils.isEmpty(attribute2)) {
                try {
                    this.mTime = Long.parseLong(attribute2);
                } catch (NumberFormatException unused) {
                }
            }
            this.mDeltaTimeExp = Expression.build(variables, element.getAttribute("dtime"));
            String[] attrs = this.mAni.getAttrs();
            if (attrs != null) {
                this.mAttrsValue = new double[attrs.length];
                this.mExps = new Expression[attrs.length];
                int length = attrs.length;
                int i = 0;
                int i2 = 0;
                while (i < length) {
                    String str = attrs[i];
                    Expression expressionBuild = Expression.build(variables, element.getAttribute(str));
                    if (expressionBuild == null && i2 == 0 && !"value".equals(str)) {
                        expressionBuild = Expression.build(variables, element.getAttribute("value"));
                    }
                    this.mExps[i2] = expressionBuild;
                    i++;
                    i2++;
                }
            }
            this.mInitTime = this.mTime;
        }

        private void reevaluate() {
            Expression[] expressionArr = this.mExps;
            if (expressionArr == null) {
                return;
            }
            int length = expressionArr.length;
            int i = 0;
            int i2 = 0;
            while (i < length) {
                Expression expression = expressionArr[i];
                int i3 = i2 + 1;
                this.mAttrsValue[i2] = expression == null ? 0.0d : expression.evaluate();
                i++;
                i2 = i3;
            }
        }

        public void reset() {
            this.mNeedEvaluate = true;
            this.mTime = this.mInitTime;
        }
    }

    public BaseAnimation(Element element, String str, String[] strArr, ScreenElement screenElement) {
        this.mItems = new ArrayList<>();
        this.mLoop = true;
        this.mScreenElement = screenElement;
        this.mAttrs = strArr;
        this.mCurValues = new double[strArr.length];
        load(element, str);
    }

    public BaseAnimation(Element element, String str, String str2, ScreenElement screenElement) {
        this(element, str, new String[]{str2}, screenElement);
    }

    public BaseAnimation(Element element, String str, ScreenElement screenElement) {
        this(element, str, "value", screenElement);
    }

    public BaseAnimation(Element element, ScreenElement screenElement) {
        this(element, (String) null, "value", screenElement);
    }

    public String[] getAttrs() {
        return this.mAttrs;
    }

    private void load(Element element, String str) {
        String attribute = element.getAttribute("name");
        this.mName = attribute;
        this.mHasName = !TextUtils.isEmpty(attribute);
        Variables variables = getVariables();
        if (this.mHasName) {
            this.mCurrentFrame = new IndexedVariable(this.mName + ".current_frame", variables, true);
        }
        this.mDelay = Expression.build(variables, element.getAttribute("delay"));
        this.mInitPaused = Boolean.parseBoolean(element.getAttribute("initPause"));
        this.mLoop = !"false".equalsIgnoreCase(element.getAttribute("loop"));
        this.mTag = element.getAttribute("tag");
        Utils.traverseXmlElementChildrenTags(element, new String[]{str, ListScreenElement.ListItemElement.TAG_NAME}, new Utils.XmlTraverseListener() { // from class: com.miui.maml.animation.BaseAnimation.1
            @Override // com.miui.maml.util.Utils.XmlTraverseListener
            public void onChild(Element element2) {
                ArrayList<AnimationItem> arrayList = BaseAnimation.this.mItems;
                BaseAnimation baseAnimation = BaseAnimation.this;
                arrayList.add(baseAnimation.onCreateItem(baseAnimation, element2));
            }
        });
        if (this.mItems.size() <= 0) {
            Log.e(LOG_TAG, "empty items");
            return;
        }
        ArrayList<AnimationItem> arrayList = this.mItems;
        this.mIsTimeInfinite = arrayList.get(arrayList.size() - 1).mTime >= INFINITE_TIME;
        if (this.mItems.size() > 1 && this.mIsTimeInfinite) {
            ArrayList<AnimationItem> arrayList2 = this.mItems;
            this.mRealTimeRange = arrayList2.get(arrayList2.size() - 2).mTime;
        } else {
            ArrayList<AnimationItem> arrayList3 = this.mItems;
            this.mRealTimeRange = arrayList3.get(arrayList3.size() - 1).mTime;
        }
        Element child = Utils.getChild(element, CommandTriggers.TAG_NAME);
        if (child != null) {
            this.mTriggers = new CommandTriggers(child, this.mScreenElement);
        }
    }

    protected final Variables getVariables() {
        return this.mScreenElement.getVariables();
    }

    protected AnimationItem getItem(int i) {
        if (i < 0 || i >= this.mItems.size()) {
            return null;
        }
        return this.mItems.get(i);
    }

    protected AnimationItem onCreateItem(BaseAnimation baseAnimation, Element element) {
        return new AnimationItem(baseAnimation, element);
    }

    public void init() {
        CommandTriggers commandTriggers = this.mTriggers;
        if (commandTriggers != null) {
            commandTriggers.init();
        }
    }

    public void finish() {
        CommandTriggers commandTriggers = this.mTriggers;
        if (commandTriggers != null) {
            commandTriggers.finish();
        }
        int size = this.mItems.size();
        for (int i = 0; i < size; i++) {
            this.mItems.get(i).reset();
        }
        int length = this.mCurValues.length;
        for (int i2 = 0; i2 < length; i2++) {
            this.mCurValues[i2] = 0.0d;
        }
    }

    public void pause() {
        CommandTriggers commandTriggers = this.mTriggers;
        if (commandTriggers != null) {
            commandTriggers.pause();
        }
    }

    public void resume() {
        CommandTriggers commandTriggers = this.mTriggers;
        if (commandTriggers != null) {
            commandTriggers.resume();
        }
    }

    public void onAction(String str) {
        CommandTriggers commandTriggers = this.mTriggers;
        if (commandTriggers != null) {
            commandTriggers.onAction(str);
        }
    }

    public void reset(long j) {
        if (this.mDisable) {
            return;
        }
        int length = this.mAttrs.length;
        for (int i = 0; i < length; i++) {
            this.mCurValues[i] = getDelayValue(i);
        }
        if (this.mInitPaused) {
            playAnim(j, 0L, 0L, false, false);
        } else {
            playAnim(j, 0L, -1L, true, true);
        }
        if (this.mHasName) {
            this.mCurrentFrame.set(0.0d);
        }
        onAction("init");
    }

    protected double getDelayValue(int i) {
        AnimationItem item = getItem(0);
        if (item != null) {
            return item.get(i);
        }
        return 0.0d;
    }

    public void playAnim(long j, long j2, long j3, boolean z, boolean z2) {
        Expression expression;
        if (this.mDisable) {
            return;
        }
        this.mResetTime = j;
        if (j2 < 0 && j2 != -1) {
            j2 = 0;
        }
        this.mStartTime = j2;
        this.mAnimStartTime = j2;
        if (j3 < 0 && j3 != -1) {
            j3 = 0;
        }
        this.mEndTime = j3;
        this.mAnimEndTime = j3;
        this.mIsLoop = z;
        this.mIsDelay = z2;
        this.mIsReverse = j2 == -1 || (j2 >= j3 && j3 >= 0);
        if (j2 == j3) {
            this.mIsLoop = false;
        }
        if (z2 && (expression = this.mDelay) != null) {
            this.mResetTime = (long) (j + expression.evaluate());
        }
        this.mIsFirstFrame = true;
        this.mIsLastFrame = false;
        this.mIsPaused = false;
        this.mIsFirstReset = true;
        this.mPlayTimeRange = 0L;
    }

    public final void tick(long j) {
        long j2;
        long j3;
        if (this.mIsPaused || this.mDisable) {
            return;
        }
        long j4 = j - this.mResetTime;
        long j5 = 0;
        int i = 0;
        AnimationItem animationItem = null;
        if (j4 < 0) {
            if (this.mIsFirstFrame) {
                this.mIsFirstFrame = false;
                j4 = 0;
            } else {
                onTick(null, null, 0.0f);
                return;
            }
        }
        if (this.mIsFirstReset || (this.mIsLastFrame && !this.mIsTimeInfinite && this.mLoop && this.mIsLoop)) {
            resetTime();
        }
        if ((this.mIsTimeInfinite || !this.mLoop || !this.mIsLoop) && this.mIsLastFrame) {
            this.mIsPaused = true;
            this.mPauseTime = this.mResetTime + this.mPlayTimeRange;
            if (this.mHasName) {
                this.mCurrentFrame.set(this.mEndTime);
            }
            onAction("end");
            return;
        }
        long j6 = this.mPlayTimeRange;
        if (j4 >= j6) {
            this.mResetTime = j - (j4 % (j6 + 1));
            this.mIsLastFrame = true;
            j4 = j6;
        }
        if (this.mIsReverse) {
            j2 = this.mAnimStartTime - j4;
        } else {
            j2 = this.mAnimStartTime + j4;
        }
        long j7 = j2 % (this.mRealTimeRange + 1);
        int size = this.mItems.size();
        AnimationItem animationItem2 = null;
        while (i < size) {
            AnimationItem animationItem3 = this.mItems.get(i);
            if (j7 < animationItem3.mTime) {
                if (i == 0) {
                    j3 = animationItem3.mTime;
                } else {
                    animationItem = this.mItems.get(i - 1);
                    j3 = animationItem3.mTime - animationItem.mTime;
                    j5 = animationItem.mTime;
                }
                long j8 = j3;
                AnimationItem animationItem4 = animationItem;
                onTick(animationItem4, animationItem3, getRatio(animationItem4, j7, j5, j8));
                return;
            }
            i++;
            animationItem2 = animationItem3;
        }
        onTick(null, animationItem2, 1.0f);
    }

    protected void onTick(AnimationItem animationItem, AnimationItem animationItem2, float f) {
        if (animationItem == null && animationItem2 == null) {
            return;
        }
        double defaultValue = getDefaultValue();
        int length = this.mAttrs.length;
        for (int i = 0; i < length; i++) {
            double d = animationItem == null ? defaultValue : animationItem.get(i);
            this.mCurValues[i] = d + ((animationItem2.get(i) - d) * ((double) f));
        }
    }

    public double getCurValue(int i) {
        return this.mCurValues[i];
    }

    public void setCurValue(int i, double d) {
        this.mCurValues[i] = d;
    }

    public void pauseAnim(long j) {
        if (this.mDisable || this.mIsPaused) {
            return;
        }
        this.mIsPaused = true;
        this.mPauseTime = j;
    }

    public void resumeAnim(long j) {
        if (!this.mDisable && this.mIsPaused) {
            this.mIsPaused = false;
            this.mResetTime += j - this.mPauseTime;
        }
    }

    private float getRatio(AnimationItem animationItem, long j, long j2, long j3) {
        float f = j3 == 0 ? 1.0f : (j - j2) / j3;
        return (animationItem == null || animationItem.mInterpolator == null) ? f : animationItem.mInterpolator.get(f);
    }

    private void resetTime() {
        if (this.mIsFirstReset) {
            this.mIsFirstReset = false;
        }
        this.mIsLastFrame = false;
        int size = this.mItems.size();
        for (int i = 0; i < size; i++) {
            this.mItems.get(i).reset();
        }
        reevaluate();
        this.mAnimStartTime = transToAnimTime(this.mStartTime);
        long jTransToAnimTime = transToAnimTime(this.mEndTime);
        this.mAnimEndTime = jTransToAnimTime;
        this.mPlayTimeRange = Math.abs(jTransToAnimTime - this.mAnimStartTime);
    }

    private long transToAnimTime(long j) {
        return (j == -1 || j > this.mRealTimeRange) ? this.mRealTimeRange : j;
    }

    private void reevaluate() {
        int size = this.mItems.size();
        long j = 0;
        for (int i = 0; i < size; i++) {
            AnimationItem animationItem = this.mItems.get(i);
            if (animationItem.mDeltaTimeExp != null) {
                long jEvaluate = (long) animationItem.mDeltaTimeExp.evaluate();
                if (jEvaluate < 0) {
                    jEvaluate = 0;
                }
                j += jEvaluate;
                animationItem.mTime = j;
            } else if (animationItem.mTime >= j) {
                j = animationItem.mTime;
            }
        }
        boolean z = j >= INFINITE_TIME;
        this.mIsTimeInfinite = z;
        if (size > 1 && z) {
            this.mRealTimeRange = this.mItems.get(size - 2).mTime;
        } else {
            this.mRealTimeRange = j;
        }
    }

    public String getTag() {
        return this.mTag;
    }

    public void setDisable(boolean z) {
        this.mDisable = z;
    }
}
