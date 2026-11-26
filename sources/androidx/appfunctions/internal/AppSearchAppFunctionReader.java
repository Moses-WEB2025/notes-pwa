package androidx.appfunctions.internal;

import android.content.Context;
import android.util.Log;
import androidx.appfunctions.AppFunctionFunctionNotFoundException;
import androidx.appfunctions.AppFunctionSearchSpec;
import androidx.appfunctions.metadata.AppFunctionComponentsMetadata;
import androidx.appfunctions.metadata.AppFunctionComponentsMetadataDocument;
import androidx.appfunctions.metadata.AppFunctionDeprecationMetadata;
import androidx.appfunctions.metadata.AppFunctionDeprecationMetadataDocument;
import androidx.appfunctions.metadata.AppFunctionMetadata;
import androidx.appfunctions.metadata.AppFunctionMetadataDocument;
import androidx.appfunctions.metadata.AppFunctionMetadataKt;
import androidx.appfunctions.metadata.AppFunctionPackageMetadata;
import androidx.appfunctions.metadata.AppFunctionParameterMetadata;
import androidx.appfunctions.metadata.AppFunctionParameterMetadataDocument;
import androidx.appfunctions.metadata.AppFunctionResponseMetadata;
import androidx.appfunctions.metadata.AppFunctionResponseMetadataDocument;
import androidx.appfunctions.metadata.AppFunctionRuntimeMetadata;
import androidx.appfunctions.metadata.AppFunctionSchemaMetadata;
import androidx.appfunctions.metadata.CompileTimeAppFunctionMetadata;
import androidx.appsearch.app.AppSearchBatchResult;
import androidx.appsearch.app.GenericDocument;
import androidx.appsearch.app.GetByDocumentIdRequest;
import androidx.appsearch.app.GlobalSearchSession;
import androidx.appsearch.app.SearchResult;
import androidx.appsearch.app.SearchResults;
import androidx.appsearch.app.SearchSpec;
import androidx.appsearch.exceptions.AppSearchException;
import androidx.appsearch.observer.DocumentChangeInfo;
import androidx.appsearch.observer.ObserverCallback;
import androidx.appsearch.observer.ObserverSpec;
import androidx.appsearch.observer.SchemaChangeInfo;
import androidx.exifinterface.media.ExifInterface;
import ch.qos.logback.core.CoreConstants;
import com.google.common.util.concurrent.ListenableFuture;
import com.market.sdk.reflect.Field;
import com.market.sdk.reflect.ReflectUtilsForMiui;
import defpackage.R2;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.CancellationException;
import kotlin.Metadata;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.collections.SetsKt;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.IntrinsicsKt;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Reflection;
import kotlin.time.Duration;
import kotlin.time.DurationKt;
import kotlin.time.DurationUnit;
import kotlinx.coroutines.Job;
import kotlinx.coroutines.channels.Channel;
import kotlinx.coroutines.channels.ChannelKt;
import kotlinx.coroutines.channels.ProducerScope;
import kotlinx.coroutines.channels.SendChannel;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowCollector;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.guava.ListenableFutureKt;
import miuix.navigation.SplitLayout;

/* JADX INFO: compiled from: AppSearchAppFunctionReader.kt */
/* JADX INFO: loaded from: classes.dex */
@Metadata(d1 = {"\u0000¬\u0001\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\"\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010$\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010%\n\u0002\b\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\b\u0001\u0018\u0000 B2\u00020\u0001:\u0002ABB\u0019\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\b\u0010\u0004\u001a\u0004\u0018\u00010\u0005¢\u0006\u0004\b\u0006\u0010\u0007J\u001c\u0010\b\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u000b0\n0\t2\u0006\u0010\f\u001a\u00020\rH\u0016J\u0016\u0010\u000e\u001a\u00020\u000f2\f\u0010\u0010\u001a\b\u0012\u0004\u0012\u00020\u00120\u0011H\u0002J$\u0010\u0013\u001a\b\u0012\u0004\u0012\u00020\u000b0\n2\u0006\u0010\u0014\u001a\u00020\u00152\u0006\u0010\f\u001a\u00020\rH\u0082@¢\u0006\u0002\u0010\u0016J2\u0010\u0017\u001a\u000e\u0012\u0004\u0012\u00020\u0012\u0012\u0004\u0012\u00020\u00190\u00182\u0006\u0010\u0014\u001a\u00020\u00152\u000e\u0010\u0010\u001a\n\u0012\u0004\u0012\u00020\u0012\u0018\u00010\u0011H\u0082@¢\u0006\u0002\u0010\u001aJ$\u0010\u001b\u001a\u00020\u001c2\u0006\u0010\u001d\u001a\u00020\u001e2\u0012\u0010\u001f\u001a\u000e\u0012\u0004\u0012\u00020\u0012\u0012\u0004\u0012\u00020\u00190 H\u0002J$\u0010!\u001a\u0004\u0018\u0001H\"\"\n\b\u0000\u0010\"\u0018\u0001*\u00020#2\u0006\u0010$\u001a\u00020%H\u0082\b¢\u0006\u0002\u0010&J&\u0010'\u001a\u0004\u0018\u00010(2\u0006\u0010\u001d\u001a\u00020\u001e2\u0012\u0010\u001f\u001a\u000e\u0012\u0004\u0012\u00020\u0012\u0012\u0004\u0012\u00020\u00190\u0018H\u0002J\u0018\u0010)\u001a\u00020*2\u0006\u0010+\u001a\u00020,2\u0006\u0010-\u001a\u00020.H\u0002J\u0012\u0010/\u001a\u0004\u0018\u0001002\u0006\u00101\u001a\u00020%H\u0002J \u00102\u001a\u0004\u0018\u00010(2\u0006\u00103\u001a\u00020\u00122\u0006\u00104\u001a\u00020\u0012H\u0096@¢\u0006\u0002\u00105J\u0018\u00106\u001a\u00020\u00122\u0006\u00104\u001a\u00020\u00122\u0006\u00103\u001a\u00020\u0012H\u0002J\"\u00107\u001a\n\u0012\u0004\u0012\u000208\u0018\u00010\n2\u0006\u00109\u001a\u00020,2\b\u0010:\u001a\u0004\u0018\u000100H\u0002J\u001c\u0010;\u001a\u0004\u0018\u00010<2\u0006\u00109\u001a\u00020,2\b\u0010:\u001a\u0004\u0018\u000100H\u0002J8\u0010=\u001a\u0004\u0018\u00010\u00192\u0006\u00104\u001a\u00020\u00122\u0006\u00109\u001a\u00020,2\b\u0010:\u001a\u0004\u0018\u0001002\u0012\u0010\u001f\u001a\u000e\u0012\u0004\u0012\u00020\u0012\u0012\u0004\u0012\u00020\u00190\u0018H\u0002J\u0012\u0010>\u001a\u0004\u0018\u00010?2\u0006\u00109\u001a\u00020,H\u0002J\u0010\u0010@\u001a\u00020*2\u0006\u00101\u001a\u00020,H\u0002R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000R\u0010\u0010\u0004\u001a\u0004\u0018\u00010\u0005X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006C"}, d2 = {"Landroidx/appfunctions/internal/AppSearchAppFunctionReader;", "Landroidx/appfunctions/internal/AppFunctionReader;", CoreConstants.CONTEXT_SCOPE_VALUE, "Landroid/content/Context;", "schemaAppFunctionInventory", "Landroidx/appfunctions/internal/SchemaAppFunctionInventory;", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "(Landroid/content/Context;Landroidx/appfunctions/internal/SchemaAppFunctionInventory;)V", "searchAppFunctions", "Lkotlinx/coroutines/flow/Flow;", "", "Landroidx/appfunctions/metadata/AppFunctionPackageMetadata;", "searchFunctionSpec", "Landroidx/appfunctions/AppFunctionSearchSpec;", "buildObserverSpec", "Landroidx/appsearch/observer/ObserverSpec;", "packageNames", "", "", "performSearch", "session", "Landroidx/appsearch/app/GlobalSearchSession;", "(Landroidx/appsearch/app/GlobalSearchSession;Landroidx/appfunctions/AppFunctionSearchSpec;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "searchTopLevelComponent", "", "Landroidx/appfunctions/metadata/AppFunctionComponentsMetadata;", "(Landroidx/appsearch/app/GlobalSearchSession;Ljava/util/Set;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "extractAppFunctionComponentsMetadataFromSearchResult", "", "searchResult", "Landroidx/appsearch/app/SearchResult;", "sharedTopLevelComponentsByPackage", "", "safeCastToDocumentClass", ExifInterface.GPS_DIRECTION_TRUE, "", "genericDocument", "Landroidx/appsearch/app/GenericDocument;", "(Landroidx/appsearch/app/GenericDocument;)Ljava/lang/Object;", "convertSearchResultToAppFunctionMetadata", "Landroidx/appfunctions/metadata/AppFunctionMetadata;", "computeEffectivelyEnabled", "", "staticMetadata", "Landroidx/appfunctions/metadata/AppFunctionMetadataDocument;", "runtimeMetadata", "Landroidx/appfunctions/metadata/AppFunctionRuntimeMetadata;", "buildSchemaMetadataFromGdForLegacyIndexer", "Landroidx/appfunctions/metadata/AppFunctionSchemaMetadata;", "document", "getAppFunctionMetadata", "functionId", "packageName", "(Ljava/lang/String;Ljava/lang/String;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "getAppFunctionId", "getAppFunctionParameterMetadata", "Landroidx/appfunctions/metadata/AppFunctionParameterMetadata;", "appFunctionMetadataDocument", "schemaMetadata", "getAppFunctionResponseMetadata", "Landroidx/appfunctions/metadata/AppFunctionResponseMetadata;", "getAppFunctionComponentsMetadata", "getAppFunctionDeprecationMetadata", "Landroidx/appfunctions/metadata/AppFunctionDeprecationMetadata;", "isAppFunctionMetadataDocumentFromDynamicIndexer", "AppSearchChannelObserver", "Companion", AppFunctionMetadataKt.APP_FUNCTION_NAMESPACE}, k = 1, mv = {2, 1, 0}, xi = 48)
public final class AppSearchAppFunctionReader implements AppFunctionReader {

    @Deprecated
    public static final String APP_FUNCTIONS_NAMESPACE = "app_functions";

    @Deprecated
    public static final String APP_FUNCTIONS_RUNTIME_DATABASE_NAME = "appfunctions-db";

    @Deprecated
    public static final String APP_FUNCTIONS_RUNTIME_NAMESPACE = "app_functions_runtime";

    @Deprecated
    public static final String APP_FUNCTIONS_STATIC_DATABASE_NAME = "apps-db";
    private static final Companion Companion = new Companion(null);
    private static final long OBSERVER_DEBOUNCE_MILLIS;
    private static final SearchSpec RUNTIME_SEARCH_SPEC;

    @Deprecated
    public static final String SYSTEM_PACKAGE_NAME = "android";
    private final Context context;
    private final SchemaAppFunctionInventory schemaAppFunctionInventory;

    /* JADX INFO: renamed from: androidx.appfunctions.internal.AppSearchAppFunctionReader$getAppFunctionMetadata$1, reason: invalid class name */
    /* JADX INFO: compiled from: AppSearchAppFunctionReader.kt */
    @Metadata(k = 3, mv = {2, 1, 0}, xi = 48)
    @DebugMetadata(c = "androidx.appfunctions.internal.AppSearchAppFunctionReader", f = "AppSearchAppFunctionReader.kt", i = {0, 0, 1, 1, 1, 1, 2, 2, 2, 2, 2, 3, 3, 3, 3, 3, 3, 3}, l = {R2.attr.actionSheetArrowStyle, R2.attr.activatedColor, R2.attr.alertDialogCenterButtons, 406}, m = "getAppFunctionMetadata", n = {"functionId", "packageName", "functionId", "packageName", "session", "documentId", "functionId", "packageName", "session", "documentId", "staticSearchResult", "functionId", "packageName", "staticMetadataDocument", "runtimeMetadataDocument", "schemaMetadata", "parameterMetadata", "responseMetadata"}, s = {"L$0", "L$1", "L$0", "L$1", "L$2", "L$3", "L$0", "L$1", "L$2", "L$3", "L$4", "L$0", "L$1", "L$2", "L$3", "L$4", "L$5", "L$6"}, v = 1)
    static final class AnonymousClass1 extends ContinuationImpl {
        Object L$0;
        Object L$1;
        Object L$10;
        Object L$2;
        Object L$3;
        Object L$4;
        Object L$5;
        Object L$6;
        Object L$7;
        Object L$8;
        Object L$9;
        int label;
        /* synthetic */ Object result;

        AnonymousClass1(Continuation<? super AnonymousClass1> continuation) {
            super(continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            this.result = obj;
            this.label |= Integer.MIN_VALUE;
            return AppSearchAppFunctionReader.this.getAppFunctionMetadata(null, null, this);
        }
    }

    /* JADX INFO: renamed from: androidx.appfunctions.internal.AppSearchAppFunctionReader$performSearch$1, reason: invalid class name and case insensitive filesystem */
    /* JADX INFO: compiled from: AppSearchAppFunctionReader.kt */
    @Metadata(k = 3, mv = {2, 1, 0}, xi = 48)
    @DebugMetadata(c = "androidx.appfunctions.internal.AppSearchAppFunctionReader", f = "AppSearchAppFunctionReader.kt", i = {0, 0, 0}, l = {R2.array.ai_tag_close_gradient_border_new_colors, R2.array.ai_widget_blend_colors}, m = "performSearch", n = {"session", "searchFunctionSpec", "staticMetadataSearchSpecWithJoin"}, s = {"L$0", "L$1", "L$2"}, v = 1)
    static final class C01601 extends ContinuationImpl {
        Object L$0;
        Object L$1;
        Object L$2;
        int label;
        /* synthetic */ Object result;

        C01601(Continuation<? super C01601> continuation) {
            super(continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            this.result = obj;
            this.label |= Integer.MIN_VALUE;
            return AppSearchAppFunctionReader.this.performSearch(null, null, this);
        }
    }

    /* JADX INFO: renamed from: androidx.appfunctions.internal.AppSearchAppFunctionReader$searchTopLevelComponent$1, reason: invalid class name and case insensitive filesystem */
    /* JADX INFO: compiled from: AppSearchAppFunctionReader.kt */
    @Metadata(k = 3, mv = {2, 1, 0}, xi = 48)
    @DebugMetadata(c = "androidx.appfunctions.internal.AppSearchAppFunctionReader", f = "AppSearchAppFunctionReader.kt", i = {0}, l = {R2.array.eras}, m = "searchTopLevelComponent", n = {"sharedTopLevelComponentsByPackage"}, s = {"L$0"}, v = 1)
    static final class C01621 extends ContinuationImpl {
        Object L$0;
        int label;
        /* synthetic */ Object result;

        C01621(Continuation<? super C01621> continuation) {
            super(continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            this.result = obj;
            this.label |= Integer.MIN_VALUE;
            return AppSearchAppFunctionReader.this.searchTopLevelComponent(null, null, this);
        }
    }

    public AppSearchAppFunctionReader(Context context, SchemaAppFunctionInventory schemaAppFunctionInventory) {
        Intrinsics.checkNotNullParameter(context, "context");
        this.context = context;
        this.schemaAppFunctionInventory = schemaAppFunctionInventory;
    }

    /* JADX INFO: renamed from: androidx.appfunctions.internal.AppSearchAppFunctionReader$searchAppFunctions$1, reason: invalid class name and case insensitive filesystem */
    /* JADX INFO: compiled from: AppSearchAppFunctionReader.kt */
    @Metadata(d1 = {"\u0000\u0012\n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\u0010\u0000\u001a\u00020\u0001*\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00040\u00030\u0002H\n"}, d2 = {"<anonymous>", "", "Lkotlinx/coroutines/flow/FlowCollector;", "", "Landroidx/appfunctions/metadata/AppFunctionPackageMetadata;"}, k = 3, mv = {2, 1, 0}, xi = 48)
    @DebugMetadata(c = "androidx.appfunctions.internal.AppSearchAppFunctionReader$searchAppFunctions$1", f = "AppSearchAppFunctionReader.kt", i = {}, l = {84}, m = "invokeSuspend", n = {}, s = {}, v = 1)
    static final class C01611 extends SuspendLambda implements Function2<FlowCollector<? super List<? extends AppFunctionPackageMetadata>>, Continuation<? super Unit>, Object> {
        private /* synthetic */ Object L$0;
        int label;

        C01611(Continuation<? super C01611> continuation) {
            super(2, continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
            C01611 c01611 = new C01611(continuation);
            c01611.L$0 = obj;
            return c01611;
        }

        @Override // kotlin.jvm.functions.Function2
        public /* bridge */ /* synthetic */ Object invoke(FlowCollector<? super List<? extends AppFunctionPackageMetadata>> flowCollector, Continuation<? super Unit> continuation) {
            return invoke2((FlowCollector<? super List<AppFunctionPackageMetadata>>) flowCollector, continuation);
        }

        /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
        public final Object invoke2(FlowCollector<? super List<AppFunctionPackageMetadata>> flowCollector, Continuation<? super Unit> continuation) {
            return ((C01611) create(flowCollector, continuation)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            Object coroutine_suspended = IntrinsicsKt.getCOROUTINE_SUSPENDED();
            int i = this.label;
            if (i == 0) {
                ResultKt.throwOnFailure(obj);
                this.label = 1;
                if (((FlowCollector) this.L$0).emit(CollectionsKt.emptyList(), this) == coroutine_suspended) {
                    return coroutine_suspended;
                }
            } else {
                if (i != 1) {
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                }
                ResultKt.throwOnFailure(obj);
            }
            return Unit.INSTANCE;
        }
    }

    @Override // androidx.appfunctions.internal.AppFunctionReader
    public Flow<List<AppFunctionPackageMetadata>> searchAppFunctions(AppFunctionSearchSpec searchFunctionSpec) {
        Intrinsics.checkNotNullParameter(searchFunctionSpec, "searchFunctionSpec");
        Set<String> packageNames = searchFunctionSpec.getPackageNames();
        if (packageNames != null && packageNames.isEmpty()) {
            return FlowKt.flow(new C01611(null));
        }
        return FlowKt.callbackFlow(new AnonymousClass2(searchFunctionSpec, null));
    }

    /* JADX INFO: renamed from: androidx.appfunctions.internal.AppSearchAppFunctionReader$searchAppFunctions$2, reason: invalid class name */
    /* JADX INFO: compiled from: AppSearchAppFunctionReader.kt */
    @Metadata(d1 = {"\u0000\u0012\n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\u0010\u0000\u001a\u00020\u0001*\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00040\u00030\u0002H\n"}, d2 = {"<anonymous>", "", "Lkotlinx/coroutines/channels/ProducerScope;", "", "Landroidx/appfunctions/metadata/AppFunctionPackageMetadata;"}, k = 3, mv = {2, 1, 0}, xi = 48)
    @DebugMetadata(c = "androidx.appfunctions.internal.AppSearchAppFunctionReader$searchAppFunctions$2", f = "AppSearchAppFunctionReader.kt", i = {0, 1, 1, 2, 2}, l = {88, 91, 91, 112}, m = "invokeSuspend", n = {"$this$callbackFlow", "$this$callbackFlow", "session", "$this$callbackFlow", "session"}, s = {"L$0", "L$0", "L$1", "L$0", "L$1"}, v = 1)
    static final class AnonymousClass2 extends SuspendLambda implements Function2<ProducerScope<? super List<? extends AppFunctionPackageMetadata>>, Continuation<? super Unit>, Object> {
        final /* synthetic */ AppFunctionSearchSpec $searchFunctionSpec;
        private /* synthetic */ Object L$0;
        Object L$1;
        Object L$2;
        int label;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        AnonymousClass2(AppFunctionSearchSpec appFunctionSearchSpec, Continuation<? super AnonymousClass2> continuation) {
            super(2, continuation);
            this.$searchFunctionSpec = appFunctionSearchSpec;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
            AnonymousClass2 anonymousClass2 = AppSearchAppFunctionReader.this.new AnonymousClass2(this.$searchFunctionSpec, continuation);
            anonymousClass2.L$0 = obj;
            return anonymousClass2;
        }

        @Override // kotlin.jvm.functions.Function2
        public /* bridge */ /* synthetic */ Object invoke(ProducerScope<? super List<? extends AppFunctionPackageMetadata>> producerScope, Continuation<? super Unit> continuation) {
            return invoke2((ProducerScope<? super List<AppFunctionPackageMetadata>>) producerScope, continuation);
        }

        /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
        public final Object invoke2(ProducerScope<? super List<AppFunctionPackageMetadata>> producerScope, Continuation<? super Unit> continuation) {
            return ((AnonymousClass2) create(producerScope, continuation)).invokeSuspend(Unit.INSTANCE);
        }

        /* JADX WARN: Code duplicated, block: B:24:0x0098  */
        /* JADX WARN: Code duplicated, block: B:27:0x00a9  */
        /* JADX WARN: Code restructure failed: missing block: B:29:0x00f1, code lost:
        
            if (kotlinx.coroutines.channels.ProduceKt.awaitClose(r11, new androidx.appfunctions.internal.AppSearchAppFunctionReader$searchAppFunctions$2$$ExternalSyntheticLambda0(r2, r10, r13), r20) == r1) goto L30;
         */
        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        public final java.lang.Object invokeSuspend(java.lang.Object r21) throws androidx.appsearch.exceptions.AppSearchException {
            /*
                Method dump skipped, instruction units count: 247
                To view this dump change 'Code comments level' option to 'DEBUG'
            */
            throw new UnsupportedOperationException("Method not decompiled: androidx.appfunctions.internal.AppSearchAppFunctionReader.AnonymousClass2.invokeSuspend(java.lang.Object):java.lang.Object");
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static final Unit invokeSuspend$lambda$0(Job job, AppSearchChannelObserver appSearchChannelObserver, GlobalSearchSession globalSearchSession) throws AppSearchException {
            Job.DefaultImpls.cancel$default(job, (CancellationException) null, 1, (Object) null);
            appSearchChannelObserver.close();
            globalSearchSession.unregisterObserverCallback(AppSearchAppFunctionReader.SYSTEM_PACKAGE_NAME, appSearchChannelObserver);
            globalSearchSession.close();
            return Unit.INSTANCE;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX INFO: compiled from: AppSearchAppFunctionReader.kt */
    @Metadata(d1 = {"\u0000,\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u0002\u0018\u00002\u00020\u0001B\u0007¢\u0006\u0004\b\u0002\u0010\u0003J\u0010\u0010\u0007\u001a\u00020\u00062\u0006\u0010\b\u001a\u00020\tH\u0016J\u0010\u0010\n\u001a\u00020\u00062\u0006\u0010\b\u001a\u00020\u000bH\u0016J\f\u0010\f\u001a\b\u0012\u0004\u0012\u00020\u00060\rJ\u0006\u0010\u000e\u001a\u00020\u0006R\u0014\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00060\u0005X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u000f"}, d2 = {"Landroidx/appfunctions/internal/AppSearchAppFunctionReader$AppSearchChannelObserver;", "Landroidx/appsearch/observer/ObserverCallback;", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "()V", "updateChannel", "Lkotlinx/coroutines/channels/Channel;", "", "onSchemaChanged", "changeInfo", "Landroidx/appsearch/observer/SchemaChangeInfo;", "onDocumentChanged", "Landroidx/appsearch/observer/DocumentChangeInfo;", "observe", "Lkotlinx/coroutines/flow/Flow;", SplitLayout.TAG_CLOSE, AppFunctionMetadataKt.APP_FUNCTION_NAMESPACE}, k = 1, mv = {2, 1, 0}, xi = 48)
    static final class AppSearchChannelObserver implements ObserverCallback {
        private final Channel<Unit> updateChannel = ChannelKt.Channel$default(0, null, null, 6, null);

        @Override // androidx.appsearch.observer.ObserverCallback
        public void onSchemaChanged(SchemaChangeInfo changeInfo) {
            Intrinsics.checkNotNullParameter(changeInfo, "changeInfo");
            this.updateChannel.mo4054trySendJP2dKIU(Unit.INSTANCE);
        }

        @Override // androidx.appsearch.observer.ObserverCallback
        public void onDocumentChanged(DocumentChangeInfo changeInfo) {
            Intrinsics.checkNotNullParameter(changeInfo, "changeInfo");
            this.updateChannel.mo4054trySendJP2dKIU(Unit.INSTANCE);
        }

        public final Flow<Unit> observe() {
            return FlowKt.receiveAsFlow(this.updateChannel);
        }

        public final void close() {
            SendChannel.DefaultImpls.close$default(this.updateChannel, null, 1, null);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final ObserverSpec buildObserverSpec(Set<String> packageNames) {
        ObserverSpec.Builder builder = new ObserverSpec.Builder();
        ArrayList arrayList = new ArrayList();
        for (String str : packageNames) {
            CollectionsKt.addAll(arrayList, CollectionsKt.listOf((Object[]) new String[]{"AppFunctionStaticMetadata-" + str, "AppFunctionRuntimeMetadata-" + str, "AppFunctionComponentMetadataDocument-" + str}));
        }
        ObserverSpec observerSpecBuild = builder.addFilterSchemas(arrayList).build();
        Intrinsics.checkNotNullExpressionValue(observerSpecBuild, "build(...)");
        return observerSpecBuild;
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Code duplicated, block: B:7:0x0014  */
    /* JADX WARN: Code restructure failed: missing block: B:20:0x00d5, code lost:
    
        if (r13 == r1) goto L21;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final java.lang.Object performSearch(androidx.appsearch.app.GlobalSearchSession r11, androidx.appfunctions.AppFunctionSearchSpec r12, kotlin.coroutines.Continuation<? super java.util.List<androidx.appfunctions.metadata.AppFunctionPackageMetadata>> r13) {
        /*
            Method dump skipped, instruction units count: 329
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.appfunctions.internal.AppSearchAppFunctionReader.performSearch(androidx.appsearch.app.GlobalSearchSession, androidx.appfunctions.AppFunctionSearchSpec, kotlin.coroutines.Continuation):java.lang.Object");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final AppFunctionMetadata performSearch$lambda$0(AppSearchAppFunctionReader appSearchAppFunctionReader, Map map, SearchResult searchResult) {
        Intrinsics.checkNotNullParameter(searchResult, "searchResult");
        return appSearchAppFunctionReader.convertSearchResultToAppFunctionMetadata(searchResult, map);
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Code duplicated, block: B:7:0x0014  */
    public final Object searchTopLevelComponent(GlobalSearchSession globalSearchSession, Set<String> set, Continuation<? super Map<String, AppFunctionComponentsMetadata>> continuation) {
        C01621 c01621;
        if (continuation instanceof C01621) {
            c01621 = (C01621) continuation;
            if ((c01621.label & Integer.MIN_VALUE) != 0) {
                c01621.label -= Integer.MIN_VALUE;
            } else {
                c01621 = new C01621(continuation);
            }
        } else {
            c01621 = new C01621(continuation);
        }
        Object obj = c01621.result;
        Object coroutine_suspended = IntrinsicsKt.getCOROUTINE_SUSPENDED();
        int i = c01621.label;
        if (i != 0) {
            if (i != 1) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            Map map = (Map) c01621.L$0;
            ResultKt.throwOnFailure(obj);
            return map;
        }
        ResultKt.throwOnFailure(obj);
        SearchSpec.Builder builderAddFilterNamespaces = new SearchSpec.Builder().addFilterNamespaces(APP_FUNCTIONS_NAMESPACE);
        if (set == null) {
            set = SetsKt.emptySet();
        }
        ArrayList arrayList = new ArrayList();
        Iterator<T> it = set.iterator();
        while (it.hasNext()) {
            CollectionsKt.addAll(arrayList, CollectionsKt.listOf("AppFunctionComponentMetadataDocument-" + ((String) it.next())));
        }
        SearchSpec searchSpecBuild = builderAddFilterNamespaces.addFilterSchemas(arrayList).addFilterPackageNames(SYSTEM_PACKAGE_NAME).setVerbatimSearchEnabled(true).setNumericSearchEnabled(true).setListFilterQueryLanguageEnabled(true).build();
        Intrinsics.checkNotNullExpressionValue(searchSpecBuild, "build(...)");
        final LinkedHashMap linkedHashMap = new LinkedHashMap();
        SearchResults searchResultsSearch = globalSearchSession.search("", searchSpecBuild);
        Intrinsics.checkNotNullExpressionValue(searchResultsSearch, "search(...)");
        Function1 function1 = new Function1() { // from class: androidx.appfunctions.internal.AppSearchAppFunctionReader$$ExternalSyntheticLambda0
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj2) {
                return AppSearchAppFunctionReader.searchTopLevelComponent$lambda$1(this.f$0, linkedHashMap, (SearchResult) obj2);
            }
        };
        c01621.L$0 = linkedHashMap;
        c01621.label = 1;
        return AppSearchUtilsKt.readAll(searchResultsSearch, function1, c01621) == coroutine_suspended ? coroutine_suspended : linkedHashMap;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit searchTopLevelComponent$lambda$1(AppSearchAppFunctionReader appSearchAppFunctionReader, Map map, SearchResult searchResult) {
        Intrinsics.checkNotNullParameter(searchResult, "searchResult");
        appSearchAppFunctionReader.extractAppFunctionComponentsMetadataFromSearchResult(searchResult, map);
        return Unit.INSTANCE;
    }

    private final void extractAppFunctionComponentsMetadataFromSearchResult(SearchResult searchResult, Map<String, AppFunctionComponentsMetadata> sharedTopLevelComponentsByPackage) {
        Object documentClass;
        AppFunctionComponentsMetadata appFunctionComponentsMetadata;
        String propertyString = searchResult.getGenericDocument().getPropertyString("packageName");
        if (propertyString == null) {
            throw new IllegalStateException("Required value was null.".toString());
        }
        GenericDocument genericDocument = searchResult.getGenericDocument();
        Intrinsics.checkNotNullExpressionValue(genericDocument, "getGenericDocument(...)");
        try {
            documentClass = genericDocument.toDocumentClass(AppFunctionComponentsMetadataDocument.class);
        } catch (Exception e) {
            Log.e(Constants.APP_FUNCTIONS_TAG, "Failed to convert search result " + genericDocument.getId() + " to " + Reflection.getOrCreateKotlinClass(AppFunctionComponentsMetadataDocument.class).getSimpleName(), e);
            documentClass = null;
        }
        AppFunctionComponentsMetadataDocument appFunctionComponentsMetadataDocument = (AppFunctionComponentsMetadataDocument) documentClass;
        if (appFunctionComponentsMetadataDocument == null || (appFunctionComponentsMetadata = appFunctionComponentsMetadataDocument.toAppFunctionComponentsMetadata()) == null || appFunctionComponentsMetadata.getDataTypes().isEmpty()) {
            return;
        }
        sharedTopLevelComponentsByPackage.put(propertyString, appFunctionComponentsMetadata);
    }

    private final /* synthetic */ <T> T safeCastToDocumentClass(GenericDocument genericDocument) {
        try {
            Intrinsics.reifiedOperationMarker(4, ExifInterface.GPS_DIRECTION_TRUE);
            return (T) genericDocument.toDocumentClass(Object.class);
        } catch (Exception e) {
            StringBuilder sbAppend = new StringBuilder("Failed to convert search result ").append(genericDocument.getId()).append(" to ");
            Intrinsics.reifiedOperationMarker(4, ExifInterface.GPS_DIRECTION_TRUE);
            Log.e(Constants.APP_FUNCTIONS_TAG, sbAppend.append(Reflection.getOrCreateKotlinClass(Object.class).getSimpleName()).toString(), e);
            return null;
        }
    }

    private final AppFunctionMetadata convertSearchResultToAppFunctionMetadata(SearchResult searchResult, Map<String, AppFunctionComponentsMetadata> sharedTopLevelComponentsByPackage) {
        Object documentClass;
        Object documentClass2;
        AppFunctionResponseMetadata appFunctionResponseMetadata;
        AppFunctionComponentsMetadata appFunctionComponentsMetadata;
        String propertyString = searchResult.getGenericDocument().getPropertyString("functionId");
        if (propertyString == null) {
            throw new IllegalStateException("Required value was null.".toString());
        }
        String propertyString2 = searchResult.getGenericDocument().getPropertyString("packageName");
        if (propertyString2 == null) {
            throw new IllegalStateException("Required value was null.".toString());
        }
        GenericDocument genericDocument = searchResult.getGenericDocument();
        Intrinsics.checkNotNullExpressionValue(genericDocument, "getGenericDocument(...)");
        try {
            documentClass = genericDocument.toDocumentClass(AppFunctionMetadataDocument.class);
        } catch (Exception e) {
            Log.e(Constants.APP_FUNCTIONS_TAG, "Failed to convert search result " + genericDocument.getId() + " to " + Reflection.getOrCreateKotlinClass(AppFunctionMetadataDocument.class).getSimpleName(), e);
            documentClass = null;
        }
        AppFunctionMetadataDocument appFunctionMetadataDocument = (AppFunctionMetadataDocument) documentClass;
        if (appFunctionMetadataDocument == null) {
            return null;
        }
        List<SearchResult> joinedResults = searchResult.getJoinedResults();
        Intrinsics.checkNotNullExpressionValue(joinedResults, "getJoinedResults(...)");
        SearchResult searchResult2 = (SearchResult) CollectionsKt.singleOrNull((List) joinedResults);
        if (searchResult2 == null) {
            Log.e(Constants.APP_FUNCTIONS_TAG, "Runtime metadata not found for " + appFunctionMetadataDocument.getId());
            return null;
        }
        GenericDocument genericDocument2 = searchResult2.getGenericDocument();
        Intrinsics.checkNotNullExpressionValue(genericDocument2, "getGenericDocument(...)");
        try {
            documentClass2 = genericDocument2.toDocumentClass(AppFunctionRuntimeMetadata.class);
        } catch (Exception e2) {
            Log.e(Constants.APP_FUNCTIONS_TAG, "Failed to convert search result " + genericDocument2.getId() + " to " + Reflection.getOrCreateKotlinClass(AppFunctionRuntimeMetadata.class).getSimpleName(), e2);
            documentClass2 = null;
        }
        AppFunctionRuntimeMetadata appFunctionRuntimeMetadata = (AppFunctionRuntimeMetadata) documentClass2;
        if (appFunctionRuntimeMetadata == null) {
            return null;
        }
        GenericDocument genericDocument3 = searchResult.getGenericDocument();
        Intrinsics.checkNotNullExpressionValue(genericDocument3, "getGenericDocument(...)");
        AppFunctionSchemaMetadata appFunctionSchemaMetadataBuildSchemaMetadataFromGdForLegacyIndexer = buildSchemaMetadataFromGdForLegacyIndexer(genericDocument3);
        List<AppFunctionParameterMetadata> appFunctionParameterMetadata = getAppFunctionParameterMetadata(appFunctionMetadataDocument, appFunctionSchemaMetadataBuildSchemaMetadataFromGdForLegacyIndexer);
        if (appFunctionParameterMetadata == null || (appFunctionResponseMetadata = getAppFunctionResponseMetadata(appFunctionMetadataDocument, appFunctionSchemaMetadataBuildSchemaMetadataFromGdForLegacyIndexer)) == null || (appFunctionComponentsMetadata = getAppFunctionComponentsMetadata(propertyString2, appFunctionMetadataDocument, appFunctionSchemaMetadataBuildSchemaMetadataFromGdForLegacyIndexer, sharedTopLevelComponentsByPackage)) == null) {
            return null;
        }
        AppFunctionDeprecationMetadata appFunctionDeprecationMetadata = getAppFunctionDeprecationMetadata(appFunctionMetadataDocument);
        boolean zComputeEffectivelyEnabled = computeEffectivelyEnabled(appFunctionMetadataDocument, appFunctionRuntimeMetadata);
        String description = appFunctionMetadataDocument.getDescription();
        if (description == null) {
            description = "";
        }
        return new AppFunctionMetadata(propertyString, propertyString2, zComputeEffectivelyEnabled, appFunctionSchemaMetadataBuildSchemaMetadataFromGdForLegacyIndexer, appFunctionParameterMetadata, appFunctionResponseMetadata, appFunctionComponentsMetadata, description, appFunctionDeprecationMetadata);
    }

    private final boolean computeEffectivelyEnabled(AppFunctionMetadataDocument staticMetadata, AppFunctionRuntimeMetadata runtimeMetadata) {
        int enabled = (int) runtimeMetadata.getEnabled();
        if (enabled == 0) {
            return staticMetadata.isEnabledByDefault();
        }
        if (enabled == 1) {
            return true;
        }
        if (enabled == 2) {
            return false;
        }
        throw new IllegalStateException("Unknown AppFunction state: " + runtimeMetadata.getEnabled() + '.');
    }

    private final AppFunctionSchemaMetadata buildSchemaMetadataFromGdForLegacyIndexer(GenericDocument document) {
        String propertyString = document.getPropertyString("schemaName");
        String propertyString2 = document.getPropertyString("schemaCategory");
        long propertyLong = document.getPropertyLong("schemaVersion");
        if (propertyString != null && propertyString2 != null && propertyLong != 0) {
            return new AppFunctionSchemaMetadata(propertyString2, propertyString, propertyLong);
        }
        if (propertyString == null && propertyString2 == null && propertyLong == 0) {
            return null;
        }
        Log.e(Constants.APP_FUNCTIONS_TAG, "Unexpected state: schemaName=" + propertyString + ", schemaCategory=" + propertyString2 + ", schemaVersion=" + propertyLong);
        return null;
    }

    /* JADX WARN: Code duplicated, block: B:31:0x015b  */
    /* JADX WARN: Code duplicated, block: B:34:0x0171  */
    /* JADX WARN: Code duplicated, block: B:42:0x01b5 A[RETURN] */
    /* JADX WARN: Code duplicated, block: B:49:0x01ec A[ADDED_TO_REGION, RETURN] */
    /* JADX WARN: Code duplicated, block: B:50:0x01ed  */
    /* JADX WARN: Code duplicated, block: B:59:0x0223  */
    /* JADX WARN: Code duplicated, block: B:62:0x023c A[RETURN] */
    /* JADX WARN: Code duplicated, block: B:63:0x023d  */
    /* JADX WARN: Code duplicated, block: B:65:0x024f  */
    /* JADX WARN: Code duplicated, block: B:67:0x026a  */
    /* JADX WARN: Code duplicated, block: B:69:0x01b6 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:71:0x017e A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:7:0x001e  */
    @Override // androidx.appfunctions.internal.AppFunctionReader
    public Object getAppFunctionMetadata(String str, String str2, Continuation<? super AppFunctionMetadata> continuation) throws AppFunctionFunctionNotFoundException {
        AnonymousClass1 anonymousClass1;
        char c;
        Object obj;
        String str3;
        String str4;
        String appFunctionId;
        GlobalSearchSession globalSearchSession;
        String str5;
        String str6;
        AppSearchBatchResult appSearchBatchResult;
        Object objAwait;
        AppSearchBatchResult appSearchBatchResult2;
        String str7;
        AppSearchBatchResult appSearchBatchResult3;
        GenericDocument genericDocument;
        GenericDocument genericDocument2;
        Object documentClass;
        AppFunctionMetadataDocument appFunctionMetadataDocument;
        Object documentClass2;
        AppFunctionRuntimeMetadata appFunctionRuntimeMetadata;
        AppFunctionSchemaMetadata appFunctionSchemaMetadataBuildSchemaMetadataFromGdForLegacyIndexer;
        List<AppFunctionParameterMetadata> appFunctionParameterMetadata;
        AppFunctionResponseMetadata appFunctionResponseMetadata;
        Object objSearchTopLevelComponent;
        AppSearchAppFunctionReader appSearchAppFunctionReader;
        AppFunctionSchemaMetadata appFunctionSchemaMetadata;
        AppFunctionResponseMetadata appFunctionResponseMetadata2;
        String str8;
        AppFunctionMetadataDocument appFunctionMetadataDocument2;
        AppFunctionMetadataDocument appFunctionMetadataDocument3;
        String str9;
        AppFunctionRuntimeMetadata appFunctionRuntimeMetadata2;
        String str10;
        List<AppFunctionParameterMetadata> list;
        AppFunctionComponentsMetadata appFunctionComponentsMetadata;
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
        Object objCreateSearchSession = anonymousClass1.result;
        Object coroutine_suspended = IntrinsicsKt.getCOROUTINE_SUSPENDED();
        int i = anonymousClass1.label;
        if (i == 0) {
            c = 0;
            obj = null;
            ResultKt.throwOnFailure(objCreateSearchSession);
            Context context = this.context;
            str3 = str;
            anonymousClass1.L$0 = str3;
            str4 = str2;
            anonymousClass1.L$1 = str4;
            anonymousClass1.label = 1;
            objCreateSearchSession = AppSearchUtilsKt.createSearchSession(context, anonymousClass1);
            if (objCreateSearchSession != coroutine_suspended) {
            }
            return coroutine_suspended;
        }
        if (i == 1) {
            c = 0;
            obj = null;
            String str11 = (String) anonymousClass1.L$1;
            String str12 = (String) anonymousClass1.L$0;
            ResultKt.throwOnFailure(objCreateSearchSession);
            str4 = str11;
            str3 = str12;
        } else {
            if (i == 2) {
                String str13 = (String) anonymousClass1.L$3;
                GlobalSearchSession globalSearchSession2 = (GlobalSearchSession) anonymousClass1.L$2;
                c = 0;
                String str14 = (String) anonymousClass1.L$1;
                obj = null;
                String str15 = (String) anonymousClass1.L$0;
                ResultKt.throwOnFailure(objCreateSearchSession);
                str5 = str15;
                appFunctionId = str13;
                str6 = str14;
                globalSearchSession = globalSearchSession2;
                appSearchBatchResult = (AppSearchBatchResult) objCreateSearchSession;
                GetByDocumentIdRequest.Builder builder = new GetByDocumentIdRequest.Builder(APP_FUNCTIONS_RUNTIME_NAMESPACE);
                String[] strArr = new String[1];
                strArr[c] = appFunctionId;
                ListenableFuture<AppSearchBatchResult<String, GenericDocument>> byDocumentIdAsync = globalSearchSession.getByDocumentIdAsync(SYSTEM_PACKAGE_NAME, APP_FUNCTIONS_RUNTIME_DATABASE_NAME, builder.addIds(strArr).build());
                Intrinsics.checkNotNullExpressionValue(byDocumentIdAsync, "getByDocumentIdAsync(...)");
                anonymousClass1.L$0 = str5;
                anonymousClass1.L$1 = str6;
                anonymousClass1.L$2 = globalSearchSession;
                anonymousClass1.L$3 = appFunctionId;
                anonymousClass1.L$4 = appSearchBatchResult;
                anonymousClass1.label = 3;
                objAwait = ListenableFutureKt.await(byDocumentIdAsync, anonymousClass1);
                if (objAwait != coroutine_suspended) {
                    appSearchBatchResult2 = appSearchBatchResult;
                    objCreateSearchSession = objAwait;
                    str7 = str5;
                    appSearchBatchResult3 = (AppSearchBatchResult) objCreateSearchSession;
                    genericDocument = (GenericDocument) appSearchBatchResult2.getSuccesses().get(appFunctionId);
                    if (genericDocument == null) {
                        throw new AppFunctionFunctionNotFoundException("Function " + str7 + " is not available under " + str6);
                    }
                    genericDocument2 = (GenericDocument) appSearchBatchResult3.getSuccesses().get(appFunctionId);
                    if (genericDocument2 == null) {
                        throw new AppFunctionFunctionNotFoundException("Function " + str7 + " is not available under " + str6);
                    }
                    documentClass = genericDocument.toDocumentClass(AppFunctionMetadataDocument.class);
                    appFunctionMetadataDocument = (AppFunctionMetadataDocument) documentClass;
                    if (appFunctionMetadataDocument == null) {
                        return obj;
                    }
                    documentClass2 = genericDocument2.toDocumentClass(AppFunctionRuntimeMetadata.class);
                    appFunctionRuntimeMetadata = (AppFunctionRuntimeMetadata) documentClass2;
                    if (appFunctionRuntimeMetadata != null) {
                        return obj;
                    }
                    Set<String> of = SetsKt.setOf(str6);
                    anonymousClass1.L$0 = str7;
                    anonymousClass1.L$1 = str6;
                    anonymousClass1.L$2 = appFunctionMetadataDocument;
                    anonymousClass1.L$3 = appFunctionRuntimeMetadata;
                    anonymousClass1.L$4 = appFunctionSchemaMetadataBuildSchemaMetadataFromGdForLegacyIndexer;
                    anonymousClass1.L$5 = appFunctionParameterMetadata;
                    anonymousClass1.L$6 = appFunctionResponseMetadata;
                    anonymousClass1.L$7 = this;
                    anonymousClass1.L$8 = str6;
                    anonymousClass1.L$9 = appFunctionMetadataDocument;
                    anonymousClass1.L$10 = appFunctionSchemaMetadataBuildSchemaMetadataFromGdForLegacyIndexer;
                    anonymousClass1.label = 4;
                    objSearchTopLevelComponent = searchTopLevelComponent(globalSearchSession, of, anonymousClass1);
                    if (objSearchTopLevelComponent != coroutine_suspended) {
                        appSearchAppFunctionReader = this;
                        appFunctionSchemaMetadata = appFunctionSchemaMetadataBuildSchemaMetadataFromGdForLegacyIndexer;
                        appFunctionResponseMetadata2 = appFunctionResponseMetadata;
                        str8 = str6;
                        appFunctionMetadataDocument2 = appFunctionMetadataDocument;
                        appFunctionMetadataDocument3 = appFunctionMetadataDocument2;
                        str9 = str7;
                        appFunctionRuntimeMetadata2 = appFunctionRuntimeMetadata;
                        objCreateSearchSession = objSearchTopLevelComponent;
                        str10 = str8;
                    }
                }
                return coroutine_suspended;
            }
            if (i == 3) {
                AppSearchBatchResult appSearchBatchResult4 = (AppSearchBatchResult) anonymousClass1.L$4;
                String str16 = (String) anonymousClass1.L$3;
                globalSearchSession = (GlobalSearchSession) anonymousClass1.L$2;
                String str17 = (String) anonymousClass1.L$1;
                str7 = (String) anonymousClass1.L$0;
                ResultKt.throwOnFailure(objCreateSearchSession);
                appFunctionId = str16;
                obj = null;
                appSearchBatchResult2 = appSearchBatchResult4;
                str6 = str17;
                appSearchBatchResult3 = (AppSearchBatchResult) objCreateSearchSession;
                genericDocument = (GenericDocument) appSearchBatchResult2.getSuccesses().get(appFunctionId);
                if (genericDocument == null) {
                    throw new AppFunctionFunctionNotFoundException("Function " + str7 + " is not available under " + str6);
                }
                genericDocument2 = (GenericDocument) appSearchBatchResult3.getSuccesses().get(appFunctionId);
                if (genericDocument2 == null) {
                    throw new AppFunctionFunctionNotFoundException("Function " + str7 + " is not available under " + str6);
                }
                try {
                    documentClass = genericDocument.toDocumentClass(AppFunctionMetadataDocument.class);
                } catch (Exception e) {
                    Log.e(Constants.APP_FUNCTIONS_TAG, "Failed to convert search result " + genericDocument.getId() + " to " + Reflection.getOrCreateKotlinClass(AppFunctionMetadataDocument.class).getSimpleName(), e);
                    documentClass = obj;
                }
                appFunctionMetadataDocument = (AppFunctionMetadataDocument) documentClass;
                if (appFunctionMetadataDocument == null) {
                    return obj;
                }
                try {
                    documentClass2 = genericDocument2.toDocumentClass(AppFunctionRuntimeMetadata.class);
                } catch (Exception e2) {
                    Log.e(Constants.APP_FUNCTIONS_TAG, "Failed to convert search result " + genericDocument2.getId() + " to " + Reflection.getOrCreateKotlinClass(AppFunctionRuntimeMetadata.class).getSimpleName(), e2);
                    documentClass2 = obj;
                }
                appFunctionRuntimeMetadata = (AppFunctionRuntimeMetadata) documentClass2;
                if (appFunctionRuntimeMetadata != null || (appFunctionParameterMetadata = getAppFunctionParameterMetadata(appFunctionMetadataDocument, (appFunctionSchemaMetadataBuildSchemaMetadataFromGdForLegacyIndexer = buildSchemaMetadataFromGdForLegacyIndexer(genericDocument)))) == null || (appFunctionResponseMetadata = getAppFunctionResponseMetadata(appFunctionMetadataDocument, appFunctionSchemaMetadataBuildSchemaMetadataFromGdForLegacyIndexer)) == null) {
                    return obj;
                }
                Set<String> of2 = SetsKt.setOf(str6);
                anonymousClass1.L$0 = str7;
                anonymousClass1.L$1 = str6;
                anonymousClass1.L$2 = appFunctionMetadataDocument;
                anonymousClass1.L$3 = appFunctionRuntimeMetadata;
                anonymousClass1.L$4 = appFunctionSchemaMetadataBuildSchemaMetadataFromGdForLegacyIndexer;
                anonymousClass1.L$5 = appFunctionParameterMetadata;
                anonymousClass1.L$6 = appFunctionResponseMetadata;
                anonymousClass1.L$7 = this;
                anonymousClass1.L$8 = str6;
                anonymousClass1.L$9 = appFunctionMetadataDocument;
                anonymousClass1.L$10 = appFunctionSchemaMetadataBuildSchemaMetadataFromGdForLegacyIndexer;
                anonymousClass1.label = 4;
                objSearchTopLevelComponent = searchTopLevelComponent(globalSearchSession, of2, anonymousClass1);
                if (objSearchTopLevelComponent != coroutine_suspended) {
                    appSearchAppFunctionReader = this;
                    appFunctionSchemaMetadata = appFunctionSchemaMetadataBuildSchemaMetadataFromGdForLegacyIndexer;
                    appFunctionResponseMetadata2 = appFunctionResponseMetadata;
                    str8 = str6;
                    appFunctionMetadataDocument2 = appFunctionMetadataDocument;
                    appFunctionMetadataDocument3 = appFunctionMetadataDocument2;
                    str9 = str7;
                    appFunctionRuntimeMetadata2 = appFunctionRuntimeMetadata;
                    objCreateSearchSession = objSearchTopLevelComponent;
                    str10 = str8;
                }
                return coroutine_suspended;
            }
            if (i != 4) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            appFunctionSchemaMetadataBuildSchemaMetadataFromGdForLegacyIndexer = (AppFunctionSchemaMetadata) anonymousClass1.L$10;
            appFunctionMetadataDocument2 = (AppFunctionMetadataDocument) anonymousClass1.L$9;
            str10 = (String) anonymousClass1.L$8;
            appSearchAppFunctionReader = (AppSearchAppFunctionReader) anonymousClass1.L$7;
            AppFunctionResponseMetadata appFunctionResponseMetadata3 = (AppFunctionResponseMetadata) anonymousClass1.L$6;
            appFunctionParameterMetadata = (List) anonymousClass1.L$5;
            AppFunctionSchemaMetadata appFunctionSchemaMetadata2 = (AppFunctionSchemaMetadata) anonymousClass1.L$4;
            appFunctionRuntimeMetadata2 = (AppFunctionRuntimeMetadata) anonymousClass1.L$3;
            appFunctionMetadataDocument3 = (AppFunctionMetadataDocument) anonymousClass1.L$2;
            String str18 = (String) anonymousClass1.L$1;
            String str19 = (String) anonymousClass1.L$0;
            ResultKt.throwOnFailure(objCreateSearchSession);
            str9 = str19;
            appFunctionResponseMetadata2 = appFunctionResponseMetadata3;
            appFunctionSchemaMetadata = appFunctionSchemaMetadata2;
            str8 = str18;
            obj = null;
        }
        list = appFunctionParameterMetadata;
        appFunctionComponentsMetadata = appSearchAppFunctionReader.getAppFunctionComponentsMetadata(str10, appFunctionMetadataDocument2, appFunctionSchemaMetadataBuildSchemaMetadataFromGdForLegacyIndexer, (Map) objCreateSearchSession);
        if (appFunctionComponentsMetadata == null) {
            return obj;
        }
        return new AppFunctionMetadata(str9, str8, computeEffectivelyEnabled(appFunctionMetadataDocument3, appFunctionRuntimeMetadata2), appFunctionSchemaMetadata, list, appFunctionResponseMetadata2, appFunctionComponentsMetadata, null, null, R2.attr.alignItems, null);
        GlobalSearchSession globalSearchSession3 = (GlobalSearchSession) objCreateSearchSession;
        appFunctionId = getAppFunctionId(str4, str3);
        GetByDocumentIdRequest.Builder builder2 = new GetByDocumentIdRequest.Builder(APP_FUNCTIONS_NAMESPACE);
        String[] strArr2 = new String[1];
        strArr2[c] = appFunctionId;
        ListenableFuture<AppSearchBatchResult<String, GenericDocument>> byDocumentIdAsync2 = globalSearchSession3.getByDocumentIdAsync(SYSTEM_PACKAGE_NAME, APP_FUNCTIONS_STATIC_DATABASE_NAME, builder2.addIds(strArr2).build());
        Intrinsics.checkNotNullExpressionValue(byDocumentIdAsync2, "getByDocumentIdAsync(...)");
        anonymousClass1.L$0 = str3;
        anonymousClass1.L$1 = str4;
        anonymousClass1.L$2 = globalSearchSession3;
        anonymousClass1.L$3 = appFunctionId;
        anonymousClass1.label = 2;
        Object objAwait2 = ListenableFutureKt.await(byDocumentIdAsync2, anonymousClass1);
        if (objAwait2 != coroutine_suspended) {
            String str20 = str4;
            globalSearchSession = globalSearchSession3;
            objCreateSearchSession = objAwait2;
            str5 = str3;
            str6 = str20;
            appSearchBatchResult = (AppSearchBatchResult) objCreateSearchSession;
            GetByDocumentIdRequest.Builder builder3 = new GetByDocumentIdRequest.Builder(APP_FUNCTIONS_RUNTIME_NAMESPACE);
            String[] strArr3 = new String[1];
            strArr3[c] = appFunctionId;
            ListenableFuture<AppSearchBatchResult<String, GenericDocument>> byDocumentIdAsync3 = globalSearchSession.getByDocumentIdAsync(SYSTEM_PACKAGE_NAME, APP_FUNCTIONS_RUNTIME_DATABASE_NAME, builder3.addIds(strArr3).build());
            Intrinsics.checkNotNullExpressionValue(byDocumentIdAsync3, "getByDocumentIdAsync(...)");
            anonymousClass1.L$0 = str5;
            anonymousClass1.L$1 = str6;
            anonymousClass1.L$2 = globalSearchSession;
            anonymousClass1.L$3 = appFunctionId;
            anonymousClass1.L$4 = appSearchBatchResult;
            anonymousClass1.label = 3;
            objAwait = ListenableFutureKt.await(byDocumentIdAsync3, anonymousClass1);
            if (objAwait != coroutine_suspended) {
                appSearchBatchResult2 = appSearchBatchResult;
                objCreateSearchSession = objAwait;
                str7 = str5;
                appSearchBatchResult3 = (AppSearchBatchResult) objCreateSearchSession;
                genericDocument = (GenericDocument) appSearchBatchResult2.getSuccesses().get(appFunctionId);
                if (genericDocument == null) {
                    throw new AppFunctionFunctionNotFoundException("Function " + str7 + " is not available under " + str6);
                }
                genericDocument2 = (GenericDocument) appSearchBatchResult3.getSuccesses().get(appFunctionId);
                if (genericDocument2 == null) {
                    throw new AppFunctionFunctionNotFoundException("Function " + str7 + " is not available under " + str6);
                }
                documentClass = genericDocument.toDocumentClass(AppFunctionMetadataDocument.class);
                appFunctionMetadataDocument = (AppFunctionMetadataDocument) documentClass;
                if (appFunctionMetadataDocument == null) {
                    return obj;
                }
                documentClass2 = genericDocument2.toDocumentClass(AppFunctionRuntimeMetadata.class);
                appFunctionRuntimeMetadata = (AppFunctionRuntimeMetadata) documentClass2;
                if (appFunctionRuntimeMetadata != null) {
                    return obj;
                }
                Set<String> of3 = SetsKt.setOf(str6);
                anonymousClass1.L$0 = str7;
                anonymousClass1.L$1 = str6;
                anonymousClass1.L$2 = appFunctionMetadataDocument;
                anonymousClass1.L$3 = appFunctionRuntimeMetadata;
                anonymousClass1.L$4 = appFunctionSchemaMetadataBuildSchemaMetadataFromGdForLegacyIndexer;
                anonymousClass1.L$5 = appFunctionParameterMetadata;
                anonymousClass1.L$6 = appFunctionResponseMetadata;
                anonymousClass1.L$7 = this;
                anonymousClass1.L$8 = str6;
                anonymousClass1.L$9 = appFunctionMetadataDocument;
                anonymousClass1.L$10 = appFunctionSchemaMetadataBuildSchemaMetadataFromGdForLegacyIndexer;
                anonymousClass1.label = 4;
                objSearchTopLevelComponent = searchTopLevelComponent(globalSearchSession, of3, anonymousClass1);
                if (objSearchTopLevelComponent != coroutine_suspended) {
                    appSearchAppFunctionReader = this;
                    appFunctionSchemaMetadata = appFunctionSchemaMetadataBuildSchemaMetadataFromGdForLegacyIndexer;
                    appFunctionResponseMetadata2 = appFunctionResponseMetadata;
                    str8 = str6;
                    appFunctionMetadataDocument2 = appFunctionMetadataDocument;
                    appFunctionMetadataDocument3 = appFunctionMetadataDocument2;
                    str9 = str7;
                    appFunctionRuntimeMetadata2 = appFunctionRuntimeMetadata;
                    objCreateSearchSession = objSearchTopLevelComponent;
                    str10 = str8;
                    list = appFunctionParameterMetadata;
                    appFunctionComponentsMetadata = appSearchAppFunctionReader.getAppFunctionComponentsMetadata(str10, appFunctionMetadataDocument2, appFunctionSchemaMetadataBuildSchemaMetadataFromGdForLegacyIndexer, (Map) objCreateSearchSession);
                    if (appFunctionComponentsMetadata == null) {
                        return obj;
                    }
                    return new AppFunctionMetadata(str9, str8, computeEffectivelyEnabled(appFunctionMetadataDocument3, appFunctionRuntimeMetadata2), appFunctionSchemaMetadata, list, appFunctionResponseMetadata2, appFunctionComponentsMetadata, null, null, R2.attr.alignItems, null);
                }
            }
        }
        return coroutine_suspended;
    }

    private final String getAppFunctionId(String packageName, String functionId) {
        return packageName + '/' + functionId;
    }

    private final List<AppFunctionParameterMetadata> getAppFunctionParameterMetadata(AppFunctionMetadataDocument appFunctionMetadataDocument, AppFunctionSchemaMetadata schemaMetadata) {
        SchemaAppFunctionInventory schemaAppFunctionInventory;
        Map<AppFunctionSchemaMetadata, CompileTimeAppFunctionMetadata> schemaFunctionsMap;
        CompileTimeAppFunctionMetadata compileTimeAppFunctionMetadata;
        if (!isAppFunctionMetadataDocumentFromDynamicIndexer(appFunctionMetadataDocument)) {
            if (schemaMetadata == null || (schemaAppFunctionInventory = this.schemaAppFunctionInventory) == null || (schemaFunctionsMap = schemaAppFunctionInventory.getSchemaFunctionsMap()) == null || (compileTimeAppFunctionMetadata = schemaFunctionsMap.get(schemaMetadata)) == null) {
                return null;
            }
            return compileTimeAppFunctionMetadata.getParameters();
        }
        List<AppFunctionParameterMetadataDocument> parameters = appFunctionMetadataDocument.getParameters();
        if (parameters == null) {
            return CollectionsKt.emptyList();
        }
        List<AppFunctionParameterMetadataDocument> list = parameters;
        ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(list, 10));
        Iterator<T> it = list.iterator();
        while (it.hasNext()) {
            arrayList.add(((AppFunctionParameterMetadataDocument) it.next()).toAppFunctionParameterMetadata());
        }
        return arrayList;
    }

    private final AppFunctionResponseMetadata getAppFunctionResponseMetadata(AppFunctionMetadataDocument appFunctionMetadataDocument, AppFunctionSchemaMetadata schemaMetadata) {
        SchemaAppFunctionInventory schemaAppFunctionInventory;
        Map<AppFunctionSchemaMetadata, CompileTimeAppFunctionMetadata> schemaFunctionsMap;
        CompileTimeAppFunctionMetadata compileTimeAppFunctionMetadata;
        if (isAppFunctionMetadataDocumentFromDynamicIndexer(appFunctionMetadataDocument)) {
            AppFunctionResponseMetadataDocument response = appFunctionMetadataDocument.getResponse();
            if (response == null) {
                throw new IllegalStateException("Required value was null.".toString());
            }
            return response.toAppFunctionResponseMetadata();
        }
        if (schemaMetadata == null || (schemaAppFunctionInventory = this.schemaAppFunctionInventory) == null || (schemaFunctionsMap = schemaAppFunctionInventory.getSchemaFunctionsMap()) == null || (compileTimeAppFunctionMetadata = schemaFunctionsMap.get(schemaMetadata)) == null) {
            return null;
        }
        return compileTimeAppFunctionMetadata.getResponse();
    }

    private final AppFunctionComponentsMetadata getAppFunctionComponentsMetadata(String packageName, AppFunctionMetadataDocument appFunctionMetadataDocument, AppFunctionSchemaMetadata schemaMetadata, Map<String, AppFunctionComponentsMetadata> sharedTopLevelComponentsByPackage) {
        SchemaAppFunctionInventory schemaAppFunctionInventory;
        if (isAppFunctionMetadataDocumentFromDynamicIndexer(appFunctionMetadataDocument)) {
            AppFunctionComponentsMetadata appFunctionComponentsMetadata = sharedTopLevelComponentsByPackage.get(packageName);
            return appFunctionComponentsMetadata == null ? new AppFunctionComponentsMetadata(null, 1, null) : appFunctionComponentsMetadata;
        }
        if (schemaMetadata == null || (schemaAppFunctionInventory = this.schemaAppFunctionInventory) == null) {
            return null;
        }
        return schemaAppFunctionInventory.getComponentsMetadata();
    }

    private final AppFunctionDeprecationMetadata getAppFunctionDeprecationMetadata(AppFunctionMetadataDocument appFunctionMetadataDocument) {
        AppFunctionDeprecationMetadataDocument deprecation = appFunctionMetadataDocument.getDeprecation();
        if (deprecation != null) {
            return deprecation.toAppFunctionDeprecationMetadata();
        }
        return null;
    }

    private final boolean isAppFunctionMetadataDocumentFromDynamicIndexer(AppFunctionMetadataDocument document) {
        return document.getResponse() != null;
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX INFO: compiled from: AppSearchAppFunctionReader.kt */
    @Metadata(d1 = {"\u0000$\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0003\b\u0082\u0003\u0018\u00002\u00020\u0001B\t\b\u0002¢\u0006\u0004\b\u0002\u0010\u0003R\u000e\u0010\u0004\u001a\u00020\u0005X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0005X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\u0005X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\b\u001a\u00020\u0005X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\t\u001a\u00020\u0005X\u0086T¢\u0006\u0002\n\u0000R\u0013\u0010\n\u001a\u00020\u000b¢\u0006\n\n\u0002\u0010\u000e\u001a\u0004\b\f\u0010\rR\u0011\u0010\u000f\u001a\u00020\u0010¢\u0006\b\n\u0000\u001a\u0004\b\u0011\u0010\u0012¨\u0006\u0013"}, d2 = {"Landroidx/appfunctions/internal/AppSearchAppFunctionReader$Companion;", "", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "()V", "SYSTEM_PACKAGE_NAME", "", "APP_FUNCTIONS_NAMESPACE", "APP_FUNCTIONS_RUNTIME_NAMESPACE", "APP_FUNCTIONS_STATIC_DATABASE_NAME", "APP_FUNCTIONS_RUNTIME_DATABASE_NAME", "OBSERVER_DEBOUNCE_MILLIS", "Lkotlin/time/Duration;", "getOBSERVER_DEBOUNCE_MILLIS-UwyO8pc", "()J", Field.LONG_SIGNATURE_PRIMITIVE, "RUNTIME_SEARCH_SPEC", "Landroidx/appsearch/app/SearchSpec;", "getRUNTIME_SEARCH_SPEC", "()Landroidx/appsearch/app/SearchSpec;", AppFunctionMetadataKt.APP_FUNCTION_NAMESPACE}, k = 1, mv = {2, 1, 0}, xi = 48)
    static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }

        /* JADX INFO: renamed from: getOBSERVER_DEBOUNCE_MILLIS-UwyO8pc, reason: not valid java name */
        public final long m11getOBSERVER_DEBOUNCE_MILLISUwyO8pc() {
            return AppSearchAppFunctionReader.OBSERVER_DEBOUNCE_MILLIS;
        }

        public final SearchSpec getRUNTIME_SEARCH_SPEC() {
            return AppSearchAppFunctionReader.RUNTIME_SEARCH_SPEC;
        }
    }

    static {
        Duration.Companion companion = Duration.INSTANCE;
        OBSERVER_DEBOUNCE_MILLIS = DurationKt.toDuration(1, DurationUnit.SECONDS);
        SearchSpec searchSpecBuild = new SearchSpec.Builder().addFilterNamespaces(APP_FUNCTIONS_RUNTIME_NAMESPACE).addFilterDocumentClasses(AppFunctionRuntimeMetadata.class).addFilterPackageNames(SYSTEM_PACKAGE_NAME).setVerbatimSearchEnabled(true).build();
        Intrinsics.checkNotNullExpressionValue(searchSpecBuild, "build(...)");
        RUNTIME_SEARCH_SPEC = searchSpecBuild;
    }
}
