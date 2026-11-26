package com.miui.maml.data;

import android.text.TextUtils;
import android.util.Log;
import com.miui.handwriting.common.ai.NetConfig;
import com.miui.maml.util.Utils;
import com.xiaomi.onetrack.g.a;
import java.math.BigDecimal;
import miui.util.HashUtils;

/* JADX INFO: loaded from: classes3.dex */
public class BaseFunctions extends Expression.FunctionImpl {
    private static final String LOG_TAG = "Expression";
    private Fun fun;
    private Expression mEvalExp;
    private String mEvalExpStr;

    private enum Fun {
        INVALID,
        RAND,
        SIN,
        COS,
        TAN,
        ASIN,
        ACOS,
        ATAN,
        SINH,
        COSH,
        SQRT,
        ABS,
        LEN,
        EVAL,
        PRECISE_EVAL,
        ROUND,
        INT,
        NUM,
        MIN,
        MAX,
        POW,
        LOG,
        LOG10,
        DIGIT,
        EQ,
        NE,
        GE,
        GT,
        LE,
        LT,
        ISNULL,
        NOT,
        IFELSE,
        AND,
        OR,
        EQS,
        SUBSTR,
        HASH,
        FLOOR,
        CEIL
    }

    public static void load() {
        Expression.FunctionExpression.registerFunction("rand", new BaseFunctions(Fun.RAND, 0));
        Expression.FunctionExpression.registerFunction("sin", new BaseFunctions(Fun.SIN, 1));
        Expression.FunctionExpression.registerFunction("cos", new BaseFunctions(Fun.COS, 1));
        Expression.FunctionExpression.registerFunction("tan", new BaseFunctions(Fun.TAN, 1));
        Expression.FunctionExpression.registerFunction("asin", new BaseFunctions(Fun.ASIN, 1));
        Expression.FunctionExpression.registerFunction("acos", new BaseFunctions(Fun.ACOS, 1));
        Expression.FunctionExpression.registerFunction("atan", new BaseFunctions(Fun.ATAN, 1));
        Expression.FunctionExpression.registerFunction("sinh", new BaseFunctions(Fun.SINH, 1));
        Expression.FunctionExpression.registerFunction("cosh", new BaseFunctions(Fun.COSH, 1));
        Expression.FunctionExpression.registerFunction("sqrt", new BaseFunctions(Fun.SQRT, 1));
        Expression.FunctionExpression.registerFunction("abs", new BaseFunctions(Fun.ABS, 1));
        Expression.FunctionExpression.registerFunction("len", new BaseFunctions(Fun.LEN, 1));
        Expression.FunctionExpression.registerFunction("eval", new BaseFunctions(Fun.EVAL, 1));
        Expression.FunctionExpression.registerFunction("preciseeval", new BaseFunctions(Fun.PRECISE_EVAL, 2));
        Expression.FunctionExpression.registerFunction("round", new BaseFunctions(Fun.ROUND, 1));
        Expression.FunctionExpression.registerFunction("int", new BaseFunctions(Fun.INT, 1));
        Expression.FunctionExpression.registerFunction(NetConfig.PARAM_CREATION_NUM, new BaseFunctions(Fun.NUM, 1));
        Expression.FunctionExpression.registerFunction("isnull", new BaseFunctions(Fun.ISNULL, 1));
        Expression.FunctionExpression.registerFunction("not", new BaseFunctions(Fun.NOT, 1));
        Expression.FunctionExpression.registerFunction("min", new BaseFunctions(Fun.MIN, 2));
        Expression.FunctionExpression.registerFunction("max", new BaseFunctions(Fun.MAX, 2));
        Expression.FunctionExpression.registerFunction("pow", new BaseFunctions(Fun.POW, 2));
        Expression.FunctionExpression.registerFunction("log", new BaseFunctions(Fun.LOG, 1));
        Expression.FunctionExpression.registerFunction("log10", new BaseFunctions(Fun.LOG10, 1));
        Expression.FunctionExpression.registerFunction("digit", new BaseFunctions(Fun.DIGIT, 2));
        Expression.FunctionExpression.registerFunction("eq", new BaseFunctions(Fun.EQ, 2));
        Expression.FunctionExpression.registerFunction("ne", new BaseFunctions(Fun.NE, 2));
        Expression.FunctionExpression.registerFunction("ge", new BaseFunctions(Fun.GE, 2));
        Expression.FunctionExpression.registerFunction("gt", new BaseFunctions(Fun.GT, 2));
        Expression.FunctionExpression.registerFunction("le", new BaseFunctions(Fun.LE, 2));
        Expression.FunctionExpression.registerFunction("lt", new BaseFunctions(Fun.LT, 2));
        Expression.FunctionExpression.registerFunction("ifelse", new BaseFunctions(Fun.IFELSE, 3));
        Expression.FunctionExpression.registerFunction("and", new BaseFunctions(Fun.AND, 2));
        Expression.FunctionExpression.registerFunction("or", new BaseFunctions(Fun.OR, 2));
        Expression.FunctionExpression.registerFunction("eqs", new BaseFunctions(Fun.EQS, 2));
        Expression.FunctionExpression.registerFunction("substr", new BaseFunctions(Fun.SUBSTR, 2));
        Expression.FunctionExpression.registerFunction(a.e, new BaseFunctions(Fun.HASH, 2));
        Expression.FunctionExpression.registerFunction("nullobj", new NullObjFunction());
        Expression.FunctionExpression.registerFunction("floor", new BaseFunctions(Fun.FLOOR, 1));
        Expression.FunctionExpression.registerFunction("ceil", new BaseFunctions(Fun.CEIL, 1));
    }

    private static class NullObjFunction extends Expression.FunctionImpl {
        private String mObjName;
        private int mVarIndex;

        @Override // com.miui.maml.data.Expression.FunctionImpl
        public String evaluateStr(Expression[] expressionArr, Variables variables) {
            return null;
        }

        public NullObjFunction() {
            super(1);
            this.mVarIndex = -1;
        }

        @Override // com.miui.maml.data.Expression.FunctionImpl
        public double evaluate(Expression[] expressionArr, Variables variables) {
            String strEvaluateStr = expressionArr[0].evaluateStr();
            if (strEvaluateStr != this.mObjName) {
                this.mObjName = strEvaluateStr;
                if (TextUtils.isEmpty(strEvaluateStr) || !variables.existsObj(this.mObjName)) {
                    this.mVarIndex = -1;
                } else {
                    this.mVarIndex = variables.registerVariable(this.mObjName);
                }
            }
            int i = this.mVarIndex;
            return (i == -1 || variables.get(i) == null) ? 1.0d : 0.0d;
        }

        @Override // com.miui.maml.data.Expression.FunctionImpl
        public void reset() {
            this.mObjName = null;
        }
    }

    private BaseFunctions(Fun fun, int i) {
        super(i);
        this.fun = fun;
    }

    @Override // com.miui.maml.data.Expression.FunctionImpl
    public double evaluate(Expression[] expressionArr, Variables variables) {
        if (AnonymousClass1.$SwitchMap$com$miui$maml$data$BaseFunctions$Fun[this.fun.ordinal()] == 1) {
            return Math.random();
        }
        int i = 0;
        double dEvaluate = expressionArr[0].evaluate();
        switch (this.fun) {
            case SIN:
                return Math.sin(dEvaluate);
            case COS:
                return Math.cos(dEvaluate);
            case TAN:
                return Math.tan(dEvaluate);
            case ASIN:
                return Math.asin(dEvaluate);
            case ACOS:
                return Math.acos(dEvaluate);
            case ATAN:
                return Math.atan(dEvaluate);
            case SINH:
                return Math.sinh(dEvaluate);
            case COSH:
                return Math.cosh(dEvaluate);
            case SQRT:
                return Math.sqrt(dEvaluate);
            case ABS:
                return Math.abs(dEvaluate);
            case LEN:
                String strEvaluateStr = expressionArr[0].evaluateStr();
                if (strEvaluateStr == null) {
                    return 0.0d;
                }
                return strEvaluateStr.length();
            case EVAL:
                String strEvaluateStr2 = expressionArr[0].evaluateStr();
                if (strEvaluateStr2 == null) {
                    return 0.0d;
                }
                if (!strEvaluateStr2.equals(this.mEvalExpStr)) {
                    this.mEvalExpStr = strEvaluateStr2;
                    this.mEvalExp = Expression.build(variables, strEvaluateStr2);
                }
                Expression expression = this.mEvalExp;
                if (expression == null) {
                    return 0.0d;
                }
                return expression.evaluate();
            case PRECISE_EVAL:
                String strEvaluateStr3 = expressionArr[0].evaluateStr();
                if (strEvaluateStr3 == null) {
                    return 0.0d;
                }
                if (!strEvaluateStr3.equals(this.mEvalExpStr)) {
                    this.mEvalExpStr = strEvaluateStr3;
                    this.mEvalExp = Expression.build(variables, strEvaluateStr3);
                }
                Expression expression2 = this.mEvalExp;
                BigDecimal bigDecimalPreciseEvaluate = expression2 != null ? expression2.preciseEvaluate() : null;
                if (bigDecimalPreciseEvaluate == null) {
                    return Double.NaN;
                }
                int iScale = bigDecimalPreciseEvaluate.scale();
                int iEvaluate = (int) expressionArr[1].evaluate();
                if (iEvaluate > 0 && iScale > iEvaluate) {
                    bigDecimalPreciseEvaluate = bigDecimalPreciseEvaluate.setScale(iEvaluate, 4);
                }
                return bigDecimalPreciseEvaluate.doubleValue();
            case ROUND:
                return Math.round(dEvaluate);
            case INT:
                return (int) dEvaluate;
            case NUM:
                return dEvaluate;
            case ISNULL:
                return expressionArr[0].isNull() ? 1.0d : 0.0d;
            case NOT:
                return dEvaluate > 0.0d ? 0.0d : 1.0d;
            case MIN:
                return Math.min(dEvaluate, expressionArr[1].evaluate());
            case MAX:
                return Math.max(dEvaluate, expressionArr[1].evaluate());
            case POW:
                return Math.pow(dEvaluate, expressionArr[1].evaluate());
            case LOG:
                return Math.log(dEvaluate);
            case LOG10:
                return Math.log10(dEvaluate);
            case DIGIT:
                return digit((int) dEvaluate, (int) expressionArr[1].evaluate());
            case EQ:
                return dEvaluate == expressionArr[1].evaluate() ? 1.0d : 0.0d;
            case NE:
                return dEvaluate != expressionArr[1].evaluate() ? 1.0d : 0.0d;
            case GE:
                return dEvaluate >= expressionArr[1].evaluate() ? 1.0d : 0.0d;
            case GT:
                return dEvaluate > expressionArr[1].evaluate() ? 1.0d : 0.0d;
            case LE:
                return dEvaluate <= expressionArr[1].evaluate() ? 1.0d : 0.0d;
            case LT:
                return dEvaluate < expressionArr[1].evaluate() ? 1.0d : 0.0d;
            case IFELSE:
                int length = expressionArr.length;
                if (length % 2 != 1) {
                    Log.e(LOG_TAG, "function parameter number should be 2*n+1: " + this.fun.toString());
                    return 0.0d;
                }
                while (true) {
                    int i2 = length - 1;
                    if (i >= i2 / 2) {
                        return expressionArr[i2].evaluate();
                    }
                    int i3 = i * 2;
                    if (expressionArr[i3].evaluate() > 0.0d) {
                        return expressionArr[i3 + 1].evaluate();
                    }
                    i++;
                }
                break;
            case AND:
                int length2 = expressionArr.length;
                while (i < length2) {
                    if (expressionArr[i].evaluate() <= 0.0d) {
                        return 0.0d;
                    }
                    i++;
                }
                return 1.0d;
            case OR:
                int length3 = expressionArr.length;
                while (i < length3) {
                    if (expressionArr[i].evaluate() > 0.0d) {
                        return 1.0d;
                    }
                    i++;
                }
                return 0.0d;
            case EQS:
                return TextUtils.equals(expressionArr[0].evaluateStr(), expressionArr[1].evaluateStr()) ? 1.0d : 0.0d;
            case SUBSTR:
                return Utils.stringToDouble(evaluateStr(expressionArr, variables), 0.0d);
            case FLOOR:
                return Math.floor(dEvaluate);
            case CEIL:
                return Math.ceil(dEvaluate);
            default:
                Log.e(LOG_TAG, "fail to evalute FunctionExpression, invalid function: " + this.fun.toString());
                return 0.0d;
        }
    }

    @Override // com.miui.maml.data.Expression.FunctionImpl
    public String evaluateStr(Expression[] expressionArr, Variables variables) {
        int i = AnonymousClass1.$SwitchMap$com$miui$maml$data$BaseFunctions$Fun[this.fun.ordinal()];
        int i2 = 0;
        if (i == 13) {
            String strEvaluateStr = expressionArr[0].evaluateStr();
            if (strEvaluateStr == null) {
                return null;
            }
            if (!strEvaluateStr.equals(this.mEvalExpStr)) {
                this.mEvalExpStr = strEvaluateStr;
                this.mEvalExp = Expression.build(variables, strEvaluateStr);
            }
            Expression expression = this.mEvalExp;
            if (expression == null) {
                return null;
            }
            return expression.evaluateStr();
        }
        if (i != 32) {
            if (i != 36) {
                if (i == 39) {
                    String strEvaluateStr2 = expressionArr[0].evaluateStr();
                    String strEvaluateStr3 = expressionArr[1].evaluateStr();
                    if (strEvaluateStr2 == null || strEvaluateStr3 == null) {
                        return null;
                    }
                    return HashUtils.getHash(strEvaluateStr2, strEvaluateStr3);
                }
                return Utils.doubleToString(evaluate(expressionArr, variables));
            }
            String strEvaluateStr4 = expressionArr[0].evaluateStr();
            if (strEvaluateStr4 == null) {
                return null;
            }
            int length = expressionArr.length;
            int iEvaluate = (int) expressionArr[1].evaluate();
            try {
                if (length >= 3) {
                    int iEvaluate2 = (int) expressionArr[2].evaluate();
                    int length2 = strEvaluateStr4.length();
                    if (iEvaluate2 > length2) {
                        iEvaluate2 = length2;
                    }
                    return strEvaluateStr4.substring(iEvaluate, iEvaluate2 + iEvaluate);
                }
                return strEvaluateStr4.substring(iEvaluate);
            } catch (IndexOutOfBoundsException unused) {
                return null;
            }
        }
        int length3 = expressionArr.length;
        if (length3 % 2 != 1) {
            Log.e(LOG_TAG, "function parameter number should be 2*n+1: " + this.fun.toString());
            return null;
        }
        while (true) {
            int i3 = length3 - 1;
            if (i2 >= i3 / 2) {
                return expressionArr[i3].evaluateStr();
            }
            int i4 = i2 * 2;
            if (expressionArr[i4].evaluate() > 0.0d) {
                return expressionArr[i4 + 1].evaluateStr();
            }
            i2++;
        }
    }

    @Override // com.miui.maml.data.Expression.FunctionImpl
    public void reset() {
        this.mEvalExpStr = null;
        this.mEvalExp = null;
    }

    private int digit(int i, int i2) {
        if (i2 <= 0) {
            return -1;
        }
        if (i == 0 && i2 == 1) {
            return 0;
        }
        for (int i3 = 0; i > 0 && i3 < i2 - 1; i3++) {
            i /= 10;
        }
        if (i > 0) {
            return i % 10;
        }
        return -1;
    }
}
