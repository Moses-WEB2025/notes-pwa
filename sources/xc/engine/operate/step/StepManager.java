package xc.engine.operate.step;

import android.graphics.RectF;
import android.util.Log;
import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;
import xc.engine.api.PenEngine;
import xc.engine.core.graphics.engine.systems.MathSystem;
import xc.engine.operate.edit.IEditListener;
import xc.engine.operate.step.type.IStep;
import xc.engine.operate.step.type.StepChangePageSize;
import xc.engine.operate.step.type.StepEditClear;
import xc.engine.operate.step.type.StepEditDelete;
import xc.engine.utils.PageSizeConfig;

/* JADX INFO: loaded from: classes6.dex */
public class StepManager extends IStepManager {
    private static final String TAG = "StepManager";
    public final PenEngine mPenEngine;
    public final List<IStep> mUndoList = new ArrayList();
    public final List<IStep> mRedoList = new ArrayList();

    public StepManager(PenEngine penEngine) {
        this.mPenEngine = penEngine;
    }

    @Override // xc.engine.operate.step.IStepManager
    public int getUndoSize() {
        return this.mUndoList.size();
    }

    @Override // xc.engine.operate.step.IStepManager
    public void undo() {
        undo(true);
    }

    @Override // xc.engine.operate.step.IStepManager
    public void undo(final boolean z) {
        if (this.mPenEngine.isRelease()) {
            return;
        }
        Log.d(TAG, "undo ");
        this.mPenEngine.mPenEngineHandlerWrapper.sendMessage(new Runnable() { // from class: xc.engine.operate.step.StepManager$$ExternalSyntheticLambda1
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.m4652lambda$undo$0$xcengineoperatestepStepManager(z);
            }
        });
    }

    /* JADX INFO: renamed from: lambda$undo$0$xc-engine-operate-step-StepManager, reason: not valid java name */
    /* synthetic */ void m4652lambda$undo$0$xcengineoperatestepStepManager(boolean z) {
        int size = this.mUndoList.size();
        String str = TAG;
        Log.d(str, "undo size " + this.mUndoList.size());
        if (size > 0) {
            IStep iStep = this.mUndoList.get(size - 1);
            this.mUndoList.remove(iStep);
            this.mRedoList.add(iStep);
            long jCurrentTimeMillis = System.currentTimeMillis();
            RectF rectFUndo = iStep.undo();
            Log.d(str, "undo " + iStep.getClass().getSimpleName() + ",undo cost " + (System.currentTimeMillis() - jCurrentTimeMillis));
            MathSystem mathSystem = this.mPenEngine.getCurveEngine().getMathSystem();
            if (mathSystem.getHasJustCleared()) {
                mathSystem.setFromUndoRedo(true);
                mathSystem.setHasJustCleared(false);
            }
            long jCurrentTimeMillis2 = System.currentTimeMillis();
            refreshData(rectFUndo, false);
            Log.d(str, "undo refreshData cost " + (System.currentTimeMillis() - jCurrentTimeMillis2));
            IEditListener iEditListener = this.mPenEngine.mEditOperator.mEditListener;
            if (iEditListener != null) {
                if (iStep instanceof StepChangePageSize) {
                    PageSizeConfig pageSizeConfig = ((StepChangePageSize) iStep).undoConfig;
                    pageSizeConfig.isChangeFromUndo = true;
                    Log.d(str, "undo StepChangePageSize " + pageSizeConfig);
                    iEditListener.onPageSizeChanged(pageSizeConfig);
                }
                iEditListener.onStepChanged(0, iStep.getPageIndex(), z);
            } else {
                Log.d(str, "undo iEditListener == null ");
            }
            this.mPenEngine.getCurveEngine().getMathDataManager().setBehavior(2);
        }
    }

    @Override // xc.engine.operate.step.IStepManager
    public void redo() {
        redo(true);
    }

    @Override // xc.engine.operate.step.IStepManager
    public void redo(final boolean z) {
        if (this.mPenEngine.isRelease()) {
            return;
        }
        Log.d(TAG, "redo  ");
        this.mPenEngine.mPenEngineHandlerWrapper.sendMessage(new Runnable() { // from class: xc.engine.operate.step.StepManager$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.m4651lambda$redo$1$xcengineoperatestepStepManager(z);
            }
        });
    }

    /* JADX INFO: renamed from: lambda$redo$1$xc-engine-operate-step-StepManager, reason: not valid java name */
    /* synthetic */ void m4651lambda$redo$1$xcengineoperatestepStepManager(boolean z) {
        int size = this.mRedoList.size();
        String str = TAG;
        Log.d(str, "redo size " + this.mRedoList.size());
        if (size > 0) {
            IStep iStep = this.mRedoList.get(size - 1);
            this.mRedoList.remove(iStep);
            this.mUndoList.add(iStep);
            long jCurrentTimeMillis = System.currentTimeMillis();
            RectF rectFRedo = iStep.redo();
            Log.d(str, "redo " + iStep.getClass().getSimpleName() + ",redo cost " + (System.currentTimeMillis() - jCurrentTimeMillis));
            long jCurrentTimeMillis2 = System.currentTimeMillis();
            refreshData(rectFRedo, false);
            Log.d(str, "redo refreshData cost " + (System.currentTimeMillis() - jCurrentTimeMillis2));
            IEditListener iEditListener = this.mPenEngine.mEditOperator.mEditListener;
            if (iEditListener != null) {
                if (iStep instanceof StepChangePageSize) {
                    StepChangePageSize stepChangePageSize = (StepChangePageSize) iStep;
                    PageSizeConfig pageSizeConfig = stepChangePageSize.redoConfig;
                    pageSizeConfig.isChangeFromUndo = false;
                    Log.d(str, "redo StepChangePageSize " + pageSizeConfig);
                    iEditListener.onPageSizeChanged(stepChangePageSize.redoConfig);
                }
                iEditListener.onStepChanged(1, iStep.getPageIndex(), z);
            } else {
                Log.d(str, "redo iEditListener == null ");
            }
            this.mPenEngine.getCurveEngine().getMathDataManager().setBehavior(2);
        }
    }

    @Override // xc.engine.operate.step.IStepManager
    public List<IStep> getRedoList() {
        return this.mRedoList;
    }

    @Override // xc.engine.operate.step.IStepManager
    public List<IStep> getUndoList() {
        return this.mUndoList;
    }

    @Override // xc.engine.operate.step.IStepManager
    public void addRedoList(List<IStep> list) {
        Log.d(TAG, "addRedoList");
        if (list == null || list.isEmpty()) {
            return;
        }
        this.mRedoList.addAll(list);
    }

    @Override // xc.engine.operate.step.IStepManager
    public void addUndoList(List<IStep> list) {
        String str = TAG;
        Log.d(str, "addUndoList");
        if (list == null || list.isEmpty()) {
            return;
        }
        Log.d(str, "addUndoList undo size " + this.mUndoList.size());
        this.mUndoList.addAll(list);
    }

    @Override // xc.engine.operate.step.IStepManager
    public void addStep(IStep iStep) {
        this.mUndoList.add(iStep);
        this.mRedoList.clear();
        IEditListener iEditListener = this.mPenEngine.mEditOperator.mEditListener;
        if (iEditListener != null) {
            if (iStep instanceof StepChangePageSize) {
                iEditListener.onStepChanged(6, iStep.getPageIndex());
            } else if (iStep instanceof StepEditDelete) {
                int pageIndex = iStep.getPageIndex();
                ListIterator<IStep> listIterator = this.mUndoList.listIterator();
                while (listIterator.hasNext()) {
                    IStep next = listIterator.next();
                    int pageIndex2 = next.getPageIndex();
                    if (pageIndex2 == pageIndex) {
                        listIterator.remove();
                    } else if (pageIndex2 > pageIndex) {
                        next.setPageIndex(pageIndex2 - 1);
                    }
                }
                iEditListener.onStepChanged(8, iStep.getPageIndex());
            } else {
                iEditListener.onStepChanged(2, iStep.getPageIndex());
            }
        }
        if (iStep instanceof StepEditClear) {
            this.mPenEngine.getCurveEngine().getTextSystem().markDirty();
            this.mPenEngine.getCurveEngine().getImgSystem().markDirty();
        }
        Log.d(TAG, "addStep undo size " + this.mUndoList.size());
    }

    @Override // xc.engine.operate.step.IStepManager
    public void addStepForAI(IStep iStep) {
        this.mUndoList.add(iStep);
        this.mRedoList.clear();
        IEditListener iEditListener = this.mPenEngine.mEditOperator.mEditListener;
        if (iEditListener != null) {
            iEditListener.onStepChanged(7, iStep.getPageIndex());
        }
        Log.d(TAG, "addStep undo size " + this.mUndoList.size());
    }

    @Override // xc.engine.operate.step.IStepManager
    public boolean canUndo() {
        return !this.mUndoList.isEmpty();
    }

    private void refreshData(RectF rectF, Boolean bool) {
        float drawRatio = this.mPenEngine.mCanvasOperator.getDrawRatio();
        this.mPenEngine.getCurveEngine().getCurveSystem().updateArea(new RectF(rectF.left * drawRatio, rectF.top * drawRatio, rectF.right * drawRatio, rectF.bottom * drawRatio), true, true, bool.booleanValue());
    }

    @Override // xc.engine.operate.step.IStepManager
    public boolean canRedo() {
        return !this.mRedoList.isEmpty();
    }

    @Override // xc.engine.operate.step.IStepManager
    public void clearStep() {
        this.mUndoList.clear();
        this.mRedoList.clear();
    }

    @Override // xc.engine.operate.step.IStepManager
    public void removeChangePageSizeStep(boolean z) {
        IEditListener iEditListener;
        IEditListener iEditListener2;
        int size = this.mUndoList.size() - 1;
        boolean z2 = false;
        boolean z3 = false;
        int pageIndex = 0;
        while (size >= 0) {
            IStep iStep = this.mUndoList.get(size);
            if (iStep instanceof StepChangePageSize) {
                StepChangePageSize stepChangePageSize = (StepChangePageSize) iStep;
                Log.d(TAG, "removeChangePageSizeStep undo size " + this.mUndoList.size() + "; isUnLimit " + stepChangePageSize.undoConfig.isUnLimit + "; index " + size + "; isUnLimitStepChangePageSize " + z3);
                pageIndex = iStep.getPageIndex();
                if (z3) {
                    this.mUndoList.remove(size);
                } else if (!z || stepChangePageSize.undoConfig.isUnLimit) {
                    this.mUndoList.remove(size);
                    z3 = true;
                }
            }
            size--;
        }
        Log.d(TAG, "removeChangePageSizeStep isUndoChangePageSize " + z3);
        if (z3 && (iEditListener2 = this.mPenEngine.mEditOperator.mEditListener) != null) {
            iEditListener2.onStepChanged(5, pageIndex);
        }
        int size2 = this.mRedoList.size() - 1;
        while (size2 >= 0) {
            IStep iStep2 = this.mRedoList.get(size2);
            if (iStep2 instanceof StepChangePageSize) {
                pageIndex = iStep2.getPageIndex();
                StepChangePageSize stepChangePageSize2 = (StepChangePageSize) iStep2;
                Log.d(TAG, "removeChangePageSizeStep undo size " + this.mRedoList.size() + "; isUnLimit " + stepChangePageSize2.redoConfig.isUnLimit + "; index " + size2 + "; isRedoChangePageSize " + z2);
                if (z2) {
                    this.mRedoList.remove(size2);
                } else if (!z || stepChangePageSize2.redoConfig.isUnLimit) {
                    this.mRedoList.remove(size2);
                    z2 = true;
                }
            }
            size2--;
        }
        Log.d(TAG, "removeChangePageSizeStep isRedoChangePageSize " + z2);
        if (!z2 || (iEditListener = this.mPenEngine.mEditOperator.mEditListener) == null) {
            return;
        }
        iEditListener.onStepChanged(5, pageIndex);
    }
}
