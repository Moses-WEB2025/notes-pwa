package com.miui.aiengine.common.cloud;

import com.market.sdk.reflect.ReflectUtilsForMiui;
import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* JADX INFO: compiled from: ServerException.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000$\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\u0003\n\u0002\b\u0004\n\u0002\u0010\b\n\u0002\b\u0002\b\u0016\u0018\u0000 \f2\u00060\u0001j\u0002`\u0002:\u0001\fB\u001d\b\u0016\u0012\b\u0010\u0003\u001a\u0004\u0018\u00010\u0004\u0012\b\u0010\u0005\u001a\u0004\u0018\u00010\u0006¢\u0006\u0004\b\u0007\u0010\bB\u0013\b\u0016\u0012\b\u0010\u0003\u001a\u0004\u0018\u00010\u0004¢\u0006\u0004\b\u0007\u0010\tR\u000e\u0010\n\u001a\u00020\u000bX\u0082\u000e¢\u0006\u0002\n\u0000¨\u0006\r"}, d2 = {"Lcom/miui/aiengine/common/cloud/ServerException;", "Ljava/lang/Exception;", "Lkotlin/Exception;", "detailMessage", "", "throwable", "", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "(Ljava/lang/String;Ljava/lang/Throwable;)V", "(Ljava/lang/String;)V", "statusCode", "", "Companion", "net_release"}, k = 1, mv = {2, 2, 0}, xi = 48)
public class ServerException extends Exception {

    /* JADX INFO: renamed from: Companion, reason: from kotlin metadata */
    public static final Companion INSTANCE = new Companion(null);
    private int statusCode;

    public ServerException(String str, Throwable th) {
        super(str, th);
    }

    public ServerException(String str) {
        super(str);
    }

    /* JADX INFO: compiled from: ServerException.kt */
    @Metadata(d1 = {"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\b\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\t\b\u0002¢\u0006\u0004\b\u0002\u0010\u0003J\u000e\u0010\u0004\u001a\u00020\u00052\u0006\u0010\u0006\u001a\u00020\u0007¨\u0006\b"}, d2 = {"Lcom/miui/aiengine/common/cloud/ServerException$Companion;", "", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "()V", "isOpenServerException", "", "statusCode", "", "net_release"}, k = 1, mv = {2, 2, 0}, xi = 48)
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }

        public final boolean isOpenServerException(int statusCode) {
            return statusCode == 400 || statusCode == 401 || statusCode == 403 || statusCode == 406 || statusCode / 100 == 5;
        }
    }
}
