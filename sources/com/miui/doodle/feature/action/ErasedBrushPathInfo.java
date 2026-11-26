package com.miui.doodle.feature.action;

import com.market.sdk.reflect.ReflectUtilsForMiui;
import com.miui.doodle.document.Dot;
import com.miui.doodle.feature.draw.DoodleBrushPath;
import com.xiaomi.mirror.RemoteDeviceInfo;
import java.util.Set;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: ErasedBrushPathInfo.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000<\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010#\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0002\b\b\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0002\b\u0086\b\u0018\u00002\u00020\u0001B\u001d\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\f\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00060\u0005¢\u0006\u0004\b\u0007\u0010\bJ\u0006\u0010\u0011\u001a\u00020\u0012J\u0006\u0010\u0013\u001a\u00020\u0012J\u0006\u0010\u0014\u001a\u00020\u0012J\u0006\u0010\u0015\u001a\u00020\u0012J\u0006\u0010\u0016\u001a\u00020\u0012J\t\u0010\u0017\u001a\u00020\u0003HÆ\u0003J\u000f\u0010\u0018\u001a\b\u0012\u0004\u0012\u00020\u00060\u0005HÆ\u0003J#\u0010\u0019\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\u000e\b\u0002\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00060\u0005HÆ\u0001J\u0013\u0010\u001a\u001a\u00020\u001b2\b\u0010\u001c\u001a\u0004\u0018\u00010\u0001HÖ\u0003J\t\u0010\u001d\u001a\u00020\u001eHÖ\u0001J\t\u0010\u001f\u001a\u00020\u000eHÖ\u0001R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\nR\u0017\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00060\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\fR\u0014\u0010\r\u001a\u00020\u000eX\u0086D¢\u0006\b\n\u0000\u001a\u0004\b\u000f\u0010\u0010¨\u0006 "}, d2 = {"Lcom/miui/doodle/feature/action/ErasedBrushPathInfo;", "", "brushPath", "Lcom/miui/doodle/feature/draw/DoodleBrushPath;", "removedDots", "", "Lcom/miui/doodle/document/Dot;", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "(Lcom/miui/doodle/feature/draw/DoodleBrushPath;Ljava/util/Set;)V", "getBrushPath", "()Lcom/miui/doodle/feature/draw/DoodleBrushPath;", "getRemovedDots", "()Ljava/util/Set;", "TAG", "", "getTAG", "()Ljava/lang/String;", "restoreOld", "", "restoreNew", "cacheToFile", "restoreFromFile", "destroy", "component1", "component2", "copy", "equals", "", RemoteDeviceInfo.MANUFACTURER_OTHER, "hashCode", "", "toString", "libDoodle_release"}, k = 1, mv = {2, 2, 0}, xi = 48)
public final /* data */ class ErasedBrushPathInfo {
    private final String TAG;
    private final DoodleBrushPath brushPath;
    private final Set<Dot> removedDots;

    /* JADX WARN: Multi-variable type inference failed */
    public static /* synthetic */ ErasedBrushPathInfo copy$default(ErasedBrushPathInfo erasedBrushPathInfo, DoodleBrushPath doodleBrushPath, Set set, int i, Object obj) {
        if ((i & 1) != 0) {
            doodleBrushPath = erasedBrushPathInfo.brushPath;
        }
        if ((i & 2) != 0) {
            set = erasedBrushPathInfo.removedDots;
        }
        return erasedBrushPathInfo.copy(doodleBrushPath, set);
    }

    public final void cacheToFile() {
    }

    /* JADX INFO: renamed from: component1, reason: from getter */
    public final DoodleBrushPath getBrushPath() {
        return this.brushPath;
    }

    public final Set<Dot> component2() {
        return this.removedDots;
    }

    public final ErasedBrushPathInfo copy(DoodleBrushPath brushPath, Set<Dot> removedDots) {
        Intrinsics.checkNotNullParameter(brushPath, "brushPath");
        Intrinsics.checkNotNullParameter(removedDots, "removedDots");
        return new ErasedBrushPathInfo(brushPath, removedDots);
    }

    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof ErasedBrushPathInfo)) {
            return false;
        }
        ErasedBrushPathInfo erasedBrushPathInfo = (ErasedBrushPathInfo) other;
        return Intrinsics.areEqual(this.brushPath, erasedBrushPathInfo.brushPath) && Intrinsics.areEqual(this.removedDots, erasedBrushPathInfo.removedDots);
    }

    public int hashCode() {
        return (this.brushPath.hashCode() * 31) + this.removedDots.hashCode();
    }

    public final void restoreFromFile() {
    }

    public String toString() {
        return "ErasedBrushPathInfo(brushPath=" + this.brushPath + ", removedDots=" + this.removedDots + ")";
    }

    public ErasedBrushPathInfo(DoodleBrushPath brushPath, Set<Dot> removedDots) {
        Intrinsics.checkNotNullParameter(brushPath, "brushPath");
        Intrinsics.checkNotNullParameter(removedDots, "removedDots");
        this.brushPath = brushPath;
        this.removedDots = removedDots;
        this.TAG = "ErasedBrushPathInfo";
    }

    public final DoodleBrushPath getBrushPath() {
        return this.brushPath;
    }

    public final Set<Dot> getRemovedDots() {
        return this.removedDots;
    }

    public final String getTAG() {
        return this.TAG;
    }

    public final void restoreOld() {
        this.brushPath.removeEraserDots(this.removedDots);
    }

    public final void restoreNew() {
        this.brushPath.restoreEraserDots(this.removedDots);
    }

    public final void destroy() {
        this.removedDots.clear();
    }
}
