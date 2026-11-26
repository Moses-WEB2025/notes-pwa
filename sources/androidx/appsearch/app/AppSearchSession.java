package androidx.appsearch.app;

import com.google.common.util.concurrent.ListenableFuture;
import java.io.Closeable;
import java.util.List;
import java.util.Set;

/* JADX INFO: loaded from: classes.dex */
public interface AppSearchSession extends Closeable {
    @Override // java.io.Closeable, java.lang.AutoCloseable
    void close();

    ListenableFuture<AppSearchBatchResult<String, GenericDocument>> getByDocumentIdAsync(GetByDocumentIdRequest getByDocumentIdRequest);

    Features getFeatures();

    ListenableFuture<Set<String>> getNamespacesAsync();

    ListenableFuture<GetSchemaResponse> getSchemaAsync();

    ListenableFuture<StorageInfo> getStorageInfoAsync();

    ListenableFuture<AppSearchBatchResult<String, Void>> putAsync(PutDocumentsRequest putDocumentsRequest);

    ListenableFuture<AppSearchBatchResult<String, Void>> removeAsync(RemoveByDocumentIdRequest removeByDocumentIdRequest);

    ListenableFuture<Void> removeAsync(String str, SearchSpec searchSpec);

    ListenableFuture<Void> reportUsageAsync(ReportUsageRequest reportUsageRequest);

    ListenableFuture<Void> requestFlushAsync();

    SearchResults search(String str, SearchSpec searchSpec);

    ListenableFuture<List<SearchSuggestionResult>> searchSuggestionAsync(String str, SearchSuggestionSpec searchSuggestionSpec);

    ListenableFuture<SetSchemaResponse> setSchemaAsync(SetSchemaRequest setSchemaRequest);

    default ListenableFuture<OpenBlobForWriteResponse> openBlobForWriteAsync(Set<AppSearchBlobHandle> set) {
        throw new UnsupportedOperationException("BLOB_STORAGE is not available on this AppSearch implementation.");
    }

    default ListenableFuture<RemoveBlobResponse> removeBlobAsync(Set<AppSearchBlobHandle> set) {
        throw new UnsupportedOperationException("BLOB_STORAGE is not available on this AppSearch implementation.");
    }

    default ListenableFuture<CommitBlobResponse> commitBlobAsync(Set<AppSearchBlobHandle> set) {
        throw new UnsupportedOperationException("BLOB_STORAGE is not available on this AppSearch implementation.");
    }

    default ListenableFuture<OpenBlobForReadResponse> openBlobForReadAsync(Set<AppSearchBlobHandle> set) {
        throw new UnsupportedOperationException("BLOB_STORAGE is not available on this AppSearch implementation.");
    }

    default ListenableFuture<Void> setBlobVisibilityAsync(SetBlobVisibilityRequest setBlobVisibilityRequest) {
        throw new UnsupportedOperationException("BLOB_STORAGE is not available on this AppSearch implementation.");
    }
}
