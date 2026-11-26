package androidx.appsearch.platformstorage.converter;

import androidx.appsearch.app.AppSearchBatchResult;
import androidx.appsearch.app.AppSearchResult;
import androidx.appsearch.exceptions.AppSearchException;
import androidx.concurrent.futures.ResolvableFuture;
import androidx.core.util.Preconditions;
import java.util.Map;
import java.util.function.Function;

/* JADX INFO: loaded from: classes.dex */
public final class AppSearchResultToPlatformConverter {
    private AppSearchResultToPlatformConverter() {
    }

    public static <PlatformType, JetpackType> AppSearchResult<JetpackType> platformAppSearchResultToJetpack(android.app.appsearch.AppSearchResult<PlatformType> appSearchResult, Function<PlatformType, JetpackType> function) {
        Preconditions.checkNotNull(appSearchResult);
        if (appSearchResult.isSuccess()) {
            try {
                return AppSearchResult.newSuccessfulResult(function.apply(appSearchResult.getResultValue()));
            } catch (Throwable th) {
                return AppSearchResult.throwableToFailedResult(th);
            }
        }
        return AppSearchResult.newFailedResult(appSearchResult.getResultCode(), appSearchResult.getErrorMessage());
    }

    public static <PlatformType, JetpackType> void platformAppSearchResultToFuture(android.app.appsearch.AppSearchResult<PlatformType> appSearchResult, ResolvableFuture<JetpackType> resolvableFuture, Function<PlatformType, JetpackType> function) {
        Preconditions.checkNotNull(appSearchResult);
        Preconditions.checkNotNull(resolvableFuture);
        if (appSearchResult.isSuccess()) {
            try {
                resolvableFuture.set(function.apply(appSearchResult.getResultValue()));
                return;
            } catch (Throwable th) {
                resolvableFuture.setException(th);
                return;
            }
        }
        resolvableFuture.setException(new AppSearchException(appSearchResult.getResultCode(), appSearchResult.getErrorMessage()));
    }

    public static <T> void platformAppSearchResultToFuture(android.app.appsearch.AppSearchResult<T> appSearchResult, ResolvableFuture<T> resolvableFuture) {
        platformAppSearchResultToFuture(appSearchResult, resolvableFuture, Function.identity());
    }

    public static <K, PlatformValue, JetpackValue> AppSearchBatchResult<K, JetpackValue> platformAppSearchBatchResultToJetpack(android.app.appsearch.AppSearchBatchResult<K, PlatformValue> appSearchBatchResult, Function<PlatformValue, JetpackValue> function) {
        Preconditions.checkNotNull(appSearchBatchResult);
        Preconditions.checkNotNull(function);
        AppSearchBatchResult.Builder builder = new AppSearchBatchResult.Builder();
        for (Map.Entry<K, PlatformValue> entry : appSearchBatchResult.getSuccesses().entrySet()) {
            try {
                builder.setSuccess(entry.getKey(), function.apply(entry.getValue()));
            } catch (Throwable th) {
                builder.setResult(entry.getKey(), AppSearchResult.throwableToFailedResult(th));
            }
        }
        for (Map.Entry<K, android.app.appsearch.AppSearchResult<PlatformValue>> entry2 : appSearchBatchResult.getFailures().entrySet()) {
            builder.setFailure(entry2.getKey(), entry2.getValue().getResultCode(), entry2.getValue().getErrorMessage());
        }
        return builder.build();
    }
}
