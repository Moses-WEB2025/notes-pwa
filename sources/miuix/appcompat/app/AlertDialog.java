package miuix.appcompat.app;

import android.app.Activity;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.DialogInterface;
import android.content.res.Configuration;
import android.database.Cursor;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.SystemClock;
import android.text.TextUtils;
import android.util.Log;
import android.util.TypedValue;
import android.view.ContextThemeWrapper;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatDialog;
import androidx.arch.core.executor.ArchTaskExecutor;
import androidx.arch.core.executor.DefaultTaskExecutor;
import androidx.arch.core.executor.TaskExecutor;
import androidx.core.view.ViewCompat;
import androidx.lifecycle.LifecycleOwner;
import java.lang.reflect.InvocationTargetException;
import miuix.appcompat.R;
import miuix.appcompat.internal.widget.DialogParentPanel2;
import miuix.appcompat.widget.DialogAnimHelper;
import miuix.autodensity.DensityUtil;
import miuix.core.util.EnvStateManager;
import miuix.core.util.RomUtils;
import miuix.internal.widget.ActionSheet;
import miuix.reflect.ReflectionHelper;
import miuix.view.HapticCompat;
import miuix.view.HapticFeedbackConstants;

/* JADX INFO: loaded from: classes5.dex */
public class AlertDialog extends AppCompatDialog implements DialogInterface {
    public static final int[] DIALOG_CONTENT_LAYOUT = {R.layout.miuix_appcompat_alert_dialog_content, R.layout.miuix_appcompat_alert_dialog_content_land};
    public static final String KEY_BUTTON_CLICK_AUTO_DISMISSIBLE = "BUTTON_CLICK_AUTO_DISMISSIBLE";
    private static final String TAG = "MiuixAlertDialog";
    final AlertController mAlert;
    private Runnable mCheckDismissAction;
    protected LifecycleOwnerCompat mLifecycleOwnerCompat;
    protected DialogAnimHelper.OnDismiss mOnDismiss;
    private CharSequence mShowDescription;

    public interface OnConfigurationChangedListener {
        void onConfigurationChanged(AppCompatDialog appCompatDialog, View view, Configuration configuration);
    }

    public interface OnDialogLayoutReloadListener {
        void onLayoutReload();
    }

    public interface OnDialogShowAnimListener {
        void onShowAnimComplete();

        void onShowAnimStart();
    }

    public interface OnPanelSizeChangedListener {
        void onPanelSizeChanged(AlertDialog alertDialog, DialogParentPanel2 dialogParentPanel2);
    }

    protected void onLayoutReload() {
    }

    protected AlertDialog(Context context) {
        this(context, 0);
    }

    protected AlertDialog(Context context, int i) {
        super(context, resolveDialogTheme(context, i));
        this.mCheckDismissAction = null;
        this.mOnDismiss = new DialogAnimHelper.OnDismiss() { // from class: miuix.appcompat.app.AlertDialog$$ExternalSyntheticLambda0
            @Override // miuix.appcompat.widget.DialogAnimHelper.OnDismiss
            public final void end() {
                this.f$0.m4216lambda$new$2$miuixappcompatappAlertDialog();
            }
        };
        Context context2 = parseContext(context);
        if (DensityUtil.findAutoDensityContextWrapper(context2) != null) {
            EnvStateManager.removeInfoOfContext(context);
        }
        this.mAlert = new AlertController(context2, this, getWindow());
        if (this instanceof LifecycleOwner) {
            this.mLifecycleOwnerCompat = new LifecycleOwnerCompat();
        }
        this.mShowDescription = context.getResources().getString(R.string.miuix_appcompat_show_dialog_description);
        prepareCheckDismissAction();
    }

    private void prepareCheckDismissAction() {
        final View decorView;
        Window window = getWindow();
        if (window == null || (decorView = window.getDecorView()) == null) {
            return;
        }
        this.mCheckDismissAction = new Runnable() { // from class: miuix.appcompat.app.AlertDialog$$ExternalSyntheticLambda2
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.m4217x53ef5a33(decorView);
            }
        };
    }

    /* JADX INFO: renamed from: lambda$prepareCheckDismissAction$0$miuix-appcompat-app-AlertDialog, reason: not valid java name */
    /* synthetic */ void m4217x53ef5a33(View view) {
        boolean z;
        if (this.mAlert.mIsDebugEnabled) {
            Log.d(TAG, "prepareCheckDismissAction: dialog isShowing = " + isShowing() + ", decorView.isAttachedToWindow = " + view.isAttachedToWindow());
        }
        if (view.isAttachedToWindow()) {
            super.dismiss();
            z = true;
        } else {
            z = false;
        }
        if (this.mAlert.mIsDebugEnabled && z) {
            Log.d(TAG, "prepareCheckDismissAction: ==> run super.dismiss()");
        }
    }

    protected AlertDialog(Context context, boolean z, DialogInterface.OnCancelListener onCancelListener) {
        this(context, 0);
        setCancelable(z);
        setOnCancelListener(onCancelListener);
    }

    private Context parseContext(Context context) {
        if (context == null) {
            return getContext();
        }
        return context.getClass() == ContextThemeWrapper.class ? context : getContext();
    }

    static int resolveDialogTheme(Context context, int i) {
        if (((i >>> 24) & 255) >= 1) {
            return i;
        }
        TypedValue typedValue = new TypedValue();
        context.getTheme().resolveAttribute(R.attr.miuiAlertDialogTheme, typedValue, true);
        return typedValue.resourceId;
    }

    public Button getButton(int i) {
        return this.mAlert.getButton(i);
    }

    public ListView getListView() {
        return this.mAlert.getListView();
    }

    @Override // androidx.appcompat.app.AppCompatDialog, android.app.Dialog
    public void setTitle(CharSequence charSequence) {
        this.mAlert.setTitle(charSequence);
    }

    public void setCustomTitle(View view) {
        this.mAlert.setCustomTitle(view);
    }

    public void setMessage(CharSequence charSequence) {
        this.mAlert.setMessage(charSequence);
    }

    public TextView getMessageView() {
        return this.mAlert.getMessageView();
    }

    public void setView(View view) {
        this.mAlert.setView(view);
    }

    public void replaceView(int i) {
        replaceView(i, true);
    }

    public void replaceView(int i, boolean z) {
        this.mAlert.replaceView(i, z);
    }

    public void replaceView(View view) {
        replaceView(view, true);
    }

    public void replaceView(View view, boolean z) {
        this.mAlert.replaceView(view, z);
    }

    public void setButton(int i, CharSequence charSequence, Message message) {
        this.mAlert.setButton(i, charSequence, null, message);
    }

    public void setButton(int i, CharSequence charSequence, DialogInterface.OnClickListener onClickListener) {
        this.mAlert.setButton(i, charSequence, onClickListener, null);
    }

    public void addExtraButton(CharSequence charSequence, int i, Message message) {
        this.mAlert.addExtraButton(new AlertController.ButtonInfo(charSequence, i, message));
    }

    public void addExtraButton(CharSequence charSequence, int i, DialogInterface.OnClickListener onClickListener, int i2) {
        this.mAlert.addExtraButton(new AlertController.ButtonInfo(charSequence, i, onClickListener, i2));
    }

    public void clearExtraButton() {
        this.mAlert.clearExtraButton();
    }

    public void setIcon(int i) {
        this.mAlert.setIcon(i);
    }

    public void setIcon(Drawable drawable) {
        this.mAlert.setIcon(drawable);
    }

    public void setUseSmallIcon(boolean z) {
        this.mAlert.setUseSmallIcon(z);
    }

    public void setIconSize(int i, int i2) {
        this.mAlert.setIconSize(i, i2);
    }

    public void setHapticFeedbackEnabled(boolean z) {
        this.mAlert.mHapticFeedbackEnabled = z;
    }

    public void setIconAttribute(int i) {
        TypedValue typedValue = new TypedValue();
        getContext().getTheme().resolveAttribute(i, typedValue, true);
        this.mAlert.setIcon(typedValue.resourceId);
    }

    public void setPanelSizeChangedListener(OnPanelSizeChangedListener onPanelSizeChangedListener) {
        this.mAlert.setPanelSizeChangedListener(onPanelSizeChangedListener);
    }

    public void setConfigurationChangedListener(OnConfigurationChangedListener onConfigurationChangedListener) {
        this.mAlert.setConfigurationChangedListener(onConfigurationChangedListener);
    }

    @Override // androidx.appcompat.app.AppCompatDialog, androidx.activity.ComponentDialog, android.app.Dialog
    protected void onCreate(Bundle bundle) {
        LifecycleOwnerCompat lifecycleOwnerCompat;
        if (isSystemSpecialUiThread() && (lifecycleOwnerCompat = this.mLifecycleOwnerCompat) != null) {
            lifecycleOwnerCompat.onCreate();
        }
        if (this.mAlert.isDialogImmersive() || !this.mAlert.mEnableEnterAnim) {
            getWindow().setWindowAnimations(0);
        }
        super.onCreate(bundle);
        this.mAlert.installContent(bundle);
    }

    protected void superOnCreate(Bundle bundle) {
        super.onCreate(bundle);
    }

    public boolean isChecked() {
        return this.mAlert.isChecked();
    }

    @Override // androidx.appcompat.app.AppCompatDialog, android.app.Dialog, android.view.Window.Callback
    public boolean dispatchKeyEvent(KeyEvent keyEvent) {
        if (this.mAlert.dispatchKeyEvent(keyEvent)) {
            return true;
        }
        return super.dispatchKeyEvent(keyEvent);
    }

    public boolean miuixSuperDispatchKeyEvent(KeyEvent keyEvent) {
        return super.dispatchKeyEvent(keyEvent);
    }

    @Override // androidx.activity.ComponentDialog, android.app.Dialog
    protected void onStart() {
        super.onStart();
        this.mAlert.onStart();
    }

    protected void superOnStart() {
        super.onStart();
    }

    @Override // androidx.appcompat.app.AppCompatDialog, androidx.activity.ComponentDialog, android.app.Dialog
    protected void onStop() {
        LifecycleOwnerCompat lifecycleOwnerCompat;
        LifecycleOwnerCompat lifecycleOwnerCompat2;
        if (isSystemSpecialUiThread() && (lifecycleOwnerCompat2 = this.mLifecycleOwnerCompat) != null) {
            lifecycleOwnerCompat2.onStopBefore();
        }
        super.onStop();
        this.mAlert.onStop();
        if (!isSystemSpecialUiThread() || (lifecycleOwnerCompat = this.mLifecycleOwnerCompat) == null) {
            return;
        }
        lifecycleOwnerCompat.onStopAfter();
    }

    protected void superOnStop() {
        super.onStop();
    }

    @Override // android.app.Dialog
    public void show() {
        removeCheckDismissAction("show");
        this.mAlert.mShowUpTimeMillis = SystemClock.uptimeMillis();
        super.show();
        if (getWindow() == null || this.mAlert.isDialogImmersive()) {
            return;
        }
        getWindow().getDecorView().postDelayed(new Runnable() { // from class: miuix.appcompat.app.AlertDialog$$ExternalSyntheticLambda3
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.m4218lambda$show$1$miuixappcompatappAlertDialog();
            }
        }, this.mAlert.mNonImmersiveDialogShowAnimDuration);
    }

    /* JADX INFO: renamed from: lambda$show$1$miuix-appcompat-app-AlertDialog, reason: not valid java name */
    /* synthetic */ void m4218lambda$show$1$miuixappcompatappAlertDialog() {
        if (this.mAlert.hasPendingDismiss()) {
            dismiss();
        }
    }

    public void superShow() {
        super.show();
    }

    public static class Builder {
        private final AlertController.AlertParams P;
        private boolean mActionSheetEnabled;
        private final int mTheme;

        public Builder(Context context) {
            this(context, AlertDialog.resolveDialogTheme(context, 0));
        }

        public Builder(Context context, int i) {
            this.mActionSheetEnabled = false;
            this.P = new AlertController.AlertParams(new ContextThemeWrapper(context, AlertDialog.resolveDialogTheme(context, i)));
            this.mTheme = i;
        }

        public Context getContext() {
            return this.P.mContext;
        }

        public Builder setTitle(int i) {
            AlertController.AlertParams alertParams = this.P;
            alertParams.mTitle = alertParams.mContext.getText(i);
            return this;
        }

        public Builder setHapticFeedbackEnabled(boolean z) {
            this.P.mHapticFeedbackEnabled = z;
            return this;
        }

        public Builder setTitle(CharSequence charSequence) {
            this.P.mTitle = charSequence;
            return this;
        }

        public Builder setCustomTitle(View view) {
            this.P.mCustomTitleView = view;
            return this;
        }

        public Builder setMessage(int i) {
            AlertController.AlertParams alertParams = this.P;
            alertParams.mMessage = alertParams.mContext.getText(i);
            return this;
        }

        public Builder setMessage(CharSequence charSequence) {
            this.P.mMessage = charSequence;
            return this;
        }

        public Builder setComment(int i) {
            AlertController.AlertParams alertParams = this.P;
            alertParams.mComment = alertParams.mContext.getText(i);
            return this;
        }

        public Builder setComment(CharSequence charSequence) {
            this.P.mComment = charSequence;
            return this;
        }

        public Builder setCheckBox(boolean z, CharSequence charSequence) {
            this.P.mIsChecked = z;
            this.P.mCheckBoxMessage = charSequence;
            return this;
        }

        public Builder setIcon(int i) {
            this.P.mIconId = i;
            return this;
        }

        public Builder setIcon(Drawable drawable) {
            this.P.mIcon = drawable;
            return this;
        }

        public Builder setIconAttribute(int i) {
            TypedValue typedValue = new TypedValue();
            this.P.mContext.getTheme().resolveAttribute(i, typedValue, true);
            this.P.mIconId = typedValue.resourceId;
            return this;
        }

        public Builder useSmallIcon(boolean z) {
            this.P.mSmallIcon = z;
            return this;
        }

        public Builder setIconSize(int i, int i2) {
            this.P.iconWidth = i;
            this.P.iconHeight = i2;
            return this;
        }

        public Builder setPositiveButton(int i, DialogInterface.OnClickListener onClickListener) {
            AlertController.AlertParams alertParams = this.P;
            alertParams.mPositiveButtonText = alertParams.mContext.getText(i);
            this.P.mPositiveButtonListener = onClickListener;
            return this;
        }

        public Builder setPositiveButton(CharSequence charSequence, DialogInterface.OnClickListener onClickListener) {
            this.P.mPositiveButtonText = charSequence;
            this.P.mPositiveButtonListener = onClickListener;
            return this;
        }

        public Builder setNegativeButton(int i, DialogInterface.OnClickListener onClickListener) {
            AlertController.AlertParams alertParams = this.P;
            alertParams.mNegativeButtonText = alertParams.mContext.getText(i);
            this.P.mNegativeButtonListener = onClickListener;
            return this;
        }

        public Builder setNegativeButton(CharSequence charSequence, DialogInterface.OnClickListener onClickListener) {
            this.P.mNegativeButtonText = charSequence;
            this.P.mNegativeButtonListener = onClickListener;
            return this;
        }

        public Builder setNeutralButton(int i, DialogInterface.OnClickListener onClickListener) {
            AlertController.AlertParams alertParams = this.P;
            alertParams.mNeutralButtonText = alertParams.mContext.getText(i);
            this.P.mNeutralButtonListener = onClickListener;
            return this;
        }

        public Builder setNeutralButton(CharSequence charSequence, DialogInterface.OnClickListener onClickListener) {
            this.P.mNeutralButtonText = charSequence;
            this.P.mNeutralButtonListener = onClickListener;
            return this;
        }

        public Builder addButton(CharSequence charSequence, DialogInterface.OnClickListener onClickListener, int i) {
            this.P.mExtraButtonList.add(new AlertController.ButtonInfo(charSequence, android.R.attr.buttonBarButtonStyle, onClickListener, i));
            return this;
        }

        public Builder addPositiveButton(CharSequence charSequence, DialogInterface.OnClickListener onClickListener, int i) {
            this.P.mExtraButtonList.add(new AlertController.ButtonInfo(charSequence, android.R.attr.buttonBarPositiveButtonStyle, onClickListener, i));
            return this;
        }

        public Builder addNegativeButton(CharSequence charSequence, DialogInterface.OnClickListener onClickListener, int i) {
            this.P.mExtraButtonList.add(new AlertController.ButtonInfo(charSequence, android.R.attr.buttonBarNegativeButtonStyle, onClickListener, i));
            return this;
        }

        public Builder addNeutralButton(CharSequence charSequence, DialogInterface.OnClickListener onClickListener, int i) {
            this.P.mExtraButtonList.add(new AlertController.ButtonInfo(charSequence, android.R.attr.buttonBarNeutralButtonStyle, onClickListener, i));
            return this;
        }

        public Builder clearButtons() {
            this.P.mExtraButtonList.clear();
            return this;
        }

        public Builder setCancelable(boolean z) {
            this.P.mCancelable = z;
            return this;
        }

        public Builder setOnCancelListener(DialogInterface.OnCancelListener onCancelListener) {
            this.P.mOnCancelListener = onCancelListener;
            return this;
        }

        public Builder setOnDismissListener(DialogInterface.OnDismissListener onDismissListener) {
            this.P.mOnDismissListener = onDismissListener;
            return this;
        }

        public Builder setOnShowListener(DialogInterface.OnShowListener onShowListener) {
            this.P.mOnShowListener = onShowListener;
            return this;
        }

        public Builder setOnKeyListener(DialogInterface.OnKeyListener onKeyListener) {
            this.P.mOnKeyListener = onKeyListener;
            return this;
        }

        public Builder setItems(int i, DialogInterface.OnClickListener onClickListener) {
            if (RomUtils.getHyperOsVersion() > 2) {
                this.mActionSheetEnabled = true;
            }
            AlertController.AlertParams alertParams = this.P;
            alertParams.mItems = alertParams.mContext.getResources().getTextArray(i);
            this.P.mOnClickListener = onClickListener;
            return this;
        }

        public Builder setItems(CharSequence[] charSequenceArr, DialogInterface.OnClickListener onClickListener) {
            if (RomUtils.getHyperOsVersion() > 2) {
                this.mActionSheetEnabled = true;
            }
            this.P.mItems = charSequenceArr;
            this.P.mOnClickListener = onClickListener;
            return this;
        }

        public Builder setItemsAccessibility(AccessibilityDelegateProvider accessibilityDelegateProvider) {
            this.P.mItemsProvider = accessibilityDelegateProvider;
            return this;
        }

        public Builder setAdapter(ListAdapter listAdapter, DialogInterface.OnClickListener onClickListener) {
            this.P.mAdapter = listAdapter;
            this.P.mOnClickListener = onClickListener;
            return this;
        }

        public Builder setCursor(Cursor cursor, DialogInterface.OnClickListener onClickListener, String str) {
            this.P.mCursor = cursor;
            this.P.mLabelColumn = str;
            this.P.mOnClickListener = onClickListener;
            return this;
        }

        public Builder setMultiChoiceItems(int i, boolean[] zArr, DialogInterface.OnMultiChoiceClickListener onMultiChoiceClickListener) {
            AlertController.AlertParams alertParams = this.P;
            alertParams.mItems = alertParams.mContext.getResources().getTextArray(i);
            this.P.mOnCheckboxClickListener = onMultiChoiceClickListener;
            this.P.mCheckedItems = zArr;
            this.P.mIsMultiChoice = true;
            return this;
        }

        public Builder setMultiChoiceItems(CharSequence[] charSequenceArr, boolean[] zArr, DialogInterface.OnMultiChoiceClickListener onMultiChoiceClickListener) {
            this.P.mItems = charSequenceArr;
            this.P.mOnCheckboxClickListener = onMultiChoiceClickListener;
            this.P.mCheckedItems = zArr;
            this.P.mIsMultiChoice = true;
            return this;
        }

        public Builder setMultiChoiceItems(Cursor cursor, String str, String str2, DialogInterface.OnMultiChoiceClickListener onMultiChoiceClickListener) {
            this.P.mCursor = cursor;
            this.P.mOnCheckboxClickListener = onMultiChoiceClickListener;
            this.P.mIsCheckedColumn = str;
            this.P.mLabelColumn = str2;
            this.P.mIsMultiChoice = true;
            return this;
        }

        public Builder setSingleChoiceItems(int i, int i2, DialogInterface.OnClickListener onClickListener) {
            AlertController.AlertParams alertParams = this.P;
            alertParams.mItems = alertParams.mContext.getResources().getTextArray(i);
            this.P.mOnClickListener = onClickListener;
            this.P.mCheckedItem = i2;
            this.P.mIsSingleChoice = true;
            return this;
        }

        public Builder setSingleChoiceItems(Cursor cursor, int i, String str, DialogInterface.OnClickListener onClickListener) {
            this.P.mCursor = cursor;
            this.P.mOnClickListener = onClickListener;
            this.P.mCheckedItem = i;
            this.P.mLabelColumn = str;
            this.P.mIsSingleChoice = true;
            return this;
        }

        public Builder setSingleChoiceItems(CharSequence[] charSequenceArr, int i, DialogInterface.OnClickListener onClickListener) {
            this.P.mItems = charSequenceArr;
            this.P.mOnClickListener = onClickListener;
            this.P.mCheckedItem = i;
            this.P.mIsSingleChoice = true;
            return this;
        }

        public Builder setSingleChoiceItems(ListAdapter listAdapter, int i, DialogInterface.OnClickListener onClickListener) {
            this.P.mAdapter = listAdapter;
            this.P.mOnClickListener = onClickListener;
            this.P.mCheckedItem = i;
            this.P.mIsSingleChoice = true;
            return this;
        }

        public Builder setOnItemSelectedListener(AdapterView.OnItemSelectedListener onItemSelectedListener) {
            this.P.mOnItemSelectedListener = onItemSelectedListener;
            return this;
        }

        public Builder setOnDialogShowAnimListener(OnDialogShowAnimListener onDialogShowAnimListener) {
            this.P.mOnDialogShowAnimListener = onDialogShowAnimListener;
            return this;
        }

        public Builder setEnableDialogImmersive(boolean z) {
            this.P.mEnableDialogImmersive = z;
            return this;
        }

        public Builder setNonImmersiveDialogHeight(int i) {
            this.P.mNonImmersiveDialogHeight = i;
            return this;
        }

        public Builder setUseLiteDrawable(boolean z) {
            this.P.mLiteVersion = z ? 999 : 0;
            return this;
        }

        @Deprecated
        public Builder setRelayoutWhenSwitchToLandscape(boolean z) {
            this.P.mPreferLandscape = z;
            return this;
        }

        public Builder setPreferLandscape(boolean z) {
            this.P.mPreferLandscape = z;
            return this;
        }

        public Builder setButtonForceVertical(boolean z) {
            this.P.mButtonForceVertical = z;
            return this;
        }

        public Builder setMinCustomVisibleHeight(int i) {
            this.P.mMinCustomVisibleHeight = i;
            return this;
        }

        public Builder setPrimaryButtonFirstEnabled(boolean z) {
            this.P.mPrimaryButtonFirstEnabled = z;
            return this;
        }

        public Builder setMaterialEnabled(boolean z) {
            this.P.mMaterialEnabled = z;
            return this;
        }

        public Builder setAsyncInflatePanelEnabled(boolean z) {
            this.P.mAsyncInflatePanelEnabled = z;
            return this;
        }

        public Builder setDiscardImeAnimEnabled(boolean z) {
            this.P.mDiscardImeAnimEnabled = z;
            return this;
        }

        public Builder setUseForceFlipCutout(boolean z) {
            this.P.mUseForceFlipCutout = z;
            return this;
        }

        public Builder setEnableEnterAnim(boolean z) {
            this.P.mEnableEnterAnim = z;
            return this;
        }

        public Builder setOnPanelSizeChangedListener(OnPanelSizeChangedListener onPanelSizeChangedListener) {
            this.P.mPanelSizeChangedListener = onPanelSizeChangedListener;
            return this;
        }

        public Builder setOnConfigurationChangedListener(OnConfigurationChangedListener onConfigurationChangedListener) {
            this.P.mConfigurationChangedListener = onConfigurationChangedListener;
            return this;
        }

        public Builder setView(int i) {
            this.P.mView = null;
            this.P.mViewLayoutResId = i;
            return this;
        }

        public Builder setView(View view) {
            this.P.mView = view;
            this.P.mViewLayoutResId = 0;
            return this;
        }

        public Builder setActionSheetEnabled(boolean z) {
            this.mActionSheetEnabled = z;
            return this;
        }

        public AlertDialog create() {
            boolean z = (this.P.mItems == null || this.P.mIsSingleChoice || this.P.mIsMultiChoice) ? false : true;
            boolean z2 = this.P.mView == null && this.P.mViewLayoutResId == 0;
            if (this.mActionSheetEnabled && z && z2) {
                return createActionSheet();
            }
            return createAlertDialog();
        }

        private AlertDialog createActionSheet() {
            ActionSheet.Builder builder = new ActionSheet.Builder(this.P.mContext);
            if (!TextUtils.isEmpty(this.P.mMessage)) {
                builder.setMessage(this.P.mMessage);
            }
            if (!TextUtils.isEmpty(this.P.mTitle)) {
                builder.setMessage(this.P.mTitle);
            }
            builder.setActionItems(this.P.mItems, this.P.mOnClickListener);
            if (!TextUtils.isEmpty(this.P.mNegativeButtonText)) {
                builder.setSeparateText(this.P.mNegativeButtonText);
            }
            if (this.P.mNegativeButtonListener != null) {
                builder.setSeparateClickListener(this.P.mNegativeButtonListener);
            }
            if (this.P.mOnDialogShowAnimListener != null) {
                builder.setShowAnimListener(this.P.mOnDialogShowAnimListener);
            }
            if (this.P.mOnShowListener != null) {
                builder.setOnShowListener(this.P.mOnShowListener);
            }
            if (this.P.mOnDismissListener != null) {
                builder.setOnDismissListener(this.P.mOnDismissListener);
            }
            if (this.P.mOnKeyListener != null) {
                builder.setOnKeyListener(this.P.mOnKeyListener);
            }
            if (this.P.mAdapter != null) {
                builder.setListViewAdapter(this.P.mAdapter);
            }
            if (this.P.mItemsProvider != null) {
                builder.setItemAccessibilityProvider(this.P.mItemsProvider);
            }
            AlertDialog alertDialog = (AlertDialog) builder.create();
            if (this.P.mCancelable) {
                alertDialog.setCanceledOnTouchOutside(true);
            }
            if (this.P.mOnCancelListener != null) {
                alertDialog.setOnCancelListener(this.P.mOnCancelListener);
            }
            if (this.P.mConfigurationChangedListener != null) {
                alertDialog.setConfigurationChangedListener(this.P.mConfigurationChangedListener);
            }
            return alertDialog;
        }

        private AlertDialog createAlertDialog() {
            AlertDialog alertDialog = new AlertDialog(this.P.mContext, this.mTheme);
            this.P.apply(alertDialog.mAlert);
            alertDialog.setCancelable(this.P.mCancelable);
            if (this.P.mCancelable) {
                alertDialog.setCanceledOnTouchOutside(true);
            }
            alertDialog.setOnCancelListener(this.P.mOnCancelListener);
            alertDialog.setOnDismissListener(this.P.mOnDismissListener);
            alertDialog.setOnShowListener(this.P.mOnShowListener);
            alertDialog.setOnShowAnimListener(this.P.mOnDialogShowAnimListener);
            if (this.P.mOnKeyListener != null) {
                alertDialog.setOnKeyListener(this.P.mOnKeyListener);
            }
            return alertDialog;
        }

        public AlertDialog show() {
            AlertDialog alertDialogCreate = create();
            alertDialogCreate.show();
            return alertDialogCreate;
        }
    }

    @Override // android.app.Dialog, android.view.Window.Callback
    public void onAttachedToWindow() {
        super.onAttachedToWindow();
        View decorView = getWindow().getDecorView();
        if (decorView != null && this.mAlert.mHapticFeedbackEnabled) {
            HapticCompat.performHapticFeedbackAsync(decorView, HapticFeedbackConstants.MIUI_ALERT, HapticFeedbackConstants.MIUI_POPUP_NORMAL);
        }
        this.mAlert.onAttachedToWindow();
        setAccessibilityDelegate(decorView);
    }

    protected void superOnAttachedToWindow() {
        super.onAttachedToWindow();
    }

    protected void setAccessibilityDelegate(View view) {
        if (view == null) {
            return;
        }
        ViewCompat.setAccessibilityPaneTitle(view, this.mShowDescription);
    }

    @Override // android.app.Dialog
    public void setCancelable(boolean z) {
        super.setCancelable(z);
        this.mAlert.setCancelable(z);
    }

    @Override // android.app.Dialog, android.view.Window.Callback
    public void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        this.mAlert.onDetachedFromWindow();
        removeCheckDismissAction("onDetachedFromWindow");
    }

    private void removeCheckDismissAction(String str) {
        View decorView;
        if (this.mAlert.mIsDebugEnabled) {
            Log.d(TAG, "removeCheckDismissAction: ==> remove action at: " + str);
        }
        Window window = getWindow();
        if (this.mCheckDismissAction == null || window == null || (decorView = window.getDecorView()) == null) {
            return;
        }
        decorView.removeCallbacks(this.mCheckDismissAction);
    }

    public void superOnDetachedFromWindow() {
        super.onDetachedFromWindow();
    }

    /* JADX INFO: renamed from: lambda$new$2$miuix-appcompat-app-AlertDialog, reason: not valid java name */
    /* synthetic */ void m4216lambda$new$2$miuixappcompatappAlertDialog() {
        View decorView;
        if (getWindow() == null || (decorView = getWindow().getDecorView()) == null || !decorView.isAttachedToWindow()) {
            return;
        }
        realDismiss();
    }

    @Override // androidx.appcompat.app.AppCompatDialog, android.app.Dialog, android.content.DialogInterface
    public void dismiss() {
        if (!this.mAlert.isDialogImmersive() && this.mAlert.isAsyncInflatePanelEnabled()) {
            this.mAlert.safeRemovePanelFromParent();
        }
        View decorView = getWindow().getDecorView();
        if (this.mAlert.isShowingAnimation()) {
            this.mAlert.setPendingDismiss(true);
            return;
        }
        this.mAlert.setPendingDismiss(false);
        if (DensityUtil.findAutoDensityContextWrapper(decorView.getContext()) != null) {
            EnvStateManager.removeInfoOfContext(decorView.getContext());
        }
        if (this.mAlert.isDialogImmersive()) {
            Activity associatedActivity = getAssociatedActivity();
            if (associatedActivity != null && associatedActivity.isFinishing()) {
                dismissIfAttachedToWindow(decorView);
                return;
            } else {
                dismissWithAnimationOrNot(decorView);
                return;
            }
        }
        dismissIfAttachedToWindow(decorView);
    }

    protected void dismissWithAnimationOrNot(View view) {
        if (view != null) {
            if (view.getHandler() != null) {
                dismissWithAnimationExistDecorView(view);
                return;
            } else {
                dismissIfAttachedToWindow(view);
                return;
            }
        }
        super.dismiss();
    }

    protected void dismissWithAnimationExistDecorView(View view) {
        if (Thread.currentThread() == view.getHandler().getLooper().getThread()) {
            this.mAlert.dismiss(this.mOnDismiss);
        } else {
            view.post(new Runnable() { // from class: miuix.appcompat.app.AlertDialog$$ExternalSyntheticLambda1
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.m4215x5ed47038();
                }
            });
        }
    }

    /* JADX INFO: renamed from: lambda$dismissWithAnimationExistDecorView$3$miuix-appcompat-app-AlertDialog, reason: not valid java name */
    /* synthetic */ void m4215x5ed47038() {
        this.mAlert.dismiss(this.mOnDismiss);
    }

    protected void dismissIfAttachedToWindow(View view) {
        if (view != null && !view.isAttachedToWindow()) {
            postCheckAndDismissSafely(view);
        } else {
            super.dismiss();
        }
    }

    private void postCheckAndDismissSafely(View view) {
        if (view == null) {
            return;
        }
        try {
            if (this.mAlert.mIsDebugEnabled) {
                Log.d(TAG, "postCheckAndDismissSafely: mCheckDismissAction = " + this.mCheckDismissAction);
            }
            Runnable runnable = this.mCheckDismissAction;
            if (runnable != null) {
                view.post(runnable);
            }
        } catch (Exception unused) {
            Log.d(TAG, "postCheckAndDismissSafely: decorView.post dismiss failed");
        }
    }

    public void realDismiss() {
        super.dismiss();
    }

    public Activity getAssociatedActivity() {
        Activity ownerActivity = getOwnerActivity();
        Context context = getContext();
        while (ownerActivity == null && context != null) {
            if (context instanceof Activity) {
                ownerActivity = (Activity) context;
            } else {
                context = context instanceof ContextWrapper ? ((ContextWrapper) context).getBaseContext() : null;
            }
        }
        return ownerActivity;
    }

    public void dismissWithoutAnimation() {
        View decorView = getWindow().getDecorView();
        if (getWindow().getDecorView().isAttachedToWindow()) {
            if (this.mAlert.isShowingAnimation()) {
                this.mAlert.setPendingDismiss(true);
                return;
            }
            this.mAlert.setPendingDismiss(false);
            if (DensityUtil.findAutoDensityContextWrapper(decorView.getContext()) != null) {
                EnvStateManager.removeInfoOfContext(decorView.getContext());
            }
            realDismiss();
        }
    }

    public void setEnableImmersive(boolean z) {
        this.mAlert.setEnableImmersive(z);
    }

    public void setNonImmersiveDialogHeight(int i) {
        this.mAlert.setNonImmersiveDialogHeight(i);
    }

    @Deprecated
    public void setRelayoutWhenSwitchToLandscape(boolean z) {
        this.mAlert.setPreferLandscape(z);
    }

    public void setPreferLandscape(boolean z) {
        this.mAlert.setPreferLandscape(z);
    }

    public void setButtonForceVertical(boolean z) {
        this.mAlert.setButtonForceVertical(z);
    }

    public void setPrimaryButtonFirstEnabled(boolean z) {
        this.mAlert.setPrimaryButtonFirstEnabled(z);
    }

    public void setMaterialEnabled(boolean z) {
        this.mAlert.setMaterialEnabled(z);
    }

    public void setMinCustomVisibleHeight(int i) {
        this.mAlert.setMinCustomVisibleHeight(i);
    }

    public void setEnableEnterAnim(boolean z) {
        this.mAlert.setEnableEnterAnim(z);
    }

    @Override // android.app.Dialog
    public void setCanceledOnTouchOutside(boolean z) {
        super.setCanceledOnTouchOutside(z);
        this.mAlert.setCanceledOnTouchOutside(z);
    }

    public void setOnShowAnimListener(OnDialogShowAnimListener onDialogShowAnimListener) {
        this.mAlert.setShowAnimListener(onDialogShowAnimListener);
    }

    public void setOnLayoutReloadListener(OnDialogLayoutReloadListener onDialogLayoutReloadListener) {
        this.mAlert.setLayoutReloadListener(onDialogLayoutReloadListener);
    }

    public void setCustomPanelSize(ViewGroup.LayoutParams layoutParams) {
        this.mAlert.setCustomPanelSize(layoutParams);
    }

    protected boolean isSystemSpecialUiThread() {
        return TextUtils.equals("android.ui", Thread.currentThread().getName()) || TextUtils.equals("android.imms", Thread.currentThread().getName()) || TextUtils.equals("system_server", Thread.currentThread().getName());
    }

    protected class LifecycleOwnerCompat {
        private Object mOriginalExecutor;
        private TaskExecutor mSpecialUiExecutor;

        protected LifecycleOwnerCompat() {
        }

        public void onCreate() {
            try {
                Object fieldValue = ReflectionHelper.getFieldValue(ArchTaskExecutor.class, ArchTaskExecutor.getInstance(), "mDelegate");
                if (fieldValue != null) {
                    this.mOriginalExecutor = fieldValue;
                }
            } catch (InvocationTargetException e) {
                Log.d("MiuixDialog", "onCreate() taskExecutor get failed InvocationTargetException " + e);
            } catch (IllegalAccessException e2) {
                Log.d("MiuixDialog", "onCreate() taskExecutor get failed IllegalAccessException " + e2);
            } catch (NoSuchMethodException e3) {
                Log.d("MiuixDialog", "onCreate() taskExecutor get failed NoSuchMethodException " + e3);
            } finally {
                this.mSpecialUiExecutor = createSpecialUiTaskExecutor();
                ArchTaskExecutor.getInstance().setDelegate(this.mSpecialUiExecutor);
            }
        }

        /* JADX WARN: Code duplicated, block: B:36:0x0095  */
        /* JADX WARN: Code restructure failed: missing block: B:11:0x0028, code lost:
        
            if (androidx.arch.core.executor.ArchTaskExecutor.getInstance().isMainThread() == false) goto L12;
         */
        /* JADX WARN: Code restructure failed: missing block: B:37:0x009e, code lost:
        
            return;
         */
        /* JADX WARN: Multi-variable type inference failed */
        /* JADX WARN: Type inference failed for: r7v10 */
        /* JADX WARN: Type inference failed for: r7v6 */
        /* JADX WARN: Type inference failed for: r7v7 */
        /* JADX WARN: Type inference failed for: r7v9 */
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        public void onStopBefore() {
            /*
                r7 = this;
                java.lang.String r0 = "MiuixDialog"
                java.lang.String r1 = "onStop() taskExecutor get failed InvocationTargetException "
                java.lang.String r2 = "onStop() taskExecutor get failed NoSuchMethodException "
                java.lang.String r3 = "onStop() taskExecutor get failed IllegalAccessException "
                java.lang.Class<androidx.arch.core.executor.ArchTaskExecutor> r4 = androidx.arch.core.executor.ArchTaskExecutor.class
                androidx.arch.core.executor.ArchTaskExecutor r5 = androidx.arch.core.executor.ArchTaskExecutor.getInstance()     // Catch: java.lang.Throwable -> L34 java.lang.reflect.InvocationTargetException -> L36 java.lang.NoSuchMethodException -> L56 java.lang.IllegalAccessException -> L76
                java.lang.String r6 = "mDelegate"
                java.lang.Object r0 = miuix.reflect.ReflectionHelper.getFieldValue(r4, r5, r6)     // Catch: java.lang.Throwable -> L34 java.lang.reflect.InvocationTargetException -> L36 java.lang.NoSuchMethodException -> L56 java.lang.IllegalAccessException -> L76
                if (r0 == 0) goto L1c
                java.lang.Object r1 = r7.mOriginalExecutor
                if (r0 == r1) goto L1c
                r7.mOriginalExecutor = r0
            L1c:
                androidx.arch.core.executor.TaskExecutor r1 = r7.mSpecialUiExecutor
                if (r0 != r1) goto L2a
                androidx.arch.core.executor.ArchTaskExecutor r0 = androidx.arch.core.executor.ArchTaskExecutor.getInstance()
                boolean r0 = r0.isMainThread()
                if (r0 != 0) goto L9e
            L2a:
                androidx.arch.core.executor.ArchTaskExecutor r0 = androidx.arch.core.executor.ArchTaskExecutor.getInstance()
                androidx.arch.core.executor.TaskExecutor r7 = r7.mSpecialUiExecutor
                r0.setDelegate(r7)
                return
            L34:
                r0 = move-exception
                goto L9f
            L36:
                r2 = move-exception
                java.lang.StringBuilder r3 = new java.lang.StringBuilder     // Catch: java.lang.Throwable -> L34
                r3.<init>(r1)     // Catch: java.lang.Throwable -> L34
                java.lang.StringBuilder r1 = r3.append(r2)     // Catch: java.lang.Throwable -> L34
                java.lang.String r1 = r1.toString()     // Catch: java.lang.Throwable -> L34
                android.util.Log.d(r0, r1)     // Catch: java.lang.Throwable -> L34
                androidx.arch.core.executor.TaskExecutor r0 = r7.mSpecialUiExecutor
                if (r0 != 0) goto L95
                androidx.arch.core.executor.ArchTaskExecutor r0 = androidx.arch.core.executor.ArchTaskExecutor.getInstance()
                boolean r0 = r0.isMainThread()
                if (r0 != 0) goto L9e
                goto L95
            L56:
                r1 = move-exception
                java.lang.StringBuilder r3 = new java.lang.StringBuilder     // Catch: java.lang.Throwable -> L34
                r3.<init>(r2)     // Catch: java.lang.Throwable -> L34
                java.lang.StringBuilder r1 = r3.append(r1)     // Catch: java.lang.Throwable -> L34
                java.lang.String r1 = r1.toString()     // Catch: java.lang.Throwable -> L34
                android.util.Log.d(r0, r1)     // Catch: java.lang.Throwable -> L34
                androidx.arch.core.executor.TaskExecutor r0 = r7.mSpecialUiExecutor
                if (r0 != 0) goto L95
                androidx.arch.core.executor.ArchTaskExecutor r0 = androidx.arch.core.executor.ArchTaskExecutor.getInstance()
                boolean r0 = r0.isMainThread()
                if (r0 != 0) goto L9e
                goto L95
            L76:
                r1 = move-exception
                java.lang.StringBuilder r2 = new java.lang.StringBuilder     // Catch: java.lang.Throwable -> L34
                r2.<init>(r3)     // Catch: java.lang.Throwable -> L34
                java.lang.StringBuilder r1 = r2.append(r1)     // Catch: java.lang.Throwable -> L34
                java.lang.String r1 = r1.toString()     // Catch: java.lang.Throwable -> L34
                android.util.Log.d(r0, r1)     // Catch: java.lang.Throwable -> L34
                androidx.arch.core.executor.TaskExecutor r0 = r7.mSpecialUiExecutor
                if (r0 != 0) goto L95
                androidx.arch.core.executor.ArchTaskExecutor r0 = androidx.arch.core.executor.ArchTaskExecutor.getInstance()
                boolean r0 = r0.isMainThread()
                if (r0 != 0) goto L9e
            L95:
                androidx.arch.core.executor.ArchTaskExecutor r0 = androidx.arch.core.executor.ArchTaskExecutor.getInstance()
                androidx.arch.core.executor.TaskExecutor r7 = r7.mSpecialUiExecutor
                r0.setDelegate(r7)
            L9e:
                return
            L9f:
                androidx.arch.core.executor.TaskExecutor r1 = r7.mSpecialUiExecutor
                if (r1 != 0) goto Lad
                androidx.arch.core.executor.ArchTaskExecutor r1 = androidx.arch.core.executor.ArchTaskExecutor.getInstance()
                boolean r1 = r1.isMainThread()
                if (r1 != 0) goto Lb6
            Lad:
                androidx.arch.core.executor.ArchTaskExecutor r1 = androidx.arch.core.executor.ArchTaskExecutor.getInstance()
                androidx.arch.core.executor.TaskExecutor r7 = r7.mSpecialUiExecutor
                r1.setDelegate(r7)
            Lb6:
                throw r0
            */
            throw new UnsupportedOperationException("Method not decompiled: miuix.appcompat.app.AlertDialog.LifecycleOwnerCompat.onStopBefore():void");
        }

        public void onStopAfter() {
            if (this.mOriginalExecutor instanceof TaskExecutor) {
                ArchTaskExecutor.getInstance().setDelegate((TaskExecutor) this.mOriginalExecutor);
            }
        }

        private TaskExecutor createSpecialUiTaskExecutor() {
            return new DefaultTaskExecutor() { // from class: miuix.appcompat.app.AlertDialog.LifecycleOwnerCompat.1
                private final Object mLock = new Object();
                private volatile Handler mSpecialMainHandler;

                @Override // androidx.arch.core.executor.DefaultTaskExecutor, androidx.arch.core.executor.TaskExecutor
                public boolean isMainThread() {
                    return true;
                }

                @Override // androidx.arch.core.executor.DefaultTaskExecutor, androidx.arch.core.executor.TaskExecutor
                public void postToMainThread(Runnable runnable) {
                    if (this.mSpecialMainHandler == null) {
                        synchronized (this.mLock) {
                            if (this.mSpecialMainHandler == null) {
                                this.mSpecialMainHandler = createAsync(Looper.myLooper());
                            }
                        }
                    }
                    this.mSpecialMainHandler.post(runnable);
                }

                private Handler createAsync(Looper looper) {
                    return Handler.createAsync(looper);
                }
            };
        }
    }
}
