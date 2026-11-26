package com.miui.pad.feature.notes.calc;

import android.app.Application;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.MotionEvent;
import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.core.view.InputDeviceCompat;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import ch.qos.logback.core.CoreConstants;
import com.market.sdk.reflect.ReflectUtilsForMiui;
import com.miui.common.app.BaseNoteApp;
import com.miui.common.app.ShareViewModel;
import com.miui.common.base.NoteLoadDataEntity;
import com.miui.common.tool.JumpVerificationUtils;
import com.miui.notes.NoteApp;
import com.miui.notes.R;
import com.miui.notes.tool.PermissionNewUtils;
import com.miui.notes.tool.PermissionUtils;
import com.miui.pad.feature.notes.multihandwrite.CreationEditActivity;
import com.miui.penengine.posture.MiuiStylusPosture;
import com.miui.penengine.touchfilm.MiuiTouchFilmUtils;
import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import miuix.appcompat.app.AppCompatActivity;
import xc.engine.api.PenEngine;
import xc.engine.utils.PreferenceUtils;

/* JADX INFO: compiled from: CalcEditActivity.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000@\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\b\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\f\u0018\u0000 $2\u00020\u0001:\u0001$B\u0007¢\u0006\u0004\b\u0002\u0010\u0003J\u0012\u0010\u0011\u001a\u00020\u00122\b\u0010\u0013\u001a\u0004\u0018\u00010\u0014H\u0014J\u0012\u0010\u0015\u001a\u00020\u00122\b\u0010\u0016\u001a\u0004\u0018\u00010\u0010H\u0014J\u0010\u0010\u0017\u001a\u00020\u00072\u0006\u0010\u0018\u001a\u00020\u0019H\u0016J\u0012\u0010\u001a\u001a\u00020\u00122\b\u0010\u0016\u001a\u0004\u0018\u00010\u0010H\u0002J\b\u0010\u001b\u001a\u00020\u0012H\u0002J\u0010\u0010\u001c\u001a\u00020\u00122\u0006\u0010\u001d\u001a\u00020\u0007H\u0016J\b\u0010\u001e\u001a\u00020\u0012H\u0014J\b\u0010\u001f\u001a\u00020\u0012H\u0016J\b\u0010 \u001a\u00020\u0012H\u0002J\b\u0010!\u001a\u00020\u0012H\u0014J\b\u0010\"\u001a\u00020\u0012H\u0002J\b\u0010#\u001a\u00020\u0012H\u0002R\u0010\u0010\u0004\u001a\u0004\u0018\u00010\u0005X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0007X\u0082\u000e¢\u0006\u0002\n\u0000R\u001a\u0010\b\u001a\u00020\tX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\n\u0010\u000b\"\u0004\b\f\u0010\rR\u0014\u0010\u000e\u001a\b\u0012\u0004\u0012\u00020\u00100\u000fX\u0082.¢\u0006\u0002\n\u0000¨\u0006%"}, d2 = {"Lcom/miui/pad/feature/notes/calc/CalcEditActivity;", "Lmiuix/appcompat/app/AppCompatActivity;", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "()V", "calcEditFragment", "Lcom/miui/pad/feature/notes/calc/CalcEditFragment;", "launchedFromCalculator", "", "mPostureDegree", "", "getMPostureDegree", "()I", "setMPostureDegree", "(I)V", "toCtaLauncher", "Landroidx/activity/result/ActivityResultLauncher;", "Landroid/content/Intent;", "onCreate", "", "savedInstanceState", "Landroid/os/Bundle;", "onNewIntent", "intent", "dispatchGenericMotionEvent", "ev", "Landroid/view/MotionEvent;", "preprocessExternalCalculatorJump", "initFragment", "onWindowFocusChanged", "hasFocus", "onDestroy", "finish", "ensureShareEntityForExternalLaunch", "onResume", "initCtaLauncher", "requestCta", "Companion", "app_PhoneGlobalRelease"}, k = 1, mv = {2, 2, 0}, xi = 48)
public final class CalcEditActivity extends AppCompatActivity {

    /* JADX INFO: renamed from: Companion, reason: from kotlin metadata */
    public static final Companion INSTANCE = new Companion(null);
    private static final String EXTRA_EXTERNAL_LAUNCH = "external_launch";
    private static final String EXTRA_INFINITE_CANVAS = "infinite_canvas";
    private static final String EXTRA_LIMITED_TOOLBAR = "limited_toolbar";
    private static final String TAG = "CreationEditActivity";
    private CalcEditFragment calcEditFragment;
    private boolean launchedFromCalculator;
    private int mPostureDegree;
    private ActivityResultLauncher<Intent> toCtaLauncher;

    /* JADX INFO: compiled from: CalcEditActivity.kt */
    @Metadata(d1 = {"\u0000 \n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\t\b\u0002¢\u0006\u0004\b\u0002\u0010\u0003J\u000e\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\fR\u000e\u0010\u0004\u001a\u00020\u0005X\u0082T¢\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0005X\u0082T¢\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\u0005X\u0082T¢\u0006\u0002\n\u0000R\u000e\u0010\b\u001a\u00020\u0005X\u0082T¢\u0006\u0002\n\u0000¨\u0006\r"}, d2 = {"Lcom/miui/pad/feature/notes/calc/CalcEditActivity$Companion;", "", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "()V", "TAG", "", "EXTRA_EXTERNAL_LAUNCH", "EXTRA_INFINITE_CANVAS", "EXTRA_LIMITED_TOOLBAR", "createExternalLaunchIntent", "Landroid/content/Intent;", CoreConstants.CONTEXT_SCOPE_VALUE, "Landroid/content/Context;", "app_PhoneGlobalRelease"}, k = 1, mv = {2, 2, 0}, xi = 48)
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }

        public final Intent createExternalLaunchIntent(Context context) {
            Intrinsics.checkNotNullParameter(context, "context");
            Intent intent = new Intent(context, (Class<?>) CreationEditActivity.class);
            intent.putExtra(CalcEditActivity.EXTRA_EXTERNAL_LAUNCH, true);
            intent.putExtra(CalcEditActivity.EXTRA_INFINITE_CANVAS, true);
            intent.putExtra(CalcEditActivity.EXTRA_LIMITED_TOOLBAR, true);
            return intent;
        }
    }

    public final int getMPostureDegree() {
        return this.mPostureDegree;
    }

    public final void setMPostureDegree(int i) {
        this.mPostureDegree = i;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // miuix.appcompat.app.AppCompatActivity, androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    public void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.NavigatorActivityTheme);
        super.onCreate(savedInstanceState);
        preprocessExternalCalculatorJump(getIntent());
        initCtaLauncher();
        if (savedInstanceState == null) {
            requestCta();
        }
        setContentView(R.layout.activity_creation_edit);
        ensureShareEntityForExternalLaunch();
        MiuiStylusPosture.init(getApplicationContext());
        CalcTransferManager.INSTANCE.getInstance().getCalcStatus(new CalcTransferManager.CalcLifeListener() { // from class: com.miui.pad.feature.notes.calc.CalcEditActivity.onCreate.1
            @Override // com.miui.pad.feature.notes.calc.CalcTransferManager.CalcLifeListener
            public void onCalcDestroy() {
                CalcEditActivity.this.initFragment();
            }
        });
    }

    @Override // androidx.activity.ComponentActivity, android.app.Activity
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        requestCta();
    }

    @Override // android.app.Activity, android.view.Window.Callback
    public boolean dispatchGenericMotionEvent(MotionEvent ev) {
        Intrinsics.checkNotNullParameter(ev, "ev");
        if ((ev.getSource() & InputDeviceCompat.SOURCE_JOYSTICK) == 16777232 && ev.getAction() == 2) {
            int axisValue = (int) (360 * ev.getAxisValue(17));
            this.mPostureDegree = axisValue;
            PreferenceUtils.setV(this, axisValue);
            return false;
        }
        return super.dispatchGenericMotionEvent(ev);
    }

    private final void preprocessExternalCalculatorJump(Intent intent) {
        String action;
        if (intent != null) {
            try {
                action = intent.getAction();
            } catch (Exception unused) {
                return;
            }
        } else {
            action = null;
        }
        if (Intrinsics.areEqual(action, "android.intent.action.VIEW")) {
            Uri data = intent.getData();
            boolean z = data != null && TextUtils.equals(data.getScheme(), "minote") && TextUtils.equals(data.getHost(), "com.miui.notes") && TextUtils.equals(data.getPath(), "/calcHandWrite");
            ComponentName callingActivity = getCallingActivity();
            boolean zVerifyCalculatorJumpSource = JumpVerificationUtils.verifyCalculatorJumpSource(callingActivity != null ? callingActivity.getPackageName() : null);
            if (z && zVerifyCalculatorJumpSource) {
                intent.putExtra(EXTRA_EXTERNAL_LAUNCH, true);
                intent.putExtra(EXTRA_LIMITED_TOOLBAR, true);
                intent.putExtra(EXTRA_INFINITE_CANVAS, true);
                this.launchedFromCalculator = true;
                return;
            }
            finish();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void initFragment() {
        FragmentManager supportFragmentManager = getSupportFragmentManager();
        Intrinsics.checkNotNullExpressionValue(supportFragmentManager, "getSupportFragmentManager(...)");
        CalcEditFragment calcEditFragment = (CalcEditFragment) supportFragmentManager.findFragmentById(R.id.editor_content);
        this.calcEditFragment = calcEditFragment;
        if (calcEditFragment == null) {
            CalcEditFragment calcEditFragment2 = new CalcEditFragment();
            Bundle bundle = new Bundle();
            bundle.putInt("isCreation", 1);
            calcEditFragment2.setArguments(bundle);
            this.calcEditFragment = calcEditFragment2;
            FragmentTransaction fragmentTransactionBeginTransaction = supportFragmentManager.beginTransaction();
            Intrinsics.checkNotNullExpressionValue(fragmentTransactionBeginTransaction, "beginTransaction(...)");
            CalcEditFragment calcEditFragment3 = this.calcEditFragment;
            Intrinsics.checkNotNull(calcEditFragment3);
            fragmentTransactionBeginTransaction.add(R.id.editor_content, calcEditFragment3);
            fragmentTransactionBeginTransaction.commit();
        }
    }

    @Override // android.app.Activity, android.view.Window.Callback
    public void onWindowFocusChanged(boolean hasFocus) {
        CalcEditFragment calcEditFragment;
        super.onWindowFocusChanged(hasFocus);
        if (!hasFocus || (calcEditFragment = this.calcEditFragment) == null) {
            return;
        }
        calcEditFragment.onWindowFocused();
    }

    @Override // miuix.appcompat.app.AppCompatActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    protected void onDestroy() {
        super.onDestroy();
        PenEngine.setIsEmptyCreationFromCreate(true);
        MiuiStylusPosture.destroy();
        MiuiTouchFilmUtils.onDestroy();
    }

    @Override // miuix.appcompat.app.AppCompatActivity, android.app.Activity
    public void finish() {
        if (this.launchedFromCalculator) {
            setResult(-1);
        }
        super.finish();
    }

    private final void ensureShareEntityForExternalLaunch() {
        Application application = getApplication();
        NoteApp noteApp = application instanceof NoteApp ? (NoteApp) application : null;
        if (noteApp == null) {
            return;
        }
        ShareViewModel globalSharedViewModel = noteApp.getGlobalSharedViewModel();
        if (globalSharedViewModel.getShareEntityData().getValue() == null) {
            globalSharedViewModel.getShareEntityData().postValue(new NoteLoadDataEntity(-2L, 0L, "", 3, System.currentTimeMillis(), false, null));
        }
    }

    @Override // androidx.fragment.app.FragmentActivity, android.app.Activity
    protected void onResume() {
        super.onResume();
        MiuiTouchFilmUtils.init(getApplicationContext(), null);
    }

    private final void initCtaLauncher() {
        ActivityResultLauncher<Intent> activityResultLauncherRegisterForActivityResult = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback() { // from class: com.miui.pad.feature.notes.calc.CalcEditActivity$$ExternalSyntheticLambda0
            @Override // androidx.activity.result.ActivityResultCallback
            public final void onActivityResult(Object obj) {
                CalcEditActivity.initCtaLauncher$lambda$2(this.f$0, (ActivityResult) obj);
            }
        });
        Intrinsics.checkNotNullExpressionValue(activityResultLauncherRegisterForActivityResult, "registerForActivityResult(...)");
        this.toCtaLauncher = activityResultLauncherRegisterForActivityResult;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void initCtaLauncher$lambda$2(CalcEditActivity calcEditActivity, ActivityResult result) {
        Intrinsics.checkNotNullParameter(result, "result");
        int resultCode = result.getResultCode();
        if (resultCode == -3) {
            ActivityResultLauncher<Intent> activityResultLauncher = calcEditActivity.toCtaLauncher;
            if (activityResultLauncher == null) {
                Intrinsics.throwUninitializedPropertyAccessException("toCtaLauncher");
                activityResultLauncher = null;
            }
            PermissionNewUtils.showCtaDialog(activityResultLauncher);
            return;
        }
        if (resultCode != 1) {
            if (resultCode != 666) {
                return;
            }
            calcEditActivity.finish();
        } else {
            com.miui.notes.tool.PreferenceUtils.setFirstHandle(false, calcEditActivity.getApplicationContext());
            com.miui.notes.tool.PreferenceUtils.setCTAAccepted(true, calcEditActivity.getApplicationContext());
            com.miui.notes.tool.PreferenceUtils.setAllowNetwork(BaseNoteApp.INSTANCE.getInstance(), true);
        }
    }

    private final void requestCta() {
        try {
            if (!com.miui.notes.tool.PreferenceUtils.isCTAAccepted(getApplicationContext()) && PermissionUtils.supportNewPermissionStyle() && com.miui.notes.tool.PreferenceUtils.isFirstHandle(getApplicationContext())) {
                ActivityResultLauncher<Intent> activityResultLauncher = this.toCtaLauncher;
                if (activityResultLauncher == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("toCtaLauncher");
                    activityResultLauncher = null;
                }
                PermissionNewUtils.showCtaDialog(activityResultLauncher);
            }
        } catch (Exception unused) {
        }
    }
}
