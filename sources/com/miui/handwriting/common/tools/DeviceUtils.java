package com.miui.handwriting.common.tools;

import android.content.Context;
import android.hardware.input.InputManager;
import android.os.Build;
import android.view.InputDevice;
import com.miui.deviceid.IdentifierManager;
import com.miui.handwriting.common.HandWritingViewApplication;
import com.miui.libhandwritingpage.utils.MetaReflectUtil;
import com.miui.richeditor.schema.HtmlParser;

/* JADX INFO: loaded from: classes3.dex */
public class DeviceUtils {
    public static String getOAId() {
        if (IdentifierManager.isSupported()) {
            return IdentifierManager.getOAID(HandWritingViewApplication.INSTANCE.getApplication());
        }
        return "";
    }

    public static int getXiaomiStylusType(Context context) {
        int iIntValue;
        if (context == null) {
            return 0;
        }
        InputManager inputManager = (InputManager) context.getSystemService(HtmlParser.InputElement.TAG);
        for (int i : inputManager.getInputDeviceIds()) {
            InputDevice inputDevice = inputManager.getInputDevice(i);
            if (inputDevice != null && (iIntValue = ((Integer) MetaReflectUtil.callObjectMethod(inputDevice, "isXiaomiStylus", new Class[0], new Object[0])).intValue()) > 0 && iIntValue <= 4) {
                return iIntValue;
            }
        }
        return 0;
    }

    public static boolean isSmallPad() {
        return Build.DEVICE.equals("spark") || Build.DEVICE.equals("flare");
    }

    public static boolean isO88() {
        return Build.DEVICE.equals("turner");
    }
}
