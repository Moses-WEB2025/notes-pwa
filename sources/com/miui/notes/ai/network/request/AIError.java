package com.miui.notes.ai.network.request;

import com.market.sdk.reflect.ReflectUtilsForMiui;
import com.xiaomi.mirror.RemoteDeviceInfo;
import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: AIError.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u00004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0000\n\u0002\b\u000b\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\b6\u0018\u0000*\u0004\b\u0000\u0010\u00012\u00020\u0002:\t\u0005\u0006\u0007\b\t\n\u000b\f\rB\t\b\u0004¢\u0006\u0004\b\u0003\u0010\u0004\u0082\u0001\t\u000e\u000f\u0010\u0011\u0012\u0013\u0014\u0015\u0016¨\u0006\u0017"}, d2 = {"Lcom/miui/notes/ai/network/request/AIError;", "ResponseErrorDetail", "", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "()V", "Error", "IllegalError", "TempBannedError", "ForeverBannedError", "TextLengthLimitError", "AuthTokenError", "NotSupportError", "SystemUpdateError", "LanguageNotSupportError", "Lcom/miui/notes/ai/network/request/AIError$AuthTokenError;", "Lcom/miui/notes/ai/network/request/AIError$Error;", "Lcom/miui/notes/ai/network/request/AIError$ForeverBannedError;", "Lcom/miui/notes/ai/network/request/AIError$IllegalError;", "Lcom/miui/notes/ai/network/request/AIError$LanguageNotSupportError;", "Lcom/miui/notes/ai/network/request/AIError$NotSupportError;", "Lcom/miui/notes/ai/network/request/AIError$SystemUpdateError;", "Lcom/miui/notes/ai/network/request/AIError$TempBannedError;", "Lcom/miui/notes/ai/network/request/AIError$TextLengthLimitError;", "libAiTextWidget_release"}, k = 1, mv = {2, 2, 0}, xi = 48)
public abstract class AIError<ResponseErrorDetail> {
    public /* synthetic */ AIError(DefaultConstructorMarker defaultConstructorMarker) {
        this();
    }

    private AIError() {
    }

    /* JADX INFO: compiled from: AIError.kt */
    @Metadata(d1 = {"\u0000&\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\n\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0000\b\u0086\b\u0018\u0000*\u0004\b\u0001\u0010\u00012\b\u0012\u0004\u0012\u0002H\u00010\u0002B\u000f\u0012\u0006\u0010\u0003\u001a\u00028\u0001¢\u0006\u0004\b\u0004\u0010\u0005J\u000e\u0010\t\u001a\u00028\u0001HÆ\u0003¢\u0006\u0002\u0010\u0007J\u001e\u0010\n\u001a\b\u0012\u0004\u0012\u00028\u00010\u00002\b\b\u0002\u0010\u0003\u001a\u00028\u0001HÆ\u0001¢\u0006\u0002\u0010\u000bJ\u0013\u0010\f\u001a\u00020\r2\b\u0010\u000e\u001a\u0004\u0018\u00010\u000fHÖ\u0003J\t\u0010\u0010\u001a\u00020\u0011HÖ\u0001J\t\u0010\u0012\u001a\u00020\u0013HÖ\u0001R\u0013\u0010\u0003\u001a\u00028\u0001¢\u0006\n\n\u0002\u0010\b\u001a\u0004\b\u0006\u0010\u0007¨\u0006\u0014"}, d2 = {"Lcom/miui/notes/ai/network/request/AIError$Error;", "ResponseErrorDetail", "Lcom/miui/notes/ai/network/request/AIError;", "param", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "(Ljava/lang/Object;)V", "getParam", "()Ljava/lang/Object;", "Ljava/lang/Object;", "component1", "copy", "(Ljava/lang/Object;)Lcom/miui/notes/ai/network/request/AIError$Error;", "equals", "", RemoteDeviceInfo.MANUFACTURER_OTHER, "", "hashCode", "", "toString", "", "libAiTextWidget_release"}, k = 1, mv = {2, 2, 0}, xi = 48)
    public static final /* data */ class Error<ResponseErrorDetail> extends AIError<ResponseErrorDetail> {
        private final ResponseErrorDetail param;

        /* JADX WARN: Multi-variable type inference failed */
        public static /* synthetic */ Error copy$default(Error error, Object obj, int i, Object obj2) {
            if ((i & 1) != 0) {
                obj = error.param;
            }
            return error.copy(obj);
        }

        public final ResponseErrorDetail component1() {
            return this.param;
        }

        public final Error<ResponseErrorDetail> copy(ResponseErrorDetail param) {
            return new Error<>(param);
        }

        public boolean equals(Object other) {
            if (this == other) {
                return true;
            }
            return (other instanceof Error) && Intrinsics.areEqual(this.param, ((Error) other).param);
        }

        public int hashCode() {
            ResponseErrorDetail responseerrordetail = this.param;
            if (responseerrordetail == null) {
                return 0;
            }
            return responseerrordetail.hashCode();
        }

        public String toString() {
            return "Error(param=" + this.param + ")";
        }

        public Error(ResponseErrorDetail responseerrordetail) {
            super(null);
            this.param = responseerrordetail;
        }

        public final ResponseErrorDetail getParam() {
            return this.param;
        }
    }

    /* JADX INFO: compiled from: AIError.kt */
    @Metadata(d1 = {"\u0000&\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\n\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0000\b\u0086\b\u0018\u0000*\u0004\b\u0001\u0010\u00012\b\u0012\u0004\u0012\u0002H\u00010\u0002B\u000f\u0012\u0006\u0010\u0003\u001a\u00028\u0001¢\u0006\u0004\b\u0004\u0010\u0005J\u000e\u0010\t\u001a\u00028\u0001HÆ\u0003¢\u0006\u0002\u0010\u0007J\u001e\u0010\n\u001a\b\u0012\u0004\u0012\u00028\u00010\u00002\b\b\u0002\u0010\u0003\u001a\u00028\u0001HÆ\u0001¢\u0006\u0002\u0010\u000bJ\u0013\u0010\f\u001a\u00020\r2\b\u0010\u000e\u001a\u0004\u0018\u00010\u000fHÖ\u0003J\t\u0010\u0010\u001a\u00020\u0011HÖ\u0001J\t\u0010\u0012\u001a\u00020\u0013HÖ\u0001R\u0013\u0010\u0003\u001a\u00028\u0001¢\u0006\n\n\u0002\u0010\b\u001a\u0004\b\u0006\u0010\u0007¨\u0006\u0014"}, d2 = {"Lcom/miui/notes/ai/network/request/AIError$IllegalError;", "ResponseErrorDetail", "Lcom/miui/notes/ai/network/request/AIError;", "param", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "(Ljava/lang/Object;)V", "getParam", "()Ljava/lang/Object;", "Ljava/lang/Object;", "component1", "copy", "(Ljava/lang/Object;)Lcom/miui/notes/ai/network/request/AIError$IllegalError;", "equals", "", RemoteDeviceInfo.MANUFACTURER_OTHER, "", "hashCode", "", "toString", "", "libAiTextWidget_release"}, k = 1, mv = {2, 2, 0}, xi = 48)
    public static final /* data */ class IllegalError<ResponseErrorDetail> extends AIError<ResponseErrorDetail> {
        private final ResponseErrorDetail param;

        /* JADX WARN: Multi-variable type inference failed */
        public static /* synthetic */ IllegalError copy$default(IllegalError illegalError, Object obj, int i, Object obj2) {
            if ((i & 1) != 0) {
                obj = illegalError.param;
            }
            return illegalError.copy(obj);
        }

        public final ResponseErrorDetail component1() {
            return this.param;
        }

        public final IllegalError<ResponseErrorDetail> copy(ResponseErrorDetail param) {
            return new IllegalError<>(param);
        }

        public boolean equals(Object other) {
            if (this == other) {
                return true;
            }
            return (other instanceof IllegalError) && Intrinsics.areEqual(this.param, ((IllegalError) other).param);
        }

        public int hashCode() {
            ResponseErrorDetail responseerrordetail = this.param;
            if (responseerrordetail == null) {
                return 0;
            }
            return responseerrordetail.hashCode();
        }

        public String toString() {
            return "IllegalError(param=" + this.param + ")";
        }

        public IllegalError(ResponseErrorDetail responseerrordetail) {
            super(null);
            this.param = responseerrordetail;
        }

        public final ResponseErrorDetail getParam() {
            return this.param;
        }
    }

    /* JADX INFO: compiled from: AIError.kt */
    @Metadata(d1 = {"\u0000&\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\n\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0000\b\u0086\b\u0018\u0000*\u0004\b\u0001\u0010\u00012\b\u0012\u0004\u0012\u0002H\u00010\u0002B\u000f\u0012\u0006\u0010\u0003\u001a\u00028\u0001¢\u0006\u0004\b\u0004\u0010\u0005J\u000e\u0010\t\u001a\u00028\u0001HÆ\u0003¢\u0006\u0002\u0010\u0007J\u001e\u0010\n\u001a\b\u0012\u0004\u0012\u00028\u00010\u00002\b\b\u0002\u0010\u0003\u001a\u00028\u0001HÆ\u0001¢\u0006\u0002\u0010\u000bJ\u0013\u0010\f\u001a\u00020\r2\b\u0010\u000e\u001a\u0004\u0018\u00010\u000fHÖ\u0003J\t\u0010\u0010\u001a\u00020\u0011HÖ\u0001J\t\u0010\u0012\u001a\u00020\u0013HÖ\u0001R\u0013\u0010\u0003\u001a\u00028\u0001¢\u0006\n\n\u0002\u0010\b\u001a\u0004\b\u0006\u0010\u0007¨\u0006\u0014"}, d2 = {"Lcom/miui/notes/ai/network/request/AIError$TempBannedError;", "ResponseErrorDetail", "Lcom/miui/notes/ai/network/request/AIError;", "param", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "(Ljava/lang/Object;)V", "getParam", "()Ljava/lang/Object;", "Ljava/lang/Object;", "component1", "copy", "(Ljava/lang/Object;)Lcom/miui/notes/ai/network/request/AIError$TempBannedError;", "equals", "", RemoteDeviceInfo.MANUFACTURER_OTHER, "", "hashCode", "", "toString", "", "libAiTextWidget_release"}, k = 1, mv = {2, 2, 0}, xi = 48)
    public static final /* data */ class TempBannedError<ResponseErrorDetail> extends AIError<ResponseErrorDetail> {
        private final ResponseErrorDetail param;

        /* JADX WARN: Multi-variable type inference failed */
        public static /* synthetic */ TempBannedError copy$default(TempBannedError tempBannedError, Object obj, int i, Object obj2) {
            if ((i & 1) != 0) {
                obj = tempBannedError.param;
            }
            return tempBannedError.copy(obj);
        }

        public final ResponseErrorDetail component1() {
            return this.param;
        }

        public final TempBannedError<ResponseErrorDetail> copy(ResponseErrorDetail param) {
            return new TempBannedError<>(param);
        }

        public boolean equals(Object other) {
            if (this == other) {
                return true;
            }
            return (other instanceof TempBannedError) && Intrinsics.areEqual(this.param, ((TempBannedError) other).param);
        }

        public int hashCode() {
            ResponseErrorDetail responseerrordetail = this.param;
            if (responseerrordetail == null) {
                return 0;
            }
            return responseerrordetail.hashCode();
        }

        public String toString() {
            return "TempBannedError(param=" + this.param + ")";
        }

        public TempBannedError(ResponseErrorDetail responseerrordetail) {
            super(null);
            this.param = responseerrordetail;
        }

        public final ResponseErrorDetail getParam() {
            return this.param;
        }
    }

    /* JADX INFO: compiled from: AIError.kt */
    @Metadata(d1 = {"\u0000&\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\n\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0000\b\u0086\b\u0018\u0000*\u0004\b\u0001\u0010\u00012\b\u0012\u0004\u0012\u0002H\u00010\u0002B\u000f\u0012\u0006\u0010\u0003\u001a\u00028\u0001¢\u0006\u0004\b\u0004\u0010\u0005J\u000e\u0010\t\u001a\u00028\u0001HÆ\u0003¢\u0006\u0002\u0010\u0007J\u001e\u0010\n\u001a\b\u0012\u0004\u0012\u00028\u00010\u00002\b\b\u0002\u0010\u0003\u001a\u00028\u0001HÆ\u0001¢\u0006\u0002\u0010\u000bJ\u0013\u0010\f\u001a\u00020\r2\b\u0010\u000e\u001a\u0004\u0018\u00010\u000fHÖ\u0003J\t\u0010\u0010\u001a\u00020\u0011HÖ\u0001J\t\u0010\u0012\u001a\u00020\u0013HÖ\u0001R\u0013\u0010\u0003\u001a\u00028\u0001¢\u0006\n\n\u0002\u0010\b\u001a\u0004\b\u0006\u0010\u0007¨\u0006\u0014"}, d2 = {"Lcom/miui/notes/ai/network/request/AIError$ForeverBannedError;", "ResponseErrorDetail", "Lcom/miui/notes/ai/network/request/AIError;", "param", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "(Ljava/lang/Object;)V", "getParam", "()Ljava/lang/Object;", "Ljava/lang/Object;", "component1", "copy", "(Ljava/lang/Object;)Lcom/miui/notes/ai/network/request/AIError$ForeverBannedError;", "equals", "", RemoteDeviceInfo.MANUFACTURER_OTHER, "", "hashCode", "", "toString", "", "libAiTextWidget_release"}, k = 1, mv = {2, 2, 0}, xi = 48)
    public static final /* data */ class ForeverBannedError<ResponseErrorDetail> extends AIError<ResponseErrorDetail> {
        private final ResponseErrorDetail param;

        /* JADX WARN: Multi-variable type inference failed */
        public static /* synthetic */ ForeverBannedError copy$default(ForeverBannedError foreverBannedError, Object obj, int i, Object obj2) {
            if ((i & 1) != 0) {
                obj = foreverBannedError.param;
            }
            return foreverBannedError.copy(obj);
        }

        public final ResponseErrorDetail component1() {
            return this.param;
        }

        public final ForeverBannedError<ResponseErrorDetail> copy(ResponseErrorDetail param) {
            return new ForeverBannedError<>(param);
        }

        public boolean equals(Object other) {
            if (this == other) {
                return true;
            }
            return (other instanceof ForeverBannedError) && Intrinsics.areEqual(this.param, ((ForeverBannedError) other).param);
        }

        public int hashCode() {
            ResponseErrorDetail responseerrordetail = this.param;
            if (responseerrordetail == null) {
                return 0;
            }
            return responseerrordetail.hashCode();
        }

        public String toString() {
            return "ForeverBannedError(param=" + this.param + ")";
        }

        public ForeverBannedError(ResponseErrorDetail responseerrordetail) {
            super(null);
            this.param = responseerrordetail;
        }

        public final ResponseErrorDetail getParam() {
            return this.param;
        }
    }

    /* JADX INFO: compiled from: AIError.kt */
    @Metadata(d1 = {"\u0000&\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\n\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0000\b\u0086\b\u0018\u0000*\u0004\b\u0001\u0010\u00012\b\u0012\u0004\u0012\u0002H\u00010\u0002B\u000f\u0012\u0006\u0010\u0003\u001a\u00028\u0001¢\u0006\u0004\b\u0004\u0010\u0005J\u000e\u0010\t\u001a\u00028\u0001HÆ\u0003¢\u0006\u0002\u0010\u0007J\u001e\u0010\n\u001a\b\u0012\u0004\u0012\u00028\u00010\u00002\b\b\u0002\u0010\u0003\u001a\u00028\u0001HÆ\u0001¢\u0006\u0002\u0010\u000bJ\u0013\u0010\f\u001a\u00020\r2\b\u0010\u000e\u001a\u0004\u0018\u00010\u000fHÖ\u0003J\t\u0010\u0010\u001a\u00020\u0011HÖ\u0001J\t\u0010\u0012\u001a\u00020\u0013HÖ\u0001R\u0013\u0010\u0003\u001a\u00028\u0001¢\u0006\n\n\u0002\u0010\b\u001a\u0004\b\u0006\u0010\u0007¨\u0006\u0014"}, d2 = {"Lcom/miui/notes/ai/network/request/AIError$TextLengthLimitError;", "ResponseErrorDetail", "Lcom/miui/notes/ai/network/request/AIError;", "param", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "(Ljava/lang/Object;)V", "getParam", "()Ljava/lang/Object;", "Ljava/lang/Object;", "component1", "copy", "(Ljava/lang/Object;)Lcom/miui/notes/ai/network/request/AIError$TextLengthLimitError;", "equals", "", RemoteDeviceInfo.MANUFACTURER_OTHER, "", "hashCode", "", "toString", "", "libAiTextWidget_release"}, k = 1, mv = {2, 2, 0}, xi = 48)
    public static final /* data */ class TextLengthLimitError<ResponseErrorDetail> extends AIError<ResponseErrorDetail> {
        private final ResponseErrorDetail param;

        /* JADX WARN: Multi-variable type inference failed */
        public static /* synthetic */ TextLengthLimitError copy$default(TextLengthLimitError textLengthLimitError, Object obj, int i, Object obj2) {
            if ((i & 1) != 0) {
                obj = textLengthLimitError.param;
            }
            return textLengthLimitError.copy(obj);
        }

        public final ResponseErrorDetail component1() {
            return this.param;
        }

        public final TextLengthLimitError<ResponseErrorDetail> copy(ResponseErrorDetail param) {
            return new TextLengthLimitError<>(param);
        }

        public boolean equals(Object other) {
            if (this == other) {
                return true;
            }
            return (other instanceof TextLengthLimitError) && Intrinsics.areEqual(this.param, ((TextLengthLimitError) other).param);
        }

        public int hashCode() {
            ResponseErrorDetail responseerrordetail = this.param;
            if (responseerrordetail == null) {
                return 0;
            }
            return responseerrordetail.hashCode();
        }

        public String toString() {
            return "TextLengthLimitError(param=" + this.param + ")";
        }

        public TextLengthLimitError(ResponseErrorDetail responseerrordetail) {
            super(null);
            this.param = responseerrordetail;
        }

        public final ResponseErrorDetail getParam() {
            return this.param;
        }
    }

    /* JADX INFO: compiled from: AIError.kt */
    @Metadata(d1 = {"\u0000&\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\n\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0000\b\u0086\b\u0018\u0000*\u0004\b\u0001\u0010\u00012\b\u0012\u0004\u0012\u0002H\u00010\u0002B\u000f\u0012\u0006\u0010\u0003\u001a\u00028\u0001¢\u0006\u0004\b\u0004\u0010\u0005J\u000e\u0010\t\u001a\u00028\u0001HÆ\u0003¢\u0006\u0002\u0010\u0007J\u001e\u0010\n\u001a\b\u0012\u0004\u0012\u00028\u00010\u00002\b\b\u0002\u0010\u0003\u001a\u00028\u0001HÆ\u0001¢\u0006\u0002\u0010\u000bJ\u0013\u0010\f\u001a\u00020\r2\b\u0010\u000e\u001a\u0004\u0018\u00010\u000fHÖ\u0003J\t\u0010\u0010\u001a\u00020\u0011HÖ\u0001J\t\u0010\u0012\u001a\u00020\u0013HÖ\u0001R\u0013\u0010\u0003\u001a\u00028\u0001¢\u0006\n\n\u0002\u0010\b\u001a\u0004\b\u0006\u0010\u0007¨\u0006\u0014"}, d2 = {"Lcom/miui/notes/ai/network/request/AIError$AuthTokenError;", "ResponseErrorDetail", "Lcom/miui/notes/ai/network/request/AIError;", "param", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "(Ljava/lang/Object;)V", "getParam", "()Ljava/lang/Object;", "Ljava/lang/Object;", "component1", "copy", "(Ljava/lang/Object;)Lcom/miui/notes/ai/network/request/AIError$AuthTokenError;", "equals", "", RemoteDeviceInfo.MANUFACTURER_OTHER, "", "hashCode", "", "toString", "", "libAiTextWidget_release"}, k = 1, mv = {2, 2, 0}, xi = 48)
    public static final /* data */ class AuthTokenError<ResponseErrorDetail> extends AIError<ResponseErrorDetail> {
        private final ResponseErrorDetail param;

        /* JADX WARN: Multi-variable type inference failed */
        public static /* synthetic */ AuthTokenError copy$default(AuthTokenError authTokenError, Object obj, int i, Object obj2) {
            if ((i & 1) != 0) {
                obj = authTokenError.param;
            }
            return authTokenError.copy(obj);
        }

        public final ResponseErrorDetail component1() {
            return this.param;
        }

        public final AuthTokenError<ResponseErrorDetail> copy(ResponseErrorDetail param) {
            return new AuthTokenError<>(param);
        }

        public boolean equals(Object other) {
            if (this == other) {
                return true;
            }
            return (other instanceof AuthTokenError) && Intrinsics.areEqual(this.param, ((AuthTokenError) other).param);
        }

        public int hashCode() {
            ResponseErrorDetail responseerrordetail = this.param;
            if (responseerrordetail == null) {
                return 0;
            }
            return responseerrordetail.hashCode();
        }

        public String toString() {
            return "AuthTokenError(param=" + this.param + ")";
        }

        public AuthTokenError(ResponseErrorDetail responseerrordetail) {
            super(null);
            this.param = responseerrordetail;
        }

        public final ResponseErrorDetail getParam() {
            return this.param;
        }
    }

    /* JADX INFO: compiled from: AIError.kt */
    @Metadata(d1 = {"\u0000&\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\n\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0000\b\u0086\b\u0018\u0000*\u0004\b\u0001\u0010\u00012\b\u0012\u0004\u0012\u0002H\u00010\u0002B\u000f\u0012\u0006\u0010\u0003\u001a\u00028\u0001¢\u0006\u0004\b\u0004\u0010\u0005J\u000e\u0010\t\u001a\u00028\u0001HÆ\u0003¢\u0006\u0002\u0010\u0007J\u001e\u0010\n\u001a\b\u0012\u0004\u0012\u00028\u00010\u00002\b\b\u0002\u0010\u0003\u001a\u00028\u0001HÆ\u0001¢\u0006\u0002\u0010\u000bJ\u0013\u0010\f\u001a\u00020\r2\b\u0010\u000e\u001a\u0004\u0018\u00010\u000fHÖ\u0003J\t\u0010\u0010\u001a\u00020\u0011HÖ\u0001J\t\u0010\u0012\u001a\u00020\u0013HÖ\u0001R\u0013\u0010\u0003\u001a\u00028\u0001¢\u0006\n\n\u0002\u0010\b\u001a\u0004\b\u0006\u0010\u0007¨\u0006\u0014"}, d2 = {"Lcom/miui/notes/ai/network/request/AIError$NotSupportError;", "ResponseErrorDetail", "Lcom/miui/notes/ai/network/request/AIError;", "param", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "(Ljava/lang/Object;)V", "getParam", "()Ljava/lang/Object;", "Ljava/lang/Object;", "component1", "copy", "(Ljava/lang/Object;)Lcom/miui/notes/ai/network/request/AIError$NotSupportError;", "equals", "", RemoteDeviceInfo.MANUFACTURER_OTHER, "", "hashCode", "", "toString", "", "libAiTextWidget_release"}, k = 1, mv = {2, 2, 0}, xi = 48)
    public static final /* data */ class NotSupportError<ResponseErrorDetail> extends AIError<ResponseErrorDetail> {
        private final ResponseErrorDetail param;

        /* JADX WARN: Multi-variable type inference failed */
        public static /* synthetic */ NotSupportError copy$default(NotSupportError notSupportError, Object obj, int i, Object obj2) {
            if ((i & 1) != 0) {
                obj = notSupportError.param;
            }
            return notSupportError.copy(obj);
        }

        public final ResponseErrorDetail component1() {
            return this.param;
        }

        public final NotSupportError<ResponseErrorDetail> copy(ResponseErrorDetail param) {
            return new NotSupportError<>(param);
        }

        public boolean equals(Object other) {
            if (this == other) {
                return true;
            }
            return (other instanceof NotSupportError) && Intrinsics.areEqual(this.param, ((NotSupportError) other).param);
        }

        public int hashCode() {
            ResponseErrorDetail responseerrordetail = this.param;
            if (responseerrordetail == null) {
                return 0;
            }
            return responseerrordetail.hashCode();
        }

        public String toString() {
            return "NotSupportError(param=" + this.param + ")";
        }

        public NotSupportError(ResponseErrorDetail responseerrordetail) {
            super(null);
            this.param = responseerrordetail;
        }

        public final ResponseErrorDetail getParam() {
            return this.param;
        }
    }

    /* JADX INFO: compiled from: AIError.kt */
    @Metadata(d1 = {"\u0000&\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\n\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0000\b\u0086\b\u0018\u0000*\u0004\b\u0001\u0010\u00012\b\u0012\u0004\u0012\u0002H\u00010\u0002B\u000f\u0012\u0006\u0010\u0003\u001a\u00028\u0001¢\u0006\u0004\b\u0004\u0010\u0005J\u000e\u0010\t\u001a\u00028\u0001HÆ\u0003¢\u0006\u0002\u0010\u0007J\u001e\u0010\n\u001a\b\u0012\u0004\u0012\u00028\u00010\u00002\b\b\u0002\u0010\u0003\u001a\u00028\u0001HÆ\u0001¢\u0006\u0002\u0010\u000bJ\u0013\u0010\f\u001a\u00020\r2\b\u0010\u000e\u001a\u0004\u0018\u00010\u000fHÖ\u0003J\t\u0010\u0010\u001a\u00020\u0011HÖ\u0001J\t\u0010\u0012\u001a\u00020\u0013HÖ\u0001R\u0013\u0010\u0003\u001a\u00028\u0001¢\u0006\n\n\u0002\u0010\b\u001a\u0004\b\u0006\u0010\u0007¨\u0006\u0014"}, d2 = {"Lcom/miui/notes/ai/network/request/AIError$SystemUpdateError;", "ResponseErrorDetail", "Lcom/miui/notes/ai/network/request/AIError;", "param", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "(Ljava/lang/Object;)V", "getParam", "()Ljava/lang/Object;", "Ljava/lang/Object;", "component1", "copy", "(Ljava/lang/Object;)Lcom/miui/notes/ai/network/request/AIError$SystemUpdateError;", "equals", "", RemoteDeviceInfo.MANUFACTURER_OTHER, "", "hashCode", "", "toString", "", "libAiTextWidget_release"}, k = 1, mv = {2, 2, 0}, xi = 48)
    public static final /* data */ class SystemUpdateError<ResponseErrorDetail> extends AIError<ResponseErrorDetail> {
        private final ResponseErrorDetail param;

        /* JADX WARN: Multi-variable type inference failed */
        public static /* synthetic */ SystemUpdateError copy$default(SystemUpdateError systemUpdateError, Object obj, int i, Object obj2) {
            if ((i & 1) != 0) {
                obj = systemUpdateError.param;
            }
            return systemUpdateError.copy(obj);
        }

        public final ResponseErrorDetail component1() {
            return this.param;
        }

        public final SystemUpdateError<ResponseErrorDetail> copy(ResponseErrorDetail param) {
            return new SystemUpdateError<>(param);
        }

        public boolean equals(Object other) {
            if (this == other) {
                return true;
            }
            return (other instanceof SystemUpdateError) && Intrinsics.areEqual(this.param, ((SystemUpdateError) other).param);
        }

        public int hashCode() {
            ResponseErrorDetail responseerrordetail = this.param;
            if (responseerrordetail == null) {
                return 0;
            }
            return responseerrordetail.hashCode();
        }

        public String toString() {
            return "SystemUpdateError(param=" + this.param + ")";
        }

        public SystemUpdateError(ResponseErrorDetail responseerrordetail) {
            super(null);
            this.param = responseerrordetail;
        }

        public final ResponseErrorDetail getParam() {
            return this.param;
        }
    }

    /* JADX INFO: compiled from: AIError.kt */
    @Metadata(d1 = {"\u0000&\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\n\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0000\b\u0086\b\u0018\u0000*\u0004\b\u0001\u0010\u00012\b\u0012\u0004\u0012\u0002H\u00010\u0002B\u000f\u0012\u0006\u0010\u0003\u001a\u00028\u0001¢\u0006\u0004\b\u0004\u0010\u0005J\u000e\u0010\t\u001a\u00028\u0001HÆ\u0003¢\u0006\u0002\u0010\u0007J\u001e\u0010\n\u001a\b\u0012\u0004\u0012\u00028\u00010\u00002\b\b\u0002\u0010\u0003\u001a\u00028\u0001HÆ\u0001¢\u0006\u0002\u0010\u000bJ\u0013\u0010\f\u001a\u00020\r2\b\u0010\u000e\u001a\u0004\u0018\u00010\u000fHÖ\u0003J\t\u0010\u0010\u001a\u00020\u0011HÖ\u0001J\t\u0010\u0012\u001a\u00020\u0013HÖ\u0001R\u0013\u0010\u0003\u001a\u00028\u0001¢\u0006\n\n\u0002\u0010\b\u001a\u0004\b\u0006\u0010\u0007¨\u0006\u0014"}, d2 = {"Lcom/miui/notes/ai/network/request/AIError$LanguageNotSupportError;", "ResponseErrorDetail", "Lcom/miui/notes/ai/network/request/AIError;", "param", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "(Ljava/lang/Object;)V", "getParam", "()Ljava/lang/Object;", "Ljava/lang/Object;", "component1", "copy", "(Ljava/lang/Object;)Lcom/miui/notes/ai/network/request/AIError$LanguageNotSupportError;", "equals", "", RemoteDeviceInfo.MANUFACTURER_OTHER, "", "hashCode", "", "toString", "", "libAiTextWidget_release"}, k = 1, mv = {2, 2, 0}, xi = 48)
    public static final /* data */ class LanguageNotSupportError<ResponseErrorDetail> extends AIError<ResponseErrorDetail> {
        private final ResponseErrorDetail param;

        /* JADX WARN: Multi-variable type inference failed */
        public static /* synthetic */ LanguageNotSupportError copy$default(LanguageNotSupportError languageNotSupportError, Object obj, int i, Object obj2) {
            if ((i & 1) != 0) {
                obj = languageNotSupportError.param;
            }
            return languageNotSupportError.copy(obj);
        }

        public final ResponseErrorDetail component1() {
            return this.param;
        }

        public final LanguageNotSupportError<ResponseErrorDetail> copy(ResponseErrorDetail param) {
            return new LanguageNotSupportError<>(param);
        }

        public boolean equals(Object other) {
            if (this == other) {
                return true;
            }
            return (other instanceof LanguageNotSupportError) && Intrinsics.areEqual(this.param, ((LanguageNotSupportError) other).param);
        }

        public int hashCode() {
            ResponseErrorDetail responseerrordetail = this.param;
            if (responseerrordetail == null) {
                return 0;
            }
            return responseerrordetail.hashCode();
        }

        public String toString() {
            return "LanguageNotSupportError(param=" + this.param + ")";
        }

        public LanguageNotSupportError(ResponseErrorDetail responseerrordetail) {
            super(null);
            this.param = responseerrordetail;
        }

        public final ResponseErrorDetail getParam() {
            return this.param;
        }
    }
}
