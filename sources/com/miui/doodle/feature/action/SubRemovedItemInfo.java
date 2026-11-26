package com.miui.doodle.feature.action;

import com.market.sdk.reflect.ReflectUtilsForMiui;
import com.miui.doodle.base.core.IDoodleItem;
import com.xiaomi.mirror.RemoteDeviceInfo;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import miui.cloud.CloudPushConstants;

/* JADX INFO: compiled from: SubRemovedItemInfo.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000$\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0010\n\u0002\u0010\u000e\n\u0000\b\u0086\b\u0018\u00002\u00020\u0001B\u001f\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007¢\u0006\u0004\b\b\u0010\tJ\t\u0010\u0010\u001a\u00020\u0003HÆ\u0003J\t\u0010\u0011\u001a\u00020\u0005HÆ\u0003J\t\u0010\u0012\u001a\u00020\u0007HÆ\u0003J'\u0010\u0013\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u00052\b\b\u0002\u0010\u0006\u001a\u00020\u0007HÆ\u0001J\u0013\u0010\u0014\u001a\u00020\u00072\b\u0010\u0015\u001a\u0004\u0018\u00010\u0001HÖ\u0003J\t\u0010\u0016\u001a\u00020\u0005HÖ\u0001J\t\u0010\u0017\u001a\u00020\u0018HÖ\u0001R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\n\u0010\u000bR\u0011\u0010\u0004\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\f\u0010\rR\u0011\u0010\u0006\u001a\u00020\u0007¢\u0006\b\n\u0000\u001a\u0004\b\u000e\u0010\u000f¨\u0006\u0019"}, d2 = {"Lcom/miui/doodle/feature/action/SubRemovedItemInfo;", "", CloudPushConstants.XML_ITEM, "Lcom/miui/doodle/base/core/IDoodleItem;", "index", "", "lastRemoved", "", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "(Lcom/miui/doodle/base/core/IDoodleItem;IZ)V", "getItem", "()Lcom/miui/doodle/base/core/IDoodleItem;", "getIndex", "()I", "getLastRemoved", "()Z", "component1", "component2", "component3", "copy", "equals", RemoteDeviceInfo.MANUFACTURER_OTHER, "hashCode", "toString", "", "libDoodle_release"}, k = 1, mv = {2, 2, 0}, xi = 48)
public final /* data */ class SubRemovedItemInfo {
    private final int index;
    private final IDoodleItem item;
    private final boolean lastRemoved;

    public static /* synthetic */ SubRemovedItemInfo copy$default(SubRemovedItemInfo subRemovedItemInfo, IDoodleItem iDoodleItem, int i, boolean z, int i2, Object obj) {
        if ((i2 & 1) != 0) {
            iDoodleItem = subRemovedItemInfo.item;
        }
        if ((i2 & 2) != 0) {
            i = subRemovedItemInfo.index;
        }
        if ((i2 & 4) != 0) {
            z = subRemovedItemInfo.lastRemoved;
        }
        return subRemovedItemInfo.copy(iDoodleItem, i, z);
    }

    /* JADX INFO: renamed from: component1, reason: from getter */
    public final IDoodleItem getItem() {
        return this.item;
    }

    /* JADX INFO: renamed from: component2, reason: from getter */
    public final int getIndex() {
        return this.index;
    }

    /* JADX INFO: renamed from: component3, reason: from getter */
    public final boolean getLastRemoved() {
        return this.lastRemoved;
    }

    public final SubRemovedItemInfo copy(IDoodleItem item, int index, boolean lastRemoved) {
        Intrinsics.checkNotNullParameter(item, "item");
        return new SubRemovedItemInfo(item, index, lastRemoved);
    }

    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof SubRemovedItemInfo)) {
            return false;
        }
        SubRemovedItemInfo subRemovedItemInfo = (SubRemovedItemInfo) other;
        return Intrinsics.areEqual(this.item, subRemovedItemInfo.item) && this.index == subRemovedItemInfo.index && this.lastRemoved == subRemovedItemInfo.lastRemoved;
    }

    public int hashCode() {
        return (((this.item.hashCode() * 31) + Integer.hashCode(this.index)) * 31) + Boolean.hashCode(this.lastRemoved);
    }

    public String toString() {
        return "SubRemovedItemInfo(item=" + this.item + ", index=" + this.index + ", lastRemoved=" + this.lastRemoved + ")";
    }

    public SubRemovedItemInfo(IDoodleItem item, int i, boolean z) {
        Intrinsics.checkNotNullParameter(item, "item");
        this.item = item;
        this.index = i;
        this.lastRemoved = z;
    }

    public final int getIndex() {
        return this.index;
    }

    public final IDoodleItem getItem() {
        return this.item;
    }

    public final boolean getLastRemoved() {
        return this.lastRemoved;
    }
}
