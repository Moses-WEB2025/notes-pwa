package com.xiaomi.ai.mibrain;

/* JADX INFO: loaded from: classes4.dex */
public class MibrainVad {
    private static final int FIRSTPACK = 0;
    private static final int INTERMEIDATE_PACK = 1;
    private boolean mIsEnd;
    private boolean mIsSpeak;
    private int mPackCount;
    private long mVadInstance;

    private native long newVad();

    private native void releaseVad(long j);

    private native int vadCheckBegin(long j, byte[] bArr, int i, int i2);

    private native int vadCheckEnd(long j, byte[] bArr, int i, int i2);

    private native int vadInit(long j, float f, float f2, float f3);

    private native int vadUnInit(long j);

    public int init(int i, int i2, int i3, float f, float f2, float f3) {
        if (i != 16000 && i2 != 1 && i3 != 16) {
            return -1;
        }
        long jNewVad = newVad();
        this.mVadInstance = jNewVad;
        if (jNewVad == 0) {
            return -1;
        }
        if (vadInit(jNewVad, f, f2, f3) == 0) {
            return 0;
        }
        releaseVad(this.mVadInstance);
        this.mVadInstance = 0L;
        return -1;
    }

    static {
        Mibrainsdk.loadMibrainLibs();
    }

    public boolean isSpeak(byte[] bArr, int i) throws MibrainException {
        MibrainVad mibrainVad;
        if (i < 8000 || i >= 16000) {
            throw new MibrainException("buffer must more than 8000bytes and less than 16000bytes");
        }
        if (bArr == null) {
            throw new MibrainException("buffer is null ");
        }
        if (this.mIsEnd) {
            mibrainVad = this;
        } else {
            int i2 = this.mPackCount + 1;
            this.mPackCount = i2;
            if (this.mIsSpeak) {
                mibrainVad = this;
                if (mibrainVad.vadCheckEnd(this.mVadInstance, bArr, i, 1) >= 0) {
                    mibrainVad.mIsSpeak = false;
                    mibrainVad.mIsEnd = true;
                }
            } else {
                mibrainVad = this;
                if (mibrainVad.vadCheckBegin(mibrainVad.mVadInstance, bArr, i, i2 == 1 ? 0 : 1) >= 0) {
                    mibrainVad.mIsSpeak = true;
                }
            }
        }
        return mibrainVad.mIsSpeak;
    }

    public void release() {
        this.mIsEnd = true;
        long j = this.mVadInstance;
        if (j != 0) {
            vadUnInit(j);
        }
        long j2 = this.mVadInstance;
        if (j2 != 0) {
            releaseVad(j2);
            this.mVadInstance = 0L;
        }
    }
}
