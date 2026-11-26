package miuix.media;

/* JADX INFO: loaded from: classes5.dex */
class LocalAACRecorder extends LocalMediaRecorder {
    @Override // miuix.media.LocalMediaRecorder, miuix.media.Recorder
    public boolean canPause() {
        return true;
    }

    LocalAACRecorder() {
    }

    @Override // miuix.media.LocalMediaRecorder, miuix.media.Recorder
    public void setAudioSource(int i) {
        super.setAudioSource(i);
        setOutputFormat(6);
        setAudioEncoder(3);
    }
}
