package miuix.smartaction;

import android.content.Context;
import android.view.ActionMode;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import java.lang.reflect.Method;

/* JADX INFO: loaded from: classes6.dex */
public class SmartActionHelper {
    public static boolean isSupportSmartAction(Context context, View view) {
        Method declaredMethod;
        try {
            Class<?> clsLoadClass = context.getApplicationContext().createPackageContext("com.miuix.editor", 3).getClassLoader().loadClass("miuix.toolbar.FloatingActionModeHelper");
            if (clsLoadClass == null || (declaredMethod = clsLoadClass.getDeclaredMethod("supportHook", View.class, ActionMode.Callback.class, Integer.TYPE)) == null) {
                return false;
            }
            declaredMethod.setAccessible(true);
            return ((Boolean) declaredMethod.invoke(null, view, new ActionMode.Callback2() { // from class: miuix.smartaction.SmartActionHelper.1
                @Override // android.view.ActionMode.Callback
                public boolean onActionItemClicked(ActionMode actionMode, MenuItem menuItem) {
                    return false;
                }

                @Override // android.view.ActionMode.Callback
                public boolean onCreateActionMode(ActionMode actionMode, Menu menu) {
                    return false;
                }

                @Override // android.view.ActionMode.Callback
                public void onDestroyActionMode(ActionMode actionMode) {
                }

                @Override // android.view.ActionMode.Callback
                public boolean onPrepareActionMode(ActionMode actionMode, Menu menu) {
                    return false;
                }
            }, 1)).booleanValue();
        } catch (Exception unused) {
            return false;
        }
    }
}
