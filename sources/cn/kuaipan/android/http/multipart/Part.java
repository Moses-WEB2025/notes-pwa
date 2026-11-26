package cn.kuaipan.android.http.multipart;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import org.apache.http.util.EncodingUtils;

/* JADX INFO: loaded from: classes.dex */
public abstract class Part {
    protected static final String BOUNDARY = "----------------314159265358979323846";
    protected static final byte[] BOUNDARY_BYTES;
    protected static final String CHARSET = "; charset=";
    protected static final byte[] CHARSET_BYTES;
    protected static final String CONTENT_DISPOSITION = "Content-Disposition: form-data; name=";
    protected static final byte[] CONTENT_DISPOSITION_BYTES;
    protected static final String CONTENT_TRANSFER_ENCODING = "Content-Transfer-Encoding: ";
    protected static final byte[] CONTENT_TRANSFER_ENCODING_BYTES;
    protected static final String CONTENT_TYPE = "Content-Type: ";
    protected static final byte[] CONTENT_TYPE_BYTES;
    protected static final String CRLF = "\r\n";
    protected static final byte[] CRLF_BYTES;
    private static final byte[] DEFAULT_BOUNDARY_BYTES;
    protected static final String EXTRA = "--";
    protected static final byte[] EXTRA_BYTES;
    protected static final String QUOTE = "\"";
    protected static final byte[] QUOTE_BYTES;
    private byte[] boundaryBytes;

    public abstract String getCharSet();

    public abstract String getContentType();

    public abstract String getName();

    public abstract String getTransferEncoding();

    public boolean isRepeatable() {
        return true;
    }

    protected abstract long lengthOfData() throws IOException;

    protected abstract void sendData(OutputStream outputStream) throws IOException;

    static {
        byte[] asciiBytes = EncodingUtils.getAsciiBytes(BOUNDARY);
        BOUNDARY_BYTES = asciiBytes;
        DEFAULT_BOUNDARY_BYTES = asciiBytes;
        CRLF_BYTES = EncodingUtils.getAsciiBytes("\r\n");
        QUOTE_BYTES = EncodingUtils.getAsciiBytes(QUOTE);
        EXTRA_BYTES = EncodingUtils.getAsciiBytes(EXTRA);
        CONTENT_DISPOSITION_BYTES = EncodingUtils.getAsciiBytes(CONTENT_DISPOSITION);
        CONTENT_TYPE_BYTES = EncodingUtils.getAsciiBytes(CONTENT_TYPE);
        CHARSET_BYTES = EncodingUtils.getAsciiBytes(CHARSET);
        CONTENT_TRANSFER_ENCODING_BYTES = EncodingUtils.getAsciiBytes(CONTENT_TRANSFER_ENCODING);
    }

    public static String getBoundary() {
        return BOUNDARY;
    }

    protected byte[] getPartBoundary() {
        byte[] bArr = this.boundaryBytes;
        return bArr == null ? DEFAULT_BOUNDARY_BYTES : bArr;
    }

    void setPartBoundary(byte[] bArr) {
        this.boundaryBytes = bArr;
    }

    protected void sendStart(OutputStream outputStream) throws IOException {
        outputStream.write(EXTRA_BYTES);
        outputStream.write(getPartBoundary());
        outputStream.write(CRLF_BYTES);
    }

    protected void sendDispositionHeader(OutputStream outputStream) throws IOException {
        outputStream.write(CONTENT_DISPOSITION_BYTES);
        byte[] bArr = QUOTE_BYTES;
        outputStream.write(bArr);
        outputStream.write(EncodingUtils.getAsciiBytes(getName()));
        outputStream.write(bArr);
    }

    protected void sendContentTypeHeader(OutputStream outputStream) throws IOException {
        String contentType = getContentType();
        if (contentType != null) {
            outputStream.write(CRLF_BYTES);
            outputStream.write(CONTENT_TYPE_BYTES);
            outputStream.write(EncodingUtils.getAsciiBytes(contentType));
            String charSet = getCharSet();
            if (charSet != null) {
                outputStream.write(CHARSET_BYTES);
                outputStream.write(EncodingUtils.getAsciiBytes(charSet));
            }
        }
    }

    protected void sendTransferEncodingHeader(OutputStream outputStream) throws IOException {
        String transferEncoding = getTransferEncoding();
        if (transferEncoding != null) {
            outputStream.write(CRLF_BYTES);
            outputStream.write(CONTENT_TRANSFER_ENCODING_BYTES);
            outputStream.write(EncodingUtils.getAsciiBytes(transferEncoding));
        }
    }

    protected void sendEndOfHeader(OutputStream outputStream) throws IOException {
        byte[] bArr = CRLF_BYTES;
        outputStream.write(bArr);
        outputStream.write(bArr);
    }

    protected void sendEnd(OutputStream outputStream) throws IOException {
        outputStream.write(CRLF_BYTES);
    }

    public void send(OutputStream outputStream) throws IOException {
        sendStart(outputStream);
        sendDispositionHeader(outputStream);
        sendContentTypeHeader(outputStream);
        sendTransferEncodingHeader(outputStream);
        sendEndOfHeader(outputStream);
        sendData(outputStream);
        sendEnd(outputStream);
    }

    public long length() throws IOException {
        if (lengthOfData() < 0) {
            return -1L;
        }
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        sendStart(byteArrayOutputStream);
        sendDispositionHeader(byteArrayOutputStream);
        sendContentTypeHeader(byteArrayOutputStream);
        sendTransferEncodingHeader(byteArrayOutputStream);
        sendEndOfHeader(byteArrayOutputStream);
        sendEnd(byteArrayOutputStream);
        return ((long) byteArrayOutputStream.size()) + lengthOfData();
    }

    public String toString() {
        return getName();
    }

    public static void sendParts(OutputStream outputStream, Part[] partArr) throws IOException {
        sendParts(outputStream, partArr, DEFAULT_BOUNDARY_BYTES);
    }

    public static void sendParts(OutputStream outputStream, Part[] partArr, byte[] bArr) throws IOException {
        if (partArr == null) {
            throw new IllegalArgumentException("Parts may not be null");
        }
        if (bArr == null || bArr.length == 0) {
            throw new IllegalArgumentException("partBoundary may not be empty");
        }
        for (int i = 0; i < partArr.length; i++) {
            partArr[i].setPartBoundary(bArr);
            partArr[i].send(outputStream);
        }
        byte[] bArr2 = EXTRA_BYTES;
        outputStream.write(bArr2);
        outputStream.write(bArr);
        outputStream.write(bArr2);
        outputStream.write(CRLF_BYTES);
    }

    public static long getLengthOfParts(Part[] partArr) throws IOException {
        return getLengthOfParts(partArr, DEFAULT_BOUNDARY_BYTES);
    }

    public static long getLengthOfParts(Part[] partArr, byte[] bArr) throws IOException {
        if (partArr == null) {
            throw new IllegalArgumentException("Parts may not be null");
        }
        long j = 0;
        for (int i = 0; i < partArr.length; i++) {
            partArr[i].setPartBoundary(bArr);
            long length = partArr[i].length();
            if (length < 0) {
                return -1L;
            }
            j += length;
        }
        byte[] bArr2 = EXTRA_BYTES;
        return j + ((long) bArr2.length) + ((long) bArr.length) + ((long) bArr2.length) + ((long) CRLF_BYTES.length);
    }
}
