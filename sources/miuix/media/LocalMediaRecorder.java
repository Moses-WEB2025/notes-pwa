package miuix.media;

import android.media.MediaRecorder;
import android.os.ParcelFileDescriptor;
import android.os.SystemClock;
import android.text.TextUtils;
import android.util.Log;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.concurrent.atomic.AtomicBoolean;
import miuix.reflect.Reflects;

/* JADX INFO: loaded from: classes5.dex */
class LocalMediaRecorder implements Recorder {
    private static final String TAG = "Media:LocalMediaRecorder";
    private static final Method setParameter;
    private String mDestFilePath;
    private String mExtraParameter;
    private boolean mIsPaused;
    private MediaRecorder.OnErrorListener mOnErrorListener;
    private MediaRecorder.OnInfoListener mOnInfoListener;
    private RecorderAppendThread mRecorderAppendThread;
    private ParcelFileDescriptor mRecorderAppendWriteFd;
    private Recorder.OnErrorListener mRecorderOnErrorListener;
    private int mRecordingDuration;
    private Object mSyncer = new Object();
    private int mAudioSource = -1;
    private int mSamplingRate = -1;
    private int mOutputFormat = -1;
    private int mAudioEncoder = -1;
    private int mBitRate = -1;
    private long mMaxSize = -1;
    private int mMaxDuration = -1;
    private long mSizeRecored = 0;
    private int mNumChannels = -1;
    private long mStartTime = 0;
    private MediaRecorder mMediaRecorder = new MediaRecorder();

    protected int getHeaderLen() {
        return 0;
    }

    @Override // miuix.media.Recorder
    public void setQuality(int i) {
    }

    static {
        Method declaredMethod;
        try {
            declaredMethod = Reflects.getDeclaredMethod((Class<?>) MediaRecorder.class, "setParameter", (Class<?>[]) new Class[]{String.class});
        } catch (Exception e) {
            Log.w(TAG, "Could not get method:setParameter", e);
            declaredMethod = null;
        }
        setParameter = declaredMethod;
    }

    protected void resetParameters() {
        this.mDestFilePath = null;
        this.mExtraParameter = null;
        this.mAudioSource = -1;
        this.mSamplingRate = -1;
        this.mAudioEncoder = -1;
        this.mOutputFormat = -1;
        this.mBitRate = -1;
        this.mMaxSize = -1L;
        this.mNumChannels = -1;
        this.mSizeRecored = 0L;
        this.mOnErrorListener = null;
        this.mOnInfoListener = null;
        this.mRecorderOnErrorListener = null;
    }

    @Override // miuix.media.Recorder
    public void setAudioSource(int i) {
        synchronized (this.mSyncer) {
            this.mAudioSource = i;
            this.mMediaRecorder.setAudioSource(i);
        }
    }

    @Override // miuix.media.Recorder
    public void setAudioSamplingRate(int i) {
        synchronized (this.mSyncer) {
            this.mSamplingRate = i;
            this.mMediaRecorder.setAudioSamplingRate(i);
        }
    }

    @Override // miuix.media.Recorder
    public void setOutputFormat(int i) {
        synchronized (this.mSyncer) {
            this.mOutputFormat = i;
            this.mMediaRecorder.setOutputFormat(i);
        }
    }

    @Override // miuix.media.Recorder
    public void setAudioEncoder(int i) {
        synchronized (this.mSyncer) {
            this.mAudioEncoder = i;
            this.mMediaRecorder.setAudioEncoder(i);
        }
    }

    @Override // miuix.media.Recorder
    public void setOutputFile(String str) {
        synchronized (this.mSyncer) {
            this.mDestFilePath = str;
            this.mMediaRecorder.setOutputFile(str);
        }
    }

    @Override // miuix.media.Recorder
    public void prepare() throws IOException {
        synchronized (this.mSyncer) {
            this.mMediaRecorder.prepare();
        }
    }

    @Override // miuix.media.Recorder
    public void reset() {
        synchronized (this.mSyncer) {
            resetParameters();
            this.mMediaRecorder.reset();
        }
    }

    @Override // miuix.media.Recorder
    public void release() {
        synchronized (this.mSyncer) {
            resetParameters();
            MediaRecorder mediaRecorder = this.mMediaRecorder;
            if (mediaRecorder != null) {
                mediaRecorder.release();
                this.mMediaRecorder = null;
            }
        }
    }

    @Override // miuix.media.Recorder
    public void start() {
        synchronized (this.mSyncer) {
            this.mMediaRecorder.start();
            this.mStartTime = SystemClock.elapsedRealtime();
            this.mRecordingDuration = 0;
        }
    }

    @Override // miuix.media.Recorder
    public void stop() {
        synchronized (this.mSyncer) {
            resetParameters();
            MediaRecorder mediaRecorder = this.mMediaRecorder;
            if (mediaRecorder != null) {
                mediaRecorder.stop();
                ParcelFileDescriptor parcelFileDescriptor = this.mRecorderAppendWriteFd;
                if (parcelFileDescriptor != null) {
                    try {
                        parcelFileDescriptor.close();
                        waitingForAppendThread();
                    } catch (IOException e) {
                        throw new IllegalStateException(e);
                    }
                }
            }
            this.mIsPaused = false;
        }
    }

    @Override // miuix.media.Recorder
    public int getMaxAmplitude() {
        int maxAmplitude;
        synchronized (this.mSyncer) {
            maxAmplitude = this.mIsPaused ? 0 : this.mMediaRecorder.getMaxAmplitude();
        }
        return maxAmplitude;
    }

    @Override // miuix.media.Recorder
    public boolean canPause() {
        synchronized (this.mSyncer) {
        }
        return false;
    }

    @Override // miuix.media.Recorder
    public void pause() {
        synchronized (this.mSyncer) {
            this.mIsPaused = true;
            this.mMediaRecorder.stop();
            this.mMediaRecorder.release();
            this.mMediaRecorder = null;
            this.mRecordingDuration = (int) (((long) this.mRecordingDuration) + (SystemClock.elapsedRealtime() - this.mStartTime));
            ParcelFileDescriptor parcelFileDescriptor = this.mRecorderAppendWriteFd;
            if (parcelFileDescriptor != null) {
                try {
                    parcelFileDescriptor.close();
                    waitingForAppendThread();
                } catch (IOException e) {
                    throw new IllegalStateException(e);
                }
            }
            this.mSizeRecored = new File(this.mDestFilePath).length();
        }
    }

    @Override // miuix.media.Recorder
    public void resume() throws IllegalStateException, IOException {
        synchronized (this.mSyncer) {
            if (this.mIsPaused) {
                this.mMediaRecorder = new MediaRecorder();
                if (!TextUtils.isEmpty(this.mExtraParameter)) {
                    setExtraParameters(this.mExtraParameter);
                }
                int i = this.mAudioSource;
                if (i != -1) {
                    this.mMediaRecorder.setAudioSource(i);
                }
                int i2 = this.mSamplingRate;
                if (i2 != -1) {
                    this.mMediaRecorder.setAudioSamplingRate(i2);
                }
                int i3 = this.mOutputFormat;
                if (i3 != -1) {
                    this.mMediaRecorder.setOutputFormat(i3);
                }
                int i4 = this.mAudioEncoder;
                if (i4 != -1) {
                    this.mMediaRecorder.setAudioEncoder(i4);
                }
                int i5 = this.mBitRate;
                if (i5 != -1) {
                    this.mMediaRecorder.setAudioEncodingBitRate(i5);
                }
                int i6 = this.mNumChannels;
                if (i6 != -1) {
                    this.mMediaRecorder.setAudioChannels(i6);
                }
                long j = this.mMaxSize;
                if (j != -1) {
                    this.mMediaRecorder.setMaxFileSize(j - this.mSizeRecored);
                }
                int i7 = this.mMaxDuration;
                if (i7 != -1) {
                    this.mMediaRecorder.setMaxDuration(i7);
                }
                MediaRecorder.OnErrorListener onErrorListener = this.mOnErrorListener;
                if (onErrorListener != null) {
                    this.mMediaRecorder.setOnErrorListener(onErrorListener);
                }
                MediaRecorder.OnInfoListener onInfoListener = this.mOnInfoListener;
                if (onInfoListener != null) {
                    this.mMediaRecorder.setOnInfoListener(onInfoListener);
                }
                ParcelFileDescriptor[] parcelFileDescriptorArrCreatePipe = ParcelFileDescriptor.createPipe();
                this.mRecorderAppendWriteFd = parcelFileDescriptorArrCreatePipe[1];
                this.mRecorderAppendThread = new RecorderAppendThread(parcelFileDescriptorArrCreatePipe[0]);
                this.mMediaRecorder.setOutputFile(this.mRecorderAppendWriteFd.getFileDescriptor());
                this.mRecorderAppendThread.start();
                this.mMediaRecorder.prepare();
                this.mMediaRecorder.start();
                this.mIsPaused = false;
                this.mStartTime = SystemClock.elapsedRealtime();
            }
        }
    }

    @Override // miuix.media.Recorder
    public boolean isPaused() {
        boolean z;
        synchronized (this.mSyncer) {
            z = this.mIsPaused;
        }
        return z;
    }

    @Override // miuix.media.Recorder
    public void setAudioEncodingBitRate(int i) {
        synchronized (this.mSyncer) {
            this.mBitRate = i;
            this.mMediaRecorder.setAudioEncodingBitRate(i);
        }
    }

    @Override // miuix.media.Recorder
    public void setMaxFileSize(long j) {
        synchronized (this.mSyncer) {
            this.mMaxSize = j;
            this.mMediaRecorder.setMaxFileSize(j);
        }
    }

    @Override // miuix.media.Recorder
    public void setMaxDuration(int i) {
        synchronized (this.mSyncer) {
            this.mMaxDuration = i;
            this.mMediaRecorder.setMaxDuration(i);
        }
    }

    @Override // miuix.media.Recorder
    public void setExtraParameters(String str) {
        synchronized (this.mSyncer) {
            this.mExtraParameter = str;
            setExtraParametersLocal(str);
        }
    }

    @Override // miuix.media.Recorder
    public void setOnErrorListener(Recorder.OnErrorListener onErrorListener) {
        this.mRecorderOnErrorListener = onErrorListener;
        setOnErrorListener(new MediaRecorder.OnErrorListener() { // from class: miuix.media.LocalMediaRecorder.1
            @Override // android.media.MediaRecorder.OnErrorListener
            public void onError(MediaRecorder mediaRecorder, int i, int i2) {
                LocalMediaRecorder.this.mRecorderOnErrorListener.onError(LocalMediaRecorder.this, RecorderUtils.convertErrorCode(i, true));
            }
        });
        setOnInfoListener(new MediaRecorder.OnInfoListener() { // from class: miuix.media.LocalMediaRecorder.2
            @Override // android.media.MediaRecorder.OnInfoListener
            public void onInfo(MediaRecorder mediaRecorder, int i, int i2) {
                LocalMediaRecorder.this.mRecorderOnErrorListener.onError(LocalMediaRecorder.this, RecorderUtils.convertErrorCode(i, true));
            }
        });
    }

    @Override // miuix.media.Recorder
    public long getRecordingTimeInMillis() {
        if (isPaused()) {
            return this.mRecordingDuration;
        }
        return (SystemClock.elapsedRealtime() - this.mStartTime) + ((long) this.mRecordingDuration);
    }

    @Override // miuix.media.Recorder
    public void setAudioChannel(int i) {
        synchronized (this.mSyncer) {
            try {
                if (i == 16) {
                    this.mNumChannels = 1;
                } else {
                    this.mNumChannels = 2;
                }
                this.mMediaRecorder.setAudioChannels(this.mNumChannels);
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public void setOnErrorListener(MediaRecorder.OnErrorListener onErrorListener) {
        synchronized (this.mSyncer) {
            this.mOnErrorListener = onErrorListener;
            this.mMediaRecorder.setOnErrorListener(onErrorListener);
        }
    }

    public void setOnInfoListener(MediaRecorder.OnInfoListener onInfoListener) {
        synchronized (this.mSyncer) {
            this.mOnInfoListener = onInfoListener;
            this.mMediaRecorder.setOnInfoListener(onInfoListener);
        }
    }

    private void setExtraParametersLocal(String str) {
        Method method = setParameter;
        if (method == null || TextUtils.isEmpty(str)) {
            return;
        }
        try {
            Reflects.invoke(this.mMediaRecorder, method, str);
        } catch (Exception e) {
            Log.w(TAG, "failed to invoke setparameter", e);
        }
    }

    private class RecorderAppendThread extends Thread {
        private AtomicBoolean mRunningLatch;
        private ParcelFileDescriptor mSrcFd;

        RecorderAppendThread(ParcelFileDescriptor parcelFileDescriptor) {
            this.mSrcFd = parcelFileDescriptor;
        }

        /* JADX WARN: Code duplicated, block: B:66:0x00be A[Catch: IOException -> 0x00ba, TryCatch #0 {IOException -> 0x00ba, blocks: (B:62:0x00b6, B:66:0x00be, B:67:0x00c1), top: B:78:0x00b6 }] */
        /* JADX WARN: Code duplicated, block: B:78:0x00b6 A[EXC_TOP_SPLITTER, SYNTHETIC] */
        /* JADX WARN: Code duplicated, block: B:86:0x00a4 A[EXC_TOP_SPLITTER, SYNTHETIC] */
        /* JADX WARN: Code duplicated, block: B:90:0x00d1 A[EXC_TOP_SPLITTER, SYNTHETIC] */
        @Override // java.lang.Thread, java.lang.Runnable
        public void run() throws Throwable {
            FileOutputStream fileOutputStream;
            if (!LocalMediaRecorder.this.canPause()) {
                return;
            }
            this.mRunningLatch = new AtomicBoolean(true);
            byte[] bArr = new byte[1024];
            FileInputStream fileInputStream = null;
            try {
                FileInputStream fileInputStream2 = new FileInputStream(this.mSrcFd.getFileDescriptor());
                try {
                    fileOutputStream = new FileOutputStream(LocalMediaRecorder.this.mDestFilePath, true);
                    try {
                        int headerLen = LocalMediaRecorder.this.getHeaderLen();
                        int i = 0;
                        while (headerLen - i > 0) {
                            i = fileInputStream2.read(bArr, 0, headerLen);
                            headerLen -= i;
                        }
                        while (true) {
                            int i2 = fileInputStream2.read(bArr);
                            if (i2 > 0) {
                                fileOutputStream.write(bArr, 0, i2);
                            } else {
                                try {
                                    break;
                                } catch (IOException e) {
                                    Log.e(LocalMediaRecorder.TAG, "IOException", e);
                                }
                            }
                        }
                        fileInputStream2.close();
                        fileOutputStream.close();
                        this.mSrcFd.close();
                        synchronized (this.mRunningLatch) {
                            this.mRunningLatch.set(false);
                            this.mRunningLatch.notify();
                        }
                    } catch (IOException e2) {
                        e = e2;
                        fileInputStream = fileInputStream2;
                        try {
                            Log.e(LocalMediaRecorder.TAG, "IOException", e);
                            if (fileInputStream != null) {
                                try {
                                    fileInputStream.close();
                                } catch (IOException e3) {
                                    Log.e(LocalMediaRecorder.TAG, "IOException", e3);
                                    synchronized (this.mRunningLatch) {
                                        this.mRunningLatch.set(false);
                                        this.mRunningLatch.notify();
                                    }
                                }
                            }
                            if (fileOutputStream != null) {
                                fileOutputStream.close();
                            }
                            this.mSrcFd.close();
                            synchronized (this.mRunningLatch) {
                                this.mRunningLatch.set(false);
                                this.mRunningLatch.notify();
                            }
                        } catch (Throwable th) {
                            th = th;
                            if (fileInputStream != null) {
                                try {
                                    fileInputStream.close();
                                } catch (IOException e4) {
                                    Log.e(LocalMediaRecorder.TAG, "IOException", e4);
                                    synchronized (this.mRunningLatch) {
                                        this.mRunningLatch.set(false);
                                        this.mRunningLatch.notify();
                                    }
                                    throw th;
                                }
                            }
                            if (fileOutputStream != null) {
                                fileOutputStream.close();
                            }
                            this.mSrcFd.close();
                            synchronized (this.mRunningLatch) {
                                this.mRunningLatch.set(false);
                                this.mRunningLatch.notify();
                                throw th;
                            }
                        }
                    } catch (Throwable th2) {
                        th = th2;
                        fileInputStream = fileInputStream2;
                        if (fileInputStream != null) {
                            fileInputStream.close();
                        }
                        if (fileOutputStream != null) {
                            fileOutputStream.close();
                        }
                        this.mSrcFd.close();
                        synchronized (this.mRunningLatch) {
                            this.mRunningLatch.set(false);
                            this.mRunningLatch.notify();
                            throw th;
                        }
                    }
                } catch (IOException e5) {
                    e = e5;
                    fileOutputStream = null;
                } catch (Throwable th3) {
                    th = th3;
                    fileOutputStream = null;
                }
            } catch (IOException e6) {
                e = e6;
                fileOutputStream = null;
            } catch (Throwable th4) {
                th = th4;
                fileOutputStream = null;
            }
        }
    }

    private void waitingForAppendThread() {
        RecorderAppendThread recorderAppendThread = this.mRecorderAppendThread;
        if (recorderAppendThread != null) {
            synchronized (recorderAppendThread.mRunningLatch) {
                while (this.mRecorderAppendThread.mRunningLatch.get()) {
                    try {
                        this.mRecorderAppendThread.mRunningLatch.wait();
                    } catch (InterruptedException e) {
                        Log.e(TAG, "InterruptedException", e);
                    }
                }
            }
            this.mRecorderAppendThread = null;
        }
    }
}
