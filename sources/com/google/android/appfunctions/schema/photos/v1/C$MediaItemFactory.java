package com.google.android.appfunctions.schema.photos.v1;

import androidx.appfunctions.AppFunctionData;
import androidx.appfunctions.internal.AppFunctionSerializableFactory;
import com.google.android.appfunctions.schema.types.v1.C$DateTimeFactory;
import com.market.sdk.reflect.ReflectUtilsForMiui;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: renamed from: com.google.android.appfunctions.schema.photos.v1.$MediaItemFactory, reason: invalid class name */
/* JADX INFO: compiled from: com.google.android.appfunctions:appfunctions-schema@@1.0.7 */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000\u0016\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0003\b\u0007\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u0001B\u0007¢\u0006\u0004\b\u0003\u0010\u0004J\u0010\u0010\u0005\u001a\u00020\u00022\u0006\u0010\u0006\u001a\u00020\u0007H\u0016J\u0010\u0010\b\u001a\u00020\u00072\u0006\u0010\t\u001a\u00020\u0002H\u0016¨\u0006\n"}, d2 = {"com/google/android/appfunctions/schema/photos/v1/$MediaItemFactory", "Landroidx/appfunctions/internal/AppFunctionSerializableFactory;", "Lcom/google/android/appfunctions/schema/photos/v1/MediaItem;", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "()V", "fromAppFunctionData", "appFunctionData", "Landroidx/appfunctions/AppFunctionData;", "toAppFunctionData", "appFunctionSerializable", "java.com.google.android.libraries.llm.appfunctions.appfunctions_schema2.src.com.google.android.appfunctions.schema.photos.v1_v1"}, k = 1, mv = {2, 1, 0}, xi = 48)
public final class C$MediaItemFactory implements AppFunctionSerializableFactory<MediaItem> {
    /* JADX WARN: Can't rename method to resolve collision */
    @Override // androidx.appfunctions.internal.AppFunctionSerializableFactory
    public MediaItem fromAppFunctionData(AppFunctionData appFunctionData) {
        Intrinsics.checkNotNullParameter(appFunctionData, "appFunctionData");
        AppFunctionData appFunctionDataWithSpec = getAppFunctionDataWithSpec(appFunctionData, "com.google.android.appfunctions.schema.photos.v1.MediaItem");
        C$DateTimeFactory c$DateTimeFactory = new C$DateTimeFactory();
        String stringOrNull = appFunctionDataWithSpec.getStringOrNull("id");
        if (stringOrNull == null) {
            throw new IllegalStateException("Required value was null.");
        }
        String stringOrNull2 = appFunctionDataWithSpec.getStringOrNull("type");
        if (stringOrNull2 == null) {
            throw new IllegalStateException("Required value was null.");
        }
        AppFunctionData appFunctionData2 = appFunctionDataWithSpec.getAppFunctionData("dateCreated");
        if (appFunctionData2 != null) {
            return new MediaItem(stringOrNull, stringOrNull2, c$DateTimeFactory.fromAppFunctionData(appFunctionData2), appFunctionDataWithSpec.getStringOrNull("title"), appFunctionDataWithSpec.getBooleanOrNull("starred"), appFunctionDataWithSpec.getStringOrNull("location"), appFunctionDataWithSpec.getStringOrNull("albumId"));
        }
        throw new IllegalStateException("Required value was null.");
    }

    @Override // androidx.appfunctions.internal.AppFunctionSerializableFactory
    public AppFunctionData toAppFunctionData(MediaItem appFunctionSerializable) {
        Intrinsics.checkNotNullParameter(appFunctionSerializable, "appFunctionSerializable");
        C$DateTimeFactory c$DateTimeFactory = new C$DateTimeFactory();
        AppFunctionData.Builder appFunctionDataBuilder = getAppFunctionDataBuilder("com.google.android.appfunctions.schema.photos.v1.MediaItem");
        appFunctionDataBuilder.setString("id", appFunctionSerializable.getId());
        appFunctionDataBuilder.setString("type", appFunctionSerializable.getType());
        appFunctionDataBuilder.setAppFunctionData("dateCreated", c$DateTimeFactory.toAppFunctionData(appFunctionSerializable.getDateCreated()));
        String title = appFunctionSerializable.getTitle();
        if (title != null) {
            appFunctionDataBuilder.setString("title", title);
        }
        Boolean starred = appFunctionSerializable.getStarred();
        if (starred != null) {
            appFunctionDataBuilder.setBoolean("starred", starred.booleanValue());
        }
        String location = appFunctionSerializable.getLocation();
        if (location != null) {
            appFunctionDataBuilder.setString("location", location);
        }
        String albumId = appFunctionSerializable.getAlbumId();
        if (albumId != null) {
            appFunctionDataBuilder.setString("albumId", albumId);
        }
        return appFunctionDataBuilder.build();
    }
}
