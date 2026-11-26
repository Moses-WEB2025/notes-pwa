package com.miui.notes.ai.request;

import androidx.exifinterface.media.ExifInterface;
import com.market.sdk.reflect.ReflectUtilsForMiui;
import com.xiaomi.mirror.RemoteDeviceInfo;
import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: AIResult.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u00000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0000\n\u0002\b\n\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\b6\u0018\u0000*\u0006\b\u0000\u0010\u0001 \u00012\u00020\u0002:\b\u0005\u0006\u0007\b\t\n\u000b\fB\t\b\u0004¢\u0006\u0004\b\u0003\u0010\u0004\u0082\u0001\b\r\u000e\u000f\u0010\u0011\u0012\u0013\u0014¨\u0006\u0015"}, d2 = {"Lcom/miui/notes/ai/request/AIResult;", ExifInterface.GPS_DIRECTION_TRUE, "", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "()V", "Complete", "Loading", "Error", "IllegalError", "TempBannedError", "ForeverBannedError", "AuthTokenError", "Finish", "Lcom/miui/notes/ai/request/AIResult$AuthTokenError;", "Lcom/miui/notes/ai/request/AIResult$Complete;", "Lcom/miui/notes/ai/request/AIResult$Error;", "Lcom/miui/notes/ai/request/AIResult$Finish;", "Lcom/miui/notes/ai/request/AIResult$ForeverBannedError;", "Lcom/miui/notes/ai/request/AIResult$IllegalError;", "Lcom/miui/notes/ai/request/AIResult$Loading;", "Lcom/miui/notes/ai/request/AIResult$TempBannedError;", "app_PhoneGlobalRelease"}, k = 1, mv = {2, 2, 0}, xi = 48)
public abstract class AIResult<T> {
    public /* synthetic */ AIResult(DefaultConstructorMarker defaultConstructorMarker) {
        this();
    }

    /* JADX INFO: compiled from: AIResult.kt */
    @Metadata(d1 = {"\u0000&\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\n\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0000\b\u0086\b\u0018\u0000*\u0006\b\u0001\u0010\u0001 \u00012\b\u0012\u0004\u0012\u0002H\u00010\u0002B\u000f\u0012\u0006\u0010\u0003\u001a\u00028\u0001¢\u0006\u0004\b\u0004\u0010\u0005J\u000e\u0010\t\u001a\u00028\u0001HÆ\u0003¢\u0006\u0002\u0010\u0007J\u001e\u0010\n\u001a\b\u0012\u0004\u0012\u00028\u00010\u00002\b\b\u0002\u0010\u0003\u001a\u00028\u0001HÆ\u0001¢\u0006\u0002\u0010\u000bJ\u0013\u0010\f\u001a\u00020\r2\b\u0010\u000e\u001a\u0004\u0018\u00010\u000fHÖ\u0003J\t\u0010\u0010\u001a\u00020\u0011HÖ\u0001J\t\u0010\u0012\u001a\u00020\u0013HÖ\u0001R\u0013\u0010\u0003\u001a\u00028\u0001¢\u0006\n\n\u0002\u0010\b\u001a\u0004\b\u0006\u0010\u0007¨\u0006\u0014"}, d2 = {"Lcom/miui/notes/ai/request/AIResult$Complete;", ExifInterface.GPS_DIRECTION_TRUE, "Lcom/miui/notes/ai/request/AIResult;", "param", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "(Ljava/lang/Object;)V", "getParam", "()Ljava/lang/Object;", "Ljava/lang/Object;", "component1", "copy", "(Ljava/lang/Object;)Lcom/miui/notes/ai/request/AIResult$Complete;", "equals", "", RemoteDeviceInfo.MANUFACTURER_OTHER, "", "hashCode", "", "toString", "", "app_PhoneGlobalRelease"}, k = 1, mv = {2, 2, 0}, xi = 48)
    public static final /* data */ class Complete<T> extends AIResult<T> {
        private final T param;

        /* JADX WARN: Multi-variable type inference failed */
        public static /* synthetic */ Complete copy$default(Complete complete, Object obj, int i, Object obj2) {
            if ((i & 1) != 0) {
                obj = complete.param;
            }
            return complete.copy(obj);
        }

        public final T component1() {
            return this.param;
        }

        public final Complete<T> copy(T param) {
            return new Complete<>(param);
        }

        public boolean equals(Object other) {
            if (this == other) {
                return true;
            }
            return (other instanceof Complete) && Intrinsics.areEqual(this.param, ((Complete) other).param);
        }

        public int hashCode() {
            T t = this.param;
            if (t == null) {
                return 0;
            }
            return t.hashCode();
        }

        public String toString() {
            return "Complete(param=" + this.param + ")";
        }

        public Complete(T t) {
            super(null);
            this.param = t;
        }

        public final T getParam() {
            return this.param;
        }
    }

    private AIResult() {
    }

    /* JADX INFO: compiled from: AIResult.kt */
    @Metadata(d1 = {"\u0000(\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0001\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0000\bÆ\n\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u0001B\t\b\u0002¢\u0006\u0004\b\u0003\u0010\u0004J\u0013\u0010\u0005\u001a\u00020\u00062\b\u0010\u0007\u001a\u0004\u0018\u00010\bHÖ\u0003J\t\u0010\t\u001a\u00020\nHÖ\u0001J\t\u0010\u000b\u001a\u00020\fHÖ\u0001¨\u0006\r"}, d2 = {"Lcom/miui/notes/ai/request/AIResult$Loading;", "Lcom/miui/notes/ai/request/AIResult;", "", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "()V", "equals", "", RemoteDeviceInfo.MANUFACTURER_OTHER, "", "hashCode", "", "toString", "", "app_PhoneGlobalRelease"}, k = 1, mv = {2, 2, 0}, xi = 48)
    public static final /* data */ class Loading extends AIResult {
        public static final Loading INSTANCE = new Loading();

        public boolean equals(Object other) {
            if (this == other) {
                return true;
            }
            if (!(other instanceof Loading)) {
                return false;
            }
            return true;
        }

        public int hashCode() {
            return -187218630;
        }

        public String toString() {
            return "Loading";
        }

        private Loading() {
            super(null);
        }
    }

    /* JADX INFO: compiled from: AIResult.kt */
    @Metadata(d1 = {"\u0000(\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0001\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0000\bÆ\n\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u0001B\t\b\u0002¢\u0006\u0004\b\u0003\u0010\u0004J\u0013\u0010\u0005\u001a\u00020\u00062\b\u0010\u0007\u001a\u0004\u0018\u00010\bHÖ\u0003J\t\u0010\t\u001a\u00020\nHÖ\u0001J\t\u0010\u000b\u001a\u00020\fHÖ\u0001¨\u0006\r"}, d2 = {"Lcom/miui/notes/ai/request/AIResult$Error;", "Lcom/miui/notes/ai/request/AIResult;", "", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "()V", "equals", "", RemoteDeviceInfo.MANUFACTURER_OTHER, "", "hashCode", "", "toString", "", "app_PhoneGlobalRelease"}, k = 1, mv = {2, 2, 0}, xi = 48)
    public static final /* data */ class Error extends AIResult {
        public static final Error INSTANCE = new Error();

        public boolean equals(Object other) {
            if (this == other) {
                return true;
            }
            if (!(other instanceof Error)) {
                return false;
            }
            return true;
        }

        public int hashCode() {
            return -735044218;
        }

        public String toString() {
            return "Error";
        }

        private Error() {
            super(null);
        }
    }

    /* JADX INFO: compiled from: AIResult.kt */
    @Metadata(d1 = {"\u0000(\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0001\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0000\bÆ\n\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u0001B\t\b\u0002¢\u0006\u0004\b\u0003\u0010\u0004J\u0013\u0010\u0005\u001a\u00020\u00062\b\u0010\u0007\u001a\u0004\u0018\u00010\bHÖ\u0003J\t\u0010\t\u001a\u00020\nHÖ\u0001J\t\u0010\u000b\u001a\u00020\fHÖ\u0001¨\u0006\r"}, d2 = {"Lcom/miui/notes/ai/request/AIResult$IllegalError;", "Lcom/miui/notes/ai/request/AIResult;", "", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "()V", "equals", "", RemoteDeviceInfo.MANUFACTURER_OTHER, "", "hashCode", "", "toString", "", "app_PhoneGlobalRelease"}, k = 1, mv = {2, 2, 0}, xi = 48)
    public static final /* data */ class IllegalError extends AIResult {
        public static final IllegalError INSTANCE = new IllegalError();

        public boolean equals(Object other) {
            if (this == other) {
                return true;
            }
            if (!(other instanceof IllegalError)) {
                return false;
            }
            return true;
        }

        public int hashCode() {
            return 1705308916;
        }

        public String toString() {
            return "IllegalError";
        }

        private IllegalError() {
            super(null);
        }
    }

    /* JADX INFO: compiled from: AIResult.kt */
    @Metadata(d1 = {"\u0000(\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0001\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0000\bÆ\n\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u0001B\t\b\u0002¢\u0006\u0004\b\u0003\u0010\u0004J\u0013\u0010\u0005\u001a\u00020\u00062\b\u0010\u0007\u001a\u0004\u0018\u00010\bHÖ\u0003J\t\u0010\t\u001a\u00020\nHÖ\u0001J\t\u0010\u000b\u001a\u00020\fHÖ\u0001¨\u0006\r"}, d2 = {"Lcom/miui/notes/ai/request/AIResult$TempBannedError;", "Lcom/miui/notes/ai/request/AIResult;", "", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "()V", "equals", "", RemoteDeviceInfo.MANUFACTURER_OTHER, "", "hashCode", "", "toString", "", "app_PhoneGlobalRelease"}, k = 1, mv = {2, 2, 0}, xi = 48)
    public static final /* data */ class TempBannedError extends AIResult {
        public static final TempBannedError INSTANCE = new TempBannedError();

        public boolean equals(Object other) {
            if (this == other) {
                return true;
            }
            if (!(other instanceof TempBannedError)) {
                return false;
            }
            return true;
        }

        public int hashCode() {
            return -445177068;
        }

        public String toString() {
            return "TempBannedError";
        }

        private TempBannedError() {
            super(null);
        }
    }

    /* JADX INFO: compiled from: AIResult.kt */
    @Metadata(d1 = {"\u0000(\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0001\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0000\bÆ\n\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u0001B\t\b\u0002¢\u0006\u0004\b\u0003\u0010\u0004J\u0013\u0010\u0005\u001a\u00020\u00062\b\u0010\u0007\u001a\u0004\u0018\u00010\bHÖ\u0003J\t\u0010\t\u001a\u00020\nHÖ\u0001J\t\u0010\u000b\u001a\u00020\fHÖ\u0001¨\u0006\r"}, d2 = {"Lcom/miui/notes/ai/request/AIResult$ForeverBannedError;", "Lcom/miui/notes/ai/request/AIResult;", "", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "()V", "equals", "", RemoteDeviceInfo.MANUFACTURER_OTHER, "", "hashCode", "", "toString", "", "app_PhoneGlobalRelease"}, k = 1, mv = {2, 2, 0}, xi = 48)
    public static final /* data */ class ForeverBannedError extends AIResult {
        public static final ForeverBannedError INSTANCE = new ForeverBannedError();

        public boolean equals(Object other) {
            if (this == other) {
                return true;
            }
            if (!(other instanceof ForeverBannedError)) {
                return false;
            }
            return true;
        }

        public int hashCode() {
            return 175363813;
        }

        public String toString() {
            return "ForeverBannedError";
        }

        private ForeverBannedError() {
            super(null);
        }
    }

    /* JADX INFO: compiled from: AIResult.kt */
    @Metadata(d1 = {"\u0000(\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0001\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0000\bÆ\n\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u0001B\t\b\u0002¢\u0006\u0004\b\u0003\u0010\u0004J\u0013\u0010\u0005\u001a\u00020\u00062\b\u0010\u0007\u001a\u0004\u0018\u00010\bHÖ\u0003J\t\u0010\t\u001a\u00020\nHÖ\u0001J\t\u0010\u000b\u001a\u00020\fHÖ\u0001¨\u0006\r"}, d2 = {"Lcom/miui/notes/ai/request/AIResult$AuthTokenError;", "Lcom/miui/notes/ai/request/AIResult;", "", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "()V", "equals", "", RemoteDeviceInfo.MANUFACTURER_OTHER, "", "hashCode", "", "toString", "", "app_PhoneGlobalRelease"}, k = 1, mv = {2, 2, 0}, xi = 48)
    public static final /* data */ class AuthTokenError extends AIResult {
        public static final AuthTokenError INSTANCE = new AuthTokenError();

        public boolean equals(Object other) {
            if (this == other) {
                return true;
            }
            if (!(other instanceof AuthTokenError)) {
                return false;
            }
            return true;
        }

        public int hashCode() {
            return 100730265;
        }

        public String toString() {
            return "AuthTokenError";
        }

        private AuthTokenError() {
            super(null);
        }
    }

    /* JADX INFO: compiled from: AIResult.kt */
    @Metadata(d1 = {"\u0000(\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0001\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0000\bÆ\n\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u0001B\t\b\u0002¢\u0006\u0004\b\u0003\u0010\u0004J\u0013\u0010\u0005\u001a\u00020\u00062\b\u0010\u0007\u001a\u0004\u0018\u00010\bHÖ\u0003J\t\u0010\t\u001a\u00020\nHÖ\u0001J\t\u0010\u000b\u001a\u00020\fHÖ\u0001¨\u0006\r"}, d2 = {"Lcom/miui/notes/ai/request/AIResult$Finish;", "Lcom/miui/notes/ai/request/AIResult;", "", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "()V", "equals", "", RemoteDeviceInfo.MANUFACTURER_OTHER, "", "hashCode", "", "toString", "", "app_PhoneGlobalRelease"}, k = 1, mv = {2, 2, 0}, xi = 48)
    public static final /* data */ class Finish extends AIResult {
        public static final Finish INSTANCE = new Finish();

        public boolean equals(Object other) {
            if (this == other) {
                return true;
            }
            if (!(other instanceof Finish)) {
                return false;
            }
            return true;
        }

        public int hashCode() {
            return -1291341611;
        }

        public String toString() {
            return "Finish";
        }

        private Finish() {
            super(null);
        }
    }
}
