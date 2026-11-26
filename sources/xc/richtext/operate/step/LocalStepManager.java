package xc.richtext.operate.step;

import android.content.Intent;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;
import xc.engine.api.CreationPageSDK;
import xc.richtext.EngineBindContext;
import xc.richtext.view.SpannedLayoutChanger;

/* JADX INFO: loaded from: classes2.dex */
public class LocalStepManager {
    private static final String RECEIVER_ACTION_RENDER = "com.miui.creation.render";
    public final EngineBindContext mEngineBindContext;
    public final List<ISpannedStep> mRedoSteplist;
    public final List<ISpannedStep> mUndoStepList;

    public LocalStepManager() {
        this.mUndoStepList = new ArrayList();
        this.mRedoSteplist = new ArrayList();
        this.mEngineBindContext = null;
    }

    public LocalStepManager(EngineBindContext engineBindContext) {
        this.mUndoStepList = new ArrayList();
        this.mRedoSteplist = new ArrayList();
        this.mEngineBindContext = engineBindContext;
    }

    public void addStep(ISpannedStep iSpannedStep) {
        addStep(iSpannedStep, 0);
    }

    public void addStepForAI(ISpannedStep iSpannedStep) {
        addStep(iSpannedStep, 3);
    }

    private void addStep(ISpannedStep iSpannedStep, int i) {
        if (this.mEngineBindContext == null) {
            return;
        }
        this.mUndoStepList.add(iSpannedStep);
        this.mRedoSteplist.clear();
        SpannedLayoutChanger spannedLayoutChanger = this.mEngineBindContext.mSpannedEditOperator.mSpannedLayoutChanger;
        if (iSpannedStep instanceof RemovePageStep) {
            final int pageIndex = iSpannedStep.getPageIndex();
            this.mUndoStepList.removeIf(new Predicate() { // from class: xc.richtext.operate.step.LocalStepManager$$ExternalSyntheticLambda0
                @Override // java.util.function.Predicate
                public final boolean test(Object obj) {
                    return LocalStepManager.lambda$addStep$0(pageIndex, (ISpannedStep) obj);
                }
            });
            i = 4;
        }
        if (spannedLayoutChanger != null) {
            spannedLayoutChanger.onStepChanged(i, iSpannedStep.getPageIndex());
        }
    }

    static /* synthetic */ boolean lambda$addStep$0(int i, ISpannedStep iSpannedStep) {
        return iSpannedStep.getPageIndex() == i;
    }

    public void undo() {
        undo(true);
    }

    public void undo(boolean z) {
        int size = this.mUndoStepList.size();
        if (size > 0) {
            ISpannedStep iSpannedStep = this.mUndoStepList.get(size - 1);
            this.mUndoStepList.remove(iSpannedStep);
            this.mRedoSteplist.add(iSpannedStep);
            int[] iArrUndo = iSpannedStep.undo();
            EngineBindContext engineBindContext = this.mEngineBindContext;
            SpannedLayoutChanger spannedLayoutChanger = engineBindContext == null ? null : engineBindContext.mSpannedEditOperator.mSpannedLayoutChanger;
            if (spannedLayoutChanger != null) {
                spannedLayoutChanger.onStepChanged(1, iSpannedStep.getPageIndex(), z);
                sendBroadCast();
                spannedLayoutChanger.onSpannedDataChanged(iArrUndo, true, true);
            }
        }
    }

    public void redo() {
        redo(true);
    }

    public void redo(boolean z) {
        int size = this.mRedoSteplist.size();
        if (size > 0) {
            ISpannedStep iSpannedStep = this.mRedoSteplist.get(size - 1);
            this.mRedoSteplist.remove(iSpannedStep);
            this.mUndoStepList.add(iSpannedStep);
            int[] iArrRedo = iSpannedStep.redo();
            EngineBindContext engineBindContext = this.mEngineBindContext;
            SpannedLayoutChanger spannedLayoutChanger = engineBindContext == null ? null : engineBindContext.mSpannedEditOperator.mSpannedLayoutChanger;
            if (spannedLayoutChanger != null) {
                spannedLayoutChanger.onStepChanged(2, iSpannedStep.getPageIndex(), z);
                sendBroadCast();
                spannedLayoutChanger.onSpannedDataChanged(iArrRedo, true, true);
            }
        }
    }

    private void sendBroadCast() {
        Intent intent = new Intent();
        intent.setAction(RECEIVER_ACTION_RENDER);
        LocalBroadcastManager.getInstance(CreationPageSDK.sApplication).sendBroadcast(intent);
    }

    public void clearAllList() {
        this.mUndoStepList.clear();
        this.mRedoSteplist.clear();
    }

    public void setUndoList(List<ISpannedStep> list) {
        this.mUndoStepList.clear();
        this.mUndoStepList.addAll(list);
    }

    public void setRedoList(List<ISpannedStep> list) {
        this.mRedoSteplist.clear();
        this.mRedoSteplist.addAll(list);
    }

    public List<ISpannedStep> getUndoList() {
        return this.mUndoStepList;
    }

    public List<ISpannedStep> getRedoList() {
        return this.mRedoSteplist;
    }

    public final ISpannedStep getLastUndoStep() {
        int size = this.mUndoStepList.size();
        if (size > 0) {
            return this.mUndoStepList.get(size - 1);
        }
        return null;
    }

    public final ISpannedStep getLastRedoStep() {
        int size = this.mRedoSteplist.size();
        if (size > 0) {
            return this.mRedoSteplist.get(size - 1);
        }
        return null;
    }

    public final void updateOffset(float f) {
        for (ISpannedStep iSpannedStep : this.mUndoStepList) {
            if (iSpannedStep instanceof MixedStep) {
                ((MixedStep) iSpannedStep).updateSpanUpdateInfoList(f);
            }
        }
    }
}
