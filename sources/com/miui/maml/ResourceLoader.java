package com.miui.maml;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;
import android.os.MemoryFile;
import android.text.TextUtils;
import android.util.Log;
import com.miui.maml.util.Utils;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Locale;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import net.lingala.zip4j.util.InternalZipConstants;
import org.w3c.dom.Element;
import org.xml.sax.SAXException;

/* JADX INFO: loaded from: classes3.dex */
public abstract class ResourceLoader {
    private static final String CONFIG_FILE_NAME = "config.xml";
    private static final String IMAGES_FOLDER_NAME = "images";
    private static final String LOG_TAG = "ResourceLoader";
    private static final String MANIFEST_FILE_NAME = "manifest.xml";
    protected String mLanguageCountrySuffix;
    protected String mLanguageSuffix;
    protected Locale mLocale;
    private String mThemeName;
    protected String mManifestName = MANIFEST_FILE_NAME;
    protected String mConfigName = CONFIG_FILE_NAME;

    public void finish() {
    }

    public File getExtraFile(String str) {
        return null;
    }

    public abstract InputStream getInputStream(String str, long[] jArr);

    public void init() {
    }

    public abstract boolean resourceExists(String str);

    public ResourceLoader setLocal(Locale locale) {
        if (locale != null) {
            this.mLanguageSuffix = locale.getLanguage();
            String string = locale.toString();
            this.mLanguageCountrySuffix = string;
            if (TextUtils.equals(this.mLanguageSuffix, string)) {
                this.mLanguageSuffix = null;
            }
        }
        this.mLocale = locale;
        return this;
    }

    public Locale getLocale() {
        return this.mLocale;
    }

    /* JADX WARN: Code duplicated, block: B:40:0x0034 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    public MemoryFile getFile(String str) {
        long[] jArr = new long[1];
        InputStream inputStream = getInputStream(str, jArr);
        if (inputStream == null) {
            return null;
        }
        try {
            try {
                byte[] bArr = new byte[65536];
                MemoryFile memoryFile = new MemoryFile(str, (int) jArr[0]);
                int i = 0;
                while (true) {
                    int i2 = inputStream.read(bArr, 0, 65536);
                    if (i2 <= 0) {
                        break;
                    }
                    memoryFile.writeBytes(bArr, 0, i, i2);
                    i += i2;
                }
                if (memoryFile.length() > 0) {
                    if (inputStream != null) {
                        try {
                            inputStream.close();
                        } catch (IOException unused) {
                        }
                    }
                    return memoryFile;
                }
                if (inputStream != null) {
                    try {
                        inputStream.close();
                    } catch (IOException unused2) {
                    }
                }
                return null;
            } catch (IOException e) {
                Log.e(LOG_TAG, e.toString());
                if (inputStream != null) {
                    inputStream.close();
                }
            } catch (OutOfMemoryError e2) {
                Log.e(LOG_TAG, e2.toString());
                if (inputStream != null) {
                    inputStream.close();
                }
            }
        } catch (Throwable th) {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException unused3) {
                }
            }
            throw th;
        }
    }

    /* JADX WARN: Code duplicated, block: B:50:0x00a4 A[DONT_GENERATE, EXC_TOP_SPLITTER, SYNTHETIC] */
    public ResourceManager.BitmapInfo getBitmapInfo(String str, BitmapFactory.Options options) {
        String pathForLanguage = getPathForLanguage(str, "images");
        if (pathForLanguage == null) {
            Log.d(LOG_TAG, "TRY AGAIN to get getPathForLanguage: " + str);
            pathForLanguage = getPathForLanguage(str, "images");
            if (pathForLanguage == null) {
                Log.e(LOG_TAG, "fail to get getPathForLanguage: " + str);
                return null;
            }
        }
        InputStream inputStream = getInputStream(pathForLanguage);
        if (inputStream == null) {
            Log.d(LOG_TAG, "TRY AGAIN to get InputStream: " + str);
            inputStream = getInputStream(pathForLanguage);
            if (inputStream == null) {
                Log.e(LOG_TAG, "fail to get InputStream: " + str);
                return null;
            }
        }
        try {
            Rect rect = new Rect();
            Bitmap bitmapDecodeStream = BitmapFactory.decodeStream(inputStream, rect, options);
            if (bitmapDecodeStream == null) {
                Log.d(LOG_TAG, "TRY AGAIN to decode bitmap: " + str);
                if (BitmapFactory.decodeStream(inputStream, rect, options) != null) {
                    return null;
                }
                Log.e(LOG_TAG, "fail to decode bitmap: " + str);
                return null;
            }
            return new ResourceManager.BitmapInfo(bitmapDecodeStream, rect);
        } catch (OutOfMemoryError e) {
            Log.e(LOG_TAG, e.toString());
        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException unused) {
                }
            }
        }
    }

    public Element getManifestRoot() {
        return getXmlRoot(this.mManifestName);
    }

    public Element getConfigRoot() {
        return getXmlRoot(this.mConfigName);
    }

    /* JADX WARN: Code duplicated, block: B:42:0x0035 A[DONT_GENERATE, EXC_TOP_SPLITTER, SYNTHETIC] */
    private Element getXmlRoot(String str) {
        InputStream inputStream = getInputStream(getPathForLanguage(str));
        try {
            if (inputStream == null) {
                Log.e(LOG_TAG, "getXmlRoot local inputStream is null");
                return null;
            }
            return DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(inputStream).getDocumentElement();
        } catch (OutOfMemoryError e) {
            Log.e(LOG_TAG, e.toString());
            return null;
        } catch (IOException e2) {
            Log.e(LOG_TAG, e2.toString());
            return null;
        } catch (Exception e3) {
            Log.e(LOG_TAG, e3.toString());
            return null;
        } catch (ParserConfigurationException e4) {
            Log.e(LOG_TAG, e4.toString());
            return null;
        } catch (SAXException e5) {
            Log.e(LOG_TAG, e5.toString());
            return null;
        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException unused) {
                }
            }
        }
    }

    /* JADX WARN: Code duplicated, block: B:13:0x002d A[PHI: r0
  0x002d: PHI (r0v4 java.lang.String) = (r0v3 java.lang.String), (r0v3 java.lang.String), (r0v6 java.lang.String) binds: [B:7:0x0016, B:9:0x001e, B:11:0x002a] A[DONT_GENERATE, DONT_INLINE]] */
    /* JADX WARN: Code duplicated, block: B:6:0x0015  */
    public String getPathForLanguage(String str) {
        String strAddFileNameSuffix;
        String str2;
        if (TextUtils.isEmpty(this.mLanguageCountrySuffix)) {
            strAddFileNameSuffix = null;
        } else {
            strAddFileNameSuffix = Utils.addFileNameSuffix(str, this.mLanguageCountrySuffix);
            if (!resourceExists(strAddFileNameSuffix)) {
                strAddFileNameSuffix = null;
            }
        }
        if (strAddFileNameSuffix == null && !TextUtils.isEmpty(this.mLanguageSuffix)) {
            strAddFileNameSuffix = Utils.addFileNameSuffix(str, this.mLanguageSuffix);
            str2 = resourceExists(strAddFileNameSuffix) ? strAddFileNameSuffix : null;
        }
        return str2 != null ? str2 : str;
    }

    private String getPathForLanguage(String str, String str2) {
        if (!TextUtils.isEmpty(this.mLanguageCountrySuffix)) {
            String str3 = str2 + "_" + this.mLanguageCountrySuffix + InternalZipConstants.ZIP_FILE_SEPARATOR + str;
            if (resourceExists(str3)) {
                return str3;
            }
        }
        if (!TextUtils.isEmpty(this.mLanguageSuffix)) {
            String str4 = str2 + "_" + this.mLanguageSuffix + InternalZipConstants.ZIP_FILE_SEPARATOR + str;
            if (resourceExists(str4)) {
                return str4;
            }
        }
        if (!TextUtils.isEmpty(str2)) {
            String str5 = str2 + InternalZipConstants.ZIP_FILE_SEPARATOR + str;
            if (resourceExists(str5)) {
                return str5;
            }
        }
        if (resourceExists(str)) {
            return str;
        }
        return null;
    }

    public final InputStream getInputStream(String str) {
        return getInputStream(str, null);
    }

    public String getID() {
        return "";
    }
}
