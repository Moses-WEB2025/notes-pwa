package com.miui.maml.data;

import android.text.TextUtils;
import androidx.constraintlayout.core.motion.utils.TypedValues;
import com.miui.doodle.feature.draw.DoodleBitmap;
import com.miui.maml.CommandTrigger;
import com.miui.maml.ScreenContext;
import com.miui.maml.ScreenElementRoot;
import com.miui.maml.util.Utils;
import java.util.ArrayList;
import org.w3c.dom.Element;

/* JADX INFO: loaded from: classes3.dex */
public abstract class VariableBinder {
    private String mDependency;
    protected boolean mFinished;
    protected String mName;
    protected boolean mPaused;
    protected boolean mQueryAtStart;
    private ContentProviderBinder.QueryCompleteListener mQueryCompletedListener;
    protected ScreenElementRoot mRoot;
    protected CommandTrigger mTrigger;
    protected ArrayList<Variable> mVariables = new ArrayList<>();

    protected Variable onLoadVariable(Element element) {
        return null;
    }

    public void refresh() {
    }

    public void startQuery() {
    }

    public void tick() {
    }

    public void init() {
        this.mFinished = false;
        this.mPaused = false;
        CommandTrigger commandTrigger = this.mTrigger;
        if (commandTrigger != null) {
            commandTrigger.init();
        }
        if (TextUtils.isEmpty(getDependency()) && this.mQueryAtStart) {
            startQuery();
        }
    }

    public void finish() {
        CommandTrigger commandTrigger = this.mTrigger;
        if (commandTrigger != null) {
            commandTrigger.finish();
        }
        this.mFinished = true;
    }

    public void pause() {
        CommandTrigger commandTrigger = this.mTrigger;
        if (commandTrigger != null) {
            commandTrigger.pause();
        }
        this.mPaused = true;
    }

    public void resume() {
        CommandTrigger commandTrigger = this.mTrigger;
        if (commandTrigger != null) {
            commandTrigger.resume();
        }
        this.mPaused = false;
    }

    public String getName() {
        return this.mName;
    }

    public String getDependency() {
        return this.mDependency;
    }

    public VariableBinder(Element element, ScreenElementRoot screenElementRoot) {
        this.mQueryAtStart = true;
        this.mRoot = screenElementRoot;
        if (element != null) {
            this.mName = element.getAttribute("name");
            this.mDependency = element.getAttribute("dependency");
            this.mQueryAtStart = !"false".equalsIgnoreCase(element.getAttribute("queryAtStart"));
            this.mTrigger = CommandTrigger.fromParentElement(element, this.mRoot);
        }
    }

    public final void accept(VariableBinderVisitor variableBinderVisitor) {
        variableBinderVisitor.visit(this);
    }

    protected ScreenContext getContext() {
        return this.mRoot.getContext();
    }

    public Variables getVariables() {
        return this.mRoot.getVariables();
    }

    public static class TypedValue {
        public static final int BITMAP = 7;
        public static final int DOUBLE = 6;
        public static final int FLOAT = 5;
        public static final int INT = 3;
        public static final int LONG = 4;
        public static final int NUM_ARR = 8;
        public static final int STRING = 2;
        public static final int STR_ARR = 9;
        public static final int TYPE_BASE = 1000;
        public String mName;
        public int mType;
        public String mTypeStr;

        public TypedValue(String str, String str2) {
            initInner(str, str2);
        }

        public TypedValue(Element element) {
            if (element == null) {
                throw new NullPointerException("node is null");
            }
            initInner(element.getAttribute("name"), element.getAttribute("type"));
        }

        private void initInner(String str, String str2) {
            this.mName = str;
            this.mTypeStr = str2;
            this.mType = parseType(str2);
        }

        protected int parseType(String str) {
            if (TypedValues.Custom.S_STRING.equalsIgnoreCase(str)) {
                return 2;
            }
            if ("double".equalsIgnoreCase(str) || "number".equalsIgnoreCase(str)) {
                return 6;
            }
            if (TypedValues.Custom.S_FLOAT.equalsIgnoreCase(str)) {
                return 5;
            }
            if ("int".equalsIgnoreCase(str) || TypedValues.Custom.S_INT.equalsIgnoreCase(str)) {
                return 3;
            }
            if ("long".equalsIgnoreCase(str)) {
                return 4;
            }
            if (DoodleBitmap.KEY_BITMAP.equalsIgnoreCase(str)) {
                return 7;
            }
            if ("number[]".equalsIgnoreCase(str)) {
                return 8;
            }
            return "string[]".equalsIgnoreCase(str) ? 9 : 6;
        }

        public boolean isNumber() {
            int i = this.mType;
            return i >= 3 && i <= 6;
        }

        public boolean isArray() {
            int i = this.mType;
            return i == 8 || i == 9;
        }
    }

    public static class Variable extends TypedValue {
        public static final String TAG_NAME = "Variable";
        private Expression mArrayIndex;
        protected double mDefNumberValue;
        protected String mDefStringValue;
        protected IndexedVariable mVar;

        public Variable(String str, String str2, Variables variables) {
            super(str, str2);
            this.mVar = new IndexedVariable(this.mName, variables, isNumber());
        }

        public Variable(Element element, Variables variables) {
            super(element);
            this.mArrayIndex = Expression.build(variables, element.getAttribute("arrIndex"));
            this.mVar = new IndexedVariable(this.mName, variables, isNumber() && this.mArrayIndex == null);
            this.mDefStringValue = element.getAttribute("default");
            if (isNumber()) {
                if (TextUtils.isEmpty(this.mDefStringValue)) {
                    this.mDefStringValue = null;
                    this.mDefNumberValue = 0.0d;
                } else {
                    try {
                        this.mDefNumberValue = Double.parseDouble(this.mDefStringValue);
                    } catch (NumberFormatException unused) {
                        this.mDefStringValue = null;
                        this.mDefNumberValue = 0.0d;
                    }
                }
            }
        }

        public void set(double d) {
            Expression expression = this.mArrayIndex;
            if (expression != null) {
                this.mVar.setArr((int) expression.evaluate(), d);
            } else {
                this.mVar.set(d);
            }
        }

        public void set(Object obj) {
            double dDoubleValue;
            if (isNumber()) {
                if ((obj instanceof String) && !TextUtils.isEmpty((String) obj)) {
                    try {
                        dDoubleValue = Utils.parseDouble((String) obj);
                    } catch (NumberFormatException unused) {
                    }
                } else {
                    dDoubleValue = obj instanceof Number ? ((Number) obj).doubleValue() : 0.0d;
                }
                Expression expression = this.mArrayIndex;
                if (expression != null) {
                    this.mVar.setArr((int) expression.evaluate(), dDoubleValue);
                    return;
                } else {
                    this.mVar.set(dDoubleValue);
                    return;
                }
            }
            if (obj instanceof Number) {
                obj = Utils.numberToString((Number) obj);
            }
            Expression expression2 = this.mArrayIndex;
            if (expression2 != null) {
                this.mVar.setArr((int) expression2.evaluate(), obj);
            } else {
                this.mVar.set(obj);
            }
        }

        public double getNumber() {
            if (isNumber()) {
                Expression expression = this.mArrayIndex;
                if (expression != null) {
                    return this.mVar.getArrDouble((int) expression.evaluate());
                }
                return this.mVar.getDouble();
            }
            Expression expression2 = this.mArrayIndex;
            if (expression2 != null) {
                return Utils.stringToDouble(this.mVar.getArrString((int) expression2.evaluate()), 0.0d);
            }
            return Utils.stringToDouble(this.mVar.getString(), 0.0d);
        }
    }

    public void setQueryCompleteListener(ContentProviderBinder.QueryCompleteListener queryCompleteListener) {
        this.mQueryCompletedListener = queryCompleteListener;
    }

    protected final void onUpdateComplete() {
        CommandTrigger commandTrigger = this.mTrigger;
        if (commandTrigger != null) {
            commandTrigger.perform();
        }
        if (this.mQueryCompletedListener != null && !TextUtils.isEmpty(this.mName)) {
            this.mQueryCompletedListener.onQueryCompleted(this.mName);
        }
        this.mRoot.requestUpdate();
    }

    protected void addVariable(Variable variable) {
        this.mVariables.add(variable);
    }

    protected void loadVariables(Element element) {
        Utils.traverseXmlElementChildren(element, Variable.TAG_NAME, new Utils.XmlTraverseListener() { // from class: com.miui.maml.data.VariableBinder.1
            @Override // com.miui.maml.util.Utils.XmlTraverseListener
            public void onChild(Element element2) {
                Variable variableOnLoadVariable = VariableBinder.this.onLoadVariable(element2);
                if (variableOnLoadVariable != null) {
                    VariableBinder.this.mVariables.add(variableOnLoadVariable);
                }
            }
        });
    }
}
