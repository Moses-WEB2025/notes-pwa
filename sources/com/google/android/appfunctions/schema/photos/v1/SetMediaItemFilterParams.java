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
@Metadata(d1 = {"\u0000(\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u000b\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0002\b\u0007\u0018\u0000 \u00152\u00020\u0001:\u0001\u0015B'\u0012\n\b\u0002\u0010\u0002\u001a\u0004\u0018\u00010\u0003\u0012\n\b\u0002\u0010\u0004\u001a\u0004\u0018\u00010\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0003¢\u0006\u0004\b\u0007\u0010\bJ\u0013\u0010\u0010\u001a\u00020\u00112\b\u0010\u0012\u001a\u0004\u0018\u00010\u0001H\u0096\u0002J\b\u0010\u0013\u001a\u00020\u0014H\u0016R\u0013\u0010\u0002\u001a\u0004\u0018\u00010\u0003¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\nR\u0013\u0010\u0004\u001a\u0004\u0018\u00010\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\fR\u001c\u0010\u0006\u001a\u00020\u00038\u0006X\u0087\u0004¢\u0006\u000e\n\u0000\u0012\u0004\b\r\u0010\u000e\u001a\u0004\b\u000f\u0010\n¨\u0006\u0016"}, d2 = {"Lcom/google/android/appfunctions/schema/photos/v1/SetMediaItemFilterParams;", "", "mediaItemId", "", "mediaItemUri", "Landroid/net/Uri;", "filter", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "(Ljava/lang/String;Landroid/net/Uri;Ljava/lang/String;)V", "getMediaItemId", "()Ljava/lang/String;", "getMediaItemUri", "()Landroid/net/Uri;", "getFilter$annotations", "()V", "getFilter", "equals", "", RemoteDeviceInfo.MANUFACTURER_OTHER, "hashCode", "", "Companion", "java.com.google.android.libraries.llm.appfunctions.appfunctions_schema2.src.com.google.android.appfunctions.schema.photos.v1_v1"}, k = 1, mv = {2, 1, 0}, xi = 48)
public final class SetMediaItemFilterParams {
    public static final String FILTER_TYPE_AUTO = "AUTO";
    public static final String FILTER_TYPE_COOL = "COOL";
    public static final String FILTER_TYPE_GRAYSCALE = "GRAYSCALE";
    public static final String FILTER_TYPE_NATURAL = "NATURAL";
    public static final String FILTER_TYPE_ORIGINAL = "ORIGINAL";
    public static final String FILTER_TYPE_WARM = "WARM";
    private final String filter;
    private final String mediaItemId;
    private final Uri mediaItemUri;

    public SetMediaItemFilterParams(String str, Uri uri, String filter) {
        Intrinsics.checkNotNullParameter(filter, "filter");
        this.mediaItemId = str;
        this.mediaItemUri = uri;
        this.filter = filter;
    }

    public /* synthetic */ SetMediaItemFilterParams(String str, Uri uri, String str2, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(1 == (i & 1) ? null : str, (i & 2) != 0 ? null : uri, str2);
    }

    public boolean equals(Object other) {
        if (!(other instanceof SetMediaItemFilterParams)) {
            return false;
        }
        SetMediaItemFilterParams setMediaItemFilterParams = (SetMediaItemFilterParams) other;
        return Intrinsics.areEqual(this.mediaItemId, setMediaItemFilterParams.mediaItemId) && Intrinsics.areEqual(this.mediaItemUri, setMediaItemFilterParams.mediaItemUri) && Intrinsics.areEqual(this.filter, setMediaItemFilterParams.filter);
    }

    public final String getFilter() {
        return this.filter;
    }

    public final String getMediaItemId() {
        return this.mediaItemId;
    }

    public final Uri getMediaItemUri() {
        return this.mediaItemUri;
    }

    public int hashCode() {
        return Objects.hash(this.mediaItemId, this.mediaItemUri, this.filter);
    }
}
