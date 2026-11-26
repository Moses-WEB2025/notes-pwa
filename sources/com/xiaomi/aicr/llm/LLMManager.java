package com.xiaomi.aicr.llm;

import android.app.ActivityManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.pm.PackageInfo;
import android.os.Binder;
import android.os.Build;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.os.ResultReceiver;
import android.provider.Settings;
import android.util.Log;
import androidx.core.app.NotificationCompat;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.DefaultLifecycleObserver;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleOwner;
import ch.qos.logback.core.CoreConstants;
import com.market.sdk.reflect.ReflectUtilsForMiui;
import com.miui.notes.basefeature.NotesNormalConstants;
import com.xiaomi.aicr.common.AiServiceConnection;
import com.xiaomi.aicr.common.SmartLog;
import com.xiaomi.aicr.constant.Constants;
import com.xiaomi.mirror.synergy.CallMethod;
import java.util.HashMap;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.JvmStatic;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.Regex;
import kotlinx.coroutines.channels.BufferOverflow;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.MutableSharedFlow;
import kotlinx.coroutines.flow.SharedFlowKt;
import miuix.smartaction.SmartAction;
import org.apache.commons.lang3.StringUtils;

/* JADX INFO: compiled from: LLMManager.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000\u0081\u0001\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010 \n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\r\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004*\u0001\u0011\u0018\u0000 B2\u00020\u0001:\u0001BB#\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\n\b\u0002\u0010\u0006\u001a\u0004\u0018\u00010\u0007¢\u0006\u0004\b\b\u0010\tJ\b\u0010\u0016\u001a\u00020\u0017H\u0002J\u0006\u0010\u0018\u001a\u00020\u0019J|\u0010\u001a\u001a\u00020\u00192\b\u0010\u001b\u001a\u0004\u0018\u00010\u001c2\u0010\u0010\u001d\u001a\f\u0012\u0006\u0012\u0004\u0018\u00010\u001c\u0018\u00010\u001e2\u0010\u0010\u001f\u001a\f\u0012\u0006\u0012\u0004\u0018\u00010\u001c\u0018\u00010\u001e2\u0010\u0010 \u001a\f\u0012\u0006\u0012\u0004\u0018\u00010\u001c\u0018\u00010\u001e2\u0010\u0010!\u001a\f\u0012\u0006\u0012\u0004\u0018\u00010\u001c\u0018\u00010\u001e2\u0010\u0010\"\u001a\f\u0012\u0006\u0012\u0004\u0018\u00010\u001c\u0018\u00010\u001e2\u0010\u0010#\u001a\f\u0012\u0006\u0012\u0004\u0018\u00010\u001c\u0018\u00010\u001eJ\u0086\u0001\u0010$\u001a\u00020\u00192\b\u0010\u001b\u001a\u0004\u0018\u00010\u001c2\u0010\u0010\u001d\u001a\f\u0012\u0006\u0012\u0004\u0018\u00010\u001c\u0018\u00010\u001e2\u0010\u0010\u001f\u001a\f\u0012\u0006\u0012\u0004\u0018\u00010\u001c\u0018\u00010\u001e2\u0010\u0010 \u001a\f\u0012\u0006\u0012\u0004\u0018\u00010\u001c\u0018\u00010\u001e2\u0010\u0010!\u001a\f\u0012\u0006\u0012\u0004\u0018\u00010\u001c\u0018\u00010\u001e2\u0010\u0010\"\u001a\f\u0012\u0006\u0012\u0004\u0018\u00010\u001c\u0018\u00010\u001e2\u0010\u0010#\u001a\f\u0012\u0006\u0012\u0004\u0018\u00010\u001c\u0018\u00010\u001e2\b\u0010%\u001a\u0004\u0018\u00010&J\u000e\u0010'\u001a\u00020\u00192\u0006\u0010(\u001a\u00020\u001cJ\u000e\u0010)\u001a\u00020\u00192\u0006\u0010*\u001a\u00020+J\u0006\u0010,\u001a\u00020\u0019J\u001e\u0010-\u001a\n\u0012\u0004\u0012\u00020/\u0018\u00010.2\u0006\u0010!\u001a\u00020\u001c2\u0006\u00100\u001a\u00020\u001cJ\u001e\u00101\u001a\n\u0012\u0004\u0012\u00020/\u0018\u00010.2\u0006\u0010!\u001a\u00020\u001c2\u0006\u00100\u001a\u00020\u001cJ\u001e\u00102\u001a\n\u0012\u0004\u0012\u00020/\u0018\u00010.2\u0006\u0010!\u001a\u00020\u001c2\u0006\u00100\u001a\u00020\u001cJ&\u00103\u001a\n\u0012\u0004\u0012\u00020/\u0018\u00010.2\u0006\u0010!\u001a\u00020\u001c2\u0006\u00100\u001a\u00020\u001c2\u0006\u00104\u001a\u00020\u001cJ&\u00105\u001a\n\u0012\u0004\u0012\u00020/\u0018\u00010.2\u0006\u00100\u001a\u00020\u001c2\u0006\u00106\u001a\u00020\u001c2\u0006\u00107\u001a\u00020\u0019J\u0006\u00108\u001a\u00020\u0019J\u0016\u00109\u001a\u00020\u00192\u0006\u0010(\u001a\u00020\u001c2\u0006\u0010:\u001a\u00020\u0019J\u0006\u0010;\u001a\u00020\u0019J\u001e\u0010<\u001a\n\u0012\u0004\u0012\u00020=\u0018\u00010.2\u0006\u0010(\u001a\u00020\u001c2\u0006\u0010>\u001a\u00020?J\u0006\u0010@\u001a\u00020\u001cJ\u0006\u0010A\u001a\u00020\u001cR\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004¢\u0006\u0002\n\u0000R\u0010\u0010\u0006\u001a\u0004\u0018\u00010\u0007X\u0082\u0004¢\u0006\u0002\n\u0000R\u0010\u0010\n\u001a\u0004\u0018\u00010\u000bX\u0082\u000e¢\u0006\u0002\n\u0000R\u0011\u0010\f\u001a\u00020\r¢\u0006\b\n\u0000\u001a\u0004\b\u000e\u0010\u000fR\u0010\u0010\u0010\u001a\u00020\u0011X\u0082\u0004¢\u0006\u0004\n\u0002\u0010\u0012R\u0011\u0010\u0013\u001a\u00020\u00148F¢\u0006\u0006\u001a\u0004\b\u0013\u0010\u0015¨\u0006C"}, d2 = {"Lcom/xiaomi/aicr/llm/LLMManager;", "", CoreConstants.CONTEXT_SCOPE_VALUE, "Landroid/content/Context;", "scc", "Lcom/xiaomi/aicr/common/AiServiceConnection;", "lifecycleOwner", "Landroidx/lifecycle/LifecycleOwner;", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "(Landroid/content/Context;Lcom/xiaomi/aicr/common/AiServiceConnection;Landroidx/lifecycle/LifecycleOwner;)V", NotificationCompat.CATEGORY_SERVICE, "Lcom/xiaomi/aicr/llm/ILLMService;", "mToken", "Landroid/os/Binder;", "getMToken", "()Landroid/os/Binder;", "connection", "com/xiaomi/aicr/llm/LLMManager$connection$1", "Lcom/xiaomi/aicr/llm/LLMManager$connection$1;", "isConnected", "", "()Z", "connectLLMService", "", "messageSummarizeCheck", "", "messageSummarizeAddTask", "session", "", "packageList", "", "receiverList", "messageList", "bizCode", "category", "inputID", "messageSummarizeAddTaskV2", "receiver", "Landroid/os/ResultReceiver;", "textEditCheck", "type", "textEditSearchDevice", CallMethod.ARG_CALLBACK, "Lcom/xiaomi/aicr/llm/IDeviceSearchCallback;", "cancelTextEditSearchDevice", "addTextEditSummaryRequest", "Lkotlinx/coroutines/flow/Flow;", "Lcom/xiaomi/aicr/llm/TextEditProcessResult;", "inputText", "addTextEditExpandRequest", "addTextEditCorrectRequest", "addTextEditPolishRequest", "polishType", "addTextEditCOTRequest", SmartAction.Feature.QUERY, "addThink", "refresh", "initModel", "lastingTime", "releaseConnect", "addOnLineCreationRequest", "Lcom/xiaomi/aicr/llm/AICreationResult;", "bundle", "Landroid/os/Bundle;", "creationScreenShot", "creationGetVersion", "Companion", "aicr_AI_sdk_release"}, k = 1, mv = {2, 0, 0}, xi = 48)
public final class LLMManager {

    /* JADX INFO: renamed from: Companion, reason: from kotlin metadata */
    public static final Companion INSTANCE = new Companion(null);
    public static final String LLM_BASE_MODEL = "LLM_BASE_MODEL";
    public static final String LLM_COT_MODEL = "LLM_COT_MODEL";
    private static final String LLM_SERVICE_NAME = "com.xiaomi.aicr.llm.LLMService";
    public static final int LLM_TASK_CROSS = 9004;
    public static final int LLM_TASK_EDITTEXT_CONTROL = 9002;
    public static final int LLM_TASK_EDITTEXT_COT = 9003;
    public static final int LLM_TASK_MESSAGE_SUMMARIZE = 9001;
    private static final String PACKAGE_NAME = "com.xiaomi.aicr";
    public static final String POLISH_STYLE_CONCISE = "concise";
    public static final String POLISH_STYLE_FRIENDLY = "friendly";
    public static final String POLISH_STYLE_GENERAL = "general";
    public static final String POLISH_STYLE_PROFESSIONAL = "professional";
    private static final String TAG = "LLMManager";
    private final LLMManager$connection$1 connection;
    private final Context context;
    private final LifecycleOwner lifecycleOwner;
    private final Binder mToken;
    private final AiServiceConnection scc;
    private ILLMService service;

    @JvmStatic
    public static final Map<String, String> getLLMCloudConfig(Context context, int i) {
        return INSTANCE.getLLMCloudConfig(context, i);
    }

    @JvmStatic
    public static final String isDistComputeSupport(Context context, String str) {
        return INSTANCE.isDistComputeSupport(context, str);
    }

    @JvmStatic
    public static final boolean isLLMSupport(Context context, int i) {
        return INSTANCE.isLLMSupport(context, i);
    }

    @JvmStatic
    public static final boolean isModelManageSupport(Context context, String str) {
        return INSTANCE.isModelManageSupport(context, str);
    }

    /* JADX WARN: Type inference failed for: r2v2, types: [com.xiaomi.aicr.llm.LLMManager$connection$1] */
    public LLMManager(Context context, AiServiceConnection scc, LifecycleOwner lifecycleOwner) {
        Lifecycle lifecycle;
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(scc, "scc");
        this.context = context;
        this.scc = scc;
        this.lifecycleOwner = lifecycleOwner;
        this.mToken = new Binder();
        this.connection = new ServiceConnection() { // from class: com.xiaomi.aicr.llm.LLMManager$connection$1
            @Override // android.content.ServiceConnection
            public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
                Intrinsics.checkNotNullParameter(componentName, "componentName");
                Intrinsics.checkNotNullParameter(iBinder, "iBinder");
                SmartLog.i("LLMManager", "onServiceConnected");
                LLMManager lLMManager = this.this$0;
                ILLMService iLLMServiceAsInterface = ILLMService.Stub.asInterface(iBinder);
                LLMManager lLMManager2 = this.this$0;
                try {
                    iLLMServiceAsInterface.registerDeathLinkCallback(lLMManager2.getMToken(), lLMManager2.getMToken().toString());
                } catch (Exception e) {
                    SmartLog.e("LLMManager", e.toString());
                    lLMManager2.scc.onError(-100);
                }
                lLMManager.service = iLLMServiceAsInterface;
                this.this$0.scc.onServiceConnected(0);
            }

            @Override // android.content.ServiceConnection
            public void onServiceDisconnected(ComponentName componentName) {
                Intrinsics.checkNotNullParameter(componentName, "componentName");
                SmartLog.i("LLMManager", "onServiceDisconnected");
                this.this$0.service = null;
                this.this$0.scc.onServiceDisconnected();
            }
        };
        if (lifecycleOwner != null && (lifecycle = lifecycleOwner.getLifecycle()) != null) {
            lifecycle.addObserver(new DefaultLifecycleObserver() { // from class: com.xiaomi.aicr.llm.LLMManager.1
                @Override // androidx.lifecycle.DefaultLifecycleObserver
                public void onDestroy(LifecycleOwner owner) {
                    Intrinsics.checkNotNullParameter(owner, "owner");
                    LLMManager.this.lifecycleOwner.getLifecycle().removeObserver(this);
                    LLMManager.this.releaseConnect();
                }
            });
        }
        connectLLMService();
    }

    public /* synthetic */ LLMManager(Context context, AiServiceConnection aiServiceConnection, LifecycleOwner lifecycleOwner, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(context, aiServiceConnection, (i & 4) != 0 ? null : lifecycleOwner);
    }

    public final Binder getMToken() {
        return this.mToken;
    }

    public final boolean isConnected() {
        SmartLog.i(TAG, "isConnected");
        return this.service != null;
    }

    private final void connectLLMService() {
        SmartLog.i(TAG, "connectLLMService");
        if (Build.VERSION.SDK_INT >= 31) {
            Intent intent = new Intent();
            intent.setComponent(new ComponentName("com.xiaomi.aicr", LLM_SERVICE_NAME));
            try {
                this.context.startService(intent);
            } catch (Exception e) {
                SmartLog.e(TAG, "startService exception: " + e);
            }
            try {
                Boolean.valueOf(this.context.bindService(intent, this.connection, 1));
            } catch (SecurityException e2) {
                Integer.valueOf(SmartLog.e(TAG, "bindService exception: " + e2));
            }
        }
    }

    public final int messageSummarizeCheck() {
        try {
            if (this.service != null) {
                SmartLog.i(TAG, "messageSummarizeCheck");
                ILLMService iLLMService = this.service;
                if (iLLMService != null) {
                    return iLLMService.messageSummarizeCheck();
                }
                return 3;
            }
            SmartLog.i(TAG, "service not connected yet!");
            connectLLMService();
            return 3;
        } catch (Exception e) {
            SmartLog.e(TAG, "messageSummarizeCheck exception: " + e);
        }
    }

    public final int messageSummarizeAddTask(String session, List<String> packageList, List<String> receiverList, List<String> messageList, List<String> bizCode, List<String> category, List<String> inputID) {
        try {
            if (this.service != null) {
                Log.i(TAG, "messageSummarizeProcess");
                ILLMService iLLMService = this.service;
                if (iLLMService != null) {
                    return iLLMService.messageSummarizeProcess(session, packageList, receiverList, messageList, bizCode, category, inputID);
                }
                return -1;
            }
            SmartLog.i(TAG, "service not connected yet!");
            connectLLMService();
            return -1;
        } catch (Exception e) {
            SmartLog.e(TAG, "messageSummarizeProcess exception: " + e);
        }
    }

    public final int messageSummarizeAddTaskV2(String session, List<String> packageList, List<String> receiverList, List<String> messageList, List<String> bizCode, List<String> category, List<String> inputID, ResultReceiver receiver) {
        try {
            if (this.service != null) {
                SmartLog.i(TAG, "messageSummarizeProcessV2");
                ILLMService iLLMService = this.service;
                if (iLLMService != null) {
                    return iLLMService.messageSummarizeProcessV2(session, packageList, receiverList, messageList, bizCode, category, inputID, receiver);
                }
                return -1;
            }
            SmartLog.i(TAG, "service not connected yet!");
            connectLLMService();
            return -1;
        } catch (Exception e) {
            SmartLog.e(TAG, "messageSummarizeProcess exception: " + e);
        }
    }

    public final int textEditCheck(String type) {
        Intrinsics.checkNotNullParameter(type, "type");
        try {
            if (this.service != null) {
                SmartLog.i(TAG, "textEditCheck");
                ILLMService iLLMService = this.service;
                if (iLLMService != null) {
                    return iLLMService.textEditCheck(type);
                }
                return 3;
            }
            SmartLog.i(TAG, "service not connected yet!");
            connectLLMService();
            return 3;
        } catch (Exception e) {
            SmartLog.e(TAG, "textEditSummaryCheck exception: " + e);
        }
    }

    public final int textEditSearchDevice(IDeviceSearchCallback callback) {
        Intrinsics.checkNotNullParameter(callback, "callback");
        try {
            if (this.service != null) {
                SmartLog.i(TAG, "textEditSearchDevice");
                ILLMService iLLMService = this.service;
                if (iLLMService != null) {
                    return iLLMService.textEditSearchDevice(callback);
                }
                return 3;
            }
            SmartLog.i(TAG, "service not connected yet!");
            connectLLMService();
            return 3;
        } catch (Exception e) {
            SmartLog.e(TAG, "textEditSearchDevice exception: " + e);
        }
    }

    public final int cancelTextEditSearchDevice() {
        try {
            if (this.service != null) {
                SmartLog.i(TAG, "cancelTextEditSearchDevice");
                ILLMService iLLMService = this.service;
                if (iLLMService != null) {
                    return iLLMService.cancelTextEditSearchDevice();
                }
                return 3;
            }
            SmartLog.i(TAG, "service not connected yet!");
            connectLLMService();
            return 3;
        } catch (RemoteException e) {
            SmartLog.e(TAG, "cancelTextEditSummarySearchDevice exception: " + e);
        }
    }

    public final Flow<TextEditProcessResult> addTextEditSummaryRequest(String bizCode, String inputText) {
        Intrinsics.checkNotNullParameter(bizCode, "bizCode");
        Intrinsics.checkNotNullParameter(inputText, "inputText");
        try {
            SmartLog.i(TAG, "addTextEditSummaryRequest");
            if (this.service != null) {
                final MutableSharedFlow MutableSharedFlow = SharedFlowKt.MutableSharedFlow(3000, 3000, BufferOverflow.DROP_LATEST);
                ILLMService iLLMService = this.service;
                if (iLLMService != null) {
                    iLLMService.textEditProcess(NotesNormalConstants.SUMMARY_TABLE_NAME, bizCode, inputText, new ITextEditProcessCallback.Stub() { // from class: com.xiaomi.aicr.llm.LLMManager.addTextEditSummaryRequest.1
                        @Override // com.xiaomi.aicr.llm.ITextEditProcessCallback
                        public void onResult(TextEditProcessResult result) {
                            Intrinsics.checkNotNullParameter(result, "result");
                            SmartLog.i(LLMManager.TAG, "editText: onResult");
                            MutableSharedFlow.tryEmit(result);
                        }
                    });
                }
                return MutableSharedFlow;
            }
            SmartLog.i(TAG, "service not connected yet!");
            connectLLMService();
            return null;
        } catch (Exception e) {
            SmartLog.e(TAG, "addTextEditSummaryRequest exception: " + e);
            return null;
        }
    }

    public final Flow<TextEditProcessResult> addTextEditExpandRequest(String bizCode, String inputText) {
        Intrinsics.checkNotNullParameter(bizCode, "bizCode");
        Intrinsics.checkNotNullParameter(inputText, "inputText");
        try {
            SmartLog.i(TAG, "addTextEditExpandRequest");
            if (this.service != null) {
                final MutableSharedFlow MutableSharedFlow = SharedFlowKt.MutableSharedFlow(3000, 3000, BufferOverflow.DROP_LATEST);
                ILLMService iLLMService = this.service;
                if (iLLMService != null) {
                    iLLMService.textEditProcess("extend", bizCode, inputText, new ITextEditProcessCallback.Stub() { // from class: com.xiaomi.aicr.llm.LLMManager.addTextEditExpandRequest.1
                        @Override // com.xiaomi.aicr.llm.ITextEditProcessCallback
                        public void onResult(TextEditProcessResult result) {
                            Intrinsics.checkNotNullParameter(result, "result");
                            MutableSharedFlow.tryEmit(result);
                        }
                    });
                }
                return MutableSharedFlow;
            }
            SmartLog.i(TAG, "service not connected yet!");
            connectLLMService();
            return null;
        } catch (Exception e) {
            SmartLog.e(TAG, "addTextEditExpandRequest exception: " + e);
            return null;
        }
    }

    public final Flow<TextEditProcessResult> addTextEditCorrectRequest(String bizCode, String inputText) {
        Intrinsics.checkNotNullParameter(bizCode, "bizCode");
        Intrinsics.checkNotNullParameter(inputText, "inputText");
        try {
            SmartLog.i(TAG, "addTextEditCorrectRequest");
            if (this.service != null) {
                final MutableSharedFlow MutableSharedFlow = SharedFlowKt.MutableSharedFlow(3000, 3000, BufferOverflow.DROP_LATEST);
                ILLMService iLLMService = this.service;
                if (iLLMService != null) {
                    iLLMService.textEditProcess("proofread", bizCode, inputText, new ITextEditProcessCallback.Stub() { // from class: com.xiaomi.aicr.llm.LLMManager.addTextEditCorrectRequest.1
                        @Override // com.xiaomi.aicr.llm.ITextEditProcessCallback
                        public void onResult(TextEditProcessResult result) {
                            Intrinsics.checkNotNullParameter(result, "result");
                            MutableSharedFlow.tryEmit(result);
                        }
                    });
                }
                return MutableSharedFlow;
            }
            SmartLog.i(TAG, "service not connected yet!");
            connectLLMService();
            return null;
        } catch (Exception e) {
            SmartLog.e(TAG, "addTextEditCorrectRequest exception: " + e);
            return null;
        }
    }

    public final Flow<TextEditProcessResult> addTextEditPolishRequest(String bizCode, String inputText, String polishType) {
        Intrinsics.checkNotNullParameter(bizCode, "bizCode");
        Intrinsics.checkNotNullParameter(inputText, "inputText");
        Intrinsics.checkNotNullParameter(polishType, "polishType");
        try {
            SmartLog.i(TAG, "addTextEditPolishRequest");
            if (this.service != null) {
                final MutableSharedFlow MutableSharedFlow = SharedFlowKt.MutableSharedFlow(3000, 3000, BufferOverflow.DROP_LATEST);
                ILLMService iLLMService = this.service;
                if (iLLMService != null) {
                    iLLMService.textEditProcess(polishType, bizCode, inputText, new ITextEditProcessCallback.Stub() { // from class: com.xiaomi.aicr.llm.LLMManager.addTextEditPolishRequest.1
                        @Override // com.xiaomi.aicr.llm.ITextEditProcessCallback
                        public void onResult(TextEditProcessResult result) {
                            Intrinsics.checkNotNullParameter(result, "result");
                            MutableSharedFlow.tryEmit(result);
                        }
                    });
                }
                return MutableSharedFlow;
            }
            SmartLog.i(TAG, "service not connected yet!");
            connectLLMService();
            return null;
        } catch (Exception e) {
            SmartLog.e(TAG, "addTextEditPolishRequest exception: " + e);
            return null;
        }
    }

    public final Flow<TextEditProcessResult> addTextEditCOTRequest(String inputText, String query, int addThink) {
        Intrinsics.checkNotNullParameter(inputText, "inputText");
        Intrinsics.checkNotNullParameter(query, "query");
        try {
            SmartLog.i(TAG, "addTextEditCOTRequest");
            if (this.service != null) {
                final MutableSharedFlow MutableSharedFlow = SharedFlowKt.MutableSharedFlow(3000, 3000, BufferOverflow.DROP_LATEST);
                ILLMService iLLMService = this.service;
                if (iLLMService != null) {
                    iLLMService.textEditCOTProcess(addThink == 0 ? "COT" : "COT_addThink", inputText, query, new ITextEditProcessCallback.Stub() { // from class: com.xiaomi.aicr.llm.LLMManager.addTextEditCOTRequest.1
                        @Override // com.xiaomi.aicr.llm.ITextEditProcessCallback
                        public void onResult(TextEditProcessResult result) {
                            Intrinsics.checkNotNullParameter(result, "result");
                            MutableSharedFlow.tryEmit(result);
                        }
                    });
                }
                return MutableSharedFlow;
            }
            SmartLog.i(TAG, "service not connected yet!");
            connectLLMService();
            return null;
        } catch (Exception e) {
            SmartLog.e(TAG, "addTextEditCOTRequest exception: " + e);
            return null;
        }
    }

    public final int refresh() {
        try {
            SmartLog.i(TAG, "refresh");
            ILLMService iLLMService = this.service;
            if (iLLMService == null) {
                SmartLog.i(TAG, "service not connected yet!");
                connectLLMService();
                return -1;
            }
            if (iLLMService == null) {
                return 0;
            }
            iLLMService.textEditStopAllProcess();
            return 0;
        } catch (Exception e) {
            SmartLog.e(TAG, "addTextEditPolishRequest exception: " + e);
            return -1;
        }
    }

    public final int initModel(String type, int lastingTime) {
        Intrinsics.checkNotNullParameter(type, "type");
        try {
            SmartLog.i(TAG, "initModel");
            ILLMService iLLMService = this.service;
            if (iLLMService == null) {
                SmartLog.i(TAG, "service not connected yet!");
                connectLLMService();
                return -1;
            }
            if (iLLMService == null) {
                return 0;
            }
            iLLMService.initModel(type, lastingTime);
            return 0;
        } catch (Exception e) {
            SmartLog.e(TAG, "initModel exception: " + e);
            return -1;
        }
    }

    public final int releaseConnect() {
        SmartLog.i(TAG, "releaseConnect");
        if (!isConnected()) {
            SmartLog.i(TAG, "service not connected!");
            return -1;
        }
        try {
            ILLMService iLLMService = this.service;
            if (iLLMService != null) {
                iLLMService.removeDeathLinkCallback(this.mToken.toString());
            }
        } catch (Exception e) {
            SmartLog.e(TAG, "fail to release connect :" + e);
        }
        this.context.unbindService(this.connection);
        this.service = null;
        return 0;
    }

    public final Flow<AICreationResult> addOnLineCreationRequest(String type, Bundle bundle) {
        Intrinsics.checkNotNullParameter(type, "type");
        Intrinsics.checkNotNullParameter(bundle, "bundle");
        try {
            SmartLog.i(TAG, "addOnLineCreationRequest");
            if (this.service != null) {
                final MutableSharedFlow MutableSharedFlow = SharedFlowKt.MutableSharedFlow(3000, 3000, BufferOverflow.DROP_LATEST);
                ILLMService iLLMService = this.service;
                if (iLLMService != null) {
                    iLLMService.onLineCreationProcess(type, bundle, new IAICreationCallback.Stub() { // from class: com.xiaomi.aicr.llm.LLMManager.addOnLineCreationRequest.1
                        @Override // com.xiaomi.aicr.llm.IAICreationCallback
                        public void onResult(AICreationResult result) {
                            Intrinsics.checkNotNullParameter(result, "result");
                            MutableSharedFlow.tryEmit(result);
                        }
                    });
                }
                return MutableSharedFlow;
            }
            SmartLog.i(TAG, "service not connected yet!");
            connectLLMService();
            return null;
        } catch (Exception e) {
            SmartLog.e(TAG, "addOnLineCreationRequest exception: " + e);
            return null;
        }
    }

    public final String creationScreenShot() {
        String strCreationScreenShot;
        try {
            SmartLog.i(TAG, "creationScreenShot");
            ILLMService iLLMService = this.service;
            if (iLLMService != null) {
                return (iLLMService == null || (strCreationScreenShot = iLLMService.creationScreenShot()) == null) ? "" : strCreationScreenShot;
            }
            SmartLog.i(TAG, "service not connected yet!");
            connectLLMService();
            return "";
        } catch (Exception e) {
            SmartLog.e(TAG, "creationScreenShot exception: " + e);
        }
    }

    public final String creationGetVersion() {
        String strCreationGetVersion;
        try {
            SmartLog.i(TAG, "creationGetVersion");
            ILLMService iLLMService = this.service;
            if (iLLMService != null) {
                return (iLLMService == null || (strCreationGetVersion = iLLMService.creationGetVersion()) == null) ? "" : strCreationGetVersion;
            }
            SmartLog.i(TAG, "service not connected yet!");
            connectLLMService();
            return "";
        } catch (Exception e) {
            SmartLog.e(TAG, "creationGetVersion exception: " + e);
        }
    }

    /* JADX INFO: compiled from: LLMManager.kt */
    @Metadata(d1 = {"\u00002\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0010\b\n\u0002\b\n\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010$\n\u0002\b\u0005\b\u0086\u0003\u0018\u00002\u00020\u0001B\t\b\u0002¢\u0006\u0004\b\u0002\u0010\u0003J\u0018\u0010\u0013\u001a\u00020\u00142\u0006\u0010\u0015\u001a\u00020\u00162\u0006\u0010\u0017\u001a\u00020\tH\u0007J&\u0010\u0018\u001a\u0010\u0012\u0004\u0012\u00020\u0005\u0012\u0006\u0012\u0004\u0018\u00010\u00050\u00192\u0006\u0010\u0015\u001a\u00020\u00162\u0006\u0010\u0017\u001a\u00020\tH\u0007J\u0018\u0010\u001a\u001a\u00020\u00052\u0006\u0010\u0015\u001a\u00020\u00162\u0006\u0010\u001b\u001a\u00020\u0005H\u0007J\u0018\u0010\u001c\u001a\u00020\u00142\u0006\u0010\u0015\u001a\u00020\u00162\u0006\u0010\u001d\u001a\u00020\u0005H\u0007R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082T¢\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0005X\u0082T¢\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\u0005X\u0082T¢\u0006\u0002\n\u0000R\u000e\u0010\b\u001a\u00020\tX\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\n\u001a\u00020\tX\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\u000b\u001a\u00020\tX\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\f\u001a\u00020\tX\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\r\u001a\u00020\u0005X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\u000e\u001a\u00020\u0005X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\u000f\u001a\u00020\u0005X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\u0010\u001a\u00020\u0005X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\u0011\u001a\u00020\u0005X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\u0012\u001a\u00020\u0005X\u0086T¢\u0006\u0002\n\u0000¨\u0006\u001e"}, d2 = {"Lcom/xiaomi/aicr/llm/LLMManager$Companion;", "", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "()V", "TAG", "", CoreConstants.PACKAGE_NAME_KEY, "LLM_SERVICE_NAME", "LLM_TASK_MESSAGE_SUMMARIZE", "", "LLM_TASK_EDITTEXT_CONTROL", "LLM_TASK_EDITTEXT_COT", "LLM_TASK_CROSS", LLMManager.LLM_BASE_MODEL, LLMManager.LLM_COT_MODEL, "POLISH_STYLE_GENERAL", "POLISH_STYLE_PROFESSIONAL", "POLISH_STYLE_CONCISE", "POLISH_STYLE_FRIENDLY", "isLLMSupport", "", CoreConstants.CONTEXT_SCOPE_VALUE, "Landroid/content/Context;", "type", "getLLMCloudConfig", "", "isDistComputeSupport", "packageName", "isModelManageSupport", "arg", "aicr_AI_sdk_release"}, k = 1, mv = {2, 0, 0}, xi = 48)
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }

        @JvmStatic
        public final boolean isLLMSupport(Context context, int type) {
            Intrinsics.checkNotNullParameter(context, "context");
            try {
                SmartLog.i(LLMManager.TAG, "isLLMSupport: " + type);
                Map<String, String> lLMCloudConfig = getLLMCloudConfig(context, type);
                switch (type) {
                    case 9001:
                    case 9002:
                    case 9003:
                        if (lLMCloudConfig.get(Constants.KEY_SUPPORT) == null || !Intrinsics.areEqual("local", lLMCloudConfig.get(Constants.KEY_SUPPORT))) {
                            SmartLog.i(LLMManager.TAG, "task " + type + " is not support");
                            return false;
                        }
                        if (lLMCloudConfig.get("ram") != null) {
                            ActivityManager.MemoryInfo memoryInfo = new ActivityManager.MemoryInfo();
                            Object systemService = ContextCompat.getSystemService(context, ActivityManager.class);
                            Intrinsics.checkNotNull(systemService);
                            ((ActivityManager) systemService).getMemoryInfo(memoryInfo);
                            long j = memoryInfo.totalMem;
                            String str = lLMCloudConfig.get("ram");
                            Intrinsics.checkNotNull(str);
                            long j2 = 1000;
                            if (j > Long.parseLong(str) * j2 * j2 * j2) {
                                SmartLog.i(LLMManager.TAG, "totalMemory is " + j + ",  ram support");
                            } else {
                                SmartLog.i(LLMManager.TAG, "totalMemory is " + j + ",  ram not support");
                                return false;
                            }
                        }
                        if (lLMCloudConfig.get("MIAI") == null) {
                            return true;
                        }
                        try {
                            PackageInfo packageInfo = context.getPackageManager().getPackageInfo("com.xiaomi.aicr", 0);
                            Intrinsics.checkNotNullExpressionValue(packageInfo, "getPackageInfo(...)");
                            long longVersionCode = packageInfo.getLongVersionCode();
                            SmartLog.i(LLMManager.TAG, "MIAI version: " + longVersionCode);
                            String str2 = lLMCloudConfig.get("MIAI");
                            if (longVersionCode > (str2 != null ? Long.parseLong(str2) : 0L)) {
                                return true;
                            }
                            SmartLog.i(LLMManager.TAG, "MIAI not support");
                            return false;
                        } catch (Exception unused) {
                            SmartLog.i(LLMManager.TAG, "MIAI not found");
                            return false;
                        }
                        return false;
                    default:
                        if (type == 9004) {
                            if (lLMCloudConfig.get(Constants.KEY_SUPPORT) != null && Intrinsics.areEqual("true", lLMCloudConfig.get(Constants.KEY_SUPPORT))) {
                                return true;
                            }
                            SmartLog.i(LLMManager.TAG, "task " + type + " is not support");
                            return false;
                        }
                        return false;
                }
            } catch (Exception e) {
                SmartLog.e(LLMManager.TAG, "isLLMSupport exception: " + e);
            }
        }

        @JvmStatic
        public final Map<String, String> getLLMCloudConfig(Context context, int type) {
            String str;
            List listEmptyList;
            List listEmptyList2;
            String str2;
            List listEmptyList3;
            List listEmptyList4;
            String str3;
            List listEmptyList5;
            List listEmptyList6;
            String str4;
            List listEmptyList7;
            List listEmptyList8;
            int i;
            List listEmptyList9;
            List listEmptyList10;
            Intrinsics.checkNotNullParameter(context, "context");
            SmartLog.i(LLMManager.TAG, "getLLMCloudConfig: " + type);
            String string = Settings.Global.getString(context.getContentResolver(), "MIAI_LLM_Cloud_Control");
            HashMap map = new HashMap();
            int i2 = 2;
            int i3 = 1;
            if (string != null && !Intrinsics.areEqual(string, "")) {
                List<String> listSplit = new Regex("\\|").split(string, 0);
                if (!listSplit.isEmpty()) {
                    ListIterator<String> listIterator = listSplit.listIterator(listSplit.size());
                    while (true) {
                        if (listIterator.hasPrevious()) {
                            if (listIterator.previous().length() != 0) {
                                listEmptyList9 = CollectionsKt.take(listSplit, listIterator.nextIndex() + 1);
                                break;
                            }
                        } else {
                            listEmptyList9 = CollectionsKt.emptyList();
                            break;
                        }
                    }
                } else {
                    listEmptyList9 = CollectionsKt.emptyList();
                    break;
                }
                for (String str5 : (String[]) listEmptyList9.toArray(new String[0])) {
                    List<String> listSplit2 = new Regex(",").split(str5, 0);
                    if (!listSplit2.isEmpty()) {
                        ListIterator<String> listIterator2 = listSplit2.listIterator(listSplit2.size());
                        while (true) {
                            if (listIterator2.hasPrevious()) {
                                if (listIterator2.previous().length() != 0) {
                                    listEmptyList10 = CollectionsKt.take(listSplit2, listIterator2.nextIndex() + 1);
                                    break;
                                }
                            } else {
                                listEmptyList10 = CollectionsKt.emptyList();
                                break;
                            }
                        }
                    } else {
                        listEmptyList10 = CollectionsKt.emptyList();
                        break;
                    }
                    String[] strArr = (String[]) listEmptyList10.toArray(new String[0]);
                    if (strArr.length == 2) {
                        SmartLog.i(LLMManager.TAG, "addLLMCloudConfig: key " + strArr[0] + " value " + strArr[1]);
                        map.put(strArr[0], strArr[1]);
                    }
                }
            }
            HashMap map2 = new HashMap();
            if (type == 9001 && (str4 = (String) map.get("Message_Summarize")) != null) {
                List<String> listSplit3 = new Regex(StringUtils.SPACE).split(str4, 0);
                if (!listSplit3.isEmpty()) {
                    ListIterator<String> listIterator3 = listSplit3.listIterator(listSplit3.size());
                    while (true) {
                        if (listIterator3.hasPrevious()) {
                            if (listIterator3.previous().length() != 0) {
                                listEmptyList7 = CollectionsKt.take(listSplit3, listIterator3.nextIndex() + 1);
                                break;
                            }
                        } else {
                            listEmptyList7 = CollectionsKt.emptyList();
                            break;
                        }
                    }
                } else {
                    listEmptyList7 = CollectionsKt.emptyList();
                    break;
                }
                String[] strArr2 = (String[]) listEmptyList7.toArray(new String[0]);
                int length = strArr2.length;
                int i4 = 0;
                while (i4 < length) {
                    List<String> listSplit4 = new Regex(":").split(strArr2[i4], 0);
                    if (!listSplit4.isEmpty()) {
                        ListIterator<String> listIterator4 = listSplit4.listIterator(listSplit4.size());
                        while (true) {
                            if (listIterator4.hasPrevious()) {
                                if (listIterator4.previous().length() != 0) {
                                    listEmptyList8 = CollectionsKt.take(listSplit4, listIterator4.nextIndex() + i3);
                                    break;
                                }
                            } else {
                                listEmptyList8 = CollectionsKt.emptyList();
                                break;
                            }
                        }
                    } else {
                        listEmptyList8 = CollectionsKt.emptyList();
                        break;
                    }
                    String[] strArr3 = (String[]) listEmptyList8.toArray(new String[0]);
                    if (strArr3.length == i2) {
                        i = i3;
                        SmartLog.i(LLMManager.TAG, "addMessageSummarizeConfig: key " + strArr3[0] + " value " + strArr3[i3]);
                        map2.put(strArr3[0], strArr3[i]);
                    } else {
                        i = i3;
                    }
                    i4++;
                    i2 = 2;
                    i3 = i;
                }
            }
            int i5 = i3;
            if (type == 9002 && (str3 = (String) map.get("Edit_Text_Control")) != null) {
                List<String> listSplit5 = new Regex(StringUtils.SPACE).split(str3, 0);
                if (!listSplit5.isEmpty()) {
                    ListIterator<String> listIterator5 = listSplit5.listIterator(listSplit5.size());
                    while (true) {
                        if (listIterator5.hasPrevious()) {
                            if (listIterator5.previous().length() != 0) {
                                listEmptyList5 = CollectionsKt.take(listSplit5, listIterator5.nextIndex() + 1);
                                break;
                            }
                        } else {
                            listEmptyList5 = CollectionsKt.emptyList();
                            break;
                        }
                    }
                } else {
                    listEmptyList5 = CollectionsKt.emptyList();
                    break;
                }
                for (String str6 : (String[]) listEmptyList5.toArray(new String[0])) {
                    List<String> listSplit6 = new Regex(":").split(str6, 0);
                    if (!listSplit6.isEmpty()) {
                        ListIterator<String> listIterator6 = listSplit6.listIterator(listSplit6.size());
                        while (true) {
                            if (listIterator6.hasPrevious()) {
                                if (listIterator6.previous().length() != 0) {
                                    listEmptyList6 = CollectionsKt.take(listSplit6, listIterator6.nextIndex() + 1);
                                    break;
                                }
                            } else {
                                listEmptyList6 = CollectionsKt.emptyList();
                                break;
                            }
                        }
                    } else {
                        listEmptyList6 = CollectionsKt.emptyList();
                        break;
                    }
                    String[] strArr4 = (String[]) listEmptyList6.toArray(new String[0]);
                    if (strArr4.length == 2) {
                        SmartLog.i(LLMManager.TAG, "addEditTextControlConfig: key " + strArr4[0] + " value " + strArr4[i5]);
                        map2.put(strArr4[0], strArr4[i5]);
                    }
                }
            }
            if (type == 9003 && (str2 = (String) map.get("Edit_Text_Control_COT")) != null) {
                List<String> listSplit7 = new Regex(StringUtils.SPACE).split(str2, 0);
                if (!listSplit7.isEmpty()) {
                    ListIterator<String> listIterator7 = listSplit7.listIterator(listSplit7.size());
                    while (true) {
                        if (listIterator7.hasPrevious()) {
                            if (listIterator7.previous().length() != 0) {
                                listEmptyList3 = CollectionsKt.take(listSplit7, listIterator7.nextIndex() + 1);
                                break;
                            }
                        } else {
                            listEmptyList3 = CollectionsKt.emptyList();
                            break;
                        }
                    }
                } else {
                    listEmptyList3 = CollectionsKt.emptyList();
                    break;
                }
                for (String str7 : (String[]) listEmptyList3.toArray(new String[0])) {
                    List<String> listSplit8 = new Regex(":").split(str7, 0);
                    if (!listSplit8.isEmpty()) {
                        ListIterator<String> listIterator8 = listSplit8.listIterator(listSplit8.size());
                        while (true) {
                            if (listIterator8.hasPrevious()) {
                                if (listIterator8.previous().length() != 0) {
                                    listEmptyList4 = CollectionsKt.take(listSplit8, listIterator8.nextIndex() + 1);
                                    break;
                                }
                            } else {
                                listEmptyList4 = CollectionsKt.emptyList();
                                break;
                            }
                        }
                    } else {
                        listEmptyList4 = CollectionsKt.emptyList();
                        break;
                    }
                    String[] strArr5 = (String[]) listEmptyList4.toArray(new String[0]);
                    if (strArr5.length == 2) {
                        SmartLog.i(LLMManager.TAG, "addEditTextControlConfig: key " + strArr5[0] + " value " + strArr5[i5]);
                        map2.put(strArr5[0], strArr5[i5]);
                    }
                }
            }
            if (type == 9004 && (str = (String) map.get("Cross")) != null) {
                List<String> listSplit9 = new Regex(StringUtils.SPACE).split(str, 0);
                if (!listSplit9.isEmpty()) {
                    ListIterator<String> listIterator9 = listSplit9.listIterator(listSplit9.size());
                    while (true) {
                        if (listIterator9.hasPrevious()) {
                            if (listIterator9.previous().length() != 0) {
                                listEmptyList = CollectionsKt.take(listSplit9, listIterator9.nextIndex() + 1);
                                break;
                            }
                        } else {
                            listEmptyList = CollectionsKt.emptyList();
                            break;
                        }
                    }
                } else {
                    listEmptyList = CollectionsKt.emptyList();
                    break;
                }
                for (String str8 : (String[]) listEmptyList.toArray(new String[0])) {
                    List<String> listSplit10 = new Regex(":").split(str8, 0);
                    if (!listSplit10.isEmpty()) {
                        ListIterator<String> listIterator10 = listSplit10.listIterator(listSplit10.size());
                        while (true) {
                            if (listIterator10.hasPrevious()) {
                                if (listIterator10.previous().length() != 0) {
                                    listEmptyList2 = CollectionsKt.take(listSplit10, listIterator10.nextIndex() + 1);
                                    break;
                                }
                            } else {
                                listEmptyList2 = CollectionsKt.emptyList();
                                break;
                            }
                        }
                    } else {
                        listEmptyList2 = CollectionsKt.emptyList();
                        break;
                    }
                    String[] strArr6 = (String[]) listEmptyList2.toArray(new String[0]);
                    if (strArr6.length == 2) {
                        SmartLog.i(LLMManager.TAG, "addCrossConfig: key " + strArr6[0] + " value " + strArr6[i5]);
                        map2.put(strArr6[0], strArr6[i5]);
                    }
                }
            }
            return map2;
        }

        @JvmStatic
        public final String isDistComputeSupport(Context context, String packageName) {
            Intrinsics.checkNotNullParameter(context, "context");
            Intrinsics.checkNotNullParameter(packageName, "packageName");
            SmartLog.i(LLMManager.TAG, "isDistComputeSupport");
            return "not support";
        }

        @JvmStatic
        public final boolean isModelManageSupport(Context context, String arg) {
            Intrinsics.checkNotNullParameter(context, "context");
            Intrinsics.checkNotNullParameter(arg, "arg");
            SmartLog.i(LLMManager.TAG, "isModelManageSupport");
            return isLLMSupport(context, 9001) || isLLMSupport(context, 9003);
        }
    }
}
