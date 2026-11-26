package com.miui.notes.tool.tapshareutils;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.ResultReceiver;
import com.miui.common.tool.Logger;
import java.util.ArrayList;
import java.util.List;

/* JADX INFO: loaded from: classes4.dex */
public class TapShareRestoreHelper {
    private static final String TAG = "TapShareRestoreHelper";

    public interface RestoreCallback {
        void onError(String str);

        void onMultipleSuccess(List<Long> list);

        void onSuccess(long j);
    }

    public static void restoreSingleNote(Context context, Uri uri, RestoreCallback restoreCallback) {
        TapShareRestoreService.startRestoreSingle(context, uri, new RestoreResultReceiver(new Handler(Looper.getMainLooper()), restoreCallback, false));
    }

    public static void restoreMultipleNotes(Context context, List<Uri> list, RestoreCallback restoreCallback) {
        TapShareRestoreService.startRestoreMultiple(context, new ArrayList(list), new RestoreResultReceiver(new Handler(Looper.getMainLooper()), restoreCallback, true));
    }

    public static void restoreFromIOS(Context context, Uri uri, RestoreCallback restoreCallback) {
        TapShareRestoreService.startRestoreFromIOS(context, uri, new RestoreResultReceiver(new Handler(Looper.getMainLooper()), restoreCallback, false));
    }

    private static class RestoreResultReceiver extends ResultReceiver {
        private final RestoreCallback callback;
        private final boolean isMultiple;

        public RestoreResultReceiver(Handler handler, RestoreCallback restoreCallback, boolean z) {
            super(handler);
            this.callback = restoreCallback;
            this.isMultiple = z;
        }

        @Override // android.os.ResultReceiver
        protected void onReceiveResult(int i, Bundle bundle) {
            RestoreCallback restoreCallback = this.callback;
            if (restoreCallback == null) {
                Logger.INSTANCE.d(TapShareRestoreHelper.TAG, "Callback is null, ignoring result");
                return;
            }
            if (i == 0) {
                handleSuccess(bundle);
            } else if (i == 1) {
                handleError(bundle);
            } else {
                restoreCallback.onError("Unknown result code: " + i);
            }
        }

        private void handleSuccess(Bundle bundle) {
            if (this.isMultiple) {
                List<Long> list = (List) bundle.getSerializable(TapShareRestoreService.KEY_NOTE_IDS);
                if (list != null) {
                    this.callback.onMultipleSuccess(list);
                    return;
                } else {
                    this.callback.onError("Note IDs are null");
                    return;
                }
            }
            long j = bundle.getLong("note_id", -1L);
            if (j > 0) {
                this.callback.onSuccess(j);
            } else {
                this.callback.onError("Invalid note ID");
            }
        }

        private void handleError(Bundle bundle) {
            this.callback.onError(bundle.getString(TapShareRestoreService.KEY_ERROR_MESSAGE, "Unknown error"));
        }
    }
}
