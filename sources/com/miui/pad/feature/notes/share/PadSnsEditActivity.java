package com.miui.pad.feature.notes.share;

import android.app.Activity;
import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.ContentResolver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.ResolveInfo;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.activity.result.ActivityResultLauncher;
import androidx.core.content.IntentCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.OnApplyWindowInsetsListener;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.exifinterface.media.ExifInterface;
import androidx.hardware.DataSpace;
import androidx.lifecycle.ViewModelProvider;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import ch.qos.logback.core.CoreConstants;
import com.market.sdk.reflect.ReflectUtilsForMiui;
import com.miui.common.app.BaseNoteApp;
import com.miui.common.base.BaseActivity;
import com.miui.common.base.BaseAlarmAlertActivity;
import com.miui.common.base.DialogManagerInterface;
import com.miui.common.tool.CompatUtils;
import com.miui.common.tool.ExternalAppUtils;
import com.miui.common.tool.ImageUtils;
import com.miui.common.tool.RomUtils;
import com.miui.common.tool.UIUtils;
import com.miui.common.tool.Utils;
import com.miui.maml.component.MamlView;
import com.miui.notes.R;
import com.miui.notes.ai.AigcPicMetaData;
import com.miui.notes.base.accessibility.AccessibilityDelegateUtil;
import com.miui.notes.base.utils.BitmapUtils;
import com.miui.notes.base.utils.Logger;
import com.miui.notes.cloudservice.NotesConstants;
import com.miui.notes.controller.PasswordController;
import com.miui.notes.model.SharedViewModel;
import com.miui.notes.model.WorkingNote;
import com.miui.notes.theme.AnimTheme;
import com.miui.notes.theme.util.ResourceManager;
import com.miui.notes.tool.PermissionUtils;
import com.miui.notes.tool.PreferenceUtils;
import com.miui.notes.tool.SyncUtils;
import com.miui.notes.tool.TempFileManager;
import com.miui.notes.ui.SnsEditActivity;
import com.miui.notes.ui.jsbridge.MindShareDataHolder;
import com.miui.notes.ui.view.WeiboPreviewView;
import com.miui.pad.richeditor.share.PadRenderBuilder;
import com.miui.richeditor.schema.HtmlParser;
import com.miui.richeditor.share.RenderBuilder;
import com.miui.richeditor.share.render.Render;
import com.miui.richeditor.theme.Theme;
import com.xiaomi.mirror.synergy.CallMethod;
import defpackage.R2;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;
import kotlin.Lazy;
import kotlin.LazyKt;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import miuix.android.content.SystemIntent;
import miuix.animation.Folme;
import miuix.animation.base.AnimConfig;
import miuix.appcompat.app.ActionBar;
import miuix.appcompat.app.Fragment;
import miuix.appcompat.app.ProgressDialog;
import miuix.graphics.BitmapFactory;
import miuix.responsive.map.ScreenSpec;

/* JADX INFO: compiled from: PadSnsEditActivity.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000¦\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\t\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0010 \n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u0011\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u000f\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0010\u0015\n\u0002\b\t\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\t\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u000b\u0018\u0000 ¡\u00012\u00020\u00012\u00020\u0002:\b\u009e\u0001\u009f\u0001 \u0001¡\u0001B\u0007¢\u0006\u0004\b\u0003\u0010\u0004J\u0012\u0010V\u001a\u00020W2\b\u0010X\u001a\u0004\u0018\u00010YH\u0016J\b\u0010Z\u001a\u00020WH\u0002J\b\u0010[\u001a\u00020WH\u0002J\u0010\u0010\\\u001a\u00020W2\u0006\u0010]\u001a\u00020\bH\u0016J\b\u0010_\u001a\u00020WH\u0002J\u0010\u0010`\u001a\u00020W2\u0006\u0010a\u001a\u00020=H\u0002J\b\u0010b\u001a\u00020WH\u0002J\b\u0010c\u001a\u00020WH\u0002J\b\u0010d\u001a\u00020WH\u0002J\u0010\u0010e\u001a\u00020\b2\u0006\u0010f\u001a\u00020?H\u0002J\u0010\u0010g\u001a\u00020W2\u0006\u0010h\u001a\u00020iH\u0002J\u0010\u0010j\u001a\u00020W2\u0006\u0010k\u001a\u00020\nH\u0002J\b\u0010l\u001a\u00020WH\u0002J+\u0010m\u001a\u00020W2\u0006\u0010n\u001a\u00020\b2\f\u0010o\u001a\b\u0012\u0004\u0012\u00020\n0K2\u0006\u0010p\u001a\u00020qH\u0016¢\u0006\u0002\u0010rJ\u0010\u0010s\u001a\u00020W2\u0006\u0010t\u001a\u00020\u0014H\u0002J\u0010\u0010u\u001a\u00020W2\u0006\u0010v\u001a\u000203H\u0002J\u0012\u0010w\u001a\u00020W2\b\u0010x\u001a\u0004\u0018\u000103H\u0002J\u0018\u0010y\u001a\u00020W2\u0006\u0010z\u001a\u00020{2\u0006\u0010|\u001a\u00020\u0006H\u0002J\u0018\u0010}\u001a\u00020?2\u0006\u0010~\u001a\u00020\u007f2\u0006\u0010z\u001a\u00020{H\u0002J\u0011\u0010\u0080\u0001\u001a\u00020W2\u0006\u0010v\u001a\u000203H\u0002J\u0013\u0010\u0081\u0001\u001a\u00020W2\b\u0010\u0082\u0001\u001a\u00030\u0083\u0001H\u0016J\t\u0010\u0084\u0001\u001a\u00020?H\u0016J\t\u0010\u0085\u0001\u001a\u00020?H\u0016J\n\u0010\u0086\u0001\u001a\u00030\u0087\u0001H\u0016J\f\u0010\u0088\u0001\u001a\u0005\u0018\u00010\u0089\u0001H\u0016J\t\u0010\u008a\u0001\u001a\u00020WH\u0014J\t\u0010\u008b\u0001\u001a\u00020WH\u0014J\t\u0010\u008c\u0001\u001a\u00020WH\u0014J%\u0010\u008d\u0001\u001a\u00020W2\u0006\u0010n\u001a\u00020\b2\u0007\u0010\u008e\u0001\u001a\u00020\b2\t\u0010\u008f\u0001\u001a\u0004\u0018\u00010{H\u0014J\t\u0010\u0090\u0001\u001a\u00020WH\u0016J\u0013\u0010\u0091\u0001\u001a\u00020W2\b\u0010\u0092\u0001\u001a\u00030\u0093\u0001H\u0017J\t\u0010\u0094\u0001\u001a\u00020?H\u0014J&\u0010\u0095\u0001\u001a\u00020W2\b\u0010\u0092\u0001\u001a\u00030\u0093\u00012\b\u0010\u0096\u0001\u001a\u00030\u0097\u00012\u0007\u0010\u0098\u0001\u001a\u00020?H\u0016J\t\u0010\u0099\u0001\u001a\u00020WH\u0002J\t\u0010\u009a\u0001\u001a\u00020WH\u0002J\t\u0010\u009b\u0001\u001a\u00020WH\u0002J\t\u0010\u009c\u0001\u001a\u00020WH\u0016J\t\u0010\u009d\u0001\u001a\u00020WH\u0002R\u0010\u0010\u0005\u001a\u0004\u0018\u00010\u0006X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\bX\u0082\u000e¢\u0006\u0002\n\u0000R\u0010\u0010\t\u001a\u0004\u0018\u00010\nX\u0082\u000e¢\u0006\u0002\n\u0000R\u0010\u0010\u000b\u001a\u0004\u0018\u00010\fX\u0082\u000e¢\u0006\u0002\n\u0000R\u0014\u0010\r\u001a\b\u0018\u00010\u000eR\u00020\u000fX\u0082\u000e¢\u0006\u0002\n\u0000R\u0014\u0010\u0010\u001a\b\u0018\u00010\u000eR\u00020\u000fX\u0082\u000e¢\u0006\u0002\n\u0000R\u0010\u0010\u0011\u001a\u0004\u0018\u00010\u0012X\u0082\u000e¢\u0006\u0002\n\u0000R\u0010\u0010\u0013\u001a\u0004\u0018\u00010\u0014X\u0082\u000e¢\u0006\u0002\n\u0000R\u0010\u0010\u0015\u001a\u0004\u0018\u00010\u0016X\u0082\u000e¢\u0006\u0002\n\u0000R\u0010\u0010\u0017\u001a\u0004\u0018\u00010\u0018X\u0082\u000e¢\u0006\u0002\n\u0000R\u0010\u0010\u0019\u001a\u0004\u0018\u00010\u0014X\u0082\u000e¢\u0006\u0002\n\u0000R\u0010\u0010\u001a\u001a\u0004\u0018\u00010\u001bX\u0082\u000e¢\u0006\u0002\n\u0000R\u0010\u0010\u001c\u001a\u0004\u0018\u00010\u001dX\u0082\u000e¢\u0006\u0002\n\u0000R\u0010\u0010\u001e\u001a\u0004\u0018\u00010\nX\u0082\u000e¢\u0006\u0002\n\u0000R\u0010\u0010\u001f\u001a\u0004\u0018\u00010\nX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010 \u001a\u00020\bX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010!\u001a\u00020\bX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\"\u001a\u00020#X\u0082\u000e¢\u0006\u0002\n\u0000R\u0010\u0010$\u001a\u0004\u0018\u00010%X\u0082\u000e¢\u0006\u0002\n\u0000R\u0010\u0010&\u001a\u0004\u0018\u00010'X\u0082\u000e¢\u0006\u0002\n\u0000R\u0010\u0010(\u001a\u0004\u0018\u00010)X\u0082\u000e¢\u0006\u0002\n\u0000R\u001b\u0010*\u001a\u00020+8BX\u0082\u0084\u0002¢\u0006\f\n\u0004\b.\u0010/\u001a\u0004\b,\u0010-R\u0010\u00100\u001a\u0004\u0018\u000101X\u0082\u000e¢\u0006\u0002\n\u0000R\u0010\u00102\u001a\u0004\u0018\u000103X\u0082\u000e¢\u0006\u0002\n\u0000R\u0010\u00104\u001a\u0004\u0018\u000105X\u0082\u000e¢\u0006\u0002\n\u0000R\u0010\u00106\u001a\u0004\u0018\u000107X\u0082\u000e¢\u0006\u0002\n\u0000R\u0010\u00108\u001a\u0004\u0018\u000109X\u0082\u000e¢\u0006\u0002\n\u0000R\u0010\u0010:\u001a\u0004\u0018\u00010;X\u0082\u000e¢\u0006\u0002\n\u0000R\u0010\u0010<\u001a\u0004\u0018\u00010=X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010>\u001a\u00020?X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010@\u001a\u00020?X\u0082\u000e¢\u0006\u0002\n\u0000R\u0010\u0010A\u001a\u0004\u0018\u00010\nX\u0082\u000e¢\u0006\u0002\n\u0000R\u0016\u0010B\u001a\n\u0012\u0004\u0012\u00020\u0006\u0018\u00010CX\u0082\u000e¢\u0006\u0002\n\u0000R\u001c\u0010D\u001a\u0004\u0018\u00010EX\u0084\u000e¢\u0006\u000e\n\u0000\u001a\u0004\bF\u0010G\"\u0004\bH\u0010IR\u0018\u0010J\u001a\n\u0012\u0004\u0012\u00020\u0006\u0018\u00010KX\u0082\u000e¢\u0006\u0004\n\u0002\u0010LR\u0010\u0010M\u001a\u0004\u0018\u000107X\u0082\u000e¢\u0006\u0002\n\u0000R\u0010\u0010N\u001a\u0004\u0018\u00010\u0014X\u0082\u000e¢\u0006\u0002\n\u0000R\u0010\u0010O\u001a\u0004\u0018\u00010PX\u0082\u000e¢\u0006\u0002\n\u0000R\u001b\u0010Q\u001a\u00020R8BX\u0082\u0084\u0002¢\u0006\f\n\u0004\bU\u0010/\u001a\u0004\bS\u0010TR\u000e\u0010^\u001a\u000207X\u0082\u000e¢\u0006\u0002\n\u0000¨\u0006¢\u0001"}, d2 = {"Lcom/miui/pad/feature/notes/share/PadSnsEditActivity;", "Lcom/miui/common/base/BaseActivity;", "Lcom/miui/common/base/DialogManagerInterface;", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "()V", CallMethod.ARG_URI, "Landroid/net/Uri;", "bgIndex", "", NotesConstants.JSON_KEY_MODIFY_DATE, "", "mHomeView", "Landroid/widget/ImageView;", "mResTheme", "Landroid/content/res/Resources$Theme;", "Landroid/content/res/Resources;", "mDarkTheme", "mPasswordController", "Lcom/miui/notes/controller/PasswordController;", "mContentView", "Landroid/view/View;", "mTitleView", "Landroid/widget/TextView;", "mPreviewParent", "Landroid/widget/FrameLayout;", "mPreviewScroll", "mPreview", "Lcom/miui/notes/ui/view/WeiboPreviewView;", "mDialog", "Lmiuix/appcompat/app/ProgressDialog;", "mContent", "mTitle", "mShareType", "mThemeId", "mModifiedTime", "", "mRender", "Lcom/miui/richeditor/share/render/Render;", "mWorkingNote", "Lcom/miui/notes/model/WorkingNote;", "mShareGenerateTask", "Lcom/miui/pad/feature/notes/share/PadSnsEditActivity$LaunchSendActivityTask;", "mHandler", "Lcom/miui/pad/feature/notes/share/PadSnsEditActivity$PadSnsEditHandler;", "getMHandler", "()Lcom/miui/pad/feature/notes/share/PadSnsEditActivity$PadSnsEditHandler;", "mHandler$delegate", "Lkotlin/Lazy;", "mSaveToGalleryTask", "Lcom/miui/pad/feature/notes/share/PadSnsEditActivity$SaveToGalleryTask;", "mShareFile", "Ljava/io/File;", "mShareContainer", "Landroid/view/ViewGroup;", "mPreviewCallBack", "Ljava/lang/Runnable;", "mChooserView", "Landroidx/recyclerview/widget/RecyclerView;", "mChooserAdapter", "Lcom/miui/pad/feature/notes/share/PadResolverAdapter;", "mTheme", "Lcom/miui/richeditor/theme/Theme;", "mIsForceDark", "", "isInFullWindowGestureMode", "mSharePicBase64", "mShareImages", "", "mLocalBroadcastManager", "Landroidx/localbroadcastmanager/content/LocalBroadcastManager;", "getMLocalBroadcastManager", "()Landroidx/localbroadcastmanager/content/LocalBroadcastManager;", "setMLocalBroadcastManager", "(Landroidx/localbroadcastmanager/content/LocalBroadcastManager;)V", "mHybridEditorRenderImgUris", "", "[Landroid/net/Uri;", "mMamlRenderCallback", "mActionBarView", "mFinishReceiver", "Landroid/content/BroadcastReceiver;", "sharedViewModel", "Lcom/miui/notes/model/SharedViewModel;", "getSharedViewModel", "()Lcom/miui/notes/model/SharedViewModel;", "sharedViewModel$delegate", "onCreate", "", "savedInstanceState", "Landroid/os/Bundle;", "setFinishBroadcastReceiver", "initialize", "setTheme", "resid", "mRendTask", "applyTheme", "setActionBar", "theme", "setBackground", "updateWindowStatus", "updatePreview", "getResourceId", "useFloat", "updateAnimPreview", "animTheme", "Lcom/miui/notes/theme/AnimTheme;", "showProgressDialog", "message", "dismissProgressDialog", "onRequestPermissionsResult", "requestCode", "permissions", "grantResults", "", "(I[Ljava/lang/String;[I)V", "initShareContainer", "parent", "checkPermissionAndSaveToGallery", "file", "showShareContainer", "shareFile", "doShare", "intent", "Landroid/content/Intent;", "previewUri", "isXmanShareSupport", CoreConstants.CONTEXT_SCOPE_VALUE, "Landroid/content/Context;", "doSaveToGallery", "manageDialog", BaseAlarmAlertActivity.DIALOG_FRAGMENT_TAG, "Landroid/app/Dialog;", "dismissDialog", "isDialogShowing", "getThisActivity", "Landroid/app/Activity;", "getThisFragment", "Lmiuix/appcompat/app/Fragment;", "onResume", "onStart", "onStop", "onActivityResult", "resultCode", "data", "onDestroy", "onConfigurationChanged", "configuration", "Landroid/content/res/Configuration;", "isResponsiveEnabled", "onResponsiveLayout", "screenSpec", "Lmiuix/responsive/map/ScreenSpec;", "responsiveLayoutStateChanged", "setPreviewContent", "updatePreviewContentPadding", "deleteTempUri", "onBackPressed", "smallWindowAdapter", "PadSnsEditHandler", "LaunchSendActivityTask", "SaveToGalleryTask", "Companion", "app_PhoneGlobalRelease"}, k = 1, mv = {2, 2, 0}, xi = 48)
public final class PadSnsEditActivity extends BaseActivity implements DialogManagerInterface {

    /* JADX INFO: renamed from: Companion, reason: from kotlin metadata */
    public static final Companion INSTANCE = new Companion(null);
    private static final String EXTRA_KEY_IS_PRIVACY = "extra_is_privacy";
    private static final String EXTRA_KEY_MODIFIED_TIME = "extra_modified_time";
    private static final String EXTRA_KEY_SHARE_HAND_WRITE_BG_INDEX = "extra_share_hand_write_bg_index";
    private static final String EXTRA_KEY_SHARE_PIC = "extra_share_pic";
    private static final String EXTRA_KEY_SHARE_PICS = "extra_share_pics";
    private static final String EXTRA_KEY_TEXTSIZE = "extra_text_size";
    private static final String EXTRA_KEY_THEME = "extra_theme";
    private static final String EXTRA_KEY_TYPE = "extra_type";
    private static final String EXTRA_KEY_WORKING_NOTE = "extra_key_working_note";
    public static final int EXTRA_TYPE_AS_HYBRID = 3;
    public static final int EXTRA_TYPE_AS_IMAGE = 2;
    public static final int EXTRA_TYPE_AS_IMAGES = 4;
    public static final int EXTRA_TYPE_WEIBO = 1;
    private static final String INTENT_CLASSNAME_SHARE_WECHAT = "com.tencent.mm.ui.tools.ShareImgUI";
    private static final String INTENT_CLASSNAME_SHARE_WECHAT_MOMENTS = "com.tencent.mm.ui.tools.ShareToTimeLineUI";
    private static final String KEY_HYBRID_IMAGES = "extra_hybrid_images";
    private static final int POST_DELAYED = 500;
    private static final int REQUEST_CODE_AS_IMAGE = 2;
    private static final int REQUEST_CODE_WEIBO = 1;
    private static final String TAG = "PadSnsEditActivity";
    private int bgIndex;
    private boolean isInFullWindowGestureMode;
    private View mActionBarView;
    private PadResolverAdapter mChooserAdapter;
    private RecyclerView mChooserView;
    private String mContent;
    private View mContentView;
    private Resources.Theme mDarkTheme;
    private ProgressDialog mDialog;
    private BroadcastReceiver mFinishReceiver;
    private ImageView mHomeView;
    private Uri[] mHybridEditorRenderImgUris;
    private boolean mIsForceDark;
    private LocalBroadcastManager mLocalBroadcastManager;
    private Runnable mMamlRenderCallback;
    private long mModifiedTime;
    private PasswordController mPasswordController;
    private WeiboPreviewView mPreview;
    private Runnable mPreviewCallBack;
    private FrameLayout mPreviewParent;
    private View mPreviewScroll;
    private Render mRender;
    private Resources.Theme mResTheme;
    private SaveToGalleryTask mSaveToGalleryTask;
    private ViewGroup mShareContainer;
    private File mShareFile;
    private LaunchSendActivityTask mShareGenerateTask;
    private List<? extends Uri> mShareImages;
    private String mSharePicBase64;
    private int mShareType;
    private Theme mTheme;
    private int mThemeId;
    private String mTitle;
    private TextView mTitleView;
    private WorkingNote mWorkingNote;
    private String modifyDate;
    private Uri uri;

    /* JADX INFO: renamed from: mHandler$delegate, reason: from kotlin metadata */
    private final Lazy mHandler = LazyKt.lazy(new Function0() { // from class: com.miui.pad.feature.notes.share.PadSnsEditActivity$$ExternalSyntheticLambda13
        @Override // kotlin.jvm.functions.Function0
        public final Object invoke() {
            return PadSnsEditActivity.mHandler_delegate$lambda$0();
        }
    });

    /* JADX INFO: renamed from: sharedViewModel$delegate, reason: from kotlin metadata */
    private final Lazy sharedViewModel = LazyKt.lazy(new Function0() { // from class: com.miui.pad.feature.notes.share.PadSnsEditActivity$$ExternalSyntheticLambda14
        @Override // kotlin.jvm.functions.Function0
        public final Object invoke() {
            return PadSnsEditActivity.sharedViewModel_delegate$lambda$1(this.f$0);
        }
    });
    private Runnable mRendTask = new Runnable() { // from class: com.miui.pad.feature.notes.share.PadSnsEditActivity$$ExternalSyntheticLambda1
        @Override // java.lang.Runnable
        public final void run() {
            PadSnsEditActivity.mRendTask$lambda$3(this.f$0);
        }
    };

    @Override // com.miui.common.base.DialogManagerInterface
    public boolean dismissDialog() {
        return false;
    }

    @Override // com.miui.common.base.DialogManagerInterface
    public Fragment getThisFragment() {
        return null;
    }

    @Override // com.miui.common.base.DialogManagerInterface
    public boolean isDialogShowing() {
        return false;
    }

    @Override // miuix.appcompat.app.AppCompatActivity
    protected boolean isResponsiveEnabled() {
        return true;
    }

    @Override // com.miui.common.base.DialogManagerInterface
    public void manageDialog(Dialog dialog) {
        Intrinsics.checkNotNullParameter(dialog, "dialog");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final PadSnsEditHandler getMHandler() {
        return (PadSnsEditHandler) this.mHandler.getValue();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final PadSnsEditHandler mHandler_delegate$lambda$0() {
        Looper mainLooper = Looper.getMainLooper();
        Intrinsics.checkNotNullExpressionValue(mainLooper, "getMainLooper(...)");
        return new PadSnsEditHandler(mainLooper);
    }

    protected final LocalBroadcastManager getMLocalBroadcastManager() {
        return this.mLocalBroadcastManager;
    }

    protected final void setMLocalBroadcastManager(LocalBroadcastManager localBroadcastManager) {
        this.mLocalBroadcastManager = localBroadcastManager;
    }

    private final SharedViewModel getSharedViewModel() {
        return (SharedViewModel) this.sharedViewModel.getValue();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final SharedViewModel sharedViewModel_delegate$lambda$1(PadSnsEditActivity padSnsEditActivity) {
        return (SharedViewModel) new ViewModelProvider(padSnsEditActivity).get(SharedViewModel.class);
    }

    /* JADX INFO: compiled from: PadSnsEditActivity.kt */
    @Metadata(d1 = {"\u0000\u0012\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\u0018\u00002\u00020\u0001B\u000f\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0004\b\u0004\u0010\u0005¨\u0006\u0006"}, d2 = {"Lcom/miui/pad/feature/notes/share/PadSnsEditActivity$PadSnsEditHandler;", "Landroid/os/Handler;", "mLoop", "Landroid/os/Looper;", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "(Landroid/os/Looper;)V", "app_PhoneGlobalRelease"}, k = 1, mv = {2, 2, 0}, xi = 48)
    public static final class PadSnsEditHandler extends Handler {
        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public PadSnsEditHandler(Looper mLoop) {
            super(mLoop);
            Intrinsics.checkNotNullParameter(mLoop, "mLoop");
        }
    }

    @Override // com.miui.common.base.BaseActivity, miuix.appcompat.app.AppCompatActivity, androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    public void onCreate(Bundle savedInstanceState) {
        Theme.StatusBarStyle statusBarStyle;
        if (getIntent() != null) {
            boolean z = false;
            this.mThemeId = 0;
            Theme theme = ResourceManager.getTheme(0);
            this.mTheme = theme;
            if (theme != null && (statusBarStyle = theme.getStatusBarStyle(this)) != null && statusBarStyle.mStatusBar == 2) {
                z = true;
            }
            this.mIsForceDark = z;
            if (z) {
                setTheme(R.style.NoteTheme_Sns_Dark);
            }
        }
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pad_sns_edit_activity);
        String string = getResources().getString(R.string.dlg_info_creating_message);
        Intrinsics.checkNotNullExpressionValue(string, "getString(...)");
        showProgressDialog(string);
        initialize();
        this.isInFullWindowGestureMode = UIUtils.isInFullWindowGestureMode(this);
        UIUtils.setNavigationTrans(this);
        updateWindowStatus();
        setFinishBroadcastReceiver();
    }

    private final void setFinishBroadcastReceiver() {
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(PasswordController.ACTIN_CANCEL);
        BroadcastReceiver broadcastReceiver = new BroadcastReceiver() { // from class: com.miui.pad.feature.notes.share.PadSnsEditActivity.setFinishBroadcastReceiver.1
            @Override // android.content.BroadcastReceiver
            public void onReceive(Context context, Intent intent) {
                Intrinsics.checkNotNullParameter(context, "context");
                Intrinsics.checkNotNullParameter(intent, "intent");
                PadSnsEditActivity.this.finish();
            }
        };
        this.mFinishReceiver = broadcastReceiver;
        registerReceiver(broadcastReceiver, intentFilter, 2);
    }

    private final void initialize() {
        Intent intent = getIntent();
        if (intent == null) {
            Logger.INSTANCE.e(TAG, "intent is null");
            finish();
            return;
        }
        this.mContent = intent.getStringExtra("android.intent.extra.TEXT");
        this.mTitle = intent.getStringExtra("android.intent.extra.TITLE");
        if (TextUtils.isEmpty(this.mContent) && TextUtils.isEmpty(this.mTitle)) {
            Logger.INSTANCE.e(TAG, "text in intent is empty");
            finish();
            return;
        }
        this.mShareType = intent.getIntExtra(EXTRA_KEY_TYPE, 1);
        this.mModifiedTime = intent.getLongExtra(EXTRA_KEY_MODIFIED_TIME, 0L);
        Object data = MindShareDataHolder.getInstance().getData(EXTRA_KEY_WORKING_NOTE);
        if (data instanceof WorkingNote) {
            this.mWorkingNote = (WorkingNote) data;
        }
        if (getSharedViewModel().getSharePicBase64() == null) {
            Logger.INSTANCE.d(TAG, "initialize set sharePicBase64");
            Object data2 = MindShareDataHolder.getInstance().getData(EXTRA_KEY_SHARE_PIC);
            this.mSharePicBase64 = data2 instanceof String ? (String) data2 : null;
            getSharedViewModel().setSharePicBase64(this.mSharePicBase64);
        } else {
            Logger.INSTANCE.d(TAG, "initialize recover sharePicBase64");
            this.mSharePicBase64 = getSharedViewModel().getSharePicBase64();
        }
        this.bgIndex = intent.getIntExtra(EXTRA_KEY_SHARE_HAND_WRITE_BG_INDEX, 0);
        this.modifyDate = intent.getStringExtra(IntentCompat.EXTRA_TIME);
        this.mShareImages = intent.getParcelableArrayListExtra(EXTRA_KEY_SHARE_PICS);
        ViewGroup viewGroup = (ViewGroup) findViewById(R.id.share_container);
        this.mShareContainer = viewGroup;
        Intrinsics.checkNotNull(viewGroup);
        viewGroup.setVisibility(8);
        ViewGroup viewGroup2 = this.mShareContainer;
        Intrinsics.checkNotNull(viewGroup2);
        initShareContainer(viewGroup2);
        this.mPreviewParent = (FrameLayout) findViewById(R.id.preview_parent);
        this.mPreviewScroll = findViewById(R.id.sv_preview);
        FrameLayout frameLayout = this.mPreviewParent;
        Intrinsics.checkNotNull(frameLayout);
        frameLayout.setBackgroundColor(0);
        View view = this.mPreviewScroll;
        Intrinsics.checkNotNull(view);
        view.setBackgroundColor(0);
        this.mPreview = (WeiboPreviewView) findViewById(R.id.preview);
        this.mPreviewCallBack = new Runnable() { // from class: com.miui.pad.feature.notes.share.PadSnsEditActivity$$ExternalSyntheticLambda5
            @Override // java.lang.Runnable
            public final void run() {
                PadSnsEditActivity.initialize$lambda$2(this.f$0);
            }
        };
        WeiboPreviewView weiboPreviewView = this.mPreview;
        Intrinsics.checkNotNull(weiboPreviewView);
        weiboPreviewView.post(this.mPreviewCallBack);
        applyTheme();
        WeiboPreviewView weiboPreviewView2 = this.mPreview;
        Intrinsics.checkNotNull(weiboPreviewView2);
        weiboPreviewView2.invalidate();
        if (RomUtils.isFoldDevice()) {
            setFloatingWindowMode(false);
        }
        setPreviewContent();
        this.mContentView = findViewById(R.id.content_view);
        if (intent.getBooleanExtra(EXTRA_KEY_IS_PRIVACY, false)) {
            PasswordController passwordController = new PasswordController(this, true);
            this.mPasswordController = passwordController;
            Intrinsics.checkNotNull(passwordController);
            passwordController.setLockSateListener(new PasswordController.SimpleLockStateListener() { // from class: com.miui.pad.feature.notes.share.PadSnsEditActivity.initialize.2
                @Override // com.miui.notes.controller.PasswordController.SimpleLockStateListener, com.miui.notes.controller.PasswordController.LockStateListener
                public void onLockStateChanged(PasswordController controller, int lockState) {
                    Intrinsics.checkNotNullParameter(controller, "controller");
                    super.onLockStateChanged(controller, lockState);
                    if (lockState == 0) {
                        View view2 = PadSnsEditActivity.this.mContentView;
                        Intrinsics.checkNotNull(view2);
                        view2.setVisibility(8);
                    } else {
                        if (lockState != 2) {
                            return;
                        }
                        View view3 = PadSnsEditActivity.this.mContentView;
                        Intrinsics.checkNotNull(view3);
                        view3.setVisibility(0);
                    }
                }
            }, 2);
            getWindow().addFlags(8192);
        }
        smallWindowAdapter();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void initialize$lambda$2(PadSnsEditActivity padSnsEditActivity) {
        Logger.INSTANCE.d(TAG, "mPreviewCallBack run");
        Theme theme = ResourceManager.getTheme(padSnsEditActivity.mThemeId);
        if (theme == null) {
            theme = ResourceManager.getTheme(0);
        }
        if (theme instanceof AnimTheme) {
            padSnsEditActivity.updateAnimPreview((AnimTheme) theme);
        } else {
            padSnsEditActivity.updatePreview();
        }
    }

    @Override // android.app.Activity, android.view.ContextThemeWrapper, android.content.ContextWrapper, android.content.Context
    public void setTheme(int resid) {
        if (RomUtils.isFoldDevice()) {
            if (UIUtils.isAboveAndroidR()) {
                setTranslucent(false);
            }
            resid = R.style.NoteTheme_PadSns_FOLD;
        }
        super.setTheme(resid);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void mRendTask$lambda$3(PadSnsEditActivity padSnsEditActivity) {
        Logger.INSTANCE.d(TAG, "mRender run");
        if (padSnsEditActivity.mRender == null) {
            Logger.INSTANCE.e(TAG, "mRender should not be null");
            return;
        }
        LaunchSendActivityTask launchSendActivityTask = padSnsEditActivity.mShareGenerateTask;
        if (launchSendActivityTask != null) {
            Intrinsics.checkNotNull(launchSendActivityTask);
            launchSendActivityTask.cancel(true);
            padSnsEditActivity.mShareGenerateTask = null;
        }
        LaunchSendActivityTask launchSendActivityTask2 = new LaunchSendActivityTask(padSnsEditActivity, padSnsEditActivity.getIntent().getStringExtra(SnsEditActivity.KEY_AIGC_META));
        padSnsEditActivity.mShareGenerateTask = launchSendActivityTask2;
        Intrinsics.checkNotNull(launchSendActivityTask2);
        launchSendActivityTask2.execute(new Unit[0]);
    }

    private final void applyTheme() {
        Theme theme = this.mTheme;
        Intrinsics.checkNotNull(theme);
        setActionBar(theme);
        setBackground();
    }

    private final void setActionBar(Theme theme) {
        PadSnsEditActivity padSnsEditActivity = this;
        theme.getStatusBarStyle(padSnsEditActivity).apply(this);
        ActionBar appCompatActionBar = getAppCompatActionBar();
        Intrinsics.checkNotNull(appCompatActionBar);
        appCompatActionBar.setBackgroundDrawable(getDrawable(R.drawable.v8_placeholder));
        ActionBar appCompatActionBar2 = getAppCompatActionBar();
        Intrinsics.checkNotNull(appCompatActionBar2);
        appCompatActionBar2.setDisplayHomeAsUpEnabled(false);
        ActionBar appCompatActionBar3 = getAppCompatActionBar();
        Intrinsics.checkNotNull(appCompatActionBar3);
        appCompatActionBar3.setDisplayShowTitleEnabled(false);
        ActionBar appCompatActionBar4 = getAppCompatActionBar();
        Intrinsics.checkNotNull(appCompatActionBar4);
        appCompatActionBar4.setDisplayShowCustomEnabled(true);
        if (this.mActionBarView == null) {
            View decorView = getWindow().getDecorView();
            Intrinsics.checkNotNull(decorView, "null cannot be cast to non-null type android.view.ViewGroup");
            this.mActionBarView = UIUtils.inflateView((ViewGroup) decorView, R.layout.sns_edit_actionbar);
        }
        ActionBar appCompatActionBar5 = getAppCompatActionBar();
        Intrinsics.checkNotNull(appCompatActionBar5);
        appCompatActionBar5.setCustomView(this.mActionBarView, new androidx.appcompat.app.ActionBar.LayoutParams(-1, -2));
        ActionBar appCompatActionBar6 = getAppCompatActionBar();
        Intrinsics.checkNotNull(appCompatActionBar6);
        this.mTitleView = (TextView) appCompatActionBar6.getCustomView().findViewById(R.id.title);
        ActionBar appCompatActionBar7 = getAppCompatActionBar();
        Intrinsics.checkNotNull(appCompatActionBar7);
        ImageView imageView = (ImageView) appCompatActionBar7.getCustomView().findViewById(R.id.home);
        this.mHomeView = imageView;
        AccessibilityDelegateUtil.setButtonClassAccessibilityDelegate(imageView);
        ImageView imageView2 = this.mHomeView;
        Intrinsics.checkNotNull(imageView2);
        imageView2.setOnClickListener(new View.OnClickListener() { // from class: com.miui.pad.feature.notes.share.PadSnsEditActivity$$ExternalSyntheticLambda12
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f$0.onBackPressed();
            }
        });
        Folme.useAt(this.mHomeView).touch().handleTouchOf(this.mHomeView, new AnimConfig[0]);
        ImageView imageView3 = this.mHomeView;
        Intrinsics.checkNotNull(imageView3);
        UIUtils.setFolmeHoverEffect(imageView3);
        if (this.mIsForceDark) {
            Resources.Theme themeNewTheme = getResources().newTheme();
            this.mDarkTheme = themeNewTheme;
            Intrinsics.checkNotNull(themeNewTheme);
            themeNewTheme.applyStyle(R.style.NoteTheme_Edit_Dark, true);
            ImageView imageView4 = this.mHomeView;
            Intrinsics.checkNotNull(imageView4);
            imageView4.setImageDrawable(getResources().getDrawable(getResourceId(true), this.mDarkTheme));
            TextView textView = this.mTitleView;
            Intrinsics.checkNotNull(textView);
            textView.setTextColor(getColor(R.color.sns_title_text_color_dark));
        } else {
            this.mResTheme = theme.getActionBarStyle(padSnsEditActivity).getResTheme();
            ImageView imageView5 = this.mHomeView;
            Intrinsics.checkNotNull(imageView5);
            imageView5.setImageDrawable(getResources().getDrawable(getResourceId(true), this.mResTheme));
            TextView textView2 = this.mTitleView;
            Intrinsics.checkNotNull(textView2);
            textView2.setTextColor(getResources().getColor(R.color.action_button_theme_color, this.mResTheme));
        }
        TextView textView3 = this.mTitleView;
        Intrinsics.checkNotNull(textView3);
        textView3.setText(R.string.sns_title_edit);
    }

    private final void setBackground() {
        FrameLayout frameLayout = this.mPreviewParent;
        Intrinsics.checkNotNull(frameLayout);
        View actionBarOverlayLayout = UIUtils.getActionBarOverlayLayout(frameLayout);
        Intrinsics.checkNotNull(actionBarOverlayLayout, "null cannot be cast to non-null type android.view.ViewGroup");
        ((ViewGroup) actionBarOverlayLayout).setBackgroundColor(getColor(R.color.pad_note_sns_share_bg));
    }

    private final void updateWindowStatus() {
        if (UIUtils.isNightMode(this)) {
            getWindow().getDecorView().setSystemUiVisibility(1280);
        } else {
            getWindow().getDecorView().setSystemUiVisibility(R2.dimen.passport_dialog_title_vertical_padding);
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    private final void updatePreview() {
        com.miui.common.tool.Logger.INSTANCE.d(TAG, "updatePreview");
        Theme theme = ResourceManager.getTheme(this.mThemeId);
        if (theme == null) {
            theme = ResourceManager.getTheme(0);
        }
        Intrinsics.checkNotNull(theme);
        Theme.ShareStyle shareStyle = theme.getShareStyle(BaseNoteApp.INSTANCE.getInstance(), this.mModifiedTime);
        int i = (theme.getId() == 0 && UIUtils.isNightMode(this)) ? R.drawable.ic_richtext_share_checkbox_u_light : R.drawable.ic_richtext_share_checkbox_u;
        int i2 = this.mShareType;
        if (i2 == 3) {
            if (TextUtils.isEmpty(this.mSharePicBase64)) {
                this.mRender = new RenderBuilder(this).setTitle(TextUtils.isEmpty(this.mTitle) ? "" : this.mTitle).setContent(this.mContent).setThemeId(this.mThemeId).setStyle(shareStyle).setCheckRes(R.drawable.ic_richtext_share_checkbox_c).setUnCheckRes(i).build();
            } else {
                ImageUtils imageUtils = ImageUtils.INSTANCE;
                String str = this.mSharePicBase64;
                Intrinsics.checkNotNull(str);
                Bitmap bitmapBase64ToBitmap = imageUtils.base64ToBitmap(str);
                PadSnsEditActivity padSnsEditActivity = this;
                this.uri = SnsEditActivity.createTempUri(padSnsEditActivity, bitmapBase64ToBitmap, true);
                this.mRender = new RenderBuilder(padSnsEditActivity).setThemeId(this.mThemeId).setStyle(shareStyle).buildFromHybrid(this.uri);
            }
        } else if (i2 == 4) {
            if (this.mShareImages == null) {
                return;
            } else {
                this.mRender = new PadRenderBuilder(this).setStyle(shareStyle).setTitle(this.mTitle).buildHandWrite(this.mShareImages, this.bgIndex, this.modifyDate);
            }
        } else if (TextUtils.isEmpty(this.mSharePicBase64)) {
            this.mRender = new PadRenderBuilder(this).setTitle(TextUtils.isEmpty(this.mTitle) ? "" : this.mTitle).setContent(this.mContent).setThemeId(this.mThemeId).setStyle(shareStyle).setCheckRes(R.drawable.ic_richtext_share_checkbox_c).setUnCheckRes(i).build();
        } else {
            ImageUtils imageUtils2 = ImageUtils.INSTANCE;
            String str2 = this.mSharePicBase64;
            Intrinsics.checkNotNull(str2);
            Bitmap bitmapBase64ToBitmap2 = imageUtils2.base64ToBitmap2(str2);
            PadSnsEditActivity padSnsEditActivity2 = this;
            this.uri = SnsEditActivity.createTempUri(padSnsEditActivity2, bitmapBase64ToBitmap2, false);
            this.mRender = new PadRenderBuilder(padSnsEditActivity2).buildFromUri(this.uri);
            if (bitmapBase64ToBitmap2 != null && !bitmapBase64ToBitmap2.isRecycled()) {
                bitmapBase64ToBitmap2.recycle();
            }
        }
        if (this.mRender != null) {
            FrameLayout frameLayout = this.mPreviewParent;
            Intrinsics.checkNotNull(frameLayout);
            frameLayout.post(new Runnable() { // from class: com.miui.pad.feature.notes.share.PadSnsEditActivity$$ExternalSyntheticLambda4
                @Override // java.lang.Runnable
                public final void run() {
                    PadSnsEditActivity.updatePreview$lambda$5(this.f$0);
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void updatePreview$lambda$5(PadSnsEditActivity padSnsEditActivity) {
        padSnsEditActivity.mRendTask.run();
        WeiboPreviewView weiboPreviewView = padSnsEditActivity.mPreview;
        Intrinsics.checkNotNull(weiboPreviewView);
        weiboPreviewView.setRender(padSnsEditActivity.mRender);
        WeiboPreviewView weiboPreviewView2 = padSnsEditActivity.mPreview;
        Intrinsics.checkNotNull(weiboPreviewView2);
        weiboPreviewView2.requestLayout();
    }

    private final int getResourceId(boolean useFloat) {
        return (RomUtils.isPadDevice() && useFloat) ? R.drawable.ic_pad_actionbar_back : R.drawable.ic_action_back;
    }

    private final void updateAnimPreview(AnimTheme animTheme) {
        final Theme.ShareStyle shareStyle = animTheme.getShareStyle(BaseNoteApp.INSTANCE.getInstance(), this.mModifiedTime);
        final int i = (animTheme.getId() == 0 && UIUtils.isNightMode(this)) ? R.drawable.ic_richtext_share_checkbox_u_light : R.drawable.ic_richtext_share_checkbox_u;
        final MamlView backgroundViewForSns = animTheme.getBackgroundViewForSns(getApplicationContext());
        Intrinsics.checkNotNull(backgroundViewForSns);
        backgroundViewForSns.init();
        backgroundViewForSns.sendCommand("init");
        final FrameLayout frameLayout = new FrameLayout(this);
        frameLayout.addView(backgroundViewForSns, new FrameLayout.LayoutParams(-1, -1));
        frameLayout.setAlpha(0.0f);
        FrameLayout frameLayout2 = this.mPreviewParent;
        Intrinsics.checkNotNull(frameLayout2);
        frameLayout2.addView(frameLayout, 0, new FrameLayout.LayoutParams(-1, -1));
        int i2 = this.mShareType;
        final int i3 = R.drawable.ic_richtext_share_checkbox_c;
        if (i2 == 3) {
            this.mMamlRenderCallback = new Runnable() { // from class: com.miui.pad.feature.notes.share.PadSnsEditActivity$$ExternalSyntheticLambda8
                @Override // java.lang.Runnable
                public final void run() {
                    PadSnsEditActivity.updateAnimPreview$lambda$6(this.f$0, frameLayout);
                }
            };
            FrameLayout frameLayout3 = this.mPreviewParent;
            Intrinsics.checkNotNull(frameLayout3);
            frameLayout3.postDelayed(new Runnable() { // from class: com.miui.pad.feature.notes.share.PadSnsEditActivity$$ExternalSyntheticLambda9
                @Override // java.lang.Runnable
                public final void run() {
                    PadSnsEditActivity.updateAnimPreview$lambda$7(this.f$0, shareStyle, i3, i, backgroundViewForSns);
                }
            }, 400L);
            return;
        }
        FrameLayout frameLayout4 = this.mPreviewParent;
        Intrinsics.checkNotNull(frameLayout4);
        frameLayout4.postDelayed(new Runnable() { // from class: com.miui.pad.feature.notes.share.PadSnsEditActivity$$ExternalSyntheticLambda10
            @Override // java.lang.Runnable
            public final void run() {
                PadSnsEditActivity.updateAnimPreview$lambda$9(this.f$0, shareStyle, i3, i, backgroundViewForSns, frameLayout);
            }
        }, 100L);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void updateAnimPreview$lambda$6(PadSnsEditActivity padSnsEditActivity, FrameLayout frameLayout) {
        if (padSnsEditActivity.mRender != null) {
            padSnsEditActivity.mRendTask.run();
            WeiboPreviewView weiboPreviewView = padSnsEditActivity.mPreview;
            Intrinsics.checkNotNull(weiboPreviewView);
            weiboPreviewView.setRender(padSnsEditActivity.mRender);
            WeiboPreviewView weiboPreviewView2 = padSnsEditActivity.mPreview;
            Intrinsics.checkNotNull(weiboPreviewView2);
            weiboPreviewView2.requestLayout();
            FrameLayout frameLayout2 = padSnsEditActivity.mPreviewParent;
            Intrinsics.checkNotNull(frameLayout2);
            frameLayout2.removeView(frameLayout);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void updateAnimPreview$lambda$7(PadSnsEditActivity padSnsEditActivity, Theme.ShareStyle shareStyle, int i, int i2, MamlView mamlView) {
        padSnsEditActivity.mRender = new RenderBuilder(padSnsEditActivity).setTitle(TextUtils.isEmpty(padSnsEditActivity.mTitle) ? "" : padSnsEditActivity.mTitle).setContent(padSnsEditActivity.mContent).setThemeId(padSnsEditActivity.mThemeId).setStyle(shareStyle).setCheckRes(i).setUnCheckRes(i2).buildWithCallback(mamlView, padSnsEditActivity.mMamlRenderCallback);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void updateAnimPreview$lambda$9(final PadSnsEditActivity padSnsEditActivity, Theme.ShareStyle shareStyle, int i, int i2, MamlView mamlView, final FrameLayout frameLayout) {
        padSnsEditActivity.mRender = new PadRenderBuilder(padSnsEditActivity).setTitle(TextUtils.isEmpty(padSnsEditActivity.mTitle) ? "" : padSnsEditActivity.mTitle).setContent(padSnsEditActivity.mContent).setThemeId(padSnsEditActivity.mThemeId).setStyle(shareStyle).setCheckRes(i).setUnCheckRes(i2).buildWithCallback(mamlView, new Runnable() { // from class: com.miui.pad.feature.notes.share.PadSnsEditActivity$$ExternalSyntheticLambda6
            @Override // java.lang.Runnable
            public final void run() {
                PadSnsEditActivity.updateAnimPreview$lambda$9$lambda$8(this.f$0, frameLayout);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void updateAnimPreview$lambda$9$lambda$8(PadSnsEditActivity padSnsEditActivity, FrameLayout frameLayout) {
        if (padSnsEditActivity.mRender != null) {
            padSnsEditActivity.mRendTask.run();
            WeiboPreviewView weiboPreviewView = padSnsEditActivity.mPreview;
            Intrinsics.checkNotNull(weiboPreviewView);
            weiboPreviewView.setRender(padSnsEditActivity.mRender);
            WeiboPreviewView weiboPreviewView2 = padSnsEditActivity.mPreview;
            Intrinsics.checkNotNull(weiboPreviewView2);
            weiboPreviewView2.requestLayout();
            FrameLayout frameLayout2 = padSnsEditActivity.mPreviewParent;
            Intrinsics.checkNotNull(frameLayout2);
            frameLayout2.removeView(frameLayout);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void showProgressDialog(final String message) {
        ProgressDialog progressDialog = this.mDialog;
        if (progressDialog != null) {
            Intrinsics.checkNotNull(progressDialog);
            if (progressDialog.isShowing()) {
                return;
            }
        }
        runOnUiThread(new Runnable() { // from class: com.miui.pad.feature.notes.share.PadSnsEditActivity$$ExternalSyntheticLambda3
            @Override // java.lang.Runnable
            public final void run() {
                PadSnsEditActivity.showProgressDialog$lambda$11(this.f$0, message);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void showProgressDialog$lambda$11(final PadSnsEditActivity padSnsEditActivity, String str) {
        ProgressDialog progressDialog = new ProgressDialog(padSnsEditActivity);
        padSnsEditActivity.mDialog = progressDialog;
        Intrinsics.checkNotNull(progressDialog);
        progressDialog.setOnCancelListener(new DialogInterface.OnCancelListener() { // from class: com.miui.pad.feature.notes.share.PadSnsEditActivity$$ExternalSyntheticLambda11
            @Override // android.content.DialogInterface.OnCancelListener
            public final void onCancel(DialogInterface dialogInterface) {
                PadSnsEditActivity.showProgressDialog$lambda$11$lambda$10(this.f$0, dialogInterface);
            }
        });
        ProgressDialog progressDialog2 = padSnsEditActivity.mDialog;
        Intrinsics.checkNotNull(progressDialog2);
        progressDialog2.setMessage(str);
        ProgressDialog progressDialog3 = padSnsEditActivity.mDialog;
        Intrinsics.checkNotNull(progressDialog3);
        progressDialog3.setIndeterminate(true);
        ProgressDialog progressDialog4 = padSnsEditActivity.mDialog;
        Intrinsics.checkNotNull(progressDialog4);
        progressDialog4.setCanceledOnTouchOutside(false);
        ProgressDialog progressDialog5 = padSnsEditActivity.mDialog;
        Intrinsics.checkNotNull(progressDialog5);
        progressDialog5.show();
        ProgressDialog progressDialog6 = padSnsEditActivity.mDialog;
        Intrinsics.checkNotNull(progressDialog6);
        ProgressDialog progressDialog7 = padSnsEditActivity.mDialog;
        Intrinsics.checkNotNull(progressDialog7);
        UIUtils.setDialogFullScreen(progressDialog6, UIUtils.getRealNavigationBarHeight(progressDialog7.getWindow()));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void showProgressDialog$lambda$11$lambda$10(PadSnsEditActivity padSnsEditActivity, DialogInterface dialogInterface) {
        WeiboPreviewView weiboPreviewView = padSnsEditActivity.mPreview;
        Intrinsics.checkNotNull(weiboPreviewView);
        weiboPreviewView.removeCallbacks(padSnsEditActivity.mPreviewCallBack);
        LaunchSendActivityTask launchSendActivityTask = padSnsEditActivity.mShareGenerateTask;
        if (launchSendActivityTask != null) {
            Intrinsics.checkNotNull(launchSendActivityTask);
            launchSendActivityTask.cancel(true);
        }
        padSnsEditActivity.finish();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void dismissProgressDialog() {
        runOnUiThread(new Runnable() { // from class: com.miui.pad.feature.notes.share.PadSnsEditActivity$$ExternalSyntheticLambda2
            @Override // java.lang.Runnable
            public final void run() {
                PadSnsEditActivity.dismissProgressDialog$lambda$12(this.f$0);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void dismissProgressDialog$lambda$12(PadSnsEditActivity padSnsEditActivity) {
        ProgressDialog progressDialog = padSnsEditActivity.mDialog;
        if (progressDialog != null) {
            try {
                Intrinsics.checkNotNull(progressDialog);
                progressDialog.dismiss();
            } catch (Exception e) {
                e.printStackTrace();
            }
            padSnsEditActivity.mDialog = null;
        }
        WeiboPreviewView weiboPreviewView = padSnsEditActivity.mPreview;
        Intrinsics.checkNotNull(weiboPreviewView);
        weiboPreviewView.invalidate();
    }

    @Override // androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, android.app.Activity
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        Intrinsics.checkNotNullParameter(permissions, "permissions");
        Intrinsics.checkNotNullParameter(grantResults, "grantResults");
        if (requestCode == 4) {
            PermissionUtils.processOnStoragePermission(this, permissions, grantResults);
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    private final void initShareContainer(View parent) {
        RecyclerView recyclerView = (RecyclerView) parent.findViewById(R.id.chooser_recycler);
        this.mChooserView = recyclerView;
        Intrinsics.checkNotNull(recyclerView);
        PadSnsEditActivity padSnsEditActivity = this;
        recyclerView.setLayoutManager(new LinearLayoutManager(padSnsEditActivity, 0, false));
        PadResolverAdapter padResolverAdapter = new PadResolverAdapter(padSnsEditActivity, null, this.mTheme, true);
        this.mChooserAdapter = padResolverAdapter;
        Intrinsics.checkNotNull(padResolverAdapter);
        padResolverAdapter.setOnIntentSelectedListener(new PadResolverAdapter.OnImageSelectedListener() { // from class: com.miui.pad.feature.notes.share.PadSnsEditActivity.initShareContainer.1
            @Override // com.miui.pad.feature.notes.share.PadResolverAdapter.OnImageSelectedListener
            public void onSaveToGallery() {
                if (PadSnsEditActivity.this.mShareFile == null) {
                    return;
                }
                PadSnsEditActivity padSnsEditActivity2 = PadSnsEditActivity.this;
                File file = padSnsEditActivity2.mShareFile;
                Intrinsics.checkNotNull(file);
                padSnsEditActivity2.checkPermissionAndSaveToGallery(file);
            }

            @Override // com.miui.pad.feature.notes.share.PadResolverAdapter.OnIntentSelectedListener
            public void onIntentSelected(Intent i) {
                Intrinsics.checkNotNullParameter(i, "i");
                if (PadSnsEditActivity.this.mShareFile != null) {
                    Uri uriFromFile = Uri.fromFile(PadSnsEditActivity.this.mShareFile);
                    PadSnsEditActivity padSnsEditActivity2 = PadSnsEditActivity.this;
                    Intrinsics.checkNotNull(uriFromFile);
                    padSnsEditActivity2.doShare(i, uriFromFile);
                }
            }
        });
        RecyclerView recyclerView2 = this.mChooserView;
        Intrinsics.checkNotNull(recyclerView2);
        recyclerView2.setAdapter(this.mChooserAdapter);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void checkPermissionAndSaveToGallery(final File file) {
        if (Utils.isAboveSDK33()) {
            doSaveToGallery(file);
        } else if (PermissionUtils.supportNewPermissionStyle() && !PreferenceUtils.isCTAAccepted(getApplicationContext())) {
            PermissionUtils.showCtaDialogWithCallback(this, new PermissionUtils.AcceptListener() { // from class: com.miui.pad.feature.notes.share.PadSnsEditActivity.checkPermissionAndSaveToGallery.1
                @Override // com.miui.notes.tool.PermissionUtils.AcceptListener
                public void reject() {
                }

                @Override // com.miui.notes.tool.PermissionUtils.AcceptListener
                public void accept() {
                    PadSnsEditActivity padSnsEditActivity = PadSnsEditActivity.this;
                    final PadSnsEditActivity padSnsEditActivity2 = PadSnsEditActivity.this;
                    final File file2 = file;
                    PermissionUtils.requestOnStoragePermission(padSnsEditActivity, new PermissionUtils.AcceptListener() { // from class: com.miui.pad.feature.notes.share.PadSnsEditActivity$checkPermissionAndSaveToGallery$1$accept$1
                        @Override // com.miui.notes.tool.PermissionUtils.AcceptListener
                        public void accept() {
                            padSnsEditActivity2.doSaveToGallery(file2);
                        }

                        @Override // com.miui.notes.tool.PermissionUtils.AcceptListener
                        public void reject() {
                            Toast.makeText(BaseNoteApp.INSTANCE.getInstance(), R.string.toast_refuse_to_use_external_storage, 0).show();
                        }
                    });
                }
            });
        } else {
            PermissionUtils.requestOnStoragePermission(this, new PermissionUtils.AcceptListener() { // from class: com.miui.pad.feature.notes.share.PadSnsEditActivity.checkPermissionAndSaveToGallery.2
                @Override // com.miui.notes.tool.PermissionUtils.AcceptListener
                public void accept() {
                    PadSnsEditActivity.this.doSaveToGallery(file);
                }

                @Override // com.miui.notes.tool.PermissionUtils.AcceptListener
                public void reject() {
                    Toast.makeText(BaseNoteApp.INSTANCE.getInstance(), R.string.toast_refuse_to_use_external_storage, 0).show();
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void showShareContainer(File shareFile) {
        this.mShareFile = shareFile;
        Intent intent = new Intent("android.intent.action.SEND");
        intent.setType("image/jpeg");
        PadResolverAdapter padResolverAdapter = this.mChooserAdapter;
        Intrinsics.checkNotNull(padResolverAdapter);
        padResolverAdapter.requery(intent);
        ViewGroup viewGroup = this.mShareContainer;
        Intrinsics.checkNotNull(viewGroup);
        viewGroup.setBackgroundResource(R.color.pad_note_sns_share_container_bg);
        ViewGroup viewGroup2 = this.mShareContainer;
        Intrinsics.checkNotNull(viewGroup2);
        viewGroup2.setVisibility(0);
        WeiboPreviewView weiboPreviewView = this.mPreview;
        Intrinsics.checkNotNull(weiboPreviewView);
        weiboPreviewView.invalidate();
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Code duplicated, block: B:6:0x0073  */
    /* JADX WARN: Code duplicated, block: B:8:0x0079  */
    public final void doShare(Intent intent, Uri previewUri) {
        PadSnsEditActivity padSnsEditActivity = this;
        Uri compatUri = CompatUtils.getCompatUri(padSnsEditActivity, previewUri);
        intent.setAction("android.intent.action.SEND");
        intent.setType("image/jpeg");
        intent.putExtra("android.intent.extra.STREAM", compatUri);
        intent.addCategory("android.intent.category.DEFAULT");
        intent.addFlags(DataSpace.RANGE_LIMITED);
        intent.addFlags(67108864);
        intent.addFlags(8388608);
        intent.addFlags(1);
        ComponentName component = intent.getComponent();
        Intrinsics.checkNotNull(component);
        grantUriPermission(component.getPackageName(), compatUri, 3);
        Intent intent2 = new Intent("miui.intent.action.XMAN_SHARE_MANAGER");
        intent2.setPackage("com.miui.xman");
        ComponentName component2 = intent.getComponent();
        Intrinsics.checkNotNull(component2);
        if (!Intrinsics.areEqual(component2.getClassName(), INTENT_CLASSNAME_SHARE_WECHAT)) {
            ComponentName component3 = intent.getComponent();
            Intrinsics.checkNotNull(component3);
            if (Intrinsics.areEqual(component3.getClassName(), INTENT_CLASSNAME_SHARE_WECHAT_MOMENTS)) {
                if (isXmanShareSupport(padSnsEditActivity, intent2)) {
                    grantUriPermission("com.miui.xman", compatUri, 3);
                    intent2.putExtra("android.intent.extra.INTENT", intent);
                    startActivityForResult(intent2, 2);
                    return;
                }
            }
        } else if (isXmanShareSupport(padSnsEditActivity, intent2)) {
            grantUriPermission("com.miui.xman", compatUri, 3);
            intent2.putExtra("android.intent.extra.INTENT", intent);
            startActivityForResult(intent2, 2);
            return;
        }
        startActivityForResult(intent, 2);
    }

    private final boolean isXmanShareSupport(Context context, Intent intent) {
        try {
            List<ResolveInfo> listQueryIntentActivities = context.getPackageManager().queryIntentActivities(intent, 32);
            Intrinsics.checkNotNullExpressionValue(listQueryIntentActivities, "queryIntentActivities(...)");
            return listQueryIntentActivities.size() > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void doSaveToGallery(File file) {
        SaveToGalleryTask saveToGalleryTask = this.mSaveToGalleryTask;
        if (saveToGalleryTask != null) {
            Intrinsics.checkNotNull(saveToGalleryTask);
            saveToGalleryTask.cancel(true);
            this.mSaveToGalleryTask = null;
        }
        SaveToGalleryTask saveToGalleryTask2 = new SaveToGalleryTask(this);
        this.mSaveToGalleryTask = saveToGalleryTask2;
        Intrinsics.checkNotNull(saveToGalleryTask2);
        saveToGalleryTask2.execute(file);
    }

    @Override // com.miui.common.base.DialogManagerInterface
    public Activity getThisActivity() {
        return this;
    }

    /* JADX INFO: compiled from: PadSnsEditActivity.kt */
    @Metadata(d1 = {"\u0000H\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0010\u0011\n\u0002\b\f\b\u0000\u0018\u00002\u0014\u0012\u0004\u0012\u00020\u0002\u0012\u0004\u0012\u00020\u0002\u0012\u0004\u0012\u00020\u00020\u0001B\u001b\u0012\u0006\u0010\u0003\u001a\u00020\u0004\u0012\n\b\u0002\u0010\u0005\u001a\u0004\u0018\u00010\u0006¢\u0006\u0004\b\u0007\u0010\bJ!\u0010\u0018\u001a\u00020\u00022\u0012\u0010\u0019\u001a\n\u0012\u0006\b\u0001\u0012\u00020\u00020\u001a\"\u00020\u0002H\u0014¢\u0006\u0002\u0010\u001bJ\u001c\u0010\u001f\u001a\u0004\u0018\u00010\u00102\u0006\u0010 \u001a\u00020\u00152\b\u0010!\u001a\u0004\u0018\u00010\u0010H\u0002J\b\u0010\"\u001a\u00020\u0002H\u0014J\u0015\u0010#\u001a\u00020\u00022\u0006\u0010$\u001a\u00020\u0002H\u0014¢\u0006\u0002\u0010%R\u0013\u0010\u0005\u001a\u0004\u0018\u00010\u0006¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\nR\u001c\u0010\u000b\u001a\u0010\u0012\f\u0012\n \r*\u0004\u0018\u00010\u00040\u00040\fX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u000e\u001a\u00020\u0006X\u0082D¢\u0006\u0002\n\u0000R\u0010\u0010\u000f\u001a\u0004\u0018\u00010\u0010X\u0082\u000e¢\u0006\u0002\n\u0000R\u0010\u0010\u0011\u001a\u0004\u0018\u00010\u0010X\u0082\u000e¢\u0006\u0002\n\u0000R\u0010\u0010\u0012\u001a\u0004\u0018\u00010\u0013X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0014\u001a\u00020\u0015X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0016\u001a\u00020\u0017X\u0082\u000e¢\u0006\u0002\n\u0000R\u0016\u0010\u001c\u001a\u0004\u0018\u00010\u00138BX\u0082\u0004¢\u0006\u0006\u001a\u0004\b\u001d\u0010\u001e¨\u0006&"}, d2 = {"Lcom/miui/pad/feature/notes/share/PadSnsEditActivity$LaunchSendActivityTask;", "Landroid/os/AsyncTask;", "", "activity", "Lcom/miui/pad/feature/notes/share/PadSnsEditActivity;", "metaAiInfo", "", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "(Lcom/miui/pad/feature/notes/share/PadSnsEditActivity;Ljava/lang/String;)V", "getMetaAiInfo", "()Ljava/lang/String;", "iActivityRef", "Ljava/lang/ref/WeakReference;", "kotlin.jvm.PlatformType", "TAG", "iPreviewUri", "Landroid/net/Uri;", "iThumbnailUri", "iShareFile", "Ljava/io/File;", "iContext", "Landroid/content/Context;", "iShareType", "", "doInBackground", "params", "", "([Lkotlin/Unit;)V", "shareFile", "getShareFile", "()Ljava/io/File;", "getThumbnailUri", CoreConstants.CONTEXT_SCOPE_VALUE, "previewUri", "onCancelled", "onPostExecute", "result", "(Lkotlin/Unit;)V", "app_PhoneGlobalRelease"}, k = 1, mv = {2, 2, 0}, xi = 48)
    public static final class LaunchSendActivityTask extends AsyncTask<Unit, Unit, Unit> {
        private final String TAG;
        private final WeakReference<PadSnsEditActivity> iActivityRef;
        private final Context iContext;
        private Uri iPreviewUri;
        private File iShareFile;
        private int iShareType;
        private Uri iThumbnailUri;
        private final String metaAiInfo;

        public /* synthetic */ LaunchSendActivityTask(PadSnsEditActivity padSnsEditActivity, String str, int i, DefaultConstructorMarker defaultConstructorMarker) {
            this(padSnsEditActivity, (i & 2) != 0 ? null : str);
        }

        @Override // android.os.AsyncTask
        public /* bridge */ /* synthetic */ Unit doInBackground(Unit[] unitArr) {
            doInBackground2(unitArr);
            return Unit.INSTANCE;
        }

        public final String getMetaAiInfo() {
            return this.metaAiInfo;
        }

        public LaunchSendActivityTask(PadSnsEditActivity activity, String str) {
            Intrinsics.checkNotNullParameter(activity, "activity");
            this.metaAiInfo = str;
            WeakReference<PadSnsEditActivity> weakReference = new WeakReference<>(activity);
            this.iActivityRef = weakReference;
            this.TAG = "LaunchSendActivityTask";
            Context applicationContext = activity.getApplicationContext();
            Intrinsics.checkNotNullExpressionValue(applicationContext, "getApplicationContext(...)");
            this.iContext = applicationContext;
            PadSnsEditActivity padSnsEditActivity = weakReference.get();
            if (padSnsEditActivity != null) {
                this.iShareType = padSnsEditActivity.mShareType;
            }
        }

        /* JADX INFO: renamed from: doInBackground, reason: avoid collision after fix types in other method */
        protected void doInBackground2(Unit... params) {
            Intrinsics.checkNotNullParameter(params, "params");
            Logger.INSTANCE.d(this.TAG, "doInback...");
            if (isCancelled()) {
                Logger.INSTANCE.i(this.TAG, "isCancelled, return.");
                return;
            }
            File shareFile = getShareFile();
            this.iShareFile = shareFile;
            if (shareFile != null) {
                Uri uriFromFile = Uri.fromFile(shareFile);
                this.iPreviewUri = uriFromFile;
                if (this.iShareType == 1) {
                    this.iThumbnailUri = getThumbnailUri(this.iContext, uriFromFile);
                    return;
                }
                return;
            }
            Logger.INSTANCE.e(this.TAG, "Fail to get preview file");
        }

        private final File getShareFile() {
            File fileCreateFile = TempFileManager.getInstance().createFile("share", Utils.JPG_SUFFIX);
            if (fileCreateFile == null) {
                Logger.INSTANCE.e(this.TAG, "Fail to getShareFile, shareFile is null");
                return null;
            }
            if (this.iActivityRef.get() != null) {
                PadSnsEditActivity padSnsEditActivity = this.iActivityRef.get();
                Intrinsics.checkNotNull(padSnsEditActivity);
                Render render = padSnsEditActivity.mRender;
                Intrinsics.checkNotNull(render);
                if (render.saveToFile(fileCreateFile)) {
                    fileCreateFile.setReadable(true, false);
                    fileCreateFile.setWritable(true, false);
                    if (this.metaAiInfo != null) {
                        Logger.INSTANCE.d(this.TAG, "set ret:" + BitmapUtils.INSTANCE.writeExifMetaData(fileCreateFile, ExifInterface.TAG_USER_COMMENT, this.metaAiInfo) + ",with value:" + BitmapUtils.INSTANCE.getMetaExifData(fileCreateFile, ExifInterface.TAG_USER_COMMENT));
                    }
                    return fileCreateFile;
                }
                Logger.INSTANCE.e(this.TAG, "Fail to getShareFile, saveToFile failed");
                return null;
            }
            Logger.INSTANCE.e(this.TAG, "Fail to getShareFile, wk is null failed");
            return null;
        }

        private final Uri getThumbnailUri(Context context, Uri previewUri) {
            Bitmap thumbnailBitmap = ImageUtils.INSTANCE.getThumbnailBitmap(context, previewUri);
            if (thumbnailBitmap == null) {
                Logger.INSTANCE.e(this.TAG, "Fail to get thumbnail");
                return null;
            }
            File fileCreateFile = TempFileManager.getInstance().createFile("thumbnail", Utils.JPG_SUFFIX);
            if (fileCreateFile == null) {
                Logger.INSTANCE.e(this.TAG, "Fail to create thumbnail file");
                return null;
            }
            try {
                BitmapFactory.saveToFile(thumbnailBitmap, fileCreateFile.getPath());
                if (!thumbnailBitmap.isRecycled()) {
                    thumbnailBitmap.recycle();
                }
                return Uri.fromFile(fileCreateFile);
            } catch (IOException e) {
                Logger.INSTANCE.e(this.TAG, "Fail to save thumbnail,exception:" + e);
                return null;
            }
        }

        @Override // android.os.AsyncTask
        protected void onCancelled() {
            PadSnsEditActivity padSnsEditActivity = this.iActivityRef.get();
            if (padSnsEditActivity != null && padSnsEditActivity.mShareGenerateTask == this) {
                padSnsEditActivity.mShareGenerateTask = null;
                Logger.INSTANCE.d(this.TAG, "LaunchSendActivityTask onCancelled");
                padSnsEditActivity.dismissProgressDialog();
            }
            super.onCancelled();
        }

        /* JADX INFO: Access modifiers changed from: protected */
        @Override // android.os.AsyncTask
        public void onPostExecute(Unit result) {
            Intrinsics.checkNotNullParameter(result, "result");
            Logger.INSTANCE.d(this.TAG, "LaunchSendActivityTask onPostExecute ");
            PadSnsEditActivity padSnsEditActivity = this.iActivityRef.get();
            if (padSnsEditActivity != null) {
                if (padSnsEditActivity.mShareGenerateTask == this) {
                    padSnsEditActivity.mShareGenerateTask = null;
                    Logger.INSTANCE.d("SnsEditActivity", "LaunchSendActivityTask onPostExecute");
                    padSnsEditActivity.dismissProgressDialog();
                    if (this.iPreviewUri != null) {
                        padSnsEditActivity.showShareContainer(this.iShareFile);
                        return;
                    } else {
                        Logger.INSTANCE.e(this.TAG, "Fail to get preview file");
                        return;
                    }
                }
                Logger.INSTANCE.w(this.TAG, "task has changed, don't apply result");
            }
        }
    }

    @Override // com.miui.common.base.BaseActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    protected void onResume() {
        super.onResume();
        updateWindowStatus();
        UIUtils.checkOrientationInFoldDevice(this);
        PadSnsEditActivity padSnsEditActivity = this;
        if (this.isInFullWindowGestureMode != UIUtils.isInFullWindowGestureMode(padSnsEditActivity)) {
            this.isInFullWindowGestureMode = UIUtils.isInFullWindowGestureMode(padSnsEditActivity);
            UIUtils.setNavigationTrans(this);
        }
    }

    @Override // com.miui.common.base.BaseActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    protected void onStart() {
        super.onStart();
        PasswordController passwordController = this.mPasswordController;
        if (passwordController != null) {
            Intrinsics.checkNotNull(passwordController);
            passwordController.unlock();
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.miui.common.base.BaseActivity, miuix.appcompat.app.AppCompatActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    public void onStop() {
        super.onStop();
        PasswordController passwordController = this.mPasswordController;
        if (passwordController != null) {
            Intrinsics.checkNotNull(passwordController);
            passwordController.lock();
        }
    }

    @Override // androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, android.app.Activity
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 1) {
            updatePreview();
            return;
        }
        if (requestCode == 2) {
            finish();
            return;
        }
        if (requestCode == 200) {
            if (resultCode == 1) {
                PreferenceUtils.setFirstHandle(false, getApplicationContext());
                PreferenceUtils.setCTAAccepted(true, getApplicationContext());
                PreferenceUtils.setAllowNetwork(this, true);
                Bundle bundle = new Bundle();
                bundle.putInt("push_type", 0);
                SyncUtils.requestSync(BaseNoteApp.INSTANCE.getInstance(), bundle, true);
            } else if (resultCode == 666) {
                PreferenceUtils.setFirstHandle(false, getApplicationContext());
                PreferenceUtils.setCTAAccepted(false, getApplicationContext());
            }
            PermissionUtils.processCtaResult(this, resultCode);
            return;
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override // com.miui.common.base.BaseActivity, miuix.appcompat.app.AppCompatActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    public void onDestroy() {
        com.miui.common.tool.Logger.INSTANCE.d(TAG, "onDestroy");
        try {
            unregisterReceiver(this.mFinishReceiver);
        } catch (Exception e) {
            Logger.INSTANCE.e(TAG, "onDestroy receiver is unregister or canceled: " + e);
        }
        getMHandler().removeCallbacksAndMessages(null);
        Render render = this.mRender;
        if (render != null) {
            Intrinsics.checkNotNull(render);
            render.destroy();
            this.mRender = null;
        }
        ProgressDialog progressDialog = this.mDialog;
        if (progressDialog != null) {
            Intrinsics.checkNotNull(progressDialog);
            if (progressDialog.isShowing()) {
                ProgressDialog progressDialog2 = this.mDialog;
                Intrinsics.checkNotNull(progressDialog2);
                progressDialog2.dismiss();
            }
        }
        LaunchSendActivityTask launchSendActivityTask = this.mShareGenerateTask;
        if (launchSendActivityTask != null) {
            Intrinsics.checkNotNull(launchSendActivityTask);
            launchSendActivityTask.cancel(true);
        }
        deleteTempUri();
        if (this.mMamlRenderCallback != null) {
            this.mMamlRenderCallback = null;
        }
        ResourceManager.clearThemes();
        PasswordController passwordController = this.mPasswordController;
        if (passwordController != null) {
            Intrinsics.checkNotNull(passwordController);
            passwordController.destroy();
        }
        super.onDestroy();
    }

    @Override // com.miui.common.base.BaseActivity, miuix.appcompat.app.AppCompatActivity, androidx.activity.ComponentActivity, android.app.Activity, android.content.ComponentCallbacks
    public void onConfigurationChanged(Configuration configuration) {
        Intrinsics.checkNotNullParameter(configuration, "configuration");
        super.onConfigurationChanged(configuration);
        UIUtils.setNavigationTrans(this);
        RecyclerView recyclerView = this.mChooserView;
        if (recyclerView == null || this.mChooserAdapter == null) {
            return;
        }
        Intrinsics.checkNotNull(recyclerView);
        recyclerView.postDelayed(new Runnable() { // from class: com.miui.pad.feature.notes.share.PadSnsEditActivity$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                PadSnsEditActivity.onConfigurationChanged$lambda$13(this.f$0);
            }
        }, 200L);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void onConfigurationChanged$lambda$13(PadSnsEditActivity padSnsEditActivity) {
        RecyclerView recyclerView = padSnsEditActivity.mChooserView;
        Intrinsics.checkNotNull(recyclerView);
        recyclerView.invalidate();
        PadResolverAdapter padResolverAdapter = padSnsEditActivity.mChooserAdapter;
        Intrinsics.checkNotNull(padResolverAdapter);
        padResolverAdapter.notifyDataSetChanged();
    }

    @Override // miuix.appcompat.app.AppCompatActivity, miuix.responsive.interfaces.IResponsive
    public void onResponsiveLayout(Configuration configuration, ScreenSpec screenSpec, boolean responsiveLayoutStateChanged) {
        Intrinsics.checkNotNullParameter(configuration, "configuration");
        Intrinsics.checkNotNullParameter(screenSpec, "screenSpec");
        super.onResponsiveLayout(configuration, screenSpec, responsiveLayoutStateChanged);
        if (responsiveLayoutStateChanged && RomUtils.isPadDevice()) {
            boolean z = getResponsiveState().getType() != 1;
            if (this.mHomeView != null) {
                Drawable drawable = getResources().getDrawable(getResourceId(z), this.mIsForceDark ? this.mDarkTheme : this.mResTheme);
                ImageView imageView = this.mHomeView;
                Intrinsics.checkNotNull(imageView);
                imageView.setImageDrawable(drawable);
            }
            if (this.mTitleView != null) {
                int color = getResources().getColor(this.mIsForceDark ? R.color.sns_title_text_color_dark : R.color.action_button_theme_color, this.mIsForceDark ? this.mDarkTheme : this.mResTheme);
                TextView textView = this.mTitleView;
                Intrinsics.checkNotNull(textView);
                textView.setTextColor(color);
            }
        }
        setPreviewContent();
        UIUtils.checkOrientationInFoldDevice(this);
    }

    private final void setPreviewContent() {
        updatePreviewContentPadding();
        WeiboPreviewView weiboPreviewView = this.mPreview;
        if (weiboPreviewView != null) {
            Intrinsics.checkNotNull(weiboPreviewView);
            weiboPreviewView.postDelayed(new Runnable() { // from class: com.miui.pad.feature.notes.share.PadSnsEditActivity$$ExternalSyntheticLambda7
                @Override // java.lang.Runnable
                public final void run() {
                    PadSnsEditActivity.setPreviewContent$lambda$14(this.f$0);
                }
            }, 100L);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void setPreviewContent$lambda$14(PadSnsEditActivity padSnsEditActivity) {
        WeiboPreviewView weiboPreviewView = padSnsEditActivity.mPreview;
        if (weiboPreviewView != null) {
            Intrinsics.checkNotNull(weiboPreviewView);
            weiboPreviewView.requestLayout();
        }
    }

    private final void updatePreviewContentPadding() {
        int dimensionPixelSize;
        int dimensionPixelSize2;
        int type = getResponsiveState().getType();
        if (RomUtils.isFoldDevice()) {
            setFloatingWindowMode(false);
            if (type != 1) {
                dimensionPixelSize = getResources().getDimensionPixelSize(R.dimen.pad_share_padding_start_full);
                dimensionPixelSize2 = getResources().getDimensionPixelSize(R.dimen.pad_share_padding_end_full);
            } else {
                dimensionPixelSize = getResources().getDimensionPixelSize(R.dimen.fold_share_padding_start);
                dimensionPixelSize2 = getResources().getDimensionPixelSize(R.dimen.fold_share_padding_end);
            }
            View view = this.mPreviewScroll;
            if (view != null) {
                Intrinsics.checkNotNull(view);
                View view2 = this.mPreviewScroll;
                Intrinsics.checkNotNull(view2);
                int paddingTop = view2.getPaddingTop();
                View view3 = this.mPreviewScroll;
                Intrinsics.checkNotNull(view3);
                view.setPaddingRelative(dimensionPixelSize, paddingTop, dimensionPixelSize2, view3.getPaddingBottom());
            }
        }
    }

    /* JADX INFO: compiled from: PadSnsEditActivity.kt */
    @Metadata(d1 = {"\u0000.\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0011\n\u0002\b\u0005\b\u0000\u0018\u00002\u0018\u0012\u0006\u0012\u0004\u0018\u00010\u0002\u0012\u0004\u0012\u00020\u0003\u0012\u0006\u0012\u0004\u0018\u00010\u00040\u0001B\u000f\u0012\u0006\u0010\u0005\u001a\u00020\u0006¢\u0006\u0004\b\u0007\u0010\bJ'\u0010\f\u001a\u0004\u0018\u00010\u00042\u0016\u0010\r\u001a\f\u0012\b\b\u0001\u0012\u0004\u0018\u00010\u00020\u000e\"\u0004\u0018\u00010\u0002H\u0014¢\u0006\u0002\u0010\u000fJ\b\u0010\u0010\u001a\u00020\u0003H\u0014J\u0012\u0010\u0011\u001a\u00020\u00032\b\u0010\u0012\u001a\u0004\u0018\u00010\u0004H\u0014R\u001c\u0010\t\u001a\u0010\u0012\f\u0012\n \u000b*\u0004\u0018\u00010\u00060\u00060\nX\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u0013"}, d2 = {"Lcom/miui/pad/feature/notes/share/PadSnsEditActivity$SaveToGalleryTask;", "Landroid/os/AsyncTask;", "Ljava/io/File;", "", "", "activity", "Lcom/miui/pad/feature/notes/share/PadSnsEditActivity;", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "(Lcom/miui/pad/feature/notes/share/PadSnsEditActivity;)V", "iActivityRef", "Ljava/lang/ref/WeakReference;", "kotlin.jvm.PlatformType", "doInBackground", "files", "", "([Ljava/io/File;)Ljava/lang/String;", "onPreExecute", "onPostExecute", "result", "app_PhoneGlobalRelease"}, k = 1, mv = {2, 2, 0}, xi = 48)
    public static final class SaveToGalleryTask extends AsyncTask<File, Unit, String> {
        private final WeakReference<PadSnsEditActivity> iActivityRef;

        public SaveToGalleryTask(PadSnsEditActivity activity) {
            Intrinsics.checkNotNullParameter(activity, "activity");
            this.iActivityRef = new WeakReference<>(activity);
        }

        /* JADX INFO: Access modifiers changed from: protected */
        /* JADX WARN: Code duplicated, block: B:60:0x00b1 A[DONT_INVERT] */
        /* JADX WARN: Code duplicated, block: B:61:0x00b3 A[Catch: IOException -> 0x00af, TRY_LEAVE, TryCatch #5 {IOException -> 0x00af, blocks: (B:57:0x00ab, B:61:0x00b3), top: B:68:0x00ab }] */
        /* JADX WARN: Code duplicated, block: B:68:0x00ab A[EXC_TOP_SPLITTER, SYNTHETIC] */
        /* JADX WARN: Multi-variable type inference failed */
        /* JADX WARN: Type inference failed for: r7v12 */
        /* JADX WARN: Type inference failed for: r7v22 */
        /* JADX WARN: Type inference failed for: r7v3, types: [java.io.FileOutputStream] */
        @Override // android.os.AsyncTask
        public String doInBackground(File... files) throws Throwable {
            File file;
            ?? r7;
            FileOutputStream fileOutputStream;
            FileInputStream fileInputStream;
            Intrinsics.checkNotNullParameter(files, "files");
            FileInputStream fileInputStream2 = null;
            if (files.length == 0 || (file = files[0]) == null) {
                return null;
            }
            Intrinsics.checkNotNull(file);
            if (!file.exists()) {
                return null;
            }
            File fileObtainJpgFile = Utils.obtainJpgFile(BaseNoteApp.INSTANCE.getInstance());
            try {
                try {
                    if (fileObtainJpgFile == null) {
                        return null;
                    }
                    try {
                        fileInputStream = new FileInputStream(file);
                        try {
                            fileOutputStream = new FileOutputStream(fileObtainJpgFile);
                            try {
                                byte[] bArr = new byte[1024];
                                while (true) {
                                    int i = fileInputStream.read(bArr);
                                    if (i == -1) {
                                        break;
                                    }
                                    fileOutputStream.write(bArr, 0, i);
                                }
                                fileInputStream.close();
                            } catch (FileNotFoundException e) {
                                e = e;
                                e.printStackTrace();
                                if (fileInputStream != null) {
                                    fileInputStream.close();
                                }
                                if (fileOutputStream != null) {
                                }
                                MediaScannerConnection.scanFile(BaseNoteApp.INSTANCE.getInstance(), new String[]{fileObtainJpgFile.toString()}, null, new MediaScannerConnection.OnScanCompletedListener() { // from class: com.miui.pad.feature.notes.share.PadSnsEditActivity$SaveToGalleryTask$$ExternalSyntheticLambda0
                                    @Override // android.media.MediaScannerConnection.OnScanCompletedListener
                                    public final void onScanCompleted(String str, Uri uri) {
                                        PadSnsEditActivity.SaveToGalleryTask.doInBackground$lambda$1(str, uri);
                                    }
                                });
                                BaseNoteApp.INSTANCE.getInstance().sendBroadcast(new Intent(SystemIntent.ACTION_SAVE_TO_CLOUD_GALLERY));
                                return fileObtainJpgFile.getAbsolutePath();
                            } catch (IOException e2) {
                                e = e2;
                                e.printStackTrace();
                                if (fileInputStream != null) {
                                    fileInputStream.close();
                                }
                                if (fileOutputStream != null) {
                                }
                                MediaScannerConnection.scanFile(BaseNoteApp.INSTANCE.getInstance(), new String[]{fileObtainJpgFile.toString()}, null, new MediaScannerConnection.OnScanCompletedListener() { // from class: com.miui.pad.feature.notes.share.PadSnsEditActivity$SaveToGalleryTask$$ExternalSyntheticLambda0
                                    @Override // android.media.MediaScannerConnection.OnScanCompletedListener
                                    public final void onScanCompleted(String str, Uri uri) {
                                        PadSnsEditActivity.SaveToGalleryTask.doInBackground$lambda$1(str, uri);
                                    }
                                });
                                BaseNoteApp.INSTANCE.getInstance().sendBroadcast(new Intent(SystemIntent.ACTION_SAVE_TO_CLOUD_GALLERY));
                                return fileObtainJpgFile.getAbsolutePath();
                            }
                        } catch (FileNotFoundException e3) {
                            e = e3;
                            fileOutputStream = null;
                        } catch (IOException e4) {
                            e = e4;
                            fileOutputStream = null;
                        } catch (Throwable th) {
                            th = th;
                            file = null;
                            fileInputStream2 = fileInputStream;
                            r7 = file;
                            if (fileInputStream2 != null) {
                                try {
                                    fileInputStream2.close();
                                    if (r7 != 0) {
                                        r7.close();
                                    }
                                } catch (IOException e5) {
                                    e5.printStackTrace();
                                    throw th;
                                }
                            } else if (r7 != 0) {
                                r7.close();
                            }
                            throw th;
                        }
                    } catch (FileNotFoundException e6) {
                        e = e6;
                        fileOutputStream = null;
                        fileInputStream = null;
                    } catch (IOException e7) {
                        e = e7;
                        fileOutputStream = null;
                        fileInputStream = null;
                    } catch (Throwable th2) {
                        th = th2;
                        r7 = 0;
                        if (fileInputStream2 != null) {
                            fileInputStream2.close();
                            if (r7 != 0) {
                                r7.close();
                            }
                        } else if (r7 != 0) {
                            r7.close();
                        }
                        throw th;
                    }
                    fileOutputStream.close();
                } catch (Throwable th3) {
                    th = th3;
                }
            } catch (IOException e8) {
                e8.printStackTrace();
            }
            MediaScannerConnection.scanFile(BaseNoteApp.INSTANCE.getInstance(), new String[]{fileObtainJpgFile.toString()}, null, new MediaScannerConnection.OnScanCompletedListener() { // from class: com.miui.pad.feature.notes.share.PadSnsEditActivity$SaveToGalleryTask$$ExternalSyntheticLambda0
                @Override // android.media.MediaScannerConnection.OnScanCompletedListener
                public final void onScanCompleted(String str, Uri uri) {
                    PadSnsEditActivity.SaveToGalleryTask.doInBackground$lambda$1(str, uri);
                }
            });
            BaseNoteApp.INSTANCE.getInstance().sendBroadcast(new Intent(SystemIntent.ACTION_SAVE_TO_CLOUD_GALLERY));
            return fileObtainJpgFile.getAbsolutePath();
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static final void doInBackground$lambda$1(String str, Uri uri) {
            Logger.INSTANCE.i(PadSnsEditActivity.TAG, "Scanned " + str + " completely.");
        }

        @Override // android.os.AsyncTask
        protected void onPreExecute() {
            PadSnsEditHandler mHandler;
            PadSnsEditActivity padSnsEditActivity = this.iActivityRef.get();
            if (padSnsEditActivity == null || (mHandler = padSnsEditActivity.getMHandler()) == null) {
                return;
            }
            mHandler.postDelayed(new Runnable() { // from class: com.miui.pad.feature.notes.share.PadSnsEditActivity$SaveToGalleryTask$$ExternalSyntheticLambda1
                @Override // java.lang.Runnable
                public final void run() {
                    PadSnsEditActivity.SaveToGalleryTask.onPreExecute$lambda$2(this.f$0);
                }
            }, 500L);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static final void onPreExecute$lambda$2(SaveToGalleryTask saveToGalleryTask) {
            PadSnsEditActivity padSnsEditActivity = saveToGalleryTask.iActivityRef.get();
            if (padSnsEditActivity == null || padSnsEditActivity.mSaveToGalleryTask != saveToGalleryTask || padSnsEditActivity.isFinishing()) {
                return;
            }
            String string = padSnsEditActivity.getResources().getString(R.string.dlg_info_save_to_local_message);
            Intrinsics.checkNotNullExpressionValue(string, "getString(...)");
            padSnsEditActivity.showProgressDialog(string);
        }

        /* JADX INFO: Access modifiers changed from: protected */
        @Override // android.os.AsyncTask
        public void onPostExecute(String result) {
            PadSnsEditActivity padSnsEditActivity = this.iActivityRef.get();
            if (padSnsEditActivity != null && padSnsEditActivity.mSaveToGalleryTask != this) {
                Logger.INSTANCE.w(PadSnsEditActivity.TAG, "Task has changed, don't apply result");
                return;
            }
            Intrinsics.checkNotNull(padSnsEditActivity);
            padSnsEditActivity.mSaveToGalleryTask = null;
            padSnsEditActivity.dismissProgressDialog();
            if (result == null) {
                Toast.makeText(BaseNoteApp.INSTANCE.getInstance(), padSnsEditActivity.getResources().getString(R.string.save_to_local_failure_message), 0).show();
            } else {
                Toast.makeText(BaseNoteApp.INSTANCE.getInstance(), padSnsEditActivity.getResources().getString(R.string.save_to_local_success_message), 0).show();
                ExternalAppUtils.openGallery(padSnsEditActivity);
            }
            padSnsEditActivity.deleteTempUri();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void deleteTempUri() {
        Uri[] uriArr = this.mHybridEditorRenderImgUris;
        if (uriArr != null) {
            Intrinsics.checkNotNull(uriArr);
            if (uriArr.length > 0) {
                Uri[] uriArr2 = this.mHybridEditorRenderImgUris;
                Intrinsics.checkNotNull(uriArr2);
                int length = uriArr2.length;
                for (int i = 0; i < length; i++) {
                    Uri[] uriArr3 = this.mHybridEditorRenderImgUris;
                    Intrinsics.checkNotNull(uriArr3);
                    Uri uri = uriArr3[i];
                    Intrinsics.checkNotNull(uri);
                    File file = new File(uri.getPath());
                    try {
                        Boolean.valueOf(file.delete());
                    } catch (Exception e) {
                        Logger.INSTANCE.w(TAG, "HybridEditorRenderImg delete fail: " + file.getName() + " e:" + e);
                        Unit unit = Unit.INSTANCE;
                    }
                }
                this.mHybridEditorRenderImgUris = null;
            }
        }
        try {
            if (this.uri != null) {
                ContentResolver contentResolver = getContentResolver();
                Uri uri2 = this.uri;
                Intrinsics.checkNotNull(uri2);
                contentResolver.delete(uri2, null, null);
            }
        } catch (Exception e2) {
            e2.printStackTrace();
        }
    }

    @Override // androidx.activity.ComponentActivity, android.app.Activity
    public void onBackPressed() {
        setResult(-1);
        super.onBackPressed();
    }

    private final void smallWindowAdapter() {
        ViewGroup viewGroup = this.mShareContainer;
        if (viewGroup == null) {
            return;
        }
        Intrinsics.checkNotNull(viewGroup);
        ViewCompat.setOnApplyWindowInsetsListener(viewGroup, new OnApplyWindowInsetsListener() { // from class: com.miui.pad.feature.notes.share.PadSnsEditActivity.smallWindowAdapter.1
            @Override // androidx.core.view.OnApplyWindowInsetsListener
            public WindowInsetsCompat onApplyWindowInsets(View v, WindowInsetsCompat insets) {
                Intrinsics.checkNotNullParameter(v, "v");
                Intrinsics.checkNotNullParameter(insets, "insets");
                WindowInsetsCompat rootWindowInsets = ViewCompat.getRootWindowInsets(v);
                ViewGroup viewGroup2 = PadSnsEditActivity.this.mShareContainer;
                Intrinsics.checkNotNull(viewGroup2);
                Activity activity = UIUtils.getActivity(viewGroup2.getContext());
                if (rootWindowInsets != null && activity != null) {
                    Insets insetsIgnoringVisibility = rootWindowInsets.getInsetsIgnoringVisibility(WindowInsetsCompat.Type.systemBars());
                    Intrinsics.checkNotNullExpressionValue(insetsIgnoringVisibility, "getInsetsIgnoringVisibility(...)");
                    if (UIUtils.isInMultiWindowMode(activity) || RomUtils.isFoldDevice()) {
                        ViewGroup viewGroup3 = PadSnsEditActivity.this.mShareContainer;
                        Intrinsics.checkNotNull(viewGroup3);
                        viewGroup3.setPadding(0, 0, 0, insetsIgnoringVisibility.bottom);
                    } else {
                        ViewGroup viewGroup4 = PadSnsEditActivity.this.mShareContainer;
                        Intrinsics.checkNotNull(viewGroup4);
                        viewGroup4.setPadding(0, 0, 0, 0);
                        return insets;
                    }
                }
                return insets;
            }
        });
    }

    /* JADX INFO: compiled from: PadSnsEditActivity.kt */
    @Metadata(d1 = {"\u0000`\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0010\t\n\u0000\n\u0002\u0010\u0007\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0014\b\u0086\u0003\u0018\u00002\u00020\u0001B\t\b\u0002¢\u0006\u0004\b\u0002\u0010\u0003Jr\u0010\b\u001a\u00020\t2\u000e\u0010\n\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\f0\u000b2\b\u0010\r\u001a\u0004\u0018\u00010\u000e2\b\u0010\u000f\u001a\u0004\u0018\u00010\u00052\b\u0010\u0010\u001a\u0004\u0018\u00010\u00052\u0006\u0010\u0011\u001a\u00020\u00122\u0006\u0010\u0013\u001a\u00020\u00122\u0006\u0010\u0014\u001a\u00020\u00152\u0006\u0010\u0016\u001a\u00020\u00172\b\u0010\u0018\u001a\u0004\u0018\u00010\u00052\b\u0010\u0019\u001a\u0004\u0018\u00010\u001a2\b\u0010\u001b\u001a\u0004\u0018\u00010\u001cJ\u0082\u0001\u0010\b\u001a\u00020\t2\u000e\u0010\n\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\f0\u000b2\b\u0010\r\u001a\u0004\u0018\u00010\u000e2\b\u0010\u000f\u001a\u0004\u0018\u00010\u00052\b\u0010\u0010\u001a\u0004\u0018\u00010\u00052\b\u0010\u001d\u001a\u0004\u0018\u00010\u00052\u0006\u0010\u0011\u001a\u00020\u00122\u001e\u0010\u001e\u001a\u001a\u0012\u0006\u0012\u0004\u0018\u00010 \u0018\u00010\u001fj\f\u0012\u0006\u0012\u0004\u0018\u00010 \u0018\u0001`!2\u0006\u0010\"\u001a\u00020\u00122\u0006\u0010#\u001a\u00020\u00152\n\b\u0002\u0010\u001b\u001a\u0004\u0018\u00010\u001cR\u000e\u0010\u0004\u001a\u00020\u0005X\u0082T¢\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0005X\u0082T¢\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\u0005X\u0082T¢\u0006\u0002\n\u0000R\u000e\u0010$\u001a\u00020\u0005X\u0082T¢\u0006\u0002\n\u0000R\u000e\u0010%\u001a\u00020\u0005X\u0082T¢\u0006\u0002\n\u0000R\u000e\u0010&\u001a\u00020\u0005X\u0082T¢\u0006\u0002\n\u0000R\u000e\u0010'\u001a\u00020\u0005X\u0082T¢\u0006\u0002\n\u0000R\u000e\u0010(\u001a\u00020\u0005X\u0082T¢\u0006\u0002\n\u0000R\u000e\u0010)\u001a\u00020\u0005X\u0082T¢\u0006\u0002\n\u0000R\u000e\u0010*\u001a\u00020\u0005X\u0082T¢\u0006\u0002\n\u0000R\u000e\u0010+\u001a\u00020\u0005X\u0082T¢\u0006\u0002\n\u0000R\u000e\u0010,\u001a\u00020\u0012X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010-\u001a\u00020\u0012X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010.\u001a\u00020\u0012X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010/\u001a\u00020\u0012X\u0086T¢\u0006\u0002\n\u0000R\u000e\u00100\u001a\u00020\u0005X\u0082T¢\u0006\u0002\n\u0000R\u000e\u00101\u001a\u00020\u0012X\u0082T¢\u0006\u0002\n\u0000R\u000e\u00102\u001a\u00020\u0012X\u0082T¢\u0006\u0002\n\u0000R\u000e\u00103\u001a\u00020\u0012X\u0082T¢\u0006\u0002\n\u0000R\u000e\u00104\u001a\u00020\u0005X\u0082T¢\u0006\u0002\n\u0000¨\u00065"}, d2 = {"Lcom/miui/pad/feature/notes/share/PadSnsEditActivity$Companion;", "", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "()V", "INTENT_CLASSNAME_SHARE_WECHAT", "", "INTENT_CLASSNAME_SHARE_WECHAT_MOMENTS", "EXTRA_KEY_IS_PRIVACY", "launchActivity", "", "launcher", "Landroidx/activity/result/ActivityResultLauncher;", "Landroid/content/Intent;", "activity", "Landroid/app/Activity;", "content", "title", "type", "", "themeId", "modifiedTime", "", "textSize", "", "base64", "workingNote", "Lcom/miui/notes/model/WorkingNote;", "aigcMeta", "Lcom/miui/notes/ai/AigcPicMetaData;", "time", HtmlParser.ImageElement.TAG, "Ljava/util/ArrayList;", "Landroid/net/Uri;", "Lkotlin/collections/ArrayList;", "bgIndex", "folderId", "EXTRA_KEY_TYPE", "EXTRA_KEY_THEME", "EXTRA_KEY_MODIFIED_TIME", "EXTRA_KEY_TEXTSIZE", "EXTRA_KEY_SHARE_PIC", "EXTRA_KEY_SHARE_PICS", "EXTRA_KEY_WORKING_NOTE", "EXTRA_KEY_SHARE_HAND_WRITE_BG_INDEX", "EXTRA_TYPE_WEIBO", "EXTRA_TYPE_AS_IMAGE", "EXTRA_TYPE_AS_HYBRID", "EXTRA_TYPE_AS_IMAGES", "TAG", "REQUEST_CODE_WEIBO", "REQUEST_CODE_AS_IMAGE", "POST_DELAYED", "KEY_HYBRID_IMAGES", "app_PhoneGlobalRelease"}, k = 1, mv = {2, 2, 0}, xi = 48)
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }

        public final void launchActivity(ActivityResultLauncher<Intent> launcher, Activity activity, String content, String title, int type, int themeId, long modifiedTime, float textSize, String base64, WorkingNote workingNote, AigcPicMetaData aigcMeta) {
            Intrinsics.checkNotNullParameter(launcher, "launcher");
            if (activity == null) {
                Logger.INSTANCE.d(PadSnsEditActivity.TAG, "activity is null");
                return;
            }
            Intent intent = new Intent(activity, (Class<?>) PadSnsEditActivity.class);
            intent.putExtra("android.intent.extra.TEXT", content);
            intent.putExtra("android.intent.extra.TITLE", title);
            intent.putExtra(PadSnsEditActivity.EXTRA_KEY_TYPE, type);
            intent.putExtra(PadSnsEditActivity.EXTRA_KEY_THEME, themeId);
            intent.putExtra(PadSnsEditActivity.EXTRA_KEY_MODIFIED_TIME, modifiedTime);
            intent.putExtra(PadSnsEditActivity.EXTRA_KEY_TEXTSIZE, textSize);
            if (aigcMeta != null) {
                intent.putExtra(SnsEditActivity.KEY_AIGC_META, aigcMeta.toStr());
            }
            intent.putExtra(PadSnsEditActivity.EXTRA_KEY_IS_PRIVACY, workingNote != null && workingNote.getFolderId() == -4);
            if (workingNote != null) {
                MindShareDataHolder.getInstance().setData(PadSnsEditActivity.EXTRA_KEY_WORKING_NOTE, workingNote);
            }
            MindShareDataHolder.getInstance().setData(PadSnsEditActivity.EXTRA_KEY_SHARE_PIC, base64);
            Bundle extras = intent.getExtras();
            Intrinsics.checkNotNull(extras);
            if (Utils.bundleIsSmall(extras)) {
                launcher.launch(intent);
            }
        }

        public final void launchActivity(ActivityResultLauncher<Intent> launcher, Activity activity, String content, String title, String time, int type, ArrayList<Uri> img, int bgIndex, long folderId, AigcPicMetaData aigcMeta) {
            Intrinsics.checkNotNullParameter(launcher, "launcher");
            Intent intent = new Intent(activity, (Class<?>) PadSnsEditActivity.class);
            intent.putExtra("android.intent.extra.TEXT", content);
            intent.putExtra("android.intent.extra.TITLE", title);
            intent.putExtra(IntentCompat.EXTRA_TIME, time);
            if (aigcMeta != null) {
                intent.putExtra(SnsEditActivity.KEY_AIGC_META, aigcMeta.toStr());
            }
            intent.putExtra(PadSnsEditActivity.EXTRA_KEY_TYPE, type);
            intent.putExtra(PadSnsEditActivity.EXTRA_KEY_IS_PRIVACY, folderId == -4);
            intent.putParcelableArrayListExtra(PadSnsEditActivity.EXTRA_KEY_SHARE_PICS, img);
            intent.putExtra(PadSnsEditActivity.EXTRA_KEY_SHARE_HAND_WRITE_BG_INDEX, bgIndex);
            launcher.launch(intent);
        }
    }
}
