package com.miui.handwriting.common.taskservice;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.DeadSystemException;
import android.os.IBinder;
import android.util.Log;
import ch.qos.logback.core.CoreConstants;
import com.market.sdk.reflect.ReflectUtilsForMiui;
import com.miui.creation.taskservice.ICreationTaskInterface;
import com.miui.creation.taskservice.ISearchCallback;
import com.miui.creation.taskservice.ITaskServiceCallback;
import com.miui.handwriting.common.HandWritingViewApplication;
import com.xiaomi.mirror.synergy.CallMethod;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import kotlinx.coroutines.BuildersKt__Builders_commonKt;
import kotlinx.coroutines.CoroutineExceptionHandler;
import kotlinx.coroutines.CoroutineScopeKt;
import kotlinx.coroutines.Dispatchers;

/* JADX INFO: compiled from: CreationServiceManager.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u0000Z\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\b\n\u0002\b\r\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\n\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\bÆ\u0002\u0018\u00002\u00020\u0001B\t\b\u0002¢\u0006\u0004\b\u0002\u0010\u0003J\u000e\u0010(\u001a\u00020)2\u0006\u0010*\u001a\u00020+J\u0006\u0010,\u001a\u00020)J \u0010-\u001a\u00020)2\u0006\u0010.\u001a\u00020\u00072\u0006\u0010/\u001a\u00020\u00152\u0006\u00100\u001a\u00020\u0017H\u0002J\u0006\u00103\u001a\u00020)J\u0018\u00106\u001a\u00020\u00192\u0006\u0010.\u001a\u00020\u00072\b\u00107\u001a\u0004\u0018\u000108J\"\u00106\u001a\u00020\u00192\u0006\u0010.\u001a\u00020\u00072\b\u0010/\u001a\u0004\u0018\u00010\u00152\b\u00100\u001a\u0004\u0018\u00010\u0017J0\u00106\u001a\u00020\u00192\u0006\u0010.\u001a\u00020\u00072\b\u0010/\u001a\u0004\u0018\u00010\u00152\b\u00100\u001a\u0004\u0018\u00010\u00172\n\b\u0002\u00107\u001a\u0004\u0018\u000108H\u0002R\u000e\u0010\u0004\u001a\u00020\u0005X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0007X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\b\u001a\u00020\u0007X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\t\u001a\u00020\u0007X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\n\u001a\u00020\u0007X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\u000b\u001a\u00020\u0007X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\f\u001a\u00020\u0007X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\r\u001a\u00020\u0007X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\u000e\u001a\u00020\u0007X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\u000f\u001a\u00020\u0007X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\u0010\u001a\u00020\u0007X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\u0011\u001a\u00020\u0007X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\u0012\u001a\u00020\u0007X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\u0013\u001a\u00020\u0007X\u0082\u000e¢\u0006\u0002\n\u0000R\u0010\u0010\u0014\u001a\u0004\u0018\u00010\u0015X\u0082\u000e¢\u0006\u0002\n\u0000R\u0010\u0010\u0016\u001a\u0004\u0018\u00010\u0017X\u0082\u000e¢\u0006\u0002\n\u0000R\u001a\u0010\u0018\u001a\u00020\u0019X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0018\u0010\u001a\"\u0004\b\u001b\u0010\u001cR\u001c\u0010\u001d\u001a\u0004\u0018\u00010\u001eX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u001f\u0010 \"\u0004\b!\u0010\"R\u001a\u0010#\u001a\u00020\u0007X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b$\u0010%\"\u0004\b&\u0010'R\u000e\u00101\u001a\u000202X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u00104\u001a\u000205X\u0082\u0004¢\u0006\u0002\n\u0000¨\u00069"}, d2 = {"Lcom/miui/handwriting/common/taskservice/CreationServiceManager;", "", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "()V", "TAG", "", "AUTO_SAVE_THUMBNAIL", "", "EXPORT_PREVIEW_IMAGE", "EXPORT_PAGE_TO_AI", "EXPORT_PAGE_TO_PC", "EXPORT_FILE", "SAVE_THUMBNAIL_AND_RECOGNITION", "SELECT_RECOGNITION", "SET_SEARCH_CALLBACK", "REMOVE_SEARCH_CALLBACK", "SELECT_CLASSIFICATION_FOR_AI", "SAVE_RECOGNITION_ONLY", "RESTART_THRESHOLD", "taskType", "lastBundle", "Landroid/os/Bundle;", "lastCallBack", "Lcom/miui/creation/taskservice/ITaskServiceCallback;", "isAbnormalDisconnection", "", "()Z", "setAbnormalDisconnection", "(Z)V", "taskServiceBridge", "Lcom/miui/creation/taskservice/ICreationTaskInterface;", "getTaskServiceBridge", "()Lcom/miui/creation/taskservice/ICreationTaskInterface;", "setTaskServiceBridge", "(Lcom/miui/creation/taskservice/ICreationTaskInterface;)V", "restartNum", "getRestartNum", "()I", "setRestartNum", "(I)V", "bindCreationTaskService", "", CoreConstants.CONTEXT_SCOPE_VALUE, "Landroid/content/Context;", "clearLastMessage", "setLastData", "taskCode", "bundle", CallMethod.ARG_CALLBACK, "mConnection", "Landroid/content/ServiceConnection;", "restartServiceTask", "exceptionHandler", "Lkotlinx/coroutines/CoroutineExceptionHandler;", "dispatchTask", "searchCallback", "Lcom/miui/creation/taskservice/ISearchCallback;", "libHandWritingCommon_release"}, k = 1, mv = {2, 2, 0}, xi = 48)
public final class CreationServiceManager {
    public static final int AUTO_SAVE_THUMBNAIL = 1;
    public static final int EXPORT_FILE = 5;
    public static final int EXPORT_PAGE_TO_AI = 3;
    public static final int EXPORT_PAGE_TO_PC = 4;
    public static final int EXPORT_PREVIEW_IMAGE = 2;
    public static final int REMOVE_SEARCH_CALLBACK = 9;
    public static final int RESTART_THRESHOLD = 3;
    public static final int SAVE_RECOGNITION_ONLY = 11;
    public static final int SAVE_THUMBNAIL_AND_RECOGNITION = 6;
    public static final int SELECT_CLASSIFICATION_FOR_AI = 10;
    public static final int SELECT_RECOGNITION = 7;
    public static final int SET_SEARCH_CALLBACK = 8;
    public static final String TAG = "CreationServiceManager";
    private static boolean isAbnormalDisconnection;
    private static Bundle lastBundle;
    private static ITaskServiceCallback lastCallBack;
    private static int restartNum;
    private static ICreationTaskInterface taskServiceBridge;
    public static final CreationServiceManager INSTANCE = new CreationServiceManager();
    private static int taskType = -1;
    private static final ServiceConnection mConnection = new ServiceConnection() { // from class: com.miui.handwriting.common.taskservice.CreationServiceManager$mConnection$1
        @Override // android.content.ServiceConnection
        public void onServiceConnected(ComponentName className, IBinder service) {
            Intrinsics.checkNotNullParameter(className, "className");
            Intrinsics.checkNotNullParameter(service, "service");
            CreationServiceManager.INSTANCE.setTaskServiceBridge(ICreationTaskInterface.Stub.asInterface(service));
            if (CreationServiceManager.INSTANCE.isAbnormalDisconnection()) {
                CreationServiceManager.INSTANCE.restartServiceTask();
            }
        }

        @Override // android.content.ServiceConnection
        public void onServiceDisconnected(ComponentName className) {
            Intrinsics.checkNotNullParameter(className, "className");
            CreationServiceManager.INSTANCE.setAbnormalDisconnection(true);
            CreationServiceManager.INSTANCE.setTaskServiceBridge(null);
            CreationServiceManager.INSTANCE.bindCreationTaskService(HandWritingViewApplication.INSTANCE.getApplication());
        }
    };
    private static final CoroutineExceptionHandler exceptionHandler = new CreationServiceManager$special$$inlined$CoroutineExceptionHandler$1(CoroutineExceptionHandler.INSTANCE);

    private CreationServiceManager() {
    }

    public final boolean isAbnormalDisconnection() {
        return isAbnormalDisconnection;
    }

    public final void setAbnormalDisconnection(boolean z) {
        isAbnormalDisconnection = z;
    }

    public final ICreationTaskInterface getTaskServiceBridge() {
        return taskServiceBridge;
    }

    public final void setTaskServiceBridge(ICreationTaskInterface iCreationTaskInterface) {
        taskServiceBridge = iCreationTaskInterface;
    }

    public final int getRestartNum() {
        return restartNum;
    }

    public final void setRestartNum(int i) {
        restartNum = i;
    }

    public final void bindCreationTaskService(Context context) {
        Intrinsics.checkNotNullParameter(context, "context");
        if (taskServiceBridge == null) {
            try {
                context.bindService(new Intent(context, (Class<?>) CreationTaskService.class), mConnection, 1);
            } catch (DeadSystemException e) {
                Log.d(TAG, "" + e);
            }
        }
    }

    public final void clearLastMessage() {
        taskType = -1;
        lastBundle = null;
        lastCallBack = null;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void setLastData(int taskCode, Bundle bundle, ITaskServiceCallback callback) {
        taskType = taskCode;
        lastBundle = bundle;
        lastCallBack = callback;
    }

    public final void restartServiceTask() {
        Bundle bundle;
        ITaskServiceCallback iTaskServiceCallback;
        int i = taskType;
        if (i == -1 || (bundle = lastBundle) == null || (iTaskServiceCallback = lastCallBack) == null) {
            return;
        }
        CreationServiceManager creationServiceManager = INSTANCE;
        restartNum++;
        creationServiceManager.dispatchTask(i, bundle, iTaskServiceCallback);
    }

    public final boolean dispatchTask(int taskCode, ISearchCallback searchCallback) {
        return dispatchTask(taskCode, null, null, searchCallback);
    }

    public final boolean dispatchTask(int taskCode, Bundle bundle, ITaskServiceCallback callback) {
        return dispatchTask(taskCode, bundle, callback, null);
    }

    static /* synthetic */ boolean dispatchTask$default(CreationServiceManager creationServiceManager, int i, Bundle bundle, ITaskServiceCallback iTaskServiceCallback, ISearchCallback iSearchCallback, int i2, Object obj) {
        if ((i2 & 8) != 0) {
            iSearchCallback = null;
        }
        return creationServiceManager.dispatchTask(i, bundle, iTaskServiceCallback, iSearchCallback);
    }

    private final boolean dispatchTask(int taskCode, Bundle bundle, ITaskServiceCallback callback, ISearchCallback searchCallback) {
        ICreationTaskInterface iCreationTaskInterface = taskServiceBridge;
        if (iCreationTaskInterface == null) {
            return true;
        }
        if (iCreationTaskInterface.asBinder().isBinderAlive()) {
            BuildersKt__Builders_commonKt.launch$default(CoroutineScopeKt.CoroutineScope(Dispatchers.getDefault()), exceptionHandler, null, new CreationServiceManager$dispatchTask$1$1(taskCode, bundle, callback, iCreationTaskInterface, searchCallback, null), 2, null);
            return true;
        }
        Log.e(TAG, "dispatchTask: binder dead");
        return false;
    }
}
