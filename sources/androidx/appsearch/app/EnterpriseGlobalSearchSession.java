package androidx.appsearch.app;

import com.google.common.util.concurrent.ListenableFuture;

/* JADX INFO: loaded from: classes.dex */
public interface EnterpriseGlobalSearchSession {
    ListenableFuture<AppSearchBatchResult<String, GenericDocument>> getByDocumentIdAsync(String str, String str2, GetByDocumentIdRequest getByDocumentIdRequest);

    Features getFeatures();

    ListenableFuture<GetSchemaResponse> getSchemaAsync(String str, String str2);

    SearchResults search(String str, SearchSpec searchSpec);
}
