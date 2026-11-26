package com.miui.notes.page.viewmodel;

import androidx.exifinterface.media.ExifInterface;
import com.market.sdk.reflect.ReflectUtilsForMiui;
import com.xiaomi.mirror.RemoteDeviceInfo;
import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: OperationResult.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000\u0018\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0000\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\b6\u0018\u0000*\u0006\b\u0000\u0010\u0001 \u00012\u00020\u0002:\u0002\u0005\u0006B\t\b\u0004¢\u0006\u0004\b\u0003\u0010\u0004\u0082\u0001\u0002\u0007\b¨\u0006\t"}, d2 = {"Lcom/miui/notes/page/viewmodel/Result;", ExifInterface.GPS_DIRECTION_TRUE, "", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "()V", "Complete", "Loading", "Lcom/miui/notes/page/viewmodel/Result$Complete;", "Lcom/miui/notes/page/viewmodel/Result$Loading;", "app_PhoneGlobalRelease"}, k = 1, mv = {2, 2, 0}, xi = 48)
public abstract class Result<T> {
    public /* synthetic */ Result(DefaultConstructorMarker defaultConstructorMarker) {
        this();
    }

    /* JADX INFO: compiled from: OperationResult.kt */
    @Metadata(d1 = {"\u0000.\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\f\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0000\b\u0086\b\u0018\u0000*\u0006\b\u0001\u0010\u0001 \u00012\b\u0012\u0004\u0012\u0002H\u00010\u0002B\u0017\u0012\u0006\u0010\u0003\u001a\u00028\u0001\u0012\u0006\u0010\u0004\u001a\u00020\u0005¢\u0006\u0004\b\u0006\u0010\u0007J\u000e\u0010\r\u001a\u00028\u0001HÆ\u0003¢\u0006\u0002\u0010\tJ\t\u0010\u000e\u001a\u00020\u0005HÆ\u0003J(\u0010\u000f\u001a\b\u0012\u0004\u0012\u00028\u00010\u00002\b\b\u0002\u0010\u0003\u001a\u00028\u00012\b\b\u0002\u0010\u0004\u001a\u00020\u0005HÆ\u0001¢\u0006\u0002\u0010\u0010J\u0013\u0010\u0011\u001a\u00020\u00122\b\u0010\u0013\u001a\u0004\u0018\u00010\u0014HÖ\u0003J\t\u0010\u0015\u001a\u00020\u0016HÖ\u0001J\t\u0010\u0017\u001a\u00020\u0018HÖ\u0001R\u0013\u0010\u0003\u001a\u00028\u0001¢\u0006\n\n\u0002\u0010\n\u001a\u0004\b\b\u0010\tR\u0011\u0010\u0004\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\f¨\u0006\u0019"}, d2 = {"Lcom/miui/notes/page/viewmodel/Result$Complete;", ExifInterface.GPS_DIRECTION_TRUE, "Lcom/miui/notes/page/viewmodel/Result;", "param", "operation", "Lcom/miui/notes/page/viewmodel/Operation;", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "(Ljava/lang/Object;Lcom/miui/notes/page/viewmodel/Operation;)V", "getParam", "()Ljava/lang/Object;", "Ljava/lang/Object;", "getOperation", "()Lcom/miui/notes/page/viewmodel/Operation;", "component1", "component2", "copy", "(Ljava/lang/Object;Lcom/miui/notes/page/viewmodel/Operation;)Lcom/miui/notes/page/viewmodel/Result$Complete;", "equals", "", RemoteDeviceInfo.MANUFACTURER_OTHER, "", "hashCode", "", "toString", "", "app_PhoneGlobalRelease"}, k = 1, mv = {2, 2, 0}, xi = 48)
    public static final /* data */ class Complete<T> extends Result<T> {
        private final Operation operation;
        private final T param;

        /* JADX WARN: Multi-variable type inference failed */
        public static /* synthetic */ Complete copy$default(Complete complete, Object obj, Operation operation, int i, Object obj2) {
            if ((i & 1) != 0) {
                obj = complete.param;
            }
            if ((i & 2) != 0) {
                operation = complete.operation;
            }
            return complete.copy(obj, operation);
        }

        public final T component1() {
            return this.param;
        }

        /* JADX INFO: renamed from: component2, reason: from getter */
        public final Operation getOperation() {
            return this.operation;
        }

        public final Complete<T> copy(T param, Operation operation) {
            Intrinsics.checkNotNullParameter(operation, "operation");
            return new Complete<>(param, operation);
        }

        public boolean equals(Object other) {
            if (this == other) {
                return true;
            }
            if (!(other instanceof Complete)) {
                return false;
            }
            Complete complete = (Complete) other;
            return Intrinsics.areEqual(this.param, complete.param) && this.operation == complete.operation;
        }

        public int hashCode() {
            T t = this.param;
            return ((t == null ? 0 : t.hashCode()) * 31) + this.operation.hashCode();
        }

        public String toString() {
            return "Complete(param=" + this.param + ", operation=" + this.operation + ")";
        }

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public Complete(T t, Operation operation) {
            super(null);
            Intrinsics.checkNotNullParameter(operation, "operation");
            this.param = t;
            this.operation = operation;
        }

        public final Operation getOperation() {
            return this.operation;
        }

        public final T getParam() {
            return this.param;
        }
    }

    private Result() {
    }

    /* JADX INFO: compiled from: OperationResult.kt */
    @Metadata(d1 = {"\u0000*\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0001\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0007\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\b\u0086\b\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u0001B\u000f\u0012\u0006\u0010\u0003\u001a\u00020\u0004¢\u0006\u0004\b\u0005\u0010\u0006J\t\u0010\t\u001a\u00020\u0004HÆ\u0003J\u0013\u0010\n\u001a\u00020\u00002\b\b\u0002\u0010\u0003\u001a\u00020\u0004HÆ\u0001J\u0013\u0010\u000b\u001a\u00020\f2\b\u0010\r\u001a\u0004\u0018\u00010\u000eHÖ\u0003J\t\u0010\u000f\u001a\u00020\u0010HÖ\u0001J\t\u0010\u0011\u001a\u00020\u0004HÖ\u0001R\u0011\u0010\u0003\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\b¨\u0006\u0012"}, d2 = {"Lcom/miui/notes/page/viewmodel/Result$Loading;", "Lcom/miui/notes/page/viewmodel/Result;", "", "loadingText", "", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "(Ljava/lang/String;)V", "getLoadingText", "()Ljava/lang/String;", "component1", "copy", "equals", "", RemoteDeviceInfo.MANUFACTURER_OTHER, "", "hashCode", "", "toString", "app_PhoneGlobalRelease"}, k = 1, mv = {2, 2, 0}, xi = 48)
    public static final /* data */ class Loading extends Result {
        private final String loadingText;

        public static /* synthetic */ Loading copy$default(Loading loading, String str, int i, Object obj) {
            if ((i & 1) != 0) {
                str = loading.loadingText;
            }
            return loading.copy(str);
        }

        /* JADX INFO: renamed from: component1, reason: from getter */
        public final String getLoadingText() {
            return this.loadingText;
        }

        public final Loading copy(String loadingText) {
            Intrinsics.checkNotNullParameter(loadingText, "loadingText");
            return new Loading(loadingText);
        }

        public boolean equals(Object other) {
            if (this == other) {
                return true;
            }
            return (other instanceof Loading) && Intrinsics.areEqual(this.loadingText, ((Loading) other).loadingText);
        }

        public int hashCode() {
            return this.loadingText.hashCode();
        }

        public String toString() {
            return "Loading(loadingText=" + this.loadingText + ")";
        }

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public Loading(String loadingText) {
            super(null);
            Intrinsics.checkNotNullParameter(loadingText, "loadingText");
            this.loadingText = loadingText;
        }

        public final String getLoadingText() {
            return this.loadingText;
        }
    }
}
