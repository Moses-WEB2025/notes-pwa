package com.miui.pad.feature.notes.handwrite;

import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.text.TextUtils;
import ch.qos.logback.core.CoreConstants;
import com.google.gson.Gson;
import com.market.sdk.reflect.ReflectUtilsForMiui;
import com.miui.common.app.BaseNoteApp;
import com.miui.handwritecommon.api.bean.WebImage;
import com.miui.handwriting.common.tools.constants.Creation;
import com.miui.notes.R;
import com.miui.notes.excerpt.NotifyUtils;
import com.miui.notes.model.HandWriteEntity;
import com.miui.notes.provider.Notes;
import com.miui.notes.provider.NotesProvider;
import com.miui.notes.store.NoteStore;
import com.miui.notes.tool.AttachmentUtils;
import com.miui.notes.tool.PreferenceUtils;
import com.xiaomi.micloudsdk.utils.MiCloudConstants;
import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import kotlin.Metadata;
import kotlin.jvm.JvmStatic;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import miuix.smartaction.SmartAction;

/* JADX INFO: compiled from: HandWriteDBHelper.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000n\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0010\u0011\n\u0002\b\u0002\n\u0002\u0010\t\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0002\b\u0004\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\r\bÆ\u0002\u0018\u00002\u00020\u0001:\u00019B\t\b\u0002¢\u0006\u0004\b\u0002\u0010\u0003J \u0010\u000b\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\f2\u0006\u0010\u000e\u001a\u00020\u000f2\u0006\u0010\u0010\u001a\u00020\fH\u0007J \u0010\u0011\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\f2\u0006\u0010\u000e\u001a\u00020\u000f2\u0006\u0010\u0010\u001a\u00020\fH\u0007J(\u0010\u0012\u001a\u00020\u00132\u0006\u0010\u0014\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\f2\u0006\u0010\u000e\u001a\u00020\u000f2\u0006\u0010\u0015\u001a\u00020\fH\u0007J\u0010\u0010\u0016\u001a\u00020\u00132\u0006\u0010\u0014\u001a\u00020\fH\u0007J\u001e\u0010\u0017\u001a\u00020\u00182\u0006\u0010\u0019\u001a\u00020\u001a2\u0006\u0010\u001b\u001a\u00020\f2\u0006\u0010\u001c\u001a\u00020\u0005J\u001a\u0010\u001d\u001a\u0004\u0018\u00010\u001e2\u0006\u0010\u0019\u001a\u00020\u001a2\u0006\u0010\u001f\u001a\u00020\u0005H\u0007J<\u0010 \u001a\u00020!2\b\u0010\u000e\u001a\u0004\u0018\u00010\u000f2\u0006\u0010\u001c\u001a\u00020\u00052\b\u0010\"\u001a\u0004\u0018\u00010\u00052\u0006\u0010\r\u001a\u00020\f2\u0006\u0010#\u001a\u00020\f2\u0006\u0010\u0015\u001a\u00020\fH\u0002J`\u0010$\u001a\u0012\u0012\u0004\u0012\u00020!0%j\b\u0012\u0004\u0012\u00020!`&2\b\u0010\u001c\u001a\u0004\u0018\u00010\u00052\b\u0010'\u001a\u0004\u0018\u00010\u00052\u000e\u0010(\u001a\n\u0012\u0004\u0012\u00020\u0005\u0018\u00010)2\b\u0010*\u001a\u0004\u0018\u00010\u00052\b\u0010+\u001a\u0004\u0018\u00010\u00052\u000e\u0010,\u001a\n\u0012\u0004\u0012\u00020-\u0018\u00010)H\u0002J\"\u0010.\u001a\n\u0012\u0004\u0012\u00020\u0005\u0018\u00010)2\u0010\u0010/\u001a\f\u0012\u0006\u0012\u0004\u0018\u00010\u0005\u0018\u00010)H\u0007J\u001c\u00104\u001a\u0004\u0018\u00010\u00052\u0006\u0010\u0019\u001a\u00020\u001a2\b\u00105\u001a\u0004\u0018\u00010\u0005H\u0007J\u001a\u00106\u001a\u00020\u00182\u0006\u0010\u0019\u001a\u00020\u001a2\b\u00107\u001a\u0004\u0018\u00010\u0005H\u0007J\u0016\u00108\u001a\u00020\f2\u0006\u0010#\u001a\u00020\f2\u0006\u0010\u0015\u001a\u00020\fR\u000e\u0010\u0004\u001a\u00020\u0005X\u0082T¢\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0005X\u0082T¢\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\u0005X\u0086T¢\u0006\u0002\n\u0000R\u0016\u0010\b\u001a\b\u0012\u0004\u0012\u00020\u00050\tX\u0082\u0004¢\u0006\u0004\n\u0002\u0010\nR\u001a\u00100\u001a\u00020\u00058FX\u0087\u0004¢\u0006\f\u0012\u0004\b1\u0010\u0003\u001a\u0004\b2\u00103¨\u0006:"}, d2 = {"Lcom/miui/pad/feature/notes/handwrite/HandWriteDBHelper;", "", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "()V", "ENT_FILE_TYPE", "", "ENT_SUFFIX", "DOWNLOAD_HANDWRITE_ACTION", "NOTE_PROJECTION", "", "[Ljava/lang/String;", "createNewNote", "", MiCloudConstants.PDC.J_PARENT_ID, "entity", "Lcom/miui/notes/model/HandWriteEntity;", "time", "insertNewNote", "updateHandWrite", "", "id", "modifyTime", "deleteNote", "deleteData", "", CoreConstants.CONTEXT_SCOPE_VALUE, "Landroid/content/Context;", NotifyUtils.KEY_NOTE_ID, "content", SmartAction.Feature.QUERY, "Lcom/miui/pad/feature/notes/handwrite/HandWriteDBHelper$HandWriteNoteInfo;", "selection", "getContentValues", "Landroid/content/ContentValues;", Creation.TEXT_CONTENT, "createTime", "buildDataValuesList", "Ljava/util/ArrayList;", "Lkotlin/collections/ArrayList;", "thumbnail", Notes.Note.IMAGES, "", "entFile", "recognizedFile", "webImages", "Lcom/miui/handwritecommon/api/bean/WebImage;", "getImages", "imagePaths", "entFileName", "getEntFileName$annotations", "getEntFileName", "()Ljava/lang/String;", "reNameEnt", "oldName", "deleteFile", "fileName", "getSortDate", "HandWriteNoteInfo", "app_PhoneGlobalRelease"}, k = 1, mv = {2, 2, 0}, xi = 48)
public final class HandWriteDBHelper {
    public static final String DOWNLOAD_HANDWRITE_ACTION = "com.miui.notes.handwrite.download";
    private static final String ENT_FILE_TYPE = "ent/file";
    private static final String ENT_SUFFIX = ".ent";
    public static final HandWriteDBHelper INSTANCE = new HandWriteDBHelper();
    private static final String[] NOTE_PROJECTION = {"_id", "parent_id", "created_date", "modified_date", "snippet", Notes.Note.LOCAL_MODIFIED, "type", Notes.Note.NOTE_CONTENT_TYPE, Notes.Note.MIND_CONTENT, "title", "sync_id"};

    @JvmStatic
    public static /* synthetic */ void getEntFileName$annotations() {
    }

    private HandWriteDBHelper() {
    }

    @JvmStatic
    public static final long createNewNote(long parentId, HandWriteEntity entity, long time) {
        Intrinsics.checkNotNullParameter(entity, "entity");
        HandWriteDBHelper handWriteDBHelper = INSTANCE;
        String str = Notes.Utils.insertNoteAtomic(BaseNoteApp.INSTANCE.getInstance(), handWriteDBHelper.getContentValues(entity, entity.toNoteContent(), entity.textContent, parentId, time, time), handWriteDBHelper.buildDataValuesList("", null, null, null, null, null)).getPathSegments().get(2);
        Intrinsics.checkNotNullExpressionValue(str, "get(...)");
        return Long.parseLong(str);
    }

    @JvmStatic
    public static final long insertNewNote(long parentId, HandWriteEntity entity, long time) {
        Intrinsics.checkNotNullParameter(entity, "entity");
        HandWriteDBHelper handWriteDBHelper = INSTANCE;
        String str = Notes.Utils.insertNoteAtomic(BaseNoteApp.INSTANCE.getInstance(), handWriteDBHelper.getContentValues(entity, entity.toNoteContent(), entity.textContent, parentId, time, time), handWriteDBHelper.buildDataValuesList("", null, null, null, null, null)).getPathSegments().get(2);
        Intrinsics.checkNotNullExpressionValue(str, "get(...)");
        return Long.parseLong(str);
    }

    @JvmStatic
    public static final boolean updateHandWrite(long id, long parentId, HandWriteEntity entity, long modifyTime) {
        String string;
        Intrinsics.checkNotNullParameter(entity, "entity");
        String noteContent = entity.toNoteContent();
        if (entity.isEmptyContent) {
            string = "";
        } else if (!TextUtils.isEmpty(entity.textContent)) {
            string = entity.textContent;
        } else {
            string = BaseNoteApp.INSTANCE.getInstance().getString(R.string.pad_note_list_not_text_stub);
        }
        String str = string;
        HandWriteDBHelper handWriteDBHelper = INSTANCE;
        return Notes.Utils.updateNoteAtomic(BaseNoteApp.INSTANCE.getInstance(), id, handWriteDBHelper.getContentValues(entity, noteContent, str, parentId, 0L, modifyTime), handWriteDBHelper.buildDataValuesList(str, entity.thumbnail, entity.images, entity.entName, entity.getRecognizedFile(), entity.webImages), null, null) > 0;
    }

    @JvmStatic
    public static final boolean deleteNote(long id) {
        return NoteStore.delete(BaseNoteApp.INSTANCE.getInstance(), new long[]{id}) > 0;
    }

    public final void deleteData(Context context, long noteId, String content) {
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(content, "content");
        Uri uriWithAppendedId = ContentUris.withAppendedId(Notes.Data.CONTENT_URI, noteId);
        Intrinsics.checkNotNullExpressionValue(uriWithAppendedId, "withAppendedId(...)");
        context.getContentResolver().delete(uriWithAppendedId, "content=?", new String[]{content});
    }

    @JvmStatic
    public static final HandWriteNoteInfo query(Context context, String selection) {
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(selection, "selection");
        Cursor cursorQuery = context.getContentResolver().query(NotesProvider.appendFlag(Notes.Note.CONTENT_URI, NotesProvider.CALLER_IS_PRIVACY), NOTE_PROJECTION, "type=0 AND " + selection, null, null);
        if (cursorQuery == null) {
            return null;
        }
        try {
            if (cursorQuery.moveToNext()) {
                return HandWriteNoteInfo.INSTANCE.valueOf(cursorQuery);
            }
            return null;
        } finally {
            cursorQuery.close();
        }
    }

    private final ContentValues getContentValues(HandWriteEntity entity, String content, String textContent, long parentId, long createTime, long modifyTime) {
        ContentValues contentValues = new ContentValues();
        contentValues.put("parent_id", Long.valueOf(parentId));
        if (createTime != 0) {
            contentValues.put("created_date", Long.valueOf(createTime));
        }
        contentValues.put("modified_date", Long.valueOf(modifyTime));
        contentValues.put(Notes.Note.MIND_CONTENT, content);
        contentValues.put(Notes.Note.LOCAL_MODIFIED, (Integer) 1);
        contentValues.put("type", (Integer) 0);
        contentValues.put("snippet", textContent);
        contentValues.put(Notes.Note.NOTE_CONTENT_TYPE, "handwrite");
        if (entity != null) {
            contentValues.put("title", entity.title);
            contentValues.put(Notes.Note.WEB_IMAGES, new Gson().toJson(entity.webImages));
        }
        return contentValues;
    }

    private final ArrayList<ContentValues> buildDataValuesList(String content, String thumbnail, List<String> images, String entFile, String recognizedFile, List<? extends WebImage> webImages) {
        ArrayList<ContentValues> arrayList = new ArrayList<>();
        ContentValues contentValues = new ContentValues();
        contentValues.put(Notes.Data.MIME_TYPE, Notes.TextData.CONTENT_ITEM_TYPE);
        contentValues.put("content", content);
        arrayList.add(contentValues);
        if (!TextUtils.isEmpty(entFile)) {
            ContentValues contentValues2 = new ContentValues();
            contentValues2.put(Notes.Data.MIME_TYPE, "ent/file");
            contentValues2.put("content", entFile);
            arrayList.add(contentValues2);
        }
        if (!TextUtils.isEmpty(recognizedFile)) {
            ContentValues contentValues3 = new ContentValues();
            contentValues3.put(Notes.Data.MIME_TYPE, "ent/file");
            contentValues3.put("content", recognizedFile);
            arrayList.add(contentValues3);
        }
        if (images != null) {
            int size = images.size();
            for (int i = 0; i < size; i++) {
                if (!TextUtils.isEmpty(images.get(i))) {
                    ContentValues contentValues4 = new ContentValues();
                    contentValues4.put("content", images.get(i));
                    contentValues4.put(Notes.Data.MIME_TYPE, "image/jpeg");
                    arrayList.add(contentValues4);
                }
            }
        }
        if (webImages != null) {
            int size2 = webImages.size();
            for (int i2 = 0; i2 < size2; i2++) {
                if (!TextUtils.isEmpty(webImages.get(i2).getImage())) {
                    ContentValues contentValues5 = new ContentValues();
                    contentValues5.put("content", webImages.get(i2).getImage());
                    contentValues5.put(Notes.Data.MIME_TYPE, "image/jpeg");
                    arrayList.add(contentValues5);
                }
            }
        }
        if (!TextUtils.isEmpty(thumbnail)) {
            ContentValues contentValues6 = new ContentValues();
            contentValues6.put("content", thumbnail);
            contentValues6.put(Notes.Data.MIME_TYPE, "image/jpeg");
            arrayList.add(contentValues6);
        }
        Iterator<ContentValues> it = arrayList.iterator();
        Intrinsics.checkNotNullExpressionValue(it, "iterator(...)");
        while (it.hasNext()) {
            ContentValues next = it.next();
            Intrinsics.checkNotNullExpressionValue(next, "next(...)");
            next.put(Notes.Data.DIRTY, (Integer) 1);
        }
        return arrayList;
    }

    @JvmStatic
    public static final List<String> getImages(List<String> imagePaths) {
        if (imagePaths == null) {
            return null;
        }
        ArrayList arrayList = new ArrayList();
        int size = imagePaths.size();
        for (int i = 0; i < size; i++) {
            if (!TextUtils.isEmpty(imagePaths.get(i))) {
                String name = new File(imagePaths.get(i)).getName();
                Intrinsics.checkNotNullExpressionValue(name, "getName(...)");
                arrayList.add(name);
            }
        }
        return arrayList;
    }

    public static final String getEntFileName() {
        return System.currentTimeMillis() + ".ent";
    }

    @JvmStatic
    public static final String reNameEnt(Context context, String oldName) {
        Intrinsics.checkNotNullParameter(context, "context");
        String attachmentPath = AttachmentUtils.getAttachmentPath(context, oldName);
        try {
            File file = new File(attachmentPath);
            String attachmentPath2 = AttachmentUtils.getAttachmentPath(context, AttachmentUtils.getFileSha1Digest(attachmentPath) + ".ent");
            if (TextUtils.equals(attachmentPath2, attachmentPath)) {
                return null;
            }
            File file2 = new File(attachmentPath2);
            if (!file.renameTo(file2)) {
                return null;
            }
            file2.setReadable(true, false);
            String name = file2.getName();
            if (TextUtils.isEmpty(name)) {
                return null;
            }
            return name;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @JvmStatic
    public static final void deleteFile(Context context, String fileName) {
        Intrinsics.checkNotNullParameter(context, "context");
        try {
            new File(AttachmentUtils.getAttachmentPath(context, fileName)).delete();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public final long getSortDate(long createTime, long modifyTime) {
        return PreferenceUtils.getNoteSortWay(BaseNoteApp.INSTANCE.getInstance(), 1) == 0 ? createTime : modifyTime;
    }

    /* JADX INFO: compiled from: HandWriteDBHelper.kt */
    @Metadata(d1 = {"\u0000$\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0003\n\u0002\u0010\t\n\u0002\b\t\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0010\b\n\u0002\b\u000e\u0018\u0000  2\u00020\u0001:\u0001 B\u0007¢\u0006\u0004\b\u0002\u0010\u0003R\u001e\u0010\u0006\u001a\u00020\u00052\u0006\u0010\u0004\u001a\u00020\u0005@BX\u0086\u000e¢\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\bR\u001e\u0010\t\u001a\u00020\u00052\u0006\u0010\u0004\u001a\u00020\u0005@BX\u0086\u000e¢\u0006\b\n\u0000\u001a\u0004\b\n\u0010\bR\u001e\u0010\u000b\u001a\u00020\u00052\u0006\u0010\u0004\u001a\u00020\u0005@BX\u0086\u000e¢\u0006\b\n\u0000\u001a\u0004\b\f\u0010\bR\u001e\u0010\r\u001a\u00020\u00052\u0006\u0010\u0004\u001a\u00020\u0005@BX\u0086\u000e¢\u0006\b\n\u0000\u001a\u0004\b\u000e\u0010\bR\"\u0010\u0010\u001a\u0004\u0018\u00010\u000f2\b\u0010\u0004\u001a\u0004\u0018\u00010\u000f@BX\u0086\u000e¢\u0006\b\n\u0000\u001a\u0004\b\u0011\u0010\u0012R\u001e\u0010\u0014\u001a\u00020\u00132\u0006\u0010\u0004\u001a\u00020\u0013@BX\u0086\u000e¢\u0006\b\n\u0000\u001a\u0004\b\u0015\u0010\u0016R\u001e\u0010\u0017\u001a\u00020\u00132\u0006\u0010\u0004\u001a\u00020\u0013@BX\u0086\u000e¢\u0006\b\n\u0000\u001a\u0004\b\u0018\u0010\u0016R\"\u0010\u0019\u001a\u0004\u0018\u00010\u000f2\b\u0010\u0004\u001a\u0004\u0018\u00010\u000f@BX\u0086\u000e¢\u0006\b\n\u0000\u001a\u0004\b\u001a\u0010\u0012R\"\u0010\u001b\u001a\u0004\u0018\u00010\u000f2\b\u0010\u0004\u001a\u0004\u0018\u00010\u000f@BX\u0086\u000e¢\u0006\b\n\u0000\u001a\u0004\b\u001c\u0010\u0012R\u0010\u0010\u001d\u001a\u0004\u0018\u00010\u000fX\u0082\u000e¢\u0006\u0002\n\u0000R\u001e\u0010\u001e\u001a\u00020\u00052\u0006\u0010\u0004\u001a\u00020\u0005@BX\u0086\u000e¢\u0006\b\n\u0000\u001a\u0004\b\u001f\u0010\b¨\u0006!"}, d2 = {"Lcom/miui/pad/feature/notes/handwrite/HandWriteDBHelper$HandWriteNoteInfo;", "", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "()V", "value", "", "id", "getId", "()J", MiCloudConstants.PDC.J_PARENT_ID, "getParentId", "createdDate", "getCreatedDate", "modifiedDate", "getModifiedDate", "", "snippet", "getSnippet", "()Ljava/lang/String;", "", "localModified", "getLocalModified", "()I", "type", "getType", "contentType", "getContentType", "handWriteContent", "getHandWriteContent", "mTitle", "syncId", "getSyncId", "Companion", "app_PhoneGlobalRelease"}, k = 1, mv = {2, 2, 0}, xi = 48)
    public static final class HandWriteNoteInfo {

        /* JADX INFO: renamed from: Companion, reason: from kotlin metadata */
        public static final Companion INSTANCE = new Companion(null);
        private String contentType;
        private long createdDate;
        private String handWriteContent;
        private long id = -2;
        private int localModified;
        private String mTitle;
        private long modifiedDate;
        private long parentId;
        private String snippet;
        private long syncId;
        private int type;

        public final long getId() {
            return this.id;
        }

        public final long getParentId() {
            return this.parentId;
        }

        public final long getCreatedDate() {
            return this.createdDate;
        }

        public final long getModifiedDate() {
            return this.modifiedDate;
        }

        public final String getSnippet() {
            return this.snippet;
        }

        public final int getLocalModified() {
            return this.localModified;
        }

        public final int getType() {
            return this.type;
        }

        public final String getContentType() {
            return this.contentType;
        }

        public final String getHandWriteContent() {
            return this.handWriteContent;
        }

        public final long getSyncId() {
            return this.syncId;
        }

        /* JADX INFO: compiled from: HandWriteDBHelper.kt */
        @Metadata(d1 = {"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\t\b\u0002¢\u0006\u0004\b\u0002\u0010\u0003J\u000e\u0010\u0004\u001a\u00020\u00052\u0006\u0010\u0006\u001a\u00020\u0007¨\u0006\b"}, d2 = {"Lcom/miui/pad/feature/notes/handwrite/HandWriteDBHelper$HandWriteNoteInfo$Companion;", "", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "()V", CoreConstants.VALUE_OF, "Lcom/miui/pad/feature/notes/handwrite/HandWriteDBHelper$HandWriteNoteInfo;", "cursor", "Landroid/database/Cursor;", "app_PhoneGlobalRelease"}, k = 1, mv = {2, 2, 0}, xi = 48)
        public static final class Companion {
            public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
                this();
            }

            private Companion() {
            }

            public final HandWriteNoteInfo valueOf(Cursor cursor) {
                Intrinsics.checkNotNullParameter(cursor, "cursor");
                HandWriteNoteInfo handWriteNoteInfo = new HandWriteNoteInfo();
                handWriteNoteInfo.id = cursor.getLong(0);
                handWriteNoteInfo.parentId = cursor.getLong(1);
                handWriteNoteInfo.createdDate = cursor.getLong(2);
                handWriteNoteInfo.modifiedDate = cursor.getLong(3);
                handWriteNoteInfo.snippet = cursor.getString(4);
                handWriteNoteInfo.localModified = cursor.getInt(5);
                handWriteNoteInfo.type = cursor.getInt(6);
                handWriteNoteInfo.contentType = cursor.getString(7);
                handWriteNoteInfo.handWriteContent = cursor.getString(8);
                handWriteNoteInfo.mTitle = cursor.getString(9);
                handWriteNoteInfo.syncId = cursor.getLong(10);
                return handWriteNoteInfo;
            }
        }
    }
}
