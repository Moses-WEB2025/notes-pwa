package androidx.appfunctions.internal.serializableproxies;

import androidx.appfunctions.metadata.AppFunctionMetadataKt;
import ch.qos.logback.core.CoreConstants;
import com.market.sdk.reflect.ReflectUtilsForMiui;
import com.xiaomi.mirror.RemoteDeviceInfo;
import java.time.ZoneId;
import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: AppFunctionZoneId.kt */
/* JADX INFO: loaded from: classes.dex */
@Metadata(d1 = {"\u0000*\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0003\b\u0087\b\u0018\u0000 \u00122\u00020\u0001:\u0001\u0012B\u000f\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0004\b\u0004\u0010\u0005J\u0006\u0010\b\u001a\u00020\tJ\t\u0010\n\u001a\u00020\u0003HÆ\u0003J\u0013\u0010\u000b\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u0003HÆ\u0001J\u0014\u0010\f\u001a\u00020\r2\b\u0010\u000e\u001a\u0004\u0018\u00010\u0001HÖ\u0083\u0004J\n\u0010\u000f\u001a\u00020\u0010HÖ\u0081\u0004J\n\u0010\u0011\u001a\u00020\u0003HÖ\u0081\u0004R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0006\u0010\u0007¨\u0006\u0013"}, d2 = {"Landroidx/appfunctions/internal/serializableproxies/AppFunctionZoneId;", "", "zoneID", "", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "(Ljava/lang/String;)V", "getZoneID", "()Ljava/lang/String;", "toZoneId", "Ljava/time/ZoneId;", "component1", "copy", "equals", "", RemoteDeviceInfo.MANUFACTURER_OTHER, "hashCode", "", "toString", "Companion", AppFunctionMetadataKt.APP_FUNCTION_NAMESPACE}, k = 1, mv = {2, 1, 0}, xi = 48)
public final /* data */ class AppFunctionZoneId {

    /* JADX INFO: renamed from: Companion, reason: from kotlin metadata */
    public static final Companion INSTANCE = new Companion(null);
    private final String zoneID;

    public static /* synthetic */ AppFunctionZoneId copy$default(AppFunctionZoneId appFunctionZoneId, String str, int i, Object obj) {
        if ((i & 1) != 0) {
            str = appFunctionZoneId.zoneID;
        }
        return appFunctionZoneId.copy(str);
    }

    /* JADX INFO: renamed from: component1, reason: from getter */
    public final String getZoneID() {
        return this.zoneID;
    }

    public final AppFunctionZoneId copy(String zoneID) {
        Intrinsics.checkNotNullParameter(zoneID, "zoneID");
        return new AppFunctionZoneId(zoneID);
    }

    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        return (other instanceof AppFunctionZoneId) && Intrinsics.areEqual(this.zoneID, ((AppFunctionZoneId) other).zoneID);
    }

    public int hashCode() {
        return this.zoneID.hashCode();
    }

    public String toString() {
        return "AppFunctionZoneId(zoneID=" + this.zoneID + CoreConstants.RIGHT_PARENTHESIS_CHAR;
    }

    public AppFunctionZoneId(String zoneID) {
        Intrinsics.checkNotNullParameter(zoneID, "zoneID");
        this.zoneID = zoneID;
    }

    public final String getZoneID() {
        return this.zoneID;
    }

    public final ZoneId toZoneId() {
        ZoneId zoneIdOf = ZoneId.of(this.zoneID);
        Intrinsics.checkNotNullExpressionValue(zoneIdOf, "of(...)");
        return zoneIdOf;
    }

    /* JADX INFO: compiled from: AppFunctionZoneId.kt */
    @Metadata(d1 = {"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\t\b\u0002¢\u0006\u0004\b\u0002\u0010\u0003J\u000e\u0010\u0004\u001a\u00020\u00052\u0006\u0010\u0006\u001a\u00020\u0007¨\u0006\b"}, d2 = {"Landroidx/appfunctions/internal/serializableproxies/AppFunctionZoneId$Companion;", "", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "()V", "fromZoneId", "Landroidx/appfunctions/internal/serializableproxies/AppFunctionZoneId;", "zoneId", "Ljava/time/ZoneId;", AppFunctionMetadataKt.APP_FUNCTION_NAMESPACE}, k = 1, mv = {2, 1, 0}, xi = 48)
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }

        public final AppFunctionZoneId fromZoneId(ZoneId zoneId) {
            Intrinsics.checkNotNullParameter(zoneId, "zoneId");
            String id = zoneId.getId();
            Intrinsics.checkNotNullExpressionValue(id, "getId(...)");
            return new AppFunctionZoneId(id);
        }
    }
}
