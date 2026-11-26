package com.miui.notes.model;

import com.google.gson.Gson;
import com.market.sdk.reflect.ReflectUtilsForMiui;
import com.miui.handwritecommon.api.bean.WebImage;
import com.miui.handwriting.common.tools.constants.Creation;
import com.miui.notes.provider.Notes;
import java.util.List;
import kotlin.Metadata;
import kotlin.jvm.JvmStatic;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;

/* JADX INFO: compiled from: HandWriteEntity.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000(\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0010 \n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\f\u0018\u0000 \u00172\u00020\u0001:\u0001\u0017BQ\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0003\u0012\b\u0010\u0005\u001a\u0004\u0018\u00010\u0003\u0012\u000e\u0010\u0006\u001a\n\u0012\u0004\u0012\u00020\u0003\u0018\u00010\u0007\u0012\u0006\u0010\b\u001a\u00020\t\u0012\u0006\u0010\n\u001a\u00020\u0003\u0012\u000e\u0010\u000b\u001a\n\u0012\u0004\u0012\u00020\f\u0018\u00010\u0007¢\u0006\u0004\b\r\u0010\u000eJ\u0006\u0010\u0016\u001a\u00020\u0003R\u0012\u0010\u0002\u001a\u00020\u00038\u0006@\u0006X\u0087\u000e¢\u0006\u0002\n\u0000R\u0012\u0010\u0004\u001a\u00020\u00038\u0006@\u0006X\u0087\u000e¢\u0006\u0002\n\u0000R\u0014\u0010\u0005\u001a\u0004\u0018\u00010\u00038\u0006@\u0006X\u0087\u000e¢\u0006\u0002\n\u0000R\u001a\u0010\u0006\u001a\n\u0012\u0004\u0012\u00020\u0003\u0018\u00010\u00078\u0006@\u0006X\u0087\u000e¢\u0006\u0002\n\u0000R\u0014\u0010\u000f\u001a\u0004\u0018\u00010\u00038\u0006@\u0006X\u0087\u000e¢\u0006\u0002\n\u0000R\u0014\u0010\u0010\u001a\u0004\u0018\u00010\u00038\u0006@\u0006X\u0087\u000e¢\u0006\u0002\n\u0000R\u001e\u0010\u0011\u001a\u0004\u0018\u00010\u00038FX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0012\u0010\u0013\"\u0004\b\u0014\u0010\u0015R\u0012\u0010\b\u001a\u00020\t8\u0006@\u0006X\u0087\u000e¢\u0006\u0002\n\u0000R\u0010\u0010\n\u001a\u00020\u00038\u0006X\u0087\u0004¢\u0006\u0002\n\u0000R\u001a\u0010\u000b\u001a\n\u0012\u0004\u0012\u00020\f\u0018\u00010\u00078\u0006@\u0006X\u0087\u000e¢\u0006\u0002\n\u0000¨\u0006\u0018"}, d2 = {"Lcom/miui/notes/model/HandWriteEntity;", "", "title", "", "entName", "thumbnail", Notes.Note.IMAGES, "", "isEmptyContent", "", "canvasBgType", "webImages", "Lcom/miui/handwritecommon/api/bean/WebImage;", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/util/List;ZLjava/lang/String;Ljava/util/List;)V", Creation.TEXT_CONTENT, "recognizedText", "recognizedFile", "getRecognizedFile", "()Ljava/lang/String;", "setRecognizedFile", "(Ljava/lang/String;)V", "toNoteContent", "Companion", "app_PhoneGlobalRelease"}, k = 1, mv = {2, 2, 0}, xi = 48)
public final class HandWriteEntity {

    /* JADX INFO: renamed from: Companion, reason: from kotlin metadata */
    public static final Companion INSTANCE = new Companion(null);
    public static final String HandWritePrdfix = "<HandWrite Prdfix>";
    public final String canvasBgType;
    public String entName;
    public List<String> images;
    public boolean isEmptyContent;
    private String recognizedFile;
    public String recognizedText;
    public String textContent;
    public String thumbnail;
    public String title;
    public List<? extends WebImage> webImages;

    @JvmStatic
    public static final HandWriteEntity toHandWriteEntity(String str) {
        return INSTANCE.toHandWriteEntity(str);
    }

    public HandWriteEntity(String title, String entName, String str, List<String> list, boolean z, String canvasBgType, List<? extends WebImage> list2) {
        Intrinsics.checkNotNullParameter(title, "title");
        Intrinsics.checkNotNullParameter(entName, "entName");
        Intrinsics.checkNotNullParameter(canvasBgType, "canvasBgType");
        this.title = title;
        this.entName = entName;
        this.thumbnail = str;
        this.images = list;
        this.isEmptyContent = z;
        this.canvasBgType = canvasBgType;
        this.webImages = list2;
    }

    public final void setRecognizedFile(String str) {
        this.recognizedFile = str;
    }

    public final String getRecognizedFile() {
        String str = this.recognizedFile;
        return str == null ? "" : str;
    }

    public final String toNoteContent() {
        return HandWritePrdfix + new Gson().toJson(this);
    }

    /* JADX INFO: compiled from: HandWriteEntity.kt */
    @Metadata(d1 = {"\u0000\u001a\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u0086\u0003\u0018\u00002\u00020\u0001B\t\b\u0002¢\u0006\u0004\b\u0002\u0010\u0003J\u0012\u0010\u0006\u001a\u0004\u0018\u00010\u00072\u0006\u0010\b\u001a\u00020\u0005H\u0007R\u000e\u0010\u0004\u001a\u00020\u0005X\u0086T¢\u0006\u0002\n\u0000¨\u0006\t"}, d2 = {"Lcom/miui/notes/model/HandWriteEntity$Companion;", "", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "()V", "HandWritePrdfix", "", "toHandWriteEntity", "Lcom/miui/notes/model/HandWriteEntity;", "entityString", "app_PhoneGlobalRelease"}, k = 1, mv = {2, 2, 0}, xi = 48)
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }

        @JvmStatic
        public final HandWriteEntity toHandWriteEntity(String entityString) {
            Intrinsics.checkNotNullParameter(entityString, "entityString");
            if (!StringsKt.startsWith$default(entityString, HandWriteEntity.HandWritePrdfix, false, 2, (Object) null)) {
                return null;
            }
            String strSubstring = entityString.substring(18);
            Intrinsics.checkNotNullExpressionValue(strSubstring, "substring(...)");
            return (HandWriteEntity) new Gson().fromJson(strSubstring, HandWriteEntity.class);
        }
    }
}
