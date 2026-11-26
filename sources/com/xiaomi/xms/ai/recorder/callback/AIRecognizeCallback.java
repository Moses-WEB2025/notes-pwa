package com.xiaomi.xms.ai.recorder.callback;

import com.android.soundrecorder.ai.base.bean.AISentence;
import java.util.List;

/* JADX INFO: loaded from: classes4.dex */
public interface AIRecognizeCallback {
    void onError(int i, String str);

    void onEvent(int i, String str);

    void onInterrupt();

    void onRecognizeResultUpdate(List<AISentence> list, boolean z, boolean z2, boolean z3, String str);

    void onRecognizeStatusChange(int i, List<AISentence> list);

    void onRelease();
}
