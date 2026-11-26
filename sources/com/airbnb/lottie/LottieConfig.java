package com.airbnb.lottie;

import com.airbnb.lottie.configurations.reducemotion.ReducedMotionOption;
import com.airbnb.lottie.configurations.reducemotion.SystemReducedMotionOption;
import com.airbnb.lottie.network.LottieNetworkCacheProvider;
import com.airbnb.lottie.network.LottieNetworkFetcher;
import java.io.File;

/* JADX INFO: loaded from: classes.dex */
public class LottieConfig {
    final LottieNetworkCacheProvider cacheProvider;
    final AsyncUpdates defaultAsyncUpdates;
    final boolean disablePathInterpolatorCache;
    final boolean enableNetworkCache;
    final boolean enableSystraceMarkers;
    final LottieNetworkFetcher networkFetcher;
    final ReducedMotionOption reducedMotionOption;

    private LottieConfig(LottieNetworkFetcher lottieNetworkFetcher, LottieNetworkCacheProvider lottieNetworkCacheProvider, boolean z, boolean z2, boolean z3, AsyncUpdates asyncUpdates, ReducedMotionOption reducedMotionOption) {
        this.networkFetcher = lottieNetworkFetcher;
        this.cacheProvider = lottieNetworkCacheProvider;
        this.enableSystraceMarkers = z;
        this.enableNetworkCache = z2;
        this.disablePathInterpolatorCache = z3;
        this.defaultAsyncUpdates = asyncUpdates;
        this.reducedMotionOption = reducedMotionOption;
    }

    public static final class Builder {
        private LottieNetworkCacheProvider cacheProvider;
        private LottieNetworkFetcher networkFetcher;
        private boolean enableSystraceMarkers = false;
        private boolean enableNetworkCache = true;
        private boolean disablePathInterpolatorCache = true;
        private AsyncUpdates defaultAsyncUpdates = AsyncUpdates.AUTOMATIC;
        private ReducedMotionOption reducedMotionOption = new SystemReducedMotionOption();

        public Builder setNetworkFetcher(LottieNetworkFetcher lottieNetworkFetcher) {
            this.networkFetcher = lottieNetworkFetcher;
            return this;
        }

        public Builder setNetworkCacheDir(final File file) {
            if (this.cacheProvider != null) {
                throw new IllegalStateException("There is already a cache provider!");
            }
            this.cacheProvider = new LottieNetworkCacheProvider(this) { // from class: com.airbnb.lottie.LottieConfig.Builder.1
                final /* synthetic */ Builder this$0;

                {
                    this.this$0 = this;
                }

                @Override // com.airbnb.lottie.network.LottieNetworkCacheProvider
                public File getCacheDir() {
                    if (!file.isDirectory()) {
                        throw new IllegalArgumentException("cache file must be a directory");
                    }
                    return file;
                }
            };
            return this;
        }

        public Builder setNetworkCacheProvider(final LottieNetworkCacheProvider lottieNetworkCacheProvider) {
            if (this.cacheProvider != null) {
                throw new IllegalStateException("There is already a cache provider!");
            }
            this.cacheProvider = new LottieNetworkCacheProvider(this) { // from class: com.airbnb.lottie.LottieConfig.Builder.2
                final /* synthetic */ Builder this$0;

                {
                    this.this$0 = this;
                }

                @Override // com.airbnb.lottie.network.LottieNetworkCacheProvider
                public File getCacheDir() {
                    File cacheDir = lottieNetworkCacheProvider.getCacheDir();
                    if (cacheDir.isDirectory()) {
                        return cacheDir;
                    }
                    throw new IllegalArgumentException("cache file must be a directory");
                }
            };
            return this;
        }

        public Builder setEnableSystraceMarkers(boolean z) {
            this.enableSystraceMarkers = z;
            return this;
        }

        public Builder setEnableNetworkCache(boolean z) {
            this.enableNetworkCache = z;
            return this;
        }

        public Builder setDisablePathInterpolatorCache(boolean z) {
            this.disablePathInterpolatorCache = z;
            return this;
        }

        public Builder setDefaultAsyncUpdates(AsyncUpdates asyncUpdates) {
            this.defaultAsyncUpdates = asyncUpdates;
            return this;
        }

        public Builder setReducedMotionOption(ReducedMotionOption reducedMotionOption) {
            this.reducedMotionOption = reducedMotionOption;
            return this;
        }

        public LottieConfig build() {
            return new LottieConfig(this.networkFetcher, this.cacheProvider, this.enableSystraceMarkers, this.enableNetworkCache, this.disablePathInterpolatorCache, this.defaultAsyncUpdates, this.reducedMotionOption);
        }
    }
}
