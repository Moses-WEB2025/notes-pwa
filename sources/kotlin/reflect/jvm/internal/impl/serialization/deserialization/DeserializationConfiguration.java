package kotlin.reflect.jvm.internal.impl.serialization.deserialization;

import kotlin.reflect.jvm.internal.impl.metadata.deserialization.MetadataVersion;

/* JADX INFO: compiled from: DeserializationConfiguration.kt */
/* JADX INFO: loaded from: classes5.dex */
public interface DeserializationConfiguration {
    boolean getAllowUnstableDependencies();

    MetadataVersion getMetadataVersion();

    boolean getPreserveDeclarationsOrdering();

    boolean getReportErrorsOnPreReleaseDependencies();

    boolean getSkipMetadataVersionCheck();

    boolean getSkipPrereleaseCheck();

    boolean getTypeAliasesAllowed();

    /* JADX INFO: compiled from: DeserializationConfiguration.kt */
    public static final class Default implements DeserializationConfiguration {
        public static final Default INSTANCE = new Default();

        @Override // kotlin.reflect.jvm.internal.impl.serialization.deserialization.DeserializationConfiguration
        public boolean getAllowUnstableDependencies() {
            return false;
        }

        @Override // kotlin.reflect.jvm.internal.impl.serialization.deserialization.DeserializationConfiguration
        public boolean getPreserveDeclarationsOrdering() {
            return false;
        }

        @Override // kotlin.reflect.jvm.internal.impl.serialization.deserialization.DeserializationConfiguration
        public boolean getReportErrorsOnPreReleaseDependencies() {
            return false;
        }

        @Override // kotlin.reflect.jvm.internal.impl.serialization.deserialization.DeserializationConfiguration
        public boolean getSkipMetadataVersionCheck() {
            return false;
        }

        @Override // kotlin.reflect.jvm.internal.impl.serialization.deserialization.DeserializationConfiguration
        public boolean getSkipPrereleaseCheck() {
            return false;
        }

        @Override // kotlin.reflect.jvm.internal.impl.serialization.deserialization.DeserializationConfiguration
        public boolean getTypeAliasesAllowed() {
            return true;
        }

        @Override // kotlin.reflect.jvm.internal.impl.serialization.deserialization.DeserializationConfiguration
        public MetadataVersion getMetadataVersion() {
            return MetadataVersion.INSTANCE;
        }

        private Default() {
        }
    }
}
