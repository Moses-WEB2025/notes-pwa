package com.miui.aiengine.common.cloud;

import android.os.Parcel;
import android.os.Parcelable;
import ch.qos.logback.core.rolling.helper.IntegerTokenConverter;
import com.market.sdk.reflect.ReflectUtilsForMiui;
import com.market.sdk.utils.Language;
import java.net.SocketTimeoutException;
import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.apache.commons.text.StringSubstitutor;
import org.apache.http.conn.ConnectTimeoutException;

/* JADX INFO: compiled from: NetFailedStatParam.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u00006\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\t\n\u0002\b\u0002\n\u0002\u0010\u0003\n\u0000\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0010\u0002\n\u0002\b\u0005\u0018\u0000 \u001b2\u00020\u0001:\u0001\u001bB3\b\u0016\u0012\b\u0010\u0002\u001a\u0004\u0018\u00010\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0005\u0012\u0006\u0010\u0007\u001a\u00020\b\u0012\u0006\u0010\t\u001a\u00020\n¢\u0006\u0004\b\u000b\u0010\fB\u0011\b\u0014\u0012\u0006\u0010\r\u001a\u00020\u000e¢\u0006\u0004\b\u000b\u0010\u000fJ\u0010\u0010\u0014\u001a\u00020\n2\u0006\u0010\u0007\u001a\u00020\bH\u0002J\b\u0010\u0015\u001a\u00020\nH\u0016J\u0018\u0010\u0016\u001a\u00020\u00172\u0006\u0010\u0018\u001a\u00020\u000e2\u0006\u0010\u0019\u001a\u00020\nH\u0016J\b\u0010\u001a\u001a\u00020\u0003H\u0016R\u0013\u0010\u0002\u001a\u0004\u0018\u00010\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0010\u0010\u0011R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0005X\u0082\u0004¢\u0006\u0002\n\u0000R\u0010\u0010\u0012\u001a\u0004\u0018\u00010\u0003X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0013\u001a\u00020\nX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\t\u001a\u00020\nX\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u001c"}, d2 = {"Lcom/miui/aiengine/common/cloud/NetFailedStatParam;", "Landroid/os/Parcelable;", "url", "", "requestStartTime", "", "timeCost", "throwable", "", "retryCount", "", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "(Ljava/lang/String;JJLjava/lang/Throwable;I)V", Language.LA_IN, "Landroid/os/Parcel;", "(Landroid/os/Parcel;)V", "getUrl", "()Ljava/lang/String;", "exceptionName", "resultType", "getResultType", "describeContents", "writeToParcel", "", "parcel", IntegerTokenConverter.CONVERTER_KEY, "toString", "CREATOR", "net_release"}, k = 1, mv = {2, 2, 0}, xi = 48)
public final class NetFailedStatParam implements Parcelable {

    /* JADX INFO: renamed from: CREATOR, reason: from kotlin metadata */
    public static final Companion INSTANCE = new Companion(null);
    private final String exceptionName;
    private final long requestStartTime;
    private final int resultType;
    private final int retryCount;
    private final long timeCost;
    private final String url;

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public final String getUrl() {
        return this.url;
    }

    public NetFailedStatParam(String str, long j, long j2, Throwable throwable, int i) {
        Intrinsics.checkNotNullParameter(throwable, "throwable");
        this.url = str;
        this.requestStartTime = j;
        this.timeCost = j2;
        this.exceptionName = throwable.getClass().getSimpleName();
        this.resultType = getResultType(throwable);
        this.retryCount = i;
    }

    protected NetFailedStatParam(Parcel in) {
        Intrinsics.checkNotNullParameter(in, "in");
        this.url = in.readString();
        this.requestStartTime = in.readLong();
        this.timeCost = in.readLong();
        this.exceptionName = in.readString();
        this.resultType = in.readInt();
        this.retryCount = in.readInt();
    }

    private final int getResultType(Throwable throwable) {
        return ((throwable instanceof ConnectTimeoutException) || (throwable instanceof SocketTimeoutException)) ? 2 : 1;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        Intrinsics.checkNotNullParameter(parcel, "parcel");
        parcel.writeString(this.url);
        parcel.writeLong(this.requestStartTime);
        parcel.writeLong(this.timeCost);
        parcel.writeString(this.exceptionName);
        parcel.writeInt(this.resultType);
        parcel.writeInt(this.retryCount);
    }

    public String toString() {
        return "NetFailedStatParam{url='" + this.url + "', requestStartTime=" + this.requestStartTime + ", timeCost=" + this.timeCost + ", exceptionName='" + this.exceptionName + "', resultType=" + this.resultType + ", retryCount=" + this.retryCount + StringSubstitutor.DEFAULT_VAR_END;
    }

    /* JADX INFO: renamed from: com.miui.aiengine.common.cloud.NetFailedStatParam$CREATOR, reason: from kotlin metadata */
    /* JADX INFO: compiled from: NetFailedStatParam.kt */
    @Metadata(d1 = {"\u0000$\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0011\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\b\u0086\u0003\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u0001B\t\b\u0002¢\u0006\u0004\b\u0003\u0010\u0004J\u0010\u0010\u0005\u001a\u00020\u00022\u0006\u0010\u0006\u001a\u00020\u0007H\u0016J\u001d\u0010\b\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\u00020\t2\u0006\u0010\n\u001a\u00020\u000bH\u0016¢\u0006\u0002\u0010\f¨\u0006\r"}, d2 = {"Lcom/miui/aiengine/common/cloud/NetFailedStatParam$CREATOR;", "Landroid/os/Parcelable$Creator;", "Lcom/miui/aiengine/common/cloud/NetFailedStatParam;", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "()V", "createFromParcel", "parcel", "Landroid/os/Parcel;", "newArray", "", "size", "", "(I)[Lcom/miui/aiengine/common/cloud/NetFailedStatParam;", "net_release"}, k = 1, mv = {2, 2, 0}, xi = 48)
    public static final class Companion implements Parcelable.Creator<NetFailedStatParam> {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public NetFailedStatParam createFromParcel(Parcel parcel) {
            Intrinsics.checkNotNullParameter(parcel, "parcel");
            return new NetFailedStatParam(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public NetFailedStatParam[] newArray(int size) {
            return new NetFailedStatParam[size];
        }
    }
}
