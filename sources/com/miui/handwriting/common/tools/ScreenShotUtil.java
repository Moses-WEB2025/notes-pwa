package com.miui.handwriting.common.tools;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Rect;
import android.hardware.HardwareBuffer;
import android.hardware.display.DisplayManager;
import android.hardware.display.VirtualDisplay;
import android.media.Image;
import android.media.ImageReader;
import android.os.Build;
import android.os.IBinder;
import android.os.ServiceManager;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.IWindowManager;
import android.view.WindowManager;
import com.miui.libhandwritingpage.utils.MetaReflectUtil;
import java.lang.reflect.Method;
import java.nio.ByteBuffer;

/* JADX INFO: loaded from: classes3.dex */
public class ScreenShotUtil {
    public static final String TAG = "ScreenShotUtil";

    public static Bitmap getScreenshot(Context context) {
        WindowManager windowManager = (WindowManager) context.getSystemService("window");
        if (Build.VERSION.SDK_INT > 33) {
            return getScreenshotU(context);
        }
        if (Build.VERSION.SDK_INT >= 31) {
            return getScreenShotS(context);
        }
        try {
            Class<?> cls = Class.forName("android.view.SurfaceControl");
            return (Bitmap) cls.getMethod("screenshot", Rect.class, Integer.TYPE, Integer.TYPE, Integer.TYPE).invoke(cls, new Rect(), 0, 0, Integer.valueOf(windowManager.getDefaultDisplay().getRotation()));
        } catch (Throwable th) {
            Log.e(TAG, th.toString());
            return null;
        }
    }

    /* JADX WARN: Code duplicated, block: B:101:0x00b4 A[DONT_GENERATE, EXC_TOP_SPLITTER, PHI: r10
  0x00b4: PHI (r10v5 android.media.ImageReader) = (r10v3 android.media.ImageReader), (r10v9 android.media.ImageReader) binds: [B:60:0x00dc, B:41:0x00b2] A[DONT_GENERATE, DONT_INLINE], SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:87:0x00ca A[DONT_GENERATE, EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:95:0x00d4 A[DONT_GENERATE, EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Multi-variable type inference failed */
    public static Bitmap getScreenshot2(Context context) {
        ImageReader imageReaderNewInstance;
        VirtualDisplay virtualDisplayCreateVirtualDisplay;
        Image image;
        try {
            DisplayMetrics displayMetrics = new DisplayMetrics();
            ((WindowManager) context.getSystemService("window")).getDefaultDisplay().getRealMetrics(displayMetrics);
            DisplayManager displayManager = (DisplayManager) context.getSystemService("display");
            imageReaderNewInstance = ImageReader.newInstance(displayMetrics.widthPixels, displayMetrics.heightPixels, 1, 1);
            try {
                virtualDisplayCreateVirtualDisplay = displayManager.createVirtualDisplay("capture_screen", displayMetrics.widthPixels, displayMetrics.heightPixels, displayMetrics.densityDpi, imageReaderNewInstance.getSurface(), 19);
                int i = 0;
                Image imageAcquireLatestImage = null;
                while (imageAcquireLatestImage == null) {
                    try {
                        Thread.sleep(50L);
                        imageAcquireLatestImage = imageReaderNewInstance.acquireLatestImage();
                        i++;
                        if (i > 10) {
                            break;
                        }
                    } catch (Throwable th) {
                        th = th;
                        image = imageAcquireLatestImage;
                        try {
                            Log.e(TAG, th.toString());
                        } finally {
                            if (image != 0) {
                                try {
                                    image.close();
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }
                            if (virtualDisplayCreateVirtualDisplay != null) {
                                try {
                                    virtualDisplayCreateVirtualDisplay.release();
                                } catch (Exception e2) {
                                    e2.printStackTrace();
                                }
                            }
                            if (imageReaderNewInstance != null) {
                                try {
                                    imageReaderNewInstance.close();
                                } catch (Exception e3) {
                                    e3.printStackTrace();
                                }
                            }
                        }
                    }
                }
                if (imageAcquireLatestImage == null) {
                    if (imageAcquireLatestImage != null) {
                        try {
                            imageAcquireLatestImage.close();
                        } catch (Exception e4) {
                            e4.printStackTrace();
                        }
                    }
                    if (virtualDisplayCreateVirtualDisplay != null) {
                        try {
                            virtualDisplayCreateVirtualDisplay.release();
                        } catch (Exception e5) {
                            e5.printStackTrace();
                        }
                    }
                    if (imageReaderNewInstance != null) {
                    }
                    return null;
                }
                int width = imageAcquireLatestImage.getWidth();
                int height = imageAcquireLatestImage.getHeight();
                Image.Plane[] planes = imageAcquireLatestImage.getPlanes();
                ByteBuffer buffer = planes[0].getBuffer();
                int pixelStride = planes[0].getPixelStride();
                Bitmap bitmapCreateBitmap = Bitmap.createBitmap(width + ((planes[0].getRowStride() - (pixelStride * width)) / pixelStride), height, Bitmap.Config.ARGB_8888);
                bitmapCreateBitmap.copyPixelsFromBuffer(buffer);
                if (imageAcquireLatestImage != null) {
                    try {
                        imageAcquireLatestImage.close();
                    } catch (Exception e6) {
                        e6.printStackTrace();
                    }
                }
                if (virtualDisplayCreateVirtualDisplay != null) {
                    try {
                        virtualDisplayCreateVirtualDisplay.release();
                    } catch (Exception e7) {
                        e7.printStackTrace();
                    }
                }
                if (imageReaderNewInstance != null) {
                    try {
                        imageReaderNewInstance.close();
                    } catch (Exception e8) {
                        e8.printStackTrace();
                    }
                }
                return bitmapCreateBitmap;
            } catch (Throwable th2) {
                th = th2;
                virtualDisplayCreateVirtualDisplay = null;
                image = virtualDisplayCreateVirtualDisplay;
                Log.e(TAG, th.toString());
                return null;
            }
        } catch (Throwable th3) {
            th = th3;
            imageReaderNewInstance = null;
            virtualDisplayCreateVirtualDisplay = null;
        }
    }

    public static Bitmap getScreenshotU(Context context) {
        HardwareBuffer hardwareBuffer;
        Bitmap bitmap = null;
        try {
            Display display = ((DisplayManager) context.getSystemService("display")).getDisplay(0);
            DisplayMetrics realScreenSize = com.miui.libhandwritingpage.utils.UIUtils.getRealScreenSize(context);
            Rect rect = new Rect(0, 0, realScreenSize.widthPixels, realScreenSize.heightPixels);
            if (!Class.forName("android.view.DisplayAddress$Physical").isInstance(Class.forName("android.view.Display").getDeclaredMethod("getAddress", new Class[0]).invoke(display, new Object[0]))) {
                Log.e(TAG, "Skipping Screenshot - Default display does not have a physical address: " + display);
                return null;
            }
            Class<?> cls = Class.forName("android.window.ScreenCapture$CaptureArgs$Builder");
            Class<?> cls2 = Class.forName("android.window.ScreenCapture$CaptureArgs");
            Class<?> cls3 = Class.forName("android.window.ScreenCapture$ScreenCaptureListener");
            Object objCallObjectMethod = MetaReflectUtil.callObjectMethod(MetaReflectUtil.callObjectMethod(cls.getConstructor(new Class[0]).newInstance(new Object[0]), "setSourceCrop", new Class[]{Rect.class}, rect), "build", null, new Object[0]);
            Object objCallStaticObjectMethod = MetaReflectUtil.callStaticObjectMethod(Class.forName("android.window.ScreenCapture"), null, "createSyncCaptureListener", null, new Object[0]);
            MetaReflectUtil.callObjectMethod(IWindowManager.Stub.asInterface(ServiceManager.getService("window")), "captureDisplay", new Class[]{Integer.TYPE, cls2, cls3}, 0, objCallObjectMethod, objCallStaticObjectMethod);
            Object objCallObjectMethod2 = MetaReflectUtil.callObjectMethod(objCallStaticObjectMethod, "getBuffer", null, new Object[0]);
            Bitmap bitmap2 = objCallObjectMethod2 == null ? null : (Bitmap) MetaReflectUtil.callObjectMethod(objCallObjectMethod2, "asBitmap", null, new Object[0]);
            if (bitmap2 != null) {
                try {
                    Bitmap bitmapCopy = bitmap2.copy(Bitmap.Config.ARGB_8888, false);
                    try {
                        bitmap2.recycle();
                        bitmap2 = bitmapCopy;
                    } catch (Throwable th) {
                        th = th;
                        bitmap = bitmapCopy;
                        Log.e(TAG, th.toString());
                        return bitmap;
                    }
                } catch (Throwable th2) {
                    th = th2;
                    bitmap = bitmap2;
                }
            }
            if (objCallObjectMethod2 != null && (hardwareBuffer = (HardwareBuffer) MetaReflectUtil.callObjectMethod(objCallObjectMethod2, "getHardwareBuffer", null, new Object[0])) != null) {
                hardwareBuffer.close();
                Log.i(TAG, "hard ware buffer close!");
            }
            return bitmap2;
        } catch (Throwable th3) {
            th = th3;
        }
    }

    public static Bitmap getScreenShotS(Context context) {
        Bitmap bitmap;
        Throwable th;
        HardwareBuffer hardwareBuffer;
        try {
            Class<?> cls = Class.forName("android.view.SurfaceControl");
            Class<?> cls2 = Class.forName("android.view.SurfaceControl$DisplayCaptureArgs");
            Class<?> cls3 = Class.forName("android.view.SurfaceControl$DisplayCaptureArgs$Builder");
            Object objInvoke = cls3.getMethod("build", new Class[0]).invoke(cls3.getConstructor(IBinder.class).newInstance((IBinder) cls.getMethod("getInternalDisplayToken", new Class[0]).invoke(null, new Object[0])), new Object[0]);
            Method method = cls.getMethod("captureDisplay", cls2);
            method.setAccessible(true);
            Object objInvoke2 = method.invoke(null, objInvoke);
            bitmap = objInvoke2 == null ? null : (Bitmap) MetaReflectUtil.callObjectMethod(objInvoke2, "asBitmap", null, new Object[0]);
            if (bitmap != null) {
                try {
                    Bitmap bitmapCopy = bitmap.copy(Bitmap.Config.ARGB_8888, false);
                    try {
                        bitmap.recycle();
                        bitmap = bitmapCopy;
                    } catch (Throwable th2) {
                        th = th2;
                        bitmap = bitmapCopy;
                        Log.e(TAG, th.toString());
                        return bitmap;
                    }
                } catch (Throwable th3) {
                    th = th3;
                }
            }
            if (objInvoke2 != null && (hardwareBuffer = (HardwareBuffer) MetaReflectUtil.callObjectMethod(objInvoke2, "getHardwareBuffer", null, new Object[0])) != null) {
                hardwareBuffer.close();
                Log.i(TAG, "hard ware buffer close!");
            }
            return bitmap;
        } catch (Throwable th4) {
            bitmap = null;
            th = th4;
        }
    }
}
