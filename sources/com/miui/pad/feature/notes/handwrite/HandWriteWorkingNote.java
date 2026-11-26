package com.miui.pad.feature.notes.handwrite;

import android.content.Context;
import ch.qos.logback.core.CoreConstants;
import com.market.sdk.reflect.ReflectUtilsForMiui;
import com.miui.common.app.BaseNoteApp;
import com.miui.handwritecommon.api.bean.WebImage;
import com.miui.handwriting.common.tools.constants.Creation;
import com.miui.notes.excerpt.NotifyUtils;
import com.miui.notes.model.HandWriteEntity;
import com.miui.notes.provider.Notes;
import com.miui.notes.tool.PreferenceUtils;
import java.util.List;
import kotlin.Metadata;
import kotlin.jvm.JvmStatic;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: HandWriteWorkingNote.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000B\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0003\n\u0002\u0010\t\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u000b\n\u0002\u0010\u000e\n\u0002\b\u0006\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0007\u0018\u0000 *2\u00020\u0001:\u0001*B\u0007¢\u0006\u0004\b\u0002\u0010\u0003J\u0006\u0010\u0007\u001a\u00020\u0005J\u000e\u0010\b\u001a\u00020\t2\u0006\u0010\u0006\u001a\u00020\u0005J\u0010\u0010%\u001a\u00020\t2\u0006\u0010\u0004\u001a\u00020\u0005H\u0002J\u0006\u0010&\u001a\u00020\u001eJ\u0006\u0010'\u001a\u00020\u001eR\u0012\u0010\u0004\u001a\u00020\u00058\u0006@\u0006X\u0087\u000e¢\u0006\u0002\n\u0000R\u0012\u0010\u0006\u001a\u00020\u00058\u0006@\u0006X\u0087\u000e¢\u0006\u0002\n\u0000R\u001a\u0010\n\u001a\u00020\u000bX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\f\u0010\r\"\u0004\b\u000e\u0010\u000fR\u001a\u0010\u0010\u001a\u00020\u0005X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0011\u0010\u0012\"\u0004\b\u0013\u0010\u0014R\u0012\u0010\u0015\u001a\u00020\u00058\u0006@\u0006X\u0087\u000e¢\u0006\u0002\n\u0000R\u0014\u0010\u0016\u001a\u0004\u0018\u00010\u00178\u0006@\u0006X\u0087\u000e¢\u0006\u0002\n\u0000R\u0014\u0010\u0018\u001a\u0004\u0018\u00010\u00178\u0006@\u0006X\u0087\u000e¢\u0006\u0002\n\u0000R\u0014\u0010\u0019\u001a\u0004\u0018\u00010\u00178\u0006@\u0006X\u0087\u000e¢\u0006\u0002\n\u0000R\u0014\u0010\u001a\u001a\u0004\u0018\u00010\u00178\u0006@\u0006X\u0087\u000e¢\u0006\u0002\n\u0000R\u0014\u0010\u001b\u001a\u0004\u0018\u00010\u00178\u0006@\u0006X\u0087\u000e¢\u0006\u0002\n\u0000R\u0014\u0010\u001c\u001a\u0004\u0018\u00010\u00178\u0006@\u0006X\u0087\u000e¢\u0006\u0002\n\u0000R\u0012\u0010\u001d\u001a\u00020\u001e8\u0006@\u0006X\u0087\u000e¢\u0006\u0002\n\u0000R\u0014\u0010\u001f\u001a\u0004\u0018\u00010\u00178\u0006@\u0006X\u0087\u000e¢\u0006\u0002\n\u0000R\u001a\u0010 \u001a\n\u0012\u0004\u0012\u00020\u0017\u0018\u00010!8\u0006@\u0006X\u0087\u000e¢\u0006\u0002\n\u0000R\u0012\u0010\"\u001a\u00020\u00058\u0006@\u0006X\u0087\u000e¢\u0006\u0002\n\u0000R\u001a\u0010#\u001a\n\u0012\u0004\u0012\u00020$\u0018\u00010!8\u0006@\u0006X\u0087\u000e¢\u0006\u0002\n\u0000R\u0011\u0010(\u001a\u00020\u00058F¢\u0006\u0006\u001a\u0004\b)\u0010\u0012¨\u0006+"}, d2 = {"Lcom/miui/pad/feature/notes/handwrite/HandWriteWorkingNote;", "", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "()V", NotifyUtils.KEY_NOTE_ID, "", "folderId", "getFolderId", "setFolderId", "", CoreConstants.CONTEXT_SCOPE_VALUE, "Landroid/content/Context;", "getContext", "()Landroid/content/Context;", "setContext", "(Landroid/content/Context;)V", "createTime", "getCreateTime", "()J", "setCreateTime", "(J)V", "modifyTime", Creation.TEXT_CONTENT, "", "recognizedFile", "recognizedText", "entName", "title", "canvasBgType", "isEmptyContent", "", "thumbnail", Notes.Note.IMAGES, "", "syncId", "webImages", "Lcom/miui/handwritecommon/api/bean/WebImage;", "load", "saveHandWrite", "insertHandWrite", "sortDate", "getSortDate", "Companion", "app_PhoneGlobalRelease"}, k = 1, mv = {2, 2, 0}, xi = 48)
public final class HandWriteWorkingNote {

    /* JADX INFO: renamed from: Companion, reason: from kotlin metadata */
    public static final Companion INSTANCE = new Companion(null);
    public String canvasBgType;
    private long createTime;
    public String entName;
    public long folderId;
    public List<String> images;
    public boolean isEmptyContent;
    public long modifyTime;
    public String recognizedFile;
    public String recognizedText;
    public long syncId;
    public String textContent;
    public String thumbnail;
    public String title;
    public List<? extends WebImage> webImages;
    public long noteId = -2;
    private Context context = BaseNoteApp.INSTANCE.getInstance();

    @JvmStatic
    public static final HandWriteWorkingNote createNote(long j) {
        return INSTANCE.createNote(j);
    }

    @JvmStatic
    public static final HandWriteWorkingNote loadNote(long j) {
        return INSTANCE.loadNote(j);
    }

    public final long getFolderId() {
        return this.folderId;
    }

    public final void setFolderId(long folderId) {
        this.folderId = folderId;
    }

    public final Context getContext() {
        return this.context;
    }

    public final void setContext(Context context) {
        Intrinsics.checkNotNullParameter(context, "<set-?>");
        this.context = context;
    }

    public final long getCreateTime() {
        return this.createTime;
    }

    public final void setCreateTime(long j) {
        this.createTime = j;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void load(long noteId) {
        HandWriteDBHelper.HandWriteNoteInfo handWriteNoteInfoQuery = HandWriteDBHelper.query(this.context, "_id=" + noteId);
        this.noteId = noteId;
        if (handWriteNoteInfoQuery != null) {
            this.createTime = handWriteNoteInfoQuery.getCreatedDate();
            this.folderId = handWriteNoteInfoQuery.getParentId();
            this.modifyTime = handWriteNoteInfoQuery.getModifiedDate();
            this.syncId = handWriteNoteInfoQuery.getSyncId();
            String handWriteContent = handWriteNoteInfoQuery.getHandWriteContent();
            HandWriteEntity.Companion companion = HandWriteEntity.INSTANCE;
            Intrinsics.checkNotNull(handWriteContent);
            HandWriteEntity handWriteEntity = companion.toHandWriteEntity(handWriteContent);
            if (handWriteEntity != null) {
                this.textContent = handWriteEntity.textContent;
                this.recognizedFile = handWriteEntity.getRecognizedFile();
                this.recognizedText = handWriteEntity.recognizedText;
                this.entName = handWriteEntity.entName;
                this.title = handWriteEntity.title;
                this.canvasBgType = handWriteEntity.canvasBgType;
                this.isEmptyContent = handWriteEntity.isEmptyContent;
                this.thumbnail = handWriteEntity.thumbnail;
                this.images = handWriteEntity.images;
            }
        }
    }

    public final boolean saveHandWrite() {
        String str = this.title;
        Intrinsics.checkNotNull(str);
        String str2 = this.entName;
        Intrinsics.checkNotNull(str2);
        String str3 = this.thumbnail;
        List<String> list = this.images;
        boolean z = this.isEmptyContent;
        String str4 = this.canvasBgType;
        Intrinsics.checkNotNull(str4);
        return HandWriteDBHelper.updateHandWrite(this.noteId, this.folderId, new HandWriteEntity(str, str2, str3, list, z, str4, this.webImages), this.modifyTime);
    }

    public final boolean insertHandWrite() {
        if (this.isEmptyContent) {
            return false;
        }
        String str = this.title;
        Intrinsics.checkNotNull(str);
        String str2 = this.entName;
        Intrinsics.checkNotNull(str2);
        String str3 = this.thumbnail;
        List<String> list = this.images;
        boolean z = this.isEmptyContent;
        String str4 = this.canvasBgType;
        Intrinsics.checkNotNull(str4);
        this.noteId = HandWriteDBHelper.insertNewNote(this.folderId, new HandWriteEntity(str, str2, str3, list, z, str4, this.webImages), this.modifyTime);
        return true;
    }

    public final long getSortDate() {
        if (PreferenceUtils.getNoteSortWay(BaseNoteApp.INSTANCE.getInstance(), 1) == 0) {
            return this.createTime;
        }
        return this.modifyTime;
    }

    /* JADX INFO: compiled from: HandWriteWorkingNote.kt */
    @Metadata(d1 = {"\u0000\u001a\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\t\n\u0002\b\u0003\b\u0086\u0003\u0018\u00002\u00020\u0001B\t\b\u0002¢\u0006\u0004\b\u0002\u0010\u0003J\u0010\u0010\u0004\u001a\u00020\u00052\u0006\u0010\u0006\u001a\u00020\u0007H\u0007J\u0010\u0010\b\u001a\u00020\u00052\u0006\u0010\t\u001a\u00020\u0007H\u0007¨\u0006\n"}, d2 = {"Lcom/miui/pad/feature/notes/handwrite/HandWriteWorkingNote$Companion;", "", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "()V", "createNote", "Lcom/miui/pad/feature/notes/handwrite/HandWriteWorkingNote;", "foldId", "", "loadNote", NotifyUtils.KEY_NOTE_ID, "app_PhoneGlobalRelease"}, k = 1, mv = {2, 2, 0}, xi = 48)
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }

        @JvmStatic
        public final HandWriteWorkingNote createNote(long foldId) {
            HandWriteWorkingNote handWriteWorkingNote = new HandWriteWorkingNote();
            HandWriteEntity handWriteEntity = new HandWriteEntity("", "", "", null, true, "", null);
            handWriteWorkingNote.isEmptyContent = true;
            long jCurrentTimeMillis = System.currentTimeMillis();
            handWriteWorkingNote.noteId = HandWriteDBHelper.createNewNote(foldId, handWriteEntity, jCurrentTimeMillis);
            handWriteWorkingNote.folderId = foldId;
            handWriteWorkingNote.setCreateTime(jCurrentTimeMillis);
            handWriteWorkingNote.modifyTime = jCurrentTimeMillis;
            return handWriteWorkingNote;
        }

        @JvmStatic
        public final HandWriteWorkingNote loadNote(long noteId) {
            HandWriteWorkingNote handWriteWorkingNote = new HandWriteWorkingNote();
            handWriteWorkingNote.load(noteId);
            return handWriteWorkingNote;
        }
    }
}
