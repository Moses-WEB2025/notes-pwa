package com.miui.todo.view;

import android.app.Activity;
import com.miui.common.record.AudioInputListener;
import com.miui.common.tool.AudioUtils;
import com.miui.common.view.AudioCreateWindow;
import com.miui.common.view.BaseAudioCreatePanel;

/* JADX INFO: loaded from: classes4.dex */
public class TodoAudioCreateWindow extends AudioCreateWindow {
    public TodoAudioCreateWindow(Activity activity, BaseAudioCreatePanel.StateCallback stateCallback, AudioInputListener audioInputListener) {
        super(activity, AudioUtils.TODO_MP3_DIR_NAME, stateCallback, audioInputListener, false);
    }
}
