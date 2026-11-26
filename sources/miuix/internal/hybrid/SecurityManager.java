package miuix.internal.hybrid;

import android.content.Context;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import org.apache.commons.lang3.CharUtils;

/* JADX INFO: loaded from: classes5.dex */
public class SecurityManager {
    private static final String KEY_FILE_NAME = "hybrid_key.pem";
    private static String sPublicKey;
    private Config mConfig;
    private long mExpiredTime;
    private String mSign;
    private Boolean mValidSignature;

    public SecurityManager(Config config, Context context) {
        this.mConfig = config;
        if (config != null && config.getSecurity() != null) {
            this.mExpiredTime = config.getSecurity().getTimestamp();
            this.mSign = config.getSecurity().getSignature();
        }
        if (sPublicKey == null) {
            sPublicKey = getPublicKey(context);
        }
    }

    public boolean isExpired() {
        long j = this.mExpiredTime;
        return 0 < j && j < System.currentTimeMillis();
    }

    public boolean isValidSignature() {
        if (this.mValidSignature == null) {
            try {
                this.mValidSignature = Boolean.valueOf(isValidSignature(ConfigUtils.getRawConfig(this.mConfig), this.mSign));
            } catch (Exception unused) {
                this.mValidSignature = false;
            }
        }
        return this.mValidSignature.booleanValue();
    }

    private boolean isValidSignature(String str, String str2) throws Exception {
        return str2 != null && SignUtils.verify(str, SignUtils.getPublicKey(sPublicKey), str2);
    }

    private String getPublicKey(Context context) throws Throwable {
        InputStream inputStreamOpen;
        File file = new File(getHybridBaseFolder(context), KEY_FILE_NAME);
        BufferedReader bufferedReader = null;
        try {
            try {
                if (file.exists()) {
                    inputStreamOpen = new FileInputStream(file);
                } else {
                    inputStreamOpen = context.getResources().getAssets().open("keys/hybrid_key.pem");
                }
                BufferedReader bufferedReader2 = new BufferedReader(new InputStreamReader(inputStreamOpen));
                try {
                    String publicKey = readPublicKey(bufferedReader2);
                    try {
                        bufferedReader2.close();
                        return publicKey;
                    } catch (IOException e) {
                        e.printStackTrace();
                        return publicKey;
                    }
                } catch (IOException unused) {
                    throw new IllegalStateException("cannot read hybrid key.");
                } catch (Throwable th) {
                    th = th;
                    bufferedReader = bufferedReader2;
                    if (bufferedReader != null) {
                        try {
                            bufferedReader.close();
                        } catch (IOException e2) {
                            e2.printStackTrace();
                        }
                    }
                    throw th;
                }
            } catch (IOException unused2) {
            }
        } catch (Throwable th2) {
            th = th2;
        }
    }

    private String readPublicKey(BufferedReader bufferedReader) throws IOException {
        StringBuilder sb = new StringBuilder();
        while (true) {
            String line = bufferedReader.readLine();
            if (line != null) {
                if (!"".equals(line.trim()) && !line.startsWith("-----")) {
                    sb.append(line);
                    sb.append(CharUtils.CR);
                }
            } else {
                return sb.substring(0, sb.length() - 1);
            }
        }
    }

    private File getHybridBaseFolder(Context context) {
        return new File(context.getFilesDir(), "miuisdk");
    }
}
