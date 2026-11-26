package com.miui.doodle.feature.action;

import com.market.sdk.reflect.ReflectUtilsForMiui;
import com.miui.common.stat.NotesTextStat;
import com.miui.doodle.feature.draw.DoodleView;
import com.miui.doodle.feature.draw.LayerView;
import com.miui.notes.base.utils.Logger;
import java.util.Iterator;
import java.util.List;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.apache.commons.lang3.StringUtils;

/* JADX INFO: compiled from: RemoveAction.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u00000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010!\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\b\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0006\u0018\u00002\u00020\u0001B+\u0012\f\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003\u0012\f\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00060\u0003\u0012\u0006\u0010\u0007\u001a\u00020\b¢\u0006\u0004\b\t\u0010\nJ\u0010\u0010\u0010\u001a\u00020\u00112\u0006\u0010\u0012\u001a\u00020\u0013H\u0016J\u0010\u0010\u0014\u001a\u00020\u00112\u0006\u0010\u0012\u001a\u00020\u0013H\u0016J\b\u0010\u0015\u001a\u00020\u0011H\u0016J\b\u0010\u0016\u001a\u00020\u0011H\u0016J\b\u0010\u0017\u001a\u00020\u0011H\u0016J\b\u0010\u0018\u001a\u00020\u0011H\u0016R\u0017\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\fR\u0017\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00060\u0003¢\u0006\b\n\u0000\u001a\u0004\b\r\u0010\fR\u0011\u0010\u0007\u001a\u00020\b¢\u0006\b\n\u0000\u001a\u0004\b\u000e\u0010\u000f¨\u0006\u0019"}, d2 = {"Lcom/miui/doodle/feature/action/RemoveAction;", "Lcom/miui/doodle/feature/action/BaseAction;", "removedItemInfos", "", "Lcom/miui/doodle/feature/action/RemovedItemInfo;", "removedLayerBitmapList", "Lcom/miui/doodle/feature/action/RemovedLayerBitmapInfo;", "layerIndex", "", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "(Ljava/util/List;Ljava/util/List;I)V", "getRemovedItemInfos", "()Ljava/util/List;", "getRemovedLayerBitmapList", "getLayerIndex", "()I", NotesTextStat.UNDO, "", "doodleView", "Lcom/miui/doodle/feature/draw/DoodleView;", NotesTextStat.REDO, "cacheToFile", "restoreFromFile", "releaseJobs", "destroy", "libDoodle_release"}, k = 1, mv = {2, 2, 0}, xi = 48)
public final class RemoveAction extends BaseAction {
    private final int layerIndex;
    private final List<RemovedItemInfo> removedItemInfos;
    private final List<RemovedLayerBitmapInfo> removedLayerBitmapList;

    public final List<RemovedItemInfo> getRemovedItemInfos() {
        return this.removedItemInfos;
    }

    public final List<RemovedLayerBitmapInfo> getRemovedLayerBitmapList() {
        return this.removedLayerBitmapList;
    }

    public RemoveAction(List<RemovedItemInfo> removedItemInfos, List<RemovedLayerBitmapInfo> removedLayerBitmapList, int i) {
        Intrinsics.checkNotNullParameter(removedItemInfos, "removedItemInfos");
        Intrinsics.checkNotNullParameter(removedLayerBitmapList, "removedLayerBitmapList");
        this.removedItemInfos = removedItemInfos;
        this.removedLayerBitmapList = removedLayerBitmapList;
        this.layerIndex = i;
    }

    public final int getLayerIndex() {
        return this.layerIndex;
    }

    @Override // com.miui.doodle.feature.action.IAction
    public void undo(DoodleView doodleView) {
        Intrinsics.checkNotNullParameter(doodleView, "doodleView");
        LayerView layerView = doodleView.getLayerViewList().get(this.layerIndex);
        if (!this.removedLayerBitmapList.isEmpty()) {
            for (int size = this.removedLayerBitmapList.size() - 1; -1 < size; size--) {
                RemovedLayerBitmapInfo removedLayerBitmapInfo = this.removedLayerBitmapList.get(size);
                layerView.getLayer().getLayerBitmapList().add(removedLayerBitmapInfo.getLayerBitmapIndex(), removedLayerBitmapInfo.getLayerBitmap());
            }
        }
        for (RemovedItemInfo removedItemInfo : this.removedItemInfos) {
            if (!removedItemInfo.getItemsRemovedList().isEmpty()) {
                for (int size2 = removedItemInfo.getItemsRemovedList().size() - 1; -1 < size2; size2--) {
                    SubRemovedItemInfo subRemovedItemInfo = removedItemInfo.getItemsRemovedList().get(size2);
                    Logger.INSTANCE.d("NewActionRemove", "add subItemInfo " + subRemovedItemInfo.getIndex() + StringUtils.SPACE + subRemovedItemInfo.getItem());
                    if (subRemovedItemInfo.getIndex() >= removedItemInfo.getLayerBitmap().getItems().size()) {
                        removedItemInfo.getLayerBitmap().getItems().add(subRemovedItemInfo.getItem());
                        Logger.INSTANCE.i("NewActionRemove", "waring outindex it.layerBitmap " + removedItemInfo.getLayerBitmap());
                    } else {
                        removedItemInfo.getLayerBitmap().getItems().add(subRemovedItemInfo.getIndex(), subRemovedItemInfo.getItem());
                    }
                }
            }
            removedItemInfo.restoreOldLayerBm(doodleView);
        }
    }

    @Override // com.miui.doodle.feature.action.IAction
    public void redo(DoodleView doodleView) {
        Intrinsics.checkNotNullParameter(doodleView, "doodleView");
        LayerView layerView = doodleView.getLayerViewList().get(this.layerIndex);
        if (!this.removedLayerBitmapList.isEmpty()) {
            int size = this.removedLayerBitmapList.size();
            for (int i = 0; i < size; i++) {
                layerView.getLayer().getLayerBitmapList().remove(this.removedLayerBitmapList.get(i).getLayerBitmap());
            }
        }
        for (RemovedItemInfo removedItemInfo : this.removedItemInfos) {
            if (!removedItemInfo.getItemsRemovedList().isEmpty()) {
                int size2 = removedItemInfo.getItemsRemovedList().size();
                for (int i2 = 0; i2 < size2; i2++) {
                    removedItemInfo.getLayerBitmap().getItems().remove(removedItemInfo.getItemsRemovedList().get(i2).getItem());
                }
            }
            removedItemInfo.restoreNewLayerBm(doodleView);
        }
    }

    @Override // com.miui.doodle.feature.action.BaseAction, com.miui.doodle.feature.action.IAction
    public void cacheToFile() {
        if (getEnableCacheToFile()) {
            return;
        }
        for (RemovedItemInfo removedItemInfo : this.removedItemInfos) {
            if (!removedItemInfo.getNeedRemovedLayerBitmap()) {
                removedItemInfo.cacheToFile();
            }
        }
    }

    @Override // com.miui.doodle.feature.action.BaseAction, com.miui.doodle.feature.action.IAction
    public void restoreFromFile() {
        if (getEnableCacheToFile()) {
            return;
        }
        for (RemovedItemInfo removedItemInfo : this.removedItemInfos) {
            if (!removedItemInfo.getNeedRemovedLayerBitmap()) {
                removedItemInfo.restoreFromFile();
            }
        }
    }

    @Override // com.miui.doodle.feature.action.BaseAction, com.miui.doodle.feature.action.IAction
    public void releaseJobs() {
        if (getEnableCacheToFile()) {
            return;
        }
        Iterator<T> it = this.removedItemInfos.iterator();
        while (it.hasNext()) {
            ((RemovedItemInfo) it.next()).releaseJobs();
        }
    }

    @Override // com.miui.doodle.feature.action.BaseAction, com.miui.doodle.feature.action.IAction
    public void destroy() {
        for (RemovedItemInfo removedItemInfo : this.removedItemInfos) {
            if (!removedItemInfo.getNeedRemovedLayerBitmap()) {
                removedItemInfo.destroy();
            }
        }
    }
}
