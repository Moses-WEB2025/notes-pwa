package com.miui.notes.ui;

import android.text.SpannableString;
import android.text.util.Linkify;

/* JADX INFO: loaded from: classes4.dex */
public class PhoneNumberCacheHelper {
    private static boolean sPhoneNumberResourceCached = false;

    public static boolean hasPhoneNumberResourceCached() {
        return sPhoneNumberResourceCached;
    }

    public static void cachePhoneNumberResource() {
        if (sPhoneNumberResourceCached) {
            return;
        }
        Thread thread = new Thread(new Runnable() { // from class: com.miui.notes.ui.PhoneNumberCacheHelper$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                Linkify.addLinks(new SpannableString("12345"), 15);
            }
        }, "PhoneNumberResourceCache");
        thread.setPriority(1);
        thread.start();
        sPhoneNumberResourceCached = true;
    }
}
