package com.miui.handwriting.common.ai;

import android.accounts.AccountManager;
import android.accounts.AccountManagerCallback;
import android.app.Activity;
import android.app.Application;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import com.google.gson.Gson;
import com.market.sdk.reflect.ReflectUtilsForMiui;
import com.miui.aiengine.common.Error;
import com.miui.aiengine.common.ExtendedAuthToken;
import com.miui.aiengine.common.SUCCESS;
import com.miui.aiengine.common.cloud.CloudServerException;
import com.miui.aiengine.common.request.HttpBodyEntity;
import com.miui.aiengine.common.request.HttpMethod;
import com.miui.aiengine.common.request.HttpRequest;
import com.miui.aiengine.common.utils.MiAccountManager;
import com.miui.aiengine.common.utils.OsUtils;
import com.miui.handwriting.common.HandWritingViewApplication;
import com.miui.handwriting.common.ai.data.Result;
import com.miui.handwriting.common.ai.data.VersionData;
import com.miui.libhandwritingpage.utils.RomUtils;
import com.xiaomi.mirror.synergy.CallMethod;
import java.net.SocketTimeoutException;
import java.util.HashMap;
import java.util.Locale;
import kotlin.Metadata;
import kotlin.NoWhenBranchMatchedException;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;
import miuix.micloudview.accounts.ExtraAccountManager;

/* JADX INFO: compiled from: CreationAIRequest2.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0004\u0018\u0000 \u00042\u00020\u0001:\u0001\u0004B\u0007¢\u0006\u0004\b\u0002\u0010\u0003¨\u0006\u0005"}, d2 = {"Lcom/miui/handwriting/common/ai/CreationAIRequest2;", "", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "()V", "Companion", "libHandWritingCommon_release"}, k = 1, mv = {2, 2, 0}, xi = 48)
public final class CreationAIRequest2 {
    private static final String AI_SID;
    public static final int CODE_CREATION_IGNORE = -2;
    public static final int CODE_NOT_ENOUGH_CREATE_TIME = 70002;

    /* JADX INFO: renamed from: Companion, reason: from kotlin metadata */
    public static final Companion INSTANCE = new Companion(null);
    public static final int RETRY_COUNT = 3;
    public static final int STATUS_PRODUCT_SENSITIVE_IMAGE = 10111;
    public static final int STATUS_SENSITIVE_PREVIEW = 10101;
    public static final int STATUS_SENSITIVE_PROMPT = 10110;
    public static final String TAG = "CreationRequest2";

    /* JADX INFO: compiled from: CreationAIRequest2.kt */
    @Metadata(d1 = {"\u00008\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0002\b\u0004\n\u0002\u0010\b\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0005\b\u0086\u0003\u0018\u00002\u00020\u0001B\t\b\u0002¢\u0006\u0004\b\u0002\u0010\u0003J\u0006\u0010\u0010\u001a\u00020\u0011Jh\u0010\u0012\u001a\u00020\u00052\u0006\u0010\u0013\u001a\u00020\u00052&\u0010\u0014\u001a\"\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u0005\u0018\u00010\u0015j\u0010\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u0005\u0018\u0001`\u00162&\u0010\u0017\u001a\"\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u0005\u0018\u00010\u0015j\u0010\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u0005\u0018\u0001`\u00162\b\b\u0002\u0010\u0018\u001a\u00020\u0019J\u0092\u0001\u0010\u001a\u001a\u00020\u00052\u0006\u0010\u0013\u001a\u00020\u00052&\u0010\u0014\u001a\"\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u0005\u0018\u00010\u0015j\u0010\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u0005\u0018\u0001`\u00162&\u0010\u0017\u001a\"\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u0005\u0018\u00010\u0015j\u0010\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u0005\u0018\u0001`\u00162(\b\u0002\u0010\u001b\u001a\"\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u0005\u0018\u00010\u0015j\u0010\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u0005\u0018\u0001`\u00162\b\b\u0002\u0010\u0018\u001a\u00020\u0019Jf\u0010\u001c\u001a\u00020\u00052\u0006\u0010\u0013\u001a\u00020\u00052\"\u0010\u0017\u001a\u001e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u00050\u0015j\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u0005`\u00162(\b\u0002\u0010\u001d\u001a\"\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u0005\u0018\u00010\u0015j\u0010\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u0005\u0018\u0001`\u00162\b\b\u0002\u0010\u0018\u001a\u00020\u0019R\u000e\u0010\u0004\u001a\u00020\u0005X\u0086T¢\u0006\u0002\n\u0000R\u0011\u0010\u0006\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\bR\u000e\u0010\t\u001a\u00020\nX\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\u000b\u001a\u00020\nX\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\f\u001a\u00020\nX\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\r\u001a\u00020\nX\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\u000e\u001a\u00020\nX\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\u000f\u001a\u00020\nX\u0086T¢\u0006\u0002\n\u0000¨\u0006\u001e"}, d2 = {"Lcom/miui/handwriting/common/ai/CreationAIRequest2$Companion;", "", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "()V", "TAG", "", "AI_SID", "getAI_SID", "()Ljava/lang/String;", "RETRY_COUNT", "", "STATUS_SENSITIVE_PROMPT", "STATUS_SENSITIVE_PREVIEW", "STATUS_PRODUCT_SENSITIVE_IMAGE", "CODE_NOT_ENOUGH_CREATE_TIME", "CODE_CREATION_IGNORE", "getAuthToken", "Landroid/os/Bundle;", CallMethod.METHOD_GET, "url", "headers", "Ljava/util/HashMap;", "Lkotlin/collections/HashMap;", "params", "needAuth", "", "post", "multiPartMedias", "upload", "medias", "libHandWritingCommon_release"}, k = 1, mv = {2, 2, 0}, xi = 48)
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }

        public final String getAI_SID() {
            return CreationAIRequest2.AI_SID;
        }

        public final Bundle getAuthToken() {
            Bundle result = AccountManager.get(HandWritingViewApplication.INSTANCE.getApplication()).getAuthToken(ExtraAccountManager.getXiaomiAccount(HandWritingViewApplication.INSTANCE.getApplication()), getAI_SID(), (Bundle) null, (Activity) null, (AccountManagerCallback<Bundle>) null, (Handler) null).getResult();
            Intrinsics.checkNotNullExpressionValue(result, "getResult(...)");
            return result;
        }

        public static /* synthetic */ String get$default(Companion companion, String str, HashMap map, HashMap map2, boolean z, int i, Object obj) {
            if ((i & 8) != 0) {
                z = true;
            }
            return companion.get(str, map, map2, z);
        }

        public final String get(String url, HashMap<String, String> headers, HashMap<String, String> params, boolean needAuth) throws CloudServerException {
            ExtendedAuthToken extendedAuthToken;
            Intrinsics.checkNotNullParameter(url, "url");
            int i = 0;
            do {
                String authToken = needAuth ? MiAccountManager.INSTANCE.getAuthToken(HandWritingViewApplication.INSTANCE.getApplication(), getAI_SID()) : null;
                if (authToken == null && needAuth) {
                    String json = new Gson().toJson(new Result("", false, -2, "", 0L, new VersionData("")));
                    Intrinsics.checkNotNullExpressionValue(json, "toJson(...)");
                    return json;
                }
                if (needAuth) {
                    ExtendedAuthToken.Companion companion = ExtendedAuthToken.INSTANCE;
                    Intrinsics.checkNotNull(authToken);
                    extendedAuthToken = companion.parse(authToken);
                } else {
                    extendedAuthToken = null;
                }
                try {
                    try {
                        try {
                            com.miui.aiengine.common.Result resultExecute = new HttpRequest.Builder().setUrl(url).setHttpMethod(HttpMethod.GET.INSTANCE).setHeader(headers, null).setBodyEntity(new HttpBodyEntity(null, params, 1, null)).setEncrypt(extendedAuthToken).build().execute();
                            Intrinsics.checkNotNull(resultExecute);
                            if (resultExecute instanceof SUCCESS) {
                                return ((SUCCESS) resultExecute).getData();
                            }
                            if (resultExecute instanceof Error) {
                                return "";
                            }
                            throw new NoWhenBranchMatchedException();
                        } catch (CloudServerException e) {
                            e = e;
                            Log.e(CreationAIRequest2.TAG, "request fail:" + e);
                            if (!StringsKt.contains$default((CharSequence) e.toString(), (CharSequence) "401", false, 2, (Object) null)) {
                            }
                            throw e;
                        }
                    } catch (CloudServerException e2) {
                        e = e2;
                        Log.e(CreationAIRequest2.TAG, "request fail:" + e);
                        if (!StringsKt.contains$default((CharSequence) e.toString(), (CharSequence) "401", false, 2, (Object) null) && i < 3) {
                            Log.w(CreationAIRequest2.TAG, "request failed, invalid and retry");
                            MiAccountManager miAccountManager = MiAccountManager.INSTANCE;
                            Application application = HandWritingViewApplication.INSTANCE.getApplication();
                            Intrinsics.checkNotNull(authToken);
                            miAccountManager.invalidAuthToken(application, authToken);
                            i++;
                        } else {
                            throw e;
                        }
                    }
                } catch (CloudServerException e3) {
                    e = e3;
                }
            } while (i <= 3);
            return "";
        }

        /* JADX WARN: Multi-variable type inference failed */
        public static /* synthetic */ String post$default(Companion companion, String str, HashMap map, HashMap map2, HashMap map3, boolean z, int i, Object obj) {
            if ((i & 8) != 0) {
                map3 = null;
            }
            HashMap map4 = map3;
            if ((i & 16) != 0) {
                z = true;
            }
            return companion.post(str, map, map2, map4, z);
        }

        /* JADX WARN: Code duplicated, block: B:41:0x00c1  */
        /* JADX WARN: Code duplicated, block: B:55:0x00e0 A[ADDED_TO_REGION, REMOVE, SYNTHETIC] */
        public final String post(String url, HashMap<String, String> headers, HashMap<String, String> params, HashMap<String, String> multiPartMedias, boolean needAuth) throws CloudServerException {
            ExtendedAuthToken extendedAuthToken;
            Intrinsics.checkNotNullParameter(url, "url");
            int i = 0;
            do {
                String authToken = needAuth ? MiAccountManager.INSTANCE.getAuthToken(HandWritingViewApplication.INSTANCE.getApplication(), getAI_SID()) : null;
                if (authToken == null && needAuth) {
                    String json = new Gson().toJson(new Result("", false, -2, "", 0L, new VersionData("")));
                    Intrinsics.checkNotNullExpressionValue(json, "toJson(...)");
                    return json;
                }
                if (needAuth) {
                    ExtendedAuthToken.Companion companion = ExtendedAuthToken.INSTANCE;
                    Intrinsics.checkNotNull(authToken);
                    extendedAuthToken = companion.parse(authToken);
                } else {
                    extendedAuthToken = null;
                }
                try {
                    try {
                        try {
                            try {
                                com.miui.aiengine.common.Result resultExecute = new HttpRequest.Builder().setUrl(url).setHttpMethod(HttpMethod.POST.INSTANCE).setHeader(headers, null).setBodyEntity(new HttpBodyEntity(null, params, 1, null)).setMultiPartMedias(multiPartMedias).setEncrypt(extendedAuthToken).build().execute();
                                Intrinsics.checkNotNull(resultExecute);
                                if (resultExecute instanceof SUCCESS) {
                                    return ((SUCCESS) resultExecute).getData();
                                }
                                if (resultExecute instanceof Error) {
                                    return "";
                                }
                                throw new NoWhenBranchMatchedException();
                            } catch (CloudServerException e) {
                                e = e;
                                if (!StringsKt.contains$default((CharSequence) e.toString(), (CharSequence) "401", false, 2, (Object) null)) {
                                }
                                throw e;
                            }
                        } catch (CloudServerException e2) {
                            e = e2;
                            if (!StringsKt.contains$default((CharSequence) e.toString(), (CharSequence) "401", false, 2, (Object) null) && i < 3) {
                                Log.w(CreationAIRequest2.TAG, "request failed, invalid and retry");
                                MiAccountManager miAccountManager = MiAccountManager.INSTANCE;
                                Application application = HandWritingViewApplication.INSTANCE.getApplication();
                                Intrinsics.checkNotNull(authToken);
                                miAccountManager.invalidAuthToken(application, authToken);
                                i++;
                            } else {
                                throw e;
                            }
                        }
                    } catch (CloudServerException e3) {
                        e = e3;
                        if (!StringsKt.contains$default((CharSequence) e.toString(), (CharSequence) "401", false, 2, (Object) null)) {
                        }
                        throw e;
                    }
                } catch (CloudServerException e4) {
                    e = e4;
                }
            } while (i <= 3);
            return "";
        }

        /* JADX WARN: Multi-variable type inference failed */
        public static /* synthetic */ String upload$default(Companion companion, String str, HashMap map, HashMap map2, boolean z, int i, Object obj) {
            if ((i & 4) != 0) {
                map2 = null;
            }
            if ((i & 8) != 0) {
                z = true;
            }
            return companion.upload(str, map, map2, z);
        }

        public final String upload(String url, HashMap<String, String> params, HashMap<String, String> medias, boolean needAuth) throws Exception {
            Intrinsics.checkNotNullParameter(url, "url");
            Intrinsics.checkNotNullParameter(params, "params");
            String data = "";
            int i = 0;
            do {
                String authToken = needAuth ? MiAccountManager.INSTANCE.getAuthToken(HandWritingViewApplication.INSTANCE.getApplication(), getAI_SID()) : null;
                if (needAuth) {
                    ExtendedAuthToken.Companion companion = ExtendedAuthToken.INSTANCE;
                    Intrinsics.checkNotNull(authToken);
                    companion.parse(authToken);
                }
                try {
                    try {
                        HashMap<String, String> map = new HashMap<>();
                        map.put("serviceToken", authToken == null ? "" : authToken);
                        map.put("accept-language", Locale.getDefault().toString());
                        map.put("user-agent", OsUtils.INSTANCE.getUA());
                        try {
                            com.miui.aiengine.common.Result resultExecute = new HttpRequest.Builder().setUrl(url).setHttpMethod(HttpMethod.POST.INSTANCE).setHeader(map, null).setBodyEntity(new HttpBodyEntity(null, params, 1, null)).setMultiPartMedias(medias).setEncrypt(null).build().execute();
                            Log.d(CreationAIRequest2.TAG, "upload codess:" + resultExecute);
                            if (resultExecute instanceof SUCCESS) {
                                data = ((SUCCESS) resultExecute).getData();
                            } else if (!(resultExecute instanceof Error) && resultExecute != null) {
                                throw new NoWhenBranchMatchedException();
                            }
                            Log.d(CreationAIRequest2.TAG, "upload code:" + data);
                            return data;
                        } catch (CloudServerException e) {
                            e = e;
                            if (StringsKt.contains$default((CharSequence) e.toString(), (CharSequence) "401", false, 2, (Object) null) && i < 3) {
                                Log.e(CreationAIRequest2.TAG, "upload retry: " + i);
                                MiAccountManager miAccountManager = MiAccountManager.INSTANCE;
                                Application application = HandWritingViewApplication.INSTANCE.getApplication();
                                Intrinsics.checkNotNull(authToken);
                                miAccountManager.invalidAuthToken(application, authToken);
                                i++;
                            } else {
                                e.printStackTrace();
                                throw e;
                            }
                        } catch (SocketTimeoutException e2) {
                            e = e2;
                            if (i >= 3) {
                                e.printStackTrace();
                                throw e;
                            }
                            i++;
                        }
                    } catch (CloudServerException e3) {
                        e = e3;
                    } catch (SocketTimeoutException e4) {
                        e = e4;
                    }
                } catch (Exception e5) {
                    e5.printStackTrace();
                    Log.d(CreationAIRequest2.TAG, "e:" + e5);
                    throw e5;
                }
            } while (i <= 3);
            return data;
        }
    }

    static {
        AI_SID = RomUtils.isInternationalBuild() ? "miuisec_avatar_intl" : "miuisec_avatar";
    }
}
