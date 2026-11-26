package skin.support.content.res;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import java.io.File;
import java.lang.ref.WeakReference;
import java.util.HashMap;
import java.util.Iterator;
import java.util.WeakHashMap;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import skin.support.SkinCompatManager;
import skin.support.utils.ImageUtils;
import skin.support.utils.SkinPreference;
import skin.support.utils.Slog;

/* JADX INFO: loaded from: classes6.dex */
public class SkinCompatUserThemeManager {
    private static SkinCompatUserThemeManager INSTANCE = new SkinCompatUserThemeManager();
    private static final String KEY_DRAWABLE_NAME = "drawableName";
    private static final String KEY_DRAWABLE_PATH_AND_ANGLE = "drawablePathAndAngle";
    private static final String KEY_TYPE = "type";
    private static final String KEY_TYPE_COLOR = "color";
    private static final String KEY_TYPE_DRAWABLE = "drawable";
    private static final String TAG = "SkinCompatUserThemeManager";
    private boolean mColorEmpty;
    private boolean mDrawableEmpty;
    private final HashMap<String, ColorState> mColorNameStateMap = new HashMap<>();
    private final Object mColorCacheLock = new Object();
    private final WeakHashMap<Integer, WeakReference<ColorStateList>> mColorCaches = new WeakHashMap<>();
    private final HashMap<String, String> mDrawablePathAndAngleMap = new HashMap<>();
    private final Object mDrawableCacheLock = new Object();
    private final WeakHashMap<Integer, WeakReference<Drawable>> mDrawableCaches = new WeakHashMap<>();

    private SkinCompatUserThemeManager() {
        try {
            startLoadFromSharedPreferences();
        } catch (JSONException e) {
            this.mColorNameStateMap.clear();
            this.mDrawablePathAndAngleMap.clear();
            if (Slog.DEBUG) {
                Slog.i(TAG, "startLoadFromSharedPreferences error: " + e);
            }
        }
    }

    private void startLoadFromSharedPreferences() throws JSONException {
        String userTheme = SkinPreference.getInstance().getUserTheme();
        if (TextUtils.isEmpty(userTheme)) {
            return;
        }
        JSONArray jSONArray = new JSONArray(userTheme);
        if (Slog.DEBUG) {
            Slog.i(TAG, "startLoadFromSharedPreferences: " + jSONArray.toString());
        }
        int length = jSONArray.length();
        for (int i = 0; i < length; i++) {
            JSONObject jSONObject = jSONArray.getJSONObject(i);
            if (jSONObject.has("type")) {
                String string = jSONObject.getString("type");
                if ("color".equals(string)) {
                    ColorState colorStateFromJSONObject = ColorState.fromJSONObject(jSONObject);
                    if (colorStateFromJSONObject != null) {
                        this.mColorNameStateMap.put(colorStateFromJSONObject.colorName, colorStateFromJSONObject);
                    }
                } else if (KEY_TYPE_DRAWABLE.equals(string)) {
                    String string2 = jSONObject.getString(KEY_DRAWABLE_NAME);
                    String string3 = jSONObject.getString(KEY_DRAWABLE_PATH_AND_ANGLE);
                    if (!TextUtils.isEmpty(string2) && !TextUtils.isEmpty(string3)) {
                        this.mDrawablePathAndAngleMap.put(string2, string3);
                    }
                }
            }
        }
        this.mColorEmpty = this.mColorNameStateMap.isEmpty();
        this.mDrawableEmpty = this.mDrawablePathAndAngleMap.isEmpty();
    }

    public void apply() {
        JSONArray jSONArray = new JSONArray();
        Iterator<String> it = this.mColorNameStateMap.keySet().iterator();
        while (it.hasNext()) {
            ColorState colorState = this.mColorNameStateMap.get(it.next());
            if (colorState != null) {
                try {
                    jSONArray.put(ColorState.toJSONObject(colorState).putOpt("type", "color"));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }
        for (String str : this.mDrawablePathAndAngleMap.keySet()) {
            try {
                jSONArray.put(new JSONObject().putOpt("type", KEY_TYPE_DRAWABLE).putOpt(KEY_DRAWABLE_NAME, str).putOpt(KEY_DRAWABLE_PATH_AND_ANGLE, this.mDrawablePathAndAngleMap.get(str)));
            } catch (JSONException e2) {
                e2.printStackTrace();
            }
        }
        if (Slog.DEBUG) {
            Slog.i(TAG, "Apply user theme: " + jSONArray.toString());
        }
        SkinPreference.getInstance().setUserTheme(jSONArray.toString()).commitEditor();
        SkinCompatManager.getInstance().notifyUpdateSkin();
    }

    public static SkinCompatUserThemeManager get() {
        return INSTANCE;
    }

    public void addColorState(int i, ColorState colorState) {
        String entryName = getEntryName(i, "color");
        if (TextUtils.isEmpty(entryName) || colorState == null) {
            return;
        }
        colorState.colorName = entryName;
        this.mColorNameStateMap.put(entryName, colorState);
        removeColorInCache(i);
        this.mColorEmpty = false;
    }

    public void addColorState(int i, String str) {
        if (ColorState.checkColorValid("colorDefault", str)) {
            String entryName = getEntryName(i, "color");
            if (TextUtils.isEmpty(entryName)) {
                return;
            }
            this.mColorNameStateMap.put(entryName, new ColorState(entryName, str));
            removeColorInCache(i);
            this.mColorEmpty = false;
        }
    }

    public void removeColorState(int i) {
        String entryName = getEntryName(i, "color");
        if (TextUtils.isEmpty(entryName)) {
            return;
        }
        this.mColorNameStateMap.remove(entryName);
        removeColorInCache(i);
        this.mColorEmpty = this.mColorNameStateMap.isEmpty();
    }

    void removeColorState(String str) {
        if (TextUtils.isEmpty(str)) {
            return;
        }
        this.mColorNameStateMap.remove(str);
        this.mColorEmpty = this.mColorNameStateMap.isEmpty();
    }

    public ColorState getColorState(String str) {
        return this.mColorNameStateMap.get(str);
    }

    public ColorState getColorState(int i) {
        String entryName = getEntryName(i, "color");
        if (TextUtils.isEmpty(entryName)) {
            return null;
        }
        return this.mColorNameStateMap.get(entryName);
    }

    public ColorStateList getColorStateList(int i) {
        ColorState colorState;
        ColorStateList cachedColor = getCachedColor(i);
        if (cachedColor == null) {
            String entryName = getEntryName(i, "color");
            if (!TextUtils.isEmpty(entryName) && (colorState = this.mColorNameStateMap.get(entryName)) != null && (cachedColor = colorState.parse()) != null) {
                addColorToCache(i, cachedColor);
            }
        }
        return cachedColor;
    }

    public void addDrawablePath(int i, String str) {
        if (checkPathValid(str)) {
            String entryName = getEntryName(i, KEY_TYPE_DRAWABLE);
            if (TextUtils.isEmpty(entryName)) {
                return;
            }
            this.mDrawablePathAndAngleMap.put(entryName, str + ":" + String.valueOf(ImageUtils.getImageRotateAngle(str)));
            removeDrawableInCache(i);
            this.mDrawableEmpty = false;
        }
    }

    public void addDrawablePath(int i, String str, int i2) {
        if (checkPathValid(str)) {
            String entryName = getEntryName(i, KEY_TYPE_DRAWABLE);
            if (TextUtils.isEmpty(entryName)) {
                return;
            }
            this.mDrawablePathAndAngleMap.put(entryName, str + ":" + String.valueOf(i2));
            removeDrawableInCache(i);
            this.mDrawableEmpty = false;
        }
    }

    public void removeDrawablePath(int i) {
        String entryName = getEntryName(i, KEY_TYPE_DRAWABLE);
        if (TextUtils.isEmpty(entryName)) {
            return;
        }
        this.mDrawablePathAndAngleMap.remove(entryName);
        removeDrawableInCache(i);
        this.mDrawableEmpty = this.mDrawablePathAndAngleMap.isEmpty();
    }

    public String getDrawablePath(String str) {
        String str2 = this.mDrawablePathAndAngleMap.get(str);
        if (!TextUtils.isEmpty(str2)) {
            return str2.split(":")[0];
        }
        return "";
    }

    public int getDrawableAngle(String str) {
        String str2 = this.mDrawablePathAndAngleMap.get(str);
        if (TextUtils.isEmpty(str2)) {
            return 0;
        }
        String[] strArrSplit = str2.split(":");
        if (strArrSplit.length == 2) {
            return Integer.valueOf(strArrSplit[1]).intValue();
        }
        return 0;
    }

    public Drawable getDrawable(int i) {
        Drawable cachedDrawable = getCachedDrawable(i);
        if (cachedDrawable == null) {
            String entryName = getEntryName(i, KEY_TYPE_DRAWABLE);
            if (!TextUtils.isEmpty(entryName)) {
                String str = this.mDrawablePathAndAngleMap.get(entryName);
                if (!TextUtils.isEmpty(str)) {
                    String[] strArrSplit = str.split(":");
                    String str2 = strArrSplit[0];
                    int iIntValue = strArrSplit.length == 2 ? Integer.valueOf(strArrSplit[1]).intValue() : 0;
                    if (checkPathValid(str2)) {
                        if (iIntValue == 0) {
                            cachedDrawable = Drawable.createFromPath(str2);
                        } else {
                            Matrix matrix = new Matrix();
                            matrix.postRotate(iIntValue);
                            Bitmap bitmapDecodeFile = BitmapFactory.decodeFile(str2);
                            cachedDrawable = new BitmapDrawable((Resources) null, Bitmap.createBitmap(bitmapDecodeFile, 0, 0, bitmapDecodeFile.getWidth(), bitmapDecodeFile.getHeight(), matrix, true));
                        }
                        if (cachedDrawable != null) {
                            addDrawableToCache(i, cachedDrawable);
                        }
                    }
                }
            }
        }
        return cachedDrawable;
    }

    public void clearColors() {
        this.mColorNameStateMap.clear();
        clearColorCaches();
        this.mColorEmpty = true;
        apply();
    }

    public void clearDrawables() {
        this.mDrawablePathAndAngleMap.clear();
        clearDrawableCaches();
        this.mDrawableEmpty = true;
        apply();
    }

    boolean isColorEmpty() {
        return this.mColorEmpty;
    }

    boolean isDrawableEmpty() {
        return this.mDrawableEmpty;
    }

    void clearCaches() {
        clearColorCaches();
        clearDrawableCaches();
    }

    private void clearColorCaches() {
        synchronized (this.mColorCacheLock) {
            this.mColorCaches.clear();
        }
    }

    private void clearDrawableCaches() {
        synchronized (this.mDrawableCacheLock) {
            this.mDrawableCaches.clear();
        }
    }

    private ColorStateList getCachedColor(int i) {
        synchronized (this.mColorCacheLock) {
            WeakReference<ColorStateList> weakReference = this.mColorCaches.get(Integer.valueOf(i));
            if (weakReference != null) {
                ColorStateList colorStateList = weakReference.get();
                if (colorStateList != null) {
                    return colorStateList;
                }
                this.mColorCaches.remove(Integer.valueOf(i));
            }
            return null;
        }
    }

    private void addColorToCache(int i, ColorStateList colorStateList) {
        if (colorStateList != null) {
            synchronized (this.mColorCacheLock) {
                this.mColorCaches.put(Integer.valueOf(i), new WeakReference<>(colorStateList));
            }
        }
    }

    private void removeColorInCache(int i) {
        synchronized (this.mColorCacheLock) {
            this.mColorCaches.remove(Integer.valueOf(i));
        }
    }

    private Drawable getCachedDrawable(int i) {
        synchronized (this.mDrawableCacheLock) {
            WeakReference<Drawable> weakReference = this.mDrawableCaches.get(Integer.valueOf(i));
            if (weakReference != null) {
                Drawable drawable = weakReference.get();
                if (drawable != null) {
                    return drawable;
                }
                this.mDrawableCaches.remove(Integer.valueOf(i));
            }
            return null;
        }
    }

    private void addDrawableToCache(int i, Drawable drawable) {
        if (drawable != null) {
            synchronized (this.mDrawableCacheLock) {
                this.mDrawableCaches.put(Integer.valueOf(i), new WeakReference<>(drawable));
            }
        }
    }

    private void removeDrawableInCache(int i) {
        synchronized (this.mDrawableCacheLock) {
            this.mDrawableCaches.remove(Integer.valueOf(i));
        }
    }

    private String getEntryName(int i, String str) {
        Context context = SkinCompatManager.getInstance().getContext();
        if (str.equalsIgnoreCase(context.getResources().getResourceTypeName(i))) {
            return context.getResources().getResourceEntryName(i);
        }
        return null;
    }

    private static boolean checkPathValid(String str) {
        boolean z = !TextUtils.isEmpty(str) && new File(str).exists();
        if (Slog.DEBUG && !z) {
            Slog.i(TAG, "Invalid drawable path : " + str);
        }
        return z;
    }
}
