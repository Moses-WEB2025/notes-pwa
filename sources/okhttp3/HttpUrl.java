package okhttp3;

import ch.qos.logback.classic.pattern.CallerDataConverter;
import ch.qos.logback.core.CoreConstants;
import com.market.sdk.reflect.ReflectUtilsForMiui;
import com.miui.richeditor.IRichEditor;
import com.miui.richeditor.schema.HtmlParser;
import com.xiaomi.micloudsdk.utils.MiCloudConstants;
import com.xiaomi.mirror.RemoteDeviceInfo;
import com.xiaomi.mirror.synergy.CallMethod;
import com.xiaomi.onetrack.api.ah;
import defpackage.R2;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import kotlin.Deprecated;
import kotlin.DeprecationLevel;
import kotlin.Metadata;
import kotlin.ReplaceWith;
import kotlin.collections.CollectionsKt;
import kotlin.collections.SetsKt;
import kotlin.internal.ProgressionUtilKt;
import kotlin.jvm.JvmStatic;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.ranges.IntProgression;
import kotlin.ranges.RangesKt;
import kotlin.text.Regex;
import kotlin.text.StringsKt;
import kotlin.text.Typography;
import miuix.smartaction.SmartAction;
import net.lingala.zip4j.util.InternalZipConstants;
import okhttp3.internal._HostnamesCommonKt;
import okhttp3.internal._UtilCommonKt;
import okhttp3.internal.publicsuffix.PublicSuffixDatabase;
import okhttp3.internal.url._UrlKt;

/* JADX INFO: compiled from: HttpUrl.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000F\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0004\n\u0002\u0010\b\n\u0000\n\u0002\u0010 \n\u0002\b\t\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\f\n\u0002\u0010\"\n\u0002\b\n\n\u0002\u0018\u0002\n\u0002\b\u001b\u0018\u0000 K2\u00020\u0001:\u0002JKBc\b\u0002\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0003\u0012\u0006\u0010\u0005\u001a\u00020\u0003\u0012\u0006\u0010\u0006\u001a\u00020\u0003\u0012\u0006\u0010\u0007\u001a\u00020\b\u0012\f\u0010\t\u001a\b\u0012\u0004\u0012\u00020\u00030\n\u0012\u0010\u0010\u000b\u001a\f\u0012\u0006\u0012\u0004\u0018\u00010\u0003\u0018\u00010\n\u0012\b\u0010\f\u001a\u0004\u0018\u00010\u0003\u0012\u0006\u0010\r\u001a\u00020\u0003¢\u0006\u0004\b\u000e\u0010\u000fJ\r\u0010\u0016\u001a\u00020\u0017H\u0007¢\u0006\u0002\b\rJ\r\u0010\u0018\u001a\u00020\u0019H\u0007¢\u0006\u0002\b\u001aJ\u0010\u0010#\u001a\u0004\u0018\u00010\u00032\u0006\u0010$\u001a\u00020\u0003J\u0016\u0010(\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\u00030\n2\u0006\u0010$\u001a\u00020\u0003J\u000e\u0010)\u001a\u00020\u00032\u0006\u0010*\u001a\u00020\bJ\u0010\u0010+\u001a\u0004\u0018\u00010\u00032\u0006\u0010*\u001a\u00020\bJ\u0006\u0010-\u001a\u00020\u0003J\u0010\u0010.\u001a\u0004\u0018\u00010\u00002\u0006\u0010/\u001a\u00020\u0003J\u0006\u00100\u001a\u000201J\u0010\u00100\u001a\u0004\u0018\u0001012\u0006\u0010/\u001a\u00020\u0003J\u0013\u00102\u001a\u00020\u00142\b\u00103\u001a\u0004\u0018\u00010\u0001H\u0096\u0002J\b\u00104\u001a\u00020\bH\u0016J\b\u00105\u001a\u00020\u0003H\u0016J\b\u00106\u001a\u0004\u0018\u00010\u0003J\r\u0010\r\u001a\u00020\u0017H\u0007¢\u0006\u0002\b7J\r\u0010\u001a\u001a\u00020\u0019H\u0007¢\u0006\u0002\b8J\r\u0010\u0002\u001a\u00020\u0003H\u0007¢\u0006\u0002\b9J\r\u0010\u001b\u001a\u00020\u0003H\u0007¢\u0006\u0002\b:J\r\u0010\u0004\u001a\u00020\u0003H\u0007¢\u0006\u0002\b;J\r\u0010\u001c\u001a\u00020\u0003H\u0007¢\u0006\u0002\b<J\r\u0010\u0005\u001a\u00020\u0003H\u0007¢\u0006\u0002\b=J\r\u0010\u0006\u001a\u00020\u0003H\u0007¢\u0006\u0002\b>J\r\u0010\u0007\u001a\u00020\bH\u0007¢\u0006\u0002\b?J\r\u0010\u001d\u001a\u00020\bH\u0007¢\u0006\u0002\b@J\r\u0010\u001e\u001a\u00020\u0003H\u0007¢\u0006\u0002\bAJ\u0013\u0010\u001f\u001a\b\u0012\u0004\u0012\u00020\u00030\nH\u0007¢\u0006\u0002\bBJ\u0013\u0010\t\u001a\b\u0012\u0004\u0012\u00020\u00030\nH\u0007¢\u0006\u0002\bCJ\u000f\u0010 \u001a\u0004\u0018\u00010\u0003H\u0007¢\u0006\u0002\bDJ\u000f\u0010!\u001a\u0004\u0018\u00010\u0003H\u0007¢\u0006\u0002\bEJ\r\u0010\"\u001a\u00020\bH\u0007¢\u0006\u0002\bFJ\u0013\u0010%\u001a\b\u0012\u0004\u0012\u00020\u00030&H\u0007¢\u0006\u0002\bGJ\u000f\u0010,\u001a\u0004\u0018\u00010\u0003H\u0007¢\u0006\u0002\bHJ\u000f\u0010\f\u001a\u0004\u0018\u00010\u0003H\u0007¢\u0006\u0002\bIR\u0013\u0010\u0002\u001a\u00020\u00038G¢\u0006\b\n\u0000\u001a\u0004\b\u0002\u0010\u0010R\u0013\u0010\u0004\u001a\u00020\u00038G¢\u0006\b\n\u0000\u001a\u0004\b\u0004\u0010\u0010R\u0013\u0010\u0005\u001a\u00020\u00038G¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0010R\u0013\u0010\u0006\u001a\u00020\u00038G¢\u0006\b\n\u0000\u001a\u0004\b\u0006\u0010\u0010R\u0013\u0010\u0007\u001a\u00020\b8G¢\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\u0011R\u0019\u0010\t\u001a\b\u0012\u0004\u0012\u00020\u00030\n8G¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\u0012R\u0018\u0010\u000b\u001a\f\u0012\u0006\u0012\u0004\u0018\u00010\u0003\u0018\u00010\nX\u0082\u0004¢\u0006\u0002\n\u0000R\u0015\u0010\f\u001a\u0004\u0018\u00010\u00038G¢\u0006\b\n\u0000\u001a\u0004\b\f\u0010\u0010R\u000e\u0010\r\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000R\u0011\u0010\u0013\u001a\u00020\u00148F¢\u0006\u0006\u001a\u0004\b\u0013\u0010\u0015R\u0011\u0010\u001b\u001a\u00020\u00038G¢\u0006\u0006\u001a\u0004\b\u001b\u0010\u0010R\u0011\u0010\u001c\u001a\u00020\u00038G¢\u0006\u0006\u001a\u0004\b\u001c\u0010\u0010R\u0011\u0010\u001d\u001a\u00020\b8G¢\u0006\u0006\u001a\u0004\b\u001d\u0010\u0011R\u0011\u0010\u001e\u001a\u00020\u00038G¢\u0006\u0006\u001a\u0004\b\u001e\u0010\u0010R\u0017\u0010\u001f\u001a\b\u0012\u0004\u0012\u00020\u00030\n8G¢\u0006\u0006\u001a\u0004\b\u001f\u0010\u0012R\u0013\u0010 \u001a\u0004\u0018\u00010\u00038G¢\u0006\u0006\u001a\u0004\b \u0010\u0010R\u0013\u0010!\u001a\u0004\u0018\u00010\u00038G¢\u0006\u0006\u001a\u0004\b!\u0010\u0010R\u0011\u0010\"\u001a\u00020\b8G¢\u0006\u0006\u001a\u0004\b\"\u0010\u0011R\u0017\u0010%\u001a\b\u0012\u0004\u0012\u00020\u00030&8G¢\u0006\u0006\u001a\u0004\b%\u0010'R\u0013\u0010,\u001a\u0004\u0018\u00010\u00038G¢\u0006\u0006\u001a\u0004\b,\u0010\u0010¨\u0006L"}, d2 = {"Lokhttp3/HttpUrl;", "", ah.C, "", "username", "password", "host", ah.E, "", "pathSegments", "", "queryNamesAndValues", "fragment", "url", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;ILjava/util/List;Ljava/util/List;Ljava/lang/String;Ljava/lang/String;)V", "()Ljava/lang/String;", "()I", "()Ljava/util/List;", "isHttps", "", "()Z", "toUrl", "Ljava/net/URL;", "toUri", "Ljava/net/URI;", CallMethod.ARG_URI, "encodedUsername", "encodedPassword", "pathSize", "encodedPath", "encodedPathSegments", "encodedQuery", SmartAction.Feature.QUERY, "querySize", "queryParameter", "name", "queryParameterNames", "", "()Ljava/util/Set;", "queryParameterValues", "queryParameterName", "index", "queryParameterValue", "encodedFragment", "redact", "resolve", "link", "newBuilder", "Lokhttp3/HttpUrl$Builder;", "equals", RemoteDeviceInfo.MANUFACTURER_OTHER, "hashCode", "toString", "topPrivateDomain", "-deprecated_url", "-deprecated_uri", "-deprecated_scheme", "-deprecated_encodedUsername", "-deprecated_username", "-deprecated_encodedPassword", "-deprecated_password", "-deprecated_host", "-deprecated_port", "-deprecated_pathSize", "-deprecated_encodedPath", "-deprecated_encodedPathSegments", "-deprecated_pathSegments", "-deprecated_encodedQuery", "-deprecated_query", "-deprecated_querySize", "-deprecated_queryParameterNames", "-deprecated_encodedFragment", "-deprecated_fragment", "Builder", "Companion", "okhttp"}, k = 1, mv = {2, 2, 0}, xi = 48)
public final class HttpUrl {

    /* JADX INFO: renamed from: Companion, reason: from kotlin metadata */
    public static final Companion INSTANCE = new Companion(null);
    private final String fragment;
    private final String host;
    private final String password;
    private final List<String> pathSegments;
    private final int port;
    private final List<String> queryNamesAndValues;
    private final String scheme;
    private final String url;
    private final String username;

    public /* synthetic */ HttpUrl(String str, String str2, String str3, String str4, int i, List list, List list2, String str5, String str6, DefaultConstructorMarker defaultConstructorMarker) {
        this(str, str2, str3, str4, i, list, list2, str5, str6);
    }

    @JvmStatic
    public static final int defaultPort(String str) {
        return INSTANCE.defaultPort(str);
    }

    @JvmStatic
    public static final HttpUrl get(String str) {
        return INSTANCE.get(str);
    }

    @JvmStatic
    public static final HttpUrl get(URI uri) {
        return INSTANCE.get(uri);
    }

    @JvmStatic
    public static final HttpUrl get(URL url) {
        return INSTANCE.get(url);
    }

    @JvmStatic
    public static final HttpUrl parse(String str) {
        return INSTANCE.parse(str);
    }

    private HttpUrl(String str, String str2, String str3, String str4, int i, List<String> list, List<String> list2, String str5, String str6) {
        this.scheme = str;
        this.username = str2;
        this.password = str3;
        this.host = str4;
        this.port = i;
        this.pathSegments = list;
        this.queryNamesAndValues = list2;
        this.fragment = str5;
        this.url = str6;
    }

    public final String scheme() {
        return this.scheme;
    }

    public final String username() {
        return this.username;
    }

    public final String password() {
        return this.password;
    }

    public final String host() {
        return this.host;
    }

    public final int port() {
        return this.port;
    }

    public final List<String> pathSegments() {
        return this.pathSegments;
    }

    public final String fragment() {
        return this.fragment;
    }

    public final boolean isHttps() {
        return Intrinsics.areEqual(this.scheme, "https");
    }

    public final URL url() {
        try {
            return new URL(this.url);
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }
    }

    public final URI uri() {
        String string = newBuilder().reencodeForUri$okhttp().toString();
        try {
            return new URI(string);
        } catch (URISyntaxException e) {
            try {
                URI uriCreate = URI.create(new Regex("[\\u0000-\\u001F\\u007F-\\u009F\\p{javaWhitespace}]").replace(string, ""));
                Intrinsics.checkNotNull(uriCreate);
                return uriCreate;
            } catch (Exception unused) {
                throw new RuntimeException(e);
            }
        }
    }

    public final String encodedUsername() {
        if (this.username.length() == 0) {
            return "";
        }
        int length = this.scheme.length() + 3;
        String str = this.url;
        String strSubstring = this.url.substring(length, _UtilCommonKt.delimiterOffset(str, ":@", length, str.length()));
        Intrinsics.checkNotNullExpressionValue(strSubstring, "substring(...)");
        return strSubstring;
    }

    public final String encodedPassword() {
        if (this.password.length() == 0) {
            return "";
        }
        String strSubstring = this.url.substring(StringsKt.indexOf$default((CharSequence) this.url, CoreConstants.COLON_CHAR, this.scheme.length() + 3, false, 4, (Object) null) + 1, StringsKt.indexOf$default((CharSequence) this.url, '@', 0, false, 6, (Object) null));
        Intrinsics.checkNotNullExpressionValue(strSubstring, "substring(...)");
        return strSubstring;
    }

    public final int pathSize() {
        return this.pathSegments.size();
    }

    public final String encodedPath() {
        int iIndexOf$default = StringsKt.indexOf$default((CharSequence) this.url, '/', this.scheme.length() + 3, false, 4, (Object) null);
        String str = this.url;
        String strSubstring = this.url.substring(iIndexOf$default, _UtilCommonKt.delimiterOffset(str, "?#", iIndexOf$default, str.length()));
        Intrinsics.checkNotNullExpressionValue(strSubstring, "substring(...)");
        return strSubstring;
    }

    public final List<String> encodedPathSegments() {
        int iIndexOf$default = StringsKt.indexOf$default((CharSequence) this.url, '/', this.scheme.length() + 3, false, 4, (Object) null);
        String str = this.url;
        int iDelimiterOffset = _UtilCommonKt.delimiterOffset(str, "?#", iIndexOf$default, str.length());
        ArrayList arrayList = new ArrayList();
        while (iIndexOf$default < iDelimiterOffset) {
            int i = iIndexOf$default + 1;
            int iDelimiterOffset2 = _UtilCommonKt.delimiterOffset(this.url, '/', i, iDelimiterOffset);
            String strSubstring = this.url.substring(i, iDelimiterOffset2);
            Intrinsics.checkNotNullExpressionValue(strSubstring, "substring(...)");
            arrayList.add(strSubstring);
            iIndexOf$default = iDelimiterOffset2;
        }
        return arrayList;
    }

    public final String encodedQuery() {
        if (this.queryNamesAndValues == null) {
            return null;
        }
        int iIndexOf$default = StringsKt.indexOf$default((CharSequence) this.url, '?', 0, false, 6, (Object) null) + 1;
        String str = this.url;
        String strSubstring = this.url.substring(iIndexOf$default, _UtilCommonKt.delimiterOffset(str, '#', iIndexOf$default, str.length()));
        Intrinsics.checkNotNullExpressionValue(strSubstring, "substring(...)");
        return strSubstring;
    }

    public final String query() {
        if (this.queryNamesAndValues == null) {
            return null;
        }
        StringBuilder sb = new StringBuilder();
        INSTANCE.toQueryString(this.queryNamesAndValues, sb);
        return sb.toString();
    }

    public final int querySize() {
        List<String> list = this.queryNamesAndValues;
        if (list != null) {
            return list.size() / 2;
        }
        return 0;
    }

    public final String queryParameter(String name) {
        Intrinsics.checkNotNullParameter(name, "name");
        List<String> list = this.queryNamesAndValues;
        if (list == null) {
            return null;
        }
        IntProgression intProgressionStep = RangesKt.step(RangesKt.until(0, list.size()), 2);
        int first = intProgressionStep.getFirst();
        int last = intProgressionStep.getLast();
        int step = intProgressionStep.getStep();
        if ((step > 0 && first <= last) || (step < 0 && last <= first)) {
            while (!Intrinsics.areEqual(name, this.queryNamesAndValues.get(first))) {
                if (first != last) {
                    first += step;
                }
            }
            return this.queryNamesAndValues.get(first + 1);
        }
        return null;
    }

    public final Set<String> queryParameterNames() {
        if (this.queryNamesAndValues == null) {
            return SetsKt.emptySet();
        }
        LinkedHashSet linkedHashSet = new LinkedHashSet(this.queryNamesAndValues.size() / 2, 1.0f);
        IntProgression intProgressionStep = RangesKt.step(RangesKt.until(0, this.queryNamesAndValues.size()), 2);
        int first = intProgressionStep.getFirst();
        int last = intProgressionStep.getLast();
        int step = intProgressionStep.getStep();
        if ((step > 0 && first <= last) || (step < 0 && last <= first)) {
            while (true) {
                String str = this.queryNamesAndValues.get(first);
                Intrinsics.checkNotNull(str);
                linkedHashSet.add(str);
                if (first == last) {
                    break;
                }
                first += step;
            }
        }
        Set<String> setUnmodifiableSet = Collections.unmodifiableSet(linkedHashSet);
        Intrinsics.checkNotNullExpressionValue(setUnmodifiableSet, "unmodifiableSet(...)");
        return setUnmodifiableSet;
    }

    public final List<String> queryParameterValues(String name) {
        Intrinsics.checkNotNullParameter(name, "name");
        if (this.queryNamesAndValues == null) {
            return CollectionsKt.emptyList();
        }
        ArrayList arrayList = new ArrayList(4);
        IntProgression intProgressionStep = RangesKt.step(RangesKt.until(0, this.queryNamesAndValues.size()), 2);
        int first = intProgressionStep.getFirst();
        int last = intProgressionStep.getLast();
        int step = intProgressionStep.getStep();
        if ((step > 0 && first <= last) || (step < 0 && last <= first)) {
            while (true) {
                if (Intrinsics.areEqual(name, this.queryNamesAndValues.get(first))) {
                    arrayList.add(this.queryNamesAndValues.get(first + 1));
                }
                if (first == last) {
                    break;
                }
                first += step;
            }
        }
        List<String> listUnmodifiableList = Collections.unmodifiableList(arrayList);
        Intrinsics.checkNotNullExpressionValue(listUnmodifiableList, "unmodifiableList(...)");
        return listUnmodifiableList;
    }

    public final String queryParameterName(int index) {
        List<String> list = this.queryNamesAndValues;
        if (list == null) {
            throw new IndexOutOfBoundsException();
        }
        String str = list.get(index * 2);
        Intrinsics.checkNotNull(str);
        return str;
    }

    public final String queryParameterValue(int index) {
        List<String> list = this.queryNamesAndValues;
        if (list == null) {
            throw new IndexOutOfBoundsException();
        }
        return list.get((index * 2) + 1);
    }

    public final String encodedFragment() {
        if (this.fragment == null) {
            return null;
        }
        String strSubstring = this.url.substring(StringsKt.indexOf$default((CharSequence) this.url, '#', 0, false, 6, (Object) null) + 1);
        Intrinsics.checkNotNullExpressionValue(strSubstring, "substring(...)");
        return strSubstring;
    }

    public final String redact() {
        Builder builderNewBuilder = newBuilder("/...");
        Intrinsics.checkNotNull(builderNewBuilder);
        return builderNewBuilder.username("").password("").build().getUrl();
    }

    public final HttpUrl resolve(String link) {
        Intrinsics.checkNotNullParameter(link, "link");
        Builder builderNewBuilder = newBuilder(link);
        if (builderNewBuilder != null) {
            return builderNewBuilder.build();
        }
        return null;
    }

    public final Builder newBuilder() {
        Builder builder = new Builder();
        builder.setScheme$okhttp(this.scheme);
        builder.setEncodedUsername$okhttp(encodedUsername());
        builder.setEncodedPassword$okhttp(encodedPassword());
        builder.setHost$okhttp(this.host);
        builder.setPort$okhttp(this.port != INSTANCE.defaultPort(this.scheme) ? this.port : -1);
        builder.getEncodedPathSegments$okhttp().clear();
        builder.getEncodedPathSegments$okhttp().addAll(encodedPathSegments());
        builder.encodedQuery(encodedQuery());
        builder.setEncodedFragment$okhttp(encodedFragment());
        return builder;
    }

    public final Builder newBuilder(String link) {
        Intrinsics.checkNotNullParameter(link, "link");
        try {
            return new Builder().parse$okhttp(this, link);
        } catch (IllegalArgumentException unused) {
            return null;
        }
    }

    public boolean equals(Object other) {
        return (other instanceof HttpUrl) && Intrinsics.areEqual(((HttpUrl) other).url, this.url);
    }

    public int hashCode() {
        return this.url.hashCode();
    }

    /* JADX INFO: renamed from: toString, reason: from getter */
    public String getUrl() {
        return this.url;
    }

    public final String topPrivateDomain() {
        if (_HostnamesCommonKt.canParseAsIpAddress(this.host)) {
            return null;
        }
        return PublicSuffixDatabase.INSTANCE.get().getEffectiveTldPlusOne(this.host);
    }

    @Deprecated(level = DeprecationLevel.ERROR, message = "moved to toUrl()", replaceWith = @ReplaceWith(expression = "toUrl()", imports = {}))
    /* JADX INFO: renamed from: -deprecated_url, reason: not valid java name */
    public final URL m4438deprecated_url() {
        return url();
    }

    @Deprecated(level = DeprecationLevel.ERROR, message = "moved to toUri()", replaceWith = @ReplaceWith(expression = "toUri()", imports = {}))
    /* JADX INFO: renamed from: -deprecated_uri, reason: not valid java name */
    public final URI m4437deprecated_uri() {
        return uri();
    }

    @Deprecated(level = DeprecationLevel.ERROR, message = "moved to val", replaceWith = @ReplaceWith(expression = ah.C, imports = {}))
    /* JADX INFO: renamed from: -deprecated_scheme, reason: not valid java name and from getter */
    public final String getScheme() {
        return this.scheme;
    }

    @Deprecated(level = DeprecationLevel.ERROR, message = "moved to val", replaceWith = @ReplaceWith(expression = "encodedUsername", imports = {}))
    /* JADX INFO: renamed from: -deprecated_encodedUsername, reason: not valid java name */
    public final String m4426deprecated_encodedUsername() {
        return encodedUsername();
    }

    @Deprecated(level = DeprecationLevel.ERROR, message = "moved to val", replaceWith = @ReplaceWith(expression = "username", imports = {}))
    /* JADX INFO: renamed from: -deprecated_username, reason: not valid java name and from getter */
    public final String getUsername() {
        return this.username;
    }

    @Deprecated(level = DeprecationLevel.ERROR, message = "moved to val", replaceWith = @ReplaceWith(expression = "encodedPassword", imports = {}))
    /* JADX INFO: renamed from: -deprecated_encodedPassword, reason: not valid java name */
    public final String m4422deprecated_encodedPassword() {
        return encodedPassword();
    }

    @Deprecated(level = DeprecationLevel.ERROR, message = "moved to val", replaceWith = @ReplaceWith(expression = "password", imports = {}))
    /* JADX INFO: renamed from: -deprecated_password, reason: not valid java name and from getter */
    public final String getPassword() {
        return this.password;
    }

    @Deprecated(level = DeprecationLevel.ERROR, message = "moved to val", replaceWith = @ReplaceWith(expression = "host", imports = {}))
    /* JADX INFO: renamed from: -deprecated_host, reason: not valid java name and from getter */
    public final String getHost() {
        return this.host;
    }

    @Deprecated(level = DeprecationLevel.ERROR, message = "moved to val", replaceWith = @ReplaceWith(expression = ah.E, imports = {}))
    /* JADX INFO: renamed from: -deprecated_port, reason: not valid java name and from getter */
    public final int getPort() {
        return this.port;
    }

    @Deprecated(level = DeprecationLevel.ERROR, message = "moved to val", replaceWith = @ReplaceWith(expression = "pathSize", imports = {}))
    /* JADX INFO: renamed from: -deprecated_pathSize, reason: not valid java name */
    public final int m4431deprecated_pathSize() {
        return pathSize();
    }

    @Deprecated(level = DeprecationLevel.ERROR, message = "moved to val", replaceWith = @ReplaceWith(expression = "encodedPath", imports = {}))
    /* JADX INFO: renamed from: -deprecated_encodedPath, reason: not valid java name */
    public final String m4423deprecated_encodedPath() {
        return encodedPath();
    }

    @Deprecated(level = DeprecationLevel.ERROR, message = "moved to val", replaceWith = @ReplaceWith(expression = "encodedPathSegments", imports = {}))
    /* JADX INFO: renamed from: -deprecated_encodedPathSegments, reason: not valid java name */
    public final List<String> m4424deprecated_encodedPathSegments() {
        return encodedPathSegments();
    }

    @Deprecated(level = DeprecationLevel.ERROR, message = "moved to val", replaceWith = @ReplaceWith(expression = "pathSegments", imports = {}))
    /* JADX INFO: renamed from: -deprecated_pathSegments, reason: not valid java name */
    public final List<String> m4430deprecated_pathSegments() {
        return this.pathSegments;
    }

    @Deprecated(level = DeprecationLevel.ERROR, message = "moved to val", replaceWith = @ReplaceWith(expression = "encodedQuery", imports = {}))
    /* JADX INFO: renamed from: -deprecated_encodedQuery, reason: not valid java name */
    public final String m4425deprecated_encodedQuery() {
        return encodedQuery();
    }

    @Deprecated(level = DeprecationLevel.ERROR, message = "moved to val", replaceWith = @ReplaceWith(expression = SmartAction.Feature.QUERY, imports = {}))
    /* JADX INFO: renamed from: -deprecated_query, reason: not valid java name */
    public final String m4433deprecated_query() {
        return query();
    }

    @Deprecated(level = DeprecationLevel.ERROR, message = "moved to val", replaceWith = @ReplaceWith(expression = "querySize", imports = {}))
    /* JADX INFO: renamed from: -deprecated_querySize, reason: not valid java name */
    public final int m4435deprecated_querySize() {
        return querySize();
    }

    @Deprecated(level = DeprecationLevel.ERROR, message = "moved to val", replaceWith = @ReplaceWith(expression = "queryParameterNames", imports = {}))
    /* JADX INFO: renamed from: -deprecated_queryParameterNames, reason: not valid java name */
    public final Set<String> m4434deprecated_queryParameterNames() {
        return queryParameterNames();
    }

    @Deprecated(level = DeprecationLevel.ERROR, message = "moved to val", replaceWith = @ReplaceWith(expression = "encodedFragment", imports = {}))
    /* JADX INFO: renamed from: -deprecated_encodedFragment, reason: not valid java name */
    public final String m4421deprecated_encodedFragment() {
        return encodedFragment();
    }

    @Deprecated(level = DeprecationLevel.ERROR, message = "moved to val", replaceWith = @ReplaceWith(expression = "fragment", imports = {}))
    /* JADX INFO: renamed from: -deprecated_fragment, reason: not valid java name and from getter */
    public final String getFragment() {
        return this.fragment;
    }

    /* JADX INFO: compiled from: HttpUrl.kt */
    @Metadata(d1 = {"\u0000N\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0002\b\u000e\n\u0002\u0010\b\n\u0002\b\u0005\n\u0002\u0010!\n\u0002\b\u0013\n\u0002\u0010\u000b\n\u0002\b\u0012\n\u0002\u0010\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010 \n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0013\u0018\u00002\u00020\u0001B\u0007¢\u0006\u0004\b\u0002\u0010\u0003J\u000e\u0010\u0004\u001a\u00020\u00002\u0006\u0010\u0004\u001a\u00020\u0005J\u000e\u0010$\u001a\u00020\u00002\u0006\u0010$\u001a\u00020\u0005J\u000e\u0010\n\u001a\u00020\u00002\u0006\u0010\n\u001a\u00020\u0005J\u000e\u0010%\u001a\u00020\u00002\u0006\u0010%\u001a\u00020\u0005J\u000e\u0010\r\u001a\u00020\u00002\u0006\u0010\r\u001a\u00020\u0005J\u000e\u0010\u0010\u001a\u00020\u00002\u0006\u0010\u0010\u001a\u00020\u0005J\u000e\u0010\u0013\u001a\u00020\u00002\u0006\u0010\u0013\u001a\u00020\u0014J\u000e\u0010&\u001a\u00020\u00002\u0006\u0010'\u001a\u00020\u0005J\u000e\u0010(\u001a\u00020\u00002\u0006\u0010)\u001a\u00020\u0005J\u000e\u0010*\u001a\u00020\u00002\u0006\u0010+\u001a\u00020\u0005J\u000e\u0010,\u001a\u00020\u00002\u0006\u0010\u0019\u001a\u00020\u0005J\u0018\u0010(\u001a\u00020\u00002\u0006\u0010)\u001a\u00020\u00052\u0006\u0010-\u001a\u00020.H\u0002J\u0016\u0010/\u001a\u00020\u00002\u0006\u00100\u001a\u00020\u00142\u0006\u0010'\u001a\u00020\u0005J\u0016\u00101\u001a\u00020\u00002\u0006\u00100\u001a\u00020\u00142\u0006\u0010+\u001a\u00020\u0005J\u000e\u00102\u001a\u00020\u00002\u0006\u00100\u001a\u00020\u0014J\u000e\u00103\u001a\u00020\u00002\u0006\u00103\u001a\u00020\u0005J\u0010\u00104\u001a\u00020\u00002\b\u00104\u001a\u0004\u0018\u00010\u0005J\u0010\u00105\u001a\u00020\u00002\b\u00105\u001a\u0004\u0018\u00010\u0005J\u0018\u00106\u001a\u00020\u00002\u0006\u00107\u001a\u00020\u00052\b\u00108\u001a\u0004\u0018\u00010\u0005J\u0018\u00109\u001a\u00020\u00002\u0006\u0010:\u001a\u00020\u00052\b\u0010;\u001a\u0004\u0018\u00010\u0005J\u0018\u0010<\u001a\u00020\u00002\u0006\u00107\u001a\u00020\u00052\b\u00108\u001a\u0004\u0018\u00010\u0005J\u0018\u0010=\u001a\u00020\u00002\u0006\u0010:\u001a\u00020\u00052\b\u0010;\u001a\u0004\u0018\u00010\u0005J\u000e\u0010>\u001a\u00020\u00002\u0006\u00107\u001a\u00020\u0005J\u000e\u0010?\u001a\u00020\u00002\u0006\u0010:\u001a\u00020\u0005J\u0010\u0010@\u001a\u00020A2\u0006\u0010B\u001a\u00020\u0005H\u0002J\u0010\u0010C\u001a\u00020\u00002\b\u0010C\u001a\u0004\u0018\u00010\u0005J\u0010\u0010!\u001a\u00020\u00002\b\u0010!\u001a\u0004\u0018\u00010\u0005J\r\u0010D\u001a\u00020\u0000H\u0000¢\u0006\u0002\bEJ\u0006\u0010F\u001a\u00020GJ\b\u0010H\u001a\u00020\u0014H\u0002J\b\u0010I\u001a\u00020\u0005H\u0016J\u001e\u0010J\u001a\u00020A*\b\u0012\u0004\u0012\u00020\u00050K2\n\u0010L\u001a\u00060Mj\u0002`NH\u0002J\u001f\u0010O\u001a\u00020\u00002\b\u0010P\u001a\u0004\u0018\u00010G2\u0006\u0010Q\u001a\u00020\u0005H\u0000¢\u0006\u0002\bRJ \u0010S\u001a\u00020A2\u0006\u0010Q\u001a\u00020\u00052\u0006\u0010T\u001a\u00020\u00142\u0006\u0010U\u001a\u00020\u0014H\u0002J0\u0010V\u001a\u00020A2\u0006\u0010Q\u001a\u00020\u00052\u0006\u0010W\u001a\u00020\u00142\u0006\u0010U\u001a\u00020\u00142\u0006\u0010X\u001a\u00020.2\u0006\u0010-\u001a\u00020.H\u0002J\b\u0010Y\u001a\u00020AH\u0002J\u0010\u0010Z\u001a\u00020.2\u0006\u0010Q\u001a\u00020\u0005H\u0002J\u0010\u0010[\u001a\u00020.2\u0006\u0010Q\u001a\u00020\u0005H\u0002J\u0014\u0010\\\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\u00050\u001a*\u00020\u0005H\u0002J \u0010]\u001a\u00020\u00142\u0006\u0010Q\u001a\u00020\u00052\u0006\u0010W\u001a\u00020\u00142\u0006\u0010U\u001a\u00020\u0014H\u0002J\u001c\u0010^\u001a\u00020\u0014*\u00020\u00052\u0006\u0010W\u001a\u00020\u00142\u0006\u0010U\u001a\u00020\u0014H\u0002J \u0010_\u001a\u00020\u00142\u0006\u0010Q\u001a\u00020\u00052\u0006\u0010W\u001a\u00020\u00142\u0006\u0010U\u001a\u00020\u0014H\u0002J \u0010`\u001a\u00020\u00142\u0006\u0010Q\u001a\u00020\u00052\u0006\u0010W\u001a\u00020\u00142\u0006\u0010U\u001a\u00020\u0014H\u0002R\u001c\u0010\u0004\u001a\u0004\u0018\u00010\u0005X\u0080\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0006\u0010\u0007\"\u0004\b\b\u0010\tR\u001a\u0010\n\u001a\u00020\u0005X\u0080\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u000b\u0010\u0007\"\u0004\b\f\u0010\tR\u001a\u0010\r\u001a\u00020\u0005X\u0080\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u000e\u0010\u0007\"\u0004\b\u000f\u0010\tR\u001c\u0010\u0010\u001a\u0004\u0018\u00010\u0005X\u0080\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0011\u0010\u0007\"\u0004\b\u0012\u0010\tR\u001a\u0010\u0013\u001a\u00020\u0014X\u0080\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0015\u0010\u0016\"\u0004\b\u0017\u0010\u0018R\u001a\u0010\u0019\u001a\b\u0012\u0004\u0012\u00020\u00050\u001aX\u0080\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u001b\u0010\u001cR$\u0010\u001d\u001a\f\u0012\u0006\u0012\u0004\u0018\u00010\u0005\u0018\u00010\u001aX\u0080\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u001e\u0010\u001c\"\u0004\b\u001f\u0010 R\u001c\u0010!\u001a\u0004\u0018\u00010\u0005X\u0080\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\"\u0010\u0007\"\u0004\b#\u0010\t¨\u0006a"}, d2 = {"Lokhttp3/HttpUrl$Builder;", "", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "()V", ah.C, "", "getScheme$okhttp", "()Ljava/lang/String;", "setScheme$okhttp", "(Ljava/lang/String;)V", "encodedUsername", "getEncodedUsername$okhttp", "setEncodedUsername$okhttp", "encodedPassword", "getEncodedPassword$okhttp", "setEncodedPassword$okhttp", "host", "getHost$okhttp", "setHost$okhttp", ah.E, "", "getPort$okhttp", "()I", "setPort$okhttp", "(I)V", "encodedPathSegments", "", "getEncodedPathSegments$okhttp", "()Ljava/util/List;", "encodedQueryNamesAndValues", "getEncodedQueryNamesAndValues$okhttp", "setEncodedQueryNamesAndValues$okhttp", "(Ljava/util/List;)V", "encodedFragment", "getEncodedFragment$okhttp", "setEncodedFragment$okhttp", "username", "password", "addPathSegment", "pathSegment", "addPathSegments", "pathSegments", "addEncodedPathSegment", "encodedPathSegment", "addEncodedPathSegments", "alreadyEncoded", "", "setPathSegment", "index", "setEncodedPathSegment", "removePathSegment", "encodedPath", SmartAction.Feature.QUERY, "encodedQuery", "addQueryParameter", "name", "value", "addEncodedQueryParameter", "encodedName", "encodedValue", "setQueryParameter", "setEncodedQueryParameter", "removeAllQueryParameters", "removeAllEncodedQueryParameters", "removeAllCanonicalQueryParameters", "", "canonicalName", "fragment", "reencodeForUri", "reencodeForUri$okhttp", "build", "Lokhttp3/HttpUrl;", "effectivePort", "toString", "toPathString", "", "out", "Ljava/lang/StringBuilder;", "Lkotlin/text/StringBuilder;", "parse", "base", HtmlParser.InputElement.TAG, "parse$okhttp", "resolvePath", "startPos", "limit", MiCloudConstants.SYNC.XIAOMI_REQUEST_TYPE_PUSH, "pos", "addTrailingSlash", "pop", "isDot", "isDotDot", "toQueryNamesAndValues", "schemeDelimiterOffset", "slashCount", "portColonOffset", "parsePort", "okhttp"}, k = 1, mv = {2, 2, 0}, xi = 48)
    public static final class Builder {
        private String encodedFragment;
        private List<String> encodedQueryNamesAndValues;
        private String host;
        private String scheme;
        private String encodedUsername = "";
        private String encodedPassword = "";
        private int port = -1;
        private final List<String> encodedPathSegments = CollectionsKt.mutableListOf("");

        /* JADX INFO: renamed from: getScheme$okhttp, reason: from getter */
        public final String getScheme() {
            return this.scheme;
        }

        public final void setScheme$okhttp(String str) {
            this.scheme = str;
        }

        /* JADX INFO: renamed from: getEncodedUsername$okhttp, reason: from getter */
        public final String getEncodedUsername() {
            return this.encodedUsername;
        }

        public final void setEncodedUsername$okhttp(String str) {
            Intrinsics.checkNotNullParameter(str, "<set-?>");
            this.encodedUsername = str;
        }

        /* JADX INFO: renamed from: getEncodedPassword$okhttp, reason: from getter */
        public final String getEncodedPassword() {
            return this.encodedPassword;
        }

        public final void setEncodedPassword$okhttp(String str) {
            Intrinsics.checkNotNullParameter(str, "<set-?>");
            this.encodedPassword = str;
        }

        /* JADX INFO: renamed from: getHost$okhttp, reason: from getter */
        public final String getHost() {
            return this.host;
        }

        public final void setHost$okhttp(String str) {
            this.host = str;
        }

        /* JADX INFO: renamed from: getPort$okhttp, reason: from getter */
        public final int getPort() {
            return this.port;
        }

        public final void setPort$okhttp(int i) {
            this.port = i;
        }

        public final List<String> getEncodedPathSegments$okhttp() {
            return this.encodedPathSegments;
        }

        public final List<String> getEncodedQueryNamesAndValues$okhttp() {
            return this.encodedQueryNamesAndValues;
        }

        public final void setEncodedQueryNamesAndValues$okhttp(List<String> list) {
            this.encodedQueryNamesAndValues = list;
        }

        /* JADX INFO: renamed from: getEncodedFragment$okhttp, reason: from getter */
        public final String getEncodedFragment() {
            return this.encodedFragment;
        }

        public final void setEncodedFragment$okhttp(String str) {
            this.encodedFragment = str;
        }

        public final Builder scheme(String scheme) {
            Intrinsics.checkNotNullParameter(scheme, "scheme");
            if (StringsKt.equals(scheme, "http", true)) {
                this.scheme = "http";
                return this;
            }
            if (StringsKt.equals(scheme, "https", true)) {
                this.scheme = "https";
                return this;
            }
            throw new IllegalArgumentException("unexpected scheme: " + scheme);
        }

        public final Builder username(String username) {
            Intrinsics.checkNotNullParameter(username, "username");
            this.encodedUsername = _UrlKt.canonicalize$default(username, 0, 0, " \"':;<=>@[]^`{}|/\\?#", false, false, false, false, 123, null);
            return this;
        }

        public final Builder encodedUsername(String encodedUsername) {
            Intrinsics.checkNotNullParameter(encodedUsername, "encodedUsername");
            this.encodedUsername = _UrlKt.canonicalize$default(encodedUsername, 0, 0, " \"':;<=>@[]^`{}|/\\?#", true, false, false, false, 115, null);
            return this;
        }

        public final Builder password(String password) {
            Intrinsics.checkNotNullParameter(password, "password");
            this.encodedPassword = _UrlKt.canonicalize$default(password, 0, 0, " \"':;<=>@[]^`{}|/\\?#", false, false, false, false, 123, null);
            return this;
        }

        public final Builder encodedPassword(String encodedPassword) {
            Intrinsics.checkNotNullParameter(encodedPassword, "encodedPassword");
            this.encodedPassword = _UrlKt.canonicalize$default(encodedPassword, 0, 0, " \"':;<=>@[]^`{}|/\\?#", true, false, false, false, 115, null);
            return this;
        }

        public final Builder host(String host) {
            Intrinsics.checkNotNullParameter(host, "host");
            String canonicalHost = _HostnamesCommonKt.toCanonicalHost(_UrlKt.percentDecode$default(host, 0, 0, false, 7, null));
            if (canonicalHost == null) {
                throw new IllegalArgumentException("unexpected host: " + host);
            }
            this.host = canonicalHost;
            return this;
        }

        public final Builder port(int port) {
            if (1 > port || port >= 65536) {
                throw new IllegalArgumentException(("unexpected port: " + port).toString());
            }
            this.port = port;
            return this;
        }

        public final Builder addPathSegment(String pathSegment) {
            Intrinsics.checkNotNullParameter(pathSegment, "pathSegment");
            push(pathSegment, 0, pathSegment.length(), false, false);
            return this;
        }

        public final Builder addPathSegments(String pathSegments) {
            Intrinsics.checkNotNullParameter(pathSegments, "pathSegments");
            return addPathSegments(pathSegments, false);
        }

        public final Builder addEncodedPathSegment(String encodedPathSegment) {
            Intrinsics.checkNotNullParameter(encodedPathSegment, "encodedPathSegment");
            push(encodedPathSegment, 0, encodedPathSegment.length(), false, true);
            return this;
        }

        public final Builder addEncodedPathSegments(String encodedPathSegments) {
            Intrinsics.checkNotNullParameter(encodedPathSegments, "encodedPathSegments");
            return addPathSegments(encodedPathSegments, true);
        }

        private final Builder addPathSegments(String pathSegments, boolean alreadyEncoded) {
            int i = 0;
            while (true) {
                int iDelimiterOffset = _UtilCommonKt.delimiterOffset(pathSegments, "/\\", i, pathSegments.length());
                this.push(pathSegments, i, iDelimiterOffset, iDelimiterOffset < pathSegments.length(), alreadyEncoded);
                i = iDelimiterOffset + 1;
                if (i > pathSegments.length()) {
                    return this;
                }
                this = this;
                pathSegments = pathSegments;
                alreadyEncoded = alreadyEncoded;
            }
        }

        public final Builder setPathSegment(int index, String pathSegment) {
            Intrinsics.checkNotNullParameter(pathSegment, "pathSegment");
            String strCanonicalize$default = _UrlKt.canonicalize$default(pathSegment, 0, 0, _UrlKt.PATH_SEGMENT_ENCODE_SET, false, false, false, false, 123, null);
            if (isDot(strCanonicalize$default) || isDotDot(strCanonicalize$default)) {
                throw new IllegalArgumentException(("unexpected path segment: " + pathSegment).toString());
            }
            this.encodedPathSegments.set(index, strCanonicalize$default);
            return this;
        }

        public final Builder setEncodedPathSegment(int index, String encodedPathSegment) {
            Intrinsics.checkNotNullParameter(encodedPathSegment, "encodedPathSegment");
            String strCanonicalize$default = _UrlKt.canonicalize$default(encodedPathSegment, 0, 0, _UrlKt.PATH_SEGMENT_ENCODE_SET, true, false, false, false, 115, null);
            this.encodedPathSegments.set(index, strCanonicalize$default);
            if (isDot(strCanonicalize$default) || isDotDot(strCanonicalize$default)) {
                throw new IllegalArgumentException(("unexpected path segment: " + encodedPathSegment).toString());
            }
            return this;
        }

        public final Builder removePathSegment(int index) {
            this.encodedPathSegments.remove(index);
            if (this.encodedPathSegments.isEmpty()) {
                this.encodedPathSegments.add("");
            }
            return this;
        }

        public final Builder encodedPath(String encodedPath) {
            Intrinsics.checkNotNullParameter(encodedPath, "encodedPath");
            if (!StringsKt.startsWith$default(encodedPath, InternalZipConstants.ZIP_FILE_SEPARATOR, false, 2, (Object) null)) {
                throw new IllegalArgumentException(("unexpected encodedPath: " + encodedPath).toString());
            }
            resolvePath(encodedPath, 0, encodedPath.length());
            return this;
        }

        public final Builder query(String query) {
            String strCanonicalize$default;
            this.encodedQueryNamesAndValues = (query == null || (strCanonicalize$default = _UrlKt.canonicalize$default(query, 0, 0, _UrlKt.QUERY_ENCODE_SET, false, false, true, false, 91, null)) == null) ? null : toQueryNamesAndValues(strCanonicalize$default);
            return this;
        }

        public final Builder encodedQuery(String encodedQuery) {
            String strCanonicalize$default;
            this.encodedQueryNamesAndValues = (encodedQuery == null || (strCanonicalize$default = _UrlKt.canonicalize$default(encodedQuery, 0, 0, _UrlKt.QUERY_ENCODE_SET, true, false, true, false, 83, null)) == null) ? null : toQueryNamesAndValues(strCanonicalize$default);
            return this;
        }

        public final Builder addQueryParameter(String name, String value) {
            Intrinsics.checkNotNullParameter(name, "name");
            if (this.encodedQueryNamesAndValues == null) {
                this.encodedQueryNamesAndValues = new ArrayList();
            }
            List<String> list = this.encodedQueryNamesAndValues;
            Intrinsics.checkNotNull(list);
            list.add(_UrlKt.canonicalize$default(name, 0, 0, _UrlKt.QUERY_COMPONENT_ENCODE_SET, false, false, true, false, 91, null));
            List<String> list2 = this.encodedQueryNamesAndValues;
            Intrinsics.checkNotNull(list2);
            list2.add(value != null ? _UrlKt.canonicalize$default(value, 0, 0, _UrlKt.QUERY_COMPONENT_ENCODE_SET, false, false, true, false, 91, null) : null);
            return this;
        }

        public final Builder addEncodedQueryParameter(String encodedName, String encodedValue) {
            Intrinsics.checkNotNullParameter(encodedName, "encodedName");
            if (this.encodedQueryNamesAndValues == null) {
                this.encodedQueryNamesAndValues = new ArrayList();
            }
            List<String> list = this.encodedQueryNamesAndValues;
            Intrinsics.checkNotNull(list);
            list.add(_UrlKt.canonicalize$default(encodedName, 0, 0, _UrlKt.QUERY_COMPONENT_REENCODE_SET, true, false, true, false, 83, null));
            List<String> list2 = this.encodedQueryNamesAndValues;
            Intrinsics.checkNotNull(list2);
            list2.add(encodedValue != null ? _UrlKt.canonicalize$default(encodedValue, 0, 0, _UrlKt.QUERY_COMPONENT_REENCODE_SET, true, false, true, false, 83, null) : null);
            return this;
        }

        public final Builder setQueryParameter(String name, String value) {
            Intrinsics.checkNotNullParameter(name, "name");
            removeAllQueryParameters(name);
            addQueryParameter(name, value);
            return this;
        }

        public final Builder setEncodedQueryParameter(String encodedName, String encodedValue) {
            Intrinsics.checkNotNullParameter(encodedName, "encodedName");
            removeAllEncodedQueryParameters(encodedName);
            addEncodedQueryParameter(encodedName, encodedValue);
            return this;
        }

        public final Builder removeAllQueryParameters(String name) {
            Intrinsics.checkNotNullParameter(name, "name");
            if (this.encodedQueryNamesAndValues == null) {
                return this;
            }
            removeAllCanonicalQueryParameters(_UrlKt.canonicalize$default(name, 0, 0, _UrlKt.QUERY_COMPONENT_ENCODE_SET, false, false, true, false, 91, null));
            return this;
        }

        public final Builder removeAllEncodedQueryParameters(String encodedName) {
            Intrinsics.checkNotNullParameter(encodedName, "encodedName");
            if (this.encodedQueryNamesAndValues == null) {
                return this;
            }
            removeAllCanonicalQueryParameters(_UrlKt.canonicalize$default(encodedName, 0, 0, _UrlKt.QUERY_COMPONENT_REENCODE_SET, true, false, true, false, 83, null));
            return this;
        }

        private final void removeAllCanonicalQueryParameters(String canonicalName) {
            List<String> list = this.encodedQueryNamesAndValues;
            Intrinsics.checkNotNull(list);
            int size = list.size() - 2;
            int progressionLastElement = ProgressionUtilKt.getProgressionLastElement(size, 0, -2);
            if (progressionLastElement > size) {
                return;
            }
            while (true) {
                List<String> list2 = this.encodedQueryNamesAndValues;
                Intrinsics.checkNotNull(list2);
                if (Intrinsics.areEqual(canonicalName, list2.get(size))) {
                    List<String> list3 = this.encodedQueryNamesAndValues;
                    Intrinsics.checkNotNull(list3);
                    list3.remove(size + 1);
                    List<String> list4 = this.encodedQueryNamesAndValues;
                    Intrinsics.checkNotNull(list4);
                    list4.remove(size);
                    List<String> list5 = this.encodedQueryNamesAndValues;
                    Intrinsics.checkNotNull(list5);
                    if (list5.isEmpty()) {
                        this.encodedQueryNamesAndValues = null;
                        return;
                    }
                }
                if (size == progressionLastElement) {
                    return;
                } else {
                    size -= 2;
                }
            }
        }

        public final Builder fragment(String fragment) {
            this.encodedFragment = fragment != null ? _UrlKt.canonicalize$default(fragment, 0, 0, "", false, false, false, true, 59, null) : null;
            return this;
        }

        public final Builder encodedFragment(String encodedFragment) {
            this.encodedFragment = encodedFragment != null ? _UrlKt.canonicalize$default(encodedFragment, 0, 0, "", true, false, false, true, 51, null) : null;
            return this;
        }

        public final Builder reencodeForUri$okhttp() {
            String str = this.host;
            this.host = str != null ? new Regex("[\"<>^`{|}]").replace(str, "") : null;
            int size = this.encodedPathSegments.size();
            for (int i = 0; i < size; i++) {
                List<String> list = this.encodedPathSegments;
                list.set(i, _UrlKt.canonicalize$default(list.get(i), 0, 0, _UrlKt.PATH_SEGMENT_ENCODE_SET_URI, true, true, false, false, 99, null));
            }
            List<String> list2 = this.encodedQueryNamesAndValues;
            if (list2 != null) {
                int size2 = list2.size();
                for (int i2 = 0; i2 < size2; i2++) {
                    String str2 = list2.get(i2);
                    list2.set(i2, str2 != null ? _UrlKt.canonicalize$default(str2, 0, 0, _UrlKt.QUERY_COMPONENT_ENCODE_SET_URI, true, true, true, false, 67, null) : null);
                }
            }
            String str3 = this.encodedFragment;
            this.encodedFragment = str3 != null ? _UrlKt.canonicalize$default(str3, 0, 0, _UrlKt.FRAGMENT_ENCODE_SET_URI, true, true, false, true, 35, null) : null;
            return this;
        }

        public final HttpUrl build() {
            ArrayList arrayList;
            String str = this.scheme;
            if (str == null) {
                throw new IllegalStateException("scheme == null");
            }
            String strPercentDecode$default = _UrlKt.percentDecode$default(this.encodedUsername, 0, 0, false, 7, null);
            String strPercentDecode$default2 = _UrlKt.percentDecode$default(this.encodedPassword, 0, 0, false, 7, null);
            String str2 = this.host;
            if (str2 == null) {
                throw new IllegalStateException("host == null");
            }
            int iEffectivePort = effectivePort();
            List<String> list = this.encodedPathSegments;
            ArrayList arrayList2 = new ArrayList(CollectionsKt.collectionSizeOrDefault(list, 10));
            Iterator<T> it = list.iterator();
            while (it.hasNext()) {
                arrayList2.add(_UrlKt.percentDecode$default((String) it.next(), 0, 0, false, 7, null));
            }
            ArrayList arrayList3 = arrayList2;
            List<String> list2 = this.encodedQueryNamesAndValues;
            if (list2 != null) {
                List<String> list3 = list2;
                ArrayList arrayList4 = new ArrayList(CollectionsKt.collectionSizeOrDefault(list3, 10));
                for (String str3 : list3) {
                    arrayList4.add(str3 != null ? _UrlKt.percentDecode$default(str3, 0, 0, true, 3, null) : null);
                }
                arrayList = arrayList4;
            } else {
                arrayList = null;
            }
            String str4 = this.encodedFragment;
            return new HttpUrl(str, strPercentDecode$default, strPercentDecode$default2, str2, iEffectivePort, arrayList3, arrayList, str4 != null ? _UrlKt.percentDecode$default(str4, 0, 0, false, 7, null) : null, toString(), null);
        }

        private final int effectivePort() {
            int i = this.port;
            if (i != -1) {
                return i;
            }
            Companion companion = HttpUrl.INSTANCE;
            String str = this.scheme;
            Intrinsics.checkNotNull(str);
            return companion.defaultPort(str);
        }

        /* JADX WARN: Code duplicated, block: B:29:0x008f  */
        public String toString() {
            StringBuilder sb = new StringBuilder();
            String str = this.scheme;
            if (str != null) {
                sb.append(str);
                sb.append("://");
            } else {
                sb.append("//");
            }
            if (this.encodedUsername.length() > 0 || this.encodedPassword.length() > 0) {
                sb.append(this.encodedUsername);
                if (this.encodedPassword.length() > 0) {
                    sb.append(CoreConstants.COLON_CHAR);
                    sb.append(this.encodedPassword);
                }
                sb.append('@');
            }
            String str2 = this.host;
            if (str2 != null) {
                Intrinsics.checkNotNull(str2);
                if (StringsKt.contains$default((CharSequence) str2, CoreConstants.COLON_CHAR, false, 2, (Object) null)) {
                    sb.append('[');
                    sb.append(this.host);
                    sb.append(']');
                } else {
                    sb.append(this.host);
                }
            }
            if (this.port != -1 || this.scheme != null) {
                int iEffectivePort = effectivePort();
                if (this.scheme != null) {
                    Companion companion = HttpUrl.INSTANCE;
                    String str3 = this.scheme;
                    Intrinsics.checkNotNull(str3);
                    if (iEffectivePort != companion.defaultPort(str3)) {
                        sb.append(CoreConstants.COLON_CHAR);
                        sb.append(iEffectivePort);
                    }
                } else {
                    sb.append(CoreConstants.COLON_CHAR);
                    sb.append(iEffectivePort);
                }
            }
            toPathString(this.encodedPathSegments, sb);
            if (this.encodedQueryNamesAndValues != null) {
                sb.append('?');
                Companion companion2 = HttpUrl.INSTANCE;
                List<String> list = this.encodedQueryNamesAndValues;
                Intrinsics.checkNotNull(list);
                companion2.toQueryString(list, sb);
            }
            if (this.encodedFragment != null) {
                sb.append('#');
                sb.append(this.encodedFragment);
            }
            return sb.toString();
        }

        private final void toPathString(List<String> list, StringBuilder sb) {
            int size = list.size();
            for (int i = 0; i < size; i++) {
                sb.append('/');
                sb.append(list.get(i));
            }
        }

        public final Builder parse$okhttp(HttpUrl base, String input) {
            int iDelimiterOffset;
            boolean z;
            int i;
            int i2;
            char c;
            String input2 = input;
            Intrinsics.checkNotNullParameter(input2, "input");
            int iIndexOfFirstNonAsciiWhitespace$default = _UtilCommonKt.indexOfFirstNonAsciiWhitespace$default(input2, 0, 0, 3, null);
            int iIndexOfLastNonAsciiWhitespace$default = _UtilCommonKt.indexOfLastNonAsciiWhitespace$default(input2, iIndexOfFirstNonAsciiWhitespace$default, 0, 2, null);
            int iSchemeDelimiterOffset = schemeDelimiterOffset(input2, iIndexOfFirstNonAsciiWhitespace$default, iIndexOfLastNonAsciiWhitespace$default);
            boolean z2 = true;
            if (iSchemeDelimiterOffset != -1) {
                if (StringsKt.startsWith(input2, IRichEditor.SCHEME_HTTPS, iIndexOfFirstNonAsciiWhitespace$default, true)) {
                    this.scheme = "https";
                    iIndexOfFirstNonAsciiWhitespace$default += 6;
                } else if (StringsKt.startsWith(input2, IRichEditor.SCHEME_HTTP, iIndexOfFirstNonAsciiWhitespace$default, true)) {
                    this.scheme = "http";
                    iIndexOfFirstNonAsciiWhitespace$default += 5;
                } else {
                    StringBuilder sb = new StringBuilder("Expected URL scheme 'http' or 'https' but was '");
                    String strSubstring = input2.substring(0, iSchemeDelimiterOffset);
                    Intrinsics.checkNotNullExpressionValue(strSubstring, "substring(...)");
                    throw new IllegalArgumentException(sb.append(strSubstring).append(CoreConstants.SINGLE_QUOTE_CHAR).toString());
                }
            } else if (base != null) {
                this.scheme = base.scheme();
            } else {
                throw new IllegalArgumentException("Expected URL scheme 'http' or 'https' but no scheme was found for " + (input2.length() > 6 ? StringsKt.take(input2, 6) + "..." : input2));
            }
            int iSlashCount = slashCount(input2, iIndexOfFirstNonAsciiWhitespace$default, iIndexOfLastNonAsciiWhitespace$default);
            char c2 = '?';
            char c3 = '#';
            if (iSlashCount >= 2 || base == null || !Intrinsics.areEqual(base.scheme(), this.scheme)) {
                boolean z3 = false;
                boolean z4 = false;
                int i3 = iIndexOfFirstNonAsciiWhitespace$default + iSlashCount;
                while (true) {
                    iDelimiterOffset = _UtilCommonKt.delimiterOffset(input2, "@/\\?#", i3, iIndexOfLastNonAsciiWhitespace$default);
                    byte bCharAt = iDelimiterOffset != iIndexOfLastNonAsciiWhitespace$default ? input2.charAt(iDelimiterOffset) : (byte) -1;
                    if (bCharAt == -1 || bCharAt == c3 || bCharAt == 47 || bCharAt == 92 || bCharAt == c2) {
                        break;
                    }
                    if (bCharAt == 64) {
                        if (!z3) {
                            int iDelimiterOffset2 = _UtilCommonKt.delimiterOffset(input2, CoreConstants.COLON_CHAR, i3, iDelimiterOffset);
                            z = z2;
                            String strCanonicalize$default = _UrlKt.canonicalize$default(input2, i3, iDelimiterOffset2, " \"':;<=>@[]^`{}|/\\?#", true, false, false, false, 112, null);
                            if (z4) {
                                strCanonicalize$default = this.encodedUsername + "%40" + strCanonicalize$default;
                            }
                            this.encodedUsername = strCanonicalize$default;
                            if (iDelimiterOffset2 != iDelimiterOffset) {
                                i2 = iDelimiterOffset;
                                this.encodedPassword = _UrlKt.canonicalize$default(input, iDelimiterOffset2 + 1, i2, " \"':;<=>@[]^`{}|/\\?#", true, false, false, false, 112, null);
                                z3 = z;
                            } else {
                                i2 = iDelimiterOffset;
                            }
                            input2 = input;
                            i = i2;
                            z4 = z;
                        } else {
                            z = z2;
                            input2 = input;
                            i = iDelimiterOffset;
                            this.encodedPassword += "%40" + _UrlKt.canonicalize$default(input2, i3, iDelimiterOffset, " \"':;<=>@[]^`{}|/\\?#", true, false, false, false, 112, null);
                        }
                        i3 = i + 1;
                        z2 = z;
                        c3 = '#';
                        c2 = '?';
                    }
                }
                int iPortColonOffset = portColonOffset(input2, i3, iDelimiterOffset);
                int i4 = iPortColonOffset + 1;
                if (i4 < iDelimiterOffset) {
                    this.host = _HostnamesCommonKt.toCanonicalHost(_UrlKt.percentDecode$default(input2, i3, iPortColonOffset, false, 4, null));
                    int port = parsePort(input2, i4, iDelimiterOffset);
                    this.port = port;
                    if (port == -1) {
                        StringBuilder sb2 = new StringBuilder("Invalid URL port: \"");
                        String strSubstring2 = input2.substring(i4, iDelimiterOffset);
                        Intrinsics.checkNotNullExpressionValue(strSubstring2, "substring(...)");
                        throw new IllegalArgumentException(sb2.append(strSubstring2).append('\"').toString().toString());
                    }
                } else {
                    this.host = _HostnamesCommonKt.toCanonicalHost(_UrlKt.percentDecode$default(input2, i3, iPortColonOffset, false, 4, null));
                    Companion companion = HttpUrl.INSTANCE;
                    String str = this.scheme;
                    Intrinsics.checkNotNull(str);
                    this.port = companion.defaultPort(str);
                }
                if (this.host == null) {
                    StringBuilder sb3 = new StringBuilder("Invalid URL host: \"");
                    String strSubstring3 = input2.substring(i3, iPortColonOffset);
                    Intrinsics.checkNotNullExpressionValue(strSubstring3, "substring(...)");
                    throw new IllegalArgumentException(sb3.append(strSubstring3).append('\"').toString().toString());
                }
                iIndexOfFirstNonAsciiWhitespace$default = iDelimiterOffset;
            } else {
                this.encodedUsername = base.encodedUsername();
                this.encodedPassword = base.encodedPassword();
                this.host = base.host();
                this.port = base.port();
                this.encodedPathSegments.clear();
                this.encodedPathSegments.addAll(base.encodedPathSegments());
                if (iIndexOfFirstNonAsciiWhitespace$default == iIndexOfLastNonAsciiWhitespace$default || input2.charAt(iIndexOfFirstNonAsciiWhitespace$default) == '#') {
                    encodedQuery(base.encodedQuery());
                }
            }
            int iDelimiterOffset3 = _UtilCommonKt.delimiterOffset(input2, "?#", iIndexOfFirstNonAsciiWhitespace$default, iIndexOfLastNonAsciiWhitespace$default);
            resolvePath(input2, iIndexOfFirstNonAsciiWhitespace$default, iDelimiterOffset3);
            if (iDelimiterOffset3 >= iIndexOfLastNonAsciiWhitespace$default || input2.charAt(iDelimiterOffset3) != '?') {
                c = '#';
            } else {
                c = '#';
                int iDelimiterOffset4 = _UtilCommonKt.delimiterOffset(input2, '#', iDelimiterOffset3, iIndexOfLastNonAsciiWhitespace$default);
                this.encodedQueryNamesAndValues = toQueryNamesAndValues(_UrlKt.canonicalize$default(input2, iDelimiterOffset3 + 1, iDelimiterOffset4, _UrlKt.QUERY_ENCODE_SET, true, false, true, false, 80, null));
                iDelimiterOffset3 = iDelimiterOffset4;
            }
            if (iDelimiterOffset3 < iIndexOfLastNonAsciiWhitespace$default && input2.charAt(iDelimiterOffset3) == c) {
                this.encodedFragment = _UrlKt.canonicalize$default(input2, iDelimiterOffset3 + 1, iIndexOfLastNonAsciiWhitespace$default, "", true, false, false, true, 48, null);
            }
            return this;
        }

        private final void resolvePath(String input, int startPos, int limit) {
            if (startPos == limit) {
                return;
            }
            char cCharAt = input.charAt(startPos);
            if (cCharAt == '/' || cCharAt == '\\') {
                this.encodedPathSegments.clear();
                this.encodedPathSegments.add("");
                startPos++;
            } else {
                List<String> list = this.encodedPathSegments;
                list.set(list.size() - 1, "");
            }
            int i = startPos;
            while (i < limit) {
                int iDelimiterOffset = _UtilCommonKt.delimiterOffset(input, "/\\", i, limit);
                boolean z = iDelimiterOffset < limit;
                this = this;
                input = input;
                this.push(input, i, iDelimiterOffset, z, true);
                i = z ? iDelimiterOffset + 1 : iDelimiterOffset;
            }
        }

        private final void push(String input, int pos, int limit, boolean addTrailingSlash, boolean alreadyEncoded) {
            String strCanonicalize$default = _UrlKt.canonicalize$default(input, pos, limit, _UrlKt.PATH_SEGMENT_ENCODE_SET, alreadyEncoded, false, false, false, 112, null);
            if (isDot(strCanonicalize$default)) {
                return;
            }
            if (isDotDot(strCanonicalize$default)) {
                pop();
                return;
            }
            List<String> list = this.encodedPathSegments;
            if (list.get(list.size() - 1).length() == 0) {
                List<String> list2 = this.encodedPathSegments;
                list2.set(list2.size() - 1, strCanonicalize$default);
            } else {
                this.encodedPathSegments.add(strCanonicalize$default);
            }
            if (addTrailingSlash) {
                this.encodedPathSegments.add("");
            }
        }

        private final void pop() {
            List<String> list = this.encodedPathSegments;
            if (list.remove(list.size() - 1).length() == 0 && !this.encodedPathSegments.isEmpty()) {
                List<String> list2 = this.encodedPathSegments;
                list2.set(list2.size() - 1, "");
            } else {
                this.encodedPathSegments.add("");
            }
        }

        private final boolean isDot(String input) {
            return Intrinsics.areEqual(input, ".") || StringsKt.equals(input, "%2e", true);
        }

        private final boolean isDotDot(String input) {
            return Intrinsics.areEqual(input, CallerDataConverter.DEFAULT_RANGE_DELIMITER) || StringsKt.equals(input, "%2e.", true) || StringsKt.equals(input, ".%2e", true) || StringsKt.equals(input, "%2e%2e", true);
        }

        private final List<String> toQueryNamesAndValues(String str) {
            ArrayList arrayList = new ArrayList();
            int i = 0;
            while (i <= str.length()) {
                String str2 = str;
                int iIndexOf$default = StringsKt.indexOf$default((CharSequence) str2, Typography.amp, i, false, 4, (Object) null);
                if (iIndexOf$default == -1) {
                    iIndexOf$default = str.length();
                }
                int iIndexOf$default2 = StringsKt.indexOf$default((CharSequence) str2, '=', i, false, 4, (Object) null);
                if (iIndexOf$default2 == -1 || iIndexOf$default2 > iIndexOf$default) {
                    String strSubstring = str.substring(i, iIndexOf$default);
                    Intrinsics.checkNotNullExpressionValue(strSubstring, "substring(...)");
                    arrayList.add(strSubstring);
                    arrayList.add(null);
                } else {
                    String strSubstring2 = str.substring(i, iIndexOf$default2);
                    Intrinsics.checkNotNullExpressionValue(strSubstring2, "substring(...)");
                    arrayList.add(strSubstring2);
                    String strSubstring3 = str.substring(iIndexOf$default2 + 1, iIndexOf$default);
                    Intrinsics.checkNotNullExpressionValue(strSubstring3, "substring(...)");
                    arrayList.add(strSubstring3);
                }
                i = iIndexOf$default + 1;
            }
            return arrayList;
        }

        private final int schemeDelimiterOffset(String input, int pos, int limit) {
            if (limit - pos < 2) {
                return -1;
            }
            char cCharAt = input.charAt(pos);
            if ((Intrinsics.compare((int) cCharAt, 97) >= 0 && Intrinsics.compare((int) cCharAt, 122) <= 0) || (Intrinsics.compare((int) cCharAt, 65) >= 0 && Intrinsics.compare((int) cCharAt, 90) <= 0)) {
                while (true) {
                    pos++;
                    if (pos >= limit) {
                        break;
                    }
                    char cCharAt2 = input.charAt(pos);
                    if ('a' > cCharAt2 || cCharAt2 >= '{') {
                        if ('A' > cCharAt2 || cCharAt2 >= '[') {
                            if ('0' > cCharAt2 || cCharAt2 >= ':') {
                                if (cCharAt2 != '+' && cCharAt2 != '-' && cCharAt2 != '.') {
                                    if (cCharAt2 == ':') {
                                        return pos;
                                    }
                                }
                            }
                        }
                    }
                }
            }
            return -1;
        }

        private final int slashCount(String str, int i, int i2) {
            int i3 = 0;
            while (i < i2) {
                char cCharAt = str.charAt(i);
                if (cCharAt != '/' && cCharAt != '\\') {
                    break;
                }
                i3++;
                i++;
            }
            return i3;
        }

        private final int portColonOffset(String input, int pos, int limit) {
            while (pos < limit) {
                char cCharAt = input.charAt(pos);
                if (cCharAt == ':') {
                    return pos;
                }
                if (cCharAt == '[') {
                    do {
                        pos++;
                        if (pos >= limit) {
                            break;
                        }
                    } while (input.charAt(pos) != ']');
                }
                pos++;
            }
            return limit;
        }

        private final int parsePort(String input, int pos, int limit) {
            try {
                int i = Integer.parseInt(_UrlKt.canonicalize$default(input, pos, limit, "", false, false, false, false, 120, null));
                if (1 > i || i >= 65536) {
                    return -1;
                }
                return i;
            } catch (NumberFormatException unused) {
            }
        }
    }

    /* JADX INFO: compiled from: HttpUrl.kt */
    @Metadata(d1 = {"\u0000@\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0003\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\u0002\n\u0002\u0010 \n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\b\u0086\u0003\u0018\u00002\u00020\u0001B\t\b\u0002¢\u0006\u0004\b\u0002\u0010\u0003J\u0010\u0010\u0004\u001a\u00020\u00052\u0006\u0010\u0006\u001a\u00020\u0007H\u0007J \u0010\b\u001a\u00020\t*\n\u0012\u0006\u0012\u0004\u0018\u00010\u00070\n2\n\u0010\u000b\u001a\u00060\fj\u0002`\rH\u0002J\u0011\u0010\u000e\u001a\u00020\u000f*\u00020\u0007H\u0007¢\u0006\u0002\b\u0010J\u0013\u0010\u0011\u001a\u0004\u0018\u00010\u000f*\u00020\u0007H\u0007¢\u0006\u0002\b\u0012J\u0013\u0010\u0011\u001a\u0004\u0018\u00010\u000f*\u00020\u0013H\u0007¢\u0006\u0002\b\u0010J\u0013\u0010\u0011\u001a\u0004\u0018\u00010\u000f*\u00020\u0014H\u0007¢\u0006\u0002\b\u0010J\u0015\u0010\u0010\u001a\u00020\u000f2\u0006\u0010\u0015\u001a\u00020\u0007H\u0007¢\u0006\u0002\b\u0016J\u0017\u0010\u0012\u001a\u0004\u0018\u00010\u000f2\u0006\u0010\u0015\u001a\u00020\u0007H\u0007¢\u0006\u0002\b\u0017J\u0017\u0010\u0010\u001a\u0004\u0018\u00010\u000f2\u0006\u0010\u0015\u001a\u00020\u0013H\u0007¢\u0006\u0002\b\u0016J\u0017\u0010\u0010\u001a\u0004\u0018\u00010\u000f2\u0006\u0010\u0018\u001a\u00020\u0014H\u0007¢\u0006\u0002\b\u0016¨\u0006\u0019"}, d2 = {"Lokhttp3/HttpUrl$Companion;", "", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "()V", "defaultPort", "", ah.C, "", "toQueryString", "", "", "out", "Ljava/lang/StringBuilder;", "Lkotlin/text/StringBuilder;", "toHttpUrl", "Lokhttp3/HttpUrl;", CallMethod.METHOD_GET, "toHttpUrlOrNull", "parse", "Ljava/net/URL;", "Ljava/net/URI;", "url", "-deprecated_get", "-deprecated_parse", CallMethod.ARG_URI, "okhttp"}, k = 1, mv = {2, 2, 0}, xi = 48)
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }

        @JvmStatic
        public final int defaultPort(String scheme) {
            Intrinsics.checkNotNullParameter(scheme, "scheme");
            if (Intrinsics.areEqual(scheme, "http")) {
                return 80;
            }
            if (Intrinsics.areEqual(scheme, "https")) {
                return R2.attr.backgroundPrimaryDisableColor;
            }
            return -1;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public final void toQueryString(List<String> list, StringBuilder sb) {
            IntProgression intProgressionStep = RangesKt.step(RangesKt.until(0, list.size()), 2);
            int first = intProgressionStep.getFirst();
            int last = intProgressionStep.getLast();
            int step = intProgressionStep.getStep();
            if ((step <= 0 || first > last) && (step >= 0 || last > first)) {
                return;
            }
            while (true) {
                String str = list.get(first);
                String str2 = list.get(first + 1);
                if (first > 0) {
                    sb.append(Typography.amp);
                }
                sb.append(str);
                if (str2 != null) {
                    sb.append('=');
                    sb.append(str2);
                }
                if (first == last) {
                    return;
                } else {
                    first += step;
                }
            }
        }

        @JvmStatic
        public final HttpUrl get(String str) {
            Intrinsics.checkNotNullParameter(str, "<this>");
            return new Builder().parse$okhttp(null, str).build();
        }

        @JvmStatic
        public final HttpUrl parse(String str) {
            Intrinsics.checkNotNullParameter(str, "<this>");
            try {
                return get(str);
            } catch (IllegalArgumentException unused) {
                return null;
            }
        }

        @JvmStatic
        public final HttpUrl get(URL url) {
            Intrinsics.checkNotNullParameter(url, "<this>");
            String string = url.toString();
            Intrinsics.checkNotNullExpressionValue(string, "toString(...)");
            return parse(string);
        }

        @JvmStatic
        public final HttpUrl get(URI uri) {
            Intrinsics.checkNotNullParameter(uri, "<this>");
            String string = uri.toString();
            Intrinsics.checkNotNullExpressionValue(string, "toString(...)");
            return parse(string);
        }

        @Deprecated(level = DeprecationLevel.ERROR, message = "moved to extension function", replaceWith = @ReplaceWith(expression = "url.toHttpUrl()", imports = {"okhttp3.HttpUrl.Companion.toHttpUrl"}))
        /* JADX INFO: renamed from: -deprecated_get, reason: not valid java name */
        public final HttpUrl m4440deprecated_get(String url) {
            Intrinsics.checkNotNullParameter(url, "url");
            return get(url);
        }

        @Deprecated(level = DeprecationLevel.ERROR, message = "moved to extension function", replaceWith = @ReplaceWith(expression = "url.toHttpUrlOrNull()", imports = {"okhttp3.HttpUrl.Companion.toHttpUrlOrNull"}))
        /* JADX INFO: renamed from: -deprecated_parse, reason: not valid java name */
        public final HttpUrl m4443deprecated_parse(String url) {
            Intrinsics.checkNotNullParameter(url, "url");
            return parse(url);
        }

        @Deprecated(level = DeprecationLevel.ERROR, message = "moved to extension function", replaceWith = @ReplaceWith(expression = "url.toHttpUrlOrNull()", imports = {"okhttp3.HttpUrl.Companion.toHttpUrlOrNull"}))
        /* JADX INFO: renamed from: -deprecated_get, reason: not valid java name */
        public final HttpUrl m4442deprecated_get(URL url) {
            Intrinsics.checkNotNullParameter(url, "url");
            return get(url);
        }

        @Deprecated(level = DeprecationLevel.ERROR, message = "moved to extension function", replaceWith = @ReplaceWith(expression = "uri.toHttpUrlOrNull()", imports = {"okhttp3.HttpUrl.Companion.toHttpUrlOrNull"}))
        /* JADX INFO: renamed from: -deprecated_get, reason: not valid java name */
        public final HttpUrl m4441deprecated_get(URI uri) {
            Intrinsics.checkNotNullParameter(uri, "uri");
            return get(uri);
        }
    }
}
