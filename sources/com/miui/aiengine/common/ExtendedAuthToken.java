package com.miui.aiengine.common;

import android.text.TextUtils;
import com.market.sdk.reflect.ReflectUtilsForMiui;
import java.util.List;
import java.util.ListIterator;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.Regex;

/* JADX INFO: compiled from: ExtendedAuthToken.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000\"\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000e\n\u0002\b\b\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0002\u0018\u0000 \u00102\u00020\u0001:\u0001\u0010B\u0019\b\u0002\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0003¢\u0006\u0004\b\u0005\u0010\u0006J\u0006\u0010\n\u001a\u00020\u0003J\u0013\u0010\u000b\u001a\u00020\f2\b\u0010\r\u001a\u0004\u0018\u00010\u0001H\u0096\u0002J\b\u0010\u000e\u001a\u00020\u000fH\u0016R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\bR\u0011\u0010\u0004\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\b¨\u0006\u0011"}, d2 = {"Lcom/miui/aiengine/common/ExtendedAuthToken;", "", "authToken", "", "security", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "(Ljava/lang/String;Ljava/lang/String;)V", "getAuthToken", "()Ljava/lang/String;", "getSecurity", "toPlain", "equals", "", "o", "hashCode", "", "Companion", "net_release"}, k = 1, mv = {2, 2, 0}, xi = 48)
public final class ExtendedAuthToken {

    /* JADX INFO: renamed from: Companion, reason: from kotlin metadata */
    public static final Companion INSTANCE = new Companion(null);
    private static final String SP = ",";
    private final String authToken;
    private final String security;

    public /* synthetic */ ExtendedAuthToken(String str, String str2, DefaultConstructorMarker defaultConstructorMarker) {
        this(str, str2);
    }

    private ExtendedAuthToken(String str, String str2) {
        this.authToken = str;
        this.security = str2;
    }

    public final String getAuthToken() {
        return this.authToken;
    }

    public final String getSecurity() {
        return this.security;
    }

    public final String toPlain() {
        return this.authToken + "," + this.security;
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || !Intrinsics.areEqual(getClass(), o.getClass())) {
            return false;
        }
        com.xiaomi.accountsdk.account.data.ExtendedAuthToken extendedAuthToken = (com.xiaomi.accountsdk.account.data.ExtendedAuthToken) o;
        return Intrinsics.areEqual(this.authToken, extendedAuthToken.authToken) && Intrinsics.areEqual(this.security, extendedAuthToken.security);
    }

    public int hashCode() {
        return (this.authToken.hashCode() * 31) + this.security.hashCode();
    }

    /* JADX INFO: compiled from: ExtendedAuthToken.kt */
    @Metadata(d1 = {"\u0000\u001a\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\b\u0086\u0003\u0018\u00002\u00020\u0001B\t\b\u0002¢\u0006\u0004\b\u0002\u0010\u0003J\u0016\u0010\u0006\u001a\u00020\u00072\u0006\u0010\b\u001a\u00020\u00052\u0006\u0010\t\u001a\u00020\u0005J\u0010\u0010\n\u001a\u0004\u0018\u00010\u00072\u0006\u0010\u000b\u001a\u00020\u0005R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082T¢\u0006\u0002\n\u0000¨\u0006\f"}, d2 = {"Lcom/miui/aiengine/common/ExtendedAuthToken$Companion;", "", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "()V", "SP", "", "build", "Lcom/miui/aiengine/common/ExtendedAuthToken;", "authToken", "security", "parse", "plain", "net_release"}, k = 1, mv = {2, 2, 0}, xi = 48)
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }

        public final ExtendedAuthToken build(String authToken, String security) {
            Intrinsics.checkNotNullParameter(authToken, "authToken");
            Intrinsics.checkNotNullParameter(security, "security");
            return new ExtendedAuthToken(authToken, security, null);
        }

        public final ExtendedAuthToken parse(String plain) {
            List listEmptyList;
            Intrinsics.checkNotNullParameter(plain, "plain");
            String str = plain;
            DefaultConstructorMarker defaultConstructorMarker = null;
            if (TextUtils.isEmpty(str)) {
                return null;
            }
            List<String> listSplit = new Regex(",").split(str, 0);
            if (!listSplit.isEmpty()) {
                ListIterator<String> listIterator = listSplit.listIterator(listSplit.size());
                while (true) {
                    if (listIterator.hasPrevious()) {
                        if (listIterator.previous().length() != 0) {
                            listEmptyList = CollectionsKt.take(listSplit, listIterator.nextIndex() + 1);
                            break;
                        }
                    } else {
                        listEmptyList = CollectionsKt.emptyList();
                        break;
                    }
                }
            } else {
                listEmptyList = CollectionsKt.emptyList();
                break;
            }
            String[] strArr = (String[]) listEmptyList.toArray(new String[0]);
            if (strArr.length != 2 || TextUtils.isEmpty(strArr[0]) || TextUtils.isEmpty(strArr[1])) {
                return null;
            }
            return new ExtendedAuthToken(strArr[0], strArr[1], defaultConstructorMarker);
        }
    }
}
