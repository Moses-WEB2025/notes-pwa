package com.miui.pad.feature.notes.multihandwrite;

import android.app.Activity;
import android.app.Application;
import android.content.Intent;
import android.os.Bundle;
import android.os.PowerManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import androidx.activity.result.ActivityResultLauncher;
import androidx.core.view.InputDeviceCompat;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import com.miui.common.app.BaseNoteApp;
import com.miui.common.base.NoteLoadDataEntity;
import com.miui.handwriting.common.MiuiHandWritingView;
import com.miui.handwriting.common.taskservice.CreationServiceManager;
import com.miui.handwriting.common.tools.DisplayCutoutHolder;
import com.miui.handwriting.common.tools.constants.Constants;
import com.miui.handwriting.common.tools.constants.Creation;
import com.miui.handwriting.common.view.BaseActivity;
import com.miui.libhandwritingpage.utils.Utils;
import com.miui.notes.R;
import com.miui.penengine.posture.MiuiStylusPosture;
import com.miui.penengine.touchfilm.MiuiTouchFilmUtils;
import java.util.Timer;
import miuix.androidbasewidget.widget.EditText;
import xc.engine.api.PenEngine;
import xc.engine.utils.PreferenceUtils;

/* JADX INFO: loaded from: classes4.dex */
public class CreationEditActivity extends BaseActivity {
    public static final String INTENT_IMPORT_IMAGE = "import_bg_image";
    public static final String INTENT_IMPORT_PDF = "import_pdf";
    private static final String TAG = "CreationEditActivity";
    private static final String TAG_INSPIRATION = "Inspiration";
    private CreationEditFragment creationEditFragment;
    private MultiHandWriteEntity handWriteEntity;
    private int mPostureDegree;
    private Timer mTimer;
    private MiuiHandWritingView miuiHandWritingView;
    private boolean wakeUp;
    private boolean isShowOnKeyguard = false;
    private boolean isNeedClearFlag = false;

    private void hideNavigationBar() {
    }

    public static void launchFromCreateByAction(Application application) {
    }

    public static void launchFromImportFile(Activity activity, String str, int i) {
    }

    public void launchFromExternal(Intent intent) {
    }

    @Override // android.app.Activity, android.view.Window.Callback
    public boolean dispatchGenericMotionEvent(MotionEvent motionEvent) {
        if ((motionEvent.getSource() & InputDeviceCompat.SOURCE_JOYSTICK) == 16777232 && motionEvent.getAction() == 2) {
            this.mPostureDegree = (int) (motionEvent.getAxisValue(17) * 360.0f);
            PreferenceUtils.setV(BaseNoteApp.getInstance(), this.mPostureDegree);
            return false;
        }
        return super.dispatchGenericMotionEvent(motionEvent);
    }

    public static void launchFromCreate(Activity activity, NoteLoadDataEntity noteLoadDataEntity, boolean z, boolean z2, ActivityResultLauncher<Intent> activityResultLauncher) {
        Intent intent = new Intent(activity, (Class<?>) CreationEditActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("entity", noteLoadDataEntity);
        bundle.putBoolean(Creation.JUMP_IS_IN_MULTIWINDOW_MODE, z);
        bundle.putBoolean(Creation.JUMP_IS_CREATE_NEW, z2);
        intent.putExtras(bundle);
        activityResultLauncher.launch(intent);
    }

    public static void launchFromLoading(Activity activity, Long l) {
        Intent intent = new Intent(activity, (Class<?>) CreationEditActivity.class);
        intent.putExtra("id", l);
        activity.startActivity(intent);
    }

    public static void launchFromMishow(Activity activity, Long l) {
        Intent intent = new Intent(activity, (Class<?>) CreationEditActivity.class);
        intent.putExtra("id", l);
        intent.putExtra(Creation.FROM_MISHOW, true);
        activity.startActivity(intent);
    }

    public static void launchFromSearch(Activity activity, Long l, String str) {
        Intent intent = new Intent(activity, (Class<?>) CreationEditActivity.class);
        intent.putExtra("id", l);
        intent.putExtra(Creation.FROM_SEARCH, true);
        intent.putExtra(Creation.SEARCH_CONTENT, str);
        activity.startActivity(intent);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // miuix.appcompat.app.AppCompatActivity, androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        Log.d(TAG, "onCreate");
        DisplayCutoutHolder.INSTANCE.init(this);
        hideNavigationBar();
        setContentView(R.layout.activity_creation_edit);
        deliverIntent(getIntent());
        getWindow().setColorMode(1);
        MiuiStylusPosture.init(getApplicationContext());
    }

    @Override // androidx.fragment.app.FragmentActivity, android.app.Activity
    protected void onResume() {
        super.onResume();
        hideNavigationBar();
        if (this.isShowOnKeyguard) {
            getWindow().addFlags(524288);
        }
        Log.e(TAG_INSPIRATION, "onResume");
        wakeUpScreen(getIntent());
        CreationServiceManager.INSTANCE.bindCreationTaskService(BaseNoteApp.getInstance());
        MiuiTouchFilmUtils.init(getApplicationContext(), null);
    }

    @Override // androidx.fragment.app.FragmentActivity, android.app.Activity
    protected void onPause() {
        super.onPause();
        if (this.isNeedClearFlag) {
            this.isNeedClearFlag = false;
            getWindow().clearFlags(524288);
        }
        if (this.isShowOnKeyguard) {
            this.isNeedClearFlag = true;
        }
    }

    @Override // miuix.appcompat.app.AppCompatActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    protected void onDestroy() {
        super.onDestroy();
        Timer timer = this.mTimer;
        if (timer != null) {
            timer.cancel();
            this.mTimer.purge();
            this.mTimer = null;
        }
        this.creationEditFragment = null;
        this.handWriteEntity = null;
        this.miuiHandWritingView = null;
        PenEngine.setIsEmptyCreationFromCreate(true);
        MiuiTouchFilmUtils.onDestroy();
    }

    private void wakeUpScreen(Intent intent) {
        if (intent == null || this.wakeUp) {
            Log.e(TAG_INSPIRATION, "wakeUpScreen: intent is null");
            return;
        }
        String stringExtra = intent.getStringExtra(Constants.INTENT_SHORTCUT);
        String type = intent.getType();
        Log.e(TAG_INSPIRATION, "wakeUpScreen: shortcut is " + stringExtra);
        Log.e(TAG_INSPIRATION, "wakeUpScreen: type is " + type);
        if (TextUtils.equals(type, Constants.CONTENT_ITEM_TYPE) && TextUtils.equals(stringExtra, Constants.EXTERNAL_JUMP_STYLUS) && TextUtils.equals(intent.getStringExtra(Constants.INTENT_SCENE), Constants.EXTERNAL_JUMP_STYLUS_SCREEN_OFF)) {
            Log.e(TAG_INSPIRATION, "wakeUpScreen");
            PowerManager.WakeLock wakeLockNewWakeLock = null;
            try {
                wakeLockNewWakeLock = ((PowerManager) getSystemService("power")).newWakeLock(268435462, getClass().getCanonicalName());
                wakeLockNewWakeLock.acquire(10000L);
                this.wakeUp = true;
            } catch (Exception e) {
                Log.e(TAG_INSPIRATION, "WakeLock acquire failed", e);
            } finally {
                if (wakeLockNewWakeLock != null && wakeLockNewWakeLock.isHeld()) {
                    wakeLockNewWakeLock.release();
                }
            }
        }
    }

    private void initFragment() {
        FragmentManager supportFragmentManager = getSupportFragmentManager();
        CreationEditFragment creationEditFragment = (CreationEditFragment) supportFragmentManager.findFragmentById(R.id.editor_content);
        this.creationEditFragment = creationEditFragment;
        if (creationEditFragment == null) {
            Bundle bundle = new Bundle();
            bundle.putInt("isCreation", 0);
            CreationEditFragment creationEditFragment2 = new CreationEditFragment();
            this.creationEditFragment = creationEditFragment2;
            creationEditFragment2.setArguments(bundle);
            this.creationEditFragment.setArguments(getIntent().getExtras());
            FragmentTransaction fragmentTransactionBeginTransaction = supportFragmentManager.beginTransaction();
            fragmentTransactionBeginTransaction.add(R.id.editor_content, this.creationEditFragment);
            fragmentTransactionBeginTransaction.commit();
        }
    }

    @Override // android.app.Activity, android.view.Window.Callback
    public void onWindowFocusChanged(boolean z) {
        super.onWindowFocusChanged(z);
        CreationEditFragment creationEditFragment = this.creationEditFragment;
        if (creationEditFragment == null || !z) {
            return;
        }
        creationEditFragment.onWindowFocused();
    }

    private void deliverIntent(Intent intent) {
        if (intent == null) {
            Log.e(TAG_INSPIRATION, "deliverIntent: intent is null");
            return;
        }
        Log.e(TAG_INSPIRATION, "deliverIntent: action is " + intent.getAction());
        if (TextUtils.equals(intent.getAction(), Constants.INSERT_CREATION) && isLaunchFromPen(intent)) {
            String stringExtra = intent.getStringExtra(Constants.INTENT_SCENE);
            Log.e(TAG_INSPIRATION, "Scene is " + stringExtra);
            if (TextUtils.equals(stringExtra, Constants.EXTERNAL_JUMP_STYLUS_SCREEN_OFF)) {
                Log.e(TAG_INSPIRATION, "off_screen");
                this.wakeUp = false;
                this.isShowOnKeyguard = true;
                intent.putExtra(Constants.EXTERNAL_JUMP_STYLUS_SCREEN_OFF, true);
            } else if (TextUtils.equals(stringExtra, Constants.EXTERNAL_JUMP_STYLUS_KEYGUARD)) {
                Log.e(TAG_INSPIRATION, "keyguard");
                this.isShowOnKeyguard = true;
                intent.putExtra(Constants.EXTERNAL_JUMP_STYLUS_KEYGUARD, true);
            }
            launchFromExternal(intent);
        } else if (TextUtils.equals(intent.getAction(), Constants.INSERT_CREATION_FROM_PC)) {
            if (intent.getBooleanExtra(Constants.PC_INTENT_WHEN_LOCKED, false)) {
                this.isShowOnKeyguard = true;
            }
            launchFromExternal(intent);
        }
        initFragment();
    }

    private boolean isLaunchFromPen(Intent intent) {
        if (intent.getType() == null) {
            Log.d(TAG, "intent type is  null");
            return false;
        }
        return TextUtils.equals(intent.getType(), Constants.CONTENT_ITEM_TYPE);
    }

    @Override // androidx.activity.ComponentActivity, android.app.Activity
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override // android.app.Activity, android.view.Window.Callback
    public boolean dispatchTouchEvent(MotionEvent motionEvent) {
        View currentFocus = getCurrentFocus();
        boolean zDispatchTouchEvent = super.dispatchTouchEvent(motionEvent);
        if (currentFocus instanceof EditText) {
            View currentFocus2 = getCurrentFocus();
            int[] iArr = new int[2];
            currentFocus2.getLocationOnScreen(iArr);
            float rawX = (motionEvent.getRawX() + currentFocus2.getLeft()) - iArr[0];
            float rawY = (motionEvent.getRawY() + currentFocus2.getTop()) - iArr[1];
            if (motionEvent.getAction() == 0 && (rawX < currentFocus2.getLeft() || rawX > currentFocus2.getRight() || rawY < currentFocus2.getTop() || rawY > currentFocus2.getBottom())) {
                Utils.hideSoftInput(currentFocus2);
                currentFocus.clearFocus();
            }
        }
        return zDispatchTouchEvent;
    }

    @Override // android.app.Activity
    public void onTopResumedActivityChanged(boolean z) {
        super.onTopResumedActivityChanged(z);
        Log.i(TAG, "onTopResumedActivityChanged " + z);
        if (z) {
            return;
        }
        Utils.hideSoftInput(getWindow().getDecorView());
    }

    @Override // androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, android.app.Activity
    protected void onActivityResult(int i, int i2, Intent intent) {
        super.onActivityResult(i, i2, intent);
        this.creationEditFragment.onActivityResult(i, i2, intent);
    }
}
