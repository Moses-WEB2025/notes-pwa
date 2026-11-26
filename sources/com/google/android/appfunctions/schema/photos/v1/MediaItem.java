package com.google.android.appfunctions.schema.photos.v1;

import com.google.android.appfunctions.schema.types.v1.DateTime;
import com.market.sdk.reflect.ReflectUtilsForMiui;
import com.xiaomi.mirror.RemoteDeviceInfo;
import java.util.Objects;
import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: com.google.android.appfunctions:appfunctions-schema@@1.0.7 */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000*\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0014\n\u0002\u0010\b\n\u0002\b\u0002\b\u0007\u0018\u0000 \u001f2\u00020\u0001:\u0001\u001fBO\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0003\u0012\u0006\u0010\u0005\u001a\u00020\u0006\u0012\n\b\u0002\u0010\u0007\u001a\u0004\u0018\u00010\u0003\u0012\n\b\u0002\u0010\b\u001a\u0004\u0018\u00010\t\u0012\n\b\u0002\u0010\n\u001a\u0004\u0018\u00010\u0003\u0012\n\b\u0002\u0010\u000b\u001a\u0004\u0018\u00010\u0003¢\u0006\u0004\b\f\u0010\rJ\u0013\u0010\u001b\u001a\u00020\t2\b\u0010\u001c\u001a\u0004\u0018\u00010\u0001H\u0096\u0002J\b\u0010\u001d\u001a\u00020\u001eH\u0016R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u000e\u0010\u000fR\u001c\u0010\u0004\u001a\u00020\u00038\u0006X\u0087\u0004¢\u0006\u000e\n\u0000\u0012\u0004\b\u0010\u0010\u0011\u001a\u0004\b\u0012\u0010\u000fR\u0011\u0010\u0005\u001a\u00020\u0006¢\u0006\b\n\u0000\u001a\u0004\b\u0013\u0010\u0014R\u0013\u0010\u0007\u001a\u0004\u0018\u00010\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0015\u0010\u000fR\u0015\u0010\b\u001a\u0004\u0018\u00010\t¢\u0006\n\n\u0002\u0010\u0018\u001a\u0004\b\u0016\u0010\u0017R\u0013\u0010\n\u001a\u0004\u0018\u00010\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0019\u0010\u000fR\u0013\u0010\u000b\u001a\u0004\u0018\u00010\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u001a\u0010\u000f¨\u0006 "}, d2 = {"Lcom/google/android/appfunctions/schema/photos/v1/MediaItem;", "", "id", "", "type", "dateCreated", "Lcom/google/android/appfunctions/schema/types/v1/DateTime;", "title", "starred", "", "location", "albumId", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "(Ljava/lang/String;Ljava/lang/String;Lcom/google/android/appfunctions/schema/types/v1/DateTime;Ljava/lang/String;Ljava/lang/Boolean;Ljava/lang/String;Ljava/lang/String;)V", "getId", "()Ljava/lang/String;", "getType$annotations", "()V", "getType", "getDateCreated", "()Lcom/google/android/appfunctions/schema/types/v1/DateTime;", "getTitle", "getStarred", "()Ljava/lang/Boolean;", "Ljava/lang/Boolean;", "getLocation", "getAlbumId", "equals", RemoteDeviceInfo.MANUFACTURER_OTHER, "hashCode", "", "Companion", "java.com.google.android.libraries.llm.appfunctions.appfunctions_schema2.src.com.google.android.appfunctions.schema.photos.v1_v1"}, k = 1, mv = {2, 1, 0}, xi = 48)
public final class MediaItem {
    public static final String ASSIST_FOREGROUND_TYPE = "com.google.android.appfunctions.schema.photos.v1.MediaItemForeground";
    public static final String MEDIA_ITEM_TYPE_PHOTO = "PHOTO";
    public static final String MEDIA_ITEM_TYPE_VIDEO = "VIDEO";
    private final String albumId;
    private final DateTime dateCreated;
    private final String id;
    private final String location;
    private final Boolean starred;
    private final String title;
    private final String type;

    public MediaItem(String id, String type, DateTime dateCreated, String str, Boolean bool, String str2, String str3) {
        Intrinsics.checkNotNullParameter(id, "id");
        Intrinsics.checkNotNullParameter(type, "type");
        Intrinsics.checkNotNullParameter(dateCreated, "dateCreated");
        this.id = id;
        this.type = type;
        this.dateCreated = dateCreated;
        this.title = str;
        this.starred = bool;
        this.location = str2;
        this.albumId = str3;
    }

    public /* synthetic */ MediaItem(String str, String str2, DateTime dateTime, String str3, Boolean bool, String str4, String str5, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(str, str2, dateTime, (i & 8) != 0 ? null : str3, (i & 16) != 0 ? null : bool, (i & 32) != 0 ? null : str4, (i & 64) != 0 ? null : str5);
    }

    public boolean equals(Object other) {
        if (!(other instanceof MediaItem)) {
            return false;
        }
        MediaItem mediaItem = (MediaItem) other;
        return Intrinsics.areEqual(this.id, mediaItem.id) && Intrinsics.areEqual(this.type, mediaItem.type) && Intrinsics.areEqual(this.dateCreated, mediaItem.dateCreated) && Intrinsics.areEqual(this.title, mediaItem.title) && Intrinsics.areEqual(this.starred, mediaItem.starred) && Intrinsics.areEqual(this.location, mediaItem.location) && Intrinsics.areEqual(this.albumId, mediaItem.albumId);
    }

    public final String getAlbumId() {
        return this.albumId;
    }

    public final DateTime getDateCreated() {
        return this.dateCreated;
    }

    public final String getId() {
        return this.id;
    }

    public final String getLocation() {
        return this.location;
    }

    public final Boolean getStarred() {
        return this.starred;
    }

    public final String getTitle() {
        return this.title;
    }

    public final String getType() {
        return this.type;
    }

    public int hashCode() {
        return Objects.hash(this.id, this.type, this.dateCreated, this.title, this.starred, this.location, this.albumId);
    }
}
