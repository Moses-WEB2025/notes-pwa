package com.miui.notes.model;

import android.text.TextUtils;
import com.google.gson.Gson;
import com.market.sdk.reflect.ReflectUtilsForMiui;
import java.util.Arrays;
import kotlin.Metadata;
import kotlin.jvm.JvmStatic;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.StringCompanionObject;

/* JADX INFO: compiled from: MindEntity.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000 \n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0005\n\u0002\u0010\b\n\u0002\b\u0005\u0018\u0000 \u000f2\u00020\u0001:\u0001\u000fB)\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\b\u0010\u0006\u001a\u0004\u0018\u00010\u0005\u0012\u0006\u0010\u0007\u001a\u00020\u0005¢\u0006\u0004\b\b\u0010\tJ\u0006\u0010\u000e\u001a\u00020\u0005R\u0012\u0010\u0002\u001a\u00020\u00038\u0006@\u0006X\u0087\u000e¢\u0006\u0002\n\u0000R\u0012\u0010\u0004\u001a\u00020\u00058\u0006@\u0006X\u0087\u000e¢\u0006\u0002\n\u0000R\u0014\u0010\u0006\u001a\u0004\u0018\u00010\u00058\u0006@\u0006X\u0087\u000e¢\u0006\u0002\n\u0000R\u0012\u0010\u0007\u001a\u00020\u00058\u0006@\u0006X\u0087\u000e¢\u0006\u0002\n\u0000R\u0011\u0010\n\u001a\u00020\u000b8F¢\u0006\u0006\u001a\u0004\b\f\u0010\r¨\u0006\u0010"}, d2 = {"Lcom/miui/notes/model/MindEntity;", "", "isMap", "", "title", "", "content", "thumbnail", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "(ZLjava/lang/String;Ljava/lang/String;Ljava/lang/String;)V", "contentType", "", "getContentType", "()I", "toNoteContent", "Companion", "app_PhoneGlobalRelease"}, k = 1, mv = {2, 2, 0}, xi = 48)
public final class MindEntity {

    /* JADX INFO: renamed from: Companion, reason: from kotlin metadata */
    public static final Companion INSTANCE = new Companion(null);
    public static final String DEFAULT_CONTENT_TEMPLATE = "{\"currentPage\":\"/mind\",\"data\":{\"id\":\"root\",\"label\":\"%s\",\"type\":\"root-node\",\"parentId\":\"ROOT\",\"collapsed\":false,\"children\":[],\"nodeHeight\":61,\"x\":0,\"y\":-61,\"depth\":0,\"style\":{\"draging\":{\"fill\":\"rgba(249, 179, 0, 0.2)\",\"path-shape\":{\"stroke\":\"transparent\"},\"text-shape\":{\"fillOpacity\":0.6}},\"rootFocused\":{},\"hover\":{\"fill\":\"#FFFBF1\",\"stroke\":\"rgba(255, 187, 0, 0.3)\",\"lineWidth\":1},\"rootHover\":{\"stroke\":\"rgba(255, 187, 0, 0.3)\",\"lineWidth\":1}},\"contextStack\":[],\"custom_containerWidth\":200},\"userInput\":\"\"}";
    public static final int EDIT_MODE_CREATE = 0;
    public static final int EDIT_MODE_VIEW = 1;
    public static final String MindPrefix = "<MiMind-Prefix>";
    public static final String MindPrefixError = "<MiMind Prdfix>";
    public static final int VIEW_TYPE_MAP = 0;
    public static final int VIEW_TYPE_OVERVIEW = 1;
    public String content;
    public boolean isMap;
    public String thumbnail;
    public String title;

    @JvmStatic
    public static final String createNewMindContentByTitle(String str) {
        return INSTANCE.createNewMindContentByTitle(str);
    }

    public MindEntity(boolean z, String title, String str, String thumbnail) {
        Intrinsics.checkNotNullParameter(title, "title");
        Intrinsics.checkNotNullParameter(thumbnail, "thumbnail");
        this.isMap = z;
        this.title = title;
        if (TextUtils.isEmpty(str)) {
            this.content = INSTANCE.createNewMindContentByTitle(TextUtils.isEmpty(this.title) ? "" : this.title);
        } else {
            this.content = str;
        }
        this.thumbnail = thumbnail;
    }

    public final int getContentType() {
        return !this.isMap ? 1 : 0;
    }

    public final String toNoteContent() {
        return MindPrefixError + new Gson().toJson(this);
    }

    /* JADX INFO: compiled from: MindEntity.kt */
    @Metadata(d1 = {"\u0000\u001c\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0003\n\u0002\u0010\b\n\u0002\b\u0004\n\u0002\u0010\u000e\n\u0002\b\u0005\b\u0086\u0003\u0018\u00002\u00020\u0001B\t\b\u0002¢\u0006\u0004\b\u0002\u0010\u0003J\u0010\u0010\r\u001a\u00020\n2\u0006\u0010\u000e\u001a\u00020\nH\u0007R\u000e\u0010\u0004\u001a\u00020\u0005X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0005X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\u0005X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\b\u001a\u00020\u0005X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\t\u001a\u00020\nX\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\u000b\u001a\u00020\nX\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\f\u001a\u00020\nX\u0086T¢\u0006\u0002\n\u0000¨\u0006\u000f"}, d2 = {"Lcom/miui/notes/model/MindEntity$Companion;", "", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "()V", "VIEW_TYPE_MAP", "", "VIEW_TYPE_OVERVIEW", "EDIT_MODE_CREATE", "EDIT_MODE_VIEW", "MindPrefixError", "", "MindPrefix", "DEFAULT_CONTENT_TEMPLATE", "createNewMindContentByTitle", "title", "app_PhoneGlobalRelease"}, k = 1, mv = {2, 2, 0}, xi = 48)
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }

        @JvmStatic
        public final String createNewMindContentByTitle(String title) {
            Intrinsics.checkNotNullParameter(title, "title");
            StringCompanionObject stringCompanionObject = StringCompanionObject.INSTANCE;
            String str = String.format(MindEntity.DEFAULT_CONTENT_TEMPLATE, Arrays.copyOf(new Object[]{title}, 1));
            Intrinsics.checkNotNullExpressionValue(str, "format(...)");
            return str;
        }
    }
}
