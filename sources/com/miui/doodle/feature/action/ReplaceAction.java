package com.miui.doodle.feature.action;

import android.graphics.Bitmap;
import android.text.TextUtils;
import com.market.sdk.reflect.ReflectUtilsForMiui;
import com.miui.common.stat.NotesTextStat;
import com.miui.doodle.base.core.IDoodleItem;
import com.miui.doodle.document.LayerBitmap;
import com.miui.doodle.feature.draw.DoodleView;
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

/* JADX INFO: compiled from: ReplaceAction.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000R\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0011\n\u0002\u0010\u000e\n\u0002\b\n\n\u0002\u0018\u0002\n\u0002\b\u000e\n\u0002\u0010\u000b\n\u0002\b\u0004\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\b\u0018\u00002\u00020\u0001B;\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0003\u0012\u0006\u0010\u0005\u001a\u00020\u0006\u0012\u0006\u0010\u0007\u001a\u00020\b\u0012\b\u0010\t\u001a\u0004\u0018\u00010\n\u0012\b\u0010\u000b\u001a\u0004\u0018\u00010\n¢\u0006\u0004\b\f\u0010\rJ\u0010\u0010<\u001a\u00020=2\u0006\u0010>\u001a\u00020?H\u0016J\u0010\u0010@\u001a\u00020=2\u0006\u0010>\u001a\u00020?H\u0016J\u0010\u0010A\u001a\u00020=2\u0006\u0010>\u001a\u00020?H\u0002J\u0010\u0010B\u001a\u00020=2\u0006\u0010>\u001a\u00020?H\u0002J\b\u0010C\u001a\u00020=H\u0016J\b\u0010D\u001a\u00020=H\u0016J\b\u0010E\u001a\u00020=H\u0016J\b\u0010F\u001a\u00020=H\u0016R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u000e\u0010\u000fR\u0011\u0010\u0004\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0010\u0010\u000fR\u0011\u0010\u0005\u001a\u00020\u0006¢\u0006\b\n\u0000\u001a\u0004\b\u0011\u0010\u0012R\u0011\u0010\u0007\u001a\u00020\b¢\u0006\b\n\u0000\u001a\u0004\b\u0013\u0010\u0014R\u001c\u0010\t\u001a\u0004\u0018\u00010\nX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0015\u0010\u0016\"\u0004\b\u0017\u0010\u0018R\u001c\u0010\u000b\u001a\u0004\u0018\u00010\nX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0019\u0010\u0016\"\u0004\b\u001a\u0010\u0018R\u0014\u0010\u001b\u001a\u00020\u001cX\u0086D¢\u0006\b\n\u0000\u001a\u0004\b\u001d\u0010\u001eR\u001a\u0010\u001f\u001a\u00020\u001cX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b \u0010\u001e\"\u0004\b!\u0010\"R\u001a\u0010#\u001a\u00020\u001cX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b$\u0010\u001e\"\u0004\b%\u0010\"R\"\u0010&\u001a\n\u0012\u0004\u0012\u00020\u001c\u0018\u00010'X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b(\u0010)\"\u0004\b*\u0010+R\"\u0010,\u001a\n\u0012\u0004\u0012\u00020\u001c\u0018\u00010'X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b-\u0010)\"\u0004\b.\u0010+R$\u0010/\u001a\f\u0012\u0006\u0012\u0004\u0018\u00010\n\u0018\u00010'X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b0\u0010)\"\u0004\b1\u0010+R$\u00102\u001a\f\u0012\u0006\u0012\u0004\u0018\u00010\n\u0018\u00010'X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b3\u0010)\"\u0004\b4\u0010+R\u001a\u00105\u001a\u000206X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b5\u00107\"\u0004\b8\u00109R\u000e\u0010:\u001a\u00020;X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006G"}, d2 = {"Lcom/miui/doodle/feature/action/ReplaceAction;", "Lcom/miui/doodle/feature/action/BaseAction;", "oldItem", "Lcom/miui/doodle/base/core/IDoodleItem;", "newItem", "itemIndexInLayerBitmap", "", "layerBitmap", "Lcom/miui/doodle/document/LayerBitmap;", "oldLayerBitmap", "Landroid/graphics/Bitmap;", "newLayerBitmap", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "(Lcom/miui/doodle/base/core/IDoodleItem;Lcom/miui/doodle/base/core/IDoodleItem;ILcom/miui/doodle/document/LayerBitmap;Landroid/graphics/Bitmap;Landroid/graphics/Bitmap;)V", "getOldItem", "()Lcom/miui/doodle/base/core/IDoodleItem;", "getNewItem", "getItemIndexInLayerBitmap", "()I", "getLayerBitmap", "()Lcom/miui/doodle/document/LayerBitmap;", "getOldLayerBitmap", "()Landroid/graphics/Bitmap;", "setOldLayerBitmap", "(Landroid/graphics/Bitmap;)V", "getNewLayerBitmap", "setNewLayerBitmap", "TAG", "", "getTAG", "()Ljava/lang/String;", "oldBmCacheKey", "getOldBmCacheKey", "setOldBmCacheKey", "(Ljava/lang/String;)V", "newBmCacheKey", "getNewBmCacheKey", "setNewBmCacheKey", "deferredOldBmCacheKey", "Lkotlinx/coroutines/Deferred;", "getDeferredOldBmCacheKey", "()Lkotlinx/coroutines/Deferred;", "setDeferredOldBmCacheKey", "(Lkotlinx/coroutines/Deferred;)V", "deferredNewBmCacheKey", "getDeferredNewBmCacheKey", "setDeferredNewBmCacheKey", "deferredOldBm", "getDeferredOldBm", "setDeferredOldBm", "deferredNewBm", "getDeferredNewBm", "setDeferredNewBm", "isCachedFile", "", "()Z", "setCachedFile", "(Z)V", "lock", "", NotesTextStat.UNDO, "", "doodleView", "Lcom/miui/doodle/feature/draw/DoodleView;", NotesTextStat.REDO, "restoreOldLayerBm", "restoreNewLayerBm", "cacheToFile", "restoreFromFile", "releaseJobs", "destroy", "libDoodle_release"}, k = 1, mv = {2, 2, 0}, xi = 48)
public final class ReplaceAction extends BaseAction {
    private final String TAG;
    private Deferred<Bitmap> deferredNewBm;
    private Deferred<String> deferredNewBmCacheKey;
    private Deferred<Bitmap> deferredOldBm;
    private Deferred<String> deferredOldBmCacheKey;
    private boolean isCachedFile;
    private final int itemIndexInLayerBitmap;
    private final LayerBitmap layerBitmap;
    private final Object lock;
    private String newBmCacheKey;
    private final IDoodleItem newItem;
    private Bitmap newLayerBitmap;
    private String oldBmCacheKey;
    private final IDoodleItem oldItem;
    private Bitmap oldLayerBitmap;

    public final IDoodleItem getOldItem() {
        return this.oldItem;
    }

    public final IDoodleItem getNewItem() {
        return this.newItem;
    }

    public final int getItemIndexInLayerBitmap() {
        return this.itemIndexInLayerBitmap;
    }

    public final LayerBitmap getLayerBitmap() {
        return this.layerBitmap;
    }

    public final Bitmap getOldLayerBitmap() {
        return this.oldLayerBitmap;
    }

    public final void setOldLayerBitmap(Bitmap bitmap) {
        this.oldLayerBitmap = bitmap;
    }

    public ReplaceAction(IDoodleItem oldItem, IDoodleItem newItem, int i, LayerBitmap layerBitmap, Bitmap bitmap, Bitmap bitmap2) {
        Intrinsics.checkNotNullParameter(oldItem, "oldItem");
        Intrinsics.checkNotNullParameter(newItem, "newItem");
        Intrinsics.checkNotNullParameter(layerBitmap, "layerBitmap");
        this.oldItem = oldItem;
        this.newItem = newItem;
        this.itemIndexInLayerBitmap = i;
        this.layerBitmap = layerBitmap;
        this.oldLayerBitmap = bitmap;
        this.newLayerBitmap = bitmap2;
        this.TAG = "ReplaceAction";
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

    @Override // com.miui.doodle.feature.action.IAction
    public void undo(DoodleView doodleView) {
        Intrinsics.checkNotNullParameter(doodleView, "doodleView");
        this.layerBitmap.getItems().remove(this.newItem);
        restoreOldLayerBm(doodleView);
    }

    @Override // com.miui.doodle.feature.action.IAction
    public void redo(DoodleView doodleView) {
        Intrinsics.checkNotNullParameter(doodleView, "doodleView");
        this.layerBitmap.getItems().add(this.newItem);
        restoreNewLayerBm(doodleView);
    }

    /* JADX INFO: renamed from: com.miui.doodle.feature.action.ReplaceAction$restoreOldLayerBm$1, reason: invalid class name and case insensitive filesystem */
    /* JADX INFO: compiled from: ReplaceAction.kt */
    @Metadata(d1 = {"\u0000\n\n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\u0010\u0000\u001a\u00020\u0001*\u00020\u0002H\n"}, d2 = {"<anonymous>", "", "Lkotlinx/coroutines/CoroutineScope;"}, k = 3, mv = {2, 2, 0}, xi = 48)
    @DebugMetadata(c = "com.miui.doodle.feature.action.ReplaceAction$restoreOldLayerBm$1", f = "ReplaceAction.kt", i = {}, l = {80, 91}, m = "invokeSuspend", n = {}, s = {})
    static final class C02091 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
        final /* synthetic */ DoodleView $doodleView;
        int label;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        C02091(DoodleView doodleView, Continuation<? super C02091> continuation) {
            super(2, continuation);
            this.$doodleView = doodleView;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
            return ReplaceAction.this.new C02091(this.$doodleView, continuation);
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(CoroutineScope coroutineScope, Continuation<? super Unit> continuation) {
            return ((C02091) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
        }

        /* JADX WARN: Code duplicated, block: B:40:0x0070 A[Catch: Exception -> 0x0023, TRY_LEAVE, TryCatch #2 {Exception -> 0x0023, blocks: (B:12:0x001f, B:38:0x006c, B:40:0x0070, B:33:0x0058, B:35:0x0060), top: B:56:0x0009 }] */
        /* JADX WARN: Code restructure failed: missing block: B:47:0x00c0, code lost:
        
            if (r6 == r0) goto L48;
         */
        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        public final java.lang.Object invokeSuspend(java.lang.Object r6) {
            /*
                Method dump skipped, instruction units count: 243
                To view this dump change 'Code comments level' option to 'DEBUG'
            */
            throw new UnsupportedOperationException("Method not decompiled: com.miui.doodle.feature.action.ReplaceAction.C02091.invokeSuspend(java.lang.Object):java.lang.Object");
        }

        /* JADX INFO: renamed from: com.miui.doodle.feature.action.ReplaceAction$restoreOldLayerBm$1$3, reason: invalid class name */
        /* JADX INFO: compiled from: ReplaceAction.kt */
        @Metadata(d1 = {"\u0000\n\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\u0010\u0000\u001a\u0004\u0018\u00010\u0001*\u00020\u0002H\n"}, d2 = {"<anonymous>", "Landroid/graphics/Bitmap;", "Lkotlinx/coroutines/CoroutineScope;"}, k = 3, mv = {2, 2, 0}, xi = 48)
        @DebugMetadata(c = "com.miui.doodle.feature.action.ReplaceAction$restoreOldLayerBm$1$3", f = "ReplaceAction.kt", i = {}, l = {}, m = "invokeSuspend", n = {}, s = {})
        static final class AnonymousClass3 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Bitmap>, Object> {
            int label;
            final /* synthetic */ ReplaceAction this$0;

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            AnonymousClass3(ReplaceAction replaceAction, Continuation<? super AnonymousClass3> continuation) {
                super(2, continuation);
                this.this$0 = replaceAction;
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

    private final void restoreOldLayerBm(DoodleView doodleView) {
        BuildersKt__Builders_commonKt.launch$default(GlobalScope.INSTANCE, Dispatchers.getMain(), null, new C02091(doodleView, null), 2, null);
    }

    /* JADX INFO: renamed from: com.miui.doodle.feature.action.ReplaceAction$restoreNewLayerBm$1, reason: invalid class name and case insensitive filesystem */
    /* JADX INFO: compiled from: ReplaceAction.kt */
    @Metadata(d1 = {"\u0000\n\n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\u0010\u0000\u001a\u00020\u0001*\u00020\u0002H\n"}, d2 = {"<anonymous>", "", "Lkotlinx/coroutines/CoroutineScope;"}, k = 3, mv = {2, 2, 0}, xi = 48)
    @DebugMetadata(c = "com.miui.doodle.feature.action.ReplaceAction$restoreNewLayerBm$1", f = "ReplaceAction.kt", i = {}, l = {118, 129}, m = "invokeSuspend", n = {}, s = {})
    static final class C02081 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
        final /* synthetic */ DoodleView $doodleView;
        int label;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        C02081(DoodleView doodleView, Continuation<? super C02081> continuation) {
            super(2, continuation);
            this.$doodleView = doodleView;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
            return ReplaceAction.this.new C02081(this.$doodleView, continuation);
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(CoroutineScope coroutineScope, Continuation<? super Unit> continuation) {
            return ((C02081) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
        }

        /* JADX WARN: Code duplicated, block: B:40:0x0070 A[Catch: Exception -> 0x0023, TRY_LEAVE, TryCatch #2 {Exception -> 0x0023, blocks: (B:12:0x001f, B:38:0x006c, B:40:0x0070, B:33:0x0058, B:35:0x0060), top: B:56:0x0009 }] */
        /* JADX WARN: Code restructure failed: missing block: B:47:0x00c0, code lost:
        
            if (r6 == r0) goto L48;
         */
        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        public final java.lang.Object invokeSuspend(java.lang.Object r6) {
            /*
                Method dump skipped, instruction units count: 243
                To view this dump change 'Code comments level' option to 'DEBUG'
            */
            throw new UnsupportedOperationException("Method not decompiled: com.miui.doodle.feature.action.ReplaceAction.C02081.invokeSuspend(java.lang.Object):java.lang.Object");
        }

        /* JADX INFO: renamed from: com.miui.doodle.feature.action.ReplaceAction$restoreNewLayerBm$1$3, reason: invalid class name */
        /* JADX INFO: compiled from: ReplaceAction.kt */
        @Metadata(d1 = {"\u0000\n\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\u0010\u0000\u001a\u0004\u0018\u00010\u0001*\u00020\u0002H\n"}, d2 = {"<anonymous>", "Landroid/graphics/Bitmap;", "Lkotlinx/coroutines/CoroutineScope;"}, k = 3, mv = {2, 2, 0}, xi = 48)
        @DebugMetadata(c = "com.miui.doodle.feature.action.ReplaceAction$restoreNewLayerBm$1$3", f = "ReplaceAction.kt", i = {}, l = {}, m = "invokeSuspend", n = {}, s = {})
        static final class AnonymousClass3 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Bitmap>, Object> {
            int label;
            final /* synthetic */ ReplaceAction this$0;

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            AnonymousClass3(ReplaceAction replaceAction, Continuation<? super AnonymousClass3> continuation) {
                super(2, continuation);
                this.this$0 = replaceAction;
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

    private final void restoreNewLayerBm(DoodleView doodleView) {
        BuildersKt__Builders_commonKt.launch$default(GlobalScope.INSTANCE, Dispatchers.getMain(), null, new C02081(doodleView, null), 2, null);
    }

    @Override // com.miui.doodle.feature.action.BaseAction, com.miui.doodle.feature.action.IAction
    public void cacheToFile() {
        if (!getEnableCacheToFile() || this.isCachedFile) {
            return;
        }
        BuildersKt__Builders_commonKt.launch$default(GlobalScope.INSTANCE, Dispatchers.getIO(), null, new AnonymousClass1(null), 2, null);
        BuildersKt__Builders_commonKt.launch$default(GlobalScope.INSTANCE, Dispatchers.getIO(), null, new AnonymousClass2(null), 2, null);
    }

    /* JADX INFO: renamed from: com.miui.doodle.feature.action.ReplaceAction$cacheToFile$1, reason: invalid class name */
    /* JADX INFO: compiled from: ReplaceAction.kt */
    @Metadata(d1 = {"\u0000\n\n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\u0010\u0000\u001a\u00020\u0001*\u00020\u0002H\n"}, d2 = {"<anonymous>", "", "Lkotlinx/coroutines/CoroutineScope;"}, k = 3, mv = {2, 2, 0}, xi = 48)
    @DebugMetadata(c = "com.miui.doodle.feature.action.ReplaceAction$cacheToFile$1", f = "ReplaceAction.kt", i = {0, 0, 0}, l = {R2.array.ai_save_tag_download_gradient_border_colors}, m = "invokeSuspend", n = {"$this$launch", "it", "$i$a$-let-ReplaceAction$cacheToFile$1$1"}, s = {"L$0", "L$1", "I$0"})
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
            AnonymousClass1 anonymousClass1 = ReplaceAction.this.new AnonymousClass1(continuation);
            anonymousClass1.L$0 = obj;
            return anonymousClass1;
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(CoroutineScope coroutineScope, Continuation<? super Unit> continuation) {
            return ((AnonymousClass1) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            ReplaceAction replaceAction;
            CoroutineScope coroutineScope = (CoroutineScope) this.L$0;
            Object coroutine_suspended = IntrinsicsKt.getCOROUTINE_SUSPENDED();
            int i = this.label;
            if (i == 0) {
                ResultKt.throwOnFailure(obj);
                Bitmap oldLayerBitmap = ReplaceAction.this.getOldLayerBitmap();
                if (oldLayerBitmap != null) {
                    ReplaceAction replaceAction2 = ReplaceAction.this;
                    replaceAction2.setDeferredOldBmCacheKey(BuildersKt__Builders_commonKt.async$default(coroutineScope, Dispatchers.getIO(), null, new ReplaceAction$cacheToFile$1$1$1(oldLayerBitmap, replaceAction2, null), 2, null));
                    Deferred<String> deferredOldBmCacheKey = replaceAction2.getDeferredOldBmCacheKey();
                    Intrinsics.checkNotNull(deferredOldBmCacheKey);
                    this.L$0 = SpillingKt.nullOutSpilledVariable(coroutineScope);
                    this.L$1 = SpillingKt.nullOutSpilledVariable(oldLayerBitmap);
                    this.L$2 = replaceAction2;
                    this.I$0 = 0;
                    this.label = 1;
                    obj = deferredOldBmCacheKey.await(this);
                    if (obj == coroutine_suspended) {
                        return coroutine_suspended;
                    }
                    replaceAction = replaceAction2;
                }
                return Unit.INSTANCE;
            }
            if (i != 1) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            replaceAction = (ReplaceAction) this.L$2;
            ResultKt.throwOnFailure(obj);
            replaceAction.setOldBmCacheKey((String) obj);
            return Unit.INSTANCE;
        }
    }

    /* JADX INFO: renamed from: com.miui.doodle.feature.action.ReplaceAction$cacheToFile$2, reason: invalid class name */
    /* JADX INFO: compiled from: ReplaceAction.kt */
    @Metadata(d1 = {"\u0000\n\n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\u0010\u0000\u001a\u00020\u0001*\u00020\u0002H\n"}, d2 = {"<anonymous>", "", "Lkotlinx/coroutines/CoroutineScope;"}, k = 3, mv = {2, 2, 0}, xi = 48)
    @DebugMetadata(c = "com.miui.doodle.feature.action.ReplaceAction$cacheToFile$2", f = "ReplaceAction.kt", i = {0, 0, 0}, l = {R2.array.ai_tag_gradient_border_colors}, m = "invokeSuspend", n = {"$this$launch", "it", "$i$a$-let-ReplaceAction$cacheToFile$2$1"}, s = {"L$0", "L$2", "I$0"})
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
            AnonymousClass2 anonymousClass2 = ReplaceAction.this.new AnonymousClass2(continuation);
            anonymousClass2.L$0 = obj;
            return anonymousClass2;
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(CoroutineScope coroutineScope, Continuation<? super Unit> continuation) {
            return ((AnonymousClass2) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            ReplaceAction replaceAction;
            ReplaceAction replaceAction2;
            CoroutineScope coroutineScope = (CoroutineScope) this.L$0;
            Object coroutine_suspended = IntrinsicsKt.getCOROUTINE_SUSPENDED();
            int i = this.label;
            if (i == 0) {
                ResultKt.throwOnFailure(obj);
                Bitmap newLayerBitmap = ReplaceAction.this.getNewLayerBitmap();
                if (newLayerBitmap != null) {
                    ReplaceAction replaceAction3 = ReplaceAction.this;
                    replaceAction3.setDeferredNewBmCacheKey(BuildersKt__Builders_commonKt.async$default(coroutineScope, Dispatchers.getIO(), null, new ReplaceAction$cacheToFile$2$1$1(newLayerBitmap, replaceAction3, null), 2, null));
                    Deferred<String> deferredNewBmCacheKey = replaceAction3.getDeferredNewBmCacheKey();
                    Intrinsics.checkNotNull(deferredNewBmCacheKey);
                    this.L$0 = SpillingKt.nullOutSpilledVariable(coroutineScope);
                    this.L$1 = replaceAction3;
                    this.L$2 = SpillingKt.nullOutSpilledVariable(newLayerBitmap);
                    this.L$3 = replaceAction3;
                    this.I$0 = 0;
                    this.label = 1;
                    obj = deferredNewBmCacheKey.await(this);
                    if (obj == coroutine_suspended) {
                        return coroutine_suspended;
                    }
                    replaceAction = replaceAction3;
                    replaceAction2 = replaceAction;
                }
                return Unit.INSTANCE;
            }
            if (i != 1) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            replaceAction2 = (ReplaceAction) this.L$3;
            replaceAction = (ReplaceAction) this.L$1;
            ResultKt.throwOnFailure(obj);
            replaceAction2.setNewBmCacheKey((String) obj);
            replaceAction.setCachedFile(true);
            return Unit.INSTANCE;
        }
    }

    @Override // com.miui.doodle.feature.action.BaseAction, com.miui.doodle.feature.action.IAction
    public void restoreFromFile() {
        if (getEnableCacheToFile() && this.isCachedFile) {
            BuildersKt__Builders_commonKt.launch$default(GlobalScope.INSTANCE, Dispatchers.getIO(), null, new C02061(null), 2, null);
            BuildersKt__Builders_commonKt.launch$default(GlobalScope.INSTANCE, Dispatchers.getIO(), null, new C02072(null), 2, null);
        }
    }

    /* JADX INFO: renamed from: com.miui.doodle.feature.action.ReplaceAction$restoreFromFile$1, reason: invalid class name and case insensitive filesystem */
    /* JADX INFO: compiled from: ReplaceAction.kt */
    @Metadata(d1 = {"\u0000\n\n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\u0010\u0000\u001a\u00020\u0001*\u00020\u0002H\n"}, d2 = {"<anonymous>", "", "Lkotlinx/coroutines/CoroutineScope;"}, k = 3, mv = {2, 2, 0}, xi = 48)
    @DebugMetadata(c = "com.miui.doodle.feature.action.ReplaceAction$restoreFromFile$1", f = "ReplaceAction.kt", i = {}, l = {}, m = "invokeSuspend", n = {}, s = {})
    static final class C02061 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
        private /* synthetic */ Object L$0;
        int label;

        C02061(Continuation<? super C02061> continuation) {
            super(2, continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
            C02061 c02061 = ReplaceAction.this.new C02061(continuation);
            c02061.L$0 = obj;
            return c02061;
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(CoroutineScope coroutineScope, Continuation<? super Unit> continuation) {
            return ((C02061) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
        }

        /* JADX INFO: renamed from: com.miui.doodle.feature.action.ReplaceAction$restoreFromFile$1$1, reason: invalid class name and collision with other inner class name */
        /* JADX INFO: compiled from: ReplaceAction.kt */
        @Metadata(d1 = {"\u0000\n\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\u0010\u0000\u001a\u0004\u0018\u00010\u0001*\u00020\u0002H\n"}, d2 = {"<anonymous>", "Landroid/graphics/Bitmap;", "Lkotlinx/coroutines/CoroutineScope;"}, k = 3, mv = {2, 2, 0}, xi = 48)
        @DebugMetadata(c = "com.miui.doodle.feature.action.ReplaceAction$restoreFromFile$1$1", f = "ReplaceAction.kt", i = {}, l = {}, m = "invokeSuspend", n = {}, s = {})
        static final class C00391 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Bitmap>, Object> {
            int label;
            final /* synthetic */ ReplaceAction this$0;

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            C00391(ReplaceAction replaceAction, Continuation<? super C00391> continuation) {
                super(2, continuation);
                this.this$0 = replaceAction;
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
                return new C00391(this.this$0, continuation);
            }

            @Override // kotlin.jvm.functions.Function2
            public final Object invoke(CoroutineScope coroutineScope, Continuation<? super Bitmap> continuation) {
                return ((C00391) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
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
                ReplaceAction.this.setDeferredOldBm(BuildersKt__Builders_commonKt.async$default(coroutineScope, Dispatchers.getIO(), null, new C00391(ReplaceAction.this, null), 2, null));
                return Unit.INSTANCE;
            }
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
    }

    /* JADX INFO: renamed from: com.miui.doodle.feature.action.ReplaceAction$restoreFromFile$2, reason: invalid class name and case insensitive filesystem */
    /* JADX INFO: compiled from: ReplaceAction.kt */
    @Metadata(d1 = {"\u0000\n\n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\u0010\u0000\u001a\u00020\u0001*\u00020\u0002H\n"}, d2 = {"<anonymous>", "", "Lkotlinx/coroutines/CoroutineScope;"}, k = 3, mv = {2, 2, 0}, xi = 48)
    @DebugMetadata(c = "com.miui.doodle.feature.action.ReplaceAction$restoreFromFile$2", f = "ReplaceAction.kt", i = {}, l = {}, m = "invokeSuspend", n = {}, s = {})
    static final class C02072 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
        private /* synthetic */ Object L$0;
        int label;

        C02072(Continuation<? super C02072> continuation) {
            super(2, continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
            C02072 c02072 = ReplaceAction.this.new C02072(continuation);
            c02072.L$0 = obj;
            return c02072;
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(CoroutineScope coroutineScope, Continuation<? super Unit> continuation) {
            return ((C02072) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
        }

        /* JADX INFO: renamed from: com.miui.doodle.feature.action.ReplaceAction$restoreFromFile$2$1, reason: invalid class name */
        /* JADX INFO: compiled from: ReplaceAction.kt */
        @Metadata(d1 = {"\u0000\n\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\u0010\u0000\u001a\u0004\u0018\u00010\u0001*\u00020\u0002H\n"}, d2 = {"<anonymous>", "Landroid/graphics/Bitmap;", "Lkotlinx/coroutines/CoroutineScope;"}, k = 3, mv = {2, 2, 0}, xi = 48)
        @DebugMetadata(c = "com.miui.doodle.feature.action.ReplaceAction$restoreFromFile$2$1", f = "ReplaceAction.kt", i = {}, l = {}, m = "invokeSuspend", n = {}, s = {})
        static final class AnonymousClass1 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Bitmap>, Object> {
            int label;
            final /* synthetic */ ReplaceAction this$0;

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            AnonymousClass1(ReplaceAction replaceAction, Continuation<? super AnonymousClass1> continuation) {
                super(2, continuation);
                this.this$0 = replaceAction;
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
                ReplaceAction.this.setDeferredNewBm(BuildersKt__Builders_commonKt.async$default(coroutineScope, Dispatchers.getIO(), null, new AnonymousClass1(ReplaceAction.this, null), 2, null));
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
