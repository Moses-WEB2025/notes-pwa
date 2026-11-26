package androidx.appsearch.platformstorage;

import android.app.appsearch.AppSearchManager;
import android.app.appsearch.AppSearchResult;
import android.content.Context;
import android.os.Build;
import androidx.appsearch.app.AppSearchEnvironmentFactory;
import androidx.appsearch.app.AppSearchSession;
import androidx.appsearch.app.EnterpriseGlobalSearchSession;
import androidx.appsearch.app.Features;
import androidx.appsearch.app.GlobalSearchSession;
import androidx.appsearch.exceptions.AppSearchException;
import androidx.appsearch.platformstorage.converter.SearchContextToPlatformConverter;
import androidx.concurrent.futures.ResolvableFuture;
import androidx.core.util.Preconditions;
import com.google.common.util.concurrent.ListenableFuture;
import java.util.concurrent.Executor;
import java.util.function.Consumer;
import net.lingala.zip4j.util.InternalZipConstants;

/* JADX INFO: loaded from: classes.dex */
public final class PlatformStorage {
    static final Executor EXECUTOR = AppSearchEnvironmentFactory.getEnvironmentInstance().createCachedThreadPoolExecutor();

    private PlatformStorage() {
    }

    public static final class SearchContext {
        final Context mContext;
        final String mDatabaseName;
        final Executor mExecutor;

        SearchContext(Context context, String str, Executor executor) {
            this.mContext = (Context) Preconditions.checkNotNull(context);
            this.mDatabaseName = (String) Preconditions.checkNotNull(str);
            this.mExecutor = (Executor) Preconditions.checkNotNull(executor);
        }

        public Context getContext() {
            return this.mContext;
        }

        public String getDatabaseName() {
            return this.mDatabaseName;
        }

        public Executor getWorkerExecutor() {
            return this.mExecutor;
        }

        public static final class Builder {
            private final Context mContext;
            private final String mDatabaseName;
            private Executor mExecutor;

            public Builder(Context context, String str) {
                this.mContext = (Context) Preconditions.checkNotNull(context);
                Preconditions.checkNotNull(str);
                if (str.contains(InternalZipConstants.ZIP_FILE_SEPARATOR)) {
                    throw new IllegalArgumentException("Database name cannot contain '/'");
                }
                this.mDatabaseName = str;
            }

            public Builder setWorkerExecutor(Executor executor) {
                this.mExecutor = (Executor) Preconditions.checkNotNull(executor);
                return this;
            }

            public SearchContext build() {
                if (this.mExecutor == null) {
                    this.mExecutor = PlatformStorage.EXECUTOR;
                }
                return new SearchContext(this.mContext, this.mDatabaseName, this.mExecutor);
            }
        }
    }

    public static final class GlobalSearchContext {
        final Context mContext;
        final Executor mExecutor;

        GlobalSearchContext(Context context, Executor executor) {
            this.mContext = (Context) Preconditions.checkNotNull(context);
            this.mExecutor = (Executor) Preconditions.checkNotNull(executor);
        }

        public Context getContext() {
            return this.mContext;
        }

        public Executor getWorkerExecutor() {
            return this.mExecutor;
        }

        public static final class Builder {
            private final Context mContext;
            private Executor mExecutor;

            public Builder(Context context) {
                this.mContext = (Context) Preconditions.checkNotNull(context);
            }

            public Builder setWorkerExecutor(Executor executor) {
                Preconditions.checkNotNull(executor);
                this.mExecutor = executor;
                return this;
            }

            public GlobalSearchContext build() {
                if (this.mExecutor == null) {
                    this.mExecutor = PlatformStorage.EXECUTOR;
                }
                return new GlobalSearchContext(this.mContext, this.mExecutor);
            }
        }
    }

    public static ListenableFuture<AppSearchSession> createSearchSessionAsync(final SearchContext searchContext) {
        Preconditions.checkNotNull(searchContext);
        AppSearchManager appSearchManager = (AppSearchManager) searchContext.mContext.getSystemService(AppSearchManager.class);
        final ResolvableFuture resolvableFutureCreate = ResolvableFuture.create();
        appSearchManager.createSearchSession(SearchContextToPlatformConverter.toPlatformSearchContext(searchContext), searchContext.mExecutor, new Consumer() { // from class: androidx.appsearch.platformstorage.PlatformStorage$$ExternalSyntheticLambda0
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                PlatformStorage.lambda$createSearchSessionAsync$0(resolvableFutureCreate, searchContext, (AppSearchResult) obj);
            }
        });
        return resolvableFutureCreate;
    }

    static /* synthetic */ void lambda$createSearchSessionAsync$0(ResolvableFuture resolvableFuture, SearchContext searchContext, AppSearchResult appSearchResult) {
        if (appSearchResult.isSuccess()) {
            resolvableFuture.set(new SearchSessionImpl((android.app.appsearch.AppSearchSession) appSearchResult.getResultValue(), searchContext.mExecutor, searchContext.mContext));
        } else {
            resolvableFuture.setException(new AppSearchException(appSearchResult.getResultCode(), appSearchResult.getErrorMessage()));
        }
    }

    public static ListenableFuture<GlobalSearchSession> createGlobalSearchSessionAsync(final GlobalSearchContext globalSearchContext) {
        Preconditions.checkNotNull(globalSearchContext);
        AppSearchManager appSearchManager = (AppSearchManager) globalSearchContext.mContext.getSystemService(AppSearchManager.class);
        final ResolvableFuture resolvableFutureCreate = ResolvableFuture.create();
        appSearchManager.createGlobalSearchSession(globalSearchContext.mExecutor, new Consumer() { // from class: androidx.appsearch.platformstorage.PlatformStorage$$ExternalSyntheticLambda2
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                PlatformStorage.lambda$createGlobalSearchSessionAsync$1(resolvableFutureCreate, globalSearchContext, (AppSearchResult) obj);
            }
        });
        return resolvableFutureCreate;
    }

    static /* synthetic */ void lambda$createGlobalSearchSessionAsync$1(ResolvableFuture resolvableFuture, GlobalSearchContext globalSearchContext, AppSearchResult appSearchResult) {
        if (appSearchResult.isSuccess()) {
            resolvableFuture.set(new GlobalSearchSessionImpl((android.app.appsearch.GlobalSearchSession) appSearchResult.getResultValue(), globalSearchContext.mExecutor, globalSearchContext.mContext));
        } else {
            resolvableFuture.setException(new AppSearchException(appSearchResult.getResultCode(), appSearchResult.getErrorMessage()));
        }
    }

    public static ListenableFuture<EnterpriseGlobalSearchSession> createEnterpriseGlobalSearchSessionAsync(final GlobalSearchContext globalSearchContext) {
        if (Build.VERSION.SDK_INT < 35) {
            throw new UnsupportedOperationException("ENTERPRISE_GLOBAL_SEARCH_SESSION is not supported on this AppSearch implementation");
        }
        Preconditions.checkNotNull(globalSearchContext);
        AppSearchManager appSearchManager = (AppSearchManager) globalSearchContext.mContext.getSystemService(AppSearchManager.class);
        final ResolvableFuture resolvableFutureCreate = ResolvableFuture.create();
        ApiHelperForV.createEnterpriseGlobalSearchSession(appSearchManager, globalSearchContext.mExecutor, new Consumer() { // from class: androidx.appsearch.platformstorage.PlatformStorage$$ExternalSyntheticLambda1
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                PlatformStorage.lambda$createEnterpriseGlobalSearchSessionAsync$2(resolvableFutureCreate, globalSearchContext, (AppSearchResult) obj);
            }
        });
        return resolvableFutureCreate;
    }

    static /* synthetic */ void lambda$createEnterpriseGlobalSearchSessionAsync$2(ResolvableFuture resolvableFuture, GlobalSearchContext globalSearchContext, AppSearchResult appSearchResult) {
        if (appSearchResult.isSuccess()) {
            resolvableFuture.set(new EnterpriseGlobalSearchSessionImpl((android.app.appsearch.EnterpriseGlobalSearchSession) appSearchResult.getResultValue(), globalSearchContext.mExecutor, globalSearchContext.mContext));
        } else {
            resolvableFuture.setException(new AppSearchException(appSearchResult.getResultCode(), appSearchResult.getErrorMessage()));
        }
    }

    public static Features getFeatures(Context context) {
        return new FeaturesImpl(context);
    }

    private static class ApiHelperForV {
        private ApiHelperForV() {
        }

        static void createEnterpriseGlobalSearchSession(AppSearchManager appSearchManager, Executor executor, Consumer<AppSearchResult<android.app.appsearch.EnterpriseGlobalSearchSession>> consumer) {
            appSearchManager.createEnterpriseGlobalSearchSession(executor, consumer);
        }
    }
}
