package kotlinx.coroutines.debug.internal;

import kotlin.Metadata;

/* JADX INFO: compiled from: DebugProbesImpl.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000\b\n\u0000\n\u0002\u0010\u000e\n\u0000\u001a\f\u0010\u0000\u001a\u00020\u0001*\u00020\u0001H\u0002¨\u0006\u0002"}, d2 = {"repr", "", "kotlinx-coroutines-core"}, k = 2, mv = {2, 1, 0}, xi = 48)
public final class DebugProbesImplKt {
    /* JADX INFO: Access modifiers changed from: private */
    public static final String repr(String str) {
        StringBuilder sb = new StringBuilder("\"");
        int length = str.length();
        for (int i = 0; i < length; i++) {
            char cCharAt = str.charAt(i);
            if (cCharAt != '\r') {
                if (cCharAt != '\"') {
                    if (cCharAt == '\\') {
                        sb.append("\\\\");
                    } else {
                        switch (cCharAt) {
                            case '\b':
                                sb.append("\\b");
                                break;
                            case '\t':
                                sb.append("\\t");
                                break;
                            case '\n':
                                sb.append("\\n");
                                break;
                            default:
                                sb.append(cCharAt);
                                break;
                        }
                    }
                } else {
                    sb.append("\\\"");
                }
            } else {
                sb.append("\\r");
            }
        }
        sb.append('\"');
        return sb.toString();
    }
}
