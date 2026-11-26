package androidx.appfunctions;

import android.net.Uri;
import androidx.appfunctions.metadata.AppFunctionMetadataKt;
import ch.qos.logback.core.CoreConstants;
import com.market.sdk.reflect.ReflectUtilsForMiui;
import com.xiaomi.mirror.RemoteDeviceInfo;
import com.xiaomi.mirror.synergy.CallMethod;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.Objects;
import kotlin.Metadata;
import kotlin.annotation.AnnotationRetention;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: AppFunctionUriGrant.kt */
/* JADX INFO: loaded from: classes.dex */
@Metadata(d1 = {"\u0000(\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0007\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0002\b\u0003\b\u0007\u0018\u0000 \u00132\u00020\u0001:\u0002\u0012\u0013B\u0017\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005¢\u0006\u0004\b\u0006\u0010\u0007J\u0013\u0010\f\u001a\u00020\r2\b\u0010\u000e\u001a\u0004\u0018\u00010\u0001H\u0096\u0002J\b\u0010\u000f\u001a\u00020\u0005H\u0016J\b\u0010\u0010\u001a\u00020\u0011H\u0016R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\b\u0010\tR\u0011\u0010\u0004\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\n\u0010\u000b¨\u0006\u0014"}, d2 = {"Landroidx/appfunctions/AppFunctionUriGrant;", "", CallMethod.ARG_URI, "Landroid/net/Uri;", "modeFlags", "", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "(Landroid/net/Uri;I)V", "getUri", "()Landroid/net/Uri;", "getModeFlags", "()I", "equals", "", RemoteDeviceInfo.MANUFACTURER_OTHER, "hashCode", "toString", "", "GrantUriMode", "Companion", AppFunctionMetadataKt.APP_FUNCTION_NAMESPACE}, k = 1, mv = {2, 1, 0}, xi = 48)
public final class AppFunctionUriGrant {
    private static final Companion Companion = new Companion(null);
    private final int modeFlags;
    private final Uri uri;

    /* JADX INFO: compiled from: AppFunctionUriGrant.kt */
    @Metadata(d1 = {"\u0000\n\n\u0002\u0018\u0002\n\u0002\u0010\u001b\n\u0000\b\u0081\u0002\u0018\u00002\u00020\u0001B\u0000¨\u0006\u0002"}, d2 = {"Landroidx/appfunctions/AppFunctionUriGrant$GrantUriMode;", "", AppFunctionMetadataKt.APP_FUNCTION_NAMESPACE}, k = 1, mv = {2, 1, 0}, xi = 48)
    @Retention(RetentionPolicy.SOURCE)
    @kotlin.annotation.Retention(AnnotationRetention.SOURCE)
    public @interface GrantUriMode {
    }

    public AppFunctionUriGrant(Uri uri, int i) {
        Intrinsics.checkNotNullParameter(uri, "uri");
        this.uri = uri;
        this.modeFlags = i;
        if (!Companion.isAccessUriMode(i)) {
            throw new IllegalArgumentException("Must set either FLAG_GRANT_READ_URI_PERMISSION or FLAG_GRANT_WRITE_URI_PERMISSION to specify the access mode".toString());
        }
    }

    public final Uri getUri() {
        return this.uri;
    }

    public final int getModeFlags() {
        return this.modeFlags;
    }

    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof AppFunctionUriGrant)) {
            return false;
        }
        AppFunctionUriGrant appFunctionUriGrant = (AppFunctionUriGrant) other;
        return Intrinsics.areEqual(this.uri, appFunctionUriGrant.uri) && this.modeFlags == appFunctionUriGrant.modeFlags;
    }

    public int hashCode() {
        return Objects.hash(this.uri, Integer.valueOf(this.modeFlags));
    }

    public String toString() {
        return "AppFunctionUriGrant(uri=" + this.uri + ", modeFlags=" + this.modeFlags + CoreConstants.RIGHT_PARENTHESIS_CHAR;
    }

    /* JADX INFO: compiled from: AppFunctionUriGrant.kt */
    @Metadata(d1 = {"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\b\n\u0000\b\u0082\u0003\u0018\u00002\u00020\u0001B\t\b\u0002¢\u0006\u0004\b\u0002\u0010\u0003J\u0010\u0010\u0004\u001a\u00020\u00052\u0006\u0010\u0006\u001a\u00020\u0007H\u0002¨\u0006\b"}, d2 = {"Landroidx/appfunctions/AppFunctionUriGrant$Companion;", "", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "()V", "isAccessUriMode", "", "modeFlags", "", AppFunctionMetadataKt.APP_FUNCTION_NAMESPACE}, k = 1, mv = {2, 1, 0}, xi = 48)
    private static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        /* JADX INFO: Access modifiers changed from: private */
        public final boolean isAccessUriMode(int modeFlags) {
            return (modeFlags & 3) != 0;
        }

        private Companion() {
        }
    }
}
