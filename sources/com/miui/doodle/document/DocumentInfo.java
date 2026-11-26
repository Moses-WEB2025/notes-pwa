package com.miui.doodle.document;

import com.market.sdk.reflect.ReflectUtilsForMiui;
import kotlin.Metadata;
import kotlin.jvm.JvmStatic;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.json.JSONException;
import org.json.JSONObject;

/* JADX INFO: compiled from: DocumentInfo.kt */
/* JADX INFO: loaded from: classes.dex */
@Metadata(d1 = {"\u0000,\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0003\n\u0002\u0010\t\n\u0002\b\u0005\n\u0002\u0010\u000e\n\u0002\b\u000e\n\u0002\u0010\u000b\n\u0002\b\t\n\u0002\u0018\u0002\n\u0002\b\u0002\u0018\u0000 %2\u00020\u0001:\u0001%B\u0007¢\u0006\u0004\b\u0002\u0010\u0003J\u0006\u0010\"\u001a\u00020\u0000J\u0006\u0010#\u001a\u00020$R\u001a\u0010\u0004\u001a\u00020\u0005X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0006\u0010\u0007\"\u0004\b\b\u0010\tR\u001a\u0010\n\u001a\u00020\u000bX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\f\u0010\r\"\u0004\b\u000e\u0010\u000fR\u001a\u0010\u0010\u001a\u00020\u000bX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0011\u0010\r\"\u0004\b\u0012\u0010\u000fR\u001a\u0010\u0013\u001a\u00020\u0005X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0014\u0010\u0007\"\u0004\b\u0015\u0010\tR\u001a\u0010\u0016\u001a\u00020\u0005X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0017\u0010\u0007\"\u0004\b\u0018\u0010\tR\u001a\u0010\u0019\u001a\u00020\u001aX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u001b\u0010\u001c\"\u0004\b\u001d\u0010\u001eR\u001a\u0010\u001f\u001a\u00020\u000bX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b \u0010\r\"\u0004\b!\u0010\u000f¨\u0006&"}, d2 = {"Lcom/miui/doodle/document/DocumentInfo;", "", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "()V", "version", "", "getVersion", "()J", "setVersion", "(J)V", DocumentInfo.KEY_APP, "", "getApp", "()Ljava/lang/String;", "setApp", "(Ljava/lang/String;)V", DocumentInfo.KEY_AUTHOR, "getAuthor", "setAuthor", DocumentInfo.KEY_CREATED_AT, "getCreatedAt", "setCreatedAt", DocumentInfo.KEY_LAST_MODIFIED, "getLastModified", "setLastModified", DocumentInfo.KEY_EDIT_IMAGE, "", "getEditImage", "()Z", "setEditImage", "(Z)V", DocumentInfo.KEY_BACKGROUND_TYPE, "getBackgroundType", "setBackgroundType", "copy", "toJSONObject", "Lorg/json/JSONObject;", "Companion", "libDoodle_release"}, k = 1, mv = {2, 2, 0}, xi = 48)
public final class DocumentInfo {
    public static final String BACKGROUND_TYPE_DOT = "dot";
    public static final String BACKGROUND_TYPE_WHITE = "white";
    public static final long CURRENT_SUPPORT_VERSION = 0;

    /* JADX INFO: renamed from: Companion, reason: from kotlin metadata */
    public static final Companion INSTANCE = new Companion(null);
    private static final String KEY_APP = "app";
    private static final String KEY_AUTHOR = "author";
    private static final String KEY_BACKGROUND_TYPE = "backgroundType";
    private static final String KEY_CREATED_AT = "createdAt";
    private static final String KEY_EDIT_IMAGE = "editImage";
    private static final String KEY_LAST_MODIFIED = "lastModified";
    private static final String KEY_VERSION = "version";
    private String app = "com.miui.notes";
    private String author = "";
    private String backgroundType;
    private long createdAt;
    private boolean editImage;
    private long lastModified;
    private long version;

    @JvmStatic
    public static final DocumentInfo fromJSONObject(JSONObject jSONObject) throws JSONException {
        return INSTANCE.fromJSONObject(jSONObject);
    }

    public DocumentInfo() {
        long jCurrentTimeMillis = System.currentTimeMillis();
        this.createdAt = jCurrentTimeMillis;
        this.lastModified = jCurrentTimeMillis;
        this.backgroundType = "dot";
    }

    public final long getVersion() {
        return this.version;
    }

    public final void setVersion(long j) {
        this.version = j;
    }

    public final String getApp() {
        return this.app;
    }

    public final void setApp(String str) {
        Intrinsics.checkNotNullParameter(str, "<set-?>");
        this.app = str;
    }

    public final String getAuthor() {
        return this.author;
    }

    public final void setAuthor(String str) {
        Intrinsics.checkNotNullParameter(str, "<set-?>");
        this.author = str;
    }

    public final long getCreatedAt() {
        return this.createdAt;
    }

    public final void setCreatedAt(long j) {
        this.createdAt = j;
    }

    public final long getLastModified() {
        return this.lastModified;
    }

    public final void setLastModified(long j) {
        this.lastModified = j;
    }

    public final boolean getEditImage() {
        return this.editImage;
    }

    public final void setEditImage(boolean z) {
        this.editImage = z;
    }

    public final String getBackgroundType() {
        return this.backgroundType;
    }

    public final void setBackgroundType(String str) {
        Intrinsics.checkNotNullParameter(str, "<set-?>");
        this.backgroundType = str;
    }

    public final DocumentInfo copy() {
        DocumentInfo documentInfo = new DocumentInfo();
        documentInfo.version = this.version;
        documentInfo.app = this.app;
        documentInfo.author = this.author;
        documentInfo.createdAt = this.createdAt;
        documentInfo.lastModified = this.lastModified;
        documentInfo.editImage = this.editImage;
        return documentInfo;
    }

    public final JSONObject toJSONObject() throws JSONException {
        JSONObject jSONObject = new JSONObject();
        jSONObject.put("version", this.version);
        jSONObject.put(KEY_APP, this.app);
        jSONObject.put(KEY_AUTHOR, this.author);
        jSONObject.put(KEY_CREATED_AT, this.createdAt);
        jSONObject.put(KEY_LAST_MODIFIED, this.lastModified);
        jSONObject.put(KEY_EDIT_IMAGE, this.editImage);
        jSONObject.put(KEY_BACKGROUND_TYPE, this.backgroundType);
        return jSONObject;
    }

    /* JADX INFO: compiled from: DocumentInfo.kt */
    @Metadata(d1 = {"\u0000(\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0002\b\u0007\n\u0002\u0010\t\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\t\b\u0002¢\u0006\u0004\b\u0002\u0010\u0003J\u0010\u0010\u0010\u001a\u00020\u00112\u0006\u0010\u0012\u001a\u00020\u0013H\u0007R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082T¢\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0005X\u0082T¢\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\u0005X\u0082T¢\u0006\u0002\n\u0000R\u000e\u0010\b\u001a\u00020\u0005X\u0082T¢\u0006\u0002\n\u0000R\u000e\u0010\t\u001a\u00020\u0005X\u0082T¢\u0006\u0002\n\u0000R\u000e\u0010\n\u001a\u00020\u0005X\u0082T¢\u0006\u0002\n\u0000R\u000e\u0010\u000b\u001a\u00020\u0005X\u0082T¢\u0006\u0002\n\u0000R\u000e\u0010\f\u001a\u00020\rX\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\u000e\u001a\u00020\u0005X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\u000f\u001a\u00020\u0005X\u0086T¢\u0006\u0002\n\u0000¨\u0006\u0014"}, d2 = {"Lcom/miui/doodle/document/DocumentInfo$Companion;", "", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "()V", "KEY_VERSION", "", "KEY_APP", "KEY_AUTHOR", "KEY_CREATED_AT", "KEY_LAST_MODIFIED", "KEY_EDIT_IMAGE", "KEY_BACKGROUND_TYPE", "CURRENT_SUPPORT_VERSION", "", "BACKGROUND_TYPE_DOT", "BACKGROUND_TYPE_WHITE", "fromJSONObject", "Lcom/miui/doodle/document/DocumentInfo;", "jsonObject", "Lorg/json/JSONObject;", "libDoodle_release"}, k = 1, mv = {2, 2, 0}, xi = 48)
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }

        @JvmStatic
        public final DocumentInfo fromJSONObject(JSONObject jsonObject) throws JSONException {
            Intrinsics.checkNotNullParameter(jsonObject, "jsonObject");
            DocumentInfo documentInfo = new DocumentInfo();
            documentInfo.setVersion(jsonObject.getLong("version"));
            String string = jsonObject.getString(DocumentInfo.KEY_APP);
            Intrinsics.checkNotNullExpressionValue(string, "getString(...)");
            documentInfo.setApp(string);
            String string2 = jsonObject.getString(DocumentInfo.KEY_AUTHOR);
            Intrinsics.checkNotNullExpressionValue(string2, "getString(...)");
            documentInfo.setAuthor(string2);
            documentInfo.setCreatedAt(jsonObject.getLong(DocumentInfo.KEY_CREATED_AT));
            documentInfo.setLastModified(jsonObject.getLong(DocumentInfo.KEY_LAST_MODIFIED));
            documentInfo.setEditImage(jsonObject.optBoolean(DocumentInfo.KEY_EDIT_IMAGE));
            String strOptString = jsonObject.optString(DocumentInfo.KEY_BACKGROUND_TYPE, "dot");
            Intrinsics.checkNotNullExpressionValue(strOptString, "optString(...)");
            documentInfo.setBackgroundType(strOptString);
            return documentInfo;
        }
    }
}
