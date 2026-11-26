package androidx.appsearch.platformstorage.converter;

import android.app.appsearch.AppSearchManager;
import androidx.appsearch.platformstorage.PlatformStorage;
import androidx.core.util.Preconditions;

/* JADX INFO: loaded from: classes.dex */
public final class SearchContextToPlatformConverter {
    private SearchContextToPlatformConverter() {
    }

    public static AppSearchManager.SearchContext toPlatformSearchContext(PlatformStorage.SearchContext searchContext) {
        Preconditions.checkNotNull(searchContext);
        return new AppSearchManager.SearchContext.Builder(searchContext.getDatabaseName()).build();
    }
}
