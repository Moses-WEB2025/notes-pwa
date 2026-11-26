package androidx.appsearch.platformstorage.converter;

import android.app.appsearch.SearchSuggestionSpec;
import android.os.Build;
import androidx.core.util.Preconditions;
import java.util.Collection;
import java.util.List;
import java.util.Map;

/* JADX INFO: loaded from: classes.dex */
public final class SearchSuggestionSpecToPlatformConverter {
    private SearchSuggestionSpecToPlatformConverter() {
    }

    public static SearchSuggestionSpec toPlatformSearchSuggestionSpec(androidx.appsearch.app.SearchSuggestionSpec searchSuggestionSpec) {
        Preconditions.checkNotNull(searchSuggestionSpec);
        SearchSuggestionSpec.Builder builder = new SearchSuggestionSpec.Builder(searchSuggestionSpec.getMaximumResultCount());
        builder.addFilterNamespaces(searchSuggestionSpec.getFilterNamespaces()).addFilterSchemas(searchSuggestionSpec.getFilterSchemas()).setRankingStrategy(searchSuggestionSpec.getRankingStrategy());
        for (Map.Entry<String, List<String>> entry : searchSuggestionSpec.getFilterDocumentIds().entrySet()) {
            builder.addFilterDocumentIds(entry.getKey(), entry.getValue());
        }
        Map<String, List<String>> filterProperties = searchSuggestionSpec.getFilterProperties();
        if (!filterProperties.isEmpty()) {
            if (Build.VERSION.SDK_INT < 35) {
                throw new UnsupportedOperationException("SEARCH_SPEC_ADD_FILTER_PROPERTIES is not available on this AppSearch implementation.");
            }
            for (Map.Entry<String, List<String>> entry2 : filterProperties.entrySet()) {
                ApiHelperForV.addFilterProperties(builder, entry2.getKey(), entry2.getValue());
            }
        }
        if (!searchSuggestionSpec.getSearchStringParameters().isEmpty()) {
            throw new UnsupportedOperationException("SEARCH_SPEC_SEARCH_STRING_PARAMETERS is not available on this AppSearch implementation.");
        }
        return builder.build();
    }

    private static class ApiHelperForV {
        private ApiHelperForV() {
        }

        static void addFilterProperties(SearchSuggestionSpec.Builder builder, String str, Collection<String> collection) {
            builder.addFilterProperties(str, collection);
        }
    }
}
