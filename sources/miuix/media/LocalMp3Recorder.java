package miuix.media;

/* JADX INFO: loaded from: classes5.dex */
class LocalMp3Recorder extends Mp3Recorder implements Recorder {
    private Recorder.OnErrorListener mOnErrorListener;

    @Override // miuix.media.Recorder
    public boolean canPause() {
        return true;
    }

    @Override // miuix.media.Recorder
    public void setAudioEncoder(int i) {
    }

    @Override // miuix.media.Recorder
    public void setOutputFormat(int i) {
    }

    LocalMp3Recorder() {
    }

    @Override // miuix.media.Recorder
    public void setAudioEncodingBitRate(int i) {
        super.setOutBitRate(i);
    }

    @Override // miuix.media.Recorder
    public void setMaxDuration(int i) {
        super.setMaxDuration(i);
    }

    @Override // miuix.media.Recorder
    public void setOnErrorListener(Recorder.OnErrorListener onErrorListener) {
        this.mOnErrorListener = onErrorListener;
        super.setOnErrorListener(new Mp3Recorder.RecordingErrorListener() { // from class: miuix.media.LocalMp3Recorder.1
            @Override // miuix.media.Mp3Recorder.RecordingErrorListener
            public void onError(Mp3Recorder mp3Recorder, int i) {
                LocalMp3Recorder.this.mOnErrorListener.onError(LocalMp3Recorder.this, RecorderUtils.convertErrorCode(i, false));
            }
        });
    }
}
