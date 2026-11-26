package com.miui.maml;

import android.media.SoundPool;
import android.os.Handler;
import android.os.ParcelFileDescriptor;
import android.support.v4.media.session.PlaybackStateCompat;
import android.util.Log;
import androidx.hardware.DataSpace;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import miui.content.res.ThemeResources;

/* JADX INFO: loaded from: classes3.dex */
public class SoundManager implements SoundPool.OnLoadCompleteListener {
    private static final String ADVANCE = "advance/";
    private static final String LOCKSCREEN_AUDIO = "lockscreen_audio/";
    private static final String LOG_TAG = "MamlSoundManager";
    private static final int MAX_FILE_SIZE = 524288;
    private static final int MAX_STREAMS = 8;
    private Handler mHandler;
    private boolean mInitialized;
    private ResourceManager mResourceManager;
    private SoundPool mSoundPool;
    private HashMap<String, Integer> mSoundPoolMap = new HashMap<>();
    private HashMap<Integer, SoundOptions> mPendingSoundMap = new HashMap<>();
    private ArrayList<Integer> mPlayingSoundMap = new ArrayList<>();
    private Object mInitSignal = new Object();

    public enum Command {
        Play,
        Pause,
        Resume,
        Stop;

        public static Command parse(String str) {
            if ("pause".equals(str)) {
                return Pause;
            }
            if ("resume".equals(str)) {
                return Resume;
            }
            if ("stop".equals(str)) {
                return Stop;
            }
            return Play;
        }
    }

    public static class SoundOptions {
        public boolean mKeepCur;
        public boolean mLoop;
        public float mVolume;

        public SoundOptions(boolean z, boolean z2, float f) {
            this.mKeepCur = z;
            this.mLoop = z2;
            if (f < 0.0f) {
                this.mVolume = 0.0f;
            } else if (f > 1.0f) {
                this.mVolume = 1.0f;
            } else {
                this.mVolume = f;
            }
        }
    }

    public SoundManager(ScreenContext screenContext) {
        this.mResourceManager = screenContext.mResourceManager;
        this.mHandler = screenContext.getHandler();
    }

    private void init() {
        if (this.mInitialized) {
            return;
        }
        if (Thread.currentThread().getId() == this.mHandler.getLooper().getThread().getId()) {
            SoundPool soundPool = new SoundPool(8, 3, 100);
            this.mSoundPool = soundPool;
            soundPool.setOnLoadCompleteListener(this);
            this.mInitialized = true;
            return;
        }
        this.mHandler.post(new Runnable() { // from class: com.miui.maml.SoundManager.1
            @Override // java.lang.Runnable
            public void run() {
                SoundManager.this.mSoundPool = new SoundPool(8, 3, 100);
                SoundManager.this.mSoundPool.setOnLoadCompleteListener(SoundManager.this);
                synchronized (SoundManager.this.mInitSignal) {
                    SoundManager.this.mInitialized = true;
                    SoundManager.this.mInitSignal.notify();
                }
            }
        });
        synchronized (this.mInitSignal) {
            while (!this.mInitialized) {
                try {
                    this.mInitSignal.wait();
                } catch (InterruptedException unused) {
                }
            }
        }
    }

    public synchronized void playSound(int i, Command command) {
        if (!this.mInitialized) {
            init();
        }
        if (this.mSoundPool != null && i > 0) {
            int i2 = AnonymousClass2.$SwitchMap$com$miui$maml$SoundManager$Command[command.ordinal()];
            if (i2 == 2) {
                this.mSoundPool.pause(i);
            } else if (i2 == 3) {
                this.mSoundPool.resume(i);
            } else if (i2 == 4) {
                this.mSoundPool.stop(i);
                synchronized (this.mPlayingSoundMap) {
                    this.mPlayingSoundMap.remove(Integer.valueOf(i));
                }
            }
        }
    }

    /* JADX INFO: renamed from: com.miui.maml.SoundManager$2, reason: invalid class name */
    static /* synthetic */ class AnonymousClass2 {
        static final /* synthetic */ int[] $SwitchMap$com$miui$maml$SoundManager$Command;

        static {
            int[] iArr = new int[Command.values().length];
            $SwitchMap$com$miui$maml$SoundManager$Command = iArr;
            try {
                iArr[Command.Play.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$com$miui$maml$SoundManager$Command[Command.Pause.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                $SwitchMap$com$miui$maml$SoundManager$Command[Command.Resume.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                $SwitchMap$com$miui$maml$SoundManager$Command[Command.Stop.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
        }
    }

    public synchronized int playSound(String str, SoundOptions soundOptions) {
        if (!this.mInitialized) {
            init();
        }
        if (this.mSoundPool == null) {
            return 0;
        }
        Integer numValueOf = this.mSoundPoolMap.get(str);
        if (numValueOf == null) {
            File file = new File(ThemeResources.THEME_MAGIC_PATH + "lockscreen_audio/advance/" + str);
            if (!file.exists()) {
                file = this.mResourceManager.getExtraFile(str);
                if (file == null || !file.exists()) {
                    Log.e(LOG_TAG, "the sound does not exist: " + str);
                    return 0;
                }
            } else if (file.length() > PlaybackStateCompat.ACTION_SET_SHUFFLE_MODE_ENABLED) {
                Log.w(LOG_TAG, String.format("the sound file is larger than %d KB: %s", 512, str));
                return 0;
            }
            try {
                ParcelFileDescriptor parcelFileDescriptorOpen = ParcelFileDescriptor.open(file, DataSpace.RANGE_LIMITED);
                if (parcelFileDescriptorOpen != null) {
                    try {
                        numValueOf = Integer.valueOf(this.mSoundPool.load(parcelFileDescriptorOpen.getFileDescriptor(), 0L, file.length(), 1));
                        this.mSoundPoolMap.put(str, numValueOf);
                    } catch (Throwable th) {
                        try {
                            throw th;
                        } catch (Throwable th2) {
                            if (parcelFileDescriptorOpen == null) {
                                throw th2;
                            }
                            try {
                                parcelFileDescriptorOpen.close();
                                throw th2;
                            } catch (Throwable th3) {
                                th.addSuppressed(th3);
                                throw th2;
                            }
                        }
                    }
                }
                if (parcelFileDescriptorOpen != null) {
                    parcelFileDescriptorOpen.close();
                }
            } catch (IOException e) {
                Log.e(LOG_TAG, "fail to load sound. ", e);
            }
            this.mPendingSoundMap.put(numValueOf, soundOptions);
            return 0;
        }
        return playSoundImp(numValueOf.intValue(), soundOptions);
    }

    public synchronized void release() {
        if (this.mInitialized) {
            stopAllPlaying();
            if (this.mSoundPool != null) {
                this.mSoundPoolMap.clear();
                this.mSoundPool.setOnLoadCompleteListener(null);
                this.mSoundPool.release();
                this.mSoundPool = null;
            }
            this.mInitialized = false;
        }
    }

    private synchronized int playSoundImp(int i, SoundOptions soundOptions) {
        int iPlay;
        if (this.mSoundPool == null) {
            return 0;
        }
        if (!soundOptions.mKeepCur) {
            stopAllPlaying();
        }
        try {
            synchronized (this.mPlayingSoundMap) {
                iPlay = this.mSoundPool.play(i, soundOptions.mVolume, soundOptions.mVolume, 1, soundOptions.mLoop ? -1 : 0, 1.0f);
                this.mPlayingSoundMap.add(Integer.valueOf(iPlay));
            }
            return iPlay;
        } catch (Exception e) {
            Log.e(LOG_TAG, e.toString());
            return 0;
        }
    }

    protected void stopAllPlaying() {
        if (this.mPlayingSoundMap.isEmpty()) {
            return;
        }
        synchronized (this.mPlayingSoundMap) {
            if (this.mSoundPool != null) {
                Iterator<Integer> it = this.mPlayingSoundMap.iterator();
                while (it.hasNext()) {
                    this.mSoundPool.stop(it.next().intValue());
                }
            }
            this.mPlayingSoundMap.clear();
        }
    }

    @Override // android.media.SoundPool.OnLoadCompleteListener
    public void onLoadComplete(SoundPool soundPool, int i, int i2) {
        if (i2 == 0) {
            playSoundImp(i, this.mPendingSoundMap.get(Integer.valueOf(i)));
        }
        this.mPendingSoundMap.remove(Integer.valueOf(i));
    }

    public void pause() {
        stopAllPlaying();
    }
}
