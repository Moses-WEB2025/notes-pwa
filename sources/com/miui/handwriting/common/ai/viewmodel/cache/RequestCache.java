package com.miui.handwriting.common.ai.viewmodel.cache;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import ch.qos.logback.core.CoreConstants;
import com.google.gson.JsonSyntaxException;
import com.market.sdk.reflect.ReflectUtilsForMiui;
import com.miui.handwriting.common.ai.data.CreationImage;
import com.miui.handwriting.common.tools.GsonUtils;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;

/* JADX INFO: compiled from: RequestCache.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u00002\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0002\b\u0006\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\bÆ\u0002\u0018\u00002\u00020\u0001B\t\b\u0002¢\u0006\u0004\b\u0002\u0010\u0003J\u000e\u0010\t\u001a\u00020\u00052\u0006\u0010\n\u001a\u00020\u0005J\u001e\u0010\u000b\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\u000e2\u0006\u0010\u000f\u001a\u00020\u00052\u0006\u0010\u0010\u001a\u00020\u0011J\u0018\u0010\u0012\u001a\u0004\u0018\u00010\u00112\u0006\u0010\r\u001a\u00020\u000e2\u0006\u0010\u000f\u001a\u00020\u0005J\u0010\u0010\u0013\u001a\u00020\u00142\u0006\u0010\r\u001a\u00020\u000eH\u0002J\u000e\u0010\u0015\u001a\u00020\u00052\u0006\u0010\u0016\u001a\u00020\u0005R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082T¢\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0005X\u0082T¢\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\u0005X\u0082T¢\u0006\u0002\n\u0000R\u000e\u0010\b\u001a\u00020\u0005X\u0082T¢\u0006\u0002\n\u0000¨\u0006\u0017"}, d2 = {"Lcom/miui/handwriting/common/ai/viewmodel/cache/RequestCache;", "", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "()V", "CACHE_FILE_NAME", "", "TAG", "COMBINATION", "AI_TAG", "generateKey", "path", "setImageUrlRequestCache", "", CoreConstants.CONTEXT_SCOPE_VALUE, "Landroid/content/Context;", "spannedKey", "creationImage", "Lcom/miui/handwriting/common/ai/data/CreationImage;", "getImageUrlRequestCache", "getRequestSp", "Landroid/content/SharedPreferences;", "getAiImageKeyByPath", "imagePath", "libHandWritingCommon_release"}, k = 1, mv = {2, 2, 0}, xi = 48)
public final class RequestCache {
    private static final String AI_TAG = "Ai_Tag";
    private static final String CACHE_FILE_NAME = "creation_request_file";
    private static final String COMBINATION = "_";
    public static final RequestCache INSTANCE = new RequestCache();
    private static final String TAG = "RequestCache";

    private RequestCache() {
    }

    public final String generateKey(String path) {
        Intrinsics.checkNotNullParameter(path, "path");
        return "generateKey: " + getAiImageKeyByPath(path);
    }

    public final void setImageUrlRequestCache(Context context, String spannedKey, CreationImage creationImage) {
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(spannedKey, "spannedKey");
        Intrinsics.checkNotNullParameter(creationImage, "creationImage");
        Log.i(TAG, "setImageUrlRequestCache key:" + spannedKey + ",value:" + creationImage);
        getRequestSp(context).edit().putString(spannedKey, GsonUtils.getGsonInstance().toJson(creationImage)).apply();
    }

    public final CreationImage getImageUrlRequestCache(Context context, String spannedKey) {
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(spannedKey, "spannedKey");
        Log.i(TAG, "getImageUrlRequestCache key:" + spannedKey);
        try {
            return (CreationImage) GsonUtils.getGsonInstance().fromJson(getRequestSp(context).getString(spannedKey, ""), CreationImage.class);
        } catch (JsonSyntaxException e) {
            Log.e(TAG, "getImageUrlRequest fail:" + e);
            return null;
        }
    }

    private final SharedPreferences getRequestSp(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(CACHE_FILE_NAME, 0);
        Intrinsics.checkNotNullExpressionValue(sharedPreferences, "getSharedPreferences(...)");
        return sharedPreferences;
    }

    public final String getAiImageKeyByPath(String imagePath) {
        Exception exc;
        Intrinsics.checkNotNullParameter(imagePath, "imagePath");
        Log.i(TAG, "getAiImageKeyByPath imagePath:" + imagePath);
        String str = "";
        try {
            int length = String.valueOf(System.currentTimeMillis()).length();
            int iIndexOf$default = StringsKt.indexOf$default((CharSequence) imagePath, "Ai_Tag", 0, false, 6, (Object) null);
            String strSubstring = imagePath.substring(iIndexOf$default, length + iIndexOf$default + 6);
            Intrinsics.checkNotNullExpressionValue(strSubstring, "substring(...)");
            try {
                Log.i(TAG, "getAiImageKeyByPath key:" + strSubstring);
                return strSubstring;
            } catch (Exception e) {
                exc = e;
                str = strSubstring;
                Log.i(TAG, "getAiImageKeyByPath error:" + exc);
                return str;
            }
        } catch (Exception e2) {
            exc = e2;
        }
    }
}
