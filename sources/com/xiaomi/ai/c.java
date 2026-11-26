package com.xiaomi.ai;

import android.content.Context;
import android.location.Location;
import android.media.AudioManager;
import android.media.AudioRecord;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.text.SpannableStringBuilder;
import android.text.TextUtils;
import com.miui.common.tool.AudioUtils;
import com.miui.notes.ai.stat.AITextWidgetStat;
import com.xiaomi.ai.api.common.Instruction;
import com.xiaomi.ai.mibrain.GetAnonymousAuthorizationInterface;
import com.xiaomi.ai.mibrain.MibrainException;
import com.xiaomi.ai.mibrain.MibrainOauthManager;
import com.xiaomi.ai.mibrain.MibrainRequest;
import com.xiaomi.ai.mibrain.MibrainRequestListener;
import com.xiaomi.ai.mibrain.MibrainRequestParams;
import com.xiaomi.ai.mibrain.MibrainRequestParamsBuilder;
import com.xiaomi.ai.mibrain.Mibrainsdk;
import com.xiaomi.ai.utils.Log;
import com.xiaomi.ai.utils.UploadHelper;
import com.xiaomi.onetrack.api.ah;
import java.util.Locale;
import java.util.TimeZone;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.atomic.AtomicBoolean;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.SystemProperties;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/* JADX INFO: loaded from: classes4.dex */
public class c extends SpeechEngine {
    private static final String b = "MiSpeechSDK:MiAiEngine";
    private static final int f = 4;
    private static final int g = 10;
    private static final int h = 5;
    private com.xiaomi.ai.streamplayer.k.a A;
    GetAnonymousAuthorizationInterface a;
    private SpannableStringBuilder c;
    private int d;
    private String e;
    private com.xiaomi.ai.streamplayer.k i;
    private volatile boolean j;
    private a k;
    private AudioManager l;
    private MibrainRequest m;
    private e n;
    private final Object o;
    private final Object p;
    private String q;
    private volatile SpeechEngine.ParamBuilder r;
    private m s;
    private int t;
    private String u;
    private String v;
    private String w;
    private int x;
    private ExecutorService y;
    private AtomicBoolean z;

    /* JADX INFO: Access modifiers changed from: private */
    class a extends Thread {
        private MibrainRequest b;
        private boolean c;
        private SpeechEngine.ParamBuilder d;

        a(MibrainRequest mibrainRequest, SpeechEngine.ParamBuilder paramBuilder) {
            this.b = mibrainRequest;
            this.d = paramBuilder;
        }

        private void a(b bVar) {
            if (bVar == null || bVar.a == null) {
                return;
            }
            float fA = com.xiaomi.ai.utils.n.a(bVar.a, bVar.a.length);
            if (this.d.needAsr) {
                c.this.notifyAsrBufferReceived(bVar.a);
                c.this.notifyAsrRmsChanged(fA);
            }
            if (this.d.needVpRec) {
                c.this.notifyVoiceRecBufferReceived(bVar.a);
                c.this.notifyVoiceRecRmsChanged(fA);
            }
            if (this.d.needVpReg) {
                c.this.notifyVoiceRegBufferReceived(bVar.a);
                c.this.notifyVoiceRegRmsChanged(fA);
            }
        }

        private void a(e eVar) {
            while (eVar.c()) {
                a(eVar.b());
            }
            eVar.a(c.this.new b(new byte[0], 0, 0, true));
            eVar.b.clear();
        }

        private boolean a() {
            if (this.d.asrRequest != null && this.d.asrRequest.d == AsrRequest.DataInputMode.DATA_INPUT_MODE_RECORDER) {
                return true;
            }
            if (this.d.voiceprintRegisterRequest == null || this.d.voiceprintRegisterRequest.mode != VoiceprintRegisterRequest.DataInputMode.DATA_INPUT_MODE_RECORDER) {
                return this.d.voiceprintRecognizeRequest != null && this.d.voiceprintRecognizeRequest.mode == VoiceprintRecognizeRequest.DataInputMode.DATA_INPUT_MODE_RECORDER;
            }
            return true;
        }

        private void b(e eVar) {
            c cVar;
            String str;
            synchronized (c.class) {
                if (!isInterrupted()) {
                    c.this.a(com.xiaomi.ai.utils.h.a("recordingToBuffer", -16711936));
                    int minBufferSize = AudioRecord.getMinBufferSize(16000, 16, 2);
                    AudioRecord audioRecord = new AudioRecord(1, 16000, 16, 2, minBufferSize);
                    if (audioRecord.getState() == 1) {
                        try {
                            audioRecord.startRecording();
                            short[] sArr = new short[minBufferSize];
                            while (true) {
                                try {
                                    try {
                                        if (c.this.j && !isInterrupted() && eVar.c()) {
                                            int i = audioRecord.read(sArr, 0, minBufferSize);
                                            if (i <= 0) {
                                                c.this.j = false;
                                                Log.MiLogE(c.b, "audio read size =0");
                                                try {
                                                    audioRecord.stop();
                                                    break;
                                                } catch (IllegalStateException e) {
                                                    c.this.a("IllegalStateException: ", e);
                                                    break;
                                                }
                                            }
                                            byte[] bArrA = com.xiaomi.ai.utils.e.a(sArr, i);
                                            b bVar = c.this.new b(bArrA, 0, bArrA.length, false);
                                            eVar.a(bVar);
                                            a(bVar);
                                        } else {
                                            eVar.a(c.this.new b(new byte[0], 0, 0, true));
                                            try {
                                                audioRecord.stop();
                                                break;
                                            } catch (IllegalStateException e2) {
                                                e = e2;
                                                cVar = c.this;
                                                str = "IllegalStateException: ";
                                                cVar.a(str, e);
                                            }
                                        }
                                    } catch (Throwable th) {
                                        try {
                                            audioRecord.stop();
                                        } catch (IllegalStateException e3) {
                                            c.this.a("IllegalStateException: ", e3);
                                        }
                                        audioRecord.release();
                                        throw th;
                                    }
                                } catch (Exception e4) {
                                    Log.MiLogW(c.b, "InterruptedException at recordingToBuffer: ", e4);
                                    try {
                                        audioRecord.stop();
                                    } catch (IllegalStateException e5) {
                                        e = e5;
                                        cVar = c.this;
                                        str = "IllegalStateException: ";
                                        cVar.a(str, e);
                                    }
                                }
                            }
                            audioRecord.release();
                        } catch (IllegalStateException e6) {
                            synchronized (c.this.o) {
                                if (!c()) {
                                    c.this.a("IllegalStateException: ", e6);
                                    audioRecord.release();
                                    SpeechError speechError = new SpeechError(SpeechEngine.ProcessStage.STAGE_REQUESTING);
                                    speechError.errMsg = e6.toString();
                                    speechError.errCode = 11;
                                    speechError.errType = 2;
                                    c.this.notifyError(this.d, speechError);
                                }
                            }
                        }
                        return;
                    }
                    synchronized (c.this.o) {
                        if (!c()) {
                            c.this.a("recorder init failed");
                            SpeechError speechError2 = new SpeechError(SpeechEngine.ProcessStage.STAGE_REQUESTING);
                            speechError2.errCode = 5;
                            speechError2.errType = 2;
                            speechError2.errMsg = "Fail to init recorder";
                            c.this.notifyError(this.d, speechError2);
                        }
                    }
                    throw th;
                }
            }
        }

        private boolean b() {
            if (this.d.asrRequest != null && this.d.asrRequest.d == AsrRequest.DataInputMode.DATA_INPUT_MODE_BUFFER) {
                return true;
            }
            if (this.d.voiceprintRegisterRequest == null || this.d.voiceprintRegisterRequest.mode != VoiceprintRegisterRequest.DataInputMode.DATA_INPUT_MODE_BUFFER) {
                return this.d.voiceprintRecognizeRequest != null && this.d.voiceprintRecognizeRequest.mode == VoiceprintRecognizeRequest.DataInputMode.DATA_INPUT_MODE_BUFFER;
            }
            return true;
        }

        private boolean c() {
            return (c.this.r == this.d && c.this.m == this.b) ? false : true;
        }

        private boolean d() {
            synchronized (c.this.o) {
                if (c()) {
                    return true;
                }
                c.this.notifyAsrReadyForSpeech();
                return false;
            }
        }

        private boolean e() {
            synchronized (c.this.o) {
                if (c()) {
                    return true;
                }
                c.this.notifyAsrBeginningOfSpeech();
                return false;
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void f() {
            synchronized (c.this.o) {
                boolean z = true;
                if (this.c || c()) {
                    StringBuilder sbAppend = new StringBuilder("call failed isNotifyEnd ").append(this.c).append("mMibrainRequest==request");
                    if (this.b != c.this.m) {
                        z = false;
                    }
                    Log.MiLogW(c.b, sbAppend.append(z).toString());
                } else {
                    c.this.notifyAsrEndOfSpeech();
                    this.c = true;
                }
            }
        }

        private boolean g() {
            synchronized (c.this.o) {
                if (c()) {
                    return true;
                }
                c.this.notifyVoiceRecBeginningOfSpeech();
                return false;
            }
        }

        private void h() {
            synchronized (c.this.o) {
                boolean z = true;
                if (this.c || c()) {
                    StringBuilder sbAppend = new StringBuilder("call failed isNotifyEnd ").append(this.c).append("mMibrainRequest==request");
                    if (this.b != c.this.m) {
                        z = false;
                    }
                    Log.MiLogW(c.b, sbAppend.append(z).toString());
                } else {
                    c.this.notifyVoiceRecEndOfSpeech();
                    this.c = true;
                }
            }
        }

        private boolean i() {
            synchronized (c.this.o) {
                if (c()) {
                    return true;
                }
                c.this.notifyVoiceRegBeginningOfSpeech();
                return false;
            }
        }

        private void j() {
            synchronized (c.this.o) {
                boolean z = true;
                if (this.c || c()) {
                    StringBuilder sbAppend = new StringBuilder("call failed isNotifyEnd ").append(this.c).append("mMibrainRequest==request");
                    if (this.b != c.this.m) {
                        z = false;
                    }
                    Log.MiLogE(c.b, sbAppend.append(z).toString());
                } else {
                    c.this.notifyVoiceRegEndOfSpeech();
                    this.c = true;
                }
            }
        }

        @Override // java.lang.Thread, java.lang.Runnable
        public void run() {
            e eVar;
            if (isInterrupted() || (eVar = c.this.n) == null) {
                return;
            }
            if (this.d.needAsr && d()) {
                return;
            }
            if (this.d.needAsr && e()) {
                return;
            }
            if (this.d.needVpRec && g()) {
                return;
            }
            if (this.d.needVpReg && i()) {
                return;
            }
            try {
                if (a()) {
                    eVar.d();
                }
                if (a()) {
                    b(eVar);
                } else {
                    if (!b()) {
                        throw new IllegalArgumentException("date input mode error");
                    }
                    a(eVar);
                }
                if (!b()) {
                    eVar.e();
                }
                if (this.d.needAsr) {
                    f();
                }
                if (this.d.needVpRec) {
                    h();
                }
                if (this.d.needVpReg) {
                    j();
                }
            } catch (Throwable th) {
                if (!b()) {
                    eVar.e();
                }
                throw th;
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    class b {
        byte[] a;
        boolean b;
        boolean c;
        EnumC0117c d;
        String e;

        b(EnumC0117c enumC0117c) {
            this.d = enumC0117c;
        }

        b(String str, EnumC0117c enumC0117c) {
            this.e = str;
            this.d = enumC0117c;
        }

        b(byte[] bArr, int i, int i2, boolean z) {
            this.a = bArr;
            if (bArr == null) {
                throw new NullPointerException("addBuffer params error, buffer is null ");
            }
            if (i2 < 0 || i < 0) {
                throw new IllegalArgumentException("addBuffer params error,offset < 0 or len <0");
            }
            if (i2 + i > bArr.length) {
                throw new IllegalArgumentException("addBuffer params error,offset + len > bytes.length");
            }
            byte[] bArr2 = new byte[i2];
            this.a = bArr2;
            System.arraycopy(bArr, i, bArr2, 0, i2);
            this.b = z;
            this.d = EnumC0117c.DATA;
        }

        b(byte[] bArr, int i, int i2, boolean z, boolean z2) {
            this.a = bArr;
            if (bArr == null) {
                throw new NullPointerException("addBuffer params error, buffer is null ");
            }
            if (i2 < 0 || i < 0) {
                throw new IllegalArgumentException("addBuffer params error,offset < 0 or len <0");
            }
            if (i2 + i > bArr.length) {
                throw new IllegalArgumentException("addBuffer params error,offset + len > bytes.length");
            }
            byte[] bArr2 = new byte[i2];
            this.a = bArr2;
            System.arraycopy(bArr, i, bArr2, 0, i2);
            this.b = z;
            this.c = z2;
            this.d = EnumC0117c.DATA;
        }
    }

    /* JADX INFO: renamed from: com.xiaomi.ai.c$c, reason: collision with other inner class name */
    private enum EnumC0117c {
        DATA,
        FLAG_ABANDON_START,
        FLAG_ABANDON_END,
        NLP_DATA,
        VAD_BEGIN
    }

    /* JADX INFO: Access modifiers changed from: private */
    class d implements MibrainRequestListener {
        private MibrainRequest b;
        private a c;
        private SpeechEngine.ParamBuilder e;
        private String g;
        private boolean d = false;
        private volatile SpeechEngine.ProcessStage f = SpeechEngine.ProcessStage.STAGE_REQUESTING;

        d(SpeechEngine.ParamBuilder paramBuilder, MibrainRequest mibrainRequest) {
            this.b = mibrainRequest;
            this.e = paramBuilder;
        }

        private SpeechError a(JSONObject jSONObject) {
            String str;
            JSONObject jSONObject2;
            int i;
            SpeechError speechError;
            String strOptString;
            if (this.e.needEvent && this.e.eventRequest != null) {
                return null;
            }
            try {
                String string = jSONObject.getJSONObject("meta").getString("type");
                if ("ERROR".equals(string)) {
                    JSONObject jSONObject3 = jSONObject.getJSONObject(ah.H);
                    int i2 = jSONObject3.getInt("error_code");
                    strOptString = jSONObject3.getString("error_msg");
                    speechError = new SpeechError(this.f);
                    speechError.errType = 1;
                    speechError.errCode = i2;
                } else {
                    if ("TRANSACTION_BEGIN".equals(string) || "RESULT_VOR_FINAL".equals(string) || "RESULT_VOR_QUERY".equals(string) || "RESULT_VOR_DEL".equals(string)) {
                        str = "RESULT_VOR_FINAL";
                    } else {
                        str = "RESULT_VOR_FINAL";
                        if (!"RESULT_VOR_REG".equals(string) && !"RESULT_VOR_REG_PARTIAL".equals(string) && !"RESULT_VOR_REG_FINAL".equals(string) && !"RESULT_ASR_PARTIAL".equals(string) && !"RESULT_ASR_FINAL".equals(string) && !"RESULT_NLP".equals(string) && !"RESULT_TTS_BEGIN".equals(string) && !"RESULT_TTS_END".equals(string) && !"TRANSACTION_END".equals(string) && !"RESULT_FILE_STORED".equals(string)) {
                            Log.MiLogE(c.b, "unkown type result = " + jSONObject);
                            return null;
                        }
                    }
                    boolean z = false;
                    switch (k.a[this.f.ordinal()]) {
                        case 1:
                            if (!"TRANSACTION_BEGIN".equals(string)) {
                                z = true;
                            }
                            break;
                        case 2:
                            if (!"RESULT_ASR_PARTIAL".equals(string) && !"RESULT_ASR_FINAL".equals(string) && !"RESULT_FILE_STORED".equals(string)) {
                                z = true;
                            }
                            break;
                        case 3:
                            boolean z2 = !"RESULT_NLP".equals(string);
                            if (!this.d) {
                                z = z2;
                            } else {
                                this.f = SpeechEngine.ProcessStage.STAGE_TTS;
                            }
                            break;
                        case 4:
                            if (!str.equals(string)) {
                                z = true;
                            }
                            break;
                        case 5:
                            if (!"RESULT_VOR_REG_FINAL".equals(string) && !"RESULT_VOR_REG_PARTIAL".equals(string) && !"TRANSACTION_END".equals(string)) {
                                z = true;
                            }
                            break;
                        case 6:
                            if (!"RESULT_TTS_BEGIN".equals(string) && !"RESULT_TTS_END".equals(string) && !"TRANSACTION_END".equals(string)) {
                                z = true;
                            }
                            break;
                        case 7:
                            if (!"RESULT_VOR_DEL".equals(string)) {
                                z = true;
                            }
                            break;
                        case 8:
                            if (!"RESULT_VOR_QUERY".equals(string)) {
                                z = true;
                            }
                            break;
                    }
                    if (z) {
                        SpeechError speechError2 = new SpeechError(this.f);
                        speechError2.errType = 2;
                        speechError2.errCode = 3;
                        speechError2.errMsg = "Internal state error";
                        return speechError2;
                    }
                    if (this.f != SpeechEngine.ProcessStage.STAGE_NLP || (i = (jSONObject2 = jSONObject.getJSONObject(ah.H).getJSONObject("status")).getInt("code")) == 200) {
                        return null;
                    }
                    speechError = new SpeechError(this.f);
                    speechError.errType = 1;
                    speechError.errCode = i;
                    strOptString = jSONObject2.optString("error_details");
                }
                speechError.errMsg = strOptString;
                return speechError;
            } catch (JSONException e) {
                c.this.a("JSONException: ", e);
                SpeechError speechError3 = new SpeechError(this.f);
                speechError3.errType = 2;
                speechError3.errCode = 2;
                speechError3.errMsg = e.toString();
                return speechError3;
            }
        }

        /* JADX WARN: Code duplicated, block: B:315:0x0553 A[Catch: all -> 0x055c, TryCatch #16 {, blocks: (B:311:0x054b, B:313:0x0551, B:315:0x0553), top: B:339:0x054b }] */
        /* JADX WARN: Code duplicated, block: B:339:0x054b A[EXC_TOP_SPLITTER, SYNTHETIC] */
        /* JADX WARN: Multi-variable type inference failed */
        /* JADX WARN: Type inference fix 'apply assigned field type' failed
        java.lang.UnsupportedOperationException: ArgType.getObject(), call class: class jadx.core.dex.instructions.args.ArgType$UnknownArg
        	at jadx.core.dex.instructions.args.ArgType.getObject(ArgType.java:596)
        	at jadx.core.dex.attributes.nodes.ClassTypeVarsAttr.getTypeVarsMapFor(ClassTypeVarsAttr.java:35)
        	at jadx.core.dex.nodes.utils.TypeUtils.replaceClassGenerics(TypeUtils.java:177)
        	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.insertExplicitUseCast(FixTypesVisitor.java:397)
        	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.tryFieldTypeWithNewCasts(FixTypesVisitor.java:359)
        	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.applyFieldType(FixTypesVisitor.java:309)
        	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.visit(FixTypesVisitor.java:94)
         */
        private void a(MibrainRequest mibrainRequest, String str) {
            SpeechError speechError;
            c cVar;
            StringBuilder sbAppend;
            if (b(mibrainRequest)) {
                return;
            }
            try {
                JSONObject jSONObject = new JSONObject(str);
                a(mibrainRequest, jSONObject);
                SpeechError speechErrorA = a(jSONObject);
                if (speechErrorA != null) {
                    c.this.a(com.xiaomi.ai.utils.h.a("Text received: ERROR!!!"));
                    c.this.a(com.xiaomi.ai.utils.h.a(com.xiaomi.ai.utils.a.a(str), -65536));
                    synchronized (c.this.o) {
                        if (!b(mibrainRequest)) {
                            c.this.notifyError(this.e, speechErrorA);
                        }
                    }
                    return;
                }
                c.this.a(com.xiaomi.ai.utils.h.a("Text received: "));
                c.this.a(com.xiaomi.ai.utils.a.a(str));
                if (c.this.mResultParser == null) {
                    c.this.mResultParser = new SpeechEngine.ResultParser();
                }
                JSONObject jSONObject2 = jSONObject.getJSONObject("meta");
                String string = jSONObject2.getString("type");
                if ("TRANSACTION_BEGIN".equals(string)) {
                    synchronized (c.this.o) {
                        if (b(mibrainRequest)) {
                            return;
                        }
                        this.f = c.this.getNextStage(this.e, this.f);
                        cVar = c.this;
                        sbAppend = new StringBuilder("TRANSACTION_BEGIN stage: ").append(this.f);
                    }
                } else {
                    if ("RESULT_ASR_PARTIAL".equals(string)) {
                        SpeechResult asrResult = c.this.mResultParser.getAsrResult(jSONObject);
                        synchronized (c.this.o) {
                            if (asrResult.query == null) {
                                SpeechError speechError2 = new SpeechError(SpeechEngine.ProcessStage.STAGE_ASR);
                                speechError2.errCode = 12;
                                speechError2.errType = 2;
                                c.this.notifyError(this.e, speechError2);
                            } else if (!b(mibrainRequest)) {
                                c.this.notifyAsrParticalResults(asrResult);
                                return;
                            }
                            return;
                        }
                    }
                    if ("RESULT_ASR_FINAL".equals(string)) {
                        SpeechResult asrResult2 = c.this.mResultParser.getAsrResult(jSONObject);
                        synchronized (c.this.o) {
                            if (asrResult2.query == null) {
                                SpeechError speechError3 = new SpeechError(SpeechEngine.ProcessStage.STAGE_ASR);
                                speechError3.errCode = 12;
                                speechError3.errType = 2;
                                c.this.notifyError(this.e, speechError3);
                            } else if (!b(mibrainRequest)) {
                                this.d = TextUtils.isEmpty(asrResult2.query);
                                c.this.notifyAsrResults(asrResult2);
                                this.f = c.this.getNextStage(this.e, this.f);
                                cVar = c.this;
                                sbAppend = new StringBuilder("TRESULT_ASR_FINAL stage: ").append(this.f);
                            }
                            return;
                        }
                    }
                    SpeechResult nlpResult = null;
                    nlpResult = null;
                    if ("RESULT_FILE_STORED".equals(string)) {
                        String strOptString = jSONObject2.optString("request_id", null);
                        synchronized (c.this.o) {
                            try {
                                if (strOptString == null) {
                                    SpeechError speechError4 = new SpeechError(SpeechEngine.ProcessStage.STAGE_ASR);
                                    speechError4.errCode = 12;
                                    speechError4.errType = 2;
                                    c.this.notifyError(this.e, speechError4);
                                } else if (!b(mibrainRequest)) {
                                    c.this.notifyAsrFileStored(strOptString, jSONObject.toString());
                                    this.f = c.this.getNextStage(this.e, this.f);
                                    cVar = c.this;
                                    sbAppend = new StringBuilder("RESULT_FILE_STORED stage: ").append(this.f);
                                }
                                return;
                            } catch (Throwable th) {
                                throw th;
                            }
                        }
                    }
                    byte b = 0;
                    if (!"RESULT_NLP".equals(string)) {
                        if ("RESULT_TTS_BEGIN".equals(string)) {
                            for (int i = 0; i < 10 && c.this.l.isBluetoothScoOn(); i++) {
                                synchronized (c.this.o) {
                                    c.this.o.wait(100L);
                                }
                            }
                            String strOptString2 = jSONObject.getJSONObject("meta").getJSONObject("tts").optString("codec");
                            synchronized (c.this.o) {
                                if (b(mibrainRequest)) {
                                    return;
                                }
                                if (c.this.z.get()) {
                                    c.this.i = new com.xiaomi.ai.streamplayer.k(c.this.mStreamType);
                                    c.this.i.a(strOptString2);
                                    c.this.i.a(c.this.A);
                                    c.this.i.a(c.this.x);
                                    c.this.i.a();
                                }
                                c.this.notifyTtsTransStart();
                                return;
                            }
                        }
                        if ("RESULT_TTS_END".equals(string) || (this.f == SpeechEngine.ProcessStage.STAGE_TTS && "TRANSACTION_END".equals(string))) {
                            synchronized (c.this.o) {
                                if (b(mibrainRequest)) {
                                    return;
                                }
                                if (c.this.i != null) {
                                    c.this.i.b();
                                }
                                this.f = c.this.getNextStage(this.e, this.f);
                                c.this.a("RESULT_TTS_END stage: " + this.f);
                                boolean z = "RESULT_TTS_END".equals(string) || this.f == SpeechEngine.ProcessStage.STAGE_TTS;
                                synchronized (c.this.o) {
                                    if (b(mibrainRequest)) {
                                        return;
                                    }
                                    c.this.notifyTtsTransEnd(z);
                                    return;
                                }
                            }
                        }
                        if ("RESULT_TTS_URL".equals(string)) {
                            synchronized (c.this.o) {
                                if (b(mibrainRequest)) {
                                    return;
                                }
                                c.this.notifyTtsGotUrl(jSONObject.getJSONObject(ah.H).toString());
                                return;
                            }
                        }
                        if ("RESULT_VOR_REG_FINAL".equals(string)) {
                            SpeechResult voiceRegResult = c.this.mResultParser.getVoiceRegResult(jSONObject);
                            synchronized (c.this.o) {
                                if (b(mibrainRequest)) {
                                    return;
                                }
                                c.this.notifyVoiceRegResults(voiceRegResult);
                                this.f = c.this.getNextStage(this.e, this.f);
                                return;
                            }
                        }
                        if ("RESULT_VOR_FINAL".equals(string)) {
                            SpeechResult voiceRecResult = c.this.mResultParser.getVoiceRecResult(jSONObject);
                            synchronized (c.this.o) {
                                if (b(mibrainRequest)) {
                                    return;
                                }
                                c.this.notifyVoiceRecResults(voiceRecResult);
                                this.f = c.this.getNextStage(this.e, this.f);
                                return;
                            }
                        }
                        if ("RESULT_VOR_REG_PARTIAL".equals(string)) {
                            SpeechResult voiceRegResult2 = c.this.mResultParser.getVoiceRegResult(jSONObject);
                            synchronized (c.this.o) {
                                if (b(mibrainRequest)) {
                                    return;
                                }
                                c.this.notifyVoiceRegResults(voiceRegResult2);
                                this.f = c.this.getNextStage(this.e, this.f);
                                return;
                            }
                        }
                        if ("RESULT_VOR_DEL".equals(string)) {
                            SpeechResult voiceDelResult = c.this.mResultParser.getVoiceDelResult(jSONObject);
                            synchronized (c.this.o) {
                                if (b(mibrainRequest)) {
                                    return;
                                }
                                c.this.notifyVoiceDelResults(voiceDelResult);
                                this.f = c.this.getNextStage(this.e, this.f);
                                return;
                            }
                        }
                        if ("RESULT_VOR_QUERY".equals(string)) {
                            SpeechResult voiceQryResult = c.this.mResultParser.getVoiceQryResult(jSONObject);
                            synchronized (c.this.o) {
                                if (b(mibrainRequest)) {
                                    return;
                                }
                                c.this.notifyVoiceQryResults(voiceQryResult);
                                this.f = c.this.getNextStage(this.e, this.f);
                                return;
                            }
                        }
                        if ("SERVICE_EVENT".equals(string)) {
                            if (jSONObject2.has("event_level")) {
                                this.g = jSONObject2.getString("event_level");
                            }
                            synchronized (c.this.o) {
                                if (b(mibrainRequest)) {
                                    return;
                                }
                                c.this.notifyServiceEvent(new ServiceEvent(this.g, jSONObject.getJSONObject(ah.H)));
                                return;
                            }
                        }
                        if ("INSTRUCTION".equals(string)) {
                            Instruction[] instruction = c.this.mResultParser.getInstruction(jSONObject);
                            try {
                                if (jSONObject.getJSONObject(ah.H).has("answer")) {
                                    nlpResult = c.this.mResultParser.getNlpResult(jSONObject);
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            synchronized (c.this.o) {
                                if (instruction != null) {
                                    c.this.notifyInstruction(instruction, nlpResult);
                                }
                            }
                            return;
                        }
                        return;
                    }
                    SpeechResult nlpResult2 = c.this.mResultParser.getNlpResult(jSONObject);
                    c.this.q = nlpResult2.sessionId;
                    Instruction[] instruction2 = jSONObject.getJSONObject(ah.H).has("instructions") ? c.this.mResultParser.getInstruction(jSONObject) : null;
                    byte b2 = instruction2 != null;
                    if (this.e.nlpRequest == null || !this.e.nlpRequest.discardInstructionOfNlpResult) {
                        b = b2;
                    }
                    synchronized (c.this.o) {
                        if (b(mibrainRequest)) {
                            return;
                        }
                        if (b != 0) {
                            c.this.notifyInstruction(instruction2, nlpResult2);
                        } else {
                            c.this.notifyNlpResults(nlpResult2);
                        }
                        this.f = c.this.getNextStage(this.e, this.f);
                        cVar = c.this;
                        sbAppend = new StringBuilder("RESULT_NLP stage: ").append(this.f);
                    }
                }
                cVar.a(sbAppend.toString());
                return;
            } catch (Exception e2) {
                c.this.a("Exception in onTextMessage: ", e2);
                speechError = new SpeechError(this.f);
                speechError.errType = 2;
                speechError.errCode = 1;
                speechError.errMsg = e2.toString();
                synchronized (c.this.o) {
                    if (!b(mibrainRequest)) {
                        c.this.notifyError(this.e, speechError);
                    }
                }
            }
            c.this.a("Exception in onTextMessage: ", e2);
            speechError = new SpeechError(this.f);
            speechError.errType = 2;
            speechError.errCode = 1;
            speechError.errMsg = e2.toString();
            synchronized (c.this.o) {
                if (!b(mibrainRequest)) {
                    c.this.notifyError(this.e, speechError);
                }
            }
        }

        private void a(MibrainRequest mibrainRequest, JSONObject jSONObject) {
            try {
                a aVar = this.c;
                if (aVar == null) {
                    return;
                }
                String string = jSONObject.getJSONObject("meta").getString("type");
                if ("RESULT_ASR_VAD".equals(string)) {
                    if (jSONObject.getJSONObject(ah.H).getBoolean("is_final")) {
                        synchronized (c.this.o) {
                            if (!b(mibrainRequest)) {
                                aVar.f();
                            }
                        }
                        return;
                    }
                    return;
                }
                if (!"RESULT_ASR_FINAL".equals(string) && !"RESULT_FILE_STORED".equals(string)) {
                    return;
                }
                synchronized (c.this.o) {
                    if (!b(mibrainRequest)) {
                        aVar.f();
                    }
                }
                return;
                c.this.a("JSONException: ", e);
            } catch (JSONException e) {
                c.this.a("JSONException: ", e);
            }
        }

        private void a(MibrainRequest mibrainRequest, byte[] bArr) {
            synchronized (c.this.o) {
                if (!b(mibrainRequest)) {
                    com.xiaomi.ai.streamplayer.k kVar = c.this.i;
                    if (bArr != null) {
                        if (kVar != null) {
                            kVar.a(bArr, 0, bArr.length);
                        }
                    } else {
                        SpeechError speechError = new SpeechError(this.f);
                        speechError.errType = 2;
                        speechError.errCode = 6;
                        speechError.errMsg = "resolveBinaryResult binary is null";
                        c.this.notifyError(this.e, speechError);
                    }
                }
            }
        }

        private boolean a(MibrainRequest mibrainRequest) {
            if (mibrainRequest == this.b) {
                return true;
            }
            SpeechError speechError = new SpeechError(this.f);
            speechError.errType = 2;
            speechError.errCode = -1;
            speechError.errMsg = "FATAL ERROR isResponseCorrect check failed !!";
            c.this.notifyError(this.e, speechError);
            return false;
        }

        private boolean b(MibrainRequest mibrainRequest) {
            boolean z;
            synchronized (c.this.o) {
                z = c.this.hasError() || c.this.m != mibrainRequest;
            }
            return z;
        }

        public String a() {
            return this.g;
        }

        public void a(a aVar) {
            this.c = aVar;
        }

        @Override // com.xiaomi.ai.mibrain.MibrainRequestListener
        public void onEventTrack(String str) {
            if (c.this.mEventTrackListener != null) {
                c.this.mEventTrackListener.onEventTrack(str);
            } else {
                Log.MiLogE(c.b, "no Need onEventTrack callback ,skip");
            }
        }

        @Override // com.xiaomi.ai.mibrain.MibrainRequestListener
        public void onInstruction(MibrainRequest mibrainRequest, String str) {
            if (TextUtils.isEmpty(str)) {
                return;
            }
            a(mibrainRequest, str);
        }

        @Override // com.xiaomi.ai.mibrain.MibrainRequestListener
        public String onNeedUpdateToken() {
            if (c.this.mUpdateTokenCallback != null) {
                Log.MiLogE(c.b, "Need UpdateToken");
                return c.this.mUpdateTokenCallback.onNeedUpdateToken();
            }
            Log.MiLogE(c.b, "no Need UpdateToken callback ,skip");
            return null;
        }

        @Override // com.xiaomi.ai.mibrain.MibrainRequestListener
        public void onRequestASRResult(MibrainRequest mibrainRequest, String str, boolean z) {
            if (!a(mibrainRequest)) {
                Log.MiLogE(c.b, " isResponseCorrect = false ,mibrainRequest = " + mibrainRequest + " this.request = " + this.b);
            }
            if (TextUtils.isEmpty(str)) {
                return;
            }
            a(mibrainRequest, str);
        }

        @Override // com.xiaomi.ai.mibrain.MibrainRequestListener
        public void onRequestError(MibrainRequest mibrainRequest, String str, int i, int i2) {
            if (!a(mibrainRequest)) {
                Log.MiLogE(c.b, " isResponseCorrect = false ,mibrainRequest = " + mibrainRequest + " this.request = " + this.b);
            }
            synchronized (c.this.o) {
                if (b(mibrainRequest)) {
                    return;
                }
                SpeechError speechError = new SpeechError(this.f);
                if (i == 1) {
                    try {
                        JSONObject jSONObject = new JSONObject(str);
                        JSONObject jSONObject2 = jSONObject.getJSONObject(ah.H);
                        speechError.errCode = jSONObject2.getInt("error_code");
                        speechError.errMsg = jSONObject2.getString("error_msg");
                        speechError.requestId = jSONObject.getJSONObject("meta").getString("request_id");
                    } catch (JSONException e) {
                        speechError.errType = 2;
                        speechError.errCode = -9;
                        str = e.getMessage();
                        speechError.errMsg = str;
                    }
                    c.this.notifyError(this.e, speechError);
                }
                speechError.errType = i;
                speechError.errCode = i2;
                speechError.errMsg = str;
                c.this.notifyError(this.e, speechError);
            }
        }

        @Override // com.xiaomi.ai.mibrain.MibrainRequestListener
        public String onRequestGetToken(MibrainRequest mibrainRequest, boolean z) {
            if (!a(mibrainRequest)) {
                Log.MiLogE(c.b, " isResponseCorrect = false ,mibrainRequest = " + mibrainRequest + " this.request = " + this.b);
            }
            if (b(mibrainRequest)) {
                return null;
            }
            m mVar = c.this.s;
            if (mVar != null) {
                m.a aVarA = mVar.a(z);
                if (aVarA != null && aVarA.a != null) {
                    return aVarA.a;
                }
                synchronized (c.this.o) {
                    if (b(mibrainRequest)) {
                        return null;
                    }
                    SpeechError speechError = new SpeechError(this.f);
                    speechError.errType = 2;
                    speechError.errCode = -23;
                    speechError.errMsg = "oauth getToken failed code= " + (aVarA == null ? "" : Integer.valueOf(aVarA.b));
                    c.this.notifyError(this.e, speechError);
                }
            } else {
                Log.MiLogE(c.b, "onRequestGetToken aiOauthHelper = null ");
            }
            return null;
        }

        @Override // com.xiaomi.ai.mibrain.MibrainRequestListener
        public void onRequestNLpResult(MibrainRequest mibrainRequest, String str) {
            if (!a(mibrainRequest)) {
                Log.MiLogE(c.b, " isResponseCorrect = false ,mibrainRequest = " + mibrainRequest + " this.request = " + this.b);
            }
            if (TextUtils.isEmpty(str)) {
                return;
            }
            a(mibrainRequest, str);
        }

        @Override // com.xiaomi.ai.mibrain.MibrainRequestListener
        public void onRequestOtherResult(MibrainRequest mibrainRequest, String str) {
            if (!a(mibrainRequest)) {
                Log.MiLogE(c.b, " isResponseCorrect = false ,mibrainRequest = " + mibrainRequest + " this.request = " + this.b);
            }
            if (TextUtils.isEmpty(str)) {
                return;
            }
            a(mibrainRequest, str);
        }

        @Override // com.xiaomi.ai.mibrain.MibrainRequestListener
        public void onRequestSessionEnd(MibrainRequest mibrainRequest) {
            if (a(mibrainRequest)) {
                return;
            }
            Log.MiLogE(c.b, " isResponseCorrect = false ,mibrainRequest = " + mibrainRequest + " this.request = " + this.b);
        }

        @Override // com.xiaomi.ai.mibrain.MibrainRequestListener
        public void onRequestSessionStart(MibrainRequest mibrainRequest) {
            if (a(mibrainRequest)) {
                return;
            }
            Log.MiLogE(c.b, " isResponseCorrect = false ,mibrainRequest = " + mibrainRequest + " this.request = " + this.b);
        }

        @Override // com.xiaomi.ai.mibrain.MibrainRequestListener
        public void onRequestTTSResult(MibrainRequest mibrainRequest, String str, byte[] bArr, boolean z) {
            if (!a(mibrainRequest)) {
                Log.MiLogE(c.b, " isResponseCorrect = false ,mibrainRequest = " + mibrainRequest + " this.request = " + this.b);
            }
            if (TextUtils.isEmpty(str)) {
                a(mibrainRequest, bArr);
            } else {
                a(mibrainRequest, str);
            }
        }

        @Override // com.xiaomi.ai.mibrain.MibrainRequestListener
        public void onRequestVoiceprintDeleteResult(MibrainRequest mibrainRequest, String str) {
            if (!a(mibrainRequest)) {
                Log.MiLogE(c.b, " isResponseCorrect = false ,mibrainRequest = " + mibrainRequest + " this.request = " + this.b);
            }
            if (TextUtils.isEmpty(str)) {
                return;
            }
            a(mibrainRequest, str);
        }

        @Override // com.xiaomi.ai.mibrain.MibrainRequestListener
        public void onRequestVoiceprintQueryResult(MibrainRequest mibrainRequest, String str) {
            if (!a(mibrainRequest)) {
                Log.MiLogE(c.b, " isResponseCorrect = false ,mibrainRequest = " + mibrainRequest + " this.request = " + this.b);
            }
            if (TextUtils.isEmpty(str)) {
                return;
            }
            a(mibrainRequest, str);
        }

        @Override // com.xiaomi.ai.mibrain.MibrainRequestListener
        public void onRequestVoiceprintRecognizedResult(MibrainRequest mibrainRequest, String str) {
            if (!a(mibrainRequest)) {
                Log.MiLogE(c.b, " isResponseCorrect = false ,mibrainRequest = " + mibrainRequest + " this.request = " + this.b);
            }
            if (TextUtils.isEmpty(str)) {
                return;
            }
            a(mibrainRequest, str);
        }

        @Override // com.xiaomi.ai.mibrain.MibrainRequestListener
        public void onRequestVoiceprintRegisteredResult(MibrainRequest mibrainRequest, String str, boolean z) {
            if (!a(mibrainRequest)) {
                Log.MiLogE(c.b, " isResponseCorrect = false ,mibrainRequest = " + mibrainRequest + " this.request = " + this.b);
            }
            if (TextUtils.isEmpty(str)) {
                return;
            }
            a(mibrainRequest, str);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    class e {
        private MibrainRequest c;
        private SpeechEngine.ParamBuilder d;
        private boolean e;
        private Thread f;
        private d g;
        private BlockingQueue<b> b = new LinkedBlockingDeque();
        private Runnable h = new l(this);

        public e(SpeechEngine.ParamBuilder paramBuilder) {
            this.d = paramBuilder;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void a(b bVar) {
            this.b.add(bVar);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void a(d dVar) {
            this.g = dVar;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void a(MibrainRequest mibrainRequest) {
            this.c = mibrainRequest;
        }

        private boolean a() {
            return (this.d == c.this.r && c.this.m == this.c) ? false : true;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public b b() {
            try {
                b bVarTake = this.b.take();
                if (bVarTake.d == EnumC0117c.DATA) {
                    if (bVarTake.a != null && !this.e) {
                        int iAddAudioData = this.c.addAudioData(bVarTake.a, bVarTake.c, bVarTake.b);
                        if (bVarTake.b) {
                            Log.MiLogW(c.b, "add end flag");
                        }
                        if (iAddAudioData == -5) {
                            this.e = true;
                            return null;
                        }
                        if (iAddAudioData < 0) {
                            SpeechError speechError = new SpeechError(this.g.f);
                            speechError.errMsg = "sdk addAudioData failed ";
                            speechError.errCode = iAddAudioData;
                            speechError.errType = 2;
                            synchronized (c.this.o) {
                                if (!a()) {
                                    c.this.notifyError(this.d, speechError);
                                }
                            }
                            this.e = true;
                            return null;
                        }
                    } else if (bVarTake.b) {
                        Log.MiLogW(c.b, "add end flag");
                        int iAddAudioData2 = this.c.addAudioData(new byte[0], true);
                        if (iAddAudioData2 == -5) {
                            this.e = true;
                            return null;
                        }
                        if (iAddAudioData2 < 0) {
                            SpeechError speechError2 = new SpeechError(this.g.f);
                            speechError2.errMsg = "sdk addAudioData failed ";
                            speechError2.errCode = iAddAudioData2;
                            speechError2.errType = 2;
                            synchronized (c.this.o) {
                                if (!a()) {
                                    c.this.notifyError(this.d, speechError2);
                                }
                            }
                            this.e = true;
                            return null;
                        }
                    }
                } else if (bVarTake.d == EnumC0117c.NLP_DATA) {
                    if (bVarTake.e != null && !this.e) {
                        int iAddNlpData = this.c.addNlpData(bVarTake.e);
                        if (iAddNlpData == -5) {
                            this.e = true;
                            return null;
                        }
                        if (iAddNlpData < 0) {
                            SpeechError speechError3 = new SpeechError(this.g.f);
                            speechError3.errMsg = "sdk addNlpData failed ";
                            speechError3.errCode = iAddNlpData;
                            speechError3.errType = 2;
                            synchronized (c.this.o) {
                                if (!a()) {
                                    c.this.notifyError(this.d, speechError3);
                                }
                            }
                            this.e = true;
                            return null;
                        }
                    }
                } else if (bVarTake.d == EnumC0117c.VAD_BEGIN && !this.e) {
                    int iAddRawData = this.c.addRawData(UploadHelper.generateVADBeginJSON().getBytes(), 4, false, false);
                    if (iAddRawData == -5) {
                        this.e = true;
                        return null;
                    }
                    if (iAddRawData < 0) {
                        SpeechError speechError4 = new SpeechError(this.g.f);
                        speechError4.errMsg = "add VAD_BEGIN failed";
                        speechError4.errCode = iAddRawData;
                        speechError4.errType = 2;
                        synchronized (c.this.o) {
                            if (!a()) {
                                c.this.notifyError(this.d, speechError4);
                            }
                        }
                        this.e = true;
                        return null;
                    }
                }
                if (!bVarTake.b) {
                    return bVarTake;
                }
                this.e = true;
                return null;
            } catch (InterruptedException unused) {
                return null;
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public boolean c() {
            MibrainRequest mibrainRequest;
            return !Thread.currentThread().isInterrupted() && this.d == c.this.r && this.c.getErrorCode() == 0 && (mibrainRequest = this.c) != null && mibrainRequest == c.this.m && !this.e;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void d() {
            Thread thread = new Thread(this.h);
            this.f = thread;
            thread.start();
            Log.MiLogE(c.b, "RecorderCacheQueueHelper start ");
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void e() {
            this.b.add(c.this.new b(new byte[0], 0, 0, true));
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void f() {
            this.b.add(c.this.new b(new byte[0], 0, 0, true));
            this.e = true;
            Thread thread = this.f;
            if (thread != null) {
                thread.interrupt();
            }
        }
    }

    c(Context context, String str, String str2) {
        super(context, str, str2);
        this.d = 0;
        this.e = null;
        this.o = new Object();
        this.p = new Object();
        this.r = null;
        this.s = null;
        this.t = 0;
        this.a = new h(this);
        this.A = new j(this);
        this.l = (AudioManager) context.getSystemService(AITextWidgetStat.TYPE_AUDIO);
        this.x = 1;
        this.y = Executors.newSingleThreadExecutor();
        this.z = new AtomicBoolean(true);
    }

    private String a(boolean z) {
        m.a aVarA;
        try {
            m mVar = this.s;
            if (mVar == null || (aVarA = mVar.a(z)) == null || aVarA.a == null) {
                return null;
            }
            return aVarA.a;
        } catch (IllegalStateException e2) {
            e2.printStackTrace();
            return null;
        }
    }

    private String a(boolean z, String str) {
        m.a aVarA;
        if (str == null) {
            return null;
        }
        try {
            m mVar = this.s;
            if (mVar != null && (aVarA = mVar.a(z)) != null && aVarA.a != null) {
                return aVarA.a;
            }
        } catch (IllegalStateException e2) {
            e2.printStackTrace();
        }
        return null;
    }

    private void a(SpeechEngine.ParamBuilder paramBuilder) {
        if (this.t == ENGINE_AUTH_MIAI && this.mUseExternalOAuthToken == 0 && this.mOauthListener == null) {
            throw new IllegalStateException("please call setMiAIOauthCallbacks first !!");
        }
        if (this.t == ENGINE_AUTH_MIAO && this.mUseExternalOAuthToken == 0 && this.mOauthListener == null) {
            throw new IllegalStateException("please call setMiAOOauthCallbacks first !!");
        }
        if (this.t == ENGINE_AUTH_MIOT && this.mToken == null) {
            throw new IllegalStateException("please call updateMiotAuth first !!");
        }
        if (this.t == ENGINE_AUTH_TP && this.mToken == null) {
            throw new IllegalStateException("please call updateTPAuth first !!");
        }
        endSpeech();
        endVoiceprintRegister();
        endVoiceprintRecognize();
        if (paramBuilder.needTts && !paramBuilder.needAsr && !paramBuilder.needNlp && paramBuilder.ttsRequest.h != null) {
            paramBuilder.needExtraNlp = true;
            paramBuilder.nlpRequest = paramBuilder.ttsRequest.h;
        }
        if (!paramBuilder.needTts && paramBuilder.needAsr && !paramBuilder.needNlp && paramBuilder.asrRequest.n != null) {
            paramBuilder.needExtraNlp = true;
            paramBuilder.nlpRequest = paramBuilder.asrRequest.n;
        }
        synchronized (this.o) {
            if (paramBuilder.needAsr || paramBuilder.needVpRec || paramBuilder.needVpReg) {
                this.j = true;
            }
            this.n = new e(paramBuilder);
            this.r = paramBuilder;
        }
        new Thread(new i(this, paramBuilder)).start();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void a(CharSequence charSequence) {
        a(charSequence, (Throwable) null);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void a(CharSequence charSequence, Throwable th) {
        synchronized (this.p) {
            try {
                if (th != null) {
                    Log.MiLogE(b, charSequence.toString(), th);
                    this.c.append((CharSequence) (((Object) charSequence) + th.toString()));
                } else {
                    Log.MiLogD(b, charSequence.toString());
                    this.c.append(charSequence);
                }
                this.c.append((CharSequence) StringUtils.LF);
            } catch (Throwable th2) {
                throw th2;
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public MibrainRequestParams b(SpeechEngine.ParamBuilder paramBuilder) throws JSONException {
        JSONArray jSONArray;
        JSONObject jSONObject;
        int i;
        int i2;
        String str;
        NetworkInfo activeNetworkInfo = ((ConnectivityManager) this.mContext.getSystemService("connectivity")).getActiveNetworkInfo();
        MibrainRequestParamsBuilder mibrainRequestParamsBuilder = new MibrainRequestParamsBuilder();
        mibrainRequestParamsBuilder.setAppId(this.mAppId).setToken(this.mToken).setApiKey(this.u);
        if (this.mUseExternalOAuthToken == 0 && (this.t == ENGINE_AUTH_MIAI || this.t == ENGINE_AUTH_MIAO)) {
            mibrainRequestParamsBuilder.setToken(null);
        }
        mibrainRequestParamsBuilder.setProtocolVersion(this.mProtocolVersion);
        mibrainRequestParamsBuilder.setEventLevel(paramBuilder.mEventLevel);
        mibrainRequestParamsBuilder.setApiKey(paramBuilder.apiKey);
        mibrainRequestParamsBuilder.setConnectTimeOut(this.mConnectTimeout);
        mibrainRequestParamsBuilder.setReceiverTimeOut(this.mReceiveTimeout);
        mibrainRequestParamsBuilder.setUploadTimeout(this.mUploadTimeout);
        mibrainRequestParamsBuilder.setActiveTimeout(this.mActiveTimeout);
        mibrainRequestParamsBuilder.setEnv(this.d);
        mibrainRequestParamsBuilder.setAuthMode(this.t);
        mibrainRequestParamsBuilder.setNeedExtraNLP(paramBuilder.needExtraNlp ? 1 : 0);
        mibrainRequestParamsBuilder.setStartEventTrack(this.mStartEventTrack);
        mibrainRequestParamsBuilder.setEventTrackReceiver(this.mEventTrackReceiver);
        mibrainRequestParamsBuilder.setUseExternalOAuthToken(this.mUseExternalOAuthToken);
        if (paramBuilder.needWakeupData) {
            mibrainRequestParamsBuilder.setWakeupInfo(paramBuilder.wakeupInfoString);
            mibrainRequestParamsBuilder.setNeedWakeupData(true);
        }
        if (paramBuilder.needAsr) {
            mibrainRequestParamsBuilder.setChannels(1);
            mibrainRequestParamsBuilder.setMaxEmptyAudioTime((paramBuilder.asrRequest == null || !paramBuilder.asrRequest.b) ? 4 : 8);
            mibrainRequestParamsBuilder.setAudioBitNum(16);
            mibrainRequestParamsBuilder.setSampleRate(16000);
            mibrainRequestParamsBuilder.setAsrLang(paramBuilder.asrRequest.f);
            mibrainRequestParamsBuilder.setIsForSai(paramBuilder.asrRequest != null && paramBuilder.asrRequest.b);
            mibrainRequestParamsBuilder.setPreAsrTrack(paramBuilder.asrRequest.g);
            mibrainRequestParamsBuilder.setAsrRemoveEndPunc(paramBuilder.asrRequest.c);
            mibrainRequestParamsBuilder.setCodecMode(paramBuilder.asrRequest.h);
            mibrainRequestParamsBuilder.setOpusBitRates(paramBuilder.asrRequest.j);
            mibrainRequestParamsBuilder.setOpusFrameSize(paramBuilder.asrRequest.i);
            mibrainRequestParamsBuilder.setASRDialect(paramBuilder.asrRequest.k);
            mibrainRequestParamsBuilder.setASRDisableTimeout(paramBuilder.asrRequest.l ? 1 : 0);
            mibrainRequestParamsBuilder.setASRAudioMaxTime(paramBuilder.asrRequest.m);
            if (paramBuilder.asrRequest != null) {
                mibrainRequestParamsBuilder.setASRVendor(paramBuilder.asrRequest.a());
            }
        }
        if (paramBuilder.needTts) {
            new JSONObject().put("codec", AudioUtils.NOTE_MP3_DIR_NAME);
            mibrainRequestParamsBuilder.setTTSCodec(AudioUtils.NOTE_MP3_DIR_NAME);
            if (paramBuilder.ttsRequest != null) {
                mibrainRequestParamsBuilder.setTTSVendor(paramBuilder.ttsRequest.a());
                mibrainRequestParamsBuilder.setTTSTimeout(paramBuilder.ttsRequest.b);
                String packageName = this.mContext.getPackageName();
                if ("com.zmifi.phoneai".equals(packageName) || "com.xiaomi.translator".equals(packageName)) {
                    mibrainRequestParamsBuilder.setTTSLang(paramBuilder.ttsRequest.g);
                }
            }
        }
        if (paramBuilder.asrRequest != null) {
            mibrainRequestParamsBuilder.setUseInternalVadFlag(paramBuilder.asrRequest.a);
        }
        if (paramBuilder.asrRequest != null) {
            mibrainRequestParamsBuilder.setVadTypeLocal(paramBuilder.asrRequest.e == AsrRequest.VadMode.VAD_MODE_LOCAL ? MibrainRequestParamsBuilder.VadMode.VAD_MODE_LOCAL : MibrainRequestParamsBuilder.VadMode.VAD_MODE_CLOUD);
        }
        String strB = com.xiaomi.ai.utils.n.b(this.mContext);
        mibrainRequestParamsBuilder.setScopeData(com.xiaomi.ai.utils.n.b(strB));
        mibrainRequestParamsBuilder.setDeviceId(strB);
        JSONObject jSONObject2 = new JSONObject();
        if (activeNetworkInfo != null) {
            jSONObject2.put("network", activeNetworkInfo.getType() == 0 ? "mobile" : "wifi");
        }
        if (!TextUtils.isEmpty(this.mDeviceModel)) {
            jSONObject2.put("model", this.mDeviceModel);
        }
        jSONObject2.put("ip", "");
        if ((paramBuilder.needNlp || paramBuilder.needExtraNlp) && paramBuilder.nlpRequest != null) {
            if (paramBuilder.nlpRequest.latitude != null && paramBuilder.nlpRequest.longitude != null) {
                jSONObject2.put("latitude", paramBuilder.nlpRequest.latitude);
                jSONObject2.put("longitude", paramBuilder.nlpRequest.longitude);
            } else if (paramBuilder.nlpRequest.locationEnable) {
                Location locationA = com.xiaomi.ai.utils.b.a(this.mContext);
                if (locationA != null) {
                    jSONObject2.put("latitude", locationA.getLatitude());
                    jSONObject2.put("longitude", locationA.getLongitude());
                } else {
                    Log.MiLogE(b, "location is null");
                }
            }
            mibrainRequestParamsBuilder.setCustomNLPVersion(paramBuilder.nlpRequest.nlpVersion != null ? paramBuilder.nlpRequest.nlpVersion : "2.2");
            jSONObject2.put("time_zone", TimeZone.getDefault().getID());
            jSONObject2.put("time_zone_display_name", TimeZone.getDefault().getDisplayName());
            mibrainRequestParamsBuilder.setIsPendingSend(paramBuilder.nlpRequest.isPendingSend);
        }
        mibrainRequestParamsBuilder.setUserAgent(this.mUserAgent != null ? this.mUserAgent : System.getProperty(SystemProperties.HTTP_AGENT));
        mibrainRequestParamsBuilder.setDevice(jSONObject2.toString());
        if (paramBuilder.needTts && paramBuilder.ttsRequest != null) {
            mibrainRequestParamsBuilder.setTTSText(paramBuilder.ttsRequest.a);
            mibrainRequestParamsBuilder.setTTSTone(paramBuilder.ttsRequest.e);
            mibrainRequestParamsBuilder.setTTSVolume(paramBuilder.ttsRequest.d);
            mibrainRequestParamsBuilder.setTTSSpeed(paramBuilder.ttsRequest.c);
            mibrainRequestParamsBuilder.setTTSAsFileUrl(paramBuilder.ttsRequest.f ? 1 : 0);
        }
        String str2 = this.e;
        if (str2 != null) {
            if (this.d != 2) {
                Log.MiLogE(b, "only STAGING env  can setCustomNLPApi !!!!");
                return null;
            }
            mibrainRequestParamsBuilder.setCustomNLPApi(str2);
            mibrainRequestParamsBuilder.setCustomNLPAppId(this.mAppId);
            mibrainRequestParamsBuilder.setCustomNLPAppToken(this.mToken);
        }
        if ((paramBuilder.needNlp || paramBuilder.needExtraNlp) && paramBuilder.nlpRequest != null) {
            mibrainRequestParamsBuilder.setNLPText(paramBuilder.nlpRequest.getTextToProcess());
            JSONObject jSONObject3 = new JSONObject();
            if (paramBuilder.nlpRequest.startNewSession) {
                this.q = "";
            }
            jSONObject3.put("is_new", paramBuilder.nlpRequest.startNewSession);
            jSONObject3.put("id", paramBuilder.nlpRequest.mSessionId != null ? paramBuilder.nlpRequest.mSessionId : this.q);
            mibrainRequestParamsBuilder.setNLPSession(jSONObject3.toString());
            if (paramBuilder.nlpRequest.device != null) {
                mibrainRequestParamsBuilder.setDevice(paramBuilder.nlpRequest.device.toString());
            }
            if (paramBuilder.nlpRequest.speechData != null) {
                mibrainRequestParamsBuilder.setNLPSpeechData(paramBuilder.nlpRequest.speechData.toString());
            }
            if (paramBuilder.nlpRequest.userInfo != null) {
                mibrainRequestParamsBuilder.setNLPUserInfoIdType(paramBuilder.nlpRequest.userInfo.userType);
                mibrainRequestParamsBuilder.setNLPUserInfoId(paramBuilder.nlpRequest.userInfo.userId);
                mibrainRequestParamsBuilder.setNLPUserInfoExtend(paramBuilder.nlpRequest.userInfo.extend);
            }
            if (paramBuilder.nlpRequest.contextMap != null || paramBuilder.nlpRequest.contextStrMap != null) {
                JSONObject jSONObject4 = new JSONObject();
                if (paramBuilder.nlpRequest.contextMap != null) {
                    for (String str3 : paramBuilder.nlpRequest.contextMap.keySet()) {
                        try {
                            String strTrim = paramBuilder.nlpRequest.contextMap.get(str3).trim();
                            if (strTrim.startsWith("[") && strTrim.endsWith("]")) {
                                jSONArray = new JSONArray(strTrim);
                                jSONObject = null;
                            } else {
                                jSONObject = new JSONObject(strTrim);
                                jSONArray = null;
                            }
                        } catch (JSONException e2) {
                            Log.MiLogE(b, "JSONException when set context for key: " + str3, e2);
                            jSONArray = null;
                            jSONObject = null;
                        }
                        if (jSONArray != null) {
                            jSONObject4.put(str3, jSONArray);
                        } else if (jSONObject != null) {
                            jSONObject4.put(str3, jSONObject);
                        }
                    }
                }
                if (paramBuilder.nlpRequest.contextStrMap != null) {
                    for (String str4 : paramBuilder.nlpRequest.contextStrMap.keySet()) {
                        jSONObject4.put(str4, paramBuilder.nlpRequest.contextStrMap.get(str4));
                    }
                }
                mibrainRequestParamsBuilder.setNLPContext(jSONObject4.toString());
            }
            if (paramBuilder.nlpRequest.context != null) {
                mibrainRequestParamsBuilder.setNLPContext(paramBuilder.nlpRequest.context.toString());
            }
            if (paramBuilder.nlpRequest.requestId != null) {
                mibrainRequestParamsBuilder.setRequestId(paramBuilder.nlpRequest.requestId);
            }
        }
        if (paramBuilder.voiceprintRegisterRequest != null && paramBuilder.needVpReg) {
            mibrainRequestParamsBuilder.setVoiceprintUserId(paramBuilder.voiceprintRegisterRequest.userId);
            mibrainRequestParamsBuilder.setVoiceprintFamilyId(paramBuilder.voiceprintRegisterRequest.family);
            mibrainRequestParamsBuilder.setVoiceprintQueries(paramBuilder.voiceprintRegisterRequest.query);
            mibrainRequestParamsBuilder.setVoiceprintVendor(paramBuilder.voiceprintRegisterRequest.vendorStr);
            mibrainRequestParamsBuilder.setVoiceId(paramBuilder.voiceprintRegisterRequest.voiceId);
            mibrainRequestParamsBuilder.setVoiceprintDisableTextDependent(paramBuilder.voiceprintRegisterRequest.disableTextDependent);
        }
        if (paramBuilder.voiceprintDeleteRequest != null && paramBuilder.needVpDel) {
            mibrainRequestParamsBuilder.setVoiceprintDeleteIds(paramBuilder.voiceprintDeleteRequest.ids.toString());
            mibrainRequestParamsBuilder.setVoiceprintFamilyId(paramBuilder.voiceprintDeleteRequest.family);
            mibrainRequestParamsBuilder.setVoiceprintVendor(paramBuilder.voiceprintDeleteRequest.vendorStr);
        }
        if (paramBuilder.voiceprintQueryRequest != null && paramBuilder.needVpQry) {
            mibrainRequestParamsBuilder.setVoiceprintFamilyId(paramBuilder.voiceprintQueryRequest.family);
            mibrainRequestParamsBuilder.setVoiceprintVendor(paramBuilder.voiceprintQueryRequest.vendorStr);
        }
        if (paramBuilder.voiceprintRecognizeRequest != null && paramBuilder.needVpRec) {
            mibrainRequestParamsBuilder.setVoiceprintQueries(paramBuilder.voiceprintRecognizeRequest.query);
            mibrainRequestParamsBuilder.setVoiceprintFamilyId(paramBuilder.voiceprintRecognizeRequest.family);
            mibrainRequestParamsBuilder.setVoiceprintUserId(paramBuilder.voiceprintRecognizeRequest.id);
            mibrainRequestParamsBuilder.setVoiceprintVendor(paramBuilder.voiceprintRecognizeRequest.vendorStr);
            mibrainRequestParamsBuilder.setVoiceprintDisableTextDependent(paramBuilder.voiceprintRecognizeRequest.disableTextDependent);
        }
        if (paramBuilder.needEvent && paramBuilder.eventRequest != null) {
            mibrainRequestParamsBuilder.setEventNameSpace(paramBuilder.eventRequest.a);
            mibrainRequestParamsBuilder.setEventName(paramBuilder.eventRequest.b);
            mibrainRequestParamsBuilder.setEventPayload(paramBuilder.eventRequest.c);
            if (!paramBuilder.eventRequest.h && (str = this.q) != null && !str.isEmpty()) {
                mibrainRequestParamsBuilder.setEventSessionId(this.q);
            }
            mibrainRequestParamsBuilder.setEventContextNameSpace(paramBuilder.eventRequest.d);
            mibrainRequestParamsBuilder.setEventContextName(paramBuilder.eventRequest.e);
            mibrainRequestParamsBuilder.setEventContextPayload(paramBuilder.eventRequest.f);
            if (paramBuilder.eventRequest.i != null) {
                mibrainRequestParamsBuilder.setDevice(paramBuilder.eventRequest.i.toString());
            }
        }
        try {
            if (paramBuilder.needAsr && !paramBuilder.needTts && !paramBuilder.needNlp && !paramBuilder.needTts && !paramBuilder.needVpReg && !paramBuilder.needVpRec && !paramBuilder.needVpQry && !paramBuilder.needVpDel) {
                i = MibrainRequestParamsBuilder.MI_CMD_ASR;
            } else if (paramBuilder.needAsr && paramBuilder.needNlp && !paramBuilder.needTts && !paramBuilder.needVpReg && !paramBuilder.needVpRec && !paramBuilder.needVpQry && !paramBuilder.needVpDel) {
                i = MibrainRequestParamsBuilder.MI_CMD_ASR_NLP;
            } else if (paramBuilder.needAsr && paramBuilder.needNlp && paramBuilder.needTts && !paramBuilder.needVpReg && !paramBuilder.needVpRec && !paramBuilder.needVpQry && !paramBuilder.needVpDel) {
                i = MibrainRequestParamsBuilder.MI_CMD_ASR_NLP_TTS;
            } else if (!paramBuilder.needAsr && paramBuilder.needNlp && !paramBuilder.needTts && !paramBuilder.needVpReg && !paramBuilder.needVpRec && !paramBuilder.needVpQry && !paramBuilder.needVpDel) {
                i = MibrainRequestParamsBuilder.MI_CMD_NLP;
            } else if (paramBuilder.needAsr || !paramBuilder.needNlp || !paramBuilder.needTts || paramBuilder.needVpReg || paramBuilder.needVpRec || paramBuilder.needVpQry || paramBuilder.needVpDel) {
                if (paramBuilder.needAsr || paramBuilder.needNlp || !paramBuilder.needTts || paramBuilder.needVpReg || paramBuilder.needVpRec || paramBuilder.needVpQry || paramBuilder.needVpDel) {
                    if (paramBuilder.needVpReg && !paramBuilder.needAsr && !paramBuilder.needNlp && !paramBuilder.needTts && !paramBuilder.needVpDel && !paramBuilder.needVpQry && !paramBuilder.needVpRec) {
                        i2 = MibrainRequestParamsBuilder.MI_CMD_VOR_REG;
                    } else if (paramBuilder.needVpRec && !paramBuilder.needAsr && !paramBuilder.needNlp && !paramBuilder.needTts && !paramBuilder.needVpDel && !paramBuilder.needVpQry && !paramBuilder.needVpReg) {
                        i2 = MibrainRequestParamsBuilder.MI_CMD_VOR;
                    } else if (paramBuilder.needVpDel && !paramBuilder.needAsr && !paramBuilder.needNlp && !paramBuilder.needTts && !paramBuilder.needVpRec && !paramBuilder.needVpQry && !paramBuilder.needVpReg) {
                        i = MibrainRequestParamsBuilder.MI_CMD_VOR_DELETE;
                    } else if (!paramBuilder.needVpQry || paramBuilder.needAsr || paramBuilder.needNlp || paramBuilder.needTts || paramBuilder.needVpRec || paramBuilder.needVpDel || paramBuilder.needVpReg) {
                        if (!paramBuilder.needEvent) {
                            throw new IllegalArgumentException("needAsr:" + paramBuilder.needAsr + ",needNlp:" + paramBuilder.needNlp + ",needTts:" + paramBuilder.needTts + ",needVpReg:" + paramBuilder.needVpReg + ",needVpDel:" + paramBuilder.needVpDel + ",needVpRec:" + paramBuilder.needVpRec + ",needVpQry:" + paramBuilder.needVpQry + ",needEvent:" + paramBuilder.needEvent + "  combine Illegally ");
                        }
                        i = paramBuilder.eventRequest.g ? MibrainRequestParamsBuilder.MI_CMD_EVENT_TTS : MibrainRequestParamsBuilder.MI_CMD_EVENT;
                    } else {
                        i = MibrainRequestParamsBuilder.MI_CMD_VOR_QUERY;
                    }
                    mibrainRequestParamsBuilder.setRequestCmd(i2);
                    mibrainRequestParamsBuilder.setReceiverTimeOut(this.mReceiveTimeout * 2);
                    return mibrainRequestParamsBuilder.buid();
                }
                i = MibrainRequestParamsBuilder.MI_CMD_TTS;
            } else {
                i = MibrainRequestParamsBuilder.MI_CMD_NLP_TTS;
            }
            return mibrainRequestParamsBuilder.buid();
        } catch (MibrainException e3) {
            e3.printStackTrace();
            throw new IllegalArgumentException(e3);
        }
        mibrainRequestParamsBuilder.setRequestCmd(i);
    }

    private String b() {
        String strB;
        return (this.mContext == null || (strB = com.xiaomi.ai.utils.n.b(this.mContext)) == null) ? "" : com.xiaomi.ai.utils.n.b(strB);
    }

    public CharSequence a() {
        return this.c;
    }

    @Override // com.xiaomi.ai.SpeechEngine
    public void addBuffer(byte[] bArr, int i, int i2) {
        e eVar = this.n;
        if (eVar == null) {
            throw new IllegalStateException("you should call doStartIntegrally first !!");
        }
        eVar.a(new b(bArr, i, i2, false));
    }

    @Override // com.xiaomi.ai.SpeechEngine
    public void addNlpData(JSONObject jSONObject) {
        e eVar = this.n;
        if (eVar == null) {
            throw new IllegalStateException("you should call doStartIntegrally first !!");
        }
        eVar.a(new b(jSONObject.toString(), EnumC0117c.NLP_DATA));
    }

    @Override // com.xiaomi.ai.SpeechEngine
    public void addVADBegin() {
        e eVar = this.n;
        if (eVar == null) {
            throw new IllegalStateException("you should call doStartIntegrally first 1!!");
        }
        eVar.a(new b(EnumC0117c.VAD_BEGIN));
    }

    @Override // com.xiaomi.ai.SpeechEngine
    public void cancelTTS() {
        synchronized (this.o) {
            com.xiaomi.ai.streamplayer.k kVar = this.i;
            if (kVar != null) {
                kVar.c();
                this.i = null;
            }
        }
    }

    @Override // com.xiaomi.ai.SpeechEngine
    public void cleanAllUserLoginData() {
        try {
            m mVar = this.s;
            if (mVar != null) {
                mVar.e();
            }
        } catch (IllegalStateException e2) {
            e2.printStackTrace();
        }
    }

    @Override // com.xiaomi.ai.SpeechEngine
    public void cleanUserLoginData() {
        try {
            m mVar = this.s;
            if (mVar != null) {
                if (mVar.a() == null) {
                    Log.MiLogE(b, "oAuth is null");
                } else {
                    this.s.d();
                }
            }
        } catch (IllegalStateException e2) {
            e2.printStackTrace();
        }
    }

    @Override // com.xiaomi.ai.SpeechEngine
    public void cleanUserLoginData(String str) {
        try {
            m mVar = this.s;
            if (mVar != null) {
                if (mVar.a(str) == null) {
                    Log.MiLogE(b, "oAuth is null " + str);
                } else {
                    this.s.c(str);
                }
            }
        } catch (IllegalStateException e2) {
            e2.printStackTrace();
        }
    }

    @Override // com.xiaomi.ai.SpeechEngine
    public void doAsyncTrackUpload(HTTPCallback hTTPCallback, TrackInfo trackInfo, String str) {
        if (this.mTrackUtils != null) {
            this.mTrackUtils.a(this.d);
            this.mTrackUtils.a(hTTPCallback, trackInfo, str, getAuthorizationValue(), this.t);
        }
    }

    @Override // com.xiaomi.ai.SpeechEngine
    public void doAsyncTrackUpload(TrackInfo trackInfo, String str) {
        if (this.mTrackUtils != null) {
            this.mTrackUtils.a(this.d);
            this.mTrackUtils.a(this.mTrackUtils.a(), trackInfo, str, getAuthorizationValue(), this.t);
        }
    }

    @Override // com.xiaomi.ai.SpeechEngine
    protected void doDeleteVoiceprint(VoiceprintDeleteRequest voiceprintDeleteRequest) {
        SpeechEngine.ParamBuilder paramBuilder = new SpeechEngine.ParamBuilder();
        paramBuilder.needVpDel = true;
        paramBuilder.setApiKey(this.u);
        paramBuilder.voiceprintDeleteRequest = voiceprintDeleteRequest;
        synchronized (this.p) {
            this.c = new SpannableStringBuilder();
        }
        paramBuilder.setApiKey(this.u);
        a(paramBuilder);
    }

    @Override // com.xiaomi.ai.SpeechEngine
    protected void doListenSpeech(AsrRequest asrRequest) {
        SpeechEngine.ParamBuilder paramBuilder = new SpeechEngine.ParamBuilder();
        paramBuilder.needAsr();
        paramBuilder.setApiKey(this.u);
        paramBuilder.setAsrRequest(asrRequest);
        synchronized (this.p) {
            this.c = new SpannableStringBuilder();
        }
        a(paramBuilder);
    }

    @Override // com.xiaomi.ai.SpeechEngine
    protected void doQueryVoiceprint(VoiceprintQueryRequest voiceprintQueryRequest) {
        SpeechEngine.ParamBuilder paramBuilder = new SpeechEngine.ParamBuilder();
        paramBuilder.needVpQry = true;
        paramBuilder.setApiKey(this.u);
        paramBuilder.voiceprintQueryRequest = voiceprintQueryRequest;
        synchronized (this.p) {
            this.c = new SpannableStringBuilder();
        }
        paramBuilder.setApiKey(this.u);
        a(paramBuilder);
    }

    @Override // com.xiaomi.ai.SpeechEngine
    protected void doSemanticsParse(NlpRequest nlpRequest) {
        SpeechEngine.ParamBuilder paramBuilder = new SpeechEngine.ParamBuilder();
        paramBuilder.needNlp();
        paramBuilder.setApiKey(this.u);
        paramBuilder.setNlpRequest(nlpRequest);
        synchronized (this.p) {
            this.c = new SpannableStringBuilder();
        }
        a(paramBuilder);
    }

    @Override // com.xiaomi.ai.SpeechEngine
    protected void doSpeak(TtsRequest ttsRequest) {
        SpeechEngine.ParamBuilder paramBuilder = new SpeechEngine.ParamBuilder();
        paramBuilder.needTts();
        paramBuilder.setApiKey(this.u);
        paramBuilder.setTtsRequest(ttsRequest);
        synchronized (this.p) {
            this.c = new SpannableStringBuilder();
        }
        a(paramBuilder);
    }

    @Override // com.xiaomi.ai.SpeechEngine
    protected void doStartIntegrally(SpeechEngine.ParamBuilder paramBuilder) {
        synchronized (this.p) {
            this.c = new SpannableStringBuilder();
        }
        paramBuilder.setApiKey(this.u);
        a(paramBuilder);
    }

    @Override // com.xiaomi.ai.SpeechEngine
    protected void doVoiceprintRecognize(VoiceprintRecognizeRequest voiceprintRecognizeRequest) {
        SpeechEngine.ParamBuilder paramBuilder = new SpeechEngine.ParamBuilder();
        paramBuilder.needVpRec = true;
        paramBuilder.setApiKey(this.u);
        paramBuilder.voiceprintRecognizeRequest = voiceprintRecognizeRequest;
        synchronized (this.p) {
            this.c = new SpannableStringBuilder();
        }
        a(paramBuilder);
    }

    @Override // com.xiaomi.ai.SpeechEngine
    protected void doVoiceprintRegister(VoiceprintRegisterRequest voiceprintRegisterRequest) {
        SpeechEngine.ParamBuilder paramBuilder = new SpeechEngine.ParamBuilder();
        paramBuilder.needVpReg = true;
        paramBuilder.setApiKey(this.u);
        paramBuilder.voiceprintRegisterRequest = voiceprintRegisterRequest;
        synchronized (this.p) {
            this.c = new SpannableStringBuilder();
        }
        a(paramBuilder);
    }

    @Override // com.xiaomi.ai.SpeechEngine
    public void endSpeech() {
        this.j = false;
        e eVar = this.n;
        if (eVar != null) {
            eVar.a(new b(new byte[0], 0, 0, true));
        }
    }

    @Override // com.xiaomi.ai.SpeechEngine
    public void endVoiceprintRecognize() {
        this.j = false;
        e eVar = this.n;
        if (eVar != null) {
            eVar.a(new b(new byte[0], 0, 0, true));
        }
    }

    @Override // com.xiaomi.ai.SpeechEngine
    public void endVoiceprintRegister() {
        this.j = false;
        e eVar = this.n;
        if (eVar != null) {
            eVar.a(new b(new byte[0], 0, 0, true));
        }
    }

    @Override // com.xiaomi.ai.SpeechEngine
    public void forceStop() {
        synchronized (this.o) {
            super.forceStop();
            com.xiaomi.ai.streamplayer.k kVar = this.i;
            if (kVar != null) {
                kVar.c();
                this.i = null;
            }
            e eVar = this.n;
            if (eVar != null) {
                eVar.f();
                this.n = null;
            }
            a aVar = this.k;
            if (aVar != null) {
                aVar.interrupt();
                this.k = null;
            }
            MibrainRequest mibrainRequest = this.m;
            if (mibrainRequest != null) {
                mibrainRequest.asyncStopRequest();
                this.m = null;
            }
            this.r = null;
            setBluetoothOn(false);
        }
    }

    @Override // com.xiaomi.ai.SpeechEngine
    public String getAuthorizationValue() {
        return getAuthorizationValue(false);
    }

    @Override // com.xiaomi.ai.SpeechEngine
    public String getAuthorizationValue(boolean z) {
        String str;
        if (this.t == ENGINE_AUTH_MIOT) {
            if (this.mAppId == null || this.mToken == null) {
                return null;
            }
            return String.format("MIOT-TOKEN-V1 app_id:%s,%s", this.mAppId, this.mToken);
        }
        if (this.t == ENGINE_AUTH_MIAI) {
            String strA = a(z);
            if (strA == null) {
                return null;
            }
            return String.format("DO-TOKEN-V1 app_id:%s,scope_data:%s,access_token:%s", this.mAppId, b(), strA);
        }
        if (this.t == ENGINE_AUTH_MIAO) {
            String strA2 = a(z);
            if (strA2 == null) {
                return null;
            }
            return String.format("AO-TOKEN-V1 dev_app_id:%s,access_token:%s", this.mAppId, strA2);
        }
        if (this.t == ENGINE_AUTH_ANONYMOUS) {
            String strA3 = a(z, this.mAppId);
            if (strA3 != null) {
                return this.s.a(this.mAppId, this.u, strA3);
            }
            str = "anon";
        } else {
            if (this.t != ENGINE_AUTH_ANONYMOUS_PROXY) {
                if (this.t != ENGINE_AUTH_TP || this.mAppId == null || this.mToken == null) {
                    return null;
                }
                return String.format("TP-TOKEN-V1 app_id:%s,access_token:%s", this.mAppId, this.mToken);
            }
            String strA4 = a(z, this.mAppId);
            if (strA4 != null) {
                return this.s.a(this.mAppId, this.u, strA4);
            }
            str = "anon proxy";
        }
        Log.MiLogD(str, "failed");
        return null;
    }

    @Override // com.xiaomi.ai.SpeechEngine
    public boolean getOAuthStatus() {
        StringBuilder sb;
        m mVar = this.s;
        if (mVar == null) {
            return false;
        }
        if (this.t == ENGINE_AUTH_ANONYMOUS || this.t == ENGINE_AUTH_ANONYMOUS_PROXY) {
            if (mVar.a(this.mAppId) != null) {
                return true;
            }
            sb = new StringBuilder("NO AUTH DATA, mode ");
        } else {
            if (mVar.a() != null) {
                return true;
            }
            sb = new StringBuilder("NO AUTH DATA, mode ");
        }
        Log.MiLogD(b, sb.append(this.t).toString());
        return false;
    }

    @Override // com.xiaomi.ai.SpeechEngine
    public String getResourceDetails(String str, String str2) {
        if (str == null || str2 == null) {
            return null;
        }
        String authorizationValue = getAuthorizationValue();
        if (authorizationValue == null) {
            a(true);
            authorizationValue = getAuthorizationValue();
            if (authorizationValue == null) {
                return null;
            }
        }
        return MibrainRequest.getResourceDetails(str, str2, authorizationValue, this.d);
    }

    @Override // com.xiaomi.ai.SpeechEngine
    public int getTTSMode() {
        return this.x;
    }

    @Override // com.xiaomi.ai.SpeechEngine
    public UploadHelper getUploadHelper(SendWakeupDataStatusInterface sendWakeupDataStatusInterface) {
        UploadHelper uploadHelper = new UploadHelper();
        if (uploadHelper.startUploadRequest(sendWakeupDataStatusInterface, this.s, this.d, this.t, this.mAppId, this.mToken, this.u, com.xiaomi.ai.utils.n.b(com.xiaomi.ai.utils.n.b(this.mContext))) != 0) {
            return null;
        }
        return uploadHelper;
    }

    @Override // com.xiaomi.ai.SpeechEngine
    public void init(Context context, String str, int i) {
        super.init(context, str, i);
        Log.setJNILogHook(true);
        Mibrainsdk.setLogLevel(Mibrainsdk.MIBRAIN_DEBUG_LEVEL_WARNING);
        if (i == ENGINE_AUTH_MIAI || i == ENGINE_AUTH_MIAO || i == ENGINE_AUTH_ANONYMOUS || i == ENGINE_AUTH_ANONYMOUS_PROXY) {
            m mVar = new m(context, str, this.mToken, this.mAppId);
            this.s = mVar;
            mVar.b();
            this.s.a(i != ENGINE_AUTH_MIAI ? 0 : 1);
            if (i == ENGINE_AUTH_ANONYMOUS || i == ENGINE_AUTH_ANONYMOUS_PROXY) {
                this.s.a(str, com.xiaomi.ai.utils.n.b(context), new com.xiaomi.ai.utils.g(context).d(), i);
                this.u = str;
            }
        }
        this.t = i;
    }

    @Override // com.xiaomi.ai.SpeechEngine
    public void release() {
        super.release();
        synchronized (this.o) {
            m mVar = this.s;
            if (mVar != null) {
                mVar.c();
                this.s = null;
            }
            e eVar = this.n;
            if (eVar != null) {
                eVar.f();
                this.n = null;
            }
            setBluetoothOn(false);
        }
    }

    @Override // com.xiaomi.ai.SpeechEngine
    public int sendPendingNLPData(NlpRequest nlpRequest) {
        nlpRequest.setIsPendingSend(0);
        SpeechEngine.ParamBuilder paramBuilder = new SpeechEngine.ParamBuilder();
        paramBuilder.needNlp();
        paramBuilder.setApiKey(this.u);
        paramBuilder.setNlpRequest(nlpRequest);
        try {
            MibrainRequestParams mibrainRequestParamsB = b(paramBuilder);
            if (this.m == null) {
                Log.MiLogE(b, "NULL ERROR");
            }
            MibrainRequest mibrainRequest = this.m;
            if (mibrainRequest == null) {
                return -1;
            }
            mibrainRequest.updateRequestParams(mibrainRequestParamsB);
            return this.m.sendPendingData();
        } catch (JSONException e2) {
            e2.printStackTrace();
            return -1;
        }
    }

    @Override // com.xiaomi.ai.SpeechEngine
    public void sendWakeupData(SendWakeupDataStatusInterface sendWakeupDataStatusInterface, String str, String str2, String str3, int i, int i2, String str4, byte[] bArr) {
        g gVar = new g(this, i, str3, str, str2, i2, str4, bArr, new f(this, sendWakeupDataStatusInterface));
        ExecutorService executorService = this.y;
        if (executorService != null) {
            executorService.execute(gVar);
        } else if (sendWakeupDataStatusInterface != null) {
            sendWakeupDataStatusInterface.onSendError("ThreadPool error");
        }
    }

    @Override // com.xiaomi.ai.SpeechEngine
    public void setAnonymousAuthCallbacks(MiAiOauthCallbacks miAiOauthCallbacks) {
        super.setMiAIOauthCallbacks(miAiOauthCallbacks);
        if (miAiOauthCallbacks == null) {
            throw new NullPointerException("aiOauthListener is null");
        }
        if (this.t != ENGINE_AUTH_ANONYMOUS && this.t != ENGINE_AUTH_ANONYMOUS_PROXY) {
            throw new IllegalArgumentException("setMiAOOauthCallbacks is only for ENGINE_AUTH_ANONYMOUS");
        }
        this.s.a(miAiOauthCallbacks);
    }

    @Override // com.xiaomi.ai.SpeechEngine
    public boolean setAuthorizationTokens(String str, String str2, long j) {
        m mVar = this.s;
        if (mVar != null) {
            return mVar.a(this.t, this.mAppId, str, str2, j);
        }
        Log.MiLogE(b, "setAuthorizationTokens: mOauthHelper is null\n");
        return false;
    }

    @Override // com.xiaomi.ai.SpeechEngine
    public void setCustomNlpUrl(String str) {
        this.e = str;
    }

    @Override // com.xiaomi.ai.SpeechEngine
    public void setEnv(int i) {
        setEnv(i, this.mAppId, this.mToken);
    }

    @Override // com.xiaomi.ai.SpeechEngine
    public void setEnv(int i, String str) {
        setEnv(i, str, this.mToken);
    }

    @Override // com.xiaomi.ai.SpeechEngine
    public void setEnv(int i, String str, String str2) {
        m mVar = this.s;
        if (mVar != null) {
            mVar.b(i);
        }
        this.d = i;
        this.mAppId = str;
        this.mToken = str2;
    }

    @Override // com.xiaomi.ai.SpeechEngine
    public void setExternalOAuthToken(String str) {
        if (TextUtils.isEmpty(str)) {
            throw new IllegalArgumentException("OAuth token  is empty");
        }
        setUseExternalOAuthToken(true);
        this.mToken = str;
    }

    @Override // com.xiaomi.ai.SpeechEngine
    public void setMiAIOauthCallbacks(MiAiOauthCallbacks miAiOauthCallbacks) {
        super.setMiAIOauthCallbacks(miAiOauthCallbacks);
        if (miAiOauthCallbacks == null) {
            throw new NullPointerException("aiOauthListener is null");
        }
        if (this.t != ENGINE_AUTH_MIAI) {
            throw new IllegalArgumentException("setMiAOOauthCallbacks is only for ENGINE_AUTH_MIAO");
        }
        this.s.a(miAiOauthCallbacks);
    }

    @Override // com.xiaomi.ai.SpeechEngine
    public void setMiAOOauthCallbacks(MiAiOauthCallbacks miAiOauthCallbacks) {
        super.setMiAIOauthCallbacks(miAiOauthCallbacks);
        if (miAiOauthCallbacks == null) {
            throw new NullPointerException("aiOauthListener is null");
        }
        if (this.t != ENGINE_AUTH_MIAO) {
            throw new IllegalArgumentException("setMiAOOauthCallbacks is only for ENGINE_AUTH_MIAO");
        }
        this.s.a(miAiOauthCallbacks);
    }

    @Override // com.xiaomi.ai.SpeechEngine
    public void setTTSMode(int i) {
        this.x = i;
    }

    @Override // com.xiaomi.ai.SpeechEngine
    public void setTokenUpdatedCallback(MibrainOauthManager.updateTokenCallback updatetokencallback) {
        m mVar = this.s;
        if (mVar != null) {
            mVar.a(updatetokencallback);
        }
    }

    @Override // com.xiaomi.ai.SpeechEngine
    public void setTrackUploadCallback(HTTPCallback hTTPCallback) {
        this.mTrackUtils.a(hTTPCallback);
    }

    @Override // com.xiaomi.ai.SpeechEngine
    public void setTtsEnable(boolean z) {
        synchronized (this.o) {
            this.z.set(z);
        }
    }

    @Override // com.xiaomi.ai.SpeechEngine
    public int startUploadASRRecord(SendWakeupDataStatusInterface sendWakeupDataStatusInterface, UploadHelper.ASRRecordInfo aSRRecordInfo, byte[] bArr) {
        if (sendWakeupDataStatusInterface == null) {
            return -1;
        }
        com.xiaomi.ai.e eVar = new com.xiaomi.ai.e(this, sendWakeupDataStatusInterface, aSRRecordInfo, bArr);
        ExecutorService executorService = this.y;
        if (executorService != null) {
            executorService.execute(eVar);
            return 0;
        }
        sendWakeupDataStatusInterface.onSendError("ThreadPool error");
        return -1;
    }

    @Override // com.xiaomi.ai.SpeechEngine
    public int startUploadWakeupData(SendWakeupDataStatusInterface sendWakeupDataStatusInterface, UploadHelper.WakeupDataInfo wakeupDataInfo, byte[] bArr) {
        if (sendWakeupDataStatusInterface == null) {
            return -1;
        }
        com.xiaomi.ai.d dVar = new com.xiaomi.ai.d(this, sendWakeupDataStatusInterface, wakeupDataInfo, bArr);
        ExecutorService executorService = this.y;
        if (executorService != null) {
            executorService.execute(dVar);
            return 0;
        }
        sendWakeupDataStatusInterface.onSendError("ThreadPool error");
        return -1;
    }

    @Override // com.xiaomi.ai.SpeechEngine
    public boolean updateAnonymousProxyArgs(String str, String str2, String str3, String str4, String str5, String str6) {
        if (this.t != ENGINE_AUTH_ANONYMOUS_PROXY) {
            Log.MiLogE("ERROR", "wrong mode " + this.t);
            return false;
        }
        m mVar = this.s;
        if (mVar == null || mVar.a(str, str2, str3, str4, str5, str6) != 0) {
            return false;
        }
        this.w = str;
        this.mAppId = str;
        this.mToken = str2;
        this.u = str3;
        return true;
    }

    @Override // com.xiaomi.ai.SpeechEngine
    public void updateMiotAuth(String str, String str2) {
        if (TextUtils.isEmpty(str) || TextUtils.isEmpty(str2)) {
            throw new IllegalArgumentException("Miot token or session  is empty");
        }
        this.mToken = String.format(Locale.getDefault(), "session_id:%s,token:%s", str2, str);
    }

    @Override // com.xiaomi.ai.SpeechEngine
    public void updateTPAuth(String str) {
        if (TextUtils.isEmpty(str)) {
            throw new IllegalArgumentException(" token  is empty");
        }
        this.mToken = str;
    }
}
