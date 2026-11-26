package com.miui.libhandwritingpage.utils;

import android.util.Log;
import com.miui.libhandwritingpage.listener.PageStateListener;
import com.miui.libhandwritingpage.model.PageInkModel;
import com.miui.libhandwritingpage.newbean.beanPageInfo.PageItemData;
import com.miui.libhandwritingpage.newbean.beanStep.StepInfo;
import com.miui.libhandwritingpage.view.PageItemView;
import com.miui.libhandwritingpage.view.select.SelectViewGroup;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;
import xc.engine.api.CreationPageSDK;

/* JADX INFO: loaded from: classes3.dex */
public class PageStepHelper {
    private static final String TAG = "PageStepHelper";
    private PageInkModel mPageInkModel;
    private StepInfo mStepInfo;
    private SelectViewGroup selectView;
    private PageStateListener stateListener;
    private final List<StepInfo> undoList = new ArrayList(0);
    private final List<StepInfo> redoList = new ArrayList(0);
    private StepInfo undoStepInfo = null;
    private StepInfo redoStepInfo = null;
    private int stepCount = 0;

    public interface NoVisibleHandleListener {
        void onHandle(PageItemData pageItemData);
    }

    public PageStepHelper(PageInkModel pageInkModel, PageStateListener pageStateListener) {
        this.mPageInkModel = pageInkModel;
        this.stateListener = pageStateListener;
    }

    public void setPageStateListener(PageStateListener pageStateListener) {
        this.stateListener = pageStateListener;
    }

    public void setStepCount(int i) {
        this.stepCount = i;
    }

    public void setStepChangedCur(int i, int i2) {
        setStepChangedCur(i, i2, true);
    }

    public void setStepChangedCur(int i, int i2, boolean z) {
        String str = TAG;
        Log.d(str, "setStepChangedCur stepType " + i + "; position " + i2);
        List<PageItemData> itemDataList = this.mPageInkModel.getItemDataList();
        if (this.mPageInkModel == null || itemDataList == null || itemDataList.size() <= i2) {
            return;
        }
        updateByCurChange(i, i2, itemDataList);
        if (this.stateListener != null) {
            Log.d(str, "setStepChangedCur stateListener undoList size  " + this.undoList.size() + "; redoList size  " + this.redoList.size());
            this.stateListener.onStepChanged(i, i2, !this.undoList.isEmpty(), !this.redoList.isEmpty(), z);
            this.stateListener.onContentChanged(i2);
        }
    }

    private void updateByCurChange(int i, int i2, List<PageItemData> list) {
        StepInfo stepInfo;
        StepInfo stepInfo2;
        StepInfo stepInfo3;
        StepInfo stepInfo4;
        String str = TAG;
        Log.i(str, "updateByCurChange: stepType = " + i);
        if (i == 2 || i == 7) {
            Log.d(str, "updateByCurChange stepCount " + this.stepCount + "; mStepInfo " + this.mStepInfo);
            int i3 = this.stepCount;
            if (i3 == 1 && (stepInfo = this.mStepInfo) != null) {
                this.stepCount = i3 - 1;
                stepInfo.stepType = 2;
            } else {
                stepInfo = new StepInfo();
                stepInfo.stepType = 0;
                this.undoList.add(stepInfo);
            }
            stepInfo.isAboutAI = i == 7;
            stepInfo.itemData = list.get(i2);
            this.redoList.clear();
            return;
        }
        if (i == 6) {
            int i4 = this.stepCount;
            if (i4 == 1 && (stepInfo4 = this.mStepInfo) != null) {
                this.stepCount = i4 - 1;
                stepInfo4.stepType = 2;
            } else {
                stepInfo4 = new StepInfo();
                stepInfo4.isPageSize = true;
                stepInfo4.stepType = 0;
                this.undoList.add(stepInfo4);
            }
            stepInfo4.itemData = list.get(i2);
            this.redoList.clear();
            return;
        }
        if (i != 5) {
            if (i == 0 && (stepInfo3 = this.undoStepInfo) != null && stepInfo3.stepType == 2) {
                this.undoStepInfo = null;
                return;
            } else {
                if (i == 1 && (stepInfo2 = this.redoStepInfo) != null && stepInfo2.stepType == 2) {
                    this.redoStepInfo = null;
                    return;
                }
                return;
            }
        }
        Log.d(str, "updateByCurChange stepCount " + this.stepCount + "; mStepInfo " + this.mStepInfo + "; undoList.size " + this.undoList.size() + "; redoList.size  " + this.redoList.size());
        for (int size = this.undoList.size() - 1; size >= 0; size--) {
            if (this.undoList.get(size).isPageSize) {
                this.undoList.remove(size);
            }
        }
        for (int size2 = this.redoList.size() - 1; size2 >= 0; size2--) {
            if (this.redoList.get(size2).isPageSize) {
                this.redoList.remove(size2);
            }
        }
        Log.d(TAG, "updateByCurChange STEP_REMOVE_PAGE_SIZE  undoList.size " + this.undoList.size() + "; redoList.size  " + this.redoList.size());
    }

    public void setStepChangedSpanned(int i, int i2) {
        setStepChangedSpanned(i, i2, true);
    }

    public void setStepChangedSpanned(int i, int i2, boolean z) {
        List<PageItemData> itemDataList;
        int i3;
        PageInkModel pageInkModel = this.mPageInkModel;
        if (pageInkModel == null || (itemDataList = pageInkModel.getItemDataList()) == null || itemDataList.size() <= i2) {
            return;
        }
        if (i == 0 || i == 3) {
            StepInfo stepInfo = new StepInfo();
            this.mStepInfo = stepInfo;
            int i4 = this.stepCount;
            if (i4 == 2) {
                this.stepCount = i4 - 1;
                stepInfo.stepType = 2;
            } else if (i4 == 1) {
                this.stepCount = i4 - 1;
                stepInfo.stepType = 1;
            } else {
                stepInfo.stepType = 1;
            }
            this.mStepInfo.isAboutAI = i == 3;
            this.mStepInfo.itemData = itemDataList.get(i2);
            this.undoList.add(this.mStepInfo);
            this.redoList.clear();
        }
        Log.i(TAG, "setStepChangedSpan stateListener undoList size  " + this.undoList.size() + "; redoList size  " + this.redoList.size());
        if (i == 0) {
            i3 = 2;
        } else if (i != 1) {
            i3 = i != 2 ? 3 : 1;
        } else {
            i3 = 0;
        }
        PageStateListener pageStateListener = this.stateListener;
        if (pageStateListener != null) {
            if (i3 == 2 && this.stepCount == 1) {
                return;
            }
            pageStateListener.onStepChanged(i3, i2, !this.undoList.isEmpty(), !this.redoList.isEmpty(), z);
            this.stateListener.onContentChanged(i2);
        }
    }

    public void removeStep(final int i) {
        if (this.undoList.isEmpty()) {
            return;
        }
        Log.i(TAG, "removeStep: undoList size = " + this.undoList.size());
        this.undoList.removeIf(new Predicate() { // from class: com.miui.libhandwritingpage.utils.PageStepHelper$$ExternalSyntheticLambda2
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return PageStepHelper.lambda$removeStep$0(i, (StepInfo) obj);
            }
        });
        this.redoList.clear();
    }

    static /* synthetic */ boolean lambda$removeStep$0(int i, StepInfo stepInfo) {
        int pageIndex = stepInfo.itemData.getPageIndex();
        Log.i(TAG, "removeStep: curPageIndex = " + pageIndex + ", delPageIndex: " + i);
        return pageIndex == i;
    }

    public void setSelectView(SelectViewGroup selectViewGroup) {
        this.selectView = selectViewGroup;
    }

    public boolean canUndo() {
        Log.d(TAG, "canUndo " + this.undoList.size());
        return this.undoList.size() > 0;
    }

    public boolean canRedo() {
        return this.redoList.size() > 0;
    }

    public void undo(NoVisibleHandleListener noVisibleHandleListener) {
        PageStateListener pageStateListener;
        int i;
        if (canUndo()) {
            int size = this.undoList.size();
            StepInfo stepInfoRemove = this.undoList.remove(size - 1);
            boolean z = stepInfoRemove == null || !stepInfoRemove.isAboutAI;
            stepUndo(noVisibleHandleListener, stepInfoRemove, z);
            if (stepInfoRemove.isAboutAI && (i = size - 2) >= 0 && this.undoList.get(i) != null && this.undoList.get(i).isAboutAI) {
                stepUndo(noVisibleHandleListener, this.undoList.remove(i), true);
            } else {
                if (z || (pageStateListener = this.stateListener) == null) {
                    return;
                }
                pageStateListener.onStepChanged(0, getPositionByItemData(stepInfoRemove.itemData), this.undoList.size() > 0, this.redoList.size() > 0, true);
            }
        }
    }

    public void stepUndo(NoVisibleHandleListener noVisibleHandleListener, StepInfo stepInfo, boolean z) {
        this.redoList.add(stepInfo);
        if (stepInfo != null) {
            int i = stepInfo.stepType;
            if (i == 0) {
                undoCur(stepInfo, true, noVisibleHandleListener, z);
                return;
            }
            if (i == 1) {
                undoSpanned(stepInfo, z);
            } else {
                if (i != 2) {
                    return;
                }
                undoCur(stepInfo, false, noVisibleHandleListener, z);
                undoSpanned(stepInfo, z);
                this.undoStepInfo = stepInfo;
            }
        }
    }

    public void redo(NoVisibleHandleListener noVisibleHandleListener) {
        PageStateListener pageStateListener;
        int i;
        if (canRedo()) {
            int size = this.redoList.size();
            StepInfo stepInfoRemove = this.redoList.remove(size - 1);
            boolean z = stepInfoRemove == null || !stepInfoRemove.isAboutAI;
            stepRedo(noVisibleHandleListener, stepInfoRemove, z);
            if (stepInfoRemove.isAboutAI && (i = size - 2) >= 0 && this.redoList.get(i) != null && this.redoList.get(i).isAboutAI) {
                stepRedo(noVisibleHandleListener, this.redoList.remove(i), true);
            } else {
                if (z || (pageStateListener = this.stateListener) == null) {
                    return;
                }
                pageStateListener.onStepChanged(1, getPositionByItemData(stepInfoRemove.itemData), this.undoList.size() > 0, this.redoList.size() > 0, true);
            }
        }
    }

    public void stepRedo(NoVisibleHandleListener noVisibleHandleListener, StepInfo stepInfo, boolean z) {
        this.undoList.add(stepInfo);
        if (stepInfo != null) {
            int i = stepInfo.stepType;
            if (i == 0) {
                redoCur(stepInfo, true, noVisibleHandleListener, z);
                return;
            }
            if (i == 1) {
                redoSpanned(stepInfo, z);
            } else {
                if (i != 2) {
                    return;
                }
                redoCur(stepInfo, false, noVisibleHandleListener, z);
                redoSpanned(stepInfo, z);
                this.redoStepInfo = stepInfo;
            }
        }
    }

    private void undoCur(StepInfo stepInfo, boolean z, NoVisibleHandleListener noVisibleHandleListener, boolean z2) {
        PageInkModel pageInkModel;
        if (getPositionByItemData(stepInfo.itemData) == -1 || (pageInkModel = this.mPageInkModel) == null) {
            return;
        }
        PageItemView currentItemView = pageInkModel.getCurrentItemView();
        if (currentItemView != null) {
            currentItemView.undo(z2);
            if (z) {
                currentItemView.resetSelectData();
                return;
            }
            return;
        }
        noVisibleHandleListener.onHandle(stepInfo.itemData);
    }

    private void undoSpanned(StepInfo stepInfo, boolean z) {
        PageInkModel pageInkModel;
        PageItemView currentItemView;
        if (stepInfo.itemData.spannedData == null) {
            if (getPositionByItemData(stepInfo.itemData) == -1 || (pageInkModel = this.mPageInkModel) == null || (currentItemView = pageInkModel.getCurrentItemView()) == null) {
                return;
            }
            currentItemView.undoSpanned(z);
            return;
        }
        stepInfo.itemData.spannedData.undo();
        setStepChangedSpanned(1, -1, z);
    }

    private void redoCur(StepInfo stepInfo, boolean z, NoVisibleHandleListener noVisibleHandleListener, boolean z2) {
        PageInkModel pageInkModel;
        if (getPositionByItemData(stepInfo.itemData) == -1 || (pageInkModel = this.mPageInkModel) == null) {
            return;
        }
        PageItemView currentItemView = pageInkModel.getCurrentItemView();
        if (currentItemView != null) {
            currentItemView.redo(z2);
            if (z) {
                currentItemView.resetSelectData();
                return;
            }
            return;
        }
        noVisibleHandleListener.onHandle(stepInfo.itemData);
    }

    private void redoSpanned(StepInfo stepInfo, final boolean z) {
        PageInkModel pageInkModel;
        if (stepInfo.itemData.spannedData == null) {
            if (getPositionByItemData(stepInfo.itemData) == -1 || (pageInkModel = this.mPageInkModel) == null) {
                return;
            }
            final PageItemView currentItemView = pageInkModel.getCurrentItemView();
            CreationPageSDK.with(currentItemView.getContext()).post(new Runnable() { // from class: com.miui.libhandwritingpage.utils.PageStepHelper$$ExternalSyntheticLambda3
                @Override // java.lang.Runnable
                public final void run() {
                    currentItemView.redoSpanned(z);
                }
            });
            return;
        }
        stepInfo.itemData.spannedData.redo();
        setStepChangedSpanned(2, -1, z);
    }

    private int getPositionByItemData(PageItemData pageItemData) {
        List<PageItemData> itemDataList;
        PageInkModel pageInkModel = this.mPageInkModel;
        if (pageInkModel != null && (itemDataList = pageInkModel.getItemDataList()) != null && itemDataList.size() != 0) {
            for (int i = 0; i < itemDataList.size(); i++) {
                if (itemDataList.get(i) == pageItemData) {
                    return i;
                }
            }
        }
        return -1;
    }

    static /* synthetic */ boolean lambda$removeAllStepByViewId$2(int i, StepInfo stepInfo) {
        return stepInfo.itemData != null && i == stepInfo.itemData.viewId;
    }

    public void removeAllStepByViewId(int i, final int i2) {
        if (i2 == 0) {
            return;
        }
        this.undoList.removeIf(new Predicate() { // from class: com.miui.libhandwritingpage.utils.PageStepHelper$$ExternalSyntheticLambda0
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return PageStepHelper.lambda$removeAllStepByViewId$2(i2, (StepInfo) obj);
            }
        });
        this.redoList.removeIf(new Predicate() { // from class: com.miui.libhandwritingpage.utils.PageStepHelper$$ExternalSyntheticLambda1
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return PageStepHelper.lambda$removeAllStepByViewId$3(i2, (StepInfo) obj);
            }
        });
        PageStateListener pageStateListener = this.stateListener;
        if (pageStateListener != null) {
            pageStateListener.onStepChanged(4, i, this.undoList.size() > 0, this.redoList.size() > 0, true);
            this.stateListener.onContentChanged(i);
        }
    }

    static /* synthetic */ boolean lambda$removeAllStepByViewId$3(int i, StepInfo stepInfo) {
        return stepInfo.itemData != null && i == stepInfo.itemData.viewId;
    }

    public void release() {
        this.undoList.clear();
        this.redoList.clear();
    }

    public PageInkModel getPageInkModel() {
        return this.mPageInkModel;
    }
}
