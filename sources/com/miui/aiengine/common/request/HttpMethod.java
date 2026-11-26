package com.miui.aiengine.common.request;

import com.market.sdk.reflect.ReflectUtilsForMiui;
import com.xiaomi.accountsdk.request.PassportSimpleRequest;
import com.xiaomi.mirror.RemoteDeviceInfo;
import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* JADX INFO: compiled from: HttpMethod.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000\u001c\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\b6\u0018\u00002\u00020\u0001:\u0002\b\tB\u0011\b\u0004\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0004\b\u0004\u0010\u0005R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0006\u0010\u0007\u0082\u0001\u0002\n\u000b¨\u0006\f"}, d2 = {"Lcom/miui/aiengine/common/request/HttpMethod;", "", "name", "", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "(Ljava/lang/String;)V", "getName", "()Ljava/lang/String;", PassportSimpleRequest.HTTP_METHOD_POST, PassportSimpleRequest.HTTP_METHOD_GET, "Lcom/miui/aiengine/common/request/HttpMethod$GET;", "Lcom/miui/aiengine/common/request/HttpMethod$POST;", "net_release"}, k = 1, mv = {2, 2, 0}, xi = 48)
public abstract class HttpMethod {
    private final String name;

    public /* synthetic */ HttpMethod(String str, DefaultConstructorMarker defaultConstructorMarker) {
        this(str);
    }

    private HttpMethod(String str) {
        this.name = str;
    }

    public final String getName() {
        return this.name;
    }

    /* JADX INFO: compiled from: HttpMethod.kt */
    @Metadata(d1 = {"\u0000$\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0000\bÆ\n\u0018\u00002\u00020\u0001B\t\b\u0002¢\u0006\u0004\b\u0002\u0010\u0003J\u0013\u0010\u0004\u001a\u00020\u00052\b\u0010\u0006\u001a\u0004\u0018\u00010\u0007HÖ\u0003J\t\u0010\b\u001a\u00020\tHÖ\u0001J\t\u0010\n\u001a\u00020\u000bHÖ\u0001¨\u0006\f"}, d2 = {"Lcom/miui/aiengine/common/request/HttpMethod$POST;", "Lcom/miui/aiengine/common/request/HttpMethod;", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "()V", "equals", "", RemoteDeviceInfo.MANUFACTURER_OTHER, "", "hashCode", "", "toString", "", "net_release"}, k = 1, mv = {2, 2, 0}, xi = 48)
    public static final /* data */ class POST extends HttpMethod {
        public static final POST INSTANCE = new POST();

        public boolean equals(Object other) {
            if (this == other) {
                return true;
            }
            if (!(other instanceof POST)) {
                return false;
            }
            return true;
        }

        public int hashCode() {
            return -628688394;
        }

        public String toString() {
            return PassportSimpleRequest.HTTP_METHOD_POST;
        }

        private POST() {
            super(PassportSimpleRequest.HTTP_METHOD_POST, null);
        }
    }

    /* JADX INFO: compiled from: HttpMethod.kt */
    @Metadata(d1 = {"\u0000$\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0000\bÆ\n\u0018\u00002\u00020\u0001B\t\b\u0002¢\u0006\u0004\b\u0002\u0010\u0003J\u0013\u0010\u0004\u001a\u00020\u00052\b\u0010\u0006\u001a\u0004\u0018\u00010\u0007HÖ\u0003J\t\u0010\b\u001a\u00020\tHÖ\u0001J\t\u0010\n\u001a\u00020\u000bHÖ\u0001¨\u0006\f"}, d2 = {"Lcom/miui/aiengine/common/request/HttpMethod$GET;", "Lcom/miui/aiengine/common/request/HttpMethod;", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "()V", "equals", "", RemoteDeviceInfo.MANUFACTURER_OTHER, "", "hashCode", "", "toString", "", "net_release"}, k = 1, mv = {2, 2, 0}, xi = 48)
    public static final /* data */ class GET extends HttpMethod {
        public static final GET INSTANCE = new GET();

        public boolean equals(Object other) {
            if (this == other) {
                return true;
            }
            if (!(other instanceof GET)) {
                return false;
            }
            return true;
        }

        public int hashCode() {
            return -574478560;
        }

        public String toString() {
            return PassportSimpleRequest.HTTP_METHOD_GET;
        }

        private GET() {
            super(PassportSimpleRequest.HTTP_METHOD_GET, null);
        }
    }
}
