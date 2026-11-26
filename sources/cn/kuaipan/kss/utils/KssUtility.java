package cn.kuaipan.kss.utils;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import org.apache.http.HttpResponse;

/* JADX INFO: loaded from: classes.dex */
public class KssUtility {
    public static String getResponseStringBody(HttpResponse httpResponse) throws Throwable {
        BufferedReader bufferedReader;
        StringBuffer stringBuffer = new StringBuffer();
        BufferedReader bufferedReader2 = null;
        try {
            bufferedReader = new BufferedReader(new InputStreamReader(httpResponse.getEntity().getContent()));
            while (true) {
                try {
                    try {
                        String line = bufferedReader.readLine();
                        if (line != null) {
                            stringBuffer.append(line);
                        } else {
                            closeBufferedReader(bufferedReader);
                            return stringBuffer.toString();
                        }
                    } catch (Throwable th) {
                        th = th;
                        bufferedReader2 = bufferedReader;
                    }
                } catch (Exception e) {
                    e = e;
                    e.printStackTrace();
                    closeBufferedReader(bufferedReader);
                    return null;
                }
                th = th;
                bufferedReader2 = bufferedReader;
                closeBufferedReader(bufferedReader2);
                throw th;
            }
        } catch (Exception e2) {
            e = e2;
            bufferedReader = null;
        } catch (Throwable th2) {
            th = th2;
        }
    }

    public static void closeBufferedReader(BufferedReader bufferedReader) {
        if (bufferedReader != null) {
            try {
                bufferedReader.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static void closeInputStream(InputStream inputStream) {
        if (inputStream != null) {
            try {
                inputStream.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
