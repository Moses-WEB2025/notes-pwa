package cn.kuaipan.android.exception;

import java.io.Serializable;

/* JADX INFO: loaded from: classes.dex */
public interface IKscError extends ErrorCode, Serializable {
    int getErrorCode();

    String getSimpleMessage();
}
