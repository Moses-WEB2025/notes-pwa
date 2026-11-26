package okhttp3;

import com.market.sdk.reflect.ReflectUtilsForMiui;
import com.xiaomi.opensdk.file.utils.FileSDKUtils;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;
import kotlin.Deprecated;
import kotlin.DeprecationLevel;
import kotlin.Metadata;
import kotlin.ReplaceWith;
import kotlin.jvm.JvmStatic;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import okhttp3.internal._UtilJvmKt;
import okio.Buffer;
import okio.BufferedSink;
import okio.ByteString;

/* JADX INFO: compiled from: MultipartBody.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000N\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0010\t\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0004\n\u0002\u0010\u000b\n\u0002\b\u0005\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0006\u0018\u0000 &2\u00020\u0001:\u0003$%&B'\b\u0000\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\f\u0010\u0006\u001a\b\u0012\u0004\u0012\u00020\b0\u0007¢\u0006\u0004\b\t\u0010\nJ\u000e\u0010\u0016\u001a\u00020\b2\u0006\u0010\u0017\u001a\u00020\u0014J\b\u0010\u0018\u001a\u00020\u0019H\u0016J\b\u0010\r\u001a\u00020\u0005H\u0016J\r\u0010\u0004\u001a\u00020\u0005H\u0007¢\u0006\u0002\b\u001aJ\r\u0010\u0010\u001a\u00020\u0011H\u0007¢\u0006\u0002\b\u001bJ\r\u0010\u0013\u001a\u00020\u0014H\u0007¢\u0006\u0002\b\u001cJ\u0013\u0010\u0006\u001a\b\u0012\u0004\u0012\u00020\b0\u0007H\u0007¢\u0006\u0002\b\u001dJ\b\u0010\u000e\u001a\u00020\u000fH\u0016J\u0010\u0010\u001e\u001a\u00020\u001f2\u0006\u0010 \u001a\u00020!H\u0016J\u001a\u0010\"\u001a\u00020\u000f2\b\u0010 \u001a\u0004\u0018\u00010!2\u0006\u0010#\u001a\u00020\u0019H\u0002R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000R\u0013\u0010\u0004\u001a\u00020\u00058G¢\u0006\b\n\u0000\u001a\u0004\b\u0004\u0010\u000bR\u0019\u0010\u0006\u001a\b\u0012\u0004\u0012\u00020\b0\u00078G¢\u0006\b\n\u0000\u001a\u0004\b\u0006\u0010\fR\u000e\u0010\r\u001a\u00020\u0005X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u000e\u001a\u00020\u000fX\u0082\u000e¢\u0006\u0002\n\u0000R\u0011\u0010\u0010\u001a\u00020\u00118G¢\u0006\u0006\u001a\u0004\b\u0010\u0010\u0012R\u0011\u0010\u0013\u001a\u00020\u00148G¢\u0006\u0006\u001a\u0004\b\u0013\u0010\u0015¨\u0006'"}, d2 = {"Lokhttp3/MultipartBody;", "Lokhttp3/RequestBody;", "boundaryByteString", "Lokio/ByteString;", "type", "Lokhttp3/MediaType;", "parts", "", "Lokhttp3/MultipartBody$Part;", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "(Lokio/ByteString;Lokhttp3/MediaType;Ljava/util/List;)V", "()Lokhttp3/MediaType;", "()Ljava/util/List;", "contentType", "contentLength", "", "boundary", "", "()Ljava/lang/String;", "size", "", "()I", "part", "index", "isOneShot", "", "-deprecated_type", "-deprecated_boundary", "-deprecated_size", "-deprecated_parts", "writeTo", "", "sink", "Lokio/BufferedSink;", "writeOrCountBytes", "countBytes", "Part", "Builder", "Companion", "okhttp"}, k = 1, mv = {2, 2, 0}, xi = 48)
public final class MultipartBody extends RequestBody {
    private final ByteString boundaryByteString;
    private long contentLength;
    private final MediaType contentType;
    private final List<Part> parts;
    private final MediaType type;

    /* JADX INFO: renamed from: Companion, reason: from kotlin metadata */
    public static final Companion INSTANCE = new Companion(null);
    public static final MediaType MIXED = MediaType.INSTANCE.get("multipart/mixed");
    public static final MediaType ALTERNATIVE = MediaType.INSTANCE.get("multipart/alternative");
    public static final MediaType DIGEST = MediaType.INSTANCE.get("multipart/digest");
    public static final MediaType PARALLEL = MediaType.INSTANCE.get("multipart/parallel");
    public static final MediaType FORM = MediaType.INSTANCE.get("multipart/form-data");
    private static final byte[] COLONSPACE = {58, 32};
    private static final byte[] CRLF = {13, 10};
    private static final byte[] DASHDASH = {45, 45};

    public final MediaType type() {
        return this.type;
    }

    public final List<Part> parts() {
        return this.parts;
    }

    public MultipartBody(ByteString boundaryByteString, MediaType type, List<Part> parts) {
        Intrinsics.checkNotNullParameter(boundaryByteString, "boundaryByteString");
        Intrinsics.checkNotNullParameter(type, "type");
        Intrinsics.checkNotNullParameter(parts, "parts");
        this.boundaryByteString = boundaryByteString;
        this.type = type;
        this.parts = parts;
        this.contentType = MediaType.INSTANCE.get(type + "; boundary=" + boundary());
        this.contentLength = -1L;
    }

    public final String boundary() {
        return this.boundaryByteString.utf8();
    }

    public final int size() {
        return this.parts.size();
    }

    public final Part part(int index) {
        return this.parts.get(index);
    }

    @Override // okhttp3.RequestBody
    public boolean isOneShot() {
        List<Part> list = this.parts;
        if ((list instanceof Collection) && list.isEmpty()) {
            return false;
        }
        Iterator<T> it = list.iterator();
        while (it.hasNext()) {
            if (((Part) it.next()).body().isOneShot()) {
                return true;
            }
        }
        return false;
    }

    @Override // okhttp3.RequestBody
    /* JADX INFO: renamed from: contentType, reason: from getter */
    public MediaType getContentType() {
        return this.contentType;
    }

    @Deprecated(level = DeprecationLevel.ERROR, message = "moved to val", replaceWith = @ReplaceWith(expression = "type", imports = {}))
    /* JADX INFO: renamed from: -deprecated_type, reason: not valid java name and from getter */
    public final MediaType getType() {
        return this.type;
    }

    @Deprecated(level = DeprecationLevel.ERROR, message = "moved to val", replaceWith = @ReplaceWith(expression = "boundary", imports = {}))
    /* JADX INFO: renamed from: -deprecated_boundary, reason: not valid java name */
    public final String m4448deprecated_boundary() {
        return boundary();
    }

    @Deprecated(level = DeprecationLevel.ERROR, message = "moved to val", replaceWith = @ReplaceWith(expression = "size", imports = {}))
    /* JADX INFO: renamed from: -deprecated_size, reason: not valid java name */
    public final int m4450deprecated_size() {
        return size();
    }

    @Deprecated(level = DeprecationLevel.ERROR, message = "moved to val", replaceWith = @ReplaceWith(expression = "parts", imports = {}))
    /* JADX INFO: renamed from: -deprecated_parts, reason: not valid java name */
    public final List<Part> m4449deprecated_parts() {
        return this.parts;
    }

    @Override // okhttp3.RequestBody
    public long contentLength() throws IOException {
        long j = this.contentLength;
        if (j != -1) {
            return j;
        }
        long jWriteOrCountBytes = writeOrCountBytes(null, true);
        this.contentLength = jWriteOrCountBytes;
        return jWriteOrCountBytes;
    }

    @Override // okhttp3.RequestBody
    public void writeTo(BufferedSink sink) throws IOException {
        Intrinsics.checkNotNullParameter(sink, "sink");
        writeOrCountBytes(sink, false);
    }

    private final long writeOrCountBytes(BufferedSink sink, boolean countBytes) throws IOException {
        Buffer buffer;
        if (countBytes) {
            Buffer buffer2 = new Buffer();
            buffer = buffer2;
            sink = buffer2;
        } else {
            buffer = null;
        }
        int size = this.parts.size();
        long j = 0;
        for (int i = 0; i < size; i++) {
            Part part = this.parts.get(i);
            Headers headers = part.headers();
            RequestBody requestBodyBody = part.body();
            Intrinsics.checkNotNull(sink);
            sink.write(DASHDASH);
            sink.write(this.boundaryByteString);
            sink.write(CRLF);
            if (headers != null) {
                int size2 = headers.size();
                for (int i2 = 0; i2 < size2; i2++) {
                    sink.writeUtf8(headers.name(i2)).write(COLONSPACE).writeUtf8(headers.value(i2)).write(CRLF);
                }
            }
            MediaType contentType = requestBodyBody.getContentType();
            if (contentType != null) {
                sink.writeUtf8("Content-Type: ").writeUtf8(contentType.toString()).write(CRLF);
            }
            long jContentLength = requestBodyBody.contentLength();
            if (jContentLength == -1 && countBytes) {
                Intrinsics.checkNotNull(buffer);
                buffer.clear();
                return -1L;
            }
            byte[] bArr = CRLF;
            sink.write(bArr);
            if (countBytes) {
                j += jContentLength;
            } else {
                requestBodyBody.writeTo(sink);
            }
            sink.write(bArr);
        }
        Intrinsics.checkNotNull(sink);
        byte[] bArr2 = DASHDASH;
        sink.write(bArr2);
        sink.write(this.boundaryByteString);
        sink.write(bArr2);
        sink.write(CRLF);
        if (!countBytes) {
            return j;
        }
        Intrinsics.checkNotNull(buffer);
        long size3 = j + buffer.size();
        buffer.clear();
        return size3;
    }

    /* JADX INFO: compiled from: MultipartBody.kt */
    @Metadata(d1 = {"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\b\u0018\u0000 \f2\u00020\u0001:\u0001\fB\u001b\b\u0002\u0012\b\u0010\u0002\u001a\u0004\u0018\u00010\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005¢\u0006\u0004\b\u0006\u0010\u0007J\u000f\u0010\u0002\u001a\u0004\u0018\u00010\u0003H\u0007¢\u0006\u0002\b\nJ\r\u0010\u0004\u001a\u00020\u0005H\u0007¢\u0006\u0002\b\u000bR\u0015\u0010\u0002\u001a\u0004\u0018\u00010\u00038G¢\u0006\b\n\u0000\u001a\u0004\b\u0002\u0010\bR\u0013\u0010\u0004\u001a\u00020\u00058G¢\u0006\b\n\u0000\u001a\u0004\b\u0004\u0010\t¨\u0006\r"}, d2 = {"Lokhttp3/MultipartBody$Part;", "", "headers", "Lokhttp3/Headers;", "body", "Lokhttp3/RequestBody;", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "(Lokhttp3/Headers;Lokhttp3/RequestBody;)V", "()Lokhttp3/Headers;", "()Lokhttp3/RequestBody;", "-deprecated_headers", "-deprecated_body", "Companion", "okhttp"}, k = 1, mv = {2, 2, 0}, xi = 48)
    public static final class Part {

        /* JADX INFO: renamed from: Companion, reason: from kotlin metadata */
        public static final Companion INSTANCE = new Companion(null);
        private final RequestBody body;
        private final Headers headers;

        public /* synthetic */ Part(Headers headers, RequestBody requestBody, DefaultConstructorMarker defaultConstructorMarker) {
            this(headers, requestBody);
        }

        @JvmStatic
        public static final Part create(Headers headers, RequestBody requestBody) {
            return INSTANCE.create(headers, requestBody);
        }

        @JvmStatic
        public static final Part create(RequestBody requestBody) {
            return INSTANCE.create(requestBody);
        }

        @JvmStatic
        public static final Part createFormData(String str, String str2) {
            return INSTANCE.createFormData(str, str2);
        }

        @JvmStatic
        public static final Part createFormData(String str, String str2, RequestBody requestBody) {
            return INSTANCE.createFormData(str, str2, requestBody);
        }

        private Part(Headers headers, RequestBody requestBody) {
            this.headers = headers;
            this.body = requestBody;
        }

        public final Headers headers() {
            return this.headers;
        }

        public final RequestBody body() {
            return this.body;
        }

        @Deprecated(level = DeprecationLevel.ERROR, message = "moved to val", replaceWith = @ReplaceWith(expression = "headers", imports = {}))
        /* JADX INFO: renamed from: -deprecated_headers, reason: not valid java name and from getter */
        public final Headers getHeaders() {
            return this.headers;
        }

        @Deprecated(level = DeprecationLevel.ERROR, message = "moved to val", replaceWith = @ReplaceWith(expression = "body", imports = {}))
        /* JADX INFO: renamed from: -deprecated_body, reason: not valid java name and from getter */
        public final RequestBody getBody() {
            return this.body;
        }

        /* JADX INFO: compiled from: MultipartBody.kt */
        @Metadata(d1 = {"\u0000(\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0003\b\u0086\u0003\u0018\u00002\u00020\u0001B\t\b\u0002¢\u0006\u0004\b\u0002\u0010\u0003J\u0010\u0010\u0004\u001a\u00020\u00052\u0006\u0010\u0006\u001a\u00020\u0007H\u0007J\u001a\u0010\u0004\u001a\u00020\u00052\b\u0010\b\u001a\u0004\u0018\u00010\t2\u0006\u0010\u0006\u001a\u00020\u0007H\u0007J\u0018\u0010\n\u001a\u00020\u00052\u0006\u0010\u000b\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\fH\u0007J\"\u0010\n\u001a\u00020\u00052\u0006\u0010\u000b\u001a\u00020\f2\b\u0010\u000e\u001a\u0004\u0018\u00010\f2\u0006\u0010\u0006\u001a\u00020\u0007H\u0007¨\u0006\u000f"}, d2 = {"Lokhttp3/MultipartBody$Part$Companion;", "", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "()V", "create", "Lokhttp3/MultipartBody$Part;", "body", "Lokhttp3/RequestBody;", "headers", "Lokhttp3/Headers;", "createFormData", "name", "", "value", FileSDKUtils.J_FILENAME, "okhttp"}, k = 1, mv = {2, 2, 0}, xi = 48)
        public static final class Companion {
            public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
                this();
            }

            private Companion() {
            }

            @JvmStatic
            public final Part create(RequestBody body) {
                Intrinsics.checkNotNullParameter(body, "body");
                return create(null, body);
            }

            @JvmStatic
            public final Part create(Headers headers, RequestBody body) {
                Intrinsics.checkNotNullParameter(body, "body");
                DefaultConstructorMarker defaultConstructorMarker = null;
                if ((headers != null ? headers.get("Content-Type") : null) != null) {
                    throw new IllegalArgumentException("Unexpected header: Content-Type".toString());
                }
                if ((headers != null ? headers.get("Content-Length") : null) != null) {
                    throw new IllegalArgumentException("Unexpected header: Content-Length".toString());
                }
                return new Part(headers, body, defaultConstructorMarker);
            }

            @JvmStatic
            public final Part createFormData(String name, String value) {
                Intrinsics.checkNotNullParameter(name, "name");
                Intrinsics.checkNotNullParameter(value, "value");
                return createFormData(name, null, RequestBody.Companion.create$default(RequestBody.INSTANCE, value, (MediaType) null, 1, (Object) null));
            }

            @JvmStatic
            public final Part createFormData(String name, String filename, RequestBody body) {
                Intrinsics.checkNotNullParameter(name, "name");
                Intrinsics.checkNotNullParameter(body, "body");
                StringBuilder sb = new StringBuilder();
                sb.append("form-data; name=");
                MultipartBody.INSTANCE.appendQuotedString$okhttp(sb, name);
                if (filename != null) {
                    sb.append("; filename=");
                    MultipartBody.INSTANCE.appendQuotedString$okhttp(sb, filename);
                }
                return create(new Headers.Builder().addUnsafeNonAscii("Content-Disposition", sb.toString()).build(), body);
            }
        }
    }

    /* JADX INFO: compiled from: MultipartBody.kt */
    @Metadata(d1 = {"\u0000>\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010!\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u0001B\u0013\b\u0007\u0012\b\b\u0002\u0010\u0002\u001a\u00020\u0003¢\u0006\u0004\b\u0004\u0010\u0005J\u000e\u0010\f\u001a\u00020\u00002\u0006\u0010\u0007\u001a\u00020\bJ\u000e\u0010\r\u001a\u00020\u00002\u0006\u0010\u000e\u001a\u00020\u000fJ\u0018\u0010\r\u001a\u00020\u00002\b\u0010\u0010\u001a\u0004\u0018\u00010\u00112\u0006\u0010\u000e\u001a\u00020\u000fJ\u0016\u0010\u0012\u001a\u00020\u00002\u0006\u0010\u0013\u001a\u00020\u00032\u0006\u0010\u0014\u001a\u00020\u0003J \u0010\u0012\u001a\u00020\u00002\u0006\u0010\u0013\u001a\u00020\u00032\b\u0010\u0015\u001a\u0004\u0018\u00010\u00032\u0006\u0010\u000e\u001a\u00020\u000fJ\u000e\u0010\r\u001a\u00020\u00002\u0006\u0010\u0016\u001a\u00020\u000bJ\u0006\u0010\u0017\u001a\u00020\u0018R\u000e\u0010\u0002\u001a\u00020\u0006X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\bX\u0082\u000e¢\u0006\u0002\n\u0000R\u0014\u0010\t\u001a\b\u0012\u0004\u0012\u00020\u000b0\nX\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u0019"}, d2 = {"Lokhttp3/MultipartBody$Builder;", "", "boundary", "", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "(Ljava/lang/String;)V", "Lokio/ByteString;", "type", "Lokhttp3/MediaType;", "parts", "", "Lokhttp3/MultipartBody$Part;", "setType", "addPart", "body", "Lokhttp3/RequestBody;", "headers", "Lokhttp3/Headers;", "addFormDataPart", "name", "value", FileSDKUtils.J_FILENAME, "part", "build", "Lokhttp3/MultipartBody;", "okhttp"}, k = 1, mv = {2, 2, 0}, xi = 48)
    public static final class Builder {
        private final ByteString boundary;
        private final List<Part> parts;
        private MediaType type;

        public Builder() {
            this(null, 1, 0 == true ? 1 : 0);
        }

        public Builder(String boundary) {
            Intrinsics.checkNotNullParameter(boundary, "boundary");
            this.boundary = ByteString.INSTANCE.encodeUtf8(boundary);
            this.type = MultipartBody.MIXED;
            this.parts = new ArrayList();
        }

        /* JADX WARN: Illegal instructions before constructor call */
        public /* synthetic */ Builder(String str, int i, DefaultConstructorMarker defaultConstructorMarker) {
            if ((i & 1) != 0) {
                str = UUID.randomUUID().toString();
                Intrinsics.checkNotNullExpressionValue(str, "toString(...)");
            }
            this(str);
        }

        public final Builder setType(MediaType type) {
            Intrinsics.checkNotNullParameter(type, "type");
            if (!Intrinsics.areEqual(type.type(), "multipart")) {
                throw new IllegalArgumentException(("multipart != " + type).toString());
            }
            this.type = type;
            return this;
        }

        public final Builder addPart(RequestBody body) {
            Intrinsics.checkNotNullParameter(body, "body");
            addPart(Part.INSTANCE.create(body));
            return this;
        }

        public final Builder addPart(Headers headers, RequestBody body) {
            Intrinsics.checkNotNullParameter(body, "body");
            addPart(Part.INSTANCE.create(headers, body));
            return this;
        }

        public final Builder addFormDataPart(String name, String value) {
            Intrinsics.checkNotNullParameter(name, "name");
            Intrinsics.checkNotNullParameter(value, "value");
            addPart(Part.INSTANCE.createFormData(name, value));
            return this;
        }

        public final Builder addFormDataPart(String name, String filename, RequestBody body) {
            Intrinsics.checkNotNullParameter(name, "name");
            Intrinsics.checkNotNullParameter(body, "body");
            addPart(Part.INSTANCE.createFormData(name, filename, body));
            return this;
        }

        public final Builder addPart(Part part) {
            Intrinsics.checkNotNullParameter(part, "part");
            this.parts.add(part);
            return this;
        }

        public final MultipartBody build() {
            if (this.parts.isEmpty()) {
                throw new IllegalStateException("Multipart body must have at least one part.".toString());
            }
            return new MultipartBody(this.boundary, this.type, _UtilJvmKt.toImmutableList(this.parts));
        }
    }

    /* JADX INFO: compiled from: MultipartBody.kt */
    @Metadata(d1 = {"\u00002\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u0012\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\b\u0086\u0003\u0018\u00002\u00020\u0001B\t\b\u0002¢\u0006\u0004\b\u0002\u0010\u0003J\u001d\u0010\u000e\u001a\u00020\u000f*\u00060\u0010j\u0002`\u00112\u0006\u0010\u0012\u001a\u00020\u0013H\u0000¢\u0006\u0002\b\u0014R\u0010\u0010\u0004\u001a\u00020\u00058\u0006X\u0087\u0004¢\u0006\u0002\n\u0000R\u0010\u0010\u0006\u001a\u00020\u00058\u0006X\u0087\u0004¢\u0006\u0002\n\u0000R\u0010\u0010\u0007\u001a\u00020\u00058\u0006X\u0087\u0004¢\u0006\u0002\n\u0000R\u0010\u0010\b\u001a\u00020\u00058\u0006X\u0087\u0004¢\u0006\u0002\n\u0000R\u0010\u0010\t\u001a\u00020\u00058\u0006X\u0087\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\n\u001a\u00020\u000bX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\f\u001a\u00020\u000bX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\r\u001a\u00020\u000bX\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u0015"}, d2 = {"Lokhttp3/MultipartBody$Companion;", "", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "()V", "MIXED", "Lokhttp3/MediaType;", "ALTERNATIVE", "DIGEST", "PARALLEL", "FORM", "COLONSPACE", "", "CRLF", "DASHDASH", "appendQuotedString", "", "Ljava/lang/StringBuilder;", "Lkotlin/text/StringBuilder;", "key", "", "appendQuotedString$okhttp", "okhttp"}, k = 1, mv = {2, 2, 0}, xi = 48)
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }

        public final void appendQuotedString$okhttp(StringBuilder sb, String key) {
            Intrinsics.checkNotNullParameter(sb, "<this>");
            Intrinsics.checkNotNullParameter(key, "key");
            sb.append('\"');
            int length = key.length();
            for (int i = 0; i < length; i++) {
                char cCharAt = key.charAt(i);
                if (cCharAt == '\n') {
                    sb.append("%0A");
                } else if (cCharAt == '\r') {
                    sb.append("%0D");
                } else if (cCharAt == '\"') {
                    sb.append("%22");
                } else {
                    sb.append(cCharAt);
                }
            }
            sb.append('\"');
        }
    }
}
