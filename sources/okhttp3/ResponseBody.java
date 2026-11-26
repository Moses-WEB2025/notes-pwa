package okhttp3;

import androidx.collection.SieveCacheKt;
import androidx.constraintlayout.core.motion.utils.TypedValues;
import androidx.exifinterface.media.ExifInterface;
import com.market.sdk.reflect.ReflectUtilsForMiui;
import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.Charset;
import kotlin.Deprecated;
import kotlin.DeprecationLevel;
import kotlin.ExceptionsKt;
import kotlin.Metadata;
import kotlin.Pair;
import kotlin.ReplaceWith;
import kotlin.jvm.JvmStatic;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import miuix.navigation.SplitLayout;
import okhttp3.internal.Internal;
import okhttp3.internal._UtilCommonKt;
import okhttp3.internal._UtilJvmKt;
import okio.Buffer;
import okio.BufferedSource;
import okio.ByteString;

/* JADX INFO: compiled from: ResponseBody.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000`\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\t\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0012\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0003\b&\u0018\u0000 \"2\u00020\u0001:\u0002!\"B\u0007¢\u0006\u0004\b\u0002\u0010\u0003J\n\u0010\u0006\u001a\u0004\u0018\u00010\u0007H&J\b\u0010\b\u001a\u00020\tH&J\u0006\u0010\n\u001a\u00020\u000bJ\b\u0010\f\u001a\u00020\rH&J\u0006\u0010\u000e\u001a\u00020\u000fJ\u0006\u0010\u0010\u001a\u00020\u0011JD\u0010\u0012\u001a\u0002H\u0013\"\b\b\u0000\u0010\u0013*\u00020\u0014*\u00020\u00002\u0012\u0010\u0015\u001a\u000e\u0012\u0004\u0012\u00020\r\u0012\u0004\u0012\u0002H\u00130\u00162\u0012\u0010\u0017\u001a\u000e\u0012\u0004\u0012\u0002H\u0013\u0012\u0004\u0012\u00020\u00180\u0016H\u0082\b¢\u0006\u0002\u0010\u0019J\u0006\u0010\u001a\u001a\u00020\u0005J\u0006\u0010\u001b\u001a\u00020\u001cJ\b\u0010\u001d\u001a\u00020\u001eH\u0002J\b\u0010\u001f\u001a\u00020 H\u0016R\u0010\u0010\u0004\u001a\u0004\u0018\u00010\u0005X\u0082\u000e¢\u0006\u0002\n\u0000¨\u0006#"}, d2 = {"Lokhttp3/ResponseBody;", "Ljava/io/Closeable;", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "()V", "reader", "Ljava/io/Reader;", "contentType", "Lokhttp3/MediaType;", "contentLength", "", "byteStream", "Ljava/io/InputStream;", "source", "Lokio/BufferedSource;", "bytes", "", "byteString", "Lokio/ByteString;", "consumeSource", ExifInterface.GPS_DIRECTION_TRUE, "", "consumer", "Lkotlin/Function1;", "sizeMapper", "", "(Lokhttp3/ResponseBody;Lkotlin/jvm/functions/Function1;Lkotlin/jvm/functions/Function1;)Ljava/lang/Object;", "charStream", TypedValues.Custom.S_STRING, "", "charset", "Ljava/nio/charset/Charset;", SplitLayout.TAG_CLOSE, "", "BomAwareReader", "Companion", "okhttp"}, k = 1, mv = {2, 2, 0}, xi = 48)
public abstract class ResponseBody implements Closeable {

    /* JADX INFO: renamed from: Companion, reason: from kotlin metadata */
    public static final Companion INSTANCE;
    public static final ResponseBody EMPTY;
    private Reader reader;

    @JvmStatic
    public static final ResponseBody create(String str, MediaType mediaType) {
        return INSTANCE.create(str, mediaType);
    }

    @Deprecated(level = DeprecationLevel.WARNING, message = "Moved to extension function. Put the 'content' argument first to fix Java", replaceWith = @ReplaceWith(expression = "content.asResponseBody(contentType, contentLength)", imports = {"okhttp3.ResponseBody.Companion.asResponseBody"}))
    @JvmStatic
    public static final ResponseBody create(MediaType mediaType, long j, BufferedSource bufferedSource) {
        return INSTANCE.create(mediaType, j, bufferedSource);
    }

    @Deprecated(level = DeprecationLevel.WARNING, message = "Moved to extension function. Put the 'content' argument first to fix Java", replaceWith = @ReplaceWith(expression = "content.toResponseBody(contentType)", imports = {"okhttp3.ResponseBody.Companion.toResponseBody"}))
    @JvmStatic
    public static final ResponseBody create(MediaType mediaType, String str) {
        return INSTANCE.create(mediaType, str);
    }

    @Deprecated(level = DeprecationLevel.WARNING, message = "Moved to extension function. Put the 'content' argument first to fix Java", replaceWith = @ReplaceWith(expression = "content.toResponseBody(contentType)", imports = {"okhttp3.ResponseBody.Companion.toResponseBody"}))
    @JvmStatic
    public static final ResponseBody create(MediaType mediaType, ByteString byteString) {
        return INSTANCE.create(mediaType, byteString);
    }

    @Deprecated(level = DeprecationLevel.WARNING, message = "Moved to extension function. Put the 'content' argument first to fix Java", replaceWith = @ReplaceWith(expression = "content.toResponseBody(contentType)", imports = {"okhttp3.ResponseBody.Companion.toResponseBody"}))
    @JvmStatic
    public static final ResponseBody create(MediaType mediaType, byte[] bArr) {
        return INSTANCE.create(mediaType, bArr);
    }

    @JvmStatic
    public static final ResponseBody create(BufferedSource bufferedSource, MediaType mediaType, long j) {
        return INSTANCE.create(bufferedSource, mediaType, j);
    }

    @JvmStatic
    public static final ResponseBody create(ByteString byteString, MediaType mediaType) {
        return INSTANCE.create(byteString, mediaType);
    }

    @JvmStatic
    public static final ResponseBody create(byte[] bArr, MediaType mediaType) {
        return INSTANCE.create(bArr, mediaType);
    }

    /* JADX INFO: renamed from: contentLength */
    public abstract long get$contentLength();

    /* JADX INFO: renamed from: contentType */
    public abstract MediaType get$contentType();

    /* JADX INFO: renamed from: source */
    public abstract BufferedSource get$this_asResponseBody();

    public final InputStream byteStream() {
        return get$this_asResponseBody().inputStream();
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r7v13 */
    /* JADX WARN: Type inference failed for: r7v2, types: [java.lang.Throwable] */
    /* JADX WARN: Type inference failed for: r7v3, types: [java.lang.Throwable] */
    private final <T> T consumeSource(ResponseBody responseBody, Function1<? super BufferedSource, ? extends T> function1, Function1<? super T, Integer> function2) throws IOException {
        long jContentLength = responseBody.get$contentLength();
        if (jContentLength > SieveCacheKt.NodeLinkMask) {
            throw new IOException("Cannot buffer entire body for content length: " + jContentLength);
        }
        BufferedSource bufferedSourceSource = responseBody.get$this_asResponseBody();
        Object obj = (Object) null;
        try {
            Object objInvoke = function1.invoke(bufferedSourceSource);
            if (bufferedSourceSource != null) {
                try {
                    bufferedSourceSource.close();
                } catch (Throwable 
                /*  JADX ERROR: Method code generation error
                    java.lang.NullPointerException: Cannot invoke "jadx.core.dex.instructions.args.SSAVar.getCodeVar()" because "ssaVar" is null
                    */
                /*
                    this = this;
                    long r0 = r6.get$contentLength()
                    r2 = 2147483647(0x7fffffff, double:1.060997895E-314)
                    int r5 = (r0 > r2 ? 1 : (r0 == r2 ? 0 : -1))
                    if (r5 > 0) goto L6b
                    okio.BufferedSource r5 = r6.get$this_asResponseBody()
                    java.io.Closeable r5 = (java.io.Closeable) r5
                    r6 = 0
                    java.lang.Object r7 = r7.invoke(r5)     // Catch: java.lang.Throwable -> L21
                    if (r5 == 0) goto L1d
                    r5.close()     // Catch: java.lang.Throwable -> L1c
                    goto L1d
                L1c:
                    r6 = move-exception
                L1d:
                    r4 = r7
                    r7 = r6
                    r6 = r4
                    goto L2c
                L21:
                    r7 = move-exception
                    if (r5 == 0) goto L2c
                    r5.close()     // Catch: java.lang.Throwable -> L28
                    goto L2c
                L28:
                    r5 = move-exception
                    kotlin.ExceptionsKt.addSuppressed(r7, r5)
                L2c:
                    if (r7 != 0) goto L6a
                    java.lang.Object r5 = r8.invoke(r6)
                    java.lang.Number r5 = (java.lang.Number) r5
                    int r5 = r5.intValue()
                    r7 = -1
                    int r7 = (r0 > r7 ? 1 : (r0 == r7 ? 0 : -1))
                    if (r7 == 0) goto L69
                    long r7 = (long) r5
                    int r7 = (r0 > r7 ? 1 : (r0 == r7 ? 0 : -1))
                    if (r7 != 0) goto L44
                    goto L69
                L44:
                    java.io.IOException r6 = new java.io.IOException
                    java.lang.StringBuilder r7 = new java.lang.StringBuilder
                    java.lang.String r8 = "Content-Length ("
                    r7.<init>(r8)
                    java.lang.StringBuilder r7 = r7.append(r0)
                    java.lang.String r8 = ") and stream length ("
                    java.lang.StringBuilder r7 = r7.append(r8)
                    java.lang.StringBuilder r5 = r7.append(r5)
                    java.lang.String r7 = ") disagree"
                    java.lang.StringBuilder r5 = r5.append(r7)
                    java.lang.String r5 = r5.toString()
                    r6.<init>(r5)
                    throw r6
                L69:
                    return r6
                L6a:
                    throw r7
                L6b:
                    java.io.IOException r5 = new java.io.IOException
                    java.lang.StringBuilder r6 = new java.lang.StringBuilder
                    java.lang.String r7 = "Cannot buffer entire body for content length: "
                    r6.<init>(r7)
                    java.lang.StringBuilder r6 = r6.append(r0)
                    java.lang.String r6 = r6.toString()
                    r5.<init>(r6)
                    throw r5
                */
                throw new UnsupportedOperationException("Method not decompiled: okhttp3.ResponseBody.consumeSource(okhttp3.ResponseBody, kotlin.jvm.functions.Function1, kotlin.jvm.functions.Function1):java.lang.Object");
            }

            public final Reader charStream() {
                Reader reader = this.reader;
                if (reader != null) {
                    return reader;
                }
                BomAwareReader bomAwareReader = new BomAwareReader(get$this_asResponseBody(), charset());
                this.reader = bomAwareReader;
                return bomAwareReader;
            }

            /* JADX WARN: Multi-variable type inference failed */
            /* JADX WARN: Type inference failed for: r4v1, types: [java.lang.Throwable] */
            /* JADX WARN: Type inference failed for: r4v2, types: [java.lang.Throwable] */
            /* JADX WARN: Type inference failed for: r4v6 */
            public final String string() throws IOException {
                BufferedSource bufferedSourceSource = get$this_asResponseBody();
                String th = null;
                try {
                    BufferedSource bufferedSource = bufferedSourceSource;
                    String string = bufferedSource.readString(_UtilJvmKt.readBomAsCharset(bufferedSource, charset()));
                    if (bufferedSourceSource != null) {
                        try {
                            bufferedSourceSource.close();
                        } catch (Throwable th2) {
                            th = th2;
                        }
                    }
                    String str = th;
                    th = string;
                    th = str;
                } catch (Throwable th3) {
                    th = th3;
                    if (bufferedSourceSource != null) {
                        try {
                            bufferedSourceSource.close();
                        } catch (Throwable th4) {
                            ExceptionsKt.addSuppressed(th, th4);
                        }
                    }
                }
                if (th == 0) {
                    return th;
                }
                throw th;
            }

            private final Charset charset() {
                return Internal.charsetOrUtf8(get$contentType());
            }

            @Override // java.io.Closeable, java.lang.AutoCloseable
            public void close() {
                _UtilCommonKt.closeQuietly(get$this_asResponseBody());
            }

            /* JADX INFO: compiled from: ResponseBody.kt */
            @Metadata(d1 = {"\u00004\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u0019\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0000\b\u0000\u0018\u00002\u00020\u0001B\u0017\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005¢\u0006\u0004\b\u0006\u0010\u0007J \u0010\u000b\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\u000e2\u0006\u0010\u000f\u001a\u00020\f2\u0006\u0010\u0010\u001a\u00020\fH\u0016J\b\u0010\u0011\u001a\u00020\u0012H\u0016R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\b\u001a\u00020\tX\u0082\u000e¢\u0006\u0002\n\u0000R\u0010\u0010\n\u001a\u0004\u0018\u00010\u0001X\u0082\u000e¢\u0006\u0002\n\u0000¨\u0006\u0013"}, d2 = {"Lokhttp3/ResponseBody$BomAwareReader;", "Ljava/io/Reader;", "source", "Lokio/BufferedSource;", "charset", "Ljava/nio/charset/Charset;", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "(Lokio/BufferedSource;Ljava/nio/charset/Charset;)V", "closed", "", "delegate", "read", "", "cbuf", "", "off", "len", SplitLayout.TAG_CLOSE, "", "okhttp"}, k = 1, mv = {2, 2, 0}, xi = 48)
            public static final class BomAwareReader extends Reader {
                private final Charset charset;
                private boolean closed;
                private Reader delegate;
                private final BufferedSource source;

                public BomAwareReader(BufferedSource source, Charset charset) {
                    Intrinsics.checkNotNullParameter(source, "source");
                    Intrinsics.checkNotNullParameter(charset, "charset");
                    this.source = source;
                    this.charset = charset;
                }

                @Override // java.io.Reader
                public int read(char[] cbuf, int off, int len) throws IOException {
                    Intrinsics.checkNotNullParameter(cbuf, "cbuf");
                    if (this.closed) {
                        throw new IOException("Stream closed");
                    }
                    InputStreamReader inputStreamReader = this.delegate;
                    if (inputStreamReader == null) {
                        inputStreamReader = new InputStreamReader(this.source.inputStream(), _UtilJvmKt.readBomAsCharset(this.source, this.charset));
                        this.delegate = inputStreamReader;
                    }
                    return inputStreamReader.read(cbuf, off, len);
                }

                @Override // java.io.Reader, java.io.Closeable, java.lang.AutoCloseable
                public void close() throws IOException {
                    this.closed = true;
                    Reader reader = this.delegate;
                    if (reader != null) {
                        reader.close();
                    } else {
                        this.source.close();
                    }
                }
            }

            /* JADX INFO: compiled from: ResponseBody.kt */
            @Metadata(d1 = {"\u00006\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0012\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\t\n\u0002\b\u0002\b\u0086\u0003\u0018\u00002\u00020\u0001B\t\b\u0002¢\u0006\u0004\b\u0002\u0010\u0003J\u001d\u0010\u0006\u001a\u00020\u0005*\u00020\u00072\n\b\u0002\u0010\b\u001a\u0004\u0018\u00010\tH\u0007¢\u0006\u0002\b\nJ\u001d\u0010\u0006\u001a\u00020\u0005*\u00020\u000b2\n\b\u0002\u0010\b\u001a\u0004\u0018\u00010\tH\u0007¢\u0006\u0002\b\nJ\u001d\u0010\u0006\u001a\u00020\u0005*\u00020\f2\n\b\u0002\u0010\b\u001a\u0004\u0018\u00010\tH\u0007¢\u0006\u0002\b\nJ'\u0010\r\u001a\u00020\u0005*\u00020\u000e2\n\b\u0002\u0010\b\u001a\u0004\u0018\u00010\t2\b\b\u0002\u0010\u000f\u001a\u00020\u0010H\u0007¢\u0006\u0002\b\nJ\u001a\u0010\n\u001a\u00020\u00052\b\u0010\b\u001a\u0004\u0018\u00010\t2\u0006\u0010\u0011\u001a\u00020\u0007H\u0007J\u001a\u0010\n\u001a\u00020\u00052\b\u0010\b\u001a\u0004\u0018\u00010\t2\u0006\u0010\u0011\u001a\u00020\u000bH\u0007J\u001a\u0010\n\u001a\u00020\u00052\b\u0010\b\u001a\u0004\u0018\u00010\t2\u0006\u0010\u0011\u001a\u00020\fH\u0007J\"\u0010\n\u001a\u00020\u00052\b\u0010\b\u001a\u0004\u0018\u00010\t2\u0006\u0010\u000f\u001a\u00020\u00102\u0006\u0010\u0011\u001a\u00020\u000eH\u0007R\u0010\u0010\u0004\u001a\u00020\u00058\u0006X\u0087\u0004¢\u0006\u0002\n\u0000¨\u0006\u0012"}, d2 = {"Lokhttp3/ResponseBody$Companion;", "", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "()V", "EMPTY", "Lokhttp3/ResponseBody;", "toResponseBody", "", "contentType", "Lokhttp3/MediaType;", "create", "", "Lokio/ByteString;", "asResponseBody", "Lokio/BufferedSource;", "contentLength", "", "content", "okhttp"}, k = 1, mv = {2, 2, 0}, xi = 48)
            public static final class Companion {
                public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
                    this();
                }

                private Companion() {
                }

                public static /* synthetic */ ResponseBody create$default(Companion companion, String str, MediaType mediaType, int i, Object obj) {
                    if ((i & 1) != 0) {
                        mediaType = null;
                    }
                    return companion.create(str, mediaType);
                }

                @JvmStatic
                public final ResponseBody create(String str, MediaType mediaType) {
                    Intrinsics.checkNotNullParameter(str, "<this>");
                    Pair<Charset, MediaType> pairChooseCharset = Internal.chooseCharset(mediaType);
                    Charset charsetComponent1 = pairChooseCharset.component1();
                    MediaType mediaTypeComponent2 = pairChooseCharset.component2();
                    Buffer bufferWriteString = new Buffer().writeString(str, charsetComponent1);
                    return create(bufferWriteString, mediaTypeComponent2, bufferWriteString.size());
                }

                public static /* synthetic */ ResponseBody create$default(Companion companion, byte[] bArr, MediaType mediaType, int i, Object obj) {
                    if ((i & 1) != 0) {
                        mediaType = null;
                    }
                    return companion.create(bArr, mediaType);
                }

                @JvmStatic
                public final ResponseBody create(byte[] bArr, MediaType mediaType) {
                    Intrinsics.checkNotNullParameter(bArr, "<this>");
                    return create(new Buffer().write(bArr), mediaType, bArr.length);
                }

                public static /* synthetic */ ResponseBody create$default(Companion companion, ByteString byteString, MediaType mediaType, int i, Object obj) {
                    if ((i & 1) != 0) {
                        mediaType = null;
                    }
                    return companion.create(byteString, mediaType);
                }

                @JvmStatic
                public final ResponseBody create(ByteString byteString, MediaType mediaType) {
                    Intrinsics.checkNotNullParameter(byteString, "<this>");
                    return create(new Buffer().write(byteString), mediaType, byteString.size());
                }

                public static /* synthetic */ ResponseBody create$default(Companion companion, BufferedSource bufferedSource, MediaType mediaType, long j, int i, Object obj) {
                    if ((i & 1) != 0) {
                        mediaType = null;
                    }
                    if ((i & 2) != 0) {
                        j = -1;
                    }
                    return companion.create(bufferedSource, mediaType, j);
                }

                @JvmStatic
                public final ResponseBody create(final BufferedSource bufferedSource, final MediaType mediaType, final long j) {
                    Intrinsics.checkNotNullParameter(bufferedSource, "<this>");
                    return new ResponseBody() { // from class: okhttp3.ResponseBody$Companion$asResponseBody$1
                        @Override // okhttp3.ResponseBody
                        /* JADX INFO: renamed from: contentType, reason: from getter */
                        public MediaType get$contentType() {
                            return mediaType;
                        }

                        @Override // okhttp3.ResponseBody
                        /* JADX INFO: renamed from: contentLength, reason: from getter */
                        public long get$contentLength() {
                            return j;
                        }

                        @Override // okhttp3.ResponseBody
                        /* JADX INFO: renamed from: source, reason: from getter */
                        public BufferedSource get$this_asResponseBody() {
                            return bufferedSource;
                        }
                    };
                }

                @Deprecated(level = DeprecationLevel.WARNING, message = "Moved to extension function. Put the 'content' argument first to fix Java", replaceWith = @ReplaceWith(expression = "content.toResponseBody(contentType)", imports = {"okhttp3.ResponseBody.Companion.toResponseBody"}))
                @JvmStatic
                public final ResponseBody create(MediaType contentType, String content) {
                    Intrinsics.checkNotNullParameter(content, "content");
                    return create(content, contentType);
                }

                @Deprecated(level = DeprecationLevel.WARNING, message = "Moved to extension function. Put the 'content' argument first to fix Java", replaceWith = @ReplaceWith(expression = "content.toResponseBody(contentType)", imports = {"okhttp3.ResponseBody.Companion.toResponseBody"}))
                @JvmStatic
                public final ResponseBody create(MediaType contentType, byte[] content) {
                    Intrinsics.checkNotNullParameter(content, "content");
                    return create(content, contentType);
                }

                @Deprecated(level = DeprecationLevel.WARNING, message = "Moved to extension function. Put the 'content' argument first to fix Java", replaceWith = @ReplaceWith(expression = "content.toResponseBody(contentType)", imports = {"okhttp3.ResponseBody.Companion.toResponseBody"}))
                @JvmStatic
                public final ResponseBody create(MediaType contentType, ByteString content) {
                    Intrinsics.checkNotNullParameter(content, "content");
                    return create(content, contentType);
                }

                @Deprecated(level = DeprecationLevel.WARNING, message = "Moved to extension function. Put the 'content' argument first to fix Java", replaceWith = @ReplaceWith(expression = "content.asResponseBody(contentType, contentLength)", imports = {"okhttp3.ResponseBody.Companion.asResponseBody"}))
                @JvmStatic
                public final ResponseBody create(MediaType contentType, long contentLength, BufferedSource content) {
                    Intrinsics.checkNotNullParameter(content, "content");
                    return create(content, contentType, contentLength);
                }
            }

            static {
                Companion companion = new Companion(null);
                INSTANCE = companion;
                EMPTY = Companion.create$default(companion, ByteString.EMPTY, (MediaType) null, 1, (Object) null);
            }

            /* JADX WARN: Multi-variable type inference failed */
            /* JADX WARN: Type inference failed for: r3v1, types: [java.lang.Throwable] */
            /* JADX WARN: Type inference failed for: r3v11 */
            /* JADX WARN: Type inference failed for: r3v2, types: [java.lang.Throwable] */
            public final byte[] bytes() throws IOException {
                long jContentLength = get$contentLength();
                if (jContentLength > SieveCacheKt.NodeLinkMask) {
                    throw new IOException("Cannot buffer entire body for content length: " + jContentLength);
                }
                BufferedSource bufferedSourceSource = get$this_asResponseBody();
                byte[] th = null;
                try {
                    byte[] byteArray = bufferedSourceSource.readByteArray();
                    if (bufferedSourceSource != null) {
                        try {
                            bufferedSourceSource.close();
                        } catch (Throwable th2) {
                            th = th2;
                        }
                    }
                    th = th;
                    th = byteArray;
                } catch (Throwable th3) {
                    th = th3;
                    if (bufferedSourceSource != null) {
                        try {
                            bufferedSourceSource.close();
                        } catch (Throwable th4) {
                            ExceptionsKt.addSuppressed(th, th4);
                        }
                    }
                }
                if (th == 0) {
                    int length = th.length;
                    if (jContentLength == -1 || jContentLength == length) {
                        return th;
                    }
                    throw new IOException("Content-Length (" + jContentLength + ") and stream length (" + length + ") disagree");
                }
                throw th;
            }

            /* JADX WARN: Multi-variable type inference failed */
            /* JADX WARN: Type inference failed for: r3v1, types: [java.lang.Throwable] */
            /* JADX WARN: Type inference failed for: r3v11 */
            /* JADX WARN: Type inference failed for: r3v2, types: [java.lang.Throwable] */
            public final ByteString byteString() throws IOException {
                long jContentLength = get$contentLength();
                if (jContentLength > SieveCacheKt.NodeLinkMask) {
                    throw new IOException("Cannot buffer entire body for content length: " + jContentLength);
                }
                BufferedSource bufferedSourceSource = get$this_asResponseBody();
                ByteString th = null;
                try {
                    ByteString byteString = bufferedSourceSource.readByteString();
                    if (bufferedSourceSource != null) {
                        try {
                            bufferedSourceSource.close();
                        } catch (Throwable th2) {
                            th = th2;
                        }
                    }
                    th = th;
                    th = byteString;
                } catch (Throwable th3) {
                    th = th3;
                    if (bufferedSourceSource != null) {
                        try {
                            bufferedSourceSource.close();
                        } catch (Throwable th4) {
                            ExceptionsKt.addSuppressed(th, th4);
                        }
                    }
                }
                if (th == 0) {
                    int size = th.size();
                    if (jContentLength == -1 || jContentLength == size) {
                        return th;
                    }
                    throw new IOException("Content-Length (" + jContentLength + ") and stream length (" + size + ") disagree");
                }
                throw th;
            }
        }
