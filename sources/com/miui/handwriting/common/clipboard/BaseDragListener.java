package com.miui.handwriting.common.clipboard;

import android.view.DragEvent;
import android.view.View;
import com.market.sdk.reflect.ReflectUtilsForMiui;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: BaseDragListener.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u0000(\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u0002\n\u0002\b\u0004\b&\u0018\u00002\u00020\u0001B\u0007¢\u0006\u0004\b\u0002\u0010\u0003J\u0018\u0010\u0004\u001a\u00020\u00052\u0006\u0010\u0006\u001a\u00020\u00072\u0006\u0010\b\u001a\u00020\tH\u0016J\u0010\u0010\n\u001a\u00020\u00052\u0006\u0010\b\u001a\u00020\tH\u0014J\u001a\u0010\u000b\u001a\u00020\u00052\b\u0010\u0006\u001a\u0004\u0018\u00010\u00072\u0006\u0010\b\u001a\u00020\tH\u0014J\u001a\u0010\f\u001a\u00020\u00052\b\u0010\u0006\u001a\u0004\u0018\u00010\u00072\u0006\u0010\b\u001a\u00020\tH\u0014J\u001a\u0010\r\u001a\u00020\u000e2\b\u0010\u0006\u001a\u0004\u0018\u00010\u00072\u0006\u0010\b\u001a\u00020\tH\u0014J\u001a\u0010\u000f\u001a\u00020\u00052\b\u0010\u0006\u001a\u0004\u0018\u00010\u00072\u0006\u0010\b\u001a\u00020\tH\u0014J\u001a\u0010\u0010\u001a\u00020\u000e2\b\u0010\u0006\u001a\u0004\u0018\u00010\u00072\u0006\u0010\b\u001a\u00020\tH\u0014J\u001a\u0010\u0011\u001a\u00020\u00052\b\u0010\u0006\u001a\u0004\u0018\u00010\u00072\u0006\u0010\b\u001a\u00020\tH\u0014¨\u0006\u0012"}, d2 = {"Lcom/miui/handwriting/common/clipboard/BaseDragListener;", "Landroid/view/View$OnDragListener;", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "()V", "onDrag", "", "v", "Landroid/view/View;", "event", "Landroid/view/DragEvent;", "needHandleEvent", "handleDragStarted", "handleDragEntered", "handleDragLocation", "", "handleDragDrop", "handleDragExit", "handleDragEnd", "libHandWritingCommon_release"}, k = 1, mv = {2, 2, 0}, xi = 48)
public abstract class BaseDragListener implements View.OnDragListener {
    protected boolean handleDragDrop(View v, DragEvent event) {
        Intrinsics.checkNotNullParameter(event, "event");
        return false;
    }

    protected boolean handleDragEnd(View v, DragEvent event) {
        Intrinsics.checkNotNullParameter(event, "event");
        return true;
    }

    protected boolean handleDragEntered(View v, DragEvent event) {
        Intrinsics.checkNotNullParameter(event, "event");
        return false;
    }

    protected void handleDragExit(View v, DragEvent event) {
        Intrinsics.checkNotNullParameter(event, "event");
    }

    protected void handleDragLocation(View v, DragEvent event) {
        Intrinsics.checkNotNullParameter(event, "event");
    }

    protected boolean handleDragStarted(View v, DragEvent event) {
        Intrinsics.checkNotNullParameter(event, "event");
        return true;
    }

    protected boolean needHandleEvent(DragEvent event) {
        Intrinsics.checkNotNullParameter(event, "event");
        return true;
    }

    @Override // android.view.View.OnDragListener
    public boolean onDrag(View v, DragEvent event) {
        Intrinsics.checkNotNullParameter(v, "v");
        Intrinsics.checkNotNullParameter(event, "event");
        boolean zNeedHandleEvent = needHandleEvent(event);
        switch (event.getAction()) {
            case 1:
                return handleDragStarted(v, event);
            case 2:
                handleDragLocation(v, event);
                return zNeedHandleEvent;
            case 3:
                return handleDragDrop(v, event);
            case 4:
                return handleDragEnd(v, event);
            case 5:
                return handleDragEntered(v, event);
            case 6:
                handleDragExit(v, event);
                return zNeedHandleEvent;
            default:
                return false;
        }
    }
}
