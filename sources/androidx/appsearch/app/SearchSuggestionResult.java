package androidx.appsearch.app;

import android.os.Parcel;
import android.os.Parcelable;
import androidx.appsearch.safeparcel.AbstractSafeParcelable;
import androidx.appsearch.safeparcel.stub.StubCreators;
import androidx.core.util.Preconditions;

/* JADX INFO: loaded from: classes.dex */
public final class SearchSuggestionResult extends AbstractSafeParcelable {
    public static final Parcelable.Creator<SearchSuggestionResult> CREATOR = new StubCreators.SearchSuggestionResultCreator();
    private Integer mHashCode;
    private final String mSuggestedResult;

    SearchSuggestionResult(String str) {
        this.mSuggestedResult = (String) Preconditions.checkNotNull(str);
    }

    public String getSuggestedResult() {
        return this.mSuggestedResult;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj instanceof SearchSuggestionResult) {
            return this.mSuggestedResult.equals(((SearchSuggestionResult) obj).mSuggestedResult);
        }
        return false;
    }

    public int hashCode() {
        if (this.mHashCode == null) {
            this.mHashCode = Integer.valueOf(this.mSuggestedResult.hashCode());
        }
        return this.mHashCode.intValue();
    }

    public static final class Builder {
        private String mSuggestedResult = "";

        public Builder setSuggestedResult(String str) {
            Preconditions.checkNotNull(str);
            Preconditions.checkStringNotEmpty(str);
            this.mSuggestedResult = str;
            return this;
        }

        public SearchSuggestionResult build() {
            return new SearchSuggestionResult(this.mSuggestedResult);
        }
    }

    @Override // androidx.appsearch.safeparcel.AbstractSafeParcelable
    public void writeToParcel(Parcel parcel, int i) {
        StubCreators.SearchSuggestionResultCreator.writeToParcel(this, parcel, i);
    }
}
