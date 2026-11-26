package com.miui.notes.ai.request;

import android.accounts.AuthenticatorException;
import android.util.Base64;
import androidx.exifinterface.media.ExifInterface;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelKt;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.market.sdk.reflect.ReflectUtilsForMiui;
import com.miui.aiengine.common.Error;
import com.miui.aiengine.common.RequestManager;
import com.miui.aiengine.common.Result;
import com.miui.aiengine.common.SUCCESS;
import com.miui.aiengine.common.request.HttpMethod;
import com.miui.common.tool.Logger;
import com.miui.common.tool.RomUtils;
import com.miui.notes.ai.AiResultActon;
import com.miui.notes.ai.FinishException;
import defpackage.R2;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.List;
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

/* JADX INFO: compiled from: AINotesViewModel.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000\u008e\u0001\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0011\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\t\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0004\u0018\u0000 T2\u00020\u0001:\u0001TB\u0007¢\u0006\u0004\b\u0002\u0010\u0003J\u0018\u0010\u000b\u001a\u00020\f2\b\u0010\r\u001a\u0004\u0018\u00010\u000e2\u0006\u0010\u000f\u001a\u00020\u0010J\u000e\u0010=\u001a\u00020\f2\u0006\u0010>\u001a\u00020\u000eJ\u000e\u0010?\u001a\u00020\f2\u0006\u0010>\u001a\u00020\u000eJª\u0001\u0010@\u001a\u00020'\"\u0004\b\u0000\u0010A2\u0006\u0010B\u001a\u00020\u000e2&\u0010C\u001a\"\u0012\u0004\u0012\u00020\u000e\u0012\u0004\u0012\u00020\u000e\u0018\u00010Dj\u0010\u0012\u0004\u0012\u00020\u000e\u0012\u0004\u0012\u00020\u000e\u0018\u0001`E2\u0012\u0010F\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u0002HA0\u00120\u00052\n\b\u0002\u0010\u000f\u001a\u0004\u0018\u00010G2(\b\u0002\u0010H\u001a\"\u0012\u0004\u0012\u00020\u000e\u0012\u0004\u0012\u00020\u000e\u0018\u00010Dj\u0010\u0012\u0004\u0012\u00020\u000e\u0012\u0004\u0012\u00020\u000e\u0018\u0001`E2\u0016\b\u0002\u00108\u001a\u0010\u0012\u0004\u0012\u00020:\u0012\u0004\u0012\u00020\f\u0018\u0001092\b\b\u0002\u0010I\u001a\u00020JH\u0002J\u0010\u0010K\u001a\u00020\f2\b\u0010>\u001a\u0004\u0018\u00010\u000eJ\u001e\u0010L\u001a\u00020\f2\u0006\u0010>\u001a\u00020\u000e2\u0006\u0010M\u001a\u00020\u000e2\u0006\u0010N\u001a\u00020\u000eJ\"\u0010O\u001a\b\u0012\u0004\u0012\u0002HA0\u0012\"\u0004\b\u0000\u0010A2\f\u0010P\u001a\b\u0012\u0004\u0012\u0002HA0QH\u0002J\u0006\u0010R\u001a\u00020\fJ\u0006\u0010S\u001a\u00020\fR\u0014\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00060\u0005X\u0082\u0004¢\u0006\u0002\n\u0000R\u0017\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\u00060\b8F¢\u0006\u0006\u001a\u0004\b\t\u0010\nR\u001a\u0010\u0011\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00130\u00120\u0005X\u0082\u0004¢\u0006\u0002\n\u0000R\u001d\u0010\u0014\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00130\u00120\b8F¢\u0006\u0006\u001a\u0004\b\u0015\u0010\nR\u001a\u0010\u0016\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00130\u00120\u0005X\u0082\u0004¢\u0006\u0002\n\u0000R\u001d\u0010\u0017\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00130\u00120\b8F¢\u0006\u0006\u001a\u0004\b\u0018\u0010\nR\u001a\u0010\u0019\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u001a0\u00120\u0005X\u0082\u0004¢\u0006\u0002\n\u0000R\u001d\u0010\u001b\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u001a0\u00120\b8F¢\u0006\u0006\u001a\u0004\b\u001c\u0010\nR\u001a\u0010\u001d\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u001e0\u00120\u0005X\u0082\u0004¢\u0006\u0002\n\u0000R\u001d\u0010\u001f\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u001e0\u00120\b8F¢\u0006\u0006\u001a\u0004\b \u0010\nR \u0010!\u001a\u0014\u0012\u0010\u0012\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020#0\"0\u00120\u0005X\u0082\u0004¢\u0006\u0002\n\u0000R#\u0010$\u001a\u0014\u0012\u0010\u0012\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020#0\"0\u00120\b8F¢\u0006\u0006\u001a\u0004\b%\u0010\nR\u001c\u0010&\u001a\u0004\u0018\u00010'X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b(\u0010)\"\u0004\b*\u0010+R\u001c\u0010,\u001a\u0004\u0018\u00010'X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b-\u0010)\"\u0004\b.\u0010+R\u001c\u0010/\u001a\u0004\u0018\u00010'X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b0\u0010)\"\u0004\b1\u0010+R\u001c\u00102\u001a\u0004\u0018\u00010'X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b3\u0010)\"\u0004\b4\u0010+R\u001c\u00105\u001a\u0004\u0018\u00010'X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b6\u0010)\"\u0004\b7\u0010+R\u001d\u00108\u001a\u000e\u0012\u0004\u0012\u00020:\u0012\u0004\u0012\u00020\f09¢\u0006\b\n\u0000\u001a\u0004\b;\u0010<¨\u0006U"}, d2 = {"Lcom/miui/notes/ai/request/AINotesViewModel;", "Landroidx/lifecycle/ViewModel;", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "()V", "_aiResultActon", "Landroidx/lifecycle/MutableLiveData;", "Lcom/miui/notes/ai/AiResultActon;", "aiResultActon", "Landroidx/lifecycle/LiveData;", "getAiResultActon", "()Landroidx/lifecycle/LiveData;", "setAiResultActon", "", "text", "", "type", "", "_formatLiveData", "Lcom/miui/notes/ai/request/AIResult;", "Lcom/miui/notes/ai/request/ResultContent;", "formatLiveData", "getFormatLiveData", "_summaryLiveData", "summaryLiveData", "getSummaryLiveData", "_correctErrorsLiveData", "Lcom/miui/notes/ai/request/CorrectErrorCombineData;", "correctErrorsLiveData", "getCorrectErrorsLiveData", "_translateLiveData", "Lcom/miui/notes/ai/request/TranslateData;", "translateLiveData", "getTranslateLiveData", "_languageLiveData", "", "Lcom/miui/notes/ai/request/LanguageItem;", "languageLiveData", "getLanguageLiveData", "formatJob", "Lkotlinx/coroutines/Job;", "getFormatJob", "()Lkotlinx/coroutines/Job;", "setFormatJob", "(Lkotlinx/coroutines/Job;)V", "summarizeJob", "getSummarizeJob", "setSummarizeJob", "correctErrorsJob", "getCorrectErrorsJob", "setCorrectErrorsJob", "translateJob", "getTranslateJob", "setTranslateJob", "getLanguageJob", "getGetLanguageJob", "setGetLanguageJob", "sseCallback", "Lkotlin/Function1;", "Lcom/miui/aiengine/common/Result;", "getSseCallback", "()Lkotlin/jvm/functions/Function1;", "requestFormatText", "inputText", "requestSummarizeText", "handleResponse", ExifInterface.GPS_DIRECTION_TRUE, "url", "params", "Ljava/util/HashMap;", "Lkotlin/collections/HashMap;", "data", "Ljava/lang/reflect/Type;", "header", XMLWriter.METHOD, "Lcom/miui/aiengine/common/request/HttpMethod;", "requestCorrectErrors", "requestTranslateText", AINotesViewModel.PARAM_SRC_LANG_CODE, AINotesViewModel.PARAM_TARGET_LANG_CODE, "delaResult", "responseData", "Lcom/miui/notes/ai/request/ResponseData;", "getSupportedLanguageList", "cancelAll", "Companion", "app_PhoneGlobalRelease"}, k = 1, mv = {2, 2, 0}, xi = 48)
public final class AINotesViewModel extends ViewModel {
    private static final String BASE_URL = "aHR0cHM6Ly9haS5lbmdpbmUubWl1aS5jb20=";
    private static final String BASE_URL_INTERNATIONAL = "aHR0cHM6Ly9haS5lbmdpbmUuaW50bC5taXVpLmNvbQ==";

    /* JADX INFO: renamed from: Companion, reason: from kotlin metadata */
    public static final Companion INSTANCE;
    public static final String PARAM_BIZ_CODE = "bizCode";
    public static final String PARAM_INPUT_TEXT = "inputText";
    public static final String PARAM_SRC_LANG_CODE = "srcLangCode";
    public static final String PARAM_TAG_TYPE = "tagType";
    public static final String PARAM_TARGET_LANG_CODE = "targetLangCode";
    private static final String TAG = "AINotesViewModel";
    private static final String URL_CORRECT_ERRORS;
    private static final String URL_FORMAT;
    private static final String URL_LANGUAGE_LIST;
    private static final String URL_SUMMARY;
    private static final String URL_TRANSLATE;
    public static final String VALUE_BIZ_CODE = "AiNote";
    private Job correctErrorsJob;
    private Job formatJob;
    private Job getLanguageJob;
    private Job summarizeJob;
    private Job translateJob;
    private final MutableLiveData<AiResultActon> _aiResultActon = new MutableLiveData<>();
    private final MutableLiveData<AIResult<ResultContent>> _formatLiveData = new MutableLiveData<>();
    private final MutableLiveData<AIResult<ResultContent>> _summaryLiveData = new MutableLiveData<>();
    private final MutableLiveData<AIResult<CorrectErrorCombineData>> _correctErrorsLiveData = new MutableLiveData<>();
    private final MutableLiveData<AIResult<TranslateData>> _translateLiveData = new MutableLiveData<>();
    private final MutableLiveData<AIResult<List<LanguageItem>>> _languageLiveData = new MutableLiveData<>();
    private final Function1<Result, Unit> sseCallback = new Function1() { // from class: com.miui.notes.ai.request.AINotesViewModel$$ExternalSyntheticLambda0
        @Override // kotlin.jvm.functions.Function1
        public final Object invoke(Object obj) {
            return AINotesViewModel.sseCallback$lambda$0((Result) obj);
        }
    };

    public final LiveData<AiResultActon> getAiResultActon() {
        return this._aiResultActon;
    }

    public final void setAiResultActon(String text, int type) {
        this._aiResultActon.setValue(new AiResultActon(text, type));
    }

    /* JADX INFO: compiled from: AINotesViewModel.kt */
    @Metadata(d1 = {"\u0000\u0014\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0002\b\u0015\b\u0086\u0003\u0018\u00002\u00020\u0001B\t\b\u0002¢\u0006\u0004\b\u0002\u0010\u0003J\b\u0010\u0019\u001a\u00020\u0005H\u0002R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082T¢\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0005X\u0082T¢\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\u0005X\u0082T¢\u0006\u0002\n\u0000R\u0011\u0010\b\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\nR\u0011\u0010\u000b\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\f\u0010\nR\u0011\u0010\r\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u000e\u0010\nR\u0011\u0010\u000f\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u0010\u0010\nR\u0011\u0010\u0011\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u0012\u0010\nR\u000e\u0010\u0013\u001a\u00020\u0005X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\u0014\u001a\u00020\u0005X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\u0015\u001a\u00020\u0005X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\u0016\u001a\u00020\u0005X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\u0017\u001a\u00020\u0005X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\u0018\u001a\u00020\u0005X\u0086T¢\u0006\u0002\n\u0000¨\u0006\u001a"}, d2 = {"Lcom/miui/notes/ai/request/AINotesViewModel$Companion;", "", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "()V", "TAG", "", "BASE_URL_INTERNATIONAL", "BASE_URL", "URL_FORMAT", "getURL_FORMAT", "()Ljava/lang/String;", "URL_SUMMARY", "getURL_SUMMARY", "URL_CORRECT_ERRORS", "getURL_CORRECT_ERRORS", "URL_TRANSLATE", "getURL_TRANSLATE", "URL_LANGUAGE_LIST", "getURL_LANGUAGE_LIST", "PARAM_INPUT_TEXT", "PARAM_BIZ_CODE", "PARAM_SRC_LANG_CODE", "PARAM_TARGET_LANG_CODE", "PARAM_TAG_TYPE", "VALUE_BIZ_CODE", "getBaseUrl", "app_PhoneGlobalRelease"}, k = 1, mv = {2, 2, 0}, xi = 48)
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }

        public final String getURL_FORMAT() {
            return AINotesViewModel.URL_FORMAT;
        }

        public final String getURL_SUMMARY() {
            return AINotesViewModel.URL_SUMMARY;
        }

        public final String getURL_CORRECT_ERRORS() {
            return AINotesViewModel.URL_CORRECT_ERRORS;
        }

        public final String getURL_TRANSLATE() {
            return AINotesViewModel.URL_TRANSLATE;
        }

        public final String getURL_LANGUAGE_LIST() {
            return AINotesViewModel.URL_LANGUAGE_LIST;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public final String getBaseUrl() {
            if (RomUtils.isInternationalBuild()) {
                byte[] bArrDecode = Base64.decode(AINotesViewModel.BASE_URL_INTERNATIONAL, 0);
                Intrinsics.checkNotNullExpressionValue(bArrDecode, "decode(...)");
                return new String(bArrDecode, Charsets.UTF_8);
            }
            byte[] bArrDecode2 = Base64.decode(AINotesViewModel.BASE_URL, 0);
            Intrinsics.checkNotNullExpressionValue(bArrDecode2, "decode(...)");
            return new String(bArrDecode2, Charsets.UTF_8);
        }
    }

    static {
        Companion companion = new Companion(null);
        INSTANCE = companion;
        URL_FORMAT = companion.getBaseUrl() + "/api/aiNote/formatText";
        URL_SUMMARY = companion.getBaseUrl() + "/api/aiNote/summarizeText";
        URL_CORRECT_ERRORS = companion.getBaseUrl() + "/api/aiNote/correctErrors";
        URL_TRANSLATE = companion.getBaseUrl() + "/api/aiNote/translateText";
        URL_LANGUAGE_LIST = companion.getBaseUrl() + "/api/aiNote/getSupportedLanguageList";
    }

    public final LiveData<AIResult<ResultContent>> getFormatLiveData() {
        return this._formatLiveData;
    }

    public final LiveData<AIResult<ResultContent>> getSummaryLiveData() {
        return this._summaryLiveData;
    }

    public final LiveData<AIResult<CorrectErrorCombineData>> getCorrectErrorsLiveData() {
        return this._correctErrorsLiveData;
    }

    public final LiveData<AIResult<TranslateData>> getTranslateLiveData() {
        return this._translateLiveData;
    }

    public final LiveData<AIResult<List<LanguageItem>>> getLanguageLiveData() {
        return this._languageLiveData;
    }

    public final Job getFormatJob() {
        return this.formatJob;
    }

    public final void setFormatJob(Job job) {
        this.formatJob = job;
    }

    public final Job getSummarizeJob() {
        return this.summarizeJob;
    }

    public final void setSummarizeJob(Job job) {
        this.summarizeJob = job;
    }

    public final Job getCorrectErrorsJob() {
        return this.correctErrorsJob;
    }

    public final void setCorrectErrorsJob(Job job) {
        this.correctErrorsJob = job;
    }

    public final Job getTranslateJob() {
        return this.translateJob;
    }

    public final void setTranslateJob(Job job) {
        this.translateJob = job;
    }

    public final Job getGetLanguageJob() {
        return this.getLanguageJob;
    }

    public final void setGetLanguageJob(Job job) {
        this.getLanguageJob = job;
    }

    public final Function1<Result, Unit> getSseCallback() {
        return this.sseCallback;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit sseCallback$lambda$0(Result it) {
        Intrinsics.checkNotNullParameter(it, "it");
        return Unit.INSTANCE;
    }

    public final void requestFormatText(String inputText) {
        Intrinsics.checkNotNullParameter(inputText, "inputText");
        Logger.INSTANCE.i(TAG, "requestFormatText");
        HashMap map = new HashMap();
        HashMap map2 = map;
        map2.put("inputText", inputText);
        map2.put("bizCode", VALUE_BIZ_CODE);
        map2.put("stream", "true");
        this.formatJob = handleResponse$default(this, URL_FORMAT, map, this._formatLiveData, new TypeToken<ResponseData<ResultContent>>() { // from class: com.miui.notes.ai.request.AINotesViewModel$requestFormatText$typeToken$1
        }.getType(), null, this.sseCallback, null, 80, null);
    }

    public final void requestSummarizeText(String inputText) {
        Intrinsics.checkNotNullParameter(inputText, "inputText");
        Logger.INSTANCE.i(TAG, "requestSummarizeText");
        HashMap map = new HashMap();
        HashMap map2 = map;
        map2.put("bizCode", VALUE_BIZ_CODE);
        map2.put("inputText", inputText);
        map2.put("stream", "true");
        this.summarizeJob = handleResponse$default(this, URL_SUMMARY, map, this._summaryLiveData, new TypeToken<ResponseData<ResultContent>>() { // from class: com.miui.notes.ai.request.AINotesViewModel$requestSummarizeText$typeToken$1
        }.getType(), null, this.sseCallback, null, 80, null);
    }

    /* JADX WARN: Multi-variable type inference failed */
    static /* synthetic */ Job handleResponse$default(AINotesViewModel aINotesViewModel, String str, HashMap map, MutableLiveData mutableLiveData, Type type, HashMap map2, Function1 function1, HttpMethod httpMethod, int i, Object obj) {
        if ((i & 8) != 0) {
            type = null;
        }
        if ((i & 16) != 0) {
            map2 = null;
        }
        if ((i & 32) != 0) {
            function1 = null;
        }
        if ((i & 64) != 0) {
            httpMethod = HttpMethod.POST.INSTANCE;
        }
        return aINotesViewModel.handleResponse(str, map, mutableLiveData, type, map2, function1, httpMethod);
    }

    /* JADX INFO: renamed from: com.miui.notes.ai.request.AINotesViewModel$handleResponse$1, reason: invalid class name and case insensitive filesystem */
    /* JADX INFO: compiled from: AINotesViewModel.kt */
    @Metadata(d1 = {"\u0000\n\n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\u0010\u0000\u001a\u00020\u0001*\u00020\u0002H\n"}, d2 = {"<anonymous>", "", "Lkotlinx/coroutines/CoroutineScope;"}, k = 3, mv = {2, 2, 0}, xi = 48)
    @DebugMetadata(c = "com.miui.notes.ai.request.AINotesViewModel$handleResponse$1", f = "AINotesViewModel.kt", i = {}, l = {R2.array.ai_edt_tag_gradient_border_colors}, m = "invokeSuspend", n = {}, s = {})
    static final class C02681 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
        final /* synthetic */ MutableLiveData<AIResult<T>> $data;
        final /* synthetic */ HashMap<String, String> $header;
        final /* synthetic */ HttpMethod $method;
        final /* synthetic */ HashMap<String, String> $params;
        final /* synthetic */ Function1<Result, Unit> $sseCallback;
        final /* synthetic */ Type $type;
        final /* synthetic */ String $url;
        int label;
        final /* synthetic */ AINotesViewModel this$0;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        /* JADX WARN: Multi-variable type inference failed */
        C02681(MutableLiveData<AIResult<T>> mutableLiveData, String str, HashMap<String, String> map, HashMap<String, String> map2, Function1<? super Result, Unit> function1, HttpMethod httpMethod, AINotesViewModel aINotesViewModel, Type type, Continuation<? super C02681> continuation) {
            super(2, continuation);
            this.$data = mutableLiveData;
            this.$url = str;
            this.$params = map;
            this.$header = map2;
            this.$sseCallback = function1;
            this.$method = httpMethod;
            this.this$0 = aINotesViewModel;
            this.$type = type;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
            return new C02681(this.$data, this.$url, this.$params, this.$header, this.$sseCallback, this.$method, this.this$0, this.$type, continuation);
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(CoroutineScope coroutineScope, Continuation<? super Unit> continuation) {
            return ((C02681) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
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
                if (BuildersKt.withContext(Dispatchers.getIO(), new C00841(this.$url, this.$params, this.$header, this.$sseCallback, this.$method, this.this$0, this.$type, this.$data, null), this) == coroutine_suspended) {
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

        /* JADX INFO: renamed from: com.miui.notes.ai.request.AINotesViewModel$handleResponse$1$1, reason: invalid class name and collision with other inner class name */
        /* JADX INFO: compiled from: AINotesViewModel.kt */
        @Metadata(d1 = {"\u0000\n\n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\u0010\u0000\u001a\u00020\u0001*\u00020\u0002H\n"}, d2 = {"<anonymous>", "", "Lkotlinx/coroutines/CoroutineScope;"}, k = 3, mv = {2, 2, 0}, xi = 48)
        @DebugMetadata(c = "com.miui.notes.ai.request.AINotesViewModel$handleResponse$1$1", f = "AINotesViewModel.kt", i = {0}, l = {R2.array.ai_input_gradient_border_colors}, m = "invokeSuspend", n = {"responseFlow"}, s = {"L$0"})
        static final class C00841 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
            final /* synthetic */ MutableLiveData<AIResult<T>> $data;
            final /* synthetic */ HashMap<String, String> $header;
            final /* synthetic */ HttpMethod $method;
            final /* synthetic */ HashMap<String, String> $params;
            final /* synthetic */ Function1<Result, Unit> $sseCallback;
            final /* synthetic */ Type $type;
            final /* synthetic */ String $url;
            Object L$0;
            int label;
            final /* synthetic */ AINotesViewModel this$0;

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            /* JADX WARN: Multi-variable type inference failed */
            C00841(String str, HashMap<String, String> map, HashMap<String, String> map2, Function1<? super Result, Unit> function1, HttpMethod httpMethod, AINotesViewModel aINotesViewModel, Type type, MutableLiveData<AIResult<T>> mutableLiveData, Continuation<? super C00841> continuation) {
                super(2, continuation);
                this.$url = str;
                this.$params = map;
                this.$header = map2;
                this.$sseCallback = function1;
                this.$method = httpMethod;
                this.this$0 = aINotesViewModel;
                this.$type = type;
                this.$data = mutableLiveData;
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
                return new C00841(this.$url, this.$params, this.$header, this.$sseCallback, this.$method, this.this$0, this.$type, this.$data, continuation);
            }

            @Override // kotlin.jvm.functions.Function2
            public final Object invoke(CoroutineScope coroutineScope, Continuation<? super Unit> continuation) {
                return ((C00841) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
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
                        final AINotesViewModel aINotesViewModel = this.this$0;
                        final Type type = this.$type;
                        final MutableLiveData<AIResult<T>> mutableLiveData = this.$data;
                        this.L$0 = SpillingKt.nullOutSpilledVariable(flowDoRequest);
                        this.label = 1;
                        Object objCollect = flowDoRequest.collect(new FlowCollector() { // from class: com.miui.notes.ai.request.AINotesViewModel.handleResponse.1.1.1
                            @Override // kotlinx.coroutines.flow.FlowCollector
                            public /* bridge */ /* synthetic */ Object emit(Object obj2, Continuation continuation) {
                                return emit((Result) obj2, (Continuation<? super Unit>) continuation);
                            }

                            public final Object emit(Result result, Continuation<? super Unit> continuation) {
                                if (result instanceof Error) {
                                    Logger.INSTANCE.e(AINotesViewModel.TAG, "e:" + result);
                                    BuildersKt__Builders_commonKt.launch$default(ViewModelKt.getViewModelScope(aINotesViewModel), null, null, new C00861(result, mutableLiveData, null), 3, null);
                                } else {
                                    if (!(result instanceof SUCCESS)) {
                                        throw new NoWhenBranchMatchedException();
                                    }
                                    ResponseData responseData = (ResponseData) new Gson().fromJson(((SUCCESS) result).getData(), type);
                                    Logger.INSTANCE.i(AINotesViewModel.TAG, "formatResultCode:" + responseData.getCode() + ", traceId:" + responseData.getTraceId() + ", message:" + responseData.getMessage());
                                    BuildersKt__Builders_commonKt.launch$default(ViewModelKt.getViewModelScope(aINotesViewModel), null, null, new AnonymousClass2(mutableLiveData, aINotesViewModel, responseData, null), 3, null);
                                }
                                return Unit.INSTANCE;
                            }

                            /* JADX INFO: renamed from: com.miui.notes.ai.request.AINotesViewModel$handleResponse$1$1$1$1, reason: invalid class name and collision with other inner class name */
                            /* JADX INFO: compiled from: AINotesViewModel.kt */
                            @Metadata(d1 = {"\u0000\n\n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\u0010\u0000\u001a\u00020\u0001*\u00020\u0002H\n"}, d2 = {"<anonymous>", "", "Lkotlinx/coroutines/CoroutineScope;"}, k = 3, mv = {2, 2, 0}, xi = 48)
                            @DebugMetadata(c = "com.miui.notes.ai.request.AINotesViewModel$handleResponse$1$1$1$1", f = "AINotesViewModel.kt", i = {}, l = {}, m = "invokeSuspend", n = {}, s = {})
                            static final class C00861 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
                                final /* synthetic */ MutableLiveData<AIResult<T>> $data;
                                final /* synthetic */ Result $it;
                                private /* synthetic */ Object L$0;
                                int label;

                                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                                C00861(Result result, MutableLiveData<AIResult<T>> mutableLiveData, Continuation<? super C00861> continuation) {
                                    super(2, continuation);
                                    this.$it = result;
                                    this.$data = mutableLiveData;
                                }

                                @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
                                public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
                                    C00861 c00861 = new C00861(this.$it, this.$data, continuation);
                                    c00861.L$0 = obj;
                                    return c00861;
                                }

                                @Override // kotlin.jvm.functions.Function2
                                public final Object invoke(CoroutineScope coroutineScope, Continuation<? super Unit> continuation) {
                                    return ((C00861) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
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
                                            this.$data.setValue(AIResult.Error.INSTANCE);
                                        }
                                    }
                                    return Unit.INSTANCE;
                                }
                            }

                            /* JADX INFO: renamed from: com.miui.notes.ai.request.AINotesViewModel$handleResponse$1$1$1$2, reason: invalid class name */
                            /* JADX INFO: compiled from: AINotesViewModel.kt */
                            @Metadata(d1 = {"\u0000\n\n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\u0010\u0000\u001a\u00020\u0001*\u00020\u0002H\n"}, d2 = {"<anonymous>", "", "Lkotlinx/coroutines/CoroutineScope;"}, k = 3, mv = {2, 2, 0}, xi = 48)
                            @DebugMetadata(c = "com.miui.notes.ai.request.AINotesViewModel$handleResponse$1$1$1$2", f = "AINotesViewModel.kt", i = {}, l = {}, m = "invokeSuspend", n = {}, s = {})
                            static final class AnonymousClass2 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
                                final /* synthetic */ MutableLiveData<AIResult<T>> $data;
                                final /* synthetic */ ResponseData<T> $responseData;
                                private /* synthetic */ Object L$0;
                                int label;
                                final /* synthetic */ AINotesViewModel this$0;

                                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                                AnonymousClass2(MutableLiveData<AIResult<T>> mutableLiveData, AINotesViewModel aINotesViewModel, ResponseData<T> responseData, Continuation<? super AnonymousClass2> continuation) {
                                    super(2, continuation);
                                    this.$data = mutableLiveData;
                                    this.this$0 = aINotesViewModel;
                                    this.$responseData = responseData;
                                }

                                @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
                                public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
                                    AnonymousClass2 anonymousClass2 = new AnonymousClass2(this.$data, this.this$0, this.$responseData, continuation);
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
                                        MutableLiveData<AIResult<T>> mutableLiveData = this.$data;
                                        AINotesViewModel aINotesViewModel = this.this$0;
                                        ResponseData<T> responseData = this.$responseData;
                                        Intrinsics.checkNotNull(responseData);
                                        mutableLiveData.setValue(aINotesViewModel.delaResult(responseData));
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
                    Logger.INSTANCE.e(AINotesViewModel.TAG, "e:" + e);
                    if (!(e instanceof CancellationException)) {
                        BuildersKt__Builders_commonKt.launch$default(ViewModelKt.getViewModelScope(this.this$0), null, null, new AnonymousClass2(this.$data, null), 3, null);
                    }
                }
                return Unit.INSTANCE;
            }

            /* JADX INFO: renamed from: com.miui.notes.ai.request.AINotesViewModel$handleResponse$1$1$2, reason: invalid class name */
            /* JADX INFO: compiled from: AINotesViewModel.kt */
            @Metadata(d1 = {"\u0000\n\n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\u0010\u0000\u001a\u00020\u0001*\u00020\u0002H\n"}, d2 = {"<anonymous>", "", "Lkotlinx/coroutines/CoroutineScope;"}, k = 3, mv = {2, 2, 0}, xi = 48)
            @DebugMetadata(c = "com.miui.notes.ai.request.AINotesViewModel$handleResponse$1$1$2", f = "AINotesViewModel.kt", i = {}, l = {}, m = "invokeSuspend", n = {}, s = {})
            static final class AnonymousClass2 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
                final /* synthetic */ MutableLiveData<AIResult<T>> $data;
                private /* synthetic */ Object L$0;
                int label;

                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                AnonymousClass2(MutableLiveData<AIResult<T>> mutableLiveData, Continuation<? super AnonymousClass2> continuation) {
                    super(2, continuation);
                    this.$data = mutableLiveData;
                }

                @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
                public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
                    AnonymousClass2 anonymousClass2 = new AnonymousClass2(this.$data, continuation);
                    anonymousClass2.L$0 = obj;
                    return anonymousClass2;
                }

                @Override // kotlin.jvm.functions.Function2
                public final Object invoke(CoroutineScope coroutineScope, Continuation<? super Unit> continuation) {
                    return ((AnonymousClass2) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
                }

                /* JADX WARN: Type inference incomplete: some casts might be missing */
                @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
                public final Object invokeSuspend(Object obj) {
                    CoroutineScope coroutineScope = (CoroutineScope) this.L$0;
                    IntrinsicsKt.getCOROUTINE_SUSPENDED();
                    if (this.label != 0) {
                        throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                    }
                    ResultKt.throwOnFailure(obj);
                    if (CoroutineScopeKt.isActive(coroutineScope)) {
                        Logger.INSTANCE.e(AINotesViewModel.TAG, "handleResponse AIResult.Error.");
                        this.$data.setValue(AIResult.Error.INSTANCE);
                    }
                    return Unit.INSTANCE;
                }
            }
        }
    }

    private final <T> Job handleResponse(String url, HashMap<String, String> params, MutableLiveData<AIResult<T>> data, Type type, HashMap<String, String> header, Function1<? super Result, Unit> sseCallback, HttpMethod method) {
        return BuildersKt__Builders_commonKt.launch$default(ViewModelKt.getViewModelScope(this), null, null, new C02681(data, url, params, header, sseCallback, method, this, type, null), 3, null);
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
        map2.put(PARAM_TAG_TYPE, "0");
        map2.put("stream", "true");
        this.correctErrorsJob = handleResponse$default(this, URL_CORRECT_ERRORS, map, this._correctErrorsLiveData, new TypeToken<ResponseData<CorrectErrorCombineData>>() { // from class: com.miui.notes.ai.request.AINotesViewModel$requestCorrectErrors$typeToken$1
        }.getType(), null, this.sseCallback, null, 80, null);
    }

    /* JADX INFO: renamed from: com.miui.notes.ai.request.AINotesViewModel$requestTranslateText$1, reason: invalid class name and case insensitive filesystem */
    /* JADX INFO: compiled from: AINotesViewModel.kt */
    @Metadata(d1 = {"\u0000\n\n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\u0010\u0000\u001a\u00020\u0001*\u00020\u0002H\n"}, d2 = {"<anonymous>", "", "Lkotlinx/coroutines/CoroutineScope;"}, k = 3, mv = {2, 2, 0}, xi = 48)
    @DebugMetadata(c = "com.miui.notes.ai.request.AINotesViewModel$requestTranslateText$1", f = "AINotesViewModel.kt", i = {0}, l = {R2.array.earthly_branches}, m = "invokeSuspend", n = {"$this$launch"}, s = {"L$0"})
    static final class C02691 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
        final /* synthetic */ String $inputText;
        final /* synthetic */ String $srcLangCode;
        final /* synthetic */ String $targetLangCode;
        private /* synthetic */ Object L$0;
        int label;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        C02691(String str, String str2, String str3, Continuation<? super C02691> continuation) {
            super(2, continuation);
            this.$inputText = str;
            this.$srcLangCode = str2;
            this.$targetLangCode = str3;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
            C02691 c02691 = AINotesViewModel.this.new C02691(this.$inputText, this.$srcLangCode, this.$targetLangCode, continuation);
            c02691.L$0 = obj;
            return c02691;
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(CoroutineScope coroutineScope, Continuation<? super Unit> continuation) {
            return ((C02691) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            CoroutineScope coroutineScope = (CoroutineScope) this.L$0;
            Object coroutine_suspended = IntrinsicsKt.getCOROUTINE_SUSPENDED();
            int i = this.label;
            if (i == 0) {
                ResultKt.throwOnFailure(obj);
                AINotesViewModel.this._translateLiveData.setValue(AIResult.Loading.INSTANCE);
                this.L$0 = coroutineScope;
                this.label = 1;
                obj = BuildersKt.withContext(Dispatchers.getIO(), new AINotesViewModel$requestTranslateText$1$result$1(this.$inputText, this.$srcLangCode, this.$targetLangCode, AINotesViewModel.this, null), this);
                if (obj == coroutine_suspended) {
                    return coroutine_suspended;
                }
            } else {
                if (i != 1) {
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                }
                ResultKt.throwOnFailure(obj);
            }
            AIResult aIResult = (AIResult) obj;
            if (CoroutineScopeKt.isActive(coroutineScope)) {
                AINotesViewModel.this._translateLiveData.setValue(aIResult);
            }
            return Unit.INSTANCE;
        }
    }

    public final void requestTranslateText(String inputText, String srcLangCode, String targetLangCode) {
        Intrinsics.checkNotNullParameter(inputText, "inputText");
        Intrinsics.checkNotNullParameter(srcLangCode, "srcLangCode");
        Intrinsics.checkNotNullParameter(targetLangCode, "targetLangCode");
        this.translateJob = BuildersKt__Builders_commonKt.launch$default(ViewModelKt.getViewModelScope(this), null, null, new C02691(inputText, srcLangCode, targetLangCode, null), 3, null);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final <T> AIResult<T> delaResult(ResponseData<T> responseData) {
        int code = responseData.getCode();
        if (code == -2) {
            return AIResult.AuthTokenError.INSTANCE;
        }
        if (code == 0) {
            return new AIResult.Complete(responseData.getData());
        }
        switch (code) {
            case 20099124:
                return AIResult.TempBannedError.INSTANCE;
            case 20099125:
                return AIResult.ForeverBannedError.INSTANCE;
            case 20099126:
            case 20099127:
                return AIResult.IllegalError.INSTANCE;
            default:
                return AIResult.Error.INSTANCE;
        }
    }

    /* JADX INFO: renamed from: com.miui.notes.ai.request.AINotesViewModel$getSupportedLanguageList$1, reason: invalid class name */
    /* JADX INFO: compiled from: AINotesViewModel.kt */
    @Metadata(d1 = {"\u0000\n\n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\u0010\u0000\u001a\u00020\u0001*\u00020\u0002H\n"}, d2 = {"<anonymous>", "", "Lkotlinx/coroutines/CoroutineScope;"}, k = 3, mv = {2, 2, 0}, xi = 48)
    @DebugMetadata(c = "com.miui.notes.ai.request.AINotesViewModel$getSupportedLanguageList$1", f = "AINotesViewModel.kt", i = {0}, l = {R2.attr.actionBarMoveIcon}, m = "invokeSuspend", n = {"$this$launch"}, s = {"L$0"})
    static final class AnonymousClass1 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
        private /* synthetic */ Object L$0;
        int label;

        AnonymousClass1(Continuation<? super AnonymousClass1> continuation) {
            super(2, continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
            AnonymousClass1 anonymousClass1 = AINotesViewModel.this.new AnonymousClass1(continuation);
            anonymousClass1.L$0 = obj;
            return anonymousClass1;
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(CoroutineScope coroutineScope, Continuation<? super Unit> continuation) {
            return ((AnonymousClass1) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            CoroutineScope coroutineScope = (CoroutineScope) this.L$0;
            Object coroutine_suspended = IntrinsicsKt.getCOROUTINE_SUSPENDED();
            int i = this.label;
            if (i == 0) {
                ResultKt.throwOnFailure(obj);
                AINotesViewModel.this._languageLiveData.setValue(AIResult.Loading.INSTANCE);
                this.L$0 = coroutineScope;
                this.label = 1;
                obj = BuildersKt.withContext(Dispatchers.getIO(), new AINotesViewModel$getSupportedLanguageList$1$result$1(null), this);
                if (obj == coroutine_suspended) {
                    return coroutine_suspended;
                }
            } else {
                if (i != 1) {
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                }
                ResultKt.throwOnFailure(obj);
            }
            AIResult aIResult = (AIResult) obj;
            if (CoroutineScopeKt.isActive(coroutineScope)) {
                AINotesViewModel.this._languageLiveData.setValue(aIResult);
            }
            return Unit.INSTANCE;
        }
    }

    public final void getSupportedLanguageList() {
        this.getLanguageJob = BuildersKt__Builders_commonKt.launch$default(ViewModelKt.getViewModelScope(this), null, null, new AnonymousClass1(null), 3, null);
    }

    public final void cancelAll() {
        Logger.INSTANCE.d(TAG, "cancelAll Task");
        RequestManager.cancelTask$default(RequestManager.INSTANCE, null, 1, null);
        Job job = this.formatJob;
        if (job != null) {
            Job.DefaultImpls.cancel$default(job, (CancellationException) null, 1, (Object) null);
        }
        Job job2 = this.summarizeJob;
        if (job2 != null) {
            Job.DefaultImpls.cancel$default(job2, (CancellationException) null, 1, (Object) null);
        }
        Job job3 = this.correctErrorsJob;
        if (job3 != null) {
            Job.DefaultImpls.cancel$default(job3, (CancellationException) null, 1, (Object) null);
        }
        Job job4 = this.translateJob;
        if (job4 != null) {
            Job.DefaultImpls.cancel$default(job4, (CancellationException) null, 1, (Object) null);
        }
        Job job5 = this.getLanguageJob;
        if (job5 != null) {
            Job.DefaultImpls.cancel$default(job5, (CancellationException) null, 1, (Object) null);
        }
    }
}
