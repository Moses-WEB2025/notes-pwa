package com.miui.notes.tool.tapshareutils;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.ResultReceiver;
import com.miui.common.tool.Logger;
import com.miui.notes.model.WorkingNote;
import java.util.ArrayList;
import java.util.List;
import net.lingala.zip4j.util.InternalZipConstants;

/* JADX INFO: loaded from: classes4.dex */
public class TapShareRestoreService extends IntentService {
    private static final String ACTION_RESTORE_FROM_IOS = "com.miui.notes.action.RESTORE_FROM_IOS";
    private static final String ACTION_RESTORE_MULTIPLE = "com.miui.notes.action.RESTORE_MULTIPLE";
    private static final String ACTION_RESTORE_SINGLE = "com.miui.notes.action.RESTORE_SINGLE";
    private static final String EXTRA_RESULT_RECEIVER = "extra_result_receiver";
    private static final String EXTRA_ZIP_URI = "extra_zip_uri";
    private static final String EXTRA_ZIP_URIS = "extra_zip_uris";
    public static final String KEY_ERROR_MESSAGE = "error_message";
    public static final String KEY_NOTE_ID = "note_id";
    public static final String KEY_NOTE_IDS = "note_ids";
    public static final int RESULT_ERROR = 1;
    public static final int RESULT_SUCCESS = 0;
    private static final String TAG = "TapShareRestoreService";

    public TapShareRestoreService() {
        super(TAG);
    }

    public static void startRestoreSingle(Context context, Uri uri, ResultReceiver resultReceiver) {
        Intent intent = new Intent(context, (Class<?>) TapShareRestoreService.class);
        intent.setAction(ACTION_RESTORE_SINGLE);
        intent.putExtra(EXTRA_ZIP_URI, uri);
        intent.putExtra(EXTRA_RESULT_RECEIVER, resultReceiver);
        context.startService(intent);
    }

    public static void startRestoreMultiple(Context context, ArrayList<Uri> arrayList, ResultReceiver resultReceiver) {
        Intent intent = new Intent(context, (Class<?>) TapShareRestoreService.class);
        intent.setAction(ACTION_RESTORE_MULTIPLE);
        intent.putParcelableArrayListExtra(EXTRA_ZIP_URIS, arrayList);
        intent.putExtra(EXTRA_RESULT_RECEIVER, resultReceiver);
        context.startService(intent);
    }

    public static void startRestoreFromIOS(Context context, Uri uri, ResultReceiver resultReceiver) {
        Intent intent = new Intent(context, (Class<?>) TapShareRestoreService.class);
        intent.setAction(ACTION_RESTORE_FROM_IOS);
        intent.putExtra(EXTRA_ZIP_URI, uri);
        intent.putExtra(EXTRA_RESULT_RECEIVER, resultReceiver);
        context.startService(intent);
    }

    @Override // android.app.IntentService
    protected void onHandleIntent(Intent intent) throws Throwable {
        if (intent == null) {
            Logger.INSTANCE.e(TAG, "Intent is null");
            return;
        }
        String action = intent.getAction();
        ResultReceiver resultReceiver = (ResultReceiver) intent.getParcelableExtra(EXTRA_RESULT_RECEIVER);
        if (action == null) {
            sendError(resultReceiver, "Action is null");
            return;
        }
        try {
            int iHashCode = action.hashCode();
            if (iHashCode != -860671597) {
                if (iHashCode != 73926755) {
                    if (iHashCode == 1971136667 && action.equals(ACTION_RESTORE_MULTIPLE)) {
                        handleRestoreMultiple(intent, resultReceiver);
                        return;
                    }
                } else if (action.equals(ACTION_RESTORE_FROM_IOS)) {
                    handleRestoreFromIOS(intent, resultReceiver);
                    return;
                }
            } else if (action.equals(ACTION_RESTORE_SINGLE)) {
                handleRestoreSingle(intent, resultReceiver);
                return;
            }
            sendError(resultReceiver, "Unknown action: " + action);
        } catch (Exception e) {
            Logger.INSTANCE.e(TAG, "Error handling intent: " + e.getMessage());
            sendError(resultReceiver, e.getMessage());
        }
    }

    private void handleRestoreSingle(Intent intent, ResultReceiver resultReceiver) {
        Uri uri = (Uri) intent.getParcelableExtra(EXTRA_ZIP_URI);
        if (uri == null) {
            sendError(resultReceiver, "ZIP URI is null");
            return;
        }
        Logger.INSTANCE.d(TAG, "Starting single note restore from URI: " + uri);
        WorkingNote workingNoteRestoreSingleNoteFromUri = TapShareRestorer.restoreSingleNoteFromUri(getApplicationContext(), uri);
        if (workingNoteRestoreSingleNoteFromUri != null && workingNoteRestoreSingleNoteFromUri.getNoteId() > 0) {
            notifyDataChanged();
            Bundle bundle = new Bundle();
            bundle.putLong("note_id", workingNoteRestoreSingleNoteFromUri.getNoteId());
            sendSuccess(resultReceiver, bundle);
            Logger.INSTANCE.d(TAG, "Single note restored successfully, ID: " + workingNoteRestoreSingleNoteFromUri.getNoteId());
            return;
        }
        sendError(resultReceiver, "Failed to restore note");
        Logger.INSTANCE.e(TAG, "Failed to restore single note");
    }

    private void handleRestoreMultiple(Intent intent, ResultReceiver resultReceiver) {
        ArrayList parcelableArrayListExtra = intent.getParcelableArrayListExtra(EXTRA_ZIP_URIS);
        if (parcelableArrayListExtra == null || parcelableArrayListExtra.isEmpty()) {
            sendError(resultReceiver, "ZIP URIs are null or empty");
            return;
        }
        Logger.INSTANCE.d(TAG, "Starting multiple notes restore, count: " + parcelableArrayListExtra.size());
        List<Long> listRestoreNotesFromUris = TapShareRestorer.restoreNotesFromUris(getApplicationContext(), parcelableArrayListExtra);
        notifyDataChanged();
        if (listRestoreNotesFromUris != null && listRestoreNotesFromUris.size() == parcelableArrayListExtra.size()) {
            Bundle bundle = new Bundle();
            bundle.putSerializable(KEY_NOTE_IDS, new ArrayList(listRestoreNotesFromUris));
            sendSuccess(resultReceiver, bundle);
            Logger.INSTANCE.d(TAG, "All notes restored successfully, count: " + listRestoreNotesFromUris.size());
            return;
        }
        String str = "Failed to restore all notes: " + (listRestoreNotesFromUris != null ? listRestoreNotesFromUris.size() : 0) + InternalZipConstants.ZIP_FILE_SEPARATOR + parcelableArrayListExtra.size() + " succeeded";
        sendError(resultReceiver, str);
        Logger.INSTANCE.e(TAG, str);
    }

    private void handleRestoreFromIOS(Intent intent, ResultReceiver resultReceiver) throws Throwable {
        Uri uri = (Uri) intent.getParcelableExtra(EXTRA_ZIP_URI);
        if (uri == null) {
            sendError(resultReceiver, "ZIP URI is null");
            return;
        }
        Logger.INSTANCE.d(TAG, "Starting iOS note restore from URI: " + uri);
        WorkingNote workingNoteRestoreNoteFromIOS = TapShareRestorer.restoreNoteFromIOS(getApplicationContext(), uri);
        if (workingNoteRestoreNoteFromIOS != null && workingNoteRestoreNoteFromIOS.getNoteId() > 0) {
            notifyDataChanged();
            Bundle bundle = new Bundle();
            bundle.putLong("note_id", workingNoteRestoreNoteFromIOS.getNoteId());
            sendSuccess(resultReceiver, bundle);
            Logger.INSTANCE.d(TAG, "iOS note restored successfully, ID: " + workingNoteRestoreNoteFromIOS.getNoteId());
            return;
        }
        sendError(resultReceiver, "Failed to restore iOS note");
        Logger.INSTANCE.e(TAG, "Failed to restore iOS note");
    }

    private void notifyDataChanged() {
        try {
            getContentResolver().notifyChange(Uri.parse("content://com.miui.notes/note"), null);
            Logger.INSTANCE.d(TAG, "Data change notification sent");
        } catch (Exception e) {
            Logger.INSTANCE.e(TAG, "Failed to send data change notification: " + e.getMessage());
        }
    }

    private void sendSuccess(ResultReceiver resultReceiver, Bundle bundle) {
        if (resultReceiver != null) {
            resultReceiver.send(0, bundle);
        }
    }

    private void sendError(ResultReceiver resultReceiver, String str) {
        sendError(resultReceiver, str, null);
    }

    private void sendError(ResultReceiver resultReceiver, String str, Bundle bundle) {
        if (resultReceiver != null) {
            if (bundle == null) {
                bundle = new Bundle();
            }
            bundle.putString(KEY_ERROR_MESSAGE, str);
            resultReceiver.send(1, bundle);
        }
    }
}
