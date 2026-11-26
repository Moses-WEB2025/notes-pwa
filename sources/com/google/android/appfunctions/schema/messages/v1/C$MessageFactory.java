package com.google.android.appfunctions.schema.messages.v1;

import androidx.appfunctions.AppFunctionData;
import androidx.appfunctions.internal.AppFunctionSerializableFactory;
import com.google.android.appfunctions.schema.types.v1.C$DateTimeFactory;
import com.google.android.appfunctions.schema.types.v1.DateTime;
import com.market.sdk.reflect.ReflectUtilsForMiui;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: renamed from: com.google.android.appfunctions.schema.messages.v1.$MessageFactory, reason: invalid class name */
/* JADX INFO: compiled from: com.google.android.appfunctions:appfunctions-schema@@1.0.7 */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000\u0016\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0003\b\u0007\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u0001B\u0007¢\u0006\u0004\b\u0003\u0010\u0004J\u0010\u0010\u0005\u001a\u00020\u00022\u0006\u0010\u0006\u001a\u00020\u0007H\u0016J\u0010\u0010\b\u001a\u00020\u00072\u0006\u0010\t\u001a\u00020\u0002H\u0016¨\u0006\n"}, d2 = {"com/google/android/appfunctions/schema/messages/v1/$MessageFactory", "Landroidx/appfunctions/internal/AppFunctionSerializableFactory;", "Lcom/google/android/appfunctions/schema/messages/v1/Message;", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "()V", "fromAppFunctionData", "appFunctionData", "Landroidx/appfunctions/AppFunctionData;", "toAppFunctionData", "appFunctionSerializable", "java.com.google.android.libraries.llm.appfunctions.appfunctions_schema2.src.com.google.android.appfunctions.schema.messages.v1_v1"}, k = 1, mv = {2, 1, 0}, xi = 48)
public final class C$MessageFactory implements AppFunctionSerializableFactory<Message> {
    /* JADX WARN: Can't rename method to resolve collision */
    @Override // androidx.appfunctions.internal.AppFunctionSerializableFactory
    public Message fromAppFunctionData(AppFunctionData appFunctionData) {
        Intrinsics.checkNotNullParameter(appFunctionData, "appFunctionData");
        AppFunctionData appFunctionDataWithSpec = getAppFunctionDataWithSpec(appFunctionData, "com.google.android.appfunctions.schema.messages.v1.Message");
        C$DateTimeFactory c$DateTimeFactory = new C$DateTimeFactory();
        C$AttachmentFactory c$AttachmentFactory = new C$AttachmentFactory();
        String stringOrNull = appFunctionDataWithSpec.getStringOrNull("id");
        if (stringOrNull == null) {
            throw new IllegalStateException("Required value was null.");
        }
        String stringOrNull2 = appFunctionDataWithSpec.getStringOrNull("messageType");
        if (stringOrNull2 == null) {
            throw new IllegalStateException("Required value was null.");
        }
        String stringOrNull3 = appFunctionDataWithSpec.getStringOrNull("body");
        if (stringOrNull3 == null) {
            throw new IllegalStateException("Required value was null.");
        }
        String stringOrNull4 = appFunctionDataWithSpec.getStringOrNull("senderId");
        if (stringOrNull4 == null) {
            throw new IllegalStateException("Required value was null.");
        }
        List<String> stringList = appFunctionDataWithSpec.getStringList("recipientsIds");
        if (stringList == null) {
            stringList = CollectionsKt.emptyList();
        }
        List<String> list = stringList;
        AppFunctionData appFunctionData2 = appFunctionDataWithSpec.getAppFunctionData("dateSent");
        DateTime dateTimeFromAppFunctionData = appFunctionData2 != null ? c$DateTimeFactory.fromAppFunctionData(appFunctionData2) : null;
        AppFunctionData appFunctionData3 = appFunctionDataWithSpec.getAppFunctionData("dateReceived");
        DateTime dateTimeFromAppFunctionData2 = appFunctionData3 != null ? c$DateTimeFactory.fromAppFunctionData(appFunctionData3) : null;
        AppFunctionData appFunctionData4 = appFunctionDataWithSpec.getAppFunctionData("dateRead");
        DateTime dateTimeFromAppFunctionData3 = appFunctionData4 != null ? c$DateTimeFactory.fromAppFunctionData(appFunctionData4) : null;
        List<AppFunctionData> appFunctionDataList = appFunctionDataWithSpec.getAppFunctionDataList("messageAttachments");
        if (appFunctionDataList == null) {
            appFunctionDataList = CollectionsKt.emptyList();
        }
        List<AppFunctionData> list2 = appFunctionDataList;
        ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(list2, 10));
        Iterator<T> it = list2.iterator();
        while (it.hasNext()) {
            arrayList.add(c$AttachmentFactory.fromAppFunctionData((AppFunctionData) it.next()));
        }
        return new Message(stringOrNull, stringOrNull2, stringOrNull3, stringOrNull4, list, dateTimeFromAppFunctionData, dateTimeFromAppFunctionData2, dateTimeFromAppFunctionData3, arrayList);
    }

    @Override // androidx.appfunctions.internal.AppFunctionSerializableFactory
    public AppFunctionData toAppFunctionData(Message appFunctionSerializable) {
        Intrinsics.checkNotNullParameter(appFunctionSerializable, "appFunctionSerializable");
        C$DateTimeFactory c$DateTimeFactory = new C$DateTimeFactory();
        C$AttachmentFactory c$AttachmentFactory = new C$AttachmentFactory();
        AppFunctionData.Builder appFunctionDataBuilder = getAppFunctionDataBuilder("com.google.android.appfunctions.schema.messages.v1.Message");
        appFunctionDataBuilder.setString("id", appFunctionSerializable.getId());
        appFunctionDataBuilder.setString("messageType", appFunctionSerializable.getMessageType());
        appFunctionDataBuilder.setString("body", appFunctionSerializable.getBody());
        appFunctionDataBuilder.setString("senderId", appFunctionSerializable.getSenderId());
        appFunctionDataBuilder.setStringList("recipientsIds", appFunctionSerializable.getRecipientsIds());
        DateTime dateSent = appFunctionSerializable.getDateSent();
        if (dateSent != null) {
            appFunctionDataBuilder.setAppFunctionData("dateSent", c$DateTimeFactory.toAppFunctionData(dateSent));
        }
        DateTime dateReceived = appFunctionSerializable.getDateReceived();
        if (dateReceived != null) {
            appFunctionDataBuilder.setAppFunctionData("dateReceived", c$DateTimeFactory.toAppFunctionData(dateReceived));
        }
        DateTime dateRead = appFunctionSerializable.getDateRead();
        if (dateRead != null) {
            appFunctionDataBuilder.setAppFunctionData("dateRead", c$DateTimeFactory.toAppFunctionData(dateRead));
        }
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
