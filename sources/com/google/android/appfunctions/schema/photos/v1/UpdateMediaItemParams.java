package com.google.android.appfunctions.schema.photos.v1;

import com.google.android.appfunctions.schema.types.v1.SetField;
import com.market.sdk.reflect.ReflectUtilsForMiui;
import com.xiaomi.mirror.RemoteDeviceInfo;
import java.util.Objects;
import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: com.google.android.appfunctions:appfunctions-schema@@1.0.7 */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000$\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\f\n\u0002\u0010\b\n\u0000\b\u0007\u0018\u00002\u00020\u0001BI\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0010\b\u0002\u0010\u0004\u001a\n\u0012\u0004\u0012\u00020\u0003\u0018\u00010\u0005\u0012\u0012\b\u0002\u0010\u0006\u001a\f\u0012\u0006\u0012\u0004\u0018\u00010\u0007\u0018\u00010\u0005\u0012\u0012\b\u0002\u0010\b\u001a\f\u0012\u0006\u0012\u0004\u0018\u00010\u0003\u0018\u00010\u0005¢\u0006\u0004\b\t\u0010\nJ\u0013\u0010\u0011\u001a\u00020\u00072\b\u0010\u0012\u001a\u0004\u0018\u00010\u0001H\u0096\u0002J\b\u0010\u0013\u001a\u00020\u0014H\u0016R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\fR\u0019\u0010\u0004\u001a\n\u0012\u0004\u0012\u00020\u0003\u0018\u00010\u0005¢\u0006\b\n\u0000\u001a\u0004\b\r\u0010\u000eR\u001b\u0010\u0006\u001a\f\u0012\u0006\u0012\u0004\u0018\u00010\u0007\u0018\u00010\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u000f\u0010\u000eR\u001b\u0010\b\u001a\f\u0012\u0006\u0012\u0004\u0018\u00010\u0003\u0018\u00010\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u0010\u0010\u000e¨\u0006\u0015"}, d2 = {"Lcom/google/android/appfunctions/schema/photos/v1/UpdateMediaItemParams;", "", "mediaItemId", "", "title", "Lcom/google/android/appfunctions/schema/types/v1/SetField;", "starred", "", "albumId", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "(Ljava/lang/String;Lcom/google/android/appfunctions/schema/types/v1/SetField;Lcom/google/android/appfunctions/schema/types/v1/SetField;Lcom/google/android/appfunctions/schema/types/v1/SetField;)V", "getMediaItemId", "()Ljava/lang/String;", "getTitle", "()Lcom/google/android/appfunctions/schema/types/v1/SetField;", "getStarred", "getAlbumId", "equals", RemoteDeviceInfo.MANUFACTURER_OTHER, "hashCode", "", "java.com.google.android.libraries.llm.appfunctions.appfunctions_schema2.src.com.google.android.appfunctions.schema.photos.v1_v1"}, k = 1, mv = {2, 1, 0}, xi = 48)
public final class UpdateMediaItemParams {
    private final SetField<String> albumId;
    private final String mediaItemId;
    private final SetField<Boolean> starred;
    private final SetField<String> title;

    public UpdateMediaItemParams(String mediaItemId, SetField<String> setField, SetField<Boolean> setField2, SetField<String> setField3) {
        Intrinsics.checkNotNullParameter(mediaItemId, "mediaItemId");
        this.mediaItemId = mediaItemId;
        this.title = setField;
        this.starred = setField2;
        this.albumId = setField3;
    }

    public /* synthetic */ UpdateMediaItemParams(String str, SetField setField, SetField setField2, SetField setField3, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(str, (i & 2) != 0 ? null : setField, (i & 4) != 0 ? null : setField2, (i & 8) != 0 ? null : setField3);
    }

    public boolean equals(Object other) {
        if (!(other instanceof UpdateMediaItemParams)) {
            return false;
        }
        UpdateMediaItemParams updateMediaItemParams = (UpdateMediaItemParams) other;
        return Intrinsics.areEqual(this.mediaItemId, updateMediaItemParams.mediaItemId) && Intrinsics.areEqual(this.title, updateMediaItemParams.title) && Intrinsics.areEqual(this.starred, updateMediaItemParams.starred) && Intrinsics.areEqual(this.albumId, updateMediaItemParams.albumId);
    }

    public final SetField<String> getAlbumId() {
        return this.albumId;
    }

    public final String getMediaItemId() {
        return this.mediaItemId;
    }

    public final SetField<Boolean> getStarred() {
        return this.starred;
    }

    public final SetField<String> getTitle() {
        return this.title;
    }

    public int hashCode() {
        return Objects.hash(this.mediaItemId, this.title, this.starred, this.albumId);
    }
}
