package com.miui.handwriting.common.ai.data.requestparam;

import ch.qos.logback.core.CoreConstants;
import com.market.sdk.reflect.ReflectUtilsForMiui;
import com.xiaomi.mirror.RemoteDeviceInfo;
import java.util.List;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: ParamUploadMetaDataForOS1.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u0000,\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010 \n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\b\t\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0002\b\u0086\b\u0018\u00002\u00020\u0001B#\u0012\f\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003\u0012\f\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00060\u0003¢\u0006\u0004\b\u0007\u0010\bJ\u000f\u0010\f\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003HÆ\u0003J\u000f\u0010\r\u001a\b\u0012\u0004\u0012\u00020\u00060\u0003HÆ\u0003J)\u0010\u000e\u001a\u00020\u00002\u000e\b\u0002\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u00032\u000e\b\u0002\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00060\u0003HÆ\u0001J\u0013\u0010\u000f\u001a\u00020\u00102\b\u0010\u0011\u001a\u0004\u0018\u00010\u0001HÖ\u0003J\t\u0010\u0012\u001a\u00020\u0013HÖ\u0001J\t\u0010\u0014\u001a\u00020\u0004HÖ\u0001R\u0017\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\nR\u0017\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00060\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\n¨\u0006\u0015"}, d2 = {"Lcom/miui/handwriting/common/ai/data/requestparam/ParamUploadMetaDataForOS1;", "", "algorithm", "", "", "imageInfo", "Lcom/miui/handwriting/common/ai/data/requestparam/ParamImageInfo;", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "(Ljava/util/List;Ljava/util/List;)V", "getAlgorithm", "()Ljava/util/List;", "getImageInfo", "component1", "component2", "copy", "equals", "", RemoteDeviceInfo.MANUFACTURER_OTHER, "hashCode", "", "toString", "libHandWritingCommon_release"}, k = 1, mv = {2, 2, 0}, xi = 48)
public final /* data */ class ParamUploadMetaDataForOS1 {
    private final List<String> algorithm;
    private final List<ParamImageInfo> imageInfo;

    /* JADX WARN: Multi-variable type inference failed */
    public static /* synthetic */ ParamUploadMetaDataForOS1 copy$default(ParamUploadMetaDataForOS1 paramUploadMetaDataForOS1, List list, List list2, int i, Object obj) {
        if ((i & 1) != 0) {
            list = paramUploadMetaDataForOS1.algorithm;
        }
        if ((i & 2) != 0) {
            list2 = paramUploadMetaDataForOS1.imageInfo;
        }
        return paramUploadMetaDataForOS1.copy(list, list2);
    }

    public final List<String> component1() {
        return this.algorithm;
    }

    public final List<ParamImageInfo> component2() {
        return this.imageInfo;
    }

    public final ParamUploadMetaDataForOS1 copy(List<String> algorithm, List<ParamImageInfo> imageInfo) {
        Intrinsics.checkNotNullParameter(algorithm, "algorithm");
        Intrinsics.checkNotNullParameter(imageInfo, "imageInfo");
        return new ParamUploadMetaDataForOS1(algorithm, imageInfo);
    }

    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof ParamUploadMetaDataForOS1)) {
            return false;
        }
        ParamUploadMetaDataForOS1 paramUploadMetaDataForOS1 = (ParamUploadMetaDataForOS1) other;
        return Intrinsics.areEqual(this.algorithm, paramUploadMetaDataForOS1.algorithm) && Intrinsics.areEqual(this.imageInfo, paramUploadMetaDataForOS1.imageInfo);
    }

    public int hashCode() {
        return (this.algorithm.hashCode() * 31) + this.imageInfo.hashCode();
    }

    public String toString() {
        return "ParamUploadMetaDataForOS1(algorithm=" + this.algorithm + ", imageInfo=" + this.imageInfo + CoreConstants.RIGHT_PARENTHESIS_CHAR;
    }

    public ParamUploadMetaDataForOS1(List<String> algorithm, List<ParamImageInfo> imageInfo) {
        Intrinsics.checkNotNullParameter(algorithm, "algorithm");
        Intrinsics.checkNotNullParameter(imageInfo, "imageInfo");
        this.algorithm = algorithm;
        this.imageInfo = imageInfo;
    }

    public final List<String> getAlgorithm() {
        return this.algorithm;
    }

    public final List<ParamImageInfo> getImageInfo() {
        return this.imageInfo;
    }
}
