package com.miui.hybirdeditor;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.app.Activity;
import android.content.ClipData;
import android.content.ClipDescription;
import android.content.Context;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.SystemClock;
import android.text.TextUtils;
import android.view.DragEvent;
import android.view.View;
import android.view.accessibility.AccessibilityManager;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.inputmethod.InputContentInfo;
import android.webkit.ConsoleMessage;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import androidx.appcompat.widget.AppCompatImageView;
import com.google.gson.Gson;
import com.market.sdk.reflect.ReflectUtilsForMiui;
import com.miui.aiengine.common.result.SSEConst;
import com.miui.common.app.BaseNoteApp;
import com.miui.common.base.NestedWebView;
import com.miui.common.stat.NotesTextStat;
import com.miui.common.tool.DisplayUtils;
import com.miui.common.tool.ImageUtils;
import com.miui.common.tool.Logger;
import com.miui.common.tool.MiFontStyle;
import com.miui.common.tool.UIUtils;
import com.miui.common.tool.Utils;
import com.miui.doodle.document.Layer;
import com.miui.hybirdeditor.jsbridge.EmbedElementInfoBean;
import com.miui.hybirdeditor.jsbridge.HybridEditorScriptInterface;
import com.miui.hybirdeditor.jsbridge.TextStatusBean;
import com.miui.maml.folme.AnimatedProperty;
import com.miui.notes.R;
import com.miui.notes.action.comon.AiActionUtil;
import com.miui.notes.basefeature.NotesNormalConstants;
import com.miui.notes.basefeature.noteedit.INoteRichEditor;
import com.miui.notes.feature.noteedit.DataExtraInfo;
import com.miui.notes.model.FolderModel;
import com.miui.notes.provider.Notes;
import com.miui.notes.tool.AttachmentUtils;
import com.miui.notes.tool.PreferenceUtils;
import com.miui.pad.feature.notes.share.ShareAppsAdapter;
import com.miui.richeditor.IRichEditor;
import com.miui.richeditor.TextStyleStateListener;
import com.miui.richeditor.schema.NoteSchema;
import com.miui.richeditor.theme.Theme;
import com.miui.richeditor.util.NoteClipManager;
import com.miui.richeditor.util.ResourceParser;
import com.xiaomi.mirror.synergy.CallMethod;
import defpackage.R2;
import java.io.File;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.Arrays;
import kotlin.Deprecated;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.StringCompanionObject;
import kotlin.text.Charsets;
import kotlin.text.Regex;
import kotlin.text.StringsKt;
import miuix.os.Environment;
import org.apache.commons.lang3.StringUtils;
import org.json.JSONObject;

/* JADX INFO: compiled from: WebRichEditor.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u0000¢\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0010\u0007\n\u0002\b\u0002\n\u0002\u0010\t\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0013\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u0002\n\u0002\b\t\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0010\u0011\n\u0002\u0018\u0002\n\u0002\b\f\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\t\n\u0002\u0018\u0002\n\u0002\b\u000b\n\u0002\u0010\r\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u000f\n\u0002\u0018\u0002\n\u0002\b\r\n\u0002\u0018\u0002\n\u0002\b\\\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0018\u0002\n\u0002\b\u0015\n\u0002\u0010\u0006\n\u0002\b/\n\u0002\u0018\u0002\n\u0002\b\n\b\u0016\u0018\u0000 ø\u00022\u00020\u0001:\u0006ö\u0002÷\u0002ø\u0002B3\u0012\b\u0010\u0002\u001a\u0004\u0018\u00010\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\b\u0010\u0006\u001a\u0004\u0018\u00010\u0007\u0012\u0006\u0010\b\u001a\u00020\t\u0012\u0006\u0010\n\u001a\u00020\u000b¢\u0006\u0004\b\f\u0010\rJ\u0010\u0010^\u001a\u00020_2\b\u0010`\u001a\u0004\u0018\u00010VJ\u000e\u0010a\u001a\u00020_2\u0006\u0010b\u001a\u000203J\u0006\u0010c\u001a\u00020_J\u0012\u0010d\u001a\u00020\u001f2\b\u0010b\u001a\u0004\u0018\u000103H\u0002J\b\u0010h\u001a\u00020iH\u0016J\u0012\u0010j\u001a\u00020_2\b\u0010k\u001a\u0004\u0018\u00010\u000fH\u0016J\u0012\u0010l\u001a\u00020_2\b\u0010m\u001a\u0004\u0018\u00010nH\u0016J\u0010\u0010o\u001a\u00020_2\u0006\u0010p\u001a\u00020\u001cH\u0016J\u0006\u0010q\u001a\u00020\u001cJ\u0010\u0010r\u001a\u00020_2\u0006\u0010s\u001a\u00020\u001cH\u0016J\n\u0010t\u001a\u0004\u0018\u00010uH\u0016J\u0010\u0010y\u001a\u00020_2\b\u0010k\u001a\u0004\u0018\u00010\u0011J+\u0010z\u001a\u00020_2\u0006\u0010{\u001a\u00020\u000b2\u0016\u0010|\u001a\f\u0012\b\b\u0001\u0012\u0004\u0018\u00010~0}\"\u0004\u0018\u00010~¢\u0006\u0002\u0010\u007fJ\u0012\u0010\u0080\u0001\u001a\u00020_2\t\u0010\u0081\u0001\u001a\u0004\u0018\u00010\u001fJ\u001f\u0010\u0082\u0001\u001a\u00020_2\t\u0010\u0081\u0001\u001a\u0004\u0018\u00010\u001f2\t\u0010\u0083\u0001\u001a\u0004\u0018\u00010\u001fH\u0016J\u001a\u0010\u0084\u0001\u001a\u00020_2\b\u0010b\u001a\u0004\u0018\u0001032\u0007\u0010\u0085\u0001\u001a\u00020\u001cJ^\u0010\u0086\u0001\u001a\u00020_2\t\u0010\u0087\u0001\u001a\u0004\u0018\u00010\u001f2\t\u0010\u0083\u0001\u001a\u0004\u0018\u00010\u001f2\t\u0010\u0088\u0001\u001a\u0004\u0018\u00010\u001f2\t\u0010\u0089\u0001\u001a\u0004\u0018\u00010\u000b2!\u0010\u008a\u0001\u001a\u001c\u0012\u0006\u0012\u0004\u0018\u00010\u001f\u0018\u00010\u008b\u0001j\r\u0012\u0006\u0012\u0004\u0018\u00010\u001f\u0018\u0001`\u008c\u0001H\u0016¢\u0006\u0003\u0010\u008d\u0001J\u0012\u0010\u008e\u0001\u001a\u00020_2\u0007\u0010\u008f\u0001\u001a\u00020\u001cH\u0016J\u001a\u0010\u0090\u0001\u001a\u00020_2\b\u0010b\u001a\u0004\u0018\u0001032\u0007\u0010\u0085\u0001\u001a\u00020\u001cJ\u0012\u0010\u0091\u0001\u001a\u00020_2\u0007\u0010\u0092\u0001\u001a\u00020\tH\u0016J5\u0010\u0093\u0001\u001a\u00020_2\t\u0010\u0088\u0001\u001a\u0004\u0018\u00010\u001f2!\u0010\u008a\u0001\u001a\u001c\u0012\u0006\u0012\u0004\u0018\u00010\u001f\u0018\u00010\u008b\u0001j\r\u0012\u0006\u0012\u0004\u0018\u00010\u001f\u0018\u0001`\u008c\u0001J\u0011\u0010\u0094\u0001\u001a\u00020_2\b\u0010\u0095\u0001\u001a\u00030\u0096\u0001JA\u0010\u0097\u0001\u001a\u00020_2\u0007\u0010\u0098\u0001\u001a\u00020\u000b2\u0007\u0010\u0099\u0001\u001a\u00020\u000b2\u0007\u0010\u009a\u0001\u001a\u00020\u000b2\u0007\u0010\u009b\u0001\u001a\u00020\u000b2\t\u0010\u009c\u0001\u001a\u0004\u0018\u00010\u001f2\t\u0010\u009d\u0001\u001a\u0004\u0018\u00010\u001fJ\u0007\u0010\u009e\u0001\u001a\u00020_J\u000b\u0010\u009f\u0001\u001a\u0004\u0018\u00010\u001fH\u0016J\u0010\u0010 \u0001\u001a\u00020_2\u0007\u0010\u0087\u0001\u001a\u00020\u001fJ\n\u0010¡\u0001\u001a\u00030¢\u0001H\u0016J\u0013\u0010£\u0001\u001a\u00020_2\n\u0010¤\u0001\u001a\u0005\u0018\u00010¥\u0001J\u0012\u0010ª\u0001\u001a\u00020_2\u0007\u0010«\u0001\u001a\u00020\u000bH\u0016J\t\u0010¬\u0001\u001a\u00020_H\u0016J\t\u0010\u00ad\u0001\u001a\u00020_H\u0016J\t\u0010®\u0001\u001a\u00020_H\u0016J\u0012\u0010¯\u0001\u001a\u00020\u000b2\u0007\u0010°\u0001\u001a\u00020\tH\u0016J\u0012\u0010±\u0001\u001a\u00020_2\u0007\u0010²\u0001\u001a\u00020\u000bH\u0016J\u0015\u0010³\u0001\u001a\u00020_2\n\u0010´\u0001\u001a\u0005\u0018\u00010µ\u0001H\u0016J\t\u0010¶\u0001\u001a\u00020_H\u0016J\t\u0010·\u0001\u001a\u00020_H\u0016J\t\u0010¸\u0001\u001a\u00020_H\u0016J\t\u0010¹\u0001\u001a\u00020_H\u0016J\t\u0010º\u0001\u001a\u00020_H\u0016J\t\u0010»\u0001\u001a\u00020_H\u0016J\t\u0010¼\u0001\u001a\u00020_H\u0016J\t\u0010½\u0001\u001a\u00020_H\u0016J\t\u0010¾\u0001\u001a\u00020_H\u0016J\u0012\u0010¿\u0001\u001a\u00020_2\t\u0010À\u0001\u001a\u0004\u0018\u00010\u001fJ\u0013\u0010Á\u0001\u001a\u00020_2\b\u0010Â\u0001\u001a\u00030Ã\u0001H\u0016J\u001c\u0010Á\u0001\u001a\u00020_2\b\u0010Â\u0001\u001a\u00030Ã\u00012\u0007\u0010Ä\u0001\u001a\u00020\u001fH\u0016J&\u0010Å\u0001\u001a\u00020_2\u0007\u0010Æ\u0001\u001a\u00020\u001f2\t\u0010Ç\u0001\u001a\u0004\u0018\u00010\u001f2\u0007\u0010Ä\u0001\u001a\u00020\u001fH\u0002J\t\u0010È\u0001\u001a\u00020_H\u0016J\t\u0010É\u0001\u001a\u00020_H\u0016J\t\u0010Ê\u0001\u001a\u00020_H\u0016J\t\u0010Ë\u0001\u001a\u00020_H\u0016J\t\u0010Ì\u0001\u001a\u00020_H\u0016J\t\u0010Í\u0001\u001a\u00020_H\u0016J\t\u0010Î\u0001\u001a\u00020_H\u0016J\t\u0010Ï\u0001\u001a\u00020_H\u0016J\t\u0010Ð\u0001\u001a\u00020_H\u0016J\t\u0010Ñ\u0001\u001a\u00020_H\u0016J\u0007\u0010Ò\u0001\u001a\u00020_J\u0007\u0010Ó\u0001\u001a\u00020_J\u0012\u0010Ô\u0001\u001a\u00020_2\t\b\u0001\u0010Õ\u0001\u001a\u00020\tJ\u0012\u0010Ö\u0001\u001a\u00020_2\u0007\u0010×\u0001\u001a\u00020\tH\u0016J\t\u0010Ø\u0001\u001a\u00020\u000bH\u0016J\t\u0010Ù\u0001\u001a\u00020\u000bH\u0016J\t\u0010Ú\u0001\u001a\u00020\u000bH\u0016J\t\u0010Û\u0001\u001a\u00020\u000bH\u0016J\t\u0010Ü\u0001\u001a\u00020_H\u0016J\t\u0010Ý\u0001\u001a\u00020_H\u0016J\t\u0010Þ\u0001\u001a\u00020_H\u0016J\u001b\u0010ß\u0001\u001a\u00020_2\u0007\u0010à\u0001\u001a\u00020\t2\u0007\u0010á\u0001\u001a\u00020\tH\u0016J\u0012\u0010ß\u0001\u001a\u00020_2\u0007\u0010â\u0001\u001a\u00020\tH\u0016J\t\u0010ã\u0001\u001a\u00020_H\u0016J\t\u0010ä\u0001\u001a\u00020\u000bH\u0016J\u0012\u0010å\u0001\u001a\u00020_2\u0007\u0010æ\u0001\u001a\u00020\u000bH\u0016J\t\u0010ç\u0001\u001a\u00020_H\u0016J\u0012\u0010Û\u0001\u001a\u00020\u000b2\u0007\u0010è\u0001\u001a\u00020\tH\u0016J\u0012\u0010é\u0001\u001a\u00020_2\u0007\u0010æ\u0001\u001a\u00020\u000bH\u0016J\u0007\u0010ê\u0001\u001a\u00020_J\u0007\u0010ë\u0001\u001a\u00020_J\u001d\u0010ì\u0001\u001a\u00020_2\t\u0010Ä\u0001\u001a\u0004\u0018\u00010\u001f2\t\u0010í\u0001\u001a\u0004\u0018\u00010\u001fJ\t\u0010î\u0001\u001a\u00020_H\u0007J(\u0010ï\u0001\u001a\u00020_2\u0011\u0010ð\u0001\u001a\f\u0012\u0006\u0012\u0004\u0018\u00010\u001f\u0018\u00010}2\u0006\u0010{\u001a\u00020\u000b¢\u0006\u0003\u0010ñ\u0001J\u0007\u0010ò\u0001\u001a\u00020_J\u0007\u0010ó\u0001\u001a\u00020_J\u0007\u0010ô\u0001\u001a\u00020_J\u0012\u0010õ\u0001\u001a\u00020_2\t\u0010ö\u0001\u001a\u0004\u0018\u00010\u001fJ\u0007\u0010÷\u0001\u001a\u00020_J\u0012\u0010ø\u0001\u001a\u00020_2\t\u0010Ä\u0001\u001a\u0004\u0018\u00010\u001fJ\u0007\u0010ù\u0001\u001a\u00020_J\u001b\u0010ú\u0001\u001a\u00020_2\t\u0010û\u0001\u001a\u0004\u0018\u00010\u001f2\u0007\u0010ü\u0001\u001a\u00020\u000bJ\u0007\u0010ý\u0001\u001a\u00020_J\u0007\u0010þ\u0001\u001a\u00020_J\u001b\u0010ÿ\u0001\u001a\u00020_2\t\u0010û\u0001\u001a\u0004\u0018\u00010\u001f2\u0007\u0010ü\u0001\u001a\u00020\u000bJ\u0012\u0010\u0080\u0002\u001a\u00020_2\t\u0010û\u0001\u001a\u0004\u0018\u00010\u001fJ\u0007\u0010\u0081\u0002\u001a\u00020_J\u0007\u0010\u0082\u0002\u001a\u00020_J\u0012\u0010\u0083\u0002\u001a\u00020_2\t\u0010\u0084\u0002\u001a\u0004\u0018\u00010\u001fJ\u0012\u0010\u0085\u0002\u001a\u00020_2\t\u0010\u0086\u0002\u001a\u0004\u0018\u00010\u001fJ\u0012\u0010\u0087\u0002\u001a\u00020_2\t\u0010\u0088\u0002\u001a\u0004\u0018\u00010\u001fJ\u0010\u0010\u0089\u0002\u001a\u00020\u000b2\u0007\u0010\u008a\u0002\u001a\u00020\u001fJ\t\u0010\u008b\u0002\u001a\u00020\u001cH\u0002J\u0007\u0010\u008c\u0002\u001a\u00020_J\u0010\u0010\u008f\u0002\u001a\u00020_2\u0007\u0010\u0090\u0002\u001a\u00020\u000bJ\u0007\u0010\u0091\u0002\u001a\u00020_J\u0012\u0010\u0092\u0002\u001a\u00020_2\t\u0010\u0093\u0002\u001a\u0004\u0018\u00010HJ\u0007\u0010\u0094\u0002\u001a\u00020\u000bJ\u0019\u0010\u0095\u0002\u001a\u00020_2\u0007\u0010\u0096\u0002\u001a\u00020\u000b2\u0007\u0010\u0097\u0002\u001a\u00020\u000bJ\u0010\u0010\u0098\u0002\u001a\u00020_2\u0007\u0010\u0099\u0002\u001a\u00020\u000bJ\u0010\u0010\u009a\u0002\u001a\u00020_2\u0007\u0010²\u0001\u001a\u00020\u000bJ\u0019\u0010\u009b\u0002\u001a\u00020_2\u0007\u0010\u009c\u0002\u001a\u00020\t2\u0007\u0010\u009d\u0002\u001a\u00020\tJ\u0013\u0010\u009e\u0002\u001a\u00020_2\n\u0010\u009f\u0002\u001a\u0005\u0018\u00010 \u0002J\u0010\u0010¡\u0002\u001a\u00020_2\u0007\u0010¢\u0002\u001a\u00020\tJ\u0007\u0010£\u0002\u001a\u00020_J\u0007\u0010¤\u0002\u001a\u00020_J\u0007\u0010¥\u0002\u001a\u00020_J\t\u0010¦\u0002\u001a\u00020\u000bH\u0007J\u0011\u0010§\u0002\u001a\u00020\t2\b\u0010¨\u0002\u001a\u00030©\u0002J\u0011\u0010ª\u0002\u001a\u00020\u000b2\b\u0010¨\u0002\u001a\u00030©\u0002J\u0011\u0010«\u0002\u001a\u00020\u000b2\b\u0010¨\u0002\u001a\u00030©\u0002J\u0011\u0010¬\u0002\u001a\u00020\u000b2\b\u0010¨\u0002\u001a\u00030©\u0002J\u0010\u0010\u00ad\u0002\u001a\u00020_2\u0007\u0010®\u0002\u001a\u00020\tJ\u0010\u0010±\u0002\u001a\u00020_2\u0007\u0010²\u0002\u001a\u00020FJ\u0010\u0010³\u0002\u001a\u00020_2\u0007\u0010²\u0002\u001a\u00020FJ\u0010\u0010´\u0002\u001a\u00020_2\u0007\u0010µ\u0002\u001a\u00020\u000bJ\u0010\u0010¶\u0002\u001a\u00020_2\u0007\u0010·\u0002\u001a\u00020\u000bJ\t\u0010¸\u0002\u001a\u00020_H\u0002J\u0012\u0010¹\u0002\u001a\u00020_2\t\u0010\u0081\u0001\u001a\u0004\u0018\u00010\u001fJ\u0012\u0010º\u0002\u001a\u00020_2\t\u0010\u0081\u0001\u001a\u0004\u0018\u00010\u001fJ\u0007\u0010»\u0002\u001a\u00020_J\u0007\u0010¼\u0002\u001a\u00020_J\u0011\u0010½\u0002\u001a\u00020_2\b\u0010¾\u0002\u001a\u00030¿\u0002J&\u0010À\u0002\u001a\u00020_2\t\u0010Ä\u0001\u001a\u0004\u0018\u00010\u001f2\t\u0010Á\u0002\u001a\u0004\u0018\u00010\u001f2\u0007\u0010Â\u0002\u001a\u00020\tJ,\u0010Ã\u0002\u001a\u00020_2\t\u0010Ä\u0002\u001a\u0004\u0018\u00010\u001f2\t\u0010Å\u0002\u001a\u0004\u0018\u00010\u000b2\u0007\u0010Æ\u0002\u001a\u00020\t¢\u0006\u0003\u0010Ç\u0002J\u0007\u0010È\u0002\u001a\u00020_J\u0007\u0010É\u0002\u001a\u00020_J\u0007\u0010Ê\u0002\u001a\u00020_J\u0007\u0010Ë\u0002\u001a\u00020_J\u0010\u0010Ì\u0002\u001a\u00020_2\u0007\u0010Í\u0002\u001a\u00020\u000bJ\u0007\u0010Î\u0002\u001a\u00020_J\u0007\u0010Ï\u0002\u001a\u00020_J-\u0010Ð\u0002\u001a\u00020_2\t\u0010À\u0001\u001a\u0004\u0018\u00010\u001f2\u0007\u0010Ñ\u0002\u001a\u00020\u000b2\u0007\u0010Ò\u0002\u001a\u00020\u000b2\u0007\u0010Ó\u0002\u001a\u00020\u000bJ\u001b\u0010Ô\u0002\u001a\u00020_2\u0007\u0010Õ\u0002\u001a\u00020\u000b2\t\u0010À\u0001\u001a\u0004\u0018\u00010\u001fJ\u0012\u0010Ö\u0002\u001a\u00020_2\t\u0010\u0083\u0001\u001a\u0004\u0018\u00010\u001fJ\u0012\u0010×\u0002\u001a\u00020_2\t\u0010Ø\u0002\u001a\u0004\u0018\u00010\u001fJ\u0012\u0010Ù\u0002\u001a\u00020_2\t\u0010Ú\u0002\u001a\u0004\u0018\u00010\u001fJ\u0007\u0010Û\u0002\u001a\u00020_J\u0007\u0010Ü\u0002\u001a\u00020_J\u0007\u0010Ý\u0002\u001a\u00020_J\u0007\u0010Þ\u0002\u001a\u00020_J\u0007\u0010ß\u0002\u001a\u00020_J\u001d\u0010\u0086\u0001\u001a\u00020_2\t\u0010À\u0001\u001a\u0004\u0018\u00010\u001f2\t\u0010\u0083\u0001\u001a\u0004\u0018\u00010\u001fJ7\u0010à\u0002\u001a\u00020_2\u0007\u0010\u0087\u0001\u001a\u00020\u001f2\t\u0010\u0083\u0001\u001a\u0004\u0018\u00010\u001f2\t\u0010\u0088\u0001\u001a\u0004\u0018\u00010\u001f2\t\u0010\u0089\u0001\u001a\u0004\u0018\u00010\u000b¢\u0006\u0003\u0010á\u0002J\u0007\u0010â\u0002\u001a\u00020_J\u0012\u0010â\u0002\u001a\u00020_2\t\u0010À\u0001\u001a\u0004\u0018\u00010\u001fJ\u0019\u0010ã\u0002\u001a\u00020_2\u0007\u0010ä\u0002\u001a\u00020\t2\u0007\u0010å\u0002\u001a\u00020\u000bJ\u0007\u0010æ\u0002\u001a\u00020_J\u0007\u0010ç\u0002\u001a\u00020_J\u0007\u0010è\u0002\u001a\u00020_J\u0007\u0010é\u0002\u001a\u00020_J\u0007\u0010ê\u0002\u001a\u00020_J\u0007\u0010ë\u0002\u001a\u00020_J&\u0010ì\u0002\u001a\u00020_2\u0007\u0010í\u0002\u001a\u00020\u001f2\u0014\u0010î\u0002\u001a\u000f\u0012\u0004\u0012\u00020\u000b\u0012\u0004\u0012\u00020_0ï\u0002J\t\u0010ð\u0002\u001a\u00020_H\u0002J\t\u0010ñ\u0002\u001a\u00020_H\u0002J\u0012\u0010ò\u0002\u001a\u00020_2\u0007\u0010ó\u0002\u001a\u00020\u000bH\u0002J\t\u0010ô\u0002\u001a\u00020\u000bH\u0002J\u0007\u0010õ\u0002\u001a\u00020\u000bR\u0014\u0010\u0002\u001a\u0004\u0018\u00010\u00038\u0006@\u0006X\u0087\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004¢\u0006\u0002\n\u0000R\u0010\u0010\u0006\u001a\u0004\u0018\u00010\u0007X\u0082\u0004¢\u0006\u0002\n\u0000R\u0010\u0010\u000e\u001a\u0004\u0018\u00010\u000fX\u0082\u000e¢\u0006\u0002\n\u0000R\u0010\u0010\u0010\u001a\u0004\u0018\u00010\u0011X\u0082\u000e¢\u0006\u0002\n\u0000R$\u0010\u0013\u001a\u00020\u000b2\u0006\u0010\u0012\u001a\u00020\u000b@DX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0013\u0010\u0014\"\u0004\b\u0015\u0010\u0016R\u000e\u0010\u0017\u001a\u00020\tX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0018\u001a\u00020\u0019X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u001a\u001a\u00020\u0019X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u001b\u001a\u00020\u001cX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u001d\u001a\u00020\u001cX\u0082\u000e¢\u0006\u0002\n\u0000R\u001c\u0010\u001e\u001a\u0004\u0018\u00010\u001fX\u0084\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b \u0010!\"\u0004\b\"\u0010#R\u001c\u0010$\u001a\u0004\u0018\u00010\u001fX\u0084\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b%\u0010!\"\u0004\b&\u0010#R$\u0010'\u001a\u00020\u001c2\u0006\u0010\u0012\u001a\u00020\u001c@DX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b(\u0010)\"\u0004\b*\u0010+R\u001c\u0010,\u001a\u0004\u0018\u00010\u001fX\u0084\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b-\u0010!\"\u0004\b.\u0010#R\u001a\u0010/\u001a\u00020\u001fX\u0084\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b0\u0010!\"\u0004\b1\u0010#R\u000e\u00102\u001a\u00020\u000bX\u0082\u000e¢\u0006\u0002\n\u0000R(\u00104\u001a\u0004\u0018\u0001032\b\u0010\u0012\u001a\u0004\u0018\u000103@DX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b5\u00106\"\u0004\b7\u00108R\u001a\u00109\u001a\u00020:X\u0084\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b;\u0010<\"\u0004\b=\u0010>R\u0010\u0010?\u001a\u0004\u0018\u00010@X\u0082\u000e¢\u0006\u0002\n\u0000R\u0010\u0010A\u001a\u0004\u0018\u00010BX\u0082\u000e¢\u0006\u0002\n\u0000R\u0010\u0010C\u001a\u0004\u0018\u00010DX\u0082\u000e¢\u0006\u0002\n\u0000R\u0010\u0010E\u001a\u0004\u0018\u00010FX\u0082\u000e¢\u0006\u0002\n\u0000R\u0010\u0010G\u001a\u0004\u0018\u00010HX\u0082\u000e¢\u0006\u0002\n\u0000R\u0012\u0010I\u001a\u00020\u000b8\u0006@\u0006X\u0087\u000e¢\u0006\u0002\n\u0000R\u0010\u0010J\u001a\u0004\u0018\u00010KX\u0082\u000e¢\u0006\u0002\n\u0000R\u0010\u0010L\u001a\u0004\u0018\u00010MX\u0082\u000e¢\u0006\u0002\n\u0000R\u001a\u0010N\u001a\u00020\u000bX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\bN\u0010\u0014\"\u0004\bO\u0010\u0016R\u001a\u0010P\u001a\u00020\u000bX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\bQ\u0010\u0014\"\u0004\bR\u0010\u0016R\u000e\u0010S\u001a\u00020\u001cX\u0082\u000e¢\u0006\u0002\n\u0000R\u0010\u0010T\u001a\u0004\u0018\u00010\u001fX\u0082\u000e¢\u0006\u0002\n\u0000R\u0010\u0010U\u001a\u0004\u0018\u00010VX\u0082\u000e¢\u0006\u0002\n\u0000R\u0010\u0010W\u001a\u0004\u0018\u00010XX\u0082\u000e¢\u0006\u0002\n\u0000R\u0010\u0010Y\u001a\u0004\u0018\u00010ZX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010[\u001a\u00020\u000bX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\\\u001a\u00020\u001fX\u0082D¢\u0006\u0002\n\u0000R\u000e\u0010]\u001a\u00020\u001fX\u0082D¢\u0006\u0002\n\u0000R\u0011\u0010e\u001a\u00020\t8F¢\u0006\u0006\u001a\u0004\bf\u0010gR\u0014\u0010v\u001a\u00020\u00198BX\u0082\u0004¢\u0006\u0006\u001a\u0004\bw\u0010xR(\u0010¦\u0001\u001a\u00020\u00192\u0006\u0010v\u001a\u00020\u00198F@FX\u0086\u000e¢\u0006\u000f\u001a\u0005\b§\u0001\u0010x\"\u0006\b¨\u0001\u0010©\u0001R\u0013\u0010\u008d\u0002\u001a\u00020\u000b8F¢\u0006\u0007\u001a\u0005\b\u008e\u0002\u0010\u0014R\u0016\u0010¯\u0002\u001a\u00020\u00198DX\u0084\u0004¢\u0006\u0007\u001a\u0005\b°\u0002\u0010x¨\u0006ù\u0002"}, d2 = {"Lcom/miui/hybirdeditor/WebRichEditor;", "Lcom/miui/notes/basefeature/noteedit/INoteRichEditor;", "mWebCore", "Lcom/miui/hybirdeditor/RichEditorWebView;", "mBridge", "Lcom/miui/hybirdeditor/jsbridge/HybridEditorScriptInterface;", "actionListener", "Lcom/miui/hybirdeditor/EditorActionListener;", "fontSizeId", "", "isPcMode", "", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "(Lcom/miui/hybirdeditor/RichEditorWebView;Lcom/miui/hybirdeditor/jsbridge/HybridEditorScriptInterface;Lcom/miui/hybirdeditor/EditorActionListener;IZ)V", "mTextStyleStateListener", "Lcom/miui/richeditor/TextStyleStateListener;", "mButtonStateListener", "Lcom/miui/hybirdeditor/WebRichEditor$ButtonStateListener;", "value", "isIsKeyboardVisible", "()Z", "setIsKeyboardVisible", "(Z)V", "mFontSizeId", "mTitleTextSize", "", "mFontTextSize", "mDataId", "", "mFolderId", "mSearchText", "", "getMSearchText", "()Ljava/lang/String;", "setMSearchText", "(Ljava/lang/String;)V", "mTitle", "getMTitle", "setMTitle", "contentLength", "getContentLength", "()J", "setContentLength", "(J)V", "mRichTextData", "getMRichTextData", "setMRichTextData", "mRichTextOriginData", "getMRichTextOriginData", "setMRichTextOriginData", "mDataFedToFrontend", "Lcom/miui/richeditor/theme/Theme;", "editorTheme", "getEditorTheme", "()Lcom/miui/richeditor/theme/Theme;", "setEditorTheme", "(Lcom/miui/richeditor/theme/Theme;)V", "mGson", "Lcom/google/gson/Gson;", "getMGson", "()Lcom/google/gson/Gson;", "setMGson", "(Lcom/google/gson/Gson;)V", "mImageInsertTask", "Lcom/miui/hybirdeditor/ImageInsertTask;", "mImageShadowView", "Landroidx/appcompat/widget/AppCompatImageView;", "mValueAnimator", "Landroid/animation/ValueAnimator;", "mDraggingElementInfo", "Lcom/miui/hybirdeditor/jsbridge/EmbedElementInfoBean;", "mTextStatusBean", "Lcom/miui/hybirdeditor/jsbridge/TextStatusBean;", "isNeedTextStyleChanged", "mTextThumbnailShadow", "Lcom/miui/hybirdeditor/TextThumbnailShadow;", "mImageThumbnailShadow", "Lcom/miui/hybirdeditor/ImageThumbnailShadow;", "isInDragMode", "setInDragMode", "mIsPcMode", "getMIsPcMode", "setMIsPcMode", "mKeyVisibleTime", "currentAigcData", "mOnRenderProcessGoneListener", "Lcom/miui/hybirdeditor/WebRichEditorClient$onRenderProcessGoneListener;", "mAccessibilityManager", "Landroid/view/accessibility/AccessibilityManager;", "mTouchExplorationStateChangeListener", "Landroid/view/accessibility/AccessibilityManager$TouchExplorationStateChangeListener;", "mIsTouchExplorationEnabled", "ACCESSIBILITY_PREF_NAME", "KEY_TOUCH_EXPLORATION_ENABLED", "setOnRenderProcessGoneListener", "", "onRenderProcessGoneListener", "initWebCore", "theme", "resetScale", "getInitParam", "fontWeight", "getFontWeight", "()I", "getContext", "Landroid/content/Context;", "setTextStyleStateListener", "listener", "setActivity", "activity", "Landroid/app/Activity;", "setDataId", "dataId", "getDataId", "setFolderId", "folderId", "getView", "Landroid/webkit/WebView;", "textScale", "getTextScale", "()F", "setButtonStateListener", "insertImages", "needWithPos", Notes.Note.IMAGES, "", "Landroid/net/Uri;", "(Z[Landroid/net/Uri;)V", "insertContent", "text", "pasteMarkdown", "title", "onThemeChanged", "lastModifiedTIme", "setData", "richTextData", "queryContent", "isNeedSetData", "highLightKeyWords", "Ljava/util/ArrayList;", "Lkotlin/collections/ArrayList;", "(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/Boolean;Ljava/util/ArrayList;)V", "setCount", "count", "setTheme", "updateFontSizeId", "id", "updateQueryContent", "updateExtraInto", "extraInfo", "Lcom/miui/notes/feature/noteedit/DataExtraInfo;", "updateViewState", "canSelect", "canQuery", "canPhrase", "canTranslate", "selectData", "htmlData", "selectAllText", "getTitle", "resetRichTextData", "getRichTextData", "", "setFontStyle", "style", "Lcom/miui/common/tool/MiFontStyle;", "textSize", "getTextSize", "setTextSize", "(F)V", "setEnableEdit", "enableEdit", NotesTextStat.UNDO, NotesTextStat.REDO, "done", "canAddNewElement", "newAddContentLength", "setEnableToggleAlignment", "enable", "setExternalClickListener", "externalClickListener", "Landroid/view/View$OnClickListener;", "setH1", "setH2", "setH3", "setDeline", "setOrderList", "setQuote", "setHr", "setIndent", "setUndent", "setAigcData", "data", "getMarkdown", "onWriteFinishedListener", "Lcom/miui/hybirdeditor/WebRichEditor$OnWriteFinishedListener;", "fileName", "writeTxtToFile", "strcontent", "filePath", "setFontBold", "setFontItalic", "setUnderline", "setBgHighLight", "setAlignCenter", "setAlignNormal", "setAlignRight", "setCheckbox", "setEditorBullet", "setListBullet", "increaseFontSize", "reduceFontSize", "setFontColor", "color", "setFontSizeLevel", "sizeLevel", "canAlignNormal", "canAlignCenter", "canAlignRight", "requestViewFocus", "notifySaveData", "startAudioInput", "stopAudioInput", "setSelection", "start", "stop", "index", "setSelectionToEnd", "isCursorVisible", "setCursorVisible", "show", "clearFocus", "direction", "setViewShowSoftInputOnFocus", "emptyNoteFocus", "editImageStart", "editImageEnd", "oldField", "insertImageStart", "insertImageEnd", "imageFileNames", "([Ljava/lang/String;Z)V", "deleteEmbedElement", "changeShowBigImg", "annotateImageStart", "annotateImageEnd", "annotation", "insertAudioStart", "insertAudioEnd", "destroy", "appendAudioText", "newText", "isPartialResult", "stopPlayingMedia", "pauseVoice", "onMeetingTextResult", "onMeetingTextFull", "hidePopMenu", "createShareImage", "pasteHtmlText", "htmlText", "pastePlainText", "plainText", "onTitleChanged", "newTitle", "onSaveData", "newRichTextData", "userEditElapsedMs", "onFrontendPagePrepared", "editorTextIsEmpty", "getEditorTextIsEmpty", "onAiTextStyleNeedChanged", "canUse", "onTextStyleNeedChanged", "onTextStyleChanged", "bean", "canInsert", "onUndoRedoStateChanged", "undoEnable", "redoEnable", "onKeyboardVisibilityChanged", "visible", "onExternalKeyboardEnable", "overScrollByPosition", AnimatedProperty.PROPERTY_NAME_X, AnimatedProperty.PROPERTY_NAME_Y, "setOnDragListener", "dragListener", "Landroid/view/View$OnDragListener;", "startDragMode", "mode", "enterDragZone", "exitDragZone", "endDragMode", "canCreateMindNote", "getDragEventType", "event", "Landroid/view/DragEvent;", "isDragEventContainText", "isDragEventContainImage", "isValidDragEventData", "selectLineInDragMode", "line", "imagePreviewMaxSizeScale", "getImagePreviewMaxSizeScale", "startDragText", "infoBean", "startDragImage", "changeShadowView", "mIsTextPlain", "setIsInTitle", "inTitle", "handleReceiveContent", "insertSummaryToTop", "replaceDoc", "highlightSummary", "highlightAllBackgroundSection", "setCurrentAmplitude", "amplitude", "", "setAudioDescription", "description", "type", "startAudioByTime", "audioFileName", "isPlaying", "startTime", "(Ljava/lang/String;Ljava/lang/Boolean;I)V", "drawSelectionFirst", "drawSelection", "drawRefreshSelection", "drawLoadingGradient", "toggleEmptyLoading", "dismiss", "removeSelectionColorBg", "removeSelectionColorBgSlowly", "aiInput", "isSummary", "firstInvoke", "isThinkContent", "finishFlow", "inNewPanel", "setTitle", "stopAiInput", "endTip", "insertSummary", "savedData", "trimCorrection", "trimAIStatement", "saveScrollPosition", "restoreToSavedScrollPosition", "beforeRefreshNewContent", "setDataWithConfig", "(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/Boolean;)V", "refreshNewContent", "changePanelTop", Layer.KEY_HEIGHT, "isPanelClose", "closeCorrectionPop", "undo2BeforeSnapshot", "specialUndo", "queuedUndo2BeforeSnapshot", "clearFlowStatus", "showSelectionEnhanced", "isShowSelector", "selector", CallMethod.ARG_CALLBACK, "Lkotlin/Function1;", "initAccessibilityManager", "cleanupAccessibilityListener", "saveAccessibilityState", "isEnabled", "loadAccessibilityState", "getAccessibilityState", "OnWriteFinishedListener", "ButtonStateListener", "Companion", "app_PhoneGlobalRelease"}, k = 1, mv = {2, 2, 0}, xi = 48)
public class WebRichEditor implements INoteRichEditor {
    public static final int ACTION_ON_SAVE_DATA = 2;
    public static final int ACTION_ON_TITLE_CHANGED = 1;
    public static final int DRAG_MODE_ELEMENT = 2;
    public static final int DRAG_MODE_TEXT = 1;
    private static final String TAG = "WebRichEditor";
    private final String ACCESSIBILITY_PREF_NAME;
    private final String KEY_TOUCH_EXPLORATION_ENABLED;
    private final EditorActionListener actionListener;
    private long contentLength;
    private String currentAigcData;
    private Theme editorTheme;
    private boolean isInDragMode;
    private boolean isIsKeyboardVisible;
    public boolean isNeedTextStyleChanged;
    private AccessibilityManager mAccessibilityManager;
    private final HybridEditorScriptInterface mBridge;
    private ButtonStateListener mButtonStateListener;
    private volatile boolean mDataFedToFrontend;
    private long mDataId;
    private EmbedElementInfoBean mDraggingElementInfo;
    private long mFolderId;
    private int mFontSizeId;
    private float mFontTextSize;
    private Gson mGson;
    private ImageInsertTask mImageInsertTask;
    private AppCompatImageView mImageShadowView;
    private ImageThumbnailShadow mImageThumbnailShadow;
    private boolean mIsPcMode;
    private boolean mIsTouchExplorationEnabled;
    private long mKeyVisibleTime;
    private WebRichEditorClient.onRenderProcessGoneListener mOnRenderProcessGoneListener;
    private String mRichTextData;
    private String mRichTextOriginData;
    private String mSearchText;
    private TextStatusBean mTextStatusBean;
    private TextStyleStateListener mTextStyleStateListener;
    private TextThumbnailShadow mTextThumbnailShadow;
    private String mTitle;
    private float mTitleTextSize;
    private AccessibilityManager.TouchExplorationStateChangeListener mTouchExplorationStateChangeListener;
    private ValueAnimator mValueAnimator;
    public RichEditorWebView mWebCore;

    /* JADX INFO: renamed from: Companion, reason: from kotlin metadata */
    public static final Companion INSTANCE = new Companion(null);
    private static final int DRAG_MODE_NONE = -1;

    /* JADX INFO: compiled from: WebRichEditor.kt */
    @Metadata(d1 = {"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0002\bæ\u0080\u0001\u0018\u00002\u00020\u0001J\u0018\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u00052\u0006\u0010\u0006\u001a\u00020\u0005H&¨\u0006\u0007À\u0006\u0003"}, d2 = {"Lcom/miui/hybirdeditor/WebRichEditor$ButtonStateListener;", "", "onUndoRedoStateChanged", "", "undoEnable", "", "redoEnable", "app_PhoneGlobalRelease"}, k = 1, mv = {2, 2, 0}, xi = 48)
    public interface ButtonStateListener {
        void onUndoRedoStateChanged(boolean undoEnable, boolean redoEnable);
    }

    /* JADX INFO: compiled from: WebRichEditor.kt */
    @Metadata(d1 = {"\u0000\u0016\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\bæ\u0080\u0001\u0018\u00002\u00020\u0001J\u0010\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u0005H&¨\u0006\u0006À\u0006\u0003"}, d2 = {"Lcom/miui/hybirdeditor/WebRichEditor$OnWriteFinishedListener;", "", "onWriteFinished", "", "markdownName", "", "app_PhoneGlobalRelease"}, k = 1, mv = {2, 2, 0}, xi = 48)
    public interface OnWriteFinishedListener {
        void onWriteFinished(String markdownName);
    }

    protected final float getImagePreviewMaxSizeScale() {
        return 1.0f;
    }

    @Override // com.miui.notes.basefeature.noteedit.INoteRichEditor
    public boolean isCursorVisible() {
        return false;
    }

    public final void selectLineInDragMode(int line) {
    }

    @Override // com.miui.notes.basefeature.noteedit.INoteRichEditor
    public void setActivity(Activity activity) {
    }

    @Override // com.miui.notes.basefeature.noteedit.INoteRichEditor
    public void setCursorVisible(boolean show) {
    }

    @Override // com.miui.notes.basefeature.noteedit.INoteRichEditor
    public void setExternalClickListener(View.OnClickListener externalClickListener) {
    }

    public final void setFontColor(int color) {
    }

    @Override // com.miui.notes.basefeature.noteedit.INoteRichEditor
    public void setSelection(int index) {
    }

    @Override // com.miui.notes.basefeature.noteedit.INoteRichEditor
    public void setSelection(int start, int stop) {
    }

    @Override // com.miui.notes.basefeature.noteedit.INoteRichEditor
    public void setSelectionToEnd() {
    }

    @Override // com.miui.notes.basefeature.noteedit.INoteRichEditor
    public void setViewShowSoftInputOnFocus(boolean show) {
    }

    @Override // com.miui.notes.basefeature.noteedit.INoteRichEditor
    public void startAudioInput() {
    }

    @Override // com.miui.notes.basefeature.noteedit.INoteRichEditor
    public void stopAudioInput() {
    }

    public WebRichEditor(RichEditorWebView richEditorWebView, HybridEditorScriptInterface mBridge, EditorActionListener editorActionListener, int i, boolean z) {
        Intrinsics.checkNotNullParameter(mBridge, "mBridge");
        this.mWebCore = richEditorWebView;
        this.mBridge = mBridge;
        this.actionListener = editorActionListener;
        this.mFontSizeId = 1;
        this.mDataId = -1L;
        this.mFolderId = -1L;
        this.mSearchText = "";
        this.mTitle = "";
        this.mRichTextData = "";
        this.mRichTextOriginData = "";
        this.mGson = new Gson();
        this.ACCESSIBILITY_PREF_NAME = "accessibility_settings";
        this.KEY_TOUCH_EXPLORATION_ENABLED = "touch_exploration_enabled";
        this.mFontSizeId = i;
        this.mIsPcMode = z;
        handleReceiveContent();
    }

    /* JADX INFO: renamed from: isIsKeyboardVisible, reason: from getter */
    public final boolean getIsIsKeyboardVisible() {
        return this.isIsKeyboardVisible;
    }

    protected final void setIsKeyboardVisible(boolean z) {
        this.isIsKeyboardVisible = z;
    }

    protected final String getMSearchText() {
        return this.mSearchText;
    }

    protected final void setMSearchText(String str) {
        this.mSearchText = str;
    }

    protected final String getMTitle() {
        return this.mTitle;
    }

    protected final void setMTitle(String str) {
        this.mTitle = str;
    }

    public final long getContentLength() {
        return this.contentLength;
    }

    protected final void setContentLength(long j) {
        this.contentLength = j;
    }

    protected final String getMRichTextData() {
        return this.mRichTextData;
    }

    protected final void setMRichTextData(String str) {
        this.mRichTextData = str;
    }

    protected final String getMRichTextOriginData() {
        return this.mRichTextOriginData;
    }

    protected final void setMRichTextOriginData(String str) {
        Intrinsics.checkNotNullParameter(str, "<set-?>");
        this.mRichTextOriginData = str;
    }

    public final Theme getEditorTheme() {
        return this.editorTheme;
    }

    protected final void setEditorTheme(Theme theme) {
        this.editorTheme = theme;
    }

    protected final Gson getMGson() {
        return this.mGson;
    }

    protected final void setMGson(Gson gson) {
        Intrinsics.checkNotNullParameter(gson, "<set-?>");
        this.mGson = gson;
    }

    /* JADX INFO: renamed from: isInDragMode, reason: from getter */
    public final boolean getIsInDragMode() {
        return this.isInDragMode;
    }

    public final void setInDragMode(boolean z) {
        this.isInDragMode = z;
    }

    public final boolean getMIsPcMode() {
        return this.mIsPcMode;
    }

    public final void setMIsPcMode(boolean z) {
        this.mIsPcMode = z;
    }

    public final void setOnRenderProcessGoneListener(WebRichEditorClient.onRenderProcessGoneListener onRenderProcessGoneListener) {
        this.mOnRenderProcessGoneListener = onRenderProcessGoneListener;
    }

    public final void initWebCore(Theme theme) {
        Intrinsics.checkNotNullParameter(theme, "theme");
        Logger.INSTANCE.d(TAG, "initWebCore, theme id: " + theme.getId());
        RichEditorWebView richEditorWebView = this.mWebCore;
        if (richEditorWebView != null) {
            richEditorWebView.setFeatureConfig("{\n\t\"select\": 1,\n    \"query\": 1,\n\t\"phrase\": 1,\n\t\"translate\": 1\n}");
            WebRichEditorClient webRichEditorClient = new WebRichEditorClient(richEditorWebView.getContext());
            webRichEditorClient.setOnRenderProcessGoneListener(this.mOnRenderProcessGoneListener);
            richEditorWebView.setWebViewClient(webRichEditorClient);
            WebSettings settings = richEditorWebView.getSettings();
            settings.setJavaScriptEnabled(true);
            settings.setTextZoom(100);
            settings.setJavaScriptCanOpenWindowsAutomatically(true);
            settings.setAllowFileAccess(false);
            settings.setDomStorageEnabled(true);
            settings.setAllowContentAccess(false);
            settings.setUseWideViewPort(true);
            settings.setBlockNetworkImage(false);
            settings.setMediaPlaybackRequiresUserGesture(false);
            WebView.setWebContentsDebuggingEnabled(false);
            richEditorWebView.loadUrl("file:///android_asset/noteeditor/index.html" + getInitParam(theme));
            richEditorWebView.setWebChromeClient(new WebChromeClient() { // from class: com.miui.hybirdeditor.WebRichEditor$initWebCore$1$2
                @Override // android.webkit.WebChromeClient
                public boolean onConsoleMessage(ConsoleMessage consoleMessage) {
                    Intrinsics.checkNotNullParameter(consoleMessage, "consoleMessage");
                    Logger.INSTANCE.v("WebRichEditor", "TextOnConsoleMessage: " + consoleMessage.message());
                    return super.onConsoleMessage(consoleMessage);
                }
            });
            richEditorWebView.addJavascriptInterface(this.mBridge, "rich_editor_interface");
        }
        initAccessibilityManager();
    }

    public final void resetScale() {
        RichEditorWebView richEditorWebView = this.mWebCore;
        if (richEditorWebView != null) {
            Intrinsics.checkNotNull(richEditorWebView);
            Logger.INSTANCE.d(TAG, "getScale: " + richEditorWebView.getScale());
            RichEditorWebView richEditorWebView2 = this.mWebCore;
            Intrinsics.checkNotNull(richEditorWebView2);
            richEditorWebView2.setInitialScale(100);
        }
    }

    private final String getInitParam(Theme theme) {
        Theme.EditorStyle editorStyle;
        if (theme == null) {
            return "";
        }
        this.editorTheme = theme;
        Rect rect = new Rect();
        Theme theme2 = this.editorTheme;
        Drawable drawable = (theme2 == null || (editorStyle = theme2.getEditorStyle(getContext())) == null) ? null : editorStyle.mForeground;
        if (drawable != null) {
            drawable.getPadding(rect);
        }
        float dimension = getContext().getResources().getDimension(R.dimen.note_editor_padding);
        int[] iArr = {(int) (rect.left + dimension), rect.top, (int) (rect.right + dimension), rect.bottom};
        Theme theme3 = this.editorTheme;
        if (theme3 != null) {
            rect = theme3.getForegroundBorderPadding(getContext());
            Intrinsics.checkNotNullExpressionValue(rect, "getForegroundBorderPadding(...)");
        }
        int[] iArr2 = {rect.left, rect.top, rect.right, rect.bottom};
        float textScale = getTextScale();
        int fontWeight = getFontWeight();
        StringCompanionObject stringCompanionObject = StringCompanionObject.INSTANCE;
        String str = String.format("?themeId=%s&textScale=%s&textPadding=%s&borderPadding=%s&fontWeight=%s", Arrays.copyOf(new Object[]{this.mGson.toJson(Integer.valueOf(this.mIsPcMode ? 0 : theme.getId())), this.mGson.toJson(Float.valueOf(textScale)), this.mGson.toJson(iArr), this.mGson.toJson(iArr2), this.mGson.toJson(Integer.valueOf(fontWeight))}, 5));
        Intrinsics.checkNotNullExpressionValue(str, "format(...)");
        return str;
    }

    public final int getFontWeight() {
        return (int) Math.min((DisplayUtils.getFontWeight(getContext()) / 33) + 1, 3.0d);
    }

    @Override // com.miui.notes.basefeature.noteedit.INoteRichEditor
    public Context getContext() {
        return BaseNoteApp.INSTANCE.getInstance();
    }

    @Override // com.miui.notes.basefeature.noteedit.INoteRichEditor
    public void setTextStyleStateListener(TextStyleStateListener listener) {
        this.mTextStyleStateListener = listener;
    }

    @Override // com.miui.notes.basefeature.noteedit.INoteRichEditor
    public void setDataId(long dataId) {
        long j = this.mDataId;
        if (j != dataId && j != -2) {
            onUndoRedoStateChanged(false, false);
        }
        this.mDataId = dataId;
    }

    /* JADX INFO: renamed from: getDataId, reason: from getter */
    public final long getMDataId() {
        return this.mDataId;
    }

    @Override // com.miui.notes.basefeature.noteedit.INoteRichEditor
    public void setFolderId(long folderId) {
        this.mFolderId = folderId;
    }

    @Override // com.miui.notes.basefeature.noteedit.INoteRichEditor
    public WebView getView() {
        return this.mWebCore;
    }

    private final float getTextScale() {
        float f = getContext().getResources().getDisplayMetrics().scaledDensity / getContext().getResources().getDisplayMetrics().density;
        float f2 = ResourceParser.TextAppearanceResources.FONT_SCALE[this.mFontSizeId];
        Logger.INSTANCE.d(TAG, "fontSystemScale " + f + " fontAppSettingScale " + f2);
        return f * f2;
    }

    public final void setButtonStateListener(ButtonStateListener listener) {
        this.mButtonStateListener = listener;
    }

    public final void insertImages(boolean needWithPos, Uri... images) {
        Intrinsics.checkNotNullParameter(images, "images");
        ImageInsertTask imageInsertTask = this.mImageInsertTask;
        if (imageInsertTask != null) {
            Intrinsics.checkNotNull(imageInsertTask);
            imageInsertTask.restoreAndCancel();
        }
        ImageInsertTask imageInsertTask2 = new ImageInsertTask(null, this, needWithPos);
        this.mImageInsertTask = imageInsertTask2;
        Intrinsics.checkNotNull(imageInsertTask2);
        imageInsertTask2.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, Arrays.copyOf(images, images.length));
        Logger.INSTANCE.d(TAG, "ImageInsertTask execute");
    }

    public final void insertContent(String text) {
        Logger.INSTANCE.d(TAG, "insertContent");
        replaceDoc(this.mRichTextData + text);
        highlightAllBackgroundSection();
        AiActionUtil.asyncNotifyResult(0);
    }

    @Override // com.miui.notes.basefeature.noteedit.INoteRichEditor
    public void pasteMarkdown(String text, String title) {
        Logger.INSTANCE.d(TAG, "pasteMarkdown");
        if (this.mWebCore != null) {
            setData(null, title);
            RichEditorWebView richEditorWebView = this.mWebCore;
            Intrinsics.checkNotNull(richEditorWebView);
            richEditorWebView.evaluateJavascript("javascript:pasteMarkdown(" + this.mGson.toJson(text) + ")", null);
        }
    }

    public final void onThemeChanged(Theme theme, long lastModifiedTIme) {
        Logger.INSTANCE.d(TAG, "onThemeChanged");
        setTheme(theme, lastModifiedTIme);
    }

    @Override // com.miui.notes.basefeature.noteedit.INoteRichEditor
    public void setData(String richTextData, String title, String queryContent, Boolean isNeedSetData, ArrayList<String> highLightKeyWords) {
        Logger.INSTANCE.d(TAG, "setData");
        String strConvertToWeb = NoteSchema.convertToWeb(richTextData);
        this.mRichTextData = strConvertToWeb;
        Intrinsics.checkNotNull(strConvertToWeb);
        this.mRichTextOriginData = strConvertToWeb;
        boolean z = true;
        this.mDataFedToFrontend = true;
        this.mSearchText = queryContent;
        this.mTitle = title;
        ArrayList<String> arrayList = highLightKeyWords;
        if (arrayList != null && !arrayList.isEmpty()) {
            z = false;
        }
        boolean z2 = !z;
        Logger.INSTANCE.i(TAG, "setData richTextData: " + (strConvertToWeb != null ? Integer.valueOf(strConvertToWeb.length()) : null) + " title: " + (title != null ? Integer.valueOf(title.length()) : null));
        Logger.INSTANCE.d(TAG, "setData web mSearchText :****** useHighLightKeyWords :" + z2);
        RichEditorWebView richEditorWebView = this.mWebCore;
        if (richEditorWebView != null) {
            richEditorWebView.evaluateJavascript("javascript:setData(" + this.mGson.toJson(strConvertToWeb) + ", " + this.mGson.toJson(title) + ", " + this.mGson.toJson(!z ? highLightKeyWords : queryContent) + ", " + this.mGson.toJson(isNeedSetData) + ")", null);
        }
    }

    @Override // com.miui.notes.basefeature.noteedit.INoteRichEditor
    public void setCount(long count) {
        this.contentLength = count;
    }

    /* JADX WARN: Code restructure failed: missing block: B:13:0x0039, code lost:
    
        if (com.miui.notes.theme.util.ResourceManager.isShowTimeTheme(r9.getId()) == false) goto L32;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void setTheme(com.miui.richeditor.theme.Theme r9, long r10) {
        /*
            Method dump skipped, instruction units count: 270
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.miui.hybirdeditor.WebRichEditor.setTheme(com.miui.richeditor.theme.Theme, long):void");
    }

    @Override // com.miui.notes.basefeature.noteedit.INoteRichEditor
    public void updateFontSizeId(int id) {
        Logger.INSTANCE.d(TAG, "updateFontSizeId:" + id);
        this.mFontSizeId = id;
        this.mTitleTextSize = getContext().getResources().getDimensionPixelSize(R.dimen.note_edit_title_font_size);
        this.mFontTextSize = DisplayUtils.sp2px(getContext(), ResourceParser.TextAppearanceResources.getEditFontSpSize(getContext(), this.mFontSizeId));
        setTextSize(getTextScale());
    }

    public final void updateQueryContent(String queryContent, ArrayList<String> highLightKeyWords) {
        Logger.INSTANCE.d(TAG, "updateQueryContent");
        this.mSearchText = queryContent;
        ArrayList<String> arrayList = highLightKeyWords;
        boolean z = arrayList == null || arrayList.isEmpty();
        Logger.INSTANCE.d(TAG, "updateQueryContent web mSearchText :*** useHighLightKeyWords :" + (!z) + "  mGson.toJson(highLightKeyWords) :");
        RichEditorWebView richEditorWebView = this.mWebCore;
        if (richEditorWebView != null) {
            richEditorWebView.evaluateJavascript("javascript:updateQueryContent(" + this.mGson.toJson(!z ? highLightKeyWords : queryContent) + ")", null);
        }
    }

    public final void updateExtraInto(DataExtraInfo extraInfo) {
        Intrinsics.checkNotNullParameter(extraInfo, "extraInfo");
        RichEditorWebView richEditorWebView = this.mWebCore;
        if (richEditorWebView != null) {
            richEditorWebView.evaluateJavascript("javascript:updateExtraInto(" + this.mGson.toJson(extraInfo.timeString) + "," + extraInfo.time + "," + extraInfo.textCount + "," + this.mGson.toJson(extraInfo.remindInfo) + "," + extraInfo.isHide + ")", null);
        }
    }

    public final void updateViewState(boolean canSelect, boolean canQuery, boolean canPhrase, boolean canTranslate, String selectData, String htmlData) {
        RichEditorWebView richEditorWebView = this.mWebCore;
        if (richEditorWebView != null) {
            richEditorWebView.updateViewState(canSelect, canQuery, canPhrase, canTranslate, selectData, htmlData);
        }
    }

    public final void selectAllText() {
        RichEditorWebView richEditorWebView = this.mWebCore;
        if (richEditorWebView != null) {
            richEditorWebView.evaluateJavascript("javascript:selectAllText()", null);
        }
    }

    @Override // com.miui.notes.basefeature.noteedit.INoteRichEditor
    public String getTitle() {
        return this.mTitle;
    }

    public final void resetRichTextData(String richTextData) {
        Intrinsics.checkNotNullParameter(richTextData, "richTextData");
        Logger.INSTANCE.d(TAG, "resetRichTextDataAndTitle richTextData length: " + richTextData.length());
        this.mRichTextData = richTextData;
        this.mRichTextOriginData = richTextData;
        this.mDataFedToFrontend = true;
    }

    @Override // com.miui.notes.basefeature.noteedit.INoteRichEditor
    public CharSequence getRichTextData() {
        if (this.mRichTextData == null) {
            this.mRichTextData = NoteSchema.convertToAncient(this.mRichTextOriginData);
        }
        Logger logger = Logger.INSTANCE;
        String str = this.mRichTextData;
        logger.d(TAG, "getRichTextData size:" + (str != null ? Integer.valueOf(str.length()) : null));
        return this.mRichTextData;
    }

    public final void setFontStyle(MiFontStyle style) {
        RichEditorWebView richEditorWebView = this.mWebCore;
        if (richEditorWebView != null) {
            richEditorWebView.evaluateJavascript("javascript:setFontStyle(" + this.mGson.toJson(style) + ")", null);
        }
    }

    /* JADX INFO: renamed from: getTextSize, reason: from getter */
    public final float getMFontTextSize() {
        return this.mFontTextSize;
    }

    public final void setTextSize(float f) {
        RichEditorWebView richEditorWebView = this.mWebCore;
        if (richEditorWebView != null) {
            richEditorWebView.evaluateJavascript("javascript:setTextSize(" + f + ")", null);
        }
    }

    @Override // com.miui.notes.basefeature.noteedit.INoteRichEditor
    public void setEnableEdit(boolean enableEdit) {
        Logger.INSTANCE.d(TAG, "setEnableEdit: " + enableEdit);
        RichEditorWebView richEditorWebView = this.mWebCore;
        if (richEditorWebView != null) {
            richEditorWebView.evaluateJavascript("javascript:setEnableEdit(" + enableEdit + ")", null);
        }
    }

    @Override // com.miui.notes.basefeature.noteedit.INoteRichEditor
    public void undo() {
        RichEditorWebView richEditorWebView = this.mWebCore;
        if (richEditorWebView != null) {
            richEditorWebView.evaluateJavascript("javascript:undo()", null);
        }
    }

    @Override // com.miui.notes.basefeature.noteedit.INoteRichEditor
    public void redo() {
        RichEditorWebView richEditorWebView = this.mWebCore;
        if (richEditorWebView != null) {
            richEditorWebView.evaluateJavascript("javascript:redo()", null);
        }
    }

    @Override // com.miui.notes.basefeature.noteedit.INoteRichEditor
    public void done() {
        long jCurrentTimeMillis = System.currentTimeMillis();
        RichEditorWebView richEditorWebView = this.mWebCore;
        if (richEditorWebView != null) {
            richEditorWebView.clearFocus();
        }
        RichEditorWebView richEditorWebView2 = this.mWebCore;
        if (richEditorWebView2 != null) {
            richEditorWebView2.evaluateJavascript("javascript:done()", null);
        }
        Logger.INSTANCE.d(TAG, "done,ts cost:" + (System.currentTimeMillis() - jCurrentTimeMillis));
    }

    @Override // com.miui.notes.basefeature.noteedit.INoteRichEditor
    public boolean canAddNewElement(int newAddContentLength) {
        return this.contentLength + ((long) newAddContentLength) <= 20000;
    }

    @Override // com.miui.notes.basefeature.noteedit.INoteRichEditor
    public void setEnableToggleAlignment(boolean enable) {
        RichEditorWebView richEditorWebView = this.mWebCore;
        if (richEditorWebView != null) {
            richEditorWebView.evaluateJavascript("javascript:setEnableToggleAlignment(" + this.mGson.toJson(Boolean.valueOf(enable)) + ")", null);
        }
    }

    @Override // com.miui.notes.basefeature.noteedit.INoteRichEditor
    public void setH1() {
        RichEditorWebView richEditorWebView = this.mWebCore;
        if (richEditorWebView != null) {
            richEditorWebView.evaluateJavascript("javascript:toggleH1()", null);
        }
    }

    @Override // com.miui.notes.basefeature.noteedit.INoteRichEditor
    public void setH2() {
        RichEditorWebView richEditorWebView = this.mWebCore;
        if (richEditorWebView != null) {
            richEditorWebView.evaluateJavascript("javascript:toggleH2()", null);
        }
    }

    @Override // com.miui.notes.basefeature.noteedit.INoteRichEditor
    public void setH3() {
        RichEditorWebView richEditorWebView = this.mWebCore;
        if (richEditorWebView != null) {
            richEditorWebView.evaluateJavascript("javascript:toggleH3()", null);
        }
    }

    @Override // com.miui.notes.basefeature.noteedit.INoteRichEditor
    public void setDeline() {
        RichEditorWebView richEditorWebView = this.mWebCore;
        if (richEditorWebView != null) {
            richEditorWebView.evaluateJavascript("javascript:toggleDelLine()", null);
        }
    }

    @Override // com.miui.notes.basefeature.noteedit.INoteRichEditor
    public void setOrderList() {
        RichEditorWebView richEditorWebView = this.mWebCore;
        if (richEditorWebView != null) {
            richEditorWebView.evaluateJavascript("javascript:toggleOrderList()", null);
        }
    }

    @Override // com.miui.notes.basefeature.noteedit.INoteRichEditor
    public void setQuote() {
        RichEditorWebView richEditorWebView = this.mWebCore;
        if (richEditorWebView != null) {
            richEditorWebView.evaluateJavascript("javascript:toggleQuote()", null);
        }
    }

    @Override // com.miui.notes.basefeature.noteedit.INoteRichEditor
    public void setHr() {
        RichEditorWebView richEditorWebView = this.mWebCore;
        if (richEditorWebView != null) {
            richEditorWebView.evaluateJavascript("javascript:addHr()", null);
        }
    }

    @Override // com.miui.notes.basefeature.noteedit.INoteRichEditor
    public void setIndent() {
        RichEditorWebView richEditorWebView = this.mWebCore;
        if (richEditorWebView != null) {
            richEditorWebView.evaluateJavascript("javascript:indentTextBlock()", null);
        }
    }

    @Override // com.miui.notes.basefeature.noteedit.INoteRichEditor
    public void setUndent() {
        RichEditorWebView richEditorWebView = this.mWebCore;
        if (richEditorWebView != null) {
            richEditorWebView.evaluateJavascript("javascript:undentTextBlock()", null);
        }
    }

    public final void setAigcData(String data) {
        this.currentAigcData = data;
    }

    @Override // com.miui.notes.basefeature.noteedit.INoteRichEditor
    public void getMarkdown(final OnWriteFinishedListener onWriteFinishedListener) {
        Intrinsics.checkNotNullParameter(onWriteFinishedListener, "onWriteFinishedListener");
        String str = this.currentAigcData;
        if (str == null) {
            str = "";
        }
        String strQuote = JSONObject.quote(str);
        RichEditorWebView richEditorWebView = this.mWebCore;
        if (richEditorWebView != null) {
            richEditorWebView.evaluateJavascript("javascript:getMarkdown(" + strQuote + ")", new ValueCallback() { // from class: com.miui.hybirdeditor.WebRichEditor$$ExternalSyntheticLambda6
                @Override // android.webkit.ValueCallback
                public final void onReceiveValue(Object obj) {
                    WebRichEditor.getMarkdown$lambda$5(this.f$0, onWriteFinishedListener, (String) obj);
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void getMarkdown$lambda$5(final WebRichEditor webRichEditor, final OnWriteFinishedListener onWriteFinishedListener, final String str) {
        AsyncTask.execute(new Runnable() { // from class: com.miui.hybirdeditor.WebRichEditor$$ExternalSyntheticLambda4
            @Override // java.lang.Runnable
            public final void run() {
                WebRichEditor.getMarkdown$lambda$5$lambda$4(str, webRichEditor, onWriteFinishedListener);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void getMarkdown$lambda$5$lambda$4(String str, WebRichEditor webRichEditor, OnWriteFinishedListener onWriteFinishedListener) {
        String strReplace;
        Logger.INSTANCE.d(TAG, "write web string to markdown file");
        int length = str.length();
        try {
            strReplace = (String) webRichEditor.mGson.fromJson(str, String.class);
        } catch (Exception e) {
            Logger.INSTANCE.e(TAG, "onReceiveValue error: " + e.getMessage());
            e.printStackTrace();
            if (length > 1) {
                Intrinsics.checkNotNull(str);
                String strSubstring = str.substring(1, length - 1);
                Intrinsics.checkNotNullExpressionValue(strSubstring, "substring(...)");
                strReplace = new Regex("\\\\n\\\\n").replace(strSubstring, StringUtils.LF);
            } else {
                strReplace = null;
            }
        }
        String str2 = webRichEditor.mTitle;
        String str3 = str2;
        if (str3 != null && !StringsKt.isBlank(str3)) {
            strReplace = "# " + str2 + SSEConst.MSG_SPLIT + strReplace;
        } else {
            Intrinsics.checkNotNull(strReplace);
        }
        String str4 = "markDown" + System.currentTimeMillis() + ".md";
        webRichEditor.writeTxtToFile(strReplace, Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).getAbsolutePath(), str4);
        onWriteFinishedListener.onWriteFinished(str4);
    }

    @Override // com.miui.notes.basefeature.noteedit.INoteRichEditor
    public void getMarkdown(final OnWriteFinishedListener onWriteFinishedListener, final String fileName) {
        Intrinsics.checkNotNullParameter(onWriteFinishedListener, "onWriteFinishedListener");
        Intrinsics.checkNotNullParameter(fileName, "fileName");
        String str = this.currentAigcData;
        if (str == null) {
            str = "";
        }
        String strQuote = JSONObject.quote(str);
        RichEditorWebView richEditorWebView = this.mWebCore;
        if (richEditorWebView != null) {
            richEditorWebView.evaluateJavascript("javascript:getMarkdown(" + strQuote + ")", new ValueCallback() { // from class: com.miui.hybirdeditor.WebRichEditor$$ExternalSyntheticLambda3
                @Override // android.webkit.ValueCallback
                public final void onReceiveValue(Object obj) {
                    WebRichEditor.getMarkdown$lambda$7(this.f$0, fileName, onWriteFinishedListener, (String) obj);
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void getMarkdown$lambda$7(final WebRichEditor webRichEditor, final String str, final OnWriteFinishedListener onWriteFinishedListener, final String str2) {
        AsyncTask.execute(new Runnable() { // from class: com.miui.hybirdeditor.WebRichEditor$$ExternalSyntheticLambda5
            @Override // java.lang.Runnable
            public final void run() {
                WebRichEditor.getMarkdown$lambda$7$lambda$6(str2, webRichEditor, str, onWriteFinishedListener);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void getMarkdown$lambda$7$lambda$6(String str, WebRichEditor webRichEditor, String str2, OnWriteFinishedListener onWriteFinishedListener) {
        String strReplace;
        Logger.INSTANCE.d(TAG, "write web string to markdown file");
        int length = str.length();
        try {
            strReplace = (String) webRichEditor.mGson.fromJson(str, String.class);
        } catch (Exception e) {
            Logger.INSTANCE.e(TAG, "onReceiveValue error: " + e.getMessage());
            e.printStackTrace();
            if (length > 1) {
                Intrinsics.checkNotNull(str);
                String strSubstring = str.substring(1, length - 1);
                Intrinsics.checkNotNullExpressionValue(strSubstring, "substring(...)");
                strReplace = new Regex("\\\\n\\\\n").replace(strSubstring, StringUtils.LF);
            } else {
                strReplace = null;
            }
        }
        String str3 = webRichEditor.mTitle;
        String str4 = str3;
        if (str4 != null && !StringsKt.isBlank(str4)) {
            strReplace = "# " + str3 + SSEConst.MSG_SPLIT + strReplace;
        } else {
            Intrinsics.checkNotNull(strReplace);
        }
        webRichEditor.writeTxtToFile(strReplace, Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).getAbsolutePath(), str2);
        onWriteFinishedListener.onWriteFinished(str2);
    }

    private final void writeTxtToFile(String strcontent, String filePath, String fileName) {
        try {
            File file = new File(filePath, fileName);
            if (!file.exists()) {
                file.getParentFile().mkdirs();
                file.createNewFile();
            }
            RandomAccessFile randomAccessFile = new RandomAccessFile(file, "rwd");
            randomAccessFile.seek(file.length());
            byte[] bytes = strcontent.getBytes(Charsets.UTF_8);
            Intrinsics.checkNotNullExpressionValue(bytes, "getBytes(...)");
            randomAccessFile.write(bytes);
            randomAccessFile.close();
        } catch (Exception unused) {
        }
    }

    @Override // com.miui.notes.basefeature.noteedit.INoteRichEditor
    public void setFontBold() {
        RichEditorWebView richEditorWebView = this.mWebCore;
        if (richEditorWebView != null) {
            richEditorWebView.evaluateJavascript("javascript:toggleFontBold()", null);
        }
    }

    @Override // com.miui.notes.basefeature.noteedit.INoteRichEditor
    public void setFontItalic() {
        RichEditorWebView richEditorWebView = this.mWebCore;
        if (richEditorWebView != null) {
            richEditorWebView.evaluateJavascript("javascript:toggleFontItalic()", null);
        }
    }

    @Override // com.miui.notes.basefeature.noteedit.INoteRichEditor
    public void setUnderline() {
        RichEditorWebView richEditorWebView = this.mWebCore;
        if (richEditorWebView != null) {
            richEditorWebView.evaluateJavascript("javascript:toggleUnderline()", null);
        }
    }

    @Override // com.miui.notes.basefeature.noteedit.INoteRichEditor
    public void setBgHighLight() {
        RichEditorWebView richEditorWebView = this.mWebCore;
        if (richEditorWebView != null) {
            richEditorWebView.evaluateJavascript("javascript:toggleBgHighLight()", null);
        }
    }

    @Override // com.miui.notes.basefeature.noteedit.INoteRichEditor
    public void setAlignCenter() {
        RichEditorWebView richEditorWebView = this.mWebCore;
        if (richEditorWebView != null) {
            richEditorWebView.evaluateJavascript("javascript:toggleAlignCenter()", null);
        }
    }

    @Override // com.miui.notes.basefeature.noteedit.INoteRichEditor
    public void setAlignNormal() {
        RichEditorWebView richEditorWebView = this.mWebCore;
        if (richEditorWebView != null) {
            richEditorWebView.evaluateJavascript("javascript:setAlignLeft()", null);
        }
    }

    @Override // com.miui.notes.basefeature.noteedit.INoteRichEditor
    public void setAlignRight() {
        RichEditorWebView richEditorWebView = this.mWebCore;
        if (richEditorWebView != null) {
            richEditorWebView.evaluateJavascript("javascript:toggleAlignRight()", null);
        }
    }

    @Override // com.miui.notes.basefeature.noteedit.INoteRichEditor
    public void setCheckbox() {
        RichEditorWebView richEditorWebView = this.mWebCore;
        if (richEditorWebView != null) {
            richEditorWebView.evaluateJavascript("javascript:toggleCheckbox()", null);
        }
    }

    @Override // com.miui.notes.basefeature.noteedit.INoteRichEditor
    public void setEditorBullet() {
        RichEditorWebView richEditorWebView = this.mWebCore;
        if (richEditorWebView != null) {
            richEditorWebView.evaluateJavascript("javascript:toggleEditorBullet()", null);
        }
    }

    @Override // com.miui.notes.basefeature.noteedit.INoteRichEditor
    public void setListBullet() {
        RichEditorWebView richEditorWebView = this.mWebCore;
        if (richEditorWebView != null) {
            richEditorWebView.evaluateJavascript("javascript:toggleListBullet()", null);
        }
    }

    public final void increaseFontSize() {
        RichEditorWebView richEditorWebView = this.mWebCore;
        if (richEditorWebView != null) {
            richEditorWebView.evaluateJavascript("javascript:increaseFontSize()", null);
        }
    }

    public final void reduceFontSize() {
        RichEditorWebView richEditorWebView = this.mWebCore;
        if (richEditorWebView != null) {
            richEditorWebView.evaluateJavascript("javascript:reduceFontSize()", null);
        }
    }

    @Override // com.miui.notes.basefeature.noteedit.INoteRichEditor
    public void setFontSizeLevel(int sizeLevel) {
        RichEditorWebView richEditorWebView = this.mWebCore;
        if (richEditorWebView != null) {
            richEditorWebView.evaluateJavascript("javascript:setFontSizeLevel(" + this.mGson.toJson(Integer.valueOf(sizeLevel)) + ")", null);
        }
    }

    @Override // com.miui.notes.basefeature.noteedit.INoteRichEditor
    public boolean canAlignNormal() {
        TextStatusBean textStatusBean = this.mTextStatusBean;
        if (textStatusBean == null) {
            return true;
        }
        Intrinsics.checkNotNull(textStatusBean);
        if (!textStatusBean.isCenterChecked) {
            TextStatusBean textStatusBean2 = this.mTextStatusBean;
            Intrinsics.checkNotNull(textStatusBean2);
            if (!textStatusBean2.isRightChecked) {
                return false;
            }
        }
        return true;
    }

    @Override // com.miui.notes.basefeature.noteedit.INoteRichEditor
    public boolean canAlignCenter() {
        TextStatusBean textStatusBean = this.mTextStatusBean;
        if (textStatusBean == null) {
            return true;
        }
        Intrinsics.checkNotNull(textStatusBean);
        if (textStatusBean.isBulletChecked) {
            return false;
        }
        TextStatusBean textStatusBean2 = this.mTextStatusBean;
        Intrinsics.checkNotNull(textStatusBean2);
        if (textStatusBean2.isAllCheckList) {
            return false;
        }
        TextStatusBean textStatusBean3 = this.mTextStatusBean;
        Intrinsics.checkNotNull(textStatusBean3);
        return !textStatusBean3.isAllOrderList;
    }

    @Override // com.miui.notes.basefeature.noteedit.INoteRichEditor
    public boolean canAlignRight() {
        TextStatusBean textStatusBean = this.mTextStatusBean;
        if (textStatusBean == null) {
            return true;
        }
        Intrinsics.checkNotNull(textStatusBean);
        if (textStatusBean.isBulletChecked) {
            return false;
        }
        TextStatusBean textStatusBean2 = this.mTextStatusBean;
        Intrinsics.checkNotNull(textStatusBean2);
        if (textStatusBean2.isAllCheckList) {
            return false;
        }
        TextStatusBean textStatusBean3 = this.mTextStatusBean;
        Intrinsics.checkNotNull(textStatusBean3);
        return !textStatusBean3.isAllOrderList;
    }

    @Override // com.miui.notes.basefeature.noteedit.INoteRichEditor
    public boolean requestViewFocus() {
        RichEditorWebView richEditorWebView = this.mWebCore;
        if (richEditorWebView != null) {
            return richEditorWebView.requestFocus();
        }
        return false;
    }

    @Override // com.miui.notes.basefeature.noteedit.INoteRichEditor
    public void notifySaveData() {
        EditorActionListener editorActionListener = this.actionListener;
        if (editorActionListener != null) {
            editorActionListener.onAction(2);
        }
    }

    @Override // com.miui.notes.basefeature.noteedit.INoteRichEditor
    public void clearFocus() {
        RichEditorWebView richEditorWebView = this.mWebCore;
        if (richEditorWebView != null) {
            richEditorWebView.clearFocus();
        }
    }

    @Override // com.miui.notes.basefeature.noteedit.INoteRichEditor
    public boolean requestViewFocus(int direction) {
        RichEditorWebView richEditorWebView = this.mWebCore;
        if (richEditorWebView != null) {
            return richEditorWebView.requestFocus(direction);
        }
        return false;
    }

    public final void emptyNoteFocus() {
        RichEditorWebView richEditorWebView = this.mWebCore;
        if (richEditorWebView != null) {
            richEditorWebView.evaluateJavascript("javascript:emptyNoteFocus()", null);
        }
    }

    public final void editImageStart() {
        RichEditorWebView richEditorWebView = this.mWebCore;
        if (richEditorWebView != null) {
            richEditorWebView.evaluateJavascript("javascript:editImageStart()", null);
        }
    }

    public final void editImageEnd(String fileName, String oldField) {
        RichEditorWebView richEditorWebView = this.mWebCore;
        if (richEditorWebView != null) {
            richEditorWebView.evaluateJavascript("javascript:editImageEnd(" + this.mGson.toJson(fileName) + "," + this.mGson.toJson(oldField) + ")", null);
        }
    }

    @Deprecated(message = "")
    public final void insertImageStart() {
        RichEditorWebView richEditorWebView = this.mWebCore;
        if (richEditorWebView != null) {
            richEditorWebView.evaluateJavascript("javascript:insertImageStart()", null);
        }
    }

    public final void insertImageEnd(String[] imageFileNames, boolean needWithPos) {
        Logger.INSTANCE.d(TAG, "insertImageEnd: " + this.mGson.toJson(imageFileNames));
        if (needWithPos) {
            RichEditorWebView richEditorWebView = this.mWebCore;
            if (richEditorWebView != null) {
                StringCompanionObject stringCompanionObject = StringCompanionObject.INSTANCE;
                String str = String.format("javascript:insertImageWithPos(%s)", Arrays.copyOf(new Object[]{this.mGson.toJson(imageFileNames)}, 1));
                Intrinsics.checkNotNullExpressionValue(str, "format(...)");
                richEditorWebView.evaluateJavascript(str, null);
            }
        } else {
            RichEditorWebView richEditorWebView2 = this.mWebCore;
            if (richEditorWebView2 != null) {
                StringCompanionObject stringCompanionObject2 = StringCompanionObject.INSTANCE;
                String str2 = String.format("javascript:insertImageEnd(%s)", Arrays.copyOf(new Object[]{this.mGson.toJson(imageFileNames)}, 1));
                Intrinsics.checkNotNullExpressionValue(str2, "format(...)");
                richEditorWebView2.evaluateJavascript(str2, null);
            }
        }
        AiActionUtil.asyncNotifyResult(0);
    }

    public final void deleteEmbedElement() {
        RichEditorWebView richEditorWebView = this.mWebCore;
        if (richEditorWebView != null) {
            richEditorWebView.evaluateJavascript("javascript:deleteEmbedElement()", null);
        }
    }

    public final void changeShowBigImg() {
        RichEditorWebView richEditorWebView = this.mWebCore;
        if (richEditorWebView != null) {
            richEditorWebView.evaluateJavascript("javascript:changeShowBigImg()", null);
        }
    }

    public final void annotateImageStart() {
        RichEditorWebView richEditorWebView = this.mWebCore;
        if (richEditorWebView != null) {
            richEditorWebView.evaluateJavascript("javascript:annotateImageStart()", null);
        }
    }

    public final void annotateImageEnd(String annotation) {
        RichEditorWebView richEditorWebView = this.mWebCore;
        if (richEditorWebView != null) {
            StringCompanionObject stringCompanionObject = StringCompanionObject.INSTANCE;
            String str = String.format("javascript:annotateImageEnd(%s)", Arrays.copyOf(new Object[]{this.mGson.toJson(annotation)}, 1));
            Intrinsics.checkNotNullExpressionValue(str, "format(...)");
            richEditorWebView.evaluateJavascript(str, null);
        }
    }

    public final void insertAudioStart() {
        Logger.INSTANCE.i(TAG, "insert audio start");
        RichEditorWebView richEditorWebView = this.mWebCore;
        if (richEditorWebView != null) {
            richEditorWebView.evaluateJavascript("javascript:insertAudioStart()", null);
        }
        TempAudio.CONTENT = "";
    }

    public final void insertAudioEnd(String fileName) {
        Logger.INSTANCE.i(TAG, "insert audio to editor");
        RichEditorWebView richEditorWebView = this.mWebCore;
        if (richEditorWebView != null) {
            richEditorWebView.evaluateJavascript("javascript:insertAudioEnd(" + this.mGson.toJson(fileName) + ")", null);
        }
    }

    public final void destroy() {
        cleanupAccessibilityListener();
        RichEditorWebView richEditorWebView = this.mWebCore;
        if (richEditorWebView != null) {
            richEditorWebView.getSettings().setJavaScriptEnabled(false);
            richEditorWebView.stopLoading();
            richEditorWebView.clearHistory();
            richEditorWebView.removeAllViewsInLayout();
            richEditorWebView.removeAllViews();
            richEditorWebView.setWebChromeClient(null);
            richEditorWebView.clearCache(true);
            richEditorWebView.destroy();
            Logger.INSTANCE.d(TAG, "destroyed");
        }
    }

    public final void appendAudioText(String newText, boolean isPartialResult) {
        RichEditorWebView richEditorWebView = this.mWebCore;
        if (richEditorWebView != null) {
            richEditorWebView.evaluateJavascript("javascript:appendAudioText(" + this.mGson.toJson(newText) + "," + isPartialResult + ")", null);
        }
    }

    public final void stopPlayingMedia() {
        RichEditorWebView richEditorWebView = this.mWebCore;
        if (richEditorWebView != null) {
            richEditorWebView.evaluateJavascript("javascript:stopPlayingMedia()", null);
        }
    }

    public final void pauseVoice() {
        RichEditorWebView richEditorWebView = this.mWebCore;
        if (richEditorWebView != null) {
            richEditorWebView.evaluateJavascript("javascript:pauseVoice()", null);
        }
    }

    public final void onMeetingTextResult(String newText, boolean isPartialResult) {
        appendAudioText(newText, isPartialResult);
    }

    public final void onMeetingTextFull(String newText) {
        RichEditorWebView richEditorWebView = this.mWebCore;
        if (richEditorWebView != null) {
            richEditorWebView.evaluateJavascript("javascript:appendMeetingTextFull(" + this.mGson.toJson(newText) + ")", null);
        }
    }

    public final void hidePopMenu() {
        RichEditorWebView richEditorWebView = this.mWebCore;
        if (richEditorWebView != null) {
            richEditorWebView.evaluateJavascript("javascript:hidePopMenu()", null);
        }
    }

    public final void createShareImage() {
        RichEditorWebView richEditorWebView = this.mWebCore;
        if (richEditorWebView != null) {
            richEditorWebView.evaluateJavascript("javascript:createShareImage()", null);
        }
    }

    public final void pasteHtmlText(String htmlText) {
        RichEditorWebView richEditorWebView = this.mWebCore;
        if (richEditorWebView != null) {
            richEditorWebView.evaluateJavascript("javascript:pasteHtmlText(" + this.mGson.toJson(htmlText) + ")", null);
        }
    }

    public final void pastePlainText(String plainText) {
        RichEditorWebView richEditorWebView = this.mWebCore;
        if (richEditorWebView != null) {
            richEditorWebView.evaluateJavascript("javascript:pastePlainText(" + this.mGson.toJson(plainText) + ")", null);
        }
    }

    public final void onTitleChanged(String newTitle) {
        this.mTitle = newTitle;
        EditorActionListener editorActionListener = this.actionListener;
        if (editorActionListener != null) {
            editorActionListener.onAction(1);
        }
    }

    public final boolean onSaveData(String newRichTextData) {
        Intrinsics.checkNotNullParameter(newRichTextData, "newRichTextData");
        long jUserEditElapsedMs = userEditElapsedMs();
        String str = newRichTextData;
        EmptyOverwriteGuard.Decision decisionDecide$default = EmptyOverwriteGuard.decide$default(str.length() == 0, this.mRichTextOriginData.length() == 0, this.mDataFedToFrontend, jUserEditElapsedMs, 0L, 16, null);
        if (!decisionDecide$default.getAccepted()) {
            Logger.INSTANCE.e(TAG, "onSaveData: reject empty overwrite, reason=" + decisionDecide$default.name() + ", current size=" + this.mRichTextOriginData.length() + ", userEditAgo=" + jUserEditElapsedMs);
            return false;
        }
        if (str.length() == 0 && this.mRichTextOriginData.length() > 0) {
            Logger.INSTANCE.i(TAG, "onSaveData: accept empty, reason=" + decisionDecide$default.name() + ", previous size=" + this.mRichTextOriginData.length() + ", userEditAgo=" + jUserEditElapsedMs);
        }
        this.mRichTextOriginData = newRichTextData;
        this.mRichTextData = null;
        return true;
    }

    private final long userEditElapsedMs() {
        RichEditorWebView richEditorWebView = this.mWebCore;
        long mLastUserEditTime = richEditorWebView != null ? richEditorWebView.getMLastUserEditTime() : 0L;
        if (mLastUserEditTime <= 0) {
            return -1L;
        }
        return SystemClock.elapsedRealtime() - mLastUserEditTime;
    }

    public final void onFrontendPagePrepared() {
        Logger.INSTANCE.d(TAG, "onFrontendPagePrepared, reset dataFed flag");
        this.mDataFedToFrontend = false;
    }

    public final boolean getEditorTextIsEmpty() {
        return this.mRichTextOriginData.length() == 0;
    }

    public final void onAiTextStyleNeedChanged(boolean canUse) {
        TextStyleStateListener textStyleStateListener = this.mTextStyleStateListener;
        if (textStyleStateListener == null) {
            return;
        }
        Intrinsics.checkNotNull(textStyleStateListener);
        textStyleStateListener.isAiCanUse(canUse);
    }

    /* JADX WARN: Code duplicated, block: B:12:0x0130  */
    public final void onTextStyleNeedChanged() {
        TextStyleStateListener textStyleStateListener;
        boolean z;
        if (this.mTextStatusBean == null || (textStyleStateListener = this.mTextStyleStateListener) == null) {
            return;
        }
        Intrinsics.checkNotNull(textStyleStateListener);
        TextStatusBean textStatusBean = this.mTextStatusBean;
        Intrinsics.checkNotNull(textStatusBean);
        textStyleStateListener.onH1(textStatusBean.isAllH1);
        TextStyleStateListener textStyleStateListener2 = this.mTextStyleStateListener;
        Intrinsics.checkNotNull(textStyleStateListener2);
        TextStatusBean textStatusBean2 = this.mTextStatusBean;
        Intrinsics.checkNotNull(textStatusBean2);
        textStyleStateListener2.onH2(textStatusBean2.isAllH2);
        TextStyleStateListener textStyleStateListener3 = this.mTextStyleStateListener;
        Intrinsics.checkNotNull(textStyleStateListener3);
        TextStatusBean textStatusBean3 = this.mTextStatusBean;
        Intrinsics.checkNotNull(textStatusBean3);
        textStyleStateListener3.onH3(textStatusBean3.isAllH3);
        TextStyleStateListener textStyleStateListener4 = this.mTextStyleStateListener;
        Intrinsics.checkNotNull(textStyleStateListener4);
        TextStatusBean textStatusBean4 = this.mTextStatusBean;
        Intrinsics.checkNotNull(textStatusBean4);
        textStyleStateListener4.onListBullet(textStatusBean4.isAllOrderList);
        TextStyleStateListener textStyleStateListener5 = this.mTextStyleStateListener;
        Intrinsics.checkNotNull(textStyleStateListener5);
        TextStatusBean textStatusBean5 = this.mTextStatusBean;
        Intrinsics.checkNotNull(textStatusBean5);
        textStyleStateListener5.onStrikeThrough(textStatusBean5.isAllDelLine);
        TextStyleStateListener textStyleStateListener6 = this.mTextStyleStateListener;
        Intrinsics.checkNotNull(textStyleStateListener6);
        TextStatusBean textStatusBean6 = this.mTextStatusBean;
        Intrinsics.checkNotNull(textStatusBean6);
        textStyleStateListener6.onQuote(textStatusBean6.isAllQuote);
        TextStyleStateListener textStyleStateListener7 = this.mTextStyleStateListener;
        Intrinsics.checkNotNull(textStyleStateListener7);
        TextStatusBean textStatusBean7 = this.mTextStatusBean;
        Intrinsics.checkNotNull(textStatusBean7);
        textStyleStateListener7.onSpliteLine(textStatusBean7.enableHr);
        TextStyleStateListener textStyleStateListener8 = this.mTextStyleStateListener;
        Intrinsics.checkNotNull(textStyleStateListener8);
        TextStatusBean textStatusBean8 = this.mTextStatusBean;
        Intrinsics.checkNotNull(textStatusBean8);
        textStyleStateListener8.canIndent(textStatusBean8.canIndent);
        TextStyleStateListener textStyleStateListener9 = this.mTextStyleStateListener;
        Intrinsics.checkNotNull(textStyleStateListener9);
        TextStatusBean textStatusBean9 = this.mTextStatusBean;
        Intrinsics.checkNotNull(textStatusBean9);
        textStyleStateListener9.canUndent(textStatusBean9.canUndent);
        TextStyleStateListener textStyleStateListener10 = this.mTextStyleStateListener;
        Intrinsics.checkNotNull(textStyleStateListener10);
        TextStatusBean textStatusBean10 = this.mTextStatusBean;
        Intrinsics.checkNotNull(textStatusBean10);
        textStyleStateListener10.canInsert(textStatusBean10.enableImageInsert);
        TextStyleStateListener textStyleStateListener11 = this.mTextStyleStateListener;
        Intrinsics.checkNotNull(textStyleStateListener11);
        TextStatusBean textStatusBean11 = this.mTextStatusBean;
        Intrinsics.checkNotNull(textStatusBean11);
        textStyleStateListener11.onBold(textStatusBean11.isBoldChecked);
        TextStyleStateListener textStyleStateListener12 = this.mTextStyleStateListener;
        Intrinsics.checkNotNull(textStyleStateListener12);
        TextStatusBean textStatusBean12 = this.mTextStatusBean;
        Intrinsics.checkNotNull(textStatusBean12);
        textStyleStateListener12.onItalic(textStatusBean12.isItalicChecked);
        TextStyleStateListener textStyleStateListener13 = this.mTextStyleStateListener;
        Intrinsics.checkNotNull(textStyleStateListener13);
        TextStatusBean textStatusBean13 = this.mTextStatusBean;
        Intrinsics.checkNotNull(textStatusBean13);
        textStyleStateListener13.onFontSize(textStatusBean13.sizeLevel);
        TextStyleStateListener textStyleStateListener14 = this.mTextStyleStateListener;
        Intrinsics.checkNotNull(textStyleStateListener14);
        TextStatusBean textStatusBean14 = this.mTextStatusBean;
        Intrinsics.checkNotNull(textStatusBean14);
        textStyleStateListener14.onUnderline(textStatusBean14.isUnderlineChecked);
        TextStyleStateListener textStyleStateListener15 = this.mTextStyleStateListener;
        Intrinsics.checkNotNull(textStyleStateListener15);
        TextStatusBean textStatusBean15 = this.mTextStatusBean;
        Intrinsics.checkNotNull(textStatusBean15);
        textStyleStateListener15.onBgHighLight(textStatusBean15.isBgHighLightChecked);
        TextStyleStateListener textStyleStateListener16 = this.mTextStyleStateListener;
        Intrinsics.checkNotNull(textStyleStateListener16);
        TextStatusBean textStatusBean16 = this.mTextStatusBean;
        Intrinsics.checkNotNull(textStatusBean16);
        textStyleStateListener16.onBullet(textStatusBean16.isBulletChecked);
        TextStyleStateListener textStyleStateListener17 = this.mTextStyleStateListener;
        Intrinsics.checkNotNull(textStyleStateListener17);
        TextStatusBean textStatusBean17 = this.mTextStatusBean;
        Intrinsics.checkNotNull(textStatusBean17);
        textStyleStateListener17.onCenter(textStatusBean17.isCenterChecked);
        TextStyleStateListener textStyleStateListener18 = this.mTextStyleStateListener;
        Intrinsics.checkNotNull(textStyleStateListener18);
        TextStatusBean textStatusBean18 = this.mTextStatusBean;
        Intrinsics.checkNotNull(textStatusBean18);
        textStyleStateListener18.onRight(textStatusBean18.isRightChecked);
        TextStyleStateListener textStyleStateListener19 = this.mTextStyleStateListener;
        Intrinsics.checkNotNull(textStyleStateListener19);
        TextStatusBean textStatusBean19 = this.mTextStatusBean;
        Intrinsics.checkNotNull(textStatusBean19);
        if (textStatusBean19.isCenterChecked) {
            z = false;
        } else {
            TextStatusBean textStatusBean20 = this.mTextStatusBean;
            Intrinsics.checkNotNull(textStatusBean20);
            if (textStatusBean20.isRightChecked) {
                z = false;
            } else {
                z = true;
            }
        }
        textStyleStateListener19.onLeft(z);
        this.isNeedTextStyleChanged = false;
    }

    public final void onTextStyleChanged(TextStatusBean bean) {
        if (bean == null) {
            return;
        }
        if (bean.isAllH1 && bean.isAllH2 && bean.isAllH3) {
            bean.isAllH1 = false;
            bean.isAllH2 = false;
            bean.isAllH3 = false;
            bean.isBgHighLightChecked = false;
            bean.isItalicChecked = false;
            bean.isUnderlineChecked = false;
            bean.isBoldChecked = false;
            bean.isAllDelLine = false;
        }
        this.mTextStatusBean = bean;
        TextStyleStateListener textStyleStateListener = this.mTextStyleStateListener;
        if (textStyleStateListener != null) {
            Intrinsics.checkNotNull(textStyleStateListener);
            textStyleStateListener.onH1(bean.isAllH1);
            TextStyleStateListener textStyleStateListener2 = this.mTextStyleStateListener;
            Intrinsics.checkNotNull(textStyleStateListener2);
            textStyleStateListener2.onH2(bean.isAllH2);
            TextStyleStateListener textStyleStateListener3 = this.mTextStyleStateListener;
            Intrinsics.checkNotNull(textStyleStateListener3);
            textStyleStateListener3.onH3(bean.isAllH3);
            TextStyleStateListener textStyleStateListener4 = this.mTextStyleStateListener;
            Intrinsics.checkNotNull(textStyleStateListener4);
            textStyleStateListener4.onListBullet(bean.isAllOrderList);
            TextStyleStateListener textStyleStateListener5 = this.mTextStyleStateListener;
            Intrinsics.checkNotNull(textStyleStateListener5);
            textStyleStateListener5.onStrikeThrough(bean.isAllDelLine);
            TextStyleStateListener textStyleStateListener6 = this.mTextStyleStateListener;
            Intrinsics.checkNotNull(textStyleStateListener6);
            textStyleStateListener6.onQuote(bean.isAllQuote);
            TextStyleStateListener textStyleStateListener7 = this.mTextStyleStateListener;
            Intrinsics.checkNotNull(textStyleStateListener7);
            textStyleStateListener7.onSpliteLine(bean.enableHr);
            TextStyleStateListener textStyleStateListener8 = this.mTextStyleStateListener;
            Intrinsics.checkNotNull(textStyleStateListener8);
            textStyleStateListener8.canIndent(bean.canIndent);
            TextStyleStateListener textStyleStateListener9 = this.mTextStyleStateListener;
            Intrinsics.checkNotNull(textStyleStateListener9);
            textStyleStateListener9.canUndent(bean.canUndent);
            TextStyleStateListener textStyleStateListener10 = this.mTextStyleStateListener;
            Intrinsics.checkNotNull(textStyleStateListener10);
            textStyleStateListener10.canInsert(bean.enableImageInsert);
            TextStyleStateListener textStyleStateListener11 = this.mTextStyleStateListener;
            Intrinsics.checkNotNull(textStyleStateListener11);
            textStyleStateListener11.onBold(bean.isBoldChecked);
            TextStyleStateListener textStyleStateListener12 = this.mTextStyleStateListener;
            Intrinsics.checkNotNull(textStyleStateListener12);
            textStyleStateListener12.onItalic(bean.isItalicChecked);
            TextStyleStateListener textStyleStateListener13 = this.mTextStyleStateListener;
            Intrinsics.checkNotNull(textStyleStateListener13);
            textStyleStateListener13.onFontSize(bean.sizeLevel);
            TextStyleStateListener textStyleStateListener14 = this.mTextStyleStateListener;
            Intrinsics.checkNotNull(textStyleStateListener14);
            textStyleStateListener14.onUnderline(bean.isUnderlineChecked);
            TextStyleStateListener textStyleStateListener15 = this.mTextStyleStateListener;
            Intrinsics.checkNotNull(textStyleStateListener15);
            textStyleStateListener15.onBgHighLight(bean.isBgHighLightChecked);
            TextStyleStateListener textStyleStateListener16 = this.mTextStyleStateListener;
            Intrinsics.checkNotNull(textStyleStateListener16);
            textStyleStateListener16.onBullet(bean.isBulletChecked);
            TextStyleStateListener textStyleStateListener17 = this.mTextStyleStateListener;
            Intrinsics.checkNotNull(textStyleStateListener17);
            textStyleStateListener17.onCenter(bean.isCenterChecked);
            TextStyleStateListener textStyleStateListener18 = this.mTextStyleStateListener;
            Intrinsics.checkNotNull(textStyleStateListener18);
            textStyleStateListener18.onRight(bean.isRightChecked);
            TextStyleStateListener textStyleStateListener19 = this.mTextStyleStateListener;
            Intrinsics.checkNotNull(textStyleStateListener19);
            textStyleStateListener19.onLeft((bean.isCenterChecked || bean.isRightChecked) ? false : true);
            this.isNeedTextStyleChanged = false;
            TextStyleStateListener textStyleStateListener20 = this.mTextStyleStateListener;
            Intrinsics.checkNotNull(textStyleStateListener20);
            textStyleStateListener20.onCheckbox(bean.isAllCheckList);
        }
    }

    public final boolean canInsert() {
        TextStatusBean textStatusBean = this.mTextStatusBean;
        if (textStatusBean == null) {
            return true;
        }
        Intrinsics.checkNotNull(textStatusBean);
        return textStatusBean.enableImageInsert;
    }

    public final void onUndoRedoStateChanged(boolean undoEnable, boolean redoEnable) {
        ButtonStateListener buttonStateListener = this.mButtonStateListener;
        if (buttonStateListener != null) {
            Intrinsics.checkNotNull(buttonStateListener);
            buttonStateListener.onUndoRedoStateChanged(undoEnable, redoEnable);
        }
    }

    public final void onKeyboardVisibilityChanged(boolean visible) {
        if (visible) {
            this.mKeyVisibleTime = System.currentTimeMillis();
        } else {
            if (Math.abs(System.currentTimeMillis() - this.mKeyVisibleTime) < 100.0d) {
                return;
            }
            RichEditorWebView richEditorWebView = this.mWebCore;
            if (richEditorWebView != null && richEditorWebView.getIgnoreViewMode()) {
                return;
            }
        }
        this.isIsKeyboardVisible = visible;
        RichEditorWebView richEditorWebView2 = this.mWebCore;
        if (richEditorWebView2 != null) {
            richEditorWebView2.evaluateJavascript("javascript:keyboardVisibilityChanged(" + this.mGson.toJson(Boolean.valueOf(visible)) + ")", null);
        }
    }

    public final void onExternalKeyboardEnable(boolean enable) {
        RichEditorWebView richEditorWebView = this.mWebCore;
        if (richEditorWebView != null) {
            richEditorWebView.evaluateJavascript("javascript:onExternalKeyboardEnable(" + this.mGson.toJson(Boolean.valueOf(enable)) + ")", null);
        }
    }

    public final void overScrollByPosition(int x, int y) {
        RichEditorWebView richEditorWebView = this.mWebCore;
        if (richEditorWebView != null) {
            richEditorWebView.evaluateJavascript("javascript:overScrollByPosition(" + this.mGson.toJson(Integer.valueOf(x)) + "," + this.mGson.toJson(Integer.valueOf(y)) + ")", null);
        }
    }

    public final void setOnDragListener(View.OnDragListener dragListener) {
        RichEditorWebView richEditorWebView = this.mWebCore;
        if (richEditorWebView != null) {
            richEditorWebView.setOnDragListener(dragListener);
        }
    }

    public final void startDragMode(int mode) {
        this.isInDragMode = true;
        RichEditorWebView richEditorWebView = this.mWebCore;
        if (richEditorWebView != null) {
            richEditorWebView.evaluateJavascript("javascript:startDragMode(" + this.mGson.toJson(Integer.valueOf(mode)) + ")", null);
        }
    }

    public final void enterDragZone() {
        RichEditorWebView richEditorWebView = this.mWebCore;
        if (richEditorWebView != null) {
            richEditorWebView.evaluateJavascript("javascript:enterDragZone()", null);
        }
    }

    public final void exitDragZone() {
        RichEditorWebView richEditorWebView = this.mWebCore;
        if (richEditorWebView != null) {
            richEditorWebView.evaluateJavascript("javascript:exitDragZone()", null);
        }
    }

    public final void endDragMode() {
        this.isInDragMode = false;
        RichEditorWebView richEditorWebView = this.mWebCore;
        if (richEditorWebView != null) {
            richEditorWebView.evaluateJavascript("javascript:endDragMode()", null);
        }
        RichEditorWebView richEditorWebView2 = this.mWebCore;
        if (richEditorWebView2 != null) {
            richEditorWebView2.clearFocus();
        }
    }

    @Deprecated(message = "")
    public final boolean canCreateMindNote() {
        return FolderModel.INSTANCE.isSupportCreateMindNote(this.mFolderId);
    }

    public final int getDragEventType(DragEvent event) {
        Intrinsics.checkNotNullParameter(event, "event");
        ClipDescription clipDescription = event.getClipDescription();
        if (clipDescription != null) {
            String[] strArrFilterMimeTypes = clipDescription.filterMimeTypes("text/*");
            if (strArrFilterMimeTypes != null && strArrFilterMimeTypes.length > 0) {
                return 1;
            }
            String[] strArrFilterMimeTypes2 = clipDescription.filterMimeTypes(ShareAppsAdapter.TYPE_IMAGE);
            if (strArrFilterMimeTypes2 != null && strArrFilterMimeTypes2.length > 0) {
                return 2;
            }
        }
        return DRAG_MODE_NONE;
    }

    public final boolean isDragEventContainText(DragEvent event) {
        String[] strArrFilterMimeTypes;
        Intrinsics.checkNotNullParameter(event, "event");
        ClipDescription clipDescription = event.getClipDescription();
        return (clipDescription == null || (strArrFilterMimeTypes = clipDescription.filterMimeTypes("text/*")) == null || strArrFilterMimeTypes.length <= 0) ? false : true;
    }

    public final boolean isDragEventContainImage(DragEvent event) {
        String[] strArrFilterMimeTypes;
        Intrinsics.checkNotNullParameter(event, "event");
        ClipDescription clipDescription = event.getClipDescription();
        return (clipDescription == null || (strArrFilterMimeTypes = clipDescription.filterMimeTypes(ShareAppsAdapter.TYPE_IMAGE)) == null || strArrFilterMimeTypes.length <= 0) ? false : true;
    }

    public final boolean isValidDragEventData(DragEvent event) {
        Intrinsics.checkNotNullParameter(event, "event");
        if (event.getClipDescription() != null) {
            String[] strArrFilterMimeTypes = event.getClipDescription().filterMimeTypes("text/*");
            String[] strArrFilterMimeTypes2 = event.getClipDescription().filterMimeTypes(ShareAppsAdapter.TYPE_IMAGE);
            if ((strArrFilterMimeTypes2 != null && strArrFilterMimeTypes2.length > 0) || (strArrFilterMimeTypes != null && strArrFilterMimeTypes.length > 0)) {
                return true;
            }
        }
        if (event.getClipData() == null || event.getClipData().getDescription() == null) {
            return false;
        }
        String[] strArrFilterMimeTypes3 = event.getClipData().getDescription().filterMimeTypes(ShareAppsAdapter.TYPE_IMAGE);
        String[] strArrFilterMimeTypes4 = event.getClipData().getDescription().filterMimeTypes("text/*");
        return (strArrFilterMimeTypes3 != null && strArrFilterMimeTypes3.length > 0) || (strArrFilterMimeTypes4 != null && strArrFilterMimeTypes4.length > 0);
    }

    public final void startDragText(EmbedElementInfoBean infoBean) {
        Intrinsics.checkNotNullParameter(infoBean, "infoBean");
        ClipData clipDataNewPlainText = ClipData.newPlainText(null, infoBean.textContent);
        try {
            RichEditorWebView richEditorWebView = this.mWebCore;
            TextThumbnailShadow textThumbnailShadow = new TextThumbnailShadow(richEditorWebView != null ? richEditorWebView.getContext() : null, infoBean.textContent, this.editorTheme);
            this.mTextThumbnailShadow = textThumbnailShadow;
            RichEditorWebView richEditorWebView2 = this.mWebCore;
            if (richEditorWebView2 != null) {
                richEditorWebView2.startDragAndDrop(clipDataNewPlainText, textThumbnailShadow, infoBean.textContent, 768);
            }
            RichEditorWebView richEditorWebView3 = this.mWebCore;
            if (richEditorWebView3 != null) {
                richEditorWebView3.stopActionMode();
            }
        } catch (Exception unused) {
        }
    }

    public final void startDragImage(EmbedElementInfoBean infoBean) {
        Intrinsics.checkNotNullParameter(infoBean, "infoBean");
        ValueAnimator valueAnimator = this.mValueAnimator;
        if (valueAnimator != null) {
            Intrinsics.checkNotNull(valueAnimator);
            valueAnimator.cancel();
        }
        String attachmentPath = AttachmentUtils.getAttachmentPath(getContext(), infoBean.fileName);
        if (attachmentPath == null) {
            return;
        }
        File file = new File(attachmentPath);
        if (file.exists()) {
            Uri imageContentUri = ImageUtils.INSTANCE.getImageContentUri(getContext(), file);
            File file2 = new File(AttachmentUtils.getAttachmentPath(getContext(), infoBean.fileName) + Utils.JPG_SUFFIX);
            if (!file2.exists()) {
                try {
                    file2.createNewFile();
                } catch (Exception unused) {
                }
            }
            AttachmentUtils.INSTANCE.saveAttachmentFile(BaseNoteApp.INSTANCE.getInstance(), imageContentUri, file2);
            ClipData clipData = new ClipData(new ClipDescription(IRichEditor.CLIP_LABEL_RICH_EDITOR, new String[]{ShareAppsAdapter.TYPE_IMAGE}), new ClipData.Item(ImageUtils.INSTANCE.getImageContentUri(getContext(), file2)));
            try {
                this.mImageThumbnailShadow = new ImageThumbnailShadow(getContext(), infoBean, attachmentPath);
                RichEditorWebView richEditorWebView = this.mWebCore;
                Intrinsics.checkNotNull(richEditorWebView);
                ImageThumbnailShadow imageThumbnailShadow = this.mImageThumbnailShadow;
                Intrinsics.checkNotNull(imageThumbnailShadow);
                if (!richEditorWebView.startDragAndDrop(clipData, imageThumbnailShadow.createImageThumbnailShadow(), infoBean, R2.attr.colorPrimaryFixed)) {
                    RichEditorWebView richEditorWebView2 = this.mWebCore;
                    if (richEditorWebView2 != null) {
                        richEditorWebView2.evaluateJavascript("javascript:resetImg()", null);
                    }
                } else if (UIUtils.isAboveAndroidS()) {
                    this.mDraggingElementInfo = infoBean;
                    ValueAnimator valueAnimatorOfFloat = ValueAnimator.ofFloat(0.0f, 1.0f);
                    valueAnimatorOfFloat.addListener(new Animator.AnimatorListener() { // from class: com.miui.hybirdeditor.WebRichEditor.startDragImage.1
                        @Override // android.animation.Animator.AnimatorListener
                        public void onAnimationRepeat(Animator animation) {
                            Intrinsics.checkNotNullParameter(animation, "animation");
                        }

                        @Override // android.animation.Animator.AnimatorListener
                        public void onAnimationStart(Animator animation) {
                            Intrinsics.checkNotNullParameter(animation, "animation");
                        }

                        @Override // android.animation.Animator.AnimatorListener
                        public void onAnimationEnd(Animator animation) {
                            Intrinsics.checkNotNullParameter(animation, "animation");
                            WebRichEditor.this.mValueAnimator = null;
                            WebRichEditor.this.mImageShadowView = null;
                            WebRichEditor.this.mDraggingElementInfo = null;
                        }

                        @Override // android.animation.Animator.AnimatorListener
                        public void onAnimationCancel(Animator animation) {
                            Intrinsics.checkNotNullParameter(animation, "animation");
                            WebRichEditor.this.mValueAnimator = null;
                            WebRichEditor.this.mImageShadowView = null;
                            WebRichEditor.this.mDraggingElementInfo = null;
                        }
                    });
                    valueAnimatorOfFloat.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: com.miui.hybirdeditor.WebRichEditor$$ExternalSyntheticLambda1
                        @Override // android.animation.ValueAnimator.AnimatorUpdateListener
                        public final void onAnimationUpdate(ValueAnimator valueAnimator2) {
                            WebRichEditor.startDragImage$lambda$9(this.f$0, valueAnimator2);
                        }
                    });
                    valueAnimatorOfFloat.setInterpolator(new AccelerateDecelerateInterpolator());
                    valueAnimatorOfFloat.setDuration(200L);
                    valueAnimatorOfFloat.start();
                    this.mValueAnimator = valueAnimatorOfFloat;
                } else {
                    this.mImageShadowView = null;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void startDragImage$lambda$9(WebRichEditor webRichEditor, ValueAnimator animation) {
        Intrinsics.checkNotNullParameter(animation, "animation");
        Object animatedValue = animation.getAnimatedValue();
        Intrinsics.checkNotNull(animatedValue, "null cannot be cast to non-null type kotlin.Float");
        float fFloatValue = ((Float) animatedValue).floatValue();
        RichEditorWebView richEditorWebView = webRichEditor.mWebCore;
        if (richEditorWebView != null) {
            ImageThumbnailShadow imageThumbnailShadow = webRichEditor.mImageThumbnailShadow;
            Intrinsics.checkNotNull(imageThumbnailShadow);
            richEditorWebView.updateDragShadow(imageThumbnailShadow.updateImageThumbnailShadow(fFloatValue));
        }
    }

    public final void changeShadowView(boolean mIsTextPlain) {
        ImageThumbnailShadow imageThumbnailShadow;
        RichEditorWebView richEditorWebView;
        TextThumbnailShadow textThumbnailShadow;
        if (!mIsTextPlain || (textThumbnailShadow = this.mTextThumbnailShadow) == null) {
            if (mIsTextPlain || (imageThumbnailShadow = this.mImageThumbnailShadow) == null || (richEditorWebView = this.mWebCore) == null) {
                return;
            }
            Intrinsics.checkNotNull(imageThumbnailShadow);
            richEditorWebView.updateDragShadow(imageThumbnailShadow.setAlpha());
            return;
        }
        Intrinsics.checkNotNull(textThumbnailShadow);
        textThumbnailShadow.setAlpha();
        RichEditorWebView richEditorWebView2 = this.mWebCore;
        if (richEditorWebView2 != null) {
            richEditorWebView2.updateDragShadow(this.mTextThumbnailShadow);
        }
    }

    public final void setIsInTitle(boolean inTitle) {
        RichEditorWebView richEditorWebView = this.mWebCore;
        if (richEditorWebView != null) {
            richEditorWebView.setIsInTitle(inTitle);
        }
    }

    private final void handleReceiveContent() {
        RichEditorWebView richEditorWebView = this.mWebCore;
        if (richEditorWebView != null) {
            richEditorWebView.setOnCommitContentListener(new NestedWebView.OnCommitContentListener() { // from class: com.miui.hybirdeditor.WebRichEditor$$ExternalSyntheticLambda0
                @Override // com.miui.common.base.NestedWebView.OnCommitContentListener
                public final boolean onCommitContent(InputContentInfo inputContentInfo, int i, Bundle bundle) {
                    return WebRichEditor.handleReceiveContent$lambda$10(this.f$0, inputContentInfo, i, bundle);
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Code duplicated, block: B:33:0x0057 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:36:0x005e A[EXC_TOP_SPLITTER, SYNTHETIC] */
    public static final boolean handleReceiveContent$lambda$10(WebRichEditor webRichEditor, InputContentInfo inputContentInfo, int i, Bundle bundle) throws Throwable {
        boolean z;
        boolean z2 = false;
        if ((i & 1) != 0) {
            try {
                inputContentInfo.requestPermission();
                z = true;
            } catch (Exception e) {
                e = e;
                z = false;
                Logger.INSTANCE.e(TAG, "handleReceiveContent commitContent error: " + e.getMessage());
                if (z) {
                    try {
                        inputContentInfo.releasePermission();
                    } catch (Exception unused) {
                    }
                }
                return false;
            } catch (Throwable th) {
                th = th;
                if (z2) {
                    try {
                        inputContentInfo.releasePermission();
                    } catch (Exception unused2) {
                    }
                }
                throw th;
            }
        } else {
            z = false;
        }
        try {
            try {
                Uri contentUri = inputContentInfo.getContentUri();
                Intrinsics.checkNotNullExpressionValue(contentUri, "getContentUri(...)");
                ClipData clipData = new ClipData(inputContentInfo.getDescription(), new ClipData.Item(contentUri));
                NoteClipManager noteClipManager = NoteClipManager.INSTANCE;
                RichEditorWebView richEditorWebView = webRichEditor.mWebCore;
                Intrinsics.checkNotNull(richEditorWebView);
                noteClipManager.showClipData(clipData, richEditorWebView);
                if (!z) {
                    return true;
                }
                try {
                    inputContentInfo.releasePermission();
                    return true;
                } catch (Exception unused3) {
                    return true;
                }
            } catch (Throwable th2) {
                th = th2;
                z2 = z;
                if (z2) {
                    inputContentInfo.releasePermission();
                }
                throw th;
            }
        } catch (Exception e2) {
            e = e2;
            Logger.INSTANCE.e(TAG, "handleReceiveContent commitContent error: " + e.getMessage());
            if (z) {
                inputContentInfo.releasePermission();
            }
            return false;
        }
    }

    public final void insertSummaryToTop(String text) {
        String string;
        if (text == null) {
            string = "";
        } else {
            String strExtractPlainTextForNotes = NoteSchema.extractPlainTextForNotes(text);
            Intrinsics.checkNotNullExpressionValue(strExtractPlainTextForNotes, "extractPlainTextForNotes(...)");
            String str = strExtractPlainTextForNotes;
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
            string = str.subSequence(i, length + 1).toString();
        }
        String string2 = BaseNoteApp.INSTANCE.getInstance().getString(R.string.summary);
        Intrinsics.checkNotNullExpressionValue(string2, "getString(...)");
        String string3 = BaseNoteApp.INSTANCE.getInstance().getString(R.string.main_body);
        Intrinsics.checkNotNullExpressionValue(string3, "getString(...)");
        String[] strArr = {string2, string, string3};
        RichEditorWebView richEditorWebView = this.mWebCore;
        if (richEditorWebView != null) {
            richEditorWebView.evaluateJavascript("javascript:insertSummaryToTop(" + this.mGson.toJson(strArr) + ")", null);
        }
    }

    public final void replaceDoc(String text) {
        Logger.INSTANCE.d(TAG, "replaceDoc");
        RichEditorWebView richEditorWebView = this.mWebCore;
        if (richEditorWebView != null) {
            richEditorWebView.evaluateJavascript("javascript:setData(" + this.mGson.toJson(text) + ", " + this.mGson.toJson(this.mTitle) + ", " + this.mGson.toJson("") + ", " + this.mGson.toJson((Object) true) + ", " + this.mGson.toJson((Object) true) + ")", null);
        }
    }

    public final void highlightSummary() {
        RichEditorWebView richEditorWebView = this.mWebCore;
        if (richEditorWebView != null) {
            richEditorWebView.evaluateJavascript("javascript:highlightSummary()", null);
        }
    }

    public final void highlightAllBackgroundSection() {
        RichEditorWebView richEditorWebView = this.mWebCore;
        if (richEditorWebView != null) {
            richEditorWebView.evaluateJavascript("javascript:highlightAllBackgroundSection()", null);
        }
    }

    public final void setCurrentAmplitude(double amplitude) {
        RichEditorWebView richEditorWebView = this.mWebCore;
        if (richEditorWebView != null) {
            richEditorWebView.evaluateJavascript("javascript:editor.onCurrentAmplitude(" + this.mGson.toJson(Double.valueOf(amplitude)) + ")", null);
        }
    }

    public final void setAudioDescription(String fileName, String description, int type) {
        String str = type == 0 ? "asr" : NotesNormalConstants.SUMMARY_TABLE_NAME;
        RichEditorWebView richEditorWebView = this.mWebCore;
        if (richEditorWebView != null) {
            richEditorWebView.evaluateJavascript("javascript:editor.applyAudioDesc(" + this.mGson.toJson(fileName) + "," + this.mGson.toJson(description) + "," + this.mGson.toJson(str) + ")", null);
        }
    }

    public final void startAudioByTime(String audioFileName, Boolean isPlaying, int startTime) {
        RichEditorWebView richEditorWebView = this.mWebCore;
        if (richEditorWebView != null) {
            Intrinsics.checkNotNull(richEditorWebView);
            richEditorWebView.evaluateJavascript("javascript:editor.applyAudioPlayState(" + this.mGson.toJson(audioFileName) + "," + this.mGson.toJson(isPlaying) + "," + this.mGson.toJson(Integer.valueOf(startTime)) + ")", null);
        }
    }

    public final void drawSelectionFirst() {
        Logger.INSTANCE.i(TAG, "drawSelectionFirst");
        RichEditorWebView richEditorWebView = this.mWebCore;
        if (richEditorWebView != null) {
            Intrinsics.checkNotNull(richEditorWebView);
            richEditorWebView.evaluateJavascript("javascript:drawSelection()", null);
        }
    }

    public final void drawSelection() {
        Logger.INSTANCE.i(TAG, "drawSelection");
        RichEditorWebView richEditorWebView = this.mWebCore;
        if (richEditorWebView != null) {
            Intrinsics.checkNotNull(richEditorWebView);
            richEditorWebView.evaluateJavascript("javascript:drawSelection(" + this.mGson.toJson((Object) true) + ")", null);
        }
    }

    public final void drawRefreshSelection() {
        Logger.INSTANCE.i(TAG, "drawRefreshSelection");
        RichEditorWebView richEditorWebView = this.mWebCore;
        if (richEditorWebView != null) {
            Intrinsics.checkNotNull(richEditorWebView);
            richEditorWebView.evaluateJavascript("javascript:drawSelection(" + this.mGson.toJson((Object) true) + ")", null);
        }
    }

    public final void drawLoadingGradient() {
        Logger.INSTANCE.i(TAG, "drawLoadingGradient");
        RichEditorWebView richEditorWebView = this.mWebCore;
        if (richEditorWebView != null) {
            Intrinsics.checkNotNull(richEditorWebView);
            richEditorWebView.evaluateJavascript("javascript:drawLoadingGradient()", null);
        }
    }

    public final void toggleEmptyLoading(boolean dismiss) {
        Logger.INSTANCE.i(TAG, "toggleEmptyLoading dismiss: " + dismiss);
        RichEditorWebView richEditorWebView = this.mWebCore;
        if (richEditorWebView != null) {
            Intrinsics.checkNotNull(richEditorWebView);
            richEditorWebView.evaluateJavascript("javascript:toggleEmptyLoading(" + this.mGson.toJson(Boolean.valueOf(dismiss)) + ")", null);
        }
    }

    public final void removeSelectionColorBg() {
        Logger.INSTANCE.i(TAG, "removeSelectionColorBg");
        RichEditorWebView richEditorWebView = this.mWebCore;
        if (richEditorWebView != null) {
            Intrinsics.checkNotNull(richEditorWebView);
            richEditorWebView.evaluateJavascript("javascript:removeSelectionColorBg()", null);
        }
    }

    public final void removeSelectionColorBgSlowly() {
        Logger.INSTANCE.i(TAG, "removeSelectionColorBgSlowly");
        RichEditorWebView richEditorWebView = this.mWebCore;
        if (richEditorWebView != null) {
            Intrinsics.checkNotNull(richEditorWebView);
            richEditorWebView.evaluateJavascript("javascript:removeSelectionColorBgSlowly()", null);
        }
    }

    public final void aiInput(String data, boolean isSummary, boolean firstInvoke, boolean isThinkContent) {
        Logger.INSTANCE.i(TAG, "aiInput firstInvoke: " + firstInvoke + ", aiInput empty: " + TextUtils.isEmpty(data) + ", isThinkContent: " + isThinkContent);
        RichEditorWebView richEditorWebView = this.mWebCore;
        if (richEditorWebView != null) {
            richEditorWebView.evaluateJavascript("javascript:aiInput(" + this.mGson.toJson(data) + "," + this.mGson.toJson(Boolean.valueOf(isSummary)) + "," + this.mGson.toJson(Boolean.valueOf(firstInvoke)) + "," + this.mGson.toJson(Boolean.valueOf(isThinkContent)) + ")", null);
        }
    }

    public final void finishFlow(boolean inNewPanel, String data) {
        Logger.INSTANCE.i(TAG, "finishFlow");
        RichEditorWebView richEditorWebView = this.mWebCore;
        if (richEditorWebView != null) {
            richEditorWebView.evaluateJavascript("javascript:finishFlow(" + inNewPanel + "," + this.mGson.toJson(data) + ")", null);
        }
    }

    public final void setTitle(String title) {
        Logger.INSTANCE.i(TAG, "setTitle");
        this.mTitle = title;
        RichEditorWebView richEditorWebView = this.mWebCore;
        if (richEditorWebView != null) {
            richEditorWebView.evaluateJavascript("javascript:setTitle(" + this.mGson.toJson(title) + ")", null);
        }
        AiActionUtil.asyncNotifyResult(0);
    }

    public final void stopAiInput(String endTip) {
        Logger.INSTANCE.i(TAG, "stopAiInput, endTip:" + endTip);
        RichEditorWebView richEditorWebView = this.mWebCore;
        Intrinsics.checkNotNull(richEditorWebView);
        richEditorWebView.evaluateJavascript("javascript:stopAiInput(" + this.mGson.toJson(endTip) + ")", null);
    }

    public final void insertSummary(String savedData) {
        Logger.INSTANCE.i(TAG, "insertSummary");
        RichEditorWebView richEditorWebView = this.mWebCore;
        if (richEditorWebView != null) {
            richEditorWebView.evaluateJavascript("javascript:insertSummary(" + this.mGson.toJson(savedData) + ")", null);
        }
    }

    public final void trimCorrection() {
        Logger.INSTANCE.i(TAG, "trimCorrection");
        RichEditorWebView richEditorWebView = this.mWebCore;
        if (richEditorWebView != null) {
            richEditorWebView.evaluateJavascript("javascript:trimCorrection()", null);
        }
    }

    public final void trimAIStatement() {
        Logger.INSTANCE.i(TAG, "trimAIStatement");
        RichEditorWebView richEditorWebView = this.mWebCore;
        if (richEditorWebView != null) {
            richEditorWebView.evaluateJavascript("javascript:trimAIStatement()", null);
        }
    }

    public final void saveScrollPosition() {
        Logger.INSTANCE.i(TAG, "saveScrollPosition");
        RichEditorWebView richEditorWebView = this.mWebCore;
        if (richEditorWebView != null) {
            richEditorWebView.evaluateJavascript("javascript:editor.saveScrollPosition()", null);
        }
    }

    public final void restoreToSavedScrollPosition() {
        Logger.INSTANCE.i(TAG, "restoreToSavedScrollPosition");
        RichEditorWebView richEditorWebView = this.mWebCore;
        if (richEditorWebView != null) {
            richEditorWebView.evaluateJavascript("javascript:editor.restoreToSavedScrollPosition()", null);
        }
    }

    public final void beforeRefreshNewContent() {
        Logger.INSTANCE.i(TAG, "beforeRefreshNewContent");
        RichEditorWebView richEditorWebView = this.mWebCore;
        if (richEditorWebView != null) {
            Intrinsics.checkNotNull(richEditorWebView);
            richEditorWebView.evaluateJavascript("javascript:beforeRefreshNewContent()", null);
        }
    }

    public final void setData(String data, String title) {
        setData(data, title, "", false, null);
    }

    public final void setDataWithConfig(String richTextData, String title, String queryContent, Boolean isNeedSetData) {
        Intrinsics.checkNotNullParameter(richTextData, "richTextData");
        Logger.INSTANCE.i(TAG, "setDataWithConfig");
        String strConvertToWeb = NoteSchema.convertToWeb(richTextData);
        Intrinsics.checkNotNullExpressionValue(strConvertToWeb, "convertToWeb(...)");
        this.mRichTextData = strConvertToWeb;
        this.mRichTextOriginData = strConvertToWeb;
        this.mDataFedToFrontend = true;
        this.mTitle = title;
        this.mSearchText = queryContent;
        RichEditorWebView richEditorWebView = this.mWebCore;
        if (richEditorWebView != null) {
            richEditorWebView.evaluateJavascript("javascript:setDataWithConfig(" + this.mGson.toJson(strConvertToWeb) + ", " + this.mGson.toJson(title) + ", " + this.mGson.toJson(queryContent) + ", " + this.mGson.toJson(isNeedSetData) + ")", null);
        }
    }

    public final void refreshNewContent() {
        Logger.INSTANCE.i(TAG, "refreshNewContent");
        RichEditorWebView richEditorWebView = this.mWebCore;
        if (richEditorWebView != null) {
            Intrinsics.checkNotNull(richEditorWebView);
            richEditorWebView.evaluateJavascript("javascript:refreshNewContent()", null);
        }
    }

    public final void refreshNewContent(String data) {
        Logger.INSTANCE.i(TAG, "refreshNewContent");
        RichEditorWebView richEditorWebView = this.mWebCore;
        if (richEditorWebView != null) {
            Intrinsics.checkNotNull(richEditorWebView);
            richEditorWebView.evaluateJavascript("javascript:refreshNewContent(" + this.mGson.toJson(data) + ")", null);
        }
    }

    public final void changePanelTop(int height, boolean isPanelClose) {
        Logger.INSTANCE.i(TAG, "changePanelTop: " + height + ", isPanelClose:" + isPanelClose);
        RichEditorWebView richEditorWebView = this.mWebCore;
        if (richEditorWebView != null) {
            Intrinsics.checkNotNull(richEditorWebView);
            richEditorWebView.evaluateJavascript("javascript:changePanelTop(" + this.mGson.toJson(Integer.valueOf(height)) + ", " + this.mGson.toJson(Boolean.valueOf(isPanelClose)) + ")", null);
        }
    }

    public final void closeCorrectionPop() {
        Logger.INSTANCE.i(TAG, "closeCorrectionPop");
        RichEditorWebView richEditorWebView = this.mWebCore;
        if (richEditorWebView != null) {
            Intrinsics.checkNotNull(richEditorWebView);
            richEditorWebView.evaluateJavascript("javascript:closeCorrectionPop()", null);
        }
    }

    public final void undo2BeforeSnapshot() {
        Logger.INSTANCE.i(TAG, "undo2BeforeSnapshot");
        RichEditorWebView richEditorWebView = this.mWebCore;
        if (richEditorWebView != null) {
            richEditorWebView.evaluateJavascript("javascript:undo2BeforeSnapshot()", null);
        }
    }

    public final void specialUndo() {
        Logger.INSTANCE.i(TAG, "specialUndo");
        RichEditorWebView richEditorWebView = this.mWebCore;
        if (richEditorWebView != null) {
            richEditorWebView.evaluateJavascript("javascript:specialUndo()", null);
        }
    }

    public final void queuedUndo2BeforeSnapshot() {
        Logger.INSTANCE.i(TAG, "queuedUndo2BeforeSnapshot");
        RichEditorWebView richEditorWebView = this.mWebCore;
        if (richEditorWebView != null) {
            richEditorWebView.evaluateJavascript("javascript:queuedUndo2BeforeSnapshot()", null);
        }
    }

    public final void clearFlowStatus() {
        Logger.INSTANCE.i(TAG, "note clearFlowStatus: ");
        RichEditorWebView richEditorWebView = this.mWebCore;
        if (richEditorWebView != null) {
            richEditorWebView.evaluateJavascript("javascript:clearFlowStatus()", null);
        }
    }

    public final void showSelectionEnhanced() {
        Logger.INSTANCE.i(TAG, "note showSelectionEnhanced: ");
        RichEditorWebView richEditorWebView = this.mWebCore;
        if (richEditorWebView != null) {
            richEditorWebView.evaluateJavascript("javascript:showSelectionEnhanced()", null);
        }
    }

    public final void isShowSelector(String selector, final Function1<? super Boolean, Unit> callback) {
        Intrinsics.checkNotNullParameter(selector, "selector");
        Intrinsics.checkNotNullParameter(callback, "callback");
        Logger.INSTANCE.d(TAG, "isShowSelector: " + selector);
        RichEditorWebView richEditorWebView = this.mWebCore;
        if (richEditorWebView != null) {
            richEditorWebView.evaluateJavascript("javascript:document.querySelector('" + selector + "')", new ValueCallback() { // from class: com.miui.hybirdeditor.WebRichEditor$$ExternalSyntheticLambda7
                @Override // android.webkit.ValueCallback
                public final void onReceiveValue(Object obj) {
                    WebRichEditor.isShowSelector$lambda$12(callback, (String) obj);
                }
            });
        } else {
            callback.invoke(false);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void isShowSelector$lambda$12(Function1 function1, String str) {
        Logger.INSTANCE.d(TAG, "isShowSelector-result = " + str);
        function1.invoke(Boolean.valueOf((str == null || Intrinsics.areEqual(str, "null")) ? false : true));
    }

    private final void initAccessibilityManager() {
        boolean zLoadAccessibilityState;
        Context context;
        RichEditorWebView richEditorWebView = this.mWebCore;
        Object systemService = (richEditorWebView == null || (context = richEditorWebView.getContext()) == null) ? null : context.getSystemService("accessibility");
        AccessibilityManager accessibilityManager = systemService instanceof AccessibilityManager ? (AccessibilityManager) systemService : null;
        this.mAccessibilityManager = accessibilityManager;
        if (accessibilityManager != null) {
            Logger.INSTANCE.d(TAG, "initAccessibilityManager");
            try {
                AccessibilityManager accessibilityManager2 = this.mAccessibilityManager;
                Intrinsics.checkNotNull(accessibilityManager2);
                zLoadAccessibilityState = accessibilityManager2.isTouchExplorationEnabled();
            } catch (Exception e) {
                Logger.INSTANCE.d(TAG, "Failed to get system accessibility state, using stored state: " + e);
                zLoadAccessibilityState = loadAccessibilityState();
            }
            this.mIsTouchExplorationEnabled = zLoadAccessibilityState;
            saveAccessibilityState(zLoadAccessibilityState);
            this.mTouchExplorationStateChangeListener = new AccessibilityManager.TouchExplorationStateChangeListener() { // from class: com.miui.hybirdeditor.WebRichEditor$$ExternalSyntheticLambda2
                @Override // android.view.accessibility.AccessibilityManager.TouchExplorationStateChangeListener
                public final void onTouchExplorationStateChanged(boolean z) {
                    WebRichEditor.initAccessibilityManager$lambda$14(this.f$0, z);
                }
            };
            try {
                AccessibilityManager accessibilityManager3 = this.mAccessibilityManager;
                Intrinsics.checkNotNull(accessibilityManager3);
                AccessibilityManager.TouchExplorationStateChangeListener touchExplorationStateChangeListener = this.mTouchExplorationStateChangeListener;
                Intrinsics.checkNotNull(touchExplorationStateChangeListener);
                accessibilityManager3.addTouchExplorationStateChangeListener(touchExplorationStateChangeListener);
            } catch (Exception e2) {
                Logger.INSTANCE.e(TAG, "Failed to add touch exploration listener: " + e2);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void initAccessibilityManager$lambda$14(WebRichEditor webRichEditor, boolean z) {
        Logger.INSTANCE.d(TAG, "onTouchExplorationStateChanged: " + z);
        webRichEditor.mIsTouchExplorationEnabled = z;
        webRichEditor.saveAccessibilityState(z);
    }

    private final void cleanupAccessibilityListener() {
        try {
            AccessibilityManager accessibilityManager = this.mAccessibilityManager;
            if (accessibilityManager == null || this.mTouchExplorationStateChangeListener == null) {
                return;
            }
            Intrinsics.checkNotNull(accessibilityManager);
            AccessibilityManager.TouchExplorationStateChangeListener touchExplorationStateChangeListener = this.mTouchExplorationStateChangeListener;
            Intrinsics.checkNotNull(touchExplorationStateChangeListener);
            accessibilityManager.removeTouchExplorationStateChangeListener(touchExplorationStateChangeListener);
            Logger.INSTANCE.d(TAG, "Removed touch exploration state change listener");
        } catch (Exception e) {
            Logger.INSTANCE.e(TAG, "Failed to remove touch exploration listener: " + e);
        }
    }

    private final void saveAccessibilityState(boolean isEnabled) {
        try {
            PreferenceUtils.setTouchExperienceState(isEnabled);
            Logger.INSTANCE.d(TAG, "saveAccessibilityState: saved " + isEnabled);
        } catch (Exception e) {
            Logger.INSTANCE.e(TAG, "Failed to save accessibility state: " + e);
        }
    }

    private final boolean loadAccessibilityState() {
        try {
            return PreferenceUtils.getTouchExperienceState();
        } catch (Exception e) {
            Logger.INSTANCE.e(TAG, "Failed to load accessibility state: " + e);
            return false;
        }
    }

    public final boolean getAccessibilityState() {
        boolean zLoadAccessibilityState;
        AccessibilityManager accessibilityManager = this.mAccessibilityManager;
        if (accessibilityManager != null) {
            Intrinsics.checkNotNull(accessibilityManager);
            zLoadAccessibilityState = accessibilityManager.isTouchExplorationEnabled();
        } else {
            zLoadAccessibilityState = loadAccessibilityState();
        }
        Logger.INSTANCE.d(TAG, "getAccessibilityState: returning " + zLoadAccessibilityState);
        return zLoadAccessibilityState;
    }

    /* JADX INFO: compiled from: WebRichEditor.kt */
    @Metadata(d1 = {"\u0000\u001a\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\b\n\u0002\b\u0007\b\u0086\u0003\u0018\u00002\u00020\u0001B\t\b\u0002¢\u0006\u0004\b\u0002\u0010\u0003R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082T¢\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0007X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\b\u001a\u00020\u0007X\u0086T¢\u0006\u0002\n\u0000R\u0014\u0010\t\u001a\u00020\u0007X\u0086D¢\u0006\b\n\u0000\u001a\u0004\b\n\u0010\u000bR\u000e\u0010\f\u001a\u00020\u0007X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\r\u001a\u00020\u0007X\u0086T¢\u0006\u0002\n\u0000¨\u0006\u000e"}, d2 = {"Lcom/miui/hybirdeditor/WebRichEditor$Companion;", "", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "()V", "TAG", "", "ACTION_ON_TITLE_CHANGED", "", "ACTION_ON_SAVE_DATA", "DRAG_MODE_NONE", "getDRAG_MODE_NONE", "()I", "DRAG_MODE_TEXT", "DRAG_MODE_ELEMENT", "app_PhoneGlobalRelease"}, k = 1, mv = {2, 2, 0}, xi = 48)
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }

        public final int getDRAG_MODE_NONE() {
            return WebRichEditor.DRAG_MODE_NONE;
        }
    }
}
