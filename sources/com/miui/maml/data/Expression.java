package com.miui.maml.data;

import android.text.TextUtils;
import android.util.Log;
import com.miui.maml.util.Utils;
import java.math.BigDecimal;
import java.math.MathContext;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Stack;
import net.lingala.zip4j.util.InternalZipConstants;
import org.apache.commons.text.StringSubstitutor;
import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Marker;

/* JADX INFO: loaded from: classes3.dex */
public abstract class Expression {
    private static final boolean DEBUG = false;
    private static final String LOG_TAG = "Expression";

    private enum Ope {
        ADD,
        MIN,
        MUL,
        DIV,
        MOD,
        BIT_AND,
        BIT_OR,
        BIT_XOR,
        BIT_NOT,
        BIT_LSHIFT,
        BIT_RSHIFT,
        NOT,
        EQ,
        NEQ,
        AND,
        OR,
        GT,
        GE,
        LT,
        LE,
        INVALID
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static boolean isDigitCharRest(char c) {
        if (c >= '0' && c <= '9') {
            return true;
        }
        if (c < 'a' || c > 'f') {
            return (c >= 'A' && c <= 'F') || c == '.';
        }
        return true;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static boolean isDigitCharStart(char c) {
        return (c >= '0' && c <= '9') || c == '.';
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static boolean isFunctionCharStart(char c) {
        if (c < 'a' || c > 'z') {
            return c >= 'A' && c <= 'Z';
        }
        return true;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static boolean isVariableChar(char c) {
        if (c >= 'a' && c <= 'z') {
            return true;
        }
        if ((c >= 'A' && c <= 'Z') || c == '_' || c == '.') {
            return true;
        }
        return c >= '0' && c <= '9';
    }

    public abstract double evaluate();

    public JSONArray evaluateJsonArray() {
        return null;
    }

    public JSONObject evaluateJsonObject() {
        return null;
    }

    public String evaluateStr() {
        return null;
    }

    public boolean isNull() {
        return false;
    }

    private static class OpeInfo {
        private static final int OPE_SIZE;
        private static final String[] mOpes;
        public int participants;
        public int priority;
        public String str;
        public boolean unary;
        private static final int[] mOpePriority = {4, 4, 3, 3, 3, 8, 9, 10, 2, 5, 5, 2, 7, 7, 11, 12, 6, 6, 6, 6};
        private static final int[] mOpePar = {2, 1, 2, 2, 2, 2, 2, 2, 1, 2, 2, 1, 2, 2, 2, 2, 2, 2, 2, 2};

        private OpeInfo() {
        }

        static {
            String[] strArr = {Marker.ANY_NON_NULL_MARKER, "-", "*", InternalZipConstants.ZIP_FILE_SEPARATOR, "%", "&", "|", "^", "~", "{{", "}}", "!", "==", "!=", "**", "||", StringSubstitutor.DEFAULT_VAR_END, "}=", "{", "{="};
            mOpes = strArr;
            OPE_SIZE = strArr.length;
        }

        public static class Parser {
            private int[] mFlags = new int[OpeInfo.OPE_SIZE];
            private int mMatch;
            private int mStep;

            public boolean accept(char c, boolean z) {
                if (z) {
                    for (int i = 0; i < OpeInfo.OPE_SIZE; i++) {
                        this.mFlags[i] = 0;
                    }
                    this.mStep = 0;
                    this.mMatch = -1;
                }
                boolean z2 = false;
                for (int i2 = 0; i2 < OpeInfo.OPE_SIZE; i2++) {
                    if (this.mFlags[i2] != -1) {
                        String str = OpeInfo.mOpes[i2];
                        int length = str.length();
                        int i3 = this.mStep;
                        if (length > i3 && str.charAt(i3) == c) {
                            boolean z3 = this.mStep == str.length() - 1;
                            this.mFlags[i2] = 0;
                            if (z3) {
                                this.mMatch = i2;
                            }
                            z2 = true;
                        } else {
                            this.mFlags[i2] = -1;
                        }
                    }
                }
                if (z2) {
                    this.mStep++;
                }
                return z2;
            }

            public Ope getMatch() {
                return this.mMatch == -1 ? Ope.INVALID : Ope.values()[this.mMatch];
            }
        }

        public static OpeInfo getOpeInfo(int i) {
            OpeInfo opeInfo = new OpeInfo();
            opeInfo.priority = mOpePriority[i];
            opeInfo.participants = mOpePar[i];
            opeInfo.str = mOpes[i];
            return opeInfo;
        }
    }

    private static class Tokenizer {
        private static final int BRACKET_MODE_NONE = 0;
        private static final int BRACKET_MODE_ROUND = 1;
        private static final int BRACKET_MODE_SQUARE = 2;
        private OpeInfo.Parser mOpeParser = new OpeInfo.Parser();
        private int mPos;
        private String mString;

        public enum TokenType {
            INVALID,
            VAR_NUM,
            VAR_STR,
            VAR_JSON_OBJ,
            VAR_JSON_ARR,
            NUM,
            STR,
            OPE,
            FUN,
            BRACKET_ROUND,
            BRACKET_SQUARE
        }

        public static class Token {
            public OpeInfo info;
            public Ope op;
            public String token;
            public TokenType type;

            public Token(TokenType tokenType, String str) {
                this.type = TokenType.INVALID;
                this.op = Ope.INVALID;
                this.type = tokenType;
                this.token = str;
            }

            public Token(TokenType tokenType, String str, Ope ope) {
                this.type = TokenType.INVALID;
                Ope ope2 = Ope.INVALID;
                this.type = tokenType;
                this.token = str;
                this.op = ope;
                this.info = OpeInfo.getOpeInfo(ope.ordinal());
            }
        }

        public Tokenizer(String str) {
            this.mString = str;
            reset();
        }

        public void reset() {
            this.mPos = 0;
        }

        public Token getToken() {
            TokenType tokenType = TokenType.INVALID;
            int length = this.mString.length();
            int i = -1;
            int i2 = 0;
            char c = 0;
            char c2 = 0;
            char c3 = 0;
            for (int i3 = this.mPos; i3 < length; i3++) {
                char cCharAt = this.mString.charAt(i3);
                if (c == 0) {
                    if (cCharAt == '#' || cCharAt == '@' || cCharAt == '$') {
                        int i4 = i3 + 1;
                        int i5 = (i4 >= length || this.mString.charAt(i4) != '$') ? 1 : 2;
                        int i6 = i3 + i5;
                        int i7 = i6;
                        while (i7 < length && Expression.isVariableChar(this.mString.charAt(i7))) {
                            i7++;
                        }
                        if (i7 == i6) {
                            Log.e(Expression.LOG_TAG, "invalid variable name:" + this.mString);
                            return null;
                        }
                        this.mPos = i7;
                        TokenType tokenType2 = TokenType.VAR_NUM;
                        if (cCharAt == '@') {
                            tokenType2 = TokenType.VAR_STR;
                        } else if (cCharAt == '$') {
                            tokenType2 = i5 == 1 ? TokenType.VAR_JSON_OBJ : TokenType.VAR_JSON_ARR;
                        }
                        return new Token(tokenType2, this.mString.substring(i6, i7));
                    }
                    if (cCharAt == '\'') {
                        int i8 = i3 + 1;
                        int i9 = i8;
                        boolean z = false;
                        while (i9 < length) {
                            char cCharAt2 = this.mString.charAt(i9);
                            if (!z && cCharAt2 == '\'') {
                                break;
                            }
                            z = cCharAt2 == '\\';
                            i9++;
                        }
                        this.mPos = i9 + 1;
                        return new Token(TokenType.STR, this.mString.substring(i8, i9).replace("\\'", "'"));
                    }
                    if (cCharAt == '(') {
                        c = 1;
                    } else if (cCharAt == '[') {
                        c = 2;
                    } else if (!Expression.isDigitCharStart(cCharAt)) {
                        if (Expression.isFunctionCharStart(cCharAt)) {
                            int i10 = i3 + 1;
                            while (i10 < length && Expression.isFunctionCharRest(this.mString.charAt(i10))) {
                                i10++;
                            }
                            this.mPos = i10;
                            return new Token(TokenType.FUN, this.mString.substring(i3, i10));
                        }
                        if (this.mOpeParser.accept(cCharAt, true)) {
                            int i11 = i3 + 1;
                            while (i11 < length && this.mOpeParser.accept(this.mString.charAt(i11), false)) {
                                i11++;
                            }
                            Ope match = this.mOpeParser.getMatch();
                            if (match != Ope.INVALID) {
                                this.mPos = i11;
                                return new Token(TokenType.OPE, this.mString.substring(i3, this.mPos), match);
                            }
                        }
                    } else {
                        int i12 = i3 + 1;
                        if (this.mString.charAt(i3) == '0' && i12 < length && this.mString.charAt(i12) == 'x') {
                            i12 = i3 + 2;
                        }
                        while (i12 < length && Expression.isDigitCharRest(this.mString.charAt(i12))) {
                            i12++;
                        }
                        this.mPos = i12;
                        return new Token(TokenType.NUM, this.mString.substring(i3, i12));
                    }
                }
                if (c != 0) {
                    if (i2 == 0) {
                        if (c == 1) {
                            tokenType = TokenType.BRACKET_ROUND;
                            c3 = ')';
                            c2 = '(';
                        } else if (c == 2) {
                            tokenType = TokenType.BRACKET_SQUARE;
                            c3 = ']';
                            c2 = '[';
                        }
                        i = i3 + 1;
                    }
                    if (cCharAt == c2) {
                        i2++;
                    } else if (cCharAt == c3 && (i2 = i2 - 1) == 0) {
                        this.mPos = i3 + 1;
                        return new Token(tokenType, this.mString.substring(i, i3));
                    }
                }
            }
            if (i2 != 0) {
                Log.e(Expression.LOG_TAG, "mismatched bracket:" + this.mString);
            }
            return null;
        }
    }

    static abstract class VariableExpression extends Expression {
        protected IndexedVariable mIndexedVar;
        protected String mName;

        public VariableExpression(Variables variables, String str, boolean z) {
            this.mName = str;
            this.mIndexedVar = new IndexedVariable(this.mName, variables, z);
        }

        public int getIndex() {
            return this.mIndexedVar.getIndex();
        }

        public int getVersion() {
            return this.mIndexedVar.getVersion();
        }

        public String getName() {
            return this.mName;
        }
    }

    static class NumberVariableExpression extends VariableExpression {
        public NumberVariableExpression(Variables variables, String str) {
            super(variables, str, true);
        }

        @Override // com.miui.maml.data.Expression
        public double evaluate() {
            return this.mIndexedVar.getDouble();
        }

        @Override // com.miui.maml.data.Expression
        public String evaluateStr() {
            return Utils.doubleToString(evaluate());
        }

        @Override // com.miui.maml.data.Expression
        public boolean isNull() {
            return this.mIndexedVar.isNull();
        }
    }

    static class StringVariableExpression extends VariableExpression {
        public StringVariableExpression(Variables variables, String str) {
            super(variables, str, false);
        }

        @Override // com.miui.maml.data.Expression
        public double evaluate() {
            String strEvaluateStr = evaluateStr();
            if (TextUtils.isEmpty(strEvaluateStr)) {
                return 0.0d;
            }
            try {
                return Double.parseDouble(strEvaluateStr);
            } catch (NumberFormatException unused) {
                return 0.0d;
            }
        }

        @Override // com.miui.maml.data.Expression
        public String evaluateStr() {
            return this.mIndexedVar.getString();
        }

        @Override // com.miui.maml.data.Expression
        public boolean isNull() {
            return this.mIndexedVar.isNull();
        }
    }

    static class JsonObjectVariableExpression extends VariableExpression {
        @Override // com.miui.maml.data.Expression
        public double evaluate() {
            return 0.0d;
        }

        public JsonObjectVariableExpression(Variables variables, String str) {
            super(variables, str, false);
        }

        @Override // com.miui.maml.data.Expression
        public String evaluateStr() {
            JSONObject jSONObjectEvaluateJsonObject = evaluateJsonObject();
            if (jSONObjectEvaluateJsonObject != null) {
                return jSONObjectEvaluateJsonObject.toString();
            }
            return null;
        }

        @Override // com.miui.maml.data.Expression
        public JSONObject evaluateJsonObject() {
            Object obj = this.mIndexedVar.get();
            if (obj instanceof JSONObject) {
                return (JSONObject) obj;
            }
            return super.evaluateJsonObject();
        }

        @Override // com.miui.maml.data.Expression
        public boolean isNull() {
            return this.mIndexedVar.isNull();
        }
    }

    static class JsonArrayVariableExpression extends VariableExpression {
        @Override // com.miui.maml.data.Expression
        public double evaluate() {
            return 0.0d;
        }

        public JsonArrayVariableExpression(Variables variables, String str) {
            super(variables, str, false);
        }

        @Override // com.miui.maml.data.Expression
        public String evaluateStr() {
            JSONArray jSONArrayEvaluateJsonArray = evaluateJsonArray();
            if (jSONArrayEvaluateJsonArray != null) {
                return jSONArrayEvaluateJsonArray.toString();
            }
            return null;
        }

        @Override // com.miui.maml.data.Expression
        public JSONArray evaluateJsonArray() {
            Object obj = this.mIndexedVar.get();
            if (obj instanceof JSONArray) {
                return (JSONArray) obj;
            }
            return super.evaluateJsonArray();
        }

        @Override // com.miui.maml.data.Expression
        public boolean isNull() {
            return this.mIndexedVar.isNull();
        }
    }

    static abstract class ArrayVariableExpression extends VariableExpression {
        protected Expression mIndexExp;

        public ArrayVariableExpression(Variables variables, String str, Expression expression) {
            super(variables, str, false);
            this.mIndexExp = expression;
        }

        @Override // com.miui.maml.data.Expression
        public void accept(ExpressionVisitor expressionVisitor) {
            expressionVisitor.visit(this);
            this.mIndexExp.accept(expressionVisitor);
        }
    }

    static class NumberArrayVariableExpression extends ArrayVariableExpression {
        public NumberArrayVariableExpression(Variables variables, String str, Expression expression) {
            super(variables, str, expression);
        }

        @Override // com.miui.maml.data.Expression
        public double evaluate() {
            return this.mIndexedVar.getArrDouble((int) this.mIndexExp.evaluate());
        }

        @Override // com.miui.maml.data.Expression
        public String evaluateStr() {
            return Utils.doubleToString(evaluate());
        }

        @Override // com.miui.maml.data.Expression
        public boolean isNull() {
            return this.mIndexedVar.isNull((int) this.mIndexExp.evaluate());
        }
    }

    static class StringArrayVariableExpression extends ArrayVariableExpression {
        public StringArrayVariableExpression(Variables variables, String str, Expression expression) {
            super(variables, str, expression);
        }

        @Override // com.miui.maml.data.Expression
        public double evaluate() {
            String strEvaluateStr = evaluateStr();
            if (TextUtils.isEmpty(strEvaluateStr)) {
                return 0.0d;
            }
            try {
                return Double.parseDouble(strEvaluateStr);
            } catch (NumberFormatException unused) {
                return 0.0d;
            }
        }

        @Override // com.miui.maml.data.Expression
        public String evaluateStr() {
            return this.mIndexedVar.getArrString((int) this.mIndexExp.evaluate());
        }

        @Override // com.miui.maml.data.Expression
        public boolean isNull() {
            return this.mIndexedVar.isNull((int) this.mIndexExp.evaluate());
        }
    }

    public static class NumberExpression extends Expression {
        private String mString;
        private double mValue;

        public NumberExpression(double d) {
            this.mValue = d;
        }

        public NumberExpression(String str) {
            if (TextUtils.isEmpty(str)) {
                Log.e(Expression.LOG_TAG, "invalid NumberExpression: null");
                return;
            }
            try {
                if (str.length() > 2 && str.indexOf("0x") == 0) {
                    this.mValue = Long.parseLong(str.substring(2), 16);
                } else {
                    this.mValue = Double.parseDouble(str);
                }
            } catch (NumberFormatException unused) {
                Log.e(Expression.LOG_TAG, "invalid NumberExpression:" + str);
            }
        }

        public void setValue(double d) {
            this.mValue = d;
        }

        @Override // com.miui.maml.data.Expression
        public double evaluate() {
            return this.mValue;
        }

        @Override // com.miui.maml.data.Expression
        public String evaluateStr() {
            if (this.mString == null) {
                this.mString = Utils.doubleToString(this.mValue);
            }
            return this.mString;
        }
    }

    static class StringExpression extends Expression {
        private String mValue;

        public StringExpression(String str) {
            this.mValue = str;
        }

        @Override // com.miui.maml.data.Expression
        public double evaluate() {
            if (TextUtils.isEmpty(this.mValue)) {
                return 0.0d;
            }
            try {
                return Double.parseDouble(this.mValue);
            } catch (NumberFormatException unused) {
                return 0.0d;
            }
        }

        @Override // com.miui.maml.data.Expression
        public String evaluateStr() {
            return this.mValue;
        }
    }

    static class UnaryExpression extends Expression {
        private Expression mExp;
        private Ope mOpe;

        public UnaryExpression(Expression expression, Ope ope) {
            Ope ope2 = Ope.INVALID;
            this.mExp = expression;
            this.mOpe = ope;
            if (ope == Ope.INVALID) {
                Log.e(Expression.LOG_TAG, "UnaryExpression: invalid operator:" + ope);
            }
        }

        @Override // com.miui.maml.data.Expression
        public double evaluate() {
            int i = AnonymousClass1.$SwitchMap$com$miui$maml$data$Expression$Ope[this.mOpe.ordinal()];
            if (i == 1) {
                return 0.0d - this.mExp.evaluate();
            }
            if (i == 2) {
                return this.mExp.evaluate() <= 0.0d ? 1.0d : 0.0d;
            }
            if (i == 3) {
                return ~((int) this.mExp.evaluate());
            }
            Log.e(Expression.LOG_TAG, "fail to evalute UnaryExpression, invalid operator");
            return this.mExp.evaluate();
        }

        @Override // com.miui.maml.data.Expression
        public boolean isNull() {
            return this.mExp.isNull();
        }

        @Override // com.miui.maml.data.Expression
        public String evaluateStr() {
            return Utils.doubleToString(evaluate());
        }

        @Override // com.miui.maml.data.Expression
        public void accept(ExpressionVisitor expressionVisitor) {
            expressionVisitor.visit(this);
            this.mExp.accept(expressionVisitor);
        }
    }

    static class BinaryExpression extends Expression {
        private Expression mExp1;
        private Expression mExp2;
        private Ope mOpe;

        public BinaryExpression(Expression expression, Expression expression2, Ope ope) {
            Ope ope2 = Ope.INVALID;
            this.mExp1 = expression;
            this.mExp2 = expression2;
            this.mOpe = ope;
            if (ope == Ope.INVALID) {
                Log.e(Expression.LOG_TAG, "BinaryExpression: invalid operator:" + ope);
            }
        }

        @Override // com.miui.maml.data.Expression
        public double evaluate() {
            int i = AnonymousClass1.$SwitchMap$com$miui$maml$data$Expression$Ope[this.mOpe.ordinal()];
            if (i != 1) {
                switch (i) {
                    case 4:
                        return this.mExp1.evaluate() + this.mExp2.evaluate();
                    case 5:
                        return this.mExp1.evaluate() * this.mExp2.evaluate();
                    case 6:
                        return this.mExp1.evaluate() / this.mExp2.evaluate();
                    case 7:
                        return this.mExp1.evaluate() % this.mExp2.evaluate();
                    case 8:
                        return ((long) this.mExp1.evaluate()) & ((long) this.mExp2.evaluate());
                    case 9:
                        return ((long) this.mExp1.evaluate()) | ((long) this.mExp2.evaluate());
                    case 10:
                        return ((long) this.mExp1.evaluate()) ^ ((long) this.mExp2.evaluate());
                    case 11:
                        return ((long) this.mExp1.evaluate()) << ((int) this.mExp2.evaluate());
                    case 12:
                        return ((long) this.mExp1.evaluate()) >> ((int) this.mExp2.evaluate());
                    case 13:
                        return this.mExp1.evaluate() == this.mExp2.evaluate() ? 1.0d : 0.0d;
                    case 14:
                        return this.mExp1.evaluate() != this.mExp2.evaluate() ? 1.0d : 0.0d;
                    case 15:
                        return (this.mExp1.evaluate() <= 0.0d || this.mExp2.evaluate() <= 0.0d) ? 0.0d : 1.0d;
                    case 16:
                        return (this.mExp1.evaluate() > 0.0d || this.mExp2.evaluate() > 0.0d) ? 1.0d : 0.0d;
                    case 17:
                        return this.mExp1.evaluate() > this.mExp2.evaluate() ? 1.0d : 0.0d;
                    case 18:
                        return this.mExp1.evaluate() >= this.mExp2.evaluate() ? 1.0d : 0.0d;
                    case 19:
                        return this.mExp1.evaluate() < this.mExp2.evaluate() ? 1.0d : 0.0d;
                    case 20:
                        return this.mExp1.evaluate() <= this.mExp2.evaluate() ? 1.0d : 0.0d;
                    default:
                        Log.e(Expression.LOG_TAG, "fail to evalute BinaryExpression, invalid operator");
                        return 0.0d;
                }
            }
            return this.mExp1.evaluate() - this.mExp2.evaluate();
        }

        @Override // com.miui.maml.data.Expression
        public BigDecimal preciseEvaluate() {
            if (this.mOpe != Ope.INVALID) {
                BigDecimal bigDecimalPreciseEvaluate = this.mExp1.preciseEvaluate();
                BigDecimal bigDecimalPreciseEvaluate2 = this.mExp2.preciseEvaluate();
                if (bigDecimalPreciseEvaluate != null && bigDecimalPreciseEvaluate2 != null) {
                    int i = AnonymousClass1.$SwitchMap$com$miui$maml$data$Expression$Ope[this.mOpe.ordinal()];
                    if (i == 1) {
                        return bigDecimalPreciseEvaluate.subtract(bigDecimalPreciseEvaluate2);
                    }
                    if (i == 4) {
                        return bigDecimalPreciseEvaluate.add(bigDecimalPreciseEvaluate2);
                    }
                    if (i == 5) {
                        return bigDecimalPreciseEvaluate.multiply(bigDecimalPreciseEvaluate2);
                    }
                    if (i == 6) {
                        try {
                            return bigDecimalPreciseEvaluate.divide(bigDecimalPreciseEvaluate2, MathContext.DECIMAL128);
                        } catch (Exception unused) {
                            return null;
                        }
                    }
                    if (i == 7) {
                        try {
                            return bigDecimalPreciseEvaluate.remainder(bigDecimalPreciseEvaluate2);
                        } catch (Exception unused2) {
                            return null;
                        }
                    }
                }
            }
            Log.e(Expression.LOG_TAG, "fail to PRECISE evalute BinaryExpression, invalid operator");
            return null;
        }

        @Override // com.miui.maml.data.Expression
        public boolean isNull() {
            int i = AnonymousClass1.$SwitchMap$com$miui$maml$data$Expression$Ope[this.mOpe.ordinal()];
            if (i == 1 || i == 4) {
                return this.mExp1.isNull() && this.mExp2.isNull();
            }
            if (i != 5 && i != 6 && i != 7) {
                switch (i) {
                    case 13:
                    case 14:
                    case 15:
                    case 16:
                        break;
                    default:
                        return true;
                }
            }
            return this.mExp1.isNull() || this.mExp2.isNull();
        }

        @Override // com.miui.maml.data.Expression
        public String evaluateStr() {
            String strEvaluateStr = this.mExp1.evaluateStr();
            String strEvaluateStr2 = this.mExp2.evaluateStr();
            if (AnonymousClass1.$SwitchMap$com$miui$maml$data$Expression$Ope[this.mOpe.ordinal()] != 4) {
                Log.e(Expression.LOG_TAG, "fail to evalute string BinaryExpression, invalid operator");
                return null;
            }
            if (strEvaluateStr == null && strEvaluateStr2 == null) {
                return null;
            }
            if (strEvaluateStr == null) {
                return strEvaluateStr2;
            }
            return strEvaluateStr2 == null ? strEvaluateStr : strEvaluateStr + strEvaluateStr2;
        }

        @Override // com.miui.maml.data.Expression
        public void accept(ExpressionVisitor expressionVisitor) {
            expressionVisitor.visit(this);
            this.mExp1.accept(expressionVisitor);
            this.mExp2.accept(expressionVisitor);
        }
    }

    public static abstract class FunctionImpl {
        public int params;

        public abstract double evaluate(Expression[] expressionArr, Variables variables);

        public JSONArray evaluateJsonArray(Expression[] expressionArr, Variables variables) {
            return null;
        }

        public JSONObject evaluateJsonObject(Expression[] expressionArr, Variables variables) {
            return null;
        }

        public abstract String evaluateStr(Expression[] expressionArr, Variables variables);

        public void reset() {
        }

        public FunctionImpl(int i) {
            this.params = i;
        }
    }

    public static class FunctionExpression extends Expression {
        protected static HashMap<String, FunctionImpl> sFunMap = new HashMap<>();
        private FunctionImpl mFun;
        private String mFunName;
        private Expression[] mParaExps;
        private Variables mVariables;

        static {
            FunctionsLoader.load();
        }

        public FunctionExpression(Variables variables, Expression[] expressionArr, String str) throws Exception {
            this.mVariables = variables;
            this.mParaExps = expressionArr;
            this.mFunName = str;
            parseFunction(str);
        }

        public static void registerFunction(String str, FunctionImpl functionImpl) {
            if (sFunMap.put(str, functionImpl) != null) {
                Log.w(Expression.LOG_TAG, "duplicated function name registation: " + str);
            }
        }

        public static void removeFunction(String str, FunctionImpl functionImpl) {
            sFunMap.remove(str);
        }

        public static void resetFunctions() {
            Iterator<Map.Entry<String, FunctionImpl>> it = sFunMap.entrySet().iterator();
            while (it.hasNext()) {
                it.next().getValue().reset();
            }
        }

        private void parseFunction(String str) throws Exception {
            FunctionImpl functionImpl = sFunMap.get(str);
            Utils.asserts(functionImpl != null, "invalid function:" + str);
            this.mFun = functionImpl;
            Utils.asserts(this.mParaExps.length >= functionImpl.params, "parameters count not matching for function: " + str);
        }

        @Override // com.miui.maml.data.Expression
        public double evaluate() {
            return this.mFun.evaluate(this.mParaExps, this.mVariables);
        }

        @Override // com.miui.maml.data.Expression
        public String evaluateStr() {
            return this.mFun.evaluateStr(this.mParaExps, this.mVariables);
        }

        @Override // com.miui.maml.data.Expression
        public JSONObject evaluateJsonObject() {
            return this.mFun.evaluateJsonObject(this.mParaExps, this.mVariables);
        }

        @Override // com.miui.maml.data.Expression
        public JSONArray evaluateJsonArray() {
            return this.mFun.evaluateJsonArray(this.mParaExps, this.mVariables);
        }

        @Override // com.miui.maml.data.Expression
        public void accept(ExpressionVisitor expressionVisitor) {
            expressionVisitor.visit(this);
            int i = 0;
            while (true) {
                Expression[] expressionArr = this.mParaExps;
                if (i >= expressionArr.length) {
                    return;
                }
                expressionArr[i].accept(expressionVisitor);
                i++;
            }
        }

        public String getFunName() {
            return this.mFunName;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static boolean isFunctionCharRest(char c) {
        if (isFunctionCharStart(c) || c == '_') {
            return true;
        }
        return c >= '0' && c <= '9';
    }

    public static Expression[] buildMultiple(Variables variables, String str) {
        if (TextUtils.isEmpty(str)) {
            return null;
        }
        Expression[] expressionArrBuildMultipleInner = buildMultipleInner(variables, str);
        Expression[] expressionArr = new Expression[expressionArrBuildMultipleInner.length];
        for (int i = 0; i < expressionArrBuildMultipleInner.length; i++) {
            Expression expression = expressionArrBuildMultipleInner[i];
            if (expression == null || (expression instanceof NumberExpression) || (expression instanceof StringExpression)) {
                expressionArr[i] = expression;
            } else {
                expressionArr[i] = new RootExpression(variables, expression);
            }
        }
        return expressionArr;
    }

    private static Expression[] buildMultipleInner(Variables variables, String str) {
        ArrayList arrayList = new ArrayList();
        int i = 0;
        boolean z = false;
        int i2 = 0;
        for (int i3 = 0; i3 < str.length(); i3++) {
            char cCharAt = str.charAt(i3);
            if (!z) {
                if (cCharAt == ',' && i2 == 0) {
                    arrayList.add(buildInner(variables, str.substring(i, i3)));
                    i = i3 + 1;
                } else if (cCharAt == '(') {
                    i2++;
                } else if (cCharAt == ')') {
                    i2--;
                }
            }
            if (cCharAt == '\'') {
                z = !z;
            }
        }
        if (i < str.length()) {
            arrayList.add(buildInner(variables, str.substring(i)));
        }
        return (Expression[]) arrayList.toArray(new Expression[arrayList.size()]);
    }

    public static Expression build(Variables variables, String str) {
        Expression expressionBuildInner = buildInner(variables, str);
        if (expressionBuildInner == null) {
            return null;
        }
        return new RootExpression(variables, expressionBuildInner);
    }

    /* JADX WARN: Code duplicated, block: B:98:0x012d A[SYNTHETIC] */
    private static Expression buildInner(Variables variables, String str) {
        Expression numberVariableExpression;
        if (TextUtils.isEmpty(str.trim())) {
            return null;
        }
        Tokenizer tokenizer = new Tokenizer(str);
        Stack stack = new Stack();
        Stack stack2 = new Stack();
        Tokenizer.Token token = null;
        while (true) {
            Tokenizer.Token token2 = tokenizer.getToken();
            if (token2 != null) {
                switch (AnonymousClass1.$SwitchMap$com$miui$maml$data$Expression$Tokenizer$TokenType[token2.type.ordinal()]) {
                    case 1:
                    case 2:
                    case 3:
                    case 4:
                    case 5:
                    case 6:
                    case 7:
                    case 8:
                        switch (AnonymousClass1.$SwitchMap$com$miui$maml$data$Expression$Tokenizer$TokenType[token2.type.ordinal()]) {
                            case 1:
                                numberVariableExpression = new NumberVariableExpression(variables, token2.token);
                                break;
                            case 2:
                                numberVariableExpression = new StringVariableExpression(variables, token2.token);
                                break;
                            case 3:
                                numberVariableExpression = new JsonObjectVariableExpression(variables, token2.token);
                                break;
                            case 4:
                                numberVariableExpression = new JsonArrayVariableExpression(variables, token2.token);
                                break;
                            case 5:
                                boolean z = !stack.empty() && ((Tokenizer.Token) stack.peek()).op == Ope.MIN && ((Tokenizer.Token) stack.peek()).info.unary;
                                numberVariableExpression = new NumberExpression((z ? "-" : "") + token2.token);
                                if (z) {
                                    stack.pop();
                                }
                                break;
                            case 6:
                                numberVariableExpression = new StringExpression(token2.token);
                                break;
                            case 7:
                                numberVariableExpression = buildBracket(variables, token2, stack);
                                if (numberVariableExpression == null) {
                                    return null;
                                }
                                break;
                            case 8:
                                if (stack2.size() < 1) {
                                    Log.e(LOG_TAG, "fail to buid: no array name before []:" + str);
                                    return null;
                                }
                                Expression expression = (Expression) stack2.pop();
                                if (expression instanceof VariableExpression) {
                                    Expression expressionBuildInner = buildInner(variables, token2.token);
                                    if (expressionBuildInner == null) {
                                        Log.e(LOG_TAG, "fail to buid: no index expression in []:" + str);
                                        return null;
                                    }
                                    String name = ((VariableExpression) expression).getName();
                                    if (expression instanceof NumberVariableExpression) {
                                        numberVariableExpression = new NumberArrayVariableExpression(variables, name, expressionBuildInner);
                                    } else if (expression instanceof StringVariableExpression) {
                                        numberVariableExpression = new StringArrayVariableExpression(variables, name, expressionBuildInner);
                                    }
                                    if (numberVariableExpression == null) {
                                        return null;
                                    }
                                } else {
                                    Log.e(LOG_TAG, "fail to buid: the expression before [] is not a variable:" + str);
                                }
                                numberVariableExpression = null;
                                if (numberVariableExpression == null) {
                                    return null;
                                }
                                break;
                            default:
                                numberVariableExpression = null;
                                break;
                        }
                        while (!stack.empty() && ((Tokenizer.Token) stack.peek()).info != null && ((Tokenizer.Token) stack.peek()).info.unary) {
                            numberVariableExpression = new UnaryExpression(numberVariableExpression, ((Tokenizer.Token) stack.pop()).op);
                        }
                        stack2.push(numberVariableExpression);
                        break;
                    case 9:
                        if (token2.info.participants == 1 && (token == null || token.type == Tokenizer.TokenType.OPE)) {
                            token2.info.unary = true;
                            stack.push(token2);
                        } else {
                            while (stack.size() > 0 && ((Tokenizer.Token) stack.peek()).type == Tokenizer.TokenType.OPE && ((Tokenizer.Token) stack.peek()).info.priority - token2.info.priority <= 0) {
                                if (stack2.size() < 2) {
                                    Log.e(LOG_TAG, "fail to buid: invalid operator position:" + str);
                                    return null;
                                }
                                stack2.push(new BinaryExpression((Expression) stack2.pop(), (Expression) stack2.pop(), ((Tokenizer.Token) stack.pop()).op));
                            }
                            stack.push(token2);
                        }
                        break;
                    case 10:
                        stack.push(token2);
                        break;
                }
                token = token2;
            } else {
                if (stack2.size() != stack.size() + 1) {
                    Log.e(LOG_TAG, "fail to buid: invalid expression:" + str);
                    return null;
                }
                Expression binaryExpression = (Expression) stack2.pop();
                while (stack.size() > 0) {
                    binaryExpression = new BinaryExpression((Expression) stack2.pop(), binaryExpression, ((Tokenizer.Token) stack.pop()).op);
                }
                return binaryExpression;
            }
        }
    }

    /* JADX INFO: renamed from: com.miui.maml.data.Expression$1, reason: invalid class name */
    static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$com$miui$maml$data$Expression$Ope;
        static final /* synthetic */ int[] $SwitchMap$com$miui$maml$data$Expression$Tokenizer$TokenType;

        static {
            int[] iArr = new int[Tokenizer.TokenType.values().length];
            $SwitchMap$com$miui$maml$data$Expression$Tokenizer$TokenType = iArr;
            try {
                iArr[Tokenizer.TokenType.VAR_NUM.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$com$miui$maml$data$Expression$Tokenizer$TokenType[Tokenizer.TokenType.VAR_STR.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                $SwitchMap$com$miui$maml$data$Expression$Tokenizer$TokenType[Tokenizer.TokenType.VAR_JSON_OBJ.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                $SwitchMap$com$miui$maml$data$Expression$Tokenizer$TokenType[Tokenizer.TokenType.VAR_JSON_ARR.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
            try {
                $SwitchMap$com$miui$maml$data$Expression$Tokenizer$TokenType[Tokenizer.TokenType.NUM.ordinal()] = 5;
            } catch (NoSuchFieldError unused5) {
            }
            try {
                $SwitchMap$com$miui$maml$data$Expression$Tokenizer$TokenType[Tokenizer.TokenType.STR.ordinal()] = 6;
            } catch (NoSuchFieldError unused6) {
            }
            try {
                $SwitchMap$com$miui$maml$data$Expression$Tokenizer$TokenType[Tokenizer.TokenType.BRACKET_ROUND.ordinal()] = 7;
            } catch (NoSuchFieldError unused7) {
            }
            try {
                $SwitchMap$com$miui$maml$data$Expression$Tokenizer$TokenType[Tokenizer.TokenType.BRACKET_SQUARE.ordinal()] = 8;
            } catch (NoSuchFieldError unused8) {
            }
            try {
                $SwitchMap$com$miui$maml$data$Expression$Tokenizer$TokenType[Tokenizer.TokenType.OPE.ordinal()] = 9;
            } catch (NoSuchFieldError unused9) {
            }
            try {
                $SwitchMap$com$miui$maml$data$Expression$Tokenizer$TokenType[Tokenizer.TokenType.FUN.ordinal()] = 10;
            } catch (NoSuchFieldError unused10) {
            }
            int[] iArr2 = new int[Ope.values().length];
            $SwitchMap$com$miui$maml$data$Expression$Ope = iArr2;
            try {
                iArr2[Ope.MIN.ordinal()] = 1;
            } catch (NoSuchFieldError unused11) {
            }
            try {
                $SwitchMap$com$miui$maml$data$Expression$Ope[Ope.NOT.ordinal()] = 2;
            } catch (NoSuchFieldError unused12) {
            }
            try {
                $SwitchMap$com$miui$maml$data$Expression$Ope[Ope.BIT_NOT.ordinal()] = 3;
            } catch (NoSuchFieldError unused13) {
            }
            try {
                $SwitchMap$com$miui$maml$data$Expression$Ope[Ope.ADD.ordinal()] = 4;
            } catch (NoSuchFieldError unused14) {
            }
            try {
                $SwitchMap$com$miui$maml$data$Expression$Ope[Ope.MUL.ordinal()] = 5;
            } catch (NoSuchFieldError unused15) {
            }
            try {
                $SwitchMap$com$miui$maml$data$Expression$Ope[Ope.DIV.ordinal()] = 6;
            } catch (NoSuchFieldError unused16) {
            }
            try {
                $SwitchMap$com$miui$maml$data$Expression$Ope[Ope.MOD.ordinal()] = 7;
            } catch (NoSuchFieldError unused17) {
            }
            try {
                $SwitchMap$com$miui$maml$data$Expression$Ope[Ope.BIT_AND.ordinal()] = 8;
            } catch (NoSuchFieldError unused18) {
            }
            try {
                $SwitchMap$com$miui$maml$data$Expression$Ope[Ope.BIT_OR.ordinal()] = 9;
            } catch (NoSuchFieldError unused19) {
            }
            try {
                $SwitchMap$com$miui$maml$data$Expression$Ope[Ope.BIT_XOR.ordinal()] = 10;
            } catch (NoSuchFieldError unused20) {
            }
            try {
                $SwitchMap$com$miui$maml$data$Expression$Ope[Ope.BIT_LSHIFT.ordinal()] = 11;
            } catch (NoSuchFieldError unused21) {
            }
            try {
                $SwitchMap$com$miui$maml$data$Expression$Ope[Ope.BIT_RSHIFT.ordinal()] = 12;
            } catch (NoSuchFieldError unused22) {
            }
            try {
                $SwitchMap$com$miui$maml$data$Expression$Ope[Ope.EQ.ordinal()] = 13;
            } catch (NoSuchFieldError unused23) {
            }
            try {
                $SwitchMap$com$miui$maml$data$Expression$Ope[Ope.NEQ.ordinal()] = 14;
            } catch (NoSuchFieldError unused24) {
            }
            try {
                $SwitchMap$com$miui$maml$data$Expression$Ope[Ope.AND.ordinal()] = 15;
            } catch (NoSuchFieldError unused25) {
            }
            try {
                $SwitchMap$com$miui$maml$data$Expression$Ope[Ope.OR.ordinal()] = 16;
            } catch (NoSuchFieldError unused26) {
            }
            try {
                $SwitchMap$com$miui$maml$data$Expression$Ope[Ope.GT.ordinal()] = 17;
            } catch (NoSuchFieldError unused27) {
            }
            try {
                $SwitchMap$com$miui$maml$data$Expression$Ope[Ope.GE.ordinal()] = 18;
            } catch (NoSuchFieldError unused28) {
            }
            try {
                $SwitchMap$com$miui$maml$data$Expression$Ope[Ope.LT.ordinal()] = 19;
            } catch (NoSuchFieldError unused29) {
            }
            try {
                $SwitchMap$com$miui$maml$data$Expression$Ope[Ope.LE.ordinal()] = 20;
            } catch (NoSuchFieldError unused30) {
            }
        }
    }

    private static Expression buildBracket(Variables variables, Tokenizer.Token token, Stack<Tokenizer.Token> stack) {
        Expression[] expressionArrBuildMultipleInner = buildMultipleInner(variables, token.token);
        if (!checkParams(expressionArrBuildMultipleInner)) {
            Log.e(LOG_TAG, "invalid expressions: " + token.token);
            return null;
        }
        try {
            if (!stack.isEmpty() && stack.peek().type == Tokenizer.TokenType.FUN) {
                return new FunctionExpression(variables, expressionArrBuildMultipleInner, stack.pop().token);
            }
            if (expressionArrBuildMultipleInner.length == 1) {
                return expressionArrBuildMultipleInner[0];
            }
            Log.e(LOG_TAG, "fail to buid: multiple expressions in brackets, but seems no function presents:" + token.token);
            return null;
        } catch (Exception e) {
            e.printStackTrace();
            Log.e(LOG_TAG, e.toString());
        }
    }

    private static boolean checkParams(Expression[] expressionArr) {
        for (Expression expression : expressionArr) {
            if (expression == null) {
                return false;
            }
        }
        return true;
    }

    public BigDecimal preciseEvaluate() {
        try {
            return BigDecimal.valueOf(evaluate());
        } catch (NumberFormatException unused) {
            return null;
        }
    }

    public void accept(ExpressionVisitor expressionVisitor) {
        expressionVisitor.visit(this);
    }
}
