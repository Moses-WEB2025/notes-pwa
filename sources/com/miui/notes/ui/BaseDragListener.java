package com.miui.notes.ui;

import android.view.DragEvent;
import android.view.View;

/* JADX INFO: loaded from: classes4.dex */
public abstract class BaseDragListener implements View.OnDragListener {
    public static final String LABEL_NOTE_EDITOR = "MiuiNotes:RichEditView";

    protected boolean handleDragDrop(View view, DragEvent dragEvent) {
        return false;
    }

    protected boolean handleDragEnd(View view, DragEvent dragEvent) {
        return true;
    }

    protected boolean handleDragEntered(View view, DragEvent dragEvent) {
        return false;
    }

    protected void handleDragExit(View view, DragEvent dragEvent) {
    }

    protected void handleDragLocation(View view, DragEvent dragEvent) {
    }

    protected boolean handleDragStarted(View view, DragEvent dragEvent) {
        return true;
    }

    protected void handleHover(boolean z) {
    }

    protected boolean needHandleEvent(DragEvent dragEvent) {
        return true;
    }

    protected void refreshSourceView(View view, boolean z, boolean z2, Object obj) {
    }

    protected void refreshTargetView(View view, boolean z) {
    }

    @Override // android.view.View.OnDragListener
    public boolean onDrag(View view, DragEvent dragEvent) {
        boolean zNeedHandleEvent = needHandleEvent(dragEvent);
        switch (dragEvent.getAction()) {
            case 1:
                return handleDragStarted(view, dragEvent);
            case 2:
                handleDragLocation(view, dragEvent);
                return zNeedHandleEvent;
            case 3:
                return handleDragDrop(view, dragEvent);
            case 4:
                return handleDragEnd(view, dragEvent);
            case 5:
                return handleDragEntered(view, dragEvent);
            case 6:
                handleDragExit(view, dragEvent);
                return zNeedHandleEvent;
            default:
                return false;
        }
    }
}
