package com.miui.handwriting.common.view.toolbar;

import android.accounts.Account;
import android.app.Activity;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.RectF;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.graphics.drawable.TransitionDrawable;
import android.provider.Settings;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import androidx.core.content.ContextCompat;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import com.miui.creation.common.R;
import com.miui.handwriting.common.HandWritingViewApplication;
import com.miui.handwriting.common.ai.VerifyServer;
import com.miui.handwriting.common.ai.data.Result;
import com.miui.handwriting.common.ai.data.VersionData;
import com.miui.handwriting.common.data.bean.BackgroundWrapper;
import com.miui.handwriting.common.data.bean.MiuiDeleteProp;
import com.miui.handwriting.common.data.bean.PenBean;
import com.miui.handwriting.common.data.bean.ShapeEditBean;
import com.miui.handwriting.common.data.dataset.CanvasBgSet;
import com.miui.handwriting.common.data.dataset.HistorySet;
import com.miui.handwriting.common.data.dataset.PenSet;
import com.miui.handwriting.common.data.dataset.ShapeEditSet;
import com.miui.handwriting.common.listener.ToolBoxActionCallBack;
import com.miui.handwriting.common.model.IMiuiHandWritingModel;
import com.miui.handwriting.common.softinputhelper.KeyboardHelper;
import com.miui.handwriting.common.tools.DeviceUtils;
import com.miui.handwriting.common.tools.DisplayCutoutHolder;
import com.miui.handwriting.common.tools.GsonUtils;
import com.miui.handwriting.common.tools.HSLColor;
import com.miui.handwriting.common.tools.PreferenceUtils;
import com.miui.handwriting.common.tools.ScreenModeUtils;
import com.miui.handwriting.common.tools.UIUtils;
import com.miui.handwriting.common.tools.constants.Constants;
import com.miui.handwriting.common.view.check.ICheck;
import com.miui.handwriting.common.view.colorpicker.ColorSelectView;
import com.miui.handwriting.common.view.handview.BaseMiuiHandWritingView;
import com.miui.handwriting.common.view.handview.pop.AIPenPopupWindow;
import com.miui.handwriting.common.view.helper.AIEnterProcessor;
import com.miui.handwriting.common.view.popup.ColorPickerPopupWindow;
import com.miui.handwriting.common.view.popup.DropDownSingleChoiceMenu;
import com.miui.handwriting.common.view.popup.EraserPopupWindow;
import com.miui.handwriting.common.view.popup.MoreActionPopupWindow;
import com.miui.handwriting.common.view.popup.PenSetPopupWindow;
import com.miui.handwriting.common.view.popup.ShapeSelectPopupWindow;
import com.miui.handwriting.common.view.quickMenu.StylusPenQuickMenu;
import com.miui.handwriting.common.view.quickMenu.listener.OnQuickColorSelectUpdateListener;
import com.miui.handwriting.common.view.quickMenu.listener.OnQuickEraserUpdateListener;
import com.miui.handwriting.common.view.quickMenu.listener.OnQuickPenColorUpdateListener;
import com.miui.handwriting.common.view.quickMenu.listener.OnQuickPenSizeColorUpdateListener;
import com.miui.libhandwritingpage.listener.PreviewListener;
import com.miui.libhandwritingpage.newbean.beanPageInfo.PageItemData;
import com.miui.libhandwritingpage.newbean.beanPageInfo.PagePreviewData;
import com.miui.libhandwritingpage.utils.RomUtils;
import com.miui.libhandwritingpage.utils.Utils;
import com.miui.libhandwritingpage.view.PageInkView;
import com.miui.libhandwritingpage.view.PageItemView;
import defpackage.R2;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import miuix.animation.Folme;
import miuix.animation.base.AnimConfig;
import miuix.animation.controller.AnimState;
import miuix.animation.property.ViewProperty;
import miuix.appcompat.app.AlertDialog;
import miuix.autodensity.AutoDensityConfig;
import miuix.micloudview.accounts.ExtraAccountManager;
import xc.engine.api.ICreationEngine;
import xc.engine.api.PenEngine;
import xc.engine.core.graphics.engine.MiCurveEngine;
import xc.engine.core.graphics.pen.PenType;
import xc.engine.data.CurveProp;
import xc.engine.data.pagedata.DataCurve;
import xc.engine.data.shape.ShapeProp;
import xc.engine.math.iink.callback.MathSelectCallback;
import xc.engine.operate.canvas.track.delete.DeleteProp;
import xc.engine.operate.canvas.track.delete.DeleteType;
import xc.engine.utils.DisplayUtils;
import xc.engine.utils.PageSizeConfig;

/* JADX INFO: loaded from: classes3.dex */
public class MiuiToolBoxView extends RelativeLayout implements PageInkView.ShieldEventListener, IMiuiHandToolbar {
    public static final int AI_TYPE_ALL = 1;
    public static final int AI_TYPE_AREA = 0;
    private static final Set<Integer> BRUSH_SIZE_TYPES = new HashSet(Arrays.asList(1, 2, 3, 4, 10));
    private static final String TAG = "MiuiToolBoxView";
    private ToolBoxActionCallBack actionCallBack;
    private final AIPenPopupWindow.OnAIPenClickListener aiPenClickListener;
    private int aiType;
    private MiuiDeleteProp currentEraserPen;
    private boolean isAISelect;
    private boolean isAiBrush;
    protected boolean isEraser;
    private boolean isKeyBoardVisInSmallWindow;
    private boolean isLongPress;
    private boolean isPc;
    protected boolean isRuler;
    private boolean isSelect;
    private int lastPenIndex;
    private AIPenPopupWindow mAIPenPopWindow;
    private int mAction;
    private View mBrushBallpoint;
    private View mBrushContainer;
    private List<Integer> mBrushDisplaySequence;
    private View mBrushEraser;
    private View mBrushInk;
    private View mBrushMarkPen;
    private View mBrushPencil;
    private View mBrushSelect;
    private float mBrushTransY;
    private View mBrushWaterColor;
    private ColorSelectView mCenterColorView;
    private ICheck mCheck;
    private View mColorContainer;
    private ColorSelectView mColorView;
    private Context mContext;
    private View mCurrentMultiSelectToolView;
    private ColorSelectView mCurrentSelectColorView;
    private View mCurrentSelectToolView;
    private AlertDialog mDeletePageDialog;
    private DropDownSingleChoiceMenu mDropDownSingleChoiceMenu;
    private MoreActionPopupWindow mEditMoreActionPopupWindow;
    private EraserPopupWindow mEraserPopupWindow;
    private BaseMiuiHandWritingView mHandWritingImpl;
    private boolean mHasChangeColor;
    private androidx.appcompat.app.AlertDialog mHotKeyPopDialog;
    private View mLastSelectForRule;
    private View mLastSelectToolView;
    private ColorSelectView mLeftColorView;
    private boolean mLimitedToolbar;
    private View.OnKeyListener mOnKeyListener;
    private IMiuiHandWritingModel mPageInkViewModel;
    private AlertDialog mPageSizeDialog;
    private boolean mPageWindowShowing;
    private ColorPickerPopupWindow mPenColorPopupWindow;
    private PenSet mPenSet;
    private PenSet.PenSetChangeListener mPenSetChangeListener;
    private PenSetPopupWindow mPenSetPopupWindow;
    private PenSetPopupWindow mPenSizePopupWindow;
    private ColorSelectView mRightColorView;
    private View.OnClickListener mSaveBtnListener;
    private int mSavedSelectViewType;
    private View mScrollContainer;
    private ShapeSelectPopupWindow mShapeSelectPopupWindow;
    private View.OnClickListener mShareBtnListener;
    private StylusPenQuickMenu mStylusPenQuickMenu;
    private TextView mTextThumbnail;
    private View mToolAI;
    private ImageView mToolBack;
    private View mToolMore;
    private View mToolParent;
    private View mToolRectSelect;
    private View mToolRedo;
    private View mToolRuler;
    private ImageView mToolShare;
    private View mToolUndo;
    private MathSelectCallback mathSelectCallBack;
    private List<PagePreviewData> multiPreviewDataList;
    Map<PenType, Bitmap> srcBitmap;
    private int tempPenIndex;
    private final View.OnClickListener toolClickListener;
    private View.OnClickListener toolUndoRedoListener;

    private void setTouchEvent(int i) {
    }

    private void updateAIView(boolean z) {
    }

    private void updateAIView(boolean z, Configuration configuration) {
    }

    @Override // com.miui.handwriting.common.view.toolbar.IMiuiHandToolbar
    public void onPreviewItemUpdated(int i, PagePreviewData pagePreviewData) {
    }

    public MiuiToolBoxView(Context context) {
        this(context, null);
    }

    public MiuiToolBoxView(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public MiuiToolBoxView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.mBrushTransY = 0.0f;
        this.isSelect = false;
        this.isLongPress = false;
        this.mCurrentSelectToolView = null;
        this.mCurrentMultiSelectToolView = null;
        this.mSavedSelectViewType = 0;
        this.isAiBrush = false;
        this.isAISelect = false;
        this.mLastSelectToolView = null;
        this.mAction = -1;
        this.mHasChangeColor = false;
        this.mCurrentSelectColorView = null;
        this.mLastSelectForRule = null;
        this.mPageWindowShowing = false;
        this.aiType = 1;
        this.lastPenIndex = -1;
        this.tempPenIndex = -1;
        this.mLimitedToolbar = false;
        this.mathSelectCallBack = new MathSelectCallback() { // from class: com.miui.handwriting.common.view.toolbar.MiuiToolBoxView.6
            @Override // xc.engine.math.iink.callback.MathSelectCallback
            public void onMathSelectRequired(RectF rectF, List<DataCurve> list) {
            }

            @Override // xc.engine.math.iink.callback.MathSelectCallback
            public void onExitSelect() {
                Log.d(MiuiToolBoxView.TAG, "onExitSelect: CurrentType:" + MiuiToolBoxView.this.mSavedSelectViewType);
                if (MiuiToolBoxView.this.mSavedSelectViewType != 6) {
                    MiuiToolBoxView.this.mPageInkViewModel.getMiCurveEngine().getMathDataManager().setSelectState(0);
                    MiuiToolBoxView.this.cancelSelect();
                }
            }
        };
        this.toolUndoRedoListener = new View.OnClickListener() { // from class: com.miui.handwriting.common.view.toolbar.MiuiToolBoxView.9
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                MiuiToolBoxView.this.getFocus();
                MiuiToolBoxView.this.mPageInkViewModel.getCurrentItemView().getCreationEngine().getCurveEngine().getMathSystem().cancelAnimation();
                int id = view.getId();
                if (id == R.id.creation_tool_iv_undo) {
                    if (Utils.isFastClick(id)) {
                        return;
                    }
                    MiuiToolBoxView.this.mHandWritingImpl.showLoadingDialog();
                    MiuiToolBoxView.this.mPageInkViewModel.undo();
                    return;
                }
                if (id != R.id.creation_tool_iv_redo || Utils.isFastClick(id)) {
                    return;
                }
                MiuiToolBoxView.this.mHandWritingImpl.showLoadingDialog();
                MiuiToolBoxView.this.mPageInkViewModel.redo();
            }
        };
        this.toolClickListener = new View.OnClickListener() { // from class: com.miui.handwriting.common.view.toolbar.MiuiToolBoxView.10
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                Utils.hideSoftInput(view);
                MiuiToolBoxView.this.getFocus();
                int id = view.getId();
                if (id == R.id.creation_brush_ballpoint) {
                    MiuiToolBoxView.this.brushClick(view, PenType.BALL);
                } else if (id == R.id.creation_brush_pencil) {
                    MiuiToolBoxView.this.brushClick(view, PenType.PENCIL);
                } else if (id == R.id.creation_brush_ink) {
                    MiuiToolBoxView.this.brushClick(view, PenType.INK);
                } else if (id == R.id.creation_brush_markpen) {
                    MiuiToolBoxView.this.brushClick(view, PenType.MARK);
                } else if (id == R.id.creation_brush_watercolor) {
                    MiuiToolBoxView.this.brushClick(view, PenType.WATER_COLOR);
                } else if (id == R.id.creation_brush_eraser) {
                    MiuiToolBoxView.this.btnClick();
                    MiuiToolBoxView miuiToolBoxView = MiuiToolBoxView.this;
                    miuiToolBoxView.enableEraser(miuiToolBoxView.getCurrentEraserPen());
                    if (MiuiToolBoxView.this.activeTool(view)) {
                        MiuiToolBoxView.this.showEraserSizePopupWindow(view);
                    }
                } else if (id == R.id.creation_brush_ai) {
                    if (RomUtils.isUseNewCreationAI()) {
                        if (!AIEnterProcessor.INSTANCE.isShowGuide(MiuiToolBoxView.this.mContext)) {
                            MiuiToolBoxView.this.actionCallBack.showGuide(false);
                        } else {
                            MiuiToolBoxView.this.enableAi(view);
                        }
                    } else {
                        MiuiToolBoxView.this.btnClick();
                        if (MiuiToolBoxView.this.mPageInkViewModel.isUnlimited()) {
                            MiuiToolBoxView.this.aiType = 0;
                            MiuiToolBoxView.this.enableFullPageSelect(false);
                        }
                        MiuiToolBoxView.this.enableAI();
                        if (MiuiToolBoxView.this.activeTool(view)) {
                            MiuiToolBoxView.this.showAIPopWindow(view, 0);
                        }
                    }
                } else if (id == R.id.creation_brush_rect_select) {
                    if (!MiuiToolBoxView.this.activeTool(view)) {
                        MiuiToolBoxView.this.btnClick();
                        MiuiToolBoxView.this.dismissPopupWindow();
                        MiuiToolBoxView.this.enableSelect();
                    } else {
                        MiuiToolBoxView.this.dismissPopupWindow();
                    }
                } else if (id == R.id.creation_brush_ruler) {
                    MiuiToolBoxView.this.handleClickRule(view);
                } else if (id == R.id.creation_brush_color_default_left || id == R.id.creation_brush_color_default_center || id == R.id.creation_brush_color_default_right) {
                    if (!MiuiToolBoxView.this.isSelect && !MiuiToolBoxView.this.isEraser && !MiuiToolBoxView.this.isAISelect) {
                        MiuiToolBoxView.this.btnClick();
                        MiuiToolBoxView.this.handleClickColorPick(view);
                    }
                } else if (id == R.id.creation_brush_color_default) {
                    if (!MiuiToolBoxView.this.isSelect && !MiuiToolBoxView.this.isEraser && !MiuiToolBoxView.this.isAISelect) {
                        MiuiToolBoxView.this.btnClick();
                        MiuiToolBoxView.this.handleClickColorPick(view);
                    }
                } else if (id != R.id.creation_brush_multi_color_default_left && id != R.id.creation_brush_multi_color_default_center && id != R.id.creation_brush_multi_color_default_right) {
                    if (id == R.id.creation_brush_select) {
                        if (!MiuiToolBoxView.this.activeToolForMultiPen(view)) {
                            MiuiToolBoxView.this.btnClick();
                            if (MiuiToolBoxView.this.mCurrentSelectToolView != null) {
                                Folme.useAt(MiuiToolBoxView.this.mCurrentSelectToolView).state().to(ViewProperty.TRANSLATION_Y, Float.valueOf(MiuiToolBoxView.this.mBrushTransY));
                            }
                            MiuiToolBoxView miuiToolBoxView2 = MiuiToolBoxView.this;
                            miuiToolBoxView2.mCurrentSelectToolView = miuiToolBoxView2.mBrushSelect;
                            if (MiuiToolBoxView.this.isAiBrush) {
                                MiuiToolBoxView.this.enableAI();
                            } else {
                                MiuiToolBoxView miuiToolBoxView3 = MiuiToolBoxView.this;
                                miuiToolBoxView3.selectPenType(miuiToolBoxView3.getCurrentPenBen().getPenType());
                            }
                        } else {
                            MiuiToolBoxView.this.dismissPopupWindow();
                            if (!MiuiToolBoxView.this.mLimitedToolbar) {
                                MiuiToolBoxView.this.showMultiPenSizePopupWindow(view);
                            } else {
                                MiuiToolBoxView miuiToolBoxView4 = MiuiToolBoxView.this;
                                miuiToolBoxView4.showPenSizePopupWindow(miuiToolBoxView4.mBrushInk);
                            }
                        }
                        MiuiToolBoxView miuiToolBoxView5 = MiuiToolBoxView.this;
                        miuiToolBoxView5.updateSelectColorView(!miuiToolBoxView5.isAISelect);
                    } else if (id == R.id.creation_multi_brush_ballpoint) {
                        MiuiToolBoxView.this.multiBrushClick(view, PenType.BALL);
                    } else if (id == R.id.creation_multi_brush_pencil) {
                        MiuiToolBoxView.this.multiBrushClick(view, PenType.PENCIL);
                    } else if (id == R.id.creation_multi_brush_ink) {
                        MiuiToolBoxView.this.multiBrushClick(view, PenType.INK);
                    } else if (id == R.id.creation_multi_brush_markpen) {
                        MiuiToolBoxView.this.multiBrushClick(view, PenType.MARK);
                    } else if (id == R.id.creation_multi_brush_watercolor) {
                        MiuiToolBoxView.this.multiBrushClick(view, PenType.WATER_COLOR);
                    } else if (id == R.id.creation_multi_ai_select) {
                        if (!RomUtils.isUseNewCreationAI() || AIEnterProcessor.INSTANCE.isShowGuide(MiuiToolBoxView.this.mContext)) {
                            MiuiToolBoxView.this.isAISelect = true;
                            MiuiToolBoxView.this.multiToolClick(view);
                            MiuiToolBoxView.this.enableAI();
                        } else {
                            MiuiToolBoxView.this.actionCallBack.showGuide(true);
                        }
                    }
                }
                MiuiToolBoxView.this.saveToolBoxState();
            }
        };
        this.aiPenClickListener = new AIPenPopupWindow.OnAIPenClickListener() { // from class: com.miui.handwriting.common.view.toolbar.MiuiToolBoxView.11
            @Override // com.miui.handwriting.common.view.handview.pop.AIPenPopupWindow.OnAIPenClickListener
            public void onFullPageClick() {
                MiuiToolBoxView.this.aiType = 1;
                MiuiToolBoxView.this.btnClick();
                MiuiToolBoxView.this.enableFullPageSelect(true);
                MiuiToolBoxView.this.enableAI();
            }

            @Override // com.miui.handwriting.common.view.handview.pop.AIPenPopupWindow.OnAIPenClickListener
            public void onSelectClick() {
                MiuiToolBoxView.this.aiType = 0;
                MiuiToolBoxView.this.btnClick();
                MiuiToolBoxView.this.enableFullPageSelect(false);
                MiuiToolBoxView.this.enableAI();
            }
        };
        this.srcBitmap = new HashMap();
        this.mContext = context;
        this.mPenSet = new PenSet();
        LayoutInflater.from(context).inflate(R.layout.creation_tool_bar, this);
        initPadding();
        initToolBoxView(context, this);
        initColorView(UIUtils.isSmallScreenNoShare(context));
        boolean zIsSmallScreenForBrush = UIUtils.isSmallScreenForBrush(context);
        initBrushView(zIsSmallScreenForBrush);
        initPenData();
        if (!zIsSmallScreenForBrush) {
            setSelectBrushForBig();
        } else {
            setSelectBrushForSmall();
        }
        initShapeData();
        initEraser();
        initToolBoxViewData();
        getFocus();
        initBrushDisplaySequence();
        initToolViewSize();
        verifyUser();
        try {
            Activity activity = Utils.getActivity(this.mContext);
            if (activity != null) {
                Intent intent = activity.getIntent();
                this.mLimitedToolbar = (intent != null && intent.getBooleanExtra("external_launch", false)) || (intent != null && intent.getBooleanExtra("limited_toolbar", false));
                Log.d(TAG, "MiuiToolBoxView: mLimitedToolbar=" + this.mLimitedToolbar);
            }
        } catch (Throwable unused) {
        }
        applyLimitedToolbarIfNeeded();
        PenSet.PenSetChangeListener penSetChangeListener = new PenSet.PenSetChangeListener() { // from class: com.miui.handwriting.common.view.toolbar.MiuiToolBoxView.1
            @Override // com.miui.handwriting.common.data.dataset.PenSet.PenSetChangeListener
            public void onPenBeanChanged(PenBean penBean) {
                MiuiToolBoxView.this.onPenSetChanged(penBean);
            }
        };
        this.mPenSetChangeListener = penSetChangeListener;
        this.mPenSet.addListener(penSetChangeListener);
    }

    private void verifyUser() {
        if (RomUtils.isN81A()) {
            PreferenceUtils.setAIBrushPermission(true);
            return;
        }
        if (!PreferenceUtils.isNetworkPermissionAccepted()) {
            hideAI();
            return;
        }
        Account xiaomiAccount = ExtraAccountManager.getXiaomiAccount(HandWritingViewApplication.INSTANCE.getApplication());
        if (xiaomiAccount != null) {
            new VerifyServer().getServerVersion(xiaomiAccount.name, new VerifyServer.CallBack() { // from class: com.miui.handwriting.common.view.toolbar.MiuiToolBoxView.2
                @Override // com.miui.handwriting.common.ai.VerifyServer.CallBack
                public void onCallBack(Result<VersionData> result) {
                    if (MiuiToolBoxView.this.mContext == null) {
                        return;
                    }
                    if (result == null || result.getCode() != 0) {
                        MiuiToolBoxView.this.hideAI();
                        return;
                    }
                    PreferenceUtils.setAIBrushPermission(true);
                    if (!UIUtils.isSmallScreenForBrush(MiuiToolBoxView.this.mContext)) {
                        MiuiToolBoxView.this.mToolAI.setVisibility(8);
                    }
                    MiuiToolBoxView.this.mBrushDisplaySequence.add(8);
                }
            });
        } else {
            hideAI();
        }
    }

    public void setStylusPenQuickMenu(StylusPenQuickMenu stylusPenQuickMenu) {
        this.mStylusPenQuickMenu = stylusPenQuickMenu;
        stylusPenQuickMenu.setPageInkModel(this.mPageInkViewModel.getPageInkModel());
        this.mStylusPenQuickMenu.setAlphaUpdateListener(new OnQuickPenColorUpdateListener() { // from class: com.miui.handwriting.common.view.toolbar.MiuiToolBoxView.3
            @Override // com.miui.handwriting.common.view.quickMenu.listener.OnQuickPenColorUpdateListener
            public void update(int i, int i2) {
                MiuiToolBoxView.this.mPageInkViewModel.getEngineConfig().penProp.setPenAlpha(i2);
                MiuiToolBoxView.this.mPageInkViewModel.getEngineConfig().penProp.setPenColor(i);
                PenBean currentPenBen = MiuiToolBoxView.this.getCurrentPenBen();
                if (currentPenBen != null) {
                    HSLColor hSLColor = new HSLColor(i);
                    currentPenBen.setHSLColor(hSLColor);
                    currentPenBen.setDefaultHSLArray(currentPenBen.getCheckedItemIndex(), hSLColor);
                    currentPenBen.setPenAlpha(i2);
                    MiuiToolBoxView.this.setPenProp(currentPenBen.convert());
                    MiuiToolBoxView.this.updateStateFromQuickMenu(hSLColor);
                    MiuiToolBoxView.this.setPenBeanToQuickColorMenu();
                }
            }
        });
        this.mStylusPenQuickMenu.setPenSizeUpdateListener(new OnQuickPenSizeColorUpdateListener() { // from class: com.miui.handwriting.common.view.toolbar.MiuiToolBoxView.4
            @Override // com.miui.handwriting.common.view.quickMenu.listener.OnQuickPenSizeColorUpdateListener
            public void update(int i) {
                float f = i;
                MiuiToolBoxView.this.mPageInkViewModel.getEngineConfig().penProp.setPenSize(f);
                PenBean currentPenBen = MiuiToolBoxView.this.getCurrentPenBen();
                if (currentPenBen != null) {
                    currentPenBen.setPenSize(f);
                    MiuiToolBoxView.this.setPenProp(currentPenBen.convert());
                }
            }
        });
        this.mStylusPenQuickMenu.setEraserUpdateListener(new OnQuickEraserUpdateListener() { // from class: com.miui.handwriting.common.view.toolbar.MiuiToolBoxView$$ExternalSyntheticLambda13
            @Override // com.miui.handwriting.common.view.quickMenu.listener.OnQuickEraserUpdateListener
            public final void onUpdate(DeleteType deleteType, float f) {
                this.f$0.m813x8a19342d(deleteType, f);
            }
        });
        this.mStylusPenQuickMenu.setColorSelectUpdateListener(new OnQuickColorSelectUpdateListener() { // from class: com.miui.handwriting.common.view.toolbar.MiuiToolBoxView$$ExternalSyntheticLambda14
            @Override // com.miui.handwriting.common.view.quickMenu.listener.OnQuickColorSelectUpdateListener
            public final void onUpdate(int i, int i2) {
                this.f$0.m814x17064b4c(i, i2);
            }
        });
        if (this.isEraser) {
            this.mStylusPenQuickMenu.updateEraserSelected(true);
            this.mStylusPenQuickMenu.updatePenSelected(false);
        } else if (this.isSelect || isAISelect()) {
            this.mStylusPenQuickMenu.updateEraserSelected(false);
            this.mStylusPenQuickMenu.updatePenSelected(false);
        } else {
            this.mStylusPenQuickMenu.updateEraserSelected(false);
            this.mStylusPenQuickMenu.updatePenSelected(true);
        }
    }

    /* JADX INFO: renamed from: lambda$setStylusPenQuickMenu$0$com-miui-handwriting-common-view-toolbar-MiuiToolBoxView, reason: not valid java name */
    /* synthetic */ void m813x8a19342d(DeleteType deleteType, float f) {
        getCurrentEraserPen().setDeleteType(deleteType);
        getCurrentEraserPen().setDeleteRadius(f);
        setDeleteProp(getCurrentEraserPen().covert());
    }

    /* JADX INFO: renamed from: lambda$setStylusPenQuickMenu$1$com-miui-handwriting-common-view-toolbar-MiuiToolBoxView, reason: not valid java name */
    /* synthetic */ void m814x17064b4c(int i, int i2) {
        PenBean currentPenBen = getCurrentPenBen();
        if (currentPenBen != null) {
            currentPenBen.setCheckedItemIndex(i);
            HSLColor hSLColor = new HSLColor(i2);
            currentPenBen.setDefaultHSLArray(i, hSLColor);
            setColorToolParams();
            currentPenBen.setHSLColor(hSLColor);
            currentPenBen.setPenAlpha(hSLColor.getAlpha());
            setPenProp(currentPenBen.convert());
            updateBrushBg();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void hideAI() {
        PreferenceUtils.setAIBrushPermission(false);
        if (this.isAiBrush) {
            PenBean currentPenBen = getCurrentPenBen();
            if (currentPenBen != null) {
                this.mSavedSelectViewType = currentPenBen.getPenType().getValue();
            }
            if (this.mPageInkViewModel != null) {
                changeBrush(this.mSavedSelectViewType);
            }
            this.isAiBrush = false;
            if (!UIUtils.isSmallScreenForBrush(this.mContext)) {
                setSelectBrushForBig();
            } else {
                setSelectBrushForSmall();
            }
        }
        this.mToolAI.setVisibility(8);
    }

    private void initPadding() {
        setPadding(getPaddingLeft(), DisplayCutoutHolder.INSTANCE.getSafeTop() + (UIUtils.isSmallWindowMode(this.mContext) ? UIUtils.getStatusBarHeight(getContext()) : 0), getPaddingRight(), getPaddingBottom());
    }

    private void initDialogKeyListener() {
        if (this.mOnKeyListener == null) {
            this.mOnKeyListener = new View.OnKeyListener() { // from class: com.miui.handwriting.common.view.toolbar.MiuiToolBoxView.5
                @Override // android.view.View.OnKeyListener
                public boolean onKey(View view, int i, KeyEvent keyEvent) {
                    Log.i(MiuiToolBoxView.TAG, "mOnKeyListener onKey keyCode=" + i + ",event.getKeyCode=" + keyEvent.getKeyCode() + ", event.getAction" + keyEvent.getAction() + ", event.getRepeatCount()=" + keyEvent.getRepeatCount());
                    if (keyEvent.getAction() == 1) {
                        if (i == 92) {
                            MiuiToolBoxView.this.dismissDialog();
                            MiuiToolBoxView.this.changeColor();
                            return true;
                        }
                        if (i == 93) {
                            MiuiToolBoxView.this.doAboutPrimaryKey();
                            MiuiToolBoxView.this.isLongPress = false;
                            return true;
                        }
                    } else if (keyEvent.getAction() == 0 && i == 93 && MiuiToolBoxView.this.isLongPress) {
                        MiuiToolBoxView.this.dismissDialog();
                        MiuiToolBoxView.this.setEraser();
                    } else if (keyEvent.getRepeatCount() == 0) {
                        MiuiToolBoxView.this.isLongPress = false;
                    } else {
                        MiuiToolBoxView.this.isLongPress = true;
                    }
                    return false;
                }
            };
        }
        this.mEraserPopupWindow.setOnKeyListener(this.mOnKeyListener);
        this.mPenColorPopupWindow.setOnKeyListener(this.mOnKeyListener);
        this.mPenSetPopupWindow.setOnKeyListener(this.mOnKeyListener);
        this.mPenSizePopupWindow.setOnKeyListener(this.mOnKeyListener);
    }

    private void initBrushDisplaySequence() {
        ArrayList arrayList = new ArrayList();
        this.mBrushDisplaySequence = arrayList;
        arrayList.add(2);
        this.mBrushDisplaySequence.add(3);
        this.mBrushDisplaySequence.add(1);
        this.mBrushDisplaySequence.add(4);
        this.mBrushDisplaySequence.add(10);
        if (RomUtils.isN81A()) {
            this.mBrushDisplaySequence.add(8);
        }
        if (this.mLimitedToolbar) {
            this.mBrushDisplaySequence.clear();
            this.mBrushDisplaySequence.add(1);
            Log.d(TAG, "Limited toolbar mode: only showing ink pen");
        }
    }

    private void initEraser() {
        this.currentEraserPen = getEraseDeleteFromLocal();
    }

    @Override // android.view.ViewGroup, android.view.View
    public boolean dispatchTouchEvent(MotionEvent motionEvent) {
        this.isKeyBoardVisInSmallWindow = KeyboardHelper.isKeyboardVisibleInMultiWindowMode(Utils.getActivity(this.mContext));
        return super.dispatchTouchEvent(motionEvent);
    }

    private void initToolBoxView(Context context, View view) {
        this.mToolParent = view.findViewById(R.id.creation_tool_parent);
        this.mScrollContainer = view.findViewById(R.id.creation_tool_panel_group);
        this.mBrushContainer = view.findViewById(R.id.creation_tool_panel_container);
        this.mColorContainer = view.findViewById(R.id.creation_tool_color_group);
        this.mBrushTransY = context.getResources().getDimension(R.dimen.creation_tool_select_tran_y);
        View viewFindViewById = view.findViewById(R.id.creation_brush_select);
        this.mBrushSelect = viewFindViewById;
        viewFindViewById.setOnClickListener(this.toolClickListener);
        View viewFindViewById2 = view.findViewById(R.id.creation_brush_ballpoint);
        this.mBrushBallpoint = viewFindViewById2;
        viewFindViewById2.setOnClickListener(this.toolClickListener);
        View viewFindViewById3 = view.findViewById(R.id.creation_brush_pencil);
        this.mBrushPencil = viewFindViewById3;
        viewFindViewById3.setOnClickListener(this.toolClickListener);
        View viewFindViewById4 = view.findViewById(R.id.creation_brush_ink);
        this.mBrushInk = viewFindViewById4;
        viewFindViewById4.setOnClickListener(this.toolClickListener);
        View viewFindViewById5 = view.findViewById(R.id.creation_brush_markpen);
        this.mBrushMarkPen = viewFindViewById5;
        viewFindViewById5.setOnClickListener(this.toolClickListener);
        View viewFindViewById6 = view.findViewById(R.id.creation_brush_watercolor);
        this.mBrushWaterColor = viewFindViewById6;
        viewFindViewById6.setOnClickListener(this.toolClickListener);
        View viewFindViewById7 = view.findViewById(R.id.creation_brush_eraser);
        this.mBrushEraser = viewFindViewById7;
        viewFindViewById7.setOnClickListener(this.toolClickListener);
        View viewFindViewById8 = view.findViewById(R.id.creation_brush_rect_select);
        this.mToolRectSelect = viewFindViewById8;
        viewFindViewById8.setOnClickListener(this.toolClickListener);
        View viewFindViewById9 = view.findViewById(R.id.creation_brush_ai);
        this.mToolAI = viewFindViewById9;
        viewFindViewById9.setOnClickListener(this.toolClickListener);
        View viewFindViewById10 = view.findViewById(R.id.creation_brush_ruler);
        this.mToolRuler = viewFindViewById10;
        viewFindViewById10.setOnClickListener(this.toolClickListener);
        ColorSelectView colorSelectView = (ColorSelectView) view.findViewById(R.id.creation_brush_color_default);
        this.mColorView = colorSelectView;
        colorSelectView.setOnClickListener(this.toolClickListener);
        Folme.useAt(this.mColorView).touch().handleTouchOf(this.mColorView, new AnimConfig[0]);
        ColorSelectView colorSelectView2 = (ColorSelectView) view.findViewById(R.id.creation_brush_color_default_left);
        this.mLeftColorView = colorSelectView2;
        colorSelectView2.setOnClickListener(this.toolClickListener);
        Folme.useAt(this.mLeftColorView).touch().handleTouchOf(this.mLeftColorView, new AnimConfig[0]);
        ColorSelectView colorSelectView3 = (ColorSelectView) view.findViewById(R.id.creation_brush_color_default_center);
        this.mCenterColorView = colorSelectView3;
        colorSelectView3.setOnClickListener(this.toolClickListener);
        Folme.useAt(this.mCenterColorView).touch().handleTouchOf(this.mCenterColorView, new AnimConfig[0]);
        ColorSelectView colorSelectView4 = (ColorSelectView) view.findViewById(R.id.creation_brush_color_default_right);
        this.mRightColorView = colorSelectView4;
        colorSelectView4.setOnClickListener(this.toolClickListener);
        Folme.useAt(this.mRightColorView).touch().handleTouchOf(this.mRightColorView, new AnimConfig[0]);
        this.mLeftColorView.setContentDescription(String.format(getResources().getString(R.string.creation_tool_color_left), 1));
        this.mCenterColorView.setContentDescription(String.format(getResources().getString(R.string.creation_tool_color_center), 2));
        this.mRightColorView.setContentDescription(String.format(getResources().getString(R.string.creation_tool_color_right), 3));
        View viewFindViewById11 = view.findViewById(R.id.creation_tool_iv_undo);
        this.mToolUndo = viewFindViewById11;
        viewFindViewById11.setEnabled(false);
        this.mToolUndo.setOnClickListener(this.toolUndoRedoListener);
        Folme.useAt(this.mToolUndo).touch().handleTouchOf(this.mToolUndo, new AnimConfig[0]);
        View viewFindViewById12 = view.findViewById(R.id.creation_tool_iv_redo);
        this.mToolRedo = viewFindViewById12;
        viewFindViewById12.setEnabled(false);
        this.mToolRedo.setOnClickListener(this.toolUndoRedoListener);
        Folme.useAt(this.mToolRedo).touch().handleTouchOf(this.mToolRedo, new AnimConfig[0]);
        this.mToolMore = view.findViewById(R.id.creation_tool_iv_more);
        ImageView imageView = (ImageView) view.findViewById(R.id.creation_tool_iv_share);
        this.mToolShare = imageView;
        imageView.setEnabled(false);
        initClickListener();
        ImageView imageView2 = (ImageView) findViewById(R.id.creation_tool_iv_back);
        this.mToolBack = imageView2;
        imageView2.setOnClickListener(new View.OnClickListener() { // from class: com.miui.handwriting.common.view.toolbar.MiuiToolBoxView$$ExternalSyntheticLambda1
            @Override // android.view.View.OnClickListener
            public final void onClick(View view2) {
                this.f$0.m809xc0dbbc2a(view2);
            }
        });
        TextView textView = (TextView) findViewById(R.id.text_thumbnail);
        this.mTextThumbnail = textView;
        textView.setOnClickListener(new View.OnClickListener() { // from class: com.miui.handwriting.common.view.toolbar.MiuiToolBoxView$$ExternalSyntheticLambda2
            @Override // android.view.View.OnClickListener
            public final void onClick(View view2) {
                this.f$0.m810x4dc8d349(view2);
            }
        });
        initToolBoxViewDim();
    }

    /* JADX INFO: renamed from: lambda$initToolBoxView$2$com-miui-handwriting-common-view-toolbar-MiuiToolBoxView, reason: not valid java name */
    /* synthetic */ void m809xc0dbbc2a(View view) {
        ICreationEngine creationEngine;
        setImportantForAccessibility(2);
        try {
            IMiuiHandWritingModel iMiuiHandWritingModel = this.mPageInkViewModel;
            if (iMiuiHandWritingModel != null && iMiuiHandWritingModel.getCurrentItemView() != null && (creationEngine = this.mPageInkViewModel.getCurrentItemView().getCreationEngine()) != null && creationEngine.getCurveEngine() != null && creationEngine.getCurveEngine().getMathSystem() != null) {
                creationEngine.getCurveEngine().getMathSystem().setSaveBtnClicked(true);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        View.OnClickListener onClickListener = this.mSaveBtnListener;
        if (onClickListener != null) {
            onClickListener.onClick(view);
        } else {
            Log.w(TAG, "listener null, need set save click listener");
        }
        getFocus();
    }

    /* JADX INFO: renamed from: lambda$initToolBoxView$3$com-miui-handwriting-common-view-toolbar-MiuiToolBoxView, reason: not valid java name */
    /* synthetic */ void m810x4dc8d349(View view) {
        this.actionCallBack.clickThumbnail();
    }

    private void initClickListener() {
        this.mToolMore.setOnClickListener(new View.OnClickListener() { // from class: com.miui.handwriting.common.view.toolbar.MiuiToolBoxView$$ExternalSyntheticLambda8
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f$0.m804x154ff3c3(view);
            }
        });
        this.mToolShare.setOnClickListener(new View.OnClickListener() { // from class: com.miui.handwriting.common.view.toolbar.MiuiToolBoxView$$ExternalSyntheticLambda9
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f$0.m805xa23d0ae2(view);
            }
        });
    }

    /* JADX INFO: renamed from: lambda$initClickListener$5$com-miui-handwriting-common-view-toolbar-MiuiToolBoxView, reason: not valid java name */
    /* synthetic */ void m804x154ff3c3(View view) {
        if (Utils.isFastClick()) {
            return;
        }
        if (this.mLimitedToolbar) {
            IMiuiHandWritingModel iMiuiHandWritingModel = this.mPageInkViewModel;
            if (iMiuiHandWritingModel == null || iMiuiHandWritingModel.isContentEmpty(iMiuiHandWritingModel.getCurrentPosition())) {
                return;
            }
            IMiuiHandWritingModel iMiuiHandWritingModel2 = this.mPageInkViewModel;
            iMiuiHandWritingModel2.clearPage(iMiuiHandWritingModel2.getCurrentPosition());
            return;
        }
        initEditMorePopupWindow(this.mPageInkViewModel.isUnlimited());
        final Activity activity = Utils.getActivity(this.mContext);
        if (activity == null || activity.isFinishing()) {
            return;
        }
        final int iInitPopupWindow = this.mEditMoreActionPopupWindow.initPopupWindow();
        Utils.hideSoftInput(this.mToolMore);
        this.mToolMore.postDelayed(new Runnable() { // from class: com.miui.handwriting.common.view.toolbar.MiuiToolBoxView$$ExternalSyntheticLambda15
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.m803x8862dca4(iInitPopupWindow, activity);
            }
        }, this.isKeyBoardVisInSmallWindow ? 800L : 200L);
    }

    /* JADX INFO: renamed from: lambda$initClickListener$4$com-miui-handwriting-common-view-toolbar-MiuiToolBoxView, reason: not valid java name */
    /* synthetic */ void m803x8862dca4(int i, Activity activity) {
        if (this.mEditMoreActionPopupWindow.isShowing()) {
            Log.d(TAG, "mEditMoreActionPopupWindow isShowing");
            return;
        }
        MoreActionPopupWindow moreActionPopupWindow = this.mEditMoreActionPopupWindow;
        moreActionPopupWindow.showAsDropDown(this.mToolMore, calculateMorePopupWindowOffset(moreActionPopupWindow), i);
        if (UIUtils.isInMultiWindowMode(activity)) {
            initEditMorePopupWindow(this.mPageInkViewModel.isUnlimited());
        }
    }

    /* JADX INFO: renamed from: lambda$initClickListener$6$com-miui-handwriting-common-view-toolbar-MiuiToolBoxView, reason: not valid java name */
    /* synthetic */ void m805xa23d0ae2(View view) {
        View.OnClickListener onClickListener = this.mShareBtnListener;
        if (onClickListener != null) {
            onClickListener.onClick(this.mToolShare);
        }
    }

    @Override // com.miui.handwriting.common.view.toolbar.ICommonMiuiHandToolbar
    public void changeColorMode(int i, int i2) {
        IMiuiHandWritingModel iMiuiHandWritingModel = this.mPageInkViewModel;
        if (iMiuiHandWritingModel != null) {
            iMiuiHandWritingModel.onTitleColorChanged(i, i2);
        }
    }

    private void initEditMorePopupWindow(boolean z) {
        MoreActionPopupWindow moreActionPopupWindow = this.mEditMoreActionPopupWindow;
        IMiuiHandWritingModel iMiuiHandWritingModel = this.mPageInkViewModel;
        moreActionPopupWindow.setBgColorChooseVisible(iMiuiHandWritingModel.canChangeSizeConfig(iMiuiHandWritingModel.getCurrentPosition()));
        this.mEditMoreActionPopupWindow.setOnKeyListener(this.mOnKeyListener);
        this.mEditMoreActionPopupWindow.updateClearContentTitle(z);
        this.mEditMoreActionPopupWindow.setShowShare(UIUtils.isSmallScreenNoShare(this.mContext));
        this.mEditMoreActionPopupWindow.setActionCallBack(new AnonymousClass7());
        this.mEditMoreActionPopupWindow.setTitleColorChangeListener(new MoreActionPopupWindow.OnTitleColorChangeListener() { // from class: com.miui.handwriting.common.view.toolbar.MiuiToolBoxView$$ExternalSyntheticLambda4
            @Override // com.miui.handwriting.common.view.popup.MoreActionPopupWindow.OnTitleColorChangeListener
            public final void onTitleColorChanged(int i) {
                this.f$0.m806x1bb6e040(i);
            }
        });
        this.mEditMoreActionPopupWindow.setShowDeletePage(this.mPageInkViewModel.getPageCount() > 1);
        this.mEditMoreActionPopupWindow.setPenWrite(this.mPageInkViewModel.isOnlyPenWrite());
        this.mEditMoreActionPopupWindow.setPenWriteCheckedChangeListener(new MoreActionPopupWindow.PenWriteCheckedChangeListener() { // from class: com.miui.handwriting.common.view.toolbar.MiuiToolBoxView$$ExternalSyntheticLambda5
            @Override // com.miui.handwriting.common.view.popup.MoreActionPopupWindow.PenWriteCheckedChangeListener
            public final void call(boolean z2) {
                this.f$0.m807xa8a3f75f(z2);
            }
        });
        this.mEditMoreActionPopupWindow.setDoubleFingerUndoCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() { // from class: com.miui.handwriting.common.view.toolbar.MiuiToolBoxView$$ExternalSyntheticLambda6
            @Override // android.widget.CompoundButton.OnCheckedChangeListener
            public final void onCheckedChanged(CompoundButton compoundButton, boolean z2) {
                this.f$0.m808x35910e7e(compoundButton, z2);
            }
        });
        PagePreviewData currentPagePreView = getCurrentPagePreView();
        this.mEditMoreActionPopupWindow.setBackgroundIndex(currentPagePreView.bgColor, CanvasBgSet.findIndex(currentPagePreView.bgGrid));
        this.mEditMoreActionPopupWindow.setPageSizeText(getPageSizeConfig(this.mPageInkViewModel.getCurrentPosition(), this.mPageInkViewModel.isUnlimited()));
    }

    /* JADX INFO: renamed from: com.miui.handwriting.common.view.toolbar.MiuiToolBoxView$7, reason: invalid class name */
    class AnonymousClass7 implements MoreActionPopupWindow.ActionCallBack {
        AnonymousClass7() {
        }

        @Override // com.miui.handwriting.common.view.popup.MoreActionPopupWindow.ActionCallBack
        public void call(int i) {
            if (i != MoreActionPopupWindow.ACTION_PAGE_SIZE) {
                MiuiToolBoxView.this.mEditMoreActionPopupWindow.dismiss();
            }
            if (i == MoreActionPopupWindow.ACTION_PICK_IMAGE) {
                if (MiuiToolBoxView.this.actionCallBack != null) {
                    MiuiToolBoxView.this.actionCallBack.insertImage();
                    return;
                }
                return;
            }
            if (i == MoreActionPopupWindow.ACTION_INSERT_TEXT) {
                MiuiToolBoxView.this.enableViewSelect();
                MiuiToolBoxView.this.insertRichText();
                return;
            }
            if (i == MoreActionPopupWindow.ACTION_SHOW_KEY_SHORT) {
                Activity activity = Utils.getActivity(MiuiToolBoxView.this.mContext);
                if (activity == null || activity.isFinishing()) {
                    return;
                }
                View viewInflate = View.inflate(activity, R.layout.creation_shortcut_key_view, null);
                MiuiToolBoxView.this.mHotKeyPopDialog = MoreActionPopupWindow.getHotKeyPopDialog(activity, viewInflate);
                if (MiuiToolBoxView.this.mHotKeyPopDialog != null) {
                    MiuiToolBoxView.this.mHotKeyPopDialog.show();
                    if (MiuiToolBoxView.this.mHotKeyPopDialog.getWindow() == null) {
                        return;
                    }
                    viewInflate.measure(0, 0);
                    int i2 = (int) (activity.getResources().getDisplayMetrics().widthPixels * 0.95f);
                    int i3 = (int) (activity.getResources().getDisplayMetrics().heightPixels * 0.85f);
                    Log.d(MiuiToolBoxView.TAG, "screenWidth:" + i2 + ",getMeasuredWidth:" + viewInflate.getMeasuredWidth());
                    WindowManager.LayoutParams attributes = MiuiToolBoxView.this.mHotKeyPopDialog.getWindow().getAttributes();
                    if (viewInflate.getMeasuredWidth() >= i2) {
                        Log.e(MiuiToolBoxView.TAG, "screen width is too small");
                        attributes.width = i2;
                        View viewFindViewById = viewInflate.findViewById(R.id.creation_img_primary_key);
                        if (viewFindViewById != null) {
                            ViewGroup.LayoutParams layoutParams = viewFindViewById.getLayoutParams();
                            layoutParams.width = attributes.width / 3;
                            viewFindViewById.setLayoutParams(layoutParams);
                        }
                        View viewFindViewById2 = viewInflate.findViewById(R.id.creation_img_sub_key);
                        if (viewFindViewById2 != null) {
                            ViewGroup.LayoutParams layoutParams2 = viewFindViewById2.getLayoutParams();
                            layoutParams2.width = attributes.width / 3;
                            viewFindViewById2.setLayoutParams(layoutParams2);
                        }
                        MiuiToolBoxView.this.mHotKeyPopDialog.getWindow().setAttributes(attributes);
                    }
                    if (viewInflate.getMeasuredHeight() >= i3) {
                        Log.e(MiuiToolBoxView.TAG, "screen  height is too small");
                        attributes.height = i3;
                        MiuiToolBoxView.this.mHotKeyPopDialog.getWindow().setAttributes(attributes);
                    }
                    if (MiuiToolBoxView.this.mHotKeyPopDialog.getContext().getResources().getConfiguration().fontScale <= DisplayUtils.DEFAULT_FONT_SIZE || !ScreenModeUtils.isFull(MiuiToolBoxView.this.mContext)) {
                        return;
                    }
                    attributes.width = MiuiToolBoxView.this.mHotKeyPopDialog.getContext().getResources().getDimensionPixelOffset(R.dimen.creation_hot_key_dialog_max_width);
                    MiuiToolBoxView.this.mHotKeyPopDialog.getWindow().setAttributes(attributes);
                    return;
                }
                return;
            }
            if (i == MoreActionPopupWindow.ACTION_DELETE_PAGE) {
                AlertDialog.Builder builder = new AlertDialog.Builder(MiuiToolBoxView.this.mContext);
                builder.setTitle(R.string.creation_more_tool_delete_page);
                builder.setMessage(R.string.creation_more_tool_delete_page_message);
                builder.setPositiveButton(R.string.creation_ppw_select_delete, new DialogInterface.OnClickListener() { // from class: com.miui.handwriting.common.view.toolbar.MiuiToolBoxView$7$$ExternalSyntheticLambda0
                    @Override // android.content.DialogInterface.OnClickListener
                    public final void onClick(DialogInterface dialogInterface, int i4) {
                        this.f$0.m818x6bfad4af(dialogInterface, i4);
                    }
                });
                builder.setNegativeButton(R.string.creation_edit_limit_dialog_cancel, (DialogInterface.OnClickListener) null);
                MiuiToolBoxView.this.mDeletePageDialog = builder.create();
                MiuiToolBoxView.this.mDeletePageDialog.show();
                return;
            }
            if (i == MoreActionPopupWindow.ACTION_SHARE) {
                if (MiuiToolBoxView.this.mShareBtnListener != null) {
                    MiuiToolBoxView.this.mShareBtnListener.onClick(MiuiToolBoxView.this.mToolMore);
                    return;
                }
                return;
            }
            if (i == MoreActionPopupWindow.ACTION_INSERT_SHAPE) {
                MiuiToolBoxView.this.mShapeSelectPopupWindow = new ShapeSelectPopupWindow(MiuiToolBoxView.this.getContext());
                MiuiToolBoxView.this.mShapeSelectPopupWindow.setActionCallBack(new ShapeSelectPopupWindow.ShapeSelectCallBack() { // from class: com.miui.handwriting.common.view.toolbar.MiuiToolBoxView.7.1
                    @Override // com.miui.handwriting.common.view.popup.ShapeSelectPopupWindow.ShapeSelectCallBack
                    public void callBackground(int i4, int i5) {
                    }

                    @Override // com.miui.handwriting.common.view.popup.ShapeSelectPopupWindow.ShapeSelectCallBack
                    public void call(int i4) {
                        ShapeEditSet.initShapeEditBeans(MiuiToolBoxView.this.getContext());
                        if (i4 == 7) {
                            ShapeEditSet.setCurShape(ShapeEditSet.getShapeEditBeanByType(7));
                        } else if (i4 == 2) {
                            ShapeEditSet.setCurShape(ShapeEditSet.getShapeEditBeanByType(2));
                        } else if (i4 == 3) {
                            ShapeEditSet.setCurShape(ShapeEditSet.getShapeEditBeanByType(3));
                        } else if (i4 == 5) {
                            ShapeEditSet.setCurShape(ShapeEditSet.getShapeEditBeanByType(5));
                        } else if (i4 == 6) {
                            ShapeEditSet.setCurShape(ShapeEditSet.getShapeEditBeanByType(6));
                        } else if (i4 == 1) {
                            ShapeEditSet.setCurShape(ShapeEditSet.getShapeEditBeanByType(1));
                        } else if (i4 == 4) {
                            ShapeEditSet.setCurShape(ShapeEditSet.getShapeEditBeanByType(4));
                        }
                        if (MiuiToolBoxView.this.mShapeSelectPopupWindow != null && MiuiToolBoxView.this.mShapeSelectPopupWindow.isShowing()) {
                            MiuiToolBoxView.this.mShapeSelectPopupWindow.dismiss();
                        }
                        MiuiToolBoxView.this.enableViewSelect();
                        MiuiToolBoxView.this.insertShape();
                    }
                });
                int dimension = (int) ((MiuiToolBoxView.this.getContext().getResources().getDimension(R.dimen.creation_tools_head_height) - MiuiToolBoxView.this.getContext().getResources().getDimension(R.dimen.creation_tool_head_icon_size)) - MiuiToolBoxView.this.getContext().getResources().getDimension(R.dimen.creation_tool_bar_topmargin));
                Log.d(MiuiToolBoxView.TAG, "more y:" + dimension);
                Utils.hideSoftInput(MiuiToolBoxView.this.mToolMore);
                ShapeSelectPopupWindow shapeSelectPopupWindow = MiuiToolBoxView.this.mShapeSelectPopupWindow;
                View view = MiuiToolBoxView.this.mToolMore;
                MiuiToolBoxView miuiToolBoxView = MiuiToolBoxView.this;
                shapeSelectPopupWindow.showAsDropDown(view, miuiToolBoxView.calculateMorePopupWindowOffset(miuiToolBoxView.mShapeSelectPopupWindow), dimension);
                return;
            }
            if (i == MoreActionPopupWindow.ACTION_CLEAR_CONTENT) {
                if (MiuiToolBoxView.this.mPageInkViewModel.isContentEmpty(MiuiToolBoxView.this.mPageInkViewModel.getCurrentPosition())) {
                    return;
                }
                MiuiToolBoxView.this.mPageInkViewModel.clearPage(MiuiToolBoxView.this.mPageInkViewModel.getCurrentPosition());
                return;
            }
            if (i == MoreActionPopupWindow.ACTION_PAGE_SIZE) {
                if (!MiuiToolBoxView.this.mPageInkViewModel.isContentEmpty(MiuiToolBoxView.this.mPageInkViewModel.getCurrentPosition())) {
                    Toast.makeText(MiuiToolBoxView.this.mContext.getApplicationContext(), MiuiToolBoxView.this.mContext.getApplicationContext().getString(R.string.creation_page_unbounded_with_content_change_size_limit), 0).show();
                    return;
                }
                MiuiToolBoxView miuiToolBoxView2 = MiuiToolBoxView.this;
                final PageSizeConfig pageSizeConfig = miuiToolBoxView2.getPageSizeConfig(miuiToolBoxView2.mPageInkViewModel.getCurrentPosition(), MiuiToolBoxView.this.mPageInkViewModel.isUnlimited());
                final PageSizeConfig pageSizeConfig2 = new PageSizeConfig(pageSizeConfig);
                MiuiToolBoxView.this.mDropDownSingleChoiceMenu = new DropDownSingleChoiceMenu(MiuiToolBoxView.this.mContext);
                ArrayList arrayList = new ArrayList();
                if (MiuiToolBoxView.this.mPageInkViewModel.isPcMode()) {
                    arrayList.add(MiuiToolBoxView.this.mContext.getString(R.string.creation_page_size_default_v));
                    arrayList.add(MiuiToolBoxView.this.mContext.getString(R.string.creation_page_size_default_h));
                    arrayList.add(String.format(MiuiToolBoxView.this.mContext.getString(R.string.creation_page_size_16_9_v), 16, 9));
                    arrayList.add(String.format(MiuiToolBoxView.this.mContext.getString(R.string.creation_page_size_16_9_h), 16, 9));
                    arrayList.add(String.format(MiuiToolBoxView.this.mContext.getString(R.string.creation_page_size_1_1), 1, 1));
                } else {
                    arrayList.add(MiuiToolBoxView.this.mContext.getString(R.string.creation_page_size_unbounded));
                    arrayList.add(MiuiToolBoxView.this.mContext.getString(R.string.creation_page_size_default_v));
                    arrayList.add(MiuiToolBoxView.this.mContext.getString(R.string.creation_page_size_default_h));
                    arrayList.add(String.format(MiuiToolBoxView.this.mContext.getString(R.string.creation_page_size_16_9_v), 16, 9));
                    arrayList.add(String.format(MiuiToolBoxView.this.mContext.getString(R.string.creation_page_size_16_9_h), 16, 9));
                    arrayList.add(String.format(MiuiToolBoxView.this.mContext.getString(R.string.creation_page_size_1_1), 1, 1));
                    arrayList.add(MiuiToolBoxView.this.mContext.getString(R.string.creation_page_size_custom));
                }
                MiuiToolBoxView.this.mDropDownSingleChoiceMenu.setAnchorView(MiuiToolBoxView.this.mEditMoreActionPopupWindow.showAnchorView());
                MiuiToolBoxView.this.mDropDownSingleChoiceMenu.setItems(arrayList);
                MiuiToolBoxView miuiToolBoxView3 = MiuiToolBoxView.this;
                miuiToolBoxView3.setDefaultItem(pageSizeConfig2, miuiToolBoxView3.mPageInkViewModel.getPageCount());
                MiuiToolBoxView.this.mDropDownSingleChoiceMenu.show();
                MiuiToolBoxView.this.mDropDownSingleChoiceMenu.setOnMenuListener(new DropDownSingleChoiceMenu.OnMenuListener() { // from class: com.miui.handwriting.common.view.toolbar.MiuiToolBoxView.7.2
                    @Override // com.miui.handwriting.common.view.popup.DropDownSingleChoiceMenu.OnMenuListener
                    public void onDismiss() {
                    }

                    @Override // com.miui.handwriting.common.view.popup.DropDownSingleChoiceMenu.OnMenuListener
                    public void onShow() {
                    }

                    @Override // com.miui.handwriting.common.view.popup.DropDownSingleChoiceMenu.OnMenuListener
                    public void onItemSelected(DropDownSingleChoiceMenu dropDownSingleChoiceMenu, int i4) {
                        if (!TextUtils.equals(MiuiToolBoxView.this.mEditMoreActionPopupWindow.getPageSizeText().toString(), dropDownSingleChoiceMenu.getItems().get(i4)) || TextUtils.equals(dropDownSingleChoiceMenu.getItems().get(i4), MiuiToolBoxView.this.mContext.getString(R.string.creation_page_size_custom))) {
                            MiuiToolBoxView.this.mDropDownSingleChoiceMenu.dismiss();
                            if (!TextUtils.equals(dropDownSingleChoiceMenu.getItems().get(i4), MiuiToolBoxView.this.mContext.getString(R.string.creation_page_size_custom))) {
                                if (!TextUtils.equals(dropDownSingleChoiceMenu.getItems().get(i4), MiuiToolBoxView.this.mContext.getString(R.string.creation_page_size_default_v))) {
                                    if (!TextUtils.equals(dropDownSingleChoiceMenu.getItems().get(i4), MiuiToolBoxView.this.mContext.getString(R.string.creation_page_size_default_h))) {
                                        if (!TextUtils.equals(dropDownSingleChoiceMenu.getItems().get(i4), String.format(MiuiToolBoxView.this.mContext.getString(R.string.creation_page_size_16_9_v), 16, 9))) {
                                            if (!TextUtils.equals(dropDownSingleChoiceMenu.getItems().get(i4), String.format(MiuiToolBoxView.this.mContext.getString(R.string.creation_page_size_16_9_h), 16, 9))) {
                                                if (!TextUtils.equals(dropDownSingleChoiceMenu.getItems().get(i4), String.format(MiuiToolBoxView.this.mContext.getString(R.string.creation_page_size_1_1), 1, 1))) {
                                                    if (TextUtils.equals(dropDownSingleChoiceMenu.getItems().get(i4), MiuiToolBoxView.this.mContext.getString(R.string.creation_page_size_unbounded))) {
                                                        if (MiuiToolBoxView.this.mPageInkViewModel.getPageCount() > 1) {
                                                            Toast.makeText(MiuiToolBoxView.this.mContext.getApplicationContext(), MiuiToolBoxView.this.mContext.getApplicationContext().getString(R.string.creation_page_unbounded_change_limit), 0).show();
                                                            return;
                                                        }
                                                        pageSizeConfig2.isUnLimit = true;
                                                        pageSizeConfig2.width = 867;
                                                        pageSizeConfig2.orientation = 1;
                                                        pageSizeConfig2.pageType = 6;
                                                    }
                                                } else {
                                                    pageSizeConfig2.pageType = 5;
                                                    pageSizeConfig2.height = 1156;
                                                    pageSizeConfig2.width = 1156;
                                                    pageSizeConfig2.isUnLimit = false;
                                                    pageSizeConfig2.type = 0;
                                                }
                                            } else {
                                                pageSizeConfig2.pageType = 3;
                                                pageSizeConfig2.width = 650;
                                                pageSizeConfig2.height = 1156;
                                                pageSizeConfig2.isUnLimit = false;
                                                pageSizeConfig2.orientation = 1;
                                                pageSizeConfig2.type = 0;
                                            }
                                        } else {
                                            pageSizeConfig2.pageType = 4;
                                            pageSizeConfig2.width = 650;
                                            pageSizeConfig2.height = 1156;
                                            pageSizeConfig2.isUnLimit = false;
                                            pageSizeConfig2.orientation = 0;
                                            pageSizeConfig2.type = 0;
                                        }
                                    } else {
                                        pageSizeConfig2.pageType = 1;
                                        pageSizeConfig2.width = 755;
                                        pageSizeConfig2.height = 1156;
                                        pageSizeConfig2.isUnLimit = false;
                                        pageSizeConfig2.orientation = 1;
                                        pageSizeConfig2.type = 0;
                                    }
                                } else {
                                    pageSizeConfig2.pageType = 2;
                                    pageSizeConfig2.width = 755;
                                    pageSizeConfig2.height = 1156;
                                    pageSizeConfig2.isUnLimit = false;
                                    pageSizeConfig2.orientation = 0;
                                    pageSizeConfig2.type = 0;
                                }
                                MiuiToolBoxView.this.adjustPageSize(pageSizeConfig2, pageSizeConfig, false);
                                return;
                            }
                            Intent intent = new Intent();
                            intent.setAction(Constants.ADJUST_PAGE_SIZE_INTENT);
                            pageSizeConfig2.pageType = 7;
                            if (pageSizeConfig2.isUnLimit) {
                                intent.putExtra("pageWidth", UIUtils.getRealWidth(MiuiToolBoxView.this.mContext));
                                intent.putExtra("pageHeight", UIUtils.getRealHeight(MiuiToolBoxView.this.mContext));
                                intent.putExtra("pageUnit", 1);
                            } else if (pageSizeConfig2.type == 1) {
                                int i5 = pageSizeConfig2.width;
                                int i6 = pageSizeConfig2.height;
                                intent.putExtra("pageWidth", i5);
                                intent.putExtra("pageHeight", i6);
                                intent.putExtra("pageUnit", 1);
                            } else if (pageSizeConfig2.type == 2) {
                                int i7 = pageSizeConfig2.width;
                                int i8 = pageSizeConfig2.height;
                                intent.putExtra("pageWidth", i7);
                                intent.putExtra("pageHeight", i8);
                                intent.putExtra("pageUnit", 2);
                            } else {
                                int i9 = pageSizeConfig2.standardWidth;
                                int i10 = (pageSizeConfig2.height * i9) / pageSizeConfig2.width;
                                intent.putExtra("pageWidth", i9);
                                intent.putExtra("pageHeight", i10);
                                intent.putExtra("pageUnit", 1);
                            }
                            LocalBroadcastManager.getInstance(MiuiToolBoxView.this.mContext).sendBroadcast(intent);
                            MiuiToolBoxView.this.mEditMoreActionPopupWindow.dismiss(false);
                        }
                    }
                });
            }
        }

        /* JADX INFO: renamed from: lambda$call$0$com-miui-handwriting-common-view-toolbar-MiuiToolBoxView$7, reason: not valid java name */
        /* synthetic */ void m818x6bfad4af(DialogInterface dialogInterface, int i) {
            if (MiuiToolBoxView.this.mPageInkViewModel.getPageCount() > 1) {
                ArrayList<Integer> arrayList = new ArrayList<>();
                arrayList.add(Integer.valueOf(MiuiToolBoxView.this.mPageInkViewModel.getCurrentPosition()));
                MiuiToolBoxView.this.mPageInkViewModel.deletePage(arrayList);
            }
        }

        @Override // com.miui.handwriting.common.view.popup.MoreActionPopupWindow.ActionCallBack
        public void callBackground(int i, int i2) {
            if (MiuiToolBoxView.this.actionCallBack != null) {
                MiuiToolBoxView.this.actionCallBack.callBackground(new BackgroundWrapper(i, i2));
            }
        }
    }

    /* JADX INFO: renamed from: lambda$initEditMorePopupWindow$7$com-miui-handwriting-common-view-toolbar-MiuiToolBoxView, reason: not valid java name */
    /* synthetic */ void m806x1bb6e040(int i) {
        IMiuiHandWritingModel iMiuiHandWritingModel = this.mPageInkViewModel;
        if (iMiuiHandWritingModel != null) {
            iMiuiHandWritingModel.onTitleColorChanged(i, -1);
        }
    }

    /* JADX INFO: renamed from: lambda$initEditMorePopupWindow$8$com-miui-handwriting-common-view-toolbar-MiuiToolBoxView, reason: not valid java name */
    /* synthetic */ void m807xa8a3f75f(boolean z) {
        this.mPageInkViewModel.setOnlyPenWrite(!z);
    }

    /* JADX INFO: renamed from: lambda$initEditMorePopupWindow$9$com-miui-handwriting-common-view-toolbar-MiuiToolBoxView, reason: not valid java name */
    /* synthetic */ void m808x35910e7e(CompoundButton compoundButton, boolean z) {
        this.mPageInkViewModel.setDoubleFingerClickUndo(z);
        HistorySet.setIsDoubleClickUndo(z);
    }

    public void setDefaultItem(PageSizeConfig pageSizeConfig, int i) {
        int i2 = pageSizeConfig.width;
        int i3 = pageSizeConfig.orientation;
        Log.d(TAG, "setDefaultItem " + pageSizeConfig + ";  pageCount " + i);
        this.mDropDownSingleChoiceMenu.setCustomSelected(false);
        if (pageSizeConfig.isUnLimit) {
            this.mDropDownSingleChoiceMenu.setSelectedItem(0);
            return;
        }
        if (i > 1) {
            this.mDropDownSingleChoiceMenu.setUnlimitedDisable(true);
        } else {
            this.mDropDownSingleChoiceMenu.setUnlimitedDisable(false);
        }
        if (pageSizeConfig.type != 0) {
            this.mDropDownSingleChoiceMenu.setSelectedItem(6);
            this.mDropDownSingleChoiceMenu.setCustomSelected(true);
            return;
        }
        int i4 = pageSizeConfig.pageType;
        if (i4 != 0) {
            if (i4 == 1) {
                this.mDropDownSingleChoiceMenu.setSelectedItem(2);
                return;
            }
            if (i4 != 2) {
                if (i4 == 3) {
                    this.mDropDownSingleChoiceMenu.setSelectedItem(4);
                    return;
                }
                if (i4 == 4) {
                    this.mDropDownSingleChoiceMenu.setSelectedItem(3);
                    return;
                }
                if (i4 == 5) {
                    this.mDropDownSingleChoiceMenu.setSelectedItem(5);
                    return;
                } else {
                    if (i4 != 7) {
                        return;
                    }
                    this.mDropDownSingleChoiceMenu.setSelectedItem(6);
                    this.mDropDownSingleChoiceMenu.setCustomSelected(true);
                    return;
                }
            }
        } else if (i3 == 0) {
            if (i2 == 755) {
                this.mDropDownSingleChoiceMenu.setSelectedItem(1);
            } else if (i2 == 650) {
                this.mDropDownSingleChoiceMenu.setSelectedItem(3);
            } else if (i2 == 1156) {
                this.mDropDownSingleChoiceMenu.setSelectedItem(5);
            } else {
                this.mDropDownSingleChoiceMenu.setSelectedItem(6);
                this.mDropDownSingleChoiceMenu.setCustomSelected(true);
            }
        } else if (i2 == 755) {
            this.mDropDownSingleChoiceMenu.setSelectedItem(2);
        } else if (i2 == 650) {
            this.mDropDownSingleChoiceMenu.setSelectedItem(4);
        } else if (i2 == 1156) {
            this.mDropDownSingleChoiceMenu.setSelectedItem(5);
        } else {
            this.mDropDownSingleChoiceMenu.setSelectedItem(6);
            this.mDropDownSingleChoiceMenu.setCustomSelected(true);
        }
        this.mDropDownSingleChoiceMenu.setSelectedItem(1);
    }

    @Override // com.miui.handwriting.common.view.toolbar.IMiuiHandToolbar
    public void adjustPageSize(final PageSizeConfig pageSizeConfig, final PageSizeConfig pageSizeConfig2, final boolean z) {
        Log.d(TAG, "adjustPageSize pageSizeConfig :" + pageSizeConfig + ";  undoConfig " + pageSizeConfig2 + "; isFromCancel " + z);
        IMiuiHandWritingModel iMiuiHandWritingModel = this.mPageInkViewModel;
        if (iMiuiHandWritingModel.isContentEmpty(iMiuiHandWritingModel.getCurrentPosition()) || (pageSizeConfig.isUnLimit && !pageSizeConfig2.isUnLimit)) {
            this.mPageInkViewModel.addChangePageSizeStep(pageSizeConfig, pageSizeConfig2);
            this.mPageInkViewModel.setPageSizeConfig(pageSizeConfig, z);
            this.mEditMoreActionPopupWindow.dismiss(false);
            return;
        }
        AlertDialog.Builder builder = new AlertDialog.Builder(this.mContext);
        builder.setTitle(R.string.creation_page_size_dialog_title);
        builder.setMessage(R.string.creation_page_size_dialog_message);
        builder.setPositiveButton(R.string.creation_page_size_dialog_positive, new DialogInterface.OnClickListener() { // from class: com.miui.handwriting.common.view.toolbar.MiuiToolBoxView$$ExternalSyntheticLambda10
            @Override // android.content.DialogInterface.OnClickListener
            public final void onClick(DialogInterface dialogInterface, int i) {
                this.f$0.m802x86c5e49c(pageSizeConfig, pageSizeConfig2, z, dialogInterface, i);
            }
        });
        builder.setNegativeButton(R.string.creation_edit_limit_dialog_cancel, (DialogInterface.OnClickListener) null);
        this.mEditMoreActionPopupWindow.dismiss(false);
        AlertDialog alertDialogCreate = builder.create();
        this.mPageSizeDialog = alertDialogCreate;
        alertDialogCreate.show();
    }

    /* JADX INFO: renamed from: lambda$adjustPageSize$10$com-miui-handwriting-common-view-toolbar-MiuiToolBoxView, reason: not valid java name */
    /* synthetic */ void m802x86c5e49c(PageSizeConfig pageSizeConfig, PageSizeConfig pageSizeConfig2, boolean z, DialogInterface dialogInterface, int i) {
        this.mPageInkViewModel.addChangePageSizeStep(pageSizeConfig, pageSizeConfig2);
        this.mPageInkViewModel.setPageSizeConfig(pageSizeConfig, z);
    }

    @Override // com.miui.handwriting.common.view.toolbar.IMiuiHandToolbar
    public void setFocus(boolean z) {
        setFocusable(z);
        setFocusableInTouchMode(z);
        if (z) {
            requestFocus();
        }
    }

    @Override // com.miui.handwriting.common.view.toolbar.IMiuiHandToolbar
    public PageSizeConfig getPageSizeConfig(int i, boolean z) {
        List<PageItemData> itemDataList = this.mPageInkViewModel.getItemDataList();
        if (itemDataList == null || itemDataList.size() == 0 || i >= itemDataList.size() || i < 0) {
            return new PageSizeConfig(755, 1156, 0);
        }
        PageSizeConfig pageSizeConfig = new PageSizeConfig();
        PageItemData pageItemData = itemDataList.get(i);
        pageSizeConfig.fixedSize = (TextUtils.isEmpty(pageItemData.pdfId) && TextUtils.isEmpty(pageItemData.bgImage)) ? false : true;
        pageSizeConfig.orientation = pageItemData.orientation;
        pageSizeConfig.type = pageItemData.sizeUnit;
        if (pageSizeConfig.type == 0) {
            pageSizeConfig.width = pageItemData.orientation == 0 ? pageItemData.width : pageItemData.height;
            pageSizeConfig.height = pageItemData.orientation == 0 ? pageItemData.height : pageItemData.width;
        } else {
            pageSizeConfig.width = pageItemData.width;
            pageSizeConfig.height = pageItemData.height;
        }
        pageSizeConfig.isUnLimit = z;
        pageSizeConfig.position = i;
        pageSizeConfig.maxWidth = pageItemData.maxWidth;
        pageSizeConfig.maxHeight = pageItemData.maxHeight;
        pageSizeConfig.exportLeft = pageItemData.exportLeft;
        pageSizeConfig.exportTop = pageItemData.exportTop;
        pageSizeConfig.exportRight = pageItemData.exportRight;
        pageSizeConfig.exportBottom = pageItemData.exportBottom;
        pageSizeConfig.standardWidth = pageItemData.standardWidth;
        pageSizeConfig.fixWidth = pageItemData.fixWidth;
        pageSizeConfig.isFixSize = pageItemData.isFixSize;
        pageSizeConfig.pageType = pageItemData.pageSizeType;
        return pageSizeConfig;
    }

    private void darkBackground(float f) {
        Activity activity = Utils.getActivity(this.mContext);
        WindowManager.LayoutParams attributes = activity.getWindow().getAttributes();
        attributes.alpha = f;
        activity.getWindow().addFlags(2);
        activity.getWindow().setAttributes(attributes);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void insertRichText() {
        this.mPageInkViewModel.addText(getTextLocation(getResources().getDimensionPixelSize(R.dimen.creation_insert_rich_text_default_width), getResources().getDimensionPixelSize(R.dimen.creation_insert_rich_text_default_height)));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void insertShape() {
        float f;
        RectF textLocation = getTextLocation(250, 250);
        ShapeProp shapePropConvert = ShapeEditSet.getCurShape().convert();
        PageItemView currentItemView = this.mPageInkViewModel.getCurrentItemView();
        if (this.mPageInkViewModel.invalidPosition()) {
            return;
        }
        RectF layout = currentItemView.getLayout();
        IMiuiHandWritingModel iMiuiHandWritingModel = this.mPageInkViewModel;
        RectF itemVisibleRectF = iMiuiHandWritingModel.getItemVisibleRectF(iMiuiHandWritingModel.getCurrentPosition());
        RectF rectF = new RectF(textLocation);
        rectF.offset(layout.left, layout.top);
        if (!itemVisibleRectF.contains(rectF)) {
            if (textLocation.top < 0.0f) {
                f = -textLocation.top;
            } else {
                f = layout.top > textLocation.top ? textLocation.top - layout.top : 0.0f;
            }
            this.mPageInkViewModel.scrollBy(0, (int) f);
            this.mPageInkViewModel.updateCurrentViewItem(layout.left + 1.0f, layout.top + 1.0f);
            textLocation.offset(0.0f, Math.max(0.0f, f));
            currentItemView.insertShapeProp(new RectF(textLocation), shapePropConvert);
        } else {
            this.mPageInkViewModel.updateCurrentViewItem(layout.left + 1.0f, layout.top + 1.0f);
            currentItemView.insertShapeProp(new RectF(textLocation), shapePropConvert);
        }
        if (PenEngine.isEmptyCreationFromCreate()) {
            PenEngine.setIsEmptyCreationFromCreate(false);
        }
        this.mPageInkViewModel.finishSelect();
        this.mPageInkViewModel.getEngineConfig().shapeProp = shapePropConvert;
    }

    public RectF getTextLocation(int i, int i2) {
        RectF itemRectF = this.mPageInkViewModel.getItemRectF();
        RectF visibleRectF = this.mPageInkViewModel.getVisibleRectF();
        float fWidth = visibleRectF.left;
        float fHeight = visibleRectF.top;
        float f = i;
        if (visibleRectF.width() >= f) {
            fWidth = ((visibleRectF.width() - f) / 2.0f) + visibleRectF.left;
        } else if (itemRectF.width() > f && itemRectF.left < 0.0f && f > itemRectF.right) {
            fWidth = itemRectF.right - f;
        }
        float f2 = i2;
        if (visibleRectF.height() >= f2) {
            fHeight = ((visibleRectF.height() - f2) / 2.0f) + visibleRectF.top;
        } else if (itemRectF.height() > f2 && itemRectF.top < 0.0f && f2 > itemRectF.bottom) {
            fHeight = itemRectF.bottom - f2;
        }
        return new RectF(fWidth, fHeight, f + fWidth, f2 + fHeight);
    }

    private void initColorView(boolean z) {
        if (z) {
            this.mColorView.setVisibility(0);
            this.mLeftColorView.setVisibility(8);
            this.mCenterColorView.setVisibility(8);
            this.mRightColorView.setVisibility(8);
            return;
        }
        this.mColorView.setVisibility(8);
        this.mLeftColorView.setVisibility(0);
        Activity activity = Utils.getActivity(this.mContext);
        if (activity != null && activity.isInMultiWindowMode() && this.mContext.getResources().getConfiguration().smallestScreenWidthDp > 700) {
            Log.i(TAG, "getActivity(mContext).isInMultiWindowMode()=" + activity.isInMultiWindowMode() + ", mContext.getResources().getConfiguration().smallestScreenWidthDp=" + this.mContext.getResources().getConfiguration().smallestScreenWidthDp);
            this.mLeftColorView.setLayoutParams((LinearLayout.LayoutParams) this.mLeftColorView.getLayoutParams());
        }
        this.mCenterColorView.setVisibility(0);
        this.mRightColorView.setVisibility(0);
    }

    private void initToolView(boolean z) {
        if (z) {
            this.mToolShare.setVisibility(8);
        } else {
            this.mToolShare.setVisibility(0);
        }
    }

    private void initBrushView(boolean z) {
        if (z) {
            this.mBrushSelect.setVisibility(0);
            this.mBrushBallpoint.setVisibility(8);
            this.mBrushPencil.setVisibility(8);
            this.mBrushInk.setVisibility(8);
            this.mBrushMarkPen.setVisibility(8);
            this.mBrushWaterColor.setVisibility(8);
            this.mToolAI.setVisibility(8);
            return;
        }
        this.mBrushSelect.setVisibility(8);
        this.mBrushBallpoint.setVisibility(0);
        this.mBrushPencil.setVisibility(0);
        this.mBrushInk.setVisibility(0);
        this.mBrushMarkPen.setVisibility(0);
        this.mBrushWaterColor.setVisibility(0);
        if (PreferenceUtils.getAIBrushPermission()) {
            this.mToolAI.setVisibility(8);
        } else {
            this.mToolAI.setVisibility(8);
        }
    }

    private void createPopupWindows(Context context) {
        ColorPickerPopupWindow colorPickerPopupWindow = new ColorPickerPopupWindow(context);
        this.mPenColorPopupWindow = colorPickerPopupWindow;
        colorPickerPopupWindow.initUi();
        this.mPenColorPopupWindow.setArrowMode(8);
        PenSetPopupWindow penSetPopupWindow = new PenSetPopupWindow(this.mContext, false, this.mPageInkViewModel.isUnlimited());
        this.mPenSetPopupWindow = penSetPopupWindow;
        penSetPopupWindow.setPenSet(this.mPenSet);
        PenSetPopupWindow penSetPopupWindow2 = new PenSetPopupWindow(this.mContext, true, this.mPageInkViewModel.isUnlimited());
        this.mPenSizePopupWindow = penSetPopupWindow2;
        penSetPopupWindow2.setPenSet(this.mPenSet);
        this.mAIPenPopWindow = new AIPenPopupWindow(this.mContext);
        MoreActionPopupWindow moreActionPopupWindow = new MoreActionPopupWindow(this.mContext);
        this.mEditMoreActionPopupWindow = moreActionPopupWindow;
        moreActionPopupWindow.setTouchInterceptor(new View.OnTouchListener() { // from class: com.miui.handwriting.common.view.toolbar.MiuiToolBoxView.8
            @Override // android.view.View.OnTouchListener
            public boolean onTouch(View view, MotionEvent motionEvent) {
                return MiuiToolBoxView.this.mPageWindowShowing;
            }
        });
        this.mPageWindowShowing = false;
        this.mEraserPopupWindow = new EraserPopupWindow(this.mContext);
        initDialogKeyListener();
    }

    private int[] getColorArray(int i) {
        return this.mContext.getResources().getIntArray(i);
    }

    private boolean isSimpleMode() {
        Intent intent;
        try {
            Activity activity = Utils.getActivity(getContext());
            return activity != null && (intent = activity.getIntent()) != null && intent.getBooleanExtra("limited_toolbar", false) && intent.getBooleanExtra("external_launch", false);
        } catch (Exception unused) {
        }
    }

    private void initDefaultColorArray() {
        this.mPenSet.getPenBeanByType(PenType.INK).setDefaultHSLArray(getColorArray(R.array.creation_ink_default_color_item));
        this.mPenSet.getPenBeanByType(PenType.PENCIL).setDefaultHSLArray(getColorArray(R.array.creation_pencil_default_color_item));
        this.mPenSet.getPenBeanByType(PenType.BALL).setDefaultHSLArray(getColorArray(R.array.creation_ball_default_color_item));
        this.mPenSet.getPenBeanByType(PenType.MARK).setDefaultHSLArray(getColorArray(R.array.creation_mark_pen_default_color_item));
        this.mPenSet.getPenBeanByType(PenType.WATER_COLOR).setDefaultHSLArray(getColorArray(R.array.creation_watercolor_default_color_item));
    }

    private void initPenBean(PenType penType) {
        PenBean penBeanFromLocal = getPenBeanFromLocal(penType);
        if (penBeanFromLocal != null) {
            this.mPenSet.updatePenBean(penBeanFromLocal);
            return;
        }
        if (penType == PenType.INK) {
            this.mPenSet.getPenBeanByType(PenType.INK).setDefaultColorArray(getColorArray(R.array.creation_ink_default_color_item));
            this.mPenSet.getPenBeanByType(PenType.INK).setPenDefaultColor();
            return;
        }
        if (penType == PenType.PENCIL) {
            this.mPenSet.getPenBeanByType(PenType.PENCIL).setDefaultColorArray(getColorArray(R.array.creation_pencil_default_color_item));
            this.mPenSet.getPenBeanByType(PenType.PENCIL).setPenDefaultColor();
            return;
        }
        if (penType == PenType.BALL) {
            this.mPenSet.getPenBeanByType(PenType.BALL).setDefaultColorArray(getColorArray(R.array.creation_ball_default_color_item));
            this.mPenSet.getPenBeanByType(PenType.BALL).setPenDefaultColor();
        } else if (penType == PenType.MARK) {
            this.mPenSet.getPenBeanByType(PenType.MARK).setDefaultColorArray(getColorArray(R.array.creation_mark_pen_default_color_item));
            this.mPenSet.getPenBeanByType(PenType.MARK).setPenDefaultColor();
        } else if (penType == PenType.WATER_COLOR) {
            this.mPenSet.getPenBeanByType(PenType.WATER_COLOR).setDefaultColorArray(getColorArray(R.array.creation_watercolor_default_color_item));
            this.mPenSet.getPenBeanByType(PenType.WATER_COLOR).setPenDefaultColor();
        }
    }

    private boolean isCalculatorMode() {
        Intent intent;
        try {
            Activity currentActivity = getCurrentActivity();
            if (currentActivity != null && (intent = currentActivity.getIntent()) != null) {
                boolean z = intent.getBooleanExtra("from_calculator", false) || intent.getBooleanExtra("external_launch", false) || intent.getBooleanExtra("limited_toolbar", false) || "calculator".equals(intent.getStringExtra("source")) || intent.hasExtra("calculator_extra");
                Log.d("APP_ID", "Intent: " + z + ", extras: " + intent.getExtras());
                return z;
            }
        } catch (Exception e) {
            Log.e("APP_ID", "Intent检测失败", e);
        }
        return false;
    }

    private Activity getCurrentActivity() {
        Context context = this.mContext;
        if (context instanceof Activity) {
            return (Activity) context;
        }
        if (!(context instanceof ContextWrapper)) {
            return null;
        }
        Context baseContext = ((ContextWrapper) context).getBaseContext();
        if (baseContext instanceof Activity) {
            return (Activity) baseContext;
        }
        return null;
    }

    private void initPenData() {
        SharedPreferences sharedPreferences = getContext().getSharedPreferences(Constants.CREATION_SHPREF, 0);
        this.mHasChangeColor = sharedPreferences.getBoolean(Constants.USER_HAS_CHANGED_PENCIL_CENTER_COLOR, false);
        if (RomUtils.isUseNewCreationAI()) {
            this.aiType = sharedPreferences.getInt(Constants.AI_TYPE, 0);
        } else {
            this.aiType = sharedPreferences.getInt(Constants.AI_TYPE, 1);
        }
        int i = sharedPreferences.getInt(isCalculatorMode() ? Constants.TOOL_CURRENT_BRUSH_CALC : Constants.TOOL_CURRENT_BRUSH, 0);
        this.mSavedSelectViewType = sharedPreferences.getInt(isCalculatorMode() ? Constants.TOOL_SELECT_STATE_SAVE_CALC : Constants.TOOL_SELECT_STATE_SAVE, 0);
        this.isAiBrush = sharedPreferences.getBoolean(Constants.TOOL_AI_BRUSH, false);
        if (i == 0 && !isCalculatorMode()) {
            initDefaultColorArray();
            this.mPenSet.getPenBeanByType(PenType.INK).setPenDefaultColor();
            this.mPenSet.getPenBeanByType(PenType.PENCIL).setPenDefaultColor();
            this.mPenSet.getPenBeanByType(PenType.BALL).setPenDefaultColor();
            this.mPenSet.getPenBeanByType(PenType.MARK).setPenDefaultColor();
            this.mPenSet.getPenBeanByType(PenType.WATER_COLOR).setPenDefaultColor();
            PenType penType = PenType.PENCIL;
            PenSet penSet = this.mPenSet;
            penSet.setCurPenBean(penSet.getPenBeanByType(penType));
            if (this.mSavedSelectViewType == 0) {
                this.mSavedSelectViewType = 3;
                return;
            }
            return;
        }
        if (i != 0 && !isCalculatorMode()) {
            int i2 = sharedPreferences.getInt(Constants.TOOL_CURRENT_BRUSH, PenType.PENCIL.getValue());
            initPenBean(PenType.INK);
            initPenBean(PenType.PENCIL);
            initPenBean(PenType.BALL);
            initPenBean(PenType.MARK);
            initPenBean(PenType.WATER_COLOR);
            PenType penType2 = PenType.getPenType(i2);
            PenSet penSet2 = this.mPenSet;
            penSet2.setCurPenBean(penSet2.getPenBeanByType(penType2));
            int i3 = this.mSavedSelectViewType;
            if (i3 == 7 || i3 == 6 || i3 == 8) {
                return;
            }
            this.mSavedSelectViewType = penType2.getValue();
            return;
        }
        if (isCalculatorMode()) {
            initDefaultColorArray();
            initPenBean(PenType.INK);
            PenType penType3 = PenType.INK;
            PenSet penSet3 = this.mPenSet;
            penSet3.setCurPenBean(penSet3.getPenBeanByType(penType3));
            if (BRUSH_SIZE_TYPES.contains(Integer.valueOf(this.mSavedSelectViewType)) || this.mSavedSelectViewType == 0) {
                this.mSavedSelectViewType = 1;
            }
        }
    }

    private void initShapeData() {
        if (getContext().getSharedPreferences(Constants.CREATION_SHPREF, 0).contains(Constants.SHAPE_ARROW)) {
            ShapeEditSet.updateShapeEditBean(ShapeEditBean.getShapeEditBeanFromLocal(getContext(), 2));
            ShapeEditSet.updateShapeEditBean(ShapeEditBean.getShapeEditBeanFromLocal(getContext(), 6));
            ShapeEditSet.updateShapeEditBean(ShapeEditBean.getShapeEditBeanFromLocal(getContext(), 7));
            ShapeEditSet.updateShapeEditBean(ShapeEditBean.getShapeEditBeanFromLocal(getContext(), 4));
            ShapeEditSet.updateShapeEditBean(ShapeEditBean.getShapeEditBeanFromLocal(getContext(), 3));
            ShapeEditSet.updateShapeEditBean(ShapeEditBean.getShapeEditBeanFromLocal(getContext(), 5));
            return;
        }
        ShapeEditSet.initDefaultColorArray(getContext());
        ShapeEditSet.initDefaultColor();
    }

    public void initialBrush() {
        Log.i(TAG, "initialBrush mPenPropType=");
        createPopupWindows(this.mContext);
        View toolViewByType = getToolViewByType(this.mSavedSelectViewType);
        toolViewByType.setTranslationY(0.0f);
        int i = this.mSavedSelectViewType;
        if (i == 6) {
            enableSelect();
        } else if (i == 7) {
            enableEraser(getCurrentEraserPen());
        } else if (i == 8) {
            enableAI();
        }
        this.mCurrentSelectToolView = toolViewByType;
        if (this.mBrushSelect.getVisibility() == 0) {
            updateSelectBrushParams();
            if (!isToolsSelected() || isAiSelected()) {
                this.mBrushSelect.setTranslationY(0.0f);
                View view = this.mBrushSelect;
                this.mCurrentSelectToolView = view;
                this.mCurrentMultiSelectToolView = view;
            }
        }
        setColorToolParams();
    }

    private void setColorToolParams() {
        PenBean currentPenBen = getCurrentPenBen();
        this.mLeftColorView.setCheck(currentPenBen.getCheckedItemIndex() == 0);
        this.mLeftColorView.setHSLColor(currentPenBen.getDefaultHSLArray()[0]);
        this.mCenterColorView.setCheck(currentPenBen.getCheckedItemIndex() == 1);
        this.mCenterColorView.setHSLColor(currentPenBen.getDefaultHSLArray()[1]);
        this.mRightColorView.setCheck(currentPenBen.getCheckedItemIndex() == 2);
        this.mRightColorView.setHSLColor(currentPenBen.getDefaultHSLArray()[2]);
        if (currentPenBen.getCheckedItemIndex() == 0) {
            this.mCurrentSelectColorView = this.mLeftColorView;
        } else if (currentPenBen.getCheckedItemIndex() == 1) {
            this.mCurrentSelectColorView = this.mCenterColorView;
        } else if (currentPenBen.getCheckedItemIndex() == 2) {
            this.mCurrentSelectColorView = this.mRightColorView;
        }
        this.mColorView.setCheck(true);
        this.mColorView.setHSLColor(this.mCurrentSelectColorView.getHSLColor());
    }

    private void initToolBoxViewData() {
        Log.e(TAG, "initToolBoxViewData");
        PenSet penSet = this.mPenSet;
        if (penSet != null && penSet.getCurPenBean() != null) {
            updateSelectBrushView(true, this.mPenSet.getCurPenBean().getPenColor(), getBrushName(this.mPenSet.getCurPenBean().getPenType()));
            updatePencilView(true, this.mPenSet.getPenBeanByType(PenType.PENCIL).getPenColor());
            updateInkView(true, this.mPenSet.getPenBeanByType(PenType.INK).getPenColor());
            updateMarkPenView(true, this.mPenSet.getPenBeanByType(PenType.MARK).getPenColor());
            updateWaterColorPenView(true, this.mPenSet.getPenBeanByType(PenType.WATER_COLOR).getPenColor());
            updateBallpointView(true, this.mPenSet.getPenBeanByType(PenType.BALL).getPenColor());
        }
        updateEraserView(true);
        updateSelectView(true);
        updateAIView(true);
        updateRulerView(true);
    }

    private void initToolBoxViewData(Configuration configuration) {
        Log.e(TAG, "initToolBoxViewData");
        PenSet penSet = this.mPenSet;
        if (penSet != null && penSet.getCurPenBean() != null) {
            updateSelectBrushView(true, this.mPenSet.getCurPenBean().getPenColor(), getBrushName(this.mPenSet.getCurPenBean().getPenType()), configuration);
            updatePencilView(true, this.mPenSet.getPenBeanByType(PenType.PENCIL).getPenColor(), configuration);
            updateInkView(true, this.mPenSet.getPenBeanByType(PenType.INK).getPenColor(), configuration);
            updateMarkPenView(true, this.mPenSet.getPenBeanByType(PenType.MARK).getPenColor(), configuration);
            updateWaterColorPenView(true, this.mPenSet.getPenBeanByType(PenType.WATER_COLOR).getPenColor(), configuration);
            updateBallpointView(true, this.mPenSet.getPenBeanByType(PenType.BALL).getPenColor(), configuration);
        }
        updateEraserView(true, configuration);
        updateSelectView(true, configuration);
        updateAIView(true, configuration);
        updateRulerView(true, configuration);
        applyLimitedToolbarIfNeeded();
    }

    private void applyLimitedToolbarIfNeeded() {
        if (this.mLimitedToolbar) {
            View view = this.mBrushPencil;
            if (view != null) {
                view.setVisibility(8);
            }
            View view2 = this.mBrushBallpoint;
            if (view2 != null) {
                view2.setVisibility(8);
            }
            View view3 = this.mBrushMarkPen;
            if (view3 != null) {
                view3.setVisibility(8);
            }
            View view4 = this.mBrushWaterColor;
            if (view4 != null) {
                view4.setVisibility(8);
            }
            View view5 = this.mToolAI;
            if (view5 != null) {
                view5.setVisibility(8);
            }
            View view6 = this.mToolRuler;
            if (view6 != null) {
                view6.setVisibility(8);
            }
            View view7 = this.mBrushSelect;
            if (view7 != null) {
                view7.setVisibility(8);
            }
            View view8 = this.mBrushInk;
            if (view8 != null) {
                view8.setVisibility(0);
            }
            View view9 = this.mToolRectSelect;
            if (view9 != null) {
                view9.setVisibility(0);
            }
            View view10 = this.mBrushEraser;
            if (view10 != null) {
                view10.setVisibility(0);
            }
            View view11 = this.mToolUndo;
            if (view11 != null) {
                view11.setVisibility(0);
            }
            View view12 = this.mToolRedo;
            if (view12 != null) {
                view12.setVisibility(0);
            }
            if (this.mToolMore != null) {
                changeCleanImageResource();
                this.mToolMore.setVisibility(0);
            }
        }
    }

    private void changeCleanImageResource() {
        int i = R.drawable.miui_penengine_action_clean;
        if (UIUtils.isNightMode()) {
            i = R.drawable.miui_penengine_action_clean_night;
        }
        View view = this.mToolMore;
        if (view != null) {
            ((ImageView) view).setImageResource(i);
        }
    }

    private MiuiDeleteProp getEraseDeleteFromLocal() {
        MiuiDeleteProp miuiDeleteProp = (MiuiDeleteProp) GsonUtils.getGsonInstance().fromJson(getContext().getSharedPreferences(Constants.CREATION_SHPREF, 0).getString(Constants.TOOL_ERASER_DELETE, ""), MiuiDeleteProp.class);
        return miuiDeleteProp == null ? getCurrentEraserPen() : miuiDeleteProp;
    }

    private PenBean getPenBeanFromLocal(PenType penType) {
        String string;
        HSLColor[] defaultHSLArray;
        SharedPreferences sharedPreferences = getContext().getSharedPreferences(Constants.CREATION_SHPREF, 0);
        int i = AnonymousClass16.$SwitchMap$xc$engine$core$graphics$pen$PenType[penType.ordinal()];
        if (i == 1) {
            string = sharedPreferences.getString(isCalculatorMode() ? Constants.TOOL_BRUSH_INK_CALC : Constants.TOOL_BRUSH_INK, "");
        } else if (i == 2) {
            string = sharedPreferences.getString(Constants.TOOL_BRUSH_MARKPEN, "");
        } else if (i == 3) {
            string = sharedPreferences.getString(Constants.TOOL_BRUSH_BALLPOINT, "");
        } else if (i == 4) {
            string = sharedPreferences.getString(Constants.TOOL_BRUSH_WATERCOLOR, "");
        } else {
            string = sharedPreferences.getString(Constants.TOOL_BRUSH_PENCIL, "");
        }
        PenBean penBean = (PenBean) GsonUtils.getGsonInstance().fromJson(string, PenBean.class);
        if (penBean != null && penBean.getDefaultHSLArray() == null) {
            penBean.setDefaultHSLArray(penBean.getDefaultColorArray());
        }
        if (penBean != null && (defaultHSLArray = penBean.getDefaultHSLArray()) != null) {
            int i2 = 0;
            for (int i3 = 0; i3 < defaultHSLArray.length; i3++) {
                i2 = (int) (i2 + defaultHSLArray[i3].color + defaultHSLArray[i3].alpha + defaultHSLArray[i3].hue + defaultHSLArray[i3].lightness + defaultHSLArray[i3].saturation);
            }
            if (i2 == 0) {
                penBean.setDefaultHSLArray(penBean.getDefaultColorArray());
            }
        }
        return penBean;
    }

    /* JADX INFO: renamed from: com.miui.handwriting.common.view.toolbar.MiuiToolBoxView$16, reason: invalid class name */
    static /* synthetic */ class AnonymousClass16 {
        static final /* synthetic */ int[] $SwitchMap$xc$engine$core$graphics$pen$PenType;

        static {
            int[] iArr = new int[PenType.values().length];
            $SwitchMap$xc$engine$core$graphics$pen$PenType = iArr;
            try {
                iArr[PenType.INK.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$xc$engine$core$graphics$pen$PenType[PenType.MARK.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                $SwitchMap$xc$engine$core$graphics$pen$PenType[PenType.BALL.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                $SwitchMap$xc$engine$core$graphics$pen$PenType[PenType.WATER_COLOR.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
            try {
                $SwitchMap$xc$engine$core$graphics$pen$PenType[PenType.PENCIL.ordinal()] = 5;
            } catch (NoSuchFieldError unused5) {
            }
        }
    }

    private String getBrushName(PenType penType) {
        if (PenType.BALL == penType) {
            return Constants.TOOL_BRUSH_BALLPOINT;
        }
        if (PenType.INK == penType) {
            return Constants.TOOL_BRUSH_INK;
        }
        if (PenType.MARK == penType) {
            return Constants.TOOL_BRUSH_MARKPEN;
        }
        return (PenType.PENCIL != penType && PenType.WATER_COLOR == penType) ? Constants.TOOL_BRUSH_WATERCOLOR : Constants.TOOL_BRUSH_PENCIL;
    }

    private void updateSelectBrushView(boolean z, int i, String str) {
        if (this.isAiBrush) {
            updateToolViewState(this.mBrushSelect, Constants.TOOL_AI_SELECT, z);
        } else {
            updateBrushViewState(this.mBrushSelect, str, i, z);
        }
    }

    private void updateSelectBrushView(boolean z, int i, String str, Configuration configuration) {
        if (this.isAiBrush) {
            updateToolViewState(this.mBrushSelect, Constants.TOOL_AI_SELECT, configuration);
        } else {
            updateBrushViewState(this.mBrushSelect, str, i, configuration);
        }
    }

    private void updatePencilView(boolean z, int i) {
        updateBrushViewState(this.mBrushPencil, Constants.TOOL_BRUSH_PENCIL, i, z);
    }

    private void updatePencilView(boolean z, int i, Configuration configuration) {
        updateBrushViewState(this.mBrushPencil, Constants.TOOL_BRUSH_PENCIL, i, configuration);
    }

    private void updateBallpointView(boolean z, int i) {
        updateBrushViewState(this.mBrushBallpoint, Constants.TOOL_BRUSH_BALLPOINT, i, z);
    }

    private void updateBallpointView(boolean z, int i, Configuration configuration) {
        updateBrushViewState(this.mBrushBallpoint, Constants.TOOL_BRUSH_BALLPOINT, i, configuration);
    }

    private void updateInkView(boolean z, int i) {
        updateBrushViewState(this.mBrushInk, Constants.TOOL_BRUSH_INK, i, z);
    }

    private void updateInkView(boolean z, int i, Configuration configuration) {
        updateBrushViewState(this.mBrushInk, Constants.TOOL_BRUSH_INK, i, configuration);
    }

    private void updateMarkPenView(boolean z, int i) {
        updateBrushViewState(this.mBrushMarkPen, Constants.TOOL_BRUSH_MARKPEN, i, z);
    }

    private void updateMarkPenView(boolean z, int i, Configuration configuration) {
        updateBrushViewState(this.mBrushMarkPen, Constants.TOOL_BRUSH_MARKPEN, i, configuration);
    }

    private void updateWaterColorPenView(boolean z, int i) {
        updateBrushViewState(this.mBrushWaterColor, Constants.TOOL_BRUSH_WATERCOLOR, i, z);
    }

    private void updateWaterColorPenView(boolean z, int i, Configuration configuration) {
        updateBrushViewState(this.mBrushWaterColor, Constants.TOOL_BRUSH_WATERCOLOR, i, configuration);
    }

    private void updateEraserView(boolean z) {
        updateToolViewState(this.mBrushEraser, Constants.TOOL_ERASER, z);
    }

    private void updateEraserView(boolean z, Configuration configuration) {
        updateToolViewState(this.mBrushEraser, Constants.TOOL_ERASER, configuration);
    }

    private void updateSelectView(boolean z) {
        updateToolViewState(this.mToolRectSelect, Constants.TOOL_SELECT, z);
    }

    private void updateSelectView(boolean z, Configuration configuration) {
        updateToolViewState(this.mToolRectSelect, Constants.TOOL_SELECT, configuration);
    }

    private void updateRulerView(boolean z) {
        updateToolViewState(this.mToolRuler, Constants.TOOL_RULER, z);
    }

    private void updateRulerView(boolean z, Configuration configuration) {
        updateToolViewState(this.mToolRuler, Constants.TOOL_RULER, configuration);
    }

    private void updateBrushViewState(View view, String str, int i, boolean z) {
        view.setForeground(createBrushFg(str, i));
        if (!UIUtils.isNightMode()) {
            view.setBackground(createBrushBg(str));
        } else {
            view.setBackground(null);
        }
    }

    private void updateBrushViewState(View view, String str, int i, Configuration configuration) {
        view.setForeground(createBrushFg(str, i));
        if (!UIUtils.isNightMode(configuration)) {
            view.setBackground(createBrushBg(str));
        } else {
            view.setBackground(null);
        }
    }

    private void updateToolViewState(View view, String str, boolean z) {
        if (Constants.TOOL_AI_SELECT.equals(str) && !RomUtils.isUseNewCreationAI()) {
            view.setBackground(createToolBg(this.mContext, Constants.TOOL_OS1_AI_SELECT));
        } else {
            view.setBackground(createToolBg(this.mContext, str));
        }
    }

    private void updateToolViewState(View view, String str, Configuration configuration) {
        if (Constants.TOOL_AI_SELECT.equals(str) && !RomUtils.isUseNewCreationAI()) {
            view.setBackground(createToolBg(this.mContext, Constants.TOOL_OS1_AI_SELECT, configuration));
        } else {
            view.setBackground(createToolBg(this.mContext, str, configuration));
        }
    }

    private void initToolViewSize() {
        ViewGroup.LayoutParams layoutParams = this.mToolParent.getLayoutParams();
        ViewGroup.LayoutParams layoutParams2 = this.mBrushContainer.getLayoutParams();
        ViewGroup.LayoutParams layoutParams3 = this.mScrollContainer.getLayoutParams();
        ViewGroup.LayoutParams layoutParams4 = this.mBrushSelect.getLayoutParams();
        ViewGroup.LayoutParams layoutParams5 = this.mBrushBallpoint.getLayoutParams();
        ViewGroup.LayoutParams layoutParams6 = this.mBrushPencil.getLayoutParams();
        ViewGroup.LayoutParams layoutParams7 = this.mBrushInk.getLayoutParams();
        ViewGroup.LayoutParams layoutParams8 = this.mBrushMarkPen.getLayoutParams();
        ViewGroup.LayoutParams layoutParams9 = this.mBrushWaterColor.getLayoutParams();
        ViewGroup.LayoutParams layoutParams10 = this.mBrushEraser.getLayoutParams();
        ViewGroup.LayoutParams layoutParams11 = this.mToolRectSelect.getLayoutParams();
        ViewGroup.LayoutParams layoutParams12 = this.mToolAI.getLayoutParams();
        ViewGroup.LayoutParams layoutParams13 = this.mToolRuler.getLayoutParams();
        ViewGroup.LayoutParams layoutParams14 = this.mColorView.getLayoutParams();
        ViewGroup.LayoutParams layoutParams15 = this.mLeftColorView.getLayoutParams();
        ViewGroup.LayoutParams layoutParams16 = this.mCenterColorView.getLayoutParams();
        ViewGroup.LayoutParams layoutParams17 = this.mRightColorView.getLayoutParams();
        LinearLayout.LayoutParams layoutParams18 = (LinearLayout.LayoutParams) this.mToolBack.getLayoutParams();
        LinearLayout.LayoutParams layoutParams19 = (LinearLayout.LayoutParams) this.mToolUndo.getLayoutParams();
        LinearLayout.LayoutParams layoutParams20 = (LinearLayout.LayoutParams) this.mToolRedo.getLayoutParams();
        LinearLayout.LayoutParams layoutParams21 = (LinearLayout.LayoutParams) this.mToolShare.getLayoutParams();
        LinearLayout.LayoutParams layoutParams22 = (LinearLayout.LayoutParams) this.mToolMore.getLayoutParams();
        this.mBrushTransY = UIUtils.getToolDimen(this.mContext.getResources().getDimension(R.dimen.creation_tool_select_tran_y));
        layoutParams.height = (int) UIUtils.getToolDimen(this.mContext.getResources().getDimension(R.dimen.creation_tools_head_height));
        layoutParams2.height = (int) UIUtils.getToolDimen(this.mContext.getResources().getDimension(R.dimen.creation_tools_head_height));
        layoutParams3.height = (int) UIUtils.getToolDimen(this.mContext.getResources().getDimension(R.dimen.creation_brush_head_height));
        updateSelectBrushParams();
        layoutParams5.height = (int) UIUtils.getToolDimen(this.mContext.getResources().getDimension(R.dimen.creation_brush_head_height));
        layoutParams5.width = (int) UIUtils.getToolDimen(this.mContext.getResources().getDimension(R.dimen.creation_tool_item_width_thin));
        layoutParams4.height = (int) UIUtils.getToolDimen(this.mContext.getResources().getDimension(R.dimen.creation_brush_head_height));
        layoutParams6.height = (int) UIUtils.getToolDimen(this.mContext.getResources().getDimension(R.dimen.creation_brush_head_height));
        layoutParams6.width = (int) UIUtils.getToolDimen(this.mContext.getResources().getDimension(R.dimen.creation_tool_item_width_thin));
        layoutParams7.height = (int) UIUtils.getToolDimen(this.mContext.getResources().getDimension(R.dimen.creation_brush_head_height));
        layoutParams7.width = (int) UIUtils.getToolDimen(this.mContext.getResources().getDimension(R.dimen.creation_tool_item_width_thin));
        layoutParams8.height = (int) UIUtils.getToolDimen(this.mContext.getResources().getDimension(R.dimen.creation_brush_head_height));
        layoutParams8.width = (int) UIUtils.getToolDimen(this.mContext.getResources().getDimension(R.dimen.creation_tool_item_width_fat));
        layoutParams9.height = (int) UIUtils.getToolDimen(this.mContext.getResources().getDimension(R.dimen.creation_brush_head_height));
        layoutParams9.width = (int) UIUtils.getToolDimen(this.mContext.getResources().getDimension(R.dimen.creation_tool_item_width_thin));
        layoutParams10.height = (int) UIUtils.getToolDimen(this.mContext.getResources().getDimension(R.dimen.creation_brush_head_height));
        layoutParams10.width = (int) UIUtils.getToolDimen(this.mContext.getResources().getDimension(R.dimen.creation_tool_item_width_fat));
        layoutParams11.height = (int) UIUtils.getToolDimen(this.mContext.getResources().getDimension(R.dimen.creation_brush_head_height));
        layoutParams11.width = (int) UIUtils.getToolDimen(this.mContext.getResources().getDimension(R.dimen.creation_tool_item_width_thin));
        layoutParams12.height = (int) UIUtils.getToolDimen(this.mContext.getResources().getDimension(R.dimen.creation_brush_head_height));
        layoutParams12.width = (int) UIUtils.getToolDimen(this.mContext.getResources().getDimension(R.dimen.creation_tool_item_width_thin));
        layoutParams13.height = (int) UIUtils.getToolDimen(this.mContext.getResources().getDimension(R.dimen.creation_brush_head_height));
        layoutParams13.width = (int) UIUtils.getToolDimen(this.mContext.getResources().getDimension(R.dimen.creation_tool_item_width_fat));
        layoutParams14.height = (int) UIUtils.getToolDimen(this.mContext.getResources().getDimension(R.dimen.creation_tool_color_item_size));
        layoutParams14.width = (int) UIUtils.getToolDimen(this.mContext.getResources().getDimension(R.dimen.creation_tool_color_item_size));
        layoutParams15.height = (int) UIUtils.getToolDimen(this.mContext.getResources().getDimension(R.dimen.creation_tool_color_item_size));
        layoutParams15.width = (int) UIUtils.getToolDimen(this.mContext.getResources().getDimension(R.dimen.creation_tool_color_item_size));
        layoutParams16.height = (int) UIUtils.getToolDimen(this.mContext.getResources().getDimension(R.dimen.creation_tool_color_item_size));
        layoutParams16.width = (int) UIUtils.getToolDimen(this.mContext.getResources().getDimension(R.dimen.creation_tool_color_item_size));
        layoutParams17.height = (int) UIUtils.getToolDimen(this.mContext.getResources().getDimension(R.dimen.creation_tool_color_item_size));
        layoutParams17.width = (int) UIUtils.getToolDimen(this.mContext.getResources().getDimension(R.dimen.creation_tool_color_item_size));
        layoutParams18.height = (int) UIUtils.getToolDimen(this.mContext.getResources().getDimension(R.dimen.creation_tool_head_icon_size));
        layoutParams18.width = (int) UIUtils.getToolDimen(this.mContext.getResources().getDimension(R.dimen.creation_tool_head_icon_size));
        layoutParams19.height = (int) UIUtils.getToolDimen(this.mContext.getResources().getDimension(R.dimen.creation_tool_head_icon_size));
        layoutParams19.width = (int) UIUtils.getToolDimen(this.mContext.getResources().getDimension(R.dimen.creation_tool_head_icon_size));
        layoutParams20.height = (int) UIUtils.getToolDimen(this.mContext.getResources().getDimension(R.dimen.creation_tool_head_icon_size));
        layoutParams20.width = (int) UIUtils.getToolDimen(this.mContext.getResources().getDimension(R.dimen.creation_tool_head_icon_size));
        layoutParams21.width = (int) UIUtils.getToolDimen(this.mContext.getResources().getDimension(R.dimen.creation_tool_head_icon_size));
        layoutParams21.height = (int) UIUtils.getToolDimen(this.mContext.getResources().getDimension(R.dimen.creation_tool_head_icon_size));
        layoutParams22.height = (int) UIUtils.getToolDimen(this.mContext.getResources().getDimension(R.dimen.creation_tool_head_icon_size));
        layoutParams22.width = (int) UIUtils.getToolDimen(this.mContext.getResources().getDimension(R.dimen.creation_tool_head_icon_size));
        this.mBrushBallpoint.setLayoutParams(layoutParams5);
        this.mBrushSelect.setLayoutParams(layoutParams4);
        this.mBrushPencil.setLayoutParams(layoutParams6);
        this.mBrushInk.setLayoutParams(layoutParams7);
        this.mBrushMarkPen.setLayoutParams(layoutParams8);
        this.mBrushWaterColor.setLayoutParams(layoutParams9);
        this.mBrushEraser.setLayoutParams(layoutParams10);
        this.mToolRectSelect.setLayoutParams(layoutParams11);
        this.mToolAI.setLayoutParams(layoutParams12);
        this.mToolRuler.setLayoutParams(layoutParams13);
        this.mColorView.setLayoutParams(layoutParams14);
        this.mLeftColorView.setLayoutParams(layoutParams15);
        this.mCenterColorView.setLayoutParams(layoutParams16);
        this.mRightColorView.setLayoutParams(layoutParams17);
        this.mToolBack.setLayoutParams(layoutParams18);
        this.mToolUndo.setLayoutParams(layoutParams19);
        this.mToolRedo.setLayoutParams(layoutParams20);
        this.mToolShare.setLayoutParams(layoutParams21);
        this.mToolMore.setLayoutParams(layoutParams22);
    }

    private void initToolBoxViewDim() {
        LinearLayout.LayoutParams layoutParams;
        LinearLayout.LayoutParams layoutParams2;
        LinearLayout.LayoutParams layoutParams3;
        LinearLayout.LayoutParams layoutParams4;
        LinearLayout.LayoutParams layoutParams5;
        LinearLayout.LayoutParams layoutParams6;
        LinearLayout.LayoutParams layoutParams7;
        LinearLayout.LayoutParams layoutParams8;
        LinearLayout.LayoutParams layoutParams9;
        LinearLayout.LayoutParams layoutParams10;
        LinearLayout.LayoutParams layoutParams11;
        LinearLayout.LayoutParams layoutParams12 = (LinearLayout.LayoutParams) this.mBrushContainer.getLayoutParams();
        LinearLayout.LayoutParams layoutParams13 = (LinearLayout.LayoutParams) this.mColorContainer.getLayoutParams();
        LinearLayout.LayoutParams layoutParams14 = (LinearLayout.LayoutParams) this.mToolBack.getLayoutParams();
        LinearLayout.LayoutParams layoutParams15 = (LinearLayout.LayoutParams) this.mLeftColorView.getLayoutParams();
        LinearLayout.LayoutParams layoutParams16 = (LinearLayout.LayoutParams) this.mColorView.getLayoutParams();
        LinearLayout.LayoutParams layoutParams17 = (LinearLayout.LayoutParams) this.mBrushSelect.getLayoutParams();
        LinearLayout.LayoutParams layoutParams18 = (LinearLayout.LayoutParams) this.mBrushBallpoint.getLayoutParams();
        LinearLayout.LayoutParams layoutParams19 = (LinearLayout.LayoutParams) this.mBrushPencil.getLayoutParams();
        LinearLayout.LayoutParams layoutParams20 = (LinearLayout.LayoutParams) this.mBrushInk.getLayoutParams();
        LinearLayout.LayoutParams layoutParams21 = (LinearLayout.LayoutParams) this.mBrushMarkPen.getLayoutParams();
        LinearLayout.LayoutParams layoutParams22 = (LinearLayout.LayoutParams) this.mBrushWaterColor.getLayoutParams();
        LinearLayout.LayoutParams layoutParams23 = (LinearLayout.LayoutParams) this.mBrushEraser.getLayoutParams();
        LinearLayout.LayoutParams layoutParams24 = (LinearLayout.LayoutParams) this.mToolRectSelect.getLayoutParams();
        LinearLayout.LayoutParams layoutParams25 = (LinearLayout.LayoutParams) this.mToolAI.getLayoutParams();
        LinearLayout.LayoutParams layoutParams26 = (LinearLayout.LayoutParams) this.mToolRuler.getLayoutParams();
        LinearLayout.LayoutParams layoutParams27 = (LinearLayout.LayoutParams) this.mToolRedo.getLayoutParams();
        LinearLayout.LayoutParams layoutParams28 = (LinearLayout.LayoutParams) this.mToolUndo.getLayoutParams();
        LinearLayout.LayoutParams layoutParams29 = (LinearLayout.LayoutParams) this.mToolShare.getLayoutParams();
        LinearLayout.LayoutParams layoutParams30 = (LinearLayout.LayoutParams) this.mToolMore.getLayoutParams();
        LinearLayout.LayoutParams layoutParams31 = layoutParams21;
        layoutParams12.setMarginStart((int) this.mContext.getResources().getDimension(R.dimen.creation_brush_container_left_margin));
        layoutParams14.setMarginStart((int) this.mContext.getResources().getDimension(R.dimen.creation_tool_bar_startendmargin));
        layoutParams15.setMarginStart((int) this.mContext.getResources().getDimension(R.dimen.creation_brush_left_color_margin_start));
        layoutParams16.setMarginStart((int) this.mContext.getResources().getDimension(R.dimen.creation_brush_left_color_margin_start));
        layoutParams16.setMarginEnd((int) this.mContext.getResources().getDimension(R.dimen.creation_brush_color_margin_start));
        layoutParams13.setMarginStart((int) this.mContext.getResources().getDimension(R.dimen.creation_color_box_margin_start));
        layoutParams28.setMarginEnd((int) this.mContext.getResources().getDimension(R.dimen.creation_tool_separate_margin_mid));
        layoutParams27.setMarginEnd((int) this.mContext.getResources().getDimension(R.dimen.creation_tool_separate_margin_mid));
        layoutParams29.setMarginEnd((int) this.mContext.getResources().getDimension(R.dimen.creation_tool_separate_margin_mid));
        layoutParams30.setMarginEnd((int) this.mContext.getResources().getDimension(R.dimen.creation_tool_separate_margin_start));
        if (ScreenModeUtils.isOneThird(this.mContext) || (ScreenModeUtils.isHalf(this.mContext) && UIUtils.isSmallScreenNoShare(this.mContext))) {
            layoutParams = layoutParams23;
            layoutParams2 = layoutParams13;
            layoutParams3 = layoutParams24;
            layoutParams4 = layoutParams25;
            layoutParams5 = layoutParams26;
            layoutParams12.leftMargin = (int) this.mContext.getResources().getDimension(R.dimen.creation_brush_container_left_margin);
            layoutParams18.setMarginStart((int) this.mContext.getResources().getDimension(R.dimen.creation_brush_margin_start_1_3));
            layoutParams19.setMarginStart((int) this.mContext.getResources().getDimension(R.dimen.creation_brush_pencil_margin_start_1_3));
            if (this.mContext.getResources().getConfiguration().smallestScreenWidthDp < 350 && UIUtils.isInSplitScreenMode(this.mContext)) {
                layoutParams16.setMarginStart((int) this.mContext.getResources().getDimension(R.dimen.creation_margin_start_1_3_mini));
                layoutParams16.setMarginEnd((int) this.mContext.getResources().getDimension(R.dimen.creation_color_margin_end));
                layoutParams20.setMarginStart((int) this.mContext.getResources().getDimension(R.dimen.creation_margin_start_1_3_mini));
                layoutParams31.setMarginStart((int) this.mContext.getResources().getDimension(R.dimen.creation_margin_start_1_3_mini));
                layoutParams22.setMarginStart((int) this.mContext.getResources().getDimension(R.dimen.creation_margin_start_1_3_mini));
                layoutParams.setMarginStart((int) this.mContext.getResources().getDimension(R.dimen.creation_margin_start_1_3_mini));
                layoutParams3.setMarginStart((int) this.mContext.getResources().getDimension(R.dimen.creation_margin_start_1_3_mini));
                layoutParams4.setMarginStart((int) this.mContext.getResources().getDimension(R.dimen.creation_margin_start_1_3_mini));
                layoutParams5.setMarginStart((int) this.mContext.getResources().getDimension(R.dimen.creation_margin_start_1_3_mini));
                layoutParams6 = layoutParams15;
                layoutParams6.setMarginStart((int) this.mContext.getResources().getDimension(R.dimen.creation_margin_start_1_3_mini));
            } else {
                layoutParams6 = layoutParams15;
                layoutParams16.setMarginStart((int) this.mContext.getResources().getDimension(R.dimen.creation_brush_left_color_margin_start_1_3));
                layoutParams16.setMarginEnd((int) this.mContext.getResources().getDimension(R.dimen.creation_color_margin_end));
                layoutParams20.setMarginStart((int) this.mContext.getResources().getDimension(R.dimen.creation_brush_ink_margin_start_1_3));
                layoutParams31.setMarginStart((int) this.mContext.getResources().getDimension(R.dimen.creation_brush_markpen_margin_start_1_3));
                layoutParams22.setMarginStart((int) this.mContext.getResources().getDimension(R.dimen.creation_brush_markpen_margin_start_1_3));
                layoutParams.setMarginStart((int) this.mContext.getResources().getDimension(R.dimen.creation_brush_eraser_margin_start_1_3));
                layoutParams3.setMarginStart((int) this.mContext.getResources().getDimension(R.dimen.creation_brush_select_margin_start_1_3));
                layoutParams4.setMarginStart((int) this.mContext.getResources().getDimension(R.dimen.creation_brush_select_margin_start_1_3));
                layoutParams5.setMarginStart((int) this.mContext.getResources().getDimension(R.dimen.creation_brush_ruler_margin_start_1_3));
                layoutParams6.setMarginStart((int) this.mContext.getResources().getDimension(R.dimen.creation_brush_left_color_margin_start_1_3));
            }
            layoutParams28.setMarginEnd((int) this.mContext.getResources().getDimension(R.dimen.creation_tool_separate_margin_mid));
            layoutParams7 = layoutParams27;
            layoutParams7.setMarginEnd((int) this.mContext.getResources().getDimension(R.dimen.creation_tool_separate_margin_mid));
            layoutParams8 = layoutParams22;
            layoutParams9 = layoutParams29;
            layoutParams9.setMarginEnd((int) this.mContext.getResources().getDimension(R.dimen.creation_tool_separate_margin_mid));
            layoutParams31 = layoutParams31;
            layoutParams10 = layoutParams30;
            layoutParams10.setMarginEnd((int) this.mContext.getResources().getDimension(R.dimen.creation_tool_separate_margin_end));
        } else if (ScreenModeUtils.isHalf(this.mContext) && !UIUtils.isSmallScreenNoShare(this.mContext)) {
            layoutParams12.leftMargin = (int) this.mContext.getResources().getDimension(R.dimen.creation_brush_margin_start_1_3);
            layoutParams16.setMarginEnd((int) this.mContext.getResources().getDimension(R.dimen.creation_brush_left_color_margin_start_1_2));
            layoutParams18.setMarginStart((int) this.mContext.getResources().getDimension(R.dimen.creation_brush_margin_start_1_2));
            layoutParams17.setMarginStart((int) this.mContext.getResources().getDimension(R.dimen.creation_brush_pencil_margin_start_1_3));
            layoutParams19.setMarginStart((int) this.mContext.getResources().getDimension(R.dimen.creation_brush_pencil_margin_start_1_2));
            layoutParams20.setMarginStart((int) this.mContext.getResources().getDimension(R.dimen.creation_brush_ink_margin_start_1_2));
            layoutParams31.setMarginStart((int) this.mContext.getResources().getDimension(R.dimen.creation_brush_markpen_margin_start_1_2));
            layoutParams22.setMarginStart((int) this.mContext.getResources().getDimension(R.dimen.creation_brush_markpen_margin_start_1_2));
            layoutParams2 = layoutParams13;
            layoutParams = layoutParams23;
            layoutParams.setMarginStart((int) this.mContext.getResources().getDimension(R.dimen.creation_brush_eraser_margin_start_1_2));
            layoutParams24.setMarginStart((int) this.mContext.getResources().getDimension(R.dimen.creation_brush_select_margin_start_1_2));
            layoutParams25.setMarginStart((int) this.mContext.getResources().getDimension(R.dimen.creation_brush_select_margin_start_1_2));
            layoutParams26.setMarginStart((int) this.mContext.getResources().getDimension(R.dimen.creation_brush_ruler_margin_start_1_2));
            layoutParams28.setMarginEnd((int) this.mContext.getResources().getDimension(R.dimen.creation_tool_bar_doredomargin_1_2));
            layoutParams27.setMarginEnd((int) this.mContext.getResources().getDimension(R.dimen.creation_tool_bar_doredomargin_1_2));
            layoutParams29.setMarginEnd((int) this.mContext.getResources().getDimension(R.dimen.creation_tool_bar_doredomargin_1_2));
            layoutParams30.setMarginEnd((int) this.mContext.getResources().getDimension(R.dimen.creation_tool_bar_endmargin_1_2));
            layoutParams17 = layoutParams17;
            layoutParams7 = layoutParams27;
            layoutParams6 = layoutParams15;
            layoutParams8 = layoutParams22;
            layoutParams9 = layoutParams29;
            layoutParams5 = layoutParams26;
            layoutParams10 = layoutParams30;
            layoutParams4 = layoutParams25;
            layoutParams3 = layoutParams24;
        } else {
            layoutParams = layoutParams23;
            layoutParams2 = layoutParams13;
            if (ScreenModeUtils.isTwoThird(this.mContext)) {
                layoutParams12.leftMargin = (int) this.mContext.getResources().getDimension(R.dimen.creation_brush_margin_start_1_3);
                layoutParams16.setMarginEnd((int) this.mContext.getResources().getDimension(R.dimen.creation_brush_left_color_margin_start_2_3));
                layoutParams18.setMarginStart((int) this.mContext.getResources().getDimension(R.dimen.creation_brush_margin_start_2_3));
                layoutParams17.setMarginStart((int) this.mContext.getResources().getDimension(R.dimen.creation_brush_pencil_margin_start_2_3));
                layoutParams19.setMarginStart((int) this.mContext.getResources().getDimension(R.dimen.creation_brush_pencil_margin_start_2_3));
                layoutParams20.setMarginStart((int) this.mContext.getResources().getDimension(R.dimen.creation_brush_ink_margin_start_2_3));
                layoutParams31.setMarginStart((int) this.mContext.getResources().getDimension(R.dimen.creation_brush_markpen_margin_start_2_3));
                layoutParams22.setMarginStart((int) this.mContext.getResources().getDimension(R.dimen.creation_brush_markpen_margin_start_2_3));
                layoutParams.setMarginStart((int) this.mContext.getResources().getDimension(R.dimen.creation_brush_eraser_margin_start_2_3));
                layoutParams3 = layoutParams24;
                layoutParams3.setMarginStart((int) this.mContext.getResources().getDimension(R.dimen.creation_brush_select_margin_start_2_3));
                layoutParams4 = layoutParams25;
                layoutParams4.setMarginStart((int) this.mContext.getResources().getDimension(R.dimen.creation_brush_select_margin_start_2_3));
                layoutParams5 = layoutParams26;
                layoutParams5.setMarginStart((int) this.mContext.getResources().getDimension(R.dimen.creation_brush_ruler_margin_start_2_3));
                layoutParams17 = layoutParams17;
                layoutParams7 = layoutParams27;
                layoutParams6 = layoutParams15;
                layoutParams31 = layoutParams31;
                layoutParams8 = layoutParams22;
                layoutParams9 = layoutParams29;
                layoutParams10 = layoutParams30;
            } else {
                layoutParams3 = layoutParams24;
                layoutParams4 = layoutParams25;
                layoutParams5 = layoutParams26;
                layoutParams12.leftMargin = (int) this.mContext.getResources().getDimension(R.dimen.creation_brush_container_left_margin);
                layoutParams18.setMarginStart((int) this.mContext.getResources().getDimension(R.dimen.creation_brush_margin_start));
                layoutParams17.setMarginStart((int) this.mContext.getResources().getDimension(R.dimen.creation_brush_select_margin_start));
                int dimension = (int) this.mContext.getResources().getDimension(R.dimen.creation_brush_separate_margin_mid);
                if (!UIUtils.isScreenLandscape(this.mContext) && DeviceUtils.isSmallPad()) {
                    dimension /= 3;
                }
                if (DeviceUtils.isO88()) {
                    dimension /= UIUtils.isScreenLandscape(this.mContext) ? 2 : 3;
                }
                layoutParams19.setMarginStart(dimension);
                layoutParams20.setMarginStart(dimension);
                layoutParams31.setMarginStart(dimension);
                layoutParams22.setMarginStart(dimension);
                layoutParams.setMarginStart(dimension);
                layoutParams3.setMarginStart(dimension);
                layoutParams4.setMarginStart(dimension);
                layoutParams5.setMarginStart(dimension);
                layoutParams31 = layoutParams31;
                layoutParams10 = layoutParams30;
                layoutParams6 = layoutParams15;
                layoutParams17 = layoutParams17;
                layoutParams7 = layoutParams27;
                layoutParams8 = layoutParams22;
                layoutParams9 = layoutParams29;
            }
        }
        if (!UIUtils.isSmallWindowMode(this.mContext)) {
            layoutParams11 = layoutParams10;
        } else if (!UIUtils.isSmallScreenForBrush(this.mContext)) {
            if (ScreenModeUtils.isFull(this.mContext)) {
                layoutParams11 = layoutParams10;
                layoutParams12.leftMargin = (int) this.mContext.getResources().getDimension(R.dimen.creation_brush_margin_start_1_3);
            } else {
                layoutParams11 = layoutParams10;
                if (ScreenModeUtils.isTwoThird(this.mContext)) {
                    layoutParams12.leftMargin = (int) this.mContext.getResources().getDimension(R.dimen.creation_brush_container_left_margin);
                }
            }
        } else {
            layoutParams11 = layoutParams10;
            layoutParams.setMarginStart((int) this.mContext.getResources().getDimension(R.dimen.creation_brush_separate_margin_mid_1_2));
            layoutParams3.setMarginStart((int) this.mContext.getResources().getDimension(R.dimen.creation_brush_separate_margin_mid_1_2));
            layoutParams4.setMarginStart((int) this.mContext.getResources().getDimension(R.dimen.creation_brush_separate_margin_mid_1_2));
            layoutParams5.setMarginStart((int) this.mContext.getResources().getDimension(R.dimen.creation_brush_separate_margin_mid_1_2));
            layoutParams28.setMarginEnd((int) this.mContext.getResources().getDimension(R.dimen.creation_tool_separate_margin_mid_1_2));
            layoutParams7.setMarginEnd((int) this.mContext.getResources().getDimension(R.dimen.creation_tool_separate_margin_mid_1_2));
            layoutParams9.setMarginEnd((int) this.mContext.getResources().getDimension(R.dimen.creation_tool_separate_margin_mid_1_2));
        }
        this.mBrushContainer.setLayoutParams(layoutParams12);
        this.mLeftColorView.setLayoutParams(layoutParams6);
        this.mColorView.setLayoutParams(layoutParams16);
        this.mBrushBallpoint.setLayoutParams(layoutParams18);
        this.mBrushPencil.setLayoutParams(layoutParams19);
        this.mBrushSelect.setLayoutParams(layoutParams17);
        this.mBrushInk.setLayoutParams(layoutParams20);
        this.mBrushMarkPen.setLayoutParams(layoutParams31);
        this.mBrushWaterColor.setLayoutParams(layoutParams8);
        this.mBrushEraser.setLayoutParams(layoutParams);
        this.mToolRectSelect.setLayoutParams(layoutParams3);
        this.mToolAI.setLayoutParams(layoutParams4);
        this.mToolRuler.setLayoutParams(layoutParams5);
        this.mColorContainer.setLayoutParams(layoutParams2);
        this.mToolUndo.setLayoutParams(layoutParams28);
        this.mToolRedo.setLayoutParams(layoutParams7);
        this.mToolShare.setLayoutParams(layoutParams9);
        this.mToolMore.setLayoutParams(layoutParams11);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void brushClick(View view, PenType penType) {
        if (!activeTool(view)) {
            btnClick();
            selectPenType(penType);
        } else {
            showPenSizePopupWindow(view);
        }
        updateSelectColorView(true);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void multiBrushClick(View view, PenType penType) {
        if (!activeToolForMultiPen(view)) {
            btnClick();
            selectPenType(penType);
            updateSelectBrushParams();
        }
        setMultiBrushData();
        updateSelectColorView(true);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void multiToolClick(View view) {
        if (!activeToolForMultiPen(view)) {
            btnClick();
            updateSelectBrushParams();
        }
        updateSelectBg();
        this.mPenSizePopupWindow.refreshPopWindow(false);
        updateSelectColorView(false);
    }

    private void updateSelectBrushParams() {
        ViewGroup.LayoutParams layoutParams = this.mBrushSelect.getLayoutParams();
        if (this.mBrushSelect.getVisibility() == 0) {
            if (!this.isAISelect && getCurrentPenBen().getPenType().getValue() == 4) {
                layoutParams.width = (int) UIUtils.getToolDimen(this.mContext.getResources().getDimension(R.dimen.creation_tool_item_width_fat));
            } else {
                layoutParams.width = (int) UIUtils.getToolDimen(this.mContext.getResources().getDimension(R.dimen.creation_tool_item_width_thin));
            }
        }
        this.mBrushSelect.setLayoutParams(layoutParams);
    }

    public MiuiDeleteProp getCurrentEraserPen() {
        if (this.currentEraserPen == null) {
            this.currentEraserPen = new MiuiDeleteProp(DeleteType.POINT, 6.0f);
        }
        return this.currentEraserPen;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void enableAi(View view) {
        this.aiType = 0;
        enableFullPageSelect(false);
        btnClick();
        enableAI();
        activeTool(view);
    }

    public void enableAiByhButton() {
        enableAi(findViewById(R.id.creation_brush_ai));
    }

    public void enalbeAiByButtonPackage() {
        this.isAISelect = true;
        multiToolClick(findViewById(R.id.creation_brush_ai));
        enableAI();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void showAIPopWindow(View view, int i) {
        AIPenPopupWindow aIPenPopupWindow = new AIPenPopupWindow(view.getContext());
        this.mAIPenPopWindow = aIPenPopupWindow;
        aIPenPopupWindow.setArrowMode(8);
        if (this.mPageInkViewModel.isUnlimited()) {
            this.aiType = 0;
        }
        this.mAIPenPopWindow.setUnlimited(this.mPageInkViewModel.isUnlimited());
        this.mAIPenPopWindow.setAiType(this.aiType);
        this.mAIPenPopWindow.initUi();
        this.mAIPenPopWindow.setListener(this.aiPenClickListener);
        this.mAIPenPopWindow.showAsDropDown(view, calculatePopupWindowOffset(view), i);
    }

    @Override // com.miui.handwriting.common.view.toolbar.IMiuiHandToolbar
    public boolean isAISelect() {
        return this.isAISelect;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void showEraserSizePopupWindow(View view) {
        EraserPopupWindow eraserPopupWindow = new EraserPopupWindow(view.getContext());
        this.mEraserPopupWindow = eraserPopupWindow;
        eraserPopupWindow.setDeleteProp(getCurrentEraserPen());
        this.mEraserPopupWindow.initUi();
        this.mEraserPopupWindow.setConfirmListener(new EraserPopupWindow.OnEraserSettingConfirmListener() { // from class: com.miui.handwriting.common.view.toolbar.MiuiToolBoxView$$ExternalSyntheticLambda11
            @Override // com.miui.handwriting.common.view.popup.EraserPopupWindow.OnEraserSettingConfirmListener
            public final void onConfirmCircleClick() {
                this.f$0.m816x229bcd14();
            }
        });
        this.mEraserPopupWindow.setArrowMode(8);
        this.mEraserPopupWindow.showAsDropDown(view, calculatePopupWindowOffset(view), 0);
    }

    /* JADX INFO: renamed from: lambda$showEraserSizePopupWindow$11$com-miui-handwriting-common-view-toolbar-MiuiToolBoxView, reason: not valid java name */
    /* synthetic */ void m816x229bcd14() {
        enableEraser(getCurrentEraserPen());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void getFocus() {
        setFocusable(true);
        setFocusableInTouchMode(true);
        requestFocus();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean activeTool(View view) {
        Log.d(TAG, "activeTool() called with: context = [" + this.mContext + "], newActiveView = [" + view + "]");
        View view2 = this.mCurrentSelectToolView;
        this.mLastSelectToolView = view2;
        this.mCurrentMultiSelectToolView = null;
        if (view2 == view) {
            return true;
        }
        if (view2 != null) {
            Folme.useAt(view2).state().to(ViewProperty.TRANSLATION_Y, Float.valueOf(this.mBrushTransY));
        }
        Folme.useAt(view).state().to(ViewProperty.TRANSLATION_Y, 0);
        this.mCurrentSelectToolView = view;
        View view3 = this.mBrushSelect;
        if (view3 != null && view3.getVisibility() == 0) {
            Folme.useAt(this.mBrushSelect).state().to(ViewProperty.TRANSLATION_Y, Float.valueOf(this.mBrushTransY));
        }
        if (this.mLimitedToolbar) {
            resetAllBrushesExcept(view);
        }
        return false;
    }

    private void resetAllBrushesExcept(View view) {
        View[] viewArr = {this.mBrushInk, this.mBrushPencil, this.mBrushBallpoint, this.mBrushMarkPen, this.mBrushWaterColor, this.mBrushEraser, this.mToolRectSelect, this.mToolAI};
        for (int i = 0; i < 8; i++) {
            View view2 = viewArr[i];
            if (view2 != null && view2 != view && view2.getVisibility() == 0) {
                Folme.useAt(view2).state().to(ViewProperty.TRANSLATION_Y, Float.valueOf(this.mBrushTransY));
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean activeToolForMultiPen(View view) {
        Log.d(TAG, "activeToolForMultiPen() called with: context = [" + this.mContext + "], newActiveView = [" + view + "]");
        View view2 = this.mCurrentMultiSelectToolView;
        if (view2 == view || this.mCurrentSelectToolView == view) {
            return true;
        }
        if (view2 != null) {
            Folme.useAt(view2).state().to(ViewProperty.TRANSLATION_Y, Float.valueOf(this.mBrushTransY));
        }
        Folme.useAt(view).state().to(ViewProperty.TRANSLATION_Y, 0);
        this.mCurrentMultiSelectToolView = view;
        return false;
    }

    protected void btnClick() {
        this.mPageInkViewModel.onCancelEvent();
        this.mPageInkViewModel.selectMoveEnable(true);
        this.mHandWritingImpl.enableAllPageAI(false);
    }

    public void selectPenType(PenType penType) {
        this.lastPenIndex = this.mPenSet.getCurPenBean().getPenType().getValue();
        this.isAiBrush = false;
        dismissAIPop();
        Log.d(TAG, "selectPenType:" + penType.getValue());
        setTouchEvent(1);
        PenSet penSet = this.mPenSet;
        penSet.setCurPenBean(penSet.getPenBeanByType(penType));
        setPenParams();
        this.mSavedSelectViewType = getCurrentPenBen().getPenType().getValue();
        setTouchEvent(0);
    }

    public void selectPenType() {
        setTouchEvent(1);
        setPenParams();
        setTouchEvent(0);
    }

    private void setPenParams() {
        setPenProp(getCurrentPenBen().convert());
        resetEraserState();
        cancelSelect();
        setColorToolParams();
    }

    public void setPenProp(CurveProp curveProp) {
        PageItemView currentItemView = this.mPageInkViewModel.getCurrentItemView();
        if (currentItemView != null) {
            currentItemView.setPenType(curveProp);
        }
        StylusPenQuickMenu stylusPenQuickMenu = this.mStylusPenQuickMenu;
        if (stylusPenQuickMenu != null) {
            stylusPenQuickMenu.updatePenSelected(true);
            this.mStylusPenQuickMenu.updateEraserSelected(false);
            this.mStylusPenQuickMenu.setCurPenInfo(getCurrentPenBen().convert());
        }
        this.mPageInkViewModel.getEngineConfig().penProp = curveProp;
        this.mPageInkViewModel.getEngineConfig().operatorMode = 2;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void handleClickRule(View view) {
        if (this.isRuler) {
            rulerAnimation(this.mToolRuler, false);
            this.mHandWritingImpl.showRuler(false);
            this.isRuler = false;
        } else {
            rulerAnimation(view, true);
            this.mLastSelectForRule = this.mCurrentSelectToolView;
            this.mHandWritingImpl.showRuler(true);
            this.isRuler = true;
        }
    }

    private void rulerAnimation(View view, boolean z) {
        if (z) {
            Folme.useAt(view).state().to(new AnimState("show").add(ViewProperty.Y, 0.0d), new AnimConfig[0]);
        } else {
            Folme.useAt(view).state().to(new AnimState("end").add(ViewProperty.Y, this.mBrushTransY), new AnimConfig[0]);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void saveToolBoxState() {
        Log.i(TAG, "saveToolBoxState");
        SharedPreferences.Editor editorEdit = getContext().getSharedPreferences(Constants.CREATION_SHPREF, 0).edit();
        if (isCalculatorMode()) {
            editorEdit.putString(Constants.TOOL_BRUSH_PENCIL_CALC, GsonUtils.getGsonInstance().toJson(this.mPenSet.getPenBeanByType(PenType.PENCIL)));
            editorEdit.putString(Constants.TOOL_BRUSH_INK_CALC, GsonUtils.getGsonInstance().toJson(this.mPenSet.getPenBeanByType(PenType.INK)));
            editorEdit.putString(Constants.TOOL_BRUSH_MARKPEN_CALC, GsonUtils.getGsonInstance().toJson(this.mPenSet.getPenBeanByType(PenType.MARK)));
            editorEdit.putString(Constants.TOOL_BRUSH_BALLPOINT_CALC, GsonUtils.getGsonInstance().toJson(this.mPenSet.getPenBeanByType(PenType.BALL)));
            editorEdit.putString(Constants.TOOL_BRUSH_WATERCOLOR_CALC, GsonUtils.getGsonInstance().toJson(this.mPenSet.getPenBeanByType(PenType.WATER_COLOR)));
            editorEdit.putInt(Constants.TOOL_CURRENT_BRUSH_CALC, this.mPenSet.getCurPenBean().getPenType().getValue());
            Log.w(TAG, "xqw- saveToolBoxState_calc: " + this.mPenSet.getCurPenBean().getPenType().getValue());
        } else {
            editorEdit.putString(Constants.TOOL_BRUSH_PENCIL, GsonUtils.getGsonInstance().toJson(this.mPenSet.getPenBeanByType(PenType.PENCIL)));
            editorEdit.putString(Constants.TOOL_BRUSH_INK, GsonUtils.getGsonInstance().toJson(this.mPenSet.getPenBeanByType(PenType.INK)));
            editorEdit.putString(Constants.TOOL_BRUSH_MARKPEN, GsonUtils.getGsonInstance().toJson(this.mPenSet.getPenBeanByType(PenType.MARK)));
            editorEdit.putString(Constants.TOOL_BRUSH_BALLPOINT, GsonUtils.getGsonInstance().toJson(this.mPenSet.getPenBeanByType(PenType.BALL)));
            editorEdit.putString(Constants.TOOL_BRUSH_WATERCOLOR, GsonUtils.getGsonInstance().toJson(this.mPenSet.getPenBeanByType(PenType.WATER_COLOR)));
            editorEdit.putInt(Constants.TOOL_CURRENT_BRUSH, this.mPenSet.getCurPenBean().getPenType().getValue());
            Log.w(TAG, "xqw- saveToolBoxState_note: " + this.mPenSet.getCurPenBean().getPenType().getValue());
        }
        editorEdit.putBoolean(Constants.TOOL_AI_BRUSH, this.isAiBrush);
        String str = isCalculatorMode() ? Constants.TOOL_SELECT_STATE_SAVE_CALC : Constants.TOOL_SELECT_STATE_SAVE;
        if (isToolsSelected()) {
            if (this.isEraser) {
                this.mSavedSelectViewType = 7;
                editorEdit.putInt(str, 7);
            } else if (this.isSelect) {
                this.mSavedSelectViewType = 6;
                editorEdit.putInt(str, 6);
            }
        } else {
            int value = this.mPenSet.getCurPenBean().getPenType().getValue();
            this.mSavedSelectViewType = value;
            editorEdit.putInt(str, value);
        }
        Log.e(TAG, "current select is " + this.mSavedSelectViewType);
        editorEdit.putBoolean(Constants.USER_HAS_CHANGED_PENCIL_CENTER_COLOR, this.mHasChangeColor);
        editorEdit.putString(Constants.TOOL_ERASER_DELETE, GsonUtils.getGsonInstance().toJson(getCurrentEraserPen()));
        editorEdit.putInt(Constants.AI_TYPE, this.aiType);
        editorEdit.apply();
        MiCurveEngine miCurveEngine = this.mPageInkViewModel.getMiCurveEngine();
        if (miCurveEngine != null) {
            miCurveEngine.getMathDataManager().setCurrentSelectPenType(this.mSavedSelectViewType);
        }
    }

    private void saveShapeState() {
        Log.i(TAG, "saveShapeState");
        SharedPreferences.Editor editorEdit = getContext().getSharedPreferences(Constants.CREATION_SHPREF, 0).edit();
        editorEdit.putString(Constants.SHAPE_ARROW, GsonUtils.getGsonInstance().toJson(ShapeEditSet.getShapeEditBeanByType(2)));
        editorEdit.putString(Constants.SHAPE_RECT, GsonUtils.getGsonInstance().toJson(ShapeEditSet.getShapeEditBeanByType(3)));
        editorEdit.putString(Constants.SHAPE_CIRCLE, GsonUtils.getGsonInstance().toJson(ShapeEditSet.getShapeEditBeanByType(5)));
        editorEdit.putString(Constants.SHAPE_BIGARROW, GsonUtils.getGsonInstance().toJson(ShapeEditSet.getShapeEditBeanByType(7)));
        editorEdit.putString(Constants.SHAPE_STAR, GsonUtils.getGsonInstance().toJson(ShapeEditSet.getShapeEditBeanByType(6)));
        editorEdit.putString(Constants.SHAPE_TRIANGLE, GsonUtils.getGsonInstance().toJson(ShapeEditSet.getShapeEditBeanByType(4)));
        editorEdit.commit();
    }

    private Drawable createToolBg(Context context, String str) {
        String packageName = context.getPackageName();
        String str2 = "creation_" + str;
        String str3 = str2 + "_shadow_s";
        int identifier = context.getResources().getIdentifier(str3, "drawable", packageName);
        int identifier2 = context.getResources().getIdentifier(str2 + "_body", "drawable", packageName);
        Drawable[] drawableArr = new Drawable[2];
        if (UIUtils.isNightMode()) {
            drawableArr[0] = new ColorDrawable().mutate();
        } else {
            drawableArr[0] = ContextCompat.getDrawable(context, identifier).mutate();
        }
        drawableArr[1] = ContextCompat.getDrawable(context, identifier2).mutate();
        return new LayerDrawable(drawableArr);
    }

    private Drawable createToolBg(Context context, String str, Configuration configuration) {
        String packageName = context.getPackageName();
        String str2 = "creation_" + str;
        String str3 = str2 + "_shadow_s";
        int identifier = context.getResources().getIdentifier(str3, "drawable", packageName);
        int identifier2 = context.getResources().getIdentifier(str2 + "_body", "drawable", packageName);
        Drawable[] drawableArr = new Drawable[2];
        if (UIUtils.isNightMode(configuration)) {
            drawableArr[0] = new ColorDrawable().mutate();
        } else {
            drawableArr[0] = ContextCompat.getDrawable(context, identifier).mutate();
        }
        drawableArr[1] = ContextCompat.getDrawable(context, identifier2).mutate();
        return new LayerDrawable(drawableArr);
    }

    private Drawable createBrushFg(String str, int i) {
        String packageName = this.mContext.getPackageName();
        String str2 = "creation_" + str;
        String str3 = str2 + "_head";
        int identifier = getResources().getIdentifier(str3, "drawable", packageName);
        int identifier2 = getResources().getIdentifier(str2 + "_body", "drawable", packageName);
        Drawable[] drawableArr = new Drawable[2];
        Drawable drawable = ContextCompat.getDrawable(this.mContext, identifier);
        Drawable drawable2 = ContextCompat.getDrawable(this.mContext, identifier2);
        if (drawable != null) {
            Drawable drawableMutate = drawable.mutate();
            drawableArr[0] = drawableMutate;
            drawableMutate.setTint(i);
        }
        if (drawable2 != null) {
            drawableArr[1] = drawable2.mutate();
        }
        return new LayerDrawable(drawableArr);
    }

    private Drawable createBrushBg(String str) {
        String packageName = this.mContext.getPackageName();
        String str2 = "creation_" + str;
        String str3 = str2 + "_shadow_s";
        int identifier = getResources().getIdentifier(str3, "drawable", packageName);
        Drawable drawableMutate = ((Drawable) Objects.requireNonNull(ContextCompat.getDrawable(this.mContext, getResources().getIdentifier(str2 + "_shadow_u", "drawable", packageName)))).mutate();
        Drawable drawableMutate2 = ((Drawable) Objects.requireNonNull(ContextCompat.getDrawable(this.mContext, identifier))).mutate();
        new TransitionDrawable(new Drawable[]{drawableMutate, drawableMutate2}).setCrossFadeEnabled(true);
        return drawableMutate2;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void updateBrushBg() {
        Log.i(TAG, "getCurrentPenBen()" + getCurrentPenBen() + getCurrentPenBen().getConvertPenType().getValue());
        PenBean currentPenBen = getCurrentPenBen();
        if (currentPenBen == null) {
            return;
        }
        int penColor = currentPenBen.getPenColor();
        if (currentPenBen.getPenType().getValue() == 3) {
            updateBrushViewState(this.mBrushPencil, Constants.TOOL_BRUSH_PENCIL, penColor, true);
            updateBrushViewState(this.mPenSizePopupWindow.mBrushPencil, Constants.TOOL_BRUSH_PENCIL, penColor, true);
        } else if (currentPenBen.getPenType().getValue() == 1) {
            updateBrushViewState(this.mBrushInk, Constants.TOOL_BRUSH_INK, penColor, true);
            updateBrushViewState(this.mPenSizePopupWindow.mBrushInk, Constants.TOOL_BRUSH_INK, penColor, true);
        } else if (currentPenBen.getPenType().getValue() == 2) {
            updateBrushViewState(this.mBrushBallpoint, Constants.TOOL_BRUSH_BALLPOINT, penColor, true);
            updateBrushViewState(this.mPenSizePopupWindow.mBrushBallpoint, Constants.TOOL_BRUSH_BALLPOINT, penColor, true);
        } else if (currentPenBen.getPenType().getValue() == 4) {
            updateBrushViewState(this.mBrushMarkPen, Constants.TOOL_BRUSH_MARKPEN, penColor, true);
            updateBrushViewState(this.mPenSizePopupWindow.mBrushMarkPen, Constants.TOOL_BRUSH_MARKPEN, penColor, true);
        } else if (currentPenBen.getPenType().getValue() == 10) {
            updateBrushViewState(this.mBrushWaterColor, Constants.TOOL_BRUSH_WATERCOLOR, penColor, true);
            updateBrushViewState(this.mPenSizePopupWindow.mBrushWaterColor, Constants.TOOL_BRUSH_WATERCOLOR, penColor, true);
        }
        this.mColorView.setHSLColor(this.mCurrentSelectColorView.getHSLColor());
        saveToolBoxState();
        updateSelectBrushView(true, penColor, getBrushName(this.mPenSet.getCurPenBean().getPenType()));
    }

    private void updateBrushBg(Configuration configuration) {
        Log.i(TAG, "getCurrentPenBen()" + getCurrentPenBen() + getCurrentPenBen().getConvertPenType().getValue());
        PenBean currentPenBen = getCurrentPenBen();
        if (currentPenBen == null) {
            return;
        }
        int penColor = currentPenBen.getPenColor();
        if (currentPenBen.getPenType().getValue() == 3) {
            updateBrushViewState(this.mBrushPencil, Constants.TOOL_BRUSH_PENCIL, penColor, configuration);
            updateBrushViewState(this.mPenSizePopupWindow.mBrushPencil, Constants.TOOL_BRUSH_PENCIL, penColor, configuration);
        } else if (currentPenBen.getPenType().getValue() == 1) {
            updateBrushViewState(this.mBrushInk, Constants.TOOL_BRUSH_INK, penColor, configuration);
            updateBrushViewState(this.mPenSizePopupWindow.mBrushInk, Constants.TOOL_BRUSH_INK, penColor, configuration);
        } else if (currentPenBen.getPenType().getValue() == 2) {
            updateBrushViewState(this.mBrushBallpoint, Constants.TOOL_BRUSH_BALLPOINT, penColor, configuration);
            updateBrushViewState(this.mPenSizePopupWindow.mBrushBallpoint, Constants.TOOL_BRUSH_BALLPOINT, penColor, configuration);
        } else if (currentPenBen.getPenType().getValue() == 4) {
            updateBrushViewState(this.mBrushMarkPen, Constants.TOOL_BRUSH_MARKPEN, penColor, configuration);
            updateBrushViewState(this.mPenSizePopupWindow.mBrushMarkPen, Constants.TOOL_BRUSH_MARKPEN, penColor, configuration);
        } else if (currentPenBen.getPenType().getValue() == 10) {
            updateBrushViewState(this.mBrushWaterColor, Constants.TOOL_BRUSH_WATERCOLOR, penColor, configuration);
            updateBrushViewState(this.mPenSizePopupWindow.mBrushWaterColor, Constants.TOOL_BRUSH_WATERCOLOR, penColor, configuration);
        }
        this.mColorView.setHSLColor(this.mCurrentSelectColorView.getHSLColor());
        saveToolBoxState();
        updateSelectBrushView(true, penColor, getBrushName(this.mPenSet.getCurPenBean().getPenType()), configuration);
    }

    private void updateSelectBg() {
        saveToolBoxState();
        if (isAiSelected()) {
            updateToolViewState(this.mToolAI, Constants.TOOL_AI_SELECT, true);
            updateToolViewState(this.mBrushSelect, Constants.TOOL_AI_SELECT, true);
            updateToolViewState(this.mPenSizePopupWindow.mAiSelect, Constants.TOOL_AI_SELECT, true);
        }
    }

    public void setMyViewMode() {
        this.mPageInkViewModel.setShieldEventListener(this);
        selectPenType();
    }

    @Override // com.miui.handwriting.common.view.toolbar.ICommonMiuiHandToolbar
    public void setToolBoxViewTopPadding(int i, int i2) {
        setPadding(getPaddingStart(), i, getPaddingRight(), getPaddingBottom());
    }

    @Override // com.miui.handwriting.common.view.toolbar.IMiuiHandToolbar, com.miui.handwriting.common.view.toolbar.ICommonMiuiHandToolbar
    public void onStepChanged(int i, final boolean z, final boolean z2) {
        Log.d(TAG, "onStepChanged canUndo " + z + "; canRedo " + z2 + "; stepType " + i);
        this.mToolUndo.post(new Runnable() { // from class: com.miui.handwriting.common.view.toolbar.MiuiToolBoxView$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.m811x60ba54a(z);
            }
        });
        this.mToolRedo.post(new Runnable() { // from class: com.miui.handwriting.common.view.toolbar.MiuiToolBoxView$$ExternalSyntheticLambda7
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.m812x92f8bc69(z2);
            }
        });
    }

    /* JADX INFO: renamed from: lambda$onStepChanged$12$com-miui-handwriting-common-view-toolbar-MiuiToolBoxView, reason: not valid java name */
    /* synthetic */ void m811x60ba54a(boolean z) {
        this.mToolUndo.setEnabled(z);
    }

    /* JADX INFO: renamed from: lambda$onStepChanged$13$com-miui-handwriting-common-view-toolbar-MiuiToolBoxView, reason: not valid java name */
    /* synthetic */ void m812x92f8bc69(boolean z) {
        this.mToolRedo.setEnabled(z);
    }

    public void registerListener() {
        MiCurveEngine miCurveEngine = this.mPageInkViewModel.getMiCurveEngine();
        if (miCurveEngine != null) {
            miCurveEngine.getMathDataManager().registerMathSelectCallBack(this.mathSelectCallBack);
        }
    }

    public void refreshPenType() {
        MiCurveEngine miCurveEngine = this.mPageInkViewModel.getMiCurveEngine();
        Log.d(TAG, "refreshPenType: " + this.mSavedSelectViewType);
        if (miCurveEngine != null) {
            miCurveEngine.getMathDataManager().setCurrentSelectPenType(this.mSavedSelectViewType);
        }
    }

    @Override // com.miui.handwriting.common.view.toolbar.IMiuiHandToolbar
    public void initToolBox(IMiuiHandWritingModel iMiuiHandWritingModel, BaseMiuiHandWritingView baseMiuiHandWritingView) {
        this.mPageInkViewModel = iMiuiHandWritingModel;
        setHandWritingView(baseMiuiHandWritingView);
        setMyViewMode();
        initialBrush();
        setOnFocusChangeListener(new View.OnFocusChangeListener() { // from class: com.miui.handwriting.common.view.toolbar.MiuiToolBoxView.12
            @Override // android.view.View.OnFocusChangeListener
            public void onFocusChange(View view, boolean z) {
                Log.i(MiuiToolBoxView.TAG, "ToolBoxView  onFocusChange hasFocus=" + z);
            }
        });
        this.mHandWritingImpl.setToolBoxPreviewListener(new PreviewListener() { // from class: com.miui.handwriting.common.view.toolbar.MiuiToolBoxView.13
            @Override // com.miui.libhandwritingpage.listener.PreviewListener
            public void onPreviewUpdate(List<PagePreviewData> list) {
                MiuiToolBoxView.this.onPreviewUpdate(list);
                MiuiToolBoxView.this.mHandWritingImpl.onPreviewUpdate(list);
            }

            @Override // com.miui.libhandwritingpage.listener.PreviewListener
            public void onPreviewItemUpdated(int i, PagePreviewData pagePreviewData) {
                Log.d(MiuiToolBoxView.TAG, "PreviewData:" + i + ":" + pagePreviewData.bgColor);
                MiuiToolBoxView.this.onPreviewItemUpdated(i, pagePreviewData);
                MiuiToolBoxView.this.mHandWritingImpl.onPreviewItemUpdated(i, pagePreviewData);
            }

            @Override // com.miui.libhandwritingpage.listener.PreviewListener
            public void onBgColorItemUpdated(int i, int i2) {
                MiuiToolBoxView.this.mHandWritingImpl.onBgColorItemUpdated(i, i2);
            }
        });
    }

    public void setHandWritingView(BaseMiuiHandWritingView baseMiuiHandWritingView) {
        this.mHandWritingImpl = baseMiuiHandWritingView;
        IMiuiHandWritingModel iMiuiHandWritingModel = this.mPageInkViewModel;
        iMiuiHandWritingModel.setOnlyPenWrite(iMiuiHandWritingModel.isOnlyPenWrite());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void enableEraser(MiuiDeleteProp miuiDeleteProp) {
        setTouchEvent(1);
        this.mPageInkViewModel.setSelect(false);
        this.isSelect = false;
        this.isAISelect = false;
        dismissAIPop();
        this.isEraser = true;
        setDeleteProp(miuiDeleteProp.covert());
        updateSelectColorView(false);
        setTouchEvent(0);
        saveToolBoxState();
        StylusPenQuickMenu stylusPenQuickMenu = this.mStylusPenQuickMenu;
        if (stylusPenQuickMenu != null) {
            stylusPenQuickMenu.updateEraserSelected(true);
            this.mStylusPenQuickMenu.updatePenSelected(false);
        }
    }

    public void setDeleteProp(DeleteProp deleteProp) {
        PageItemView currentItemView = this.mPageInkViewModel.getCurrentItemView();
        if (currentItemView != null) {
            currentItemView.setClearType(deleteProp);
        }
        this.mPageInkViewModel.getEngineConfig().deleteProp = deleteProp;
        this.mPageInkViewModel.getEngineConfig().operatorMode = 1;
        StylusPenQuickMenu stylusPenQuickMenu = this.mStylusPenQuickMenu;
        if (stylusPenQuickMenu != null) {
            stylusPenQuickMenu.setEraser(deleteProp.getDeleteType(), deleteProp.getDeleteRadius());
        }
    }

    public void enableSelect() {
        dismissPopupWindow();
        this.mPageInkViewModel.setSelect(true);
        this.isSelect = true;
        this.isAISelect = false;
        dismissAIPop();
        resetEraserState();
        try {
            View view = this.mBrushInk;
            if (view != null) {
                Folme.useAt(view).state().to(ViewProperty.TRANSLATION_Y, Float.valueOf(this.mBrushTransY));
            }
            View view2 = this.mBrushBallpoint;
            if (view2 != null) {
                Folme.useAt(view2).state().to(ViewProperty.TRANSLATION_Y, Float.valueOf(this.mBrushTransY));
            }
            View view3 = this.mBrushPencil;
            if (view3 != null) {
                Folme.useAt(view3).state().to(ViewProperty.TRANSLATION_Y, Float.valueOf(this.mBrushTransY));
            }
            View view4 = this.mBrushMarkPen;
            if (view4 != null) {
                Folme.useAt(view4).state().to(ViewProperty.TRANSLATION_Y, Float.valueOf(this.mBrushTransY));
            }
            View view5 = this.mBrushWaterColor;
            if (view5 != null) {
                Folme.useAt(view5).state().to(ViewProperty.TRANSLATION_Y, Float.valueOf(this.mBrushTransY));
            }
            View view6 = this.mCurrentSelectToolView;
            if (view6 != null && view6 != this.mToolRectSelect) {
                Folme.useAt(view6).state().to(ViewProperty.TRANSLATION_Y, Float.valueOf(this.mBrushTransY));
            }
            View view7 = this.mToolRectSelect;
            if (view7 != null) {
                Folme.useAt(view7).state().to(ViewProperty.TRANSLATION_Y, 0);
                this.mCurrentSelectToolView = this.mToolRectSelect;
            }
        } catch (Throwable unused) {
        }
        updateSelectColorView(false);
        saveToolBoxState();
        StylusPenQuickMenu stylusPenQuickMenu = this.mStylusPenQuickMenu;
        if (stylusPenQuickMenu != null) {
            stylusPenQuickMenu.updatePenSelected(false);
            this.mStylusPenQuickMenu.updateEraserSelected(false);
        }
    }

    public void enableAI() {
        this.mPageInkViewModel.setSelect(true, true);
        this.isSelect = false;
        this.isAISelect = true;
        this.isAiBrush = true;
        if (RomUtils.isUseNewCreationAI()) {
            this.mHandWritingImpl.enableAllPageAI(false);
        } else {
            this.mHandWritingImpl.enableAllPageAI(this.aiType == 1);
        }
        resetEraserState();
        updateSelectColorView(false);
        saveToolBoxState();
        StylusPenQuickMenu stylusPenQuickMenu = this.mStylusPenQuickMenu;
        if (stylusPenQuickMenu != null) {
            stylusPenQuickMenu.updatePenSelected(false);
            this.mStylusPenQuickMenu.updateEraserSelected(false);
        }
    }

    private void dismissAIPop() {
        BaseMiuiHandWritingView baseMiuiHandWritingView = this.mHandWritingImpl;
        if (baseMiuiHandWritingView != null) {
            baseMiuiHandWritingView.dismissAIPop();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void enableFullPageSelect(boolean z) {
        BaseMiuiHandWritingView baseMiuiHandWritingView = this.mHandWritingImpl;
        if (baseMiuiHandWritingView == null) {
            return;
        }
        baseMiuiHandWritingView.enableAllPageAI(z);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void cancelSelect() {
        Log.d(TAG, "cancelSelect: ");
        this.mPageInkViewModel.setSelect(false);
        this.isSelect = false;
        this.isAISelect = false;
        enableFullPageSelect(false);
    }

    private void resetEraserState() {
        this.isEraser = false;
    }

    @Override // com.miui.handwriting.common.view.toolbar.ICommonMiuiHandToolbar
    public void setShareClickListener(View.OnClickListener onClickListener) {
        this.mShareBtnListener = onClickListener;
    }

    @Override // com.miui.handwriting.common.view.toolbar.ICommonMiuiHandToolbar
    public void setSaveClickListener(View.OnClickListener onClickListener) {
        this.mSaveBtnListener = onClickListener;
    }

    @Override // com.miui.handwriting.common.view.toolbar.ICommonMiuiHandToolbar
    public void setBackClickEnable(boolean z) {
        this.mToolBack.setEnabled(z);
    }

    @Override // com.miui.handwriting.common.view.toolbar.ICommonMiuiHandToolbar
    public void setFromPc(boolean z) {
        this.isPc = z;
        if (z) {
            this.mToolShare.setImageDrawable(ContextCompat.getDrawable(this.mContext, R.drawable.creation_pc_finish));
            this.mToolBack.setImageDrawable(ContextCompat.getDrawable(this.mContext, R.drawable.creation_pc_cancel));
        } else {
            this.mToolShare.setImageDrawable(ContextCompat.getDrawable(this.mContext, R.drawable.creation_action_share));
            this.mToolBack.setImageDrawable(ContextCompat.getDrawable(this.mContext, R.drawable.creation_action_back));
        }
    }

    @Override // com.miui.handwriting.common.view.toolbar.ICommonMiuiHandToolbar
    public void setShareIconState(boolean z) {
        ImageView imageView = this.mToolShare;
        if (imageView != null) {
            imageView.setEnabled(z);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public PenBean getCurrentPenBen() {
        PenSet penSet = this.mPenSet;
        if (penSet != null) {
            return penSet.getCurPenBean();
        }
        return null;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void updateSelectColorView(boolean z) {
        this.mLeftColorView.setEnabled(z);
        this.mCenterColorView.setEnabled(z);
        this.mRightColorView.setEnabled(z);
        this.mColorView.setEnabled(z);
        this.mLeftColorView.setGrayedOut(!z);
        this.mCenterColorView.setGrayedOut(!z);
        this.mRightColorView.setGrayedOut(!z);
        this.mColorView.setGrayedOut(!z);
        if (z) {
            return;
        }
        resetPenColorPopupWindow();
    }

    private void resetPenColorPopupWindow() {
        ColorPickerPopupWindow colorPickerPopupWindow = this.mPenColorPopupWindow;
        if (colorPickerPopupWindow != null) {
            if (colorPickerPopupWindow.getFloatingPickerIsShowing()) {
                this.mPenColorPopupWindow.dismissFloatingPickerView();
                this.mPenColorPopupWindow.resetView();
            }
            if (this.mPenColorPopupWindow.isShowing()) {
                this.mPenColorPopupWindow.dismiss();
            }
        }
    }

    private void showColorPickerPopupWindow(View view) {
        this.mPenColorPopupWindow.setColor(getCurrentPenBen().getHSLColor());
        this.mPenColorPopupWindow.addOnColorChangeListener(new ColorPickerPopupWindow.OnColorPickerColorChangeListener() { // from class: com.miui.handwriting.common.view.toolbar.MiuiToolBoxView$$ExternalSyntheticLambda3
            @Override // com.miui.handwriting.common.view.popup.ColorPickerPopupWindow.OnColorPickerColorChangeListener
            public final void onColorChange(ColorPickerPopupWindow.ColorPickerChangeEvent colorPickerChangeEvent) {
                this.f$0.m815x7d296ec1(colorPickerChangeEvent);
            }
        });
        int iInitPopupWindow = this.mPenColorPopupWindow.initPopupWindow();
        if (UIUtils.isSmallScreenForBrush(this.mContext)) {
            this.mPenColorPopupWindow.mContentView.setBackgroundColor(0);
        }
        this.mPenColorPopupWindow.showAsDropDown(view, calculatePopupWindowOffset(view, false), iInitPopupWindow - 10);
    }

    /* JADX INFO: renamed from: lambda$showColorPickerPopupWindow$14$com-miui-handwriting-common-view-toolbar-MiuiToolBoxView, reason: not valid java name */
    /* synthetic */ void m815x7d296ec1(ColorPickerPopupWindow.ColorPickerChangeEvent colorPickerChangeEvent) {
        HSLColor hslColor = colorPickerChangeEvent.getHslColor();
        Log.d(TAG, "color changed index is : " + hslColor + ", mPenBean.getCheckedItemIndex()=");
        btnClick();
        getCurrentPenBen().setHSLColor(hslColor);
        getCurrentPenBen().setDefaultHSLArray(getCurrentPenBen().getCheckedItemIndex(), hslColor);
        getCurrentPenBen().setPenAlpha(hslColor.getAlpha());
        this.mCurrentSelectColorView.setHSLColor(hslColor);
        setPenProp(getCurrentPenBen().convert());
        updateBrushBg();
        setPenBeanToQuickColorMenu();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void handleClickColorPick(View view) {
        if (this.mCurrentSelectColorView == view || this.mColorView == view) {
            showColorPickerPopupWindow(view);
            return;
        }
        setTouchEvent(1);
        this.mCurrentSelectColorView = (ColorSelectView) view;
        ColorSelectView colorSelectView = this.mLeftColorView;
        colorSelectView.setCheck(colorSelectView.getId() == view.getId());
        ColorSelectView colorSelectView2 = this.mCenterColorView;
        colorSelectView2.setCheck(colorSelectView2.getId() == view.getId());
        ColorSelectView colorSelectView3 = this.mRightColorView;
        colorSelectView3.setCheck(colorSelectView3.getId() == view.getId());
        getCurrentPenBen().setHSLColor(this.mCurrentSelectColorView.getHSLColor());
        getCurrentPenBen().setPenAlpha(this.mCurrentSelectColorView.getHSLColor().getAlpha());
        setPenProp(getCurrentPenBen().convert());
        PenBean currentPenBen = getCurrentPenBen();
        if (this.mLeftColorView.isCheck()) {
            currentPenBen.setCheckedItemIndex(0);
        } else if (this.mCenterColorView.isCheck()) {
            currentPenBen.setCheckedItemIndex(1);
        } else if (this.mRightColorView.isCheck()) {
            currentPenBen.setCheckedItemIndex(2);
        }
        updateBrushBg();
        setPenBeanToQuickColorMenu();
        setTouchEvent(0);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void showPenSizePopupWindow(View view) {
        Log.d(TAG, "showPenSizePopupWindow");
        PenSetPopupWindow penSetPopupWindow = this.mPenSetPopupWindow;
        if (penSetPopupWindow != null && penSetPopupWindow.isShowing()) {
            Log.e(TAG, "showPenSizePopupWindow is showing, destroy first");
            this.mPenSetPopupWindow.dismiss();
        }
        PenSetPopupWindow penSetPopupWindow2 = new PenSetPopupWindow(Utils.getActivity(getContext()), false, this.mPageInkViewModel.isUnlimited());
        this.mPenSetPopupWindow = penSetPopupWindow2;
        penSetPopupWindow2.setPenSet(this.mPenSet);
        this.mPenSetPopupWindow.setOrignBitmap(this.srcBitmap);
        this.mPenSetPopupWindow.setFocusable(true);
        this.mPenSetPopupWindow.setTouchable(true);
        this.mPenSetPopupWindow.setOutsideTouchable(true);
        this.mPenSetPopupWindow.getContentView().setFocusableInTouchMode(true);
        this.mPenSetPopupWindow.getContentView().requestFocus();
        this.mPenSetPopupWindow.setOnKeyListener(new View.OnKeyListener() { // from class: com.miui.handwriting.common.view.toolbar.MiuiToolBoxView$$ExternalSyntheticLambda12
            @Override // android.view.View.OnKeyListener
            public final boolean onKey(View view2, int i, KeyEvent keyEvent) {
                return this.f$0.m817x7ed09fd7(view2, i, keyEvent);
            }
        });
        this.mPenSetPopupWindow.setConfirmListener(new PenSetPopupWindow.OnPenSettingConfirmListener() { // from class: com.miui.handwriting.common.view.toolbar.MiuiToolBoxView.14
            @Override // com.miui.handwriting.common.view.popup.PenSetPopupWindow.OnPenSettingConfirmListener
            public void onConfirmClick(PenBean penBean) {
                MiuiToolBoxView.this.mPenSet.updatePenBean(penBean);
                MiuiToolBoxView.this.setPenProp(penBean.convert());
                MiuiToolBoxView.this.mCurrentSelectColorView.setHSLColor(MiuiToolBoxView.this.getCurrentPenBen().getHSLColor());
                MiuiToolBoxView.this.updateBrushBg();
            }

            @Override // com.miui.handwriting.common.view.popup.PenSetPopupWindow.OnPenSettingConfirmListener
            public void onReviewBitmap(PenBean penBean, Bitmap bitmap) {
                PageItemView currentItemView = MiuiToolBoxView.this.mPageInkViewModel.getCurrentItemView();
                if (currentItemView != null) {
                    currentItemView.initPreviewBitmap(penBean.convert(), bitmap);
                }
            }

            @Override // com.miui.handwriting.common.view.popup.PenSetPopupWindow.OnPenSettingConfirmListener
            public void upReivewBitmap(Map<PenType, Bitmap> map) {
                MiuiToolBoxView.this.srcBitmap.putAll(map);
            }
        });
        this.mPenSetPopupWindow.setArrowMode(8);
        this.mPenSetPopupWindow.showAsDropDown(view, calculatePopupWindowOffset(view), 0);
    }

    /* JADX INFO: renamed from: lambda$showPenSizePopupWindow$15$com-miui-handwriting-common-view-toolbar-MiuiToolBoxView, reason: not valid java name */
    /* synthetic */ boolean m817x7ed09fd7(View view, int i, KeyEvent keyEvent) {
        if (keyEvent.getAction() == 1 && keyEvent.getKeyCode() == 196) {
            if (BRUSH_SIZE_TYPES.contains(Integer.valueOf(this.mSavedSelectViewType))) {
                if (this.mSavedSelectViewType == 3) {
                    this.mPenSetPopupWindow.setPencilUp();
                } else {
                    this.mPenSetPopupWindow.setUp();
                }
            }
            return true;
        }
        if (keyEvent.getAction() != 1 || keyEvent.getKeyCode() != 197) {
            return false;
        }
        if (BRUSH_SIZE_TYPES.contains(Integer.valueOf(this.mSavedSelectViewType))) {
            if (this.mSavedSelectViewType == 3) {
                this.mPenSetPopupWindow.setPencilDown();
            } else {
                this.mPenSetPopupWindow.setDown();
            }
        }
        return true;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void showMultiPenSizePopupWindow(View view) {
        PenSetPopupWindow penSetPopupWindow = this.mPenSizePopupWindow;
        if (penSetPopupWindow != null && penSetPopupWindow.isShowing()) {
            this.mPenSizePopupWindow.dismiss();
            this.mPenSizePopupWindow = null;
        }
        PenSetPopupWindow penSetPopupWindow2 = new PenSetPopupWindow(Utils.getActivity(getContext()), true, this.mPageInkViewModel.isUnlimited());
        this.mPenSizePopupWindow = penSetPopupWindow2;
        penSetPopupWindow2.setPenSet(this.mPenSet);
        this.mPenSizePopupWindow.setConfirmListener(new PenSetPopupWindow.OnPenSettingConfirmListener() { // from class: com.miui.handwriting.common.view.toolbar.MiuiToolBoxView.15
            @Override // com.miui.handwriting.common.view.popup.PenSetPopupWindow.OnPenSettingConfirmListener
            public void upReivewBitmap(Map<PenType, Bitmap> map) {
            }

            @Override // com.miui.handwriting.common.view.popup.PenSetPopupWindow.OnPenSettingConfirmListener
            public void onConfirmClick(PenBean penBean) {
                MiuiToolBoxView.this.setPenProp(penBean.convert());
            }

            @Override // com.miui.handwriting.common.view.popup.PenSetPopupWindow.OnPenSettingConfirmListener
            public void onReviewBitmap(PenBean penBean, Bitmap bitmap) {
                PageItemView currentItemView = MiuiToolBoxView.this.mPageInkViewModel.getCurrentItemView();
                if (currentItemView != null) {
                    currentItemView.initPreviewBitmap(penBean.convert(), bitmap);
                }
            }
        });
        initSizePopWindowViewData(this.mContext);
        this.mPenSizePopupWindow.setArrowMode(8);
        this.mPenSizePopupWindow.setBrushChangeListener(this.toolClickListener);
        if (!RomUtils.isUseNewCreationAI()) {
            this.mPenSizePopupWindow.setAiType(this.aiType);
            this.mPenSizePopupWindow.setAiSelectListener(this.aiPenClickListener);
        }
        View brushLocation = this.mPenSizePopupWindow.getBrushLocation(this.mSavedSelectViewType);
        this.mCurrentMultiSelectToolView = brushLocation;
        this.mPenSizePopupWindow.setBrushLocation(brushLocation);
        this.mPenSizePopupWindow.updatePopupWindow();
        this.mPenSizePopupWindow.showAsDropDown(view, calculatePopupWindowOffset(view), calculatePopupWindowOffsetY(this.mPenSizePopupWindow));
        this.mPenSizePopupWindow.updatePenSet(!this.isAiBrush);
    }

    private String getSelectBrush() {
        String str;
        PenBean currentPenBen = getCurrentPenBen();
        if (currentPenBen.getPenType().getValue() == 2) {
            this.mCurrentMultiSelectToolView = this.mPenSizePopupWindow.mBrushBallpoint;
            str = Constants.TOOL_BRUSH_BALLPOINT;
        } else if (currentPenBen.getPenType().getValue() == 3) {
            this.mCurrentMultiSelectToolView = this.mPenSizePopupWindow.mBrushPencil;
            str = Constants.TOOL_BRUSH_PENCIL;
        } else if (currentPenBen.getPenType().getValue() == 1) {
            this.mCurrentMultiSelectToolView = this.mPenSizePopupWindow.mBrushInk;
            str = Constants.TOOL_BRUSH_INK;
        } else if (currentPenBen.getPenType().getValue() == 4) {
            this.mCurrentMultiSelectToolView = this.mPenSizePopupWindow.mBrushMarkPen;
            str = Constants.TOOL_BRUSH_MARKPEN;
        } else if (currentPenBen.getPenType().getValue() != 10) {
            str = "";
        } else {
            this.mCurrentMultiSelectToolView = this.mPenSizePopupWindow.mBrushWaterColor;
            str = Constants.TOOL_BRUSH_WATERCOLOR;
        }
        if (isAiSelected()) {
            this.mCurrentMultiSelectToolView = this.mPenSizePopupWindow.mAiSelect;
        }
        Log.i(TAG, "getSelectBrush selectBrush=" + currentPenBen.getPenType());
        return str;
    }

    private void initSizePopWindowViewData(Context context) {
        updateBrushViewState(this.mPenSizePopupWindow.mBrushBallpoint, Constants.TOOL_BRUSH_BALLPOINT, this.mPenSet.getPenBeanByType(PenType.BALL).getPenColor(), true);
        updateBrushViewState(this.mPenSizePopupWindow.mBrushPencil, Constants.TOOL_BRUSH_PENCIL, this.mPenSet.getPenBeanByType(PenType.PENCIL).getPenColor(), true);
        updateBrushViewState(this.mPenSizePopupWindow.mBrushInk, Constants.TOOL_BRUSH_INK, this.mPenSet.getPenBeanByType(PenType.INK).getPenColor(), true);
        updateBrushViewState(this.mPenSizePopupWindow.mBrushMarkPen, Constants.TOOL_BRUSH_MARKPEN, this.mPenSet.getPenBeanByType(PenType.MARK).getPenColor(), true);
        updateBrushViewState(this.mPenSizePopupWindow.mBrushWaterColor, Constants.TOOL_BRUSH_WATERCOLOR, this.mPenSet.getPenBeanByType(PenType.WATER_COLOR).getPenColor(), true);
        updateToolViewState(this.mPenSizePopupWindow.mAiSelect, Constants.TOOL_AI_SELECT, true);
    }

    private void setMultiBrushData() {
        updateBrushBg();
        this.mPenSizePopupWindow.refreshPopWindow(true);
    }

    private boolean isToolsSelected() {
        if (this.isEraser || this.isSelect || this.isAISelect) {
            Log.d(TAG, "isToolsSelected == true, isEraser " + this.isEraser + " isSelect " + this.isSelect);
            return true;
        }
        Log.d(TAG, "isToolsSelected == false");
        return false;
    }

    private int calculatePopupWindowOffsetY(PenSetPopupWindow penSetPopupWindow) {
        double d;
        double d2;
        penSetPopupWindow.setWidth(-2);
        penSetPopupWindow.setHeight(-2);
        View contentView = penSetPopupWindow.getContentView();
        contentView.measure(0, 0);
        int measuredHeight = contentView.getMeasuredHeight();
        if (getSelectBrush().equals(Constants.TOOL_BRUSH_PENCIL)) {
            d = measuredHeight;
            d2 = 1.2d;
        } else if (getSelectBrush().equals(Constants.TOOL_BRUSH_INK)) {
            d = measuredHeight;
            d2 = 1.1d;
        } else {
            d = measuredHeight;
            d2 = 1.4d;
        }
        int i = (int) (d * d2);
        int multiHeight = DisplayUtils.getMultiHeight(Utils.getActivity(this.mContext));
        float dimension = this.mContext.getResources().getDimension(R.dimen.creation_tools_head_height);
        float dimension2 = this.mContext.getResources().getDimension(R.dimen.creation_tool_bar_topmargin);
        if (!UIUtils.isSmallScreenForBrush(this.mContext) || multiHeight >= i + dimension + dimension2) {
            return 0;
        }
        return (int) (-((dimension - UIUtils.getStatusBarHeight(this.mContext)) - dimension2));
    }

    private boolean isAiSelected() {
        boolean z = this.mSavedSelectViewType == 8;
        Log.e(TAG, "isAiSelected " + z);
        return z;
    }

    private int calculatePopupWindowOffset(View view) {
        return calculatePopupWindowOffset(view, true);
    }

    private int calculatePopupWindowOffset(View view, boolean z) {
        Resources resources;
        int i;
        if (UIUtils.isSmallScreenForBrush(this.mContext)) {
            resources = getContext().getResources();
            i = R.dimen.creation_color_pick_popup_window_egde_small;
        } else {
            resources = getContext().getResources();
            i = R.dimen.creation_color_pick_popup_window_egde;
        }
        return calculatePopupWindowOffset(view, resources.getDimensionPixelOffset(i), z);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public int calculateMorePopupWindowOffset(PopupWindow popupWindow) {
        int dimensionPixelOffset;
        if (UIUtils.isSmallScreenForBrush(this.mContext)) {
            dimensionPixelOffset = getContext().getResources().getDimensionPixelOffset(R.dimen.creation_more_action_popup_window_egde_small);
        } else {
            dimensionPixelOffset = getContext().getResources().getDimensionPixelOffset(R.dimen.creation_more_action_popup_window_egde);
        }
        int i = -dimensionPixelOffset;
        if (UIUtils.isActiveDekstopMode(this.mContext) && DisplayUtils.isRTL() && (popupWindow instanceof MoreActionPopupWindow)) {
            popupWindow.setWidth(-2);
            popupWindow.setHeight(-2);
            View contentView = popupWindow.getContentView();
            contentView.measure(0, 0);
            int measuredWidth = contentView.getMeasuredWidth();
            if (DisplayUtils.getMultiHeight(Utils.getActivity(this.mContext)) < contentView.getMeasuredHeight() + this.mContext.getResources().getDimension(R.dimen.creation_tools_head_height) + this.mContext.getResources().getDimension(R.dimen.creation_tool_bar_topmargin) + UIUtils.getStatusBarHeight(this.mContext)) {
                return (-measuredWidth) + i;
            }
        }
        return i;
    }

    private float getLogoEmptyRatio(View view) {
        int id = view.getId();
        return (R.id.creation_brush_eraser == id || R.id.creation_brush_markpen == id) ? 0.1f : 0.13157895f;
    }

    /* JADX WARN: Code duplicated, block: B:19:0x0084  */
    private int calculatePopupWindowOffset(View view, int i, boolean z) {
        int iMin;
        int width = (int) (view.getWidth() * getLogoEmptyRatio(view));
        int[] iArr = new int[2];
        view.getLocationInWindow(iArr);
        int width2 = iArr[0] + (view.getWidth() / 2);
        int dimensionPixelOffset = getContext().getResources().getDimensionPixelOffset(R.dimen.creation_color_pick_popup_window_width) / 2;
        int i2 = DisplayUtils.isRTL() ? width2 + dimensionPixelOffset : width2 - dimensionPixelOffset;
        Log.d(TAG, "getWidth:" + getWidth());
        if (DisplayUtils.isRTL()) {
            int windowWidth = DisplayUtils.getWindowWidth(Utils.getActivity(this.mContext));
            Log.d(TAG, "windowWidth:" + windowWidth);
            if (i2 > windowWidth) {
                iMin = i;
            } else if (i2 >= windowWidth - i) {
                iMin = i - (windowWidth - i2);
            } else {
                if (!z) {
                    width = 0;
                }
                iMin = Math.min(width, (windowWidth - i2) - i);
            }
        } else if (i2 < 0) {
            iMin = i;
        } else if (i2 <= i) {
            iMin = i - i2;
        } else {
            if (!z) {
                width = 0;
            }
            iMin = -Math.min(width, i2 - i);
        }
        Log.d(TAG, "x:" + i2);
        Log.d(TAG, "edge:" + i);
        Log.d(TAG, "offest:" + iMin);
        return iMin;
    }

    @Override // android.view.ViewGroup, android.view.View
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        PenSetPopupWindow penSetPopupWindow = this.mPenSizePopupWindow;
        if (penSetPopupWindow != null && penSetPopupWindow.isShowing()) {
            this.mPenSizePopupWindow.dismiss();
        }
        this.mPageInkViewModel.getMiCurveEngine().getMathDataManager().removeMathSelectCallBack(this.mathSelectCallBack);
    }

    @Override // android.view.View
    protected void onConfigurationChanged(Configuration configuration) {
        super.onConfigurationChanged(configuration);
        dismissPopupWindow();
        Log.d(TAG, "onConfigurationChanged: small sw: " + this.mContext.getResources().getConfiguration().smallestScreenWidthDp + ", sw: " + this.mContext.getResources().getConfiguration().screenWidthDp + ", sh: " + this.mContext.getResources().getConfiguration().screenHeightDp);
        AutoDensityConfig.forceUpdateDensity(this.mContext);
        Log.d(TAG, "onConfigurationChanged: sw: " + this.mContext.getResources().getConfiguration().screenWidthDp + ", sh: " + this.mContext.getResources().getConfiguration().screenHeightDp);
        DisplayCutoutHolder.INSTANCE.onConfigChanged(this.mContext);
        initPadding();
        dismissPopupWindow();
        boolean zIsSmallScreenForBrush = UIUtils.isSmallScreenForBrush(this.mContext);
        initBrushView(zIsSmallScreenForBrush);
        resetPenColorPopupWindow();
        createPopupWindows(getContext());
        initDialogKeyListener();
        initToolViewSize();
        initToolBoxViewData(configuration);
        initToolBoxViewDim();
        initColorView(UIUtils.isSmallScreenNoShare(this.mContext));
        if (!zIsSmallScreenForBrush) {
            setSelectBrushForBig();
        } else {
            setSelectBrushForSmall();
        }
        updateBrushBg(configuration);
        setColorToolParams();
        updateToolBarBg();
        initBrushDisplaySequence();
        applyLimitedToolbarIfNeeded();
        this.isKeyBoardVisInSmallWindow = false;
    }

    private void updateToolBarBg() {
        this.mToolParent.setBackgroundColor(this.mContext.getResources().getColor(R.color.creation_action_bar_bg));
        ImageView imageView = this.mToolBack;
        ImageView imageView2 = (ImageView) this.mToolRedo;
        ImageView imageView3 = (ImageView) this.mToolUndo;
        ImageView imageView4 = this.mToolShare;
        ImageView imageView5 = (ImageView) this.mToolMore;
        imageView2.setImageDrawable(this.mContext.getResources().getDrawable(R.drawable.creation_action_redo));
        imageView3.setImageDrawable(this.mContext.getResources().getDrawable(R.drawable.creation_action_undo));
        if (!this.mLimitedToolbar) {
            imageView5.setImageDrawable(this.mContext.getResources().getDrawable(R.drawable.creation_action_more));
        }
        if (this.isPc) {
            imageView4.setImageDrawable(ContextCompat.getDrawable(this.mContext, R.drawable.creation_pc_finish));
            imageView.setImageDrawable(ContextCompat.getDrawable(this.mContext, R.drawable.creation_pc_cancel));
        } else {
            imageView4.setImageDrawable(ContextCompat.getDrawable(this.mContext, R.drawable.creation_action_share));
            imageView.setImageDrawable(ContextCompat.getDrawable(this.mContext, R.drawable.creation_action_back));
        }
    }

    private void setSelectBrushForBig() {
        View[] viewArr = {this.mBrushInk, this.mBrushMarkPen, this.mBrushBallpoint, this.mBrushPencil, this.mBrushWaterColor, this.mToolRectSelect, this.mToolAI, this.mBrushEraser};
        this.mCurrentSelectToolView = getToolViewByType(this.mSavedSelectViewType);
        for (int i = 0; i < 8; i++) {
            View view = viewArr[i];
            if (view != this.mCurrentSelectToolView) {
                Folme.useAt(view).state().to(ViewProperty.TRANSLATION_Y, Float.valueOf(this.mBrushTransY));
            }
        }
        View view2 = this.mCurrentSelectToolView;
        if (view2 != null) {
            Folme.useAt(view2).state().to(ViewProperty.TRANSLATION_Y, 0);
        }
    }

    private void setSelectBrushForSmall() {
        View[] viewArr = {this.mBrushSelect, this.mToolRectSelect, this.mBrushEraser};
        View toolViewByTypeInSmall = getToolViewByTypeInSmall(this.mSavedSelectViewType);
        this.mCurrentSelectToolView = toolViewByTypeInSmall;
        this.mCurrentMultiSelectToolView = toolViewByTypeInSmall;
        for (int i = 0; i < 3; i++) {
            View view = viewArr[i];
            if (view != this.mCurrentMultiSelectToolView) {
                Folme.useAt(view).state().to(ViewProperty.TRANSLATION_Y, Float.valueOf(this.mBrushTransY));
            }
        }
        View view2 = this.mCurrentMultiSelectToolView;
        if (view2 != null) {
            Folme.useAt(view2).state().to(ViewProperty.TRANSLATION_Y, 0);
        }
    }

    private View getToolViewByType(int i) {
        if (i == 1) {
            return this.mBrushInk;
        }
        if (i == 2) {
            return this.mBrushBallpoint;
        }
        if (i == 4) {
            return this.mBrushMarkPen;
        }
        if (i == 10) {
            return this.mBrushWaterColor;
        }
        if (i == 6) {
            return this.mToolRectSelect;
        }
        if (i == 7) {
            return this.mBrushEraser;
        }
        if (i == 8) {
            return this.mToolAI;
        }
        return this.mBrushPencil;
    }

    private View getToolViewByTypeInSmall(int i) {
        if (i == 6) {
            return this.mToolRectSelect;
        }
        if (i == 7) {
            return this.mBrushEraser;
        }
        return this.mBrushSelect;
    }

    public PagePreviewData getCurrentPagePreView() {
        int currentPosition = this.mPageInkViewModel.getCurrentPosition();
        Log.d(TAG, "currentPosition:" + currentPosition);
        List<PagePreviewData> list = this.multiPreviewDataList;
        if (list == null || list.isEmpty()) {
            Log.d(TAG, "empty:");
            return new PagePreviewData();
        }
        return this.multiPreviewDataList.get(currentPosition);
    }

    @Override // com.miui.handwriting.common.view.toolbar.IMiuiHandToolbar
    public void onPreviewUpdate(List<PagePreviewData> list) {
        this.multiPreviewDataList = list;
        Log.d(TAG, "onPreviewUpdate" + list.size());
    }

    @Override // com.miui.handwriting.common.view.toolbar.IMiuiHandToolbar
    public void setCanWrite(boolean z) {
        setVisibility(z ? 0 : 8);
    }

    @Override // com.miui.handwriting.common.view.toolbar.ICommonMiuiHandToolbar
    public void setToolbarCheck(ICheck iCheck, boolean z) {
        this.mCheck = iCheck;
    }

    @Override // android.view.View, android.view.KeyEvent.Callback
    public boolean onKeyLongPress(int i, KeyEvent keyEvent) {
        Log.e(TAG, "onKeyLongPress: " + keyEvent.getKeyCode());
        if (this.mPageInkViewModel.isDrawing()) {
            return true;
        }
        if (keyEvent.getKeyCode() == 93) {
            Log.e(TAG, "onKeyLongPress: long pres!");
            dismissDialog();
            this.isLongPress = true;
            setEraser();
            return true;
        }
        return super.onKeyLongPress(i, keyEvent);
    }

    @Override // android.view.View, android.view.KeyEvent.Callback
    public boolean onKeyDown(int i, KeyEvent keyEvent) {
        Log.e(TAG, "onKeyDown: " + i);
        if (i == 93 || i == 92) {
            if (keyEvent.getRepeatCount() == 0) {
                keyEvent.startTracking();
                this.isLongPress = false;
            } else {
                this.isLongPress = true;
            }
            return true;
        }
        return super.onKeyDown(i, keyEvent);
    }

    @Override // android.view.View, android.view.KeyEvent.Callback
    public boolean onKeyUp(int i, KeyEvent keyEvent) {
        Log.e(TAG, "onKeyUp: " + keyEvent.getKeyCode() + ", isLongPress -> " + this.isLongPress);
        if (this.mPageInkViewModel.isDrawing()) {
            return true;
        }
        int keyCode = keyEvent.getKeyCode();
        if (keyCode == 92) {
            if (!this.isLongPress) {
                dismissDialog();
                changeColor();
            }
            return true;
        }
        if (keyCode == 93) {
            if (isCalculatorMode()) {
                return true;
            }
            doAboutPrimaryKey();
            return true;
        }
        switch (keyCode) {
            case 194:
                int i2 = Settings.System.getInt(this.mContext.getContentResolver(), "stylus_pinch_status", 5);
                if (i2 == 1) {
                    StylusPenQuickMenu stylusPenQuickMenu = this.mStylusPenQuickMenu;
                    if (stylusPenQuickMenu != null) {
                        stylusPenQuickMenu.dismiss();
                    }
                    changeBrushAndEraser();
                } else if (i2 == 2 && !isCalculatorMode()) {
                    StylusPenQuickMenu stylusPenQuickMenu2 = this.mStylusPenQuickMenu;
                    if (stylusPenQuickMenu2 != null) {
                        stylusPenQuickMenu2.dismiss();
                    }
                    changeLastBrush();
                }
                if (i2 == -1 && !isCalculatorMode()) {
                    doAboutPrimaryKey();
                }
                return true;
            case 195:
                int i3 = Settings.System.getInt(this.mContext.getContentResolver(), "stylus_double_click_status", 1);
                if (i3 == 1) {
                    changeBrushAndEraser();
                    StylusPenQuickMenu stylusPenQuickMenu3 = this.mStylusPenQuickMenu;
                    if (stylusPenQuickMenu3 != null) {
                        stylusPenQuickMenu3.dismiss();
                    }
                } else if (i3 == 2 && !isCalculatorMode()) {
                    changeLastBrush();
                    StylusPenQuickMenu stylusPenQuickMenu4 = this.mStylusPenQuickMenu;
                    if (stylusPenQuickMenu4 != null) {
                        stylusPenQuickMenu4.dismiss();
                    }
                }
                if (i3 == -1 && !isCalculatorMode()) {
                    doAboutPrimaryKey();
                }
                return true;
            case 196:
                if (BRUSH_SIZE_TYPES.contains(Integer.valueOf(this.mSavedSelectViewType))) {
                    changeBrushSizeUp();
                }
                return true;
            case R2.array.chinese_symbol_animals /* 197 */:
                if (BRUSH_SIZE_TYPES.contains(Integer.valueOf(this.mSavedSelectViewType))) {
                    changeBrushSizeDown();
                }
                return true;
            default:
                return super.onKeyUp(i, keyEvent);
        }
    }

    private void changeBrushAndEraser() {
        View view = this.mCurrentSelectToolView;
        View view2 = this.mBrushEraser;
        if (view == view2) {
            if (this.isAiBrush) {
                changeBrush(8);
                return;
            }
            int iIndexOf = this.mBrushDisplaySequence.indexOf(Integer.valueOf(this.mPenSet.getCurPenBean().getPenType().getValue()));
            if (iIndexOf >= 0) {
                changeBrush(this.mBrushDisplaySequence.get(iIndexOf).intValue());
                return;
            } else {
                changeBrush(this.mBrushDisplaySequence.get(0).intValue());
                return;
            }
        }
        activeTool(view2);
        enableEraser(getCurrentEraserPen());
    }

    private void changeLastBrush() {
        View view;
        if (isCalculatorMode() || (view = this.mCurrentSelectToolView) == this.mBrushEraser || view == this.mToolRectSelect) {
            return;
        }
        int i = this.lastPenIndex;
        if (i != -1) {
            changeBrush(i);
        }
        int i2 = this.lastPenIndex;
        int i3 = this.mSavedSelectViewType;
        if (i2 == i3 && i3 != 8) {
            int i4 = this.tempPenIndex;
            if (i4 == -1) {
                return;
            } else {
                changeBrush(i4);
            }
        }
        this.tempPenIndex = this.lastPenIndex;
    }

    private void changeBrushSizeUp() {
        float fMin;
        float penSize = this.mPenSizePopupWindow.getCurPenBean().getPenSize();
        float maxSize = this.mPenSizePopupWindow.getCurPenBean().getMaxSize();
        if (this.mSavedSelectViewType == 3) {
            fMin = Math.min(penSize + 1.0f, maxSize);
        } else {
            fMin = Math.min(penSize + 5.0f, maxSize);
        }
        this.mPenSizePopupWindow.getCurPenBean().setPenSize(fMin);
        getCurrentPenBen().convert().setPenSize(fMin);
        setPenProp(getCurrentPenBen().convert());
    }

    private void changeBrushSizeDown() {
        float fMax;
        float penSize = this.mPenSizePopupWindow.getCurPenBean().getPenSize();
        float minSize = this.mPenSizePopupWindow.getCurPenBean().getMinSize();
        if (this.mSavedSelectViewType == 3) {
            fMax = Math.max(penSize - 1.0f, minSize);
        } else {
            fMax = Math.max(penSize - 5.0f, minSize);
        }
        this.mPenSizePopupWindow.getCurPenBean().setPenSize(fMax);
        getCurrentPenBen().convert().setPenSize(fMax);
        setPenProp(getCurrentPenBen().convert());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void setEraser() {
        enableEraser(getCurrentEraserPen());
        activeTool(this.mBrushEraser);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void changeColor() {
        ColorSelectView colorSelectView;
        int checkedItemIndex = (this.mPenSet.getCurPenBean().getCheckedItemIndex() + 1) % 3;
        if (checkedItemIndex == 0) {
            colorSelectView = this.mLeftColorView;
        } else if (checkedItemIndex == 1) {
            colorSelectView = this.mCenterColorView;
        } else if (checkedItemIndex == 2) {
            colorSelectView = this.mRightColorView;
        } else {
            colorSelectView = this.mLeftColorView;
        }
        if (this.isSelect || this.isEraser || this.isAISelect) {
            return;
        }
        handleClickColorPick(colorSelectView);
    }

    private void changeBrush(int i) {
        View view;
        String str;
        PenType penType;
        updateSelectColorView(true);
        if (i == 4) {
            view = this.mBrushMarkPen;
            penType = PenType.MARK;
            str = Constants.TOOL_BRUSH_MARKPEN;
        } else if (i == 3) {
            view = this.mBrushPencil;
            penType = PenType.PENCIL;
            str = Constants.TOOL_BRUSH_PENCIL;
        } else if (i == 2) {
            view = this.mBrushBallpoint;
            penType = PenType.BALL;
            str = Constants.TOOL_BRUSH_BALLPOINT;
        } else if (i == 1) {
            view = this.mBrushInk;
            penType = PenType.INK;
            str = Constants.TOOL_BRUSH_INK;
        } else if (i == 10) {
            view = this.mBrushWaterColor;
            penType = PenType.WATER_COLOR;
            str = Constants.TOOL_BRUSH_WATERCOLOR;
        } else {
            view = this.mToolAI;
            str = Constants.TOOL_AI_SELECT;
            penType = null;
        }
        Log.d(TAG, "set penIndex= " + i + ", pen =" + str);
        if (!activeTool(view)) {
            if (penType != null) {
                selectPenType(penType);
                cancelSelect();
            } else {
                enableAI();
            }
        }
        if (penType != null) {
            updateBrushViewState(this.mBrushSelect, str, this.mPenSet.getPenBeanByType(penType).getPenColor(), true);
        } else {
            updateToolViewState(this.mBrushSelect, str, true);
        }
        updateSelectBrushParams();
        Folme.useAt(this.mBrushSelect).state().to(ViewProperty.TRANSLATION_Y, 0);
        updateSelectBrushParams();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void dismissDialog() {
        if (isToolsSelected()) {
            if (this.mEraserPopupWindow.isShowing()) {
                this.mEraserPopupWindow.dismiss();
            }
        } else {
            if (this.mPenColorPopupWindow.isShowing()) {
                this.mPenColorPopupWindow.dismiss();
            }
            if (this.mPenSetPopupWindow.isShowing()) {
                this.mPenSetPopupWindow.dismiss();
            }
            if (this.mPenSizePopupWindow.isShowing()) {
                this.mPenSizePopupWindow.dismiss();
            }
        }
        androidx.appcompat.app.AlertDialog alertDialog = this.mHotKeyPopDialog;
        if (alertDialog != null && alertDialog.isShowing()) {
            this.mHotKeyPopDialog.dismiss();
        }
        MoreActionPopupWindow moreActionPopupWindow = this.mEditMoreActionPopupWindow;
        if (moreActionPopupWindow == null || !moreActionPopupWindow.isShowing()) {
            return;
        }
        this.mEditMoreActionPopupWindow.dismiss();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void doAboutPrimaryKey() {
        dismissDialog();
        int value = this.mPenSet.getCurPenBean().getPenType().getValue();
        int iIndexOf = this.mBrushDisplaySequence.indexOf(Integer.valueOf(value));
        Log.d(TAG, "origin penindex= " + value);
        if (!this.isLongPress) {
            View view = this.mCurrentSelectToolView;
            if (view == this.mBrushEraser || view == this.mToolRectSelect) {
                if (this.isAiBrush) {
                    changeBrush(8);
                    return;
                } else {
                    changeBrush(this.mBrushDisplaySequence.get(iIndexOf).intValue());
                    return;
                }
            }
            if (isAISelect()) {
                changeBrush(this.mBrushDisplaySequence.get(0).intValue());
                return;
            } else {
                List<Integer> list = this.mBrushDisplaySequence;
                changeBrush(list.get((iIndexOf + 1) % list.size()).intValue());
                return;
            }
        }
        View view2 = this.mLastSelectToolView;
        if (view2 == this.mToolRectSelect) {
            enableSelect();
            activeTool(this.mToolRectSelect);
            return;
        }
        View view3 = this.mCurrentSelectToolView;
        View view4 = this.mBrushEraser;
        if (view3 != view4 || view2 == view4) {
            return;
        }
        if (this.isAiBrush) {
            changeBrush(8);
        } else {
            changeBrush(this.mBrushDisplaySequence.get(iIndexOf).intValue());
        }
    }

    @Override // com.miui.handwriting.common.view.toolbar.ICommonMiuiHandToolbar
    public void enableViewSelect() {
        enableFullPageSelect(false);
        enableSelect();
        activeTool(this.mToolRectSelect);
    }

    @Override // com.miui.handwriting.common.view.toolbar.IMiuiHandToolbar
    public void release() {
        saveToolBoxState();
        MoreActionPopupWindow moreActionPopupWindow = this.mEditMoreActionPopupWindow;
        if (moreActionPopupWindow != null) {
            moreActionPopupWindow.setTitleColorChangeListener(null);
        }
    }

    @Override // com.miui.handwriting.common.view.toolbar.IMiuiHandToolbar
    public boolean isFocus() {
        return hasFocus();
    }

    @Override // com.miui.handwriting.common.view.toolbar.IMiuiHandToolbar
    public int getToolbarHeight() {
        BaseMiuiHandWritingView baseMiuiHandWritingView = this.mHandWritingImpl;
        if (baseMiuiHandWritingView != null) {
            baseMiuiHandWritingView.setToolBarHeight(getHeight());
        }
        return getHeight();
    }

    @Override // com.miui.handwriting.common.view.toolbar.ICommonMiuiHandToolbar
    public void setActionCallBack(ToolBoxActionCallBack toolBoxActionCallBack) {
        this.actionCallBack = toolBoxActionCallBack;
    }

    @Override // com.miui.handwriting.common.view.toolbar.ICommonMiuiHandToolbar
    public int getToolBoxHeight() {
        return getToolbarHeight();
    }

    @Override // com.miui.handwriting.common.view.toolbar.ICommonMiuiHandToolbar
    public void enableAi(boolean z) {
        if (z) {
            enalbeAiByButtonPackage();
        } else {
            enableAiByhButton();
        }
    }

    @Override // com.miui.handwriting.common.view.toolbar.ICommonMiuiHandToolbar
    public void dismissPopupWindow() {
        PenSetPopupWindow penSetPopupWindow = this.mPenSizePopupWindow;
        if (penSetPopupWindow != null && penSetPopupWindow.isShowing()) {
            this.mPenSizePopupWindow.dismiss();
        }
        AIPenPopupWindow aIPenPopupWindow = this.mAIPenPopWindow;
        if (aIPenPopupWindow != null && aIPenPopupWindow.isShowing()) {
            this.mAIPenPopWindow.dismiss();
        }
        PenSetPopupWindow penSetPopupWindow2 = this.mPenSetPopupWindow;
        if (penSetPopupWindow2 != null && penSetPopupWindow2.isShowing()) {
            this.mPenSetPopupWindow.dismiss();
        }
        MoreActionPopupWindow moreActionPopupWindow = this.mEditMoreActionPopupWindow;
        if (moreActionPopupWindow != null && moreActionPopupWindow.isShowing()) {
            this.mEditMoreActionPopupWindow.dismiss();
        }
        ColorPickerPopupWindow colorPickerPopupWindow = this.mPenColorPopupWindow;
        if (colorPickerPopupWindow != null && colorPickerPopupWindow.isShowing()) {
            this.mPenColorPopupWindow.dismiss();
        }
        EraserPopupWindow eraserPopupWindow = this.mEraserPopupWindow;
        if (eraserPopupWindow != null && eraserPopupWindow.isShowing()) {
            this.mEraserPopupWindow.dismiss();
        }
        androidx.appcompat.app.AlertDialog alertDialog = this.mHotKeyPopDialog;
        if (alertDialog != null && alertDialog.isShowing()) {
            this.mHotKeyPopDialog.dismiss();
        }
        DropDownSingleChoiceMenu dropDownSingleChoiceMenu = this.mDropDownSingleChoiceMenu;
        if (dropDownSingleChoiceMenu != null) {
            dropDownSingleChoiceMenu.dismiss();
        }
        AlertDialog alertDialog2 = this.mPageSizeDialog;
        if (alertDialog2 != null && alertDialog2.isShowing()) {
            this.mPageSizeDialog.dismiss();
        }
        AlertDialog alertDialog3 = this.mDeletePageDialog;
        if (alertDialog3 == null || !alertDialog3.isShowing()) {
            return;
        }
        this.mDeletePageDialog.dismiss();
    }

    @Override // com.miui.libhandwritingpage.view.PageInkView.ShieldEventListener
    public void shieldEvent(boolean z) {
        if (z) {
            return;
        }
        this.aiType = 0;
        enableFullPageSelect(false);
    }

    public void pageLimitChange(boolean z) {
        if (z) {
            this.mTextThumbnail.setVisibility(8);
        } else {
            this.mTextThumbnail.setVisibility(0);
        }
    }

    public void updateStateFromQuickMenu(HSLColor hSLColor) {
        this.mCurrentSelectColorView.setHSLColor(hSLColor);
        updateBrushBg();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void setPenBeanToQuickColorMenu() {
        StylusPenQuickMenu stylusPenQuickMenu = this.mStylusPenQuickMenu;
        if (stylusPenQuickMenu != null) {
            stylusPenQuickMenu.setPenBean(getCurrentPenBen());
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void onPenSetChanged(PenBean penBean) {
        if (penBean == null) {
            return;
        }
        try {
            updateBrushBg();
            setPenBeanToQuickColorMenu();
        } catch (Exception unused) {
        }
    }

    @Override // com.miui.handwriting.common.view.toolbar.ICommonMiuiHandToolbar
    public PenSet getPenSet() {
        return this.mPenSet;
    }

    public void destroy() {
        PenSet.PenSetChangeListener penSetChangeListener;
        PenSet penSet = this.mPenSet;
        if (penSet != null && (penSetChangeListener = this.mPenSetChangeListener) != null) {
            penSet.removeListener(penSetChangeListener);
            this.mPenSetChangeListener = null;
        }
        this.mPenSet = null;
        this.mPageInkViewModel = null;
        this.mHandWritingImpl = null;
    }
}
