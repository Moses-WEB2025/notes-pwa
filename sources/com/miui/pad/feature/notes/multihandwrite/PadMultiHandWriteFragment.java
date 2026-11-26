package com.miui.pad.feature.notes.multihandwrite;

import android.app.Activity;
import android.app.Application;
import android.content.BroadcastReceiver;
import android.content.ClipData;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.Rect;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcelable;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appsearch.ast.FunctionNode;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.view.ViewCompat;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.loader.app.LoaderManager;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import ch.qos.logback.core.CoreConstants;
import com.market.sdk.reflect.ReflectUtilsForMiui;
import com.miui.common.app.BaseNoteApp;
import com.miui.common.app.ShareViewModel;
import com.miui.common.base.FragmentActionListener;
import com.miui.common.base.NoteLoadDataEntity;
import com.miui.common.base.SafeBroadcastReceiver;
import com.miui.common.stat.PadHandWriteStat;
import com.miui.common.stat.ToDoStat;
import com.miui.common.tool.AudioUtils;
import com.miui.common.tool.CompatUtils;
import com.miui.common.tool.RomUtils;
import com.miui.common.tool.UIUtils;
import com.miui.common.tool.Utils;
import com.miui.handwritecommon.api.util.FilePathUtils;
import com.miui.handwriting.common.data.bean.CommonSearchWrapper;
import com.miui.handwriting.common.data.bean.MiuiRecognizeWrapper;
import com.miui.handwriting.common.data.bean.MiuiSpannedDataWrapper;
import com.miui.handwriting.common.data.bean.SearchItemWrapper;
import com.miui.handwriting.common.listener.CommonExportProcessListener;
import com.miui.handwriting.common.listener.CommonPageLoadListener;
import com.miui.handwriting.common.listener.CommonPageSaveListener;
import com.miui.handwriting.common.listener.CommonPageStateListener;
import com.miui.handwriting.common.listener.CommonPreviewClickListener;
import com.miui.handwriting.common.taskservice.CreationServiceManager;
import com.miui.handwriting.common.tools.BitmapUtil;
import com.miui.handwriting.common.tools.FilePathConstant;
import com.miui.handwriting.common.tools.MainThreadUtils;
import com.miui.handwriting.common.tools.RecognizeDataHelper;
import com.miui.handwriting.common.tools.constants.Constants;
import com.miui.handwriting.common.view.handview.HandWritingManager;
import com.miui.handwriting.common.view.page.HeaderView;
import com.miui.handwriting.common.view.thumbnail.ExportImageManager;
import com.miui.libhandwritingpage.listener.PDFExportListener;
import com.miui.libhandwritingpage.listener.PdfThumbnailLoadListener;
import com.miui.libhandwritingpreview.engine.PreMiCurveEngine;
import com.miui.libhandwritingpreview.interfaces.PreICreationEngine;
import com.miui.libhandwritingpreview.model.PageInkPreModel;
import com.miui.libhandwritingpreview.system.PreExportSystem;
import com.miui.libhandwritingpreview.system.PrePageSystem;
import com.miui.libhandwritingpreview.view.MiuiHandWritingPreview;
import com.miui.libhandwritingpreview.view.PagePreItemView;
import com.miui.notes.NoteApp;
import com.miui.notes.R;
import com.miui.notes.base.utils.Logger;
import com.miui.notes.detail.BaseNoteDetailFragment;
import com.miui.notes.detail.DetailViewModel;
import com.miui.notes.detail.PadDetailFragmentClassManager;
import com.miui.notes.excerpt.NotifyUtils;
import com.miui.notes.feature.folder.ChooserFolderDialogActivity;
import com.miui.notes.home.PrivateNotesListActivity;
import com.miui.notes.home.viewmodel.HomeViewModel;
import com.miui.notes.provider.Notes;
import com.miui.notes.store.FolderStore;
import com.miui.notes.store.NoteStore;
import com.miui.notes.tool.AttachmentUtils;
import com.miui.notes.tool.PreferenceUtils;
import com.miui.notes.ui.NotesListActivity;
import com.miui.pad.feature.notes.handwrite.HandWriteDBHelper;
import com.miui.pad.feature.notes.handwrite.HandWriteHeaderController;
import com.miui.pad.feature.notes.handwrite.HandWriteWorkingNote;
import com.miui.pad.feature.notes.handwrite.TouchStylusBoostMode;
import com.miui.pad.feature.notes.share.ExportShareActivity;
import defpackage.R2;
import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import kotlin.Metadata;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.CoroutineContext;
import kotlin.coroutines.intrinsics.IntrinsicsKt;
import kotlin.coroutines.jvm.internal.Boxing;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.TypeIntrinsics;
import kotlin.ranges.RangesKt;
import kotlinx.coroutines.BuildersKt;
import kotlinx.coroutines.BuildersKt__Builders_commonKt;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.DelayKt;
import kotlinx.coroutines.Dispatchers;
import kotlinx.coroutines.Job;
import kotlinx.coroutines.SupervisorKt;
import miui.cloud.CloudPushConstants;
import miuix.android.content.MiuiIntent;
import miuix.appcompat.app.ActionBar;
import miuix.appcompat.app.AlertDialog;
import miuix.appcompat.app.ProgressDialog;
import miuix.internal.util.ViewUtils;
import miuix.navigator.Navigator;
import miuix.responsive.map.ScreenSpec;
import net.lingala.zip4j.util.InternalZipConstants;
import xc.engine.utils.FileUtil;
import xc.engine.utils.XLog;

/* JADX INFO: compiled from: PadMultiHandWriteFragment.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000\u009a\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010!\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\t\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\f\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0010\u0016\n\u0002\b\u0013\n\u0002\u0010 \n\u0002\b\n\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\b\u001e\n\u0002\u0018\u0002\n\u0002\b\u0005*\u0002\u0099\u0001\u0018\u0000 »\u00012\u00020\u00012\u00020\u00022\u00020\u0003:\u0002»\u0001B\u0007¢\u0006\u0004\b\u0004\u0010\u0005J\u001a\u0010=\u001a\u00020>2\b\u0010?\u001a\u0004\u0018\u00010\u001e2\u0006\u0010@\u001a\u00020\u000bH\u0014J\u0012\u0010A\u001a\u00020>2\b\u0010B\u001a\u0004\u0018\u00010CH\u0016J\b\u0010D\u001a\u00020>H\u0016J\b\u0010E\u001a\u00020>H\u0016J\b\u0010F\u001a\u00020>H\u0016J\u0010\u0010G\u001a\u00020>2\u0006\u0010H\u001a\u00020IH\u0016J&\u0010J\u001a\u00020\u000e2\b\u0010K\u001a\u0004\u0018\u00010L2\b\u0010M\u001a\u0004\u0018\u00010N2\b\u0010B\u001a\u0004\u0018\u00010CH\u0016J\u001a\u0010O\u001a\u00020>2\u0006\u0010P\u001a\u00020\u000e2\b\u0010B\u001a\u0004\u0018\u00010CH\u0017J\u0012\u0010Q\u001a\u00020>2\b\u0010R\u001a\u0004\u0018\u00010CH\u0016J\u001a\u0010S\u001a\u00020>2\u0006\u0010P\u001a\u00020\u000e2\b\u0010B\u001a\u0004\u0018\u00010CH\u0016J\b\u0010T\u001a\u00020>H\u0016J\b\u0010U\u001a\u00020>H\u0016J\b\u0010V\u001a\u00020>H\u0014J\u0010\u0010W\u001a\u00020>2\u0006\u0010X\u001a\u00020,H\u0016J\u0012\u0010Y\u001a\u00020\u000b2\b\u0010Z\u001a\u0004\u0018\u00010[H\u0016J\u0010\u0010\\\u001a\u00020>2\u0006\u0010Z\u001a\u00020[H\u0016J\u0010\u0010]\u001a\u00020\u000b2\u0006\u0010^\u001a\u00020_H\u0016J\"\u0010`\u001a\u00020>2\b\u0010a\u001a\u0004\u0018\u00010b2\u0006\u0010c\u001a\u00020d2\u0006\u0010e\u001a\u00020\u000bH\u0016J\u0010\u0010f\u001a\u00020>2\u0006\u0010g\u001a\u00020\u000bH\u0016J \u0010h\u001a\u00020>2\u0006\u0010i\u001a\u0002082\u0006\u0010j\u001a\u00020k2\u0006\u0010l\u001a\u00020\u0007H\u0014J\b\u0010m\u001a\u00020>H\u0016J\u0010\u0010q\u001a\u00020>2\u0006\u0010r\u001a\u00020\u000bH\u0016J\u0010\u0010s\u001a\u00020>2\u0006\u0010a\u001a\u00020bH\u0016J\b\u0010t\u001a\u00020>H\u0002J\u0006\u0010u\u001a\u00020>J\b\u0010v\u001a\u00020>H\u0002J\u0010\u0010w\u001a\u00020>2\u0006\u0010n\u001a\u000208H\u0002J\u000e\u0010x\u001a\u00020>2\u0006\u0010Z\u001a\u00020[J\u0006\u0010y\u001a\u00020\u000bJ\u0006\u0010z\u001a\u00020>J\u0010\u0010{\u001a\u00020>2\u0006\u0010|\u001a\u00020CH\u0002J\"\u0010}\u001a\u00020>2\b\u0010)\u001a\u0004\u0018\u00010\u00072\u0010\u0010~\u001a\f\u0012\u0006\u0012\u0004\u0018\u00010\u0007\u0018\u00010\u007fJ\u0012\u0010\u0080\u0001\u001a\u00020>2\u0007\u0010\u0081\u0001\u001a\u00020\u000bH\u0014J\t\u0010\u0082\u0001\u001a\u00020>H\u0002J\u0012\u0010\u0083\u0001\u001a\u00020\u00072\u0007\u0010\u0084\u0001\u001a\u00020\u000bH\u0002J\t\u0010\u0085\u0001\u001a\u00020>H\u0002J\u0011\u0010\u0086\u0001\u001a\u00020>2\u0006\u0010P\u001a\u00020\u000eH\u0002J\t\u0010\u0087\u0001\u001a\u00020>H\u0002J\t\u0010\u0088\u0001\u001a\u00020>H\u0002J\u001f\u0010\u0089\u0001\u001a\u0005\u0018\u00010\u008a\u00012\n\u0010\u008b\u0001\u001a\u0005\u0018\u00010\u008c\u00012\u0007\u0010\u008d\u0001\u001a\u00020\u0007J\t\u0010\u008e\u0001\u001a\u00020>H\u0002J\t\u0010\u008f\u0001\u001a\u00020>H\u0002J\t\u0010\u0090\u0001\u001a\u00020>H\u0002J\t\u0010\u0091\u0001\u001a\u00020>H\u0002J\t\u0010\u0092\u0001\u001a\u00020>H\u0002J\t\u0010\u0093\u0001\u001a\u00020>H\u0002J\t\u0010\u009b\u0001\u001a\u00020>H\u0002J\t\u0010\u009c\u0001\u001a\u00020>H\u0002J\t\u0010\u009d\u0001\u001a\u00020>H\u0002J\u0011\u0010\u009e\u0001\u001a\u00020>2\u0006\u0010c\u001a\u00020dH\u0002J\t\u0010\u009f\u0001\u001a\u00020>H\u0002J\t\u0010 \u0001\u001a\u00020\u000bH\u0002J\u0012\u0010¡\u0001\u001a\u00020>2\u0007\u0010¢\u0001\u001a\u00020\u0007H\u0002J\t\u0010£\u0001\u001a\u00020>H\u0002J\t\u0010¤\u0001\u001a\u00020>H\u0002J\u0007\u0010¥\u0001\u001a\u00020>J\t\u0010¦\u0001\u001a\u00020>H\u0002J\t\u0010§\u0001\u001a\u00020>H\u0002J\u0012\u0010¨\u0001\u001a\u00020>2\u0007\u0010©\u0001\u001a\u00020,H\u0016J\u0018\u0010ª\u0001\u001a\u00020>2\r\u0010«\u0001\u001a\b\u0012\u0004\u0012\u00020,0\u007fH\u0016J\u0012\u0010¬\u0001\u001a\u00020>2\u0007\u0010\u00ad\u0001\u001a\u00020,H\u0016J\u0012\u0010®\u0001\u001a\u00020>2\u0007\u0010¯\u0001\u001a\u00020,H\u0016J\u0018\u0010°\u0001\u001a\u00020>2\r\u0010«\u0001\u001a\b\u0012\u0004\u0012\u00020,0\u007fH\u0016J\u001b\u0010±\u0001\u001a\u00020>2\u0007\u0010©\u0001\u001a\u00020,2\u0007\u0010²\u0001\u001a\u00020\u0007H\u0016J$\u0010³\u0001\u001a\u00020>2\u0007\u0010\u00ad\u0001\u001a\u00020,2\u0007\u0010©\u0001\u001a\u00020,2\u0007\u0010²\u0001\u001a\u00020\u0007H\u0016J%\u0010´\u0001\u001a\u00020>2\u0007\u0010µ\u0001\u001a\u0002082\b\u0010¶\u0001\u001a\u00030·\u00012\u0007\u0010¸\u0001\u001a\u00020\u000bH\u0016J\u001b\u0010¹\u0001\u001a\u00020>2\b\u0010\u0013\u001a\u0004\u0018\u00010\u00142\b\u0010\u001f\u001a\u0004\u0018\u00010 J\u0019\u0010º\u0001\u001a\u00020>2\u0006\u0010\u0013\u001a\u00020\u00142\b\u0010\u001f\u001a\u0004\u0018\u00010 R\u0014\u0010\u0006\u001a\u00020\u0007X\u0096D¢\u0006\b\n\u0000\u001a\u0004\b\b\u0010\tR\u000e\u0010\n\u001a\u00020\u000bX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\f\u001a\u00020\u000bX\u0082\u000e¢\u0006\u0002\n\u0000R\u0010\u0010\r\u001a\u0004\u0018\u00010\u000eX\u0082\u000e¢\u0006\u0002\n\u0000R\u0010\u0010\u000f\u001a\u0004\u0018\u00010\u0010X\u0082\u000e¢\u0006\u0002\n\u0000R\u0010\u0010\u0011\u001a\u0004\u0018\u00010\u0012X\u0082\u000e¢\u0006\u0002\n\u0000R\u0010\u0010\u0013\u001a\u0004\u0018\u00010\u0014X\u0082\u000e¢\u0006\u0002\n\u0000R\u0010\u0010\u0015\u001a\u0004\u0018\u00010\u0016X\u0082\u000e¢\u0006\u0002\n\u0000R\u0010\u0010\u0017\u001a\u0004\u0018\u00010\u000eX\u0082\u000e¢\u0006\u0002\n\u0000R\u0010\u0010\u0018\u001a\u0004\u0018\u00010\u0019X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u001a\u001a\u00020\u000bX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u001b\u001a\u00020\u000bX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u001c\u001a\u00020\u000bX\u0082\u000e¢\u0006\u0002\n\u0000R\u0010\u0010\u001d\u001a\u0004\u0018\u00010\u001eX\u0082\u000e¢\u0006\u0002\n\u0000R\u0010\u0010\u001f\u001a\u0004\u0018\u00010 X\u0082\u000e¢\u0006\u0002\n\u0000R\u0010\u0010!\u001a\u0004\u0018\u00010\"X\u0082\u000e¢\u0006\u0002\n\u0000R\u0010\u0010#\u001a\u0004\u0018\u00010$X\u0082\u000e¢\u0006\u0002\n\u0000R\u0010\u0010%\u001a\u0004\u0018\u00010&X\u0082\u000e¢\u0006\u0002\n\u0000R\u0018\u0010'\u001a\f\u0012\u0006\u0012\u0004\u0018\u00010\u0007\u0018\u00010(X\u0082\u000e¢\u0006\u0002\n\u0000R\u0010\u0010)\u001a\u0004\u0018\u00010\u0007X\u0082\u000e¢\u0006\u0002\n\u0000R\u0016\u0010*\u001a\n\u0012\u0004\u0012\u00020,\u0018\u00010+X\u0082\u000e¢\u0006\u0002\n\u0000R\u0010\u0010-\u001a\u0004\u0018\u00010.X\u0082\u000e¢\u0006\u0002\n\u0000R\u0010\u0010/\u001a\u0004\u0018\u00010\u0007X\u0082\u000e¢\u0006\u0002\n\u0000R\u0010\u00100\u001a\u0004\u0018\u00010\u0019X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u00101\u001a\u00020\u000bX\u0082\u000e¢\u0006\u0002\n\u0000R\u0010\u00102\u001a\u0004\u0018\u000103X\u0082\u000e¢\u0006\u0002\n\u0000R\u0010\u00104\u001a\u0004\u0018\u000105X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u00106\u001a\u00020\u000bX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u00107\u001a\u000208X\u0082\u000e¢\u0006\u0002\n\u0000R\u0014\u00109\u001a\u00020:8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b;\u0010<R\u0014\u0010n\u001a\u0002088TX\u0094\u0004¢\u0006\u0006\u001a\u0004\bo\u0010pR\u0012\u0010\u0094\u0001\u001a\u0005\u0018\u00010\u0095\u0001X\u0082\u000e¢\u0006\u0002\n\u0000R\u0017\u0010\u0096\u0001\u001a\u00020\u000b8VX\u0096\u0004¢\u0006\b\u001a\u0006\b\u0096\u0001\u0010\u0097\u0001R\u0013\u0010\u0098\u0001\u001a\u00030\u0099\u0001X\u0082\u0004¢\u0006\u0005\n\u0003\u0010\u009a\u0001¨\u0006¼\u0001"}, d2 = {"Lcom/miui/pad/feature/notes/multihandwrite/PadMultiHandWriteFragment;", "Lcom/miui/notes/detail/BaseNoteDetailFragment;", "Lkotlinx/coroutines/CoroutineScope;", "Lcom/miui/handwriting/common/view/thumbnail/ExportImageManager$ExportCallback;", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "()V", "TAG", "", "getTAG", "()Ljava/lang/String;", "isDownloadReceiverRegistered", "", "isOperationReceiverRegistered", "loadingContainer", "Landroid/view/View;", "mRootView", "Landroidx/constraintlayout/widget/ConstraintLayout;", "mRootViewInitPadding", "Lmiuix/internal/util/ViewUtils$RelativePadding;", "multiHandWriteView", "Lcom/miui/libhandwritingpreview/view/MiuiHandWritingPreview;", "mHeaderController", "Lcom/miui/pad/feature/notes/handwrite/HandWriteHeaderController;", "customActionBarView", "loadingDialog", "Lmiuix/appcompat/app/ProgressDialog;", "mIsNaviShow", "firstSetNavi", "isHomeBack", "currentEntity", "Lcom/miui/common/base/NoteLoadDataEntity;", "handWriteEntity", "Lcom/miui/pad/feature/notes/multihandwrite/MultiHandWriteEntity;", "headerOperationCallBack", "Lcom/miui/pad/feature/notes/handwrite/HandWriteHeaderController$HeaderOperationCallback;", "mStateListener", "Lmiuix/navigator/Navigator$NavigatorStateListener;", "pageLoadListener", "Lcom/miui/handwriting/common/listener/CommonPageLoadListener;", "exportProcessListener", "Lcom/miui/handwriting/common/listener/CommonExportProcessListener;", "queryText", "mLastExportList", "", "", "operationReceiver", "Landroid/content/BroadcastReceiver;", "dirPath", "exportPreviewDialog", "isStartEdit", "mShareViewModel", "Lcom/miui/common/app/ShareViewModel;", "workingNote", "Lcom/miui/pad/feature/notes/handwrite/HandWriteWorkingNote;", "mSearchMode", "mLastExportTime", "", "coroutineContext", "Lkotlin/coroutines/CoroutineContext;", "getCoroutineContext", "()Lkotlin/coroutines/CoroutineContext;", "refreshNote", "", "noteEntity", "isFromPc", "onCreate", "savedInstanceState", "Landroid/os/Bundle;", "onStart", "onResume", "onStop", "onContentInsetChanged", "contentInset", "Landroid/graphics/Rect;", "onInflateView", "inflater", "Landroid/view/LayoutInflater;", "container", "Landroid/view/ViewGroup;", "onViewInflated", "view", "onUpdateArguments", "newArguments", "onViewCreated", "onDestroyView", "onDestroy", "initBaseResultLauncher", "onContentVisibilityChanged", "visibility", "onCreateOptionsMenu", "menu", "Landroid/view/Menu;", "onPrepareOptionsMenu", "onOptionsItemSelected", CloudPushConstants.XML_ITEM, "Landroid/view/MenuItem;", "onResponsiveLayout", "configuration", "Landroid/content/res/Configuration;", "screenSpec", "Lmiuix/responsive/map/ScreenSpec;", "responsiveLayoutStateChanged", "onMultiWindowModeChanged", "isInMultiWindowMode", "moveCheckedItems", "folderId", "checkedIds", "", "folderName", "startQueryFolder", NotifyUtils.KEY_NOTE_ID, "getNoteId", "()J", "setInSearchMode", "inSearchMode", "onConfigurationChanged", "tryHideNote", "requestDeleteNote", "deleteAndLoad", "onNoteDeleted", "updateMenu", "isNoteHidden", "setTitleHightLightColor", "loadEntity", "bundle", FunctionNode.FUNCTION_NAME_SEARCH, "recognizedTextFile", "", "setNaviState", "show", "setNaviStateWithJudge", "getSavingTitle", "empty", "refreshLayout", "initView", "inHeaderOperationCallBack", "getShareImage", "createTempUriWithFilepath", "Landroid/net/Uri;", CoreConstants.CONTEXT_SCOPE_VALUE, "Landroid/content/Context;", "filepath", "showExportPreviewDialog", "dismissExportPreviewDialog", "initNaviListener", "initPageLoadListener", "initExportListener", "updateUI", "pageStateListener", "Lcom/miui/handwriting/common/listener/CommonPageStateListener;", "isDataEmpty", "()Z", "downloadFileReceiver", "com/miui/pad/feature/notes/multihandwrite/PadMultiHandWriteFragment$downloadFileReceiver$1", "Lcom/miui/pad/feature/notes/multihandwrite/PadMultiHandWriteFragment$downloadFileReceiver$1;", "registerDownReceiver", "unregisterDownloadReceiver", "startActivity", "updateHomeButtonState", "deleteCurrentEmptyNote", "checkIsMore", "showLoadingDialog", "message", "dismissLoadingDialog", "finishActivity", "registerCreationTaskReceiver", "unregisterOperationReceiver", "releasePage", "loadPreviewFinish", "exportType", "requirePreviewList", "list", "loadPreviewError", "errorCode", "exportProgress", "progress", "requireNextList", "exportFinish", "path", "exportError", "exportThumbnailFinish", "id", "values", "Landroid/content/ContentValues;", "isAutoSave", "loadPageItem", "savePageItem", "Companion", "app_PhoneGlobalRelease"}, k = 1, mv = {2, 2, 0}, xi = 48)
public final class PadMultiHandWriteFragment extends BaseNoteDetailFragment implements CoroutineScope, ExportImageManager.ExportCallback {
    public static final int EXPORT_SIZE = 1440;
    public static final int EXPORT_TIME_GAP = 500;
    public static final String EXTRA_TAP_SHARE_MULTIPLE_INFO = "xiaomi.tap.share.MULTIPLE_INFO";
    public static final String EXTRA_TAP_SHARE_SUPPORT = "xiaomi.tap.share.SUPPORT";
    public static final String PRIVATE_NOTES_ACTIVITY = "com.miui.notes.home.PrivateNotesListActivity";
    public static final float SCALE_MAX = 3.0f;
    public static final float SCALE_MIN = 0.55f;
    private NoteLoadDataEntity currentEntity;
    private View customActionBarView;
    private String dirPath;
    private ProgressDialog exportPreviewDialog;
    private CommonExportProcessListener<String> exportProcessListener;
    private MultiHandWriteEntity handWriteEntity;
    private HandWriteHeaderController.HeaderOperationCallback headerOperationCallBack;
    private boolean isDownloadReceiverRegistered;
    private boolean isHomeBack;
    private boolean isOperationReceiverRegistered;
    private boolean isStartEdit;
    private View loadingContainer;
    private ProgressDialog loadingDialog;
    private HandWriteHeaderController mHeaderController;
    private List<Integer> mLastExportList;
    private long mLastExportTime;
    private ConstraintLayout mRootView;
    private ViewUtils.RelativePadding mRootViewInitPadding;
    private boolean mSearchMode;
    private ShareViewModel mShareViewModel;
    private Navigator.NavigatorStateListener mStateListener;
    private MiuiHandWritingPreview multiHandWriteView;
    private BroadcastReceiver operationReceiver;
    private CommonPageLoadListener pageLoadListener;
    private String queryText;
    private HandWriteWorkingNote workingNote;
    private final String TAG = "PadMultiHandWriteFragment";
    private boolean mIsNaviShow = true;
    private boolean firstSetNavi = true;
    private CommonPageStateListener pageStateListener = new CommonPageStateListener() { // from class: com.miui.pad.feature.notes.multihandwrite.PadMultiHandWriteFragment$pageStateListener$1
        @Override // com.miui.handwriting.common.listener.CommonPageStateListener
        public void dataLoadFinish() {
        }

        @Override // com.miui.handwriting.common.listener.CommonPageStateListener
        public void onDoubleUndo() {
        }

        @Override // com.miui.handwriting.common.listener.CommonPageStateListener
        public void onPageLoadingFinish() {
        }

        @Override // com.miui.handwriting.common.listener.CommonPageStateListener
        public void onStepChanged(int stepType, int position, boolean canUndo, boolean canRedo, boolean dismissLoadingDialog) {
            Log.d(this.this$0.getTAG(), "onStepChanged: ");
        }

        @Override // com.miui.handwriting.common.listener.CommonPageStateListener
        public void onContentChanged(int pageIndex) {
            Log.d(this.this$0.getTAG(), "onContentChanged: ");
        }

        @Override // com.miui.handwriting.common.listener.CommonPageStateListener
        public void onPageScale(float scale) {
            MiuiHandWritingPreview miuiHandWritingPreview = this.this$0.multiHandWriteView;
            if (miuiHandWritingPreview != null) {
                miuiHandWritingPreview.updateBackgroundMargin(scale);
            }
        }
    };
    private final PadMultiHandWriteFragment$downloadFileReceiver$1 downloadFileReceiver = new SafeBroadcastReceiver<PadMultiHandWriteFragment>(this) { // from class: com.miui.pad.feature.notes.multihandwrite.PadMultiHandWriteFragment$downloadFileReceiver$1
        {
            super(this);
        }

        @Override // com.miui.common.base.SafeBroadcastReceiver, android.content.BroadcastReceiver
        public void onReceive(Context context, Intent intent) {
            MiuiHandWritingPreview miuiHandWritingPreview;
            super.onReceive(context, intent);
            if (hasReference()) {
                PadMultiHandWriteFragment wkObject = getWkObject();
                if ((wkObject != null ? wkObject.multiHandWriteView : null) == null || wkObject.handWriteEntity == null) {
                    return;
                }
                MultiHandWriteEntity multiHandWriteEntity = wkObject.handWriteEntity;
                if (!TextUtils.equals(intent != null ? intent.getStringExtra("entName") : null, multiHandWriteEntity != null ? multiHandWriteEntity.entName : null) || (miuiHandWritingPreview = wkObject.multiHandWriteView) == null) {
                    return;
                }
                MultiHandWriteEntity multiHandWriteEntity2 = wkObject.handWriteEntity;
                miuiHandWritingPreview.load((multiHandWriteEntity2 != null ? multiHandWriteEntity2.observeEntDir() : null) + "/multi_page_ent.ent", new CommonPageLoadListener() { // from class: com.miui.pad.feature.notes.multihandwrite.PadMultiHandWriteFragment$downloadFileReceiver$1$onReceive$1
                    @Override // com.miui.handwriting.common.listener.CommonPageLoadListener
                    public void onLoadCompleted(boolean z, boolean isPdf, String str) {
                        Intrinsics.checkNotNullParameter(str, "str");
                    }

                    @Override // com.miui.handwriting.common.listener.CommonPageLoadListener
                    public void onPageCount(int i) {
                    }

                    @Override // com.miui.handwriting.common.listener.CommonPageLoadListener
                    public void onPdfError(int type) {
                    }
                });
            }
        }
    };

    @Override // com.miui.handwriting.common.view.thumbnail.ExportImageManager.ExportCallback
    public void exportThumbnailFinish(long id, ContentValues values, boolean isAutoSave) {
        Intrinsics.checkNotNullParameter(values, "values");
    }

    @Override // com.miui.notes.detail.BaseNoteDetailFragment
    protected void refreshNote(NoteLoadDataEntity noteEntity, boolean isFromPc) {
    }

    @Override // com.miui.notes.detail.BaseNoteDetailFragment
    public String getTAG() {
        return this.TAG;
    }

    @Override // kotlinx.coroutines.CoroutineScope
    public CoroutineContext getCoroutineContext() {
        return SupervisorKt.SupervisorJob$default((Job) null, 1, (Object) null).plus(Dispatchers.getMain());
    }

    @Override // com.miui.notes.detail.BaseNoteDetailFragment, miuix.appcompat.app.Fragment, androidx.fragment.app.Fragment
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(getTAG(), "onCreate: ");
        Application application = requireActivity().getApplication();
        Intrinsics.checkNotNull(application, "null cannot be cast to non-null type com.miui.notes.NoteApp");
        this.mShareViewModel = ((NoteApp) application).getGlobalSharedViewModel();
        HandWritingManager.INSTANCE.setViewType(1);
        setThemeRes(R.style.NavigatorSecondaryContentTheme);
        inHeaderOperationCallBack();
        initPageLoadListener();
        initNaviListener();
        initExportListener();
        registerDownReceiver();
        registerCreationTaskReceiver();
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
    }

    @Override // com.miui.notes.detail.BaseNoteDetailFragment, miuix.appcompat.app.Fragment, androidx.fragment.app.Fragment
    public void onStop() {
        super.onStop();
        Log.d(getTAG(), "onStop: ");
        TouchStylusBoostMode.INSTANCE.getSInstance().setMode(0);
    }

    @Override // miuix.appcompat.app.Fragment, miuix.appcompat.app.IContentInsetState
    public void onContentInsetChanged(Rect contentInset) {
        Intrinsics.checkNotNullParameter(contentInset, "contentInset");
        super.onContentInsetChanged(contentInset);
        Log.d(getTAG(), "onContentInsetChanged: ");
        ViewUtils.RelativePadding relativePadding = this.mRootViewInitPadding;
        if (relativePadding != null) {
            ViewUtils.RelativePadding relativePadding2 = new ViewUtils.RelativePadding(relativePadding);
            boolean zIsLayoutRtl = ViewUtils.isLayoutRtl(this.rootView);
            relativePadding2.start += zIsLayoutRtl ? contentInset.right : contentInset.left;
            if (!RomUtils.isPadDevice() || (!UIUtils.isFreeFormScreen(getResources().getConfiguration()) && !UIUtils.isInMultiWindowMode(getActivity()))) {
                relativePadding2.top += (this.mIsNaviShow || RomUtils.isFoldDevice()) ? contentInset.top : 0;
            } else if (this.firstSetNavi || !this.mIsNaviShow) {
                relativePadding2.top = relativePadding2.top;
            } else {
                relativePadding2.top += contentInset.top;
            }
            relativePadding2.end += zIsLayoutRtl ? contentInset.left : contentInset.right;
            relativePadding2.applyToView(this.rootView);
        }
    }

    @Override // com.miui.notes.detail.BaseNoteDetailFragment, miuix.appcompat.app.Fragment, miuix.appcompat.app.IFragment
    public View onInflateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Log.d(getTAG(), "onInflateView: ");
        View viewInflate = inflater != null ? inflater.inflate(R.layout.fragment_pad_multi_handwrite, container, false) : null;
        this.loadingContainer = viewInflate != null ? viewInflate.findViewById(R.id.loading_container) : null;
        Intrinsics.checkNotNull(viewInflate, "null cannot be cast to non-null type androidx.constraintlayout.widget.ConstraintLayout");
        this.mRootView = (ConstraintLayout) viewInflate;
        initView(viewInflate);
        return viewInflate;
    }

    @Override // com.miui.notes.detail.BaseNoteDetailFragment, miuix.appcompat.app.Fragment, miuix.appcompat.app.IFragment
    public void onViewInflated(View view, Bundle savedInstanceState) {
        Intrinsics.checkNotNullParameter(view, "view");
        super.onViewInflated(view, savedInstanceState);
        Log.d(getTAG(), "onViewInflated: ");
        refreshLayout();
        ActionBar actionBar = getActionBar();
        if (actionBar != null) {
            actionBar.setBackgroundDrawable(requireActivity().getResources().getDrawable(R.drawable.v8_placeholder, null));
        }
        setImmersionMenuEnabled(true);
        updateHomeButtonState(this.mScreenSpec);
        HomeViewModel homeViewModel = this.homeViewModel;
        if (homeViewModel != null) {
            homeViewModel.setKeyboardChange(false);
        }
        Navigator.get(this).addNavigatorStateListener(this.mStateListener);
        onUpdateArguments(getArguments());
    }

    @Override // miuix.appcompat.app.Fragment
    public void onUpdateArguments(Bundle newArguments) {
        NoteLoadDataEntity noteLoadDataEntity;
        super.onUpdateArguments(newArguments);
        if (newArguments == null || (noteLoadDataEntity = (NoteLoadDataEntity) newArguments.getSerializable("entity")) == null) {
            return;
        }
        this.currentEntity = noteLoadDataEntity;
        Bundle bundle = noteLoadDataEntity.toBundle();
        initPageLoadListener();
        Intrinsics.checkNotNull(bundle);
        loadEntity(bundle);
    }

    @Override // com.miui.notes.detail.BaseNoteDetailFragment, miuix.appcompat.app.Fragment, androidx.fragment.app.Fragment
    public void onViewCreated(View view, Bundle savedInstanceState) {
        Intrinsics.checkNotNullParameter(view, "view");
        super.onViewCreated(view, savedInstanceState);
    }

    @Override // com.miui.notes.detail.BaseNoteDetailFragment, miuix.appcompat.app.Fragment, androidx.fragment.app.Fragment
    public void onDestroyView() {
        super.onDestroyView();
        PadDetailFragmentClassManager.isLoaded(false);
        this.handWriteEntity = null;
        unregisterDownloadReceiver();
        unregisterOperationReceiver();
        this.pageStateListener = null;
        this.exportProcessListener = null;
        this.pageLoadListener = null;
        this.mSearchMode = false;
        MiuiHandWritingPreview miuiHandWritingPreview = this.multiHandWriteView;
        if (miuiHandWritingPreview != null) {
            miuiHandWritingPreview.setPreviewOnClick(null);
            miuiHandWritingPreview.setPageStateListener(null);
            miuiHandWritingPreview.release();
        }
        dismissLoadingDialog();
        dismissExportPreviewDialog();
        Navigator.get(this).removeNavigatorStateListener(this.mStateListener);
        ExportImageManager.INSTANCE.getInstance().unRegisterCallback(this);
    }

    @Override // com.miui.notes.detail.BaseNoteDetailFragment, miuix.appcompat.app.Fragment, androidx.fragment.app.Fragment
    public void onDestroy() {
        XLog.v(getTAG(), "onDestroy", new Object[0]);
        super.onDestroy();
        CreationServiceManager.INSTANCE.clearLastMessage();
        unregisterDownloadReceiver();
        unregisterOperationReceiver();
    }

    @Override // com.miui.notes.detail.BaseNoteDetailFragment
    protected void initBaseResultLauncher() {
        this.toChooseFolderResult = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() { // from class: com.miui.pad.feature.notes.multihandwrite.PadMultiHandWriteFragment.initBaseResultLauncher.1
            @Override // androidx.activity.result.ActivityResultCallback
            public void onActivityResult(ActivityResult result) {
                Intrinsics.checkNotNullParameter(result, "result");
                HomeViewModel homeViewModel = PadMultiHandWriteFragment.this.homeViewModel;
                Intrinsics.checkNotNull(homeViewModel);
                homeViewModel.toDetailResultCallback();
                if (result.getResultCode() == -1) {
                    Intent data = result.getData();
                    if (!PadMultiHandWriteFragment.this.isAdded() || data == null) {
                        return;
                    }
                    long longExtra = data.getLongExtra(ChooserFolderDialogActivity.CHOOSER_FOLDER_SELECT_FOLDER_ID, 0L);
                    if (PadMultiHandWriteFragment.this.handWriteEntity != null) {
                        MultiHandWriteEntity multiHandWriteEntity = PadMultiHandWriteFragment.this.handWriteEntity;
                        Intrinsics.checkNotNull(multiHandWriteEntity);
                        multiHandWriteEntity.folderId = longExtra;
                    }
                }
            }
        });
    }

    @Override // miuix.navigator.NavigatorFragmentListener
    public void onContentVisibilityChanged(int visibility) {
        super.onContentVisibilityChanged(visibility);
        Log.d(getTAG(), "onContentVisibilityChanged: " + visibility);
        if (visibility == 0) {
            setNaviState(false);
        } else if (visibility == 1) {
            BuildersKt__Builders_commonKt.launch$default(this, null, null, new C03511(null), 3, null);
        } else {
            if (visibility != 2) {
                return;
            }
            setNaviStateWithJudge();
        }
    }

    /* JADX INFO: renamed from: com.miui.pad.feature.notes.multihandwrite.PadMultiHandWriteFragment$onContentVisibilityChanged$1, reason: invalid class name and case insensitive filesystem */
    /* JADX INFO: compiled from: PadMultiHandWriteFragment.kt */
    @Metadata(d1 = {"\u0000\n\n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\u0010\u0000\u001a\u00020\u0001*\u00020\u0002H\n"}, d2 = {"<anonymous>", "", "Lkotlinx/coroutines/CoroutineScope;"}, k = 3, mv = {2, 2, 0}, xi = 48)
    @DebugMetadata(c = "com.miui.pad.feature.notes.multihandwrite.PadMultiHandWriteFragment$onContentVisibilityChanged$1", f = "PadMultiHandWriteFragment.kt", i = {}, l = {R2.attr.actionModePopupWindowStyle}, m = "invokeSuspend", n = {}, s = {})
    static final class C03511 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
        int label;

        C03511(Continuation<? super C03511> continuation) {
            super(2, continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
            return PadMultiHandWriteFragment.this.new C03511(continuation);
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(CoroutineScope coroutineScope, Continuation<? super Unit> continuation) {
            return ((C03511) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            Object coroutine_suspended = IntrinsicsKt.getCOROUTINE_SUSPENDED();
            int i = this.label;
            if (i == 0) {
                ResultKt.throwOnFailure(obj);
                this.label = 1;
                if (DelayKt.delay(100L, this) == coroutine_suspended) {
                    return coroutine_suspended;
                }
            } else {
                if (i != 1) {
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                }
                ResultKt.throwOnFailure(obj);
            }
            PadMultiHandWriteFragment.this.setNaviStateWithJudge();
            return Unit.INSTANCE;
        }
    }

    @Override // miuix.appcompat.app.Fragment, miuix.appcompat.app.IFragment
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.normal_note_edit_options, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override // androidx.fragment.app.Fragment
    public void onPrepareOptionsMenu(Menu menu) {
        Intrinsics.checkNotNullParameter(menu, "menu");
        updateMenu(menu);
        super.onPrepareOptionsMenu(menu);
    }

    @Override // miuix.appcompat.app.Fragment, androidx.fragment.app.Fragment
    public boolean onOptionsItemSelected(MenuItem item) {
        Intrinsics.checkNotNullParameter(item, "item");
        switch (item.getItemId()) {
            case R.id.delete /* 2131362396 */:
                requestDeleteNote();
                PadHandWriteStat.reportMoreClickEvent(ToDoStat.DELETE);
                break;
            case R.id.hide /* 2131362702 */:
                tryHideNote();
                PadHandWriteStat.reportMoreClickEvent(MiuiIntent.COMMAND_ICON_PANEL_HIDE);
                break;
            case R.id.move_to /* 2131362990 */:
                if (this.handWriteEntity != null) {
                    startQueryFolder();
                }
                break;
            case R.id.unhide /* 2131363736 */:
                if (RomUtils.isFoldDevice() && Navigator.get(this).getNavigationMode() == Navigator.Mode.C) {
                    onBackPressed();
                }
                HomeViewModel homeViewModel = this.homeViewModel;
                if (homeViewModel != null) {
                    MultiHandWriteEntity multiHandWriteEntity = this.handWriteEntity;
                    Intrinsics.checkNotNull(multiHandWriteEntity);
                    homeViewModel.unHideNote(multiHandWriteEntity.id);
                }
                PadHandWriteStat.reportMoreClickEvent("unhide");
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override // miuix.appcompat.app.Fragment, miuix.responsive.interfaces.IResponsive
    public void onResponsiveLayout(Configuration configuration, ScreenSpec screenSpec, boolean responsiveLayoutStateChanged) {
        Intrinsics.checkNotNullParameter(screenSpec, "screenSpec");
        super.onResponsiveLayout(configuration, screenSpec, responsiveLayoutStateChanged);
        Log.d(getTAG(), "onResponsiveLayout: ");
        this.mScreenSpec = screenSpec;
        updateHomeButtonState(this.mScreenSpec);
    }

    @Override // androidx.fragment.app.Fragment
    public void onMultiWindowModeChanged(boolean isInMultiWindowMode) {
        super.onMultiWindowModeChanged(isInMultiWindowMode);
        Log.d(getTAG(), "onMultiWindowModeChanged: ");
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.miui.notes.detail.BaseNoteDetailFragment
    public void moveCheckedItems(long folderId, long[] checkedIds, String folderName) {
        Intrinsics.checkNotNullParameter(checkedIds, "checkedIds");
        Intrinsics.checkNotNullParameter(folderName, "folderName");
        if (this.handWriteEntity != null) {
            this.mFolderId = folderId;
            MultiHandWriteEntity multiHandWriteEntity = this.handWriteEntity;
            Intrinsics.checkNotNull(multiHandWriteEntity);
            multiHandWriteEntity.folderId = folderId;
            DetailViewModel detailViewModel = this.detailViewModel;
            Intrinsics.checkNotNull(detailViewModel);
            detailViewModel.move(checkedIds, folderId, folderName);
        }
    }

    @Override // com.miui.notes.detail.BaseNoteDetailFragment
    public void startQueryFolder() {
        long j;
        MultiHandWriteEntity multiHandWriteEntity = this.handWriteEntity;
        if (multiHandWriteEntity == null) {
            j = 0;
        } else {
            Intrinsics.checkNotNull(multiHandWriteEntity);
            j = multiHandWriteEntity.folderId;
        }
        this.mFolderId = j;
        String str = "_id <> " + this.mFolderId;
        FolderStore folderStore = FolderStore.INSTANCE;
        LoaderManager loaderManager = getLoaderManager();
        Intrinsics.checkNotNullExpressionValue(loaderManager, "getLoaderManager(...)");
        folderStore.query(loaderManager, 0, 2, new BaseNoteDetailFragment.NoteLoaderCallbacks(), str);
    }

    @Override // com.miui.notes.detail.BaseNoteDetailFragment
    protected long getNoteId() {
        MultiHandWriteEntity multiHandWriteEntity = this.handWriteEntity;
        if (multiHandWriteEntity == null) {
            return -1L;
        }
        Intrinsics.checkNotNull(multiHandWriteEntity);
        return multiHandWriteEntity.id;
    }

    @Override // com.miui.notes.detail.BaseNoteDetailFragment
    public void setInSearchMode(boolean inSearchMode) {
        super.setInSearchMode(inSearchMode);
        this.mSearchMode = inSearchMode;
        Log.d(getTAG(), "setInSearchMode:  inSearchMode:" + inSearchMode);
        if (this.mInSearchMode) {
            return;
        }
        this.queryText = "";
        setTitleHightLightColor();
        NoteLoadDataEntity noteLoadDataEntity = this.currentEntity;
        if (noteLoadDataEntity != null) {
            noteLoadDataEntity.setSearchText("");
        }
        MiuiHandWritingPreview miuiHandWritingPreview = this.multiHandWriteView;
        if (miuiHandWritingPreview != null) {
            miuiHandWritingPreview.clearHighLight();
        }
    }

    @Override // com.miui.notes.detail.BaseNoteDetailFragment, miuix.appcompat.app.Fragment, androidx.fragment.app.Fragment, android.content.ComponentCallbacks
    public void onConfigurationChanged(Configuration configuration) {
        Intrinsics.checkNotNullParameter(configuration, "configuration");
        super.onConfigurationChanged(configuration);
        Log.d(getTAG(), "onConfigurationChanged: ");
        ProgressDialog progressDialog = this.exportPreviewDialog;
        if (progressDialog != null) {
            Context context = getContext();
            progressDialog.setMessage(context != null ? context.getString(R.string.export_preview_loading) : null);
        }
        FragmentActivity activity = getActivity();
        if (activity == null || !activity.isInMultiWindowMode() || Navigator.get(this).getNavigationMode() != Navigator.Mode.C || getView() == null) {
            return;
        }
        BuildersKt__Builders_commonKt.launch$default(this, null, null, new C03501(null), 3, null);
    }

    /* JADX INFO: renamed from: com.miui.pad.feature.notes.multihandwrite.PadMultiHandWriteFragment$onConfigurationChanged$1, reason: invalid class name and case insensitive filesystem */
    /* JADX INFO: compiled from: PadMultiHandWriteFragment.kt */
    @Metadata(d1 = {"\u0000\n\n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\u0010\u0000\u001a\u00020\u0001*\u00020\u0002H\n"}, d2 = {"<anonymous>", "", "Lkotlinx/coroutines/CoroutineScope;"}, k = 3, mv = {2, 2, 0}, xi = 48)
    @DebugMetadata(c = "com.miui.pad.feature.notes.multihandwrite.PadMultiHandWriteFragment$onConfigurationChanged$1", f = "PadMultiHandWriteFragment.kt", i = {}, l = {}, m = "invokeSuspend", n = {}, s = {})
    static final class C03501 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
        int label;

        C03501(Continuation<? super C03501> continuation) {
            super(2, continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
            return PadMultiHandWriteFragment.this.new C03501(continuation);
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(CoroutineScope coroutineScope, Continuation<? super Unit> continuation) {
            return ((C03501) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            MutableLiveData<Boolean> mutableLiveDataIsInMultiWindowMode;
            IntrinsicsKt.getCOROUTINE_SUSPENDED();
            if (this.label == 0) {
                ResultKt.throwOnFailure(obj);
                ShareViewModel shareViewModel = PadMultiHandWriteFragment.this.mShareViewModel;
                if (shareViewModel != null && (mutableLiveDataIsInMultiWindowMode = shareViewModel.isInMultiWindowMode()) != null) {
                    LifecycleOwner viewLifecycleOwner = PadMultiHandWriteFragment.this.getViewLifecycleOwner();
                    final PadMultiHandWriteFragment padMultiHandWriteFragment = PadMultiHandWriteFragment.this;
                    mutableLiveDataIsInMultiWindowMode.observe(viewLifecycleOwner, new Observer() { // from class: com.miui.pad.feature.notes.multihandwrite.PadMultiHandWriteFragment$onConfigurationChanged$1$$ExternalSyntheticLambda0
                        @Override // androidx.lifecycle.Observer
                        public final void onChanged(Object obj2) {
                            PadMultiHandWriteFragment.C03501.invokeSuspend$lambda$0(padMultiHandWriteFragment, (Boolean) obj2);
                        }
                    });
                }
                return Unit.INSTANCE;
            }
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static final void invokeSuspend$lambda$0(PadMultiHandWriteFragment padMultiHandWriteFragment, Boolean bool) {
            Navigator.get(padMultiHandWriteFragment).getFragmentManager().popBackStack();
        }
    }

    private final void tryHideNote() {
        MiuiHandWritingPreview miuiHandWritingPreview;
        MultiHandWriteEntity multiHandWriteEntity = this.handWriteEntity;
        if (multiHandWriteEntity == null || (miuiHandWritingPreview = this.multiHandWriteView) == null) {
            return;
        }
        boolean z = multiHandWriteEntity.id > 0;
        boolean zIsContentEmpty = miuiHandWritingPreview.isContentEmpty();
        if (!z || zIsContentEmpty) {
            Logger.INSTANCE.d(getTAG(), "handwrite onContentVisibilityChanged handWriteEntity is null:" + (this.handWriteEntity == null) + " isEmpty: " + zIsContentEmpty);
            if (this.handWriteEntity != null) {
                Logger logger = Logger.INSTANCE;
                String tag = getTAG();
                MultiHandWriteEntity multiHandWriteEntity2 = this.handWriteEntity;
                logger.d(tag, "handwrite onContentVisibilityChanged handWriteEntity id: " + (multiHandWriteEntity2 != null ? Long.valueOf(multiHandWriteEntity2.id) : null));
            }
            Toast.makeText(BaseNoteApp.INSTANCE.getInstance(), R.string.error_hide_empty_note, 0).show();
            return;
        }
        if (RomUtils.isFoldDevice() && Navigator.get(this).getNavigationMode() == Navigator.Mode.C) {
            onBackPressed();
        }
        HomeViewModel homeViewModel = this.homeViewModel;
        if (homeViewModel != null) {
            homeViewModel.hideNote(multiHandWriteEntity.id);
        }
    }

    public final void requestDeleteNote() {
        AlertDialog.Builder builder = new AlertDialog.Builder(requireActivity(), 2131951644);
        builder.setTitle(R.string.new_alert_title_delete);
        builder.setMessage(R.string.alert_message_delete_note);
        builder.setPositiveButton(R.string.menu_delete, new DialogInterface.OnClickListener() { // from class: com.miui.pad.feature.notes.multihandwrite.PadMultiHandWriteFragment$$ExternalSyntheticLambda6
            @Override // android.content.DialogInterface.OnClickListener
            public final void onClick(DialogInterface dialogInterface, int i) {
                PadMultiHandWriteFragment.requestDeleteNote$lambda$2(this.f$0, dialogInterface, i);
            }
        });
        builder.setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() { // from class: com.miui.pad.feature.notes.multihandwrite.PadMultiHandWriteFragment$$ExternalSyntheticLambda7
            @Override // android.content.DialogInterface.OnClickListener
            public final void onClick(DialogInterface dialogInterface, int i) {
                PadMultiHandWriteFragment.requestDeleteNote$lambda$3(dialogInterface, i);
            }
        });
        builder.show();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void requestDeleteNote$lambda$2(PadMultiHandWriteFragment padMultiHandWriteFragment, DialogInterface dialogInterface, int i) {
        PadHandWriteStat.reportDeleteClickEvent(true);
        padMultiHandWriteFragment.deleteAndLoad();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void requestDeleteNote$lambda$3(DialogInterface dialog, int i) {
        Intrinsics.checkNotNullParameter(dialog, "dialog");
        PadHandWriteStat.reportDeleteClickEvent(false);
        dialog.dismiss();
    }

    private final void deleteAndLoad() {
        AudioUtils.playDeleteRingtone();
        MultiHandWriteEntity multiHandWriteEntity = this.handWriteEntity;
        if (multiHandWriteEntity != null) {
            Intrinsics.checkNotNull(multiHandWriteEntity);
            if (multiHandWriteEntity.id <= 0) {
                return;
            }
            MultiHandWriteEntity multiHandWriteEntity2 = this.handWriteEntity;
            Intrinsics.checkNotNull(multiHandWriteEntity2);
            if (HandWriteDBHelper.deleteNote(multiHandWriteEntity2.id)) {
                MultiHandWriteEntity multiHandWriteEntity3 = this.handWriteEntity;
                Intrinsics.checkNotNull(multiHandWriteEntity3);
                onNoteDeleted(multiHandWriteEntity3.id);
            }
        }
    }

    private final void onNoteDeleted(long noteId) {
        Toast.makeText(BaseNoteApp.INSTANCE.getInstance(), R.string.toast_notes_delete_success, 0).show();
        if (this.mActionListener != null) {
            Bundle bundle = new Bundle();
            bundle.putLong("note_id", noteId);
            FragmentActionListener fragmentActionListener = this.mActionListener;
            Intrinsics.checkNotNull(fragmentActionListener);
            fragmentActionListener.onAction(27, bundle);
        }
        this.handWriteEntity = null;
        if (Navigator.Mode.C == Navigator.get(this).getNavigationMode() && isAdded() && getActivity() != null) {
            onBackPressed();
        }
    }

    public final void updateMenu(Menu menu) {
        Intrinsics.checkNotNullParameter(menu, "menu");
        menu.findItem(R.id.send_to_desktop).setVisible(false);
        menu.findItem(R.id.set_alarm).setVisible(false);
        if (isNoteHidden()) {
            menu.findItem(R.id.hide).setVisible(false);
            menu.findItem(R.id.unhide).setVisible(true);
            menu.findItem(R.id.move_to).setVisible(false);
        } else {
            menu.findItem(R.id.hide).setVisible(true);
            menu.findItem(R.id.unhide).setVisible(false);
            menu.findItem(R.id.move_to).setVisible(true);
        }
    }

    public final boolean isNoteHidden() {
        MultiHandWriteEntity multiHandWriteEntity = this.handWriteEntity;
        if (multiHandWriteEntity == null) {
            return false;
        }
        Intrinsics.checkNotNull(multiHandWriteEntity);
        return multiHandWriteEntity.folderId == -4;
    }

    public final void setTitleHightLightColor() {
        String str;
        MiuiHandWritingPreview miuiHandWritingPreview = this.multiHandWriteView;
        if (miuiHandWritingPreview != null) {
            MultiHandWriteEntity multiHandWriteEntity = this.handWriteEntity;
            if (multiHandWriteEntity == null || (str = multiHandWriteEntity.title) == null) {
                str = "";
            }
            miuiHandWritingPreview.setTitleColor(str, String.valueOf(this.queryText), BaseNoteApp.INSTANCE.getInstance().getResources().getColor(R.color.user_query_highlight));
        }
    }

    /* JADX INFO: renamed from: com.miui.pad.feature.notes.multihandwrite.PadMultiHandWriteFragment$loadEntity$1, reason: invalid class name and case insensitive filesystem */
    /* JADX INFO: compiled from: PadMultiHandWriteFragment.kt */
    @Metadata(d1 = {"\u0000\n\n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\u0010\u0000\u001a\u00020\u0001*\u00020\u0002H\n"}, d2 = {"<anonymous>", "", "Lkotlinx/coroutines/CoroutineScope;"}, k = 3, mv = {2, 2, 0}, xi = 48)
    @DebugMetadata(c = "com.miui.pad.feature.notes.multihandwrite.PadMultiHandWriteFragment$loadEntity$1", f = "PadMultiHandWriteFragment.kt", i = {0}, l = {R2.attr.cardMaxElevation}, m = "invokeSuspend", n = {NotifyUtils.KEY_NOTE_ID}, s = {"J$0"})
    static final class C03491 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
        final /* synthetic */ Bundle $bundle;
        long J$0;
        int label;
        final /* synthetic */ PadMultiHandWriteFragment this$0;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        C03491(Bundle bundle, PadMultiHandWriteFragment padMultiHandWriteFragment, Continuation<? super C03491> continuation) {
            super(2, continuation);
            this.$bundle = bundle;
            this.this$0 = padMultiHandWriteFragment;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
            return new C03491(this.$bundle, this.this$0, continuation);
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(CoroutineScope coroutineScope, Continuation<? super Unit> continuation) {
            return ((C03491) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            MultiHandWriteEntity multiHandWriteEntity;
            Object coroutine_suspended = IntrinsicsKt.getCOROUTINE_SUSPENDED();
            int i = this.label;
            if (i == 0) {
                ResultKt.throwOnFailure(obj);
                long j = this.$bundle.getLong("data_id", -1L);
                String tag = this.this$0.getTAG();
                MultiHandWriteEntity multiHandWriteEntity2 = this.this$0.handWriteEntity;
                Log.d(tag, "loadEntity: NoteID:" + j + ",CurrentID:" + (multiHandWriteEntity2 != null ? Boxing.boxLong(multiHandWriteEntity2.id) : null));
                PadMultiHandWriteFragment padMultiHandWriteFragment = this.this$0;
                padMultiHandWriteFragment.mHighLightKeyWords = padMultiHandWriteFragment.requireArguments().getStringArrayList(NoteLoadDataEntity.HIGHLIGHT_KEYWORDS);
                this.this$0.queryText = this.$bundle.getString(NoteLoadDataEntity.SEARCH_TEXT, "");
                if ((this.this$0.handWriteEntity == null || (multiHandWriteEntity = this.this$0.handWriteEntity) == null || multiHandWriteEntity.id != j) && j != -2) {
                    this.J$0 = j;
                    this.label = 1;
                    if (BuildersKt.withContext(Dispatchers.getIO(), new C01081(this.this$0, j, null), this) == coroutine_suspended) {
                        return coroutine_suspended;
                    }
                }
                return Unit.INSTANCE;
            }
            if (i != 1) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            ResultKt.throwOnFailure(obj);
            this.this$0.updateUI();
            return Unit.INSTANCE;
        }

        /* JADX INFO: renamed from: com.miui.pad.feature.notes.multihandwrite.PadMultiHandWriteFragment$loadEntity$1$1, reason: invalid class name and collision with other inner class name */
        /* JADX INFO: compiled from: PadMultiHandWriteFragment.kt */
        @Metadata(d1 = {"\u0000\n\n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\u0010\u0000\u001a\u00020\u0001*\u00020\u0002H\n"}, d2 = {"<anonymous>", "", "Lkotlinx/coroutines/CoroutineScope;"}, k = 3, mv = {2, 2, 0}, xi = 48)
        @DebugMetadata(c = "com.miui.pad.feature.notes.multihandwrite.PadMultiHandWriteFragment$loadEntity$1$1", f = "PadMultiHandWriteFragment.kt", i = {}, l = {}, m = "invokeSuspend", n = {}, s = {})
        static final class C01081 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
            final /* synthetic */ long $noteId;
            int label;
            final /* synthetic */ PadMultiHandWriteFragment this$0;

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            C01081(PadMultiHandWriteFragment padMultiHandWriteFragment, long j, Continuation<? super C01081> continuation) {
                super(2, continuation);
                this.this$0 = padMultiHandWriteFragment;
                this.$noteId = j;
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
                return new C01081(this.this$0, this.$noteId, continuation);
            }

            @Override // kotlin.jvm.functions.Function2
            public final Object invoke(CoroutineScope coroutineScope, Continuation<? super Unit> continuation) {
                return ((C01081) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Object invokeSuspend(Object obj) {
                IntrinsicsKt.getCOROUTINE_SUSPENDED();
                if (this.label != 0) {
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                }
                ResultKt.throwOnFailure(obj);
                this.this$0.handWriteEntity = MultiHandWriteDataUtils.loadNote(this.$noteId);
                this.this$0.workingNote = HandWriteWorkingNote.INSTANCE.loadNote(this.$noteId);
                return Unit.INSTANCE;
            }
        }
    }

    private final void loadEntity(Bundle bundle) {
        BuildersKt__Builders_commonKt.launch$default(this, null, null, new C03491(bundle, this, null), 3, null);
    }

    public final void search(String queryText, List<String> recognizedTextFile) {
        if (TextUtils.isEmpty(queryText) || recognizedTextFile == null || recognizedTextFile.isEmpty()) {
            return;
        }
        MultiHandWriteEntity multiHandWriteEntity = this.handWriteEntity;
        String str = multiHandWriteEntity != null ? multiHandWriteEntity.entName : null;
        MultiHandWriteEntity multiHandWriteEntity2 = this.handWriteEntity;
        String str2 = multiHandWriteEntity2 != null ? multiHandWriteEntity2.snippet : null;
        MultiHandWriteEntity multiHandWriteEntity3 = this.handWriteEntity;
        SearchItemWrapper searchItemWrapper = new SearchItemWrapper(str, recognizedTextFile, str2, multiHandWriteEntity3 != null ? multiHandWriteEntity3.thumbnail : null);
        MultiHandWriteEntity multiHandWriteEntity4 = this.handWriteEntity;
        MiuiRecognizeWrapper miuiRecognizeWrapper = RecognizeDataHelper.getMiuiRecognizeWrapper(multiHandWriteEntity4 != null ? multiHandWriteEntity4.observeEntDir() : null, searchItemWrapper);
        MultiHandWriteEntity multiHandWriteEntity5 = this.handWriteEntity;
        MiuiSpannedDataWrapper miuiSpannedDataWrapper = RecognizeDataHelper.getMiuiSpannedDataWrapper(multiHandWriteEntity5 != null ? multiHandWriteEntity5.observeEntDir() : null, searchItemWrapper);
        MultiHandWriteEntity multiHandWriteEntity6 = this.handWriteEntity;
        CommonSearchWrapper rectFromRecognizeWrapper = RecognizeDataHelper.getRectFromRecognizeWrapper(miuiRecognizeWrapper, miuiSpannedDataWrapper, multiHandWriteEntity6 != null ? multiHandWriteEntity6.snippet : null, queryText);
        MiuiHandWritingPreview miuiHandWritingPreview = this.multiHandWriteView;
        if (miuiHandWritingPreview != null) {
            miuiHandWritingPreview.setSearchResult(rectFromRecognizeWrapper.searchPageIndex, getResources().getColor(R.color.creation_search_paint_height_light), 26, rectFromRecognizeWrapper);
        }
    }

    @Override // com.miui.notes.detail.BaseNoteDetailFragment
    protected void setNaviState(boolean show) {
        MiuiHandWritingPreview miuiHandWritingPreview;
        if (this.firstSetNavi || show != this.mIsNaviShow) {
            this.firstSetNavi = false;
            this.mIsNaviShow = show;
            if (isAdded()) {
                Log.d(getTAG(), "setNaviState: " + this.firstSetNavi + "," + this.mIsNaviShow);
                if (this.mIsNaviShow) {
                    if (this.mRootView == null || (miuiHandWritingPreview = this.multiHandWriteView) == null || this.customActionBarView == null) {
                        return;
                    }
                    Intrinsics.checkNotNull(miuiHandWritingPreview);
                    String savingTitle = getSavingTitle(miuiHandWritingPreview.isContentEmpty());
                    MiuiHandWritingPreview miuiHandWritingPreview2 = this.multiHandWriteView;
                    if (miuiHandWritingPreview2 != null) {
                        miuiHandWritingPreview2.setTitle(savingTitle);
                    }
                    MultiHandWriteEntity multiHandWriteEntity = this.handWriteEntity;
                    if (multiHandWriteEntity != null && multiHandWriteEntity != null) {
                        multiHandWriteEntity.title = savingTitle;
                    }
                    View view = this.customActionBarView;
                    if (view != null) {
                        view.setVisibility(0);
                        return;
                    }
                    return;
                }
                if (this.mRootView == null || this.multiHandWriteView == null || !RomUtils.isPadDevice()) {
                    return;
                }
                FragmentActivity fragmentActivityRequireActivity = requireActivity();
                Intrinsics.checkNotNullExpressionValue(fragmentActivityRequireActivity, "requireActivity(...)");
                Utils.hideSoftInput(fragmentActivityRequireActivity);
                View view2 = this.customActionBarView;
                if (view2 == null || view2 == null) {
                    return;
                }
                view2.setVisibility(8);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void setNaviStateWithJudge() {
        Navigator navigator = Navigator.get(this);
        if (navigator != null) {
            setNaviState(navigator.getNavigationMode() != Navigator.Mode.C);
        }
    }

    private final String getSavingTitle(boolean empty) {
        String title;
        String string = getResources().getString(R.string.handwrite_description);
        Intrinsics.checkNotNullExpressionValue(string, "getString(...)");
        MiuiHandWritingPreview miuiHandWritingPreview = this.multiHandWriteView;
        if (miuiHandWritingPreview == null || (title = miuiHandWritingPreview.getTitle()) == null) {
            title = "";
        }
        if (empty) {
            if (TextUtils.equals(title, string)) {
                return "";
            }
        } else if (TextUtils.isEmpty(title)) {
            return "";
        }
        return title;
    }

    private final void refreshLayout() {
        View view = this.rootView;
        Intrinsics.checkNotNull(view);
        int paddingStart = ViewCompat.getPaddingStart(view);
        View view2 = this.rootView;
        Intrinsics.checkNotNull(view2);
        int paddingTop = view2.getPaddingTop();
        View view3 = this.rootView;
        Intrinsics.checkNotNull(view3);
        int paddingEnd = ViewCompat.getPaddingEnd(view3);
        View view4 = this.rootView;
        Intrinsics.checkNotNull(view4);
        this.mRootViewInitPadding = new ViewUtils.RelativePadding(paddingStart, paddingTop, paddingEnd, view4.getPaddingBottom());
        HandWriteHeaderController.HeaderOperationCallback headerOperationCallback = this.headerOperationCallBack;
        View viewOnInflateView = null;
        HandWriteHeaderController handWriteHeaderController = headerOperationCallback != null ? new HandWriteHeaderController(this, headerOperationCallback) : null;
        this.mHeaderController = handWriteHeaderController;
        if (handWriteHeaderController != null) {
            LayoutInflater layoutInflaterFrom = LayoutInflater.from(getContext());
            Intrinsics.checkNotNullExpressionValue(layoutInflaterFrom, "from(...)");
            viewOnInflateView = handWriteHeaderController.onInflateView(layoutInflaterFrom, (ViewGroup) getView(), null);
        }
        this.customActionBarView = viewOnInflateView;
        ActionBar actionBar = getActionBar();
        if (actionBar != null) {
            actionBar.setDisplayShowCustomEnabled(true);
        }
        ActionBar actionBar2 = getActionBar();
        if (actionBar2 != null) {
            actionBar2.setDisplayShowTitleEnabled(false);
        }
        ActionBar actionBar3 = getActionBar();
        if (actionBar3 != null) {
            actionBar3.setCustomView(this.customActionBarView, new androidx.appcompat.app.ActionBar.LayoutParams(-1, -1));
        }
        if (this.mIsNaviShow) {
            if (RomUtils.isPadDevice() && (UIUtils.isFreeFormScreen(getResources().getConfiguration()) || UIUtils.isInMultiWindowMode(getActivity()))) {
                if (this.firstSetNavi) {
                    View view5 = this.customActionBarView;
                    if (view5 != null) {
                        view5.setVisibility(8);
                        return;
                    }
                    return;
                }
                if (!this.mIsNaviShow) {
                    View view6 = this.customActionBarView;
                    if (view6 != null) {
                        view6.setVisibility(8);
                        return;
                    }
                    return;
                }
                View view7 = this.customActionBarView;
                if (view7 != null) {
                    view7.setVisibility(0);
                    return;
                }
                return;
            }
            View view8 = this.customActionBarView;
            if (view8 != null) {
                view8.setVisibility(0);
                return;
            }
            return;
        }
        View view9 = this.customActionBarView;
        if (view9 != null) {
            view9.setVisibility(8);
        }
    }

    private final void initView(View view) {
        MiuiHandWritingPreview miuiHandWritingPreview = (MiuiHandWritingPreview) view.findViewById(R.id.handwriting_view);
        this.multiHandWriteView = miuiHandWritingPreview;
        if (miuiHandWritingPreview != null) {
            miuiHandWritingPreview.initUIProperties();
        }
        if (RomUtils.isPadDevice()) {
            MiuiHandWritingPreview miuiHandWritingPreview2 = this.multiHandWriteView;
            if (miuiHandWritingPreview2 != null) {
                miuiHandWritingPreview2.setPreviewOnClick(new CommonPreviewClickListener() { // from class: com.miui.pad.feature.notes.multihandwrite.PadMultiHandWriteFragment$$ExternalSyntheticLambda3
                    @Override // com.miui.handwriting.common.listener.CommonPreviewClickListener
                    public final void onClick() {
                        PadMultiHandWriteFragment.initView$lambda$5(this.f$0);
                    }
                });
            }
            MiuiHandWritingPreview miuiHandWritingPreview3 = this.multiHandWriteView;
            if (miuiHandWritingPreview3 != null) {
                miuiHandWritingPreview3.setPageStateListener(this.pageStateListener);
            }
        } else {
            MiuiHandWritingPreview miuiHandWritingPreview4 = this.multiHandWriteView;
            if (miuiHandWritingPreview4 != null) {
                miuiHandWritingPreview4.setPreviewOnClick(new CommonPreviewClickListener() { // from class: com.miui.pad.feature.notes.multihandwrite.PadMultiHandWriteFragment$$ExternalSyntheticLambda4
                    @Override // com.miui.handwriting.common.listener.CommonPreviewClickListener
                    public final void onClick() {
                        PadMultiHandWriteFragment.initView$lambda$6(this.f$0);
                    }
                });
            }
        }
        MiuiHandWritingPreview miuiHandWritingPreview5 = this.multiHandWriteView;
        if (miuiHandWritingPreview5 != null) {
            miuiHandWritingPreview5.setTitleChangedListener(new HeaderView.OnTitleChangedListener() { // from class: com.miui.pad.feature.notes.multihandwrite.PadMultiHandWriteFragment$$ExternalSyntheticLambda5
                @Override // com.miui.handwriting.common.view.page.HeaderView.OnTitleChangedListener
                public final void onTitleChanged(String str) {
                    PadMultiHandWriteFragment.initView$lambda$7(this.f$0, str);
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void initView$lambda$5(PadMultiHandWriteFragment padMultiHandWriteFragment) {
        if (Utils.isFastClick(0, 500L)) {
            return;
        }
        MiuiHandWritingPreview miuiHandWritingPreview = padMultiHandWriteFragment.multiHandWriteView;
        if (miuiHandWritingPreview != null) {
            miuiHandWritingPreview.clearTitleCursor();
        }
        padMultiHandWriteFragment.startActivity();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void initView$lambda$6(PadMultiHandWriteFragment padMultiHandWriteFragment) {
        Toast.makeText(BaseNoteApp.INSTANCE.getInstance(), padMultiHandWriteFragment.getResources().getString(R.string.hand_write_only_support_tablet_editing), 0).show();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void initView$lambda$7(PadMultiHandWriteFragment padMultiHandWriteFragment, String str) {
        MultiHandWriteEntity multiHandWriteEntity = padMultiHandWriteFragment.handWriteEntity;
        if (multiHandWriteEntity != null) {
            String str2 = multiHandWriteEntity != null ? multiHandWriteEntity.title : null;
            MiuiHandWritingPreview miuiHandWritingPreview = padMultiHandWriteFragment.multiHandWriteView;
            if (TextUtils.equals(str2, miuiHandWritingPreview != null ? miuiHandWritingPreview.getTitle() : null)) {
                return;
            }
            MultiHandWriteEntity multiHandWriteEntity2 = padMultiHandWriteFragment.handWriteEntity;
            Long lValueOf = multiHandWriteEntity2 != null ? Long.valueOf(multiHandWriteEntity2.id) : null;
            Intrinsics.checkNotNull(lValueOf);
            long jLongValue = lValueOf.longValue();
            MiuiHandWritingPreview miuiHandWritingPreview2 = padMultiHandWriteFragment.multiHandWriteView;
            MultiHandWriteDataUtils.updateHandWrite(jLongValue, miuiHandWritingPreview2 != null ? miuiHandWritingPreview2.getTitle() : null, System.currentTimeMillis());
        }
    }

    private final void inHeaderOperationCallBack() {
        this.headerOperationCallBack = new HandWriteHeaderController.HeaderOperationCallback() { // from class: com.miui.pad.feature.notes.multihandwrite.PadMultiHandWriteFragment.inHeaderOperationCallBack.1
            @Override // com.miui.pad.feature.notes.handwrite.HandWriteHeaderController.HeaderOperationCallback
            public void onPerformAction(int action, Object... args) {
                Intrinsics.checkNotNullParameter(args, "args");
                if (action == 1) {
                    PadMultiHandWriteFragment.this.getShareImage();
                }
            }
        };
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void getShareImage() {
        MiuiHandWritingPreview miuiHandWritingPreview;
        MiuiHandWritingPreview miuiHandWritingPreview2 = this.multiHandWriteView;
        if (TextUtils.isEmpty(miuiHandWritingPreview2 != null ? miuiHandWritingPreview2.getTitle() : null) && (miuiHandWritingPreview = this.multiHandWriteView) != null && miuiHandWritingPreview.isContentEmpty()) {
            Toast.makeText(BaseNoteApp.INSTANCE.getInstance(), R.string.error_send_empty_note, 0).show();
            return;
        }
        PadHandWriteStat.reportClickEvent(PadHandWriteStat.HAND_WEITE_SHARE_CLICK, null);
        if (this.handWriteEntity == null) {
            return;
        }
        showExportPreviewDialog();
        MiuiHandWritingPreview miuiHandWritingPreview3 = this.multiHandWriteView;
        if (miuiHandWritingPreview3 != null) {
            miuiHandWritingPreview3.saveEntFile(new CommonPageSaveListener() { // from class: com.miui.pad.feature.notes.multihandwrite.PadMultiHandWriteFragment$$ExternalSyntheticLambda9
                @Override // com.miui.handwriting.common.listener.CommonPageSaveListener
                public final void onDataSaved(boolean z, String str, boolean z2) {
                    PadMultiHandWriteFragment.getShareImage$lambda$11(this.f$0, z, str, z2);
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void getShareImage$lambda$11(final PadMultiHandWriteFragment padMultiHandWriteFragment, boolean z, final String str, boolean z2) {
        PagePreItemView currentItemView;
        PageInkPreModel pageInkPreModel;
        if (z) {
            MiuiHandWritingPreview miuiHandWritingPreview = padMultiHandWriteFragment.multiHandWriteView;
            final List list = CollectionsKt.toList(RangesKt.until(0, miuiHandWritingPreview != null ? miuiHandWritingPreview.getPageCount() : 0));
            MiuiHandWritingPreview miuiHandWritingPreview2 = padMultiHandWriteFragment.multiHandWriteView;
            if (miuiHandWritingPreview2 == null || (currentItemView = miuiHandWritingPreview2.getCurrentItemView()) == null || (pageInkPreModel = currentItemView.model) == null) {
                return;
            }
            pageInkPreModel.getMultiPageThumbnailStatus(new PdfThumbnailLoadListener() { // from class: com.miui.pad.feature.notes.multihandwrite.PadMultiHandWriteFragment$$ExternalSyntheticLambda8
                @Override // com.miui.libhandwritingpage.listener.PdfThumbnailLoadListener
                public final void onFinish() {
                    PadMultiHandWriteFragment.getShareImage$lambda$11$lambda$10(this.f$0, list, str);
                }
            });
            return;
        }
        padMultiHandWriteFragment.dismissExportPreviewDialog();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void getShareImage$lambda$11$lambda$10(final PadMultiHandWriteFragment padMultiHandWriteFragment, List list, final String str) {
        PagePreItemView currentItemView;
        PreICreationEngine creationEngine;
        PreMiCurveEngine curveEngine;
        PreExportSystem exportSystem;
        MiuiHandWritingPreview miuiHandWritingPreview = padMultiHandWriteFragment.multiHandWriteView;
        if (miuiHandWritingPreview == null || (currentItemView = miuiHandWritingPreview.getCurrentItemView()) == null || (creationEngine = currentItemView.getCreationEngine()) == null || (curveEngine = creationEngine.getCurveEngine()) == null || (exportSystem = curveEngine.getExportSystem()) == null) {
            return;
        }
        exportSystem.getPageThumbnailCustomList(list, 800, new Function1() { // from class: com.miui.pad.feature.notes.multihandwrite.PadMultiHandWriteFragment$$ExternalSyntheticLambda0
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                return PadMultiHandWriteFragment.getShareImage$lambda$11$lambda$10$lambda$9(this.f$0, str, (List) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit getShareImage$lambda$11$lambda$10$lambda$9(PadMultiHandWriteFragment padMultiHandWriteFragment, String str, List bitmapList) throws Throwable {
        Intrinsics.checkNotNullParameter(bitmapList, "bitmapList");
        padMultiHandWriteFragment.dismissExportPreviewDialog();
        ExportImageManager.INSTANCE.getInstance().setBitmapPreviewList(bitmapList);
        boolean z = padMultiHandWriteFragment.getActivity() instanceof PrivateNotesListActivity;
        Log.d(padMultiHandWriteFragment.getTAG(), "is private activity:" + z);
        if (!RomUtils.isOS31Support()) {
            FragmentActivity fragmentActivityRequireActivity = padMultiHandWriteFragment.requireActivity();
            String str2 = padMultiHandWriteFragment.dirPath;
            MiuiHandWritingPreview miuiHandWritingPreview = padMultiHandWriteFragment.multiHandWriteView;
            ExportShareActivity.launchActivity(fragmentActivityRequireActivity, 1, str2, str, miuiHandWritingPreview != null ? miuiHandWritingPreview.getTitle() : null, z);
        } else {
            ArrayList<? extends Parcelable> arrayList = new ArrayList<>();
            String str3 = "Creation_Image_" + System.currentTimeMillis();
            long length = 0;
            ClipData clipDataNewRawUri = null;
            int i = 0;
            for (Object obj : bitmapList) {
                int i2 = i + 1;
                if (i < 0) {
                    CollectionsKt.throwIndexOverflow();
                }
                File file = new File(FilePathUtils.getMultiPageExportFileDir(padMultiHandWriteFragment.getContext()), str3 + "_" + i2 + ".png");
                String path = file.getPath();
                BitmapUtil.saveBitmapToFile((Bitmap) obj, file, true);
                if (FileUtil.isFileExists(file)) {
                    length += new File(path).length();
                    Context context = padMultiHandWriteFragment.getContext();
                    Intrinsics.checkNotNull(path);
                    Uri uriCreateTempUriWithFilepath = padMultiHandWriteFragment.createTempUriWithFilepath(context, path);
                    if (clipDataNewRawUri == null) {
                        clipDataNewRawUri = ClipData.newRawUri("share_image", uriCreateTempUriWithFilepath);
                    } else {
                        clipDataNewRawUri.addItem(new ClipData.Item(uriCreateTempUriWithFilepath));
                    }
                    Intrinsics.checkNotNull(uriCreateTempUriWithFilepath);
                    arrayList.add(uriCreateTempUriWithFilepath);
                }
                i = i2;
            }
            boolean z2 = arrayList.size() > 1;
            Intent intent = new Intent(z2 ? "android.intent.action.SEND_MULTIPLE" : "android.intent.action.SEND");
            intent.setFlags(1);
            intent.setType("image/png");
            if (!z2) {
                intent.putExtra("android.intent.extra.STREAM", arrayList.get(0));
                intent.setClipData(ClipData.newRawUri(null, (Uri) arrayList.get(0)));
            } else {
                intent.setClipData(clipDataNewRawUri);
                Intrinsics.checkNotNull(intent.putParcelableArrayListExtra("android.intent.extra.STREAM", arrayList));
            }
            intent.putExtra(EXTRA_TAP_SHARE_SUPPORT, true);
            intent.putExtra(EXTRA_TAP_SHARE_MULTIPLE_INFO, String.valueOf(length));
            padMultiHandWriteFragment.startActivity(Intent.createChooser(intent, null));
        }
        return Unit.INSTANCE;
    }

    public final Uri createTempUriWithFilepath(Context context, String filepath) {
        Intrinsics.checkNotNullParameter(filepath, "filepath");
        File file = new File(filepath);
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (Exception e) {
                com.miui.common.tool.Logger.INSTANCE.e(getTAG(), " createNewFile error: " + e.getMessage());
                return null;
            }
        }
        return CompatUtils.getCompatUri(context, file);
    }

    private final void showExportPreviewDialog() {
        FragmentActivity activity = getActivity();
        if (activity == null) {
            return;
        }
        if (isAdded()) {
            ProgressDialog progressDialog = new ProgressDialog(activity);
            this.exportPreviewDialog = progressDialog;
            progressDialog.setIndeterminate(true);
            ProgressDialog progressDialog2 = this.exportPreviewDialog;
            if (progressDialog2 != null) {
                progressDialog2.setCanceledOnTouchOutside(false);
            }
            ProgressDialog progressDialog3 = this.exportPreviewDialog;
            if (progressDialog3 != null) {
                progressDialog3.setCancelable(false);
            }
        }
        ProgressDialog progressDialog4 = this.exportPreviewDialog;
        if (progressDialog4 != null) {
            progressDialog4.setMessage(activity.getString(R.string.export_preview_loading));
        }
        ProgressDialog progressDialog5 = this.exportPreviewDialog;
        if (progressDialog5 != null) {
            progressDialog5.show();
        }
    }

    private final void dismissExportPreviewDialog() {
        MainThreadUtils.runOnUiThread(new Runnable() { // from class: com.miui.pad.feature.notes.multihandwrite.PadMultiHandWriteFragment$$ExternalSyntheticLambda1
            @Override // java.lang.Runnable
            public final void run() {
                PadMultiHandWriteFragment.dismissExportPreviewDialog$lambda$12(this.f$0);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void dismissExportPreviewDialog$lambda$12(PadMultiHandWriteFragment padMultiHandWriteFragment) {
        ProgressDialog progressDialog = padMultiHandWriteFragment.exportPreviewDialog;
        if (progressDialog != null) {
            progressDialog.dismiss();
        }
    }

    private final void initNaviListener() {
        this.mStateListener = new Navigator.NavigatorStateListener() { // from class: com.miui.pad.feature.notes.multihandwrite.PadMultiHandWriteFragment.initNaviListener.1
            @Override // miuix.navigator.Navigator.NavigatorStateListener
            public void onNavigationOpenFinish() {
                Log.d(PadMultiHandWriteFragment.this.getTAG(), "onNavigationOpenFinish");
                MiuiHandWritingPreview miuiHandWritingPreview = PadMultiHandWriteFragment.this.multiHandWriteView;
                if (miuiHandWritingPreview != null) {
                    miuiHandWritingPreview.suppressLayout(false);
                }
                MiuiHandWritingPreview miuiHandWritingPreview2 = PadMultiHandWriteFragment.this.multiHandWriteView;
                if (miuiHandWritingPreview2 != null) {
                    miuiHandWritingPreview2.requestLayout();
                }
                View view = PadMultiHandWriteFragment.this.loadingContainer;
                if (view != null) {
                    view.setVisibility(8);
                }
            }

            @Override // miuix.navigator.Navigator.NavigatorStateListener
            public void onNavigationOpenBegin() {
                Log.d(PadMultiHandWriteFragment.this.getTAG(), "onNavigationOpenBegin");
                MiuiHandWritingPreview miuiHandWritingPreview = PadMultiHandWriteFragment.this.multiHandWriteView;
                if (miuiHandWritingPreview != null) {
                    miuiHandWritingPreview.suppressLayout(true);
                }
                View view = PadMultiHandWriteFragment.this.loadingContainer;
                if (view != null) {
                    view.setVisibility(0);
                }
            }

            @Override // miuix.navigator.Navigator.NavigatorStateListener
            public void onNavigationCloseBegin() {
                Log.d(PadMultiHandWriteFragment.this.getTAG(), "onNavigationCloseBegin");
                MiuiHandWritingPreview miuiHandWritingPreview = PadMultiHandWriteFragment.this.multiHandWriteView;
                if (miuiHandWritingPreview != null) {
                    miuiHandWritingPreview.suppressLayout(true);
                }
                View view = PadMultiHandWriteFragment.this.loadingContainer;
                if (view != null) {
                    view.setVisibility(0);
                }
            }

            @Override // miuix.navigator.Navigator.NavigatorStateListener
            public void onNavigationCloseFinish() {
                Log.d(PadMultiHandWriteFragment.this.getTAG(), "onNavigationCloseFinish");
                MiuiHandWritingPreview miuiHandWritingPreview = PadMultiHandWriteFragment.this.multiHandWriteView;
                if (miuiHandWritingPreview != null) {
                    miuiHandWritingPreview.suppressLayout(false);
                }
                MiuiHandWritingPreview miuiHandWritingPreview2 = PadMultiHandWriteFragment.this.multiHandWriteView;
                if (miuiHandWritingPreview2 != null) {
                    miuiHandWritingPreview2.requestLayout();
                }
                View view = PadMultiHandWriteFragment.this.loadingContainer;
                if (view != null) {
                    view.setVisibility(8);
                }
            }
        };
    }

    private final void initPageLoadListener() {
        if (this.pageLoadListener == null) {
            this.pageLoadListener = new CommonPageLoadListener() { // from class: com.miui.pad.feature.notes.multihandwrite.PadMultiHandWriteFragment.initPageLoadListener.1
                @Override // com.miui.handwriting.common.listener.CommonPageLoadListener
                public void onPdfError(int type) {
                }

                @Override // com.miui.handwriting.common.listener.CommonPageLoadListener
                public void onLoadCompleted(boolean success, boolean isPdf, String str) {
                    Intrinsics.checkNotNullParameter(str, "str");
                    Log.d(PadMultiHandWriteFragment.this.getTAG(), "onLoadCompleted: isSuccess:" + success);
                    if (PadMultiHandWriteFragment.this.isAdded() && success) {
                        PadDetailFragmentClassManager.isLoaded(true);
                        PadMultiHandWriteFragment padMultiHandWriteFragment = PadMultiHandWriteFragment.this;
                        padMultiHandWriteFragment.loadPageItem(padMultiHandWriteFragment.multiHandWriteView, PadMultiHandWriteFragment.this.handWriteEntity);
                        BuildersKt__Builders_commonKt.launch$default(PadMultiHandWriteFragment.this, Dispatchers.getIO(), null, new PadMultiHandWriteFragment$initPageLoadListener$1$onLoadCompleted$1(PadMultiHandWriteFragment.this, null), 2, null);
                    }
                }

                @Override // com.miui.handwriting.common.listener.CommonPageLoadListener
                public void onPageCount(int i) {
                    Log.d(PadMultiHandWriteFragment.this.getTAG(), "onPageCount: " + i);
                }

                @Override // com.miui.handwriting.common.listener.CommonPageLoadListener
                public String getSuniaSinglePageBgType() {
                    String str;
                    HandWriteWorkingNote handWriteWorkingNote = PadMultiHandWriteFragment.this.workingNote;
                    return (handWriteWorkingNote == null || (str = handWriteWorkingNote.canvasBgType) == null) ? "" : str;
                }
            };
        }
    }

    private final void initExportListener() {
        this.exportProcessListener = new CommonExportProcessListener<String>() { // from class: com.miui.pad.feature.notes.multihandwrite.PadMultiHandWriteFragment.initExportListener.1
            @Override // com.miui.handwriting.common.listener.CommonExportProcessListener
            public void exportStart() {
                PadMultiHandWriteFragment padMultiHandWriteFragment = PadMultiHandWriteFragment.this;
                String string = padMultiHandWriteFragment.getResources().getString(R.string.dlg_info_creating_message);
                Intrinsics.checkNotNullExpressionValue(string, "getString(...)");
                padMultiHandWriteFragment.showLoadingDialog(string);
            }

            @Override // com.miui.handwriting.common.listener.CommonExportProcessListener
            public void exportFinish(Boolean success, List<String> result) {
                PadMultiHandWriteFragment.this.dismissLoadingDialog();
            }
        };
    }

    /* JADX INFO: renamed from: com.miui.pad.feature.notes.multihandwrite.PadMultiHandWriteFragment$updateUI$1, reason: invalid class name and case insensitive filesystem */
    /* JADX INFO: compiled from: PadMultiHandWriteFragment.kt */
    @Metadata(d1 = {"\u0000\n\n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\u0010\u0000\u001a\u00020\u0001*\u00020\u0002H\n"}, d2 = {"<anonymous>", "", "Lkotlinx/coroutines/CoroutineScope;"}, k = 3, mv = {2, 2, 0}, xi = 48)
    @DebugMetadata(c = "com.miui.pad.feature.notes.multihandwrite.PadMultiHandWriteFragment$updateUI$1", f = "PadMultiHandWriteFragment.kt", i = {}, l = {}, m = "invokeSuspend", n = {}, s = {})
    static final class C03561 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
        int label;

        C03561(Continuation<? super C03561> continuation) {
            super(2, continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
            return PadMultiHandWriteFragment.this.new C03561(continuation);
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(CoroutineScope coroutineScope, Continuation<? super Unit> continuation) {
            return ((C03561) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            String str;
            IntrinsicsKt.getCOROUTINE_SUSPENDED();
            if (this.label == 0) {
                ResultKt.throwOnFailure(obj);
                if (PadMultiHandWriteFragment.this.handWriteEntity != null) {
                    PadMultiHandWriteFragment.this.dirPath = FilePathConstant.getEngineAndPageEntPath();
                    MiuiHandWritingPreview miuiHandWritingPreview = PadMultiHandWriteFragment.this.multiHandWriteView;
                    if (miuiHandWritingPreview != null) {
                        MultiHandWriteEntity multiHandWriteEntity = PadMultiHandWriteFragment.this.handWriteEntity;
                        if (multiHandWriteEntity == null || (str = multiHandWriteEntity.title) == null) {
                            str = "";
                        }
                        miuiHandWritingPreview.setTitle(str);
                    }
                    PadMultiHandWriteFragment.this.setTitleHightLightColor();
                    MultiHandWriteEntity multiHandWriteEntity2 = PadMultiHandWriteFragment.this.handWriteEntity;
                    if (multiHandWriteEntity2 != null) {
                        long sortDate = multiHandWriteEntity2.getSortDate();
                        MiuiHandWritingPreview miuiHandWritingPreview2 = PadMultiHandWriteFragment.this.multiHandWriteView;
                        if (miuiHandWritingPreview2 != null) {
                            miuiHandWritingPreview2.setTitleTime(sortDate);
                        }
                    }
                    CommonPageLoadListener commonPageLoadListener = PadMultiHandWriteFragment.this.pageLoadListener;
                    if (commonPageLoadListener != null) {
                        PadMultiHandWriteFragment padMultiHandWriteFragment = PadMultiHandWriteFragment.this;
                        MiuiHandWritingPreview miuiHandWritingPreview3 = padMultiHandWriteFragment.multiHandWriteView;
                        if (miuiHandWritingPreview3 != null) {
                            MultiHandWriteEntity multiHandWriteEntity3 = padMultiHandWriteFragment.handWriteEntity;
                            boolean zIsSuniaSignalPage = multiHandWriteEntity3 != null ? multiHandWriteEntity3.isSuniaSignalPage() : false;
                            MultiHandWriteEntity multiHandWriteEntity4 = padMultiHandWriteFragment.handWriteEntity;
                            if (multiHandWriteEntity4 != null) {
                                if (zIsSuniaSignalPage) {
                                    BaseNoteApp companion = BaseNoteApp.INSTANCE.getInstance();
                                    HandWriteWorkingNote handWriteWorkingNote = padMultiHandWriteFragment.workingNote;
                                    String attachmentPath = AttachmentUtils.getAttachmentPath(companion, handWriteWorkingNote != null ? handWriteWorkingNote.entName : null);
                                    if (attachmentPath != null) {
                                        miuiHandWritingPreview3.load(attachmentPath, commonPageLoadListener);
                                    }
                                } else {
                                    miuiHandWritingPreview3.load(multiHandWriteEntity4.observeEntDir() + "/multi_page_ent.ent", commonPageLoadListener);
                                }
                            }
                            if (padMultiHandWriteFragment.mSearchMode) {
                                String str2 = padMultiHandWriteFragment.queryText;
                                MultiHandWriteEntity multiHandWriteEntity5 = padMultiHandWriteFragment.handWriteEntity;
                                padMultiHandWriteFragment.search(str2, multiHandWriteEntity5 != null ? multiHandWriteEntity5.recognizedFile : null);
                            }
                        }
                    }
                    return Unit.INSTANCE;
                }
                return Unit.INSTANCE;
            }
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void updateUI() {
        BuildersKt__Builders_commonKt.launch$default(this, null, null, new C03561(null), 3, null);
    }

    @Override // com.miui.notes.detail.BaseNoteDetailFragment
    public boolean isDataEmpty() {
        MiuiHandWritingPreview miuiHandWritingPreview = this.multiHandWriteView;
        if (miuiHandWritingPreview == null) {
            return true;
        }
        Intrinsics.checkNotNull(miuiHandWritingPreview);
        return miuiHandWritingPreview.isContentEmpty();
    }

    private final void registerDownReceiver() {
        if (this.isDownloadReceiverRegistered) {
            return;
        }
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("com.miui.notes.handwrite.download");
        try {
            if (Utils.isAboveSDK34()) {
                requireActivity().registerReceiver(this.downloadFileReceiver, intentFilter, 2);
            } else {
                requireActivity().registerReceiver(this.downloadFileReceiver, intentFilter);
            }
            this.isDownloadReceiverRegistered = true;
            Unit unit = Unit.INSTANCE;
        } catch (Exception e) {
            Integer.valueOf(Log.e(getTAG(), "Failed to register download receiver", e));
        }
    }

    private final void unregisterDownloadReceiver() {
        if (this.isDownloadReceiverRegistered) {
            try {
                requireActivity().unregisterReceiver(this.downloadFileReceiver);
                this.isDownloadReceiverRegistered = false;
            } catch (IllegalArgumentException e) {
                Log.w(getTAG(), "Download receiver already unregistered", e);
            }
        }
    }

    /* JADX INFO: renamed from: com.miui.pad.feature.notes.multihandwrite.PadMultiHandWriteFragment$startActivity$1, reason: invalid class name and case insensitive filesystem */
    /* JADX INFO: compiled from: PadMultiHandWriteFragment.kt */
    @Metadata(d1 = {"\u0000\n\n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\u0010\u0000\u001a\u00020\u0001*\u00020\u0002H\n"}, d2 = {"<anonymous>", "", "Lkotlinx/coroutines/CoroutineScope;"}, k = 3, mv = {2, 2, 0}, xi = 48)
    @DebugMetadata(c = "com.miui.pad.feature.notes.multihandwrite.PadMultiHandWriteFragment$startActivity$1", f = "PadMultiHandWriteFragment.kt", i = {}, l = {R2.attr.floatingActionButtonSmallTertiaryStyle}, m = "invokeSuspend", n = {}, s = {})
    static final class C03551 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
        int label;

        C03551(Continuation<? super C03551> continuation) {
            super(2, continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
            return PadMultiHandWriteFragment.this.new C03551(continuation);
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(CoroutineScope coroutineScope, Continuation<? super Unit> continuation) {
            return ((C03551) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            Object coroutine_suspended = IntrinsicsKt.getCOROUTINE_SUSPENDED();
            int i = this.label;
            if (i == 0) {
                ResultKt.throwOnFailure(obj);
                Log.d(PadMultiHandWriteFragment.this.getTAG(), "startActivity: ");
                PadMultiHandWriteFragment.this.isStartEdit = true;
                this.label = 1;
                if (BuildersKt.withContext(Dispatchers.getIO(), new C01091(PadMultiHandWriteFragment.this, null), this) == coroutine_suspended) {
                    return coroutine_suspended;
                }
            } else {
                if (i != 1) {
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                }
                ResultKt.throwOnFailure(obj);
            }
            NoteLoadDataEntity noteLoadDataEntity = PadMultiHandWriteFragment.this.currentEntity;
            if (noteLoadDataEntity != null) {
                PadMultiHandWriteFragment padMultiHandWriteFragment = PadMultiHandWriteFragment.this;
                FragmentActivity fragmentActivityRequireActivity = padMultiHandWriteFragment.requireActivity();
                Intrinsics.checkNotNull(fragmentActivityRequireActivity, "null cannot be cast to non-null type com.miui.notes.ui.NotesListActivity");
                NotesListActivity notesListActivity = (NotesListActivity) fragmentActivityRequireActivity;
                CreationEditActivity.launchFromCreate(notesListActivity, noteLoadDataEntity, false, false, notesListActivity.getReceivePadMultiData());
                Navigator.get(padMultiHandWriteFragment).getFragmentManager().popBackStack();
            }
            return Unit.INSTANCE;
        }

        /* JADX INFO: renamed from: com.miui.pad.feature.notes.multihandwrite.PadMultiHandWriteFragment$startActivity$1$1, reason: invalid class name and collision with other inner class name */
        /* JADX INFO: compiled from: PadMultiHandWriteFragment.kt */
        @Metadata(d1 = {"\u0000\n\n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\u0010\u0000\u001a\u0004\u0018\u00010\u0001*\u00020\u0002H\n"}, d2 = {"<anonymous>", "", "Lkotlinx/coroutines/CoroutineScope;"}, k = 3, mv = {2, 2, 0}, xi = 48)
        @DebugMetadata(c = "com.miui.pad.feature.notes.multihandwrite.PadMultiHandWriteFragment$startActivity$1$1", f = "PadMultiHandWriteFragment.kt", i = {}, l = {}, m = "invokeSuspend", n = {}, s = {})
        static final class C01091 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
            int label;
            final /* synthetic */ PadMultiHandWriteFragment this$0;

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            C01091(PadMultiHandWriteFragment padMultiHandWriteFragment, Continuation<? super C01091> continuation) {
                super(2, continuation);
                this.this$0 = padMultiHandWriteFragment;
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
                return new C01091(this.this$0, continuation);
            }

            @Override // kotlin.jvm.functions.Function2
            public final Object invoke(CoroutineScope coroutineScope, Continuation<? super Unit> continuation) {
                return ((C01091) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Object invokeSuspend(Object obj) {
                IntrinsicsKt.getCOROUTINE_SUSPENDED();
                if (this.label == 0) {
                    ResultKt.throwOnFailure(obj);
                    MiuiHandWritingPreview miuiHandWritingPreview = this.this$0.multiHandWriteView;
                    if (miuiHandWritingPreview == null) {
                        return null;
                    }
                    PadMultiHandWriteFragment padMultiHandWriteFragment = this.this$0;
                    padMultiHandWriteFragment.savePageItem(miuiHandWritingPreview, padMultiHandWriteFragment.handWriteEntity);
                    return Unit.INSTANCE;
                }
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
        }
    }

    private final void startActivity() {
        BuildersKt__Builders_commonKt.launch$default(this, null, null, new C03551(null), 3, null);
    }

    private final void updateHomeButtonState(ScreenSpec screenSpec) {
        boolean z = PreferenceUtils.getNoteHomePageArrayWay(requireActivity(), 0) == 1;
        if (!RomUtils.isFoldDevice()) {
            z = false;
        }
        this.isHomeBack = z || screenSpec.type == 1;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void deleteCurrentEmptyNote() {
        MultiHandWriteEntity multiHandWriteEntity;
        String tag = getTAG();
        MultiHandWriteEntity multiHandWriteEntity2 = this.handWriteEntity;
        Log.d(tag, "deleteEmptyNote: ID:" + (multiHandWriteEntity2 != null ? Long.valueOf(multiHandWriteEntity2.id) : null) + " --isDataEmpty:" + isDataEmpty());
        if (!isDataEmpty() || (multiHandWriteEntity = this.handWriteEntity) == null) {
            return;
        }
        Intrinsics.checkNotNull(multiHandWriteEntity);
        if (multiHandWriteEntity.id <= 0 || this.isStartEdit || !checkIsMore()) {
            return;
        }
        BaseNoteApp companion = BaseNoteApp.INSTANCE.getInstance();
        MultiHandWriteEntity multiHandWriteEntity3 = this.handWriteEntity;
        Intrinsics.checkNotNull(multiHandWriteEntity3);
        NoteStore.deleteEmpty(companion, "_id = " + multiHandWriteEntity3.id);
    }

    private final boolean checkIsMore() {
        Integer num;
        Map<Long, Integer> entityIdCountMap;
        ShareViewModel shareViewModel = this.mShareViewModel;
        if (shareViewModel == null || (entityIdCountMap = shareViewModel.getEntityIdCountMap()) == null) {
            num = null;
        } else {
            MultiHandWriteEntity multiHandWriteEntity = this.handWriteEntity;
            Intrinsics.checkNotNull(multiHandWriteEntity);
            num = entityIdCountMap.get(Long.valueOf(multiHandWriteEntity.id));
        }
        Log.d(getTAG(), "checkIsMore: NoteListActivityCount:" + NotesListActivity.INSTANCE.getActivityCount() + ",IdCount:" + num);
        return num != null && num.intValue() == 0;
    }

    /* JADX INFO: renamed from: com.miui.pad.feature.notes.multihandwrite.PadMultiHandWriteFragment$showLoadingDialog$1, reason: invalid class name and case insensitive filesystem */
    /* JADX INFO: compiled from: PadMultiHandWriteFragment.kt */
    @Metadata(d1 = {"\u0000\n\n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\u0010\u0000\u001a\u00020\u0001*\u00020\u0002H\n"}, d2 = {"<anonymous>", "", "Lkotlinx/coroutines/CoroutineScope;"}, k = 3, mv = {2, 2, 0}, xi = 48)
    @DebugMetadata(c = "com.miui.pad.feature.notes.multihandwrite.PadMultiHandWriteFragment$showLoadingDialog$1", f = "PadMultiHandWriteFragment.kt", i = {}, l = {}, m = "invokeSuspend", n = {}, s = {})
    static final class C03541 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
        final /* synthetic */ String $message;
        int label;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        C03541(String str, Continuation<? super C03541> continuation) {
            super(2, continuation);
            this.$message = str;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
            return PadMultiHandWriteFragment.this.new C03541(this.$message, continuation);
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(CoroutineScope coroutineScope, Continuation<? super Unit> continuation) {
            return ((C03541) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            IntrinsicsKt.getCOROUTINE_SUSPENDED();
            if (this.label != 0) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            ResultKt.throwOnFailure(obj);
            if (PadMultiHandWriteFragment.this.isAdded()) {
                if (PadMultiHandWriteFragment.this.loadingDialog == null) {
                    PadMultiHandWriteFragment padMultiHandWriteFragment = PadMultiHandWriteFragment.this;
                    ProgressDialog progressDialog = new ProgressDialog(PadMultiHandWriteFragment.this.getContext());
                    String str = this.$message;
                    progressDialog.setIndeterminate(true);
                    progressDialog.setCanceledOnTouchOutside(false);
                    progressDialog.setCancelable(false);
                    progressDialog.setMessage(str);
                    padMultiHandWriteFragment.loadingDialog = progressDialog;
                }
                ProgressDialog progressDialog2 = PadMultiHandWriteFragment.this.loadingDialog;
                if (progressDialog2 != null) {
                    progressDialog2.show();
                }
                return Unit.INSTANCE;
            }
            return Unit.INSTANCE;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void showLoadingDialog(String message) {
        BuildersKt__Builders_commonKt.launch$default(this, null, null, new C03541(message, null), 3, null);
    }

    /* JADX INFO: renamed from: com.miui.pad.feature.notes.multihandwrite.PadMultiHandWriteFragment$dismissLoadingDialog$1, reason: invalid class name */
    /* JADX INFO: compiled from: PadMultiHandWriteFragment.kt */
    @Metadata(d1 = {"\u0000\n\n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\u0010\u0000\u001a\u00020\u0001*\u00020\u0002H\n"}, d2 = {"<anonymous>", "", "Lkotlinx/coroutines/CoroutineScope;"}, k = 3, mv = {2, 2, 0}, xi = 48)
    @DebugMetadata(c = "com.miui.pad.feature.notes.multihandwrite.PadMultiHandWriteFragment$dismissLoadingDialog$1", f = "PadMultiHandWriteFragment.kt", i = {}, l = {}, m = "invokeSuspend", n = {}, s = {})
    static final class AnonymousClass1 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
        int label;

        AnonymousClass1(Continuation<? super AnonymousClass1> continuation) {
            super(2, continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
            return PadMultiHandWriteFragment.this.new AnonymousClass1(continuation);
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(CoroutineScope coroutineScope, Continuation<? super Unit> continuation) {
            return ((AnonymousClass1) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            ProgressDialog progressDialog;
            IntrinsicsKt.getCOROUTINE_SUSPENDED();
            if (this.label == 0) {
                ResultKt.throwOnFailure(obj);
                if (PadMultiHandWriteFragment.this.loadingDialog != null) {
                    ProgressDialog progressDialog2 = PadMultiHandWriteFragment.this.loadingDialog;
                    Intrinsics.checkNotNull(progressDialog2);
                    if (progressDialog2.isShowing() && (progressDialog = PadMultiHandWriteFragment.this.loadingDialog) != null) {
                        progressDialog.dismiss();
                    }
                }
                return Unit.INSTANCE;
            }
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void dismissLoadingDialog() {
        BuildersKt__Builders_commonKt.launch$default(this, null, null, new AnonymousClass1(null), 3, null);
    }

    /* JADX INFO: renamed from: com.miui.pad.feature.notes.multihandwrite.PadMultiHandWriteFragment$finishActivity$1, reason: invalid class name and case insensitive filesystem */
    /* JADX INFO: compiled from: PadMultiHandWriteFragment.kt */
    @Metadata(d1 = {"\u0000\n\n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\u0010\u0000\u001a\u00020\u0001*\u00020\u0002H\n"}, d2 = {"<anonymous>", "", "Lkotlinx/coroutines/CoroutineScope;"}, k = 3, mv = {2, 2, 0}, xi = 48)
    @DebugMetadata(c = "com.miui.pad.feature.notes.multihandwrite.PadMultiHandWriteFragment$finishActivity$1", f = "PadMultiHandWriteFragment.kt", i = {}, l = {}, m = "invokeSuspend", n = {}, s = {})
    static final class C03431 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
        int label;

        C03431(Continuation<? super C03431> continuation) {
            super(2, continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
            return PadMultiHandWriteFragment.this.new C03431(continuation);
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(CoroutineScope coroutineScope, Continuation<? super Unit> continuation) {
            return ((C03431) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            IntrinsicsKt.getCOROUTINE_SUSPENDED();
            if (this.label != 0) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            ResultKt.throwOnFailure(obj);
            Log.d(PadMultiHandWriteFragment.this.getTAG(), "finishActivity: ");
            try {
                if (PadMultiHandWriteFragment.this.isAdded() && PadMultiHandWriteFragment.this.getActivity() != null && !PadMultiHandWriteFragment.this.requireActivity().isFinishing()) {
                    PadMultiHandWriteFragment.this.requireActivity().finish();
                }
            } catch (Exception e) {
                Log.e(PadMultiHandWriteFragment.this.getTAG(), "Error finishing activity", e);
            }
            return Unit.INSTANCE;
        }
    }

    private final void finishActivity() {
        BuildersKt__Builders_commonKt.launch$default(this, null, null, new C03431(null), 3, null);
    }

    public final void registerCreationTaskReceiver() {
        if (this.isOperationReceiverRegistered) {
            Log.w(getTAG(), "Operation receiver already registered");
            return;
        }
        this.operationReceiver = new C03521();
        IntentFilter intentFilter = new IntentFilter(Constants.OPERATION_INTENT);
        intentFilter.addAction(Constants.ADJUST_PAGE_SIZE_INTENT);
        try {
            BroadcastReceiver broadcastReceiver = this.operationReceiver;
            if (broadcastReceiver != null) {
                LocalBroadcastManager.getInstance(BaseNoteApp.INSTANCE.getInstance()).registerReceiver(broadcastReceiver, intentFilter);
                this.isOperationReceiverRegistered = true;
            }
        } catch (Exception e) {
            Log.e(getTAG(), "Failed to register operation receiver", e);
        }
    }

    /* JADX INFO: renamed from: com.miui.pad.feature.notes.multihandwrite.PadMultiHandWriteFragment$registerCreationTaskReceiver$1, reason: invalid class name and case insensitive filesystem */
    /* JADX INFO: compiled from: PadMultiHandWriteFragment.kt */
    @Metadata(d1 = {"\u0000\u001d\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000*\u0001\u0000\b\n\u0018\u00002\u00020\u0001J\u001a\u0010\u0002\u001a\u00020\u00032\b\u0010\u0004\u001a\u0004\u0018\u00010\u00052\u0006\u0010\u0006\u001a\u00020\u0007H\u0016¨\u0006\b"}, d2 = {"com/miui/pad/feature/notes/multihandwrite/PadMultiHandWriteFragment$registerCreationTaskReceiver$1", "Landroid/content/BroadcastReceiver;", "onReceive", "", CoreConstants.CONTEXT_SCOPE_VALUE, "Landroid/content/Context;", "intent", "Landroid/content/Intent;", "app_PhoneGlobalRelease"}, k = 1, mv = {2, 2, 0}, xi = 48)
    public static final class C03521 extends BroadcastReceiver {
        C03521() {
        }

        @Override // android.content.BroadcastReceiver
        public void onReceive(Context context, Intent intent) {
            final String uniqueFileName;
            PagePreItemView currentItemView;
            PageInkPreModel pageInkPreModel;
            Intrinsics.checkNotNullParameter(intent, "intent");
            Bundle extras = intent.getExtras();
            if (TextUtils.equals(Constants.OPERATION_INTENT, intent.getAction()) && extras != null && TextUtils.equals(extras.getString("operationType"), "export")) {
                if (System.currentTimeMillis() - PadMultiHandWriteFragment.this.mLastExportTime >= 500) {
                    PadMultiHandWriteFragment.this.mLastExportTime = System.currentTimeMillis();
                    boolean isInPrivate = PreferenceUtils.getIsInPrivate();
                    FragmentActivity fragmentActivityRequireActivity = PadMultiHandWriteFragment.this.requireActivity();
                    Intrinsics.checkNotNullExpressionValue(fragmentActivityRequireActivity, "requireActivity(...)");
                    if (!(isInPrivate && Intrinsics.areEqual(fragmentActivityRequireActivity.getClass().getName(), PadMultiHandWriteFragment.PRIVATE_NOTES_ACTIVITY)) && isInPrivate) {
                        return;
                    }
                    final int i = extras.getInt("exportType");
                    String string = extras.getString("folderPath");
                    String string2 = extras.getString("folderName");
                    final ArrayList<Integer> integerArrayList = extras.getIntegerArrayList("exportPageIndexList");
                    Log.d(PadMultiHandWriteFragment.this.getTAG(), "export type:" + i);
                    if (integerArrayList == null || integerArrayList.isEmpty()) {
                        return;
                    }
                    if (i == 1) {
                        uniqueFileName = FileUtil.getUniqueFileName(string + InternalZipConstants.ZIP_FILE_SEPARATOR + string2);
                        Intrinsics.checkNotNullExpressionValue(uniqueFileName, "getUniqueFileName(...)");
                        ExportImageManager.INSTANCE.getInstance().startExportPdf(i, integerArrayList, uniqueFileName, PadMultiHandWriteFragment.this);
                    } else {
                        uniqueFileName = string + InternalZipConstants.ZIP_FILE_SEPARATOR + FileUtil.getUniqueFolderName(string, string2);
                        MiuiHandWritingPreview miuiHandWritingPreview = PadMultiHandWriteFragment.this.multiHandWriteView;
                        if (miuiHandWritingPreview != null && (currentItemView = miuiHandWritingPreview.getCurrentItemView()) != null && (pageInkPreModel = currentItemView.model) != null) {
                            final PadMultiHandWriteFragment padMultiHandWriteFragment = PadMultiHandWriteFragment.this;
                            pageInkPreModel.getMultiPageThumbnailStatus(new PdfThumbnailLoadListener() { // from class: com.miui.pad.feature.notes.multihandwrite.PadMultiHandWriteFragment$registerCreationTaskReceiver$1$$ExternalSyntheticLambda0
                                @Override // com.miui.libhandwritingpage.listener.PdfThumbnailLoadListener
                                public final void onFinish() {
                                    PadMultiHandWriteFragment.C03521.onReceive$lambda$0(i, integerArrayList, uniqueFileName, padMultiHandWriteFragment);
                                }
                            });
                        }
                    }
                    Log.d(PadMultiHandWriteFragment.this.getTAG(), "folder path:" + string + " --folder name:" + string2 + " ---out path: " + uniqueFileName);
                    return;
                }
                Log.w(PadMultiHandWriteFragment.this.getTAG(), "export time too near return");
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static final void onReceive$lambda$0(int i, ArrayList arrayList, String str, PadMultiHandWriteFragment padMultiHandWriteFragment) {
            ExportImageManager.INSTANCE.getInstance().startExportImage(i, arrayList, str, padMultiHandWriteFragment);
        }
    }

    private final void unregisterOperationReceiver() {
        if (this.isOperationReceiverRegistered && this.operationReceiver != null) {
            try {
                LocalBroadcastManager localBroadcastManager = LocalBroadcastManager.getInstance(BaseNoteApp.INSTANCE.getInstance());
                BroadcastReceiver broadcastReceiver = this.operationReceiver;
                Intrinsics.checkNotNull(broadcastReceiver);
                localBroadcastManager.unregisterReceiver(broadcastReceiver);
                this.isOperationReceiverRegistered = false;
            } catch (IllegalArgumentException e) {
                Log.w(getTAG(), "Operation receiver already unregistered", e);
            }
        }
        this.operationReceiver = null;
    }

    private final void releasePage() {
        PagePreItemView currentItemView;
        PreICreationEngine creationEngine;
        PreMiCurveEngine curveEngine;
        PrePageSystem pageSystem;
        PagePreItemView currentItemView2;
        PageInkPreModel pageInkPreModel;
        PagePreItemView currentItemView3;
        PageInkPreModel pageInkPreModel2;
        List<Integer> list = this.mLastExportList;
        if (list != null) {
            Intrinsics.checkNotNull(list);
            if (list.isEmpty()) {
                return;
            }
            MiuiHandWritingPreview miuiHandWritingPreview = this.multiHandWriteView;
            if (TextUtils.isEmpty((miuiHandWritingPreview == null || (currentItemView3 = miuiHandWritingPreview.getCurrentItemView()) == null || (pageInkPreModel2 = currentItemView3.model) == null) ? null : pageInkPreModel2.getPdfId())) {
                Log.d(getTAG(), "common page need not release");
                return;
            }
            MiuiHandWritingPreview miuiHandWritingPreview2 = this.multiHandWriteView;
            int currentPosition = (miuiHandWritingPreview2 == null || (currentItemView2 = miuiHandWritingPreview2.getCurrentItemView()) == null || (pageInkPreModel = currentItemView2.model) == null) ? 0 : pageInkPreModel.getCurrentPosition();
            List<Integer> list2 = this.mLastExportList;
            Intrinsics.checkNotNull(list2);
            Iterator<Integer> it = list2.iterator();
            while (it.hasNext()) {
                int iIntValue = it.next().intValue();
                if (iIntValue < currentPosition - 4 || iIntValue > currentPosition + 4) {
                    MiuiHandWritingPreview miuiHandWritingPreview3 = this.multiHandWriteView;
                    if (miuiHandWritingPreview3 != null && (currentItemView = miuiHandWritingPreview3.getCurrentItemView()) != null && (creationEngine = currentItemView.getCreationEngine()) != null && (curveEngine = creationEngine.getCurveEngine()) != null && (pageSystem = curveEngine.getPageSystem()) != null) {
                        pageSystem.releasePageBgImage(iIntValue);
                    }
                }
            }
            this.mLastExportList = null;
        }
    }

    @Override // com.miui.handwriting.common.view.thumbnail.ExportImageManager.ExportCallback
    public void loadPreviewFinish(int exportType) {
        dismissExportPreviewDialog();
        FragmentActivity fragmentActivityRequireActivity = requireActivity();
        String str = this.dirPath;
        MultiHandWriteEntity multiHandWriteEntity = this.handWriteEntity;
        String str2 = multiHandWriteEntity != null ? multiHandWriteEntity.entName : null;
        MiuiHandWritingPreview miuiHandWritingPreview = this.multiHandWriteView;
        ExportShareActivity.launchActivity((Activity) fragmentActivityRequireActivity, 1, str, str2, miuiHandWritingPreview != null ? miuiHandWritingPreview.getTitle() : null, false);
    }

    @Override // com.miui.handwriting.common.view.thumbnail.ExportImageManager.ExportCallback
    public void requirePreviewList(List<Integer> list) {
        PagePreItemView currentItemView;
        PageInkPreModel pageInkPreModel;
        Intrinsics.checkNotNullParameter(list, "list");
        MiuiHandWritingPreview miuiHandWritingPreview = this.multiHandWriteView;
        if (miuiHandWritingPreview == null || (currentItemView = miuiHandWritingPreview.getCurrentItemView()) == null || (pageInkPreModel = currentItemView.model) == null) {
            return;
        }
        pageInkPreModel.getPdfBitmapForExport(list, new C03531(list));
    }

    /* JADX INFO: renamed from: com.miui.pad.feature.notes.multihandwrite.PadMultiHandWriteFragment$requirePreviewList$1, reason: invalid class name and case insensitive filesystem */
    /* JADX INFO: compiled from: PadMultiHandWriteFragment.kt */
    @Metadata(d1 = {"\u0000\u0013\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002*\u0001\u0000\b\n\u0018\u00002\u00020\u0001J\b\u0010\u0002\u001a\u00020\u0003H\u0016J\b\u0010\u0004\u001a\u00020\u0003H\u0016¨\u0006\u0005"}, d2 = {"com/miui/pad/feature/notes/multihandwrite/PadMultiHandWriteFragment$requirePreviewList$1", "Lcom/miui/libhandwritingpage/listener/PDFExportListener;", "onFinish", "", "onLoading", "app_PhoneGlobalRelease"}, k = 1, mv = {2, 2, 0}, xi = 48)
    public static final class C03531 implements PDFExportListener {
        final /* synthetic */ List<Integer> $list;

        C03531(List<Integer> list) {
            this.$list = list;
        }

        @Override // com.miui.libhandwritingpage.listener.PDFExportListener
        public void onFinish() {
            PagePreItemView currentItemView;
            PreICreationEngine creationEngine;
            PreMiCurveEngine curveEngine;
            PreExportSystem exportSystem;
            MiuiHandWritingPreview miuiHandWritingPreview = PadMultiHandWriteFragment.this.multiHandWriteView;
            if (miuiHandWritingPreview == null || (currentItemView = miuiHandWritingPreview.getCurrentItemView()) == null || (creationEngine = currentItemView.getCreationEngine()) == null || (curveEngine = creationEngine.getCurveEngine()) == null || (exportSystem = curveEngine.getExportSystem()) == null) {
                return;
            }
            exportSystem.getPageThumbnailCustomList(this.$list, 400, new Function1() { // from class: com.miui.pad.feature.notes.multihandwrite.PadMultiHandWriteFragment$requirePreviewList$1$$ExternalSyntheticLambda0
                @Override // kotlin.jvm.functions.Function1
                public final Object invoke(Object obj) {
                    return PadMultiHandWriteFragment.C03531.onFinish$lambda$0((List) obj);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static final Unit onFinish$lambda$0(List bitmapList) {
            Intrinsics.checkNotNullParameter(bitmapList, "bitmapList");
            ExportImageManager.INSTANCE.getInstance().addPreviewBitmap(bitmapList);
            return Unit.INSTANCE;
        }

        @Override // com.miui.libhandwritingpage.listener.PDFExportListener
        public void onLoading() {
            Log.e(PadMultiHandWriteFragment.this.getTAG(), "require preview list error");
        }
    }

    @Override // com.miui.handwriting.common.view.thumbnail.ExportImageManager.ExportCallback
    public void loadPreviewError(int errorCode) {
        dismissExportPreviewDialog();
        Log.e(getTAG(), "load preview error:" + errorCode);
    }

    @Override // com.miui.handwriting.common.view.thumbnail.ExportImageManager.ExportCallback
    public void exportProgress(int progress) {
        Intent intent = new Intent(Constants.EXPORT_OPERATION_INTENT);
        intent.putExtra("operationType", "onProgress");
        intent.putExtra("percentDone", progress);
        Log.d(getTAG(), "Fragment receive: MSG_FRAGMENT_EXPORT_ON_PROGRESS: " + progress);
        LocalBroadcastManager.getInstance(BaseNoteApp.INSTANCE.getInstance()).sendBroadcast(intent);
    }

    @Override // com.miui.handwriting.common.view.thumbnail.ExportImageManager.ExportCallback
    public void requireNextList(List<Integer> list) {
        PagePreItemView currentItemView;
        PageInkPreModel pageInkPreModel;
        PagePreItemView currentItemView2;
        PreICreationEngine creationEngine;
        PreMiCurveEngine curveEngine;
        PreExportSystem exportSystem;
        PagePreItemView currentItemView3;
        PageInkPreModel pageInkPreModel2;
        Intrinsics.checkNotNullParameter(list, "list");
        releasePage();
        this.mLastExportList = TypeIntrinsics.asMutableList(list);
        MiuiHandWritingPreview miuiHandWritingPreview = this.multiHandWriteView;
        if (TextUtils.isEmpty((miuiHandWritingPreview == null || (currentItemView3 = miuiHandWritingPreview.getCurrentItemView()) == null || (pageInkPreModel2 = currentItemView3.model) == null) ? null : pageInkPreModel2.getPdfId())) {
            MiuiHandWritingPreview miuiHandWritingPreview2 = this.multiHandWriteView;
            if (miuiHandWritingPreview2 == null || (currentItemView2 = miuiHandWritingPreview2.getCurrentItemView()) == null || (creationEngine = currentItemView2.getCreationEngine()) == null || (curveEngine = creationEngine.getCurveEngine()) == null || (exportSystem = curveEngine.getExportSystem()) == null) {
                return;
            }
            exportSystem.getPageThumbnailCustomList(list, 1440, new Function1() { // from class: com.miui.pad.feature.notes.multihandwrite.PadMultiHandWriteFragment$$ExternalSyntheticLambda2
                @Override // kotlin.jvm.functions.Function1
                public final Object invoke(Object obj) {
                    return PadMultiHandWriteFragment.requireNextList$lambda$15((List) obj);
                }
            });
            return;
        }
        MiuiHandWritingPreview miuiHandWritingPreview3 = this.multiHandWriteView;
        if (miuiHandWritingPreview3 == null || (currentItemView = miuiHandWritingPreview3.getCurrentItemView()) == null || (pageInkPreModel = currentItemView.model) == null) {
            return;
        }
        pageInkPreModel.getPdfBitmapForExport(list, new AnonymousClass2(list));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit requireNextList$lambda$15(List bitmapList) {
        Intrinsics.checkNotNullParameter(bitmapList, "bitmapList");
        ExportImageManager.INSTANCE.getInstance().addExportBitmap(bitmapList);
        return Unit.INSTANCE;
    }

    /* JADX INFO: renamed from: com.miui.pad.feature.notes.multihandwrite.PadMultiHandWriteFragment$requireNextList$2, reason: invalid class name */
    /* JADX INFO: compiled from: PadMultiHandWriteFragment.kt */
    @Metadata(d1 = {"\u0000\u0013\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002*\u0001\u0000\b\n\u0018\u00002\u00020\u0001J\b\u0010\u0002\u001a\u00020\u0003H\u0016J\b\u0010\u0004\u001a\u00020\u0003H\u0016¨\u0006\u0005"}, d2 = {"com/miui/pad/feature/notes/multihandwrite/PadMultiHandWriteFragment$requireNextList$2", "Lcom/miui/libhandwritingpage/listener/PDFExportListener;", "onFinish", "", "onLoading", "app_PhoneGlobalRelease"}, k = 1, mv = {2, 2, 0}, xi = 48)
    public static final class AnonymousClass2 implements PDFExportListener {
        final /* synthetic */ List<Integer> $list;

        AnonymousClass2(List<Integer> list) {
            this.$list = list;
        }

        @Override // com.miui.libhandwritingpage.listener.PDFExportListener
        public void onFinish() {
            PagePreItemView currentItemView;
            PreICreationEngine creationEngine;
            PreMiCurveEngine curveEngine;
            PreExportSystem exportSystem;
            MiuiHandWritingPreview miuiHandWritingPreview = PadMultiHandWriteFragment.this.multiHandWriteView;
            if (miuiHandWritingPreview == null || (currentItemView = miuiHandWritingPreview.getCurrentItemView()) == null || (creationEngine = currentItemView.getCreationEngine()) == null || (curveEngine = creationEngine.getCurveEngine()) == null || (exportSystem = curveEngine.getExportSystem()) == null) {
                return;
            }
            exportSystem.getPageThumbnailCustomList(this.$list, 1440, new Function1() { // from class: com.miui.pad.feature.notes.multihandwrite.PadMultiHandWriteFragment$requireNextList$2$$ExternalSyntheticLambda0
                @Override // kotlin.jvm.functions.Function1
                public final Object invoke(Object obj) {
                    return PadMultiHandWriteFragment.AnonymousClass2.onFinish$lambda$0((List) obj);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static final Unit onFinish$lambda$0(List bitmapList) {
            Intrinsics.checkNotNullParameter(bitmapList, "bitmapList");
            ExportImageManager.INSTANCE.getInstance().addExportBitmap(bitmapList);
            return Unit.INSTANCE;
        }

        @Override // com.miui.libhandwritingpage.listener.PDFExportListener
        public void onLoading() {
            Log.e(PadMultiHandWriteFragment.this.getTAG(), "require next list error");
        }
    }

    @Override // com.miui.handwriting.common.view.thumbnail.ExportImageManager.ExportCallback
    public void exportFinish(int exportType, String path) {
        Intrinsics.checkNotNullParameter(path, "path");
        releasePage();
        Intent intent = new Intent(Constants.EXPORT_OPERATION_INTENT);
        intent.putExtra("operationType", "onCompleted");
        intent.putExtra("isCompletedSuccess", true);
        intent.putExtra("completedPath", path);
        Log.d(getTAG(), "EXPORT_completedPath:" + path);
        intent.putExtra("exportType", exportType);
        LocalBroadcastManager.getInstance(BaseNoteApp.INSTANCE.getInstance()).sendBroadcast(intent);
    }

    @Override // com.miui.handwriting.common.view.thumbnail.ExportImageManager.ExportCallback
    public void exportError(int errorCode, int exportType, String path) {
        Intrinsics.checkNotNullParameter(path, "path");
        dismissExportPreviewDialog();
        Log.e(getTAG(), "export error:" + errorCode);
        releasePage();
        Intent intent = new Intent(Constants.EXPORT_OPERATION_INTENT);
        intent.putExtra("operationType", "onCompleted");
        intent.putExtra("isCompletedSuccess", false);
        intent.putExtra("completedPath", path);
        Log.d(getTAG(), "EXPORT_completedPath:" + path);
        intent.putExtra("exportType", exportType);
        LocalBroadcastManager.getInstance(BaseNoteApp.INSTANCE.getInstance()).sendBroadcast(intent);
    }

    public final void loadPageItem(MiuiHandWritingPreview multiHandWriteView, MultiHandWriteEntity handWriteEntity) {
        if (handWriteEntity == null || multiHandWriteView == null) {
            return;
        }
        int iMax = Math.max(0, handWriteEntity.getCurrentPageNum());
        float fCoerceIn = RangesKt.coerceIn(handWriteEntity.getCurrentPageScale(), 0.55f, 3.0f);
        Log.d(getTAG(), "loadPageItem: CurrentPageNum=" + iMax + ", currentPageScale=" + fCoerceIn);
        multiHandWriteView.resetScale(true, Float.valueOf(fCoerceIn));
        multiHandWriteView.jumpPage(iMax, false);
    }

    public final void savePageItem(MiuiHandWritingPreview multiHandWriteView, MultiHandWriteEntity handWriteEntity) {
        Intrinsics.checkNotNullParameter(multiHandWriteView, "multiHandWriteView");
        if (handWriteEntity == null) {
            return;
        }
        int iCoerceIn = RangesKt.coerceIn(multiHandWriteView.getCurrentPosition(), 0, Math.max(0, multiHandWriteView.getPageCount() - 1));
        float factoryScale = multiHandWriteView.getFactoryScale();
        Log.d(getTAG(), "savePageItem: currentPageNum:" + iCoerceIn + ", currentPageScale:" + factoryScale);
        handWriteEntity.setCurrentPageNum(iCoerceIn);
        ContentValues contentValues = new ContentValues();
        contentValues.put("current_page_num", Integer.valueOf(iCoerceIn));
        contentValues.put(Notes.Note.CURRENT_PAGE_SCALE, Float.valueOf(factoryScale));
        MultiHandWriteDataUtils.updateValues(contentValues, handWriteEntity.id);
    }
}
