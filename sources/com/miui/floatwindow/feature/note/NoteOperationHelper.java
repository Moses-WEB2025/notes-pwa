package com.miui.floatwindow.feature.note;

import android.app.Application;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Toast;
import com.google.gson.Gson;
import com.market.sdk.reflect.ReflectUtilsForMiui;
import com.miui.common.app.BaseNoteApp;
import com.miui.common.base.NoteLoadDataEntity;
import com.miui.common.tool.LiteUtils;
import com.miui.common.tool.Logger;
import com.miui.common.tool.RomUtils;
import com.miui.floatwindow.feature.root.FloatFullWindow;
import com.miui.notes.R;
import com.miui.notes.cache.NoteCache;
import com.miui.notes.constant.NoteIntent;
import com.miui.notes.excerpt.NotifyUtils;
import com.miui.notes.model.MindEntity;
import com.miui.notes.model.NoteEntity;
import com.miui.notes.model.NoteEntityHelper;
import com.miui.notes.model.NoteModel;
import com.miui.notes.provider.Notes;
import com.miui.notes.ui.NotesListActivity;
import com.miui.notes.ui.activity.EditActivity;
import com.miui.notes.ui.activity.PhoneHandWriteActivity;
import com.miui.notes.ui.activity.PhoneMultiHandWriteActivity;
import com.miui.notes.ui.activity.WebViewBrainActivity;
import com.miui.richeditor.EditorActionMode;
import kotlin.Metadata;
import kotlin.jvm.JvmStatic;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;

/* JADX INFO: compiled from: NoteOperationHelper.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000>\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0010\t\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0010\u000b\n\u0002\b\u0002\bÆ\u0002\u0018\u00002\u00020\u0001B\t\b\u0002¢\u0006\u0004\b\u0002\u0010\u0003J\u0006\u0010\b\u001a\u00020\tJ\u000e\u0010\n\u001a\u00020\t2\u0006\u0010\u000b\u001a\u00020\fJ\u000e\u0010\n\u001a\u00020\t2\u0006\u0010\r\u001a\u00020\u000eJ\u0016\u0010\u000f\u001a\u00020\t2\u0006\u0010\u0010\u001a\u00020\u00112\u0006\u0010\u0012\u001a\u00020\u0007J\u000e\u0010\u0013\u001a\u00020\t2\u0006\u0010\r\u001a\u00020\u000eJ\u000e\u0010\u0014\u001a\u00020\t2\u0006\u0010\r\u001a\u00020\u000eJ\u000e\u0010\u0015\u001a\u00020\t2\u0006\u0010\u0016\u001a\u00020\fJ\u0012\u0010\u0017\u001a\u00020\u00182\b\u0010\u0019\u001a\u0004\u0018\u00010\u0007H\u0007R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0007X\u0082T¢\u0006\u0002\n\u0000¨\u0006\u001a"}, d2 = {"Lcom/miui/floatwindow/feature/note/NoteOperationHelper;", "", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "()V", "sContext", "Landroid/app/Application;", "MIME_TYPE_TEXT_NOTE", "", "showAllNotes", "", "openNote", NotifyUtils.KEY_NOTE_ID, "", "noteCache", "Lcom/miui/notes/cache/NoteCache;", "openMindNote", "noteModel", "Lcom/miui/notes/model/NoteModel;", "mindEntity", "openHandWriteNote", "openMultiHandWriteNote", "addNote", "folderId", "hasNewStyle", "", "content", "app_PhoneGlobalRelease"}, k = 1, mv = {2, 2, 0}, xi = 48)
public final class NoteOperationHelper {
    private static final String MIME_TYPE_TEXT_NOTE = "vnd.android.cursor.item/text_note";
    public static final NoteOperationHelper INSTANCE = new NoteOperationHelper();
    private static final Application sContext = BaseNoteApp.INSTANCE.getInstance();

    private NoteOperationHelper() {
    }

    public final void showAllNotes() {
        Application application = sContext;
        Intent intent = new Intent(application, (Class<?>) NotesListActivity.class);
        intent.setAction(NoteIntent.ACTION_VIEW);
        intent.setFlags(335544320);
        application.startActivity(intent);
    }

    public final void openNote(long noteId) {
        Intent intent;
        Logger.INSTANCE.d(FloatFullWindow.TAG, " openNote: noteId=" + noteId + " ,to EditActivity");
        if (!RomUtils.isPadMode()) {
            intent = new Intent(sContext, (Class<?>) EditActivity.class);
            intent.setFlags(268484608);
        } else {
            intent = new Intent(sContext, (Class<?>) NotesListActivity.class);
            intent.setFlags(268484608);
        }
        intent.putExtra("android.intent.extra.UID", noteId);
        intent.putExtra(NoteIntent.KEY_FOLDER_ID, -6);
        intent.putExtra(NoteIntent.KEY_ACTION_MODE, EditorActionMode.VIEW);
        intent.putExtra(NoteIntent.KEY_IS_FROM_FLOAT_WINDOW, true);
        intent.putExtra(NoteIntent.KEY_NOTE_THEME_ID, 0);
        intent.setAction("android.intent.action.VIEW");
        intent.setType("vnd.android.cursor.item/text_note");
        sContext.startActivity(intent);
    }

    public final void openNote(NoteCache noteCache) {
        Intent intent;
        Intrinsics.checkNotNullParameter(noteCache, "noteCache");
        if (!RomUtils.isPadMode()) {
            intent = new Intent(sContext, (Class<?>) EditActivity.class);
            intent.setFlags(268484608);
        } else {
            intent = new Intent(sContext, (Class<?>) NotesListActivity.class);
            intent.setFlags(268484608);
        }
        intent.putExtra("android.intent.extra.UID", noteCache.getNote().getId());
        intent.putExtra(Notes.Intents.INTENT_EXTRA_NEW_STYLE, hasNewStyle(noteCache.getNote().getSnippet()));
        Application application = sContext;
        NoteEntity noteEntityLoad = NoteEntityHelper.load(application, "_id=" + noteCache.getNote().getId(), null);
        intent.putExtra(NoteIntent.KEY_FOLDER_ID, noteEntityLoad != null ? (int) noteEntityLoad.getParentId() : -6);
        intent.putExtra(NoteIntent.KEY_NOTE_THEME_ID, noteCache.getNote().getThemeId());
        intent.putExtra(NoteIntent.KEY_ACTION_MODE, EditorActionMode.VIEW);
        intent.putExtra(NoteIntent.KEY_IS_FROM_FLOAT_WINDOW, true);
        intent.setAction("android.intent.action.VIEW");
        intent.setType("vnd.android.cursor.item/text_note");
        application.startActivity(intent);
    }

    public final void openMindNote(NoteModel noteModel, String mindEntity) {
        int i;
        int i2;
        Intent intent;
        Intrinsics.checkNotNullParameter(noteModel, "noteModel");
        Intrinsics.checkNotNullParameter(mindEntity, "mindEntity");
        if (LiteUtils.isSuperLite()) {
            Toast.makeText(sContext, R.string.note_mindnote_not_support_in_phone_model, 0).show();
            return;
        }
        if (TextUtils.isEmpty(mindEntity)) {
            i = 0;
            i2 = 0;
        } else {
            Gson gson = new Gson();
            String strSubstring = mindEntity.substring(15);
            Intrinsics.checkNotNullExpressionValue(strSubstring, "substring(...)");
            i = !((MindEntity) gson.fromJson(strSubstring, MindEntity.class)).isMap ? 1 : 0;
            i2 = 1;
        }
        if (!RomUtils.isPadMode()) {
            intent = new Intent(sContext, (Class<?>) WebViewBrainActivity.class);
            intent.setFlags(268484608);
        } else {
            intent = new Intent(sContext, (Class<?>) NotesListActivity.class);
            intent.setFlags(268484608);
        }
        intent.putExtra("android.intent.extra.UID", noteModel.getId());
        intent.putExtra(Notes.Intents.INTENT_EXTRA_NEW_STYLE, hasNewStyle(noteModel.getSnippet()));
        intent.putExtra(NoteIntent.KEY_ACTION_MODE, EditorActionMode.VIEW);
        intent.putExtra(NoteIntent.KEY_IS_FROM_FLOAT_WINDOW, true);
        NoteLoadDataEntity noteLoadDataEntity = new NoteLoadDataEntity(noteModel.getId(), noteModel.getFolderId(), "", 1, noteModel.getUpdatedAt(), i, i2, noteModel.getTitle(), false, null);
        Bundle bundle = new Bundle();
        bundle.putSerializable("entity", noteLoadDataEntity);
        intent.putExtras(bundle);
        intent.setAction("android.intent.action.VIEW");
        sContext.startActivity(intent);
    }

    public final void openHandWriteNote(NoteCache noteCache) {
        Intrinsics.checkNotNullParameter(noteCache, "noteCache");
        if (!RomUtils.isPadMode()) {
            Application application = sContext;
            Intent intent = new Intent(application, (Class<?>) PhoneHandWriteActivity.class);
            intent.setFlags(268484608);
            intent.putExtra("android.intent.extra.UID", noteCache.getNote().getId());
            intent.putExtra(Notes.Intents.INTENT_EXTRA_NEW_STYLE, hasNewStyle(noteCache.getNote().getSnippet()));
            intent.setAction("android.intent.action.VIEW");
            application.startActivity(intent);
            return;
        }
        Application application2 = sContext;
        Intent intent2 = new Intent(application2, (Class<?>) NotesListActivity.class);
        intent2.setFlags(268484608);
        intent2.putExtra("android.intent.extra.UID", noteCache.getNote().getId());
        intent2.putExtra(Notes.Intents.INTENT_EXTRA_NEW_STYLE, hasNewStyle(noteCache.getNote().getSnippet()));
        intent2.putExtra(NoteIntent.KEY_IS_FROM_FLOAT_WINDOW, true);
        intent2.setAction("android.intent.action.VIEW");
        application2.startActivity(intent2);
    }

    public final void openMultiHandWriteNote(NoteCache noteCache) {
        Intrinsics.checkNotNullParameter(noteCache, "noteCache");
        if (!RomUtils.isPadMode()) {
            Application application = sContext;
            Intent intent = new Intent(application, (Class<?>) PhoneMultiHandWriteActivity.class);
            intent.setFlags(335544320);
            intent.putExtra("android.intent.extra.UID", noteCache.getNote().getId());
            intent.putExtra(Notes.Intents.INTENT_EXTRA_NEW_STYLE, hasNewStyle(noteCache.getNote().getSnippet()));
            intent.setAction("android.intent.action.VIEW");
            application.startActivity(intent);
            return;
        }
        Application application2 = sContext;
        Intent intent2 = new Intent(application2, (Class<?>) NotesListActivity.class);
        intent2.setFlags(335544320);
        intent2.putExtra("android.intent.extra.UID", noteCache.getNote().getId());
        intent2.putExtra(Notes.Intents.INTENT_EXTRA_NEW_STYLE, hasNewStyle(noteCache.getNote().getSnippet()));
        intent2.putExtra(NoteIntent.KEY_IS_FROM_FLOAT_WINDOW, true);
        intent2.setAction("android.intent.action.VIEW");
        application2.startActivity(intent2);
    }

    public final void addNote(long folderId) {
        if (!RomUtils.isPadMode()) {
            Application application = sContext;
            Intent intent = new Intent(application, (Class<?>) EditActivity.class);
            intent.setAction("android.intent.action.INSERT_OR_EDIT");
            intent.putExtra("com.miui.notes.folder_id", folderId);
            intent.putExtra(NoteIntent.KEY_ACTION_MODE, EditorActionMode.EDIT);
            intent.putExtra(NoteIntent.KEY_IS_FROM_FLOAT_WINDOW, true);
            intent.setFlags(268484608);
            intent.setType("vnd.android.cursor.item/text_note");
            application.startActivity(intent);
            return;
        }
        Application application2 = sContext;
        Intent intent2 = new Intent(application2, (Class<?>) NotesListActivity.class);
        intent2.setAction(NoteIntent.ACTION_PAD_INSERT_OR_EDIT);
        intent2.putExtra("com.miui.notes.folder_id", folderId);
        intent2.putExtra(NoteIntent.KEY_ACTION_MODE, EditorActionMode.EDIT);
        intent2.putExtra(NoteIntent.KEY_IS_FROM_FLOAT_WINDOW, true);
        intent2.setFlags(335544320);
        intent2.setType("vnd.android.cursor.item/text_note");
        application2.startActivity(intent2);
    }

    @JvmStatic
    public static final boolean hasNewStyle(String content) {
        if (!TextUtils.isEmpty(content)) {
            Intrinsics.checkNotNull(content);
            if (StringsKt.startsWith$default(content, "<new-format/>", false, 2, (Object) null)) {
                return true;
            }
        }
        return false;
    }
}
