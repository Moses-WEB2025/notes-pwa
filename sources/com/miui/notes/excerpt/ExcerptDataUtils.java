package com.miui.notes.excerpt;

import android.content.Context;
import android.net.Uri;
import android.text.SpannableStringBuilder;
import com.bumptech.glide.Glide;
import com.miui.notes.NoteApp;
import com.miui.notes.base.utils.Logger;
import com.miui.notes.editor.NoteRichEditor;
import com.miui.notes.model.WorkingNote;
import com.miui.notes.theme.util.ResourceManager;
import com.miui.notes.tool.AttachmentUtils;
import com.miui.richeditor.schema.NoteSchema;
import com.miui.richeditor.schema.RichEditorContentLiteHandler;
import com.miui.richeditor.util.NoteClipManager;
import java.io.File;
import java.util.List;
import java.util.UUID;
import org.apache.commons.lang3.StringUtils;

/* JADX INFO: loaded from: classes4.dex */
public class ExcerptDataUtils {
    private static final String TAG = "ExcerptDataUtils";

    public static long saveHtmlExcerpt(HtmlResult htmlResult, String str, UUID uuid) {
        Logger.INSTANCE.d(TAG, "saveHtmlExcerpt:" + uuid + ",." + str);
        NoteSchema.SimpleBuilder simpleBuilder = new NoteSchema.SimpleBuilder(NoteApp.getInstance());
        String title = htmlResult.getTitle();
        List<HtmlResultLine> list = htmlResult.getList();
        if (list != null) {
            for (HtmlResultLine htmlResultLine : list) {
                if (htmlResultLine.getType() == 0) {
                    simpleBuilder.appendText(htmlResultLine.getLineContent());
                } else {
                    addImage(htmlResultLine.getLineContent(), simpleBuilder);
                }
                simpleBuilder.appendNewLine();
            }
        }
        simpleBuilder.appendLink(str, title);
        simpleBuilder.appendNewLine();
        long jGenerateExcerpt = generateExcerpt(simpleBuilder, title);
        NotifyUtils.getInstance().notifyExcerptLinkResult(uuid, true, jGenerateExcerpt);
        return jGenerateExcerpt;
    }

    public static long saveClipDataExcerpt(Context context, UUID uuid, boolean z) {
        Logger.INSTANCE.d(TAG, "saveClipDataExcerpt:,isHybridEditor:" + z);
        NoteClipManager.INSTANCE.getClipData(context, z);
        NoteSchema.SimpleBuilder simpleBuilder = new NoteSchema.SimpleBuilder(NoteApp.getInstance());
        simpleBuilder.appendText(StringUtils.SPACE);
        long jGenerateExcerpt = generateExcerpt(simpleBuilder, "");
        NotifyUtils.getInstance().notifyExcerptLinkResult(uuid, true, jGenerateExcerpt);
        NoteClipManager.INSTANCE.updateExcerptByClipData(context, new NoteSchema.SimpleBuilder(NoteApp.getInstance()), WorkingNote.loadNote("_id=" + jGenerateExcerpt, null));
        return jGenerateExcerpt;
    }

    public static long saveTextExcerpt(String str, UUID uuid) {
        NoteSchema.SimpleBuilder simpleBuilder = new NoteSchema.SimpleBuilder(NoteApp.getInstance());
        simpleBuilder.appendText(str);
        simpleBuilder.appendNewLine();
        long jGenerateExcerpt = generateExcerpt(simpleBuilder, "");
        NotifyUtils.getInstance().notifyExcerptLinkResult(uuid, true, jGenerateExcerpt);
        return jGenerateExcerpt;
    }

    public static long saveImageExcerpt(String str, UUID uuid) {
        NoteSchema.SimpleBuilder simpleBuilder = new NoteSchema.SimpleBuilder(NoteApp.getInstance());
        addImage(str, simpleBuilder);
        simpleBuilder.appendNewLine();
        long jGenerateExcerpt = generateExcerpt(simpleBuilder, "");
        NotifyUtils.getInstance().notifyExcerptLinkResult(uuid, true, jGenerateExcerpt);
        return jGenerateExcerpt;
    }

    public static long saveLinkExcerpt(String str, String str2, UUID uuid) {
        Logger.INSTANCE.d(TAG, "saveLinkExcerpt " + uuid + ",." + str + "," + str2);
        NoteSchema.SimpleBuilder simpleBuilder = new NoteSchema.SimpleBuilder(NoteApp.getInstance());
        simpleBuilder.appendLink(str, str2);
        simpleBuilder.appendNewLine();
        long jGenerateExcerpt = generateExcerpt(simpleBuilder, "");
        NotifyUtils.getInstance().notifyExcerptLinkResult(uuid, true, jGenerateExcerpt);
        return jGenerateExcerpt;
    }

    public static long updateExcerptLink(long j, String str, String str2) {
        Logger.INSTANCE.d(TAG, "updateExcerptLink " + j + ",." + str + "," + str2);
        WorkingNote workingNoteLoadNote = WorkingNote.loadNote("_id=" + j, null);
        new NoteRichEditor(NoteApp.getInstance()).onThemeChanged(ResourceManager.getTheme(workingNoteLoadNote.getThemeId()));
        SpannableStringBuilder spannableStringBuilder = new SpannableStringBuilder(NoteSchema.parseAncient(workingNoteLoadNote.getContent(), new RichEditorContentLiteHandler(), NoteApp.getInstance()));
        NoteSchema.SimpleBuilder simpleBuilder = new NoteSchema.SimpleBuilder(NoteApp.getInstance());
        simpleBuilder.appendText(spannableStringBuilder);
        simpleBuilder.appendLink(str, str2);
        simpleBuilder.appendNewLine();
        return updateExcerpt(simpleBuilder, workingNoteLoadNote);
    }

    public static long updateExcerpt(long j, ExcerptInfo excerptInfo) {
        WorkingNote workingNoteLoadNote = WorkingNote.loadNote("_id=" + j, null);
        SpannableStringBuilder spannableStringBuilder = new SpannableStringBuilder(NoteSchema.parseAncient(workingNoteLoadNote.getContent(), new RichEditorContentLiteHandler(), NoteApp.getInstance()));
        NoteSchema.SimpleBuilder simpleBuilder = new NoteSchema.SimpleBuilder(NoteApp.getInstance());
        simpleBuilder.appendText(spannableStringBuilder);
        String excerptContent = excerptInfo.getExcerptContent();
        if (excerptInfo.getExcerptType() == 0) {
            simpleBuilder.appendText(excerptContent);
        } else {
            addImage(excerptContent, simpleBuilder);
        }
        simpleBuilder.appendNewLine();
        return updateExcerpt(simpleBuilder, workingNoteLoadNote);
    }

    public static void updateExcerptByClip(Context context, long j, ExcerptInfo excerptInfo, boolean z) {
        NoteClipManager.INSTANCE.getClipData(context, z);
        WorkingNote workingNoteLoadNote = WorkingNote.loadNote("_id=" + j, null);
        SpannableStringBuilder spannableStringBuilder = new SpannableStringBuilder(NoteSchema.parseAncient(workingNoteLoadNote.getContent(), new RichEditorContentLiteHandler(), NoteApp.getInstance()));
        NoteSchema.SimpleBuilder simpleBuilder = new NoteSchema.SimpleBuilder(NoteApp.getInstance());
        simpleBuilder.appendText(spannableStringBuilder);
        String excerptContent = excerptInfo.getExcerptContent();
        if (excerptInfo.getExcerptType() == 0) {
            NoteClipManager.INSTANCE.updateExcerptByClipData(context, simpleBuilder, workingNoteLoadNote);
            return;
        }
        addImage(excerptContent, simpleBuilder);
        simpleBuilder.appendNewLine();
        updateExcerpt(simpleBuilder, workingNoteLoadNote);
    }

    public static void addImage(String str, NoteSchema.SimpleBuilder simpleBuilder) {
        String strSaveImageFile;
        try {
            if (LinkUtils.isAccurateValidUrl(str)) {
                File file = Glide.with(NoteApp.getInstance()).downloadOnly().load(str).submit().get();
                if (MediaUtils.isSkipImg(file.getAbsolutePath())) {
                    return;
                } else {
                    strSaveImageFile = AttachmentUtils.saveImageFile(NoteApp.getInstance(), file);
                }
            } else {
                strSaveImageFile = AttachmentUtils.saveImageFile((Context) NoteApp.getInstance(), Uri.fromFile(new File(str)), true);
            }
            if (strSaveImageFile != null) {
                simpleBuilder.appendImage(strSaveImageFile);
            }
        } catch (Exception e) {
            Logger.INSTANCE.d(TAG, e.toString());
        }
    }

    public static void addLocalImage(Uri uri, NoteSchema.SimpleBuilder simpleBuilder) throws Throwable {
        try {
            String strSaveImageFile = AttachmentUtils.saveImageFile((Context) NoteApp.getInstance(), uri, true);
            if (strSaveImageFile != null) {
                simpleBuilder.appendImage(strSaveImageFile);
            }
        } catch (Exception e) {
            Logger.INSTANCE.d(TAG, e.toString());
        }
    }

    public static long generateExcerpt(NoteSchema.SimpleBuilder simpleBuilder, String str) {
        Logger.INSTANCE.d(TAG, "generateExcerpt:" + str);
        String ancient = simpleBuilder.toAncient();
        WorkingNote workingNoteCreateEmptyNote = WorkingNote.createEmptyNote(0L, 0, -1, 0);
        workingNoteCreateEmptyNote.setWorkingText(ancient);
        workingNoteCreateEmptyNote.setWorkingTitle(str);
        workingNoteCreateEmptyNote.setFolderId(-6L);
        return workingNoteCreateEmptyNote.saveNoteWithId(true);
    }

    public static long updateExcerpt(NoteSchema.SimpleBuilder simpleBuilder, WorkingNote workingNote) {
        workingNote.setWorkingText(simpleBuilder.toAncient());
        Logger.INSTANCE.d(TAG, "updateExcerpt: " + workingNote);
        return workingNote.saveNoteWithId(true);
    }
}
