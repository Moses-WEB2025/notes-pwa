package com.xiaomi.miai;

import com.xiaomi.miai.utils.Requires;
import java.io.IOException;
import okhttp3.Response;

/* JADX INFO: loaded from: classes4.dex */
public class SDKException extends RuntimeException {
    public SDKException(String str, Throwable th) {
        super(str, th);
    }

    public static SDKException onErrorResponse(Response response) {
        return onErrorResponse(response, null);
    }

    public static SDKException onErrorResponse(Response response, Throwable th) {
        int iCode = response.code();
        try {
            return onErrorResponse(iCode, "headers=" + response.headers() + ", body=" + response.body().string(), th);
        } catch (IOException e) {
            return new HttpResponseError(iCode, "parse body failed.", e);
        }
    }

    public static SDKException onErrorResponse(int i, String str, Throwable th) {
        int httpStatus = getHttpStatus(i);
        Requires.isTrue(httpStatus < 200 || httpStatus >= 300, "HttpResponseError only happens if status code >= 400. it's " + httpStatus);
        if (httpStatus >= 400 && httpStatus < 500) {
            return new HttpClientError(i, "Got HttpClientError: " + str, th);
        }
        if (httpStatus >= 500 && httpStatus < 600) {
            return new HttpServerError(i, "Got HttpServerError: " + str, th);
        }
        return new HttpResponseError(i, "unknown http code.", th);
    }

    static int getHttpStatus(int i) {
        if (i < 1000) {
            return i;
        }
        while (i > 1000) {
            i /= 10;
        }
        return i;
    }

    public static class HttpResponseError extends SDKException {
        private int code;

        public HttpResponseError(int i, String str, Throwable th) {
            super(str, th);
            this.code = i;
        }

        public int getCode() {
            return this.code;
        }

        @Override // java.lang.Throwable
        public String toString() {
            return "HttpResponseError{code=" + this.code + ", message='" + getMessage() + "'}";
        }

        public int getHttpStatus() {
            return SDKException.getHttpStatus(this.code);
        }
    }

    public static class HttpClientError extends HttpResponseError {
        public HttpClientError(int i, String str, Throwable th) {
            super(i, str, th);
        }
    }

    public static class HttpServerError extends HttpResponseError {
        public HttpServerError(int i, String str, Throwable th) {
            super(i, str, th);
        }
    }

    public static class LocalHardwareError extends SDKException {
        public LocalHardwareError(String str, Throwable th) {
            super(str, th);
        }
    }
}
