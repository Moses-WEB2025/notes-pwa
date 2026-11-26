package com.miui.handwriting.common.ai.viewmodel;

import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;
import android.widget.Toast;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelKt;
import ch.qos.logback.core.CoreConstants;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.market.sdk.reflect.ReflectUtilsForMiui;
import com.miui.common.stat.ToDoStat;
import com.miui.creation.common.R;
import com.miui.handwriting.common.HandWritingViewApplication;
import com.miui.handwriting.common.ai.AESUtil;
import com.miui.handwriting.common.ai.CreationAIRequest;
import com.miui.handwriting.common.ai.CreationAiFragmentForOS1;
import com.miui.handwriting.common.ai.data.CreationDataForOS1;
import com.miui.handwriting.common.ai.data.CreationImageForOS1;
import com.miui.handwriting.common.ai.data.Result;
import com.miui.handwriting.common.ai.data.ThemeData;
import com.miui.handwriting.common.ai.data.ThemeItem;
import com.miui.handwriting.common.ai.data.UploadMetaData;
import com.miui.handwriting.common.ai.data.requestparam.ParamCreationImage;
import com.miui.handwriting.common.tools.DeviceUtils;
import com.miui.handwriting.common.tools.Utils;
import com.miui.libhandwritingpage.utils.RomUtils;
import com.miui.notes.ai.stat.AITextWidgetStat;
import com.xiaomi.onetrack.api.ah;
import defpackage.R2;
import java.io.File;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.CancellationException;
import java.util.concurrent.TimeUnit;
import javax.crypto.SecretKey;
import javax.crypto.spec.GCMParameterSpec;
import kotlin.Metadata;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.collections.ArraysKt;
import kotlin.collections.CollectionsKt;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.IntrinsicsKt;
import kotlin.coroutines.jvm.internal.Boxing;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.SpillingKt;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlinx.coroutines.BuildersKt;
import kotlinx.coroutines.BuildersKt__Builders_commonKt;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.Dispatchers;
import kotlinx.coroutines.Job;
import okhttp3.Call;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;

/* JADX INFO: compiled from: AIViewModel.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u0000z\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\b\n\u0002\b\b\n\u0002\u0010\u000b\n\u0002\b\u000e\n\u0002\u0018\u0002\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\f\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\t\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0007\n\u0002\b\t\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\u0018\u0000 a2\u00020\u0001:\u0001aB\u0007¢\u0006\u0004\b\u0002\u0010\u0003J\u000e\u0010*\u001a\u00020+2\u0006\u0010,\u001a\u00020%J\u0006\u0010J\u001a\u00020+J.\u0010K\u001a\u00020+2\u0006\u0010L\u001a\u00020%2\u0006\u0010M\u001a\u00020%2\u0006\u0010N\u001a\u00020O2\u0006\u0010P\u001a\u00020%2\u0006\u0010Q\u001a\u00020RJ\b\u0010S\u001a\u00020+H\u0014J\u0006\u0010T\u001a\u00020+JN\u0010U\u001a\u00020+2\u0006\u0010L\u001a\u00020%2\u0006\u0010M\u001a\u00020%2\u0006\u0010V\u001a\u00020%2\u0006\u0010W\u001a\u00020%2\u0006\u0010N\u001a\u00020O2\u0006\u0010P\u001a\u00020%2\u0006\u0010Q\u001a\u00020R2\u0006\u0010X\u001a\u00020%H\u0082@¢\u0006\u0002\u0010YJ\u001e\u0010Z\u001a\u00020+2\u000e\u0010[\u001a\n\u0012\u0004\u0012\u00020]\u0018\u00010\\H\u0082@¢\u0006\u0002\u0010^J.\u0010_\u001a\u00020+2\u0006\u0010L\u001a\u00020%2\u0006\u0010`\u001a\u00020%2\u0006\u0010N\u001a\u00020O2\u0006\u0010P\u001a\u00020%2\u0006\u0010Q\u001a\u00020\fR \u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00060\u0005X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0007\u0010\b\"\u0004\b\t\u0010\nR\u001a\u0010\u000b\u001a\u00020\fX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\r\u0010\u000e\"\u0004\b\u000f\u0010\u0010R\u001a\u0010\u0011\u001a\u00020\fX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0012\u0010\u000e\"\u0004\b\u0013\u0010\u0010R\u001a\u0010\u0014\u001a\u00020\u0015X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0016\u0010\u0017\"\u0004\b\u0018\u0010\u0019R\u001a\u0010\u001a\u001a\u00020\fX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u001b\u0010\u000e\"\u0004\b\u001c\u0010\u0010R\u001a\u0010\u001d\u001a\u00020\fX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u001e\u0010\u000e\"\u0004\b\u001f\u0010\u0010R\u001a\u0010 \u001a\u00020\u0015X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b!\u0010\u0017\"\u0004\b\"\u0010\u0019R\u0014\u0010#\u001a\b\u0012\u0004\u0012\u00020%0$X\u0082\u0004¢\u0006\u0002\n\u0000R\u0017\u0010&\u001a\b\u0012\u0004\u0012\u00020%0'8F¢\u0006\u0006\u001a\u0004\b(\u0010)R\u0016\u0010-\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010.0$X\u0082\u0004¢\u0006\u0002\n\u0000R\u0019\u0010/\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010.0'8F¢\u0006\u0006\u001a\u0004\b0\u0010)R\u0014\u00101\u001a\b\u0012\u0004\u0012\u00020\u00060$X\u0082\u0004¢\u0006\u0002\n\u0000R\u0017\u00102\u001a\b\u0012\u0004\u0012\u00020\u00060'8F¢\u0006\u0006\u001a\u0004\b3\u0010)R\u0014\u00104\u001a\b\u0012\u0004\u0012\u00020\u00150$X\u0082\u0004¢\u0006\u0002\n\u0000R\u0017\u00105\u001a\b\u0012\u0004\u0012\u00020\u00150'8F¢\u0006\u0006\u001a\u0004\b6\u0010)R\u0016\u00107\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\u00060$X\u0082\u0004¢\u0006\u0002\n\u0000R\u0019\u00108\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\u00060'8F¢\u0006\u0006\u001a\u0004\b9\u0010)R\u001c\u0010:\u001a\u0004\u0018\u00010;X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b<\u0010=\"\u0004\b>\u0010?R\u001c\u0010@\u001a\u0004\u0018\u00010;X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\bA\u0010=\"\u0004\bB\u0010?R \u0010C\u001a\b\u0012\u0004\u0012\u00020E0DX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\bF\u0010G\"\u0004\bH\u0010I¨\u0006b"}, d2 = {"Lcom/miui/handwriting/common/ai/viewmodel/AIViewModel;", "Landroidx/lifecycle/ViewModel;", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "()V", "previewItemList", "", "Lcom/miui/handwriting/common/ai/data/CreationImageForOS1;", "getPreviewItemList", "()Ljava/util/List;", "setPreviewItemList", "(Ljava/util/List;)V", "currentPage", "", "getCurrentPage", "()I", "setCurrentPage", "(I)V", "creationType", "getCreationType", "setCreationType", "sensitiveToast", "", "getSensitiveToast", "()Z", "setSensitiveToast", "(Z)V", "imageWidth", "getImageWidth", "setImageWidth", "imageHeight", "getImageHeight", "setImageHeight", "showLoading", "getShowLoading", "setShowLoading", "_imageUrlLiveData", "Landroidx/lifecycle/MutableLiveData;", "", "imageUrlLiveData", "Landroidx/lifecycle/LiveData;", "getImageUrlLiveData", "()Landroidx/lifecycle/LiveData;", "setImageUrl", "", "str", "_themeLiveData", "Lcom/miui/handwriting/common/ai/data/ThemeData;", "themeLiveData", "getThemeLiveData", "_creationSmallImageLiveData", "creationSmallImageLiveData", "getCreationSmallImageLiveData", "_errorCreation", "errorCreation", "getErrorCreation", "_creationBigImageLiveData", "creationBigImageLiveData", "getCreationBigImageLiveData", "uploadJob", "Lkotlinx/coroutines/Job;", "getUploadJob", "()Lkotlinx/coroutines/Job;", "setUploadJob", "(Lkotlinx/coroutines/Job;)V", "requestEnhanceJob", "getRequestEnhanceJob", "setRequestEnhanceJob", "uploadCallList", "Ljava/util/HashSet;", "Lokhttp3/Call;", "getUploadCallList", "()Ljava/util/HashSet;", "setUploadCallList", "(Ljava/util/HashSet;)V", "requestListTheme", "requestUploadMeta", "mid", AITextWidgetStat.TYPE_IMAGE, "theme", "Lcom/miui/handwriting/common/ai/data/ThemeItem;", "prompt", "rate", "", "onCleared", ToDoStat.CANCEL, "requestCreation", "serverKey", "iv", "requestId", "(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Lcom/miui/handwriting/common/ai/data/ThemeItem;Ljava/lang/String;FLjava/lang/String;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "postSmallImageResult", "result", "Lcom/miui/handwriting/common/ai/data/Result;", "Lcom/miui/handwriting/common/ai/data/CreationDataForOS1;", "(Lcom/miui/handwriting/common/ai/data/Result;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "requestEnhance", "imageData", "Companion", "libHandWritingCommon_release"}, k = 1, mv = {2, 2, 0}, xi = 48)
public final class AIViewModel extends ViewModel {
    public static final String AES_GCM_256 = "AES-GCM-256";
    public static final String CONTENT_TYPE_CREATE = "multipart/form-data";

    /* JADX INFO: renamed from: Companion, reason: from kotlin metadata */
    public static final Companion INSTANCE;
    public static final String HEADERS_CONTENT_TYPE = "Content-Type";
    public static final String PARAM_APK_VERSION = "_apkversion";
    public static final String PARAM_APK_VERSION_CODE = "_apkversioncode";
    public static final String PARAM_CREATION_TYPE = "type";
    public static final String PARAM_DATA = "data";
    public static final String PARAM_FILE = "file";
    public static final String PARAM_MI_ID = "miId";
    public static final String PARAM_OA_ID = "oaid";
    public static final String PARAM_PROMPT = "prompt";
    public static final String PARAM_RATE = "rate";
    public static final String PARAM_REQUEST_ID = "requestId";
    public static final String PARAM_THEME = "theme";
    public static final String PARAM_THEME_ID = "themeId";
    public static final String PARAM_TIMESTAMP = "timestamp";
    private static final String TAG = "AIViewModel";
    private static final String URL_CREATION;
    private static final String URL_ENHANCE;
    private static final String URL_GET_VERSION;
    private static final String URL_THEME;
    private static final String URL_UPLOAD_META;
    private static final OkHttpClient okHttpClient;
    private final MutableLiveData<CreationImageForOS1> _creationBigImageLiveData;
    private final MutableLiveData<CreationImageForOS1> _creationSmallImageLiveData;
    private final MutableLiveData<Boolean> _errorCreation;
    private final MutableLiveData<String> _imageUrlLiveData;
    private final MutableLiveData<ThemeData> _themeLiveData;
    private int creationType;
    private int currentPage;
    private int imageHeight;
    private int imageWidth;
    private List<CreationImageForOS1> previewItemList;
    private Job requestEnhanceJob;
    private boolean sensitiveToast;
    private boolean showLoading;
    private HashSet<Call> uploadCallList;
    private Job uploadJob;

    /* JADX INFO: compiled from: AIViewModel.kt */
    @Metadata(d1 = {"\u0000\u001c\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0002\b\u001d\n\u0002\u0018\u0002\n\u0002\b\u0003\b\u0086\u0003\u0018\u00002\u00020\u0001B\t\b\u0002¢\u0006\u0004\b\u0002\u0010\u0003J\b\u0010\u0006\u001a\u00020\u0005H\u0002R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082T¢\u0006\u0002\n\u0000R\u0011\u0010\u0007\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\b\u0010\tR\u0011\u0010\n\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\tR\u0011\u0010\f\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\r\u0010\tR\u0011\u0010\u000e\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u000f\u0010\tR\u0011\u0010\u0010\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u0011\u0010\tR\u000e\u0010\u0012\u001a\u00020\u0005X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\u0013\u001a\u00020\u0005X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\u0014\u001a\u00020\u0005X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\u0015\u001a\u00020\u0005X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\u0016\u001a\u00020\u0005X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\u0017\u001a\u00020\u0005X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\u0018\u001a\u00020\u0005X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\u0019\u001a\u00020\u0005X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\u001a\u001a\u00020\u0005X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\u001b\u001a\u00020\u0005X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\u001c\u001a\u00020\u0005X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\u001d\u001a\u00020\u0005X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\u001e\u001a\u00020\u0005X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\u001f\u001a\u00020\u0005X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010 \u001a\u00020\u0005X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010!\u001a\u00020\u0005X\u0086T¢\u0006\u0002\n\u0000R\u0011\u0010\"\u001a\u00020#¢\u0006\b\n\u0000\u001a\u0004\b$\u0010%¨\u0006&"}, d2 = {"Lcom/miui/handwriting/common/ai/viewmodel/AIViewModel$Companion;", "", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "()V", "TAG", "", "getBaseUrl", "URL_GET_VERSION", "getURL_GET_VERSION", "()Ljava/lang/String;", "URL_THEME", "getURL_THEME", "URL_UPLOAD_META", "getURL_UPLOAD_META", "URL_CREATION", "getURL_CREATION", "URL_ENHANCE", "getURL_ENHANCE", "CONTENT_TYPE_CREATE", "AES_GCM_256", "PARAM_MI_ID", "PARAM_OA_ID", "PARAM_TIMESTAMP", "PARAM_DATA", "PARAM_FILE", "PARAM_THEME", "PARAM_APK_VERSION", "PARAM_APK_VERSION_CODE", "PARAM_THEME_ID", "PARAM_PROMPT", "PARAM_RATE", "PARAM_REQUEST_ID", "PARAM_CREATION_TYPE", "HEADERS_CONTENT_TYPE", "okHttpClient", "Lokhttp3/OkHttpClient;", "getOkHttpClient", "()Lokhttp3/OkHttpClient;", "libHandWritingCommon_release"}, k = 1, mv = {2, 2, 0}, xi = 48)
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }

        /* JADX INFO: Access modifiers changed from: private */
        public final String getBaseUrl() {
            if (RomUtils.isInternationalBuild()) {
                return "https://avatar-ai.engine.intl.mi.com";
            }
            return "https://avatar-ai.sec.miui.com";
        }

        public final String getURL_GET_VERSION() {
            return AIViewModel.URL_GET_VERSION;
        }

        public final String getURL_THEME() {
            return AIViewModel.URL_THEME;
        }

        public final String getURL_UPLOAD_META() {
            return AIViewModel.URL_UPLOAD_META;
        }

        public final String getURL_CREATION() {
            return AIViewModel.URL_CREATION;
        }

        public final String getURL_ENHANCE() {
            return AIViewModel.URL_ENHANCE;
        }

        public final OkHttpClient getOkHttpClient() {
            return AIViewModel.okHttpClient;
        }
    }

    public AIViewModel() {
        String EMPTY_PIC = CreationAiFragmentForOS1.EMPTY_PIC;
        Intrinsics.checkNotNullExpressionValue(EMPTY_PIC, "EMPTY_PIC");
        String EMPTY_PIC2 = CreationAiFragmentForOS1.EMPTY_PIC;
        Intrinsics.checkNotNullExpressionValue(EMPTY_PIC2, "EMPTY_PIC");
        String EMPTY_PIC3 = CreationAiFragmentForOS1.EMPTY_PIC;
        Intrinsics.checkNotNullExpressionValue(EMPTY_PIC3, "EMPTY_PIC");
        String EMPTY_PIC4 = CreationAiFragmentForOS1.EMPTY_PIC;
        Intrinsics.checkNotNullExpressionValue(EMPTY_PIC4, "EMPTY_PIC");
        this.previewItemList = CollectionsKt.listOf((Object[]) new CreationImageForOS1[]{new CreationImageForOS1(EMPTY_PIC), new CreationImageForOS1(EMPTY_PIC2), new CreationImageForOS1(EMPTY_PIC3), new CreationImageForOS1(EMPTY_PIC4)});
        this.creationType = 1;
        this._imageUrlLiveData = new MutableLiveData<>();
        this._themeLiveData = new MutableLiveData<>();
        this._creationSmallImageLiveData = new MutableLiveData<>();
        this._errorCreation = new MutableLiveData<>();
        this._creationBigImageLiveData = new MutableLiveData<>();
        this.uploadCallList = new HashSet<>();
    }

    static {
        Companion companion = new Companion(null);
        INSTANCE = companion;
        URL_GET_VERSION = companion.getBaseUrl() + "/creation/getVersion";
        URL_THEME = companion.getBaseUrl() + "/creation/listTheme";
        URL_UPLOAD_META = companion.getBaseUrl() + "/creation/uploadMeta";
        URL_CREATION = companion.getBaseUrl() + "/creation/create";
        URL_ENHANCE = companion.getBaseUrl() + "/creation/enhance";
        okHttpClient = new OkHttpClient.Builder().connectTimeout(20L, TimeUnit.SECONDS).writeTimeout(20L, TimeUnit.SECONDS).readTimeout(20L, TimeUnit.SECONDS).build();
    }

    public final List<CreationImageForOS1> getPreviewItemList() {
        return this.previewItemList;
    }

    public final void setPreviewItemList(List<CreationImageForOS1> list) {
        Intrinsics.checkNotNullParameter(list, "<set-?>");
        this.previewItemList = list;
    }

    public final int getCurrentPage() {
        return this.currentPage;
    }

    public final void setCurrentPage(int i) {
        this.currentPage = i;
    }

    public final int getCreationType() {
        return this.creationType;
    }

    public final void setCreationType(int i) {
        this.creationType = i;
    }

    public final boolean getSensitiveToast() {
        return this.sensitiveToast;
    }

    public final void setSensitiveToast(boolean z) {
        this.sensitiveToast = z;
    }

    public final int getImageWidth() {
        return this.imageWidth;
    }

    public final void setImageWidth(int i) {
        this.imageWidth = i;
    }

    public final int getImageHeight() {
        return this.imageHeight;
    }

    public final void setImageHeight(int i) {
        this.imageHeight = i;
    }

    public final boolean getShowLoading() {
        return this.showLoading;
    }

    public final void setShowLoading(boolean z) {
        this.showLoading = z;
    }

    public final LiveData<String> getImageUrlLiveData() {
        return this._imageUrlLiveData;
    }

    public final void setImageUrl(String str) {
        Intrinsics.checkNotNullParameter(str, "str");
        this._imageUrlLiveData.setValue(str);
    }

    public final LiveData<ThemeData> getThemeLiveData() {
        return this._themeLiveData;
    }

    public final LiveData<CreationImageForOS1> getCreationSmallImageLiveData() {
        return this._creationSmallImageLiveData;
    }

    public final LiveData<Boolean> getErrorCreation() {
        return this._errorCreation;
    }

    public final LiveData<CreationImageForOS1> getCreationBigImageLiveData() {
        return this._creationBigImageLiveData;
    }

    public final Job getUploadJob() {
        return this.uploadJob;
    }

    public final void setUploadJob(Job job) {
        this.uploadJob = job;
    }

    public final Job getRequestEnhanceJob() {
        return this.requestEnhanceJob;
    }

    public final void setRequestEnhanceJob(Job job) {
        this.requestEnhanceJob = job;
    }

    public final HashSet<Call> getUploadCallList() {
        return this.uploadCallList;
    }

    public final void setUploadCallList(HashSet<Call> hashSet) {
        Intrinsics.checkNotNullParameter(hashSet, "<set-?>");
        this.uploadCallList = hashSet;
    }

    /* JADX INFO: renamed from: com.miui.handwriting.common.ai.viewmodel.AIViewModel$requestListTheme$1, reason: invalid class name and case insensitive filesystem */
    /* JADX INFO: compiled from: AIViewModel.kt */
    @Metadata(d1 = {"\u0000\n\n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\u0010\u0000\u001a\u00020\u0001*\u00020\u0002H\n"}, d2 = {"<anonymous>", "", "Lkotlinx/coroutines/CoroutineScope;"}, k = 3, mv = {2, 2, 0}, xi = 48)
    @DebugMetadata(c = "com.miui.handwriting.common.ai.viewmodel.AIViewModel$requestListTheme$1", f = "AIViewModel.kt", i = {}, l = {150}, m = "invokeSuspend", n = {}, s = {})
    static final class C02271 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
        int label;

        C02271(Continuation<? super C02271> continuation) {
            super(2, continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
            return AIViewModel.this.new C02271(continuation);
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(CoroutineScope coroutineScope, Continuation<? super Unit> continuation) {
            return ((C02271) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
        }

        /* JADX INFO: renamed from: com.miui.handwriting.common.ai.viewmodel.AIViewModel$requestListTheme$1$1, reason: invalid class name and collision with other inner class name */
        /* JADX INFO: compiled from: AIViewModel.kt */
        @Metadata(d1 = {"\u0000\n\n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\u0010\u0000\u001a\u00020\u0001*\u00020\u0002H\n"}, d2 = {"<anonymous>", "", "Lkotlinx/coroutines/CoroutineScope;"}, k = 3, mv = {2, 2, 0}, xi = 48)
        @DebugMetadata(c = "com.miui.handwriting.common.ai.viewmodel.AIViewModel$requestListTheme$1$1", f = "AIViewModel.kt", i = {}, l = {}, m = "invokeSuspend", n = {}, s = {})
        static final class C00531 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
            int label;
            final /* synthetic */ AIViewModel this$0;

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            C00531(AIViewModel aIViewModel, Continuation<? super C00531> continuation) {
                super(2, continuation);
                this.this$0 = aIViewModel;
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
                return new C00531(this.this$0, continuation);
            }

            @Override // kotlin.jvm.functions.Function2
            public final Object invoke(CoroutineScope coroutineScope, Continuation<? super Unit> continuation) {
                return ((C00531) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Object invokeSuspend(Object obj) {
                IntrinsicsKt.getCOROUTINE_SUSPENDED();
                if (this.label != 0) {
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                }
                ResultKt.throwOnFailure(obj);
                try {
                    Result result = (Result) new Gson().fromJson(CreationAIRequest.INSTANCE.get(AIViewModel.INSTANCE.getURL_THEME(), null, null), new TypeToken<Result<ThemeData>>() { // from class: com.miui.handwriting.common.ai.viewmodel.AIViewModel$requestListTheme$1$1$aiThemeBeanResult$1
                    }.getType());
                    Log.d(AIViewModel.TAG, "listTheme:" + result);
                    this.this$0._themeLiveData.postValue(result.getData());
                } catch (Exception e) {
                    Log.e(AIViewModel.TAG, "e:" + e);
                }
                return Unit.INSTANCE;
            }
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            Object coroutine_suspended = IntrinsicsKt.getCOROUTINE_SUSPENDED();
            int i = this.label;
            if (i == 0) {
                ResultKt.throwOnFailure(obj);
                this.label = 1;
                if (BuildersKt.withContext(Dispatchers.getIO(), new C00531(AIViewModel.this, null), this) == coroutine_suspended) {
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
    }

    public final void requestListTheme() {
        BuildersKt__Builders_commonKt.launch$default(ViewModelKt.getViewModelScope(this), null, null, new C02271(null), 3, null);
    }

    /* JADX INFO: renamed from: com.miui.handwriting.common.ai.viewmodel.AIViewModel$requestUploadMeta$1, reason: invalid class name and case insensitive filesystem */
    /* JADX INFO: compiled from: AIViewModel.kt */
    @Metadata(d1 = {"\u0000\n\n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\u0010\u0000\u001a\u00020\u0001*\u00020\u0002H\n"}, d2 = {"<anonymous>", "", "Lkotlinx/coroutines/CoroutineScope;"}, k = 3, mv = {2, 2, 0}, xi = 48)
    @DebugMetadata(c = "com.miui.handwriting.common.ai.viewmodel.AIViewModel$requestUploadMeta$1", f = "AIViewModel.kt", i = {}, l = {R2.array.ai_tag_blend_colors}, m = "invokeSuspend", n = {}, s = {})
    static final class C02281 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
        final /* synthetic */ String $image;
        final /* synthetic */ String $mid;
        final /* synthetic */ String $prompt;
        final /* synthetic */ float $rate;
        final /* synthetic */ ThemeItem $theme;
        int label;
        final /* synthetic */ AIViewModel this$0;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        C02281(String str, String str2, AIViewModel aIViewModel, ThemeItem themeItem, String str3, float f, Continuation<? super C02281> continuation) {
            super(2, continuation);
            this.$mid = str;
            this.$image = str2;
            this.this$0 = aIViewModel;
            this.$theme = themeItem;
            this.$prompt = str3;
            this.$rate = f;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
            return new C02281(this.$mid, this.$image, this.this$0, this.$theme, this.$prompt, this.$rate, continuation);
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(CoroutineScope coroutineScope, Continuation<? super Unit> continuation) {
            return ((C02281) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
        }

        /* JADX INFO: renamed from: com.miui.handwriting.common.ai.viewmodel.AIViewModel$requestUploadMeta$1$1, reason: invalid class name and collision with other inner class name */
        /* JADX INFO: compiled from: AIViewModel.kt */
        @Metadata(d1 = {"\u0000\n\n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\u0010\u0000\u001a\u00020\u0001*\u00020\u0002H\n"}, d2 = {"<anonymous>", "", "Lkotlinx/coroutines/CoroutineScope;"}, k = 3, mv = {2, 2, 0}, xi = 48)
        @DebugMetadata(c = "com.miui.handwriting.common.ai.viewmodel.AIViewModel$requestUploadMeta$1$1", f = "AIViewModel.kt", i = {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1}, l = {R2.array.setting_phone_note_layout_way, R2.attr.StretchableWidgetStyle}, m = "invokeSuspend", n = {"$this$withContext", "params", "algorithm", "options", "paramImageInfo", "fileSha256Digest", "file", "bodyData", ah.H, "uploadMetaResult", "$this$withContext", "e"}, s = {"L$0", "L$1", "L$2", "L$3", "L$4", "L$5", "L$6", "L$7", "L$8", "L$9", "L$0", "L$1"})
        static final class C00541 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
            final /* synthetic */ String $image;
            final /* synthetic */ String $mid;
            final /* synthetic */ String $prompt;
            final /* synthetic */ float $rate;
            final /* synthetic */ ThemeItem $theme;
            private /* synthetic */ Object L$0;
            Object L$1;
            Object L$2;
            Object L$3;
            Object L$4;
            Object L$5;
            Object L$6;
            Object L$7;
            Object L$8;
            Object L$9;
            int label;
            final /* synthetic */ AIViewModel this$0;

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            C00541(String str, String str2, AIViewModel aIViewModel, ThemeItem themeItem, String str3, float f, Continuation<? super C00541> continuation) {
                super(2, continuation);
                this.$mid = str;
                this.$image = str2;
                this.this$0 = aIViewModel;
                this.$theme = themeItem;
                this.$prompt = str3;
                this.$rate = f;
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
                C00541 c00541 = new C00541(this.$mid, this.$image, this.this$0, this.$theme, this.$prompt, this.$rate, continuation);
                c00541.L$0 = obj;
                return c00541;
            }

            @Override // kotlin.jvm.functions.Function2
            public final Object invoke(CoroutineScope coroutineScope, Continuation<? super Unit> continuation) {
                return ((C00541) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
            }

            /* JADX WARN: Code restructure failed: missing block: B:38:0x0250, code lost:
            
                if (kotlinx.coroutines.BuildersKt.withContext(kotlinx.coroutines.Dispatchers.getMain(), new com.miui.handwriting.common.ai.viewmodel.AIViewModel.C02281.C00541.AnonymousClass3(r28.this$0, null), r28) == r4) goto L39;
             */
            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            /*
                Code decompiled incorrectly, please refer to instructions dump.
                To view partially-correct code enable 'Show inconsistent code' option in preferences
            */
            public final java.lang.Object invokeSuspend(java.lang.Object r29) throws java.lang.Throwable {
                /*
                    Method dump skipped, instruction units count: 598
                    To view this dump change 'Code comments level' option to 'DEBUG'
                */
                throw new UnsupportedOperationException("Method not decompiled: com.miui.handwriting.common.ai.viewmodel.AIViewModel.C02281.C00541.invokeSuspend(java.lang.Object):java.lang.Object");
            }

            /* JADX INFO: renamed from: com.miui.handwriting.common.ai.viewmodel.AIViewModel$requestUploadMeta$1$1$1, reason: invalid class name and collision with other inner class name */
            /* JADX INFO: compiled from: AIViewModel.kt */
            @Metadata(d1 = {"\u0000\n\n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\u0010\u0000\u001a\u00020\u0001*\u00020\u0002H\n"}, d2 = {"<anonymous>", "", "Lkotlinx/coroutines/CoroutineScope;"}, k = 3, mv = {2, 2, 0}, xi = 48)
            @DebugMetadata(c = "com.miui.handwriting.common.ai.viewmodel.AIViewModel$requestUploadMeta$1$1$1", f = "AIViewModel.kt", i = {}, l = {212}, m = "invokeSuspend", n = {}, s = {})
            static final class C00551 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
                final /* synthetic */ String $image;
                final /* synthetic */ String $mid;
                final /* synthetic */ String $prompt;
                final /* synthetic */ float $rate;
                final /* synthetic */ ThemeItem $theme;
                final /* synthetic */ Result<UploadMetaData> $uploadMetaResult;
                int label;
                final /* synthetic */ AIViewModel this$0;

                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                C00551(AIViewModel aIViewModel, String str, String str2, Result<UploadMetaData> result, ThemeItem themeItem, String str3, float f, Continuation<? super C00551> continuation) {
                    super(2, continuation);
                    this.this$0 = aIViewModel;
                    this.$mid = str;
                    this.$image = str2;
                    this.$uploadMetaResult = result;
                    this.$theme = themeItem;
                    this.$prompt = str3;
                    this.$rate = f;
                }

                @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
                public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
                    return new C00551(this.this$0, this.$mid, this.$image, this.$uploadMetaResult, this.$theme, this.$prompt, this.$rate, continuation);
                }

                @Override // kotlin.jvm.functions.Function2
                public final Object invoke(CoroutineScope coroutineScope, Continuation<? super Unit> continuation) {
                    return ((C00551) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
                }

                @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
                public final Object invokeSuspend(Object obj) {
                    Object coroutine_suspended = IntrinsicsKt.getCOROUTINE_SUSPENDED();
                    int i = this.label;
                    if (i == 0) {
                        ResultKt.throwOnFailure(obj);
                        this.label = 1;
                        if (this.this$0.requestCreation(this.$mid, this.$image, this.$uploadMetaResult.getData().getKey(), this.$uploadMetaResult.getData().getOptions().getIv(), this.$theme, this.$prompt, this.$rate, this.$uploadMetaResult.getData().getRequestId(), this) == coroutine_suspended) {
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
            }

            /* JADX INFO: renamed from: com.miui.handwriting.common.ai.viewmodel.AIViewModel$requestUploadMeta$1$1$2, reason: invalid class name */
            /* JADX INFO: compiled from: AIViewModel.kt */
            @Metadata(d1 = {"\u0000\n\n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\u0010\u0000\u001a\u00020\u0001*\u00020\u0002H\n"}, d2 = {"<anonymous>", "", "Lkotlinx/coroutines/CoroutineScope;"}, k = 3, mv = {2, 2, 0}, xi = 48)
            @DebugMetadata(c = "com.miui.handwriting.common.ai.viewmodel.AIViewModel$requestUploadMeta$1$1$2", f = "AIViewModel.kt", i = {}, l = {}, m = "invokeSuspend", n = {}, s = {})
            static final class AnonymousClass2 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
                int label;

                AnonymousClass2(Continuation<? super AnonymousClass2> continuation) {
                    super(2, continuation);
                }

                @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
                public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
                    return new AnonymousClass2(continuation);
                }

                @Override // kotlin.jvm.functions.Function2
                public final Object invoke(CoroutineScope coroutineScope, Continuation<? super Unit> continuation) {
                    return ((AnonymousClass2) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
                }

                @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
                public final Object invokeSuspend(Object obj) {
                    IntrinsicsKt.getCOROUTINE_SUSPENDED();
                    if (this.label != 0) {
                        throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                    }
                    ResultKt.throwOnFailure(obj);
                    Toast.makeText(HandWritingViewApplication.INSTANCE.getApplication(), HandWritingViewApplication.INSTANCE.getApplication().getString(R.string.creation_ai_disable_limit_message), 0).show();
                    return Unit.INSTANCE;
                }
            }

            /* JADX INFO: renamed from: com.miui.handwriting.common.ai.viewmodel.AIViewModel$requestUploadMeta$1$1$3, reason: invalid class name */
            /* JADX INFO: compiled from: AIViewModel.kt */
            @Metadata(d1 = {"\u0000\n\n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\u0010\u0000\u001a\u00020\u0001*\u00020\u0002H\n"}, d2 = {"<anonymous>", "", "Lkotlinx/coroutines/CoroutineScope;"}, k = 3, mv = {2, 2, 0}, xi = 48)
            @DebugMetadata(c = "com.miui.handwriting.common.ai.viewmodel.AIViewModel$requestUploadMeta$1$1$3", f = "AIViewModel.kt", i = {}, l = {}, m = "invokeSuspend", n = {}, s = {})
            static final class AnonymousClass3 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
                int label;
                final /* synthetic */ AIViewModel this$0;

                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                AnonymousClass3(AIViewModel aIViewModel, Continuation<? super AnonymousClass3> continuation) {
                    super(2, continuation);
                    this.this$0 = aIViewModel;
                }

                @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
                public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
                    return new AnonymousClass3(this.this$0, continuation);
                }

                @Override // kotlin.jvm.functions.Function2
                public final Object invoke(CoroutineScope coroutineScope, Continuation<? super Unit> continuation) {
                    return ((AnonymousClass3) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
                }

                @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
                public final Object invokeSuspend(Object obj) {
                    IntrinsicsKt.getCOROUTINE_SUSPENDED();
                    if (this.label != 0) {
                        throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                    }
                    ResultKt.throwOnFailure(obj);
                    if (!Utils.isNetworkAvailable(HandWritingViewApplication.INSTANCE.getApplication())) {
                        Toast.makeText(HandWritingViewApplication.INSTANCE.getApplication(), HandWritingViewApplication.INSTANCE.getApplication().getString(R.string.creation_ai_network_error_message), 0).show();
                    }
                    this.this$0._errorCreation.postValue(Boxing.boxBoolean(true));
                    return Unit.INSTANCE;
                }
            }
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            Object coroutine_suspended = IntrinsicsKt.getCOROUTINE_SUSPENDED();
            int i = this.label;
            if (i == 0) {
                ResultKt.throwOnFailure(obj);
                this.label = 1;
                if (BuildersKt.withContext(Dispatchers.getIO(), new C00541(this.$mid, this.$image, this.this$0, this.$theme, this.$prompt, this.$rate, null), this) == coroutine_suspended) {
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
    }

    public final void requestUploadMeta(String mid, String image, ThemeItem theme, String prompt, float rate) {
        Intrinsics.checkNotNullParameter(mid, "mid");
        Intrinsics.checkNotNullParameter(image, "image");
        Intrinsics.checkNotNullParameter(theme, "theme");
        Intrinsics.checkNotNullParameter(prompt, "prompt");
        cancel();
        this.uploadJob = BuildersKt__Builders_commonKt.launch$default(ViewModelKt.getViewModelScope(this), null, null, new C02281(mid, image, this, theme, prompt, rate, null), 3, null);
    }

    @Override // androidx.lifecycle.ViewModel
    protected void onCleared() {
        super.onCleared();
        cancel();
    }

    public final void cancel() {
        this.sensitiveToast = false;
        Job job = this.uploadJob;
        if (job != null) {
            Job.DefaultImpls.cancel$default(job, (CancellationException) null, 1, (Object) null);
        }
        Job job2 = this.requestEnhanceJob;
        if (job2 != null) {
            Job.DefaultImpls.cancel$default(job2, (CancellationException) null, 1, (Object) null);
        }
        this.imageWidth = 0;
        this.imageHeight = 0;
        Iterator<Call> it = this.uploadCallList.iterator();
        Intrinsics.checkNotNullExpressionValue(it, "iterator(...)");
        while (it.hasNext()) {
            Call next = it.next();
            Intrinsics.checkNotNullExpressionValue(next, "next(...)");
            next.cancel();
        }
        this.uploadCallList.clear();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final Object requestCreation(String str, String str2, String str3, String str4, ThemeItem themeItem, String str5, float f, String str6, Continuation<? super Unit> continuation) throws Exception {
        byte[] imageByteArray = Utils.getImageByteArray(str2);
        Log.d(TAG, "imageByte size:" + imageByteArray.length + ' ');
        byte[] bArrDecode = Base64.decode(str3, 0);
        SecretKey secretKeyLoadKey = AESUtil.loadKey(bArrDecode);
        StringBuilder sbAppend = new StringBuilder("serverKey :").append(str3).append("  decode:");
        Intrinsics.checkNotNull(bArrDecode);
        Log.d(TAG, sbAppend.append(ArraysKt.asList(bArrDecode)).toString());
        byte[] bArrDecode2 = Base64.decode(str4, 0);
        GCMParameterSpec gCMParameterSpecLoadGCMParameterSpec = AESUtil.loadGCMParameterSpec(bArrDecode2);
        StringBuilder sbAppend2 = new StringBuilder("iv:").append(str4).append("  toByte:");
        Intrinsics.checkNotNull(bArrDecode2);
        Log.d(TAG, sbAppend2.append(ArraysKt.asList(bArrDecode2)).toString());
        byte[] bArrEncrypt = AESUtil.encrypt(imageByteArray, secretKeyLoadKey, gCMParameterSpecLoadGCMParameterSpec);
        MultipartBody.Builder builder = new MultipartBody.Builder(null, 1, null);
        builder.setType(MultipartBody.FORM);
        builder.addFormDataPart("miId", str);
        String oAId = DeviceUtils.getOAId();
        Intrinsics.checkNotNullExpressionValue(oAId, "getOAId(...)");
        builder.addFormDataPart("oaid", oAId);
        builder.addFormDataPart("timestamp", String.valueOf(System.currentTimeMillis()));
        builder.addFormDataPart("theme", themeItem.getTheme());
        builder.addFormDataPart("themeId", String.valueOf(themeItem.getThemeId()));
        builder.addFormDataPart("prompt", str5);
        builder.addFormDataPart("rate", String.valueOf(f));
        builder.addFormDataPart("requestId", str6);
        builder.addFormDataPart("type", String.valueOf(this.creationType));
        String packageName = HandWritingViewApplication.INSTANCE.getApplication().getPackageName();
        Intrinsics.checkNotNullExpressionValue(packageName, "getPackageName(...)");
        PackageManager packageManager = HandWritingViewApplication.INSTANCE.getApplication().getPackageManager();
        Intrinsics.checkNotNullExpressionValue(packageManager, "getPackageManager(...)");
        PackageInfo packageInfo = packageManager.getPackageInfo(packageName, 0);
        Intrinsics.checkNotNullExpressionValue(packageInfo, "getPackageInfo(...)");
        String str7 = packageInfo.versionName;
        long longVersionCode = packageInfo.getLongVersionCode();
        Intrinsics.checkNotNull(str7);
        builder.addFormDataPart("_apkversion", str7);
        builder.addFormDataPart(PARAM_APK_VERSION_CODE, String.valueOf(longVersionCode));
        File fileSaveFile = Utils.saveFile(bArrEncrypt);
        Intrinsics.checkNotNullExpressionValue(fileSaveFile, "saveFile(...)");
        builder.addFormDataPart("file", fileSaveFile.getName(), RequestBody.INSTANCE.create(fileSaveFile, MediaType.INSTANCE.get("application/octet-stream")));
        Log.d(TAG, "multipartBody=={mid:" + str + ",oid:" + DeviceUtils.getOAId() + ",theme:" + themeItem + ",prompt:" + str5 + ",rate:" + f + ",requestId:" + str6 + ",fileName:" + fileSaveFile.getName() + ",type:" + this.creationType + CoreConstants.CURLY_RIGHT);
        Log.e(TAG, "multipartBody==request:" + str6 + CoreConstants.COMMA_CHAR);
        Result<CreationDataForOS1> result = (Result) new Gson().fromJson(CreationAIRequest.INSTANCE.upload(URL_CREATION, builder.build(), this.uploadCallList), new TypeToken<Result<CreationDataForOS1>>() { // from class: com.miui.handwriting.common.ai.viewmodel.AIViewModel$requestCreation$result$1
        }.getType());
        Log.d(TAG, "requestCreation result:" + result);
        Object objPostSmallImageResult = postSmallImageResult(result, continuation);
        return objPostSmallImageResult == IntrinsicsKt.getCOROUTINE_SUSPENDED() ? objPostSmallImageResult : Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final Object postSmallImageResult(Result<CreationDataForOS1> result, Continuation<? super Unit> continuation) {
        CreationImageForOS1 creationImageForOS1;
        if (this.currentPage == 0) {
            return Unit.INSTANCE;
        }
        int i = 0;
        if (result != null && result.getCode() == 0 && !result.getData().getResult().isEmpty()) {
            creationImageForOS1 = result.getData().getResult().get(0);
        } else {
            Log.e(TAG, "requestCreation result:" + result);
            String ERROR_PIC = CreationAiFragmentForOS1.ERROR_PIC;
            Intrinsics.checkNotNullExpressionValue(ERROR_PIC, "ERROR_PIC");
            creationImageForOS1 = new CreationImageForOS1(ERROR_PIC);
        }
        Iterator<CreationImageForOS1> it = this.previewItemList.iterator();
        while (it.hasNext() && !TextUtils.equals(it.next().getImageData(), CreationAiFragmentForOS1.EMPTY_PIC)) {
            i++;
        }
        if (i < this.previewItemList.size()) {
            this.previewItemList.get(i).setImageData(creationImageForOS1.getImageData());
        }
        this._creationSmallImageLiveData.postValue(creationImageForOS1);
        if (!this.sensitiveToast) {
            this.sensitiveToast = true;
            Object objWithContext = BuildersKt.withContext(Dispatchers.getMain(), new AnonymousClass2(result, null), continuation);
            return objWithContext == IntrinsicsKt.getCOROUTINE_SUSPENDED() ? objWithContext : Unit.INSTANCE;
        }
        return Unit.INSTANCE;
    }

    /* JADX INFO: renamed from: com.miui.handwriting.common.ai.viewmodel.AIViewModel$postSmallImageResult$2, reason: invalid class name */
    /* JADX INFO: compiled from: AIViewModel.kt */
    @Metadata(d1 = {"\u0000\n\n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\u0010\u0000\u001a\u00020\u0001*\u00020\u0002H\n"}, d2 = {"<anonymous>", "", "Lkotlinx/coroutines/CoroutineScope;"}, k = 3, mv = {2, 2, 0}, xi = 48)
    @DebugMetadata(c = "com.miui.handwriting.common.ai.viewmodel.AIViewModel$postSmallImageResult$2", f = "AIViewModel.kt", i = {}, l = {}, m = "invokeSuspend", n = {}, s = {})
    static final class AnonymousClass2 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
        final /* synthetic */ Result<CreationDataForOS1> $result;
        int label;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        AnonymousClass2(Result<CreationDataForOS1> result, Continuation<? super AnonymousClass2> continuation) {
            super(2, continuation);
            this.$result = result;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
            return new AnonymousClass2(this.$result, continuation);
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(CoroutineScope coroutineScope, Continuation<? super Unit> continuation) {
            return ((AnonymousClass2) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            IntrinsicsKt.getCOROUTINE_SUSPENDED();
            if (this.label != 0) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            ResultKt.throwOnFailure(obj);
            Result<CreationDataForOS1> result = this.$result;
            if (result != null) {
                if (result.getCode() == 10110) {
                    Toast.makeText(HandWritingViewApplication.INSTANCE.getApplication(), HandWritingViewApplication.INSTANCE.getApplication().getString(R.string.creation_sensitive_prompt), 0).show();
                } else if (this.$result.getCode() == 10101) {
                    Toast.makeText(HandWritingViewApplication.INSTANCE.getApplication(), HandWritingViewApplication.INSTANCE.getApplication().getString(R.string.creation_sensitive_preiview), 0).show();
                }
            } else if (!Utils.isNetworkAvailable(HandWritingViewApplication.INSTANCE.getApplication())) {
                Toast.makeText(HandWritingViewApplication.INSTANCE.getApplication(), HandWritingViewApplication.INSTANCE.getApplication().getString(R.string.creation_ai_network_error_message), 0).show();
            }
            return Unit.INSTANCE;
        }
    }

    /* JADX INFO: renamed from: com.miui.handwriting.common.ai.viewmodel.AIViewModel$requestEnhance$1, reason: invalid class name */
    /* JADX INFO: compiled from: AIViewModel.kt */
    @Metadata(d1 = {"\u0000\n\n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\u0010\u0000\u001a\u00020\u0001*\u00020\u0002H\n"}, d2 = {"<anonymous>", "", "Lkotlinx/coroutines/CoroutineScope;"}, k = 3, mv = {2, 2, 0}, xi = 48)
    @DebugMetadata(c = "com.miui.handwriting.common.ai.viewmodel.AIViewModel$requestEnhance$1", f = "AIViewModel.kt", i = {}, l = {421}, m = "invokeSuspend", n = {}, s = {})
    static final class AnonymousClass1 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
        final /* synthetic */ String $imageData;
        final /* synthetic */ String $mid;
        final /* synthetic */ String $prompt;
        final /* synthetic */ int $rate;
        final /* synthetic */ ThemeItem $theme;
        int label;
        final /* synthetic */ AIViewModel this$0;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        AnonymousClass1(String str, String str2, ThemeItem themeItem, String str3, int i, AIViewModel aIViewModel, Continuation<? super AnonymousClass1> continuation) {
            super(2, continuation);
            this.$mid = str;
            this.$imageData = str2;
            this.$theme = themeItem;
            this.$prompt = str3;
            this.$rate = i;
            this.this$0 = aIViewModel;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
            return new AnonymousClass1(this.$mid, this.$imageData, this.$theme, this.$prompt, this.$rate, this.this$0, continuation);
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(CoroutineScope coroutineScope, Continuation<? super Unit> continuation) {
            return ((AnonymousClass1) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
        }

        /* JADX INFO: renamed from: com.miui.handwriting.common.ai.viewmodel.AIViewModel$requestEnhance$1$1, reason: invalid class name and collision with other inner class name */
        /* JADX INFO: compiled from: AIViewModel.kt */
        @Metadata(d1 = {"\u0000\n\n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\u0010\u0000\u001a\u00020\u0001*\u00020\u0002H\n"}, d2 = {"<anonymous>", "", "Lkotlinx/coroutines/CoroutineScope;"}, k = 3, mv = {2, 2, 0}, xi = 48)
        @DebugMetadata(c = "com.miui.handwriting.common.ai.viewmodel.AIViewModel$requestEnhance$1$1", f = "AIViewModel.kt", i = {0, 0, 0, 0, 0}, l = {R2.attr.backgroundPrimaryDisableColor}, m = "invokeSuspend", n = {"params", "data", "dataJson", ah.H, "result"}, s = {"L$0", "L$1", "L$2", "L$3", "L$4"})
        static final class C00511 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
            final /* synthetic */ String $imageData;
            final /* synthetic */ String $mid;
            final /* synthetic */ String $prompt;
            final /* synthetic */ int $rate;
            final /* synthetic */ ThemeItem $theme;
            Object L$0;
            Object L$1;
            Object L$2;
            Object L$3;
            Object L$4;
            int label;
            final /* synthetic */ AIViewModel this$0;

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            C00511(String str, String str2, ThemeItem themeItem, String str3, int i, AIViewModel aIViewModel, Continuation<? super C00511> continuation) {
                super(2, continuation);
                this.$mid = str;
                this.$imageData = str2;
                this.$theme = themeItem;
                this.$prompt = str3;
                this.$rate = i;
                this.this$0 = aIViewModel;
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
                return new C00511(this.$mid, this.$imageData, this.$theme, this.$prompt, this.$rate, this.this$0, continuation);
            }

            @Override // kotlin.jvm.functions.Function2
            public final Object invoke(CoroutineScope coroutineScope, Continuation<? super Unit> continuation) {
                return ((C00511) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Object invokeSuspend(Object obj) {
                Result result;
                Object coroutine_suspended = IntrinsicsKt.getCOROUTINE_SUSPENDED();
                int i = this.label;
                try {
                    if (i == 0) {
                        ResultKt.throwOnFailure(obj);
                        HashMap<String, String> map = new HashMap<>();
                        map.put("miId", this.$mid);
                        map.put("oaid", DeviceUtils.getOAId());
                        map.put("timestamp", String.valueOf(System.currentTimeMillis()));
                        ParamCreationImage paramCreationImage = new ParamCreationImage(this.$imageData, String.valueOf(this.$theme.getThemeId()), this.$theme.getTheme(), this.$prompt, this.$rate, this.this$0.getImageHeight(), this.this$0.getImageWidth());
                        String json = new Gson().toJson(paramCreationImage);
                        map.put("data", json);
                        Log.d(AIViewModel.TAG, "requestEnhance params:" + paramCreationImage);
                        String strPost = CreationAIRequest.INSTANCE.post(AIViewModel.INSTANCE.getURL_ENHANCE(), null, map);
                        Result result2 = (Result) new Gson().fromJson(strPost, new TypeToken<Result<CreationImageForOS1>>() { // from class: com.miui.handwriting.common.ai.viewmodel.AIViewModel$requestEnhance$1$1$result$1
                        }.getType());
                        this.this$0._creationBigImageLiveData.postValue(result2.getData());
                        this.L$0 = SpillingKt.nullOutSpilledVariable(map);
                        this.L$1 = SpillingKt.nullOutSpilledVariable(paramCreationImage);
                        this.L$2 = SpillingKt.nullOutSpilledVariable(json);
                        this.L$3 = SpillingKt.nullOutSpilledVariable(strPost);
                        this.L$4 = result2;
                        this.label = 1;
                        if (BuildersKt.withContext(Dispatchers.getMain(), new C00521(result2, null), this) == coroutine_suspended) {
                            return coroutine_suspended;
                        }
                        result = result2;
                    } else {
                        if (i != 1) {
                            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                        }
                        result = (Result) this.L$4;
                        ResultKt.throwOnFailure(obj);
                    }
                    Log.d(AIViewModel.TAG, "requestEnhance result:" + result);
                } catch (Exception e) {
                    Log.e(AIViewModel.TAG, "requestEnhance e:" + e);
                    this.this$0._creationBigImageLiveData.postValue(new CreationImageForOS1(this.$imageData));
                }
                return Unit.INSTANCE;
            }

            /* JADX INFO: renamed from: com.miui.handwriting.common.ai.viewmodel.AIViewModel$requestEnhance$1$1$1, reason: invalid class name and collision with other inner class name */
            /* JADX INFO: compiled from: AIViewModel.kt */
            @Metadata(d1 = {"\u0000\n\n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\u0010\u0000\u001a\u00020\u0001*\u00020\u0002H\n"}, d2 = {"<anonymous>", "", "Lkotlinx/coroutines/CoroutineScope;"}, k = 3, mv = {2, 2, 0}, xi = 48)
            @DebugMetadata(c = "com.miui.handwriting.common.ai.viewmodel.AIViewModel$requestEnhance$1$1$1", f = "AIViewModel.kt", i = {}, l = {}, m = "invokeSuspend", n = {}, s = {})
            static final class C00521 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
                final /* synthetic */ Result<CreationImageForOS1> $result;
                int label;

                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                C00521(Result<CreationImageForOS1> result, Continuation<? super C00521> continuation) {
                    super(2, continuation);
                    this.$result = result;
                }

                @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
                public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
                    return new C00521(this.$result, continuation);
                }

                @Override // kotlin.jvm.functions.Function2
                public final Object invoke(CoroutineScope coroutineScope, Continuation<? super Unit> continuation) {
                    return ((C00521) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
                }

                @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
                public final Object invokeSuspend(Object obj) {
                    IntrinsicsKt.getCOROUTINE_SUSPENDED();
                    if (this.label != 0) {
                        throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                    }
                    ResultKt.throwOnFailure(obj);
                    int code = this.$result.getCode();
                    if (code == 10101) {
                        Toast.makeText(HandWritingViewApplication.INSTANCE.getApplication(), HandWritingViewApplication.INSTANCE.getApplication().getString(R.string.creation_sensitive_preiview), 0).show();
                    } else if (code == 10110) {
                        Toast.makeText(HandWritingViewApplication.INSTANCE.getApplication(), HandWritingViewApplication.INSTANCE.getApplication().getString(R.string.creation_sensitive_prompt), 0).show();
                    } else if (code == 10111) {
                        Toast.makeText(HandWritingViewApplication.INSTANCE.getApplication(), this.$result.getDescription(), 0).show();
                    }
                    return Unit.INSTANCE;
                }
            }
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            Object coroutine_suspended = IntrinsicsKt.getCOROUTINE_SUSPENDED();
            int i = this.label;
            if (i == 0) {
                ResultKt.throwOnFailure(obj);
                this.label = 1;
                if (BuildersKt.withContext(Dispatchers.getIO(), new C00511(this.$mid, this.$imageData, this.$theme, this.$prompt, this.$rate, this.this$0, null), this) == coroutine_suspended) {
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
    }

    public final void requestEnhance(String mid, String imageData, ThemeItem theme, String prompt, int rate) {
        Intrinsics.checkNotNullParameter(mid, "mid");
        Intrinsics.checkNotNullParameter(imageData, "imageData");
        Intrinsics.checkNotNullParameter(theme, "theme");
        Intrinsics.checkNotNullParameter(prompt, "prompt");
        this.requestEnhanceJob = BuildersKt__Builders_commonKt.launch$default(ViewModelKt.getViewModelScope(this), null, null, new AnonymousClass1(mid, imageData, theme, prompt, rate, this, null), 3, null);
    }
}
