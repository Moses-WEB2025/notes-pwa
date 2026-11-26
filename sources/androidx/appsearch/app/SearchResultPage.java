package androidx.appsearch.app;

import android.os.Parcel;
import android.os.Parcelable;
import androidx.appsearch.safeparcel.AbstractSafeParcelable;
import androidx.appsearch.safeparcel.stub.StubCreators;
import java.util.Collections;
import java.util.List;

/* JADX INFO: loaded from: classes.dex */
public class SearchResultPage extends AbstractSafeParcelable {
    public static final Parcelable.Creator<SearchResultPage> CREATOR = new StubCreators.SearchResultPageCreator();
    private final long mNextPageToken;
    private final List<SearchResult> mResults;

    public SearchResultPage(long j, List<SearchResult> list) {
        this.mNextPageToken = j;
        this.mResults = list;
    }

    public SearchResultPage() {
        this.mNextPageToken = 0L;
        this.mResults = Collections.emptyList();
    }

    public long getNextPageToken() {
        return this.mNextPageToken;
    }

    public List<SearchResult> getResults() {
        List<SearchResult> list = this.mResults;
        return list == null ? Collections.emptyList() : list;
    }

    @Override // androidx.appsearch.safeparcel.AbstractSafeParcelable
    public void writeToParcel(Parcel parcel, int i) {
        StubCreators.SearchResultPageCreator.writeToParcel(this, parcel, i);
    }
}
