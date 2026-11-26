package com.google.common.io;

import androidx.collection.SieveCacheKt;
import com.google.common.base.Ascii;
import com.google.common.base.Optional;
import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableList;
import com.google.common.hash.Funnels;
import com.google.common.hash.HashCode;
import com.google.common.hash.HashFunction;
import com.google.common.hash.Hasher;
import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.Reader;
import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;

/* JADX INFO: loaded from: classes2.dex */
@ElementTypesAreNonnullByDefault
public abstract class ByteSource {
    public abstract InputStream openStream() throws IOException;

    protected ByteSource() {
    }

    public CharSource asCharSource(Charset charset) {
        return new AsCharSource(charset);
    }

    public InputStream openBufferedStream() throws IOException {
        InputStream inputStreamOpenStream = openStream();
        if (inputStreamOpenStream instanceof BufferedInputStream) {
            return (BufferedInputStream) inputStreamOpenStream;
        }
        return new BufferedInputStream(inputStreamOpenStream);
    }

    public ByteSource slice(long offset, long length) {
        return new SlicedByteSource(offset, length);
    }

    public boolean isEmpty() throws Throwable {
        Optional<Long> optionalSizeIfKnown = sizeIfKnown();
        if (optionalSizeIfKnown.isPresent()) {
            return optionalSizeIfKnown.get().longValue() == 0;
        }
        Closer closerCreate = Closer.create();
        try {
            boolean z = ((InputStream) closerCreate.register(openStream())).read() == -1;
            closerCreate.close();
            return z;
        } catch (Throwable th) {
            try {
                throw closerCreate.rethrow(th);
            } catch (Throwable th2) {
                closerCreate.close();
                throw th2;
            }
        }
    }

    public Optional<Long> sizeIfKnown() {
        return Optional.absent();
    }

    public long size() throws Throwable {
        Optional<Long> optionalSizeIfKnown = sizeIfKnown();
        if (optionalSizeIfKnown.isPresent()) {
            return optionalSizeIfKnown.get().longValue();
        }
        Closer closerCreate = Closer.create();
        try {
            long jCountBySkipping = countBySkipping((InputStream) closerCreate.register(openStream()));
            closerCreate.close();
            return jCountBySkipping;
        } catch (IOException unused) {
            closerCreate.close();
            Closer closerCreate2 = Closer.create();
            try {
                long jExhaust = ByteStreams.exhaust((InputStream) closerCreate2.register(openStream()));
                closerCreate2.close();
                return jExhaust;
            } catch (Throwable th) {
                try {
                    throw closerCreate2.rethrow(th);
                } catch (Throwable th2) {
                    closerCreate2.close();
                    throw th2;
                }
            }
        } catch (Throwable th3) {
            closerCreate.close();
            throw th3;
        }
    }

    private long countBySkipping(InputStream in) throws IOException {
        long j = 0;
        while (true) {
            long jSkipUpTo = ByteStreams.skipUpTo(in, SieveCacheKt.NodeLinkMask);
            if (jSkipUpTo <= 0) {
                return j;
            }
            j += jSkipUpTo;
        }
    }

    public long copyTo(OutputStream output) throws Throwable {
        Preconditions.checkNotNull(output);
        Closer closerCreate = Closer.create();
        try {
            long jCopy = ByteStreams.copy((InputStream) closerCreate.register(openStream()), output);
            closerCreate.close();
            return jCopy;
        } catch (Throwable th) {
            try {
                throw closerCreate.rethrow(th);
            } catch (Throwable th2) {
                closerCreate.close();
                throw th2;
            }
        }
    }

    public long copyTo(ByteSink sink) throws Throwable {
        Preconditions.checkNotNull(sink);
        Closer closerCreate = Closer.create();
        try {
            long jCopy = ByteStreams.copy((InputStream) closerCreate.register(openStream()), (OutputStream) closerCreate.register(sink.openStream()));
            closerCreate.close();
            return jCopy;
        } catch (Throwable th) {
            try {
                throw closerCreate.rethrow(th);
            } catch (Throwable th2) {
                closerCreate.close();
                throw th2;
            }
        }
    }

    public byte[] read() throws Throwable {
        byte[] byteArray;
        Closer closerCreate = Closer.create();
        try {
            InputStream inputStream = (InputStream) closerCreate.register(openStream());
            Optional<Long> optionalSizeIfKnown = sizeIfKnown();
            if (optionalSizeIfKnown.isPresent()) {
                byteArray = ByteStreams.toByteArray(inputStream, optionalSizeIfKnown.get().longValue());
            } else {
                byteArray = ByteStreams.toByteArray(inputStream);
            }
            closerCreate.close();
            return byteArray;
        } catch (Throwable th) {
            try {
                throw closerCreate.rethrow(th);
            } catch (Throwable th2) {
                closerCreate.close();
                throw th2;
            }
        }
    }

    @ParametricNullness
    public <T> T read(ByteProcessor<T> byteProcessor) throws Throwable {
        Preconditions.checkNotNull(byteProcessor);
        Closer closerCreate = Closer.create();
        try {
            T t = (T) ByteStreams.readBytes((InputStream) closerCreate.register(openStream()), byteProcessor);
            closerCreate.close();
            return t;
        } catch (Throwable th) {
            try {
                throw closerCreate.rethrow(th);
            } catch (Throwable th2) {
                closerCreate.close();
                throw th2;
            }
        }
    }

    public HashCode hash(HashFunction hashFunction) throws Throwable {
        Hasher hasherNewHasher = hashFunction.newHasher();
        copyTo(Funnels.asOutputStream(hasherNewHasher));
        return hasherNewHasher.hash();
    }

    public boolean contentEquals(ByteSource other) throws Throwable {
        int i;
        Preconditions.checkNotNull(other);
        byte[] bArrCreateBuffer = ByteStreams.createBuffer();
        byte[] bArrCreateBuffer2 = ByteStreams.createBuffer();
        Closer closerCreate = Closer.create();
        try {
            InputStream inputStream = (InputStream) closerCreate.register(openStream());
            InputStream inputStream2 = (InputStream) closerCreate.register(other.openStream());
            do {
                i = ByteStreams.read(inputStream, bArrCreateBuffer, 0, bArrCreateBuffer.length);
                if (i == ByteStreams.read(inputStream2, bArrCreateBuffer2, 0, bArrCreateBuffer2.length) && Arrays.equals(bArrCreateBuffer, bArrCreateBuffer2)) {
                }
                closerCreate.close();
                return false;
            } while (i == bArrCreateBuffer.length);
            closerCreate.close();
            return true;
        } catch (Throwable th) {
            try {
                throw closerCreate.rethrow(th);
            } catch (Throwable th2) {
                closerCreate.close();
                throw th2;
            }
        }
    }

    public static ByteSource concat(Iterable<? extends ByteSource> sources) {
        return new ConcatenatedByteSource(sources);
    }

    public static ByteSource concat(Iterator<? extends ByteSource> sources) {
        return concat(ImmutableList.copyOf(sources));
    }

    public static ByteSource concat(ByteSource... sources) {
        return concat(ImmutableList.copyOf(sources));
    }

    public static ByteSource wrap(byte[] b) {
        return new ByteArrayByteSource(b);
    }

    public static ByteSource empty() {
        return EmptyByteSource.INSTANCE;
    }

    class AsCharSource extends CharSource {
        final Charset charset;

        AsCharSource(Charset charset) {
            this.charset = (Charset) Preconditions.checkNotNull(charset);
        }

        @Override // com.google.common.io.CharSource
        public ByteSource asByteSource(Charset charset) {
            if (charset.equals(this.charset)) {
                return ByteSource.this;
            }
            return super.asByteSource(charset);
        }

        @Override // com.google.common.io.CharSource
        public Reader openStream() throws IOException {
            return new InputStreamReader(ByteSource.this.openStream(), this.charset);
        }

        @Override // com.google.common.io.CharSource
        public String read() throws IOException {
            return new String(ByteSource.this.read(), this.charset);
        }

        public String toString() {
            return ByteSource.this.toString() + ".asCharSource(" + this.charset + ")";
        }
    }

    private final class SlicedByteSource extends ByteSource {
        final long length;
        final long offset;

        SlicedByteSource(long offset, long length) {
            Preconditions.checkArgument(offset >= 0, "offset (%s) may not be negative", offset);
            Preconditions.checkArgument(length >= 0, "length (%s) may not be negative", length);
            this.offset = offset;
            this.length = length;
        }

        @Override // com.google.common.io.ByteSource
        public InputStream openStream() throws IOException {
            return sliceStream(ByteSource.this.openStream());
        }

        @Override // com.google.common.io.ByteSource
        public InputStream openBufferedStream() throws IOException {
            return sliceStream(ByteSource.this.openBufferedStream());
        }

        private InputStream sliceStream(InputStream in) throws Throwable {
            long j = this.offset;
            if (j > 0) {
                try {
                    if (ByteStreams.skipUpTo(in, j) < this.offset) {
                        in.close();
                        return new ByteArrayInputStream(new byte[0]);
                    }
                } catch (Throwable th) {
                    Closer closerCreate = Closer.create();
                    closerCreate.register(in);
                    try {
                        throw closerCreate.rethrow(th);
                    } catch (Throwable th2) {
                        closerCreate.close();
                        throw th2;
                    }
                }
            }
            return ByteStreams.limit(in, this.length);
        }

        @Override // com.google.common.io.ByteSource
        public ByteSource slice(long offset, long length) {
            Preconditions.checkArgument(offset >= 0, "offset (%s) may not be negative", offset);
            Preconditions.checkArgument(length >= 0, "length (%s) may not be negative", length);
            long j = this.length - offset;
            if (j <= 0) {
                return ByteSource.empty();
            }
            return ByteSource.this.slice(this.offset + offset, Math.min(length, j));
        }

        @Override // com.google.common.io.ByteSource
        public boolean isEmpty() throws IOException {
            return this.length == 0 || super.isEmpty();
        }

        @Override // com.google.common.io.ByteSource
        public Optional<Long> sizeIfKnown() {
            Optional<Long> optionalSizeIfKnown = ByteSource.this.sizeIfKnown();
            if (optionalSizeIfKnown.isPresent()) {
                long jLongValue = optionalSizeIfKnown.get().longValue();
                return Optional.of(Long.valueOf(Math.min(this.length, jLongValue - Math.min(this.offset, jLongValue))));
            }
            return Optional.absent();
        }

        public String toString() {
            return ByteSource.this.toString() + ".slice(" + this.offset + ", " + this.length + ")";
        }
    }

    private static class ByteArrayByteSource extends ByteSource {
        final byte[] bytes;
        final int length;
        final int offset;

        ByteArrayByteSource(byte[] bytes) {
            this(bytes, 0, bytes.length);
        }

        ByteArrayByteSource(byte[] bytes, int offset, int length) {
            this.bytes = bytes;
            this.offset = offset;
            this.length = length;
        }

        @Override // com.google.common.io.ByteSource
        public InputStream openStream() {
            return new ByteArrayInputStream(this.bytes, this.offset, this.length);
        }

        @Override // com.google.common.io.ByteSource
        public InputStream openBufferedStream() {
            return openStream();
        }

        @Override // com.google.common.io.ByteSource
        public boolean isEmpty() {
            return this.length == 0;
        }

        @Override // com.google.common.io.ByteSource
        public long size() {
            return this.length;
        }

        @Override // com.google.common.io.ByteSource
        public Optional<Long> sizeIfKnown() {
            return Optional.of(Long.valueOf(this.length));
        }

        @Override // com.google.common.io.ByteSource
        public byte[] read() {
            byte[] bArr = this.bytes;
            int i = this.offset;
            return Arrays.copyOfRange(bArr, i, this.length + i);
        }

        @Override // com.google.common.io.ByteSource
        @ParametricNullness
        public <T> T read(ByteProcessor<T> processor) throws IOException {
            processor.processBytes(this.bytes, this.offset, this.length);
            return processor.getResult();
        }

        @Override // com.google.common.io.ByteSource
        public long copyTo(OutputStream output) throws IOException {
            output.write(this.bytes, this.offset, this.length);
            return this.length;
        }

        @Override // com.google.common.io.ByteSource
        public HashCode hash(HashFunction hashFunction) throws IOException {
            return hashFunction.hashBytes(this.bytes, this.offset, this.length);
        }

        @Override // com.google.common.io.ByteSource
        public ByteSource slice(long offset, long length) {
            Preconditions.checkArgument(offset >= 0, "offset (%s) may not be negative", offset);
            Preconditions.checkArgument(length >= 0, "length (%s) may not be negative", length);
            long jMin = Math.min(offset, this.length);
            return new ByteArrayByteSource(this.bytes, this.offset + ((int) jMin), (int) Math.min(length, ((long) this.length) - jMin));
        }

        public String toString() {
            return "ByteSource.wrap(" + Ascii.truncate(BaseEncoding.base16().encode(this.bytes, this.offset, this.length), 30, "...") + ")";
        }
    }

    private static final class EmptyByteSource extends ByteArrayByteSource {
        static final EmptyByteSource INSTANCE = new EmptyByteSource();

        EmptyByteSource() {
            super(new byte[0]);
        }

        @Override // com.google.common.io.ByteSource
        public CharSource asCharSource(Charset charset) {
            Preconditions.checkNotNull(charset);
            return CharSource.empty();
        }

        @Override // com.google.common.io.ByteSource.ByteArrayByteSource, com.google.common.io.ByteSource
        public byte[] read() {
            return this.bytes;
        }

        @Override // com.google.common.io.ByteSource.ByteArrayByteSource
        public String toString() {
            return "ByteSource.empty()";
        }
    }

    private static final class ConcatenatedByteSource extends ByteSource {
        final Iterable<? extends ByteSource> sources;

        ConcatenatedByteSource(Iterable<? extends ByteSource> sources) {
            this.sources = (Iterable) Preconditions.checkNotNull(sources);
        }

        @Override // com.google.common.io.ByteSource
        public InputStream openStream() throws IOException {
            return new MultiInputStream(this.sources.iterator());
        }

        @Override // com.google.common.io.ByteSource
        public boolean isEmpty() throws IOException {
            Iterator<? extends ByteSource> it = this.sources.iterator();
            while (it.hasNext()) {
                if (!it.next().isEmpty()) {
                    return false;
                }
            }
            return true;
        }

        @Override // com.google.common.io.ByteSource
        public Optional<Long> sizeIfKnown() {
            Iterable<? extends ByteSource> iterable = this.sources;
            if (!(iterable instanceof Collection)) {
                return Optional.absent();
            }
            Iterator<? extends ByteSource> it = iterable.iterator();
            long jLongValue = 0;
            while (it.hasNext()) {
                Optional<Long> optionalSizeIfKnown = it.next().sizeIfKnown();
                if (!optionalSizeIfKnown.isPresent()) {
                    return Optional.absent();
                }
                jLongValue += optionalSizeIfKnown.get().longValue();
                if (jLongValue < 0) {
                    return Optional.of(Long.MAX_VALUE);
                }
            }
            return Optional.of(Long.valueOf(jLongValue));
        }

        @Override // com.google.common.io.ByteSource
        public long size() throws IOException {
            Iterator<? extends ByteSource> it = this.sources.iterator();
            long size = 0;
            while (it.hasNext()) {
                size += it.next().size();
                if (size < 0) {
                    return Long.MAX_VALUE;
                }
            }
            return size;
        }

        public String toString() {
            return "ByteSource.concat(" + this.sources + ")";
        }
    }
}
