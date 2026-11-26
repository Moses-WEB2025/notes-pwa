package com.miui.notes.ainlsquery;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import androidx.activity.result.ActivityResultLauncher;
import ch.qos.logback.core.CoreConstants;
import ch.qos.logback.core.joran.action.Action;
import com.market.sdk.reflect.ReflectUtilsForMiui;
import com.miui.common.app.BaseNoteApp;
import com.miui.common.tool.Logger;
import com.miui.common.tool.Utils;
import com.miui.notes.adapter.BindContext;
import com.miui.notes.basefeature.NotesNormalConstants;
import com.miui.notes.excerpt.NotifyUtils;
import com.miui.notes.tool.PreferenceUtils;
import com.xiaomi.aicr.constant.Constants;
import com.xiaomi.aicr.vision.VisionAttribute;
import com.xiaomi.mirror.synergy.CallMethod;
import com.xiaomi.onetrack.api.ah;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import kotlin.Lazy;
import kotlin.LazyKt;
import kotlin.LazyThreadSafetyMode;
import kotlin.Metadata;
import kotlin.Pair;
import kotlin.collections.CollectionsKt;
import kotlin.comparisons.ComparisonsKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import miuix.smartaction.SmartAction;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/* JADX INFO: compiled from: AiQueryCapabilityAccess.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000\u0082\u0001\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0002\b%\n\u0002\u0010\b\n\u0002\b7\n\u0002\u0018\u0002\n\u0002\b\u0018\n\u0002\u0018\u0002\n\u0002\u0010\t\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u000b\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0011\n\u0002\b\u0005\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\u0018\u0000 ®\u00012\u00020\u0001:\u0002®\u0001B\t\b\u0002¢\u0006\u0004\b\u0002\u0010\u0003J\b\u0010\u008b\u0001\u001a\u00030\u008c\u0001J\b\u0010\u008d\u0001\u001a\u00030\u008c\u0001J\t\u0010\u008e\u0001\u001a\u00020+H\u0002J\t\u0010\u008f\u0001\u001a\u00020+H\u0002J\t\u0010\u0090\u0001\u001a\u00020+H\u0002J\b\u0010\u0091\u0001\u001a\u00030\u008c\u0001J\u0015\u0010\u0092\u0001\u001a\u0010\u0012\u0005\u0012\u00030\u008c\u0001\u0012\u0004\u0012\u00020+0\u0093\u0001J\t\u0010\u0094\u0001\u001a\u00020+H\u0002J>\u0010\u0095\u0001\u001a\u0005\u0018\u00010\u0096\u00012\t\u0010\u0097\u0001\u001a\u0004\u0018\u00010\u00052\u0007\u0010\u0098\u0001\u001a\u00020+2\u0018\u0010\u0099\u0001\u001a\r\u0012\b\b\u0001\u0012\u0004\u0018\u00010\u00050\u009a\u0001\"\u0004\u0018\u00010\u0005¢\u0006\u0003\u0010\u009b\u0001JI\u0010\u009c\u0001\u001a\u0010\u0012\u0005\u0012\u00030\u008c\u0001\u0012\u0004\u0012\u00020\u00050\u0093\u00012\t\u0010\u009d\u0001\u001a\u0004\u0018\u00010\u00052\u0007\u0010\u0098\u0001\u001a\u00020+2\u0018\u0010\u0099\u0001\u001a\r\u0012\b\b\u0001\u0012\u0004\u0018\u00010\u00050\u009a\u0001\"\u0004\u0018\u00010\u0005¢\u0006\u0003\u0010\u009e\u0001J\u001c\u0010\u009f\u0001\u001a\u00030 \u00012\u0007\u0010¡\u0001\u001a\u00020\u00052\t\u0010R\u001a\u0005\u0018\u00010¢\u0001J\u0012\u0010£\u0001\u001a\u00020}2\u0007\u0010¤\u0001\u001a\u00020\u0005H\u0002J\u0012\u0010¥\u0001\u001a\u00020+2\u0007\u0010¤\u0001\u001a\u00020\u0005H\u0002J\b\u0010¦\u0001\u001a\u00030 \u0001J\u0012\u0010§\u0001\u001a\u00030 \u00012\b\u0010¨\u0001\u001a\u00030©\u0001J&\u0010ª\u0001\u001a\u00030 \u00012\b\u0010¨\u0001\u001a\u00030©\u00012\b\u0010«\u0001\u001a\u00030¬\u00012\b\u0010\u00ad\u0001\u001a\u00030\u008c\u0001R\u0014\u0010\u0004\u001a\u00020\u0005X\u0086D¢\u0006\b\n\u0000\u001a\u0004\b\u0006\u0010\u0007R\u0014\u0010\b\u001a\u00020\u0005X\u0086D¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\u0007R\u0014\u0010\n\u001a\u00020\u0005X\u0086D¢\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\u0007R\u0014\u0010\f\u001a\u00020\u0005X\u0086D¢\u0006\b\n\u0000\u001a\u0004\b\r\u0010\u0007R\u0014\u0010\u000e\u001a\u00020\u0005X\u0086D¢\u0006\b\n\u0000\u001a\u0004\b\u000f\u0010\u0007R\u0014\u0010\u0010\u001a\u00020\u0005X\u0086D¢\u0006\b\n\u0000\u001a\u0004\b\u0011\u0010\u0007R\u0014\u0010\u0012\u001a\u00020\u0005X\u0086D¢\u0006\b\n\u0000\u001a\u0004\b\u0013\u0010\u0007R\u0014\u0010\u0014\u001a\u00020\u0005X\u0086D¢\u0006\b\n\u0000\u001a\u0004\b\u0015\u0010\u0007R\u0014\u0010\u0016\u001a\u00020\u0005X\u0086D¢\u0006\b\n\u0000\u001a\u0004\b\u0017\u0010\u0007R\u0014\u0010\u0018\u001a\u00020\u0005X\u0086D¢\u0006\b\n\u0000\u001a\u0004\b\u0019\u0010\u0007R\u0014\u0010\u001a\u001a\u00020\u0005X\u0086D¢\u0006\b\n\u0000\u001a\u0004\b\u001b\u0010\u0007R\u0014\u0010\u001c\u001a\u00020\u0005X\u0086D¢\u0006\b\n\u0000\u001a\u0004\b\u001d\u0010\u0007R\u0014\u0010\u001e\u001a\u00020\u0005X\u0086D¢\u0006\b\n\u0000\u001a\u0004\b\u001f\u0010\u0007R\u0014\u0010 \u001a\u00020\u0005X\u0086D¢\u0006\b\n\u0000\u001a\u0004\b!\u0010\u0007R\u0014\u0010\"\u001a\u00020\u0005X\u0086D¢\u0006\b\n\u0000\u001a\u0004\b#\u0010\u0007R\u0014\u0010$\u001a\u00020\u0005X\u0086D¢\u0006\b\n\u0000\u001a\u0004\b%\u0010\u0007R\u0014\u0010&\u001a\u00020\u0005X\u0086D¢\u0006\b\n\u0000\u001a\u0004\b'\u0010\u0007R\u0014\u0010(\u001a\u00020\u0005X\u0086D¢\u0006\b\n\u0000\u001a\u0004\b)\u0010\u0007R\u0014\u0010*\u001a\u00020+X\u0086D¢\u0006\b\n\u0000\u001a\u0004\b,\u0010-R\u0014\u0010.\u001a\u00020+X\u0086D¢\u0006\b\n\u0000\u001a\u0004\b/\u0010-R\u0014\u00100\u001a\u00020+X\u0086D¢\u0006\b\n\u0000\u001a\u0004\b1\u0010-R\u0014\u00102\u001a\u00020+X\u0086D¢\u0006\b\n\u0000\u001a\u0004\b3\u0010-R\u0014\u00104\u001a\u00020+X\u0086D¢\u0006\b\n\u0000\u001a\u0004\b5\u0010-R\u0014\u00106\u001a\u00020+X\u0086D¢\u0006\b\n\u0000\u001a\u0004\b7\u0010-R\u0014\u00108\u001a\u00020+X\u0086D¢\u0006\b\n\u0000\u001a\u0004\b9\u0010-R\u0014\u0010:\u001a\u00020+X\u0086D¢\u0006\b\n\u0000\u001a\u0004\b;\u0010-R\u0014\u0010<\u001a\u00020+X\u0086D¢\u0006\b\n\u0000\u001a\u0004\b=\u0010-R\u0014\u0010>\u001a\u00020+X\u0086D¢\u0006\b\n\u0000\u001a\u0004\b?\u0010-R\u0014\u0010@\u001a\u00020\u0005X\u0086D¢\u0006\b\n\u0000\u001a\u0004\bA\u0010\u0007R\u0014\u0010B\u001a\u00020\u0005X\u0086D¢\u0006\b\n\u0000\u001a\u0004\bC\u0010\u0007R\u0014\u0010D\u001a\u00020\u0005X\u0086D¢\u0006\b\n\u0000\u001a\u0004\bE\u0010\u0007R\u0014\u0010F\u001a\u00020\u0005X\u0086D¢\u0006\b\n\u0000\u001a\u0004\bG\u0010\u0007R\u0014\u0010H\u001a\u00020\u0005X\u0086D¢\u0006\b\n\u0000\u001a\u0004\bI\u0010\u0007R\u0014\u0010J\u001a\u00020\u0005X\u0086D¢\u0006\b\n\u0000\u001a\u0004\bK\u0010\u0007R\u0014\u0010L\u001a\u00020\u0005X\u0086D¢\u0006\b\n\u0000\u001a\u0004\bM\u0010\u0007R\u001a\u0010N\u001a\u00020\u0005X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\bO\u0010\u0007\"\u0004\bP\u0010QR\u001a\u0010R\u001a\u00020\u0005X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\bS\u0010\u0007\"\u0004\bT\u0010QR\u0014\u0010U\u001a\u00020+X\u0086D¢\u0006\b\n\u0000\u001a\u0004\bV\u0010-R\u0011\u0010W\u001a\u00020+¢\u0006\b\n\u0000\u001a\u0004\bX\u0010-R\u0011\u0010Y\u001a\u00020+¢\u0006\b\n\u0000\u001a\u0004\bZ\u0010-R\u0011\u0010[\u001a\u00020+¢\u0006\b\n\u0000\u001a\u0004\b\\\u0010-R\u0011\u0010]\u001a\u00020+¢\u0006\b\n\u0000\u001a\u0004\b^\u0010-R\u0014\u0010_\u001a\u00020+X\u0086D¢\u0006\b\n\u0000\u001a\u0004\b`\u0010-R\u000e\u0010a\u001a\u00020\u0005X\u0082D¢\u0006\u0002\n\u0000R\u0011\u0010b\u001a\u00020c¢\u0006\b\n\u0000\u001a\u0004\bd\u0010eR\u001a\u0010f\u001a\u00020\u0005X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\bg\u0010\u0007\"\u0004\bh\u0010QR\u001a\u0010i\u001a\u00020\u0005X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\bj\u0010\u0007\"\u0004\bk\u0010QR\u001a\u0010l\u001a\u00020\u0005X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\bm\u0010\u0007\"\u0004\bn\u0010QR\u001a\u0010o\u001a\u00020\u0005X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\bp\u0010\u0007\"\u0004\bq\u0010QR\u001a\u0010r\u001a\u00020\u0005X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\bs\u0010\u0007\"\u0004\bt\u0010QR\u001a\u0010u\u001a\u00020\u0005X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\bv\u0010\u0007\"\u0004\bw\u0010QR\u001a\u0010x\u001a\u00020\u0005X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\by\u0010\u0007\"\u0004\bz\u0010QR@\u0010{\u001a\"\u0012\u0004\u0012\u00020}\u0012\u0004\u0012\u00020~\u0018\u00010|j\u0010\u0012\u0004\u0012\u00020}\u0012\u0004\u0012\u00020~\u0018\u0001`\u007f8FX\u0086\u000e¢\u0006\u0012\n\u0000\u001a\u0006\b\u0080\u0001\u0010\u0081\u0001\"\u0006\b\u0082\u0001\u0010\u0083\u0001R3\u0010\u0084\u0001\u001a\u0014\u0012\u0004\u0012\u00020}0\u0085\u0001j\t\u0012\u0004\u0012\u00020}`\u0086\u00018FX\u0086\u000e¢\u0006\u0012\n\u0000\u001a\u0006\b\u0087\u0001\u0010\u0088\u0001\"\u0006\b\u0089\u0001\u0010\u008a\u0001¨\u0006¯\u0001"}, d2 = {"Lcom/miui/notes/ainlsquery/AiQueryCapabilityAccess;", "", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "()V", "TAG", "", "getTAG", "()Ljava/lang/String;", "KEV_STATUS", "getKEV_STATUS", "KEY_CAPABILITY", "getKEY_CAPABILITY", "KEY_SCENE_STATUS", "getKEY_SCENE_STATUS", "KEY_EVENT", "getKEY_EVENT", "KEY_PERMISSION_STATE", "getKEY_PERMISSION_STATE", "KEY_PERMISSION_LIST", "getKEY_PERMISSION_LIST", "KEY_QUERY_RESULTS", "getKEY_QUERY_RESULTS", "KEY_CLIENT", "getKEY_CLIENT", "KEY_QUERY_BODY", "getKEY_QUERY_BODY", "KEY_QUERY_ID", "getKEY_QUERY_ID", "KEY_SESSION_ID", "getKEY_SESSION_ID", "KEY_TIMEOUT", "getKEY_TIMEOUT", "KEY_SCOPE", "getKEY_SCOPE", "KEY_EXTRA_PARAMS", "getKEY_EXTRA_PARAMS", "KEY_LLM_SUGGESTIONS", "getKEY_LLM_SUGGESTIONS", "KEY_LLM_RESPONSE", "getKEY_LLM_RESPONSE", "KEY_SEARCH_DB_PROGRESS", "getKEY_SEARCH_DB_PROGRESS", "RESULT_OK", "", "getRESULT_OK", "()I", "RESULT_SERVICE_CONNECT_FAILED", "getRESULT_SERVICE_CONNECT_FAILED", "RESULT_UNKNOWN_ERROR", "getRESULT_UNKNOWN_ERROR", "RESULT_NOT_SUPPORT_DOMAIN", "getRESULT_NOT_SUPPORT_DOMAIN", "RESULT_NOT_SUPPORT_CAPABILITY", "getRESULT_NOT_SUPPORT_CAPABILITY", "RESULT_SERVER_NO_PERMISSION", "getRESULT_SERVER_NO_PERMISSION", "RESULT_CLIENT_NO_PERMISSION", "getRESULT_CLIENT_NO_PERMISSION", "RESULT_CLIENT_ERROR_PARAMS", "getRESULT_CLIENT_ERROR_PARAMS", "RESULT_SWITCH_NOT_OPEN", "getRESULT_SWITCH_NOT_OPEN", "USE_NORMAL_SEARCH", "getUSE_NORMAL_SEARCH", "METHOD_INIT", "getMETHOD_INIT", "METHOD_QUERY", "getMETHOD_QUERY", "METHOD_REQUEST_PERMISSION", "getMETHOD_REQUEST_PERMISSION", "METHOD_CHECK_PERMISSION", "getMETHOD_CHECK_PERMISSION", "METHOD_IS_SUPPORT", "getMETHOD_IS_SUPPORT", "METHOD_GET_PROGRESS", "getMETHOD_GET_PROGRESS", "KEY_IN_PROGRESS", "getKEY_IN_PROGRESS", "content_id", "getContent_id", "setContent_id", "(Ljava/lang/String;)V", CallMethod.ARG_EXTRA_STRING, "getExtra", "setExtra", "QUERY_GALLERY_CAPABILITY", "getQUERY_GALLERY_CAPABILITY", "QUERY_NOTE_CAPABILITY", "getQUERY_NOTE_CAPABILITY", "QUERY_SMS_CAPABILITY", "getQUERY_SMS_CAPABILITY", "QUERY_FILES_CAPABILITY", "getQUERY_FILES_CAPABILITY", "QUERY_RECORDING_CAPABILITY", "getQUERY_RECORDING_CAPABILITY", "CRS_CAP_NLU_QUERY", "getCRS_CAP_NLU_QUERY", "SERVICE_PACKAGE_NAME", CallMethod.ARG_URI, "Landroid/net/Uri;", "getUri", "()Landroid/net/Uri;", "title", "getTitle", "setTitle", "date", "getDate", "setDate", "size", "getSize", "setSize", "chunk_text", "getChunk_text", "setChunk_text", "chunk_start", "getChunk_start", "setChunk_start", "chunk_end", "getChunk_end", "setChunk_end", "highlight_keywords", "getHighlight_keywords", "setHighlight_keywords", "aiQueryNoteList", "Ljava/util/HashMap;", "", "Lcom/miui/notes/ainlsquery/AiQueryNoteExtra;", "Lkotlin/collections/HashMap;", "getAiQueryNoteList", "()Ljava/util/HashMap;", "setAiQueryNoteList", "(Ljava/util/HashMap;)V", "aiQueryNoteIdArr", "Ljava/util/ArrayList;", "Lkotlin/collections/ArrayList;", "getAiQueryNoteIdArr", "()Ljava/util/ArrayList;", "setAiQueryNoteIdArr", "(Ljava/util/ArrayList;)V", "isSupportAiQuery", "", "isNeedRequestPermission", "isSupportCapability", "checkPermission", "requestPermission", "isAiModelInitSuccess", "getProgress", "Lkotlin/Pair;", "initCapability", SmartAction.Feature.QUERY, "Landroid/os/Bundle;", "body", "timeout", "params", "", "(Ljava/lang/String;I[Ljava/lang/String;)Landroid/os/Bundle;", "startQuery", "searchText", "(Ljava/lang/String;I[Ljava/lang/String;)Lkotlin/Pair;", "addQueryNoteExtra", "", NotifyUtils.KEY_NOTE_ID, "Lorg/json/JSONObject;", "strToLong", "str", "strToInt", "clearQueryNoteList", "startSearchActivity", "bindContext", "Lcom/miui/notes/adapter/BindContext;", "destroySearchActivity", CoreConstants.CONTEXT_SCOPE_VALUE, "Landroid/content/Context;", "isFromCreate", "Companion", "app_PhoneGlobalRelease"}, k = 1, mv = {2, 2, 0}, xi = 48)
public final class AiQueryCapabilityAccess {

    /* JADX INFO: renamed from: Companion, reason: from kotlin metadata */
    public static final Companion INSTANCE = new Companion(null);
    private static final Lazy<AiQueryCapabilityAccess> instance$delegate = LazyKt.lazy(LazyThreadSafetyMode.SYNCHRONIZED, new Function0() { // from class: com.miui.notes.ainlsquery.AiQueryCapabilityAccess$$ExternalSyntheticLambda0
        @Override // kotlin.jvm.functions.Function0
        public final Object invoke() {
            return AiQueryCapabilityAccess.instance_delegate$lambda$3();
        }
    });
    private final int RESULT_OK;
    private ArrayList<Long> aiQueryNoteIdArr;
    private HashMap<Long, AiQueryNoteExtra> aiQueryNoteList;
    private String chunk_end;
    private String chunk_start;
    private String chunk_text;
    private String date;
    private String highlight_keywords;
    private String size;
    private String title;
    private final Uri uri;
    private final String TAG = "Notes:AiQueryCapabilityAccess";
    private final String KEV_STATUS = "Status";
    private final String KEY_CAPABILITY = Constants.KEY_CAPABILITY;
    private final String KEY_SCENE_STATUS = "Status";
    private final String KEY_EVENT = Constants.KEY_EVENT;
    private final String KEY_PERMISSION_STATE = Constants.KEY_PERMISSION_STATE;
    private final String KEY_PERMISSION_LIST = Constants.KEY_PERMISSION_LIST;
    private final String KEY_QUERY_RESULTS = "results";
    private final String KEY_CLIENT = "client";
    private final String KEY_QUERY_BODY = "body";
    private final String KEY_QUERY_ID = "query_id";
    private final String KEY_SESSION_ID = "session_id";
    private final String KEY_TIMEOUT = "timeout";
    private final String KEY_SCOPE = Action.SCOPE_ATTRIBUTE;
    private final String KEY_EXTRA_PARAMS = "extra_params";
    private final String KEY_LLM_SUGGESTIONS = "suggestions";
    private final String KEY_LLM_RESPONSE = ah.H;
    private final String KEY_SEARCH_DB_PROGRESS = "progress";
    private final int RESULT_SERVICE_CONNECT_FAILED = -1001;
    private final int RESULT_UNKNOWN_ERROR = -1002;
    private final int RESULT_NOT_SUPPORT_DOMAIN = -2001;
    private final int RESULT_NOT_SUPPORT_CAPABILITY = -2002;
    private final int RESULT_SERVER_NO_PERMISSION = -2003;
    private final int RESULT_CLIENT_NO_PERMISSION = -4001;
    private final int RESULT_CLIENT_ERROR_PARAMS = -4002;
    private final int RESULT_SWITCH_NOT_OPEN = -3001;
    private final int USE_NORMAL_SEARCH = -1003;
    private final String METHOD_INIT = "init_nls";
    private final String METHOD_QUERY = SmartAction.Feature.QUERY;
    private final String METHOD_REQUEST_PERMISSION = "request_permission";
    private final String METHOD_CHECK_PERMISSION = "check_permission";
    private final String METHOD_IS_SUPPORT = "is_support";
    private final String METHOD_GET_PROGRESS = "get_progress";
    private final String KEY_IN_PROGRESS = "in_progress";
    private String content_id = "content_id";
    private String extra = CallMethod.ARG_EXTRA_STRING;
    private final int QUERY_GALLERY_CAPABILITY = 1;
    private final int QUERY_NOTE_CAPABILITY = 2;
    private final int QUERY_SMS_CAPABILITY = 4;
    private final int QUERY_FILES_CAPABILITY = 8;
    private final int QUERY_RECORDING_CAPABILITY = 16;
    private final int CRS_CAP_NLU_QUERY = 5000;
    private final String SERVICE_PACKAGE_NAME = NotesNormalConstants.AICR_PACKAGE;

    private AiQueryCapabilityAccess() {
        Uri uri = Uri.parse("content://com.xiaomi.aicr.provider.NLSCapabilityProvider");
        Intrinsics.checkNotNullExpressionValue(uri, "parse(...)");
        this.uri = uri;
        this.title = "title";
        this.date = "date";
        this.size = "size";
        this.chunk_text = "chunk_text";
        this.chunk_start = "chunk_start";
        this.chunk_end = "chunk_end";
        this.highlight_keywords = "highlight_keywords";
        this.aiQueryNoteList = new HashMap<>();
        this.aiQueryNoteIdArr = new ArrayList<>();
    }

    /* JADX INFO: compiled from: AiQueryCapabilityAccess.kt */
    @Metadata(d1 = {"\u0000\u0014\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0005\b\u0086\u0003\u0018\u00002\u00020\u0001B\t\b\u0002¢\u0006\u0004\b\u0002\u0010\u0003R\u001b\u0010\u0004\u001a\u00020\u00058FX\u0086\u0084\u0002¢\u0006\f\n\u0004\b\b\u0010\t\u001a\u0004\b\u0006\u0010\u0007¨\u0006\n"}, d2 = {"Lcom/miui/notes/ainlsquery/AiQueryCapabilityAccess$Companion;", "", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "()V", "instance", "Lcom/miui/notes/ainlsquery/AiQueryCapabilityAccess;", "getInstance", "()Lcom/miui/notes/ainlsquery/AiQueryCapabilityAccess;", "instance$delegate", "Lkotlin/Lazy;", "app_PhoneGlobalRelease"}, k = 1, mv = {2, 2, 0}, xi = 48)
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }

        public final AiQueryCapabilityAccess getInstance() {
            return (AiQueryCapabilityAccess) AiQueryCapabilityAccess.instance$delegate.getValue();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final AiQueryCapabilityAccess instance_delegate$lambda$3() {
        return new AiQueryCapabilityAccess();
    }

    public final String getTAG() {
        return this.TAG;
    }

    public final String getKEV_STATUS() {
        return this.KEV_STATUS;
    }

    public final String getKEY_CAPABILITY() {
        return this.KEY_CAPABILITY;
    }

    public final String getKEY_SCENE_STATUS() {
        return this.KEY_SCENE_STATUS;
    }

    public final String getKEY_EVENT() {
        return this.KEY_EVENT;
    }

    public final String getKEY_PERMISSION_STATE() {
        return this.KEY_PERMISSION_STATE;
    }

    public final String getKEY_PERMISSION_LIST() {
        return this.KEY_PERMISSION_LIST;
    }

    public final String getKEY_QUERY_RESULTS() {
        return this.KEY_QUERY_RESULTS;
    }

    public final String getKEY_CLIENT() {
        return this.KEY_CLIENT;
    }

    public final String getKEY_QUERY_BODY() {
        return this.KEY_QUERY_BODY;
    }

    public final String getKEY_QUERY_ID() {
        return this.KEY_QUERY_ID;
    }

    public final String getKEY_SESSION_ID() {
        return this.KEY_SESSION_ID;
    }

    public final String getKEY_TIMEOUT() {
        return this.KEY_TIMEOUT;
    }

    public final String getKEY_SCOPE() {
        return this.KEY_SCOPE;
    }

    public final String getKEY_EXTRA_PARAMS() {
        return this.KEY_EXTRA_PARAMS;
    }

    public final String getKEY_LLM_SUGGESTIONS() {
        return this.KEY_LLM_SUGGESTIONS;
    }

    public final String getKEY_LLM_RESPONSE() {
        return this.KEY_LLM_RESPONSE;
    }

    public final String getKEY_SEARCH_DB_PROGRESS() {
        return this.KEY_SEARCH_DB_PROGRESS;
    }

    public final int getRESULT_OK() {
        return this.RESULT_OK;
    }

    public final int getRESULT_SERVICE_CONNECT_FAILED() {
        return this.RESULT_SERVICE_CONNECT_FAILED;
    }

    public final int getRESULT_UNKNOWN_ERROR() {
        return this.RESULT_UNKNOWN_ERROR;
    }

    public final int getRESULT_NOT_SUPPORT_DOMAIN() {
        return this.RESULT_NOT_SUPPORT_DOMAIN;
    }

    public final int getRESULT_NOT_SUPPORT_CAPABILITY() {
        return this.RESULT_NOT_SUPPORT_CAPABILITY;
    }

    public final int getRESULT_SERVER_NO_PERMISSION() {
        return this.RESULT_SERVER_NO_PERMISSION;
    }

    public final int getRESULT_CLIENT_NO_PERMISSION() {
        return this.RESULT_CLIENT_NO_PERMISSION;
    }

    public final int getRESULT_CLIENT_ERROR_PARAMS() {
        return this.RESULT_CLIENT_ERROR_PARAMS;
    }

    public final int getRESULT_SWITCH_NOT_OPEN() {
        return this.RESULT_SWITCH_NOT_OPEN;
    }

    public final int getUSE_NORMAL_SEARCH() {
        return this.USE_NORMAL_SEARCH;
    }

    public final String getMETHOD_INIT() {
        return this.METHOD_INIT;
    }

    public final String getMETHOD_QUERY() {
        return this.METHOD_QUERY;
    }

    public final String getMETHOD_REQUEST_PERMISSION() {
        return this.METHOD_REQUEST_PERMISSION;
    }

    public final String getMETHOD_CHECK_PERMISSION() {
        return this.METHOD_CHECK_PERMISSION;
    }

    public final String getMETHOD_IS_SUPPORT() {
        return this.METHOD_IS_SUPPORT;
    }

    public final String getMETHOD_GET_PROGRESS() {
        return this.METHOD_GET_PROGRESS;
    }

    public final String getKEY_IN_PROGRESS() {
        return this.KEY_IN_PROGRESS;
    }

    public final String getContent_id() {
        return this.content_id;
    }

    public final void setContent_id(String str) {
        Intrinsics.checkNotNullParameter(str, "<set-?>");
        this.content_id = str;
    }

    public final String getExtra() {
        return this.extra;
    }

    public final void setExtra(String str) {
        Intrinsics.checkNotNullParameter(str, "<set-?>");
        this.extra = str;
    }

    public final int getQUERY_GALLERY_CAPABILITY() {
        return this.QUERY_GALLERY_CAPABILITY;
    }

    public final int getQUERY_NOTE_CAPABILITY() {
        return this.QUERY_NOTE_CAPABILITY;
    }

    public final int getQUERY_SMS_CAPABILITY() {
        return this.QUERY_SMS_CAPABILITY;
    }

    public final int getQUERY_FILES_CAPABILITY() {
        return this.QUERY_FILES_CAPABILITY;
    }

    public final int getQUERY_RECORDING_CAPABILITY() {
        return this.QUERY_RECORDING_CAPABILITY;
    }

    public final int getCRS_CAP_NLU_QUERY() {
        return this.CRS_CAP_NLU_QUERY;
    }

    public final Uri getUri() {
        return this.uri;
    }

    public final String getTitle() {
        return this.title;
    }

    public final void setTitle(String str) {
        Intrinsics.checkNotNullParameter(str, "<set-?>");
        this.title = str;
    }

    public final String getDate() {
        return this.date;
    }

    public final void setDate(String str) {
        Intrinsics.checkNotNullParameter(str, "<set-?>");
        this.date = str;
    }

    public final String getSize() {
        return this.size;
    }

    public final void setSize(String str) {
        Intrinsics.checkNotNullParameter(str, "<set-?>");
        this.size = str;
    }

    public final String getChunk_text() {
        return this.chunk_text;
    }

    public final void setChunk_text(String str) {
        Intrinsics.checkNotNullParameter(str, "<set-?>");
        this.chunk_text = str;
    }

    public final String getChunk_start() {
        return this.chunk_start;
    }

    public final void setChunk_start(String str) {
        Intrinsics.checkNotNullParameter(str, "<set-?>");
        this.chunk_start = str;
    }

    public final String getChunk_end() {
        return this.chunk_end;
    }

    public final void setChunk_end(String str) {
        Intrinsics.checkNotNullParameter(str, "<set-?>");
        this.chunk_end = str;
    }

    public final String getHighlight_keywords() {
        return this.highlight_keywords;
    }

    public final void setHighlight_keywords(String str) {
        Intrinsics.checkNotNullParameter(str, "<set-?>");
        this.highlight_keywords = str;
    }

    public final void setAiQueryNoteList(HashMap<Long, AiQueryNoteExtra> map) {
        this.aiQueryNoteList = map;
    }

    public final HashMap<Long, AiQueryNoteExtra> getAiQueryNoteList() {
        return this.aiQueryNoteList;
    }

    public final void setAiQueryNoteIdArr(ArrayList<Long> arrayList) {
        Intrinsics.checkNotNullParameter(arrayList, "<set-?>");
        this.aiQueryNoteIdArr = arrayList;
    }

    public final ArrayList<Long> getAiQueryNoteIdArr() {
        return this.aiQueryNoteIdArr;
    }

    /* JADX WARN: Code duplicated, block: B:12:0x0021  */
    public final boolean isSupportAiQuery() {
        boolean z;
        if (isSupportCapability() == this.QUERY_NOTE_CAPABILITY) {
            z = true;
            if (checkPermission() != this.QUERY_NOTE_CAPABILITY && (!isNeedRequestPermission() || requestPermission() != this.QUERY_NOTE_CAPABILITY)) {
                z = false;
            }
        } else {
            z = false;
        }
        Logger.INSTANCE.d(this.TAG, "isSupportCapability isSupport  :" + z);
        return z;
    }

    public final boolean isNeedRequestPermission() {
        int packageCode = Utils.getPackageCode();
        int packageCode2 = Utils.getPackageCode(this.SERVICE_PACKAGE_NAME);
        boolean z = true;
        if (PreferenceUtils.getIsFirstRequestMIAIPermission()) {
            PreferenceUtils.setIsFirstRequestMIAIPermission();
            PreferenceUtils.setMyPackageVersionCode(packageCode);
            PreferenceUtils.setMIAIPackageVersionCode(packageCode2);
        } else if ((packageCode == 0 || packageCode <= PreferenceUtils.getMyPackageVersionCode()) && (packageCode2 == 0 || packageCode2 <= PreferenceUtils.getMIAIPackageVersionCode())) {
            z = false;
        } else {
            PreferenceUtils.setMyPackageVersionCode(packageCode);
            PreferenceUtils.setMIAIPackageVersionCode(packageCode2);
        }
        Logger.INSTANCE.d(this.TAG, "isSupportCapability isNeedRequestPermission :" + z);
        return z;
    }

    private final int isSupportCapability() {
        int i = this.RESULT_NOT_SUPPORT_CAPABILITY;
        Bundle bundle = new Bundle();
        bundle.putInt(this.KEY_CAPABILITY, this.CRS_CAP_NLU_QUERY);
        bundle.putInt(this.KEY_SCOPE, this.QUERY_NOTE_CAPABILITY);
        try {
            Bundle bundleCall = BaseNoteApp.INSTANCE.getInstance().getContentResolver().call(this.uri, this.METHOD_IS_SUPPORT, BaseNoteApp.INSTANCE.getInstance().getPackageName(), bundle);
            if (bundleCall != null) {
                i = bundleCall.getInt(this.KEY_SCENE_STATUS, this.RESULT_NOT_SUPPORT_CAPABILITY);
            }
        } catch (Exception e) {
            Logger.INSTANCE.d(this.TAG, "isSupportCapability : " + e);
        }
        Logger.INSTANCE.d(this.TAG, "isSupportCapability state  :" + i);
        return i;
    }

    private final int checkPermission() {
        int i = this.RESULT_UNKNOWN_ERROR;
        Bundle bundle = new Bundle();
        bundle.putInt(this.KEY_CAPABILITY, this.CRS_CAP_NLU_QUERY);
        bundle.putInt(this.KEY_SCOPE, this.QUERY_NOTE_CAPABILITY);
        try {
            Bundle bundleCall = BaseNoteApp.INSTANCE.getInstance().getContentResolver().call(this.uri, this.METHOD_CHECK_PERMISSION, BaseNoteApp.INSTANCE.getInstance().getPackageName(), bundle);
            if (bundleCall != null) {
                i = bundleCall.getInt(this.KEY_SCENE_STATUS, this.RESULT_UNKNOWN_ERROR);
            }
        } catch (Exception e) {
            Logger.INSTANCE.d(this.TAG, "checkPermission : " + e);
        }
        Logger.INSTANCE.d(this.TAG, "checkPermission state  :" + i);
        return i;
    }

    private final int requestPermission() {
        int i = this.RESULT_UNKNOWN_ERROR;
        Bundle bundle = new Bundle();
        bundle.putInt(this.KEY_CAPABILITY, this.CRS_CAP_NLU_QUERY);
        bundle.putInt(this.KEY_SCOPE, this.QUERY_NOTE_CAPABILITY);
        try {
            Bundle bundleCall = BaseNoteApp.INSTANCE.getInstance().getContentResolver().call(this.uri, this.METHOD_REQUEST_PERMISSION, BaseNoteApp.INSTANCE.getInstance().getPackageName(), bundle);
            if (bundleCall != null) {
                i = bundleCall.getInt(this.KEY_SCENE_STATUS, this.RESULT_UNKNOWN_ERROR);
            }
        } catch (Exception e) {
            Logger.INSTANCE.d(this.TAG, "requestPermission : " + e);
        }
        Logger.INSTANCE.d(this.TAG, "requestPermission state  :" + i);
        return i;
    }

    public final boolean isAiModelInitSuccess() {
        return initCapability() > 0;
    }

    public final Pair<Boolean, Integer> getProgress() {
        int i = this.RESULT_UNKNOWN_ERROR;
        Bundle bundle = new Bundle();
        bundle.putInt(this.KEY_SCOPE, this.QUERY_NOTE_CAPABILITY);
        boolean z = true;
        try {
            Bundle bundleCall = BaseNoteApp.INSTANCE.getInstance().getContentResolver().call(this.uri, this.METHOD_GET_PROGRESS, BaseNoteApp.INSTANCE.getInstance().getPackageName(), bundle);
            Logger.INSTANCE.d(this.TAG, "getProgress : " + (bundleCall != null));
            if (bundleCall != null) {
                i = bundleCall.getInt(this.KEY_IN_PROGRESS, this.RESULT_UNKNOWN_ERROR);
                int i2 = this.RESULT_UNKNOWN_ERROR;
                if (i == i2) {
                    try {
                        i = bundleCall.getInt(this.KEY_SEARCH_DB_PROGRESS, i2);
                        z = false;
                    } catch (Exception e) {
                        e = e;
                        z = false;
                        Logger.INSTANCE.e(this.TAG, "getProgress : " + e);
                    }
                }
                Logger.INSTANCE.d(this.TAG, "getProgress state : " + i + " isNew : " + z);
            }
        } catch (Exception e2) {
            e = e2;
        }
        return new Pair<>(Boolean.valueOf(z), Integer.valueOf(i));
    }

    private final int initCapability() {
        int i = this.RESULT_UNKNOWN_ERROR;
        Bundle bundle = new Bundle();
        bundle.putInt(this.KEY_SCOPE, this.QUERY_NOTE_CAPABILITY);
        try {
            Bundle bundleCall = BaseNoteApp.INSTANCE.getInstance().getContentResolver().call(this.uri, this.METHOD_INIT, BaseNoteApp.INSTANCE.getInstance().getPackageName(), bundle);
            if (bundleCall != null) {
                i = bundleCall.getInt(this.KEY_SCENE_STATUS, this.RESULT_UNKNOWN_ERROR);
            }
        } catch (Exception e) {
            Logger.INSTANCE.d(this.TAG, "initCapability : " + e);
        }
        Logger.INSTANCE.d(this.TAG, "initCapability state  :" + i);
        return i;
    }

    public final Bundle query(String body, int timeout, String... params) {
        Intrinsics.checkNotNullParameter(params, "params");
        Bundle bundle = new Bundle();
        bundle.putString(this.KEY_QUERY_BODY, body);
        bundle.putInt(this.KEY_SCOPE, this.QUERY_NOTE_CAPABILITY);
        bundle.putInt(this.KEY_TIMEOUT, timeout);
        bundle.putStringArray(this.KEY_EXTRA_PARAMS, params);
        try {
            Bundle bundleCall = BaseNoteApp.INSTANCE.getInstance().getContentResolver().call(this.uri, this.METHOD_QUERY, BaseNoteApp.INSTANCE.getInstance().getPackageName(), bundle);
            if (bundleCall != null) {
                Logger.INSTANCE.d(this.TAG, "query res != null");
                if (bundleCall.getInt(this.KEV_STATUS, this.RESULT_UNKNOWN_ERROR) < 0) {
                    return null;
                }
                return bundleCall;
            }
        } catch (Exception e) {
            Logger.INSTANCE.d(this.TAG, "query : " + e);
        }
        return null;
    }

    public final Pair<Boolean, String> startQuery(String searchText, int timeout, String... params) throws JSONException {
        Intrinsics.checkNotNullParameter(params, "params");
        Bundle bundleQuery = query(searchText, 500, new String[0]);
        StringBuilder sb = new StringBuilder();
        clearQueryNoteList();
        getAiQueryNoteIdArr().clear();
        if (bundleQuery != null) {
            Logger.INSTANCE.d(this.TAG, "startQuery KEV_STATUS  :" + bundleQuery.getInt(this.KEV_STATUS));
            if (bundleQuery.getInt(this.KEV_STATUS) == this.USE_NORMAL_SEARCH) {
                return new Pair<>(false, sb.toString());
            }
            String string = bundleQuery.getString(this.KEY_QUERY_RESULTS);
            if (string != null) {
                JSONArray jSONArray = new JSONArray(string);
                sb.append("_id IN (");
                int length = jSONArray.length();
                for (int i = 0; i < length; i++) {
                    JSONArray jSONArrayOptJSONArray = jSONArray.getJSONObject(i).optJSONArray("cursor");
                    if (jSONArrayOptJSONArray != null) {
                        int length2 = jSONArrayOptJSONArray.length();
                        for (int i2 = 0; i2 < length2; i2++) {
                            JSONObject jSONObject = jSONArrayOptJSONArray.getJSONObject(i2);
                            String strOptString = jSONObject.optString("content_id");
                            jSONObject.optString("type");
                            JSONObject jSONObjectOptJSONObject = jSONObject.optJSONObject(CallMethod.ARG_EXTRA_STRING);
                            sb.append(strOptString);
                            if (i2 != jSONArrayOptJSONArray.length() - 1) {
                                sb.append(",");
                            }
                            ArrayList<Long> aiQueryNoteIdArr = getAiQueryNoteIdArr();
                            Intrinsics.checkNotNull(strOptString);
                            aiQueryNoteIdArr.add(Long.valueOf(strToLong(strOptString)));
                            if (jSONObjectOptJSONObject != null) {
                                addQueryNoteExtra(strOptString, jSONObjectOptJSONObject);
                            }
                        }
                    }
                }
                sb.append(")");
                Logger.INSTANCE.d(this.TAG, "startQuery selection  :" + ((Object) sb));
            } else {
                sb.append("_id IN (-100)");
                Logger.INSTANCE.d(this.TAG, "startQuery selection2  :" + ((Object) sb));
            }
            return new Pair<>(true, sb.toString());
        }
        return new Pair<>(false, sb.toString());
    }

    public final void addQueryNoteExtra(String noteId, JSONObject extra) {
        Intrinsics.checkNotNullParameter(noteId, "noteId");
        if (extra != null) {
            String strOptString = extra.optString(this.title);
            String strOptString2 = extra.optString(this.date);
            String strOptString3 = extra.optString(this.chunk_text);
            String strOptString4 = extra.optString(this.chunk_start);
            Intrinsics.checkNotNullExpressionValue(strOptString4, "optString(...)");
            int iStrToInt = strToInt(strOptString4);
            String strOptString5 = extra.optString(this.chunk_end);
            Intrinsics.checkNotNullExpressionValue(strOptString5, "optString(...)");
            int iStrToInt2 = strToInt(strOptString5);
            JSONArray jSONArrayOptJSONArray = extra.optJSONArray(this.highlight_keywords);
            ArrayList arrayList = new ArrayList();
            if (jSONArrayOptJSONArray != null) {
                int length = jSONArrayOptJSONArray.length();
                for (int i = 0; i < length; i++) {
                    String strOptString6 = jSONArrayOptJSONArray.optString(i);
                    if (!TextUtils.isEmpty(strOptString6)) {
                        arrayList.add(strOptString6);
                    }
                }
            }
            CollectionsKt.sortWith(arrayList, new Comparator() { // from class: com.miui.notes.ainlsquery.AiQueryCapabilityAccess$addQueryNoteExtra$lambda$2$$inlined$compareByDescending$1
                /* JADX WARN: Multi-variable type inference failed */
                @Override // java.util.Comparator
                public final int compare(T t, T t2) {
                    return ComparisonsKt.compareValues(Integer.valueOf(((String) t2).length()), Integer.valueOf(((String) t).length()));
                }
            });
            Intrinsics.checkNotNull(strOptString);
            Intrinsics.checkNotNull(strOptString2);
            Intrinsics.checkNotNull(strOptString3);
            AiQueryNoteExtra aiQueryNoteExtra = new AiQueryNoteExtra(strOptString, strOptString2, 0, strOptString3, iStrToInt, iStrToInt2, arrayList);
            Logger.INSTANCE.d(this.TAG, "startQuery aiQueryNoteExtra ");
            HashMap<Long, AiQueryNoteExtra> aiQueryNoteList = getAiQueryNoteList();
            if (aiQueryNoteList != null) {
                aiQueryNoteList.put(Long.valueOf(strToLong(noteId)), aiQueryNoteExtra);
            }
        }
    }

    private final long strToLong(String str) {
        try {
            return Long.parseLong(str);
        } catch (Exception e) {
            e.printStackTrace();
            return 0L;
        }
    }

    private final int strToInt(String str) {
        try {
            return Integer.parseInt(str);
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    public final void clearQueryNoteList() {
        HashMap<Long, AiQueryNoteExtra> aiQueryNoteList = getAiQueryNoteList();
        if (aiQueryNoteList != null) {
            aiQueryNoteList.clear();
        }
    }

    public final void startSearchActivity(BindContext bindContext) {
        Intrinsics.checkNotNullParameter(bindContext, "bindContext");
        Intent intent = new Intent();
        intent.setAction(NotesNormalConstants.AICR_SEARCH_DB_PROGRESS);
        intent.setClassName(NotesNormalConstants.AICR_PACKAGE, NotesNormalConstants.AICR_SEARCH_ACTIVITY);
        try {
            ActivityResultLauncher<Intent> searchActivityLauncher = bindContext.getSearchActivityLauncher();
            if (searchActivityLauncher != null) {
                searchActivityLauncher.launch(intent);
            }
            bindContext.setIsNeedDestroySearch(true);
            bindContext.setCheckAiQueryProgress(true);
        } catch (Exception e) {
            Logger.INSTANCE.d(this.TAG, e.toString());
        }
    }

    public final void destroySearchActivity(BindContext bindContext, Context context, boolean isFromCreate) {
        Intrinsics.checkNotNullParameter(bindContext, "bindContext");
        Intrinsics.checkNotNullParameter(context, "context");
        Intent intent = new Intent();
        intent.putExtra("destroy", true);
        intent.setAction(NotesNormalConstants.AICR_SEARCH_DB_PROGRESS);
        intent.addFlags(VisionAttribute.VISION_TYPE_CLASS_ENTITY);
        try {
            if (!bindContext.getIsIsNeedDestroySearch() && !isFromCreate) {
                return;
            }
            context.startActivity(intent);
            bindContext.setIsNeedDestroySearch(false);
        } catch (Exception e) {
            Logger.INSTANCE.d(this.TAG, "onDestroySearchMode: " + e);
        }
    }
}
