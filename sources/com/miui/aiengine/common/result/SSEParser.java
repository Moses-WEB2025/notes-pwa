package com.miui.aiengine.common.result;

import com.market.sdk.reflect.ReflectUtilsForMiui;
import com.miui.aiengine.common.NetConfig;
import com.miui.aiengine.common.utils.Logger;
import com.miui.richeditor.schema.HtmlParser;
import com.xiaomi.mirror.synergy.CallMethod;
import java.io.InputStream;
import kotlin.Metadata;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;

/* JADX INFO: compiled from: SSEParser.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000,\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\bÆ\u0002\u0018\u00002\u00020\u0001B\t\b\u0002¢\u0006\u0004\b\u0002\u0010\u0003J\u001e\u0010\u0011\u001a\u00020\u00122\u0006\u0010\u0004\u001a\u00020\u00052\u000e\u0010\n\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\f0\u000bJ\u000e\u0010\u0013\u001a\u00020\u00142\u0006\u0010\u0004\u001a\u00020\u0014R\u001c\u0010\u0004\u001a\u0004\u0018\u00010\u0005X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0006\u0010\u0007\"\u0004\b\b\u0010\tR$\u0010\n\u001a\f\u0012\u0006\u0012\u0004\u0018\u00010\f\u0018\u00010\u000bX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\r\u0010\u000e\"\u0004\b\u000f\u0010\u0010¨\u0006\u0015"}, d2 = {"Lcom/miui/aiengine/common/result/SSEParser;", "Lcom/miui/aiengine/common/utils/Logger;", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "()V", HtmlParser.InputElement.TAG, "Ljava/io/InputStream;", "getInput", "()Ljava/io/InputStream;", "setInput", "(Ljava/io/InputStream;)V", CallMethod.ARG_CALLBACK, "Lkotlin/Function0;", "Lcom/miui/aiengine/common/result/SSEMessage;", "getCallback", "()Lkotlin/jvm/functions/Function0;", "setCallback", "(Lkotlin/jvm/functions/Function0;)V", "init", "", "parse", "", "net_release"}, k = 1, mv = {2, 2, 0}, xi = 48)
public final class SSEParser extends Logger {
    public static final SSEParser INSTANCE = new SSEParser();
    private static Function0<SSEMessage> callback;
    private static InputStream input;

    private SSEParser() {
        super(NetConfig.TAG);
    }

    public final InputStream getInput() {
        return input;
    }

    public final void setInput(InputStream inputStream) {
        input = inputStream;
    }

    public final Function0<SSEMessage> getCallback() {
        return callback;
    }

    public final void setCallback(Function0<SSEMessage> function0) {
        callback = function0;
    }

    public final void init(InputStream input2, Function0<SSEMessage> callback2) {
        Intrinsics.checkNotNullParameter(input2, "input");
        Intrinsics.checkNotNullParameter(callback2, "callback");
        input = input2;
        callback = callback2;
    }

    public final String parse(String input2) {
        Intrinsics.checkNotNullParameter(input2, "input");
        logDebug("parse(" + input2 + ")");
        return StringsKt.replaceFirst$default(input2, "data:", "", false, 4, (Object) null);
    }
}
