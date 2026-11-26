package com.miui.pad.feature.notes.handwrite;

import android.os.AsyncTask;
import com.miui.common.tool.RomUtils;
import com.miui.notes.base.utils.Logger;

/* JADX INFO: loaded from: classes4.dex */
public class RecognizeEngine {
    private static RecognizeEngine recognizeEngine;

    public static void destroy() {
    }

    private RecognizeEngine() {
        Logger.INSTANCE.d("RecognizeEngine", "RecognizeEngine initRecognizeEngine");
        if (!RomUtils.isPadDevice() || RomUtils.isInternationalBuild()) {
            return;
        }
        AsyncTask.execute(new Runnable() { // from class: com.miui.pad.feature.notes.handwrite.RecognizeEngine$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                RecognizeEngine.lambda$new$0();
            }
        });
    }

    static /* synthetic */ void lambda$new$0() {
        Thread.currentThread().setName("recognizeEngine");
        Logger.INSTANCE.d("RecognizeEngine", "RecognizeEngine initRecognizeEngine--doInBackground");
    }

    public static synchronized void initRecognizeEngine() {
        if (recognizeEngine == null) {
            recognizeEngine = new RecognizeEngine();
        }
    }
}
