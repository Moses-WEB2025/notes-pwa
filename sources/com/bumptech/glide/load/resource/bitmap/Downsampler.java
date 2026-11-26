package com.bumptech.glide.load.resource.bitmap;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.ColorSpace;
import android.os.ParcelFileDescriptor;
import android.util.DisplayMetrics;
import android.util.Log;
import com.bumptech.glide.load.DecodeFormat;
import com.bumptech.glide.load.ImageHeaderParser;
import com.bumptech.glide.load.Option;
import com.bumptech.glide.load.Options;
import com.bumptech.glide.load.PreferredColorSpace;
import com.bumptech.glide.load.data.ParcelFileDescriptorRewinder;
import com.bumptech.glide.load.engine.Resource;
import com.bumptech.glide.load.engine.bitmap_recycle.ArrayPool;
import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool;
import com.bumptech.glide.util.LogTime;
import com.bumptech.glide.util.Preconditions;
import com.bumptech.glide.util.Util;
import com.miui.maml.folme.AnimatedProperty;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.util.Arrays;
import java.util.Collections;
import java.util.EnumSet;
import java.util.HashSet;
import java.util.List;
import java.util.Queue;
import java.util.Set;

/* JADX INFO: loaded from: classes.dex */
public final class Downsampler {
    static final String TAG = "Downsampler";
    private final BitmapPool bitmapPool;
    private final ArrayPool byteArrayPool;
    private final DisplayMetrics displayMetrics;
    private final HardwareConfigState hardwareConfigState = HardwareConfigState.getInstance();
    private final List<ImageHeaderParser> parsers;
    public static final Option<DecodeFormat> DECODE_FORMAT = Option.memory("com.bumptech.glide.load.resource.bitmap.Downsampler.DecodeFormat", DecodeFormat.DEFAULT);
    public static final Option<PreferredColorSpace> PREFERRED_COLOR_SPACE = Option.memory("com.bumptech.glide.load.resource.bitmap.Downsampler.PreferredColorSpace");

    @Deprecated
    public static final Option<DownsampleStrategy> DOWNSAMPLE_STRATEGY = DownsampleStrategy.OPTION;
    public static final Option<Boolean> FIX_BITMAP_SIZE_TO_REQUESTED_DIMENSIONS = Option.memory("com.bumptech.glide.load.resource.bitmap.Downsampler.FixBitmapSize", false);
    public static final Option<Boolean> ALLOW_HARDWARE_CONFIG = Option.memory("com.bumptech.glide.load.resource.bitmap.Downsampler.AllowHardwareDecode", false);
    private static final String WBMP_MIME_TYPE = "image/vnd.wap.wbmp";
    private static final String ICO_MIME_TYPE = "image/x-ico";
    private static final Set<String> NO_DOWNSAMPLE_PRE_N_MIME_TYPES = Collections.unmodifiableSet(new HashSet(Arrays.asList(WBMP_MIME_TYPE, ICO_MIME_TYPE)));
    private static final DecodeCallbacks EMPTY_CALLBACKS = new DecodeCallbacks() { // from class: com.bumptech.glide.load.resource.bitmap.Downsampler.1
        @Override // com.bumptech.glide.load.resource.bitmap.Downsampler.DecodeCallbacks
        public void onDecodeComplete(BitmapPool bitmapPool, Bitmap bitmap) {
        }

        @Override // com.bumptech.glide.load.resource.bitmap.Downsampler.DecodeCallbacks
        public void onObtainBounds() {
        }
    };
    private static final Set<ImageHeaderParser.ImageType> TYPES_THAT_USE_POOL_PRE_KITKAT = Collections.unmodifiableSet(EnumSet.of(ImageHeaderParser.ImageType.JPEG, ImageHeaderParser.ImageType.PNG_A, ImageHeaderParser.ImageType.PNG));
    private static final Queue<BitmapFactory.Options> OPTIONS_QUEUE = Util.createQueue(0);

    public interface DecodeCallbacks {
        void onDecodeComplete(BitmapPool bitmapPool, Bitmap bitmap) throws IOException;

        void onObtainBounds();
    }

    private static boolean isRotationRequired(int i) {
        return i == 90 || i == 270;
    }

    private static int round(double d) {
        return (int) (d + 0.5d);
    }

    private boolean shouldUsePool(ImageHeaderParser.ImageType imageType) {
        return true;
    }

    public boolean handles(InputStream inputStream) {
        return true;
    }

    public boolean handles(ByteBuffer byteBuffer) {
        return true;
    }

    public Downsampler(List<ImageHeaderParser> list, DisplayMetrics displayMetrics, BitmapPool bitmapPool, ArrayPool arrayPool) {
        this.parsers = list;
        this.displayMetrics = (DisplayMetrics) Preconditions.checkNotNull(displayMetrics);
        this.bitmapPool = (BitmapPool) Preconditions.checkNotNull(bitmapPool);
        this.byteArrayPool = (ArrayPool) Preconditions.checkNotNull(arrayPool);
    }

    public boolean handles(ParcelFileDescriptor parcelFileDescriptor) {
        return ParcelFileDescriptorRewinder.isSupported();
    }

    public Resource<Bitmap> decode(InputStream inputStream, int i, int i2, Options options) throws IOException {
        return decode(inputStream, i, i2, options, EMPTY_CALLBACKS);
    }

    public Resource<Bitmap> decode(ByteBuffer byteBuffer, int i, int i2, Options options) throws IOException {
        return decode(new ImageReader.ByteBufferReader(byteBuffer, this.parsers, this.byteArrayPool), i, i2, options, EMPTY_CALLBACKS);
    }

    public Resource<Bitmap> decode(InputStream inputStream, int i, int i2, Options options, DecodeCallbacks decodeCallbacks) throws IOException {
        return decode(new ImageReader.InputStreamImageReader(inputStream, this.parsers, this.byteArrayPool), i, i2, options, decodeCallbacks);
    }

    void decode(byte[] bArr, int i, int i2, Options options) throws IOException {
        decode(new ImageReader.ByteArrayReader(bArr, this.parsers, this.byteArrayPool), i, i2, options, EMPTY_CALLBACKS);
    }

    void decode(File file, int i, int i2, Options options) throws IOException {
        decode(new ImageReader.FileReader(file, this.parsers, this.byteArrayPool), i, i2, options, EMPTY_CALLBACKS);
    }

    public Resource<Bitmap> decode(ParcelFileDescriptor parcelFileDescriptor, int i, int i2, Options options) throws IOException {
        return decode(new ImageReader.ParcelFileDescriptorImageReader(parcelFileDescriptor, this.parsers, this.byteArrayPool), i, i2, options, EMPTY_CALLBACKS);
    }

    private Resource<Bitmap> decode(ImageReader imageReader, int i, int i2, Options options, DecodeCallbacks decodeCallbacks) throws IOException {
        byte[] bArr = (byte[]) this.byteArrayPool.get(65536, byte[].class);
        BitmapFactory.Options defaultOptions = getDefaultOptions();
        defaultOptions.inTempStorage = bArr;
        DecodeFormat decodeFormat = (DecodeFormat) options.get(DECODE_FORMAT);
        PreferredColorSpace preferredColorSpace = (PreferredColorSpace) options.get(PREFERRED_COLOR_SPACE);
        DownsampleStrategy downsampleStrategy = (DownsampleStrategy) options.get(DownsampleStrategy.OPTION);
        boolean zBooleanValue = ((Boolean) options.get(FIX_BITMAP_SIZE_TO_REQUESTED_DIMENSIONS)).booleanValue();
        Option<Boolean> option = ALLOW_HARDWARE_CONFIG;
        try {
            return BitmapResource.obtain(decodeFromWrappedStreams(imageReader, defaultOptions, downsampleStrategy, decodeFormat, preferredColorSpace, options.get(option) != null && ((Boolean) options.get(option)).booleanValue(), i, i2, zBooleanValue, decodeCallbacks), this.bitmapPool);
        } finally {
            releaseOptions(defaultOptions);
            this.byteArrayPool.put(bArr);
        }
    }

    private Bitmap decodeFromWrappedStreams(ImageReader imageReader, BitmapFactory.Options options, DownsampleStrategy downsampleStrategy, DecodeFormat decodeFormat, PreferredColorSpace preferredColorSpace, boolean z, int i, int i2, boolean z2, DecodeCallbacks decodeCallbacks) throws IOException {
        String str;
        int iRound;
        long logTime = LogTime.getLogTime();
        int[] dimensions = getDimensions(imageReader, options, decodeCallbacks, this.bitmapPool);
        int i3 = dimensions[0];
        int i4 = dimensions[1];
        String str2 = options.outMimeType;
        boolean z3 = (i3 == -1 || i4 == -1) ? false : z;
        int imageOrientation = imageReader.getImageOrientation();
        int exifOrientationDegrees = TransformationUtils.getExifOrientationDegrees(imageOrientation);
        boolean zIsExifOrientationRequired = TransformationUtils.isExifOrientationRequired(imageOrientation);
        int i5 = i;
        if (i5 == Integer.MIN_VALUE) {
            i5 = isRotationRequired(exifOrientationDegrees) ? i4 : i3;
        }
        if (i2 == -2147483648) {
            i2 = isRotationRequired(exifOrientationDegrees) ? i3 : i4;
        }
        ImageHeaderParser.ImageType imageType = imageReader.getImageType();
        calculateScaling(imageType, imageReader, decodeCallbacks, this.bitmapPool, downsampleStrategy, exifOrientationDegrees, i3, i4, i5, i2, options);
        int i6 = i5;
        int i7 = i2;
        calculateConfig(imageReader, decodeFormat, z3, zIsExifOrientationRequired, options, i6, i7);
        int i8 = options.inSampleSize;
        if (shouldUsePool(imageType)) {
            if (i3 < 0 || i4 < 0 || !z2) {
                float f = isScaling(options) ? options.inTargetDensity / options.inDensity : 1.0f;
                int i9 = options.inSampleSize;
                float f2 = i3;
                float f3 = i9;
                int iCeil = (int) Math.ceil(f2 / f3);
                int iCeil2 = (int) Math.ceil(i4 / f3);
                int iRound2 = Math.round(iCeil * f);
                iRound = Math.round(iCeil2 * f);
                str = TAG;
                if (Log.isLoggable(str, 2)) {
                    Log.v(str, "Calculated target [" + iRound2 + AnimatedProperty.PROPERTY_NAME_X + iRound + "] for source [" + i3 + AnimatedProperty.PROPERTY_NAME_X + i4 + "], sampleSize: " + i9 + ", targetDensity: " + options.inTargetDensity + ", density: " + options.inDensity + ", density multiplier: " + f);
                }
                i6 = iRound2;
            } else {
                str = TAG;
                iRound = i7;
            }
            if (i6 > 0 && iRound > 0) {
                setInBitmap(options, this.bitmapPool, i6, iRound);
            }
        } else {
            str = TAG;
        }
        if (preferredColorSpace != null) {
            options.inPreferredColorSpace = ColorSpace.get((preferredColorSpace == PreferredColorSpace.DISPLAY_P3 && options.outColorSpace != null && options.outColorSpace.isWideGamut()) ? ColorSpace.Named.DISPLAY_P3 : ColorSpace.Named.SRGB);
        }
        Bitmap bitmapDecodeStream = decodeStream(imageReader, options, decodeCallbacks, this.bitmapPool);
        decodeCallbacks.onDecodeComplete(this.bitmapPool, bitmapDecodeStream);
        if (Log.isLoggable(str, 2)) {
            logDecode(i3, i4, str2, options, bitmapDecodeStream, i, i2, logTime);
        }
        if (bitmapDecodeStream == null) {
            return null;
        }
        bitmapDecodeStream.setDensity(this.displayMetrics.densityDpi);
        Bitmap bitmapRotateImageExif = TransformationUtils.rotateImageExif(this.bitmapPool, bitmapDecodeStream, imageOrientation);
        if (!bitmapDecodeStream.equals(bitmapRotateImageExif)) {
            this.bitmapPool.put(bitmapDecodeStream);
        }
        return bitmapRotateImageExif;
    }

    private static void calculateScaling(ImageHeaderParser.ImageType imageType, ImageReader imageReader, DecodeCallbacks decodeCallbacks, BitmapPool bitmapPool, DownsampleStrategy downsampleStrategy, int i, int i2, int i3, int i4, int i5, BitmapFactory.Options options) throws IOException {
        int i6;
        int i7;
        int iMin;
        int i8;
        int iFloor;
        int iFloor2;
        if (i2 <= 0 || i3 <= 0) {
            if (Log.isLoggable(TAG, 3)) {
                Log.d(TAG, "Unable to determine dimensions for: " + imageType + " with target [" + i4 + AnimatedProperty.PROPERTY_NAME_X + i5 + "]");
                return;
            }
            return;
        }
        if (isRotationRequired(i)) {
            i7 = i2;
            i6 = i3;
        } else {
            i6 = i2;
            i7 = i3;
        }
        float scaleFactor = downsampleStrategy.getScaleFactor(i6, i7, i4, i5);
        if (scaleFactor <= 0.0f) {
            throw new IllegalArgumentException("Cannot scale with factor: " + scaleFactor + " from: " + downsampleStrategy + ", source: [" + i2 + AnimatedProperty.PROPERTY_NAME_X + i3 + "], target: [" + i4 + AnimatedProperty.PROPERTY_NAME_X + i5 + "]");
        }
        DownsampleStrategy.SampleSizeRounding sampleSizeRounding = downsampleStrategy.getSampleSizeRounding(i6, i7, i4, i5);
        if (sampleSizeRounding == null) {
            throw new IllegalArgumentException("Cannot round with null rounding");
        }
        float f = i6;
        float f2 = i7;
        int i9 = i6;
        int i10 = i7;
        int iRound = i9 / round(scaleFactor * f);
        int iRound2 = i10 / round(scaleFactor * f2);
        if (sampleSizeRounding == DownsampleStrategy.SampleSizeRounding.MEMORY) {
            iMin = Math.max(iRound, iRound2);
        } else {
            iMin = Math.min(iRound, iRound2);
        }
        int iMax = Math.max(1, Integer.highestOneBit(iMin));
        if (sampleSizeRounding == DownsampleStrategy.SampleSizeRounding.MEMORY && iMax < 1.0f / scaleFactor) {
            iMax <<= 1;
        }
        options.inSampleSize = iMax;
        if (imageType == ImageHeaderParser.ImageType.JPEG) {
            float fMin = Math.min(iMax, 8);
            i8 = 0;
            iFloor = (int) Math.ceil(f / fMin);
            iFloor2 = (int) Math.ceil(f2 / fMin);
            int i11 = iMax / 8;
            if (i11 > 0) {
                iFloor /= i11;
                iFloor2 /= i11;
            }
        } else {
            i8 = 0;
            if (imageType == ImageHeaderParser.ImageType.PNG || imageType == ImageHeaderParser.ImageType.PNG_A) {
                float f3 = iMax;
                iFloor = (int) Math.floor(f / f3);
                iFloor2 = (int) Math.floor(f2 / f3);
            } else if (imageType.isWebp()) {
                float f4 = iMax;
                iFloor = Math.round(f / f4);
                iFloor2 = Math.round(f2 / f4);
            } else if (i9 % iMax != 0 || i10 % iMax != 0) {
                int[] dimensions = getDimensions(imageReader, options, decodeCallbacks, bitmapPool);
                iFloor = dimensions[0];
                iFloor2 = dimensions[1];
            } else {
                iFloor = i9 / iMax;
                iFloor2 = i10 / iMax;
            }
        }
        double scaleFactor2 = downsampleStrategy.getScaleFactor(iFloor, iFloor2, i4, i5);
        options.inTargetDensity = adjustTargetDensityForError(scaleFactor2);
        options.inDensity = getDensityMultiplier(scaleFactor2);
        if (isScaling(options)) {
            options.inScaled = true;
        } else {
            int i12 = i8;
            options.inTargetDensity = i12;
            options.inDensity = i12;
        }
        if (Log.isLoggable(TAG, 2)) {
            Log.v(TAG, "Calculate scaling, source: [" + i2 + AnimatedProperty.PROPERTY_NAME_X + i3 + "], degreesToRotate: " + i + ", target: [" + i4 + AnimatedProperty.PROPERTY_NAME_X + i5 + "], power of two scaled: [" + iFloor + AnimatedProperty.PROPERTY_NAME_X + iFloor2 + "], exact scale factor: " + scaleFactor + ", power of 2 sample size: " + iMax + ", adjusted scale factor: " + scaleFactor2 + ", target density: " + options.inTargetDensity + ", density: " + options.inDensity);
        }
    }

    private static int adjustTargetDensityForError(double d) {
        int densityMultiplier = getDensityMultiplier(d);
        int iRound = round(((double) densityMultiplier) * d);
        return round((d / ((double) (iRound / densityMultiplier))) * ((double) iRound));
    }

    private static int getDensityMultiplier(double d) {
        if (d > 1.0d) {
            d = 1.0d / d;
        }
        return (int) Math.round(d * 2.147483647E9d);
    }

    private void calculateConfig(ImageReader imageReader, DecodeFormat decodeFormat, boolean z, boolean z2, BitmapFactory.Options options, int i, int i2) {
        boolean zHasAlpha;
        if (this.hardwareConfigState.setHardwareConfigIfAllowed(i, i2, options, z, z2)) {
            return;
        }
        if (decodeFormat == DecodeFormat.PREFER_ARGB_8888) {
            options.inPreferredConfig = Bitmap.Config.ARGB_8888;
            return;
        }
        try {
            zHasAlpha = imageReader.getImageType().hasAlpha();
        } catch (IOException e) {
            if (Log.isLoggable(TAG, 3)) {
                Log.d(TAG, "Cannot determine whether the image has alpha or not from header, format " + decodeFormat, e);
            }
            zHasAlpha = false;
        }
        options.inPreferredConfig = zHasAlpha ? Bitmap.Config.ARGB_8888 : Bitmap.Config.RGB_565;
        if (options.inPreferredConfig == Bitmap.Config.RGB_565) {
            options.inDither = true;
        }
    }

    private static int[] getDimensions(ImageReader imageReader, BitmapFactory.Options options, DecodeCallbacks decodeCallbacks, BitmapPool bitmapPool) throws IOException {
        options.inJustDecodeBounds = true;
        decodeStream(imageReader, options, decodeCallbacks, bitmapPool);
        options.inJustDecodeBounds = false;
        return new int[]{options.outWidth, options.outHeight};
    }

    /* JADX WARN: Code restructure failed: missing block: B:26:?, code lost:
    
        throw r1;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private static android.graphics.Bitmap decodeStream(com.bumptech.glide.load.resource.bitmap.ImageReader r5, android.graphics.BitmapFactory.Options r6, com.bumptech.glide.load.resource.bitmap.Downsampler.DecodeCallbacks r7, com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool r8) throws java.io.IOException {
        /*
            java.lang.String r0 = "Downsampler"
            boolean r1 = r6.inJustDecodeBounds
            if (r1 != 0) goto Lc
            r7.onObtainBounds()
            r5.stopGrowingBuffers()
        Lc:
            int r1 = r6.outWidth
            int r2 = r6.outHeight
            java.lang.String r3 = r6.outMimeType
            java.util.concurrent.locks.Lock r4 = com.bumptech.glide.load.resource.bitmap.TransformationUtils.getBitmapDrawableLock()
            r4.lock()
            android.graphics.Bitmap r5 = r5.decodeBitmap(r6)     // Catch: java.lang.IllegalArgumentException -> L25 java.lang.Throwable -> L49
        L1d:
            java.util.concurrent.locks.Lock r6 = com.bumptech.glide.load.resource.bitmap.TransformationUtils.getBitmapDrawableLock()
            r6.unlock()
            return r5
        L25:
            r4 = move-exception
            java.io.IOException r1 = newIoExceptionForInBitmapAssertion(r4, r1, r2, r3, r6)     // Catch: java.lang.Throwable -> L49
            r2 = 3
            boolean r2 = android.util.Log.isLoggable(r0, r2)     // Catch: java.lang.Throwable -> L49
            if (r2 == 0) goto L36
            java.lang.String r2 = "Failed to decode with inBitmap, trying again without Bitmap re-use"
            android.util.Log.d(r0, r2, r1)     // Catch: java.lang.Throwable -> L49
        L36:
            android.graphics.Bitmap r0 = r6.inBitmap     // Catch: java.lang.Throwable -> L49
            if (r0 == 0) goto L48
            android.graphics.Bitmap r0 = r6.inBitmap     // Catch: java.io.IOException -> L47 java.lang.Throwable -> L49
            r8.put(r0)     // Catch: java.io.IOException -> L47 java.lang.Throwable -> L49
            r0 = 0
            r6.inBitmap = r0     // Catch: java.io.IOException -> L47 java.lang.Throwable -> L49
            android.graphics.Bitmap r5 = decodeStream(r5, r6, r7, r8)     // Catch: java.io.IOException -> L47 java.lang.Throwable -> L49
            goto L1d
        L47:
            throw r1     // Catch: java.lang.Throwable -> L49
        L48:
            throw r1     // Catch: java.lang.Throwable -> L49
        L49:
            r5 = move-exception
            java.util.concurrent.locks.Lock r6 = com.bumptech.glide.load.resource.bitmap.TransformationUtils.getBitmapDrawableLock()
            r6.unlock()
            throw r5
        */
        throw new UnsupportedOperationException("Method not decompiled: com.bumptech.glide.load.resource.bitmap.Downsampler.decodeStream(com.bumptech.glide.load.resource.bitmap.ImageReader, android.graphics.BitmapFactory$Options, com.bumptech.glide.load.resource.bitmap.Downsampler$DecodeCallbacks, com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool):android.graphics.Bitmap");
    }

    private static boolean isScaling(BitmapFactory.Options options) {
        return options.inTargetDensity > 0 && options.inDensity > 0 && options.inTargetDensity != options.inDensity;
    }

    private static void logDecode(int i, int i2, String str, BitmapFactory.Options options, Bitmap bitmap, int i3, int i4, long j) {
        Log.v(TAG, "Decoded " + getBitmapString(bitmap) + " from [" + i + AnimatedProperty.PROPERTY_NAME_X + i2 + "] " + str + " with inBitmap " + getInBitmapString(options) + " for [" + i3 + AnimatedProperty.PROPERTY_NAME_X + i4 + "], sample size: " + options.inSampleSize + ", density: " + options.inDensity + ", target density: " + options.inTargetDensity + ", thread: " + Thread.currentThread().getName() + ", duration: " + LogTime.getElapsedMillis(j));
    }

    private static String getInBitmapString(BitmapFactory.Options options) {
        return getBitmapString(options.inBitmap);
    }

    private static String getBitmapString(Bitmap bitmap) {
        if (bitmap == null) {
            return null;
        }
        return "[" + bitmap.getWidth() + AnimatedProperty.PROPERTY_NAME_X + bitmap.getHeight() + "] " + bitmap.getConfig() + (" (" + bitmap.getAllocationByteCount() + ")");
    }

    private static IOException newIoExceptionForInBitmapAssertion(IllegalArgumentException illegalArgumentException, int i, int i2, String str, BitmapFactory.Options options) {
        return new IOException("Exception decoding bitmap, outWidth: " + i + ", outHeight: " + i2 + ", outMimeType: " + str + ", inBitmap: " + getInBitmapString(options), illegalArgumentException);
    }

    private static void setInBitmap(BitmapFactory.Options options, BitmapPool bitmapPool, int i, int i2) {
        if (options.inPreferredConfig == Bitmap.Config.HARDWARE) {
            return;
        }
        Bitmap.Config config = options.outConfig;
        if (config == null) {
            config = options.inPreferredConfig;
        }
        options.inBitmap = bitmapPool.getDirty(i, i2, config);
    }

    private static synchronized BitmapFactory.Options getDefaultOptions() {
        BitmapFactory.Options optionsPoll;
        Queue<BitmapFactory.Options> queue = OPTIONS_QUEUE;
        synchronized (queue) {
            optionsPoll = queue.poll();
        }
        if (optionsPoll == null) {
            optionsPoll = new BitmapFactory.Options();
            resetOptions(optionsPoll);
        }
        return optionsPoll;
    }

    private static void releaseOptions(BitmapFactory.Options options) {
        resetOptions(options);
        Queue<BitmapFactory.Options> queue = OPTIONS_QUEUE;
        synchronized (queue) {
            queue.offer(options);
        }
    }

    private static void resetOptions(BitmapFactory.Options options) {
        options.inTempStorage = null;
        options.inDither = false;
        options.inScaled = false;
        options.inSampleSize = 1;
        options.inPreferredConfig = null;
        options.inJustDecodeBounds = false;
        options.inDensity = 0;
        options.inTargetDensity = 0;
        options.inPreferredColorSpace = null;
        options.outColorSpace = null;
        options.outConfig = null;
        options.outWidth = 0;
        options.outHeight = 0;
        options.outMimeType = null;
        options.inBitmap = null;
        options.inMutable = true;
    }
}
