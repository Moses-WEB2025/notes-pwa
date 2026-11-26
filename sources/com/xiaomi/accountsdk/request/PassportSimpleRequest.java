package com.xiaomi.accountsdk.request;

import java.io.IOException;

/* JADX INFO: loaded from: classes4.dex */
public abstract class PassportSimpleRequest extends PassportRequest {
    public static final String HTTP_METHOD_GET = "GET";
    public static final String HTTP_METHOD_POST = "POST";
    protected final PassportRequestArguments arguments;

    public abstract String getMethod();

    protected PassportSimpleRequest(PassportRequestArguments passportRequestArguments) {
        if (passportRequestArguments == null) {
            throw new IllegalArgumentException("arguments can't be null");
        }
        this.arguments = passportRequestArguments;
    }

    public PassportSimpleRequest copy() {
        PassportRequestArguments passportRequestArgumentsCopy = this.arguments.copy();
        if (this instanceof GetAsString) {
            return new GetAsString(passportRequestArgumentsCopy);
        }
        if (this instanceof PostAsString) {
            return new PostAsString(passportRequestArgumentsCopy);
        }
        throw new IllegalStateException("");
    }

    public boolean isHTTPS() {
        return this.arguments.url != null && this.arguments.url.toLowerCase().startsWith("https");
    }

    public static class GetAsString extends PassportSimpleRequest {
        public GetAsString(PassportRequestArguments passportRequestArguments) {
            super(passportRequestArguments);
        }

        @Override // com.xiaomi.accountsdk.request.PassportSimpleRequest
        public String getMethod() {
            return PassportSimpleRequest.HTTP_METHOD_GET;
        }

        @Override // com.xiaomi.accountsdk.request.PassportRequest
        public SimpleRequest.StringContent execute() throws PassportRequestException, IOException {
            try {
                return SimpleRequestForAccount.getAsString(this.arguments.url, this.arguments.params, this.arguments.headers, this.arguments.cookies, this.arguments.readBody, this.arguments.timeoutMillis);
            } catch (AccessDeniedException e) {
                throw new PassportRequestException(e);
            } catch (AuthenticationFailureException e2) {
                throw new PassportRequestException(e2);
            }
        }
    }

    public static class PostAsString extends PassportSimpleRequest {
        public PostAsString(PassportRequestArguments passportRequestArguments) {
            super(passportRequestArguments);
        }

        @Override // com.xiaomi.accountsdk.request.PassportSimpleRequest
        public String getMethod() {
            return PassportSimpleRequest.HTTP_METHOD_POST;
        }

        @Override // com.xiaomi.accountsdk.request.PassportRequest
        public SimpleRequest.StringContent execute() throws PassportRequestException, IOException {
            try {
                return SimpleRequestForAccount.postAsString(this.arguments.url, this.arguments.params, this.arguments.cookies, this.arguments.headers, this.arguments.urlParams, this.arguments.readBody, this.arguments.timeoutMillis);
            } catch (AccessDeniedException e) {
                throw new PassportRequestException(e);
            } catch (AuthenticationFailureException e2) {
                throw new PassportRequestException(e2);
            }
        }
    }
}
