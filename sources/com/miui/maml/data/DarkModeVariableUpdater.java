package com.miui.maml.data;

import android.content.Context;
import android.content.Intent;
import com.miui.maml.NotifierManager;

/* JADX INFO: loaded from: classes3.dex */
public class DarkModeVariableUpdater extends NotifierVariableUpdater {
    private IndexedVariable mDarkMode;
    private IndexedVariable mDarkWallpaperMode;

    public DarkModeVariableUpdater(VariableUpdaterManager variableUpdaterManager) {
        super(variableUpdaterManager, NotifierManager.TYPE_DARK_MODE);
        this.mDarkMode = new IndexedVariable(VariableNames.VAR_DARK_MODE, getRoot().getContext().mVariables, true);
        this.mDarkWallpaperMode = new IndexedVariable(VariableNames.VAR_DARK_MODE_WALLPAPER, getRoot().getContext().mVariables, true);
    }

    @Override // com.miui.maml.data.NotifierVariableUpdater, com.miui.maml.NotifierManager.OnNotifyListener
    public void onNotify(Context context, Intent intent, Object obj) {
        if (obj instanceof Integer) {
            int iIntValue = ((Integer) obj).intValue();
            if ((iIntValue & 1) == 1) {
                this.mDarkMode.set((Object) true);
            } else {
                this.mDarkMode.set((Object) false);
            }
            if ((iIntValue & 3) == 3) {
                this.mDarkWallpaperMode.set((Object) true);
                getRoot().setDarkWallpaperMode(true);
            } else {
                this.mDarkWallpaperMode.set((Object) false);
                getRoot().setDarkWallpaperMode(false);
            }
            getRoot().requestUpdate();
        }
    }
}
