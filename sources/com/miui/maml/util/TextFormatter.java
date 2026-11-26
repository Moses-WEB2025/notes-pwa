package com.miui.maml.util;

import android.text.TextUtils;
import android.util.Log;
import com.miui.maml.StylesManager;
import com.miui.maml.data.Expression;
import com.miui.maml.data.IndexedVariable;
import com.miui.maml.data.Variables;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.IllegalFormatException;
import org.w3c.dom.Element;

/* JADX INFO: loaded from: classes3.dex */
public class TextFormatter {
    private static final String LOG_TAG = "TextFormatter";
    private String mFormat;
    private Expression mFormatExpression;
    private IndexedVariable mIndexedFormatVar;
    private IndexedVariable mIndexedTextVar;
    private FormatPara[] mParas;
    private Object[] mParasValue;
    private String mText;
    private Expression mTextExpression;

    public TextFormatter(Variables variables, String str) {
        this(variables, str, "", "");
    }

    public TextFormatter(Variables variables, String str, String str2) {
        this(variables, "", str, str2);
    }

    public TextFormatter(Variables variables, String str, String str2, String str3) {
        this.mText = str;
        if (str.startsWith("@")) {
            String strSubstring = this.mText.substring(1);
            this.mText = strSubstring;
            if (!strSubstring.startsWith("@")) {
                this.mIndexedTextVar = new IndexedVariable(this.mText, variables, false);
            }
        }
        this.mFormat = str2;
        if (str2.startsWith("@")) {
            String strSubstring2 = this.mFormat.substring(1);
            this.mFormat = strSubstring2;
            if (!strSubstring2.startsWith("@")) {
                this.mIndexedFormatVar = new IndexedVariable(this.mFormat, variables, false);
            }
        }
        if (TextUtils.isEmpty(str3)) {
            return;
        }
        FormatPara[] formatParaArrBuildArray = FormatPara.buildArray(variables, str3);
        this.mParas = formatParaArrBuildArray;
        if (formatParaArrBuildArray != null) {
            this.mParasValue = new Object[formatParaArrBuildArray.length];
        }
    }

    public TextFormatter(Variables variables, String str, Expression expression) {
        this(variables, str, "", "", expression, null);
    }

    public TextFormatter(Variables variables, String str, String str2, String str3, Expression expression, Expression expression2) {
        this(variables, str, str2, str3);
        this.mTextExpression = expression;
        this.mFormatExpression = expression2;
    }

    public void setParams(Object... objArr) {
        if (objArr != null) {
            this.mParas = null;
            int length = objArr.length;
            if (this.mParasValue == null) {
                this.mParasValue = new Object[length];
            }
            Object[] objArr2 = this.mParasValue;
            if (objArr2.length < length) {
                length = objArr2.length;
            }
            this.mParasValue = Arrays.copyOf(objArr, length);
        }
    }

    public void setText(String str) {
        this.mText = str;
        this.mFormat = "";
    }

    public String getFormat() {
        Expression expression = this.mFormatExpression;
        if (expression != null) {
            return expression.evaluateStr();
        }
        IndexedVariable indexedVariable = this.mIndexedFormatVar;
        if (indexedVariable != null) {
            return indexedVariable.getString();
        }
        return this.mFormat;
    }

    public boolean hasFormat() {
        return !TextUtils.isEmpty(this.mFormat);
    }

    public String getText() {
        Expression expression = this.mTextExpression;
        if (expression != null) {
            return expression.evaluateStr();
        }
        String format = getFormat();
        if (!TextUtils.isEmpty(format)) {
            if (this.mParas != null) {
                int i = 0;
                while (true) {
                    FormatPara[] formatParaArr = this.mParas;
                    if (i >= formatParaArr.length) {
                        break;
                    }
                    this.mParasValue[i] = formatParaArr[i].evaluate();
                    i++;
                }
            }
            Object[] objArr = this.mParasValue;
            if (objArr != null) {
                try {
                    return String.format(format, objArr);
                } catch (IllegalFormatException unused) {
                    return "Format error: " + format;
                }
            }
        }
        IndexedVariable indexedVariable = this.mIndexedTextVar;
        if (indexedVariable != null) {
            return indexedVariable.getString();
        }
        return this.mText;
    }

    private static abstract class FormatPara {
        public abstract Object evaluate();

        private FormatPara() {
        }

        public static FormatPara[] buildArray(Variables variables, String str) {
            ArrayList arrayList = new ArrayList();
            int i = 0;
            int i2 = 0;
            for (int i3 = 0; i3 < str.length(); i3++) {
                char cCharAt = str.charAt(i3);
                if (i2 == 0 && cCharAt == ',') {
                    FormatPara formatParaBuild = build(variables, str.substring(i, i3));
                    if (formatParaBuild == null) {
                        return null;
                    }
                    arrayList.add(formatParaBuild);
                    i = i3 + 1;
                }
                if (cCharAt == '(') {
                    i2++;
                } else if (cCharAt == ')') {
                    i2--;
                }
            }
            FormatPara formatParaBuild2 = build(variables, str.substring(i));
            if (formatParaBuild2 == null) {
                return null;
            }
            arrayList.add(formatParaBuild2);
            return (FormatPara[]) arrayList.toArray(new FormatPara[arrayList.size()]);
        }

        public static FormatPara build(Variables variables, String str) {
            String strTrim = str.trim();
            if (strTrim.startsWith("@")) {
                return new StringVarPara(variables, strTrim.substring(1));
            }
            Expression expressionBuild = Expression.build(variables, strTrim);
            if (expressionBuild == null) {
                Log.e(TextFormatter.LOG_TAG, "invalid parameter expression:" + str);
                return null;
            }
            return new ExpressioPara(expressionBuild);
        }
    }

    private static class ExpressioPara extends FormatPara {
        private Expression mExp;

        public ExpressioPara(Expression expression) {
            super();
            this.mExp = expression;
        }

        @Override // com.miui.maml.util.TextFormatter.FormatPara
        public Object evaluate() {
            return Long.valueOf((long) this.mExp.evaluate());
        }
    }

    private static class StringVarPara extends FormatPara {
        private IndexedVariable mVar;
        private String mVariable;

        public StringVarPara(Variables variables, String str) {
            super();
            this.mVariable = str;
            this.mVar = new IndexedVariable(this.mVariable, variables, false);
        }

        @Override // com.miui.maml.util.TextFormatter.FormatPara
        public Object evaluate() {
            String string = this.mVar.getString();
            return string == null ? "" : string;
        }
    }

    public static TextFormatter fromElement(Variables variables, Element element, StylesManager.Style style) {
        String attr = StyleHelper.getAttr(element, "paras", style);
        if (TextUtils.isEmpty(attr)) {
            attr = StyleHelper.getAttr(element, "params", style);
        }
        return new TextFormatter(variables, StyleHelper.getAttr(element, "text", style), StyleHelper.getAttr(element, "format", style), attr, Expression.build(variables, StyleHelper.getAttr(element, "textExp", style)), Expression.build(variables, StyleHelper.getAttr(element, "formatExp", style)));
    }

    public static TextFormatter fromElement(Variables variables, Element element, String str, String str2, String str3, String str4, String str5) {
        return new TextFormatter(variables, element.getAttribute(str), element.getAttribute(str2), element.getAttribute(str3), Expression.build(variables, element.getAttribute(str4)), Expression.build(variables, element.getAttribute(str5)));
    }
}
