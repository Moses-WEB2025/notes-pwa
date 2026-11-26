package okhttp3.sse.internal;

import com.market.sdk.reflect.ReflectUtilsForMiui;
import com.miui.maml.elements.MusicLyricParser;
import com.xiaomi.mirror.synergy.CallMethod;
import java.io.IOException;
import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import okhttp3.internal._UtilCommonKt;
import okio.Buffer;
import okio.BufferedSource;
import okio.ByteString;
import okio.Options;
import org.apache.commons.lang3.StringUtils;

/* JADX INFO: compiled from: ServerSentEventReader.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u00004\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\u0018\u0000 \u00132\u00020\u0001:\u0002\u0012\u0013B\u0017\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005¢\u0006\u0004\b\u0006\u0010\u0007J\u0006\u0010\n\u001a\u00020\u000bJ$\u0010\f\u001a\u00020\r2\b\u0010\u000e\u001a\u0004\u0018\u00010\t2\b\u0010\u000f\u001a\u0004\u0018\u00010\t2\u0006\u0010\u0010\u001a\u00020\u0011H\u0002R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004¢\u0006\u0002\n\u0000R\u0010\u0010\b\u001a\u0004\u0018\u00010\tX\u0082\u000e¢\u0006\u0002\n\u0000¨\u0006\u0014"}, d2 = {"Lokhttp3/sse/internal/ServerSentEventReader;", "", "source", "Lokio/BufferedSource;", CallMethod.ARG_CALLBACK, "Lokhttp3/sse/internal/ServerSentEventReader$Callback;", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "(Lokio/BufferedSource;Lokhttp3/sse/internal/ServerSentEventReader$Callback;)V", "lastId", "", "processNextEvent", "", "completeEvent", "", "id", "type", "data", "Lokio/Buffer;", "Callback", "Companion", "okhttp-sse"}, k = 1, mv = {2, 2, 0}, xi = 48)
public final class ServerSentEventReader {
    private final Callback callback;
    private String lastId;
    private final BufferedSource source;

    /* JADX INFO: renamed from: Companion, reason: from kotlin metadata */
    public static final Companion INSTANCE = new Companion(null);
    private static final Options options = Options.INSTANCE.of(ByteString.INSTANCE.encodeUtf8(MusicLyricParser.CRLF), ByteString.INSTANCE.encodeUtf8(StringUtils.CR), ByteString.INSTANCE.encodeUtf8(StringUtils.LF), ByteString.INSTANCE.encodeUtf8("data: "), ByteString.INSTANCE.encodeUtf8("data:"), ByteString.INSTANCE.encodeUtf8("data\r\n"), ByteString.INSTANCE.encodeUtf8("data\r"), ByteString.INSTANCE.encodeUtf8("data\n"), ByteString.INSTANCE.encodeUtf8("id: "), ByteString.INSTANCE.encodeUtf8("id:"), ByteString.INSTANCE.encodeUtf8("id\r\n"), ByteString.INSTANCE.encodeUtf8("id\r"), ByteString.INSTANCE.encodeUtf8("id\n"), ByteString.INSTANCE.encodeUtf8("event: "), ByteString.INSTANCE.encodeUtf8("event:"), ByteString.INSTANCE.encodeUtf8("event\r\n"), ByteString.INSTANCE.encodeUtf8("event\r"), ByteString.INSTANCE.encodeUtf8("event\n"), ByteString.INSTANCE.encodeUtf8("retry: "), ByteString.INSTANCE.encodeUtf8("retry:"));
    private static final ByteString CRLF = ByteString.INSTANCE.encodeUtf8(MusicLyricParser.CRLF);

    /* JADX INFO: compiled from: ServerSentEventReader.kt */
    @Metadata(d1 = {"\u0000\u001e\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0004\n\u0002\u0010\t\n\u0000\bf\u0018\u00002\u00020\u0001J$\u0010\u0002\u001a\u00020\u00032\b\u0010\u0004\u001a\u0004\u0018\u00010\u00052\b\u0010\u0006\u001a\u0004\u0018\u00010\u00052\u0006\u0010\u0007\u001a\u00020\u0005H&J\u0010\u0010\b\u001a\u00020\u00032\u0006\u0010\t\u001a\u00020\nH&ø\u0001\u0000\u0082\u0002\u0006\n\u0004\b!0\u0001¨\u0006\u000bÀ\u0006\u0001"}, d2 = {"Lokhttp3/sse/internal/ServerSentEventReader$Callback;", "", "onEvent", "", "id", "", "type", "data", "onRetryChange", "timeMs", "", "okhttp-sse"}, k = 1, mv = {2, 2, 0}, xi = 48)
    public interface Callback {
        void onEvent(String id, String type, String data);

        void onRetryChange(long timeMs);
    }

    public ServerSentEventReader(BufferedSource source, Callback callback) {
        Intrinsics.checkNotNullParameter(source, "source");
        Intrinsics.checkNotNullParameter(callback, "callback");
        this.source = source;
        this.callback = callback;
    }

    public final boolean processNextEvent() throws IOException {
        String utf8LineStrict = this.lastId;
        Buffer buffer = new Buffer();
        while (true) {
            String utf8LineStrict2 = null;
            while (true) {
                BufferedSource bufferedSource = this.source;
                Options options2 = options;
                int iSelect = bufferedSource.select(options2);
                if (iSelect >= 0 && iSelect < 3) {
                    completeEvent(utf8LineStrict, utf8LineStrict2, buffer);
                    return true;
                }
                if (3 <= iSelect && iSelect < 5) {
                    INSTANCE.readData(this.source, buffer);
                } else if (5 <= iSelect && iSelect < 8) {
                    buffer.writeByte(10);
                } else if (8 <= iSelect && iSelect < 10) {
                    utf8LineStrict = this.source.readUtf8LineStrict();
                    if (utf8LineStrict.length() <= 0) {
                        utf8LineStrict = null;
                    }
                } else if (10 <= iSelect && iSelect < 13) {
                    utf8LineStrict = null;
                } else if (13 <= iSelect && iSelect < 15) {
                    utf8LineStrict2 = this.source.readUtf8LineStrict();
                    if (utf8LineStrict2.length() <= 0) {
                        break;
                    }
                } else {
                    if (15 <= iSelect && iSelect < 18) {
                        break;
                    }
                    if (18 <= iSelect && iSelect < 20) {
                        long retryMs = INSTANCE.readRetryMs(this.source);
                        if (retryMs != -1) {
                            this.callback.onRetryChange(retryMs);
                        }
                    } else if (iSelect == -1) {
                        long jIndexOfElement = this.source.indexOfElement(CRLF);
                        if (jIndexOfElement == -1) {
                            return false;
                        }
                        this.source.skip(jIndexOfElement);
                        this.source.select(options2);
                    } else {
                        throw new AssertionError();
                    }
                }
            }
        }
    }

    private final void completeEvent(String id, String type, Buffer data) throws IOException {
        if (data.size() != 0) {
            this.lastId = id;
            data.skip(1L);
            this.callback.onEvent(id, type, data.readUtf8());
        }
    }

    /* JADX INFO: compiled from: ServerSentEventReader.kt */
    @Metadata(d1 = {"\u00000\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\t\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\t\b\u0002¢\u0006\u0004\b\u0002\u0010\u0003J\u0014\u0010\n\u001a\u00020\u000b*\u00020\f2\u0006\u0010\r\u001a\u00020\u000eH\u0002J\f\u0010\u000f\u001a\u00020\u0010*\u00020\fH\u0002R\u0011\u0010\u0004\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u0006\u0010\u0007R\u000e\u0010\b\u001a\u00020\tX\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u0011"}, d2 = {"Lokhttp3/sse/internal/ServerSentEventReader$Companion;", "", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "()V", "options", "Lokio/Options;", "getOptions", "()Lokio/Options;", "CRLF", "Lokio/ByteString;", "readData", "", "Lokio/BufferedSource;", "data", "Lokio/Buffer;", "readRetryMs", "", "okhttp-sse"}, k = 1, mv = {2, 2, 0}, xi = 48)
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }

        public final Options getOptions() {
            return ServerSentEventReader.options;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public final void readData(BufferedSource bufferedSource, Buffer buffer) throws IOException {
            buffer.writeByte(10);
            bufferedSource.readFully(buffer, bufferedSource.indexOfElement(ServerSentEventReader.CRLF));
            bufferedSource.select(getOptions());
        }

        /* JADX INFO: Access modifiers changed from: private */
        public final long readRetryMs(BufferedSource bufferedSource) throws IOException {
            return _UtilCommonKt.toLongOrDefault(bufferedSource.readUtf8LineStrict(), -1L);
        }
    }
}
