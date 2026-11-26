package net.lingala.zip4j.io.inputstream;

import java.io.EOFException;
import java.io.IOException;
import java.io.InputStream;
import java.io.PushbackInputStream;
import java.util.zip.DataFormatException;
import java.util.zip.Inflater;

/* JADX INFO: loaded from: classes6.dex */
public class InflaterInputStream extends DecompressedInputStream {
    private byte[] buff;
    private Inflater inflater;
    private int len;
    private byte[] singleByteBuffer;

    public InflaterInputStream(CipherInputStream cipherInputStream, int i) {
        super(cipherInputStream);
        this.singleByteBuffer = new byte[1];
        this.inflater = new Inflater(true);
        this.buff = new byte[i];
    }

    @Override // net.lingala.zip4j.io.inputstream.DecompressedInputStream, java.io.InputStream
    public int read() throws IOException {
        if (read(this.singleByteBuffer) == -1) {
            return -1;
        }
        return this.singleByteBuffer[0];
    }

    @Override // net.lingala.zip4j.io.inputstream.DecompressedInputStream, java.io.InputStream
    public int read(byte[] bArr) throws IOException {
        return read(bArr, 0, bArr.length);
    }

    @Override // net.lingala.zip4j.io.inputstream.DecompressedInputStream, java.io.InputStream
    public int read(byte[] bArr, int i, int i2) throws IOException {
        while (true) {
            try {
                int iInflate = this.inflater.inflate(bArr, i, i2);
                if (iInflate != 0) {
                    return iInflate;
                }
                if (!this.inflater.finished() && !this.inflater.needsDictionary()) {
                    if (this.inflater.needsInput()) {
                        fill();
                    }
                }
                return -1;
            } catch (DataFormatException e) {
                throw new IOException(e);
            }
        }
    }

    @Override // net.lingala.zip4j.io.inputstream.DecompressedInputStream
    public void endOfEntryReached(InputStream inputStream) throws IOException {
        Inflater inflater = this.inflater;
        if (inflater != null) {
            inflater.end();
            this.inflater = null;
        }
        super.endOfEntryReached(inputStream);
    }

    @Override // net.lingala.zip4j.io.inputstream.DecompressedInputStream
    public void pushBackInputStreamIfNecessary(PushbackInputStream pushbackInputStream) throws IOException {
        int remaining = this.inflater.getRemaining();
        if (remaining > 0) {
            pushbackInputStream.unread(getLastReadRawDataCache(), this.len - remaining, remaining);
        }
    }

    @Override // net.lingala.zip4j.io.inputstream.DecompressedInputStream, java.io.InputStream, java.io.Closeable, java.lang.AutoCloseable
    public void close() throws IOException {
        Inflater inflater = this.inflater;
        if (inflater != null) {
            inflater.end();
        }
        super.close();
    }

    private void fill() throws IOException {
        byte[] bArr = this.buff;
        int i = super.read(bArr, 0, bArr.length);
        this.len = i;
        if (i == -1) {
            throw new EOFException("Unexpected end of input stream");
        }
        this.inflater.setInput(this.buff, 0, i);
    }
}
