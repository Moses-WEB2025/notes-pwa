package com.miui.pad.feature.notes.meeting;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;
import androidx.exifinterface.media.ExifInterface;
import com.market.sdk.reflect.Field;
import com.market.sdk.reflect.ReflectUtilsForMiui;
import com.miui.common.app.BaseNoteApp;
import com.miui.common.tool.RomUtils;
import com.miui.common.tool.Utils;
import com.miui.notes.base.utils.Logger;
import com.miui.richeditor.IRichEditor;
import com.xiaomi.miai.SDKEngine;
import com.xiaomi.miai.api.SpeechGeneral;
import com.xiaomi.miai.api.SpeechMiAiSpeechAsr;
import com.xiaomi.miai.api.SpeechMiAiSpeechConference;
import com.xiaomi.miai.api.common.APIUtils;
import com.xiaomi.miai.api.common.Instruction;
import com.xiaomi.miai.auth.AuthTokenStorage;
import com.xiaomi.miai.auth.TokenRecord;
import com.xiaomi.miai.auth.UserInfo;
import com.xiaomi.miai.config.ConfigKeys;
import com.xiaomi.miai.config.Endpoints;
import com.xiaomi.miai.config.MiaiSdkConfig;
import com.xiaomi.miai.network.HttpClients;
import com.xiaomi.miai.network.UserAgent;
import com.xiaomi.onetrack.api.ah;
import com.xiaomi.onetrack.api.ai;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.TreeMap;
import java.util.concurrent.ConcurrentHashMap;
import kotlin.Metadata;
import kotlin.TuplesKt;
import kotlin.collections.CollectionsKt;
import kotlin.collections.MapsKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;
import miuix.navigation.SplitLayout;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.WebSocket;
import okhttp3.WebSocketListener;
import okio.ByteString;
import org.apache.commons.lang3.StringUtils;

/* JADX INFO: compiled from: WebSocketThread.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000\u008e\u0001\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\b\n\u0002\b\u0010\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0010\t\n\u0002\b\u0005\n\u0002\u0010\u0002\n\u0002\b\n\n\u0002\u0010\u0012\n\u0002\b\u0004\n\u0002\u0010!\n\u0002\u0018\u0002\n\u0002\b\u000b\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\u0018\u00002\u00020\u0001:\u0001iB\u0017\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005¢\u0006\u0004\b\u0006\u0010\u0007J\u0006\u0010D\u001a\u00020EJ\u0006\u0010F\u001a\u00020EJ\u0006\u0010G\u001a\u00020\rJ\u0006\u0010H\u001a\u00020\rJ\u000e\u0010I\u001a\u00020E2\u0006\u0010J\u001a\u00020\u0016J\u0006\u0010K\u001a\u00020EJ\u000e\u0010L\u001a\u00020E2\u0006\u0010J\u001a\u00020\u0016J\u0006\u0010M\u001a\u00020EJ\u001e\u0010N\u001a\u00020E2\u0006\u0010O\u001a\u00020P2\u0006\u0010Q\u001a\u00020\u001c2\u0006\u0010R\u001a\u00020\u001cJ&\u0010S\u001a\u00020\r2\f\u0010T\u001a\b\u0012\u0004\u0012\u00020V0U2\u0006\u0010W\u001a\u00020\r2\u0006\u0010X\u001a\u00020\u0011H\u0002J.\u0010Y\u001a\u00020\r2\f\u0010T\u001a\b\u0012\u0004\u0012\u00020V0U2\u0006\u0010W\u001a\u00020\r2\u0006\u0010X\u001a\u00020\u00112\u0006\u0010Z\u001a\u00020\u0011H\u0002J\u000e\u0010[\u001a\u00020E2\u0006\u0010\\\u001a\u00020\rJ\u0010\u0010]\u001a\u0004\u0018\u00010\r2\u0006\u0010^\u001a\u00020\rJ\b\u0010_\u001a\u00020\rH\u0002J\u000e\u0010`\u001a\u00020E2\u0006\u0010a\u001a\u00020bJ\u000e\u0010c\u001a\u00020E2\u0006\u0010d\u001a\u00020eJ&\u0010f\u001a\u00020\r2\f\u0010T\u001a\b\u0012\u0004\u0012\u00020V0U2\u0006\u0010W\u001a\u00020\r2\u0006\u0010Z\u001a\u00020\u0011H\u0002J6\u0010f\u001a\u00020\r2\f\u0010T\u001a\b\u0012\u0004\u0012\u00020V0U2\u0006\u0010W\u001a\u00020\r2\u0006\u0010g\u001a\u00020\u00112\u0006\u0010X\u001a\u00020\u00112\u0006\u0010Z\u001a\u00020\u0011H\u0002J\b\u0010h\u001a\u00020EH\u0002R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\b\u0010\tR\u0011\u0010\u0004\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\n\u0010\u000bR\u0014\u0010\f\u001a\u00020\rX\u0086D¢\u0006\b\n\u0000\u001a\u0004\b\u000e\u0010\u000fR\u0014\u0010\u0010\u001a\u00020\u0011X\u0086D¢\u0006\b\n\u0000\u001a\u0004\b\u0010\u0010\u0012R\u0014\u0010\u0013\u001a\u00020\u0011X\u0086D¢\u0006\b\n\u0000\u001a\u0004\b\u0013\u0010\u0012R\u0014\u0010\u0014\u001a\u00020\u0011X\u0086D¢\u0006\b\n\u0000\u001a\u0004\b\u0014\u0010\u0012R\u001c\u0010\u0015\u001a\u0004\u0018\u00010\u0016X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0017\u0010\u0018\"\u0004\b\u0019\u0010\u001aR\u000e\u0010\u001b\u001a\u00020\u001cX\u0082\u000e¢\u0006\u0002\n\u0000R\u001a\u0010\u001d\u001a\u00020\rX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u001e\u0010\u000f\"\u0004\b\u001f\u0010 R\u001a\u0010!\u001a\u00020\u0011X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\"\u0010\u0012\"\u0004\b#\u0010$R\u001a\u0010%\u001a\u00020\u0011X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b&\u0010\u0012\"\u0004\b'\u0010$R\u001a\u0010(\u001a\u00020\u0011X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b)\u0010\u0012\"\u0004\b*\u0010$R\u000e\u0010+\u001a\u00020\rX\u0082\u000e¢\u0006\u0002\n\u0000R*\u0010,\u001a\u001e\u0012\u0004\u0012\u00020\r\u0012\u0004\u0012\u00020\r0-j\u000e\u0012\u0004\u0012\u00020\r\u0012\u0004\u0012\u00020\r`.X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010/\u001a\u00020\rX\u0082D¢\u0006\u0002\n\u0000R\u000e\u00100\u001a\u00020\rX\u0082D¢\u0006\u0002\n\u0000R\u000e\u00101\u001a\u00020\rX\u0082D¢\u0006\u0002\n\u0000R*\u00102\u001a\u001e\u0012\u0004\u0012\u00020\r\u0012\u0004\u0012\u00020\r0-j\u000e\u0012\u0004\u0012\u00020\r\u0012\u0004\u0012\u00020\r`.X\u0082\u000e¢\u0006\u0002\n\u0000R\u001a\u00103\u001a\u000e\u0012\u0004\u0012\u00020\u001c\u0012\u0004\u0012\u00020504X\u0082\u000e¢\u0006\u0002\n\u0000R\u001a\u00106\u001a\u000e\u0012\u0004\u0012\u00020\u001c\u0012\u0004\u0012\u00020807X\u0082\u000e¢\u0006\u0002\n\u0000R\u001a\u00109\u001a\u00020\rX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b:\u0010\u000f\"\u0004\b;\u0010 R\u000e\u0010<\u001a\u00020\u001cX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010=\u001a\u00020\u001cX\u0082\u000e¢\u0006\u0002\n\u0000R\u001a\u0010>\u001a\u00020?X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b@\u0010A\"\u0004\bB\u0010C¨\u0006j"}, d2 = {"Lcom/miui/pad/feature/notes/meeting/WebSocketThread;", "", "mContext", "Landroid/content/Context;", "mListener", "Lcom/miui/pad/feature/notes/meeting/WebSocketThread$OnMessageReceiveListener;", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "(Landroid/content/Context;Lcom/miui/pad/feature/notes/meeting/WebSocketThread$OnMessageReceiveListener;)V", "getMContext", "()Landroid/content/Context;", "getMListener", "()Lcom/miui/pad/feature/notes/meeting/WebSocketThread$OnMessageReceiveListener;", "TAG", "", "getTAG", "()Ljava/lang/String;", "isDebug", "", "()Z", "isNewVersion", "isProduct", "mWebSocket", "Lokhttp3/WebSocket;", "getMWebSocket", "()Lokhttp3/WebSocket;", "setMWebSocket", "(Lokhttp3/WebSocket;)V", "mChannel", "", "mAppName", "getMAppName", "setMAppName", "(Ljava/lang/String;)V", "mNeedTranslate", "getMNeedTranslate", "setMNeedTranslate", "(Z)V", "mIsFold", "getMIsFold", "setMIsFold", "mIsSingle", "getMIsSingle", "setMIsSingle", "PREF_MEETING", "langMap", "Ljava/util/HashMap;", "Lkotlin/collections/HashMap;", "ROLE_PREFIX", "UNKNOWN_ROLE", "EMPTY_TRANSLATE", "roleNameMap", "mixResultMap", "Ljava/util/TreeMap;", "Lcom/miui/pad/feature/notes/meeting/SpeechConferenceMix;", "vorResultMap", "Ljava/util/concurrent/ConcurrentHashMap;", "Lcom/xiaomi/miai/api/SpeechMiAiSpeechConference$VorResult;", "lastRole", "getLastRole", "setLastRole", "lastRoleID", "finishId", "startTime", "", "getStartTime", "()J", "setStartTime", "(J)V", "connectionWebSocket", "", SplitLayout.TAG_CLOSE, "getModelType", "getDeviceType", "sendRecognize", "webSocket", "updateInfo", "startAsr", "stopAsr", "sendBuffer", "array", "", "start", "end", "findSingleText", "sentences", "", "Lcom/xiaomi/miai/api/SpeechMiAiSpeechAsr$Sentence;", "detectedLang", "hasRoleUpdate", "findTranslateText", "isComplete", "addEnterFlag", "time", "getVirtualRoleName", "vorRole", "getCurrentTimeInterval", "onReceiveConferenceResult", "confResult", "Lcom/xiaomi/miai/api/SpeechMiAiSpeechConference$ConferenceServiceResult;", "onReceiveVorResult", "vorResult", "Lcom/xiaomi/miai/api/SpeechMiAiSpeechConference$SpeechConferenceVorResult;", "processText", "needTranslate", "outPutAllRoleResult", "OnMessageReceiveListener", "app_PhoneGlobalRelease"}, k = 1, mv = {2, 2, 0}, xi = 48)
public final class WebSocketThread {
    private final String EMPTY_TRANSLATE;
    private String PREF_MEETING;
    private final String ROLE_PREFIX;
    private final String TAG;
    private final String UNKNOWN_ROLE;
    private int finishId;
    private final boolean isDebug;
    private final boolean isNewVersion;
    private final boolean isProduct;
    private HashMap<String, String> langMap;
    private String lastRole;
    private int lastRoleID;
    private String mAppName;
    private int mChannel;
    private final Context mContext;
    private boolean mIsFold;
    private boolean mIsSingle;
    private final OnMessageReceiveListener mListener;
    private boolean mNeedTranslate;
    private WebSocket mWebSocket;
    private TreeMap<Integer, SpeechConferenceMix> mixResultMap;
    private HashMap<String, String> roleNameMap;
    private long startTime;
    private ConcurrentHashMap<Integer, SpeechMiAiSpeechConference.VorResult> vorResultMap;

    /* JADX INFO: compiled from: WebSocketThread.kt */
    @Metadata(d1 = {"\u0000 \n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0000\bf\u0018\u00002\u00020\u0001J\b\u0010\u0002\u001a\u00020\u0003H&J\u0012\u0010\u0004\u001a\u00020\u00032\b\u0010\u0005\u001a\u0004\u0018\u00010\u0006H&J\u0012\u0010\u0007\u001a\u00020\u00032\b\u0010\u0005\u001a\u0004\u0018\u00010\u0006H&J\u0012\u0010\b\u001a\u00020\u00032\b\u0010\u0005\u001a\u0004\u0018\u00010\u0006H&J\u0012\u0010\t\u001a\u00020\u00032\b\u0010\u0005\u001a\u0004\u0018\u00010\u0006H&J\u0010\u0010\n\u001a\u00020\u00032\u0006\u0010\u000b\u001a\u00020\u0006H&J\u0012\u0010\f\u001a\u00020\u00032\b\u0010\r\u001a\u0004\u0018\u00010\u000eH&¨\u0006\u000fÀ\u0006\u0003"}, d2 = {"Lcom/miui/pad/feature/notes/meeting/WebSocketThread$OnMessageReceiveListener;", "", "onSocketOpen", "", "onMessagePart", "message", "", "onMessageFull", "onMessageEnd", "onClosing", "onClosed", "reason", "onFailure", ah.H, "Lokhttp3/Response;", "app_PhoneGlobalRelease"}, k = 1, mv = {2, 2, 0}, xi = 48)
    public interface OnMessageReceiveListener {
        void onClosed(String reason);

        void onClosing(String message);

        void onFailure(Response response);

        void onMessageEnd(String message);

        void onMessageFull(String message);

        void onMessagePart(String message);

        void onSocketOpen();
    }

    public WebSocketThread(Context mContext, OnMessageReceiveListener mListener) {
        Intrinsics.checkNotNullParameter(mContext, "mContext");
        Intrinsics.checkNotNullParameter(mListener, "mListener");
        this.mContext = mContext;
        this.mListener = mListener;
        this.TAG = "WebSocketThread";
        this.isDebug = true;
        this.isNewVersion = true;
        this.isProduct = true;
        this.mChannel = 2;
        this.mAppName = IRichEditor.CLIP_LABEL_RICH_EDITOR;
        this.mIsSingle = true;
        this.PREF_MEETING = "ai_meeting_pref";
        this.langMap = MapsKt.hashMapOf(TuplesKt.to("zh-Hans", "中"), TuplesKt.to("en-US", "英"));
        this.ROLE_PREFIX = "参会人";
        this.UNKNOWN_ROLE = "mock_client_id_conference_-1_cluster";
        this.EMPTY_TRANSLATE = "\n[中]：\n[英]：\n";
        this.roleNameMap = MapsKt.hashMapOf(TuplesKt.to("mock_client_id_conference_0_cluster", "参会人" + ExifInterface.GPS_MEASUREMENT_IN_PROGRESS), TuplesKt.to("mock_client_id_conference_1_cluster", "参会人B"), TuplesKt.to("mock_client_id_conference_2_cluster", "参会人" + Field.CHAR_SIGNATURE_PRIMITIVE), TuplesKt.to("mock_client_id_conference_3_cluster", "参会人" + Field.DOUBLE_SIGNATURE_PRIMITIVE), TuplesKt.to("mock_client_id_conference_4_cluster", "参会人" + ExifInterface.LONGITUDE_EAST), TuplesKt.to("mock_client_id_conference_5_cluster", "参会人" + Field.FLOAT_SIGNATURE_PRIMITIVE), TuplesKt.to("mock_client_id_conference_6_cluster", "参会人G"), TuplesKt.to("mock_client_id_conference_7_cluster", "参会人" + ai.b), TuplesKt.to("mock_client_id_conference_8_cluster", "参会人" + Field.INT_SIGNATURE_PRIMITIVE), TuplesKt.to("mock_client_id_conference_9_cluster", "参会人" + Field.LONG_SIGNATURE_PRIMITIVE), TuplesKt.to("mock_client_id_conference_10_cluster", "参会人K"), TuplesKt.to("mock_client_id_conference_11_cluster", "参会人L"), TuplesKt.to("mock_client_id_conference_12_cluster", "参会人M"), TuplesKt.to("mock_client_id_conference_13_cluster", "参会人N"), TuplesKt.to("mock_client_id_conference_14_cluster", "参会人O"), TuplesKt.to("mock_client_id_conference_15_cluster", "参会人P"), TuplesKt.to("mock_client_id_conference_16_cluster", "参会人Q"), TuplesKt.to("mock_client_id_conference_17_cluster", "参会人R"), TuplesKt.to("mock_client_id_conference_18_cluster", "参会人S"), TuplesKt.to("mock_client_id_conference_19_cluster", "参会人" + ExifInterface.GPS_DIRECTION_TRUE), TuplesKt.to("mock_client_id_conference_20_cluster", "参会人U"), TuplesKt.to("mock_client_id_conference_21_cluster", "参会人V"), TuplesKt.to("mock_client_id_conference_22_cluster", "参会人" + ExifInterface.LONGITUDE_WEST), TuplesKt.to("mock_client_id_conference_23_cluster", "参会人X"), TuplesKt.to("mock_client_id_conference_24_cluster", "参会人Y"), TuplesKt.to("mock_client_id_conference_25_cluster", "参会人" + Field.BOOLEAN_SIGNATURE_PRIMITIVE));
        this.mixResultMap = new TreeMap<>();
        this.vorResultMap = new ConcurrentHashMap<>(1000);
        this.lastRole = "";
        this.lastRoleID = -1;
        this.finishId = -1;
    }

    public final Context getMContext() {
        return this.mContext;
    }

    public final OnMessageReceiveListener getMListener() {
        return this.mListener;
    }

    public final String getTAG() {
        return this.TAG;
    }

    /* JADX INFO: renamed from: isDebug, reason: from getter */
    public final boolean getIsDebug() {
        return this.isDebug;
    }

    /* JADX INFO: renamed from: isNewVersion, reason: from getter */
    public final boolean getIsNewVersion() {
        return this.isNewVersion;
    }

    /* JADX INFO: renamed from: isProduct, reason: from getter */
    public final boolean getIsProduct() {
        return this.isProduct;
    }

    public final WebSocket getMWebSocket() {
        return this.mWebSocket;
    }

    public final void setMWebSocket(WebSocket webSocket) {
        this.mWebSocket = webSocket;
    }

    public final String getMAppName() {
        return this.mAppName;
    }

    public final void setMAppName(String str) {
        Intrinsics.checkNotNullParameter(str, "<set-?>");
        this.mAppName = str;
    }

    public final boolean getMNeedTranslate() {
        return this.mNeedTranslate;
    }

    public final void setMNeedTranslate(boolean z) {
        this.mNeedTranslate = z;
    }

    public final boolean getMIsFold() {
        return this.mIsFold;
    }

    public final void setMIsFold(boolean z) {
        this.mIsFold = z;
    }

    public final boolean getMIsSingle() {
        return this.mIsSingle;
    }

    public final void setMIsSingle(boolean z) {
        this.mIsSingle = z;
    }

    public final String getLastRole() {
        return this.lastRole;
    }

    public final void setLastRole(String str) {
        Intrinsics.checkNotNullParameter(str, "<set-?>");
        this.lastRole = str;
    }

    public final long getStartTime() {
        return this.startTime;
    }

    public final void setStartTime(long j) {
        this.startTime = j;
    }

    public final void connectionWebSocket() {
        this.mChannel = 2;
        if ((Intrinsics.areEqual(Build.DEVICE, "elish") || Intrinsics.areEqual(Build.DEVICE, "enuma")) && Utils.isSupport4micMode()) {
            this.mChannel = 4;
        }
        if (RomUtils.isPadDevice()) {
            this.mAppName = "Notes-Pad";
        }
        this.startTime = System.currentTimeMillis();
        SDKEngine sDKEngine = SDKEngine.get();
        Intrinsics.checkNotNullExpressionValue(sDKEngine, "get(...)");
        MiaiSdkConfig miaiSdkConfigPut = sDKEngine.getConfig().put(ConfigKeys.USER_INFO, new UserInfo("", "")).put(ConfigKeys.USER_AGENT, UserAgent.create().add(this.mAppName, BaseNoteApp.INSTANCE.getInstance().getPackageName()));
        if (this.isProduct) {
            miaiSdkConfigPut.put(ConfigKeys.ENV_ENDPOINTS, Endpoints.Production);
        } else {
            miaiSdkConfigPut.put(ConfigKeys.ENV_ENDPOINTS, new Endpoints.StagingEndpoints() { // from class: com.miui.pad.feature.notes.meeting.WebSocketThread.connectionWebSocket.1
                @Override // com.xiaomi.miai.config.Endpoints
                public String getMiaiSodApiUrl(String path) {
                    Intrinsics.checkNotNullParameter(path, "path");
                    return StringsKt.contains$default((CharSequence) path, (CharSequence) "memo", false, 2, (Object) null) ? "ws://10.38.167.78:11546/speech/conference/v1/memo" : super.getMiaiSodApiUrl(path);
                }
            });
        }
        sDKEngine.getAuthManager().register(new NoteHardwareAuthProvider("CL78442", "notes"));
        final SharedPreferences sharedPreferences = this.mContext.getSharedPreferences(this.PREF_MEETING, 0);
        sDKEngine.getAuthManager().setStorage(new AuthTokenStorage() { // from class: com.miui.pad.feature.notes.meeting.WebSocketThread.connectionWebSocket.2
            @Override // com.xiaomi.miai.auth.AuthTokenStorage
            public void write(TokenRecord p0) {
                sharedPreferences.edit().putString("auth", APIUtils.getGson().toJson(p0)).apply();
            }

            @Override // com.xiaomi.miai.auth.AuthTokenStorage
            public TokenRecord read(String tokenType, String identity) {
                String string = sharedPreferences.getString("auth", "");
                if (string == null || string.length() == 0) {
                    return null;
                }
                return (TokenRecord) APIUtils.getGson().fromJson(string, TokenRecord.class);
            }
        });
        HttpClients httpClients = sDKEngine.getHttpClients();
        String miaiSodApiUrl = ((Endpoints) miaiSdkConfigPut.get(ConfigKeys.ENV_ENDPOINTS)).getMiaiSodApiUrl("/speech/conference/v1/memo");
        Logger.INSTANCE.e(this.TAG, "webSocketUrl: " + miaiSodApiUrl);
        Request.Builder builder = new Request.Builder();
        Intrinsics.checkNotNull(miaiSodApiUrl);
        httpClients.websocketRequestWithAuth(builder.url(miaiSodApiUrl).build(), new WebSocketListener() { // from class: com.miui.pad.feature.notes.meeting.WebSocketThread.connectionWebSocket.3
            @Override // okhttp3.WebSocketListener
            public void onOpen(WebSocket webSocket, Response response) {
                Intrinsics.checkNotNullParameter(webSocket, "webSocket");
                Intrinsics.checkNotNullParameter(response, "response");
                super.onOpen(webSocket, response);
                if (WebSocketThread.this.getIsDebug()) {
                    Logger.INSTANCE.e(WebSocketThread.this.getTAG(), "onOpen: ");
                }
                WebSocketThread.this.setMWebSocket(webSocket);
                WebSocketThread.this.sendRecognize(webSocket);
                WebSocketThread.this.updateInfo();
                WebSocketThread.this.startAsr(webSocket);
                WebSocketThread.this.getMListener().onSocketOpen();
            }

            /* JADX WARN: Multi-variable type inference failed */
            @Override // okhttp3.WebSocketListener
            public void onMessage(WebSocket webSocket, String text) {
                Intrinsics.checkNotNullParameter(webSocket, "webSocket");
                Intrinsics.checkNotNullParameter(text, "text");
                super.onMessage(webSocket, text);
                if (WebSocketThread.this.getIsDebug()) {
                    Logger.INSTANCE.e(WebSocketThread.this.getTAG(), "onMessage e : " + text);
                }
                Instruction instruction = APIUtils.readInstruction(text);
                Intrinsics.checkNotNullExpressionValue(instruction, "readInstruction(...)");
                T payload = instruction.getPayload();
                if (payload instanceof SpeechMiAiSpeechConference.ConferenceServiceResult) {
                    if (WebSocketThread.this.getIsDebug()) {
                        Logger.INSTANCE.e(WebSocketThread.this.getTAG(), "onMessage: +SpeechConferenceResult");
                    }
                    WebSocketThread webSocketThread = WebSocketThread.this;
                    T payload2 = instruction.getPayload();
                    Intrinsics.checkNotNull(payload2, "null cannot be cast to non-null type com.xiaomi.miai.api.SpeechMiAiSpeechConference.ConferenceServiceResult");
                    webSocketThread.onReceiveConferenceResult((SpeechMiAiSpeechConference.ConferenceServiceResult) payload2);
                    return;
                }
                if (payload instanceof SpeechMiAiSpeechConference.SpeechConferenceVorResult) {
                    if (WebSocketThread.this.getIsDebug()) {
                        Logger.INSTANCE.e(WebSocketThread.this.getTAG(), "onMessage: +SpeechConferenceVorResult");
                    }
                    WebSocketThread webSocketThread2 = WebSocketThread.this;
                    T payload3 = instruction.getPayload();
                    Intrinsics.checkNotNull(payload3, "null cannot be cast to non-null type com.xiaomi.miai.api.SpeechMiAiSpeechConference.SpeechConferenceVorResult");
                    webSocketThread2.onReceiveVorResult((SpeechMiAiSpeechConference.SpeechConferenceVorResult) payload3);
                    return;
                }
                Logger.INSTANCE.e(WebSocketThread.this.getTAG(), "onMessage: error type:" + instruction.getName());
                WebSocketThread.this.getMListener().onFailure(null);
            }

            @Override // okhttp3.WebSocketListener
            public void onMessage(WebSocket webSocket, ByteString bytes) {
                Intrinsics.checkNotNullParameter(webSocket, "webSocket");
                Intrinsics.checkNotNullParameter(bytes, "bytes");
                super.onMessage(webSocket, bytes);
                if (WebSocketThread.this.getIsDebug()) {
                    Logger.INSTANCE.e(WebSocketThread.this.getTAG(), "onMessagev b: " + bytes);
                }
            }

            @Override // okhttp3.WebSocketListener
            public void onClosing(WebSocket webSocket, int code, String reason) {
                Intrinsics.checkNotNullParameter(webSocket, "webSocket");
                Intrinsics.checkNotNullParameter(reason, "reason");
                super.onClosing(webSocket, code, reason);
                if (WebSocketThread.this.getIsDebug()) {
                    Logger.INSTANCE.e(WebSocketThread.this.getTAG(), "onClosing: code " + code + " reason " + reason);
                }
                WebSocketThread.this.getMListener().onClosing(reason);
            }

            @Override // okhttp3.WebSocketListener
            public void onClosed(WebSocket webSocket, int code, String reason) {
                Intrinsics.checkNotNullParameter(webSocket, "webSocket");
                Intrinsics.checkNotNullParameter(reason, "reason");
                super.onClosed(webSocket, code, reason);
                if (WebSocketThread.this.getIsDebug()) {
                    Logger.INSTANCE.e(WebSocketThread.this.getTAG(), "onClosed: code " + code + " reason " + reason);
                }
                WebSocketThread.this.getMListener().onClosed(reason);
            }

            @Override // okhttp3.WebSocketListener
            public void onFailure(WebSocket webSocket, Throwable t, Response response) {
                Intrinsics.checkNotNullParameter(webSocket, "webSocket");
                Intrinsics.checkNotNullParameter(t, "t");
                super.onFailure(webSocket, t, response);
                if (WebSocketThread.this.getIsDebug()) {
                    Logger.INSTANCE.e(WebSocketThread.this.getTAG(), "onFailure: " + response + t);
                }
                WebSocketThread.this.getMListener().onFailure(response);
            }
        });
    }

    public final void close() {
        WebSocket webSocket = this.mWebSocket;
        if (webSocket != null) {
            webSocket.close(1000, "closing");
        }
    }

    public final String getModelType() {
        if (RomUtils.isPadDevice()) {
            return "PAD";
        }
        return "CELLPHONE";
    }

    public final String getDeviceType() {
        String DEVICE = Build.DEVICE;
        Intrinsics.checkNotNullExpressionValue(DEVICE, "DEVICE");
        return DEVICE;
    }

    public final void sendRecognize(WebSocket webSocket) {
        SpeechMiAiSpeechAsr.SpeechRecognize speechRecognize;
        Intrinsics.checkNotNullParameter(webSocket, "webSocket");
        if (this.isNewVersion) {
            speechRecognize = new SpeechMiAiSpeechAsr.SpeechRecognize(getModelType(), SpeechMiAiSpeechAsr.AsrCodec.pcm, 16, 16000, this.mChannel, CollectionsKt.listOf(SpeechGeneral.Vendor.XiaoMi));
            speechRecognize.setDeviceType(getDeviceType());
        } else {
            speechRecognize = new SpeechMiAiSpeechAsr.SpeechRecognize(Build.DEVICE, SpeechMiAiSpeechAsr.AsrCodec.pcm, 16, 16000, this.mChannel, CollectionsKt.listOf(SpeechGeneral.Vendor.XiaoMi));
        }
        String jsonString = APIUtils.toJsonString(APIUtils.buildEvent(speechRecognize));
        if (this.isDebug) {
            Logger.INSTANCE.e(this.TAG, "sendRecognize: " + jsonString);
        }
        Intrinsics.checkNotNull(jsonString);
        webSocket.send(jsonString);
    }

    public final void updateInfo() {
        String jsonString = APIUtils.toJsonString(APIUtils.buildEvent(new SpeechMiAiSpeechConference.ConferenceServiceInfoUpdate(this.mIsSingle, this.mNeedTranslate, this.mIsFold)));
        if (this.isDebug) {
            Logger.INSTANCE.e(this.TAG, "onMessage111: update infoStr: " + jsonString);
        }
        WebSocket webSocket = this.mWebSocket;
        if (webSocket != null) {
            Intrinsics.checkNotNull(jsonString);
            webSocket.send(jsonString);
        }
    }

    public final void startAsr(WebSocket webSocket) {
        Intrinsics.checkNotNullParameter(webSocket, "webSocket");
        String jsonString = APIUtils.toJsonString(APIUtils.buildEvent(new SpeechGeneral.RecognizeStreamStarted()));
        if (this.isDebug) {
            Logger.INSTANCE.e(this.TAG, "startStr: " + jsonString);
        }
        Intrinsics.checkNotNull(jsonString);
        webSocket.send(jsonString);
    }

    public final void stopAsr() {
        String jsonString = APIUtils.toJsonString(APIUtils.buildEvent(new SpeechGeneral.RecognizeStreamEnded()));
        if (this.isDebug) {
            Logger.INSTANCE.e(this.TAG, "stopAsr: " + jsonString);
        }
        WebSocket webSocket = this.mWebSocket;
        if (webSocket != null) {
            Intrinsics.checkNotNull(jsonString);
            webSocket.send(jsonString);
        }
        this.mNeedTranslate = false;
        this.mIsFold = false;
        this.mIsSingle = true;
    }

    public final void sendBuffer(byte[] array, int start, int end) {
        Intrinsics.checkNotNullParameter(array, "array");
        WebSocket webSocket = this.mWebSocket;
        if (webSocket != null) {
            webSocket.send(ByteString.INSTANCE.of(array, start, end));
        }
    }

    private final String findSingleText(List<SpeechMiAiSpeechAsr.Sentence> sentences, String detectedLang, boolean hasRoleUpdate) {
        if (this.isDebug) {
            Logger.INSTANCE.e(this.TAG, "findSentenceText: " + detectedLang);
        }
        if (Intrinsics.areEqual(detectedLang, "unknown-language")) {
            detectedLang = "zh-Hans";
        }
        for (SpeechMiAiSpeechAsr.Sentence sentence : sentences) {
            if (Intrinsics.areEqual(sentence.getLang(), detectedLang)) {
                String text = sentence.getText();
                Intrinsics.checkNotNullExpressionValue(text, "getText(...)");
                return text;
            }
        }
        return "";
    }

    private final String findTranslateText(List<SpeechMiAiSpeechAsr.Sentence> sentences, String detectedLang, boolean hasRoleUpdate, boolean isComplete) {
        StringBuffer stringBuffer = new StringBuffer("");
        if (!hasRoleUpdate) {
            stringBuffer.append(StringUtils.LF);
        }
        if (this.isDebug) {
            Logger.INSTANCE.e(this.TAG, "findSentenceText: " + detectedLang);
        }
        if (Intrinsics.areEqual(detectedLang, "unknown-language")) {
            detectedLang = "zh-Hans";
        }
        for (SpeechMiAiSpeechAsr.Sentence sentence : sentences) {
            if (Intrinsics.areEqual(sentence.getLang(), detectedLang)) {
                stringBuffer.append("[");
                if (isComplete) {
                    stringBuffer.append(this.langMap.get(sentence.getLang()));
                } else {
                    stringBuffer.append("识别中");
                }
                stringBuffer.append("]：").append(sentence.getText()).append(StringUtils.LF);
            }
        }
        for (SpeechMiAiSpeechAsr.Sentence sentence2 : sentences) {
            if (!Intrinsics.areEqual(sentence2.getLang(), detectedLang)) {
                stringBuffer.append("[");
                if (isComplete) {
                    stringBuffer.append(this.langMap.get(sentence2.getLang()));
                } else {
                    stringBuffer.append("识别中");
                }
                stringBuffer.append("]：").append(sentence2.getText()).append(StringUtils.LF);
            }
        }
        String string = stringBuffer.toString();
        Intrinsics.checkNotNullExpressionValue(string, "toString(...)");
        return string;
    }

    public final void addEnterFlag(String time) {
        Intrinsics.checkNotNullParameter(time, "time");
        SpeechConferenceMix speechConferenceMix = this.mixResultMap.get(Integer.valueOf(this.finishId));
        if (speechConferenceMix != null) {
            speechConferenceMix.getEnterFlagList().add(time);
        }
    }

    public final String getVirtualRoleName(String vorRole) {
        Intrinsics.checkNotNullParameter(vorRole, "vorRole");
        if (vorRole.length() == 0) {
            return null;
        }
        return this.roleNameMap.get(vorRole);
    }

    private final String getCurrentTimeInterval() {
        String str = new SimpleDateFormat("mm:ss").format(Long.valueOf(System.currentTimeMillis() - this.startTime));
        Intrinsics.checkNotNullExpressionValue(str, "format(...)");
        return str;
    }

    public final void onReceiveConferenceResult(SpeechMiAiSpeechConference.ConferenceServiceResult confResult) {
        Intrinsics.checkNotNullParameter(confResult, "confResult");
        if (this.isDebug) {
            Logger.INSTANCE.e(this.TAG, "onReceiveConferenceResult: " + confResult.getDetectedLang());
        }
        for (SpeechMiAiSpeechAsr.Sentence sentence : confResult.getSentences()) {
            if (this.isDebug) {
                Logger.INSTANCE.e(this.TAG, "onMessage0: sentences id " + confResult.getSentenceId() + StringUtils.SPACE + sentence.getText() + " iscomplete " + confResult.isSentenceCompleted() + StringUtils.SPACE + confResult.getDetectedLang());
            }
        }
        int sentenceId = confResult.getSentenceId();
        SpeechConferenceMix speechConferenceMix = new SpeechConferenceMix(sentenceId, confResult, getCurrentTimeInterval());
        this.mixResultMap.put(Integer.valueOf(sentenceId), speechConferenceMix);
        if (confResult.isSentenceCompleted()) {
            this.finishId = sentenceId;
            List<SpeechMiAiSpeechAsr.Sentence> sentences = confResult.getSentences();
            Intrinsics.checkNotNullExpressionValue(sentences, "getSentences(...)");
            String detectedLang = confResult.getDetectedLang();
            Intrinsics.checkNotNullExpressionValue(detectedLang, "getDetectedLang(...)");
            String strProcessText = processText(sentences, detectedLang, true);
            speechConferenceMix.setSingle(this.mIsSingle);
            speechConferenceMix.setTranslate(this.mNeedTranslate);
            if (Intrinsics.areEqual(strProcessText, this.EMPTY_TRANSLATE)) {
                strProcessText = "";
            }
            this.mListener.onMessageEnd(strProcessText);
            return;
        }
        if (sentenceId == this.finishId + 1) {
            List<SpeechMiAiSpeechAsr.Sentence> sentences2 = confResult.getSentences();
            Intrinsics.checkNotNullExpressionValue(sentences2, "getSentences(...)");
            String detectedLang2 = confResult.getDetectedLang();
            Intrinsics.checkNotNullExpressionValue(detectedLang2, "getDetectedLang(...)");
            this.mListener.onMessagePart(processText(sentences2, detectedLang2, false));
        }
    }

    public final void onReceiveVorResult(SpeechMiAiSpeechConference.SpeechConferenceVorResult vorResult) {
        Intrinsics.checkNotNullParameter(vorResult, "vorResult");
        boolean z = false;
        for (SpeechMiAiSpeechConference.VorResult vorResult2 : vorResult.getVorResults()) {
            int sentenceId = vorResult2.getSentenceId();
            this.vorResultMap.put(Integer.valueOf(sentenceId), vorResult2);
            if (!Intrinsics.areEqual(vorResult2.getVorRole(), this.UNKNOWN_ROLE)) {
                if (this.vorResultMap.contains(Integer.valueOf(sentenceId))) {
                    z = true;
                } else if (sentenceId >= this.lastRoleID && !Intrinsics.areEqual(vorResult2.getVorRole(), this.UNKNOWN_ROLE)) {
                    if (!Intrinsics.areEqual(this.lastRole, vorResult2.getVorRole())) {
                        z = true;
                    }
                    String vorRole = vorResult2.getVorRole();
                    Intrinsics.checkNotNullExpressionValue(vorRole, "getVorRole(...)");
                    this.lastRole = vorRole;
                    this.lastRoleID = sentenceId;
                }
            }
        }
        if (!z || this.mIsSingle) {
            return;
        }
        outPutAllRoleResult();
    }

    private final String processText(List<SpeechMiAiSpeechAsr.Sentence> sentences, String detectedLang, boolean isComplete) {
        return this.mNeedTranslate ? findTranslateText(sentences, detectedLang, false, isComplete) : findSingleText(sentences, detectedLang, false);
    }

    private final String processText(List<SpeechMiAiSpeechAsr.Sentence> sentences, String detectedLang, boolean needTranslate, boolean hasRoleUpdate, boolean isComplete) {
        if (needTranslate) {
            return findTranslateText(sentences, detectedLang, hasRoleUpdate, isComplete);
        }
        return findSingleText(sentences, detectedLang, hasRoleUpdate);
    }

    private final void outPutAllRoleResult() {
        boolean z;
        StringBuilder sb = new StringBuilder();
        String vorRole = "";
        for (SpeechConferenceMix speechConferenceMix : this.mixResultMap.values()) {
            Intrinsics.checkNotNullExpressionValue(speechConferenceMix, "next(...)");
            SpeechConferenceMix speechConferenceMix2 = speechConferenceMix;
            SpeechMiAiSpeechConference.ConferenceServiceResult sentenceResult = speechConferenceMix2.getSentenceResult();
            if (!sentenceResult.isSentenceCompleted()) {
                break;
            }
            int sentenceId = sentenceResult.getSentenceId();
            Logger.INSTANCE.e(this.TAG, "outPutAllRoleResult: " + sentenceId);
            SpeechMiAiSpeechConference.VorResult vorResult = this.vorResultMap.get(Integer.valueOf(sentenceId));
            if (vorResult == null || Intrinsics.areEqual(vorRole, vorResult.getVorRole()) || speechConferenceMix2.getIsSingle() || Intrinsics.areEqual(vorResult.getVorRole(), this.UNKNOWN_ROLE)) {
                z = false;
            } else {
                vorRole = vorResult.getVorRole();
                Intrinsics.checkNotNullExpressionValue(vorRole, "getVorRole(...)");
                sb.append(StringUtils.LF).append(speechConferenceMix2.getTime()).append(StringUtils.SPACE).append(this.roleNameMap.get(vorResult.getVorRole())).append(StringUtils.LF);
                z = true;
            }
            List<SpeechMiAiSpeechAsr.Sentence> sentences = sentenceResult.getSentences();
            Intrinsics.checkNotNullExpressionValue(sentences, "getSentences(...)");
            String detectedLang = sentenceResult.getDetectedLang();
            Intrinsics.checkNotNullExpressionValue(detectedLang, "getDetectedLang(...)");
            WebSocketThread webSocketThread = this;
            String strProcessText = webSocketThread.processText(sentences, detectedLang, speechConferenceMix2.getIsTranslate(), z, sentenceResult.isSentenceCompleted());
            if (Intrinsics.areEqual(strProcessText, webSocketThread.EMPTY_TRANSLATE)) {
                strProcessText = "";
            }
            sb.append(strProcessText);
            ArrayList<String> enterFlagList = speechConferenceMix2.getEnterFlagList();
            if (!enterFlagList.isEmpty()) {
                Iterator<String> it = enterFlagList.iterator();
                Intrinsics.checkNotNullExpressionValue(it, "iterator(...)");
                while (it.hasNext()) {
                    String next = it.next();
                    Intrinsics.checkNotNullExpressionValue(next, "next(...)");
                    String str = next;
                    if (Intrinsics.areEqual(str, StringUtils.LF)) {
                        sb.append(StringUtils.LF);
                    } else {
                        sb.append(StringUtils.LF).append(str).append(". ");
                        if (webSocketThread.isProduct) {
                            if (vorResult != null && !speechConferenceMix2.getIsSingle()) {
                                sb.append(webSocketThread.roleNameMap.get(vorResult.getVorRole()));
                            }
                        } else if (vorResult != null && !speechConferenceMix2.getIsSingle()) {
                            sb.append(webSocketThread.roleNameMap.get(vorResult.getVorRole()));
                        }
                        sb.append(StringUtils.LF);
                    }
                }
            }
            this = webSocketThread;
        }
        WebSocketThread webSocketThread2 = this;
        Logger.INSTANCE.e(webSocketThread2.TAG, "outPutAllRoleResult: " + ((Object) sb));
        webSocketThread2.mListener.onMessageFull(sb.toString());
    }
}
