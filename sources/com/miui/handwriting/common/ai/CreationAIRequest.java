package com.miui.handwriting.common.ai;

import android.accounts.AccountManager;
import android.accounts.AccountManagerCallback;
import android.accounts.AuthenticatorException;
import android.accounts.OperationCanceledException;
import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import com.google.gson.Gson;
import com.market.sdk.reflect.ReflectUtilsForMiui;
import com.miui.handwriting.common.HandWritingViewApplication;
import com.miui.handwriting.common.ai.data.Result;
import com.miui.handwriting.common.ai.data.VersionData;
import com.miui.handwriting.common.ai.viewmodel.AIViewModel;
import com.miui.libhandwritingpage.utils.RomUtils;
import com.xiaomi.micloudsdk.data.ExtendedAuthToken;
import com.xiaomi.micloudsdk.exception.CloudServerException;
import com.xiaomi.micloudsdk.request.utils.Request;
import com.xiaomi.micloudsdk.request.utils.RequestContext;
import com.xiaomi.mirror.synergy.CallMethod;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Locale;
import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;
import miuix.micloudview.accounts.ExtraAccountManager;
import okhttp3.Call;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;

/* JADX INFO: compiled from: CreationAIRequest.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0004\u0018\u0000 \u00042\u00020\u0001:\u0001\u0004B\u0007¢\u0006\u0004\b\u0002\u0010\u0003¨\u0006\u0005"}, d2 = {"Lcom/miui/handwriting/common/ai/CreationAIRequest;", "", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "()V", "Companion", "libHandWritingCommon_release"}, k = 1, mv = {2, 2, 0}, xi = 48)
public final class CreationAIRequest {
    private static final String AI_SID;
    public static final int CODE_NOT_ENOUGH_CREATE_TIME = 70002;

    /* JADX INFO: renamed from: Companion, reason: from kotlin metadata */
    public static final Companion INSTANCE = new Companion(null);
    public static final int RETRY_COUNT = 3;
    public static final int STATUS_PRODUCT_SENSITIVE_IMAGE = 10111;
    public static final int STATUS_SENSITIVE_PREVIEW = 10101;
    public static final int STATUS_SENSITIVE_PROMPT = 10110;
    public static final String TAG = "CreationRequest";

    /* JADX INFO: compiled from: CreationAIRequest.kt */
    @Metadata(d1 = {"\u0000<\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0002\b\u0004\n\u0002\u0010\b\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\t\b\u0002¢\u0006\u0004\b\u0002\u0010\u0003J\u0006\u0010\u000f\u001a\u00020\u0010J:\u0010\u0011\u001a\u00020\u00052\u0006\u0010\u0012\u001a\u00020\u00052\u0014\u0010\u0013\u001a\u0010\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u0005\u0018\u00010\u00142\u0014\u0010\u0015\u001a\u0010\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u0005\u0018\u00010\u0014J:\u0010\u0016\u001a\u00020\u00052\u0006\u0010\u0012\u001a\u00020\u00052\u0014\u0010\u0013\u001a\u0010\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u0005\u0018\u00010\u00142\u0014\u0010\u0015\u001a\u0010\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u0005\u0018\u00010\u0014J$\u0010\u0017\u001a\u00020\u00052\u0006\u0010\u0012\u001a\u00020\u00052\u0006\u0010\u0018\u001a\u00020\u00192\f\u0010\u001a\u001a\b\u0012\u0004\u0012\u00020\u001c0\u001bR\u000e\u0010\u0004\u001a\u00020\u0005X\u0086T¢\u0006\u0002\n\u0000R\u0011\u0010\u0006\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\bR\u000e\u0010\t\u001a\u00020\nX\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\u000b\u001a\u00020\nX\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\f\u001a\u00020\nX\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\r\u001a\u00020\nX\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\u000e\u001a\u00020\nX\u0086T¢\u0006\u0002\n\u0000¨\u0006\u001d"}, d2 = {"Lcom/miui/handwriting/common/ai/CreationAIRequest$Companion;", "", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "()V", "TAG", "", "AI_SID", "getAI_SID", "()Ljava/lang/String;", "RETRY_COUNT", "", "STATUS_SENSITIVE_PROMPT", "STATUS_SENSITIVE_PREVIEW", "STATUS_PRODUCT_SENSITIVE_IMAGE", "CODE_NOT_ENOUGH_CREATE_TIME", "getAuthToken", "Landroid/os/Bundle;", CallMethod.METHOD_GET, "url", "headers", "Ljava/util/HashMap;", "params", "post", "upload", "body", "Lokhttp3/RequestBody;", "uploadCallList", "Ljava/util/HashSet;", "Lokhttp3/Call;", "libHandWritingCommon_release"}, k = 1, mv = {2, 2, 0}, xi = 48)
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }

        public final String getAI_SID() {
            return CreationAIRequest.AI_SID;
        }

        public final Bundle getAuthToken() throws OperationCanceledException, IOException, AuthenticatorException {
            Bundle result = AccountManager.get(HandWritingViewApplication.INSTANCE.getApplication()).getAuthToken(ExtraAccountManager.getXiaomiAccount(HandWritingViewApplication.INSTANCE.getApplication()), getAI_SID(), (Bundle) null, (Activity) null, (AccountManagerCallback<Bundle>) null, (Handler) null).getResult();
            Intrinsics.checkNotNullExpressionValue(result, "getResult(...)");
            return result;
        }

        public final String get(String url, HashMap<String, String> headers, HashMap<String, String> params) {
            Intrinsics.checkNotNullParameter(url, "url");
            int i = 0;
            do {
                String string = getAuthToken().getString("authtoken");
                if (string == null) {
                    String json = new Gson().toJson(new Result("", false, -2, "", 0L, new VersionData("")));
                    Intrinsics.checkNotNullExpressionValue(json, "toJson(...)");
                    return json;
                }
                try {
                    String strSecureGet = Request.secureGet(url, headers, params, null, ExtendedAuthToken.parse(string));
                    Intrinsics.checkNotNullExpressionValue(strSecureGet, "secureGet(...)");
                    return strSecureGet;
                } catch (CloudServerException e) {
                    if (StringsKt.contains$default((CharSequence) e.toString(), (CharSequence) "401", false, 2, (Object) null) && i < 3) {
                        Log.w("CreationRequest", "request failed, invalid and retry");
                        AccountManager.get(HandWritingViewApplication.INSTANCE.getApplication()).invalidateAuthToken("com.xiaomi", string);
                        i++;
                    } else {
                        throw e;
                    }
                }
            } while (i <= 3);
            return "";
        }

        public final String post(String url, HashMap<String, String> headers, HashMap<String, String> params) {
            Intrinsics.checkNotNullParameter(url, "url");
            int i = 0;
            do {
                String string = getAuthToken().getString("authtoken");
                try {
                    String strSecurePost = Request.securePost(url, headers, params, null, ExtendedAuthToken.parse(string));
                    Intrinsics.checkNotNullExpressionValue(strSecurePost, "securePost(...)");
                    return strSecurePost;
                } catch (CloudServerException e) {
                    if (StringsKt.contains$default((CharSequence) e.toString(), (CharSequence) "401", false, 2, (Object) null) && i < 3) {
                        Log.w("CreationRequest", "request failed, invalid and retry");
                        AccountManager.get(HandWritingViewApplication.INSTANCE.getApplication()).invalidateAuthToken("com.xiaomi", string);
                        i++;
                    } else {
                        throw e;
                    }
                }
            } while (i <= 3);
            return "";
        }

        public final String upload(String url, RequestBody body, HashSet<Call> uploadCallList) {
            String strString;
            Intrinsics.checkNotNullParameter(url, "url");
            Intrinsics.checkNotNullParameter(body, "body");
            Intrinsics.checkNotNullParameter(uploadCallList, "uploadCallList");
            int i = 0;
            do {
                String string = getAuthToken().getString("authtoken");
                try {
                    okhttp3.Request.Builder builderAddHeader = new okhttp3.Request.Builder().url(url).addHeader("Content-Type", "multipart/form-data").addHeader("serviceToken", string == null ? "" : string);
                    String string2 = Locale.getDefault().toString();
                    Intrinsics.checkNotNullExpressionValue(string2, "toString(...)");
                    okhttp3.Request.Builder builderAddHeader2 = builderAddHeader.addHeader("accept-language", string2);
                    String userAgent = RequestContext.getUserAgent();
                    Intrinsics.checkNotNullExpressionValue(userAgent, "getUserAgent(...)");
                    Call callNewCall = AIViewModel.INSTANCE.getOkHttpClient().newCall(builderAddHeader2.addHeader("user-agent", userAgent).post(body).build());
                    uploadCallList.add(callNewCall);
                    Response responseExecute = callNewCall.execute();
                    Log.d("CreationRequest", "upload code:" + responseExecute);
                    if (responseExecute.code() == 200) {
                        ResponseBody responseBodyBody = responseExecute.body();
                        return (responseBodyBody == null || (strString = responseBodyBody.string()) == null) ? "" : strString;
                    }
                    if (responseExecute.code() == 401 && i < 3) {
                        Log.w("CreationRequest", "request failed, invalid and retry");
                        AccountManager.get(HandWritingViewApplication.INSTANCE.getApplication()).invalidateAuthToken("com.xiaomi", string);
                        i++;
                    } else {
                        Log.d("CreationRequest", "other code:" + responseExecute.code());
                        return "";
                    }
                } catch (Exception e) {
                    Log.d("CreationRequest", "e:" + e);
                    return "";
                }
            } while (i <= 3);
            return "";
        }
    }

    static {
        AI_SID = RomUtils.isInternationalBuild() ? "miuisec_avatar_intl" : "miuisec_avatar";
    }
}
