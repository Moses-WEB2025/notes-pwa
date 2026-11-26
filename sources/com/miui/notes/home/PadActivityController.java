package com.miui.notes.home;

import android.R;
import android.content.ClipData;
import android.content.ContentUris;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.database.Cursor;
import android.net.Uri;
import android.os.Binder;
import android.os.Bundle;
import android.os.Handler;
import android.os.PowerManager;
import android.text.TextUtils;
import android.view.DragEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowInsets;
import android.view.accessibility.AccessibilityNodeInfo;
import androidx.activity.result.ActivityResultLauncher;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.loader.app.LoaderManager;
import androidx.recyclerview.widget.RecyclerView;
import com.miui.common.app.BaseNoteApp;
import com.miui.common.base.NoteLoadDataEntity;
import com.miui.common.tool.AudioUtils;
import com.miui.common.tool.KeyboardHelper;
import com.miui.common.tool.Logger;
import com.miui.common.tool.RomUtils;
import com.miui.common.tool.UIUtils;
import com.miui.common.tool.Utils;
import com.miui.common.tool.event.LiveDataEventBus;
import com.miui.common.tool.softinputhelper.KeyboardPaddingProcessor;
import com.miui.notes.NoteApp;
import com.miui.notes.basefeature.noteedit.BaseHybridNoteEditFragment;
import com.miui.notes.cache.NoteCache;
import com.miui.notes.component.DeleteFolderDialog;
import com.miui.notes.component.RenameFolderDialog;
import com.miui.notes.constant.NoteConfig;
import com.miui.notes.constant.NoteIntent;
import com.miui.notes.detail.PadDetailFragmentClassManager;
import com.miui.notes.feature.folder.CreateFolderDialog;
import com.miui.notes.feature.folder.FolderDialogActivity;
import com.miui.notes.home.viewmodel.FolderOperationViewModel;
import com.miui.notes.home.viewmodel.NavigationFolderMenu;
import com.miui.notes.home.viewmodel.NavigationMenuData;
import com.miui.notes.home.viewmodel.SelectNoteData;
import com.miui.notes.mishow.MiShowPreferenceHelper;
import com.miui.notes.mishow.MiShowReceiver;
import com.miui.notes.model.FolderModel;
import com.miui.notes.model.WorkingNote;
import com.miui.notes.page.viewmodel.NoteOperationViewModel;
import com.miui.notes.page.viewmodel.Operation;
import com.miui.notes.page.viewmodel.Result;
import com.miui.notes.provider.NoteProviderAccessUtils;
import com.miui.notes.provider.Notes;
import com.miui.notes.store.ItemStore;
import com.miui.notes.tool.HandoffHelper;
import com.miui.notes.tool.MiuiPlusHelper;
import com.miui.notes.tool.PreferenceUtils;
import com.miui.notes.ui.NotesListActivity;
import com.miui.pad.feature.notes.handwrite.PadHandWriteFragment;
import com.miui.pad.feature.notes.multihandwrite.CreationEditActivity;
import com.miui.pad.feature.settings.PadAppPreferenceActivity;
import com.miui.todo.constant.TodoIntent;
import com.miui.todo.feature.settings.QuickNotePreferenceActivity;
import com.xiaomi.aiinput.AIInputResultListener;
import com.xiaomi.aiinput.AIInputTextManager;
import com.xiaomi.dist.handoff.sdk.HandoffSession;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.function.Supplier;
import miuix.navigator.Navigator;
import miuix.navigator.adapter.CategoryAdapter;
import miuix.navigator.adapter.FooterAdapter;
import miuix.navigator.adapter.HintWidgetProvider;
import miuix.navigator.adapter.ListCategoryAdapter;
import miuix.navigator.adapter.MenuCategoryAdapter;
import miuix.navigator.draganddrop.DragStartFeedback;
import miuix.navigator.draganddrop.NavigatorDragListener;
import miuix.navigator.navigatorinfo.ActivityNavInfo;
import miuix.navigator.navigatorinfo.DetailFragmentNavInfo;
import miuix.navigator.navigatorinfo.UpdateFragmentNavInfo;
import org.apache.commons.lang3.StringUtils;

/* JADX INFO: loaded from: classes4.dex */
public class PadActivityController extends BaseNoteListActivityController {
    private static final int FOOTER_ID = 103;
    private static final int NORMAL_FOLDER_CATEGORY_ID = 101;
    public static final String NOTE_LIST_DRAG_LABEL = "note_list_drag";
    private static final int OTHER_FOLDER_CATEGORY_ID = 102;
    public static final int REQUEST_CODE_QUERY_FOLDER_LIST_WITH_MENU = 1;
    private static final int STICK_FOLDER_CATEGORY_ID = 100;
    private static final String TAG = "PadActivityController";
    private final int EDIT_ACTION_DELETE;
    private final int EDIT_ACTION_EDIT;
    private final int EDIT_ACTION_STICK;
    private FolderOperationViewModel folderOperationViewModel;
    private boolean isNeedRegisteredKeyboard;
    private final KeyboardPaddingProcessor keyboardPaddingProcessor;
    private AIInputTextManager.AITextCallback mAIInputCallback;
    public Handler mHandler;
    private HandoffSession mHandoffSession;
    private boolean mIsSupportAIInput;
    protected KeyboardHelper mNoteEditKeyboardHelper;
    private final ViewGroup mRootView;
    private View mSecondaryContentDecorWrapper;
    private boolean needRegisteredKeyboard;
    private Navigator.Category normalCategory;
    private ListCategoryAdapter normalCategoryAdapter;
    private List<ListCategoryAdapter.Item> normalCategoryList;
    private NoteOperationViewModel noteOperationViewModel;
    private Navigator.Category otherCategory;
    private MenuCategoryAdapter otherCategoryAdapter;
    private List<MenuCategoryAdapter.Item> otherCategoryList;
    private Navigator.Category stickCategory;
    private ListCategoryAdapter stickCategoryAdapter;
    private List<ListCategoryAdapter.Item> stickCategoryList;
    public final View toolLayout;
    private boolean wakeUp;

    static /* synthetic */ void lambda$rename$13(DialogInterface dialogInterface) {
    }

    public PadActivityController(NotesListActivity notesListActivity) {
        super(notesListActivity);
        this.mNoteEditKeyboardHelper = null;
        this.EDIT_ACTION_STICK = 1;
        this.EDIT_ACTION_DELETE = 2;
        this.EDIT_ACTION_EDIT = 3;
        this.mIsSupportAIInput = false;
        this.isNeedRegisteredKeyboard = false;
        this.needRegisteredKeyboard = false;
        this.keyboardPaddingProcessor = new KeyboardPaddingProcessor() { // from class: com.miui.notes.home.PadActivityController.6
            @Override // com.miui.common.tool.softinputhelper.KeyboardPaddingProcessor, com.miui.common.tool.softinputhelper.KeyboardHeightListener
            public void onKeyboardHeightChangedEnd(View view, WindowInsets windowInsets, int i) {
                super.onKeyboardHeightChangedEnd(view, windowInsets, i);
            }

            @Override // com.miui.common.tool.softinputhelper.KeyboardPaddingProcessor
            public void updateViewPaddingBottom(View view, WindowInsets windowInsets, int i) {
                Logger.INSTANCE.v(PadActivityController.TAG, "updateViewPaddingBottom paddingBottom = " + i);
                handleKeyboardStatus(i);
                WindowInsetsCompat rootWindowInsets = ViewCompat.getRootWindowInsets(view);
                if (rootWindowInsets != null) {
                    Insets insets = rootWindowInsets.getInsets(WindowInsetsCompat.Type.systemBars());
                    boolean zIsVisible = rootWindowInsets.isVisible(WindowInsetsCompat.Type.ime());
                    boolean showKeyboard = KeyboardHelper.INSTANCE.getShowKeyboard();
                    if (BaseNoteApp.INSTANCE.isMultiWindowMode()) {
                        if (!zIsVisible && i == 0) {
                            super.updateViewPaddingBottom(view, windowInsets, insets.bottom);
                            return;
                        } else {
                            super.updateViewPaddingBottom(view, windowInsets, i);
                            return;
                        }
                    }
                    if (!showKeyboard && i == 0) {
                        super.updateViewPaddingBottom(view, windowInsets, insets.bottom);
                        return;
                    } else {
                        super.updateViewPaddingBottom(view, windowInsets, Math.max(i, insets.bottom));
                        return;
                    }
                }
                super.updateViewPaddingBottom(view, windowInsets, i);
            }

            private void handleKeyboardStatus(int i) {
                if (PadActivityController.this.getActivity().isInMultiWindowMode()) {
                    if (UIUtils.isAboveAndroidR()) {
                        boolean showKeyboard = KeyboardHelper.INSTANCE.getShowKeyboard();
                        Logger.INSTANCE.d(PadActivityController.TAG, "isShowKeyBoard = " + PadActivityController.this.getHomeViewModel().isShowKeyBoard() + " isImeShow = " + showKeyboard);
                        if (PadActivityController.this.getHomeViewModel().isShowKeyBoard() != showKeyboard) {
                            Logger.INSTANCE.i(PadActivityController.TAG, "setKeyBoardShowStatus ,isImeShow:" + showKeyboard);
                            PadActivityController.this.getHomeViewModel().setKeyBoardShowStatus(showKeyboard);
                        }
                    }
                } else if (UIUtils.isAboveAndroidR()) {
                    boolean showKeyboard2 = KeyboardHelper.INSTANCE.getShowKeyboard();
                    if (PadActivityController.this.getHomeViewModel().isShowKeyBoard() != showKeyboard2) {
                        Logger.INSTANCE.i(PadActivityController.TAG, "setKeyBoardShowStatus imeshow" + showKeyboard2);
                        PadActivityController.this.getHomeViewModel().setKeyBoardShowStatus(showKeyboard2);
                    }
                } else if (i <= 0) {
                    if (PadActivityController.this.getHomeViewModel().isShowKeyBoard()) {
                        Logger.INSTANCE.i(PadActivityController.TAG, "setKeyBoardShowStatus false,0");
                        PadActivityController.this.getHomeViewModel().setKeyBoardShowStatus(false);
                    }
                } else if (!PadActivityController.this.getHomeViewModel().isShowKeyBoard()) {
                    Logger.INSTANCE.i(PadActivityController.TAG, "setKeyBoardShowStatus true");
                    PadActivityController.this.getHomeViewModel().setKeyBoardShowStatus(true);
                }
                View viewFindViewById = PadActivityController.this.mSecondaryContentDecorWrapper.findViewById(R.id.content);
                if (viewFindViewById != null) {
                    viewFindViewById.setPadding(viewFindViewById.getPaddingLeft(), viewFindViewById.getPaddingTop(), viewFindViewById.getPaddingRight(), i);
                }
                PadActivityController.this.getHomeViewModel().setKeyBoardForPadding(i);
            }
        };
        NotesListActivity notesListActivity2 = (NotesListActivity) new WeakReference(notesListActivity).get();
        ViewGroup viewGroup = (ViewGroup) ((ViewGroup) notesListActivity2.findViewById(R.id.content)).getParent();
        this.mRootView = viewGroup;
        LayoutInflater.from(notesListActivity2).inflate(com.miui.notes.R.layout.pad_activity_extra_layout, viewGroup);
        this.toolLayout = notesListActivity2.findViewById(com.miui.notes.R.id.pad_activity_tool_layout_root);
        this.mHandler = new Handler(notesListActivity2.getMainLooper());
    }

    @Override // com.miui.notes.basefeature.IActivityController
    public void onCreate() {
        Logger.INSTANCE.d(TAG, "onCreate");
        getHomeViewModel().setFolderId(PreferenceUtils.getLongPreference(getActivity(), PreferenceUtils.PREF_LAST_FOLDER_ID, NoteConfig.getDefaultFolderId()));
        initCategory();
        getHomeViewModel().getNaviMenuData().observe(getActivity(), new Observer() { // from class: com.miui.notes.home.PadActivityController$$ExternalSyntheticLambda3
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                this.f$0.createNaviMenu((NavigationMenuData) obj);
            }
        });
        getHomeViewModel().loadNavigationMenuData(LoaderManager.getInstance(getActivity()), 1);
        this.noteOperationViewModel = (NoteOperationViewModel) new ViewModelProvider(getActivity()).get(NoteOperationViewModel.class);
        FolderOperationViewModel folderOperationViewModel = (FolderOperationViewModel) new ViewModelProvider(getActivity()).get(FolderOperationViewModel.class);
        this.folderOperationViewModel = folderOperationViewModel;
        folderOperationViewModel.getNoteOperation().observe(getActivity(), new Observer() { // from class: com.miui.notes.home.PadActivityController$$ExternalSyntheticLambda4
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                this.f$0.lambda$onCreate$1((Result) obj);
            }
        });
        Logger.INSTANCE.d(TAG, "onCreate/start observe page selected state");
        getHomeViewModel().getPageSelectedLiveData().observe(getActivity(), new Observer() { // from class: com.miui.notes.home.PadActivityController$$ExternalSyntheticLambda5
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                this.f$0.lambda$onCreate$2((Integer) obj);
            }
        });
        getHomeViewModel().getToHomeFromOutMode().observe(getActivity(), new Observer() { // from class: com.miui.notes.home.PadActivityController$$ExternalSyntheticLambda6
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                this.f$0.lambda$onCreate$4((Boolean) obj);
            }
        });
        this.mHandoffSession = HandoffHelper.publishDeepLink(getActivity(), new NoteSupplier(this), 5);
        initAIInput();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onCreate$1(Result result) {
        if (!(result instanceof Result.Loading) && (result instanceof Result.Complete)) {
            if (((Result.Complete) result).getOperation() == Operation.DELETE) {
                AudioUtils.playDeleteRingtone();
                getHomeViewModel().refreshFolderName(LoaderManager.getInstance(getActivity()));
            } else {
                Operation operation = Operation.STICK;
            }
            this.mHandler.postDelayed(new Runnable() { // from class: com.miui.notes.home.PadActivityController$$ExternalSyntheticLambda14
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.lambda$onCreate$0();
                }
            }, 150L);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onCreate$0() {
        ListCategoryAdapter listCategoryAdapter = this.stickCategoryAdapter;
        if (listCategoryAdapter == null || this.normalCategoryAdapter == null) {
            return;
        }
        listCategoryAdapter.finishEdit(false);
        this.normalCategoryAdapter.finishEdit(false);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onCreate$2(Integer num) {
        logDebug("note PageSelected onChanged:" + num);
        if (num.intValue() == 0) {
            registerNoteEditKeyboardDetector();
            SelectNoteData selectedNoteWrapper = getHomeViewModel().getSelectedNoteWrapper();
            boolean z = !getHomeViewModel().getPageIndexIsChange() || getActivity().getNavigator().getNavigationMode() == Navigator.Mode.C;
            Logger.INSTANCE.d(TAG, "SelectNoteDataEntity onChanged page :" + num + ",selectNoteData :" + selectedNoteWrapper + ",notNeedRefreshDetail :" + z);
            if (z) {
                return;
            }
            if (selectedNoteWrapper != null) {
                getHomeViewModel().setSelectNote(selectedNoteWrapper.getEntity(), selectedNoteWrapper.getOpenDetail(), selectedNoteWrapper.getUseDetailFragmentNav());
                return;
            } else {
                getHomeViewModel().setSelectNote(null, getActivity().getNavigator().getNavigationMode() != Navigator.Mode.C, false);
                return;
            }
        }
        if (num.intValue() == 1) {
            unregisterNoteEditKeyboardDetector();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onCreate$4(Boolean bool) {
        if (bool.booleanValue()) {
            if (this.toolLayout == null) {
                getActivity().onBackPressed();
                return;
            }
            unregisterNoteEditKeyboardDetector();
            ViewGroup.MarginLayoutParams marginLayoutParams = (ViewGroup.MarginLayoutParams) this.toolLayout.getLayoutParams();
            marginLayoutParams.bottomMargin = 0;
            this.toolLayout.setLayoutParams(marginLayoutParams);
            this.mHandler.post(new Runnable() { // from class: com.miui.notes.home.PadActivityController$$ExternalSyntheticLambda11
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.lambda$onCreate$3();
                }
            });
            this.mHandler.postDelayed(new Runnable() { // from class: com.miui.notes.home.PadActivityController$$ExternalSyntheticLambda12
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.registerNoteEditKeyboardDetector();
                }
            }, 100L);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onCreate$3() {
        getActivity().onBackPressed();
        Logger.INSTANCE.i(TAG, "setKeyBoardShowStatus false ,backpress");
        getHomeViewModel().setKeyBoardShowStatus(false);
    }

    public static class NoteSupplier implements Supplier {
        private final WeakReference<PadActivityController> refs;

        public NoteSupplier(PadActivityController padActivityController) {
            this.refs = new WeakReference<>(padActivityController);
        }

        @Override // java.util.function.Supplier
        public Object get() {
            if (this.refs.get() == null) {
                return null;
            }
            Uri deepLink = this.refs.get().getDeepLink();
            Logger.INSTANCE.d("Notes:HandoffHelper", "get note deeplink ");
            return deepLink == null ? Uri.parse("minote://data/notes/") : deepLink;
        }
    }

    /* JADX WARN: Code duplicated, block: B:17:0x008e  */
    @Override // com.miui.notes.basefeature.IActivityController
    public void onPostCreate(Bundle bundle) {
        boolean z;
        Logger.INSTANCE.d(TAG, "onPostCreate:");
        if (bundle != null && getHomeViewModel().getSelectNoteLiveData().getValue() != null && getActivity().getNavigator().getNavigationMode() == Navigator.Mode.C) {
            SelectNoteData value = getHomeViewModel().getSelectNoteLiveData().getValue();
            if (getHomeViewModel().getSelectNoteLiveData().getValue().getEntity() != null && (getHomeViewModel().getSelectNoteLiveData().getValue().getEntity().getNoteType() == 2 || getHomeViewModel().getSelectNoteLiveData().getValue().getEntity().getNoteType() == 3)) {
                z = getActivity().getNavigator().getContentVisibility() == 1;
            }
            value.setOpenDetail(z);
        }
        getHomeViewModel().getSelectNoteLiveData().observe(getActivity(), new Observer() { // from class: com.miui.notes.home.PadActivityController$$ExternalSyntheticLambda9
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                this.f$0.lambda$onPostCreate$5((SelectNoteData) obj);
            }
        });
        getHomeViewModel().getKeyboardChange().observe(getActivity(), new Observer() { // from class: com.miui.notes.home.PadActivityController$$ExternalSyntheticLambda10
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                this.f$0.lambda$onPostCreate$6((Boolean) obj);
            }
        });
        if (getActivity().getIntent() != null && bundle != null) {
            String action = getActivity().getIntent().getAction();
            Logger.INSTANCE.d(TAG, "onPostCreate action = " + action);
            if (NoteIntent.ACTION_SEARCH_NOTES.equals(action)) {
                Logger.INSTANCE.d(TAG, "onPostCreate change intent action");
                getActivity().getIntent().setAction("android.intent.action.MAIN");
            }
        }
        deliverIntent(getActivity().getIntent());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onPostCreate$5(SelectNoteData selectNoteData) {
        Logger.INSTANCE.d(TAG, "SelectNoteDataEntity onChanged entity:" + (selectNoteData.getEntity() == null ? "null" : Long.valueOf(selectNoteData.getEntity().getNoteId())));
        if (selectNoteData.getOpenDetail()) {
            Logger.INSTANCE.d(TAG, "SelectNoteDataEntity: openDetail");
            toDetailFragment(selectNoteData.getEntity(), selectNoteData.getUseDetailFragmentNav(), "onPostCreate,observe noteEntity livedata changed");
        } else {
            Logger.INSTANCE.d(TAG, "SelectNoteDataEntity: openDetail set true");
            selectNoteData.setOpenDetail(true);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onPostCreate$6(Boolean bool) {
        Logger.INSTANCE.d(TAG, "onKeyboardChange " + bool);
        if (bool.booleanValue()) {
            unregisterNoteEditKeyboardDetector();
            registerNoteEditKeyboardDetector();
            this.mNoteEditKeyboardHelper.update();
            this.isNeedRegisteredKeyboard = false;
            return;
        }
        this.isNeedRegisteredKeyboard = true;
        unregisterNoteEditKeyboardDetector();
    }

    private void toDetailFragment(NoteLoadDataEntity noteLoadDataEntity, boolean z, String str) {
        Logger.INSTANCE.d(TAG, "toDetailFragment,useDetailFragmentNav:" + z + ",invokeSource:" + str);
        if (getHomeViewModel().getPageSelected() != 0) {
            return;
        }
        if (getActivity().isInMultiWindowMode() && noteLoadDataEntity != null && noteLoadDataEntity.getNoteType() == 3 && getActivity().getNavigator().getNavigationMode() != Navigator.Mode.NLC && getActivity().getNavigator().getNavigationMode() != Navigator.Mode.LC && !RomUtils.isFoldDevice()) {
            CreationEditActivity.launchFromCreate(getActivity(), noteLoadDataEntity, false, false, (ActivityResultLauncher) Objects.requireNonNull(getActivity().getReceivePadMultiData()));
            return;
        }
        if (this.isNeedRegisteredKeyboard && noteLoadDataEntity != null && noteLoadDataEntity.getNoteType() != 3) {
            this.mHandler.postDelayed(new Runnable() { // from class: com.miui.notes.home.PadActivityController$$ExternalSyntheticLambda2
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.lambda$toDetailFragment$7();
                }
            }, 200L);
        }
        if (PreferenceUtils.getPreferenceFirstCreateHandWrite(getActivity().getApplicationContext()) && noteLoadDataEntity != null && noteLoadDataEntity.getNoteType() == 3) {
            PreferenceUtils.setPreferenceFromCreate(NoteApp.getInstance(), false);
        }
        navigateToDetailFragment(noteLoadDataEntity, z, prepareArgumentsBundle(noteLoadDataEntity));
        handleNoteTypeSpecificActions(noteLoadDataEntity);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$toDetailFragment$7() {
        registerNoteEditKeyboardDetector();
        this.mNoteEditKeyboardHelper.update();
        this.isNeedRegisteredKeyboard = false;
    }

    private Bundle prepareArgumentsBundle(NoteLoadDataEntity noteLoadDataEntity) {
        Bundle bundle = new Bundle();
        bundle.putSerializable("entity", noteLoadDataEntity);
        if (noteLoadDataEntity != null) {
            getActivity().setResult(noteLoadDataEntity.getNoteType() == 2 ? 10 : 11);
        }
        return bundle;
    }

    private void handleNoteTypeSpecificActions(NoteLoadDataEntity noteLoadDataEntity) {
        if (noteLoadDataEntity != null && noteLoadDataEntity.getNoteType() == 0 && this.needRegisteredKeyboard) {
            unregisterNoteEditKeyboardDetector();
            registerNoteEditKeyboardDetector();
            this.needRegisteredKeyboard = false;
        }
    }

    private void navigateToDetailFragment(NoteLoadDataEntity noteLoadDataEntity, boolean z, Bundle bundle) {
        DetailFragmentNavInfo detailFragmentNavInfo = new DetailFragmentNavInfo(-1, PadDetailFragmentClassManager.observeClass(noteLoadDataEntity, !z ? 1 : 0), bundle);
        Logger.INSTANCE.d(TAG, "toDetailFragment,navigate to detail fragment, useDetailFragmentNav: " + z + " fragmentNavInfo: " + detailFragmentNavInfo);
        if (noteLoadDataEntity != null && noteLoadDataEntity.getNoteType() == 3) {
            getActivity().getNavigator().navigate(detailFragmentNavInfo);
        } else {
            getActivity().getNavigator().navigate(detailFragmentNavInfo);
        }
    }

    @Override // com.miui.notes.home.BaseNoteListActivityController, com.miui.notes.basefeature.IActivityController
    public void onResume() {
        wakeUpScreen(getActivity().getIntent());
        UIUtils.checkOrientationInFoldDevice(getActivity());
    }

    @Override // com.miui.notes.basefeature.IActivityController
    public void onNewIntent(Intent intent) {
        Logger.INSTANCE.d(TAG, "onNewIntent:" + intent);
        deliverIntent(intent);
        this.wakeUp = false;
        if (this.mHandoffSession != null) {
            this.mHandoffSession.onNewIntent(intent);
        }
    }

    public void deliverIntent(final Intent intent) {
        Logger.INSTANCE.d(TAG, "deliverIntent " + intent);
        if (intent == null) {
            Logger.INSTANCE.i(TAG, "deliverIntent ,intent null!!");
            return;
        }
        String action = intent.getAction();
        if (TextUtils.isEmpty(action)) {
            Logger.INSTANCE.i(TAG, "deliverIntent ,action null!!");
            return;
        }
        Logger.INSTANCE.d(TAG, "deliverIntent action = " + action);
        getActivity().setIntent(intent);
        boolean z = NoteIntent.ACTION_PAD_INSERT_OR_EDIT.equals(action) || "android.intent.action.INSERT_OR_EDIT".equals(action);
        boolean zEquals = "android.intent.action.SEND".equals(action);
        boolean zEquals2 = "android.intent.action.VIEW".equals(action);
        boolean zEquals3 = NoteIntent.ACTION_VIEW.equals(action);
        boolean zEquals4 = NoteIntent.ACTION_SEARCH_NOTES.equals(action);
        boolean zEquals5 = TodoIntent.ACTION_SHORTCUT_INSERT_OR_EDIT.equals(action);
        boolean zEquals6 = TodoIntent.ACTION_INSERT_OR_EDIT.equals(action);
        boolean zEquals7 = TodoIntent.ACTION_VIEW.equals(action);
        boolean z2 = "android.intent.action.VIEW".equals(action) && intent.getData() != null && "minote".equals(intent.getData().getScheme());
        boolean z3 = !TextUtils.isEmpty(action) && action.equals(TodoIntent.ACTION_SEARCH_NOTES);
        if (intent.getData() != null && "mishare".equals(intent.getData().getScheme())) {
            if (getHomeViewModel().getPageSelected() != 0) {
                getHomeViewModel().setCurrentItem(0);
            }
            if (getHomeViewModel().isSearchMode()) {
                getHomeViewModel().setStopSearch(true);
            }
            getHomeViewModel().setIntent(intent);
            return;
        }
        if (z || zEquals) {
            if (getHomeViewModel().getPageSelected() != 0) {
                getHomeViewModel().setCurrentItem(0);
            }
            if (getHomeViewModel().isSearchMode()) {
                getHomeViewModel().setStopSearch(true);
            }
            boolean booleanExtra = intent.getBooleanExtra(NoteIntent.KEY_IS_FROM_FLOAT_WINDOW, false);
            boolean booleanExtra2 = intent.getBooleanExtra("create_note_immediately", false);
            if (this.mHandler != null && RomUtils.isPadMode() && booleanExtra && !booleanExtra2) {
                this.mHandler.postDelayed(new Runnable() { // from class: com.miui.notes.home.PadActivityController$$ExternalSyntheticLambda15
                    @Override // java.lang.Runnable
                    public final void run() {
                        this.f$0.lambda$deliverIntent$8(intent);
                    }
                }, 600L);
                return;
            } else {
                getHomeViewModel().createNoteFromIntent(intent);
                return;
            }
        }
        if (z2) {
            handleMiuiPlusIntent(intent);
            return;
        }
        if (!zEquals2) {
            if (zEquals3) {
                if (getHomeViewModel().getPageSelected() != 0) {
                    getHomeViewModel().setCurrentItem(0);
                }
                lambda$deliverIntent$9(-1L, -4097L, null);
                return;
            }
            if (zEquals4) {
                if (getHomeViewModel().getPageSelected() != 0) {
                    getHomeViewModel().setCurrentItem(0);
                }
                getHomeViewModel().setIntent(intent);
                return;
            } else if (zEquals5 || zEquals6 || zEquals7) {
                getHomeViewModel().setCurrentItem(1);
                getHomeViewModel().setIntent(intent);
                return;
            } else {
                if (z3) {
                    if (getHomeViewModel().getPageSelected() != 1) {
                        getHomeViewModel().setCurrentItem(1);
                    }
                    getHomeViewModel().setIntent(intent);
                    return;
                }
                return;
            }
        }
        if (getHomeViewModel().getPageSelected() != 0) {
            getHomeViewModel().setCurrentItem(0);
        }
        final long extraNoteIdFromIntent = getExtraNoteIdFromIntent(intent);
        Cursor cursorQueryNote = NoteProviderAccessUtils.queryNote(NoteApp.getInstance(), extraNoteIdFromIntent);
        final ArrayList<String> stringArrayListExtra = intent.getStringArrayListExtra(NoteIntent.ACTION_WITH_AI_QUERY);
        cursorQueryNote.moveToFirst();
        if (cursorQueryNote.getCount() > 0) {
            cursorQueryNote.close();
            long j = -4097;
            if (intent.hasExtra(NoteIntent.KEY_FOLDER_ID)) {
                long intExtra = intent.getIntExtra(NoteIntent.KEY_FOLDER_ID, -4097);
                if (intExtra == -6) {
                    j = intExtra;
                }
            }
            if (RomUtils.isFoldDevice() && PreferenceUtils.getNoteHomePageArrayWay(NoteApp.getInstance(), 0) == 1 && !UIUtils.isInNarrowScreen(NoteApp.getInstance())) {
                final long j2 = j;
                this.mHandler.postDelayed(new Runnable() { // from class: com.miui.notes.home.PadActivityController$$ExternalSyntheticLambda1
                    @Override // java.lang.Runnable
                    public final void run() {
                        this.f$0.lambda$deliverIntent$9(extraNoteIdFromIntent, j2, stringArrayListExtra);
                    }
                }, 50L);
            } else {
                PadActivityController padActivityController = this;
                padActivityController.lambda$deliverIntent$9(extraNoteIdFromIntent, j, stringArrayListExtra);
            }
        } else {
            cursorQueryNote.close();
            cursorQueryNote = NoteProviderAccessUtils.queryPrivateNote(NoteApp.getInstance(), extraNoteIdFromIntent);
            cursorQueryNote.moveToFirst();
            if (cursorQueryNote.getCount() > 0) {
                NoteCache noteCache = new NoteCache(ItemStore.parseNote(cursorQueryNote));
                if (noteCache.getNote().getFolderId() == -4) {
                    PrivateNotesListActivity.openPrivateNote(NoteApp.getInstance(), noteCache.getNote().getId());
                }
            } else {
                Logger.INSTANCE.d("PadDeliverIntent", "isNoteViewIntent data not found");
            }
        }
        cursorQueryNote.close();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$deliverIntent$8(Intent intent) {
        getHomeViewModel().createNoteFromIntent(intent);
    }

    /* JADX INFO: renamed from: com.miui.notes.home.PadActivityController$1, reason: invalid class name */
    class AnonymousClass1 implements MiuiPlusHelper.MiuiPlusCallBack {
        AnonymousClass1() {
        }

        @Override // com.miui.notes.tool.MiuiPlusHelper.MiuiPlusCallBack
        public void onOpenNote(final long j, final long j2) {
            Logger.INSTANCE.d("Notes:HandoffHelper", "onOpenNote, noteId: " + j + " folderId: " + j2);
            if (j2 == -4) {
                PrivateNotesListActivity.openPrivateNote(PadActivityController.this.getActivity(), j);
            } else {
                PadActivityController.this.getHomeViewModel().selectPage(0);
                PadActivityController.this.mHandler.post(new Runnable() { // from class: com.miui.notes.home.PadActivityController$1$$ExternalSyntheticLambda0
                    @Override // java.lang.Runnable
                    public final void run() {
                        this.f$0.lambda$onOpenNote$0(j, j2);
                    }
                });
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onOpenNote$0(long j, long j2) {
            PadActivityController.this.lambda$deliverIntent$9(j, j2, null);
        }

        @Override // com.miui.notes.tool.MiuiPlusHelper.MiuiPlusCallBack
        public void onOpenAppPage(String str) {
            Logger.INSTANCE.d("Notes:HandoffHelper", "onOpenAppPage: " + str);
            if ("app_setting".equals(str)) {
                PadAppPreferenceActivity.open(PadActivityController.this.getActivity());
            } else if ("quick_note_setting".equals(str)) {
                QuickNotePreferenceActivity.open(PadActivityController.this.getActivity());
            }
        }

        @Override // com.miui.notes.tool.MiuiPlusHelper.MiuiPlusCallBack
        public void onError(int i) {
            Logger.INSTANCE.d("Notes:HandoffHelper", "onError: " + i);
        }
    }

    private void handleMiuiPlusIntent(Intent intent) {
        MiuiPlusHelper.checkMiuiplusDataFromIntent(getActivity(), intent, new AnonymousClass1());
    }

    protected final long getExtraNoteIdFromIntent(Intent intent) {
        long j = 0;
        long longExtra = intent.getLongExtra("android.intent.extra.UID", 0L);
        if (longExtra <= 0) {
            if (intent.getStringExtra("mishow_name") != null) {
                long noteId = MiShowPreferenceHelper.getNoteId(intent.getStringExtra("mishow_name"));
                if (WorkingNote.loadNote("_id=" + noteId, null).getFolderId() == -3) {
                    Logger.INSTANCE.i(MiShowReceiver.TAG, "noteId is in trash folder");
                } else {
                    j = noteId;
                }
                Logger.INSTANCE.i(MiShowReceiver.TAG, "intent get noteId: " + j);
                return j;
            }
            Uri data = intent.getData();
            if (data == null || !"notes".equals(data.getAuthority())) {
                Logger.INSTANCE.e(TAG, "Invalid data uri: " + (data != null ? data.toString() : null));
            } else {
                try {
                    return ContentUris.parseId(data);
                } catch (Exception e) {
                    Logger.INSTANCE.e(TAG, "Fail to parse note id from uri: " + data + StringUtils.SPACE + e);
                }
            }
        }
        return longExtra;
    }

    /* JADX INFO: renamed from: openNote, reason: merged with bridge method [inline-methods] */
    public void lambda$deliverIntent$9(long j, long j2, ArrayList<String> arrayList) {
        if (j == -1) {
            getHomeViewModel().setFolderId(j2);
            return;
        }
        Cursor cursorQueryNote = NoteProviderAccessUtils.queryNote(NoteApp.getInstance(), j);
        cursorQueryNote.moveToFirst();
        if (cursorQueryNote.getCount() > 0) {
            NoteCache noteCache = new NoteCache(ItemStore.parseNote(cursorQueryNote));
            noteCache.setHighlightKeywords(arrayList);
            getHomeViewModel().onOpenNote(j2, noteCache, "");
            getHomeViewModel().setFolderId(j2);
        } else {
            getHomeViewModel().setFolderId(j2);
        }
        cursorQueryNote.close();
    }

    private void gotoHandWrite(String str) {
        if (TextUtils.equals(str, PadHandWriteFragment.EXTERNAL_JUMP_STYLUS_SCREEN_OFF)) {
            getHomeViewModel().createHandWriteNote(true);
            return;
        }
        if (TextUtils.equals(str, PadHandWriteFragment.EXTERNAL_JUMP_STYLUS_KEYGUARD)) {
            getHomeViewModel().createHandWriteNote(true);
            return;
        }
        if (TextUtils.equals(str, PadHandWriteFragment.EXTERNAL_JUMP_STYLUS_HOME) || TextUtils.equals(str, PadHandWriteFragment.EXTERNAL_JUMP_STYLUS_APP)) {
            if (!showPreviousNote()) {
                getHomeViewModel().createHandWriteNote(true);
            } else {
                getHomeViewModel().clearCover(true);
            }
        }
    }

    private boolean showPreviousNote() {
        NoteLoadDataEntity selectedNote = getHomeViewModel().getSelectedNote();
        return (getActivity().getNavigator() == null || getActivity().getNavigator().isContentOpen() || getActivity().getNavigator().getNavigationMode() == Navigator.Mode.C || getHomeViewModel().getPageSelected() != 0 || selectedNote == null || selectedNote.getNoteType() != 2) ? false : true;
    }

    private void wakeUpScreen(Intent intent) {
        if (intent == null || this.wakeUp) {
            return;
        }
        String stringExtra = intent.getStringExtra(PadHandWriteFragment.INTENT_SHORTCUT);
        if (TextUtils.equals(intent.getType(), Notes.TextData.CONTENT_ITEM_TYPE) && TextUtils.equals(stringExtra, PadHandWriteFragment.EXTERNAL_JUMP_STYLUS) && TextUtils.equals(intent.getStringExtra(PadHandWriteFragment.INTENT_SCENE), PadHandWriteFragment.EXTERNAL_JUMP_STYLUS_SCREEN_OFF)) {
            ((PowerManager) getActivity().getSystemService("power")).newWakeLock(268435462, getClass().getCanonicalName()).acquire(10000L);
            this.wakeUp = true;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void createNaviMenu(NavigationMenuData navigationMenuData) {
        NavigationFolderMenu stickFolderMenu = navigationMenuData.getStickFolderMenu();
        this.stickCategoryList.clear();
        this.stickCategoryAdapter.setEditListener(null);
        if (!stickFolderMenu.getFolderList().isEmpty()) {
            this.stickCategory.setTitle(stickFolderMenu.getTitle());
            HintWidgetProvider hintWidgetProvider = (HintWidgetProvider) this.stickCategoryAdapter.getWidgetProvider();
            for (FolderModel folderModel : stickFolderMenu.getFolderList()) {
                ListCategoryAdapter.Item item = new ListCategoryAdapter.Item(folderModel.getSubject(), com.miui.notes.R.drawable.pad_sidebar_folder_icon);
                Bundle bundle = new Bundle();
                bundle.putInt(ContentFragment.ARG_PAGE, 0);
                bundle.putLong("data_id", folderModel.getId());
                item.setNavigatorInfo(new UpdateFragmentNavInfo(((int) folderModel.getId()) + 4097, getActivity().getDefaultContentFragment(), bundle));
                hintWidgetProvider.setHint(item, Utils.obtainLocalInteger(folderModel.getCount()));
                this.stickCategoryList.add(item);
            }
            this.stickCategoryAdapter.setEditListener(new EditListener(stickFolderMenu, false));
        }
        if (this.stickCategoryAdapter.isEditing()) {
            this.stickCategoryAdapter.dispatchEditAction(-2);
        }
        this.stickCategoryAdapter.notifyDataSetChanged();
        NavigationFolderMenu normalFolderMenu = navigationMenuData.getNormalFolderMenu();
        this.normalCategoryList.clear();
        this.normalCategoryAdapter.setEditListener(null);
        if (!normalFolderMenu.getFolderList().isEmpty()) {
            this.normalCategory.setTitle(normalFolderMenu.getTitle());
            HintWidgetProvider hintWidgetProvider2 = (HintWidgetProvider) this.normalCategoryAdapter.getWidgetProvider();
            for (FolderModel folderModel2 : normalFolderMenu.getFolderList()) {
                ListCategoryAdapter.Item item2 = new ListCategoryAdapter.Item(folderModel2.getSubject(), com.miui.notes.R.drawable.pad_sidebar_folder_icon);
                Bundle bundle2 = new Bundle();
                bundle2.putInt(ContentFragment.ARG_PAGE, 0);
                bundle2.putLong("data_id", folderModel2.getId());
                item2.setNavigatorInfo(new UpdateFragmentNavInfo(((int) folderModel2.getId()) + 4097, getActivity().getDefaultContentFragment(), bundle2));
                hintWidgetProvider2.setHint(item2, Utils.obtainLocalInteger(folderModel2.getCount()));
                this.normalCategoryList.add(item2);
            }
            this.normalCategoryAdapter.setEditListener(new EditListener(normalFolderMenu, true));
        }
        if (this.normalCategoryAdapter.isEditing()) {
            this.normalCategoryAdapter.dispatchEditAction(-2);
        }
        this.normalCategoryAdapter.notifyDataSetChanged();
        NavigationFolderMenu otherFolderMenu = navigationMenuData.getOtherFolderMenu();
        this.otherCategoryList.clear();
        if (!otherFolderMenu.getFolderList().isEmpty()) {
            this.otherCategory.setTitle(otherFolderMenu.getTitle());
            HintWidgetProvider hintWidgetProvider3 = (HintWidgetProvider) this.otherCategoryAdapter.getWidgetProvider();
            for (FolderModel folderModel3 : otherFolderMenu.getFolderList()) {
                MenuCategoryAdapter.Item item3 = new MenuCategoryAdapter.Item(folderModel3.getSubject(), folderModel3.getId() == 0 ? com.miui.notes.R.drawable.pad_sidebar_folder_icon : com.miui.notes.R.drawable.pad_ic_navi_trash);
                if (folderModel3.getId() == 0) {
                    Bundle bundle3 = new Bundle();
                    bundle3.putInt(ContentFragment.ARG_PAGE, 0);
                    bundle3.putLong("data_id", folderModel3.getId());
                    item3.setNavigatorInfo(new UpdateFragmentNavInfo(((int) folderModel3.getId()) + 4097, getActivity().getDefaultContentFragment(), bundle3));
                } else if (folderModel3.getId() == -3) {
                    Intent intent = new Intent(getActivity(), (Class<?>) FolderDialogActivity.class);
                    intent.setAction(FolderDialogActivity.ACTION_TO_TRASH);
                    item3.setNavigatorInfo(new ActivityNavInfo(getActivity(), -1, intent));
                }
                hintWidgetProvider3.setHint(item3, Utils.obtainLocalInteger(folderModel3.getCount()));
                this.otherCategoryList.add(item3);
            }
        }
        this.otherCategoryAdapter.notifyDataSetChanged();
    }

    private void initCategory() {
        this.stickCategoryList = new ArrayList();
        this.stickCategory = getActivity().getNavigator().newCategory(100);
        ListCategoryAdapter listCategoryAdapter = new ListCategoryAdapter(this.stickCategoryList, com.miui.notes.R.layout.miuix_navigator_item_label, new HintWidgetProvider(), getEditConfig(true));
        this.stickCategoryAdapter = listCategoryAdapter;
        listCategoryAdapter.setShowIconOnEdit(true);
        this.stickCategory.setAdapter(this.stickCategoryAdapter);
        this.stickCategory.setNavigatorDragListener(new NavigatorDragListener() { // from class: com.miui.notes.home.PadActivityController.2
            @Override // miuix.navigator.draganddrop.NavigatorDragListener
            public void onBindDragPlaceholder(RecyclerView.ViewHolder viewHolder, boolean z) {
            }

            @Override // miuix.navigator.draganddrop.NavigatorDragListener
            public boolean onDropInsert(DragEvent dragEvent, CategoryAdapter<? extends CategoryAdapter.Item> categoryAdapter, int i) {
                return false;
            }

            @Override // miuix.navigator.draganddrop.NavigatorDragListener
            public void onDragStart(DragEvent dragEvent, DragStartFeedback dragStartFeedback) {
                dragStartFeedback.setCanAccept(true);
            }

            @Override // miuix.navigator.draganddrop.NavigatorDragListener
            public boolean onDropAccept(DragEvent dragEvent, RecyclerView.ViewHolder viewHolder) {
                ClipData clipData = dragEvent.getClipData();
                if (clipData.getDescription() != null && TextUtils.equals(clipData.getDescription().getLabel(), PadActivityController.NOTE_LIST_DRAG_LABEL)) {
                    int bindingAdapterPosition = viewHolder.getBindingAdapterPosition();
                    if (viewHolder.getBindingAdapter() == PadActivityController.this.stickCategoryAdapter && PadActivityController.this.getHomeViewModel().getNaviMenuData().getValue() != null) {
                        return PadActivityController.this.dropAccept(clipData, PadActivityController.this.getHomeViewModel().getNaviMenuData().getValue().getStickFolderMenu().getFolderList().get(bindingAdapterPosition));
                    }
                }
                return false;
            }
        });
        getActivity().getNavigator().addCategory(this.stickCategory);
        this.normalCategoryList = new ArrayList();
        this.normalCategory = getActivity().getNavigator().newCategory(101);
        ListCategoryAdapter listCategoryAdapter2 = new ListCategoryAdapter(this.normalCategoryList, com.miui.notes.R.layout.miuix_navigator_item_label, new HintWidgetProvider(), getEditConfig(false));
        this.normalCategoryAdapter = listCategoryAdapter2;
        listCategoryAdapter2.setShowIconOnEdit(true);
        this.normalCategory.setAdapter(this.normalCategoryAdapter);
        this.normalCategory.setNavigatorDragListener(new NavigatorDragListener() { // from class: com.miui.notes.home.PadActivityController.3
            @Override // miuix.navigator.draganddrop.NavigatorDragListener
            public void onBindDragPlaceholder(RecyclerView.ViewHolder viewHolder, boolean z) {
            }

            @Override // miuix.navigator.draganddrop.NavigatorDragListener
            public boolean onDropInsert(DragEvent dragEvent, CategoryAdapter<? extends CategoryAdapter.Item> categoryAdapter, int i) {
                return false;
            }

            @Override // miuix.navigator.draganddrop.NavigatorDragListener
            public void onDragStart(DragEvent dragEvent, DragStartFeedback dragStartFeedback) {
                dragStartFeedback.setCanAccept(true);
            }

            @Override // miuix.navigator.draganddrop.NavigatorDragListener
            public boolean onDropAccept(DragEvent dragEvent, RecyclerView.ViewHolder viewHolder) {
                ClipData clipData = dragEvent.getClipData();
                if (clipData.getDescription() != null && TextUtils.equals(clipData.getDescription().getLabel(), PadActivityController.NOTE_LIST_DRAG_LABEL)) {
                    int bindingAdapterPosition = viewHolder.getBindingAdapterPosition();
                    if (viewHolder.getBindingAdapter() == PadActivityController.this.normalCategoryAdapter && PadActivityController.this.getHomeViewModel().getNaviMenuData().getValue() != null) {
                        return PadActivityController.this.dropAccept(clipData, PadActivityController.this.getHomeViewModel().getNaviMenuData().getValue().getNormalFolderMenu().getFolderList().get(bindingAdapterPosition));
                    }
                }
                return false;
            }
        });
        getActivity().getNavigator().addCategory(this.normalCategory);
        this.otherCategoryList = new ArrayList();
        this.otherCategory = getActivity().getNavigator().newCategory(102, 103);
        MenuCategoryAdapter menuCategoryAdapter = new MenuCategoryAdapter(this.otherCategoryList, com.miui.notes.R.layout.miuix_navigator_item_label, new HintWidgetProvider(), CategoryAdapter.EditConfig.uneditableConfig());
        this.otherCategoryAdapter = menuCategoryAdapter;
        menuCategoryAdapter.setFooterAdapter(new AnonymousClass4());
        this.otherCategory.setAdapter(this.otherCategoryAdapter);
        this.otherCategory.setNavigatorDragListener(new NavigatorDragListener() { // from class: com.miui.notes.home.PadActivityController.5
            @Override // miuix.navigator.draganddrop.NavigatorDragListener
            public void onBindDragPlaceholder(RecyclerView.ViewHolder viewHolder, boolean z) {
            }

            @Override // miuix.navigator.draganddrop.NavigatorDragListener
            public boolean onDropInsert(DragEvent dragEvent, CategoryAdapter<? extends CategoryAdapter.Item> categoryAdapter, int i) {
                return false;
            }

            @Override // miuix.navigator.draganddrop.NavigatorDragListener
            public void onDragStart(DragEvent dragEvent, DragStartFeedback dragStartFeedback) {
                dragStartFeedback.setCanAccept(true);
            }

            @Override // miuix.navigator.draganddrop.NavigatorDragListener
            public void onDragHover(RecyclerView.ViewHolder viewHolder) {
                int bindingAdapterPosition = viewHolder.getBindingAdapterPosition();
                if (PadActivityController.this.getHomeViewModel().getNaviMenuData().getValue() == null || PadActivityController.this.getHomeViewModel().getNaviMenuData().getValue().getOtherFolderMenu().getFolderList().get(bindingAdapterPosition).getId() != -3) {
                    super.onDragHover(viewHolder);
                }
            }

            @Override // miuix.navigator.draganddrop.NavigatorDragListener
            public boolean onDropAccept(DragEvent dragEvent, RecyclerView.ViewHolder viewHolder) {
                ClipData clipData = dragEvent.getClipData();
                if (clipData.getDescription() != null && TextUtils.equals(clipData.getDescription().getLabel(), PadActivityController.NOTE_LIST_DRAG_LABEL)) {
                    int bindingAdapterPosition = viewHolder.getBindingAdapterPosition();
                    if (viewHolder.getBindingAdapter() == PadActivityController.this.otherCategoryAdapter && PadActivityController.this.getHomeViewModel().getNaviMenuData().getValue() != null) {
                        return PadActivityController.this.dropAccept(clipData, PadActivityController.this.getHomeViewModel().getNaviMenuData().getValue().getOtherFolderMenu().getFolderList().get(bindingAdapterPosition));
                    }
                }
                return false;
            }
        });
        getActivity().getNavigator().addCategory(this.otherCategory);
    }

    /* JADX INFO: renamed from: com.miui.notes.home.PadActivityController$4, reason: invalid class name */
    class AnonymousClass4 extends FooterAdapter {
        @Override // miuix.navigator.adapter.FooterAdapter
        public void onBindFooterView(RecyclerView.ViewHolder viewHolder) {
        }

        AnonymousClass4() {
        }

        @Override // miuix.navigator.adapter.FooterAdapter
        public RecyclerView.ViewHolder onCreateFooterView(ViewGroup viewGroup) {
            View viewInflate = LayoutInflater.from(PadActivityController.this.getActivity()).inflate(com.miui.notes.R.layout.pad_navi_menu_create_folder_layout, viewGroup, false);
            viewInflate.setOnClickListener(new View.OnClickListener() { // from class: com.miui.notes.home.PadActivityController.4.1
                @Override // android.view.View.OnClickListener
                public void onClick(View view) {
                    if (Utils.isFastClick(view.getId(), 500L)) {
                        return;
                    }
                    BaseHybridNoteEditFragment.removeAiFloatingWindow();
                    CreateFolderDialog createFolderDialog = new CreateFolderDialog(PadActivityController.this.getActivity());
                    createFolderDialog.setOnDismissListener(new DialogInterface.OnDismissListener() { // from class: com.miui.notes.home.PadActivityController.4.1.1
                        @Override // android.content.DialogInterface.OnDismissListener
                        public void onDismiss(DialogInterface dialogInterface) {
                            CreateFolderDialog createFolderDialog2 = (CreateFolderDialog) dialogInterface;
                            long folderId = createFolderDialog2.getFolderId();
                            Logger.INSTANCE.d(PadActivityController.TAG, "showCreateFolderDialog onDismiss id: " + folderId + " subject:" + createFolderDialog2.getFolderName());
                            if (folderId > 0) {
                                PadActivityController.this.getHomeViewModel().refreshFolderName(LoaderManager.getInstance(PadActivityController.this.getActivity()));
                            }
                        }
                    });
                    createFolderDialog.show();
                }
            });
            viewInflate.setAccessibilityDelegate(new View.AccessibilityDelegate() { // from class: com.miui.notes.home.PadActivityController.4.2
                @Override // android.view.View.AccessibilityDelegate
                public void onInitializeAccessibilityNodeInfo(View view, AccessibilityNodeInfo accessibilityNodeInfo) {
                    super.onInitializeAccessibilityNodeInfo(view, accessibilityNodeInfo);
                    accessibilityNodeInfo.setClassName("android.widget.Button");
                    accessibilityNodeInfo.setContentDescription(PadActivityController.this.getActivity().getResources().getString(com.miui.notes.R.string.menu_create_folder));
                }
            });
            return new RecyclerView.ViewHolder(viewInflate) { // from class: com.miui.notes.home.PadActivityController.4.3
            };
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean dropAccept(ClipData clipData, FolderModel folderModel) {
        if (folderModel.getId() == -2 || getHomeViewModel().getFolderId() == -2) {
            Logger.INSTANCE.d(TAG, "dropAccept: cannot move to or from the call notes folder");
            return false;
        }
        if (clipData.getItemCount() > 0) {
            CharSequence text = clipData.getItemAt(0).getText();
            if (!TextUtils.isEmpty(text)) {
                String[] strArrSplit = TextUtils.split((String) text, ",");
                long[] jArr = new long[strArrSplit.length];
                for (int i = 0; i < strArrSplit.length; i++) {
                    jArr[i] = Long.parseLong(strArrSplit[i]);
                }
                Logger.INSTANCE.d(TAG, "dropAccept ids:" + Arrays.toString(jArr));
                if (folderModel.getId() != -3) {
                    this.noteOperationViewModel.move(jArr, folderModel.getId(), folderModel.getSubject());
                } else {
                    this.noteOperationViewModel.delete(jArr, false);
                }
                LiveDataEventBus.with(LiveDataEventBus.BUS_EXIT_LIST_MULTI).setValue(true);
                return true;
            }
        }
        return false;
    }

    private class EditListener implements ListCategoryAdapter.EditListener {
        private NavigationFolderMenu folderMenu;
        private boolean stick;

        public EditListener(NavigationFolderMenu navigationFolderMenu, boolean z) {
            this.folderMenu = navigationFolderMenu;
            this.stick = z;
        }

        @Override // miuix.navigator.adapter.ListCategoryAdapter.EditListener
        public void onSelectionChanged(CategoryAdapter<?> categoryAdapter, int[] iArr, Menu menu) {
            BaseHybridNoteEditFragment.removeAiFloatingWindow();
            super.onSelectionChanged(categoryAdapter, iArr, menu);
            if (iArr.length > 0) {
                for (int i : iArr) {
                    if (this.folderMenu.getFolderList().get(i).getId() <= 0) {
                        menu.findItem(2).setEnabled(false);
                        menu.findItem(3).setEnabled(false);
                        return;
                    }
                }
                return;
            }
            menu.findItem(2).setEnabled(false);
            menu.findItem(3).setEnabled(false);
            menu.findItem(1).setEnabled(false);
        }

        @Override // miuix.navigator.adapter.ListCategoryAdapter.EditListener
        public boolean onEditAction(CategoryAdapter<?> categoryAdapter, int i, int[] iArr) {
            if (iArr.length <= 0) {
                return false;
            }
            ArrayList arrayList = new ArrayList();
            for (int i2 : iArr) {
                arrayList.add(this.folderMenu.getFolderList().get(i2));
            }
            if (i == 1) {
                PadActivityController.this.folderOperationViewModel.stick(arrayList, this.stick);
            } else if (i == 2) {
                PadActivityController.this.delete(arrayList);
            } else if (i == 3) {
                PadActivityController.this.rename(this.folderMenu.getFolderList().get(iArr[0]));
            }
            return true;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void delete(List<FolderModel> list) {
        DeleteFolderDialog.create(getActivity(), list, new DeleteFolderDialog.DeleteDialogCallback() { // from class: com.miui.notes.home.PadActivityController$$ExternalSyntheticLambda13
            @Override // com.miui.notes.component.DeleteFolderDialog.DeleteDialogCallback
            public final void onPositiveClick(List list2) {
                this.f$0.lambda$delete$10(list2);
            }
        }).show();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$delete$10(List list) {
        this.folderOperationViewModel.delete(list);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void rename(FolderModel folderModel) {
        if (folderModel.getId() <= 0) {
            return;
        }
        final RenameFolderDialog renameFolderDialog = new RenameFolderDialog(getActivity(), folderModel);
        renameFolderDialog.setNegativeListener(new View.OnClickListener() { // from class: com.miui.notes.home.PadActivityController$$ExternalSyntheticLambda0
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                renameFolderDialog.dismiss();
            }
        });
        renameFolderDialog.setPositiveListener(new View.OnClickListener() { // from class: com.miui.notes.home.PadActivityController$$ExternalSyntheticLambda7
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f$0.lambda$rename$12(view);
            }
        });
        renameFolderDialog.setOnDismissListener(new DialogInterface.OnDismissListener() { // from class: com.miui.notes.home.PadActivityController$$ExternalSyntheticLambda8
            @Override // android.content.DialogInterface.OnDismissListener
            public final void onDismiss(DialogInterface dialogInterface) {
                PadActivityController.lambda$rename$13(dialogInterface);
            }
        });
        if (getActivity().isFinishing()) {
            return;
        }
        renameFolderDialog.show();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$rename$12(View view) {
        this.stickCategoryAdapter.finishEdit(false);
        this.normalCategoryAdapter.finishEdit(false);
    }

    private CategoryAdapter.EditConfig getEditConfig(boolean z) {
        CategoryAdapter.EditMenu editMenu = new CategoryAdapter.EditMenu(1, z ? com.miui.notes.R.string.menu_unstick : com.miui.notes.R.string.menu_stick, z ? com.miui.notes.R.drawable.miui_ic_unstick : com.miui.notes.R.drawable.miui_ic_stick, 2, true, true);
        return NoteListEditConfig.listNoteConfig(false).addMenu(editMenu).addMenu(new CategoryAdapter.EditMenu(2, com.miui.notes.R.string.menu_delete, com.miui.notes.R.drawable.miui_ic_delete, 2, true, true)).addMenu(new CategoryAdapter.EditMenu(3, com.miui.notes.R.string.menu_edit, com.miui.notes.R.drawable.miui_ic_rename, 1, true, true));
    }

    @Override // com.miui.notes.home.BaseNoteListActivityController, com.miui.notes.basefeature.IActivityController
    public void onMultiWindowModeChanged(boolean z) {
        if (z && getHomeViewModel().getPageSelected() == 0) {
            unregisterNoteEditKeyboardDetector();
            registerNoteEditKeyboardDetector();
        }
        this.needRegisteredKeyboard = z && getHomeViewModel().getPageSelected() == 0;
    }

    @Override // com.miui.notes.home.BaseNoteListActivityController, com.miui.notes.basefeature.IActivityController
    public void onConfigurationChanged(Configuration configuration) {
        super.onConfigurationChanged(configuration);
        if (getHomeViewModel().getPageSelected() == 0) {
            unregisterNoteEditKeyboardDetector();
            SelectNoteData value = getHomeViewModel().getSelectNoteLiveData().getValue();
            if (value == null || value.getEntity() == null) {
                return;
            }
            if (value.getEntity().getNoteType() != 3) {
                registerNoteEditKeyboardDetector();
            } else if (getActivity().isInMultiWindowMode()) {
                registerNoteEditKeyboardDetector();
            }
        }
    }

    public void registerNoteEditKeyboardDetector() {
        Logger.INSTANCE.d(TAG, "registerNoteEditKeyboardDetector");
        this.mSecondaryContentDecorWrapper = this.mRootView.findViewById(com.miui.notes.R.id.secondary_content_decor_wrapper);
        this.mNoteEditKeyboardHelper = new KeyboardHelper(this.toolLayout, getActivity().getWindow().getDecorView(), this.keyboardPaddingProcessor);
    }

    public void unregisterNoteEditKeyboardDetector() {
        Logger.INSTANCE.d(TAG, "unregisterNoteEditKeyboardDetector");
        KeyboardHelper keyboardHelper = this.mNoteEditKeyboardHelper;
        if (keyboardHelper != null) {
            keyboardHelper.release();
            this.mNoteEditKeyboardHelper = null;
        }
    }

    @Override // com.miui.notes.home.BaseNoteListActivityController, com.miui.notes.basefeature.IActivityController
    public void onDestroy() {
        LoaderManager.getInstance(getActivity()).destroyLoader(1);
        Handler handler = this.mHandler;
        if (handler != null) {
            handler.removeCallbacksAndMessages(null);
        }
        releaseAIInput();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public Fragment getDetailFragment() {
        FragmentManager fragmentManager;
        if (getActivity().getNavigator() == null || getActivity().getNavigator().getByTag(Navigator.TAG_SECONDARY_CONTENT) == null || (fragmentManager = getActivity().getNavigator().getByTag(Navigator.TAG_SECONDARY_CONTENT).getFragmentManager()) == null) {
            return null;
        }
        return fragmentManager.findFragmentByTag(Navigator.TAG_SECONDARY_CONTENT);
    }

    private void initAIInputCallBack() {
        this.mAIInputCallback = new AIInputTextManager.AITextCallback() { // from class: com.miui.notes.home.PadActivityController.7
            @Override // com.xiaomi.aiinput.AIInputTextManager.AITextCallback
            public void onFocusedAITextEdit(View view, String str, int i, AIInputResultListener aIInputResultListener) {
                Fragment detailFragment = PadActivityController.this.getDetailFragment();
                if (i == 0) {
                    if (detailFragment instanceof BaseHybridNoteEditFragment) {
                        ((BaseHybridNoteEditFragment) detailFragment).replaceText(str);
                    }
                } else if (i == 1 && (detailFragment instanceof BaseHybridNoteEditFragment)) {
                    ((BaseHybridNoteEditFragment) detailFragment).insertText(str);
                }
                try {
                    aIInputResultListener.onHandleTextView(i, true);
                } catch (Exception e) {
                    Logger.INSTANCE.e(PadActivityController.TAG, "onFocusedAITextEdit error" + e.getMessage());
                }
            }

            @Override // com.xiaomi.aiinput.AIInputTextManager.AITextCallback
            public void onRequestFocusedAITextInfo(View view, AIInputResultListener aIInputResultListener) {
                Fragment detailFragment = PadActivityController.this.getDetailFragment();
                try {
                    if (detailFragment instanceof BaseHybridNoteEditFragment) {
                        ((BaseHybridNoteEditFragment) detailFragment).handleGetAiTextInfo(aIInputResultListener, PadActivityController.this.getActivity().toString());
                    }
                } catch (Exception e) {
                    Logger.INSTANCE.e(PadActivityController.TAG, "onRequestFocusedAITextInfo error" + e.getMessage());
                }
            }
        };
    }

    public Uri getDeepLink() {
        NoteLoadDataEntity selectedNote;
        if (getHomeViewModel() == null || (selectedNote = getHomeViewModel().getSelectedNote()) == null) {
            return null;
        }
        Binder.clearCallingIdentity();
        WorkingNote workingNoteLoadNote = WorkingNote.loadNote("_id=" + selectedNote.getNoteId(), null);
        return HandoffHelper.getNoteDeepLink(workingNoteLoadNote.getSyncId(), workingNoteLoadNote.getFolderId());
    }

    private void initAIInput() {
        try {
            this.mIsSupportAIInput = AIInputTextManager.getInstance(getActivity().getApplicationContext()) != null;
        } catch (Throwable th) {
            th.printStackTrace();
            Logger.INSTANCE.e(TAG, "setIsSupportAIInput error: " + th);
            this.mIsSupportAIInput = false;
        }
        if (this.mIsSupportAIInput) {
            initAIInputCallBack();
            AIInputTextManager.getInstance(getActivity().getApplicationContext()).registerAITextCallback(this.mAIInputCallback);
        }
    }

    private void releaseAIInput() {
        if (!this.mIsSupportAIInput || this.mAIInputCallback == null) {
            return;
        }
        AIInputTextManager.getInstance(getActivity()).unregisterAITextCallback(this.mAIInputCallback);
    }
}
