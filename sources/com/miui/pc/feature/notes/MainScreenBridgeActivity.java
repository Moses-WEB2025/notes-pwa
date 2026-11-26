package com.miui.pc.feature.notes;

import android.content.ClipData;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcelable;
import android.os.PowerManager;
import android.text.TextUtils;
import androidx.core.provider.FontsContractCompat;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import com.miui.common.base.BaseActivity;
import com.miui.common.base.SafeBroadcastReceiver;
import com.miui.common.tool.CompatUtils;
import com.miui.common.tool.Utils;
import com.miui.doodle.DoodleApplication;
import com.miui.doodle.base.DoodleParams;
import com.miui.notes.NoteApp;
import com.miui.notes.doodle.feature.DoodleActivity;
import com.miui.notes.doodle.feature.DoodleFragment;
import com.miui.notes.provider.NotesProvider;
import com.miui.notes.tool.AttachmentUtils;
import com.miui.pad.feature.notes.share.ShareAppsAdapter;
import java.io.File;

/* JADX INFO: loaded from: classes4.dex */
public class MainScreenBridgeActivity extends BaseActivity {
    private static final String ACTION_GALLERY_ALBUM = "com.miui.gallery.action.VIEW_ALBUM";
    public static final String BRIDGE_ACTION_FINISH = "com.miui.pcbridge.action.finish";
    public static final String BRIDGE_ACTION_RESULT = "com.miui.pcbridge.action.result";
    private static final String CAMERA_PKG_NAME = "com.android.camera";
    public static final String EXTRA_METHOD = "bridge_method";
    public static final String EXTRA_PARAMS = "bridge_params";
    private static final String GALLERY_PKG_NAME = "com.miui.gallery";
    public static final String KEY_DOODLE_RESULT = "doodle_result";
    public static final int KEY_PICK_LIMITED_NUMBER = 10;
    public static final String KEY_PICK_RESULT_DATA = "pick-result-data";
    public static final String KEY_PICK_UPPER_BOUND = "pick-upper-bound";
    public static final int METHOD_FINISH = -1;
    public static final int METHOD_SELECT_PHOTO = 2;
    public static final int METHOD_START_DOODLE = 3;
    public static final int METHOD_TAKE_PHOTO = 1;
    public static final int REQUEST_CODE_CAMERA = 1;
    public static final int REQUEST_CODE_DOODLE = 32;
    public static final int REQUEST_CODE_DOODLE_EDIT = 64;
    public static final int REQUEST_CODE_GALLERY = 2;
    public static final String SEND_TYPE_IMAGE_JPEG = "image/jpeg";
    public static final String SEND_TYPE_TEXT = "text/plain";
    private static final String TAG = "MainScreenBridgeActivity";
    LocalBroadcastManager mLocalBroadcastManager = null;
    private MyFinishReceiver mFinishReceiver = new MyFinishReceiver(this);

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.miui.common.base.BaseActivity, miuix.appcompat.app.AppCompatActivity, androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(BRIDGE_ACTION_FINISH);
        turnOnScreen();
        getWindow().addFlags(128);
        this.mLocalBroadcastManager = LocalBroadcastManager.getInstance(this);
        if (Utils.isAboveSDK34()) {
            registerReceiver(this.mFinishReceiver, intentFilter, 2);
        } else {
            registerReceiver(this.mFinishReceiver, intentFilter);
        }
        requestWindowFeature(1);
        if (getIntent() == null || getIntent().getExtras() == null) {
            return;
        }
        int i = getIntent().getExtras().getInt(EXTRA_METHOD, 0);
        if (i == 1) {
            takePhoto(1);
        } else if (i == 2) {
            selectPhoto(2);
        } else if (i == 3) {
            startDoodle(getIntent().getExtras().getParcelable(EXTRA_PARAMS));
        }
        if (BRIDGE_ACTION_FINISH.equals(getIntent().getAction())) {
            finish();
        }
    }

    @Override // com.miui.common.base.BaseActivity, androidx.activity.ComponentActivity, android.app.Activity
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        if (BRIDGE_ACTION_FINISH.equals(intent.getAction())) {
            finish();
        }
    }

    @Override // androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, android.app.Activity
    protected void onActivityResult(int i, int i2, Intent intent) {
        super.onActivityResult(i, i2, intent);
        Intent intent2 = new Intent();
        intent2.setAction(BRIDGE_ACTION_RESULT);
        intent2.putExtra("request_code", i);
        intent2.putExtra(FontsContractCompat.Columns.RESULT_CODE, i2);
        if (i != 2) {
            if (i != 32) {
                if (i == 64 && intent != null) {
                    String stringExtra = intent.getStringExtra(DoodleFragment.KEY_IMAGE_NAME);
                    if (!TextUtils.isEmpty(stringExtra)) {
                        if (!DoodleApplication.SaveFlagNoChange.contentEquals(stringExtra)) {
                            intent2.putExtra(KEY_DOODLE_RESULT, AttachmentUtils.saveImageFile(NoteApp.getInstance(), new File(stringExtra)));
                        } else {
                            intent2.putExtra(KEY_DOODLE_RESULT, DoodleApplication.SaveFlagNoChange);
                        }
                    } else {
                        intent2.putExtra(KEY_DOODLE_RESULT, "");
                    }
                }
            } else if (intent != null) {
                intent2.putExtra(KEY_DOODLE_RESULT, AttachmentUtils.saveImageFile(NoteApp.getInstance(), new File(intent.getStringExtra(DoodleFragment.KEY_IMAGE_NAME))));
            }
        } else if (intent != null) {
            intent2.putExtra("pick-result-data", intent.getParcelableArrayListExtra("pick-result-data"));
        }
        sendBroadcast(intent2);
        finish();
    }

    @Override // com.miui.common.base.BaseActivity, miuix.appcompat.app.AppCompatActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    protected void onDestroy() {
        getWindow().clearFlags(128);
        try {
            unregisterReceiver(this.mFinishReceiver);
        } catch (Exception e) {
            e.printStackTrace();
        }
        this.mLocalBroadcastManager = null;
        super.onDestroy();
    }

    private void takePhoto(int i) {
        Uri compatUri;
        Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
        intent.setFlags(524290);
        File scrapFile = NotesProvider.getScrapFile(this);
        if (scrapFile == null || (compatUri = CompatUtils.getCompatUri(this, scrapFile)) == null) {
            return;
        }
        intent.putExtra("output", compatUri);
        intent.setClipData(ClipData.newUri(getContentResolver(), "Camera", compatUri));
        intent.setPackage(CAMERA_PKG_NAME);
        if (getPackageManager().queryIntentActivities(intent, 0).size() <= 0) {
            intent.setPackage(null);
        }
        startActivityForResult(Intent.createChooser(intent, null), i);
    }

    private void selectPhoto(int i) {
        Intent intent = new Intent("android.intent.action.PICK");
        intent.setFlags(524288);
        intent.putExtra("pick-upper-bound", 10);
        intent.setType(ShareAppsAdapter.TYPE_IMAGE);
        intent.setPackage("com.miui.gallery");
        if (getPackageManager().queryIntentActivities(intent, 0).size() <= 0) {
            intent.setPackage(null);
        }
        startActivityForResult(Intent.createChooser(intent, null), i);
    }

    private void startDoodle(Parcelable parcelable) {
        Intent intent = new Intent(this, (Class<?>) DoodleActivity.class);
        if (parcelable != null) {
            intent.putExtra(DoodleFragment.KEY_PARAMS, parcelable);
            startActivityForResult(intent, 64);
            return;
        }
        DoodleApplication.cancelAndDeletePreloadData();
        DoodleParams doodleParams = new DoodleParams();
        doodleParams.setFullScreen(false);
        intent.putExtra(DoodleFragment.KEY_PARAMS, doodleParams);
        startActivityForResult(intent, 32);
    }

    private void turnOnScreen() {
        PowerManager.WakeLock wakeLockNewWakeLock = ((PowerManager) getSystemService("power")).newWakeLock(268435466, getClass().getName());
        wakeLockNewWakeLock.acquire();
        wakeLockNewWakeLock.release();
    }

    private static class MyFinishReceiver extends SafeBroadcastReceiver<MainScreenBridgeActivity> {
        public MyFinishReceiver(MainScreenBridgeActivity mainScreenBridgeActivity) {
            super(mainScreenBridgeActivity);
        }

        @Override // com.miui.common.base.SafeBroadcastReceiver, android.content.BroadcastReceiver
        public void onReceive(Context context, Intent intent) {
            if (hasReference()) {
                getWkObject().finish();
            }
        }
    }
}
