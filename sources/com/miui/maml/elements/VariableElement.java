package com.miui.maml.elements;

import android.graphics.Canvas;
import android.util.Log;
import com.miui.maml.CommandTrigger;
import com.miui.maml.ScreenElementRoot;
import com.miui.maml.animation.BaseAnimation;
import com.miui.maml.animation.VariableAnimation;
import com.miui.maml.data.Expression;
import com.miui.maml.data.IndexedVariable;
import com.miui.maml.data.VariableType;
import com.miui.maml.data.Variables;
import com.miui.maml.util.Utils;
import org.apache.commons.text.lookup.StringLookupFactory;
import org.w3c.dom.Element;

/* JADX INFO: loaded from: classes3.dex */
public class VariableElement extends ScreenElement {
    public static final String LENGTH_SUFFIX = ".length";
    private static final String LOG_TAG = "VariableElement";
    private static final String OLD_VALUE = "old_value";
    public static final String TAG_NAME = "Var";
    private VariableAnimation mAnimation;
    private int mArraySize;
    private Expression[] mArrayValues;
    private boolean mConst;
    private Expression mExpression;
    private Expression mIndexExpression;
    private boolean mInited;
    private IndexedVariable mLengthVar;
    private double mOldValue;
    private IndexedVariable mOldVar;
    private double mThreshold;
    private CommandTrigger mTrigger;
    private VariableType mType;
    private IndexedVariable mVar;

    @Override // com.miui.maml.elements.ScreenElement
    protected void doRender(Canvas canvas) {
    }

    public VariableElement(Element element, ScreenElementRoot screenElementRoot) {
        super(element, screenElementRoot);
        if (element != null) {
            this.mExpression = Expression.build(getVariables(), element.getAttribute("expression"));
            this.mIndexExpression = Expression.build(getVariables(), element.getAttribute("index"));
            this.mThreshold = Math.abs(Utils.getAttrAsFloat(element, "threshold", 1.0f));
            this.mType = VariableType.parseType(element.getAttribute("type"));
            this.mConst = Boolean.parseBoolean(element.getAttribute(StringLookupFactory.KEY_CONST));
            this.mArraySize = Utils.getAttrAsInt(element, "size", 0);
            Variables variables = getVariables();
            this.mVar = new IndexedVariable(this.mName, variables, this.mType.isNumber());
            this.mOldVar = new IndexedVariable(this.mName + ".old_value", variables, this.mType.isNumber());
            this.mTrigger = CommandTrigger.fromParentElement(element, screenElementRoot);
            if (this.mType.isArray()) {
                Expression[] expressionArrBuildMultiple = Expression.buildMultiple(variables, element.getAttribute("values"));
                this.mArrayValues = expressionArrBuildMultiple;
                if (expressionArrBuildMultiple != null) {
                    this.mArraySize = expressionArrBuildMultiple.length;
                }
                if (this.mArraySize > 0) {
                    if (!variables.createArray(this.mName, this.mArraySize, this.mType.mTypeClass)) {
                        Log.e(LOG_TAG, "fail to create array:" + this.mName);
                        this.mArraySize = 0;
                    }
                } else {
                    Log.e(LOG_TAG, "array size is 0:" + this.mName);
                }
                IndexedVariable indexedVariable = new IndexedVariable(this.mName + LENGTH_SUFFIX, variables, true);
                this.mLengthVar = indexedVariable;
                indexedVariable.set(this.mArraySize);
            }
        }
    }

    @Override // com.miui.maml.elements.ScreenElement
    protected BaseAnimation onCreateAnimation(String str, Element element) {
        if (VariableAnimation.TAG_NAME.equals(str)) {
            VariableAnimation variableAnimation = new VariableAnimation(element, this);
            this.mAnimation = variableAnimation;
            return variableAnimation;
        }
        return super.onCreateAnimation(str, element);
    }

    @Override // com.miui.maml.elements.ScreenElement
    protected void doTick(long j) {
        if (this.mConst) {
            return;
        }
        super.doTick(j);
        update();
    }

    @Override // com.miui.maml.elements.ScreenElement
    public void init() {
        super.init();
        CommandTrigger commandTrigger = this.mTrigger;
        if (commandTrigger != null) {
            commandTrigger.init();
        }
        update();
        this.mInited = true;
    }

    @Override // com.miui.maml.elements.ScreenElement
    protected void onSetAnimBefore() {
        this.mAnimation = null;
    }

    @Override // com.miui.maml.elements.ScreenElement
    protected void onSetAnimEnable(BaseAnimation baseAnimation) {
        if (baseAnimation instanceof VariableAnimation) {
            this.mAnimation = (VariableAnimation) baseAnimation;
        }
    }

    @Override // com.miui.maml.elements.ScreenElement
    protected void playAnim(long j, long j2, long j3, boolean z, boolean z2) {
        super.playAnim(j, j2, j3, z, z2);
        update();
    }

    @Override // com.miui.maml.elements.ScreenElement
    protected void pauseAnim(long j) {
        super.pauseAnim(j);
        update();
    }

    @Override // com.miui.maml.elements.ScreenElement
    protected void resumeAnim(long j) {
        super.resumeAnim(j);
        update();
    }

    @Override // com.miui.maml.elements.ScreenElement
    public void reset(long j) {
        super.reset(j);
        update();
    }

    @Override // com.miui.maml.elements.ScreenElement
    public void pause() {
        super.pause();
        CommandTrigger commandTrigger = this.mTrigger;
        if (commandTrigger != null) {
            commandTrigger.pause();
        }
    }

    @Override // com.miui.maml.elements.ScreenElement
    public void resume() {
        super.resume();
        CommandTrigger commandTrigger = this.mTrigger;
        if (commandTrigger != null) {
            commandTrigger.resume();
        }
    }

    @Override // com.miui.maml.elements.ScreenElement
    public void finish() {
        super.finish();
        CommandTrigger commandTrigger = this.mTrigger;
        if (commandTrigger != null) {
            commandTrigger.finish();
        }
        this.mInited = false;
    }

    /* JADX INFO: renamed from: com.miui.maml.elements.VariableElement$1, reason: invalid class name */
    static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$com$miui$maml$data$VariableType;

        static {
            int[] iArr = new int[VariableType.values().length];
            $SwitchMap$com$miui$maml$data$VariableType = iArr;
            try {
                iArr[VariableType.STR.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$com$miui$maml$data$VariableType[VariableType.STR_ARR.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                $SwitchMap$com$miui$maml$data$VariableType[VariableType.NUM.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
        }
    }

    private void update() {
        Expression expression;
        int i = AnonymousClass1.$SwitchMap$com$miui$maml$data$VariableType[this.mType.ordinal()];
        if (i == 1) {
            Expression expression2 = this.mExpression;
            if (expression2 != null) {
                String strEvaluateStr = expression2.evaluateStr();
                String string = this.mVar.getString();
                if (Utils.equals(strEvaluateStr, string)) {
                    return;
                }
                this.mOldVar.set(string);
                this.mVar.set(strEvaluateStr);
                CommandTrigger commandTrigger = this.mTrigger;
                if (commandTrigger != null) {
                    commandTrigger.perform();
                    return;
                }
                return;
            }
            return;
        }
        int i2 = 0;
        if (i != 2) {
            if (i == 3) {
                double d = getDouble(false, 0);
                this.mVar.set(d);
                onValueChange(d);
                return;
            }
            if (this.mType.isNumberArray()) {
                Expression expression3 = this.mIndexExpression;
                if (expression3 != null) {
                    int iEvaluate = (int) expression3.evaluate();
                    double d2 = getDouble(true, iEvaluate);
                    this.mVar.setArr(iEvaluate, d2);
                    onValueChange(d2);
                    return;
                }
                Expression[] expressionArr = this.mArrayValues;
                if (expressionArr != null) {
                    int length = expressionArr.length;
                    Object obj = this.mVar.get();
                    while (i2 < length) {
                        Expression expression4 = this.mArrayValues[i2];
                        Variables.putValueToArr(obj, i2, expression4 == null ? 0.0d : expression4.evaluate());
                        i2++;
                    }
                    this.mVar.set(obj);
                    return;
                }
                return;
            }
            return;
        }
        if (this.mIndexExpression != null && (expression = this.mExpression) != null) {
            String strEvaluateStr2 = expression.evaluateStr();
            int iEvaluate2 = (int) this.mIndexExpression.evaluate();
            String arrString = this.mVar.getArrString(iEvaluate2);
            if (Utils.equals(strEvaluateStr2, arrString)) {
                return;
            }
            this.mOldVar.set(arrString);
            this.mVar.setArr(iEvaluate2, strEvaluateStr2);
            CommandTrigger commandTrigger2 = this.mTrigger;
            if (commandTrigger2 != null) {
                commandTrigger2.perform();
                return;
            }
            return;
        }
        if (this.mArrayValues != null) {
            Object obj2 = this.mVar.get();
            if (obj2 instanceof String[]) {
                String[] strArr = (String[]) obj2;
                int iMin = Math.min(this.mArrayValues.length, strArr.length);
                while (i2 < iMin) {
                    Expression expression5 = this.mArrayValues[i2];
                    strArr[i2] = expression5 == null ? null : expression5.evaluateStr();
                    i2++;
                }
                this.mVar.set(strArr);
            }
        }
    }

    private double getDouble(boolean z, int i) {
        VariableAnimation variableAnimation = this.mAnimation;
        if (variableAnimation != null) {
            return variableAnimation.getValue();
        }
        Expression expression = this.mExpression;
        if (expression != null) {
            return expression.evaluate();
        }
        IndexedVariable indexedVariable = this.mVar;
        return z ? indexedVariable.getArrDouble(i) : indexedVariable.getDouble();
    }

    private void onValueChange(double d) {
        if (!this.mInited) {
            this.mOldValue = d;
        }
        if (this.mTrigger == null || Math.abs(d - this.mOldValue) < this.mThreshold) {
            return;
        }
        this.mOldVar.set(this.mOldValue);
        this.mOldValue = d;
        this.mTrigger.perform();
    }
}
