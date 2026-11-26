package com.miui.doodle.utils;

import android.app.ActivityManager;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.text.TextUtils;
import androidx.collection.LruCache;
import ch.qos.logback.core.CoreConstants;
import com.market.sdk.reflect.ReflectUtilsForMiui;
import com.miui.doodle.base.core.IDoodle;
import com.miui.doodle.document.DocumentReader;
import com.miui.doodle.document.DoodleDocument;
import com.miui.doodle.feature.draw.DoodleBitmap;
import com.miui.doodle.feature.draw.DoodlePenManager;
import com.miui.doodle.feature.draw.DoodleView;
import com.miui.notes.base.utils.Logger;
import com.xiaomi.mirror.synergy.CallMethod;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.util.Map;
import java.util.concurrent.CancellationException;
import java.util.concurrent.ConcurrentHashMap;
import kotlin.Metadata;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.CoroutineContext;
import kotlin.coroutines.intrinsics.IntrinsicsKt;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;
import kotlinx.coroutines.BuildersKt__Builders_commonKt;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.CoroutineScopeKt;
import kotlinx.coroutines.Deferred;
import kotlinx.coroutines.Dispatchers;
import kotlinx.coroutines.Job;

/* JADX INFO: compiled from: DoodleCacheManager.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000d\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0010%\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\n\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\n\n\u0002\u0018\u0002\n\u0002\b\u000e\n\u0002\u0018\u0002\n\u0002\b\u0003\bÆ\u0002\u0018\u00002\u00020\u0001B\t\b\u0002¢\u0006\u0004\b\u0002\u0010\u0003J\u000e\u0010\u0015\u001a\u00020\u00162\u0006\u0010\u0017\u001a\u00020\u0018J\u0018\u0010\u001e\u001a\u00020\u00162\u0006\u0010\u001f\u001a\u00020\u00052\b\u0010 \u001a\u0004\u0018\u00010\u000bJ\u0010\u0010!\u001a\u0004\u0018\u00010\u000b2\u0006\u0010\u001f\u001a\u00020\u0005J\u000e\u0010\"\u001a\u00020\u00162\u0006\u0010\u001f\u001a\u00020\u0005J\u000e\u0010#\u001a\u00020\u00162\u0006\u0010\u001f\u001a\u00020\u0005J\b\u0010$\u001a\u00020\u0016H\u0016J\u000e\u0010%\u001a\u00020\u00162\u0006\u0010&\u001a\u00020\u0018J\u0016\u00109\u001a\u00020\u00162\u0006\u0010:\u001a\u00020\u00052\u0006\u0010&\u001a\u00020\u0018J\u0018\u0010;\u001a\u0004\u0018\u00010)2\u0006\u0010:\u001a\u00020\u00052\u0006\u0010&\u001a\u00020\u0018J\u0006\u0010<\u001a\u00020\u0016J\u0006\u0010=\u001a\u00020\u0016J\u0018\u0010>\u001a\u00020\u00052\b\u0010 \u001a\u0004\u0018\u00010\u000b2\u0006\u0010?\u001a\u00020\u0005J\u0010\u0010@\u001a\u0004\u0018\u00010\u000b2\u0006\u0010A\u001a\u00020\u0005R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082T¢\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0007X\u0082T¢\u0006\u0002\n\u0000R\u000e\u0010\b\u001a\u00020\u0005X\u0086T¢\u0006\u0002\n\u0000R\u001a\u0010\t\u001a\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u000b0\nX\u0082\u0004¢\u0006\u0002\n\u0000R\u001c\u0010\f\u001a\u0004\u0018\u00010\rX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u000e\u0010\u000f\"\u0004\b\u0010\u0010\u0011R\u001c\u0010\u0012\u001a\u0004\u0018\u00010\rX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0013\u0010\u000f\"\u0004\b\u0014\u0010\u0011R\u0011\u0010\u0019\u001a\u00020\u001a8F¢\u0006\u0006\u001a\u0004\b\u0019\u0010\u001bR\u001a\u0010\u001c\u001a\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u000b0\u001dX\u0082\u0004¢\u0006\u0002\n\u0000R$\u0010'\u001a\f\u0012\u0006\u0012\u0004\u0018\u00010)\u0018\u00010(X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b*\u0010+\"\u0004\b,\u0010-R\u001c\u0010.\u001a\u0004\u0018\u00010\u0005X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b/\u00100\"\u0004\b1\u00102R\u001c\u00103\u001a\u0004\u0018\u000104X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b5\u00106\"\u0004\b7\u00108R\u0012\u0010B\u001a\u00020CX\u0096\u0005¢\u0006\u0006\u001a\u0004\bD\u0010E¨\u0006F"}, d2 = {"Lcom/miui/doodle/utils/DoodleCacheManager;", "Lkotlinx/coroutines/CoroutineScope;", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "()V", "TAG", "", "BRUSH_BITMAP_CACHE_SIZE", "", "HIS_CACHE_HEAD", "bitmapInSavingMap", "", "Landroid/graphics/Bitmap;", "cacheDir", "Ljava/io/File;", "getCacheDir", "()Ljava/io/File;", "setCacheDir", "(Ljava/io/File;)V", "cachePreDir", "getCachePreDir", "setCachePreDir", "init", "", "appContext", "Landroid/content/Context;", "isBusy", "", "()Z", "brushBitmapLruCache", "Landroidx/collection/LruCache;", "put", "key", DoodleBitmap.KEY_BITMAP, CallMethod.METHOD_GET, "removeKey", "removeKeyFromDisk", "clear", "resize", CoreConstants.CONTEXT_SCOPE_VALUE, "preLoadJob", "Lkotlinx/coroutines/Deferred;", "Lcom/miui/doodle/document/DoodleDocument;", "getPreLoadJob", "()Lkotlinx/coroutines/Deferred;", "setPreLoadJob", "(Lkotlinx/coroutines/Deferred;)V", "preLoadImagePath", "getPreLoadImagePath", "()Ljava/lang/String;", "setPreLoadImagePath", "(Ljava/lang/String;)V", "preLoadDoodleView", "Lcom/miui/doodle/base/core/IDoodle;", "getPreLoadDoodleView", "()Lcom/miui/doodle/base/core/IDoodle;", "setPreLoadDoodleView", "(Lcom/miui/doodle/base/core/IDoodle;)V", "preloadData", "imagePath", "doPreloadData", "cancelAndDeletePreloadData", "clearPreloadCache", "saveImageToFile", "nameHead", "restoreImageFromFile", "cacheKey", "coroutineContext", "Lkotlin/coroutines/CoroutineContext;", "getCoroutineContext", "()Lkotlin/coroutines/CoroutineContext;", "libDoodle_release"}, k = 1, mv = {2, 2, 0}, xi = 48)
public final class DoodleCacheManager implements CoroutineScope {
    private static final int BRUSH_BITMAP_CACHE_SIZE = 314572800;
    public static final String HIS_CACHE_HEAD = "his_";
    private static final String TAG = "DoodleCacheManager";
    private static File cacheDir;
    private static File cachePreDir;
    private static IDoodle preLoadDoodleView;
    private static String preLoadImagePath;
    private static Deferred<DoodleDocument> preLoadJob;
    private final /* synthetic */ CoroutineScope $$delegate_0 = CoroutineScopeKt.MainScope();
    public static final DoodleCacheManager INSTANCE = new DoodleCacheManager();
    private static final Map<String, Bitmap> bitmapInSavingMap = new ConcurrentHashMap();
    private static final LruCache<String, Bitmap> brushBitmapLruCache = new LruCache<String, Bitmap>() { // from class: com.miui.doodle.utils.DoodleCacheManager$brushBitmapLruCache$1
        /* JADX INFO: Access modifiers changed from: protected */
        @Override // androidx.collection.LruCache
        public int sizeOf(String key, Bitmap value) {
            Intrinsics.checkNotNullParameter(key, "key");
            Intrinsics.checkNotNullParameter(value, "value");
            return value.getByteCount();
        }

        /* JADX INFO: Access modifiers changed from: protected */
        @Override // androidx.collection.LruCache
        public void entryRemoved(boolean evicted, String key, Bitmap oldValue, Bitmap newValue) {
            Intrinsics.checkNotNullParameter(key, "key");
            Intrinsics.checkNotNullParameter(oldValue, "oldValue");
            super.entryRemoved(evicted, key, oldValue, newValue);
            synchronized (oldValue) {
                if (evicted) {
                    if (!oldValue.isRecycled()) {
                        oldValue.recycle();
                    }
                }
                Unit unit = Unit.INSTANCE;
            }
        }
    };

    @Override // kotlinx.coroutines.CoroutineScope
    public CoroutineContext getCoroutineContext() {
        return this.$$delegate_0.getCoroutineContext();
    }

    public final void removeKeyFromDisk(String key) {
        Intrinsics.checkNotNullParameter(key, "key");
    }

    private DoodleCacheManager() {
    }

    public final File getCacheDir() {
        return cacheDir;
    }

    public final void setCacheDir(File file) {
        cacheDir = file;
    }

    public final File getCachePreDir() {
        return cachePreDir;
    }

    public final void setCachePreDir(File file) {
        cachePreDir = file;
    }

    public final void init(Context appContext) {
        File file;
        File file2;
        Intrinsics.checkNotNullParameter(appContext, "appContext");
        File file3 = new File(appContext.getCacheDir(), "doodle_cache");
        cacheDir = file3;
        Intrinsics.checkNotNull(file3);
        if (!file3.exists() && (file2 = cacheDir) != null) {
            file2.mkdirs();
        }
        File file4 = new File(appContext.getExternalCacheDir(), "doodle_preload_cache");
        cachePreDir = file4;
        Intrinsics.checkNotNull(file4);
        if (file4.exists() || (file = cachePreDir) == null) {
            return;
        }
        file.mkdirs();
    }

    public final boolean isBusy() {
        return bitmapInSavingMap.size() > 5;
    }

    public final void put(String key, Bitmap bitmap) {
        Intrinsics.checkNotNullParameter(key, "key");
        LruCache<String, Bitmap> lruCache = brushBitmapLruCache;
        synchronized (lruCache) {
            if (bitmap == null) {
                return;
            }
            lruCache.put(key, bitmap);
        }
    }

    public final Bitmap get(String key) {
        Intrinsics.checkNotNullParameter(key, "key");
        LruCache<String, Bitmap> lruCache = brushBitmapLruCache;
        synchronized (lruCache) {
            Bitmap bitmap = lruCache.get(key);
            if (bitmap == null || bitmap.isRecycled()) {
                return null;
            }
            Logger.INSTANCE.i(TAG, "Cache hit!");
            return bitmap;
        }
    }

    public final void removeKey(String key) {
        Intrinsics.checkNotNullParameter(key, "key");
        brushBitmapLruCache.remove(key);
    }

    public void clear() {
        brushBitmapLruCache.evictAll();
    }

    public final void resize(Context context) {
        Intrinsics.checkNotNullParameter(context, "context");
        Object systemService = context.getSystemService("activity");
        Intrinsics.checkNotNull(systemService, "null cannot be cast to non-null type android.app.ActivityManager");
        int memoryClass = (((ActivityManager) systemService).getMemoryClass() * 1048576) / 8;
        if (BRUSH_BITMAP_CACHE_SIZE > memoryClass) {
            brushBitmapLruCache.resize(memoryClass);
        }
    }

    public final Deferred<DoodleDocument> getPreLoadJob() {
        return preLoadJob;
    }

    public final void setPreLoadJob(Deferred<DoodleDocument> deferred) {
        preLoadJob = deferred;
    }

    public final String getPreLoadImagePath() {
        return preLoadImagePath;
    }

    public final void setPreLoadImagePath(String str) {
        preLoadImagePath = str;
    }

    public final IDoodle getPreLoadDoodleView() {
        return preLoadDoodleView;
    }

    public final void setPreLoadDoodleView(IDoodle iDoodle) {
        preLoadDoodleView = iDoodle;
    }

    public final void preloadData(String imagePath, Context context) {
        Intrinsics.checkNotNullParameter(imagePath, "imagePath");
        Intrinsics.checkNotNullParameter(context, "context");
        DoodlePenManager doodlePenManager = DoodlePenManager.INSTANCE;
        Context applicationContext = context.getApplicationContext();
        Intrinsics.checkNotNullExpressionValue(applicationContext, "getApplicationContext(...)");
        doodlePenManager.initAllPens(applicationContext);
        Deferred<DoodleDocument> deferred = preLoadJob;
        if (deferred != null) {
            Intrinsics.checkNotNull(deferred);
            if (!deferred.isCancelled()) {
                if (imagePath.equals(preLoadImagePath)) {
                    Logger.INSTANCE.d(TAG, "preloadData same data return");
                    return;
                }
                Deferred<DoodleDocument> deferred2 = preLoadJob;
                if (deferred2 != null) {
                    Job.DefaultImpls.cancel$default((Job) deferred2, (CancellationException) null, 1, (Object) null);
                }
                preLoadDoodleView = null;
            }
        }
        preLoadDoodleView = new DoodleView(context, null, null, null, false, false, false, 120, null);
        preLoadImagePath = imagePath;
        preLoadJob = BuildersKt__Builders_commonKt.async$default(this, Dispatchers.getIO(), null, new C02221(imagePath, context, null), 2, null);
    }

    /* JADX INFO: renamed from: com.miui.doodle.utils.DoodleCacheManager$preloadData$1, reason: invalid class name and case insensitive filesystem */
    /* JADX INFO: compiled from: DoodleCacheManager.kt */
    @Metadata(d1 = {"\u0000\n\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\u0010\u0000\u001a\u0004\u0018\u00010\u0001*\u00020\u0002H\n"}, d2 = {"<anonymous>", "Lcom/miui/doodle/document/DoodleDocument;", "Lkotlinx/coroutines/CoroutineScope;"}, k = 3, mv = {2, 2, 0}, xi = 48)
    @DebugMetadata(c = "com.miui.doodle.utils.DoodleCacheManager$preloadData$1", f = "DoodleCacheManager.kt", i = {}, l = {}, m = "invokeSuspend", n = {}, s = {})
    static final class C02221 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super DoodleDocument>, Object> {
        final /* synthetic */ Context $context;
        final /* synthetic */ String $imagePath;
        int label;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        C02221(String str, Context context, Continuation<? super C02221> continuation) {
            super(2, continuation);
            this.$imagePath = str;
            this.$context = context;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
            return new C02221(this.$imagePath, this.$context, continuation);
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(CoroutineScope coroutineScope, Continuation<? super DoodleDocument> continuation) {
            return ((C02221) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            IntrinsicsKt.getCOROUTINE_SUSPENDED();
            if (this.label != 0) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            ResultKt.throwOnFailure(obj);
            return DoodleCacheManager.INSTANCE.doPreloadData(this.$imagePath, this.$context);
        }
    }

    public final DoodleDocument doPreloadData(String imagePath, Context context) throws Throwable {
        DoodleDocument doodleDocument;
        Intrinsics.checkNotNullParameter(imagePath, "imagePath");
        Intrinsics.checkNotNullParameter(context, "context");
        Logger.INSTANCE.d(TAG, "preloadData doPreloadData " + imagePath);
        if (!TextUtils.isEmpty(imagePath) && DocumentReader.INSTANCE.isDoodleFile(imagePath)) {
            File file = new File(Utils.INSTANCE.getDoodleCacheDir(), "doodle_snapshot_" + System.currentTimeMillis());
            Utils utils = Utils.INSTANCE;
            String absolutePath = file.getAbsolutePath();
            Intrinsics.checkNotNullExpressionValue(absolutePath, "getAbsolutePath(...)");
            File fileCopyFile = utils.copyFile(imagePath, absolutePath);
            if (fileCopyFile != null) {
                DocumentReader documentReader = new DocumentReader(fileCopyFile);
                IDoodle iDoodle = preLoadDoodleView;
                if (iDoodle != null) {
                    Intrinsics.checkNotNull(iDoodle);
                    doodleDocument = documentReader.read(context, iDoodle, true);
                    if (doodleDocument != null) {
                        doodleDocument.setSnapShotPath(fileCopyFile.getAbsolutePath());
                    }
                } else {
                    doodleDocument = null;
                }
                preLoadDoodleView = null;
                return doodleDocument;
            }
        }
        return null;
    }

    public final void cancelAndDeletePreloadData() {
        Deferred<DoodleDocument> deferred = preLoadJob;
        if (deferred != null) {
            Intrinsics.checkNotNull(deferred);
            if (!deferred.isCancelled()) {
                Deferred<DoodleDocument> deferred2 = preLoadJob;
                if (deferred2 != null) {
                    Job.DefaultImpls.cancel$default((Job) deferred2, (CancellationException) null, 1, (Object) null);
                }
                preLoadDoodleView = null;
            }
        }
        preLoadJob = null;
        preLoadImagePath = null;
        clearPreloadCache();
    }

    /* JADX INFO: renamed from: com.miui.doodle.utils.DoodleCacheManager$clearPreloadCache$1, reason: invalid class name */
    /* JADX INFO: compiled from: DoodleCacheManager.kt */
    @Metadata(d1 = {"\u0000\n\n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\u0010\u0000\u001a\u00020\u0001*\u00020\u0002H\n"}, d2 = {"<anonymous>", "", "Lkotlinx/coroutines/CoroutineScope;"}, k = 3, mv = {2, 2, 0}, xi = 48)
    @DebugMetadata(c = "com.miui.doodle.utils.DoodleCacheManager$clearPreloadCache$1", f = "DoodleCacheManager.kt", i = {}, l = {}, m = "invokeSuspend", n = {}, s = {})
    static final class AnonymousClass1 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
        int label;

        AnonymousClass1(Continuation<? super AnonymousClass1> continuation) {
            super(2, continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
            return new AnonymousClass1(continuation);
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(CoroutineScope coroutineScope, Continuation<? super Unit> continuation) {
            return ((AnonymousClass1) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            IntrinsicsKt.getCOROUTINE_SUSPENDED();
            if (this.label != 0) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            ResultKt.throwOnFailure(obj);
            Utils.INSTANCE.clearDoodlePreloadCacheDir();
            return Unit.INSTANCE;
        }
    }

    public final void clearPreloadCache() {
        BuildersKt__Builders_commonKt.launch$default(this, Dispatchers.getIO(), null, new AnonymousClass1(null), 2, null);
    }

    public final String saveImageToFile(Bitmap bitmap, String nameHead) {
        Intrinsics.checkNotNullParameter(nameHead, "nameHead");
        if (bitmap != null && !bitmap.isRecycled()) {
            try {
                String str = nameHead + bitmap.hashCode();
                FileOutputStream fileOutputStream = new FileOutputStream(new File(Utils.INSTANCE.getDoodleCacheDir(), str));
                BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(fileOutputStream);
                long jCurrentTimeMillis = System.currentTimeMillis();
                if (Build.VERSION.SDK_INT >= 30) {
                    bitmap.compress(Bitmap.CompressFormat.WEBP_LOSSLESS, 50, bufferedOutputStream);
                } else {
                    bitmap.compress(Bitmap.CompressFormat.PNG, 100, bufferedOutputStream);
                }
                Logger.INSTANCE.d("CacheManager", "saveImageToFile cost " + (System.currentTimeMillis() - jCurrentTimeMillis));
                bufferedOutputStream.flush();
                bufferedOutputStream.close();
                fileOutputStream.flush();
                fileOutputStream.close();
                return str;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return "";
    }

    public final Bitmap restoreImageFromFile(String cacheKey) {
        Intrinsics.checkNotNullParameter(cacheKey, "cacheKey");
        try {
            return BitmapFactory.decodeFile(new File(Utils.INSTANCE.getDoodleCacheDir(), cacheKey).getPath());
        } catch (Exception unused) {
            return null;
        }
    }
}
