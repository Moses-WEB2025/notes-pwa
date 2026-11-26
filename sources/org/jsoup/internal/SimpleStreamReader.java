package org.jsoup.internal;

import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.nio.charset.CoderResult;
import java.nio.charset.CodingErrorAction;
import org.jsoup.helper.Validate;

/* JADX INFO: loaded from: classes6.dex */
public class SimpleStreamReader extends Reader {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    private ByteBuffer byteBuf;
    private final CharsetDecoder decoder;
    private final InputStream in;

    public SimpleStreamReader(InputStream inputStream, Charset charset) {
        this.in = inputStream;
        this.decoder = charset.newDecoder().onMalformedInput(CodingErrorAction.REPLACE).onUnmappableCharacter(CodingErrorAction.REPLACE);
        ByteBuffer byteBufferWrap = ByteBuffer.wrap(SimpleBufferedInput.BufferPool.borrow());
        this.byteBuf = byteBufferWrap;
        byteBufferWrap.flip();
    }

    @Override // java.io.Reader
    public int read(char[] cArr, int i, int i2) throws IOException {
        Validate.notNull(this.byteBuf);
        CharBuffer charBufferWrap = CharBuffer.wrap(cArr, i, i2);
        if (charBufferWrap.position() != 0) {
            charBufferWrap = charBufferWrap.slice();
        }
        boolean z = false;
        while (true) {
            CoderResult coderResultDecode = this.decoder.decode(this.byteBuf, charBufferWrap, z);
            if (coderResultDecode.isUnderflow()) {
                if (!z && charBufferWrap.hasRemaining() && (charBufferWrap.position() <= 0 || this.in.available() > 0)) {
                    if (bufferUp() < 0) {
                        if (charBufferWrap.position() == 0 && !this.byteBuf.hasRemaining()) {
                            z = true;
                            break;
                        }
                        z = true;
                    } else {
                        continue;
                    }
                } else {
                    break;
                }
            } else {
                if (coderResultDecode.isOverflow()) {
                    break;
                }
                coderResultDecode.throwException();
            }
        }
        if (z) {
            this.decoder.reset();
        }
        if (charBufferWrap.position() == 0) {
            return -1;
        }
        return charBufferWrap.position();
    }

    private int bufferUp() throws IOException {
        this.byteBuf.compact();
        try {
            int iPosition = this.byteBuf.position();
            int i = this.in.read(this.byteBuf.array(), this.byteBuf.arrayOffset() + iPosition, this.byteBuf.limit() - iPosition);
            if (i < 0) {
                this.byteBuf.flip();
                return i;
            }
            if (i == 0) {
                throw new IOException("Underlying input stream returned zero bytes");
            }
            this.byteBuf.position(iPosition + i);
            this.byteBuf.flip();
            return this.byteBuf.remaining();
        } catch (Throwable th) {
            this.byteBuf.flip();
            throw th;
        }
    }

    @Override // java.io.Reader, java.io.Closeable, java.lang.AutoCloseable
    public void close() throws IOException {
        if (this.byteBuf == null) {
            return;
        }
        SimpleBufferedInput.BufferPool.release(this.byteBuf.array());
        this.byteBuf = null;
        this.in.close();
    }
}
