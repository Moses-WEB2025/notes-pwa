package com.miui.doodle.feature.action;

import android.graphics.Bitmap;
import android.text.TextUtils;
import com.market.sdk.reflect.ReflectUtilsForMiui;
import com.miui.doodle.document.LayerBitmap;
import com.miui.doodle.feature.draw.DoodleView;
import com.miui.doodle.utils.DoodleCacheManager;
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

/* JADX INFO: compiled from: RemovedItemInfo.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000L\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010!\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0014\n\u0002\u0010\u000e\n\u0002\b\b\n\u0002\u0018\u0002\n\u0002\b\u0010\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0006\u0018\u00002\u00020\u0001BA\u0012\f\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003\u0012\u0006\u0010\u0005\u001a\u00020\u0006\u0012\u0006\u0010\u0007\u001a\u00020\b\u0012\u0006\u0010\t\u001a\u00020\n\u0012\b\u0010\u000b\u001a\u0004\u0018\u00010\f\u0012\b\u0010\r\u001a\u0004\u0018\u00010\f¢\u0006\u0004\b\u000e\u0010\u000fJ\u000e\u0010:\u001a\u00020;2\u0006\u0010<\u001a\u00020=J\u000e\u0010>\u001a\u00020;2\u0006\u0010<\u001a\u00020=J\u0006\u0010?\u001a\u00020;J\u0006\u0010@\u001a\u00020;J\u0006\u0010A\u001a\u00020;J\u0006\u0010B\u001a\u00020;R\u0017\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0010\u0010\u0011R\u0011\u0010\u0005\u001a\u00020\u0006¢\u0006\b\n\u0000\u001a\u0004\b\u0012\u0010\u0013R\u0011\u0010\u0007\u001a\u00020\b¢\u0006\b\n\u0000\u001a\u0004\b\u0014\u0010\u0015R\u001a\u0010\t\u001a\u00020\nX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0016\u0010\u0017\"\u0004\b\u0018\u0010\u0019R\u001c\u0010\u000b\u001a\u0004\u0018\u00010\fX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u001a\u0010\u001b\"\u0004\b\u001c\u0010\u001dR\u001c\u0010\r\u001a\u0004\u0018\u00010\fX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u001e\u0010\u001b\"\u0004\b\u001f\u0010\u001dR\u001a\u0010 \u001a\u00020!X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\"\u0010#\"\u0004\b$\u0010%R\u001a\u0010&\u001a\u00020!X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b'\u0010#\"\u0004\b(\u0010%R\"\u0010)\u001a\n\u0012\u0004\u0012\u00020!\u0018\u00010*X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b+\u0010,\"\u0004\b-\u0010.R\"\u0010/\u001a\n\u0012\u0004\u0012\u00020!\u0018\u00010*X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b0\u0010,\"\u0004\b1\u0010.R$\u00102\u001a\f\u0012\u0006\u0012\u0004\u0018\u00010\f\u0018\u00010*X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b3\u0010,\"\u0004\b4\u0010.R$\u00105\u001a\f\u0012\u0006\u0012\u0004\u0018\u00010\f\u0018\u00010*X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b6\u0010,\"\u0004\b7\u0010.R\u001a\u00108\u001a\u00020\nX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b8\u0010\u0017\"\u0004\b9\u0010\u0019¨\u0006C"}, d2 = {"Lcom/miui/doodle/feature/action/RemovedItemInfo;", "", "itemsRemovedList", "", "Lcom/miui/doodle/feature/action/SubRemovedItemInfo;", "layerBitmapIndex", "", "layerBitmap", "Lcom/miui/doodle/document/LayerBitmap;", "needRemovedLayerBitmap", "", "oldLayerBitmap", "Landroid/graphics/Bitmap;", "newLayerBitmap", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "(Ljava/util/List;ILcom/miui/doodle/document/LayerBitmap;ZLandroid/graphics/Bitmap;Landroid/graphics/Bitmap;)V", "getItemsRemovedList", "()Ljava/util/List;", "getLayerBitmapIndex", "()I", "getLayerBitmap", "()Lcom/miui/doodle/document/LayerBitmap;", "getNeedRemovedLayerBitmap", "()Z", "setNeedRemovedLayerBitmap", "(Z)V", "getOldLayerBitmap", "()Landroid/graphics/Bitmap;", "setOldLayerBitmap", "(Landroid/graphics/Bitmap;)V", "getNewLayerBitmap", "setNewLayerBitmap", "oldBmCacheKey", "", "getOldBmCacheKey", "()Ljava/lang/String;", "setOldBmCacheKey", "(Ljava/lang/String;)V", "newBmCacheKey", "getNewBmCacheKey", "setNewBmCacheKey", "deferredOldBmCacheKey", "Lkotlinx/coroutines/Deferred;", "getDeferredOldBmCacheKey", "()Lkotlinx/coroutines/Deferred;", "setDeferredOldBmCacheKey", "(Lkotlinx/coroutines/Deferred;)V", "deferredNewBmCacheKey", "getDeferredNewBmCacheKey", "setDeferredNewBmCacheKey", "deferredOldBm", "getDeferredOldBm", "setDeferredOldBm", "deferredNewBm", "getDeferredNewBm", "setDeferredNewBm", "isCachedFile", "setCachedFile", "restoreOldLayerBm", "", "doodleView", "Lcom/miui/doodle/feature/draw/DoodleView;", "restoreNewLayerBm", "cacheToFile", "restoreFromFile", "releaseJobs", "destroy", "libDoodle_release"}, k = 1, mv = {2, 2, 0}, xi = 48)
public final class RemovedItemInfo {
    private Deferred<Bitmap> deferredNewBm;
    private Deferred<String> deferredNewBmCacheKey;
    private Deferred<Bitmap> deferredOldBm;
    private Deferred<String> deferredOldBmCacheKey;
    private boolean isCachedFile;
    private final List<SubRemovedItemInfo> itemsRemovedList;
    private final LayerBitmap layerBitmap;
    private final int layerBitmapIndex;
    private boolean needRemovedLayerBitmap;
    private String newBmCacheKey;
    private Bitmap newLayerBitmap;
    private String oldBmCacheKey;
    private Bitmap oldLayerBitmap;

    public RemovedItemInfo(List<SubRemovedItemInfo> itemsRemovedList, int i, LayerBitmap layerBitmap, boolean z, Bitmap bitmap, Bitmap bitmap2) {
        Intrinsics.checkNotNullParameter(itemsRemovedList, "itemsRemovedList");
        Intrinsics.checkNotNullParameter(layerBitmap, "layerBitmap");
        this.itemsRemovedList = itemsRemovedList;
        this.layerBitmapIndex = i;
        this.layerBitmap = layerBitmap;
        this.needRemovedLayerBitmap = z;
        this.oldLayerBitmap = bitmap;
        this.newLayerBitmap = bitmap2;
        this.oldBmCacheKey = "";
        this.newBmCacheKey = "";
    }

    public final List<SubRemovedItemInfo> getItemsRemovedList() {
        return this.itemsRemovedList;
    }

    public final int getLayerBitmapIndex() {
        return this.layerBitmapIndex;
    }

    public final LayerBitmap getLayerBitmap() {
        return this.layerBitmap;
    }

    public final boolean getNeedRemovedLayerBitmap() {
        return this.needRemovedLayerBitmap;
    }

    public final Bitmap getNewLayerBitmap() {
        return this.newLayerBitmap;
    }

    public final Bitmap getOldLayerBitmap() {
        return this.oldLayerBitmap;
    }

    public final void setNeedRemovedLayerBitmap(boolean z) {
        this.needRemovedLayerBitmap = z;
    }

    public final void setNewLayerBitmap(Bitmap bitmap) {
        this.newLayerBitmap = bitmap;
    }

    public final void setOldLayerBitmap(Bitmap bitmap) {
        this.oldLayerBitmap = bitmap;
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

    /* JADX INFO: renamed from: com.miui.doodle.feature.action.RemovedItemInfo$restoreOldLayerBm$1, reason: invalid class name and case insensitive filesystem */
    /* JADX INFO: compiled from: RemovedItemInfo.kt */
    @Metadata(d1 = {"\u0000\n\n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\u0010\u0000\u001a\u00020\u0001*\u00020\u0002H\n"}, d2 = {"<anonymous>", "", "Lkotlinx/coroutines/CoroutineScope;"}, k = 3, mv = {2, 2, 0}, xi = 48)
    @DebugMetadata(c = "com.miui.doodle.feature.action.RemovedItemInfo$restoreOldLayerBm$1", f = "RemovedItemInfo.kt", i = {}, l = {34}, m = "invokeSuspend", n = {}, s = {})
    static final class C02051 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
        final /* synthetic */ DoodleView $doodleView;
        Object L$0;
        int label;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        C02051(DoodleView doodleView, Continuation<? super C02051> continuation) {
            super(2, continuation);
            this.$doodleView = doodleView;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
            return RemovedItemInfo.this.new C02051(this.$doodleView, continuation);
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(CoroutineScope coroutineScope, Continuation<? super Unit> continuation) {
            return ((C02051) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
        }

        /* JADX WARN: Code duplicated, block: B:22:0x004e  */
        /* JADX WARN: Code duplicated, block: B:24:0x0056  */
        /* JADX WARN: Code duplicated, block: B:26:0x0072 A[RETURN] */
        /* JADX WARN: Code duplicated, block: B:27:0x0073  */
        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            LayerBitmap layerBitmap;
            Object objAwait;
            LayerBitmap layerBitmap2;
            Object coroutine_suspended = IntrinsicsKt.getCOROUTINE_SUSPENDED();
            int i = this.label;
            if (i == 0) {
                ResultKt.throwOnFailure(obj);
                if (RemovedItemInfo.this.getOldLayerBitmap() != null) {
                    Bitmap oldLayerBitmap = RemovedItemInfo.this.getOldLayerBitmap();
                    Intrinsics.checkNotNull(oldLayerBitmap);
                    if (!oldLayerBitmap.isRecycled()) {
                        Bitmap oldLayerBitmap2 = RemovedItemInfo.this.getOldLayerBitmap();
                        if (oldLayerBitmap2 != null) {
                            RemovedItemInfo removedItemInfo = RemovedItemInfo.this;
                            synchronized (oldLayerBitmap2) {
                                removedItemInfo.getLayerBitmap().updateBitmap(oldLayerBitmap2);
                                Unit unit = Unit.INSTANCE;
                            }
                        }
                    } else if (RemovedItemInfo.this.getDeferredOldBm() != null) {
                        layerBitmap = RemovedItemInfo.this.getLayerBitmap();
                        Deferred<Bitmap> deferredOldBm = RemovedItemInfo.this.getDeferredOldBm();
                        Intrinsics.checkNotNull(deferredOldBm);
                        this.L$0 = layerBitmap;
                        this.label = 1;
                        objAwait = deferredOldBm.await(this);
                        if (objAwait == coroutine_suspended) {
                            return coroutine_suspended;
                        }
                        layerBitmap2 = layerBitmap;
                        obj = objAwait;
                    }
                } else if (RemovedItemInfo.this.getDeferredOldBm() != null) {
                    layerBitmap = RemovedItemInfo.this.getLayerBitmap();
                    Deferred<Bitmap> deferredOldBm2 = RemovedItemInfo.this.getDeferredOldBm();
                    Intrinsics.checkNotNull(deferredOldBm2);
                    this.L$0 = layerBitmap;
                    this.label = 1;
                    objAwait = deferredOldBm2.await(this);
                    if (objAwait == coroutine_suspended) {
                        return coroutine_suspended;
                    }
                    layerBitmap2 = layerBitmap;
                    obj = objAwait;
                }
                this.$doodleView.invalidate();
                return Unit.INSTANCE;
            }
            if (i != 1) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            layerBitmap2 = (LayerBitmap) this.L$0;
            ResultKt.throwOnFailure(obj);
            Intrinsics.checkNotNull(obj);
            layerBitmap2.updateBitmap((Bitmap) obj);
            RemovedItemInfo.this.setDeferredOldBm(null);
            this.$doodleView.invalidate();
            return Unit.INSTANCE;
        }
    }

    public final void restoreOldLayerBm(DoodleView doodleView) {
        Intrinsics.checkNotNullParameter(doodleView, "doodleView");
        BuildersKt__Builders_commonKt.launch$default(GlobalScope.INSTANCE, Dispatchers.getMain(), null, new C02051(doodleView, null), 2, null);
    }

    /* JADX INFO: renamed from: com.miui.doodle.feature.action.RemovedItemInfo$restoreNewLayerBm$1, reason: invalid class name and case insensitive filesystem */
    /* JADX INFO: compiled from: RemovedItemInfo.kt */
    @Metadata(d1 = {"\u0000\n\n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\u0010\u0000\u001a\u00020\u0001*\u00020\u0002H\n"}, d2 = {"<anonymous>", "", "Lkotlinx/coroutines/CoroutineScope;"}, k = 3, mv = {2, 2, 0}, xi = 48)
    @DebugMetadata(c = "com.miui.doodle.feature.action.RemovedItemInfo$restoreNewLayerBm$1", f = "RemovedItemInfo.kt", i = {}, l = {50}, m = "invokeSuspend", n = {}, s = {})
    static final class C02041 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
        final /* synthetic */ DoodleView $doodleView;
        Object L$0;
        int label;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        C02041(DoodleView doodleView, Continuation<? super C02041> continuation) {
            super(2, continuation);
            this.$doodleView = doodleView;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
            return RemovedItemInfo.this.new C02041(this.$doodleView, continuation);
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(CoroutineScope coroutineScope, Continuation<? super Unit> continuation) {
            return ((C02041) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
        }

        /* JADX WARN: Code duplicated, block: B:22:0x004e  */
        /* JADX WARN: Code duplicated, block: B:24:0x0056  */
        /* JADX WARN: Code duplicated, block: B:26:0x0072 A[RETURN] */
        /* JADX WARN: Code duplicated, block: B:27:0x0073  */
        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            LayerBitmap layerBitmap;
            Object objAwait;
            LayerBitmap layerBitmap2;
            Object coroutine_suspended = IntrinsicsKt.getCOROUTINE_SUSPENDED();
            int i = this.label;
            if (i == 0) {
                ResultKt.throwOnFailure(obj);
                if (RemovedItemInfo.this.getNewLayerBitmap() != null) {
                    Bitmap newLayerBitmap = RemovedItemInfo.this.getNewLayerBitmap();
                    Intrinsics.checkNotNull(newLayerBitmap);
                    if (!newLayerBitmap.isRecycled()) {
                        Bitmap newLayerBitmap2 = RemovedItemInfo.this.getNewLayerBitmap();
                        if (newLayerBitmap2 != null) {
                            RemovedItemInfo removedItemInfo = RemovedItemInfo.this;
                            synchronized (newLayerBitmap2) {
                                removedItemInfo.getLayerBitmap().updateBitmap(newLayerBitmap2);
                                Unit unit = Unit.INSTANCE;
                            }
                        }
                    } else if (RemovedItemInfo.this.getDeferredNewBm() != null) {
                        layerBitmap = RemovedItemInfo.this.getLayerBitmap();
                        Deferred<Bitmap> deferredNewBm = RemovedItemInfo.this.getDeferredNewBm();
                        Intrinsics.checkNotNull(deferredNewBm);
                        this.L$0 = layerBitmap;
                        this.label = 1;
                        objAwait = deferredNewBm.await(this);
                        if (objAwait == coroutine_suspended) {
                            return coroutine_suspended;
                        }
                        layerBitmap2 = layerBitmap;
                        obj = objAwait;
                    }
                } else if (RemovedItemInfo.this.getDeferredNewBm() != null) {
                    layerBitmap = RemovedItemInfo.this.getLayerBitmap();
                    Deferred<Bitmap> deferredNewBm2 = RemovedItemInfo.this.getDeferredNewBm();
                    Intrinsics.checkNotNull(deferredNewBm2);
                    this.L$0 = layerBitmap;
                    this.label = 1;
                    objAwait = deferredNewBm2.await(this);
                    if (objAwait == coroutine_suspended) {
                        return coroutine_suspended;
                    }
                    layerBitmap2 = layerBitmap;
                    obj = objAwait;
                }
                this.$doodleView.invalidate();
                return Unit.INSTANCE;
            }
            if (i != 1) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            layerBitmap2 = (LayerBitmap) this.L$0;
            ResultKt.throwOnFailure(obj);
            Intrinsics.checkNotNull(obj);
            layerBitmap2.updateBitmap((Bitmap) obj);
            RemovedItemInfo.this.setDeferredNewBm(null);
            this.$doodleView.invalidate();
            return Unit.INSTANCE;
        }
    }

    public final void restoreNewLayerBm(DoodleView doodleView) {
        Intrinsics.checkNotNullParameter(doodleView, "doodleView");
        BuildersKt__Builders_commonKt.launch$default(GlobalScope.INSTANCE, Dispatchers.getMain(), null, new C02041(doodleView, null), 2, null);
    }

    public final void cacheToFile() {
        if (this.isCachedFile) {
            BuildersKt__Builders_commonKt.launch$default(GlobalScope.INSTANCE, Dispatchers.getIO(), null, new AnonymousClass1(null), 2, null);
            BuildersKt__Builders_commonKt.launch$default(GlobalScope.INSTANCE, Dispatchers.getIO(), null, new AnonymousClass2(null), 2, null);
        }
    }

    /* JADX INFO: renamed from: com.miui.doodle.feature.action.RemovedItemInfo$cacheToFile$1, reason: invalid class name */
    /* JADX INFO: compiled from: RemovedItemInfo.kt */
    @Metadata(d1 = {"\u0000\n\n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\u0010\u0000\u001a\u00020\u0001*\u00020\u0002H\n"}, d2 = {"<anonymous>", "", "Lkotlinx/coroutines/CoroutineScope;"}, k = 3, mv = {2, 2, 0}, xi = 48)
    @DebugMetadata(c = "com.miui.doodle.feature.action.RemovedItemInfo$cacheToFile$1", f = "RemovedItemInfo.kt", i = {0}, l = {72}, m = "invokeSuspend", n = {"$this$launch"}, s = {"L$0"})
    static final class AnonymousClass1 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
        private /* synthetic */ Object L$0;
        Object L$1;
        int label;

        AnonymousClass1(Continuation<? super AnonymousClass1> continuation) {
            super(2, continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
            AnonymousClass1 anonymousClass1 = RemovedItemInfo.this.new AnonymousClass1(continuation);
            anonymousClass1.L$0 = obj;
            return anonymousClass1;
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(CoroutineScope coroutineScope, Continuation<? super Unit> continuation) {
            return ((AnonymousClass1) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            RemovedItemInfo removedItemInfo;
            CoroutineScope coroutineScope = (CoroutineScope) this.L$0;
            Object coroutine_suspended = IntrinsicsKt.getCOROUTINE_SUSPENDED();
            int i = this.label;
            if (i == 0) {
                ResultKt.throwOnFailure(obj);
                if (RemovedItemInfo.this.getOldLayerBitmap() != null) {
                    RemovedItemInfo.this.setDeferredOldBmCacheKey(BuildersKt__Builders_commonKt.async$default(coroutineScope, Dispatchers.getIO(), null, new C00371(RemovedItemInfo.this, null), 2, null));
                    RemovedItemInfo removedItemInfo2 = RemovedItemInfo.this;
                    Deferred<String> deferredOldBmCacheKey = removedItemInfo2.getDeferredOldBmCacheKey();
                    Intrinsics.checkNotNull(deferredOldBmCacheKey);
                    this.L$0 = SpillingKt.nullOutSpilledVariable(coroutineScope);
                    this.L$1 = removedItemInfo2;
                    this.label = 1;
                    Object objAwait = deferredOldBmCacheKey.await(this);
                    if (objAwait == coroutine_suspended) {
                        return coroutine_suspended;
                    }
                    obj = objAwait;
                    removedItemInfo = removedItemInfo2;
                }
                return Unit.INSTANCE;
            }
            if (i != 1) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            removedItemInfo = (RemovedItemInfo) this.L$1;
            ResultKt.throwOnFailure(obj);
            removedItemInfo.setOldBmCacheKey((String) obj);
            return Unit.INSTANCE;
        }

        /* JADX INFO: renamed from: com.miui.doodle.feature.action.RemovedItemInfo$cacheToFile$1$1, reason: invalid class name and collision with other inner class name */
        /* JADX INFO: compiled from: RemovedItemInfo.kt */
        @Metadata(d1 = {"\u0000\n\n\u0000\n\u0002\u0010\u000e\n\u0002\u0018\u0002\u0010\u0000\u001a\u00020\u0001*\u00020\u0002H\n"}, d2 = {"<anonymous>", "", "Lkotlinx/coroutines/CoroutineScope;"}, k = 3, mv = {2, 2, 0}, xi = 48)
        @DebugMetadata(c = "com.miui.doodle.feature.action.RemovedItemInfo$cacheToFile$1$1", f = "RemovedItemInfo.kt", i = {}, l = {}, m = "invokeSuspend", n = {}, s = {})
        static final class C00371 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super String>, Object> {
            int label;
            final /* synthetic */ RemovedItemInfo this$0;

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            C00371(RemovedItemInfo removedItemInfo, Continuation<? super C00371> continuation) {
                super(2, continuation);
                this.this$0 = removedItemInfo;
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
                return new C00371(this.this$0, continuation);
            }

            @Override // kotlin.jvm.functions.Function2
            public final Object invoke(CoroutineScope coroutineScope, Continuation<? super String> continuation) {
                return ((C00371) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Object invokeSuspend(Object obj) {
                IntrinsicsKt.getCOROUTINE_SUSPENDED();
                if (this.label != 0) {
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                }
                ResultKt.throwOnFailure(obj);
                String strSaveImageToFile = DoodleCacheManager.INSTANCE.saveImageToFile(this.this$0.getOldLayerBitmap(), DoodleCacheManager.HIS_CACHE_HEAD);
                if (!TextUtils.isEmpty(strSaveImageToFile)) {
                    Bitmap oldLayerBitmap = this.this$0.getOldLayerBitmap();
                    if (oldLayerBitmap != null) {
                        oldLayerBitmap.recycle();
                    }
                    this.this$0.setOldLayerBitmap(null);
                }
                return strSaveImageToFile;
            }
        }
    }

    /* JADX INFO: renamed from: com.miui.doodle.feature.action.RemovedItemInfo$cacheToFile$2, reason: invalid class name */
    /* JADX INFO: compiled from: RemovedItemInfo.kt */
    @Metadata(d1 = {"\u0000\n\n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\u0010\u0000\u001a\u00020\u0001*\u00020\u0002H\n"}, d2 = {"<anonymous>", "", "Lkotlinx/coroutines/CoroutineScope;"}, k = 3, mv = {2, 2, 0}, xi = 48)
    @DebugMetadata(c = "com.miui.doodle.feature.action.RemovedItemInfo$cacheToFile$2", f = "RemovedItemInfo.kt", i = {0}, l = {88}, m = "invokeSuspend", n = {"$this$launch"}, s = {"L$0"})
    static final class AnonymousClass2 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
        private /* synthetic */ Object L$0;
        Object L$1;
        int label;

        AnonymousClass2(Continuation<? super AnonymousClass2> continuation) {
            super(2, continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
            AnonymousClass2 anonymousClass2 = RemovedItemInfo.this.new AnonymousClass2(continuation);
            anonymousClass2.L$0 = obj;
            return anonymousClass2;
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(CoroutineScope coroutineScope, Continuation<? super Unit> continuation) {
            return ((AnonymousClass2) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            RemovedItemInfo removedItemInfo;
            CoroutineScope coroutineScope = (CoroutineScope) this.L$0;
            Object coroutine_suspended = IntrinsicsKt.getCOROUTINE_SUSPENDED();
            int i = this.label;
            if (i == 0) {
                ResultKt.throwOnFailure(obj);
                if (RemovedItemInfo.this.getNewLayerBitmap() != null) {
                    RemovedItemInfo.this.setDeferredNewBmCacheKey(BuildersKt__Builders_commonKt.async$default(coroutineScope, Dispatchers.getIO(), null, new AnonymousClass1(RemovedItemInfo.this, null), 2, null));
                    RemovedItemInfo removedItemInfo2 = RemovedItemInfo.this;
                    Deferred<String> deferredNewBmCacheKey = removedItemInfo2.getDeferredNewBmCacheKey();
                    Intrinsics.checkNotNull(deferredNewBmCacheKey);
                    this.L$0 = SpillingKt.nullOutSpilledVariable(coroutineScope);
                    this.L$1 = removedItemInfo2;
                    this.label = 1;
                    Object objAwait = deferredNewBmCacheKey.await(this);
                    if (objAwait == coroutine_suspended) {
                        return coroutine_suspended;
                    }
                    removedItemInfo = removedItemInfo2;
                    obj = objAwait;
                }
                return Unit.INSTANCE;
            }
            if (i != 1) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            removedItemInfo = (RemovedItemInfo) this.L$1;
            ResultKt.throwOnFailure(obj);
            removedItemInfo.setNewBmCacheKey((String) obj);
            RemovedItemInfo.this.setCachedFile(true);
            return Unit.INSTANCE;
        }

        /* JADX INFO: renamed from: com.miui.doodle.feature.action.RemovedItemInfo$cacheToFile$2$1, reason: invalid class name */
        /* JADX INFO: compiled from: RemovedItemInfo.kt */
        @Metadata(d1 = {"\u0000\n\n\u0000\n\u0002\u0010\u000e\n\u0002\u0018\u0002\u0010\u0000\u001a\u00020\u0001*\u00020\u0002H\n"}, d2 = {"<anonymous>", "", "Lkotlinx/coroutines/CoroutineScope;"}, k = 3, mv = {2, 2, 0}, xi = 48)
        @DebugMetadata(c = "com.miui.doodle.feature.action.RemovedItemInfo$cacheToFile$2$1", f = "RemovedItemInfo.kt", i = {}, l = {}, m = "invokeSuspend", n = {}, s = {})
        static final class AnonymousClass1 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super String>, Object> {
            int label;
            final /* synthetic */ RemovedItemInfo this$0;

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            AnonymousClass1(RemovedItemInfo removedItemInfo, Continuation<? super AnonymousClass1> continuation) {
                super(2, continuation);
                this.this$0 = removedItemInfo;
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
                return new AnonymousClass1(this.this$0, continuation);
            }

            @Override // kotlin.jvm.functions.Function2
            public final Object invoke(CoroutineScope coroutineScope, Continuation<? super String> continuation) {
                return ((AnonymousClass1) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Object invokeSuspend(Object obj) {
                IntrinsicsKt.getCOROUTINE_SUSPENDED();
                if (this.label != 0) {
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                }
                ResultKt.throwOnFailure(obj);
                String strSaveImageToFile = DoodleCacheManager.INSTANCE.saveImageToFile(this.this$0.getNewLayerBitmap(), DoodleCacheManager.HIS_CACHE_HEAD);
                if (!TextUtils.isEmpty(strSaveImageToFile)) {
                    Bitmap newLayerBitmap = this.this$0.getNewLayerBitmap();
                    if (newLayerBitmap != null) {
                        newLayerBitmap.recycle();
                    }
                    this.this$0.setNewLayerBitmap(null);
                }
                return strSaveImageToFile;
            }
        }
    }

    public final void restoreFromFile() {
        if (this.isCachedFile) {
            BuildersKt__Builders_commonKt.launch$default(GlobalScope.INSTANCE, Dispatchers.getIO(), null, new C02021(null), 2, null);
            BuildersKt__Builders_commonKt.launch$default(GlobalScope.INSTANCE, Dispatchers.getIO(), null, new C02032(null), 2, null);
        }
    }

    /* JADX INFO: renamed from: com.miui.doodle.feature.action.RemovedItemInfo$restoreFromFile$1, reason: invalid class name and case insensitive filesystem */
    /* JADX INFO: compiled from: RemovedItemInfo.kt */
    @Metadata(d1 = {"\u0000\n\n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\u0010\u0000\u001a\u00020\u0001*\u00020\u0002H\n"}, d2 = {"<anonymous>", "", "Lkotlinx/coroutines/CoroutineScope;"}, k = 3, mv = {2, 2, 0}, xi = 48)
    @DebugMetadata(c = "com.miui.doodle.feature.action.RemovedItemInfo$restoreFromFile$1", f = "RemovedItemInfo.kt", i = {}, l = {}, m = "invokeSuspend", n = {}, s = {})
    static final class C02021 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
        private /* synthetic */ Object L$0;
        int label;

        C02021(Continuation<? super C02021> continuation) {
            super(2, continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
            C02021 c02021 = RemovedItemInfo.this.new C02021(continuation);
            c02021.L$0 = obj;
            return c02021;
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(CoroutineScope coroutineScope, Continuation<? super Unit> continuation) {
            return ((C02021) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            CoroutineScope coroutineScope = (CoroutineScope) this.L$0;
            IntrinsicsKt.getCOROUTINE_SUSPENDED();
            if (this.label == 0) {
                ResultKt.throwOnFailure(obj);
                RemovedItemInfo.this.setDeferredOldBm(BuildersKt__Builders_commonKt.async$default(coroutineScope, Dispatchers.getIO(), null, new C00381(RemovedItemInfo.this, null), 2, null));
                return Unit.INSTANCE;
            }
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }

        /* JADX INFO: renamed from: com.miui.doodle.feature.action.RemovedItemInfo$restoreFromFile$1$1, reason: invalid class name and collision with other inner class name */
        /* JADX INFO: compiled from: RemovedItemInfo.kt */
        @Metadata(d1 = {"\u0000\n\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\u0010\u0000\u001a\u0004\u0018\u00010\u0001*\u00020\u0002H\n"}, d2 = {"<anonymous>", "Landroid/graphics/Bitmap;", "Lkotlinx/coroutines/CoroutineScope;"}, k = 3, mv = {2, 2, 0}, xi = 48)
        @DebugMetadata(c = "com.miui.doodle.feature.action.RemovedItemInfo$restoreFromFile$1$1", f = "RemovedItemInfo.kt", i = {}, l = {}, m = "invokeSuspend", n = {}, s = {})
        static final class C00381 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Bitmap>, Object> {
            int label;
            final /* synthetic */ RemovedItemInfo this$0;

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            C00381(RemovedItemInfo removedItemInfo, Continuation<? super C00381> continuation) {
                super(2, continuation);
                this.this$0 = removedItemInfo;
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
                return new C00381(this.this$0, continuation);
            }

            @Override // kotlin.jvm.functions.Function2
            public final Object invoke(CoroutineScope coroutineScope, Continuation<? super Bitmap> continuation) {
                return ((C00381) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
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

    /* JADX INFO: renamed from: com.miui.doodle.feature.action.RemovedItemInfo$restoreFromFile$2, reason: invalid class name and case insensitive filesystem */
    /* JADX INFO: compiled from: RemovedItemInfo.kt */
    @Metadata(d1 = {"\u0000\n\n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\u0010\u0000\u001a\u00020\u0001*\u00020\u0002H\n"}, d2 = {"<anonymous>", "", "Lkotlinx/coroutines/CoroutineScope;"}, k = 3, mv = {2, 2, 0}, xi = 48)
    @DebugMetadata(c = "com.miui.doodle.feature.action.RemovedItemInfo$restoreFromFile$2", f = "RemovedItemInfo.kt", i = {}, l = {}, m = "invokeSuspend", n = {}, s = {})
    static final class C02032 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
        private /* synthetic */ Object L$0;
        int label;

        C02032(Continuation<? super C02032> continuation) {
            super(2, continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
            C02032 c02032 = RemovedItemInfo.this.new C02032(continuation);
            c02032.L$0 = obj;
            return c02032;
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(CoroutineScope coroutineScope, Continuation<? super Unit> continuation) {
            return ((C02032) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            CoroutineScope coroutineScope = (CoroutineScope) this.L$0;
            IntrinsicsKt.getCOROUTINE_SUSPENDED();
            if (this.label == 0) {
                ResultKt.throwOnFailure(obj);
                RemovedItemInfo.this.setDeferredNewBm(BuildersKt__Builders_commonKt.async$default(coroutineScope, Dispatchers.getIO(), null, new AnonymousClass1(RemovedItemInfo.this, null), 2, null));
                return Unit.INSTANCE;
            }
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }

        /* JADX INFO: renamed from: com.miui.doodle.feature.action.RemovedItemInfo$restoreFromFile$2$1, reason: invalid class name */
        /* JADX INFO: compiled from: RemovedItemInfo.kt */
        @Metadata(d1 = {"\u0000\n\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\u0010\u0000\u001a\u0004\u0018\u00010\u0001*\u00020\u0002H\n"}, d2 = {"<anonymous>", "Landroid/graphics/Bitmap;", "Lkotlinx/coroutines/CoroutineScope;"}, k = 3, mv = {2, 2, 0}, xi = 48)
        @DebugMetadata(c = "com.miui.doodle.feature.action.RemovedItemInfo$restoreFromFile$2$1", f = "RemovedItemInfo.kt", i = {}, l = {}, m = "invokeSuspend", n = {}, s = {})
        static final class AnonymousClass1 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Bitmap>, Object> {
            int label;
            final /* synthetic */ RemovedItemInfo this$0;

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            AnonymousClass1(RemovedItemInfo removedItemInfo, Continuation<? super AnonymousClass1> continuation) {
                super(2, continuation);
                this.this$0 = removedItemInfo;
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
        Bitmap bitmap = this.oldLayerBitmap;
        if (bitmap != null) {
            bitmap.recycle();
        }
        this.oldLayerBitmap = null;
        Bitmap bitmap2 = this.newLayerBitmap;
        if (bitmap2 != null) {
            bitmap2.recycle();
        }
        this.newLayerBitmap = null;
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
    }
}
