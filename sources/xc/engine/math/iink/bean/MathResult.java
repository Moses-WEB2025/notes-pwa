package xc.engine.math.iink.bean;

import com.market.sdk.reflect.ReflectUtilsForMiui;
import com.xiaomi.mirror.RemoteDeviceInfo;
import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: MathResult.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000*\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u001a\n\u0002\u0010\b\n\u0002\b\u0002\b\u0086\b\u0018\u00002\u00020\u0001BE\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0003\u0012\u0006\u0010\u0005\u001a\u00020\u0003\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u0012\u0006\u0010\b\u001a\u00020\u0007\u0012\b\b\u0002\u0010\t\u001a\u00020\n\u0012\n\b\u0002\u0010\u000b\u001a\u0004\u0018\u00010\u0003¢\u0006\u0004\b\f\u0010\rJ\t\u0010\u001a\u001a\u00020\u0003HÆ\u0003J\t\u0010\u001b\u001a\u00020\u0003HÆ\u0003J\t\u0010\u001c\u001a\u00020\u0003HÆ\u0003J\t\u0010\u001d\u001a\u00020\u0007HÆ\u0003J\t\u0010\u001e\u001a\u00020\u0007HÆ\u0003J\t\u0010\u001f\u001a\u00020\nHÆ\u0003J\u000b\u0010 \u001a\u0004\u0018\u00010\u0003HÆ\u0003JQ\u0010!\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u00032\b\b\u0002\u0010\u0005\u001a\u00020\u00032\b\b\u0002\u0010\u0006\u001a\u00020\u00072\b\b\u0002\u0010\b\u001a\u00020\u00072\b\b\u0002\u0010\t\u001a\u00020\n2\n\b\u0002\u0010\u000b\u001a\u0004\u0018\u00010\u0003HÆ\u0001J\u0013\u0010\"\u001a\u00020\n2\b\u0010#\u001a\u0004\u0018\u00010\u0001HÖ\u0003J\t\u0010$\u001a\u00020%HÖ\u0001J\t\u0010&\u001a\u00020\u0003HÖ\u0001R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u000e\u0010\u000fR\u0011\u0010\u0004\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0010\u0010\u000fR\u0011\u0010\u0005\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0011\u0010\u000fR\u001a\u0010\u0006\u001a\u00020\u0007X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0012\u0010\u0013\"\u0004\b\u0014\u0010\u0015R\u0011\u0010\b\u001a\u00020\u0007¢\u0006\b\n\u0000\u001a\u0004\b\u0016\u0010\u0013R\u0011\u0010\t\u001a\u00020\n¢\u0006\b\n\u0000\u001a\u0004\b\u0017\u0010\u0018R\u0013\u0010\u000b\u001a\u0004\u0018\u00010\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0019\u0010\u000f¨\u0006'"}, d2 = {"Lxc/engine/math/iink/bean/MathResult;", "", "mathBlockId", "", "expression", "result", "boundingBox", "Lxc/engine/math/iink/bean/BoundingBox;", "formulaBox", "shouldClear", "", "formulaLatex", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Lxc/engine/math/iink/bean/BoundingBox;Lxc/engine/math/iink/bean/BoundingBox;ZLjava/lang/String;)V", "getMathBlockId", "()Ljava/lang/String;", "getExpression", "getResult", "getBoundingBox", "()Lxc/engine/math/iink/bean/BoundingBox;", "setBoundingBox", "(Lxc/engine/math/iink/bean/BoundingBox;)V", "getFormulaBox", "getShouldClear", "()Z", "getFormulaLatex", "component1", "component2", "component3", "component4", "component5", "component6", "component7", "copy", "equals", RemoteDeviceInfo.MANUFACTURER_OTHER, "hashCode", "", "toString", "libXCEngine_release"}, k = 1, mv = {2, 2, 0}, xi = 48)
public final /* data */ class MathResult {
    private BoundingBox boundingBox;
    private final String expression;
    private final BoundingBox formulaBox;
    private final String formulaLatex;
    private final String mathBlockId;
    private final String result;
    private final boolean shouldClear;

    public static /* synthetic */ MathResult copy$default(MathResult mathResult, String str, String str2, String str3, BoundingBox boundingBox, BoundingBox boundingBox2, boolean z, String str4, int i, Object obj) {
        if ((i & 1) != 0) {
            str = mathResult.mathBlockId;
        }
        if ((i & 2) != 0) {
            str2 = mathResult.expression;
        }
        if ((i & 4) != 0) {
            str3 = mathResult.result;
        }
        if ((i & 8) != 0) {
            boundingBox = mathResult.boundingBox;
        }
        if ((i & 16) != 0) {
            boundingBox2 = mathResult.formulaBox;
        }
        if ((i & 32) != 0) {
            z = mathResult.shouldClear;
        }
        if ((i & 64) != 0) {
            str4 = mathResult.formulaLatex;
        }
        boolean z2 = z;
        String str5 = str4;
        BoundingBox boundingBox3 = boundingBox2;
        String str6 = str3;
        return mathResult.copy(str, str2, str6, boundingBox, boundingBox3, z2, str5);
    }

    /* JADX INFO: renamed from: component1, reason: from getter */
    public final String getMathBlockId() {
        return this.mathBlockId;
    }

    /* JADX INFO: renamed from: component2, reason: from getter */
    public final String getExpression() {
        return this.expression;
    }

    /* JADX INFO: renamed from: component3, reason: from getter */
    public final String getResult() {
        return this.result;
    }

    /* JADX INFO: renamed from: component4, reason: from getter */
    public final BoundingBox getBoundingBox() {
        return this.boundingBox;
    }

    /* JADX INFO: renamed from: component5, reason: from getter */
    public final BoundingBox getFormulaBox() {
        return this.formulaBox;
    }

    /* JADX INFO: renamed from: component6, reason: from getter */
    public final boolean getShouldClear() {
        return this.shouldClear;
    }

    /* JADX INFO: renamed from: component7, reason: from getter */
    public final String getFormulaLatex() {
        return this.formulaLatex;
    }

    public final MathResult copy(String mathBlockId, String expression, String result, BoundingBox boundingBox, BoundingBox formulaBox, boolean shouldClear, String formulaLatex) {
        Intrinsics.checkNotNullParameter(mathBlockId, "mathBlockId");
        Intrinsics.checkNotNullParameter(expression, "expression");
        Intrinsics.checkNotNullParameter(result, "result");
        Intrinsics.checkNotNullParameter(boundingBox, "boundingBox");
        Intrinsics.checkNotNullParameter(formulaBox, "formulaBox");
        return new MathResult(mathBlockId, expression, result, boundingBox, formulaBox, shouldClear, formulaLatex);
    }

    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof MathResult)) {
            return false;
        }
        MathResult mathResult = (MathResult) other;
        return Intrinsics.areEqual(this.mathBlockId, mathResult.mathBlockId) && Intrinsics.areEqual(this.expression, mathResult.expression) && Intrinsics.areEqual(this.result, mathResult.result) && Intrinsics.areEqual(this.boundingBox, mathResult.boundingBox) && Intrinsics.areEqual(this.formulaBox, mathResult.formulaBox) && this.shouldClear == mathResult.shouldClear && Intrinsics.areEqual(this.formulaLatex, mathResult.formulaLatex);
    }

    public int hashCode() {
        int iHashCode = ((((((((((this.mathBlockId.hashCode() * 31) + this.expression.hashCode()) * 31) + this.result.hashCode()) * 31) + this.boundingBox.hashCode()) * 31) + this.formulaBox.hashCode()) * 31) + Boolean.hashCode(this.shouldClear)) * 31;
        String str = this.formulaLatex;
        return iHashCode + (str == null ? 0 : str.hashCode());
    }

    public String toString() {
        return "MathResult(mathBlockId=" + this.mathBlockId + ", expression=" + this.expression + ", result=" + this.result + ", boundingBox=" + this.boundingBox + ", formulaBox=" + this.formulaBox + ", shouldClear=" + this.shouldClear + ", formulaLatex=" + this.formulaLatex + ")";
    }

    public MathResult(String mathBlockId, String expression, String result, BoundingBox boundingBox, BoundingBox formulaBox, boolean z, String str) {
        Intrinsics.checkNotNullParameter(mathBlockId, "mathBlockId");
        Intrinsics.checkNotNullParameter(expression, "expression");
        Intrinsics.checkNotNullParameter(result, "result");
        Intrinsics.checkNotNullParameter(boundingBox, "boundingBox");
        Intrinsics.checkNotNullParameter(formulaBox, "formulaBox");
        this.mathBlockId = mathBlockId;
        this.expression = expression;
        this.result = result;
        this.boundingBox = boundingBox;
        this.formulaBox = formulaBox;
        this.shouldClear = z;
        this.formulaLatex = str;
    }

    public /* synthetic */ MathResult(String str, String str2, String str3, BoundingBox boundingBox, BoundingBox boundingBox2, boolean z, String str4, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(str, str2, str3, boundingBox, boundingBox2, (i & 32) != 0 ? false : z, (i & 64) != 0 ? null : str4);
    }

    public final String getMathBlockId() {
        return this.mathBlockId;
    }

    public final String getExpression() {
        return this.expression;
    }

    public final String getResult() {
        return this.result;
    }

    public final BoundingBox getBoundingBox() {
        return this.boundingBox;
    }

    public final void setBoundingBox(BoundingBox boundingBox) {
        Intrinsics.checkNotNullParameter(boundingBox, "<set-?>");
        this.boundingBox = boundingBox;
    }

    public final BoundingBox getFormulaBox() {
        return this.formulaBox;
    }

    public final boolean getShouldClear() {
        return this.shouldClear;
    }

    public final String getFormulaLatex() {
        return this.formulaLatex;
    }
}
