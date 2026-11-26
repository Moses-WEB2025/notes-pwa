package com.miui.handwriting.common.taskservice;

import android.app.Application;
import android.content.ContentValues;
import android.graphics.RectF;
import android.os.Environment;
import android.os.RemoteException;
import android.text.TextUtils;
import android.util.Log;
import androidx.core.app.NotificationCompat;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.market.sdk.reflect.ReflectUtilsForMiui;
import com.miui.creation.taskservice.ISearchCallback;
import com.miui.creation.taskservice.ITaskServiceCallback;
import com.miui.doodle.document.Layer;
import com.miui.handwriting.common.data.bean.MiuiRecognizeData;
import com.miui.handwriting.common.data.bean.MiuiRecognizeWrapper;
import com.miui.handwriting.common.data.bean.MiuiSpannedData;
import com.miui.handwriting.common.data.bean.MiuiSpannedDataWrapper;
import com.miui.handwriting.common.data.bean.PartiBean;
import com.miui.handwriting.common.filemanager.strategy.MiuiImportStrategy;
import com.miui.handwriting.common.tools.FilePathConstant;
import com.miui.handwriting.common.tools.RecognizeDataHelper;
import com.miui.handwriting.common.tools.constants.Creation;
import com.miui.libhandwritingpage.newbean.beanCurve.CurveBean;
import com.miui.libhandwritingpage.newbean.beanPageInfo.PageData;
import com.miui.libhandwritingpage.newbean.beanTouch.TData;
import com.miui.libhandwritingpage.task.ServiceExportTask;
import com.miui.libhandwritingpage.utils.PageDataUtils;
import com.xiaomi.mirror.synergy.CallMethod;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.BiConsumer;
import kotlin.Metadata;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.IntrinsicsKt;
import kotlin.coroutines.jvm.internal.Boxing;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;
import kotlinx.coroutines.BuildersKt__Builders_commonKt;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.CoroutineScopeKt;
import xc.engine.api.listener.PageColorListener;
import xc.engine.core.algorithm.bean.StrokeClassificationPoint;
import xc.engine.core.algorithm.recognize.RecognitionThread;
import xc.engine.core.algorithm.recognize.RecognizePoint;
import xc.engine.data.CsBufferBean;
import xc.engine.data.DataInterfaceSet;
import xc.engine.data.IDataSwapListener;
import xc.engine.utils.DisplayUtils;
import xc.engine.utils.FileUtil;
import xc.richtext.data.SpannedData;
import xc.richtext.data.SpannedMap;

/* JADX INFO: compiled from: TaskHelper.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u0000\u0098\u0001\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0014\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\t\n\u0000\n\u0002\u0010\n\n\u0002\b\u0005\n\u0002\u0010\u0011\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\f\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010%\n\u0002\b\u000b\bÆ\u0002\u0018\u00002\u00020\u0001B\t\b\u0002¢\u0006\u0004\b\u0002\u0010\u0003J8\u0010\f\u001a\u00020\r2\b\u0010\u000e\u001a\u0004\u0018\u00010\u000f2\u0006\u0010\u0010\u001a\u00020\u00052\u0006\u0010\u0011\u001a\u00020\u00122\u0006\u0010\u0013\u001a\u00020\u00142\u0006\u0010\u0015\u001a\u00020\u00052\u0006\u0010\u0016\u001a\u00020\u0017J \u0010\u0018\u001a\u00020\r2\b\u0010\u000e\u001a\u0004\u0018\u00010\u000f2\u0006\u0010\u0010\u001a\u00020\u00052\u0006\u0010\u0016\u001a\u00020\u0017J&\u0010\u0019\u001a\u00020\u001a2\f\u0010\u001b\u001a\b\u0012\u0004\u0012\u00020\u001d0\u001c2\u0006\u0010\u001e\u001a\u00020\u001d2\u0006\u0010\u001f\u001a\u00020\u0012H\u0002J@\u0010 \u001a\u00020\r2\b\u0010\u000e\u001a\u0004\u0018\u00010\u000f2\u0006\u0010!\u001a\u00020\u00052\u0006\u0010\u0015\u001a\u00020\u00052\f\u0010\"\u001a\b\u0012\u0004\u0012\u00020#0\u001c2\u0006\u0010$\u001a\u00020%2\b\u0010\u0016\u001a\u0004\u0018\u00010\u0017J\u000e\u0010&\u001a\u00020'2\u0006\u0010(\u001a\u00020\u0012J\u0010\u0010)\u001a\u00020\u00122\u0006\u0010*\u001a\u00020'H\u0002Jm\u0010+\u001a\u00020\r2\u0006\u0010\u0015\u001a\u00020\u00052\u0006\u0010$\u001a\u00020%2\u000e\u0010,\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\u00050-2\u0014\u0010.\u001a\u0010\u0012\f\u0012\n\u0018\u00010/j\u0004\u0018\u0001`00-2\u000e\u00101\u001a\n\u0012\u0006\u0012\u0004\u0018\u0001020-2\u000e\u00103\u001a\n\u0012\u0006\u0012\u0004\u0018\u0001040-2\b\u0010\u0016\u001a\u0004\u0018\u00010\u0017H\u0002¢\u0006\u0002\u00105Jl\u00106\u001a\u00020\r2\u0006\u00107\u001a\u0002082\b\u00109\u001a\u0004\u0018\u00010\u000f2\u0006\u0010\u0015\u001a\u00020\u00052\u0006\u0010:\u001a\u00020\u00052\u0006\u0010;\u001a\u00020\u00052\u0006\u0010<\u001a\u00020\u00142\u0006\u0010=\u001a\u00020\u00122\f\u0010\"\u001a\b\u0012\u0004\u0012\u00020#0\u001c2\f\u0010>\u001a\b\u0012\u0004\u0012\u00020\u00120\u001c2\u0006\u0010?\u001a\u00020\u00142\u0006\u0010\u0016\u001a\u00020\u0017Jd\u0010@\u001a\u00020\r2\u0006\u00107\u001a\u0002082\b\u00109\u001a\u0004\u0018\u00010\u000f2\b\u0010\u000e\u001a\u0004\u0018\u00010\u000f2\u0006\u0010A\u001a\u00020\u00052\b\u0010B\u001a\u0004\u0018\u00010\u00052\u0006\u0010\u0015\u001a\u00020\u00052\f\u0010\"\u001a\b\u0012\u0004\u0012\u00020#0\u001c2\u0006\u0010$\u001a\u00020%2\u0006\u0010C\u001a\u00020\u00142\b\u0010\u0016\u001a\u0004\u0018\u00010\u0017J\"\u0010D\u001a\u00020E2\u0012\u0010F\u001a\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u00010G2\u0006\u0010H\u001a\u00020\u0014Jj\u0010I\u001a\u00020\r2\u0006\u00107\u001a\u0002082\b\u00109\u001a\u0004\u0018\u00010\u000f2\b\u0010J\u001a\u0004\u0018\u00010\u00052\b\u0010!\u001a\u0004\u0018\u00010\u00052\f\u0010\"\u001a\b\u0012\u0004\u0012\u00020#0\u001c2\u0006\u0010K\u001a\u00020\u00052\u0006\u0010=\u001a\u00020\u00122\u0006\u0010L\u001a\u00020\u00052\u0006\u0010M\u001a\u00020\u00052\u0006\u0010N\u001a\u00020\u00122\u0006\u0010\u0016\u001a\u00020\u0017J>\u0010O\u001a\u00020\r2\u0006\u00107\u001a\u0002082\b\u00109\u001a\u0004\u0018\u00010\u000f2\u0006\u0010P\u001a\u00020\u00052\u0006\u0010Q\u001a\u00020\u00052\f\u0010\"\u001a\b\u0012\u0004\u0012\u00020#0\u001c2\u0006\u0010\u0016\u001a\u00020\u0017R\u000e\u0010\u0004\u001a\u00020\u0005X\u0086T¢\u0006\u0002\n\u0000R\u001c\u0010\u0006\u001a\u0004\u0018\u00010\u0007X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\b\u0010\t\"\u0004\b\n\u0010\u000b¨\u0006R"}, d2 = {"Lcom/miui/handwriting/common/taskservice/TaskHelper;", "", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "()V", "TAG", "", "searchCallback", "Lcom/miui/creation/taskservice/ISearchCallback;", "getSearchCallback", "()Lcom/miui/creation/taskservice/ISearchCallback;", "setSearchCallback", "(Lcom/miui/creation/taskservice/ISearchCallback;)V", "doSelectRecognition", "", "recognitionThread", "Ljava/util/concurrent/ThreadPoolExecutor;", "filePath", "textColor", "", "isRecognizeAsText", "", "dirPath", CallMethod.ARG_CALLBACK, "Lcom/miui/creation/taskservice/ITaskServiceCallback;", "doSelectClassificationAndRecognition", "coordinatesInterpolation", "", "pointsList", "", "Lxc/engine/core/algorithm/bean/StrokeClassificationPoint;", "point", "pointIndex", "saveRecognition", "entFilePath", "dataList", "Lcom/miui/libhandwritingpage/newbean/beanPageInfo/PageData;", "creationID", "", "getDataType", "", "type", "changedToSpannedType", "dataType", "lastPageRecognitionDataProcessing", "textContentList", "", "spanTextContentList", "Ljava/lang/StringBuilder;", "Lkotlin/text/StringBuilder;", "miuiRecognizeData", "Lcom/miui/handwriting/common/data/bean/MiuiRecognizeWrapper;", "miuiSpannedDataWrappers", "Lcom/miui/handwriting/common/data/bean/MiuiSpannedDataWrapper;", "(Ljava/lang/String;J[Ljava/lang/String;[Ljava/lang/StringBuilder;[Lcom/miui/handwriting/common/data/bean/MiuiRecognizeWrapper;[Lcom/miui/handwriting/common/data/bean/MiuiSpannedDataWrapper;Lcom/miui/creation/taskservice/ITaskServiceCallback;)V", "doExportFile", NotificationCompat.CATEGORY_SERVICE, "Lcom/miui/handwriting/common/taskservice/CreationTaskService;", "exportThread", "folderPath", "folderName", "isDir", "exportType", "exportPageIndexList", "fromAI", "exportThumbnail", "thumbnailName", "recognitionEntFilePath", "isAutoSave", "getContentValues", "Landroid/content/ContentValues;", "values", "", "isNeedUpdateModifiedTime", "doExportPreviewImage", "preViewFolder", "outFileName", "entDirPath", "creationTitle", Layer.KEY_WIDTH, "doExportImgToPc", "dirPathToPc", "folderPathToPc", "libHandWritingCommon_release"}, k = 1, mv = {2, 2, 0}, xi = 48)
public final class TaskHelper {
    public static final TaskHelper INSTANCE = new TaskHelper();
    public static final String TAG = "TaskHelper";
    private static ISearchCallback searchCallback;

    /* JADX INFO: Access modifiers changed from: private */
    public final int changedToSpannedType(short dataType) {
        if (dataType == 11) {
            return 11;
        }
        return dataType == 7 ? 7 : 0;
    }

    public final short getDataType(int type) {
        if (type != 7) {
            return type != 11 ? (short) 1 : (short) 11;
        }
        return (short) 7;
    }

    private TaskHelper() {
    }

    public final ISearchCallback getSearchCallback() {
        return searchCallback;
    }

    public final void setSearchCallback(ISearchCallback iSearchCallback) {
        searchCallback = iSearchCallback;
    }

    public final void doSelectRecognition(ThreadPoolExecutor recognitionThread, final String filePath, final int textColor, final boolean isRecognizeAsText, final String dirPath, final ITaskServiceCallback callback) {
        Intrinsics.checkNotNullParameter(filePath, "filePath");
        Intrinsics.checkNotNullParameter(dirPath, "dirPath");
        Intrinsics.checkNotNullParameter(callback, "callback");
        if (recognitionThread != null) {
            recognitionThread.execute(new Runnable() { // from class: com.miui.handwriting.common.taskservice.TaskHelper$$ExternalSyntheticLambda1
                @Override // java.lang.Runnable
                public final void run() throws RemoteException {
                    TaskHelper.doSelectRecognition$lambda$5(filePath, dirPath, callback, isRecognizeAsText, textColor);
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void doSelectRecognition$lambda$5(String str, String str2, ITaskServiceCallback iTaskServiceCallback, boolean z, int i) throws RemoteException {
        String jsonFromFile = FileUtil.getJsonFromFile(str);
        Log.d(TAG, "recognitionData: " + jsonFromFile);
        List list = (List) new Gson().fromJson(jsonFromFile, new TypeToken<List<? extends CurveBean>>() { // from class: com.miui.handwriting.common.taskservice.TaskHelper$doSelectRecognition$1$curveData$1
        }.getType());
        if (list != null) {
            ArrayList<RecognizePoint> arrayList = new ArrayList<>();
            Iterator it = list.iterator();
            while (it.hasNext()) {
                List<TData> list2 = ((CurveBean) it.next()).pointDataList;
                if (list2 != null) {
                    for (TData tData : list2) {
                        arrayList.add(new RecognizePoint(tData.x, tData.y, tData.time, tData.action));
                    }
                }
            }
            long jCurrentTimeMillis = System.currentTimeMillis();
            Log.e(TAG, "selectRecognize startTime " + jCurrentTimeMillis);
            Thread threadCurrentThread = Thread.currentThread();
            Intrinsics.checkNotNull(threadCurrentThread, "null cannot be cast to non-null type xc.engine.core.algorithm.recognize.RecognitionThread");
            String strSyncRecognize = ((RecognitionThread) threadCurrentThread).syncRecognize(arrayList);
            String selectRecognizePath = RecognizeDataHelper.getSelectRecognizePath(str2);
            Log.e(TAG, "recognizeResult: " + strSyncRecognize);
            PartiBean partiBean = (PartiBean) new Gson().fromJson(strSyncRecognize, PartiBean.class);
            Log.e(TAG, "selectRecognize execute " + (System.currentTimeMillis() - jCurrentTimeMillis) + "ms");
            RecognizeDataHelper.writeSelectRecognizeToFile(partiBean, selectRecognizePath);
            if (iTaskServiceCallback.asBinder().isBinderAlive()) {
                if (z) {
                    iTaskServiceCallback.recognizeAsText(selectRecognizePath, i);
                    return;
                } else {
                    iTaskServiceCallback.copyAsText(selectRecognizePath);
                    return;
                }
            }
            return;
        }
        Log.i(TAG, "recognize SelectData Fail curveData is null");
    }

    public final void doSelectClassificationAndRecognition(ThreadPoolExecutor recognitionThread, String filePath, final ITaskServiceCallback callback) {
        Intrinsics.checkNotNullParameter(filePath, "filePath");
        Intrinsics.checkNotNullParameter(callback, "callback");
        if (recognitionThread != null) {
            recognitionThread.execute(new Runnable() { // from class: com.miui.handwriting.common.taskservice.TaskHelper$$ExternalSyntheticLambda3
                @Override // java.lang.Runnable
                public final void run() throws RemoteException {
                    TaskHelper.doSelectClassificationAndRecognition$lambda$6(callback);
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void doSelectClassificationAndRecognition$lambda$6(ITaskServiceCallback iTaskServiceCallback) throws RemoteException {
        Log.e(TAG, "doSelectClassificationAndRecognition: SmartClassification module removed");
        if (iTaskServiceCallback.asBinder().isBinderAlive()) {
            iTaskServiceCallback.classificationForAi(false, "");
        }
    }

    private final float[] coordinatesInterpolation(List<? extends StrokeClassificationPoint> pointsList, StrokeClassificationPoint point, int pointIndex) {
        float[] fArr = {0.0f, 0.0f};
        if (point.x != 0.0f) {
            fArr[0] = point.x;
        } else {
            int i = pointIndex - 1;
            while (i >= 0 && pointsList.get(i).x == 0.0f) {
                i--;
            }
            float f = (i < 0 || pointsList.get(i).x == 0.0f) ? 0.0f : pointsList.get(i).x;
            int i2 = pointIndex + 1;
            while (i2 < pointsList.size() && pointsList.get(i2).x == 0.0f) {
                i2++;
            }
            float f2 = (i2 >= pointsList.size() || pointsList.get(i2).x == 0.0f) ? 0.0f : pointsList.get(i2).x;
            if (f != 0.0f && f2 != 0.0f) {
                fArr[0] = (f + f2) / 2;
            } else if (f != 0.0f) {
                fArr[0] = f;
            } else if (f2 != 0.0f) {
                fArr[0] = f2;
            }
        }
        if (point.y != 0.0f) {
            fArr[1] = point.y;
            return fArr;
        }
        int i3 = pointIndex - 1;
        while (i3 >= 0 && pointsList.get(i3).y == 0.0f) {
            i3--;
        }
        float f3 = (i3 < 0 || pointsList.get(i3).y == 0.0f) ? 0.0f : pointsList.get(i3).y;
        int i4 = pointIndex + 1;
        while (i4 < pointsList.size() && pointsList.get(i4).y == 0.0f) {
            i4++;
        }
        float f4 = (i4 >= pointsList.size() || pointsList.get(i4).y == 0.0f) ? 0.0f : pointsList.get(i4).y;
        if (f3 != 0.0f && f4 != 0.0f) {
            fArr[1] = (f3 + f4) / 2;
            return fArr;
        }
        if (f3 != 0.0f) {
            fArr[1] = f3;
            return fArr;
        }
        if (f4 == 0.0f) {
            return fArr;
        }
        fArr[1] = f4;
        return fArr;
    }

    public final void saveRecognition(ThreadPoolExecutor recognitionThread, String entFilePath, String dirPath, List<? extends PageData> dataList, final long creationID, final ITaskServiceCallback callback) {
        final List<? extends PageData> dataList2 = dataList;
        final String entFilePath2 = entFilePath;
        Intrinsics.checkNotNullParameter(entFilePath2, "entFilePath");
        final String dirPath2 = dirPath;
        Intrinsics.checkNotNullParameter(dirPath2, "dirPath");
        Intrinsics.checkNotNullParameter(dataList2, "dataList");
        if (recognitionThread != null) {
            int size = dataList2.size();
            final int i = 0;
            final int i2 = 0;
            for (int i3 = 0; i3 < size; i3++) {
                if (!TextUtils.isEmpty(dataList2.get(i3).inkPath)) {
                    i2++;
                }
            }
            final long jCurrentTimeMillis = System.currentTimeMillis();
            Log.e(TAG, "recognizeText startTime:" + jCurrentTimeMillis);
            final AtomicInteger atomicInteger = new AtomicInteger(0);
            AtomicInteger atomicInteger2 = new AtomicInteger(0);
            final AtomicBoolean atomicBoolean = new AtomicBoolean(false);
            if (i2 > 0) {
                final String[] strArr = new String[i2];
                final StringBuilder[] sbArr = new StringBuilder[i2];
                final MiuiRecognizeWrapper[] miuiRecognizeWrapperArr = new MiuiRecognizeWrapper[i2];
                final MiuiSpannedDataWrapper[] miuiSpannedDataWrapperArr = new MiuiSpannedDataWrapper[i2];
                while (i < i2) {
                    miuiRecognizeWrapperArr[i] = new MiuiRecognizeWrapper();
                    miuiSpannedDataWrapperArr[i] = new MiuiSpannedDataWrapper();
                    final AtomicInteger atomicInteger3 = atomicInteger2;
                    recognitionThread.execute(new Runnable() { // from class: com.miui.handwriting.common.taskservice.TaskHelper$$ExternalSyntheticLambda0
                        @Override // java.lang.Runnable
                        public final void run() throws RemoteException {
                            TaskHelper.saveRecognition$lambda$10$lambda$9(dataList2, i, strArr, miuiRecognizeWrapperArr, creationID, entFilePath2, atomicInteger, i2, atomicBoolean, dirPath2, sbArr, miuiSpannedDataWrapperArr, callback, jCurrentTimeMillis, atomicInteger3);
                        }
                    });
                    i++;
                    entFilePath2 = entFilePath;
                    dirPath2 = dirPath;
                    dataList2 = dataList;
                    atomicInteger2 = atomicInteger3;
                }
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void saveRecognition$lambda$10$lambda$9(final List list, final int i, final String[] strArr, final MiuiRecognizeWrapper[] miuiRecognizeWrapperArr, final long j, String str, AtomicInteger atomicInteger, final int i2, final AtomicBoolean atomicBoolean, final String str2, final StringBuilder[] sbArr, final MiuiSpannedDataWrapper[] miuiSpannedDataWrapperArr, final ITaskServiceCallback iTaskServiceCallback, long j2, final AtomicInteger atomicInteger2) throws RemoteException {
        DataInterfaceSet dataInterfaceSet = new DataInterfaceSet();
        dataInterfaceSet.setDataSwapListener(new IDataSwapListener() { // from class: com.miui.handwriting.common.taskservice.TaskHelper$saveRecognition$1$1$1
            @Override // xc.engine.data.IDataSwapListener
            public CsBufferBean onSaveListener(int pageIndex) {
                return null;
            }

            @Override // xc.engine.data.IDataSwapListener
            public void onLoadListener(int version, int[] types, int pageIndex, List<byte[]> bytes) throws RemoteException {
                Intrinsics.checkNotNullParameter(types, "types");
                Intrinsics.checkNotNullParameter(bytes, "bytes");
                sbArr[i] = new StringBuilder();
                if (!(types.length == 0)) {
                    int length = types.length;
                    short[] sArr = new short[length];
                    for (int i3 = 0; i3 < length; i3++) {
                        sArr[i3] = TaskHelper.INSTANCE.getDataType(types[i3]);
                    }
                    int[] iArr = new int[length];
                    for (int i4 = 0; i4 < length; i4++) {
                        iArr[i4] = TaskHelper.INSTANCE.changedToSpannedType(sArr[i4]);
                    }
                    SpannedMap spannedMapCreateInstance = SpannedMap.createInstance();
                    spannedMapCreateInstance.loadTextFromBuffer(new File(list.get(i).inkPath).getParent(), pageIndex, iArr, bytes);
                    List<SpannedData> dataList = spannedMapCreateInstance.getDataList(11);
                    if (dataList != null) {
                        int size = dataList.size();
                        for (int i5 = 0; i5 < size; i5++) {
                            SpannedData spannedData = dataList.get(i5);
                            int id = spannedData.getId();
                            String text = spannedData.getText();
                            RectF rectF = spannedData.getRectF();
                            float textSize = spannedData.getTextSize();
                            if (rectF != null && !Double.isNaN(rectF.left)) {
                                MiuiSpannedDataWrapper miuiSpannedDataWrapper = miuiSpannedDataWrapperArr[i];
                                Intrinsics.checkNotNull(miuiSpannedDataWrapper);
                                miuiSpannedDataWrapper.getList().add(new MiuiSpannedData(j, str2, i, i5, id, text, textSize, rectF.left, rectF.top, rectF.width(), rectF.height()));
                                StringBuilder sb = sbArr[i];
                                Intrinsics.checkNotNull(sb);
                                sb.append(text);
                            }
                        }
                    }
                }
                if (atomicInteger2.get() == i2 - 1 && !atomicBoolean.compareAndSet(false, true)) {
                    TaskHelper.INSTANCE.lastPageRecognitionDataProcessing(str2, j, strArr, sbArr, miuiRecognizeWrapperArr, miuiSpannedDataWrapperArr, iTaskServiceCallback);
                }
                atomicInteger2.incrementAndGet();
            }
        });
        List<CurveBean> pageDataList = PageDataUtils.getPageDataList(((PageData) list.get(i)).inkPath, dataInterfaceSet, i);
        ArrayList<RecognizePoint> arrayList = new ArrayList<>();
        if (pageDataList != null) {
            int size = pageDataList.size();
            for (int i3 = 0; i3 < size; i3++) {
                List<TData> list2 = pageDataList.get(i3).pointDataList;
                if (list2 != null) {
                    int size2 = list2.size();
                    for (int i4 = 0; i4 < size2; i4++) {
                        arrayList.add(new RecognizePoint(list2.get(i4).x, list2.get(i4).y, list2.get(i4).time, list2.get(i4).action));
                    }
                }
            }
        }
        Thread threadCurrentThread = Thread.currentThread();
        Intrinsics.checkNotNull(threadCurrentThread, "null cannot be cast to non-null type xc.engine.core.algorithm.recognize.RecognitionThread");
        PartiBean partiBean = (PartiBean) new Gson().fromJson(((RecognitionThread) threadCurrentThread).syncRecognize(arrayList), PartiBean.class);
        strArr[i] = RecognizeDataHelper.getLabel(partiBean);
        MiuiRecognizeWrapper miuiRecognizeWrapper = miuiRecognizeWrapperArr[i];
        Intrinsics.checkNotNull(miuiRecognizeWrapper);
        List<MiuiRecognizeData> list3 = miuiRecognizeWrapper.getList();
        List<MiuiRecognizeData> listPartiBeanToRecognizeList = RecognizeDataHelper.partiBeanToRecognizeList(j, partiBean, str, i);
        Intrinsics.checkNotNullExpressionValue(listPartiBeanToRecognizeList, "partiBeanToRecognizeList(...)");
        list3.addAll(listPartiBeanToRecognizeList);
        if (atomicInteger.get() == i2 - 1) {
            if (!atomicBoolean.compareAndSet(false, true)) {
                INSTANCE.lastPageRecognitionDataProcessing(str2, j, strArr, sbArr, miuiRecognizeWrapperArr, miuiSpannedDataWrapperArr, iTaskServiceCallback);
            }
            Log.e(TAG, "recognizeText execute " + (System.currentTimeMillis() - j2) + "ms");
        }
        atomicInteger.incrementAndGet();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void lastPageRecognitionDataProcessing(String dirPath, long creationID, String[] textContentList, StringBuilder[] spanTextContentList, MiuiRecognizeWrapper[] miuiRecognizeData, MiuiSpannedDataWrapper[] miuiSpannedDataWrappers, ITaskServiceCallback callback) throws RemoteException {
        String recognizePath = RecognizeDataHelper.getRecognizePath(dirPath);
        MiuiRecognizeWrapper miuiRecognizeWrapper = new MiuiRecognizeWrapper();
        for (MiuiRecognizeWrapper miuiRecognizeWrapper2 : miuiRecognizeData) {
            List<MiuiRecognizeData> list = miuiRecognizeWrapper.getList();
            Intrinsics.checkNotNull(miuiRecognizeWrapper2);
            List<MiuiRecognizeData> list2 = miuiRecognizeWrapper2.getList();
            Intrinsics.checkNotNullExpressionValue(list2, "getList(...)");
            list.addAll(list2);
        }
        RecognizeDataHelper.writeRecognizeToFile(miuiRecognizeWrapper, recognizePath);
        String spannedDataPath = RecognizeDataHelper.getSpannedDataPath(dirPath);
        MiuiSpannedDataWrapper miuiSpannedDataWrapper = new MiuiSpannedDataWrapper();
        for (MiuiSpannedDataWrapper miuiSpannedDataWrapper2 : miuiSpannedDataWrappers) {
            List<MiuiSpannedData> list3 = miuiSpannedDataWrapper.getList();
            Intrinsics.checkNotNull(miuiSpannedDataWrapper2);
            List<MiuiSpannedData> list4 = miuiSpannedDataWrapper2.getList();
            Intrinsics.checkNotNullExpressionValue(list4, "getList(...)");
            list3.addAll(list4);
        }
        RecognizeDataHelper.writeRecognizeToFile(miuiSpannedDataWrapper, spannedDataPath);
        ArrayList arrayList = new ArrayList();
        Intrinsics.checkNotNull(recognizePath);
        arrayList.add(recognizePath);
        Intrinsics.checkNotNull(spannedDataPath);
        arrayList.add(spannedDataPath);
        HashMap map = new HashMap();
        Gson gson = new Gson();
        StringBuilder sb = new StringBuilder();
        for (String str : textContentList) {
            sb.append(str);
            sb.append(RecognizeDataHelper.SPLIT);
        }
        sb.append(RecognizeDataHelper.SPANNED_SPLIT);
        StringBuilder sb2 = new StringBuilder();
        for (StringBuilder sb3 : spanTextContentList) {
            sb2.append((CharSequence) sb3);
            sb2.append(RecognizeDataHelper.SPLIT);
        }
        sb.append((CharSequence) sb2);
        map.put(Creation.TEXT_CONTENT, sb.toString());
        map.put("recognized_file_names", gson.toJson(arrayList));
        ContentValues contentValues = getContentValues(map, true);
        if (contentValues.isEmpty()) {
            return;
        }
        ISearchCallback iSearchCallback = searchCallback;
        if (iSearchCallback != null) {
            iSearchCallback.doSearch();
        }
        if (callback != null) {
            try {
                callback.onGenComplete(creationID, contentValues);
            } catch (Exception e) {
                Log.e(TAG, "onGenComplete error: " + e.getMessage());
            }
        }
    }

    public final void doExportFile(CreationTaskService service, ThreadPoolExecutor exportThread, String dirPath, String folderPath, String folderName, boolean isDir, int exportType, List<? extends PageData> dataList, List<Integer> exportPageIndexList, boolean fromAI, ITaskServiceCallback callback) {
        StringBuilder sb;
        String uniqueFileName;
        Intrinsics.checkNotNullParameter(service, "service");
        Intrinsics.checkNotNullParameter(dirPath, "dirPath");
        Intrinsics.checkNotNullParameter(folderPath, "folderPath");
        Intrinsics.checkNotNullParameter(folderName, "folderName");
        Intrinsics.checkNotNullParameter(dataList, "dataList");
        Intrinsics.checkNotNullParameter(exportPageIndexList, "exportPageIndexList");
        Intrinsics.checkNotNullParameter(callback, "callback");
        Log.d(TAG, "export: dirPath" + dirPath);
        Log.d(TAG, "export: outFileName before:" + folderPath + '/' + folderName);
        if (isDir) {
            sb = new StringBuilder();
            uniqueFileName = sb.append(folderPath).append('/').append(FileUtil.getUniqueFolderName(folderPath, folderName)).toString();
        } else {
            sb = new StringBuilder();
            uniqueFileName = FileUtil.getUniqueFileName(sb.append(folderPath).append('/').append(folderName).toString());
        }
        Log.d(TAG, "export: outFileName end:" + uniqueFileName);
        BuildersKt__Builders_commonKt.launch$default(CoroutineScopeKt.MainScope(), null, null, new AnonymousClass1(service, dirPath, new File(uniqueFileName), exportType, exportThread, fromAI, dataList, exportPageIndexList, callback, null), 3, null);
    }

    /* JADX INFO: renamed from: com.miui.handwriting.common.taskservice.TaskHelper$doExportFile$1, reason: invalid class name */
    /* JADX INFO: compiled from: TaskHelper.kt */
    @Metadata(d1 = {"\u0000\n\n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\u0010\u0000\u001a\u00020\u0001*\u00020\u0002H\n"}, d2 = {"<anonymous>", "", "Lkotlinx/coroutines/CoroutineScope;"}, k = 3, mv = {2, 2, 0}, xi = 48)
    @DebugMetadata(c = "com.miui.handwriting.common.taskservice.TaskHelper$doExportFile$1", f = "TaskHelper.kt", i = {}, l = {}, m = "invokeSuspend", n = {}, s = {})
    static final class AnonymousClass1 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
        final /* synthetic */ ITaskServiceCallback $callback;
        final /* synthetic */ List<PageData> $dataList;
        final /* synthetic */ String $dirPath;
        final /* synthetic */ List<Integer> $exportPageIndexList;
        final /* synthetic */ ThreadPoolExecutor $exportThread;
        final /* synthetic */ int $exportType;
        final /* synthetic */ boolean $fromAI;
        final /* synthetic */ File $outFile;
        final /* synthetic */ CreationTaskService $service;
        int label;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        /* JADX WARN: Multi-variable type inference failed */
        AnonymousClass1(CreationTaskService creationTaskService, String str, File file, int i, ThreadPoolExecutor threadPoolExecutor, boolean z, List<? extends PageData> list, List<Integer> list2, ITaskServiceCallback iTaskServiceCallback, Continuation<? super AnonymousClass1> continuation) {
            super(2, continuation);
            this.$service = creationTaskService;
            this.$dirPath = str;
            this.$outFile = file;
            this.$exportType = i;
            this.$exportThread = threadPoolExecutor;
            this.$fromAI = z;
            this.$dataList = list;
            this.$exportPageIndexList = list2;
            this.$callback = iTaskServiceCallback;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
            return new AnonymousClass1(this.$service, this.$dirPath, this.$outFile, this.$exportType, this.$exportThread, this.$fromAI, this.$dataList, this.$exportPageIndexList, this.$callback, continuation);
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(CoroutineScope coroutineScope, Continuation<? super Unit> continuation) {
            return ((AnonymousClass1) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            IntrinsicsKt.getCOROUTINE_SUSPENDED();
            if (this.label != 0) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            ResultKt.throwOnFailure(obj);
            CreationTaskService creationTaskService = this.$service;
            String str = this.$dirPath;
            String absolutePath = this.$outFile.getAbsolutePath();
            int i = this.$exportType;
            ThreadPoolExecutor threadPoolExecutor = this.$exportThread;
            final ITaskServiceCallback iTaskServiceCallback = this.$callback;
            final boolean z = this.$fromAI;
            final int i2 = this.$exportType;
            ServiceExportTask serviceExportTask = new ServiceExportTask(creationTaskService, str, absolutePath, i, threadPoolExecutor, new ServiceExportTask.ExportListener() { // from class: com.miui.handwriting.common.taskservice.TaskHelper$doExportFile$1$task$1
                @Override // com.miui.libhandwritingpage.task.ServiceExportTask.ExportListener
                public void onCompleted(boolean success, String path) throws RemoteException {
                    Intrinsics.checkNotNullParameter(path, "path");
                    if (iTaskServiceCallback.asBinder().isBinderAlive()) {
                        if (z) {
                            iTaskServiceCallback.exportToAI(success, path, i2);
                        } else {
                            iTaskServiceCallback.exportCompleted(success, path, i2);
                        }
                    }
                }

                @Override // com.miui.libhandwritingpage.task.ServiceExportTask.ExportListener
                public void onProgress(int percentDone) throws RemoteException {
                    Log.d(TaskHelper.TAG, "onProgress percentDone: " + percentDone);
                    if (iTaskServiceCallback.asBinder().isBinderAlive()) {
                        iTaskServiceCallback.exportOnProgress(percentDone);
                    }
                }
            });
            serviceExportTask.setWidth(1440);
            Log.e(TaskHelper.TAG, "exportFile task start pre");
            ArrayList arrayList = new ArrayList();
            if (this.$fromAI) {
                arrayList.add(this.$dataList.get(0));
                if (this.$dataList.get(0).isFixSize) {
                    serviceExportTask.setWidth(this.$dataList.get(0).width);
                }
            } else {
                int size = this.$exportPageIndexList.size();
                for (int i3 = 0; i3 < size; i3++) {
                    arrayList.add(this.$dataList.get(this.$exportPageIndexList.get(i3).intValue()));
                }
            }
            List<Integer> list = this.$exportPageIndexList;
            final List<PageData> list2 = this.$dataList;
            serviceExportTask.start(arrayList, list, new PageColorListener() { // from class: com.miui.handwriting.common.taskservice.TaskHelper.doExportFile.1.1
                @Override // xc.engine.api.listener.PageColorListener
                public int transformColor(int pageIndex, int color) {
                    return color;
                }

                @Override // xc.engine.api.listener.PageColorListener
                public int transformSpannedColor(int pageIndex, int color) {
                    if (list2.size() > pageIndex) {
                        if (list2.get(pageIndex).bgColor == -16777216) {
                            if (color == -16777216) {
                                return -1;
                            }
                        } else if (list2.get(pageIndex).bgColor == -1 && color == -1) {
                            return -16777216;
                        }
                    }
                    return color;
                }
            });
            Log.e(TaskHelper.TAG, "exportFile task start");
            return Unit.INSTANCE;
        }
    }

    public final void exportThumbnail(CreationTaskService service, ThreadPoolExecutor exportThread, ThreadPoolExecutor recognitionThread, String thumbnailName, String recognitionEntFilePath, String dirPath, List<? extends PageData> dataList, long creationID, boolean isAutoSave, ITaskServiceCallback callback) {
        Intrinsics.checkNotNullParameter(service, "service");
        Intrinsics.checkNotNullParameter(thumbnailName, "thumbnailName");
        Intrinsics.checkNotNullParameter(dirPath, "dirPath");
        Intrinsics.checkNotNullParameter(dataList, "dataList");
        String entFilePathThumb = FilePathConstant.getEntFilePathThumb(dirPath);
        String strValueOf = String.valueOf(System.currentTimeMillis());
        if (FileUtil.createOrExistsDir(entFilePathThumb)) {
            BuildersKt__Builders_commonKt.launch$default(CoroutineScopeKt.MainScope(), null, null, new C02411(entFilePathThumb, thumbnailName, strValueOf, service, dirPath, exportThread, dataList, callback, creationID, isAutoSave, recognitionThread, recognitionEntFilePath, null), 3, null);
        }
    }

    /* JADX INFO: renamed from: com.miui.handwriting.common.taskservice.TaskHelper$exportThumbnail$1, reason: invalid class name and case insensitive filesystem */
    /* JADX INFO: compiled from: TaskHelper.kt */
    @Metadata(d1 = {"\u0000\n\n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\u0010\u0000\u001a\u00020\u0001*\u00020\u0002H\n"}, d2 = {"<anonymous>", "", "Lkotlinx/coroutines/CoroutineScope;"}, k = 3, mv = {2, 2, 0}, xi = 48)
    @DebugMetadata(c = "com.miui.handwriting.common.taskservice.TaskHelper$exportThumbnail$1", f = "TaskHelper.kt", i = {}, l = {}, m = "invokeSuspend", n = {}, s = {})
    static final class C02411 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
        final /* synthetic */ ITaskServiceCallback $callback;
        final /* synthetic */ long $creationID;
        final /* synthetic */ List<PageData> $dataList;
        final /* synthetic */ String $dirPath;
        final /* synthetic */ ThreadPoolExecutor $exportThread;
        final /* synthetic */ boolean $isAutoSave;
        final /* synthetic */ String $outFileName;
        final /* synthetic */ String $recognitionEntFilePath;
        final /* synthetic */ ThreadPoolExecutor $recognitionThread;
        final /* synthetic */ CreationTaskService $service;
        final /* synthetic */ String $thumbnailName;
        final /* synthetic */ String $thumbnailPath;
        int label;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        /* JADX WARN: Multi-variable type inference failed */
        C02411(String str, String str2, String str3, CreationTaskService creationTaskService, String str4, ThreadPoolExecutor threadPoolExecutor, List<? extends PageData> list, ITaskServiceCallback iTaskServiceCallback, long j, boolean z, ThreadPoolExecutor threadPoolExecutor2, String str5, Continuation<? super C02411> continuation) {
            super(2, continuation);
            this.$thumbnailPath = str;
            this.$thumbnailName = str2;
            this.$outFileName = str3;
            this.$service = creationTaskService;
            this.$dirPath = str4;
            this.$exportThread = threadPoolExecutor;
            this.$dataList = list;
            this.$callback = iTaskServiceCallback;
            this.$creationID = j;
            this.$isAutoSave = z;
            this.$recognitionThread = threadPoolExecutor2;
            this.$recognitionEntFilePath = str5;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
            return new C02411(this.$thumbnailPath, this.$thumbnailName, this.$outFileName, this.$service, this.$dirPath, this.$exportThread, this.$dataList, this.$callback, this.$creationID, this.$isAutoSave, this.$recognitionThread, this.$recognitionEntFilePath, continuation);
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(CoroutineScope coroutineScope, Continuation<? super Unit> continuation) {
            return ((C02411) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            IntrinsicsKt.getCOROUTINE_SUSPENDED();
            if (this.label != 0) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            ResultKt.throwOnFailure(obj);
            final File file = new File(this.$thumbnailPath, this.$thumbnailName);
            File file2 = new File(this.$thumbnailPath, this.$outFileName);
            CreationTaskService creationTaskService = this.$service;
            String str = this.$dirPath;
            String absolutePath = file2.getAbsolutePath();
            ThreadPoolExecutor threadPoolExecutor = this.$exportThread;
            final ITaskServiceCallback iTaskServiceCallback = this.$callback;
            final long j = this.$creationID;
            final boolean z = this.$isAutoSave;
            final ThreadPoolExecutor threadPoolExecutor2 = this.$recognitionThread;
            final String str2 = this.$recognitionEntFilePath;
            final String str3 = this.$dirPath;
            final List<PageData> list = this.$dataList;
            ServiceExportTask serviceExportTask = new ServiceExportTask(creationTaskService, str, absolutePath, 3, threadPoolExecutor, new ServiceExportTask.ExportListener() { // from class: com.miui.handwriting.common.taskservice.TaskHelper$exportThumbnail$1$task$1
                @Override // com.miui.libhandwritingpage.task.ServiceExportTask.ExportListener
                public void onProgress(int progress) {
                }

                @Override // com.miui.libhandwritingpage.task.ServiceExportTask.ExportListener
                public void onCompleted(boolean success, String path) throws RemoteException {
                    if (path != null) {
                        ITaskServiceCallback iTaskServiceCallback2 = iTaskServiceCallback;
                        long j2 = j;
                        File file3 = file;
                        boolean z2 = z;
                        ThreadPoolExecutor threadPoolExecutor3 = threadPoolExecutor2;
                        String str4 = str2;
                        String str5 = str3;
                        List<PageData> list2 = list;
                        HashMap map = new HashMap();
                        map.put(Creation.THUMBNAIL_NAME, path);
                        ContentValues contentValues = TaskHelper.INSTANCE.getContentValues(map, true);
                        if (!contentValues.isEmpty()) {
                            if (iTaskServiceCallback2 != null) {
                                iTaskServiceCallback2.onGenComplete(j2, contentValues);
                            }
                            FileUtil.deleteFile(file3);
                        }
                        if (z2) {
                            return;
                        }
                        MiuiImportStrategy.deleteImportCache();
                        if (iTaskServiceCallback2 != null && iTaskServiceCallback2.asBinder().isBinderAlive()) {
                            iTaskServiceCallback2.backToHome();
                        }
                        TaskHelper taskHelper = TaskHelper.INSTANCE;
                        Intrinsics.checkNotNull(str4);
                        taskHelper.saveRecognition(threadPoolExecutor3, str4, str5, list2, j2, iTaskServiceCallback2);
                        return;
                    }
                    boolean z3 = z;
                    ITaskServiceCallback iTaskServiceCallback3 = iTaskServiceCallback;
                    ThreadPoolExecutor threadPoolExecutor4 = threadPoolExecutor2;
                    String str6 = str2;
                    String str7 = str3;
                    List<PageData> list3 = list;
                    long j3 = j;
                    if (z3) {
                        return;
                    }
                    boolean zExportThumbnailFail = false;
                    if (iTaskServiceCallback3 != null && iTaskServiceCallback3.asBinder().isBinderAlive()) {
                        zExportThumbnailFail = iTaskServiceCallback3.exportThumbnailFail();
                    }
                    if (zExportThumbnailFail) {
                        TaskHelper taskHelper2 = TaskHelper.INSTANCE;
                        Intrinsics.checkNotNull(str6);
                        taskHelper2.saveRecognition(threadPoolExecutor4, str6, str7, list3, j3, iTaskServiceCallback3);
                    }
                }
            });
            Application application = this.$service.getApplication();
            serviceExportTask.setThumbnailSize(DisplayUtils.dip2px(application, 272.0f), DisplayUtils.dip2px(application, 198.0f));
            ArrayList arrayList = new ArrayList();
            ArrayList arrayList2 = new ArrayList();
            arrayList.add(this.$dataList.get(0));
            arrayList2.add(Boxing.boxInt(0));
            final List<PageData> list2 = this.$dataList;
            serviceExportTask.start(arrayList, arrayList2, new PageColorListener() { // from class: com.miui.handwriting.common.taskservice.TaskHelper.exportThumbnail.1.2
                @Override // xc.engine.api.listener.PageColorListener
                public int transformColor(int pageIndex, int color) {
                    return color;
                }

                @Override // xc.engine.api.listener.PageColorListener
                public int transformSpannedColor(int pageIndex, int color) {
                    if (TextUtils.isEmpty(list2.get(pageIndex).pdfId) && TextUtils.isEmpty(list2.get(pageIndex).bgImage)) {
                        if (list2.get(pageIndex).bgColor == -16777216) {
                            if (color == -16777216) {
                                return -1;
                            }
                        } else if (color == -1) {
                            return -16777216;
                        }
                    }
                    return color;
                }
            });
            return Unit.INSTANCE;
        }
    }

    public final ContentValues getContentValues(Map<String, Object> values, final boolean isNeedUpdateModifiedTime) {
        Intrinsics.checkNotNullParameter(values, "values");
        final ContentValues contentValues = new ContentValues();
        if (!values.isEmpty()) {
            values.forEach(new BiConsumer() { // from class: com.miui.handwriting.common.taskservice.TaskHelper$$ExternalSyntheticLambda2
                @Override // java.util.function.BiConsumer
                public final void accept(Object obj, Object obj2) {
                    TaskHelper.getContentValues$lambda$11(contentValues, isNeedUpdateModifiedTime, (String) obj, obj2);
                }
            });
        }
        return contentValues;
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Failed to restore switch over string. Please report as a decompilation issue */
    public static final void getContentValues$lambda$11(ContentValues contentValues, boolean z, String str, Object value) {
        Intrinsics.checkNotNullParameter(value, "value");
        if (str != null) {
            try {
                switch (str.hashCode()) {
                    case -2111535777:
                        if (str.equals("ent_name")) {
                            contentValues.put("ent_name", value.toString());
                        }
                        break;
                    case -1987699608:
                        if (str.equals(Creation.DRAWING_PAPER_TYPE)) {
                            contentValues.put(Creation.DRAWING_PAPER_TYPE, Integer.valueOf(Integer.parseInt(value.toString())));
                        }
                        break;
                    case -1942977599:
                        if (str.equals(Creation.BG_IMAGE_NAME)) {
                            contentValues.put(Creation.BG_IMAGE_NAME, value.toString());
                        }
                        break;
                    case -1223001102:
                        if (str.equals(Creation.IMAGE_NAMES)) {
                            contentValues.put(Creation.IMAGE_NAMES, value.toString());
                        }
                        break;
                    case -1200880356:
                        if (str.equals(Creation.STICK_TIME)) {
                            contentValues.put(Creation.STICK_TIME, Long.valueOf(Long.parseLong(value.toString())));
                        }
                        break;
                    case -1023651534:
                        if (str.equals(Creation.IS_EMPTY_CONTENT)) {
                            contentValues.put(Creation.IS_EMPTY_CONTENT, Integer.valueOf(!Boolean.parseBoolean(value.toString()) ? 1 : 0));
                        }
                        break;
                    case -806401608:
                        if (str.equals(Creation.PDF_NAME)) {
                            contentValues.put(Creation.PDF_NAME, value.toString());
                        }
                        break;
                    case -233430927:
                        if (str.equals(Creation.TOTAL_PAGE_NUM)) {
                            contentValues.put(Creation.TOTAL_PAGE_NUM, Integer.valueOf(Integer.parseInt(value.toString())));
                        }
                        break;
                    case 85191132:
                        if (str.equals("current_page_num")) {
                            contentValues.put("current_page_num", Integer.valueOf(Integer.parseInt(value.toString())));
                        }
                        break;
                    case 110371416:
                        if (str.equals("title")) {
                            contentValues.put("title", value.toString());
                        }
                        break;
                    case 111594124:
                        if (str.equals(Creation.TEXT_CONTENT)) {
                            contentValues.put(Creation.TEXT_CONTENT, value.toString());
                        }
                        break;
                    case 126078352:
                        if (str.equals(Creation.NEW_FEATURE_TYPE)) {
                            contentValues.put(Creation.NEW_FEATURE_TYPE, Integer.valueOf(Integer.parseInt(value.toString())));
                        }
                        break;
                    case 527488652:
                        if (str.equals("folder_id")) {
                            contentValues.put("folder_id", Integer.valueOf(Integer.parseInt(value.toString())));
                        }
                        break;
                    case 759797246:
                        if (str.equals(Creation.THUMBNAIL_NAME)) {
                            contentValues.put(Creation.THUMBNAIL_NAME, value.toString());
                        }
                        break;
                    case 916314744:
                        if (str.equals(Creation.BG_COLOR_TYPE)) {
                            contentValues.put(Creation.BG_COLOR_TYPE, Long.valueOf(Long.parseLong(value.toString())));
                        }
                        break;
                    case 2003148228:
                        if (str.equals(Creation.CREATED_TIME)) {
                            contentValues.put(Creation.CREATED_TIME, Long.valueOf(Long.parseLong(value.toString())));
                        }
                        break;
                    case 2094030467:
                        if (str.equals("modified_time")) {
                            if (!z) {
                                contentValues.put("modified_time", Long.valueOf(Long.parseLong(value.toString())));
                            } else {
                                contentValues.put("modified_time", Long.valueOf(System.currentTimeMillis()));
                            }
                        }
                        break;
                    case 2118628622:
                        if (str.equals("recognized_file_names")) {
                            contentValues.put("recognized_file_names", value.toString());
                        }
                        break;
                    default:
                        break;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public final void doExportPreviewImage(CreationTaskService service, ThreadPoolExecutor exportThread, String preViewFolder, String entFilePath, List<? extends PageData> dataList, String outFileName, int exportType, String entDirPath, String creationTitle, int width, ITaskServiceCallback callback) {
        Intrinsics.checkNotNullParameter(service, "service");
        Intrinsics.checkNotNullParameter(dataList, "dataList");
        Intrinsics.checkNotNullParameter(outFileName, "outFileName");
        Intrinsics.checkNotNullParameter(entDirPath, "entDirPath");
        Intrinsics.checkNotNullParameter(creationTitle, "creationTitle");
        Intrinsics.checkNotNullParameter(callback, "callback");
        File file = new File(Environment.getExternalStorageDirectory(), outFileName);
        Log.d(TAG, "exportPreviewImage: " + outFileName + " dirPath: " + entDirPath);
        if (file.exists()) {
            file.delete();
        }
        BuildersKt__Builders_commonKt.launch$default(CoroutineScopeKt.MainScope(), null, null, new C02401(service, entDirPath, file, exportThread, width, dataList, callback, preViewFolder, entFilePath, exportType, creationTitle, null), 3, null);
    }

    /* JADX INFO: renamed from: com.miui.handwriting.common.taskservice.TaskHelper$doExportPreviewImage$1, reason: invalid class name and case insensitive filesystem */
    /* JADX INFO: compiled from: TaskHelper.kt */
    @Metadata(d1 = {"\u0000\n\n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\u0010\u0000\u001a\u00020\u0001*\u00020\u0002H\n"}, d2 = {"<anonymous>", "", "Lkotlinx/coroutines/CoroutineScope;"}, k = 3, mv = {2, 2, 0}, xi = 48)
    @DebugMetadata(c = "com.miui.handwriting.common.taskservice.TaskHelper$doExportPreviewImage$1", f = "TaskHelper.kt", i = {}, l = {}, m = "invokeSuspend", n = {}, s = {})
    static final class C02401 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
        final /* synthetic */ ITaskServiceCallback $callback;
        final /* synthetic */ String $creationTitle;
        final /* synthetic */ List<PageData> $dataList;
        final /* synthetic */ String $entDirPath;
        final /* synthetic */ String $entFilePath;
        final /* synthetic */ ThreadPoolExecutor $exportThread;
        final /* synthetic */ int $exportType;
        final /* synthetic */ File $outFile;
        final /* synthetic */ String $preViewFolder;
        final /* synthetic */ CreationTaskService $service;
        final /* synthetic */ int $width;
        int label;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        /* JADX WARN: Multi-variable type inference failed */
        C02401(CreationTaskService creationTaskService, String str, File file, ThreadPoolExecutor threadPoolExecutor, int i, List<? extends PageData> list, ITaskServiceCallback iTaskServiceCallback, String str2, String str3, int i2, String str4, Continuation<? super C02401> continuation) {
            super(2, continuation);
            this.$service = creationTaskService;
            this.$entDirPath = str;
            this.$outFile = file;
            this.$exportThread = threadPoolExecutor;
            this.$width = i;
            this.$dataList = list;
            this.$callback = iTaskServiceCallback;
            this.$preViewFolder = str2;
            this.$entFilePath = str3;
            this.$exportType = i2;
            this.$creationTitle = str4;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
            return new C02401(this.$service, this.$entDirPath, this.$outFile, this.$exportThread, this.$width, this.$dataList, this.$callback, this.$preViewFolder, this.$entFilePath, this.$exportType, this.$creationTitle, continuation);
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(CoroutineScope coroutineScope, Continuation<? super Unit> continuation) {
            return ((C02401) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            IntrinsicsKt.getCOROUTINE_SUSPENDED();
            if (this.label != 0) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            ResultKt.throwOnFailure(obj);
            CreationTaskService creationTaskService = this.$service;
            String str = this.$entDirPath;
            String absolutePath = this.$outFile.getAbsolutePath();
            ThreadPoolExecutor threadPoolExecutor = this.$exportThread;
            final ITaskServiceCallback iTaskServiceCallback = this.$callback;
            final List<PageData> list = this.$dataList;
            final String str2 = this.$preViewFolder;
            final String str3 = this.$entDirPath;
            final String str4 = this.$entFilePath;
            final int i = this.$exportType;
            final String str5 = this.$creationTitle;
            ServiceExportTask serviceExportTask = new ServiceExportTask(creationTaskService, str, absolutePath, 4, threadPoolExecutor, new ServiceExportTask.ExportListener() { // from class: com.miui.handwriting.common.taskservice.TaskHelper$doExportPreviewImage$1$task$1
                @Override // com.miui.libhandwritingpage.task.ServiceExportTask.ExportListener
                public void onProgress(int progress) {
                }

                @Override // com.miui.libhandwritingpage.task.ServiceExportTask.ExportListener
                public void onCompleted(boolean success, String path) throws RemoteException {
                    Intrinsics.checkNotNullParameter(path, "path");
                    Log.e(TaskHelper.TAG, "exportPreviewImage onCompleted: " + success + ", path: " + path);
                    if (iTaskServiceCallback.asBinder().isBinderAlive()) {
                        if (success) {
                            ArrayList arrayList = new ArrayList();
                            int size = list.size();
                            for (int i2 = 0; i2 < size; i2++) {
                                String str6 = str2;
                                if (str6 == null) {
                                    str6 = "preview";
                                }
                                arrayList.add(str3 + File.separator + str6 + File.separator + list.get(i2).covePath);
                            }
                            iTaskServiceCallback.showExportPreview(str4, i, str5, arrayList, str3);
                            return;
                        }
                        iTaskServiceCallback.exportPreviewFail();
                    }
                }
            });
            serviceExportTask.setWidth(this.$width);
            ArrayList arrayList = new ArrayList();
            ArrayList arrayList2 = new ArrayList(this.$dataList);
            int size = this.$dataList.size();
            for (int i2 = 0; i2 < size; i2++) {
                arrayList.add(Boxing.boxInt(i2));
            }
            Log.d(TaskHelper.TAG, "exportFile: " + arrayList2.size() + " dateList size: " + this.$dataList.size());
            final List<PageData> list2 = this.$dataList;
            serviceExportTask.start(arrayList2, arrayList, new PageColorListener() { // from class: com.miui.handwriting.common.taskservice.TaskHelper.doExportPreviewImage.1.1
                @Override // xc.engine.api.listener.PageColorListener
                public int transformColor(int pageIndex, int color) {
                    return color;
                }

                @Override // xc.engine.api.listener.PageColorListener
                public int transformSpannedColor(int pageIndex, int color) {
                    if (TextUtils.isEmpty(list2.get(pageIndex).pdfId) && TextUtils.isEmpty(list2.get(pageIndex).bgImage) && list2.size() > pageIndex) {
                        if (list2.get(pageIndex).bgColor == -16777216) {
                            if (color == -16777216) {
                                return -1;
                            }
                        } else if (list2.get(pageIndex).bgColor == -1 && color == -1) {
                            return -16777216;
                        }
                    }
                    return color;
                }
            });
            return Unit.INSTANCE;
        }
    }

    public final void doExportImgToPc(CreationTaskService service, ThreadPoolExecutor exportThread, String dirPathToPc, String folderPathToPc, List<? extends PageData> dataList, ITaskServiceCallback callback) {
        Intrinsics.checkNotNullParameter(service, "service");
        Intrinsics.checkNotNullParameter(dirPathToPc, "dirPathToPc");
        Intrinsics.checkNotNullParameter(folderPathToPc, "folderPathToPc");
        Intrinsics.checkNotNullParameter(dataList, "dataList");
        Intrinsics.checkNotNullParameter(callback, "callback");
        String str = folderPathToPc + '/' + System.currentTimeMillis();
        Log.d(TAG, "export: outFileName end:" + str);
        BuildersKt__Builders_commonKt.launch$default(CoroutineScopeKt.MainScope(), null, null, new C02391(service, dirPathToPc, new File(str), exportThread, dataList, callback, null), 3, null);
    }

    /* JADX INFO: renamed from: com.miui.handwriting.common.taskservice.TaskHelper$doExportImgToPc$1, reason: invalid class name and case insensitive filesystem */
    /* JADX INFO: compiled from: TaskHelper.kt */
    @Metadata(d1 = {"\u0000\n\n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\u0010\u0000\u001a\u00020\u0001*\u00020\u0002H\n"}, d2 = {"<anonymous>", "", "Lkotlinx/coroutines/CoroutineScope;"}, k = 3, mv = {2, 2, 0}, xi = 48)
    @DebugMetadata(c = "com.miui.handwriting.common.taskservice.TaskHelper$doExportImgToPc$1", f = "TaskHelper.kt", i = {}, l = {}, m = "invokeSuspend", n = {}, s = {})
    static final class C02391 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
        final /* synthetic */ ITaskServiceCallback $callback;
        final /* synthetic */ List<PageData> $dataList;
        final /* synthetic */ String $dirPathToPc;
        final /* synthetic */ ThreadPoolExecutor $exportThread;
        final /* synthetic */ File $outFile;
        final /* synthetic */ CreationTaskService $service;
        int label;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        /* JADX WARN: Multi-variable type inference failed */
        C02391(CreationTaskService creationTaskService, String str, File file, ThreadPoolExecutor threadPoolExecutor, List<? extends PageData> list, ITaskServiceCallback iTaskServiceCallback, Continuation<? super C02391> continuation) {
            super(2, continuation);
            this.$service = creationTaskService;
            this.$dirPathToPc = str;
            this.$outFile = file;
            this.$exportThread = threadPoolExecutor;
            this.$dataList = list;
            this.$callback = iTaskServiceCallback;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
            return new C02391(this.$service, this.$dirPathToPc, this.$outFile, this.$exportThread, this.$dataList, this.$callback, continuation);
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(CoroutineScope coroutineScope, Continuation<? super Unit> continuation) {
            return ((C02391) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            IntrinsicsKt.getCOROUTINE_SUSPENDED();
            if (this.label != 0) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            ResultKt.throwOnFailure(obj);
            CreationTaskService creationTaskService = this.$service;
            String str = this.$dirPathToPc;
            String absolutePath = this.$outFile.getAbsolutePath();
            ThreadPoolExecutor threadPoolExecutor = this.$exportThread;
            final ITaskServiceCallback iTaskServiceCallback = this.$callback;
            ServiceExportTask serviceExportTask = new ServiceExportTask(creationTaskService, str, absolutePath, 5, threadPoolExecutor, new ServiceExportTask.ExportListener() { // from class: com.miui.handwriting.common.taskservice.TaskHelper$doExportImgToPc$1$task$1
                @Override // com.miui.libhandwritingpage.task.ServiceExportTask.ExportListener
                public void onProgress(int i) {
                }

                @Override // com.miui.libhandwritingpage.task.ServiceExportTask.ExportListener
                public void onCompleted(boolean success, String path) throws RemoteException {
                    Intrinsics.checkNotNullParameter(path, "path");
                    if (iTaskServiceCallback.asBinder().isBinderAlive()) {
                        iTaskServiceCallback.exportToPcCompleted(path);
                    }
                }
            });
            ArrayList arrayList = new ArrayList(this.$dataList);
            ArrayList arrayList2 = new ArrayList();
            int size = this.$dataList.size();
            for (int i = 0; i < size; i++) {
                arrayList2.add(Boxing.boxInt(i));
            }
            serviceExportTask.setWidth(1440);
            final List<PageData> list = this.$dataList;
            serviceExportTask.start(arrayList, arrayList2, new PageColorListener() { // from class: com.miui.handwriting.common.taskservice.TaskHelper.doExportImgToPc.1.1
                @Override // xc.engine.api.listener.PageColorListener
                public int transformColor(int pageIndex, int color) {
                    return color;
                }

                @Override // xc.engine.api.listener.PageColorListener
                public int transformSpannedColor(int pageIndex, int color) {
                    if (list.size() > pageIndex) {
                        if (list.get(pageIndex).bgColor == -16777216) {
                            if (color == -16777216) {
                                return -1;
                            }
                        } else if (list.get(pageIndex).bgColor == -1 && color == -1) {
                            return -16777216;
                        }
                    }
                    return color;
                }
            });
            return Unit.INSTANCE;
        }
    }
}
