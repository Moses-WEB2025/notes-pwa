package com.miui.maml.data;

import java.util.HashSet;
import org.json.JSONArray;
import org.json.JSONObject;

/* JADX INFO: loaded from: classes3.dex */
public class RootExpression extends Expression {
    public static final String LOG_TAG = "RootExression";
    private boolean mAlwaysEvaluate;
    private double mDoubleValue;
    private Expression mExp;
    private JSONArray mJsonArrayValue;
    private JSONObject mJsonObjectValue;
    private String mStringValue;
    private Variables mVars;
    private VarVersion[] mVersions;
    private HashSet<VarVersion> mVersionSet = new HashSet<>();
    private boolean mIsNumInit = false;
    private boolean mIsStrInit = false;
    private boolean mIsJsonObjectInit = false;
    private boolean mIsJsonArrayInit = false;
    private VarVersionVisitor mVarVersionVisitor = null;

    @Override // com.miui.maml.data.Expression
    public void accept(ExpressionVisitor expressionVisitor) {
    }

    public static class VarVersion {
        int mIndex;
        private boolean mIsNumber;
        int mVersion;

        public VarVersion(int i, int i2, boolean z) {
            this.mIndex = i;
            this.mVersion = i2;
            this.mIsNumber = z;
        }

        public int hashCode() {
            boolean z = this.mIsNumber;
            int i = this.mIndex;
            return z ? i : (-i) - 1;
        }

        public boolean equals(Object obj) {
            if (obj instanceof VarVersion) {
                VarVersion varVersion = (VarVersion) obj;
                if (varVersion.mIsNumber == this.mIsNumber && varVersion.mIndex == this.mIndex) {
                    return true;
                }
            }
            return false;
        }

        public int getVer(Variables variables) {
            return variables.getVer(this.mIndex, this.mIsNumber);
        }
    }

    private static class VarVersionVisitor extends ExpressionVisitor {
        private RootExpression mRoot;

        public VarVersionVisitor(RootExpression rootExpression) {
            this.mRoot = rootExpression;
        }

        @Override // com.miui.maml.data.ExpressionVisitor
        public void visit(Expression expression) {
            if (expression instanceof Expression.VariableExpression) {
                Expression.VariableExpression variableExpression = (Expression.VariableExpression) expression;
                variableExpression.evaluate();
                this.mRoot.addVarVersion(new VarVersion(variableExpression.getIndex(), variableExpression.getVersion(), expression instanceof Expression.NumberVariableExpression));
            } else if (expression instanceof Expression.FunctionExpression) {
                String funName = ((Expression.FunctionExpression) expression).getFunName();
                if ("rand".equals(funName) || "eval".equals(funName) || "preciseeval".equals(funName) || "newJsonObject".equals(funName) || "newJsonArray".equals(funName)) {
                    this.mRoot.mAlwaysEvaluate = true;
                }
            }
        }
    }

    public RootExpression(Variables variables, Expression expression) {
        this.mVars = variables;
        this.mExp = expression;
    }

    public void addVarVersion(VarVersion varVersion) {
        this.mVersionSet.add(varVersion);
    }

    @Override // com.miui.maml.data.Expression
    public double evaluate() {
        int ver;
        boolean z = true;
        if (!this.mIsNumInit) {
            this.mDoubleValue = this.mExp.evaluate();
            if (this.mVarVersionVisitor == null) {
                VarVersionVisitor varVersionVisitor = new VarVersionVisitor(this);
                this.mVarVersionVisitor = varVersionVisitor;
                this.mExp.accept(varVersionVisitor);
                if (this.mVersionSet.size() <= 0) {
                    this.mVersions = null;
                } else {
                    VarVersion[] varVersionArr = new VarVersion[this.mVersionSet.size()];
                    this.mVersions = varVersionArr;
                    this.mVersionSet.toArray(varVersionArr);
                }
            }
            this.mIsNumInit = true;
        } else {
            if (!this.mAlwaysEvaluate) {
                int i = 0;
                if (this.mVersions != null) {
                    boolean z2 = false;
                    while (true) {
                        VarVersion[] varVersionArr2 = this.mVersions;
                        if (i >= varVersionArr2.length) {
                            break;
                        }
                        VarVersion varVersion = varVersionArr2[i];
                        if (varVersion != null && varVersion.mVersion != (ver = varVersion.getVer(this.mVars))) {
                            varVersion.mVersion = ver;
                            z2 = true;
                        }
                        i++;
                    }
                    z = z2;
                } else {
                    z = false;
                }
            }
            if (z) {
                this.mDoubleValue = this.mExp.evaluate();
            }
        }
        return this.mDoubleValue;
    }

    @Override // com.miui.maml.data.Expression
    public String evaluateStr() {
        int ver;
        boolean z = true;
        if (!this.mIsStrInit) {
            this.mStringValue = this.mExp.evaluateStr();
            if (this.mVarVersionVisitor == null) {
                VarVersionVisitor varVersionVisitor = new VarVersionVisitor(this);
                this.mVarVersionVisitor = varVersionVisitor;
                this.mExp.accept(varVersionVisitor);
                VarVersion[] varVersionArr = new VarVersion[this.mVersionSet.size()];
                this.mVersions = varVersionArr;
                this.mVersionSet.toArray(varVersionArr);
            }
            this.mIsStrInit = true;
        } else {
            if (!this.mAlwaysEvaluate) {
                int i = 0;
                if (this.mVersions != null) {
                    boolean z2 = false;
                    while (true) {
                        VarVersion[] varVersionArr2 = this.mVersions;
                        if (i >= varVersionArr2.length) {
                            break;
                        }
                        VarVersion varVersion = varVersionArr2[i];
                        if (varVersion != null && varVersion.mVersion != (ver = varVersion.getVer(this.mVars))) {
                            varVersion.mVersion = ver;
                            z2 = true;
                        }
                        i++;
                    }
                    z = z2;
                } else {
                    z = false;
                }
            }
            if (z) {
                this.mStringValue = this.mExp.evaluateStr();
            }
        }
        return this.mStringValue;
    }

    @Override // com.miui.maml.data.Expression
    public JSONObject evaluateJsonObject() {
        int ver;
        boolean z = true;
        if (!this.mIsJsonObjectInit) {
            this.mJsonObjectValue = this.mExp.evaluateJsonObject();
            if (this.mVarVersionVisitor == null) {
                VarVersionVisitor varVersionVisitor = new VarVersionVisitor(this);
                this.mVarVersionVisitor = varVersionVisitor;
                this.mExp.accept(varVersionVisitor);
                VarVersion[] varVersionArr = new VarVersion[this.mVersionSet.size()];
                this.mVersions = varVersionArr;
                this.mVersionSet.toArray(varVersionArr);
            }
            this.mIsJsonObjectInit = true;
        } else {
            if (!this.mAlwaysEvaluate) {
                int i = 0;
                if (this.mVersions != null) {
                    boolean z2 = false;
                    while (true) {
                        VarVersion[] varVersionArr2 = this.mVersions;
                        if (i >= varVersionArr2.length) {
                            break;
                        }
                        VarVersion varVersion = varVersionArr2[i];
                        if (varVersion != null && varVersion.mVersion != (ver = varVersion.getVer(this.mVars))) {
                            varVersion.mVersion = ver;
                            z2 = true;
                        }
                        i++;
                    }
                    z = z2;
                } else {
                    z = false;
                }
            }
            if (z) {
                this.mJsonObjectValue = this.mExp.evaluateJsonObject();
            }
        }
        return this.mJsonObjectValue;
    }

    @Override // com.miui.maml.data.Expression
    public JSONArray evaluateJsonArray() {
        int ver;
        boolean z = true;
        if (!this.mIsJsonArrayInit) {
            this.mJsonArrayValue = this.mExp.evaluateJsonArray();
            if (this.mVarVersionVisitor == null) {
                VarVersionVisitor varVersionVisitor = new VarVersionVisitor(this);
                this.mVarVersionVisitor = varVersionVisitor;
                this.mExp.accept(varVersionVisitor);
                VarVersion[] varVersionArr = new VarVersion[this.mVersionSet.size()];
                this.mVersions = varVersionArr;
                this.mVersionSet.toArray(varVersionArr);
            }
            this.mIsJsonArrayInit = true;
        } else {
            if (!this.mAlwaysEvaluate) {
                int i = 0;
                if (this.mVersions != null) {
                    boolean z2 = false;
                    while (true) {
                        VarVersion[] varVersionArr2 = this.mVersions;
                        if (i >= varVersionArr2.length) {
                            break;
                        }
                        VarVersion varVersion = varVersionArr2[i];
                        if (varVersion != null && varVersion.mVersion != (ver = varVersion.getVer(this.mVars))) {
                            varVersion.mVersion = ver;
                            z2 = true;
                        }
                        i++;
                    }
                    z = z2;
                } else {
                    z = false;
                }
            }
            if (z) {
                this.mJsonArrayValue = this.mExp.evaluateJsonArray();
            }
        }
        return this.mJsonArrayValue;
    }

    @Override // com.miui.maml.data.Expression
    public boolean isNull() {
        return this.mExp.isNull();
    }
}
