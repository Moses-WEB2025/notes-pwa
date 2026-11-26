package com.miui.aiengine.common.cloud;

import android.text.TextUtils;
import androidx.collection.SieveCacheKt;
import com.market.sdk.reflect.ReflectUtilsForMiui;
import com.miui.aiengine.common.NetConfig;
import com.miui.aiengine.common.utils.LogUtils;
import java.io.IOException;
import java.net.HttpURLConnection;
import kotlin.Deprecated;
import kotlin.Metadata;
import kotlin.jvm.JvmStatic;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: CloudServerException.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u00006\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u0003\n\u0002\b\u0004\n\u0002\u0010\u000e\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\t\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\t\n\u0002\b\u0002\u0018\u0000 \u001e2\u00020\u0001:\u0001\u001eB\u001b\b\u0016\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\b\u0010\u0004\u001a\u0004\u0018\u00010\u0005¢\u0006\u0004\b\u0006\u0010\u0007B\u0011\b\u0016\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0004\b\u0006\u0010\bB\u0019\b\u0016\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\t\u001a\u00020\n¢\u0006\u0004\b\u0006\u0010\u000bB\u0019\b\u0016\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\f\u001a\u00020\u0003¢\u0006\u0004\b\u0006\u0010\rB!\b\u0016\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\f\u001a\u00020\u0003\u0012\u0006\u0010\u000e\u001a\u00020\u0003¢\u0006\u0004\b\u0006\u0010\u000fB\u0019\b\u0016\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0010\u001a\u00020\u0011¢\u0006\u0004\b\u0006\u0010\u0012J\u0006\u0010\u001a\u001a\u00020\u001bJ\u0006\u0010\u001c\u001a\u00020\u001dR\u0016\u0010\u0013\u001a\u00020\nX\u0086D¢\u0006\n\n\u0002\b\u0016\u001a\u0004\b\u0014\u0010\u0015R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u000e¢\u0006\u0002\n\u0000R\u001a\u0010\f\u001a\u00020\u0003X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0017\u0010\u0018\"\u0004\b\u0019\u0010\bR\u000e\u0010\u000e\u001a\u00020\u0003X\u0082\u000e¢\u0006\u0002\n\u0000¨\u0006\u001f"}, d2 = {"Lcom/miui/aiengine/common/cloud/CloudServerException;", "Lcom/miui/aiengine/common/cloud/ServerException;", "statusCode", "", "cause", "", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "(ILjava/lang/Throwable;)V", "(I)V", "message", "", "(ILjava/lang/String;)V", "code", "(II)V", "retryTime", "(III)V", "conn", "Ljava/net/HttpURLConnection;", "(ILjava/net/HttpURLConnection;)V", "TAG", "getTAG", "()Ljava/lang/String;", "TAG$1", "getCode", "()I", "setCode", "is5xxServerException", "", "get5xxServerExceptionRetryTime", "", "Companion", "net_release"}, k = 1, mv = {2, 2, 0}, xi = 48)
public final class CloudServerException extends ServerException {
    public static final String COLUMN_AUTHORITY = "authority";
    public static final String COLUMN_ERROR_CODE = "error_code";
    public static final String COLUMN_TIME = "time";
    public static final int PRIVACY_ERROR_CODE = 52003;
    private static final String RETRY_AFTER = "Retry-After";
    private static final String TAG = "CloudServerException";

    /* JADX INFO: renamed from: TAG$1, reason: from kotlin metadata */
    private final String TAG;
    private int code;
    private int retryTime;
    private int statusCode;

    /* JADX INFO: renamed from: Companion, reason: from kotlin metadata */
    public static final Companion INSTANCE = new Companion(null);
    private static final int DEFAULT_STATUS_CODE = com.xiaomi.micloudsdk.exception.CloudServerException.DEFAULT_STATUS_CODE;
    private static final int CODE_SYNC_UNKNOWN = -1;
    private static final int CODE_ACTIVATED_FAIL = 1001;
    private static final int CODE_PERMISSION_LIMIT = 1002;
    private static final int CODE_SECURE_SPACE_LIMIT = 1003;

    @JvmStatic
    public static final boolean isCloudServerException(int i) {
        return INSTANCE.isCloudServerException(i);
    }

    public final String getTAG() {
        return this.TAG;
    }

    public final int getCode() {
        return this.code;
    }

    public final void setCode(int i) {
        this.code = i;
    }

    public CloudServerException(int i, Throwable th) {
        super("status: " + i, th);
        this.TAG = TAG;
        this.statusCode = i;
    }

    public CloudServerException(int i) {
        super("status: " + i);
        this.TAG = TAG;
        this.statusCode = i;
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public CloudServerException(int i, String message) {
        super("status: " + i + " message: " + message);
        Intrinsics.checkNotNullParameter(message, "message");
        this.TAG = TAG;
        this.statusCode = i;
    }

    public CloudServerException(int i, int i2) {
        super("status: " + i);
        this.TAG = TAG;
        this.statusCode = i;
        this.code = i2;
        this.retryTime = Integer.MAX_VALUE;
    }

    public CloudServerException(int i, int i2, int i3) {
        super("status: " + i);
        this.TAG = TAG;
        this.statusCode = i;
        this.code = i2;
        this.retryTime = i3 * 1000;
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public CloudServerException(int i, HttpURLConnection conn) {
        super("status: " + i);
        Intrinsics.checkNotNullParameter(conn, "conn");
        this.TAG = TAG;
        this.statusCode = i;
        try {
            if (conn.getResponseCode() != 503 || TextUtils.isEmpty(conn.getHeaderField("Retry-After"))) {
                return;
            }
            String headerField = conn.getHeaderField("Retry-After");
            Intrinsics.checkNotNullExpressionValue(headerField, "getHeaderField(...)");
            this.retryTime = Integer.parseInt(headerField) * 1000;
        } catch (IOException e) {
            LogUtils.INSTANCE.logError(NetConfig.TAG, this.TAG, "Can't find retry time in 503 HttpURLConnection response:" + e.getMessage());
        } catch (NumberFormatException e2) {
            LogUtils.INSTANCE.logError(NetConfig.TAG, this.TAG, "Can't find retry time in 503 HttpURLConnection response:" + e2.getMessage());
        }
    }

    public final boolean is5xxServerException() {
        return this.statusCode / 100 == 5;
    }

    public final long get5xxServerExceptionRetryTime() {
        int i;
        return (this.statusCode != 503 || (i = this.retryTime) <= 0) ? SieveCacheKt.NodeLinkMask : i;
    }

    /* JADX INFO: compiled from: CloudServerException.kt */
    @Metadata(d1 = {"\u0000\"\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\b\n\u0002\b\u0015\n\u0002\u0010\u000b\n\u0002\b\u0002\b\u0086\u0003\u0018\u00002\u00020\u0001B\t\b\u0002¢\u0006\u0004\b\u0002\u0010\u0003J\u0010\u0010\u001c\u001a\u00020\u001d2\u0006\u0010\u001e\u001a\u00020\u0007H\u0007R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082T¢\u0006\u0002\n\u0000R\u001c\u0010\u0006\u001a\u00020\u00078\u0006X\u0087D¢\u0006\u000e\n\u0000\u0012\u0004\b\b\u0010\u0003\u001a\u0004\b\t\u0010\nR\u000e\u0010\u000b\u001a\u00020\u0007X\u0086T¢\u0006\u0002\n\u0000R\u001c\u0010\f\u001a\u00020\u00078\u0006X\u0087D¢\u0006\u000e\n\u0000\u0012\u0004\b\r\u0010\u0003\u001a\u0004\b\u000e\u0010\nR\u001c\u0010\u000f\u001a\u00020\u00078\u0006X\u0087D¢\u0006\u000e\n\u0000\u0012\u0004\b\u0010\u0010\u0003\u001a\u0004\b\u0011\u0010\nR\u001c\u0010\u0012\u001a\u00020\u00078\u0006X\u0087D¢\u0006\u000e\n\u0000\u0012\u0004\b\u0013\u0010\u0003\u001a\u0004\b\u0014\u0010\nR\u001c\u0010\u0015\u001a\u00020\u00078\u0006X\u0087D¢\u0006\u000e\n\u0000\u0012\u0004\b\u0016\u0010\u0003\u001a\u0004\b\u0017\u0010\nR\u000e\u0010\u0018\u001a\u00020\u0005X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\u0019\u001a\u00020\u0005X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\u001a\u001a\u00020\u0005X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\u001b\u001a\u00020\u0005X\u0082T¢\u0006\u0002\n\u0000¨\u0006\u001f"}, d2 = {"Lcom/miui/aiengine/common/cloud/CloudServerException$Companion;", "", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "()V", "TAG", "", "DEFAULT_STATUS_CODE", "", "getDEFAULT_STATUS_CODE$annotations", "getDEFAULT_STATUS_CODE", "()I", "PRIVACY_ERROR_CODE", "CODE_SYNC_UNKNOWN", "getCODE_SYNC_UNKNOWN$annotations", "getCODE_SYNC_UNKNOWN", "CODE_ACTIVATED_FAIL", "getCODE_ACTIVATED_FAIL$annotations", "getCODE_ACTIVATED_FAIL", "CODE_PERMISSION_LIMIT", "getCODE_PERMISSION_LIMIT$annotations", "getCODE_PERMISSION_LIMIT", "CODE_SECURE_SPACE_LIMIT", "getCODE_SECURE_SPACE_LIMIT$annotations", "getCODE_SECURE_SPACE_LIMIT", "COLUMN_AUTHORITY", "COLUMN_TIME", "COLUMN_ERROR_CODE", "RETRY_AFTER", "isCloudServerException", "", "statusCode", "net_release"}, k = 1, mv = {2, 2, 0}, xi = 48)
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        @Deprecated(message = "")
        public static /* synthetic */ void getCODE_ACTIVATED_FAIL$annotations() {
        }

        @Deprecated(message = "")
        public static /* synthetic */ void getCODE_PERMISSION_LIMIT$annotations() {
        }

        @Deprecated(message = "")
        public static /* synthetic */ void getCODE_SECURE_SPACE_LIMIT$annotations() {
        }

        @Deprecated(message = "")
        public static /* synthetic */ void getCODE_SYNC_UNKNOWN$annotations() {
        }

        @Deprecated(message = "")
        public static /* synthetic */ void getDEFAULT_STATUS_CODE$annotations() {
        }

        private Companion() {
        }

        public final int getDEFAULT_STATUS_CODE() {
            return CloudServerException.DEFAULT_STATUS_CODE;
        }

        public final int getCODE_SYNC_UNKNOWN() {
            return CloudServerException.CODE_SYNC_UNKNOWN;
        }

        public final int getCODE_ACTIVATED_FAIL() {
            return CloudServerException.CODE_ACTIVATED_FAIL;
        }

        public final int getCODE_PERMISSION_LIMIT() {
            return CloudServerException.CODE_PERMISSION_LIMIT;
        }

        public final int getCODE_SECURE_SPACE_LIMIT() {
            return CloudServerException.CODE_SECURE_SPACE_LIMIT;
        }

        @JvmStatic
        public final boolean isCloudServerException(int statusCode) {
            return statusCode == 400 || statusCode == 401 || statusCode == 403 || statusCode == 406 || statusCode / 100 == 5;
        }
    }
}
