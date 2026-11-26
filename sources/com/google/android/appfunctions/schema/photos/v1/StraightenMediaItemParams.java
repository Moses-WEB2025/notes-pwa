package com.google.android.appfunctions.schema.photos.v1;

import android.net.Uri;
import com.market.sdk.reflect.ReflectUtilsForMiui;
import com.xiaomi.mirror.RemoteDeviceInfo;
import java.util.Objects;
import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: com.google.android.appfunctions:appfunctions-schema@@1.0.7 */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000*\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0006\n\u0002\b\u000f\n\u0002\u0010\b\n\u0000\b\u0007\u0018\u00002\u00020\u0001B7\u0012\n\b\u0002\u0010\u0002\u001a\u0004\u0018\u00010\u0003\u0012\n\b\u0002\u0010\u0004\u001a\u0004\u0018\u00010\u0005\u0012\n\b\u0002\u0010\u0006\u001a\u0004\u0018\u00010\u0007\u0012\n\b\u0002\u0010\b\u001a\u0004\u0018\u00010\t¢\u0006\u0004\b\n\u0010\u000bJ\u0013\u0010\u0016\u001a\u00020\u00072\b\u0010\u0017\u001a\u0004\u0018\u00010\u0001H\u0096\u0002J\b\u0010\u0018\u001a\u00020\u0019H\u0016R\u0013\u0010\u0002\u001a\u0004\u0018\u00010\u0003¢\u0006\b\n\u0000\u001a\u0004\b\f\u0010\rR\u0013\u0010\u0004\u001a\u0004\u0018\u00010\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u000e\u0010\u000fR\u0015\u0010\u0006\u001a\u0004\u0018\u00010\u0007¢\u0006\n\n\u0002\u0010\u0012\u001a\u0004\b\u0010\u0010\u0011R\u0015\u0010\b\u001a\u0004\u0018\u00010\t¢\u0006\n\n\u0002\u0010\u0015\u001a\u0004\b\u0013\u0010\u0014¨\u0006\u001a"}, d2 = {"Lcom/google/android/appfunctions/schema/photos/v1/StraightenMediaItemParams;", "", "mediaItemId", "", "mediaItemUri", "Landroid/net/Uri;", "autoStraighten", "", "straightenDegrees", "", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "(Ljava/lang/String;Landroid/net/Uri;Ljava/lang/Boolean;Ljava/lang/Double;)V", "getMediaItemId", "()Ljava/lang/String;", "getMediaItemUri", "()Landroid/net/Uri;", "getAutoStraighten", "()Ljava/lang/Boolean;", "Ljava/lang/Boolean;", "getStraightenDegrees", "()Ljava/lang/Double;", "Ljava/lang/Double;", "equals", RemoteDeviceInfo.MANUFACTURER_OTHER, "hashCode", "", "java.com.google.android.libraries.llm.appfunctions.appfunctions_schema2.src.com.google.android.appfunctions.schema.photos.v1_v1"}, k = 1, mv = {2, 1, 0}, xi = 48)
public final class StraightenMediaItemParams {
    private final Boolean autoStraighten;
    private final String mediaItemId;
    private final Uri mediaItemUri;
    private final Double straightenDegrees;

    public StraightenMediaItemParams() {
        this(null, null, null, null, 15, null);
    }

    public StraightenMediaItemParams(String str, Uri uri, Boolean bool, Double d) {
        this.mediaItemId = str;
        this.mediaItemUri = uri;
        this.autoStraighten = bool;
        this.straightenDegrees = d;
    }

    public /* synthetic */ StraightenMediaItemParams(String str, Uri uri, Boolean bool, Double d, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(1 == (i & 1) ? null : str, (i & 2) != 0 ? null : uri, (i & 4) != 0 ? null : bool, (i & 8) != 0 ? null : d);
    }

    public boolean equals(Object other) {
        if (!(other instanceof StraightenMediaItemParams)) {
            return false;
        }
        StraightenMediaItemParams straightenMediaItemParams = (StraightenMediaItemParams) other;
        return Intrinsics.areEqual(this.mediaItemId, straightenMediaItemParams.mediaItemId) && Intrinsics.areEqual(this.mediaItemUri, straightenMediaItemParams.mediaItemUri) && Intrinsics.areEqual(this.autoStraighten, straightenMediaItemParams.autoStraighten) && Intrinsics.areEqual(this.straightenDegrees, straightenMediaItemParams.straightenDegrees);
    }

    public final Boolean getAutoStraighten() {
        return this.autoStraighten;
    }

    public final String getMediaItemId() {
        return this.mediaItemId;
    }

    public final Uri getMediaItemUri() {
        return this.mediaItemUri;
    }

    public final Double getStraightenDegrees() {
        return this.straightenDegrees;
    }

    public int hashCode() {
        return Objects.hash(this.mediaItemId, this.mediaItemUri, this.autoStraighten, this.straightenDegrees);
    }
}
