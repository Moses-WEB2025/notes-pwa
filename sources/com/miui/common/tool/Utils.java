package com.miui.common.tool;

import android.app.Activity;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.IBinder;
import android.os.Parcel;
import android.provider.Settings;
import android.support.v4.media.session.PlaybackStateCompat;
import android.text.TextUtils;
import android.view.View;
import android.view.accessibility.AccessibilityManager;
import android.view.inputmethod.InputMethodManager;
import androidx.constraintlayout.core.motion.utils.TypedValues;
import ch.qos.logback.core.CoreConstants;
import ch.qos.logback.core.rolling.helper.IntegerTokenConverter;
import com.google.android.material.timepicker.TimeModel;
import com.google.gson.Gson;
import com.market.sdk.reflect.ReflectUtilsForMiui;
import com.miui.common.app.BaseNoteApp;
import com.miui.common.base.BaseAlarmAlertActivity;
import com.miui.doodle.document.Layer;
import com.miui.duokantext.Encoder;
import com.miui.duokantext.EncoderFactory;
import com.miui.notes.R;
import com.miui.notes.component.datafolder.FolderItemEntity;
import com.miui.notes.model.FolderModel;
import com.miui.notes.model.MindEntity;
import com.miui.notes.tool.TempFileManager;
import com.miui.perm.autostart.AutoStartManager;
import com.miui.richeditor.schema.HtmlParser;
import com.miui.todo.data.Todo;
import com.xiaomi.mirror.synergy.CallMethod;
import com.xiaomi.onetrack.util.ab;
import com.xiaomi.verificationsdk.internal.Constants;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.net.URLDecoder;
import java.text.Collator;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;
import java.util.Locale;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.io.CloseableKt;
import kotlin.jvm.JvmStatic;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.ArrayIteratorKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.StringCompanionObject;
import kotlin.text.Regex;
import kotlin.text.StringsKt;
import miuix.core.util.SystemProperties;
import net.lingala.zip4j.util.InternalZipConstants;
import org.apache.commons.lang3.StringUtils;
import org.ccil.cowan.tagsoup.XMLWriter;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/* JADX INFO: compiled from: Utils.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000\u0096\u0001\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\r\n\u0002\u0010\t\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u001e\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\t\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\f\n\u0002\u0010!\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b+\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\f\bÆ\u0002\u0018\u00002\u00020\u0001B\t\b\u0002¢\u0006\u0004\b\u0002\u0010\u0003J\u0018\u0010\u001e\u001a\u00020\u00052\u0006\u0010\u001f\u001a\u00020\b2\u0006\u0010 \u001a\u00020\bH\u0007J\u0016\u0010!\u001a\u00020\u00052\u0006\u0010\"\u001a\u00020\u00052\u0006\u0010\u001f\u001a\u00020\bJ\u0010\u0010\u001e\u001a\u00020\u00052\u0006\u0010\u001f\u001a\u00020\bH\u0007J\u0012\u0010#\u001a\u00020\u00182\b\u0010$\u001a\u0004\u0018\u00010\u0005H\u0007J \u0010-\u001a\u00020\u00052\u0006\u0010.\u001a\u00020\b2\u0006\u0010/\u001a\u00020\b2\u0006\u00100\u001a\u00020\bH\u0007J\u0018\u00101\u001a\u00020\u00052\u0006\u0010.\u001a\u00020\b2\u0006\u0010/\u001a\u00020\bH\u0007J\u001a\u00104\u001a\u00020\b2\u0006\u00105\u001a\u00020\u00052\n\u00106\u001a\u0006\u0012\u0002\b\u000307J\u001e\u00108\u001a\u0002092\u0006\u0010:\u001a\u00020;2\u0006\u0010<\u001a\u00020\u00052\u0006\u0010=\u001a\u00020\u0005J\u0018\u0010>\u001a\u0002092\u0006\u0010?\u001a\u00020@2\u0006\u0010A\u001a\u00020\u0018H\u0007J\u0010\u0010>\u001a\u0002092\u0006\u0010?\u001a\u00020@H\u0007J\u0010\u0010B\u001a\u0002092\u0006\u0010?\u001a\u00020@H\u0007J\u0010\u0010C\u001a\u0002092\u0006\u0010D\u001a\u00020@H\u0007J\u0010\u0010E\u001a\u0002092\u0006\u0010F\u001a\u00020GH\u0007J\u0012\u0010E\u001a\u0002092\b\u0010?\u001a\u0004\u0018\u00010@H\u0007J\b\u0010H\u001a\u000209H\u0007J\u0010\u0010K\u001a\u00020\b2\u0006\u0010L\u001a\u00020\u0005H\u0007J\u0012\u0010O\u001a\u0004\u0018\u00010\u00052\u0006\u0010:\u001a\u00020;H\u0007J\u0012\u0010P\u001a\u0004\u0018\u00010Q2\b\u0010R\u001a\u0004\u0018\u00010SJ\b\u0010T\u001a\u00020UH\u0007J\u001c\u0010V\u001a\u0004\u0018\u00010\u00052\u0006\u0010:\u001a\u00020;2\b\u0010L\u001a\u0004\u0018\u00010\u0005H\u0007J\u0010\u0010W\u001a\u0004\u0018\u00010\u00052\u0006\u0010X\u001a\u00020YJ\u0012\u0010Z\u001a\u00020\u00182\b\u0010:\u001a\u0004\u0018\u00010;H\u0007J\u0014\u0010[\u001a\u0004\u0018\u00010\\2\b\u0010:\u001a\u0004\u0018\u00010;H\u0007J&\u0010]\u001a\u0004\u0018\u00010\\2\b\u0010:\u001a\u0004\u0018\u00010;2\u0006\u0010^\u001a\u00020\u00052\b\u0010_\u001a\u0004\u0018\u00010\u0005H\u0002J\u0012\u0010`\u001a\u0004\u0018\u00010\\2\b\u0010:\u001a\u0004\u0018\u00010;J\u0012\u0010a\u001a\u0004\u0018\u00010\\2\b\u0010:\u001a\u0004\u0018\u00010;J\u000e\u0010b\u001a\u0002092\u0006\u0010?\u001a\u00020@J\u000e\u0010c\u001a\u00020\b2\u0006\u0010d\u001a\u00020\u0005J\u000e\u0010e\u001a\u00020\u00052\u0006\u0010f\u001a\u00020\bJ\u0014\u0010g\u001a\u0002092\f\u0010h\u001a\b\u0012\u0004\u0012\u00020j0iJ\u0014\u0010k\u001a\u0002092\f\u0010h\u001a\b\u0012\u0004\u0012\u00020j0iJ\u0016\u0010l\u001a\u0002092\u000e\u0010m\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010n0iJ\u0012\u0010o\u001a\u00020\u00052\b\u0010p\u001a\u0004\u0018\u00010\u0005H\u0007J\u0012\u0010u\u001a\u0002092\b\u0010F\u001a\u0004\u0018\u00010GH\u0007J\u0010\u0010v\u001a\u0002092\b\u0010F\u001a\u0004\u0018\u00010GJ\u0012\u0010{\u001a\u00020\u00182\b\u0010:\u001a\u0004\u0018\u00010;H\u0007J\u001b\u0010\u0087\u0001\u001a\u00020\u00182\u0007\u0010\u0088\u0001\u001a\u00020\b2\u0007\u0010\u0089\u0001\u001a\u00020\u0016H\u0007J\t\u0010\u008a\u0001\u001a\u000209H\u0007J!\u0010\u008b\u0001\u001a\u0004\u0018\u00010\u00052\t\u0010\u008c\u0001\u001a\u0004\u0018\u00010\u00052\t\u0010\u008d\u0001\u001a\u0004\u0018\u00010\u0005H\u0007J\u0013\u0010\u008e\u0001\u001a\u00020\u00182\b\u0010:\u001a\u0004\u0018\u00010;H\u0007J\u0014\u0010\u0094\u0001\u001a\u0004\u0018\u00010\u00052\u0007\u0010\u0095\u0001\u001a\u00020\u0016H\u0007J\u0013\u0010\u0098\u0001\u001a\u00020\u00182\b\u0010\u0099\u0001\u001a\u00030\u009a\u0001H\u0007J\u001d\u0010\u009b\u0001\u001a\u0002092\u0006\u0010:\u001a\u00020;2\n\u0010\u009c\u0001\u001a\u0005\u0018\u00010\u009d\u0001H\u0007J\u0014\u0010\u009e\u0001\u001a\u00020\u00052\t\u0010\u009f\u0001\u001a\u0004\u0018\u00010\u0005H\u0007J\u0014\u0010¤\u0001\u001a\u00020\b2\t\u0010¥\u0001\u001a\u0004\u0018\u00010\u0005H\u0007J\u001b\u0010¦\u0001\u001a\u0002092\u0007\u0010§\u0001\u001a\u00020\u00052\t\u0010¨\u0001\u001a\u0004\u0018\u00010\u0005R\u000e\u0010\u0004\u001a\u00020\u0005X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0005X\u0082T¢\u0006\u0002\n\u0000R\u0011\u0010\u0007\u001a\u00020\b¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\nR\u000e\u0010\u000b\u001a\u00020\u0005X\u0082T¢\u0006\u0002\n\u0000R\u000e\u0010\f\u001a\u00020\u0005X\u0082T¢\u0006\u0002\n\u0000R\u000e\u0010\r\u001a\u00020\u0005X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\u000e\u001a\u00020\u0005X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\u000f\u001a\u00020\u0005X\u0082T¢\u0006\u0002\n\u0000R\u000e\u0010\u0010\u001a\u00020\bX\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\u0011\u001a\u00020\bX\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\u0012\u001a\u00020\bX\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\u0013\u001a\u00020\bX\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\u0014\u001a\u00020\u0005X\u0082T¢\u0006\u0002\n\u0000R\u000e\u0010\u0015\u001a\u00020\u0016X\u0082\u0004¢\u0006\u0002\n\u0000R\u0012\u0010\u0017\u001a\u00020\u00188\u0006@\u0006X\u0087\u000e¢\u0006\u0002\n\u0000R\u0012\u0010\u0019\u001a\u00020\u00188\u0006@\u0006X\u0087\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u001a\u001a\u00020\u0005X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\u001b\u001a\u00020\u0005X\u0082T¢\u0006\u0002\n\u0000R\u000e\u0010\u001c\u001a\u00020\u0005X\u0082T¢\u0006\u0002\n\u0000R\u000e\u0010\u001d\u001a\u00020\bX\u0082T¢\u0006\u0002\n\u0000R\u0011\u0010%\u001a\u00020\u00058F¢\u0006\u0006\u001a\u0004\b&\u0010'R\u001a\u0010(\u001a\u00020\u00188FX\u0087\u0004¢\u0006\f\u0012\u0004\b)\u0010\u0003\u001a\u0004\b(\u0010*R\u001a\u0010+\u001a\u00020\u00188FX\u0087\u0004¢\u0006\f\u0012\u0004\b,\u0010\u0003\u001a\u0004\b+\u0010*R\u001a\u00102\u001a\u00020\u00188FX\u0087\u0004¢\u0006\f\u0012\u0004\b3\u0010\u0003\u001a\u0004\b2\u0010*R\u001a\u0010I\u001a\u00020\b8FX\u0087\u0004¢\u0006\f\u0012\u0004\bJ\u0010\u0003\u001a\u0004\bK\u0010\nR\u0013\u0010M\u001a\u0004\u0018\u00010\u00058F¢\u0006\u0006\u001a\u0004\bN\u0010'R\u001a\u0010q\u001a\u00020\bX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\br\u0010\n\"\u0004\bs\u0010tR\u001a\u0010w\u001a\u00020\u00188FX\u0087\u0004¢\u0006\f\u0012\u0004\bx\u0010\u0003\u001a\u0004\bw\u0010*R\u001a\u0010y\u001a\u00020\u00188FX\u0087\u0004¢\u0006\f\u0012\u0004\bz\u0010\u0003\u001a\u0004\by\u0010*R\u001a\u0010|\u001a\u00020\u00188FX\u0087\u0004¢\u0006\f\u0012\u0004\b}\u0010\u0003\u001a\u0004\b|\u0010*R\u001d\u0010~\u001a\u00020\u0016X\u0086\u000e¢\u0006\u0011\n\u0000\u001a\u0005\b\u007f\u0010\u0080\u0001\"\u0006\b\u0081\u0001\u0010\u0082\u0001R\u000f\u0010\u0083\u0001\u001a\u00020\bX\u0082\u000e¢\u0006\u0002\n\u0000R\"\u0010\u0085\u0001\u001a\u00020\u00162\u0007\u0010\u0084\u0001\u001a\u00020\u0016@BX\u0086\u000e¢\u0006\n\n\u0000\u001a\u0006\b\u0086\u0001\u0010\u0080\u0001R\u0013\u0010\u008f\u0001\u001a\u00020\u00188F¢\u0006\u0007\u001a\u0005\b\u008f\u0001\u0010*R\u001d\u0010\u0090\u0001\u001a\u00020\u00188FX\u0087\u0004¢\u0006\u000e\u0012\u0005\b\u0091\u0001\u0010\u0003\u001a\u0005\b\u0090\u0001\u0010*R\u001d\u0010\u0092\u0001\u001a\u00020\u00188FX\u0087\u0004¢\u0006\u000e\u0012\u0005\b\u0093\u0001\u0010\u0003\u001a\u0005\b\u0092\u0001\u0010*R\u0013\u0010\u0096\u0001\u001a\u00020\b8F¢\u0006\u0007\u001a\u0005\b\u0097\u0001\u0010\nR\u001d\u0010 \u0001\u001a\u00020\u00188FX\u0087\u0004¢\u0006\u000e\u0012\u0005\b¡\u0001\u0010\u0003\u001a\u0005\b \u0001\u0010*R\u001d\u0010¢\u0001\u001a\u00020\u00188FX\u0087\u0004¢\u0006\u000e\u0012\u0005\b£\u0001\u0010\u0003\u001a\u0005\b¢\u0001\u0010*¨\u0006©\u0001"}, d2 = {"Lcom/miui/common/tool/Utils;", "", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "()V", "JPG_SUFFIX", "", "TAG", "TURN_SCREEN_ON_FLAGS", "", "getTURN_SCREEN_ON_FLAGS", "()I", "ACTION_GALLERY_ALBUM", "GALLERY_PKG_NAME", "SEND_TYPE_IMAGE_JPEG", "SEND_TYPE_TEXT", "CAMERA_PKG_NAME", "SHORT_ANIM_DURATION_300", "SHORT_ANIM_DURATION_280", "SHORT_ANIM_DURATION_200", "SHORT_ANIM_DURATION_150", "JPG_DIR_NAME", "MAX_SIZE", "", "isDoodleOnWin", "", "isCtaOnWin", "UTF8", "DEFAULT_FORMAT_PATTERN", "RECORD_PACKAGE_NAME", "recordVersion", "obtainLocalInteger", "number", "quantityId", "obtainLocalNumber", "format", "isInThisCountry", "country", "region", "getRegion", "()Ljava/lang/String;", "isUseChinese", "isUseChinese$annotations", "()Z", "isSupport4micMode", "isSupport4micMode$annotations", "getAllSyncItemTerms", "noteCount", "folderCount", "todoCount", "getAllItemTerms", "isRTL", "isRTL$annotations", "getResId", "variableName", "c", "Ljava/lang/Class;", "copyFilesFromAssets", "", CoreConstants.CONTEXT_SCOPE_VALUE, "Landroid/content/Context;", "assetsPath", "savePath", "showSoftInput", "view", "Landroid/view/View;", Constants.FORCE, "showSoftInputWithFocus", "showKeyboardWithRestartInput", "targetView", "hideSoftInput", "activity", "Landroid/app/Activity;", "checkAndSwitchAutoStart", "packageCode", "getPackageCode$annotations", "getPackageCode", "packageName", "packageVersionName", "getPackageVersionName", "getDeviceName", "getPrimaryClip", "Landroid/content/ClipData;", "clipboardManager", "Landroid/content/ClipboardManager;", "newDiscardPolicySingleThreadExecutor", "Ljava/util/concurrent/ExecutorService;", "getApplicationLabel", "parseUpperFolder", CallMethod.ARG_URI, "Landroid/net/Uri;", "isInCommunication", "obtainJpgFile", "Ljava/io/File;", "obtainJpgFilePath", "prefix", "suffix", "obtainCameraFileDir", "obtainDownloadFileDir", "saveViewSnapshotToFileForDebug", "strToInt", "str", "integerToStr", IntegerTokenConverter.CONVERTER_KEY, "sortFolderWithName", "folderModels", "", "Lcom/miui/notes/model/FolderModel;", "sortFolder", "sortFolderItem", Layer.KEY_ITEMS, "Lcom/miui/notes/component/datafolder/FolderItemEntity;", "getMindContent", "mindContent", "SNAPSHOT_INTERVAL", "getSNAPSHOT_INTERVAL", "setSNAPSHOT_INTERVAL", "(I)V", "notifyTakeSnapshotQs", "notifyRemoveSnapshotQs", "isAboveSDK33", "isAboveSDK33$annotations", "isAboveSDK34", "isAboveSDK34$annotations", "isAccessibilityEnable", "isInvisibleMode", "isInvisibleMode$annotations", "lastClickTime", "getLastClickTime", "()J", "setLastClickTime", "(J)V", "lastButtonId", "value", "performActionClickTime", "getPerformActionClickTime", "isFastClick", "buttonId", "intervals", "updatePerformActionClickTime", "decodeUrl", "url", XMLWriter.ENCODING, "isExternalKeyboardEnable", "isBaiduMiInput", "isSupportSuperClipboard", "isSupportSuperClipboard$annotations", "isSupportLyra", "isSupportLyra$annotations", "formatTime", "time", "webViewVersion", "getWebViewVersion", "bundleIsSmall", "bundle", "Landroid/os/Bundle;", "fixInputMethodMemLeak", "windowToken", "Landroid/os/IBinder;", "trimStringEnd", TypedValues.Custom.S_STRING, "isRecordIsInstall", "isRecordIsInstall$annotations", "isNeedUpdateRecord", "isNeedUpdateRecord$annotations", "getStringSize", HtmlParser.InputElement.TAG, "saveJsonToFile", "fileName", "jsonData", "app_PhoneGlobalRelease"}, k = 1, mv = {2, 2, 0}, xi = 48)
public final class Utils {
    private static final String ACTION_GALLERY_ALBUM = "com.miui.gallery.action.VIEW_ALBUM";
    private static final String CAMERA_PKG_NAME = "com.android.camera";
    private static final String DEFAULT_FORMAT_PATTERN = "yyyy-MM-dd HH:mm:ss";
    private static final String GALLERY_PKG_NAME = "com.miui.gallery";
    private static final String JPG_DIR_NAME = "Notes";
    public static final String JPG_SUFFIX = ".jpg";
    private static final String RECORD_PACKAGE_NAME = "com.android.soundrecorder";
    public static final String SEND_TYPE_IMAGE_JPEG = "image/jpeg";
    public static final String SEND_TYPE_TEXT = "text/plain";
    public static final int SHORT_ANIM_DURATION_150 = 150;
    public static final int SHORT_ANIM_DURATION_200 = 200;
    public static final int SHORT_ANIM_DURATION_280 = 280;
    public static final int SHORT_ANIM_DURATION_300 = 300;
    private static final String TAG = "Utils";
    public static final String UTF8 = "UTF-8";
    public static boolean isCtaOnWin = false;
    public static boolean isDoodleOnWin = false;
    private static int lastButtonId = 0;
    private static long lastClickTime = 0;
    private static long performActionClickTime = 0;
    private static final int recordVersion = 601150;
    public static final Utils INSTANCE = new Utils();
    private static final int TURN_SCREEN_ON_FLAGS = BaseAlarmAlertActivity.TURN_SCREEN_ON_FLAGS;
    private static final long MAX_SIZE = PlaybackStateCompat.ACTION_SET_REPEAT_MODE;
    private static int SNAPSHOT_INTERVAL = 1000;

    @JvmStatic
    public static /* synthetic */ void getPackageCode$annotations() {
    }

    @JvmStatic
    public static /* synthetic */ void isAboveSDK33$annotations() {
    }

    @JvmStatic
    public static /* synthetic */ void isAboveSDK34$annotations() {
    }

    @JvmStatic
    public static final boolean isInCommunication(Context context) {
        return false;
    }

    @JvmStatic
    public static /* synthetic */ void isInvisibleMode$annotations() {
    }

    @JvmStatic
    public static /* synthetic */ void isNeedUpdateRecord$annotations() {
    }

    @JvmStatic
    public static /* synthetic */ void isRTL$annotations() {
    }

    @JvmStatic
    public static /* synthetic */ void isRecordIsInstall$annotations() {
    }

    @JvmStatic
    public static /* synthetic */ void isSupport4micMode$annotations() {
    }

    @JvmStatic
    public static /* synthetic */ void isSupportLyra$annotations() {
    }

    @JvmStatic
    public static /* synthetic */ void isSupportSuperClipboard$annotations() {
    }

    @JvmStatic
    public static /* synthetic */ void isUseChinese$annotations() {
    }

    private Utils() {
    }

    public final int getTURN_SCREEN_ON_FLAGS() {
        return TURN_SCREEN_ON_FLAGS;
    }

    @JvmStatic
    public static final String obtainLocalInteger(int number, int quantityId) {
        return INSTANCE.obtainLocalNumber(BaseNoteApp.INSTANCE.getInstance().getResources().getQuantityText(quantityId, number).toString(), number);
    }

    public final String obtainLocalNumber(String format, int number) {
        Intrinsics.checkNotNullParameter(format, "format");
        StringCompanionObject stringCompanionObject = StringCompanionObject.INSTANCE;
        String str = String.format(Locale.getDefault(), format, Arrays.copyOf(new Object[]{Integer.valueOf(number)}, 1));
        Intrinsics.checkNotNullExpressionValue(str, "format(...)");
        return str;
    }

    @JvmStatic
    public static final String obtainLocalInteger(int number) {
        return INSTANCE.obtainLocalNumber(TimeModel.NUMBER_FORMAT, number);
    }

    @JvmStatic
    public static final boolean isInThisCountry(String country) {
        if (TextUtils.isEmpty(country)) {
            return false;
        }
        return Intrinsics.areEqual(INSTANCE.getRegion(), country);
    }

    public final String getRegion() {
        String country = SystemProperties.get("ro.miui.region");
        if (TextUtils.isEmpty(country)) {
            country = SystemProperties.get("ro.product.locale.region");
            if (TextUtils.isEmpty(country)) {
                country = SystemProperties.get("persist.sys.country");
                if (TextUtils.isEmpty(country)) {
                    country = Locale.getDefault().getCountry();
                }
            }
        }
        Intrinsics.checkNotNull(country);
        return country;
    }

    public static final boolean isUseChinese() {
        String language = BaseNoteApp.INSTANCE.getInstance().getResources().getConfiguration().getLocales().get(0).getLanguage();
        Intrinsics.checkNotNull(language);
        return StringsKt.endsWith$default(language, "zh", false, 2, (Object) null);
    }

    public static final boolean isSupport4micMode() {
        return SystemProperties.getBoolean("ro.vendor.audio.4mic.support", false);
    }

    @JvmStatic
    public static final String getAllSyncItemTerms(int noteCount, int folderCount, int todoCount) {
        Resources resources = BaseNoteApp.INSTANCE.getInstance().getResources();
        String quantityString = resources.getQuantityString(R.plurals.note_terms, noteCount, Integer.valueOf(noteCount));
        Intrinsics.checkNotNullExpressionValue(quantityString, "getQuantityString(...)");
        Intrinsics.checkNotNullExpressionValue(resources.getQuantityString(R.plurals.folder_terms, folderCount, Integer.valueOf(folderCount)), "getQuantityString(...)");
        String quantityString2 = resources.getQuantityString(R.plurals.todo_terms, todoCount, Integer.valueOf(todoCount));
        Intrinsics.checkNotNullExpressionValue(quantityString2, "getQuantityString(...)");
        if (todoCount <= 0) {
            String quantityString3 = resources.getQuantityString(R.plurals.note_terms, noteCount, Integer.valueOf(noteCount));
            Intrinsics.checkNotNullExpressionValue(quantityString3, "getQuantityString(...)");
            return quantityString3;
        }
        if (noteCount > 0) {
            String string = resources.getString(R.string.sync_note_todo, quantityString, quantityString2);
            Intrinsics.checkNotNullExpressionValue(string, "getString(...)");
            return string;
        }
        String quantityString4 = resources.getQuantityString(R.plurals.todo_terms, todoCount, Integer.valueOf(todoCount));
        Intrinsics.checkNotNullExpressionValue(quantityString4, "getQuantityString(...)");
        return quantityString4;
    }

    @JvmStatic
    public static final String getAllItemTerms(int noteCount, int folderCount) {
        Resources resources = BaseNoteApp.INSTANCE.getInstance().getResources();
        if (folderCount <= 0) {
            String quantityString = resources.getQuantityString(R.plurals.note_terms, noteCount, Integer.valueOf(noteCount));
            Intrinsics.checkNotNullExpressionValue(quantityString, "getQuantityString(...)");
            return quantityString;
        }
        if (noteCount > 0) {
            String quantityString2 = resources.getQuantityString(R.plurals.note_terms, noteCount, Integer.valueOf(noteCount));
            Intrinsics.checkNotNullExpressionValue(quantityString2, "getQuantityString(...)");
            String quantityString3 = resources.getQuantityString(R.plurals.folder_terms, folderCount, Integer.valueOf(folderCount));
            Intrinsics.checkNotNullExpressionValue(quantityString3, "getQuantityString(...)");
            String string = resources.getString(R.string.all_terms, quantityString2, quantityString3);
            Intrinsics.checkNotNullExpressionValue(string, "getString(...)");
            return string;
        }
        String quantityString4 = resources.getQuantityString(R.plurals.folder_terms, folderCount, Integer.valueOf(folderCount));
        Intrinsics.checkNotNullExpressionValue(quantityString4, "getQuantityString(...)");
        return quantityString4;
    }

    public static final boolean isRTL() {
        return TextUtils.getLayoutDirectionFromLocale(Locale.getDefault()) == 1;
    }

    public final int getResId(String variableName, Class<?> c) {
        Intrinsics.checkNotNullParameter(variableName, "variableName");
        Intrinsics.checkNotNullParameter(c, "c");
        try {
            Field declaredField = c.getDeclaredField(variableName);
            return declaredField.getInt(declaredField);
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }

    /* JADX WARN: Code duplicated, block: B:66:0x00d4 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:70:0x00de A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:84:? A[SYNTHETIC] */
    public final void copyFilesFromAssets(Context context, String assetsPath, String savePath) throws Throwable {
        InputStream inputStream;
        InputStream inputStreamOpen;
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(assetsPath, "assetsPath");
        Intrinsics.checkNotNullParameter(savePath, "savePath");
        FileOutputStream fileOutputStream = null;
        try {
            try {
                String[] list = context.getAssets().list(assetsPath);
                Intrinsics.checkNotNull(list);
                if (list.length > 0) {
                    new File(savePath).mkdirs();
                    Iterator it = ArrayIteratorKt.iterator(list);
                    while (it.hasNext()) {
                        String str = (String) it.next();
                        copyFilesFromAssets(context, assetsPath + File.separator + str, savePath + File.separator + str);
                    }
                    inputStreamOpen = null;
                } else {
                    inputStreamOpen = context.getAssets().open(assetsPath);
                    try {
                        FileOutputStream fileOutputStream2 = new FileOutputStream(new File(savePath));
                        try {
                            byte[] bArr = new byte[1024];
                            while (true) {
                                int i = inputStreamOpen.read(bArr);
                                if (i == -1) {
                                    break;
                                } else {
                                    fileOutputStream2.write(bArr, 0, i);
                                }
                            }
                            fileOutputStream2.flush();
                            fileOutputStream = fileOutputStream2;
                        } catch (Exception e) {
                            fileOutputStream = fileOutputStream2;
                            inputStream = inputStreamOpen;
                            e = e;
                            try {
                                e.printStackTrace();
                                if (fileOutputStream != null) {
                                    try {
                                        fileOutputStream.close();
                                    } catch (IOException e2) {
                                        e2.printStackTrace();
                                    }
                                }
                                if (inputStream == null) {
                                    return;
                                } else {
                                    inputStream.close();
                                }
                            } catch (Throwable th) {
                                th = th;
                                if (fileOutputStream != null) {
                                    try {
                                        fileOutputStream.close();
                                    } catch (IOException e3) {
                                        e3.printStackTrace();
                                    }
                                }
                                if (inputStream != null) {
                                    throw th;
                                }
                                try {
                                    inputStream.close();
                                    throw th;
                                } catch (IOException e4) {
                                    e4.printStackTrace();
                                    throw th;
                                }
                            }
                        } catch (Throwable th2) {
                            fileOutputStream = fileOutputStream2;
                            inputStream = inputStreamOpen;
                            th = th2;
                            if (fileOutputStream != null) {
                                fileOutputStream.close();
                            }
                            if (inputStream != null) {
                                throw th;
                            }
                            inputStream.close();
                            throw th;
                        }
                    } catch (Exception e5) {
                        inputStream = inputStreamOpen;
                        e = e5;
                    } catch (Throwable th3) {
                        inputStream = inputStreamOpen;
                        th = th3;
                    }
                }
                if (fileOutputStream != null) {
                    try {
                        fileOutputStream.close();
                    } catch (IOException e6) {
                        e6.printStackTrace();
                    }
                }
                if (inputStreamOpen != null) {
                    inputStreamOpen.close();
                }
            } catch (Exception e7) {
                e = e7;
                inputStream = null;
            } catch (Throwable th4) {
                th = th4;
                inputStream = null;
            }
        } catch (IOException e8) {
            e8.printStackTrace();
        }
    }

    @JvmStatic
    public static final void showSoftInput(View view, boolean force) {
        Intrinsics.checkNotNullParameter(view, "view");
        Logger.INSTANCE.i(TAG, "showSoftInput " + view);
        InputMethodManager inputMethodManager = (InputMethodManager) view.getContext().getSystemService("input_method");
        if (inputMethodManager != null && (!inputMethodManager.isActive(view) || force)) {
            Logger.INSTANCE.i(TAG, "showSoftInput " + inputMethodManager.showSoftInput(view, 0));
        }
        view.requestFocus();
    }

    @JvmStatic
    public static final void showSoftInput(View view) {
        Intrinsics.checkNotNullParameter(view, "view");
        Logger.INSTANCE.d(TAG, "showSoftInput");
        InputMethodManager inputMethodManager = (InputMethodManager) view.getContext().getSystemService("input_method");
        if (inputMethodManager != null) {
            inputMethodManager.showSoftInput(view, 0);
        }
    }

    @JvmStatic
    public static final void showSoftInputWithFocus(View view) {
        Intrinsics.checkNotNullParameter(view, "view");
        InputMethodManager inputMethodManager = (InputMethodManager) view.getContext().getSystemService("input_method");
        if (inputMethodManager != null) {
            view.requestFocus();
            inputMethodManager.showSoftInput(view, 0);
        }
    }

    @JvmStatic
    public static final void showKeyboardWithRestartInput(View targetView) {
        Intrinsics.checkNotNullParameter(targetView, "targetView");
        Logger.INSTANCE.d(TAG, "showKeyboardWithRestartInput");
        InputMethodManager inputMethodManager = (InputMethodManager) targetView.getContext().getSystemService("input_method");
        if (targetView.requestFocus()) {
            if (inputMethodManager != null) {
                inputMethodManager.restartInput(targetView);
            }
            if (inputMethodManager != null) {
                inputMethodManager.showSoftInput(targetView, 1);
            }
        }
    }

    @JvmStatic
    public static final void hideSoftInput(Activity activity) {
        Intrinsics.checkNotNullParameter(activity, "activity");
        View currentFocus = activity.getCurrentFocus();
        if (currentFocus == null) {
            currentFocus = activity.getWindow().getDecorView();
        }
        hideSoftInput(currentFocus);
    }

    @JvmStatic
    public static final void hideSoftInput(View view) {
        if (view == null) {
            return;
        }
        Logger.INSTANCE.d(TAG, "hideSoftInput");
        try {
            InputMethodManager inputMethodManager = (InputMethodManager) view.getContext().getSystemService("input_method");
            if (inputMethodManager != null) {
                inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
            }
        } catch (IllegalStateException e) {
            Logger.INSTANCE.i(TAG, "Failed to hide keyboard, e:" + e);
        }
    }

    @JvmStatic
    public static final void checkAndSwitchAutoStart() {
        Logger.INSTANCE.i(TAG, "checkAndSwitchAutoStart: " + AutoStartManager.checkAutoStart(BaseNoteApp.INSTANCE.getInstance()));
        if (AutoStartManager.checkAutoStart(BaseNoteApp.INSTANCE.getInstance()) != 0) {
            AutoStartManager.switchAutoStart(true, BaseNoteApp.INSTANCE.getInstance(), new AutoStartManager.AutoStartManagerObserver() { // from class: com.miui.common.tool.Utils$$ExternalSyntheticLambda0
                @Override // com.miui.perm.autostart.AutoStartManager.AutoStartManagerObserver
                public final void onAutoStartSwitched(int i) {
                    Utils.checkAndSwitchAutoStart$lambda$1(i);
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void checkAndSwitchAutoStart$lambda$1(int i) {
        Logger.INSTANCE.i(TAG, "onAutoStartSwitched result:" + i);
    }

    public static final int getPackageCode() {
        try {
            return BaseNoteApp.INSTANCE.getInstance().getPackageManager().getPackageInfo(BaseNoteApp.INSTANCE.getInstance().getPackageName(), 0).versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            return 0;
        }
    }

    @JvmStatic
    public static final int getPackageCode(String packageName) {
        Intrinsics.checkNotNullParameter(packageName, "packageName");
        try {
            return BaseNoteApp.INSTANCE.getInstance().getPackageManager().getPackageInfo(packageName, 0).versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            return 0;
        }
    }

    public final String getPackageVersionName() {
        try {
            return BaseNoteApp.INSTANCE.getInstance().getPackageManager().getPackageInfo(BaseNoteApp.INSTANCE.getInstance().getPackageName(), 0).versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }

    @JvmStatic
    public static final String getDeviceName(Context context) {
        Intrinsics.checkNotNullParameter(context, "context");
        return Settings.Global.getString(context.getContentResolver(), "device_name");
    }

    public final ClipData getPrimaryClip(ClipboardManager clipboardManager) {
        if (clipboardManager == null) {
            return null;
        }
        try {
            return (ClipData) clipboardManager.getClass().getMethod("getUserPrimaryClip", new Class[0]).invoke(clipboardManager, new Object[0]);
        } catch (Exception unused) {
            return clipboardManager.getPrimaryClip();
        }
    }

    @JvmStatic
    public static final ExecutorService newDiscardPolicySingleThreadExecutor() {
        return new ThreadPoolExecutor(1, 1, 0L, TimeUnit.MILLISECONDS, new LinkedBlockingQueue(), new ThreadPoolExecutor.DiscardPolicy());
    }

    @JvmStatic
    public static final String getApplicationLabel(Context context, String packageName) {
        PackageManager packageManager;
        Intrinsics.checkNotNullParameter(context, "context");
        if (packageName == null || (packageManager = context.getPackageManager()) == null) {
            return null;
        }
        try {
            ApplicationInfo applicationInfo = packageManager.getApplicationInfo(packageName, 0);
            Intrinsics.checkNotNullExpressionValue(applicationInfo, "getApplicationInfo(...)");
            CharSequence applicationLabel = packageManager.getApplicationLabel(applicationInfo);
            Intrinsics.checkNotNullExpressionValue(applicationLabel, "getApplicationLabel(...)");
            return applicationLabel.toString();
        } catch (PackageManager.NameNotFoundException e) {
            Logger.INSTANCE.w(TAG, "Fail to get application info for package: " + packageName + ",with exception:" + e);
            return null;
        }
    }

    public final String parseUpperFolder(Uri uri) {
        List listEmptyList;
        Intrinsics.checkNotNullParameter(uri, "uri");
        String encodedPath = uri.getEncodedPath();
        StringBuilder sb = new StringBuilder();
        if (encodedPath == null) {
            return null;
        }
        List<String> listSplit = new Regex(InternalZipConstants.ZIP_FILE_SEPARATOR).split(encodedPath, 0);
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
        if (strArr.length >= 3) {
            sb.append(strArr[strArr.length - 3]).append(InternalZipConstants.ZIP_FILE_SEPARATOR);
        }
        if (strArr.length < 2) {
            return null;
        }
        sb.append(strArr[strArr.length - 2]);
        return sb.toString();
    }

    @JvmStatic
    public static final File obtainJpgFile(Context context) {
        return INSTANCE.obtainJpgFilePath(context, String.valueOf(System.currentTimeMillis()), JPG_SUFFIX);
    }

    private final File obtainJpgFilePath(Context context, String prefix, String suffix) {
        try {
            return File.createTempFile(prefix, suffix, obtainCameraFileDir(context));
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public final File obtainCameraFileDir(Context context) {
        File externalStoragePublicDirectory = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM);
        if (externalStoragePublicDirectory == null) {
            Logger.INSTANCE.e(TAG, "Fail to getExternalCacheDir.");
            return null;
        }
        File file = new File(externalStoragePublicDirectory, "Notes");
        if (file.exists() || file.mkdirs() || file.exists()) {
            return file;
        }
        Logger.INSTANCE.e(TAG, "Fail to create CameraDir:" + file);
        return null;
    }

    public final File obtainDownloadFileDir(Context context) {
        File externalStoragePublicDirectory = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);
        if (externalStoragePublicDirectory == null) {
            Logger.INSTANCE.e(TAG, "Fail to getExternalCacheDir.");
            return null;
        }
        File file = new File(externalStoragePublicDirectory, "Notes");
        if (file.exists() || file.mkdirs() || file.exists()) {
            return file;
        }
        Logger.INSTANCE.e(TAG, "Fail to create CameraDir:" + file);
        return null;
    }

    /* JADX WARN: Code duplicated, block: B:59:0x00f2 A[Catch: IOException -> 0x00ed, TRY_LEAVE, TryCatch #9 {IOException -> 0x00ed, blocks: (B:55:0x00e9, B:59:0x00f2), top: B:73:0x00e9 }] */
    /* JADX WARN: Code duplicated, block: B:65:0x0100  */
    /* JADX WARN: Code duplicated, block: B:73:0x00e9 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:83:? A[SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:85:? A[RETURN, SYNTHETIC] */
    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r13v20 */
    /* JADX WARN: Type inference failed for: r13v3, types: [java.io.FileOutputStream] */
    /* JADX WARN: Type inference failed for: r13v6 */
    /* JADX WARN: Type inference failed for: r3v4 */
    /* JADX WARN: Type inference failed for: r3v5, types: [java.io.FileInputStream] */
    /* JADX WARN: Type inference failed for: r3v6 */
    /* JADX WARN: Type inference failed for: r4v1 */
    /* JADX WARN: Type inference failed for: r4v7 */
    /* JADX WARN: Type inference failed for: r4v8 */
    public final void saveViewSnapshotToFileForDebug(View view) throws Throwable {
        Throwable th;
        ?? r13;
        File file;
        ?? r4;
        FileOutputStream fileOutputStream;
        FileInputStream fileInputStream;
        Intrinsics.checkNotNullParameter(view, "view");
        System.loadLibrary("duokantext");
        Context context = view.getContext();
        int width = view.getWidth();
        int height = view.getHeight();
        Bitmap bitmapCreateBitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        Intrinsics.checkNotNullExpressionValue(bitmapCreateBitmap, "createBitmap(...)");
        Canvas canvas = new Canvas();
        canvas.setBitmap(bitmapCreateBitmap);
        view.draw(canvas);
        File fileCreateFile = TempFileManager.getInstance().createFile("share", JPG_SUFFIX);
        Encoder encoderCreate = EncoderFactory.getInstance().create(false);
        try {
            encoderCreate.createEncoder(fileCreateFile.getPath(), 100, width, height);
            encoderCreate.startCompress();
            int[] iArr = new int[width * 1024];
            int i = 0;
            while (i < height) {
                int iMin = (int) Math.min(i + 1024, height);
                int i2 = iMin - i;
                bitmapCreateBitmap.getPixels(iArr, 0, width, 0, 0, width, i2);
                encoderCreate.savePixels(iArr, i2);
                i = iMin;
            }
            encoderCreate.finishCompress();
            File fileObtainJpgFile = obtainJpgFile(context);
            if (fileCreateFile != null && fileObtainJpgFile != null) {
                ?? r3 = 0;
                try {
                    try {
                        try {
                            fileInputStream = new FileInputStream(fileCreateFile);
                            try {
                                fileOutputStream = new FileOutputStream(fileObtainJpgFile);
                                try {
                                    byte[] bArr = new byte[1024];
                                    while (true) {
                                        int i3 = fileInputStream.read(bArr);
                                        if (i3 == -1) {
                                            break;
                                        } else {
                                            fileOutputStream.write(bArr, 0, i3);
                                        }
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
                                    String string = fileObtainJpgFile.toString();
                                    Intrinsics.checkNotNullExpressionValue(string, "toString(...)");
                                    MediaScannerConnection.scanFile(context, new String[]{string}, null, new MediaScannerConnection.OnScanCompletedListener() { // from class: com.miui.common.tool.Utils.saveViewSnapshotToFileForDebug.2
                                        @Override // android.media.MediaScannerConnection.OnScanCompletedListener
                                        public void onScanCompleted(String path, Uri uri) {
                                            Logger.INSTANCE.i(Utils.TAG, "Scanned " + path + " completely.");
                                        }
                                    });
                                    if (bitmapCreateBitmap.isRecycled()) {
                                    }
                                    bitmapCreateBitmap.recycle();
                                } catch (IOException e2) {
                                    e = e2;
                                    e.printStackTrace();
                                    if (fileInputStream != null) {
                                        fileInputStream.close();
                                    }
                                    if (fileOutputStream != null) {
                                    }
                                    String string2 = fileObtainJpgFile.toString();
                                    Intrinsics.checkNotNullExpressionValue(string2, "toString(...)");
                                    MediaScannerConnection.scanFile(context, new String[]{string2}, null, new MediaScannerConnection.OnScanCompletedListener() { // from class: com.miui.common.tool.Utils.saveViewSnapshotToFileForDebug.2
                                        @Override // android.media.MediaScannerConnection.OnScanCompletedListener
                                        public void onScanCompleted(String path, Uri uri) {
                                            Logger.INSTANCE.i(Utils.TAG, "Scanned " + path + " completely.");
                                        }
                                    });
                                    if (bitmapCreateBitmap.isRecycled()) {
                                        bitmapCreateBitmap.recycle();
                                    }
                                }
                            } catch (FileNotFoundException e3) {
                                e = e3;
                                fileOutputStream = null;
                            } catch (IOException e4) {
                                e = e4;
                                fileOutputStream = null;
                            } catch (Throwable th2) {
                                th = th2;
                                file = null;
                                r4 = fileInputStream;
                                r3 = r4;
                                r13 = file;
                                if (r3 != 0) {
                                    try {
                                        r3.close();
                                    } catch (IOException e5) {
                                        e5.printStackTrace();
                                        throw th;
                                    }
                                }
                                if (r13 != 0) {
                                    throw th;
                                }
                                r13.close();
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
                        } catch (Throwable th3) {
                            th = th3;
                            r13 = 0;
                            if (r3 != 0) {
                                r3.close();
                            }
                            if (r13 != 0) {
                                throw th;
                            }
                            r13.close();
                            throw th;
                        }
                        fileOutputStream.close();
                    } catch (IOException e8) {
                        e8.printStackTrace();
                    }
                    String string3 = fileObtainJpgFile.toString();
                    Intrinsics.checkNotNullExpressionValue(string3, "toString(...)");
                    MediaScannerConnection.scanFile(context, new String[]{string3}, null, new MediaScannerConnection.OnScanCompletedListener() { // from class: com.miui.common.tool.Utils.saveViewSnapshotToFileForDebug.2
                        @Override // android.media.MediaScannerConnection.OnScanCompletedListener
                        public void onScanCompleted(String path, Uri uri) {
                            Logger.INSTANCE.i(Utils.TAG, "Scanned " + path + " completely.");
                        }
                    });
                } catch (Throwable th4) {
                    th = th4;
                    r4 = width;
                    file = fileCreateFile;
                }
            }
            if (bitmapCreateBitmap.isRecycled()) {
                bitmapCreateBitmap.recycle();
            }
        } catch (IOException unused) {
        }
    }

    public final int strToInt(String str) {
        Intrinsics.checkNotNullParameter(str, "str");
        try {
            return Integer.parseInt(str);
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    public final String integerToStr(int i) {
        try {
            return String.valueOf(i);
        } catch (Exception e) {
            e.printStackTrace();
            return "0";
        }
    }

    public final void sortFolderWithName(List<FolderModel> folderModels) {
        Intrinsics.checkNotNullParameter(folderModels, "folderModels");
        final Collator collator = Collator.getInstance(Locale.CHINA);
        final Function2 function2 = new Function2() { // from class: com.miui.common.tool.Utils$$ExternalSyntheticLambda3
            @Override // kotlin.jvm.functions.Function2
            public final Object invoke(Object obj, Object obj2) {
                return Integer.valueOf(Utils.sortFolderWithName$lambda$4(collator, (FolderModel) obj, (FolderModel) obj2));
            }
        };
        CollectionsKt.sortWith(folderModels, new Comparator() { // from class: com.miui.common.tool.Utils$$ExternalSyntheticLambda4
            @Override // java.util.Comparator
            public final int compare(Object obj, Object obj2) {
                return Utils.sortFolderWithName$lambda$5(function2, obj, obj2);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final int sortFolderWithName$lambda$5(Function2 function2, Object obj, Object obj2) {
        return ((Number) function2.invoke(obj, obj2)).intValue();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final int sortFolderWithName$lambda$4(Collator collator, FolderModel folderModel, FolderModel folderModel2) {
        if (folderModel == null || folderModel2 == null || folderModel.getSubject() == null || folderModel2.getSubject() == null) {
            return 0;
        }
        collator.compare(folderModel.getSubject(), folderModel2.getSubject());
        return 0;
    }

    public final void sortFolder(List<FolderModel> folderModels) {
        Intrinsics.checkNotNullParameter(folderModels, "folderModels");
        final Collator collator = Collator.getInstance(Locale.CHINA);
        final Function2 function2 = new Function2() { // from class: com.miui.common.tool.Utils$$ExternalSyntheticLambda5
            @Override // kotlin.jvm.functions.Function2
            public final Object invoke(Object obj, Object obj2) {
                return Integer.valueOf(Utils.sortFolder$lambda$6(collator, (FolderModel) obj, (FolderModel) obj2));
            }
        };
        CollectionsKt.sortWith(folderModels, new Comparator() { // from class: com.miui.common.tool.Utils$$ExternalSyntheticLambda6
            @Override // java.util.Comparator
            public final int compare(Object obj, Object obj2) {
                return Utils.sortFolder$lambda$7(function2, obj, obj2);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final int sortFolder$lambda$7(Function2 function2, Object obj, Object obj2) {
        return ((Number) function2.invoke(obj, obj2)).intValue();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final int sortFolder$lambda$6(Collator collator, FolderModel folderModel, FolderModel folderModel2) {
        if (folderModel == null || folderModel2 == null) {
            return 0;
        }
        if (folderModel.getStickAt() > 0 && folderModel2.getStickAt() <= 0) {
            return -1;
        }
        if (folderModel.getStickAt() <= 0 && folderModel2.getStickAt() > 0) {
            return 1;
        }
        String subject = folderModel.getSubject();
        if (subject == null) {
            subject = "";
        }
        String subject2 = folderModel2.getSubject();
        return collator.compare(subject, subject2 != null ? subject2 : "");
    }

    public final void sortFolderItem(List<FolderItemEntity> items) {
        Intrinsics.checkNotNullParameter(items, "items");
        final Collator collator = Collator.getInstance(Locale.CHINA);
        final Function2 function2 = new Function2() { // from class: com.miui.common.tool.Utils$$ExternalSyntheticLambda1
            @Override // kotlin.jvm.functions.Function2
            public final Object invoke(Object obj, Object obj2) {
                return Integer.valueOf(Utils.sortFolderItem$lambda$8(collator, (FolderItemEntity) obj, (FolderItemEntity) obj2));
            }
        };
        CollectionsKt.sortWith(items, new Comparator() { // from class: com.miui.common.tool.Utils$$ExternalSyntheticLambda2
            @Override // java.util.Comparator
            public final int compare(Object obj, Object obj2) {
                return Utils.sortFolderItem$lambda$9(function2, obj, obj2);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final int sortFolderItem$lambda$9(Function2 function2, Object obj, Object obj2) {
        return ((Number) function2.invoke(obj, obj2)).intValue();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final int sortFolderItem$lambda$8(Collator collator, FolderItemEntity folderItemEntity, FolderItemEntity folderItemEntity2) {
        if (folderItemEntity == null || folderItemEntity2 == null) {
            return 0;
        }
        if (folderItemEntity.isStick() && !folderItemEntity2.isStick()) {
            return -1;
        }
        if (!folderItemEntity.isStick() && folderItemEntity2.isStick()) {
            return 1;
        }
        String folderName = folderItemEntity.getFolderName();
        if (folderName == null) {
            folderName = "";
        }
        String folderName2 = folderItemEntity2.getFolderName();
        return collator.compare(folderName, folderName2 != null ? folderName2 : "");
    }

    @JvmStatic
    public static final String getMindContent(String mindContent) {
        try {
            if (!TextUtils.isEmpty(mindContent)) {
                Gson gson = new Gson();
                Intrinsics.checkNotNull(mindContent);
                String strSubstring = mindContent.substring(15);
                Intrinsics.checkNotNullExpressionValue(strSubstring, "substring(...)");
                String str = ((MindEntity) gson.fromJson(strSubstring, MindEntity.class)).content;
                String str2 = str;
                if (str2 != null && str2.length() != 0) {
                    JSONArray jSONArray = new JSONObject(str).getJSONObject("data").getJSONArray("children");
                    LinkedList linkedList = new LinkedList();
                    linkedList.offer(jSONArray);
                    while (!linkedList.isEmpty()) {
                        JSONArray jSONArray2 = (JSONArray) linkedList.poll();
                        if (jSONArray2 != null) {
                            int length = jSONArray2.length();
                            for (int i = 0; i < length; i++) {
                                JSONObject jSONObject = jSONArray2.getJSONObject(i);
                                if (jSONObject != null) {
                                    String string = jSONObject.getString(Todo.LABEL);
                                    if (!TextUtils.isEmpty(string)) {
                                        Intrinsics.checkNotNull(string);
                                        return StringsKt.replace$default(StringsKt.replace$default(StringsKt.replace$default(string, "\\t", "", false, 4, (Object) null), "\\r", "", false, 4, (Object) null), "\\n", "", false, 4, (Object) null);
                                    }
                                    linkedList.offer(jSONObject.getJSONArray("children"));
                                }
                            }
                        }
                    }
                }
                return "";
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return "";
    }

    public final int getSNAPSHOT_INTERVAL() {
        return SNAPSHOT_INTERVAL;
    }

    public final void setSNAPSHOT_INTERVAL(int i) {
        SNAPSHOT_INTERVAL = i;
    }

    @JvmStatic
    public static final void notifyTakeSnapshotQs(Activity activity) {
        try {
            Method declaredMethod = Activity.class.getDeclaredMethod("notifyTakeSnapshotQs", new Class[0]);
            declaredMethod.setAccessible(true);
            declaredMethod.invoke(activity, new Object[0]);
        } catch (Exception e) {
            Logger.INSTANCE.d(TAG, "error in notifyTakeSnapshotQs " + e);
        }
    }

    public final void notifyRemoveSnapshotQs(Activity activity) {
        try {
            Method declaredMethod = Activity.class.getDeclaredMethod("notifyRemoveSnapshotQs", new Class[0]);
            declaredMethod.setAccessible(true);
            declaredMethod.invoke(activity, new Object[0]);
        } catch (Exception e) {
            Logger.INSTANCE.d(TAG, "error in notifyRemoveSnapshotQs " + e);
        }
    }

    public static final boolean isAboveSDK33() {
        return Build.VERSION.SDK_INT >= 33;
    }

    public static final boolean isAboveSDK34() {
        return Build.VERSION.SDK_INT >= 34;
    }

    @JvmStatic
    public static final boolean isAccessibilityEnable(Context context) {
        AccessibilityManager accessibilityManager;
        return context != null && (accessibilityManager = (AccessibilityManager) context.getApplicationContext().getSystemService("accessibility")) != null && accessibilityManager.isEnabled() && accessibilityManager.isTouchExplorationEnabled();
    }

    public static final boolean isInvisibleMode() {
        return Settings.Secure.getInt(BaseNoteApp.INSTANCE.getInstance().getContentResolver(), "key_invisible_mode_state", 0) == 1;
    }

    public final long getLastClickTime() {
        return lastClickTime;
    }

    public final void setLastClickTime(long j) {
        lastClickTime = j;
    }

    public final long getPerformActionClickTime() {
        return performActionClickTime;
    }

    @JvmStatic
    public static final boolean isFastClick(int buttonId, long intervals) {
        long jCurrentTimeMillis = System.currentTimeMillis();
        long j = lastClickTime;
        long j2 = jCurrentTimeMillis - j;
        if (j > 0 && j2 < intervals && j2 > 0) {
            Logger.INSTANCE.d(TAG, "isFastClick fast click  " + lastButtonId + " ，" + lastClickTime + "，" + j2 + "，" + jCurrentTimeMillis);
            return true;
        }
        lastClickTime = jCurrentTimeMillis;
        Logger.INSTANCE.d(TAG, "isFastClick click btn " + buttonId + " with ts:" + lastClickTime + StringUtils.SPACE);
        lastButtonId = buttonId;
        return false;
    }

    @JvmStatic
    public static final void updatePerformActionClickTime() {
        performActionClickTime = System.currentTimeMillis();
    }

    @JvmStatic
    public static final String decodeUrl(String url, String encoding) {
        if (url != null) {
            String strDecode = null;
            while (!Intrinsics.areEqual(url, strDecode)) {
                try {
                    url = URLDecoder.decode(url, encoding);
                    strDecode = URLDecoder.decode(url, encoding);
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
            }
        }
        return url;
    }

    @JvmStatic
    public static final boolean isExternalKeyboardEnable(Context context) {
        return (context == null || context.getResources() == null || context.getResources().getConfiguration() == null || context.getResources().getConfiguration().keyboard != 2) ? false : true;
    }

    public final boolean isBaiduMiInput() {
        String string = Settings.Secure.getString(BaseNoteApp.INSTANCE.getInstance().getContentResolver(), "default_input_method");
        if (TextUtils.isEmpty(string)) {
            return false;
        }
        Intrinsics.checkNotNull(string);
        return StringsKt.startsWith$default(string, "com.baidu.input_mi", false, 2, (Object) null);
    }

    public static final boolean isSupportSuperClipboard() {
        return Intrinsics.areEqual("1", SystemProperties.get("persist.sys.support_super_clipboard", "0"));
    }

    public static final boolean isSupportLyra() {
        try {
            ApplicationInfo applicationInfo = BaseNoteApp.INSTANCE.getInstance().getPackageManager().getApplicationInfo("com.xiaomi.mirror", 128);
            Intrinsics.checkNotNullExpressionValue(applicationInfo, "getApplicationInfo(...)");
            return applicationInfo.metaData.getBoolean("com.xiaomi.mirror.lyra", false);
        } catch (Exception unused) {
            return false;
        }
    }

    @JvmStatic
    public static final String formatTime(long time) {
        try {
            return new SimpleDateFormat(DEFAULT_FORMAT_PATTERN).format(Long.valueOf(time));
        } catch (Exception unused) {
            return String.valueOf(time);
        }
    }

    public final int getWebViewVersion() {
        List listEmptyList;
        try {
            PackageInfo packageInfo = BaseNoteApp.INSTANCE.getInstance().getPackageManager().getPackageInfo("com.google.android.webview", 0);
            Logger.INSTANCE.d(TAG, "webView version name: " + packageInfo.versionName + ", versionCode: " + packageInfo.versionCode);
            String str = packageInfo.versionName;
            Intrinsics.checkNotNull(str);
            List<String> listSplit = new Regex(ab.a).split(str, 0);
            if (listSplit.isEmpty()) {
                listEmptyList = CollectionsKt.emptyList();
            } else {
                ListIterator<String> listIterator = listSplit.listIterator(listSplit.size());
                while (listIterator.hasPrevious()) {
                    if (listIterator.previous().length() != 0) {
                        listEmptyList = CollectionsKt.take(listSplit, listIterator.nextIndex() + 1);
                    }
                }
                listEmptyList = CollectionsKt.emptyList();
            }
            String str2 = ((String[]) listEmptyList.toArray(new String[0]))[0];
            Intrinsics.checkNotNull(str2);
            return Integer.parseInt(str2);
        } catch (Throwable th) {
            Logger.INSTANCE.e(TAG, "getWebViewVersion error: " + th);
            th.printStackTrace();
            return Integer.MAX_VALUE;
        }
    }

    @JvmStatic
    public static final boolean bundleIsSmall(Bundle bundle) {
        Intrinsics.checkNotNullParameter(bundle, "bundle");
        Parcel parcelObtain = Parcel.obtain();
        Intrinsics.checkNotNullExpressionValue(parcelObtain, "obtain(...)");
        bundle.writeToParcel(parcelObtain, 0);
        long jDataSize = parcelObtain.dataSize();
        parcelObtain.recycle();
        return jDataSize < MAX_SIZE;
    }

    @JvmStatic
    public static final void fixInputMethodMemLeak(Context context, IBinder windowToken) {
        Intrinsics.checkNotNullParameter(context, "context");
        InputMethodManager inputMethodManager = (InputMethodManager) context.getSystemService("input_method");
        if (inputMethodManager == null) {
            return;
        }
        String[] strArr = {"mCurRootView", "mServedView", "mNextServedView"};
        for (int i = 0; i < 3; i++) {
            try {
                Field declaredField = inputMethodManager.getClass().getDeclaredField(strArr[i]);
                declaredField.setAccessible(true);
                Object obj = declaredField.get(inputMethodManager);
                if ((obj instanceof View) && ((View) obj).getWindowToken() == windowToken) {
                    declaredField.set(inputMethodManager, null);
                }
            } catch (Exception e) {
                Logger.INSTANCE.e("Notes:fixInputMethodMemLeak", "Exception: " + e);
            }
        }
    }

    @JvmStatic
    public static final String trimStringEnd(String string) {
        if (string == null) {
            return "";
        }
        int length = string.length();
        while (length > 0 && Intrinsics.compare((int) string.charAt(length - 1), 32) <= 0) {
            length--;
        }
        if (length >= string.length()) {
            return string;
        }
        String strSubstring = string.substring(0, length);
        Intrinsics.checkNotNullExpressionValue(strSubstring, "substring(...)");
        return strSubstring;
    }

    public static final boolean isRecordIsInstall() {
        try {
            return BaseNoteApp.INSTANCE.getInstance().getPackageManager().getPackageInfo("com.android.soundrecorder", 0) != null;
        } catch (Exception e) {
            Logger.INSTANCE.d(TAG, e.toString());
        }
    }

    public static final boolean isNeedUpdateRecord() {
        try {
            PackageInfo packageInfo = BaseNoteApp.INSTANCE.getInstance().getPackageManager().getPackageInfo("com.android.soundrecorder", 0);
            return packageInfo == null || packageInfo.versionCode < recordVersion;
        } catch (Exception e) {
            Logger.INSTANCE.d(TAG, e.toString());
        }
        return true;
    }

    @JvmStatic
    public static final int getStringSize(String input) {
        if (TextUtils.isEmpty(input)) {
            return 0;
        }
        Intrinsics.checkNotNull(input);
        return input.length();
    }

    public final void saveJsonToFile(String fileName, String jsonData) {
        Intrinsics.checkNotNullParameter(fileName, "fileName");
        try {
            FileWriter fileWriter = new FileWriter(new File(fileName));
            try {
                fileWriter.write(jsonData);
                Unit unit = Unit.INSTANCE;
                CloseableKt.closeFinally(fileWriter, null);
            } catch (Throwable th) {
                try {
                    throw th;
                } catch (Throwable th2) {
                    CloseableKt.closeFinally(fileWriter, th);
                    throw th2;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
