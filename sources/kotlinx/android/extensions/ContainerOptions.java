package kotlinx.android.extensions;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import kotlin.Metadata;

/* JADX INFO: compiled from: ContainerOptions.kt */
/* JADX INFO: loaded from: classes5.dex */
@Retention(RetentionPolicy.RUNTIME)
@Metadata(d1 = {"\u0000\u0012\n\u0002\u0018\u0002\n\u0002\u0010\u001b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\b\u0086\u0002\u0018\u00002\u00020\u0001B\u0012\u0012\u0010\b\u0002\u0010\u0002\u001a\u00020\u0003B\u0006\b\n0\u00038\u0004R\u000f\u0010\u0002\u001a\u00020\u0003¢\u0006\u0006\u001a\u0004\b\u0002\u0010\u0005¨\u0006\u0006"}, d2 = {"Lkotlinx/android/extensions/ContainerOptions;", "", "cache", "Lkotlinx/android/extensions/CacheImplementation;", "HASH_MAP", "()Lkotlinx/android/extensions/CacheImplementation;", "kotlin-android-extensions-runtime"}, k = 1, mv = {2, 2, 0}, xi = 48)
public @interface ContainerOptions {
    CacheImplementation cache() default CacheImplementation.HASH_MAP;
}
