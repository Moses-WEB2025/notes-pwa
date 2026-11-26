package kotlinx.coroutines.channels;

import androidx.exifinterface.media.ExifInterface;
import defpackage.R2;
import java.util.concurrent.CancellationException;
import kotlin.Deprecated;
import kotlin.DeprecationLevel;
import kotlin.Metadata;
import kotlin.ReplaceWith;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlinx.coroutines.ExceptionsKt;
import kotlinx.coroutines.selects.SelectClause1;

/* JADX INFO: compiled from: Channels.common.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000@\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0010\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0010 \n\u0002\b\u0002\n\u0002\u0010\u0003\n\u0000\u001a$\u0010\u0002\u001a\u0004\u0018\u0001H\u0003\"\b\b\u0000\u0010\u0003*\u00020\u0004*\b\u0012\u0004\u0012\u0002H\u00030\u0005H\u0087@¢\u0006\u0002\u0010\u0006\u001a$\u0010\u0007\u001a\n\u0012\u0006\u0012\u0004\u0018\u0001H\u00030\b\"\b\b\u0000\u0010\u0003*\u00020\u0004*\b\u0012\u0004\u0012\u0002H\u00030\u0005H\u0007\u001aP\u0010\t\u001a\u0002H\n\"\u0004\b\u0000\u0010\u0003\"\u0004\b\u0001\u0010\n*\b\u0012\u0004\u0012\u0002H\u00030\u00052\u001d\u0010\u000b\u001a\u0019\u0012\n\u0012\b\u0012\u0004\u0012\u0002H\u00030\u0005\u0012\u0004\u0012\u0002H\n0\f¢\u0006\u0002\b\rH\u0086\b\u0082\u0002\n\n\b\b\u0001\u0012\u0002\u0010\u0001 \u0001¢\u0006\u0002\u0010\u000e\u001a2\u0010\u000f\u001a\u00020\u0010\"\u0004\b\u0000\u0010\u0003*\b\u0012\u0004\u0012\u0002H\u00030\u00052\u0012\u0010\u0011\u001a\u000e\u0012\u0004\u0012\u0002H\u0003\u0012\u0004\u0012\u00020\u00100\fH\u0086H¢\u0006\u0002\u0010\u0012\u001a$\u0010\u0013\u001a\b\u0012\u0004\u0012\u0002H\u00030\u0014\"\u0004\b\u0000\u0010\u0003*\b\u0012\u0004\u0012\u0002H\u00030\u0005H\u0086@¢\u0006\u0002\u0010\u0006\u001a\u001a\u0010\u0015\u001a\u00020\u0010*\u0006\u0012\u0002\b\u00030\u00052\b\u0010\u0016\u001a\u0004\u0018\u00010\u0017H\u0001\"\u000e\u0010\u0000\u001a\u00020\u0001X\u0080T¢\u0006\u0002\n\u0000¨\u0006\u0018"}, d2 = {"DEFAULT_CLOSE_MESSAGE", "", "receiveOrNull", ExifInterface.LONGITUDE_EAST, "", "Lkotlinx/coroutines/channels/ReceiveChannel;", "(Lkotlinx/coroutines/channels/ReceiveChannel;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "onReceiveOrNull", "Lkotlinx/coroutines/selects/SelectClause1;", "consume", "R", "block", "Lkotlin/Function1;", "Lkotlin/ExtensionFunctionType;", "(Lkotlinx/coroutines/channels/ReceiveChannel;Lkotlin/jvm/functions/Function1;)Ljava/lang/Object;", "consumeEach", "", "action", "(Lkotlinx/coroutines/channels/ReceiveChannel;Lkotlin/jvm/functions/Function1;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "toList", "", "cancelConsumed", "cause", "", "kotlinx-coroutines-core"}, k = 5, mv = {2, 1, 0}, xi = 48, xs = "kotlinx/coroutines/channels/ChannelsKt")
final /* synthetic */ class ChannelsKt__Channels_commonKt {

    /* JADX INFO: renamed from: kotlinx.coroutines.channels.ChannelsKt__Channels_commonKt$consumeEach$1, reason: invalid class name */
    /* JADX INFO: compiled from: Channels.common.kt */
    @Metadata(k = 3, mv = {2, 1, 0}, xi = 176)
    @DebugMetadata(c = "kotlinx.coroutines.channels.ChannelsKt__Channels_commonKt", f = "Channels.common.kt", i = {0, 0}, l = {R2.array.ai_select_btn_colors}, m = "consumeEach", n = {"action", "$this$consume$iv"}, s = {"L$0", "L$1"})
    static final class AnonymousClass1<E> extends ContinuationImpl {
        Object L$0;
        Object L$1;
        Object L$2;
        int label;
        /* synthetic */ Object result;

        AnonymousClass1(Continuation<? super AnonymousClass1> continuation) {
            super(continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            this.result = obj;
            this.label |= Integer.MIN_VALUE;
            return ChannelsKt__Channels_commonKt.consumeEach(null, null, this);
        }
    }

    /* JADX INFO: renamed from: kotlinx.coroutines.channels.ChannelsKt__Channels_commonKt$toList$1, reason: invalid class name and case insensitive filesystem */
    /* JADX INFO: compiled from: Channels.common.kt */
    @Metadata(k = 3, mv = {2, 1, 0}, xi = 48)
    @DebugMetadata(c = "kotlinx.coroutines.channels.ChannelsKt__Channels_commonKt", f = "Channels.common.kt", i = {0, 0}, l = {R2.array.default_card_stroke_gradient_colors_dark}, m = "toList", n = {"$this$toList_u24lambda_u242", "$this$consume$iv$iv"}, s = {"L$1", "L$2"})
    static final class C04071<E> extends ContinuationImpl {
        Object L$0;
        Object L$1;
        Object L$2;
        Object L$3;
        int label;
        /* synthetic */ Object result;

        C04071(Continuation<? super C04071> continuation) {
            super(continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            this.result = obj;
            this.label |= Integer.MIN_VALUE;
            return ChannelsKt.toList(null, this);
        }
    }

    @Deprecated(level = DeprecationLevel.HIDDEN, message = "Deprecated in the favour of 'receiveCatching'", replaceWith = @ReplaceWith(expression = "receiveCatching().getOrNull()", imports = {}))
    public static final /* synthetic */ Object receiveOrNull(ReceiveChannel receiveChannel, Continuation continuation) {
        Intrinsics.checkNotNull(receiveChannel, "null cannot be cast to non-null type kotlinx.coroutines.channels.ReceiveChannel<E of kotlinx.coroutines.channels.ChannelsKt__Channels_commonKt.receiveOrNull?>");
        return receiveChannel.receiveOrNull(continuation);
    }

    @Deprecated(level = DeprecationLevel.HIDDEN, message = "Deprecated in the favour of 'onReceiveCatching'")
    public static final /* synthetic */ SelectClause1 onReceiveOrNull(ReceiveChannel receiveChannel) {
        Intrinsics.checkNotNull(receiveChannel, "null cannot be cast to non-null type kotlinx.coroutines.channels.ReceiveChannel<E of kotlinx.coroutines.channels.ChannelsKt__Channels_commonKt.onReceiveOrNull?>");
        return receiveChannel.getOnReceiveOrNull();
    }

    public static final <E, R> R consume(ReceiveChannel<? extends E> receiveChannel, Function1<? super ReceiveChannel<? extends E>, ? extends R> function1) {
        try {
            R rInvoke = function1.invoke(receiveChannel);
            ChannelsKt.cancelConsumed(receiveChannel, null);
            return rInvoke;
        } catch (Throwable th) {
            try {
                throw th;
            } catch (Throwable th2) {
                ChannelsKt.cancelConsumed(receiveChannel, th);
                throw th2;
            }
        }
    }

    /* JADX WARN: Code duplicated, block: B:23:0x005a A[RETURN] */
    /* JADX WARN: Code duplicated, block: B:24:0x005b  */
    /* JADX WARN: Code duplicated, block: B:27:0x0066 A[Catch: all -> 0x0037, TryCatch #1 {all -> 0x0037, blocks: (B:12:0x0033, B:25:0x005e, B:27:0x0066, B:21:0x004c, B:28:0x006f), top: B:39:0x0033 }] */
    /* JADX WARN: Code duplicated, block: B:7:0x0014  */
    /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:24:0x005b -> B:25:0x005e). Please report as a decompilation issue!!! */
    /*  JADX ERROR: JadxOverflowException in pass: RegionMakerVisitor
        jadx.core.utils.exceptions.JadxOverflowException: Regions stack size limit reached
        	at jadx.core.utils.ErrorsCounter.addError(ErrorsCounter.java:59)
        	at jadx.core.utils.ErrorsCounter.error(ErrorsCounter.java:31)
        	at jadx.core.dex.attributes.nodes.NotificationAttrNode.addError(NotificationAttrNode.java:19)
        */
    public static final <E> java.lang.Object consumeEach(kotlinx.coroutines.channels.ReceiveChannel<? extends E> r6, kotlin.jvm.functions.Function1<? super E, kotlin.Unit> r7, kotlin.coroutines.Continuation<? super kotlin.Unit> r8) {
        /*
            boolean r0 = r8 instanceof kotlinx.coroutines.channels.ChannelsKt__Channels_commonKt.AnonymousClass1
            if (r0 == 0) goto L14
            r0 = r8
            kotlinx.coroutines.channels.ChannelsKt__Channels_commonKt$consumeEach$1 r0 = (kotlinx.coroutines.channels.ChannelsKt__Channels_commonKt.AnonymousClass1) r0
            int r1 = r0.label
            r2 = -2147483648(0xffffffff80000000, float:-0.0)
            r1 = r1 & r2
            if (r1 == 0) goto L14
            int r8 = r0.label
            int r8 = r8 - r2
            r0.label = r8
            goto L19
        L14:
            kotlinx.coroutines.channels.ChannelsKt__Channels_commonKt$consumeEach$1 r0 = new kotlinx.coroutines.channels.ChannelsKt__Channels_commonKt$consumeEach$1
            r0.<init>(r8)
        L19:
            java.lang.Object r8 = r0.result
            java.lang.Object r1 = kotlin.coroutines.intrinsics.IntrinsicsKt.getCOROUTINE_SUSPENDED()
            int r2 = r0.label
            r3 = 0
            r4 = 1
            if (r2 == 0) goto L41
            if (r2 != r4) goto L39
            java.lang.Object r6 = r0.L$2
            kotlinx.coroutines.channels.ChannelIterator r6 = (kotlinx.coroutines.channels.ChannelIterator) r6
            java.lang.Object r7 = r0.L$1
            kotlinx.coroutines.channels.ReceiveChannel r7 = (kotlinx.coroutines.channels.ReceiveChannel) r7
            java.lang.Object r2 = r0.L$0
            kotlin.jvm.functions.Function1 r2 = (kotlin.jvm.functions.Function1) r2
            kotlin.ResultKt.throwOnFailure(r8)     // Catch: java.lang.Throwable -> L37
            goto L5e
        L37:
            r6 = move-exception
            goto L7b
        L39:
            java.lang.IllegalStateException r6 = new java.lang.IllegalStateException
            java.lang.String r7 = "call to 'resume' before 'invoke' with coroutine"
            r6.<init>(r7)
            throw r6
        L41:
            kotlin.ResultKt.throwOnFailure(r8)
            kotlinx.coroutines.channels.ChannelIterator r8 = r6.iterator()     // Catch: java.lang.Throwable -> L77
            r5 = r7
            r7 = r6
            r6 = r8
            r8 = r5
        L4c:
            r0.L$0 = r8     // Catch: java.lang.Throwable -> L37
            r0.L$1 = r7     // Catch: java.lang.Throwable -> L37
            r0.L$2 = r6     // Catch: java.lang.Throwable -> L37
            r0.label = r4     // Catch: java.lang.Throwable -> L37
            java.lang.Object r2 = r6.hasNext(r0)     // Catch: java.lang.Throwable -> L37
            if (r2 != r1) goto L5b
            return r1
        L5b:
            r5 = r2
            r2 = r8
            r8 = r5
        L5e:
            java.lang.Boolean r8 = (java.lang.Boolean) r8     // Catch: java.lang.Throwable -> L37
            boolean r8 = r8.booleanValue()     // Catch: java.lang.Throwable -> L37
            if (r8 == 0) goto L6f
            java.lang.Object r8 = r6.next()     // Catch: java.lang.Throwable -> L37
            r2.invoke(r8)     // Catch: java.lang.Throwable -> L37
            r8 = r2
            goto L4c
        L6f:
            kotlin.Unit r6 = kotlin.Unit.INSTANCE     // Catch: java.lang.Throwable -> L37
            kotlinx.coroutines.channels.ChannelsKt.cancelConsumed(r7, r3)
            kotlin.Unit r6 = kotlin.Unit.INSTANCE
            return r6
        L77:
            r7 = move-exception
            r5 = r7
            r7 = r6
            r6 = r5
        L7b:
            throw r6     // Catch: java.lang.Throwable -> L7c
        L7c:
            r8 = move-exception
            kotlinx.coroutines.channels.ChannelsKt.cancelConsumed(r7, r6)
            throw r8
        */
        throw new UnsupportedOperationException("Method not decompiled: kotlinx.coroutines.channels.ChannelsKt__Channels_commonKt.consumeEach(kotlinx.coroutines.channels.ReceiveChannel, kotlin.jvm.functions.Function1, kotlin.coroutines.Continuation):java.lang.Object");
    }

    /* JADX WARN: Code duplicated, block: B:23:0x0065 A[RETURN] */
    /* JADX WARN: Code duplicated, block: B:24:0x0066  */
    /* JADX WARN: Code duplicated, block: B:27:0x0071 A[Catch: all -> 0x003b, TryCatch #3 {all -> 0x003b, blocks: (B:12:0x0037, B:25:0x0069, B:27:0x0071, B:28:0x007a), top: B:45:0x0037 }] */
    /* JADX WARN: Code duplicated, block: B:7:0x0014  */
    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r5v1, types: [java.lang.Object] */
    /* JADX WARN: Type inference failed for: r5v2, types: [java.util.List] */
    /* JADX WARN: Type inference failed for: r5v5 */
    /* JADX WARN: Type inference failed for: r5v6 */
    /* JADX WARN: Type inference failed for: r5v7 */
    /* JADX WARN: Type inference failed for: r5v8 */
    /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:24:0x0066 -> B:25:0x0069). Please report as a decompilation issue!!! */
    /*  JADX ERROR: JadxOverflowException in pass: RegionMakerVisitor
        jadx.core.utils.exceptions.JadxOverflowException: Regions stack size limit reached
        	at jadx.core.utils.ErrorsCounter.addError(ErrorsCounter.java:59)
        	at jadx.core.utils.ErrorsCounter.error(ErrorsCounter.java:31)
        	at jadx.core.dex.attributes.nodes.NotificationAttrNode.addError(NotificationAttrNode.java:19)
        */
    public static final <E> java.lang.Object toList(kotlinx.coroutines.channels.ReceiveChannel<? extends E> r8, kotlin.coroutines.Continuation<? super java.util.List<? extends E>> r9) {
        /*
            boolean r0 = r9 instanceof kotlinx.coroutines.channels.ChannelsKt__Channels_commonKt.C04071
            if (r0 == 0) goto L14
            r0 = r9
            kotlinx.coroutines.channels.ChannelsKt__Channels_commonKt$toList$1 r0 = (kotlinx.coroutines.channels.ChannelsKt__Channels_commonKt.C04071) r0
            int r1 = r0.label
            r2 = -2147483648(0xffffffff80000000, float:-0.0)
            r1 = r1 & r2
            if (r1 == 0) goto L14
            int r9 = r0.label
            int r9 = r9 - r2
            r0.label = r9
            goto L19
        L14:
            kotlinx.coroutines.channels.ChannelsKt__Channels_commonKt$toList$1 r0 = new kotlinx.coroutines.channels.ChannelsKt__Channels_commonKt$toList$1
            r0.<init>(r9)
        L19:
            java.lang.Object r9 = r0.result
            java.lang.Object r1 = kotlin.coroutines.intrinsics.IntrinsicsKt.getCOROUTINE_SUSPENDED()
            int r2 = r0.label
            r3 = 0
            r4 = 1
            if (r2 == 0) goto L46
            if (r2 != r4) goto L3e
            java.lang.Object r8 = r0.L$3
            kotlinx.coroutines.channels.ChannelIterator r8 = (kotlinx.coroutines.channels.ChannelIterator) r8
            java.lang.Object r2 = r0.L$2
            kotlinx.coroutines.channels.ReceiveChannel r2 = (kotlinx.coroutines.channels.ReceiveChannel) r2
            java.lang.Object r5 = r0.L$1
            java.util.List r5 = (java.util.List) r5
            java.lang.Object r6 = r0.L$0
            java.util.List r6 = (java.util.List) r6
            kotlin.ResultKt.throwOnFailure(r9)     // Catch: java.lang.Throwable -> L3b
            goto L69
        L3b:
            r8 = move-exception
            r9 = r2
            goto L8a
        L3e:
            java.lang.IllegalStateException r8 = new java.lang.IllegalStateException
            java.lang.String r9 = "call to 'resume' before 'invoke' with coroutine"
            r8.<init>(r9)
            throw r8
        L46:
            kotlin.ResultKt.throwOnFailure(r9)
            java.util.List r9 = kotlin.collections.CollectionsKt.createListBuilder()
            kotlinx.coroutines.channels.ChannelIterator r2 = r8.iterator()     // Catch: java.lang.Throwable -> L86
            r5 = r9
            r6 = r5
            r9 = r8
            r8 = r2
        L55:
            r0.L$0 = r6     // Catch: java.lang.Throwable -> L84
            r0.L$1 = r5     // Catch: java.lang.Throwable -> L84
            r0.L$2 = r9     // Catch: java.lang.Throwable -> L84
            r0.L$3 = r8     // Catch: java.lang.Throwable -> L84
            r0.label = r4     // Catch: java.lang.Throwable -> L84
            java.lang.Object r2 = r8.hasNext(r0)     // Catch: java.lang.Throwable -> L84
            if (r2 != r1) goto L66
            return r1
        L66:
            r7 = r2
            r2 = r9
            r9 = r7
        L69:
            java.lang.Boolean r9 = (java.lang.Boolean) r9     // Catch: java.lang.Throwable -> L3b
            boolean r9 = r9.booleanValue()     // Catch: java.lang.Throwable -> L3b
            if (r9 == 0) goto L7a
            java.lang.Object r9 = r8.next()     // Catch: java.lang.Throwable -> L3b
            r5.add(r9)     // Catch: java.lang.Throwable -> L3b
            r9 = r2
            goto L55
        L7a:
            kotlin.Unit r8 = kotlin.Unit.INSTANCE     // Catch: java.lang.Throwable -> L3b
            kotlinx.coroutines.channels.ChannelsKt.cancelConsumed(r2, r3)
            java.util.List r8 = kotlin.collections.CollectionsKt.build(r6)
            return r8
        L84:
            r8 = move-exception
            goto L8a
        L86:
            r9 = move-exception
            r7 = r9
            r9 = r8
            r8 = r7
        L8a:
            throw r8     // Catch: java.lang.Throwable -> L8b
        L8b:
            r0 = move-exception
            kotlinx.coroutines.channels.ChannelsKt.cancelConsumed(r9, r8)
            throw r0
        */
        throw new UnsupportedOperationException("Method not decompiled: kotlinx.coroutines.channels.ChannelsKt__Channels_commonKt.toList(kotlinx.coroutines.channels.ReceiveChannel, kotlin.coroutines.Continuation):java.lang.Object");
    }

    public static final void cancelConsumed(ReceiveChannel<?> receiveChannel, Throwable th) {
        CancellationException CancellationException = null;
        if (th != null) {
            CancellationException = th instanceof CancellationException ? (CancellationException) th : null;
            if (CancellationException == null) {
                CancellationException = ExceptionsKt.CancellationException("Channel was consumed, consumer had failed", th);
            }
        }
        receiveChannel.cancel(CancellationException);
    }

    private static final <E> Object consumeEach$$forInline(ReceiveChannel<? extends E> receiveChannel, Function1<? super E, Unit> function1, Continuation<? super Unit> continuation) {
        try {
            ChannelIterator<? extends E> it = receiveChannel.iterator();
            while (((Boolean) it.hasNext(null)).booleanValue()) {
                function1.invoke(it.next());
            }
            Unit unit = Unit.INSTANCE;
            ChannelsKt.cancelConsumed(receiveChannel, null);
            return Unit.INSTANCE;
        } catch (Throwable th) {
            try {
                throw th;
            } catch (Throwable th2) {
                ChannelsKt.cancelConsumed(receiveChannel, th);
                throw th2;
            }
        }
    }
}
