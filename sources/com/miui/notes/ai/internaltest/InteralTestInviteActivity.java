package com.miui.notes.ai.internaltest;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Rect;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import com.miui.ai.text.widget.R;
import com.miui.notes.ai.utils.DisplayUtil;
import com.miui.notes.ai.utils.FreeModeUtils;
import com.miui.notes.ai.utils.PreferenceUtils;
import com.miui.notes.ai.utils.RomUtils;
import com.miui.notes.ai.utils.ScreenPosistionUtils;
import com.miui.notes.ai.utils.UIUtils;
import com.miui.notes.base.utils.Logger;
import miuix.animation.Folme;
import miuix.animation.base.AnimConfig;

/* JADX INFO: loaded from: classes4.dex */
public class InteralTestInviteActivity extends Activity {
    public static final String ACTION_INTERNAL_TEST_INVITE = "action.internal_test_invite";
    public static final String ACTION_INTERNAL_TEST_INVITE_KEY = "action.internal_test_invite_key";
    private static String TAG = "InteralTestInviteActivity";

    @Override // android.app.Activity
    protected void onCreate(Bundle bundle) {
        View viewFindViewById;
        super.onCreate(bundle);
        Logger.INSTANCE.i(TAG, "onCreate");
        if (PreferenceUtils.isInternalTestInviteAccepted(getApplicationContext()) || PreferenceUtils.isInternalTestInviteAcceptedForSpecial(getApplicationContext())) {
            Logger.INSTANCE.d(TAG, "Internal is Accepted, turn to NotesListActivity");
            try {
                Intent intent = new Intent();
                intent.setClassName("com.miui.notes", "com.miui.notes.ui.NotesListActivity");
                intent.setFlags(805306368);
                startActivity(intent);
                finish();
            } catch (Exception e) {
                Logger.INSTANCE.e(TAG, "startActivity error:" + e);
            }
        }
        if ((RomUtils.isFoldDevice() && ScreenPosistionUtils.isInNarrowScreen(this)) || RomUtils.isPhoneDevice()) {
            setRequestedOrientation(1);
        }
        if (RomUtils.isFoldDevice()) {
            if (ScreenPosistionUtils.isInNarrowScreen(this)) {
                setContentView(R.layout.ai_invitation_test_activity);
            } else if (DisplayUtil.isLandScape(this)) {
                setContentView(R.layout.ai_invitation_test_activity_flod_in_land);
            } else {
                setContentView(R.layout.ai_invitation_test_activity_flod_in);
            }
        } else if (RomUtils.isPadDevice()) {
            if (DisplayUtil.isLandScape(this)) {
                if (RomUtils.isMiniPad()) {
                    setContentView(R.layout.ai_invitation_test_activity_pad_small_screen_landscape);
                } else {
                    setContentView(R.layout.ai_invitation_test_activity_pad_landscape);
                }
            } else {
                setContentView(R.layout.ai_invitation_test_activity_pad_portrait);
            }
        } else {
            setContentView(R.layout.ai_invitation_test_activity);
        }
        if (RomUtils.isAboveSDK35() && (viewFindViewById = findViewById(R.id.root_view)) != null) {
            viewFindViewById.setFitsSystemWindows(true);
        }
        if (findViewById(R.id.invitation_icon_container) != null && findViewById(R.id.head_ll) != null && findViewById(R.id.log_iv) != null) {
            if (UIUtils.isSmallWindowMode(this)) {
                findViewById(R.id.invitation_icon_container).setVisibility(8);
            } else if (isInMultiWindowMode()) {
                findViewById(R.id.invitation_icon_container).setVisibility(8);
                if (getResources().getConfiguration().screenHeightDp < 300) {
                    findViewById(R.id.head_ll).setVisibility(8);
                    findViewById(R.id.log_iv).setVisibility(8);
                } else if (RomUtils.isPhoneDevice() && DisplayUtil.isLandScape(this)) {
                    findViewById(R.id.head_ll).setVisibility(8);
                    findViewById(R.id.log_iv).setVisibility(0);
                } else if (RomUtils.isPhoneDevice() && !DisplayUtil.isLandScape(this)) {
                    findViewById(R.id.head_ll).setVisibility(0);
                    findViewById(R.id.log_iv).setVisibility(0);
                } else if (RomUtils.isFoldDevice() && ScreenPosistionUtils.isInNarrowScreen(this) && DisplayUtil.isLandScape(this)) {
                    findViewById(R.id.head_ll).setVisibility(8);
                    findViewById(R.id.log_iv).setVisibility(0);
                } else if (RomUtils.isFoldDevice() && ScreenPosistionUtils.isInNarrowScreen(this) && !DisplayUtil.isLandScape(this)) {
                    findViewById(R.id.head_ll).setVisibility(0);
                    findViewById(R.id.log_iv).setVisibility(0);
                }
            } else {
                findViewById(R.id.invitation_icon_container).setVisibility(0);
                findViewById(R.id.head_ll).setVisibility(0);
                findViewById(R.id.log_iv).setVisibility(0);
            }
        }
        Logger.INSTANCE.d(TAG, "onCreate: Build.DEVICE: " + Build.DEVICE);
        findViewById(R.id.back_iv).setOnClickListener(new View.OnClickListener() { // from class: com.miui.notes.ai.internaltest.InteralTestInviteActivity.1
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                InteralTestInviteActivity.this.finish();
            }
        });
        findViewById(R.id.back_iv).setContentDescription(getString(R.string.ai_text_widget_return));
        findViewById(R.id.btn_agress).setOnClickListener(new View.OnClickListener() { // from class: com.miui.notes.ai.internaltest.InteralTestInviteActivity.2
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                Logger.INSTANCE.i(InteralTestInviteActivity.TAG, "click agree");
                Intent intent2 = new Intent();
                intent2.setAction(InteralTestInviteActivity.ACTION_INTERNAL_TEST_INVITE);
                intent2.putExtra(InteralTestInviteActivity.ACTION_INTERNAL_TEST_INVITE_KEY, true);
                InteralTestInviteActivity.this.sendBroadcast(intent2);
                PreferenceUtils.setInternalTestInviteAcceptedForSpecial(InteralTestInviteActivity.this, true);
                InteralTestInviteActivity.this.finish();
            }
        });
        findViewById(R.id.btn_reject).setOnClickListener(new View.OnClickListener() { // from class: com.miui.notes.ai.internaltest.InteralTestInviteActivity.3
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                Intent intent2 = new Intent();
                intent2.setAction(InteralTestInviteActivity.ACTION_INTERNAL_TEST_INVITE);
                intent2.putExtra(InteralTestInviteActivity.ACTION_INTERNAL_TEST_INVITE_KEY, false);
                InteralTestInviteActivity.this.sendBroadcast(intent2);
                InteralTestInviteActivity.this.finish();
            }
        });
        Folme.useAt(findViewById(R.id.btn_agress)).touch().handleTouchOf(findViewById(R.id.btn_agress), new AnimConfig[0]);
        Folme.useAt(findViewById(R.id.btn_reject)).touch().handleTouchOf(findViewById(R.id.btn_reject), new AnimConfig[0]);
    }

    @Override // android.app.Activity
    protected void onDestroy() {
        Logger.INSTANCE.i(TAG, "onDestroy");
        super.onDestroy();
    }

    @Override // android.app.Activity, android.content.ComponentCallbacks
    public void onConfigurationChanged(Configuration configuration) {
        super.onConfigurationChanged(configuration);
        if (findViewById(R.id.invitation_icon_container) == null || findViewById(R.id.head_ll) == null || findViewById(R.id.log_iv) == null) {
            return;
        }
        if (UIUtils.isSmallWindowMode(this)) {
            findViewById(R.id.invitation_icon_container).setVisibility(8);
            Rect rectIsHostInFreeMode = FreeModeUtils.isHostInFreeMode(getTaskId());
            if (rectIsHostInFreeMode != null && rectIsHostInFreeMode.height() < 500) {
                findViewById(R.id.head_ll).setVisibility(8);
                findViewById(R.id.log_iv).setVisibility(8);
                return;
            } else {
                if (rectIsHostInFreeMode == null || rectIsHostInFreeMode.height() < 500) {
                    return;
                }
                findViewById(R.id.head_ll).setVisibility(0);
                findViewById(R.id.log_iv).setVisibility(0);
                return;
            }
        }
        if (isInMultiWindowMode()) {
            findViewById(R.id.invitation_icon_container).setVisibility(8);
            if (getResources().getConfiguration().screenHeightDp < 300) {
                findViewById(R.id.head_ll).setVisibility(8);
                findViewById(R.id.log_iv).setVisibility(8);
                return;
            }
            if (RomUtils.isPhoneDevice() && DisplayUtil.isLandScape(this)) {
                findViewById(R.id.head_ll).setVisibility(8);
                findViewById(R.id.log_iv).setVisibility(0);
                return;
            }
            if (RomUtils.isPhoneDevice() && !DisplayUtil.isLandScape(this)) {
                findViewById(R.id.head_ll).setVisibility(0);
                findViewById(R.id.log_iv).setVisibility(0);
                return;
            }
            if (RomUtils.isFoldDevice() && ScreenPosistionUtils.isInNarrowScreen(this) && DisplayUtil.isLandScape(this)) {
                findViewById(R.id.head_ll).setVisibility(8);
                findViewById(R.id.log_iv).setVisibility(0);
                return;
            } else {
                if (RomUtils.isFoldDevice() && ScreenPosistionUtils.isInNarrowScreen(this) && !DisplayUtil.isLandScape(this)) {
                    findViewById(R.id.head_ll).setVisibility(0);
                    findViewById(R.id.log_iv).setVisibility(0);
                    return;
                }
                return;
            }
        }
        findViewById(R.id.invitation_icon_container).setVisibility(0);
        findViewById(R.id.head_ll).setVisibility(0);
        findViewById(R.id.log_iv).setVisibility(0);
    }
}
