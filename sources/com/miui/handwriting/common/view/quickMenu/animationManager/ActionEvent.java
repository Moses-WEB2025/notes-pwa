package com.miui.handwriting.common.view.quickMenu.animationManager;

import com.xiaomi.mirror.synergy.CallMethod;
import kotlin.Metadata;

/* JADX INFO: compiled from: ActionEvent.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u0000\u0016\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\bf\u0018\u00002\u00020\u0001J\u0014\u0010\u0002\u001a\u00020\u00032\n\b\u0002\u0010\u0004\u001a\u0004\u0018\u00010\u0005H&ø\u0001\u0000\u0082\u0002\u0006\n\u0004\b!0\u0001¨\u0006\u0006À\u0006\u0001"}, d2 = {"Lcom/miui/handwriting/common/view/quickMenu/animationManager/ActionEvent;", "", "handle", "", CallMethod.ARG_CALLBACK, "Lcom/miui/handwriting/common/view/quickMenu/animationManager/onEventFinished;", "libHandWritingCommon_release"}, k = 1, mv = {2, 2, 0}, xi = 48)
public interface ActionEvent {
    void handle(onEventFinished callback);

    static /* synthetic */ void handle$default(ActionEvent actionEvent, onEventFinished oneventfinished, int i, Object obj) {
        if (obj != null) {
            throw new UnsupportedOperationException("Super calls with default arguments not supported in this target, function: handle");
        }
        if ((i & 1) != 0) {
            oneventfinished = null;
        }
        actionEvent.handle(oneventfinished);
    }
}
