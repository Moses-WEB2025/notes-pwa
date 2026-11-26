package xc.engine.operate.step;

import java.util.List;
import xc.engine.api.PenEngine;
import xc.engine.operate.step.type.IStep;

/* JADX INFO: loaded from: classes6.dex */
public abstract class IStepManager {
    public abstract void addRedoList(List<IStep> list);

    public abstract void addStep(IStep iStep);

    public abstract void addStepForAI(IStep iStep);

    public abstract void addUndoList(List<IStep> list);

    public abstract boolean canRedo();

    public abstract boolean canUndo();

    public abstract void clearStep();

    public abstract List<IStep> getRedoList();

    public abstract List<IStep> getUndoList();

    public abstract int getUndoSize();

    public abstract void redo();

    public abstract void redo(boolean z);

    public abstract void removeChangePageSizeStep(boolean z);

    public abstract void undo();

    public abstract void undo(boolean z);

    public static IStepManager createInstance(PenEngine penEngine) {
        return new StepManager(penEngine);
    }
}
