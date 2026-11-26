package com.miui.notes.editor;

import com.miui.notes.base.utils.Logger;
import com.miui.notes.editor.UndoOperation;
import java.util.ArrayList;

/* JADX INFO: loaded from: classes4.dex */
public class UndoManager<OP extends UndoOperation> {
    private static final String TAG = "UndoManager";
    private boolean mApplying;
    private int mCapacity;
    private UndoOperationCreator<OP> mCreator;
    private ArrayList<OP> mOperations;
    private int mPosition = 0;
    private UndoStateChangedListener mStateListener;
    private OP mWorking;

    public interface UndoOperationCreator<T extends UndoOperation> {
        T createOperation();
    }

    public interface UndoStateChangedListener {
        void onUndoStateChanged(UndoManager<?> undoManager);
    }

    public UndoManager(UndoOperationCreator<OP> undoOperationCreator, int i) {
        this.mCreator = undoOperationCreator;
        this.mOperations = new ArrayList<>(i);
        this.mCapacity = i;
    }

    public void setUndoStateChangedListener(UndoStateChangedListener undoStateChangedListener) {
        this.mStateListener = undoStateChangedListener;
    }

    public void notifyStateChanged() {
        OP op = this.mWorking;
        if (op != null && op.hasData()) {
            while (this.mPosition < this.mOperations.size()) {
                this.mOperations.remove(this.mPosition);
            }
        }
        UndoStateChangedListener undoStateChangedListener = this.mStateListener;
        if (undoStateChangedListener != null) {
            undoStateChangedListener.onUndoStateChanged(this);
        }
    }

    public OP getOperation() {
        if (this.mWorking == null) {
            this.mWorking = (OP) this.mCreator.createOperation();
        }
        return this.mWorking;
    }

    public void commit() {
        commitInternally();
        notifyStateChanged();
    }

    private void commitInternally() {
        OP op = this.mWorking;
        if (op == null || !op.hasData()) {
            return;
        }
        this.mWorking.commit();
        while (this.mPosition < this.mOperations.size()) {
            this.mOperations.remove(this.mPosition);
        }
        if (this.mOperations.size() == this.mCapacity) {
            this.mOperations.remove(0);
            this.mPosition--;
        }
        this.mOperations.add(this.mWorking);
        this.mPosition++;
        this.mWorking = null;
    }

    public void undo() {
        commitInternally();
        this.mApplying = true;
        int i = this.mPosition;
        if (i > 0) {
            this.mOperations.get(i - 1).undo();
            this.mPosition--;
        } else {
            Logger.INSTANCE.w(TAG, "Invalid position when undo: " + this.mPosition);
        }
        this.mApplying = false;
        this.mWorking = null;
        notifyStateChanged();
    }

    public void redo() {
        this.mApplying = true;
        if (this.mPosition < this.mOperations.size()) {
            this.mOperations.get(this.mPosition).redo();
            this.mPosition++;
        } else {
            Logger.INSTANCE.w(TAG, "Invalid position when undo: " + this.mPosition);
        }
        this.mApplying = false;
        this.mWorking = null;
        notifyStateChanged();
    }

    public boolean canUndo() {
        if (this.mPosition > 0) {
            return true;
        }
        OP op = this.mWorking;
        return op != null && op.hasData();
    }

    public boolean canRedo() {
        return this.mPosition < this.mOperations.size();
    }

    public void clear() {
        this.mOperations.clear();
        this.mPosition = 0;
        this.mWorking = null;
        notifyStateChanged();
    }

    public boolean isApplying() {
        return this.mApplying;
    }
}
