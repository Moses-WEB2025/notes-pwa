package com.miui.doodle.feature.action;

import com.market.sdk.reflect.ReflectUtilsForMiui;
import com.miui.common.stat.NotesTextStat;
import com.miui.doodle.feature.draw.DoodleView;
import java.util.Iterator;
import java.util.List;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: EraseAction.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000$\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0006\u0018\u00002\u00020\u0001B\u0015\u0012\f\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003¢\u0006\u0004\b\u0005\u0010\u0006J\u0010\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\fH\u0016J\u0010\u0010\r\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\fH\u0016J\b\u0010\u000e\u001a\u00020\nH\u0016J\b\u0010\u000f\u001a\u00020\nH\u0016J\b\u0010\u0010\u001a\u00020\nH\u0016J\b\u0010\u0011\u001a\u00020\nH\u0016R\u0017\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\b¨\u0006\u0012"}, d2 = {"Lcom/miui/doodle/feature/action/EraseAction;", "Lcom/miui/doodle/feature/action/BaseAction;", "eraserLayerBitmapInfo", "", "Lcom/miui/doodle/feature/action/EraserLayerBitmapInfo;", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "(Ljava/util/List;)V", "getEraserLayerBitmapInfo", "()Ljava/util/List;", NotesTextStat.UNDO, "", "doodleView", "Lcom/miui/doodle/feature/draw/DoodleView;", NotesTextStat.REDO, "cacheToFile", "restoreFromFile", "releaseJobs", "destroy", "libDoodle_release"}, k = 1, mv = {2, 2, 0}, xi = 48)
public final class EraseAction extends BaseAction {
    private final List<EraserLayerBitmapInfo> eraserLayerBitmapInfo;

    public EraseAction(List<EraserLayerBitmapInfo> eraserLayerBitmapInfo) {
        Intrinsics.checkNotNullParameter(eraserLayerBitmapInfo, "eraserLayerBitmapInfo");
        this.eraserLayerBitmapInfo = eraserLayerBitmapInfo;
    }

    public final List<EraserLayerBitmapInfo> getEraserLayerBitmapInfo() {
        return this.eraserLayerBitmapInfo;
    }

    @Override // com.miui.doodle.feature.action.IAction
    public void undo(DoodleView doodleView) {
        Intrinsics.checkNotNullParameter(doodleView, "doodleView");
        Iterator<T> it = this.eraserLayerBitmapInfo.iterator();
        while (it.hasNext()) {
            ((EraserLayerBitmapInfo) it.next()).restoreOldLayerBm(doodleView);
        }
        doodleView.invalidate();
    }

    @Override // com.miui.doodle.feature.action.IAction
    public void redo(DoodleView doodleView) {
        Intrinsics.checkNotNullParameter(doodleView, "doodleView");
        Iterator<T> it = this.eraserLayerBitmapInfo.iterator();
        while (it.hasNext()) {
            ((EraserLayerBitmapInfo) it.next()).restoreNewLayerBm(doodleView);
        }
        doodleView.invalidate();
    }

    @Override // com.miui.doodle.feature.action.BaseAction, com.miui.doodle.feature.action.IAction
    public void cacheToFile() {
        if (getEnableCacheToFile()) {
            Iterator<T> it = this.eraserLayerBitmapInfo.iterator();
            while (it.hasNext()) {
                ((EraserLayerBitmapInfo) it.next()).cacheToFile();
            }
        }
    }

    @Override // com.miui.doodle.feature.action.BaseAction, com.miui.doodle.feature.action.IAction
    public void restoreFromFile() {
        if (getEnableCacheToFile()) {
            Iterator<T> it = this.eraserLayerBitmapInfo.iterator();
            while (it.hasNext()) {
                ((EraserLayerBitmapInfo) it.next()).restoreFromFile();
            }
        }
    }

    @Override // com.miui.doodle.feature.action.BaseAction, com.miui.doodle.feature.action.IAction
    public void releaseJobs() {
        if (getEnableCacheToFile()) {
            Iterator<T> it = this.eraserLayerBitmapInfo.iterator();
            while (it.hasNext()) {
                ((EraserLayerBitmapInfo) it.next()).releaseJobs();
            }
        }
    }

    @Override // com.miui.doodle.feature.action.BaseAction, com.miui.doodle.feature.action.IAction
    public void destroy() {
        Iterator<T> it = this.eraserLayerBitmapInfo.iterator();
        while (it.hasNext()) {
            ((EraserLayerBitmapInfo) it.next()).destroy();
        }
    }
}
