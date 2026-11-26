package com.miui.handwriting.common.taskservice;

import android.app.Service;
import android.content.Intent;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;
import com.market.sdk.reflect.ReflectUtilsForMiui;
import com.miui.creation.taskservice.ICreationTaskInterface;
import com.miui.creation.taskservice.ISearchCallback;
import com.miui.creation.taskservice.ITaskServiceCallback;
import com.miui.doodle.document.Layer;
import com.miui.libhandwritingpage.newbean.beanPageInfo.PageData;
import com.miui.libhandwritingpage.utils.PageDataUtils;
import com.xiaomi.mirror.synergy.CallMethod;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import xc.engine.core.algorithm.recognize.RecognitionThread;
import xc.engine.utils.FileUtil;

/* JADX INFO: compiled from: CreationTaskService.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u0000g\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\b\b\n\u0002\u0010\b\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\t\n\u0002\b\u000e\n\u0002\u0018\u0002\n\u0002\b\u0002*\u0001\u0016\u0018\u00002\u00020\u0001B\u0007¢\u0006\u0004\b\u0002\u0010\u0003J\u0010\u0010\f\u001a\u00020\r2\u0006\u0010\u000e\u001a\u00020\u000fH\u0016J\u0012\u0010\u0010\u001a\u00020\u00112\b\u0010\u000e\u001a\u0004\u0018\u00010\u000fH\u0016J\u0012\u0010\u0012\u001a\u00020\u00132\b\u0010\u000e\u001a\u0004\u0018\u00010\u000fH\u0016J\u0006\u0010\u0014\u001a\u00020\u0013J\\\u0010\u0018\u001a\u00020\u00132\u0006\u0010\u0019\u001a\u00020\u00052\u0006\u0010\u001a\u001a\u00020\u00052\u0006\u0010\u001b\u001a\u00020\u00052\u0006\u0010\u001c\u001a\u00020\u00112\u0006\u0010\u001d\u001a\u00020\u001e2\f\u0010\u001f\u001a\b\u0012\u0004\u0012\u00020!0 2\f\u0010\"\u001a\b\u0012\u0004\u0012\u00020\u001e0 2\u0006\u0010#\u001a\u00020\u00112\u0006\u0010$\u001a\u00020%H\u0002JJ\u0010&\u001a\u00020\u00132\u0006\u0010'\u001a\u00020\u00052\b\u0010(\u001a\u0004\u0018\u00010\u00052\u0006\u0010\u0019\u001a\u00020\u00052\f\u0010\u001f\u001a\b\u0012\u0004\u0012\u00020!0 2\u0006\u0010)\u001a\u00020*2\u0006\u0010+\u001a\u00020\u00112\b\u0010$\u001a\u0004\u0018\u00010%H\u0002J8\u0010,\u001a\u00020\u00132\u0006\u0010(\u001a\u00020\u00052\u0006\u0010\u0019\u001a\u00020\u00052\f\u0010\u001f\u001a\b\u0012\u0004\u0012\u00020!0 2\u0006\u0010)\u001a\u00020*2\b\u0010$\u001a\u0004\u0018\u00010%H\u0002JX\u0010-\u001a\u00020\u00132\b\u0010.\u001a\u0004\u0018\u00010\u00052\u0006\u0010/\u001a\u00020\u00052\f\u0010\u001f\u001a\b\u0012\u0004\u0012\u00020!0 2\u0006\u00100\u001a\u00020\u00052\u0006\u0010\u001d\u001a\u00020\u001e2\u0006\u00101\u001a\u00020\u00052\u0006\u00102\u001a\u00020\u00052\u0006\u00103\u001a\u00020\u001e2\u0006\u0010$\u001a\u00020%H\u0002J.\u00104\u001a\u00020\u00132\u0006\u00105\u001a\u00020\u00052\u0006\u00106\u001a\u00020\u00052\f\u0010\u001f\u001a\b\u0012\u0004\u0012\u00020!0 2\u0006\u0010$\u001a\u00020%H\u0002J\u0012\u00107\u001a\u00020\u00132\b\u00108\u001a\u0004\u0018\u000109H\u0002J\b\u0010:\u001a\u00020\u0013H\u0002R\u0014\u0010\u0004\u001a\u00020\u0005X\u0086D¢\u0006\b\n\u0000\u001a\u0004\b\u0006\u0010\u0007R\u000e\u0010\b\u001a\u00020\u0005X\u0082\u000e¢\u0006\u0002\n\u0000R\u0010\u0010\t\u001a\u0004\u0018\u00010\nX\u0082\u000e¢\u0006\u0002\n\u0000R\u0010\u0010\u000b\u001a\u0004\u0018\u00010\nX\u0082\u000e¢\u0006\u0002\n\u0000R\u0010\u0010\u0015\u001a\u00020\u0016X\u0082\u0004¢\u0006\u0004\n\u0002\u0010\u0017¨\u0006;"}, d2 = {"Lcom/miui/handwriting/common/taskservice/CreationTaskService;", "Landroid/app/Service;", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "()V", "TAG", "", "getTAG", "()Ljava/lang/String;", "tempDir", "exportService", "Ljava/util/concurrent/ThreadPoolExecutor;", "recognitionService", "onBind", "Landroid/os/IBinder;", "intent", "Landroid/content/Intent;", "onUnbind", "", "onRebind", "", "init", "mBinder", "com/miui/handwriting/common/taskservice/CreationTaskService$mBinder$1", "Lcom/miui/handwriting/common/taskservice/CreationTaskService$mBinder$1;", "doExportFile", "dirPath", "folderPath", "folderName", "isDir", "exportType", "", "dataList", "", "Lcom/miui/libhandwritingpage/newbean/beanPageInfo/PageData;", "exportPageIndexList", "fromAI", CallMethod.ARG_CALLBACK, "Lcom/miui/creation/taskservice/ITaskServiceCallback;", "exportThumbnail", "thumbnailName", "recognitionEntFilePath", "creationID", "", "isAutoSave", "doSaveRecognition", "doExportPreviewImage", "preViewFolder", "entFilePath", "outFileName", "entDirPath", "creationTitle", Layer.KEY_WIDTH, "doExportImgToPc", "dirPathToPc", "folderPathToPc", "doSetSearchCallback", "searchCallback", "Lcom/miui/creation/taskservice/ISearchCallback;", "doRemoveSearchCallback", "libHandWritingCommon_release"}, k = 1, mv = {2, 2, 0}, xi = 48)
public final class CreationTaskService extends Service {
    private ThreadPoolExecutor exportService;
    private ThreadPoolExecutor recognitionService;
    private final String TAG = "CreationTaskService";
    private String tempDir = "";
    private final CreationTaskService$mBinder$1 mBinder = new ICreationTaskInterface.Stub() { // from class: com.miui.handwriting.common.taskservice.CreationTaskService$mBinder$1
        @Override // com.miui.creation.taskservice.ICreationTaskInterface
        public void exportToPc(Bundle bundle, ITaskServiceCallback callback) {
            Intrinsics.checkNotNullParameter(callback, "callback");
        }

        @Override // com.miui.creation.taskservice.ICreationTaskInterface
        public void autoSavaThumbnail(Bundle bundle, ITaskServiceCallback callback) {
            Intrinsics.checkNotNullParameter(bundle, "bundle");
            Intrinsics.checkNotNullParameter(callback, "callback");
            String string = bundle.getString("dirPath");
            String string2 = bundle.getString("filePath");
            long j = bundle.getLong("creationId");
            String string3 = bundle.getString("thumbnailPath");
            ArrayList arrayList = new ArrayList();
            List<PageData> itemList = PageDataUtils.parseItemList(string2, this.this$0.tempDir);
            Intrinsics.checkNotNullExpressionValue(itemList, "parseItemList(...)");
            arrayList.addAll(itemList);
            if (arrayList.isEmpty()) {
                return;
            }
            CreationTaskService creationTaskService = this.this$0;
            if (string3 == null) {
                string3 = "";
            }
            Intrinsics.checkNotNull(string);
            creationTaskService.exportThumbnail(string3, null, string, arrayList, j, true, callback);
        }

        @Override // com.miui.creation.taskservice.ICreationTaskInterface
        public void saveThumbnailAndRecognition(Bundle bundle, ITaskServiceCallback callback) throws RemoteException {
            Intrinsics.checkNotNullParameter(bundle, "bundle");
            Intrinsics.checkNotNullParameter(callback, "callback");
            Log.d(this.this$0.getTAG(), "Service receive: MSG_SERVICE_RECOGNITION");
            String string = bundle.getString("dirPath");
            String string2 = bundle.getString("entFilePath");
            long j = bundle.getLong("creationEntityId");
            String string3 = bundle.getString("thumbnailPath");
            ArrayList arrayList = new ArrayList();
            List<PageData> itemList = PageDataUtils.parseItemList(string2, this.this$0.tempDir);
            Intrinsics.checkNotNullExpressionValue(itemList, "parseItemList(...)");
            arrayList.addAll(itemList);
            if (!arrayList.isEmpty()) {
                CreationTaskService creationTaskService = this.this$0;
                if (string3 == null) {
                    string3 = "";
                }
                Intrinsics.checkNotNull(string);
                creationTaskService.exportThumbnail(string3, string2, string, arrayList, j, false, callback);
                return;
            }
            Log.i(this.this$0.getTAG(), "export thumb list is empty");
            if (callback.asBinder().isBinderAlive()) {
                callback.backToHome();
            }
        }

        @Override // com.miui.creation.taskservice.ICreationTaskInterface
        public void exportPreviewImage(Bundle bundle, ITaskServiceCallback callback) {
            Intrinsics.checkNotNullParameter(bundle, "bundle");
            Intrinsics.checkNotNullParameter(callback, "callback");
            Log.d(this.this$0.getTAG(), "Service receive: MSG_SERVICE_EXPORT_PREVIEW_IMAGE");
            String string = bundle.getString("pre_view");
            String string2 = bundle.getString("outFileName");
            int i = bundle.getInt("exportType");
            String string3 = bundle.getString("entFilePath");
            String string4 = bundle.getString("dirPath");
            int i2 = bundle.getInt(Layer.KEY_WIDTH);
            String string5 = bundle.getString("creationTitle");
            ArrayList arrayList = new ArrayList();
            List<PageData> itemList = PageDataUtils.parseItemList(string3, this.this$0.tempDir);
            Intrinsics.checkNotNullExpressionValue(itemList, "parseItemList(...)");
            arrayList.addAll(itemList);
            Log.d(this.this$0.getTAG(), "export thumb list is : " + arrayList.size() + " export name: " + string2 + " path is " + string3);
            CreationTaskService creationTaskService = this.this$0;
            Intrinsics.checkNotNull(string3);
            Intrinsics.checkNotNull(string2);
            Intrinsics.checkNotNull(string4);
            Intrinsics.checkNotNull(string5);
            creationTaskService.doExportPreviewImage(string, string3, arrayList, string2, i, string4, string5, i2, callback);
        }

        @Override // com.miui.creation.taskservice.ICreationTaskInterface
        public void exportToAi(Bundle bundle, ITaskServiceCallback callback) {
            Intrinsics.checkNotNullParameter(bundle, "bundle");
            Intrinsics.checkNotNullParameter(callback, "callback");
            String string = bundle.getString("dirPath");
            String string2 = bundle.getString("folderPath");
            String string3 = bundle.getString("folderName");
            boolean z = bundle.getBoolean("isDir");
            int i = bundle.getInt("exportType");
            String string4 = bundle.getString("entFilePath");
            ArrayList<Integer> integerArrayList = bundle.getIntegerArrayList("exportPageIndexList");
            ArrayList arrayList = new ArrayList();
            List<PageData> itemList = PageDataUtils.parseItemList(string4, this.this$0.tempDir);
            Intrinsics.checkNotNull(integerArrayList);
            PageData pageData = itemList.get(integerArrayList.get(0).intValue());
            Intrinsics.checkNotNullExpressionValue(pageData, "get(...)");
            arrayList.add(pageData);
            CreationTaskService creationTaskService = this.this$0;
            Intrinsics.checkNotNull(string);
            Intrinsics.checkNotNull(string2);
            Intrinsics.checkNotNull(string3);
            creationTaskService.doExportFile(string, string2, string3, z, i, arrayList, integerArrayList, true, callback);
        }

        @Override // com.miui.creation.taskservice.ICreationTaskInterface
        public void exportFile(Bundle bundle, ITaskServiceCallback callback) {
            Intrinsics.checkNotNullParameter(bundle, "bundle");
            Intrinsics.checkNotNullParameter(callback, "callback");
            Log.d(this.this$0.getTAG(), "Service receive: MSG_SERVICE_EXPORT_FILE");
            String string = bundle.getString("dirPath");
            String string2 = bundle.getString("folderPath");
            String string3 = bundle.getString("folderName");
            String string4 = bundle.getString("entFilePath");
            boolean z = bundle.getBoolean("isDir");
            int i = bundle.getInt("exportType");
            ArrayList<Integer> integerArrayList = bundle.getIntegerArrayList("exportPageIndexList");
            ArrayList arrayList = new ArrayList();
            List<PageData> itemList = PageDataUtils.parseItemList(string4, this.this$0.tempDir);
            Intrinsics.checkNotNullExpressionValue(itemList, "parseItemList(...)");
            arrayList.addAll(itemList);
            CreationTaskService creationTaskService = this.this$0;
            Intrinsics.checkNotNull(string);
            Intrinsics.checkNotNull(string2);
            Intrinsics.checkNotNull(string3);
            Intrinsics.checkNotNull(integerArrayList);
            creationTaskService.doExportFile(string, string2, string3, z, i, arrayList, integerArrayList, false, callback);
        }

        @Override // com.miui.creation.taskservice.ICreationTaskInterface
        public void selectRecognition(Bundle bundle, ITaskServiceCallback callback) {
            Intrinsics.checkNotNullParameter(bundle, "bundle");
            Intrinsics.checkNotNullParameter(callback, "callback");
            String string = bundle.getString("filePath");
            int i = bundle.getInt("textColor");
            boolean z = bundle.getBoolean("isRecognizeAsText");
            String string2 = bundle.getString("dirPath");
            TaskHelper taskHelper = TaskHelper.INSTANCE;
            ThreadPoolExecutor threadPoolExecutor = this.this$0.recognitionService;
            Intrinsics.checkNotNull(string);
            Intrinsics.checkNotNull(string2);
            taskHelper.doSelectRecognition(threadPoolExecutor, string, i, z, string2, callback);
        }

        @Override // com.miui.creation.taskservice.ICreationTaskInterface
        public void saveRecognition(Bundle bundle, ITaskServiceCallback callback) {
            Intrinsics.checkNotNullParameter(bundle, "bundle");
            Log.d(this.this$0.getTAG(), "Service receive: MSG_SERVICE_ONLY_RECOGNITION");
            String string = bundle.getString("dirPath");
            String string2 = bundle.getString("entFilePath");
            long j = bundle.getLong("creationEntityId");
            ArrayList arrayList = new ArrayList();
            List<PageData> itemList = PageDataUtils.parseItemList(string2, this.this$0.tempDir);
            Intrinsics.checkNotNullExpressionValue(itemList, "parseItemList(...)");
            arrayList.addAll(itemList);
            if (arrayList.isEmpty()) {
                return;
            }
            CreationTaskService creationTaskService = this.this$0;
            Intrinsics.checkNotNull(string2);
            Intrinsics.checkNotNull(string);
            creationTaskService.doSaveRecognition(string2, string, arrayList, j, callback);
        }

        @Override // com.miui.creation.taskservice.ICreationTaskInterface
        public void selectClassificationForAi(Bundle bundle, ITaskServiceCallback callback) {
            Intrinsics.checkNotNullParameter(bundle, "bundle");
            Intrinsics.checkNotNullParameter(callback, "callback");
            String string = bundle.getString("filePath");
            TaskHelper taskHelper = TaskHelper.INSTANCE;
            ThreadPoolExecutor threadPoolExecutor = this.this$0.recognitionService;
            Intrinsics.checkNotNull(string);
            taskHelper.doSelectClassificationAndRecognition(threadPoolExecutor, string, callback);
        }

        @Override // com.miui.creation.taskservice.ICreationTaskInterface
        public void setSearchCallback(ISearchCallback searchCallback) {
            this.this$0.doSetSearchCallback(searchCallback);
        }

        @Override // com.miui.creation.taskservice.ICreationTaskInterface
        public void removeSearchCallback() {
            this.this$0.doRemoveSearchCallback();
        }
    };

    private final void doExportImgToPc(String dirPathToPc, String folderPathToPc, List<? extends PageData> dataList, ITaskServiceCallback callback) {
    }

    public final String getTAG() {
        return this.TAG;
    }

    @Override // android.app.Service
    public IBinder onBind(Intent intent) {
        Intrinsics.checkNotNullParameter(intent, "intent");
        init();
        return this.mBinder;
    }

    @Override // android.app.Service
    public boolean onUnbind(Intent intent) {
        return super.onUnbind(intent);
    }

    @Override // android.app.Service
    public void onRebind(Intent intent) {
        super.onRebind(intent);
    }

    public final void init() {
        String dir = FileUtil.getDir(this, FileUtil.PAGE_EXPORT);
        Intrinsics.checkNotNullExpressionValue(dir, "getDir(...)");
        this.tempDir = dir;
        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(2, 4, 5000L, TimeUnit.MILLISECONDS, new LinkedBlockingQueue(), new ThreadFactory() { // from class: com.miui.handwriting.common.taskservice.CreationTaskService$$ExternalSyntheticLambda0
            @Override // java.util.concurrent.ThreadFactory
            public final Thread newThread(Runnable runnable) {
                return CreationTaskService.init$lambda$0(this.f$0, runnable);
            }
        }, new ThreadPoolExecutor.DiscardOldestPolicy());
        this.recognitionService = threadPoolExecutor;
        threadPoolExecutor.allowCoreThreadTimeOut(true);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Thread init$lambda$0(CreationTaskService creationTaskService, Runnable runnable) {
        return new RecognitionThread(runnable, creationTaskService.getApplication());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void doExportFile(String dirPath, String folderPath, String folderName, boolean isDir, int exportType, List<? extends PageData> dataList, List<Integer> exportPageIndexList, boolean fromAI, ITaskServiceCallback callback) {
        TaskHelper.INSTANCE.doExportFile(this, this.exportService, dirPath, folderPath, folderName, isDir, exportType, dataList, exportPageIndexList, fromAI, callback);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void exportThumbnail(String thumbnailName, String recognitionEntFilePath, String dirPath, List<? extends PageData> dataList, long creationID, boolean isAutoSave, ITaskServiceCallback callback) {
        TaskHelper.INSTANCE.exportThumbnail(this, this.exportService, this.recognitionService, thumbnailName, recognitionEntFilePath, dirPath, dataList, creationID, isAutoSave, callback);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void doSaveRecognition(String recognitionEntFilePath, String dirPath, List<? extends PageData> dataList, long creationID, ITaskServiceCallback callback) {
        TaskHelper.INSTANCE.saveRecognition(this.recognitionService, recognitionEntFilePath, dirPath, dataList, creationID, callback);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void doExportPreviewImage(String preViewFolder, String entFilePath, List<? extends PageData> dataList, String outFileName, int exportType, String entDirPath, String creationTitle, int width, ITaskServiceCallback callback) {
        TaskHelper.INSTANCE.doExportPreviewImage(this, this.exportService, preViewFolder, entFilePath, dataList, outFileName, exportType, entDirPath, creationTitle, width, callback);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void doSetSearchCallback(ISearchCallback searchCallback) {
        TaskHelper.INSTANCE.setSearchCallback(searchCallback);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void doRemoveSearchCallback() {
        TaskHelper.INSTANCE.setSearchCallback(null);
    }
}
