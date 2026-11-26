package com.miui.aiengine.common.cloud;

import com.market.sdk.reflect.ReflectUtilsForMiui;
import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: DownloadFileFailedStatParam.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\b\n\u0002\b\b\u0018\u0000 \f2\u00020\u0001:\u0001\fB\u001f\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0003¢\u0006\u0004\b\u0007\u0010\bJ\b\u0010\u000b\u001a\u00020\u0003H\u0016R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\nR\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\r"}, d2 = {"Lcom/miui/aiengine/common/cloud/DownloadFileFailedStatParam;", "", "url", "", "httpStatusCode", "", "exceptionName", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "(Ljava/lang/String;ILjava/lang/String;)V", "getUrl", "()Ljava/lang/String;", "toString", "Companion", "net_release"}, k = 1, mv = {2, 2, 0}, xi = 48)
public final class DownloadFileFailedStatParam {

    /* JADX INFO: renamed from: Companion, reason: from kotlin metadata */
    public static final Companion INSTANCE = new Companion(null);
    private final String exceptionName;
    private final int httpStatusCode;
    private final String url;

    public DownloadFileFailedStatParam(String url, int i, String exceptionName) {
        Intrinsics.checkNotNullParameter(url, "url");
        Intrinsics.checkNotNullParameter(exceptionName, "exceptionName");
        this.url = url;
        this.httpStatusCode = i;
        this.exceptionName = exceptionName;
    }

    public final String getUrl() {
        return this.url;
    }

    public String toString() {
        return "DownloadFileFailedStatParam{url='" + this.url + "', httpStatusCode=" + this.httpStatusCode + ", exceptionName='" + this.exceptionName + "'}";
    }

    /* JADX INFO: compiled from: DownloadFileFailedStatParam.kt */
    @Metadata(d1 = {"\u0000 \n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\b\u0086\u0003\u0018\u00002\u00020\u0001B\t\b\u0002¢\u0006\u0004\b\u0002\u0010\u0003J\u001e\u0010\u0004\u001a\u00020\u00052\u0006\u0010\u0006\u001a\u00020\u00072\u0006\u0010\b\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u0007¨\u0006\u000b"}, d2 = {"Lcom/miui/aiengine/common/cloud/DownloadFileFailedStatParam$Companion;", "", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "()V", "createDownloadFileFailedStatParam", "Lcom/miui/aiengine/common/cloud/DownloadFileFailedStatParam;", "url", "", "httpStatusCode", "", "exceptionName", "net_release"}, k = 1, mv = {2, 2, 0}, xi = 48)
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }

        public final DownloadFileFailedStatParam createDownloadFileFailedStatParam(String url, int httpStatusCode, String exceptionName) {
            Intrinsics.checkNotNullParameter(url, "url");
            Intrinsics.checkNotNullParameter(exceptionName, "exceptionName");
            return new DownloadFileFailedStatParam(url, httpStatusCode, exceptionName);
        }
    }
}
