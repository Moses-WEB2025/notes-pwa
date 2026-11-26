package com.google.android.appfunctions.schema.memory.v1;

import androidx.appfunctions.AppFunctionData;
import androidx.appfunctions.internal.AppFunctionSerializableFactory;
import com.google.android.appfunctions.schema.types.v1.C$DateTimeFactory;
import com.google.android.appfunctions.schema.types.v1.DateTime;
import com.market.sdk.reflect.ReflectUtilsForMiui;
import com.miui.handwriting.common.tools.constants.Creation;
import com.miui.notes.basefeature.NotesNormalConstants;
import java.util.List;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: renamed from: com.google.android.appfunctions.schema.memory.v1.$MemoryItemFactory, reason: invalid class name */
/* JADX INFO: compiled from: com.google.android.appfunctions:appfunctions-schema@@1.0.7 */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000\u0016\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0003\b\u0007\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u0001B\u0007¢\u0006\u0004\b\u0003\u0010\u0004J\u0010\u0010\u0005\u001a\u00020\u00022\u0006\u0010\u0006\u001a\u00020\u0007H\u0016J\u0010\u0010\b\u001a\u00020\u00072\u0006\u0010\t\u001a\u00020\u0002H\u0016¨\u0006\n"}, d2 = {"com/google/android/appfunctions/schema/memory/v1/$MemoryItemFactory", "Landroidx/appfunctions/internal/AppFunctionSerializableFactory;", "Lcom/google/android/appfunctions/schema/memory/v1/MemoryItem;", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "()V", "fromAppFunctionData", "appFunctionData", "Landroidx/appfunctions/AppFunctionData;", "toAppFunctionData", "appFunctionSerializable", "java.com.google.android.libraries.llm.appfunctions.appfunctions_schema2.src.com.google.android.appfunctions.schema.memory.v1_v1"}, k = 1, mv = {2, 1, 0}, xi = 48)
public final class C$MemoryItemFactory implements AppFunctionSerializableFactory<MemoryItem> {
    /* JADX WARN: Can't rename method to resolve collision */
    @Override // androidx.appfunctions.internal.AppFunctionSerializableFactory
    public MemoryItem fromAppFunctionData(AppFunctionData appFunctionData) {
        Intrinsics.checkNotNullParameter(appFunctionData, "appFunctionData");
        AppFunctionData appFunctionDataWithSpec = getAppFunctionDataWithSpec(appFunctionData, "com.google.android.appfunctions.schema.memory.v1.MemoryItem");
        C$DateTimeFactory c$DateTimeFactory = new C$DateTimeFactory();
        String stringOrNull = appFunctionDataWithSpec.getStringOrNull("id");
        if (stringOrNull == null) {
            throw new IllegalStateException("Required value was null.");
        }
        String stringOrNull2 = appFunctionDataWithSpec.getStringOrNull("title");
        String stringOrNull3 = appFunctionDataWithSpec.getStringOrNull(NotesNormalConstants.SUMMARY_TABLE_NAME);
        String stringOrNull4 = appFunctionDataWithSpec.getStringOrNull(Creation.TEXT_CONTENT);
        if (stringOrNull4 == null) {
            throw new IllegalStateException("Required value was null.");
        }
        List<String> stringList = appFunctionDataWithSpec.getStringList("keywords");
        if (stringList == null) {
            stringList = CollectionsKt.emptyList();
        }
        List<String> list = stringList;
        AppFunctionData appFunctionData2 = appFunctionDataWithSpec.getAppFunctionData("createdAt");
        if (appFunctionData2 == null) {
            throw new IllegalStateException("Required value was null.");
        }
        DateTime dateTimeFromAppFunctionData = c$DateTimeFactory.fromAppFunctionData(appFunctionData2);
        AppFunctionData appFunctionData3 = appFunctionDataWithSpec.getAppFunctionData("lastUpdatedAt");
        if (appFunctionData3 != null) {
            return new MemoryItem(stringOrNull, stringOrNull2, stringOrNull3, stringOrNull4, list, dateTimeFromAppFunctionData, c$DateTimeFactory.fromAppFunctionData(appFunctionData3), appFunctionDataWithSpec.getStringOrNull("additionalMetadataJson"));
        }
        throw new IllegalStateException("Required value was null.");
    }

    @Override // androidx.appfunctions.internal.AppFunctionSerializableFactory
    public AppFunctionData toAppFunctionData(MemoryItem appFunctionSerializable) {
        Intrinsics.checkNotNullParameter(appFunctionSerializable, "appFunctionSerializable");
        C$DateTimeFactory c$DateTimeFactory = new C$DateTimeFactory();
        AppFunctionData.Builder appFunctionDataBuilder = getAppFunctionDataBuilder("com.google.android.appfunctions.schema.memory.v1.MemoryItem");
        appFunctionDataBuilder.setString("id", appFunctionSerializable.getId());
        String title = appFunctionSerializable.getTitle();
        if (title != null) {
            appFunctionDataBuilder.setString("title", title);
        }
        String summary = appFunctionSerializable.getSummary();
        if (summary != null) {
            appFunctionDataBuilder.setString(NotesNormalConstants.SUMMARY_TABLE_NAME, summary);
        }
        appFunctionDataBuilder.setString(Creation.TEXT_CONTENT, appFunctionSerializable.getTextContent());
        appFunctionDataBuilder.setStringList("keywords", appFunctionSerializable.getKeywords());
        appFunctionDataBuilder.setAppFunctionData("createdAt", c$DateTimeFactory.toAppFunctionData(appFunctionSerializable.getCreatedAt()));
        appFunctionDataBuilder.setAppFunctionData("lastUpdatedAt", c$DateTimeFactory.toAppFunctionData(appFunctionSerializable.getLastUpdatedAt()));
        String additionalMetadataJson = appFunctionSerializable.getAdditionalMetadataJson();
        if (additionalMetadataJson != null) {
            appFunctionDataBuilder.setString("additionalMetadataJson", additionalMetadataJson);
        }
        return appFunctionDataBuilder.build();
    }
}
