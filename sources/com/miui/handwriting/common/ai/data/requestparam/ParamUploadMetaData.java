package com.miui.handwriting.common.ai.data.requestparam;

import ch.qos.logback.core.CoreConstants;
import com.market.sdk.reflect.ReflectUtilsForMiui;
import com.xiaomi.mirror.RemoteDeviceInfo;
import java.util.List;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: ParamUploadMetaData.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u0000,\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010 \n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\b\f\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0002\b\u0086\b\u0018\u00002\u00020\u0001B3\u0012\f\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003\u0012\f\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00060\u0003\u0012\u000e\u0010\u0007\u001a\n\u0012\u0004\u0012\u00020\u0006\u0018\u00010\u0003¢\u0006\u0004\b\b\u0010\tJ\u000f\u0010\u000e\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003HÆ\u0003J\u000f\u0010\u000f\u001a\b\u0012\u0004\u0012\u00020\u00060\u0003HÆ\u0003J\u0011\u0010\u0010\u001a\n\u0012\u0004\u0012\u00020\u0006\u0018\u00010\u0003HÆ\u0003J;\u0010\u0011\u001a\u00020\u00002\u000e\b\u0002\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u00032\u000e\b\u0002\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00060\u00032\u0010\b\u0002\u0010\u0007\u001a\n\u0012\u0004\u0012\u00020\u0006\u0018\u00010\u0003HÆ\u0001J\u0013\u0010\u0012\u001a\u00020\u00132\b\u0010\u0014\u001a\u0004\u0018\u00010\u0001HÖ\u0003J\t\u0010\u0015\u001a\u00020\u0016HÖ\u0001J\t\u0010\u0017\u001a\u00020\u0004HÖ\u0001R\u0017\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003¢\u0006\b\n\u0000\u001a\u0004\b\n\u0010\u000bR\u0017\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00060\u0003¢\u0006\b\n\u0000\u001a\u0004\b\f\u0010\u000bR\u0019\u0010\u0007\u001a\n\u0012\u0004\u0012\u00020\u0006\u0018\u00010\u0003¢\u0006\b\n\u0000\u001a\u0004\b\r\u0010\u000b¨\u0006\u0018"}, d2 = {"Lcom/miui/handwriting/common/ai/data/requestparam/ParamUploadMetaData;", "", "algorithm", "", "", "imageInfo", "Lcom/miui/handwriting/common/ai/data/requestparam/ParamImageInfo;", "guideImageInfo", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "(Ljava/util/List;Ljava/util/List;Ljava/util/List;)V", "getAlgorithm", "()Ljava/util/List;", "getImageInfo", "getGuideImageInfo", "component1", "component2", "component3", "copy", "equals", "", RemoteDeviceInfo.MANUFACTURER_OTHER, "hashCode", "", "toString", "libHandWritingCommon_release"}, k = 1, mv = {2, 2, 0}, xi = 48)
public final /* data */ class ParamUploadMetaData {
    private final List<String> algorithm;
    private final List<ParamImageInfo> guideImageInfo;
    private final List<ParamImageInfo> imageInfo;

    /* JADX WARN: Multi-variable type inference failed */
    public static /* synthetic */ ParamUploadMetaData copy$default(ParamUploadMetaData paramUploadMetaData, List list, List list2, List list3, int i, Object obj) {
        if ((i & 1) != 0) {
            list = paramUploadMetaData.algorithm;
        }
        if ((i & 2) != 0) {
            list2 = paramUploadMetaData.imageInfo;
        }
        if ((i & 4) != 0) {
            list3 = paramUploadMetaData.guideImageInfo;
        }
        return paramUploadMetaData.copy(list, list2, list3);
    }

    public final List<String> component1() {
        return this.algorithm;
    }

    public final List<ParamImageInfo> component2() {
        return this.imageInfo;
    }

    public final List<ParamImageInfo> component3() {
        return this.guideImageInfo;
    }

    public final ParamUploadMetaData copy(List<String> algorithm, List<ParamImageInfo> imageInfo, List<ParamImageInfo> guideImageInfo) {
        Intrinsics.checkNotNullParameter(algorithm, "algorithm");
        Intrinsics.checkNotNullParameter(imageInfo, "imageInfo");
        return new ParamUploadMetaData(algorithm, imageInfo, guideImageInfo);
    }

    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof ParamUploadMetaData)) {
            return false;
        }
        ParamUploadMetaData paramUploadMetaData = (ParamUploadMetaData) other;
        return Intrinsics.areEqual(this.algorithm, paramUploadMetaData.algorithm) && Intrinsics.areEqual(this.imageInfo, paramUploadMetaData.imageInfo) && Intrinsics.areEqual(this.guideImageInfo, paramUploadMetaData.guideImageInfo);
    }

    public int hashCode() {
        int iHashCode = ((this.algorithm.hashCode() * 31) + this.imageInfo.hashCode()) * 31;
        List<ParamImageInfo> list = this.guideImageInfo;
        return iHashCode + (list == null ? 0 : list.hashCode());
    }

    public String toString() {
        return "ParamUploadMetaData(algorithm=" + this.algorithm + ", imageInfo=" + this.imageInfo + ", guideImageInfo=" + this.guideImageInfo + CoreConstants.RIGHT_PARENTHESIS_CHAR;
    }

    public ParamUploadMetaData(List<String> algorithm, List<ParamImageInfo> imageInfo, List<ParamImageInfo> list) {
        Intrinsics.checkNotNullParameter(algorithm, "algorithm");
        Intrinsics.checkNotNullParameter(imageInfo, "imageInfo");
        this.algorithm = algorithm;
        this.imageInfo = imageInfo;
        this.guideImageInfo = list;
    }

    public final List<String> getAlgorithm() {
        return this.algorithm;
    }

    public final List<ParamImageInfo> getGuideImageInfo() {
        return this.guideImageInfo;
    }

    public final List<ParamImageInfo> getImageInfo() {
        return this.imageInfo;
    }
}
