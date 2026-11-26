package kotlin.reflect.jvm.internal.impl.load.java.typeEnhancement;

import java.util.ArrayList;
import java.util.List;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: predefinedEnhancementInfo.kt */
/* JADX INFO: loaded from: classes5.dex */
public final class PredefinedFunctionEnhancementInfo {
    private final String errorsSinceLanguageVersion;
    private final List<TypeEnhancementInfo> parametersInfo;
    private final TypeEnhancementInfo returnTypeInfo;
    private final PredefinedFunctionEnhancementInfo warningModeClone;

    public PredefinedFunctionEnhancementInfo() {
        this(null, null, null, 7, null);
    }

    public PredefinedFunctionEnhancementInfo(TypeEnhancementInfo typeEnhancementInfo, List<TypeEnhancementInfo> parametersInfo, String str) {
        Intrinsics.checkNotNullParameter(parametersInfo, "parametersInfo");
        this.returnTypeInfo = typeEnhancementInfo;
        this.parametersInfo = parametersInfo;
        this.errorsSinceLanguageVersion = str;
        PredefinedFunctionEnhancementInfo predefinedFunctionEnhancementInfo = null;
        if (str != null) {
            TypeEnhancementInfo typeEnhancementInfoCopyForWarnings = typeEnhancementInfo != null ? typeEnhancementInfo.copyForWarnings() : null;
            List<TypeEnhancementInfo> list = parametersInfo;
            ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(list, 10));
            for (TypeEnhancementInfo typeEnhancementInfo2 : list) {
                arrayList.add(typeEnhancementInfo2 != null ? typeEnhancementInfo2.copyForWarnings() : null);
            }
            predefinedFunctionEnhancementInfo = new PredefinedFunctionEnhancementInfo(typeEnhancementInfoCopyForWarnings, arrayList, null);
        }
        this.warningModeClone = predefinedFunctionEnhancementInfo;
    }

    public final TypeEnhancementInfo getReturnTypeInfo() {
        return this.returnTypeInfo;
    }

    public /* synthetic */ PredefinedFunctionEnhancementInfo(TypeEnhancementInfo typeEnhancementInfo, List list, String str, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this((i & 1) != 0 ? null : typeEnhancementInfo, (i & 2) != 0 ? CollectionsKt.emptyList() : list, (i & 4) != 0 ? null : str);
    }

    public final List<TypeEnhancementInfo> getParametersInfo() {
        return this.parametersInfo;
    }

    public final String getErrorsSinceLanguageVersion() {
        return this.errorsSinceLanguageVersion;
    }

    public final PredefinedFunctionEnhancementInfo getWarningModeClone() {
        return this.warningModeClone;
    }
}
