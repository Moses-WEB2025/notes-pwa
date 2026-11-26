package androidx.appsearch.platformstorage;

import android.app.appsearch.AppSearchResult;
import android.app.appsearch.BatchResultCallback;
import android.app.appsearch.observer.DocumentChangeInfo;
import android.app.appsearch.observer.SchemaChangeInfo;
import android.content.Context;
import android.os.Build;
import androidx.appsearch.app.AppSearchBatchResult;
import androidx.appsearch.app.Features;
import androidx.appsearch.app.GenericDocument;
import androidx.appsearch.app.GetByDocumentIdRequest;
import androidx.appsearch.app.GetSchemaResponse;
import androidx.appsearch.app.GlobalSearchSession;
import androidx.appsearch.app.ReportSystemUsageRequest;
import androidx.appsearch.app.SearchResults;
import androidx.appsearch.app.SearchSpec;
import androidx.appsearch.exceptions.AppSearchException;
import androidx.appsearch.observer.ObserverCallback;
import androidx.appsearch.observer.ObserverSpec;
import androidx.appsearch.platformstorage.converter.AppSearchResultToPlatformConverter;
import androidx.appsearch.platformstorage.converter.ObserverSpecToPlatformConverter;
import androidx.appsearch.platformstorage.converter.RequestToPlatformConverter;
import androidx.appsearch.platformstorage.converter.SearchSpecToPlatformConverter;
import androidx.appsearch.platformstorage.util.BatchResultCallbackAdapter;
import androidx.collection.ArrayMap;
import androidx.concurrent.futures.ResolvableFuture;
import androidx.core.util.Preconditions;
import com.google.common.util.concurrent.ListenableFuture;
import java.util.Map;
import java.util.concurrent.Executor;
import java.util.function.Consumer;

/* JADX INFO: loaded from: classes.dex */
class GlobalSearchSessionImpl implements GlobalSearchSession {
    private final Context mContext;
    private final Executor mExecutor;
    private final Features mFeatures;
    private final Map<ObserverCallback, android.app.appsearch.observer.ObserverCallback> mObserverCallbacksLocked = new ArrayMap();
    private final android.app.appsearch.GlobalSearchSession mPlatformSession;

    GlobalSearchSessionImpl(android.app.appsearch.GlobalSearchSession globalSearchSession, Executor executor, Context context) {
        this.mPlatformSession = (android.app.appsearch.GlobalSearchSession) Preconditions.checkNotNull(globalSearchSession);
        this.mExecutor = (Executor) Preconditions.checkNotNull(executor);
        Context context2 = (Context) Preconditions.checkNotNull(context);
        this.mContext = context2;
        this.mFeatures = new FeaturesImpl(context2);
    }

    @Override // androidx.appsearch.app.GlobalSearchSession
    public ListenableFuture<AppSearchBatchResult<String, GenericDocument>> getByDocumentIdAsync(String str, String str2, GetByDocumentIdRequest getByDocumentIdRequest) {
        if (Build.VERSION.SDK_INT < 33) {
            throw new UnsupportedOperationException("GLOBAL_SEARCH_SESSION_GET_BY_ID is not supported on this AppSearch implementation.");
        }
        Preconditions.checkNotNull(str);
        Preconditions.checkNotNull(str2);
        Preconditions.checkNotNull(getByDocumentIdRequest);
        ResolvableFuture resolvableFutureCreate = ResolvableFuture.create();
        ApiHelperForT.getByDocumentId(this.mPlatformSession, str, str2, RequestToPlatformConverter.toPlatformGetByDocumentIdRequest(getByDocumentIdRequest), this.mExecutor, new BatchResultCallbackAdapter(resolvableFutureCreate, new EnterpriseGlobalSearchSessionImpl$$ExternalSyntheticLambda0()));
        return resolvableFutureCreate;
    }

    @Override // androidx.appsearch.app.GlobalSearchSession
    public SearchResults search(String str, SearchSpec searchSpec) {
        Preconditions.checkNotNull(str);
        Preconditions.checkNotNull(searchSpec);
        return new SearchResultsImpl(this.mPlatformSession.search(str, SearchSpecToPlatformConverter.toPlatformSearchSpec(this.mContext, searchSpec)), searchSpec, this.mExecutor, this.mContext);
    }

    @Override // androidx.appsearch.app.GlobalSearchSession
    public ListenableFuture<Void> reportSystemUsageAsync(ReportSystemUsageRequest reportSystemUsageRequest) {
        Preconditions.checkNotNull(reportSystemUsageRequest);
        final ResolvableFuture resolvableFutureCreate = ResolvableFuture.create();
        this.mPlatformSession.reportSystemUsage(RequestToPlatformConverter.toPlatformReportSystemUsageRequest(reportSystemUsageRequest), this.mExecutor, new Consumer() { // from class: androidx.appsearch.platformstorage.GlobalSearchSessionImpl$$ExternalSyntheticLambda1
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                AppSearchResultToPlatformConverter.platformAppSearchResultToFuture((AppSearchResult) obj, resolvableFutureCreate);
            }
        });
        return resolvableFutureCreate;
    }

    @Override // androidx.appsearch.app.GlobalSearchSession
    public ListenableFuture<GetSchemaResponse> getSchemaAsync(String str, String str2) {
        if (Build.VERSION.SDK_INT < 33) {
            throw new UnsupportedOperationException("GLOBAL_SEARCH_SESSION_GET_SCHEMA is not supported on this AppSearch implementation.");
        }
        final ResolvableFuture resolvableFutureCreate = ResolvableFuture.create();
        ApiHelperForT.getSchema(this.mPlatformSession, str, str2, this.mExecutor, new Consumer() { // from class: androidx.appsearch.platformstorage.GlobalSearchSessionImpl$$ExternalSyntheticLambda0
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                AppSearchResultToPlatformConverter.platformAppSearchResultToFuture((AppSearchResult) obj, resolvableFutureCreate, new EnterpriseGlobalSearchSessionImpl$$ExternalSyntheticLambda2());
            }
        });
        return resolvableFutureCreate;
    }

    @Override // androidx.appsearch.app.GlobalSearchSession
    public Features getFeatures() {
        return this.mFeatures;
    }

    @Override // androidx.appsearch.app.GlobalSearchSession
    public void registerObserverCallback(String str, ObserverSpec observerSpec, Executor executor, final ObserverCallback observerCallback) throws AppSearchException {
        Preconditions.checkNotNull(str);
        Preconditions.checkNotNull(observerSpec);
        Preconditions.checkNotNull(executor);
        Preconditions.checkNotNull(observerCallback);
        if (Build.VERSION.SDK_INT < 33) {
            throw new UnsupportedOperationException("GLOBAL_SEARCH_SESSION_REGISTER_OBSERVER_CALLBACK is not supported on this AppSearch implementation");
        }
        synchronized (this.mObserverCallbacksLocked) {
            android.app.appsearch.observer.ObserverCallback observerCallback2 = this.mObserverCallbacksLocked.get(observerCallback);
            if (observerCallback2 == null) {
                observerCallback2 = new android.app.appsearch.observer.ObserverCallback() { // from class: androidx.appsearch.platformstorage.GlobalSearchSessionImpl.1
                    @Override // android.app.appsearch.observer.ObserverCallback
                    public void onSchemaChanged(SchemaChangeInfo schemaChangeInfo) {
                        observerCallback.onSchemaChanged(ObserverSpecToPlatformConverter.toJetpackSchemaChangeInfo(schemaChangeInfo));
                    }

                    @Override // android.app.appsearch.observer.ObserverCallback
                    public void onDocumentChanged(DocumentChangeInfo documentChangeInfo) {
                        observerCallback.onDocumentChanged(ObserverSpecToPlatformConverter.toJetpackDocumentChangeInfo(documentChangeInfo));
                    }
                };
            }
            try {
                ApiHelperForT.registerObserverCallback(this.mPlatformSession, str, ObserverSpecToPlatformConverter.toPlatformObserverSpec(observerSpec), executor, observerCallback2);
                this.mObserverCallbacksLocked.put(observerCallback, observerCallback2);
            } catch (android.app.appsearch.exceptions.AppSearchException e) {
                throw new AppSearchException(e.getResultCode(), e.getMessage(), e.getCause());
            }
        }
    }

    @Override // androidx.appsearch.app.GlobalSearchSession
    public void unregisterObserverCallback(String str, ObserverCallback observerCallback) throws AppSearchException {
        Preconditions.checkNotNull(str);
        Preconditions.checkNotNull(observerCallback);
        if (Build.VERSION.SDK_INT < 33) {
            throw new UnsupportedOperationException("GLOBAL_SEARCH_SESSION_REGISTER_OBSERVER_CALLBACK is not supported on this AppSearch implementation");
        }
        synchronized (this.mObserverCallbacksLocked) {
            android.app.appsearch.observer.ObserverCallback observerCallback2 = this.mObserverCallbacksLocked.get(observerCallback);
            if (observerCallback2 == null) {
                return;
            }
            try {
                ApiHelperForT.unregisterObserverCallback(this.mPlatformSession, str, observerCallback2);
                this.mObserverCallbacksLocked.remove(observerCallback);
            } catch (android.app.appsearch.exceptions.AppSearchException e) {
                throw new AppSearchException(e.getResultCode(), e.getMessage(), e.getCause());
            }
        }
    }

    @Override // androidx.appsearch.app.GlobalSearchSession, java.io.Closeable, java.lang.AutoCloseable
    public void close() {
        this.mPlatformSession.close();
    }

    private static class ApiHelperForT {
        private ApiHelperForT() {
        }

        static void getByDocumentId(android.app.appsearch.GlobalSearchSession globalSearchSession, String str, String str2, android.app.appsearch.GetByDocumentIdRequest getByDocumentIdRequest, Executor executor, BatchResultCallback<String, android.app.appsearch.GenericDocument> batchResultCallback) {
            globalSearchSession.getByDocumentId(str, str2, getByDocumentIdRequest, executor, batchResultCallback);
        }

        static void getSchema(android.app.appsearch.GlobalSearchSession globalSearchSession, String str, String str2, Executor executor, Consumer<AppSearchResult<android.app.appsearch.GetSchemaResponse>> consumer) {
            globalSearchSession.getSchema(str, str2, executor, consumer);
        }

        static void registerObserverCallback(android.app.appsearch.GlobalSearchSession globalSearchSession, String str, android.app.appsearch.observer.ObserverSpec observerSpec, Executor executor, android.app.appsearch.observer.ObserverCallback observerCallback) throws android.app.appsearch.exceptions.AppSearchException {
            globalSearchSession.registerObserverCallback(str, observerSpec, executor, observerCallback);
        }

        static void unregisterObserverCallback(android.app.appsearch.GlobalSearchSession globalSearchSession, String str, android.app.appsearch.observer.ObserverCallback observerCallback) throws android.app.appsearch.exceptions.AppSearchException {
            globalSearchSession.unregisterObserverCallback(str, observerCallback);
        }
    }
}
