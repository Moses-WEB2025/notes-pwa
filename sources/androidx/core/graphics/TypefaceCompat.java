package androidx.core.graphics;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Typeface;
import android.os.CancellationSignal;
import android.os.Handler;
import androidx.collection.LruCache;
import androidx.core.content.res.FontResourcesParserCompat;
import androidx.core.content.res.ResourcesCompat;
import androidx.core.provider.FontRequest;
import androidx.core.provider.FontsContractCompat;
import androidx.core.util.Preconditions;
import androidx.tracing.Trace;
import ch.qos.logback.core.CoreConstants;
import java.util.List;

/* JADX INFO: loaded from: classes.dex */
public class TypefaceCompat {
    public static final boolean DOWNLOADABLE_FALLBACK_DEBUG = false;
    public static final boolean DOWNLOADABLE_FONT_TRACING = true;
    private static final LruCache<String, Typeface> sTypefaceCache;
    private static final TypefaceCompatBaseImpl sTypefaceCompatImpl;

    static {
        Trace.beginSection("TypefaceCompat static init");
        sTypefaceCompatImpl = new TypefaceCompatApi29Impl();
        sTypefaceCache = new LruCache<>(16);
        Trace.endSection();
    }

    private TypefaceCompat() {
    }

    public static Typeface findFromCache(Resources resources, int i, String str, int i2, int i3) {
        return sTypefaceCache.get(createResourceUid(resources, i, str, i2, i3));
    }

    @Deprecated
    public static Typeface findFromCache(Resources resources, int i, int i2) {
        return findFromCache(resources, i, null, 0, i2);
    }

    private static String createResourceUid(Resources resources, int i, String str, int i2, int i3) {
        return resources.getResourcePackageName(i) + CoreConstants.DASH_CHAR + str + CoreConstants.DASH_CHAR + i2 + CoreConstants.DASH_CHAR + i + CoreConstants.DASH_CHAR + i3;
    }

    private static Typeface getSystemFontFamily(String str) {
        if (str != null && !str.isEmpty()) {
            Typeface typefaceCreate = Typeface.create(str, 0);
            Typeface typefaceCreate2 = Typeface.create(Typeface.DEFAULT, 0);
            if (typefaceCreate != null && !typefaceCreate.equals(typefaceCreate2)) {
                return typefaceCreate;
            }
        }
        return null;
    }

    public static Typeface createFromResourcesFamilyXml(Context context, FontResourcesParserCompat.FamilyResourceEntry familyResourceEntry, Resources resources, int i, String str, int i2, int i3, ResourcesCompat.FontCallback fontCallback, Handler handler, boolean z) {
        Typeface typefaceCreateFromFontFamilyFilesResourceEntry;
        if (familyResourceEntry instanceof FontResourcesParserCompat.ProviderResourceEntry) {
            FontResourcesParserCompat.ProviderResourceEntry providerResourceEntry = (FontResourcesParserCompat.ProviderResourceEntry) familyResourceEntry;
            Typeface systemFontFamily = getSystemFontFamily(providerResourceEntry.getSystemFontFamilyName());
            if (systemFontFamily != null) {
                if (fontCallback != null) {
                    fontCallback.callbackSuccessAsync(systemFontFamily, handler);
                }
                return systemFontFamily;
            }
            typefaceCreateFromFontFamilyFilesResourceEntry = FontsContractCompat.requestFont(context, (List<FontRequest>) (providerResourceEntry.getFallbackRequest() != null ? TypefaceCompat$$ExternalSyntheticBackport0.m(new Object[]{providerResourceEntry.getRequest(), providerResourceEntry.getFallbackRequest()}) : TypefaceCompat$$ExternalSyntheticBackport0.m(new Object[]{providerResourceEntry.getRequest()})), i3, !z ? fontCallback != null : providerResourceEntry.getFetchStrategy() != 0, z ? providerResourceEntry.getTimeout() : -1, ResourcesCompat.FontCallback.getHandler(handler), new ResourcesCallbackAdapter(fontCallback));
        } else {
            typefaceCreateFromFontFamilyFilesResourceEntry = sTypefaceCompatImpl.createFromFontFamilyFilesResourceEntry(context, (FontResourcesParserCompat.FontFamilyFilesResourceEntry) familyResourceEntry, resources, i3);
            if (fontCallback != null) {
                if (typefaceCreateFromFontFamilyFilesResourceEntry != null) {
                    fontCallback.callbackSuccessAsync(typefaceCreateFromFontFamilyFilesResourceEntry, handler);
                } else {
                    fontCallback.callbackFailAsync(-3, handler);
                }
            }
        }
        if (typefaceCreateFromFontFamilyFilesResourceEntry != null) {
            sTypefaceCache.put(createResourceUid(resources, i, str, i2, i3), typefaceCreateFromFontFamilyFilesResourceEntry);
        }
        return typefaceCreateFromFontFamilyFilesResourceEntry;
    }

    @Deprecated
    public static Typeface createFromResourcesFamilyXml(Context context, FontResourcesParserCompat.FamilyResourceEntry familyResourceEntry, Resources resources, int i, int i2, ResourcesCompat.FontCallback fontCallback, Handler handler, boolean z) {
        return createFromResourcesFamilyXml(context, familyResourceEntry, resources, i, null, 0, i2, fontCallback, handler, z);
    }

    public static Typeface createFromResourcesFontFile(Context context, Resources resources, int i, String str, int i2, int i3) {
        Typeface typefaceCreateFromResourcesFontFile = sTypefaceCompatImpl.createFromResourcesFontFile(context, resources, i, str, i3);
        if (typefaceCreateFromResourcesFontFile != null) {
            sTypefaceCache.put(createResourceUid(resources, i, str, i2, i3), typefaceCreateFromResourcesFontFile);
        }
        return typefaceCreateFromResourcesFontFile;
    }

    @Deprecated
    public static Typeface createFromResourcesFontFile(Context context, Resources resources, int i, String str, int i2) {
        return createFromResourcesFontFile(context, resources, i, str, 0, i2);
    }

    public static Typeface createFromFontInfo(Context context, CancellationSignal cancellationSignal, FontsContractCompat.FontInfo[] fontInfoArr, int i) {
        Trace.beginSection("TypefaceCompat.createFromFontInfo");
        try {
            return sTypefaceCompatImpl.createFromFontInfo(context, cancellationSignal, fontInfoArr, i);
        } finally {
            Trace.endSection();
        }
    }

    public static Typeface createFromFontInfoWithFallback(Context context, CancellationSignal cancellationSignal, List<FontsContractCompat.FontInfo[]> list, int i) {
        Trace.beginSection("TypefaceCompat.createFromFontInfoWithFallback");
        try {
            return sTypefaceCompatImpl.createFromFontInfoWithFallback(context, cancellationSignal, list, i);
        } finally {
            Trace.endSection();
        }
    }

    private static Typeface getBestFontFromFamily(Context context, Typeface typeface, int i) {
        TypefaceCompatBaseImpl typefaceCompatBaseImpl = sTypefaceCompatImpl;
        FontResourcesParserCompat.FontFamilyFilesResourceEntry fontFamily = typefaceCompatBaseImpl.getFontFamily(typeface);
        if (fontFamily == null) {
            return null;
        }
        return typefaceCompatBaseImpl.createFromFontFamilyFilesResourceEntry(context, fontFamily, context.getResources(), i);
    }

    public static Typeface create(Context context, Typeface typeface, int i) {
        if (context == null) {
            throw new IllegalArgumentException("Context cannot be null");
        }
        return Typeface.create(typeface, i);
    }

    public static Typeface create(Context context, Typeface typeface, int i, boolean z) {
        if (context == null) {
            throw new IllegalArgumentException("Context cannot be null");
        }
        Preconditions.checkArgumentInRange(i, 1, 1000, "weight");
        if (typeface == null) {
            typeface = Typeface.DEFAULT;
        }
        return sTypefaceCompatImpl.createWeightStyle(context, typeface, i, z);
    }

    public static void clearCache() {
        sTypefaceCache.evictAll();
    }

    public static class ResourcesCallbackAdapter extends FontsContractCompat.FontRequestCallback {
        private ResourcesCompat.FontCallback mFontCallback;

        public ResourcesCallbackAdapter(ResourcesCompat.FontCallback fontCallback) {
            this.mFontCallback = fontCallback;
        }

        @Override // androidx.core.provider.FontsContractCompat.FontRequestCallback
        public void onTypefaceRetrieved(Typeface typeface) {
            ResourcesCompat.FontCallback fontCallback = this.mFontCallback;
            if (fontCallback != null) {
                fontCallback.m57x46c88379(typeface);
            }
        }

        @Override // androidx.core.provider.FontsContractCompat.FontRequestCallback
        public void onTypefaceRequestFailed(int i) {
            ResourcesCompat.FontCallback fontCallback = this.mFontCallback;
            if (fontCallback != null) {
                fontCallback.m56xb24343b7(i);
            }
        }
    }
}
