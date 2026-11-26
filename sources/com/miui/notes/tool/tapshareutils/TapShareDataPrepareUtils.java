package com.miui.notes.tool.tapshareutils;

import android.content.Context;
import android.net.Uri;
import android.view.View;
import com.miui.common.tool.Logger;
import com.miui.mishare.tapshare.TapShareDataHandler;
import com.miui.notes.model.WorkingNote;
import com.miui.notes.ui.AlarmReceiver;
import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;

/* JADX INFO: loaded from: classes4.dex */
public class TapShareDataPrepareUtils {
    private static final String TAG = "TapShareDataPrepareUtils";

    public static TapShareDataModel.SingleNoteTapShareData prepareSingleNoteDataWithPreview(Context context, WorkingNote workingNote, File file, View view) {
        File cacheDir;
        if (context == null || !TapShareDataModel.isNoteValid(workingNote)) {
            Logger.INSTANCE.e(TAG, "Invalid parameters for prepareSingleNoteData");
            return null;
        }
        TapShareDataModel.SingleNoteTapShareData singleNoteTapShareDataCreateSingleNoteData = TapShareDataModel.createSingleNoteData(workingNote);
        if (singleNoteTapShareDataCreateSingleNoteData == null || (cacheDir = TapShareFileUtils.getCacheDir(context, file)) == null) {
            return null;
        }
        if (view != null) {
            singleNoteTapShareDataCreateSingleNoteData.previewImageUri = TapSharePreviewGenerator.captureViewToFile(context, view, cacheDir);
        }
        try {
            singleNoteTapShareDataCreateSingleNoteData.zipUri = TapShareZipGenerator.generateZipUri(context, workingNote, cacheDir);
            if (singleNoteTapShareDataCreateSingleNoteData.zipUri != null) {
                return singleNoteTapShareDataCreateSingleNoteData;
            }
            return null;
        } catch (IOException e) {
            Logger.INSTANCE.e(TAG, "Error generating ZIP file: " + e.getMessage());
            return null;
        }
    }

    public static TapShareDataModel.MultipleNotesTapShareData prepareMultipleNotesData(Context context, List<WorkingNote> list, File file) {
        TapShareDataModel.MultipleNotesTapShareData multipleNotesTapShareDataCreateMultipleNotesData;
        if (context == null || list == null || list.isEmpty()) {
            Logger.INSTANCE.e(TAG, "Invalid parameters for prepareMultipleNotesData");
            return null;
        }
        File cacheDir = TapShareFileUtils.getCacheDir(context, file);
        if (cacheDir == null || (multipleNotesTapShareDataCreateMultipleNotesData = TapShareDataModel.createMultipleNotesData(list)) == null) {
            return null;
        }
        Iterator<WorkingNote> it = list.iterator();
        while (it.hasNext()) {
            processNoteForSharing(context, it.next(), cacheDir, multipleNotesTapShareDataCreateMultipleNotesData);
        }
        if (multipleNotesTapShareDataCreateMultipleNotesData.zipUris.isEmpty()) {
            return null;
        }
        return multipleNotesTapShareDataCreateMultipleNotesData;
    }

    public static boolean shareSingleNote(Context context, TapShareDataModel.SingleNoteTapShareData singleNoteTapShareData, TapShareDataHandler tapShareDataHandler) {
        return TapShareSender.shareSingleNote(context, singleNoteTapShareData, tapShareDataHandler);
    }

    public static boolean shareMultipleNotes(Context context, TapShareDataModel.MultipleNotesTapShareData multipleNotesTapShareData, TapShareDataHandler tapShareDataHandler) {
        return TapShareSender.shareMultipleNotes(context, multipleNotesTapShareData, tapShareDataHandler);
    }

    public static WorkingNote restoreSingleNoteFromUri(Context context, Uri uri) {
        WorkingNote workingNoteRestoreSingleNoteFromUri = TapShareRestorer.restoreSingleNoteFromUri(context, uri);
        scheduleAlertIfNeeded(context, workingNoteRestoreSingleNoteFromUri);
        return workingNoteRestoreSingleNoteFromUri;
    }

    public static List<Long> restoreNotesFromUris(Context context, List<Uri> list) {
        List<Long> listRestoreNotesFromUris = TapShareRestorer.restoreNotesFromUris(context, list);
        scheduleAlertsForNotes(context, listRestoreNotesFromUris);
        return listRestoreNotesFromUris;
    }

    public static WorkingNote restoreNoteFromIOS(Context context, Uri uri) {
        return TapShareRestorer.restoreNoteFromIOS(context, uri);
    }

    private static void scheduleAlertIfNeeded(Context context, WorkingNote workingNote) {
        if (context == null || workingNote == null) {
            return;
        }
        long alertDate = workingNote.getAlertDate();
        if (alertDate > System.currentTimeMillis()) {
            AlarmReceiver.scheduleAlert(context, workingNote.getNoteId(), alertDate, false);
            Logger.INSTANCE.d(TAG, "Scheduled alert for restored note, ID: " + workingNote.getNoteId() + ", alertDate: " + alertDate);
        }
    }

    private static void scheduleAlertsForNotes(Context context, List<Long> list) {
        if (context == null || list == null || list.isEmpty()) {
            return;
        }
        for (Long l : list) {
            try {
                WorkingNote workingNoteLoadNote = WorkingNote.loadNote("_id=" + l, null);
                if (workingNoteLoadNote != null) {
                    scheduleAlertIfNeeded(context, workingNoteLoadNote);
                }
            } catch (Exception e) {
                Logger.INSTANCE.e(TAG, "Failed to schedule alert for note ID: " + l + ", error: " + e.getMessage());
            }
        }
    }

    private static void processNoteForSharing(Context context, WorkingNote workingNote, File file, TapShareDataModel.MultipleNotesTapShareData multipleNotesTapShareData) {
        if (!TapShareDataModel.isNoteValid(workingNote)) {
            Logger.INSTANCE.w(TAG, "Skipping invalid note");
            return;
        }
        try {
            Uri uriGenerateZipUri = TapShareZipGenerator.generateZipUri(context, workingNote, file);
            if (uriGenerateZipUri != null) {
                multipleNotesTapShareData.zipUris.add(uriGenerateZipUri);
                TapShareDataModel.addPreviewInfo(multipleNotesTapShareData, workingNote);
            }
        } catch (IOException e) {
            Logger.INSTANCE.e(TAG, "Error generating ZIP file: " + e.getMessage());
        }
    }
}
