package androidx.appsearch.safeparcel.stub;

import android.os.Parcel;
import androidx.appsearch.app.AppSearchBlobHandle;
import androidx.appsearch.app.AppSearchSchema;
import androidx.appsearch.app.CommitBlobResponse;
import androidx.appsearch.app.EmbeddingVector;
import androidx.appsearch.app.GetByDocumentIdRequest;
import androidx.appsearch.app.GetSchemaResponse;
import androidx.appsearch.app.InternalSetSchemaResponse;
import androidx.appsearch.app.InternalVisibilityConfig;
import androidx.appsearch.app.JoinSpec;
import androidx.appsearch.app.OpenBlobForReadResponse;
import androidx.appsearch.app.OpenBlobForWriteResponse;
import androidx.appsearch.app.RemoveBlobResponse;
import androidx.appsearch.app.RemoveByDocumentIdRequest;
import androidx.appsearch.app.ReportUsageRequest;
import androidx.appsearch.app.SchemaVisibilityConfig;
import androidx.appsearch.app.SearchResult;
import androidx.appsearch.app.SearchResultPage;
import androidx.appsearch.app.SearchSpec;
import androidx.appsearch.app.SearchSuggestionResult;
import androidx.appsearch.app.SearchSuggestionSpec;
import androidx.appsearch.app.SetSchemaResponse;
import androidx.appsearch.app.StorageInfo;
import androidx.appsearch.app.VisibilityPermissionConfig;
import androidx.appsearch.observer.ObserverSpec;
import androidx.appsearch.safeparcel.PropertyConfigParcel;
import androidx.appsearch.stats.SchemaMigrationStats;

/* JADX INFO: loaded from: classes.dex */
public class StubCreators {

    public static class StorageInfoCreator extends AbstractCreator<StorageInfo> {
        @Override // androidx.appsearch.safeparcel.stub.AbstractCreator, android.os.Parcelable.Creator
        public /* bridge */ /* synthetic */ Object createFromParcel(Parcel parcel) {
            return super.createFromParcel(parcel);
        }

        @Override // androidx.appsearch.safeparcel.stub.AbstractCreator, android.os.Parcelable.Creator
        public /* bridge */ /* synthetic */ Object[] newArray(int i) {
            return super.newArray(i);
        }
    }

    public static class PropertyConfigParcelCreator extends AbstractCreator<PropertyConfigParcel> {
        @Override // androidx.appsearch.safeparcel.stub.AbstractCreator, android.os.Parcelable.Creator
        public /* bridge */ /* synthetic */ Object createFromParcel(Parcel parcel) {
            return super.createFromParcel(parcel);
        }

        @Override // androidx.appsearch.safeparcel.stub.AbstractCreator, android.os.Parcelable.Creator
        public /* bridge */ /* synthetic */ Object[] newArray(int i) {
            return super.newArray(i);
        }
    }

    public static class JoinableConfigParcelCreator extends AbstractCreator<PropertyConfigParcel.JoinableConfigParcel> {
        @Override // androidx.appsearch.safeparcel.stub.AbstractCreator, android.os.Parcelable.Creator
        public /* bridge */ /* synthetic */ Object createFromParcel(Parcel parcel) {
            return super.createFromParcel(parcel);
        }

        @Override // androidx.appsearch.safeparcel.stub.AbstractCreator, android.os.Parcelable.Creator
        public /* bridge */ /* synthetic */ Object[] newArray(int i) {
            return super.newArray(i);
        }
    }

    public static class StringIndexingConfigParcelCreator extends AbstractCreator<PropertyConfigParcel.StringIndexingConfigParcel> {
        @Override // androidx.appsearch.safeparcel.stub.AbstractCreator, android.os.Parcelable.Creator
        public /* bridge */ /* synthetic */ Object createFromParcel(Parcel parcel) {
            return super.createFromParcel(parcel);
        }

        @Override // androidx.appsearch.safeparcel.stub.AbstractCreator, android.os.Parcelable.Creator
        public /* bridge */ /* synthetic */ Object[] newArray(int i) {
            return super.newArray(i);
        }
    }

    public static class IntegerIndexingConfigParcelCreator extends AbstractCreator<PropertyConfigParcel.IntegerIndexingConfigParcel> {
        @Override // androidx.appsearch.safeparcel.stub.AbstractCreator, android.os.Parcelable.Creator
        public /* bridge */ /* synthetic */ Object createFromParcel(Parcel parcel) {
            return super.createFromParcel(parcel);
        }

        @Override // androidx.appsearch.safeparcel.stub.AbstractCreator, android.os.Parcelable.Creator
        public /* bridge */ /* synthetic */ Object[] newArray(int i) {
            return super.newArray(i);
        }
    }

    public static class DocumentIndexingConfigParcelCreator extends AbstractCreator<PropertyConfigParcel.DocumentIndexingConfigParcel> {
        @Override // androidx.appsearch.safeparcel.stub.AbstractCreator, android.os.Parcelable.Creator
        public /* bridge */ /* synthetic */ Object createFromParcel(Parcel parcel) {
            return super.createFromParcel(parcel);
        }

        @Override // androidx.appsearch.safeparcel.stub.AbstractCreator, android.os.Parcelable.Creator
        public /* bridge */ /* synthetic */ Object[] newArray(int i) {
            return super.newArray(i);
        }
    }

    public static class VisibilityConfigCreator extends AbstractCreator<SchemaVisibilityConfig> {
        @Override // androidx.appsearch.safeparcel.stub.AbstractCreator, android.os.Parcelable.Creator
        public /* bridge */ /* synthetic */ Object createFromParcel(Parcel parcel) {
            return super.createFromParcel(parcel);
        }

        @Override // androidx.appsearch.safeparcel.stub.AbstractCreator, android.os.Parcelable.Creator
        public /* bridge */ /* synthetic */ Object[] newArray(int i) {
            return super.newArray(i);
        }
    }

    public static class EmbeddingIndexingConfigParcelCreator extends AbstractCreator<PropertyConfigParcel.EmbeddingIndexingConfigParcel> {
        @Override // androidx.appsearch.safeparcel.stub.AbstractCreator, android.os.Parcelable.Creator
        public /* bridge */ /* synthetic */ Object createFromParcel(Parcel parcel) {
            return super.createFromParcel(parcel);
        }

        @Override // androidx.appsearch.safeparcel.stub.AbstractCreator, android.os.Parcelable.Creator
        public /* bridge */ /* synthetic */ Object[] newArray(int i) {
            return super.newArray(i);
        }
    }

    public static class InternalVisibilityConfigCreator extends AbstractCreator<InternalVisibilityConfig> {
        @Override // androidx.appsearch.safeparcel.stub.AbstractCreator, android.os.Parcelable.Creator
        public /* bridge */ /* synthetic */ Object createFromParcel(Parcel parcel) {
            return super.createFromParcel(parcel);
        }

        @Override // androidx.appsearch.safeparcel.stub.AbstractCreator, android.os.Parcelable.Creator
        public /* bridge */ /* synthetic */ Object[] newArray(int i) {
            return super.newArray(i);
        }
    }

    public static class VisibilityPermissionConfigCreator extends AbstractCreator<VisibilityPermissionConfig> {
        @Override // androidx.appsearch.safeparcel.stub.AbstractCreator, android.os.Parcelable.Creator
        public /* bridge */ /* synthetic */ Object createFromParcel(Parcel parcel) {
            return super.createFromParcel(parcel);
        }

        @Override // androidx.appsearch.safeparcel.stub.AbstractCreator, android.os.Parcelable.Creator
        public /* bridge */ /* synthetic */ Object[] newArray(int i) {
            return super.newArray(i);
        }
    }

    public static class SchemaMigrationStatsCreator extends AbstractCreator<SchemaMigrationStats> {
        @Override // androidx.appsearch.safeparcel.stub.AbstractCreator, android.os.Parcelable.Creator
        public /* bridge */ /* synthetic */ Object createFromParcel(Parcel parcel) {
            return super.createFromParcel(parcel);
        }

        @Override // androidx.appsearch.safeparcel.stub.AbstractCreator, android.os.Parcelable.Creator
        public /* bridge */ /* synthetic */ Object[] newArray(int i) {
            return super.newArray(i);
        }
    }

    public static class SearchSuggestionResultCreator extends AbstractCreator<SearchSuggestionResult> {
        @Override // androidx.appsearch.safeparcel.stub.AbstractCreator, android.os.Parcelable.Creator
        public /* bridge */ /* synthetic */ Object createFromParcel(Parcel parcel) {
            return super.createFromParcel(parcel);
        }

        @Override // androidx.appsearch.safeparcel.stub.AbstractCreator, android.os.Parcelable.Creator
        public /* bridge */ /* synthetic */ Object[] newArray(int i) {
            return super.newArray(i);
        }
    }

    public static class SearchSuggestionSpecCreator extends AbstractCreator<SearchSuggestionSpec> {
        @Override // androidx.appsearch.safeparcel.stub.AbstractCreator, android.os.Parcelable.Creator
        public /* bridge */ /* synthetic */ Object createFromParcel(Parcel parcel) {
            return super.createFromParcel(parcel);
        }

        @Override // androidx.appsearch.safeparcel.stub.AbstractCreator, android.os.Parcelable.Creator
        public /* bridge */ /* synthetic */ Object[] newArray(int i) {
            return super.newArray(i);
        }
    }

    public static class ObserverSpecCreator extends AbstractCreator<ObserverSpec> {
        @Override // androidx.appsearch.safeparcel.stub.AbstractCreator, android.os.Parcelable.Creator
        public /* bridge */ /* synthetic */ Object createFromParcel(Parcel parcel) {
            return super.createFromParcel(parcel);
        }

        @Override // androidx.appsearch.safeparcel.stub.AbstractCreator, android.os.Parcelable.Creator
        public /* bridge */ /* synthetic */ Object[] newArray(int i) {
            return super.newArray(i);
        }
    }

    public static class SetSchemaResponseCreator extends AbstractCreator<SetSchemaResponse> {
        @Override // androidx.appsearch.safeparcel.stub.AbstractCreator, android.os.Parcelable.Creator
        public /* bridge */ /* synthetic */ Object createFromParcel(Parcel parcel) {
            return super.createFromParcel(parcel);
        }

        @Override // androidx.appsearch.safeparcel.stub.AbstractCreator, android.os.Parcelable.Creator
        public /* bridge */ /* synthetic */ Object[] newArray(int i) {
            return super.newArray(i);
        }
    }

    public static class MigrationFailureCreator extends AbstractCreator<SetSchemaResponse.MigrationFailure> {
        @Override // androidx.appsearch.safeparcel.stub.AbstractCreator, android.os.Parcelable.Creator
        public /* bridge */ /* synthetic */ Object createFromParcel(Parcel parcel) {
            return super.createFromParcel(parcel);
        }

        @Override // androidx.appsearch.safeparcel.stub.AbstractCreator, android.os.Parcelable.Creator
        public /* bridge */ /* synthetic */ Object[] newArray(int i) {
            return super.newArray(i);
        }
    }

    public static class InternalSetSchemaResponseCreator extends AbstractCreator<InternalSetSchemaResponse> {
        @Override // androidx.appsearch.safeparcel.stub.AbstractCreator, android.os.Parcelable.Creator
        public /* bridge */ /* synthetic */ Object createFromParcel(Parcel parcel) {
            return super.createFromParcel(parcel);
        }

        @Override // androidx.appsearch.safeparcel.stub.AbstractCreator, android.os.Parcelable.Creator
        public /* bridge */ /* synthetic */ Object[] newArray(int i) {
            return super.newArray(i);
        }
    }

    public static class SearchSpecCreator extends AbstractCreator<SearchSpec> {
        @Override // androidx.appsearch.safeparcel.stub.AbstractCreator, android.os.Parcelable.Creator
        public /* bridge */ /* synthetic */ Object createFromParcel(Parcel parcel) {
            return super.createFromParcel(parcel);
        }

        @Override // androidx.appsearch.safeparcel.stub.AbstractCreator, android.os.Parcelable.Creator
        public /* bridge */ /* synthetic */ Object[] newArray(int i) {
            return super.newArray(i);
        }
    }

    public static class JoinSpecCreator extends AbstractCreator<JoinSpec> {
        @Override // androidx.appsearch.safeparcel.stub.AbstractCreator, android.os.Parcelable.Creator
        public /* bridge */ /* synthetic */ Object createFromParcel(Parcel parcel) {
            return super.createFromParcel(parcel);
        }

        @Override // androidx.appsearch.safeparcel.stub.AbstractCreator, android.os.Parcelable.Creator
        public /* bridge */ /* synthetic */ Object[] newArray(int i) {
            return super.newArray(i);
        }
    }

    public static class GetSchemaResponseCreator extends AbstractCreator<GetSchemaResponse> {
        @Override // androidx.appsearch.safeparcel.stub.AbstractCreator, android.os.Parcelable.Creator
        public /* bridge */ /* synthetic */ Object createFromParcel(Parcel parcel) {
            return super.createFromParcel(parcel);
        }

        @Override // androidx.appsearch.safeparcel.stub.AbstractCreator, android.os.Parcelable.Creator
        public /* bridge */ /* synthetic */ Object[] newArray(int i) {
            return super.newArray(i);
        }
    }

    public static class AppSearchSchemaCreator extends AbstractCreator<AppSearchSchema> {
        @Override // androidx.appsearch.safeparcel.stub.AbstractCreator, android.os.Parcelable.Creator
        public /* bridge */ /* synthetic */ Object createFromParcel(Parcel parcel) {
            return super.createFromParcel(parcel);
        }

        @Override // androidx.appsearch.safeparcel.stub.AbstractCreator, android.os.Parcelable.Creator
        public /* bridge */ /* synthetic */ Object[] newArray(int i) {
            return super.newArray(i);
        }
    }

    public static class SearchResultCreator extends AbstractCreator<SearchResult> {
        @Override // androidx.appsearch.safeparcel.stub.AbstractCreator, android.os.Parcelable.Creator
        public /* bridge */ /* synthetic */ Object createFromParcel(Parcel parcel) {
            return super.createFromParcel(parcel);
        }

        @Override // androidx.appsearch.safeparcel.stub.AbstractCreator, android.os.Parcelable.Creator
        public /* bridge */ /* synthetic */ Object[] newArray(int i) {
            return super.newArray(i);
        }
    }

    public static class MatchInfoCreator extends AbstractCreator<SearchResult.MatchInfo> {
        @Override // androidx.appsearch.safeparcel.stub.AbstractCreator, android.os.Parcelable.Creator
        public /* bridge */ /* synthetic */ Object createFromParcel(Parcel parcel) {
            return super.createFromParcel(parcel);
        }

        @Override // androidx.appsearch.safeparcel.stub.AbstractCreator, android.os.Parcelable.Creator
        public /* bridge */ /* synthetic */ Object[] newArray(int i) {
            return super.newArray(i);
        }
    }

    public static class TextMatchInfoCreator extends AbstractCreator<SearchResult.TextMatchInfo> {
        @Override // androidx.appsearch.safeparcel.stub.AbstractCreator, android.os.Parcelable.Creator
        public /* bridge */ /* synthetic */ Object createFromParcel(Parcel parcel) {
            return super.createFromParcel(parcel);
        }

        @Override // androidx.appsearch.safeparcel.stub.AbstractCreator, android.os.Parcelable.Creator
        public /* bridge */ /* synthetic */ Object[] newArray(int i) {
            return super.newArray(i);
        }
    }

    public static class EmbeddingMatchInfoCreator extends AbstractCreator<SearchResult.EmbeddingMatchInfo> {
        @Override // androidx.appsearch.safeparcel.stub.AbstractCreator, android.os.Parcelable.Creator
        public /* bridge */ /* synthetic */ Object createFromParcel(Parcel parcel) {
            return super.createFromParcel(parcel);
        }

        @Override // androidx.appsearch.safeparcel.stub.AbstractCreator, android.os.Parcelable.Creator
        public /* bridge */ /* synthetic */ Object[] newArray(int i) {
            return super.newArray(i);
        }
    }

    public static class SearchResultPageCreator extends AbstractCreator<SearchResultPage> {
        @Override // androidx.appsearch.safeparcel.stub.AbstractCreator, android.os.Parcelable.Creator
        public /* bridge */ /* synthetic */ Object createFromParcel(Parcel parcel) {
            return super.createFromParcel(parcel);
        }

        @Override // androidx.appsearch.safeparcel.stub.AbstractCreator, android.os.Parcelable.Creator
        public /* bridge */ /* synthetic */ Object[] newArray(int i) {
            return super.newArray(i);
        }
    }

    public static class RemoveByDocumentIdRequestCreator extends AbstractCreator<RemoveByDocumentIdRequest> {
        @Override // androidx.appsearch.safeparcel.stub.AbstractCreator, android.os.Parcelable.Creator
        public /* bridge */ /* synthetic */ Object createFromParcel(Parcel parcel) {
            return super.createFromParcel(parcel);
        }

        @Override // androidx.appsearch.safeparcel.stub.AbstractCreator, android.os.Parcelable.Creator
        public /* bridge */ /* synthetic */ Object[] newArray(int i) {
            return super.newArray(i);
        }
    }

    public static class ReportUsageRequestCreator extends AbstractCreator<ReportUsageRequest> {
        @Override // androidx.appsearch.safeparcel.stub.AbstractCreator, android.os.Parcelable.Creator
        public /* bridge */ /* synthetic */ Object createFromParcel(Parcel parcel) {
            return super.createFromParcel(parcel);
        }

        @Override // androidx.appsearch.safeparcel.stub.AbstractCreator, android.os.Parcelable.Creator
        public /* bridge */ /* synthetic */ Object[] newArray(int i) {
            return super.newArray(i);
        }
    }

    public static class GetByDocumentIdRequestCreator extends AbstractCreator<GetByDocumentIdRequest> {
        @Override // androidx.appsearch.safeparcel.stub.AbstractCreator, android.os.Parcelable.Creator
        public /* bridge */ /* synthetic */ Object createFromParcel(Parcel parcel) {
            return super.createFromParcel(parcel);
        }

        @Override // androidx.appsearch.safeparcel.stub.AbstractCreator, android.os.Parcelable.Creator
        public /* bridge */ /* synthetic */ Object[] newArray(int i) {
            return super.newArray(i);
        }
    }

    public static class EmbeddingVectorCreator extends AbstractCreator<EmbeddingVector> {
        @Override // androidx.appsearch.safeparcel.stub.AbstractCreator, android.os.Parcelable.Creator
        public /* bridge */ /* synthetic */ Object createFromParcel(Parcel parcel) {
            return super.createFromParcel(parcel);
        }

        @Override // androidx.appsearch.safeparcel.stub.AbstractCreator, android.os.Parcelable.Creator
        public /* bridge */ /* synthetic */ Object[] newArray(int i) {
            return super.newArray(i);
        }
    }

    public static class AppSearchBlobHandleCreator extends AbstractCreator<AppSearchBlobHandle> {
        @Override // androidx.appsearch.safeparcel.stub.AbstractCreator, android.os.Parcelable.Creator
        public /* bridge */ /* synthetic */ Object createFromParcel(Parcel parcel) {
            return super.createFromParcel(parcel);
        }

        @Override // androidx.appsearch.safeparcel.stub.AbstractCreator, android.os.Parcelable.Creator
        public /* bridge */ /* synthetic */ Object[] newArray(int i) {
            return super.newArray(i);
        }
    }

    public static class OpenBlobForWriteResponseCreator extends AbstractCreator<OpenBlobForWriteResponse> {
        @Override // androidx.appsearch.safeparcel.stub.AbstractCreator, android.os.Parcelable.Creator
        public /* bridge */ /* synthetic */ Object createFromParcel(Parcel parcel) {
            return super.createFromParcel(parcel);
        }

        @Override // androidx.appsearch.safeparcel.stub.AbstractCreator, android.os.Parcelable.Creator
        public /* bridge */ /* synthetic */ Object[] newArray(int i) {
            return super.newArray(i);
        }
    }

    public static class CommitBlobResponseCreator extends AbstractCreator<CommitBlobResponse> {
        @Override // androidx.appsearch.safeparcel.stub.AbstractCreator, android.os.Parcelable.Creator
        public /* bridge */ /* synthetic */ Object createFromParcel(Parcel parcel) {
            return super.createFromParcel(parcel);
        }

        @Override // androidx.appsearch.safeparcel.stub.AbstractCreator, android.os.Parcelable.Creator
        public /* bridge */ /* synthetic */ Object[] newArray(int i) {
            return super.newArray(i);
        }
    }

    public static class OpenBlobForReadResponseCreator extends AbstractCreator<OpenBlobForReadResponse> {
        @Override // androidx.appsearch.safeparcel.stub.AbstractCreator, android.os.Parcelable.Creator
        public /* bridge */ /* synthetic */ Object createFromParcel(Parcel parcel) {
            return super.createFromParcel(parcel);
        }

        @Override // androidx.appsearch.safeparcel.stub.AbstractCreator, android.os.Parcelable.Creator
        public /* bridge */ /* synthetic */ Object[] newArray(int i) {
            return super.newArray(i);
        }
    }

    public static class RemoveBlobResponseCreator extends AbstractCreator<RemoveBlobResponse> {
        @Override // androidx.appsearch.safeparcel.stub.AbstractCreator, android.os.Parcelable.Creator
        public /* bridge */ /* synthetic */ Object createFromParcel(Parcel parcel) {
            return super.createFromParcel(parcel);
        }

        @Override // androidx.appsearch.safeparcel.stub.AbstractCreator, android.os.Parcelable.Creator
        public /* bridge */ /* synthetic */ Object[] newArray(int i) {
            return super.newArray(i);
        }
    }
}
