package com.miui.maml.data;

import android.util.Log;
import com.miui.maml.util.Utils;
import java.util.regex.PatternSyntaxException;

/* JADX INFO: loaded from: classes3.dex */
public class StringFunctions extends Expression.FunctionImpl {
    private static final String LOG_TAG = "Expression";
    private final Fun mFun;

    private enum Fun {
        INVALID,
        STR_TOLOWER,
        STR_TOUPPER,
        STR_TRIM,
        STR_REPLACE,
        STR_REPLACEALL,
        STR_REPLACEFIRST,
        STR_CONTAINS,
        STR_STARTWITH,
        STR_ENDSWITH,
        STR_ISEMPTY,
        STR_MATCHES,
        STR_INDEXOF,
        STR_LASTINDEXOF
    }

    @Override // com.miui.maml.data.Expression.FunctionImpl
    public void reset() {
    }

    public static void load() {
        Expression.FunctionExpression.registerFunction("strToLowerCase", new StringFunctions(Fun.STR_TOLOWER, 1));
        Expression.FunctionExpression.registerFunction("strToUpperCase", new StringFunctions(Fun.STR_TOUPPER, 1));
        Expression.FunctionExpression.registerFunction("strTrim", new StringFunctions(Fun.STR_TRIM, 1));
        Expression.FunctionExpression.registerFunction("strReplace", new StringFunctions(Fun.STR_REPLACE, 3));
        Expression.FunctionExpression.registerFunction("strReplaceAll", new StringFunctions(Fun.STR_REPLACEALL, 3));
        Expression.FunctionExpression.registerFunction("strReplaceFirst", new StringFunctions(Fun.STR_REPLACEFIRST, 3));
        Expression.FunctionExpression.registerFunction("strContains", new StringFunctions(Fun.STR_CONTAINS, 2));
        Expression.FunctionExpression.registerFunction("strStartsWith", new StringFunctions(Fun.STR_STARTWITH, 2));
        Expression.FunctionExpression.registerFunction("strEndsWith", new StringFunctions(Fun.STR_ENDSWITH, 2));
        Expression.FunctionExpression.registerFunction("strIsEmpty", new StringFunctions(Fun.STR_ISEMPTY, 1));
        Expression.FunctionExpression.registerFunction("strMatches", new StringFunctions(Fun.STR_MATCHES, 2));
        Expression.FunctionExpression.registerFunction("strIndexOf", new StringFunctions(Fun.STR_INDEXOF, 2));
        Expression.FunctionExpression.registerFunction("strLastIndexOf", new StringFunctions(Fun.STR_LASTINDEXOF, 2));
    }

    private StringFunctions(Fun fun, int i) {
        super(i);
        this.mFun = fun;
    }

    @Override // com.miui.maml.data.Expression.FunctionImpl
    public double evaluate(Expression[] expressionArr, Variables variables) {
        switch (this.mFun) {
            case STR_REPLACE:
            case STR_REPLACEALL:
            case STR_REPLACEFIRST:
            case STR_TOLOWER:
            case STR_TOUPPER:
            case STR_TRIM:
                return Utils.stringToDouble(evaluateStr(expressionArr, variables), 0.0d);
            default:
                String strEvaluateStr = expressionArr[0].evaluateStr();
                if (AnonymousClass1.$SwitchMap$com$miui$maml$data$StringFunctions$Fun[this.mFun.ordinal()] == 7) {
                    return (strEvaluateStr == null || strEvaluateStr.isEmpty()) ? 1.0d : 0.0d;
                }
                String strEvaluateStr2 = expressionArr[1].evaluateStr();
                switch (AnonymousClass1.$SwitchMap$com$miui$maml$data$StringFunctions$Fun[this.mFun.ordinal()]) {
                    case 1:
                        return (strEvaluateStr == null || strEvaluateStr2 == null || !strEvaluateStr.contains(strEvaluateStr2)) ? 0.0d : 1.0d;
                    case 2:
                        return (strEvaluateStr == null || strEvaluateStr2 == null || !strEvaluateStr.startsWith(strEvaluateStr2)) ? 0.0d : 1.0d;
                    case 3:
                        return (strEvaluateStr == null || strEvaluateStr2 == null || !strEvaluateStr.endsWith(strEvaluateStr2)) ? 0.0d : 1.0d;
                    case 4:
                        if (strEvaluateStr != null && strEvaluateStr2 != null) {
                            try {
                                if (strEvaluateStr.matches(strEvaluateStr2)) {
                                    return 1.0d;
                                }
                            } catch (PatternSyntaxException unused) {
                                Log.w(LOG_TAG, "invaid pattern of matches: " + strEvaluateStr2);
                            }
                        }
                        return 0.0d;
                    case 5:
                        if (strEvaluateStr == null || strEvaluateStr2 == null) {
                            return -1.0d;
                        }
                        return strEvaluateStr.indexOf(strEvaluateStr2);
                    case 6:
                        if (strEvaluateStr == null || strEvaluateStr2 == null) {
                            return -1.0d;
                        }
                        return strEvaluateStr.lastIndexOf(strEvaluateStr2);
                    default:
                        Log.e(LOG_TAG, "fail to evalute FunctionExpression, invalid function: " + this.mFun.toString());
                        return 0.0d;
                }
        }
    }

    @Override // com.miui.maml.data.Expression.FunctionImpl
    public String evaluateStr(Expression[] expressionArr, Variables variables) {
        switch (AnonymousClass1.$SwitchMap$com$miui$maml$data$StringFunctions$Fun[this.mFun.ordinal()]) {
            case 1:
            case 2:
            case 3:
            case 4:
            case 5:
            case 6:
            case 7:
                return Utils.doubleToString(evaluate(expressionArr, variables));
            default:
                String strEvaluateStr = expressionArr[0].evaluateStr();
                if (strEvaluateStr == null) {
                    return null;
                }
                switch (this.mFun) {
                    case STR_TOLOWER:
                        return strEvaluateStr.toLowerCase();
                    case STR_TOUPPER:
                        return strEvaluateStr.toUpperCase();
                    case STR_TRIM:
                        return strEvaluateStr.trim();
                    default:
                        String strEvaluateStr2 = expressionArr[1].evaluateStr();
                        String strEvaluateStr3 = expressionArr[2].evaluateStr();
                        if (strEvaluateStr2 == null || strEvaluateStr3 == null) {
                            return strEvaluateStr;
                        }
                        switch (this.mFun) {
                            case STR_REPLACE:
                                return strEvaluateStr.replace(strEvaluateStr2, strEvaluateStr3);
                            case STR_REPLACEALL:
                                try {
                                    return strEvaluateStr.replaceAll(strEvaluateStr2, strEvaluateStr3);
                                } catch (PatternSyntaxException unused) {
                                    Log.w(LOG_TAG, "invaid pattern of replaceAll: " + strEvaluateStr2);
                                    return strEvaluateStr;
                                }
                            case STR_REPLACEFIRST:
                                try {
                                    return strEvaluateStr.replaceFirst(strEvaluateStr2, strEvaluateStr3);
                                } catch (PatternSyntaxException unused2) {
                                    Log.w(LOG_TAG, "invaid pattern of replaceFirst:" + strEvaluateStr2);
                                    return strEvaluateStr;
                                }
                            default:
                                Log.e(LOG_TAG, "fail to evaluteStr FunctionExpression, invalid function: " + this.mFun.toString());
                                return null;
                        }
                }
        }
    }
}
