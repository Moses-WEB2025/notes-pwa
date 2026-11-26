package androidx.appsearch.stats;

import androidx.core.util.Preconditions;

/* JADX INFO: loaded from: classes.dex */
public abstract class BaseStats {
    private static final int LAUNCH_VM = 0;
    private final long mEnabledFeatures;

    protected BaseStats(Builder<?> builder) {
        Preconditions.checkNotNull(builder);
        this.mEnabledFeatures = builder.mEnabledFeatures;
    }

    public long getEnabledFeatures() {
        return this.mEnabledFeatures;
    }

    public static abstract class Builder<BuilderType extends Builder> {
        private final BuilderType mBuilderTypeInstance = this;
        long mEnabledFeatures;

        protected abstract BaseStats build();

        public BuilderType setLaunchVMEnabled(boolean z) {
            modifyEnabledFeature(0, z);
            return this.mBuilderTypeInstance;
        }

        private void modifyEnabledFeature(int i, boolean z) {
            if (z) {
                this.mEnabledFeatures = (1 << i) | this.mEnabledFeatures;
            } else {
                this.mEnabledFeatures = (~(1 << i)) & this.mEnabledFeatures;
            }
        }
    }
}
