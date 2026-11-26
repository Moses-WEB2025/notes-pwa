package com.miui.doodle.feature.action;

import android.graphics.Bitmap;
import android.text.TextUtils;
import com.market.sdk.reflect.ReflectUtilsForMiui;
import com.miui.doodle.document.LayerBitmap;
import com.miui.doodle.feature.draw.DoodleView;
import com.miui.doodle.utils.DoodleCacheManager;
import com.miui.notes.base.utils.Logger;
import defpackage.R2;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.CancellationException;
import kotlin.Metadata;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.IntrinsicsKt;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.SpillingKt;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;
import kotlinx.coroutines.BuildersKt__Builders_commonKt;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.Deferred;
import kotlinx.coroutines.Dispatchers;
import kotlinx.coroutines.GlobalScope;
import kotlinx.coroutines.Job;

/* JADX INFO: compiled from: EraserLayerBitmapInfo.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000N\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u000f\n\u0002\u0010\u000e\n\u0002\b\n\n\u0002\u0018\u0002\n\u0002\b\u000e\n\u0002\u0010\u000b\n\u0002\b\u0007\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0006\u0018\u00002\u00020\u0001BC\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u000e\u0010\u0004\u001a\n\u0012\u0004\u0012\u00020\u0006\u0018\u00010\u0005\u0012\u000e\u0010\u0007\u001a\n\u0012\u0004\u0012\u00020\b\u0018\u00010\u0005\u0012\b\u0010\t\u001a\u0004\u0018\u00010\n\u0012\b\u0010\u000b\u001a\u0004\u0018\u00010\n¢\u0006\u0004\b\f\u0010\rJ\u000e\u0010;\u001a\u00020<2\u0006\u0010=\u001a\u00020>J\u000e\u0010?\u001a\u00020<2\u0006\u0010=\u001a\u00020>J\u0006\u0010@\u001a\u00020<J\u0006\u0010A\u001a\u00020<J\u0006\u0010B\u001a\u00020<J\u0006\u0010C\u001a\u00020<R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u000e\u0010\u000fR\u0019\u0010\u0004\u001a\n\u0012\u0004\u0012\u00020\u0006\u0018\u00010\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u0010\u0010\u0011R\u0019\u0010\u0007\u001a\n\u0012\u0004\u0012\u00020\b\u0018\u00010\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u0012\u0010\u0011R\u001c\u0010\t\u001a\u0004\u0018\u00010\nX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0013\u0010\u0014\"\u0004\b\u0015\u0010\u0016R\u001c\u0010\u000b\u001a\u0004\u0018\u00010\nX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0017\u0010\u0014\"\u0004\b\u0018\u0010\u0016R\u0014\u0010\u0019\u001a\u00020\u001aX\u0086D¢\u0006\b\n\u0000\u001a\u0004\b\u001b\u0010\u001cR\u001a\u0010\u001d\u001a\u00020\u001aX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u001e\u0010\u001c\"\u0004\b\u001f\u0010 R\u001a\u0010!\u001a\u00020\u001aX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\"\u0010\u001c\"\u0004\b#\u0010 R\"\u0010$\u001a\n\u0012\u0004\u0012\u00020\u001a\u0018\u00010%X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b&\u0010'\"\u0004\b(\u0010)R\"\u0010*\u001a\n\u0012\u0004\u0012\u00020\u001a\u0018\u00010%X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b+\u0010'\"\u0004\b,\u0010)R$\u0010-\u001a\f\u0012\u0006\u0012\u0004\u0018\u00010\n\u0018\u00010%X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b.\u0010'\"\u0004\b/\u0010)R$\u00100\u001a\f\u0012\u0006\u0012\u0004\u0018\u00010\n\u0018\u00010%X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b1\u0010'\"\u0004\b2\u0010)R\u001a\u00103\u001a\u000204X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b3\u00105\"\u0004\b6\u00107R\u0011\u00108\u001a\u00020\u0001¢\u0006\b\n\u0000\u001a\u0004\b9\u0010:¨\u0006D"}, d2 = {"Lcom/miui/doodle/feature/action/EraserLayerBitmapInfo;", "", "layerBitmap", "Lcom/miui/doodle/document/LayerBitmap;", "erasedBrushPathInfos", "", "Lcom/miui/doodle/feature/action/ErasedBrushPathInfo;", "removedBrushPathInfos", "Lcom/miui/doodle/feature/action/RemovedBrushPathInfo;", "oldLayerBitmap", "Landroid/graphics/Bitmap;", "newLayerBitmap", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "(Lcom/miui/doodle/document/LayerBitmap;Ljava/util/List;Ljava/util/List;Landroid/graphics/Bitmap;Landroid/graphics/Bitmap;)V", "getLayerBitmap", "()Lcom/miui/doodle/document/LayerBitmap;", "getErasedBrushPathInfos", "()Ljava/util/List;", "getRemovedBrushPathInfos", "getOldLayerBitmap", "()Landroid/graphics/Bitmap;", "setOldLayerBitmap", "(Landroid/graphics/Bitmap;)V", "getNewLayerBitmap", "setNewLayerBitmap", "TAG", "", "getTAG", "()Ljava/lang/String;", "oldBmCacheKey", "getOldBmCacheKey", "setOldBmCacheKey", "(Ljava/lang/String;)V", "newBmCacheKey", "getNewBmCacheKey", "setNewBmCacheKey", "deferredOldBmCacheKey", "Lkotlinx/coroutines/Deferred;", "getDeferredOldBmCacheKey", "()Lkotlinx/coroutines/Deferred;", "setDeferredOldBmCacheKey", "(Lkotlinx/coroutines/Deferred;)V", "deferredNewBmCacheKey", "getDeferredNewBmCacheKey", "setDeferredNewBmCacheKey", "deferredOldBm", "getDeferredOldBm", "setDeferredOldBm", "deferredNewBm", "getDeferredNewBm", "setDeferredNewBm", "isCachedFile", "", "()Z", "setCachedFile", "(Z)V", "lock", "getLock", "()Ljava/lang/Object;", "restoreOldLayerBm", "", "doodleView", "Lcom/miui/doodle/feature/draw/DoodleView;", "restoreNewLayerBm", "cacheToFile", "restoreFromFile", "releaseJobs", "destroy", "libDoodle_release"}, k = 1, mv = {2, 2, 0}, xi = 48)
public final class EraserLayerBitmapInfo {
    private final String TAG;
    private Deferred<Bitmap> deferredNewBm;
    private Deferred<String> deferredNewBmCacheKey;
    private Deferred<Bitmap> deferredOldBm;
    private Deferred<String> deferredOldBmCacheKey;
    private final List<ErasedBrushPathInfo> erasedBrushPathInfos;
    private boolean isCachedFile;
    private final LayerBitmap layerBitmap;
    private final Object lock;
    private String newBmCacheKey;
    private Bitmap newLayerBitmap;
    private String oldBmCacheKey;
    private Bitmap oldLayerBitmap;
    private final List<RemovedBrushPathInfo> removedBrushPathInfos;

    public EraserLayerBitmapInfo(LayerBitmap layerBitmap, List<ErasedBrushPathInfo> list, List<RemovedBrushPathInfo> list2, Bitmap bitmap, Bitmap bitmap2) {
        Intrinsics.checkNotNullParameter(layerBitmap, "layerBitmap");
        this.layerBitmap = layerBitmap;
        this.erasedBrushPathInfos = list;
        this.removedBrushPathInfos = list2;
        this.oldLayerBitmap = bitmap;
        this.newLayerBitmap = bitmap2;
        this.TAG = "EraserLayerBitmapInfo";
        this.oldBmCacheKey = "";
        this.newBmCacheKey = "";
        this.lock = new Object();
    }

    public final LayerBitmap getLayerBitmap() {
        return this.layerBitmap;
    }

    public final List<ErasedBrushPathInfo> getErasedBrushPathInfos() {
        return this.erasedBrushPathInfos;
    }

    public final List<RemovedBrushPathInfo> getRemovedBrushPathInfos() {
        return this.removedBrushPathInfos;
    }

    public final Bitmap getOldLayerBitmap() {
        return this.oldLayerBitmap;
    }

    public final void setOldLayerBitmap(Bitmap bitmap) {
        this.oldLayerBitmap = bitmap;
    }

    public final Bitmap getNewLayerBitmap() {
        return this.newLayerBitmap;
    }

    public final void setNewLayerBitmap(Bitmap bitmap) {
        this.newLayerBitmap = bitmap;
    }

    public final String getTAG() {
        return this.TAG;
    }

    public final String getOldBmCacheKey() {
        return this.oldBmCacheKey;
    }

    public final void setOldBmCacheKey(String str) {
        Intrinsics.checkNotNullParameter(str, "<set-?>");
        this.oldBmCacheKey = str;
    }

    public final String getNewBmCacheKey() {
        return this.newBmCacheKey;
    }

    public final void setNewBmCacheKey(String str) {
        Intrinsics.checkNotNullParameter(str, "<set-?>");
        this.newBmCacheKey = str;
    }

    public final Deferred<String> getDeferredOldBmCacheKey() {
        return this.deferredOldBmCacheKey;
    }

    public final void setDeferredOldBmCacheKey(Deferred<String> deferred) {
        this.deferredOldBmCacheKey = deferred;
    }

    public final Deferred<String> getDeferredNewBmCacheKey() {
        return this.deferredNewBmCacheKey;
    }

    public final void setDeferredNewBmCacheKey(Deferred<String> deferred) {
        this.deferredNewBmCacheKey = deferred;
    }

    public final Deferred<Bitmap> getDeferredOldBm() {
        return this.deferredOldBm;
    }

    public final void setDeferredOldBm(Deferred<Bitmap> deferred) {
        this.deferredOldBm = deferred;
    }

    public final Deferred<Bitmap> getDeferredNewBm() {
        return this.deferredNewBm;
    }

    public final void setDeferredNewBm(Deferred<Bitmap> deferred) {
        this.deferredNewBm = deferred;
    }

    /* JADX INFO: renamed from: isCachedFile, reason: from getter */
    public final boolean getIsCachedFile() {
        return this.isCachedFile;
    }

    public final void setCachedFile(boolean z) {
        this.isCachedFile = z;
    }

    public final Object getLock() {
        return this.lock;
    }

    /* JADX INFO: renamed from: com.miui.doodle.feature.action.EraserLayerBitmapInfo$restoreOldLayerBm$1, reason: invalid class name and case insensitive filesystem */
    /* JADX INFO: compiled from: EraserLayerBitmapInfo.kt */
    @Metadata(d1 = {"\u0000\n\n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\u0010\u0000\u001a\u00020\u0001*\u00020\u0002H\n"}, d2 = {"<anonymous>", "", "Lkotlinx/coroutines/CoroutineScope;"}, k = 3, mv = {2, 2, 0}, xi = 48)
    @DebugMetadata(c = "com.miui.doodle.feature.action.EraserLayerBitmapInfo$restoreOldLayerBm$1", f = "EraserLayerBitmapInfo.kt", i = {0, 1, 2, 2, 2, 2}, l = {52, 60, 89}, m = "invokeSuspend", n = {"$this$launch", "$this$launch", "$this$launch", "restoreOldJobs", "jobs", "$i$a$-let-EraserLayerBitmapInfo$restoreOldLayerBm$1$5"}, s = {"L$0", "L$0", "L$0", "L$1", "L$3", "I$0"})
    static final class C02011 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
        final /* synthetic */ DoodleView $doodleView;
        int I$0;
        private /* synthetic */ Object L$0;
        Object L$1;
        Object L$2;
        Object L$3;
        int label;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        C02011(DoodleView doodleView, Continuation<? super C02011> continuation) {
            super(2, continuation);
            this.$doodleView = doodleView;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
            C02011 c02011 = EraserLayerBitmapInfo.this.new C02011(this.$doodleView, continuation);
            c02011.L$0 = obj;
            return c02011;
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(CoroutineScope coroutineScope, Continuation<? super Unit> continuation) {
            return ((C02011) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
        }

        /* JADX WARN: Code duplicated, block: B:51:0x00f5 A[Catch: Exception -> 0x0035, TRY_LEAVE, TryCatch #1 {Exception -> 0x0035, blocks: (B:10:0x0030, B:49:0x00f1, B:51:0x00f5, B:46:0x00d3), top: B:76:0x000f }] */
        /* JADX WARN: Code duplicated, block: B:56:0x0121  */
        /* JADX WARN: Code duplicated, block: B:59:0x012f A[LOOP:0: B:57:0x0129->B:59:0x012f, LOOP_END] */
        /* JADX WARN: Code duplicated, block: B:62:0x0160  */
        /* JADX WARN: Code duplicated, block: B:65:0x016c A[LOOP:1: B:63:0x0166->B:65:0x016c, LOOP_END] */
        /* JADX WARN: Code restructure failed: missing block: B:67:0x01a7, code lost:
        
            if (kotlinx.coroutines.AwaitKt.awaitAll(r2, r11) == r7) goto L68;
         */
        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        public final java.lang.Object invokeSuspend(java.lang.Object r12) {
            /*
                Method dump skipped, instruction units count: 451
                To view this dump change 'Code comments level' option to 'DEBUG'
            */
            throw new UnsupportedOperationException("Method not decompiled: com.miui.doodle.feature.action.EraserLayerBitmapInfo.C02011.invokeSuspend(java.lang.Object):java.lang.Object");
        }

        /* JADX INFO: renamed from: com.miui.doodle.feature.action.EraserLayerBitmapInfo$restoreOldLayerBm$1$2, reason: invalid class name */
        /* JADX INFO: compiled from: EraserLayerBitmapInfo.kt */
        @Metadata(d1 = {"\u0000\n\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\u0010\u0000\u001a\u0004\u0018\u00010\u0001*\u00020\u0002H\n"}, d2 = {"<anonymous>", "Landroid/graphics/Bitmap;", "Lkotlinx/coroutines/CoroutineScope;"}, k = 3, mv = {2, 2, 0}, xi = 48)
        @DebugMetadata(c = "com.miui.doodle.feature.action.EraserLayerBitmapInfo$restoreOldLayerBm$1$2", f = "EraserLayerBitmapInfo.kt", i = {}, l = {}, m = "invokeSuspend", n = {}, s = {})
        static final class AnonymousClass2 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Bitmap>, Object> {
            int label;
            final /* synthetic */ EraserLayerBitmapInfo this$0;

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            AnonymousClass2(EraserLayerBitmapInfo eraserLayerBitmapInfo, Continuation<? super AnonymousClass2> continuation) {
                super(2, continuation);
                this.this$0 = eraserLayerBitmapInfo;
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
                return new AnonymousClass2(this.this$0, continuation);
            }

            @Override // kotlin.jvm.functions.Function2
            public final Object invoke(CoroutineScope coroutineScope, Continuation<? super Bitmap> continuation) {
                return ((AnonymousClass2) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Object invokeSuspend(Object obj) {
                IntrinsicsKt.getCOROUTINE_SUSPENDED();
                if (this.label != 0) {
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                }
                ResultKt.throwOnFailure(obj);
                return DoodleCacheManager.INSTANCE.restoreImageFromFile(this.this$0.getOldBmCacheKey());
            }
        }
    }

    public final void restoreOldLayerBm(DoodleView doodleView) {
        Intrinsics.checkNotNullParameter(doodleView, "doodleView");
        BuildersKt__Builders_commonKt.launch$default(GlobalScope.INSTANCE, Dispatchers.getMain(), null, new C02011(doodleView, null), 2, null);
    }

    /* JADX INFO: renamed from: com.miui.doodle.feature.action.EraserLayerBitmapInfo$restoreNewLayerBm$1, reason: invalid class name and case insensitive filesystem */
    /* JADX INFO: compiled from: EraserLayerBitmapInfo.kt */
    @Metadata(d1 = {"\u0000\n\n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\u0010\u0000\u001a\u00020\u0001*\u00020\u0002H\n"}, d2 = {"<anonymous>", "", "Lkotlinx/coroutines/CoroutineScope;"}, k = 3, mv = {2, 2, 0}, xi = 48)
    @DebugMetadata(c = "com.miui.doodle.feature.action.EraserLayerBitmapInfo$restoreNewLayerBm$1", f = "EraserLayerBitmapInfo.kt", i = {0, 1, 2, 2, 2, 2}, l = {114, 122, 150}, m = "invokeSuspend", n = {"$this$launch", "$this$launch", "$this$launch", "restoreNewJobs", "jobs", "$i$a$-let-EraserLayerBitmapInfo$restoreNewLayerBm$1$5"}, s = {"L$0", "L$0", "L$0", "L$1", "L$3", "I$0"})
    static final class C02001 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
        final /* synthetic */ DoodleView $doodleView;
        int I$0;
        private /* synthetic */ Object L$0;
        Object L$1;
        Object L$2;
        Object L$3;
        int label;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        C02001(DoodleView doodleView, Continuation<? super C02001> continuation) {
            super(2, continuation);
            this.$doodleView = doodleView;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
            C02001 c02001 = EraserLayerBitmapInfo.this.new C02001(this.$doodleView, continuation);
            c02001.L$0 = obj;
            return c02001;
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(CoroutineScope coroutineScope, Continuation<? super Unit> continuation) {
            return ((C02001) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
        }

        /* JADX WARN: Code duplicated, block: B:51:0x00f5 A[Catch: Exception -> 0x0035, TRY_LEAVE, TryCatch #1 {Exception -> 0x0035, blocks: (B:10:0x0030, B:49:0x00f1, B:51:0x00f5, B:46:0x00d3), top: B:76:0x000f }] */
        /* JADX WARN: Code duplicated, block: B:56:0x0121  */
        /* JADX WARN: Code duplicated, block: B:59:0x012f A[LOOP:0: B:57:0x0129->B:59:0x012f, LOOP_END] */
        /* JADX WARN: Code duplicated, block: B:62:0x015c  */
        /* JADX WARN: Code duplicated, block: B:65:0x0168 A[LOOP:1: B:63:0x0162->B:65:0x0168, LOOP_END] */
        /* JADX WARN: Code restructure failed: missing block: B:67:0x01a3, code lost:
        
            if (kotlinx.coroutines.AwaitKt.awaitAll(r2, r11) == r7) goto L68;
         */
        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        public final java.lang.Object invokeSuspend(java.lang.Object r12) {
            /*
                Method dump skipped, instruction units count: 447
                To view this dump change 'Code comments level' option to 'DEBUG'
            */
            throw new UnsupportedOperationException("Method not decompiled: com.miui.doodle.feature.action.EraserLayerBitmapInfo.C02001.invokeSuspend(java.lang.Object):java.lang.Object");
        }

        /* JADX INFO: renamed from: com.miui.doodle.feature.action.EraserLayerBitmapInfo$restoreNewLayerBm$1$2, reason: invalid class name */
        /* JADX INFO: compiled from: EraserLayerBitmapInfo.kt */
        @Metadata(d1 = {"\u0000\n\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\u0010\u0000\u001a\u0004\u0018\u00010\u0001*\u00020\u0002H\n"}, d2 = {"<anonymous>", "Landroid/graphics/Bitmap;", "Lkotlinx/coroutines/CoroutineScope;"}, k = 3, mv = {2, 2, 0}, xi = 48)
        @DebugMetadata(c = "com.miui.doodle.feature.action.EraserLayerBitmapInfo$restoreNewLayerBm$1$2", f = "EraserLayerBitmapInfo.kt", i = {}, l = {}, m = "invokeSuspend", n = {}, s = {})
        static final class AnonymousClass2 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Bitmap>, Object> {
            int label;
            final /* synthetic */ EraserLayerBitmapInfo this$0;

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            AnonymousClass2(EraserLayerBitmapInfo eraserLayerBitmapInfo, Continuation<? super AnonymousClass2> continuation) {
                super(2, continuation);
                this.this$0 = eraserLayerBitmapInfo;
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
                return new AnonymousClass2(this.this$0, continuation);
            }

            @Override // kotlin.jvm.functions.Function2
            public final Object invoke(CoroutineScope coroutineScope, Continuation<? super Bitmap> continuation) {
                return ((AnonymousClass2) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Object invokeSuspend(Object obj) {
                IntrinsicsKt.getCOROUTINE_SUSPENDED();
                if (this.label != 0) {
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                }
                ResultKt.throwOnFailure(obj);
                return DoodleCacheManager.INSTANCE.restoreImageFromFile(this.this$0.getNewBmCacheKey());
            }
        }
    }

    public final void restoreNewLayerBm(DoodleView doodleView) {
        Intrinsics.checkNotNullParameter(doodleView, "doodleView");
        BuildersKt__Builders_commonKt.launch$default(GlobalScope.INSTANCE, Dispatchers.getMain(), null, new C02001(doodleView, null), 2, null);
    }

    public final void cacheToFile() {
        if (this.isCachedFile) {
            return;
        }
        BuildersKt__Builders_commonKt.launch$default(GlobalScope.INSTANCE, Dispatchers.getIO(), null, new AnonymousClass1(null), 2, null);
        BuildersKt__Builders_commonKt.launch$default(GlobalScope.INSTANCE, Dispatchers.getIO(), null, new AnonymousClass2(null), 2, null);
        List<ErasedBrushPathInfo> list = this.erasedBrushPathInfos;
        if (list != null) {
            Iterator<T> it = list.iterator();
            while (it.hasNext()) {
                ((ErasedBrushPathInfo) it.next()).cacheToFile();
            }
        }
    }

    /* JADX INFO: renamed from: com.miui.doodle.feature.action.EraserLayerBitmapInfo$cacheToFile$1, reason: invalid class name */
    /* JADX INFO: compiled from: EraserLayerBitmapInfo.kt */
    @Metadata(d1 = {"\u0000\n\n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\u0010\u0000\u001a\u00020\u0001*\u00020\u0002H\n"}, d2 = {"<anonymous>", "", "Lkotlinx/coroutines/CoroutineScope;"}, k = 3, mv = {2, 2, 0}, xi = 48)
    @DebugMetadata(c = "com.miui.doodle.feature.action.EraserLayerBitmapInfo$cacheToFile$1", f = "EraserLayerBitmapInfo.kt", i = {0, 0, 0}, l = {R2.array.ai_tag_input_send_blend_colors}, m = "invokeSuspend", n = {"$this$launch", "it", "$i$a$-let-EraserLayerBitmapInfo$cacheToFile$1$1"}, s = {"L$0", "L$1", "I$0"})
    static final class AnonymousClass1 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
        int I$0;
        private /* synthetic */ Object L$0;
        Object L$1;
        Object L$2;
        int label;

        AnonymousClass1(Continuation<? super AnonymousClass1> continuation) {
            super(2, continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
            AnonymousClass1 anonymousClass1 = EraserLayerBitmapInfo.this.new AnonymousClass1(continuation);
            anonymousClass1.L$0 = obj;
            return anonymousClass1;
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(CoroutineScope coroutineScope, Continuation<? super Unit> continuation) {
            return ((AnonymousClass1) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            EraserLayerBitmapInfo eraserLayerBitmapInfo;
            CoroutineScope coroutineScope = (CoroutineScope) this.L$0;
            Object coroutine_suspended = IntrinsicsKt.getCOROUTINE_SUSPENDED();
            int i = this.label;
            if (i == 0) {
                ResultKt.throwOnFailure(obj);
                Bitmap oldLayerBitmap = EraserLayerBitmapInfo.this.getOldLayerBitmap();
                if (oldLayerBitmap != null) {
                    EraserLayerBitmapInfo eraserLayerBitmapInfo2 = EraserLayerBitmapInfo.this;
                    eraserLayerBitmapInfo2.setDeferredOldBmCacheKey(BuildersKt__Builders_commonKt.async$default(coroutineScope, Dispatchers.getIO(), null, new EraserLayerBitmapInfo$cacheToFile$1$1$1(eraserLayerBitmapInfo2, oldLayerBitmap, null), 2, null));
                    Deferred<String> deferredOldBmCacheKey = eraserLayerBitmapInfo2.getDeferredOldBmCacheKey();
                    Intrinsics.checkNotNull(deferredOldBmCacheKey);
                    this.L$0 = SpillingKt.nullOutSpilledVariable(coroutineScope);
                    this.L$1 = SpillingKt.nullOutSpilledVariable(oldLayerBitmap);
                    this.L$2 = eraserLayerBitmapInfo2;
                    this.I$0 = 0;
                    this.label = 1;
                    obj = deferredOldBmCacheKey.await(this);
                    if (obj == coroutine_suspended) {
                        return coroutine_suspended;
                    }
                    eraserLayerBitmapInfo = eraserLayerBitmapInfo2;
                }
                return Unit.INSTANCE;
            }
            if (i != 1) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            eraserLayerBitmapInfo = (EraserLayerBitmapInfo) this.L$2;
            ResultKt.throwOnFailure(obj);
            eraserLayerBitmapInfo.setOldBmCacheKey((String) obj);
            return Unit.INSTANCE;
        }
    }

    /* JADX INFO: renamed from: com.miui.doodle.feature.action.EraserLayerBitmapInfo$cacheToFile$2, reason: invalid class name */
    /* JADX INFO: compiled from: EraserLayerBitmapInfo.kt */
    @Metadata(d1 = {"\u0000\n\n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\u0010\u0000\u001a\u00020\u0001*\u00020\u0002H\n"}, d2 = {"<anonymous>", "", "Lkotlinx/coroutines/CoroutineScope;"}, k = 3, mv = {2, 2, 0}, xi = 48)
    @DebugMetadata(c = "com.miui.doodle.feature.action.EraserLayerBitmapInfo$cacheToFile$2", f = "EraserLayerBitmapInfo.kt", i = {0, 0, 0}, l = {198}, m = "invokeSuspend", n = {"$this$launch", "it", "$i$a$-let-EraserLayerBitmapInfo$cacheToFile$2$1"}, s = {"L$0", "L$2", "I$0"})
    static final class AnonymousClass2 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
        int I$0;
        private /* synthetic */ Object L$0;
        Object L$1;
        Object L$2;
        Object L$3;
        int label;

        AnonymousClass2(Continuation<? super AnonymousClass2> continuation) {
            super(2, continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
            AnonymousClass2 anonymousClass2 = EraserLayerBitmapInfo.this.new AnonymousClass2(continuation);
            anonymousClass2.L$0 = obj;
            return anonymousClass2;
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(CoroutineScope coroutineScope, Continuation<? super Unit> continuation) {
            return ((AnonymousClass2) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            EraserLayerBitmapInfo eraserLayerBitmapInfo;
            EraserLayerBitmapInfo eraserLayerBitmapInfo2;
            CoroutineScope coroutineScope = (CoroutineScope) this.L$0;
            Object coroutine_suspended = IntrinsicsKt.getCOROUTINE_SUSPENDED();
            int i = this.label;
            if (i == 0) {
                ResultKt.throwOnFailure(obj);
                Bitmap newLayerBitmap = EraserLayerBitmapInfo.this.getNewLayerBitmap();
                if (newLayerBitmap != null) {
                    EraserLayerBitmapInfo eraserLayerBitmapInfo3 = EraserLayerBitmapInfo.this;
                    eraserLayerBitmapInfo3.setDeferredNewBmCacheKey(BuildersKt__Builders_commonKt.async$default(coroutineScope, Dispatchers.getIO(), null, new EraserLayerBitmapInfo$cacheToFile$2$1$1(eraserLayerBitmapInfo3, newLayerBitmap, null), 2, null));
                    Deferred<String> deferredNewBmCacheKey = eraserLayerBitmapInfo3.getDeferredNewBmCacheKey();
                    Intrinsics.checkNotNull(deferredNewBmCacheKey);
                    this.L$0 = SpillingKt.nullOutSpilledVariable(coroutineScope);
                    this.L$1 = eraserLayerBitmapInfo3;
                    this.L$2 = SpillingKt.nullOutSpilledVariable(newLayerBitmap);
                    this.L$3 = eraserLayerBitmapInfo3;
                    this.I$0 = 0;
                    this.label = 1;
                    obj = deferredNewBmCacheKey.await(this);
                    if (obj == coroutine_suspended) {
                        return coroutine_suspended;
                    }
                    eraserLayerBitmapInfo = eraserLayerBitmapInfo3;
                    eraserLayerBitmapInfo2 = eraserLayerBitmapInfo;
                }
                return Unit.INSTANCE;
            }
            if (i != 1) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            eraserLayerBitmapInfo2 = (EraserLayerBitmapInfo) this.L$3;
            eraserLayerBitmapInfo = (EraserLayerBitmapInfo) this.L$1;
            ResultKt.throwOnFailure(obj);
            eraserLayerBitmapInfo2.setNewBmCacheKey((String) obj);
            eraserLayerBitmapInfo.setCachedFile(true);
            return Unit.INSTANCE;
        }
    }

    /* JADX INFO: renamed from: com.miui.doodle.feature.action.EraserLayerBitmapInfo$restoreFromFile$1, reason: invalid class name and case insensitive filesystem */
    /* JADX INFO: compiled from: EraserLayerBitmapInfo.kt */
    @Metadata(d1 = {"\u0000\n\n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\u0010\u0000\u001a\u00020\u0001*\u00020\u0002H\n"}, d2 = {"<anonymous>", "", "Lkotlinx/coroutines/CoroutineScope;"}, k = 3, mv = {2, 2, 0}, xi = 48)
    @DebugMetadata(c = "com.miui.doodle.feature.action.EraserLayerBitmapInfo$restoreFromFile$1", f = "EraserLayerBitmapInfo.kt", i = {}, l = {}, m = "invokeSuspend", n = {}, s = {})
    static final class C01981 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
        private /* synthetic */ Object L$0;
        int label;

        C01981(Continuation<? super C01981> continuation) {
            super(2, continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
            C01981 c01981 = EraserLayerBitmapInfo.this.new C01981(continuation);
            c01981.L$0 = obj;
            return c01981;
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(CoroutineScope coroutineScope, Continuation<? super Unit> continuation) {
            return ((C01981) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            CoroutineScope coroutineScope = (CoroutineScope) this.L$0;
            IntrinsicsKt.getCOROUTINE_SUSPENDED();
            if (this.label == 0) {
                ResultKt.throwOnFailure(obj);
                EraserLayerBitmapInfo.this.setDeferredOldBm(BuildersKt__Builders_commonKt.async$default(coroutineScope, Dispatchers.getIO(), null, new C00361(EraserLayerBitmapInfo.this, null), 2, null));
                return Unit.INSTANCE;
            }
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }

        /* JADX INFO: renamed from: com.miui.doodle.feature.action.EraserLayerBitmapInfo$restoreFromFile$1$1, reason: invalid class name and collision with other inner class name */
        /* JADX INFO: compiled from: EraserLayerBitmapInfo.kt */
        @Metadata(d1 = {"\u0000\n\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\u0010\u0000\u001a\u0004\u0018\u00010\u0001*\u00020\u0002H\n"}, d2 = {"<anonymous>", "Landroid/graphics/Bitmap;", "Lkotlinx/coroutines/CoroutineScope;"}, k = 3, mv = {2, 2, 0}, xi = 48)
        @DebugMetadata(c = "com.miui.doodle.feature.action.EraserLayerBitmapInfo$restoreFromFile$1$1", f = "EraserLayerBitmapInfo.kt", i = {}, l = {}, m = "invokeSuspend", n = {}, s = {})
        static final class C00361 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Bitmap>, Object> {
            int label;
            final /* synthetic */ EraserLayerBitmapInfo this$0;

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            C00361(EraserLayerBitmapInfo eraserLayerBitmapInfo, Continuation<? super C00361> continuation) {
                super(2, continuation);
                this.this$0 = eraserLayerBitmapInfo;
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
                return new C00361(this.this$0, continuation);
            }

            @Override // kotlin.jvm.functions.Function2
            public final Object invoke(CoroutineScope coroutineScope, Continuation<? super Bitmap> continuation) {
                return ((C00361) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Object invokeSuspend(Object obj) {
                IntrinsicsKt.getCOROUTINE_SUSPENDED();
                if (this.label != 0) {
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                }
                ResultKt.throwOnFailure(obj);
                if (TextUtils.isEmpty(this.this$0.getOldBmCacheKey())) {
                    return null;
                }
                return DoodleCacheManager.INSTANCE.restoreImageFromFile(this.this$0.getOldBmCacheKey());
            }
        }
    }

    public final void restoreFromFile() {
        hashCode();
        BuildersKt__Builders_commonKt.launch$default(GlobalScope.INSTANCE, Dispatchers.getIO(), null, new C01981(null), 2, null);
        BuildersKt__Builders_commonKt.launch$default(GlobalScope.INSTANCE, Dispatchers.getIO(), null, new C01992(null), 2, null);
        List<ErasedBrushPathInfo> list = this.erasedBrushPathInfos;
        if (list != null) {
            Iterator<T> it = list.iterator();
            while (it.hasNext()) {
                ((ErasedBrushPathInfo) it.next()).restoreFromFile();
            }
        }
    }

    /* JADX INFO: renamed from: com.miui.doodle.feature.action.EraserLayerBitmapInfo$restoreFromFile$2, reason: invalid class name and case insensitive filesystem */
    /* JADX INFO: compiled from: EraserLayerBitmapInfo.kt */
    @Metadata(d1 = {"\u0000\n\n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\u0010\u0000\u001a\u00020\u0001*\u00020\u0002H\n"}, d2 = {"<anonymous>", "", "Lkotlinx/coroutines/CoroutineScope;"}, k = 3, mv = {2, 2, 0}, xi = 48)
    @DebugMetadata(c = "com.miui.doodle.feature.action.EraserLayerBitmapInfo$restoreFromFile$2", f = "EraserLayerBitmapInfo.kt", i = {}, l = {}, m = "invokeSuspend", n = {}, s = {})
    static final class C01992 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
        private /* synthetic */ Object L$0;
        int label;

        C01992(Continuation<? super C01992> continuation) {
            super(2, continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
            C01992 c01992 = EraserLayerBitmapInfo.this.new C01992(continuation);
            c01992.L$0 = obj;
            return c01992;
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(CoroutineScope coroutineScope, Continuation<? super Unit> continuation) {
            return ((C01992) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            CoroutineScope coroutineScope = (CoroutineScope) this.L$0;
            IntrinsicsKt.getCOROUTINE_SUSPENDED();
            if (this.label == 0) {
                ResultKt.throwOnFailure(obj);
                EraserLayerBitmapInfo.this.setDeferredNewBm(BuildersKt__Builders_commonKt.async$default(coroutineScope, Dispatchers.getIO(), null, new AnonymousClass1(EraserLayerBitmapInfo.this, null), 2, null));
                return Unit.INSTANCE;
            }
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }

        /* JADX INFO: renamed from: com.miui.doodle.feature.action.EraserLayerBitmapInfo$restoreFromFile$2$1, reason: invalid class name */
        /* JADX INFO: compiled from: EraserLayerBitmapInfo.kt */
        @Metadata(d1 = {"\u0000\n\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\u0010\u0000\u001a\u0004\u0018\u00010\u0001*\u00020\u0002H\n"}, d2 = {"<anonymous>", "Landroid/graphics/Bitmap;", "Lkotlinx/coroutines/CoroutineScope;"}, k = 3, mv = {2, 2, 0}, xi = 48)
        @DebugMetadata(c = "com.miui.doodle.feature.action.EraserLayerBitmapInfo$restoreFromFile$2$1", f = "EraserLayerBitmapInfo.kt", i = {}, l = {}, m = "invokeSuspend", n = {}, s = {})
        static final class AnonymousClass1 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Bitmap>, Object> {
            int label;
            final /* synthetic */ EraserLayerBitmapInfo this$0;

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            AnonymousClass1(EraserLayerBitmapInfo eraserLayerBitmapInfo, Continuation<? super AnonymousClass1> continuation) {
                super(2, continuation);
                this.this$0 = eraserLayerBitmapInfo;
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
                return new AnonymousClass1(this.this$0, continuation);
            }

            @Override // kotlin.jvm.functions.Function2
            public final Object invoke(CoroutineScope coroutineScope, Continuation<? super Bitmap> continuation) {
                return ((AnonymousClass1) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Object invokeSuspend(Object obj) {
                IntrinsicsKt.getCOROUTINE_SUSPENDED();
                if (this.label != 0) {
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                }
                ResultKt.throwOnFailure(obj);
                if (TextUtils.isEmpty(this.this$0.getNewBmCacheKey())) {
                    return null;
                }
                return DoodleCacheManager.INSTANCE.restoreImageFromFile(this.this$0.getNewBmCacheKey());
            }
        }
    }

    public final void releaseJobs() {
        Deferred<Bitmap> deferred = this.deferredOldBm;
        if (deferred != null) {
            Job.DefaultImpls.cancel$default((Job) deferred, (CancellationException) null, 1, (Object) null);
        }
        this.deferredOldBm = null;
        Deferred<Bitmap> deferred2 = this.deferredNewBm;
        if (deferred2 != null) {
            Job.DefaultImpls.cancel$default((Job) deferred2, (CancellationException) null, 1, (Object) null);
        }
        this.deferredNewBm = null;
    }

    public final void destroy() {
        Logger.INSTANCE.d("EraserLayerBitmapInfo", hashCode() + " destroy " + this);
        synchronized (this.lock) {
            Bitmap bitmap = this.oldLayerBitmap;
            if (bitmap != null) {
                bitmap.recycle();
                this.oldLayerBitmap = null;
            }
            Bitmap bitmap2 = this.newLayerBitmap;
            if (bitmap2 != null) {
                bitmap2.recycle();
                this.newLayerBitmap = null;
                Unit unit = Unit.INSTANCE;
            }
        }
        Deferred<String> deferred = this.deferredOldBmCacheKey;
        if (deferred != null) {
            Job.DefaultImpls.cancel$default((Job) deferred, (CancellationException) null, 1, (Object) null);
        }
        this.deferredOldBmCacheKey = null;
        Deferred<String> deferred2 = this.deferredNewBmCacheKey;
        if (deferred2 != null) {
            Job.DefaultImpls.cancel$default((Job) deferred2, (CancellationException) null, 1, (Object) null);
        }
        this.deferredNewBmCacheKey = null;
        Deferred<Bitmap> deferred3 = this.deferredOldBm;
        if (deferred3 != null) {
            Job.DefaultImpls.cancel$default((Job) deferred3, (CancellationException) null, 1, (Object) null);
        }
        this.deferredOldBm = null;
        Deferred<Bitmap> deferred4 = this.deferredNewBm;
        if (deferred4 != null) {
            Job.DefaultImpls.cancel$default((Job) deferred4, (CancellationException) null, 1, (Object) null);
        }
        this.deferredNewBm = null;
        List<ErasedBrushPathInfo> list = this.erasedBrushPathInfos;
        if (list != null) {
            Iterator<T> it = list.iterator();
            while (it.hasNext()) {
                ((ErasedBrushPathInfo) it.next()).destroy();
            }
        }
    }
}
