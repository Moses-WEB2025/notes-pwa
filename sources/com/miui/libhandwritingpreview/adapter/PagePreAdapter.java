package com.miui.libhandwritingpreview.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;
import ch.qos.logback.core.CoreConstants;
import com.market.sdk.reflect.ReflectUtilsForMiui;
import com.miui.libhandwritingpage.listener.PageStateListener;
import com.miui.libhandwritingpage.newbean.beanPageInfo.PageItemData;
import com.miui.libhandwritingpreview.DataBindPreHandler;
import com.miui.libhandwritingpreview.engine.PenPreEngine;
import com.miui.libhandwritingpreview.interfaces.PreICreationEngine;
import com.miui.libhandwritingpreview.manager.PagePreLayoutManager;
import com.miui.libhandwritingpreview.model.PageInkPreModel;
import com.miui.libhandwritingpreview.view.PagePreDrawView;
import com.miui.libhandwritingpreview.view.PagePreItemView;
import java.io.File;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.StringCompanionObject;
import xc.engine.api.CreationPageSDK;
import xc.engine.operate.canvas.ICanvasOperator;
import xc.engine.utils.FileUtil;

/* JADX INFO: compiled from: PagePreAdapter.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u0000v\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010%\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0007\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0010 \n\u0000\n\u0002\u0010!\n\u0002\b\n\n\u0002\u0018\u0002\n\u0002\b\u0002\u0018\u0000 >2\u00020\u0001:\u0001>B#\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\b\u0010\u0004\u001a\u0004\u0018\u00010\u0005\u0012\b\u0010\u0006\u001a\u0004\u0018\u00010\u0007¢\u0006\u0004\b\b\u0010\tJ \u0010\u001e\u001a\u00020\u001f2\u0006\u0010 \u001a\u00020\u00142\b\u0010!\u001a\u0004\u0018\u00010\u00152\u0006\u0010\"\u001a\u00020#J\u0016\u0010$\u001a\u00020\u001f2\u0006\u0010 \u001a\u00020\u00142\u0006\u0010!\u001a\u00020\u0015J\u0012\u0010%\u001a\u0004\u0018\u00010\u00142\b\u0010&\u001a\u0004\u0018\u00010'J\u001e\u0010(\u001a\u00020\u001f2\u0006\u0010)\u001a\u00020\u00172\u0006\u0010*\u001a\u00020\u00172\u0006\u0010+\u001a\u00020\u0017J\u0018\u0010,\u001a\u00020\u001f2\u0006\u0010 \u001a\u00020\u00142\u0006\u0010-\u001a\u00020\u0015H\u0002J\u001c\u0010.\u001a\u00020\u001f2\u0006\u0010 \u001a\u00020\u00142\f\u0010/\u001a\b\u0012\u0004\u0012\u00020\u001700J\u001c\u00101\u001a\u00020\u001f2\u0006\u0010 \u001a\u00020\u00142\f\u0010/\u001a\b\u0012\u0004\u0012\u00020\u001702J\u0010\u00103\u001a\u00020\u001f2\b\u00104\u001a\u0004\u0018\u00010\u0007J\u000e\u00105\u001a\u00020\u001f2\u0006\u00106\u001a\u00020\u0011J\u0016\u00107\u001a\u00020\u001f2\u0006\u0010 \u001a\u00020\u00142\u0006\u0010-\u001a\u00020\u0015J\u0018\u00108\u001a\u00020\u001f2\u0006\u0010 \u001a\u00020\u00142\u0006\u0010-\u001a\u00020\u0015H\u0002J\u0018\u00109\u001a\u00020#2\u0006\u0010!\u001a\u00020\u00152\u0006\u0010 \u001a\u00020\u0014H\u0002J\u0006\u0010:\u001a\u00020\u001fJ\u0010\u0010;\u001a\u00020\u001f2\b\u0010<\u001a\u0004\u0018\u00010=R\u001c\u0010\n\u001a\u0010\u0012\f\u0012\n \f*\u0004\u0018\u00010\u00030\u00030\u000bX\u0082\u0004¢\u0006\u0002\n\u0000R\u0016\u0010\r\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\u00050\u000bX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u000e\u001a\u00020\u000fX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0010\u001a\u00020\u0011X\u0082\u000e¢\u0006\u0002\n\u0000R\u001a\u0010\u0012\u001a\u000e\u0012\u0004\u0012\u00020\u0014\u0012\u0004\u0012\u00020\u00150\u0013X\u0082\u0004¢\u0006\u0002\n\u0000R\u001a\u0010\u0016\u001a\u00020\u0017X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0018\u0010\u0019\"\u0004\b\u001a\u0010\u001bR\u000e\u0010\u001c\u001a\u00020\u0017X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u001d\u001a\u00020\u0017X\u0082\u000e¢\u0006\u0002\n\u0000¨\u0006?"}, d2 = {"Lcom/miui/libhandwritingpreview/adapter/PagePreAdapter;", "", CoreConstants.CONTEXT_SCOPE_VALUE, "Landroid/content/Context;", "model", "Lcom/miui/libhandwritingpreview/model/PageInkPreModel;", "stateListener", "Lcom/miui/libhandwritingpage/listener/PageStateListener;", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "(Landroid/content/Context;Lcom/miui/libhandwritingpreview/model/PageInkPreModel;Lcom/miui/libhandwritingpage/listener/PageStateListener;)V", "mContextRef", "Ljava/lang/ref/WeakReference;", "kotlin.jvm.PlatformType", "mModelRef", "dataBindHandler", "Lcom/miui/libhandwritingpreview/DataBindPreHandler;", "entFilePath", "", "taskMap", "", "Lcom/miui/libhandwritingpreview/view/PagePreItemView;", "Lcom/miui/libhandwritingpage/newbean/beanPageInfo/PageItemData;", "viewIndex", "", "getViewIndex", "()I", "setViewIndex", "(I)V", "searchPageIndex", "exportIndex", "onTaskFinish", "", "itemView", "data", "needLoad", "", "onLoadFinish", "onCreateItemView", "drawView", "Lcom/miui/libhandwritingpreview/view/PagePreDrawView;", "updateItemView", "visibleWidth", "visibleHeight", "realWidth", "bindPageItemData", "itemData", "onBindItemView", "keyList", "", "bindItemForExport", "", "setPageStateListener", "listener", "setEntFilePath", "path", "startBindTask", "loadData", "isEmpty", "release", "waitForIdle", "runnable", "Ljava/lang/Runnable;", "Companion", "libHandWritingPreview_release"}, k = 1, mv = {2, 2, 0}, xi = 48)
public final class PagePreAdapter {
    private static final String TAG;
    private final DataBindPreHandler dataBindHandler;
    private String entFilePath;
    private int exportIndex;
    private final WeakReference<Context> mContextRef;
    private final WeakReference<PageInkPreModel> mModelRef;
    private int searchPageIndex;
    private final Map<PagePreItemView, PageItemData> taskMap;
    private int viewIndex;

    public final void setPageStateListener(PageStateListener listener) {
    }

    public PagePreAdapter(Context context, PageInkPreModel pageInkPreModel, PageStateListener pageStateListener) {
        Intrinsics.checkNotNullParameter(context, "context");
        this.mContextRef = new WeakReference<>(context);
        this.mModelRef = new WeakReference<>(pageInkPreModel);
        this.dataBindHandler = new DataBindPreHandler(this);
        this.entFilePath = "";
        this.taskMap = new HashMap();
        this.searchPageIndex = -1;
        this.exportIndex = -1;
    }

    public final int getViewIndex() {
        return this.viewIndex;
    }

    public final void setViewIndex(int i) {
        this.viewIndex = i;
    }

    public final void onTaskFinish(final PagePreItemView itemView, final PageItemData data, boolean needLoad) {
        Intrinsics.checkNotNullParameter(itemView, "itemView");
        if (needLoad) {
            final CompletableFuture completableFuture = new CompletableFuture();
            CreationPageSDK.with(this.mContextRef.get()).post(new Runnable() { // from class: com.miui.libhandwritingpreview.adapter.PagePreAdapter$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    PagePreAdapter.onTaskFinish$lambda$0(itemView, data, this, completableFuture);
                }
            });
            try {
                completableFuture.get();
            } catch (Exception e) {
                e.printStackTrace();
                Unit unit = Unit.INSTANCE;
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void onTaskFinish$lambda$0(PagePreItemView pagePreItemView, PageItemData pageItemData, PagePreAdapter pagePreAdapter, CompletableFuture completableFuture) {
        pagePreItemView.bindEngineData(pageItemData);
        pagePreAdapter.taskMap.remove(pagePreItemView);
        completableFuture.complete(null);
    }

    public final void onLoadFinish(final PagePreItemView itemView, final PageItemData data) {
        Intrinsics.checkNotNullParameter(itemView, "itemView");
        Intrinsics.checkNotNullParameter(data, "data");
        final CompletableFuture completableFuture = new CompletableFuture();
        CreationPageSDK.with(this.mContextRef.get()).post(new Runnable() { // from class: com.miui.libhandwritingpreview.adapter.PagePreAdapter$$ExternalSyntheticLambda1
            @Override // java.lang.Runnable
            public final void run() {
                PagePreAdapter.onLoadFinish$lambda$1(itemView, data, this, completableFuture);
            }
        });
        try {
            completableFuture.get();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void onLoadFinish$lambda$1(PagePreItemView pagePreItemView, PageItemData pageItemData, PagePreAdapter pagePreAdapter, CompletableFuture completableFuture) {
        PageInkPreModel pageInkPreModel;
        if (pagePreItemView.getTag() == null || pagePreItemView.getTag() == pageItemData) {
            pagePreAdapter.taskMap.remove(pagePreItemView);
        }
        if ((pagePreAdapter.exportIndex != -1 || pageItemData.getPageIndex() < 3) && (pageInkPreModel = pagePreAdapter.mModelRef.get()) != null) {
            pageInkPreModel.bindPageImage(pageItemData.getPageIndex());
        }
        if (pageItemData.getPageIndex() == pagePreAdapter.exportIndex) {
            pagePreAdapter.exportIndex = -1;
            PageInkPreModel pageInkPreModel2 = pagePreAdapter.mModelRef.get();
            if (pageInkPreModel2 != null) {
                pageInkPreModel2.bindExportFinish();
            }
        }
        if (pageItemData.getPageIndex() == pagePreAdapter.exportIndex) {
            pagePreAdapter.exportIndex = -1;
        }
        PageInkPreModel pageInkPreModel3 = pagePreAdapter.mModelRef.get();
        if (pageInkPreModel3 != null && pageInkPreModel3.getCurrentPosition() == pageItemData.getPageIndex()) {
            String str = TAG;
            StringCompanionObject stringCompanionObject = StringCompanionObject.INSTANCE;
            Integer numValueOf = Integer.valueOf(pageItemData.getPageIndex());
            PageInkPreModel pageInkPreModel4 = pagePreAdapter.mModelRef.get();
            Intrinsics.checkNotNull(pageInkPreModel4);
            PagePreLayoutManager layoutManager = pageInkPreModel4.getLayoutManager();
            String str2 = String.format("xxxxxx onLoadFinish: page[%d] and updateStateView scale[%f]", Arrays.copyOf(new Object[]{numValueOf, layoutManager != null ? Float.valueOf(layoutManager.getScale()) : null}, 2));
            Intrinsics.checkNotNullExpressionValue(str2, "format(...)");
            Log.i(str, str2);
            PreICreationEngine creationEngine = pagePreItemView.getCreationEngine();
            Intrinsics.checkNotNull(creationEngine);
            creationEngine.getCurveEngine().getRenderService().refreshAll();
            PageInkPreModel pageInkPreModel5 = pagePreAdapter.mModelRef.get();
            Intrinsics.checkNotNull(pageInkPreModel5);
            PageInkPreModel pageInkPreModel6 = pagePreAdapter.mModelRef.get();
            Intrinsics.checkNotNull(pageInkPreModel6);
            pageInkPreModel5.updateStateView(pageInkPreModel6.getCurrentPosition());
        }
        completableFuture.complete(null);
    }

    public final PagePreItemView onCreateItemView(PagePreDrawView drawView) {
        PageInkPreModel pageInkPreModel;
        Context context = this.mContextRef.get();
        if (context == null || (pageInkPreModel = this.mModelRef.get()) == null) {
            return null;
        }
        PagePreItemView pagePreItemView = new PagePreItemView(context);
        pagePreItemView.model = pageInkPreModel;
        pagePreItemView.setUnLimit(pageInkPreModel.getIsUnlimited());
        pagePreItemView.init(drawView);
        if (pageInkPreModel.getItemDataList().size() != 1 || !pageInkPreModel.getItemDataList().get(0).isContentEmpty) {
            PenPreEngine.setIsEmptyCreationFromCreate(false);
        }
        pagePreItemView.index = this.viewIndex;
        this.viewIndex++;
        pagePreItemView.setSearchWrapper(pageInkPreModel.getSearchWrapper());
        pagePreItemView.setEntPath(this.entFilePath);
        return pagePreItemView;
    }

    public final void updateItemView(int visibleWidth, int visibleHeight, int realWidth) {
        PagePreItemView currentItemView;
        ICanvasOperator canvasOperator;
        PagePreLayoutManager layoutManager;
        PageInkPreModel pageInkPreModel = this.mModelRef.get();
        if (pageInkPreModel == null || (currentItemView = pageInkPreModel.getCurrentItemView()) == null) {
            return;
        }
        PreICreationEngine creationEngine = currentItemView.getCreationEngine();
        if (creationEngine != null && (canvasOperator = creationEngine.getCanvasOperator()) != null) {
            float f = realWidth;
            PageInkPreModel pageInkPreModel2 = this.mModelRef.get();
            canvasOperator.setVisibleFixedWidth(f / ((pageInkPreModel2 == null || (layoutManager = pageInkPreModel2.getLayoutManager()) == null) ? 1.0f : layoutManager.getPageScaleRatio()));
        }
        currentItemView.setVisibleSize(visibleWidth, visibleHeight);
    }

    private final void bindPageItemData(PagePreItemView itemView, PageItemData itemData) {
        if (itemData.spannedData != null) {
            if (itemData.hadUnBindSpanned) {
                itemView.bindSpannedEngineData(itemData);
                if (itemData.spannedData != null) {
                    itemData.spannedData.release();
                    itemData.spannedData = null;
                }
                itemData.hadUnBindSpanned = false;
            } else {
                itemData.spannedData.release();
                itemData.spannedData = null;
            }
        }
        loadData(itemView, itemData);
        startBindTask(itemView, itemData);
    }

    public final void onBindItemView(PagePreItemView itemView, List<Integer> keyList) {
        Intrinsics.checkNotNullParameter(itemView, "itemView");
        Intrinsics.checkNotNullParameter(keyList, "keyList");
        new ArrayList();
        if (this.mModelRef.get() != null) {
            PageInkPreModel pageInkPreModel = this.mModelRef.get();
            Intrinsics.checkNotNull(pageInkPreModel);
            List<PageItemData> itemDataList = pageInkPreModel.getItemDataList();
            if (itemDataList == null || itemDataList.size() < keyList.size()) {
                return;
            }
            Iterator<Integer> it = keyList.iterator();
            while (it.hasNext()) {
                bindPageItemData(itemView, itemDataList.get(it.next().intValue()));
            }
        }
    }

    public final void bindItemForExport(PagePreItemView itemView, List<Integer> keyList) {
        Intrinsics.checkNotNullParameter(itemView, "itemView");
        Intrinsics.checkNotNullParameter(keyList, "keyList");
        this.exportIndex = keyList.get(keyList.size() - 1).intValue();
        onBindItemView(itemView, keyList);
    }

    public final void setEntFilePath(String path) {
        PagePreItemView currentItemView;
        Intrinsics.checkNotNullParameter(path, "path");
        this.entFilePath = path;
        PageInkPreModel pageInkPreModel = this.mModelRef.get();
        if (pageInkPreModel == null || (currentItemView = pageInkPreModel.getCurrentItemView()) == null) {
            return;
        }
        currentItemView.setEntPath(path);
    }

    public final void startBindTask(PagePreItemView itemView, PageItemData itemData) {
        Intrinsics.checkNotNullParameter(itemView, "itemView");
        Intrinsics.checkNotNullParameter(itemData, "itemData");
        if (itemData.hadLoad) {
            return;
        }
        this.taskMap.put(itemView, itemData);
        if (TextUtils.isEmpty(itemData.filePath)) {
            itemData.filePath = new File(FileUtil.getDir(CreationPageSDK.sApplication, FileUtil.PAGE_EXPORT), UUID.randomUUID().toString()).getAbsolutePath() + ".ent";
        }
        this.dataBindHandler.addLoadTask(itemView, itemData);
    }

    private final void loadData(PagePreItemView itemView, PageItemData itemData) {
        PageInkPreModel pageInkPreModel = this.mModelRef.get();
        String dirPath = pageInkPreModel != null ? pageInkPreModel.getDirPath() : null;
        if (TextUtils.isEmpty(dirPath)) {
            return;
        }
        if (!TextUtils.isEmpty(itemData.pdfId)) {
            itemView.setPdfData(new File(dirPath, itemData.pdfId).getAbsolutePath(), itemData.pdfIndex);
        } else {
            itemView.setPdfData(null, -1);
        }
    }

    private final boolean isEmpty(PageItemData data, PagePreItemView itemView) {
        return TextUtils.isEmpty(data.pdfId) && TextUtils.isEmpty(data.bgImage) && itemView.isContentEmpty(data.getPageIndex());
    }

    public final void release() {
        this.dataBindHandler.quitLooper();
        WeakReference<Context> weakReference = this.mContextRef;
        if (weakReference != null) {
            weakReference.clear();
        }
        WeakReference<PageInkPreModel> weakReference2 = this.mModelRef;
        if (weakReference2 != null) {
            weakReference2.clear();
        }
    }

    public final void waitForIdle(Runnable runnable) {
        DataBindPreHandler dataBindPreHandler = this.dataBindHandler;
        if (dataBindPreHandler != null) {
            dataBindPreHandler.addWaitIdleTask(runnable);
        }
    }

    static {
        Intrinsics.checkNotNullExpressionValue("PagePreAdapter", "getSimpleName(...)");
        TAG = "PagePreAdapter";
    }
}
