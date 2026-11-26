package com.miui.aiengine.common.interceptor;

import com.miui.aiengine.common.Result;
import com.miui.aiengine.common.request.HttpRequest;
import com.xiaomi.accountsdk.account.stat.StatConstants;
import kotlin.Metadata;

/* JADX INFO: compiled from: Interceptor.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000 \n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\bf\u0018\u00002\u00020\u0001:\u0001\nJ\u0012\u0010\u0006\u001a\u0004\u0018\u00010\u00072\u0006\u0010\b\u001a\u00020\tH&R\u0012\u0010\u0002\u001a\u00020\u0003X¦\u0004¢\u0006\u0006\u001a\u0004\b\u0004\u0010\u0005¨\u0006\u000bÀ\u0006\u0003"}, d2 = {"Lcom/miui/aiengine/common/interceptor/Interceptor;", "", "name", "", "getName", "()Ljava/lang/String;", "intercept", "Lcom/miui/aiengine/common/Result;", "chain", "Lcom/miui/aiengine/common/interceptor/Interceptor$Chain;", "Chain", "net_release"}, k = 1, mv = {2, 2, 0}, xi = 48)
public interface Interceptor {

    /* JADX INFO: compiled from: Interceptor.kt */
    @Metadata(d1 = {"\u0000\u0016\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\bf\u0018\u00002\u00020\u0001J\b\u0010\u0002\u001a\u00020\u0003H&J\u0012\u0010\u0004\u001a\u0004\u0018\u00010\u00052\u0006\u0010\u0002\u001a\u00020\u0003H&¨\u0006\u0006À\u0006\u0003"}, d2 = {"Lcom/miui/aiengine/common/interceptor/Interceptor$Chain;", "", StatConstants.Event.REQUEST, "Lcom/miui/aiengine/common/request/HttpRequest;", "proceed", "Lcom/miui/aiengine/common/Result;", "net_release"}, k = 1, mv = {2, 2, 0}, xi = 48)
    public interface Chain {
        Result proceed(HttpRequest request) throws IllegalStateException;

        HttpRequest request();
    }

    String getName();

    Result intercept(Chain chain) throws IllegalStateException;
}
