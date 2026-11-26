package androidx.appsearch.builtintypes;

import androidx.appfunctions.internal.AppSearchAppFunctionReader;
import androidx.appsearch.app.SearchSpec;
import androidx.core.util.Preconditions;

/* JADX INFO: loaded from: classes.dex */
public class BuiltInCorpusFilters {
    public static SearchSpec.Builder searchMobileApplicationCorpus(SearchSpec.Builder builder) {
        return ((SearchSpec.Builder) Preconditions.checkNotNull(builder)).addFilterPackageNames(AppSearchAppFunctionReader.SYSTEM_PACKAGE_NAME).addFilterNamespaces("apps");
    }

    public static SearchSpec.Builder searchPersonCorpus(SearchSpec.Builder builder) {
        return ((SearchSpec.Builder) Preconditions.checkNotNull(builder)).addFilterPackageNames(AppSearchAppFunctionReader.SYSTEM_PACKAGE_NAME).addFilterSchemas(C$$__AppSearch__Person.SCHEMA_NAME);
    }

    private BuiltInCorpusFilters() {
    }
}
