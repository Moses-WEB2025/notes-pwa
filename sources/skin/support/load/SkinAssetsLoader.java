package skin.support.load;

import android.content.Context;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import skin.support.utils.SkinConstants;
import skin.support.utils.SkinFileUtils;

/* JADX INFO: loaded from: classes6.dex */
public class SkinAssetsLoader extends SkinSDCardLoader {
    @Override // skin.support.load.SkinSDCardLoader, skin.support.SkinCompatManager.SkinLoaderStrategy
    public String getTargetResourceEntryName(Context context, String str, int i) {
        return null;
    }

    @Override // skin.support.SkinCompatManager.SkinLoaderStrategy
    public int getType() {
        return 0;
    }

    @Override // skin.support.load.SkinSDCardLoader
    protected String getSkinPath(Context context, String str) {
        return copySkinFromAssets(context, str);
    }

    private String copySkinFromAssets(Context context, String str) {
        String absolutePath = new File(SkinFileUtils.getSkinDir(context), str).getAbsolutePath();
        try {
            InputStream inputStreamOpen = context.getAssets().open(SkinConstants.SKIN_DEPLOY_PATH + File.separator + str);
            FileOutputStream fileOutputStream = new FileOutputStream(absolutePath);
            byte[] bArr = new byte[1024];
            while (true) {
                int i = inputStreamOpen.read(bArr);
                if (i != -1) {
                    fileOutputStream.write(bArr, 0, i);
                } else {
                    fileOutputStream.close();
                    inputStreamOpen.close();
                    return absolutePath;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            return absolutePath;
        }
    }
}
