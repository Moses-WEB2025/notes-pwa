package com.miui.notes.ai.utils;

import android.app.Service;
import android.content.Context;
import android.os.Build;
import android.view.Display;
import defpackage.R2;
import miuix.autodensity.AutoDensityConfig;
import miuix.core.util.WindowUtils;

/* JADX INFO: loaded from: classes4.dex */
public class ContextWrapperUtil {
    public static Context wrapperWindowContext(Service service) {
        Display defaultDisplay = WindowUtils.getWindowManager(service).getDefaultDisplay();
        Context contextCreateDisplayContext = null;
        if (defaultDisplay != null) {
            if (Build.VERSION.SDK_INT >= 31) {
                contextCreateDisplayContext = service.createWindowContext(defaultDisplay, R2.attr.preferenceCheckableItemSetMaskPaddingEnd, null);
            } else {
                contextCreateDisplayContext = service.createDisplayContext(defaultDisplay);
            }
        }
        return contextCreateDisplayContext != null ? AutoDensityConfig.createAutoDensityContextWrapper(contextCreateDisplayContext) : contextCreateDisplayContext;
    }
}
