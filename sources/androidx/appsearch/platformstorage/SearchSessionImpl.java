package androidx.appsearch.platformstorage;

import android.app.appsearch.AppSearchResult;
import android.content.Context;
import android.os.Build;
import androidx.appsearch.app.AppSearchBatchResult;
import androidx.appsearch.app.AppSearchSession;
import androidx.appsearch.app.Features;
import androidx.appsearch.app.GenericDocument;
import androidx.appsearch.app.GetByDocumentIdRequest;
import androidx.appsearch.app.GetSchemaResponse;
import androidx.appsearch.app.PutDocumentsRequest;
import androidx.appsearch.app.RemoveByDocumentIdRequest;
import androidx.appsearch.app.ReportUsageRequest;
import androidx.appsearch.app.SearchResults;
import androidx.appsearch.app.SearchSpec;
import androidx.appsearch.app.SearchSuggestionResult;
import androidx.appsearch.app.SearchSuggestionSpec;
import androidx.appsearch.app.SetSchemaRequest;
import androidx.appsearch.app.SetSchemaResponse;
import androidx.appsearch.app.StorageInfo;
import androidx.appsearch.exceptions.AppSearchException;
import androidx.appsearch.exceptions.IllegalSchemaException;
import androidx.appsearch.platformstorage.converter.AppSearchResultToPlatformConverter;
import androidx.appsearch.platformstorage.converter.RequestToPlatformConverter;
import androidx.appsearch.platformstorage.converter.ResponseToPlatformConverter;
import androidx.appsearch.platformstorage.converter.SearchSpecToPlatformConverter;
import androidx.appsearch.platformstorage.converter.SearchSuggestionResultToPlatformConverter;
import androidx.appsearch.platformstorage.converter.SearchSuggestionSpecToPlatformConverter;
import androidx.appsearch.platformstorage.converter.SetSchemaRequestToPlatformConverter;
import androidx.appsearch.platformstorage.util.AppSearchVersionUtil;
import androidx.appsearch.platformstorage.util.BatchResultCallbackAdapter;
import androidx.appsearch.platformstorage.util.SchemaValidationUtil;
import androidx.concurrent.futures.ResolvableFuture;
import androidx.core.util.Preconditions;
import com.google.common.util.concurrent.ListenableFuture;
import java.util.List;
import java.util.Set;
import java.util.concurrent.Executor;
import java.util.function.Consumer;
import java.util.function.Function;

/* JADX INFO: loaded from: classes.dex */
class SearchSessionImpl implements AppSearchSession {
    private final Context mContext;
    private final Executor mExecutor;
    private final Features mFeatures;
    private final android.app.appsearch.AppSearchSession mPlatformSession;

    SearchSessionImpl(android.app.appsearch.AppSearchSession appSearchSession, Executor executor, Context context) {
        this.mPlatformSession = (android.app.appsearch.AppSearchSession) Preconditions.checkNotNull(appSearchSession);
        this.mExecutor = (Executor) Preconditions.checkNotNull(executor);
        Context context2 = (Context) Preconditions.checkNotNull(context);
        this.mContext = context2;
        this.mFeatures = new FeaturesImpl(context2);
    }

    @Override // androidx.appsearch.app.AppSearchSession
    public ListenableFuture<SetSchemaResponse> setSchemaAsync(SetSchemaRequest setSchemaRequest) {
        Preconditions.checkNotNull(setSchemaRequest);
        final ResolvableFuture resolvableFutureCreate = ResolvableFuture.create();
        if (needsSchemaValidation()) {
            try {
                SchemaValidationUtil.checkSchemasAreValidOrThrow(setSchemaRequest.getSchemas(), getFeatures().getMaxIndexedProperties());
            } catch (IllegalSchemaException e) {
                resolvableFutureCreate.setException(new AppSearchException(3, e.getMessage()));
                return resolvableFutureCreate;
            }
        }
        android.app.appsearch.AppSearchSession appSearchSession = this.mPlatformSession;
        android.app.appsearch.SetSchemaRequest platformSetSchemaRequest = SetSchemaRequestToPlatformConverter.toPlatformSetSchemaRequest(setSchemaRequest);
        Executor executor = this.mExecutor;
        appSearchSession.setSchema(platformSetSchemaRequest, executor, executor, new Consumer() { // from class: androidx.appsearch.platformstorage.SearchSessionImpl$$ExternalSyntheticLambda11
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                AppSearchResultToPlatformConverter.platformAppSearchResultToFuture((AppSearchResult) obj, resolvableFutureCreate, new Function() { // from class: androidx.appsearch.platformstorage.SearchSessionImpl$$ExternalSyntheticLambda2
                    @Override // java.util.function.Function
                    public final Object apply(Object obj2) {
                        return SetSchemaRequestToPlatformConverter.toJetpackSetSchemaResponse((android.app.appsearch.SetSchemaResponse) obj2);
                    }
                });
            }
        });
        return resolvableFutureCreate;
    }

    @Override // androidx.appsearch.app.AppSearchSession
    public ListenableFuture<GetSchemaResponse> getSchemaAsync() {
        final ResolvableFuture resolvableFutureCreate = ResolvableFuture.create();
        this.mPlatformSession.getSchema(this.mExecutor, new Consumer() { // from class: androidx.appsearch.platformstorage.SearchSessionImpl$$ExternalSyntheticLambda5
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                AppSearchResultToPlatformConverter.platformAppSearchResultToFuture((AppSearchResult) obj, resolvableFutureCreate, new EnterpriseGlobalSearchSessionImpl$$ExternalSyntheticLambda2());
            }
        });
        return resolvableFutureCreate;
    }

    @Override // androidx.appsearch.app.AppSearchSession
    public ListenableFuture<Set<String>> getNamespacesAsync() {
        final ResolvableFuture resolvableFutureCreate = ResolvableFuture.create();
        this.mPlatformSession.getNamespaces(this.mExecutor, new Consumer() { // from class: androidx.appsearch.platformstorage.SearchSessionImpl$$ExternalSyntheticLambda9
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                AppSearchResultToPlatformConverter.platformAppSearchResultToFuture((AppSearchResult) obj, resolvableFutureCreate);
            }
        });
        return resolvableFutureCreate;
    }

    @Override // androidx.appsearch.app.AppSearchSession
    public ListenableFuture<AppSearchBatchResult<String, Void>> putAsync(PutDocumentsRequest putDocumentsRequest) {
        Preconditions.checkNotNull(putDocumentsRequest);
        ResolvableFuture resolvableFutureCreate = ResolvableFuture.create();
        this.mPlatformSession.put(RequestToPlatformConverter.toPlatformPutDocumentsRequest(putDocumentsRequest), this.mExecutor, BatchResultCallbackAdapter.forSameValueType(resolvableFutureCreate));
        return resolvableFutureCreate;
    }

    @Override // androidx.appsearch.app.AppSearchSession
    public ListenableFuture<AppSearchBatchResult<String, GenericDocument>> getByDocumentIdAsync(GetByDocumentIdRequest getByDocumentIdRequest) {
        Preconditions.checkNotNull(getByDocumentIdRequest);
        ResolvableFuture resolvableFutureCreate = ResolvableFuture.create();
        this.mPlatformSession.getByDocumentId(RequestToPlatformConverter.toPlatformGetByDocumentIdRequest(getByDocumentIdRequest), this.mExecutor, new BatchResultCallbackAdapter(resolvableFutureCreate, new EnterpriseGlobalSearchSessionImpl$$ExternalSyntheticLambda0()));
        return resolvableFutureCreate;
    }

    @Override // androidx.appsearch.app.AppSearchSession
    public SearchResults search(String str, SearchSpec searchSpec) {
        Preconditions.checkNotNull(str);
        Preconditions.checkNotNull(searchSpec);
        return new SearchResultsImpl(this.mPlatformSession.search(str, SearchSpecToPlatformConverter.toPlatformSearchSpec(this.mContext, searchSpec)), searchSpec, this.mExecutor, this.mContext);
    }

    @Override // androidx.appsearch.app.AppSearchSession
    public ListenableFuture<List<SearchSuggestionResult>> searchSuggestionAsync(String str, SearchSuggestionSpec searchSuggestionSpec) {
        Preconditions.checkNotNull(str);
        Preconditions.checkNotNull(searchSuggestionSpec);
        if (Build.VERSION.SDK_INT >= 34) {
            final ResolvableFuture resolvableFutureCreate = ResolvableFuture.create();
            ApiHelperForU.searchSuggestion(this.mPlatformSession, str, SearchSuggestionSpecToPlatformConverter.toPlatformSearchSuggestionSpec(searchSuggestionSpec), this.mExecutor, new Consumer() { // from class: androidx.appsearch.platformstorage.SearchSessionImpl$$ExternalSyntheticLambda0
                @Override // java.util.function.Consumer
                public final void accept(Object obj) {
                    AppSearchResultToPlatformConverter.platformAppSearchResultToFuture((AppSearchResult) obj, resolvableFutureCreate, new Function() { // from class: androidx.appsearch.platformstorage.SearchSessionImpl$$ExternalSyntheticLambda10
                        @Override // java.util.function.Function
                        public final Object apply(Object obj2) {
                            return SearchSuggestionResultToPlatformConverter.toJetpackSearchSuggestionResults((List) obj2);
                        }
                    });
                }
            });
            return resolvableFutureCreate;
        }
        throw new UnsupportedOperationException("Search Suggestion is not supported on this AppSearch implementation.");
    }

    @Override // androidx.appsearch.app.AppSearchSession
    public ListenableFuture<Void> reportUsageAsync(ReportUsageRequest reportUsageRequest) {
        Preconditions.checkNotNull(reportUsageRequest);
        final ResolvableFuture resolvableFutureCreate = ResolvableFuture.create();
        this.mPlatformSession.reportUsage(RequestToPlatformConverter.toPlatformReportUsageRequest(reportUsageRequest), this.mExecutor, new Consumer() { // from class: androidx.appsearch.platformstorage.SearchSessionImpl$$ExternalSyntheticLambda4
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                AppSearchResultToPlatformConverter.platformAppSearchResultToFuture((AppSearchResult) obj, resolvableFutureCreate);
            }
        });
        return resolvableFutureCreate;
    }

    @Override // androidx.appsearch.app.AppSearchSession
    public ListenableFuture<AppSearchBatchResult<String, Void>> removeAsync(RemoveByDocumentIdRequest removeByDocumentIdRequest) {
        Preconditions.checkNotNull(removeByDocumentIdRequest);
        ResolvableFuture resolvableFutureCreate = ResolvableFuture.create();
        this.mPlatformSession.remove(RequestToPlatformConverter.toPlatformRemoveByDocumentIdRequest(removeByDocumentIdRequest), this.mExecutor, BatchResultCallbackAdapter.forSameValueType(resolvableFutureCreate));
        return resolvableFutureCreate;
    }

    @Override // androidx.appsearch.app.AppSearchSession
    public ListenableFuture<Void> removeAsync(final String str, final SearchSpec searchSpec) {
        Preconditions.checkNotNull(str);
        Preconditions.checkNotNull(searchSpec);
        final ResolvableFuture resolvableFutureCreate = ResolvableFuture.create();
        if (searchSpec.getJoinSpec() != null) {
            throw new IllegalArgumentException("JoinSpec not allowed in removeByQuery, but JoinSpec was provided.");
        }
        if (Build.VERSION.SDK_INT < 33 && !searchSpec.getFilterNamespaces().isEmpty()) {
            this.mPlatformSession.getNamespaces(this.mExecutor, new Consumer() { // from class: androidx.appsearch.platformstorage.SearchSessionImpl$$ExternalSyntheticLambda7
                @Override // java.util.function.Consumer
                public final void accept(Object obj) {
                    this.f$0.m16xf162c8d8(resolvableFutureCreate, searchSpec, str, (AppSearchResult) obj);
                }
            });
            return resolvableFutureCreate;
        }
        this.mPlatformSession.remove(str, SearchSpecToPlatformConverter.toPlatformSearchSpec(this.mContext, searchSpec), this.mExecutor, new Consumer() { // from class: androidx.appsearch.platformstorage.SearchSessionImpl$$ExternalSyntheticLambda8
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                AppSearchResultToPlatformConverter.platformAppSearchResultToFuture((AppSearchResult) obj, resolvableFutureCreate);
            }
        });
        return resolvableFutureCreate;
    }

    /* JADX INFO: renamed from: lambda$removeAsync$6$androidx-appsearch-platformstorage-SearchSessionImpl, reason: not valid java name */
    /* synthetic */ void m16xf162c8d8(final ResolvableFuture resolvableFuture, SearchSpec searchSpec, String str, AppSearchResult appSearchResult) {
        if (!appSearchResult.isSuccess()) {
            resolvableFuture.setException(new AppSearchException(appSearchResult.getResultCode(), appSearchResult.getErrorMessage()));
            return;
        }
        try {
            Set set = (Set) appSearchResult.getResultValue();
            List<String> filterNamespaces = searchSpec.getFilterNamespaces();
            for (int i = 0; i < filterNamespaces.size(); i++) {
                if (set.contains(filterNamespaces.get(i))) {
                    this.mPlatformSession.remove(str, SearchSpecToPlatformConverter.toPlatformSearchSpec(this.mContext, searchSpec), this.mExecutor, new Consumer() { // from class: androidx.appsearch.platformstorage.SearchSessionImpl$$ExternalSyntheticLambda3
                        @Override // java.util.function.Consumer
                        public final void accept(Object obj) {
                            AppSearchResultToPlatformConverter.platformAppSearchResultToFuture((AppSearchResult) obj, resolvableFuture);
                        }
                    });
                    return;
                }
            }
            resolvableFuture.set(null);
        } catch (Throwable th) {
            resolvableFuture.setException(th);
        }
    }

    @Override // androidx.appsearch.app.AppSearchSession
    public ListenableFuture<StorageInfo> getStorageInfoAsync() {
        final ResolvableFuture resolvableFutureCreate = ResolvableFuture.create();
        this.mPlatformSession.getStorageInfo(this.mExecutor, new Consumer() { // from class: androidx.appsearch.platformstorage.SearchSessionImpl$$ExternalSyntheticLambda6
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                AppSearchResultToPlatformConverter.platformAppSearchResultToFuture((AppSearchResult) obj, resolvableFutureCreate, new Function() { // from class: androidx.appsearch.platformstorage.SearchSessionImpl$$ExternalSyntheticLambda1
                    @Override // java.util.function.Function
                    public final Object apply(Object obj2) {
                        return ResponseToPlatformConverter.toJetpackStorageInfo((android.app.appsearch.StorageInfo) obj2);
                    }
                });
            }
        });
        return resolvableFutureCreate;
    }

    @Override // androidx.appsearch.app.AppSearchSession
    public ListenableFuture<Void> requestFlushAsync() {
        ResolvableFuture resolvableFutureCreate = ResolvableFuture.create();
        resolvableFutureCreate.set(null);
        return resolvableFutureCreate;
    }

    @Override // androidx.appsearch.app.AppSearchSession
    public Features getFeatures() {
        return this.mFeatures;
    }

    @Override // androidx.appsearch.app.AppSearchSession, java.io.Closeable, java.lang.AutoCloseable
    public void close() {
        this.mPlatformSession.close();
    }

    private boolean needsSchemaValidation() {
        long appSearchVersionCode = AppSearchVersionUtil.getAppSearchVersionCode(this.mContext);
        return appSearchVersionCode >= AppSearchVersionUtil.APPSEARCH_U_BASE_VERSION_CODE && appSearchVersionCode < AppSearchVersionUtil.APPSEARCH_M2023_11_VERSION_CODE;
    }

    static class ApiHelperForU {
        private ApiHelperForU() {
        }

        static void searchSuggestion(android.app.appsearch.AppSearchSession appSearchSession, String str, android.app.appsearch.SearchSuggestionSpec searchSuggestionSpec, Executor executor, Consumer<AppSearchResult<List<android.app.appsearch.SearchSuggestionResult>>> consumer) {
            appSearchSession.searchSuggestion(str, searchSuggestionSpec, executor, consumer);
        }
    }
}
