package com.miui.handwriting.common.ai;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Point;
import android.graphics.RectF;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import com.google.common.net.HttpHeaders;
import com.google.gson.Gson;
import com.miui.creation.common.R;
import com.miui.handwriting.common.HandWritingViewApplication;
import com.miui.handwriting.common.ai.data.CreationImage;
import com.miui.handwriting.common.ai.data.Result;
import com.miui.handwriting.common.ai.data.VersionData;
import com.miui.handwriting.common.ai.viewmodel.cache.RequestCache;
import com.miui.handwriting.common.engine.export.BitmapHolder;
import com.miui.handwriting.common.listener.CanvasElementTransactCallback;
import com.miui.handwriting.common.listener.CommonPageSaveListener;
import com.miui.handwriting.common.mask.CanvasElementTransactionMask;
import com.miui.handwriting.common.model.IMiuiHandWritingModel;
import com.miui.handwriting.common.tools.MainThreadUtils;
import com.miui.handwriting.common.tools.PreferenceUtils;
import com.miui.handwriting.common.tools.ThreadPoolHelper;
import com.miui.handwriting.common.tools.Utils;
import com.miui.handwriting.common.tools.constants.Constants;
import com.miui.handwriting.common.view.handview.CreationShieldPageInkView;
import com.miui.handwriting.common.view.handview.pop.AICreationPopupWindow;
import com.miui.handwriting.common.view.handview.pop.AICreationPopupWindowForOS1;
import com.miui.handwriting.common.view.toolbar.IMiuiHandToolbar;
import com.miui.libhandwritingpage.newbean.CanvasMaskEntity;
import com.miui.libhandwritingpage.newbean.beanCurve.CurveBean;
import com.miui.libhandwritingpage.newbean.beanPageInfo.PagePreviewData;
import com.miui.libhandwritingpage.newbean.beanTouch.TouchPosition;
import com.miui.libhandwritingpage.utils.BitmapUtil;
import com.miui.libhandwritingpage.utils.UIUtils;
import com.miui.libhandwritingpage.view.PageItemView;
import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.concurrent.atomic.AtomicBoolean;
import miuix.appcompat.app.AlertDialog;
import miuix.appcompat.app.ProgressDialog;
import miuix.micloudview.accounts.ExtraAccountManager;
import org.apache.commons.lang3.StringUtils;
import xc.engine.api.listener.DrawErrorCallback;
import xc.engine.core.algorithm.bean.StrokeClassificationCurve;
import xc.engine.data.pagedata.DataCurve;
import xc.engine.utils.FileUtil;
import xc.richtext.data.SpannedData;

/* JADX INFO: loaded from: classes3.dex */
public class AiCreationHelper {
    private static final int AI_SELECT_SPAN_TYPE_EMPTY = 0;
    private static final int AI_SELECT_SPAN_TYPE_IMG_ONLY = 2;
    private static final int AI_SELECT_SPAN_TYPE_MIXED = 3;
    private static final int AI_SELECT_SPAN_TYPE_TEXT_ONLY = 1;
    public static final int AREA_AI_FOR_OS1 = 4000;
    private static final int DELAY_TIME_FINISH_SELECT = 500;
    private static final int SELECT_ID = 10000;
    private static final int SELECT_SIZE = 1280;
    private static final String TAG = "AiCreationHelper";
    private static final String ai_tag = "Ai_Tag";
    private ProgressDialog aiDialog;
    private AlertDialog aiErrorDialog;
    private int aiViewIndex;
    private RectF animRect;
    private final AtomicBoolean isSavingBitmap = new AtomicBoolean(false);
    private final AtomicBoolean isVerifyAccount = new AtomicBoolean(false);
    private PopupWindow mAICreationPop;
    private String mAiInsertImagePath;
    private RectF mAiReplaceRectF;
    private List<TouchPosition> mAiSelectData;
    private CanvasElementTransactCallback mCanvasElementTransactCallback;
    private ClassificationCallBack mClassificationCallBack;
    private final Context mContext;
    private CreationImage mCreationImage;
    private DrawErrorCallback mDrawErrorCallback;
    private boolean mIsPartiSelect;
    private boolean mIsRecreate;
    private RectF mLastRectF_ai;
    private AlertDialog mNetworkCtaDialog;
    private NetworkPermissionCallback mNetworkPermissionCallback;
    private IMiuiHandWritingModel mPageInkViewModel;
    private List<PagePreviewData> mPreviewDataList;
    private CreationShieldPageInkView mShieldPageInkView;
    private IMiuiHandToolbar mToolBoxView;
    private CanvasElementTransactionMask mTransactionMask;
    private VerifyServer.CallBack mVerifyServerCallback;
    private RectF rectF_ai;

    static /* synthetic */ void lambda$checkNetworkForAi$8(DialogInterface dialogInterface, int i) {
    }

    public AiCreationHelper(Context context, IMiuiHandWritingModel iMiuiHandWritingModel, CreationShieldPageInkView creationShieldPageInkView, IMiuiHandToolbar iMiuiHandToolbar) {
        this.mContext = context;
        this.mPageInkViewModel = iMiuiHandWritingModel;
        this.mShieldPageInkView = creationShieldPageInkView;
        this.mToolBoxView = iMiuiHandToolbar;
    }

    /* JADX INFO: renamed from: verifyAccountForOS1, reason: merged with bridge method [inline-methods] */
    public boolean m705xf59ec7ec(final boolean z, final int i) {
        this.mNetworkPermissionCallback = new NetworkPermissionCallback() { // from class: com.miui.handwriting.common.ai.AiCreationHelper$$ExternalSyntheticLambda10
            @Override // com.miui.handwriting.common.ai.NetworkPermissionCallback
            public final void onNetworkAccepted() {
                this.f$0.m705xf59ec7ec(z, i);
            }
        };
        if (!checkNetworkForAi()) {
            return false;
        }
        this.mVerifyServerCallback = new VerifyServer.CallBack() { // from class: com.miui.handwriting.common.ai.AiCreationHelper$$ExternalSyntheticLambda11
            @Override // com.miui.handwriting.common.ai.VerifyServer.CallBack
            public final void onCallBack(Result result) {
                this.f$0.m706xf52861ed(z, i, result);
            }
        };
        checkAccountAndVerify(true);
        return false;
    }

    /* JADX INFO: renamed from: lambda$verifyAccountForOS1$1$com-miui-handwriting-common-ai-AiCreationHelper, reason: not valid java name */
    /* synthetic */ void m706xf52861ed(boolean z, final int i, Result result) {
        IMiuiHandWritingModel iMiuiHandWritingModel = this.mPageInkViewModel;
        if (iMiuiHandWritingModel != null) {
            iMiuiHandWritingModel.selectMoveEnable(true);
        }
        if (result == null || result.getCode() != 0) {
            verifyFailOption(result);
            return;
        }
        if (z) {
            Bitmap selectScreenshot = this.mPageInkViewModel.getSelectScreenshot(false);
            if (selectScreenshot == null) {
                dismissAIDialog();
                return;
            }
            List<PagePreviewData> list = this.mPreviewDataList;
            int i2 = (list == null || list.size() < this.mPageInkViewModel.getCurrentPosition() + 1) ? -1 : this.mPreviewDataList.get(this.mPageInkViewModel.getCurrentPosition()).bgColor;
            String tempDir = Utils.getTempDir();
            if (tempDir != null) {
                File file = new File(tempDir, String.valueOf(System.currentTimeMillis()));
                BitmapUtil.saveBitmapToFile(selectScreenshot, file.getAbsolutePath());
                Log.d(TAG, "tmpFile image:" + file.getAbsolutePath());
                dismissAIDialog();
                CreationAiActivityForOS1.open(Utils.getActivity(this.mContext), file.getAbsolutePath(), 2, i2, 4000);
                return;
            }
            return;
        }
        this.mPageInkViewModel.saveEntFile(new CommonPageSaveListener() { // from class: com.miui.handwriting.common.ai.AiCreationHelper.1
            @Override // com.miui.handwriting.common.listener.CommonPageSaveListener
            public void onDataSaved(boolean z2, String str, boolean z3) {
                if (AiCreationHelper.this.mPageInkViewModel.isContentEmpty(AiCreationHelper.this.mPageInkViewModel.getCurrentPosition())) {
                    Toast.makeText(AiCreationHelper.this.mContext, R.string.creation_ai_empty, 0).show();
                    AiCreationHelper.this.dismissAIDialog();
                } else {
                    if (!z2) {
                        AiCreationHelper.this.dismissAILoadingDialog();
                        Log.i(AiCreationHelper.TAG, "showAI save fail");
                        return;
                    }
                    Intent intent = new Intent();
                    intent.setAction(Constants.OPERATION_INTENT);
                    intent.putExtra("operationType", "exportToAI");
                    intent.putExtra(HttpHeaders.ReferrerPolicyValues.ORIGIN, i);
                    LocalBroadcastManager.getInstance(HandWritingViewApplication.INSTANCE.getApplication()).sendBroadcast(intent);
                }
            }
        });
    }

    public boolean verifyAccount(boolean z, boolean z2, boolean z3, boolean z4) {
        return verifyAccount(z, z2, z3, z4, null);
    }

    public boolean verifyAccount(final boolean z, final boolean z2, final boolean z3, final boolean z4, final AiCreationCheckCallback aiCreationCheckCallback) {
        this.mIsPartiSelect = z;
        this.mIsRecreate = z4;
        if (Utils.isFastClick(10000, 2000L) && !z4 && !z) {
            Log.e(TAG, "verifyAccount is fast verifyAccount");
            if (aiCreationCheckCallback != null) {
                aiCreationCheckCallback.onAiCreationCheckResult(false);
            }
            return false;
        }
        if (this.isSavingBitmap.get()) {
            Log.e(TAG, "isSavingBitmap, cannot creation again ,ignore ");
            if (aiCreationCheckCallback != null) {
                aiCreationCheckCallback.onAiCreationCheckResult(false);
            }
            return false;
        }
        if (this.isVerifyAccount.get()) {
            Log.e(TAG, "isVerifyAccount, cannot creation again ,ignore ");
            if (aiCreationCheckCallback != null) {
                aiCreationCheckCallback.onAiCreationCheckResult(false);
            }
            return false;
        }
        Log.i(TAG, "verifyAccount isFromAI:" + z2 + ",isAiError:" + z3);
        this.mNetworkPermissionCallback = new NetworkPermissionCallback() { // from class: com.miui.handwriting.common.ai.AiCreationHelper$$ExternalSyntheticLambda6
            @Override // com.miui.handwriting.common.ai.NetworkPermissionCallback
            public final void onNetworkAccepted() {
                this.f$0.m702x314c40aa(z2, z3, z4, aiCreationCheckCallback);
            }
        };
        if (!checkNetworkForAi()) {
            if (aiCreationCheckCallback != null) {
                aiCreationCheckCallback.onAiCreationCheckResult(false);
            }
            return false;
        }
        this.mVerifyServerCallback = new VerifyServer.CallBack() { // from class: com.miui.handwriting.common.ai.AiCreationHelper$$ExternalSyntheticLambda7
            @Override // com.miui.handwriting.common.ai.VerifyServer.CallBack
            public final void onCallBack(Result result) throws Throwable {
                this.f$0.m704x305f74ac(aiCreationCheckCallback, z2, z3, z, z4, result);
            }
        };
        this.isVerifyAccount.set(true);
        if (checkAccountAndVerify(false)) {
            return true;
        }
        this.isVerifyAccount.set(false);
        if (aiCreationCheckCallback != null) {
            aiCreationCheckCallback.onAiCreationCheckResult(false);
        }
        return false;
    }

    /* JADX INFO: renamed from: lambda$verifyAccount$2$com-miui-handwriting-common-ai-AiCreationHelper, reason: not valid java name */
    /* synthetic */ void m702x314c40aa(boolean z, boolean z2, boolean z3, AiCreationCheckCallback aiCreationCheckCallback) {
        verifyAccount(false, z, z2, z3, aiCreationCheckCallback);
    }

    /* JADX INFO: renamed from: lambda$verifyAccount$4$com-miui-handwriting-common-ai-AiCreationHelper, reason: not valid java name */
    /* synthetic */ void m704x305f74ac(AiCreationCheckCallback aiCreationCheckCallback, boolean z, final boolean z2, final boolean z3, final boolean z4, Result result) throws Throwable {
        CanvasMaskEntity selectViewAndMask;
        final Bitmap selectScreenshot;
        IMiuiHandWritingModel iMiuiHandWritingModel = this.mPageInkViewModel;
        if (iMiuiHandWritingModel != null) {
            iMiuiHandWritingModel.selectMoveEnable(true);
        }
        this.isVerifyAccount.set(false);
        if (result == null || result.getCode() != 0) {
            verifyFailOption(result);
            return;
        }
        if (aiCreationCheckCallback != null) {
            aiCreationCheckCallback.onAiCreationCheckResult(true);
        }
        if (!z || z2) {
            Log.i(TAG, "onCallBack: not isFromAI or isAIError!");
            selectViewAndMask = getSelectViewAndMask(false);
            if (z3) {
                try {
                    selectScreenshot = BitmapFactory.decodeFile(this.mPageInkViewModel.getAiBitmapPath());
                } catch (Exception e) {
                    e.printStackTrace();
                    selectScreenshot = this.mPageInkViewModel.getSelectScreenshot(true);
                }
            } else {
                selectScreenshot = this.mPageInkViewModel.getSelectScreenshot(true);
            }
        } else {
            Log.i(TAG, "onCallBack: isFromAI!");
            selectScreenshot = BitmapHolder.INSTANCE.getBitmapByAllSelect();
            selectViewAndMask = BitmapHolder.INSTANCE.getCanvasBitmapsBySelect();
        }
        final CanvasMaskEntity canvasMaskEntity = selectViewAndMask;
        if (selectScreenshot == null) {
            Log.e(TAG, "selectScreenshot is null");
            dismissAIDialog();
            return;
        }
        List<PagePreviewData> list = this.mPreviewDataList;
        final int i = (list == null || list.size() < this.mPageInkViewModel.getCurrentPosition() + 1) ? -1 : this.mPreviewDataList.get(this.mPageInkViewModel.getCurrentPosition()).bgColor;
        String tempDir = Utils.getTempDir();
        if (tempDir != null) {
            final File file = new File(tempDir, String.valueOf(System.currentTimeMillis()));
            if (this.isSavingBitmap.get()) {
                Log.e(TAG, "isSavingBitmap, cannot creation again ,ignore ");
            } else {
                this.mPageInkViewModel.selectMoveEnable(false);
                ThreadPoolHelper.getInstance().executeWithCallback(new Runnable() { // from class: com.miui.handwriting.common.ai.AiCreationHelper$$ExternalSyntheticLambda3
                    @Override // java.lang.Runnable
                    public final void run() {
                        this.f$0.m703x30d5daab(selectScreenshot, file);
                    }
                }, new ThreadPoolHelper.ExecuteCallback() { // from class: com.miui.handwriting.common.ai.AiCreationHelper.2
                    @Override // com.miui.handwriting.common.tools.ThreadPoolHelper.ExecuteCallback
                    public void onSuccess() {
                        String str;
                        Log.d(AiCreationHelper.TAG, "tmpFile image:" + file.getAbsolutePath());
                        AiCreationHelper.this.dismissAIDialog();
                        if (AiCreationHelper.this.mCanvasElementTransactCallback != null) {
                            RectF newSelectSaveRectF = AiCreationHelper.this.mPageInkViewModel.getNewSelectSaveRectF(AiCreationHelper.this.rectF_ai, AiCreationHelper.this.aiViewIndex);
                            RectF selectRectFWithoutOffset = AiCreationHelper.this.mPageInkViewModel.getSelectRectFWithoutOffset(AiCreationHelper.this.rectF_ai, AiCreationHelper.this.aiViewIndex);
                            Log.d(AiCreationHelper.TAG, "rectF :" + selectRectFWithoutOffset + ", origin: " + newSelectSaveRectF);
                            if (selectRectFWithoutOffset != null && !selectRectFWithoutOffset.isEmpty()) {
                                SpannedData clickImage = AiCreationHelper.this.mPageInkViewModel.getClickImage();
                                if (z4 || z3) {
                                    String strGenerateKey = (clickImage == null || clickImage.mBitmapPath == null) ? null : RequestCache.INSTANCE.generateKey(clickImage.mBitmapPath);
                                    BitmapHolder bitmapHolder = BitmapHolder.INSTANCE;
                                    if (z2) {
                                        newSelectSaveRectF = null;
                                    }
                                    bitmapHolder.setBitmapReactF(newSelectSaveRectF);
                                    Log.i(AiCreationHelper.TAG, "recreateKey :" + strGenerateKey);
                                    str = strGenerateKey;
                                } else {
                                    str = null;
                                }
                                if (!z2 && !z4 && !z3) {
                                    BitmapHolder.INSTANCE.setTransactRect(AiCreationHelper.this.animRect);
                                } else {
                                    BitmapHolder.INSTANCE.setTransactRect(selectRectFWithoutOffset);
                                }
                                float f = clickImage != null ? clickImage.mDegrees : 0.0f;
                                Log.d(AiCreationHelper.TAG, "verifyAccount onCallBack, select rect: " + selectRectFWithoutOffset);
                                if (canvasMaskEntity != null) {
                                    AiCreationHelper.this.mCanvasElementTransactCallback.onTransactCanvasElement(selectRectFWithoutOffset, canvasMaskEntity, f, i, file.getAbsolutePath(), z4, str, z3);
                                } else {
                                    Log.e(AiCreationHelper.TAG, "onCallBack: bitmapArr is null or size < 2");
                                }
                            }
                        }
                        AiCreationHelper.this.isSavingBitmap.set(false);
                        AiCreationHelper.this.mPageInkViewModel.selectMoveEnable(true);
                    }

                    @Override // com.miui.handwriting.common.tools.ThreadPoolHelper.ExecuteCallback
                    public void onError(String str) {
                        Log.d(AiCreationHelper.TAG, "onError:" + str);
                        AiCreationHelper.this.dismissAIDialog();
                        AiCreationHelper.this.isSavingBitmap.set(false);
                        AiCreationHelper.this.mPageInkViewModel.selectMoveEnable(true);
                    }
                });
            }
        }
    }

    /* JADX INFO: renamed from: lambda$verifyAccount$3$com-miui-handwriting-common-ai-AiCreationHelper, reason: not valid java name */
    /* synthetic */ void m703x30d5daab(Bitmap bitmap, File file) {
        this.isSavingBitmap.set(true);
        BitmapUtil.saveBitmapToFile(bitmap, file.getAbsolutePath());
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX INFO: renamed from: verifyAccountForText, reason: merged with bridge method [inline-methods] */
    public boolean m707x4f15cb95(final String str) {
        if (Utils.isFastClick(10000, 2000L)) {
            Log.v(TAG, "verifyAccountForText is too fast");
            return false;
        }
        this.mNetworkPermissionCallback = new NetworkPermissionCallback() { // from class: com.miui.handwriting.common.ai.AiCreationHelper$$ExternalSyntheticLambda1
            @Override // com.miui.handwriting.common.ai.NetworkPermissionCallback
            public final void onNetworkAccepted() {
                this.f$0.m707x4f15cb95(str);
            }
        };
        if (!checkNetworkForAi()) {
            return false;
        }
        this.mVerifyServerCallback = new VerifyServer.CallBack() { // from class: com.miui.handwriting.common.ai.AiCreationHelper$$ExternalSyntheticLambda2
            @Override // com.miui.handwriting.common.ai.VerifyServer.CallBack
            public final void onCallBack(Result result) throws Throwable {
                this.f$0.m708x4e9f6596(str, result);
            }
        };
        checkAccountAndVerify(false);
        return false;
    }

    /* JADX INFO: renamed from: lambda$verifyAccountForText$6$com-miui-handwriting-common-ai-AiCreationHelper, reason: not valid java name */
    /* synthetic */ void m708x4e9f6596(String str, Result result) throws Throwable {
        RectF selectRectFWithoutOffset;
        IMiuiHandWritingModel iMiuiHandWritingModel = this.mPageInkViewModel;
        if (iMiuiHandWritingModel != null) {
            iMiuiHandWritingModel.selectMoveEnable(true);
        }
        if (result != null && result.getCode() == 0) {
            CanvasMaskEntity selectViewAndMask = getSelectViewAndMask(false);
            dismissAIDialog();
            if (this.mCanvasElementTransactCallback == null || (selectRectFWithoutOffset = this.mPageInkViewModel.getSelectRectFWithoutOffset(this.rectF_ai, this.aiViewIndex)) == null || selectRectFWithoutOffset.isEmpty()) {
                return;
            }
            if (selectViewAndMask != null) {
                this.mCanvasElementTransactCallback.onTransactCanvasElementFromText(selectRectFWithoutOffset, selectViewAndMask, str);
                return;
            } else {
                Log.i(TAG, "onCallBack: canvasMaskEntity is null");
                return;
            }
        }
        verifyFailOption(result);
    }

    private boolean checkNetworkForAi() {
        if (!PreferenceUtils.isNetworkPermissionAccepted()) {
            MainThreadUtils.runOnUiThread(new Runnable() { // from class: com.miui.handwriting.common.ai.AiCreationHelper$$ExternalSyntheticLambda12
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.m696x2fb8e868();
                }
            });
            return false;
        }
        if (Utils.isNetworkAvailable(HandWritingViewApplication.INSTANCE.getApplication())) {
            return true;
        }
        this.mShieldPageInkView.postDelayed(new Runnable() { // from class: com.miui.handwriting.common.ai.AiCreationHelper.3
            @Override // java.lang.Runnable
            public void run() {
                AiCreationHelper.this.mPageInkViewModel.finishSelect();
            }
        }, 500L);
        Toast.makeText(HandWritingViewApplication.INSTANCE.getApplication(), HandWritingViewApplication.INSTANCE.getApplication().getString(R.string.creation_ai_network_error_message), 0).show();
        return false;
    }

    /* JADX INFO: renamed from: lambda$checkNetworkForAi$9$com-miui-handwriting-common-ai-AiCreationHelper, reason: not valid java name */
    /* synthetic */ void m696x2fb8e868() {
        this.mNetworkCtaDialog = null;
        AlertDialog.Builder builder = new AlertDialog.Builder(this.mContext);
        builder.setTitle(R.string.creation_request_network_title);
        builder.setMessage(R.string.creation_request_network_desc_for_ai);
        builder.setPositiveButton(R.string.creation_cta_accept, new DialogInterface.OnClickListener() { // from class: com.miui.handwriting.common.ai.AiCreationHelper$$ExternalSyntheticLambda14
            @Override // android.content.DialogInterface.OnClickListener
            public final void onClick(DialogInterface dialogInterface, int i) {
                this.f$0.m695x30a5b466(dialogInterface, i);
            }
        });
        builder.setNegativeButton(R.string.creation_cta_not_accept, new DialogInterface.OnClickListener() { // from class: com.miui.handwriting.common.ai.AiCreationHelper$$ExternalSyntheticLambda15
            @Override // android.content.DialogInterface.OnClickListener
            public final void onClick(DialogInterface dialogInterface, int i) {
                AiCreationHelper.lambda$checkNetworkForAi$8(dialogInterface, i);
            }
        });
        AlertDialog alertDialogCreate = builder.create();
        this.mNetworkCtaDialog = alertDialogCreate;
        alertDialogCreate.show();
    }

    /* JADX INFO: renamed from: lambda$checkNetworkForAi$7$com-miui-handwriting-common-ai-AiCreationHelper, reason: not valid java name */
    /* synthetic */ void m695x30a5b466(DialogInterface dialogInterface, int i) {
        PreferenceUtils.setNetworkPermissionAccepted(true);
        NetworkPermissionCallback networkPermissionCallback = this.mNetworkPermissionCallback;
        if (networkPermissionCallback != null) {
            networkPermissionCallback.onNetworkAccepted();
        }
    }

    private boolean checkAccountAndVerify(boolean z) {
        Account xiaomiAccount = ExtraAccountManager.getXiaomiAccount(HandWritingViewApplication.INSTANCE.getApplication());
        if (xiaomiAccount == null) {
            AccountManager.get(HandWritingViewApplication.INSTANCE.getApplication()).addAccount("com.xiaomi", CreationAIRequest.INSTANCE.getAI_SID(), null, null, Utils.getActivity(this.mContext), null, null);
            IMiuiHandWritingModel iMiuiHandWritingModel = this.mPageInkViewModel;
            if (iMiuiHandWritingModel != null) {
                iMiuiHandWritingModel.finishSelect();
            }
            return false;
        }
        if (this.mVerifyServerCallback == null) {
            return false;
        }
        if (z) {
            showAIDialog();
        }
        VerifyServer verifyServer = new VerifyServer();
        IMiuiHandWritingModel iMiuiHandWritingModel2 = this.mPageInkViewModel;
        if (iMiuiHandWritingModel2 != null) {
            iMiuiHandWritingModel2.selectMoveEnable(false);
        }
        verifyServer.getServerVersion(xiaomiAccount.name, this.mVerifyServerCallback);
        return true;
    }

    private void verifyFailOption(Result<VersionData> result) {
        if (result != null && result.getCode() == 50041) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this.mContext);
            builder.setTitle(R.string.creation_ai_disable_title);
            builder.setMessage(R.string.creation_ai_disable_message);
            builder.setPositiveButton(R.string.creation_ai_ok, (DialogInterface.OnClickListener) null);
            AlertDialog alertDialogCreate = builder.create();
            this.aiErrorDialog = alertDialogCreate;
            alertDialogCreate.show();
            dismissAIDialog();
            return;
        }
        if (result != null && result.getCode() == Integer.MAX_VALUE) {
            AlertDialog.Builder builder2 = new AlertDialog.Builder(this.mContext);
            builder2.setMessage(R.string.creation_ai_disable_limit_message);
            builder2.setPositiveButton(R.string.creation_ai_ok, (DialogInterface.OnClickListener) null);
            AlertDialog alertDialogCreate2 = builder2.create();
            this.aiErrorDialog = alertDialogCreate2;
            alertDialogCreate2.show();
            TextView textView = (TextView) this.aiErrorDialog.findViewById(miuix.appcompat.R.id.message);
            if (textView != null) {
                textView.setTextAlignment(4);
            }
            dismissAIDialog();
            return;
        }
        if (result != null && result.getCode() == -2) {
            AsyncTask.execute(new Runnable() { // from class: com.miui.handwriting.common.ai.AiCreationHelper$$ExternalSyntheticLambda13
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.m710xe67f3f60();
                }
            });
        } else {
            this.mShieldPageInkView.post(new Runnable() { // from class: com.miui.handwriting.common.ai.AiCreationHelper.4
                @Override // java.lang.Runnable
                public void run() {
                    AiCreationHelper.this.mPageInkViewModel.selectMoveEnable(true);
                    Toast.makeText(HandWritingViewApplication.INSTANCE.getApplication(), HandWritingViewApplication.INSTANCE.getApplication().getString(R.string.net_error), 0).show();
                }
            });
            dismissAIDialog();
        }
    }

    /* JADX INFO: renamed from: lambda$verifyFailOption$11$com-miui-handwriting-common-ai-AiCreationHelper, reason: not valid java name */
    /* synthetic */ void m710xe67f3f60() {
        final Bundle authToken = CreationAIRequest2.INSTANCE.getAuthToken();
        this.mShieldPageInkView.post(new Runnable() { // from class: com.miui.handwriting.common.ai.AiCreationHelper$$ExternalSyntheticLambda4
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.m709xe6f5a55f(authToken);
            }
        });
    }

    /* JADX INFO: renamed from: lambda$verifyFailOption$10$com-miui-handwriting-common-ai-AiCreationHelper, reason: not valid java name */
    /* synthetic */ void m709xe6f5a55f(Bundle bundle) {
        Utils.getActivity(this.mContext).startActivity((Intent) bundle.getParcelable("intent"));
        dismissAIDialog();
    }

    private void classificationByCurves(List<StrokeClassificationCurve> list) {
        Log.e(TAG, "start classificationByCurves: ");
        if (list != null && !list.isEmpty()) {
            String json = new Gson().toJson(list);
            String str = this.mContext.getExternalFilesDir("") + File.separator + "classification.json";
            FileUtil.saveJsonToFile(str, json);
            Intent intent = new Intent();
            intent.setAction(Constants.OPERATION_INTENT);
            intent.putExtra("operationType", "classificationForAi");
            intent.putExtra("filePath", str);
            LocalBroadcastManager.getInstance(HandWritingViewApplication.INSTANCE.getApplication()).sendBroadcast(intent);
            return;
        }
        IMiuiHandWritingModel iMiuiHandWritingModel = this.mPageInkViewModel;
        if (iMiuiHandWritingModel != null) {
            iMiuiHandWritingModel.selectMoveEnable(true);
        }
        Log.e(TAG, "classificationByCurves: strokeClassificationCurveLs is empty");
    }

    public void startAIFunctionForOS2(final RectF rectF, final List<TouchPosition> list, final int i, List<DataCurve> list2, final List<Point> list3) {
        if ((list == null || list.isEmpty()) && (rectF == null || rectF.isEmpty())) {
            Log.i(TAG, "no select point");
            return;
        }
        if (this.isSavingBitmap.get()) {
            Log.e(TAG, "isSavingBitmap, cannot creation again ,ignore ");
            return;
        }
        if (this.isVerifyAccount.get()) {
            Log.e(TAG, "isVerifyAccount, cannot creation again ,ignore ");
            return;
        }
        this.rectF_ai = rectF;
        if (!rectF.isEmpty()) {
            this.mLastRectF_ai = new RectF(this.rectF_ai);
        }
        this.aiViewIndex = i;
        if (!list.isEmpty()) {
            this.mAiSelectData = list;
        }
        final List<CurveBean> allSelectedDataList = this.mPageInkViewModel.getAllSelectedDataList();
        final boolean zIsFromAi = this.mPageInkViewModel.isFromAi();
        final boolean zIsContainerCurves = isContainerCurves(allSelectedDataList);
        boolean zIsContainShape = isContainShape(allSelectedDataList);
        final int aiSelectSpanContainType = getAiSelectSpanContainType();
        Log.e(TAG, "is contain ai:" + zIsFromAi + ", viewIndex:" + i + ", isContainerCurves:" + zIsContainerCurves + ", isContainShape:" + zIsContainShape + ", aiSelectSpanType:" + aiSelectSpanContainType);
        if (zIsFromAi || zIsContainShape || !(aiSelectSpanContainType == 0 || aiSelectSpanContainType == 1)) {
            aiFunctionFormDraw(rectF, list, i, list3, allSelectedDataList, zIsFromAi, zIsContainerCurves, aiSelectSpanContainType);
            return;
        }
        if (!zIsContainerCurves && aiSelectSpanContainType == 1) {
            BitmapHolder.INSTANCE.setIsFromText(true);
            aiFunctionFormText(list, null);
            return;
        }
        List<StrokeClassificationCurve> selectedStrokeClassificationCurves = this.mPageInkViewModel.getSelectedStrokeClassificationCurves();
        if (selectedStrokeClassificationCurves != null && !selectedStrokeClassificationCurves.isEmpty()) {
            this.mClassificationCallBack = new ClassificationCallBack() { // from class: com.miui.handwriting.common.ai.AiCreationHelper$$ExternalSyntheticLambda9
                @Override // com.miui.handwriting.common.ai.ClassificationCallBack
                public final void startAiFormClassification(boolean z, String str) {
                    this.f$0.m701xf1411ea1(list, rectF, i, list3, allSelectedDataList, zIsFromAi, zIsContainerCurves, aiSelectSpanContainType, z, str);
                }
            };
            this.mPageInkViewModel.selectMoveEnable(false);
            classificationByCurves(selectedStrokeClassificationCurves);
            return;
        }
        aiFunctionFormDraw(rectF, list, i, list3, allSelectedDataList, zIsFromAi, zIsContainerCurves, aiSelectSpanContainType);
    }

    /* JADX INFO: renamed from: lambda$startAIFunctionForOS2$13$com-miui-handwriting-common-ai-AiCreationHelper, reason: not valid java name */
    /* synthetic */ void m701xf1411ea1(final List list, final RectF rectF, final int i, final List list2, final List list3, final boolean z, final boolean z2, final int i2, final boolean z3, final String str) {
        MainThreadUtils.runOnUiThread(new Runnable() { // from class: com.miui.handwriting.common.ai.AiCreationHelper$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.m700xf1b784a0(z3, list, str, rectF, i, list2, list3, z, z2, i2);
            }
        });
    }

    /* JADX INFO: renamed from: lambda$startAIFunctionForOS2$12$com-miui-handwriting-common-ai-AiCreationHelper, reason: not valid java name */
    /* synthetic */ void m700xf1b784a0(boolean z, List list, String str, RectF rectF, int i, List list2, List list3, boolean z2, boolean z3, int i2) {
        if (!z) {
            aiFunctionFormDraw(rectF, list, i, list2, list3, z2, z3, i2);
        } else {
            BitmapHolder.INSTANCE.setIsFromText(true);
            aiFunctionFormText(list, str);
        }
    }

    public void startAiFormClassification(boolean z, String str) {
        IMiuiHandWritingModel iMiuiHandWritingModel = this.mPageInkViewModel;
        if (iMiuiHandWritingModel != null) {
            iMiuiHandWritingModel.selectMoveEnable(true);
        }
        ClassificationCallBack classificationCallBack = this.mClassificationCallBack;
        if (classificationCallBack != null) {
            classificationCallBack.startAiFormClassification(z, str);
        }
    }

    private void aiFunctionFormDraw(RectF rectF, List<TouchPosition> list, int i, List<Point> list2, List<CurveBean> list3, boolean z, boolean z2, int i2) {
        RectF rectF2;
        float fMin;
        float fMin2;
        float fMax;
        float fMax2;
        Log.e(TAG, "start aiFunctionFormDraw");
        if (list == null || list.isEmpty()) {
            if ((z && !z2) || z2 || (rectF2 = this.rectF_ai) == null || rectF2.isEmpty()) {
                return;
            }
            verifyAccount(false, true, true, false);
            return;
        }
        if (z && !z2) {
            showByAICreate(this.rectF_ai, i);
            return;
        }
        if (list3 == null) {
            Log.i(TAG, "no select curves");
            if (isOnlyContainText()) {
                Log.e(TAG, "isOnlyContainText");
                this.mPageInkViewModel.selectMoveEnable(true);
                this.mPageInkViewModel.finishSelect();
                Toast.makeText(this.mContext, R.string.ai_generate_select_fail_new, 0).show();
                return;
            }
            List<SpannedData> allSelectImageSpanned = this.mPageInkViewModel.getAllSelectImageSpanned();
            if (allSelectImageSpanned != null) {
                for (SpannedData spannedData : allSelectImageSpanned) {
                    if (spannedData.mBitmapPath != null && !spannedData.mBitmapPath.contains("Ai_Tag")) {
                        verifyAccount(false, true, true, false);
                        return;
                    }
                }
            }
            RectF touchOnAiBitmap = getTouchOnAiBitmap(this.mPageInkViewModel.getAllFromAISpanned(), list);
            RectF rectF3 = new RectF();
            if (list2 == null || list2.size() <= 0) {
                fMin = 0.0f;
                fMin2 = 0.0f;
                fMax = 0.0f;
                fMax2 = 0.0f;
            } else {
                fMin = list2.get(0).x;
                fMin2 = list2.get(0).y;
                fMax = list2.get(0).x;
                fMax2 = list2.get(0).y;
                for (Point point : list2) {
                    float f = point.x;
                    float f2 = point.y;
                    fMin = Math.min(fMin, f);
                    fMin2 = Math.min(fMin2, f2);
                    fMax = Math.max(fMax, f);
                    fMax2 = Math.max(fMax2, f2);
                }
            }
            rectF3.set(fMin, fMin2, fMax, fMax2);
            if (touchOnAiBitmap != null && i2 == 0) {
                this.mPageInkViewModel.simulateClick(new TouchPosition(rectF3.centerX(), rectF3.centerY(), System.currentTimeMillis()));
                return;
            }
            if (getTouchOnAiBitmap(this.mPageInkViewModel.getAllFromInsertSpanned(), list) != null && this.rectF_ai.isEmpty()) {
                this.mPageInkViewModel.simulateClick(new TouchPosition(rectF3.centerX(), rectF3.centerY(), System.currentTimeMillis()));
                return;
            }
            RectF newSelectSaveRectF = this.mPageInkViewModel.getNewSelectSaveRectF(this.rectF_ai, this.aiViewIndex);
            if (newSelectSaveRectF == null || newSelectSaveRectF.isEmpty()) {
                Log.e(TAG, "select is null");
                Toast.makeText(this.mContext, R.string.ai_generate_select_fail_new, 0).show();
                return;
            } else if (this.mPageInkViewModel.getSelectScreenshot(true) == null) {
                Log.e(TAG, "selectBitmap is null");
                Toast.makeText(this.mContext, R.string.ai_generate_select_fail_new, 0).show();
                return;
            } else {
                verifyAccount(false, true, true, false);
                return;
            }
        }
        RectF newSelectSaveRectF2 = this.mPageInkViewModel.getNewSelectSaveRectF(this.rectF_ai, this.aiViewIndex);
        float scale = this.mPageInkViewModel.getScale();
        float fWidth = newSelectSaveRectF2.width() / scale;
        float fHeight = newSelectSaveRectF2.height() / scale;
        Log.i(TAG, "select too large,width:" + fWidth + ",height:" + fHeight);
        if (fWidth > 1280.0f || fHeight > 1280.0f) {
            verifyAccount(false, true, true, false);
            return;
        }
        if (rectF.right - rectF.left > 1280.0f || rectF.bottom - rectF.top > 1280.0f) {
            Log.i(TAG, "creation by select to large");
            verifyAccount(false, true, true, false);
        } else {
            Log.e(TAG, "SmartSelect module removed, creation by ai is not available");
            verifyAccount(false, true, true, false);
        }
    }

    public void aiFunctionFormText(List<TouchPosition> list, String str) {
        if (list == null || list.isEmpty()) {
            return;
        }
        StringBuilder sb = new StringBuilder();
        if (str != null && !str.isEmpty()) {
            sb = new StringBuilder(str);
        }
        if (getAiSelectSpanContainType() == 1) {
            for (String str2 : getAiSelectSpanTextList()) {
                if (sb.length() == 0) {
                    sb.append(str2);
                } else {
                    sb.append(StringUtils.SPACE).append(str2);
                }
            }
        }
        String string = sb.toString();
        boolean zIsAllSpaces = Utils.isAllSpaces(string);
        Log.e(TAG, "aiFunctionFormText: promptStr -> " + string);
        if (!zIsAllSpaces && !string.isEmpty()) {
            if (string.length() > 200) {
                this.mPageInkViewModel.selectMoveEnable(true);
                this.mPageInkViewModel.finishSelect();
                Toast.makeText(this.mContext, R.string.ai_text_creation_limit, 0).show();
                return;
            }
            m707x4f15cb95(string);
            return;
        }
        this.mPageInkViewModel.selectMoveEnable(true);
        this.mPageInkViewModel.finishSelect();
        Toast.makeText(this.mContext, R.string.ai_generate_select_fail_new, 0).show();
    }

    private void showByAICreate(final RectF rectF, final int i) {
        PopupWindow popupWindow = this.mAICreationPop;
        if (popupWindow == null || !popupWindow.isShowing()) {
            AICreationPopupWindow aICreationPopupWindow = new AICreationPopupWindow(this.mContext);
            this.mAICreationPop = aICreationPopupWindow;
            aICreationPopupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() { // from class: com.miui.handwriting.common.ai.AiCreationHelper$$ExternalSyntheticLambda8
                @Override // android.widget.PopupWindow.OnDismissListener
                public final void onDismiss() {
                    this.f$0.m699x21144db7();
                }
            });
            ((AICreationPopupWindow) this.mAICreationPop).setOnAICreationCallback(new AICreationPopupWindow.OnAICreationClickListener() { // from class: com.miui.handwriting.common.ai.AiCreationHelper.5
                @Override // com.miui.handwriting.common.view.handview.pop.AICreationPopupWindow.OnAICreationClickListener
                public void onAICreationClick() {
                    AiCreationHelper.this.verifyAccount(false, false, false, true);
                }

                @Override // com.miui.handwriting.common.view.handview.pop.AICreationPopupWindow.OnAICreationClickListener
                public void onJubuCreationClick() {
                    AiCreationHelper.this.verifyAccount(true, false, false, false);
                }
            });
            this.mShieldPageInkView.post(new Runnable() { // from class: com.miui.handwriting.common.ai.AiCreationHelper.6
                @Override // java.lang.Runnable
                public void run() {
                    ((AICreationPopupWindow) AiCreationHelper.this.mAICreationPop).show(AiCreationHelper.this.mShieldPageInkView, AiCreationHelper.this.mPageInkViewModel.getNewSelectSaveRectF(rectF, i), AiCreationHelper.this.mPageInkViewModel.getItemRectF());
                    Utils.getActivity(AiCreationHelper.this.mContext).getWindow().setFlags(1024, 1024);
                }
            });
        }
    }

    /* JADX INFO: renamed from: lambda$showByAICreate$14$com-miui-handwriting-common-ai-AiCreationHelper, reason: not valid java name */
    /* synthetic */ void m699x21144db7() {
        this.mPageInkViewModel.selectMoveEnable(true);
    }

    public void startAIFunctionForOS1(final RectF rectF, List<TouchPosition> list, final int i, final int i2) {
        if (rectF == null || rectF.isEmpty()) {
            return;
        }
        if ((list == null || list.isEmpty()) && rectF.isEmpty()) {
            Log.i(TAG, "no select point");
            return;
        }
        this.rectF_ai = rectF;
        if (rectF != null && !rectF.isEmpty()) {
            this.mLastRectF_ai = new RectF(this.rectF_ai);
        }
        this.aiViewIndex = i;
        if (list != null && !list.isEmpty()) {
            this.mAiSelectData = list;
        }
        PopupWindow popupWindow = this.mAICreationPop;
        if (popupWindow == null || !popupWindow.isShowing()) {
            AICreationPopupWindowForOS1 aICreationPopupWindowForOS1 = new AICreationPopupWindowForOS1(this.mContext);
            this.mAICreationPop = aICreationPopupWindowForOS1;
            aICreationPopupWindowForOS1.setOnAICreationCallback(new AICreationPopupWindowForOS1.OnAICreationClickListener() { // from class: com.miui.handwriting.common.ai.AiCreationHelper.7
                @Override // com.miui.handwriting.common.view.handview.pop.AICreationPopupWindowForOS1.OnAICreationClickListener
                public void onAICreationClick() {
                    AiCreationHelper.this.m705xf59ec7ec(true, i2);
                }
            });
            this.mShieldPageInkView.post(new Runnable() { // from class: com.miui.handwriting.common.ai.AiCreationHelper.8
                @Override // java.lang.Runnable
                public void run() {
                    ((AICreationPopupWindowForOS1) AiCreationHelper.this.mAICreationPop).show(AiCreationHelper.this.mShieldPageInkView, AiCreationHelper.this.mPageInkViewModel.getNewSelectSaveRectF(rectF, i), AiCreationHelper.this.mPageInkViewModel.getItemRectF());
                    Utils.getActivity(AiCreationHelper.this.mContext).getWindow().setFlags(1024, 1024);
                }
            });
        }
    }

    /* JADX INFO: renamed from: com.miui.handwriting.common.ai.AiCreationHelper$9, reason: invalid class name */
    class AnonymousClass9 implements DrawErrorCallback {
        @Override // xc.engine.api.listener.DrawErrorCallback
        public void onSuccess() {
        }

        AnonymousClass9() {
        }

        @Override // xc.engine.api.listener.DrawErrorCallback
        public void onError(int i) {
            if (i == 2) {
                MainThreadUtils.runOnUiThread(new Runnable() { // from class: com.miui.handwriting.common.ai.AiCreationHelper$9$$ExternalSyntheticLambda0
                    @Override // java.lang.Runnable
                    public final void run() {
                        Toast.makeText(HandWritingViewApplication.INSTANCE.getApplication(), R.string.creation_edit_limit_img, 0).show();
                    }
                });
            }
        }
    }

    public void insertImageFormDraw(boolean z) {
        this.mDrawErrorCallback = new AnonymousClass9();
        if (z) {
            RectF rectF = this.rectF_ai;
            if (rectF == null || rectF.isEmpty()) {
                this.rectF_ai = this.mLastRectF_ai;
            }
            if (this.mIsPartiSelect || this.mIsRecreate) {
                this.mAiReplaceRectF = this.mPageInkViewModel.getNewSelectSaveRectF(this.rectF_ai, this.aiViewIndex);
            } else if (this.mPageInkViewModel.isUnlimited()) {
                this.mAiReplaceRectF = BitmapHolder.INSTANCE.getTransactRect();
                fitAiPicRadio();
            } else {
                RectF rectF2 = new RectF(BitmapHolder.INSTANCE.getBitmapReactF());
                if (rectF2.isEmpty()) {
                    this.mAiReplaceRectF = this.mPageInkViewModel.getNewSelectSaveRectF(this.rectF_ai, this.aiViewIndex);
                    fitAiPicRadio();
                } else {
                    RectF newSelectSaveRectF = this.mPageInkViewModel.getNewSelectSaveRectF(this.rectF_ai, this.aiViewIndex);
                    float scale = this.mPageInkViewModel.getScale();
                    if (rectF2.isEmpty()) {
                        this.mAiReplaceRectF = newSelectSaveRectF;
                        fitAiPicRadio();
                    } else {
                        float canvasDrawWidth = ((this.mPageInkViewModel.isUnlimited() || this.mPageInkViewModel.isUnLimitToNormal()) ? 1.0f : this.mPageInkViewModel.getCurrentItemView().getCreationEngine().getCanvasOperator().getCanvasDrawWidth() / UIUtils.getRealWidth(this.mContext)) * 1280.0f * scale;
                        float fWidth = (canvasDrawWidth - newSelectSaveRectF.width()) / 2.0f;
                        float fHeight = (canvasDrawWidth - newSelectSaveRectF.height()) / 2.0f;
                        RectF rectF3 = new RectF(newSelectSaveRectF.left - fWidth, newSelectSaveRectF.top - fHeight, newSelectSaveRectF.right + fWidth, newSelectSaveRectF.bottom + fHeight);
                        RectF rectF4 = new RectF(rectF2.left * scale, rectF2.top * scale, rectF2.right * scale, rectF2.bottom * scale);
                        this.mAiReplaceRectF = new RectF(rectF3.left + rectF4.left, rectF3.top + rectF4.top, rectF3.right - ((rectF3.width() - rectF4.left) - rectF4.width()), rectF3.bottom - ((rectF3.height() - rectF4.top) - rectF4.height()));
                        fitAiPicRadio();
                        Log.d(TAG, "insertImageFormDraw: mAiReplaceRectF " + this.mAiReplaceRectF.width() + StringUtils.SPACE + this.mAiReplaceRectF.height());
                        Log.d(TAG, "insertImageFormDraw: bitmapReactF " + rectF4.width() + StringUtils.SPACE + rectF4.height());
                        Log.d(TAG, "insertImageFormDraw: allRectArea " + rectF3.width() + StringUtils.SPACE + rectF3.height());
                        Log.d(TAG, "insertImageFormDraw: dst " + (this.mAiReplaceRectF.left - rectF3.left) + ", " + (this.mAiReplaceRectF.top - rectF3.top) + ", mAiReplaceRectF " + this.mAiReplaceRectF + ", bitmapReactF " + BitmapHolder.INSTANCE.getBitmapReactF() + ", scale " + this.mPageInkViewModel.getScale());
                    }
                }
            }
            this.mShieldPageInkView.post(new Runnable() { // from class: com.miui.handwriting.common.ai.AiCreationHelper$$ExternalSyntheticLambda16
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.m698x241e72a0();
                }
            });
            SpannedData clickImage = this.mPageInkViewModel.getClickImage();
            Log.d(TAG, "setImageUrlRequest,spannedData:" + clickImage);
            if (this.mCreationImage == null || clickImage == null || clickImage.mBitmapPath == null) {
                return;
            }
            String strGenerateKey = RequestCache.INSTANCE.generateKey(clickImage.mBitmapPath);
            Log.d(TAG, "setImageUrlRequest,imageKey:" + strGenerateKey + ",mCreationImage:" + this.mCreationImage);
            RequestCache.INSTANCE.setImageUrlRequestCache(this.mContext, strGenerateKey, this.mCreationImage);
            return;
        }
        RectF newSelectSaveRectF2 = this.mPageInkViewModel.getNewSelectSaveRectF(this.rectF_ai, this.aiViewIndex);
        this.mAiReplaceRectF = newSelectSaveRectF2;
        IMiuiHandWritingModel iMiuiHandWritingModel = this.mPageInkViewModel;
        iMiuiHandWritingModel.addSelectRectBitmap(this.mAiInsertImagePath, newSelectSaveRectF2, iMiuiHandWritingModel.getCurrentPosition(), true, this.mDrawErrorCallback);
    }

    /* JADX INFO: renamed from: lambda$insertImageFormDraw$15$com-miui-handwriting-common-ai-AiCreationHelper, reason: not valid java name */
    /* synthetic */ void m698x241e72a0() {
        IMiuiHandWritingModel iMiuiHandWritingModel = this.mPageInkViewModel;
        iMiuiHandWritingModel.addSelectRectBitmap(this.mAiInsertImagePath, this.mAiReplaceRectF, iMiuiHandWritingModel.getCurrentPosition(), true, this.mDrawErrorCallback);
        BitmapHolder.INSTANCE.resetReactF();
    }

    public void insertImageFromText() {
        int i;
        RectF rectF;
        IMiuiHandWritingModel iMiuiHandWritingModel = this.mPageInkViewModel;
        if (iMiuiHandWritingModel != null) {
            int currentPosition = iMiuiHandWritingModel.getCurrentPosition();
            if (this.mPageInkViewModel.isUnlimited()) {
                this.mAiReplaceRectF = BitmapHolder.INSTANCE.getTransactRect();
            } else {
                this.mAiReplaceRectF = BitmapHolder.INSTANCE.getInsertRectFForLimit();
                int newPageIndex = BitmapHolder.INSTANCE.getNewPageIndex();
                if (BitmapHolder.INSTANCE.getIsNeedNewPage() && newPageIndex >= 0 && newPageIndex < this.mPageInkViewModel.getPageCount()) {
                    i = newPageIndex;
                }
                rectF = this.mAiReplaceRectF;
                if (rectF != null && !rectF.isEmpty()) {
                    AnonymousClass10 anonymousClass10 = new AnonymousClass10();
                    this.mDrawErrorCallback = anonymousClass10;
                    this.mPageInkViewModel.addSelectRectBitmap(this.mAiInsertImagePath, this.mAiReplaceRectF, i, false, anonymousClass10);
                }
            }
            i = currentPosition;
            rectF = this.mAiReplaceRectF;
            if (rectF != null) {
                AnonymousClass10 anonymousClass11 = new AnonymousClass10();
                this.mDrawErrorCallback = anonymousClass11;
                this.mPageInkViewModel.addSelectRectBitmap(this.mAiInsertImagePath, this.mAiReplaceRectF, i, false, anonymousClass11);
            }
        }
        BitmapHolder.INSTANCE.setIsOverLimitPageNum(false);
        BitmapHolder.INSTANCE.setIsNeedNewPage(false);
        BitmapHolder.INSTANCE.setNewPageIndex(-1);
        BitmapHolder.INSTANCE.resetReactF();
        SpannedData clickImage = this.mPageInkViewModel.getClickImage();
        if (this.mCreationImage != null && clickImage != null && clickImage.mBitmapPath != null) {
            String strGenerateKey = RequestCache.INSTANCE.generateKey(clickImage.mBitmapPath);
            Log.d(TAG, "setImageUrlRequest,imageKey:" + strGenerateKey + ",mCreationImage:" + this.mCreationImage);
            RequestCache.INSTANCE.setImageUrlRequestCache(this.mContext, strGenerateKey, this.mCreationImage);
        }
        IMiuiHandWritingModel iMiuiHandWritingModel2 = this.mPageInkViewModel;
        if (iMiuiHandWritingModel2 != null) {
            iMiuiHandWritingModel2.finishSelect();
        }
        CanvasElementTransactionMask canvasElementTransactionMask = this.mTransactionMask;
        if (canvasElementTransactionMask != null) {
            canvasElementTransactionMask.release();
            this.mTransactionMask.setVisibility(8);
        }
    }

    /* JADX INFO: renamed from: com.miui.handwriting.common.ai.AiCreationHelper$10, reason: invalid class name */
    class AnonymousClass10 implements DrawErrorCallback {
        @Override // xc.engine.api.listener.DrawErrorCallback
        public void onSuccess() {
        }

        AnonymousClass10() {
        }

        @Override // xc.engine.api.listener.DrawErrorCallback
        public void onError(int i) {
            if (i == 2) {
                MainThreadUtils.runOnUiThread(new Runnable() { // from class: com.miui.handwriting.common.ai.AiCreationHelper$10$$ExternalSyntheticLambda0
                    @Override // java.lang.Runnable
                    public final void run() {
                        Toast.makeText(HandWritingViewApplication.INSTANCE.getApplication(), R.string.creation_edit_limit_img, 0).show();
                    }
                });
            }
        }
    }

    public void dismissTransactionMask() {
        CanvasElementTransactionMask canvasElementTransactionMask = this.mTransactionMask;
        if (canvasElementTransactionMask != null) {
            canvasElementTransactionMask.release();
            this.mTransactionMask.setVisibility(8);
        }
    }

    private RectF findAiInsertAreaFromText(RectF rectF) {
        PageItemView currentItemView;
        if (this.mPageInkViewModel == null || this.mContext == null || rectF == null || rectF.isEmpty() || (currentItemView = this.mPageInkViewModel.getCurrentItemView()) == null) {
            return rectF;
        }
        float dimension = this.mContext.getResources().getDimension(R.dimen.ai_from_text_margin) * this.mPageInkViewModel.getScale();
        Log.i(TAG, "findAiInsertAreaFromText: page type is unlimited -> " + this.mPageInkViewModel.isUnlimited());
        if (this.mPageInkViewModel.isUnlimited()) {
            float imageSizeFromText = AiCreationUtil.getImageSizeFromText(rectF, currentItemView.model.getVisibleRectF(), this.mPageInkViewModel.getScale());
            float imageSizeFromText2 = AiCreationUtil.getImageSizeFromText(rectF, currentItemView.model.getVisibleRectF(), this.mPageInkViewModel.getScale());
            BitmapHolder.INSTANCE.setImageWidth(imageSizeFromText);
            BitmapHolder.INSTANCE.setImageHeight(imageSizeFromText2);
            RectF rectFFindEmptyAreaForUnLimit = AiCreationUtil.findEmptyAreaForUnLimit(currentItemView, new RectF(rectF), imageSizeFromText, imageSizeFromText2, dimension);
            if (!rectFFindEmptyAreaForUnLimit.isEmpty()) {
                Log.i(TAG, "findAiInsertAreaFromText: find empty area");
                return rectFFindEmptyAreaForUnLimit;
            }
            Log.i(TAG, "findAiInsertAreaFromText: insert right area");
            return AiCreationUtil.getInsertImgAreaFromSelect(rectF, dimension, imageSizeFromText, imageSizeFromText2, 1);
        }
        RectF rectF2 = new RectF(currentItemView.getLayout());
        float imageSizeFromText3 = AiCreationUtil.getImageSizeFromText(rectF, rectF2, 1.0f);
        float imageSizeFromText4 = AiCreationUtil.getImageSizeFromText(rectF, rectF2, 1.0f);
        BitmapHolder.INSTANCE.setImageWidth(imageSizeFromText3);
        BitmapHolder.INSTANCE.setImageHeight(imageSizeFromText4);
        RectF newSelectSaveRectF = this.mPageInkViewModel.getNewSelectSaveRectF(this.rectF_ai, this.aiViewIndex);
        int iFindEmptyAreaForLimit = AiCreationUtil.findEmptyAreaForLimit(currentItemView, new RectF(rectF), imageSizeFromText3, imageSizeFromText4, dimension);
        Log.i(TAG, "findAiInsertAreaFromText: emptyAreaType -> " + iFindEmptyAreaForLimit);
        if (iFindEmptyAreaForLimit != -1) {
            RectF insertImgAreaFromSelect = AiCreationUtil.getInsertImgAreaFromSelect(rectF, dimension, imageSizeFromText3, imageSizeFromText4, iFindEmptyAreaForLimit);
            BitmapHolder.INSTANCE.setInsertRectFForLimit(AiCreationUtil.getInsertImgAreaFromSelect(newSelectSaveRectF, dimension, imageSizeFromText3, imageSizeFromText4, iFindEmptyAreaForLimit));
            return insertImgAreaFromSelect;
        }
        int insertTypeForLimitPageWithoutEmpty = AiCreationUtil.getInsertTypeForLimitPageWithoutEmpty(currentItemView, rectF, imageSizeFromText3, imageSizeFromText4, dimension);
        Log.i(TAG, "findAiInsertAreaFromText: insert type is " + insertTypeForLimitPageWithoutEmpty);
        if (insertTypeForLimitPageWithoutEmpty != 5) {
            RectF insertImgAreaFromSelect2 = AiCreationUtil.getInsertImgAreaFromSelect(rectF, dimension, imageSizeFromText3, imageSizeFromText4, insertTypeForLimitPageWithoutEmpty);
            BitmapHolder.INSTANCE.setInsertRectFForLimit(AiCreationUtil.getInsertImgAreaFromSelect(newSelectSaveRectF, dimension, imageSizeFromText3, imageSizeFromText4, insertTypeForLimitPageWithoutEmpty));
            return insertImgAreaFromSelect2;
        }
        if (this.mPageInkViewModel.getPageCount() >= 500) {
            Toast.makeText(this.mContext, R.string.creation_edit_limit_new_page, 0).show();
            BitmapHolder.INSTANCE.setIsOverLimitPageNum(true);
            RectF insertAreaForLimitPage = AiCreationUtil.getInsertAreaForLimitPage(rectF, imageSizeFromText3, imageSizeFromText4);
            BitmapHolder.INSTANCE.setInsertRectFForLimit(AiCreationUtil.getInsertAreaForLimitPage(newSelectSaveRectF, imageSizeFromText3, imageSizeFromText4));
            return insertAreaForLimitPage;
        }
        int currentPosition = this.mPageInkViewModel.getCurrentPosition();
        BitmapHolder.INSTANCE.setIsNeedNewPage(true);
        BitmapHolder.INSTANCE.setNewPageIndex(currentPosition + 1);
        BitmapHolder.INSTANCE.setNewPageWidth(currentItemView.getWidth());
        BitmapHolder.INSTANCE.setNewPageHeight(currentItemView.getHeight());
        float dimensionPixelSize = currentItemView.getLayout().bottom + (this.mContext.getResources().getDimensionPixelSize(R.dimen.creation_page_spacing) * this.mPageInkViewModel.getScale());
        RectF insetAreaForNewPage = AiCreationUtil.getInsetAreaForNewPage(rectF, dimensionPixelSize, imageSizeFromText3, imageSizeFromText4, dimension);
        BitmapHolder.INSTANCE.setInsertRectFForLimit(AiCreationUtil.getInsetAreaForNewPage(newSelectSaveRectF, dimensionPixelSize, imageSizeFromText3, imageSizeFromText4, dimension));
        return insetAreaForNewPage;
    }

    public void calculateAiImageRectForText() {
        RectF rectF;
        RectF selectRectFWithoutOffset;
        if (this.mPageInkViewModel == null || (rectF = this.rectF_ai) == null || rectF.isEmpty() || (selectRectFWithoutOffset = this.mPageInkViewModel.getSelectRectFWithoutOffset(this.rectF_ai, this.aiViewIndex)) == null || selectRectFWithoutOffset.isEmpty()) {
            return;
        }
        BitmapHolder.INSTANCE.setTransactRect(findAiInsertAreaFromText(selectRectFWithoutOffset));
    }

    private int getAiSelectSpanContainType() {
        List<SpannedData> selectedSpanList = this.mPageInkViewModel.getSelectedSpanList();
        if (selectedSpanList != null && !selectedSpanList.isEmpty()) {
            boolean z = false;
            boolean z2 = false;
            for (SpannedData spannedData : selectedSpanList) {
                if (spannedData.getType() == 11) {
                    z = true;
                }
                if (spannedData.getType() == 7) {
                    z2 = true;
                }
                if (z && z2) {
                    return 3;
                }
            }
            if (z) {
                return 1;
            }
            if (z2) {
                return 2;
            }
        }
        return 0;
    }

    private List<String> getAiSelectSpanTextList() {
        List<SpannedData> selectedSpanList = this.mPageInkViewModel.getSelectedSpanList();
        ArrayList arrayList = new ArrayList();
        if (selectedSpanList != null && !selectedSpanList.isEmpty()) {
            for (SpannedData spannedData : selectedSpanList) {
                if (spannedData.getType() == 11) {
                    arrayList.add(spannedData.getText());
                }
            }
        }
        return arrayList;
    }

    private void showAIDialog() {
        ProgressDialog progressDialog = this.aiDialog;
        if (progressDialog == null || !progressDialog.isShowing()) {
            Activity activity = Utils.getActivity(this.mContext);
            if (this.aiDialog == null) {
                ProgressDialog progressDialog2 = new ProgressDialog(activity);
                this.aiDialog = progressDialog2;
                progressDialog2.setMessage(activity.getString(miuix.springback.R.string.miuix_sbl_tracking_progress_labe_up_refresh));
                this.aiDialog.setIndeterminate(true);
                this.aiDialog.setCanceledOnTouchOutside(false);
                this.aiDialog.setCancelable(false);
            }
            this.aiDialog.show();
        }
    }

    public void dismissAIDialog() {
        ProgressDialog progressDialog = this.aiDialog;
        if (progressDialog != null) {
            progressDialog.dismiss();
            this.aiDialog = null;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void dismissAILoadingDialog() {
        CreationShieldPageInkView creationShieldPageInkView = this.mShieldPageInkView;
        if (creationShieldPageInkView != null) {
            creationShieldPageInkView.postDelayed(new Runnable() { // from class: com.miui.handwriting.common.ai.AiCreationHelper.11
                @Override // java.lang.Runnable
                public void run() {
                    AiCreationHelper.this.dismissAIDialog();
                }
            }, 50L);
        }
    }

    public void dismissAIPop() {
        PopupWindow popupWindow = this.mAICreationPop;
        if (popupWindow != null) {
            popupWindow.dismiss();
        }
    }

    public void dismissNetworkCtaDialog() {
        AlertDialog alertDialog = this.mNetworkCtaDialog;
        if (alertDialog == null || !alertDialog.isShowing()) {
            return;
        }
        this.mNetworkCtaDialog.dismiss();
    }

    public void dismissAiErrorDialog() {
        AlertDialog alertDialog = this.aiErrorDialog;
        if (alertDialog == null || !alertDialog.isShowing()) {
            return;
        }
        this.aiErrorDialog.dismiss();
        this.aiErrorDialog = null;
    }

    /* JADX WARN: Code duplicated, block: B:42:0x00c6  */
    /* JADX WARN: Code duplicated, block: B:49:0x00db  */
    /* JADX WARN: Code duplicated, block: B:54:0x00ed  */
    /* JADX WARN: Code duplicated, block: B:60:0x00fe  */
    /* JADX WARN: Code duplicated, block: B:64:0x00b4 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:74:? A[SYNTHETIC] */
    private CanvasMaskEntity getSelectViewAndMask(Boolean bool) throws Throwable {
        final float f;
        final float fMax;
        Throwable th;
        CanvasMaskEntity canvasMaskEntity;
        RectF layout;
        Throwable th2;
        final PageItemView currentItemView = this.mPageInkViewModel.getCurrentItemView();
        CanvasMaskEntity canvasMaskEntity2 = null;
        if (currentItemView == null) {
            return null;
        }
        float f2 = currentItemView.isUnLimit() ? 1.0f : -1.0f;
        if ((!bool.booleanValue() || currentItemView.isUnLimit()) && (bool.booleanValue() || !currentItemView.isUnLimit())) {
            f = 0.0f;
            fMax = 0.0f;
        } else {
            int[] iArr = {0, 0};
            currentItemView.getLocationInWindow(iArr);
            float f3 = iArr[0] * f2;
            fMax = f2 * Math.max(0, iArr[1] - getToolBoxHeight());
            f = f3;
        }
        final int statusBarHeight = UIUtils.isSmallWindowMode(this.mContext) ? UIUtils.getStatusBarHeight(this.mContext) : 0;
        FutureTask futureTask = new FutureTask(new Callable() { // from class: com.miui.handwriting.common.ai.AiCreationHelper$$ExternalSyntheticLambda5
            @Override // java.util.concurrent.Callable
            public final Object call() {
                return this.f$0.m697x4fbec2c5(currentItemView, f, fMax, statusBarHeight);
            }
        });
        if (this.mPageInkViewModel.isUnlimited()) {
            new Thread(futureTask).start();
            canvasMaskEntity = (CanvasMaskEntity) futureTask.get(2000L, TimeUnit.MILLISECONDS);
            if (canvasMaskEntity == null) {
                canvasMaskEntity = new CanvasMaskEntity();
            }
            if (currentItemView.isUnLimitToNormal) {
                layout = currentItemView.getLayout();
                if (layout != null) {
                    canvasMaskEntity.setOffsetX(layout.left);
                }
            } else {
                canvasMaskEntity.setOffsetY(fMax);
            }
            canvasMaskEntity.setToolBarHeight(getToolBoxHeight());
        } else {
            try {
                if (currentItemView.isUnLimitToNormal) {
                    try {
                        try {
                            new Thread(futureTask).start();
                            canvasMaskEntity = (CanvasMaskEntity) futureTask.get(2000L, TimeUnit.MILLISECONDS);
                            if (canvasMaskEntity == null) {
                                canvasMaskEntity = new CanvasMaskEntity();
                            }
                        } catch (Throwable th3) {
                            th = th3;
                            if (canvasMaskEntity2 == null) {
                                new CanvasMaskEntity();
                                throw th;
                            }
                            throw th;
                        }
                    } catch (InterruptedException | ExecutionException | TimeoutException unused) {
                        canvasMaskEntity = new CanvasMaskEntity();
                        try {
                            Log.i(TAG, "getSelectViewAndMask: create mask timeout");
                        } catch (Throwable th4) {
                            canvasMaskEntity2 = canvasMaskEntity;
                            th = th4;
                            if (canvasMaskEntity2 == null) {
                                new CanvasMaskEntity();
                                throw th;
                            }
                            throw th;
                        }
                    }
                    if (currentItemView.isUnLimitToNormal) {
                        layout = currentItemView.getLayout();
                        if (layout != null && layout.left > 0.0f) {
                            canvasMaskEntity.setOffsetX(layout.left);
                        }
                    } else {
                        canvasMaskEntity.setOffsetY(fMax);
                    }
                    canvasMaskEntity.setToolBarHeight(getToolBoxHeight());
                } else {
                    try {
                        new Thread(futureTask).start();
                        canvasMaskEntity = (CanvasMaskEntity) futureTask.get(2000L, TimeUnit.MILLISECONDS);
                        if (canvasMaskEntity == null) {
                            canvasMaskEntity = new CanvasMaskEntity();
                        }
                    } catch (InterruptedException | ExecutionException | TimeoutException unused2) {
                        canvasMaskEntity = new CanvasMaskEntity();
                        try {
                            Log.i(TAG, "getSelectViewAndMask: create mask timeout");
                        } catch (Throwable th5) {
                            canvasMaskEntity2 = canvasMaskEntity;
                            th2 = th5;
                            if (canvasMaskEntity2 == null) {
                                new CanvasMaskEntity();
                                throw th2;
                            }
                            throw th2;
                        }
                    }
                    canvasMaskEntity.setExpandHeight(statusBarHeight);
                    canvasMaskEntity.setToolBarHeight(getToolBoxHeight());
                }
            } catch (Throwable th6) {
                th2 = th6;
            }
        }
        return canvasMaskEntity;
    }

    /* JADX INFO: renamed from: lambda$getSelectViewAndMask$16$com-miui-handwriting-common-ai-AiCreationHelper, reason: not valid java name */
    /* synthetic */ CanvasMaskEntity m697x4fbec2c5(PageItemView pageItemView, float f, float f2, int i) throws Exception {
        if (this.mPageInkViewModel.isUnlimited() || pageItemView.isUnLimitToNormal) {
            return this.mPageInkViewModel.getSelectScreenshotAndMask(false, false, 0.0f, 0.0f, 0);
        }
        return this.mPageInkViewModel.getSelectScreenshotAndMask(false, false, f, f2, i);
    }

    private boolean isOnlyContainText() {
        List<SpannedData> selectedSpanList = this.mPageInkViewModel.getSelectedSpanList();
        if (selectedSpanList == null || selectedSpanList.isEmpty()) {
            return false;
        }
        Iterator<SpannedData> it = selectedSpanList.iterator();
        while (it.hasNext()) {
            if (it.next().getType() != 11) {
                return false;
            }
        }
        return true;
    }

    private RectF getTouchOnAiBitmap(List<SpannedData> list, List<TouchPosition> list2) {
        RectF rectF = null;
        if (list != null && !list.isEmpty() && list2 != null && !list2.isEmpty()) {
            for (SpannedData spannedData : list) {
                for (TouchPosition touchPosition : list2) {
                    if (spannedData.getRectF().contains(touchPosition.x, touchPosition.y)) {
                        rectF = spannedData.getRectF();
                        break;
                    }
                }
            }
        }
        return rectF;
    }

    private boolean isContainerCurves(List<CurveBean> list) {
        return (list == null || list.isEmpty()) ? false : true;
    }

    private boolean isContainShape(List<CurveBean> list) {
        if (list == null || list.isEmpty()) {
            return false;
        }
        Iterator<CurveBean> it = list.iterator();
        while (it.hasNext()) {
            if (it.next().isShapeType) {
                return true;
            }
        }
        return false;
    }

    public int getToolBoxHeight() {
        IMiuiHandToolbar iMiuiHandToolbar = this.mToolBoxView;
        if (iMiuiHandToolbar == null) {
            return 0;
        }
        return iMiuiHandToolbar.getToolBoxHeight();
    }

    private void fitAiPicRadio() {
        Bitmap bitmapDecodeFile = BitmapFactory.decodeFile(this.mAiInsertImagePath);
        if (bitmapDecodeFile.getWidth() / bitmapDecodeFile.getHeight() < this.mAiReplaceRectF.width() / this.mAiReplaceRectF.height()) {
            float fWidth = ((this.mAiReplaceRectF.width() * bitmapDecodeFile.getHeight()) / bitmapDecodeFile.getWidth()) - this.mAiReplaceRectF.height();
            float f = fWidth / 2.0f;
            this.mAiReplaceRectF.top -= f;
            this.mAiReplaceRectF.bottom += f;
            return;
        }
        float width = ((bitmapDecodeFile.getWidth() * this.mAiReplaceRectF.height()) / bitmapDecodeFile.getHeight()) - this.mAiReplaceRectF.width();
        float f2 = width / 2.0f;
        this.mAiReplaceRectF.left -= f2;
        this.mAiReplaceRectF.right += f2;
    }

    public RectF getSelectAiRect() {
        IMiuiHandWritingModel iMiuiHandWritingModel = this.mPageInkViewModel;
        if (iMiuiHandWritingModel != null) {
            return iMiuiHandWritingModel.getNewSelectSaveRectF(this.rectF_ai, this.aiViewIndex);
        }
        return new RectF();
    }

    public void setPreviewDataList(List<PagePreviewData> list) {
        this.mPreviewDataList = list;
    }

    public void setAiInsertImagePath(String str) {
        this.mAiInsertImagePath = str;
    }

    public List<TouchPosition> getAiSelectData() {
        return this.mAiSelectData;
    }

    public void setCreationImage(CreationImage creationImage) {
        this.mCreationImage = creationImage;
    }

    public void setTransactionMask(CanvasElementTransactionMask canvasElementTransactionMask) {
        this.mTransactionMask = canvasElementTransactionMask;
    }

    public void setCanvasElementTransactListener(CanvasElementTransactCallback canvasElementTransactCallback) {
        this.mCanvasElementTransactCallback = canvasElementTransactCallback;
    }

    public void release() {
        AlertDialog alertDialog = this.mNetworkCtaDialog;
        if (alertDialog != null && alertDialog.isShowing()) {
            this.mNetworkCtaDialog.dismiss();
            this.mNetworkCtaDialog = null;
        }
        this.mNetworkPermissionCallback = null;
        this.mVerifyServerCallback = null;
        this.mClassificationCallBack = null;
        this.mDrawErrorCallback = null;
        dismissAIDialog();
        this.mCanvasElementTransactCallback = null;
        this.isSavingBitmap.set(false);
        this.isVerifyAccount.set(false);
        IMiuiHandWritingModel iMiuiHandWritingModel = this.mPageInkViewModel;
        if (iMiuiHandWritingModel != null) {
            iMiuiHandWritingModel.selectMoveEnable(true);
        }
    }
}
