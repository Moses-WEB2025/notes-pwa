package androidx.appsearch.platformstorage;

import android.app.appsearch.AppSearchResult;
import android.content.Context;
import android.os.Build;
import androidx.appsearch.app.GenericDocument;
import androidx.appsearch.app.SearchResult;
import androidx.appsearch.app.SearchResults;
import androidx.appsearch.app.SearchSpec;
import androidx.appsearch.exceptions.AppSearchException;
import androidx.appsearch.platformstorage.converter.SearchResultToPlatformConverter;
import androidx.appsearch.platformstorage.util.AppSearchVersionUtil;
import androidx.collection.ArraySet;
import androidx.concurrent.futures.ResolvableFuture;
import androidx.core.util.Preconditions;
import com.google.common.util.concurrent.ListenableFuture;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.Executor;
import java.util.function.Consumer;

/* JADX INFO: loaded from: classes.dex */
class SearchResultsImpl implements SearchResults {
    private final Context mContext;
    private final Executor mExecutor;
    private final android.app.appsearch.SearchResults mPlatformResults;
    private final SearchSpec mSearchSpec;

    SearchResultsImpl(android.app.appsearch.SearchResults searchResults, SearchSpec searchSpec, Executor executor, Context context) {
        this.mPlatformResults = (android.app.appsearch.SearchResults) Preconditions.checkNotNull(searchResults);
        this.mSearchSpec = (SearchSpec) Preconditions.checkNotNull(searchSpec);
        this.mExecutor = (Executor) Preconditions.checkNotNull(executor);
        this.mContext = (Context) Preconditions.checkNotNull(context);
    }

    @Override // androidx.appsearch.app.SearchResults
    public ListenableFuture<List<SearchResult>> getNextPageAsync() {
        final ResolvableFuture resolvableFutureCreate = ResolvableFuture.create();
        this.mPlatformResults.getNextPage(this.mExecutor, new Consumer() { // from class: androidx.appsearch.platformstorage.SearchResultsImpl$$ExternalSyntheticLambda0
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                this.f$0.m15xec5cbb8(resolvableFutureCreate, (AppSearchResult) obj);
            }
        });
        return resolvableFutureCreate;
    }

    /* JADX INFO: renamed from: lambda$getNextPageAsync$0$androidx-appsearch-platformstorage-SearchResultsImpl, reason: not valid java name */
    /* synthetic */ void m15xec5cbb8(ResolvableFuture resolvableFuture, AppSearchResult appSearchResult) {
        if (appSearchResult.isSuccess()) {
            List list = (List) appSearchResult.getResultValue();
            ArrayList arrayList = new ArrayList(list.size());
            Map<String, List<String>> projections = this.mSearchSpec.getProjections();
            boolean z = (Build.VERSION.SDK_INT < 34) && ((AppSearchVersionUtil.getAppSearchVersionCode(this.mContext) > AppSearchVersionUtil.APPSEARCH_U_BASE_VERSION_CODE ? 1 : (AppSearchVersionUtil.getAppSearchVersionCode(this.mContext) == AppSearchVersionUtil.APPSEARCH_U_BASE_VERSION_CODE ? 0 : -1)) < 0) && !projections.isEmpty();
            for (int i = 0; i < list.size(); i++) {
                if ((Build.VERSION.SDK_INT == 31 || Build.VERSION.SDK_INT == 32) && i == 0 && !this.mSearchSpec.getFilterNamespaces().isEmpty() && !this.mSearchSpec.getFilterNamespaces().contains(((android.app.appsearch.SearchResult) list.get(i)).getGenericDocument().getNamespace())) {
                    resolvableFuture.set(Collections.emptyList());
                    return;
                }
                SearchResult jetpackSearchResult = SearchResultToPlatformConverter.toJetpackSearchResult((android.app.appsearch.SearchResult) list.get(i));
                if (z) {
                    arrayList.add(manuallyApplyProjection(jetpackSearchResult, projections));
                } else {
                    arrayList.add(jetpackSearchResult);
                }
            }
            resolvableFuture.set(arrayList);
            return;
        }
        resolvableFuture.setException(new AppSearchException(appSearchResult.getResultCode(), appSearchResult.getErrorMessage()));
    }

    @Override // androidx.appsearch.app.SearchResults, java.io.Closeable, java.lang.AutoCloseable
    public void close() {
        this.mPlatformResults.close();
    }

    private SearchResult manuallyApplyProjection(SearchResult searchResult, Map<String, List<String>> map) {
        ArraySet arraySet;
        GenericDocument genericDocument = searchResult.getGenericDocument();
        GenericDocument.Builder builder = new GenericDocument.Builder(genericDocument);
        List<String> list = map.get(genericDocument.getSchemaType());
        List<String> list2 = map.get("*");
        if (list != null) {
            arraySet = new ArraySet(list);
        } else {
            arraySet = list2 != null ? new ArraySet(list2) : null;
        }
        if (arraySet != null) {
            for (String str : genericDocument.getPropertyNames()) {
                GenericDocument propertyDocument = genericDocument.getPropertyDocument(str);
                if (propertyDocument != null) {
                    applyProjectionToNestedDocument(builder, propertyDocument, str, arraySet);
                } else if (!arraySet.contains(str)) {
                    builder.clearProperty(str);
                }
            }
        }
        SearchResult.Builder genericDocument2 = new SearchResult.Builder(searchResult).setGenericDocument(builder.build());
        List<SearchResult.MatchInfo> matchInfos = searchResult.getMatchInfos();
        if (!matchInfos.isEmpty()) {
            genericDocument2.clearMatchInfos();
        }
        for (SearchResult.MatchInfo matchInfo : matchInfos) {
            String propertyPath = matchInfo.getPropertyPath();
            boolean z = list != null && list.contains(propertyPath);
            boolean z2 = list2 != null && list2.contains(propertyPath);
            if (z || z2) {
                genericDocument2.addMatchInfo(new SearchResult.MatchInfo.Builder(matchInfo).build());
            }
        }
        return genericDocument2.build();
    }

    private void applyProjectionToNestedDocument(GenericDocument.Builder builder, GenericDocument genericDocument, String str, Set<String> set) {
        ArraySet arraySet = new ArraySet();
        for (String str2 : set) {
            if (str2.startsWith(str + ".")) {
                arraySet.add(str2.substring(str.length() + 1));
            }
        }
        if (arraySet.isEmpty()) {
            return;
        }
        GenericDocument.Builder builder2 = new GenericDocument.Builder(genericDocument);
        for (String str3 : genericDocument.getPropertyNames()) {
            GenericDocument propertyDocument = genericDocument.getPropertyDocument(str3);
            if (propertyDocument != null) {
                applyProjectionToNestedDocument(builder2, propertyDocument, str3, arraySet);
            } else if (!arraySet.contains(str3)) {
                builder2.clearProperty(str3);
            }
        }
        builder.setPropertyDocument(str, builder2.build());
    }
}
