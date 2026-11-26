package kotlin.reflect.jvm.internal.impl.platform;

import ch.qos.logback.core.CoreConstants;

/* JADX INFO: compiled from: TargetPlatform.kt */
/* JADX INFO: loaded from: classes5.dex */
public abstract class SimplePlatform {
    private final String platformName;
    private final TargetPlatformVersion targetPlatformVersion;

    public String toString() {
        String targetName = getTargetName();
        return targetName.length() > 0 ? this.platformName + " (" + targetName + CoreConstants.RIGHT_PARENTHESIS_CHAR : this.platformName;
    }

    public String getTargetName() {
        return getTargetPlatformVersion().getDescription();
    }

    public TargetPlatformVersion getTargetPlatformVersion() {
        return this.targetPlatformVersion;
    }
}
