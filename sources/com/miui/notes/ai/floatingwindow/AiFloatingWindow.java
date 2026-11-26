package com.miui.notes.ai.floatingwindow;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Point;
import android.graphics.Rect;
import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewStub;
import android.view.ViewTreeObserver;
import android.view.WindowManager;
import android.webkit.WebView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.hardware.DataSpace;
import com.miui.ai.text.widget.R;
import com.miui.hybirdeditor.EmptyOverwriteGuard;
import com.miui.notes.ai.Action;
import com.miui.notes.ai.AiTextWidgetService;
import com.miui.notes.ai.account.ExtraAccountManager;
import com.miui.notes.ai.data.DataListener;
import com.miui.notes.ai.data.input.PolishStyle;
import com.miui.notes.ai.data.input.RequestType;
import com.miui.notes.ai.data.input.UserInputHolder;
import com.miui.notes.ai.data.output.AiOutputData;
import com.miui.notes.ai.editor.AiEditor;
import com.miui.notes.ai.editor.IAiEditor;
import com.miui.notes.ai.editor.IWebViewListener;
import com.miui.notes.ai.local.LLMSearchListener;
import com.miui.notes.ai.network.AINetWorkUtils;
import com.miui.notes.ai.network.AiNoteRequest;
import com.miui.notes.ai.network.GlobalAiTextRequestManager;
import com.miui.notes.ai.network.NetworkError;
import com.miui.notes.ai.network.RequestState;
import com.miui.notes.ai.share.AIDialogActivity;
import com.miui.notes.ai.stat.AITextWidgetStat;
import com.miui.notes.ai.ui.AiButtomItem;
import com.miui.notes.ai.ui.AiRecommendItem;
import com.miui.notes.ai.ui.AiToast;
import com.miui.notes.ai.ui.AiWaterMarkView;
import com.miui.notes.ai.ui.AiWidgetTabItem;
import com.miui.notes.ai.ui.CustomAiTextWidgetEditText;
import com.miui.notes.ai.ui.FloatingDecorView;
import com.miui.notes.ai.ui.RenderViewLayout;
import com.miui.notes.ai.ui.material.MaterialEffectContainer;
import com.miui.notes.ai.ui.material.ViewType;
import com.miui.notes.ai.ui.shader.AiShaderEffectRenderer;
import com.miui.notes.ai.utils.AIPreferencesUtils;
import com.miui.notes.ai.utils.Constant;
import com.miui.notes.ai.utils.DisplayUtil;
import com.miui.notes.ai.utils.ForegroundInfoUtils;
import com.miui.notes.ai.utils.FreeModeUtils;
import com.miui.notes.ai.utils.KeyboardListenerHelper;
import com.miui.notes.ai.utils.LocaleUtils;
import com.miui.notes.ai.utils.MaterialEffectUtil;
import com.miui.notes.ai.utils.NoteSchemaUtil;
import com.miui.notes.ai.utils.PreferenceUtils;
import com.miui.notes.ai.utils.ReflectUtils;
import com.miui.notes.ai.utils.RomUtils;
import com.miui.notes.ai.utils.ScreenPosistionUtils;
import com.miui.notes.ai.utils.SoftInputUtil;
import com.miui.notes.ai.utils.SplitScreenModelUtils;
import com.miui.notes.ai.utils.SystemNavigationKeyClickListener;
import com.miui.notes.ai.utils.TextExtKt;
import com.miui.notes.ai.utils.WindowTouchRegionHelper;
import com.miui.notes.base.accessibility.AccessibilityDelegateUtil;
import com.miui.notes.base.utils.FrameUpdateMonitor;
import com.miui.notes.base.utils.Logger;
import com.miui.notes.basefeature.NotesNormalConstants;
import com.xiaomi.aicr.llm.LLMManager;
import defpackage.R2;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import miui.process.ForegroundInfo;
import miui.process.IForegroundWindowListener;
import miuix.animation.Folme;
import miuix.animation.IStateStyle;
import miuix.animation.ITouchStyle;
import miuix.animation.base.AnimConfig;
import miuix.animation.controller.AnimState;
import miuix.animation.listener.TransitionListener;
import miuix.animation.listener.UpdateInfo;
import miuix.animation.property.ValueProperty;
import miuix.animation.property.ViewProperty;
import miuix.animation.utils.EaseManager;
import miuix.animation.utils.SpringInterpolator;
import miuix.core.util.MiuiBlurUtils;
import org.apache.commons.lang3.StringUtils;

/* JADX INFO: loaded from: classes4.dex */
public class AiFloatingWindow implements View.OnClickListener, DataListener, KeyboardListenerHelper.IKeyBoardChangedListener, IWebViewListener, FloatingDecorView.OnScreenChangeListener, AiTextWidgetService.OnScreenChangeListener, LLMSearchListener, AIDialogActivity.DialogCancelListener, AIDialogActivity.DialogRetryListener, AIDialogActivity.DialogCloseListener {
    private static final long SHADER_EFFECT_REFRESH_INTERVAL = 33;
    private static final int SHADER_STATE_INPUT = 2;
    private static final int SHADER_STATE_PANEL = 1;
    private static final String TAG = "AiFloatingWindow";
    public static boolean isAiErrorImageShow = false;
    public static int mAiEndTextCount = 0;
    public static String mAiTraceId = null;
    public static String mContentStatus = "unknown";
    public static int mContentStyle = -1;
    public static int mContentTextCount = 0;
    public static String mContentType = "unknown";
    public static int mCorrectMisNum = 0;
    public static String mElementName = null;
    public static int mEndAllCount = 0;
    public static boolean mHasFlowData = false;
    public static boolean mInTitle = false;
    public static boolean mIsNoteAi = false;
    public static int mStartAllCount = 0;
    private static final String subPackageName = "com.xiaomi.subscreencenter";
    private final int TYPE_EASY;
    private final int TYPE_FRIENDLY;
    private final int TYPE_GENERIC;
    private final int TYPE_PROFESSIONAL;
    private AIPreferencesUtils aiPreferencesUtils;
    private String aiSessionId;
    private int aiTextCount;
    int cotInputwidth;
    private int currentNarrow;
    private int currentOri;
    private int currentRotation;
    private Rect currentScreenRect;
    public long endRequestTime;
    boolean inHighAnim;
    private boolean isButtonClicked;
    private boolean isClickReturned;
    private boolean isExceedNumberLimit;
    private boolean isKeyBoardShowing;
    private boolean ismRecommendShowBegin;
    private int lastNarrow;
    private int lastOri;
    private int lastRotation;
    private Rect lastScreenRect;
    private long lastTriggeredTime;
    private View mAIInputDeepSeekButton;
    public AINetWorkUtils mAINetWorkUtils;
    private int mAIUseNum;
    private View mAiBack;
    private AiButtomItem mAiBackText;
    private AiWidgetTabItem mAiCheck;
    private MaterialEffectContainer mAiCheckContainer;
    private View mAiCopy;
    private View mAiDeepSeekButtonIcon;
    private View mAiDone;
    protected String mAiDoneData;
    private View mAiDoneTab;
    protected IAiEditor mAiEditor;
    protected String mAiEndGenerateTip;
    protected String mAiEndTip;
    private View mAiErrorImage;
    private AiWidgetTabItem mAiExtension;
    private MaterialEffectContainer mAiExtensionContainer;
    private View mAiImage;
    private TextView mAiInputDeepSeekText;
    private View mAiInputIconView;
    protected View mAiInsert;
    private long mAiOpenTime;
    private View mAiRefresh;
    private AiButtomItem mAiRefreshText;
    protected String mAiSavedData;
    private AiShaderEffectRenderer mAiShaderEffectRenderer;
    private AiWidgetTabItem mAiSummary;
    private MaterialEffectContainer mAiSummaryContainer;
    protected AiTextWidgetService mAiTextWidgetService;
    protected String mAiTitleData;
    private AiToast mAiToast;
    private AiWaterMarkView mAiWaterMarkView;
    private WebView mAiWebView;
    private Runnable mAnimChangeRunnable;
    private ForegroundWindowObserver mAppObserver;
    private float[] mBgBoundData;
    private View mBgGradientColorView;
    private RenderViewLayout mBgGradientColorViewContainer;
    private TextView mBtnGeneratingStop;
    protected View mCloseButton;
    public boolean mCloudIsDevice;
    private final Context mContext;
    protected String mDeleteLabel;
    private float mDeltaTime;
    private boolean mDuringInputToPanel;
    private volatile boolean mDuringRemoveView;
    private View mEasy;
    private int mEffectBgPadding;
    private float mEffectStartTime;
    private String mErrorCode;
    protected boolean mFinishFlow;
    public boolean mFirst;
    protected boolean mFirstInvokeFlow;
    private View mFlagView;
    private View mFloatingContentView;
    private View mFloatingRimEffectView;
    private View mFloatingView;
    private View mFriendly;
    private View mGeneral;
    private View mGeneratingInflate;
    private View mGeneratingStop;
    private ViewStub mGeneratingStub;
    private View mGeneratingView;
    protected Handler mHandler;
    private boolean mHasChangeInit;
    private boolean mHasEditInit;
    protected boolean mHasStopGenerating;
    private boolean mHasTouchInput;
    private boolean mHasWebInit;
    private String mHostPackageName;
    private int mHostTaskId;
    private int mImeHeight;
    private boolean mImeVisible;
    protected CustomAiTextWidgetEditText mInput;
    private View mInputContentView;
    Runnable mInputEffectRunnable;
    private ViewTreeObserver.OnGlobalLayoutListener mInputGlobalLayoutListener;
    private int mInputIncreasedHeight;
    private View mInputLayout;
    private View mInputRimEffectView;
    protected ImageView mInputSendIconImage;
    private View mInputSendView;
    private boolean mIsApplyShaderEffect;
    private boolean mIsClickStopGenerating;
    public boolean mIsCloudSupportCot;
    protected boolean mIsDeleteLabel;
    private boolean mIsFromPrivate;
    protected boolean mIsNeedMatchThinkOneTrack;
    private boolean mIsRecommendInput;
    private boolean mIsRefreshGenerating;
    private boolean mIsShowing;
    public boolean mIsSupportCot;
    public boolean mIsSupportLLMWriting;
    protected boolean mIsThinkContent;
    private boolean mKeyBoardChangeEnd;
    private boolean mKeyBoardChangeStart;
    private boolean mKeyBoardVisible;
    private KeyboardListenerHelper mKeyboardListenerHelper;
    protected String mLastFlowData;
    private long mLastGlobalTime;
    private int mLoadingEndHeight;
    protected int mMaxGenerateNumber;
    protected int mMaxInputNumber;
    protected int mMaxTextLengthNumber;
    private boolean mNetError;
    private String mNoteContent;
    private String mNoteTitle;
    protected boolean mOnFlowDone;
    private TextView mPanelAiTitle;
    private TextView mPanelAiTitleEnd;
    private final Runnable mPanelEffectRunnable;
    private View mPanelInflate;
    private ViewStub mPanelStub;
    private TextView mPanelTitleStart;
    private View mPanelView;
    private WindowManager.LayoutParams mParams;
    protected PolishStyle mPolishStyle;
    private TextView mPolishTitle;
    private float mPreUpdateTime;
    private View mProfessional;
    private int mRecommendBgPadding;
    private View[] mRecommendContainers;
    private View mRecommendDeepSeekButton;
    private TextView mRecommendDeepSeekButtonText;
    private boolean mRecommendDismiss;
    private EditText mRecommendEdit;
    private View mRecommendInflate;
    private View mRecommendInput;
    private View mRecommendInputContentView;
    private View mRecommendInputRimEffectView;
    private AiRecommendItem[] mRecommendItems;
    private ViewStub mRecommendPanelStub;
    private View mRecommendSendIcon;
    private ImageView mRecommendSendIconImage;
    private int mRecommendSize;
    private View mRecommendView;
    protected RequestState mRequestState;
    private FloatingDecorView mRootView;
    private final Point mScreenSize;
    private int mSelectCount;
    private String mSelectedText;
    private int mShaderState;
    protected long mStartRequestTime;
    protected int mThinkCount;
    protected long mThinkTime;
    private float mTime;
    private View mTitleLayout;
    private WindowCloseListener mWindowCloseListener;
    private WindowManager mWindowManager;
    private ScreenPosistionUtils.Position mWindowPosition;
    private WindowTouchRegionHelper mWindowTouchRegionHelper;
    private boolean misAiDeepSeekSelected;
    private long recieveNarrowChangeTime;
    public final Action recyclingWindowAction;
    private Rect smallWindowRect;
    public long startRequestTime;

    public interface ForegroundWindowObserverCallback {
        void onForegroundWindowChanged(ForegroundInfo foregroundInfo);
    }

    public interface WindowCloseListener {
        void onWindowClosed();
    }

    protected boolean canRecommendItemEnable() {
        return true;
    }

    @Override // com.miui.notes.ai.editor.IWebViewListener
    public void onPagePrepared() {
    }

    protected void refreshAiAction() {
    }

    protected void selectAllTextIfNecessary() {
    }

    public static class ForegroundWindowObserver extends IForegroundWindowListener.Stub {
        private volatile ForegroundWindowObserverCallback call;
        private final Object callbackLock = new Object();

        public ForegroundWindowObserver(ForegroundWindowObserverCallback foregroundWindowObserverCallback) {
            Logger.INSTANCE.d(AiFloatingWindow.TAG, "ForegroundWindowObserver new ");
            this.call = foregroundWindowObserverCallback;
        }

        @Override // miui.process.IForegroundWindowListener
        public void onForegroundWindowChanged(ForegroundInfo foregroundInfo) {
            if (foregroundInfo == null || foregroundInfo.mForegroundPackageName == null) {
                return;
            }
            Logger.INSTANCE.d(AiFloatingWindow.TAG, "onForegroundWindowChanged,foreground name: " + foregroundInfo.mForegroundPackageName);
            if (foregroundInfo.mForegroundPackageName.equals(AiFloatingWindow.subPackageName)) {
                Logger.INSTANCE.d(AiFloatingWindow.TAG, "onForegroundWindowChanged,is subscreencenter return");
                return;
            }
            synchronized (this.callbackLock) {
                if (this.call != null) {
                    this.call.onForegroundWindowChanged(foregroundInfo);
                }
            }
        }

        public void destroy() {
            this.call = null;
        }
    }

    private void initAppObserver() {
        Logger.INSTANCE.d(TAG, "initAppObserver");
        try {
            ForegroundWindowObserver foregroundWindowObserver = new ForegroundWindowObserver(new ForegroundWindowObserverCallback() { // from class: com.miui.notes.ai.floatingwindow.AiFloatingWindow$$ExternalSyntheticLambda7
                @Override // com.miui.notes.ai.floatingwindow.AiFloatingWindow.ForegroundWindowObserverCallback
                public final void onForegroundWindowChanged(ForegroundInfo foregroundInfo) {
                    this.f$0.lambda$initAppObserver$0(foregroundInfo);
                }
            });
            this.mAppObserver = foregroundWindowObserver;
            ForegroundInfoUtils.registerForegroundWindowListener(foregroundWindowObserver);
        } catch (Throwable unused) {
            Logger.INSTANCE.d(TAG, "initAppObserver error");
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initAppObserver$0(ForegroundInfo foregroundInfo) {
        Rect rect;
        if (RomUtils.isFoldDevice()) {
            setTouchableRegion();
        }
        Logger.INSTANCE.i(TAG, "onForegroundWindowChanged current " + foregroundInfo.mForegroundPackageName + " last " + foregroundInfo.mLastForegroundPackageName + " currentOri " + this.currentOri + " lastOri " + this.lastOri + " mMultiWindowForegroundPackageName " + foregroundInfo.mMultiWindowForegroundPackageName);
        Logger.INSTANCE.i(TAG, "onForegroundWindowChanged smallWindowRect : " + this.smallWindowRect);
        if (SplitScreenModelUtils.isSplitScreenMode(this.mHostTaskId) == SplitScreenModelUtils.SplitResult.FALSE && this.smallWindowRect == null) {
            if (!foregroundInfo.mForegroundPackageName.equals(this.mHostPackageName) && foregroundInfo.mLastForegroundPackageName.equals(this.mHostPackageName)) {
                if (this.currentOri == this.lastOri) {
                    Logger.INSTANCE.i(TAG, "onForegroundWindowChanged not in split screen and detach");
                    detachFromWindowFromForeInfoChange();
                    return;
                } else {
                    Logger.INSTANCE.i(TAG, "onForegroundWindowChanged not in split screen and just rotate");
                    return;
                }
            }
            if (foregroundInfo.mForegroundPackageName.equals(this.mHostPackageName) || foregroundInfo.mLastForegroundPackageName.equals(this.mHostPackageName)) {
                return;
            }
            if (foregroundInfo.mForegroundPackageName.equals(foregroundInfo.mMultiWindowForegroundPackageName)) {
                Logger.INSTANCE.i(TAG, "onForegroundWindowChanged not in split screen & mForegroundPackage equals mMultiWindowForegroundPackage");
                return;
            } else if (this.currentOri == this.lastOri) {
                Logger.INSTANCE.i(TAG, "onForegroundWindowChanged not in split screen & hostpackage not in listener & rotate detach");
                detachFromWindowFromForeInfoChange();
                return;
            } else {
                Logger.INSTANCE.i(TAG, "onForegroundWindowChanged not in split screen & hostpackage not in listener & not rotate detach");
                detachFromWindowFromForeInfoChange();
                return;
            }
        }
        if (SplitScreenModelUtils.isSplitScreenMode(this.mHostTaskId) == SplitScreenModelUtils.SplitResult.TRUE) {
            List<String> topSplitPackageNames = SplitScreenModelUtils.getTopSplitPackageNames(this.mContext);
            if (topSplitPackageNames != null && !topSplitPackageNames.isEmpty()) {
                Iterator<String> it = topSplitPackageNames.iterator();
                while (it.hasNext()) {
                    Logger.INSTANCE.i(TAG, "splitName is " + it.next());
                }
            } else {
                Logger.INSTANCE.i(TAG, "splitNames is null");
            }
            if (this.currentOri == this.lastOri) {
                if (topSplitPackageNames != null && !topSplitPackageNames.isEmpty() && !topSplitPackageNames.contains(foregroundInfo.mForegroundPackageName)) {
                    Logger.INSTANCE.i(TAG, "onForegroundWindowChanged current package not in origin splitNames & not rotate");
                    detachFromWindowFromForeInfoChange();
                }
                Logger.INSTANCE.i(TAG, "onForegroundWindowChanged in split screen and not rotate");
                if (!foregroundInfo.mForegroundPackageName.equals(this.mHostPackageName) && foregroundInfo.mLastForegroundPackageName.equals(this.mHostPackageName)) {
                    Logger.INSTANCE.i(TAG, "onForegroundWindowChanged in split screen and last package is notes");
                    return;
                } else if (foregroundInfo.mForegroundPackageName.equals(foregroundInfo.mLastForegroundPackageName)) {
                    Logger.INSTANCE.i(TAG, "onForegroundWindowChanged in split screen but same process");
                    return;
                } else {
                    Logger.INSTANCE.i(TAG, "onForegroundWindowChanged in split screen & not rotate & not detach");
                    return;
                }
            }
            if (topSplitPackageNames != null && !topSplitPackageNames.isEmpty() && !topSplitPackageNames.contains(foregroundInfo.mForegroundPackageName) && !topSplitPackageNames.contains(null)) {
                Logger.INSTANCE.i(TAG, "onForegroundWindowChanged current package not in origin splitNames & rotate");
                detachFromWindowFromForeInfoChange();
            }
            Logger.INSTANCE.i(TAG, "onForegroundWindowChanged in split screen and rotate");
            return;
        }
        if (SplitScreenModelUtils.isSplitScreenMode(this.mHostTaskId) == SplitScreenModelUtils.SplitResult.NOT_FOUND && this.smallWindowRect == null) {
            if (foregroundInfo.mForegroundPackageName.equals(this.mHostPackageName) || !foregroundInfo.mLastForegroundPackageName.equals(this.mHostPackageName)) {
                return;
            }
            if (this.currentOri == this.lastOri) {
                Logger.INSTANCE.i(TAG, "onForegroundWindowChanged not known in split screen and detach");
                detachFromWindowFromForeInfoChange();
                return;
            } else {
                Logger.INSTANCE.i(TAG, "onForegroundWindowChanged not known in split screen and just rotate");
                return;
            }
        }
        if (this.smallWindowRect != null) {
            List<String> allPackageInFreeMode = FreeModeUtils.getAllPackageInFreeMode(this.mContext);
            if (allPackageInFreeMode != null && !allPackageInFreeMode.isEmpty()) {
                Iterator<String> it2 = allPackageInFreeMode.iterator();
                while (it2.hasNext()) {
                    Logger.INSTANCE.i(TAG, "onForegroundWindowChanged in freeMode freeModePackage " + it2.next());
                }
            }
            Logger.INSTANCE.i(TAG, "onForegroundWindowChanged in freeMode");
            if (this.currentRotation != this.lastRotation) {
                if (allPackageInFreeMode != null && !allPackageInFreeMode.isEmpty() && !allPackageInFreeMode.contains(foregroundInfo.mForegroundPackageName) && !allPackageInFreeMode.contains(foregroundInfo.mLastForegroundPackageName)) {
                    Logger.INSTANCE.i(TAG, "onForegroundWindowChanged in freeMode has  " + foregroundInfo.mForegroundPackageName + "in foreground and seems like rotate");
                    detachFromWindowFromForeInfoChange();
                }
                Logger.INSTANCE.i(TAG, "onForegroundWindowChanged in freeMode and rotate");
                return;
            }
            if (RomUtils.isFoldDevice() && this.currentNarrow != this.lastNarrow) {
                Logger.INSTANCE.i(TAG, "during narrow change & receive message currentNarrow is " + this.currentNarrow + " lastNarrow is " + this.lastNarrow);
                this.recieveNarrowChangeTime = System.currentTimeMillis();
                return;
            }
            if (allPackageInFreeMode != null && !allPackageInFreeMode.isEmpty() && !allPackageInFreeMode.contains(foregroundInfo.mForegroundPackageName) && !allPackageInFreeMode.contains(foregroundInfo.mLastForegroundPackageName)) {
                Logger.INSTANCE.i(TAG, "onForegroundWindowChanged in freeMode has  " + foregroundInfo.mForegroundPackageName + "in foreground" + foregroundInfo.mLastForegroundPackageName + " in last gap time is " + (System.currentTimeMillis() - this.recieveNarrowChangeTime) + " recieveNarrowChangeTime is " + this.recieveNarrowChangeTime);
                detachFromWindowFromForeInfoChange();
                return;
            }
            if (allPackageInFreeMode != null && !allPackageInFreeMode.isEmpty() && !allPackageInFreeMode.contains(foregroundInfo.mForegroundPackageName)) {
                if (RomUtils.isPhoneDevice() || RomUtils.isPadDevice()) {
                    Logger.INSTANCE.i(TAG, "onForegroundWindowChanged in freeMode with phone or pad has  " + foregroundInfo.mForegroundPackageName + "in foreground" + foregroundInfo.mLastForegroundPackageName + " in last");
                    detachFromWindowFromForeInfoChange();
                    return;
                }
                Logger.INSTANCE.i(TAG, " lastScreenRect " + this.lastScreenRect + " currentScreenRect " + this.currentScreenRect);
                Rect rect2 = this.currentScreenRect;
                if (rect2 != null && (rect = this.lastScreenRect) != null && rect2.equals(rect)) {
                    Logger.INSTANCE.i(TAG, "rect not move detach currentNarrow " + this.currentNarrow + " lastNarrow " + this.lastNarrow + " recieveNarrowChangeTime is " + this.recieveNarrowChangeTime + " gap time is " + (System.currentTimeMillis() - this.recieveNarrowChangeTime));
                    if (this.currentScreenRect.equals(this.smallWindowRect)) {
                        Logger.INSTANCE.i(TAG, "rect not move detach but equals samll rect");
                        return;
                    } else {
                        Logger.INSTANCE.i(TAG, "rect not move detach but not equals samll rect, detach");
                        detachFromWindowFromForeInfoChange();
                        return;
                    }
                }
                Logger.INSTANCE.i(TAG, "rect move not detach");
                return;
            }
            if (foregroundInfo.mForegroundPackageName.equals(foregroundInfo.mLastForegroundPackageName)) {
                if (!DisplayUtil.isInFullWindowGestureMode(this.mContext)) {
                    Rect rectIsHostInFreeMode = FreeModeUtils.isHostInFreeMode(this.mHostTaskId);
                    if (rectIsHostInFreeMode != null) {
                        if (rectIsHostInFreeMode.left != this.smallWindowRect.left && rectIsHostInFreeMode.height() != this.smallWindowRect.height() && rectIsHostInFreeMode.width() != this.smallWindowRect.width()) {
                            Logger.INSTANCE.i(TAG, "onForegroundWindowChanged in navigation origin rect " + this.smallWindowRect + " now rect " + rectIsHostInFreeMode + " so detach");
                            Logger.INSTANCE.i(TAG, "onForegroundWindowChanged in navigation newRect.height() " + rectIsHostInFreeMode.height() + " newRect.width() " + rectIsHostInFreeMode.width() + " smallWindowRect.height() " + this.smallWindowRect.height() + " smallWindowRect.width() " + this.smallWindowRect.width());
                            detachFromWindowFromForeInfoChange();
                            return;
                        } else {
                            Logger.INSTANCE.i(TAG, "onForegroundWindowChanged not in navigation newRect.height() " + rectIsHostInFreeMode.height() + " newRect.width() " + rectIsHostInFreeMode.width() + " smallWindowRect.height() " + this.smallWindowRect.height() + " smallWindowRect.width() " + this.smallWindowRect.width());
                            Logger.INSTANCE.i(TAG, "onForegroundWindowChanged not in navigation origin rect " + this.smallWindowRect + " now rect " + rectIsHostInFreeMode);
                            return;
                        }
                    }
                    Logger.INSTANCE.i(TAG, "onForegroundWindowChanged not in navigation origin rect " + this.smallWindowRect + " now rect is null");
                    return;
                }
                Logger.INSTANCE.i(TAG, "onForegroundWindowChanged not in navigation origin rect " + this.smallWindowRect + " now rect " + FreeModeUtils.isHostInFreeMode(this.mHostTaskId) + " so detach");
                return;
            }
            Logger.INSTANCE.i(TAG, "onForegroundWindowChanged in freeMode & not rotate & not detach");
        }
    }

    public AiFloatingWindow(Context context) {
        String str;
        this.misAiDeepSeekSelected = false;
        this.isKeyBoardShowing = false;
        this.mRecommendSize = 0;
        this.mRecommendDismiss = false;
        this.mImeVisible = false;
        this.mImeHeight = -1;
        this.mIsApplyShaderEffect = true;
        this.mEffectStartTime = System.nanoTime();
        this.mScreenSize = new Point();
        this.mBgBoundData = new float[4];
        this.mIsRecommendInput = false;
        this.mHasWebInit = false;
        this.mHasEditInit = false;
        this.mHasChangeInit = false;
        this.mMaxInputNumber = 200;
        this.isExceedNumberLimit = false;
        this.mMaxGenerateNumber = 2000;
        this.mMaxTextLengthNumber = 20000;
        this.mRequestState = RequestState.STATE_UNKNOWN;
        this.mPolishStyle = PolishStyle.TYPE_UNKNOWN;
        this.mWindowTouchRegionHelper = null;
        this.mHandler = new Handler(Looper.getMainLooper());
        this.mHasStopGenerating = false;
        this.mDuringRemoveView = false;
        this.lastOri = 0;
        this.currentOri = 0;
        this.mIsFromPrivate = false;
        this.isButtonClicked = false;
        this.lastRotation = -1;
        this.currentRotation = -1;
        this.lastScreenRect = null;
        this.currentScreenRect = null;
        this.lastNarrow = -1;
        this.currentNarrow = -1;
        this.mShaderState = -1;
        this.mAiTextWidgetService = null;
        this.mInputGlobalLayoutListener = null;
        this.aiPreferencesUtils = null;
        this.TYPE_GENERIC = 1;
        this.TYPE_PROFESSIONAL = 2;
        this.TYPE_EASY = 3;
        this.TYPE_FRIENDLY = 4;
        this.recieveNarrowChangeTime = -1L;
        this.inHighAnim = false;
        this.mIsClickStopGenerating = false;
        this.mCloudIsDevice = false;
        this.mIsSupportLLMWriting = false;
        this.mIsSupportCot = false;
        this.mIsCloudSupportCot = true;
        this.mIsRefreshGenerating = false;
        this.mIsShowing = false;
        this.mAIUseNum = 1;
        this.aiTextCount = 0;
        this.mAiOpenTime = 0L;
        this.mInputIncreasedHeight = 0;
        this.mLastGlobalTime = 0L;
        this.mTime = 0.0f;
        this.mDeltaTime = 0.0f;
        this.lastTriggeredTime = System.currentTimeMillis();
        this.mPanelEffectRunnable = new Runnable() { // from class: com.miui.notes.ai.floatingwindow.AiFloatingWindow.1
            @Override // java.lang.Runnable
            public void run() {
                AiFloatingWindow.this.tick();
                AiFloatingWindow.this.mAiShaderEffectRenderer.setAnimTime(AiFloatingWindow.this.mTime);
                if (AiFloatingWindow.this.mFloatingContentView != null) {
                    AiFloatingWindow.this.mAiShaderEffectRenderer.setForePanelWH(AiFloatingWindow.this.mFloatingContentView.getWidth(), AiFloatingWindow.this.mFloatingContentView.getHeight());
                }
                AiFloatingWindow.this.mAiShaderEffectRenderer.setFloatingPanelWH(AiFloatingWindow.this.mFloatingRimEffectView.getWidth(), AiFloatingWindow.this.mFloatingRimEffectView.getHeight());
                AiFloatingWindow.this.mAiShaderEffectRenderer.setBgEffectViewWH(AiFloatingWindow.this.mBgGradientColorView.getWidth(), AiFloatingWindow.this.mBgGradientColorView.getHeight());
                int[] iArr = new int[2];
                AiFloatingWindow.this.mFloatingRimEffectView.getLocationInWindow(iArr);
                AiFloatingWindow.this.mBgBoundData[0] = iArr[0] / AiFloatingWindow.this.mScreenSize.x;
                AiFloatingWindow.this.mBgBoundData[1] = 1.0f - ((iArr[1] + AiFloatingWindow.this.mFloatingRimEffectView.getHeight()) / AiFloatingWindow.this.mScreenSize.y);
                AiFloatingWindow.this.mBgBoundData[2] = AiFloatingWindow.this.mFloatingRimEffectView.getWidth() / AiFloatingWindow.this.mScreenSize.x;
                AiFloatingWindow.this.mBgBoundData[3] = AiFloatingWindow.this.mFloatingRimEffectView.getHeight() / AiFloatingWindow.this.mScreenSize.y;
                AiFloatingWindow.this.mAiShaderEffectRenderer.setBgBound(AiFloatingWindow.this.mBgBoundData);
                AiFloatingWindow.this.mAiShaderEffectRenderer.updateMaterial();
                AiFloatingWindow.this.mAiShaderEffectRenderer.applyPanelBgColors();
                AiFloatingWindow.this.mBgGradientColorView.setRenderEffect(AiFloatingWindow.this.mAiShaderEffectRenderer.getRenderEffect(8));
                AiFloatingWindow.this.mFloatingRimEffectView.setRenderEffect(AiFloatingWindow.this.mAiShaderEffectRenderer.getRenderEffect(9));
            }
        };
        this.mInputEffectRunnable = new Runnable() { // from class: com.miui.notes.ai.floatingwindow.AiFloatingWindow.2
            @Override // java.lang.Runnable
            public void run() {
                boolean z = AiFloatingWindow.this.mIsRecommendInput;
                AiFloatingWindow aiFloatingWindow = AiFloatingWindow.this;
                View view = z ? aiFloatingWindow.mRecommendView : aiFloatingWindow.mInputLayout;
                AiFloatingWindow aiFloatingWindow2 = AiFloatingWindow.this;
                View view2 = z ? aiFloatingWindow2.mRecommendInputContentView : aiFloatingWindow2.mInputContentView;
                AiFloatingWindow aiFloatingWindow3 = AiFloatingWindow.this;
                View view3 = z ? aiFloatingWindow3.mRecommendInputRimEffectView : aiFloatingWindow3.mInputRimEffectView;
                AiFloatingWindow aiFloatingWindow4 = AiFloatingWindow.this;
                int i = z ? aiFloatingWindow4.mRecommendBgPadding : aiFloatingWindow4.mEffectBgPadding;
                AiFloatingWindow.this.tick();
                AiFloatingWindow.this.mAiShaderEffectRenderer.setAnimTime(AiFloatingWindow.this.mTime);
                AiFloatingWindow.this.mAiShaderEffectRenderer.setForePanelWH(view2.getWidth(), view2.getHeight());
                AiFloatingWindow.this.mAiShaderEffectRenderer.setFloatingPanelWH(view3.getWidth(), view3.getHeight());
                AiFloatingWindow.this.mAiShaderEffectRenderer.setBgEffectViewWH(AiFloatingWindow.this.mBgGradientColorView.getWidth(), AiFloatingWindow.this.mBgGradientColorView.getHeight());
                int[] iArr = new int[2];
                view.getLocationInWindow(iArr);
                AiFloatingWindow.this.mBgBoundData[0] = view.getLeft() / AiFloatingWindow.this.mScreenSize.x;
                AiFloatingWindow.this.mBgBoundData[1] = 1.0f - (((iArr[1] + view.getHeight()) + i) / AiFloatingWindow.this.mScreenSize.y);
                AiFloatingWindow.this.mBgBoundData[2] = view.getWidth() / AiFloatingWindow.this.mScreenSize.x;
                if (AiFloatingWindow.this.mIsSupportCot) {
                    AiFloatingWindow.this.mBgBoundData[3] = (view.getHeight() + i) / AiFloatingWindow.this.mScreenSize.y;
                } else {
                    AiFloatingWindow.this.mBgBoundData[3] = (view.getHeight() + (i * 2.0f)) / AiFloatingWindow.this.mScreenSize.y;
                }
                if (AiFloatingWindow.this.mBgBoundData[1] < 0.0f) {
                    AiFloatingWindow.this.mBgBoundData[1] = 1.0f - ((iArr[1] + view.getHeight()) / AiFloatingWindow.this.mScreenSize.y);
                    AiFloatingWindow.this.mBgBoundData[3] = view.getHeight() / AiFloatingWindow.this.mScreenSize.y;
                }
                AiFloatingWindow.this.mAiShaderEffectRenderer.setBgBound(AiFloatingWindow.this.mBgBoundData);
                AiFloatingWindow.this.mAiShaderEffectRenderer.updateMaterial();
                AiFloatingWindow.this.mAiShaderEffectRenderer.applyInputStyleBgColors(z);
                AiFloatingWindow.this.mBgGradientColorView.setRenderEffect(AiFloatingWindow.this.mAiShaderEffectRenderer.getRenderEffect(8));
                view3.setRenderEffect(AiFloatingWindow.this.mAiShaderEffectRenderer.getRenderEffect(10));
            }
        };
        this.recyclingWindowAction = new Action() { // from class: com.miui.notes.ai.floatingwindow.AiFloatingWindow.3
            @Override // com.miui.notes.ai.Action
            public void run() {
                AiFloatingWindow.this.trackReportAiClose(UserInputHolder.getInstance().getRequestType(), AITextWidgetStat.AI_BACK_CLOSE);
                Logger.INSTANCE.d(AiFloatingWindow.TAG, "recyclingWindowAction mRequestState: " + AiFloatingWindow.this.mRequestState + " mDuringInputToPanel:" + AiFloatingWindow.this.mDuringInputToPanel + " ,mImeVisible：" + AiFloatingWindow.this.mImeVisible);
                if ((AiFloatingWindow.this.mRequestState == RequestState.STATE_INPUT || AiFloatingWindow.this.mDuringInputToPanel || AiFloatingWindow.this.mImeVisible) && AiFloatingWindow.this.mRequestState != RequestState.STATE_RECOMMEND) {
                    return;
                }
                AiFloatingWindow.this.detachFromWindow();
            }
        };
        this.mFirstInvokeFlow = false;
        this.mIsThinkContent = false;
        this.mIsNeedMatchThinkOneTrack = false;
        this.mFinishFlow = false;
        this.mOnFlowDone = false;
        this.startRequestTime = 0L;
        this.endRequestTime = 0L;
        this.mFirst = false;
        this.mIsDeleteLabel = false;
        this.mDeleteLabel = "";
        this.mThinkTime = 0L;
        this.mThinkCount = 0;
        this.mContext = context;
        if (Build.VERSION.SDK_INT >= 33) {
            this.mAiShaderEffectRenderer = new AiShaderEffectRenderer(context);
        }
        if (RomUtils.isInternationalBuild()) {
            str = "\n<text indent=\"1\"><s1-size>" + context.getString(R.string.ai_content_generates_reminders) + "</s1-size></text>";
        } else {
            str = "\n<text indent=\"1\"><s1-size>" + context.getString(R.string.ai_generate_tip) + "</s1-size></text>";
        }
        this.mAiEndTip = str;
        this.mAiEndGenerateTip = context.getString(RomUtils.isInternationalBuild() ? R.string.ai_content_generates_reminders : R.string.ai_generate_tip);
        mIsNoteAi = false;
    }

    public AiFloatingWindow(Context context, AiTextWidgetService aiTextWidgetService) {
        String str;
        this.misAiDeepSeekSelected = false;
        this.isKeyBoardShowing = false;
        this.mRecommendSize = 0;
        this.mRecommendDismiss = false;
        this.mImeVisible = false;
        this.mImeHeight = -1;
        this.mIsApplyShaderEffect = true;
        this.mEffectStartTime = System.nanoTime();
        this.mScreenSize = new Point();
        this.mBgBoundData = new float[4];
        this.mIsRecommendInput = false;
        this.mHasWebInit = false;
        this.mHasEditInit = false;
        this.mHasChangeInit = false;
        this.mMaxInputNumber = 200;
        this.isExceedNumberLimit = false;
        this.mMaxGenerateNumber = 2000;
        this.mMaxTextLengthNumber = 20000;
        this.mRequestState = RequestState.STATE_UNKNOWN;
        this.mPolishStyle = PolishStyle.TYPE_UNKNOWN;
        this.mWindowTouchRegionHelper = null;
        this.mHandler = new Handler(Looper.getMainLooper());
        this.mHasStopGenerating = false;
        this.mDuringRemoveView = false;
        this.lastOri = 0;
        this.currentOri = 0;
        this.mIsFromPrivate = false;
        this.isButtonClicked = false;
        this.lastRotation = -1;
        this.currentRotation = -1;
        this.lastScreenRect = null;
        this.currentScreenRect = null;
        this.lastNarrow = -1;
        this.currentNarrow = -1;
        this.mShaderState = -1;
        this.mAiTextWidgetService = null;
        this.mInputGlobalLayoutListener = null;
        this.aiPreferencesUtils = null;
        this.TYPE_GENERIC = 1;
        this.TYPE_PROFESSIONAL = 2;
        this.TYPE_EASY = 3;
        this.TYPE_FRIENDLY = 4;
        this.recieveNarrowChangeTime = -1L;
        this.inHighAnim = false;
        this.mIsClickStopGenerating = false;
        this.mCloudIsDevice = false;
        this.mIsSupportLLMWriting = false;
        this.mIsSupportCot = false;
        this.mIsCloudSupportCot = true;
        this.mIsRefreshGenerating = false;
        this.mIsShowing = false;
        this.mAIUseNum = 1;
        this.aiTextCount = 0;
        this.mAiOpenTime = 0L;
        this.mInputIncreasedHeight = 0;
        this.mLastGlobalTime = 0L;
        this.mTime = 0.0f;
        this.mDeltaTime = 0.0f;
        this.lastTriggeredTime = System.currentTimeMillis();
        this.mPanelEffectRunnable = new Runnable() { // from class: com.miui.notes.ai.floatingwindow.AiFloatingWindow.1
            @Override // java.lang.Runnable
            public void run() {
                AiFloatingWindow.this.tick();
                AiFloatingWindow.this.mAiShaderEffectRenderer.setAnimTime(AiFloatingWindow.this.mTime);
                if (AiFloatingWindow.this.mFloatingContentView != null) {
                    AiFloatingWindow.this.mAiShaderEffectRenderer.setForePanelWH(AiFloatingWindow.this.mFloatingContentView.getWidth(), AiFloatingWindow.this.mFloatingContentView.getHeight());
                }
                AiFloatingWindow.this.mAiShaderEffectRenderer.setFloatingPanelWH(AiFloatingWindow.this.mFloatingRimEffectView.getWidth(), AiFloatingWindow.this.mFloatingRimEffectView.getHeight());
                AiFloatingWindow.this.mAiShaderEffectRenderer.setBgEffectViewWH(AiFloatingWindow.this.mBgGradientColorView.getWidth(), AiFloatingWindow.this.mBgGradientColorView.getHeight());
                int[] iArr = new int[2];
                AiFloatingWindow.this.mFloatingRimEffectView.getLocationInWindow(iArr);
                AiFloatingWindow.this.mBgBoundData[0] = iArr[0] / AiFloatingWindow.this.mScreenSize.x;
                AiFloatingWindow.this.mBgBoundData[1] = 1.0f - ((iArr[1] + AiFloatingWindow.this.mFloatingRimEffectView.getHeight()) / AiFloatingWindow.this.mScreenSize.y);
                AiFloatingWindow.this.mBgBoundData[2] = AiFloatingWindow.this.mFloatingRimEffectView.getWidth() / AiFloatingWindow.this.mScreenSize.x;
                AiFloatingWindow.this.mBgBoundData[3] = AiFloatingWindow.this.mFloatingRimEffectView.getHeight() / AiFloatingWindow.this.mScreenSize.y;
                AiFloatingWindow.this.mAiShaderEffectRenderer.setBgBound(AiFloatingWindow.this.mBgBoundData);
                AiFloatingWindow.this.mAiShaderEffectRenderer.updateMaterial();
                AiFloatingWindow.this.mAiShaderEffectRenderer.applyPanelBgColors();
                AiFloatingWindow.this.mBgGradientColorView.setRenderEffect(AiFloatingWindow.this.mAiShaderEffectRenderer.getRenderEffect(8));
                AiFloatingWindow.this.mFloatingRimEffectView.setRenderEffect(AiFloatingWindow.this.mAiShaderEffectRenderer.getRenderEffect(9));
            }
        };
        this.mInputEffectRunnable = new Runnable() { // from class: com.miui.notes.ai.floatingwindow.AiFloatingWindow.2
            @Override // java.lang.Runnable
            public void run() {
                boolean z = AiFloatingWindow.this.mIsRecommendInput;
                AiFloatingWindow aiFloatingWindow = AiFloatingWindow.this;
                View view = z ? aiFloatingWindow.mRecommendView : aiFloatingWindow.mInputLayout;
                AiFloatingWindow aiFloatingWindow2 = AiFloatingWindow.this;
                View view2 = z ? aiFloatingWindow2.mRecommendInputContentView : aiFloatingWindow2.mInputContentView;
                AiFloatingWindow aiFloatingWindow3 = AiFloatingWindow.this;
                View view3 = z ? aiFloatingWindow3.mRecommendInputRimEffectView : aiFloatingWindow3.mInputRimEffectView;
                AiFloatingWindow aiFloatingWindow4 = AiFloatingWindow.this;
                int i = z ? aiFloatingWindow4.mRecommendBgPadding : aiFloatingWindow4.mEffectBgPadding;
                AiFloatingWindow.this.tick();
                AiFloatingWindow.this.mAiShaderEffectRenderer.setAnimTime(AiFloatingWindow.this.mTime);
                AiFloatingWindow.this.mAiShaderEffectRenderer.setForePanelWH(view2.getWidth(), view2.getHeight());
                AiFloatingWindow.this.mAiShaderEffectRenderer.setFloatingPanelWH(view3.getWidth(), view3.getHeight());
                AiFloatingWindow.this.mAiShaderEffectRenderer.setBgEffectViewWH(AiFloatingWindow.this.mBgGradientColorView.getWidth(), AiFloatingWindow.this.mBgGradientColorView.getHeight());
                int[] iArr = new int[2];
                view.getLocationInWindow(iArr);
                AiFloatingWindow.this.mBgBoundData[0] = view.getLeft() / AiFloatingWindow.this.mScreenSize.x;
                AiFloatingWindow.this.mBgBoundData[1] = 1.0f - (((iArr[1] + view.getHeight()) + i) / AiFloatingWindow.this.mScreenSize.y);
                AiFloatingWindow.this.mBgBoundData[2] = view.getWidth() / AiFloatingWindow.this.mScreenSize.x;
                if (AiFloatingWindow.this.mIsSupportCot) {
                    AiFloatingWindow.this.mBgBoundData[3] = (view.getHeight() + i) / AiFloatingWindow.this.mScreenSize.y;
                } else {
                    AiFloatingWindow.this.mBgBoundData[3] = (view.getHeight() + (i * 2.0f)) / AiFloatingWindow.this.mScreenSize.y;
                }
                if (AiFloatingWindow.this.mBgBoundData[1] < 0.0f) {
                    AiFloatingWindow.this.mBgBoundData[1] = 1.0f - ((iArr[1] + view.getHeight()) / AiFloatingWindow.this.mScreenSize.y);
                    AiFloatingWindow.this.mBgBoundData[3] = view.getHeight() / AiFloatingWindow.this.mScreenSize.y;
                }
                AiFloatingWindow.this.mAiShaderEffectRenderer.setBgBound(AiFloatingWindow.this.mBgBoundData);
                AiFloatingWindow.this.mAiShaderEffectRenderer.updateMaterial();
                AiFloatingWindow.this.mAiShaderEffectRenderer.applyInputStyleBgColors(z);
                AiFloatingWindow.this.mBgGradientColorView.setRenderEffect(AiFloatingWindow.this.mAiShaderEffectRenderer.getRenderEffect(8));
                view3.setRenderEffect(AiFloatingWindow.this.mAiShaderEffectRenderer.getRenderEffect(10));
            }
        };
        this.recyclingWindowAction = new Action() { // from class: com.miui.notes.ai.floatingwindow.AiFloatingWindow.3
            @Override // com.miui.notes.ai.Action
            public void run() {
                AiFloatingWindow.this.trackReportAiClose(UserInputHolder.getInstance().getRequestType(), AITextWidgetStat.AI_BACK_CLOSE);
                Logger.INSTANCE.d(AiFloatingWindow.TAG, "recyclingWindowAction mRequestState: " + AiFloatingWindow.this.mRequestState + " mDuringInputToPanel:" + AiFloatingWindow.this.mDuringInputToPanel + " ,mImeVisible：" + AiFloatingWindow.this.mImeVisible);
                if ((AiFloatingWindow.this.mRequestState == RequestState.STATE_INPUT || AiFloatingWindow.this.mDuringInputToPanel || AiFloatingWindow.this.mImeVisible) && AiFloatingWindow.this.mRequestState != RequestState.STATE_RECOMMEND) {
                    return;
                }
                AiFloatingWindow.this.detachFromWindow();
            }
        };
        this.mFirstInvokeFlow = false;
        this.mIsThinkContent = false;
        this.mIsNeedMatchThinkOneTrack = false;
        this.mFinishFlow = false;
        this.mOnFlowDone = false;
        this.startRequestTime = 0L;
        this.endRequestTime = 0L;
        this.mFirst = false;
        this.mIsDeleteLabel = false;
        this.mDeleteLabel = "";
        this.mThinkTime = 0L;
        this.mThinkCount = 0;
        this.mContext = context;
        this.mAiTextWidgetService = aiTextWidgetService;
        if (Build.VERSION.SDK_INT >= 33) {
            this.mAiShaderEffectRenderer = new AiShaderEffectRenderer(context);
        }
        if (RomUtils.isInternationalBuild()) {
            str = "\n<text indent=\"1\"><s1-size>" + context.getString(R.string.ai_content_generates_reminders) + "</s1-size></text>";
        } else {
            str = "\n<text indent=\"1\"><s1-size>" + context.getString(R.string.ai_generate_tip) + "</s1-size></text>";
        }
        this.mAiEndTip = str;
        this.mAiEndGenerateTip = context.getString(RomUtils.isInternationalBuild() ? R.string.ai_content_generates_reminders : R.string.ai_generate_tip);
        mIsNoteAi = false;
    }

    private void initializeShaderEffect() {
        this.mEffectBgPadding = this.mAiShaderEffectRenderer.getInputBgEffectBoundsPadding(this.mContext, false);
        this.mRecommendBgPadding = this.mAiShaderEffectRenderer.getInputBgEffectBoundsPadding(this.mContext, true);
        DisplayMetrics screenMetrics = DisplayUtil.getScreenMetrics(this.mContext);
        this.mScreenSize.set(screenMetrics.widthPixels, screenMetrics.heightPixels);
        tick();
        this.mAiShaderEffectRenderer.setUniforms(this.mTime, 2);
        FrameUpdateMonitor.INSTANCE.startMonitor(new Function1() { // from class: com.miui.notes.ai.floatingwindow.AiFloatingWindow$$ExternalSyntheticLambda5
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                return this.f$0.lambda$initializeShaderEffect$1((Float) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ Unit lambda$initializeShaderEffect$1(Float f) {
        if (this.mPreUpdateTime >= f.floatValue() || f.floatValue() - this.mPreUpdateTime <= 33.0f) {
            return null;
        }
        this.mPreUpdateTime = f.floatValue();
        int i = this.mShaderState;
        if (i == 1) {
            this.mPanelEffectRunnable.run();
            return null;
        }
        if (i != 2) {
            return null;
        }
        if (!this.mCloudIsDevice && !this.mIsSupportCot) {
            return null;
        }
        this.mInputEffectRunnable.run();
        return null;
    }

    private void configureBasicState(String str, String str2, boolean z) {
        resetTime();
        initAppObserver();
        int i = !DisplayUtil.isLandScape(this.mContext) ? 1 : 0;
        this.currentOri = i;
        this.lastOri = i;
        this.mNoteTitle = NoteSchemaUtil.extractPlainText(str);
        this.mNoteContent = NoteSchemaUtil.extractPlainText(str2);
        this.mIsFromPrivate = z;
        if (RomUtils.isFoldDevice()) {
            boolean zIsInNarrowScreen = ScreenPosistionUtils.isInNarrowScreen(this.mContext);
            this.currentNarrow = zIsInNarrowScreen ? 1 : 0;
            this.lastNarrow = zIsInNarrowScreen ? 1 : 0;
        }
    }

    private void configureWindowState(String str, Rect rect, int i, String str2) {
        this.aiSessionId = UUID.randomUUID().toString();
        this.mSelectedText = str;
        this.mSelectCount = NoteSchemaUtil.getPlainTextCount(str);
        this.mHostTaskId = i;
        this.mHostPackageName = str2;
        UserInputHolder.getInstance().setContentInput(str);
        this.mWindowPosition = ScreenPosistionUtils.calWindowPosition(rect, this.mContext, i);
        Rect rectIsHostInFreeMode = FreeModeUtils.isHostInFreeMode(i);
        this.smallWindowRect = rectIsHostInFreeMode;
        if (rectIsHostInFreeMode != null) {
            int rotationFreeMode = FreeModeUtils.getRotationFreeMode(i);
            this.currentRotation = rotationFreeMode;
            this.lastRotation = rotationFreeMode;
        }
    }

    private void initNetworkCheck() {
        AINetWorkUtils aINetWorkUtils = new AINetWorkUtils(this.mContext, this.mCloudIsDevice, this.mIsSupportLLMWriting);
        this.mAINetWorkUtils = aINetWorkUtils;
        aINetWorkUtils.initManger();
    }

    private void showDeviceDialog() {
        showLLMSearchDeviceDialog();
    }

    private void initAndShowWindow() {
        initAndShowFloatingWindow();
    }

    public void showAiFloatingWindow(String str, Rect rect, int i, String str2, String str3, String str4, boolean z) {
        configureBasicState(str3, str4, z);
        configureWindowState(str, rect, i, str2);
        Logger.INSTANCE.i(TAG, "showAiFloatingWindow, text size:" + (str == null ? 0 : str.length()));
        this.mIsSupportLLMWriting = RomUtils.isLLMAIWriting(this.mContext);
        boolean z2 = (PreferenceUtils.isAcceptedMode(this.mContext) || this.mIsSupportLLMWriting) ? false : true;
        this.mCloudIsDevice = z2;
        this.mIsSupportCot = (!z2 && (RomUtils.isSupportLLMCotAI(this.mContext) || this.mIsSupportLLMWriting)) || this.mIsCloudSupportCot;
        initNetworkCheck();
        if (this.mIsSupportLLMWriting && !this.mAINetWorkUtils.textEditCheck()) {
            showDeviceDialog();
        } else {
            initAndShowWindow();
        }
    }

    private void initAndShowFloatingWindow() {
        Logger.INSTANCE.d(TAG, "initAndShowFloatingWindow mWindowManager :" + this.mWindowManager + "  ,mRootView : " + this.mRootView);
        if (RomUtils.isFoldDevice()) {
            Rect rect = this.smallWindowRect;
            this.currentScreenRect = rect;
            this.lastScreenRect = rect;
        }
        if (this.mWindowManager == null && this.mRootView == null) {
            initView();
            AiNoteRequest.INSTANCE.init(this.mRootView.getContext().getApplicationContext());
            lambda$setOnScreenChange$23(DisplayUtil.isLandScape(this.mContext));
            setRecommendPanelState(DisplayUtil.isLandScape(this.mContext));
            addToWindow();
            setTouchableRegion();
            handleTouchEvent();
            handleImeChanged();
            registerNavigationKeyEventReceiver();
            registerBackEventReceiver();
        }
        if (TextUtils.isEmpty(this.mSelectedText)) {
            setUiState(RequestState.STATE_RECOMMEND);
        } else {
            setUiState(RequestState.STATE_PANEL);
        }
    }

    private void initView() {
        if (this.mIsSupportCot) {
            CustomAiTextWidgetEditText.isCotMode = true;
            CustomAiTextWidgetEditText.padding = (int) this.mContext.getResources().getDimension(R.dimen.ai_edit_cot_padding);
            CustomAiTextWidgetEditText.viewMaxHeight = ((int) this.mContext.getResources().getDimension(R.dimen.ai_recommend_layout_edit_max_cot_height)) - (CustomAiTextWidgetEditText.padding * 2);
        } else {
            CustomAiTextWidgetEditText.isCotMode = false;
            CustomAiTextWidgetEditText.padding = (int) this.mContext.getResources().getDimension(R.dimen.ai_edit_padding);
            CustomAiTextWidgetEditText.viewMaxHeight = ((int) this.mContext.getResources().getDimension(R.dimen.ai_recommend_layout_edit_max_height)) - (CustomAiTextWidgetEditText.padding * 2);
        }
        this.cotInputwidth = (int) this.mContext.getResources().getDimension(R.dimen.ai_edit_cot_padding);
        this.misAiDeepSeekSelected = PreferenceUtils.getAiModelSwitch(this.mContext);
        FloatingDecorView floatingDecorView = (FloatingDecorView) LayoutInflater.from(this.mContext).inflate(R.layout.floating_window_layout, (ViewGroup) null);
        this.mRootView = floatingDecorView;
        this.mFloatingView = floatingDecorView.findViewById(R.id.floating_view);
        this.mInputLayout = this.mRootView.findViewById(R.id.input_layout);
        TextView textView = (TextView) this.mRootView.findViewById(R.id.ai_title);
        this.mPanelAiTitle = textView;
        textView.setAccessibilityTraversalBefore(R.id.close_button);
        this.mPanelAiTitleEnd = (TextView) this.mRootView.findViewById(R.id.ai_title_end);
        this.mPanelTitleStart = (TextView) this.mRootView.findViewById(R.id.ai_title_start);
        View viewFindViewById = this.mRootView.findViewById(R.id.close_button);
        this.mCloseButton = viewFindViewById;
        viewFindViewById.setAccessibilityTraversalBefore(R.id.input);
        CustomAiTextWidgetEditText customAiTextWidgetEditText = (CustomAiTextWidgetEditText) this.mRootView.findViewById(R.id.input);
        this.mInput = customAiTextWidgetEditText;
        customAiTextWidgetEditText.setOnClickListener(this);
        this.mInput.setAccessibilityTraversalBefore(R.id.ai_summary_material_effect);
        TextExtKt.setEditProperty(this.mInput, "400");
        TextExtKt.setTextProperty(this.mPanelAiTitle, "400");
        this.mFloatingContentView = this.mRootView.findViewById(R.id.floating_content_view);
        this.mBgGradientColorViewContainer = (RenderViewLayout) this.mRootView.findViewById(R.id.bg_effect_view_container);
        View view = new View(this.mContext);
        this.mBgGradientColorView = view;
        this.mBgGradientColorViewContainer.attachView(view, 0.5f);
        this.mFloatingRimEffectView = this.mRootView.findViewById(R.id.floating_rim_view);
        this.mAiInputIconView = this.mRootView.findViewById(R.id.ai_input_icon_image);
        this.mAiInputDeepSeekText = (TextView) this.mRootView.findViewById(R.id.ai_input_DeepSeek_button_text);
        this.mAiDeepSeekButtonIcon = this.mRootView.findViewById(R.id.ai_input_icon);
        if (PreferenceUtils.getAiModelSwitch(this.mContext)) {
            this.mAiInputDeepSeekText.setTextColor(this.mContext.getResources().getColor(R.color.ai_deepseek_button_text_color));
        } else {
            this.mAiInputDeepSeekText.setTextColor(this.mContext.getResources().getColor(R.color.ai_deepseek_button_unchecked_text));
        }
        this.mInputSendIconImage = (ImageView) this.mRootView.findViewById(R.id.ai_input_send_icon_image);
        AccessibilityDelegateUtil.setButtonAccessibilityDelegate(this.mCloseButton);
        if (this.mCloudIsDevice) {
            this.mInput.setImeOptions(33554432);
            if (KeyboardListenerHelper.isHardwareKeyboard(this.mContext)) {
                this.mInput.setImeOptions(4);
                this.mInput.setOnKeyListener(new View.OnKeyListener() { // from class: com.miui.notes.ai.floatingwindow.AiFloatingWindow$$ExternalSyntheticLambda8
                    @Override // android.view.View.OnKeyListener
                    public final boolean onKey(View view2, int i, KeyEvent keyEvent) {
                        return this.f$0.lambda$initView$2(view2, i, keyEvent);
                    }
                });
            }
            this.mInput.addTextChangedListener(new TextWatcher() { // from class: com.miui.notes.ai.floatingwindow.AiFloatingWindow.4
                @Override // android.text.TextWatcher
                public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {
                }

                @Override // android.text.TextWatcher
                public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
                }

                @Override // android.text.TextWatcher
                public void afterTextChanged(Editable editable) {
                    if (AiFloatingWindow.this.mInput.getText().toString().isEmpty()) {
                        AiFloatingWindow.this.mInputSendIconImage.setImageDrawable(AiFloatingWindow.this.mContext.getDrawable(R.drawable.ai_input_send_icon));
                    } else {
                        AiFloatingWindow.this.mInputSendIconImage.setImageDrawable(AiFloatingWindow.this.mContext.getDrawable(R.drawable.ai_input_send_normalcy_icon));
                    }
                }
            });
        } else {
            this.mInput.addTextChangedListener(new TextWatcher() { // from class: com.miui.notes.ai.floatingwindow.AiFloatingWindow.5
                @Override // android.text.TextWatcher
                public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {
                }

                @Override // android.text.TextWatcher
                public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
                }

                @Override // android.text.TextWatcher
                public void afterTextChanged(Editable editable) {
                    if (AiFloatingWindow.this.mInput.getText().toString().isEmpty()) {
                        AiFloatingWindow.this.mInputSendIconImage.setImageDrawable(AiFloatingWindow.this.mContext.getDrawable(R.drawable.ai_input_send_icon));
                    } else {
                        AiFloatingWindow.this.mInputSendIconImage.setImageDrawable(AiFloatingWindow.this.mContext.getDrawable(R.drawable.ai_input_send_normalcy_icon));
                    }
                }
            });
        }
        if (KeyboardListenerHelper.isHardwareKeyboard(this.mContext)) {
            this.mCloseButton.setOnKeyListener(new View.OnKeyListener() { // from class: com.miui.notes.ai.floatingwindow.AiFloatingWindow$$ExternalSyntheticLambda9
                @Override // android.view.View.OnKeyListener
                public final boolean onKey(View view2, int i, KeyEvent keyEvent) {
                    return this.f$0.lambda$initView$3(view2, i, keyEvent);
                }
            });
        }
        this.mRootView.setOnScreenChangeListener(this);
        if (TextUtils.isEmpty(this.mSelectedText)) {
            initAIView();
        } else {
            initEditView();
            changeInit();
        }
        trackReportAiOpen();
        this.mRootView.post(new Runnable() { // from class: com.miui.notes.ai.floatingwindow.AiFloatingWindow$$ExternalSyntheticLambda10
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.initAdvancedMaterialEffects();
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ boolean lambda$initView$2(View view, int i, KeyEvent keyEvent) {
        if (keyEvent.getAction() != 0 || i != 66) {
            return false;
        }
        if (keyEvent.isAltPressed()) {
            this.mInput.append(StringUtils.LF);
            return true;
        }
        if (!this.mHasTouchInput) {
            return false;
        }
        if (!TextUtils.isEmpty(this.mInput.getText().toString())) {
            if (inputBoxCommandWordLimit(this.mInput)) {
                return false;
            }
            clearEditorFlowState();
            UserInputHolder.getInstance().setEditInput(this.mInput.getText().toString());
            UserInputHolder.getInstance().setRequestType(RequestType.TYPE_INPUT);
            SoftInputUtil.hideSoftInput(this.mContext, this.mInput);
            this.mPanelAiTitle.setText("");
            setUiState(RequestState.STATE_LOADING);
            startRequestAiData();
        }
        return true;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ boolean lambda$initView$3(View view, int i, KeyEvent keyEvent) {
        if (this.mRequestState != RequestState.STATE_COMPLETE && this.mRequestState != RequestState.STATE_ERROR && this.mRequestState != RequestState.STATE_PANEL) {
            return false;
        }
        if (i != 66 && i != 62) {
            return false;
        }
        Logger.INSTANCE.i(TAG, "拦截外接键盘，enter键空/格键触发view");
        return true;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void initAdvancedMaterialEffects() {
        if (MiuiBlurUtils.isEffectEnable(this.mContext)) {
            Logger.INSTANCE.d(TAG, "initAdvancedMaterialEffects, panel enable: " + MaterialEffectUtil.setPassWindowBlurEnabled(this.mFloatingContentView, true));
            if (TextUtils.isEmpty(this.mSelectedText)) {
                MaterialEffectUtil.setPassWindowBlurEnabled(this.mRecommendInputContentView, true);
                for (View view : this.mRecommendContainers) {
                    MaterialEffectUtil.setPassWindowBlurEnabled(view, true);
                }
            } else {
                MaterialEffectUtil.setPassWindowBlurEnabled(this.mInputContentView, true);
            }
            Logger.INSTANCE.d(TAG, "initAdvancedMaterialEffects, support material effect: " + MiuiBlurUtils.isEffectEnable(this.mContext));
        }
    }

    private void initEditor() {
        if (this.mGeneratingInflate.findViewById(R.id.ai_webview) != null) {
            this.mAiEditor = new AiEditor(this.mContext, (WebView) this.mGeneratingInflate.findViewById(R.id.ai_webview), this);
        }
    }

    private boolean isXiaomiAccountLogin() {
        return ExtraAccountManager.isLogin(this.mContext);
    }

    public WebView getAiWebView() {
        return this.mAiWebView;
    }

    public View getGeneratingView() {
        return this.mGeneratingView;
    }

    public View getFloatingContentView() {
        return this.mFloatingContentView;
    }

    public View getTitleLayoutView() {
        return this.mTitleLayout;
    }

    public View getAiInsertView() {
        return this.mAiInsert;
    }

    public View getAiErrorImage() {
        return this.mAiErrorImage;
    }

    public View getAiCopyView() {
        return this.mAiCopy;
    }

    public View getAiDoneView() {
        return this.mAiDone;
    }

    public View getAiRefreshView() {
        return this.mAiRefresh;
    }

    public AiButtomItem getAiRefreshTextView() {
        return this.mAiRefreshText;
    }

    public View getAiBackView() {
        return this.mAiBack;
    }

    public AiButtomItem getAiBackTextView() {
        return this.mAiBackText;
    }

    public View getGeneratingStopView() {
        return this.mGeneratingStop;
    }

    public TextView getPanelTitleView() {
        return this.mPanelAiTitle;
    }

    @Override // android.view.View.OnClickListener
    public void onClick(View view) {
        AiFloatingWindow aiFloatingWindow;
        int id = view.getId();
        changeInit();
        if (this.isButtonClicked && !this.isClickReturned) {
            Logger.INSTANCE.i(TAG, " isButtonClicked is true just return onClick view is " + id);
            this.isClickReturned = true;
            return;
        }
        this.isClickReturned = false;
        this.isButtonClicked = true;
        this.mHandler.postDelayed(new Runnable() { // from class: com.miui.notes.ai.floatingwindow.AiFloatingWindow$$ExternalSyntheticLambda36
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.lambda$onClick$4();
            }
        }, 50L);
        this.mInput.clearFocus();
        if (this.mHasStopGenerating && !TextUtils.isEmpty(this.mAiSavedData) && id == R.id.ai_refesh && (this.mRequestState == RequestState.STATE_COMPLETE || this.mRequestState == RequestState.STATE_ERROR)) {
            return;
        }
        if (id == R.id.ai_summary_material_effect) {
            UserInputHolder.getInstance().setRequestType(RequestType.TYPE_SUMMARY);
            selectAllTextIfNecessary();
            clearEditorFlowState();
            setUiState(RequestState.STATE_LOADING);
            startRequestAiData();
            return;
        }
        if (id == R.id.ai_check_material_effect) {
            UserInputHolder.getInstance().setRequestType(RequestType.TYPE_CHECK);
            selectAllTextIfNecessary();
            clearEditorFlowState();
            setUiState(RequestState.STATE_LOADING);
            startRequestAiData();
            return;
        }
        if (id == R.id.ai_extension_material_effect) {
            UserInputHolder.getInstance().setRequestType(RequestType.TYPE_EXTENSION);
            clearEditorFlowState();
            selectAllTextIfNecessary();
            setUiState(RequestState.STATE_LOADING);
            startRequestAiData();
            return;
        }
        if (id == R.id.ai_general || id == R.id.ai_professional || id == R.id.ai_easy || id == R.id.ai_friendly) {
            UserInputHolder.getInstance().setRequestType(RequestType.TYPE_POLISH);
            if (id == R.id.ai_general) {
                this.mPolishStyle = PolishStyle.TYPE_COMMON;
                UserInputHolder.getInstance().setPolishStyle(PolishStyle.TYPE_COMMON);
            } else if (id == R.id.ai_professional) {
                this.mPolishStyle = PolishStyle.TYPE_PROFESSIONAL;
                UserInputHolder.getInstance().setPolishStyle(PolishStyle.TYPE_PROFESSIONAL);
            } else if (id == R.id.ai_easy) {
                this.mPolishStyle = PolishStyle.TYPE_EASY;
                UserInputHolder.getInstance().setPolishStyle(PolishStyle.TYPE_EASY);
            } else if (id == R.id.ai_friendly) {
                this.mPolishStyle = PolishStyle.TYPE_FRIENDLY;
                UserInputHolder.getInstance().setPolishStyle(PolishStyle.TYPE_FRIENDLY);
            }
            selectAllTextIfNecessary();
            clearEditorFlowState();
            setUiState(RequestState.STATE_LOADING);
            startRequestAiData();
            return;
        }
        if (id == R.id.ai_back) {
            if (UserInputHolder.getInstance().getRequestType() == RequestType.TYPE_RECOMMEND) {
                backToRecommend();
            } else {
                setUiState(RequestState.STATE_PANEL);
            }
            trackReportAiUsedOrAiCanceled(UserInputHolder.getInstance().getRequestType(), AITextWidgetStat.AI_CANCEL, true);
            MiuiFolme.goneView(this.mAiErrorImage);
            cancelAiAction(false);
            return;
        }
        if (id == R.id.ai_refesh) {
            this.mIsRefreshGenerating = true;
            clearEditorFlowState();
            setUiState(RequestState.STATE_LOADING);
            MiuiFolme.goneView(this.mAiErrorImage);
            refreshAiAction();
            startRequestAiData();
            return;
        }
        if (id == R.id.ai_copy) {
            Logger.INSTANCE.i(TAG, "doCopy");
            String str = this.mAiSavedData;
            if (str == null || str.isEmpty()) {
                return;
            }
            this.mAiEditor.doCopy();
            detachFromWindow();
            mElementName = AITextWidgetStat.AI_COPY;
            trackReportAiUsedOrAiCanceled(UserInputHolder.getInstance().getRequestType(), mElementName, false);
            trackReportAiClose(UserInputHolder.getInstance().getRequestType(), AITextWidgetStat.AI_USED_CLOSE);
            mElementName = null;
            Toast.makeText(this.mContext, R.string.ai_already_copy_toast_text, 0).show();
            return;
        }
        if (id == R.id.ai_done || id == R.id.close_button) {
            if (this.mFirstInvokeFlow) {
                aiFloatingWindow = this;
                aiFloatingWindow.trackReportAiEnd(UserInputHolder.getInstance().getRequestType(), 0, AITextWidgetStat.CLOSURE_WINDOW, AITextWidgetStat.CLOSURE_WINDOW, AITextWidgetStat.CLOSURE_WINDOW);
            } else {
                aiFloatingWindow = this;
            }
            if (aiFloatingWindow.mRequestState == RequestState.STATE_COMPLETE && id == R.id.close_button) {
                aiFloatingWindow.trackReportAiUsedOrAiCanceled(UserInputHolder.getInstance().getRequestType(), AITextWidgetStat.AI_CANCEL, true);
            }
            aiFloatingWindow.trackReportAiClose(UserInputHolder.getInstance().getRequestType(), AITextWidgetStat.AI_BTN_CLOSE);
            aiFloatingWindow.detachFromWindow();
            return;
        }
        if (id == R.id.container_generating_stop) {
            Logger.INSTANCE.i(TAG, "stop, mOnFlowDone:" + this.mOnFlowDone + " visibility:" + this.mGeneratingStop.getVisibility());
            if (this.mOnFlowDone || this.mGeneratingStop.getVisibility() == 8) {
                return;
            }
            stopGenerating(this.mFirstInvokeFlow ? "" : this.mAiEndGenerateTip);
            this.mIsClickStopGenerating = true;
            setUiState(RequestState.STATE_COMPLETE);
            return;
        }
        if (id == R.id.ai_input_send_icon_container) {
            if (TextUtils.isEmpty(this.mInput.getText().toString())) {
                return;
            }
            SoftInputUtil.hideSoftInput(this.mContext, this.mInput);
            if (inputBoxCommandWordLimit(this.mInput)) {
                return;
            }
            clearEditorFlowState();
            UserInputHolder.getInstance().setEditInput(this.mInput.getText().toString());
            UserInputHolder.getInstance().setRequestType(RequestType.TYPE_INPUT);
            this.mPanelAiTitle.setText("");
            setUiState(RequestState.STATE_LOADING);
            startRequestAiData();
            return;
        }
        if (id == R.id.ai_recommend_send_icon) {
            if (TextUtils.isEmpty(this.mRecommendEdit.getText().toString())) {
                return;
            }
            SoftInputUtil.hideSoftInput(this.mContext, this.mRecommendEdit);
            if (inputBoxCommandWordLimit(this.mRecommendEdit)) {
                return;
            }
            clearEditorFlowState();
            UserInputHolder.getInstance().setEditInput(this.mRecommendEdit.getText().toString());
            UserInputHolder.getInstance().setRequestType(RequestType.TYPE_RECOMMEND);
            recommendSendAnim();
            return;
        }
        if (id == R.id.ai_input_DeepSeek_button_container) {
            if (!this.misAiDeepSeekSelected) {
                this.mAIInputDeepSeekButton.setSelected(true);
                this.mAiInputDeepSeekText.setTextColor(this.mContext.getResources().getColor(R.color.ai_deepseek_button_text_color));
                this.misAiDeepSeekSelected = true;
                PreferenceUtils.setAiModelSwitch(this.mContext, true);
            } else {
                this.mAIInputDeepSeekButton.setSelected(false);
                this.mAiInputDeepSeekText.setTextColor(this.mContext.getResources().getColor(R.color.ai_deepseek_button_unchecked_text));
                this.misAiDeepSeekSelected = false;
                PreferenceUtils.setAiModelSwitch(this.mContext, false);
            }
            this.mInput.requestFocus();
            trackReportDeepSeekClick();
            return;
        }
        if (id == R.id.ai_recommend_DeepSeek_container) {
            if (!this.misAiDeepSeekSelected) {
                this.mRecommendDeepSeekButton.setSelected(true);
                this.mRecommendDeepSeekButtonText.setTextColor(this.mContext.getResources().getColor(R.color.ai_deepseek_button_text_color));
                this.misAiDeepSeekSelected = true;
                PreferenceUtils.setAiModelSwitch(this.mContext, true);
            } else {
                this.mRecommendDeepSeekButton.setSelected(false);
                this.mRecommendDeepSeekButtonText.setTextColor(this.mContext.getResources().getColor(R.color.ai_deepseek_button_unchecked_text));
                this.misAiDeepSeekSelected = false;
                PreferenceUtils.setAiModelSwitch(this.mContext, false);
            }
            trackReportDeepSeekClick();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onClick$4() {
        this.isButtonClicked = false;
    }

    protected void clearEditorFlowState() {
        IAiEditor iAiEditor = this.mAiEditor;
        if (iAiEditor != null) {
            iAiEditor.clearFlowStatus();
        }
    }

    protected void startRequestAiData() {
        Logger.INSTANCE.d(TAG, "startRequestAiData");
        this.startRequestTime = System.currentTimeMillis();
        trackReportAiStart(UserInputHolder.getInstance().getRequestType());
        trackReportAiUse(UserInputHolder.getInstance().getRequestType());
        boolean z = false;
        this.mFirst = false;
        this.mFirstInvokeFlow = true;
        this.mFinishFlow = false;
        this.mOnFlowDone = false;
        this.mHasStopGenerating = false;
        this.mAiSavedData = null;
        this.mAiDoneData = null;
        this.mLastFlowData = null;
        this.mAiTitleData = null;
        mHasFlowData = false;
        this.mIsDeleteLabel = false;
        this.mDeleteLabel = "";
        this.mThinkTime = 0L;
        this.mThinkCount = 0;
        mCorrectMisNum = 0;
        if (this.misAiDeepSeekSelected && this.mIsSupportCot && (UserInputHolder.getInstance().getRequestType() == RequestType.TYPE_INPUT || UserInputHolder.getInstance().getRequestType() == RequestType.TYPE_RECOMMEND)) {
            z = true;
        }
        this.mIsThinkContent = z;
        this.mIsNeedMatchThinkOneTrack = z;
        AINetWorkUtils aINetWorkUtils = this.mAINetWorkUtils;
        if (aINetWorkUtils != null) {
            aINetWorkUtils.stopRequest();
        }
        if (this.mCloudIsDevice) {
            AINetWorkUtils aINetWorkUtils2 = new AINetWorkUtils(this.mContext, this.mCloudIsDevice, this.mIsSupportLLMWriting);
            this.mAINetWorkUtils = aINetWorkUtils2;
            aINetWorkUtils2.startRequest(this, this.misAiDeepSeekSelected);
        } else {
            AINetWorkUtils aINetWorkUtils3 = this.mAINetWorkUtils;
            if (aINetWorkUtils3 != null) {
                aINetWorkUtils3.startRequest(this, this.misAiDeepSeekSelected);
            }
            this.mHandler.postDelayed(new Runnable() { // from class: com.miui.notes.ai.floatingwindow.AiFloatingWindow.6
                @Override // java.lang.Runnable
                public void run() {
                    Logger.INSTANCE.d(AiFloatingWindow.TAG, "mRequestState ： " + AiFloatingWindow.this.mRequestState + " , mHasFlowData :" + AiFloatingWindow.mHasFlowData);
                    if (AiFloatingWindow.this.mRequestState != RequestState.STATE_LOADING || AiFloatingWindow.mHasFlowData || AiFloatingWindow.this.mPanelAiTitle == null) {
                        return;
                    }
                    Logger.INSTANCE.d(AiFloatingWindow.TAG, "local AI waiting for more than 2.5S, change title");
                    AiFloatingWindow.this.mPanelAiTitle.setText(AiFloatingWindow.this.mContext.getString(R.string.aicr_generate_waiting_title));
                }
            }, 2500L);
        }
        this.mStartRequestTime = System.currentTimeMillis();
    }

    public void listenString(final AiOutputData aiOutputData) {
        Logger.INSTANCE.i(TAG, "listenString, [DONE].equals(result.resultContentStream): " + GlobalAiTextRequestManager.FLOW_DONE_TAG.equals(aiOutputData.resultContentStream));
        if (this.mRequestState == RequestState.STATE_COMPLETE) {
            Logger.INSTANCE.i(TAG, "listenString STATE_COMPLETE,return");
        } else {
            this.mHandler.post(new Runnable() { // from class: com.miui.notes.ai.floatingwindow.AiFloatingWindow.7
                @Override // java.lang.Runnable
                public void run() {
                    String str;
                    if (!AiFloatingWindow.this.mFirst) {
                        AiFloatingWindow.this.endRequestTime = System.currentTimeMillis();
                        AiFloatingWindow.this.mFirst = true;
                    }
                    Logger.INSTANCE.i(AiFloatingWindow.TAG, "real listenString");
                    if (AiFloatingWindow.this.mAiEditor != null) {
                        AiFloatingWindow.mAiTraceId = aiOutputData.traceId;
                        AiFloatingWindow.mHasFlowData = true;
                        if (GlobalAiTextRequestManager.FLOW_DONE_TAG.equals(aiOutputData.resultContentStream)) {
                            if (AiFloatingWindow.this.mFirstInvokeFlow) {
                                AiFloatingWindow.this.mAiEditor.addText(StringUtils.SPACE, true, false);
                            }
                            AiFloatingWindow.this.mAiDoneData = aiOutputData.resultContent;
                            if (!TextUtils.isEmpty(aiOutputData.theme)) {
                                AiFloatingWindow.this.mAiDoneData = aiOutputData.theme + StringUtils.LF + aiOutputData.resultContent;
                            }
                            if (!TextUtils.isEmpty(NoteSchemaUtil.hasThemeLabel(aiOutputData.resultContent))) {
                                AiFloatingWindow aiFloatingWindow = AiFloatingWindow.this;
                                aiFloatingWindow.mAiDoneData = aiFloatingWindow.mAiDoneData.replaceAll("<theme>(.*?)</theme>", "").trim();
                            }
                            AiFloatingWindow.mCorrectMisNum = aiOutputData.correctNums;
                            Logger.INSTANCE.i(AiFloatingWindow.TAG, "listenString flow real listenString done");
                            return;
                        }
                        if (aiOutputData.reasoning != null) {
                            Logger.INSTANCE.i(AiFloatingWindow.TAG, "listenString, reasoning length: " + aiOutputData.reasoning.length());
                            str = aiOutputData.reasoning;
                            AiFloatingWindow.this.mThinkCount += str.length();
                        } else {
                            str = aiOutputData.resultContentStream;
                            if (AiFloatingWindow.this.mIsThinkContent && AiFloatingWindow.this.mCloudIsDevice) {
                                AiFloatingWindow.this.mThinkTime = System.currentTimeMillis() - AiFloatingWindow.this.mStartRequestTime;
                                AiFloatingWindow.this.mIsThinkContent = false;
                                AiFloatingWindow.this.mFirstInvokeFlow = true;
                            }
                        }
                        if (TextUtils.isEmpty(str)) {
                            return;
                        }
                        if (!TextUtils.isEmpty(NoteSchemaUtil.hasDeleteLabel(str))) {
                            Logger.INSTANCE.i(AiFloatingWindow.TAG, "listenString, hasDeleteLabel: " + NoteSchemaUtil.hasDeleteLabel(str));
                            AiFloatingWindow.this.mAiEditor.addText(StringUtils.SPACE, false, AiFloatingWindow.this.mIsThinkContent);
                            AiFloatingWindow.this.mIsDeleteLabel = true;
                            AiFloatingWindow.this.mDeleteLabel = NoteSchemaUtil.hasDeleteLabel(str);
                        } else {
                            if (AiFloatingWindow.this.mIsThinkContent && "</think>".equals(aiOutputData.resultContentStream)) {
                                Logger.INSTANCE.i(AiFloatingWindow.TAG, "listenString, is </think>: " + aiOutputData.resultContentStream);
                                AiFloatingWindow.this.mIsThinkContent = false;
                                AiFloatingWindow.this.mFirstInvokeFlow = true;
                            }
                            if (!TextUtils.isEmpty(NoteSchemaUtil.hasOneTrackLabel(str))) {
                                int[] iArrMatchOneTrackLabel = NoteSchemaUtil.matchOneTrackLabel(str);
                                AiFloatingWindow.this.mThinkTime = iArrMatchOneTrackLabel[0];
                                AiFloatingWindow.this.mThinkCount = iArrMatchOneTrackLabel[1];
                            } else if (AiFloatingWindow.this.mFirstInvokeFlow) {
                                AiFloatingWindow.this.mAiEditor.addText(str, true, AiFloatingWindow.this.mIsThinkContent);
                                AiFloatingWindow.this.mFirstInvokeFlow = false;
                            } else {
                                AiFloatingWindow.this.mAiEditor.addText(str, false, AiFloatingWindow.this.mIsThinkContent);
                            }
                        }
                        Logger.INSTANCE.i(AiFloatingWindow.TAG, "real listenString flow");
                        MiuiFolme.visibleView(AiFloatingWindow.this.mAiWaterMarkView);
                    }
                }
            });
        }
    }

    @Override // com.miui.notes.ai.data.DataListener
    public void netError(final NetworkError networkError) {
        Logger.INSTANCE.i(TAG, " netError mRequestState " + this.mRequestState);
        if (this.mRequestState == RequestState.STATE_RECOMMEND) {
            return;
        }
        stopGenerating((this.mFirstInvokeFlow || (networkError != null && networkError.getErrorCodeNumber() == 8)) ? "" : this.mAiEndGenerateTip);
        this.mHandler.post(new Runnable() { // from class: com.miui.notes.ai.floatingwindow.AiFloatingWindow.8
            @Override // java.lang.Runnable
            public void run() {
                Logger.INSTANCE.d(AiFloatingWindow.TAG, "netError: " + networkError);
                if (!AiFloatingWindow.this.mFirst) {
                    AiFloatingWindow.this.endRequestTime = System.currentTimeMillis();
                    AiFloatingWindow.this.mFirst = true;
                }
                if (AiFloatingWindow.this.mRequestState == RequestState.STATE_COMPLETE) {
                    return;
                }
                AiFloatingWindow.mAiTraceId = networkError.getTraceId();
                AiFloatingWindow.this.mNetError = true;
                MiuiFolme.cleanFolme(AiFloatingWindow.this.mGeneratingView, AiFloatingWindow.this.mGeneratingStop, AiFloatingWindow.this.mPanelView, AiFloatingWindow.this.mInputRimEffectView, AiFloatingWindow.this.mInputContentView);
                AiFloatingWindow.this.handleErrorCodeNumber(networkError);
            }
        });
    }

    protected void handleErrorCodeNumber(NetworkError networkError) {
        this.mErrorCode = String.valueOf(networkError.getErrorCodeNumber());
        switch (networkError.getErrorCodeNumber()) {
            case 2:
                setUiState(RequestState.STATE_ERROR);
                break;
            case 3:
                setErrorCodeHandle(this.mContext.getString(R.string.ai_sensitive_word_error_title));
                revertFlowData();
                break;
            case 4:
                setErrorCodeHandle(this.mContext.getString(R.string.ai_multiple_violations_temporary_ban_title));
                break;
            case 5:
                setErrorCodeHandle(this.mContext.getString(R.string.ai_multiple_violations_permanent_ban_title));
                break;
            case 7:
                setErrorCodeHandle(this.mContext.getString(R.string.word_count_limit));
                break;
            case 8:
                setErrorCodeHandle(this.mContext.getString(R.string.ai_unsupport_directive_title));
                revertFlowData();
                break;
            case 9:
                setErrorCodeHandle(this.mContext.getString(R.string.upgrade_remind));
                break;
            case 10:
                setErrorCodeHandle(this.mContext.getString(R.string.ai_local_device_over_heat_title));
                break;
            case 11:
                setErrorCodeHandle(this.mContext.getString(R.string.ai_local_device_low_battery_title));
                break;
            case 12:
                setErrorCodeHandle(this.mContext.getString(R.string.ai_local_network_error_title));
                break;
            case 13:
                setErrorCodeHandle(this.mContext.getString(R.string.ai_local_language_unsupported_title));
                break;
            case 14:
                setErrorCodeHandle(this.mContext.getString(R.string.ai_llm_dis_connect_error_message));
                break;
            case 15:
                setErrorCodeHandle(this.mContext.getString(R.string.ai_language_not_support));
                break;
        }
        this.mRequestState = RequestState.STATE_ERROR;
    }

    protected void revertFlowData() {
        MiuiFolme.visibleView(this.mAiErrorImage);
        MiuiFolme.alphaShowView(this.mAiErrorImage);
        MiuiFolme.goneView(this.mAiWebView);
        isAiErrorImageShow = true;
    }

    public void setErrorCodeHandle(String str) {
        if (this.mPanelAiTitle == null || this.mAiBackText == null) {
            return;
        }
        trackReportAiEnd(UserInputHolder.getInstance().getRequestType(), 0, "fail", "code:" + this.mErrorCode + "; " + str, "fail");
        this.mPanelAiTitle.setText(str);
        this.mPanelAiTitle.setSingleLine(false);
        this.mAiBackText.setImageDrawable(this.mContext.getDrawable(R.drawable.ai_back_icon));
        this.mAiBackText.setText(this.mContext.getString(R.string.ai_text_widget_return));
        this.mAiBackText.setIconVisible(false);
        MiuiFolme.visibleView(this.mGeneratingView, this.mAiDoneTab, this.mAiBack);
        MiuiFolme.alphaShowView(this.mGeneratingView, this.mAiDoneTab, this.mAiBack);
        MiuiFolme.goneView(this.mPanelView, this.mGeneratingStop, this.mAiCopy, this.mAiInsert, this.mAiDone, this.mAiRefresh);
        if (TextUtils.isEmpty(this.mAiSavedData) || str.equals(this.mContext.getString(R.string.ai_llm_dis_connect_error_message))) {
            MiuiFolme.visibleView(this.mAiErrorImage);
            MiuiFolme.alphaShowView(this.mAiErrorImage);
            MiuiFolme.goneView(this.mAiWebView);
            isAiErrorImageShow = true;
            return;
        }
        MiuiFolme.visibleView(this.mAiWebView);
        MiuiFolme.alphaShowView(this.mAiWebView);
        MiuiFolme.goneView(this.mAiErrorImage);
        isAiErrorImageShow = false;
    }

    public void finishListen() {
        Logger.INSTANCE.i(TAG, "finishListen");
        this.mHandler.post(new Runnable() { // from class: com.miui.notes.ai.floatingwindow.AiFloatingWindow.9
            @Override // java.lang.Runnable
            public void run() {
                Logger.INSTANCE.d(AiFloatingWindow.TAG, "finishListen, mFirstInvokeFlow: " + AiFloatingWindow.this.mFirstInvokeFlow + ", mRequestState:" + AiFloatingWindow.this.mRequestState);
                if (AiFloatingWindow.this.mRequestState == RequestState.STATE_ERROR || AiFloatingWindow.this.mAiEditor == null) {
                    return;
                }
                AiFloatingWindow.this.mAiEditor.finishFlow(true, "", "");
                AiFloatingWindow.this.mFinishFlow = true;
            }
        });
    }

    @Override // com.miui.notes.ai.data.DataListener
    public void listenRecommendData(final List<String> list) {
        this.mRecommendView.post(new Runnable() { // from class: com.miui.notes.ai.floatingwindow.AiFloatingWindow.10
            @Override // java.lang.Runnable
            public void run() {
                Logger.INSTANCE.d(AiFloatingWindow.TAG, "listenRecommendData: " + list.size());
                if (AiFloatingWindow.this.mRequestState == RequestState.STATE_RECOMMEND) {
                    TransitionListener transitionListener = new TransitionListener() { // from class: com.miui.notes.ai.floatingwindow.AiFloatingWindow.10.1
                        @Override // miuix.animation.listener.TransitionListener
                        public void onComplete(Object obj) {
                            super.onComplete(obj);
                            AiFloatingWindow.this.setTouchableRegion();
                        }
                    };
                    AnimConfig ease = new AnimConfig().setEase(EaseManager.getStyle(-2, 0.9f, 0.3f));
                    ease.addListeners(transitionListener);
                    AnimConfig ease2 = new AnimConfig().setEase(EaseManager.getStyle(-2, 0.88f, 0.4f));
                    ease2.addListeners(transitionListener);
                    AnimConfig ease3 = new AnimConfig().setEase(EaseManager.getStyle(-2, 0.85f, 0.45f));
                    ease3.addListeners(transitionListener);
                    AnimConfig ease4 = new AnimConfig().setEase(EaseManager.getStyle(-2, 0.95f, 0.25f));
                    ease4.addListeners(transitionListener);
                    AnimConfig[] animConfigArr = {ease, ease2, ease3};
                    AiFloatingWindow.this.mRecommendSize = list.size();
                    int i = 0;
                    while (i < AiFloatingWindow.this.mRecommendSize && i < AiFloatingWindow.this.mRecommendItems.length) {
                        AiFloatingWindow.this.mRecommendItems[i].setText((String) list.get(i));
                        AiFloatingWindow.this.mRecommendContainers[i].setAlpha(0.0f);
                        AiFloatingWindow.this.mRecommendItems[i].setVisibility(0);
                        int i2 = i + 1;
                        float f = i2;
                        AiFloatingWindow.this.mRecommendContainers[i].setTranslationY(((AiFloatingWindow.this.mRecommendInput.getY() + AiFloatingWindow.this.mRecommendInput.getHeight()) * f) + AiFloatingWindow.this.mContext.getResources().getDimension(R.dimen.ai_floating_window_input_margin_bottom) + (f * AiFloatingWindow.this.mContext.getResources().getDimension(R.dimen.ai_recommend_item_margin_bottom_last)));
                        Folme.useAt(AiFloatingWindow.this.mRecommendContainers[i]).state().to(ViewProperty.ALPHA, Float.valueOf(1.0f), ease4).to(ViewProperty.TRANSLATION_Y, 0, animConfigArr[i]);
                        final String str = (String) list.get(i);
                        AiFloatingWindow.this.mRecommendContainers[i].setOnClickListener(new View.OnClickListener() { // from class: com.miui.notes.ai.floatingwindow.AiFloatingWindow.10.2
                            @Override // android.view.View.OnClickListener
                            public void onClick(View view) {
                                if (AiFloatingWindow.this.canRecommendItemEnable()) {
                                    AiFloatingWindow.this.initWebView();
                                    AiFloatingWindow.this.changeInit();
                                    UserInputHolder.getInstance().setEditInput(str);
                                    UserInputHolder.getInstance().setRequestType(RequestType.TYPE_RECOMMEND);
                                    SoftInputUtil.hideSoftInput(AiFloatingWindow.this.mContext, AiFloatingWindow.this.mRecommendEdit);
                                    AiFloatingWindow.this.mRecommendEdit.setText(str);
                                    AiFloatingWindow.this.mRecommendEdit.setSelection(str.length());
                                    AiFloatingWindow.this.recommendSendAnim();
                                }
                            }
                        });
                        i = i2;
                    }
                }
            }
        });
    }

    public void setUiState(RequestState requestState) {
        TextView textView;
        int plainTextCount;
        if (this.mRequestState == requestState) {
            return;
        }
        RequestType requestType = UserInputHolder.getInstance().getRequestType();
        Logger.INSTANCE.i(TAG, "setUiState " + this.mRequestState + " to " + requestState + ", mAiDoneData empty:" + TextUtils.isEmpty(this.mAiDoneData));
        this.mPanelAiTitleEnd.setVisibility(8);
        this.mPanelTitleStart.setVisibility(8);
        View view = this.mCloseButton;
        if (view != null) {
            view.setVisibility(0);
        }
        switch (AnonymousClass36.$SwitchMap$com$miui$notes$ai$network$RequestState[requestState.ordinal()]) {
            case 1:
                if (this.mRequestState == RequestState.STATE_UNKNOWN || this.mRequestState == RequestState.STATE_COMPLETE || this.mRequestState == RequestState.STATE_ERROR) {
                    this.mIsRecommendInput = true;
                    this.mIsRefreshGenerating = false;
                    if (this.mRequestState == RequestState.STATE_UNKNOWN) {
                        this.mRecommendSize = 0;
                        this.mRecommendDismiss = false;
                        if (this.mCloudIsDevice) {
                            AINetWorkUtils aINetWorkUtils = this.mAINetWorkUtils;
                            if (aINetWorkUtils != null) {
                                aINetWorkUtils.stopRequest();
                            }
                            AINetWorkUtils aINetWorkUtils2 = new AINetWorkUtils(this.mContext, this.mCloudIsDevice, this.mIsSupportLLMWriting);
                            this.mAINetWorkUtils = aINetWorkUtils2;
                            aINetWorkUtils2.startGetRecommendData(this.mHostPackageName, this);
                        }
                        this.mRecommendInputContentView.setAlpha(0.0f);
                        this.mRecommendInputRimEffectView.setAlpha(0.0f);
                        this.mBgGradientColorView.setAlpha(0.0f);
                        this.mRecommendEdit.post(new Runnable() { // from class: com.miui.notes.ai.floatingwindow.AiFloatingWindow.11
                            @Override // java.lang.Runnable
                            public void run() {
                                AnimConfig ease = new AnimConfig().setEase(EaseManager.getStyle(-2, 0.95f, 0.18f));
                                ease.addListeners(new TransitionListener() { // from class: com.miui.notes.ai.floatingwindow.AiFloatingWindow.11.1
                                    @Override // miuix.animation.listener.TransitionListener
                                    public void onComplete(Object obj) {
                                        super.onComplete(obj);
                                        AiFloatingWindow.this.initWebView();
                                    }

                                    @Override // miuix.animation.listener.TransitionListener
                                    public void onUpdate(Object obj, Collection<UpdateInfo> collection) {
                                        super.onUpdate(obj, collection);
                                        if (Build.VERSION.SDK_INT < 33 || !AiFloatingWindow.this.mIsApplyShaderEffect) {
                                            return;
                                        }
                                        AiFloatingWindow.this.onShowSoftInputShaderEffect();
                                    }

                                    @Override // miuix.animation.listener.TransitionListener
                                    public void onBegin(Object obj, Collection<UpdateInfo> collection) {
                                        super.onBegin(obj, collection);
                                        if (KeyboardListenerHelper.isHardwareKeyboard(AiFloatingWindow.this.mContext)) {
                                            AiFloatingWindow.this.mRecommendEdit.requestFocus();
                                            Logger.INSTANCE.i(AiFloatingWindow.TAG, "showSoft in hardwarekeyboard");
                                        } else {
                                            Logger.INSTANCE.i(AiFloatingWindow.TAG, "showSoft input for recommend");
                                            AiFloatingWindow.this.ismRecommendShowBegin = true;
                                            SoftInputUtil.showSoftInput(AiFloatingWindow.this.mRecommendEdit);
                                        }
                                    }
                                });
                                ease.setDelay(50L);
                                Folme.useAt(AiFloatingWindow.this.mRecommendInputContentView, AiFloatingWindow.this.mRecommendInputRimEffectView, AiFloatingWindow.this.mBgGradientColorView).state().to(ViewProperty.ALPHA, Float.valueOf(1.0f), ease);
                            }
                        });
                        MiuiFolme.goneView(this.mFloatingView, this.mInputLayout);
                        this.mRecommendView.setVisibility(0);
                        this.mHandler.postDelayed(new Runnable() { // from class: com.miui.notes.ai.floatingwindow.AiFloatingWindow$$ExternalSyntheticLambda22
                            @Override // java.lang.Runnable
                            public final void run() {
                                this.f$0.lambda$setUiState$5();
                            }
                        }, 50L);
                        this.mRecommendEdit.addOnLayoutChangeListener(new View.OnLayoutChangeListener() { // from class: com.miui.notes.ai.floatingwindow.AiFloatingWindow$$ExternalSyntheticLambda32
                            @Override // android.view.View.OnLayoutChangeListener
                            public final void onLayoutChange(View view2, int i, int i2, int i3, int i4, int i5, int i6, int i7, int i8) {
                                this.f$0.lambda$setUiState$6(view2, i, i2, i3, i4, i5, i6, i7, i8);
                            }
                        });
                    } else if (this.mRequestState == RequestState.STATE_COMPLETE) {
                        MiuiFolme.goneView(this.mFloatingView, this.mInputLayout);
                        if (KeyboardListenerHelper.isHardwareKeyboard(this.mContext)) {
                            Logger.INSTANCE.i(TAG, "showSoft in hardWareKeyBoard from complete");
                            this.mRequestState = RequestState.STATE_RECOMMEND;
                            this.mRecommendEdit.requestFocus();
                            Logger.INSTANCE.i(TAG, "set touchable region in hardWareKeyBoard from complete");
                            setTouchableRegion();
                        }
                    } else {
                        MiuiFolme.goneView(this.mFloatingView, this.mInputLayout);
                        this.mRecommendView.setVisibility(0);
                        if (KeyboardListenerHelper.isHardwareKeyboard(this.mContext)) {
                            Logger.INSTANCE.i(TAG, "showSoft in hardWareKeyBoard");
                            RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) this.mRecommendView.getLayoutParams();
                            if (DisplayUtil.hasNavigationBar(this.mContext) && !this.mImeVisible) {
                                layoutParams.bottomMargin = DisplayUtil.getNavigationBarHeightFromProp(this.mContext);
                            } else {
                                layoutParams.bottomMargin = 0;
                            }
                            this.mRecommendView.setLayoutParams(layoutParams);
                            this.mRecommendEdit.requestFocus();
                            setTouchableRegion();
                            onViewPositionChanged(ViewType.RECOMMEND_INPUT);
                        } else {
                            Logger.INSTANCE.i(TAG, "showSoft input for recommend");
                            SoftInputUtil.showSoftInput(this.mRecommendEdit);
                        }
                    }
                }
                break;
            case 2:
                this.mIsRefreshGenerating = false;
                View view2 = this.mFlagView;
                if (view2 != null) {
                    view2.setVisibility((this.mCloudIsDevice || this.mIsSupportCot) ? 0 : 8);
                }
                if (this.mPanelAiTitle == null) {
                    this.mPanelAiTitle = (TextView) this.mRootView.findViewById(R.id.ai_title);
                }
                if (this.mCloseButton == null) {
                    this.mCloseButton = this.mRootView.findViewById(R.id.close_button);
                }
                if (this.mRequestState == RequestState.STATE_UNKNOWN) {
                    View view3 = this.mAIInputDeepSeekButton;
                    if (view3 != null) {
                        view3.setVisibility(8);
                    }
                    View view4 = this.mAiInputIconView;
                    if (view4 != null) {
                        view4.setVisibility(0);
                    }
                }
                this.mAiSummaryContainer.setClickable(true);
                this.mAiCheckContainer.setClickable(true);
                this.mAiExtensionContainer.setClickable(true);
                this.mPanelAiTitle.setText(R.string.ai_text_widget_title);
                this.mPanelAiTitle.setHeight((int) TypedValue.applyDimension(1, 60.0f, Resources.getSystem().getDisplayMetrics()));
                this.mPanelAiTitle.setPadding(0, 0, 0, 0);
                this.mInputLayout.setVisibility((this.mCloudIsDevice || this.mIsSupportCot) ? 0 : 8);
                this.mNetError = false;
                this.mInput.clearFocus();
                if (this.mRequestState == RequestState.STATE_INPUT && this.mIsSupportCot) {
                    MiuiFolme.goneView(this.mGeneratingView, this.mRecommendView, this.mAiWaterMarkView);
                    this.mInputSendView.setAlpha(0.0f);
                } else {
                    MiuiFolme.goneView(this.mGeneratingView, this.mInputSendView, this.mRecommendView, this.mAiWaterMarkView);
                }
                if (this.mRequestState == RequestState.STATE_INPUT) {
                    panelAlphaAnim();
                    AINetWorkUtils aINetWorkUtils3 = this.mAINetWorkUtils;
                    if (aINetWorkUtils3 != null) {
                        aINetWorkUtils3.initModel(LLMManager.LLM_BASE_MODEL);
                    }
                }
                if (this.mRequestState != RequestState.STATE_INPUT && this.mRequestState != RequestState.STATE_COMPLETE && this.mRequestState != RequestState.STATE_ERROR) {
                    showPanelAnim();
                } else if (this.mRequestState == RequestState.STATE_COMPLETE || this.mRequestState == RequestState.STATE_ERROR) {
                    resetGenerateWindowSize();
                    backToPanelAnim();
                } else {
                    if (this.mCloudIsDevice || this.mIsSupportCot) {
                        MiuiFolme.visibleView(this.mPanelView, this.mInputLayout, this.mInputRimEffectView, this.mInputContentView);
                        MiuiFolme.alphaShowView(this.mPanelView, this.mInputLayout, this.mInputRimEffectView, this.mInputContentView);
                    } else {
                        MiuiFolme.visibleView(this.mPanelView);
                        MiuiFolme.goneView(this.mInputLayout, this.mInputRimEffectView, this.mInputContentView);
                    }
                    setTouchableRegion();
                }
                break;
            case 3:
                this.mIsClickStopGenerating = false;
                initWebView();
                View view5 = this.mFlagView;
                if (view5 != null) {
                    view5.setVisibility((this.mCloudIsDevice || this.mIsSupportCot) ? 0 : 8);
                }
                if (requestType.equals(RequestType.TYPE_CHECK) && (textView = this.mPanelAiTitle) != null && this.mBtnGeneratingStop != null) {
                    textView.setText(R.string.ai_generating_check);
                    this.mBtnGeneratingStop.setText(R.string.ai_generating_stop_check);
                } else {
                    TextView textView2 = this.mPanelAiTitle;
                    if (textView2 != null && this.mBtnGeneratingStop != null) {
                        textView2.setText(R.string.ai_generating_title);
                        this.mBtnGeneratingStop.setText(R.string.ai_generate_stop_button);
                    }
                }
                WebView webView = this.mAiWebView;
                if (webView != null) {
                    webView.setVisibility(0);
                }
                if (this.mRequestState == RequestState.STATE_RECOMMEND) {
                    lambda$setOnScreenChange$23(DisplayUtil.isLandScape(this.mContext));
                }
                loadingStartAnim();
                doWebLoadingAnim();
                break;
            case 4:
                View view6 = this.mCloseButton;
                if (view6 != null) {
                    view6.setVisibility(8);
                }
                AiButtomItem aiButtomItem = this.mAiBackText;
                if (aiButtomItem != null) {
                    aiButtomItem.setIconVisible(true);
                }
                MiuiFolme.goneView(this.mAiErrorImage);
                isAiErrorImageShow = false;
                WebView webView2 = this.mAiWebView;
                if (webView2 != null && webView2.getVisibility() == 0) {
                    View view7 = this.mCloseButton;
                    if (view7 != null) {
                        view7.setVisibility(0);
                    }
                    this.mHandler.post(new Runnable() { // from class: com.miui.notes.ai.floatingwindow.AiFloatingWindow$$ExternalSyntheticLambda33
                        @Override // java.lang.Runnable
                        public final void run() {
                            this.f$0.lambda$setUiState$7();
                        }
                    });
                }
                FloatingDecorView floatingDecorView = this.mRootView;
                if (floatingDecorView == null) {
                    Logger.INSTANCE.e(TAG, "root view is null!!!!!!!!!!");
                    return;
                }
                floatingDecorView.findViewById(R.id.ai_webview).setImportantForAccessibility(2);
                if (!TextUtils.isEmpty(this.mAiSavedData)) {
                    plainTextCount = NoteSchemaUtil.getPlainTextCount(this.mAiSavedData);
                } else {
                    plainTextCount = !TextUtils.isEmpty(this.mAiDoneData) ? NoteSchemaUtil.getPlainTextCount(this.mAiDoneData) : 0;
                }
                if (requestType.equals(RequestType.TYPE_SUMMARY)) {
                    this.mPanelAiTitle.setSingleLine(true);
                    if (this.mIsClickStopGenerating) {
                        this.mPanelAiTitle.setText(R.string.ai_title_stop_summary);
                    } else {
                        this.mPanelAiTitle.setText(R.string.ai_complete_summary_title);
                    }
                    this.mAiRefreshText.setText(this.mContext.getString(R.string.ai_text_widget_rebuild));
                    AiButtomItem aiButtomItem2 = this.mAiBackText;
                    if (aiButtomItem2 != null) {
                        aiButtomItem2.setText(this.mContext.getString(R.string.ai_text_widget_return));
                        this.mAiBackText.setImageDrawable(this.mContext.getDrawable(R.drawable.ai_back_icon));
                    }
                    MiuiFolme.goneView(this.mAiDone, this.mGeneratingStop);
                    MiuiFolme.visibleView(this.mAiBack, this.mAiRefresh, this.mAiCopy);
                } else if (requestType.equals(RequestType.TYPE_CHECK)) {
                    this.mPanelAiTitle.setSingleLine(true);
                    setCorrectNumTitle();
                    plainTextCount = mCorrectMisNum;
                    AiButtomItem aiButtomItem3 = this.mAiBackText;
                    if (aiButtomItem3 != null) {
                        aiButtomItem3.setText(this.mContext.getString(R.string.ai_text_widget_return));
                        this.mAiBackText.setImageDrawable(this.mContext.getDrawable(R.drawable.ai_back_icon));
                    }
                    MiuiFolme.goneView(this.mAiRefresh, this.mAiInsert, this.mGeneratingStop);
                    MiuiFolme.visibleView(this.mAiBack, this.mAiCopy);
                } else if (requestType.equals(RequestType.TYPE_POLISH)) {
                    this.mPanelAiTitle.setSingleLine(true);
                    if (this.mIsClickStopGenerating) {
                        this.mPanelAiTitle.setText(R.string.ai_title_stop_polish);
                    } else {
                        this.mPanelAiTitle.setText(R.string.complete_polish);
                    }
                    this.mAiRefreshText.setText(this.mContext.getString(R.string.ai_text_widget_rewrite));
                    AiButtomItem aiButtomItem4 = this.mAiBackText;
                    if (aiButtomItem4 != null) {
                        aiButtomItem4.setText(this.mContext.getString(R.string.ai_text_widget_return));
                        this.mAiBackText.setImageDrawable(this.mContext.getDrawable(R.drawable.ai_back_icon));
                    }
                    MiuiFolme.goneView(this.mAiInsert, this.mGeneratingStop, this.mAiDone);
                    MiuiFolme.visibleView(this.mAiBack, this.mAiRefresh, this.mAiCopy);
                } else if (requestType.equals(RequestType.TYPE_EXTENSION)) {
                    this.mPanelAiTitle.setSingleLine(true);
                    if (this.mIsClickStopGenerating) {
                        this.mPanelAiTitle.setText(R.string.ai_title_stop_extension);
                    } else {
                        this.mPanelAiTitle.setText(R.string.complete_extension);
                    }
                    this.mAiRefreshText.setText(this.mContext.getString(R.string.ai_text_widget_rewrite));
                    AiButtomItem aiButtomItem5 = this.mAiBackText;
                    if (aiButtomItem5 != null) {
                        aiButtomItem5.setText(this.mContext.getString(R.string.ai_text_widget_return));
                        this.mAiBackText.setImageDrawable(this.mContext.getDrawable(R.drawable.ai_back_icon));
                    }
                    MiuiFolme.goneView(this.mAiInsert, this.mGeneratingStop, this.mAiDone);
                    MiuiFolme.visibleView(this.mAiBack, this.mAiRefresh, this.mAiCopy);
                } else if (requestType.equals(RequestType.TYPE_INPUT)) {
                    this.mPanelAiTitle.setMaxLines(1);
                    this.mPanelTitleStart.setText("\"");
                    this.mPanelTitleStart.setVisibility(0);
                    this.mPanelAiTitleEnd.setText("\"");
                    this.mPanelAiTitleEnd.setVisibility(0);
                    this.mPanelAiTitle.setText(UserInputHolder.getInstance().getEditInput());
                    this.mAiRefreshText.setText(this.mContext.getString(R.string.ai_text_widget_rebuild));
                    AiButtomItem aiButtomItem6 = this.mAiBackText;
                    if (aiButtomItem6 != null) {
                        aiButtomItem6.setText(this.mContext.getString(R.string.ai_text_widget_return));
                        this.mAiBackText.setImageDrawable(this.mContext.getDrawable(R.drawable.ai_back_icon));
                    }
                    MiuiFolme.goneView(this.mAiDone, this.mGeneratingStop, this.mAiInsert);
                    MiuiFolme.visibleView(this.mAiBack, this.mAiRefresh, this.mAiCopy);
                } else if (requestType.equals(RequestType.TYPE_RECOMMEND)) {
                    if (!this.mIsDeleteLabel) {
                        this.mPanelAiTitle.setMaxLines(1);
                        this.mPanelTitleStart.setText("\"");
                        this.mPanelTitleStart.setVisibility(0);
                        this.mPanelAiTitleEnd.setText("\"");
                        this.mPanelAiTitleEnd.setVisibility(0);
                    }
                    this.mPanelAiTitle.setText(UserInputHolder.getInstance().getEditInput());
                    this.mAiRefreshText.setText(this.mContext.getString(R.string.ai_text_widget_rebuild));
                    AiButtomItem aiButtomItem7 = this.mAiBackText;
                    if (aiButtomItem7 != null) {
                        aiButtomItem7.setText(this.mContext.getString(R.string.ai_text_widget_return));
                        this.mAiBackText.setImageDrawable(this.mContext.getDrawable(R.drawable.ai_back_icon));
                    }
                    MiuiFolme.goneView(this.mAiDone, this.mAiInsert, this.mGeneratingStop);
                    MiuiFolme.visibleView(this.mAiBack, this.mAiRefresh, this.mAiCopy);
                }
                MiuiFolme.visibleView(this.mAiWaterMarkView);
                boolean z = this.mIsClickStopGenerating;
                String str = AITextWidgetStat.GENERATE_STOP;
                String str2 = z ? AITextWidgetStat.GENERATE_STOP : "success";
                if (!z) {
                    str = "success";
                }
                trackReportAiEnd(requestType, plainTextCount, str2, str, z ? AITextWidgetStat.GENERATE_STOP : "success");
                break;
                break;
            case 5:
                this.mHandler.postDelayed(new Runnable() { // from class: com.miui.notes.ai.floatingwindow.AiFloatingWindow$$ExternalSyntheticLambda34
                    @Override // java.lang.Runnable
                    public final void run() {
                        this.f$0.lambda$setUiState$8();
                    }
                }, 100L);
                this.mPanelAiTitle.setSingleLine(false);
                this.mAiRefreshText.setText(this.mContext.getString(R.string.ai_text_widget_rebuild));
                AiButtomItem aiButtomItem8 = this.mAiBackText;
                if (aiButtomItem8 != null) {
                    aiButtomItem8.setText(this.mContext.getString(R.string.ai_text_widget_return));
                    this.mAiBackText.setImageDrawable(this.mContext.getDrawable(R.drawable.ai_back_icon));
                    this.mAiBackText.setIconVisible(true);
                }
                MiuiFolme.visibleView(this.mGeneratingView, this.mAiDoneTab, this.mAiBack, this.mAiRefresh);
                MiuiFolme.alphaShowView(this.mGeneratingView, this.mAiDoneTab, this.mAiBack, this.mAiRefresh);
                MiuiFolme.goneView(this.mPanelView, this.mGeneratingStop, this.mAiCopy, this.mAiInsert, this.mAiDone, this.mAiWaterMarkView);
                if (TextUtils.isEmpty(this.mAiSavedData)) {
                    MiuiFolme.visibleView(this.mAiErrorImage);
                    MiuiFolme.alphaShowView(this.mAiErrorImage);
                    MiuiFolme.goneView(this.mAiWebView);
                    isAiErrorImageShow = true;
                } else {
                    MiuiFolme.visibleView(this.mAiWebView);
                    MiuiFolme.alphaShowView(this.mAiWebView);
                    MiuiFolme.goneView(this.mAiErrorImage);
                    isAiErrorImageShow = false;
                }
                trackReportAiEnd(requestType, 0, "fail", "code:" + this.mErrorCode + "; " + this.mContext.getString(R.string.ai_when_mistake_title), "fail");
                this.mNetError = false;
                break;
            case 6:
                this.mBgGradientColorView.setAlpha(0.0f);
                this.mHandler.postDelayed(new Runnable() { // from class: com.miui.notes.ai.floatingwindow.AiFloatingWindow$$ExternalSyntheticLambda35
                    @Override // java.lang.Runnable
                    public final void run() {
                        this.f$0.lambda$setUiState$9();
                    }
                }, 300L);
                this.mIsRecommendInput = false;
                this.isButtonClicked = false;
                this.mAiSummaryContainer.setClickable(false);
                this.mAiCheckContainer.setClickable(false);
                this.mAiExtensionContainer.setClickable(false);
                AnimConfig animConfig = new AnimConfig();
                animConfig.setEase(EaseManager.getStyle(-2, 0.95f, 0.18f));
                animConfig.addListeners(new AnonymousClass12());
                AnimConfig animConfig2 = new AnimConfig();
                animConfig2.setEase(EaseManager.getStyle(-2, 0.95f, 0.18f));
                animConfig2.addListeners(new TransitionListener() { // from class: com.miui.notes.ai.floatingwindow.AiFloatingWindow.13
                    @Override // miuix.animation.listener.TransitionListener
                    public void onComplete(Object obj) {
                        super.onComplete(obj);
                        MiuiFolme.cleanFolme(AiFloatingWindow.this.mFloatingRimEffectView);
                        if (Build.VERSION.SDK_INT < 33 || !AiFloatingWindow.this.mIsApplyShaderEffect) {
                            return;
                        }
                        AiFloatingWindow.this.onShowSoftInputShaderEffect();
                    }

                    @Override // miuix.animation.listener.TransitionListener
                    public void onBegin(Object obj) {
                        super.onBegin(obj);
                        AiFloatingWindow.this.onViewPositionChanged(ViewType.PANEL);
                    }

                    @Override // miuix.animation.listener.TransitionListener
                    public void onUpdate(Object obj, Collection<UpdateInfo> collection) {
                        super.onUpdate(obj, collection);
                        AiFloatingWindow.this.onViewPositionChanged(ViewType.PANEL);
                    }

                    @Override // miuix.animation.listener.TransitionListener
                    public void onCancel(Object obj) {
                        super.onCancel(obj);
                        AiFloatingWindow.this.mFloatingRimEffectView.setAlpha(0.0f);
                    }
                });
                Folme.useAt(this.mFloatingRimEffectView).state().to(ViewProperty.ALPHA, Float.valueOf(0.0f), ViewProperty.SCALE_X, Float.valueOf(0.95f), ViewProperty.SCALE_Y, Float.valueOf(0.95f), animConfig2);
                Folme.useAt(this.mFloatingContentView).state().to(ViewProperty.ALPHA, Float.valueOf(0.0f), ViewProperty.SCALE_X, Float.valueOf(0.95f), ViewProperty.SCALE_Y, Float.valueOf(0.95f), animConfig);
                break;
        }
        this.mRequestState = requestState;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$setUiState$5() {
        setTouchableRegion();
        AINetWorkUtils aINetWorkUtils = this.mAINetWorkUtils;
        if (aINetWorkUtils != null) {
            aINetWorkUtils.initModel(LLMManager.LLM_COT_MODEL);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$setUiState$6(View view, int i, int i2, int i3, int i4, int i5, int i6, int i7, int i8) {
        if (i4 - i2 != i8 - i6) {
            setTouchableRegion();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$setUiState$7() {
        RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) this.mGeneratingView.getLayoutParams();
        layoutParams.height = this.mLoadingEndHeight;
        Logger.INSTANCE.d(TAG, "STATE_COMPLETE mLoadingEndHeight:" + this.mLoadingEndHeight);
        this.mGeneratingView.setLayoutParams(layoutParams);
        if (TextUtils.isEmpty(this.mAiSavedData)) {
            MiuiFolme.goneView(this.mAiWebView);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$setUiState$8() {
        this.mPanelAiTitle.setText(R.string.ai_when_mistake_title);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$setUiState$9() {
        Folme.useAt(this.mBgGradientColorView).state().to(ViewProperty.ALPHA, Float.valueOf(1.0f));
    }

    /* JADX INFO: renamed from: com.miui.notes.ai.floatingwindow.AiFloatingWindow$12, reason: invalid class name */
    class AnonymousClass12 extends TransitionListener {
        AnonymousClass12() {
        }

        @Override // miuix.animation.listener.TransitionListener
        public void onComplete(Object obj) {
            super.onComplete(obj);
            MiuiFolme.cleanFolme(AiFloatingWindow.this.mFloatingContentView);
            AiFloatingWindow.this.setTouchableRegion();
            AiFloatingWindow.this.onViewPositionChanged(ViewType.PANEL);
            AiFloatingWindow.this.onPanelChangeEnd();
            if (AiFloatingWindow.this.mAINetWorkUtils != null) {
                AiFloatingWindow.this.mAINetWorkUtils.initModel(LLMManager.LLM_COT_MODEL);
            }
            CustomAiTextWidgetEditText customAiTextWidgetEditText = AiFloatingWindow.this.mInput;
            AiFloatingWindow aiFloatingWindow = AiFloatingWindow.this;
            Runnable runnable = new Runnable() { // from class: com.miui.notes.ai.floatingwindow.AiFloatingWindow$12$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.lambda$onComplete$0();
                }
            };
            aiFloatingWindow.mAnimChangeRunnable = runnable;
            customAiTextWidgetEditText.post(runnable);
            if (Build.VERSION.SDK_INT < 33 || !AiFloatingWindow.this.mIsApplyShaderEffect) {
                return;
            }
            AiFloatingWindow.this.onShowSoftInputShaderEffect();
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onComplete$0() {
            if (RomUtils.isPhoneDevice() && DisplayUtil.isLandScape(AiFloatingWindow.this.mContext)) {
                Logger.INSTANCE.i(AiFloatingWindow.TAG, "input in phone landscape");
                return;
            }
            AiFloatingWindow.this.mInput.setSingleLine(false);
            AiFloatingWindow.this.mInput.setInputType(131073);
            if (AiFloatingWindow.this.mIsSupportCot) {
                AiFloatingWindow.this.mInput.setMaxHeight(DisplayUtil.dip2px(AiFloatingWindow.this.mContext, 113.0f));
            } else {
                AiFloatingWindow.this.mInput.setMaxHeight(DisplayUtil.dip2px(AiFloatingWindow.this.mContext, 104.0f));
            }
            int[] iArr = {AiFloatingWindow.this.mInput.getHeight()};
            int[] iArr2 = {iArr[0]};
            if (AiFloatingWindow.this.mInputGlobalLayoutListener == null) {
                AiFloatingWindow.this.mInputGlobalLayoutListener = new AnonymousClass1(iArr2, iArr);
            }
            AiFloatingWindow.this.mInputLayout.getViewTreeObserver().addOnGlobalLayoutListener(AiFloatingWindow.this.mInputGlobalLayoutListener);
        }

        /* JADX INFO: renamed from: com.miui.notes.ai.floatingwindow.AiFloatingWindow$12$1, reason: invalid class name */
        class AnonymousClass1 implements ViewTreeObserver.OnGlobalLayoutListener {
            final /* synthetic */ int[] val$currentHeight;
            final /* synthetic */ int[] val$lastHeight;

            AnonymousClass1(int[] iArr, int[] iArr2) {
                this.val$lastHeight = iArr;
                this.val$currentHeight = iArr2;
            }

            @Override // android.view.ViewTreeObserver.OnGlobalLayoutListener
            public void onGlobalLayout() {
                int[] iArr = this.val$lastHeight;
                int[] iArr2 = this.val$currentHeight;
                iArr[0] = iArr2[0];
                iArr2[0] = Math.max(AiFloatingWindow.this.mInput.getHeight(), 121);
                Logger.INSTANCE.i(AiFloatingWindow.TAG, "input line height lastHeight[0] " + this.val$lastHeight[0] + " currentHeight[0] " + this.val$currentHeight[0]);
                int i = this.val$lastHeight[0];
                int i2 = this.val$currentHeight[0];
                if (i == i2) {
                    Logger.INSTANCE.i(AiFloatingWindow.TAG, " input not increase line height");
                    return;
                }
                if (i2 > i) {
                    Logger.INSTANCE.i(AiFloatingWindow.TAG, "input increase line height lastHeight[0] " + this.val$lastHeight[0] + " currentHeight[0] " + this.val$currentHeight[0]);
                    Handler handler = AiFloatingWindow.this.mHandler;
                    final int[] iArr3 = this.val$currentHeight;
                    final int[] iArr4 = this.val$lastHeight;
                    handler.post(new Runnable() { // from class: com.miui.notes.ai.floatingwindow.AiFloatingWindow$12$1$$ExternalSyntheticLambda0
                        @Override // java.lang.Runnable
                        public final void run() {
                            this.f$0.lambda$onGlobalLayout$0(iArr3, iArr4);
                        }
                    });
                    return;
                }
                Logger.INSTANCE.i(AiFloatingWindow.TAG, " input decrease line height lastHeight[0] " + this.val$lastHeight[0] + " currentHeight[0] " + this.val$currentHeight[0]);
                Handler handler2 = AiFloatingWindow.this.mHandler;
                final int[] iArr5 = this.val$currentHeight;
                final int[] iArr6 = this.val$lastHeight;
                handler2.post(new Runnable() { // from class: com.miui.notes.ai.floatingwindow.AiFloatingWindow$12$1$$ExternalSyntheticLambda1
                    @Override // java.lang.Runnable
                    public final void run() {
                        this.f$0.lambda$onGlobalLayout$1(iArr5, iArr6);
                    }
                });
            }

            /* JADX INFO: Access modifiers changed from: private */
            public /* synthetic */ void lambda$onGlobalLayout$0(int[] iArr, int[] iArr2) {
                AiFloatingWindow.this.mInputLayout.setTranslationY(AiFloatingWindow.this.mInputLayout.getTranslationY() - (iArr[0] - iArr2[0]));
                if (AiFloatingWindow.this.mIsSupportCot) {
                    ViewGroup.LayoutParams layoutParams = AiFloatingWindow.this.mInputLayout.getLayoutParams();
                    layoutParams.height = AiFloatingWindow.this.mInputLayout.getHeight() + (iArr[0] - iArr2[0]);
                    AiFloatingWindow.this.mInputLayout.setLayoutParams(layoutParams);
                    AiFloatingWindow.this.mInputIncreasedHeight += iArr[0] - iArr2[0];
                }
            }

            /* JADX INFO: Access modifiers changed from: private */
            public /* synthetic */ void lambda$onGlobalLayout$1(int[] iArr, int[] iArr2) {
                AiFloatingWindow.this.mInputLayout.setTranslationY(AiFloatingWindow.this.mInputLayout.getTranslationY() - (iArr[0] - iArr2[0]));
                if (AiFloatingWindow.this.mIsSupportCot) {
                    ViewGroup.LayoutParams layoutParams = AiFloatingWindow.this.mInputLayout.getLayoutParams();
                    layoutParams.height = AiFloatingWindow.this.mInputLayout.getHeight() + (iArr[0] - iArr2[0]);
                    AiFloatingWindow.this.mInputLayout.setLayoutParams(layoutParams);
                    AiFloatingWindow.this.mInputIncreasedHeight = Math.max(AiFloatingWindow.this.mInputIncreasedHeight + (iArr[0] - iArr2[0]), 0);
                }
            }
        }

        @Override // miuix.animation.listener.TransitionListener
        public void onBegin(Object obj) {
            super.onBegin(obj);
            AiFloatingWindow.this.onViewPositionChanged(ViewType.PANEL);
        }

        @Override // miuix.animation.listener.TransitionListener
        public void onUpdate(Object obj, Collection<UpdateInfo> collection) {
            super.onUpdate(obj, collection);
            AiFloatingWindow.this.onViewPositionChanged(ViewType.PANEL);
        }

        @Override // miuix.animation.listener.TransitionListener
        public void onCancel(Object obj) {
            super.onCancel(obj);
            AiFloatingWindow.this.mFloatingContentView.setAlpha(0.0f);
        }
    }

    private void trackReportAiUse(RequestType requestType) {
        if (this.aiPreferencesUtils == null) {
            this.aiPreferencesUtils = new AIPreferencesUtils(this.mContext);
        }
        Logger.INSTANCE.d(TAG, "trackReportAiUse: requestType === " + requestType + " &&&&&&&&&&& " + this.mPolishStyle);
        switch (AnonymousClass36.$SwitchMap$com$miui$notes$ai$data$input$RequestType[requestType.ordinal()]) {
            case 1:
                this.aiPreferencesUtils.addValue(AITextWidgetStat.TYPE_RECOMMEND_QUANTITY, this.mAIUseNum);
                break;
            case 2:
                this.aiPreferencesUtils.addValue(AITextWidgetStat.TYPE_INPUT_QUANTITY, this.mAIUseNum);
                break;
            case 3:
                this.aiPreferencesUtils.addValue(AITextWidgetStat.TYPE_SUMMARY_QUANTITY, this.mAIUseNum);
                break;
            case 4:
                this.aiPreferencesUtils.addValue(AITextWidgetStat.TYPE_EXTENSION_QUANTITY, this.mAIUseNum);
                break;
            case 5:
                this.aiPreferencesUtils.addValue(AITextWidgetStat.TYPE_CHECK_QUANTITY, this.mAIUseNum);
                break;
            case 6:
                if (this.mPolishStyle == PolishStyle.TYPE_COMMON) {
                    this.aiPreferencesUtils.addValue(AITextWidgetStat.TYPE_COMMON_QUANTITY, this.mAIUseNum);
                } else if (this.mPolishStyle == PolishStyle.TYPE_PROFESSIONAL) {
                    this.aiPreferencesUtils.addValue(AITextWidgetStat.TYPE_PROFESSIONAL_QUANTITY, this.mAIUseNum);
                } else if (this.mPolishStyle == PolishStyle.TYPE_EASY) {
                    this.aiPreferencesUtils.addValue(AITextWidgetStat.TYPE_EASY_QUANTITY, this.mAIUseNum);
                } else if (this.mPolishStyle == PolishStyle.TYPE_FRIENDLY) {
                    this.aiPreferencesUtils.addValue(AITextWidgetStat.TYPE_FRIENDLY_QUANTITY, this.mAIUseNum);
                }
                break;
        }
    }

    private void trackReportAiStart(RequestType requestType) {
        String str;
        int i;
        String str2;
        String strValueOf = String.valueOf(requestType);
        if (requestType.equals(RequestType.TYPE_POLISH)) {
            strValueOf = String.valueOf(UserInputHolder.getInstance().getPolishStyle());
        }
        String str3 = strValueOf;
        String str4 = this.mIsRefreshGenerating ? AITextWidgetStat.REFRESH_GENERATE : AITextWidgetStat.NEW_GENERATE;
        String str5 = mInTitle ? "title" : "content";
        int i2 = this.mSelectCount;
        String editInput = (requestType.equals(RequestType.TYPE_RECOMMEND) || requestType.equals(RequestType.TYPE_INPUT)) ? UserInputHolder.getInstance().getEditInput() : AITextWidgetStat.AI_DETAIL_NONE;
        boolean z = mIsNoteAi;
        String str6 = "unknown";
        String str7 = AITextWidgetStat.GENERATE_IN_FLOATING_WINDOW;
        if (z) {
            if (!requestType.equals(RequestType.TYPE_SUMMARY) && !requestType.equals(RequestType.TYPE_INPUT)) {
                str7 = AITextWidgetStat.GENERATE_IN_NOTE;
            }
            String str8 = mContentStatus;
            str = mContentType;
            i = mContentTextCount;
            str2 = str8;
        } else {
            str = "unknown";
            i = 0;
            str2 = str;
        }
        if (requestType == RequestType.TYPE_RECOMMEND) {
            str6 = AITextWidgetStat.AI_TEXT_SOURCE_RECOMMEND;
        } else if (requestType == RequestType.TYPE_INPUT) {
            str6 = AITextWidgetStat.AI_TEXT_SOURCE_INPUT;
        }
        AITextWidgetStat.reportAiStart(str, str3, str4, str7, editInput, this.mHostPackageName, str2, i, str6, this.aiSessionId, str5, i2, this.mContext);
    }

    /* JADX WARN: Code duplicated, block: B:22:0x0063 A[PHI: r6
  0x0063: PHI (r6v4 java.lang.String) = (r6v2 java.lang.String), (r6v2 java.lang.String), (r6v6 java.lang.String) binds: [B:16:0x004a, B:18:0x0052, B:20:0x0058] A[DONT_GENERATE, DONT_INLINE]] */
    private void trackReportAiEnd(RequestType requestType, int i, String str, String str2, String str3) {
        int i2;
        int i3;
        String str4;
        String str5;
        int i4;
        String strValueOf = String.valueOf(requestType);
        if (requestType.equals(RequestType.TYPE_POLISH)) {
            strValueOf = String.valueOf(UserInputHolder.getInstance().getPolishStyle());
        }
        String str6 = strValueOf;
        String str7 = this.mIsRefreshGenerating ? AITextWidgetStat.REFRESH_GENERATE : AITextWidgetStat.NEW_GENERATE;
        String str8 = mInTitle ? "title" : "content";
        int i5 = this.mSelectCount;
        int iCurrentTimeMillis = (int) (System.currentTimeMillis() - this.mStartRequestTime);
        boolean z = mIsNoteAi;
        String str9 = AITextWidgetStat.GENERATE_IN_FLOATING_WINDOW;
        if (!z) {
            i2 = 0;
            i3 = i;
            str4 = "unknown";
            str5 = str4;
        } else {
            if (requestType.equals(RequestType.TYPE_SUMMARY) || requestType.equals(RequestType.TYPE_INPUT)) {
                i4 = i;
            } else {
                boolean z2 = this.mIsClickStopGenerating;
                str9 = AITextWidgetStat.GENERATE_IN_NOTE;
                if (z2) {
                    i4 = (mEndAllCount - mStartAllCount) + this.mSelectCount;
                } else {
                    i4 = i;
                }
            }
            String str10 = mContentStatus;
            String str11 = mContentType;
            i2 = mAiEndTextCount;
            i3 = i4;
            str4 = str11;
            str5 = str10;
        }
        AITextWidgetStat.reportAiEnd(str4, str6, str7, str9, (requestType.equals(RequestType.TYPE_RECOMMEND) || requestType.equals(RequestType.TYPE_INPUT)) ? UserInputHolder.getInstance().getEditInput() : AITextWidgetStat.AI_DETAIL_NONE, i3, this.mIsDeleteLabel ? AITextWidgetStat.AI_Identify_Refuse : str, iCurrentTimeMillis, str2, this.mHostPackageName, mAiTraceId, getAiTraceType(), str5, i2, this.aiSessionId, str3, str8, i5, this.endRequestTime - this.startRequestTime, this.mThinkCount, this.mThinkTime, this.misAiDeepSeekSelected, this.mContext);
    }

    private void trackReportDeepSeekClick() {
        AITextWidgetStat.reportAiDeepSeekClick(mContentType, getContentStatus(), NoteSchemaUtil.getPlainTextCount(this.mNoteContent), this.mHostPackageName, mInTitle ? "title" : "content", NoteSchemaUtil.getPlainTextCount(this.mSelectedText), this.mSelectedText.isEmpty() ? AITextWidgetStat.GENERATE_IN_FLOATING_WINDOW : "Input_box", "false", String.valueOf(this.misAiDeepSeekSelected), this.mContext, getAiTraceType());
        Logger.INSTANCE.d(TAG, "trackReportDeepSeekClick: mNoteContent " + this.mNoteContent + " mSelectedText " + this.mSelectedText);
    }

    private void trackReportAiOpen() {
        int i;
        String str;
        int i2;
        String str2;
        boolean z;
        String str3;
        String str4 = mInTitle ? "title" : "content";
        this.mAiOpenTime = System.currentTimeMillis();
        int i3 = this.mSelectCount;
        if (!mIsNoteAi) {
            i = -1;
            str = "unknown";
            i2 = 0;
            str2 = str;
            z = false;
        } else {
            String str5 = mContentStatus;
            String str6 = mContentType;
            i2 = mContentTextCount;
            i = mContentStyle;
            str = str5;
            z = false;
            str2 = str6;
        }
        if (!TextUtils.isEmpty(this.mSelectedText)) {
            str3 = AITextWidgetStat.GENERATE_IN_FLOATING_WINDOW;
        } else {
            str3 = AITextWidgetStat.AI_INPUT_BOX;
            z = true;
        }
        AITextWidgetStat.reportAiPanelOpen(str2, str, i2, this.mHostPackageName, String.valueOf(i), str4, i3, str3, String.valueOf(z), this.mContext);
    }

    public void trackReportAiClose(RequestType requestType, String str) {
        int i;
        int i2;
        String str2;
        String str3;
        String str4;
        String str5 = mInTitle ? "title" : "content";
        int i3 = this.mSelectCount;
        boolean z = false;
        String str6 = "unknown";
        if (mIsNoteAi) {
            str2 = mContentStatus;
            str3 = mContentType;
            i2 = mContentTextCount;
            i = mContentStyle;
        } else {
            i = -1;
            i2 = 0;
            str2 = "unknown";
            str3 = str2;
        }
        String editInput = (requestType.equals(RequestType.TYPE_RECOMMEND) || requestType.equals(RequestType.TYPE_INPUT)) ? UserInputHolder.getInstance().getEditInput() : AITextWidgetStat.AI_DETAIL_NONE;
        if (!TextUtils.isEmpty(this.mSelectedText)) {
            str4 = AITextWidgetStat.GENERATE_IN_FLOATING_WINDOW;
        } else {
            str4 = AITextWidgetStat.AI_INPUT_BOX;
            z = true;
        }
        String str7 = str4;
        if (requestType == RequestType.TYPE_RECOMMEND) {
            str6 = AITextWidgetStat.AI_TEXT_SOURCE_RECOMMEND;
        } else if (requestType == RequestType.TYPE_INPUT) {
            str6 = AITextWidgetStat.AI_TEXT_SOURCE_INPUT;
        }
        AITextWidgetStat.reportAiPanelClose(str3, str2, i2, this.mHostPackageName, String.valueOf(i), str5, i3, String.valueOf(z), str7, str6, editInput, str, System.currentTimeMillis() - this.mAiOpenTime, this.mContext);
    }

    private String getAiTraceType() {
        return this.mCloudIsDevice ? AITextWidgetStat.AI_CLOUD_TYPE : AITextWidgetStat.AI_LOCAL_TYPE;
    }

    public void trackReportAiUsedOrAiCanceled(RequestType requestType, String str, boolean z) {
        int i;
        String str2;
        String str3;
        String strValueOf = String.valueOf(requestType);
        if (requestType.equals(RequestType.TYPE_POLISH)) {
            strValueOf = String.valueOf(UserInputHolder.getInstance().getPolishStyle());
        }
        String str4 = strValueOf;
        String str5 = this.mIsRefreshGenerating ? AITextWidgetStat.REFRESH_GENERATE : AITextWidgetStat.NEW_GENERATE;
        String str6 = this.mIsClickStopGenerating ? AITextWidgetStat.GENERATE_STOP : "success";
        String str7 = mInTitle ? "title" : "content";
        int i2 = this.mSelectCount;
        boolean z2 = mIsNoteAi;
        String str8 = "unknown";
        String str9 = AITextWidgetStat.GENERATE_IN_FLOATING_WINDOW;
        if (z2) {
            if (!requestType.equals(RequestType.TYPE_SUMMARY) && !requestType.equals(RequestType.TYPE_INPUT)) {
                if (this.mIsClickStopGenerating) {
                    this.aiTextCount = (mEndAllCount - mStartAllCount) + this.mSelectCount;
                }
                if (mInTitle) {
                    getAiResultCount();
                }
                str9 = AITextWidgetStat.GENERATE_IN_NOTE;
            }
            String str10 = mContentStatus;
            str2 = mContentType;
            i = mContentTextCount;
            str3 = str10;
        } else {
            getAiResultCount();
            i = 0;
            str2 = "unknown";
            str3 = str2;
        }
        String str11 = str9;
        int i3 = i;
        if (requestType.equals(RequestType.TYPE_CHECK)) {
            this.aiTextCount = mCorrectMisNum;
        }
        String editInput = (requestType.equals(RequestType.TYPE_RECOMMEND) || requestType.equals(RequestType.TYPE_INPUT)) ? UserInputHolder.getInstance().getEditInput() : AITextWidgetStat.AI_DETAIL_NONE;
        if (requestType == RequestType.TYPE_RECOMMEND) {
            str8 = AITextWidgetStat.AI_TEXT_SOURCE_RECOMMEND;
        } else if (requestType == RequestType.TYPE_INPUT) {
            str8 = AITextWidgetStat.AI_TEXT_SOURCE_INPUT;
        }
        String str12 = str8;
        if (this.mIsDeleteLabel) {
            str6 = AITextWidgetStat.AI_Identify_Refuse;
        }
        if (z) {
            AITextWidgetStat.reportAiCanceled(str2, str4, str5, str11, editInput, this.aiTextCount, str6, str, this.mHostPackageName, mAiTraceId, getAiTraceType(), str12, str3, this.aiSessionId, i3, str7, this.mSelectCount, this.mContext);
        } else {
            AITextWidgetStat.reportAiUsed(str2, str4, str5, str11, editInput, this.aiTextCount, str6, str, this.mHostPackageName, mAiTraceId, getAiTraceType(), str12, str3, this.aiSessionId, mAiEndTextCount, str7, i2, this.mThinkCount, this.mThinkTime, this.misAiDeepSeekSelected, this.mContext);
        }
    }

    private void getAiResultCount() {
        String strReplaceAll;
        if (!TextUtils.isEmpty(this.mAiSavedData)) {
            strReplaceAll = this.mAiSavedData.replaceAll("<[^>]*>", "");
        } else {
            strReplaceAll = !TextUtils.isEmpty(this.mAiDoneData) ? this.mAiDoneData.replaceAll("<[^>]*>", "") : "";
        }
        this.aiTextCount = strReplaceAll.replaceAll(this.mAiEndGenerateTip + "\\s*", "").length();
    }

    private void setCorrectNumTitle() {
        int iMax = Math.max(mCorrectMisNum, 0);
        mCorrectMisNum = iMax;
        if (iMax > 0) {
            this.mHandler.postDelayed(new Runnable() { // from class: com.miui.notes.ai.floatingwindow.AiFloatingWindow$$ExternalSyntheticLambda16
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.lambda$setCorrectNumTitle$10();
                }
            }, 1L);
        } else {
            this.mHandler.postDelayed(new Runnable() { // from class: com.miui.notes.ai.floatingwindow.AiFloatingWindow$$ExternalSyntheticLambda17
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.lambda$setCorrectNumTitle$11();
                }
            }, 1L);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$setCorrectNumTitle$10() {
        this.mPanelAiTitle.setText(com.miui.notes.ai.utils.StringUtils.obtainLocalInteger(mCorrectMisNum, R.plurals.ai_correct_mistakes, this.mContext));
        Log.i(TAG, "setCorrectNumTitle CorrectMisNum: " + mCorrectMisNum);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$setCorrectNumTitle$11() {
        Log.i(TAG, "setCorrectNumTitle no error");
        this.mPanelAiTitle.setText(R.string.ai_no_error);
    }

    protected void doWebLoadingAnim() {
        IAiEditor iAiEditor = this.mAiEditor;
        if (iAiEditor != null) {
            iAiEditor.onDataLoading();
        }
    }

    private void handleTouchEvent() {
        this.mRootView.setOnTouchListener(new View.OnTouchListener() { // from class: com.miui.notes.ai.floatingwindow.AiFloatingWindow$$ExternalSyntheticLambda25
            @Override // android.view.View.OnTouchListener
            public final boolean onTouch(View view, MotionEvent motionEvent) {
                return this.f$0.lambda$handleTouchEvent$12(view, motionEvent);
            }
        });
        this.mInput.setOnLongClickListener(new View.OnLongClickListener() { // from class: com.miui.notes.ai.floatingwindow.AiFloatingWindow.14
            @Override // android.view.View.OnLongClickListener
            public boolean onLongClick(View view) {
                AiFloatingWindow.this.mInput.requestFocus();
                return AiFloatingWindow.this.mRequestState == RequestState.STATE_PANEL;
            }
        });
        this.mInput.setOnTouchListener(new View.OnTouchListener() { // from class: com.miui.notes.ai.floatingwindow.AiFloatingWindow.15
            @Override // android.view.View.OnTouchListener
            public boolean onTouch(View view, MotionEvent motionEvent) {
                Logger.INSTANCE.d(AiFloatingWindow.TAG, "onTouch mHasTouchInput: " + AiFloatingWindow.this.mHasTouchInput + " ,event.getAction(): " + motionEvent.getAction());
                AiFloatingWindow.this.mInput.setFocusableInTouchMode(true);
                if (AiFloatingWindow.this.mHasTouchInput || motionEvent.getAction() != 1) {
                    return false;
                }
                AiFloatingWindow.this.isButtonClicked = true;
                AiFloatingWindow.this.mHasTouchInput = true;
                return false;
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ boolean lambda$handleTouchEvent$12(View view, MotionEvent motionEvent) {
        Logger.INSTANCE.d(TAG, "onTouch: " + motionEvent);
        Logger.INSTANCE.i(TAG, "floatingView onTouch x " + motionEvent.getX() + " y " + motionEvent.getY() + " action " + motionEvent.getAction());
        if (motionEvent.getAction() != 4) {
            return false;
        }
        handleOutSideEvent(motionEvent);
        CustomAiTextWidgetEditText customAiTextWidgetEditText = this.mInput;
        if (customAiTextWidgetEditText != null) {
            customAiTextWidgetEditText.clearFocus();
        }
        View view2 = this.mRecommendInput;
        if (view2 == null) {
            return false;
        }
        view2.clearFocus();
        return false;
    }

    protected void handleOutSideEvent(MotionEvent motionEvent) {
        trackReportAiClose(UserInputHolder.getInstance().getRequestType(), AITextWidgetStat.AI_BLANK_AREA_CLOSE);
        detachFromWindow();
    }

    public boolean isShowing() {
        return this.mIsShowing;
    }

    public void hideForLock() {
        Logger.INSTANCE.d(TAG, "hideForLock mIsShowing=" + this.mIsShowing);
        FloatingDecorView floatingDecorView = this.mRootView;
        if (floatingDecorView == null || !this.mIsShowing) {
            return;
        }
        floatingDecorView.setAlpha(0.0f);
    }

    protected void addToWindow() {
        RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) this.mFloatingView.getLayoutParams();
        if (DisplayUtil.hasNavigationBar(this.mContext)) {
            if (!DisplayUtil.isLandScape(this.mContext)) {
                layoutParams.bottomMargin = DisplayUtil.getNavigationBarHeightFromProp(this.mContext);
            } else {
                if (RomUtils.isPadDevice()) {
                    layoutParams.bottomMargin = DisplayUtil.getNavigationBarHeightFromProp(this.mContext);
                }
                if (RomUtils.isFoldDevice()) {
                    if (ScreenPosistionUtils.isInNarrowScreen(this.mContext)) {
                        layoutParams.bottomMargin = this.mContext.getResources().getDimensionPixelSize(R.dimen.ai_floating_window_margin_bottom);
                    } else {
                        layoutParams.bottomMargin = DisplayUtil.getNavigationBarHeightFromProp(this.mContext);
                    }
                }
            }
        } else {
            layoutParams.bottomMargin = this.mContext.getResources().getDimensionPixelSize(R.dimen.ai_floating_window_margin_bottom);
        }
        this.mFloatingView.setLayoutParams(layoutParams);
        this.mWindowManager = (WindowManager) this.mContext.getSystemService("window");
        WindowManager.LayoutParams layoutParams2 = new WindowManager.LayoutParams(-1, -1, R2.attr.preferenceCheckableItemSetMaskPaddingEnd, 262944, 1);
        this.mParams = layoutParams2;
        layoutParams2.softInputMode = 48;
        this.mParams.layoutInDisplayCutoutMode = 1;
        ReflectUtils.callMethod(this.mParams, "setTrustedOverlay", new Class[0], new Object[0]);
        if (Build.VERSION.SDK_INT >= 33 && this.mIsApplyShaderEffect) {
            initializeShaderEffect();
        }
        try {
            if (this.mIsFromPrivate) {
                this.mParams.flags |= 8192;
            }
            this.mWindowManager.addView(this.mRootView, this.mParams);
            this.mRootView.setSystemUiVisibility(3840);
            this.mIsShowing = true;
            PreferenceUtils.setAiFloatingWindowShowing(this.mContext, true);
        } catch (Exception e) {
            Logger.INSTANCE.d(TAG, "addView error: " + e);
            e.printStackTrace();
        }
    }

    private void cancelTouchableRegion() {
        Logger.INSTANCE.d(TAG, "cancelTouchableRegion ");
        if (this.mWindowTouchRegionHelper == null) {
            this.mWindowTouchRegionHelper = new WindowTouchRegionHelper(this.mFloatingView);
        }
        this.mWindowTouchRegionHelper.updateTouchAreaRect(new Rect(0, 0, 0, 0));
        this.mFloatingView.invalidate();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void setTouchableRegion() {
        if (this.mFloatingView == null) {
            return;
        }
        this.mHandler.post(new Runnable() { // from class: com.miui.notes.ai.floatingwindow.AiFloatingWindow$$ExternalSyntheticLambda6
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.lambda$setTouchableRegion$13();
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$setTouchableRegion$13() {
        Rect regionFromView;
        if (this.mWindowTouchRegionHelper == null) {
            this.mWindowTouchRegionHelper = new WindowTouchRegionHelper(this.mFloatingView);
        }
        Rect rect = new Rect();
        if (this.mRequestState == RequestState.STATE_RECOMMEND) {
            for (AiRecommendItem aiRecommendItem : this.mRecommendItems) {
                getRecommendRegionFromView(aiRecommendItem);
            }
            getRecommendRegionFromView(this.mRecommendInput);
        } else {
            if (this.mRequestState == RequestState.STATE_INPUT) {
                regionFromView = getRegionFromView(this.mInputLayout, rect);
                Logger.INSTANCE.i(TAG, "TouchRegion STATE_INPUT 区域: " + regionFromView);
                Logger.INSTANCE.i(TAG, "TouchRegion getLocalVisibleRect, mInputLayout:" + rect);
            } else {
                regionFromView = getRegionFromView(this.mFloatingView, rect);
                Logger.INSTANCE.i(TAG, "TouchRegion getLocalVisibleRect, mFloatingView:" + rect);
                Logger.INSTANCE.i(TAG, " TouchRegion mFloatingView: " + regionFromView);
            }
            this.mWindowTouchRegionHelper.updateTouchAreaRect(regionFromView);
        }
        if (!this.mWindowTouchRegionHelper.isRegistered()) {
            this.mWindowTouchRegionHelper.registerOnComputeInternalInsetsListener();
        }
        this.mFloatingView.invalidate();
    }

    private Rect getRegionFromView(View view, Rect rect) {
        view.getLocalVisibleRect(rect);
        return new Rect((int) view.getX(), (int) view.getY(), (int) (view.getX() + rect.right), (int) (view.getY() + rect.bottom));
    }

    private void getRecommendRegionFromView(View view) {
        Rect rect = new Rect();
        view.getLocalVisibleRect(rect);
        int[] iArr = new int[2];
        view.getLocationOnScreen(iArr);
        int i = iArr[0];
        Rect rect2 = new Rect(i, iArr[1], rect.right + i, iArr[1] + rect.bottom);
        this.mWindowTouchRegionHelper.updateTouchUnionRect(rect2);
        Logger.INSTANCE.i(TAG, "TouchRegion getLocalVisibleRect, mRecommendView:" + rect);
        Logger.INSTANCE.i(TAG, "TouchRegion STATE_RECOMMEND 区域: " + rect2);
    }

    protected void detachFromWindowFromForeInfoChange() {
        Logger.INSTANCE.i(TAG, "detachFromWindowFromForeInfoChange mDuringRemoveView " + this.mDuringRemoveView + " ,mRequestState:" + this.mRequestState);
        if (this.mFloatingView == null || this.mDuringRemoveView || this.mRootView == null) {
            return;
        }
        Logger.INSTANCE.i(TAG, "detachFromWindowFromForeInfoChange");
        this.mAiTextWidgetService = null;
        AINetWorkUtils aINetWorkUtils = this.mAINetWorkUtils;
        if (aINetWorkUtils != null) {
            aINetWorkUtils.stopRequest();
            this.mAINetWorkUtils.releaseConnect();
            this.mAINetWorkUtils = null;
        }
        this.mHandler.post(new Runnable() { // from class: com.miui.notes.ai.floatingwindow.AiFloatingWindow$$ExternalSyntheticLambda21
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.lambda$detachFromWindowFromForeInfoChange$14();
            }
        });
        if (this.mRequestState == RequestState.STATE_RECOMMEND) {
            TransitionListener transitionListener = new TransitionListener() { // from class: com.miui.notes.ai.floatingwindow.AiFloatingWindow.16
                @Override // miuix.animation.listener.TransitionListener
                public void onComplete(Object obj) {
                    super.onComplete(obj);
                    MiuiFolme.cleanFolme(AiFloatingWindow.this.mRecommendItems);
                    MiuiFolme.goneView(AiFloatingWindow.this.mRecommendView);
                    AiFloatingWindow.this.mRecommendDismiss = true;
                    AiFloatingWindow.this.mKeyboardListenerHelper.removeListener();
                    AiFloatingWindow.this.dismissWindow();
                }

                @Override // miuix.animation.listener.TransitionListener
                public void onUpdate(Object obj, Collection<UpdateInfo> collection) {
                    super.onUpdate(obj, collection);
                    UpdateInfo updateInfoFindByName = UpdateInfo.findByName(collection, "alpha");
                    if (updateInfoFindByName != null) {
                        AiFloatingWindow.this.mBgGradientColorView.setAlpha(updateInfoFindByName.getFloatValue());
                    }
                }

                @Override // miuix.animation.listener.TransitionListener
                public void onCancel(Object obj) {
                    super.onCancel(obj);
                    MiuiFolme.cleanFolme(AiFloatingWindow.this.mRecommendItems);
                    MiuiFolme.goneView(AiFloatingWindow.this.mRecommendView);
                    AiFloatingWindow.this.mRecommendDismiss = true;
                    AiFloatingWindow.this.mKeyboardListenerHelper.removeListener();
                    AiFloatingWindow.this.dismissWindow();
                }
            };
            AnimConfig ease = new AnimConfig().setEase(EaseManager.getStyle(-2, 0.96f, 0.32f));
            ease.addListeners(transitionListener);
            AnimConfig ease2 = new AnimConfig().setEase(EaseManager.getStyle(-2, 0.95f, 0.18f));
            ease2.addListeners(transitionListener);
            for (int i = 0; i < this.mRecommendSize; i++) {
                AiRecommendItem[] aiRecommendItemArr = this.mRecommendItems;
                if (i >= aiRecommendItemArr.length) {
                    break;
                }
                aiRecommendItemArr[i].setTranslationY((int) ((this.mRecommendInput.getY() + this.mRecommendInput.getHeight()) - (this.mRecommendItems[i].getY() + this.mRecommendItems[i].getHeight())));
                Folme.useAt(this.mRecommendItems[i]).state().to(ViewProperty.ALPHA, Float.valueOf(0.0f), ease2).to(ViewProperty.TRANSLATION_Y, Integer.valueOf((int) ((this.mRecommendInput.getY() + this.mRecommendInput.getHeight()) - (this.mRecommendItems[i].getY() + this.mRecommendItems[i].getHeight()))), ease);
            }
            Folme.useAt(this.mRecommendInput).state().to(ViewProperty.ALPHA, Float.valueOf(0.0f), new AnimConfig().setEase(EaseManager.getStyle(-2, 0.95f, 0.18f)).setDelay(50L).addListeners(transitionListener));
            return;
        }
        this.mHandler.post(new Runnable() { // from class: com.miui.notes.ai.floatingwindow.AiFloatingWindow$$ExternalSyntheticLambda23
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.lambda$detachFromWindowFromForeInfoChange$15();
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$detachFromWindowFromForeInfoChange$14() {
        stopGenerating("");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$detachFromWindowFromForeInfoChange$15() {
        FloatingDecorView floatingDecorView = this.mRootView;
        if (floatingDecorView != null) {
            floatingDecorView.setVisibility(8);
        }
        dismissWindow();
        Logger.INSTANCE.d(TAG, "detachFromWindowFromForeInfoChange dismiss end");
    }

    protected void detachFromWindow() {
        this.mAiTextWidgetService = null;
        AINetWorkUtils aINetWorkUtils = this.mAINetWorkUtils;
        if (aINetWorkUtils != null) {
            aINetWorkUtils.stopRequest();
            this.mAINetWorkUtils.releaseConnect();
            this.mAINetWorkUtils = null;
        }
        if (this.mFloatingView == null || this.mDuringRemoveView) {
            Logger.INSTANCE.i(TAG, "detachFromWindow return first");
            return;
        }
        Logger.INSTANCE.i(TAG, "detachFromWindow mRequestState: " + this.mRequestState);
        if (this.mRequestState == RequestState.STATE_LOADING) {
            stopGenerating("");
        }
        if (this.mRequestState == RequestState.STATE_RECOMMEND) {
            SoftInputUtil.hideSoftInput(this.mContext, this.mRecommendEdit);
            TransitionListener transitionListener = new TransitionListener() { // from class: com.miui.notes.ai.floatingwindow.AiFloatingWindow.17
                @Override // miuix.animation.listener.TransitionListener
                public void onComplete(Object obj) {
                    super.onComplete(obj);
                    MiuiFolme.cleanFolme(AiFloatingWindow.this.mRecommendItems);
                    MiuiFolme.goneView(AiFloatingWindow.this.mRecommendView);
                    AiFloatingWindow.this.mRecommendDismiss = true;
                    Logger.INSTANCE.i(AiFloatingWindow.TAG, "detachFromWindow onComplete mImeVisible ：" + AiFloatingWindow.this.mImeVisible + " ,mImeHeight: " + AiFloatingWindow.this.mImeHeight);
                    AiFloatingWindow.this.mKeyboardListenerHelper.removeListener();
                    Logger.INSTANCE.i(AiFloatingWindow.TAG, "detachFromWindow real dismiss");
                    AiFloatingWindow.this.dismissWindow();
                }

                @Override // miuix.animation.listener.TransitionListener
                public void onUpdate(Object obj, Collection<UpdateInfo> collection) {
                    super.onUpdate(obj, collection);
                    UpdateInfo updateInfoFindByName = UpdateInfo.findByName(collection, "alpha");
                    Logger.INSTANCE.i(AiFloatingWindow.TAG, "detachFromWindow onUpdate: " + updateInfoFindByName);
                    if (updateInfoFindByName != null) {
                        AiFloatingWindow.this.mBgGradientColorView.setAlpha(updateInfoFindByName.getFloatValue());
                    }
                }

                @Override // miuix.animation.listener.TransitionListener
                public void onCancel(Object obj) {
                    super.onCancel(obj);
                    MiuiFolme.cleanFolme(AiFloatingWindow.this.mRecommendItems);
                    MiuiFolme.goneView(AiFloatingWindow.this.mRecommendView);
                    AiFloatingWindow.this.mRecommendDismiss = true;
                    Logger.INSTANCE.i(AiFloatingWindow.TAG, "detachFromWindow onCancel mImeVisible ：" + AiFloatingWindow.this.mImeVisible + " ,mImeHeight: " + AiFloatingWindow.this.mImeHeight);
                    AiFloatingWindow.this.mKeyboardListenerHelper.removeListener();
                    Logger.INSTANCE.i(AiFloatingWindow.TAG, "detachFromWindow real dismiss");
                    AiFloatingWindow.this.dismissWindow();
                }
            };
            TransitionListener transitionListener2 = new TransitionListener();
            AnimConfig ease = new AnimConfig().setEase(EaseManager.getStyle(-2, 0.96f, 0.32f));
            ease.addListeners(transitionListener2);
            AnimConfig ease2 = new AnimConfig().setEase(EaseManager.getStyle(-2, 0.95f, 0.18f));
            ease2.addListeners(transitionListener2);
            for (int i = 0; i < this.mRecommendSize; i++) {
                View[] viewArr = this.mRecommendContainers;
                if (i >= viewArr.length) {
                    break;
                }
                viewArr[i].setTranslationY(0.0f);
                Folme.useAt(this.mRecommendContainers[i]).state().to(ViewProperty.ALPHA, Float.valueOf(0.0f), ease2).to(ViewProperty.TRANSLATION_Y, Integer.valueOf(((int) (this.mRecommendInput.getY() + this.mRecommendInput.getHeight())) * 2), ease);
            }
            this.mRecommendInputContentView.setTranslationY(0.0f);
            Folme.useAt(this.mRecommendInputContentView, this.mRecommendInputRimEffectView, this.mBgGradientColorView).state().to(ViewProperty.ALPHA, Float.valueOf(0.0f), new AnimConfig().setEase(EaseManager.getStyle(-2, 0.95f, 0.18f)));
            Folme.useAt(this.mRecommendInput).state().to(ViewProperty.ALPHA, Float.valueOf(0.0f), new AnimConfig().setEase(EaseManager.getStyle(-2, 0.95f, 0.18f)).addListeners(transitionListener));
            return;
        }
        this.mHandler.post(new Runnable() { // from class: com.miui.notes.ai.floatingwindow.AiFloatingWindow.18
            @Override // java.lang.Runnable
            public void run() {
                try {
                    AnimConfig ease3 = new AnimConfig().setEase(EaseManager.getStyle(-2, 0.96f, 0.32f));
                    ease3.addListeners(new TransitionListener() { // from class: com.miui.notes.ai.floatingwindow.AiFloatingWindow.18.1
                        @Override // miuix.animation.listener.TransitionListener
                        public void onUpdate(Object obj, Collection<UpdateInfo> collection) {
                            super.onUpdate(obj, collection);
                            AiFloatingWindow.this.onViewPositionChanged(ViewType.PANEL);
                        }

                        @Override // miuix.animation.listener.TransitionListener
                        public void onComplete(Object obj) {
                            AiFloatingWindow.this.mFloatingView.setVisibility(8);
                            AiFloatingWindow.this.mInputLayout.setVisibility(8);
                            MiuiFolme.cleanFolme(AiFloatingWindow.this.mFloatingView);
                            AiFloatingWindow.this.onViewPositionChanged(ViewType.PANEL);
                            AiFloatingWindow.this.onPanelChangeEnd();
                            AiFloatingWindow.this.dismissWindow();
                        }

                        @Override // miuix.animation.listener.TransitionListener
                        public void onCancel(Object obj) {
                            super.onCancel(obj);
                            MiuiFolme.cleanFolme(AiFloatingWindow.this.mFloatingView);
                            AiFloatingWindow.this.dismissWindow();
                        }
                    });
                    Logger.INSTANCE.i(AiFloatingWindow.TAG, "detachFromWindow panel window");
                    if (!DisplayUtil.hasNavigationBar(AiFloatingWindow.this.mContext)) {
                        if (AiFloatingWindow.this.mRequestState == RequestState.STATE_PANEL && AiFloatingWindow.this.mInputLayout != null) {
                            Folme.useAt(AiFloatingWindow.this.mInputLayout).state().to(ViewProperty.TRANSLATION_Y, Integer.valueOf(AiFloatingWindow.this.mFloatingView.getHeight()), ease3);
                        }
                        Folme.useAt(AiFloatingWindow.this.mFloatingView).state().to(ViewProperty.TRANSLATION_Y, Integer.valueOf(AiFloatingWindow.this.mFloatingView.getHeight()), ease3);
                        return;
                    }
                    if (AiFloatingWindow.this.mRequestState == RequestState.STATE_PANEL && AiFloatingWindow.this.mInputLayout != null) {
                        Folme.useAt(AiFloatingWindow.this.mInputLayout).state().to(ViewProperty.TRANSLATION_Y, Integer.valueOf(AiFloatingWindow.this.mFloatingView.getHeight() + DisplayUtil.getNavigationBarHeightFromProp(AiFloatingWindow.this.mContext)), ease3);
                    }
                    Folme.useAt(AiFloatingWindow.this.mFloatingView).state().to(ViewProperty.TRANSLATION_Y, Integer.valueOf(AiFloatingWindow.this.mFloatingView.getHeight() + DisplayUtil.getNavigationBarHeightFromProp(AiFloatingWindow.this.mContext)), ease3);
                } catch (Exception e) {
                    Logger.INSTANCE.e(AiFloatingWindow.TAG, "e : " + e);
                }
            }
        });
    }

    public void dismissWindow() {
        Logger.INSTANCE.d(TAG, "dismissWindow start");
        if (this.mDuringRemoveView) {
            return;
        }
        IAiEditor iAiEditor = this.mAiEditor;
        if (iAiEditor != null) {
            iAiEditor.closeCorrectionPop();
        }
        this.mDuringRemoveView = true;
        ForegroundWindowObserver foregroundWindowObserver = this.mAppObserver;
        if (foregroundWindowObserver != null) {
            foregroundWindowObserver.destroy();
            ForegroundInfoUtils.unregisterForegroundWindowListener(this.mAppObserver);
        }
        unregisterNavigationKeyEventReceiver();
        if (this.mWindowManager != null) {
            this.mParams.flags |= 8;
            this.mWindowManager.updateViewLayout(this.mRootView, this.mParams);
            CustomAiTextWidgetEditText customAiTextWidgetEditText = this.mInput;
            if (customAiTextWidgetEditText != null) {
                customAiTextWidgetEditText.clearFocus();
            }
            View view = this.mRecommendInput;
            if (view != null) {
                view.clearFocus();
            }
            this.mWindowManager.removeView(this.mRootView);
        }
        this.mIsShowing = false;
        PreferenceUtils.setAiFloatingWindowShowing(this.mContext, false);
        WindowTouchRegionHelper windowTouchRegionHelper = this.mWindowTouchRegionHelper;
        if (windowTouchRegionHelper != null && windowTouchRegionHelper.isRegistered()) {
            this.mWindowTouchRegionHelper.unRegisterOnComputeInternalInsetsListener();
            this.mWindowTouchRegionHelper = null;
        }
        WindowCloseListener windowCloseListener = this.mWindowCloseListener;
        if (windowCloseListener != null) {
            windowCloseListener.onWindowClosed();
        }
        this.mWindowManager = null;
        this.mRootView = null;
        this.mRequestState = RequestState.STATE_UNKNOWN;
        this.mHasTouchInput = false;
        releaseToast();
        releaseShaderRenderer();
        this.mHandler.removeCallbacksAndMessages(null);
        this.mDuringRemoveView = false;
        mAiTraceId = null;
        mContentType = "unknown";
        this.aiPreferencesUtils = null;
        Logger.INSTANCE.d(TAG, "dismissWindow end");
    }

    public void setWindowCloseListener(WindowCloseListener windowCloseListener) {
        this.mWindowCloseListener = windowCloseListener;
    }

    private void handleImeChanged() {
        KeyboardListenerHelper keyboardListenerHelper = new KeyboardListenerHelper(this.mRootView);
        this.mKeyboardListenerHelper = keyboardListenerHelper;
        keyboardListenerHelper.setKeyBoardChangedListener(this);
        this.mKeyboardListenerHelper.addListener();
    }

    /* JADX WARN: Code duplicated, block: B:72:0x0312  */
    /* JADX WARN: Code duplicated, block: B:79:0x0344  */
    /* JADX WARN: Code duplicated, block: B:81:0x0348  */
    /* JADX WARN: Code duplicated, block: B:87:0x038b  */
    @Override // com.miui.notes.ai.utils.KeyboardListenerHelper.IKeyBoardChangedListener
    public void onShowKeyboard(int i) {
        int height;
        int navigationBarHeightFromProp;
        final int iDip2px;
        AnimConfig animConfig;
        int height2;
        int navigationBarHeightFromProp2;
        View view;
        this.mImeVisible = true;
        long jCurrentTimeMillis = System.currentTimeMillis();
        Logger.INSTANCE.i(TAG, "onShowKeyboardImeHeight: " + i + "  mHasTouchInput : " + this.mHasTouchInput + " mImeVisible " + this.mImeVisible);
        if (i > 0 && (!isFastTriggered(jCurrentTimeMillis) || this.mRequestState == RequestState.STATE_RECOMMEND)) {
            this.lastTriggeredTime = jCurrentTimeMillis;
            Logger.INSTANCE.i(TAG, "onShowKeyboard mRequestState: " + this.mRequestState);
            if (TextUtils.isEmpty(this.mSelectedText) && (this.currentOri != this.lastOri || ((this.smallWindowRect != null && this.currentRotation != this.lastRotation) || (RomUtils.isFoldDevice() && this.currentNarrow != this.lastNarrow)))) {
                Logger.INSTANCE.i(TAG, " reset recommend view for roate");
                RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) this.mRecommendView.getLayoutParams();
                layoutParams.bottomMargin = i;
                this.mRecommendView.setLayoutParams(layoutParams);
                setTouchableRegion();
            }
            if (this.mHasTouchInput || this.isButtonClicked) {
                int[] iArr = {0};
                Logger.INSTANCE.d(TAG, "onShowKeyboard: beforeAnim mInput height: " + this.mInput.getHeight());
                AnimState animStateAdd = new AnimState("loadingEnd").add(ViewProperty.HEIGHT, this.mInput.getHeight() + this.mInputSendView.getLayoutParams().height + convertDpToPixels(43.0f, this.mContext));
                AnimConfig ease = new AnimConfig().setEase(EaseManager.getStyle(-2, 0.9f, 0.35f));
                ease.addListeners(new TransitionListener() { // from class: com.miui.notes.ai.floatingwindow.AiFloatingWindow.19
                    @Override // miuix.animation.listener.TransitionListener
                    public void onBegin(Object obj) {
                        super.onBegin(obj);
                        Logger.INSTANCE.d(AiFloatingWindow.TAG, "show onBegin: mInputLayout height" + AiFloatingWindow.this.mInputLayout.getHeight());
                        ViewGroup.MarginLayoutParams marginLayoutParams = (ViewGroup.MarginLayoutParams) AiFloatingWindow.this.mInput.getLayoutParams();
                        marginLayoutParams.setMarginStart(DisplayUtil.dip2px(AiFloatingWindow.this.mContext, 0.0f));
                        marginLayoutParams.setMarginEnd(DisplayUtil.dip2px(AiFloatingWindow.this.mContext, 0.0f));
                        AiFloatingWindow.this.mInput.setLayoutParams(marginLayoutParams);
                        AiFloatingWindow.this.mInput.setPadding(AiFloatingWindow.this.cotInputwidth, AiFloatingWindow.this.cotInputwidth, AiFloatingWindow.this.cotInputwidth, AiFloatingWindow.this.cotInputwidth);
                        AiFloatingWindow.this.mInputSendView.setAlpha(1.0f);
                        AiFloatingWindow.this.mInputSendView.setVisibility(0);
                        AiFloatingWindow.this.mRootView.setEnabled(false);
                        if (AiFloatingWindow.this.mIsSupportCot) {
                            AiFloatingWindow.this.mAiInputIconView.setVisibility(8);
                            AiFloatingWindow.this.mAIInputDeepSeekButton.setVisibility(0);
                        }
                    }

                    @Override // miuix.animation.listener.TransitionListener
                    public void onComplete(Object obj) {
                        super.onComplete(obj);
                        if (AiFloatingWindow.this.mRootView != null) {
                            AiFloatingWindow.this.mRootView.setEnabled(true);
                        } else {
                            Logger.INSTANCE.d(AiFloatingWindow.TAG, "onComplete()|mRootView is null");
                        }
                        Logger.INSTANCE.d(AiFloatingWindow.TAG, "show onComplete: mInputLayout height" + AiFloatingWindow.this.mInputLayout.getHeight());
                    }

                    @Override // miuix.animation.listener.TransitionListener
                    public void onCancel(Object obj) {
                        Logger.INSTANCE.d(AiFloatingWindow.TAG, "onShowKeyboard inputLayoutAnimConfig onCancel: " + obj);
                        super.onCancel(obj);
                    }
                });
                iArr[0] = this.mFloatingView.getHeight();
                int y = this.mIsSupportCot ? (int) (this.mTitleLayout.getY() + this.mTitleLayout.getHeight() + this.mInput.getHeight() + this.mInputSendView.getLayoutParams().height + DisplayUtil.dip2px(this.mContext, 43.0f)) : (int) (this.mTitleLayout.getY() + this.mTitleLayout.getHeight() + this.mInputLayout.getHeight());
                if (DisplayUtil.hasNavigationBar(this.mContext)) {
                    if (iArr[0] < DisplayUtil.getScreenMetrics(this.mContext).heightPixels) {
                        if (iArr[0] + DisplayUtil.getNavigationBarHeightFromProp(this.mContext) <= DisplayUtil.getScreenMetrics(this.mContext).heightPixels) {
                            Logger.INSTANCE.i(TAG, "navigation & floating height < screen height");
                            height2 = iArr[0];
                            navigationBarHeightFromProp2 = DisplayUtil.getNavigationBarHeightFromProp(this.mContext);
                        } else {
                            Logger.INSTANCE.i(TAG, "navigation & floating height > screen height");
                            navigationBarHeightFromProp = (DisplayUtil.getScreenMetrics(this.mContext).heightPixels - y) - (DisplayUtil.getNavigationBarHeightFromProp(this.mContext) - (DisplayUtil.getScreenMetrics(this.mContext).heightPixels - iArr[0]));
                        }
                        iDip2px = (i - navigationBarHeightFromProp) + DisplayUtil.dip2px(this.mContext, 12.0f);
                        Logger.INSTANCE.i(TAG, " mFloatingView.getHeight() " + iArr[0] + " DisplayUtil.getNavigationBarHeightFromProp(mContext) " + DisplayUtil.getNavigationBarHeightFromProp(this.mContext) + " y1  " + y + " y2 " + navigationBarHeightFromProp + " imeHeight " + i + " upDy " + iDip2px);
                        if (this.mRequestState != RequestState.STATE_LOADING) {
                            this.mInputRimEffectView.setAlpha(0.0f);
                            if (KeyboardListenerHelper.isHardwareKeyboard(this.mContext) && DisplayUtil.hasNavigationBar(this.mContext)) {
                                iDip2px += DisplayUtil.getNavigationBarHeightFromProp(this.mContext);
                            }
                            animConfig = new AnimConfig();
                            animConfig.addListeners(new TransitionListener() { // from class: com.miui.notes.ai.floatingwindow.AiFloatingWindow.20
                                @Override // miuix.animation.listener.TransitionListener
                                public void onBegin(Object obj) {
                                    super.onBegin(obj);
                                    AiFloatingWindow.this.mRootView.setEnabled(false);
                                }

                                @Override // miuix.animation.listener.TransitionListener
                                public void onUpdate(Object obj, Collection<UpdateInfo> collection) {
                                    super.onUpdate(obj, collection);
                                    UpdateInfo updateInfoFindByName = UpdateInfo.findByName(collection, "translationY");
                                    if (updateInfoFindByName != null) {
                                        AiFloatingWindow.this.mInputRimEffectView.setAlpha((float) (updateInfoFindByName.animInfo.value / updateInfoFindByName.animInfo.targetValue));
                                    }
                                    AiFloatingWindow.this.onViewPositionChanged(ViewType.PANEL_INPUT);
                                    if (Build.VERSION.SDK_INT >= 33 && AiFloatingWindow.this.mIsApplyShaderEffect && AiFloatingWindow.this.mRequestState == RequestState.STATE_INPUT) {
                                        AiFloatingWindow.this.onShowSoftInputShaderEffect();
                                    }
                                }

                                @Override // miuix.animation.listener.TransitionListener
                                public void onComplete(Object obj) {
                                    super.onComplete(obj);
                                    Logger.INSTANCE.d(AiFloatingWindow.TAG, "onShowKeyboard onComplete:" + obj + " ,mInputLayout:" + AiFloatingWindow.this.mInputLayout + " ,finalUpDy:" + iDip2px);
                                    if (AiFloatingWindow.this.mInputRimEffectView != null) {
                                        AiFloatingWindow.this.mInputRimEffectView.setAlpha(1.0f);
                                    }
                                    AiFloatingWindow.this.setTouchableRegion();
                                    if (AiFloatingWindow.this.mRootView != null) {
                                        AiFloatingWindow.this.mRootView.setEnabled(true);
                                    } else {
                                        Logger.INSTANCE.d(AiFloatingWindow.TAG, "onComplete()|mRootView is null");
                                    }
                                    if (AiFloatingWindow.this.mInputLayout != null) {
                                        AiFloatingWindow.this.mInputLayout.setTranslationY(-iDip2px);
                                    }
                                    AiFloatingWindow.this.onViewPositionChanged(ViewType.PANEL_INPUT);
                                }

                                @Override // miuix.animation.listener.TransitionListener
                                public void onCancel(Object obj) {
                                    super.onCancel(obj);
                                    Logger.INSTANCE.d(AiFloatingWindow.TAG, "onShowKeyboard onCancel:" + obj + " ,mInputLayout:" + AiFloatingWindow.this.mInputLayout + " ,finalUpDy:" + iDip2px);
                                    MiuiFolme.cleanFolme(AiFloatingWindow.this.mInputLayout);
                                    if (AiFloatingWindow.this.mInputRimEffectView != null) {
                                        AiFloatingWindow.this.mInputRimEffectView.setAlpha(1.0f);
                                    }
                                    AiFloatingWindow.this.mHasTouchInput = false;
                                }
                            });
                            if (this.mIsSupportCot) {
                                if (!this.isKeyBoardShowing) {
                                    Folme.useAt(this.mInputLayout).state().to(animStateAdd, ease, ViewProperty.TRANSLATION_Y, Integer.valueOf(-iDip2px), animConfig);
                                }
                                if (this.isKeyBoardShowing && isImeHeightChanged(i)) {
                                    Folme.useAt(this.mInputLayout).state().to(ViewProperty.TRANSLATION_Y, Integer.valueOf(-iDip2px), animConfig);
                                }
                            } else {
                                this.mInputSendView.setVisibility(0);
                                Folme.useAt(this.mInputLayout).state().to(ViewProperty.TRANSLATION_Y, Integer.valueOf(-iDip2px), animConfig);
                            }
                        }
                        if (this.mRequestState != RequestState.STATE_INPUT && this.mRequestState != RequestState.STATE_LOADING) {
                            setUiState(RequestState.STATE_INPUT);
                            if (Build.VERSION.SDK_INT >= 33 && this.mIsApplyShaderEffect) {
                                animateGradientBgFromPanelToInput(i);
                            }
                        }
                        RelativeLayout.LayoutParams layoutParams2 = (RelativeLayout.LayoutParams) this.mInputLayout.getLayoutParams();
                        layoutParams2.addRule(9, 0);
                        layoutParams2.addRule(11, 0);
                        layoutParams2.addRule(14);
                        this.mInputLayout.setLayoutParams(layoutParams2);
                    } else if (iArr[0] == DisplayUtil.getScreenMetrics(this.mContext).heightPixels) {
                        Logger.INSTANCE.i(TAG, "floating height == screen height");
                        height = DisplayUtil.getScreenMetrics(this.mContext).heightPixels;
                    } else {
                        Logger.INSTANCE.i(TAG, "floating height > screen height");
                        height2 = DisplayUtil.getScreenMetrics(this.mContext).heightPixels - this.mInputLayout.getHeight();
                        navigationBarHeightFromProp2 = DisplayUtil.getNavigationBarHeightFromProp(this.mContext);
                    }
                    height = height2 + navigationBarHeightFromProp2;
                } else if (iArr[0] <= DisplayUtil.getScreenMetrics(this.mContext).heightPixels) {
                    Logger.INSTANCE.i(TAG, "no navigation & right height");
                    height = iArr[0];
                } else {
                    Logger.INSTANCE.i(TAG, "no navigation & screen height");
                    height = DisplayUtil.getScreenMetrics(this.mContext).heightPixels - this.mInputLayout.getHeight();
                }
                navigationBarHeightFromProp = height - y;
                iDip2px = (i - navigationBarHeightFromProp) + DisplayUtil.dip2px(this.mContext, 12.0f);
                Logger.INSTANCE.i(TAG, " mFloatingView.getHeight() " + iArr[0] + " DisplayUtil.getNavigationBarHeightFromProp(mContext) " + DisplayUtil.getNavigationBarHeightFromProp(this.mContext) + " y1  " + y + " y2 " + navigationBarHeightFromProp + " imeHeight " + i + " upDy " + iDip2px);
                if (this.mRequestState != RequestState.STATE_LOADING) {
                    this.mInputRimEffectView.setAlpha(0.0f);
                    if (KeyboardListenerHelper.isHardwareKeyboard(this.mContext)) {
                        iDip2px += DisplayUtil.getNavigationBarHeightFromProp(this.mContext);
                    }
                    animConfig = new AnimConfig();
                    animConfig.addListeners(new TransitionListener() { // from class: com.miui.notes.ai.floatingwindow.AiFloatingWindow.20
                        @Override // miuix.animation.listener.TransitionListener
                        public void onBegin(Object obj) {
                            super.onBegin(obj);
                            AiFloatingWindow.this.mRootView.setEnabled(false);
                        }

                        @Override // miuix.animation.listener.TransitionListener
                        public void onUpdate(Object obj, Collection<UpdateInfo> collection) {
                            super.onUpdate(obj, collection);
                            UpdateInfo updateInfoFindByName = UpdateInfo.findByName(collection, "translationY");
                            if (updateInfoFindByName != null) {
                                AiFloatingWindow.this.mInputRimEffectView.setAlpha((float) (updateInfoFindByName.animInfo.value / updateInfoFindByName.animInfo.targetValue));
                            }
                            AiFloatingWindow.this.onViewPositionChanged(ViewType.PANEL_INPUT);
                            if (Build.VERSION.SDK_INT >= 33 && AiFloatingWindow.this.mIsApplyShaderEffect && AiFloatingWindow.this.mRequestState == RequestState.STATE_INPUT) {
                                AiFloatingWindow.this.onShowSoftInputShaderEffect();
                            }
                        }

                        @Override // miuix.animation.listener.TransitionListener
                        public void onComplete(Object obj) {
                            super.onComplete(obj);
                            Logger.INSTANCE.d(AiFloatingWindow.TAG, "onShowKeyboard onComplete:" + obj + " ,mInputLayout:" + AiFloatingWindow.this.mInputLayout + " ,finalUpDy:" + iDip2px);
                            if (AiFloatingWindow.this.mInputRimEffectView != null) {
                                AiFloatingWindow.this.mInputRimEffectView.setAlpha(1.0f);
                            }
                            AiFloatingWindow.this.setTouchableRegion();
                            if (AiFloatingWindow.this.mRootView != null) {
                                AiFloatingWindow.this.mRootView.setEnabled(true);
                            } else {
                                Logger.INSTANCE.d(AiFloatingWindow.TAG, "onComplete()|mRootView is null");
                            }
                            if (AiFloatingWindow.this.mInputLayout != null) {
                                AiFloatingWindow.this.mInputLayout.setTranslationY(-iDip2px);
                            }
                            AiFloatingWindow.this.onViewPositionChanged(ViewType.PANEL_INPUT);
                        }

                        @Override // miuix.animation.listener.TransitionListener
                        public void onCancel(Object obj) {
                            super.onCancel(obj);
                            Logger.INSTANCE.d(AiFloatingWindow.TAG, "onShowKeyboard onCancel:" + obj + " ,mInputLayout:" + AiFloatingWindow.this.mInputLayout + " ,finalUpDy:" + iDip2px);
                            MiuiFolme.cleanFolme(AiFloatingWindow.this.mInputLayout);
                            if (AiFloatingWindow.this.mInputRimEffectView != null) {
                                AiFloatingWindow.this.mInputRimEffectView.setAlpha(1.0f);
                            }
                            AiFloatingWindow.this.mHasTouchInput = false;
                        }
                    });
                    if (this.mIsSupportCot) {
                        if (!this.isKeyBoardShowing) {
                            Folme.useAt(this.mInputLayout).state().to(animStateAdd, ease, ViewProperty.TRANSLATION_Y, Integer.valueOf(-iDip2px), animConfig);
                        }
                        if (this.isKeyBoardShowing) {
                            Folme.useAt(this.mInputLayout).state().to(ViewProperty.TRANSLATION_Y, Integer.valueOf(-iDip2px), animConfig);
                        }
                    } else {
                        this.mInputSendView.setVisibility(0);
                        Folme.useAt(this.mInputLayout).state().to(ViewProperty.TRANSLATION_Y, Integer.valueOf(-iDip2px), animConfig);
                    }
                }
                if (this.mRequestState != RequestState.STATE_INPUT) {
                    setUiState(RequestState.STATE_INPUT);
                    if (Build.VERSION.SDK_INT >= 33) {
                        animateGradientBgFromPanelToInput(i);
                    }
                }
                RelativeLayout.LayoutParams layoutParams3 = (RelativeLayout.LayoutParams) this.mInputLayout.getLayoutParams();
                layoutParams3.addRule(9, 0);
                layoutParams3.addRule(11, 0);
                layoutParams3.addRule(14);
                this.mInputLayout.setLayoutParams(layoutParams3);
            } else {
                Logger.INSTANCE.d(TAG, "onShowKeyboard ,mKeyBoardChangeEnd: " + this.mKeyBoardChangeEnd + " ,mKeyBoardVisible: " + this.mKeyBoardVisible + " ,ismRecommendShowBegin: " + this.ismRecommendShowBegin + " ,mKeyBoardChangeStart: " + this.mKeyBoardChangeStart + ",imeHeight： " + i + " ,mImeVisible: " + this.mImeVisible);
                if (this.mRequestState == RequestState.STATE_RECOMMEND && (view = this.mRecommendView) != null && ((this.mKeyBoardChangeEnd && this.mKeyBoardVisible) || (this.ismRecommendShowBegin && !this.mKeyBoardChangeStart))) {
                    RelativeLayout.LayoutParams layoutParams4 = (RelativeLayout.LayoutParams) view.getLayoutParams();
                    if (DisplayUtil.hasNavigationBar(this.mContext) && !this.mImeVisible && i < DisplayUtil.getNavigationBarHeightFromProp(this.mContext)) {
                        layoutParams4.bottomMargin = DisplayUtil.getNavigationBarHeightFromProp(this.mContext);
                    } else {
                        layoutParams4.bottomMargin = i;
                    }
                    this.mRecommendView.setLayoutParams(layoutParams4);
                }
            }
        }
        this.mImeHeight = i;
        this.isKeyBoardShowing = true;
    }

    private boolean isFastTriggered(long j) {
        return j - this.lastTriggeredTime < 100;
    }

    @Override // com.miui.notes.ai.utils.KeyboardListenerHelper.IKeyBoardChangedListener
    public void onHideKeyboard() {
        this.mInput.removeCallbacks(this.mAnimChangeRunnable);
        this.mImeVisible = false;
        Logger.INSTANCE.i(TAG, " onHideKeyboard  mRequestState :" + this.mRequestState);
        Logger.INSTANCE.d(TAG, "onHideKeyboard: mInputLayout" + this.mInputLayout.getHeight() + " mImeHeight: " + this.mImeHeight + " ,mHasTouchInput: " + this.mHasTouchInput);
        if (this.mHasTouchInput && this.mImeHeight > 0) {
            this.mHasTouchInput = false;
            this.mDuringInputToPanel = true;
            Logger.INSTANCE.d(TAG, "onHideKeyboard: mInputIncreasedHeight" + this.mInputIncreasedHeight);
            final int iMax = Math.max(((this.mInputLayout.getHeight() - this.mInputSendView.getLayoutParams().height) - convertDpToPixels(43.0f, this.mContext)) - this.mInputIncreasedHeight, 121);
            AnimState animStateAdd = new AnimState("loadingEnd").add(ViewProperty.HEIGHT, iMax);
            AnimConfig ease = new AnimConfig().setEase(EaseManager.getStyle(-2, 0.9f, 0.35f));
            ease.addListeners(new TransitionListener() { // from class: com.miui.notes.ai.floatingwindow.AiFloatingWindow.21
                @Override // miuix.animation.listener.TransitionListener
                public void onBegin(Object obj) {
                    super.onBegin(obj);
                    if (AiFloatingWindow.this.mAiInputIconView == null || AiFloatingWindow.this.mAIInputDeepSeekButton == null || AiFloatingWindow.this.mInputSendView == null || !AiFloatingWindow.this.mIsSupportCot) {
                        return;
                    }
                    RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) AiFloatingWindow.this.mInput.getLayoutParams();
                    layoutParams.setMarginStart(DisplayUtil.dip2px(AiFloatingWindow.this.mContext, 10.0f));
                    layoutParams.setMarginEnd(DisplayUtil.dip2px(AiFloatingWindow.this.mContext, 10.0f));
                    layoutParams.addRule(15);
                    AiFloatingWindow.this.mInput.setLayoutParams(layoutParams);
                    AiFloatingWindow.this.mInput.setPadding(0, 0, 0, 0);
                    AiFloatingWindow.this.mInputSendView.setVisibility(8);
                    AiFloatingWindow.this.mAiInputIconView.setVisibility(0);
                }

                @Override // miuix.animation.listener.TransitionListener
                public void onCancel(Object obj) {
                    super.onCancel(obj);
                    if (AiFloatingWindow.this.mAIInputDeepSeekButton != null && AiFloatingWindow.this.mIsSupportCot) {
                        AiFloatingWindow.this.mAIInputDeepSeekButton.setVisibility(8);
                    }
                    Logger.INSTANCE.d(AiFloatingWindow.TAG, "onCancel: anim was cancel");
                }

                @Override // miuix.animation.listener.TransitionListener
                public void onComplete(Object obj) {
                    Logger.INSTANCE.d(AiFloatingWindow.TAG, "hide onComplete: mInputLayout height" + AiFloatingWindow.this.mInputLayout.getHeight());
                    super.onComplete(obj);
                    if (AiFloatingWindow.this.mAiInputIconView == null || AiFloatingWindow.this.mAIInputDeepSeekButton == null || AiFloatingWindow.this.mInputSendView == null || !AiFloatingWindow.this.mIsSupportCot) {
                        return;
                    }
                    AiFloatingWindow.this.mAIInputDeepSeekButton.setVisibility(8);
                }
            });
            AnimConfig animConfig = new AnimConfig();
            animConfig.addListeners(new TransitionListener() { // from class: com.miui.notes.ai.floatingwindow.AiFloatingWindow.22
                @Override // miuix.animation.listener.TransitionListener
                public void onBegin(Object obj) {
                    super.onBegin(obj);
                    if (AiFloatingWindow.this.mInput != null) {
                        AiFloatingWindow.this.mInput.setText("");
                        if (AiFloatingWindow.this.mInputGlobalLayoutListener != null) {
                            Logger.INSTANCE.d(AiFloatingWindow.TAG, "onBegin remove mInputGlobalLayoutListener: ");
                            AiFloatingWindow.this.mInputLayout.getViewTreeObserver().removeOnGlobalLayoutListener(AiFloatingWindow.this.mInputGlobalLayoutListener);
                            AiFloatingWindow.this.mInputGlobalLayoutListener = null;
                        }
                        AiFloatingWindow.this.mInput.setFocusableInTouchMode(false);
                    }
                }

                @Override // miuix.animation.listener.TransitionListener
                public void onComplete(Object obj) {
                    super.onComplete(obj);
                    Logger.INSTANCE.d(AiFloatingWindow.TAG, "onHideKeyboard onComplete " + obj + " ,mImeVisible: " + AiFloatingWindow.this.mImeVisible);
                    if (AiFloatingWindow.this.mImeVisible) {
                        return;
                    }
                    AiFloatingWindow.this.handleInputToPanelAnimComplete(false, iMax);
                }

                @Override // miuix.animation.listener.TransitionListener
                public void onUpdate(Object obj, Collection<UpdateInfo> collection) {
                    super.onUpdate(obj, collection);
                    AiFloatingWindow.this.onViewPositionChanged(ViewType.PANEL_INPUT);
                }

                @Override // miuix.animation.listener.TransitionListener
                public void onCancel(Object obj) {
                    super.onCancel(obj);
                    Logger.INSTANCE.d(AiFloatingWindow.TAG, "onHideKeyboard onCancel " + obj + " ,mImeVisible: " + AiFloatingWindow.this.mImeVisible);
                    if (AiFloatingWindow.this.mImeVisible) {
                        return;
                    }
                    AiFloatingWindow.this.handleInputToPanelAnimComplete(true, iMax);
                }
            });
            if (!this.mIsSupportCot) {
                this.mInputSendView.setVisibility(8);
                Folme.useAt(this.mInputLayout).state().to(ViewProperty.TRANSLATION_Y, 0, animConfig);
            } else {
                Folme.useAt(this.mInputLayout).state().to(animStateAdd, ease, ViewProperty.TRANSLATION_Y, 0, animConfig);
            }
            this.mInputIncreasedHeight = 0;
            this.mInputRimEffectView.setAlpha(1.0f);
            Folme.useAt(this.mInputRimEffectView).state().to(ViewProperty.ALPHA, Float.valueOf(0.0f), animConfig);
            if (Build.VERSION.SDK_INT >= 33 && this.mIsApplyShaderEffect) {
                onHideSoftInputShaderEffect();
                animateGradientBgFromInputToPanel();
            }
            if (this.mRequestState == RequestState.STATE_INPUT) {
                setUiState(RequestState.STATE_PANEL);
            }
            Folme.useAt(this.mFloatingContentView).state().to(ViewProperty.ALPHA, Float.valueOf(1.0f), animConfig);
            Folme.useAt(this.mFloatingRimEffectView).state().to(ViewProperty.ALPHA, Float.valueOf(1.0f), animConfig);
            lambda$setOnScreenChange$23(DisplayUtil.isLandScape(this.mContext));
        }
        this.isKeyBoardShowing = false;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void handleInputToPanelAnimComplete(boolean z, int i) {
        Logger.INSTANCE.d(TAG, "handleAnimationComplete  isCancel: " + z + " ,mTargetHeight: " + i);
        if (z) {
            MiuiFolme.cleanFolme(this.mInputLayout);
        }
        ViewGroup.LayoutParams layoutParams = this.mInputLayout.getLayoutParams();
        layoutParams.height = i;
        this.mInputLayout.setTranslationY(0.0f);
        this.mInputLayout.setLayoutParams(layoutParams);
        View view = this.mAIInputDeepSeekButton;
        if (view != null && this.mIsSupportCot) {
            view.setVisibility(8);
        }
        setTouchableRegion();
        onViewPositionChanged(ViewType.PANEL_INPUT);
        this.mInputRimEffectView.setAlpha(0.0f);
        if (Build.VERSION.SDK_INT >= 31 && this.mIsApplyShaderEffect) {
            (this.mIsRecommendInput ? this.mRecommendInputRimEffectView : this.mInputRimEffectView).setRenderEffect(null);
        }
        this.mInput.setFocusableInTouchMode(true);
        this.mDuringInputToPanel = false;
    }

    private void animateGradientBgFromPanelToInput(float f) {
        final int[] iArr = new int[2];
        final float height = this.mFloatingRimEffectView.getHeight();
        final float height2 = this.mInputLayout.getHeight() + (this.mEffectBgPadding * 2);
        this.mFloatingRimEffectView.getLocationInWindow(iArr);
        this.mInputLayout.getLocationInWindow(new int[2]);
        float f2 = iArr[1] + height;
        final float f3 = this.mScreenSize.y - (f - this.mEffectBgPadding);
        final float f4 = f2 - f3;
        Logger.INSTANCE.d(TAG, "animateGradientBgFromPanelToInput, startY: " + f2 + ", endY: " + f3 + ", dis: " + f4 + ", startHeight: " + height2 + ", endHeight: " + height + ", offsetY: " + f);
        final ValueProperty valueProperty = new ValueProperty("bgLocation", 0.03f);
        AnimConfig ease = new AnimConfig().setEase(EaseManager.getStyle(-2, 0.86f, 0.38f));
        ease.addListeners(new TransitionListener() { // from class: com.miui.notes.ai.floatingwindow.AiFloatingWindow.23
            @Override // miuix.animation.listener.TransitionListener
            public void onUpdate(Object obj, Collection<UpdateInfo> collection) {
                super.onUpdate(obj, collection);
                UpdateInfo updateInfoFindBy = UpdateInfo.findBy(collection, valueProperty);
                if (updateInfoFindBy != null) {
                    float floatValue = updateInfoFindBy.getFloatValue();
                    float f5 = 1.0f - ((floatValue - f3) / f4);
                    if (f5 <= 0.0f || f5 >= 1.0f) {
                        return;
                    }
                    float f6 = height;
                    float f7 = f6 - ((f6 - height2) * f5);
                    Logger.INSTANCE.d(AiFloatingWindow.TAG, "onUpdate bg translateY progress: " + f5 + ", value: " + floatValue + ", h: " + f7);
                    AiFloatingWindow.this.mBgBoundData[0] = iArr[0] / AiFloatingWindow.this.mScreenSize.x;
                    AiFloatingWindow.this.mBgBoundData[1] = 1.0f - (floatValue / AiFloatingWindow.this.mScreenSize.y);
                    AiFloatingWindow.this.mBgBoundData[2] = AiFloatingWindow.this.mFloatingRimEffectView.getWidth() / AiFloatingWindow.this.mScreenSize.x;
                    AiFloatingWindow.this.mBgBoundData[3] = f7 / AiFloatingWindow.this.mScreenSize.y;
                    AiFloatingWindow.this.mAiShaderEffectRenderer.setBgBound(AiFloatingWindow.this.mBgBoundData);
                    AiFloatingWindow.this.mAiShaderEffectRenderer.updateMaterial();
                }
            }
        });
        Folme.useValue("bgLocation").setTo(valueProperty, Float.valueOf(f2));
        Folme.useValue("bgLocation").to(valueProperty, Float.valueOf(f3), ease);
    }

    private void animateGradientBgFromInputToPanel() {
        int[] iArr = new int[2];
        int[] iArr2 = new int[2];
        final float height = this.mFloatingRimEffectView.getHeight();
        final float height2 = this.mInputLayout.getHeight() + (this.mEffectBgPadding * 2);
        this.mFloatingRimEffectView.getLocationInWindow(iArr);
        this.mInputLayout.getLocationInWindow(iArr2);
        final float f = iArr2[1] - this.mEffectBgPadding;
        float f2 = iArr[1];
        final float f3 = f2 - f;
        Logger.INSTANCE.d(TAG, "animateBgFromInputToPanel, startY: " + f + ", endY: " + f2 + ", dis: " + f3 + ", startHeight: " + height2 + ", endHeight: " + height);
        final ValueProperty valueProperty = new ValueProperty("bgLocation", 0.03f);
        AnimConfig ease = new AnimConfig().setEase(EaseManager.getStyle(-2, 0.86f, 0.38f));
        ease.addListeners(new TransitionListener() { // from class: com.miui.notes.ai.floatingwindow.AiFloatingWindow.24
            @Override // miuix.animation.listener.TransitionListener
            public void onUpdate(Object obj, Collection<UpdateInfo> collection) {
                super.onUpdate(obj, collection);
                UpdateInfo updateInfoFindBy = UpdateInfo.findBy(collection, valueProperty);
                if (updateInfoFindBy != null) {
                    float floatValue = updateInfoFindBy.getFloatValue();
                    float f4 = (floatValue - f) / f3;
                    float f5 = height2;
                    AiFloatingWindow.this.animateGradientBgArea(floatValue, f5 + (f4 * (height - f5)));
                }
            }
        });
        Folme.useValue("bgLocation").setTo(valueProperty, Float.valueOf(f));
        Folme.useValue("bgLocation").to(valueProperty, Float.valueOf(f2), ease);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void animateGradientBgArea(float f, float f2) {
        int[] iArr = new int[2];
        this.mFloatingRimEffectView.getLocationInWindow(iArr);
        this.mInputLayout.getLocationInWindow(new int[2]);
        this.mBgBoundData[0] = iArr[0] / this.mScreenSize.x;
        this.mBgBoundData[1] = 1.0f - ((f + f2) / this.mScreenSize.y);
        this.mBgBoundData[2] = this.mFloatingRimEffectView.getWidth() / this.mScreenSize.x;
        this.mBgBoundData[3] = f2 / this.mScreenSize.y;
        this.mAiShaderEffectRenderer.setBgBound(this.mBgBoundData);
        this.mAiShaderEffectRenderer.updateMaterial();
        this.mBgGradientColorView.setRenderEffect(this.mAiShaderEffectRenderer.getRenderEffect(8));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void onShowSoftInputShaderEffect() {
        if (this.mCloudIsDevice || this.mIsSupportCot) {
            boolean z = this.mIsRecommendInput;
            View view = z ? this.mRecommendView : this.mInputLayout;
            View view2 = z ? this.mRecommendInputContentView : this.mInputContentView;
            View view3 = z ? this.mRecommendInputRimEffectView : this.mInputRimEffectView;
            int i = z ? this.mRecommendBgPadding : this.mEffectBgPadding;
            tick();
            this.mAiShaderEffectRenderer.setUniforms(this.mTime, 1);
            this.mAiShaderEffectRenderer.setForePanelWH(view2.getWidth(), view2.getHeight());
            this.mAiShaderEffectRenderer.setFloatingPanelWH(view3.getWidth(), view3.getHeight());
            int[] iArr = new int[2];
            view.getLocationInWindow(iArr);
            this.mBgBoundData[0] = view.getLeft() / this.mScreenSize.x;
            this.mBgBoundData[1] = 1.0f - (((iArr[1] + view.getHeight()) + i) / this.mScreenSize.y);
            this.mBgBoundData[2] = view.getWidth() / this.mScreenSize.x;
            this.mBgBoundData[3] = (view.getHeight() + (i * 2.0f)) / this.mScreenSize.y;
            float[] fArr = this.mBgBoundData;
            if (fArr[1] < 0.0f) {
                fArr[1] = 1.0f - ((iArr[1] + view.getHeight()) / this.mScreenSize.y);
                this.mBgBoundData[3] = view.getHeight() / this.mScreenSize.y;
            }
            this.mAiShaderEffectRenderer.setBgBound(this.mBgBoundData);
            this.mAiShaderEffectRenderer.updateMaterial();
            this.mAiShaderEffectRenderer.applyInputStyleBgColors(z);
            view3.setRenderEffect(this.mAiShaderEffectRenderer.getRenderEffect(10));
            this.mShaderState = 2;
        }
    }

    private void onHideSoftInputShaderEffect() {
        tick();
        this.mAiShaderEffectRenderer.setUniforms(this.mTime, 2);
        this.mAiShaderEffectRenderer.setForePanelWH(this.mFloatingContentView.getWidth(), this.mFloatingContentView.getHeight());
        this.mAiShaderEffectRenderer.setFloatingPanelWH(this.mFloatingView.getWidth(), this.mFloatingView.getHeight());
        int[] iArr = new int[2];
        this.mFloatingRimEffectView.getLocationInWindow(iArr);
        this.mBgBoundData[0] = iArr[0] / this.mScreenSize.x;
        this.mBgBoundData[1] = 1.0f - ((iArr[1] + this.mFloatingRimEffectView.getHeight()) / this.mScreenSize.y);
        this.mBgBoundData[2] = this.mFloatingRimEffectView.getWidth() / this.mScreenSize.x;
        this.mBgBoundData[3] = this.mFloatingRimEffectView.getHeight() / this.mScreenSize.y;
        this.mAiShaderEffectRenderer.setBgBound(this.mBgBoundData);
        this.mAiShaderEffectRenderer.updateMaterial();
        this.mAiShaderEffectRenderer.applyPanelBgColors();
        this.mFloatingRimEffectView.setRenderEffect(this.mAiShaderEffectRenderer.getRenderEffect(9));
        if (this.mIsSupportCot) {
            this.mHandler.postDelayed(new Runnable() { // from class: com.miui.notes.ai.floatingwindow.AiFloatingWindow$$ExternalSyntheticLambda30
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.lambda$onHideSoftInputShaderEffect$16();
                }
            }, 100L);
        } else {
            this.mShaderState = 1;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onHideSoftInputShaderEffect$16() {
        this.mShaderState = 1;
    }

    @Override // com.miui.notes.ai.utils.KeyboardListenerHelper.IKeyBoardChangedListener
    public void onPrepare() {
        Logger.INSTANCE.d(TAG, "onKeyBoardChanged onPrepare");
        this.mKeyBoardChangeStart = true;
    }

    @Override // com.miui.notes.ai.utils.KeyboardListenerHelper.IKeyBoardChangedListener
    public void onKeyBoardChanged(int i, boolean z) {
        if (this.mRequestState != RequestState.STATE_RECOMMEND || this.mRecommendView == null) {
            return;
        }
        Logger.INSTANCE.d(TAG, "onKeyBoardChanged height = " + i + " mImeHeight = " + this.mImeHeight);
        if (this.mImeHeight == i) {
            return;
        }
        Logger.INSTANCE.d(TAG, "onKeyBoardChanged mImeVisible = " + this.mImeVisible + " hasNavigationBar = " + DisplayUtil.hasNavigationBar(this.mContext));
        RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) this.mRecommendView.getLayoutParams();
        if (DisplayUtil.hasNavigationBar(this.mContext) && i < DisplayUtil.getNavigationBarHeightFromProp(this.mContext)) {
            layoutParams.bottomMargin = DisplayUtil.getNavigationBarHeightFromProp(this.mContext);
        } else {
            layoutParams.bottomMargin = i;
        }
        this.mRecommendView.setLayoutParams(layoutParams);
        onViewPositionChanged(ViewType.RECOMMEND_INPUT);
        this.mImeHeight = i;
        this.mKeyBoardChangeEnd = false;
    }

    @Override // com.miui.notes.ai.utils.KeyboardListenerHelper.IKeyBoardChangedListener
    public void onKeyBoardChangedEnd(int i, boolean z) {
        View view;
        if (this.mRequestState != RequestState.STATE_RECOMMEND || (view = this.mRecommendView) == null) {
            return;
        }
        RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) view.getLayoutParams();
        Logger.INSTANCE.d(TAG, "onKeyBoardChangedEnd mImeVisible = " + this.mImeVisible + " hasNavigationBar = " + DisplayUtil.hasNavigationBar(this.mContext));
        if (DisplayUtil.hasNavigationBar(this.mContext) && !this.mImeVisible) {
            layoutParams.bottomMargin = DisplayUtil.getNavigationBarHeightFromProp(this.mContext) + i;
        } else if (DisplayUtil.hasNavigationBar(this.mContext)) {
            layoutParams.bottomMargin = Math.max(i, DisplayUtil.getNavigationBarHeightFromProp(this.mContext));
        } else {
            layoutParams.bottomMargin = i;
        }
        this.mRecommendView.setLayoutParams(layoutParams);
        setTouchableRegion();
        this.mImeHeight = i;
        onViewPositionChanged(ViewType.RECOMMEND_INPUT);
        if (this.mRecommendDismiss) {
            MiuiFolme.cleanFolme(this.mRecommendItems);
            this.mKeyboardListenerHelper.removeListener();
            dismissWindow();
        }
        this.mKeyBoardChangeEnd = true;
        this.mKeyBoardChangeStart = false;
        this.mKeyBoardVisible = z;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void recommendSendAnim() {
        TransitionListener transitionListener = new TransitionListener() { // from class: com.miui.notes.ai.floatingwindow.AiFloatingWindow.25
            @Override // miuix.animation.listener.TransitionListener
            public void onComplete(Object obj) {
                super.onComplete(obj);
                MiuiFolme.cleanFolme(AiFloatingWindow.this.mRecommendItems);
                MiuiFolme.goneView(AiFloatingWindow.this.mRecommendView);
                AiFloatingWindow.this.setUiState(RequestState.STATE_LOADING);
                AiFloatingWindow.this.startRequestAiData();
            }

            @Override // miuix.animation.listener.TransitionListener
            public void onUpdate(Object obj, Collection<UpdateInfo> collection) {
                super.onUpdate(obj, collection);
                UpdateInfo updateInfoFindBy = UpdateInfo.findBy(collection, ViewProperty.ALPHA);
                if (updateInfoFindBy != null) {
                    AiFloatingWindow.this.mBgGradientColorView.setAlpha(updateInfoFindBy.getFloatValue());
                }
            }
        };
        TransitionListener transitionListener2 = new TransitionListener();
        AnimConfig ease = new AnimConfig().setEase(EaseManager.getStyle(-2, 0.96f, 0.32f));
        ease.addListeners(transitionListener2);
        AnimConfig ease2 = new AnimConfig().setEase(EaseManager.getStyle(-2, 0.95f, 0.18f));
        ease2.addListeners(transitionListener2);
        for (int i = 0; i < this.mRecommendSize; i++) {
            View[] viewArr = this.mRecommendContainers;
            if (i >= viewArr.length) {
                break;
            }
            viewArr[i].setTranslationY(0.0f);
            Folme.useAt(this.mRecommendContainers[i]).state().to(ViewProperty.ALPHA, Float.valueOf(0.0f), ease2).to(ViewProperty.TRANSLATION_Y, Integer.valueOf(((int) (this.mRecommendInput.getY() + this.mRecommendInput.getHeight())) * 2), ease);
        }
        this.mRecommendInputContentView.setTranslationY(0.0f);
        Folme.useAt(this.mRecommendInputContentView, this.mRecommendInputRimEffectView, this.mBgGradientColorView).state().to(ViewProperty.ALPHA, Float.valueOf(0.0f), new AnimConfig().setEase(EaseManager.getStyle(-2, 0.95f, 0.18f)));
        Folme.useAt(this.mRecommendInput).state().to(ViewProperty.ALPHA, Float.valueOf(0.0f), new AnimConfig().setEase(EaseManager.getStyle(-2, 0.95f, 0.18f)).addListeners(transitionListener));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void backtoRecommendShow() {
        this.mRecommendInputContentView.setAlpha(0.0f);
        this.mRecommendInputRimEffectView.setAlpha(0.0f);
        this.mBgGradientColorView.setAlpha(0.0f);
        this.mHandler.post(new Runnable() { // from class: com.miui.notes.ai.floatingwindow.AiFloatingWindow.26
            @Override // java.lang.Runnable
            public void run() {
                AnimConfig ease = new AnimConfig().setEase(EaseManager.getStyle(-2, 0.95f, 0.18f));
                ease.addListeners(new TransitionListener() { // from class: com.miui.notes.ai.floatingwindow.AiFloatingWindow.26.1
                    @Override // miuix.animation.listener.TransitionListener
                    public void onComplete(Object obj) {
                        super.onComplete(obj);
                        AiFloatingWindow.this.setUiState(RequestState.STATE_RECOMMEND);
                    }

                    @Override // miuix.animation.listener.TransitionListener
                    public void onUpdate(Object obj, Collection<UpdateInfo> collection) {
                        super.onUpdate(obj, collection);
                        UpdateInfo updateInfoFindByName = UpdateInfo.findByName(collection, "alpha");
                        if (updateInfoFindByName != null) {
                            AiFloatingWindow.this.mBgGradientColorView.setAlpha(updateInfoFindByName.getFloatValue());
                        }
                    }

                    @Override // miuix.animation.listener.TransitionListener
                    public void onBegin(Object obj, Collection<UpdateInfo> collection) {
                        super.onBegin(obj, collection);
                        if (KeyboardListenerHelper.isHardwareKeyboard(AiFloatingWindow.this.mContext)) {
                            Logger.INSTANCE.i(AiFloatingWindow.TAG, "in hardware keyboard");
                        } else {
                            Logger.INSTANCE.i(AiFloatingWindow.TAG, "showSoft input for recommend");
                            SoftInputUtil.showSoftInput(AiFloatingWindow.this.mRecommendEdit);
                        }
                    }
                });
                ease.setDelay(50L);
                IStateStyle iStateStyleState = Folme.useAt(AiFloatingWindow.this.mRecommendInputContentView, AiFloatingWindow.this.mRecommendInputRimEffectView, AiFloatingWindow.this.mBgGradientColorView).state();
                ViewProperty viewProperty = ViewProperty.ALPHA;
                Float fValueOf = Float.valueOf(1.0f);
                iStateStyleState.to(viewProperty, fValueOf, new AnimConfig().setEase(EaseManager.getStyle(-2, 0.95f, 0.18f)));
                Folme.useAt(AiFloatingWindow.this.mRecommendInput).state().to(ViewProperty.ALPHA, fValueOf, ease);
            }
        });
        MiuiFolme.goneView(this.mFloatingView, this.mInputLayout);
        this.mRecommendView.setVisibility(0);
        this.mHandler.postDelayed(new Runnable() { // from class: com.miui.notes.ai.floatingwindow.AiFloatingWindow$$ExternalSyntheticLambda3
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.setTouchableRegion();
            }
        }, 50L);
        this.mRecommendEdit.addOnLayoutChangeListener(new View.OnLayoutChangeListener() { // from class: com.miui.notes.ai.floatingwindow.AiFloatingWindow$$ExternalSyntheticLambda4
            @Override // android.view.View.OnLayoutChangeListener
            public final void onLayoutChange(View view, int i, int i2, int i3, int i4, int i5, int i6, int i7, int i8) {
                this.f$0.lambda$backtoRecommendShow$17(view, i, i2, i3, i4, i5, i6, i7, i8);
            }
        });
        TransitionListener transitionListener = new TransitionListener();
        AnimConfig ease = new AnimConfig().setEase(EaseManager.getStyle(-2, 0.96f, 0.32f));
        ease.addListeners(transitionListener);
        AnimConfig ease2 = new AnimConfig().setEase(EaseManager.getStyle(-2, 0.95f, 0.18f));
        ease2.addListeners(transitionListener);
        for (int i = 0; i < this.mRecommendSize; i++) {
            View[] viewArr = this.mRecommendContainers;
            if (i >= viewArr.length) {
                return;
            }
            viewArr[i].setAlpha(0.0f);
            this.mRecommendItems[i].setAlpha(0.0f);
            this.mRecommendContainers[i].setTranslationY((int) (this.mRecommendInput.getY() + this.mRecommendInput.getHeight()));
            Folme.use((View) this.mRecommendItems[i]).state().to(ViewProperty.ALPHA, Float.valueOf(1.0f), ease2).to(ViewProperty.TRANSLATION_Y, 0, ease);
            Folme.use(this.mRecommendContainers[i]).state().to(ViewProperty.ALPHA, Float.valueOf(1.0f), ease2).to(ViewProperty.TRANSLATION_Y, 0, ease);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$backtoRecommendShow$17(View view, int i, int i2, int i3, int i4, int i5, int i6, int i7, int i8) {
        if (i4 - i2 != i8 - i6) {
            setTouchableRegion();
        }
    }

    @Override // com.miui.notes.ai.editor.IWebViewListener
    public void onCorrectNumChanged(int i) {
        mCorrectMisNum += i;
        setCorrectNumTitle();
    }

    @Override // com.miui.notes.ai.editor.IWebViewListener
    public void onFlowDone() {
        this.mHandler.post(new Runnable() { // from class: com.miui.notes.ai.floatingwindow.AiFloatingWindow$$ExternalSyntheticLambda26
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.lambda$onFlowDone$19();
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onFlowDone$19() {
        Logger.INSTANCE.i(TAG, "onFlowDone");
        if (this.mRequestState == RequestState.STATE_PANEL) {
            return;
        }
        this.mOnFlowDone = true;
        this.mHandler.postDelayed(new Runnable() { // from class: com.miui.notes.ai.floatingwindow.AiFloatingWindow$$ExternalSyntheticLambda13
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.lambda$onFlowDone$18();
            }
        }, EmptyOverwriteGuard.USER_EDIT_VALID_WINDOW_MS);
        this.mFirstInvokeFlow = false;
        if (!this.mHasStopGenerating) {
            this.mAiEditor.beforeRefreshNewContent();
        }
        this.mHasStopGenerating = false;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onFlowDone$18() {
        this.mOnFlowDone = false;
    }

    public void notifyRefreshComplete() {
        Logger.INSTANCE.i(TAG, "notifyRefreshComplete");
        this.mHandler.post(new Runnable() { // from class: com.miui.notes.ai.floatingwindow.AiFloatingWindow$$ExternalSyntheticLambda14
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.lambda$notifyRefreshComplete$20();
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$notifyRefreshComplete$20() {
        if (this.mRequestState == RequestState.STATE_LOADING) {
            setUiState(RequestState.STATE_COMPLETE);
        }
        this.mOnFlowDone = false;
    }

    @Override // com.miui.notes.ai.editor.IWebViewListener
    public boolean panelNeedTaller() {
        return loadingHeightAnim();
    }

    @Override // com.miui.notes.ai.editor.IWebViewListener
    public boolean isNightMode() {
        return DisplayUtil.isNightMode(this.mContext);
    }

    @Override // com.miui.notes.ai.editor.IWebViewListener
    public String getStringFromResource(String str) {
        return this.mContext.getResources().getString(com.miui.notes.ai.utils.StringUtils.getResId(str, R.string.class));
    }

    @Override // com.miui.notes.ai.editor.IWebViewListener
    public String getNumberAndStringFromResource(int i, String str) {
        return com.miui.notes.ai.utils.StringUtils.obtainLocalInteger(i, com.miui.notes.ai.utils.StringUtils.getResId(str, R.plurals.class), this.mContext);
    }

    @Override // com.miui.notes.ai.editor.IWebViewListener
    public void onSaveData(String str) {
        this.mAiSavedData = str;
    }

    @Override // com.miui.notes.ai.editor.IWebViewListener
    public void notifySetNewContent() {
        this.mHandler.post(new Runnable() { // from class: com.miui.notes.ai.floatingwindow.AiFloatingWindow$$ExternalSyntheticLambda24
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.lambda$notifySetNewContent$21();
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$notifySetNewContent$21() {
        if (this.mAiEditor != null) {
            Logger.INSTANCE.i(TAG, "notifySetNewContent, isDeleteLabel isEmpty:" + this.mIsDeleteLabel + "  , mDeleteLabel: " + this.mDeleteLabel);
            if (this.mIsDeleteLabel) {
                this.mAiEditor.setDataWithConfig(this.mDeleteLabel + this.mAiEndTip, "");
                this.mAiEditor.refreshNewContent();
                return;
            }
            Logger.INSTANCE.i(TAG, "notifySetNewContent, mAiDoneData isEmpty:" + this.mAiDoneData);
            StringBuilder sb = new StringBuilder();
            String str = this.mAiDoneData;
            if (str == null) {
                str = "";
            }
            this.mAiEditor.setDataWithConfig(sb.append(str).append(this.mAiEndTip).toString(), "");
            this.mAiEditor.refreshNewContent();
        }
    }

    protected void cancelAiAction(boolean z) {
        IAiEditor iAiEditor;
        if (UserInputHolder.getInstance().getRequestType() != RequestType.TYPE_CHECK || (iAiEditor = this.mAiEditor) == null) {
            return;
        }
        iAiEditor.closeCorrectionPop();
    }

    protected void stopGenerating(String str) {
        if (this.mAiEditor != null) {
            if (!TextUtils.isEmpty(str) && !this.mFinishFlow) {
                this.mAiEditor.finishFlow(true, "", "");
            }
            this.mFirstInvokeFlow = false;
            boolean zIsEmpty = TextUtils.isEmpty(str);
            this.mHasStopGenerating = !zIsEmpty;
            if (!zIsEmpty) {
                this.mHandler.postDelayed(new Runnable() { // from class: com.miui.notes.ai.floatingwindow.AiFloatingWindow$$ExternalSyntheticLambda29
                    @Override // java.lang.Runnable
                    public final void run() {
                        this.f$0.lambda$stopGenerating$22();
                    }
                }, 1500L);
            }
            this.mAiEditor.stopInput(str);
            AINetWorkUtils aINetWorkUtils = this.mAINetWorkUtils;
            if (aINetWorkUtils != null) {
                aINetWorkUtils.stopRequest();
                if (this.mCloudIsDevice) {
                    this.mAINetWorkUtils = null;
                    if (this.mIsThinkContent) {
                        this.mThinkTime = System.currentTimeMillis() - this.mStartRequestTime;
                    }
                }
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$stopGenerating$22() {
        this.mHasStopGenerating = false;
    }

    public void registerBackEventReceiver() {
        this.mRootView.setRecyclingWindowAction(this.recyclingWindowAction);
    }

    public void registerNavigationKeyEventReceiver() {
        SystemNavigationKeyClickListener systemNavigationKeyClickListener = SystemNavigationKeyClickListener.getInstance();
        Action action = this.recyclingWindowAction;
        systemNavigationKeyClickListener.registerHomeKeyEventReceiver(action, action, this.mContext);
    }

    public void unregisterNavigationKeyEventReceiver() {
        SystemNavigationKeyClickListener systemNavigationKeyClickListener = SystemNavigationKeyClickListener.getInstance();
        Context context = this.mContext;
        Action action = this.recyclingWindowAction;
        systemNavigationKeyClickListener.unregisterHomeKeyEventReceiver(context, action, action);
    }

    public void onConfigChange() {
        setTouchableRegion();
    }

    @Override // com.miui.notes.ai.ui.FloatingDecorView.OnScreenChangeListener, com.miui.notes.ai.AiTextWidgetService.OnScreenChangeListener
    public void setOnScreenChange(final boolean z) {
        if (RomUtils.isPhoneDevice() && SplitScreenModelUtils.isSplitScreenMode(this.mHostTaskId) == SplitScreenModelUtils.SplitResult.TRUE && z) {
            Logger.INSTANCE.i(TAG, "When the mobile phone is split screen and landscape, detachFromWindow");
            detachFromWindow();
        }
        this.lastOri = this.currentOri;
        this.currentOri = !z ? 1 : 0;
        if (this.smallWindowRect != null) {
            this.lastRotation = this.currentRotation;
            this.currentRotation = FreeModeUtils.getRotationFreeMode(this.mHostTaskId);
        }
        if (RomUtils.isFoldDevice()) {
            this.lastScreenRect = this.currentScreenRect;
            this.currentScreenRect = FreeModeUtils.isHostInFreeMode(this.mHostTaskId);
            this.lastNarrow = this.currentNarrow;
            this.currentNarrow = ScreenPosistionUtils.isInNarrowScreen(this.mContext) ? 1 : 0;
        }
        DisplayMetrics screenMetrics = DisplayUtil.getScreenMetrics(this.mContext);
        this.mScreenSize.set(screenMetrics.widthPixels, screenMetrics.heightPixels);
        Logger.INSTANCE.d(TAG, "setOnScreenChange, size: " + this.mScreenSize + " inNarrow " + ScreenPosistionUtils.isInNarrowScreen(this.mContext));
        this.smallWindowRect = FreeModeUtils.isHostInFreeMode(this.mHostTaskId);
        setRecommendPanelState(z);
        if (RomUtils.isFoldDevice()) {
            this.mHandler.postDelayed(new Runnable() { // from class: com.miui.notes.ai.floatingwindow.AiFloatingWindow$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.lambda$setOnScreenChange$23(z);
                }
            }, 10L);
        } else {
            lambda$setOnScreenChange$23(z);
        }
    }

    private void panelAlphaAnim() {
        AnimConfig animConfig = new AnimConfig();
        animConfig.setEase(EaseManager.getStyle(-2, 0.95f, 0.35f));
        animConfig.addListeners(new TransitionListener() { // from class: com.miui.notes.ai.floatingwindow.AiFloatingWindow.27
            @Override // miuix.animation.listener.TransitionListener
            public void onComplete(Object obj) {
                super.onComplete(obj);
                MiuiFolme.cleanFolme(AiFloatingWindow.this.mFloatingRimEffectView);
                MiuiFolme.cleanFolme(AiFloatingWindow.this.mFloatingContentView);
                AiFloatingWindow.this.mFloatingView.setAlpha(1.0f);
                AiFloatingWindow.this.mFloatingView.setScaleX(1.0f);
                AiFloatingWindow.this.mFloatingView.setScaleY(1.0f);
                AiFloatingWindow.this.mFloatingContentView.setAlpha(1.0f);
                AiFloatingWindow.this.mFloatingRimEffectView.setAlpha(1.0f);
                AiFloatingWindow.this.setTouchableRegion();
                AiFloatingWindow.this.onViewPositionChanged(ViewType.PANEL);
                AiFloatingWindow.this.onPanelChangeEnd();
            }
        });
        Folme.useAt(this.mFloatingRimEffectView, this.mFloatingContentView).state().to(ViewProperty.ALPHA, Float.valueOf(1.0f), ViewProperty.SCALE_X, 1, ViewProperty.SCALE_Y, 1, animConfig);
    }

    private void showPanelAnim() {
        this.mFloatingView.setAlpha(0.0f);
        this.mInputLayout.setAlpha(0.0f);
        this.mPanelView.setVisibility(0);
        this.mPanelView.setAlpha(1.0f);
        setFlagViewHeight();
        this.mHandler.postDelayed(new Runnable() { // from class: com.miui.notes.ai.floatingwindow.AiFloatingWindow.28
            @Override // java.lang.Runnable
            public void run() {
                try {
                    AnimState animStateAdd = new AnimState("panelShowStart").add(ViewProperty.TRANSLATION_Y, AiFloatingWindow.this.mFloatingView.getHeight());
                    AnimState animStateAdd2 = new AnimState("panelShowEnd").add(ViewProperty.TRANSLATION_Y, 0.0d);
                    AnimConfig ease = new AnimConfig().setEase(EaseManager.getStyle(-2, 0.86f, 0.38f));
                    ease.addListeners(new TransitionListener() { // from class: com.miui.notes.ai.floatingwindow.AiFloatingWindow.28.1
                        @Override // miuix.animation.listener.TransitionListener
                        public void onUpdate(Object obj, Collection<UpdateInfo> collection) {
                            super.onUpdate(obj, collection);
                            AiFloatingWindow.this.onViewPositionChanged(ViewType.PANEL);
                        }

                        @Override // miuix.animation.listener.TransitionListener
                        public void onBegin(Object obj) {
                            Logger.INSTANCE.d(AiFloatingWindow.TAG, "showPanelAnim onBegin");
                            AiFloatingWindow.this.mFloatingView.setAlpha(1.0f);
                            AiFloatingWindow.this.mInputLayout.setAlpha(1.0f);
                            super.onBegin(obj);
                            AiFloatingWindow.this.increaseHeights();
                            AiFloatingWindow.this.onViewPositionChanged(ViewType.PANEL);
                            AiFloatingWindow.this.mInput.setFocusableInTouchMode(false);
                        }

                        @Override // miuix.animation.listener.TransitionListener
                        public void onComplete(Object obj) {
                            Logger.INSTANCE.d(AiFloatingWindow.TAG, "showPanelAnim onComplete");
                            MiuiFolme.cleanFolme(AiFloatingWindow.this.mFloatingView);
                            MiuiFolme.cleanFolme(AiFloatingWindow.this.mInputLayout);
                            AiFloatingWindow.this.setTouchableRegion();
                            AiFloatingWindow.this.onViewPositionChanged(ViewType.PANEL);
                            AiFloatingWindow.this.onPanelChangeEnd();
                            AiFloatingWindow.this.initWebView();
                            AiFloatingWindow.this.mInput.setFocusableInTouchMode(true);
                            if (AiFloatingWindow.this.mAINetWorkUtils != null) {
                                AiFloatingWindow.this.mAINetWorkUtils.initModel(LLMManager.LLM_BASE_MODEL);
                            }
                        }
                    });
                    Folme.useAt(AiFloatingWindow.this.mFloatingView, AiFloatingWindow.this.mInputLayout).state().fromTo(animStateAdd, animStateAdd2, ease);
                } catch (Exception e) {
                    Logger.INSTANCE.e(AiFloatingWindow.TAG, "e : " + e);
                }
            }
        }, 50L);
    }

    private void backToPanelAnim() {
        this.mGeneratingView.setVisibility(0);
        MiuiFolme.goneView(this.mPanelView, this.mInputLayout);
        this.mHandler.post(new Runnable() { // from class: com.miui.notes.ai.floatingwindow.AiFloatingWindow.29
            @Override // java.lang.Runnable
            public void run() {
                try {
                    int height = AiFloatingWindow.this.mGeneratingView.getHeight();
                    int height2 = AiFloatingWindow.this.mPanelView.getHeight();
                    if (AiFloatingWindow.this.mGeneratingView.getHeight() > AiFloatingWindow.this.mPanelView.getHeight()) {
                        height = (int) ((AiFloatingWindow.this.mGeneratingView.getHeight() - AiFloatingWindow.this.mInputLayout.getHeight()) - AiFloatingWindow.this.mContext.getResources().getDimension(R.dimen.ai_panel_layout_margin_top));
                    }
                    AnimState animStateAdd = new AnimState("backToPanelStart").add(ViewProperty.HEIGHT, height).add(ViewProperty.ALPHA, 1.0d);
                    AnimState animStateAdd2 = new AnimState("backToPanelEnd").add(ViewProperty.HEIGHT, height2);
                    AnimConfig ease = new AnimConfig().setEase(EaseManager.getStyle(-2, 0.9f, 0.35f));
                    ease.addListeners(new TransitionListener() { // from class: com.miui.notes.ai.floatingwindow.AiFloatingWindow.29.1
                        @Override // miuix.animation.listener.TransitionListener
                        public void onComplete(Object obj) {
                            MiuiFolme.cleanFolme(AiFloatingWindow.this.mPanelView);
                            AiFloatingWindow.this.setTouchableRegion();
                            AiFloatingWindow.this.onPanelChangeEnd();
                        }

                        @Override // miuix.animation.listener.TransitionListener
                        public void onUpdate(Object obj, Collection<UpdateInfo> collection) {
                            super.onUpdate(obj, collection);
                            AiFloatingWindow.this.onViewPositionChanged(ViewType.PANEL);
                        }
                    });
                    Folme.useAt(AiFloatingWindow.this.mPanelView).state().fromTo(animStateAdd, animStateAdd2, ease);
                    AiFloatingWindow.this.mGeneratingView.setVisibility(8);
                    RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) AiFloatingWindow.this.mGeneratingView.getLayoutParams();
                    layoutParams.height = height2;
                    AiFloatingWindow.this.mGeneratingView.setLayoutParams(layoutParams);
                    if (AiFloatingWindow.this.mCloudIsDevice || AiFloatingWindow.this.mIsSupportCot) {
                        MiuiFolme.visibleView(AiFloatingWindow.this.mPanelView, AiFloatingWindow.this.mInputLayout, AiFloatingWindow.this.mInputRimEffectView, AiFloatingWindow.this.mInputContentView);
                    } else {
                        MiuiFolme.visibleView(AiFloatingWindow.this.mPanelView);
                        MiuiFolme.goneView(AiFloatingWindow.this.mInputLayout, AiFloatingWindow.this.mInputRimEffectView, AiFloatingWindow.this.mInputContentView);
                    }
                    AiFloatingWindow.this.mPanelView.setAlpha(1.0f);
                    AiFloatingWindow.this.mInputLayout.setAlpha(1.0f);
                    AiFloatingWindow.this.mInputRimEffectView.setAlpha(1.0f);
                    AiFloatingWindow.this.mInputContentView.setAlpha(1.0f);
                } catch (Exception e) {
                    Logger.INSTANCE.e(AiFloatingWindow.TAG, "e : " + e);
                }
            }
        });
    }

    private void loadingStartAnim() {
        Logger.INSTANCE.d(TAG, "loadingStartAnim");
        this.mAiEditor.clearDoc();
        MiuiFolme.visibleView(this.mGeneratingView, this.mAiDoneTab, this.mGeneratingStop, this.mTitleLayout, this.mFloatingView);
        MiuiFolme.goneView(this.mAiBack, this.mAiRefresh, this.mAiCopy, this.mAiInsert, this.mAiDone, this.mAiWaterMarkView);
        if (this.mRequestState == RequestState.STATE_INPUT) {
            this.mFloatingView.setScaleX(1.0f);
            this.mFloatingView.setScaleY(1.0f);
            this.mFloatingContentView.setScaleX(1.0f);
            this.mFloatingContentView.setScaleY(1.0f);
            this.mFloatingRimEffectView.setScaleX(1.0f);
            this.mFloatingRimEffectView.setScaleY(1.0f);
        }
        if (this.mRequestState == RequestState.STATE_RECOMMEND) {
            this.mFloatingView.setAlpha(0.0f);
            startLoadingFromRecommend();
        } else if (this.mRequestState == RequestState.STATE_PANEL || this.mRequestState == RequestState.STATE_INPUT) {
            this.mFloatingView.setAlpha(1.0f);
            this.mTitleLayout.setAlpha(1.0f);
            this.mGeneratingView.setAlpha(0.0f);
            this.mGeneratingStop.setAlpha(0.0f);
            dismissPanelView();
        } else if (this.mRequestState == RequestState.STATE_COMPLETE) {
            this.mGeneratingStop.setAlpha(0.0f);
            startLoadingFromPanel();
        } else {
            this.mFloatingView.setAlpha(1.0f);
            this.mTitleLayout.setAlpha(1.0f);
            this.mGeneratingView.setAlpha(0.0f);
            this.mGeneratingStop.setAlpha(0.0f);
            startLoadingFromPanel();
        }
        this.mPanelAiTitle.setSingleLine(true);
        this.mPanelAiTitle.setPadding(0, (int) TypedValue.applyDimension(1, 19.0f, Resources.getSystem().getDisplayMetrics()), 0, (int) TypedValue.applyDimension(1, 14.0f, Resources.getSystem().getDisplayMetrics()));
    }

    private void dismissPanelView() {
        Logger.INSTANCE.d(TAG, "dismissPanelView");
        this.mPanelView.setAlpha(0.0f);
        this.mInputRimEffectView.setAlpha(0.0f);
        this.mInputContentView.setAlpha(0.0f);
        Logger.INSTANCE.i(TAG, "dismissPanelView mNetError: " + this.mNetError);
        if (!this.mNetError) {
            startLoadingFromPanel();
        } else {
            MiuiFolme.goneView(this.mPanelView);
            this.mNetError = false;
        }
    }

    private void startLoadingFromPanel() {
        Logger.INSTANCE.d(TAG, "startLoadingFromPanel");
        this.mGeneratingView.postOnAnimation(new Runnable() { // from class: com.miui.notes.ai.floatingwindow.AiFloatingWindow.30
            @Override // java.lang.Runnable
            public void run() {
                int dimension;
                try {
                    if (AiFloatingWindow.this.mAiWebView != null && AiFloatingWindow.this.mAiWebView.getVisibility() == 0) {
                        dimension = AiFloatingWindow.this.mFloatingContentView.getHeight() - AiFloatingWindow.this.mTitleLayout.getHeight();
                    } else {
                        dimension = (int) AiFloatingWindow.this.mContext.getResources().getDimension(R.dimen.ai_window_no_webview_height);
                    }
                    AnimState animStateAdd = new AnimState("loadingStart").add(ViewProperty.HEIGHT, (AiFloatingWindow.this.mCloudIsDevice || AiFloatingWindow.this.mIsSupportCot) ? AiFloatingWindow.this.mFloatingContentView.getHeight() - AiFloatingWindow.this.mTitleLayout.getHeight() : dimension);
                    AnimState animStateAdd2 = new AnimState("loadingEnd").add(ViewProperty.HEIGHT, dimension);
                    AnimConfig ease = new AnimConfig().setEase(EaseManager.getStyle(-2, 0.9f, 0.35f));
                    ease.addListeners(new AnonymousClass1());
                    Folme.useAt(AiFloatingWindow.this.mGeneratingView).state().fromTo(animStateAdd, animStateAdd2, ease);
                    AnimState animStateAdd3 = new AnimState("loadingStart").add(ViewProperty.ALPHA, 0.0d);
                    AnimState animStateAdd4 = new AnimState("loadingEnd").add(ViewProperty.ALPHA, 1.0d);
                    AnimConfig ease2 = new AnimConfig().setEase(EaseManager.getStyle(-2, 0.95f, 0.35f));
                    ease2.addListeners(new TransitionListener() { // from class: com.miui.notes.ai.floatingwindow.AiFloatingWindow.30.2
                        @Override // miuix.animation.listener.TransitionListener
                        public void onComplete(Object obj) {
                            super.onComplete(obj);
                            AiFloatingWindow.this.mGeneratingView.setAlpha(1.0f);
                            AiFloatingWindow.this.mGeneratingStop.setAlpha(1.0f);
                            AiFloatingWindow.this.setTouchableRegion();
                        }

                        @Override // miuix.animation.listener.TransitionListener
                        public void onBegin(Object obj, Collection<UpdateInfo> collection) {
                            super.onBegin(obj, collection);
                            AiFloatingWindow.this.mPanelAiTitle.setSingleLine(true);
                        }
                    });
                    Folme.useAt(AiFloatingWindow.this.mGeneratingView).state().fromTo(animStateAdd3, animStateAdd4, ease2);
                    Folme.useAt(AiFloatingWindow.this.mGeneratingStop).state().fromTo(animStateAdd3, animStateAdd4, ease2);
                } catch (Exception e) {
                    Logger.INSTANCE.e(AiFloatingWindow.TAG, "e : " + e);
                }
            }

            /* JADX INFO: renamed from: com.miui.notes.ai.floatingwindow.AiFloatingWindow$30$1, reason: invalid class name */
            class AnonymousClass1 extends TransitionListener {
                AnonymousClass1() {
                }

                @Override // miuix.animation.listener.TransitionListener
                public void onBegin(Object obj) {
                    MiuiFolme.goneView(AiFloatingWindow.this.mPanelView, AiFloatingWindow.this.mInputLayout, AiFloatingWindow.this.mRecommendView);
                    super.onBegin(obj);
                    AiFloatingWindow.this.mPanelEffectRunnable.run();
                    AiFloatingWindow.this.onViewPositionChanged(ViewType.PANEL);
                }

                @Override // miuix.animation.listener.TransitionListener
                public void onUpdate(Object obj, Collection<UpdateInfo> collection) {
                    AiFloatingWindow.this.onViewPositionChanged(ViewType.PANEL);
                    super.onUpdate(obj, collection);
                    AiFloatingWindow.this.mPanelEffectRunnable.run();
                }

                /* JADX INFO: Access modifiers changed from: private */
                public /* synthetic */ void lambda$onComplete$0() {
                    AiFloatingWindow.this.mLoadingEndHeight = AiFloatingWindow.this.mGeneratingView.getHeight();
                }

                @Override // miuix.animation.listener.TransitionListener
                public void onComplete(Object obj) {
                    AiFloatingWindow.this.mHandler.post(new Runnable() { // from class: com.miui.notes.ai.floatingwindow.AiFloatingWindow$30$1$$ExternalSyntheticLambda0
                        @Override // java.lang.Runnable
                        public final void run() {
                            this.f$0.lambda$onComplete$0();
                        }
                    });
                    AiFloatingWindow.this.mGeneratingView.setAlpha(1.0f);
                    AiFloatingWindow.this.mGeneratingStop.setAlpha(1.0f);
                    MiuiFolme.cleanFolme(AiFloatingWindow.this.mGeneratingView);
                    AiFloatingWindow.this.setTouchableRegion();
                    AiFloatingWindow.this.onViewPositionChanged(ViewType.PANEL);
                    AiFloatingWindow.this.onPanelChangeEnd();
                }

                @Override // miuix.animation.listener.TransitionListener
                public void onCancel(Object obj) {
                    super.onCancel(obj);
                    RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) AiFloatingWindow.this.mGeneratingView.getLayoutParams();
                    if (AiFloatingWindow.this.mAiWebView != null && AiFloatingWindow.this.mAiWebView.getVisibility() == 8) {
                        layoutParams.height = (int) AiFloatingWindow.this.mContext.getResources().getDimension(R.dimen.ai_window_no_webview_height);
                    } else {
                        layoutParams.height = AiFloatingWindow.this.mFloatingContentView.getHeight() - AiFloatingWindow.this.mTitleLayout.getHeight();
                    }
                    AiFloatingWindow.this.mGeneratingView.setLayoutParams(layoutParams);
                }
            }
        });
    }

    private void startLoadingFromRecommend() {
        Logger.INSTANCE.d(TAG, "startLoadingFromRecommend");
        this.mHandler.post(new Runnable() { // from class: com.miui.notes.ai.floatingwindow.AiFloatingWindow.31
            @Override // java.lang.Runnable
            public void run() {
                try {
                    RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) AiFloatingWindow.this.mGeneratingView.getLayoutParams();
                    layoutParams.height = (int) AiFloatingWindow.this.mContext.getResources().getDimension(R.dimen.ai_window_webview_height);
                    AiFloatingWindow.this.mGeneratingView.setLayoutParams(layoutParams);
                    AiFloatingWindow.this.resetGenerateWindowSize();
                    AnimState animStateAdd = new AnimState("loadingStart").add(ViewProperty.TRANSLATION_Y, AiFloatingWindow.this.mFloatingView.getHeight());
                    AnimState animStateAdd2 = new AnimState("loadingEnd").add(ViewProperty.TRANSLATION_Y, 0.0d);
                    AnimConfig ease = new AnimConfig().setEase(EaseManager.getStyle(-2, 0.86f, 0.38f));
                    ease.addListeners(new TransitionListener() { // from class: com.miui.notes.ai.floatingwindow.AiFloatingWindow.31.1
                        @Override // miuix.animation.listener.TransitionListener
                        public void onBegin(Object obj) {
                            super.onBegin(obj);
                            AiFloatingWindow.this.mGeneratingStop.setAlpha(1.0f);
                            AiFloatingWindow.this.mFloatingView.setAlpha(1.0f);
                            MiuiFolme.goneView(AiFloatingWindow.this.mPanelView, AiFloatingWindow.this.mInputLayout, AiFloatingWindow.this.mRecommendView);
                            AiFloatingWindow.this.onViewPositionChanged(ViewType.PANEL);
                        }

                        @Override // miuix.animation.listener.TransitionListener
                        public void onUpdate(Object obj, Collection<UpdateInfo> collection) {
                            super.onUpdate(obj, collection);
                            AiFloatingWindow.this.onViewPositionChanged(ViewType.PANEL);
                        }

                        @Override // miuix.animation.listener.TransitionListener
                        public void onComplete(Object obj) {
                            AiFloatingWindow.this.mLoadingEndHeight = AiFloatingWindow.this.mGeneratingView.getHeight();
                            MiuiFolme.cleanFolme(AiFloatingWindow.this.mFloatingView);
                            AiFloatingWindow.this.setTouchableRegion();
                            AiFloatingWindow.this.onViewPositionChanged(ViewType.PANEL);
                            AiFloatingWindow.this.onPanelChangeEnd();
                        }
                    });
                    Folme.useAt(AiFloatingWindow.this.mFloatingView).state().fromTo(animStateAdd, animStateAdd2, ease);
                    AiFloatingWindow.this.fadeInBgColorView();
                } catch (Exception e) {
                    Logger.INSTANCE.e(AiFloatingWindow.TAG, "e : " + e);
                }
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void fadeInBgColorView() {
        Logger.INSTANCE.d(TAG, "fadeInBgColorView");
        ValueAnimator valueAnimatorOfFloat = ValueAnimator.ofFloat(0.0f, 1.0f);
        valueAnimatorOfFloat.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: com.miui.notes.ai.floatingwindow.AiFloatingWindow$$ExternalSyntheticLambda28
            @Override // android.animation.ValueAnimator.AnimatorUpdateListener
            public final void onAnimationUpdate(ValueAnimator valueAnimator) {
                this.f$0.lambda$fadeInBgColorView$24(valueAnimator);
            }
        });
        valueAnimatorOfFloat.addListener(new Animator.AnimatorListener() { // from class: com.miui.notes.ai.floatingwindow.AiFloatingWindow.32
            @Override // android.animation.Animator.AnimatorListener
            public void onAnimationRepeat(Animator animator) {
            }

            @Override // android.animation.Animator.AnimatorListener
            public void onAnimationStart(Animator animator) {
            }

            @Override // android.animation.Animator.AnimatorListener
            public void onAnimationEnd(Animator animator) {
                AiFloatingWindow.this.mBgGradientColorView.setAlpha(1.0f);
            }

            @Override // android.animation.Animator.AnimatorListener
            public void onAnimationCancel(Animator animator) {
                AiFloatingWindow.this.mBgGradientColorView.setAlpha(1.0f);
            }
        });
        valueAnimatorOfFloat.setInterpolator(new SpringInterpolator());
        valueAnimatorOfFloat.setDuration(500L).start();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$fadeInBgColorView$24(ValueAnimator valueAnimator) {
        float fFloatValue = ((Float) valueAnimator.getAnimatedValue()).floatValue();
        Logger.INSTANCE.d(TAG, "onAnimationUpdate, float value: " + fFloatValue);
        this.mBgGradientColorView.setAlpha(fFloatValue);
    }

    private void backToRecommend() {
        Logger.INSTANCE.d(TAG, "backToRecommend");
        resetGenerateWindowSize();
        this.mHandler.post(new Runnable() { // from class: com.miui.notes.ai.floatingwindow.AiFloatingWindow.33
            @Override // java.lang.Runnable
            public void run() {
                AnimConfig ease = new AnimConfig().setEase(EaseManager.getStyle(-2, 0.96f, 0.32f));
                ease.addListeners(new TransitionListener() { // from class: com.miui.notes.ai.floatingwindow.AiFloatingWindow.33.1
                    @Override // miuix.animation.listener.TransitionListener
                    public void onUpdate(Object obj, Collection<UpdateInfo> collection) {
                        super.onUpdate(obj, collection);
                    }

                    @Override // miuix.animation.listener.TransitionListener
                    public void onComplete(Object obj) {
                        AiFloatingWindow.this.mFloatingView.setVisibility(8);
                        MiuiFolme.cleanFolme(AiFloatingWindow.this.mFloatingView);
                        AiFloatingWindow.this.onViewPositionChanged(ViewType.RECOMMEND_INPUT);
                        AiFloatingWindow.this.onPanelChangeEnd();
                        AiFloatingWindow.this.backtoRecommendShow();
                    }
                });
                Logger.INSTANCE.i(AiFloatingWindow.TAG, "backToRecommend");
                if (DisplayUtil.hasNavigationBar(AiFloatingWindow.this.mContext)) {
                    Folme.useAt(AiFloatingWindow.this.mFloatingView).state().to(ViewProperty.TRANSLATION_Y, Integer.valueOf(AiFloatingWindow.this.mFloatingView.getHeight() + DisplayUtil.getNavigationBarHeightFromProp(AiFloatingWindow.this.mContext)), ease);
                } else {
                    Folme.useAt(AiFloatingWindow.this.mFloatingView).state().to(ViewProperty.TRANSLATION_Y, Integer.valueOf(AiFloatingWindow.this.mFloatingView.getHeight()), ease);
                }
            }
        });
    }

    private boolean loadingHeightAnim() {
        int maxHeight = DisplayUtil.getMaxHeight(this.mContext);
        final int height = this.mGeneratingView.getHeight();
        int dimension = (int) this.mContext.getResources().getDimension(R.dimen.ai_loading_height_up);
        if (Math.abs((this.mTitleLayout.getHeight() + height) - maxHeight) <= 1 && !this.inHighAnim) {
            Logger.INSTANCE.d(TAG, "window max height, return :" + height + ", mTitleLayout.getHeight() = " + this.mTitleLayout.getHeight() + ", maxHeight = " + maxHeight);
            return false;
        }
        if (this.mAiWebView.getVisibility() == 8 && !this.inHighAnim) {
            Logger.INSTANCE.d(TAG, "WebView GONE, return :" + height);
            return false;
        }
        if (this.inHighAnim) {
            return true;
        }
        final int iMin = Math.min(dimension, (maxHeight - this.mTitleLayout.getHeight()) - height);
        this.inHighAnim = true;
        this.mHandler.postDelayed(new Runnable() { // from class: com.miui.notes.ai.floatingwindow.AiFloatingWindow.34
            @Override // java.lang.Runnable
            public void run() {
                try {
                    Logger.INSTANCE.d(AiFloatingWindow.TAG, "loadingHeightAnim mGeneratingView: " + AiFloatingWindow.this.mGeneratingView.getHeight() + " , newHeight :" + ((int) (height + AiFloatingWindow.this.mContext.getResources().getDimension(R.dimen.ai_loading_height_up))));
                    AnimState animStateAdd = new AnimState("loadingHeightStart").add(ViewProperty.HEIGHT, height);
                    AnimState animStateAdd2 = new AnimState("loadingHeightEnd").add(ViewProperty.HEIGHT, height + iMin);
                    AnimConfig ease = new AnimConfig().setEase(EaseManager.getStyle(-2, 0.9f, 0.35f));
                    ease.addListeners(new TransitionListener() { // from class: com.miui.notes.ai.floatingwindow.AiFloatingWindow.34.1
                        @Override // miuix.animation.listener.TransitionListener
                        public void onBegin(Object obj, Collection<UpdateInfo> collection) {
                            Logger.INSTANCE.d(AiFloatingWindow.TAG, "loadingHeightAnim onBegin: " + obj);
                            super.onBegin(obj, collection);
                            AiFloatingWindow.this.onViewPositionChanged(ViewType.PANEL);
                        }

                        @Override // miuix.animation.listener.TransitionListener
                        public void onComplete(Object obj) {
                            AiFloatingWindow.this.mLoadingEndHeight = AiFloatingWindow.this.mGeneratingView.getHeight();
                            MiuiFolme.cleanFolme(AiFloatingWindow.this.mGeneratingView);
                            AiFloatingWindow.this.setTouchableRegion();
                            AiFloatingWindow.this.onViewPositionChanged(ViewType.PANEL);
                            AiFloatingWindow.this.onPanelChangeEnd();
                            AiFloatingWindow.this.inHighAnim = false;
                            Logger.INSTANCE.d(AiFloatingWindow.TAG, "anim onComplete: ");
                        }

                        @Override // miuix.animation.listener.TransitionListener
                        public void onUpdate(Object obj, Collection<UpdateInfo> collection) {
                            super.onUpdate(obj, collection);
                            AiFloatingWindow.this.onViewPositionChanged(ViewType.PANEL);
                        }

                        @Override // miuix.animation.listener.TransitionListener
                        public void onCancel(Object obj) {
                            super.onCancel(obj);
                            AiFloatingWindow.this.inHighAnim = false;
                        }
                    });
                    Folme.useAt(AiFloatingWindow.this.mGeneratingView).state().fromTo(animStateAdd, animStateAdd2, ease);
                } catch (Exception e) {
                    Logger.INSTANCE.e(AiFloatingWindow.TAG, "e : " + e);
                }
            }
        }, 0L);
        return true;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void resetGenerateWindowSize() {
        Logger.INSTANCE.d(TAG, "resetGenerateWindowSize， isAiErrorImageShow:" + isAiErrorImageShow);
        Handler handler = this.mHandler;
        if (handler != null) {
            handler.post(new Runnable() { // from class: com.miui.notes.ai.floatingwindow.AiFloatingWindow$$ExternalSyntheticLambda20
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.lambda$resetGenerateWindowSize$25();
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$resetGenerateWindowSize$25() {
        int dimension;
        Logger.INSTANCE.d(TAG, "resetGenerateWindowSize isAiErrorImageShow:" + isAiErrorImageShow);
        if (isAiErrorImageShow) {
            dimension = (int) this.mContext.getResources().getDimension(R.dimen.ai_window_webview_height);
            isAiErrorImageShow = false;
        } else {
            WebView webView = this.mAiWebView;
            if (webView != null && webView.getVisibility() == 0) {
                dimension = this.mFloatingContentView.getHeight() - this.mTitleLayout.getHeight();
            } else {
                dimension = (int) this.mContext.getResources().getDimension(R.dimen.ai_window_no_webview_height);
            }
        }
        RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) this.mGeneratingView.getLayoutParams();
        layoutParams.height = dimension;
        this.mGeneratingView.setLayoutParams(layoutParams);
        this.mLoadingEndHeight = this.mGeneratingView.getHeight();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void onViewPositionChanged(ViewType viewType) {
        if (Build.VERSION.SDK_INT < 33 || !this.mIsApplyShaderEffect) {
            Logger.INSTANCE.w(TAG, "onViewPositionChanged, unsupported because sdk version >> " + Build.VERSION.SDK_INT + " is lower than 33 or not enabled: " + this.mIsApplyShaderEffect);
            return;
        }
        int i = AnonymousClass36.$SwitchMap$com$miui$notes$ai$ui$material$ViewType[viewType.ordinal()];
        if (i != 1) {
            if (i != 2) {
                return;
            }
            onShowSoftInputShaderEffect();
            setTouchableRegion();
            return;
        }
        float translationY = this.mFloatingView.getTranslationY();
        this.mAiShaderEffectRenderer.setUiProgress(1.0f - (translationY / this.mFloatingView.getHeight()));
        this.mAiShaderEffectRenderer.setBgProgress(1.0f - (translationY / this.mFloatingView.getHeight()));
        this.mShaderState = 1;
    }

    /* JADX INFO: renamed from: com.miui.notes.ai.floatingwindow.AiFloatingWindow$36, reason: invalid class name */
    static /* synthetic */ class AnonymousClass36 {
        static final /* synthetic */ int[] $SwitchMap$com$miui$notes$ai$data$input$RequestType;
        static final /* synthetic */ int[] $SwitchMap$com$miui$notes$ai$network$RequestState;
        static final /* synthetic */ int[] $SwitchMap$com$miui$notes$ai$ui$material$ViewType;

        static {
            int[] iArr = new int[ViewType.values().length];
            $SwitchMap$com$miui$notes$ai$ui$material$ViewType = iArr;
            try {
                iArr[ViewType.PANEL.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$com$miui$notes$ai$ui$material$ViewType[ViewType.RECOMMEND_INPUT.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            int[] iArr2 = new int[RequestType.values().length];
            $SwitchMap$com$miui$notes$ai$data$input$RequestType = iArr2;
            try {
                iArr2[RequestType.TYPE_RECOMMEND.ordinal()] = 1;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                $SwitchMap$com$miui$notes$ai$data$input$RequestType[RequestType.TYPE_INPUT.ordinal()] = 2;
            } catch (NoSuchFieldError unused4) {
            }
            try {
                $SwitchMap$com$miui$notes$ai$data$input$RequestType[RequestType.TYPE_SUMMARY.ordinal()] = 3;
            } catch (NoSuchFieldError unused5) {
            }
            try {
                $SwitchMap$com$miui$notes$ai$data$input$RequestType[RequestType.TYPE_EXTENSION.ordinal()] = 4;
            } catch (NoSuchFieldError unused6) {
            }
            try {
                $SwitchMap$com$miui$notes$ai$data$input$RequestType[RequestType.TYPE_CHECK.ordinal()] = 5;
            } catch (NoSuchFieldError unused7) {
            }
            try {
                $SwitchMap$com$miui$notes$ai$data$input$RequestType[RequestType.TYPE_POLISH.ordinal()] = 6;
            } catch (NoSuchFieldError unused8) {
            }
            int[] iArr3 = new int[RequestState.values().length];
            $SwitchMap$com$miui$notes$ai$network$RequestState = iArr3;
            try {
                iArr3[RequestState.STATE_RECOMMEND.ordinal()] = 1;
            } catch (NoSuchFieldError unused9) {
            }
            try {
                $SwitchMap$com$miui$notes$ai$network$RequestState[RequestState.STATE_PANEL.ordinal()] = 2;
            } catch (NoSuchFieldError unused10) {
            }
            try {
                $SwitchMap$com$miui$notes$ai$network$RequestState[RequestState.STATE_LOADING.ordinal()] = 3;
            } catch (NoSuchFieldError unused11) {
            }
            try {
                $SwitchMap$com$miui$notes$ai$network$RequestState[RequestState.STATE_COMPLETE.ordinal()] = 4;
            } catch (NoSuchFieldError unused12) {
            }
            try {
                $SwitchMap$com$miui$notes$ai$network$RequestState[RequestState.STATE_ERROR.ordinal()] = 5;
            } catch (NoSuchFieldError unused13) {
            }
            try {
                $SwitchMap$com$miui$notes$ai$network$RequestState[RequestState.STATE_INPUT.ordinal()] = 6;
            } catch (NoSuchFieldError unused14) {
            }
        }
    }

    protected Rect onPanelChangeEnd() {
        int[] iArr = new int[2];
        Rect rect = new Rect();
        this.mFloatingView.getLocationInWindow(iArr);
        this.mFloatingView.getLocalVisibleRect(rect);
        rect.offset(iArr[0], iArr[1]);
        return rect;
    }

    protected void showToast(String str) {
        if (this.mAiToast == null) {
            this.mAiToast = new AiToast(this.mContext, this.mRootView);
        }
        this.mAiToast.showToast(str, 2000L, this.mImeHeight);
    }

    private void releaseToast() {
        AiToast aiToast = this.mAiToast;
        if (aiToast != null) {
            aiToast.release();
            this.mAiToast = null;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX INFO: renamed from: resetFloatingWindowSize, reason: merged with bridge method [inline-methods] */
    public void lambda$setOnScreenChange$23(boolean z) {
        final RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) this.mFloatingView.getLayoutParams();
        final RelativeLayout.LayoutParams layoutParams2 = (RelativeLayout.LayoutParams) this.mInputLayout.getLayoutParams();
        if (RomUtils.isPhoneDevice()) {
            handlePhoneDevice(layoutParams, z);
        } else if (RomUtils.isPadDevice()) {
            handlePadDevice(layoutParams, layoutParams2);
        } else if (RomUtils.isFoldDevice()) {
            if (this.smallWindowRect == null) {
                handleFoldDevice(layoutParams, layoutParams2);
            } else if (ScreenPosistionUtils.isInNarrowScreen(this.mContext)) {
                this.mHandler.post(new Runnable() { // from class: com.miui.notes.ai.floatingwindow.AiFloatingWindow$$ExternalSyntheticLambda18
                    @Override // java.lang.Runnable
                    public final void run() {
                        this.f$0.lambda$resetFloatingWindowSize$26(layoutParams, layoutParams2);
                    }
                });
            } else if (this.mRequestState != RequestState.STATE_RECOMMEND) {
                alignFoldCenterForSmallRect(layoutParams, layoutParams2);
            }
        }
        this.mFloatingView.setLayoutParams(layoutParams);
        if (this.mRequestState != RequestState.STATE_RECOMMEND) {
            this.mInputLayout.setLayoutParams(layoutParams2);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$resetFloatingWindowSize$26(RelativeLayout.LayoutParams layoutParams, RelativeLayout.LayoutParams layoutParams2) {
        layoutParams.leftMargin = 0;
        handleFoldDevice(layoutParams, layoutParams2);
    }

    private void handlePhoneDevice(RelativeLayout.LayoutParams layoutParams, boolean z) {
        if (this.smallWindowRect != null) {
            layoutParams.height = z ? this.mContext.getResources().getDimensionPixelSize(R.dimen.ai_floating_window_width) : -2;
            return;
        }
        if (z) {
            if (this.mRequestState == RequestState.STATE_INPUT) {
                Logger.INSTANCE.i(TAG, " do nothing");
            }
            layoutParams.addRule(12, 0);
            layoutParams.bottomMargin = this.mContext.getResources().getDimensionPixelSize(R.dimen.ai_floating_window_margin_bottom);
            layoutParams.topMargin = this.mContext.getResources().getDimensionPixelSize(R.dimen.ai_floating_window_margin_top);
            layoutParams.addRule(12);
            ConstraintLayout.LayoutParams layoutParams2 = (ConstraintLayout.LayoutParams) this.mFloatingContentView.getLayoutParams();
            layoutParams2.bottomMargin = this.mContext.getResources().getDimensionPixelSize(R.dimen.ai_floating_window_margin_bottom);
            layoutParams2.topMargin = this.mContext.getResources().getDimensionPixelSize(R.dimen.ai_floating_window_margin_top);
            this.mFloatingContentView.setLayoutParams(layoutParams2);
            return;
        }
        if (this.mRequestState == RequestState.STATE_INPUT) {
            Logger.INSTANCE.i(TAG, " do nothing");
        }
        layoutParams.addRule(12);
        layoutParams.addRule(13, 0);
        if (DisplayUtil.hasNavigationBar(this.mContext)) {
            layoutParams.bottomMargin = DisplayUtil.getNavigationBarHeightFromProp(this.mContext);
        } else {
            layoutParams.bottomMargin = this.mContext.getResources().getDimensionPixelSize(R.dimen.ai_floating_window_margin_bottom);
        }
        ConstraintLayout.LayoutParams layoutParams3 = (ConstraintLayout.LayoutParams) this.mFloatingContentView.getLayoutParams();
        layoutParams3.bottomMargin = this.mContext.getResources().getDimensionPixelSize(R.dimen.ai_floating_window_margin_bottom);
        this.mFloatingContentView.setLayoutParams(layoutParams3);
    }

    private void handlePadDevice(RelativeLayout.LayoutParams layoutParams, RelativeLayout.LayoutParams layoutParams2) {
        if (this.mWindowPosition == ScreenPosistionUtils.Position.MIDDLE) {
            if (this.smallWindowRect == null) {
                Logger.INSTANCE.e(TAG, "Rect: Rect get fail.");
                return;
            } else {
                alignPadCenter(layoutParams, layoutParams2);
                return;
            }
        }
        alignPadSide(layoutParams, layoutParams2);
    }

    private void handleFoldDevice(RelativeLayout.LayoutParams layoutParams, RelativeLayout.LayoutParams layoutParams2) {
        if (ScreenPosistionUtils.isInNarrowScreen(this.mContext)) {
            alignFoldCenter(layoutParams, layoutParams2, DisplayUtil.isLandScape(this.mContext), true);
            return;
        }
        if (this.mWindowPosition == ScreenPosistionUtils.Position.MIDDLE) {
            alignFoldCenter(layoutParams, layoutParams2, DisplayUtil.isLandScape(this.mContext), false);
        } else if (this.mWindowPosition == ScreenPosistionUtils.Position.LEFT || this.mWindowPosition == ScreenPosistionUtils.Position.RIGHT) {
            alignFoldSide(layoutParams, layoutParams2);
        }
    }

    private void alignFoldCenterForSmallRect(RelativeLayout.LayoutParams layoutParams, RelativeLayout.LayoutParams layoutParams2) {
        if (this.mRequestState != RequestState.STATE_RECOMMEND) {
            layoutParams2.addRule(14, 0);
        }
        layoutParams.addRule(14, 0);
        int i = DisplayUtil.getScreenMetrics(this.mContext).widthPixels;
        int i2 = this.smallWindowRect.right - this.smallWindowRect.left;
        int dimensionPixelSize = this.mContext.getResources().getDimensionPixelSize(R.dimen.ai_floating_window_width);
        int iCalculateLeftMargin = calculateLeftMargin((this.smallWindowRect.left + (i2 / 2)) - (dimensionPixelSize / 2), dimensionPixelSize, i);
        layoutParams.leftMargin = iCalculateLeftMargin;
        if (this.mRequestState != RequestState.STATE_RECOMMEND) {
            layoutParams2.leftMargin = iCalculateLeftMargin;
        }
    }

    private void alignFoldCenter(RelativeLayout.LayoutParams layoutParams, RelativeLayout.LayoutParams layoutParams2, boolean z, boolean z2) {
        if (this.mRequestState != RequestState.STATE_RECOMMEND) {
            layoutParams2.addRule(9, 0);
            layoutParams2.addRule(11, 0);
            layoutParams2.addRule(14);
        }
        layoutParams.addRule(9, 0);
        layoutParams.addRule(11, 0);
        layoutParams.addRule(14);
        if (z2 && z) {
            layoutParams.bottomMargin = this.mContext.getResources().getDimensionPixelSize(R.dimen.ai_floating_window_margin_bottom);
            ConstraintLayout.LayoutParams layoutParams3 = (ConstraintLayout.LayoutParams) this.mFloatingContentView.getLayoutParams();
            layoutParams3.bottomMargin = 0;
            this.mFloatingContentView.setLayoutParams(layoutParams3);
            return;
        }
        if (DisplayUtil.hasNavigationBar(this.mContext)) {
            layoutParams.bottomMargin = DisplayUtil.getNavigationBarHeightFromProp(this.mContext);
        } else {
            layoutParams.bottomMargin = this.mContext.getResources().getDimensionPixelSize(R.dimen.ai_floating_window_margin_bottom);
        }
        ConstraintLayout.LayoutParams layoutParams4 = (ConstraintLayout.LayoutParams) this.mFloatingContentView.getLayoutParams();
        layoutParams4.bottomMargin = this.mContext.getResources().getDimensionPixelSize(R.dimen.ai_floating_window_margin_bottom);
        this.mFloatingContentView.setLayoutParams(layoutParams4);
    }

    private void alignFoldSide(RelativeLayout.LayoutParams layoutParams, RelativeLayout.LayoutParams layoutParams2) {
        if (this.mWindowPosition == ScreenPosistionUtils.Position.LEFT) {
            if (this.mRequestState != RequestState.STATE_RECOMMEND) {
                layoutParams2.addRule(9);
                layoutParams2.leftMargin = this.mContext.getResources().getDimensionPixelSize(R.dimen.fold_input_margin_horizontal);
            }
            layoutParams.addRule(9);
            layoutParams.leftMargin = this.mContext.getResources().getDimensionPixelSize(R.dimen.fold_window_margin_horizontal);
            return;
        }
        if (this.mWindowPosition == ScreenPosistionUtils.Position.RIGHT) {
            if (this.mRequestState != RequestState.STATE_RECOMMEND) {
                layoutParams2.addRule(11);
                layoutParams2.rightMargin = this.mContext.getResources().getDimensionPixelSize(R.dimen.fold_input_margin_horizontal);
            }
            layoutParams.addRule(11);
            layoutParams.rightMargin = this.mContext.getResources().getDimensionPixelSize(R.dimen.fold_window_margin_horizontal);
        }
    }

    private void alignPadCenter(RelativeLayout.LayoutParams layoutParams, RelativeLayout.LayoutParams layoutParams2) {
        if (this.mRequestState != RequestState.STATE_RECOMMEND) {
            layoutParams2.addRule(14, 0);
        }
        layoutParams.addRule(14, 0);
        int i = DisplayUtil.getScreenMetrics(this.mContext).widthPixels;
        int i2 = this.smallWindowRect.right - this.smallWindowRect.left;
        int dimensionPixelSize = this.mContext.getResources().getDimensionPixelSize(R.dimen.ai_floating_window_width);
        int iCalculateLeftMargin = calculateLeftMargin((this.smallWindowRect.left + (i2 / 2)) - (dimensionPixelSize / 2), dimensionPixelSize, i);
        layoutParams.leftMargin = iCalculateLeftMargin;
        if (this.mRequestState != RequestState.STATE_RECOMMEND) {
            layoutParams2.leftMargin = iCalculateLeftMargin;
        }
    }

    private int calculateLeftMargin(int i, int i2, int i3) {
        if (i >= 0 && i + i2 <= i3) {
            return i;
        }
        if (i < 0) {
            return this.smallWindowRect.left;
        }
        return this.smallWindowRect.right - i2;
    }

    private void alignPadSide(RelativeLayout.LayoutParams layoutParams, RelativeLayout.LayoutParams layoutParams2) {
        if (this.mWindowPosition == ScreenPosistionUtils.Position.LEFT) {
            if (this.mRequestState != RequestState.STATE_RECOMMEND) {
                layoutParams2.addRule(9);
                layoutParams2.leftMargin = this.mContext.getResources().getDimensionPixelSize(R.dimen.pad_input_margin_horizontal);
            }
            layoutParams.addRule(9);
            layoutParams.leftMargin = this.mContext.getResources().getDimensionPixelSize(R.dimen.pad_window_margin_horizontal);
            return;
        }
        if (this.mWindowPosition == ScreenPosistionUtils.Position.RIGHT) {
            if (this.mRequestState != RequestState.STATE_RECOMMEND) {
                layoutParams2.addRule(11);
                layoutParams2.rightMargin = this.mContext.getResources().getDimensionPixelSize(R.dimen.pad_input_margin_horizontal);
            }
            layoutParams.addRule(11);
            layoutParams.rightMargin = this.mContext.getResources().getDimensionPixelSize(R.dimen.pad_window_margin_horizontal);
        }
    }

    private void releaseShaderRenderer() {
        FrameUpdateMonitor.INSTANCE.stopMonitor();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void increaseHeights() {
        int height = this.mAiSummary.getHeight();
        int height2 = this.mAiCheck.getHeight();
        int height3 = this.mAiExtension.getHeight();
        if (height2 > height) {
            height = height2;
        }
        if (height3 <= height) {
            height3 = height;
        }
        ViewGroup.LayoutParams layoutParams = this.mAiSummary.getLayoutParams();
        ViewGroup.LayoutParams layoutParams2 = this.mAiCheck.getLayoutParams();
        ViewGroup.LayoutParams layoutParams3 = this.mAiExtension.getLayoutParams();
        layoutParams.height = height3;
        layoutParams2.height = height3;
        layoutParams3.height = height3;
        this.mAiSummary.setLayoutParams(layoutParams);
        this.mAiCheck.setLayoutParams(layoutParams2);
        this.mAiExtension.setLayoutParams(layoutParams3);
    }

    private boolean inputBoxCommandWordLimit(EditText editText) {
        int trimmedLength = TextUtils.getTrimmedLength(editText.getText().toString());
        int i = this.mMaxInputNumber;
        if (trimmedLength > i) {
            this.isExceedNumberLimit = true;
            showToast(com.miui.notes.ai.utils.StringUtils.obtainLocalInteger(i, R.plurals.ai_cannot_useAI_toast_text, this.mContext));
        } else {
            this.isExceedNumberLimit = false;
        }
        return this.isExceedNumberLimit;
    }

    private void initAIView() {
        Logger.INSTANCE.d(TAG, "initAIView");
        ViewStub viewStub = (ViewStub) this.mRootView.findViewById(R.id.recommend_panel_stub_layout);
        this.mRecommendPanelStub = viewStub;
        View viewInflate = viewStub.inflate();
        this.mRecommendInflate = viewInflate;
        this.mRecommendDeepSeekButton = viewInflate.findViewById(R.id.ai_recommend_DeepSeek_container);
        this.mRecommendSendIcon = this.mRecommendInflate.findViewById(R.id.ai_recommend_send_icon);
        this.mAiImage = this.mRecommendInflate.findViewById(R.id.ai_recommend_icon_image);
        Folme.useAt(this.mRecommendDeepSeekButton).touch().setTint(0.0f, 0.0f, 0.0f, 0.0f).setScale(0.95f, ITouchStyle.TouchType.DOWN).handleTouchOf(this.mRecommendDeepSeekButton, new AnimConfig[0]);
        this.mRecommendDeepSeekButtonText = (TextView) this.mRecommendInflate.findViewById(R.id.ai_recommend_button_text);
        if (PreferenceUtils.getAiModelSwitch(this.mContext)) {
            this.mRecommendDeepSeekButton.setSelected(true);
            this.mRecommendDeepSeekButtonText.setTextColor(this.mContext.getResources().getColor(R.color.ai_deepseek_button_text_color));
        } else {
            this.mRecommendDeepSeekButton.setSelected(false);
            this.mRecommendDeepSeekButtonText.setTextColor(this.mContext.getResources().getColor(R.color.ai_deepseek_button_unchecked_text));
        }
        this.mRecommendView = this.mRecommendInflate.findViewById(R.id.recommend_icon_layout);
        EditText editText = (EditText) this.mRecommendInflate.findViewById(R.id.ai_recommend_edit);
        this.mRecommendEdit = editText;
        editText.setImeOptions(33554432);
        this.mRecommendInput = this.mRecommendInflate.findViewById(R.id.input_recommend_layout);
        this.mRecommendItems = new AiRecommendItem[]{(AiRecommendItem) this.mRecommendInflate.findViewById(R.id.ai_recommend_0), (AiRecommendItem) this.mRecommendInflate.findViewById(R.id.ai_recommend_1), (AiRecommendItem) this.mRecommendInflate.findViewById(R.id.ai_recommend_2)};
        this.mRecommendContainers = new View[]{this.mRecommendInflate.findViewById(R.id.container_recommend_0), this.mRecommendInflate.findViewById(R.id.container_recommend_1), this.mRecommendInflate.findViewById(R.id.container_recommend_2)};
        this.mRecommendInflate.findViewById(R.id.ai_recommend_send_icon).setOnClickListener(this);
        this.mRecommendInputRimEffectView = this.mRecommendInflate.findViewById(R.id.recommend_input_rim_effect_view);
        this.mRecommendInputContentView = this.mRecommendInflate.findViewById(R.id.recommend_input_content_view);
        this.mRecommendSendIconImage = (ImageView) this.mRecommendInflate.findViewById(R.id.ai_recommend_send_icon_image);
        if (this.mIsSupportCot) {
            this.mAiImage.setVisibility(8);
            this.mRecommendDeepSeekButton.setVisibility(0);
            RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) this.mRecommendSendIcon.getLayoutParams();
            layoutParams.addRule(3, R.id.ai_recommend_edit);
            layoutParams.removeRule(15);
            layoutParams.setMarginEnd(DisplayUtil.dip2px(this.mContext, 10.0f));
            this.mRecommendSendIcon.setLayoutParams(layoutParams);
            RelativeLayout.LayoutParams layoutParams2 = (RelativeLayout.LayoutParams) this.mRecommendEdit.getLayoutParams();
            layoutParams2.removeRule(16);
            layoutParams2.setMarginStart(0);
            layoutParams2.setMarginEnd(0);
            this.mRecommendEdit.setLayoutParams(layoutParams2);
            this.mRecommendEdit.setMaxHeight(DisplayUtil.dip2px(this.mContext, 113.0f));
            EditText editText2 = this.mRecommendEdit;
            int i = this.cotInputwidth;
            editText2.setPadding(i, i, i, i);
            this.mRecommendDeepSeekButton.setSelected(PreferenceUtils.getAiModelSwitch(this.mContext));
            this.mRecommendDeepSeekButton.setOnClickListener(this);
        } else {
            this.mRecommendEdit.setMaxHeight(DisplayUtil.dip2px(this.mContext, 104.0f));
            this.mRecommendEdit.setPadding(0, DisplayUtil.dip2px(this.mContext, 10.0f), 0, DisplayUtil.dip2px(this.mContext, 10.0f));
        }
        if (KeyboardListenerHelper.isHardwareKeyboard(this.mContext)) {
            this.mRecommendEdit.setImeOptions(4);
            this.mRecommendEdit.setOnKeyListener(new View.OnKeyListener() { // from class: com.miui.notes.ai.floatingwindow.AiFloatingWindow$$ExternalSyntheticLambda12
                @Override // android.view.View.OnKeyListener
                public final boolean onKey(View view, int i2, KeyEvent keyEvent) {
                    return this.f$0.lambda$initAIView$27(view, i2, keyEvent);
                }
            });
        }
        if (DisplayUtil.hasNavigationBar(this.mContext)) {
            RelativeLayout.LayoutParams layoutParams3 = (RelativeLayout.LayoutParams) this.mRecommendView.getLayoutParams();
            layoutParams3.bottomMargin = DisplayUtil.getNavigationBarHeightFromProp(this.mContext);
            this.mRecommendView.setLayoutParams(layoutParams3);
        }
        this.mRecommendEdit.addTextChangedListener(new TextWatcher() { // from class: com.miui.notes.ai.floatingwindow.AiFloatingWindow.35
            @Override // android.text.TextWatcher
            public void beforeTextChanged(CharSequence charSequence, int i2, int i3, int i4) {
            }

            @Override // android.text.TextWatcher
            public void onTextChanged(CharSequence charSequence, int i2, int i3, int i4) {
            }

            @Override // android.text.TextWatcher
            public void afterTextChanged(Editable editable) {
                if (AiFloatingWindow.this.mRecommendEdit.getText().toString().isEmpty()) {
                    AiFloatingWindow.this.mRecommendSendIconImage.setImageDrawable(AiFloatingWindow.this.mContext.getDrawable(R.drawable.ai_input_send_icon));
                } else {
                    AiFloatingWindow.this.mRecommendSendIconImage.setImageDrawable(AiFloatingWindow.this.mContext.getDrawable(R.drawable.ai_input_send_normalcy_icon));
                }
            }
        });
        TextExtKt.setEditProperty(this.mRecommendEdit, "400");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ boolean lambda$initAIView$27(View view, int i, KeyEvent keyEvent) {
        if (keyEvent.getAction() != 0 || i != 66) {
            return false;
        }
        if (keyEvent.isAltPressed()) {
            this.mRecommendEdit.append(StringUtils.LF);
            return true;
        }
        if (this.mRequestState != RequestState.STATE_RECOMMEND) {
            return false;
        }
        if (!TextUtils.isEmpty(this.mRecommendEdit.getText().toString())) {
            if (inputBoxCommandWordLimit(this.mRecommendEdit)) {
                return false;
            }
            clearEditorFlowState();
            UserInputHolder.getInstance().setEditInput(this.mRecommendEdit.getText().toString());
            UserInputHolder.getInstance().setRequestType(RequestType.TYPE_RECOMMEND);
            SoftInputUtil.hideSoftInput(this.mContext, this.mRecommendEdit);
            recommendSendAnim();
        }
        return true;
    }

    private void initEditView() {
        Logger.INSTANCE.d(TAG, "initAIView");
        if (this.mHasEditInit) {
            Logger.INSTANCE.i(TAG, "initAIView,already init!!!!");
            return;
        }
        this.mAIInputDeepSeekButton = this.mRootView.findViewById(R.id.ai_input_DeepSeek_button_container);
        View viewFindViewById = this.mRootView.findViewById(R.id.ai_input_send_icon_container);
        this.mInputSendView = viewFindViewById;
        if (this.mIsSupportCot) {
            RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) viewFindViewById.getLayoutParams();
            layoutParams.addRule(3, R.id.input);
            layoutParams.setMarginEnd(DisplayUtil.dip2px(this.mContext, 10.0f));
            layoutParams.removeRule(15);
            this.mInputSendView.setLayoutParams(layoutParams);
            RelativeLayout.LayoutParams layoutParams2 = (RelativeLayout.LayoutParams) this.mInput.getLayoutParams();
            layoutParams2.removeRule(16);
            layoutParams2.setMarginEnd(0);
            this.mInput.setLayoutParams(layoutParams2);
            this.mAIInputDeepSeekButton.setSelected(this.misAiDeepSeekSelected);
        } else {
            this.mInput.setPadding(0, DisplayUtil.dip2px(this.mContext, 10.0f), 0, DisplayUtil.dip2px(this.mContext, 10.0f));
        }
        this.mFlagView = this.mRootView.findViewById(R.id.flag_view);
        this.mTitleLayout = this.mRootView.findViewById(R.id.ai_text_widget_actionbar);
        this.mInputContentView = this.mRootView.findViewById(R.id.input_content_view);
        ViewStub viewStub = (ViewStub) this.mRootView.findViewById(R.id.stub_panel_layout);
        this.mPanelStub = viewStub;
        View viewInflate = viewStub.inflate();
        this.mPanelInflate = viewInflate;
        this.mPanelView = viewInflate.findViewById(R.id.panel);
        this.mAiSummary = (AiWidgetTabItem) this.mPanelInflate.findViewById(R.id.ai_summary);
        this.mAiSummaryContainer = (MaterialEffectContainer) this.mPanelInflate.findViewById(R.id.ai_summary_material_effect);
        this.mAiCheckContainer = (MaterialEffectContainer) this.mPanelInflate.findViewById(R.id.ai_check_material_effect);
        this.mAiExtensionContainer = (MaterialEffectContainer) this.mPanelInflate.findViewById(R.id.ai_extension_material_effect);
        this.mAiSummaryContainer.setAccessibilityTraversalBefore(R.id.ai_check_material_effect);
        this.mAiCheck = (AiWidgetTabItem) this.mPanelInflate.findViewById(R.id.ai_check);
        this.mAiCheckContainer.setAccessibilityTraversalBefore(R.id.ai_extension_material_effect);
        this.mAiExtension = (AiWidgetTabItem) this.mPanelInflate.findViewById(R.id.ai_extension);
        this.mPolishTitle = (TextView) this.mPanelInflate.findViewById(R.id.polish_title);
        this.mInputRimEffectView = this.mRootView.findViewById(R.id.input_rim_effect_view);
        this.mGeneral = this.mPanelInflate.findViewById(R.id.ai_general);
        this.mProfessional = this.mPanelInflate.findViewById(R.id.ai_professional);
        this.mEasy = this.mPanelInflate.findViewById(R.id.ai_easy);
        this.mFriendly = this.mPanelInflate.findViewById(R.id.ai_friendly);
        AccessibilityDelegateUtil.setButtonAccessibilityDelegate(this.mAiSummary);
        AccessibilityDelegateUtil.setButtonAccessibilityDelegate(this.mAiCheck);
        AccessibilityDelegateUtil.setButtonAccessibilityDelegate(this.mAiExtension);
        AccessibilityDelegateUtil.setButtonAccessibilityDelegate(this.mGeneral);
        AccessibilityDelegateUtil.setButtonAccessibilityDelegate(this.mProfessional);
        AccessibilityDelegateUtil.setButtonAccessibilityDelegate(this.mEasy);
        AccessibilityDelegateUtil.setButtonAccessibilityDelegate(this.mFriendly);
        TextExtKt.setTextProperty(this.mPolishTitle, Constant.POLISH_TITLE_TEXT_WGHT);
        if (!this.mCloudIsDevice && !this.mIsSupportCot) {
            RelativeLayout.LayoutParams layoutParams3 = (RelativeLayout.LayoutParams) this.mPanelInflate.getLayoutParams();
            layoutParams3.topMargin = (int) this.mContext.getResources().getDimension(R.dimen.ai_tab_margin_start_end);
            this.mPanelInflate.setLayoutParams(layoutParams3);
        }
        if (LocaleUtils.isChineseLocale()) {
            ((AiWidgetTabItem) this.mGeneral).viewTouch().setContentDescription(this.mContext.getString(R.string.ai_text_widget_general));
            ((AiWidgetTabItem) this.mGeneral).setOnScreenChangeListener(new AiWidgetTabItem.AiPolishListener() { // from class: com.miui.notes.ai.floatingwindow.AiFloatingWindow$$ExternalSyntheticLambda37
                @Override // com.miui.notes.ai.ui.AiWidgetTabItem.AiPolishListener
                public final void touchPolish(int i) {
                    this.f$0.lambda$initEditView$28(i);
                }
            });
            ((AiWidgetTabItem) this.mProfessional).viewTouch().setContentDescription(this.mContext.getString(R.string.ai_text_widget_professional));
            ((AiWidgetTabItem) this.mProfessional).setOnScreenChangeListener(new AiWidgetTabItem.AiPolishListener() { // from class: com.miui.notes.ai.floatingwindow.AiFloatingWindow$$ExternalSyntheticLambda38
                @Override // com.miui.notes.ai.ui.AiWidgetTabItem.AiPolishListener
                public final void touchPolish(int i) {
                    this.f$0.lambda$initEditView$29(i);
                }
            });
            ((AiWidgetTabItem) this.mEasy).viewTouch().setContentDescription(this.mContext.getString(R.string.ai_text_widget_easy));
            ((AiWidgetTabItem) this.mEasy).setOnScreenChangeListener(new AiWidgetTabItem.AiPolishListener() { // from class: com.miui.notes.ai.floatingwindow.AiFloatingWindow$$ExternalSyntheticLambda1
                @Override // com.miui.notes.ai.ui.AiWidgetTabItem.AiPolishListener
                public final void touchPolish(int i) {
                    this.f$0.lambda$initEditView$30(i);
                }
            });
            ((AiWidgetTabItem) this.mFriendly).viewTouch().setContentDescription(this.mContext.getString(R.string.ai_text_widget_friendly));
            ((AiWidgetTabItem) this.mFriendly).setOnScreenChangeListener(new AiWidgetTabItem.AiPolishListener() { // from class: com.miui.notes.ai.floatingwindow.AiFloatingWindow$$ExternalSyntheticLambda2
                @Override // com.miui.notes.ai.ui.AiWidgetTabItem.AiPolishListener
                public final void touchPolish(int i) {
                    this.f$0.lambda$initEditView$31(i);
                }
            });
            AccessibilityDelegateUtil.setButtonAccessibilityDelegate(((AiWidgetTabItem) this.mGeneral).viewTouch());
            AccessibilityDelegateUtil.setButtonAccessibilityDelegate(((AiWidgetTabItem) this.mProfessional).viewTouch());
            AccessibilityDelegateUtil.setButtonAccessibilityDelegate(((AiWidgetTabItem) this.mEasy).viewTouch());
            AccessibilityDelegateUtil.setButtonAccessibilityDelegate(((AiWidgetTabItem) this.mFriendly).viewTouch());
        } else {
            this.mPanelInflate.findViewById(R.id.ai_general).setOnClickListener(this);
            this.mPanelInflate.findViewById(R.id.ai_professional).setOnClickListener(this);
            this.mPanelInflate.findViewById(R.id.ai_easy).setOnClickListener(this);
            this.mPanelInflate.findViewById(R.id.ai_friendly).setOnClickListener(this);
            Folme.useAt(this.mRootView.findViewById(R.id.ai_general)).touch().setTint(0.0f, 0.0f, 0.0f, 0.0f).setScale(0.98f, ITouchStyle.TouchType.DOWN).handleTouchOf(this.mRootView.findViewById(R.id.ai_general), new AnimConfig[0]);
            Folme.useAt(this.mRootView.findViewById(R.id.ai_professional)).touch().setTint(0.0f, 0.0f, 0.0f, 0.0f).setScale(0.98f, ITouchStyle.TouchType.DOWN).handleTouchOf(this.mRootView.findViewById(R.id.ai_professional), new AnimConfig[0]);
            Folme.useAt(this.mRootView.findViewById(R.id.ai_easy)).touch().setTint(0.0f, 0.0f, 0.0f, 0.0f).setScale(0.98f, ITouchStyle.TouchType.DOWN).handleTouchOf(this.mRootView.findViewById(R.id.ai_easy), new AnimConfig[0]);
            Folme.useAt(this.mRootView.findViewById(R.id.ai_friendly)).touch().setTint(0.0f, 0.0f, 0.0f, 0.0f).setScale(0.98f, ITouchStyle.TouchType.DOWN).handleTouchOf(this.mRootView.findViewById(R.id.ai_friendly), new AnimConfig[0]);
        }
        this.mAiSummaryContainer.setOnClickListener(this);
        this.mAiCheckContainer.setOnClickListener(this);
        this.mAiExtensionContainer.setOnClickListener(this);
        Folme.useAt(this.mAIInputDeepSeekButton).touch().setTint(0.0f, 0.0f, 0.0f, 0.0f).setScale(0.95f, ITouchStyle.TouchType.DOWN).handleTouchOf(this.mAIInputDeepSeekButton, new AnimConfig[0]);
        Folme.useAt(this.mAiSummaryContainer).touch().setTint(0.0f, 0.0f, 0.0f, 0.0f).setScale(0.95f, ITouchStyle.TouchType.DOWN).handleTouchOf(this.mAiSummaryContainer, new AnimConfig[0]);
        Folme.useAt(this.mAiCheckContainer).touch().setTint(0.0f, 0.0f, 0.0f, 0.0f).setScale(0.95f, ITouchStyle.TouchType.DOWN).handleTouchOf(this.mAiCheckContainer, new AnimConfig[0]);
        Folme.useAt(this.mAiExtensionContainer).touch().setTint(0.0f, 0.0f, 0.0f, 0.0f).setScale(0.95f, ITouchStyle.TouchType.DOWN).handleTouchOf(this.mAiExtensionContainer, new AnimConfig[0]);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    /* JADX INFO: renamed from: onTouchPolish, reason: merged with bridge method [inline-methods] and merged with bridge method [inline-methods] and merged with bridge method [inline-methods] and merged with bridge method [inline-methods] */
    public void lambda$initEditView$31(int i) {
        if (this.isButtonClicked && !this.isClickReturned) {
            Logger.INSTANCE.i(TAG, " isButtonClicked is true just return onTouchPolish type is " + i);
            this.isClickReturned = true;
            return;
        }
        this.isClickReturned = false;
        this.isButtonClicked = true;
        this.mHandler.postDelayed(new Runnable() { // from class: com.miui.notes.ai.floatingwindow.AiFloatingWindow$$ExternalSyntheticLambda27
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.lambda$onTouchPolish$32();
            }
        }, 50L);
        this.mInput.clearFocus();
        UserInputHolder.getInstance().setRequestType(RequestType.TYPE_POLISH);
        if (i == 1) {
            this.mPolishStyle = PolishStyle.TYPE_COMMON;
            UserInputHolder.getInstance().setPolishStyle(PolishStyle.TYPE_COMMON);
        } else if (i == 2) {
            this.mPolishStyle = PolishStyle.TYPE_PROFESSIONAL;
            UserInputHolder.getInstance().setPolishStyle(PolishStyle.TYPE_PROFESSIONAL);
        } else if (i == 3) {
            this.mPolishStyle = PolishStyle.TYPE_EASY;
            UserInputHolder.getInstance().setPolishStyle(PolishStyle.TYPE_EASY);
        } else if (i == 4) {
            this.mPolishStyle = PolishStyle.TYPE_FRIENDLY;
            UserInputHolder.getInstance().setPolishStyle(PolishStyle.TYPE_FRIENDLY);
        }
        selectAllTextIfNecessary();
        setUiState(RequestState.STATE_LOADING);
        startRequestAiData();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onTouchPolish$32() {
        this.isButtonClicked = false;
    }

    protected void initWebView() {
        FloatingDecorView floatingDecorView;
        Logger.INSTANCE.d(TAG, "initWebView");
        if (this.mHasWebInit || (floatingDecorView = this.mRootView) == null) {
            Logger.INSTANCE.e(TAG, "root view is null or hasInitAlready" + this.mHasWebInit + "," + this.mRootView);
            return;
        }
        this.mGeneratingStub = (ViewStub) floatingDecorView.findViewById(R.id.ai_generating_stub_layout);
        this.mTitleLayout = this.mRootView.findViewById(R.id.ai_text_widget_actionbar);
        View viewInflate = this.mGeneratingStub.inflate();
        this.mGeneratingInflate = viewInflate;
        this.mAiWebView = (WebView) viewInflate.findViewById(R.id.ai_webview);
        this.mAiWaterMarkView = (AiWaterMarkView) this.mGeneratingInflate.findViewById(R.id.ai_water_mark_view);
        this.mGeneratingView = this.mGeneratingInflate.findViewById(R.id.generating_layout);
        this.mAiDoneTab = this.mGeneratingInflate.findViewById(R.id.ai_done_tab);
        this.mAiBack = this.mGeneratingInflate.findViewById(R.id.ai_back);
        this.mAiRefresh = this.mGeneratingInflate.findViewById(R.id.ai_refesh);
        this.mAiRefreshText = (AiButtomItem) this.mGeneratingInflate.findViewById(R.id.ai_refresh_text);
        this.mAiCopy = this.mGeneratingInflate.findViewById(R.id.ai_copy);
        this.mAiInsert = this.mGeneratingInflate.findViewById(R.id.ai_insert);
        this.mAiDone = this.mGeneratingInflate.findViewById(R.id.ai_done);
        this.mGeneratingStop = this.mGeneratingInflate.findViewById(R.id.container_generating_stop);
        this.mAiErrorImage = this.mGeneratingInflate.findViewById(R.id.ai_error_image);
        this.mAiBackText = (AiButtomItem) this.mGeneratingInflate.findViewById(R.id.ai_back_text);
        TextView textView = (TextView) this.mGeneratingInflate.findViewById(R.id.btn_generating_stop);
        this.mBtnGeneratingStop = textView;
        AccessibilityDelegateUtil.setButtonAccessibilityDelegate(textView);
        AccessibilityDelegateUtil.setButtonAccessibilityDelegate(this.mAiBack);
        AccessibilityDelegateUtil.setButtonAccessibilityDelegate(this.mAiRefresh);
        AccessibilityDelegateUtil.setButtonAccessibilityDelegate(this.mAiCopy);
        AccessibilityDelegateUtil.setButtonAccessibilityDelegate(this.mAiInsert);
        AccessibilityDelegateUtil.setButtonAccessibilityDelegate(this.mAiDone);
        TextExtKt.setTextProperty(this.mBtnGeneratingStop, Constant.STOP_TEXT_WGHT);
        this.mGeneratingInflate.findViewById(R.id.ai_back).setOnClickListener(this);
        this.mGeneratingInflate.findViewById(R.id.ai_refesh).setOnClickListener(this);
        this.mGeneratingInflate.findViewById(R.id.ai_copy).setOnClickListener(this);
        this.mGeneratingInflate.findViewById(R.id.ai_insert).setOnClickListener(this);
        this.mGeneratingInflate.findViewById(R.id.ai_done).setOnClickListener(this);
        this.mGeneratingInflate.findViewById(R.id.container_generating_stop).setOnClickListener(this);
        Folme.useAt(this.mGeneratingInflate.findViewById(R.id.ai_back)).touch().setTint(0.0f, 0.0f, 0.0f, 0.0f).setScale(0.95f, ITouchStyle.TouchType.DOWN).handleTouchOf(this.mGeneratingInflate.findViewById(R.id.ai_back), new AnimConfig[0]);
        Folme.useAt(this.mGeneratingInflate.findViewById(R.id.ai_refesh)).touch().setTint(0.0f, 0.0f, 0.0f, 0.0f).setScale(0.95f, ITouchStyle.TouchType.DOWN).handleTouchOf(this.mGeneratingInflate.findViewById(R.id.ai_refesh), new AnimConfig[0]);
        Folme.useAt(this.mGeneratingInflate.findViewById(R.id.ai_copy)).touch().setTint(0.0f, 0.0f, 0.0f, 0.0f).setScale(0.95f, ITouchStyle.TouchType.DOWN).handleTouchOf(this.mGeneratingInflate.findViewById(R.id.ai_copy), new AnimConfig[0]);
        Folme.useAt(this.mGeneratingInflate.findViewById(R.id.ai_insert)).touch().setTint(0.0f, 0.0f, 0.0f, 0.0f).setScale(0.95f, ITouchStyle.TouchType.DOWN).handleTouchOf(this.mGeneratingInflate.findViewById(R.id.ai_insert), new AnimConfig[0]);
        Folme.useAt(this.mGeneratingInflate.findViewById(R.id.ai_done)).touch().setTint(0.0f, 0.0f, 0.0f, 0.0f).setScale(0.95f, ITouchStyle.TouchType.DOWN).handleTouchOf(this.mRootView.findViewById(R.id.ai_done), new AnimConfig[0]);
        Folme.useAt(this.mGeneratingInflate.findViewById(R.id.container_generating_stop)).touch().setTint(0.0f, 0.0f, 0.0f, 0.0f).setScale(0.98f, ITouchStyle.TouchType.DOWN).handleTouchOf(this.mGeneratingInflate.findViewById(R.id.container_generating_stop), new AnimConfig[0]);
        initEditor();
        if (KeyboardListenerHelper.isHardwareKeyboard(this.mContext)) {
            this.mGeneratingStop.setOnKeyListener(new View.OnKeyListener() { // from class: com.miui.notes.ai.floatingwindow.AiFloatingWindow$$ExternalSyntheticLambda31
                @Override // android.view.View.OnKeyListener
                public final boolean onKey(View view, int i, KeyEvent keyEvent) {
                    return this.f$0.lambda$initWebView$33(view, i, keyEvent);
                }
            });
        }
        this.mHasWebInit = true;
        Logger.INSTANCE.d(TAG, "initWebView finished");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ boolean lambda$initWebView$33(View view, int i, KeyEvent keyEvent) {
        if (this.mRequestState != RequestState.STATE_LOADING && this.mRequestState != RequestState.STATE_ERROR) {
            return false;
        }
        if (i != 66 && i != 62) {
            return false;
        }
        Logger.INSTANCE.i(TAG, "拦截外接键盘，enter键空/格键触发view");
        return true;
    }

    protected void changeInit() {
        FloatingDecorView floatingDecorView;
        if (this.mHasChangeInit || (floatingDecorView = this.mRootView) == null) {
            return;
        }
        floatingDecorView.findViewById(R.id.ai_input_send_icon_container).setOnClickListener(this);
        this.mRootView.findViewById(R.id.ai_input_DeepSeek_button_container).setOnClickListener(this);
        this.mRootView.findViewById(R.id.close_button).setOnClickListener(this);
        this.mHasChangeInit = true;
    }

    private void setFlagViewHeight() {
        if (this.mCloudIsDevice || this.mIsSupportCot) {
            RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) this.mFlagView.getLayoutParams();
            this.mInputLayout.measure(0, 0);
            layoutParams.height = this.mInputLayout.getMeasuredHeight();
            View view = this.mFlagView;
            if (view != null) {
                view.setLayoutParams(layoutParams);
            }
        }
    }

    private void setRecommendPanelState(boolean z) {
        if (RomUtils.isPhoneDevice() && TextUtils.isEmpty(this.mSelectedText)) {
            cancelTouchableRegion();
            for (View view : this.mRecommendContainers) {
                view.setVisibility(!z ? 0 : 8);
            }
            setTouchableRegion();
        }
    }

    public boolean isLoading() {
        return this.mRequestState == RequestState.STATE_LOADING;
    }

    @Override // com.miui.notes.ai.local.LLMSearchListener
    public void onConnected() {
        Logger.INSTANCE.d(TAG, "LLM onConnected");
        this.mHandler.post(new Runnable() { // from class: com.miui.notes.ai.floatingwindow.AiFloatingWindow$$ExternalSyntheticLambda11
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.lambda$onConnected$34();
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onConnected$34() {
        AIDialogActivity.dismissProgressDialog();
        initAndShowFloatingWindow();
    }

    @Override // com.miui.notes.ai.local.LLMSearchListener
    public void onDisConnected() {
        Logger.INSTANCE.d(TAG, "LLM onDisConnected");
        this.mHandler.post(new Runnable() { // from class: com.miui.notes.ai.floatingwindow.AiFloatingWindow$$ExternalSyntheticLambda19
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.lambda$onDisConnected$35();
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onDisConnected$35() {
        stopGenerating("");
        if (this.mRequestState == RequestState.STATE_COMPLETE) {
            return;
        }
        this.mNetError = true;
        this.mRequestState = RequestState.STATE_ERROR;
        MiuiFolme.cleanFolme(this.mGeneratingView, this.mGeneratingStop, this.mPanelView, this.mInputRimEffectView, this.mInputContentView);
        setErrorCodeHandle(this.mContext.getString(R.string.ai_llm_dis_connect_error_message));
    }

    @Override // com.miui.notes.ai.local.LLMSearchListener
    public void onFailed(int i) {
        Logger.INSTANCE.d(TAG, "LLM onFailed");
        if (RomUtils.isSupportAiText(this.mContext)) {
            showLLMSearchDeviceFailDialog();
        } else if (this.mIsSupportLLMWriting) {
            showLLMSearchDeviceRetryDialog();
        }
    }

    private void startAIDialogActivity(int i) {
        Intent intent = new Intent(this.mContext, (Class<?>) AIDialogActivity.class);
        intent.putExtra(AIDialogActivity.EXTRA_DIALOG_TYPE, i);
        intent.addFlags(805306368);
        this.mContext.startActivity(intent);
    }

    private void showLLMSearchDeviceDialog() {
        this.mHandler.postDelayed(new Runnable() { // from class: com.miui.notes.ai.floatingwindow.AiFloatingWindow$$ExternalSyntheticLambda15
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.lambda$showLLMSearchDeviceDialog$36();
            }
        }, 100L);
        AIDialogActivity.setDialogCancelListener(this);
        startAIDialogActivity(1);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$showLLMSearchDeviceDialog$36() {
        Logger.INSTANCE.d(TAG, "floatingWindow showLLMSearchDeviceDialog");
        AINetWorkUtils aINetWorkUtils = this.mAINetWorkUtils;
        if (aINetWorkUtils != null) {
            aINetWorkUtils.searchDevice(this);
        }
    }

    private void showLLMSearchDeviceFailDialog() {
        AIDialogActivity.setDialogCloseListener(this);
        startAIDialogActivity(2);
    }

    private void showLLMSearchDeviceRetryDialog() {
        AIDialogActivity.setDialogRetryListener(this);
        startAIDialogActivity(3);
    }

    @Override // com.miui.notes.ai.share.AIDialogActivity.DialogCancelListener
    public void onCancelClicked() {
        AINetWorkUtils aINetWorkUtils = this.mAINetWorkUtils;
        if (aINetWorkUtils != null) {
            aINetWorkUtils.cancelSearchDevice();
        }
        detachFromWindow();
    }

    @Override // com.miui.notes.ai.share.AIDialogActivity.DialogRetryListener
    public void onRetryClicked() {
        showLLMSearchDeviceDialog();
    }

    @Override // com.miui.notes.ai.share.AIDialogActivity.DialogCloseListener
    public void onCloseClicked() {
        Intent intent = new Intent();
        intent.setClassName(NotesNormalConstants.AICR_PACKAGE, "com.xiaomi.aicr.dist.client.activity.DistComputeClientActivity");
        intent.addFlags(DataSpace.RANGE_LIMITED);
        this.mContext.startActivity(intent);
    }

    private int convertDpToPixels(float f, Context context) {
        return (int) TypedValue.applyDimension(1, f, context.getResources().getDisplayMetrics());
    }

    public void tick() {
        long jNanoTime = System.nanoTime();
        float f = (float) ((jNanoTime - this.mLastGlobalTime) * 1.0E-9d);
        this.mDeltaTime = f;
        this.mTime += f;
        this.mLastGlobalTime = jNanoTime;
    }

    public void resetTime() {
        this.mLastGlobalTime = System.nanoTime();
        this.mTime = 0.0f;
        this.mDeltaTime = 0.0f;
    }

    private String getContentStatus() {
        String str = this.mNoteTitle;
        String str2 = this.mNoteContent;
        if (str.isEmpty() && str2.isEmpty()) {
            return AITextWidgetStat.EMPTY_STATUS;
        }
        if (str.isEmpty()) {
            return AITextWidgetStat.NO_TITLE_CONTENT;
        }
        if (str2.isEmpty()) {
            return AITextWidgetStat.TITLE_NO_CONTENT;
        }
        return AITextWidgetStat.TITLE_AND_CONTENT;
    }

    private boolean isImeHeightChanged(int i) {
        return this.mImeHeight != i;
    }

    public void closeWindow() {
        Logger.INSTANCE.d(TAG, "closeWindow: ");
        detachFromWindow();
    }
}
