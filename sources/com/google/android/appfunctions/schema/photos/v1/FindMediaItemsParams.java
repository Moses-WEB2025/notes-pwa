package com.google.android.appfunctions.schema.photos.v1;

import com.google.android.appfunctions.schema.types.v1.DateTime;
import com.market.sdk.reflect.ReflectUtilsForMiui;
import com.xiaomi.mirror.RemoteDeviceInfo;
import java.util.Objects;
import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import miuix.smartaction.SmartAction;

/* JADX INFO: compiled from: com.google.android.appfunctions:appfunctions-schema@@1.0.7 */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000*\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0012\b\u0007\u0018\u00002\u00020\u0001BW\u0012\n\b\u0002\u0010\u0002\u001a\u0004\u0018\u00010\u0003\u0012\n\b\u0002\u0010\u0004\u001a\u0004\u0018\u00010\u0003\u0012\n\b\u0002\u0010\u0005\u001a\u0004\u0018\u00010\u0006\u0012\n\b\u0002\u0010\u0007\u001a\u0004\u0018\u00010\u0006\u0012\n\b\u0002\u0010\b\u001a\u0004\u0018\u00010\t\u0012\n\b\u0002\u0010\n\u001a\u0004\u0018\u00010\u0006\u0012\u0006\u0010\u000b\u001a\u00020\f¢\u0006\u0004\b\r\u0010\u000eJ\u0013\u0010\u001b\u001a\u00020\t2\b\u0010\u001c\u001a\u0004\u0018\u00010\u0001H\u0096\u0002J\b\u0010\u001d\u001a\u00020\fH\u0016R\u0013\u0010\u0002\u001a\u0004\u0018\u00010\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u000f\u0010\u0010R\u0013\u0010\u0004\u001a\u0004\u0018\u00010\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0011\u0010\u0010R\u0013\u0010\u0005\u001a\u0004\u0018\u00010\u0006¢\u0006\b\n\u0000\u001a\u0004\b\u0012\u0010\u0013R\u0013\u0010\u0007\u001a\u0004\u0018\u00010\u0006¢\u0006\b\n\u0000\u001a\u0004\b\u0014\u0010\u0013R\u0015\u0010\b\u001a\u0004\u0018\u00010\t¢\u0006\n\n\u0002\u0010\u0017\u001a\u0004\b\u0015\u0010\u0016R\u0013\u0010\n\u001a\u0004\u0018\u00010\u0006¢\u0006\b\n\u0000\u001a\u0004\b\u0018\u0010\u0013R\u0011\u0010\u000b\u001a\u00020\f¢\u0006\b\n\u0000\u001a\u0004\b\u0019\u0010\u001a¨\u0006\u001e"}, d2 = {"Lcom/google/android/appfunctions/schema/photos/v1/FindMediaItemsParams;", "", "startTime", "Lcom/google/android/appfunctions/schema/types/v1/DateTime;", "endTime", SmartAction.Feature.QUERY, "", "location", "starred", "", "albumId", "maxCount", "", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "(Lcom/google/android/appfunctions/schema/types/v1/DateTime;Lcom/google/android/appfunctions/schema/types/v1/DateTime;Ljava/lang/String;Ljava/lang/String;Ljava/lang/Boolean;Ljava/lang/String;I)V", "getStartTime", "()Lcom/google/android/appfunctions/schema/types/v1/DateTime;", "getEndTime", "getQuery", "()Ljava/lang/String;", "getLocation", "getStarred", "()Ljava/lang/Boolean;", "Ljava/lang/Boolean;", "getAlbumId", "getMaxCount", "()I", "equals", RemoteDeviceInfo.MANUFACTURER_OTHER, "hashCode", "java.com.google.android.libraries.llm.appfunctions.appfunctions_schema2.src.com.google.android.appfunctions.schema.photos.v1_v1"}, k = 1, mv = {2, 1, 0}, xi = 48)
public final class FindMediaItemsParams {
    private final String albumId;
    private final DateTime endTime;
    private final String location;
    private final int maxCount;
    private final String query;
    private final Boolean starred;
    private final DateTime startTime;

    public FindMediaItemsParams(DateTime dateTime, DateTime dateTime2, String str, String str2, Boolean bool, String str3, int i) {
        this.startTime = dateTime;
        this.endTime = dateTime2;
        this.query = str;
        this.location = str2;
        this.starred = bool;
        this.albumId = str3;
        this.maxCount = i;
    }

    public /* synthetic */ FindMediaItemsParams(DateTime dateTime, DateTime dateTime2, String str, String str2, Boolean bool, String str3, int i, int i2, DefaultConstructorMarker defaultConstructorMarker) {
        this(1 == (i2 & 1) ? null : dateTime, (i2 & 2) != 0 ? null : dateTime2, (i2 & 4) != 0 ? null : str, (i2 & 8) != 0 ? null : str2, (i2 & 16) != 0 ? null : bool, (i2 & 32) != 0 ? null : str3, i);
    }

    public boolean equals(Object other) {
        if (!(other instanceof FindMediaItemsParams)) {
            return false;
        }
        FindMediaItemsParams findMediaItemsParams = (FindMediaItemsParams) other;
        return Intrinsics.areEqual(this.startTime, findMediaItemsParams.startTime) && Intrinsics.areEqual(this.endTime, findMediaItemsParams.endTime) && Intrinsics.areEqual(this.query, findMediaItemsParams.query) && Intrinsics.areEqual(this.location, findMediaItemsParams.location) && Intrinsics.areEqual(this.starred, findMediaItemsParams.starred) && Intrinsics.areEqual(this.albumId, findMediaItemsParams.albumId) && this.maxCount == findMediaItemsParams.maxCount;
    }

    public final String getAlbumId() {
        return this.albumId;
    }

    public final DateTime getEndTime() {
        return this.endTime;
    }

    public final String getLocation() {
        return this.location;
    }

    public final int getMaxCount() {
        return this.maxCount;
    }

    public final String getQuery() {
        return this.query;
    }

    public final Boolean getStarred() {
        return this.starred;
    }

    public final DateTime getStartTime() {
        return this.startTime;
    }

    public int hashCode() {
        return Objects.hash(this.startTime, this.endTime, this.query, this.location, this.starred, this.albumId, Integer.valueOf(this.maxCount));
    }
}
