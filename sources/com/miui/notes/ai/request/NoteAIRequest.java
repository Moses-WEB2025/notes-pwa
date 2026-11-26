package com.miui.notes.ai.request;

import android.accounts.AccountManager;
import android.accounts.AccountManagerCallback;
import android.accounts.AuthenticatorException;
import android.accounts.OperationCanceledException;
import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import com.google.gson.Gson;
import com.market.sdk.reflect.ReflectUtilsForMiui;
import com.miui.common.app.BaseNoteApp;
import com.miui.notes.base.utils.Logger;
import com.xiaomi.micloudsdk.data.ExtendedAuthToken;
import com.xiaomi.micloudsdk.exception.CloudServerException;
import com.xiaomi.micloudsdk.request.utils.Request;
import com.xiaomi.mirror.synergy.CallMethod;
import java.io.IOException;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;
import miuix.micloudview.accounts.ExtraAccountManager;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Response;
import okhttp3.ResponseBody;

/* JADX INFO: compiled from: NoteAIRequest.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0004\u0018\u0000 \u00042\u00020\u0001:\u0001\u0004B\u0007¢\u0006\u0004\b\u0002\u0010\u0003¨\u0006\u0005"}, d2 = {"Lcom/miui/notes/ai/request/NoteAIRequest;", "", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "()V", "Companion", "app_PhoneGlobalRelease"}, k = 1, mv = {2, 2, 0}, xi = 48)
public final class NoteAIRequest {
    public static final String AI_SID = "hyperos_sec_common";
    public static final int ERROR_CODE_AUTHENTICATOR_EXCEPTION = -2;
    public static final int ERROR_CODE_FOREVER_BANNED = 20099125;
    public static final int ERROR_CODE_ILLEGAL_1 = 20099126;
    public static final int ERROR_CODE_ILLEGAL_2 = 20099127;
    public static final int ERROR_CODE_TEMP_BANNED = 20099124;
    public static final int RESULT_CODE_OK = 0;
    private static final int RETRY_COUNT = 3;
    public static final String TAG = "CreationRequest";

    /* JADX INFO: renamed from: Companion, reason: from kotlin metadata */
    public static final Companion INSTANCE = new Companion(null);
    private static final OkHttpClient okHttpClient = new OkHttpClient.Builder().connectTimeout(20, TimeUnit.SECONDS).writeTimeout(20, TimeUnit.SECONDS).readTimeout(20, TimeUnit.SECONDS).build();

    /* JADX INFO: compiled from: NoteAIRequest.kt */
    @Metadata(d1 = {"\u0000>\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\t\b\u0002¢\u0006\u0004\b\u0002\u0010\u0003J\u0006\u0010\u0011\u001a\u00020\u0012J:\u0010\u0013\u001a\u00020\u00052\u0006\u0010\u0014\u001a\u00020\u00052\u0014\u0010\u0015\u001a\u0010\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u0005\u0018\u00010\u00162\u0014\u0010\u0017\u001a\u0010\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u0005\u0018\u00010\u0016J:\u0010\u0018\u001a\u00020\u00052\u0006\u0010\u0014\u001a\u00020\u00052\u0014\u0010\u0015\u001a\u0010\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u0005\u0018\u00010\u00162\u0014\u0010\u0017\u001a\u0010\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u0005\u0018\u00010\u0016J:\u0010\u0019\u001a\u00020\u00052\u0006\u0010\u0014\u001a\u00020\u00052\u0014\u0010\u0015\u001a\u0010\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u0005\u0018\u00010\u00162\u0014\u0010\u0017\u001a\u0010\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u0005\u0018\u00010\u0016J\u0010\u0010\u001a\u001a\u00020\u001b2\b\u0010\u001c\u001a\u0004\u0018\u00010\u001dR\u000e\u0010\u0004\u001a\u00020\u0005X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0005X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\bX\u0082T¢\u0006\u0002\n\u0000R\u000e\u0010\t\u001a\u00020\bX\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\n\u001a\u00020\bX\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\u000b\u001a\u00020\bX\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\f\u001a\u00020\bX\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\r\u001a\u00020\bX\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\u000e\u001a\u00020\bX\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\u000f\u001a\u00020\u0010X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u001e"}, d2 = {"Lcom/miui/notes/ai/request/NoteAIRequest$Companion;", "", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "()V", "TAG", "", "AI_SID", "RETRY_COUNT", "", "ERROR_CODE_AUTHENTICATOR_EXCEPTION", "RESULT_CODE_OK", "ERROR_CODE_ILLEGAL_1", "ERROR_CODE_ILLEGAL_2", "ERROR_CODE_TEMP_BANNED", "ERROR_CODE_FOREVER_BANNED", "okHttpClient", "Lokhttp3/OkHttpClient;", "getAuthToken", "Landroid/os/Bundle;", CallMethod.METHOD_GET, "url", "headers", "Ljava/util/HashMap;", "params", "post", "debugPost", "isLogin", "", "activity", "Landroid/app/Activity;", "app_PhoneGlobalRelease"}, k = 1, mv = {2, 2, 0}, xi = 48)
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }

        public final Bundle getAuthToken() throws OperationCanceledException, IOException, AuthenticatorException {
            Bundle result = AccountManager.get(BaseNoteApp.INSTANCE.getInstance()).getAuthToken(ExtraAccountManager.getXiaomiAccount(BaseNoteApp.INSTANCE.getInstance()), "hyperos_sec_common", (Bundle) null, (Activity) null, (AccountManagerCallback<Bundle>) null, (Handler) null).getResult();
            Intrinsics.checkNotNullExpressionValue(result, "getResult(...)");
            return result;
        }

        public final String get(String url, HashMap<String, String> headers, HashMap<String, String> params) throws BadPaddingException, CloudServerException, IllegalBlockSizeException, IOException {
            Intrinsics.checkNotNullParameter(url, "url");
            int i = 0;
            do {
                String string = getAuthToken().getString("authtoken");
                if (string == null) {
                    String json = new Gson().toJson(new ResponseData(-2, "", "", "{}"));
                    Intrinsics.checkNotNullExpressionValue(json, "toJson(...)");
                    return json;
                }
                ExtendedAuthToken extendedAuthToken = ExtendedAuthToken.parse(string);
                try {
                    HashMap map = new HashMap();
                    map.put("Accept-Language", Locale.getDefault().toString());
                    if (headers != null) {
                        map.putAll(headers);
                    }
                    String strSecureGet = Request.secureGet(url, map, params, null, extendedAuthToken);
                    Intrinsics.checkNotNullExpressionValue(strSecureGet, "secureGet(...)");
                    return strSecureGet;
                } catch (CloudServerException e) {
                    if (StringsKt.contains$default((CharSequence) e.toString(), (CharSequence) "401", false, 2, (Object) null) && i < 3) {
                        Logger.INSTANCE.w("CreationRequest", "request failed, invalid and retry");
                        AccountManager.get(BaseNoteApp.INSTANCE.getInstance()).invalidateAuthToken("com.xiaomi", string);
                        i++;
                    } else {
                        throw e;
                    }
                }
            } while (i <= 3);
            return "";
        }

        public final String post(String url, HashMap<String, String> headers, HashMap<String, String> params) throws Exception {
            Intrinsics.checkNotNullParameter(url, "url");
            int i = 0;
            do {
                try {
                    String string = getAuthToken().getString("authtoken");
                    if (string == null) {
                        String json = new Gson().toJson(new ResponseData(-2, "", "", null));
                        Intrinsics.checkNotNullExpressionValue(json, "toJson(...)");
                        return json;
                    }
                    ExtendedAuthToken extendedAuthToken = ExtendedAuthToken.parse(string);
                    HashMap map = new HashMap();
                    map.put("Accept-Language", Locale.getDefault().toString());
                    if (headers != null) {
                        map.putAll(headers);
                    }
                    String strSecurePost = Request.securePost(url, map, params, null, extendedAuthToken);
                    Intrinsics.checkNotNullExpressionValue(strSecurePost, "securePost(...)");
                    return strSecurePost;
                } catch (CloudServerException e) {
                    if (StringsKt.contains$default((CharSequence) e.toString(), (CharSequence) "401", false, 2, (Object) null) && i < 3) {
                        Logger.INSTANCE.w("CreationRequest", "request failed, invalid and retry");
                        AccountManager.get(BaseNoteApp.INSTANCE.getInstance()).invalidateAuthToken("com.xiaomi", null);
                        i++;
                    } else {
                        throw e;
                    }
                } catch (Exception e2) {
                    throw e2;
                }
            } while (i <= 3);
            return "";
        }

        public final String debugPost(String url, HashMap<String, String> headers, HashMap<String, String> params) throws IOException {
            ResponseBody responseBodyBody;
            String strString;
            Intrinsics.checkNotNullParameter(url, "url");
            FormBody.Builder builder = new FormBody.Builder(null, 1, null);
            if (params != null) {
                for (Map.Entry<String, String> entry : params.entrySet()) {
                    builder.add(entry.getKey(), entry.getValue());
                }
            }
            okhttp3.Request.Builder builder2 = new okhttp3.Request.Builder();
            if (headers != null) {
                for (Map.Entry<String, String> entry2 : headers.entrySet()) {
                    builder2.addHeader(entry2.getKey(), entry2.getValue());
                }
            }
            builder2.url(url);
            builder2.post(builder.build());
            Response responseExecute = NoteAIRequest.okHttpClient.newCall(builder2.build()).execute();
            return (responseExecute.code() != 200 || (responseBodyBody = responseExecute.body()) == null || (strString = responseBodyBody.string()) == null) ? "" : strString;
        }

        public final boolean isLogin(Activity activity) {
            if (ExtraAccountManager.getXiaomiAccount(BaseNoteApp.INSTANCE.getInstance()) != null) {
                return true;
            }
            AccountManager.get(BaseNoteApp.INSTANCE.getInstance()).addAccount("com.xiaomi", "hyperos_sec_common", null, null, activity, null, null);
            return false;
        }
    }
}
