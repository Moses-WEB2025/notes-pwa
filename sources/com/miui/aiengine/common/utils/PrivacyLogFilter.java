package com.miui.aiengine.common.utils;

import com.market.sdk.reflect.ReflectUtilsForMiui;
import java.util.regex.Pattern;
import kotlin.Lazy;
import kotlin.LazyKt;
import kotlin.Metadata;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: PrivacyLogFilter.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000\u001c\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\n\n\u0002\u0010\u000e\n\u0002\b\u0005\bÆ\u0002\u0018\u00002\u00020\u0001B\t\b\u0002¢\u0006\u0004\b\u0002\u0010\u0003J\b\u0010\r\u001a\u00020\u0005H\u0002J\b\u0010\u000e\u001a\u00020\u0005H\u0002J\u000e\u0010\u000f\u001a\u00020\u00102\u0006\u0010\u0011\u001a\u00020\u0010J \u0010\u0012\u001a\u00020\u00102\u0006\u0010\u0011\u001a\u00020\u00102\u0006\u0010\u0013\u001a\u00020\u00052\u0006\u0010\u0014\u001a\u00020\u0010H\u0002R\u001b\u0010\u0004\u001a\u00020\u00058BX\u0082\u0084\u0002¢\u0006\f\n\u0004\b\b\u0010\t\u001a\u0004\b\u0006\u0010\u0007R\u001b\u0010\n\u001a\u00020\u00058BX\u0082\u0084\u0002¢\u0006\f\n\u0004\b\f\u0010\t\u001a\u0004\b\u000b\u0010\u0007¨\u0006\u0015"}, d2 = {"Lcom/miui/aiengine/common/utils/PrivacyLogFilter;", "", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "()V", "ipv4Pattern", "Ljava/util/regex/Pattern;", "getIpv4Pattern", "()Ljava/util/regex/Pattern;", "ipv4Pattern$delegate", "Lkotlin/Lazy;", "ipv6Pattern", "getIpv6Pattern", "ipv6Pattern$delegate", "initIpv4PatternString", "initIpv6PatternString", "filterPrivacyLog", "", "log", "replacePrivacyLog", "privacyReplacePattern", "replacement", "net_release"}, k = 1, mv = {2, 2, 0}, xi = 48)
public final class PrivacyLogFilter {
    public static final PrivacyLogFilter INSTANCE = new PrivacyLogFilter();

    /* JADX INFO: renamed from: ipv4Pattern$delegate, reason: from kotlin metadata */
    private static final Lazy ipv4Pattern = LazyKt.lazy(new Function0() { // from class: com.miui.aiengine.common.utils.PrivacyLogFilter$$ExternalSyntheticLambda0
        @Override // kotlin.jvm.functions.Function0
        public final Object invoke() {
            return PrivacyLogFilter.ipv4Pattern_delegate$lambda$0();
        }
    });

    /* JADX INFO: renamed from: ipv6Pattern$delegate, reason: from kotlin metadata */
    private static final Lazy ipv6Pattern = LazyKt.lazy(new Function0() { // from class: com.miui.aiengine.common.utils.PrivacyLogFilter$$ExternalSyntheticLambda1
        @Override // kotlin.jvm.functions.Function0
        public final Object invoke() {
            return PrivacyLogFilter.ipv6Pattern_delegate$lambda$1();
        }
    });

    private PrivacyLogFilter() {
    }

    private final Pattern getIpv4Pattern() {
        return (Pattern) ipv4Pattern.getValue();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Pattern ipv4Pattern_delegate$lambda$0() {
        return INSTANCE.initIpv4PatternString();
    }

    private final Pattern getIpv6Pattern() {
        return (Pattern) ipv6Pattern.getValue();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Pattern ipv6Pattern_delegate$lambda$1() {
        return INSTANCE.initIpv6PatternString();
    }

    private final Pattern initIpv4PatternString() {
        Pattern patternCompile = Pattern.compile("((\\d|[1-9]\\d|1\\d{2}|2[0-4]\\d|25[0-5])\\.){3}(\\d|[1-9]\\d|1\\d{2}|2[0-4]\\d|25[0-5])");
        Intrinsics.checkNotNullExpressionValue(patternCompile, "compile(...)");
        return patternCompile;
    }

    private final Pattern initIpv6PatternString() {
        Pattern patternCompile = Pattern.compile("(([0-9a-fA-F]{1,4}:){7,7}[0-9a-fA-F]{1,4}|([0-9a-fA-F]{1,4}:){2,7}:|([0-9a-fA-F]{1,4}:){1,6}:[0-9a-fA-F]{1,4}|([0-9a-fA-F]{1,4}:){1,5}(:[0-9a-fA-F]{1,4}){1,2}|([0-9a-fA-F]{1,4}:){1,4}(:[0-9a-fA-F]{1,4}){1,3}|([0-9a-fA-F]{1,4}:){1,3}(:[0-9a-fA-F]{1,4}){1,4}|([0-9a-fA-F]{1,4}:){1,2}(:[0-9a-fA-F]{1,4}){1,5}|[0-9a-fA-F]{1,4}:((:[0-9a-fA-F]{1,4}){1,6})|:((:[0-9a-fA-F]{1,4}){2,7})|fe80:(:[0-9a-fA-F]{0,4}){0,4}%[0-9a-zA-Z]{1,}|::(ffff(:0{1,4}){0,1}:){0,1}((25[0-5]|(2[0-4]|1{0,1}[0-9]){0,1}[0-9])\\.){3,3}(25[0-5]|(2[0-4]|1{0,1}[0-9]){0,1}[0-9])|([0-9a-fA-F]{1,4}:){1,4}:((25[0-5]|(2[0-4]|1{0,1}[0-9]){0,1}[0-9])\\.){3,3}(25[0-5]|(2[0-4]|1{0,1}[0-9]){0,1}[0-9]))");
        Intrinsics.checkNotNullExpressionValue(patternCompile, "compile(...)");
        return patternCompile;
    }

    public final String filterPrivacyLog(String log) {
        Intrinsics.checkNotNullParameter(log, "log");
        return replacePrivacyLog(replacePrivacyLog(log, getIpv4Pattern(), "@IPV4"), getIpv6Pattern(), "@IPV6");
    }

    private final String replacePrivacyLog(String log, Pattern privacyReplacePattern, String replacement) {
        String strReplaceAll = privacyReplacePattern.matcher(log).replaceAll(replacement);
        Intrinsics.checkNotNullExpressionValue(strReplaceAll, "replaceAll(...)");
        return strReplaceAll;
    }
}
