package com.miui.libhandwritingpage.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;
import com.miui.libhandwritingpage.listener.PageStateListener;
import com.miui.libhandwritingpage.listener.StepChangedListener;
import com.miui.libhandwritingpage.model.PageInkModel;
import com.miui.libhandwritingpage.newbean.beanPageInfo.PageItemData;
import com.miui.libhandwritingpage.other.DataBindHandler;
import com.miui.libhandwritingpage.utils.PageStepHelper;
import com.miui.libhandwritingpage.view.PageDrawView;
import com.miui.libhandwritingpage.view.PageItemView;
import java.io.File;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import xc.engine.api.CreationPageSDK;
import xc.engine.api.PenEngine;
import xc.engine.utils.FileUtil;
import xc.engine.utils.PageSizeConfig;

/* JADX INFO: loaded from: classes3.dex */
public class PageAdapter {
    private static final String TAG = "PageAdapter";
    private boolean isStepping;
    private final WeakReference<Context> mContextRef;
    private final WeakReference<PageInkModel> mModelRef;
    private final PageStepHelper stepHelper;
    private String entFilePath = "";
    private final Map<PageItemView, PageItemData> taskMap = new HashMap();
    int viewIndex = 0;
    private int searchPageIndex = -1;
    private int exportIndex = -1;
    private final DataBindHandler dataBindHandler = new DataBindHandler(this);

    public PageAdapter(Context context, PageInkModel pageInkModel, PageStateListener pageStateListener) {
        this.mContextRef = new WeakReference<>(context);
        this.mModelRef = new WeakReference<>(pageInkModel);
        this.stepHelper = new PageStepHelper(pageInkModel, pageStateListener);
    }

    public void onTaskFinish(final PageItemView pageItemView, final PageItemData pageItemData, boolean z) {
        if (z) {
            final CompletableFuture completableFuture = new CompletableFuture();
            CreationPageSDK.with(this.mContextRef.get()).post(new Runnable() { // from class: com.miui.libhandwritingpage.adapter.PageAdapter$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.m837xb8b5a119(pageItemView, pageItemData, completableFuture);
                }
            });
            try {
                completableFuture.get();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /* JADX INFO: renamed from: lambda$onTaskFinish$0$com-miui-libhandwritingpage-adapter-PageAdapter, reason: not valid java name */
    /* synthetic */ void m837xb8b5a119(PageItemView pageItemView, PageItemData pageItemData, CompletableFuture completableFuture) {
        pageItemView.bindEngineData(pageItemData);
        this.taskMap.remove(pageItemView);
        completableFuture.complete(null);
    }

    public void setSearchPageIndex(int i) {
        this.searchPageIndex = i;
    }

    public void onLoadFinish(final PageItemView pageItemView, final PageItemData pageItemData) {
        CreationPageSDK.with(this.mContextRef.get()).post(new Runnable() { // from class: com.miui.libhandwritingpage.adapter.PageAdapter$$ExternalSyntheticLambda4
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.m834x825a337b(pageItemView, pageItemData);
            }
        });
    }

    /* JADX INFO: renamed from: lambda$onLoadFinish$1$com-miui-libhandwritingpage-adapter-PageAdapter, reason: not valid java name */
    /* synthetic */ void m834x825a337b(PageItemView pageItemView, PageItemData pageItemData) {
        if (pageItemView.getTag() == null || pageItemView.getTag() == pageItemData) {
            this.taskMap.remove(pageItemView);
        }
        if (this.mModelRef.get() != null) {
            if (this.exportIndex != -1 || pageItemData.getPageIndex() < 3) {
                this.mModelRef.get().bindPageImage(pageItemData.getPageIndex());
            }
            if (pageItemData.getPageIndex() == this.exportIndex) {
                this.exportIndex = -1;
                this.mModelRef.get().bindExportFinish();
            }
            if (this.mModelRef.get().getCurrentPosition() == pageItemData.getPageIndex()) {
                Log.i(TAG, String.format("xxxxxx onLoadFinish: page[%d] and updateStateView scale[%f]", Integer.valueOf(pageItemData.getPageIndex()), Float.valueOf(this.mModelRef.get().getLayoutManager().getScale())));
                pageItemView.getCreationEngine().getCurveEngine().getRenderService().refreshAll();
                this.mModelRef.get().updateStateView(this.mModelRef.get().getCurrentPosition());
            }
        }
    }

    public void onUndoFinish() {
        CreationPageSDK.with(this.mContextRef.get()).post(new Runnable() { // from class: com.miui.libhandwritingpage.adapter.PageAdapter$$ExternalSyntheticLambda5
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.m838xeb73cf7a();
            }
        });
    }

    /* JADX INFO: renamed from: lambda$onUndoFinish$2$com-miui-libhandwritingpage-adapter-PageAdapter, reason: not valid java name */
    /* synthetic */ void m838xeb73cf7a() {
        PageStepHelper pageStepHelper = this.stepHelper;
        if (pageStepHelper != null) {
            pageStepHelper.setStepChangedCur(0, -1);
        }
        this.isStepping = false;
    }

    public void onRedoFinish() {
        CreationPageSDK.with(this.mContextRef.get()).post(new Runnable() { // from class: com.miui.libhandwritingpage.adapter.PageAdapter$$ExternalSyntheticLambda2
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.m836x428dd495();
            }
        });
    }

    /* JADX INFO: renamed from: lambda$onRedoFinish$3$com-miui-libhandwritingpage-adapter-PageAdapter, reason: not valid java name */
    /* synthetic */ void m836x428dd495() {
        PageStepHelper pageStepHelper = this.stepHelper;
        if (pageStepHelper != null) {
            pageStepHelper.setStepChangedCur(1, -1);
        }
        this.isStepping = false;
    }

    public PageItemView onCreateItemView(PageDrawView pageDrawView) {
        PageItemView pageItemView = new PageItemView(this.mContextRef.get());
        pageItemView.model = this.mModelRef.get();
        pageItemView.setUnLimit(this.mModelRef.get().isUnlimited());
        pageItemView.init(pageDrawView);
        if (this.mModelRef.get().getItemDataList().size() != 1 || !this.mModelRef.get().getItemDataList().get(0).isContentEmpty) {
            PenEngine.setIsEmptyCreationFromCreate(false);
        }
        pageItemView.setIndex(this.viewIndex);
        this.viewIndex++;
        pageItemView.setSearchWrapper(this.mModelRef.get().getSearchWrapper());
        pageItemView.setEntPath(this.entFilePath);
        return pageItemView;
    }

    public void updateItemView(int i, int i2, int i3) {
        PageItemView currentItemView = this.mModelRef.get().getCurrentItemView();
        currentItemView.getCreationEngine().getCanvasOperator().setVisibleFixedWidth(i3 / this.mModelRef.get().getLayoutManager().getPageScaleRatio());
        currentItemView.setVisibleSize(i, i2);
        currentItemView.setStepChangedCallback(new AnonymousClass1(currentItemView));
    }

    /* JADX INFO: renamed from: com.miui.libhandwritingpage.adapter.PageAdapter$1, reason: invalid class name */
    class AnonymousClass1 implements StepChangedListener {
        final /* synthetic */ PageItemView val$itemView;

        AnonymousClass1(PageItemView pageItemView) {
            this.val$itemView = pageItemView;
        }

        @Override // com.miui.libhandwritingpage.listener.StepChangedListener
        public void onPageSizeChanged(final PageSizeConfig pageSizeConfig) {
            Log.d(PageAdapter.TAG, "onCreateItemView undoConfig " + pageSizeConfig);
            if (CreationPageSDK.sHandler != null) {
                CreationPageSDK.with((Context) PageAdapter.this.mContextRef.get()).post(new Runnable() { // from class: com.miui.libhandwritingpage.adapter.PageAdapter$1$$ExternalSyntheticLambda0
                    @Override // java.lang.Runnable
                    public final void run() {
                        this.f$0.m841xabba43da(pageSizeConfig);
                    }
                });
            }
        }

        /* JADX INFO: renamed from: lambda$onPageSizeChanged$0$com-miui-libhandwritingpage-adapter-PageAdapter$1, reason: not valid java name */
        /* synthetic */ void m841xabba43da(PageSizeConfig pageSizeConfig) {
            if (pageSizeConfig.position != -1) {
                ((PageInkModel) PageAdapter.this.mModelRef.get()).setPageSizeConfig(pageSizeConfig, true);
            }
        }

        @Override // com.miui.libhandwritingpage.listener.StepChangedListener
        public void onStepChanged(final int i, final int i2, final boolean z) {
            Log.d(PageAdapter.TAG, "onStepChanged stepType " + i + ", pageIndex = " + i2);
            if (i == 9) {
                ((PageInkModel) PageAdapter.this.mModelRef.get()).setModifyFlagToItemData(i2, true);
            } else if (CreationPageSDK.sHandler != null) {
                CreationPageSDK.TokenProxy tokenProxyWith = CreationPageSDK.with((Context) PageAdapter.this.mContextRef.get());
                final PageItemView pageItemView = this.val$itemView;
                tokenProxyWith.post(new Runnable() { // from class: com.miui.libhandwritingpage.adapter.PageAdapter$1$$ExternalSyntheticLambda1
                    @Override // java.lang.Runnable
                    public final void run() {
                        this.f$0.m843x6fd5a27f(i2, pageItemView, i, z);
                    }
                });
            }
        }

        /* JADX INFO: renamed from: lambda$onStepChanged$1$com-miui-libhandwritingpage-adapter-PageAdapter$1, reason: not valid java name */
        /* synthetic */ void m843x6fd5a27f(int i, PageItemView pageItemView, int i2, boolean z) {
            if (PageAdapter.this.mModelRef.get() == null) {
                Log.e(PageAdapter.TAG, "onStepChanged: mModelRef is null");
                return;
            }
            List<PageItemData> itemDataList = ((PageInkModel) PageAdapter.this.mModelRef.get()).getItemDataList();
            if (itemDataList.isEmpty() || i < 0 || i >= itemDataList.size()) {
                Log.e(PageAdapter.TAG, "onStepChanged: out of list bound position = " + i + ", size = " + itemDataList.size());
                return;
            }
            ((PageInkModel) PageAdapter.this.mModelRef.get()).getItemDataList().get(i).curvTotalSavePoint = pageItemView.getCreationEngine().getDataAccess().getTotalSavePoint(i);
            PageAdapter.this.stepHelper.setStepChangedCur(i2, i, z);
        }

        @Override // com.miui.libhandwritingpage.listener.StepChangedListener
        public void onSpanStepChanged(final int i, final int i2, final boolean z) {
            Log.d(PageAdapter.TAG, "onSpanStepChanged spannedStepType " + i + ", pageIndex = " + i2);
            if (i == 5) {
                ((PageInkModel) PageAdapter.this.mModelRef.get()).setModifyFlagToItemData(i2, true);
            } else {
                if (CreationPageSDK.sHandler == null || i == 4) {
                    return;
                }
                CreationPageSDK.with((Context) PageAdapter.this.mContextRef.get()).post(new Runnable() { // from class: com.miui.libhandwritingpage.adapter.PageAdapter$1$$ExternalSyntheticLambda2
                    @Override // java.lang.Runnable
                    public final void run() {
                        this.f$0.m842xf2fc9d96(i2, i, z);
                    }
                });
            }
        }

        /* JADX INFO: renamed from: lambda$onSpanStepChanged$2$com-miui-libhandwritingpage-adapter-PageAdapter$1, reason: not valid java name */
        /* synthetic */ void m842xf2fc9d96(int i, int i2, boolean z) {
            if (i != -1) {
                PageAdapter.this.stepHelper.setStepChangedSpanned(i2, i, z);
            }
        }
    }

    public void onPageSizeChanged(final PageSizeConfig pageSizeConfig) {
        Log.d(TAG, "onPageSizeChanged  " + pageSizeConfig);
        if (CreationPageSDK.sHandler != null) {
            CreationPageSDK.with(this.mContextRef.get()).post(new Runnable() { // from class: com.miui.libhandwritingpage.adapter.PageAdapter$$ExternalSyntheticLambda3
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.m835xf6121691(pageSizeConfig);
                }
            });
        }
    }

    /* JADX INFO: renamed from: lambda$onPageSizeChanged$4$com-miui-libhandwritingpage-adapter-PageAdapter, reason: not valid java name */
    /* synthetic */ void m835xf6121691(PageSizeConfig pageSizeConfig) {
        if (pageSizeConfig.position != -1) {
            this.mModelRef.get().setPageSizeConfig(pageSizeConfig, true);
        }
    }

    private void bindPageItemData(PageItemView pageItemView, PageItemData pageItemData) {
        if (pageItemData.spannedData != null) {
            if (pageItemData.hadUnBindSpanned) {
                pageItemView.bindSpannedEngineData(pageItemData);
                if (pageItemData.spannedData != null) {
                    pageItemData.spannedData.release();
                    pageItemData.spannedData = null;
                }
                pageItemData.hadUnBindSpanned = false;
            } else {
                pageItemData.spannedData.release();
                pageItemData.spannedData = null;
            }
        }
        loadData(pageItemView, pageItemData);
        startBindTask(pageItemView, pageItemData);
    }

    public void onBindItemView(PageItemView pageItemView, List<Integer> list) {
        List<PageItemData> itemDataList;
        if (this.mModelRef.get() == null || (itemDataList = this.mModelRef.get().getItemDataList()) == null || itemDataList.size() < list.size()) {
            return;
        }
        Iterator<Integer> it = list.iterator();
        while (it.hasNext()) {
            bindPageItemData(pageItemView, itemDataList.get(it.next().intValue()));
        }
    }

    public void bindItemForExport(PageItemView pageItemView, List<Integer> list) {
        this.exportIndex = list.get(list.size() - 1).intValue();
        onBindItemView(pageItemView, list);
    }

    public void unBindItemView(PageItemView pageItemView, int i, PageInkModel.IUnBindListener iUnBindListener) {
        if (this.mModelRef.get() == null) {
            iUnBindListener.unBindSuc(pageItemView, i);
            return;
        }
        List<PageItemData> itemDataList = this.mModelRef.get().getItemDataList();
        if (itemDataList == null || itemDataList.size() <= i) {
            iUnBindListener.unBindSuc(pageItemView, i);
        } else {
            startUnBindTask(pageItemView, itemDataList.get(i), i, iUnBindListener);
        }
    }

    public boolean canUndo() {
        return this.stepHelper.canUndo();
    }

    public boolean canRedo() {
        return this.stepHelper.canRedo();
    }

    public void undo() {
        Log.d(TAG, "isStepping " + this.isStepping + " ; canUndo " + canUndo());
        if (this.isStepping || !canUndo()) {
            return;
        }
        if (this.mModelRef.get() != null) {
            this.mModelRef.get().finishSelect();
        }
        this.stepHelper.undo(new PageStepHelper.NoVisibleHandleListener() { // from class: com.miui.libhandwritingpage.adapter.PageAdapter$$ExternalSyntheticLambda6
            @Override // com.miui.libhandwritingpage.utils.PageStepHelper.NoVisibleHandleListener
            public final void onHandle(PageItemData pageItemData) {
                this.f$0.m840lambda$undo$5$commiuilibhandwritingpageadapterPageAdapter(pageItemData);
            }
        });
    }

    /* JADX INFO: renamed from: lambda$undo$5$com-miui-libhandwritingpage-adapter-PageAdapter, reason: not valid java name */
    /* synthetic */ void m840lambda$undo$5$commiuilibhandwritingpageadapterPageAdapter(PageItemData pageItemData) {
        this.isStepping = true;
        this.dataBindHandler.addUndoTask(pageItemData);
    }

    public void redo() {
        if (this.isStepping || !canRedo()) {
            return;
        }
        if (this.mModelRef.get() != null) {
            this.mModelRef.get().finishSelect();
        }
        this.stepHelper.redo(new PageStepHelper.NoVisibleHandleListener() { // from class: com.miui.libhandwritingpage.adapter.PageAdapter$$ExternalSyntheticLambda1
            @Override // com.miui.libhandwritingpage.utils.PageStepHelper.NoVisibleHandleListener
            public final void onHandle(PageItemData pageItemData) {
                this.f$0.m839lambda$redo$6$commiuilibhandwritingpageadapterPageAdapter(pageItemData);
            }
        });
    }

    /* JADX INFO: renamed from: lambda$redo$6$com-miui-libhandwritingpage-adapter-PageAdapter, reason: not valid java name */
    /* synthetic */ void m839lambda$redo$6$commiuilibhandwritingpageadapterPageAdapter(PageItemData pageItemData) {
        this.isStepping = true;
        this.dataBindHandler.addRedoTask(pageItemData);
    }

    public void setPageStateListener(PageStateListener pageStateListener) {
        this.stepHelper.setPageStateListener(pageStateListener);
    }

    public PageStepHelper getStepHelper() {
        return this.stepHelper;
    }

    public void setEntFilePath(String str) {
        this.entFilePath = str;
    }

    public void startBindTask(PageItemView pageItemView, PageItemData pageItemData) {
        if (pageItemData.hadLoad) {
            return;
        }
        this.taskMap.put(pageItemView, pageItemData);
        if (TextUtils.isEmpty(pageItemData.filePath)) {
            pageItemData.filePath = new File(FileUtil.getDir(CreationPageSDK.sApplication, FileUtil.PAGE_EXPORT), UUID.randomUUID().toString()).getAbsolutePath() + ".ent";
        }
        this.dataBindHandler.addLoadTask(pageItemView, pageItemData);
    }

    private void loadData(PageItemView pageItemView, PageItemData pageItemData) {
        pageItemView.setEngineTouchConfig(this.mModelRef.get().getEngineConfig());
        String dirPath = this.mModelRef.get().getDirPath();
        if (TextUtils.isEmpty(dirPath)) {
            return;
        }
        if (!TextUtils.isEmpty(pageItemData.pdfId)) {
            pageItemView.setPdfData(new File(dirPath, pageItemData.pdfId).getAbsolutePath(), pageItemData.pdfIndex);
        } else {
            pageItemView.setPdfData(null, -1);
        }
    }

    public void startUnBindTask(PageItemView pageItemView, PageItemData pageItemData, int i, PageInkModel.IUnBindListener iUnBindListener) {
        pageItemData.isContentEmpty = isEmpty(pageItemData, pageItemView);
        pageItemData.spannedData = pageItemView.unBindSpannedEngineData(pageItemData.getPageIndex());
        pageItemData.hadUnBindSpanned = true;
        pageItemView.spannedEngineClear(pageItemData.getPageIndex());
        pageItemView.resetScale();
        if (!this.taskMap.containsKey(pageItemView)) {
            pageItemData.engineData = pageItemView.unBindEngineData();
            pageItemData.undoList = new ArrayList(pageItemView.unBindUndoList());
            pageItemData.redoList = new ArrayList(pageItemView.unBindRedoList());
            this.dataBindHandler.addUnBindEngineDataTask(pageItemView, pageItemData);
        } else if (pageItemData.engineData != null) {
            this.dataBindHandler.addUnBindTask(pageItemView, pageItemData);
        } else {
            this.dataBindHandler.addUnBindEngineDataTask(pageItemView, pageItemData);
        }
        iUnBindListener.unBindSuc(pageItemView, i);
    }

    private boolean isEmpty(PageItemData pageItemData, PageItemView pageItemView) {
        return TextUtils.isEmpty(pageItemData.pdfId) && TextUtils.isEmpty(pageItemData.bgImage) && pageItemView.isContentEmpty(pageItemData.getPageIndex());
    }

    public void release() {
        this.dataBindHandler.quitLooper();
        WeakReference<Context> weakReference = this.mContextRef;
        if (weakReference != null) {
            weakReference.clear();
        }
        WeakReference<PageInkModel> weakReference2 = this.mModelRef;
        if (weakReference2 != null) {
            weakReference2.clear();
        }
    }

    public void waitForIdle(Runnable runnable) {
        DataBindHandler dataBindHandler = this.dataBindHandler;
        if (dataBindHandler != null) {
            dataBindHandler.addWaitIdleTask(runnable);
        }
    }

    public void removeStep(int i) {
        this.stepHelper.removeStep(i);
    }
}
