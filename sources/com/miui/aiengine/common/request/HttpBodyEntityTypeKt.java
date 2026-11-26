package com.miui.aiengine.common.request;

import kotlin.Metadata;
import kotlin.NoWhenBranchMatchedException;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: HttpBodyEntityType.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000\u0010\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\u001a\n\u0010\u0002\u001a\u00020\u0001*\u00020\u0003\u001a\n\u0010\u0004\u001a\u00020\u0003*\u00020\u0001\"\u000e\u0010\u0000\u001a\u00020\u0001X\u0086T¢\u0006\u0002\n\u0000¨\u0006\u0005"}, d2 = {"CONTENT_TYPE", "", "toHeaderString", "Lcom/miui/aiengine/common/request/HttpBodyEntityType;", "string2ContentType", "net_release"}, k = 2, mv = {2, 2, 0}, xi = 48)
public final class HttpBodyEntityTypeKt {
    public static final String CONTENT_TYPE = "content-type";

    /* JADX INFO: compiled from: HttpBodyEntityType.kt */
    @Metadata(k = 3, mv = {2, 2, 0}, xi = 48)
    public static final /* synthetic */ class WhenMappings {
        public static final /* synthetic */ int[] $EnumSwitchMapping$0;

        static {
            int[] iArr = new int[HttpBodyEntityType.values().length];
            try {
                iArr[HttpBodyEntityType.JSON.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                iArr[HttpBodyEntityType.FORM.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                iArr[HttpBodyEntityType.FORM_URLENCODED.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                iArr[HttpBodyEntityType.BINARY.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
            $EnumSwitchMapping$0 = iArr;
        }
    }

    public static final String toHeaderString(HttpBodyEntityType httpBodyEntityType) {
        Intrinsics.checkNotNullParameter(httpBodyEntityType, "<this>");
        int i = WhenMappings.$EnumSwitchMapping$0[httpBodyEntityType.ordinal()];
        if (i == 1) {
            return "application/json";
        }
        if (i == 2) {
            return "multipart/form-data";
        }
        if (i == 3) {
            return "application/x-www-form-urlencoded";
        }
        if (i != 4) {
            throw new NoWhenBranchMatchedException();
        }
        return "application/octet-stream";
    }

    /* JADX WARN: Failed to restore switch over string. Please report as a decompilation issue */
    public static final HttpBodyEntityType string2ContentType(String str) {
        Intrinsics.checkNotNullParameter(str, "<this>");
        switch (str.hashCode()) {
            case -1485569826:
                if (str.equals("application/x-www-form-urlencoded")) {
                    return HttpBodyEntityType.FORM_URLENCODED;
                }
                break;
            case -655019664:
                if (str.equals("multipart/form-data")) {
                    return HttpBodyEntityType.FORM;
                }
                break;
            case -43840953:
                if (str.equals("application/json")) {
                    return HttpBodyEntityType.JSON;
                }
                break;
            case 1178484637:
                if (str.equals("application/octet-stream")) {
                    return HttpBodyEntityType.BINARY;
                }
                break;
        }
        return HttpBodyEntityType.FORM;
    }
}
