package com.miui.handwriting.common.view.quickMenu.utils;

import ch.qos.logback.core.CoreConstants;
import com.market.sdk.reflect.ReflectUtilsForMiui;
import com.xiaomi.mirror.RemoteDeviceInfo;
import kotlin.Metadata;

/* JADX INFO: compiled from: PenMenuRes.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u0000 \n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0002\b\r\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0000\b\u0086\b\u0018\u00002\u00020\u0001B\u001f\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0003\u0012\u0006\u0010\u0005\u001a\u00020\u0003¢\u0006\u0004\b\u0006\u0010\u0007J\t\u0010\f\u001a\u00020\u0003HÆ\u0003J\t\u0010\r\u001a\u00020\u0003HÆ\u0003J\t\u0010\u000e\u001a\u00020\u0003HÆ\u0003J'\u0010\u000f\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u00032\b\b\u0002\u0010\u0005\u001a\u00020\u0003HÆ\u0001J\u0013\u0010\u0010\u001a\u00020\u00112\b\u0010\u0012\u001a\u0004\u0018\u00010\u0001HÖ\u0003J\t\u0010\u0013\u001a\u00020\u0003HÖ\u0001J\t\u0010\u0014\u001a\u00020\u0015HÖ\u0001R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\b\u0010\tR\u0011\u0010\u0004\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\n\u0010\tR\u0011\u0010\u0005\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\t¨\u0006\u0016"}, d2 = {"Lcom/miui/handwriting/common/view/quickMenu/utils/BrushResource;", "", "headId", "", "bodyId", "shadowId", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "(III)V", "getHeadId", "()I", "getBodyId", "getShadowId", "component1", "component2", "component3", "copy", "equals", "", RemoteDeviceInfo.MANUFACTURER_OTHER, "hashCode", "toString", "", "libHandWritingCommon_release"}, k = 1, mv = {2, 2, 0}, xi = 48)
public final /* data */ class BrushResource {
    private final int bodyId;
    private final int headId;
    private final int shadowId;

    public static /* synthetic */ BrushResource copy$default(BrushResource brushResource, int i, int i2, int i3, int i4, Object obj) {
        if ((i4 & 1) != 0) {
            i = brushResource.headId;
        }
        if ((i4 & 2) != 0) {
            i2 = brushResource.bodyId;
        }
        if ((i4 & 4) != 0) {
            i3 = brushResource.shadowId;
        }
        return brushResource.copy(i, i2, i3);
    }

    /* JADX INFO: renamed from: component1, reason: from getter */
    public final int getHeadId() {
        return this.headId;
    }

    /* JADX INFO: renamed from: component2, reason: from getter */
    public final int getBodyId() {
        return this.bodyId;
    }

    /* JADX INFO: renamed from: component3, reason: from getter */
    public final int getShadowId() {
        return this.shadowId;
    }

    public final BrushResource copy(int headId, int bodyId, int shadowId) {
        return new BrushResource(headId, bodyId, shadowId);
    }

    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof BrushResource)) {
            return false;
        }
        BrushResource brushResource = (BrushResource) other;
        return this.headId == brushResource.headId && this.bodyId == brushResource.bodyId && this.shadowId == brushResource.shadowId;
    }

    public int hashCode() {
        return (((Integer.hashCode(this.headId) * 31) + Integer.hashCode(this.bodyId)) * 31) + Integer.hashCode(this.shadowId);
    }

    public String toString() {
        return "BrushResource(headId=" + this.headId + ", bodyId=" + this.bodyId + ", shadowId=" + this.shadowId + CoreConstants.RIGHT_PARENTHESIS_CHAR;
    }

    public BrushResource(int i, int i2, int i3) {
        this.headId = i;
        this.bodyId = i2;
        this.shadowId = i3;
    }

    public final int getBodyId() {
        return this.bodyId;
    }

    public final int getHeadId() {
        return this.headId;
    }

    public final int getShadowId() {
        return this.shadowId;
    }
}
