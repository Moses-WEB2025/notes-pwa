package androidx.appsearch.app;

import android.util.Log;
import androidx.appsearch.exceptions.AppSearchException;
import androidx.core.util.ObjectsCompat;
import androidx.core.util.Preconditions;
import java.io.IOException;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/* JADX INFO: loaded from: classes.dex */
public final class AppSearchResult<ValueType> {
    public static final int RESULT_ALREADY_EXISTS = 12;
    public static final int RESULT_DENIED = 9;
    public static final int RESULT_INTERNAL_ERROR = 2;
    public static final int RESULT_INVALID_ARGUMENT = 3;
    public static final int RESULT_INVALID_SCHEMA = 7;
    public static final int RESULT_IO_ERROR = 4;
    public static final int RESULT_NOT_FOUND = 6;
    public static final int RESULT_OK = 0;
    public static final int RESULT_OUT_OF_SPACE = 5;
    public static final int RESULT_RATE_LIMITED = 10;
    public static final int RESULT_SECURITY_ERROR = 8;
    public static final int RESULT_UNKNOWN_ERROR = 1;
    private static final String TAG = "AppSearchResult";
    private final String mErrorMessage;
    private final int mResultCode;
    private final ValueType mResultValue;

    @Retention(RetentionPolicy.SOURCE)
    public @interface ResultCode {
    }

    private AppSearchResult(int i, ValueType valuetype, String str) {
        this.mResultCode = i;
        this.mResultValue = valuetype;
        this.mErrorMessage = str;
    }

    public boolean isSuccess() {
        return getResultCode() == 0;
    }

    public int getResultCode() {
        return this.mResultCode;
    }

    public ValueType getResultValue() {
        if (!isSuccess()) {
            throw new IllegalStateException("AppSearchResult is a failure: " + this);
        }
        return this.mResultValue;
    }

    public String getErrorMessage() {
        return this.mErrorMessage;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof AppSearchResult)) {
            return false;
        }
        AppSearchResult appSearchResult = (AppSearchResult) obj;
        return this.mResultCode == appSearchResult.mResultCode && ObjectsCompat.equals(this.mResultValue, appSearchResult.mResultValue) && ObjectsCompat.equals(this.mErrorMessage, appSearchResult.mErrorMessage);
    }

    public int hashCode() {
        return ObjectsCompat.hash(Integer.valueOf(this.mResultCode), this.mResultValue, this.mErrorMessage);
    }

    public String toString() {
        if (isSuccess()) {
            return "[SUCCESS]: " + this.mResultValue;
        }
        return "[FAILURE(" + this.mResultCode + ")]: " + this.mErrorMessage;
    }

    public static <ValueType> AppSearchResult<ValueType> newSuccessfulResult(ValueType valuetype) {
        return new AppSearchResult<>(0, valuetype, null);
    }

    public static <ValueType> AppSearchResult<ValueType> newFailedResult(int i, String str) {
        return new AppSearchResult<>(i, null, str);
    }

    public static <ValueType> AppSearchResult<ValueType> newFailedResult(AppSearchResult<?> appSearchResult) {
        Preconditions.checkState(!appSearchResult.isSuccess(), "Cannot convert a success result to a failed result");
        return newFailedResult(appSearchResult.getResultCode(), appSearchResult.getErrorMessage());
    }

    public static <ValueType> AppSearchResult<ValueType> throwableToFailedResult(Throwable th) {
        int i;
        boolean z = th instanceof AppSearchException;
        if (!z || ((AppSearchException) th).getResultCode() != 6) {
            Log.w(TAG, "Converting throwable to failed result.", th);
        }
        if (z) {
            return ((AppSearchException) th).toAppSearchResult();
        }
        String simpleName = th.getClass().getSimpleName();
        if ((th instanceof IllegalStateException) || (th instanceof NullPointerException)) {
            i = 2;
        } else if (th instanceof IllegalArgumentException) {
            i = 3;
        } else if (th instanceof IOException) {
            i = 4;
        } else {
            i = th instanceof SecurityException ? 8 : 1;
        }
        return newFailedResult(i, simpleName + ": " + th.getMessage());
    }
}
