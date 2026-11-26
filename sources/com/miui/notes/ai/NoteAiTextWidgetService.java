package com.miui.notes.ai;

import android.app.ActivityManager;
import android.content.Intent;
import android.content.res.Configuration;
import android.text.TextUtils;
import android.widget.Toast;
import com.miui.common.tool.LiteUtils;
import com.miui.hybirdeditor.RichEditorWebView;
import com.miui.notes.NoteApp;
import com.miui.notes.R;
import com.miui.notes.ai.utils.PreferenceUtils;
import com.miui.notes.ai.utils.RomUtils;
import com.miui.notes.base.utils.Logger;
import com.miui.notes.basefeature.noteedit.BaseHybridNoteEditFragment;
import java.util.List;

/* JADX INFO: loaded from: classes4.dex */
public class NoteAiTextWidgetService extends AiTextWidgetService {
    public static final String ACTION_START_AI_FLOATING_WINDOW = "action.start_ai_floating_window";
    public static final String TAG = "NoteAiTextWidgetService";
    public static int mTaskId;

    @Override // com.miui.notes.ai.AiTextWidgetService, android.app.Service
    public void onCreate() {
        super.onCreate();
        BaseHybridNoteEditFragment.mNoteAiTextWidgetService = this;
    }

    @Override // com.miui.notes.ai.AiTextWidgetService, android.app.Service
    public int onStartCommand(Intent intent, int i, int i2) {
        if (intent != null && intent.getExtras() != null && RichEditorWebView.class.getName().equals(intent.getExtras().getString("originalViewName")) && !LiteUtils.isLiteOrMiddle()) {
            List<ActivityManager.RunningTaskInfo> runningTasks = ((ActivityManager) getSystemService("activity")).getRunningTasks(1);
            if (runningTasks != null && !runningTasks.isEmpty()) {
                mTaskId = runningTasks.get(0).id;
            }
            Logger.INSTANCE.i(TAG, "NoteAiTextWidgetService onStartCommand taskId:" + mTaskId);
            Intent intent2 = new Intent(ACTION_START_AI_FLOATING_WINDOW);
            intent2.setPackage(NoteApp.getInstance().getPackageName());
            sendBroadcast(intent2);
            return 2;
        }
        if (intent != null && intent.getExtras() != null && TextUtils.isEmpty(intent.getExtras().getString("selectedText")) && PreferenceUtils.isAcceptedMode(this) && !RomUtils.isSupportLLMCotAI(this) && !RomUtils.isLLMAIWriting(this)) {
            Toast.makeText(this, getString(R.string.select_text_before_using), 0).show();
            return 2;
        }
        if (BaseHybridNoteEditFragment.mNoteAiTextWidgetService == null) {
            BaseHybridNoteEditFragment.mNoteAiTextWidgetService = this;
        }
        return super.onStartCommand(intent, i, i2);
    }

    @Override // com.miui.notes.ai.AiTextWidgetService, android.app.Service
    public void onDestroy() {
        super.onDestroy();
    }

    @Override // com.miui.notes.ai.AiTextWidgetService, android.app.Service, android.content.ComponentCallbacks
    public void onConfigurationChanged(Configuration configuration) {
        Logger.INSTANCE.i(TAG, "note onConfigurationChanged from service " + configuration + " orientation" + configuration.orientation);
        super.onConfigurationChanged(configuration);
    }
}
