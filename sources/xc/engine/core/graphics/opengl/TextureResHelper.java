package xc.engine.core.graphics.opengl;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import xc.engine.R;

/* JADX INFO: loaded from: classes6.dex */
public class TextureResHelper {
    public static final int[] waterColorTexResIds = {R.drawable.brush_watercolor};
    public static final int[] waterColorBgTexResIds = {R.drawable.brush_watercolor_bg};
    public static final int[] pencilTexResIds = {R.drawable.brush_pencil};
    public static final int[] markTexResIds = {R.drawable.brush_mark};

    public static Bitmap getBitmapFromDrawable(Context context, int i) {
        if (context == null || i == 0) {
            Log.e("TextureResHelper", "Invalid parameters");
            return null;
        }
        try {
            Resources resources = context.getResources();
            if (resources == null) {
                Log.e("TextureResHelper", "Resources is null");
                return null;
            }
            return BitmapFactory.decodeResource(resources, i);
        } catch (Exception e) {
            Log.e("TextureResHelper", "Failed to decode bitmap", e);
            return null;
        }
    }
}
