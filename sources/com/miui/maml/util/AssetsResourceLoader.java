package com.miui.maml.util;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;
import com.miui.maml.ResourceLoader;
import java.io.IOException;
import java.io.InputStream;
import net.lingala.zip4j.util.InternalZipConstants;

/* JADX INFO: loaded from: classes3.dex */
public class AssetsResourceLoader extends ResourceLoader {
    private static final String LOG_TAG = "AssetsResourceLoader";
    private Context mContext;
    private String mResourcePath;

    public AssetsResourceLoader(Context context, String str) {
        this.mContext = context.getApplicationContext();
        this.mResourcePath = str;
    }

    @Override // com.miui.maml.ResourceLoader
    public boolean resourceExists(String str) {
        if (TextUtils.isEmpty(str)) {
            return false;
        }
        try {
            InputStream inputStreamOpen = this.mContext.getAssets().open(this.mResourcePath + InternalZipConstants.ZIP_FILE_SEPARATOR + str);
            if (inputStreamOpen == null) {
                return false;
            }
            try {
                inputStreamOpen.close();
                return true;
            } catch (Exception e) {
                e.printStackTrace();
                return false;
            }
        } catch (IOException unused) {
            Log.d(LOG_TAG, "resource " + str + " do not exists");
            return false;
        }
    }

    @Override // com.miui.maml.ResourceLoader
    public InputStream getInputStream(String str, long[] jArr) {
        InputStream inputStreamOpen = null;
        if (!TextUtils.isEmpty(str)) {
            try {
                inputStreamOpen = this.mContext.getAssets().open(this.mResourcePath + InternalZipConstants.ZIP_FILE_SEPARATOR + str);
                if (jArr != null && jArr.length > 0) {
                    jArr[0] = inputStreamOpen.available();
                }
                return inputStreamOpen;
            } catch (IOException unused) {
                Log.d(LOG_TAG, "resource " + str + " do not exists");
            }
        }
        return inputStreamOpen;
    }

    @Override // com.miui.maml.ResourceLoader
    public String getID() {
        return LOG_TAG + this.mResourcePath;
    }
}
