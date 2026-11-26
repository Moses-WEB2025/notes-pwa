package com.miui.handwriting.common.ai.data.state;

import ch.qos.logback.core.CoreConstants;
import com.market.sdk.reflect.ReflectUtilsForMiui;
import com.xiaomi.mirror.RemoteDeviceInfo;
import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: BallStateLiveData.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u0000\u001e\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\b6\u0018\u00002\u00020\u0001:\u0004\u0004\u0005\u0006\u0007B\t\b\u0004¢\u0006\u0004\b\u0002\u0010\u0003\u0082\u0001\u0004\b\t\n\u000b¨\u0006\f"}, d2 = {"Lcom/miui/handwriting/common/ai/data/state/BallState;", "", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "()V", "Loading", "Success", "Filter", "Error", "Lcom/miui/handwriting/common/ai/data/state/BallState$Error;", "Lcom/miui/handwriting/common/ai/data/state/BallState$Filter;", "Lcom/miui/handwriting/common/ai/data/state/BallState$Loading;", "Lcom/miui/handwriting/common/ai/data/state/BallState$Success;", "libHandWritingCommon_release"}, k = 1, mv = {2, 2, 0}, xi = 48)
public abstract class BallState {
    public /* synthetic */ BallState(DefaultConstructorMarker defaultConstructorMarker) {
        this();
    }

    /* JADX INFO: compiled from: BallStateLiveData.kt */
    @Metadata(d1 = {"\u0000$\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0000\bÆ\n\u0018\u00002\u00020\u0001B\t\b\u0002¢\u0006\u0004\b\u0002\u0010\u0003J\u0013\u0010\u0004\u001a\u00020\u00052\b\u0010\u0006\u001a\u0004\u0018\u00010\u0007HÖ\u0003J\t\u0010\b\u001a\u00020\tHÖ\u0001J\t\u0010\n\u001a\u00020\u000bHÖ\u0001¨\u0006\f"}, d2 = {"Lcom/miui/handwriting/common/ai/data/state/BallState$Loading;", "Lcom/miui/handwriting/common/ai/data/state/BallState;", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "()V", "equals", "", RemoteDeviceInfo.MANUFACTURER_OTHER, "", "hashCode", "", "toString", "", "libHandWritingCommon_release"}, k = 1, mv = {2, 2, 0}, xi = 48)
    public static final /* data */ class Loading extends BallState {
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
            return 1849514516;
        }

        public String toString() {
            return "Loading";
        }

        private Loading() {
            super(null);
        }
    }

    private BallState() {
    }

    /* JADX INFO: compiled from: BallStateLiveData.kt */
    @Metadata(d1 = {"\u0000$\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0000\bÆ\n\u0018\u00002\u00020\u0001B\t\b\u0002¢\u0006\u0004\b\u0002\u0010\u0003J\u0013\u0010\u0004\u001a\u00020\u00052\b\u0010\u0006\u001a\u0004\u0018\u00010\u0007HÖ\u0003J\t\u0010\b\u001a\u00020\tHÖ\u0001J\t\u0010\n\u001a\u00020\u000bHÖ\u0001¨\u0006\f"}, d2 = {"Lcom/miui/handwriting/common/ai/data/state/BallState$Success;", "Lcom/miui/handwriting/common/ai/data/state/BallState;", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "()V", "equals", "", RemoteDeviceInfo.MANUFACTURER_OTHER, "", "hashCode", "", "toString", "", "libHandWritingCommon_release"}, k = 1, mv = {2, 2, 0}, xi = 48)
    public static final /* data */ class Success extends BallState {
        public static final Success INSTANCE = new Success();

        public boolean equals(Object other) {
            if (this == other) {
                return true;
            }
            if (!(other instanceof Success)) {
                return false;
            }
            return true;
        }

        public int hashCode() {
            return -354305829;
        }

        public String toString() {
            return "Success";
        }

        private Success() {
            super(null);
        }
    }

    /* JADX INFO: compiled from: BallStateLiveData.kt */
    @Metadata(d1 = {"\u0000$\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0000\bÆ\n\u0018\u00002\u00020\u0001B\t\b\u0002¢\u0006\u0004\b\u0002\u0010\u0003J\u0013\u0010\u0004\u001a\u00020\u00052\b\u0010\u0006\u001a\u0004\u0018\u00010\u0007HÖ\u0003J\t\u0010\b\u001a\u00020\tHÖ\u0001J\t\u0010\n\u001a\u00020\u000bHÖ\u0001¨\u0006\f"}, d2 = {"Lcom/miui/handwriting/common/ai/data/state/BallState$Filter;", "Lcom/miui/handwriting/common/ai/data/state/BallState;", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "()V", "equals", "", RemoteDeviceInfo.MANUFACTURER_OTHER, "", "hashCode", "", "toString", "", "libHandWritingCommon_release"}, k = 1, mv = {2, 2, 0}, xi = 48)
    public static final /* data */ class Filter extends BallState {
        public static final Filter INSTANCE = new Filter();

        public boolean equals(Object other) {
            if (this == other) {
                return true;
            }
            if (!(other instanceof Filter)) {
                return false;
            }
            return true;
        }

        public int hashCode() {
            return -394405984;
        }

        public String toString() {
            return "Filter";
        }

        private Filter() {
            super(null);
        }
    }

    /* JADX INFO: compiled from: BallStateLiveData.kt */
    @Metadata(d1 = {"\u0000&\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0007\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\b\u0086\b\u0018\u00002\u00020\u0001B\u000f\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0004\b\u0004\u0010\u0005J\t\u0010\b\u001a\u00020\u0003HÆ\u0003J\u0013\u0010\t\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u0003HÆ\u0001J\u0013\u0010\n\u001a\u00020\u000b2\b\u0010\f\u001a\u0004\u0018\u00010\rHÖ\u0003J\t\u0010\u000e\u001a\u00020\u000fHÖ\u0001J\t\u0010\u0010\u001a\u00020\u0003HÖ\u0001R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0006\u0010\u0007¨\u0006\u0011"}, d2 = {"Lcom/miui/handwriting/common/ai/data/state/BallState$Error;", "Lcom/miui/handwriting/common/ai/data/state/BallState;", "message", "", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "(Ljava/lang/String;)V", "getMessage", "()Ljava/lang/String;", "component1", "copy", "equals", "", RemoteDeviceInfo.MANUFACTURER_OTHER, "", "hashCode", "", "toString", "libHandWritingCommon_release"}, k = 1, mv = {2, 2, 0}, xi = 48)
    public static final /* data */ class Error extends BallState {
        private final String message;

        public static /* synthetic */ Error copy$default(Error error, String str, int i, Object obj) {
            if ((i & 1) != 0) {
                str = error.message;
            }
            return error.copy(str);
        }

        /* JADX INFO: renamed from: component1, reason: from getter */
        public final String getMessage() {
            return this.message;
        }

        public final Error copy(String message) {
            Intrinsics.checkNotNullParameter(message, "message");
            return new Error(message);
        }

        public boolean equals(Object other) {
            if (this == other) {
                return true;
            }
            return (other instanceof Error) && Intrinsics.areEqual(this.message, ((Error) other).message);
        }

        public int hashCode() {
            return this.message.hashCode();
        }

        public String toString() {
            return "Error(message=" + this.message + CoreConstants.RIGHT_PARENTHESIS_CHAR;
        }

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public Error(String message) {
            super(null);
            Intrinsics.checkNotNullParameter(message, "message");
            this.message = message;
        }

        public final String getMessage() {
            return this.message;
        }
    }
}
