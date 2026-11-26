package androidx.appfunctions.internal;

import android.content.Context;
import androidx.appfunctions.metadata.AppFunctionMetadataKt;
import androidx.appsearch.app.GlobalSearchSession;
import androidx.appsearch.platformstorage.PlatformStorage;
import androidx.exifinterface.media.ExifInterface;
import ch.qos.logback.core.CoreConstants;
import com.google.common.util.concurrent.ListenableFuture;
import kotlin.Metadata;
import kotlin.ResultKt;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.IntrinsicsKt;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.jvm.internal.Intrinsics;
import kotlinx.coroutines.guava.ListenableFutureKt;

/* JADX INFO: compiled from: AppSearchUtils.kt */
/* JADX INFO: loaded from: classes.dex */
@Metadata(d1 = {"\u0000,\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010 \n\u0000\n\u0002\u0010\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\u001a\u0016\u0010\u0000\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u0003H\u0081@¢\u0006\u0002\u0010\u0004\u001a:\u0010\u0005\u001a\n\u0012\u0006\u0012\u0004\u0018\u0001H\u00070\u0006\"\b\b\u0000\u0010\u0007*\u00020\b*\u00020\t2\u0014\u0010\n\u001a\u0010\u0012\u0004\u0012\u00020\f\u0012\u0006\u0012\u0004\u0018\u0001H\u00070\u000bH\u0087@¢\u0006\u0002\u0010\r¨\u0006\u000e"}, d2 = {"createSearchSession", "Landroidx/appsearch/app/GlobalSearchSession;", CoreConstants.CONTEXT_SCOPE_VALUE, "Landroid/content/Context;", "(Landroid/content/Context;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "readAll", "", ExifInterface.GPS_DIRECTION_TRUE, "", "Landroidx/appsearch/app/SearchResults;", "transformToDocumentClassOrNull", "Lkotlin/Function1;", "Landroidx/appsearch/app/SearchResult;", "(Landroidx/appsearch/app/SearchResults;Lkotlin/jvm/functions/Function1;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", AppFunctionMetadataKt.APP_FUNCTION_NAMESPACE}, k = 2, mv = {2, 1, 0}, xi = 48)
public final class AppSearchUtilsKt {

    /* JADX INFO: renamed from: androidx.appfunctions.internal.AppSearchUtilsKt$createSearchSession$1, reason: invalid class name */
    /* JADX INFO: compiled from: AppSearchUtils.kt */
    @Metadata(k = 3, mv = {2, 1, 0}, xi = 48)
    @DebugMetadata(c = "androidx.appfunctions.internal.AppSearchUtilsKt", f = "AppSearchUtils.kt", i = {}, l = {42}, m = "createSearchSession", n = {}, s = {}, v = 1)
    static final class AnonymousClass1 extends ContinuationImpl {
        int label;
        /* synthetic */ Object result;

        AnonymousClass1(Continuation<? super AnonymousClass1> continuation) {
            super(continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            this.result = obj;
            this.label |= Integer.MIN_VALUE;
            return AppSearchUtilsKt.createSearchSession(null, this);
        }
    }

    /* JADX INFO: renamed from: androidx.appfunctions.internal.AppSearchUtilsKt$readAll$1, reason: invalid class name and case insensitive filesystem */
    /* JADX INFO: compiled from: AppSearchUtils.kt */
    @Metadata(k = 3, mv = {2, 1, 0}, xi = 48)
    @DebugMetadata(c = "androidx.appfunctions.internal.AppSearchUtilsKt", f = "AppSearchUtils.kt", i = {0, 0, 0, 1, 1, 1}, l = {60, 63}, m = "readAll", n = {"$this$readAll", "transformToDocumentClassOrNull", "$this$readAll_u24lambda_u240", "$this$readAll", "transformToDocumentClassOrNull", "$this$readAll_u24lambda_u240"}, s = {"L$0", "L$1", "L$3", "L$0", "L$1", "L$3"}, v = 1)
    static final class C01631<T> extends ContinuationImpl {
        Object L$0;
        Object L$1;
        Object L$2;
        Object L$3;
        int label;
        /* synthetic */ Object result;

        C01631(Continuation<? super C01631> continuation) {
            super(continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            this.result = obj;
            this.label |= Integer.MIN_VALUE;
            return AppSearchUtilsKt.readAll(null, null, this);
        }
    }

    /* JADX WARN: Code duplicated, block: B:7:0x0014  */
    public static final Object createSearchSession(Context context, Continuation<? super GlobalSearchSession> continuation) {
        AnonymousClass1 anonymousClass1;
        if (continuation instanceof AnonymousClass1) {
            anonymousClass1 = (AnonymousClass1) continuation;
            if ((anonymousClass1.label & Integer.MIN_VALUE) != 0) {
                anonymousClass1.label -= Integer.MIN_VALUE;
            } else {
                anonymousClass1 = new AnonymousClass1(continuation);
            }
        } else {
            anonymousClass1 = new AnonymousClass1(continuation);
        }
        Object objAwait = anonymousClass1.result;
        Object coroutine_suspended = IntrinsicsKt.getCOROUTINE_SUSPENDED();
        int i = anonymousClass1.label;
        if (i == 0) {
            ResultKt.throwOnFailure(objAwait);
            ListenableFuture<GlobalSearchSession> listenableFutureCreateGlobalSearchSessionAsync = PlatformStorage.createGlobalSearchSessionAsync(new PlatformStorage.GlobalSearchContext.Builder(context).setWorkerExecutor(new AppSearchUtilsKt$$ExternalSyntheticLambda0()).build());
            Intrinsics.checkNotNullExpressionValue(listenableFutureCreateGlobalSearchSessionAsync, "createGlobalSearchSessionAsync(...)");
            anonymousClass1.label = 1;
            objAwait = ListenableFutureKt.await(listenableFutureCreateGlobalSearchSessionAsync, anonymousClass1);
            if (objAwait == coroutine_suspended) {
                return coroutine_suspended;
            }
        } else {
            if (i != 1) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            ResultKt.throwOnFailure(objAwait);
        }
        Intrinsics.checkNotNullExpressionValue(objAwait, "await(...)");
        return objAwait;
    }

    /* JADX WARN: Code duplicated, block: B:23:0x0094  */
    /* JADX WARN: Code duplicated, block: B:26:0x00b0 A[LOOP:0: B:24:0x00aa->B:26:0x00b0, LOOP_END] */
    /* JADX WARN: Code duplicated, block: B:30:0x00ca A[LOOP:1: B:28:0x00c4->B:30:0x00ca, LOOP_END] */
    /* JADX WARN: Code duplicated, block: B:34:0x00ea  */
    /* JADX WARN: Code duplicated, block: B:7:0x0014  */
    /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:34:0x00ea -> B:35:0x00ed). Please report as a decompilation issue!!! */
    /*  JADX ERROR: JadxOverflowException in pass: RegionMakerVisitor
        jadx.core.utils.exceptions.JadxOverflowException: Regions stack size limit reached
        	at jadx.core.utils.ErrorsCounter.addError(ErrorsCounter.java:59)
        	at jadx.core.utils.ErrorsCounter.error(ErrorsCounter.java:31)
        	at jadx.core.dex.attributes.nodes.NotificationAttrNode.addError(NotificationAttrNode.java:19)
        */
    public static final <T> java.lang.Object readAll(androidx.appsearch.app.SearchResults r9, kotlin.jvm.functions.Function1<? super androidx.appsearch.app.SearchResult, ? extends T> r10, kotlin.coroutines.Continuation<? super java.util.List<? extends T>> r11) {
        /*
            Method dump skipped, instruction units count: 248
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.appfunctions.internal.AppSearchUtilsKt.readAll(androidx.appsearch.app.SearchResults, kotlin.jvm.functions.Function1, kotlin.coroutines.Continuation):java.lang.Object");
    }
}
