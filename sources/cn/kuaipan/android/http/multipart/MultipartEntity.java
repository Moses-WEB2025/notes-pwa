package cn.kuaipan.android.http.multipart;

import android.util.Log;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Random;
import org.apache.http.Header;
import org.apache.http.entity.AbstractHttpEntity;
import org.apache.http.message.BasicHeader;
import org.apache.http.params.HttpParams;
import org.apache.http.util.EncodingUtils;

/* JADX INFO: loaded from: classes.dex */
public class MultipartEntity extends AbstractHttpEntity {
    public static final String MULTIPART_BOUNDARY = "http.method.multipart.boundary";
    private static byte[] MULTIPART_CHARS = EncodingUtils.getAsciiBytes("-_1234567890abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ");
    private static final String MULTIPART_FORM_CONTENT_TYPE = "multipart/form-data";
    private boolean contentConsumed = false;
    private byte[] multipartBoundary;
    private HttpParams params;
    protected Part[] parts;

    @Override // org.apache.http.HttpEntity
    public boolean isStreaming() {
        return false;
    }

    private static byte[] generateMultipartBoundary() {
        Random random = new Random();
        int iNextInt = random.nextInt(11) + 30;
        byte[] bArr = new byte[iNextInt];
        for (int i = 0; i < iNextInt; i++) {
            byte[] bArr2 = MULTIPART_CHARS;
            bArr[i] = bArr2[random.nextInt(bArr2.length)];
        }
        return bArr;
    }

    public MultipartEntity(Part[] partArr, HttpParams httpParams) {
        if (partArr == null) {
            throw new IllegalArgumentException("parts cannot be null");
        }
        if (httpParams == null) {
            throw new IllegalArgumentException("params cannot be null");
        }
        this.parts = partArr;
        this.params = httpParams;
    }

    public MultipartEntity(Part[] partArr) {
        setContentType("multipart/form-data");
        if (partArr == null) {
            throw new IllegalArgumentException("parts cannot be null");
        }
        this.parts = partArr;
        this.params = null;
    }

    protected byte[] getMultipartBoundary() {
        if (this.multipartBoundary == null) {
            HttpParams httpParams = this.params;
            String str = httpParams != null ? (String) httpParams.getParameter(MULTIPART_BOUNDARY) : null;
            if (str != null) {
                this.multipartBoundary = EncodingUtils.getAsciiBytes(str);
            } else {
                this.multipartBoundary = generateMultipartBoundary();
            }
        }
        return this.multipartBoundary;
    }

    public void appendPart(Part[] partArr) {
        if (partArr == null || partArr.length <= 0) {
            return;
        }
        Part[] partArr2 = this.parts;
        if (partArr2 == null || partArr2.length <= 0) {
            this.parts = partArr;
            return;
        }
        Part[] partArr3 = new Part[partArr2.length + partArr.length];
        this.parts = partArr3;
        System.arraycopy(partArr2, 0, partArr3, 0, partArr2.length);
        System.arraycopy(partArr, 0, this.parts, partArr2.length, partArr.length);
    }

    public Part[] getParts() {
        Part[] partArr = this.parts;
        Part[] partArr2 = new Part[partArr.length];
        System.arraycopy(partArr, 0, partArr2, 0, partArr.length);
        return partArr2;
    }

    @Override // org.apache.http.HttpEntity
    public boolean isRepeatable() {
        int i = 0;
        while (true) {
            Part[] partArr = this.parts;
            if (i >= partArr.length) {
                return true;
            }
            if (!partArr[i].isRepeatable()) {
                return false;
            }
            i++;
        }
    }

    @Override // org.apache.http.HttpEntity
    public void writeTo(OutputStream outputStream) throws IOException {
        Part.sendParts(outputStream, this.parts, getMultipartBoundary());
    }

    @Override // org.apache.http.entity.AbstractHttpEntity, org.apache.http.HttpEntity
    public Header getContentType() {
        StringBuffer stringBuffer = new StringBuffer("multipart/form-data; boundary=");
        stringBuffer.append(EncodingUtils.getAsciiString(getMultipartBoundary()));
        return new BasicHeader("Content-Type", stringBuffer.toString());
    }

    @Override // org.apache.http.HttpEntity
    public long getContentLength() {
        try {
            return Part.getLengthOfParts(this.parts, getMultipartBoundary());
        } catch (Exception e) {
            Log.e("Multipart", "An exception occurred while getting the length of the parts", e);
            return 0L;
        }
    }

    @Override // org.apache.http.HttpEntity
    public InputStream getContent() throws IllegalStateException, IOException {
        if (!isRepeatable() && this.contentConsumed) {
            throw new IllegalStateException("Content has been consumed");
        }
        this.contentConsumed = true;
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        Part.sendParts(byteArrayOutputStream, this.parts, this.multipartBoundary);
        return new ByteArrayInputStream(byteArrayOutputStream.toByteArray());
    }
}
