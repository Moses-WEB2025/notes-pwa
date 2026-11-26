package com.miui.libhandwritingpreview.model;

import android.app.Activity;
import android.content.Context;
import android.content.res.Configuration;
import android.text.Editable;
import android.text.SpannableStringBuilder;
import android.text.TextWatcher;
import android.text.style.ForegroundColorSpan;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import ch.qos.logback.core.CoreConstants;
import com.market.sdk.reflect.ReflectUtilsForMiui;
import com.miui.creation.common.R;
import com.miui.handwriting.common.softinputhelper.KeyboardHelper;
import com.miui.handwriting.common.softinputhelper.KeyboardMarginProcessor;
import com.miui.handwriting.common.tools.ScreenModeUtils;
import com.miui.handwriting.common.view.page.HeaderView;
import com.miui.handwriting.common.view.thumbnail.ThumbnailRecyclerManager;
import com.miui.libhandwritingpage.newbean.beanPageInfo.PagePreviewData;
import com.miui.libhandwritingpage.utils.UIUtils;
import com.miui.libhandwritingpage.utils.Utils;
import com.miui.libhandwritingpreview.view.CreationPaintPreviewLayout;
import com.miui.libhandwritingpreview.view.CreationShieldPrePageInkView;
import defpackage.R2;
import java.lang.ref.WeakReference;
import java.util.concurrent.CopyOnWriteArrayList;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;
import miuix.appcompat.app.ProgressDialog;
import miuix.autodensity.AutoDensityConfig;
import xc.engine.api.CreationPageSDK;

/* JADX INFO: compiled from: MiuiHandWritingPreviewModel.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u0000«\u0001\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0007\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\t\n\u0002\b\n\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\b\u0005*\u0001O\u0018\u0000 R2\u00020\u0001:\u0001RB\u0011\u0012\b\u0010\u0002\u001a\u0004\u0018\u00010\u0003¢\u0006\u0004\b\u0004\u0010\u0005J\u0006\u0010\"\u001a\u00020#J\u0010\u0010$\u001a\u00020#2\u0006\u0010%\u001a\u00020!H\u0002J\n\u0010&\u001a\u0004\u0018\u00010'H\u0002J,\u0010(\u001a\u0004\u0018\u00010)2\b\u0010*\u001a\u0004\u0018\u00010'2\b\u0010+\u001a\u0004\u0018\u00010,2\u0006\u0010-\u001a\u00020\u001c2\u0006\u0010.\u001a\u00020\u0003J\b\u0010/\u001a\u00020#H\u0002J\b\u00100\u001a\u00020#H\u0002J\b\u00101\u001a\u00020#H\u0002J\u000e\u00102\u001a\u00020#2\u0006\u00103\u001a\u000204J\u0006\u00105\u001a\u00020#J\u000e\u00106\u001a\u00020#2\u0006\u00107\u001a\u000208J\u0010\u00109\u001a\u00020#2\b\u0010:\u001a\u0004\u0018\u00010\u0016J\b\u0010;\u001a\u00020#H\u0002J\b\u0010<\u001a\u0004\u0018\u00010=J\u0018\u0010>\u001a\u00020#2\b\u0010?\u001a\u0004\u0018\u00010\n2\u0006\u0010@\u001a\u00020AJ\u0006\u0010B\u001a\u00020#J\u000e\u0010C\u001a\u00020#2\u0006\u0010@\u001a\u00020AJ\u0010\u0010D\u001a\u00020#2\b\u0010E\u001a\u0004\u0018\u00010\nJ\"\u0010F\u001a\u00020#2\b\u0010E\u001a\u0004\u0018\u00010\n2\b\u0010G\u001a\u0004\u0018\u00010\n2\u0006\u0010H\u001a\u00020\u001cJ\u0006\u0010I\u001a\u00020\nJ\u0006\u0010J\u001a\u00020#J\u0006\u0010Q\u001a\u00020#R\u001c\u0010\u0002\u001a\u0004\u0018\u00010\u0003X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0006\u0010\u0007\"\u0004\b\b\u0010\u0005R\u0010\u0010\t\u001a\u0004\u0018\u00010\nX\u0082\u000e¢\u0006\u0002\n\u0000R\u0010\u0010\u000b\u001a\u0004\u0018\u00010\fX\u0082\u000e¢\u0006\u0002\n\u0000R\u0010\u0010\r\u001a\u0004\u0018\u00010\u000eX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u000f\u001a\u00020\u0010X\u0082\u000e¢\u0006\u0002\n\u0000R\u0010\u0010\u0011\u001a\u0004\u0018\u00010\u0012X\u0082\u000e¢\u0006\u0002\n\u0000R\u0010\u0010\u0013\u001a\u0004\u0018\u00010\u0014X\u0082\u000e¢\u0006\u0002\n\u0000R\u0010\u0010\u0015\u001a\u0004\u0018\u00010\u0016X\u0082\u000e¢\u0006\u0002\n\u0000R\u0014\u0010\u0017\u001a\b\u0012\u0004\u0012\u00020\u00190\u0018X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u001a\u001a\u00020\u0010X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u001b\u001a\u00020\u001cX\u0082\u000e¢\u0006\u0002\n\u0000R\u0010\u0010\u001d\u001a\u0004\u0018\u00010\u001eX\u0082\u000e¢\u0006\u0002\n\u0000R\u0016\u0010\u001f\u001a\n\u0012\u0004\u0012\u00020!\u0018\u00010 X\u0082\u000e¢\u0006\u0002\n\u0000R\u0010\u0010K\u001a\u0004\u0018\u00010LX\u0082\u000e¢\u0006\u0002\n\u0000R\u0010\u0010M\u001a\u0004\u0018\u00010LX\u0082\u000e¢\u0006\u0002\n\u0000R\u0010\u0010N\u001a\u00020OX\u0082\u0004¢\u0006\u0004\n\u0002\u0010P¨\u0006S"}, d2 = {"Lcom/miui/libhandwritingpreview/model/MiuiHandWritingPreviewModel;", "", "miuiHandViewPreviewModel", "Lcom/miui/libhandwritingpreview/model/MiuiHandViewPreviewModel;", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "(Lcom/miui/libhandwritingpreview/model/MiuiHandViewPreviewModel;)V", "getMiuiHandViewPreviewModel", "()Lcom/miui/libhandwritingpreview/model/MiuiHandViewPreviewModel;", "setMiuiHandViewPreviewModel", "local", "", "mEditKeyboardDetector", "Lcom/miui/handwriting/common/softinputhelper/KeyboardHelper;", "mCreationPaintLayout", "Lcom/miui/libhandwritingpreview/view/CreationPaintPreviewLayout;", "mIsKeyboardVisible", "", "mShieldPageInkView", "Lcom/miui/libhandwritingpreview/view/CreationShieldPrePageInkView;", "mTitleView", "Landroid/widget/EditText;", "mOnTitleChangedListener", "Lcom/miui/handwriting/common/view/page/HeaderView$OnTitleChangedListener;", "mPreviewDataList", "Ljava/util/concurrent/CopyOnWriteArrayList;", "Lcom/miui/libhandwritingpage/newbean/beanPageInfo/PagePreviewData;", "isNightMode", "screenOrientation", "", "pasteDialog", "Lmiuix/appcompat/app/ProgressDialog;", "activityRef", "Ljava/lang/ref/WeakReference;", "Landroid/app/Activity;", "init", "", "registerActivity", "activity", "getActivityContext", "Landroid/content/Context;", "initView", "Landroid/view/View;", CoreConstants.CONTEXT_SCOPE_VALUE, "attributeSet", "Landroid/util/AttributeSet;", "defStyleAttr", "handWritingModel", "innerInitView", "initSDKCallback", "changePadding", "onConfigurationChanged", "newConfig", "Landroid/content/res/Configuration;", "initUIProperties", "updateBackgroundMargin", "scale", "", "setTitleChangedListener", "listener", "setKeyboardDetector", "getThumbnailManager", "Lcom/miui/handwriting/common/view/thumbnail/ThumbnailRecyclerManager;", "setTitleAndTime", "title", "time", "", "clearTitleCursor", "setTitleTime", "setTitle", "savingTitle", "setTitleColor", "searchStr", "color", "getTitle", "showLoadingDialog", "pasteDialogAlphaRunnable", "Ljava/lang/Runnable;", "pasteDialogDismissRunnable", "mTitleTextWatcher", "com/miui/libhandwritingpreview/model/MiuiHandWritingPreviewModel$mTitleTextWatcher$1", "Lcom/miui/libhandwritingpreview/model/MiuiHandWritingPreviewModel$mTitleTextWatcher$1;", "release", "Companion", "libHandWritingPreview_release"}, k = 1, mv = {2, 2, 0}, xi = 48)
public final class MiuiHandWritingPreviewModel {
    private static final int ALPHA_TIME = 500;
    private static final int DISMISS_TIME = 5000;
    private WeakReference<Activity> activityRef;
    private boolean isNightMode;
    private String local;
    private CreationPaintPreviewLayout mCreationPaintLayout;
    private KeyboardHelper mEditKeyboardDetector;
    private boolean mIsKeyboardVisible;
    private HeaderView.OnTitleChangedListener mOnTitleChangedListener;
    private CreationShieldPrePageInkView mShieldPageInkView;
    private EditText mTitleView;
    private MiuiHandViewPreviewModel miuiHandViewPreviewModel;
    private ProgressDialog pasteDialog;
    private final CopyOnWriteArrayList<PagePreviewData> mPreviewDataList = new CopyOnWriteArrayList<>();
    private int screenOrientation = 1;
    private Runnable pasteDialogAlphaRunnable = new Runnable() { // from class: com.miui.libhandwritingpreview.model.MiuiHandWritingPreviewModel$$ExternalSyntheticLambda1
        @Override // java.lang.Runnable
        public final void run() {
            MiuiHandWritingPreviewModel.pasteDialogAlphaRunnable$lambda$6(this.f$0);
        }
    };
    private Runnable pasteDialogDismissRunnable = new Runnable() { // from class: com.miui.libhandwritingpreview.model.MiuiHandWritingPreviewModel$$ExternalSyntheticLambda2
        @Override // java.lang.Runnable
        public final void run() {
            MiuiHandWritingPreviewModel.pasteDialogDismissRunnable$lambda$7(this.f$0);
        }
    };
    private final MiuiHandWritingPreviewModel$mTitleTextWatcher$1 mTitleTextWatcher = new TextWatcher() { // from class: com.miui.libhandwritingpreview.model.MiuiHandWritingPreviewModel$mTitleTextWatcher$1
        @Override // android.text.TextWatcher
        public void beforeTextChanged(CharSequence p0, int p1, int p2, int p3) {
        }

        @Override // android.text.TextWatcher
        public void onTextChanged(CharSequence p0, int p1, int p2, int p3) {
        }

        @Override // android.text.TextWatcher
        public void afterTextChanged(Editable s) {
            HeaderView.OnTitleChangedListener onTitleChangedListener = this.this$0.mOnTitleChangedListener;
            if (onTitleChangedListener != null) {
                onTitleChangedListener.onTitleChanged(String.valueOf(s));
            }
            CreationPaintPreviewLayout creationPaintPreviewLayout = this.this$0.mCreationPaintLayout;
            if (creationPaintPreviewLayout != null) {
                creationPaintPreviewLayout.setTitleTime(System.currentTimeMillis());
            }
        }
    };

    private final void initSDKCallback() {
    }

    /* JADX WARN: Type inference failed for: r1v5, types: [com.miui.libhandwritingpreview.model.MiuiHandWritingPreviewModel$mTitleTextWatcher$1] */
    public MiuiHandWritingPreviewModel(MiuiHandViewPreviewModel miuiHandViewPreviewModel) {
        this.miuiHandViewPreviewModel = miuiHandViewPreviewModel;
    }

    public final MiuiHandViewPreviewModel getMiuiHandViewPreviewModel() {
        return this.miuiHandViewPreviewModel;
    }

    public final void setMiuiHandViewPreviewModel(MiuiHandViewPreviewModel miuiHandViewPreviewModel) {
        this.miuiHandViewPreviewModel = miuiHandViewPreviewModel;
    }

    public final void init() {
        setKeyboardDetector();
    }

    private final void registerActivity(Activity activity) {
        this.activityRef = new WeakReference<>(activity);
    }

    private final Context getActivityContext() {
        WeakReference<Activity> weakReference = this.activityRef;
        Activity activity = weakReference != null ? weakReference.get() : null;
        if (activity == null || activity.isDestroyed() || activity.isFinishing()) {
            return null;
        }
        return activity;
    }

    public final View initView(Context context, AttributeSet attributeSet, int defStyleAttr, MiuiHandViewPreviewModel handWritingModel) {
        Intrinsics.checkNotNullParameter(handWritingModel, "handWritingModel");
        Activity activity = Utils.getActivity(context);
        Intrinsics.checkNotNullExpressionValue(activity, "getActivity(...)");
        registerActivity(activity);
        CreationPaintPreviewLayout creationPaintPreviewLayout = null;
        CreationPaintPreviewLayout creationPaintPreviewLayout2 = context != null ? new CreationPaintPreviewLayout(context, attributeSet, defStyleAttr, handWritingModel) : null;
        this.mCreationPaintLayout = creationPaintPreviewLayout2;
        if (creationPaintPreviewLayout2 != null) {
            innerInitView();
            creationPaintPreviewLayout = creationPaintPreviewLayout2;
        }
        return creationPaintPreviewLayout;
    }

    private final void innerInitView() {
        CreationPaintPreviewLayout creationPaintPreviewLayout = this.mCreationPaintLayout;
        this.mShieldPageInkView = creationPaintPreviewLayout != null ? creationPaintPreviewLayout.getPageInkView() : null;
        changePadding();
        CreationPaintPreviewLayout creationPaintPreviewLayout2 = this.mCreationPaintLayout;
        EditText titleView = creationPaintPreviewLayout2 != null ? creationPaintPreviewLayout2.getTitleView() : null;
        this.mTitleView = titleView;
        if (titleView != null) {
            titleView.addTextChangedListener(this.mTitleTextWatcher);
        }
        initSDKCallback();
    }

    private final void changePadding() {
        MiuiHandViewPreviewModel miuiHandViewPreviewModel;
        if (ScreenModeUtils.isFull(getActivityContext())) {
            Context activityContext = getActivityContext();
            if (activityContext == null || (miuiHandViewPreviewModel = this.miuiHandViewPreviewModel) == null) {
                return;
            }
            miuiHandViewPreviewModel.setPadding((int) activityContext.getResources().getDimension(R.dimen.creation_paint_view_margin), 0, (int) activityContext.getResources().getDimension(R.dimen.creation_paint_view_margin), 0);
            return;
        }
        MiuiHandViewPreviewModel miuiHandViewPreviewModel2 = this.miuiHandViewPreviewModel;
        if (miuiHandViewPreviewModel2 != null) {
            miuiHandViewPreviewModel2.setPadding(0, 0, 0, 0);
        }
    }

    public final void onConfigurationChanged(Configuration newConfig) {
        Intrinsics.checkNotNullParameter(newConfig, "newConfig");
        AutoDensityConfig.forceUpdateDensity(getActivityContext());
        setKeyboardDetector();
        changePadding();
        EditText editText = this.mTitleView;
        if (editText == null || editText.hasFocus()) {
            return;
        }
        if (UIUtils.isNightMode() == this.isNightMode) {
            WeakReference<Activity> weakReference = this.activityRef;
            if (UIUtils.getScreenOrientation(weakReference != null ? weakReference.get() : null) == this.screenOrientation) {
                return;
            }
        }
        this.isNightMode = UIUtils.isNightMode();
        WeakReference<Activity> weakReference2 = this.activityRef;
        this.screenOrientation = UIUtils.getScreenOrientation(weakReference2 != null ? weakReference2.get() : null);
        Utils.hideSoftInput(this.mCreationPaintLayout);
    }

    public final void initUIProperties() {
        this.isNightMode = UIUtils.isNightMode();
        WeakReference<Activity> weakReference = this.activityRef;
        this.screenOrientation = UIUtils.getScreenOrientation(weakReference != null ? weakReference.get() : null);
        this.local = UIUtils.getLocale(getActivityContext());
    }

    public final void updateBackgroundMargin(float scale) {
        CreationPaintPreviewLayout creationPaintPreviewLayout = this.mCreationPaintLayout;
        if (creationPaintPreviewLayout != null) {
            creationPaintPreviewLayout.updateBackgroundMargin(scale);
        }
    }

    public final void setTitleChangedListener(HeaderView.OnTitleChangedListener listener) {
        this.mOnTitleChangedListener = listener;
    }

    private final void setKeyboardDetector() {
        KeyboardHelper keyboardHelper = this.mEditKeyboardDetector;
        if (keyboardHelper != null) {
            keyboardHelper.release();
        }
        CreationPaintPreviewLayout creationPaintPreviewLayout = this.mCreationPaintLayout;
        KeyboardHelper keyboardHelper2 = new KeyboardHelper(creationPaintPreviewLayout, creationPaintPreviewLayout != null ? creationPaintPreviewLayout.getRootView() : null, new KeyboardMarginProcessor() { // from class: com.miui.libhandwritingpreview.model.MiuiHandWritingPreviewModel.setKeyboardDetector.1
            @Override // com.miui.handwriting.common.softinputhelper.KeyboardMarginProcessor, com.miui.handwriting.common.softinputhelper.KeyboardHeightListener
            public void onKeyboardHeightChanged(View view, int height, int orientation) {
                Activity activity;
                MiuiHandViewPreviewModel miuiHandViewPreviewModel;
                Intrinsics.checkNotNullParameter(view, "view");
                WeakReference weakReference = MiuiHandWritingPreviewModel.this.activityRef;
                if (weakReference == null || (activity = (Activity) weakReference.get()) == null || !activity.isInMultiWindowMode()) {
                    return;
                }
                if (UIUtils.isSmallWindowMode(activity)) {
                    boolean zIsKeyboardVisibleInMultiWindowMode = KeyboardHelper.isKeyboardVisibleInMultiWindowMode(activity);
                    if (MiuiHandWritingPreviewModel.this.mIsKeyboardVisible != zIsKeyboardVisibleInMultiWindowMode) {
                        MiuiHandWritingPreviewModel.this.mIsKeyboardVisible = zIsKeyboardVisibleInMultiWindowMode;
                        return;
                    }
                    return;
                }
                boolean zIsKeyboardVisibleInMultiWindowMode2 = KeyboardHelper.isKeyboardVisibleInMultiWindowMode(activity);
                if (MiuiHandWritingPreviewModel.this.mIsKeyboardVisible != zIsKeyboardVisibleInMultiWindowMode2) {
                    MiuiHandWritingPreviewModel.this.mIsKeyboardVisible = zIsKeyboardVisibleInMultiWindowMode2;
                }
                if (MiuiHandWritingPreviewModel.this.mIsKeyboardVisible) {
                    if (height == 0 || (miuiHandViewPreviewModel = MiuiHandWritingPreviewModel.this.getMiuiHandViewPreviewModel()) == null) {
                        return;
                    }
                    miuiHandViewPreviewModel.scrollOnKeyBoardHeightChanged(activity, height, true);
                    return;
                }
                MiuiHandViewPreviewModel miuiHandViewPreviewModel2 = MiuiHandWritingPreviewModel.this.getMiuiHandViewPreviewModel();
                if (miuiHandViewPreviewModel2 != null) {
                    miuiHandViewPreviewModel2.scrollOnKeyBoardHeightChanged(activity, height, MiuiHandWritingPreviewModel.this.mIsKeyboardVisible);
                }
            }

            @Override // com.miui.handwriting.common.softinputhelper.KeyboardMarginProcessor, com.miui.handwriting.common.softinputhelper.KeyboardHeightListener
            public void onKeyboardHeightChangedProgress(View view, int height, int orientation) {
                Activity activity;
                Intrinsics.checkNotNullParameter(view, "view");
                WeakReference weakReference = MiuiHandWritingPreviewModel.this.activityRef;
                if (weakReference == null || (activity = (Activity) weakReference.get()) == null || activity.isInMultiWindowMode()) {
                    return;
                }
                boolean zIsKeyboardVisibleInMultiWindowMode = KeyboardHelper.isKeyboardVisibleInMultiWindowMode(activity);
                if (MiuiHandWritingPreviewModel.this.mIsKeyboardVisible != zIsKeyboardVisibleInMultiWindowMode) {
                    MiuiHandWritingPreviewModel.this.mIsKeyboardVisible = zIsKeyboardVisibleInMultiWindowMode;
                }
                MiuiHandViewPreviewModel miuiHandViewPreviewModel = MiuiHandWritingPreviewModel.this.getMiuiHandViewPreviewModel();
                if (miuiHandViewPreviewModel != null) {
                    miuiHandViewPreviewModel.scrollOnKeyBoardHeightChanged(activity, height, MiuiHandWritingPreviewModel.this.mIsKeyboardVisible);
                }
            }
        });
        this.mEditKeyboardDetector = keyboardHelper2;
        Intrinsics.checkNotNull(keyboardHelper2);
        keyboardHelper2.setEnable(true);
    }

    public final ThumbnailRecyclerManager getThumbnailManager() {
        CreationPaintPreviewLayout creationPaintPreviewLayout = this.mCreationPaintLayout;
        if (creationPaintPreviewLayout != null) {
            return creationPaintPreviewLayout.getThumbnailManager();
        }
        return null;
    }

    public final void setTitleAndTime(String title, long time) {
        CreationPaintPreviewLayout creationPaintPreviewLayout = this.mCreationPaintLayout;
        if (creationPaintPreviewLayout != null) {
            creationPaintPreviewLayout.setTitleAndTime(title, time);
        }
    }

    public final void clearTitleCursor() {
        EditText titleView;
        CreationPaintPreviewLayout creationPaintPreviewLayout = this.mCreationPaintLayout;
        if (creationPaintPreviewLayout == null || (titleView = creationPaintPreviewLayout.getTitleView()) == null) {
            return;
        }
        titleView.clearFocus();
    }

    public final void setTitleTime(long time) {
        CreationPaintPreviewLayout creationPaintPreviewLayout = this.mCreationPaintLayout;
        if (creationPaintPreviewLayout != null) {
            creationPaintPreviewLayout.setTitleTime(time);
        }
    }

    public final void setTitle(String savingTitle) {
        EditText titleView;
        CreationPaintPreviewLayout creationPaintPreviewLayout = this.mCreationPaintLayout;
        if (creationPaintPreviewLayout == null || (titleView = creationPaintPreviewLayout.getTitleView()) == null) {
            return;
        }
        titleView.setText(savingTitle);
    }

    public final void setTitleColor(String savingTitle, String searchStr, int color) {
        EditText titleView;
        if (savingTitle != null) {
            String str = savingTitle;
            SpannableStringBuilder spannableStringBuilder = new SpannableStringBuilder(str);
            String str2 = searchStr;
            if (str2 != null && str2.length() != 0) {
                int iIndexOf$default = StringsKt.indexOf$default((CharSequence) str, searchStr, 0, false, 6, (Object) null);
                while (iIndexOf$default >= 0) {
                    spannableStringBuilder.setSpan(new ForegroundColorSpan(color), iIndexOf$default, searchStr.length() + iIndexOf$default, 33);
                    iIndexOf$default = StringsKt.indexOf$default((CharSequence) str, searchStr, iIndexOf$default + searchStr.length(), false, 4, (Object) null);
                }
            }
            CreationPaintPreviewLayout creationPaintPreviewLayout = this.mCreationPaintLayout;
            if (creationPaintPreviewLayout == null || (titleView = creationPaintPreviewLayout.getTitleView()) == null) {
                return;
            }
            titleView.setText(spannableStringBuilder);
        }
    }

    public final String getTitle() {
        EditText titleView;
        CreationPaintPreviewLayout creationPaintPreviewLayout = this.mCreationPaintLayout;
        return String.valueOf((creationPaintPreviewLayout == null || (titleView = creationPaintPreviewLayout.getTitleView()) == null) ? null : titleView.getText());
    }

    public final void showLoadingDialog() {
        ProgressDialog progressDialog = this.pasteDialog;
        if (progressDialog == null || progressDialog == null || !progressDialog.isShowing()) {
            CreationPaintPreviewLayout creationPaintPreviewLayout = this.mCreationPaintLayout;
            Intrinsics.checkNotNull(creationPaintPreviewLayout);
            creationPaintPreviewLayout.post(new Runnable() { // from class: com.miui.libhandwritingpreview.model.MiuiHandWritingPreviewModel$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    MiuiHandWritingPreviewModel.showLoadingDialog$lambda$5(this.f$0);
                }
            });
            CreationPaintPreviewLayout creationPaintPreviewLayout2 = this.mCreationPaintLayout;
            Intrinsics.checkNotNull(creationPaintPreviewLayout2);
            creationPaintPreviewLayout2.postDelayed(this.pasteDialogAlphaRunnable, 500L);
            CreationPaintPreviewLayout creationPaintPreviewLayout3 = this.mCreationPaintLayout;
            Intrinsics.checkNotNull(creationPaintPreviewLayout3);
            creationPaintPreviewLayout3.postDelayed(this.pasteDialogDismissRunnable, 5000L);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void showLoadingDialog$lambda$5(MiuiHandWritingPreviewModel miuiHandWritingPreviewModel) {
        WindowManager.LayoutParams attributes;
        WeakReference<Activity> weakReference = miuiHandWritingPreviewModel.activityRef;
        Activity activity = weakReference != null ? weakReference.get() : null;
        if (activity == null || activity.isFinishing()) {
            return;
        }
        ProgressDialog progressDialog = new ProgressDialog(activity);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage(activity.getString(miuix.springback.R.string.miuix_sbl_tracking_progress_labe_up_refresh));
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.setCancelable(false);
        Window window = progressDialog.getWindow();
        if (window != null && (attributes = window.getAttributes()) != null) {
            attributes.alpha = 0.0f;
            window.setAttributes(attributes);
            window.getDecorView().setSystemUiVisibility(window.getDecorView().getSystemUiVisibility() | R2.color.theme_default_high_light_color);
        }
        miuiHandWritingPreviewModel.pasteDialog = progressDialog;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void pasteDialogAlphaRunnable$lambda$6(MiuiHandWritingPreviewModel miuiHandWritingPreviewModel) {
        WindowManager.LayoutParams attributes;
        ProgressDialog progressDialog = miuiHandWritingPreviewModel.pasteDialog;
        if (progressDialog != null) {
            Intrinsics.checkNotNull(progressDialog);
            progressDialog.show();
            ProgressDialog progressDialog2 = miuiHandWritingPreviewModel.pasteDialog;
            Window window = progressDialog2 != null ? progressDialog2.getWindow() : null;
            if (window == null || (attributes = window.getAttributes()) == null) {
                return;
            }
            attributes.alpha = 1.0f;
            window.setAttributes(attributes);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void pasteDialogDismissRunnable$lambda$7(MiuiHandWritingPreviewModel miuiHandWritingPreviewModel) {
        ProgressDialog progressDialog = miuiHandWritingPreviewModel.pasteDialog;
        if (progressDialog != null) {
            progressDialog.dismiss();
        }
    }

    public final void release() {
        this.activityRef = null;
        EditText editText = this.mTitleView;
        if (editText != null) {
            editText.removeTextChangedListener(this.mTitleTextWatcher);
        }
        if (this.pasteDialogAlphaRunnable != null) {
            CreationPaintPreviewLayout creationPaintPreviewLayout = this.mCreationPaintLayout;
            if (creationPaintPreviewLayout != null) {
                Intrinsics.checkNotNull(creationPaintPreviewLayout);
                creationPaintPreviewLayout.removeCallbacks(this.pasteDialogAlphaRunnable);
            }
            this.pasteDialogAlphaRunnable = null;
        }
        if (this.pasteDialogDismissRunnable != null) {
            CreationPaintPreviewLayout creationPaintPreviewLayout2 = this.mCreationPaintLayout;
            if (creationPaintPreviewLayout2 != null) {
                Intrinsics.checkNotNull(creationPaintPreviewLayout2);
                creationPaintPreviewLayout2.removeCallbacks(this.pasteDialogDismissRunnable);
            }
            this.pasteDialogDismissRunnable = null;
        }
        ProgressDialog progressDialog = this.pasteDialog;
        if (progressDialog != null) {
            Intrinsics.checkNotNull(progressDialog);
            if (progressDialog.isShowing()) {
                ProgressDialog progressDialog2 = this.pasteDialog;
                Intrinsics.checkNotNull(progressDialog2);
                progressDialog2.dismiss();
                this.pasteDialog = null;
            }
        }
        CreationPageSDK.sErrorCallback = null;
        if (this.mCreationPaintLayout != null) {
            CreationShieldPrePageInkView creationShieldPrePageInkView = this.mShieldPageInkView;
            if (creationShieldPrePageInkView != null) {
                Intrinsics.checkNotNull(creationShieldPrePageInkView);
                creationShieldPrePageInkView.removeAllViews();
            }
            CreationPaintPreviewLayout creationPaintPreviewLayout3 = this.mCreationPaintLayout;
            Intrinsics.checkNotNull(creationPaintPreviewLayout3);
            if (creationPaintPreviewLayout3.getParent() != null) {
                CreationPaintPreviewLayout creationPaintPreviewLayout4 = this.mCreationPaintLayout;
                Intrinsics.checkNotNull(creationPaintPreviewLayout4);
                ViewParent parent = creationPaintPreviewLayout4.getParent();
                Intrinsics.checkNotNull(parent, "null cannot be cast to non-null type android.view.ViewGroup");
                ((ViewGroup) parent).removeView(this.mCreationPaintLayout);
            }
            CreationPaintPreviewLayout creationPaintPreviewLayout5 = this.mCreationPaintLayout;
            Intrinsics.checkNotNull(creationPaintPreviewLayout5);
            creationPaintPreviewLayout5.removeAllViews();
        }
        KeyboardHelper keyboardHelper = this.mEditKeyboardDetector;
        if (keyboardHelper != null) {
            Intrinsics.checkNotNull(keyboardHelper);
            keyboardHelper.release();
            this.mEditKeyboardDetector = null;
        }
        this.mShieldPageInkView = null;
        this.mCreationPaintLayout = null;
        this.mTitleView = null;
        this.mOnTitleChangedListener = null;
    }
}
