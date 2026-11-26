package com.xiaomi.xms.ai.recorder.callback;

import android.os.RemoteException;
import com.android.soundrecorder.ai.base.IAIRecognizeCallback;
import com.android.soundrecorder.ai.base.bean.AISentence;
import com.xiaomi.xms.ai.recorder.AIRecorderManager;
import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;

/* JADX INFO: loaded from: classes4.dex */
public class AIRecognizeCallbackStubImpl extends IAIRecognizeCallback.Stub {
    private AIRecognizeCallback mAIRecognizeCallback;

    public AIRecognizeCallbackStubImpl(AIRecognizeCallback aIRecognizeCallback) {
        this.mAIRecognizeCallback = aIRecognizeCallback;
    }

    @Override // com.android.soundrecorder.ai.base.IAIRecognizeCallback
    public void onRecognizeError(final int i, final String str) throws RemoteException {
        Optional.ofNullable(this.mAIRecognizeCallback).ifPresent(new Consumer() { // from class: com.xiaomi.xms.ai.recorder.callback.AIRecognizeCallbackStubImpl$$ExternalSyntheticLambda0
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                ((AIRecognizeCallback) obj).onError(i, str);
            }
        });
    }

    @Override // com.android.soundrecorder.ai.base.IAIRecognizeCallback
    public void onRecognizeEvent(final int i, final String str) throws RemoteException {
        Optional.ofNullable(this.mAIRecognizeCallback).ifPresent(new Consumer() { // from class: com.xiaomi.xms.ai.recorder.callback.AIRecognizeCallbackStubImpl$$ExternalSyntheticLambda5
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                ((AIRecognizeCallback) obj).onEvent(i, str);
            }
        });
    }

    @Override // com.android.soundrecorder.ai.base.IAIRecognizeCallback
    public void onRecognizeStatusChange(final int i, final List<AISentence> list) throws RemoteException {
        Optional.ofNullable(this.mAIRecognizeCallback).ifPresent(new Consumer() { // from class: com.xiaomi.xms.ai.recorder.callback.AIRecognizeCallbackStubImpl$$ExternalSyntheticLambda2
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                ((AIRecognizeCallback) obj).onRecognizeStatusChange(i, list);
            }
        });
    }

    @Override // com.android.soundrecorder.ai.base.IAIRecognizeCallback
    public void onRecognizeResultUpdate(final List<AISentence> list, final boolean z, final boolean z2, final boolean z3) throws RemoteException {
        final String currentFileSha1 = z3 ? AIRecorderManager.instance.getCurrentFileSha1() : "";
        Optional.ofNullable(this.mAIRecognizeCallback).ifPresent(new Consumer() { // from class: com.xiaomi.xms.ai.recorder.callback.AIRecognizeCallbackStubImpl$$ExternalSyntheticLambda1
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                ((AIRecognizeCallback) obj).onRecognizeResultUpdate(list, z, z2, z3, currentFileSha1);
            }
        });
    }

    public void resetCallback(boolean z) {
        if (z) {
            Optional.ofNullable(this.mAIRecognizeCallback).ifPresent(new Consumer() { // from class: com.xiaomi.xms.ai.recorder.callback.AIRecognizeCallbackStubImpl$$ExternalSyntheticLambda3
                @Override // java.util.function.Consumer
                public final void accept(Object obj) {
                    ((AIRecognizeCallback) obj).onInterrupt();
                }
            });
        } else {
            Optional.ofNullable(this.mAIRecognizeCallback).ifPresent(new Consumer() { // from class: com.xiaomi.xms.ai.recorder.callback.AIRecognizeCallbackStubImpl$$ExternalSyntheticLambda4
                @Override // java.util.function.Consumer
                public final void accept(Object obj) {
                    ((AIRecognizeCallback) obj).onRelease();
                }
            });
        }
        this.mAIRecognizeCallback = null;
    }
}
