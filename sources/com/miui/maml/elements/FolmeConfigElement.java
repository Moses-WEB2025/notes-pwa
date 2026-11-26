package com.miui.maml.elements;

import androidx.collection.ArraySet;
import com.miui.maml.ScreenElementRoot;
import com.miui.maml.data.Expression;
import com.miui.maml.folme.ConfigValue;
import java.util.ArrayList;
import miuix.animation.utils.EaseManager;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

/* JADX INFO: loaded from: classes3.dex */
public class FolmeConfigElement extends ConfigElement {
    public static final String TAG_NAME = "FolmeConfig";
    private ArrayList<ConfigData> mConfigs;

    private class ConfigData {
        public Expression mDelayExp;
        public Expression[] mEaseExp;
        public Expression mFromSpeedExp;
        public Expression[] mOnBeginCallbackExp;
        public Expression[] mOnCompleteCallbackExp;
        public Expression[] mOnUpdateCallbackExp;
        public Expression[] mPropertyExp;

        private ConfigData() {
        }
    }

    public FolmeConfigElement(Element element, ScreenElementRoot screenElementRoot) {
        super(element, screenElementRoot);
        ArrayList<ConfigData> arrayList = new ArrayList<>();
        this.mConfigs = arrayList;
        arrayList.add(getConfigData(element));
        NodeList childNodes = element.getChildNodes();
        int length = childNodes.getLength();
        for (int i = 0; i < length; i++) {
            if (childNodes.item(i).getNodeType() == 1) {
                Element element2 = (Element) childNodes.item(i);
                if ("Special".equals(element2.getTagName())) {
                    this.mConfigs.add(getConfigData(element2));
                }
            }
        }
    }

    @Override // com.miui.maml.elements.ConfigElement
    protected void evaluateConfigValue() {
        this.mTempValueList.clear();
        int size = this.mConfigs.size();
        for (int i = 0; i < size; i++) {
            ConfigValue configValue = new ConfigValue();
            ConfigData configData = this.mConfigs.get(i);
            if (configData.mDelayExp != null) {
                configValue.mDelay = (long) configData.mDelayExp.evaluate();
            }
            if (configData.mFromSpeedExp != null) {
                configValue.mFromSpeed = (long) configData.mFromSpeedExp.evaluate();
                configValue.mHasFromSpeed = true;
            }
            configValue.mEase = getEaseFromExpressions(configData.mEaseExp);
            getStrFromExpToSet(configData.mPropertyExp, configValue.mRelatedProperty);
            getStrFromExpToSet(configData.mOnBeginCallbackExp, configValue.mOnBeginCallback);
            getStrFromExpToSet(configData.mOnCompleteCallbackExp, configValue.mOnCompleteCallback);
            getStrFromExpToSet(configData.mOnUpdateCallbackExp, configValue.mOnUpdateCallback);
            this.mTempValueList.add(configValue);
        }
    }

    private void getStrFromExpToSet(Expression[] expressionArr, ArraySet<String> arraySet) {
        arraySet.clear();
        if (expressionArr == null || expressionArr.length <= 0) {
            return;
        }
        for (Expression expression : expressionArr) {
            if (expression != null) {
                arraySet.add(expression.evaluateStr());
            }
        }
    }

    private EaseManager.EaseStyle getEaseFromExpressions(Expression[] expressionArr) {
        Expression expression;
        if (expressionArr == null || expressionArr.length <= 0 || (expression = expressionArr[0]) == null) {
            return null;
        }
        int iEvaluate = (int) expression.evaluate();
        float[] fArr = new float[expressionArr.length - 1];
        int length = expressionArr.length;
        for (int i = 1; i < length; i++) {
            Expression expression2 = expressionArr[i];
            if (expression2 != null) {
                fArr[i - 1] = (float) expression2.evaluate();
            } else {
                fArr[i - 1] = 0.0f;
            }
        }
        try {
            return EaseManager.getStyle(iEvaluate, fArr);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private ConfigData getConfigData(Element element) {
        ConfigData configData = new ConfigData();
        configData.mEaseExp = Expression.buildMultiple(getVariables(), element.getAttribute("ease"));
        configData.mFromSpeedExp = Expression.build(getVariables(), element.getAttribute("fromSpeed"));
        configData.mDelayExp = Expression.build(getVariables(), element.getAttribute("delay"));
        configData.mOnBeginCallbackExp = Expression.buildMultiple(getVariables(), element.getAttribute("onBegin"));
        configData.mOnCompleteCallbackExp = Expression.buildMultiple(getVariables(), element.getAttribute("onComplete"));
        configData.mOnUpdateCallbackExp = Expression.buildMultiple(getVariables(), element.getAttribute("onUpdate"));
        configData.mPropertyExp = Expression.buildMultiple(getVariables(), element.getAttribute("property"));
        return configData;
    }
}
