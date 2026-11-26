package cn.kuaipan.android.utils;

import androidx.collection.SieveCacheKt;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpEntityEnclosingRequest;
import org.apache.http.HttpRequest;
import org.apache.http.HttpResponse;

/* JADX INFO: loaded from: classes.dex */
public class HttpUtils {
    private HttpUtils() {
    }

    public static StringBuffer toString(HttpRequest httpRequest) {
        HttpEntity entity;
        if (httpRequest == null) {
            return null;
        }
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append(httpRequest.getRequestLine());
        stringBuffer.append(StringUtils.LF);
        for (Header header : httpRequest.getAllHeaders()) {
            stringBuffer.append(header.toString().trim());
            stringBuffer.append(StringUtils.LF);
        }
        if ((httpRequest instanceof HttpEntityEnclosingRequest) && (entity = ((HttpEntityEnclosingRequest) httpRequest).getEntity()) != null) {
            stringBuffer.append("Content:\n");
            try {
                if (entity.isRepeatable()) {
                    stringBuffer.append(entityToString(entity, 1024));
                } else {
                    stringBuffer.append(" [DATA CAN NOT REPEAT]");
                }
            } catch (Exception unused) {
                stringBuffer.append(" [FAILED OUTPUT DATA]");
            }
            stringBuffer.append(StringUtils.LF);
        }
        return stringBuffer;
    }

    private static String entityToString(HttpEntity httpEntity, int i) throws IOException {
        String str;
        long contentLength = httpEntity.getContentLength();
        long j = i;
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream((int) Math.min(contentLength < 0 ? SieveCacheKt.NodeLinkMask : contentLength, j));
        int i2 = 0;
        boolean z = contentLength >= 0 && contentLength <= j;
        if (z) {
            httpEntity.writeTo(byteArrayOutputStream);
        } else {
            InputStream content = httpEntity.getContent();
            try {
                byte[] bArr = new byte[1024];
                int i3 = 0;
                while (true) {
                    int i4 = content.read(bArr);
                    if (i4 == -1 || i3 >= i) {
                        break;
                    }
                    byteArrayOutputStream.write(bArr, 0, Math.min(i4, i - i3));
                    i3 += i4;
                }
                content.close();
                i2 = i3;
            } catch (Throwable th) {
                content.close();
                throw th;
            }
        }
        StringBuilder sbAppend = new StringBuilder().append(byteArrayOutputStream.toString());
        if (z || i2 < i) {
            str = "";
        } else {
            str = "\n [TOO MUCH DATA TO INCLUDE, SIZE=" + contentLength + "]";
        }
        return sbAppend.append(str).toString();
    }

    public static StringBuffer toString(HttpResponse httpResponse) {
        if (httpResponse == null) {
            return null;
        }
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append(httpResponse.getStatusLine());
        stringBuffer.append(StringUtils.LF);
        for (Header header : httpResponse.getAllHeaders()) {
            stringBuffer.append(header.toString().trim());
            stringBuffer.append(StringUtils.LF);
        }
        HttpEntity entity = httpResponse.getEntity();
        if (entity != null) {
            stringBuffer.append("Content:\n");
            try {
                if (entity.isRepeatable()) {
                    stringBuffer.append(entityToString(entity, 1024));
                } else {
                    stringBuffer.append(" [DATA CAN NOT REPEAT]");
                }
            } catch (Exception unused) {
                stringBuffer.append(" [FAILED OUTPUT DATA]");
            }
            stringBuffer.append(StringUtils.LF);
        }
        return stringBuffer;
    }

    public static long getHeaderSize(Header[] headerArr) {
        long length = 0;
        if (headerArr != null && headerArr.length > 0) {
            for (Header header : headerArr) {
                length += (long) (header.toString().getBytes().length + 1);
            }
        }
        return length;
    }

    public static long getRequestSize(HttpRequest... httpRequestArr) {
        long requestSize = 0;
        if (httpRequestArr == null) {
            return 0L;
        }
        for (HttpRequest httpRequest : httpRequestArr) {
            requestSize += getRequestSize(httpRequest, true);
        }
        return requestSize;
    }

    public static long getRequestSize(HttpRequest httpRequest, boolean z) {
        if (httpRequest == null) {
            return 0L;
        }
        return ((long) (httpRequest.getRequestLine().toString().getBytes().length + 1)) + getHeaderSize(httpRequest.getAllHeaders());
    }

    public static long getResponseSize(HttpResponse... httpResponseArr) {
        long responseSize = 0;
        if (httpResponseArr == null) {
            return 0L;
        }
        for (HttpResponse httpResponse : httpResponseArr) {
            responseSize += getResponseSize(httpResponse, true);
        }
        return responseSize;
    }

    public static long getResponseSize(HttpResponse httpResponse, boolean z) {
        if (httpResponse == null) {
            return 0L;
        }
        return ((long) (httpResponse.getStatusLine().toString().getBytes().length + 1)) + getHeaderSize(httpResponse.getAllHeaders());
    }
}
