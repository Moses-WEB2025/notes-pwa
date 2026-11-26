package com.xiaomi.ai;

/* JADX INFO: loaded from: classes4.dex */
public interface AsrListener {

    public static class SimpleAsrListener implements AsrListener {
        @Override // com.xiaomi.ai.AsrListener
        public void onBeginningOfSpeech() {
        }

        @Override // com.xiaomi.ai.AsrListener
        public void onBufferReceived(byte[] bArr) {
        }

        @Override // com.xiaomi.ai.AsrListener
        public void onEndOfSpeech() {
        }

        @Override // com.xiaomi.ai.AsrListener
        public void onError(SpeechError speechError) {
        }

        @Override // com.xiaomi.ai.AsrListener
        public void onEvent() {
        }

        @Override // com.xiaomi.ai.AsrListener
        public void onFileStored(String str, String str2) {
        }

        @Override // com.xiaomi.ai.AsrListener
        public void onPartialResults(SpeechResult speechResult) {
        }

        @Override // com.xiaomi.ai.AsrListener
        public void onReadyForSpeech() {
        }

        @Override // com.xiaomi.ai.AsrListener
        public void onResults(SpeechResult speechResult) {
        }

        @Override // com.xiaomi.ai.AsrListener
        public void onRmsChanged(float f) {
        }
    }

    void onBeginningOfSpeech();

    void onBufferReceived(byte[] bArr);

    void onEndOfSpeech();

    void onError(SpeechError speechError);

    void onEvent();

    void onFileStored(String str, String str2);

    void onPartialResults(SpeechResult speechResult);

    void onReadyForSpeech();

    void onResults(SpeechResult speechResult);

    void onRmsChanged(float f);
}
