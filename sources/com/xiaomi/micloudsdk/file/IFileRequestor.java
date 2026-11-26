package com.xiaomi.micloudsdk.file;

import com.xiaomi.opensdk.exception.AuthenticationException;
import com.xiaomi.opensdk.exception.RetriableException;
import com.xiaomi.opensdk.exception.UnretriableException;
import com.xiaomi.opensdk.file.model.CommitParameter;
import com.xiaomi.opensdk.file.model.RequestUploadParameter;
import org.json.JSONObject;

/* JADX INFO: loaded from: classes4.dex */
public interface IFileRequestor<T> {
    JSONObject commitUpload(T t, CommitParameter commitParameter) throws AuthenticationException, RetriableException, UnretriableException;

    JSONObject requestDownload(T t) throws AuthenticationException, RetriableException, UnretriableException;

    JSONObject requestUpload(T t, RequestUploadParameter requestUploadParameter) throws AuthenticationException, RetriableException, UnretriableException;
}
