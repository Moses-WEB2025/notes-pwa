package miuix.media;

/* JADX INFO: loaded from: classes5.dex */
public class RecorderFactory {

    public interface AudioFormat {
        public static final int AAC = 1;
        public static final int AMR = 3;
        public static final int DEFAULT = 1;
        public static final int MP3 = 2;
        public static final int THREE_GPP = 4;
        public static final int WAV = 5;
    }

    private RecorderFactory() {
    }

    public static Recorder getRecorder(int i) {
        if (i == 1) {
            return new LocalAACRecorder();
        }
        if (i == 2) {
            return new LocalMp3Recorder();
        }
        if (i == 3) {
            return new LocalAMRRecorder();
        }
        if (i == 4) {
            return new LocalMediaRecorder();
        }
        if (i == 5) {
            return new LocalWavRecorder();
        }
        return new LocalAACRecorder();
    }
}
