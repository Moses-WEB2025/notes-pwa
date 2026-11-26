package miui.app;

import android.app.Activity;
import android.app.ActivityTaskManager;
import android.app.IActivityTaskManager;
import android.content.Context;
import android.content.res.Configuration;
import android.graphics.Rect;
import android.os.IBinder;
import android.os.Parcel;
import android.os.Parcelable;
import android.provider.Settings;
import android.util.Log;
import android.util.MiuiMultiWindowUtils;
import android.util.Singleton;
import android.view.View;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

/* JADX INFO: loaded from: classes5.dex */
public class MiuiFreeFormManager {
    public static final int ACTION_FREEFORM_END_RESIZE = 7;
    public static final int ACTION_FREEFORM_PINED = 9;
    public static final int ACTION_FREEFORM_PINED_TO_MINIFREEFORM_PINED = 15;
    public static final int ACTION_FREEFORM_START_RESIZE = 6;
    public static final int ACTION_FREEFORM_TO_FULLSCREEN = 3;
    public static final int ACTION_FREEFORM_TO_MINIFREEFORM = 2;
    public static final int ACTION_FREEFORM_UNPINED = 11;
    public static final int ACTION_FREEFORM_UPDATE_CAPTION_VISIBILITY = 8;
    public static final int ACTION_FULLSCREEN_TO_FREEFORM = 0;
    public static final int ACTION_FULLSCREEN_TO_MINIFREEFORM = 1;
    public static final int ACTION_MINIFREEFORM_PINED = 10;
    public static final int ACTION_MINIFREEFORM_PINED_TO_FREEFORM_PINED = 14;
    public static final int ACTION_MINIFREEFORM_TO_FREEFORM = 4;
    public static final int ACTION_MINIFREEFORM_TO_FULLSCREEN = 5;
    public static final int ACTION_MINI_FREEFORM_UNPINED = 12;
    public static final int ACTION_REMOVE_FLOATING_PIN_WINDOW = 13;
    public static final int BACKGROUND_PIN = 2;
    public static final int FOREGROUND_PIN = 1;
    public static final int GESTURE_WINDOWING_MODE_FREEFORM = 0;
    public static final int GESTURE_WINDOWING_MODE_SMALL_FREEFORM = 1;
    public static final int GESTURE_WINDOWING_MODE_UNDEFINED = -1;
    private static final Singleton<IMiuiFreeFormManager> IMiuiFreeFormManagerSingleton = new Singleton<IMiuiFreeFormManager>() { // from class: miui.app.MiuiFreeFormManager.1
        /* JADX INFO: Access modifiers changed from: protected */
        public IMiuiFreeFormManager create() {
            return IMiuiFreeFormManager.Stub.asInterface((IBinder) MiuiMultiWindowUtils.invoke(MiuiMultiWindowUtils.callStaticObjectMethod(ActivityTaskManager.class, IActivityTaskManager.class, "getService", (Class[]) null, (Object[]) null), "getMiuiFreeFormManagerService", new Object[0]));
        }
    };
    private static final String TAG = "MiuiFreeFormManager";
    public static final int UNPIN = 0;

    private static IMiuiFreeFormManager getService() {
        try {
            return (IMiuiFreeFormManager) IMiuiFreeFormManagerSingleton.get();
        } catch (Error unused) {
            return null;
        }
    }

    public static List<MiuiFreeFormStackInfo> getAllFreeFormStackInfosOnDisplay(int i) {
        if (getMiuiFreeformVersion() == 2) {
            throw new UnsupportedOperationException("非MIUI13小窗版本,请用MIUI12适配方案进行适配");
        }
        try {
            return getService().getAllFreeFormStackInfosOnDisplay(i);
        } catch (Exception unused) {
            Log.d(TAG, " failed getAllFreeFormStackInfosOnDisplay displayId=" + i);
            return null;
        }
    }

    public static MiuiFreeFormStackInfo getFreeFormStackInfoByStackId(int i) {
        if (getMiuiFreeformVersion() == 2) {
            throw new UnsupportedOperationException("非MIUI13小窗版本,请用MIUI12适配方案进行适配");
        }
        try {
            return getService().getFreeFormStackInfoByStackId(i);
        } catch (Exception unused) {
            Log.d(TAG, " failed getFreeFormStackInfoByStackId stackId=" + i);
            return null;
        }
    }

    public static MiuiFreeFormStackInfo getFreeFormStackInfoByView(View view) {
        if (getMiuiFreeformVersion() == 2) {
            throw new UnsupportedOperationException("非MIUI13小窗版本,请用MIUI12适配方案进行适配");
        }
        try {
            IBinder windowToken = view.getWindowToken();
            if (windowToken != null) {
                return getService().getFreeFormStackInfoByWindow(windowToken);
            }
            Method declaredMethod = View.class.getDeclaredMethod("getAttachedActivity", new Class[0]);
            declaredMethod.setAccessible(true);
            Activity activity = (Activity) declaredMethod.invoke(view, new Object[0]);
            if (activity != null) {
                return getFreeFormStackInfoByActivity(activity);
            }
            return null;
        } catch (Exception unused) {
            Log.d(TAG, " failed getFreeFormStackInfo view=" + view);
            return null;
        }
    }

    public static MiuiFreeFormStackInfo getFreeFormStackInfoByActivity(Activity activity) {
        if (getMiuiFreeformVersion() == 2) {
            throw new UnsupportedOperationException("非MIUI13小窗版本,请用MIUI12适配方案进行适配");
        }
        try {
            Method declaredMethod = Activity.class.getDeclaredMethod("getActivityToken", new Class[0]);
            declaredMethod.setAccessible(true);
            IBinder iBinder = (IBinder) declaredMethod.invoke(activity, new Object[0]);
            if (iBinder != null) {
                return getService().getFreeFormStackInfoByActivity(iBinder);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        Log.d(TAG, " failed getFreeFormStackInfo view=" + activity);
        return null;
    }

    public static void registerFreeformCallback(IFreeformCallback iFreeformCallback) {
        if (getMiuiFreeformVersion() == 2) {
            throw new UnsupportedOperationException("非MIUI13小窗版本,请用MIUI12适配方案进行适配");
        }
        if (iFreeformCallback != null) {
            try {
                getService().registerFreeformCallback(iFreeformCallback);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static void unregisterFreeformCallback(IFreeformCallback iFreeformCallback) {
        if (getMiuiFreeformVersion() == 2) {
            throw new UnsupportedOperationException("非MIUI13小窗版本,请用MIUI12适配方案进行适配");
        }
        if (iFreeformCallback != null) {
            try {
                getService().unregisterFreeformCallback(iFreeformCallback);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static int getMaxMiuiFreeFormStackCountForFlashBack(String str, boolean z) {
        if (getService() == null) {
            return 0;
        }
        try {
            return getService().getMaxMiuiFreeFormStackCountForFlashBack(str, z);
        } catch (Exception unused) {
            Log.d(TAG, " failed getMaxMiuiFreeFormStackCountForFlashBack ");
            return 0;
        }
    }

    public static boolean isSupportPin() {
        if (getMiuiFreeformVersion() == 2) {
            throw new UnsupportedOperationException("非MIUI13小窗版本,请用MIUI12适配方案进行适配");
        }
        try {
            IMiuiFreeFormManager service = getService();
            if (service != null) {
                return service.isSupportPin();
            }
            return false;
        } catch (Exception unused) {
            Log.d(TAG, " failed isSuppoertPin");
            return false;
        }
    }

    public static void updatePinFloatingWindowPos(Rect rect, int i) {
        if (getMiuiFreeformVersion() == 2) {
            throw new UnsupportedOperationException("非MIUI13小窗版本,请用MIUI12适配方案进行适配");
        }
        try {
            IMiuiFreeFormManager service = getService();
            if (service != null) {
                service.updatePinFloatingWindowPos(rect, i);
            }
        } catch (Exception unused) {
            Log.d(TAG, " failed getFreeFormStackInfoByStackId taskId=" + i);
        }
    }

    public static boolean unPinFloatingWindow(Rect rect, int i, float f, float f2, boolean z) {
        int i2;
        if (getMiuiFreeformVersion() == 2) {
            throw new UnsupportedOperationException("非MIUI13小窗版本,请用MIUI12适配方案进行适配");
        }
        try {
            IMiuiFreeFormManager service = getService();
            if (service == null) {
                return false;
            }
            i2 = i;
            try {
                return service.unPinFloatingWindow(rect, i2, f, f2, z);
            } catch (Exception unused) {
                Log.d(TAG, " failed getFreeFormStackInfoByStackId stackId=" + i2);
                return false;
            }
        } catch (Exception unused2) {
            i2 = i;
        }
    }

    public static void hidePinFloatingWindow(int i) {
        if (getMiuiFreeformVersion() == 2) {
            throw new UnsupportedOperationException("非MIUI13小窗版本,请用MIUI12适配方案进行适配");
        }
        try {
            IMiuiFreeFormManager service = getService();
            if (service != null) {
                service.hidePinFloatingWindow(i);
            }
        } catch (Exception unused) {
            Log.d(TAG, " failed hidePinFloatingWindow stackId=" + i);
        }
    }

    public static List<MiuiFreeFormStackInfo> getAllPinedFreeFormStackInfosOnDisplay(int i) {
        if (getMiuiFreeformVersion() == 2) {
            throw new UnsupportedOperationException("非MIUI13小窗版本,请用MIUI12适配方案进行适配");
        }
        try {
            IMiuiFreeFormManager service = getService();
            if (service != null) {
                return service.getAllPinedFreeFormStackInfosOnDisplay(i);
            }
        } catch (Exception unused) {
            Log.d(TAG, " failed getAllFreeFormStackInfosOnDisplay displayId=" + i);
        }
        return new ArrayList();
    }

    public static int getMiuiFreeformVersion() {
        return getService() != null ? 3 : 2;
    }

    public static int getMiuiFreeformStackShowState(Context context) {
        if (getMiuiFreeformVersion() != 2) {
            throw new UnsupportedOperationException("非MIUI12小窗版本,请用MIUI13适配方案进行适配");
        }
        return Settings.Secure.getInt(context.getContentResolver(), "freeform_window_state", -1);
    }

    public static String getMiuiFreeformStackPackageName(Context context) {
        if (getMiuiFreeformVersion() != 2) {
            throw new UnsupportedOperationException("非MIUI12小窗版本,请用MIUI13适配方案进行适配");
        }
        return Settings.Secure.getString(context.getContentResolver(), "freeform_package_name");
    }

    public static final class MiuiFreeFormStackInfo implements Parcelable {
        public static final Parcelable.Creator<MiuiFreeFormStackInfo> CREATOR = new Parcelable.Creator<MiuiFreeFormStackInfo>() { // from class: miui.app.MiuiFreeFormManager.MiuiFreeFormStackInfo.1
            @Override // android.os.Parcelable.Creator
            public MiuiFreeFormStackInfo createFromParcel(Parcel parcel) {
                return new MiuiFreeFormStackInfo(parcel);
            }

            @Override // android.os.Parcelable.Creator
            public MiuiFreeFormStackInfo[] newArray(int i) {
                return new MiuiFreeFormStackInfo[i];
            }
        };
        public Rect bounds;
        public Configuration configuration;
        public int displayId;
        public float freeFormScale;
        public float freeformCornerRadius;
        public boolean inPinMode;
        public String packageName;
        public Rect pinFloatingWindowPos;
        public Rect smallWindowBounds;
        public int stackId;
        public int userId;
        public float windowRoundCorner;
        public float windowScaleX;
        public float windowScaleY;
        public int windowState;

        @Override // android.os.Parcelable
        public int describeContents() {
            return 0;
        }

        @Override // android.os.Parcelable
        public void writeToParcel(Parcel parcel, int i) {
            parcel.writeInt(this.stackId);
            parcel.writeInt(this.bounds.left);
            parcel.writeInt(this.bounds.top);
            parcel.writeInt(this.bounds.right);
            parcel.writeInt(this.bounds.bottom);
            parcel.writeInt(this.displayId);
            parcel.writeInt(this.userId);
            parcel.writeInt(this.windowState);
            parcel.writeFloat(this.freeFormScale);
            parcel.writeString(this.packageName);
            this.configuration.writeToParcel(parcel, i);
            parcel.writeInt(this.smallWindowBounds.left);
            parcel.writeInt(this.smallWindowBounds.top);
            parcel.writeInt(this.smallWindowBounds.right);
            parcel.writeInt(this.smallWindowBounds.bottom);
            parcel.writeBoolean(this.inPinMode);
            parcel.writeFloat(this.windowScaleX);
            parcel.writeFloat(this.windowScaleY);
            parcel.writeFloat(this.windowRoundCorner);
            parcel.writeInt(this.pinFloatingWindowPos.left);
            parcel.writeInt(this.pinFloatingWindowPos.top);
            parcel.writeInt(this.pinFloatingWindowPos.right);
            parcel.writeInt(this.pinFloatingWindowPos.bottom);
            parcel.writeFloat(this.freeformCornerRadius);
        }

        public void readFromParcel(Parcel parcel) {
            this.stackId = parcel.readInt();
            this.bounds = new Rect(parcel.readInt(), parcel.readInt(), parcel.readInt(), parcel.readInt());
            this.displayId = parcel.readInt();
            this.userId = parcel.readInt();
            this.windowState = parcel.readInt();
            this.freeFormScale = parcel.readFloat();
            this.packageName = parcel.readString();
            this.configuration.readFromParcel(parcel);
            this.smallWindowBounds = new Rect(parcel.readInt(), parcel.readInt(), parcel.readInt(), parcel.readInt());
            this.inPinMode = parcel.readBoolean();
            this.windowScaleX = parcel.readFloat();
            this.windowScaleY = parcel.readFloat();
            this.windowRoundCorner = parcel.readFloat();
            this.pinFloatingWindowPos = new Rect(parcel.readInt(), parcel.readInt(), parcel.readInt(), parcel.readInt());
            this.freeformCornerRadius = parcel.readFloat();
        }

        public MiuiFreeFormStackInfo() {
            this.bounds = new Rect();
            this.configuration = new Configuration();
            this.smallWindowBounds = new Rect();
            this.pinFloatingWindowPos = new Rect();
        }

        private MiuiFreeFormStackInfo(Parcel parcel) {
            this.bounds = new Rect();
            this.configuration = new Configuration();
            this.smallWindowBounds = new Rect();
            this.pinFloatingWindowPos = new Rect();
            readFromParcel(parcel);
        }

        public String toString(String str) {
            StringBuilder sb = new StringBuilder(256);
            sb.append(str);
            sb.append("Stack id=");
            sb.append(this.stackId);
            sb.append(" bounds=");
            sb.append(this.bounds.toShortString());
            sb.append(" displayId=");
            sb.append(this.displayId);
            sb.append(" userId=");
            sb.append(this.userId);
            sb.append(" windowState=");
            sb.append(this.windowState);
            sb.append(" freeFormScale=");
            sb.append(this.freeFormScale);
            sb.append(" packageName=");
            sb.append(this.packageName);
            if (isInMiniFreeFormMode()) {
                sb.append(" smallWindowBounds=");
                sb.append(this.smallWindowBounds.toShortString());
            }
            sb.append(" configuration=");
            sb.append(this.configuration);
            sb.append(" inPinMode=");
            sb.append(this.inPinMode);
            sb.append(" windowScaleX=");
            sb.append(this.windowScaleX);
            sb.append(" windowScaleY=");
            sb.append(this.windowScaleY);
            sb.append(" windowRoundCorner=");
            sb.append(this.windowRoundCorner);
            sb.append(" pinFloatingWindowPos=");
            sb.append(this.pinFloatingWindowPos);
            sb.append("\n freeformCornerRadius=");
            sb.append(this.freeformCornerRadius);
            return sb.toString();
        }

        public boolean isInMiniFreeFormMode() {
            return this.windowState == 1;
        }

        public boolean isInFreeFormMode() {
            return this.windowState == 0;
        }

        public String toString() {
            return toString("");
        }
    }

    public static String actionToString(int i) {
        switch (i) {
            case 0:
                return "ACTION_FULLSCREEN_TO_FREEFORM";
            case 1:
                return "ACTION_FULLSCREEN_TO_MINIFREEFORM";
            case 2:
                return "ACTION_FREEFORM_TO_MINIFREEFORM";
            case 3:
                return "ACTION_FREEFORM_TO_FULLSCREEN";
            case 4:
                return "ACTION_MINIFREEFORM_TO_FREEFORM";
            case 5:
                return "ACTION_MINIFREEFORM_TO_FULLSCREEN";
            case 6:
                return "ACTION_FREEFORM_START_RESIZE";
            case 7:
                return "ACTION_FREEFORM_END_RESIZE";
            case 8:
                return "ACTION_FREEFORM_UPDATE_CAPTION_VISIBILITY";
            case 9:
                return "ACTION_FREEFORM_PINED";
            case 10:
                return "ACTION_MINIFREEFORM_PINED";
            case 11:
                return "ACTION_FREEFORM_UNPINED";
            case 12:
                return "ACTION_MINI_FREEFORM_UNPINED";
            case 13:
                return "ACTION_REMOVE_FLOATING_PIN_WINDOW";
            case 14:
                return "ACTION_MINIFREEFORM_PINED_TO_FREEFORM_PINED";
            case 15:
                return "ACTION_FREEFORM_PINED_TO_MINIFREEFORM_PINED";
            default:
                return null;
        }
    }
}
