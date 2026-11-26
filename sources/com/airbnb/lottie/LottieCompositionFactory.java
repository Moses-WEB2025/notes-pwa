package com.airbnb.lottie;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.util.Base64;
import com.airbnb.lottie.model.Font;
import com.airbnb.lottie.model.LottieCompositionCache;
import com.airbnb.lottie.network.NetworkCache;
import com.airbnb.lottie.parser.LottieCompositionMoshiParser;
import com.airbnb.lottie.parser.moshi.JsonReader;
import com.airbnb.lottie.utils.Logger;
import com.airbnb.lottie.utils.Utils;
import com.google.common.base.Ascii;
import com.miui.doodle.document.DocumentWriter;
import com.miui.notes.tool.AttachmentUtils;
import com.xiaomi.onetrack.util.ab;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.Callable;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.zip.GZIPInputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import net.lingala.zip4j.util.InternalZipConstants;
import okio.BufferedSource;
import okio.Okio;
import okio.Source;
import org.json.JSONObject;

/* JADX INFO: loaded from: classes.dex */
public class LottieCompositionFactory {
    private static final Map<String, LottieTask<LottieComposition>> taskCache = new HashMap();
    private static final Set<LottieTaskIdleListener> taskIdleListeners = new HashSet();
    private static final byte[] ZIP_MAGIC = {80, 75, 3, 4};
    private static final byte[] GZIP_MAGIC = {Ascii.US, -117, 8};

    private LottieCompositionFactory() {
    }

    public static void setMaxCacheSize(int i) {
        LottieCompositionCache.getInstance().resize(i);
    }

    public static void clearCache(Context context) {
        clearCache(context, true);
    }

    public static void clearCache(Context context, boolean z) {
        NetworkCache networkCache;
        taskCache.clear();
        LottieCompositionCache.getInstance().clear();
        if (!z || (networkCache = L.networkCache(context)) == null) {
            return;
        }
        networkCache.clear();
    }

    public static void registerLottieTaskIdleListener(LottieTaskIdleListener lottieTaskIdleListener) {
        taskIdleListeners.add(lottieTaskIdleListener);
        lottieTaskIdleListener.onIdleChanged(taskCache.size() == 0);
    }

    public static void unregisterLottieTaskIdleListener(LottieTaskIdleListener lottieTaskIdleListener) {
        taskIdleListeners.remove(lottieTaskIdleListener);
    }

    public static LottieTask<LottieComposition> fromUrl(Context context, String str) {
        return fromUrl(context, str, "url_" + str);
    }

    public static LottieTask<LottieComposition> fromUrl(final Context context, final String str, final String str2) {
        return cache(str2, new Callable() { // from class: com.airbnb.lottie.LottieCompositionFactory$$ExternalSyntheticLambda14
            @Override // java.util.concurrent.Callable
            public final Object call() {
                return LottieCompositionFactory.lambda$fromUrl$0(context, str, str2);
            }
        }, null);
    }

    static /* synthetic */ LottieResult lambda$fromUrl$0(Context context, String str, String str2) throws Exception {
        LottieResult<LottieComposition> lottieResultFetchSync = L.networkFetcher(context).fetchSync(context, str, str2);
        if (str2 != null && lottieResultFetchSync.getValue() != null) {
            LottieCompositionCache.getInstance().put(str2, lottieResultFetchSync.getValue());
        }
        return lottieResultFetchSync;
    }

    public static LottieResult<LottieComposition> fromUrlSync(Context context, String str) {
        return fromUrlSync(context, str, str);
    }

    public static LottieResult<LottieComposition> fromUrlSync(Context context, String str, String str2) {
        LottieComposition lottieComposition = str2 == null ? null : LottieCompositionCache.getInstance().get(str2);
        if (lottieComposition != null) {
            return new LottieResult<>(lottieComposition);
        }
        LottieResult<LottieComposition> lottieResultFetchSync = L.networkFetcher(context).fetchSync(context, str, str2);
        if (str2 != null && lottieResultFetchSync.getValue() != null) {
            LottieCompositionCache.getInstance().put(str2, lottieResultFetchSync.getValue());
        }
        return lottieResultFetchSync;
    }

    public static LottieTask<LottieComposition> fromAsset(Context context, String str) {
        return fromAsset(context, str, "asset_" + str);
    }

    public static LottieTask<LottieComposition> fromAsset(Context context, final String str, final String str2) {
        final Context applicationContext = context.getApplicationContext();
        return cache(str2, new Callable() { // from class: com.airbnb.lottie.LottieCompositionFactory$$ExternalSyntheticLambda5
            @Override // java.util.concurrent.Callable
            public final Object call() {
                return LottieCompositionFactory.fromAssetSync(applicationContext, str, str2);
            }
        }, null);
    }

    public static LottieResult<LottieComposition> fromAssetSync(Context context, String str) {
        return fromAssetSync(context, str, "asset_" + str);
    }

    public static LottieResult<LottieComposition> fromAssetSync(Context context, String str, String str2) {
        LottieComposition lottieComposition = str2 == null ? null : LottieCompositionCache.getInstance().get(str2);
        if (lottieComposition != null) {
            return new LottieResult<>(lottieComposition);
        }
        try {
            return fromInputStreamSync(context, context.getAssets().open(str), str2);
        } catch (IOException e) {
            return new LottieResult<>((Throwable) e);
        }
    }

    public static LottieTask<LottieComposition> fromInputStream(Context context, final InputStream inputStream, final String str) {
        final Context applicationContext = context == null ? null : context.getApplicationContext();
        return cache(str, new Callable() { // from class: com.airbnb.lottie.LottieCompositionFactory$$ExternalSyntheticLambda15
            @Override // java.util.concurrent.Callable
            public final Object call() {
                return LottieCompositionFactory.fromInputStreamSync(applicationContext, inputStream, str);
            }
        }, null);
    }

    public static LottieResult<LottieComposition> fromInputStreamSync(Context context, InputStream inputStream, String str) {
        LottieComposition lottieComposition = str == null ? null : LottieCompositionCache.getInstance().get(str);
        if (lottieComposition != null) {
            return new LottieResult<>(lottieComposition);
        }
        try {
            BufferedSource bufferedSourceBuffer = Okio.buffer(Okio.source(inputStream));
            if (isZipCompressed(bufferedSourceBuffer).booleanValue()) {
                return fromZipStreamSync(context, new ZipInputStream(bufferedSourceBuffer.inputStream()), str);
            }
            if (isGzipCompressed(bufferedSourceBuffer).booleanValue()) {
                return fromJsonInputStreamSync(new GZIPInputStream(bufferedSourceBuffer.inputStream()), str);
            }
            return fromJsonReaderSync(JsonReader.of(bufferedSourceBuffer), str);
        } catch (IOException e) {
            return new LottieResult<>((Throwable) e);
        }
    }

    public static LottieTask<LottieComposition> fromRawRes(Context context, int i) {
        return fromRawRes(context, i, rawResCacheKey(context, i));
    }

    public static LottieTask<LottieComposition> fromRawRes(Context context, final int i, final String str) {
        final WeakReference weakReference = new WeakReference(context);
        final Context applicationContext = context.getApplicationContext();
        return cache(str, new Callable() { // from class: com.airbnb.lottie.LottieCompositionFactory$$ExternalSyntheticLambda10
            @Override // java.util.concurrent.Callable
            public final Object call() {
                return LottieCompositionFactory.lambda$fromRawRes$3(weakReference, applicationContext, i, str);
            }
        }, null);
    }

    static /* synthetic */ LottieResult lambda$fromRawRes$3(WeakReference weakReference, Context context, int i, String str) throws Exception {
        Context context2 = (Context) weakReference.get();
        if (context2 != null) {
            context = context2;
        }
        return fromRawResSync(context, i, str);
    }

    public static LottieResult<LottieComposition> fromRawResSync(Context context, int i) {
        return fromRawResSync(context, i, rawResCacheKey(context, i));
    }

    public static LottieResult<LottieComposition> fromRawResSync(Context context, int i, String str) {
        LottieComposition lottieComposition = str == null ? null : LottieCompositionCache.getInstance().get(str);
        if (lottieComposition != null) {
            return new LottieResult<>(lottieComposition);
        }
        try {
            BufferedSource bufferedSourceBuffer = Okio.buffer(Okio.source(context.getResources().openRawResource(i)));
            if (isZipCompressed(bufferedSourceBuffer).booleanValue()) {
                return fromZipStreamSync(context, new ZipInputStream(bufferedSourceBuffer.inputStream()), str);
            }
            if (isGzipCompressed(bufferedSourceBuffer).booleanValue()) {
                try {
                    return fromJsonInputStreamSync(new GZIPInputStream(bufferedSourceBuffer.inputStream()), str);
                } catch (IOException e) {
                    return new LottieResult<>((Throwable) e);
                }
            }
            return fromJsonReaderSync(JsonReader.of(bufferedSourceBuffer), str);
        } catch (Resources.NotFoundException e2) {
            return new LottieResult<>((Throwable) e2);
        }
    }

    private static String rawResCacheKey(Context context, int i) {
        return "rawRes" + (isNightMode(context) ? "_night_" : "_day_") + i;
    }

    private static boolean isNightMode(Context context) {
        return (context.getResources().getConfiguration().uiMode & 48) == 32;
    }

    public static LottieTask<LottieComposition> fromJsonInputStream(final InputStream inputStream, final String str) {
        return cache(str, new Callable() { // from class: com.airbnb.lottie.LottieCompositionFactory$$ExternalSyntheticLambda16
            @Override // java.util.concurrent.Callable
            public final Object call() {
                return LottieCompositionFactory.fromJsonInputStreamSync(inputStream, str);
            }
        }, new Runnable() { // from class: com.airbnb.lottie.LottieCompositionFactory$$ExternalSyntheticLambda17
            @Override // java.lang.Runnable
            public final void run() {
                Utils.closeQuietly(inputStream);
            }
        });
    }

    public static LottieTask<LottieComposition> fromJsonInputStream(final InputStream inputStream, final String str, final boolean z) {
        return cache(str, new Callable() { // from class: com.airbnb.lottie.LottieCompositionFactory$$ExternalSyntheticLambda19
            @Override // java.util.concurrent.Callable
            public final Object call() {
                return LottieCompositionFactory.fromJsonInputStreamSync(inputStream, str, z);
            }
        }, new Runnable() { // from class: com.airbnb.lottie.LottieCompositionFactory$$ExternalSyntheticLambda1
            @Override // java.lang.Runnable
            public final void run() {
                LottieCompositionFactory.lambda$fromJsonInputStream$7(z, inputStream);
            }
        });
    }

    static /* synthetic */ void lambda$fromJsonInputStream$7(boolean z, InputStream inputStream) {
        if (z) {
            Utils.closeQuietly(inputStream);
        }
    }

    public static LottieResult<LottieComposition> fromJsonInputStreamSync(InputStream inputStream, String str) {
        return fromJsonInputStreamSync(inputStream, str, true);
    }

    public static LottieResult<LottieComposition> fromJsonInputStreamSync(InputStream inputStream, String str, boolean z) {
        return fromJsonSourceSync(Okio.source(inputStream), str, z);
    }

    @Deprecated
    public static LottieTask<LottieComposition> fromJson(final JSONObject jSONObject, final String str) {
        return cache(str, new Callable() { // from class: com.airbnb.lottie.LottieCompositionFactory$$ExternalSyntheticLambda18
            @Override // java.util.concurrent.Callable
            public final Object call() {
                return LottieCompositionFactory.fromJsonSync(jSONObject, str);
            }
        }, null);
    }

    @Deprecated
    public static LottieResult<LottieComposition> fromJsonSync(JSONObject jSONObject, String str) {
        return fromJsonStringSync(jSONObject.toString(), str);
    }

    public static LottieTask<LottieComposition> fromJsonString(final String str, final String str2) {
        return cache(str2, new Callable() { // from class: com.airbnb.lottie.LottieCompositionFactory$$ExternalSyntheticLambda2
            @Override // java.util.concurrent.Callable
            public final Object call() {
                return LottieCompositionFactory.fromJsonStringSync(str, str2);
            }
        }, null);
    }

    public static LottieResult<LottieComposition> fromJsonStringSync(String str, String str2) {
        return fromJsonSourceSync(Okio.source(new ByteArrayInputStream(str.getBytes())), str2);
    }

    public static LottieTask<LottieComposition> fromJsonSource(final Source source, final String str) {
        return cache(str, new Callable() { // from class: com.airbnb.lottie.LottieCompositionFactory$$ExternalSyntheticLambda0
            @Override // java.util.concurrent.Callable
            public final Object call() {
                return LottieCompositionFactory.fromJsonSourceSync(source, str);
            }
        }, new Runnable() { // from class: com.airbnb.lottie.LottieCompositionFactory$$ExternalSyntheticLambda11
            @Override // java.lang.Runnable
            public final void run() {
                Utils.closeQuietly(source);
            }
        });
    }

    public static LottieResult<LottieComposition> fromJsonSourceSync(Source source, String str) {
        return fromJsonSourceSync(source, str, true);
    }

    public static LottieResult<LottieComposition> fromJsonSourceSync(Source source, String str, boolean z) {
        return fromJsonReaderSyncInternal(JsonReader.of(Okio.buffer(source)), str, z);
    }

    public static LottieTask<LottieComposition> fromJsonReader(final JsonReader jsonReader, final String str) {
        return cache(str, new Callable() { // from class: com.airbnb.lottie.LottieCompositionFactory$$ExternalSyntheticLambda3
            @Override // java.util.concurrent.Callable
            public final Object call() {
                return LottieCompositionFactory.fromJsonReaderSync(jsonReader, str);
            }
        }, new Runnable() { // from class: com.airbnb.lottie.LottieCompositionFactory$$ExternalSyntheticLambda4
            @Override // java.lang.Runnable
            public final void run() {
                Utils.closeQuietly(jsonReader);
            }
        });
    }

    public static LottieResult<LottieComposition> fromJsonReaderSync(JsonReader jsonReader, String str) {
        return fromJsonReaderSync(jsonReader, str, true);
    }

    public static LottieResult<LottieComposition> fromJsonReaderSync(JsonReader jsonReader, String str, boolean z) {
        return fromJsonReaderSyncInternal(jsonReader, str, z);
    }

    private static LottieResult<LottieComposition> fromJsonReaderSyncInternal(JsonReader jsonReader, String str, boolean z) {
        try {
            LottieComposition lottieComposition = str == null ? null : LottieCompositionCache.getInstance().get(str);
            if (lottieComposition != null) {
                return new LottieResult<>(lottieComposition);
            }
            LottieComposition lottieComposition2 = LottieCompositionMoshiParser.parse(jsonReader);
            if (str != null) {
                LottieCompositionCache.getInstance().put(str, lottieComposition2);
            }
            return new LottieResult<>(lottieComposition2);
        } catch (Exception e) {
            return new LottieResult<>((Throwable) e);
        } finally {
            if (z) {
                Utils.closeQuietly(jsonReader);
            }
        }
    }

    public static LottieTask<LottieComposition> fromZipStream(ZipInputStream zipInputStream, String str) {
        return fromZipStream((Context) null, zipInputStream, str);
    }

    public static LottieTask<LottieComposition> fromZipStream(ZipInputStream zipInputStream, String str, boolean z) {
        return fromZipStream(null, zipInputStream, str, z);
    }

    public static LottieTask<LottieComposition> fromZipStream(final Context context, final ZipInputStream zipInputStream, final String str) {
        return cache(str, new Callable() { // from class: com.airbnb.lottie.LottieCompositionFactory$$ExternalSyntheticLambda6
            @Override // java.util.concurrent.Callable
            public final Object call() {
                return LottieCompositionFactory.fromZipStreamSync(context, zipInputStream, str);
            }
        }, new Runnable() { // from class: com.airbnb.lottie.LottieCompositionFactory$$ExternalSyntheticLambda7
            @Override // java.lang.Runnable
            public final void run() {
                Utils.closeQuietly(zipInputStream);
            }
        });
    }

    public static LottieTask<LottieComposition> fromZipStream(final Context context, final ZipInputStream zipInputStream, final String str, boolean z) {
        return cache(str, new Callable() { // from class: com.airbnb.lottie.LottieCompositionFactory$$ExternalSyntheticLambda12
            @Override // java.util.concurrent.Callable
            public final Object call() {
                return LottieCompositionFactory.fromZipStreamSync(context, zipInputStream, str);
            }
        }, z ? new Runnable() { // from class: com.airbnb.lottie.LottieCompositionFactory$$ExternalSyntheticLambda13
            @Override // java.lang.Runnable
            public final void run() {
                Utils.closeQuietly(zipInputStream);
            }
        } : null);
    }

    public static LottieResult<LottieComposition> fromZipStreamSync(ZipInputStream zipInputStream, String str) {
        return fromZipStreamSync(zipInputStream, str, true);
    }

    public static LottieResult<LottieComposition> fromZipStreamSync(ZipInputStream zipInputStream, String str, boolean z) {
        return fromZipStreamSync(null, zipInputStream, str, z);
    }

    public static LottieResult<LottieComposition> fromZipStreamSync(Context context, ZipInputStream zipInputStream, String str) {
        return fromZipStreamSync(context, zipInputStream, str, true);
    }

    public static LottieResult<LottieComposition> fromZipStreamSync(Context context, ZipInputStream zipInputStream, String str, boolean z) {
        try {
            return fromZipStreamSyncInternal(context, zipInputStream, str);
        } finally {
            if (z) {
                Utils.closeQuietly(zipInputStream);
            }
        }
    }

    /* JADX WARN: Code duplicated, block: B:69:0x0150 A[Catch: IOException -> 0x02b7, TryCatch #1 {IOException -> 0x02b7, blocks: (B:7:0x0019, B:9:0x001f, B:12:0x0028, B:14:0x0034, B:72:0x0183, B:15:0x0039, B:17:0x0045, B:18:0x004a, B:20:0x0056, B:21:0x006f, B:24:0x0079, B:26:0x0081, B:28:0x0089, B:31:0x0093, B:33:0x009b, B:36:0x00a4, B:37:0x00a9, B:39:0x00bb, B:41:0x00df, B:67:0x0146, B:69:0x0150, B:70:0x0170, B:66:0x0120, B:71:0x0174, B:5:0x000f, B:42:0x00e8, B:51:0x0107, B:64:0x011e, B:63:0x011b), top: B:123:0x000f, inners: #6 }] */
    private static LottieResult<LottieComposition> fromZipStreamSyncInternal(Context context, ZipInputStream zipInputStream, String str) {
        LottieComposition lottieComposition;
        HashMap map = new HashMap();
        HashMap map2 = new HashMap();
        if (str == null) {
            lottieComposition = null;
        } else {
            try {
                lottieComposition = LottieCompositionCache.getInstance().get(str);
            } catch (IOException e) {
                return new LottieResult<>((Throwable) e);
            }
        }
        if (lottieComposition != null) {
            return new LottieResult<>(lottieComposition);
        }
        ZipEntry nextEntry = zipInputStream.getNextEntry();
        LottieComposition value = null;
        while (nextEntry != null) {
            String name = nextEntry.getName();
            if (name.contains("__MACOSX")) {
                zipInputStream.closeEntry();
            } else if (nextEntry.getName().equalsIgnoreCase(DocumentWriter.MANIFEST)) {
                zipInputStream.closeEntry();
            } else if (nextEntry.getName().contains(".json")) {
                value = fromJsonReaderSyncInternal(JsonReader.of(Okio.buffer(Okio.source(zipInputStream))), null, false).getValue();
            } else if (name.contains(".png") || name.contains(".webp") || name.contains(com.miui.common.tool.Utils.JPG_SUFFIX) || name.contains(AttachmentUtils.IMAGE_SUFFIX_JPEG)) {
                String[] strArrSplit = name.split(InternalZipConstants.ZIP_FILE_SEPARATOR);
                map.put(strArrSplit[strArrSplit.length - 1], BitmapFactory.decodeStream(zipInputStream));
            } else if (name.contains(".ttf") || name.contains(".otf")) {
                String[] strArrSplit2 = name.split(InternalZipConstants.ZIP_FILE_SEPARATOR);
                String str2 = strArrSplit2[strArrSplit2.length - 1];
                String str3 = str2.split(ab.a)[0];
                if (context == null) {
                    return new LottieResult<>((Throwable) new IllegalStateException("Unable to extract font " + str3 + " please pass a non-null Context parameter"));
                }
                File file = new File(context.getCacheDir(), str2);
                try {
                    FileOutputStream fileOutputStream = new FileOutputStream(file);
                    try {
                        FileOutputStream fileOutputStream2 = new FileOutputStream(file);
                        try {
                            byte[] bArr = new byte[4096];
                            while (true) {
                                int i = zipInputStream.read(bArr);
                                if (i == -1) {
                                    break;
                                }
                                fileOutputStream2.write(bArr, 0, i);
                            }
                            fileOutputStream2.flush();
                            fileOutputStream2.close();
                            fileOutputStream.close();
                            Typeface typefaceCreateFromFile = Typeface.createFromFile(file);
                            if (!file.delete()) {
                                Logger.warning("Failed to delete temp font file " + file.getAbsolutePath() + ".");
                            }
                            map2.put(str3, typefaceCreateFromFile);
                        } catch (Throwable th) {
                            try {
                                fileOutputStream2.close();
                            } catch (Throwable th2) {
                                th.addSuppressed(th2);
                            }
                            throw th;
                        }
                    } catch (Throwable th3) {
                        try {
                            fileOutputStream.close();
                        } catch (Throwable th4) {
                            th3.addSuppressed(th4);
                        }
                        throw th3;
                    }
                } catch (Throwable th5) {
                    Logger.warning("Unable to save font " + str3 + " to the temporary file: " + str2 + ". ", th5);
                    Typeface typefaceCreateFromFile2 = Typeface.createFromFile(file);
                    if (!file.delete()) {
                        Logger.warning("Failed to delete temp font file " + file.getAbsolutePath() + ".");
                    }
                    map2.put(str3, typefaceCreateFromFile2);
                    nextEntry = zipInputStream.getNextEntry();
                }
            } else {
                zipInputStream.closeEntry();
            }
            nextEntry = zipInputStream.getNextEntry();
        }
        if (value == null) {
            return new LottieResult<>((Throwable) new IllegalArgumentException("Unable to parse composition"));
        }
        for (Map.Entry entry : map.entrySet()) {
            LottieImageAsset lottieImageAssetFindImageAssetForFileName = findImageAssetForFileName(value, (String) entry.getKey());
            if (lottieImageAssetFindImageAssetForFileName != null) {
                lottieImageAssetFindImageAssetForFileName.setBitmap(Utils.resizeBitmapIfNeeded((Bitmap) entry.getValue(), lottieImageAssetFindImageAssetForFileName.getWidth(), lottieImageAssetFindImageAssetForFileName.getHeight()));
            }
        }
        for (Map.Entry entry2 : map2.entrySet()) {
            boolean z = false;
            for (Font font : value.getFonts().values()) {
                if (font.getFamily().equals(entry2.getKey())) {
                    font.setTypeface((Typeface) entry2.getValue());
                    z = true;
                }
            }
            if (!z) {
                Logger.warning("Parsed font for " + ((String) entry2.getKey()) + " however it was not found in the animation.");
            }
        }
        if (map.isEmpty()) {
            Iterator<Map.Entry<String, LottieImageAsset>> it = value.getImages().entrySet().iterator();
            while (it.hasNext()) {
                LottieImageAsset value2 = it.next().getValue();
                if (value2 == null) {
                    return null;
                }
                String fileName = value2.getFileName();
                BitmapFactory.Options options = new BitmapFactory.Options();
                options.inScaled = true;
                options.inDensity = 160;
                if (fileName.startsWith("data:") && fileName.indexOf("base64,") > 0) {
                    try {
                        byte[] bArrDecode = Base64.decode(fileName.substring(fileName.indexOf(44) + 1), 0);
                        Bitmap bitmapDecodeByteArray = BitmapFactory.decodeByteArray(bArrDecode, 0, bArrDecode.length, options);
                        if (bitmapDecodeByteArray != null) {
                            value2.setBitmap(Utils.resizeBitmapIfNeeded(bitmapDecodeByteArray, value2.getWidth(), value2.getHeight()));
                        }
                    } catch (IllegalArgumentException e2) {
                        Logger.warning("data URL did not have correct base64 format.", e2);
                        return null;
                    }
                }
            }
        }
        if (str != null) {
            LottieCompositionCache.getInstance().put(str, value);
        }
        return new LottieResult<>(value);
    }

    private static Boolean isZipCompressed(BufferedSource bufferedSource) {
        return matchesMagicBytes(bufferedSource, ZIP_MAGIC);
    }

    private static Boolean isGzipCompressed(BufferedSource bufferedSource) {
        return matchesMagicBytes(bufferedSource, GZIP_MAGIC);
    }

    private static Boolean matchesMagicBytes(BufferedSource bufferedSource, byte[] bArr) {
        try {
            BufferedSource bufferedSourcePeek = bufferedSource.peek();
            for (byte b : bArr) {
                if (bufferedSourcePeek.readByte() != b) {
                    return false;
                }
            }
            bufferedSourcePeek.close();
            return true;
        } catch (Exception e) {
            Logger.error("Failed to check zip file header", e);
            return false;
        } catch (NoSuchMethodError unused) {
            return false;
        }
    }

    private static LottieImageAsset findImageAssetForFileName(LottieComposition lottieComposition, String str) {
        for (LottieImageAsset lottieImageAsset : lottieComposition.getImages().values()) {
            if (lottieImageAsset.getFileName().equals(str)) {
                return lottieImageAsset;
            }
        }
        return null;
    }

    private static LottieTask<LottieComposition> cache(final String str, Callable<LottieResult<LottieComposition>> callable, Runnable runnable) {
        LottieComposition lottieComposition = str == null ? null : LottieCompositionCache.getInstance().get(str);
        LottieTask<LottieComposition> lottieTask = lottieComposition != null ? new LottieTask<>(lottieComposition) : null;
        if (str != null) {
            Map<String, LottieTask<LottieComposition>> map = taskCache;
            if (map.containsKey(str)) {
                lottieTask = map.get(str);
            }
        }
        if (lottieTask != null) {
            if (runnable != null) {
                runnable.run();
            }
            return lottieTask;
        }
        LottieTask<LottieComposition> lottieTask2 = new LottieTask<>(callable);
        if (str != null) {
            final AtomicBoolean atomicBoolean = new AtomicBoolean(false);
            lottieTask2.addListener(new LottieListener() { // from class: com.airbnb.lottie.LottieCompositionFactory$$ExternalSyntheticLambda8
                @Override // com.airbnb.lottie.LottieListener
                public final void onResult(Object obj) {
                    LottieCompositionFactory.lambda$cache$18(str, atomicBoolean, (LottieComposition) obj);
                }
            });
            lottieTask2.addFailureListener(new LottieListener() { // from class: com.airbnb.lottie.LottieCompositionFactory$$ExternalSyntheticLambda9
                @Override // com.airbnb.lottie.LottieListener
                public final void onResult(Object obj) {
                    LottieCompositionFactory.lambda$cache$19(str, atomicBoolean, (Throwable) obj);
                }
            });
            if (!atomicBoolean.get()) {
                Map<String, LottieTask<LottieComposition>> map2 = taskCache;
                map2.put(str, lottieTask2);
                if (map2.size() == 1) {
                    notifyTaskCacheIdleListeners(false);
                }
            }
        }
        return lottieTask2;
    }

    static /* synthetic */ void lambda$cache$18(String str, AtomicBoolean atomicBoolean, LottieComposition lottieComposition) {
        Map<String, LottieTask<LottieComposition>> map = taskCache;
        map.remove(str);
        atomicBoolean.set(true);
        if (map.size() == 0) {
            notifyTaskCacheIdleListeners(true);
        }
    }

    static /* synthetic */ void lambda$cache$19(String str, AtomicBoolean atomicBoolean, Throwable th) {
        Map<String, LottieTask<LottieComposition>> map = taskCache;
        map.remove(str);
        atomicBoolean.set(true);
        if (map.size() == 0) {
            notifyTaskCacheIdleListeners(true);
        }
    }

    private static void notifyTaskCacheIdleListeners(boolean z) {
        ArrayList arrayList = new ArrayList(taskIdleListeners);
        for (int i = 0; i < arrayList.size(); i++) {
            ((LottieTaskIdleListener) arrayList.get(i)).onIdleChanged(z);
        }
    }
}
