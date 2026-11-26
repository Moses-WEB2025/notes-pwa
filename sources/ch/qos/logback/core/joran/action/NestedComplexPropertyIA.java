package ch.qos.logback.core.joran.action;

import ch.qos.logback.core.joran.spi.ElementPath;
import ch.qos.logback.core.joran.spi.InterpretationContext;
import ch.qos.logback.core.joran.spi.NoAutoStartUtil;
import ch.qos.logback.core.joran.util.PropertySetter;
import ch.qos.logback.core.spi.ContextAware;
import ch.qos.logback.core.spi.LifeCycle;
import ch.qos.logback.core.util.AggregationType;
import ch.qos.logback.core.util.Loader;
import ch.qos.logback.core.util.OptionHelper;
import java.util.Stack;
import org.xml.sax.Attributes;

/* JADX INFO: loaded from: classes.dex */
public class NestedComplexPropertyIA extends ImplicitAction {
    Stack<IADataForComplexProperty> actionDataStack = new Stack<>();

    /* JADX INFO: renamed from: ch.qos.logback.core.joran.action.NestedComplexPropertyIA$1, reason: invalid class name */
    static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$ch$qos$logback$core$util$AggregationType;

        static {
            int[] iArr = new int[AggregationType.values().length];
            $SwitchMap$ch$qos$logback$core$util$AggregationType = iArr;
            try {
                iArr[AggregationType.NOT_FOUND.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$ch$qos$logback$core$util$AggregationType[AggregationType.AS_BASIC_PROPERTY.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                $SwitchMap$ch$qos$logback$core$util$AggregationType[AggregationType.AS_BASIC_PROPERTY_COLLECTION.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                $SwitchMap$ch$qos$logback$core$util$AggregationType[AggregationType.AS_COMPLEX_PROPERTY_COLLECTION.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
            try {
                $SwitchMap$ch$qos$logback$core$util$AggregationType[AggregationType.AS_COMPLEX_PROPERTY.ordinal()] = 5;
            } catch (NoSuchFieldError unused5) {
            }
        }
    }

    @Override // ch.qos.logback.core.joran.action.Action
    public void begin(InterpretationContext interpretationContext, String str, Attributes attributes) {
        IADataForComplexProperty iADataForComplexPropertyPeek = this.actionDataStack.peek();
        String strSubst = interpretationContext.subst(attributes.getValue("class"));
        try {
            Class<?> clsLoadClass = !OptionHelper.isEmpty(strSubst) ? Loader.loadClass(strSubst, this.context) : iADataForComplexPropertyPeek.parentBean.getClassNameViaImplicitRules(iADataForComplexPropertyPeek.getComplexPropertyName(), iADataForComplexPropertyPeek.getAggregationType(), interpretationContext.getDefaultNestedComponentRegistry());
            if (clsLoadClass == null) {
                iADataForComplexPropertyPeek.inError = true;
                addError("Could not find an appropriate class for property [" + str + "]");
                return;
            }
            if (OptionHelper.isEmpty(strSubst)) {
                addInfo("Assuming default type [" + clsLoadClass.getName() + "] for [" + str + "] property");
            }
            iADataForComplexPropertyPeek.setNestedComplexProperty(clsLoadClass.getConstructor(new Class[0]).newInstance(new Object[0]));
            if (iADataForComplexPropertyPeek.getNestedComplexProperty() instanceof ContextAware) {
                ((ContextAware) iADataForComplexPropertyPeek.getNestedComplexProperty()).setContext(this.context);
            }
            interpretationContext.pushObject(iADataForComplexPropertyPeek.getNestedComplexProperty());
        } catch (Exception e) {
            iADataForComplexPropertyPeek.inError = true;
            addError("Could not create component [" + str + "] of type [" + strSubst + "]", e);
        }
    }

    @Override // ch.qos.logback.core.joran.action.Action
    public void end(InterpretationContext interpretationContext, String str) {
        IADataForComplexProperty iADataForComplexPropertyPop = this.actionDataStack.pop();
        if (iADataForComplexPropertyPop.inError) {
            return;
        }
        PropertySetter propertySetter = new PropertySetter(iADataForComplexPropertyPop.getNestedComplexProperty());
        propertySetter.setContext(this.context);
        if (propertySetter.computeAggregationType("parent") == AggregationType.AS_COMPLEX_PROPERTY) {
            propertySetter.setComplexProperty("parent", iADataForComplexPropertyPop.parentBean.getObj());
        }
        Object nestedComplexProperty = iADataForComplexPropertyPop.getNestedComplexProperty();
        if ((nestedComplexProperty instanceof LifeCycle) && NoAutoStartUtil.notMarkedWithNoAutoStart(nestedComplexProperty)) {
            ((LifeCycle) nestedComplexProperty).start();
        }
        if (interpretationContext.peekObject() != iADataForComplexPropertyPop.getNestedComplexProperty()) {
            addError("The object on the top the of the stack is not the component pushed earlier.");
            return;
        }
        interpretationContext.popObject();
        int i = AnonymousClass1.$SwitchMap$ch$qos$logback$core$util$AggregationType[iADataForComplexPropertyPop.aggregationType.ordinal()];
        if (i == 4) {
            iADataForComplexPropertyPop.parentBean.addComplexProperty(str, iADataForComplexPropertyPop.getNestedComplexProperty());
        } else if (i != 5) {
            addError("Unexpected aggregationType " + iADataForComplexPropertyPop.aggregationType);
        } else {
            iADataForComplexPropertyPop.parentBean.setComplexProperty(str, iADataForComplexPropertyPop.getNestedComplexProperty());
        }
    }

    @Override // ch.qos.logback.core.joran.action.ImplicitAction
    public boolean isApplicable(ElementPath elementPath, Attributes attributes, InterpretationContext interpretationContext) {
        String strPeekLast = elementPath.peekLast();
        if (interpretationContext.isEmpty()) {
            return false;
        }
        PropertySetter propertySetter = new PropertySetter(interpretationContext.peekObject());
        propertySetter.setContext(this.context);
        AggregationType aggregationTypeComputeAggregationType = propertySetter.computeAggregationType(strPeekLast);
        int i = AnonymousClass1.$SwitchMap$ch$qos$logback$core$util$AggregationType[aggregationTypeComputeAggregationType.ordinal()];
        if (i == 1 || i == 2 || i == 3) {
            return false;
        }
        if (i != 4 && i != 5) {
            addError("PropertySetter.computeAggregationType returned " + aggregationTypeComputeAggregationType);
            return false;
        }
        this.actionDataStack.push(new IADataForComplexProperty(propertySetter, aggregationTypeComputeAggregationType, strPeekLast));
        return true;
    }
}
