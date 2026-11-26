package skin.support.utils;

import android.media.ExifInterface;
import defpackage.R2;
import java.io.IOException;

/* JADX INFO: loaded from: classes6.dex */
public class ImageUtils {
    public static int getImageRotateAngle(String str) {
        ExifInterface exifInterface;
        try {
            exifInterface = new ExifInterface(str);
        } catch (IOException e) {
            e.printStackTrace();
            exifInterface = null;
        }
        if (exifInterface == null) {
            return 0;
        }
        int attributeInt = exifInterface.getAttributeInt(androidx.exifinterface.media.ExifInterface.TAG_ORIENTATION, 0);
        if (attributeInt == 3) {
            return R2.array.ai_tag_top_item_gradient_border_colors;
        }
        if (attributeInt == 6) {
            return 90;
        }
        if (attributeInt != 8) {
            return 0;
        }
        return R2.attr.actionBarPauseIcon;
    }
}
