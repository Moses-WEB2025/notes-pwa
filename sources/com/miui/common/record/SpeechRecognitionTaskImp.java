package com.miui.common.record;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import com.miui.common.tool.Logger;
import com.miui.notes.BuildConfig;
import com.miui.notes.NoteApp;
import com.miui.notes.R;
import com.xiaomi.ai.AsrRequest;
import com.xiaomi.ai.SpeechEngine;
import com.xiaomi.ai.SpeechError;
import com.xiaomi.ai.SpeechResult;
import com.xiaomi.ai.mibrain.MibrainException;

/* JADX INFO: loaded from: classes2.dex */
public class SpeechRecognitionTaskImp extends SpeechRecognitionTask {
    private static final int MSG_ADD_BUFFER_TO_ENGINE = 6;
    private static final int MSG_MAX_AMPLITUDE = 5;
    private static final int MSG_MP3_FILE_NAME_RENAME = 4;
    private static final int MSG_UPDATE_ASR_END = 3;
    private static final int MSG_UPDATE_ASR_RESULT = 1;
    private static final int MSG_UPDATE_ERROR = 2;
    private final StringBuilder mBuilder;
    private final Handler mHandler;
    private volatile SpeechEngine mMiAiEngine;
    private String mSpeechStr;

    @Override // com.xiaomi.ai.AsrListener
    public void onBeginningOfSpeech() {
    }

    @Override // com.xiaomi.ai.AsrListener
    public void onBufferReceived(byte[] bArr) {
    }

    @Override // com.xiaomi.ai.AsrListener
    public void onEvent() {
    }

    @Override // com.xiaomi.ai.AsrListener
    public void onFileStored(String str, String str2) {
    }

    @Override // com.xiaomi.ai.AsrListener
    public void onReadyForSpeech() {
    }

    @Override // com.xiaomi.ai.AsrListener
    public void onResults(SpeechResult speechResult) {
    }

    @Override // com.xiaomi.ai.AsrListener
    public void onRmsChanged(float f) {
    }

    public SpeechRecognitionTaskImp(Context context, String str, SpeechRecognitionListener speechRecognitionListener) {
        super(context, str, speechRecognitionListener);
        this.mHandler = new Handler(context.getMainLooper()) { // from class: com.miui.common.record.SpeechRecognitionTaskImp.1
            @Override // android.os.Handler
            public void dispatchMessage(Message message) {
                int i = message.what;
                if (i != 1) {
                    if (i != 3) {
                        if (i == 4) {
                            Logger.INSTANCE.i("SpeechRecognitionTask", "speechRecognition_handler: rename");
                            if ((message.obj instanceof String) && SpeechRecognitionTaskImp.this.mSpeechRecognitionListener.get() != null) {
                                SpeechRecognitionTaskImp.this.mSpeechRecognitionListener.get().onAsrSaveAudioFileName((String) message.obj);
                            }
                        } else if (i != 5) {
                            if (i == 6) {
                                if (SpeechRecognitionTaskImp.this.mMiAiEngine != null) {
                                    if (message.obj instanceof byte[]) {
                                        try {
                                            SpeechRecognitionTaskImp.this.mMiAiEngine.addBuffer((byte[]) message.obj, 0, ((byte[]) message.obj).length);
                                        } catch (IllegalStateException e) {
                                            Logger.INSTANCE.e("SpeechRecognitionTask", e.getMessage());
                                        }
                                    }
                                } else {
                                    synchronized (SpeechRecognitionTaskImp.this.mConsumerLock) {
                                        SpeechRecognitionTask.isNotify.set(true);
                                        SpeechRecognitionTaskImp.this.mConsumerLock.notifyAll();
                                    }
                                }
                            }
                        } else if ((message.obj instanceof Short) && SpeechRecognitionTaskImp.this.mSpeechRecognitionListener.get() != null) {
                            SpeechRecognitionTaskImp.this.mSpeechRecognitionListener.get().onAsrMaxAmplitudeUpdate(((Short) message.obj).shortValue());
                        }
                    } else if (SpeechRecognitionTaskImp.this.mMiAiEngine != null) {
                        SpeechRecognitionTaskImp.this.mMiAiEngine.endSpeech();
                        if ((message.obj instanceof String) && SpeechRecognitionTaskImp.this.mSpeechRecognitionListener.get() != null) {
                            SpeechRecognitionTaskImp.this.mSpeechRecognitionListener.get().onAsrAppendTextResult((String) message.obj, false);
                        }
                    }
                } else if ((message.obj instanceof String) && SpeechRecognitionTaskImp.this.mSpeechRecognitionListener.get() != null) {
                    SpeechRecognitionTaskImp.this.mSpeechRecognitionListener.get().onAsrAppendTextResult((String) message.obj, true);
                }
                super.dispatchMessage(message);
            }
        };
        this.mBuilder = new StringBuilder();
    }

    @Override // com.miui.common.record.SpeechRecognitionTask
    public void start() throws MibrainException {
        if (!isSkipSpeechRecognition()) {
            if (this.mNetWorkValid && this.mMiAiEngine == null) {
                this.mMiAiEngine = SpeechEngine.createEngine(NoteApp.getInstance(), 1, getClient(), getPassport2(), getPassport(), 1);
                this.mMiAiEngine.setAsrLisnter(this);
                this.mMiAiEngine.setEnv(0);
            }
            onRequestStart();
        }
        super.start();
    }

    @Override // com.miui.common.record.SpeechRecognitionTask
    public void onRequestStart() {
        if (this.mMiAiEngine == null) {
            return;
        }
        AsrRequest asrRequest = new AsrRequest();
        asrRequest.setDataInputMode(AsrRequest.DataInputMode.DATA_INPUT_MODE_BUFFER);
        try {
            this.mMiAiEngine.listenSpeech(asrRequest);
        } catch (Exception e) {
            Logger.INSTANCE.e("SpeechRecognitionTask", e.toString());
        }
    }

    @Override // com.miui.common.record.SpeechRecognitionTask
    protected void onBufferSend(byte[] bArr) {
        if (bArr == null || bArr.length <= 0) {
            return;
        }
        this.mHandler.obtainMessage(6, bArr).sendToTarget();
    }

    @Override // com.miui.common.record.SpeechRecognitionTask
    protected void onSpeechEnd(boolean z) {
        if (this.mSpeechStr != null) {
            if (z) {
                this.mSpeechStr += "，";
            } else {
                this.mSpeechStr += "。";
            }
            this.mBuilder.append(this.mSpeechStr);
            this.mHandler.obtainMessage(3, this.mSpeechStr).sendToTarget();
            this.mSpeechStr = null;
        }
    }

    @Override // com.miui.common.record.SpeechRecognitionTask
    protected void onRenameMp3File(String str) {
        Logger.INSTANCE.i("SpeechRecognitionTask", "onRenameMp3File");
        this.mHandler.obtainMessage(4, str).sendToTarget();
    }

    @Override // com.miui.common.record.SpeechRecognitionTask
    protected void updateMaxAmplitude(short[] sArr, int i) {
        short s = 0;
        for (int i2 = 0; i2 < i; i2++) {
            short s2 = sArr[i2];
            if (s2 > s) {
                s = s2;
            }
        }
        this.mHandler.obtainMessage(5, Short.valueOf(s)).sendToTarget();
    }

    @Override // com.miui.common.record.SpeechRecognitionTask
    public void stopEngine() {
        if (this.mNetWorkValid || this.mMiAiEngine == null) {
            return;
        }
        this.mMiAiEngine.forceStop();
        this.mMiAiEngine.release();
        this.mMiAiEngine = null;
    }

    @Override // com.miui.common.record.SpeechRecognitionTask
    public void startEngine() {
        if (this.mNetWorkValid && this.mMiAiEngine == null) {
            this.mMiAiEngine = SpeechEngine.createEngine(NoteApp.getInstance(), 1, getClient(), getPassport2(), getPassport(), 1);
            this.mMiAiEngine.setAsrLisnter(this);
            this.mMiAiEngine.setEnv(0);
            AsrRequest asrRequest = new AsrRequest();
            asrRequest.setDataInputMode(AsrRequest.DataInputMode.DATA_INPUT_MODE_BUFFER);
            this.mMiAiEngine.listenSpeech(asrRequest);
        }
    }

    @Override // com.miui.common.record.SpeechRecognitionTask
    public void shutdownNow() {
        super.shutdownNow();
        if (this.mMiAiEngine == null) {
            return;
        }
        this.mHandler.removeMessages(6);
        this.mMiAiEngine.forceStop();
    }

    @Override // com.miui.common.record.SpeechRecognitionTask
    public void destroy() {
        super.destroy();
        if (this.mMiAiEngine == null) {
            return;
        }
        this.mHandler.removeMessages(6);
        this.mHandler.removeCallbacksAndMessages(null);
        this.mMiAiEngine.release();
        this.mMiAiEngine = null;
    }

    @Override // com.xiaomi.ai.AsrListener
    public void onEndOfSpeech() {
        synchronized (this.mConsumerLock) {
            isNotify.set(true);
            this.mConsumerLock.notifyAll();
        }
    }

    @Override // com.xiaomi.ai.AsrListener
    public void onError(SpeechError speechError) {
        Logger.INSTANCE.d("SpeechRecognitionTask", "speechError:" + speechError.getErrMsg());
        synchronized (this.mConsumerLock) {
            isNotify.set(true);
            this.mConsumerLock.notifyAll();
        }
    }

    @Override // com.xiaomi.ai.AsrListener
    public void onPartialResults(SpeechResult speechResult) {
        String query = speechResult.getQuery();
        if (query != null) {
            this.mSpeechStr = speechResult.getQuery();
            if (query.length() > 0) {
                this.mHandler.obtainMessage(1, this.mSpeechStr).sendToTarget();
            }
        }
    }

    private String getClient() {
        return this.mContext.getString(R.string.asp_client1) + BuildConfig.ASP_CLIENT2;
    }

    private String getPassport() {
        return this.mContext.getString(R.string.asp_release1) + BuildConfig.ASP_PART2;
    }

    private String getPassport2() {
        return this.mContext.getString(R.string.asp_sign1) + BuildConfig.ASP_SIGN2;
    }
}
