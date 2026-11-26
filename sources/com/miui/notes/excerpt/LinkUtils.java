package com.miui.notes.excerpt;

import com.market.sdk.reflect.ReflectUtilsForMiui;
import java.util.regex.Matcher;
import kotlin.Metadata;
import kotlin.jvm.JvmStatic;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;
import miuix.util.Patterns;

/* JADX INFO: compiled from: LinkUtils.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000\u001a\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0003\bÆ\u0002\u0018\u00002\u00020\u0001B\t\b\u0002¢\u0006\u0004\b\u0002\u0010\u0003J\u0010\u0010\u0004\u001a\u00020\u00052\u0006\u0010\u0006\u001a\u00020\u0007H\u0007J\u000e\u0010\b\u001a\u00020\u00052\u0006\u0010\u0006\u001a\u00020\u0007J\u000e\u0010\t\u001a\u00020\u00072\u0006\u0010\u0006\u001a\u00020\u0007¨\u0006\n"}, d2 = {"Lcom/miui/notes/excerpt/LinkUtils;", "", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "()V", "isAccurateValidUrl", "", "urlString", "", "isClipUrlAccurateValid", "getUrl", "app_PhoneGlobalRelease"}, k = 1, mv = {2, 2, 0}, xi = 48)
public final class LinkUtils {
    public static final LinkUtils INSTANCE = new LinkUtils();

    private LinkUtils() {
    }

    @JvmStatic
    public static final boolean isAccurateValidUrl(String urlString) {
        Intrinsics.checkNotNullParameter(urlString, "urlString");
        Matcher matcher = Patterns.WEB_URL.matcher(urlString);
        if (!matcher.find()) {
            return false;
        }
        String strGroup = matcher.group();
        Intrinsics.checkNotNullExpressionValue(strGroup, "group(...)");
        return Intrinsics.areEqual(strGroup, urlString);
    }

    public final boolean isClipUrlAccurateValid(String urlString) {
        Intrinsics.checkNotNullParameter(urlString, "urlString");
        String str = urlString;
        Matcher matcher = Patterns.WEB_URL.matcher(str);
        if (!matcher.find()) {
            return false;
        }
        String strGroup = matcher.group();
        Intrinsics.checkNotNullExpressionValue(strGroup, "group(...)");
        return StringsKt.contains$default((CharSequence) str, (CharSequence) strGroup, false, 2, (Object) null);
    }

    public final String getUrl(String urlString) {
        Intrinsics.checkNotNullParameter(urlString, "urlString");
        Matcher matcher = Patterns.WEB_URL.matcher(urlString);
        if (matcher.find()) {
            String strGroup = matcher.group();
            Intrinsics.checkNotNullExpressionValue(strGroup, "group(...)");
            return strGroup;
        }
        return "";
    }
}
