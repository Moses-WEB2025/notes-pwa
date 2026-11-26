package com.miui.notes.ai.network.request;

import androidx.exifinterface.media.ExifInterface;
import com.market.sdk.reflect.ReflectUtilsForMiui;
import com.xiaomi.mirror.RemoteDeviceInfo;
import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: AIResult.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000\u001c\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0000\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\b6\u0018\u0000*\u0006\b\u0000\u0010\u0001 \u00012\u00020\u0002:\u0003\u0005\u0006\u0007B\t\b\u0004¢\u0006\u0004\b\u0003\u0010\u0004\u0082\u0001\u0003\b\t\n¨\u0006\u000b"}, d2 = {"Lcom/miui/notes/ai/network/request/AIResult;", ExifInterface.GPS_DIRECTION_TRUE, "", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "()V", "Complete", "Loading", "Finish", "Lcom/miui/notes/ai/network/request/AIResult$Complete;", "Lcom/miui/notes/ai/network/request/AIResult$Finish;", "Lcom/miui/notes/ai/network/request/AIResult$Loading;", "libAiTextWidget_release"}, k = 1, mv = {2, 2, 0}, xi = 48)
public abstract class AIResult<T> {
    public /* synthetic */ AIResult(DefaultConstructorMarker defaultConstructorMarker) {
        this();
    }

    /* JADX INFO: compiled from: AIResult.kt */
    @Metadata(d1 = {"\u0000*\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\f\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\b\u0086\b\u0018\u0000*\u0006\b\u0001\u0010\u0001 \u00012\b\u0012\u0004\u0012\u0002H\u00010\u0002B\u0017\u0012\u0006\u0010\u0003\u001a\u00028\u0001\u0012\u0006\u0010\u0004\u001a\u00020\u0005¢\u0006\u0004\b\u0006\u0010\u0007J\u000e\u0010\r\u001a\u00028\u0001HÆ\u0003¢\u0006\u0002\u0010\tJ\t\u0010\u000e\u001a\u00020\u0005HÆ\u0003J(\u0010\u000f\u001a\b\u0012\u0004\u0012\u00028\u00010\u00002\b\b\u0002\u0010\u0003\u001a\u00028\u00012\b\b\u0002\u0010\u0004\u001a\u00020\u0005HÆ\u0001¢\u0006\u0002\u0010\u0010J\u0013\u0010\u0011\u001a\u00020\u00122\b\u0010\u0013\u001a\u0004\u0018\u00010\u0014HÖ\u0003J\t\u0010\u0015\u001a\u00020\u0016HÖ\u0001J\t\u0010\u0017\u001a\u00020\u0005HÖ\u0001R\u0013\u0010\u0003\u001a\u00028\u0001¢\u0006\n\n\u0002\u0010\n\u001a\u0004\b\b\u0010\tR\u0011\u0010\u0004\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\f¨\u0006\u0018"}, d2 = {"Lcom/miui/notes/ai/network/request/AIResult$Complete;", ExifInterface.GPS_DIRECTION_TRUE, "Lcom/miui/notes/ai/network/request/AIResult;", "param", "traceId", "", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "(Ljava/lang/Object;Ljava/lang/String;)V", "getParam", "()Ljava/lang/Object;", "Ljava/lang/Object;", "getTraceId", "()Ljava/lang/String;", "component1", "component2", "copy", "(Ljava/lang/Object;Ljava/lang/String;)Lcom/miui/notes/ai/network/request/AIResult$Complete;", "equals", "", RemoteDeviceInfo.MANUFACTURER_OTHER, "", "hashCode", "", "toString", "libAiTextWidget_release"}, k = 1, mv = {2, 2, 0}, xi = 48)
    public static final /* data */ class Complete<T> extends AIResult<T> {
        private final T param;
        private final String traceId;

        /* JADX WARN: Multi-variable type inference failed */
        public static /* synthetic */ Complete copy$default(Complete complete, Object obj, String str, int i, Object obj2) {
            if ((i & 1) != 0) {
                obj = complete.param;
            }
            if ((i & 2) != 0) {
                str = complete.traceId;
            }
            return complete.copy(obj, str);
        }

        public final T component1() {
            return this.param;
        }

        /* JADX INFO: renamed from: component2, reason: from getter */
        public final String getTraceId() {
            return this.traceId;
        }

        public final Complete<T> copy(T param, String traceId) {
            Intrinsics.checkNotNullParameter(traceId, "traceId");
            return new Complete<>(param, traceId);
        }

        public boolean equals(Object other) {
            if (this == other) {
                return true;
            }
            if (!(other instanceof Complete)) {
                return false;
            }
            Complete complete = (Complete) other;
            return Intrinsics.areEqual(this.param, complete.param) && Intrinsics.areEqual(this.traceId, complete.traceId);
        }

        public int hashCode() {
            T t = this.param;
            return ((t == null ? 0 : t.hashCode()) * 31) + this.traceId.hashCode();
        }

        public String toString() {
            return "Complete(param=" + this.param + ", traceId=" + this.traceId + ")";
        }

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public Complete(T t, String traceId) {
            super(null);
            Intrinsics.checkNotNullParameter(traceId, "traceId");
            this.param = t;
            this.traceId = traceId;
        }

        public final T getParam() {
            return this.param;
        }

        public final String getTraceId() {
            return this.traceId;
        }
    }

    private AIResult() {
    }

    /* JADX INFO: compiled from: AIResult.kt */
    @Metadata(d1 = {"\u0000\u0010\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0001\n\u0002\b\u0003\bÆ\u0002\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u0001B\t\b\u0002¢\u0006\u0004\b\u0003\u0010\u0004¨\u0006\u0005"}, d2 = {"Lcom/miui/notes/ai/network/request/AIResult$Loading;", "Lcom/miui/notes/ai/network/request/AIResult;", "", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "()V", "libAiTextWidget_release"}, k = 1, mv = {2, 2, 0}, xi = 48)
    public static final class Loading extends AIResult {
        public static final Loading INSTANCE = new Loading();

        private Loading() {
            super(null);
        }
    }

    /* JADX INFO: compiled from: AIResult.kt */
    @Metadata(d1 = {"\u0000\u0010\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0001\n\u0002\b\u0003\bÆ\u0002\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u0001B\t\b\u0002¢\u0006\u0004\b\u0003\u0010\u0004¨\u0006\u0005"}, d2 = {"Lcom/miui/notes/ai/network/request/AIResult$Finish;", "Lcom/miui/notes/ai/network/request/AIResult;", "", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "()V", "libAiTextWidget_release"}, k = 1, mv = {2, 2, 0}, xi = 48)
    public static final class Finish extends AIResult {
        public static final Finish INSTANCE = new Finish();

        private Finish() {
            super(null);
        }
    }
}
