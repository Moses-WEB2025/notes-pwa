package cn.kuaipan.android.kss.upload;

import android.text.TextUtils;
import android.util.Log;
import cn.kuaipan.android.exception.ErrorCode;
import cn.kuaipan.android.exception.KscException;
import cn.kuaipan.android.exception.KscRuntimeException;
import cn.kuaipan.android.kss.KssDef;
import cn.kuaipan.android.kss.TransferStep;
import cn.kuaipan.android.utils.Base64;
import cn.kuaipan.android.utils.Encode;
import com.xiaomi.e2ee.E2EEException;
import com.xiaomi.e2ee.utils.CipherUtils;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import javax.crypto.Cipher;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/* JADX INFO: loaded from: classes.dex */
public class UploadFileInfo implements KssDef {
    private static final String LOG_TAG = "UploadFileInfo";
    private final ArrayList<BlockInfo> mBlockInfos = new ArrayList<>();
    private String mEncryptedSha1;
    private String mSha1;

    public static class BlockInfo {
        public final String encryptedSha1;
        public final String md5;
        public final String sha1;
        public final int size;

        private BlockInfo(String str, String str2, String str3, int i) {
            this.sha1 = str;
            this.encryptedSha1 = str2;
            this.md5 = str3;
            this.size = i;
        }
    }

    public static UploadFileInfo getFileInfo(File file) throws InterruptedException, KscException {
        try {
            return getFileInfoInner(new FileInputStream(file), file.getPath(), UploadEncryptInfo.Factory.create(false));
        } catch (E2EEException e) {
            throw KscException.newException(e, "getFileInfo", TransferStep.UPLOAD_GET_FILE_INFO);
        } catch (IOException e2) {
            throw KscException.newException(e2, null, TransferStep.UPLOAD_GET_FILE_INFO);
        }
    }

    public static UploadFileInfo getFileInfo(KssUploadFile kssUploadFile, UploadEncryptInfo uploadEncryptInfo) throws InterruptedException, KscException {
        try {
            return getFileInfoInner(kssUploadFile.getInputStream(), kssUploadFile.filePath, uploadEncryptInfo);
        } catch (IOException e) {
            throw KscException.newException(e, null, TransferStep.UPLOAD_GET_FILE_INFO);
        }
    }

    private static UploadFileInfo getFileInfoInner(InputStream inputStream, String str, UploadEncryptInfo uploadEncryptInfo) throws InterruptedException, KscException {
        boolean zIsNeedEncrypt = uploadEncryptInfo.isNeedEncrypt();
        try {
            try {
                try {
                    UploadFileInfo uploadFileInfo = new UploadFileInfo();
                    MessageDigest messageDigest = MessageDigest.getInstance("SHA1");
                    MessageDigest messageDigest2 = MessageDigest.getInstance("SHA1");
                    MessageDigest messageDigest3 = MessageDigest.getInstance("MD5");
                    MessageDigest messageDigest4 = MessageDigest.getInstance("SHA1");
                    MessageDigest messageDigest5 = MessageDigest.getInstance("SHA1");
                    boolean z = true;
                    Cipher decodeFileCipher = zIsNeedEncrypt ? CipherUtils.getDecodeFileCipher(uploadEncryptInfo.getRecordKey(), uploadEncryptInfo.getRecordIV(), 1) : null;
                    byte[] bArr = new byte[16384];
                    byte[] bArrUpdate = new byte[16384];
                    InputStream inputStream2 = inputStream;
                    int i = 1;
                    long j = 0;
                    while (true) {
                        int i2 = inputStream2.read(bArr);
                        if (i2 < 0) {
                            break;
                        }
                        UploadFileInfo uploadFileInfo2 = uploadFileInfo;
                        long j2 = j + ((long) i2);
                        z = z;
                        messageDigest.update(bArr, 0, i2);
                        if (zIsNeedEncrypt) {
                            bArrUpdate = decodeFileCipher.update(bArr);
                        }
                        messageDigest4.update(bArrUpdate, 0, i2);
                        decodeFileCipher = decodeFileCipher;
                        long j3 = ((long) i) * KssDef.BLOCKSIZE;
                        if (j2 < j3) {
                            messageDigest2.update(bArr, 0, i2);
                            messageDigest5.update(bArrUpdate, 0, i2);
                            messageDigest3.update(bArr, 0, i2);
                            j = j2;
                            uploadFileInfo = uploadFileInfo2;
                        } else {
                            int i3 = i2 - ((int) (j2 - j3));
                            int i4 = i + 1;
                            boolean z2 = zIsNeedEncrypt;
                            Log.d(LOG_TAG, "blockoffset: " + i3 + " len: " + i2 + " pos: " + j2 + " blockIndex" + i4 + bArr + " blockOffset > input.length: " + (((long) i3) > ((long) 16384) ? z : false));
                            messageDigest2.update(bArr, 0, i3);
                            messageDigest5.update(bArrUpdate, 0, i3);
                            messageDigest3.update(bArr, 0, i3);
                            byte[] bArr2 = bArrUpdate;
                            uploadFileInfo = uploadFileInfo2;
                            byte[] bArr3 = bArr;
                            uploadFileInfo.appendBlock(Encode.byteArrayToHexString(messageDigest2.digest()), z2 ? Encode.byteArrayToHexString(messageDigest5.digest()) : null, Encode.byteArrayToHexString(messageDigest3.digest()), KssDef.BLOCKSIZE);
                            if (i2 > i3) {
                                int i5 = i2 - i3;
                                messageDigest2.update(bArr3, i3, i5);
                                messageDigest5.update(bArr2, i3, i5);
                                messageDigest3.update(bArr3, i3, i5);
                            }
                            inputStream2 = inputStream;
                            bArr = bArr3;
                            i = i4;
                            bArrUpdate = bArr2;
                            zIsNeedEncrypt = z2;
                            j = j2;
                        }
                    }
                    boolean z3 = zIsNeedEncrypt;
                    long j4 = ((long) i) * KssDef.BLOCKSIZE;
                    if (j4 > j && j4 < j + KssDef.BLOCKSIZE) {
                        uploadFileInfo.appendBlock(Encode.byteArrayToHexString(messageDigest2.digest()), z3 ? Encode.byteArrayToHexString(messageDigest5.digest()) : null, Encode.byteArrayToHexString(messageDigest3.digest()), j - (((long) (i - 1)) * KssDef.BLOCKSIZE));
                    } else if (j == 0) {
                        throw new KscRuntimeException(ErrorCode.INVALID_PARAM, str + " read error.");
                    }
                    if (z3) {
                        String strByteArrayToHexString = Encode.byteArrayToHexString(messageDigest4.digest());
                        uploadEncryptInfo.setEncryptSha1(strByteArrayToHexString);
                        uploadFileInfo.setEncryptedSha1(strByteArrayToHexString);
                    }
                    uploadFileInfo.setSha1(Encode.byteArrayToHexString(messageDigest.digest()));
                    try {
                        inputStream.close();
                    } catch (Throwable unused) {
                    }
                    return uploadFileInfo;
                } catch (IOException e) {
                    throw KscException.newException(e, null, TransferStep.UPLOAD_GET_FILE_INFO);
                } catch (NoSuchAlgorithmException e2) {
                    throw new KscRuntimeException(ErrorCode.FRAMEWORK_UNSUPPORT, e2);
                }
            } catch (E2EEException e3) {
                throw KscException.newException(e3, "meet e2ee exception when get file info", TransferStep.UPLOAD_GET_FILE_INFO);
            }
        } catch (Throwable th) {
            try {
                inputStream.close();
            } catch (Throwable unused2) {
            }
            throw th;
        }
    }

    private static String getRecordIV(String str) throws NoSuchAlgorithmException {
        MessageDigest messageDigest = MessageDigest.getInstance("SHA1");
        messageDigest.update(str.getBytes());
        byte[] bArr = new byte[16];
        System.arraycopy(messageDigest.digest(), 0, bArr, 0, 16);
        return Base64.encodeToString(bArr, 11);
    }

    private UploadFileInfo() {
    }

    public UploadFileInfo(String str) {
        UploadFileInfo uploadFileInfo;
        try {
            JSONObject jSONObject = new JSONObject(str);
            this.mSha1 = jSONObject.optString("sha1");
            this.mEncryptedSha1 = jSONObject.optString("encryptedSha1");
            JSONArray jSONArrayOptJSONArray = jSONObject.optJSONArray("block_infos");
            if (jSONArrayOptJSONArray == null) {
                return;
            }
            int length = jSONArrayOptJSONArray.length();
            int i = 0;
            while (i < length) {
                JSONObject jSONObjectOptJSONObject = jSONArrayOptJSONArray.optJSONObject(i);
                String strOptString = jSONObjectOptJSONObject == null ? null : jSONObjectOptJSONObject.optString("sha1");
                String strOptString2 = jSONObjectOptJSONObject == null ? null : jSONObjectOptJSONObject.optString("encryptedSha1");
                String strOptString3 = jSONObjectOptJSONObject != null ? jSONObjectOptJSONObject.optString("md5") : null;
                int iOptInt = -1;
                if (jSONObjectOptJSONObject != null) {
                    iOptInt = jSONObjectOptJSONObject.optInt("size", -1);
                }
                if (TextUtils.isEmpty(strOptString) || TextUtils.isEmpty(strOptString3) || iOptInt < 0) {
                    uploadFileInfo = this;
                } else {
                    uploadFileInfo = this;
                    uploadFileInfo.appendBlock(strOptString, strOptString2, strOptString3, iOptInt);
                }
                i++;
                this = uploadFileInfo;
            }
        } catch (JSONException e) {
            Log.w(LOG_TAG, "Failed parser UploadFileInfo from a String. The String:" + str, e);
        }
    }

    private JSONArray getBlockInfos() {
        try {
            JSONArray jSONArray = new JSONArray();
            for (BlockInfo blockInfo : this.mBlockInfos) {
                JSONObject jSONObject = new JSONObject();
                jSONObject.put("sha1", blockInfo.sha1);
                jSONObject.put("encryptedSha1", blockInfo.encryptedSha1);
                jSONObject.put("md5", blockInfo.md5);
                jSONObject.put("size", blockInfo.size);
                jSONArray.put(jSONObject);
            }
            return jSONArray;
        } catch (Throwable unused) {
            Log.w(LOG_TAG, "Failed generate Json String for UploadRequestInfo");
            return null;
        }
    }

    void setEncryptedSha1(String str) {
        this.mEncryptedSha1 = str;
    }

    public String getEncryptedSha1() {
        return this.mEncryptedSha1;
    }

    void setSha1(String str) {
        this.mSha1 = str;
    }

    public String getSha1() {
        return this.mSha1;
    }

    void appendBlock(String str, String str2, String str3, long j) {
        this.mBlockInfos.add(new BlockInfo(str, str2, str3, (int) j));
    }

    public BlockInfo getBlockInfo(int i) {
        if (i >= this.mBlockInfos.size()) {
            return null;
        }
        return this.mBlockInfos.get(i);
    }

    public String getKssString() {
        JSONArray blockInfos = getBlockInfos();
        if (blockInfos == null) {
            return null;
        }
        JSONObject jSONObject = new JSONObject();
        try {
            jSONObject.put("block_infos", blockInfos);
            return jSONObject.toString();
        } catch (Throwable unused) {
            return String.valueOf(jSONObject);
        }
    }

    public String toString() {
        JSONObject jSONObject = new JSONObject();
        try {
            jSONObject.put("block_infos", getBlockInfos());
            jSONObject.put("sha1", this.mSha1);
            jSONObject.put("encryptedSha1", this.mEncryptedSha1);
        } catch (Throwable unused) {
        }
        return String.valueOf(jSONObject);
    }
}
