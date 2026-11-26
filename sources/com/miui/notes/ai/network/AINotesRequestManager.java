package com.miui.notes.ai.network;

import android.accounts.AuthenticatorException;
import android.util.Base64;
import androidx.exifinterface.media.ExifInterface;
import androidx.lifecycle.MutableLiveData;
import ch.qos.logback.core.CoreConstants;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.market.sdk.reflect.ReflectUtilsForMiui;
import com.miui.aiengine.common.Error;
import com.miui.aiengine.common.RequestManager;
import com.miui.aiengine.common.Result;
import com.miui.aiengine.common.SUCCESS;
import com.miui.aiengine.common.request.HttpMethod;
import com.miui.notes.ai.data.input.UserInputHolder;
import com.miui.notes.ai.network.request.AIError;
import com.miui.notes.ai.network.request.AIResult;
import com.miui.notes.ai.network.request.CorrectErrorCombineData;
import com.miui.notes.ai.network.request.ResponseData;
import com.miui.notes.ai.network.request.ResponseErrorDetail;
import com.miui.notes.ai.network.request.ResultContentCombine;
import com.miui.notes.ai.network.request.ResultGenerateCombine;
import com.miui.notes.ai.request.AINotesViewModel;
import com.miui.notes.ai.utils.RomUtils;
import com.miui.notes.base.utils.Logger;
import defpackage.R2;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.CancellationException;
import kotlin.Metadata;
import kotlin.NoWhenBranchMatchedException;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.IntrinsicsKt;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.SpillingKt;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.Charsets;
import kotlinx.coroutines.BuildersKt;
import kotlinx.coroutines.BuildersKt__Builders_commonKt;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.CoroutineScopeKt;
import kotlinx.coroutines.Dispatchers;
import kotlinx.coroutines.Job;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowCollector;
import org.ccil.cowan.tagsoup.XMLWriter;

/* JADX INFO: compiled from: AINotesRequestManager.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000\u0088\u0001\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\r\n\u0002\u0010\u000b\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\u0018\u0000 E2\u00020\u0001:\u0001EB\u0007¢\u0006\u0004\b\u0002\u0010\u0003J\u000e\u0010)\u001a\u00020\u001f2\u0006\u0010*\u001a\u00020\u000fJ\u0010\u0010+\u001a\u00020\u001f2\b\u0010*\u001a\u0004\u0018\u00010\u000fJ\u000e\u0010,\u001a\u00020\u001f2\u0006\u0010-\u001a\u00020\u000fJ \u0010.\u001a\u00020\u001f2\b\u0010*\u001a\u0004\u0018\u00010\u000f2\u0006\u0010/\u001a\u00020\u000f2\u0006\u00100\u001a\u000201J\u0010\u00102\u001a\u00020\u001f2\b\u0010*\u001a\u0004\u0018\u00010\u000fJ\u0010\u00103\u001a\u00020\u001f2\b\u0010*\u001a\u0004\u0018\u00010\u000fJ¾\u0001\u00104\u001a\u00020#\"\u0004\b\u0000\u001052\u0006\u00106\u001a\u00020\u000f2&\u00107\u001a\"\u0012\u0004\u0012\u00020\u000f\u0012\u0004\u0012\u00020\u000f\u0018\u000108j\u0010\u0012\u0004\u0012\u00020\u000f\u0012\u0004\u0012\u00020\u000f\u0018\u0001`92\u0012\u0010:\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u0002H50\u00060\u00052\u0012\u0010;\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u001a0\u00190\u00052\n\b\u0002\u0010<\u001a\u0004\u0018\u00010=2(\b\u0002\u0010>\u001a\"\u0012\u0004\u0012\u00020\u000f\u0012\u0004\u0012\u00020\u000f\u0018\u000108j\u0010\u0012\u0004\u0012\u00020\u000f\u0012\u0004\u0012\u00020\u000f\u0018\u0001`92\u0016\b\u0002\u0010\u001c\u001a\u0010\u0012\u0004\u0012\u00020\u001e\u0012\u0004\u0012\u00020\u001f\u0018\u00010\u001d2\b\b\u0002\u0010?\u001a\u00020@H\u0002J\u001a\u0010A\u001a\b\u0012\u0004\u0012\u00020\u001a0\u00192\n\u0010B\u001a\u0006\u0012\u0002\b\u00030CH\u0002J\u0006\u0010D\u001a\u00020\u001fR\u001d\u0010\u0004\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00070\u00060\u0005¢\u0006\b\n\u0000\u001a\u0004\b\b\u0010\tR\u001d\u0010\n\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u000b0\u00060\u0005¢\u0006\b\n\u0000\u001a\u0004\b\f\u0010\tR#\u0010\r\u001a\u0014\u0012\u0010\u0012\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u000f0\u000e0\u00060\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u0010\u0010\tR\u001d\u0010\u0011\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00120\u00060\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u0013\u0010\tR\u001d\u0010\u0014\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00070\u00060\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u0015\u0010\tR\u001d\u0010\u0016\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00070\u00060\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u0017\u0010\tR\u001d\u0010\u0018\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u001a0\u00190\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u001b\u0010\tR\u001d\u0010\u001c\u001a\u000e\u0012\u0004\u0012\u00020\u001e\u0012\u0004\u0012\u00020\u001f0\u001d¢\u0006\b\n\u0000\u001a\u0004\b \u0010!R\u0010\u0010\"\u001a\u0004\u0018\u00010#X\u0082\u000e¢\u0006\u0002\n\u0000R\u0010\u0010$\u001a\u0004\u0018\u00010#X\u0082\u000e¢\u0006\u0002\n\u0000R\u0010\u0010%\u001a\u0004\u0018\u00010#X\u0082\u000e¢\u0006\u0002\n\u0000R\u0010\u0010&\u001a\u0004\u0018\u00010#X\u0082\u000e¢\u0006\u0002\n\u0000R\u0010\u0010'\u001a\u0004\u0018\u00010#X\u0082\u000e¢\u0006\u0002\n\u0000R\u0010\u0010(\u001a\u0004\u0018\u00010#X\u0082\u000e¢\u0006\u0002\n\u0000¨\u0006F"}, d2 = {"Lcom/miui/notes/ai/network/AINotesRequestManager;", "", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "()V", "_summaryLiveData", "Landroidx/lifecycle/MutableLiveData;", "Lcom/miui/notes/ai/network/request/AIResult;", "Lcom/miui/notes/ai/network/request/ResultContentCombine;", "get_summaryLiveData", "()Landroidx/lifecycle/MutableLiveData;", "_correctErrorsLiveData", "Lcom/miui/notes/ai/network/request/CorrectErrorCombineData;", "get_correctErrorsLiveData", "_recommendLiveData", "", "", "get_recommendLiveData", "_generateTextLiveData", "Lcom/miui/notes/ai/network/request/ResultGenerateCombine;", "get_generateTextLiveData", "_polishTextLiveData", "get_polishTextLiveData", "_expandTextLiveData", "get_expandTextLiveData", "_errorLiveData", "Lcom/miui/notes/ai/network/request/AIError;", "Lcom/miui/notes/ai/network/request/ResponseErrorDetail;", "get_errorLiveData", "sseCallback", "Lkotlin/Function1;", "Lcom/miui/aiengine/common/Result;", "", "getSseCallback", "()Lkotlin/jvm/functions/Function1;", "summarizeJob", "Lkotlinx/coroutines/Job;", "correctErrorsJob", "recommendJob", "generateTextJob", "polishTextJob", "expendTextJob", "requestSummarizeText", "inputText", "requestCorrectErrors", "requestRecommend", "packageName", "requestGenerateText", "userPrompt", "enableDeepSeek", "", "requestPolishText", "requestExpandText", "handleResponse", ExifInterface.GPS_DIRECTION_TRUE, "url", "params", "Ljava/util/HashMap;", "Lkotlin/collections/HashMap;", "data", "errorListener", "type", "Ljava/lang/reflect/Type;", "header", XMLWriter.METHOD, "Lcom/miui/aiengine/common/request/HttpMethod;", "delaResult", "responseData", "Lcom/miui/notes/ai/network/request/ResponseData;", "cancelAll", "Companion", "libAiTextWidget_release"}, k = 1, mv = {2, 2, 0}, xi = 48)
public final class AINotesRequestManager {
    private static final String BASE_STAGING_URL = "aHR0cHM6Ly9zdGFnaW5nLWFpLmVuZ2luZS5taXVpLmNvbQ==";
    private static final String BASE_URL = "aHR0cHM6Ly9haS5lbmdpbmUubWl1aS5jb20=";
    private static final String BASE_URL_INTERNATIONAL = "aHR0cHM6Ly9haS5lbmdpbmUuaW50bC5taXVpLmNvbQ==";

    /* JADX INFO: renamed from: Companion, reason: from kotlin metadata */
    public static final Companion INSTANCE;
    public static final boolean DEBUG = false;
    public static final int ERROR_CODE_FOREVER_BANNED = 20099125;
    public static final int ERROR_CODE_ILLEGAL_1 = 20099126;
    public static final int ERROR_CODE_ILLEGAL_2 = 20099127;
    public static final int ERROR_CODE_LANGUAGE_NOT_SUPPORT = 20090121;
    public static final int ERROR_CODE_NOT_SUPPORT = 20090115;
    public static final int ERROR_CODE_TEMP_BANNED = 20099124;
    public static final int ERROR_CODE_TEXT_LENGTH_EXCEED_LIMIT = 20090113;
    public static final String LANG_CODE = "langCode";
    public static final String PACKAGE_NAME = "packageName";
    public static final String PARAM_BIZ_CODE = "bizCode";
    public static final String PARAM_ENABLE_DEPTH = "enableDepth";
    public static final String PARAM_INPUT_TEXT = "inputText";
    public static final String PARAM_POLISH_STYLE = "polishStyle";
    public static final String PARAM_REGION_CODE = "regionCode";
    public static final String PARAM_USER_PROMPT = "userPrompt";
    public static final int RESULT_CODE_OK = 0;
    public static final int SYSTEM_UPGRADE_CODE = 20090003;
    private static final String TAG = "AINotesRequestManager";
    private static final String URL_CORRECT_ERRORS;
    private static final String URL_EXPAND_TEXT;
    private static final String URL_GENERATE_TEXT;
    private static final String URL_POLISH_TEXT;
    private static final String URL_RECOMMEND;
    private static final String URL_SUMMARY;
    public static final String VALUE_BIZ_CODE = "AiTextControl";
    private Job correctErrorsJob;
    private Job expendTextJob;
    private Job generateTextJob;
    private Job polishTextJob;
    private Job recommendJob;
    private Job summarizeJob;
    private final MutableLiveData<AIResult<ResultContentCombine>> _summaryLiveData = new MutableLiveData<>();
    private final MutableLiveData<AIResult<CorrectErrorCombineData>> _correctErrorsLiveData = new MutableLiveData<>();
    private final MutableLiveData<AIResult<List<String>>> _recommendLiveData = new MutableLiveData<>();
    private final MutableLiveData<AIResult<ResultGenerateCombine>> _generateTextLiveData = new MutableLiveData<>();
    private final MutableLiveData<AIResult<ResultContentCombine>> _polishTextLiveData = new MutableLiveData<>();
    private final MutableLiveData<AIResult<ResultContentCombine>> _expandTextLiveData = new MutableLiveData<>();
    private final MutableLiveData<AIError<ResponseErrorDetail>> _errorLiveData = new MutableLiveData<>();
    private final Function1<Result, Unit> sseCallback = new Function1() { // from class: com.miui.notes.ai.network.AINotesRequestManager$$ExternalSyntheticLambda0
        @Override // kotlin.jvm.functions.Function1
        public final Object invoke(Object obj) {
            return AINotesRequestManager.sseCallback$lambda$0((Result) obj);
        }
    };

    /* JADX INFO: compiled from: AINotesRequestManager.kt */
    @Metadata(d1 = {"\u0000$\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0003\n\u0002\u0010\b\n\u0002\b\t\n\u0002\u0010\u000e\n\u0002\b\u001a\n\u0002\u0010\u000b\n\u0002\b\u0003\b\u0086\u0003\u0018\u00002\u00020\u0001B\t\b\u0002¢\u0006\u0004\b\u0002\u0010\u0003J\b\u0010+\u001a\u00020\u000fH\u0002J\b\u0010,\u001a\u00020\u000fH\u0002R\u000e\u0010\u0004\u001a\u00020\u0005X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0005X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\u0005X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\b\u001a\u00020\u0005X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\t\u001a\u00020\u0005X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\n\u001a\u00020\u0005X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\u000b\u001a\u00020\u0005X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\f\u001a\u00020\u0005X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\r\u001a\u00020\u0005X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\u000e\u001a\u00020\u000fX\u0082T¢\u0006\u0002\n\u0000R\u000e\u0010\u0010\u001a\u00020\u000fX\u0082T¢\u0006\u0002\n\u0000R\u000e\u0010\u0011\u001a\u00020\u000fX\u0082T¢\u0006\u0002\n\u0000R\u000e\u0010\u0012\u001a\u00020\u000fX\u0082T¢\u0006\u0002\n\u0000R\u0011\u0010\u0013\u001a\u00020\u000f¢\u0006\b\n\u0000\u001a\u0004\b\u0014\u0010\u0015R\u0011\u0010\u0016\u001a\u00020\u000f¢\u0006\b\n\u0000\u001a\u0004\b\u0017\u0010\u0015R\u0011\u0010\u0018\u001a\u00020\u000f¢\u0006\b\n\u0000\u001a\u0004\b\u0019\u0010\u0015R\u0011\u0010\u001a\u001a\u00020\u000f¢\u0006\b\n\u0000\u001a\u0004\b\u001b\u0010\u0015R\u0011\u0010\u001c\u001a\u00020\u000f¢\u0006\b\n\u0000\u001a\u0004\b\u001d\u0010\u0015R\u0011\u0010\u001e\u001a\u00020\u000f¢\u0006\b\n\u0000\u001a\u0004\b\u001f\u0010\u0015R\u000e\u0010 \u001a\u00020\u000fX\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010!\u001a\u00020\u000fX\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\"\u001a\u00020\u000fX\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010#\u001a\u00020\u000fX\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010$\u001a\u00020\u000fX\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010%\u001a\u00020\u000fX\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010&\u001a\u00020\u000fX\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010'\u001a\u00020\u000fX\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010(\u001a\u00020\u000fX\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010)\u001a\u00020*X\u0086T¢\u0006\u0002\n\u0000¨\u0006-"}, d2 = {"Lcom/miui/notes/ai/network/AINotesRequestManager$Companion;", "", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "()V", "RESULT_CODE_OK", "", "SYSTEM_UPGRADE_CODE", "ERROR_CODE_TEXT_LENGTH_EXCEED_LIMIT", "ERROR_CODE_ILLEGAL_1", "ERROR_CODE_ILLEGAL_2", "ERROR_CODE_TEMP_BANNED", "ERROR_CODE_FOREVER_BANNED", "ERROR_CODE_NOT_SUPPORT", "ERROR_CODE_LANGUAGE_NOT_SUPPORT", "TAG", "", "BASE_URL_INTERNATIONAL", "BASE_URL", "BASE_STAGING_URL", "URL_SUMMARY", "getURL_SUMMARY", "()Ljava/lang/String;", "URL_CORRECT_ERRORS", "getURL_CORRECT_ERRORS", "URL_RECOMMEND", "getURL_RECOMMEND", "URL_GENERATE_TEXT", "getURL_GENERATE_TEXT", "URL_POLISH_TEXT", "getURL_POLISH_TEXT", "URL_EXPAND_TEXT", "getURL_EXPAND_TEXT", "PARAM_INPUT_TEXT", "PARAM_USER_PROMPT", "PARAM_POLISH_STYLE", CoreConstants.PACKAGE_NAME_KEY, "LANG_CODE", "PARAM_BIZ_CODE", "PARAM_ENABLE_DEPTH", "PARAM_REGION_CODE", "VALUE_BIZ_CODE", "DEBUG", "", "getBaseUrl", "getTestBaseUrl", "libAiTextWidget_release"}, k = 1, mv = {2, 2, 0}, xi = 48)
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }

        public final String getURL_SUMMARY() {
            return AINotesRequestManager.URL_SUMMARY;
        }

        public final String getURL_CORRECT_ERRORS() {
            return AINotesRequestManager.URL_CORRECT_ERRORS;
        }

        public final String getURL_RECOMMEND() {
            return AINotesRequestManager.URL_RECOMMEND;
        }

        public final String getURL_GENERATE_TEXT() {
            return AINotesRequestManager.URL_GENERATE_TEXT;
        }

        public final String getURL_POLISH_TEXT() {
            return AINotesRequestManager.URL_POLISH_TEXT;
        }

        public final String getURL_EXPAND_TEXT() {
            return AINotesRequestManager.URL_EXPAND_TEXT;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public final String getBaseUrl() {
            if (RomUtils.isInternationalBuild()) {
                byte[] bArrDecode = Base64.decode(AINotesRequestManager.BASE_URL_INTERNATIONAL, 0);
                Intrinsics.checkNotNullExpressionValue(bArrDecode, "decode(...)");
                return new String(bArrDecode, Charsets.UTF_8);
            }
            byte[] bArrDecode2 = Base64.decode(AINotesRequestManager.BASE_URL, 0);
            Intrinsics.checkNotNullExpressionValue(bArrDecode2, "decode(...)");
            return new String(bArrDecode2, Charsets.UTF_8);
        }

        private final String getTestBaseUrl() {
            byte[] bArrDecode = Base64.decode(AINotesRequestManager.BASE_STAGING_URL, 0);
            Intrinsics.checkNotNullExpressionValue(bArrDecode, "decode(...)");
            return new String(bArrDecode, Charsets.UTF_8);
        }
    }

    static {
        Companion companion = new Companion(null);
        INSTANCE = companion;
        URL_SUMMARY = companion.getBaseUrl() + "/api/aiNote/summarizeText";
        URL_CORRECT_ERRORS = companion.getBaseUrl() + "/api/aiNote/correctErrors";
        URL_RECOMMEND = companion.getBaseUrl() + "/api/aiTextControl/getRecommends";
        URL_GENERATE_TEXT = companion.getBaseUrl() + "/api/aiTextControl/generationText";
        URL_POLISH_TEXT = companion.getBaseUrl() + "/api/aiTextControl/polishText";
        URL_EXPAND_TEXT = companion.getBaseUrl() + "/api/aiTextControl/expandText";
    }

    public final MutableLiveData<AIResult<ResultContentCombine>> get_summaryLiveData() {
        return this._summaryLiveData;
    }

    public final MutableLiveData<AIResult<CorrectErrorCombineData>> get_correctErrorsLiveData() {
        return this._correctErrorsLiveData;
    }

    public final MutableLiveData<AIResult<List<String>>> get_recommendLiveData() {
        return this._recommendLiveData;
    }

    public final MutableLiveData<AIResult<ResultGenerateCombine>> get_generateTextLiveData() {
        return this._generateTextLiveData;
    }

    public final MutableLiveData<AIResult<ResultContentCombine>> get_polishTextLiveData() {
        return this._polishTextLiveData;
    }

    public final MutableLiveData<AIResult<ResultContentCombine>> get_expandTextLiveData() {
        return this._expandTextLiveData;
    }

    public final MutableLiveData<AIError<ResponseErrorDetail>> get_errorLiveData() {
        return this._errorLiveData;
    }

    public final Function1<Result, Unit> getSseCallback() {
        return this.sseCallback;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit sseCallback$lambda$0(Result it) {
        Intrinsics.checkNotNullParameter(it, "it");
        return Unit.INSTANCE;
    }

    public final void requestSummarizeText(String inputText) {
        Intrinsics.checkNotNullParameter(inputText, "inputText");
        Logger.INSTANCE.i(TAG, "requestSummarizeText");
        HashMap map = new HashMap();
        HashMap map2 = map;
        map2.put("bizCode", VALUE_BIZ_CODE);
        map2.put("inputText", inputText);
        map2.put("stream", "true");
        this.summarizeJob = handleResponse$default(this, URL_SUMMARY, map, this._summaryLiveData, this._errorLiveData, new TypeToken<ResponseData<ResultContentCombine>>() { // from class: com.miui.notes.ai.network.AINotesRequestManager$requestSummarizeText$typeToken$1
        }.getType(), null, this.sseCallback, null, 160, null);
    }

    public final void requestCorrectErrors(String inputText) {
        Logger.INSTANCE.i(TAG, "requestCorrectErrors");
        HashMap map = new HashMap();
        if (inputText == null) {
            map.put("inputText", "");
        } else {
            map.put("inputText", inputText);
        }
        HashMap map2 = map;
        map2.put("bizCode", VALUE_BIZ_CODE);
        map2.put("stream", "true");
        map2.put(AINotesViewModel.PARAM_TAG_TYPE, "1");
        this.correctErrorsJob = handleResponse$default(this, URL_CORRECT_ERRORS, map, this._correctErrorsLiveData, this._errorLiveData, new TypeToken<ResponseData<CorrectErrorCombineData>>() { // from class: com.miui.notes.ai.network.AINotesRequestManager$requestCorrectErrors$typeToken$1
        }.getType(), null, this.sseCallback, null, 160, null);
    }

    public final void requestRecommend(String packageName) {
        Intrinsics.checkNotNullParameter(packageName, "packageName");
        Logger.INSTANCE.i(TAG, "requestRecommend");
        HashMap map = new HashMap();
        HashMap map2 = map;
        map2.put("packageName", packageName);
        map2.put(LANG_CODE, Locale.getDefault().getLanguage() + "-" + Locale.getDefault().getCountry());
        Logger.INSTANCE.i(TAG, Locale.getDefault().getLanguage() + "-" + Locale.getDefault().getCountry());
        this.recommendJob = handleResponse$default(this, URL_RECOMMEND, map, this._recommendLiveData, this._errorLiveData, new TypeToken<ResponseData<List<? extends String>>>() { // from class: com.miui.notes.ai.network.AINotesRequestManager$requestRecommend$typeToken$1
        }.getType(), null, this.sseCallback, null, 160, null);
    }

    public final void requestGenerateText(String inputText, String userPrompt, boolean enableDeepSeek) {
        Intrinsics.checkNotNullParameter(userPrompt, "userPrompt");
        Logger.INSTANCE.i(TAG, "requestGenerateText enableDeepSeek: " + enableDeepSeek);
        HashMap map = new HashMap();
        if (inputText == null) {
            map.put("inputText", "");
        } else {
            map.put("inputText", inputText);
        }
        HashMap map2 = map;
        map2.put("userPrompt", userPrompt);
        map2.put("bizCode", VALUE_BIZ_CODE);
        map2.put("stream", "true");
        map2.put(PARAM_ENABLE_DEPTH, String.valueOf(enableDeepSeek));
        this.generateTextJob = handleResponse$default(this, URL_GENERATE_TEXT, map, this._generateTextLiveData, this._errorLiveData, new TypeToken<ResponseData<ResultGenerateCombine>>() { // from class: com.miui.notes.ai.network.AINotesRequestManager$requestGenerateText$typeToken$1
        }.getType(), null, this.sseCallback, null, 160, null);
    }

    public final void requestPolishText(String inputText) {
        Logger.INSTANCE.i(TAG, "requestPolishText");
        HashMap map = new HashMap();
        if (inputText == null) {
            map.put("inputText", "");
        } else {
            map.put("inputText", inputText);
        }
        HashMap map2 = map;
        map2.put("bizCode", VALUE_BIZ_CODE);
        map2.put(PARAM_POLISH_STYLE, String.valueOf(UserInputHolder.getInstance().getPolishStyle().getValue()));
        map2.put("stream", "true");
        this.polishTextJob = handleResponse$default(this, URL_POLISH_TEXT, map, this._polishTextLiveData, this._errorLiveData, new TypeToken<ResponseData<ResultContentCombine>>() { // from class: com.miui.notes.ai.network.AINotesRequestManager$requestPolishText$typeToken$1
        }.getType(), null, this.sseCallback, null, 160, null);
    }

    public final void requestExpandText(String inputText) {
        Logger.INSTANCE.i(TAG, "requestExpandText");
        HashMap map = new HashMap();
        if (inputText == null) {
            map.put("inputText", "");
        } else {
            map.put("inputText", inputText);
        }
        HashMap map2 = map;
        map2.put("bizCode", VALUE_BIZ_CODE);
        map2.put("stream", "true");
        map2.put(LANG_CODE, Locale.getDefault().getLanguage() + "-" + Locale.getDefault().getCountry());
        Logger.INSTANCE.i(TAG, Locale.getDefault().getLanguage() + "-" + Locale.getDefault().getCountry());
        this.polishTextJob = handleResponse$default(this, URL_EXPAND_TEXT, map, this._expandTextLiveData, this._errorLiveData, new TypeToken<ResponseData<ResultContentCombine>>() { // from class: com.miui.notes.ai.network.AINotesRequestManager$requestExpandText$typeToken$1
        }.getType(), null, this.sseCallback, null, 160, null);
    }

    /* JADX WARN: Multi-variable type inference failed */
    static /* synthetic */ Job handleResponse$default(AINotesRequestManager aINotesRequestManager, String str, HashMap map, MutableLiveData mutableLiveData, MutableLiveData mutableLiveData2, Type type, HashMap map2, Function1 function1, HttpMethod httpMethod, int i, Object obj) {
        if ((i & 16) != 0) {
            type = null;
        }
        if ((i & 32) != 0) {
            map2 = null;
        }
        if ((i & 64) != 0) {
            function1 = null;
        }
        if ((i & 128) != 0) {
            httpMethod = HttpMethod.POST.INSTANCE;
        }
        return aINotesRequestManager.handleResponse(str, map, mutableLiveData, mutableLiveData2, type, map2, function1, httpMethod);
    }

    private final <T> Job handleResponse(String url, HashMap<String, String> params, MutableLiveData<AIResult<T>> data, MutableLiveData<AIError<ResponseErrorDetail>> errorListener, Type type, HashMap<String, String> header, Function1<? super Result, Unit> sseCallback, HttpMethod method) {
        if (RomUtils.isInternationalBuild() && params != null) {
            String region = RomUtils.getRegion();
            Intrinsics.checkNotNullExpressionValue(region, "getRegion(...)");
            Locale locale = Locale.getDefault();
            Intrinsics.checkNotNullExpressionValue(locale, "getDefault(...)");
            String lowerCase = region.toLowerCase(locale);
            Intrinsics.checkNotNullExpressionValue(lowerCase, "toLowerCase(...)");
            params.put(PARAM_REGION_CODE, lowerCase);
        }
        return BuildersKt__Builders_commonKt.launch$default(CoroutineScopeKt.MainScope(), null, null, new AnonymousClass1(data, url, params, header, sseCallback, method, type, errorListener, this, null), 3, null);
    }

    /* JADX INFO: renamed from: com.miui.notes.ai.network.AINotesRequestManager$handleResponse$1, reason: invalid class name */
    /* JADX INFO: compiled from: AINotesRequestManager.kt */
    @Metadata(d1 = {"\u0000\n\n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\u0010\u0000\u001a\u00020\u0001*\u00020\u0002H\n"}, d2 = {"<anonymous>", "", "Lkotlinx/coroutines/CoroutineScope;"}, k = 3, mv = {2, 2, 0}, xi = 48)
    @DebugMetadata(c = "com.miui.notes.ai.network.AINotesRequestManager$handleResponse$1", f = "AINotesRequestManager.kt", i = {}, l = {R2.array.months_shortest}, m = "invokeSuspend", n = {}, s = {})
    static final class AnonymousClass1 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
        final /* synthetic */ MutableLiveData<AIResult<T>> $data;
        final /* synthetic */ MutableLiveData<AIError<ResponseErrorDetail>> $errorListener;
        final /* synthetic */ HashMap<String, String> $header;
        final /* synthetic */ HttpMethod $method;
        final /* synthetic */ HashMap<String, String> $params;
        final /* synthetic */ Function1<Result, Unit> $sseCallback;
        final /* synthetic */ Type $type;
        final /* synthetic */ String $url;
        int label;
        final /* synthetic */ AINotesRequestManager this$0;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        /* JADX WARN: Multi-variable type inference failed */
        AnonymousClass1(MutableLiveData<AIResult<T>> mutableLiveData, String str, HashMap<String, String> map, HashMap<String, String> map2, Function1<? super Result, Unit> function1, HttpMethod httpMethod, Type type, MutableLiveData<AIError<ResponseErrorDetail>> mutableLiveData2, AINotesRequestManager aINotesRequestManager, Continuation<? super AnonymousClass1> continuation) {
            super(2, continuation);
            this.$data = mutableLiveData;
            this.$url = str;
            this.$params = map;
            this.$header = map2;
            this.$sseCallback = function1;
            this.$method = httpMethod;
            this.$type = type;
            this.$errorListener = mutableLiveData2;
            this.this$0 = aINotesRequestManager;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
            return new AnonymousClass1(this.$data, this.$url, this.$params, this.$header, this.$sseCallback, this.$method, this.$type, this.$errorListener, this.this$0, continuation);
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(CoroutineScope coroutineScope, Continuation<? super Unit> continuation) {
            return ((AnonymousClass1) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
        }

        /* JADX WARN: Type inference incomplete: some casts might be missing */
        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            Object coroutine_suspended = IntrinsicsKt.getCOROUTINE_SUSPENDED();
            int i = this.label;
            if (i == 0) {
                ResultKt.throwOnFailure(obj);
                this.$data.setValue(AIResult.Loading.INSTANCE);
                this.label = 1;
                if (BuildersKt.withContext(Dispatchers.getIO(), new C00791(this.$url, this.$params, this.$header, this.$sseCallback, this.$method, this.$type, this.$data, this.$errorListener, this.this$0, null), this) == coroutine_suspended) {
                    return coroutine_suspended;
                }
            } else {
                if (i != 1) {
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                }
                ResultKt.throwOnFailure(obj);
            }
            return Unit.INSTANCE;
        }

        /* JADX INFO: renamed from: com.miui.notes.ai.network.AINotesRequestManager$handleResponse$1$1, reason: invalid class name and collision with other inner class name */
        /* JADX INFO: compiled from: AINotesRequestManager.kt */
        @Metadata(d1 = {"\u0000\n\n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\u0010\u0000\u001a\u00020\u0001*\u00020\u0002H\n"}, d2 = {"<anonymous>", "", "Lkotlinx/coroutines/CoroutineScope;"}, k = 3, mv = {2, 2, 0}, xi = 48)
        @DebugMetadata(c = "com.miui.notes.ai.network.AINotesRequestManager$handleResponse$1$1", f = "AINotesRequestManager.kt", i = {0}, l = {R2.array.passport_account_user_gender_name}, m = "invokeSuspend", n = {"responseFlow"}, s = {"L$0"})
        static final class C00791 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
            final /* synthetic */ MutableLiveData<AIResult<T>> $data;
            final /* synthetic */ MutableLiveData<AIError<ResponseErrorDetail>> $errorListener;
            final /* synthetic */ HashMap<String, String> $header;
            final /* synthetic */ HttpMethod $method;
            final /* synthetic */ HashMap<String, String> $params;
            final /* synthetic */ Function1<Result, Unit> $sseCallback;
            final /* synthetic */ Type $type;
            final /* synthetic */ String $url;
            Object L$0;
            int label;
            final /* synthetic */ AINotesRequestManager this$0;

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            /* JADX WARN: Multi-variable type inference failed */
            C00791(String str, HashMap<String, String> map, HashMap<String, String> map2, Function1<? super Result, Unit> function1, HttpMethod httpMethod, Type type, MutableLiveData<AIResult<T>> mutableLiveData, MutableLiveData<AIError<ResponseErrorDetail>> mutableLiveData2, AINotesRequestManager aINotesRequestManager, Continuation<? super C00791> continuation) {
                super(2, continuation);
                this.$url = str;
                this.$params = map;
                this.$header = map2;
                this.$sseCallback = function1;
                this.$method = httpMethod;
                this.$type = type;
                this.$data = mutableLiveData;
                this.$errorListener = mutableLiveData2;
                this.this$0 = aINotesRequestManager;
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
                return new C00791(this.$url, this.$params, this.$header, this.$sseCallback, this.$method, this.$type, this.$data, this.$errorListener, this.this$0, continuation);
            }

            @Override // kotlin.jvm.functions.Function2
            public final Object invoke(CoroutineScope coroutineScope, Continuation<? super Unit> continuation) {
                return ((C00791) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
            }

            /* JADX WARN: Multi-variable type inference failed */
            /* JADX WARN: Type inference failed for: r9v8 */
            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Object invokeSuspend(Object obj) {
                Object coroutine_suspended = IntrinsicsKt.getCOROUTINE_SUSPENDED();
                int i = this.label;
                try {
                    if (i == 0) {
                        ResultKt.throwOnFailure(obj);
                        Flow<Result> flowDoRequest = AiNoteRequest.INSTANCE.doRequest(this.$url, this.$params, this.$header, this.$sseCallback, this.$method);
                        final Type type = this.$type;
                        final MutableLiveData<AIResult<T>> mutableLiveData = this.$data;
                        final MutableLiveData<AIError<ResponseErrorDetail>> mutableLiveData2 = this.$errorListener;
                        final AINotesRequestManager aINotesRequestManager = this.this$0;
                        this.L$0 = SpillingKt.nullOutSpilledVariable(flowDoRequest);
                        this.label = 1;
                        Object objCollect = flowDoRequest.collect(new FlowCollector() { // from class: com.miui.notes.ai.network.AINotesRequestManager.handleResponse.1.1.1
                            @Override // kotlinx.coroutines.flow.FlowCollector
                            public /* bridge */ /* synthetic */ Object emit(Object obj2, Continuation continuation) {
                                return emit((Result) obj2, (Continuation<? super Unit>) continuation);
                            }

                            public final Object emit(Result result, Continuation<? super Unit> continuation) {
                                if (result instanceof Error) {
                                    Logger.INSTANCE.e(AINotesRequestManager.TAG, "response error, e:" + result);
                                    BuildersKt__Builders_commonKt.launch$default(CoroutineScopeKt.MainScope(), null, null, new C00811(result, mutableLiveData, mutableLiveData2, null), 3, null);
                                } else {
                                    if (!(result instanceof SUCCESS)) {
                                        throw new NoWhenBranchMatchedException();
                                    }
                                    ResponseData responseData = (ResponseData) new Gson().fromJson(((SUCCESS) result).getData(), type);
                                    Logger.INSTANCE.i(AINotesRequestManager.TAG, "response formatResultCode:" + responseData.getCode() + ", traceId:" + responseData.getTraceId());
                                    if (responseData.getCode() == 0) {
                                        BuildersKt__Builders_commonKt.launch$default(CoroutineScopeKt.MainScope(), null, null, new AnonymousClass2(mutableLiveData, responseData, null), 3, null);
                                    } else {
                                        BuildersKt__Builders_commonKt.launch$default(CoroutineScopeKt.MainScope(), null, null, new AnonymousClass3(mutableLiveData2, aINotesRequestManager, responseData, null), 3, null);
                                    }
                                }
                                return Unit.INSTANCE;
                            }

                            /* JADX INFO: renamed from: com.miui.notes.ai.network.AINotesRequestManager$handleResponse$1$1$1$1, reason: invalid class name and collision with other inner class name */
                            /* JADX INFO: compiled from: AINotesRequestManager.kt */
                            @Metadata(d1 = {"\u0000\n\n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\u0010\u0000\u001a\u00020\u0001*\u00020\u0002H\n"}, d2 = {"<anonymous>", "", "Lkotlinx/coroutines/CoroutineScope;"}, k = 3, mv = {2, 2, 0}, xi = 48)
                            @DebugMetadata(c = "com.miui.notes.ai.network.AINotesRequestManager$handleResponse$1$1$1$1", f = "AINotesRequestManager.kt", i = {}, l = {}, m = "invokeSuspend", n = {}, s = {})
                            static final class C00811 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
                                final /* synthetic */ MutableLiveData<AIResult<T>> $data;
                                final /* synthetic */ MutableLiveData<AIError<ResponseErrorDetail>> $errorListener;
                                final /* synthetic */ Result $it;
                                private /* synthetic */ Object L$0;
                                int label;

                                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                                C00811(Result result, MutableLiveData<AIResult<T>> mutableLiveData, MutableLiveData<AIError<ResponseErrorDetail>> mutableLiveData2, Continuation<? super C00811> continuation) {
                                    super(2, continuation);
                                    this.$it = result;
                                    this.$data = mutableLiveData;
                                    this.$errorListener = mutableLiveData2;
                                }

                                @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
                                public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
                                    C00811 c00811 = new C00811(this.$it, this.$data, this.$errorListener, continuation);
                                    c00811.L$0 = obj;
                                    return c00811;
                                }

                                @Override // kotlin.jvm.functions.Function2
                                public final Object invoke(CoroutineScope coroutineScope, Continuation<? super Unit> continuation) {
                                    return ((C00811) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
                                }

                                @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
                                public final Object invokeSuspend(Object obj) {
                                    CoroutineScope coroutineScope = (CoroutineScope) this.L$0;
                                    IntrinsicsKt.getCOROUTINE_SUSPENDED();
                                    if (this.label != 0) {
                                        throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                                    }
                                    ResultKt.throwOnFailure(obj);
                                    if (CoroutineScopeKt.isActive(coroutineScope)) {
                                        if (((Error) this.$it).getException() instanceof FinishException) {
                                            this.$data.setValue(AIResult.Finish.INSTANCE);
                                        } else if (((Error) this.$it).getException() instanceof AuthenticatorException) {
                                            this.$errorListener.setValue(new AIError.AuthTokenError(new ResponseErrorDetail(-1, ((Error) this.$it).getException().getMessage(), "")));
                                        } else {
                                            Logger.INSTANCE.i(AINotesRequestManager.TAG, "get error from net work lib");
                                            this.$errorListener.setValue(new AIError.Error(new ResponseErrorDetail(-2, ((Error) this.$it).getException().getMessage(), "")));
                                        }
                                    }
                                    return Unit.INSTANCE;
                                }
                            }

                            /* JADX INFO: renamed from: com.miui.notes.ai.network.AINotesRequestManager$handleResponse$1$1$1$2, reason: invalid class name */
                            /* JADX INFO: compiled from: AINotesRequestManager.kt */
                            @Metadata(d1 = {"\u0000\n\n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\u0010\u0000\u001a\u00020\u0001*\u00020\u0002H\n"}, d2 = {"<anonymous>", "", "Lkotlinx/coroutines/CoroutineScope;"}, k = 3, mv = {2, 2, 0}, xi = 48)
                            @DebugMetadata(c = "com.miui.notes.ai.network.AINotesRequestManager$handleResponse$1$1$1$2", f = "AINotesRequestManager.kt", i = {}, l = {}, m = "invokeSuspend", n = {}, s = {})
                            static final class AnonymousClass2 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
                                final /* synthetic */ MutableLiveData<AIResult<T>> $data;
                                final /* synthetic */ ResponseData<T> $responseData;
                                private /* synthetic */ Object L$0;
                                int label;

                                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                                AnonymousClass2(MutableLiveData<AIResult<T>> mutableLiveData, ResponseData<T> responseData, Continuation<? super AnonymousClass2> continuation) {
                                    super(2, continuation);
                                    this.$data = mutableLiveData;
                                    this.$responseData = responseData;
                                }

                                @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
                                public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
                                    AnonymousClass2 anonymousClass2 = new AnonymousClass2(this.$data, this.$responseData, continuation);
                                    anonymousClass2.L$0 = obj;
                                    return anonymousClass2;
                                }

                                @Override // kotlin.jvm.functions.Function2
                                public final Object invoke(CoroutineScope coroutineScope, Continuation<? super Unit> continuation) {
                                    return ((AnonymousClass2) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
                                }

                                @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
                                public final Object invokeSuspend(Object obj) {
                                    CoroutineScope coroutineScope = (CoroutineScope) this.L$0;
                                    IntrinsicsKt.getCOROUTINE_SUSPENDED();
                                    if (this.label != 0) {
                                        throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                                    }
                                    ResultKt.throwOnFailure(obj);
                                    if (CoroutineScopeKt.isActive(coroutineScope)) {
                                        this.$data.setValue(new AIResult.Complete(this.$responseData.getData(), this.$responseData.getTraceId()));
                                    }
                                    return Unit.INSTANCE;
                                }
                            }

                            /* JADX INFO: renamed from: com.miui.notes.ai.network.AINotesRequestManager$handleResponse$1$1$1$3, reason: invalid class name */
                            /* JADX INFO: compiled from: AINotesRequestManager.kt */
                            @Metadata(d1 = {"\u0000\n\n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\u0010\u0000\u001a\u00020\u0001*\u00020\u0002H\n"}, d2 = {"<anonymous>", "", "Lkotlinx/coroutines/CoroutineScope;"}, k = 3, mv = {2, 2, 0}, xi = 48)
                            @DebugMetadata(c = "com.miui.notes.ai.network.AINotesRequestManager$handleResponse$1$1$1$3", f = "AINotesRequestManager.kt", i = {}, l = {}, m = "invokeSuspend", n = {}, s = {})
                            static final class AnonymousClass3 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
                                final /* synthetic */ MutableLiveData<AIError<ResponseErrorDetail>> $errorListener;
                                final /* synthetic */ ResponseData<T> $responseData;
                                private /* synthetic */ Object L$0;
                                int label;
                                final /* synthetic */ AINotesRequestManager this$0;

                                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                                AnonymousClass3(MutableLiveData<AIError<ResponseErrorDetail>> mutableLiveData, AINotesRequestManager aINotesRequestManager, ResponseData<T> responseData, Continuation<? super AnonymousClass3> continuation) {
                                    super(2, continuation);
                                    this.$errorListener = mutableLiveData;
                                    this.this$0 = aINotesRequestManager;
                                    this.$responseData = responseData;
                                }

                                @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
                                public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
                                    AnonymousClass3 anonymousClass3 = new AnonymousClass3(this.$errorListener, this.this$0, this.$responseData, continuation);
                                    anonymousClass3.L$0 = obj;
                                    return anonymousClass3;
                                }

                                @Override // kotlin.jvm.functions.Function2
                                public final Object invoke(CoroutineScope coroutineScope, Continuation<? super Unit> continuation) {
                                    return ((AnonymousClass3) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
                                }

                                @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
                                public final Object invokeSuspend(Object obj) {
                                    CoroutineScope coroutineScope = (CoroutineScope) this.L$0;
                                    IntrinsicsKt.getCOROUTINE_SUSPENDED();
                                    if (this.label != 0) {
                                        throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                                    }
                                    ResultKt.throwOnFailure(obj);
                                    if (CoroutineScopeKt.isActive(coroutineScope)) {
                                        MutableLiveData<AIError<ResponseErrorDetail>> mutableLiveData = this.$errorListener;
                                        AINotesRequestManager aINotesRequestManager = this.this$0;
                                        ResponseData<T> responseData = this.$responseData;
                                        Intrinsics.checkNotNull(responseData);
                                        mutableLiveData.setValue(aINotesRequestManager.delaResult(responseData));
                                    }
                                    return Unit.INSTANCE;
                                }
                            }
                        }, this);
                        this = objCollect;
                        if (objCollect == coroutine_suspended) {
                            return coroutine_suspended;
                        }
                    } else {
                        if (i != 1) {
                            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                        }
                        ResultKt.throwOnFailure(obj);
                        this = this;
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    Logger.INSTANCE.e(AINotesRequestManager.TAG, "e:" + e);
                    BuildersKt__Builders_commonKt.launch$default(CoroutineScopeKt.MainScope(), null, null, new AnonymousClass2(this.$errorListener, e, null), 3, null);
                }
                return Unit.INSTANCE;
            }

            /* JADX INFO: renamed from: com.miui.notes.ai.network.AINotesRequestManager$handleResponse$1$1$2, reason: invalid class name */
            /* JADX INFO: compiled from: AINotesRequestManager.kt */
            @Metadata(d1 = {"\u0000\n\n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\u0010\u0000\u001a\u00020\u0001*\u00020\u0002H\n"}, d2 = {"<anonymous>", "", "Lkotlinx/coroutines/CoroutineScope;"}, k = 3, mv = {2, 2, 0}, xi = 48)
            @DebugMetadata(c = "com.miui.notes.ai.network.AINotesRequestManager$handleResponse$1$1$2", f = "AINotesRequestManager.kt", i = {}, l = {}, m = "invokeSuspend", n = {}, s = {})
            static final class AnonymousClass2 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
                final /* synthetic */ Exception $e;
                final /* synthetic */ MutableLiveData<AIError<ResponseErrorDetail>> $errorListener;
                private /* synthetic */ Object L$0;
                int label;

                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                AnonymousClass2(MutableLiveData<AIError<ResponseErrorDetail>> mutableLiveData, Exception exc, Continuation<? super AnonymousClass2> continuation) {
                    super(2, continuation);
                    this.$errorListener = mutableLiveData;
                    this.$e = exc;
                }

                @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
                public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
                    AnonymousClass2 anonymousClass2 = new AnonymousClass2(this.$errorListener, this.$e, continuation);
                    anonymousClass2.L$0 = obj;
                    return anonymousClass2;
                }

                @Override // kotlin.jvm.functions.Function2
                public final Object invoke(CoroutineScope coroutineScope, Continuation<? super Unit> continuation) {
                    return ((AnonymousClass2) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
                }

                @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
                public final Object invokeSuspend(Object obj) {
                    CoroutineScope coroutineScope = (CoroutineScope) this.L$0;
                    IntrinsicsKt.getCOROUTINE_SUSPENDED();
                    if (this.label != 0) {
                        throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                    }
                    ResultKt.throwOnFailure(obj);
                    if (CoroutineScopeKt.isActive(coroutineScope)) {
                        this.$errorListener.setValue(new AIError.Error(new ResponseErrorDetail(-2, this.$e.getMessage(), "")));
                    }
                    return Unit.INSTANCE;
                }
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final AIError<ResponseErrorDetail> delaResult(ResponseData<?> responseData) {
        switch (responseData.getCode()) {
            case SYSTEM_UPGRADE_CODE /* 20090003 */:
                return new AIError.SystemUpdateError(new ResponseErrorDetail(responseData.getCode(), responseData.getMessage(), responseData.getTraceId()));
            case ERROR_CODE_TEXT_LENGTH_EXCEED_LIMIT /* 20090113 */:
                return new AIError.TextLengthLimitError(new ResponseErrorDetail(responseData.getCode(), responseData.getMessage(), responseData.getTraceId()));
            case ERROR_CODE_NOT_SUPPORT /* 20090115 */:
                return new AIError.NotSupportError(new ResponseErrorDetail(responseData.getCode(), responseData.getMessage(), responseData.getTraceId()));
            case ERROR_CODE_LANGUAGE_NOT_SUPPORT /* 20090121 */:
                return new AIError.LanguageNotSupportError(new ResponseErrorDetail(responseData.getCode(), responseData.getMessage(), responseData.getTraceId()));
            case 20099124:
                return new AIError.TempBannedError(new ResponseErrorDetail(responseData.getCode(), responseData.getMessage(), responseData.getTraceId()));
            case 20099125:
                return new AIError.ForeverBannedError(new ResponseErrorDetail(responseData.getCode(), responseData.getMessage(), responseData.getTraceId()));
            case 20099126:
            case 20099127:
                return new AIError.IllegalError(new ResponseErrorDetail(responseData.getCode(), responseData.getMessage(), responseData.getTraceId()));
            default:
                return new AIError.Error(new ResponseErrorDetail(responseData.getCode(), responseData.getMessage(), responseData.getTraceId()));
        }
    }

    public final void cancelAll() {
        Logger.INSTANCE.i(TAG, "cancelAll");
        RequestManager.cancelTask$default(RequestManager.INSTANCE, null, 1, null);
        Job job = this.summarizeJob;
        if (job != null) {
            Job.DefaultImpls.cancel$default(job, (CancellationException) null, 1, (Object) null);
        }
        Job job2 = this.correctErrorsJob;
        if (job2 != null) {
            Job.DefaultImpls.cancel$default(job2, (CancellationException) null, 1, (Object) null);
        }
        Job job3 = this.recommendJob;
        if (job3 != null) {
            Job.DefaultImpls.cancel$default(job3, (CancellationException) null, 1, (Object) null);
        }
        Job job4 = this.generateTextJob;
        if (job4 != null) {
            Job.DefaultImpls.cancel$default(job4, (CancellationException) null, 1, (Object) null);
        }
        Job job5 = this.expendTextJob;
        if (job5 != null) {
            Job.DefaultImpls.cancel$default(job5, (CancellationException) null, 1, (Object) null);
        }
    }
}
