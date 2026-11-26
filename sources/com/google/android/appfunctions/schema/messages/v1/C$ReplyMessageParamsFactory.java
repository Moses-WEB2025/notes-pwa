package com.google.android.appfunctions.schema.messages.v1;

import androidx.appfunctions.AppFunctionData;
import androidx.appfunctions.internal.AppFunctionSerializableFactory;
import com.market.sdk.reflect.ReflectUtilsForMiui;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: renamed from: com.google.android.appfunctions.schema.messages.v1.$ReplyMessageParamsFactory, reason: invalid class name */
/* JADX INFO: compiled from: com.google.android.appfunctions:appfunctions-schema@@1.0.7 */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000\u0016\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0003\b\u0007\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u0001B\u0007¢\u0006\u0004\b\u0003\u0010\u0004J\u0010\u0010\u0005\u001a\u00020\u00022\u0006\u0010\u0006\u001a\u00020\u0007H\u0016J\u0010\u0010\b\u001a\u00020\u00072\u0006\u0010\t\u001a\u00020\u0002H\u0016¨\u0006\n"}, d2 = {"com/google/android/appfunctions/schema/messages/v1/$ReplyMessageParamsFactory", "Landroidx/appfunctions/internal/AppFunctionSerializableFactory;", "Lcom/google/android/appfunctions/schema/messages/v1/ReplyMessageParams;", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "()V", "fromAppFunctionData", "appFunctionData", "Landroidx/appfunctions/AppFunctionData;", "toAppFunctionData", "appFunctionSerializable", "java.com.google.android.libraries.llm.appfunctions.appfunctions_schema2.src.com.google.android.appfunctions.schema.messages.v1_v1"}, k = 1, mv = {2, 1, 0}, xi = 48)
public final class C$ReplyMessageParamsFactory implements AppFunctionSerializableFactory<ReplyMessageParams> {
    /* JADX WARN: Can't rename method to resolve collision */
    @Override // androidx.appfunctions.internal.AppFunctionSerializableFactory
    public ReplyMessageParams fromAppFunctionData(AppFunctionData appFunctionData) {
        Intrinsics.checkNotNullParameter(appFunctionData, "appFunctionData");
        AppFunctionData appFunctionDataWithSpec = getAppFunctionDataWithSpec(appFunctionData, "com.google.android.appfunctions.schema.messages.v1.ReplyMessageParams");
        C$AttachmentFactory c$AttachmentFactory = new C$AttachmentFactory();
        String stringOrNull = appFunctionDataWithSpec.getStringOrNull("originalMessageId");
        if (stringOrNull == null) {
            throw new IllegalStateException("Required value was null.");
        }
        String stringOrNull2 = appFunctionDataWithSpec.getStringOrNull("body");
        if (stringOrNull2 == null) {
            throw new IllegalStateException("Required value was null.");
        }
        List<AppFunctionData> appFunctionDataList = appFunctionDataWithSpec.getAppFunctionDataList("messageAttachments");
        if (appFunctionDataList == null) {
            appFunctionDataList = CollectionsKt.emptyList();
        }
        List<AppFunctionData> list = appFunctionDataList;
        ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(list, 10));
        Iterator<T> it = list.iterator();
        while (it.hasNext()) {
            arrayList.add(c$AttachmentFactory.fromAppFunctionData((AppFunctionData) it.next()));
        }
        return new ReplyMessageParams(stringOrNull, stringOrNull2, arrayList);
    }

    @Override // androidx.appfunctions.internal.AppFunctionSerializableFactory
    public AppFunctionData toAppFunctionData(ReplyMessageParams appFunctionSerializable) {
        Intrinsics.checkNotNullParameter(appFunctionSerializable, "appFunctionSerializable");
        C$AttachmentFactory c$AttachmentFactory = new C$AttachmentFactory();
        AppFunctionData.Builder appFunctionDataBuilder = getAppFunctionDataBuilder("com.google.android.appfunctions.schema.messages.v1.ReplyMessageParams");
        appFunctionDataBuilder.setString("originalMessageId", appFunctionSerializable.getOriginalMessageId());
        appFunctionDataBuilder.setString("body", appFunctionSerializable.getBody());
        List<Attachment> messageAttachments = appFunctionSerializable.getMessageAttachments();
        ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(messageAttachments, 10));
        Iterator<T> it = messageAttachments.iterator();
        while (it.hasNext()) {
            arrayList.add(c$AttachmentFactory.toAppFunctionData((Attachment) it.next()));
        }
        appFunctionDataBuilder.setAppFunctionDataList("messageAttachments", arrayList);
        return appFunctionDataBuilder.build();
    }
}
