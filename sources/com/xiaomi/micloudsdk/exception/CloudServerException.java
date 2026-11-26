package com.xiaomi.micloudsdk.exception;

import android.text.TextUtils;
import android.util.Log;
import com.market.sdk.DesktopRecommendInfo;
import com.xiaomi.opensdk.exception.ServerException;
import java.io.IOException;
import java.net.HttpURLConnection;
import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.apache.http.impl.cookie.DateParseException;
import org.apache.http.impl.cookie.DateUtils;

/* JADX INFO: loaded from: classes4.dex */
public class CloudServerException extends ServerException {
    public static final int CODE_ACTIVATED_FAIL = 1001;
    public static final int CODE_PERMISSION_LIMIT = 1002;
    public static final int CODE_SECURE_SPACE_LIMIT = 1003;
    public static final int CODE_SYNC_UNKNOWN = -1;
    public static final String COLUMN_AUTHORITY = "authority";
    public static final String COLUMN_ERROR_CODE = "error_code";
    public static final String COLUMN_TIME = "time";
    public static final int DEFAULT_RETRY_TIME_MILLIS = 300000;
    public static final int DEFAULT_STATUS_CODE = -10001;
    public static final int PRIVACY_ERROR_CODE = 52003;
    private static final String RETRY_AFTER = "Retry-After";
    private static final String TAG = "CloudServerException";
    public int code;
    public int retryTime;
    public long serverDate;
    public int statusCode;

    public CloudServerException(int i, Throwable th) {
        super("status: " + i, th);
        this.retryTime = 300000;
        this.statusCode = i;
    }

    public CloudServerException(int i) {
        super("status: " + i);
        this.retryTime = 300000;
        this.statusCode = i;
    }

    public CloudServerException(int i, String str) {
        super("status: " + i + " message: " + str);
        this.retryTime = 300000;
        this.statusCode = i;
    }

    public CloudServerException(int i, String str, Throwable th) {
        super("status: " + i + " message: " + str, th);
        this.retryTime = 300000;
        this.statusCode = i;
    }

    public CloudServerException(int i, int i2) {
        super("status: " + i);
        this.retryTime = 300000;
        this.statusCode = i;
        this.code = i2;
    }

    public CloudServerException(int i, int i2, int i3) {
        super("status: " + i);
        this.statusCode = i;
        this.code = i2;
        this.retryTime = i3 * 1000;
    }

    public CloudServerException(int i, HttpURLConnection httpURLConnection) {
        super("status: " + i);
        this.retryTime = 300000;
        this.statusCode = i;
        try {
            if (httpURLConnection.getResponseCode() != 503 || TextUtils.isEmpty(httpURLConnection.getHeaderField("Retry-After"))) {
                return;
            }
            this.retryTime = Integer.valueOf(httpURLConnection.getHeaderField("Retry-After")).intValue() * 1000;
        } catch (IOException unused) {
            Log.d(TAG, "Can't find retry time in 503 HttpURLConnection response");
        } catch (NumberFormatException unused2) {
            Log.d(TAG, "Can't find retry time in 503 HttpURLConnection response");
        }
    }

    public CloudServerException(int i, HttpResponse httpResponse) {
        String value;
        Header firstHeader;
        super("status: " + i);
        this.retryTime = 300000;
        this.statusCode = i;
        if (httpResponse != null) {
            try {
                if (httpResponse.getStatusLine() != null && httpResponse.getStatusLine().getStatusCode() == 503 && (firstHeader = httpResponse.getFirstHeader("Retry-After")) != null && !TextUtils.isEmpty(firstHeader.getValue())) {
                    this.retryTime = Integer.valueOf(firstHeader.getValue()).intValue() * 1000;
                }
                Header firstHeader2 = httpResponse.getFirstHeader("Date");
                if (firstHeader2 == null || (value = firstHeader2.getValue()) == null) {
                    return;
                }
                try {
                    this.serverDate = DateUtils.parseDate(value).getTime();
                } catch (DateParseException e) {
                    Log.w(TAG, "Error parsing server date", e);
                }
            } catch (NumberFormatException unused) {
                Log.d(TAG, "Can't find retry time in 503 HttpURLConnection response");
            }
        }
    }

    @Override // com.xiaomi.opensdk.exception.ServerException
    public int getStatusCode() {
        return this.statusCode;
    }

    public int getCode() {
        return this.code;
    }

    public int getRetryTime() {
        return this.retryTime;
    }

    public static boolean isMiCloudServerException(int i) {
        return i == 400 || i == 401 || i == 403 || i == 406 || i / 100 == 5;
    }

    public boolean is5xxServerException() {
        return this.statusCode / 100 == 5;
    }

    public long get5xxServerExceptionRetryTime() {
        int i;
        return (this.statusCode != 503 || (i = this.retryTime) <= 0) ? DesktopRecommendInfo.DEFAULT_CACHE_TIME : i;
    }

    @Override // java.lang.Throwable
    public String getMessage() {
        StringBuilder sb = new StringBuilder("CloudServerException{ msg=");
        sb.append(super.getMessage());
        if (this.code != 0) {
            sb.append(", code=").append(this.code);
        }
        if (this.serverDate != 0) {
            sb.append(", serverDate=").append(this.serverDate);
        }
        Throwable cause = super.getCause();
        if (cause != null) {
            sb.append(", cause=").append(cause);
        }
        sb.append(" }");
        return sb.toString();
    }
}
