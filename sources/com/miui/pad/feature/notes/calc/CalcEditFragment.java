package com.miui.pad.feature.notes.calc;

import android.app.Application;
import android.content.BroadcastReceiver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.Configuration;
import android.graphics.Path;
import android.net.Uri;
import android.os.Bundle;
import android.os.RemoteException;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.activity.OnBackPressedCallback;
import androidx.activity.OnBackPressedDispatcher;
import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LifecycleOwnerKt;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import ch.qos.logback.core.rolling.helper.IntegerTokenConverter;
import com.market.sdk.reflect.ReflectUtilsForMiui;
import com.miui.common.app.BaseNoteApp;
import com.miui.common.app.ShareViewModel;
import com.miui.common.base.NoteLoadDataEntity;
import com.miui.common.tool.RomUtils;
import com.miui.creation.taskservice.ITaskServiceCallback;
import com.miui.handwriting.common.MiuiHandWritingView;
import com.miui.handwriting.common.data.bean.BackgroundWrapper;
import com.miui.handwriting.common.data.bean.PartiBean;
import com.miui.handwriting.common.data.dataset.HistorySet;
import com.miui.handwriting.common.data.dataset.ShapeRecognizeParamsSet;
import com.miui.handwriting.common.listener.CommonPageLoadListener;
import com.miui.handwriting.common.listener.CommonPageSaveListener;
import com.miui.handwriting.common.listener.CommonPageStateListener;
import com.miui.handwriting.common.listener.ToolBoxActionCallBack;
import com.miui.handwriting.common.taskservice.CreationServiceManager;
import com.miui.handwriting.common.tools.RecognizeDataHelper;
import com.miui.handwriting.common.tools.constants.Constants;
import com.miui.handwriting.common.view.handview.HandWritingManager;
import com.miui.libhandwritingpage.utils.UIUtils;
import com.miui.libhandwritingpage.view.PageItemView;
import com.miui.notes.NoteApp;
import com.miui.notes.R;
import com.miui.notes.detail.BaseNoteDetailFragment;
import com.miui.notes.feature.folder.ChooserFolderDialogActivity;
import com.miui.notes.home.viewmodel.HomeViewModel;
import com.miui.notes.tool.PreferenceUtils;
import com.miui.pad.feature.notes.handwrite.TouchStylusBoostMode;
import com.miui.pad.feature.notes.multihandwrite.HandWriteViewManager;
import com.miui.pad.feature.notes.multihandwrite.MultiHandWriteDataUtils;
import com.miui.pad.feature.notes.multihandwrite.MultiHandWriteEntity;
import defpackage.R2;
import java.io.File;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;
import java.util.function.ToIntFunction;
import java.util.stream.Stream;
import kotlin.Lazy;
import kotlin.LazyKt;
import kotlin.Metadata;
import kotlin.Result;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.CoroutineContext;
import kotlin.coroutines.intrinsics.IntrinsicsKt;
import kotlin.coroutines.jvm.internal.Boxing;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.SpillingKt;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;
import kotlinx.coroutines.BuildersKt;
import kotlinx.coroutines.BuildersKt__Builders_commonKt;
import kotlinx.coroutines.CoroutineDispatcher;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.Dispatchers;
import kotlinx.coroutines.Job;
import kotlinx.coroutines.SupervisorKt;
import miuix.appcompat.app.ProgressDialog;
import miuix.responsive.map.ScreenSpec;
import xc.engine.api.ICreationEngine;
import xc.engine.utils.FeatureManager;

/* JADX INFO: compiled from: CalcEditFragment.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000Ú\u0001\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\t\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\t\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\n\n\u0002\u0018\u0002\n\u0002\b\u000b\u0018\u00002\u00020\u00012\u00020\u0002:\u0001rB\u0007¢\u0006\u0004\b\u0003\u0010\u0004J\u001a\u00108\u001a\u0002092\b\u0010:\u001a\u0004\u0018\u00010;2\u0006\u0010<\u001a\u00020\u0012H\u0014J\u0012\u0010=\u001a\u0002092\b\u0010>\u001a\u0004\u0018\u00010?H\u0016J\u0012\u0010@\u001a\u0002092\b\u0010A\u001a\u0004\u0018\u00010?H\u0016J\u0010\u0010B\u001a\u0002092\u0006\u0010C\u001a\u00020DH\u0016J\b\u0010E\u001a\u000209H\u0016J\b\u0010F\u001a\u000209H\u0016J\b\u0010G\u001a\u000209H\u0016J\b\u0010H\u001a\u000209H\u0016J\u0006\u0010I\u001a\u000209J\b\u0010J\u001a\u000209H\u0016J\b\u0010K\u001a\u000209H\u0016J(\u0010L\u001a\u0004\u0018\u00010\u00062\b\u0010M\u001a\u0004\u0018\u00010N2\b\u0010O\u001a\u0004\u0018\u00010P2\b\u0010>\u001a\u0004\u0018\u00010?H\u0016J\u001a\u0010Q\u001a\u0002092\u0006\u0010R\u001a\u00020\u00062\b\u0010>\u001a\u0004\u0018\u00010?H\u0016J\u001a\u0010S\u001a\u0002092\u0006\u0010R\u001a\u00020\u00062\b\u0010>\u001a\u0004\u0018\u00010?H\u0016J\b\u0010T\u001a\u00020\u0012H\u0016J\b\u0010U\u001a\u000209H\u0014J\b\u0010V\u001a\u000209H\u0002J\b\u0010W\u001a\u000209H\u0002J\u0012\u0010X\u001a\u0002092\b\u0010Y\u001a\u0004\u0018\u00010ZH\u0002J\u0010\u0010[\u001a\u0002092\u0006\u0010\\\u001a\u00020]H\u0002J\u0010\u0010^\u001a\u0002092\u0006\u0010R\u001a\u00020\u0006H\u0002J\b\u0010_\u001a\u000209H\u0002J\b\u0010`\u001a\u000209H\u0002J\b\u0010a\u001a\u000209H\u0002J\b\u0010b\u001a\u000209H\u0002J\b\u0010c\u001a\u000209H\u0002J\b\u0010d\u001a\u000209H\u0002J\b\u0010e\u001a\u000209H\u0002J\b\u0010f\u001a\u000209H\u0002J\b\u0010g\u001a\u00020hH\u0002J\b\u0010i\u001a\u000209H\u0002J\u0006\u0010j\u001a\u000209J\b\u0010k\u001a\u000209H\u0002J\b\u0010l\u001a\u000209H\u0002J\u0010\u0010m\u001a\u00020\u00122\u0006\u0010n\u001a\u00020\u0012H\u0002J\b\u0010o\u001a\u000209H\u0002J\b\u0010p\u001a\u000209H\u0002J\b\u0010q\u001a\u000209H\u0002R\u0010\u0010\u0005\u001a\u0004\u0018\u00010\u0006X\u0082\u000e¢\u0006\u0002\n\u0000R\u0010\u0010\u0007\u001a\u0004\u0018\u00010\bX\u0082\u000e¢\u0006\u0002\n\u0000R\u0010\u0010\t\u001a\u0004\u0018\u00010\u0006X\u0082\u000e¢\u0006\u0002\n\u0000R\u0010\u0010\n\u001a\u0004\u0018\u00010\u000bX\u0082\u000e¢\u0006\u0002\n\u0000R\u0016\u0010\f\u001a\n\u0012\u0004\u0012\u00020\u000e\u0018\u00010\rX\u0082\u000e¢\u0006\u0002\n\u0000R\u0010\u0010\u000f\u001a\u0004\u0018\u00010\u0010X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0011\u001a\u00020\u0012X\u0082\u000e¢\u0006\u0002\n\u0000R\u0010\u0010\u0013\u001a\u0004\u0018\u00010\u0014X\u0082\u000e¢\u0006\u0002\n\u0000R\u0010\u0010\u0015\u001a\u0004\u0018\u00010\u0014X\u0082\u000e¢\u0006\u0002\n\u0000R\u0010\u0010\u0016\u001a\u0004\u0018\u00010\u0017X\u0082\u000e¢\u0006\u0002\n\u0000R\u001a\u0010\u0018\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u001a0\u00190\u0019X\u0082\u000e¢\u0006\u0002\n\u0000R\u0014\u0010\u001b\u001a\b\u0012\u0004\u0012\u00020\u001c0\u0019X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u001d\u001a\u00020\u0012X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u001e\u001a\u00020\u0012X\u0082\u000e¢\u0006\u0002\n\u0000R\u0010\u0010\u001f\u001a\u0004\u0018\u00010 X\u0082\u000e¢\u0006\u0002\n\u0000R\u0010\u0010!\u001a\u0004\u0018\u00010\"X\u0082\u000e¢\u0006\u0002\n\u0000R\u0010\u0010#\u001a\u0004\u0018\u00010$X\u0082\u000e¢\u0006\u0002\n\u0000R\u0010\u0010%\u001a\u0004\u0018\u00010&X\u0082\u000e¢\u0006\u0002\n\u0000R\u0010\u0010'\u001a\u0004\u0018\u00010&X\u0082\u000e¢\u0006\u0002\n\u0000R\u0010\u0010(\u001a\u0004\u0018\u00010)X\u0082\u000e¢\u0006\u0002\n\u0000R\u0010\u0010*\u001a\u0004\u0018\u00010+X\u0082\u000e¢\u0006\u0002\n\u0000R\u001b\u0010,\u001a\u00020-8BX\u0082\u0084\u0002¢\u0006\f\n\u0004\b0\u00101\u001a\u0004\b.\u0010/R\u0014\u00102\u001a\u0002038VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b4\u00105R\u000e\u00106\u001a\u000207X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006s"}, d2 = {"Lcom/miui/pad/feature/notes/calc/CalcEditFragment;", "Lcom/miui/notes/detail/BaseNoteDetailFragment;", "Lkotlinx/coroutines/CoroutineScope;", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "()V", "loadingContainer", "Landroid/view/View;", "multiHandWriteView", "Lcom/miui/handwriting/common/MiuiHandWritingView;", "mCalcEditFragmentView", "loadingDialog", "Lmiuix/appcompat/app/ProgressDialog;", "mGalleryResult", "Landroidx/activity/result/ActivityResultLauncher;", "Landroid/content/Intent;", "handWriteEntity", "Lcom/miui/pad/feature/notes/multihandwrite/MultiHandWriteEntity;", "isExternalLaunch", "", "externalDirPath", "", "externalEntPath", "backPressedDispatcher", "Landroidx/activity/OnBackPressedDispatcher;", "searchPath", "", "Landroid/graphics/Path;", "searchPageIndex", "", "isHomeBack", "currentIsCreate", "saveClickListener", "Landroid/view/View$OnClickListener;", "toolBoxActionCallBack", "Lcom/miui/handwriting/common/listener/ToolBoxActionCallBack;", "pageStateListener", "Lcom/miui/handwriting/common/listener/CommonPageStateListener;", "pageLoadListener", "Lcom/miui/handwriting/common/listener/CommonPageLoadListener;", "createPageLoadListener", "mOperationReceiver", "Landroid/content/BroadcastReceiver;", "mTaskCallback", "Lcom/miui/pad/feature/notes/calc/CalcEditFragment$TaskServiceCallBack;", "shareViewModel", "Lcom/miui/common/app/ShareViewModel;", "getShareViewModel", "()Lcom/miui/common/app/ShareViewModel;", "shareViewModel$delegate", "Lkotlin/Lazy;", "coroutineContext", "Lkotlin/coroutines/CoroutineContext;", "getCoroutineContext", "()Lkotlin/coroutines/CoroutineContext;", "onBackPressedCallback", "Landroidx/activity/OnBackPressedCallback;", "refreshNote", "", "noteEntity", "Lcom/miui/common/base/NoteLoadDataEntity;", "isFromPc", "onCreate", "savedInstanceState", "Landroid/os/Bundle;", "onActivityCreated", "b", "onConfigurationChanged", "configuration", "Landroid/content/res/Configuration;", "onStart", "onResume", "onPause", "onStop", "onWindowFocused", "onDestroyView", "onDestroy", "onInflateView", "inflater", "Landroid/view/LayoutInflater;", "container", "Landroid/view/ViewGroup;", "onViewInflated", "view", "onViewCreated", "onBackPressed", "initBaseResultLauncher", "loadEntity", "updateUI", "addPicToNote", "picturePath", "Landroid/net/Uri;", "updateHomeButtonState", "screenSpec", "Lmiuix/responsive/map/ScreenSpec;", "initView", "clearListener", "initListener", "initCreatePageLoadListener", "initPageLoadListener", "initActionCallBackListener", "initPageListener", "registerListener", "ensureDrawingEnabled", "saveWithExit", "Lkotlinx/coroutines/Job;", "saveExternalThenFinish", "saveExternalOnly", "saveData", "finishActivity", "saveToDB", "updateThumbnail", "showLoadingDialog", "dismissLoadingDialog", "registerCreationTaskReceiver", "TaskServiceCallBack", "app_PhoneGlobalRelease"}, k = 1, mv = {2, 2, 0}, xi = 48)
public final class CalcEditFragment extends BaseNoteDetailFragment implements CoroutineScope {
    private OnBackPressedDispatcher backPressedDispatcher;
    private CommonPageLoadListener createPageLoadListener;
    private boolean currentIsCreate;
    private String externalDirPath;
    private String externalEntPath;
    private MultiHandWriteEntity handWriteEntity;
    private boolean isExternalLaunch;
    private boolean isHomeBack;
    private View loadingContainer;
    private ProgressDialog loadingDialog;
    private View mCalcEditFragmentView;
    private ActivityResultLauncher<Intent> mGalleryResult;
    private BroadcastReceiver mOperationReceiver;
    private TaskServiceCallBack mTaskCallback;
    private MiuiHandWritingView multiHandWriteView;
    private CommonPageLoadListener pageLoadListener;
    private CommonPageStateListener pageStateListener;
    private View.OnClickListener saveClickListener;
    private ToolBoxActionCallBack toolBoxActionCallBack;
    private List<? extends List<? extends Path>> searchPath = new ArrayList();
    private List<Integer> searchPageIndex = new ArrayList();

    /* JADX INFO: renamed from: shareViewModel$delegate, reason: from kotlin metadata */
    private final Lazy shareViewModel = LazyKt.lazy(new Function0() { // from class: com.miui.pad.feature.notes.calc.CalcEditFragment$$ExternalSyntheticLambda3
        @Override // kotlin.jvm.functions.Function0
        public final Object invoke() {
            return CalcEditFragment.shareViewModel_delegate$lambda$0(this.f$0);
        }
    });
    private final OnBackPressedCallback onBackPressedCallback = new OnBackPressedCallback() { // from class: com.miui.pad.feature.notes.calc.CalcEditFragment$onBackPressedCallback$1
        {
            super(true);
        }

        @Override // androidx.activity.OnBackPressedCallback
        public void handleOnBackPressed() {
            this.this$0.saveWithExit();
        }
    };

    private final ShareViewModel getShareViewModel() {
        return (ShareViewModel) this.shareViewModel.getValue();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final ShareViewModel shareViewModel_delegate$lambda$0(CalcEditFragment calcEditFragment) {
        Application application = calcEditFragment.requireActivity().getApplication();
        Intrinsics.checkNotNull(application, "null cannot be cast to non-null type com.miui.notes.NoteApp");
        return ((NoteApp) application).getGlobalSharedViewModel();
    }

    @Override // kotlinx.coroutines.CoroutineScope
    public CoroutineContext getCoroutineContext() {
        return SupervisorKt.SupervisorJob$default((Job) null, 1, (Object) null).plus(Dispatchers.getMain());
    }

    @Override // com.miui.notes.detail.BaseNoteDetailFragment
    protected void refreshNote(NoteLoadDataEntity noteEntity, boolean isFromPc) {
        Log.d(getTAG(), "refreshNote: ");
    }

    @Override // com.miui.notes.detail.BaseNoteDetailFragment, miuix.appcompat.app.Fragment, androidx.fragment.app.Fragment
    public void onCreate(Bundle savedInstanceState) {
        setThemeRes(R.style.NoteTheme_List_NoTitle);
        super.onCreate(savedInstanceState);
        Log.d(getTAG(), "onCreate: ");
        HandWritingManager.INSTANCE.setViewType(0);
        setSupportNavigator(false);
        this.mTaskCallback = new TaskServiceCallBack(this);
        registerCreationTaskReceiver();
        CalcTransferManager.INSTANCE.getInstance().calcCreate(hashCode());
    }

    @Override // com.miui.notes.detail.BaseNoteDetailFragment, androidx.fragment.app.Fragment
    public void onActivityCreated(Bundle b) {
        super.onActivityCreated(b);
        UIUtils.setFullScreenMode(getActivity(), true);
    }

    @Override // com.miui.notes.detail.BaseNoteDetailFragment, miuix.appcompat.app.Fragment, androidx.fragment.app.Fragment, android.content.ComponentCallbacks
    public void onConfigurationChanged(Configuration configuration) {
        Intrinsics.checkNotNullParameter(configuration, "configuration");
        super.onConfigurationChanged(configuration);
        UIUtils.setFullScreenMode(getActivity(), true);
        View view = this.mCalcEditFragmentView;
        if (view != null) {
            view.setBackgroundColor(getResources().getColor(R.color.creation_action_bar_bg));
        }
    }

    @Override // com.miui.notes.detail.BaseNoteDetailFragment, androidx.fragment.app.Fragment
    public void onStart() {
        super.onStart();
        Log.d(getTAG(), "onStart: ");
        TouchStylusBoostMode.INSTANCE.getSInstance().setMode(1);
    }

    @Override // com.miui.notes.detail.BaseNoteDetailFragment, miuix.appcompat.app.Fragment, androidx.fragment.app.Fragment
    public void onResume() {
        super.onResume();
        Log.d(getTAG(), "onResume: ");
        CreationServiceManager.INSTANCE.bindCreationTaskService(BaseNoteApp.INSTANCE.getInstance());
    }

    @Override // com.miui.notes.detail.BaseNoteDetailFragment, androidx.fragment.app.Fragment
    public void onPause() {
        MiuiHandWritingView miuiHandWritingView = this.multiHandWriteView;
        if (miuiHandWritingView != null) {
            miuiHandWritingView.onPause();
        }
        super.onPause();
        MiuiHandWritingView miuiHandWritingView2 = this.multiHandWriteView;
        if (miuiHandWritingView2 != null) {
            miuiHandWritingView2.dismissPopupWindow();
        }
        FragmentActivity activity = getActivity();
        if (activity == null || !activity.isFinishing()) {
            if (activity == null || !activity.isChangingConfigurations()) {
                Log.d(getTAG(), "xqw- Calculator entry: quick save for process kill");
                LifecycleOwner viewLifecycleOwner = getViewLifecycleOwner();
                Intrinsics.checkNotNullExpressionValue(viewLifecycleOwner, "getViewLifecycleOwner(...)");
                BuildersKt__Builders_commonKt.launch$default(LifecycleOwnerKt.getLifecycleScope(viewLifecycleOwner), null, null, new C03341(null), 3, null);
            }
        }
    }

    /* JADX INFO: renamed from: com.miui.pad.feature.notes.calc.CalcEditFragment$onPause$1, reason: invalid class name and case insensitive filesystem */
    /* JADX INFO: compiled from: CalcEditFragment.kt */
    @Metadata(d1 = {"\u0000\n\n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\u0010\u0000\u001a\u00020\u0001*\u00020\u0002H\n"}, d2 = {"<anonymous>", "", "Lkotlinx/coroutines/CoroutineScope;"}, k = 3, mv = {2, 2, 0}, xi = 48)
    @DebugMetadata(c = "com.miui.pad.feature.notes.calc.CalcEditFragment$onPause$1", f = "CalcEditFragment.kt", i = {0, 0, 0}, l = {R2.array.ai_tag_close_gradient_border_new_colors}, m = "invokeSuspend", n = {"$this$launch", "$this$invokeSuspend_u24lambda_u240", "$i$a$-runCatching-CalcEditFragment$onPause$1$1"}, s = {"L$0", "L$1", "I$0"})
    static final class C03341 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
        int I$0;
        private /* synthetic */ Object L$0;
        Object L$1;
        int label;

        C03341(Continuation<? super C03341> continuation) {
            super(2, continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
            C03341 c03341 = CalcEditFragment.this.new C03341(continuation);
            c03341.L$0 = obj;
            return c03341;
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(CoroutineScope coroutineScope, Continuation<? super Unit> continuation) {
            return ((C03341) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            Object objM2532constructorimpl;
            CoroutineScope coroutineScope = (CoroutineScope) this.L$0;
            Object coroutine_suspended = IntrinsicsKt.getCOROUTINE_SUSPENDED();
            int i = this.label;
            try {
                if (i == 0) {
                    ResultKt.throwOnFailure(obj);
                    CalcEditFragment calcEditFragment = CalcEditFragment.this;
                    Result.Companion companion = Result.INSTANCE;
                    CoroutineDispatcher io2 = Dispatchers.getIO();
                    CalcEditFragment$onPause$1$1$1 calcEditFragment$onPause$1$1$1 = new CalcEditFragment$onPause$1$1$1(calcEditFragment, null);
                    this.L$0 = SpillingKt.nullOutSpilledVariable(coroutineScope);
                    this.L$1 = SpillingKt.nullOutSpilledVariable(coroutineScope);
                    this.I$0 = 0;
                    this.label = 1;
                    obj = BuildersKt.withContext(io2, calcEditFragment$onPause$1$1$1, this);
                    if (obj == coroutine_suspended) {
                        return coroutine_suspended;
                    }
                } else {
                    if (i != 1) {
                        throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                    }
                    ResultKt.throwOnFailure(obj);
                }
                objM2532constructorimpl = Result.m2532constructorimpl((Boolean) obj);
            } catch (Throwable th) {
                Result.Companion companion2 = Result.INSTANCE;
                objM2532constructorimpl = Result.m2532constructorimpl(ResultKt.createFailure(th));
            }
            CalcEditFragment calcEditFragment2 = CalcEditFragment.this;
            Throwable thM2535exceptionOrNullimpl = Result.m2535exceptionOrNullimpl(objM2532constructorimpl);
            if (thM2535exceptionOrNullimpl != null) {
                Log.e(calcEditFragment2.getTAG(), "Auto-save failed in onPause", thM2535exceptionOrNullimpl);
            }
            return Unit.INSTANCE;
        }
    }

    @Override // com.miui.notes.detail.BaseNoteDetailFragment, miuix.appcompat.app.Fragment, androidx.fragment.app.Fragment
    public void onStop() {
        super.onStop();
        Log.d(getTAG(), "onStop: ");
        TouchStylusBoostMode.INSTANCE.getSInstance().setMode(0);
    }

    public final void onWindowFocused() {
        MiuiHandWritingView miuiHandWritingView = this.multiHandWriteView;
        if (miuiHandWritingView != null) {
            miuiHandWritingView.onWindowFocused();
        }
    }

    @Override // com.miui.notes.detail.BaseNoteDetailFragment, miuix.appcompat.app.Fragment, androidx.fragment.app.Fragment
    public void onDestroyView() {
        super.onDestroyView();
        Log.d(getTAG(), "onDestroyView: ");
        UIUtils.setFullScreenMode(getActivity(), false);
        try {
            if (FeatureManager.getInstance().isEnableMathRecognition()) {
                MiuiHandWritingView miuiHandWritingView = this.multiHandWriteView;
                PageItemView currentItemView = miuiHandWritingView != null ? miuiHandWritingView.getCurrentItemView() : null;
                ICreationEngine creationEngine = currentItemView != null ? currentItemView.getCreationEngine() : null;
                if (creationEngine != null && creationEngine.getCurveEngine() != null) {
                    creationEngine.getCurveEngine().getRecognitionModel().dispose();
                    Log.d("yjg", "Page destroyed, cleared recognition engine state");
                }
            }
        } catch (Exception e) {
            Log.e(getTAG(), "Failed to clear recognition engine on destroy", e);
        }
        this.handWriteEntity = null;
        clearListener();
        MiuiHandWritingView miuiHandWritingView2 = this.multiHandWriteView;
        if (miuiHandWritingView2 != null) {
            miuiHandWritingView2.setSaveClickListener(null);
            miuiHandWritingView2.setActionCallBack(null);
            miuiHandWritingView2.setPageStateListener(null);
            miuiHandWritingView2.release();
        }
        dismissLoadingDialog();
        this.mTaskCallback = null;
    }

    @Override // com.miui.notes.detail.BaseNoteDetailFragment, miuix.appcompat.app.Fragment, androidx.fragment.app.Fragment
    public void onDestroy() {
        super.onDestroy();
        Log.d(getTAG(), "onDestroy: ");
        this.onBackPressedCallback.remove();
        CreationServiceManager.INSTANCE.clearLastMessage();
        this.mTaskCallback = null;
        if (this.mOperationReceiver != null) {
            LocalBroadcastManager localBroadcastManager = LocalBroadcastManager.getInstance(BaseNoteApp.INSTANCE.getInstance());
            BroadcastReceiver broadcastReceiver = this.mOperationReceiver;
            Intrinsics.checkNotNull(broadcastReceiver);
            localBroadcastManager.unregisterReceiver(broadcastReceiver);
            this.mOperationReceiver = null;
        }
        CalcTransferManager.INSTANCE.getInstance().calcDestroy(hashCode());
    }

    @Override // com.miui.notes.detail.BaseNoteDetailFragment, miuix.appcompat.app.Fragment, miuix.appcompat.app.IFragment
    public View onInflateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View viewInflate = inflater != null ? inflater.inflate(R.layout.fragment_creation_edit, container, false) : null;
        this.mCalcEditFragmentView = viewInflate;
        if (viewInflate != null) {
            initView(viewInflate);
        }
        return this.mCalcEditFragmentView;
    }

    @Override // com.miui.notes.detail.BaseNoteDetailFragment, miuix.appcompat.app.Fragment, miuix.appcompat.app.IFragment
    public void onViewInflated(View view, Bundle savedInstanceState) {
        Intrinsics.checkNotNullParameter(view, "view");
        super.onViewInflated(view, savedInstanceState);
        Log.d(getTAG(), "onViewInflated: ");
        MiuiHandWritingView miuiHandWritingView = this.multiHandWriteView;
        if (miuiHandWritingView != null) {
            miuiHandWritingView.setCanWrite(true);
        }
        updateHomeButtonState(this.mScreenSpec);
        HomeViewModel homeViewModel = this.homeViewModel;
        if (homeViewModel != null) {
            homeViewModel.setKeyboardChange(false);
        }
    }

    @Override // com.miui.notes.detail.BaseNoteDetailFragment, miuix.appcompat.app.Fragment, androidx.fragment.app.Fragment
    public void onViewCreated(View view, Bundle savedInstanceState) {
        Intrinsics.checkNotNullParameter(view, "view");
        super.onViewCreated(view, savedInstanceState);
        OnBackPressedDispatcher onBackPressedDispatcher = requireActivity().getOnBackPressedDispatcher();
        this.backPressedDispatcher = onBackPressedDispatcher;
        if (onBackPressedDispatcher != null) {
            LifecycleOwner viewLifecycleOwner = getViewLifecycleOwner();
            Intrinsics.checkNotNullExpressionValue(viewLifecycleOwner, "getViewLifecycleOwner(...)");
            onBackPressedDispatcher.addCallback(viewLifecycleOwner, this.onBackPressedCallback);
        }
        loadEntity();
    }

    @Override // com.miui.notes.detail.BaseNoteDetailFragment
    public boolean onBackPressed() {
        saveWithExit();
        return true;
    }

    @Override // com.miui.notes.detail.BaseNoteDetailFragment
    protected void initBaseResultLauncher() {
        this.mGalleryResult = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback() { // from class: com.miui.pad.feature.notes.calc.CalcEditFragment$$ExternalSyntheticLambda0
            @Override // androidx.activity.result.ActivityResultCallback
            public final void onActivityResult(Object obj) {
                CalcEditFragment.initBaseResultLauncher$lambda$3(this.f$0, (ActivityResult) obj);
            }
        });
        this.toChooseFolderResult = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() { // from class: com.miui.pad.feature.notes.calc.CalcEditFragment.initBaseResultLauncher.2
            @Override // androidx.activity.result.ActivityResultCallback
            public void onActivityResult(ActivityResult result) {
                Log.d(CalcEditFragment.this.getTAG(), "ChooseFolderResult: ");
                HomeViewModel homeViewModel = CalcEditFragment.this.homeViewModel;
                if (homeViewModel != null) {
                    homeViewModel.toDetailResultCallback();
                }
                if (result == null || result.getResultCode() != -1) {
                    return;
                }
                Intent data = result.getData();
                if (!CalcEditFragment.this.isAdded() || data == null) {
                    return;
                }
                long longExtra = data.getLongExtra(ChooserFolderDialogActivity.CHOOSER_FOLDER_SELECT_FOLDER_ID, 0L);
                MultiHandWriteEntity multiHandWriteEntity = CalcEditFragment.this.handWriteEntity;
                if (multiHandWriteEntity != null) {
                    multiHandWriteEntity.folderId = longExtra;
                }
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void initBaseResultLauncher$lambda$3(CalcEditFragment calcEditFragment, ActivityResult activityResult) {
        Log.d(calcEditFragment.getTAG(), "GalleryResult: ");
        HomeViewModel homeViewModel = calcEditFragment.homeViewModel;
        if (homeViewModel != null) {
            homeViewModel.toDetailResultCallback();
        }
        Intent data = activityResult != null ? activityResult.getData() : null;
        if (activityResult == null || activityResult.getResultCode() != -1 || data == null) {
            return;
        }
        calcEditFragment.addPicToNote(data.getData());
    }

    /* JADX INFO: renamed from: com.miui.pad.feature.notes.calc.CalcEditFragment$loadEntity$1, reason: invalid class name and case insensitive filesystem */
    /* JADX INFO: compiled from: CalcEditFragment.kt */
    @Metadata(d1 = {"\u0000\n\n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\u0010\u0000\u001a\u00020\u0001*\u00020\u0002H\n"}, d2 = {"<anonymous>", "", "Lkotlinx/coroutines/CoroutineScope;"}, k = 3, mv = {2, 2, 0}, xi = 48)
    @DebugMetadata(c = "com.miui.pad.feature.notes.calc.CalcEditFragment$loadEntity$1", f = "CalcEditFragment.kt", i = {}, l = {}, m = "invokeSuspend", n = {}, s = {})
    static final class C03331 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
        int label;

        C03331(Continuation<? super C03331> continuation) {
            super(2, continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
            return CalcEditFragment.this.new C03331(continuation);
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(CoroutineScope coroutineScope, Continuation<? super Unit> continuation) {
            return ((C03331) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            String str;
            IntrinsicsKt.getCOROUTINE_SUSPENDED();
            if (this.label != 0) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            ResultKt.throwOnFailure(obj);
            Log.d(CalcEditFragment.this.getTAG(), "loadNoteFromArguments: NoteID:" + CalcEditFragment.this.getNoteId());
            File externalCacheDir = CalcEditFragment.this.requireContext().getExternalCacheDir();
            String absolutePath = externalCacheDir != null ? externalCacheDir.getAbsolutePath() : null;
            CalcEditFragment calcEditFragment = CalcEditFragment.this;
            if (absolutePath != null) {
                str = absolutePath + "/external_handwrite";
                try {
                    File file = new File(str);
                    if (!file.exists()) {
                        file.mkdirs();
                    }
                } catch (Exception unused) {
                }
            } else {
                str = null;
            }
            calcEditFragment.externalDirPath = str;
            CalcEditFragment calcEditFragment2 = CalcEditFragment.this;
            String str2 = calcEditFragment2.externalDirPath;
            calcEditFragment2.externalEntPath = str2 != null ? str2 + "/multi_page_ent.ent" : null;
            CalcEditFragment.this.updateUI();
            return Unit.INSTANCE;
        }
    }

    private final void loadEntity() {
        BuildersKt__Builders_commonKt.launch$default(this, null, null, new C03331(null), 3, null);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void updateUI() {
        String tag = getTAG();
        boolean z = this.isExternalLaunch;
        MultiHandWriteEntity multiHandWriteEntity = this.handWriteEntity;
        Log.d(tag, "updateUI: isExternalLaunch=" + z + ", handWriteEntity=" + (multiHandWriteEntity != null ? Long.valueOf(multiHandWriteEntity.id) : null));
        boolean booleanExtra = requireActivity().getIntent().getBooleanExtra("infinite_canvas", false);
        Log.d(getTAG(), "External launch: entPath=" + this.externalEntPath);
        HandWriteViewManager.INSTANCE.createOrLoadExternal(this.multiHandWriteView, this.externalDirPath, this.externalEntPath, this.createPageLoadListener, this.pageLoadListener, booleanExtra);
    }

    private final void addPicToNote(Uri picturePath) {
        MiuiHandWritingView miuiHandWritingView = this.multiHandWriteView;
        if (miuiHandWritingView != null) {
            miuiHandWritingView.addImage(picturePath, 1);
        }
    }

    private final void updateHomeButtonState(ScreenSpec screenSpec) {
        boolean z = PreferenceUtils.getNoteHomePageArrayWay(requireActivity(), 0) == 1;
        if (!RomUtils.isFoldDevice()) {
            z = false;
        }
        this.isHomeBack = z || screenSpec.type == 1;
    }

    private final void initView(View view) {
        this.loadingContainer = view.findViewById(R.id.loading_container);
        MiuiHandWritingView miuiHandWritingView = (MiuiHandWritingView) view.findViewById(R.id.handwriting_view);
        this.multiHandWriteView = miuiHandWritingView;
        if (miuiHandWritingView != null) {
            miuiHandWritingView.initUIProperties();
        }
        MiuiHandWritingView miuiHandWritingView2 = this.multiHandWriteView;
        if (miuiHandWritingView2 != null) {
            miuiHandWritingView2.setTitleViewVisible(false);
        }
        MiuiHandWritingView miuiHandWritingView3 = this.multiHandWriteView;
        if (miuiHandWritingView3 != null) {
            miuiHandWritingView3.setPageIndexVisible(false);
        }
        initListener();
        registerListener();
    }

    private final void clearListener() {
        this.saveClickListener = null;
        this.toolBoxActionCallBack = null;
        this.pageLoadListener = null;
        this.createPageLoadListener = null;
    }

    private final void initListener() {
        this.saveClickListener = new View.OnClickListener() { // from class: com.miui.pad.feature.notes.calc.CalcEditFragment$$ExternalSyntheticLambda2
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f$0.saveWithExit();
            }
        };
        initActionCallBackListener();
        initPageListener();
        initPageLoadListener();
        initCreatePageLoadListener();
    }

    private final void initCreatePageLoadListener() {
        this.createPageLoadListener = new CommonPageLoadListener() { // from class: com.miui.pad.feature.notes.calc.CalcEditFragment.initCreatePageLoadListener.1
            @Override // com.miui.handwriting.common.listener.CommonPageLoadListener
            public void onPageCount(int i) {
            }

            @Override // com.miui.handwriting.common.listener.CommonPageLoadListener
            public void onPdfError(int type) {
            }

            @Override // com.miui.handwriting.common.listener.CommonPageLoadListener
            public void onLoadCompleted(boolean z, boolean isPdf, String str) {
                Intrinsics.checkNotNullParameter(str, "str");
                MiuiHandWritingView miuiHandWritingView = CalcEditFragment.this.multiHandWriteView;
                if (miuiHandWritingView != null) {
                    miuiHandWritingView.registerToolBarListener();
                }
                MiuiHandWritingView miuiHandWritingView2 = CalcEditFragment.this.multiHandWriteView;
                if (miuiHandWritingView2 != null) {
                    miuiHandWritingView2.refreshPenType();
                }
                MiuiHandWritingView miuiHandWritingView3 = CalcEditFragment.this.multiHandWriteView;
                if (miuiHandWritingView3 != null) {
                    miuiHandWritingView3.setOneShape(ShapeRecognizeParamsSet.isEnable());
                }
                CalcEditFragment.this.ensureDrawingEnabled();
            }
        };
    }

    /* JADX INFO: renamed from: com.miui.pad.feature.notes.calc.CalcEditFragment$initPageLoadListener$1, reason: invalid class name and case insensitive filesystem */
    /* JADX INFO: compiled from: CalcEditFragment.kt */
    @Metadata(d1 = {"\u0000)\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0003*\u0001\u0000\b\n\u0018\u00002\u00020\u0001J \u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u00052\u0006\u0010\u0006\u001a\u00020\u00052\u0006\u0010\u0007\u001a\u00020\bH\u0016J\u0010\u0010\t\u001a\u00020\u00032\u0006\u0010\n\u001a\u00020\u000bH\u0016J\u0010\u0010\f\u001a\u00020\u00032\u0006\u0010\r\u001a\u00020\u000bH\u0016¨\u0006\u000e"}, d2 = {"com/miui/pad/feature/notes/calc/CalcEditFragment$initPageLoadListener$1", "Lcom/miui/handwriting/common/listener/CommonPageLoadListener;", "onLoadCompleted", "", "success", "", "isPdf", "str", "", "onPageCount", IntegerTokenConverter.CONVERTER_KEY, "", "onPdfError", "type", "app_PhoneGlobalRelease"}, k = 1, mv = {2, 2, 0}, xi = 48)
    public static final class C03321 implements CommonPageLoadListener {
        /* JADX INFO: Access modifiers changed from: private */
        public static final int onLoadCompleted$lambda$0(int i) {
            return i;
        }

        @Override // com.miui.handwriting.common.listener.CommonPageLoadListener
        public void onPdfError(int type) {
        }

        C03321() {
        }

        @Override // com.miui.handwriting.common.listener.CommonPageLoadListener
        public void onLoadCompleted(boolean success, boolean isPdf, String str) {
            Intrinsics.checkNotNullParameter(str, "str");
            Log.d(CalcEditFragment.this.getTAG(), "onLoadCompleted: ");
            MiuiHandWritingView miuiHandWritingView = CalcEditFragment.this.multiHandWriteView;
            if (miuiHandWritingView != null) {
                miuiHandWritingView.setDoubleFingerClickUndo(HistorySet.isIsDoubleClickUndo());
            }
            MiuiHandWritingView miuiHandWritingView2 = CalcEditFragment.this.multiHandWriteView;
            if (miuiHandWritingView2 != null) {
                Stream stream = CalcEditFragment.this.searchPageIndex.stream();
                final Function1 function1 = new Function1() { // from class: com.miui.pad.feature.notes.calc.CalcEditFragment$initPageLoadListener$1$$ExternalSyntheticLambda0
                    @Override // kotlin.jvm.functions.Function1
                    public final Object invoke(Object obj) {
                        return Integer.valueOf(CalcEditFragment.C03321.onLoadCompleted$lambda$0(((Integer) obj).intValue()));
                    }
                };
                miuiHandWritingView2.setSearchPathArray(stream.mapToInt(new ToIntFunction() { // from class: com.miui.pad.feature.notes.calc.CalcEditFragment$initPageLoadListener$1$$ExternalSyntheticLambda1
                    @Override // java.util.function.ToIntFunction
                    public final int applyAsInt(Object obj) {
                        return CalcEditFragment.C03321.onLoadCompleted$lambda$1(function1, obj);
                    }
                }).toArray(), CalcEditFragment.this.searchPath);
            }
            MiuiHandWritingView miuiHandWritingView3 = CalcEditFragment.this.multiHandWriteView;
            if (miuiHandWritingView3 != null) {
                miuiHandWritingView3.registerToolBarListener();
            }
            MiuiHandWritingView miuiHandWritingView4 = CalcEditFragment.this.multiHandWriteView;
            if (miuiHandWritingView4 != null) {
                miuiHandWritingView4.refreshPenType();
            }
            CalcEditFragment.this.ensureDrawingEnabled();
            HandWriteViewManager.INSTANCE.loadPageItem(CalcEditFragment.this.multiHandWriteView, CalcEditFragment.this.handWriteEntity);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static final int onLoadCompleted$lambda$1(Function1 function1, Object obj) {
            return ((Number) function1.invoke(obj)).intValue();
        }

        @Override // com.miui.handwriting.common.listener.CommonPageLoadListener
        public void onPageCount(int i) {
            Log.d(CalcEditFragment.this.getTAG(), "onPageCount: " + i);
        }
    }

    private final void initPageLoadListener() {
        this.pageLoadListener = new C03321();
    }

    private final void initActionCallBackListener() {
        this.toolBoxActionCallBack = new ToolBoxActionCallBack() { // from class: com.miui.pad.feature.notes.calc.CalcEditFragment.initActionCallBackListener.1
            @Override // com.miui.handwriting.common.listener.ToolBoxActionCallBack
            public void insertImage() {
            }

            public void showGuide(boolean ispop) {
            }

            @Override // com.miui.handwriting.common.listener.ToolBoxActionCallBack
            public /* bridge */ /* synthetic */ void showGuide(Boolean bool) {
                showGuide(bool.booleanValue());
            }

            @Override // com.miui.handwriting.common.listener.ToolBoxActionCallBack
            public void callBackground(BackgroundWrapper backgroundWrapper) {
                Intrinsics.checkNotNullParameter(backgroundWrapper, "backgroundWrapper");
                MiuiHandWritingView miuiHandWritingView = CalcEditFragment.this.multiHandWriteView;
                if (miuiHandWritingView != null) {
                    miuiHandWritingView.setPageBackground(miuiHandWritingView.getCurrentPosition(), backgroundWrapper.colorIndex, backgroundWrapper.paperIndex);
                }
            }

            @Override // com.miui.handwriting.common.listener.ToolBoxActionCallBack
            public void clickThumbnail() {
                MiuiHandWritingView miuiHandWritingView = CalcEditFragment.this.multiHandWriteView;
                if (miuiHandWritingView != null) {
                    if (!miuiHandWritingView.getHandWritingView().getThumbnailManager().getShow()) {
                        miuiHandWritingView.getHandWritingView().getThumbnailManager().show(miuiHandWritingView.getCurrentItemView().model.getCurrentPosition(), miuiHandWritingView.getCurrentItemView().model.getPageCount());
                        return;
                    }
                    miuiHandWritingView.getHandWritingView().getThumbnailManager().hide();
                }
            }
        };
    }

    private final void initPageListener() {
        this.pageStateListener = new CommonPageStateListener() { // from class: com.miui.pad.feature.notes.calc.CalcEditFragment.initPageListener.1
            @Override // com.miui.handwriting.common.listener.CommonPageStateListener
            public void onContentChanged(int pageIndex) {
            }

            @Override // com.miui.handwriting.common.listener.CommonPageStateListener
            public void onStepChanged(int stepType, int position, boolean canUndo, boolean canRedo, boolean dismissLoadingDialog) {
                MiuiHandWritingView miuiHandWritingView = CalcEditFragment.this.multiHandWriteView;
                if (miuiHandWritingView != null) {
                    miuiHandWritingView.onStepChanged(stepType, position, canUndo, canRedo, dismissLoadingDialog);
                }
            }

            @Override // com.miui.handwriting.common.listener.CommonPageStateListener
            public void onDoubleUndo() {
                MiuiHandWritingView miuiHandWritingView = CalcEditFragment.this.multiHandWriteView;
                if (miuiHandWritingView != null) {
                    miuiHandWritingView.onDoubleUndo();
                }
            }

            @Override // com.miui.handwriting.common.listener.CommonPageStateListener
            public void onPageScale(float scale) {
                MiuiHandWritingView miuiHandWritingView = CalcEditFragment.this.multiHandWriteView;
                if (miuiHandWritingView != null) {
                    miuiHandWritingView.updateBackgroundMargin(scale);
                }
            }

            @Override // com.miui.handwriting.common.listener.CommonPageStateListener
            public void onPageLoadingFinish() {
                MiuiHandWritingView miuiHandWritingView = CalcEditFragment.this.multiHandWriteView;
                if (miuiHandWritingView != null) {
                    miuiHandWritingView.setShareIconState(true);
                }
                CalcEditFragment.this.ensureDrawingEnabled();
            }

            @Override // com.miui.handwriting.common.listener.CommonPageStateListener
            public void dataLoadFinish() {
                MiuiHandWritingView miuiHandWritingView = CalcEditFragment.this.multiHandWriteView;
                if (miuiHandWritingView != null) {
                    miuiHandWritingView.resetCurveCenter();
                }
            }
        };
    }

    private final void registerListener() {
        MiuiHandWritingView miuiHandWritingView = this.multiHandWriteView;
        if (miuiHandWritingView != null) {
            if (RomUtils.isPadDevice()) {
                miuiHandWritingView.setPageStateListener(this.pageStateListener);
            } else {
                miuiHandWritingView.setCanWrite(false);
            }
            miuiHandWritingView.setActionCallBack(this.toolBoxActionCallBack);
            miuiHandWritingView.setSaveClickListener(this.saveClickListener);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void ensureDrawingEnabled() {
        final MiuiHandWritingView miuiHandWritingView = this.multiHandWriteView;
        if (miuiHandWritingView != null) {
            miuiHandWritingView.post(new Runnable() { // from class: com.miui.pad.feature.notes.calc.CalcEditFragment$$ExternalSyntheticLambda1
                @Override // java.lang.Runnable
                public final void run() {
                    miuiHandWritingView.setCanWrite(true);
                }
            });
        }
    }

    /* JADX INFO: renamed from: com.miui.pad.feature.notes.calc.CalcEditFragment$saveWithExit$1, reason: invalid class name and case insensitive filesystem */
    /* JADX INFO: compiled from: CalcEditFragment.kt */
    @Metadata(d1 = {"\u0000\n\n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\u0010\u0000\u001a\u00020\u0001*\u00020\u0002H\n"}, d2 = {"<anonymous>", "", "Lkotlinx/coroutines/CoroutineScope;"}, k = 3, mv = {2, 2, 0}, xi = 48)
    @DebugMetadata(c = "com.miui.pad.feature.notes.calc.CalcEditFragment$saveWithExit$1", f = "CalcEditFragment.kt", i = {}, l = {}, m = "invokeSuspend", n = {}, s = {})
    static final class C03391 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
        int label;

        C03391(Continuation<? super C03391> continuation) {
            super(2, continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
            return CalcEditFragment.this.new C03391(continuation);
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(CoroutineScope coroutineScope, Continuation<? super Unit> continuation) {
            return ((C03391) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            IntrinsicsKt.getCOROUTINE_SUSPENDED();
            if (this.label == 0) {
                ResultKt.throwOnFailure(obj);
                MiuiHandWritingView miuiHandWritingView = CalcEditFragment.this.multiHandWriteView;
                if (miuiHandWritingView != null) {
                    miuiHandWritingView.clearTitleCursor();
                }
                CalcEditFragment.this.showLoadingDialog();
                CalcEditFragment.this.saveExternalThenFinish();
                return Unit.INSTANCE;
            }
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final Job saveWithExit() {
        return BuildersKt__Builders_commonKt.launch$default(this, null, null, new C03391(null), 3, null);
    }

    /* JADX INFO: renamed from: com.miui.pad.feature.notes.calc.CalcEditFragment$saveExternalThenFinish$1, reason: invalid class name and case insensitive filesystem */
    /* JADX INFO: compiled from: CalcEditFragment.kt */
    @Metadata(d1 = {"\u0000\n\n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\u0010\u0000\u001a\u00020\u0001*\u00020\u0002H\n"}, d2 = {"<anonymous>", "", "Lkotlinx/coroutines/CoroutineScope;"}, k = 3, mv = {2, 2, 0}, xi = 48)
    @DebugMetadata(c = "com.miui.pad.feature.notes.calc.CalcEditFragment$saveExternalThenFinish$1", f = "CalcEditFragment.kt", i = {}, l = {}, m = "invokeSuspend", n = {}, s = {})
    static final class C03381 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
        int label;

        C03381(Continuation<? super C03381> continuation) {
            super(2, continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
            return CalcEditFragment.this.new C03381(continuation);
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(CoroutineScope coroutineScope, Continuation<? super Unit> continuation) {
            return ((C03381) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            IntrinsicsKt.getCOROUTINE_SUSPENDED();
            if (this.label == 0) {
                ResultKt.throwOnFailure(obj);
                MiuiHandWritingView miuiHandWritingView = CalcEditFragment.this.multiHandWriteView;
                if (miuiHandWritingView != null) {
                    final CalcEditFragment calcEditFragment = CalcEditFragment.this;
                    Boxing.boxBoolean(miuiHandWritingView.save(new CommonPageSaveListener() { // from class: com.miui.pad.feature.notes.calc.CalcEditFragment$saveExternalThenFinish$1$$ExternalSyntheticLambda0
                        @Override // com.miui.handwriting.common.listener.CommonPageSaveListener
                        public final void onDataSaved(boolean z, String str, boolean z2) {
                            CalcEditFragment.C03381.invokeSuspend$lambda$0(calcEditFragment, z, str, z2);
                        }
                    }));
                }
                return Unit.INSTANCE;
            }
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static final void invokeSuspend$lambda$0(CalcEditFragment calcEditFragment, boolean z, String str, boolean z2) {
            Log.d(calcEditFragment.getTAG(), "saveExternalThenFinish: isSuccess:" + z + " path:" + str);
            calcEditFragment.finishActivity();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void saveExternalThenFinish() {
        BuildersKt__Builders_commonKt.launch$default(this, Dispatchers.getIO(), null, new C03381(null), 2, null);
    }

    /* JADX INFO: renamed from: com.miui.pad.feature.notes.calc.CalcEditFragment$saveExternalOnly$1, reason: invalid class name and case insensitive filesystem */
    /* JADX INFO: compiled from: CalcEditFragment.kt */
    @Metadata(d1 = {"\u0000\n\n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\u0010\u0000\u001a\u00020\u0001*\u00020\u0002H\n"}, d2 = {"<anonymous>", "", "Lkotlinx/coroutines/CoroutineScope;"}, k = 3, mv = {2, 2, 0}, xi = 48)
    @DebugMetadata(c = "com.miui.pad.feature.notes.calc.CalcEditFragment$saveExternalOnly$1", f = "CalcEditFragment.kt", i = {}, l = {}, m = "invokeSuspend", n = {}, s = {})
    static final class C03371 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
        int label;

        C03371(Continuation<? super C03371> continuation) {
            super(2, continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
            return CalcEditFragment.this.new C03371(continuation);
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(CoroutineScope coroutineScope, Continuation<? super Unit> continuation) {
            return ((C03371) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            IntrinsicsKt.getCOROUTINE_SUSPENDED();
            if (this.label == 0) {
                ResultKt.throwOnFailure(obj);
                MiuiHandWritingView miuiHandWritingView = CalcEditFragment.this.multiHandWriteView;
                if (miuiHandWritingView != null) {
                    final CalcEditFragment calcEditFragment = CalcEditFragment.this;
                    Boxing.boxBoolean(miuiHandWritingView.save(new CommonPageSaveListener() { // from class: com.miui.pad.feature.notes.calc.CalcEditFragment$saveExternalOnly$1$$ExternalSyntheticLambda0
                        @Override // com.miui.handwriting.common.listener.CommonPageSaveListener
                        public final void onDataSaved(boolean z, String str, boolean z2) {
                            CalcEditFragment.C03371.invokeSuspend$lambda$0(calcEditFragment, z, str, z2);
                        }
                    }));
                }
                return Unit.INSTANCE;
            }
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static final void invokeSuspend$lambda$0(CalcEditFragment calcEditFragment, boolean z, String str, boolean z2) {
            Log.d(calcEditFragment.getTAG(), "saveExternalOnly: isSuccess:" + z + " path:" + str);
        }
    }

    public final void saveExternalOnly() {
        BuildersKt__Builders_commonKt.launch$default(this, Dispatchers.getIO(), null, new C03371(null), 2, null);
    }

    /* JADX INFO: renamed from: com.miui.pad.feature.notes.calc.CalcEditFragment$saveData$1, reason: invalid class name and case insensitive filesystem */
    /* JADX INFO: compiled from: CalcEditFragment.kt */
    @Metadata(d1 = {"\u0000\n\n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\u0010\u0000\u001a\u00020\u0001*\u00020\u0002H\n"}, d2 = {"<anonymous>", "", "Lkotlinx/coroutines/CoroutineScope;"}, k = 3, mv = {2, 2, 0}, xi = 48)
    @DebugMetadata(c = "com.miui.pad.feature.notes.calc.CalcEditFragment$saveData$1", f = "CalcEditFragment.kt", i = {}, l = {}, m = "invokeSuspend", n = {}, s = {})
    static final class C03361 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
        int label;

        C03361(Continuation<? super C03361> continuation) {
            super(2, continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
            return CalcEditFragment.this.new C03361(continuation);
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(CoroutineScope coroutineScope, Continuation<? super Unit> continuation) {
            return ((C03361) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            IntrinsicsKt.getCOROUTINE_SUSPENDED();
            if (this.label == 0) {
                ResultKt.throwOnFailure(obj);
                MiuiHandWritingView miuiHandWritingView = CalcEditFragment.this.multiHandWriteView;
                if (miuiHandWritingView != null) {
                    final CalcEditFragment calcEditFragment = CalcEditFragment.this;
                    Boxing.boxBoolean(miuiHandWritingView.save(new CommonPageSaveListener() { // from class: com.miui.pad.feature.notes.calc.CalcEditFragment$saveData$1$$ExternalSyntheticLambda0
                        @Override // com.miui.handwriting.common.listener.CommonPageSaveListener
                        public final void onDataSaved(boolean z, String str, boolean z2) {
                            CalcEditFragment.C03361.invokeSuspend$lambda$0(calcEditFragment, z, str, z2);
                        }
                    }));
                }
                return Unit.INSTANCE;
            }
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static final void invokeSuspend$lambda$0(CalcEditFragment calcEditFragment, boolean z, String str, boolean z2) {
            Log.d(calcEditFragment.getTAG(), "saveData: isSuccess:" + z);
            if (z && calcEditFragment.saveToDB(true)) {
                calcEditFragment.finishActivity();
            }
        }
    }

    private final void saveData() {
        BuildersKt__Builders_commonKt.launch$default(this, Dispatchers.getIO(), null, new C03361(null), 2, null);
    }

    /* JADX INFO: renamed from: com.miui.pad.feature.notes.calc.CalcEditFragment$finishActivity$1, reason: invalid class name and case insensitive filesystem */
    /* JADX INFO: compiled from: CalcEditFragment.kt */
    @Metadata(d1 = {"\u0000\n\n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\u0010\u0000\u001a\u00020\u0001*\u00020\u0002H\n"}, d2 = {"<anonymous>", "", "Lkotlinx/coroutines/CoroutineScope;"}, k = 3, mv = {2, 2, 0}, xi = 48)
    @DebugMetadata(c = "com.miui.pad.feature.notes.calc.CalcEditFragment$finishActivity$1", f = "CalcEditFragment.kt", i = {}, l = {}, m = "invokeSuspend", n = {}, s = {})
    static final class C03281 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
        int label;

        C03281(Continuation<? super C03281> continuation) {
            super(2, continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
            return CalcEditFragment.this.new C03281(continuation);
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(CoroutineScope coroutineScope, Continuation<? super Unit> continuation) {
            return ((C03281) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            IntrinsicsKt.getCOROUTINE_SUSPENDED();
            if (this.label != 0) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            ResultKt.throwOnFailure(obj);
            Log.d(CalcEditFragment.this.getTAG(), "finishActivity: ");
            try {
                if (CalcEditFragment.this.isAdded() && CalcEditFragment.this.getActivity() != null && !CalcEditFragment.this.requireActivity().isFinishing()) {
                    CalcEditFragment.this.requireActivity().finish();
                }
            } catch (Exception e) {
                Log.e(CalcEditFragment.this.getTAG(), "Error finishing activity", e);
            }
            return Unit.INSTANCE;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void finishActivity() {
        BuildersKt__Builders_commonKt.launch$default(this, null, null, new C03281(null), 3, null);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final synchronized boolean saveToDB(boolean updateThumbnail) {
        MultiHandWriteEntity multiHandWriteEntity = this.handWriteEntity;
        if (multiHandWriteEntity != null && this.multiHandWriteView != null) {
            Intrinsics.checkNotNull(multiHandWriteEntity);
            MultiHandWriteEntity multiHandWriteEntityLoadNote = MultiHandWriteDataUtils.loadNote(multiHandWriteEntity.id);
            if (multiHandWriteEntityLoadNote == null) {
                MultiHandWriteEntity multiHandWriteEntity2 = this.handWriteEntity;
                Intrinsics.checkNotNull(multiHandWriteEntity2);
                long j = multiHandWriteEntity2.folderId;
                MultiHandWriteEntity multiHandWriteEntity3 = this.handWriteEntity;
                Intrinsics.checkNotNull(multiHandWriteEntity3);
                multiHandWriteEntityLoadNote = MultiHandWriteDataUtils.createNewNote(j, multiHandWriteEntity3.entName);
            }
            this.handWriteEntity = multiHandWriteEntityLoadNote;
            HandWriteViewManager handWriteViewManager = HandWriteViewManager.INSTANCE;
            MultiHandWriteEntity multiHandWriteEntity4 = this.handWriteEntity;
            Intrinsics.checkNotNull(multiHandWriteEntity4);
            MiuiHandWritingView miuiHandWritingView = this.multiHandWriteView;
            Intrinsics.checkNotNull(miuiHandWritingView);
            return handWriteViewManager.saveToDB(updateThumbnail, multiHandWriteEntity4, miuiHandWritingView);
        }
        return false;
    }

    /* JADX INFO: renamed from: com.miui.pad.feature.notes.calc.CalcEditFragment$showLoadingDialog$1, reason: invalid class name and case insensitive filesystem */
    /* JADX INFO: compiled from: CalcEditFragment.kt */
    @Metadata(d1 = {"\u0000\n\n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\u0010\u0000\u001a\u00020\u0001*\u00020\u0002H\n"}, d2 = {"<anonymous>", "", "Lkotlinx/coroutines/CoroutineScope;"}, k = 3, mv = {2, 2, 0}, xi = 48)
    @DebugMetadata(c = "com.miui.pad.feature.notes.calc.CalcEditFragment$showLoadingDialog$1", f = "CalcEditFragment.kt", i = {}, l = {}, m = "invokeSuspend", n = {}, s = {})
    static final class C03401 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
        int label;

        C03401(Continuation<? super C03401> continuation) {
            super(2, continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
            return CalcEditFragment.this.new C03401(continuation);
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(CoroutineScope coroutineScope, Continuation<? super Unit> continuation) {
            return ((C03401) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            ProgressDialog progressDialog;
            IntrinsicsKt.getCOROUTINE_SUSPENDED();
            if (this.label != 0) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            ResultKt.throwOnFailure(obj);
            FragmentActivity activity = CalcEditFragment.this.getActivity();
            if (activity == null) {
                return Unit.INSTANCE;
            }
            if (CalcEditFragment.this.loadingDialog == null && CalcEditFragment.this.isAdded()) {
                CalcEditFragment calcEditFragment = CalcEditFragment.this;
                ProgressDialog progressDialog2 = new ProgressDialog(activity);
                progressDialog2.setMessage(activity.getString(R.string.save_note_dialog));
                progressDialog2.setIndeterminate(true);
                progressDialog2.setCanceledOnTouchOutside(false);
                progressDialog2.setCancelable(false);
                calcEditFragment.loadingDialog = progressDialog2;
            }
            ProgressDialog progressDialog3 = CalcEditFragment.this.loadingDialog;
            if (progressDialog3 != null && !progressDialog3.isShowing() && (progressDialog = CalcEditFragment.this.loadingDialog) != null) {
                progressDialog.show();
            }
            return Unit.INSTANCE;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void showLoadingDialog() {
        BuildersKt__Builders_commonKt.launch$default(this, null, null, new C03401(null), 3, null);
    }

    /* JADX INFO: renamed from: com.miui.pad.feature.notes.calc.CalcEditFragment$dismissLoadingDialog$1, reason: invalid class name */
    /* JADX INFO: compiled from: CalcEditFragment.kt */
    @Metadata(d1 = {"\u0000\n\n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\u0010\u0000\u001a\u00020\u0001*\u00020\u0002H\n"}, d2 = {"<anonymous>", "", "Lkotlinx/coroutines/CoroutineScope;"}, k = 3, mv = {2, 2, 0}, xi = 48)
    @DebugMetadata(c = "com.miui.pad.feature.notes.calc.CalcEditFragment$dismissLoadingDialog$1", f = "CalcEditFragment.kt", i = {}, l = {}, m = "invokeSuspend", n = {}, s = {})
    static final class AnonymousClass1 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
        int label;

        AnonymousClass1(Continuation<? super AnonymousClass1> continuation) {
            super(2, continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
            return CalcEditFragment.this.new AnonymousClass1(continuation);
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(CoroutineScope coroutineScope, Continuation<? super Unit> continuation) {
            return ((AnonymousClass1) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            ProgressDialog progressDialog;
            IntrinsicsKt.getCOROUTINE_SUSPENDED();
            if (this.label != 0) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            ResultKt.throwOnFailure(obj);
            Log.d(CalcEditFragment.this.getTAG(), "dismissLoadingDialog: ");
            if (CalcEditFragment.this.loadingDialog != null && (progressDialog = CalcEditFragment.this.loadingDialog) != null && progressDialog.isShowing()) {
                ProgressDialog progressDialog2 = CalcEditFragment.this.loadingDialog;
                if (progressDialog2 != null) {
                    progressDialog2.dismiss();
                }
                CalcEditFragment.this.loadingDialog = null;
            }
            return Unit.INSTANCE;
        }
    }

    private final void dismissLoadingDialog() {
        BuildersKt__Builders_commonKt.launch$default(this, null, null, new AnonymousClass1(null), 3, null);
    }

    private final void registerCreationTaskReceiver() {
        if (this.mOperationReceiver == null) {
            this.mOperationReceiver = new BroadcastReceiver() { // from class: com.miui.pad.feature.notes.calc.CalcEditFragment.registerCreationTaskReceiver.1
                @Override // android.content.BroadcastReceiver
                public void onReceive(Context context, Intent intent) {
                    Intrinsics.checkNotNullParameter(intent, "intent");
                    Bundle extras = intent.getExtras();
                    Log.d(CalcEditFragment.this.getTAG(), "LocalBroadcast onReceive: " + extras);
                    if (extras != null && TextUtils.equals(Constants.OPERATION_INTENT, intent.getAction()) && TextUtils.equals(extras.getString("operationType"), "distinguish")) {
                        extras.putString("dirPath", CalcEditFragment.this.externalDirPath);
                        CreationServiceManager.INSTANCE.dispatchTask(7, extras, CalcEditFragment.this.mTaskCallback);
                    }
                }
            };
        }
        IntentFilter intentFilter = new IntentFilter(Constants.OPERATION_INTENT);
        LocalBroadcastManager localBroadcastManager = LocalBroadcastManager.getInstance(BaseNoteApp.INSTANCE.getInstance());
        BroadcastReceiver broadcastReceiver = this.mOperationReceiver;
        Intrinsics.checkNotNull(broadcastReceiver);
        localBroadcastManager.registerReceiver(broadcastReceiver, intentFilter);
    }

    /* JADX INFO: compiled from: CalcEditFragment.kt */
    @Metadata(d1 = {"\u0000J\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0002\b\u0006\n\u0002\u0010!\n\u0002\b\u000e\n\u0002\u0010\t\n\u0000\n\u0002\u0018\u0002\n\u0000\b\u0002\u0018\u00002\u00020\u0001B\u000f\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0004\b\u0004\u0010\u0005J\u001a\u0010\f\u001a\u00020\r2\b\u0010\u000e\u001a\u0004\u0018\u00010\u000f2\u0006\u0010\u0010\u001a\u00020\u0011H\u0016J\u0012\u0010\u0012\u001a\u00020\r2\b\u0010\u000e\u001a\u0004\u0018\u00010\u000fH\u0016J\u001a\u0010\u0013\u001a\u00020\r2\u0006\u0010\u0014\u001a\u00020\u00152\b\u0010\u0016\u001a\u0004\u0018\u00010\u000fH\u0016J@\u0010\u0017\u001a\u00020\r2\b\u0010\u0018\u001a\u0004\u0018\u00010\u000f2\u0006\u0010\u0019\u001a\u00020\u00112\b\u0010\u001a\u001a\u0004\u0018\u00010\u000f2\u0010\u0010\u001b\u001a\f\u0012\u0006\u0012\u0004\u0018\u00010\u000f\u0018\u00010\u001c2\b\u0010\u001d\u001a\u0004\u0018\u00010\u000fH\u0016J\u0010\u0010\u001e\u001a\u00020\r2\u0006\u0010\u001f\u001a\u00020\u0011H\u0016J\"\u0010 \u001a\u00020\r2\u0006\u0010!\u001a\u00020\u00152\b\u0010\"\u001a\u0004\u0018\u00010\u000f2\u0006\u0010\u0019\u001a\u00020\u0011H\u0016J\"\u0010#\u001a\u00020\r2\u0006\u0010!\u001a\u00020\u00152\b\u0010\"\u001a\u0004\u0018\u00010\u000f2\u0006\u0010\u0019\u001a\u00020\u0011H\u0016J\u0010\u0010$\u001a\u00020\r2\u0006\u0010%\u001a\u00020\u000fH\u0016J\b\u0010&\u001a\u00020\rH\u0016J\b\u0010'\u001a\u00020\rH\u0016J\b\u0010(\u001a\u00020\u0015H\u0016J\u001a\u0010)\u001a\u00020\r2\u0006\u0010*\u001a\u00020+2\b\u0010,\u001a\u0004\u0018\u00010-H\u0016R \u0010\u0006\u001a\b\u0012\u0004\u0012\u00020\u00030\u0007X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\b\u0010\t\"\u0004\b\n\u0010\u000b¨\u0006."}, d2 = {"Lcom/miui/pad/feature/notes/calc/CalcEditFragment$TaskServiceCallBack;", "Lcom/miui/creation/taskservice/ITaskServiceCallback$Stub;", "calcEditFragment", "Lcom/miui/pad/feature/notes/calc/CalcEditFragment;", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "(Lcom/miui/pad/feature/notes/calc/CalcEditFragment;)V", "mEditFragmentWeakReference", "Ljava/lang/ref/WeakReference;", "getMEditFragmentWeakReference", "()Ljava/lang/ref/WeakReference;", "setMEditFragmentWeakReference", "(Ljava/lang/ref/WeakReference;)V", "recognizeAsText", "", "recognizeFilePath", "", "textColor", "", "copyAsText", "classificationForAi", "isWriting", "", "recognizeLabel", "showExportPreview", "filePath", "exportType", "title", "filePathList", "", "dirPath", "exportOnProgress", "percentDone", "exportCompleted", "isSuccess", "completedPath", "exportToAI", "exportToPcCompleted", "imagePath", "exportPreviewFail", "backToHome", "exportThumbnailFail", "onGenComplete", "id", "", "values", "Landroid/content/ContentValues;", "app_PhoneGlobalRelease"}, k = 1, mv = {2, 2, 0}, xi = 48)
    private static final class TaskServiceCallBack extends ITaskServiceCallback.Stub {
        private WeakReference<CalcEditFragment> mEditFragmentWeakReference;

        @Override // com.miui.creation.taskservice.ITaskServiceCallback
        public void backToHome() throws RemoteException {
        }

        @Override // com.miui.creation.taskservice.ITaskServiceCallback
        public void classificationForAi(boolean isWriting, String recognizeLabel) throws RemoteException {
        }

        @Override // com.miui.creation.taskservice.ITaskServiceCallback
        public void exportCompleted(boolean isSuccess, String completedPath, int exportType) throws RemoteException {
        }

        @Override // com.miui.creation.taskservice.ITaskServiceCallback
        public void exportOnProgress(int percentDone) throws RemoteException {
        }

        @Override // com.miui.creation.taskservice.ITaskServiceCallback
        public void exportPreviewFail() throws RemoteException {
        }

        @Override // com.miui.creation.taskservice.ITaskServiceCallback
        public boolean exportThumbnailFail() throws RemoteException {
            return false;
        }

        @Override // com.miui.creation.taskservice.ITaskServiceCallback
        public void exportToAI(boolean isSuccess, String completedPath, int exportType) throws RemoteException {
        }

        @Override // com.miui.creation.taskservice.ITaskServiceCallback
        public void exportToPcCompleted(String imagePath) throws RemoteException {
            Intrinsics.checkNotNullParameter(imagePath, "imagePath");
        }

        @Override // com.miui.creation.taskservice.ITaskServiceCallback
        public void onGenComplete(long id, ContentValues values) throws RemoteException {
        }

        @Override // com.miui.creation.taskservice.ITaskServiceCallback
        public void recognizeAsText(String recognizeFilePath, int textColor) throws RemoteException {
        }

        @Override // com.miui.creation.taskservice.ITaskServiceCallback
        public void showExportPreview(String filePath, int exportType, String title, List<String> filePathList, String dirPath) throws RemoteException {
        }

        public TaskServiceCallBack(CalcEditFragment calcEditFragment) {
            Intrinsics.checkNotNullParameter(calcEditFragment, "calcEditFragment");
            this.mEditFragmentWeakReference = new WeakReference<>(calcEditFragment);
        }

        public final WeakReference<CalcEditFragment> getMEditFragmentWeakReference() {
            return this.mEditFragmentWeakReference;
        }

        public final void setMEditFragmentWeakReference(WeakReference<CalcEditFragment> weakReference) {
            Intrinsics.checkNotNullParameter(weakReference, "<set-?>");
            this.mEditFragmentWeakReference = weakReference;
        }

        @Override // com.miui.creation.taskservice.ITaskServiceCallback
        public void copyAsText(String recognizeFilePath) throws RemoteException {
            MiuiHandWritingView miuiHandWritingView;
            PartiBean selectRecognizeDataFromFile = RecognizeDataHelper.readSelectRecognizeDataFromFile(recognizeFilePath);
            CalcEditFragment calcEditFragment = this.mEditFragmentWeakReference.get();
            if (calcEditFragment == null || (miuiHandWritingView = calcEditFragment.multiHandWriteView) == null) {
                return;
            }
            miuiHandWritingView.copyAsText(selectRecognizeDataFromFile);
        }
    }
}
