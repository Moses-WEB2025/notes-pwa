package com.miui.notes.ai;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.graphics.Rect;
import android.os.Bundle;
import com.miui.common.stat.EditStat;
import com.miui.doodle.document.Layer;
import com.miui.notes.ai.data.input.UserInputHolder;
import com.miui.notes.ai.floatingwindow.AiFloatingWindow;
import com.miui.notes.ai.stat.AITextWidgetStat;
import com.miui.notes.ai.utils.ContextWrapperUtil;
import com.miui.notes.base.utils.Logger;
import com.miui.todo.utils.CharUtils;
import com.xiaomi.mirror.synergy.CallMethod;

/* JADX INFO: loaded from: classes4.dex */
public class AiTextWidgetServiceHelper {
    private static final String TAG = "AiTextWidgetServiceHelper";
    protected int bottom;
    protected volatile String hostPackageName;
    protected int left;
    protected AiFloatingWindow mAiFloatingWindow;
    protected Service mAiTextWidgetService;
    protected int right;
    protected int taskId;
    protected int top;
    protected Context wrapperContext;
    protected boolean mHasPassPermissionCheck = false;
    public volatile int startMode = 0;
    private volatile String mSelectedText = null;
    protected final String notesPackageName = "com.miui.notes";

    public void onDestroy() {
    }

    public AiTextWidgetServiceHelper(Service service) {
        this.mAiTextWidgetService = service;
    }

    public void showAiTextWidgetServiceHelper(boolean z) {
        this.mHasPassPermissionCheck = z;
        this.wrapperContext = ContextWrapperUtil.wrapperWindowContext(this.mAiTextWidgetService);
        this.mAiFloatingWindow = new AiFloatingWindow(this.wrapperContext, (AiTextWidgetService) this.mAiTextWidgetService);
    }

    public int onStartCommand(Intent intent, int i, int i2) {
        if (intent != null && intent.getExtras() != null) {
            Logger.INSTANCE.d(TAG, "onStartCommand: ");
            Bundle extras = intent.getExtras();
            this.hostPackageName = extras.getString("packageName");
            String string = extras.getString("selectedText");
            this.left = extras.getInt("left");
            this.top = extras.getInt(Layer.KEY_TOP);
            this.right = extras.getInt(EditStat.ELEMENT_RIGHT);
            this.bottom = extras.getInt("bottom");
            this.taskId = extras.getInt(CallMethod.ARG_TASK_ID);
            extras.getInt("originalViewLeft");
            extras.getInt("originalViewTop");
            extras.getInt("originalViewRight");
            extras.getInt("originalViewBottom");
            extras.getString("originalViewName");
            Logger.INSTANCE.d(TAG, "onStartCommand packageName: " + this.hostPackageName + " left " + this.left + " top " + this.top + " right " + this.right + " bottom " + this.bottom + " taskId " + this.taskId);
            if ("com.miui.notes".equals(this.hostPackageName)) {
                this.startMode = 0;
            } else {
                this.startMode = 1;
            }
            if (string != null && string.startsWith(CharUtils.ZERO_WIDTH_SPACE)) {
                string = string.substring(1);
            }
            this.mSelectedText = string;
            if (this.mHasPassPermissionCheck) {
                startFloatWindow(this.startMode, string);
            }
        }
        return 2;
    }

    public void startFloatWindowFromCTA() {
        Logger.INSTANCE.d(TAG, "startFloatWindowFromCTA ");
        startFloatWindow(this.mSelectedText, this);
    }

    protected void startFloatWindow(int i, String str) {
        Logger.INSTANCE.d(TAG, "startFloatWindow ");
        startFloatWindow(str, this);
    }

    public void startFloatWindow(String str, AiTextWidgetServiceHelper aiTextWidgetServiceHelper) {
        Logger.INSTANCE.i(TAG, "startFloatWindow");
        if (str == null) {
            str = "";
        }
        String str2 = str;
        if (aiTextWidgetServiceHelper != null) {
            this.mAiFloatingWindow.setWindowCloseListener(new AiFloatingWindow.WindowCloseListener() { // from class: com.miui.notes.ai.AiTextWidgetServiceHelper.1
                @Override // com.miui.notes.ai.floatingwindow.AiFloatingWindow.WindowCloseListener
                public void onWindowClosed() {
                    AiTextWidgetServiceHelper.this.stopService();
                }
            });
        }
        this.mAiFloatingWindow.showAiFloatingWindow(str2, new Rect(this.left, this.top, this.right, this.bottom), this.taskId, this.hostPackageName, null, null, false);
        AITextWidgetStat.reportAiEditorBarClick("unknown", "unknown", "unknown", str2.isEmpty() ? AITextWidgetStat.LONG_TOUCH_CURSOR : AITextWidgetStat.CHOOSE_TEXT, this.hostPackageName, "unknown", "unknown", str2.length(), this.wrapperContext);
    }

    public void closeAiFloatingWindow() {
        AiFloatingWindow aiFloatingWindow = this.mAiFloatingWindow;
        if (aiFloatingWindow == null || !aiFloatingWindow.isShowing()) {
            return;
        }
        Logger.INSTANCE.d(TAG, "closeAiTextWindow");
        this.mAiFloatingWindow.closeWindow();
    }

    public void stopService() {
        this.mAiTextWidgetService.stopForeground(true);
        this.mAiTextWidgetService.stopSelf();
        UserInputHolder.getInstance().resetUserInput();
    }
}
