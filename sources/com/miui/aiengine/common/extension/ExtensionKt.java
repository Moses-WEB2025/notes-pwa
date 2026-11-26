package com.miui.aiengine.common.extension;

import android.content.ContentValues;
import androidx.exifinterface.media.ExifInterface;
import com.miui.aiengine.common.Result;
import com.miui.aiengine.common.SUCCESS;
import com.miui.aiengine.common.cipher.CipherException;
import com.miui.aiengine.common.cipher.CryptCoder;
import com.miui.aiengine.common.cloud.RequestHelper;
import com.miui.aiengine.common.request.KVPair;
import com.miui.aiengine.common.result.SSEParser;
import com.xiaomi.mirror.synergy.CallMethod;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import okhttp3.sse.internal.ServerSentEventReader;
import okio.BufferedSource;

/* JADX INFO: compiled from: extension.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000:\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\u001a\u001d\u0010\u0000\u001a\u0002H\u0001\"\u0004\b\u0000\u0010\u0001*\u0002H\u00012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004\u001a\u001c\u0010\u0005\u001a\u0014\u0012\u0010\u0012\u000e\u0012\u0004\u0012\u00020\u0003\u0012\u0004\u0012\u00020\u00030\u00070\u0006*\u00020\b\u001a\n\u0010\t\u001a\u00020\u0003*\u00020\n\u001a(\u0010\u000b\u001a\u00020\f*\u00020\r2\b\u0010\u000e\u001a\u0004\u0018\u00010\u000f2\u0012\u0010\u0010\u001a\u000e\u0012\u0004\u0012\u00020\u0012\u0012\u0004\u0012\u00020\f0\u0011\u001a(\u0010\u000b\u001a\u00020\f*\u00020\n2\b\u0010\u000e\u001a\u0004\u0018\u00010\u000f2\u0012\u0010\u0010\u001a\u000e\u0012\u0004\u0012\u00020\u0012\u0012\u0004\u0012\u00020\f0\u0011¨\u0006\u0013"}, d2 = {"checkNotNull", ExifInterface.GPS_DIRECTION_TRUE, "errorHint", "", "(Ljava/lang/Object;Ljava/lang/String;)Ljava/lang/Object;", "toBasicNameValuePairs", "", "Lcom/miui/aiengine/common/request/KVPair;", "Landroid/content/ContentValues;", "inputStreamToString", "Ljava/io/InputStream;", "handleSSEInput", "", "Lokio/BufferedSource;", "coder", "Lcom/miui/aiengine/common/cipher/CryptCoder;", CallMethod.ARG_CALLBACK, "Lkotlin/Function1;", "Lcom/miui/aiengine/common/Result;", "net_release"}, k = 2, mv = {2, 2, 0}, xi = 48)
public final class ExtensionKt {
    public static final <T> T checkNotNull(T t, String errorHint) throws NullPointerException {
        Intrinsics.checkNotNullParameter(errorHint, "errorHint");
        if (t != null) {
            return t;
        }
        throw new NullPointerException(errorHint);
    }

    public static final List<KVPair<String, String>> toBasicNameValuePairs(ContentValues contentValues) {
        Intrinsics.checkNotNullParameter(contentValues, "<this>");
        ArrayList arrayList = new ArrayList();
        Set<Map.Entry<String, Object>> setValueSet = contentValues.valueSet();
        Intrinsics.checkNotNullExpressionValue(setValueSet, "valueSet(...)");
        Iterator<T> it = setValueSet.iterator();
        while (it.hasNext()) {
            Map.Entry entry = (Map.Entry) it.next();
            arrayList.add(new KVPair(entry.getKey(), entry.getValue().toString()));
        }
        return arrayList;
    }

    public static final String inputStreamToString(InputStream inputStream) throws IOException {
        Intrinsics.checkNotNullParameter(inputStream, "<this>");
        StringBuffer stringBuffer = new StringBuffer();
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));
        char[] cArr = new char[2048];
        while (true) {
            int i = bufferedReader.read(cArr, 0, 2048);
            if (i != -1) {
                stringBuffer.append(cArr, 0, i);
            } else {
                String string = stringBuffer.toString();
                Intrinsics.checkNotNullExpressionValue(string, "toString(...)");
                return string;
            }
        }
    }

    public static final void handleSSEInput(BufferedSource bufferedSource, final CryptCoder cryptCoder, final Function1<? super Result, Unit> callback) throws IOException {
        Intrinsics.checkNotNullParameter(bufferedSource, "<this>");
        Intrinsics.checkNotNullParameter(callback, "callback");
        final AtomicInteger atomicInteger = new AtomicInteger(0);
        do {
            try {
            } catch (IOException e) {
                RequestHelper.INSTANCE.logError("handleSSEInput " + e.getMessage());
                throw e;
            }
        } while (new ServerSentEventReader(bufferedSource, new ServerSentEventReader.Callback() { // from class: com.miui.aiengine.common.extension.ExtensionKt$handleSSEInput$reader$1
            @Override // okhttp3.sse.internal.ServerSentEventReader.Callback
            public void onEvent(String id, String type, String data) {
                String strDecrypt;
                Intrinsics.checkNotNullParameter(data, "data");
                Function1<Result, Unit> function1 = callback;
                CryptCoder cryptCoder2 = cryptCoder;
                if (cryptCoder2 != null && (strDecrypt = cryptCoder2.decrypt(data)) != null) {
                    data = strDecrypt;
                }
                SUCCESS success = new SUCCESS(data, String.valueOf(atomicInteger.getAndIncrement()));
                RequestHelper.INSTANCE.logInfo("okio sse id:" + success + ",originId:" + id);
                function1.invoke(success);
            }

            @Override // okhttp3.sse.internal.ServerSentEventReader.Callback
            public void onRetryChange(long timeMs) {
                RequestHelper.INSTANCE.logError("onRetryChange " + timeMs);
            }
        }).processNextEvent());
    }

    public static final void handleSSEInput(InputStream inputStream, CryptCoder cryptCoder, Function1<? super Result, Unit> callback) throws IOException, CipherException {
        String line;
        Intrinsics.checkNotNullParameter(inputStream, "<this>");
        Intrinsics.checkNotNullParameter(callback, "callback");
        RequestHelper.INSTANCE.logInfo("SSE parse start....");
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
        do {
            line = bufferedReader.readLine();
            if (line != null && line.equals("")) {
                RequestHelper.INSTANCE.logVerbose("one msg end ");
            } else if (line != null) {
                RequestHelper.INSTANCE.logInfo(line);
                String str = SSEParser.INSTANCE.parse(line);
                Intrinsics.checkNotNull(cryptCoder);
                String strDecrypt = cryptCoder.decrypt(str);
                RequestHelper.INSTANCE.logInfo("private protocol sse parse:" + strDecrypt);
                callback.invoke(new SUCCESS(strDecrypt, null, 2, null));
            }
        } while (line != null);
        RequestHelper.INSTANCE.logError("read over!");
    }
}
