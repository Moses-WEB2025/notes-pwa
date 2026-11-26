package com.miui.notes.tool.util;

import com.market.sdk.reflect.ReflectUtilsForMiui;
import java.util.Iterator;
import kotlin.Metadata;
import kotlin.enums.EnumEntries;
import kotlin.enums.EnumEntriesKt;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* JADX INFO: compiled from: EncryptNewErrorCode.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0010\u0010\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u001c\b\u0086\u0081\u0002\u0018\u0000  2\b\u0012\u0004\u0012\u00020\u00000\u0001:\u0001 B\u0019\b\u0002\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005¢\u0006\u0004\b\u0006\u0010\u0007R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\b\u0010\tR\u0011\u0010\u0004\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\n\u0010\u000bj\u0002\b\fj\u0002\b\rj\u0002\b\u000ej\u0002\b\u000fj\u0002\b\u0010j\u0002\b\u0011j\u0002\b\u0012j\u0002\b\u0013j\u0002\b\u0014j\u0002\b\u0015j\u0002\b\u0016j\u0002\b\u0017j\u0002\b\u0018j\u0002\b\u0019j\u0002\b\u001aj\u0002\b\u001bj\u0002\b\u001cj\u0002\b\u001dj\u0002\b\u001ej\u0002\b\u001f¨\u0006!"}, d2 = {"Lcom/miui/notes/tool/util/EncryptNewErrorCode;", "", "code", "", "description", "", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "(Ljava/lang/String;IILjava/lang/String;)V", "getCode", "()I", "getDescription", "()Ljava/lang/String;", "OK", "KEY_NOT_EXIST", "KEY_ALREADY_EXISTED", "INVALID_PARAMETER", "UNSUPPORTED_PURPOSE", "INCOMPATIBLE_PURPOSE", "UNSUPPORTED_ALGORITHM", "INCOMPATIBLE_ALGORITHM", "UNSUPPORTED_KEY_SIZE", "UNSUPPORTED_BLOCK_MODE", "INCOMPATIBLE_BLOCK_MODE", "UNSUPPORTED_MAC_LENGTH", "UNSUPPORTED_PADDING_MODE", "INCOMPATIBLE_PADDING_MODE", "UNSUPPORTED_DIGEST", "INCOMPATIBLE_DIGEST", "ACCESS_DENIED", "SERVICE_DISCONNECT", "UNKNOWN_ERROR", "SERVICE_CONNECT_FAILED", "Companion", "app_PhoneGlobalRelease"}, k = 1, mv = {2, 2, 0}, xi = 48)
public enum EncryptNewErrorCode {
    OK(0, "success"),
    KEY_NOT_EXIST(-1, "APP key not exist"),
    KEY_ALREADY_EXISTED(-2, "APP key existed"),
    INVALID_PARAMETER(-3, "invalid parameter"),
    UNSUPPORTED_PURPOSE(-4, "unsupported purpose"),
    INCOMPATIBLE_PURPOSE(-5, "incompatible purpose"),
    UNSUPPORTED_ALGORITHM(-6, "unsupported algorithm"),
    INCOMPATIBLE_ALGORITHM(-7, "incompatible algorithm"),
    UNSUPPORTED_KEY_SIZE(-8, "unsupported key size"),
    UNSUPPORTED_BLOCK_MODE(-9, "unsupported block mode"),
    INCOMPATIBLE_BLOCK_MODE(-10, "incompatible block mode"),
    UNSUPPORTED_MAC_LENGTH(-11, "unsupported mac length"),
    UNSUPPORTED_PADDING_MODE(-12, "unsupported padding mode"),
    INCOMPATIBLE_PADDING_MODE(-13, "incompatible padding mode"),
    UNSUPPORTED_DIGEST(-14, "unsupported digest"),
    INCOMPATIBLE_DIGEST(-15, "incompatible digest"),
    ACCESS_DENIED(-16, "access denied"),
    SERVICE_DISCONNECT(-17, "service disconnect"),
    UNKNOWN_ERROR(-1000, "unknown error"),
    SERVICE_CONNECT_FAILED(-1001, "service connect failed");

    private final int code;
    private final String description;
    private static final /* synthetic */ EnumEntries $ENTRIES = EnumEntriesKt.enumEntries(values());

    /* JADX INFO: renamed from: Companion, reason: from kotlin metadata */
    public static final Companion INSTANCE = new Companion(null);

    public static EnumEntries<EncryptNewErrorCode> getEntries() {
        return $ENTRIES;
    }

    EncryptNewErrorCode(int i, String str) {
        this.code = i;
        this.description = str;
    }

    public final int getCode() {
        return this.code;
    }

    public final String getDescription() {
        return this.description;
    }

    /* JADX INFO: compiled from: EncryptNewErrorCode.kt */
    @Metadata(d1 = {"\u0000\u001e\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\t\b\u0002¢\u0006\u0004\b\u0002\u0010\u0003J\u0010\u0010\u0004\u001a\u0004\u0018\u00010\u00052\u0006\u0010\u0006\u001a\u00020\u0007J\u000e\u0010\b\u001a\u00020\t2\u0006\u0010\u0006\u001a\u00020\u0007¨\u0006\n"}, d2 = {"Lcom/miui/notes/tool/util/EncryptNewErrorCode$Companion;", "", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "()V", "fromCode", "Lcom/miui/notes/tool/util/EncryptNewErrorCode;", "code", "", "getErrorDescriptionsFormatted", "", "app_PhoneGlobalRelease"}, k = 1, mv = {2, 2, 0}, xi = 48)
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }

        public final EncryptNewErrorCode fromCode(int code) {
            EncryptNewErrorCode next;
            Iterator<EncryptNewErrorCode> it = EncryptNewErrorCode.getEntries().iterator();
            while (it.hasNext()) {
                next = it.next();
                if (next.getCode() == code) {
                    return next;
                }
            }
            next = null;
            return next;
        }

        public final String getErrorDescriptionsFormatted(int code) {
            String description;
            EncryptNewErrorCode encryptNewErrorCodeFromCode = fromCode(code);
            if (encryptNewErrorCodeFromCode == null || (description = encryptNewErrorCodeFromCode.getDescription()) == null) {
                description = "Unknown error code: " + code;
            }
            return "Error[code=" + code + ", des=" + description + "]";
        }
    }
}
