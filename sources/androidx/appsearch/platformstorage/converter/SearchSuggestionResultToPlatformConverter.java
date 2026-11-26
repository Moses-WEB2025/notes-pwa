package androidx.appsearch.platformstorage.converter;

import androidx.appsearch.app.SearchSuggestionResult;
import androidx.core.util.Preconditions;
import java.util.ArrayList;
import java.util.List;

/* JADX INFO: loaded from: classes.dex */
public class SearchSuggestionResultToPlatformConverter {
    private SearchSuggestionResultToPlatformConverter() {
    }

    public static List<SearchSuggestionResult> toJetpackSearchSuggestionResults(List<android.app.appsearch.SearchSuggestionResult> list) {
        Preconditions.checkNotNull(list);
        ArrayList arrayList = new ArrayList(list.size());
        for (int i = 0; i < list.size(); i++) {
            arrayList.add(new SearchSuggestionResult.Builder().setSuggestedResult(list.get(i).getSuggestedResult()).build());
        }
        return arrayList;
    }
}
