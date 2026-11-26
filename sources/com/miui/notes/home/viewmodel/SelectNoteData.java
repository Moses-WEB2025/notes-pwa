package com.miui.notes.home.viewmodel;

import com.market.sdk.reflect.ReflectUtilsForMiui;
import com.miui.common.base.NoteLoadDataEntity;
import com.xiaomi.mirror.RemoteDeviceInfo;
import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: SelectNoteData.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000,\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\t\n\u0002\b\u0014\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0000\b\u0086\b\u0018\u00002\u00020\u0001B+\u0012\b\u0010\u0002\u001a\u0004\u0018\u00010\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\b\b\u0002\u0010\u0006\u001a\u00020\u0005\u0012\u0006\u0010\u0007\u001a\u00020\b¢\u0006\u0004\b\t\u0010\nJ\u000b\u0010\u0015\u001a\u0004\u0018\u00010\u0003HÆ\u0003J\t\u0010\u0016\u001a\u00020\u0005HÆ\u0003J\t\u0010\u0017\u001a\u00020\u0005HÆ\u0003J\t\u0010\u0018\u001a\u00020\bHÆ\u0003J3\u0010\u0019\u001a\u00020\u00002\n\b\u0002\u0010\u0002\u001a\u0004\u0018\u00010\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u00052\b\b\u0002\u0010\u0006\u001a\u00020\u00052\b\b\u0002\u0010\u0007\u001a\u00020\bHÆ\u0001J\u0013\u0010\u001a\u001a\u00020\u00052\b\u0010\u001b\u001a\u0004\u0018\u00010\u0001HÖ\u0003J\t\u0010\u001c\u001a\u00020\u001dHÖ\u0001J\t\u0010\u001e\u001a\u00020\u001fHÖ\u0001R\u0013\u0010\u0002\u001a\u0004\u0018\u00010\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\fR\u001a\u0010\u0004\u001a\u00020\u0005X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\r\u0010\u000e\"\u0004\b\u000f\u0010\u0010R\u001a\u0010\u0006\u001a\u00020\u0005X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0011\u0010\u000e\"\u0004\b\u0012\u0010\u0010R\u0011\u0010\u0007\u001a\u00020\b¢\u0006\b\n\u0000\u001a\u0004\b\u0013\u0010\u0014¨\u0006 "}, d2 = {"Lcom/miui/notes/home/viewmodel/SelectNoteData;", "", "entity", "Lcom/miui/common/base/NoteLoadDataEntity;", "openDetail", "", "useDetailFragmentNav", "folderId", "", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "(Lcom/miui/common/base/NoteLoadDataEntity;ZZJ)V", "getEntity", "()Lcom/miui/common/base/NoteLoadDataEntity;", "getOpenDetail", "()Z", "setOpenDetail", "(Z)V", "getUseDetailFragmentNav", "setUseDetailFragmentNav", "getFolderId", "()J", "component1", "component2", "component3", "component4", "copy", "equals", RemoteDeviceInfo.MANUFACTURER_OTHER, "hashCode", "", "toString", "", "app_PhoneGlobalRelease"}, k = 1, mv = {2, 2, 0}, xi = 48)
public final /* data */ class SelectNoteData {
    private final NoteLoadDataEntity entity;
    private final long folderId;
    private boolean openDetail;
    private boolean useDetailFragmentNav;

    public static /* synthetic */ SelectNoteData copy$default(SelectNoteData selectNoteData, NoteLoadDataEntity noteLoadDataEntity, boolean z, boolean z2, long j, int i, Object obj) {
        if ((i & 1) != 0) {
            noteLoadDataEntity = selectNoteData.entity;
        }
        if ((i & 2) != 0) {
            z = selectNoteData.openDetail;
        }
        if ((i & 4) != 0) {
            z2 = selectNoteData.useDetailFragmentNav;
        }
        if ((i & 8) != 0) {
            j = selectNoteData.folderId;
        }
        boolean z3 = z2;
        return selectNoteData.copy(noteLoadDataEntity, z, z3, j);
    }

    /* JADX INFO: renamed from: component1, reason: from getter */
    public final NoteLoadDataEntity getEntity() {
        return this.entity;
    }

    /* JADX INFO: renamed from: component2, reason: from getter */
    public final boolean getOpenDetail() {
        return this.openDetail;
    }

    /* JADX INFO: renamed from: component3, reason: from getter */
    public final boolean getUseDetailFragmentNav() {
        return this.useDetailFragmentNav;
    }

    /* JADX INFO: renamed from: component4, reason: from getter */
    public final long getFolderId() {
        return this.folderId;
    }

    public final SelectNoteData copy(NoteLoadDataEntity entity, boolean openDetail, boolean useDetailFragmentNav, long folderId) {
        return new SelectNoteData(entity, openDetail, useDetailFragmentNav, folderId);
    }

    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof SelectNoteData)) {
            return false;
        }
        SelectNoteData selectNoteData = (SelectNoteData) other;
        return Intrinsics.areEqual(this.entity, selectNoteData.entity) && this.openDetail == selectNoteData.openDetail && this.useDetailFragmentNav == selectNoteData.useDetailFragmentNav && this.folderId == selectNoteData.folderId;
    }

    public int hashCode() {
        NoteLoadDataEntity noteLoadDataEntity = this.entity;
        return ((((((noteLoadDataEntity == null ? 0 : noteLoadDataEntity.hashCode()) * 31) + Boolean.hashCode(this.openDetail)) * 31) + Boolean.hashCode(this.useDetailFragmentNav)) * 31) + Long.hashCode(this.folderId);
    }

    public String toString() {
        return "SelectNoteData(entity=" + this.entity + ", openDetail=" + this.openDetail + ", useDetailFragmentNav=" + this.useDetailFragmentNav + ", folderId=" + this.folderId + ")";
    }

    public SelectNoteData(NoteLoadDataEntity noteLoadDataEntity, boolean z, boolean z2, long j) {
        this.entity = noteLoadDataEntity;
        this.openDetail = z;
        this.useDetailFragmentNav = z2;
        this.folderId = j;
    }

    public /* synthetic */ SelectNoteData(NoteLoadDataEntity noteLoadDataEntity, boolean z, boolean z2, long j, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(noteLoadDataEntity, z, (i & 4) != 0 ? false : z2, j);
    }

    public final NoteLoadDataEntity getEntity() {
        return this.entity;
    }

    public final long getFolderId() {
        return this.folderId;
    }

    public final boolean getOpenDetail() {
        return this.openDetail;
    }

    public final boolean getUseDetailFragmentNav() {
        return this.useDetailFragmentNav;
    }

    public final void setOpenDetail(boolean z) {
        this.openDetail = z;
    }

    public final void setUseDetailFragmentNav(boolean z) {
        this.useDetailFragmentNav = z;
    }
}
