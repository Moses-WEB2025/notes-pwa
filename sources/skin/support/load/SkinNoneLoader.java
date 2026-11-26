package skin.support.load;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.drawable.Drawable;
import skin.support.SkinCompatManager;

/* JADX INFO: loaded from: classes6.dex */
public class SkinNoneLoader implements SkinCompatManager.SkinLoaderStrategy {
    @Override // skin.support.SkinCompatManager.SkinLoaderStrategy
    public ColorStateList getColor(Context context, String str, int i) {
        return null;
    }

    @Override // skin.support.SkinCompatManager.SkinLoaderStrategy
    public ColorStateList getColorStateList(Context context, String str, int i) {
        return null;
    }

    @Override // skin.support.SkinCompatManager.SkinLoaderStrategy
    public Drawable getDrawable(Context context, String str, int i) {
        return null;
    }

    @Override // skin.support.SkinCompatManager.SkinLoaderStrategy
    public int getType() {
        return -1;
    }

    @Override // skin.support.SkinCompatManager.SkinLoaderStrategy
    public String loadSkinInBackground(Context context, String str) {
        return "";
    }

    @Override // skin.support.SkinCompatManager.SkinLoaderStrategy
    public String getTargetResourceEntryName(Context context, String str, int i) {
        return "";
    }
}
