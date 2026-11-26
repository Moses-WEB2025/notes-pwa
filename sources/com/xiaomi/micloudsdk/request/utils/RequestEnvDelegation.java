package com.xiaomi.micloudsdk.request.utils;

import com.xiaomi.micloudsdk.data.IAuthToken;
import micloud.compat.independent.request.QueryAuthTokenException;

/* JADX INFO: loaded from: classes4.dex */
public class RequestEnvDelegation {
    public static Request.RequestEnv toRequest_RequestEnv(final RequestContext.RequestEnv requestEnv) {
        return new Request.RequestEnv() { // from class: com.xiaomi.micloudsdk.request.utils.RequestEnvDelegation.1
            @Override // com.xiaomi.micloudsdk.request.utils.Request.RequestEnv
            public IAuthToken queryAuthToken() throws QueryAuthTokenException {
                return requestEnv.queryAuthToken();
            }

            @Override // com.xiaomi.micloudsdk.request.utils.Request.RequestEnv
            public void invalidateAuthToken() {
                requestEnv.invalidateAuthToken();
            }

            @Override // com.xiaomi.micloudsdk.request.utils.Request.RequestEnv
            public String getAccountName() {
                return requestEnv.getAccountName();
            }

            @Override // com.xiaomi.micloudsdk.request.utils.Request.RequestEnv
            public String queryEncryptedAccountName() {
                return requestEnv.queryEncryptedAccountName();
            }

            @Override // com.xiaomi.micloudsdk.request.utils.Request.RequestEnv
            public String getUserAgent() {
                return requestEnv.getUserAgent();
            }

            @Override // com.xiaomi.micloudsdk.request.utils.Request.RequestEnv
            public boolean shouldUpdateHost() {
                return requestEnv.shouldUpdateHost();
            }

            @Override // com.xiaomi.micloudsdk.request.utils.Request.RequestEnv
            public int getMaxRetryCount() {
                return requestEnv.getMaxRetryCount();
            }

            @Override // com.xiaomi.micloudsdk.request.utils.Request.RequestEnv
            public long getAutoRetryInterval() {
                return requestEnv.getAutoRetryInterval();
            }
        };
    }

    public static RequestContext.RequestEnv toRequestContext_RequestEnv(final Request.RequestEnv requestEnv) {
        return new RequestContext.RequestEnv() { // from class: com.xiaomi.micloudsdk.request.utils.RequestEnvDelegation.2
            @Override // com.xiaomi.micloudsdk.request.utils.RequestContext.RequestEnv
            public IAuthToken queryAuthToken() throws QueryAuthTokenException {
                return requestEnv.queryAuthToken();
            }

            @Override // com.xiaomi.micloudsdk.request.utils.RequestContext.RequestEnv
            public void invalidateAuthToken() {
                requestEnv.invalidateAuthToken();
            }

            @Override // com.xiaomi.micloudsdk.request.utils.RequestContext.RequestEnv
            public String getAccountName() {
                return requestEnv.getAccountName();
            }

            @Override // com.xiaomi.micloudsdk.request.utils.RequestContext.RequestEnv
            public String queryEncryptedAccountName() {
                return requestEnv.queryEncryptedAccountName();
            }

            @Override // com.xiaomi.micloudsdk.request.utils.RequestContext.RequestEnv
            public String getUserAgent() {
                return requestEnv.getUserAgent();
            }

            @Override // com.xiaomi.micloudsdk.request.utils.RequestContext.RequestEnv
            public boolean shouldUpdateHost() {
                return requestEnv.shouldUpdateHost();
            }

            @Override // com.xiaomi.micloudsdk.request.utils.RequestContext.RequestEnv
            public int getMaxRetryCount() {
                return requestEnv.getMaxRetryCount();
            }

            @Override // com.xiaomi.micloudsdk.request.utils.RequestContext.RequestEnv
            public long getAutoRetryInterval() {
                return requestEnv.getAutoRetryInterval();
            }
        };
    }
}
