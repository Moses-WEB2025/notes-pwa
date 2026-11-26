package miuix.media;

/* JADX INFO: loaded from: classes5.dex */
class LocalAMRRecorder extends LocalMediaRecorder {
    private static final String AMR_NB_HEADER = "#!AMR\n";
    private static final String AMR_WB_HEADER = "#!AMR-WB\n";
    private boolean mIsAmrWB;

    @Override // miuix.media.LocalMediaRecorder, miuix.media.Recorder
    public boolean canPause() {
        return true;
    }

    LocalAMRRecorder() {
    }

    @Override // miuix.media.LocalMediaRecorder, miuix.media.Recorder
    public void setAudioEncoder(int i) {
        super.setAudioEncoder(i);
        if (i == 2) {
            this.mIsAmrWB = true;
        } else if (i == 1) {
            this.mIsAmrWB = false;
        }
    }

    @Override // miuix.media.LocalMediaRecorder, miuix.media.Recorder
    public void reset() {
        super.reset();
        this.mIsAmrWB = false;
    }

    @Override // miuix.media.LocalMediaRecorder, miuix.media.Recorder
    public void release() {
        super.release();
        this.mIsAmrWB = false;
    }

    @Override // miuix.media.LocalMediaRecorder
    protected int getHeaderLen() {
        return (this.mIsAmrWB ? AMR_WB_HEADER : AMR_NB_HEADER).length();
    }
}
