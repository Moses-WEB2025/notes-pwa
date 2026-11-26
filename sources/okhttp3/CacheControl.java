package okhttp3;

import com.market.sdk.reflect.ReflectUtilsForMiui;
import java.util.concurrent.TimeUnit;
import kotlin.Deprecated;
import kotlin.DeprecationLevel;
import kotlin.Metadata;
import kotlin.ReplaceWith;
import kotlin.jvm.JvmStatic;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.time.Duration;
import okhttp3.internal._CacheControlCommonKt;

/* JADX INFO: compiled from: CacheControl.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000\"\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\n\n\u0002\u0010\u000e\n\u0002\b\u0016\u0018\u0000 &2\u00020\u0001:\u0002%&Bs\b\u0000\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0003\u0012\u0006\u0010\u0005\u001a\u00020\u0006\u0012\u0006\u0010\u0007\u001a\u00020\u0006\u0012\u0006\u0010\b\u001a\u00020\u0003\u0012\u0006\u0010\t\u001a\u00020\u0003\u0012\u0006\u0010\n\u001a\u00020\u0003\u0012\u0006\u0010\u000b\u001a\u00020\u0006\u0012\u0006\u0010\f\u001a\u00020\u0006\u0012\u0006\u0010\r\u001a\u00020\u0003\u0012\u0006\u0010\u000e\u001a\u00020\u0003\u0012\u0006\u0010\u000f\u001a\u00020\u0003\u0012\b\u0010\u0010\u001a\u0004\u0018\u00010\u0011¢\u0006\u0004\b\u0012\u0010\u0013J\r\u0010\u0002\u001a\u00020\u0003H\u0007¢\u0006\u0002\b\u001aJ\r\u0010\u0004\u001a\u00020\u0003H\u0007¢\u0006\u0002\b\u001bJ\r\u0010\u0005\u001a\u00020\u0006H\u0007¢\u0006\u0002\b\u001cJ\r\u0010\u0007\u001a\u00020\u0006H\u0007¢\u0006\u0002\b\u001dJ\r\u0010\n\u001a\u00020\u0003H\u0007¢\u0006\u0002\b\u001eJ\r\u0010\u000b\u001a\u00020\u0006H\u0007¢\u0006\u0002\b\u001fJ\r\u0010\f\u001a\u00020\u0006H\u0007¢\u0006\u0002\b J\r\u0010\r\u001a\u00020\u0003H\u0007¢\u0006\u0002\b!J\r\u0010\u000e\u001a\u00020\u0003H\u0007¢\u0006\u0002\b\"J\r\u0010\u000f\u001a\u00020\u0003H\u0007¢\u0006\u0002\b#J\b\u0010$\u001a\u00020\u0011H\u0016R\u0013\u0010\u0002\u001a\u00020\u00038G¢\u0006\b\n\u0000\u001a\u0004\b\u0002\u0010\u0014R\u0013\u0010\u0004\u001a\u00020\u00038G¢\u0006\b\n\u0000\u001a\u0004\b\u0004\u0010\u0014R\u0013\u0010\u0005\u001a\u00020\u00068G¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0015R\u0013\u0010\u0007\u001a\u00020\u00068G¢\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\u0015R\u0011\u0010\b\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\b\u0010\u0014R\u0011\u0010\t\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\u0014R\u0013\u0010\n\u001a\u00020\u00038G¢\u0006\b\n\u0000\u001a\u0004\b\n\u0010\u0014R\u0013\u0010\u000b\u001a\u00020\u00068G¢\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\u0015R\u0013\u0010\f\u001a\u00020\u00068G¢\u0006\b\n\u0000\u001a\u0004\b\f\u0010\u0015R\u0013\u0010\r\u001a\u00020\u00038G¢\u0006\b\n\u0000\u001a\u0004\b\r\u0010\u0014R\u0013\u0010\u000e\u001a\u00020\u00038G¢\u0006\b\n\u0000\u001a\u0004\b\u000e\u0010\u0014R\u0013\u0010\u000f\u001a\u00020\u00038G¢\u0006\b\n\u0000\u001a\u0004\b\u000f\u0010\u0014R\u001c\u0010\u0010\u001a\u0004\u0018\u00010\u0011X\u0080\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0016\u0010\u0017\"\u0004\b\u0018\u0010\u0019¨\u0006'"}, d2 = {"Lokhttp3/CacheControl;", "", "noCache", "", "noStore", "maxAgeSeconds", "", "sMaxAgeSeconds", "isPrivate", "isPublic", "mustRevalidate", "maxStaleSeconds", "minFreshSeconds", "onlyIfCached", "noTransform", "immutable", "headerValue", "", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "(ZZIIZZZIIZZZLjava/lang/String;)V", "()Z", "()I", "getHeaderValue$okhttp", "()Ljava/lang/String;", "setHeaderValue$okhttp", "(Ljava/lang/String;)V", "-deprecated_noCache", "-deprecated_noStore", "-deprecated_maxAgeSeconds", "-deprecated_sMaxAgeSeconds", "-deprecated_mustRevalidate", "-deprecated_maxStaleSeconds", "-deprecated_minFreshSeconds", "-deprecated_onlyIfCached", "-deprecated_noTransform", "-deprecated_immutable", "toString", "Builder", "Companion", "okhttp"}, k = 1, mv = {2, 2, 0}, xi = 48)
public final class CacheControl {

    /* JADX INFO: renamed from: Companion, reason: from kotlin metadata */
    public static final Companion INSTANCE;
    public static final CacheControl FORCE_CACHE;
    public static final CacheControl FORCE_NETWORK;
    private String headerValue;
    private final boolean immutable;
    private final boolean isPrivate;
    private final boolean isPublic;
    private final int maxAgeSeconds;
    private final int maxStaleSeconds;
    private final int minFreshSeconds;
    private final boolean mustRevalidate;
    private final boolean noCache;
    private final boolean noStore;
    private final boolean noTransform;
    private final boolean onlyIfCached;
    private final int sMaxAgeSeconds;

    @JvmStatic
    public static final CacheControl parse(Headers headers) {
        return INSTANCE.parse(headers);
    }

    public CacheControl(boolean z, boolean z2, int i, int i2, boolean z3, boolean z4, boolean z5, int i3, int i4, boolean z6, boolean z7, boolean z8, String str) {
        this.noCache = z;
        this.noStore = z2;
        this.maxAgeSeconds = i;
        this.sMaxAgeSeconds = i2;
        this.isPrivate = z3;
        this.isPublic = z4;
        this.mustRevalidate = z5;
        this.maxStaleSeconds = i3;
        this.minFreshSeconds = i4;
        this.onlyIfCached = z6;
        this.noTransform = z7;
        this.immutable = z8;
        this.headerValue = str;
    }

    public final boolean noCache() {
        return this.noCache;
    }

    public final boolean noStore() {
        return this.noStore;
    }

    public final int maxAgeSeconds() {
        return this.maxAgeSeconds;
    }

    public final int sMaxAgeSeconds() {
        return this.sMaxAgeSeconds;
    }

    /* JADX INFO: renamed from: isPrivate, reason: from getter */
    public final boolean getIsPrivate() {
        return this.isPrivate;
    }

    /* JADX INFO: renamed from: isPublic, reason: from getter */
    public final boolean getIsPublic() {
        return this.isPublic;
    }

    public final boolean mustRevalidate() {
        return this.mustRevalidate;
    }

    public final int maxStaleSeconds() {
        return this.maxStaleSeconds;
    }

    public final int minFreshSeconds() {
        return this.minFreshSeconds;
    }

    public final boolean onlyIfCached() {
        return this.onlyIfCached;
    }

    public final boolean noTransform() {
        return this.noTransform;
    }

    public final boolean immutable() {
        return this.immutable;
    }

    /* JADX INFO: renamed from: getHeaderValue$okhttp, reason: from getter */
    public final String getHeaderValue() {
        return this.headerValue;
    }

    public final void setHeaderValue$okhttp(String str) {
        this.headerValue = str;
    }

    @Deprecated(level = DeprecationLevel.ERROR, message = "moved to val", replaceWith = @ReplaceWith(expression = "noCache", imports = {}))
    /* JADX INFO: renamed from: -deprecated_noCache, reason: not valid java name and from getter */
    public final boolean getNoCache() {
        return this.noCache;
    }

    @Deprecated(level = DeprecationLevel.ERROR, message = "moved to val", replaceWith = @ReplaceWith(expression = "noStore", imports = {}))
    /* JADX INFO: renamed from: -deprecated_noStore, reason: not valid java name and from getter */
    public final boolean getNoStore() {
        return this.noStore;
    }

    @Deprecated(level = DeprecationLevel.ERROR, message = "moved to val", replaceWith = @ReplaceWith(expression = "maxAgeSeconds", imports = {}))
    /* JADX INFO: renamed from: -deprecated_maxAgeSeconds, reason: not valid java name and from getter */
    public final int getMaxAgeSeconds() {
        return this.maxAgeSeconds;
    }

    @Deprecated(level = DeprecationLevel.ERROR, message = "moved to val", replaceWith = @ReplaceWith(expression = "sMaxAgeSeconds", imports = {}))
    /* JADX INFO: renamed from: -deprecated_sMaxAgeSeconds, reason: not valid java name and from getter */
    public final int getSMaxAgeSeconds() {
        return this.sMaxAgeSeconds;
    }

    @Deprecated(level = DeprecationLevel.ERROR, message = "moved to val", replaceWith = @ReplaceWith(expression = "mustRevalidate", imports = {}))
    /* JADX INFO: renamed from: -deprecated_mustRevalidate, reason: not valid java name and from getter */
    public final boolean getMustRevalidate() {
        return this.mustRevalidate;
    }

    @Deprecated(level = DeprecationLevel.ERROR, message = "moved to val", replaceWith = @ReplaceWith(expression = "maxStaleSeconds", imports = {}))
    /* JADX INFO: renamed from: -deprecated_maxStaleSeconds, reason: not valid java name and from getter */
    public final int getMaxStaleSeconds() {
        return this.maxStaleSeconds;
    }

    @Deprecated(level = DeprecationLevel.ERROR, message = "moved to val", replaceWith = @ReplaceWith(expression = "minFreshSeconds", imports = {}))
    /* JADX INFO: renamed from: -deprecated_minFreshSeconds, reason: not valid java name and from getter */
    public final int getMinFreshSeconds() {
        return this.minFreshSeconds;
    }

    @Deprecated(level = DeprecationLevel.ERROR, message = "moved to val", replaceWith = @ReplaceWith(expression = "onlyIfCached", imports = {}))
    /* JADX INFO: renamed from: -deprecated_onlyIfCached, reason: not valid java name and from getter */
    public final boolean getOnlyIfCached() {
        return this.onlyIfCached;
    }

    @Deprecated(level = DeprecationLevel.ERROR, message = "moved to val", replaceWith = @ReplaceWith(expression = "noTransform", imports = {}))
    /* JADX INFO: renamed from: -deprecated_noTransform, reason: not valid java name and from getter */
    public final boolean getNoTransform() {
        return this.noTransform;
    }

    @Deprecated(level = DeprecationLevel.ERROR, message = "moved to val", replaceWith = @ReplaceWith(expression = "immutable", imports = {}))
    /* JADX INFO: renamed from: -deprecated_immutable, reason: not valid java name and from getter */
    public final boolean getImmutable() {
        return this.immutable;
    }

    public String toString() {
        return _CacheControlCommonKt.commonToString(this);
    }

    /* JADX INFO: compiled from: CacheControl.kt */
    @Metadata(d1 = {"\u00000\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0002\b\b\n\u0002\u0010\b\n\u0002\b\u0014\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u0001B\u0007¢\u0006\u0004\b\u0002\u0010\u0003J\u0006\u0010\u0004\u001a\u00020\u0000J\u0006\u0010\n\u001a\u00020\u0000J\u0006\u0010\u0019\u001a\u00020\u0000J\u0006\u0010\u001c\u001a\u00020\u0000J\u0006\u0010\u001f\u001a\u00020\u0000J\u0015\u0010\"\u001a\u00020\u00002\u0006\u0010\"\u001a\u00020#¢\u0006\u0004\b$\u0010%J\u0015\u0010&\u001a\u00020\u00002\u0006\u0010&\u001a\u00020#¢\u0006\u0004\b'\u0010%J\u0015\u0010(\u001a\u00020\u00002\u0006\u0010(\u001a\u00020#¢\u0006\u0004\b)\u0010%J\u0016\u0010\"\u001a\u00020\u00002\u0006\u0010\"\u001a\u00020\u000e2\u0006\u0010*\u001a\u00020+J\u0016\u0010&\u001a\u00020\u00002\u0006\u0010&\u001a\u00020\u000e2\u0006\u0010*\u001a\u00020+J\u0016\u0010(\u001a\u00020\u00002\u0006\u0010(\u001a\u00020\u000e2\u0006\u0010*\u001a\u00020+J\u0006\u0010,\u001a\u00020-R\u001a\u0010\u0004\u001a\u00020\u0005X\u0080\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0006\u0010\u0007\"\u0004\b\b\u0010\tR\u001a\u0010\n\u001a\u00020\u0005X\u0080\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u000b\u0010\u0007\"\u0004\b\f\u0010\tR\u001a\u0010\r\u001a\u00020\u000eX\u0080\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u000f\u0010\u0010\"\u0004\b\u0011\u0010\u0012R\u001a\u0010\u0013\u001a\u00020\u000eX\u0080\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0014\u0010\u0010\"\u0004\b\u0015\u0010\u0012R\u001a\u0010\u0016\u001a\u00020\u000eX\u0080\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0017\u0010\u0010\"\u0004\b\u0018\u0010\u0012R\u001a\u0010\u0019\u001a\u00020\u0005X\u0080\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u001a\u0010\u0007\"\u0004\b\u001b\u0010\tR\u001a\u0010\u001c\u001a\u00020\u0005X\u0080\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u001d\u0010\u0007\"\u0004\b\u001e\u0010\tR\u001a\u0010\u001f\u001a\u00020\u0005X\u0080\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b \u0010\u0007\"\u0004\b!\u0010\t¨\u0006."}, d2 = {"Lokhttp3/CacheControl$Builder;", "", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "()V", "noCache", "", "getNoCache$okhttp", "()Z", "setNoCache$okhttp", "(Z)V", "noStore", "getNoStore$okhttp", "setNoStore$okhttp", "maxAgeSeconds", "", "getMaxAgeSeconds$okhttp", "()I", "setMaxAgeSeconds$okhttp", "(I)V", "maxStaleSeconds", "getMaxStaleSeconds$okhttp", "setMaxStaleSeconds$okhttp", "minFreshSeconds", "getMinFreshSeconds$okhttp", "setMinFreshSeconds$okhttp", "onlyIfCached", "getOnlyIfCached$okhttp", "setOnlyIfCached$okhttp", "noTransform", "getNoTransform$okhttp", "setNoTransform$okhttp", "immutable", "getImmutable$okhttp", "setImmutable$okhttp", "maxAge", "Lkotlin/time/Duration;", "maxAge-LRDsOJo", "(J)Lokhttp3/CacheControl$Builder;", "maxStale", "maxStale-LRDsOJo", "minFresh", "minFresh-LRDsOJo", "timeUnit", "Ljava/util/concurrent/TimeUnit;", "build", "Lokhttp3/CacheControl;", "okhttp"}, k = 1, mv = {2, 2, 0}, xi = 48)
    public static final class Builder {
        private boolean immutable;
        private int maxAgeSeconds = -1;
        private int maxStaleSeconds = -1;
        private int minFreshSeconds = -1;
        private boolean noCache;
        private boolean noStore;
        private boolean noTransform;
        private boolean onlyIfCached;

        /* JADX INFO: renamed from: getNoCache$okhttp, reason: from getter */
        public final boolean getNoCache() {
            return this.noCache;
        }

        public final void setNoCache$okhttp(boolean z) {
            this.noCache = z;
        }

        /* JADX INFO: renamed from: getNoStore$okhttp, reason: from getter */
        public final boolean getNoStore() {
            return this.noStore;
        }

        public final void setNoStore$okhttp(boolean z) {
            this.noStore = z;
        }

        /* JADX INFO: renamed from: getMaxAgeSeconds$okhttp, reason: from getter */
        public final int getMaxAgeSeconds() {
            return this.maxAgeSeconds;
        }

        public final void setMaxAgeSeconds$okhttp(int i) {
            this.maxAgeSeconds = i;
        }

        /* JADX INFO: renamed from: getMaxStaleSeconds$okhttp, reason: from getter */
        public final int getMaxStaleSeconds() {
            return this.maxStaleSeconds;
        }

        public final void setMaxStaleSeconds$okhttp(int i) {
            this.maxStaleSeconds = i;
        }

        /* JADX INFO: renamed from: getMinFreshSeconds$okhttp, reason: from getter */
        public final int getMinFreshSeconds() {
            return this.minFreshSeconds;
        }

        public final void setMinFreshSeconds$okhttp(int i) {
            this.minFreshSeconds = i;
        }

        /* JADX INFO: renamed from: getOnlyIfCached$okhttp, reason: from getter */
        public final boolean getOnlyIfCached() {
            return this.onlyIfCached;
        }

        public final void setOnlyIfCached$okhttp(boolean z) {
            this.onlyIfCached = z;
        }

        /* JADX INFO: renamed from: getNoTransform$okhttp, reason: from getter */
        public final boolean getNoTransform() {
            return this.noTransform;
        }

        public final void setNoTransform$okhttp(boolean z) {
            this.noTransform = z;
        }

        /* JADX INFO: renamed from: getImmutable$okhttp, reason: from getter */
        public final boolean getImmutable() {
            return this.immutable;
        }

        public final void setImmutable$okhttp(boolean z) {
            this.immutable = z;
        }

        public final Builder noCache() {
            return _CacheControlCommonKt.commonNoCache(this);
        }

        public final Builder noStore() {
            return _CacheControlCommonKt.commonNoStore(this);
        }

        public final Builder onlyIfCached() {
            return _CacheControlCommonKt.commonOnlyIfCached(this);
        }

        public final Builder noTransform() {
            return _CacheControlCommonKt.commonNoTransform(this);
        }

        public final Builder immutable() {
            return _CacheControlCommonKt.commonImmutable(this);
        }

        /* JADX INFO: renamed from: maxAge-LRDsOJo, reason: not valid java name */
        public final Builder m4388maxAgeLRDsOJo(long maxAge) {
            long jM3912getInWholeSecondsimpl = Duration.m3912getInWholeSecondsimpl(maxAge);
            if (jM3912getInWholeSecondsimpl < 0) {
                throw new IllegalArgumentException(("maxAge < 0: " + jM3912getInWholeSecondsimpl).toString());
            }
            this.maxAgeSeconds = _CacheControlCommonKt.commonClampToInt(jM3912getInWholeSecondsimpl);
            return this;
        }

        /* JADX INFO: renamed from: maxStale-LRDsOJo, reason: not valid java name */
        public final Builder m4389maxStaleLRDsOJo(long maxStale) {
            long jM3912getInWholeSecondsimpl = Duration.m3912getInWholeSecondsimpl(maxStale);
            if (jM3912getInWholeSecondsimpl < 0) {
                throw new IllegalArgumentException(("maxStale < 0: " + jM3912getInWholeSecondsimpl).toString());
            }
            this.maxStaleSeconds = _CacheControlCommonKt.commonClampToInt(jM3912getInWholeSecondsimpl);
            return this;
        }

        /* JADX INFO: renamed from: minFresh-LRDsOJo, reason: not valid java name */
        public final Builder m4390minFreshLRDsOJo(long minFresh) {
            long jM3912getInWholeSecondsimpl = Duration.m3912getInWholeSecondsimpl(minFresh);
            if (jM3912getInWholeSecondsimpl < 0) {
                throw new IllegalArgumentException(("minFresh < 0: " + jM3912getInWholeSecondsimpl).toString());
            }
            this.minFreshSeconds = _CacheControlCommonKt.commonClampToInt(jM3912getInWholeSecondsimpl);
            return this;
        }

        public final Builder maxAge(int maxAge, TimeUnit timeUnit) {
            Intrinsics.checkNotNullParameter(timeUnit, "timeUnit");
            if (maxAge < 0) {
                throw new IllegalArgumentException(("maxAge < 0: " + maxAge).toString());
            }
            this.maxAgeSeconds = _CacheControlCommonKt.commonClampToInt(timeUnit.toSeconds(maxAge));
            return this;
        }

        public final Builder maxStale(int maxStale, TimeUnit timeUnit) {
            Intrinsics.checkNotNullParameter(timeUnit, "timeUnit");
            if (maxStale < 0) {
                throw new IllegalArgumentException(("maxStale < 0: " + maxStale).toString());
            }
            this.maxStaleSeconds = _CacheControlCommonKt.commonClampToInt(timeUnit.toSeconds(maxStale));
            return this;
        }

        public final Builder minFresh(int minFresh, TimeUnit timeUnit) {
            Intrinsics.checkNotNullParameter(timeUnit, "timeUnit");
            if (minFresh < 0) {
                throw new IllegalArgumentException(("minFresh < 0: " + minFresh).toString());
            }
            this.minFreshSeconds = _CacheControlCommonKt.commonClampToInt(timeUnit.toSeconds(minFresh));
            return this;
        }

        public final CacheControl build() {
            return _CacheControlCommonKt.commonBuild(this);
        }
    }

    /* JADX INFO: compiled from: CacheControl.kt */
    @Metadata(d1 = {"\u0000\u001a\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\t\b\u0002¢\u0006\u0004\b\u0002\u0010\u0003J\u0010\u0010\u0007\u001a\u00020\u00052\u0006\u0010\b\u001a\u00020\tH\u0007R\u0010\u0010\u0004\u001a\u00020\u00058\u0006X\u0087\u0004¢\u0006\u0002\n\u0000R\u0010\u0010\u0006\u001a\u00020\u00058\u0006X\u0087\u0004¢\u0006\u0002\n\u0000¨\u0006\n"}, d2 = {"Lokhttp3/CacheControl$Companion;", "", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "()V", "FORCE_NETWORK", "Lokhttp3/CacheControl;", "FORCE_CACHE", "parse", "headers", "Lokhttp3/Headers;", "okhttp"}, k = 1, mv = {2, 2, 0}, xi = 48)
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }

        @JvmStatic
        public final CacheControl parse(Headers headers) {
            Intrinsics.checkNotNullParameter(headers, "headers");
            return _CacheControlCommonKt.commonParse(this, headers);
        }
    }

    static {
        Companion companion = new Companion(null);
        INSTANCE = companion;
        FORCE_NETWORK = _CacheControlCommonKt.commonForceNetwork(companion);
        FORCE_CACHE = _CacheControlCommonKt.commonForceCache(companion);
    }
}
