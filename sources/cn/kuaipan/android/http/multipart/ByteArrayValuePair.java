package cn.kuaipan.android.http.multipart;

import android.text.TextUtils;
import java.util.Arrays;
import org.apache.http.NameValuePair;
import org.apache.http.util.LangUtils;

/* JADX INFO: loaded from: classes.dex */
public class ByteArrayValuePair implements NameValuePair, Cloneable {
    private final byte[] data;
    private final String filename;
    private final String name;

    public ByteArrayValuePair(String str, String str2, byte[] bArr) {
        this.name = str;
        this.filename = str2;
        this.data = bArr;
    }

    @Override // org.apache.http.NameValuePair
    public String getName() {
        return this.name;
    }

    @Override // org.apache.http.NameValuePair
    public String getValue() {
        return this.filename;
    }

    public byte[] getData() {
        return this.data;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder(this.name);
        if (this.filename != null && this.data != null) {
            sb.append("=File[name=");
            sb.append(this.filename);
            sb.append(", data=");
            sb.append(Arrays.toString(this.data));
            sb.append("]");
        }
        return sb.toString();
    }

    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (this == obj) {
            return true;
        }
        if (obj instanceof ByteArrayValuePair) {
            ByteArrayValuePair byteArrayValuePair = (ByteArrayValuePair) obj;
            if (TextUtils.equals(this.name, byteArrayValuePair.name) && TextUtils.equals(this.filename, byteArrayValuePair.filename) && Arrays.equals(this.data, byteArrayValuePair.data)) {
                return true;
            }
        }
        return false;
    }

    public int hashCode() {
        return LangUtils.hashCode(LangUtils.hashCode(LangUtils.hashCode(17, this.name), this.filename), Arrays.hashCode(this.data));
    }

    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }
}
