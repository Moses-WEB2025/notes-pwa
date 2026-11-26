package com.miui.maml.elements;

import android.text.TextUtils;
import androidx.collection.ArraySet;
import com.miui.maml.ScreenElementRoot;
import com.miui.maml.data.Expression;
import com.miui.maml.data.IndexedVariable;
import com.miui.maml.data.Variables;
import com.miui.maml.folme.ConfigValue;
import java.util.concurrent.ConcurrentHashMap;
import miuix.animation.utils.EaseManager;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.NodeList;

/* JADX INFO: loaded from: classes3.dex */
public class AnimConfigElement extends ConfigElement {
    public static final String TAG_NAME = "AnimConfig";
    private ConcurrentHashMap<String, ConfigData> mConfigs;

    private class ConfigData {
        public IndexedVariable mDelay;
        public IndexedVariable mEase;
        public IndexedVariable mEaseLen;
        public IndexedVariable mFromSpeed;
        public IndexedVariable mOnBeginCallback;
        public IndexedVariable mOnBeginCallbackLen;
        public IndexedVariable mOnCompleteCallback;
        public IndexedVariable mOnCompleteCallbackLen;
        public IndexedVariable mOnUpdateCallback;
        public IndexedVariable mOnUpdateCallbackLen;
        public IndexedVariable mProperty;
        public IndexedVariable mPropertyLen;

        private ConfigData() {
        }
    }

    public AnimConfigElement(Element element, ScreenElementRoot screenElementRoot) {
        super(element, screenElementRoot);
        this.mConfigs = new ConcurrentHashMap<>();
        if (this.mHasName) {
            this.mConfigs.put(this.mName, getConfigData(element, this.mName));
            NodeList childNodes = element.getChildNodes();
            int length = childNodes.getLength();
            for (int i = 0; i < length; i++) {
                if (childNodes.item(i).getNodeType() == 1) {
                    Element element2 = (Element) childNodes.item(i);
                    if ("Special".equals(element2.getTagName())) {
                        String attribute = element2.getAttribute("name");
                        if (!TextUtils.isEmpty(attribute)) {
                            String str = this.mName + "." + attribute;
                            this.mConfigs.put(str, getConfigData(element2, str));
                        }
                    }
                }
            }
        }
    }

    public void updateConfigData(String str, String str2, Expression[] expressionArr) {
        ConfigData configData = this.mConfigs.get(str);
        if (configData == null) {
            configData = new ConfigData();
            this.mConfigs.put(str, configData);
        }
        updateConfigIndexVariable(configData, str, str2, expressionArr);
    }

    public void removeConfigData(String str, String str2) {
        ConfigData configData = this.mConfigs.get(str);
        if (configData != null) {
            removeConfigIndexVariable(configData, str2);
        }
    }

    public void clearConfigData() {
        for (ConfigData configData : this.mConfigs.values()) {
            removeConfigIndexVariable(configData, "fromSpeed");
            removeConfigIndexVariable(configData, "delay");
            removeConfigIndexVariable(configData, "ease");
            removeConfigIndexVariable(configData, "property");
            removeConfigIndexVariable(configData, "onUpdate");
            removeConfigIndexVariable(configData, "onBegin");
            removeConfigIndexVariable(configData, "onComplete");
        }
        this.mConfigs.clear();
    }

    @Override // com.miui.maml.elements.ConfigElement
    protected void evaluateConfigValue() {
        this.mTempValueList.clear();
        for (ConfigData configData : this.mConfigs.values()) {
            ConfigValue configValue = new ConfigValue();
            if (configData.mDelay != null) {
                configValue.mDelay = (long) configData.mDelay.getDouble();
            }
            if (configData.mFromSpeed != null) {
                configValue.mFromSpeed = (long) configData.mFromSpeed.getDouble();
                configValue.mHasFromSpeed = true;
            }
            if (configData.mEase != null) {
                configValue.mEase = getEase(configData.mEase);
            }
            if (configData.mProperty != null) {
                getConfigValueFromVarToSet(configData.mProperty, configValue.mRelatedProperty);
            }
            if (configData.mOnBeginCallback != null) {
                getConfigValueFromVarToSet(configData.mOnBeginCallback, configValue.mOnBeginCallback);
            }
            if (configData.mOnCompleteCallback != null) {
                getConfigValueFromVarToSet(configData.mOnCompleteCallback, configValue.mOnCompleteCallback);
            }
            if (configData.mOnUpdateCallback != null) {
                getConfigValueFromVarToSet(configData.mOnUpdateCallback, configValue.mOnUpdateCallback);
            }
            this.mTempValueList.add(configValue);
        }
    }

    private EaseManager.EaseStyle getEase(IndexedVariable indexedVariable) {
        Object obj = indexedVariable != null ? indexedVariable.get() : null;
        if (obj != null && (obj instanceof double[])) {
            double[] dArr = (double[]) obj;
            if (dArr.length > 0) {
                int i = (int) dArr[0];
                float[] fArr = new float[dArr.length - 1];
                int length = dArr.length;
                for (int i2 = 1; i2 < length; i2++) {
                    fArr[i2 - 1] = (float) dArr[i2];
                }
                try {
                    return EaseManager.getStyle(i, fArr);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }

    private void getConfigValueFromVarToSet(IndexedVariable indexedVariable, ArraySet<String> arraySet) {
        Object obj = indexedVariable != null ? indexedVariable.get() : null;
        if (obj == null || !(obj instanceof String[])) {
            return;
        }
        for (String str : (String[]) obj) {
            arraySet.add(str);
        }
    }

    private ConfigData getConfigData(Element element, String str) {
        Variables variables = getVariables();
        ConfigData configData = new ConfigData();
        NamedNodeMap attributes = element.getAttributes();
        int length = attributes.getLength();
        for (int i = 0; i < length; i++) {
            String nodeName = attributes.item(i).getNodeName();
            Expression[] expressionArrBuildMultiple = Expression.buildMultiple(variables, element.getAttribute(nodeName));
            if (expressionArrBuildMultiple != null) {
                updateConfigIndexVariable(configData, str, nodeName, expressionArrBuildMultiple);
            }
        }
        return configData;
    }

    /* JADX WARN: Code duplicated, block: B:100:0x01b6  */
    /* JADX WARN: Code duplicated, block: B:104:0x019d A[SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:107:0x01ae A[SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:83:0x0180 A[DONT_INVERT] */
    /* JADX WARN: Code duplicated, block: B:84:0x0182  */
    /* JADX WARN: Code duplicated, block: B:85:0x018d A[DONT_INVERT] */
    /* JADX WARN: Code duplicated, block: B:86:0x018f  */
    /* JADX WARN: Code duplicated, block: B:88:0x0193  */
    /* JADX WARN: Code duplicated, block: B:90:0x0197  */
    /* JADX WARN: Code duplicated, block: B:92:0x01a0  */
    /* JADX WARN: Code duplicated, block: B:94:0x01a4  */
    /* JADX WARN: Code duplicated, block: B:96:0x01a8  */
    /* JADX WARN: Failed to restore switch over string. Please report as a decompilation issue */
    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r6v4, types: [java.lang.String[]] */
    /* JADX WARN: Type inference failed for: r6v5, types: [double[]] */
    /* JADX WARN: Type inference failed for: r6v8, types: [java.lang.Double] */
    private void updateConfigIndexVariable(ConfigData configData, String str, String str2, Expression[] expressionArr) {
        boolean z;
        IndexedVariable indexedVariable;
        IndexedVariable indexedVariable2;
        int length;
        Object objValueOf;
        Expression expression;
        Expression expression2;
        IndexedVariable indexedVariable3;
        String str3 = str + "." + str2;
        Variables variables = getVariables();
        str2.hashCode();
        boolean z2 = true;
        int i = 0;
        byte b = -1;
        switch (str2.hashCode()) {
            case -2124458952:
                if (str2.equals("onComplete")) {
                    b = 0;
                }
                break;
            case -1353036278:
                if (str2.equals("onBegin")) {
                    b = 1;
                }
                break;
            case -993141291:
                if (str2.equals("property")) {
                    b = 2;
                }
                break;
            case 3105774:
                if (str2.equals("ease")) {
                    b = 3;
                }
                break;
            case 69481149:
                if (str2.equals("fromSpeed")) {
                    b = 4;
                }
                break;
            case 95467907:
                if (str2.equals("delay")) {
                    b = 5;
                }
                break;
            case 1559564168:
                if (str2.equals("onUpdate")) {
                    b = 6;
                }
                break;
        }
        IndexedVariable indexedVariable4 = null;
        switch (b) {
            case 0:
                if (configData.mOnCompleteCallback == null) {
                    configData.mOnCompleteCallback = new IndexedVariable(str3, variables, false);
                }
                if (configData.mOnCompleteCallbackLen == null) {
                    configData.mOnCompleteCallbackLen = new IndexedVariable(str3 + VariableElement.LENGTH_SUFFIX, variables, true);
                }
                IndexedVariable indexedVariable5 = configData.mOnCompleteCallback;
                indexedVariable4 = configData.mOnCompleteCallbackLen;
                indexedVariable3 = indexedVariable5;
                z = false;
                z2 = false;
                indexedVariable2 = indexedVariable3;
                length = expressionArr.length;
                if (length > 0) {
                    if (z2) {
                        objValueOf = Double.valueOf(expressionArr[0].evaluate());
                    } else if (z) {
                        objValueOf = new double[length];
                        while (i < length) {
                            expression2 = expressionArr[i];
                            if (expression2 != null) {
                                objValueOf[i] = expression2.evaluate();
                            }
                            i++;
                        }
                    } else {
                        objValueOf = new String[length];
                        while (i < length) {
                            expression = expressionArr[i];
                            if (expression != null) {
                                objValueOf[i] = expression.evaluateStr();
                            }
                            i++;
                        }
                    }
                    indexedVariable2.set(objValueOf);
                    if (indexedVariable4 != null) {
                        indexedVariable4.set(length);
                    }
                }
                break;
            case 1:
                if (configData.mOnBeginCallback == null) {
                    configData.mOnBeginCallback = new IndexedVariable(str3, variables, false);
                }
                if (configData.mOnBeginCallbackLen == null) {
                    configData.mOnBeginCallbackLen = new IndexedVariable(str3 + VariableElement.LENGTH_SUFFIX, variables, true);
                }
                IndexedVariable indexedVariable6 = configData.mOnBeginCallback;
                indexedVariable4 = configData.mOnBeginCallbackLen;
                indexedVariable3 = indexedVariable6;
                z = false;
                z2 = false;
                indexedVariable2 = indexedVariable3;
                length = expressionArr.length;
                if (length > 0) {
                    if (z2) {
                        objValueOf = Double.valueOf(expressionArr[0].evaluate());
                    } else if (z) {
                        objValueOf = new double[length];
                        while (i < length) {
                            expression2 = expressionArr[i];
                            if (expression2 != null) {
                                objValueOf[i] = expression2.evaluate();
                            }
                            i++;
                        }
                    } else {
                        objValueOf = new String[length];
                        while (i < length) {
                            expression = expressionArr[i];
                            if (expression != null) {
                                objValueOf[i] = expression.evaluateStr();
                            }
                            i++;
                        }
                    }
                    indexedVariable2.set(objValueOf);
                    if (indexedVariable4 != null) {
                        indexedVariable4.set(length);
                    }
                }
                break;
            case 2:
                if (configData.mProperty == null) {
                    configData.mProperty = new IndexedVariable(str3, variables, false);
                }
                if (configData.mPropertyLen == null) {
                    configData.mPropertyLen = new IndexedVariable(str3 + VariableElement.LENGTH_SUFFIX, variables, true);
                }
                IndexedVariable indexedVariable7 = configData.mProperty;
                indexedVariable4 = configData.mPropertyLen;
                indexedVariable3 = indexedVariable7;
                z = false;
                z2 = false;
                indexedVariable2 = indexedVariable3;
                length = expressionArr.length;
                if (length > 0) {
                    if (z2) {
                        objValueOf = Double.valueOf(expressionArr[0].evaluate());
                    } else if (z) {
                        objValueOf = new double[length];
                        while (i < length) {
                            expression2 = expressionArr[i];
                            if (expression2 != null) {
                                objValueOf[i] = expression2.evaluate();
                            }
                            i++;
                        }
                    } else {
                        objValueOf = new String[length];
                        while (i < length) {
                            expression = expressionArr[i];
                            if (expression != null) {
                                objValueOf[i] = expression.evaluateStr();
                            }
                            i++;
                        }
                    }
                    indexedVariable2.set(objValueOf);
                    if (indexedVariable4 != null) {
                        indexedVariable4.set(length);
                    }
                }
                break;
            case 3:
                if (configData.mEase == null) {
                    configData.mEase = new IndexedVariable(str3, variables, false);
                }
                if (configData.mEaseLen == null) {
                    configData.mEaseLen = new IndexedVariable(str3 + VariableElement.LENGTH_SUFFIX, variables, true);
                }
                IndexedVariable indexedVariable8 = configData.mEase;
                indexedVariable4 = configData.mEaseLen;
                z = true;
                z2 = false;
                indexedVariable2 = indexedVariable8;
                length = expressionArr.length;
                if (length > 0) {
                    if (z2) {
                        objValueOf = Double.valueOf(expressionArr[0].evaluate());
                    } else if (z) {
                        objValueOf = new double[length];
                        while (i < length) {
                            expression2 = expressionArr[i];
                            if (expression2 != null) {
                                objValueOf[i] = expression2.evaluate();
                            }
                            i++;
                        }
                    } else {
                        objValueOf = new String[length];
                        while (i < length) {
                            expression = expressionArr[i];
                            if (expression != null) {
                                objValueOf[i] = expression.evaluateStr();
                            }
                            i++;
                        }
                    }
                    indexedVariable2.set(objValueOf);
                    if (indexedVariable4 != null) {
                        indexedVariable4.set(length);
                    }
                }
                break;
            case 4:
                if (configData.mFromSpeed == null) {
                    configData.mFromSpeed = new IndexedVariable(str3, variables, true);
                }
                indexedVariable = configData.mFromSpeed;
                z = false;
                indexedVariable2 = indexedVariable;
                length = expressionArr.length;
                if (length > 0) {
                    if (z2) {
                        objValueOf = Double.valueOf(expressionArr[0].evaluate());
                    } else if (z) {
                        objValueOf = new double[length];
                        while (i < length) {
                            expression2 = expressionArr[i];
                            if (expression2 != null) {
                                objValueOf[i] = expression2.evaluate();
                            }
                            i++;
                        }
                    } else {
                        objValueOf = new String[length];
                        while (i < length) {
                            expression = expressionArr[i];
                            if (expression != null) {
                                objValueOf[i] = expression.evaluateStr();
                            }
                            i++;
                        }
                    }
                    indexedVariable2.set(objValueOf);
                    if (indexedVariable4 != null) {
                        indexedVariable4.set(length);
                    }
                }
                break;
            case 5:
                if (configData.mDelay == null) {
                    configData.mDelay = new IndexedVariable(str3, variables, true);
                }
                indexedVariable = configData.mDelay;
                z = false;
                indexedVariable2 = indexedVariable;
                length = expressionArr.length;
                if (length > 0) {
                    if (z2) {
                        objValueOf = Double.valueOf(expressionArr[0].evaluate());
                    } else if (z) {
                        objValueOf = new double[length];
                        while (i < length) {
                            expression2 = expressionArr[i];
                            if (expression2 != null) {
                                objValueOf[i] = expression2.evaluate();
                            }
                            i++;
                        }
                    } else {
                        objValueOf = new String[length];
                        while (i < length) {
                            expression = expressionArr[i];
                            if (expression != null) {
                                objValueOf[i] = expression.evaluateStr();
                            }
                            i++;
                        }
                    }
                    indexedVariable2.set(objValueOf);
                    if (indexedVariable4 != null) {
                        indexedVariable4.set(length);
                    }
                }
                break;
            case 6:
                if (configData.mOnUpdateCallback == null) {
                    configData.mOnUpdateCallback = new IndexedVariable(str3, variables, false);
                }
                if (configData.mOnUpdateCallbackLen == null) {
                    configData.mOnUpdateCallbackLen = new IndexedVariable(str3 + VariableElement.LENGTH_SUFFIX, variables, true);
                }
                IndexedVariable indexedVariable9 = configData.mOnUpdateCallback;
                indexedVariable4 = configData.mOnUpdateCallbackLen;
                indexedVariable3 = indexedVariable9;
                z = false;
                z2 = false;
                indexedVariable2 = indexedVariable3;
                length = expressionArr.length;
                if (length > 0) {
                    if (z2) {
                        objValueOf = Double.valueOf(expressionArr[0].evaluate());
                    } else if (z) {
                        objValueOf = new double[length];
                        while (i < length) {
                            expression2 = expressionArr[i];
                            if (expression2 != null) {
                                objValueOf[i] = expression2.evaluate();
                            }
                            i++;
                        }
                    } else {
                        objValueOf = new String[length];
                        while (i < length) {
                            expression = expressionArr[i];
                            if (expression != null) {
                                objValueOf[i] = expression.evaluateStr();
                            }
                            i++;
                        }
                    }
                    indexedVariable2.set(objValueOf);
                    if (indexedVariable4 != null) {
                        indexedVariable4.set(length);
                    }
                }
                break;
        }
    }

    private void removeConfigIndexVariable(ConfigData configData, String str) {
        str.hashCode();
        switch (str) {
            case "onComplete":
                if (configData.mOnCompleteCallback != null) {
                    configData.mOnCompleteCallback.set((Object) null);
                    configData.mOnCompleteCallback = null;
                }
                if (configData.mOnCompleteCallbackLen != null) {
                    configData.mOnCompleteCallbackLen.set(0.0d);
                    configData.mOnCompleteCallbackLen = null;
                    break;
                }
                break;
            case "onBegin":
                if (configData.mOnBeginCallback != null) {
                    configData.mOnBeginCallback.set((Object) null);
                    configData.mOnBeginCallback = null;
                }
                if (configData.mOnBeginCallbackLen != null) {
                    configData.mOnBeginCallbackLen.set(0.0d);
                    configData.mOnBeginCallbackLen = null;
                    break;
                }
                break;
            case "property":
                if (configData.mProperty != null) {
                    configData.mProperty.set((Object) null);
                    configData.mProperty = null;
                }
                if (configData.mPropertyLen != null) {
                    configData.mPropertyLen.set(0.0d);
                    configData.mPropertyLen = null;
                    break;
                }
                break;
            case "ease":
                if (configData.mEase != null) {
                    configData.mEase.set((Object) null);
                    configData.mEase = null;
                }
                if (configData.mEaseLen != null) {
                    configData.mEaseLen.set(0.0d);
                    configData.mEaseLen = null;
                    break;
                }
                break;
            case "fromSpeed":
                if (configData.mFromSpeed != null) {
                    configData.mFromSpeed.set(0.0d);
                    configData.mFromSpeed = null;
                    break;
                }
                break;
            case "delay":
                if (configData.mDelay != null) {
                    configData.mDelay.set(0.0d);
                    configData.mDelay = null;
                    break;
                }
                break;
            case "onUpdate":
                if (configData.mOnUpdateCallback != null) {
                    configData.mOnUpdateCallback.set((Object) null);
                    configData.mOnUpdateCallback = null;
                }
                if (configData.mOnUpdateCallbackLen != null) {
                    configData.mOnUpdateCallbackLen.set(0.0d);
                    configData.mOnUpdateCallbackLen = null;
                    break;
                }
                break;
        }
    }
}
