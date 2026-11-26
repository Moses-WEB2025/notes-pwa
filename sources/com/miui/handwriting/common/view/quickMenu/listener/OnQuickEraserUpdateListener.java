package com.miui.handwriting.common.view.quickMenu.listener;

import kotlin.Metadata;
import xc.engine.operate.canvas.track.delete.DeleteType;

/* JADX INFO: compiled from: OnQuickEraserUpdateListener.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u0000\u001c\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0007\n\u0000\bf\u0018\u00002\u00020\u0001J\u0018\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u00052\u0006\u0010\u0006\u001a\u00020\u0007H&ø\u0001\u0000\u0082\u0002\u0006\n\u0004\b!0\u0001¨\u0006\bÀ\u0006\u0001"}, d2 = {"Lcom/miui/handwriting/common/view/quickMenu/listener/OnQuickEraserUpdateListener;", "", "onUpdate", "", "type", "Lxc/engine/operate/canvas/track/delete/DeleteType;", "size", "", "libHandWritingCommon_release"}, k = 1, mv = {2, 2, 0}, xi = 48)
public interface OnQuickEraserUpdateListener {
    void onUpdate(DeleteType type, float size);
}
