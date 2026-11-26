package com.xiaomi.xms.ai.recorder;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.RemoteException;
import android.util.Log;
import com.android.soundrecorder.ai.base.IAIRecordService;
import com.android.soundrecorder.ai.base.IAIRecorderServiceCallback;
import com.android.soundrecorder.ai.base.aop.InterceptorAspect;
import com.android.soundrecorder.ai.base.config.RecordConfig;
import com.android.soundrecorder.ai.base.utils.Utils;
import com.xiaomi.xms.ai.recorder.callback.AIRecognizeCallback;
import com.xiaomi.xms.ai.recorder.callback.AIRecognizeCallbackStubImpl;
import com.xiaomi.xms.ai.recorder.callback.AIRecorderServiceStateCallback;
import com.xiaomi.xms.ai.recorder.callback.BindServiceCallback;
import com.xiaomi.xms.ai.recorder.callback.ReconnectCallback;
import com.xiaomi.xms.ai.recorder.interceptor.NotificationInterceptor;
import com.xiaomi.xms.ai.recorder.interceptor.PermissionInterceptor;
import com.xiaomi.xms.ai.recorder.util.DatabaseHelper;
import java.io.FileDescriptor;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

/* JADX INFO: loaded from: classes4.dex */
public enum AIRecorderManager {
    instance;

    private RecordConfig callerConfig;
    private Context mContext;
    private AIRecognizeCallbackStubImpl mRecognizeCallback;
    private IAIRecorderServiceCallback serviceCallback;
    private final List<ReconnectCallback<IAIRecordService>> mReconnectCallbacks = new ArrayList();
    private int callSource = -1;
    private String currentServiceCallbackPackageName = "";
    private Boolean isSupportAICapability = null;
    private final Object mLock = new Object();

    AIRecorderManager() {
    }

    public boolean isSupportAICapability(Context context) {
        boolean zBooleanValue;
        try {
            synchronized (this.mLock) {
                if (this.isSupportAICapability == null) {
                    Bundle bundleCall = context.getContentResolver().call(Constants.AUTHORITY_AI_RECORD_PROVIDER, Constants.KEY_AI_CAPABILITY, (String) null, (Bundle) null);
                    this.isSupportAICapability = Boolean.valueOf(bundleCall != null && bundleCall.getBoolean(Constants.KEY_AI_CAPABILITY));
                    logd("isSupportAICapability:" + this.isSupportAICapability);
                }
                zBooleanValue = this.isSupportAICapability.booleanValue();
            }
            return zBooleanValue;
        } catch (Exception e) {
            loge("isSupportAICapability: ", e);
            return false;
        }
    }

    public int getAIRecorderPreState(Context context) {
        logd("getAIRecorderPreState");
        if (!Utils.isGlobal()) {
            try {
                boolean z = context.getContentResolver().call(Constants.AUTHORITY_CTA, Constants.KEY_CTA_PERMISSION_STATE, (String) null, (Bundle) null).getBoolean(Constants.IS_ACCEPT);
                Log.i(Constants.TAG, "cta accept: " + z);
                if (!z) {
                    return 2;
                }
            } catch (Exception e) {
                loge("isSupportAICapability: ", e);
            }
        } else if (Utils.isKoreaRegion()) {
            try {
                boolean z2 = context.getContentResolver().call(Constants.AUTHORITY_CTA, Constants.KEY_KOREA_PERMISSION_STATE, (String) null, (Bundle) null).getBoolean(Constants.IS_ACCEPT);
                Log.i(Constants.TAG, "korea permission accept: " + z2);
                if (!z2) {
                    return 2;
                }
            } catch (Exception e2) {
                loge("isSupportAICapability: ", e2);
            }
        }
        try {
            Bundle bundleCall = context.getContentResolver().call(Constants.AUTHORITY_AI_RECORD_PROVIDER, Constants.KEY_AI_RECORDER_PRESTATE, (String) null, (Bundle) null);
            if (bundleCall != null) {
                return bundleCall.getInt(Constants.KEY_AI_RECORDER_PRESTATE, 4);
            }
        } catch (Exception e3) {
            loge("isSupportAICapability: ", e3);
        }
        return 4;
    }

    public void requestPermission(Activity activity) {
        logd("requestPermission");
        activity.startActivity(getRequestCTAIntent());
    }

    public static Intent getRequestCTAIntent() {
        Intent intent = new Intent("com.android.soundrecorder.action.REQUEST_CTA");
        intent.setPackage(Constants.RECORDER_PACKAGE_NAME);
        return intent;
    }

    public boolean isBindService() {
        logd("isBindService");
        return getService() != null;
    }

    public void init(Context context) {
        logd("init,sdk version:0.1.7");
        this.mContext = context;
    }

    public void setRecordConfig(RecordConfig recordConfig) {
        logd("setRecordConfig");
        this.callerConfig = recordConfig;
        this.callSource = recordConfig.getCallerSource();
    }

    @InterceptorAspect(interceptors = {PermissionInterceptor.class, NotificationInterceptor.class})
    public void startRecording() {
        logd("startRecording");
        if (getService() == null) {
            AIRecorderServiceConnection.bindService(new BindServiceCallback() { // from class: com.xiaomi.xms.ai.recorder.AIRecorderManager$$ExternalSyntheticLambda1
                @Override // com.xiaomi.xms.ai.recorder.callback.BindServiceCallback
                public final void onServiceConnected() {
                    this.f$0.realStartRecording();
                }
            });
        } else {
            realStartRecording();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void realStartRecording() {
        logd("realStartRecording");
        try {
            if (this.callerConfig != null) {
                getService().setRecordConfig(this.callerConfig);
            }
            IAIRecorderServiceCallback iAIRecorderServiceCallback = this.serviceCallback;
            if (iAIRecorderServiceCallback != null) {
                registerRecorderCallback(this.currentServiceCallbackPackageName, iAIRecorderServiceCallback);
            }
            int iStartRecording = getService().startRecording();
            if (iStartRecording != 1) {
                loge("realStartRecording", new IllegalArgumentException("startRecording fail,code is:" + iStartRecording));
                AIRecorderServiceConnection.stopService();
            }
        } catch (Exception e) {
            AIRecorderServiceConnection.stopService();
            loge("realStartRecording", e);
        }
    }

    public void stopRecording() {
        logd("stopRecording");
        try {
            if (getService() != null) {
                getService().stopRecording();
            }
        } catch (Exception e) {
            loge("stopRecording", e);
        }
    }

    public void pauseRecording() {
        logd("pauseRecording");
        try {
            if (getService() != null) {
                getService().pauseRecording();
            }
        } catch (Exception e) {
            loge("pauseRecording", e);
        }
    }

    public void resumeRecording() {
        logd("resumeRecording");
        try {
            if (getService() != null) {
                getService().resumeRecording();
            }
        } catch (Exception e) {
            loge("resumeRecording", e);
        }
    }

    public void registerRecorderCallback(IAIRecorderServiceCallback iAIRecorderServiceCallback) {
        registerRecorderCallback(this.mContext.getPackageName(), iAIRecorderServiceCallback);
    }

    public void registerRecorderCallback(String str, IAIRecorderServiceCallback iAIRecorderServiceCallback) {
        logd("registerRecorderCallback");
        this.serviceCallback = iAIRecorderServiceCallback;
        this.currentServiceCallbackPackageName = str;
        if (str == null) {
            loge("registerRecorderCallback", new IllegalArgumentException("registerRecorderCallback for null PackageName"));
            return;
        }
        if (getService() != null) {
            try {
                getService().registerRecorderCallback(this.currentServiceCallbackPackageName, this.serviceCallback);
                this.serviceCallback = null;
            } catch (RemoteException e) {
                loge("registerRecorderCallback", e);
            }
        }
    }

    public void unregisterRecorderCallback(IAIRecorderServiceCallback iAIRecorderServiceCallback) {
        unregisterRecorderCallback(this.mContext.getPackageName(), iAIRecorderServiceCallback);
    }

    public void unregisterRecorderCallback(String str, IAIRecorderServiceCallback iAIRecorderServiceCallback) {
        logd("unregisterRecorderCallbackByPackageName");
        if (str == null) {
            loge("unregisterRecorderCallback", new IllegalArgumentException("unregisterRecorderCallback for null PackageName"));
            return;
        }
        try {
            if (getService() != null) {
                getService().unregisterRecorderCallback(str, iAIRecorderServiceCallback);
            }
        } catch (Exception e) {
            loge("unregisterRecorderCallback", e);
        }
    }

    public void registerAIRecorderServiceStateCallback(AIRecorderServiceStateCallback aIRecorderServiceStateCallback) {
        logd("registerAIRecorderServiceStateCallback");
        AIRecorderServiceConnection.stateCallbacks.add(aIRecorderServiceStateCallback);
    }

    public void unregisterAIRecorderServiceStateCallback(AIRecorderServiceStateCallback aIRecorderServiceStateCallback) {
        logd("unregisterAIRecorderServiceStateCallback");
        AIRecorderServiceConnection.stateCallbacks.remove(aIRecorderServiceStateCallback);
    }

    public boolean isAIAbilityEnable() {
        logd("isAIAbilityEnable");
        if (getService() == null) {
            return false;
        }
        try {
            return getService().isAIAbilityEnable();
        } catch (Exception e) {
            loge("isAIAbilityEnable", e);
            return false;
        }
    }

    public boolean isAIRecognizeAgreeCTA() {
        logd("isAIRecognizeAgreeCTA");
        if (getService() == null) {
            return false;
        }
        try {
            return getService().isAIRecognizeAgreeCTA();
        } catch (Exception e) {
            loge("isAIRecognizeAgreeCTA", e);
            return false;
        }
    }

    public void initAIRecognizeEngine(AIRecognizeCallback aIRecognizeCallback) {
        logd("initAIRecognizeEngine");
        try {
            AIRecognizeCallbackStubImpl aIRecognizeCallbackStubImpl = this.mRecognizeCallback;
            if (aIRecognizeCallbackStubImpl != null) {
                aIRecognizeCallbackStubImpl.resetCallback(true);
                this.mRecognizeCallback = null;
            }
            this.mRecognizeCallback = new AIRecognizeCallbackStubImpl(aIRecognizeCallback);
            if (getService() != null) {
                getService().initAIRecognizeEngine(this.mRecognizeCallback);
            }
        } catch (Exception e) {
            loge("initAIRecognizeEngine", e);
        }
    }

    public void startRecognize(String str) {
        logd("startRecognize");
        try {
            if (this.mRecognizeCallback != null && getService() != null) {
                getService().startAIRecognize(str);
                return;
            }
            logW("startRecognize ,please init engine or bindService");
        } catch (Exception e) {
            loge("startRecognize", e);
        }
    }

    public String getRecognizeLanguage() {
        logd("getRecognizeLanguage");
        try {
            if (getService() != null) {
                return getService().getRecognizeLanguage();
            }
            return "";
        } catch (Exception e) {
            loge("getRecognizeLanguage", e);
            return "";
        }
    }

    public void stopRecognize() {
        logd("stopRecognize");
        try {
            if (this.mRecognizeCallback != null && getService() != null) {
                getService().stopAIRecognize();
                return;
            }
            logW("stopRecognize ,please init engine or bindService");
        } catch (Exception e) {
            loge("stopRecognize", e);
        }
    }

    public String querySummaryOrRecognizeData(String str, Context context) {
        logd("querySummaryOrRecognizeData");
        return DatabaseHelper.querySummaryOrRecognizeData(str, context);
    }

    public String querySummary(String str, Context context) {
        logd("querySummary sha1: " + str);
        return DatabaseHelper.querySummary(str, context);
    }

    public String queryRecognizeData(String str, Context context) {
        logd("queryRecognizeData sha1: " + str);
        return DatabaseHelper.queryRecognizeData(str, context);
    }

    public String generateFileSha1(FileDescriptor fileDescriptor) {
        return Utils.generateFileSha1(fileDescriptor);
    }

    public void releaseAIRecognizeEngine() {
        logd("releaseAIRecognizeEngine");
        AIRecognizeCallbackStubImpl aIRecognizeCallbackStubImpl = this.mRecognizeCallback;
        if (aIRecognizeCallbackStubImpl != null) {
            try {
                aIRecognizeCallbackStubImpl.resetCallback(false);
                if (getService() != null) {
                    getService().releaseAIRecognizeEngine();
                }
                this.mRecognizeCallback = null;
            } catch (Exception e) {
                loge("releaseAIRecognizeEngine", e);
            }
        }
    }

    public int getState() {
        if (getService() == null) {
            return 0;
        }
        try {
            return getService().getState();
        } catch (Exception e) {
            loge("getState", e);
            return -1;
        }
    }

    public int getMaxAmplitude() {
        logd("getMaxAmplitude");
        if (getService() == null) {
            return -1;
        }
        try {
            return getService().getMaxAmplitude();
        } catch (Exception e) {
            loge("getMaxAmplitude", e);
            return -1;
        }
    }

    public int getCurrentAmplitude() {
        logd("getCurrentAmplitude");
        if (getService() == null) {
            return -1;
        }
        try {
            return getService().getCurrentAmplitude();
        } catch (Exception e) {
            loge("getCurrentAmplitude", e);
            return -1;
        }
    }

    public long getCurrentRecordDuration() {
        if (getService() == null) {
            logW("getCurrentRecordDuration service is null, so duration is 0");
            return 0L;
        }
        try {
            return getService().getCurrentRecordDuration();
        } catch (Exception e) {
            loge("getCurrentRecordDuration", e);
            return 0L;
        }
    }

    public String getCurrentRecordFilePath() {
        logd("getCurrentRecordFilePath");
        if (getService() == null) {
            return null;
        }
        try {
            return getService().getCurrentRecordFilePath();
        } catch (Exception e) {
            loge("getCurrentRecordFilePath", e);
            return null;
        }
    }

    public String getCurrentServiceCallerPackageName() {
        logd("getCurrentServiceCallerPackageName");
        if (getService() == null) {
            return null;
        }
        try {
            String currentCallerPackageName = getService().getCurrentCallerPackageName();
            logd("getCurrentServiceCallerPackageName is: " + currentCallerPackageName);
            return currentCallerPackageName;
        } catch (Exception e) {
            loge("getCurrentRecordFilePath", e);
            return null;
        }
    }

    public String getCurrentFileSha1() {
        logd("getCurrentFileSha1");
        if (getService() == null) {
            return null;
        }
        try {
            return getService().getCurrentFileSha1();
        } catch (Exception e) {
            loge("getCurrentFileSha1", e);
            return null;
        }
    }

    public boolean isRecording() {
        try {
            return getState() == 20;
        } catch (Exception e) {
            loge("isRecording", e);
            return false;
        }
    }

    public boolean isPausing() {
        try {
            return getState() == 30;
        } catch (Exception e) {
            loge("isPausing", e);
            return false;
        }
    }

    public boolean isComplete() {
        try {
            return getState() == 50;
        } catch (Exception e) {
            loge("isComplete", e);
            return false;
        }
    }

    public void checkOrReconnectService(Context context, ReconnectCallback<IAIRecordService> reconnectCallback) {
        logd("checkOrReconnectService");
        if (isBindService()) {
            reconnectCallback.onReconnect(getService());
            return;
        }
        if (this.mContext == null) {
            this.mContext = context.getApplicationContext();
        }
        if (this.mReconnectCallbacks.isEmpty()) {
            logd("checkOrReconnectService callback: " + reconnectCallback + ", list: " + Arrays.toString(this.mReconnectCallbacks.toArray()));
            AIRecorderServiceConnection.bindService(new BindServiceCallback() { // from class: com.xiaomi.xms.ai.recorder.AIRecorderManager$$ExternalSyntheticLambda0
                @Override // com.xiaomi.xms.ai.recorder.callback.BindServiceCallback
                public final void onServiceConnected() {
                    this.f$0.m2519x7a0b14f4();
                }
            });
        }
        this.mReconnectCallbacks.add(reconnectCallback);
    }

    /* JADX INFO: renamed from: lambda$checkOrReconnectService$0$com-xiaomi-xms-ai-recorder-AIRecorderManager, reason: not valid java name */
    /* synthetic */ void m2519x7a0b14f4() {
        Iterator<ReconnectCallback<IAIRecordService>> it = this.mReconnectCallbacks.iterator();
        while (it.hasNext()) {
            it.next().onReconnect(getService());
        }
        this.mReconnectCallbacks.clear();
    }

    public void reset() {
        logd("reset");
        try {
            if (getService() != null) {
                getService().reset();
            }
        } catch (Exception e) {
            loge("reset", e);
        }
    }

    public void detach() {
        logd("detach");
        AIRecorderServiceConnection.unbindService();
        this.mReconnectCallbacks.clear();
    }

    public Context getContext() {
        return this.mContext;
    }

    public int getCallSource() {
        return this.callSource;
    }

    private IAIRecordService getService() {
        IAIRecordService service = AIRecorderServiceConnection.getService();
        if (service == null) {
            Log.e(Constants.TAG, "caller: " + this.callSource + " ,IAIRecordService is null,pls connect service first");
        }
        return service;
    }

    private void loge(String str, Exception exc) {
        exc.fillInStackTrace();
        Log.e(Constants.TAG, "caller: " + this.callSource + " , " + str + ": ", exc);
    }

    private void logd(String str) {
        Log.d(Constants.TAG, "caller: " + this.callSource + " , " + str + ": ");
    }

    private void logW(String str) {
        Log.w(Constants.TAG, "caller: " + this.callSource + " , " + str);
    }
}
