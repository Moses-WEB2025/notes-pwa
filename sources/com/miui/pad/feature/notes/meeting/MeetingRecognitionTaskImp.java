package com.miui.pad.feature.notes.meeting;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import ch.qos.logback.core.CoreConstants;
import com.market.sdk.reflect.ReflectUtilsForMiui;
import com.miui.notes.base.utils.Logger;
import com.xiaomi.onetrack.api.ah;
import java.text.SimpleDateFormat;
import java.util.Locale;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import okhttp3.Response;
import org.apache.commons.lang3.StringUtils;

/* JADX INFO: compiled from: MeetingRecognitionTaskImp.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000\\\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\t\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0005\n\u0002\u0010\u0012\n\u0002\b\u0005\n\u0002\u0010\u0017\n\u0000\n\u0002\u0010\b\n\u0002\b\u0015\n\u0002\u0018\u0002\n\u0002\b\u0003\u0018\u0000 92\u00020\u0001:\u00019B#\u0012\b\u0010\u0002\u001a\u0004\u0018\u00010\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\b\u0010\u0006\u001a\u0004\u0018\u00010\u0007¢\u0006\u0004\b\b\u0010\tJ \u0010\u0010\u001a\u00020\u00112\u0006\u0010\u0012\u001a\u00020\u00132\u0006\u0010\u0014\u001a\u00020\u00132\u0006\u0010\u0015\u001a\u00020\u0013H\u0016J\b\u0010\u0016\u001a\u00020\u0011H\u0016J\u0012\u0010\u0017\u001a\u00020\u00112\b\u0010\u0018\u001a\u0004\u0018\u00010\u0019H\u0014J\u0012\u0010\u001a\u001a\u00020\u00112\b\u0010\u001b\u001a\u0004\u0018\u00010\u0005H\u0014J\u001a\u0010\u001d\u001a\u00020\u00112\b\u0010\u001e\u001a\u0004\u0018\u00010\u001f2\u0006\u0010 \u001a\u00020!H\u0014J\b\u0010\"\u001a\u00020\u0011H\u0016J\b\u0010#\u001a\u00020\u0011H\u0016J \u0010$\u001a\u00020\u00112\u0006\u0010\u0012\u001a\u00020\u00132\u0006\u0010%\u001a\u00020\u00132\u0006\u0010\u0015\u001a\u00020\u0013H\u0016J\b\u0010&\u001a\u00020\u0011H\u0016J\b\u0010'\u001a\u00020\u0011H\u0016J\b\u0010(\u001a\u00020\u0011H\u0016J\u0012\u0010.\u001a\u00020\u00112\b\u0010/\u001a\u0004\u0018\u00010\u0005H\u0016J\u0012\u00100\u001a\u00020\u00112\b\u0010/\u001a\u0004\u0018\u00010\u0005H\u0016J\u0012\u00101\u001a\u00020\u00112\b\u0010/\u001a\u0004\u0018\u00010\u0005H\u0016J\u0012\u00102\u001a\u00020\u00112\b\u0010/\u001a\u0004\u0018\u00010\u0005H\u0016J\u0010\u00103\u001a\u00020\u00112\u0006\u00104\u001a\u00020\u0005H\u0016J\u0012\u00105\u001a\u00020\u00112\b\u00106\u001a\u0004\u0018\u000107H\u0016R\u0010\u0010\n\u001a\u0004\u0018\u00010\u000bX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\f\u001a\u00020\rX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u000e\u001a\u00020\u000fX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u001c\u001a\u00020\u000fX\u0082\u000e¢\u0006\u0002\n\u0000R\u001a\u0010)\u001a\u00020\u000fX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b*\u0010+\"\u0004\b,\u0010-R\u000e\u00108\u001a\u00020\u0013X\u0082\u000e¢\u0006\u0002\n\u0000¨\u0006:"}, d2 = {"Lcom/miui/pad/feature/notes/meeting/MeetingRecognitionTaskImp;", "Lcom/miui/pad/feature/notes/meeting/MeetingRecognitionTask;", CoreConstants.CONTEXT_SCOPE_VALUE, "Landroid/content/Context;", "mp3DirName", "", "meetingRecognitionListener", "Lcom/miui/pad/feature/notes/meeting/MeetingRecognitionListener;", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "(Landroid/content/Context;Ljava/lang/String;Lcom/miui/pad/feature/notes/meeting/MeetingRecognitionListener;)V", "mWebSocketThread", "Lcom/miui/pad/feature/notes/meeting/WebSocketThread;", "mHandler", "Landroid/os/Handler;", "mTaskStartTime", "", "start", "", "isSingle", "", "isTrans", "isFold", "onRequestStart", "onBufferSend", "buffer", "", "onRenameMp3File", "mp3FileName", "lastUpdateAmplitude", "updateMaxAmplitude", "pcmShortBuffer", "", "size", "", "stopEngine", "startEngine", "updateAsrInfo", "needTranslate", "addEnterFlag", "shutdownNow", "onSocketOpen", "lastPartUpdateTimeStamp", "getLastPartUpdateTimeStamp", "()J", "setLastPartUpdateTimeStamp", "(J)V", "onMessagePart", "message", "onMessageFull", "onMessageEnd", "onClosing", "onClosed", "reason", "onFailure", ah.H, "Lokhttp3/Response;", "haveEnter", "Companion", "app_PhoneGlobalRelease"}, k = 1, mv = {2, 2, 0}, xi = 48)
public final class MeetingRecognitionTaskImp extends MeetingRecognitionTask {
    private static final int MSG_ADD_BUFFER_TO_ENGINE = 6;
    private static final int MSG_ADD_FLAG_OFFLINE = 17;
    private static final int MSG_MAX_AMPLITUDE = 5;
    private static final int MSG_MP3_FILE_NAME_RENAME = 4;
    private static final int MSG_SOCKET_CLOSED = 7;
    private static final int MSG_SOCKET_FAILURE = 8;
    private static final int MSG_UPDATE_ASR_END = 3;
    private static final int MSG_UPDATE_ASR_ENTER = 16;
    private static final int MSG_UPDATE_ASR_FULL = 9;
    private static final int MSG_UPDATE_ASR_RESULT = 1;
    private static final int MSG_UPDATE_ERROR = 2;
    private boolean haveEnter;
    private long lastPartUpdateTimeStamp;
    private long lastUpdateAmplitude;
    private final Handler mHandler;
    private long mTaskStartTime;
    private volatile WebSocketThread mWebSocketThread;

    @Override // com.miui.pad.feature.notes.meeting.WebSocketThread.OnMessageReceiveListener
    public void onSocketOpen() {
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public MeetingRecognitionTaskImp(Context context, String mp3DirName, MeetingRecognitionListener meetingRecognitionListener) {
        super(context, mp3DirName, meetingRecognitionListener);
        Intrinsics.checkNotNullParameter(mp3DirName, "mp3DirName");
        Intrinsics.checkNotNull(context);
        Intrinsics.checkNotNull(meetingRecognitionListener);
        this.mHandler = new Handler(Looper.getMainLooper()) { // from class: com.miui.pad.feature.notes.meeting.MeetingRecognitionTaskImp.1
            @Override // android.os.Handler
            public void dispatchMessage(Message msg) {
                Intrinsics.checkNotNullParameter(msg, "msg");
                Logger.INSTANCE.i(MeetingRecognitionTaskImp.this.getTAG(), "dispatchMessage: " + msg.what);
                int i = msg.what;
                if (i != 1) {
                    if (i != 3) {
                        if (i != 4) {
                            if (i != 5) {
                                if (i == 7) {
                                    MeetingRecognitionTaskImp.this.getMMeetingRecognitionListener().onSocketClosed();
                                } else if (i == 8) {
                                    MeetingRecognitionTaskImp.this.getMMeetingRecognitionListener().onSocketFailure();
                                } else if (i != 9) {
                                    if (i == 16) {
                                        if (MeetingRecognitionTaskImp.this.mWebSocketThread != null && !MeetingRecognitionTaskImp.this.haveEnter) {
                                            WebSocketThread webSocketThread = MeetingRecognitionTaskImp.this.mWebSocketThread;
                                            Intrinsics.checkNotNull(webSocketThread);
                                            if (!webSocketThread.getMNeedTranslate()) {
                                                WebSocketThread webSocketThread2 = MeetingRecognitionTaskImp.this.mWebSocketThread;
                                                if (webSocketThread2 != null) {
                                                    webSocketThread2.addEnterFlag(StringUtils.LF);
                                                }
                                                MeetingRecognitionTaskImp.this.getMMeetingRecognitionListener().onMeetingTextResult(StringUtils.LF, false);
                                                MeetingRecognitionTaskImp.this.haveEnter = true;
                                            }
                                        }
                                    } else if (i == 17 && (msg.obj instanceof String)) {
                                        removeMessages(17);
                                        if (msg.obj instanceof String) {
                                            if (MeetingRecognitionTaskImp.this.getIsDebug()) {
                                                Logger logger = Logger.INSTANCE;
                                                String tag = MeetingRecognitionTaskImp.this.getTAG();
                                                Object obj = msg.obj;
                                                Intrinsics.checkNotNull(obj, "null cannot be cast to non-null type kotlin.String");
                                                logger.e(tag, "MSG_ADD_FLAG_OFFLINE: " + ((String) obj));
                                            }
                                            MeetingRecognitionListener mMeetingRecognitionListener = MeetingRecognitionTaskImp.this.getMMeetingRecognitionListener();
                                            Object obj2 = msg.obj;
                                            Intrinsics.checkNotNull(obj2, "null cannot be cast to non-null type kotlin.String");
                                            mMeetingRecognitionListener.onMeetingTextResult((String) obj2, false);
                                        }
                                    }
                                } else if (MeetingRecognitionTaskImp.this.mWebSocketThread != null) {
                                    Logger.INSTANCE.e(MeetingRecognitionTaskImp.this.getTAG(), "dispatchMessage: receive " + msg.what);
                                    removeMessages(16);
                                    MeetingRecognitionTaskImp.this.haveEnter = false;
                                    if (msg.obj instanceof String) {
                                        MeetingRecognitionTaskImp.this.setLastPartUpdateTimeStamp(System.currentTimeMillis());
                                        if (MeetingRecognitionTaskImp.this.getIsDebug()) {
                                            Logger logger2 = Logger.INSTANCE;
                                            String tag2 = MeetingRecognitionTaskImp.this.getTAG();
                                            Object obj3 = msg.obj;
                                            Intrinsics.checkNotNull(obj3, "null cannot be cast to non-null type kotlin.String");
                                            logger2.e(tag2, "MSG_UPDATE_ASR_FULL: " + ((String) obj3));
                                        }
                                        MeetingRecognitionListener mMeetingRecognitionListener2 = MeetingRecognitionTaskImp.this.getMMeetingRecognitionListener();
                                        Object obj4 = msg.obj;
                                        Intrinsics.checkNotNull(obj4, "null cannot be cast to non-null type kotlin.String");
                                        mMeetingRecognitionListener2.onMeetingTextFull((String) obj4);
                                    }
                                    sendMessageDelayed(obtainMessage(16), 2000L);
                                }
                            } else if (msg.obj instanceof Short) {
                                MeetingRecognitionListener mMeetingRecognitionListener3 = MeetingRecognitionTaskImp.this.getMMeetingRecognitionListener();
                                Object obj5 = msg.obj;
                                Intrinsics.checkNotNull(obj5, "null cannot be cast to non-null type kotlin.Short");
                                mMeetingRecognitionListener3.onMeetingMaxAmplitudeUpdate(((Short) obj5).shortValue());
                            }
                        } else if (msg.obj instanceof String) {
                            MeetingRecognitionListener mMeetingRecognitionListener4 = MeetingRecognitionTaskImp.this.getMMeetingRecognitionListener();
                            Object obj6 = msg.obj;
                            Intrinsics.checkNotNull(obj6, "null cannot be cast to non-null type kotlin.String");
                            mMeetingRecognitionListener4.onMeetingMp3FileName((String) obj6);
                        }
                    } else if (MeetingRecognitionTaskImp.this.mWebSocketThread != null) {
                        removeMessages(16);
                        MeetingRecognitionTaskImp.this.haveEnter = false;
                        Logger logger3 = Logger.INSTANCE;
                        String tag3 = MeetingRecognitionTaskImp.this.getTAG();
                        int i2 = msg.what;
                        Object obj7 = msg.obj;
                        Intrinsics.checkNotNull(obj7, "null cannot be cast to non-null type kotlin.String");
                        logger3.e(tag3, "dispatchMessage: receive " + i2 + ((String) obj7));
                        if (msg.obj instanceof String) {
                            if (MeetingRecognitionTaskImp.this.getIsDebug()) {
                                Logger logger4 = Logger.INSTANCE;
                                String tag4 = MeetingRecognitionTaskImp.this.getTAG();
                                Object obj8 = msg.obj;
                                Intrinsics.checkNotNull(obj8, "null cannot be cast to non-null type kotlin.String");
                                logger4.e(tag4, "MSG_UPDATE_ASR_END: " + ((String) obj8));
                            }
                            MeetingRecognitionTaskImp.this.setLastPartUpdateTimeStamp(System.currentTimeMillis());
                            MeetingRecognitionListener mMeetingRecognitionListener5 = MeetingRecognitionTaskImp.this.getMMeetingRecognitionListener();
                            Object obj9 = msg.obj;
                            Intrinsics.checkNotNull(obj9, "null cannot be cast to non-null type kotlin.String");
                            mMeetingRecognitionListener5.onMeetingTextResult((String) obj9, false);
                        }
                        sendMessageDelayed(obtainMessage(16), 2000L);
                    }
                } else if (msg.obj instanceof String) {
                    removeMessages(16);
                    MeetingRecognitionTaskImp.this.haveEnter = false;
                    Logger.INSTANCE.e(MeetingRecognitionTaskImp.this.getTAG(), "dispatchMessage: receive " + msg.what);
                    MeetingRecognitionTaskImp.this.setLastPartUpdateTimeStamp(System.currentTimeMillis());
                    if (MeetingRecognitionTaskImp.this.getIsDebug()) {
                        Logger logger5 = Logger.INSTANCE;
                        String tag5 = MeetingRecognitionTaskImp.this.getTAG();
                        Object obj10 = msg.obj;
                        Intrinsics.checkNotNull(obj10, "null cannot be cast to non-null type kotlin.String");
                        logger5.e(tag5, "MSG_UPDATE_ASR_RESULT: " + ((String) obj10));
                    }
                    MeetingRecognitionListener mMeetingRecognitionListener6 = MeetingRecognitionTaskImp.this.getMMeetingRecognitionListener();
                    Object obj11 = msg.obj;
                    Intrinsics.checkNotNull(obj11, "null cannot be cast to non-null type kotlin.String");
                    mMeetingRecognitionListener6.onMeetingTextResult((String) obj11, true);
                }
                super.dispatchMessage(msg);
            }
        };
    }

    @Override // com.miui.pad.feature.notes.meeting.MeetingRecognitionTask
    public void start(boolean isSingle, boolean isTrans, boolean isFold) {
        this.mTaskStartTime = System.currentTimeMillis();
        if (!isSkipSpeechRecognition()) {
            if (this.mNetWorkValid && this.mWebSocketThread == null) {
                this.mWebSocketThread = new WebSocketThread(getMContext(), this);
                WebSocketThread webSocketThread = this.mWebSocketThread;
                if (webSocketThread != null) {
                    webSocketThread.setMIsSingle(isSingle);
                }
                WebSocketThread webSocketThread2 = this.mWebSocketThread;
                if (webSocketThread2 != null) {
                    webSocketThread2.setMNeedTranslate(isTrans);
                }
                WebSocketThread webSocketThread3 = this.mWebSocketThread;
                if (webSocketThread3 != null) {
                    webSocketThread3.setMIsFold(isFold);
                }
            }
            onRequestStart();
        }
        super.start(isSingle, isTrans, isFold);
    }

    @Override // com.miui.pad.feature.notes.meeting.MeetingRecognitionTask
    public void onRequestStart() {
        if (this.mWebSocketThread == null) {
            return;
        }
        try {
            WebSocketThread webSocketThread = this.mWebSocketThread;
            if (webSocketThread != null) {
                webSocketThread.connectionWebSocket();
            }
        } catch (Exception e) {
            if (getIsDebug()) {
                Logger.INSTANCE.e(getTAG(), e.toString());
            }
        }
    }

    @Override // com.miui.pad.feature.notes.meeting.MeetingRecognitionTask
    protected void onBufferSend(byte[] buffer) {
        WebSocketThread webSocketThread;
        if (buffer != null) {
            if ((buffer.length == 0) || (webSocketThread = this.mWebSocketThread) == null) {
                return;
            }
            webSocketThread.sendBuffer(buffer, 0, buffer.length);
        }
    }

    @Override // com.miui.pad.feature.notes.meeting.MeetingRecognitionTask
    protected void onRenameMp3File(String mp3FileName) {
        Logger.INSTANCE.i(getTAG(), "onRenameMp3File sendToTarget");
        this.mHandler.obtainMessage(4, mp3FileName).sendToTarget();
    }

    @Override // com.miui.pad.feature.notes.meeting.MeetingRecognitionTask
    protected void updateMaxAmplitude(short[] pcmShortBuffer, int size) {
        short s = 0;
        for (int i = 0; i < size; i++) {
            Intrinsics.checkNotNull(pcmShortBuffer);
            short s2 = pcmShortBuffer[i];
            if (s2 > s) {
                s = s2;
            }
        }
        if (System.currentTimeMillis() - this.lastUpdateAmplitude > 100) {
            this.mHandler.obtainMessage(5, Short.valueOf(s)).sendToTarget();
            this.lastUpdateAmplitude = System.currentTimeMillis();
        }
    }

    @Override // com.miui.pad.feature.notes.meeting.MeetingRecognitionTask
    public void stopEngine() {
        if (this.mNetWorkValid || this.mWebSocketThread == null) {
            return;
        }
        WebSocketThread webSocketThread = this.mWebSocketThread;
        if (webSocketThread != null) {
            webSocketThread.stopAsr();
        }
        WebSocketThread webSocketThread2 = this.mWebSocketThread;
        if (webSocketThread2 != null) {
            webSocketThread2.close();
        }
        this.mWebSocketThread = null;
    }

    @Override // com.miui.pad.feature.notes.meeting.MeetingRecognitionTask
    public void startEngine() {
        if (this.mNetWorkValid && this.mWebSocketThread == null) {
            this.mWebSocketThread = new WebSocketThread(getMContext(), this);
            WebSocketThread webSocketThread = this.mWebSocketThread;
            if (webSocketThread != null) {
                webSocketThread.connectionWebSocket();
            }
        }
    }

    @Override // com.miui.pad.feature.notes.meeting.MeetingRecognitionTask
    public void updateAsrInfo(boolean isSingle, boolean needTranslate, boolean isFold) {
        WebSocketThread webSocketThread = this.mWebSocketThread;
        if (webSocketThread != null) {
            webSocketThread.setMNeedTranslate(needTranslate);
        }
        WebSocketThread webSocketThread2 = this.mWebSocketThread;
        if (webSocketThread2 != null) {
            webSocketThread2.setMIsSingle(isSingle);
        }
        WebSocketThread webSocketThread3 = this.mWebSocketThread;
        if (webSocketThread3 != null) {
            webSocketThread3.setMIsFold(isFold);
        }
        WebSocketThread webSocketThread4 = this.mWebSocketThread;
        if (webSocketThread4 != null) {
            webSocketThread4.updateInfo();
        }
    }

    @Override // com.miui.pad.feature.notes.meeting.MeetingRecognitionTask
    public void addEnterFlag() {
        if (isSkipSpeechRecognition() || !this.mNetWorkValid) {
            StringBuilder sb = new StringBuilder(StringUtils.LF);
            String str = new SimpleDateFormat("mm:ss", Locale.getDefault()).format(Long.valueOf(System.currentTimeMillis() - this.mTaskStartTime));
            Intrinsics.checkNotNullExpressionValue(str, "format(...)");
            sb.append(str);
            sb.append(". \n");
            Message messageObtainMessage = this.mHandler.obtainMessage(17, sb.toString());
            Intrinsics.checkNotNullExpressionValue(messageObtainMessage, "obtainMessage(...)");
            this.mHandler.sendMessageAtFrontOfQueue(messageObtainMessage);
            return;
        }
        if (this.mWebSocketThread == null) {
            return;
        }
        StringBuilder sb2 = new StringBuilder(StringUtils.LF);
        long jCurrentTimeMillis = System.currentTimeMillis();
        WebSocketThread webSocketThread = this.mWebSocketThread;
        Intrinsics.checkNotNull(webSocketThread);
        String str2 = new SimpleDateFormat("mm:ss", Locale.getDefault()).format(Long.valueOf(jCurrentTimeMillis - webSocketThread.getStartTime()));
        Intrinsics.checkNotNullExpressionValue(str2, "format(...)");
        sb2.append(str2);
        sb2.append(". ");
        WebSocketThread webSocketThread2 = this.mWebSocketThread;
        Intrinsics.checkNotNull(webSocketThread2);
        if (!webSocketThread2.getMIsSingle()) {
            WebSocketThread webSocketThread3 = this.mWebSocketThread;
            Intrinsics.checkNotNull(webSocketThread3);
            WebSocketThread webSocketThread4 = this.mWebSocketThread;
            Intrinsics.checkNotNull(webSocketThread4);
            String virtualRoleName = webSocketThread3.getVirtualRoleName(webSocketThread4.getLastRole());
            if (virtualRoleName != null) {
                sb2.append(virtualRoleName);
            }
        }
        sb2.append(StringUtils.LF);
        WebSocketThread webSocketThread5 = this.mWebSocketThread;
        if (webSocketThread5 != null) {
            webSocketThread5.addEnterFlag(str2);
        }
        Message messageObtainMessage2 = this.mHandler.obtainMessage(3, sb2.toString());
        Intrinsics.checkNotNullExpressionValue(messageObtainMessage2, "obtainMessage(...)");
        this.mHandler.sendMessageAtFrontOfQueue(messageObtainMessage2);
    }

    @Override // com.miui.pad.feature.notes.meeting.MeetingRecognitionTask
    public void shutdownNow() {
        super.shutdownNow();
        if (this.mWebSocketThread == null) {
            return;
        }
        WebSocketThread webSocketThread = this.mWebSocketThread;
        if (webSocketThread != null) {
            webSocketThread.stopAsr();
        }
        WebSocketThread webSocketThread2 = this.mWebSocketThread;
        if (webSocketThread2 != null) {
            webSocketThread2.close();
        }
        this.mWebSocketThread = null;
        this.mHandler.removeCallbacksAndMessages(null);
    }

    public final long getLastPartUpdateTimeStamp() {
        return this.lastPartUpdateTimeStamp;
    }

    public final void setLastPartUpdateTimeStamp(long j) {
        this.lastPartUpdateTimeStamp = j;
    }

    @Override // com.miui.pad.feature.notes.meeting.WebSocketThread.OnMessageReceiveListener
    public void onMessagePart(String message) {
        if (System.currentTimeMillis() - this.lastPartUpdateTimeStamp > 200) {
            this.mHandler.obtainMessage(1, message).sendToTarget();
        }
    }

    @Override // com.miui.pad.feature.notes.meeting.WebSocketThread.OnMessageReceiveListener
    public void onMessageFull(String message) {
        Logger.INSTANCE.e(getTAG(), "dispatchMessage: send9");
        Message messageObtainMessage = this.mHandler.obtainMessage(9, message);
        Intrinsics.checkNotNullExpressionValue(messageObtainMessage, "obtainMessage(...)");
        this.mHandler.sendMessage(messageObtainMessage);
    }

    @Override // com.miui.pad.feature.notes.meeting.WebSocketThread.OnMessageReceiveListener
    public void onMessageEnd(String message) {
        Logger.INSTANCE.e(getTAG(), "dispatchMessage: send3" + message);
        this.mHandler.removeMessages(1);
        Message messageObtainMessage = this.mHandler.obtainMessage(3, message);
        Intrinsics.checkNotNullExpressionValue(messageObtainMessage, "obtainMessage(...)");
        this.mHandler.sendMessageAtFrontOfQueue(messageObtainMessage);
    }

    @Override // com.miui.pad.feature.notes.meeting.WebSocketThread.OnMessageReceiveListener
    public void onClosing(String message) {
        this.mHandler.obtainMessage(7, message).sendToTarget();
    }

    @Override // com.miui.pad.feature.notes.meeting.WebSocketThread.OnMessageReceiveListener
    public void onClosed(String reason) {
        Intrinsics.checkNotNullParameter(reason, "reason");
        this.mHandler.obtainMessage(7, reason).sendToTarget();
    }

    @Override // com.miui.pad.feature.notes.meeting.WebSocketThread.OnMessageReceiveListener
    public void onFailure(Response response) {
        Logger.INSTANCE.e(getTAG(), "onFailure: ");
        this.mHandler.obtainMessage(8, response).sendToTarget();
    }
}
