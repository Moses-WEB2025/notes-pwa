package androidx.appfunctions.internal.serializableproxies;

import androidx.appfunctions.metadata.AppFunctionMetadataKt;
import ch.qos.logback.core.CoreConstants;
import com.market.sdk.reflect.ReflectUtilsForMiui;
import com.xiaomi.mirror.RemoteDeviceInfo;
import java.time.Instant;
import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: AppFunctionInstant.kt */
/* JADX INFO: loaded from: classes.dex */
@Metadata(d1 = {"\u00000\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\t\n\u0000\n\u0002\u0010\b\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0002\b\u0002\b\u0087\b\u0018\u0000 \u00172\u00020\u0001:\u0001\u0017B\u0017\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005¢\u0006\u0004\b\u0006\u0010\u0007J\u0006\u0010\f\u001a\u00020\rJ\t\u0010\u000e\u001a\u00020\u0003HÆ\u0003J\t\u0010\u000f\u001a\u00020\u0005HÆ\u0003J\u001d\u0010\u0010\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u0005HÆ\u0001J\u0014\u0010\u0011\u001a\u00020\u00122\b\u0010\u0013\u001a\u0004\u0018\u00010\u0001HÖ\u0083\u0004J\n\u0010\u0014\u001a\u00020\u0005HÖ\u0081\u0004J\n\u0010\u0015\u001a\u00020\u0016HÖ\u0081\u0004R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\b\u0010\tR\u0011\u0010\u0004\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\n\u0010\u000b¨\u0006\u0018"}, d2 = {"Landroidx/appfunctions/internal/serializableproxies/AppFunctionInstant;", "", "epochSecond", "", "nanoAdjustment", "", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "(JI)V", "getEpochSecond", "()J", "getNanoAdjustment", "()I", "toInstant", "Ljava/time/Instant;", "component1", "component2", "copy", "equals", "", RemoteDeviceInfo.MANUFACTURER_OTHER, "hashCode", "toString", "", "Companion", AppFunctionMetadataKt.APP_FUNCTION_NAMESPACE}, k = 1, mv = {2, 1, 0}, xi = 48)
public final /* data */ class AppFunctionInstant {

    /* JADX INFO: renamed from: Companion, reason: from kotlin metadata */
    public static final Companion INSTANCE = new Companion(null);
    private final long epochSecond;
    private final int nanoAdjustment;

    public static /* synthetic */ AppFunctionInstant copy$default(AppFunctionInstant appFunctionInstant, long j, int i, int i2, Object obj) {
        if ((i2 & 1) != 0) {
            j = appFunctionInstant.epochSecond;
        }
        if ((i2 & 2) != 0) {
            i = appFunctionInstant.nanoAdjustment;
        }
        return appFunctionInstant.copy(j, i);
    }

    /* JADX INFO: renamed from: component1, reason: from getter */
    public final long getEpochSecond() {
        return this.epochSecond;
    }

    /* JADX INFO: renamed from: component2, reason: from getter */
    public final int getNanoAdjustment() {
        return this.nanoAdjustment;
    }

    public final AppFunctionInstant copy(long epochSecond, int nanoAdjustment) {
        return new AppFunctionInstant(epochSecond, nanoAdjustment);
    }

    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof AppFunctionInstant)) {
            return false;
        }
        AppFunctionInstant appFunctionInstant = (AppFunctionInstant) other;
        return this.epochSecond == appFunctionInstant.epochSecond && this.nanoAdjustment == appFunctionInstant.nanoAdjustment;
    }

    public int hashCode() {
        return (Long.hashCode(this.epochSecond) * 31) + Integer.hashCode(this.nanoAdjustment);
    }

    public String toString() {
        return "AppFunctionInstant(epochSecond=" + this.epochSecond + ", nanoAdjustment=" + this.nanoAdjustment + CoreConstants.RIGHT_PARENTHESIS_CHAR;
    }

    public AppFunctionInstant(long j, int i) {
        this.epochSecond = j;
        this.nanoAdjustment = i;
    }

    public final long getEpochSecond() {
        return this.epochSecond;
    }

    public final int getNanoAdjustment() {
        return this.nanoAdjustment;
    }

    public final Instant toInstant() {
        Instant instantOfEpochSecond = Instant.ofEpochSecond(this.epochSecond, this.nanoAdjustment);
        Intrinsics.checkNotNullExpressionValue(instantOfEpochSecond, "ofEpochSecond(...)");
        return instantOfEpochSecond;
    }

    /* JADX INFO: compiled from: AppFunctionInstant.kt */
    @Metadata(d1 = {"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\t\b\u0002¢\u0006\u0004\b\u0002\u0010\u0003J\u000e\u0010\u0004\u001a\u00020\u00052\u0006\u0010\u0006\u001a\u00020\u0007¨\u0006\b"}, d2 = {"Landroidx/appfunctions/internal/serializableproxies/AppFunctionInstant$Companion;", "", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "()V", "fromInstant", "Landroidx/appfunctions/internal/serializableproxies/AppFunctionInstant;", "instant", "Ljava/time/Instant;", AppFunctionMetadataKt.APP_FUNCTION_NAMESPACE}, k = 1, mv = {2, 1, 0}, xi = 48)
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }

        public final AppFunctionInstant fromInstant(Instant instant) {
            Intrinsics.checkNotNullParameter(instant, "instant");
            return new AppFunctionInstant(instant.getEpochSecond(), instant.getNano());
        }
    }
}
