package com.miui.notes.basefeature.noteedit;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.animation.Animator;
import android.animation.IntEvaluator;
import android.animation.ValueAnimator;
import android.app.Activity;
import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.ClipData;
import android.content.ComponentName;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.database.ContentObserver;
import android.graphics.Bitmap;
import android.graphics.Rect;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.os.ParcelFileDescriptor;
import android.os.RemoteException;
import android.os.Trace;
import android.text.TextUtils;
import android.util.Base64;
import android.view.DragEvent;
import android.view.Menu;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.webkit.ValueCallback;
import android.webkit.WebView;
import android.widget.FrameLayout;
import android.widget.PopupWindow;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.LifecycleOwnerKt;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import ch.qos.logback.core.CoreConstants;
import com.android.soundrecorder.ai.base.constants.BaseConstants;
import com.google.gson.Gson;
import com.market.sdk.reflect.ReflectUtilsForMiui;
import com.miui.ai.text.widget.download.AIcrDownloadHelper;
import com.miui.common.app.BaseNoteApp;
import com.miui.common.base.BaseActivity;
import com.miui.common.base.NoteLoadDataEntity;
import com.miui.common.constant.FrameConstant;
import com.miui.common.stat.EditStat;
import com.miui.common.stat.GlobalStat;
import com.miui.common.stat.NotesTextStat;
import com.miui.common.tool.AudioUtils;
import com.miui.common.tool.CompatUtils;
import com.miui.common.tool.DisplayUtils;
import com.miui.common.tool.DualScreenManager;
import com.miui.common.tool.HapticFeedbackTool;
import com.miui.common.tool.ImageUtils;
import com.miui.common.tool.LiteUtils;
import com.miui.common.tool.Logger;
import com.miui.common.tool.MiFontStyle;
import com.miui.common.tool.NoteAIRecorderConfig;
import com.miui.common.tool.RomUtils;
import com.miui.common.tool.UIUtils;
import com.miui.common.tool.Utils;
import com.miui.common.view.IPopMenu;
import com.miui.doodle.DoodleApplication;
import com.miui.doodle.base.DoodleParams;
import com.miui.doodle.document.DocumentReader;
import com.miui.doodle.feature.draw.DoodleBitmap;
import com.miui.floatwindow.FloatWindowManagerAgent;
import com.miui.hybirdeditor.EditorActionListener;
import com.miui.hybirdeditor.RichEditorWebView;
import com.miui.hybirdeditor.WebRichEditor;
import com.miui.hybirdeditor.WebRichEditorClient;
import com.miui.hybirdeditor.jsbridge.EmbedElementInfoBean;
import com.miui.hybirdeditor.jsbridge.HybridEditorScriptInterface;
import com.miui.hybirdeditor.jsbridge.TextStatusBean;
import com.miui.maml.component.MamlView;
import com.miui.mishare.tapshare.Util.TapShareConstant;
import com.miui.notes.R;
import com.miui.notes.action.comon.AiActionUtil;
import com.miui.notes.ai.AiController;
import com.miui.notes.ai.AiResultActon;
import com.miui.notes.ai.AigcPicMetaData;
import com.miui.notes.ai.IAiActionListener;
import com.miui.notes.ai.INoteAiEditor;
import com.miui.notes.ai.NoteAiFloatingWindow;
import com.miui.notes.ai.NoteAiTextWidgetService;
import com.miui.notes.ai.data.input.RequestType;
import com.miui.notes.ai.data.input.UserInputHolder;
import com.miui.notes.ai.floatingwindow.AiFloatingWindow;
import com.miui.notes.ai.internaltest.InteralTestInviteActivity;
import com.miui.notes.ai.request.AINotesViewModel;
import com.miui.notes.ai.request.AiNoteRequest;
import com.miui.notes.ai.request.NoteAIRequest;
import com.miui.notes.ai.stat.AITextWidgetStat;
import com.miui.notes.ai.stat.NoteContentStat;
import com.miui.notes.ai.ui.BottomSheetView;
import com.miui.notes.ai.ui.WaterMarkView;
import com.miui.notes.ai.utils.ContextWrapperUtil;
import com.miui.notes.ai.utils.FreeModeUtils;
import com.miui.notes.ai.utils.NoteSchemaUtil;
import com.miui.notes.ai.utils.PreferenceUtils;
import com.miui.notes.assist.CtaActivityResult;
import com.miui.notes.basefeature.AccountMgrCallback;
import com.miui.notes.basefeature.NotesNormalConstants;
import com.miui.notes.component.datafolder.FolderItemEntity;
import com.miui.notes.component.noteedit.IBaseNoteEditToolbarController;
import com.miui.notes.constant.NoteIntent;
import com.miui.notes.cta.CTAActivity;
import com.miui.notes.cta.CTAManager;
import com.miui.notes.doodle.feature.DoodleActivity;
import com.miui.notes.doodle.feature.DoodleFragment;
import com.miui.notes.doodle.feature.PadDoodleActivity;
import com.miui.notes.doodle.feature.PadFullDoodleActivity;
import com.miui.notes.feature.noteedit.HybridRichEditorDragListener;
import com.miui.notes.home.PrivateNotesListActivity;
import com.miui.notes.home.viewmodel.HomeViewModel;
import com.miui.notes.model.NoteEntity;
import com.miui.notes.model.NoteEntityHelper;
import com.miui.notes.model.WorkingNote;
import com.miui.notes.provider.Notes;
import com.miui.notes.theme.AnimTheme;
import com.miui.notes.theme.util.NoteThemeResCache;
import com.miui.notes.theme.util.ResourceManager;
import com.miui.notes.tool.AttachmentUtils;
import com.miui.notes.tool.ClipDataHelper;
import com.miui.notes.tool.PermissionNewUtils;
import com.miui.notes.tool.PermissionUtils;
import com.miui.notes.tool.tapshareutils.TapShareDataModel;
import com.miui.notes.tool.tapshareutils.TapShareDataPrepareUtils;
import com.miui.notes.tool.tapshareutils.TapShareSender;
import com.miui.notes.tool.util.CompatFileProvider;
import com.miui.notes.ui.BaseActionBarController;
import com.miui.notes.ui.BaseDragListener;
import com.miui.notes.ui.CryptoUtils;
import com.miui.notes.ui.LoadNoteThemeResTask;
import com.miui.notes.ui.SnsEditActivity;
import com.miui.notes.ui.view.INoteInfoHeader;
import com.miui.pad.feature.notes.commonedit.AiChooserMenu;
import com.miui.pad.feature.notes.multihandwrite.PadMultiHandWriteFragment;
import com.miui.richeditor.EditorActionMode;
import com.miui.richeditor.schema.NoteSchema;
import com.miui.richeditor.share.RenderBuilder;
import com.miui.richeditor.share.render.Render;
import com.miui.richeditor.theme.Theme;
import com.miui.richeditor.util.NoteClipManager;
import com.miui.richeditor.util.ResourceParser;
import com.xiaomi.accounts.secure.SecureDatabaseHelper;
import com.xiaomi.aiinput.AIInputResultListener;
import com.xiaomi.mirror.synergy.CallMethod;
import com.xiaomi.verificationsdk.internal.Constants;
import com.xiaomi.xms.ai.recorder.AIRecorderManager;
import com.xiaomi.xms.ai.recorder.UIKit;
import com.xiaomi.xms.ai.recorder.callback.AIRecorderServiceCallback;
import com.xiaomi.xms.ai.recorder.callback.AIRecorderServiceStateCallback;
import defpackage.R2;
import java.io.File;
import java.io.OutputStream;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ListIterator;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicBoolean;
import kotlin.Deprecated;
import kotlin.Lazy;
import kotlin.LazyKt;
import kotlin.Metadata;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.IntrinsicsKt;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.io.CloseableKt;
import kotlin.jvm.JvmStatic;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Ref;
import kotlin.text.Charsets;
import kotlin.text.Regex;
import kotlin.text.StringsKt;
import kotlinx.coroutines.BuildersKt;
import kotlinx.coroutines.BuildersKt__Builders_commonKt;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.Dispatchers;
import miuix.androidbasewidget.widget.LinkMovementMethod;
import miuix.animation.utils.EaseManager;
import miuix.appcompat.app.AlertDialog;
import miuix.appcompat.app.ProgressDialog;
import miuix.micloudview.accounts.ExtraAccountManager;
import miuix.os.DeviceHelper;
import miuix.os.Environment;
import miuix.springback.view.SpringBackLayout;
import miuix.text.util.Linkify;
import net.lingala.zip4j.util.InternalZipConstants;
import org.apache.commons.lang3.StringUtils;

/* JADX INFO: compiled from: BaseHybridNoteEditFragment.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000Ø\u0003\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0002\b\u0004\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0010\t\n\u0002\b\u000e\n\u0002\u0010\b\n\u0002\b\u000b\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u000e\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u000f\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0002\b\u000f\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0016\n\u0002\u0018\u0002\n\u0002\b\n\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0010\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0007\n\u0002\b\u000f\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0010\u0011\n\u0002\u0018\u0002\n\u0002\b@\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0012\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\f\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u000b\n\u0002\u0018\u0002\n\u0002\b\u0016\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b,\n\u0002\u0018\u0002\n\u0002\b\u0010\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0012\b&\u0018\u0000 \u0088\u00042\u00020\u00012\u00020\u0002:\u0010\u0081\u0004\u0082\u0004\u0083\u0004\u0084\u0004\u0085\u0004\u0086\u0004\u0087\u0004\u0088\u0004B\u0007¢\u0006\u0004\b\u0003\u0010\u0004J\b\u0010\u0016\u001a\u0004\u0018\u00010\u0015J\b\u0010t\u001a\u00020uH\u0002J\t\u0010ª\u0001\u001a\u00020uH\u0016J\t\u0010«\u0001\u001a\u00020\u0013H$J\t\u0010¬\u0001\u001a\u000205H\u0014J\u001e\u0010\u00ad\u0001\u001a\u00020u2\n\u0010®\u0001\u001a\u0005\u0018\u00010¯\u00012\u0007\u0010°\u0001\u001a\u00020\u000bH\u0014J\u0015\u0010±\u0001\u001a\u00020u2\n\u0010²\u0001\u001a\u0005\u0018\u00010³\u0001H\u0016J\u001e\u0010´\u0001\u001a\u00020u2\u0007\u0010µ\u0001\u001a\u00020Z2\n\u0010²\u0001\u001a\u0005\u0018\u00010³\u0001H\u0016J\u0012\u0010¶\u0001\u001a\u00020u2\u0007\u0010µ\u0001\u001a\u00020ZH\u0002J\t\u0010·\u0001\u001a\u00020uH\u0002J\u001d\u0010¸\u0001\u001a\u00020u2\t\u0010¹\u0001\u001a\u0004\u0018\u00010\u001d2\t\u0010º\u0001\u001a\u0004\u0018\u00010\u0006J\u001d\u0010»\u0001\u001a\u00020u2\t\u0010¼\u0001\u001a\u0004\u0018\u00010\u00192\t\u0010¹\u0001\u001a\u0004\u0018\u00010\u001dJ\u001d\u0010½\u0001\u001a\u00020u2\t\u0010¼\u0001\u001a\u0004\u0018\u00010\u00192\t\u0010¹\u0001\u001a\u0004\u0018\u00010\u001dJ\u0015\u0010¾\u0001\u001a\u00020\u00062\n\u0010¿\u0001\u001a\u0005\u0018\u00010³\u0001H\u0002J\t\u0010À\u0001\u001a\u00020uH\u0002J\t\u0010Á\u0001\u001a\u00020uH\u0016J\t\u0010Â\u0001\u001a\u00020uH\u0016J\t\u0010Ã\u0001\u001a\u00020uH\u0016J\t\u0010Ä\u0001\u001a\u00020uH\u0016J\t\u0010Å\u0001\u001a\u00020uH\u0016J\t\u0010Æ\u0001\u001a\u00020uH\u0016J\t\u0010Ç\u0001\u001a\u00020uH\u0004J\u0013\u0010È\u0001\u001a\u00020u2\b\u0010É\u0001\u001a\u00030Ê\u0001H\u0016J\u0012\u0010Ë\u0001\u001a\u00020u2\u0007\u0010Ì\u0001\u001a\u00020\u0019H\u0014J\u0015\u0010Í\u0001\u001a\u00020\u000b2\n\u0010²\u0001\u001a\u0005\u0018\u00010³\u0001H\u0014J\u0014\u0010Î\u0001\u001a\u00020u2\t\u0010¼\u0001\u001a\u0004\u0018\u00010\u0019H\u0004J\u0014\u0010Ï\u0001\u001a\u00020u2\t\u0010¼\u0001\u001a\u0004\u0018\u00010\u0019H\u0016J\t\u0010Ð\u0001\u001a\u00020\u000bH\u0016J\t\u0010Ñ\u0001\u001a\u00020uH\u0014J\t\u0010Ò\u0001\u001a\u00020uH\u0016J\u0011\u0010Ó\u0001\u001a\u00020u2\b\u0010Ô\u0001\u001a\u00030Õ\u0001J\u001b\u0010Ö\u0001\u001a\u00020u2\u0007\u0010×\u0001\u001a\u00020&2\u0007\u0010Ø\u0001\u001a\u00020&H\u0016J\u0013\u0010Ù\u0001\u001a\u00020u2\b\u0010Ú\u0001\u001a\u00030Û\u0001H\u0016J\u0012\u0010Ü\u0001\u001a\u00020u2\u0007\u0010Ý\u0001\u001a\u00020ZH\u0014J\t\u0010Þ\u0001\u001a\u00020uH\u0014J\n\u0010ß\u0001\u001a\u00030à\u0001H\u0014J\u0012\u0010á\u0001\u001a\u00020u2\u0007\u0010Ý\u0001\u001a\u00020ZH\u0015J\u001b\u0010á\u0001\u001a\u00020u2\u0007\u0010Ý\u0001\u001a\u00020Z2\u0007\u0010â\u0001\u001a\u000205H\u0014J\t\u0010ä\u0001\u001a\u00020uH\u0014J\u0012\u0010å\u0001\u001a\u00020u2\u0007\u0010æ\u0001\u001a\u00020\u000bH\u0014J$\u0010ì\u0001\u001a\u00020u2\u0007\u0010â\u0001\u001a\u0002052\u0007\u0010í\u0001\u001a\u00020\u000b2\u0007\u0010î\u0001\u001a\u00020\u000bH\u0014J(\u0010ï\u0001\u001a\u00020u2\b\u0010ð\u0001\u001a\u00030ñ\u00012\n\u0010ò\u0001\u001a\u0005\u0018\u00010ó\u00012\u0007\u0010í\u0001\u001a\u00020\u000bH\u0014J\u001c\u0010ô\u0001\u001a\u00020u2\b\u0010ð\u0001\u001a\u00030ñ\u00012\u0007\u0010í\u0001\u001a\u00020\u000bH\u0004J\u001c\u0010ù\u0001\u001a\u00020u2\b\u0010ð\u0001\u001a\u00030ñ\u00012\u0007\u0010í\u0001\u001a\u00020\u000bH\u0004J&\u0010ú\u0001\u001a\u00020u2\n\u0010ð\u0001\u001a\u0005\u0018\u00010ñ\u00012\t\u0010í\u0001\u001a\u0004\u0018\u00010\u000bH\u0004¢\u0006\u0003\u0010û\u0001J\t\u0010ü\u0001\u001a\u00020uH\u0014J\u001f\u0010ý\u0001\u001a\u00020u2\n\u0010þ\u0001\u001a\u0005\u0018\u00010ñ\u00012\b\u0010ÿ\u0001\u001a\u00030\u009f\u0001H\u0004J\t\u0010\u0080\u0002\u001a\u00020uH\u0014J\t\u0010\u0081\u0002\u001a\u00020uH\u0014J\u0013\u0010\u0082\u0002\u001a\u00020u2\b\u0010\u0083\u0002\u001a\u00030Õ\u0001H\u0014J$\u0010\u0084\u0002\u001a\u00020u2\b\u0010\u0085\u0002\u001a\u00030\u0086\u00022\u000f\u0010\u0087\u0002\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\u00190AH\u0014J\u0014\u0010\u0088\u0002\u001a\u0005\u0018\u00010\u0086\u00022\b\u0010\u0089\u0002\u001a\u00030Õ\u0001J\u0012\u0010\u008a\u0002\u001a\u00020u2\u0007\u0010\u008b\u0002\u001a\u000205H\u0014J4\u0010\u008c\u0002\u001a\u00020u2\u0007\u0010\u008d\u0002\u001a\u00020\u000b2\u001a\u0010\u008e\u0002\u001a\u000e\u0012\t\b\u0001\u0012\u0005\u0018\u00010\u0090\u00020\u008f\u0002\"\u0005\u0018\u00010\u0090\u0002H\u0016¢\u0006\u0003\u0010\u0091\u0002J\u0014\u0010\u0092\u0002\u001a\u00020u2\t\u0010\u0093\u0002\u001a\u0004\u0018\u00010\u0006H\u0016J\u0014\u0010\u0094\u0002\u001a\u00020u2\t\u0010\u0093\u0002\u001a\u0004\u0018\u00010\u0006H\u0016J\u001d\u0010\u0095\u0002\u001a\u00020u2\t\u0010\u0096\u0002\u001a\u0004\u0018\u00010\u00062\t\u0010\u0097\u0002\u001a\u0004\u0018\u00010\u0006J\u0014\u0010\u0098\u0002\u001a\u0004\u0018\u00010\u00062\t\u0010\u0099\u0002\u001a\u0004\u0018\u00010\u0006J\u0014\u0010\u009a\u0002\u001a\u00020u2\t\u0010\u0096\u0002\u001a\u0004\u0018\u00010\u0006H\u0016J\u0012\u0010\u009b\u0002\u001a\u00020u2\t\u0010\u0096\u0002\u001a\u0004\u0018\u00010\u0006J\u0014\u0010\u009c\u0002\u001a\u00020\u001d2\t\u0010¼\u0001\u001a\u0004\u0018\u00010\u0019H\u0004J\t\u0010\u009d\u0002\u001a\u00020uH\u0016J\u0007\u0010\u009e\u0002\u001a\u00020uJ\u0012\u0010\u009f\u0002\u001a\u00020\u000b2\u0007\u0010 \u0002\u001a\u00020\u000bH\u0016J\u0010\u0010¤\u0002\u001a\u00020\u00062\u0007\u0010¥\u0002\u001a\u00020\u0006J\u0010\u0010¦\u0002\u001a\u00020u2\u0007\u0010¥\u0002\u001a\u00020\u0006J\u0007\u0010§\u0002\u001a\u00020uJ\t\u0010¨\u0002\u001a\u00020uH\u0016J\u001d\u0010§\u0002\u001a\u00020u2\t\u0010Ì\u0001\u001a\u0004\u0018\u00010\u00062\t\u0010©\u0002\u001a\u0004\u0018\u00010\u0006J\u0012\u0010§\u0002\u001a\u00020u2\t\u0010Ì\u0001\u001a\u0004\u0018\u00010\u0006J\u0007\u0010ª\u0002\u001a\u00020uJ\u0007\u0010«\u0002\u001a\u00020uJ\u0010\u0010¬\u0002\u001a\u00020u2\u0007\u0010\u00ad\u0002\u001a\u000205J\t\u0010®\u0002\u001a\u00020uH\u0016J\t\u0010¯\u0002\u001a\u00020\u000bH\u0014J\u0007\u0010°\u0002\u001a\u00020\u000bJ\t\u0010±\u0002\u001a\u00020\u000bH\u0016J\u0012\u0010²\u0002\u001a\u00020u2\u0007\u0010³\u0002\u001a\u000205H\u0016J\u0010\u0010µ\u0002\u001a\u00020u2\u0007\u0010¶\u0002\u001a\u000205J\u0019\u0010·\u0002\u001a\u00020u2\u0007\u0010¸\u0002\u001a\u00020\u000b2\u0007\u0010í\u0001\u001a\u00020\u000bJ\u0013\u0010¹\u0002\u001a\u00020u2\b\u0010º\u0002\u001a\u00030Õ\u0001H\u0016J\u0013\u0010»\u0002\u001a\u00020u2\b\u0010º\u0002\u001a\u00030Õ\u0001H\u0016J\u0013\u0010¼\u0002\u001a\u00020u2\b\u0010º\u0002\u001a\u00030Õ\u0001H\u0004J\u0013\u0010½\u0002\u001a\u00020u2\b\u0010º\u0002\u001a\u00030Õ\u0001H\u0004J\u0007\u0010¾\u0002\u001a\u00020uJ\u0007\u0010¿\u0002\u001a\u00020uJ\u0007\u0010À\u0002\u001a\u00020uJ\u0007\u0010Á\u0002\u001a\u00020uJ\u001d\u0010Â\u0002\u001a\u00020u2\u000e\u0010Ã\u0002\u001a\t\u0012\u0004\u0012\u00020\u00060\u008f\u0002¢\u0006\u0003\u0010Ä\u0002J\u0010\u0010Å\u0002\u001a\u00020u2\u0007\u0010Æ\u0002\u001a\u00020\u000bJ\u001d\u0010Ç\u0002\u001a\u00020u2\t\u0010È\u0002\u001a\u0004\u0018\u00010C2\u0007\u0010í\u0001\u001a\u00020\u000bH\u0016J&\u0010Ç\u0002\u001a\u00020u2\t\u0010È\u0002\u001a\u0004\u0018\u00010C2\u0007\u0010É\u0002\u001a\u00020\u000b2\u0007\u0010í\u0001\u001a\u00020\u000bH\u0014J\t\u0010Ê\u0002\u001a\u00020uH\u0014J\t\u0010Ë\u0002\u001a\u00020uH\u0014J\t\u0010Ì\u0002\u001a\u00020uH\u0014J\u0012\u0010Í\u0002\u001a\u00020u2\u0007\u0010É\u0002\u001a\u00020\u000bH\u0014J\u0012\u0010Î\u0002\u001a\u00020u2\u0007\u0010É\u0002\u001a\u00020\u000bH\u0004J\t\u0010Ï\u0002\u001a\u00020uH\u0004J1\u0010Ð\u0002\u001a\u0014\u0012\u0004\u0012\u00020\u00060Ñ\u0002j\t\u0012\u0004\u0012\u00020\u0006`Ò\u00022\u000e\u0010Ó\u0002\u001a\t\u0012\u0004\u0012\u00020\u00060\u008f\u0002H\u0002¢\u0006\u0003\u0010Ô\u0002J)\u0010Õ\u0002\u001a\u0005\u0018\u00010\u0090\u00022\b\u0010Ö\u0002\u001a\u00030×\u00022\b\u0010Ø\u0002\u001a\u00030Ù\u00022\u0007\u0010Ú\u0002\u001a\u00020\u000bH\u0002J\u0019\u0010Û\u0002\u001a\u00070Ü\u0002R\u00020\u00012\t\u0010¼\u0001\u001a\u0004\u0018\u00010\u0019H\u0014J\u0014\u0010Ý\u0002\u001a\u00020\u000b2\t\u0010¼\u0001\u001a\u0004\u0018\u00010\u0019H\u0014J\t\u0010Þ\u0002\u001a\u00020uH\u0014J\t\u0010ß\u0002\u001a\u000205H\u0014J\t\u0010à\u0002\u001a\u00020uH\u0014J\u0007\u0010á\u0002\u001a\u00020uJ\t\u0010â\u0002\u001a\u00020uH\u0015J\t\u0010ã\u0002\u001a\u00020uH\u0016J\u001b\u0010ä\u0002\u001a\u00020u2\u0007\u0010¹\u0001\u001a\u00020\u001d2\u0007\u0010å\u0002\u001a\u00020\u000bH\u0014J\t\u0010æ\u0002\u001a\u00020uH\u0014J\t\u0010ç\u0002\u001a\u00020uH\u0014J!\u0010è\u0002\u001a\u00020u2\n\u0010é\u0002\u001a\u0005\u0018\u00010\u0090\u00022\n\u0010ê\u0002\u001a\u0005\u0018\u00010\u0090\u0002H\u0016J\u001a\u0010ë\u0002\u001a\u00020u2\u0007\u0010ì\u0002\u001a\u00020m2\b\u0010ð\u0001\u001a\u00030ñ\u0001J\u0013\u0010í\u0002\u001a\u00020u2\n\u0010î\u0002\u001a\u0005\u0018\u00010ï\u0002J/\u0010ð\u0002\u001a\u00020u2\n\u0010ñ\u0002\u001a\u0005\u0018\u00010ò\u00022\u0007\u0010ó\u0002\u001a\u0002052\u0007\u0010ô\u0002\u001a\u0002052\b\u0010õ\u0002\u001a\u00030ö\u0002J\u001f\u0010÷\u0002\u001a\u0005\u0018\u00010\u0090\u00022\n\u0010Ö\u0002\u001a\u0005\u0018\u00010×\u00022\u0007\u0010ø\u0002\u001a\u00020fJ(\u0010÷\u0002\u001a\u0005\u0018\u00010\u0090\u00022\n\u0010Ö\u0002\u001a\u0005\u0018\u00010×\u00022\u0007\u0010ø\u0002\u001a\u00020f2\u0007\u0010ù\u0002\u001a\u000205J\t\u0010û\u0002\u001a\u00020uH\u0002J\t\u0010ü\u0002\u001a\u00020uH\u0002J\t\u0010ý\u0002\u001a\u00020uH\u0014J\u0007\u0010þ\u0002\u001a\u00020uJ\u0007\u0010ÿ\u0002\u001a\u00020uJ2\u0010\u0080\u0003\u001a\u00020u2\u0007\u0010\u008b\u0002\u001a\u0002052\u0007\u0010\u0081\u0003\u001a\u0002052\t\u0010Ã\u0002\u001a\u0004\u0018\u00010\u00062\n\u0010\u0082\u0003\u001a\u0005\u0018\u00010\u0083\u0003H\u0014J\t\u0010\u0084\u0003\u001a\u00020uH\u0014J\t\u0010\u0085\u0003\u001a\u00020uH\u0014J\u0012\u0010\u0086\u0003\u001a\u00020u2\u0007\u0010\u0087\u0003\u001a\u00020\u000bH\u0007J\u001c\u0010\u0088\u0003\u001a\u00020\u000b2\b\u0010\u0089\u0003\u001a\u00030\u008a\u00032\u0007\u0010\u008b\u0003\u001a\u00020\u000bH\u0016J\u001c\u0010\u008c\u0003\u001a\u00020\u000b2\b\u0010\u008d\u0003\u001a\u00030\u008e\u00032\u0007\u0010\u008b\u0003\u001a\u00020\u000bH\u0016J\u0013\u0010\u008f\u0003\u001a\u00020u2\b\u0010\u0090\u0003\u001a\u00030³\u0001H\u0016J\u0013\u0010\u0091\u0003\u001a\u00020u2\b\u0010\u008d\u0003\u001a\u00030\u008e\u0003H\u0016J\t\u0010\u0093\u0003\u001a\u00020uH\u0004J\t\u0010\u0094\u0003\u001a\u00020uH\u0015J\u0007\u0010\u0095\u0003\u001a\u00020uJ\u0012\u0010\u0096\u0003\u001a\u00020u2\t\u0010\u0093\u0002\u001a\u0004\u0018\u00010\u0006J\u0012\u0010\u0097\u0003\u001a\u00020u2\t\u0010\u0093\u0002\u001a\u0004\u0018\u00010\u0006J\u001c\u0010\u0098\u0003\u001a\u00020u2\b\u0010\u0099\u0003\u001a\u00030\u009a\u00032\t\u0010\u009b\u0003\u001a\u0004\u0018\u00010\u0006J\t\u0010\u009c\u0003\u001a\u00020uH\u0016J\t\u0010\u009d\u0003\u001a\u00020uH\u0004J\t\u0010¢\u0003\u001a\u00020uH\u0014J\t\u0010£\u0003\u001a\u00020uH\u0002J\u0012\u0010¤\u0003\u001a\u00020u2\u0007\u0010¥\u0003\u001a\u00020ZH\u0014J\t\u0010¦\u0003\u001a\u00020uH\u0004J\u0012\u0010§\u0003\u001a\u00020u2\u0007\u0010\u008b\u0002\u001a\u000205H\u0004J\t\u0010¨\u0003\u001a\u00020uH\u0002J\t\u0010©\u0003\u001a\u00020uH\u0014J\u0007\u0010ª\u0003\u001a\u00020\u000bJ\t\u0010«\u0003\u001a\u00020\u000bH\u0002J\t\u0010¬\u0003\u001a\u00020uH\u0016J\t\u0010®\u0003\u001a\u00020uH\u0004J\t\u0010¯\u0003\u001a\u00020uH\u0002J\t\u0010´\u0003\u001a\u00020uH\u0004J\u0007\u0010µ\u0003\u001a\u00020uJ$\u0010¶\u0003\u001a\u00020u2\t\u0010·\u0003\u001a\u0004\u0018\u00010\u00062\u0007\u0010¸\u0003\u001a\u00020\u000b2\u0007\u0010¹\u0003\u001a\u000205J\u0007\u0010º\u0003\u001a\u00020uJ\u0012\u0010»\u0003\u001a\u00020u2\t\u0010\u0096\u0002\u001a\u0004\u0018\u00010\u0006J\t\u0010¼\u0003\u001a\u00020\u000bH\u0002J\t\u0010½\u0003\u001a\u00020uH\u0016J&\u0010¾\u0003\u001a\u00020u2\t\u0010Ì\u0001\u001a\u0004\u0018\u00010\u00062\u0007\u0010¿\u0003\u001a\u00020\u000b2\u0007\u0010À\u0003\u001a\u00020\u000bH\u0016J\t\u0010Á\u0003\u001a\u00020uH\u0016J(\u0010Â\u0003\u001a\u00020u2\u0007\u0010Ã\u0003\u001a\u00020\u000b2\t\u0010Ì\u0001\u001a\u0004\u0018\u00010\u00062\t\u0010Ä\u0003\u001a\u0004\u0018\u00010\u0006H\u0016J\t\u0010Å\u0003\u001a\u00020uH\u0016J\t\u0010Æ\u0003\u001a\u00020uH\u0016J\u001b\u0010Ç\u0003\u001a\u00020u2\u0007\u0010È\u0003\u001a\u00020\u000b2\u0007\u0010É\u0003\u001a\u000205H\u0016J\u0014\u0010Ê\u0003\u001a\u00020u2\t\u0010Ì\u0001\u001a\u0004\u0018\u00010\u0006H\u0016J\u0007\u0010Ë\u0003\u001a\u00020\u000bJ\u0007\u0010Ì\u0003\u001a\u00020\u000bJ\t\u0010Í\u0003\u001a\u00020uH\u0016J\t\u0010Î\u0003\u001a\u00020uH\u0016J\t\u0010Ï\u0003\u001a\u00020uH\u0016J\t\u0010Ð\u0003\u001a\u00020uH\u0016J\t\u0010Ñ\u0003\u001a\u00020uH\u0016J\t\u0010Ò\u0003\u001a\u00020uH\u0016J\t\u0010Ó\u0003\u001a\u00020uH\u0016J\u001d\u0010Ô\u0003\u001a\u00020u2\u0007\u0010Ì\u0001\u001a\u00020\u00062\t\u0010Ä\u0003\u001a\u0004\u0018\u00010\u0006H\u0016J\u001f\u0010Õ\u0003\u001a\u00020u2\t\u0010Ì\u0001\u001a\u0004\u0018\u00010\u00062\t\u0010Ä\u0003\u001a\u0004\u0018\u00010\u0006H\u0016J\t\u0010Ö\u0003\u001a\u00020uH\u0016J\t\u0010×\u0003\u001a\u00020uH\u0016J\t\u0010Ø\u0003\u001a\u00020uH\u0016J\u0014\u0010Ù\u0003\u001a\u00020u2\t\u0010Ú\u0003\u001a\u0004\u0018\u00010\u0006H\u0016J\u0019\u0010Û\u0003\u001a\u00020u2\u0007\u0010Ü\u0003\u001a\u00020\u000b2\u0007\u0010Ý\u0003\u001a\u00020\u000bJ\u0013\u0010Þ\u0003\u001a\u00020u2\n\u0010ß\u0003\u001a\u0005\u0018\u00010à\u0003J\u0010\u0010á\u0003\u001a\u00020u2\u0007\u0010â\u0003\u001a\u000205J\u0012\u0010ã\u0003\u001a\u00020u2\t\u0010Ä\u0003\u001a\u0004\u0018\u00010\u0006J\u0012\u0010ä\u0003\u001a\u00020u2\t\u0010å\u0003\u001a\u0004\u0018\u00010\u0006JA\u0010æ\u0003\u001a\u00020u2\u0007\u0010ç\u0003\u001a\u00020\u000b2\u0007\u0010è\u0003\u001a\u00020\u000b2\u0007\u0010é\u0003\u001a\u00020\u000b2\u0007\u0010ê\u0003\u001a\u00020\u000b2\t\u0010ë\u0003\u001a\u0004\u0018\u00010\u00062\t\u0010ì\u0003\u001a\u0004\u0018\u00010\u0006J\u0007\u0010í\u0003\u001a\u00020uJ*\u0010¦\u0003\u001a\u00020u2\t\u0010î\u0003\u001a\u0004\u0018\u00010\u00062\n\u0010ï\u0003\u001a\u0005\u0018\u00010\u009f\u00012\b\u0010ð\u0003\u001a\u00030ñ\u0003H\u0002J\u001c\u0010ò\u0003\u001a\u00020u2\u0007\u0010î\u0003\u001a\u00020\u00062\b\u0010ð\u0003\u001a\u00030ñ\u0003H\u0002J\u001d\u0010ó\u0003\u001a\u00020u2\t\u0010¼\u0001\u001a\u0004\u0018\u00010\u00192\u0007\u0010ô\u0003\u001a\u00020\u000bH\u0002J\u001f\u0010õ\u0003\u001a\u00020u2\n\u0010ð\u0003\u001a\u0005\u0018\u00010ñ\u00032\b\u0010ö\u0003\u001a\u00030÷\u0003H\u0004J\u0015\u0010ø\u0003\u001a\u00020u2\n\u0010ö\u0003\u001a\u0005\u0018\u00010÷\u0003H\u0002J\u0013\u0010ù\u0003\u001a\u00020u2\b\u0010ð\u0003\u001a\u00030ñ\u0003H\u0004J\u0013\u0010ú\u0003\u001a\u00020u2\b\u0010ð\u0003\u001a\u00030ñ\u0003H\u0004J\u0013\u0010û\u0003\u001a\u00020u2\b\u0010ð\u0003\u001a\u00030ñ\u0003H\u0004J\u001e\u0010ü\u0003\u001a\u00020u2\b\u0010ð\u0003\u001a\u00030ñ\u00032\t\u0010µ\u0001\u001a\u0004\u0018\u00010ZH\u0004J\u0012\u0010ý\u0003\u001a\u00020u2\u0007\u0010þ\u0003\u001a\u00020\u000bH\u0002J\u0007\u0010ÿ\u0003\u001a\u00020uJ\u0007\u0010\u0080\u0004\u001a\u00020\u000bR\u0016\u0010\u0005\u001a\u00020\u0006X\u0096D¢\u0006\n\n\u0002\b\t\u001a\u0004\b\u0007\u0010\bR\u0012\u0010\n\u001a\u00020\u000b8\u0004@\u0004X\u0085\u000e¢\u0006\u0002\n\u0000R\u0014\u0010\f\u001a\u0004\u0018\u00010\r8\u0004@\u0004X\u0085\u000e¢\u0006\u0002\n\u0000R\u0014\u0010\u000e\u001a\u0004\u0018\u00010\u000f8\u0004@\u0004X\u0085\u000e¢\u0006\u0002\n\u0000R\u0014\u0010\u0010\u001a\u0004\u0018\u00010\u00118\u0004@\u0004X\u0085\u000e¢\u0006\u0002\n\u0000R\u0010\u0010\u0012\u001a\u0004\u0018\u00010\u0013X\u0082\u000e¢\u0006\u0002\n\u0000R\u0014\u0010\u0014\u001a\u0004\u0018\u00010\u00158\u0004@\u0004X\u0085\u000e¢\u0006\u0002\n\u0000R\u0014\u0010\u0017\u001a\u0004\u0018\u00010\u00118\u0004@\u0004X\u0085\u000e¢\u0006\u0002\n\u0000R\u0014\u0010\u0018\u001a\u0004\u0018\u00010\u00198\u0004@\u0004X\u0085\u000e¢\u0006\u0002\n\u0000R\u0014\u0010\u001a\u001a\u0004\u0018\u00010\u001b8\u0004@\u0004X\u0085\u000e¢\u0006\u0002\n\u0000R\u0014\u0010\u001c\u001a\u0004\u0018\u00010\u001d8\u0004@\u0004X\u0085\u000e¢\u0006\u0002\n\u0000R\u001c\u0010\u001e\u001a\u0004\u0018\u00010\u0006X\u0084\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u001f\u0010\b\"\u0004\b \u0010!R\u001c\u0010\"\u001a\u0004\u0018\u00010\u0006X\u0084\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b#\u0010\b\"\u0004\b$\u0010!R\u001a\u0010%\u001a\u00020&X\u0084\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b'\u0010(\"\u0004\b)\u0010*R\u001c\u0010+\u001a\u0004\u0018\u00010\u0006X\u0084\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b,\u0010\b\"\u0004\b-\u0010!R\u001c\u0010.\u001a\u0004\u0018\u00010\u0006X\u0084\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b/\u0010\b\"\u0004\b0\u0010!R\u001a\u00101\u001a\u00020&X\u0084\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b2\u0010(\"\u0004\b3\u0010*R\u0012\u00104\u001a\u0002058\u0006@\u0006X\u0087\u000e¢\u0006\u0002\n\u0000R\u0012\u00106\u001a\u00020&8\u0006@\u0006X\u0087\u000e¢\u0006\u0002\n\u0000R\u0012\u00107\u001a\u00020\u000b8\u0004@\u0004X\u0085\u000e¢\u0006\u0002\n\u0000R\u001c\u00108\u001a\u0004\u0018\u00010\u0006X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b9\u0010\b\"\u0004\b:\u0010!R\u0012\u0010;\u001a\u00020\u000b8\u0004@\u0004X\u0085\u000e¢\u0006\u0002\n\u0000R\u0012\u0010<\u001a\u00020\u000b8\u0004@\u0004X\u0085\u000e¢\u0006\u0002\n\u0000R\u0012\u0010=\u001a\u00020\u000b8\u0004@\u0004X\u0085\u000e¢\u0006\u0002\n\u0000R\u0012\u0010>\u001a\u00020\u000b8\u0006@\u0006X\u0087\u000e¢\u0006\u0002\n\u0000R\u000e\u0010?\u001a\u00020\u000bX\u0082\u000e¢\u0006\u0002\n\u0000R\u0018\u0010@\u001a\f\u0012\u0006\u0012\u0004\u0018\u00010\u0019\u0018\u00010AX\u0082\u000e¢\u0006\u0002\n\u0000R\u0010\u0010B\u001a\u0004\u0018\u00010CX\u0082\u000e¢\u0006\u0002\n\u0000R\u0018\u0010D\u001a\f\u0012\u0006\u0012\u0004\u0018\u00010\u0019\u0018\u00010AX\u0082\u000e¢\u0006\u0002\n\u0000R\u0018\u0010E\u001a\f\u0012\u0006\u0012\u0004\u0018\u00010\u0019\u0018\u00010AX\u0082\u000e¢\u0006\u0002\n\u0000R\u0018\u0010F\u001a\f\u0012\u0006\u0012\u0004\u0018\u00010\u0019\u0018\u00010AX\u0082\u000e¢\u0006\u0002\n\u0000R\u0018\u0010G\u001a\f\u0012\u0006\u0012\u0004\u0018\u00010\u0019\u0018\u00010AX\u0082\u000e¢\u0006\u0002\n\u0000R\u0016\u0010H\u001a\n\u0012\u0004\u0012\u00020\u0019\u0018\u00010AX\u0082\u000e¢\u0006\u0002\n\u0000R\u001a\u0010I\u001a\u00020\u000bX\u0084\u000e¢\u0006\u000e\n\u0000\u001a\u0004\bJ\u0010K\"\u0004\bL\u0010MR\u001c\u0010N\u001a\u0004\u0018\u00010\u0006X\u0084\u000e¢\u0006\u000e\n\u0000\u001a\u0004\bO\u0010\b\"\u0004\bP\u0010!R\u0010\u0010Q\u001a\u0004\u0018\u00010RX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010S\u001a\u000205X\u0082\u000e¢\u0006\u0002\n\u0000R\u0012\u0010T\u001a\u00020\u000b8\u0004@\u0004X\u0085\u000e¢\u0006\u0002\n\u0000R\u0010\u0010U\u001a\u0004\u0018\u00010VX\u0082\u000e¢\u0006\u0002\n\u0000R\u0010\u0010W\u001a\u0004\u0018\u00010XX\u0082\u000e¢\u0006\u0002\n\u0000R\u0010\u0010Y\u001a\u0004\u0018\u00010ZX\u0082\u000e¢\u0006\u0002\n\u0000R\u0010\u0010[\u001a\u0004\u0018\u00010\\X\u0082\u000e¢\u0006\u0002\n\u0000R\u001c\u0010]\u001a\u0004\u0018\u00010\u0006X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b^\u0010\b\"\u0004\b_\u0010!R\u000e\u0010`\u001a\u00020\u000bX\u0082\u000e¢\u0006\u0002\n\u0000R\u0010\u0010a\u001a\u0004\u0018\u00010bX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010c\u001a\u00020dX\u0082\u0004¢\u0006\u0002\n\u0000R\u0010\u0010e\u001a\u0004\u0018\u00010fX\u0082\u000e¢\u0006\u0002\n\u0000R\u0010\u0010g\u001a\u0004\u0018\u00010hX\u0082\u000e¢\u0006\u0002\n\u0000R\u0012\u0010i\u001a\u0004\u0018\u00010&X\u0082\u000e¢\u0006\u0004\n\u0002\u0010jR\u0010\u0010k\u001a\u0004\u0018\u00010\u0011X\u0082\u000e¢\u0006\u0002\n\u0000R\u0010\u0010l\u001a\u0004\u0018\u00010mX\u0082\u000e¢\u0006\u0002\n\u0000R\u0010\u0010n\u001a\u0004\u0018\u00010oX\u0082\u000e¢\u0006\u0002\n\u0000R\u0010\u0010p\u001a\u0004\u0018\u00010qX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010r\u001a\u00020sX\u0082\u0004¢\u0006\u0002\n\u0000R\u0010\u0010v\u001a\u0004\u0018\u00010wX\u0082\u000e¢\u0006\u0002\n\u0000R\u0010\u0010x\u001a\u0004\u0018\u00010yX\u0082\u000e¢\u0006\u0002\n\u0000R\u0010\u0010z\u001a\u0004\u0018\u00010\u0006X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010{\u001a\u00020&X\u0082\u000e¢\u0006\u0002\n\u0000R\u001a\u0010|\u001a\u00020\u000bX\u0084\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b|\u0010K\"\u0004\b}\u0010MR\u001d\u0010~\u001a\u0004\u0018\u00010\u0006X\u0084\u000e¢\u0006\u000f\n\u0000\u001a\u0004\b\u007f\u0010\b\"\u0005\b\u0080\u0001\u0010!R\u0012\u0010\u0081\u0001\u001a\u0005\u0018\u00010\u0082\u0001X\u0082\u000e¢\u0006\u0002\n\u0000R\u000f\u0010\u0083\u0001\u001a\u00020\u000bX\u0082\u000e¢\u0006\u0002\n\u0000R\u0018\u0010\u0084\u0001\u001a\u000b\u0012\u0004\u0012\u00020u\u0018\u00010\u0085\u0001X\u0082\u000e¢\u0006\u0002\n\u0000R\u0012\u0010\u0086\u0001\u001a\u0005\u0018\u00010\u0087\u0001X\u0082\u000e¢\u0006\u0002\n\u0000R\u0016\u0010\u0088\u0001\u001a\u00020\u0006X\u0086D¢\u0006\t\n\u0000\u001a\u0005\b\u0089\u0001\u0010\bR\u0016\u0010\u008a\u0001\u001a\u00020\u0006X\u0086D¢\u0006\t\n\u0000\u001a\u0005\b\u008b\u0001\u0010\bR\u0016\u0010\u008c\u0001\u001a\u00020\u0006X\u0086D¢\u0006\t\n\u0000\u001a\u0005\b\u008d\u0001\u0010\bR\u0016\u0010\u008e\u0001\u001a\u00020\u0006X\u0086D¢\u0006\t\n\u0000\u001a\u0005\b\u008f\u0001\u0010\bR\u0016\u0010\u0090\u0001\u001a\u00020\u0006X\u0086D¢\u0006\t\n\u0000\u001a\u0005\b\u0091\u0001\u0010\bR\u0016\u0010\u0092\u0001\u001a\u00020\u0006X\u0086D¢\u0006\t\n\u0000\u001a\u0005\b\u0093\u0001\u0010\bR\u0016\u0010\u0094\u0001\u001a\u00020\u0006X\u0086D¢\u0006\t\n\u0000\u001a\u0005\b\u0095\u0001\u0010\bR!\u0010\u0096\u0001\u001a\u00030\u0097\u00018BX\u0082\u0084\u0002¢\u0006\u0010\n\u0006\b\u009a\u0001\u0010\u009b\u0001\u001a\u0006\b\u0098\u0001\u0010\u0099\u0001R\u0015\u0010\u009c\u0001\u001a\u0004\u0018\u00010\u00068\u0004@\u0004X\u0085\u000e¢\u0006\u0002\n\u0000R\u0015\u0010\u009d\u0001\u001a\u0004\u0018\u00010\u00068\u0004@\u0004X\u0085\u000e¢\u0006\u0002\n\u0000R\u0012\u0010\u009e\u0001\u001a\u0005\u0018\u00010\u009f\u0001X\u0082\u000e¢\u0006\u0002\n\u0000R\u0011\u0010 \u0001\u001a\u0004\u0018\u00010\u0006X\u0082\u000e¢\u0006\u0002\n\u0000R\u000f\u0010¡\u0001\u001a\u00020\u000bX\u0082\u000e¢\u0006\u0002\n\u0000R\u001d\u0010¢\u0001\u001a\u00020\u000bX\u0084\u000e¢\u0006\u0010\n\u0000\u001a\u0005\b£\u0001\u0010K\"\u0005\b¤\u0001\u0010MR\u001f\u0010¥\u0001\u001a\u00020oX\u0086\u000e¢\u0006\u0012\n\u0000\u001a\u0006\b¦\u0001\u0010§\u0001\"\u0006\b¨\u0001\u0010©\u0001R\u0013\u0010ã\u0001\u001a\u00020\u000b8F¢\u0006\u0007\u001a\u0005\bã\u0001\u0010KR\u0014\u0010ç\u0001\u001a\u0002058F¢\u0006\b\u001a\u0006\bè\u0001\u0010é\u0001R\u0014\u0010ê\u0001\u001a\u0002058F¢\u0006\b\u001a\u0006\bë\u0001\u0010é\u0001R\u0018\u0010õ\u0001\u001a\u00030ö\u00018TX\u0094\u0004¢\u0006\b\u001a\u0006\b÷\u0001\u0010ø\u0001R\u0016\u0010¡\u0002\u001a\u00020\u000b8VX\u0096\u0004¢\u0006\u0007\u001a\u0005\b¡\u0002\u0010KR\u0017\u0010¢\u0002\u001a\u0002058VX\u0096\u0004¢\u0006\b\u001a\u0006\b£\u0002\u0010é\u0001R\u0013\u0010³\u0002\u001a\u00020&8F¢\u0006\u0007\u001a\u0005\b´\u0002\u0010(R\u0011\u0010ú\u0002\u001a\u0004\u0018\u00010oX\u0082\u000e¢\u0006\u0002\n\u0000R\u0014\u0010\u0092\u0003\u001a\u00020\u000bX¦\u0004¢\u0006\u0007\u001a\u0005\b\u0092\u0003\u0010KR\u0016\u0010\u009e\u0003\u001a\u00020\u00068BX\u0082\u0004¢\u0006\u0007\u001a\u0005\b\u009f\u0003\u0010\bR\u0013\u0010 \u0003\u001a\u00020\u00068F¢\u0006\u0007\u001a\u0005\b¡\u0003\u0010\bR\u0016\u0010\u00ad\u0003\u001a\u00020\u000b8DX\u0084\u0004¢\u0006\u0007\u001a\u0005\b\u00ad\u0003\u0010KR\u0012\u0010°\u0003\u001a\u0005\u0018\u00010±\u0003X\u0082\u0004¢\u0006\u0002\n\u0000R\u0012\u0010²\u0003\u001a\u0005\u0018\u00010³\u0003X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u0089\u0004"}, d2 = {"Lcom/miui/notes/basefeature/noteedit/BaseHybridNoteEditFragment;", "Lcom/miui/notes/basefeature/noteedit/BaseNormalNoteEditFragment;", "Lcom/miui/notes/ai/INoteAiEditor;", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "()V", "TAG", "", "getTAG", "()Ljava/lang/String;", "TAG$1", "mEnableEdit", "", "mBgContentContainer", "Lmiuix/springback/view/SpringBackLayout;", "mEditorWebView", "Lcom/miui/hybirdeditor/RichEditorWebView;", "mEditorMaskView", "Landroid/widget/FrameLayout;", "mJsBridge", "Lcom/miui/hybirdeditor/jsbridge/HybridEditorScriptInterface;", "mEditor", "Lcom/miui/hybirdeditor/WebRichEditor;", "getWebRichEditor", "mEditContainerLayout", "mPendingHandleIntent", "Landroid/content/Intent;", "mDragListener", "Lcom/miui/notes/feature/noteedit/HybridRichEditorDragListener;", "mWorkingNoteFromRestore", "Lcom/miui/notes/model/WorkingNote;", "mAiDoneData", "getMAiDoneData", "setMAiDoneData", "(Ljava/lang/String;)V", "mAiDoneData2", "getMAiDoneData2", "setMAiDoneData2", "mOriginalId", "", "getMOriginalId", "()J", "setMOriginalId", "(J)V", "mOriginalContent", "getMOriginalContent", "setMOriginalContent", "mOriginalTitle", "getMOriginalTitle", "setMOriginalTitle", "mEnterTimeForEditMode", "getMEnterTimeForEditMode", "setMEnterTimeForEditMode", "mClickCount", "", "mStartTime", "mIsStop", "mNewImageName", "getMNewImageName", "setMNewImageName", "mNeedHideEditToolBar", "mNeedVisible", "mIsPcMode", "mIsReport", "mIsCreated", "toCtaBeforeAITask", "Landroidx/activity/result/ActivityResultLauncher;", "mOldActionMode", "Lcom/miui/richeditor/EditorActionMode;", "toCtaBeforeAITextWidgetTask", "toGlobalPermissionLauncher", "toAudioRecorderPermissionLauncher", "toAudioRecorderShowLauncher", "toAddAccountLauncher", "mNotSaveData", "getMNotSaveData", "()Z", "setMNotSaveData", "(Z)V", "mAiData", "getMAiData", "setMAiData", "mAiController", "Lcom/miui/notes/ai/AiController;", "mDismissType", "mInRefreshAnim", "mAiBottomSheetView", "Lcom/miui/notes/ai/ui/BottomSheetView;", "mAiToast", "Landroid/widget/Toast;", "mAiView", "Landroid/view/View;", "mWaterMarkView", "Lcom/miui/notes/ai/ui/WaterMarkView;", "aigcData", "getAigcData", "setAigcData", "isFromNoteToolBarEnter", "excerptContentObserver", "Landroid/database/ContentObserver;", "isSetExcerptObserver", "Ljava/util/concurrent/atomic/AtomicBoolean;", "mRender", "Lcom/miui/richeditor/share/render/Render;", "mDialog", "Lmiuix/appcompat/app/ProgressDialog;", "mShowTime", "Ljava/lang/Long;", "mMamlViewContainer", "mRenderGenerateCallback", "Lcom/miui/notes/basefeature/noteedit/BaseNormalNoteEditFragment$RenderGenerateCallback;", "renderGenerateTask", "Ljava/lang/Runnable;", "mLocalDownloadDialog", "Landroid/app/Dialog;", "mAiReceiver", "Landroid/content/BroadcastReceiver;", "onPermissionAccept", "", "mAccountManagerCallback", "Lcom/miui/notes/basefeature/AccountMgrCallback;", "mAiRecorderConfig", "Lcom/miui/common/tool/NoteAIRecorderConfig;", "mPlayingAudioFileName", "mAIRecorderLastStartTime", "isStartAiTextWindow", "setStartAiTextWindow", "mLastAudioFileName", "getMLastAudioFileName", "setMLastAudioFileName", "mSummaryResultObserver", "Lcom/miui/notes/basefeature/noteedit/BaseHybridNoteEditFragment$SummaryResultObserver;", "mIsRecordRecordSummaryRegistered", "sumaryResultChangeCallback", "Lkotlin/Function0;", "mRegisteredContentResolver", "Landroid/content/ContentResolver;", "EXTRA_TAP_SHARE_SUPPORT", "getEXTRA_TAP_SHARE_SUPPORT", "EXTRA_TAP_SHARE_DEEP_LINK", "getEXTRA_TAP_SHARE_DEEP_LINK", "EXTRA_TAP_SHARE_DEEP_ACTION", "getEXTRA_TAP_SHARE_DEEP_ACTION", "EXTRA_TAP_SHARE_THUMBNAIL_URI", "getEXTRA_TAP_SHARE_THUMBNAIL_URI", "EXTRA_CONTENT_PREVIEW_NOTES", "getEXTRA_CONTENT_PREVIEW_NOTES", "EXTRA_TAP_SHARE_TITLE", "getEXTRA_TAP_SHARE_TITLE", "EXTRA_TAP_SHARE_SUBTITLE", "getEXTRA_TAP_SHARE_SUBTITLE", "mEditorActionListener", "Lcom/miui/hybirdeditor/EditorActionListener;", "getMEditorActionListener", "()Lcom/miui/hybirdeditor/EditorActionListener;", "mEditorActionListener$delegate", "Lkotlin/Lazy;", "mDoodleFileId", "mFormattedDoodleFileId", "mAiTextUseRect", "Landroid/graphics/Rect;", "mAITextUseValue", "mNeedSelectAll", "mCTAFirstAuthorization", "getMCTAFirstAuthorization", "setMCTAFirstAuthorization", "mEditEnableRunnable", "getMEditEnableRunnable", "()Ljava/lang/Runnable;", "setMEditEnableRunnable", "(Ljava/lang/Runnable;)V", "onEnterActionMode", "createJsBridge", "getThemeResId", "refreshNote", "noteEntity", "Lcom/miui/common/base/NoteLoadDataEntity;", "isFromPc", "onCreate", "savedInstanceState", "Landroid/os/Bundle;", "onViewInflated", "view", "frameBoost", "initRecordSummaryResultObserver", "trackReportClipBoard", "workingNote", "elementName", "trackReportOpenContent", "intent", "trackReportQuitContent", "getSource", SecureDatabaseHelper.TABLE_EXTRAS, "registerReceiver", "onStart", "onStop", "onResume", "onPause", "onDestroy", "onDestroyView", "onChangeFontStyle", "onPrepareOptionsMenu", "menu", "Landroid/view/Menu;", "setmEidtDoodleResult", "data", "handleInstanceState", "preHandleIntent", "onNewIntent", "isOnSaveInstanceCalled", "sendAsMarkdown", "onNoteThemeChanged", "onStartDragElement", "infoBean", "Lcom/miui/hybirdeditor/jsbridge/EmbedElementInfoBean;", "onClockAlertChanged", "newDate", "oldDate", "onConfigurationChanged", "newConfig", "Landroid/content/res/Configuration;", "initView", "rootView", "initNoteInfoHeader", "getActionBarBaseDragListener", "Lcom/miui/notes/ui/BaseDragListener;", "initNoteEditor", "themeId", "isInRecord", "resetEditContent", "setEnableEdit", "enableEdit", "widgetId", "getWidgetId", "()I", "widgetType", "getWidgetType", "updateNoteThemeOnNative", "withAnim", "forceUpdate", "onPostUpdateNoteTheme", "theme", "Lcom/miui/richeditor/theme/Theme;", "resCache", "Lcom/miui/notes/theme/util/NoteThemeResCache;", "updateEditContentBgStyleLite", "noteEditorPadding", "", "getNoteEditorPadding", "()F", "updateEditContentBgStyle", "updateEditorTheme", "(Lcom/miui/richeditor/theme/Theme;Ljava/lang/Boolean;)V", "responseEditUiState", "changePaddingOnThemeChanged", "newTheme", "headerNewPadding", "requestXiaoAiPermission", "updateRecordMode", "editImage", "embedElementInfo", "startToDoodle", "params", "Lcom/miui/doodle/base/DoodleParams;", "doodleResult", "createEditImageParams", "imageInfo", "addAttachment", "type", "insertImages", "needWithPos", "imageUris", "", "Landroid/net/Uri;", "(Z[Landroid/net/Uri;)V", "insertContent", "text", "insertTitle", "updateDoodle", "fileName", "oldField", "getOriginalFileId", "formattedId", "startInsertDoodle", "insertDoodle", "handleNewNotes", "onPagePrepared", "saveDoodle", "saveNote", Constants.FORCE, "isDarkMode", "deviceFlag", "getDeviceFlag", "getStringFromResource", "key", "showToast", "onFlowDone", "onStopDone", Notes.Data.DATA2, "notifyRefreshComplete", "notifySetNewContent", "onCorrectNumChanged", "changeCount", "onRefreshDone", "dismissAllDialogOrPanel", "dismissAiView", "onBackPressed", "onTextCountChanged", "textCount", "getTextCount", "onStateChanged", "state", "onRichTextPanelStateChanged", "isOpen", "onLinkElementClicked", "elementInfoBean", "onImageElementClicked", "viewImage", "viewLinkByBrowser", "onPopMenuHide", "onAnnotateImageInDocStart", "hidePopMenu", "onAnnotateImageInDocEnd", "onShareImage", "imgBase64", "([Ljava/lang/String;)V", "onDisableScroll", "disable", "updateActionMode", "actionMode", "showRichTextTool", "onViewMode", "onAudioMode", "onTitleMode", "onEditMode", "onFirstEditMode", "recordToView", "getUrisFromImages", "Ljava/util/ArrayList;", "Lkotlin/collections/ArrayList;", "imageBase64Datas", "([Ljava/lang/String;)Ljava/util/ArrayList;", "createImageTempUri", CoreConstants.CONTEXT_SCOPE_VALUE, "Landroid/content/Context;", DoodleBitmap.KEY_BITMAP, "Landroid/graphics/Bitmap;", "isPng", "getEditNoteLoadTask", "Lcom/miui/notes/basefeature/noteedit/BaseNormalNoteEditFragment$EditNoteLoadTask;", "handleSendIntent", "doneEdit", "getAutoSaveCommonDuration", "asyncSaveNoteInstantlyIfNeeded", "closeTriggerView", "updateWorkingTextAndTitle", "refreshAlertTime", "setWorkingNote", "forceEdit", "sendAsText", "sendAsImage", "startSharePageByRenderUri", "renderUri", "previewUri", "generateRender", CallMethod.ARG_CALLBACK, "generateAnimRender", "animTheme", "Lcom/miui/notes/theme/AnimTheme;", "createShareAnimRenderUri", "shareStyle", "Lcom/miui/richeditor/theme/Theme$ShareStyle;", "checkedRes", "unCheckedRes", "mamlView", "Lcom/miui/maml/component/MamlView;", "createRenderTempUri", "render", "validHeight", "mShowCreatingProgressDialogTask", "dismissProgressDialogWithJudge", "dismissProgressDialog", "sendAsDuplicate", "handleNoteContent", "createDuplicateAndSend", "launchSnsShareActivity", "requestCode", "aigcPicMetaData", "Lcom/miui/notes/ai/AigcPicMetaData;", "hideNote", "unhideNote", "invalidCurrentNote", "forceSave", "insertImageFromClipData", "clipData", "Landroid/content/ClipData;", "insertAtEnd", "insertImageFromDragEvent", "event", "Landroid/view/DragEvent;", "onSaveInstanceState", "outState", "onDragDropFromBlankView", "isNoteCreated", "clearAiTextWidgetIntentType", "handleAudioCTARequest", "doPaste", "replaceText", "insertText", "handleGetAiTextInfo", "aiInputResultListener", "Lcom/xiaomi/aiinput/AIInputResultListener;", "contentName", "showSoftInput", "showAiTextWindow", "contentStatus", "getContentStatus", "noteContentStatus", "getNoteContentStatus", "initFloatingWindow", "onWindowClosed", "showAttachmentAiChooser", "v", "startAiTextWidget", "startAiAction", "showAiDialog", "initBaseResultLauncher", "checkoutLoginState", "requestAiPermission", "onClickAudioBtnAboveOS2", "isAllowRecord", "initAIRecorderManager", "finishAiRecord", "mAiRecorderServiceCallback", "Lcom/xiaomi/xms/ai/recorder/callback/AIRecorderServiceCallback;", "mAiRecorderServiceStateCallback", "Lcom/xiaomi/xms/ai/recorder/callback/AIRecorderServiceStateCallback;", "startAIRecord", "stopAIRecord", "showAIRecorderComponent", "audioFileName", "isPlaying", "startTime", "showAIRecorderRecording", "getAudioDescription", "checkAIRecorderPermission", "doCopy", "addText", "firstFlow", "isThinkContent", "clearDoc", "finishFlow", "inNewPanel", "title", "onDone", "onFloatingWindowVisible", "onFloatingWindowClose", "save", "aiUsed", "insertSummary", "isAiCorrect", "isAiCheckType", "doneCorrection", "trimAIStatement", "saveScrollPosition", "restoreToSavedScrollPosition", "revert", "onDataLoading", "beforeRefreshNewContent", "setDataWithConfig", "setData", "refreshNewContent", "closeCorrectionPop", "clearFlowStatus", "stopInput", "endTip", "onUndoRedoStateChanged", "undoEnable", "redoEnable", "onTextStyleChanged", "statusBean", "Lcom/miui/hybirdeditor/jsbridge/TextStatusBean;", "performHaptic", "typeId", "onTitleChanged", "onSaveData", "richText", "onUpdateViewState", "canSelect", "canQuery", "canPhrase", "canTranslate", "selectData", "htmlData", "onChangeToMindMap", "value", "rect", "activity", "Landroid/app/Activity;", "reportAiClick", "trackReportEditMode", "isQuitEditMode", "showXiaoaiAcceptDialog", "acceptListener", "Lcom/miui/notes/tool/PermissionNewUtils$AcceptListener;", "tryLoginXiaomiAccount", "showOverlayPermissionDialog", "processCta", "doAfterCTAAccepted", "gotoRealAITextWidget", "showWaterMark", "show", "resetToolBarStaus", "isNewNoteFromMiShow", "NotesAccountAcceptListener", "SummaryResultObserver", "EditorActionListenerImpl", "HybridEditNoteLoadTask", "NoteWindowCloseListener", "NoteAIRecorderServiceCallback", "NotesAcceptListener", "Companion", "app_PhoneGlobalRelease"}, k = 1, mv = {2, 2, 0}, xi = 48)
public abstract class BaseHybridNoteEditFragment extends BaseNormalNoteEditFragment implements INoteAiEditor {

    /* JADX INFO: renamed from: Companion, reason: from kotlin metadata */
    public static final Companion INSTANCE = new Companion(null);
    private static final String EXTRA_KEY_IS_PRIVACY = "extra_is_privacy";
    private static boolean IN_TITLE = false;
    public static final int STATE_ERROR_NONE = 1;
    public static final int STATE_ERROR_NO_CTA = 2;
    public static final int STATE_ERROR_NO_PERMISSION = 3;
    public static final int STATE_ERROR_UNKNOW = 4;
    protected static final String TAG = "BaseHybridNoteEditFragment";
    private static boolean mFullScreen;
    protected static NoteAiFloatingWindow mNoteAiFloatingWindow;
    public static NoteAiTextWidgetService mNoteAiTextWidgetService;
    private String aigcData;
    private ContentObserver excerptContentObserver;
    private boolean isFromNoteToolBarEnter;
    private boolean isStartAiTextWindow;
    private long mAIRecorderLastStartTime;
    private AccountMgrCallback mAccountManagerCallback;
    private BottomSheetView mAiBottomSheetView;
    private AiController mAiController;
    private String mAiData;
    private String mAiDoneData;
    private String mAiDoneData2;
    private NoteAIRecorderConfig mAiRecorderConfig;
    private Rect mAiTextUseRect;
    private Toast mAiToast;
    private View mAiView;
    protected SpringBackLayout mBgContentContainer;
    private boolean mCTAFirstAuthorization;
    public int mClickCount;
    private ProgressDialog mDialog;
    protected String mDoodleFileId;
    protected HybridRichEditorDragListener mDragListener;
    protected FrameLayout mEditContainerLayout;
    protected WebRichEditor mEditor;
    protected FrameLayout mEditorMaskView;
    protected RichEditorWebView mEditorWebView;
    private long mEnterTimeForEditMode;
    protected String mFormattedDoodleFileId;
    protected boolean mInRefreshAnim;
    private boolean mIsCreated;
    protected boolean mIsPcMode;
    private boolean mIsRecordRecordSummaryRegistered;
    public boolean mIsReport;
    protected boolean mIsStop;
    private HybridEditorScriptInterface mJsBridge;
    private String mLastAudioFileName;
    private Dialog mLocalDownloadDialog;
    private FrameLayout mMamlViewContainer;
    protected boolean mNeedHideEditToolBar;
    private boolean mNeedSelectAll;
    private String mNewImageName;
    private boolean mNotSaveData;
    private String mOriginalContent;
    private long mOriginalId;
    private String mOriginalTitle;
    protected Intent mPendingHandleIntent;
    private String mPlayingAudioFileName;
    private ContentResolver mRegisteredContentResolver;
    private Render mRender;
    private BaseNormalNoteEditFragment.RenderGenerateCallback mRenderGenerateCallback;
    public long mStartTime;
    private SummaryResultObserver mSummaryResultObserver;
    private WaterMarkView mWaterMarkView;
    protected WorkingNote mWorkingNoteFromRestore;
    private Runnable renderGenerateTask;
    private Function0<Unit> sumaryResultChangeCallback;
    private ActivityResultLauncher<Intent> toAddAccountLauncher;
    private ActivityResultLauncher<Intent> toAudioRecorderPermissionLauncher;
    private ActivityResultLauncher<Intent> toAudioRecorderShowLauncher;
    private ActivityResultLauncher<Intent> toCtaBeforeAITask;
    private ActivityResultLauncher<Intent> toCtaBeforeAITextWidgetTask;
    private ActivityResultLauncher<Intent> toGlobalPermissionLauncher;

    /* JADX INFO: renamed from: TAG$1, reason: from kotlin metadata */
    private final String TAG = "BaseNoteDetailFragment";
    protected boolean mEnableEdit = true;
    protected boolean mNeedVisible = true;
    private EditorActionMode mOldActionMode = EditorActionMode.VIEW;
    private int mDismissType = -1;
    private final AtomicBoolean isSetExcerptObserver = new AtomicBoolean(false);
    private Long mShowTime = 0L;
    private final BroadcastReceiver mAiReceiver = new BroadcastReceiver() { // from class: com.miui.notes.basefeature.noteedit.BaseHybridNoteEditFragment$mAiReceiver$1
        @Override // android.content.BroadcastReceiver
        public void onReceive(Context context, Intent intent) {
            Intrinsics.checkNotNullParameter(intent, "intent");
            if (Intrinsics.areEqual(intent.getAction(), InteralTestInviteActivity.ACTION_INTERNAL_TEST_INVITE) && !PreferenceUtils.isInternalTestInviteAccepted(context)) {
                if (intent.getBooleanExtra(InteralTestInviteActivity.ACTION_INTERNAL_TEST_INVITE_KEY, false)) {
                    PreferenceUtils.setInternalTestInviteAccepted(this.this$0.getContext(), true);
                    if (!AccountMgrCallback.INSTANCE.checkoutIsLoginXiaomiAccount()) {
                        Logger.INSTANCE.d("BaseHybridNoteEditFragment", "should login first");
                        BaseHybridNoteEditFragment baseHybridNoteEditFragment = this.this$0;
                        baseHybridNoteEditFragment.showXiaoaiAcceptDialog(baseHybridNoteEditFragment.getActivity(), new BaseHybridNoteEditFragment.NotesAccountAcceptListener(this.this$0));
                        return;
                    }
                    BaseHybridNoteEditFragment.Companion companion = BaseHybridNoteEditFragment.INSTANCE;
                    FragmentActivity fragmentActivityRequireActivity = this.this$0.requireActivity();
                    Intrinsics.checkNotNullExpressionValue(fragmentActivityRequireActivity, "requireActivity(...)");
                    if (companion.isNeedDownloadOrUpdateLocalAi(fragmentActivityRequireActivity)) {
                        BaseHybridNoteEditFragment baseHybridNoteEditFragment2 = this.this$0;
                        BaseHybridNoteEditFragment.Companion companion2 = BaseHybridNoteEditFragment.INSTANCE;
                        FragmentActivity fragmentActivityRequireActivity2 = this.this$0.requireActivity();
                        Intrinsics.checkNotNullExpressionValue(fragmentActivityRequireActivity2, "requireActivity(...)");
                        baseHybridNoteEditFragment2.mLocalDownloadDialog = companion2.showLocalDownloadDialog(fragmentActivityRequireActivity2);
                        return;
                    }
                    if (PermissionNewUtils.hasOverlayPermission(this.this$0.getActivity())) {
                        return;
                    }
                    BaseHybridNoteEditFragment baseHybridNoteEditFragment3 = this.this$0;
                    FragmentActivity fragmentActivityRequireActivity3 = baseHybridNoteEditFragment3.requireActivity();
                    Intrinsics.checkNotNullExpressionValue(fragmentActivityRequireActivity3, "requireActivity(...)");
                    baseHybridNoteEditFragment3.showOverlayPermissionDialog(fragmentActivityRequireActivity3);
                    return;
                }
                return;
            }
            if (this.this$0.mIsStop) {
                return;
            }
            Logger.INSTANCE.i("BaseHybridNoteEditFragment", "startAiTextWidget from editor, support:" + RomUtils.isSupportAiText(this.this$0.getContext()) + " current taskId:" + this.this$0.requireActivity().getTaskId() + " ,broadCast taskId:" + NoteAiTextWidgetService.mTaskId);
            if (this.this$0.requireActivity().getTaskId() == NoteAiTextWidgetService.mTaskId) {
                this.this$0.isFromNoteToolBarEnter = false;
                this.this$0.startAiTextWidget();
            }
        }
    };
    private final String EXTRA_TAP_SHARE_SUPPORT = PadMultiHandWriteFragment.EXTRA_TAP_SHARE_SUPPORT;
    private final String EXTRA_TAP_SHARE_DEEP_LINK = "xiaomi.share.LINK";
    private final String EXTRA_TAP_SHARE_DEEP_ACTION = "xiaomi.share.action";
    private final String EXTRA_TAP_SHARE_THUMBNAIL_URI = "xiaomi.tap.share.THUMBNAIL_URI";
    private final String EXTRA_CONTENT_PREVIEW_NOTES = "xiaomi.content.preview.MIUI_NOTES";
    private final String EXTRA_TAP_SHARE_TITLE = "xiaomi.tap.share.TITLE";
    private final String EXTRA_TAP_SHARE_SUBTITLE = "xiaomi.tap.share.SUBTITLE";

    /* JADX INFO: renamed from: mEditorActionListener$delegate, reason: from kotlin metadata */
    private final Lazy mEditorActionListener = LazyKt.lazy(new Function0() { // from class: com.miui.notes.basefeature.noteedit.BaseHybridNoteEditFragment$$ExternalSyntheticLambda10
        @Override // kotlin.jvm.functions.Function0
        public final Object invoke() {
            return BaseHybridNoteEditFragment.mEditorActionListener_delegate$lambda$0(this.f$0);
        }
    });
    private String mAITextUseValue = "";
    private Runnable mEditEnableRunnable = new Runnable() { // from class: com.miui.notes.basefeature.noteedit.BaseHybridNoteEditFragment$$ExternalSyntheticLambda12
        @Override // java.lang.Runnable
        public final void run() {
            BaseHybridNoteEditFragment.mEditEnableRunnable$lambda$1(this.f$0);
        }
    };
    private Runnable mShowCreatingProgressDialogTask = new Runnable() { // from class: com.miui.notes.basefeature.noteedit.BaseHybridNoteEditFragment$$ExternalSyntheticLambda13
        @Override // java.lang.Runnable
        public final void run() {
            BaseHybridNoteEditFragment.mShowCreatingProgressDialogTask$lambda$42(this.f$0);
        }
    };
    private final AIRecorderServiceCallback mAiRecorderServiceCallback = new NoteAIRecorderServiceCallback(this);
    private final AIRecorderServiceStateCallback mAiRecorderServiceStateCallback = new AIRecorderServiceStateCallback() { // from class: com.miui.notes.basefeature.noteedit.BaseHybridNoteEditFragment$mAiRecorderServiceStateCallback$1
        @Override // com.xiaomi.xms.ai.recorder.callback.AIRecorderServiceStateCallback
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            Logger.INSTANCE.d("BaseHybridNoteEditFragment", "onServiceConnected componentName: " + componentName + " iBinder: " + iBinder);
        }

        @Override // com.xiaomi.xms.ai.recorder.callback.AIRecorderServiceStateCallback
        public void onServiceDisconnected(ComponentName componentName) {
            Logger.INSTANCE.d("BaseHybridNoteEditFragment", "onServiceDisconnected componentName: " + componentName + " isRecording = " + this.this$0.mIsRecording);
            this.this$0.finishAiRecord();
        }
    };

    @JvmStatic
    public static final void hideAiFloatingWindowForLock() {
        INSTANCE.hideAiFloatingWindowForLock();
    }

    @JvmStatic
    public static final void removeAiFloatingWindow() {
        INSTANCE.removeAiFloatingWindow();
    }

    @Override // com.miui.notes.ai.editor.IAiEditor
    public void clearDoc() {
    }

    protected abstract HybridEditorScriptInterface createJsBridge();

    @Override // com.miui.notes.ai.editor.IAiEditor
    public void doCopy() {
    }

    @Override // com.miui.notes.basefeature.noteedit.BaseNormalNoteEditFragment
    protected int getAutoSaveCommonDuration() {
        return 5000;
    }

    @Override // com.miui.notes.basefeature.noteedit.BaseNormalNoteEditFragment
    protected int getThemeResId() {
        return R.style.NavigatorSecondaryContentTheme;
    }

    public abstract boolean isNoteCreated();

    @Override // com.miui.notes.ui.BaseActionBarController.EditCallback
    public boolean isOnSaveInstanceCalled() {
        return false;
    }

    public final void onAnnotateImageInDocEnd() {
    }

    public final void onAnnotateImageInDocStart() {
    }

    public final void onPopMenuHide() {
    }

    @Override // com.miui.notes.detail.BaseNoteDetailFragment
    protected void refreshNote(NoteLoadDataEntity noteEntity, boolean isFromPc) {
    }

    @Override // com.miui.notes.basefeature.noteedit.BaseNormalNoteEditFragment
    protected void resetEditContent() {
    }

    public BaseHybridNoteEditFragment() {
        this.mActionMode = EditorActionMode.VIEW;
    }

    @Override // com.miui.notes.detail.BaseNoteDetailFragment
    public String getTAG() {
        return this.TAG;
    }

    /* JADX INFO: renamed from: getWebRichEditor, reason: from getter */
    public final WebRichEditor getMEditor() {
        return this.mEditor;
    }

    protected final String getMAiDoneData() {
        return this.mAiDoneData;
    }

    protected final void setMAiDoneData(String str) {
        this.mAiDoneData = str;
    }

    protected final String getMAiDoneData2() {
        return this.mAiDoneData2;
    }

    protected final void setMAiDoneData2(String str) {
        this.mAiDoneData2 = str;
    }

    protected final long getMOriginalId() {
        return this.mOriginalId;
    }

    protected final void setMOriginalId(long j) {
        this.mOriginalId = j;
    }

    protected final String getMOriginalContent() {
        return this.mOriginalContent;
    }

    protected final void setMOriginalContent(String str) {
        this.mOriginalContent = str;
    }

    protected final String getMOriginalTitle() {
        return this.mOriginalTitle;
    }

    protected final void setMOriginalTitle(String str) {
        this.mOriginalTitle = str;
    }

    protected final long getMEnterTimeForEditMode() {
        return this.mEnterTimeForEditMode;
    }

    protected final void setMEnterTimeForEditMode(long j) {
        this.mEnterTimeForEditMode = j;
    }

    public final String getMNewImageName() {
        return this.mNewImageName;
    }

    public final void setMNewImageName(String str) {
        this.mNewImageName = str;
    }

    protected final boolean getMNotSaveData() {
        return this.mNotSaveData;
    }

    protected final void setMNotSaveData(boolean z) {
        this.mNotSaveData = z;
    }

    protected final String getMAiData() {
        return this.mAiData;
    }

    protected final void setMAiData(String str) {
        this.mAiData = str;
    }

    public final String getAigcData() {
        return this.aigcData;
    }

    public final void setAigcData(String str) {
        this.aigcData = str;
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX INFO: compiled from: BaseHybridNoteEditFragment.kt */
    @Metadata(d1 = {"\u0000\"\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u0002\n\u0002\b\u0002\b\u0002\u0018\u00002\u00020\u0001B\u0011\u0012\b\u0010\u0002\u001a\u0004\u0018\u00010\u0003¢\u0006\u0004\b\u0004\u0010\u0005J\b\u0010\f\u001a\u00020\rH\u0016J\b\u0010\u000e\u001a\u00020\rH\u0016R$\u0010\u0006\u001a\f\u0012\u0006\u0012\u0004\u0018\u00010\u0003\u0018\u00010\u0007X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\b\u0010\t\"\u0004\b\n\u0010\u000b¨\u0006\u000f"}, d2 = {"Lcom/miui/notes/basefeature/noteedit/BaseHybridNoteEditFragment$NotesAccountAcceptListener;", "Lcom/miui/notes/tool/PermissionNewUtils$AcceptListener;", "fragment", "Lcom/miui/notes/basefeature/noteedit/BaseHybridNoteEditFragment;", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "(Lcom/miui/notes/basefeature/noteedit/BaseHybridNoteEditFragment;)V", "weakReference", "Ljava/lang/ref/WeakReference;", "getWeakReference", "()Ljava/lang/ref/WeakReference;", "setWeakReference", "(Ljava/lang/ref/WeakReference;)V", "accept", "", "reject", "app_PhoneGlobalRelease"}, k = 1, mv = {2, 2, 0}, xi = 48)
    static final class NotesAccountAcceptListener implements PermissionNewUtils.AcceptListener {
        private WeakReference<BaseHybridNoteEditFragment> weakReference;

        @Override // com.miui.notes.tool.PermissionNewUtils.AcceptListener
        public void reject() {
        }

        public NotesAccountAcceptListener(BaseHybridNoteEditFragment baseHybridNoteEditFragment) {
            this.weakReference = new WeakReference<>(baseHybridNoteEditFragment);
        }

        public final WeakReference<BaseHybridNoteEditFragment> getWeakReference() {
            return this.weakReference;
        }

        public final void setWeakReference(WeakReference<BaseHybridNoteEditFragment> weakReference) {
            this.weakReference = weakReference;
        }

        @Override // com.miui.notes.tool.PermissionNewUtils.AcceptListener
        public void accept() {
            BaseHybridNoteEditFragment baseHybridNoteEditFragment;
            WeakReference<BaseHybridNoteEditFragment> weakReference = this.weakReference;
            if (weakReference == null) {
                baseHybridNoteEditFragment = null;
            } else {
                Intrinsics.checkNotNull(weakReference);
                baseHybridNoteEditFragment = weakReference.get();
            }
            if (baseHybridNoteEditFragment == null) {
                return;
            }
            baseHybridNoteEditFragment.onPermissionAccept();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void onPermissionAccept() {
        Companion companion = INSTANCE;
        FragmentActivity fragmentActivityRequireActivity = requireActivity();
        Intrinsics.checkNotNullExpressionValue(fragmentActivityRequireActivity, "requireActivity(...)");
        if (companion.isNeedDownloadOrUpdateLocalAi(fragmentActivityRequireActivity)) {
            FragmentActivity fragmentActivityRequireActivity2 = requireActivity();
            Intrinsics.checkNotNullExpressionValue(fragmentActivityRequireActivity2, "requireActivity(...)");
            this.mLocalDownloadDialog = companion.showLocalDownloadDialog(fragmentActivityRequireActivity2);
        } else {
            if (PermissionNewUtils.hasOverlayPermission(getActivity())) {
                this.isFromNoteToolBarEnter = false;
                return;
            }
            FragmentActivity fragmentActivityRequireActivity3 = requireActivity();
            Intrinsics.checkNotNullExpressionValue(fragmentActivityRequireActivity3, "requireActivity(...)");
            showOverlayPermissionDialog(fragmentActivityRequireActivity3);
        }
    }

    /* JADX INFO: renamed from: isStartAiTextWindow, reason: from getter */
    protected final boolean getIsStartAiTextWindow() {
        return this.isStartAiTextWindow;
    }

    protected final void setStartAiTextWindow(boolean z) {
        this.isStartAiTextWindow = z;
    }

    protected final String getMLastAudioFileName() {
        return this.mLastAudioFileName;
    }

    protected final void setMLastAudioFileName(String str) {
        this.mLastAudioFileName = str;
    }

    public final String getEXTRA_TAP_SHARE_SUPPORT() {
        return this.EXTRA_TAP_SHARE_SUPPORT;
    }

    public final String getEXTRA_TAP_SHARE_DEEP_LINK() {
        return this.EXTRA_TAP_SHARE_DEEP_LINK;
    }

    public final String getEXTRA_TAP_SHARE_DEEP_ACTION() {
        return this.EXTRA_TAP_SHARE_DEEP_ACTION;
    }

    public final String getEXTRA_TAP_SHARE_THUMBNAIL_URI() {
        return this.EXTRA_TAP_SHARE_THUMBNAIL_URI;
    }

    public final String getEXTRA_CONTENT_PREVIEW_NOTES() {
        return this.EXTRA_CONTENT_PREVIEW_NOTES;
    }

    public final String getEXTRA_TAP_SHARE_TITLE() {
        return this.EXTRA_TAP_SHARE_TITLE;
    }

    public final String getEXTRA_TAP_SHARE_SUBTITLE() {
        return this.EXTRA_TAP_SHARE_SUBTITLE;
    }

    /* JADX INFO: compiled from: BaseHybridNoteEditFragment.kt */
    @Metadata(d1 = {"\u0000(\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0002\b\b\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0000\b\u0002\u0018\u00002\u00020\u0001B#\u0012\b\u0010\u0002\u001a\u0004\u0018\u00010\u0003\u0012\u0010\b\u0002\u0010\u0004\u001a\n\u0012\u0004\u0012\u00020\u0006\u0018\u00010\u0005¢\u0006\u0004\b\u0007\u0010\bJ\u001a\u0010\r\u001a\u00020\u00062\u0006\u0010\u000e\u001a\u00020\u000f2\b\u0010\u0010\u001a\u0004\u0018\u00010\u0011H\u0016R\"\u0010\u0004\u001a\n\u0012\u0004\u0012\u00020\u0006\u0018\u00010\u0005X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\t\u0010\n\"\u0004\b\u000b\u0010\f¨\u0006\u0012"}, d2 = {"Lcom/miui/notes/basefeature/noteedit/BaseHybridNoteEditFragment$SummaryResultObserver;", "Landroid/database/ContentObserver;", "handler", "Landroid/os/Handler;", "onChangeCallback", "Lkotlin/Function0;", "", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "(Landroid/os/Handler;Lkotlin/jvm/functions/Function0;)V", "getOnChangeCallback", "()Lkotlin/jvm/functions/Function0;", "setOnChangeCallback", "(Lkotlin/jvm/functions/Function0;)V", "onChange", "selfChange", "", CallMethod.ARG_URI, "Landroid/net/Uri;", "app_PhoneGlobalRelease"}, k = 1, mv = {2, 2, 0}, xi = 48)
    private static final class SummaryResultObserver extends ContentObserver {
        private Function0<Unit> onChangeCallback;

        public SummaryResultObserver(Handler handler, Function0<Unit> function0) {
            super(handler);
            this.onChangeCallback = function0;
        }

        public /* synthetic */ SummaryResultObserver(Handler handler, Function0 function0, int i, DefaultConstructorMarker defaultConstructorMarker) {
            this(handler, (i & 2) != 0 ? null : function0);
        }

        public final Function0<Unit> getOnChangeCallback() {
            return this.onChangeCallback;
        }

        public final void setOnChangeCallback(Function0<Unit> function0) {
            this.onChangeCallback = function0;
        }

        @Override // android.database.ContentObserver
        public void onChange(boolean selfChange, Uri uri) {
            super.onChange(selfChange, uri);
            Logger.INSTANCE.d(BaseHybridNoteEditFragment.TAG, "SummaryResultObserver onChange");
            Function0<Unit> function0 = this.onChangeCallback;
            if (function0 != null) {
                function0.invoke();
            }
        }
    }

    private final EditorActionListener getMEditorActionListener() {
        return (EditorActionListener) this.mEditorActionListener.getValue();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final EditorActionListenerImpl mEditorActionListener_delegate$lambda$0(BaseHybridNoteEditFragment baseHybridNoteEditFragment) {
        return new EditorActionListenerImpl(new WeakReference(baseHybridNoteEditFragment));
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX INFO: compiled from: BaseHybridNoteEditFragment.kt */
    @Metadata(d1 = {"\u0000\"\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\b\n\u0000\b\u0002\u0018\u00002\u00020\u0001B\u0015\u0012\f\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003¢\u0006\u0004\b\u0005\u0010\u0006J\u0010\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\fH\u0016R\u0017\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\b¨\u0006\r"}, d2 = {"Lcom/miui/notes/basefeature/noteedit/BaseHybridNoteEditFragment$EditorActionListenerImpl;", "Lcom/miui/hybirdeditor/EditorActionListener;", "fragRef", "Ljava/lang/ref/WeakReference;", "Lcom/miui/notes/basefeature/noteedit/BaseHybridNoteEditFragment;", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "(Ljava/lang/ref/WeakReference;)V", "getFragRef", "()Ljava/lang/ref/WeakReference;", "onAction", "", "action", "", "app_PhoneGlobalRelease"}, k = 1, mv = {2, 2, 0}, xi = 48)
    static final class EditorActionListenerImpl implements EditorActionListener {
        private final WeakReference<BaseHybridNoteEditFragment> fragRef;

        public EditorActionListenerImpl(WeakReference<BaseHybridNoteEditFragment> fragRef) {
            Intrinsics.checkNotNullParameter(fragRef, "fragRef");
            this.fragRef = fragRef;
        }

        public final WeakReference<BaseHybridNoteEditFragment> getFragRef() {
            return this.fragRef;
        }

        @Override // com.miui.hybirdeditor.EditorActionListener
        public void onAction(int action) {
            BaseHybridNoteEditFragment baseHybridNoteEditFragment = this.fragRef.get();
            if (baseHybridNoteEditFragment != null) {
                if (action != 1) {
                    if (action != 2) {
                        return;
                    }
                    baseHybridNoteEditFragment.asyncSaveNote();
                } else {
                    WorkingNote workingNote = baseHybridNoteEditFragment.mWorkingNote;
                    if (workingNote != null) {
                        WebRichEditor webRichEditor = baseHybridNoteEditFragment.mEditor;
                        workingNote.setWorkingTitle(webRichEditor != null ? webRichEditor.getTitle() : null);
                    }
                }
            }
        }
    }

    protected final boolean getMCTAFirstAuthorization() {
        return this.mCTAFirstAuthorization;
    }

    protected final void setMCTAFirstAuthorization(boolean z) {
        this.mCTAFirstAuthorization = z;
    }

    public final Runnable getMEditEnableRunnable() {
        return this.mEditEnableRunnable;
    }

    public final void setMEditEnableRunnable(Runnable runnable) {
        Intrinsics.checkNotNullParameter(runnable, "<set-?>");
        this.mEditEnableRunnable = runnable;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void mEditEnableRunnable$lambda$1(BaseHybridNoteEditFragment baseHybridNoteEditFragment) {
        WebRichEditor webRichEditor = baseHybridNoteEditFragment.mEditor;
        if (webRichEditor != null) {
            Intrinsics.checkNotNull(webRichEditor);
            webRichEditor.setEnableEdit(baseHybridNoteEditFragment.mEnableEdit);
            WebRichEditor webRichEditor2 = baseHybridNoteEditFragment.mEditor;
            Intrinsics.checkNotNull(webRichEditor2);
            webRichEditor2.setOnDragListener(baseHybridNoteEditFragment.mEnableEdit ? baseHybridNoteEditFragment.mDragListener : null);
        }
    }

    @Override // com.miui.notes.detail.BaseNoteDetailFragment
    public void onEnterActionMode() {
        super.onEnterActionMode();
        doneEdit();
        INSTANCE.removeAiFloatingWindow();
    }

    @Override // com.miui.notes.basefeature.noteedit.BaseNormalNoteEditFragment, com.miui.notes.detail.BaseNoteDetailFragment, miuix.appcompat.app.Fragment, androidx.fragment.app.Fragment
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        PreferenceUtils.setFirstEnterNote(getContext());
        Logger.INSTANCE.d(TAG, "onCreate with bundle: " + (savedInstanceState != null));
        this.mNotSaveData = false;
        this.mIsCreated = true;
    }

    /* JADX WARN: Code duplicated, block: B:29:0x00e2  */
    @Override // com.miui.notes.basefeature.noteedit.BaseNormalNoteEditFragment, com.miui.notes.detail.BaseNoteDetailFragment, miuix.appcompat.app.Fragment, miuix.appcompat.app.IFragment
    public void onViewInflated(View view, Bundle savedInstanceState) {
        int intExtra;
        boolean z;
        Intrinsics.checkNotNullParameter(view, "view");
        super.onViewInflated(view, savedInstanceState);
        Logger.INSTANCE.d(TAG, "onViewInflated,with bundle:" + (savedInstanceState != null));
        this.mStartTime = System.currentTimeMillis();
        this.mWindowInitFlags = requireActivity().getWindow().getAttributes().flags;
        this.mJsBridge = createJsBridge();
        frameBoost(view);
        if (savedInstanceState == null) {
            Intent intent = requireActivity().getIntent();
            if (intent != null) {
                this.mPendingHandleIntent = intent;
                intExtra = intent.getIntExtra(NoteIntent.KEY_NOTE_THEME_ID, 0);
            } else {
                intExtra = 0;
            }
        } else {
            this.mDoodleFileId = savedInstanceState.getString("mDoodleFileId");
            Logger.INSTANCE.d(TAG, "get mDoodleFileId from savedInstanceState " + this.mDoodleFileId);
            intExtra = savedInstanceState.getInt("last_theme_id", 0);
            Logger.INSTANCE.d(TAG, "get themeId from savedInstanceState " + intExtra);
        }
        if (RomUtils.isPadMode() || LiteUtils.isNewLiteOrMiddle()) {
            intExtra = 0;
        }
        Intent intent2 = this.mPendingHandleIntent;
        if (intent2 != null) {
            Intrinsics.checkNotNull(intent2);
            if (Intrinsics.areEqual("android.intent.action.INSERT_OR_EDIT", intent2.getAction())) {
                Intent intent3 = this.mPendingHandleIntent;
                Intrinsics.checkNotNull(intent3);
                long longExtra = intent3.getLongExtra(Notes.Intents.INTENT_EXTRA_CALL_DATE, 0L);
                if (longExtra == 0 || longExtra != com.miui.notes.tool.PreferenceUtils.getLastCallNoteData()) {
                    Intent intent4 = this.mPendingHandleIntent;
                    Intrinsics.checkNotNull(intent4);
                    if (Intrinsics.areEqual(NoteIntent.TYPE_ACTION_INSERT_OR_EDIT_AUDIO_NOTE, intent4.getType())) {
                        z = false;
                    } else {
                        z = true;
                    }
                } else {
                    z = false;
                }
                this.mIsEditNotes = z;
            }
        }
        if (this.mIsEditNotes) {
            HybridEditorScriptInterface hybridEditorScriptInterface = this.mJsBridge;
            Intrinsics.checkNotNull(hybridEditorScriptInterface);
            hybridEditorScriptInterface.setExtraInfo(this.mIsFromPrivate);
        }
        Logger.INSTANCE.d(TAG, "onViewInflated,init editor and update theme");
        initNoteEditor(view, intExtra);
        updateNoteThemeOnNative(intExtra, false, true);
        view.post(new Runnable() { // from class: com.miui.notes.basefeature.noteedit.BaseHybridNoteEditFragment$$ExternalSyntheticLambda30
            @Override // java.lang.Runnable
            public final void run() {
                BaseHybridNoteEditFragment.onViewInflated$lambda$2(this.f$0);
            }
        });
        if (this.mHandler != null) {
            new Thread(new Runnable() { // from class: com.miui.notes.basefeature.noteedit.BaseHybridNoteEditFragment$$ExternalSyntheticLambda31
                @Override // java.lang.Runnable
                public final void run() {
                    BaseHybridNoteEditFragment.onViewInflated$lambda$4(this.f$0);
                }
            }).start();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void onViewInflated$lambda$2(BaseHybridNoteEditFragment baseHybridNoteEditFragment) {
        baseHybridNoteEditFragment.registerReceiver();
        INSTANCE.removeAiFloatingWindow();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void onViewInflated$lambda$4(final BaseHybridNoteEditFragment baseHybridNoteEditFragment) {
        try {
            if (!baseHybridNoteEditFragment.isSupportAIRecorder() || !Utils.isRecordIsInstall() || Utils.isNeedUpdateRecord() || baseHybridNoteEditFragment.mHandler == null) {
                return;
            }
            baseHybridNoteEditFragment.mHandler.post(new Runnable() { // from class: com.miui.notes.basefeature.noteedit.BaseHybridNoteEditFragment$$ExternalSyntheticLambda58
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.initRecordSummaryResultObserver();
                }
            });
        } catch (Exception e) {
            Logger.INSTANCE.e(TAG, "check AIRecorder support failed: " + e);
        }
    }

    private final void frameBoost(View view) {
        Logger.INSTANCE.d(TAG, "frame boost");
        final ProgressBar progressBar = (ProgressBar) view.findViewById(R.id.frame_progress_bar);
        if (progressBar == null || this.mHandler == null) {
            return;
        }
        progressBar.setVisibility(0);
        this.mHandler.post(new Runnable() { // from class: com.miui.notes.basefeature.noteedit.BaseHybridNoteEditFragment$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                BaseHybridNoteEditFragment.frameBoost$lambda$5(progressBar);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void frameBoost$lambda$5(ProgressBar progressBar) {
        progressBar.setVisibility(8);
        Logger.INSTANCE.d(TAG, "frame boost progress hide");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void initRecordSummaryResultObserver() {
        if (this.mIsRecordRecordSummaryRegistered) {
            Logger.INSTANCE.d(TAG, "SummaryResultObserver already registered, skip");
            return;
        }
        if (this.mSummaryResultObserver == null) {
            final WeakReference weakReference = new WeakReference(this);
            this.sumaryResultChangeCallback = new Function0() { // from class: com.miui.notes.basefeature.noteedit.BaseHybridNoteEditFragment$$ExternalSyntheticLambda42
                @Override // kotlin.jvm.functions.Function0
                public final Object invoke() {
                    return BaseHybridNoteEditFragment.initRecordSummaryResultObserver$lambda$6(weakReference);
                }
            };
            Handler handler = this.mHandler;
            Function0<Unit> function0 = this.sumaryResultChangeCallback;
            Intrinsics.checkNotNull(function0);
            this.mSummaryResultObserver = new SummaryResultObserver(handler, function0);
        }
        try {
            ContentResolver contentResolver = requireContext().getContentResolver();
            Uri uri = NotesNormalConstants.SUMMARY_CONTENT_URI;
            SummaryResultObserver summaryResultObserver = this.mSummaryResultObserver;
            Intrinsics.checkNotNull(summaryResultObserver);
            contentResolver.registerContentObserver(uri, true, summaryResultObserver);
            Uri uri2 = NotesNormalConstants.RECOGNIZE_CONTENT_URI;
            SummaryResultObserver summaryResultObserver2 = this.mSummaryResultObserver;
            Intrinsics.checkNotNull(summaryResultObserver2);
            contentResolver.registerContentObserver(uri2, true, summaryResultObserver2);
            this.mRegisteredContentResolver = contentResolver;
            this.mIsRecordRecordSummaryRegistered = true;
        } catch (Exception e) {
            Logger.INSTANCE.d(TAG, "registerContentObserver fail:" + e);
            this.mIsRecordRecordSummaryRegistered = false;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit initRecordSummaryResultObserver$lambda$6(WeakReference weakReference) {
        BaseHybridNoteEditFragment baseHybridNoteEditFragment = (BaseHybridNoteEditFragment) weakReference.get();
        if (baseHybridNoteEditFragment != null && !TextUtils.isEmpty(baseHybridNoteEditFragment.mLastAudioFileName)) {
            Logger.INSTANCE.d(TAG, "SummaryResultObserver onChange mLastAudioFileName :" + baseHybridNoteEditFragment.mLastAudioFileName);
            String str = baseHybridNoteEditFragment.mLastAudioFileName;
            Intrinsics.checkNotNull(str);
            baseHybridNoteEditFragment.getAudioDescription(str);
        }
        return Unit.INSTANCE;
    }

    public final void trackReportClipBoard(WorkingNote workingNote, String elementName) {
        long createdDate;
        int i;
        int themeId;
        String str;
        String str2;
        if (workingNote == null) {
            createdDate = 0;
            i = 0;
            themeId = 0;
            str = "";
            str2 = str;
        } else {
            createdDate = workingNote.getCreatedDate();
            int plainTextCount = NoteSchemaUtil.getPlainTextCount(NoteSchema.convertToWeb(workingNote.getContent()));
            String contentType = NoteSchemaUtil.getContentType(NoteSchema.convertToWeb(workingNote.getContent()));
            String noteContentStatus = getNoteContentStatus();
            themeId = workingNote.getThemeId();
            i = plainTextCount;
            str2 = noteContentStatus;
            str = contentType;
        }
        long j = createdDate;
        FragmentActivity activity = getActivity();
        NotesTextStat.reportClipBoardEvent(str, str2, i, j, themeId, elementName, activity != null ? activity.getPackageName() : null);
    }

    public final void trackReportOpenContent(Intent intent, WorkingNote workingNote) {
        String str;
        long j;
        int themeId;
        String str2;
        String str3;
        String str4;
        int i;
        if (intent == null) {
            return;
        }
        Bundle extras = intent.getExtras();
        List<FolderItemEntity> list = GlobalStat.Status.folderList;
        String source = getSource(extras);
        long j2 = extras != null ? extras.getLong("com.miui.notes.folder_id") : 0L;
        if (j2 == -4097) {
            str = NoteContentStat.NOTES_ALL_NAME;
        } else {
            str = NoteContentStat.NOTES_TYPE_NAME;
        }
        String folderName = "全部";
        if (list != null && !list.isEmpty()) {
            for (FolderItemEntity folderItemEntity : list) {
                if (folderItemEntity.getFolderId() == j2) {
                    folderName = folderItemEntity.getFolderName();
                }
            }
        }
        if (workingNote == null) {
            j = 0;
            themeId = 0;
            str2 = "否";
            str3 = "";
            str4 = str3;
            i = 0;
        } else {
            long createdDate = workingNote.getCreatedDate();
            str2 = workingNote.getAlertDate() > 0 ? "是" : "否";
            int plainTextCount = NoteSchemaUtil.getPlainTextCount(NoteSchema.convertToWeb(workingNote.getContent()));
            String contentType = NoteSchemaUtil.getContentType(NoteSchema.convertToWeb(workingNote.getContent()));
            String noteContentStatus = getNoteContentStatus();
            themeId = workingNote.getThemeId();
            i = plainTextCount;
            str3 = contentType;
            str4 = noteContentStatus;
            j = createdDate;
        }
        String str5 = this.mIsFromPrivate ? "是" : "否";
        String str6 = NoteContentStat.IS_STICK ? "是" : "否";
        NoteContentStat.IS_NOTE_BTN_BACK = false;
        NotesTextStat.reportOpenEditEvent(source, str, folderName, str2, str6, str5, str3, str4, i, j, themeId);
    }

    public final void trackReportQuitContent(Intent intent, WorkingNote workingNote) {
        String str;
        String str2;
        int themeId;
        String noteContentStatus;
        long createdDate;
        int plainTextCount;
        String contentType;
        if (intent == null) {
            return;
        }
        Bundle extras = intent.getExtras();
        List<FolderItemEntity> list = GlobalStat.Status.folderList;
        String source = getSource(extras);
        long j = extras != null ? extras.getLong("com.miui.notes.folder_id") : 0L;
        if (j == -4097) {
            str = NoteContentStat.NOTES_ALL_NAME;
        } else {
            str = NoteContentStat.NOTES_TYPE_NAME;
        }
        String folderName = "全部";
        if (list != null && !list.isEmpty()) {
            for (FolderItemEntity folderItemEntity : GlobalStat.Status.folderList) {
                if (folderItemEntity.getFolderId() == j) {
                    folderName = folderItemEntity.getFolderName();
                }
            }
        }
        if (workingNote == null) {
            str2 = "否";
            themeId = 0;
            noteContentStatus = "";
            createdDate = 0;
            plainTextCount = 0;
            contentType = noteContentStatus;
        } else {
            createdDate = workingNote.getCreatedDate();
            String str3 = workingNote.getAlertDate() > 0 ? "是" : "否";
            plainTextCount = NoteSchemaUtil.getPlainTextCount(NoteSchema.convertToWeb(workingNote.getContent()));
            contentType = NoteSchemaUtil.getContentType(NoteSchema.convertToWeb(workingNote.getContent()));
            noteContentStatus = getNoteContentStatus();
            themeId = workingNote.getThemeId();
            str2 = str3;
        }
        String str4 = this.mIsFromPrivate ? "是" : "否";
        String str5 = NoteContentStat.IS_STICK ? "是" : "否";
        String str6 = str;
        long jCurrentTimeMillis = System.currentTimeMillis() - this.mStartTime;
        if (NoteContentStat.IS_NOTE_BTN_BACK) {
            NoteContentStat.QUIT_WAY = NoteContentStat.QUIT_WAY_BTN_BACK;
        }
        NotesTextStat.reportQuitEditEvent(source, str6, folderName, contentType, noteContentStatus, plainTextCount, createdDate, String.valueOf(themeId), jCurrentTimeMillis, NoteContentStat.QUIT_WAY, str2, str5, str4);
    }

    private final String getSource(Bundle extras) {
        if (extras != null) {
            if (com.miui.notes.ai.utils.RomUtils.isPhoneDevice()) {
                if (extras.containsKey("android.intent.extra.REFERRER") && extras.containsKey("intent_extra_data_key")) {
                    return NoteContentStat.TYPE_GLOBAL_SEARCH;
                }
                if (extras.containsKey("intent_extra_data_key")) {
                    return NoteContentStat.TYPE_NOTES_SEARCH;
                }
                if (extras.containsKey("com.miui.notes.widget_type")) {
                    return NoteContentStat.TYPE_CARD_WIDGET;
                }
                if (extras.containsKey(NoteIntent.KEY_IS_FROM_FLOAT_WINDOW)) {
                    return extras.getBoolean(NoteIntent.KEY_IS_FROM_FLOAT_WINDOW) ? "全局速记" : "";
                }
                if (extras.containsKey("android.intent.extra.PHONE_NUMBER")) {
                    return "通话笔记";
                }
                return "笔记首页";
            }
            if (this.mIsFromFloatWindow) {
                return "全局速记";
            }
            return (!extras.containsKey("com.miui.notes.widget_type") || extras.getInt("com.miui.notes.widget_type") == -1) ? "unknown" : NoteContentStat.TYPE_CARD_WIDGET;
        }
        return "";
    }

    private final void registerReceiver() {
        Logger.INSTANCE.d(TAG, "registerReceiver-aiReceiver");
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(InteralTestInviteActivity.ACTION_INTERNAL_TEST_INVITE);
        intentFilter.addAction(NoteAiTextWidgetService.ACTION_START_AI_FLOATING_WINDOW);
        intentFilter.setPriority(1000);
        if (getContext() != null) {
            if (Utils.isAboveSDK34()) {
                requireContext().registerReceiver(this.mAiReceiver, intentFilter, 2);
            } else {
                requireContext().registerReceiver(this.mAiReceiver, intentFilter);
            }
        }
    }

    @Override // com.miui.notes.detail.BaseNoteDetailFragment, androidx.fragment.app.Fragment
    public void onStart() {
        Logger.INSTANCE.d(TAG, "onStart: ");
        super.onStart();
        this.mIsStop = false;
    }

    @Override // com.miui.notes.basefeature.noteedit.BaseNormalNoteEditFragment, com.miui.notes.detail.BaseNoteDetailFragment, miuix.appcompat.app.Fragment, androidx.fragment.app.Fragment
    public void onStop() {
        super.onStop();
        Logger.INSTANCE.i(TAG, "onStop, isFlip: " + RomUtils.isFlipFoldDevice() + ",isTinyScreen: " + DeviceHelper.isTinyScreen(requireContext().getApplicationContext()));
        if (RomUtils.isFlipFoldDevice() && DeviceHelper.isTinyScreen(requireContext().getApplicationContext())) {
            Logger.INSTANCE.i(TAG, "onStop, flip tiny screen stop ai");
            INSTANCE.removeAiFloatingWindow();
            mNoteAiFloatingWindow = null;
        }
        WebRichEditor webRichEditor = this.mEditor;
        if (webRichEditor != null) {
            Intrinsics.checkNotNull(webRichEditor);
            webRichEditor.pauseVoice();
        }
        if (this.mActionMode != EditorActionMode.VIEW) {
            NoteContentStat.IS_BTN_BACK = false;
            trackReportEditMode(this.mPendingHandleIntent, true);
        } else {
            trackReportQuitContent(this.mPendingHandleIntent, this.mWorkingNote);
            NoteContentStat.cancel();
        }
        this.mIsStop = true;
        hidePopMenu();
    }

    @Override // com.miui.notes.basefeature.noteedit.BaseNormalNoteEditFragment, com.miui.notes.detail.BaseNoteDetailFragment, miuix.appcompat.app.Fragment, androidx.fragment.app.Fragment
    public void onResume() {
        Logger.INSTANCE.i(TAG, "onResume: ");
        super.onResume();
        if (this.mActionMode == EditorActionMode.EDIT || this.mActionMode == EditorActionMode.EDIT_TITLE) {
            trackReportEditMode(this.mPendingHandleIntent, false);
        }
        if (!this.mInAiMode) {
            this.mNotSaveData = false;
        }
        if (this.mPagePrepare) {
            Logger.INSTANCE.d(getTAG(), "onChangeFontStyle");
            onChangeFontStyle();
        }
        if (FloatWindowManagerAgent.isContinuousExcerpt() && isThisNoteDoExcerpt()) {
            FloatWindowManagerAgent.pauseContinuousExcerpt();
        }
    }

    @Override // com.miui.notes.basefeature.noteedit.BaseNormalNoteEditFragment, com.miui.notes.detail.BaseNoteDetailFragment, androidx.fragment.app.Fragment
    public void onPause() {
        super.onPause();
        Logger.INSTANCE.d(TAG, "onPause: ");
        if (FloatWindowManagerAgent.isContinuousExcerpt() && isThisNoteDoExcerpt()) {
            FloatWindowManagerAgent.resumeContinuousExcerpt();
        }
        Dialog dialog = this.mLocalDownloadDialog;
        if (dialog != null) {
            Intrinsics.checkNotNull(dialog);
            if (dialog.isShowing()) {
                Logger.INSTANCE.d(TAG, "mLocalDownloadDialog is showing: true");
                Dialog dialog2 = this.mLocalDownloadDialog;
                Intrinsics.checkNotNull(dialog2);
                dialog2.dismiss();
            }
        }
    }

    @Override // com.miui.notes.basefeature.noteedit.BaseNormalNoteEditFragment, com.miui.notes.detail.BaseNoteDetailFragment, miuix.appcompat.app.Fragment, androidx.fragment.app.Fragment
    public void onDestroy() {
        Logger.INSTANCE.i(TAG, "onDestroy");
        if (getActivity() != null && this.excerptContentObserver != null && this.isSetExcerptObserver.get()) {
            ContentResolver contentResolver = requireActivity().getContentResolver();
            ContentObserver contentObserver = this.excerptContentObserver;
            Intrinsics.checkNotNull(contentObserver);
            contentResolver.unregisterContentObserver(contentObserver);
        }
        if (this.mAccountManagerCallback != null) {
            Logger.INSTANCE.d(TAG, "releaseAccountManagerCallback");
            AccountMgrCallback accountMgrCallback = this.mAccountManagerCallback;
            Intrinsics.checkNotNull(accountMgrCallback);
            accountMgrCallback.setAcceptListener(null);
            AccountMgrCallback accountMgrCallback2 = this.mAccountManagerCallback;
            Intrinsics.checkNotNull(accountMgrCallback2);
            accountMgrCallback2.setToAddAccountLauncher(null);
            this.mAccountManagerCallback = null;
        }
        if (this.mHandler != null) {
            this.mHandler.removeCallbacks(this.mEditEnableRunnable);
        }
        SummaryResultObserver summaryResultObserver = this.mSummaryResultObserver;
        if (summaryResultObserver != null) {
            try {
                try {
                    ContentResolver contentResolver2 = this.mRegisteredContentResolver;
                    if (contentResolver2 != null) {
                        contentResolver2.unregisterContentObserver(summaryResultObserver);
                        Unit unit = Unit.INSTANCE;
                    }
                } catch (Exception e) {
                    Logger.INSTANCE.e(TAG, "onDestroy, unregisterContentObserver error: " + e);
                    Unit unit2 = Unit.INSTANCE;
                }
                summaryResultObserver.setOnChangeCallback(null);
                this.sumaryResultChangeCallback = null;
                this.mSummaryResultObserver = null;
                this.mRegisteredContentResolver = null;
                this.mIsRecordRecordSummaryRegistered = false;
            } catch (Throwable th) {
                summaryResultObserver.setOnChangeCallback(null);
                this.sumaryResultChangeCallback = null;
                this.mSummaryResultObserver = null;
                this.mRegisteredContentResolver = null;
                this.mIsRecordRecordSummaryRegistered = false;
                throw th;
            }
        }
        WebRichEditor webRichEditor = this.mEditor;
        if (webRichEditor != null) {
            Intrinsics.checkNotNull(webRichEditor);
            webRichEditor.setButtonStateListener(null);
            WebRichEditor webRichEditor2 = this.mEditor;
            Intrinsics.checkNotNull(webRichEditor2);
            webRichEditor2.setOnRenderProcessGoneListener(null);
            WebRichEditor webRichEditor3 = this.mEditor;
            Intrinsics.checkNotNull(webRichEditor3);
            webRichEditor3.destroy();
        }
        BottomSheetView bottomSheetView = this.mAiBottomSheetView;
        if (bottomSheetView != null) {
            Intrinsics.checkNotNull(bottomSheetView);
            bottomSheetView.dismissWithoutAnim();
        }
        AiController aiController = this.mAiController;
        if (aiController != null) {
            Intrinsics.checkNotNull(aiController);
            aiController.destroy();
        }
        NoteAiFloatingWindow noteAiFloatingWindow = mNoteAiFloatingWindow;
        if (noteAiFloatingWindow != null) {
            Intrinsics.checkNotNull(noteAiFloatingWindow);
            noteAiFloatingWindow.closeWindow();
        }
        NoteAiTextWidgetService noteAiTextWidgetService = mNoteAiTextWidgetService;
        if (noteAiTextWidgetService != null) {
            Intrinsics.checkNotNull(noteAiTextWidgetService);
            noteAiTextWidgetService.stopSelf();
            mNoteAiTextWidgetService = null;
        }
        mNoteAiFloatingWindow = null;
        RichEditorWebView richEditorWebView = this.mEditorWebView;
        if (richEditorWebView != null) {
            Intrinsics.checkNotNull(richEditorWebView);
            richEditorWebView.setOnTouchListener(null);
            RichEditorWebView richEditorWebView2 = this.mEditorWebView;
            Intrinsics.checkNotNull(richEditorWebView2);
            richEditorWebView2.destroy();
        }
        super.onDestroy();
    }

    @Override // com.miui.notes.basefeature.noteedit.BaseNormalNoteEditFragment, com.miui.notes.detail.BaseNoteDetailFragment, miuix.appcompat.app.Fragment, androidx.fragment.app.Fragment
    public void onDestroyView() {
        super.onDestroyView();
        ResourceManager.clearThemes();
        PermissionUtils.releaseListeners();
        onAnimThemeBgViewChanged(null);
        SummaryResultObserver summaryResultObserver = this.mSummaryResultObserver;
        if (summaryResultObserver != null) {
            try {
                try {
                    ContentResolver contentResolver = this.mRegisteredContentResolver;
                    if (contentResolver != null) {
                        contentResolver.unregisterContentObserver(summaryResultObserver);
                        Unit unit = Unit.INSTANCE;
                    }
                } catch (Exception e) {
                    Logger.INSTANCE.e(TAG, "onDestroyView, unregisterContentObserver error: " + e);
                    Unit unit2 = Unit.INSTANCE;
                }
                summaryResultObserver.setOnChangeCallback(null);
                this.sumaryResultChangeCallback = null;
                this.mSummaryResultObserver = null;
                this.mRegisteredContentResolver = null;
                this.mIsRecordRecordSummaryRegistered = false;
            } catch (Throwable th) {
                summaryResultObserver.setOnChangeCallback(null);
                this.sumaryResultChangeCallback = null;
                this.mSummaryResultObserver = null;
                this.mRegisteredContentResolver = null;
                this.mIsRecordRecordSummaryRegistered = false;
                throw th;
            }
        }
        if (Build.VERSION.SDK_INT >= 30 || !UIUtils.isInFullWindowGestureMode(getActivity())) {
            requireActivity().getWindow().setSoftInputMode(0);
        }
        if (getContext() != null) {
            try {
                requireContext().unregisterReceiver(this.mAiReceiver);
            } catch (Exception e2) {
                Logger.INSTANCE.e(TAG, "onDestroyView, unregisterReceiver error: " + e2);
            }
        }
        if (this.mAiRecorderServiceCallback != null) {
            AIRecorderManager.instance.unregisterRecorderCallback(requireContext().getPackageName(), this.mAiRecorderServiceCallback);
        }
        if (this.mAiRecorderServiceStateCallback != null) {
            AIRecorderManager.instance.unregisterAIRecorderServiceStateCallback(this.mAiRecorderServiceStateCallback);
        }
    }

    protected final void onChangeFontStyle() {
        WebRichEditor webRichEditor = this.mEditor;
        Intrinsics.checkNotNull(webRichEditor);
        webRichEditor.setFontStyle(MiFontStyle.Builder.build(UIUtils.getMiuiVersion(), !miuix.os.Build.IS_INTERNATIONAL_BUILD, DisplayUtils.getFontWeight(getContext()) - 50));
    }

    @Override // androidx.fragment.app.Fragment
    public void onPrepareOptionsMenu(Menu menu) {
        Intrinsics.checkNotNullParameter(menu, "menu");
        BaseActionBarController actionBarController = getActionBarController();
        if (actionBarController != null) {
            actionBarController.updateMenu(menu);
        }
        if (this.mFolderId == -2 || this.mFolderId == -5) {
            menu.findItem(R.id.move_to).setVisible(false);
        }
        super.onPrepareOptionsMenu(menu);
    }

    @Override // com.miui.notes.basefeature.noteedit.BaseNormalNoteEditFragment
    protected void setmEidtDoodleResult(Intent data) {
        Intrinsics.checkNotNullParameter(data, "data");
        String stringExtra = data.getStringExtra(DoodleFragment.KEY_IMAGE_NAME);
        this.mNewImageName = stringExtra;
        if (!TextUtils.isEmpty(stringExtra) && this.mPagePrepare) {
            String strSaveImageFile = AttachmentUtils.saveImageFile(BaseNoteApp.INSTANCE.getInstance(), new File(this.mNewImageName));
            if (strSaveImageFile == null) {
                strSaveImageFile = this.mDoodleFileId;
            }
            updateDoodle(strSaveImageFile, this.mDoodleFileId);
            this.mNewImageName = null;
            this.mDoodleFileId = null;
            return;
        }
        if (this.mDoodleFileId == null || !this.mPagePrepare) {
            return;
        }
        updateDoodle(null, this.mDoodleFileId);
        this.mDoodleFileId = null;
    }

    /* JADX WARN: Code duplicated, block: B:37:0x00ec  */
    /* JADX WARN: Code duplicated, block: B:39:0x0106  */
    protected boolean handleInstanceState(Bundle savedInstanceState) {
        WebRichEditor webRichEditor;
        WorkingNote workingNoteLoadNote = WorkingNote.INSTANCE.loadNote(savedInstanceState);
        Logger.INSTANCE.d(TAG, "handleInstanceState,workingNote");
        if (workingNoteLoadNote == null) {
            return false;
        }
        if (!RomUtils.isPadDevice()) {
            this.mSearchText = savedInstanceState != null ? savedInstanceState.getString("user_query") : null;
            this.mHighLightKeyWords = savedInstanceState != null ? savedInstanceState.getStringArrayList("user_ai_query") : null;
        }
        this.mNewDoodle = savedInstanceState != null && savedInstanceState.getBoolean("new_doodle");
        this.mWorkingNoteFromRestore = workingNoteLoadNote;
        String temAudioPath = com.miui.notes.tool.PreferenceUtils.getTemAudioPath(BaseNoteApp.INSTANCE.getInstance());
        Logger.INSTANCE.d(TAG, "tmpAudioPath :" + temAudioPath);
        if (!TextUtils.isEmpty(temAudioPath)) {
            if (!AudioUtils.isMp3FileNameFormat(temAudioPath)) {
                temAudioPath = AudioUtils.renameMp3FileToSha1(AudioUtils.NOTE_MP3_DIR_NAME, temAudioPath);
            }
            if (!TextUtils.isEmpty(temAudioPath)) {
                String content = workingNoteLoadNote.getContent();
                Intrinsics.checkNotNull(content);
                workingNoteLoadNote.setWorkingText(StringsKt.replace$default(content, AudioUtils.TEMP_AUDIO_FLAG, "<sound fileid=\"" + temAudioPath + "\" />", false, 4, (Object) null));
                workingNoteLoadNote.saveNote();
            }
        }
        if (workingNoteLoadNote.getMNoteEntity() != null) {
            NoteEntity mNoteEntity = workingNoteLoadNote.getMNoteEntity();
            Intrinsics.checkNotNull(mNoteEntity);
            if (mNoteEntity.isEmpty()) {
                NoteEntity mNoteEntity2 = workingNoteLoadNote.getMNoteEntity();
                Intrinsics.checkNotNull(mNoteEntity2);
                if (TextUtils.equals(mNoteEntity2.getNoteType(), "common") && this.homeViewModel != null) {
                    Logger.INSTANCE.d(TAG, "workingNote isEmpty,create new");
                    HomeViewModel homeViewModel = this.homeViewModel;
                    Intrinsics.checkNotNull(homeViewModel);
                    homeViewModel.createNormalNote();
                } else {
                    setWorkingNote(workingNoteLoadNote, false);
                    com.miui.notes.tool.PreferenceUtils.setTemAudioPath(BaseNoteApp.INSTANCE.getInstance(), "");
                    if (!TextUtils.isEmpty(this.mSearchText) && (webRichEditor = this.mEditor) != null) {
                        Logger.INSTANCE.d(TAG, "Reapplying search highlight after instance state restore");
                        webRichEditor.updateQueryContent(this.mSearchText, this.mHighLightKeyWords);
                    }
                }
            } else {
                setWorkingNote(workingNoteLoadNote, false);
                com.miui.notes.tool.PreferenceUtils.setTemAudioPath(BaseNoteApp.INSTANCE.getInstance(), "");
                if (!TextUtils.isEmpty(this.mSearchText)) {
                    Logger.INSTANCE.d(TAG, "Reapplying search highlight after instance state restore");
                    webRichEditor.updateQueryContent(this.mSearchText, this.mHighLightKeyWords);
                }
            }
        } else {
            setWorkingNote(workingNoteLoadNote, false);
            com.miui.notes.tool.PreferenceUtils.setTemAudioPath(BaseNoteApp.INSTANCE.getInstance(), "");
            if (!TextUtils.isEmpty(this.mSearchText)) {
                Logger.INSTANCE.d(TAG, "Reapplying search highlight after instance state restore");
                webRichEditor.updateQueryContent(this.mSearchText, this.mHighLightKeyWords);
            }
        }
        return true;
    }

    protected final void preHandleIntent(Intent intent) {
        if (intent == null || this.mLoadTask == null) {
            return;
        }
        this.mLoadTask.cancel();
        this.mLoadTask = null;
    }

    @Override // com.miui.notes.basefeature.noteedit.BaseNormalNoteEditFragment, com.miui.notes.detail.BaseNoteDetailFragment
    public void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        if (intent != null) {
            if (this.mPagePrepare) {
                this.mPendingHandleIntent = intent;
                handleIntentData(intent);
            } else {
                preHandleIntent(intent);
            }
            String action = intent.getAction();
            if (Intrinsics.areEqual("android.intent.action.VIEW", action) || Intrinsics.areEqual("android.intent.action.SEND", action)) {
                Logger.INSTANCE.d(TAG, "onNewIntent/intent action view or send");
                this.mNeedVisible = false;
                this.mNeedHideEditToolBar = true;
            }
        }
    }

    @Override // com.miui.notes.basefeature.noteedit.BaseNormalNoteEditFragment
    protected void sendAsMarkdown() {
        String aigcDataId;
        String aigcDataId2;
        Logger.INSTANCE.d(TAG, "sendAsMarkdown mWorkingNote isEmpty:" + isWorkingNoteEmpty());
        if (isAdded()) {
            if (this.mWorkingNote == null) {
                Toast.makeText(requireContext().getApplicationContext(), R.string.error_send_empty_note, 0).show();
                return;
            }
            updateWorkingTextAndTitle();
            if (isWorkingNoteEmpty()) {
                Toast.makeText(requireContext().getApplicationContext(), R.string.error_send_empty_note, 0).show();
                return;
            }
            WorkingNote workingNote = this.mWorkingNote;
            String str = "";
            if (workingNote != null && workingNote.getAiTextUsed() == 1) {
                WorkingNote workingNote2 = this.mWorkingNote;
                if (workingNote2 == null || (aigcDataId = workingNote2.getAigcDataId()) == null) {
                    aigcDataId = "";
                }
                WorkingNote workingNote3 = this.mWorkingNote;
                if (workingNote3 != null && (aigcDataId2 = workingNote3.getAigcDataId()) != null) {
                    str = aigcDataId2;
                }
                this.aigcData = new AigcPicMetaData(aigcDataId, str).toStr();
            } else {
                this.aigcData = "";
            }
            WebRichEditor webRichEditor = this.mEditor;
            Intrinsics.checkNotNull(webRichEditor);
            webRichEditor.setAigcData(this.aigcData);
            WebRichEditor webRichEditor2 = this.mEditor;
            Intrinsics.checkNotNull(webRichEditor2);
            webRichEditor2.getMarkdown(new WebRichEditor.OnWriteFinishedListener() { // from class: com.miui.notes.basefeature.noteedit.BaseHybridNoteEditFragment$$ExternalSyntheticLambda43
                @Override // com.miui.hybirdeditor.WebRichEditor.OnWriteFinishedListener
                public final void onWriteFinished(String str2) {
                    BaseHybridNoteEditFragment.sendAsMarkdown$lambda$10(this.f$0, str2);
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void sendAsMarkdown$lambda$10(final BaseHybridNoteEditFragment baseHybridNoteEditFragment, final String markdownName) {
        Intrinsics.checkNotNullParameter(markdownName, "markdownName");
        Logger.INSTANCE.d(TAG, "onMarkDown File build!!start FileManager!!");
        if (baseHybridNoteEditFragment.isAdded()) {
            baseHybridNoteEditFragment.mHandler.post(new Runnable() { // from class: com.miui.notes.basefeature.noteedit.BaseHybridNoteEditFragment$$ExternalSyntheticLambda51
                @Override // java.lang.Runnable
                public final void run() {
                    BaseHybridNoteEditFragment.sendAsMarkdown$lambda$10$lambda$9(this.f$0, markdownName);
                }
            });
            Intent intent = new Intent();
            if (RomUtils.isInternationalBuild()) {
                intent.setClassName(TapShareConstant.FILE_EXPLORER_GLOBAL_PACKAGE_NAME, "com.android.fileexplorer.FileExplorerTabActivity");
            } else {
                intent.setClassName(TapShareConstant.FILE_EXPLORER_PACKAGE_NAME, "com.android.fileexplorer.MainActivity");
            }
            intent.setFlags(268468224);
            intent.putExtra("current_directory", Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).getAbsolutePath());
            baseHybridNoteEditFragment.startActivity(intent);
            NoteContentStat.QUIT_WAY = NoteContentStat.QUIT_WAY_MARK_DOWN_EXPORT;
            baseHybridNoteEditFragment.trackReportQuitContent(baseHybridNoteEditFragment.mPendingHandleIntent, baseHybridNoteEditFragment.mWorkingNote);
            NoteContentStat.cancel();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void sendAsMarkdown$lambda$10$lambda$9(BaseHybridNoteEditFragment baseHybridNoteEditFragment, String str) {
        Resources resources;
        Context context = baseHybridNoteEditFragment.getContext();
        Context context2 = baseHybridNoteEditFragment.getContext();
        Toast.makeText(context, (context2 == null || (resources = context2.getResources()) == null) ? null : resources.getString(R.string.export_markdown_saved_at, str), 0).show();
    }

    @Override // com.miui.notes.model.WorkingNote.NoteSettingChangedListener
    public void onNoteThemeChanged() {
        if (isDetached() || this.mWorkingNote == null) {
            return;
        }
        WorkingNote workingNote = this.mWorkingNote;
        Intrinsics.checkNotNull(workingNote);
        updateNoteThemeOnNative(workingNote.getThemeId(), true, false);
        asyncSaveNote();
    }

    public final void onStartDragElement(final EmbedElementInfoBean infoBean) {
        Intrinsics.checkNotNullParameter(infoBean, "infoBean");
        this.mHandler.post(new Runnable() { // from class: com.miui.notes.basefeature.noteedit.BaseHybridNoteEditFragment$$ExternalSyntheticLambda50
            @Override // java.lang.Runnable
            public final void run() {
                BaseHybridNoteEditFragment.onStartDragElement$lambda$11(infoBean, this);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void onStartDragElement$lambda$11(EmbedElementInfoBean embedElementInfoBean, BaseHybridNoteEditFragment baseHybridNoteEditFragment) {
        WebRichEditor webRichEditor;
        if (embedElementInfoBean.type == 0) {
            WebRichEditor webRichEditor2 = baseHybridNoteEditFragment.mEditor;
            if (webRichEditor2 != null) {
                Intrinsics.checkNotNull(webRichEditor2);
                webRichEditor2.startDragImage(embedElementInfoBean);
                return;
            }
            return;
        }
        if (embedElementInfoBean.type == 1 || embedElementInfoBean.type == 2 || embedElementInfoBean.type != -1 || (webRichEditor = baseHybridNoteEditFragment.mEditor) == null) {
            return;
        }
        Intrinsics.checkNotNull(webRichEditor);
        webRichEditor.startDragText(embedElementInfoBean);
    }

    @Override // com.miui.notes.basefeature.noteedit.BaseNormalNoteEditFragment, com.miui.notes.model.WorkingNote.NoteSettingChangedListener
    public void onClockAlertChanged(long newDate, long oldDate) {
        WorkingNote workingNote = this.mWorkingNote;
        Intrinsics.checkNotNull(workingNote);
        if (workingNote.getNoteId() > 0) {
            WorkingNote workingNote2 = this.mWorkingNote;
            Intrinsics.checkNotNull(workingNote2);
            workingNote2.updateAlarmInfo();
            WebRichEditor webRichEditor = this.mEditor;
            Intrinsics.checkNotNull(webRichEditor);
            WorkingNote workingNote3 = this.mWorkingNote;
            Intrinsics.checkNotNull(workingNote3);
            webRichEditor.updateExtraInto(workingNote3.getExtraInfo());
        }
        super.onClockAlertChanged(newDate, oldDate);
    }

    @Override // com.miui.notes.basefeature.noteedit.BaseNormalNoteEditFragment, com.miui.notes.detail.BaseNoteDetailFragment, miuix.appcompat.app.Fragment, androidx.fragment.app.Fragment, android.content.ComponentCallbacks
    public void onConfigurationChanged(Configuration newConfig) {
        Intrinsics.checkNotNullParameter(newConfig, "newConfig");
        super.onConfigurationChanged(newConfig);
        Logger.INSTANCE.i(TAG, "onConfigurationChanged");
        if (RomUtils.isFlipFoldDevice() && DeviceHelper.isTinyScreen(requireContext().getApplicationContext())) {
            Logger.INSTANCE.i(TAG, "onConfigurationChanged, flip tiny screen stop ai");
            INSTANCE.removeAiFloatingWindow();
            mNoteAiFloatingWindow = null;
        }
        if (this.mPagePrepare) {
            checkEditEnable();
        }
        if (this.mShowingPopupWindow != null) {
            this.mShowingPopupWindow.dismiss();
            this.mShowingPopupWindow = null;
        }
        BottomSheetView bottomSheetView = this.mAiBottomSheetView;
        if (bottomSheetView != null) {
            Intrinsics.checkNotNull(bottomSheetView);
            bottomSheetView.adapterNavigationBar();
        }
        if (mFullScreen != (getActivity() != null && requireActivity().isInMultiWindowMode())) {
            INSTANCE.removeAiFloatingWindow();
            mNoteAiFloatingWindow = null;
        }
    }

    @Override // com.miui.notes.basefeature.noteedit.BaseNormalNoteEditFragment
    protected void initView(View rootView) {
        Intrinsics.checkNotNullParameter(rootView, "rootView");
        super.initView(rootView);
        this.mContentView = getView();
        this.mBgContentContainer = (SpringBackLayout) rootView.findViewById(R.id.content_container);
        this.mEditContainerLayout = (FrameLayout) rootView.findViewById(R.id.ll_note_edit);
    }

    @Override // com.miui.notes.basefeature.noteedit.BaseNormalNoteEditFragment
    protected void initNoteInfoHeader() {
        this.mNoteExtraInfoController = createNoteExtraInfoController(this.mRootView.findViewById(R.id.note_extra_info), this.mRootView.findViewById(R.id.note_folder_info));
        this.mNoteExtraInfoController.bindFragment(this);
        this.mNoteExtraInfoController.setNoteMenuClickListener(new INoteInfoHeader.NoteMenuClickListener() { // from class: com.miui.notes.basefeature.noteedit.BaseHybridNoteEditFragment.initNoteInfoHeader.1
            @Override // com.miui.notes.ui.view.INoteInfoHeader.NoteMenuClickListener
            public boolean onMenuClick() {
                if (BaseHybridNoteEditFragment.this.getContext() == null || !BaseHybridNoteEditFragment.this.isThisNoteDoExcerpt()) {
                    return true;
                }
                Toast.makeText(BaseHybridNoteEditFragment.this.requireContext().getApplicationContext(), R.string.excerpting, 0).show();
                return false;
            }

            @Override // com.miui.notes.ui.view.INoteInfoHeader.NoteMenuClickListener
            public void onItemChecked(long id, long[] checkedIds, String name) {
                Intrinsics.checkNotNullParameter(checkedIds, "checkedIds");
                Intrinsics.checkNotNullParameter(name, "name");
                BaseHybridNoteEditFragment.this.moveCheckedItems(id, checkedIds, name);
            }

            @Override // com.miui.notes.ui.view.INoteInfoHeader.NoteMenuClickListener
            public void onItemPerformClick(int position) {
                BaseHybridNoteEditFragment.this.startQueryMenuFolder(position);
            }
        });
    }

    @Override // com.miui.notes.basefeature.noteedit.BaseNormalNoteEditFragment
    protected BaseDragListener getActionBarBaseDragListener() {
        return new BaseDragListener() { // from class: com.miui.notes.basefeature.noteedit.BaseHybridNoteEditFragment.getActionBarBaseDragListener.1
            @Override // com.miui.notes.ui.BaseDragListener
            protected boolean handleDragDrop(View v, DragEvent event) {
                return false;
            }

            @Override // com.miui.notes.ui.BaseDragListener
            protected boolean handleDragStarted(View v, DragEvent event) {
                Intrinsics.checkNotNullParameter(event, "event");
                WebRichEditor webRichEditor = BaseHybridNoteEditFragment.this.mEditor;
                Intrinsics.checkNotNull(webRichEditor);
                return webRichEditor.isValidDragEventData(event);
            }

            @Override // com.miui.notes.ui.BaseDragListener
            protected boolean handleDragEntered(View v, DragEvent event) {
                Intrinsics.checkNotNullParameter(v, "v");
                return v.onDragEvent(event);
            }
        };
    }

    @Override // com.miui.notes.basefeature.noteedit.BaseNormalNoteEditFragment
    protected void initNoteEditor(View rootView) {
        Intrinsics.checkNotNullParameter(rootView, "rootView");
        RichEditorWebView richEditorWebView = (RichEditorWebView) rootView.findViewById(R.id.web_view);
        this.mEditorWebView = richEditorWebView;
        Intrinsics.checkNotNull(richEditorWebView);
        richEditorWebView.setBackgroundColor(0);
        RichEditorWebView richEditorWebView2 = this.mEditorWebView;
        Intrinsics.checkNotNull(richEditorWebView2);
        richEditorWebView2.setOnTouchListener(new View.OnTouchListener() { // from class: com.miui.notes.basefeature.noteedit.BaseHybridNoteEditFragment$$ExternalSyntheticLambda7
            @Override // android.view.View.OnTouchListener
            public final boolean onTouch(View view, MotionEvent motionEvent) {
                return BaseHybridNoteEditFragment.initNoteEditor$lambda$12(this.f$0, view, motionEvent);
            }
        });
        View viewFindViewById = rootView.findViewById(R.id.loading_container);
        RichEditorWebView richEditorWebView3 = this.mEditorWebView;
        Intrinsics.checkNotNull(richEditorWebView3);
        richEditorWebView3.setLoadingContainer(viewFindViewById);
        RichEditorWebView richEditorWebView4 = this.mEditorWebView;
        Intrinsics.checkNotNull(richEditorWebView4);
        FragmentActivity fragmentActivityRequireActivity = requireActivity();
        Intrinsics.checkNotNullExpressionValue(fragmentActivityRequireActivity, "requireActivity(...)");
        richEditorWebView4.setActivity(fragmentActivityRequireActivity);
        RichEditorWebView richEditorWebView5 = this.mEditorWebView;
        Intrinsics.checkNotNull(richEditorWebView5);
        richEditorWebView5.setConsumeTouch(true);
        RichEditorWebView richEditorWebView6 = this.mEditorWebView;
        Intrinsics.checkNotNull(richEditorWebView6);
        richEditorWebView6.setOnEditBarListener(new RichEditorWebView.OnEditBarListener() { // from class: com.miui.notes.basefeature.noteedit.BaseHybridNoteEditFragment.initNoteEditor.2
            @Override // com.miui.hybirdeditor.RichEditorWebView.OnEditBarListener
            public void doCut() {
                BaseHybridNoteEditFragment baseHybridNoteEditFragment = BaseHybridNoteEditFragment.this;
                baseHybridNoteEditFragment.trackReportClipBoard(baseHybridNoteEditFragment.mWorkingNote, NotesTextStat.CUT);
            }

            @Override // com.miui.hybirdeditor.RichEditorWebView.OnEditBarListener
            public void doPaste() {
                BaseHybridNoteEditFragment baseHybridNoteEditFragment = BaseHybridNoteEditFragment.this;
                baseHybridNoteEditFragment.trackReportClipBoard(baseHybridNoteEditFragment.mWorkingNote, NotesTextStat.PASTE);
            }

            @Override // com.miui.hybirdeditor.RichEditorWebView.OnEditBarListener
            public void doShare() {
                BaseHybridNoteEditFragment baseHybridNoteEditFragment = BaseHybridNoteEditFragment.this;
                baseHybridNoteEditFragment.trackReportClipBoard(baseHybridNoteEditFragment.mWorkingNote, NotesTextStat.SHARE);
            }

            @Override // com.miui.hybirdeditor.RichEditorWebView.OnEditBarListener
            public void doCopy() {
                BaseHybridNoteEditFragment baseHybridNoteEditFragment = BaseHybridNoteEditFragment.this;
                baseHybridNoteEditFragment.trackReportClipBoard(baseHybridNoteEditFragment.mWorkingNote, NotesTextStat.COPY);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final boolean initNoteEditor$lambda$12(BaseHybridNoteEditFragment baseHybridNoteEditFragment, View view, MotionEvent motionEvent) {
        MamlView mamlView = baseHybridNoteEditFragment.mAnimThemeBgView;
        if (mamlView == null) {
            return false;
        }
        mamlView.onTouchEvent(motionEvent);
        return false;
    }

    protected void initNoteEditor(View rootView, int themeId) {
        Intrinsics.checkNotNullParameter(rootView, "rootView");
        Logger.INSTANCE.d(TAG, "initNoteEditor..");
        initNoteEditor(rootView);
        WeakReference weakReference = new WeakReference(getMEditorActionListener());
        RichEditorWebView richEditorWebView = this.mEditorWebView;
        HybridEditorScriptInterface hybridEditorScriptInterface = this.mJsBridge;
        Intrinsics.checkNotNull(hybridEditorScriptInterface);
        WebRichEditor webRichEditor = new WebRichEditor(richEditorWebView, hybridEditorScriptInterface, (EditorActionListener) weakReference.get(), com.miui.notes.tool.PreferenceUtils.getFontSize(getActivity(), 1), this.mIsPcMode);
        this.mEditor = webRichEditor;
        Intrinsics.checkNotNull(webRichEditor);
        webRichEditor.setOnRenderProcessGoneListener(new WebRichEditorClient.onRenderProcessGoneListener() { // from class: com.miui.notes.basefeature.noteedit.BaseHybridNoteEditFragment$$ExternalSyntheticLambda3
            @Override // com.miui.hybirdeditor.WebRichEditorClient.onRenderProcessGoneListener
            public final void onRenderProcessExit(WebView webView) {
                BaseHybridNoteEditFragment.initNoteEditor$lambda$13(this.f$0, webView);
            }
        });
        Logger.INSTANCE.d(TAG, "initNoteEditor,init webCore");
        WebRichEditor webRichEditor2 = this.mEditor;
        Intrinsics.checkNotNull(webRichEditor2);
        Theme theme = ResourceManager.getTheme(themeId);
        Intrinsics.checkNotNullExpressionValue(theme, "getTheme(...)");
        webRichEditor2.initWebCore(theme);
        this.mNoteRichEditor = this.mEditor;
        WebRichEditor webRichEditor3 = this.mEditor;
        Intrinsics.checkNotNull(webRichEditor3);
        webRichEditor3.setButtonStateListener(new WebRichEditor.ButtonStateListener() { // from class: com.miui.notes.basefeature.noteedit.BaseHybridNoteEditFragment$$ExternalSyntheticLambda4
            @Override // com.miui.hybirdeditor.WebRichEditor.ButtonStateListener
            public final void onUndoRedoStateChanged(boolean z, boolean z2) {
                BaseHybridNoteEditFragment.initNoteEditor$lambda$15(this.f$0, z, z2);
            }
        });
        this.mEditorMaskView = (FrameLayout) rootView.findViewById(R.id.content_mask);
        HybridRichEditorDragListener hybridRichEditorDragListener = new HybridRichEditorDragListener(0);
        this.mDragListener = hybridRichEditorDragListener;
        Intrinsics.checkNotNull(hybridRichEditorDragListener);
        hybridRichEditorDragListener.setEditFragment(this, this.mEditor);
        WebRichEditor webRichEditor4 = this.mEditor;
        Intrinsics.checkNotNull(webRichEditor4);
        webRichEditor4.setOnDragListener(this.mDragListener);
        initToolbarController(rootView);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void initNoteEditor$lambda$13(BaseHybridNoteEditFragment baseHybridNoteEditFragment, WebView webView) {
        Logger.INSTANCE.e(TAG, "onRenderProcessExit");
        if (webView != baseHybridNoteEditFragment.mEditorWebView || baseHybridNoteEditFragment.getActivity() == null) {
            return;
        }
        Logger.INSTANCE.e(TAG, "finish activity ");
        baseHybridNoteEditFragment.requireActivity().finish();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void initNoteEditor$lambda$15(final BaseHybridNoteEditFragment baseHybridNoteEditFragment, final boolean z, final boolean z2) {
        baseHybridNoteEditFragment.mHandler.post(new Runnable() { // from class: com.miui.notes.basefeature.noteedit.BaseHybridNoteEditFragment$$ExternalSyntheticLambda35
            @Override // java.lang.Runnable
            public final void run() {
                BaseHybridNoteEditFragment.initNoteEditor$lambda$15$lambda$14(this.f$0, z, z2);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void initNoteEditor$lambda$15$lambda$14(BaseHybridNoteEditFragment baseHybridNoteEditFragment, boolean z, boolean z2) {
        if (baseHybridNoteEditFragment.mActionBarController != null) {
            baseHybridNoteEditFragment.mActionBarController.updateUndoState(z, z2);
        }
    }

    public final boolean isInRecord() {
        return this.mIsRecording;
    }

    @Override // com.miui.notes.basefeature.noteedit.BaseNormalNoteEditFragment
    protected void setEnableEdit(boolean enableEdit) {
        Logger.INSTANCE.d(TAG, "setEnableEdit:" + enableEdit);
        if (enableEdit && BaseNormalNoteEditFragment.mWarnToast != null) {
            BaseNormalNoteEditFragment.mWarnToast.cancel();
        }
        if (this.mInAiMode && enableEdit) {
            return;
        }
        this.mEnableEdit = enableEdit;
        if (this.mHandler != null) {
            this.mHandler.removeCallbacks(this.mEditEnableRunnable);
            this.mHandler.postDelayed(this.mEditEnableRunnable, 300L);
        }
    }

    public final int getWidgetId() {
        if (this.mWorkingNote == null) {
            return 0;
        }
        WorkingNote workingNote = this.mWorkingNote;
        Intrinsics.checkNotNull(workingNote);
        return workingNote.getWidgetId();
    }

    public final int getWidgetType() {
        if (this.mWorkingNote == null) {
            return 0;
        }
        WorkingNote workingNote = this.mWorkingNote;
        Intrinsics.checkNotNull(workingNote);
        return workingNote.getWidgetType();
    }

    protected void updateNoteThemeOnNative(int themeId, final boolean withAnim, boolean forceUpdate) {
        Logger.INSTANCE.d(TAG, "updateNoteThemeOnNative mIsChangingTheme" + this.mIsChangingTheme + ",themeId:" + themeId + ",currentTheme:" + this.mCurrentTheme + ",forceUpdate:" + forceUpdate);
        if (this.mEditToolbarController != null) {
            this.mEditToolbarController.lock();
        }
        if (this.mCurrentTheme != null && this.mCurrentTheme.getId() == themeId && !forceUpdate) {
            onNoteThemeChangeFinish();
            return;
        }
        if (this.mIsChangingTheme) {
            return;
        }
        this.mIsChangingTheme = true;
        this.mResLoadDisposables.clear();
        if (!forceUpdate) {
            WebRichEditor webRichEditor = this.mEditor;
            Intrinsics.checkNotNull(webRichEditor);
            if (webRichEditor.getEditorTheme() != null) {
                WebRichEditor webRichEditor2 = this.mEditor;
                Intrinsics.checkNotNull(webRichEditor2);
                Theme editorTheme = webRichEditor2.getEditorTheme();
                Intrinsics.checkNotNull(editorTheme);
                if (themeId == editorTheme.getId()) {
                    this.mCurrentTheme = ResourceManager.getTheme(themeId);
                    onNoteThemeChangeFinish();
                    return;
                }
            }
        }
        final Theme theme = ResourceManager.getTheme(themeId);
        if (this.mCurrentTheme != null || ResourceManager.pPreLoadResCache == null || ResourceManager.pPreLoadResCache.themeId != themeId) {
            Logger.INSTANCE.d(TAG, "LoadNoteThemeResTask start");
            new LoadNoteThemeResTask(this.mResLoadDisposables) { // from class: com.miui.notes.basefeature.noteedit.BaseHybridNoteEditFragment.updateNoteThemeOnNative.1
                /* JADX INFO: Access modifiers changed from: protected */
                /* JADX WARN: Can't rename method to resolve collision */
                @Override // com.miui.common.tool.RxStandardAsyncTask
                public NoteThemeResCache doInBackground() {
                    Logger.INSTANCE.d(BaseHybridNoteEditFragment.TAG, "LoadNoteThemeResTask/doingBackground");
                    return BaseHybridNoteEditFragment.this.loadNoteThemeResToCache(theme);
                }

                /* JADX INFO: Access modifiers changed from: protected */
                @Override // com.miui.common.tool.RxStandardAsyncTask
                public void onPost(NoteThemeResCache result) {
                    try {
                        BaseHybridNoteEditFragment baseHybridNoteEditFragment = BaseHybridNoteEditFragment.this;
                        Theme theme2 = theme;
                        Intrinsics.checkNotNull(theme2);
                        baseHybridNoteEditFragment.onPostUpdateNoteTheme(theme2, result, withAnim);
                    } catch (Exception e) {
                        Logger.INSTANCE.e(BaseHybridNoteEditFragment.TAG, "error in onPostUpdateNoteTheme" + e);
                    }
                }
            }.execute();
        } else {
            Intrinsics.checkNotNull(theme);
            onPostUpdateNoteTheme(theme, ResourceManager.pPreLoadResCache, withAnim);
            ResourceManager.clearPreloadResCache();
        }
    }

    protected void onPostUpdateNoteTheme(Theme theme, NoteThemeResCache resCache, boolean withAnim) {
        Intrinsics.checkNotNullParameter(theme, "theme");
        updateEditorTheme(theme, Boolean.valueOf(!this.isLite));
        this.mIsForceDark = theme.getStatusBarStyle(getActivity()).mStatusBar == 2;
        if (getActionBarController() != null) {
            getActionBarController().updateStyle(theme, resCache);
        }
        updateWindowStatus(this.mIsForceDark);
        if (this.isLite) {
            updateEditContentBgStyleLite(theme, false);
        } else {
            updateEditContentBgStyle(theme, withAnim);
        }
        if (this.mEditToolbarController != null) {
            this.mEditToolbarController.updateTheme(theme, resCache);
            this.mEditToolbarController.unlock();
        }
        if (this.mSharePopupWindow != null) {
            this.mSharePopupWindow.updateStyle(theme);
        }
        if (this.mCurrentTheme == null || !Intrinsics.areEqual(this.mCurrentTheme, theme)) {
            this.mCurrentTheme = theme;
        }
        this.mIsChangingTheme = false;
    }

    protected final void updateEditContentBgStyleLite(Theme theme, boolean withAnim) {
        boolean z;
        Intrinsics.checkNotNullParameter(theme, "theme");
        Theme.EditorStyle editorStyle = theme.getEditorStyle(getActivity());
        Rect rect = new Rect();
        if (theme instanceof AnimTheme) {
            if (this.mAnimThemeLiteBgView != null) {
                this.mAnimThemeLiteBgView.setBackground(editorStyle.mBackground);
            }
            if (getView() != null) {
                requireView().setBackground(null);
            }
        } else {
            if (this.mAnimThemeLiteBgView != null) {
                this.mAnimThemeLiteBgView.setBackground(null);
            }
            if (getView() != null) {
                requireView().setBackground(editorStyle.mBackground);
            }
        }
        rect.set(theme.getForegroundPadding(getActivity()));
        if (theme.getId() == 7 || theme.getId() == 8 || theme.getId() == 6) {
            if (theme.getId() == 7) {
                rect.left = (int) requireContext().getResources().getDimension(R.dimen.theme_antique_header_padding_horizontal);
                rect.right = rect.left;
            }
            z = false;
        } else {
            z = true;
        }
        Rect rect2 = new Rect(rect);
        if (z) {
            rect2.left = (int) (rect2.left + getNoteEditorPadding());
            rect2.right = (int) (rect2.right + getNoteEditorPadding());
        }
        if (withAnim) {
            changePaddingOnThemeChanged(theme, rect2);
        } else if (getActionBarController() != null) {
            getActionBarController().updateHeadInfoMargin(rect2);
        }
        onNoteThemeChangeFinish();
    }

    protected float getNoteEditorPadding() {
        return requireContext().getResources().getDimension(R.dimen.note_editor_padding);
    }

    protected final void updateEditContentBgStyle(Theme theme, boolean withAnim) {
        boolean z;
        long j;
        final int i;
        Intrinsics.checkNotNullParameter(theme, "theme");
        if (isAdded()) {
            final ViewGroup viewGroup = (ViewGroup) getView();
            final Theme.EditorStyle editorStyle = theme.getEditorStyle(getContext());
            Rect rect = new Rect();
            rect.set(theme.getForegroundPadding(getContext()));
            if (theme.getId() == 7 || theme.getId() == 8 || theme.getId() == 6) {
                if (theme.getId() == 7) {
                    rect.left = (int) requireContext().getResources().getDimension(R.dimen.theme_antique_header_padding_horizontal);
                    rect.right = rect.left;
                }
                z = false;
            } else {
                z = true;
            }
            Rect rect2 = new Rect(rect);
            if (z) {
                rect2.left = (int) (rect2.left + getNoteEditorPadding());
                rect2.right = (int) (rect2.right + getNoteEditorPadding());
            }
            if (withAnim) {
                changePaddingOnThemeChanged(theme, rect2);
            } else if (getActionBarController() != null && !RomUtils.isPadMode()) {
                getActionBarController().updateHeadInfoMargin(rect2);
            }
            if (theme instanceof AnimTheme) {
                AnimTheme animTheme = (AnimTheme) theme;
                final MamlView backgroundView = animTheme.getBackgroundView(getContext());
                if (backgroundView != null) {
                    if (this.mCurrentTheme == null) {
                        this.mContentView.setBackground(new ColorDrawable(requireContext().getColor(editorStyle.mBlurMixColor3)));
                    }
                    if (this.mCurrentTheme == null || !(this.mCurrentTheme instanceof AnimTheme) || this.mAnimThemeBgView == null) {
                        j = 50;
                        i = 0;
                    } else {
                        Theme theme2 = this.mCurrentTheme;
                        Intrinsics.checkNotNull(theme2, "null cannot be cast to non-null type com.miui.notes.theme.AnimTheme");
                        AnimTheme animTheme2 = (AnimTheme) theme2;
                        animTheme2.pause();
                        animTheme2.onDestroyView();
                        j = 800;
                        i = 1;
                    }
                    final long j2 = j;
                    backgroundView.setAlpha(1.0f);
                    animTheme.reload();
                    if (backgroundView.getParent() != null) {
                        ViewParent parent = backgroundView.getParent();
                        Intrinsics.checkNotNull(parent, "null cannot be cast to non-null type android.view.ViewGroup");
                        ((ViewGroup) parent).removeView(backgroundView);
                    }
                    Intrinsics.checkNotNull(viewGroup);
                    viewGroup.postDelayed(new Runnable() { // from class: com.miui.notes.basefeature.noteedit.BaseHybridNoteEditFragment$$ExternalSyntheticLambda18
                        @Override // java.lang.Runnable
                        public final void run() {
                            BaseHybridNoteEditFragment.updateEditContentBgStyle$lambda$17(backgroundView, viewGroup, i, this, j2, editorStyle);
                        }
                    }, 0L);
                    return;
                }
                return;
            }
            if (this.mAnimThemeBgView != null) {
                if (this.mCurrentTheme != null && (this.mCurrentTheme instanceof AnimTheme)) {
                    Theme theme3 = this.mCurrentTheme;
                    Intrinsics.checkNotNull(theme3, "null cannot be cast to non-null type com.miui.notes.theme.AnimTheme");
                    final AnimTheme animTheme3 = (AnimTheme) theme3;
                    this.mAnimThemeBgView.animate().alpha(0.0f).withEndAction(new Runnable() { // from class: com.miui.notes.basefeature.noteedit.BaseHybridNoteEditFragment$$ExternalSyntheticLambda19
                        @Override // java.lang.Runnable
                        public final void run() {
                            BaseHybridNoteEditFragment.updateEditContentBgStyle$lambda$18(this.f$0, animTheme3, viewGroup);
                        }
                    }).setDuration(300L).start();
                }
            } else {
                onNoteThemeChangeFinish();
            }
            this.mContentView.setBackground(editorStyle.mBackground);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void updateEditContentBgStyle$lambda$17(View view, final ViewGroup viewGroup, final int i, final BaseHybridNoteEditFragment baseHybridNoteEditFragment, long j, final Theme.EditorStyle editorStyle) {
        if (view.getParent() != null) {
            ViewParent parent = view.getParent();
            Intrinsics.checkNotNull(parent, "null cannot be cast to non-null type android.view.ViewGroup");
            ((ViewGroup) parent).removeView(view);
        }
        view.setFocusable(false);
        view.setFocusableInTouchMode(false);
        view.setImportantForAccessibility(2);
        viewGroup.addView(view, i, new ViewGroup.LayoutParams(-1, -1));
        baseHybridNoteEditFragment.onAnimThemeBgViewChanged(view);
        baseHybridNoteEditFragment.mHandler.postDelayed(new Runnable() { // from class: com.miui.notes.basefeature.noteedit.BaseHybridNoteEditFragment$$ExternalSyntheticLambda40
            @Override // java.lang.Runnable
            public final void run() {
                BaseHybridNoteEditFragment.updateEditContentBgStyle$lambda$17$lambda$16(this.f$0, editorStyle, i, viewGroup);
            }
        }, j);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void updateEditContentBgStyle$lambda$17$lambda$16(BaseHybridNoteEditFragment baseHybridNoteEditFragment, Theme.EditorStyle editorStyle, int i, ViewGroup viewGroup) {
        if (baseHybridNoteEditFragment.isAdded()) {
            baseHybridNoteEditFragment.mContentView.setBackground(new ColorDrawable(baseHybridNoteEditFragment.requireContext().getColor(editorStyle.mBlurMixColor3)));
            if (i > 0) {
                viewGroup.removeViewAt(0);
            }
            baseHybridNoteEditFragment.onNoteThemeChangeFinish();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void updateEditContentBgStyle$lambda$18(BaseHybridNoteEditFragment baseHybridNoteEditFragment, AnimTheme animTheme, ViewGroup viewGroup) {
        if (baseHybridNoteEditFragment.isAdded()) {
            animTheme.pause();
            animTheme.onDestroyView();
            Intrinsics.checkNotNull(viewGroup);
            viewGroup.removeViewAt(0);
            baseHybridNoteEditFragment.onAnimThemeBgViewChanged(null);
            baseHybridNoteEditFragment.onNoteThemeChangeFinish();
        }
    }

    protected final void updateEditorTheme(Theme theme, Boolean withAnim) {
        long modifiedDate;
        Logger logger = Logger.INSTANCE;
        String simpleName = getClass().getSimpleName();
        Intrinsics.checkNotNullExpressionValue(simpleName, "getSimpleName(...)");
        logger.d(simpleName, "updateEditorTheme");
        WebRichEditor webRichEditor = this.mEditor;
        Intrinsics.checkNotNull(webRichEditor);
        if (this.mWorkingNote == null) {
            modifiedDate = System.currentTimeMillis();
        } else {
            WorkingNote workingNote = this.mWorkingNote;
            Intrinsics.checkNotNull(workingNote);
            modifiedDate = workingNote.getModifiedDate();
        }
        webRichEditor.setTheme(theme, modifiedDate);
    }

    @Override // com.miui.notes.basefeature.noteedit.BaseNormalNoteEditFragment
    protected void responseEditUiState() {
        if (isInEditAction()) {
            notifyEditUiState(1);
        } else {
            notifyEditUiState(0);
        }
    }

    protected final void changePaddingOnThemeChanged(Theme newTheme, final Rect headerNewPadding) {
        Intrinsics.checkNotNullParameter(headerNewPadding, "headerNewPadding");
        if (getActionBarController() == null) {
            return;
        }
        if (this.mEditUiPaddingAnimator != null && this.mEditUiPaddingAnimator.isRunning()) {
            this.mEditUiPaddingAnimator.cancel();
        }
        final Rect rect = new Rect();
        ViewGroup.LayoutParams layoutParams = getActionBarController().getHeadInfo().getLayoutParams();
        Intrinsics.checkNotNull(layoutParams, "null cannot be cast to non-null type android.widget.FrameLayout.LayoutParams");
        FrameLayout.LayoutParams layoutParams2 = (FrameLayout.LayoutParams) layoutParams;
        rect.left = layoutParams2.leftMargin;
        rect.right = layoutParams2.rightMargin;
        ValueAnimator valueAnimatorOfFloat = ValueAnimator.ofFloat(0.0f, 1.0f);
        valueAnimatorOfFloat.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: com.miui.notes.basefeature.noteedit.BaseHybridNoteEditFragment.changePaddingOnThemeChanged.1
            private float fraction;
            private final IntEvaluator evaluator = new IntEvaluator();
            private Rect infoHeadMargin = new Rect();

            public final IntEvaluator getEvaluator() {
                return this.evaluator;
            }

            public final Rect getInfoHeadMargin() {
                return this.infoHeadMargin;
            }

            public final void setInfoHeadMargin(Rect rect2) {
                Intrinsics.checkNotNullParameter(rect2, "<set-?>");
                this.infoHeadMargin = rect2;
            }

            public final float getFraction() {
                return this.fraction;
            }

            public final void setFraction(float f) {
                this.fraction = f;
            }

            @Override // android.animation.ValueAnimator.AnimatorUpdateListener
            public void onAnimationUpdate(ValueAnimator animation) {
                Intrinsics.checkNotNullParameter(animation, "animation");
                float animatedFraction = animation.getAnimatedFraction();
                this.fraction = animatedFraction;
                Rect rect2 = this.infoHeadMargin;
                Integer numEvaluate = this.evaluator.evaluate(animatedFraction, Integer.valueOf(rect.left), Integer.valueOf(headerNewPadding.left));
                Intrinsics.checkNotNullExpressionValue(numEvaluate, "evaluate(...)");
                rect2.left = numEvaluate.intValue();
                Rect rect3 = this.infoHeadMargin;
                Integer numEvaluate2 = this.evaluator.evaluate(this.fraction, Integer.valueOf(rect.right), Integer.valueOf(headerNewPadding.right));
                Intrinsics.checkNotNullExpressionValue(numEvaluate2, "evaluate(...)");
                rect3.right = numEvaluate2.intValue();
                this.getActionBarController().updateHeadInfoMargin(this.infoHeadMargin);
            }
        });
        valueAnimatorOfFloat.addListener(new Animator.AnimatorListener() { // from class: com.miui.notes.basefeature.noteedit.BaseHybridNoteEditFragment.changePaddingOnThemeChanged.2
            @Override // android.animation.Animator.AnimatorListener
            public void onAnimationCancel(Animator animation) {
                Intrinsics.checkNotNullParameter(animation, "animation");
            }

            @Override // android.animation.Animator.AnimatorListener
            public void onAnimationRepeat(Animator animation) {
                Intrinsics.checkNotNullParameter(animation, "animation");
            }

            @Override // android.animation.Animator.AnimatorListener
            public void onAnimationStart(Animator animation) {
                Intrinsics.checkNotNullParameter(animation, "animation");
                BaseHybridNoteEditFragment.this.mIsChangingTheme = true;
            }

            @Override // android.animation.Animator.AnimatorListener
            public void onAnimationEnd(Animator animation) {
                Intrinsics.checkNotNullParameter(animation, "animation");
                BaseHybridNoteEditFragment.this.mIsChangingTheme = false;
            }
        });
        valueAnimatorOfFloat.setInterpolator(new EaseManager.SpringInterpolator());
        valueAnimatorOfFloat.start();
        this.mEditUiPaddingAnimator = valueAnimatorOfFloat;
    }

    @Override // com.miui.notes.basefeature.noteedit.BaseNormalNoteEditFragment
    protected void requestXiaoAiPermission() {
        Logger.INSTANCE.d(TAG, "requestXiaoAiPermission!!");
        FragmentActivity activity = getActivity();
        if (activity == null || activity.isFinishing()) {
            return;
        }
        if (!com.miui.notes.tool.PreferenceUtils.getAllowXiaoai(activity) && !RomUtils.isInternationalBuild()) {
            manageDialog(PermissionUtils.showXiaoaiAcceptDialog(activity, new PermissionUtils.AcceptListener() { // from class: com.miui.notes.basefeature.noteedit.BaseHybridNoteEditFragment.requestXiaoAiPermission.1
                @Override // com.miui.notes.tool.PermissionUtils.AcceptListener
                public void accept() {
                    Logger.INSTANCE.d(BaseHybridNoteEditFragment.TAG, "accept!!");
                    BaseHybridNoteEditFragment.this.updateRecordMode();
                }

                @Override // com.miui.notes.tool.PermissionUtils.AcceptListener
                public void reject() {
                    Logger.INSTANCE.d(BaseHybridNoteEditFragment.TAG, "reject!!");
                }
            }));
        } else {
            updateRecordMode();
        }
    }

    @Override // com.miui.notes.basefeature.noteedit.BaseNormalNoteEditFragment
    protected void updateRecordMode() {
        Logger.INSTANCE.d(TAG, "updateRecordMode,isRecording=true");
        super.updateRecordMode();
        WebRichEditor webRichEditor = this.mEditor;
        Intrinsics.checkNotNull(webRichEditor);
        webRichEditor.insertAudioStart();
        updateActionMode(EditorActionMode.EDIT_RECODE, true);
        unregisterTapShareCallback();
    }

    protected void editImage(EmbedElementInfoBean embedElementInfo) {
        Intrinsics.checkNotNullParameter(embedElementInfo, "embedElementInfo");
        if (embedElementInfo.type == 0) {
            WebRichEditor webRichEditor = this.mEditor;
            Intrinsics.checkNotNull(webRichEditor);
            webRichEditor.editImageStart();
            DoodleParams doodleParamsCreateEditImageParams = createEditImageParams(embedElementInfo);
            if (doodleParamsCreateEditImageParams != null) {
                this.mDoodleFileId = embedElementInfo.fileName;
                ActivityResultLauncher<Intent> mEidtDooleResult = this.mEidtDooleResult;
                Intrinsics.checkNotNullExpressionValue(mEidtDooleResult, "mEidtDooleResult");
                startToDoodle(doodleParamsCreateEditImageParams, mEidtDooleResult);
            }
        }
    }

    @Override // com.miui.notes.basefeature.noteedit.BaseNormalNoteEditFragment
    protected void startToDoodle(DoodleParams params, ActivityResultLauncher<Intent> doodleResult) {
        Intent intent;
        Intrinsics.checkNotNullParameter(params, "params");
        Intrinsics.checkNotNullParameter(doodleResult, "doodleResult");
        if (isAdded()) {
            if (this.mWorkingNote != null) {
                WorkingNote workingNote = this.mWorkingNote;
                Intrinsics.checkNotNull(workingNote);
                if (Intrinsics.areEqual("", workingNote.getContent())) {
                    this.mNewDoodle = true;
                }
            }
            if (RomUtils.isFoldDevice()) {
                intent = new Intent(getContext(), (Class<?>) PadFullDoodleActivity.class);
            } else if (RomUtils.isPadDevice()) {
                intent = new Intent(getContext(), (Class<?>) PadDoodleActivity.class);
            } else {
                intent = new Intent(getActivity(), (Class<?>) DoodleActivity.class);
            }
            Logger.INSTANCE.d(TAG, "startToDoodle ID_PRIVACY_FOLDER: -4");
            intent.putExtra(EXTRA_KEY_IS_PRIVACY, getWorkingNote() != null && getWorkingNote().getFolderId() == -4);
            intent.putExtra(DoodleFragment.KEY_PARAMS, params);
            if (this.mWorkingNote != null) {
                WorkingNote workingNote2 = this.mWorkingNote;
                Intrinsics.checkNotNull(workingNote2);
                intent.putExtra(DoodleActivity.EXTRA_KEY_IS_HIDDEN, workingNote2.isHidden());
            }
            notifyAction(3, null);
            doodleResult.launch(intent);
        }
    }

    public final DoodleParams createEditImageParams(EmbedElementInfoBean imageInfo) {
        Intrinsics.checkNotNullParameter(imageInfo, "imageInfo");
        if (isAdded() && !TextUtils.isEmpty(imageInfo.fileName)) {
            return createEditImageParams(imageInfo.fileName);
        }
        return null;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.miui.notes.basefeature.noteedit.BaseNormalNoteEditFragment
    public void addAttachment(int type) {
        Logger.INSTANCE.d(TAG, "addAttachment");
        updateActionMode(EditorActionMode.VIEW, true);
        Utils.hideSoftInput(getView());
        super.addAttachment(type);
    }

    @Override // com.miui.notes.basefeature.noteedit.BaseNormalNoteEditFragment
    public void insertImages(boolean needWithPos, Uri... imageUris) {
        Intrinsics.checkNotNullParameter(imageUris, "imageUris");
        WebRichEditor webRichEditor = this.mEditor;
        Intrinsics.checkNotNull(webRichEditor);
        webRichEditor.insertImages(needWithPos, (Uri[]) Arrays.copyOf(imageUris, imageUris.length));
    }

    @Override // com.miui.notes.basefeature.noteedit.BaseNormalNoteEditFragment
    public void insertContent(String text) {
        WebRichEditor webRichEditor = this.mEditor;
        Intrinsics.checkNotNull(webRichEditor);
        webRichEditor.insertContent(text);
    }

    @Override // com.miui.notes.basefeature.noteedit.BaseNormalNoteEditFragment
    public void insertTitle(String text) {
        WebRichEditor webRichEditor = this.mEditor;
        Intrinsics.checkNotNull(webRichEditor);
        String str = webRichEditor.getTitle() + text;
        WebRichEditor webRichEditor2 = this.mEditor;
        Intrinsics.checkNotNull(webRichEditor2);
        webRichEditor2.setTitle(str);
    }

    public final void updateDoodle(String fileName, String oldField) {
        if (Intrinsics.areEqual(oldField, getOriginalFileId(this.mFormattedDoodleFileId))) {
            WebRichEditor webRichEditor = this.mEditor;
            Intrinsics.checkNotNull(webRichEditor);
            webRichEditor.editImageEnd(fileName, this.mFormattedDoodleFileId);
        } else {
            WebRichEditor webRichEditor2 = this.mEditor;
            Intrinsics.checkNotNull(webRichEditor2);
            webRichEditor2.editImageEnd(fileName, oldField);
        }
    }

    public final String getOriginalFileId(String formattedId) {
        List listEmptyList;
        if (formattedId == null) {
            return "";
        }
        List<String> listSplit = new Regex("--").split(formattedId, 0);
        if (!listSplit.isEmpty()) {
            ListIterator<String> listIterator = listSplit.listIterator(listSplit.size());
            while (true) {
                if (listIterator.hasPrevious()) {
                    if (listIterator.previous().length() != 0) {
                        listEmptyList = CollectionsKt.take(listSplit, listIterator.nextIndex() + 1);
                        break;
                    }
                } else {
                    listEmptyList = CollectionsKt.emptyList();
                    break;
                }
            }
        } else {
            listEmptyList = CollectionsKt.emptyList();
            break;
        }
        String[] strArr = (String[]) listEmptyList.toArray(new String[0]);
        if (strArr.length == 0) {
            return "";
        }
        return strArr[0];
    }

    @Override // com.miui.notes.basefeature.noteedit.BaseNormalNoteEditFragment
    public void startInsertDoodle(String fileName) {
        insertDoodle(fileName);
    }

    public final void insertDoodle(String fileName) {
        WebRichEditor webRichEditor = this.mEditor;
        Intrinsics.checkNotNull(webRichEditor);
        webRichEditor.insertImageEnd(new String[]{fileName}, false);
    }

    protected final WorkingNote handleNewNotes(Intent intent) {
        Logger logger = Logger.INSTANCE;
        String simpleName = getClass().getSimpleName();
        Intrinsics.checkNotNullExpressionValue(simpleName, "getSimpleName(...)");
        logger.d(simpleName, "handleNewNotes ,with intent " + intent);
        if (intent == null) {
            return WorkingNote.INSTANCE.createEmptyNote(0L, 0, -1, ResourceParser.getDefaultBgId(getActivity()));
        }
        this.mIsEditNotes = true;
        this.mIsFromPrivate = PrivateNotesListActivity.ACTION_FROM_PRIVACY;
        PrivateNotesListActivity.ACTION_FROM_PRIVACY = false;
        this.mIsFromFloatWindow = false;
        return WorkingNote.INSTANCE.createEmptyNote(intent.getLongExtra("com.miui.notes.folder_id", 0L), intent.getIntExtra("com.miui.notes.widget_id", 0), intent.getIntExtra("com.miui.notes.widget_type", -1), intent.getIntExtra(Notes.Intents.INTENT_EXTRA_BACKGROUND_ID, ResourceParser.getDefaultBgId(getActivity())));
    }

    public void onPagePrepared() {
        try {
            Trace.beginSection("EditActivity/onPagePrepared");
            Logger.INSTANCE.d(TAG, "onPagePrepared mPagePrepare: " + this.mPagePrepare);
            this.mPagePrepare = true;
            WebRichEditor webRichEditor = this.mEditor;
            if (webRichEditor != null) {
                webRichEditor.onFrontendPagePrepared();
            }
            this.mHandler.post(new Runnable() { // from class: com.miui.notes.basefeature.noteedit.BaseHybridNoteEditFragment$$ExternalSyntheticLambda62
                @Override // java.lang.Runnable
                public final void run() {
                    BaseHybridNoteEditFragment.onPagePrepared$lambda$20(this.f$0);
                }
            });
            RichEditorWebView richEditorWebView = this.mEditorWebView;
            Intrinsics.checkNotNull(richEditorWebView);
            richEditorWebView.setConsumeTouch(false);
        } finally {
            Trace.endSection();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Code duplicated, block: B:9:0x002d A[Catch: all -> 0x0039, TRY_LEAVE, TryCatch #0 {all -> 0x0039, blocks: (B:2:0x0000, B:4:0x0009, B:6:0x0012, B:8:0x0016, B:9:0x002d), top: B:15:0x0000 }] */
    public static final void onPagePrepared$lambda$20(BaseHybridNoteEditFragment baseHybridNoteEditFragment) {
        try {
            Trace.beginSection("EditActivity/onPagePrepared-task-run");
            WorkingNote workingNote = baseHybridNoteEditFragment.mWorkingNoteFromRestore;
            if (workingNote != null) {
                Intrinsics.checkNotNull(workingNote);
                if (!workingNote.isEmpty() || Utils.isDoodleOnWin) {
                    Logger.INSTANCE.d(TAG, "onPagePrepared,setWorkingNoteFromRestore");
                    WorkingNote workingNote2 = baseHybridNoteEditFragment.mWorkingNoteFromRestore;
                    Intrinsics.checkNotNull(workingNote2);
                    baseHybridNoteEditFragment.setWorkingNote(workingNote2, false);
                    baseHybridNoteEditFragment.mWorkingNoteFromRestore = null;
                } else {
                    baseHybridNoteEditFragment.handleIntentData(baseHybridNoteEditFragment.mPendingHandleIntent);
                    baseHybridNoteEditFragment.onChangeFontStyle();
                }
            } else {
                baseHybridNoteEditFragment.handleIntentData(baseHybridNoteEditFragment.mPendingHandleIntent);
                baseHybridNoteEditFragment.onChangeFontStyle();
            }
        } finally {
            Trace.endSection();
        }
    }

    public final void saveDoodle() {
        String content;
        if (!TextUtils.isEmpty(this.saveNewCachePath)) {
            startInsertDoodle(AttachmentUtils.saveImageFile(BaseNoteApp.INSTANCE.getInstance(), new File(this.saveNewCachePath)));
        }
        this.saveNewCachePath = null;
        if (RomUtils.isPadDevice() || TextUtils.isEmpty(this.mDoodleFileId)) {
            return;
        }
        if (!TextUtils.isEmpty(this.mNewImageName)) {
            String strSaveImageFile = AttachmentUtils.saveImageFile(BaseNoteApp.INSTANCE.getInstance(), new File(this.mNewImageName));
            if (this.mWorkingNote != null && strSaveImageFile != null) {
                WorkingNote workingNote = this.mWorkingNote;
                Intrinsics.checkNotNull(workingNote);
                if (workingNote.getContent() == null) {
                    content = "";
                } else {
                    WorkingNote workingNote2 = this.mWorkingNote;
                    Intrinsics.checkNotNull(workingNote2);
                    content = workingNote2.getContent();
                }
                String str = content;
                WorkingNote workingNote3 = this.mWorkingNote;
                Intrinsics.checkNotNull(workingNote3);
                String title = workingNote3.getTitle();
                Intrinsics.checkNotNull(str);
                String str2 = this.mDoodleFileId;
                Intrinsics.checkNotNull(str2);
                String strReplace$default = StringsKt.replace$default(str, str2, strSaveImageFile, false, 4, (Object) null);
                this.mNoteRichEditor.setData(strReplace$default, title, this.mSearchText, Boolean.valueOf(this.mIsNeedSetData), this.mHighLightKeyWords);
                WorkingNote workingNote4 = this.mWorkingNote;
                Intrinsics.checkNotNull(workingNote4);
                workingNote4.setWorkingText(strReplace$default);
                WorkingNote workingNote5 = this.mWorkingNote;
                Intrinsics.checkNotNull(workingNote5);
                workingNote5.saveNote();
            }
            this.mNewImageName = null;
        } else {
            updateDoodle(null, this.mDoodleFileId);
        }
        this.mDoodleFileId = null;
    }

    @Override // com.miui.notes.basefeature.noteedit.BaseNormalNoteEditFragment
    public boolean saveNote(boolean force) {
        String richTextData;
        Logger.INSTANCE.d(TAG, "saveNote " + force + ",mWorkingNote:,editor:" + this.mNoteRichEditor);
        if (this.mNoteRichEditor == null || this.mWorkingNote == null) {
            return false;
        }
        if (force) {
            INoteRichEditor iNoteRichEditor = this.mNoteRichEditor;
            if (iNoteRichEditor == null || (richTextData = iNoteRichEditor.getRichTextData()) == null) {
            }
            String string = richTextData.toString();
            if (StringsKt.contains$default((CharSequence) string, (CharSequence) AudioUtils.TEMP_AUDIO_FLAG, false, 2, (Object) null) && !isSupportAIRecorder()) {
                string = StringsKt.replace$default(string, AudioUtils.TEMP_AUDIO_FLAG, StringUtils.SPACE, false, 4, (Object) null);
            }
            Logger.INSTANCE.i(TAG, "saveNote,with text length:" + richTextData.length());
            if (this.mWorkingNote != null) {
                WorkingNote workingNote = this.mWorkingNote;
                Intrinsics.checkNotNull(workingNote);
                workingNote.setWorkingText(string);
            }
        } else {
            Logger.INSTANCE.i(TAG, "saveNote force:false " + Thread.currentThread().getName());
            updateWorkingTextAndTitle();
        }
        WorkingNote workingNote2 = this.mWorkingNote;
        Intrinsics.checkNotNull(workingNote2);
        long noteId = workingNote2.getNoteId();
        WorkingNote workingNote3 = this.mWorkingNote;
        Intrinsics.checkNotNull(workingNote3);
        boolean zSaveNote = workingNote3.saveNote(force);
        if (noteId <= 0 && zSaveNote && this.mWorkingNote != null) {
            INoteRichEditor iNoteRichEditor2 = this.mNoteRichEditor;
            WorkingNote workingNote4 = this.mWorkingNote;
            Intrinsics.checkNotNull(workingNote4);
            iNoteRichEditor2.setDataId(workingNote4.getNoteId());
            INoteRichEditor iNoteRichEditor3 = this.mNoteRichEditor;
            WorkingNote workingNote5 = this.mWorkingNote;
            Intrinsics.checkNotNull(workingNote5);
            iNoteRichEditor3.setFolderId(workingNote5.getFolderId());
            if (isAdded()) {
                requireActivity().setResult(-1);
            }
            registerNoteContentObserver(true);
        }
        return zSaveNote;
    }

    public boolean isDarkMode() {
        return DisplayUtils.isNightMode();
    }

    public int getDeviceFlag() {
        if (RomUtils.isPadDevice()) {
            return 1;
        }
        return (!RomUtils.isFoldDevice() || UIUtils.isInNarrowScreen(getContext())) ? 0 : 1;
    }

    public final String getStringFromResource(String key) {
        Intrinsics.checkNotNullParameter(key, "key");
        String string = getResources().getString(Utils.INSTANCE.getResId(key, R.string.class));
        Intrinsics.checkNotNullExpressionValue(string, "getString(...)");
        return string;
    }

    public final void showToast(String key) {
        Intrinsics.checkNotNullParameter(key, "key");
        Toast.makeText(BaseNoteApp.INSTANCE.getInstance(), getStringFromResource(key), 1).show();
    }

    public final void onFlowDone() {
        NoteAiFloatingWindow noteAiFloatingWindow = mNoteAiFloatingWindow;
        if (noteAiFloatingWindow != null) {
            Intrinsics.checkNotNull(noteAiFloatingWindow);
            noteAiFloatingWindow.flowDown();
        }
    }

    public void onStopDone() {
        Logger.INSTANCE.i(TAG, "onStopDone mNoteAiFloatingWindow:" + mNoteAiFloatingWindow);
        NoteAiFloatingWindow noteAiFloatingWindow = mNoteAiFloatingWindow;
        if (noteAiFloatingWindow != null) {
            Intrinsics.checkNotNull(noteAiFloatingWindow);
            if (noteAiFloatingWindow.getIsDeleteLabel()) {
                this.mHandler.post(new Runnable() { // from class: com.miui.notes.basefeature.noteedit.BaseHybridNoteEditFragment$$ExternalSyntheticLambda52
                    @Override // java.lang.Runnable
                    public final void run() {
                        BaseHybridNoteEditFragment.onStopDone$lambda$21(this.f$0);
                    }
                });
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void onStopDone$lambda$21(BaseHybridNoteEditFragment baseHybridNoteEditFragment) {
        if (Intrinsics.areEqual(baseHybridNoteEditFragment.mAiData, baseHybridNoteEditFragment.mOriginalContent)) {
            return;
        }
        Logger.INSTANCE.i(TAG, "onStopDone queuedUndo2BeforeSnapshot");
        WebRichEditor webRichEditor = baseHybridNoteEditFragment.mEditor;
        Intrinsics.checkNotNull(webRichEditor);
        webRichEditor.queuedUndo2BeforeSnapshot();
        baseHybridNoteEditFragment.mAiData = baseHybridNoteEditFragment.mOriginalContent;
    }

    public final void onFlowDone(String data, String data2) {
        if (mNoteAiFloatingWindow != null) {
            this.mAiDoneData = data;
            this.mAiDoneData2 = data2;
            if (Intrinsics.areEqual(data, this.mOriginalContent)) {
                return;
            }
            NoteAiFloatingWindow noteAiFloatingWindow = mNoteAiFloatingWindow;
            Intrinsics.checkNotNull(noteAiFloatingWindow);
            noteAiFloatingWindow.flowDown();
        }
    }

    public final void onFlowDone(String data) {
        if (mNoteAiFloatingWindow != null) {
            this.mAiDoneData = data;
            this.mAiDoneData2 = null;
            if (Intrinsics.areEqual(data, this.mOriginalContent)) {
                return;
            }
            NoteAiFloatingWindow noteAiFloatingWindow = mNoteAiFloatingWindow;
            Intrinsics.checkNotNull(noteAiFloatingWindow);
            noteAiFloatingWindow.flowDown();
        }
    }

    public final void notifyRefreshComplete() {
        NoteAiFloatingWindow noteAiFloatingWindow = mNoteAiFloatingWindow;
        if (noteAiFloatingWindow != null) {
            Intrinsics.checkNotNull(noteAiFloatingWindow);
            noteAiFloatingWindow.notifyRefreshComplete();
        }
    }

    public final void notifySetNewContent() {
        this.mHandler.post(new Runnable() { // from class: com.miui.notes.basefeature.noteedit.BaseHybridNoteEditFragment$$ExternalSyntheticLambda59
            @Override // java.lang.Runnable
            public final void run() {
                BaseHybridNoteEditFragment.notifySetNewContent$lambda$22(this.f$0);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void notifySetNewContent$lambda$22(BaseHybridNoteEditFragment baseHybridNoteEditFragment) {
        NoteAiFloatingWindow noteAiFloatingWindow;
        Logger.INSTANCE.i(TAG, "notifySetNewContent, mAiDoneData isEmpty:" + TextUtils.isEmpty(baseHybridNoteEditFragment.mAiDoneData));
        if (baseHybridNoteEditFragment.mAiDoneData == null || (noteAiFloatingWindow = mNoteAiFloatingWindow) == null) {
            return;
        }
        Intrinsics.checkNotNull(noteAiFloatingWindow);
        String aiTitle = noteAiFloatingWindow.getAiTitle();
        Logger.INSTANCE.i(TAG, "notifySetNewContent, mAiDoneData isEmpty:" + TextUtils.isEmpty(baseHybridNoteEditFragment.mAiDoneData));
        if (TextUtils.isEmpty(baseHybridNoteEditFragment.mOriginalContent) && TextUtils.isEmpty(baseHybridNoteEditFragment.mOriginalTitle) && !TextUtils.isEmpty(aiTitle)) {
            String str = baseHybridNoteEditFragment.mAiDoneData;
            Intrinsics.checkNotNull(str);
            baseHybridNoteEditFragment.setDataWithConfig(str, aiTitle);
            WebRichEditor webRichEditor = baseHybridNoteEditFragment.mEditor;
            Intrinsics.checkNotNull(webRichEditor);
            webRichEditor.setTitle(aiTitle);
        } else {
            String str2 = baseHybridNoteEditFragment.mAiDoneData;
            Intrinsics.checkNotNull(str2);
            baseHybridNoteEditFragment.setDataWithConfig(str2, baseHybridNoteEditFragment.mOriginalTitle);
        }
        if (baseHybridNoteEditFragment.mAiDoneData2 == null) {
            WebRichEditor webRichEditor2 = baseHybridNoteEditFragment.mEditor;
            Intrinsics.checkNotNull(webRichEditor2);
            webRichEditor2.refreshNewContent();
        } else {
            WebRichEditor webRichEditor3 = baseHybridNoteEditFragment.mEditor;
            Intrinsics.checkNotNull(webRichEditor3);
            webRichEditor3.refreshNewContent(baseHybridNoteEditFragment.mAiDoneData2);
        }
    }

    public final void onCorrectNumChanged(final int changeCount) {
        if (mNoteAiFloatingWindow == null || this.mHandler == null) {
            return;
        }
        this.mHandler.post(new Runnable() { // from class: com.miui.notes.basefeature.noteedit.BaseHybridNoteEditFragment.onCorrectNumChanged.1
            @Override // java.lang.Runnable
            public void run() {
                NoteAiFloatingWindow noteAiFloatingWindow = BaseHybridNoteEditFragment.mNoteAiFloatingWindow;
                Intrinsics.checkNotNull(noteAiFloatingWindow);
                noteAiFloatingWindow.onCorrectNumChanged(changeCount);
            }
        });
    }

    public void onRefreshDone() {
        Logger.INSTANCE.i(TAG, "onRefreshDone in fragment");
        this.mInRefreshAnim = false;
    }

    @Override // com.miui.notes.basefeature.noteedit.BaseNormalNoteEditFragment
    protected boolean dismissAllDialogOrPanel() {
        return dismissAiView() || super.dismissAllDialogOrPanel();
    }

    public final boolean dismissAiView() {
        BottomSheetView bottomSheetView = this.mAiBottomSheetView;
        if (bottomSheetView == null) {
            return false;
        }
        Intrinsics.checkNotNull(bottomSheetView);
        if (!bottomSheetView.isShowing()) {
            return false;
        }
        BottomSheetView bottomSheetView2 = this.mAiBottomSheetView;
        Intrinsics.checkNotNull(bottomSheetView2);
        bottomSheetView2.dismiss();
        return true;
    }

    @Override // com.miui.notes.basefeature.noteedit.BaseNormalNoteEditFragment, com.miui.notes.detail.BaseNoteDetailFragment
    public boolean onBackPressed() {
        if (dismissAllDialogOrPanel()) {
            return true;
        }
        this.mIsKillSaveTaskBeforeDestroy = true;
        killSaveTask();
        return false;
    }

    public void onTextCountChanged(final int textCount) {
        if (getActivity() != null) {
            this.mHandler.post(new Runnable() { // from class: com.miui.notes.basefeature.noteedit.BaseHybridNoteEditFragment$$ExternalSyntheticLambda29
                @Override // java.lang.Runnable
                public final void run() {
                    BaseHybridNoteEditFragment.onTextCountChanged$lambda$23(this.f$0, textCount);
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void onTextCountChanged$lambda$23(BaseHybridNoteEditFragment baseHybridNoteEditFragment, int i) {
        if (baseHybridNoteEditFragment.mWorkingNote != null) {
            WorkingNote workingNote = baseHybridNoteEditFragment.mWorkingNote;
            Intrinsics.checkNotNull(workingNote);
            workingNote.getExtraInfo().textCount = i;
            WebRichEditor webRichEditor = baseHybridNoteEditFragment.mEditor;
            Intrinsics.checkNotNull(webRichEditor);
            WorkingNote workingNote2 = baseHybridNoteEditFragment.mWorkingNote;
            Intrinsics.checkNotNull(workingNote2);
            webRichEditor.updateExtraInto(workingNote2.getExtraInfo());
            if (baseHybridNoteEditFragment.getActionBarController() != null) {
                baseHybridNoteEditFragment.getActionBarController().updateTextCount(i);
            }
            WorkingNote workingNote3 = baseHybridNoteEditFragment.mWorkingNote;
            Intrinsics.checkNotNull(workingNote3);
            workingNote3.setTextCount(i);
            WebRichEditor webRichEditor2 = baseHybridNoteEditFragment.mEditor;
            Intrinsics.checkNotNull(webRichEditor2);
            webRichEditor2.setCount(i);
            NoteAiFloatingWindow noteAiFloatingWindow = mNoteAiFloatingWindow;
            if (noteAiFloatingWindow != null) {
                Intrinsics.checkNotNull(noteAiFloatingWindow);
                noteAiFloatingWindow.setTextCount(i);
            }
        }
    }

    public final long getTextCount() {
        if (this.mWorkingNote == null) {
            return 0L;
        }
        WorkingNote workingNote = this.mWorkingNote;
        Intrinsics.checkNotNull(workingNote);
        return workingNote.getExtraInfo().textCount;
    }

    public final void onStateChanged(final int state) {
        Logger.INSTANCE.d(TAG, "onStateChanged " + state + ",act:" + getActivity());
        this.mHandler.post(new Runnable() { // from class: com.miui.notes.basefeature.noteedit.BaseHybridNoteEditFragment$$ExternalSyntheticLambda41
            @Override // java.lang.Runnable
            public final void run() {
                BaseHybridNoteEditFragment.onStateChanged$lambda$24(state, this);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void onStateChanged$lambda$24(int i, BaseHybridNoteEditFragment baseHybridNoteEditFragment) {
        if (i != 0 && baseHybridNoteEditFragment.homeViewModel != null) {
            HomeViewModel homeViewModel = baseHybridNoteEditFragment.homeViewModel;
            Intrinsics.checkNotNull(homeViewModel);
            Integer value = homeViewModel.getNoteMultiModeLiveData().getValue();
            if (value != null && value.intValue() == 1) {
                Logger.INSTANCE.i(TAG, "in list action mode");
                WebRichEditor webRichEditor = baseHybridNoteEditFragment.mEditor;
                Intrinsics.checkNotNull(webRichEditor);
                webRichEditor.done();
                return;
            }
        }
        Logger.INSTANCE.d(TAG, "onStateChanged " + i + " mSplitMode:" + baseHybridNoteEditFragment.mSplitMode);
        IN_TITLE = i == 1;
        WebRichEditor webRichEditor2 = baseHybridNoteEditFragment.mEditor;
        Intrinsics.checkNotNull(webRichEditor2);
        webRichEditor2.setIsInTitle(i == 1);
        WebRichEditor webRichEditor3 = baseHybridNoteEditFragment.mEditor;
        Intrinsics.checkNotNull(webRichEditor3);
        if (webRichEditor3.getIsInDragMode() && i != 0) {
            Logger.INSTANCE.d(TAG, "in drag mode, can't edit");
            return;
        }
        if (baseHybridNoteEditFragment.mSplitMode == 1) {
            baseHybridNoteEditFragment.updateActionMode(EditorActionMode.VIEW, false);
            return;
        }
        if (i == 0) {
            Logger.INSTANCE.d(TAG, "case mode view");
            baseHybridNoteEditFragment.updateActionMode(EditorActionMode.VIEW, true);
            return;
        }
        if (i == 1) {
            Logger.INSTANCE.d(TAG, "case mode title");
            if (baseHybridNoteEditFragment.mEnableEdit) {
                baseHybridNoteEditFragment.updateActionMode(EditorActionMode.EDIT_TITLE, true);
                return;
            }
            return;
        }
        if (i == 2) {
            Logger.INSTANCE.d(TAG, "case mode edit");
            if (!baseHybridNoteEditFragment.mEnableEdit || baseHybridNoteEditFragment.mIsRecording || Utils.isCtaOnWin) {
                return;
            }
            baseHybridNoteEditFragment.updateActionMode(EditorActionMode.EDIT, true);
            return;
        }
        if (i == 3) {
            Logger.INSTANCE.d(TAG, "case mode edit-richtxt");
            if (baseHybridNoteEditFragment.mEnableEdit) {
                baseHybridNoteEditFragment.updateActionMode(EditorActionMode.EDIT, true, true);
                return;
            }
            return;
        }
        if (i != 4) {
            return;
        }
        Logger.INSTANCE.d(TAG, "case mode edit-record");
        if (baseHybridNoteEditFragment.mEnableEdit) {
            baseHybridNoteEditFragment.updateActionMode(EditorActionMode.EDIT_RECODE, true, true);
        }
    }

    public final void onRichTextPanelStateChanged(final boolean isOpen, final boolean withAnim) {
        this.mHandler.postDelayed(new Runnable() { // from class: com.miui.notes.basefeature.noteedit.BaseHybridNoteEditFragment$$ExternalSyntheticLambda32
            @Override // java.lang.Runnable
            public final void run() {
                BaseHybridNoteEditFragment.onRichTextPanelStateChanged$lambda$25(this.f$0, isOpen, withAnim);
            }
        }, 300L);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void onRichTextPanelStateChanged$lambda$25(BaseHybridNoteEditFragment baseHybridNoteEditFragment, boolean z, boolean z2) {
        if (baseHybridNoteEditFragment.mEditToolbarController != null) {
            baseHybridNoteEditFragment.mEditToolbarController.setTextStylePanelVisible(z, z2);
        }
    }

    public void onLinkElementClicked(final EmbedElementInfoBean elementInfoBean) {
        Intrinsics.checkNotNullParameter(elementInfoBean, "elementInfoBean");
        this.mHandler.post(new Runnable() { // from class: com.miui.notes.basefeature.noteedit.BaseHybridNoteEditFragment$$ExternalSyntheticLambda21
            @Override // java.lang.Runnable
            public final void run() {
                BaseHybridNoteEditFragment.onLinkElementClicked$lambda$29(this.f$0, elementInfoBean);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void onLinkElementClicked$lambda$29(final BaseHybridNoteEditFragment baseHybridNoteEditFragment, final EmbedElementInfoBean embedElementInfoBean) {
        Context context;
        if (baseHybridNoteEditFragment.isAdded() && (context = baseHybridNoteEditFragment.getContext()) != null) {
            RichEditorWebView richEditorWebView = baseHybridNoteEditFragment.mEditorWebView;
            if (richEditorWebView != null) {
                Intrinsics.checkNotNull(richEditorWebView);
                richEditorWebView.stopActionMode();
            }
            if (baseHybridNoteEditFragment.mShowingPopupWindow != null) {
                if (baseHybridNoteEditFragment.mShowingPopupWindow.isShowing()) {
                    baseHybridNoteEditFragment.mShowingPopupWindow.dismiss();
                }
                baseHybridNoteEditFragment.mShowingPopupWindow = null;
                WebRichEditor webRichEditor = baseHybridNoteEditFragment.mEditor;
                Intrinsics.checkNotNull(webRichEditor);
                webRichEditor.hidePopMenu();
            }
            final RichEditorWebView richEditorWebView2 = baseHybridNoteEditFragment.mEditorWebView;
            Rect rect = new Rect();
            Intrinsics.checkNotNull(richEditorWebView2);
            richEditorWebView2.getGlobalVisibleRect(rect);
            Rect rect2 = new Rect(embedElementInfoBean.eLeft, embedElementInfoBean.eTop, embedElementInfoBean.eRight, embedElementInfoBean.eBottom);
            rect2.offset(rect.left, rect.top);
            final RichTextFloatMenuInfo richTextFloatMenuInfoCreateLinkCardMenu = RichTextFloatMenuHelper.createLinkCardMenu(context, rect2, rect, new View.OnClickListener() { // from class: com.miui.notes.basefeature.noteedit.BaseHybridNoteEditFragment$$ExternalSyntheticLambda65
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    BaseHybridNoteEditFragment.onLinkElementClicked$lambda$29$lambda$26(this.f$0, embedElementInfoBean, view);
                }
            });
            baseHybridNoteEditFragment.mShowingPopupWindow = richTextFloatMenuInfoCreateLinkCardMenu.menu;
            baseHybridNoteEditFragment.mShowingPopupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() { // from class: com.miui.notes.basefeature.noteedit.BaseHybridNoteEditFragment$$ExternalSyntheticLambda66
                @Override // android.widget.PopupWindow.OnDismissListener
                public final void onDismiss() {
                    BaseHybridNoteEditFragment.onLinkElementClicked$lambda$29$lambda$27(this.f$0);
                }
            });
            baseHybridNoteEditFragment.mHandler.post(new Runnable() { // from class: com.miui.notes.basefeature.noteedit.BaseHybridNoteEditFragment$$ExternalSyntheticLambda67
                @Override // java.lang.Runnable
                public final void run() {
                    BaseHybridNoteEditFragment.onLinkElementClicked$lambda$29$lambda$28(this.f$0, richEditorWebView2, richTextFloatMenuInfoCreateLinkCardMenu);
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void onLinkElementClicked$lambda$29$lambda$26(BaseHybridNoteEditFragment baseHybridNoteEditFragment, EmbedElementInfoBean embedElementInfoBean, View view) {
        Intrinsics.checkNotNull(view);
        int id = view.getId();
        if (id != R.id.delete_image) {
            if (id != R.id.view_link) {
                return;
            }
            baseHybridNoteEditFragment.viewLinkByBrowser(embedElementInfoBean);
        } else {
            WebRichEditor webRichEditor = baseHybridNoteEditFragment.mEditor;
            Intrinsics.checkNotNull(webRichEditor);
            webRichEditor.deleteEmbedElement();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void onLinkElementClicked$lambda$29$lambda$27(BaseHybridNoteEditFragment baseHybridNoteEditFragment) {
        baseHybridNoteEditFragment.mShowingPopupWindow = null;
        WebRichEditor webRichEditor = baseHybridNoteEditFragment.mEditor;
        Intrinsics.checkNotNull(webRichEditor);
        webRichEditor.hidePopMenu();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void onLinkElementClicked$lambda$29$lambda$28(BaseHybridNoteEditFragment baseHybridNoteEditFragment, View view, RichTextFloatMenuInfo richTextFloatMenuInfo) {
        if (!baseHybridNoteEditFragment.isAdded() || baseHybridNoteEditFragment.mShowingPopupWindow == null) {
            return;
        }
        baseHybridNoteEditFragment.mShowingPopupWindow.showAtLocation(view, richTextFloatMenuInfo.gravity, richTextFloatMenuInfo.showX, richTextFloatMenuInfo.showY);
        view.requestFocus();
        baseHybridNoteEditFragment.mClickCount = 0;
    }

    public void onImageElementClicked(final EmbedElementInfoBean elementInfoBean) {
        Intrinsics.checkNotNullParameter(elementInfoBean, "elementInfoBean");
        this.mHandler.post(new Runnable() { // from class: com.miui.notes.basefeature.noteedit.BaseHybridNoteEditFragment$$ExternalSyntheticLambda20
            @Override // java.lang.Runnable
            public final void run() {
                BaseHybridNoteEditFragment.onImageElementClicked$lambda$32(this.f$0, elementInfoBean);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void onImageElementClicked$lambda$32(final BaseHybridNoteEditFragment baseHybridNoteEditFragment, final EmbedElementInfoBean embedElementInfoBean) {
        if (baseHybridNoteEditFragment.isAdded() && baseHybridNoteEditFragment.mEnableEdit) {
            RichEditorWebView richEditorWebView = baseHybridNoteEditFragment.mEditorWebView;
            if (richEditorWebView != null) {
                Intrinsics.checkNotNull(richEditorWebView);
                richEditorWebView.stopActionMode();
            }
            Context context = baseHybridNoteEditFragment.getContext();
            String str = embedElementInfoBean.fileName;
            baseHybridNoteEditFragment.mFormattedDoodleFileId = str;
            embedElementInfoBean.fileName = baseHybridNoteEditFragment.getOriginalFileId(str);
            Context contextRequireContext = baseHybridNoteEditFragment.requireContext();
            Intrinsics.checkNotNullExpressionValue(contextRequireContext, "requireContext(...)");
            String attachmentPath = AttachmentUtils.getAttachmentPath(contextRequireContext, embedElementInfoBean.fileName);
            if (attachmentPath == null || !new File(attachmentPath).exists() || context == null) {
                return;
            }
            if (baseHybridNoteEditFragment.mShowingPopupWindow != null) {
                if (baseHybridNoteEditFragment.mShowingPopupWindow.isShowing()) {
                    baseHybridNoteEditFragment.mShowingPopupWindow.dismiss();
                }
                baseHybridNoteEditFragment.mShowingPopupWindow = null;
                WebRichEditor webRichEditor = baseHybridNoteEditFragment.mEditor;
                Intrinsics.checkNotNull(webRichEditor);
                webRichEditor.hidePopMenu();
                return;
            }
            if (DocumentReader.INSTANCE.isDoodleFile(attachmentPath)) {
                Context contextRequireContext2 = baseHybridNoteEditFragment.requireContext();
                Intrinsics.checkNotNullExpressionValue(contextRequireContext2, "requireContext(...)");
                DoodleApplication.preloadData(attachmentPath, contextRequireContext2);
            }
            final RichEditorWebView richEditorWebView2 = baseHybridNoteEditFragment.mEditorWebView;
            Rect rect = new Rect();
            Intrinsics.checkNotNull(richEditorWebView2);
            richEditorWebView2.getGlobalVisibleRect(rect);
            Rect rect2 = new Rect(embedElementInfoBean.eLeft, embedElementInfoBean.eTop, embedElementInfoBean.eRight, embedElementInfoBean.eBottom);
            rect2.offset(rect.left, rect.top);
            final RichTextFloatMenuInfo richTextFloatMenuInfoCreateImageActionMenu = RichTextFloatMenuHelper.createImageActionMenu(context, rect2, rect, embedElementInfoBean.imgSizeLevel != 0, new View.OnClickListener() { // from class: com.miui.notes.basefeature.noteedit.BaseHybridNoteEditFragment$$ExternalSyntheticLambda1
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    BaseHybridNoteEditFragment.onImageElementClicked$lambda$32$lambda$30(this.f$0, embedElementInfoBean, view);
                }
            });
            richTextFloatMenuInfoCreateImageActionMenu.menu.setOnDismissListener(new PopupWindow.OnDismissListener() { // from class: com.miui.notes.basefeature.noteedit.BaseHybridNoteEditFragment$$ExternalSyntheticLambda2
                @Override // android.widget.PopupWindow.OnDismissListener
                public final void onDismiss() {
                    BaseHybridNoteEditFragment.onImageElementClicked$lambda$32$lambda$31(this.f$0);
                }
            });
            baseHybridNoteEditFragment.mShowingPopupWindow = richTextFloatMenuInfoCreateImageActionMenu.menu;
            richEditorWebView2.post(new Runnable() { // from class: com.miui.notes.basefeature.noteedit.BaseHybridNoteEditFragment$onImageElementClicked$1$2
                @Override // java.lang.Runnable
                public void run() {
                    if (!this.this$0.isAdded() || this.this$0.mShowingPopupWindow == null) {
                        return;
                    }
                    this.this$0.mShowingPopupWindow.showAtLocation(richEditorWebView2, richTextFloatMenuInfoCreateImageActionMenu.gravity, richTextFloatMenuInfoCreateImageActionMenu.showX, richTextFloatMenuInfoCreateImageActionMenu.showY);
                    richEditorWebView2.requestFocus();
                    this.this$0.mClickCount = 0;
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void onImageElementClicked$lambda$32$lambda$30(BaseHybridNoteEditFragment baseHybridNoteEditFragment, EmbedElementInfoBean embedElementInfoBean, View view) {
        switch (view.getId()) {
            case R.id.annotate_image /* 2131362007 */:
                WebRichEditor webRichEditor = baseHybridNoteEditFragment.mEditor;
                Intrinsics.checkNotNull(webRichEditor);
                webRichEditor.annotateImageStart();
                break;
            case R.id.copy_image /* 2131362247 */:
                NoteClipManager noteClipManager = NoteClipManager.INSTANCE;
                Context contextRequireContext = baseHybridNoteEditFragment.requireContext();
                Intrinsics.checkNotNullExpressionValue(contextRequireContext, "requireContext(...)");
                noteClipManager.copyImage(embedElementInfoBean, contextRequireContext);
                break;
            case R.id.delete_image /* 2131362399 */:
                WebRichEditor webRichEditor2 = baseHybridNoteEditFragment.mEditor;
                Intrinsics.checkNotNull(webRichEditor2);
                webRichEditor2.deleteEmbedElement();
                break;
            case R.id.edit_image /* 2131362464 */:
                baseHybridNoteEditFragment.editImage(embedElementInfoBean);
                break;
            case R.id.scale_image /* 2131363347 */:
                WebRichEditor webRichEditor3 = baseHybridNoteEditFragment.mEditor;
                Intrinsics.checkNotNull(webRichEditor3);
                webRichEditor3.changeShowBigImg();
                break;
            case R.id.view_image /* 2131363771 */:
                baseHybridNoteEditFragment.viewImage(embedElementInfoBean);
                break;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void onImageElementClicked$lambda$32$lambda$31(BaseHybridNoteEditFragment baseHybridNoteEditFragment) {
        baseHybridNoteEditFragment.mShowingPopupWindow = null;
        WebRichEditor webRichEditor = baseHybridNoteEditFragment.mEditor;
        Intrinsics.checkNotNull(webRichEditor);
        webRichEditor.hidePopMenu();
    }

    protected final void viewImage(EmbedElementInfoBean elementInfoBean) {
        Intrinsics.checkNotNullParameter(elementInfoBean, "elementInfoBean");
        viewImage(elementInfoBean.fileName);
    }

    protected final void viewLinkByBrowser(EmbedElementInfoBean elementInfoBean) {
        Intrinsics.checkNotNullParameter(elementInfoBean, "elementInfoBean");
        viewLinkByBrowser(elementInfoBean.url);
    }

    public final void hidePopMenu() {
        this.mHandler.post(new Runnable() { // from class: com.miui.notes.basefeature.noteedit.BaseHybridNoteEditFragment$$ExternalSyntheticLambda26
            @Override // java.lang.Runnable
            public final void run() {
                BaseHybridNoteEditFragment.hidePopMenu$lambda$33(this.f$0);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void hidePopMenu$lambda$33(BaseHybridNoteEditFragment baseHybridNoteEditFragment) {
        if (baseHybridNoteEditFragment.mShowingPopupWindow != null) {
            if (baseHybridNoteEditFragment.mShowingPopupWindow.isShowing()) {
                baseHybridNoteEditFragment.mShowingPopupWindow.dismiss();
            }
            baseHybridNoteEditFragment.mShowingPopupWindow = null;
            WebRichEditor webRichEditor = baseHybridNoteEditFragment.mEditor;
            Intrinsics.checkNotNull(webRichEditor);
            webRichEditor.hidePopMenu();
        }
    }

    public final void onShareImage(final String[] imgBase64) {
        Intrinsics.checkNotNullParameter(imgBase64, "imgBase64");
        Logger.INSTANCE.d(TAG, "onShareImage: ");
        if (isAdded()) {
            Intent intent = new Intent(FrameConstant.ACTION_NOTE_CONTENT_CREATED);
            intent.putStringArrayListExtra("image_uri", getUrisFromImages(imgBase64));
            final boolean[] zArr = {this.mLocalBroadcastManager.sendBroadcast(intent)};
            for (int i = 1; !zArr[0] && i < 4; i++) {
                this.mHandler.postDelayed(new Runnable() { // from class: com.miui.notes.basefeature.noteedit.BaseHybridNoteEditFragment$$ExternalSyntheticLambda36
                    @Override // java.lang.Runnable
                    public final void run() {
                        BaseHybridNoteEditFragment.onShareImage$lambda$34(this.f$0, imgBase64, zArr);
                    }
                }, 50L);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void onShareImage$lambda$34(BaseHybridNoteEditFragment baseHybridNoteEditFragment, String[] strArr, boolean[] zArr) {
        Intent intent = new Intent(FrameConstant.ACTION_NOTE_CONTENT_CREATED);
        intent.putStringArrayListExtra("image_uri", baseHybridNoteEditFragment.getUrisFromImages(strArr));
        zArr[0] = baseHybridNoteEditFragment.mLocalBroadcastManager.sendBroadcast(intent);
    }

    public final void onDisableScroll(final boolean disable) {
        if (isAdded()) {
            this.mHandler.post(new Runnable() { // from class: com.miui.notes.basefeature.noteedit.BaseHybridNoteEditFragment$$ExternalSyntheticLambda60
                @Override // java.lang.Runnable
                public final void run() {
                    BaseHybridNoteEditFragment.onDisableScroll$lambda$35(this.f$0, disable);
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void onDisableScroll$lambda$35(BaseHybridNoteEditFragment baseHybridNoteEditFragment, boolean z) {
        SpringBackLayout springBackLayout = baseHybridNoteEditFragment.mBgContentContainer;
        Intrinsics.checkNotNull(springBackLayout);
        springBackLayout.setNestedScrollingEnabled(!z);
        SpringBackLayout springBackLayout2 = baseHybridNoteEditFragment.mBgContentContainer;
        Intrinsics.checkNotNull(springBackLayout2);
        springBackLayout2.setSpringBackEnable(!z);
        RichEditorWebView richEditorWebView = baseHybridNoteEditFragment.mEditorWebView;
        Intrinsics.checkNotNull(richEditorWebView);
        richEditorWebView.setNestedScrollingEnabled(!z);
    }

    @Override // com.miui.notes.basefeature.noteedit.BaseNormalNoteEditFragment
    public void updateActionMode(EditorActionMode actionMode, boolean withAnim) {
        Logger.INSTANCE.d(TAG, "updateActionMode,with anim:" + withAnim + ",actionMode:" + actionMode);
        boolean z = false;
        updateActionMode(actionMode, false, withAnim);
        if (actionMode == EditorActionMode.VIEW && this.mIsCreated) {
            return;
        }
        if (actionMode != EditorActionMode.VIEW) {
            this.mIsCreated = false;
        }
        if (this.mOldActionMode == actionMode) {
            this.mIsReport = true;
        } else {
            this.mIsReport = false;
            this.mOldActionMode = actionMode;
        }
        if (this.mIsReport) {
            return;
        }
        NoteContentStat.IS_CLICKED = false;
        Intent intent = this.mPendingHandleIntent;
        if (actionMode != EditorActionMode.EDIT && actionMode != EditorActionMode.EDIT_TITLE) {
            z = true;
        }
        trackReportEditMode(intent, z);
    }

    protected void updateActionMode(EditorActionMode actionMode, boolean showRichTextTool, boolean withAnim) {
        Logger.INSTANCE.d(TAG, "updateActionMode(actionMode:" + actionMode + ",showRichTextTool:" + showRichTextTool + ",withAnim:" + withAnim + ")");
        this.mActionBarController.updateActionMode(actionMode, false, withAnim);
        if (this.homeViewModel != null) {
            HomeViewModel homeViewModel = this.homeViewModel;
            Intrinsics.checkNotNull(homeViewModel);
            homeViewModel.setEditStatus(actionMode == EditorActionMode.EDIT || actionMode == EditorActionMode.EDIT_TITLE);
        }
        if (this.mActionMode == actionMode) {
            if (this.mEditToolbarController != null && this.mActionMode == EditorActionMode.EDIT && !this.mEditToolbarController.ismToolbarIsShow()) {
                IBaseNoteEditToolbarController iBaseNoteEditToolbarController = this.mEditToolbarController;
                WebRichEditor webRichEditor = this.mEditor;
                Intrinsics.checkNotNull(webRichEditor);
                iBaseNoteEditToolbarController.onEditContentMode(true, showRichTextTool, !webRichEditor.getEditorTextIsEmpty());
            }
            Logger.INSTANCE.i(TAG, "updateActionMode not change ,return)");
            return;
        }
        if (actionMode == EditorActionMode.VIEW) {
            onViewMode();
            if (this.mActionMode == EditorActionMode.EDIT_RECODE) {
                recordToView();
            }
        } else if (actionMode == EditorActionMode.EDIT) {
            this.mNotSaveData = false;
            if (this.mEnableEdit) {
                onEditMode(showRichTextTool);
            }
        } else if (actionMode == EditorActionMode.EDIT_RECODE) {
            this.mNotSaveData = false;
            onAudioMode();
        } else if (actionMode == EditorActionMode.EDIT_TITLE) {
            this.mNotSaveData = false;
            onTitleMode();
        }
        this.mActionMode = actionMode;
    }

    protected void onViewMode() {
        WebRichEditor webRichEditor;
        Logger.INSTANCE.d(TAG, "onViewMode");
        if (!this.mInSearchMode && (webRichEditor = this.mEditor) != null) {
            Intrinsics.checkNotNull(webRichEditor);
            if (webRichEditor.getIsIsKeyboardVisible()) {
                Utils.hideSoftInput(this.mEditorWebView);
            }
        }
        if (this.mEditToolbarController != null) {
            this.mEditToolbarController.hide(this.isStartAiTextWindow);
            this.isStartAiTextWindow = false;
        }
        if (this.mAiChooserMenu != null) {
            this.mAiChooserMenu.dismiss();
            this.mAiChooserMenu = null;
        }
        if (this.mImageChooserMenu != null) {
            this.mImageChooserMenu.dismiss();
        }
    }

    protected void onAudioMode() {
        FrameLayout frameLayout = this.mEditorMaskView;
        if (frameLayout != null) {
            Intrinsics.checkNotNull(frameLayout);
            frameLayout.setVisibility(0);
        }
    }

    protected void onTitleMode() {
        if (this.mEditToolbarController != null) {
            this.mEditToolbarController.onEditTitleMode();
        }
    }

    protected void onEditMode(boolean showRichTextTool) {
        if (this.mEditToolbarController != null) {
            IBaseNoteEditToolbarController iBaseNoteEditToolbarController = this.mEditToolbarController;
            WebRichEditor webRichEditor = this.mEditor;
            Intrinsics.checkNotNull(webRichEditor);
            iBaseNoteEditToolbarController.onEditContentMode(true, showRichTextTool, !webRichEditor.getEditorTextIsEmpty());
        }
    }

    protected final void onFirstEditMode(boolean showRichTextTool) {
        if (this.mEditToolbarController != null) {
            IBaseNoteEditToolbarController iBaseNoteEditToolbarController = this.mEditToolbarController;
            WebRichEditor webRichEditor = this.mEditor;
            Intrinsics.checkNotNull(webRichEditor);
            iBaseNoteEditToolbarController.onFirstEditContentMode(true, showRichTextTool, !webRichEditor.getEditorTextIsEmpty());
        }
    }

    protected final void recordToView() {
        FrameLayout frameLayout = this.mEditorMaskView;
        if (frameLayout != null) {
            Intrinsics.checkNotNull(frameLayout);
            frameLayout.setVisibility(8);
            FrameLayout frameLayout2 = this.mEditorMaskView;
            Intrinsics.checkNotNull(frameLayout2);
            frameLayout2.removeAllViews();
        }
        registerTapShareCallBack();
    }

    private final ArrayList<String> getUrisFromImages(String[] imageBase64Datas) {
        ArrayList<String> arrayList = new ArrayList<>(imageBase64Datas.length);
        for (String str : imageBase64Datas) {
            Bitmap bitmapBase64ToBitmap = ImageUtils.INSTANCE.base64ToBitmap(str);
            Context contextRequireContext = requireContext();
            Intrinsics.checkNotNullExpressionValue(contextRequireContext, "requireContext(...)");
            Intrinsics.checkNotNull(bitmapBase64ToBitmap);
            Uri uriCreateImageTempUri = createImageTempUri(contextRequireContext, bitmapBase64ToBitmap, true);
            if (uriCreateImageTempUri != null) {
                arrayList.add(uriCreateImageTempUri.toString());
            }
        }
        return arrayList;
    }

    private final Uri createImageTempUri(Context context, Bitmap bitmap, boolean isPng) {
        String str = "/IMG_SHARE_" + System.currentTimeMillis();
        File externalCacheDir = requireActivity().getExternalCacheDir();
        File file = new File((externalCacheDir != null ? externalCacheDir.getAbsolutePath() : null) + str + (isPng ? ".png" : AttachmentUtils.IMAGE_SUFFIX_JPEG));
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (Exception e) {
                Logger.INSTANCE.e(TAG, "createNewFile error: " + e);
            }
        }
        Uri compatUri = CompatUtils.getCompatUri(BaseNoteApp.INSTANCE.getInstance(), file);
        Intrinsics.checkNotNullExpressionValue(compatUri, "getCompatUri(...)");
        ContentResolver contentResolver = context.getContentResolver();
        try {
            OutputStream outputStreamOpenOutputStream = contentResolver.openOutputStream(compatUri);
            try {
                OutputStream outputStream = outputStreamOpenOutputStream;
                if (isPng) {
                    Bitmap.CompressFormat compressFormat = Bitmap.CompressFormat.PNG;
                    Intrinsics.checkNotNull(outputStream);
                    bitmap.compress(compressFormat, 100, outputStream);
                } else {
                    Bitmap.CompressFormat compressFormat2 = Bitmap.CompressFormat.JPEG;
                    Intrinsics.checkNotNull(outputStream);
                    bitmap.compress(compressFormat2, 90, outputStream);
                }
                CloseableKt.closeFinally(outputStreamOpenOutputStream, null);
                return compatUri;
            } catch (Throwable th) {
                try {
                    throw th;
                } catch (Throwable th2) {
                    CloseableKt.closeFinally(outputStreamOpenOutputStream, th);
                    throw th2;
                }
            }
        } catch (Exception unused) {
            contentResolver.delete(compatUri, null, null);
            return null;
        }
    }

    @Override // com.miui.notes.basefeature.noteedit.BaseNormalNoteEditFragment
    protected BaseNormalNoteEditFragment.EditNoteLoadTask getEditNoteLoadTask(Intent intent) {
        return new HybridEditNoteLoadTask(intent);
    }

    @Override // com.miui.notes.basefeature.noteedit.BaseNormalNoteEditFragment
    protected boolean handleSendIntent(Intent intent) {
        Logger.INSTANCE.d(TAG, "handleSendIntent");
        updateActionMode(EditorActionMode.VIEW, true);
        return super.handleSendIntent(intent);
    }

    @Override // com.miui.notes.basefeature.noteedit.BaseNormalNoteEditFragment
    protected void doneEdit() {
        super.doneEdit();
    }

    @Override // com.miui.notes.basefeature.noteedit.BaseNormalNoteEditFragment
    protected void asyncSaveNoteInstantlyIfNeeded() {
        if (DualScreenManager.INSTANCE.isPcActivityLiving() && this.mIsRecording) {
            return;
        }
        super.asyncSaveNoteInstantlyIfNeeded();
    }

    public final void closeTriggerView() {
        if (this.mNestedHeaderLayout == null || !this.mNestedHeaderLayout.isTriggerOpen()) {
            return;
        }
        this.mNestedHeaderLayout.setAutoTriggerClose(false);
    }

    @Override // com.miui.notes.basefeature.noteedit.BaseNormalNoteEditFragment
    protected void updateWorkingTextAndTitle() {
        Logger.INSTANCE.d(TAG, " updateWorkingTextAndTitle:");
        if (this.mWorkingNote == null) {
            return;
        }
        WorkingNote workingNote = this.mWorkingNote;
        Intrinsics.checkNotNull(workingNote);
        WebRichEditor webRichEditor = this.mEditor;
        Intrinsics.checkNotNull(webRichEditor);
        workingNote.setWorkingTitle(webRichEditor.getTitle());
        WebRichEditor webRichEditor2 = this.mEditor;
        Intrinsics.checkNotNull(webRichEditor2);
        CharSequence richTextData = webRichEditor2.getRichTextData();
        Intrinsics.checkNotNull(richTextData, "null cannot be cast to non-null type kotlin.String");
        String str = (String) richTextData;
        Logger.INSTANCE.d(TAG, "updateWorkingTextAndTitle:" + str.length());
        WorkingNote workingNote2 = this.mWorkingNote;
        Intrinsics.checkNotNull(workingNote2);
        workingNote2.setWorkingText(str);
    }

    @Override // com.miui.notes.basefeature.noteedit.BaseNormalNoteEditFragment
    public void refreshAlertTime() {
        if (this.mWorkingNote != null) {
            WorkingNote workingNote = this.mWorkingNote;
            Intrinsics.checkNotNull(workingNote);
            if (workingNote.getNoteId() > 0) {
                WorkingNote workingNote2 = this.mWorkingNote;
                Intrinsics.checkNotNull(workingNote2);
                workingNote2.updateAlarmInfo();
                WebRichEditor webRichEditor = this.mEditor;
                Intrinsics.checkNotNull(webRichEditor);
                WorkingNote workingNote3 = this.mWorkingNote;
                Intrinsics.checkNotNull(workingNote3);
                webRichEditor.updateExtraInto(workingNote3.getExtraInfo());
                if (getActionBarController() != null) {
                    getActionBarController().updateAlarm(this.mWorkingNote);
                }
            }
        }
    }

    @Override // com.miui.notes.basefeature.noteedit.BaseNormalNoteEditFragment
    protected void setWorkingNote(final WorkingNote workingNote, boolean forceEdit) {
        Intrinsics.checkNotNullParameter(workingNote, "workingNote");
        super.setWorkingNote(workingNote, forceEdit);
        WorkingNote workingNote2 = this.mWorkingNote;
        Intrinsics.checkNotNull(workingNote2);
        updateNoteThemeOnNative(workingNote2.getThemeId(), false, false);
        getActionBarController().getHeadInfo().post(new Runnable() { // from class: com.miui.notes.basefeature.noteedit.BaseHybridNoteEditFragment$$ExternalSyntheticLambda61
            @Override // java.lang.Runnable
            public final void run() {
                BaseHybridNoteEditFragment.setWorkingNote$lambda$37(workingNote, this);
            }
        });
        if (this.isSetExcerptObserver.get() || !isThisNoteDoExcerpt()) {
            return;
        }
        this.excerptContentObserver = new ContentObserver(new Handler(Looper.getMainLooper())) { // from class: com.miui.notes.basefeature.noteedit.BaseHybridNoteEditFragment.setWorkingNote.2
            @Override // android.database.ContentObserver
            public void onChange(boolean selfChange) {
                super.onChange(selfChange);
                Logger.INSTANCE.d(BaseHybridNoteEditFragment.TAG, "my uri changed onChange workingNote.getNoteId(): " + FloatWindowManagerAgent.getExcerptId());
                if (!BaseHybridNoteEditFragment.this.isThisNoteDoExcerpt() || BaseHybridNoteEditFragment.this.getWorkingNote() == null || BaseHybridNoteEditFragment.this.getWorkingNote().getNoteId() == FloatWindowManagerAgent.getExcerptId()) {
                    return;
                }
                BaseHybridNoteEditFragment.this.setWorkingNote(WorkingNote.INSTANCE.loadNote("_id=" + FloatWindowManagerAgent.getExcerptId(), null), true);
            }
        };
        Uri uriWithAppendedId = ContentUris.withAppendedId(Notes.Note.CONTENT_URI_ATOMIC, FloatWindowManagerAgent.getExcerptId());
        Intrinsics.checkNotNullExpressionValue(uriWithAppendedId, "withAppendedId(...)");
        Logger.INSTANCE.d(TAG, "observer uri ");
        if (getActivity() != null) {
            ContentResolver contentResolver = requireActivity().getContentResolver();
            ContentObserver contentObserver = this.excerptContentObserver;
            Intrinsics.checkNotNull(contentObserver);
            contentResolver.registerContentObserver(uriWithAppendedId, true, contentObserver);
            this.isSetExcerptObserver.set(true);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void setWorkingNote$lambda$37(WorkingNote workingNote, BaseHybridNoteEditFragment baseHybridNoteEditFragment) {
        Logger.INSTANCE.d(TAG, "setWorkingNote,updateExtraInfo");
        workingNote.getExtraInfo().textCount = workingNote.getTextCount();
        WebRichEditor webRichEditor = baseHybridNoteEditFragment.mEditor;
        Intrinsics.checkNotNull(webRichEditor);
        webRichEditor.updateExtraInto(workingNote.getExtraInfo());
        baseHybridNoteEditFragment.getActionBarController().update(workingNote, 0);
    }

    @Override // com.miui.notes.basefeature.noteedit.BaseNormalNoteEditFragment
    protected void sendAsText() {
        super.sendAsText();
        NoteContentStat.QUIT_WAY = NoteContentStat.QUIT_WAY_SHARE;
        trackReportQuitContent(this.mPendingHandleIntent, this.mWorkingNote);
        NoteContentStat.cancel();
    }

    @Override // com.miui.notes.basefeature.noteedit.BaseNormalNoteEditFragment
    protected void sendAsImage() {
        Theme theme;
        AigcPicMetaData aigcPicMetaData;
        String aigcDataId;
        String aigcDataId2;
        Logger.INSTANCE.d(TAG, "sendAsImg");
        if (isWorkingNoteEmpty()) {
            Toast.makeText(BaseNoteApp.INSTANCE.getInstance(), R.string.error_send_empty_note, 0).show();
            return;
        }
        WebRichEditor webRichEditor = this.mEditor;
        Intrinsics.checkNotNull(webRichEditor);
        webRichEditor.pauseVoice();
        if (!RomUtils.isOS31Support()) {
            WorkingNote workingNote = this.mWorkingNote;
            if (workingNote == null || workingNote.getAiTextUsed() != 1) {
                aigcPicMetaData = null;
            } else {
                WorkingNote workingNote2 = this.mWorkingNote;
                if (workingNote2 == null || (aigcDataId = workingNote2.getAigcDataId()) == null) {
                    aigcDataId = "";
                }
                WorkingNote workingNote3 = this.mWorkingNote;
                if (workingNote3 == null || (aigcDataId2 = workingNote3.getAigcDataId()) == null) {
                    aigcDataId2 = "";
                }
                aigcPicMetaData = new AigcPicMetaData(aigcDataId, aigcDataId2);
            }
            launchSnsShareActivity(3, 16, "", aigcPicMetaData);
        } else {
            Handler handler = this.mHandler;
            Runnable runnable = this.mShowCreatingProgressDialogTask;
            Intrinsics.checkNotNull(runnable);
            handler.post(runnable);
            this.mRenderGenerateCallback = new BaseNormalNoteEditFragment.RenderGenerateCallback() { // from class: com.miui.notes.basefeature.noteedit.BaseHybridNoteEditFragment.sendAsImage.1
                @Override // com.miui.notes.basefeature.noteedit.BaseNormalNoteEditFragment.RenderGenerateCallback
                public void onComplete(Uri uri, Uri previewUri) {
                    BaseHybridNoteEditFragment.this.startSharePageByRenderUri(uri, previewUri);
                    BaseHybridNoteEditFragment.this.dismissProgressDialogWithJudge();
                }
            };
            if (com.miui.notes.ai.utils.RomUtils.isPadDevice() || com.miui.notes.ai.utils.RomUtils.isFoldDevice() || this.mWorkingNote == null) {
                theme = ResourceManager.getTheme(0);
            } else {
                WorkingNote workingNote4 = this.mWorkingNote;
                Intrinsics.checkNotNull(workingNote4);
                theme = ResourceManager.getTheme(workingNote4.getThemeId());
            }
            if (theme == null) {
                theme = ResourceManager.getTheme(0);
            }
            if (theme instanceof AnimTheme) {
                generateAnimRender((AnimTheme) theme);
            } else {
                BaseNormalNoteEditFragment.RenderGenerateCallback renderGenerateCallback = this.mRenderGenerateCallback;
                if (renderGenerateCallback == null) {
                    Logger.INSTANCE.d(TAG, "sendAsImg mRenderGenerateCallback is null");
                    return;
                } else {
                    Intrinsics.checkNotNull(renderGenerateCallback);
                    Intrinsics.checkNotNull(theme);
                    generateRender(renderGenerateCallback, theme);
                }
            }
        }
        NoteContentStat.QUIT_WAY = NoteContentStat.QUIT_WAY_SHARE;
        trackReportQuitContent(this.mPendingHandleIntent, this.mWorkingNote);
        NoteContentStat.cancel();
    }

    @Override // com.miui.notes.basefeature.noteedit.BaseNormalNoteEditFragment
    public void startSharePageByRenderUri(Uri renderUri, Uri previewUri) {
        Intent intent = new Intent();
        intent.addFlags(1);
        Context context = getContext();
        if (context != null) {
            context.grantUriPermission("com.android.intentresolver", previewUri, 1);
        }
        intent.putExtra(this.EXTRA_CONTENT_PREVIEW_NOTES, previewUri);
        intent.setClipData(ClipData.newRawUri("share_image", renderUri));
        intent.setAction("android.intent.action.SEND");
        intent.putExtra(this.EXTRA_TAP_SHARE_SUPPORT, true);
        intent.putExtra("android.intent.extra.STREAM", renderUri);
        intent.setType("image/png");
        startActivity(Intent.createChooser(intent, null));
        NoteContentStat.QUIT_WAY = NoteContentStat.QUIT_WAY_SHARE;
        trackReportQuitContent(this.mPendingHandleIntent, this.mWorkingNote);
        NoteContentStat.cancel();
    }

    public final void generateRender(BaseNormalNoteEditFragment.RenderGenerateCallback callback, Theme theme) {
        Intrinsics.checkNotNullParameter(callback, "callback");
        Intrinsics.checkNotNullParameter(theme, "theme");
        Ref.ObjectRef objectRef = new Ref.ObjectRef();
        Ref.ObjectRef objectRef2 = new Ref.ObjectRef();
        Context context = getContext();
        WorkingNote workingNote = this.mWorkingNote;
        Intrinsics.checkNotNull(workingNote);
        Theme.ShareStyle shareStyle = theme.getShareStyle(context, workingNote.getModifiedDate());
        int i = (theme.getId() == 0 && UIUtils.isNightMode(getContext())) ? R.drawable.ic_richtext_share_checkbox_u_light : R.drawable.ic_richtext_share_checkbox_u;
        RenderBuilder renderBuilder = new RenderBuilder(getContext());
        WorkingNote workingNote2 = this.mWorkingNote;
        String title = null;
        RenderBuilder content = renderBuilder.setContent(workingNote2 != null ? workingNote2.getContent() : null);
        WorkingNote workingNote3 = this.mWorkingNote;
        if (TextUtils.isEmpty(workingNote3 != null ? workingNote3.getTitle() : null)) {
            title = "";
        } else {
            WorkingNote workingNote4 = this.mWorkingNote;
            if (workingNote4 != null) {
                title = workingNote4.getTitle();
            }
        }
        RenderBuilder title2 = content.setTitle(title);
        WorkingNote workingNote5 = this.mWorkingNote;
        Intrinsics.checkNotNull(workingNote5);
        this.mRender = title2.setThemeId(workingNote5.getThemeId()).setStyle(shareStyle).setCheckRes(R.drawable.ic_richtext_share_checkbox_c).setUnCheckRes(i).build();
        BuildersKt__Builders_commonKt.launch$default(LifecycleOwnerKt.getLifecycleScope(this), Dispatchers.getDefault(), null, new C02711(objectRef, objectRef2, callback, null), 2, null);
    }

    /* JADX INFO: renamed from: com.miui.notes.basefeature.noteedit.BaseHybridNoteEditFragment$generateRender$1, reason: invalid class name and case insensitive filesystem */
    /* JADX INFO: compiled from: BaseHybridNoteEditFragment.kt */
    @Metadata(d1 = {"\u0000\n\n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\u0010\u0000\u001a\u00020\u0001*\u00020\u0002H\n"}, d2 = {"<anonymous>", "", "Lkotlinx/coroutines/CoroutineScope;"}, k = 3, mv = {2, 2, 0}, xi = 48)
    @DebugMetadata(c = "com.miui.notes.basefeature.noteedit.BaseHybridNoteEditFragment$generateRender$1", f = "BaseHybridNoteEditFragment.kt", i = {}, l = {R2.color.ai_enhanced_setting_page_bg}, m = "invokeSuspend", n = {}, s = {})
    static final class C02711 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
        final /* synthetic */ BaseNormalNoteEditFragment.RenderGenerateCallback $callback;
        final /* synthetic */ Ref.ObjectRef<Uri> $previewUri;
        final /* synthetic */ Ref.ObjectRef<Uri> $uri;
        int label;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        C02711(Ref.ObjectRef<Uri> objectRef, Ref.ObjectRef<Uri> objectRef2, BaseNormalNoteEditFragment.RenderGenerateCallback renderGenerateCallback, Continuation<? super C02711> continuation) {
            super(2, continuation);
            this.$uri = objectRef;
            this.$previewUri = objectRef2;
            this.$callback = renderGenerateCallback;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
            return BaseHybridNoteEditFragment.this.new C02711(this.$uri, this.$previewUri, this.$callback, continuation);
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(CoroutineScope coroutineScope, Continuation<? super Unit> continuation) {
            return ((C02711) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
        }

        /* JADX INFO: renamed from: com.miui.notes.basefeature.noteedit.BaseHybridNoteEditFragment$generateRender$1$1, reason: invalid class name and collision with other inner class name */
        /* JADX INFO: compiled from: BaseHybridNoteEditFragment.kt */
        @Metadata(d1 = {"\u0000\n\n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\u0010\u0000\u001a\u00020\u0001*\u00020\u0002H\n"}, d2 = {"<anonymous>", "", "Lkotlinx/coroutines/CoroutineScope;"}, k = 3, mv = {2, 2, 0}, xi = 48)
        @DebugMetadata(c = "com.miui.notes.basefeature.noteedit.BaseHybridNoteEditFragment$generateRender$1$1", f = "BaseHybridNoteEditFragment.kt", i = {}, l = {}, m = "invokeSuspend", n = {}, s = {})
        static final class C00881 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
            final /* synthetic */ Ref.ObjectRef<Uri> $previewUri;
            final /* synthetic */ Ref.ObjectRef<Uri> $uri;
            int label;
            final /* synthetic */ BaseHybridNoteEditFragment this$0;

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            C00881(BaseHybridNoteEditFragment baseHybridNoteEditFragment, Ref.ObjectRef<Uri> objectRef, Ref.ObjectRef<Uri> objectRef2, Continuation<? super C00881> continuation) {
                super(2, continuation);
                this.this$0 = baseHybridNoteEditFragment;
                this.$uri = objectRef;
                this.$previewUri = objectRef2;
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
                return new C00881(this.this$0, this.$uri, this.$previewUri, continuation);
            }

            @Override // kotlin.jvm.functions.Function2
            public final Object invoke(CoroutineScope coroutineScope, Continuation<? super Unit> continuation) {
                return ((C00881) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
            }

            /* JADX WARN: Type inference failed for: r0v2, types: [T, android.net.Uri] */
            /* JADX WARN: Type inference failed for: r3v4, types: [T, android.net.Uri] */
            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Object invokeSuspend(Object obj) {
                IntrinsicsKt.getCOROUTINE_SUSPENDED();
                if (this.label == 0) {
                    ResultKt.throwOnFailure(obj);
                    Render render = this.this$0.mRender;
                    if (render != null) {
                        render.shouldBlock = false;
                    }
                    Ref.ObjectRef<Uri> objectRef = this.$uri;
                    BaseHybridNoteEditFragment baseHybridNoteEditFragment = this.this$0;
                    Context context = baseHybridNoteEditFragment.getContext();
                    Render render2 = this.this$0.mRender;
                    Intrinsics.checkNotNull(render2);
                    objectRef.element = baseHybridNoteEditFragment.createRenderTempUri(context, render2);
                    Render render3 = this.this$0.mRender;
                    Intrinsics.checkNotNull(render3);
                    int height = render3.getHeight();
                    Render render4 = this.this$0.mRender;
                    if (render4 != null) {
                        int height2 = render4.getHeight();
                        BaseHybridNoteEditFragment baseHybridNoteEditFragment2 = this.this$0;
                        if (height2 > baseHybridNoteEditFragment2.SHARE_PREVIEW_IMAGE_MAX_HEIGHT) {
                            height = baseHybridNoteEditFragment2.SHARE_PREVIEW_IMAGE_MAX_HEIGHT;
                        } else if (height2 < baseHybridNoteEditFragment2.SHARE_PREVIEW_IMAGE_MIN_HEIGHT) {
                            Render render5 = baseHybridNoteEditFragment2.mRender;
                            Intrinsics.checkNotNull(render5);
                            render5.setHeight(baseHybridNoteEditFragment2.SHARE_PREVIEW_IMAGE_MIN_HEIGHT);
                            height = baseHybridNoteEditFragment2.SHARE_PREVIEW_IMAGE_MIN_HEIGHT;
                        }
                    }
                    Ref.ObjectRef<Uri> objectRef2 = this.$previewUri;
                    BaseHybridNoteEditFragment baseHybridNoteEditFragment3 = this.this$0;
                    Context context2 = baseHybridNoteEditFragment3.getContext();
                    Render render6 = this.this$0.mRender;
                    Intrinsics.checkNotNull(render6);
                    objectRef2.element = baseHybridNoteEditFragment3.createRenderTempUri(context2, render6, height);
                    return Unit.INSTANCE;
                }
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            Object coroutine_suspended = IntrinsicsKt.getCOROUTINE_SUSPENDED();
            int i = this.label;
            if (i == 0) {
                ResultKt.throwOnFailure(obj);
                this.label = 1;
                if (BuildersKt.withContext(Dispatchers.getIO(), new C00881(BaseHybridNoteEditFragment.this, this.$uri, this.$previewUri, null), this) == coroutine_suspended) {
                    return coroutine_suspended;
                }
            } else {
                if (i != 1) {
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                }
                ResultKt.throwOnFailure(obj);
            }
            Logger.INSTANCE.d(BaseHybridNoteEditFragment.this.getTAG(), "generateRen der uri: " + this.$uri.element + " previewUri " + this.$previewUri.element);
            if (this.$uri.element != null && this.$previewUri.element != null) {
                this.$callback.onComplete(this.$uri.element, this.$previewUri.element);
            }
            return Unit.INSTANCE;
        }
    }

    public final void generateAnimRender(AnimTheme animTheme) {
        final Theme.ShareStyle shareStyle;
        final MamlView backgroundViewForSns;
        if (animTheme != null) {
            BaseNoteApp companion = BaseNoteApp.INSTANCE.getInstance();
            WorkingNote workingNote = this.mWorkingNote;
            Intrinsics.checkNotNull(workingNote);
            shareStyle = animTheme.getShareStyle(companion, workingNote.getModifiedDate());
        } else {
            shareStyle = null;
        }
        final int i = (animTheme != null && animTheme.getId() == 0 && UIUtils.isNightMode(getContext())) ? R.drawable.ic_richtext_share_checkbox_u_light : R.drawable.ic_richtext_share_checkbox_u;
        if (animTheme != null) {
            FragmentActivity activity = getActivity();
            backgroundViewForSns = animTheme.getBackgroundViewForSns(activity != null ? activity.getApplicationContext() : null);
        } else {
            backgroundViewForSns = null;
        }
        Intrinsics.checkNotNull(backgroundViewForSns);
        backgroundViewForSns.setId(R.id.content_group);
        backgroundViewForSns.init();
        backgroundViewForSns.sendCommand("init");
        Logger.INSTANCE.d(getTAG(), "createAnimShareRenderUri mamlView: " + backgroundViewForSns);
        Context context = getContext();
        FrameLayout frameLayout = context != null ? new FrameLayout(context) : null;
        this.mMamlViewContainer = frameLayout;
        if (frameLayout != null) {
            frameLayout.addView(backgroundViewForSns, new FrameLayout.LayoutParams(-1, -1));
        }
        RichEditorWebView richEditorWebView = this.mEditorWebView;
        if (richEditorWebView != null) {
            richEditorWebView.addView(this.mMamlViewContainer, 0, new FrameLayout.LayoutParams(-1, -1));
        }
        FrameLayout frameLayout2 = this.mMamlViewContainer;
        if (frameLayout2 != null) {
            frameLayout2.setAlpha(0.0f);
        }
        final int i2 = R.drawable.ic_richtext_share_checkbox_c;
        this.renderGenerateTask = new Runnable() { // from class: com.miui.notes.basefeature.noteedit.BaseHybridNoteEditFragment$$ExternalSyntheticLambda16
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.createShareAnimRenderUri(shareStyle, i2, i, backgroundViewForSns);
            }
        };
        Handler handler = this.mHandler;
        Runnable runnable = this.renderGenerateTask;
        Intrinsics.checkNotNull(runnable);
        handler.postDelayed(runnable, 500L);
    }

    public final void createShareAnimRenderUri(Theme.ShareStyle shareStyle, int checkedRes, int unCheckedRes, MamlView mamlView) {
        String title;
        Intrinsics.checkNotNullParameter(mamlView, "mamlView");
        RenderBuilder renderBuilder = new RenderBuilder(getContext());
        WorkingNote workingNote = this.mWorkingNote;
        Intrinsics.checkNotNull(workingNote);
        RenderBuilder content = renderBuilder.setContent(workingNote.getContent());
        WorkingNote workingNote2 = this.mWorkingNote;
        if (TextUtils.isEmpty(workingNote2 != null ? workingNote2.getTitle() : null)) {
            title = "";
        } else {
            WorkingNote workingNote3 = this.mWorkingNote;
            title = workingNote3 != null ? workingNote3.getTitle() : null;
        }
        RenderBuilder title2 = content.setTitle(title);
        WorkingNote workingNote4 = this.mWorkingNote;
        Integer numValueOf = workingNote4 != null ? Integer.valueOf(workingNote4.getThemeId()) : null;
        Intrinsics.checkNotNull(numValueOf);
        this.mRender = title2.setThemeId(numValueOf.intValue()).setStyle(shareStyle).setCheckRes(checkedRes).setUnCheckRes(unCheckedRes).buildWithCallback(mamlView, new Runnable() { // from class: com.miui.notes.basefeature.noteedit.BaseHybridNoteEditFragment$$ExternalSyntheticLambda63
            @Override // java.lang.Runnable
            public final void run() {
                BaseHybridNoteEditFragment.createShareAnimRenderUri$lambda$40(this.f$0);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void createShareAnimRenderUri$lambda$40(BaseHybridNoteEditFragment baseHybridNoteEditFragment) {
        Logger.INSTANCE.d(baseHybridNoteEditFragment.getTAG(), "createShareAnimRenderUri mRender: " + baseHybridNoteEditFragment.mRender);
        BuildersKt__Builders_commonKt.launch$default(LifecycleOwnerKt.getLifecycleScope(baseHybridNoteEditFragment), Dispatchers.getMain(), null, new BaseHybridNoteEditFragment$createShareAnimRenderUri$1$1(baseHybridNoteEditFragment, null), 2, null);
    }

    public final Uri createRenderTempUri(Context context, Render render) {
        File externalCacheDir;
        Intrinsics.checkNotNullParameter(render, "render");
        File file = new File(((context == null || (externalCacheDir = context.getExternalCacheDir()) == null) ? null : externalCacheDir.getAbsolutePath()) + InternalZipConstants.ZIP_FILE_SEPARATOR + ("IMG_SHARE_" + System.currentTimeMillis() + ".png"));
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (Exception e) {
                Logger.INSTANCE.e(getTAG(), " createNewFile error: " + e);
                return null;
            }
        }
        if (render.saveToFile(file)) {
            Uri compatUri = CompatUtils.getCompatUri(context, file);
            Intrinsics.checkNotNullExpressionValue(compatUri, "getCompatUri(...)");
            return compatUri;
        }
        if (file.exists()) {
            file.delete();
        }
        return null;
    }

    public final Uri createRenderTempUri(Context context, Render render, int validHeight) {
        File externalCacheDir;
        Intrinsics.checkNotNullParameter(render, "render");
        File file = new File(((context == null || (externalCacheDir = context.getExternalCacheDir()) == null) ? null : externalCacheDir.getAbsolutePath()) + InternalZipConstants.ZIP_FILE_SEPARATOR + ("IMG_SHARE_PREVIEW" + System.currentTimeMillis() + ".png"));
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (Exception e) {
                Logger.INSTANCE.e(getTAG(), " createNewFile error: " + e);
                return null;
            }
        }
        if (render.saveToFile(file, validHeight)) {
            Uri compatUri = CompatUtils.getCompatUri(context, file);
            Intrinsics.checkNotNullExpressionValue(compatUri, "getCompatUri(...)");
            return compatUri;
        }
        if (file.exists()) {
            file.delete();
        }
        return null;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void mShowCreatingProgressDialogTask$lambda$42(final BaseHybridNoteEditFragment baseHybridNoteEditFragment) {
        Logger.INSTANCE.d(baseHybridNoteEditFragment.getTAG(), "run init dialog: ");
        ProgressDialog progressDialog = new ProgressDialog(baseHybridNoteEditFragment.getContext());
        baseHybridNoteEditFragment.mDialog = progressDialog;
        progressDialog.setOnCancelListener(new DialogInterface.OnCancelListener() { // from class: com.miui.notes.basefeature.noteedit.BaseHybridNoteEditFragment$$ExternalSyntheticLambda45
            @Override // android.content.DialogInterface.OnCancelListener
            public final void onCancel(DialogInterface dialogInterface) {
                BaseHybridNoteEditFragment.mShowCreatingProgressDialogTask$lambda$42$lambda$41(this.f$0, dialogInterface);
            }
        });
        ProgressDialog progressDialog2 = baseHybridNoteEditFragment.mDialog;
        if (progressDialog2 != null) {
            progressDialog2.setMessage(baseHybridNoteEditFragment.getResources().getString(R.string.dlg_info_creating_message));
        }
        ProgressDialog progressDialog3 = baseHybridNoteEditFragment.mDialog;
        if (progressDialog3 != null) {
            progressDialog3.setIndeterminate(true);
        }
        ProgressDialog progressDialog4 = baseHybridNoteEditFragment.mDialog;
        if (progressDialog4 != null) {
            progressDialog4.setCanceledOnTouchOutside(false);
        }
        baseHybridNoteEditFragment.mShowTime = Long.valueOf(System.currentTimeMillis());
        ProgressDialog progressDialog5 = baseHybridNoteEditFragment.mDialog;
        if (progressDialog5 != null) {
            progressDialog5.show();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void mShowCreatingProgressDialogTask$lambda$42$lambda$41(BaseHybridNoteEditFragment baseHybridNoteEditFragment, DialogInterface dialogInterface) {
        Render render = baseHybridNoteEditFragment.mRender;
        if (render != null) {
            render.shouldBlock = true;
        }
        if (baseHybridNoteEditFragment.renderGenerateTask != null) {
            Handler handler = baseHybridNoteEditFragment.mHandler;
            Runnable runnable = baseHybridNoteEditFragment.renderGenerateTask;
            Intrinsics.checkNotNull(runnable);
            handler.removeCallbacks(runnable);
            baseHybridNoteEditFragment.renderGenerateTask = null;
        }
        RichEditorWebView richEditorWebView = baseHybridNoteEditFragment.mEditorWebView;
        if (richEditorWebView != null) {
            richEditorWebView.removeView(baseHybridNoteEditFragment.mMamlViewContainer);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void dismissProgressDialogWithJudge() {
        long jCurrentTimeMillis = System.currentTimeMillis();
        Long l = this.mShowTime;
        Intrinsics.checkNotNull(l);
        if (Math.abs(jCurrentTimeMillis - l.longValue()) < 100) {
            this.mHandler.postDelayed(new Runnable() { // from class: com.miui.notes.basefeature.noteedit.BaseHybridNoteEditFragment$$ExternalSyntheticLambda33
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.dismissProgressDialog();
                }
            }, 100L);
        } else {
            this.mHandler.post(new Runnable() { // from class: com.miui.notes.basefeature.noteedit.BaseHybridNoteEditFragment$$ExternalSyntheticLambda44
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.dismissProgressDialog();
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void dismissProgressDialog() {
        ProgressDialog progressDialog = this.mDialog;
        if (progressDialog != null) {
            try {
                Intrinsics.checkNotNull(progressDialog);
                progressDialog.dismiss();
            } catch (Exception e) {
                Logger.INSTANCE.d(getTAG(), "dismissProgressDialog dismiss exception ");
                e.printStackTrace();
            }
            this.mDialog = null;
        }
    }

    @Override // com.miui.notes.basefeature.noteedit.BaseNormalNoteEditFragment
    protected void sendAsDuplicate() {
        super.sendAsDuplicate();
        Logger.INSTANCE.d(TAG, "sendAsDuplicate mWorkingNote isEmpty:" + isWorkingNoteEmpty());
        if (isAdded()) {
            handleNoteContent();
        }
    }

    public final void handleNoteContent() {
        if (this.mWorkingNote == null) {
            Toast.makeText(requireContext().getApplicationContext(), R.string.error_send_empty_note, 0).show();
            return;
        }
        updateWorkingTextAndTitle();
        if (isWorkingNoteEmpty()) {
            Toast.makeText(requireContext().getApplicationContext(), R.string.error_send_empty_note, 0).show();
        } else {
            createDuplicateAndSend();
        }
    }

    public final void createDuplicateAndSend() {
        String title;
        String txtContentFirstLine;
        TapShareDataModel.SingleNoteTapShareData singleNoteTapShareDataPrepareSingleNoteDataWithPreview = TapShareDataPrepareUtils.prepareSingleNoteDataWithPreview(getContext(), this.mWorkingNote, null, this.mEditorWebView);
        if (singleNoteTapShareDataPrepareSingleNoteDataWithPreview == null) {
            Logger.INSTANCE.d(TAG, "Failed to prepare tap share data");
            return;
        }
        Intent intent = new Intent("android.intent.action.SEND");
        intent.putExtra(this.EXTRA_TAP_SHARE_DEEP_LINK, TapShareSender.DEEP_LINK);
        intent.putExtra(this.EXTRA_TAP_SHARE_SUPPORT, true);
        intent.putExtra(this.EXTRA_TAP_SHARE_DEEP_ACTION, "note");
        intent.setFlags(1);
        intent.setType("application/zip");
        intent.putExtra("android.intent.extra.STREAM", singleNoteTapShareDataPrepareSingleNoteDataWithPreview.zipUri);
        intent.setClipData(ClipData.newRawUri(null, singleNoteTapShareDataPrepareSingleNoteDataWithPreview.zipUri));
        String str = this.EXTRA_TAP_SHARE_TITLE;
        WorkingNote workingNote = this.mWorkingNote;
        if (TextUtils.isEmpty(workingNote != null ? workingNote.getTitle() : null)) {
            Context context = getContext();
            WorkingNote workingNote2 = this.mWorkingNote;
            title = getTxtContentFirstLine(context, workingNote2 != null ? workingNote2.getContent() : null);
        } else {
            WorkingNote workingNote3 = this.mWorkingNote;
            title = workingNote3 != null ? workingNote3.getTitle() : null;
        }
        intent.putExtra(str, title);
        String str2 = this.EXTRA_TAP_SHARE_SUBTITLE;
        WorkingNote workingNote4 = this.mWorkingNote;
        if (TextUtils.isEmpty(workingNote4 != null ? workingNote4.getTitle() : null)) {
            txtContentFirstLine = null;
        } else {
            Context context2 = getContext();
            WorkingNote workingNote5 = this.mWorkingNote;
            txtContentFirstLine = getTxtContentFirstLine(context2, workingNote5 != null ? workingNote5.getContent() : null);
        }
        intent.putExtra(str2, txtContentFirstLine);
        Intent intentCreateChooser = Intent.createChooser(intent, null);
        intentCreateChooser.putExtra("android.intent.extra.EXCLUDE_COMPONENTS", new ComponentName[]{new ComponentName("com.miui.notes", "com.miui.notes.ui.activity.IntermediaryActivity")});
        Intrinsics.checkNotNull(intentCreateChooser);
        startActivity(intentCreateChooser);
    }

    protected void launchSnsShareActivity(int type, int requestCode, String imgBase64, AigcPicMetaData aigcPicMetaData) {
        int themeId;
        Logger.INSTANCE.d(TAG, "launchSnsShareActivity :" + type + ",imgBase64:" + imgBase64);
        ActivityResultLauncher<Intent> activityResultLauncher = this.mToShareResult;
        BaseActivity baseActivity = (BaseActivity) getActivity();
        WorkingNote workingNote = this.mWorkingNote;
        Intrinsics.checkNotNull(workingNote);
        String content = workingNote.getContent();
        WorkingNote workingNote2 = this.mWorkingNote;
        Intrinsics.checkNotNull(workingNote2);
        String title = workingNote2.getTitle();
        if (LiteUtils.isNewLiteOrMiddle()) {
            themeId = 0;
        } else {
            WorkingNote workingNote3 = this.mWorkingNote;
            Intrinsics.checkNotNull(workingNote3);
            themeId = workingNote3.getThemeId();
        }
        int i = themeId;
        WorkingNote workingNote4 = this.mWorkingNote;
        Intrinsics.checkNotNull(workingNote4);
        long modifiedDate = workingNote4.getModifiedDate();
        WebRichEditor webRichEditor = this.mEditor;
        Intrinsics.checkNotNull(webRichEditor);
        SnsEditActivity.launchActivity(activityResultLauncher, baseActivity, content, title, type, i, modifiedDate, webRichEditor.getMFontTextSize(), requestCode, imgBase64, this.mWorkingNote, aigcPicMetaData);
    }

    @Override // com.miui.notes.basefeature.noteedit.BaseNormalNoteEditFragment
    protected void hideNote() {
        super.hideNote();
        WebRichEditor webRichEditor = this.mEditor;
        Intrinsics.checkNotNull(webRichEditor);
        WorkingNote workingNote = this.mWorkingNote;
        Intrinsics.checkNotNull(workingNote);
        webRichEditor.updateExtraInto(workingNote.getExtraInfo());
    }

    @Override // com.miui.notes.basefeature.noteedit.BaseNormalNoteEditFragment
    protected void unhideNote() {
        super.unhideNote();
        WebRichEditor webRichEditor = this.mEditor;
        Intrinsics.checkNotNull(webRichEditor);
        WorkingNote workingNote = this.mWorkingNote;
        Intrinsics.checkNotNull(workingNote);
        webRichEditor.updateExtraInto(workingNote.getExtraInfo());
    }

    @Deprecated(message = "")
    public final void invalidCurrentNote(boolean forceSave) {
        if (forceSave) {
            saveNote(true);
        }
        if (this.mWorkingNote != null) {
            WorkingNote workingNote = this.mWorkingNote;
            Intrinsics.checkNotNull(workingNote);
            workingNote.invalid();
        }
    }

    @Override // com.miui.notes.basefeature.noteedit.BaseNormalNoteEditFragment
    public boolean insertImageFromClipData(ClipData clipData, boolean insertAtEnd) {
        Intrinsics.checkNotNullParameter(clipData, "clipData");
        return ClipDataHelper.getImageUrisFromClipDataInFragment(this, clipData, new ClipDataHelper.UriGetCallback() { // from class: com.miui.notes.basefeature.noteedit.BaseHybridNoteEditFragment.insertImageFromClipData.1
            @Override // com.miui.notes.tool.ClipDataHelper.UriGetCallback
            public void onException(String reason) {
            }

            @Override // com.miui.notes.tool.ClipDataHelper.UriGetCallback
            public void onGetUri(ArrayList<Uri> imageUris) {
                if (imageUris == null || imageUris.isEmpty()) {
                    return;
                }
                int size = imageUris.size();
                Uri[] uriArr = new Uri[size];
                imageUris.toArray(uriArr);
                BaseHybridNoteEditFragment.this.insertImages(false, (Uri[]) Arrays.copyOf(uriArr, size));
            }
        });
    }

    @Override // com.miui.notes.basefeature.noteedit.BaseNormalNoteEditFragment
    public boolean insertImageFromDragEvent(DragEvent event, boolean insertAtEnd) {
        Intrinsics.checkNotNullParameter(event, "event");
        return ClipDataHelper.getImageUrisFromDragEventInFragment(this, event, new ClipDataHelper.UriGetCallback() { // from class: com.miui.notes.basefeature.noteedit.BaseHybridNoteEditFragment.insertImageFromDragEvent.1
            @Override // com.miui.notes.tool.ClipDataHelper.UriGetCallback
            public void onException(String reason) {
            }

            @Override // com.miui.notes.tool.ClipDataHelper.UriGetCallback
            public void onGetUri(ArrayList<Uri> imageUris) {
                if (imageUris == null || imageUris.isEmpty()) {
                    return;
                }
                int size = imageUris.size();
                Uri[] uriArr = new Uri[size];
                imageUris.toArray(uriArr);
                BaseHybridNoteEditFragment.this.insertImages(true, (Uri[]) Arrays.copyOf(uriArr, size));
            }
        });
    }

    @Override // com.miui.notes.basefeature.noteedit.BaseNormalNoteEditFragment, androidx.fragment.app.Fragment
    public void onSaveInstanceState(Bundle outState) {
        Intrinsics.checkNotNullParameter(outState, "outState");
        Logger.INSTANCE.i(TAG, "onSaveInstanceState ");
        super.onSaveInstanceState(outState);
        String str = this.mDoodleFileId;
        if (str != null) {
            outState.putString("mDoodleFileId", str);
        }
        if (this.mWorkingNote != null) {
            WorkingNote workingNote = this.mWorkingNote;
            Intrinsics.checkNotNull(workingNote);
            if (workingNote.isEmpty()) {
                return;
            }
            saveNoteInstantlyIfNeeded();
        }
    }

    @Override // com.miui.notes.basefeature.noteedit.BaseNormalNoteEditFragment
    public void onDragDropFromBlankView(DragEvent event) {
        Intrinsics.checkNotNullParameter(event, "event");
        ClipDataHelper.getImageUrisFromDragEventInFragment(this, event, new ClipDataHelper.UriGetCallback() { // from class: com.miui.notes.basefeature.noteedit.BaseHybridNoteEditFragment.onDragDropFromBlankView.1
            @Override // com.miui.notes.tool.ClipDataHelper.UriGetCallback
            public void onException(String reason) {
            }

            @Override // com.miui.notes.tool.ClipDataHelper.UriGetCallback
            public void onGetUri(ArrayList<Uri> imageUris) throws Throwable {
                if (imageUris == null || imageUris.isEmpty()) {
                    return;
                }
                int size = imageUris.size();
                Uri[] uriArr = new Uri[size];
                imageUris.toArray(uriArr);
                NoteSchema.SimpleBuilder simpleBuilder = new NoteSchema.SimpleBuilder(BaseHybridNoteEditFragment.this.getContext());
                for (int i = 0; i < size; i++) {
                    FragmentActivity fragmentActivityRequireActivity = BaseHybridNoteEditFragment.this.requireActivity();
                    Intrinsics.checkNotNullExpressionValue(fragmentActivityRequireActivity, "requireActivity(...)");
                    Uri uri = uriArr[i];
                    Intrinsics.checkNotNull(uri);
                    String strSaveImageFile = AttachmentUtils.saveImageFile((Context) fragmentActivityRequireActivity, uri, true);
                    if (strSaveImageFile != null) {
                        if (!simpleBuilder.isEmpty()) {
                            simpleBuilder.appendNewLine();
                        }
                        simpleBuilder.appendImage(strSaveImageFile);
                        simpleBuilder.appendNewLine();
                    }
                }
                long j = BaseHybridNoteEditFragment.this.mFolderId;
                WorkingNote workingNoteCreateEmptyNote = WorkingNote.INSTANCE.createEmptyNote((j == -4097 || j < 0) ? 0L : j, 0, -1, ResourceParser.getDefaultBgId(BaseHybridNoteEditFragment.this.getActivity()));
                String ancient = simpleBuilder.toAncient();
                if (ancient.length() > 20000) {
                    Intrinsics.checkNotNull(ancient);
                    ancient = ancient.substring(0, 20000);
                    Intrinsics.checkNotNullExpressionValue(ancient, "substring(...)");
                }
                workingNoteCreateEmptyNote.setWorkingText(ancient);
                workingNoteCreateEmptyNote.handleSent();
                workingNoteCreateEmptyNote.saveNote(true);
            }
        });
    }

    /* JADX INFO: compiled from: BaseHybridNoteEditFragment.kt */
    @Metadata(d1 = {"\u0000$\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\b\u0086\u0004\u0018\u00002\u00060\u0001R\u00020\u0002B\u0011\u0012\b\u0010\u0003\u001a\u0004\u0018\u00010\u0004¢\u0006\u0004\b\u0005\u0010\u0006J\b\u0010\u0007\u001a\u00020\bH\u0016J\u0012\u0010\t\u001a\u00020\b2\b\u0010\n\u001a\u0004\u0018\u00010\u000bH\u0016¨\u0006\f"}, d2 = {"Lcom/miui/notes/basefeature/noteedit/BaseHybridNoteEditFragment$HybridEditNoteLoadTask;", "Lcom/miui/notes/basefeature/noteedit/BaseNormalNoteEditFragment$EditNoteLoadTask;", "Lcom/miui/notes/basefeature/noteedit/BaseNormalNoteEditFragment;", "intent", "Landroid/content/Intent;", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "(Lcom/miui/notes/basefeature/noteedit/BaseHybridNoteEditFragment;Landroid/content/Intent;)V", "onInsertAudio", "", "onEnterEdit", "workingNote", "Lcom/miui/notes/model/WorkingNote;", "app_PhoneGlobalRelease"}, k = 1, mv = {2, 2, 0}, xi = 48)
    public final class HybridEditNoteLoadTask extends BaseNormalNoteEditFragment.EditNoteLoadTask {
        public HybridEditNoteLoadTask(Intent intent) {
            super(intent);
        }

        @Override // com.miui.notes.basefeature.noteedit.BaseNormalNoteEditFragment.EditNoteLoadTask
        public void onInsertAudio() {
            BaseHybridNoteEditFragment.this.startRecordWithCheckFromEditTask();
        }

        @Override // com.miui.notes.basefeature.noteedit.BaseNormalNoteEditFragment.EditNoteLoadTask
        public void onEnterEdit(WorkingNote workingNote) {
            Logger.INSTANCE.d(BaseHybridNoteEditFragment.TAG, "HybridEditNoteLoadTask/onEnterEdit");
            if (Intrinsics.areEqual(NoteIntent.TYPE_ACTION_SHOW_AI_TEXT_WIDGET, this.intent.getType())) {
                if (BaseHybridNoteEditFragment.this.getContext() != null && RomUtils.isSupportAiText(BaseHybridNoteEditFragment.this.getContext())) {
                    Logger.INSTANCE.d(BaseHybridNoteEditFragment.TAG, "HybridEditNoteLoadTask/onEnterEdit startAiTextWidget");
                    BaseHybridNoteEditFragment.this.startAiTextWidget();
                    if (com.miui.notes.tool.PreferenceUtils.isAiCTAAccepted(BaseHybridNoteEditFragment.this.getContext())) {
                        if (BaseHybridNoteEditFragment.this.mHandler != null) {
                            Handler handler = BaseHybridNoteEditFragment.this.mHandler;
                            final BaseHybridNoteEditFragment baseHybridNoteEditFragment = BaseHybridNoteEditFragment.this;
                            handler.postDelayed(new Runnable() { // from class: com.miui.notes.basefeature.noteedit.BaseHybridNoteEditFragment$HybridEditNoteLoadTask$$ExternalSyntheticLambda0
                                @Override // java.lang.Runnable
                                public final void run() {
                                    BaseHybridNoteEditFragment.HybridEditNoteLoadTask.onEnterEdit$lambda$0(baseHybridNoteEditFragment);
                                }
                            }, 150L);
                            return;
                        } else {
                            Logger.INSTANCE.w(BaseHybridNoteEditFragment.TAG, "HybridEditNoteLoadTask/onEnterEdit mHandler is null, clearAiTextWidgetIntentType immediately");
                            BaseHybridNoteEditFragment.this.clearAiTextWidgetIntentType();
                            return;
                        }
                    }
                    return;
                }
                BaseHybridNoteEditFragment.this.clearAiTextWidgetIntentType();
            }
            if (this.intent.getExtras() != null) {
                Bundle extras = this.intent.getExtras();
                Intrinsics.checkNotNull(extras);
                EditorActionMode editorActionMode = (EditorActionMode) extras.get(NoteIntent.KEY_ACTION_MODE);
                if (editorActionMode != null && editorActionMode == EditorActionMode.EDIT_RECODE) {
                    Logger.INSTANCE.d(BaseHybridNoteEditFragment.TAG, "onEnterEdit:" + workingNote + ",edit_recode");
                    BaseHybridNoteEditFragment.this.updateActionMode(EditorActionMode.EDIT_RECODE, true);
                } else if (BaseHybridNoteEditFragment.this.mEditorWebView != null) {
                    RichEditorWebView richEditorWebView = BaseHybridNoteEditFragment.this.mEditorWebView;
                    Intrinsics.checkNotNull(richEditorWebView);
                    richEditorWebView.requestFocus();
                }
                String stringExtra = this.intent.getStringExtra(NoteIntent.EXTRA_INSERT_TITLE_NOTE);
                if (!TextUtils.isEmpty(stringExtra)) {
                    WebRichEditor webRichEditor = BaseHybridNoteEditFragment.this.mEditor;
                    Intrinsics.checkNotNull(webRichEditor);
                    webRichEditor.setTitle(stringExtra);
                }
                AiActionUtil.asyncNotifyResult(0);
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static final void onEnterEdit$lambda$0(BaseHybridNoteEditFragment baseHybridNoteEditFragment) {
            Logger.INSTANCE.d(BaseHybridNoteEditFragment.TAG, "HybridEditNoteLoadTask/delayed clearAiTextWidgetIntentType");
            baseHybridNoteEditFragment.clearAiTextWidgetIntentType();
        }
    }

    protected final void clearAiTextWidgetIntentType() {
        Intent intent;
        FragmentActivity activity = getActivity();
        if (activity != null && (intent = activity.getIntent()) != null && Intrinsics.areEqual(NoteIntent.TYPE_ACTION_SHOW_AI_TEXT_WIDGET, intent.getType())) {
            intent.setType(null);
        }
        Intent intent2 = this.mPendingHandleIntent;
        if (intent2 == null || !Intrinsics.areEqual(NoteIntent.TYPE_ACTION_SHOW_AI_TEXT_WIDGET, intent2.getType())) {
            return;
        }
        intent2.setType(null);
    }

    @Override // com.miui.notes.basefeature.noteedit.BaseNormalNoteEditFragment
    @Deprecated(message = "")
    protected void handleAudioCTARequest() {
        if (!CTAManager.getInstance().isAccepted(getContext())) {
            Logger.INSTANCE.d(TAG, "handleAudioCTARequest/cta not accepted");
            FragmentActivity activity = getActivity();
            if (activity instanceof CTAActivity) {
                ((CTAActivity) activity).showCtaDialog();
                CTAManager.getInstance().addListener(new CTAManager.CTAListener() { // from class: com.miui.notes.basefeature.noteedit.BaseHybridNoteEditFragment.handleAudioCTARequest.1
                    @Override // com.miui.notes.cta.CTAManager.CTAListener
                    public void onAccept() {
                        BaseHybridNoteEditFragment.this.updateRecordMode();
                    }

                    @Override // com.miui.notes.cta.CTAManager.CTAListener
                    public void onReject() {
                        Logger.INSTANCE.w(BaseHybridNoteEditFragment.TAG, "user deny to authorization");
                    }
                });
                return;
            } else {
                Logger.INSTANCE.e(TAG, "get Activity is not instanceof CTAActivity");
                return;
            }
        }
        updateRecordMode();
    }

    public final void doPaste() {
        this.mHandler.post(new Runnable() { // from class: com.miui.notes.basefeature.noteedit.BaseHybridNoteEditFragment$$ExternalSyntheticLambda15
            @Override // java.lang.Runnable
            public final void run() {
                BaseHybridNoteEditFragment.doPaste$lambda$48(this.f$0);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void doPaste$lambda$48(BaseHybridNoteEditFragment baseHybridNoteEditFragment) {
        RichEditorWebView richEditorWebView = baseHybridNoteEditFragment.mEditorWebView;
        if (richEditorWebView != null) {
            Intrinsics.checkNotNull(richEditorWebView);
            richEditorWebView.doPaste();
        }
    }

    public final void replaceText(String text) {
        RichEditorWebView richEditorWebView = this.mEditorWebView;
        if (richEditorWebView != null) {
            Intrinsics.checkNotNull(richEditorWebView);
            richEditorWebView.replaceText(text);
        }
    }

    public final void insertText(String text) {
        RichEditorWebView richEditorWebView = this.mEditorWebView;
        if (richEditorWebView != null) {
            Intrinsics.checkNotNull(richEditorWebView);
            richEditorWebView.insertText(text);
        }
    }

    public final void handleGetAiTextInfo(AIInputResultListener aiInputResultListener, String contentName) throws RemoteException {
        Intrinsics.checkNotNullParameter(aiInputResultListener, "aiInputResultListener");
        RichEditorWebView richEditorWebView = this.mEditorWebView;
        if (richEditorWebView != null) {
            Intrinsics.checkNotNull(richEditorWebView);
            richEditorWebView.handleGetAiTextInfo(aiInputResultListener, contentName);
        }
    }

    @Override // com.miui.notes.detail.BaseNoteDetailFragment
    public void showSoftInput() {
        super.showSoftInput();
        if (!this.mIsEditNotes || this.mHandler == null || this.mRootView == null) {
            return;
        }
        this.mHandler.postDelayed(new Runnable() { // from class: com.miui.notes.basefeature.noteedit.BaseHybridNoteEditFragment.showSoftInput.1
            @Override // java.lang.Runnable
            public void run() {
                View mRootView = BaseHybridNoteEditFragment.this.mRootView;
                Intrinsics.checkNotNullExpressionValue(mRootView, "mRootView");
                Utils.showSoftInput(mRootView);
            }
        }, 200L);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public final void showAiTextWindow() {
        Logger.INSTANCE.d(TAG, "showAiTextWindow-web register js bridge");
        RichEditorWebView richEditorWebView = this.mEditorWebView;
        Intrinsics.checkNotNull(richEditorWebView);
        richEditorWebView.evaluateJavascript("javascript:getSelectionSavedData()", new ValueCallback() { // from class: com.miui.notes.basefeature.noteedit.BaseHybridNoteEditFragment$$ExternalSyntheticLambda6
            @Override // android.webkit.ValueCallback
            public final void onReceiveValue(Object obj) {
                BaseHybridNoteEditFragment.showAiTextWindow$lambda$50(this.f$0, (String) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void showAiTextWindow$lambda$50(BaseHybridNoteEditFragment baseHybridNoteEditFragment, String str) {
        baseHybridNoteEditFragment.mAITextUseValue = str == null ? "" : (String) new Gson().fromJson(str, String.class);
        Logger logger = Logger.INSTANCE;
        String str2 = baseHybridNoteEditFragment.mAITextUseValue;
        Intrinsics.checkNotNull(str2);
        logger.i(TAG, "showAiTextWindow onReceiveValue: " + str2.length() + ",inTitle:" + IN_TITLE + ",act:" + baseHybridNoteEditFragment.getActivity());
        FragmentActivity activity = baseHybridNoteEditFragment.getActivity();
        if (activity == null) {
            return;
        }
        RichEditorWebView richEditorWebView = baseHybridNoteEditFragment.mEditorWebView;
        Intrinsics.checkNotNull(richEditorWebView);
        String mSelectData = richEditorWebView.getMSelectData();
        if (!IN_TITLE) {
            if (TextUtils.isEmpty(baseHybridNoteEditFragment.mAITextUseValue)) {
                WorkingNote workingNote = baseHybridNoteEditFragment.mWorkingNote;
                Intrinsics.checkNotNull(workingNote);
                if (!workingNote.isEmpty()) {
                    WorkingNote workingNote2 = baseHybridNoteEditFragment.mWorkingNote;
                    Intrinsics.checkNotNull(workingNote2);
                    String strExtractPlainText = NoteSchemaUtil.extractPlainText(NoteSchema.convertToWeb(workingNote2.getContent()));
                    Intrinsics.checkNotNullExpressionValue(strExtractPlainText, "extractPlainText(...)");
                    String str3 = strExtractPlainText;
                    int length = str3.length() - 1;
                    int i = 0;
                    boolean z = false;
                    while (i <= length) {
                        boolean z2 = Intrinsics.compare((int) str3.charAt(!z ? i : length), 32) <= 0;
                        if (z) {
                            if (!z2) {
                                break;
                            } else {
                                length--;
                            }
                        } else if (z2) {
                            i++;
                        } else {
                            z = true;
                        }
                    }
                    if (!TextUtils.isEmpty(str3.subSequence(i, length + 1).toString())) {
                        WorkingNote workingNote3 = baseHybridNoteEditFragment.mWorkingNote;
                        Intrinsics.checkNotNull(workingNote3);
                        baseHybridNoteEditFragment.mAITextUseValue = NoteSchema.convertToWeb(workingNote3.getContent());
                        baseHybridNoteEditFragment.mNeedSelectAll = true;
                    }
                }
            }
            Logger.INSTANCE.i(TAG, "onReceiveValue mNeedSelectAll: " + baseHybridNoteEditFragment.mNeedSelectAll);
        } else if (mSelectData != null) {
            baseHybridNoteEditFragment.mAITextUseValue = mSelectData;
            Logger logger2 = Logger.INSTANCE;
            String str4 = baseHybridNoteEditFragment.mAITextUseValue;
            Intrinsics.checkNotNull(str4);
            logger2.i(TAG, "showAiTextWindow title: " + str4.length());
        }
        WorkingNote workingNote4 = baseHybridNoteEditFragment.mWorkingNote;
        Intrinsics.checkNotNull(workingNote4);
        baseHybridNoteEditFragment.mOriginalId = workingNote4.getNoteId();
        WorkingNote workingNote5 = baseHybridNoteEditFragment.mWorkingNote;
        Intrinsics.checkNotNull(workingNote5);
        baseHybridNoteEditFragment.mOriginalContent = workingNote5.getContent();
        WorkingNote workingNote6 = baseHybridNoteEditFragment.mWorkingNote;
        Intrinsics.checkNotNull(workingNote6);
        baseHybridNoteEditFragment.mOriginalTitle = workingNote6.getTitle();
        WorkingNote workingNote7 = baseHybridNoteEditFragment.mWorkingNote;
        Intrinsics.checkNotNull(workingNote7);
        baseHybridNoteEditFragment.mAiData = workingNote7.getContent();
        baseHybridNoteEditFragment.initFloatingWindow();
        Rect rect = new Rect();
        activity.getWindow().getDecorView().getWindowVisibleDisplayFrame(rect);
        baseHybridNoteEditFragment.mAiTextUseRect = rect;
        baseHybridNoteEditFragment.processCta(activity);
    }

    private final String getContentStatus() {
        if (TextUtils.isEmpty(this.mOriginalTitle) && TextUtils.isEmpty(this.mOriginalContent)) {
            return AITextWidgetStat.EMPTY_STATUS;
        }
        if (TextUtils.isEmpty(this.mOriginalTitle)) {
            return AITextWidgetStat.NO_TITLE_CONTENT;
        }
        if (TextUtils.isEmpty(this.mOriginalContent)) {
            return AITextWidgetStat.TITLE_NO_CONTENT;
        }
        return AITextWidgetStat.TITLE_AND_CONTENT;
    }

    public final String getNoteContentStatus() {
        if (this.mWorkingNote == null) {
            return AITextWidgetStat.EMPTY_STATUS;
        }
        WorkingNote workingNote = this.mWorkingNote;
        String title = workingNote != null ? workingNote.getTitle() : null;
        if (title == null || title.length() == 0) {
            return AITextWidgetStat.NO_TITLE_CONTENT;
        }
        WorkingNote workingNote2 = this.mWorkingNote;
        String content = workingNote2 != null ? workingNote2.getContent() : null;
        if (content == null || content.length() == 0) {
            return AITextWidgetStat.TITLE_NO_CONTENT;
        }
        return AITextWidgetStat.TITLE_AND_CONTENT;
    }

    /* JADX WARN: Code duplicated, block: B:19:0x0072  */
    protected void initFloatingWindow() {
        int i;
        Logger.INSTANCE.d(TAG, "initFloatingWindow");
        NoteAiFloatingWindow noteAiFloatingWindow = mNoteAiFloatingWindow;
        if (noteAiFloatingWindow != null) {
            Intrinsics.checkNotNull(noteAiFloatingWindow);
            if (noteAiFloatingWindow.isShowing()) {
                NoteAiFloatingWindow noteAiFloatingWindow2 = mNoteAiFloatingWindow;
                Intrinsics.checkNotNull(noteAiFloatingWindow2);
                noteAiFloatingWindow2.closeWindow();
                return;
            }
        }
        if (mNoteAiFloatingWindow == null) {
            if (mNoteAiTextWidgetService != null) {
                mNoteAiFloatingWindow = new NoteAiFloatingWindow(ContextWrapperUtil.wrapperWindowContext(mNoteAiTextWidgetService), this, mNoteAiTextWidgetService);
            } else {
                mNoteAiFloatingWindow = new NoteAiFloatingWindow(requireContext(), this, null);
            }
            NoteAiFloatingWindow noteAiFloatingWindow3 = mNoteAiFloatingWindow;
            Intrinsics.checkNotNull(noteAiFloatingWindow3);
            noteAiFloatingWindow3.setInTitle(IN_TITLE);
            if (this.mWorkingNote != null) {
                WorkingNote workingNote = this.mWorkingNote;
                Intrinsics.checkNotNull(workingNote);
                if (workingNote.getExtraInfo() != null) {
                    WorkingNote workingNote2 = this.mWorkingNote;
                    Intrinsics.checkNotNull(workingNote2);
                    i = workingNote2.getExtraInfo().textCount;
                } else {
                    i = 0;
                }
            } else {
                i = 0;
            }
            Logger.INSTANCE.i(TAG, "initFloatingWindow textCount: " + i);
            NoteAiFloatingWindow noteAiFloatingWindow4 = mNoteAiFloatingWindow;
            Intrinsics.checkNotNull(noteAiFloatingWindow4);
            noteAiFloatingWindow4.setNoteWordCount(i);
            NoteAiFloatingWindow noteAiFloatingWindow5 = mNoteAiFloatingWindow;
            Intrinsics.checkNotNull(noteAiFloatingWindow5);
            noteAiFloatingWindow5.setWindowCloseListener(new NoteWindowCloseListener(this));
            NoteAiFloatingWindow noteAiFloatingWindow6 = mNoteAiFloatingWindow;
            Intrinsics.checkNotNull(noteAiFloatingWindow6);
            noteAiFloatingWindow6.setAiActionListener(new NoteAiFloatingWindow.IAiActionListener() { // from class: com.miui.notes.basefeature.noteedit.BaseHybridNoteEditFragment.initFloatingWindow.1
                @Override // com.miui.notes.ai.NoteAiFloatingWindow.IAiActionListener
                public void onCancelAiAction(boolean detach, boolean isDelete) {
                    String content;
                    Logger.INSTANCE.i(BaseHybridNoteEditFragment.TAG, "onCancelAiAction detach:" + detach + " , isDelete: " + isDelete);
                    if (!Intrinsics.areEqual(BaseHybridNoteEditFragment.this.getMAiData(), BaseHybridNoteEditFragment.this.getMOriginalContent()) && !isDelete) {
                        WebRichEditor webRichEditor = BaseHybridNoteEditFragment.this.mEditor;
                        Intrinsics.checkNotNull(webRichEditor);
                        webRichEditor.restoreToSavedScrollPosition();
                        WebRichEditor webRichEditor2 = BaseHybridNoteEditFragment.this.mEditor;
                        Intrinsics.checkNotNull(webRichEditor2);
                        webRichEditor2.queuedUndo2BeforeSnapshot();
                        BaseHybridNoteEditFragment baseHybridNoteEditFragment = BaseHybridNoteEditFragment.this;
                        baseHybridNoteEditFragment.setMAiData(baseHybridNoteEditFragment.getMOriginalContent());
                    }
                    WebRichEditor webRichEditor3 = BaseHybridNoteEditFragment.this.mEditor;
                    Intrinsics.checkNotNull(webRichEditor3);
                    webRichEditor3.setTitle(TextUtils.isEmpty(BaseHybridNoteEditFragment.this.getMOriginalTitle()) ? "" : BaseHybridNoteEditFragment.this.getMOriginalTitle());
                    BaseHybridNoteEditFragment baseHybridNoteEditFragment2 = BaseHybridNoteEditFragment.this;
                    baseHybridNoteEditFragment2.onTitleChanged(TextUtils.isEmpty(baseHybridNoteEditFragment2.getMOriginalTitle()) ? "" : BaseHybridNoteEditFragment.this.getMOriginalTitle());
                    if (!detach) {
                        if (BaseHybridNoteEditFragment.this.mWorkingNote != null) {
                            WorkingNote workingNote3 = BaseHybridNoteEditFragment.this.mWorkingNote;
                            Intrinsics.checkNotNull(workingNote3);
                            content = workingNote3.getContent();
                        } else {
                            content = "";
                        }
                        if (!TextUtils.isEmpty(content)) {
                            WebRichEditor webRichEditor4 = BaseHybridNoteEditFragment.this.mEditor;
                            Intrinsics.checkNotNull(webRichEditor4);
                            webRichEditor4.drawSelection();
                        }
                    }
                    BaseHybridNoteEditFragment.this.closeCorrectionPop();
                    BaseHybridNoteEditFragment.this.showWaterMark(false);
                    Logger.INSTANCE.i(BaseHybridNoteEditFragment.TAG, "onCancelAiAction isEmpty:" + TextUtils.isEmpty(BaseHybridNoteEditFragment.this.mAITextUseValue));
                    if (TextUtils.isEmpty(BaseHybridNoteEditFragment.this.mAITextUseValue)) {
                        WebRichEditor webRichEditor5 = BaseHybridNoteEditFragment.this.mEditor;
                        Intrinsics.checkNotNull(webRichEditor5);
                        webRichEditor5.removeSelectionColorBg();
                    }
                    WebRichEditor webRichEditor6 = BaseHybridNoteEditFragment.this.mEditor;
                    Intrinsics.checkNotNull(webRichEditor6);
                    String mOriginalContent = BaseHybridNoteEditFragment.this.getMOriginalContent() != null ? BaseHybridNoteEditFragment.this.getMOriginalContent() : "";
                    Intrinsics.checkNotNull(mOriginalContent);
                    webRichEditor6.resetRichTextData(mOriginalContent);
                    WebRichEditor webRichEditor7 = BaseHybridNoteEditFragment.this.mEditor;
                    Intrinsics.checkNotNull(webRichEditor7);
                    webRichEditor7.updateQueryContent(BaseHybridNoteEditFragment.this.mSearchText, BaseHybridNoteEditFragment.this.mHighLightKeyWords);
                }

                @Override // com.miui.notes.ai.NoteAiFloatingWindow.IAiActionListener
                public void onRefreshAiAction() {
                    String content;
                    Logger logger = Logger.INSTANCE;
                    String content2 = "";
                    if (BaseHybridNoteEditFragment.this.mWorkingNote != null) {
                        WorkingNote workingNote3 = BaseHybridNoteEditFragment.this.mWorkingNote;
                        Intrinsics.checkNotNull(workingNote3);
                        content = workingNote3.getContent();
                    } else {
                        content = "";
                    }
                    logger.i(BaseHybridNoteEditFragment.TAG, "onRefreshAiAction WorkingNote is empty :" + TextUtils.isEmpty(content));
                    WebRichEditor webRichEditor = BaseHybridNoteEditFragment.this.mEditor;
                    Intrinsics.checkNotNull(webRichEditor);
                    String mOriginalContent = BaseHybridNoteEditFragment.this.getMOriginalContent();
                    Intrinsics.checkNotNull(mOriginalContent);
                    webRichEditor.setDataWithConfig(mOriginalContent, BaseHybridNoteEditFragment.this.getMOriginalTitle() == null ? "" : BaseHybridNoteEditFragment.this.getMOriginalTitle(), "", Boolean.valueOf(TextUtils.isEmpty(BaseHybridNoteEditFragment.this.getMOriginalContent()) && TextUtils.isEmpty(BaseHybridNoteEditFragment.this.getMOriginalTitle())));
                    WebRichEditor webRichEditor2 = BaseHybridNoteEditFragment.this.mEditor;
                    Intrinsics.checkNotNull(webRichEditor2);
                    webRichEditor2.setTitle(BaseHybridNoteEditFragment.this.getMOriginalTitle() == null ? "" : BaseHybridNoteEditFragment.this.getMOriginalTitle());
                    if (BaseHybridNoteEditFragment.this.mWorkingNote != null) {
                        WorkingNote workingNote4 = BaseHybridNoteEditFragment.this.mWorkingNote;
                        Intrinsics.checkNotNull(workingNote4);
                        content2 = workingNote4.getContent();
                    }
                    if (!TextUtils.isEmpty(content2)) {
                        WebRichEditor webRichEditor3 = BaseHybridNoteEditFragment.this.mEditor;
                        Intrinsics.checkNotNull(webRichEditor3);
                        webRichEditor3.drawRefreshSelection();
                    }
                    if (TextUtils.isEmpty(BaseHybridNoteEditFragment.this.mAITextUseValue)) {
                        WebRichEditor webRichEditor4 = BaseHybridNoteEditFragment.this.mEditor;
                        Intrinsics.checkNotNull(webRichEditor4);
                        webRichEditor4.removeSelectionColorBg();
                    }
                }

                @Override // com.miui.notes.ai.NoteAiFloatingWindow.IAiActionListener
                public Rect getNoteSize() {
                    if (BaseHybridNoteEditFragment.this.getActivity() == null || BaseHybridNoteEditFragment.this.requireActivity().getWindow() == null || BaseHybridNoteEditFragment.this.requireActivity().getWindow().getDecorView() == null) {
                        return null;
                    }
                    Rect rect = new Rect();
                    BaseHybridNoteEditFragment.this.requireActivity().getWindow().getDecorView().getWindowVisibleDisplayFrame(rect);
                    return rect;
                }

                /* JADX WARN: Code duplicated, block: B:12:0x0076  */
                @Override // com.miui.notes.ai.NoteAiFloatingWindow.IAiActionListener
                public void onPanelPositionChanged(Rect rect) {
                    Intrinsics.checkNotNullParameter(rect, "rect");
                    int[] iArr = new int[2];
                    RichEditorWebView richEditorWebView = BaseHybridNoteEditFragment.this.mEditorWebView;
                    Intrinsics.checkNotNull(richEditorWebView);
                    richEditorWebView.getLocationOnScreen(iArr);
                    int i2 = iArr[0];
                    int i3 = iArr[1];
                    RichEditorWebView richEditorWebView2 = BaseHybridNoteEditFragment.this.mEditorWebView;
                    Intrinsics.checkNotNull(richEditorWebView2);
                    int width = richEditorWebView2.getWidth() + i2;
                    int i4 = iArr[1];
                    RichEditorWebView richEditorWebView3 = BaseHybridNoteEditFragment.this.mEditorWebView;
                    Intrinsics.checkNotNull(richEditorWebView3);
                    int i5 = (BaseHybridNoteEditFragment.this.getActivity() == null || FreeModeUtils.isFreeFormScreen(BaseHybridNoteEditFragment.this.requireActivity().getResources().getConfiguration())) ? -1 : rect.top - new Rect(i2, i3, width, i4 + richEditorWebView3.getHeight()).top;
                    if (i5 != -1) {
                        RichEditorWebView richEditorWebView4 = BaseHybridNoteEditFragment.this.mEditorWebView;
                        Intrinsics.checkNotNull(richEditorWebView4);
                        if (i5 < richEditorWebView4.getHeight()) {
                            WebRichEditor webRichEditor = BaseHybridNoteEditFragment.this.mEditor;
                            Intrinsics.checkNotNull(webRichEditor);
                            webRichEditor.changePanelTop(i5, false);
                        } else {
                            WebRichEditor webRichEditor2 = BaseHybridNoteEditFragment.this.mEditor;
                            Intrinsics.checkNotNull(webRichEditor2);
                            RichEditorWebView richEditorWebView5 = BaseHybridNoteEditFragment.this.mEditorWebView;
                            Intrinsics.checkNotNull(richEditorWebView5);
                            webRichEditor2.changePanelTop(richEditorWebView5.getHeight(), false);
                        }
                    } else {
                        WebRichEditor webRichEditor3 = BaseHybridNoteEditFragment.this.mEditor;
                        Intrinsics.checkNotNull(webRichEditor3);
                        RichEditorWebView richEditorWebView6 = BaseHybridNoteEditFragment.this.mEditorWebView;
                        Intrinsics.checkNotNull(richEditorWebView6);
                        webRichEditor3.changePanelTop(richEditorWebView6.getHeight(), false);
                    }
                    Logger logger = Logger.INSTANCE;
                    RichEditorWebView richEditorWebView7 = BaseHybridNoteEditFragment.this.mEditorWebView;
                    Intrinsics.checkNotNull(richEditorWebView7);
                    logger.i(BaseHybridNoteEditFragment.TAG, "change height: " + richEditorWebView7.getHeight() + ", to height: " + i5);
                }

                @Override // com.miui.notes.ai.NoteAiFloatingWindow.IAiActionListener
                public void selectAll() {
                    if (BaseHybridNoteEditFragment.this.mNeedSelectAll) {
                        Logger.INSTANCE.i(BaseHybridNoteEditFragment.TAG, "need selectAll");
                        WebRichEditor webRichEditor = BaseHybridNoteEditFragment.this.mEditor;
                        Intrinsics.checkNotNull(webRichEditor);
                        webRichEditor.selectAllText();
                        WebRichEditor webRichEditor2 = BaseHybridNoteEditFragment.this.mEditor;
                        Intrinsics.checkNotNull(webRichEditor2);
                        webRichEditor2.drawSelectionFirst();
                        BaseHybridNoteEditFragment.this.mNeedSelectAll = false;
                        BaseHybridNoteEditFragment baseHybridNoteEditFragment = BaseHybridNoteEditFragment.this;
                        baseHybridNoteEditFragment.trackReportClipBoard(baseHybridNoteEditFragment.mWorkingNote, NotesTextStat.SELECT_ALL);
                    }
                }

                @Override // com.miui.notes.ai.NoteAiFloatingWindow.IAiActionListener
                public void showSelectionEnhanced() {
                    BaseHybridNoteEditFragment.this.setMNotSaveData(true);
                    WebRichEditor webRichEditor = BaseHybridNoteEditFragment.this.mEditor;
                    Intrinsics.checkNotNull(webRichEditor);
                    webRichEditor.showSelectionEnhanced();
                }

                @Override // com.miui.notes.ai.NoteAiFloatingWindow.IAiActionListener
                public String getNotesContent() {
                    if (!BaseHybridNoteEditFragment.this.getMNotSaveData() || BaseHybridNoteEditFragment.this.getMAiData() == null) {
                        if (BaseHybridNoteEditFragment.this.mWorkingNote != null) {
                            WorkingNote workingNote3 = BaseHybridNoteEditFragment.this.mWorkingNote;
                            Intrinsics.checkNotNull(workingNote3);
                            if (workingNote3.getContent() != null) {
                                WorkingNote workingNote4 = BaseHybridNoteEditFragment.this.mWorkingNote;
                                Intrinsics.checkNotNull(workingNote4);
                                String strConvertToWeb = NoteSchema.convertToWeb(workingNote4.getContent());
                                Intrinsics.checkNotNullExpressionValue(strConvertToWeb, "convertToWeb(...)");
                                return strConvertToWeb;
                            }
                            return "";
                        }
                        return "";
                    }
                    String strConvertToWeb2 = NoteSchema.convertToWeb(BaseHybridNoteEditFragment.this.getMAiData());
                    Intrinsics.checkNotNullExpressionValue(strConvertToWeb2, "convertToWeb(...)");
                    return strConvertToWeb2;
                }

                @Override // com.miui.notes.ai.NoteAiFloatingWindow.IAiActionListener
                public void onNotifyRefreshComplete(boolean saveData) {
                    if (saveData) {
                        BaseHybridNoteEditFragment.this.setMNotSaveData(false);
                    }
                    Logger.INSTANCE.i(BaseHybridNoteEditFragment.TAG, "onNotifyRefreshComplete mNotSaveData:" + BaseHybridNoteEditFragment.this.getMNotSaveData());
                }
            });
        }
    }

    /* JADX INFO: compiled from: BaseHybridNoteEditFragment.kt */
    @Metadata(d1 = {"\u0000\u001e\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\b\u0002\u0018\u00002\u00020\u0001B\u0011\u0012\b\u0010\u0002\u001a\u0004\u0018\u00010\u0003¢\u0006\u0004\b\u0004\u0010\u0005J\b\u0010\b\u001a\u00020\tH\u0016R\u0018\u0010\u0006\u001a\f\u0012\u0006\u0012\u0004\u0018\u00010\u0003\u0018\u00010\u0007X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\n"}, d2 = {"Lcom/miui/notes/basefeature/noteedit/BaseHybridNoteEditFragment$NoteWindowCloseListener;", "Lcom/miui/notes/ai/floatingwindow/AiFloatingWindow$WindowCloseListener;", "fragment", "Lcom/miui/notes/basefeature/noteedit/BaseHybridNoteEditFragment;", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "(Lcom/miui/notes/basefeature/noteedit/BaseHybridNoteEditFragment;)V", "mReference", "Ljava/lang/ref/WeakReference;", "onWindowClosed", "", "app_PhoneGlobalRelease"}, k = 1, mv = {2, 2, 0}, xi = 48)
    private static final class NoteWindowCloseListener implements AiFloatingWindow.WindowCloseListener {
        private final WeakReference<BaseHybridNoteEditFragment> mReference;

        public NoteWindowCloseListener(BaseHybridNoteEditFragment baseHybridNoteEditFragment) {
            this.mReference = new WeakReference<>(baseHybridNoteEditFragment);
        }

        @Override // com.miui.notes.ai.floatingwindow.AiFloatingWindow.WindowCloseListener
        public void onWindowClosed() {
            WeakReference<BaseHybridNoteEditFragment> weakReference = this.mReference;
            if (weakReference == null || weakReference.get() == null) {
                return;
            }
            BaseHybridNoteEditFragment baseHybridNoteEditFragment = this.mReference.get();
            Intrinsics.checkNotNull(baseHybridNoteEditFragment);
            baseHybridNoteEditFragment.onWindowClosed();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void onWindowClosed() {
        if (this.mEditor != null) {
            if (this.mHandler != null) {
                this.mHandler.postDelayed(new Runnable() { // from class: com.miui.notes.basefeature.noteedit.BaseHybridNoteEditFragment$$ExternalSyntheticLambda27
                    @Override // java.lang.Runnable
                    public final void run() {
                        BaseHybridNoteEditFragment.onWindowClosed$lambda$51(this.f$0);
                    }
                }, 2000L);
            }
            if (this.mEditorWebView != null) {
                WebRichEditor webRichEditor = this.mEditor;
                Intrinsics.checkNotNull(webRichEditor);
                RichEditorWebView richEditorWebView = this.mEditorWebView;
                Intrinsics.checkNotNull(richEditorWebView);
                webRichEditor.changePanelTop(richEditorWebView.getHeight(), true);
            } else {
                WebRichEditor webRichEditor2 = this.mEditor;
                Intrinsics.checkNotNull(webRichEditor2);
                webRichEditor2.changePanelTop(10000, true);
            }
        }
        NoteAiTextWidgetService noteAiTextWidgetService = mNoteAiTextWidgetService;
        if (noteAiTextWidgetService != null) {
            Intrinsics.checkNotNull(noteAiTextWidgetService);
            noteAiTextWidgetService.stopSelf();
            mNoteAiTextWidgetService = null;
        }
        showWaterMark(false);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void onWindowClosed$lambda$51(BaseHybridNoteEditFragment baseHybridNoteEditFragment) {
        WebRichEditor webRichEditor = baseHybridNoteEditFragment.mEditor;
        Intrinsics.checkNotNull(webRichEditor);
        webRichEditor.clearFlowStatus();
    }

    @Override // com.miui.notes.basefeature.noteedit.BaseNormalNoteEditFragment
    protected void showAttachmentAiChooser(View v) {
        final View.OnFocusChangeListener onFocusChangeListener;
        Intrinsics.checkNotNullParameter(v, "v");
        Logger.INSTANCE.d(TAG, "showAttachmentAiChooser");
        if (RomUtils.isSupportAiText(getContext()) || com.miui.notes.ai.utils.RomUtils.isLLMAIWriting(getContext())) {
            startAiTextWidget();
            this.isFromNoteToolBarEnter = true;
            return;
        }
        int height = 0;
        if (v.getAlpha() < 1.0f) {
            Toast toast = this.mAiToast;
            if (toast != null) {
                Intrinsics.checkNotNull(toast);
                toast.cancel();
            }
            Toast toastMakeText = Toast.makeText(getContext(), R.string.enter_content, 0);
            this.mAiToast = toastMakeText;
            Intrinsics.checkNotNull(toastMakeText);
            toastMakeText.show();
            return;
        }
        WorkingNote workingNote = this.mWorkingNote;
        Intrinsics.checkNotNull(workingNote);
        if (workingNote.getExtraInfo().textCount > AiController.MAX_WORD_COUNT.intValue()) {
            Toast toast2 = this.mAiToast;
            if (toast2 != null) {
                Intrinsics.checkNotNull(toast2);
                toast2.cancel();
            }
            Context context = getContext();
            Integer MAX_WORD_COUNT = AiController.MAX_WORD_COUNT;
            Intrinsics.checkNotNullExpressionValue(MAX_WORD_COUNT, "MAX_WORD_COUNT");
            Toast toastMakeText2 = Toast.makeText(context, Utils.obtainLocalInteger(MAX_WORD_COUNT.intValue(), R.plurals.ai_content_exceeded_warn), 0);
            this.mAiToast = toastMakeText2;
            Intrinsics.checkNotNull(toastMakeText2);
            toastMakeText2.show();
            return;
        }
        if (checkoutLoginState()) {
            this.mAiView = v;
            if (requestAiPermission()) {
                RichEditorWebView richEditorWebView = this.mEditorWebView;
                if (richEditorWebView != null) {
                    Intrinsics.checkNotNull(richEditorWebView);
                    onFocusChangeListener = richEditorWebView.getOnFocusChangeListener();
                    RichEditorWebView richEditorWebView2 = this.mEditorWebView;
                    Intrinsics.checkNotNull(richEditorWebView2);
                    richEditorWebView2.setOnFocusChangeListener(null);
                    RichEditorWebView richEditorWebView3 = this.mEditorWebView;
                    Intrinsics.checkNotNull(richEditorWebView3);
                    richEditorWebView3.clearFocus();
                    RichEditorWebView richEditorWebView4 = this.mEditorWebView;
                    Intrinsics.checkNotNull(richEditorWebView4);
                    richEditorWebView4.stopActionMode();
                } else {
                    onFocusChangeListener = null;
                }
                super.showAttachmentAiChooser(v);
                if (this.mAiChooserMenu != null) {
                    this.mAiChooserMenu.dismiss();
                    this.mAiChooserMenu = null;
                }
                Context context2 = getContext();
                RichEditorWebView richEditorWebView5 = this.mEditorWebView;
                if (richEditorWebView5 != null) {
                    Intrinsics.checkNotNull(richEditorWebView5);
                    height = richEditorWebView5.getHeight();
                }
                this.mAiChooserMenu = new AiChooserMenu(context2, height);
                this.mAiChooserMenu.setOnMenuListener(new IPopMenu.OnMenuListener() { // from class: com.miui.notes.basefeature.noteedit.BaseHybridNoteEditFragment.showAttachmentAiChooser.1
                    @Override // com.miui.common.view.IPopMenu.OnMenuListener
                    public void onDismiss() {
                    }

                    @Override // com.miui.common.view.IPopMenu.OnMenuListener
                    public void onShow() {
                        if (BaseHybridNoteEditFragment.this.mEditorWebView != null) {
                            RichEditorWebView richEditorWebView6 = BaseHybridNoteEditFragment.this.mEditorWebView;
                            Intrinsics.checkNotNull(richEditorWebView6);
                            richEditorWebView6.requestFocus();
                            if (onFocusChangeListener != null) {
                                RichEditorWebView richEditorWebView7 = BaseHybridNoteEditFragment.this.mEditorWebView;
                                Intrinsics.checkNotNull(richEditorWebView7);
                                richEditorWebView7.setOnFocusChangeListener(onFocusChangeListener);
                            }
                        }
                    }

                    @Override // com.miui.common.view.IPopMenu.OnMenuListener
                    public void onItemSelected(View view, int index) {
                        if (BaseHybridNoteEditFragment.this.checkoutLoginState()) {
                            BaseHybridNoteEditFragment.this.startAiAction(index);
                        }
                    }
                });
                this.mAiChooserMenu.show(v, new int[2]);
            }
        }
    }

    protected final void startAiTextWidget() {
        Logger.INSTANCE.d(TAG, "startAiTextWidget-startAiTextWidgetService");
        ComponentName componentNameStartService = requireContext().startService(new Intent(getContext(), (Class<?>) NoteAiTextWidgetService.class));
        this.isStartAiTextWindow = true;
        if (componentNameStartService != null) {
            Logger.INSTANCE.d(TAG, "startAiTextWidget with service component being starting");
            showAiTextWindow();
        } else {
            Logger.INSTANCE.d(TAG, "startAiTextWidget delay 10ms");
            if (this.mHandler != null) {
                this.mHandler.postDelayed(new Runnable() { // from class: com.miui.notes.basefeature.noteedit.BaseHybridNoteEditFragment$$ExternalSyntheticLambda28
                    @Override // java.lang.Runnable
                    public final void run() {
                        this.f$0.showAiTextWindow();
                    }
                }, 10L);
            }
        }
    }

    protected final void startAiAction(int type) {
        if (this.mAiController == null) {
            FragmentActivity fragmentActivityRequireActivity = requireActivity();
            Intrinsics.checkNotNullExpressionValue(fragmentActivityRequireActivity, "requireActivity(...)");
            AINotesViewModel aINotesViewModel = (AINotesViewModel) new ViewModelProvider(fragmentActivityRequireActivity).get(AINotesViewModel.class);
            aINotesViewModel.setAiResultActon("", -1);
            BaseHybridNoteEditFragment baseHybridNoteEditFragment = this;
            aINotesViewModel.getAiResultActon().observe(baseHybridNoteEditFragment, new Observer<AiResultActon>() { // from class: com.miui.notes.basefeature.noteedit.BaseHybridNoteEditFragment.startAiAction.1
                @Override // androidx.lifecycle.Observer
                public void onChanged(AiResultActon aiResultActon) {
                    Intrinsics.checkNotNullParameter(aiResultActon, "aiResultActon");
                    if (aiResultActon.getType() == 0) {
                        WebRichEditor webRichEditor = BaseHybridNoteEditFragment.this.mEditor;
                        Intrinsics.checkNotNull(webRichEditor);
                        webRichEditor.insertSummaryToTop(aiResultActon.getText());
                    } else if (aiResultActon.getType() == 1) {
                        WebRichEditor webRichEditor2 = BaseHybridNoteEditFragment.this.mEditor;
                        Intrinsics.checkNotNull(webRichEditor2);
                        webRichEditor2.replaceDoc(aiResultActon.getText());
                    }
                }
            });
            AiNoteRequest aiNoteRequest = AiNoteRequest.INSTANCE;
            Context applicationContext = BaseNoteApp.INSTANCE.getInstance().getApplicationContext();
            Intrinsics.checkNotNullExpressionValue(applicationContext, "getApplicationContext(...)");
            aiNoteRequest.init(applicationContext);
            AiController aiController = new AiController(getActivity(), aINotesViewModel, baseHybridNoteEditFragment);
            this.mAiController = aiController;
            Intrinsics.checkNotNull(aiController);
            aiController.setOnRenderProcessGoneListener(new AiController.OnRenderProcessGoneListener() { // from class: com.miui.notes.basefeature.noteedit.BaseHybridNoteEditFragment$$ExternalSyntheticLambda48
                @Override // com.miui.notes.ai.AiController.OnRenderProcessGoneListener
                public final void onRenderProcessExit(WebView webView) {
                    BaseHybridNoteEditFragment.startAiAction$lambda$53(this.f$0, webView);
                }
            });
        }
        AiController aiController2 = this.mAiController;
        Intrinsics.checkNotNull(aiController2);
        WorkingNote workingNote = this.mWorkingNote;
        Intrinsics.checkNotNull(workingNote);
        String content = workingNote.getContent();
        WorkingNote workingNote2 = this.mWorkingNote;
        Intrinsics.checkNotNull(workingNote2);
        aiController2.requestAiText(type, content, workingNote2.getTitle());
        showAiDialog();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void startAiAction$lambda$53(BaseHybridNoteEditFragment baseHybridNoteEditFragment, WebView webView) {
        BottomSheetView bottomSheetView = baseHybridNoteEditFragment.mAiBottomSheetView;
        if (bottomSheetView != null) {
            Intrinsics.checkNotNull(bottomSheetView);
            bottomSheetView.dismissWithoutAnim();
        }
        baseHybridNoteEditFragment.mAiBottomSheetView = null;
        AiController aiController = baseHybridNoteEditFragment.mAiController;
        if (aiController != null) {
            Intrinsics.checkNotNull(aiController);
            aiController.destroy();
        }
    }

    private final void showAiDialog() {
        doneEdit();
        if (this.mAiBottomSheetView == null) {
            BottomSheetView bottomSheetView = new BottomSheetView(getActivity(), R.layout.ai_editor_layout);
            this.mAiBottomSheetView = bottomSheetView;
            Intrinsics.checkNotNull(bottomSheetView);
            bottomSheetView.addDismissCallback(new BottomSheetView.DismissCallback() { // from class: com.miui.notes.basefeature.noteedit.BaseHybridNoteEditFragment$$ExternalSyntheticLambda9
                @Override // com.miui.notes.ai.ui.BottomSheetView.DismissCallback
                public final void onDismiss() {
                    BaseHybridNoteEditFragment.showAiDialog$lambda$54(this.f$0);
                }
            });
        }
        AiController aiController = this.mAiController;
        Intrinsics.checkNotNull(aiController);
        BottomSheetView bottomSheetView2 = this.mAiBottomSheetView;
        Intrinsics.checkNotNull(bottomSheetView2);
        aiController.setAndInitView(bottomSheetView2.getContentView());
        AiController aiController2 = this.mAiController;
        Intrinsics.checkNotNull(aiController2);
        aiController2.setAiActionListener(new IAiActionListener() { // from class: com.miui.notes.basefeature.noteedit.BaseHybridNoteEditFragment.showAiDialog.2
            @Override // com.miui.notes.ai.IAiActionListener
            public void onDismiss(int type) {
                BaseHybridNoteEditFragment.this.mDismissType = type;
                BottomSheetView bottomSheetView3 = BaseHybridNoteEditFragment.this.mAiBottomSheetView;
                Intrinsics.checkNotNull(bottomSheetView3);
                bottomSheetView3.dismiss();
            }

            @Override // com.miui.notes.ai.IAiActionListener
            public boolean isLoginState() {
                return BaseHybridNoteEditFragment.this.checkoutLoginState();
            }
        });
        BottomSheetView bottomSheetView3 = this.mAiBottomSheetView;
        Intrinsics.checkNotNull(bottomSheetView3);
        bottomSheetView3.show();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void showAiDialog$lambda$54(BaseHybridNoteEditFragment baseHybridNoteEditFragment) {
        Logger.INSTANCE.i(TAG, "ai view dismiss:" + baseHybridNoteEditFragment.mDismissType);
        int i = baseHybridNoteEditFragment.mDismissType;
        if (i == 0) {
            baseHybridNoteEditFragment.mDismissType = -1;
            baseHybridNoteEditFragment.mInRefreshAnim = true;
            WebRichEditor webRichEditor = baseHybridNoteEditFragment.mEditor;
            Intrinsics.checkNotNull(webRichEditor);
            webRichEditor.highlightSummary();
        } else if (i == 1) {
            baseHybridNoteEditFragment.mDismissType = -1;
            baseHybridNoteEditFragment.mInRefreshAnim = true;
            WebRichEditor webRichEditor2 = baseHybridNoteEditFragment.mEditor;
            Intrinsics.checkNotNull(webRichEditor2);
            webRichEditor2.highlightAllBackgroundSection();
        }
        AiController aiController = baseHybridNoteEditFragment.mAiController;
        Intrinsics.checkNotNull(aiController);
        aiController.reset();
    }

    @Override // com.miui.notes.basefeature.noteedit.BaseNormalNoteEditFragment, com.miui.notes.detail.BaseNoteDetailFragment
    protected void initBaseResultLauncher() {
        super.initBaseResultLauncher();
        this.toCtaBeforeAITask = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new CtaActivityResult() { // from class: com.miui.notes.basefeature.noteedit.BaseHybridNoteEditFragment.initBaseResultLauncher.1
            @Override // com.miui.notes.assist.CtaActivityResult
            public void onCtaActivityResult(ActivityResult result) {
                Intrinsics.checkNotNullParameter(result, "result");
                Logger.INSTANCE.i(BaseHybridNoteEditFragment.TAG, "toCtaBeforeAITask result:" + result.getResultCode());
                if (result.getResultCode() == 1) {
                    NoteAIRequest.INSTANCE.isLogin(BaseHybridNoteEditFragment.this.getActivity());
                } else if (result.getResultCode() == -3) {
                    PermissionNewUtils.showCtaDialog(BaseHybridNoteEditFragment.this.toCtaBeforeAITask);
                }
            }
        });
        this.toCtaBeforeAITextWidgetTask = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new CtaActivityResult() { // from class: com.miui.notes.basefeature.noteedit.BaseHybridNoteEditFragment.initBaseResultLauncher.2
            @Override // com.miui.notes.assist.CtaActivityResult
            public void onCtaActivityResult(ActivityResult result) {
                Intrinsics.checkNotNullParameter(result, "result");
                Logger.INSTANCE.i(BaseHybridNoteEditFragment.TAG, "toCtaBeforeAITextWidgetTask result:" + result.getResultCode());
                if (result.getResultCode() == 1) {
                    if (BaseHybridNoteEditFragment.this.getMCTAFirstAuthorization()) {
                        BaseHybridNoteEditFragment.this.setMCTAFirstAuthorization(false);
                    }
                    BaseHybridNoteEditFragment baseHybridNoteEditFragment = BaseHybridNoteEditFragment.this;
                    FragmentActivity fragmentActivityRequireActivity = baseHybridNoteEditFragment.requireActivity();
                    Intrinsics.checkNotNullExpressionValue(fragmentActivityRequireActivity, "requireActivity(...)");
                    baseHybridNoteEditFragment.doAfterCTAAccepted(fragmentActivityRequireActivity);
                    return;
                }
                BaseHybridNoteEditFragment.this.clearAiTextWidgetIntentType();
            }
        });
        this.toGlobalPermissionLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback() { // from class: com.miui.notes.basefeature.noteedit.BaseHybridNoteEditFragment$$ExternalSyntheticLambda54
            @Override // androidx.activity.result.ActivityResultCallback
            public final void onActivityResult(Object obj) {
                BaseHybridNoteEditFragment.initBaseResultLauncher$lambda$55(this.f$0, (ActivityResult) obj);
            }
        });
        this.toAudioRecorderPermissionLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback() { // from class: com.miui.notes.basefeature.noteedit.BaseHybridNoteEditFragment$$ExternalSyntheticLambda56
            @Override // androidx.activity.result.ActivityResultCallback
            public final void onActivityResult(Object obj) {
                BaseHybridNoteEditFragment.initBaseResultLauncher$lambda$56(this.f$0, (ActivityResult) obj);
            }
        });
        this.toAudioRecorderShowLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new AnonymousClass5());
        this.toAddAccountLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback() { // from class: com.miui.notes.basefeature.noteedit.BaseHybridNoteEditFragment$$ExternalSyntheticLambda57
            @Override // androidx.activity.result.ActivityResultCallback
            public final void onActivityResult(Object obj) {
                BaseHybridNoteEditFragment.initBaseResultLauncher$lambda$57((ActivityResult) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void initBaseResultLauncher$lambda$55(BaseHybridNoteEditFragment baseHybridNoteEditFragment, ActivityResult activityResult) {
        Logger.INSTANCE.i(TAG, "toGlobalPermissionLauncher result:" + activityResult.getResultCode());
        if (activityResult.getResultCode() == 2) {
            if (baseHybridNoteEditFragment.mCTAFirstAuthorization) {
                baseHybridNoteEditFragment.mCTAFirstAuthorization = false;
            }
            com.miui.notes.tool.PreferenceUtils.setGlobalPermissionAccepted(true, baseHybridNoteEditFragment.getContext());
            FragmentActivity fragmentActivityRequireActivity = baseHybridNoteEditFragment.requireActivity();
            Intrinsics.checkNotNullExpressionValue(fragmentActivityRequireActivity, "requireActivity(...)");
            baseHybridNoteEditFragment.doAfterCTAAccepted(fragmentActivityRequireActivity);
            return;
        }
        baseHybridNoteEditFragment.clearAiTextWidgetIntentType();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void initBaseResultLauncher$lambda$56(BaseHybridNoteEditFragment baseHybridNoteEditFragment, ActivityResult activityResult) {
        if (activityResult.getResultCode() == -1) {
            if (!baseHybridNoteEditFragment.mIsRecordRecordSummaryRegistered) {
                baseHybridNoteEditFragment.initRecordSummaryResultObserver();
            }
            if (activityResult.getData() != null) {
                Intent data = activityResult.getData();
                Intrinsics.checkNotNull(data);
                int intExtra = data.getIntExtra(BaseConstants.REQUEST_RESULT, 0);
                Logger.INSTANCE.i(TAG, "request result: " + intExtra);
                if (intExtra == 10) {
                    baseHybridNoteEditFragment.updateActionMode(EditorActionMode.EDIT, false);
                    baseHybridNoteEditFragment.getActionBarController().tempChangeUndoRedoView(true);
                    baseHybridNoteEditFragment.startAIRecord();
                    return;
                }
                return;
            }
            return;
        }
        Logger.INSTANCE.i(TAG, "request fail");
    }

    /* JADX INFO: renamed from: com.miui.notes.basefeature.noteedit.BaseHybridNoteEditFragment$initBaseResultLauncher$5, reason: invalid class name */
    /* JADX INFO: compiled from: BaseHybridNoteEditFragment.kt */
    @Metadata(d1 = {"\u0000\u0017\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002*\u0001\u0000\b\n\u0018\u00002\n\u0012\u0006\u0012\u0004\u0018\u00010\u00020\u0001J\u0012\u0010\u0003\u001a\u00020\u00042\b\u0010\u0005\u001a\u0004\u0018\u00010\u0002H\u0016¨\u0006\u0006"}, d2 = {"com/miui/notes/basefeature/noteedit/BaseHybridNoteEditFragment$initBaseResultLauncher$5", "Landroidx/activity/result/ActivityResultCallback;", "Landroidx/activity/result/ActivityResult;", "onActivityResult", "", "result", "app_PhoneGlobalRelease"}, k = 1, mv = {2, 2, 0}, xi = 48)
    public static final class AnonymousClass5 implements ActivityResultCallback<ActivityResult> {
        AnonymousClass5() {
        }

        @Override // androidx.activity.result.ActivityResultCallback
        public void onActivityResult(ActivityResult result) {
            if (result == null) {
                return;
            }
            int resultCode = result.getResultCode();
            Intent data = result.getData();
            if (resultCode != -1 || data == null) {
                return;
            }
            final int intExtra = data.getIntExtra("extra_is_record_seek_to_time", 0);
            Logger.INSTANCE.d(BaseHybridNoteEditFragment.TAG, "extra_is_record_seek_to_time: " + intExtra);
            final int intExtra2 = data.getIntExtra("extra_record_state", 0);
            Logger.INSTANCE.d(BaseHybridNoteEditFragment.TAG, "extra_record_state: " + intExtra2);
            if (BaseHybridNoteEditFragment.this.getActivity() == null || BaseHybridNoteEditFragment.this.mEditor == null || BaseHybridNoteEditFragment.this.mPlayingAudioFileName == null) {
                return;
            }
            FragmentActivity fragmentActivityRequireActivity = BaseHybridNoteEditFragment.this.requireActivity();
            final BaseHybridNoteEditFragment baseHybridNoteEditFragment = BaseHybridNoteEditFragment.this;
            fragmentActivityRequireActivity.runOnUiThread(new Runnable() { // from class: com.miui.notes.basefeature.noteedit.BaseHybridNoteEditFragment$initBaseResultLauncher$5$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    BaseHybridNoteEditFragment.AnonymousClass5.onActivityResult$lambda$0(intExtra2, baseHybridNoteEditFragment, intExtra);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static final void onActivityResult$lambda$0(int i, BaseHybridNoteEditFragment baseHybridNoteEditFragment, int i2) {
            boolean z = i == 1;
            WebRichEditor webRichEditor = baseHybridNoteEditFragment.mEditor;
            Intrinsics.checkNotNull(webRichEditor);
            webRichEditor.startAudioByTime(baseHybridNoteEditFragment.mPlayingAudioFileName, Boolean.valueOf(z), i2);
            baseHybridNoteEditFragment.mPlayingAudioFileName = null;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void initBaseResultLauncher$lambda$57(ActivityResult activityResult) {
        Logger logger = Logger.INSTANCE;
        Intrinsics.checkNotNull(activityResult);
        logger.d(TAG, "add account resultCode : " + activityResult.getResultCode());
    }

    public final boolean checkoutLoginState() {
        if (!com.miui.notes.tool.PreferenceUtils.isCTAAccepted(getContext()) && PermissionUtils.supportNewPermissionStyle()) {
            PermissionNewUtils.showCtaDialog(this.toCtaBeforeAITask);
            return false;
        }
        if (getActivity() == null) {
            return false;
        }
        return NoteAIRequest.INSTANCE.isLogin(getActivity());
    }

    private final boolean requestAiPermission() {
        FragmentActivity activity = getActivity();
        if (activity == null || activity.isFinishing() || com.miui.notes.tool.PreferenceUtils.getAllowAi(activity)) {
            return true;
        }
        manageDialog(PermissionUtils.showAiAcceptDialog(activity, new DialogInterface.OnDismissListener() { // from class: com.miui.notes.basefeature.noteedit.BaseHybridNoteEditFragment$$ExternalSyntheticLambda22
            @Override // android.content.DialogInterface.OnDismissListener
            public final void onDismiss(DialogInterface dialogInterface) {
                BaseHybridNoteEditFragment.requestAiPermission$lambda$59(this.f$0, dialogInterface);
            }
        }));
        return false;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void requestAiPermission$lambda$59(final BaseHybridNoteEditFragment baseHybridNoteEditFragment, DialogInterface dialogInterface) {
        if (baseHybridNoteEditFragment.mHandler != null) {
            baseHybridNoteEditFragment.mHandler.postDelayed(new Runnable() { // from class: com.miui.notes.basefeature.noteedit.BaseHybridNoteEditFragment$$ExternalSyntheticLambda49
                @Override // java.lang.Runnable
                public final void run() {
                    BaseHybridNoteEditFragment.requestAiPermission$lambda$59$lambda$58(this.f$0);
                }
            }, 200L);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void requestAiPermission$lambda$59$lambda$58(BaseHybridNoteEditFragment baseHybridNoteEditFragment) {
        if (baseHybridNoteEditFragment.mAiView == null || !com.miui.notes.tool.PreferenceUtils.getAllowAi(BaseNoteApp.INSTANCE.getInstance())) {
            return;
        }
        View view = baseHybridNoteEditFragment.mAiView;
        Intrinsics.checkNotNull(view);
        baseHybridNoteEditFragment.showAttachmentAiChooser(view);
    }

    @Override // com.miui.notes.basefeature.noteedit.BaseNormalNoteEditFragment
    public void onClickAudioBtnAboveOS2() {
        super.onClickAudioBtnAboveOS2();
        INSTANCE.removeAiFloatingWindow();
        if (!this.mIsRecording && isAllowRecord()) {
            initAIRecorderManager();
            if (getActivity() == null) {
                return;
            }
            if (this.mNoteRichEditor != null && !this.mNoteRichEditor.canAddNewElement(63)) {
                onContentOverLength(0);
                return;
            }
            if (AIRecorderManager.instance.getAIRecorderPreState(BaseNoteApp.INSTANCE.getInstance()) == 1) {
                startAIRecord();
                return;
            } else {
                if (AIRecorderManager.instance.getAIRecorderPreState(BaseNoteApp.INSTANCE.getInstance()) != 4) {
                    ActivityResultLauncher<Intent> activityResultLauncher = this.toAudioRecorderPermissionLauncher;
                    Intrinsics.checkNotNull(activityResultLauncher);
                    activityResultLauncher.launch(AIRecorderManager.getRequestCTAIntent());
                    return;
                }
                return;
            }
        }
        if (this.mIsRecording) {
            stopAIRecord();
        }
    }

    protected final boolean isAllowRecord() {
        if (Utils.isInvisibleMode()) {
            Toast.makeText(requireContext().getApplicationContext(), requireContext().getString(R.string.stealth_mode_tip), 0).show();
            return false;
        }
        if (UIUtils.isFoldInternalScreen(getContext()) || getActivity() == null || !CompatUtils.invokeIsInMultiWindowMode(getActivity())) {
            return true;
        }
        FragmentActivity fragmentActivityRequireActivity = requireActivity();
        Intrinsics.checkNotNullExpressionValue(fragmentActivityRequireActivity, "requireActivity(...)");
        Utils.hideSoftInput(fragmentActivityRequireActivity);
        Toast.makeText(requireContext().getApplicationContext(), R.string.toast_refuse_to_record_when_in_multi_window_new, 0).show();
        return false;
    }

    protected final void initAIRecorderManager() {
        try {
            AIRecorderManager.instance.init(BaseNoteApp.INSTANCE.getInstance());
            Logger.INSTANCE.i(TAG, "initAIRecorderManager");
        } catch (Exception e) {
            Logger.INSTANCE.i(TAG, "onCreate: AIRecorderManager Error Message -> " + e.getMessage());
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX INFO: compiled from: BaseHybridNoteEditFragment.kt */
    @Metadata(d1 = {"\u0000<\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\t\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0010\u0006\n\u0002\b\u0002\b\u0002\u0018\u00002\u00020\u0001B\u0011\u0012\b\u0010\u0002\u001a\u0004\u0018\u00010\u0003¢\u0006\u0004\b\u0004\u0010\u0005J\u0018\u0010\b\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u000b2\u0006\u0010\f\u001a\u00020\rH\u0016J\u001a\u0010\u000e\u001a\u00020\t2\u0006\u0010\u000f\u001a\u00020\u000b2\b\u0010\u0010\u001a\u0004\u0018\u00010\u0011H\u0016J\u0018\u0010\u0012\u001a\u00020\t2\u0006\u0010\u0013\u001a\u00020\u00142\u0006\u0010\u0015\u001a\u00020\u000bH\u0016R\u0016\u0010\u0006\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\u00030\u0007X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u0016"}, d2 = {"Lcom/miui/notes/basefeature/noteedit/BaseHybridNoteEditFragment$NoteAIRecorderServiceCallback;", "Lcom/xiaomi/xms/ai/recorder/callback/AIRecorderServiceCallback;", "fragment", "Lcom/miui/notes/basefeature/noteedit/BaseHybridNoteEditFragment;", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "(Lcom/miui/notes/basefeature/noteedit/BaseHybridNoteEditFragment;)V", "iFragmentRef", "Ljava/lang/ref/WeakReference;", "onStateChanged", "", "state", "", "duration", "", "onErrorOccurred", "errCode", "errMsg", "", "onAmplitudeChanged", "percent", "", "amplitude", "app_PhoneGlobalRelease"}, k = 1, mv = {2, 2, 0}, xi = 48)
    static final class NoteAIRecorderServiceCallback extends AIRecorderServiceCallback {
        private final WeakReference<BaseHybridNoteEditFragment> iFragmentRef;

        public NoteAIRecorderServiceCallback(BaseHybridNoteEditFragment baseHybridNoteEditFragment) {
            this.iFragmentRef = new WeakReference<>(baseHybridNoteEditFragment);
        }

        @Override // com.xiaomi.xms.ai.recorder.callback.AIRecorderServiceCallback, com.android.soundrecorder.ai.base.IAIRecorderServiceCallback
        public void onStateChanged(int state, long duration) throws RemoteException {
            super.onStateChanged(state, duration);
            Logger.INSTANCE.d(BaseHybridNoteEditFragment.TAG, "onStateChanged: " + state + ",duration:" + duration);
            final BaseHybridNoteEditFragment baseHybridNoteEditFragment = this.iFragmentRef.get();
            if (baseHybridNoteEditFragment == null) {
                Logger.INSTANCE.e(BaseHybridNoteEditFragment.TAG, "onStateChanged: fragment is null");
                return;
            }
            if (state != 20) {
                if (state != 50) {
                    return;
                }
                baseHybridNoteEditFragment.finishAiRecord();
            } else if (baseHybridNoteEditFragment.getActivity() != null) {
                baseHybridNoteEditFragment.requireActivity().runOnUiThread(new Runnable() { // from class: com.miui.notes.basefeature.noteedit.BaseHybridNoteEditFragment$NoteAIRecorderServiceCallback$$ExternalSyntheticLambda0
                    @Override // java.lang.Runnable
                    public final void run() {
                        BaseHybridNoteEditFragment.NoteAIRecorderServiceCallback.onStateChanged$lambda$0(baseHybridNoteEditFragment);
                    }
                });
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static final void onStateChanged$lambda$0(BaseHybridNoteEditFragment baseHybridNoteEditFragment) {
            WebRichEditor webRichEditor = baseHybridNoteEditFragment.mEditor;
            Intrinsics.checkNotNull(webRichEditor);
            webRichEditor.insertAudioStart();
            baseHybridNoteEditFragment.getEditToolBarController().hide(false);
            baseHybridNoteEditFragment.getActionBarController().tempChangeUndoRedoView(true);
            baseHybridNoteEditFragment.recordNotifyNavigation(true);
        }

        @Override // com.xiaomi.xms.ai.recorder.callback.AIRecorderServiceCallback, com.android.soundrecorder.ai.base.IAIRecorderServiceCallback
        public void onErrorOccurred(int errCode, String errMsg) throws RemoteException {
            Logger.INSTANCE.i(BaseHybridNoteEditFragment.TAG, "onErrorOccured: errCode -> " + errCode + " , errMsg -> " + errMsg);
        }

        @Override // com.xiaomi.xms.ai.recorder.callback.AIRecorderServiceCallback, com.android.soundrecorder.ai.base.IAIRecorderServiceCallback
        public void onAmplitudeChanged(final double percent, int amplitude) throws RemoteException {
            super.onAmplitudeChanged(percent, amplitude);
            final BaseHybridNoteEditFragment baseHybridNoteEditFragment = this.iFragmentRef.get();
            Logger.INSTANCE.v(BaseHybridNoteEditFragment.TAG, "onAmplitudeChanged: " + percent + ",amplitude:" + amplitude);
            if (baseHybridNoteEditFragment == null || baseHybridNoteEditFragment.getActivity() == null) {
                return;
            }
            baseHybridNoteEditFragment.requireActivity().runOnUiThread(new Runnable() { // from class: com.miui.notes.basefeature.noteedit.BaseHybridNoteEditFragment$NoteAIRecorderServiceCallback$$ExternalSyntheticLambda1
                @Override // java.lang.Runnable
                public final void run() {
                    BaseHybridNoteEditFragment.NoteAIRecorderServiceCallback.onAmplitudeChanged$lambda$1(baseHybridNoteEditFragment, percent);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static final void onAmplitudeChanged$lambda$1(BaseHybridNoteEditFragment baseHybridNoteEditFragment, double d) {
            WebRichEditor webRichEditor = baseHybridNoteEditFragment.mEditor;
            Intrinsics.checkNotNull(webRichEditor);
            webRichEditor.setCurrentAmplitude(d);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void finishAiRecord() {
        final String strRenameMp3FileToSha1;
        Logger.INSTANCE.d(TAG, "finishAiRecord");
        NoteAIRecorderConfig noteAIRecorderConfig = this.mAiRecorderConfig;
        if (noteAIRecorderConfig != null) {
            Intrinsics.checkNotNull(noteAIRecorderConfig);
            strRenameMp3FileToSha1 = AudioUtils.renameMp3FileToSha1(AudioUtils.NOTE_MP3_DIR_NAME, noteAIRecorderConfig.getAudioOutputFilePath());
        } else {
            strRenameMp3FileToSha1 = null;
        }
        int currentRecordDuration = (int) ((AIRecorderManager.instance.getCurrentRecordDuration() / ((long) 1000)) + 1);
        Logger.INSTANCE.d(TAG, "onStateChanged: RecordDuration -> " + currentRecordDuration);
        EditStat.trackVoiceCreate(currentRecordDuration);
        this.mIsRecording = false;
        if (this.mAiRecorderServiceCallback != null) {
            AIRecorderManager.instance.unregisterRecorderCallback(BaseNoteApp.INSTANCE.getInstance().getPackageName(), this.mAiRecorderServiceCallback);
        }
        if (this.mAiRecorderServiceStateCallback != null) {
            AIRecorderManager.instance.unregisterAIRecorderServiceStateCallback(this.mAiRecorderServiceStateCallback);
        }
        registerTapShareCallBack();
        Logger.INSTANCE.d(TAG, "onStateChanged: FINISHED -> " + strRenameMp3FileToSha1);
        com.miui.notes.tool.PreferenceUtils.setTemAudioPath(BaseNoteApp.INSTANCE.getInstance(), strRenameMp3FileToSha1);
        if (getActivity() != null && strRenameMp3FileToSha1 != null && strRenameMp3FileToSha1.length() != 0) {
            this.mLastAudioFileName = strRenameMp3FileToSha1;
            requireActivity().runOnUiThread(new Runnable() { // from class: com.miui.notes.basefeature.noteedit.BaseHybridNoteEditFragment$$ExternalSyntheticLambda46
                @Override // java.lang.Runnable
                public final void run() {
                    BaseHybridNoteEditFragment.finishAiRecord$lambda$60(this.f$0, strRenameMp3FileToSha1);
                }
            });
        }
        this.mHandler.post(new Runnable() { // from class: com.miui.notes.basefeature.noteedit.BaseHybridNoteEditFragment$$ExternalSyntheticLambda47
            @Override // java.lang.Runnable
            public final void run() {
                BaseHybridNoteEditFragment.finishAiRecord$lambda$61(this.f$0);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void finishAiRecord$lambda$60(BaseHybridNoteEditFragment baseHybridNoteEditFragment, String str) {
        WebRichEditor webRichEditor = baseHybridNoteEditFragment.mEditor;
        Intrinsics.checkNotNull(webRichEditor);
        webRichEditor.insertAudioEnd(str);
        baseHybridNoteEditFragment.recordNotifyNavigation(false);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void finishAiRecord$lambda$61(BaseHybridNoteEditFragment baseHybridNoteEditFragment) {
        baseHybridNoteEditFragment.updateActionMode(EditorActionMode.VIEW, true);
    }

    protected final void startAIRecord() {
        long jCurrentTimeMillis = System.currentTimeMillis();
        if (jCurrentTimeMillis - this.mAIRecorderLastStartTime < 1000) {
            return;
        }
        this.mAIRecorderLastStartTime = jCurrentTimeMillis;
        Logger.INSTANCE.d(TAG, "startAIRecord: ");
        String strObtainMp3FilePath = AudioUtils.obtainMp3FilePath(AudioUtils.NOTE_MP3_DIR_NAME);
        com.miui.notes.tool.PreferenceUtils.setTemAudioPath(BaseNoteApp.INSTANCE.getInstance(), strObtainMp3FilePath);
        this.mAiRecorderConfig = new NoteAIRecorderConfig(strObtainMp3FilePath);
        AIRecorderManager.instance.setRecordConfig(this.mAiRecorderConfig);
        AIRecorderManager.instance.registerRecorderCallback(BaseNoteApp.INSTANCE.getInstance().getPackageName(), this.mAiRecorderServiceCallback);
        AIRecorderManager.instance.registerAIRecorderServiceStateCallback(this.mAiRecorderServiceStateCallback);
        AIRecorderManager.instance.startRecording();
        this.mIsRecording = true;
        unregisterTapShareCallback();
    }

    public final void stopAIRecord() {
        Logger.INSTANCE.d(TAG, "stopAIRecord: ");
        AIRecorderManager.instance.stopRecording();
        this.mIsRecording = false;
        registerTapShareCallBack();
    }

    public final void showAIRecorderComponent(final String audioFileName, boolean isPlaying, int startTime) {
        try {
            File file = new File(AudioUtils.obtainMp3FileDir(AudioUtils.NOTE_MP3_DIR_NAME), audioFileName);
            this.mLastAudioFileName = audioFileName;
            Logger.INSTANCE.d(TAG, "showAIRecorderComponent :" + this.mLastAudioFileName);
            Uri uriForFile = CompatFileProvider.getUriForFile(BaseNoteApp.INSTANCE.getInstance(), "com.miui.notes", file);
            final Intent intent = new Intent("miui.intent.action.recorder.play");
            intent.setPackage(com.xiaomi.xms.ai.recorder.Constants.RECORDER_PACKAGE_NAME);
            Bundle bundle = new Bundle();
            bundle.putString("playback_path", file.getPath());
            bundle.putString("playback_name", file.getName());
            bundle.putInt("extra_is_record_seek_to_time", startTime);
            bundle.putParcelable("playback_url", uriForFile);
            bundle.putBoolean("playback_auto", isPlaying);
            intent.putExtra("extra_data_bundle", bundle);
            if (getContext() == null || getActivity() == null) {
                return;
            }
            requireContext().grantUriPermission(com.xiaomi.xms.ai.recorder.Constants.RECORDER_PACKAGE_NAME, uriForFile, 1);
            intent.addFlags(3);
            requireActivity().runOnUiThread(new Runnable() { // from class: com.miui.notes.basefeature.noteedit.BaseHybridNoteEditFragment$$ExternalSyntheticLambda64
                @Override // java.lang.Runnable
                public final void run() {
                    BaseHybridNoteEditFragment.showAIRecorderComponent$lambda$62(this.f$0, audioFileName, intent);
                }
            });
        } catch (Exception e) {
            Logger.INSTANCE.e(TAG, "showAIRecorderComponent: " + e.getMessage());
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void showAIRecorderComponent$lambda$62(BaseHybridNoteEditFragment baseHybridNoteEditFragment, String str, Intent intent) {
        if (Utils.isFastClick(0, 200L) || !baseHybridNoteEditFragment.checkAIRecorderPermission()) {
            return;
        }
        INSTANCE.removeAiFloatingWindow();
        Logger.INSTANCE.d(TAG, "showAIRecorderComponent: ");
        baseHybridNoteEditFragment.mPlayingAudioFileName = str;
        Logger.INSTANCE.d(TAG, "showAIRecorderComponent start: ");
        ActivityResultLauncher<Intent> activityResultLauncher = baseHybridNoteEditFragment.toAudioRecorderShowLauncher;
        Intrinsics.checkNotNull(activityResultLauncher);
        activityResultLauncher.launch(intent);
        baseHybridNoteEditFragment.isAudioRecorderShowed = true;
    }

    public final void showAIRecorderRecording() {
        this.mHandler.post(new Runnable() { // from class: com.miui.notes.basefeature.noteedit.BaseHybridNoteEditFragment$$ExternalSyntheticLambda55
            @Override // java.lang.Runnable
            public final void run() {
                BaseHybridNoteEditFragment.showAIRecorderRecording$lambda$63(this.f$0);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void showAIRecorderRecording$lambda$63(BaseHybridNoteEditFragment baseHybridNoteEditFragment) {
        if (Utils.isFastClick(0, 200L) || !baseHybridNoteEditFragment.checkAIRecorderPermission() || baseHybridNoteEditFragment.getContext() == null) {
            return;
        }
        Logger.INSTANCE.d(TAG, "showAIRecorderRecording: ");
        Context contextRequireContext = baseHybridNoteEditFragment.requireContext();
        Intrinsics.checkNotNullExpressionValue(contextRequireContext, "requireContext(...)");
        UIKit.openRecordUI(contextRequireContext);
    }

    public final void getAudioDescription(final String fileName) {
        String str = fileName;
        if (str == null || str.length() == 0) {
            Logger.INSTANCE.d(TAG, "getAudioDescription: fileName is null or empty, skip.");
        } else {
            new Thread(new Runnable() { // from class: com.miui.notes.basefeature.noteedit.BaseHybridNoteEditFragment$$ExternalSyntheticLambda14
                @Override // java.lang.Runnable
                public final void run() {
                    BaseHybridNoteEditFragment.getAudioDescription$lambda$65(fileName, this);
                }
            }).start();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void getAudioDescription$lambda$65(final String str, final BaseHybridNoteEditFragment baseHybridNoteEditFragment) {
        File file = new File(AudioUtils.obtainMp3FileDir(AudioUtils.NOTE_MP3_DIR_NAME), str);
        try {
            if (!file.exists()) {
                Logger.INSTANCE.d(TAG, "getAudioDescription: " + str + " not exist!");
                return;
            }
            ParcelFileDescriptor parcelFileDescriptorOpen = ParcelFileDescriptor.open(file, 805306368);
            String strGenerateFileSha1 = AIRecorderManager.instance.generateFileSha1(parcelFileDescriptorOpen.getFileDescriptor());
            final String strQuerySummary = AIRecorderManager.instance.querySummary(strGenerateFileSha1, BaseNoteApp.INSTANCE.getInstance());
            final String strQueryRecognizeData = AIRecorderManager.instance.queryRecognizeData(strGenerateFileSha1, BaseNoteApp.INSTANCE.getInstance());
            Logger.INSTANCE.d(TAG, "getAudioDescription: audioSummaryDescription -> " + strQuerySummary);
            Logger.INSTANCE.d(TAG, "getAudioDescription: audioRecognizeDataDescription -> " + strQueryRecognizeData);
            baseHybridNoteEditFragment.mHandler.post(new Runnable() { // from class: com.miui.notes.basefeature.noteedit.BaseHybridNoteEditFragment$$ExternalSyntheticLambda11
                @Override // java.lang.Runnable
                public final void run() {
                    BaseHybridNoteEditFragment.getAudioDescription$lambda$65$lambda$64(strQueryRecognizeData, strQuerySummary, baseHybridNoteEditFragment, str);
                }
            });
            if (parcelFileDescriptorOpen != null) {
                parcelFileDescriptorOpen.close();
            }
        } catch (Exception e) {
            Logger.INSTANCE.i(TAG, "getAudioDescription: " + e.getMessage());
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void getAudioDescription$lambda$65$lambda$64(String str, String str2, BaseHybridNoteEditFragment baseHybridNoteEditFragment, String str3) {
        if (TextUtils.isEmpty(str) && TextUtils.isEmpty(str2)) {
            return;
        }
        if (TextUtils.isEmpty(str2)) {
            WebRichEditor webRichEditor = baseHybridNoteEditFragment.mEditor;
            Intrinsics.checkNotNull(webRichEditor);
            webRichEditor.setAudioDescription(str3, str, 0);
        } else {
            WebRichEditor webRichEditor2 = baseHybridNoteEditFragment.mEditor;
            Intrinsics.checkNotNull(webRichEditor2);
            webRichEditor2.setAudioDescription(str3, str2, 1);
        }
    }

    private final boolean checkAIRecorderPermission() {
        if (getContext() != null && getActivity() != null) {
            if (Utils.isRecordIsInstall()) {
                if (Utils.isNeedUpdateRecord()) {
                    showUpdateRecordDialog();
                } else {
                    if (AIRecorderManager.instance.getAIRecorderPreState(BaseNoteApp.INSTANCE.getInstance()) == 1) {
                        return true;
                    }
                    if (AIRecorderManager.instance.getAIRecorderPreState(BaseNoteApp.INSTANCE.getInstance()) != 4) {
                        AIRecorderManager.instance.requestPermission(getActivity());
                    }
                }
            } else {
                showInstallRecordDialog();
            }
        }
        return false;
    }

    @Override // com.miui.notes.ai.editor.IAiEditor
    public void addText(String data, boolean firstFlow, boolean isThinkContent) {
        WebRichEditor webRichEditor;
        Logger.INSTANCE.d(TAG, "addText mPagePrepare: " + this.mPagePrepare + " mEditor: " + this.mEditor);
        if (!this.mPagePrepare || (webRichEditor = this.mEditor) == null) {
            return;
        }
        Intrinsics.checkNotNull(webRichEditor);
        webRichEditor.aiInput(data, false, firstFlow, isThinkContent);
        showWaterMark(true);
    }

    @Override // com.miui.notes.ai.editor.IAiEditor
    public void finishFlow(boolean inNewPanel, String data, String title) {
        WebRichEditor webRichEditor = this.mEditor;
        if (webRichEditor != null) {
            Intrinsics.checkNotNull(webRichEditor);
            webRichEditor.finishFlow(inNewPanel, data);
            if (TextUtils.isEmpty(this.mOriginalTitle) && TextUtils.isEmpty(this.mOriginalContent) && !TextUtils.isEmpty(title)) {
                WebRichEditor webRichEditor2 = this.mEditor;
                Intrinsics.checkNotNull(webRichEditor2);
                webRichEditor2.setTitle(title);
            }
        }
    }

    @Override // com.miui.notes.ai.INoteAiEditor
    public void onDone() {
        if (UserInputHolder.getInstance().getRequestType() == RequestType.TYPE_CHECK) {
            trimAIStatement();
            doneCorrection();
        } else {
            trimAIStatement();
        }
        this.mNotSaveData = false;
    }

    @Override // com.miui.notes.ai.INoteAiEditor
    public void onFloatingWindowVisible() {
        Logger.INSTANCE.i(TAG, "onFloatingWindowVisible");
        if (this.mActionBarController != null) {
            this.mActionBarController.startAiMode();
        }
        this.mInAiMode = true;
        this.mNotSaveData = true;
        handleBrowseMode();
    }

    @Override // com.miui.notes.ai.INoteAiEditor
    public void onFloatingWindowClose(boolean save, int aiUsed) {
        WorkingNote workingNote;
        Logger.INSTANCE.i(getTAG(), "onFloatingWindowClose, save:" + save + " ,aiUsed: " + aiUsed);
        WorkingNote workingNote2 = this.mWorkingNote;
        if ((workingNote2 == null || workingNote2.getAiTextUsed() != 1) && (workingNote = this.mWorkingNote) != null) {
            workingNote.setAiTextUsed(aiUsed);
        }
        this.mInAiMode = false;
        this.mNeedSelectAll = false;
        closeCorrectionPop();
        handleBrowseMode();
        WebRichEditor webRichEditor = this.mEditor;
        if (webRichEditor != null) {
            webRichEditor.toggleEmptyLoading(true);
        }
        if (this.mActionBarController != null) {
            this.mActionBarController.cancelAiMode();
        }
        if (!this.mCTAFirstAuthorization) {
            NoteAiFloatingWindow noteAiFloatingWindow = mNoteAiFloatingWindow;
            if (noteAiFloatingWindow != null) {
                Intrinsics.checkNotNull(noteAiFloatingWindow);
                noteAiFloatingWindow.setAiActionListener(null);
            }
            mNoteAiFloatingWindow = null;
        }
        if (this.mAccountManagerCallback != null) {
            this.mAccountManagerCallback = null;
        }
        WebRichEditor webRichEditor2 = this.mEditor;
        if (webRichEditor2 != null) {
            if (!save) {
                Intrinsics.checkNotNull(webRichEditor2);
                webRichEditor2.removeSelectionColorBg();
            } else {
                Intrinsics.checkNotNull(webRichEditor2);
                webRichEditor2.removeSelectionColorBgSlowly();
            }
        }
        if (aiUsed == 1) {
            WorkingNote workingNote3 = this.mWorkingNote;
            if (workingNote3 != null) {
                workingNote3.setAigcDataId(AiFloatingWindow.mAiTraceId);
                return;
            }
            return;
        }
        WorkingNote workingNote4 = this.mWorkingNote;
        if (workingNote4 != null) {
            workingNote4.setAigcDataId(UUID.randomUUID().toString());
        }
    }

    @Override // com.miui.notes.ai.INoteAiEditor
    public void insertSummary(String data) {
        this.mNotSaveData = false;
        if (this.mEditor != null) {
            String str = "<text indent=\"1\"><h3-size>" + BaseNoteApp.INSTANCE.getInstance().getString(R.string.summary) + "</h3-size></text>\n" + data + "\n<text indent=\"1\"><h3-size>" + BaseNoteApp.INSTANCE.getInstance().getString(R.string.main_body) + "<h3-size/></text>";
            WebRichEditor webRichEditor = this.mEditor;
            Intrinsics.checkNotNull(webRichEditor);
            webRichEditor.insertSummary(str);
        }
    }

    public final boolean isAiCorrect() {
        Logger.INSTANCE.i(getTAG(), "isAiCorrect: " + AiFloatingWindow.mCorrectMisNum);
        return AiFloatingWindow.mCorrectMisNum != 0;
    }

    public final boolean isAiCheckType() {
        return UserInputHolder.getInstance().getRequestType() == RequestType.TYPE_CHECK;
    }

    @Override // com.miui.notes.ai.INoteAiEditor
    public void doneCorrection() {
        WebRichEditor webRichEditor = this.mEditor;
        if (webRichEditor != null) {
            Intrinsics.checkNotNull(webRichEditor);
            webRichEditor.trimCorrection();
        }
    }

    @Override // com.miui.notes.ai.INoteAiEditor
    public void trimAIStatement() {
        WebRichEditor webRichEditor = this.mEditor;
        if (webRichEditor != null) {
            Intrinsics.checkNotNull(webRichEditor);
            webRichEditor.trimAIStatement();
        }
    }

    @Override // com.miui.notes.ai.INoteAiEditor
    public void saveScrollPosition() {
        WebRichEditor webRichEditor = this.mEditor;
        if (webRichEditor != null) {
            Intrinsics.checkNotNull(webRichEditor);
            webRichEditor.saveScrollPosition();
        }
    }

    @Override // com.miui.notes.ai.INoteAiEditor
    public void restoreToSavedScrollPosition() {
        WebRichEditor webRichEditor = this.mEditor;
        if (webRichEditor != null) {
            Intrinsics.checkNotNull(webRichEditor);
            webRichEditor.restoreToSavedScrollPosition();
        }
    }

    @Override // com.miui.notes.ai.INoteAiEditor
    public void revert() {
        if (this.mWorkingNote == null) {
            return;
        }
        long j = this.mOriginalId;
        WorkingNote workingNote = this.mWorkingNote;
        Intrinsics.checkNotNull(workingNote);
        if (j == workingNote.getNoteId()) {
            Intrinsics.areEqual(this.mAiData, this.mOriginalContent);
        }
    }

    @Override // com.miui.notes.ai.editor.IAiEditor
    public void onDataLoading() {
        RequestType requestType = UserInputHolder.getInstance().getRequestType();
        if (requestType == RequestType.TYPE_SUMMARY || requestType == RequestType.TYPE_INPUT) {
            return;
        }
        NoteAiFloatingWindow noteAiFloatingWindow = mNoteAiFloatingWindow;
        if (noteAiFloatingWindow != null) {
            Intrinsics.checkNotNull(noteAiFloatingWindow);
            if (noteAiFloatingWindow.getInTitle()) {
                return;
            }
        }
        if (this.mEditor != null) {
            if (requestType == RequestType.TYPE_RECOMMEND) {
                if (TextUtils.isEmpty(this.mOriginalContent)) {
                    WebRichEditor webRichEditor = this.mEditor;
                    Intrinsics.checkNotNull(webRichEditor);
                    webRichEditor.toggleEmptyLoading(false);
                    return;
                }
                return;
            }
            WebRichEditor webRichEditor2 = this.mEditor;
            Intrinsics.checkNotNull(webRichEditor2);
            webRichEditor2.drawLoadingGradient();
        }
    }

    @Override // com.miui.notes.ai.editor.IAiEditor
    public void beforeRefreshNewContent() {
        WebRichEditor webRichEditor = this.mEditor;
        if (webRichEditor != null) {
            Intrinsics.checkNotNull(webRichEditor);
            webRichEditor.beforeRefreshNewContent();
        }
    }

    @Override // com.miui.notes.ai.editor.IAiEditor
    public void setDataWithConfig(String data, String title) {
        Intrinsics.checkNotNullParameter(data, "data");
        WebRichEditor webRichEditor = this.mEditor;
        if (webRichEditor != null) {
            Intrinsics.checkNotNull(webRichEditor);
            webRichEditor.setDataWithConfig(data, title, null, false);
        }
    }

    @Override // com.miui.notes.ai.editor.IAiEditor
    public void setData(String data, String title) {
        WebRichEditor webRichEditor = this.mEditor;
        if (webRichEditor != null) {
            Intrinsics.checkNotNull(webRichEditor);
            webRichEditor.setData(data, title, null, false, null);
        }
    }

    @Override // com.miui.notes.ai.editor.IAiEditor
    public void refreshNewContent() {
        WebRichEditor webRichEditor = this.mEditor;
        if (webRichEditor != null) {
            Intrinsics.checkNotNull(webRichEditor);
            webRichEditor.refreshNewContent();
            showWaterMark(true);
        }
    }

    @Override // com.miui.notes.ai.editor.IAiEditor
    public void closeCorrectionPop() {
        WebRichEditor webRichEditor = this.mEditor;
        if (webRichEditor != null) {
            Intrinsics.checkNotNull(webRichEditor);
            webRichEditor.closeCorrectionPop();
        }
    }

    @Override // com.miui.notes.ai.editor.IAiEditor
    public void clearFlowStatus() {
        WebRichEditor webRichEditor = this.mEditor;
        if (webRichEditor != null) {
            Intrinsics.checkNotNull(webRichEditor);
            webRichEditor.closeCorrectionPop();
        }
    }

    @Override // com.miui.notes.ai.editor.IAiEditor
    public void stopInput(String endTip) {
        WebRichEditor webRichEditor = this.mEditor;
        if (webRichEditor != null) {
            Intrinsics.checkNotNull(webRichEditor);
            webRichEditor.stopAiInput(endTip);
            WebRichEditor webRichEditor2 = this.mEditor;
            Intrinsics.checkNotNull(webRichEditor2);
            webRichEditor2.removeSelectionColorBg();
        }
    }

    public final void onUndoRedoStateChanged(final boolean undoEnable, final boolean redoEnable) {
        Logger.INSTANCE.d(TAG, "onUndoRedoStateChanged");
        this.mHandler.post(new Runnable() { // from class: com.miui.notes.basefeature.noteedit.BaseHybridNoteEditFragment$$ExternalSyntheticLambda8
            @Override // java.lang.Runnable
            public final void run() {
                BaseHybridNoteEditFragment.onUndoRedoStateChanged$lambda$66(this.f$0, undoEnable, redoEnable);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void onUndoRedoStateChanged$lambda$66(BaseHybridNoteEditFragment baseHybridNoteEditFragment, boolean z, boolean z2) {
        WebRichEditor webRichEditor = baseHybridNoteEditFragment.mEditor;
        if (webRichEditor != null) {
            Intrinsics.checkNotNull(webRichEditor);
            webRichEditor.onUndoRedoStateChanged(z, z2);
        }
    }

    public final void onTextStyleChanged(final TextStatusBean statusBean) {
        Logger.INSTANCE.d(TAG, "onTextStyleChanged");
        this.mHandler.post(new Runnable() { // from class: com.miui.notes.basefeature.noteedit.BaseHybridNoteEditFragment$$ExternalSyntheticLambda5
            @Override // java.lang.Runnable
            public final void run() {
                BaseHybridNoteEditFragment.onTextStyleChanged$lambda$67(this.f$0, statusBean);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void onTextStyleChanged$lambda$67(BaseHybridNoteEditFragment baseHybridNoteEditFragment, TextStatusBean textStatusBean) {
        if (baseHybridNoteEditFragment.mEditor == null || baseHybridNoteEditFragment.mInAiMode) {
            return;
        }
        WebRichEditor webRichEditor = baseHybridNoteEditFragment.mEditor;
        Intrinsics.checkNotNull(webRichEditor);
        webRichEditor.onTextStyleChanged(textStatusBean);
    }

    public final void performHaptic(final int typeId) {
        if (getActivity() != null) {
            this.mHandler.post(new Runnable() { // from class: com.miui.notes.basefeature.noteedit.BaseHybridNoteEditFragment$$ExternalSyntheticLambda53
                @Override // java.lang.Runnable
                public final void run() {
                    BaseHybridNoteEditFragment.performHaptic$lambda$68(this.f$0, typeId);
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void performHaptic$lambda$68(BaseHybridNoteEditFragment baseHybridNoteEditFragment, int i) {
        HapticFeedbackTool companion = HapticFeedbackTool.INSTANCE.getInstance();
        RichEditorWebView richEditorWebView = baseHybridNoteEditFragment.mEditorWebView;
        Intrinsics.checkNotNull(richEditorWebView);
        companion.performHapticByType(richEditorWebView.getView(), i);
    }

    public final void onTitleChanged(String title) {
        Logger.INSTANCE.d(TAG, "onTitleChanged");
        WebRichEditor webRichEditor = this.mEditor;
        if (webRichEditor == null || this.mNotSaveData) {
            Logger.INSTANCE.d(TAG, "onTitleChanged,title null or " + this.mNotSaveData);
        } else {
            Intrinsics.checkNotNull(webRichEditor);
            webRichEditor.onTitleChanged(title);
            asyncSaveNote();
        }
    }

    public final void onSaveData(final String richText) {
        Logger.INSTANCE.d(TAG, "onSavaData mNotSaveData：" + this.mNotSaveData);
        if (this.mNotSaveData) {
            this.mAiData = richText;
            Logger.INSTANCE.d(TAG, "onSavaData return");
            return;
        }
        if (NoteEntityHelper.isContentEmpty(richText)) {
            richText = "";
        }
        WebRichEditor webRichEditor = this.mEditor;
        if (webRichEditor == null) {
            return;
        }
        Intrinsics.checkNotNull(webRichEditor);
        Intrinsics.checkNotNull(richText);
        boolean zOnSaveData = webRichEditor.onSaveData(richText);
        asyncSaveNote();
        if (zOnSaveData && isSupportAIRecorder() && this.mWorkingNote != null) {
            WorkingNote workingNote = this.mWorkingNote;
            Intrinsics.checkNotNull(workingNote);
            if (!Intrinsics.areEqual(workingNote.getContent(), richText)) {
                WorkingNote workingNote2 = this.mWorkingNote;
                Intrinsics.checkNotNull(workingNote2);
                workingNote2.setWorkingText(richText);
            }
            WorkingNote workingNote3 = this.mWorkingNote;
            Intrinsics.checkNotNull(workingNote3);
            if (workingNote3.getNoteId() != -2) {
                WorkingNote workingNote4 = this.mWorkingNote;
                Intrinsics.checkNotNull(workingNote4);
                workingNote4.saveNote(true);
            }
        }
        if (getActivity() != null) {
            requireActivity().runOnUiThread(new Runnable() { // from class: com.miui.notes.basefeature.noteedit.BaseHybridNoteEditFragment$$ExternalSyntheticLambda34
                @Override // java.lang.Runnable
                public final void run() {
                    BaseHybridNoteEditFragment.onSaveData$lambda$69(this.f$0, richText);
                }
            });
        }
        if (this.mAiChooserMenu != null) {
            this.mAiChooserMenu.dismiss();
            this.mAiChooserMenu = null;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void onSaveData$lambda$69(BaseHybridNoteEditFragment baseHybridNoteEditFragment, String str) {
        WebRichEditor webRichEditor = baseHybridNoteEditFragment.mEditor;
        Intrinsics.checkNotNull(webRichEditor);
        webRichEditor.onAiTextStyleNeedChanged(!(str.length() == 0));
        if (baseHybridNoteEditFragment.mWorkingNote != null) {
            WebRichEditor webRichEditor2 = baseHybridNoteEditFragment.mEditor;
            Intrinsics.checkNotNull(webRichEditor2);
            WorkingNote workingNote = baseHybridNoteEditFragment.mWorkingNote;
            Intrinsics.checkNotNull(workingNote);
            webRichEditor2.updateExtraInto(workingNote.getExtraInfo());
        }
        baseHybridNoteEditFragment.closeTriggerView();
    }

    public final void onUpdateViewState(boolean canSelect, boolean canQuery, boolean canPhrase, boolean canTranslate, String selectData, String htmlData) {
        if (this.mEditor == null) {
            return;
        }
        if (selectData != null && selectData.length() > 0) {
            NoteContentStat.IS_CLICKED = true;
            NoteContentStat.ENTER_EDIT_WAY = AITextWidgetStat.CHOOSE_TEXT;
            canQuery = true;
            canTranslate = true;
        } else {
            NoteContentStat.IS_CLICKED = true;
            NoteContentStat.ENTER_EDIT_WAY = AITextWidgetStat.LONG_TOUCH_CURSOR;
        }
        WebRichEditor webRichEditor = this.mEditor;
        Intrinsics.checkNotNull(webRichEditor);
        webRichEditor.updateViewState(canSelect, canQuery, canPhrase, canTranslate, selectData, htmlData);
    }

    public final void onChangeToMindMap() {
        Logger.INSTANCE.d(TAG, "onChangeToMindMap");
        if (getActivity() != null) {
            this.mHandler.post(new Runnable() { // from class: com.miui.notes.basefeature.noteedit.BaseHybridNoteEditFragment$$ExternalSyntheticLambda17
                @Override // java.lang.Runnable
                public final void run() {
                    BaseHybridNoteEditFragment.onChangeToMindMap$lambda$70(this.f$0);
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void onChangeToMindMap$lambda$70(BaseHybridNoteEditFragment baseHybridNoteEditFragment) {
        if (RomUtils.isPadDevice() || RomUtils.isFoldDevice()) {
            baseHybridNoteEditFragment.onChangeToMindNote();
        } else {
            baseHybridNoteEditFragment.delayChangeToMindNote();
        }
    }

    private final void startAiTextWidget(String value, Rect rect, Activity activity) {
        String content;
        Logger.INSTANCE.i(TAG, "startAiTextWidget  with value");
        mFullScreen = getActivity() != null && requireActivity().isInMultiWindowMode();
        if (PreferenceUtils.isAcceptedMode(getContext())) {
            String strExtractPlainText = NoteSchemaUtil.extractPlainText(value);
            Intrinsics.checkNotNullExpressionValue(strExtractPlainText, "extractPlainText(...)");
            String str = strExtractPlainText;
            int length = str.length() - 1;
            int i = 0;
            boolean z = false;
            while (i <= length) {
                boolean z2 = Intrinsics.compare((int) str.charAt(!z ? i : length), 32) <= 0;
                if (z) {
                    if (!z2) {
                        break;
                    } else {
                        length--;
                    }
                } else if (z2) {
                    i++;
                } else {
                    z = true;
                }
            }
            if (TextUtils.isEmpty(str.subSequence(i, length + 1).toString())) {
                Activity activity2 = activity;
                if (!com.miui.notes.ai.utils.RomUtils.isSupportLLMCotAI(activity2) && !com.miui.notes.ai.utils.RomUtils.isLLMAIWriting(activity2)) {
                    Logger.INSTANCE.i(TAG, "startLocalAiTextWidget with no value");
                    Toast.makeText(getContext(), getString(R.string.select_text_before_using), 0).show();
                    return;
                }
            }
        }
        if (mNoteAiFloatingWindow != null) {
            if (this.mWorkingNote != null) {
                WorkingNote workingNote = this.mWorkingNote;
                Intrinsics.checkNotNull(workingNote);
                content = workingNote.getContent();
            } else {
                content = "";
            }
            if (!TextUtils.isEmpty(content) && !this.mNeedSelectAll) {
                Logger.INSTANCE.i(TAG, "no text");
                WebRichEditor webRichEditor = this.mEditor;
                Intrinsics.checkNotNull(webRichEditor);
                webRichEditor.drawSelectionFirst();
            }
            WebRichEditor webRichEditor2 = this.mEditor;
            Intrinsics.checkNotNull(webRichEditor2);
            webRichEditor2.showSelectionEnhanced();
            if (this.mWorkingNote != null) {
                NoteAiFloatingWindow noteAiFloatingWindow = mNoteAiFloatingWindow;
                Intrinsics.checkNotNull(noteAiFloatingWindow);
                String strImgConvertToAncient = NoteSchema.imgConvertToAncient(value);
                int taskId = activity.getTaskId();
                String packageName = activity.getPackageName();
                WorkingNote workingNote2 = this.mWorkingNote;
                Intrinsics.checkNotNull(workingNote2);
                String title = workingNote2.getTitle();
                WorkingNote workingNote3 = this.mWorkingNote;
                Intrinsics.checkNotNull(workingNote3);
                noteAiFloatingWindow.showAiFloatingWindow(strImgConvertToAncient, rect, taskId, packageName, title, workingNote3.getContent(), this.mIsFromPrivate);
            } else {
                NoteAiFloatingWindow noteAiFloatingWindow2 = mNoteAiFloatingWindow;
                Intrinsics.checkNotNull(noteAiFloatingWindow2);
                noteAiFloatingWindow2.showAiFloatingWindow(NoteSchema.imgConvertToAncient(value), rect, activity.getTaskId(), activity.getPackageName(), "", "", this.mIsFromPrivate);
            }
            String strImgConvertToAncient2 = NoteSchema.imgConvertToAncient(value);
            Intrinsics.checkNotNullExpressionValue(strImgConvertToAncient2, "imgConvertToAncient(...)");
            reportAiClick(strImgConvertToAncient2, activity);
        }
    }

    /* JADX WARN: Code duplicated, block: B:16:0x0062  */
    /* JADX WARN: Code duplicated, block: B:17:0x006c  */
    /* JADX WARN: Code duplicated, block: B:20:0x0085  */
    /* JADX WARN: Code duplicated, block: B:21:0x0089  */
    /* JADX WARN: Code duplicated, block: B:24:0x009a  */
    /* JADX WARN: Code duplicated, block: B:25:0x009d  */
    /* JADX WARN: Code duplicated, block: B:28:0x00a4  */
    /* JADX WARN: Code duplicated, block: B:30:0x00b2  */
    /* JADX WARN: Code duplicated, block: B:33:0x00c9  */
    /* JADX WARN: Code duplicated, block: B:35:0x00db  */
    private final void reportAiClick(String value, Activity activity) {
        int i;
        int plainTextCount;
        int themeId;
        NoteAiFloatingWindow noteAiFloatingWindow;
        String str;
        String str2;
        int plainTextCount2;
        String str3;
        String str4;
        String str5;
        int themeId2 = 0;
        if (this.mNotSaveData && (str5 = this.mAiData) != null) {
            plainTextCount = NoteSchemaUtil.getPlainTextCount(NoteSchema.convertToWeb(str5));
            AiFloatingWindow.mContentType = NoteSchemaUtil.getContentType(NoteSchema.convertToWeb(this.mAiData));
        } else {
            if (this.mWorkingNote != null) {
                WorkingNote workingNote = this.mWorkingNote;
                Intrinsics.checkNotNull(workingNote);
                if (workingNote.getContent() != null) {
                    WorkingNote workingNote2 = this.mWorkingNote;
                    Intrinsics.checkNotNull(workingNote2);
                    plainTextCount = NoteSchemaUtil.getPlainTextCount(NoteSchema.convertToWeb(workingNote2.getContent()));
                    WorkingNote workingNote3 = this.mWorkingNote;
                    Intrinsics.checkNotNull(workingNote3);
                    AiFloatingWindow.mContentType = NoteSchemaUtil.getContentType(NoteSchema.convertToWeb(workingNote3.getContent()));
                }
                AiFloatingWindow.mContentStatus = getContentStatus();
                AiFloatingWindow.mContentTextCount = i;
                if (this.mWorkingNote != null) {
                    WorkingNote workingNote4 = this.mWorkingNote;
                    Intrinsics.checkNotNull(workingNote4);
                    themeId = workingNote4.getThemeId();
                } else {
                    themeId = 0;
                }
                AiFloatingWindow.mContentStyle = themeId;
                NoteAiFloatingWindow noteAiFloatingWindow2 = mNoteAiFloatingWindow;
                Intrinsics.checkNotNull(noteAiFloatingWindow2);
                AiFloatingWindow.mInTitle = noteAiFloatingWindow2.getInTitle();
                noteAiFloatingWindow = mNoteAiFloatingWindow;
                Intrinsics.checkNotNull(noteAiFloatingWindow);
                if (noteAiFloatingWindow.getInTitle()) {
                    str = "title";
                } else {
                    str = "content";
                }
                str2 = str;
                plainTextCount2 = NoteSchemaUtil.getPlainTextCount(value);
                if (value.length() == 0) {
                    str3 = AITextWidgetStat.LONG_TOUCH_CURSOR;
                } else {
                    str3 = AITextWidgetStat.CHOOSE_TEXT;
                }
                str4 = str3;
                if (this.isFromNoteToolBarEnter) {
                    String str6 = AiFloatingWindow.mContentType;
                    String contentStatus = getContentStatus();
                    String packageName = activity.getPackageName();
                    if (this.mWorkingNote != null) {
                        WorkingNote workingNote5 = this.mWorkingNote;
                        Intrinsics.checkNotNull(workingNote5);
                        themeId2 = workingNote5.getThemeId();
                    }
                    AITextWidgetStat.reportAiNoteToolWidgetClick(str6, contentStatus, i, packageName, String.valueOf(themeId2), str4, str2, plainTextCount2, getContext());
                    return;
                }
                String str7 = AiFloatingWindow.mContentType;
                String contentStatus2 = getContentStatus();
                String strValueOf = String.valueOf(i);
                String packageName2 = activity.getPackageName();
                if (this.mWorkingNote != null) {
                    WorkingNote workingNote6 = this.mWorkingNote;
                    Intrinsics.checkNotNull(workingNote6);
                    themeId2 = workingNote6.getThemeId();
                }
                AITextWidgetStat.reportAiEditorBarClick(str7, contentStatus2, strValueOf, str4, packageName2, String.valueOf(themeId2), str2, plainTextCount2, getContext());
            }
            i = 0;
            AiFloatingWindow.mContentStatus = getContentStatus();
            AiFloatingWindow.mContentTextCount = i;
            if (this.mWorkingNote != null) {
                WorkingNote workingNote7 = this.mWorkingNote;
                Intrinsics.checkNotNull(workingNote7);
                themeId = workingNote7.getThemeId();
            } else {
                themeId = 0;
            }
            AiFloatingWindow.mContentStyle = themeId;
            NoteAiFloatingWindow noteAiFloatingWindow3 = mNoteAiFloatingWindow;
            Intrinsics.checkNotNull(noteAiFloatingWindow3);
            AiFloatingWindow.mInTitle = noteAiFloatingWindow3.getInTitle();
            noteAiFloatingWindow = mNoteAiFloatingWindow;
            Intrinsics.checkNotNull(noteAiFloatingWindow);
            if (noteAiFloatingWindow.getInTitle()) {
                str = "title";
            } else {
                str = "content";
            }
            str2 = str;
            plainTextCount2 = NoteSchemaUtil.getPlainTextCount(value);
            if (value.length() == 0) {
                str3 = AITextWidgetStat.LONG_TOUCH_CURSOR;
            } else {
                str3 = AITextWidgetStat.CHOOSE_TEXT;
            }
            str4 = str3;
            if (this.isFromNoteToolBarEnter) {
                String str8 = AiFloatingWindow.mContentType;
                String contentStatus3 = getContentStatus();
                String packageName3 = activity.getPackageName();
                if (this.mWorkingNote != null) {
                    WorkingNote workingNote8 = this.mWorkingNote;
                    Intrinsics.checkNotNull(workingNote8);
                    themeId2 = workingNote8.getThemeId();
                }
                AITextWidgetStat.reportAiNoteToolWidgetClick(str8, contentStatus3, i, packageName3, String.valueOf(themeId2), str4, str2, plainTextCount2, getContext());
                return;
            }
            String str9 = AiFloatingWindow.mContentType;
            String contentStatus4 = getContentStatus();
            String strValueOf2 = String.valueOf(i);
            String packageName4 = activity.getPackageName();
            if (this.mWorkingNote != null) {
                WorkingNote workingNote9 = this.mWorkingNote;
                Intrinsics.checkNotNull(workingNote9);
                themeId2 = workingNote9.getThemeId();
            }
            AITextWidgetStat.reportAiEditorBarClick(str9, contentStatus4, strValueOf2, str4, packageName4, String.valueOf(themeId2), str2, plainTextCount2, getContext());
        }
        i = plainTextCount;
        AiFloatingWindow.mContentStatus = getContentStatus();
        AiFloatingWindow.mContentTextCount = i;
        if (this.mWorkingNote != null) {
            WorkingNote workingNote10 = this.mWorkingNote;
            Intrinsics.checkNotNull(workingNote10);
            themeId = workingNote10.getThemeId();
        } else {
            themeId = 0;
        }
        AiFloatingWindow.mContentStyle = themeId;
        NoteAiFloatingWindow noteAiFloatingWindow4 = mNoteAiFloatingWindow;
        Intrinsics.checkNotNull(noteAiFloatingWindow4);
        AiFloatingWindow.mInTitle = noteAiFloatingWindow4.getInTitle();
        noteAiFloatingWindow = mNoteAiFloatingWindow;
        Intrinsics.checkNotNull(noteAiFloatingWindow);
        if (noteAiFloatingWindow.getInTitle()) {
            str = "title";
        } else {
            str = "content";
        }
        str2 = str;
        plainTextCount2 = NoteSchemaUtil.getPlainTextCount(value);
        if (value.length() == 0) {
            str3 = AITextWidgetStat.LONG_TOUCH_CURSOR;
        } else {
            str3 = AITextWidgetStat.CHOOSE_TEXT;
        }
        str4 = str3;
        if (this.isFromNoteToolBarEnter) {
            String str10 = AiFloatingWindow.mContentType;
            String contentStatus5 = getContentStatus();
            String packageName5 = activity.getPackageName();
            if (this.mWorkingNote != null) {
                WorkingNote workingNote11 = this.mWorkingNote;
                Intrinsics.checkNotNull(workingNote11);
                themeId2 = workingNote11.getThemeId();
            }
            AITextWidgetStat.reportAiNoteToolWidgetClick(str10, contentStatus5, i, packageName5, String.valueOf(themeId2), str4, str2, plainTextCount2, getContext());
            return;
        }
        String str11 = AiFloatingWindow.mContentType;
        String contentStatus6 = getContentStatus();
        String strValueOf3 = String.valueOf(i);
        String packageName6 = activity.getPackageName();
        if (this.mWorkingNote != null) {
            WorkingNote workingNote12 = this.mWorkingNote;
            Intrinsics.checkNotNull(workingNote12);
            themeId2 = workingNote12.getThemeId();
        }
        AITextWidgetStat.reportAiEditorBarClick(str11, contentStatus6, strValueOf3, str4, packageName6, String.valueOf(themeId2), str2, plainTextCount2, getContext());
    }

    /* JADX WARN: Code duplicated, block: B:21:0x0087  */
    private final void trackReportEditMode(Intent intent, boolean isQuitEditMode) {
        String contentType;
        String strValueOf;
        int i;
        String str;
        String str2;
        int themeId;
        int themeId2;
        if (intent == null) {
            return;
        }
        Bundle extras = intent.getExtras();
        List<FolderItemEntity> list = GlobalStat.Status.folderList;
        String source = getSource(extras);
        String strValueOf2 = String.valueOf(System.currentTimeMillis());
        String str3 = "否";
        if (this.mWorkingNote != null) {
            WorkingNote workingNote = this.mWorkingNote;
            Intrinsics.checkNotNull(workingNote);
            if (workingNote.getContent() != null) {
                WorkingNote workingNote2 = this.mWorkingNote;
                Intrinsics.checkNotNull(workingNote2);
                int plainTextCount = NoteSchemaUtil.getPlainTextCount(NoteSchema.convertToWeb(workingNote2.getContent()));
                WorkingNote workingNote3 = this.mWorkingNote;
                Intrinsics.checkNotNull(workingNote3);
                contentType = NoteSchemaUtil.getContentType(NoteSchema.convertToWeb(workingNote3.getContent()));
                WorkingNote workingNote4 = this.mWorkingNote;
                Intrinsics.checkNotNull(workingNote4);
                String str4 = workingNote4.getAlertDate() > 0 ? "是" : "否";
                WorkingNote workingNote5 = this.mWorkingNote;
                Intrinsics.checkNotNull(workingNote5);
                String str5 = workingNote5.isHidden() ? "是" : "否";
                str3 = NoteContentStat.IS_STICK ? "是" : "否";
                WorkingNote workingNote6 = this.mWorkingNote;
                Intrinsics.checkNotNull(workingNote6);
                i = plainTextCount;
                str = str3;
                str3 = str4;
                strValueOf = String.valueOf(workingNote6.getCreatedDate());
                str2 = str5;
            } else {
                contentType = "text";
                strValueOf = strValueOf2;
                i = 0;
                str = "否";
                str2 = str;
            }
        } else {
            contentType = "text";
            strValueOf = strValueOf2;
            i = 0;
            str = "否";
            str2 = str;
        }
        String str6 = contentType;
        if (i == 0) {
            NoteContentStat.ENTER_EDIT_WAY = AITextWidgetStat.NEW_NOTE;
        }
        if (i != 0 && !NoteContentStat.IS_CLICKED) {
            NoteContentStat.ENTER_EDIT_WAY = AITextWidgetStat.CLICK_EDIT_ENTER;
        }
        String folderName = "全部";
        String str7 = NoteContentStat.NOTES_ALL_NAME;
        if (extras != null) {
            long j = extras.getLong("com.miui.notes.folder_id");
            if (j != -4097) {
                str7 = NoteContentStat.NOTES_TYPE_NAME;
            }
            if (list != null && !list.isEmpty()) {
                for (FolderItemEntity folderItemEntity : GlobalStat.Status.folderList) {
                    if (folderItemEntity.getFolderId() == j) {
                        folderName = folderItemEntity.getFolderName();
                    }
                }
            }
        }
        if (isQuitEditMode) {
            if (NoteContentStat.IS_BTN_BACK) {
                NoteContentStat.QUIT_EDIT_WAY = NoteContentStat.QUIT_WAY_BTN_BACK;
            } else {
                NoteContentStat.QUIT_EDIT_WAY = "SystemBack";
            }
            int iCurrentTimeMillis = (int) (System.currentTimeMillis() - this.mEnterTimeForEditMode);
            String str8 = str3;
            String str9 = str;
            String str10 = str7;
            String noteContentStatus = getNoteContentStatus();
            if (this.mWorkingNote != null) {
                WorkingNote workingNote7 = this.mWorkingNote;
                Intrinsics.checkNotNull(workingNote7);
                themeId2 = workingNote7.getThemeId();
            } else {
                themeId2 = 0;
            }
            NotesTextStat.reportQuitEditModeEvent(source, str10, folderName, str8, str9, str2, str6, noteContentStatus, i, strValueOf, themeId2, NoteContentStat.ENTER_EDIT_WAY, NoteContentStat.QUIT_EDIT_WAY, iCurrentTimeMillis);
            return;
        }
        String str11 = str3;
        String str12 = str;
        String str13 = str7;
        NoteContentStat.IS_BTN_BACK = false;
        this.mEnterTimeForEditMode = System.currentTimeMillis();
        String noteContentStatus2 = getNoteContentStatus();
        if (this.mWorkingNote != null) {
            WorkingNote workingNote8 = this.mWorkingNote;
            Intrinsics.checkNotNull(workingNote8);
            themeId = workingNote8.getThemeId();
        } else {
            themeId = 0;
        }
        NotesTextStat.reportEnterEditModeEvent(source, str13, folderName, str11, str12, str2, str6, noteContentStatus2, i, strValueOf, themeId, NoteContentStat.ENTER_EDIT_WAY);
    }

    protected final void showXiaoaiAcceptDialog(Activity activity, final PermissionNewUtils.AcceptListener acceptListener) {
        Intrinsics.checkNotNullParameter(acceptListener, "acceptListener");
        Logger.INSTANCE.d(TAG, "showXiaoaiAcceptDialog ," + acceptListener + ",act:" + activity);
        if (activity == null) {
            return;
        }
        View viewInflate = activity.getLayoutInflater().inflate(R.layout.new_custom_guide_dialog, (ViewGroup) null);
        TextView textView = (TextView) viewInflate.findViewById(R.id.main_purpose);
        textView.setAutoLinkMask(Linkify.ALL);
        textView.setText(R.string.xiaomi_account_agree_desc);
        textView.setMovementMethod(LinkMovementMethod.getInstance());
        new AlertDialog.Builder(activity).setTitle(R.string.xiaomi_account_agree_title).setView(viewInflate).setPositiveButton(R.string.xiaomi_account_agree_confirm_ok, new DialogInterface.OnClickListener() { // from class: com.miui.notes.basefeature.noteedit.BaseHybridNoteEditFragment$$ExternalSyntheticLambda37
            @Override // android.content.DialogInterface.OnClickListener
            public final void onClick(DialogInterface dialogInterface, int i) {
                this.f$0.tryLoginXiaomiAccount(acceptListener);
            }
        }).setNegativeButton(R.string.ai_text_confirm_cancel, new DialogInterface.OnClickListener() { // from class: com.miui.notes.basefeature.noteedit.BaseHybridNoteEditFragment$$ExternalSyntheticLambda38
            @Override // android.content.DialogInterface.OnClickListener
            public final void onClick(DialogInterface dialogInterface, int i) {
                BaseHybridNoteEditFragment.showXiaoaiAcceptDialog$lambda$73(acceptListener, dialogInterface, i);
            }
        }).setCancelable(true).setOnCancelListener(new DialogInterface.OnCancelListener() { // from class: com.miui.notes.basefeature.noteedit.BaseHybridNoteEditFragment$$ExternalSyntheticLambda39
            @Override // android.content.DialogInterface.OnCancelListener
            public final void onCancel(DialogInterface dialogInterface) {
                BaseHybridNoteEditFragment.showXiaoaiAcceptDialog$lambda$74(acceptListener, dialogInterface);
            }
        }).show();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void showXiaoaiAcceptDialog$lambda$73(PermissionNewUtils.AcceptListener acceptListener, DialogInterface dialogInterface, int i) {
        Logger.INSTANCE.d(TAG, "showXiaoaiAcceptDialog->login btn click");
        acceptListener.accept();
        Intrinsics.checkNotNull(dialogInterface);
        dialogInterface.dismiss();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void showXiaoaiAcceptDialog$lambda$74(PermissionNewUtils.AcceptListener acceptListener, DialogInterface dialogInterface) {
        Logger.INSTANCE.d(TAG, "showXiaoaiAcceptDialog->cancel btn click");
        acceptListener.reject();
        Intrinsics.checkNotNull(dialogInterface);
        dialogInterface.dismiss();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void tryLoginXiaomiAccount(PermissionNewUtils.AcceptListener acceptListener) {
        Logger.INSTANCE.d(TAG, "tryLoginXiaomiAccount" + this.toAddAccountLauncher);
        AccountManager accountManager = AccountManager.get(BaseNoteApp.INSTANCE.getInstance());
        AccountMgrCallback accountMgrCallback = new AccountMgrCallback(acceptListener, this.toAddAccountLauncher);
        this.mAccountManagerCallback = accountMgrCallback;
        accountManager.addAccount("com.xiaomi", "hyperos_sec_common", null, null, null, accountMgrCallback, null);
    }

    protected final void showOverlayPermissionDialog(final Activity activity) {
        Intrinsics.checkNotNullParameter(activity, "activity");
        View viewInflate = activity.getLayoutInflater().inflate(R.layout.new_custom_guide_dialog, (ViewGroup) null);
        TextView textView = (TextView) viewInflate.findViewById(R.id.main_purpose);
        textView.setAutoLinkMask(Linkify.ALL);
        textView.setText(R.string.xiaomi_overlay_agree_desc);
        textView.setMovementMethod(LinkMovementMethod.getInstance());
        new AlertDialog.Builder(activity).setTitle(R.string.xiaomi_overlay_agree_title).setView(viewInflate).setPositiveButton(R.string.xiaomi_overlay_agree_confirm_ok, new DialogInterface.OnClickListener() { // from class: com.miui.notes.basefeature.noteedit.BaseHybridNoteEditFragment$$ExternalSyntheticLambda23
            @Override // android.content.DialogInterface.OnClickListener
            public final void onClick(DialogInterface dialogInterface, int i) {
                PermissionNewUtils.applyOverlayPermission(activity);
            }
        }).setNegativeButton(R.string.ai_text_confirm_cancel, new DialogInterface.OnClickListener() { // from class: com.miui.notes.basefeature.noteedit.BaseHybridNoteEditFragment$$ExternalSyntheticLambda24
            @Override // android.content.DialogInterface.OnClickListener
            public final void onClick(DialogInterface dialogInterface, int i) {
                BaseHybridNoteEditFragment.showOverlayPermissionDialog$lambda$76(dialogInterface, i);
            }
        }).setCancelable(true).setOnCancelListener(new DialogInterface.OnCancelListener() { // from class: com.miui.notes.basefeature.noteedit.BaseHybridNoteEditFragment$$ExternalSyntheticLambda25
            @Override // android.content.DialogInterface.OnCancelListener
            public final void onCancel(DialogInterface dialogInterface) {
                BaseHybridNoteEditFragment.showOverlayPermissionDialog$lambda$77(dialogInterface);
            }
        }).show();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void showOverlayPermissionDialog$lambda$76(DialogInterface dialogInterface, int i) {
        Intrinsics.checkNotNull(dialogInterface);
        dialogInterface.dismiss();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void showOverlayPermissionDialog$lambda$77(DialogInterface dialogInterface) {
        Intrinsics.checkNotNull(dialogInterface);
        dialogInterface.dismiss();
    }

    /* JADX INFO: compiled from: BaseHybridNoteEditFragment.kt */
    @Metadata(d1 = {"\u0000(\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u0002\n\u0002\b\u0002\b\u0002\u0018\u00002\u00020\u0001B\u0019\u0012\b\u0010\u0002\u001a\u0004\u0018\u00010\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005¢\u0006\u0004\b\u0006\u0010\u0007J\b\u0010\u0011\u001a\u00020\u0012H\u0016J\b\u0010\u0013\u001a\u00020\u0012H\u0016R\u001a\u0010\u0004\u001a\u00020\u0005X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0004\u0010\b\"\u0004\b\t\u0010\nR$\u0010\u000b\u001a\f\u0012\u0006\u0012\u0004\u0018\u00010\u0003\u0018\u00010\fX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\r\u0010\u000e\"\u0004\b\u000f\u0010\u0010¨\u0006\u0014"}, d2 = {"Lcom/miui/notes/basefeature/noteedit/BaseHybridNoteEditFragment$NotesAcceptListener;", "Lcom/miui/notes/tool/PermissionNewUtils$AcceptListener;", "fragment", "Lcom/miui/notes/basefeature/noteedit/BaseHybridNoteEditFragment;", "isFirstEnter", "", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "(Lcom/miui/notes/basefeature/noteedit/BaseHybridNoteEditFragment;Z)V", "()Z", "setFirstEnter", "(Z)V", "weakReference", "Ljava/lang/ref/WeakReference;", "getWeakReference", "()Ljava/lang/ref/WeakReference;", "setWeakReference", "(Ljava/lang/ref/WeakReference;)V", "accept", "", "reject", "app_PhoneGlobalRelease"}, k = 1, mv = {2, 2, 0}, xi = 48)
    private static final class NotesAcceptListener implements PermissionNewUtils.AcceptListener {
        private boolean isFirstEnter;
        private WeakReference<BaseHybridNoteEditFragment> weakReference;

        @Override // com.miui.notes.tool.PermissionNewUtils.AcceptListener
        public void reject() {
        }

        public NotesAcceptListener(BaseHybridNoteEditFragment baseHybridNoteEditFragment, boolean z) {
            this.isFirstEnter = z;
            this.weakReference = new WeakReference<>(baseHybridNoteEditFragment);
        }

        /* JADX INFO: renamed from: isFirstEnter, reason: from getter */
        public final boolean getIsFirstEnter() {
            return this.isFirstEnter;
        }

        public final void setFirstEnter(boolean z) {
            this.isFirstEnter = z;
        }

        public final WeakReference<BaseHybridNoteEditFragment> getWeakReference() {
            return this.weakReference;
        }

        public final void setWeakReference(WeakReference<BaseHybridNoteEditFragment> weakReference) {
            this.weakReference = weakReference;
        }

        @Override // com.miui.notes.tool.PermissionNewUtils.AcceptListener
        public void accept() {
            BaseHybridNoteEditFragment baseHybridNoteEditFragment;
            WeakReference<BaseHybridNoteEditFragment> weakReference = this.weakReference;
            if (weakReference == null) {
                baseHybridNoteEditFragment = null;
            } else {
                Intrinsics.checkNotNull(weakReference);
                baseHybridNoteEditFragment = weakReference.get();
            }
            if (baseHybridNoteEditFragment != null) {
                Companion companion = BaseHybridNoteEditFragment.INSTANCE;
                FragmentActivity fragmentActivityRequireActivity = baseHybridNoteEditFragment.requireActivity();
                Intrinsics.checkNotNullExpressionValue(fragmentActivityRequireActivity, "requireActivity(...)");
                if (companion.isNeedDownloadOrUpdateLocalAi(fragmentActivityRequireActivity)) {
                    Companion companion2 = BaseHybridNoteEditFragment.INSTANCE;
                    FragmentActivity fragmentActivityRequireActivity2 = baseHybridNoteEditFragment.requireActivity();
                    Intrinsics.checkNotNullExpressionValue(fragmentActivityRequireActivity2, "requireActivity(...)");
                    companion2.showLocalDownloadDialog(fragmentActivityRequireActivity2);
                    return;
                }
                if (PermissionNewUtils.hasOverlayPermission(baseHybridNoteEditFragment.getActivity())) {
                    if (this.isFirstEnter) {
                        return;
                    }
                    FragmentActivity fragmentActivityRequireActivity3 = baseHybridNoteEditFragment.requireActivity();
                    Intrinsics.checkNotNullExpressionValue(fragmentActivityRequireActivity3, "requireActivity(...)");
                    baseHybridNoteEditFragment.gotoRealAITextWidget(fragmentActivityRequireActivity3, null);
                    return;
                }
                FragmentActivity fragmentActivityRequireActivity4 = baseHybridNoteEditFragment.requireActivity();
                Intrinsics.checkNotNullExpressionValue(fragmentActivityRequireActivity4, "requireActivity(...)");
                baseHybridNoteEditFragment.showOverlayPermissionDialog(fragmentActivityRequireActivity4);
            }
        }
    }

    protected final void processCta(Activity activity) {
        Intrinsics.checkNotNullParameter(activity, "activity");
        if (com.miui.notes.tool.PreferenceUtils.isAiCTAAccepted(getContext()) && ((PreferenceUtils.isInternalTestInviteAccepted(getContext()) || PreferenceUtils.isInternalTestInviteAcceptedForSpecial(getContext())) && AccountMgrCallback.INSTANCE.checkoutIsLoginXiaomiAccount() && PermissionNewUtils.hasOverlayPermission(getActivity()) && !INSTANCE.isNeedDownloadOrUpdateLocalAi(activity))) {
            Logger.INSTANCE.d(TAG, "processCta->startAiTextWidget");
            startAiTextWidget(this.mAITextUseValue, this.mAiTextUseRect, activity);
            return;
        }
        if (!com.miui.notes.tool.PreferenceUtils.isAiCTAAccepted(getContext())) {
            this.mCTAFirstAuthorization = true;
            if (RomUtils.isInternationalBuild()) {
                PermissionNewUtils.requestGlobalPermissionDialog(this.toGlobalPermissionLauncher);
            } else {
                PermissionNewUtils.showCtaDialog(this.toCtaBeforeAITextWidgetTask);
            }
        } else if (!PreferenceUtils.isInternalTestInviteAccepted(getContext())) {
            if (!PreferenceUtils.isInternalTestInviteAcceptedForSpecial(getContext())) {
                startActivity(new Intent(activity, (Class<?>) InteralTestInviteActivity.class));
            }
        } else if (!AccountMgrCallback.INSTANCE.checkoutIsLoginXiaomiAccount()) {
            showXiaoaiAcceptDialog(activity, new NotesAcceptListener(this, true));
        } else {
            Companion companion = INSTANCE;
            if (companion.isNeedDownloadOrUpdateLocalAi(activity)) {
                this.mLocalDownloadDialog = companion.showLocalDownloadDialog(activity);
            } else {
                showOverlayPermissionDialog(activity);
            }
        }
        onViewMode();
    }

    protected final void doAfterCTAAccepted(Activity activity) {
        Intrinsics.checkNotNullParameter(activity, "activity");
        if ((PreferenceUtils.isInternalTestInviteAccepted(getContext()) || PreferenceUtils.isInternalTestInviteAcceptedForSpecial(getContext())) && AccountMgrCallback.INSTANCE.checkoutIsLoginXiaomiAccount() && PermissionNewUtils.hasOverlayPermission(getActivity()) && !INSTANCE.isNeedDownloadOrUpdateLocalAi(activity)) {
            if (RomUtils.isInternationalBuild()) {
                Intent intent = activity.getIntent();
                if (!Intrinsics.areEqual(NoteIntent.TYPE_ACTION_SHOW_AI_TEXT_WIDGET, intent != null ? intent.getType() : null)) {
                    return;
                }
            }
            gotoRealAITextWidget(activity, null);
        } else if (!PreferenceUtils.isInternalTestInviteAccepted(getContext())) {
            if (!PreferenceUtils.isInternalTestInviteAcceptedForSpecial(getContext())) {
                startActivity(new Intent(activity, (Class<?>) InteralTestInviteActivity.class));
            }
        } else if (!AccountMgrCallback.INSTANCE.checkoutIsLoginXiaomiAccount()) {
            showXiaoaiAcceptDialog(activity, new NotesAcceptListener(this, true));
        } else {
            Companion companion = INSTANCE;
            if (companion.isNeedDownloadOrUpdateLocalAi(activity)) {
                this.mLocalDownloadDialog = companion.showLocalDownloadDialog(activity);
            } else {
                showOverlayPermissionDialog(activity);
            }
        }
        clearAiTextWidgetIntentType();
    }

    protected final void gotoRealAITextWidget(Activity activity, View view) {
        Intrinsics.checkNotNullParameter(activity, "activity");
        startAiTextWidget(this.mAITextUseValue, this.mAiTextUseRect, activity);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void showWaterMark(boolean show) {
        String str;
        ViewGroup viewGroup = (ViewGroup) getView();
        if (viewGroup == null) {
            return;
        }
        if (show) {
            if (this.mWaterMarkView == null) {
                this.mWaterMarkView = new WaterMarkView(BaseNoteApp.INSTANCE.getInstance());
            }
            Account xiaomiAccount = ExtraAccountManager.getXiaomiAccount(BaseNoteApp.INSTANCE.getInstance());
            if (xiaomiAccount == null) {
                Logger.INSTANCE.e(TAG, "showMarkView xiaomiAccount is null!!!");
            } else {
                String name = xiaomiAccount.name;
                Intrinsics.checkNotNullExpressionValue(name, "name");
                try {
                    str = CryptoUtils.encrypt(name);
                    Intrinsics.checkNotNullExpressionValue(str, "encrypt(...)");
                } catch (Exception e) {
                    Logger logger = Logger.INSTANCE;
                    String message = e.getMessage();
                    Intrinsics.checkNotNull(message);
                    logger.i(TAG, message);
                    e.printStackTrace();
                    byte[] bytes = name.getBytes(Charsets.UTF_8);
                    Intrinsics.checkNotNullExpressionValue(bytes, "getBytes(...)");
                    byte[] bArrEncode = Base64.encode(bytes, 0);
                    Intrinsics.checkNotNullExpressionValue(bArrEncode, "encode(...)");
                    str = new String(bArrEncode, Charsets.UTF_8);
                }
                WaterMarkView waterMarkView = this.mWaterMarkView;
                Intrinsics.checkNotNull(waterMarkView);
                waterMarkView.setUserId(str);
            }
            WaterMarkView waterMarkView2 = this.mWaterMarkView;
            Intrinsics.checkNotNull(waterMarkView2);
            if (waterMarkView2.getParent() == null) {
                viewGroup.addView(this.mWaterMarkView);
                return;
            }
            return;
        }
        WaterMarkView waterMarkView3 = this.mWaterMarkView;
        if (waterMarkView3 != null) {
            viewGroup.removeView(waterMarkView3);
        }
    }

    public final void resetToolBarStaus() {
        IBaseNoteEditToolbarController iBaseNoteEditToolbarController = this.mEditToolbarController;
        if (iBaseNoteEditToolbarController != null) {
            iBaseNoteEditToolbarController.resetRichTextStatus();
        }
    }

    public final boolean isNewNoteFromMiShow() {
        Intent intent;
        FragmentActivity activity = getActivity();
        return Intrinsics.areEqual(NoteIntent.TYPE_ACTION_SHOW_AI_TEXT_WIDGET, (activity == null || (intent = activity.getIntent()) == null) ? null : intent.getType());
    }

    /* JADX INFO: compiled from: BaseHybridNoteEditFragment.kt */
    @Metadata(d1 = {"\u0000L\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0005\n\u0002\u0010\u000b\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\t\b\u0002¢\u0006\u0004\b\u0002\u0010\u0003J\b\u0010\u0019\u001a\u00020\u001aH\u0007J\b\u0010\u001b\u001a\u00020\u001aH\u0007J\u0012\u0010\u001c\u001a\u0004\u0018\u00010\u001d2\u0006\u0010\u001e\u001a\u00020\u001fH\u0004J\u0010\u0010 \u001a\u00020\u000f2\u0006\u0010!\u001a\u00020\"H\u0002R\u000e\u0010\u0004\u001a\u00020\u0005X\u0084T¢\u0006\u0002\n\u0000R\u0014\u0010\u0006\u001a\u0004\u0018\u00010\u00078\u0004@\u0004X\u0085\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\b\u001a\u00020\tX\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\n\u001a\u00020\tX\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\u000b\u001a\u00020\tX\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\f\u001a\u00020\tX\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\r\u001a\u00020\u0005X\u0082T¢\u0006\u0002\n\u0000R\u001a\u0010\u000e\u001a\u00020\u000fX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0010\u0010\u0011\"\u0004\b\u0012\u0010\u0013R\u0014\u0010\u0014\u001a\u0004\u0018\u00010\u00158\u0006@\u0006X\u0087\u000e¢\u0006\u0002\n\u0000R\u001a\u0010\u0016\u001a\u00020\u000fX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0017\u0010\u0011\"\u0004\b\u0018\u0010\u0013¨\u0006#"}, d2 = {"Lcom/miui/notes/basefeature/noteedit/BaseHybridNoteEditFragment$Companion;", "", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "()V", "TAG", "", "mNoteAiFloatingWindow", "Lcom/miui/notes/ai/NoteAiFloatingWindow;", "STATE_ERROR_NONE", "", "STATE_ERROR_NO_CTA", "STATE_ERROR_NO_PERMISSION", "STATE_ERROR_UNKNOW", "EXTRA_KEY_IS_PRIVACY", "mFullScreen", "", "getMFullScreen", "()Z", "setMFullScreen", "(Z)V", "mNoteAiTextWidgetService", "Lcom/miui/notes/ai/NoteAiTextWidgetService;", "IN_TITLE", "getIN_TITLE", "setIN_TITLE", "removeAiFloatingWindow", "", "hideAiFloatingWindowForLock", "showLocalDownloadDialog", "Landroid/app/Dialog;", "activity", "Landroid/app/Activity;", "isNeedDownloadOrUpdateLocalAi", CoreConstants.CONTEXT_SCOPE_VALUE, "Landroid/content/Context;", "app_PhoneGlobalRelease"}, k = 1, mv = {2, 2, 0}, xi = 48)
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static final void showLocalDownloadDialog$lambda$2(DialogInterface dialogInterface) {
        }

        private Companion() {
        }

        public final boolean getMFullScreen() {
            return BaseHybridNoteEditFragment.mFullScreen;
        }

        public final void setMFullScreen(boolean z) {
            BaseHybridNoteEditFragment.mFullScreen = z;
        }

        public final boolean getIN_TITLE() {
            return BaseHybridNoteEditFragment.IN_TITLE;
        }

        public final void setIN_TITLE(boolean z) {
            BaseHybridNoteEditFragment.IN_TITLE = z;
        }

        @JvmStatic
        public final void removeAiFloatingWindow() {
            Logger.INSTANCE.d(BaseHybridNoteEditFragment.TAG, "removeAiFloatingWindow mNoteAiFloatingWindow: " + BaseHybridNoteEditFragment.mNoteAiFloatingWindow);
            if (BaseHybridNoteEditFragment.mNoteAiFloatingWindow != null) {
                NoteAiFloatingWindow noteAiFloatingWindow = BaseHybridNoteEditFragment.mNoteAiFloatingWindow;
                Intrinsics.checkNotNull(noteAiFloatingWindow);
                if (noteAiFloatingWindow.isShowing()) {
                    NoteAiFloatingWindow noteAiFloatingWindow2 = BaseHybridNoteEditFragment.mNoteAiFloatingWindow;
                    Intrinsics.checkNotNull(noteAiFloatingWindow2);
                    noteAiFloatingWindow2.closeWindow();
                }
            }
        }

        @JvmStatic
        public final void hideAiFloatingWindowForLock() {
            Logger.INSTANCE.d(BaseHybridNoteEditFragment.TAG, "hideAiFloatingWindowForLock mNoteAiFloatingWindow: " + BaseHybridNoteEditFragment.mNoteAiFloatingWindow);
            if (BaseHybridNoteEditFragment.mNoteAiFloatingWindow != null) {
                NoteAiFloatingWindow noteAiFloatingWindow = BaseHybridNoteEditFragment.mNoteAiFloatingWindow;
                Intrinsics.checkNotNull(noteAiFloatingWindow);
                if (noteAiFloatingWindow.isShowing()) {
                    NoteAiFloatingWindow noteAiFloatingWindow2 = BaseHybridNoteEditFragment.mNoteAiFloatingWindow;
                    Intrinsics.checkNotNull(noteAiFloatingWindow2);
                    noteAiFloatingWindow2.hideForLock();
                }
            }
        }

        protected final Dialog showLocalDownloadDialog(final Activity activity) {
            int i;
            int i2;
            int i3;
            Intrinsics.checkNotNullParameter(activity, "activity");
            Activity activity2 = activity;
            if (AIcrDownloadHelper.getModelStatus(activity2)) {
                i = R.string.aicr_update_title;
                i2 = R.string.aicr_update_content;
                i3 = R.string.aicr_update_button;
            } else {
                i = R.string.aicr_down_title;
                i2 = R.string.aicr_down_content;
                i3 = R.string.aicr_download_confirm;
            }
            return new AlertDialog.Builder(activity2).setTitle(i).setMessage(i2).setPositiveButton(i3, new DialogInterface.OnClickListener() { // from class: com.miui.notes.basefeature.noteedit.BaseHybridNoteEditFragment$Companion$$ExternalSyntheticLambda0
                @Override // android.content.DialogInterface.OnClickListener
                public final void onClick(DialogInterface dialogInterface, int i4) {
                    BaseHybridNoteEditFragment.Companion.showLocalDownloadDialog$lambda$0(activity, dialogInterface, i4);
                }
            }).setNegativeButton(R.string.dialog_button_cancel, new DialogInterface.OnClickListener() { // from class: com.miui.notes.basefeature.noteedit.BaseHybridNoteEditFragment$Companion$$ExternalSyntheticLambda1
                @Override // android.content.DialogInterface.OnClickListener
                public final void onClick(DialogInterface dialogInterface, int i4) {
                    dialogInterface.dismiss();
                }
            }).setCancelable(true).setOnCancelListener(new DialogInterface.OnCancelListener() { // from class: com.miui.notes.basefeature.noteedit.BaseHybridNoteEditFragment$Companion$$ExternalSyntheticLambda2
                @Override // android.content.DialogInterface.OnCancelListener
                public final void onCancel(DialogInterface dialogInterface) {
                    BaseHybridNoteEditFragment.Companion.showLocalDownloadDialog$lambda$2(dialogInterface);
                }
            }).show();
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static final void showLocalDownloadDialog$lambda$0(Activity activity, DialogInterface dialogInterface, int i) {
            Intent intent = new Intent();
            intent.setClassName(NotesNormalConstants.AICR_PACKAGE, "com.xiaomi.aicr.common.download.ModelActivity");
            activity.startActivity(intent);
            dialogInterface.dismiss();
        }

        /* JADX INFO: Access modifiers changed from: private */
        public final boolean isNeedDownloadOrUpdateLocalAi(Context context) {
            Logger.INSTANCE.d(BaseHybridNoteEditFragment.TAG, "isNeedDownloadOrUpdateLocalAi isAcceptedMode:" + PreferenceUtils.isAcceptedMode(context) + " ,ModelStatus: " + (!AIcrDownloadHelper.getModelStatus(context)) + " ,CotConfig: " + AIcrDownloadHelper.getCotConfig(context));
            if (PreferenceUtils.isAcceptedMode(context)) {
                return (AIcrDownloadHelper.getModelStatus(context) && AIcrDownloadHelper.getCotConfig(context)) ? false : true;
            }
            return false;
        }
    }
}
