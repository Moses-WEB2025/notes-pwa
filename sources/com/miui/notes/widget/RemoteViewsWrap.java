package com.miui.notes.widget;

import android.widget.RemoteViews;
import com.market.sdk.reflect.ReflectUtilsForMiui;
import com.xiaomi.mirror.RemoteDeviceInfo;
import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: RemoteViewsWrap.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000&\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u000e\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0000\b\u0086\b\u0018\u00002\u00020\u0001B\u001d\u0012\n\b\u0002\u0010\u0002\u001a\u0004\u0018\u00010\u0003\u0012\b\b\u0002\u0010\u0004\u001a\u00020\u0005¢\u0006\u0004\b\u0006\u0010\u0007J\u000b\u0010\u0010\u001a\u0004\u0018\u00010\u0003HÆ\u0003J\t\u0010\u0011\u001a\u00020\u0005HÆ\u0003J\u001f\u0010\u0012\u001a\u00020\u00002\n\b\u0002\u0010\u0002\u001a\u0004\u0018\u00010\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u0005HÆ\u0001J\u0013\u0010\u0013\u001a\u00020\u00142\b\u0010\u0015\u001a\u0004\u0018\u00010\u0001HÖ\u0003J\t\u0010\u0016\u001a\u00020\u0005HÖ\u0001J\t\u0010\u0017\u001a\u00020\u0018HÖ\u0001R\u001c\u0010\u0002\u001a\u0004\u0018\u00010\u0003X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\b\u0010\t\"\u0004\b\n\u0010\u000bR\u001a\u0010\u0004\u001a\u00020\u0005X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\f\u0010\r\"\u0004\b\u000e\u0010\u000f¨\u0006\u0019"}, d2 = {"Lcom/miui/notes/widget/RemoteViewsWrap;", "", "remoteViews", "Landroid/widget/RemoteViews;", "widgetId", "", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "(Landroid/widget/RemoteViews;I)V", "getRemoteViews", "()Landroid/widget/RemoteViews;", "setRemoteViews", "(Landroid/widget/RemoteViews;)V", "getWidgetId", "()I", "setWidgetId", "(I)V", "component1", "component2", "copy", "equals", "", RemoteDeviceInfo.MANUFACTURER_OTHER, "hashCode", "toString", "", "app_PhoneGlobalRelease"}, k = 1, mv = {2, 2, 0}, xi = 48)
public final /* data */ class RemoteViewsWrap {
    private RemoteViews remoteViews;
    private int widgetId;

    public RemoteViewsWrap() {
        this(null, 0, 3, 0 == true ? 1 : 0);
    }

    public static /* synthetic */ RemoteViewsWrap copy$default(RemoteViewsWrap remoteViewsWrap, RemoteViews remoteViews, int i, int i2, Object obj) {
        if ((i2 & 1) != 0) {
            remoteViews = remoteViewsWrap.remoteViews;
        }
        if ((i2 & 2) != 0) {
            i = remoteViewsWrap.widgetId;
        }
        return remoteViewsWrap.copy(remoteViews, i);
    }

    /* JADX INFO: renamed from: component1, reason: from getter */
    public final RemoteViews getRemoteViews() {
        return this.remoteViews;
    }

    /* JADX INFO: renamed from: component2, reason: from getter */
    public final int getWidgetId() {
        return this.widgetId;
    }

    public final RemoteViewsWrap copy(RemoteViews remoteViews, int widgetId) {
        return new RemoteViewsWrap(remoteViews, widgetId);
    }

    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof RemoteViewsWrap)) {
            return false;
        }
        RemoteViewsWrap remoteViewsWrap = (RemoteViewsWrap) other;
        return Intrinsics.areEqual(this.remoteViews, remoteViewsWrap.remoteViews) && this.widgetId == remoteViewsWrap.widgetId;
    }

    public int hashCode() {
        RemoteViews remoteViews = this.remoteViews;
        return ((remoteViews == null ? 0 : remoteViews.hashCode()) * 31) + Integer.hashCode(this.widgetId);
    }

    public String toString() {
        return "RemoteViewsWrap(remoteViews=" + this.remoteViews + ", widgetId=" + this.widgetId + ")";
    }

    public RemoteViewsWrap(RemoteViews remoteViews, int i) {
        this.remoteViews = remoteViews;
        this.widgetId = i;
    }

    public /* synthetic */ RemoteViewsWrap(RemoteViews remoteViews, int i, int i2, DefaultConstructorMarker defaultConstructorMarker) {
        this((i2 & 1) != 0 ? null : remoteViews, (i2 & 2) != 0 ? 0 : i);
    }

    public final RemoteViews getRemoteViews() {
        return this.remoteViews;
    }

    public final int getWidgetId() {
        return this.widgetId;
    }

    public final void setRemoteViews(RemoteViews remoteViews) {
        this.remoteViews = remoteViews;
    }

    public final void setWidgetId(int i) {
        this.widgetId = i;
    }
}
