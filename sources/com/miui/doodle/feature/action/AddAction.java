package com.miui.doodle.feature.action;

import android.graphics.Bitmap;
import android.text.TextUtils;
import com.market.sdk.reflect.ReflectUtilsForMiui;
import com.miui.common.stat.NotesTextStat;
import com.miui.doodle.base.core.IDoodleItem;
import com.miui.doodle.document.LayerBitmap;
import com.miui.doodle.feature.draw.DoodleView;
import com.miui.doodle.feature.draw.LayerView;
import com.miui.doodle.utils.DoodleCacheManager;
import defpackage.R2;
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

/* JADX INFO: compiled from: AddAction.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000Z\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0010\n\u0002\u0010\u000e\n\u0002\b\n\n\u0002\u0018\u0002\n\u0002\b\u000e\n\u0002\u0010\u000b\n\u0002\b\u0004\n\u0002\u0010\u0000\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0006\u0018\u00002\u00020\u0001B3\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u0012\b\u0010\b\u001a\u0004\u0018\u00010\t\u0012\b\u0010\n\u001a\u0004\u0018\u00010\t¢\u0006\u0004\b\u000b\u0010\fJ\u0010\u0010<\u001a\u00020=2\u0006\u0010>\u001a\u00020?H\u0016J\u0010\u0010@\u001a\u00020=2\u0006\u0010>\u001a\u00020?H\u0016J\u0018\u0010A\u001a\u00020=2\u0006\u0010>\u001a\u00020?2\u0006\u0010B\u001a\u00020CH\u0002J\u0018\u0010D\u001a\u00020=2\u0006\u0010>\u001a\u00020?2\u0006\u0010B\u001a\u00020CH\u0002J\b\u0010E\u001a\u00020=H\u0016J\b\u0010F\u001a\u00020=H\u0016J\b\u0010G\u001a\u00020=H\u0016J\b\u0010H\u001a\u00020=H\u0016R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\r\u0010\u000eR\u0011\u0010\u0004\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u000f\u0010\u0010R\u0011\u0010\u0006\u001a\u00020\u0007¢\u0006\b\n\u0000\u001a\u0004\b\u0011\u0010\u0012R\u001c\u0010\b\u001a\u0004\u0018\u00010\tX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0013\u0010\u0014\"\u0004\b\u0015\u0010\u0016R\u001c\u0010\n\u001a\u0004\u0018\u00010\tX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0017\u0010\u0014\"\u0004\b\u0018\u0010\u0016R\u0014\u0010\u0019\u001a\u00020\u001aX\u0086D¢\u0006\b\n\u0000\u001a\u0004\b\u001b\u0010\u001cR\u001a\u0010\u001d\u001a\u00020\u001aX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u001e\u0010\u001c\"\u0004\b\u001f\u0010 R\u001a\u0010!\u001a\u00020\u001aX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\"\u0010\u001c\"\u0004\b#\u0010 R\"\u0010$\u001a\n\u0012\u0004\u0012\u00020\u001a\u0018\u00010%X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b&\u0010'\"\u0004\b(\u0010)R\"\u0010*\u001a\n\u0012\u0004\u0012\u00020\u001a\u0018\u00010%X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b+\u0010'\"\u0004\b,\u0010)R$\u0010-\u001a\f\u0012\u0006\u0012\u0004\u0018\u00010\t\u0018\u00010%X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b.\u0010'\"\u0004\b/\u0010)R$\u00100\u001a\f\u0012\u0006\u0012\u0004\u0018\u00010\t\u0018\u00010%X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b1\u0010'\"\u0004\b2\u0010)R\u001a\u00103\u001a\u000204X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b3\u00105\"\u0004\b6\u00107R\u0011\u00108\u001a\u000209¢\u0006\b\n\u0000\u001a\u0004\b:\u0010;¨\u0006I"}, d2 = {"Lcom/miui/doodle/feature/action/AddAction;", "Lcom/miui/doodle/feature/action/BaseAction;", "addedItem", "Lcom/miui/doodle/base/core/IDoodleItem;", "layerView", "Lcom/miui/doodle/feature/draw/LayerView;", "layerBitmapIndex", "", "oldLayerBitmap", "Landroid/graphics/Bitmap;", "newLayerBitmap", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "(Lcom/miui/doodle/base/core/IDoodleItem;Lcom/miui/doodle/feature/draw/LayerView;ILandroid/graphics/Bitmap;Landroid/graphics/Bitmap;)V", "getAddedItem", "()Lcom/miui/doodle/base/core/IDoodleItem;", "getLayerView", "()Lcom/miui/doodle/feature/draw/LayerView;", "getLayerBitmapIndex", "()I", "getOldLayerBitmap", "()Landroid/graphics/Bitmap;", "setOldLayerBitmap", "(Landroid/graphics/Bitmap;)V", "getNewLayerBitmap", "setNewLayerBitmap", "TAG", "", "getTAG", "()Ljava/lang/String;", "oldBmCacheKey", "getOldBmCacheKey", "setOldBmCacheKey", "(Ljava/lang/String;)V", "newBmCacheKey", "getNewBmCacheKey", "setNewBmCacheKey", "deferredOldBmCacheKey", "Lkotlinx/coroutines/Deferred;", "getDeferredOldBmCacheKey", "()Lkotlinx/coroutines/Deferred;", "setDeferredOldBmCacheKey", "(Lkotlinx/coroutines/Deferred;)V", "deferredNewBmCacheKey", "getDeferredNewBmCacheKey", "setDeferredNewBmCacheKey", "deferredOldBm", "getDeferredOldBm", "setDeferredOldBm", "deferredNewBm", "getDeferredNewBm", "setDeferredNewBm", "isCachedFile", "", "()Z", "setCachedFile", "(Z)V", "lock", "", "getLock", "()Ljava/lang/Object;", NotesTextStat.UNDO, "", "doodleView", "Lcom/miui/doodle/feature/draw/DoodleView;", NotesTextStat.REDO, "restoreOldLayerBm", "layerBm", "Lcom/miui/doodle/document/LayerBitmap;", "restoreNewLayerBm", "cacheToFile", "restoreFromFile", "releaseJobs", "destroy", "libDoodle_release"}, k = 1, mv = {2, 2, 0}, xi = 48)
public final class AddAction extends BaseAction {
    private final String TAG;
    private final IDoodleItem addedItem;
    private Deferred<Bitmap> deferredNewBm;
    private Deferred<String> deferredNewBmCacheKey;
    private Deferred<Bitmap> deferredOldBm;
    private Deferred<String> deferredOldBmCacheKey;
    private boolean isCachedFile;
    private final int layerBitmapIndex;
    private final LayerView layerView;
    private final Object lock;
    private String newBmCacheKey;
    private Bitmap newLayerBitmap;
    private String oldBmCacheKey;
    private Bitmap oldLayerBitmap;

    public final IDoodleItem getAddedItem() {
        return this.addedItem;
    }

    public final LayerView getLayerView() {
        return this.layerView;
    }

    public final int getLayerBitmapIndex() {
        return this.layerBitmapIndex;
    }

    public final Bitmap getOldLayerBitmap() {
        return this.oldLayerBitmap;
    }

    public final void setOldLayerBitmap(Bitmap bitmap) {
        this.oldLayerBitmap = bitmap;
    }

    public AddAction(IDoodleItem addedItem, LayerView layerView, int i, Bitmap bitmap, Bitmap bitmap2) {
        Intrinsics.checkNotNullParameter(addedItem, "addedItem");
        Intrinsics.checkNotNullParameter(layerView, "layerView");
        this.addedItem = addedItem;
        this.layerView = layerView;
        this.layerBitmapIndex = i;
        this.oldLayerBitmap = bitmap;
        this.newLayerBitmap = bitmap2;
        this.TAG = "AddAction";
        this.oldBmCacheKey = "";
        this.newBmCacheKey = "";
        this.lock = new Object();
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

    @Override // com.miui.doodle.feature.action.IAction
    public void undo(DoodleView doodleView) {
        Intrinsics.checkNotNullParameter(doodleView, "doodleView");
        LayerBitmap layerBitmap = this.layerView.getLayer().getLayerBitmapList().get(this.layerBitmapIndex);
        layerBitmap.getItems().remove(this.addedItem);
        restoreOldLayerBm(doodleView, layerBitmap);
    }

    @Override // com.miui.doodle.feature.action.IAction
    public void redo(DoodleView doodleView) {
        Intrinsics.checkNotNullParameter(doodleView, "doodleView");
        LayerBitmap layerBitmap = this.layerView.getLayer().getLayerBitmapList().get(this.layerBitmapIndex);
        layerBitmap.getItems().add(this.addedItem);
        restoreNewLayerBm(doodleView, layerBitmap);
    }

    /* JADX INFO: renamed from: com.miui.doodle.feature.action.AddAction$restoreOldLayerBm$1, reason: invalid class name and case insensitive filesystem */
    /* JADX INFO: compiled from: AddAction.kt */
    @Metadata(d1 = {"\u0000\n\n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\u0010\u0000\u001a\u00020\u0001*\u00020\u0002H\n"}, d2 = {"<anonymous>", "", "Lkotlinx/coroutines/CoroutineScope;"}, k = 3, mv = {2, 2, 0}, xi = 48)
    @DebugMetadata(c = "com.miui.doodle.feature.action.AddAction$restoreOldLayerBm$1", f = "AddAction.kt", i = {}, l = {69, 80}, m = "invokeSuspend", n = {}, s = {})
    static final class C01971 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
        final /* synthetic */ DoodleView $doodleView;
        final /* synthetic */ LayerBitmap $layerBm;
        int label;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        C01971(DoodleView doodleView, LayerBitmap layerBitmap, Continuation<? super C01971> continuation) {
            super(2, continuation);
            this.$doodleView = doodleView;
            this.$layerBm = layerBitmap;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
            return AddAction.this.new C01971(this.$doodleView, this.$layerBm, continuation);
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(CoroutineScope coroutineScope, Continuation<? super Unit> continuation) {
            return ((C01971) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
        }

        /* JADX WARN: Code duplicated, block: B:40:0x0072 A[Catch: Exception -> 0x0023, TRY_LEAVE, TryCatch #1 {Exception -> 0x0023, blocks: (B:12:0x001f, B:38:0x006e, B:40:0x0072, B:33:0x005a, B:35:0x0062), top: B:56:0x0009 }] */
        /* JADX WARN: Code restructure failed: missing block: B:47:0x00be, code lost:
        
            if (r6 == r0) goto L48;
         */
        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        public final java.lang.Object invokeSuspend(java.lang.Object r6) {
            /*
                Method dump skipped, instruction units count: 237
                To view this dump change 'Code comments level' option to 'DEBUG'
            */
            throw new UnsupportedOperationException("Method not decompiled: com.miui.doodle.feature.action.AddAction.C01971.invokeSuspend(java.lang.Object):java.lang.Object");
        }

        /* JADX INFO: renamed from: com.miui.doodle.feature.action.AddAction$restoreOldLayerBm$1$3, reason: invalid class name */
        /* JADX INFO: compiled from: AddAction.kt */
        @Metadata(d1 = {"\u0000\n\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\u0010\u0000\u001a\u0004\u0018\u00010\u0001*\u00020\u0002H\n"}, d2 = {"<anonymous>", "Landroid/graphics/Bitmap;", "Lkotlinx/coroutines/CoroutineScope;"}, k = 3, mv = {2, 2, 0}, xi = 48)
        @DebugMetadata(c = "com.miui.doodle.feature.action.AddAction$restoreOldLayerBm$1$3", f = "AddAction.kt", i = {}, l = {}, m = "invokeSuspend", n = {}, s = {})
        static final class AnonymousClass3 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Bitmap>, Object> {
            int label;
            final /* synthetic */ AddAction this$0;

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            AnonymousClass3(AddAction addAction, Continuation<? super AnonymousClass3> continuation) {
                super(2, continuation);
                this.this$0 = addAction;
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
                return new AnonymousClass3(this.this$0, continuation);
            }

            @Override // kotlin.jvm.functions.Function2
            public final Object invoke(CoroutineScope coroutineScope, Continuation<? super Bitmap> continuation) {
                return ((AnonymousClass3) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
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

    private final void restoreOldLayerBm(DoodleView doodleView, LayerBitmap layerBm) {
        BuildersKt__Builders_commonKt.launch$default(GlobalScope.INSTANCE, Dispatchers.getMain(), null, new C01971(doodleView, layerBm, null), 2, null);
    }

    /* JADX INFO: renamed from: com.miui.doodle.feature.action.AddAction$restoreNewLayerBm$1, reason: invalid class name and case insensitive filesystem */
    /* JADX INFO: compiled from: AddAction.kt */
    @Metadata(d1 = {"\u0000\n\n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\u0010\u0000\u001a\u00020\u0001*\u00020\u0002H\n"}, d2 = {"<anonymous>", "", "Lkotlinx/coroutines/CoroutineScope;"}, k = 3, mv = {2, 2, 0}, xi = 48)
    @DebugMetadata(c = "com.miui.doodle.feature.action.AddAction$restoreNewLayerBm$1", f = "AddAction.kt", i = {}, l = {108, 119}, m = "invokeSuspend", n = {}, s = {})
    static final class C01961 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
        final /* synthetic */ DoodleView $doodleView;
        final /* synthetic */ LayerBitmap $layerBm;
        int label;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        C01961(DoodleView doodleView, LayerBitmap layerBitmap, Continuation<? super C01961> continuation) {
            super(2, continuation);
            this.$doodleView = doodleView;
            this.$layerBm = layerBitmap;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
            return AddAction.this.new C01961(this.$doodleView, this.$layerBm, continuation);
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(CoroutineScope coroutineScope, Continuation<? super Unit> continuation) {
            return ((C01961) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
        }

        /* JADX WARN: Code duplicated, block: B:40:0x0072 A[Catch: Exception -> 0x0023, TRY_LEAVE, TryCatch #1 {Exception -> 0x0023, blocks: (B:12:0x001f, B:38:0x006e, B:40:0x0072, B:33:0x005a, B:35:0x0062), top: B:56:0x0009 }] */
        /* JADX WARN: Code restructure failed: missing block: B:47:0x00be, code lost:
        
            if (r6 == r0) goto L48;
         */
        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        public final java.lang.Object invokeSuspend(java.lang.Object r6) {
            /*
                Method dump skipped, instruction units count: 237
                To view this dump change 'Code comments level' option to 'DEBUG'
            */
            throw new UnsupportedOperationException("Method not decompiled: com.miui.doodle.feature.action.AddAction.C01961.invokeSuspend(java.lang.Object):java.lang.Object");
        }

        /* JADX INFO: renamed from: com.miui.doodle.feature.action.AddAction$restoreNewLayerBm$1$3, reason: invalid class name */
        /* JADX INFO: compiled from: AddAction.kt */
        @Metadata(d1 = {"\u0000\n\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\u0010\u0000\u001a\u0004\u0018\u00010\u0001*\u00020\u0002H\n"}, d2 = {"<anonymous>", "Landroid/graphics/Bitmap;", "Lkotlinx/coroutines/CoroutineScope;"}, k = 3, mv = {2, 2, 0}, xi = 48)
        @DebugMetadata(c = "com.miui.doodle.feature.action.AddAction$restoreNewLayerBm$1$3", f = "AddAction.kt", i = {}, l = {}, m = "invokeSuspend", n = {}, s = {})
        static final class AnonymousClass3 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Bitmap>, Object> {
            int label;
            final /* synthetic */ AddAction this$0;

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            AnonymousClass3(AddAction addAction, Continuation<? super AnonymousClass3> continuation) {
                super(2, continuation);
                this.this$0 = addAction;
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
                return new AnonymousClass3(this.this$0, continuation);
            }

            @Override // kotlin.jvm.functions.Function2
            public final Object invoke(CoroutineScope coroutineScope, Continuation<? super Bitmap> continuation) {
                return ((AnonymousClass3) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
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

    private final void restoreNewLayerBm(DoodleView doodleView, LayerBitmap layerBm) {
        BuildersKt__Builders_commonKt.launch$default(GlobalScope.INSTANCE, Dispatchers.getMain(), null, new C01961(doodleView, layerBm, null), 2, null);
    }

    @Override // com.miui.doodle.feature.action.BaseAction, com.miui.doodle.feature.action.IAction
    public void cacheToFile() {
        if (!getEnableCacheToFile() || this.isCachedFile) {
            return;
        }
        BuildersKt__Builders_commonKt.launch$default(GlobalScope.INSTANCE, Dispatchers.getDefault(), null, new AnonymousClass1(null), 2, null);
    }

    /* JADX INFO: renamed from: com.miui.doodle.feature.action.AddAction$cacheToFile$1, reason: invalid class name */
    /* JADX INFO: compiled from: AddAction.kt */
    @Metadata(d1 = {"\u0000\n\n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\u0010\u0000\u001a\u00020\u0001*\u00020\u0002H\n"}, d2 = {"<anonymous>", "", "Lkotlinx/coroutines/CoroutineScope;"}, k = 3, mv = {2, 2, 0}, xi = 48)
    @DebugMetadata(c = "com.miui.doodle.feature.action.AddAction$cacheToFile$1", f = "AddAction.kt", i = {0, 0, 0, 1, 1, 1}, l = {R2.array.ai_material_view_bg_blend_modes, R2.array.ai_select_button_colors}, m = "invokeSuspend", n = {"$this$launch", "it", "$i$a$-let-AddAction$cacheToFile$1$1", "$this$launch", "it", "$i$a$-let-AddAction$cacheToFile$1$2"}, s = {"L$0", "L$1", "I$0", "L$0", "L$2", "I$0"})
    static final class AnonymousClass1 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
        int I$0;
        private /* synthetic */ Object L$0;
        Object L$1;
        Object L$2;
        Object L$3;
        int label;

        AnonymousClass1(Continuation<? super AnonymousClass1> continuation) {
            super(2, continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
            AnonymousClass1 anonymousClass1 = AddAction.this.new AnonymousClass1(continuation);
            anonymousClass1.L$0 = obj;
            return anonymousClass1;
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(CoroutineScope coroutineScope, Continuation<? super Unit> continuation) {
            return ((AnonymousClass1) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
        }

        /* JADX WARN: Code duplicated, block: B:19:0x0089  */
        /* JADX WARN: Code duplicated, block: B:22:0x00c5  */
        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            AddAction addAction;
            Bitmap newLayerBitmap;
            AddAction addAction2;
            AddAction addAction3;
            AddAction addAction4;
            CoroutineScope coroutineScope = (CoroutineScope) this.L$0;
            Object coroutine_suspended = IntrinsicsKt.getCOROUTINE_SUSPENDED();
            int i = this.label;
            if (i == 0) {
                ResultKt.throwOnFailure(obj);
                Bitmap oldLayerBitmap = AddAction.this.getOldLayerBitmap();
                if (oldLayerBitmap != null) {
                    AddAction addAction5 = AddAction.this;
                    addAction5.setDeferredOldBmCacheKey(BuildersKt__Builders_commonKt.async$default(coroutineScope, Dispatchers.getDefault(), null, new AddAction$cacheToFile$1$1$1(addAction5, oldLayerBitmap, null), 2, null));
                    Deferred<String> deferredOldBmCacheKey = addAction5.getDeferredOldBmCacheKey();
                    Intrinsics.checkNotNull(deferredOldBmCacheKey);
                    this.L$0 = coroutineScope;
                    this.L$1 = SpillingKt.nullOutSpilledVariable(oldLayerBitmap);
                    this.L$2 = addAction5;
                    this.I$0 = 0;
                    this.label = 1;
                    obj = deferredOldBmCacheKey.await(this);
                    if (obj != coroutine_suspended) {
                        addAction = addAction5;
                    }
                } else {
                    newLayerBitmap = AddAction.this.getNewLayerBitmap();
                    if (newLayerBitmap != null) {
                        addAction2 = AddAction.this;
                        addAction2.setDeferredNewBmCacheKey(BuildersKt__Builders_commonKt.async$default(coroutineScope, Dispatchers.getDefault(), null, new AddAction$cacheToFile$1$2$1(addAction2, newLayerBitmap, null), 2, null));
                        Deferred<String> deferredNewBmCacheKey = addAction2.getDeferredNewBmCacheKey();
                        Intrinsics.checkNotNull(deferredNewBmCacheKey);
                        this.L$0 = SpillingKt.nullOutSpilledVariable(coroutineScope);
                        this.L$1 = addAction2;
                        this.L$2 = SpillingKt.nullOutSpilledVariable(newLayerBitmap);
                        this.L$3 = addAction2;
                        this.I$0 = 0;
                        this.label = 2;
                        obj = deferredNewBmCacheKey.await(this);
                        if (obj != coroutine_suspended) {
                            addAction3 = addAction2;
                            addAction4 = addAction3;
                            addAction4.setNewBmCacheKey((String) obj);
                            addAction3.setCachedFile(true);
                        }
                    }
                    return Unit.INSTANCE;
                }
                return coroutine_suspended;
            }
            if (i == 1) {
                addAction = (AddAction) this.L$2;
                ResultKt.throwOnFailure(obj);
            } else {
                if (i != 2) {
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                }
                addAction4 = (AddAction) this.L$3;
                addAction3 = (AddAction) this.L$1;
                ResultKt.throwOnFailure(obj);
            }
            addAction4.setNewBmCacheKey((String) obj);
            addAction3.setCachedFile(true);
            return Unit.INSTANCE;
            addAction.setOldBmCacheKey((String) obj);
            newLayerBitmap = AddAction.this.getNewLayerBitmap();
            if (newLayerBitmap != null) {
                addAction2 = AddAction.this;
                addAction2.setDeferredNewBmCacheKey(BuildersKt__Builders_commonKt.async$default(coroutineScope, Dispatchers.getDefault(), null, new AddAction$cacheToFile$1$2$1(addAction2, newLayerBitmap, null), 2, null));
                Deferred<String> deferredNewBmCacheKey2 = addAction2.getDeferredNewBmCacheKey();
                Intrinsics.checkNotNull(deferredNewBmCacheKey2);
                this.L$0 = SpillingKt.nullOutSpilledVariable(coroutineScope);
                this.L$1 = addAction2;
                this.L$2 = SpillingKt.nullOutSpilledVariable(newLayerBitmap);
                this.L$3 = addAction2;
                this.I$0 = 0;
                this.label = 2;
                obj = deferredNewBmCacheKey2.await(this);
                if (obj != coroutine_suspended) {
                    addAction3 = addAction2;
                    addAction4 = addAction3;
                    addAction4.setNewBmCacheKey((String) obj);
                    addAction3.setCachedFile(true);
                }
                return coroutine_suspended;
            }
            return Unit.INSTANCE;
        }
    }

    @Override // com.miui.doodle.feature.action.BaseAction, com.miui.doodle.feature.action.IAction
    public void restoreFromFile() {
        if (getEnableCacheToFile() && this.isCachedFile) {
            BuildersKt__Builders_commonKt.launch$default(GlobalScope.INSTANCE, Dispatchers.getIO(), null, new C01951(null), 2, null);
            BuildersKt__Builders_commonKt.launch$default(GlobalScope.INSTANCE, Dispatchers.getIO(), null, new AnonymousClass2(null), 2, null);
        }
    }

    /* JADX INFO: renamed from: com.miui.doodle.feature.action.AddAction$restoreFromFile$1, reason: invalid class name and case insensitive filesystem */
    /* JADX INFO: compiled from: AddAction.kt */
    @Metadata(d1 = {"\u0000\n\n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\u0010\u0000\u001a\u00020\u0001*\u00020\u0002H\n"}, d2 = {"<anonymous>", "", "Lkotlinx/coroutines/CoroutineScope;"}, k = 3, mv = {2, 2, 0}, xi = 48)
    @DebugMetadata(c = "com.miui.doodle.feature.action.AddAction$restoreFromFile$1", f = "AddAction.kt", i = {}, l = {}, m = "invokeSuspend", n = {}, s = {})
    static final class C01951 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
        private /* synthetic */ Object L$0;
        int label;

        C01951(Continuation<? super C01951> continuation) {
            super(2, continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
            C01951 c01951 = AddAction.this.new C01951(continuation);
            c01951.L$0 = obj;
            return c01951;
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(CoroutineScope coroutineScope, Continuation<? super Unit> continuation) {
            return ((C01951) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
        }

        /* JADX INFO: renamed from: com.miui.doodle.feature.action.AddAction$restoreFromFile$1$1, reason: invalid class name and collision with other inner class name */
        /* JADX INFO: compiled from: AddAction.kt */
        @Metadata(d1 = {"\u0000\n\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\u0010\u0000\u001a\u0004\u0018\u00010\u0001*\u00020\u0002H\n"}, d2 = {"<anonymous>", "Landroid/graphics/Bitmap;", "Lkotlinx/coroutines/CoroutineScope;"}, k = 3, mv = {2, 2, 0}, xi = 48)
        @DebugMetadata(c = "com.miui.doodle.feature.action.AddAction$restoreFromFile$1$1", f = "AddAction.kt", i = {}, l = {}, m = "invokeSuspend", n = {}, s = {})
        static final class C00351 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Bitmap>, Object> {
            int label;
            final /* synthetic */ AddAction this$0;

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            C00351(AddAction addAction, Continuation<? super C00351> continuation) {
                super(2, continuation);
                this.this$0 = addAction;
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
                return new C00351(this.this$0, continuation);
            }

            @Override // kotlin.jvm.functions.Function2
            public final Object invoke(CoroutineScope coroutineScope, Continuation<? super Bitmap> continuation) {
                return ((C00351) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
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

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            CoroutineScope coroutineScope = (CoroutineScope) this.L$0;
            IntrinsicsKt.getCOROUTINE_SUSPENDED();
            if (this.label == 0) {
                ResultKt.throwOnFailure(obj);
                AddAction.this.setDeferredOldBm(BuildersKt__Builders_commonKt.async$default(coroutineScope, Dispatchers.getIO(), null, new C00351(AddAction.this, null), 2, null));
                return Unit.INSTANCE;
            }
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
    }

    /* JADX INFO: renamed from: com.miui.doodle.feature.action.AddAction$restoreFromFile$2, reason: invalid class name */
    /* JADX INFO: compiled from: AddAction.kt */
    @Metadata(d1 = {"\u0000\n\n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\u0010\u0000\u001a\u00020\u0001*\u00020\u0002H\n"}, d2 = {"<anonymous>", "", "Lkotlinx/coroutines/CoroutineScope;"}, k = 3, mv = {2, 2, 0}, xi = 48)
    @DebugMetadata(c = "com.miui.doodle.feature.action.AddAction$restoreFromFile$2", f = "AddAction.kt", i = {}, l = {}, m = "invokeSuspend", n = {}, s = {})
    static final class AnonymousClass2 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
        private /* synthetic */ Object L$0;
        int label;

        AnonymousClass2(Continuation<? super AnonymousClass2> continuation) {
            super(2, continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
            AnonymousClass2 anonymousClass2 = AddAction.this.new AnonymousClass2(continuation);
            anonymousClass2.L$0 = obj;
            return anonymousClass2;
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(CoroutineScope coroutineScope, Continuation<? super Unit> continuation) {
            return ((AnonymousClass2) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
        }

        /* JADX INFO: renamed from: com.miui.doodle.feature.action.AddAction$restoreFromFile$2$1, reason: invalid class name */
        /* JADX INFO: compiled from: AddAction.kt */
        @Metadata(d1 = {"\u0000\n\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\u0010\u0000\u001a\u0004\u0018\u00010\u0001*\u00020\u0002H\n"}, d2 = {"<anonymous>", "Landroid/graphics/Bitmap;", "Lkotlinx/coroutines/CoroutineScope;"}, k = 3, mv = {2, 2, 0}, xi = 48)
        @DebugMetadata(c = "com.miui.doodle.feature.action.AddAction$restoreFromFile$2$1", f = "AddAction.kt", i = {}, l = {}, m = "invokeSuspend", n = {}, s = {})
        static final class AnonymousClass1 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Bitmap>, Object> {
            int label;
            final /* synthetic */ AddAction this$0;

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            AnonymousClass1(AddAction addAction, Continuation<? super AnonymousClass1> continuation) {
                super(2, continuation);
                this.this$0 = addAction;
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

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            CoroutineScope coroutineScope = (CoroutineScope) this.L$0;
            IntrinsicsKt.getCOROUTINE_SUSPENDED();
            if (this.label == 0) {
                ResultKt.throwOnFailure(obj);
                AddAction.this.setDeferredNewBm(BuildersKt__Builders_commonKt.async$default(coroutineScope, Dispatchers.getIO(), null, new AnonymousClass1(AddAction.this, null), 2, null));
                return Unit.INSTANCE;
            }
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
    }

    @Override // com.miui.doodle.feature.action.BaseAction, com.miui.doodle.feature.action.IAction
    public void releaseJobs() {
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

    @Override // com.miui.doodle.feature.action.BaseAction, com.miui.doodle.feature.action.IAction
    public void destroy() {
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
    }
}
