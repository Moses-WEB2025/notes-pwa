package com.miui.doodle.feature.action;

import com.market.sdk.reflect.ReflectUtilsForMiui;
import com.miui.common.stat.NotesTextStat;
import com.miui.doodle.document.LayerBitmap;
import com.miui.doodle.feature.draw.DoodleView;
import com.miui.doodle.feature.draw.LayerView;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: AddLayerBitmapAction.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000,\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\t\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0006\u0018\u00002\u00020\u0001B\u001f\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007¢\u0006\u0004\b\b\u0010\tJ\u0010\u0010\u0010\u001a\u00020\u00112\u0006\u0010\u0012\u001a\u00020\u0013H\u0016J\u0010\u0010\u0014\u001a\u00020\u00112\u0006\u0010\u0012\u001a\u00020\u0013H\u0016J\b\u0010\u0015\u001a\u00020\u0011H\u0016J\b\u0010\u0016\u001a\u00020\u0011H\u0016J\b\u0010\u0017\u001a\u00020\u0011H\u0016J\b\u0010\u0018\u001a\u00020\u0011H\u0016R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\n\u0010\u000bR\u0011\u0010\u0004\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\f\u0010\rR\u0011\u0010\u0006\u001a\u00020\u0007¢\u0006\b\n\u0000\u001a\u0004\b\u000e\u0010\u000f¨\u0006\u0019"}, d2 = {"Lcom/miui/doodle/feature/action/AddLayerBitmapAction;", "Lcom/miui/doodle/feature/action/BaseAction;", "addedLayerBitmap", "Lcom/miui/doodle/document/LayerBitmap;", "layerView", "Lcom/miui/doodle/feature/draw/LayerView;", "layerBitmapIndex", "", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "(Lcom/miui/doodle/document/LayerBitmap;Lcom/miui/doodle/feature/draw/LayerView;I)V", "getAddedLayerBitmap", "()Lcom/miui/doodle/document/LayerBitmap;", "getLayerView", "()Lcom/miui/doodle/feature/draw/LayerView;", "getLayerBitmapIndex", "()I", NotesTextStat.UNDO, "", "doodleView", "Lcom/miui/doodle/feature/draw/DoodleView;", NotesTextStat.REDO, "cacheToFile", "restoreFromFile", "releaseJobs", "destroy", "libDoodle_release"}, k = 1, mv = {2, 2, 0}, xi = 48)
public final class AddLayerBitmapAction extends BaseAction {
    private final LayerBitmap addedLayerBitmap;
    private final int layerBitmapIndex;
    private final LayerView layerView;

    @Override // com.miui.doodle.feature.action.BaseAction, com.miui.doodle.feature.action.IAction
    public void cacheToFile() {
    }

    @Override // com.miui.doodle.feature.action.BaseAction, com.miui.doodle.feature.action.IAction
    public void destroy() {
    }

    @Override // com.miui.doodle.feature.action.BaseAction, com.miui.doodle.feature.action.IAction
    public void releaseJobs() {
    }

    @Override // com.miui.doodle.feature.action.BaseAction, com.miui.doodle.feature.action.IAction
    public void restoreFromFile() {
    }

    public final LayerBitmap getAddedLayerBitmap() {
        return this.addedLayerBitmap;
    }

    public final LayerView getLayerView() {
        return this.layerView;
    }

    public AddLayerBitmapAction(LayerBitmap addedLayerBitmap, LayerView layerView, int i) {
        Intrinsics.checkNotNullParameter(addedLayerBitmap, "addedLayerBitmap");
        Intrinsics.checkNotNullParameter(layerView, "layerView");
        this.addedLayerBitmap = addedLayerBitmap;
        this.layerView = layerView;
        this.layerBitmapIndex = i;
    }

    public final int getLayerBitmapIndex() {
        return this.layerBitmapIndex;
    }

    @Override // com.miui.doodle.feature.action.IAction
    public void undo(DoodleView doodleView) {
        Intrinsics.checkNotNullParameter(doodleView, "doodleView");
        this.layerView.getLayer().getLayerBitmapList().remove(this.layerBitmapIndex);
        doodleView.invalidate();
    }

    @Override // com.miui.doodle.feature.action.IAction
    public void redo(DoodleView doodleView) {
        Intrinsics.checkNotNullParameter(doodleView, "doodleView");
        this.layerView.getLayer().getLayerBitmapList().add(this.layerBitmapIndex, this.addedLayerBitmap);
        doodleView.invalidate();
    }
}
