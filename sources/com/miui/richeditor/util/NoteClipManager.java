package com.miui.richeditor.util;

import android.content.ClipData;
import android.content.ClipDescription;
import android.content.ClipboardManager;
import android.content.Context;
import android.net.Uri;
import android.text.Editable;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.TextUtils;
import android.text.style.StrikethroughSpan;
import android.widget.Toast;
import ch.qos.logback.core.CoreConstants;
import com.google.gson.Gson;
import com.market.sdk.reflect.ReflectUtilsForMiui;
import com.miui.common.app.BaseNoteApp;
import com.miui.common.tool.ImageUtils;
import com.miui.common.tool.LiteUtils;
import com.miui.common.tool.Logger;
import com.miui.common.tool.Utils;
import com.miui.hybirdeditor.RichEditorWebView;
import com.miui.hybirdeditor.jsbridge.EmbedElementInfoBean;
import com.miui.notes.R;
import com.miui.notes.excerpt.ExcerptDataUtils;
import com.miui.notes.model.WorkingNote;
import com.miui.notes.tool.AttachmentUtils;
import com.miui.richeditor.IEditorContext;
import com.miui.richeditor.IRichEditor;
import com.miui.richeditor.RichEditTextView;
import com.miui.richeditor.schema.HtmlParser;
import com.miui.richeditor.schema.NoteSchema;
import com.miui.richeditor.style.AudioSpan;
import com.miui.richeditor.style.BaseImageSpan;
import com.miui.richeditor.style.CheckboxSpan;
import com.miui.richeditor.style.EditLineHeightSpan;
import com.miui.richeditor.style.LinkCardSpan;
import com.xiaomi.miai.SDKConstants;
import com.xiaomi.mirror.synergy.CallMethod;
import com.xiaomi.onetrack.api.ba;
import defpackage.R2;
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.SortedMap;
import kotlin.Metadata;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.collections.MapsKt;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.IntrinsicsKt;
import kotlin.coroutines.jvm.internal.Boxing;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.SpillingKt;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.StringCompanionObject;
import kotlin.text.Regex;
import kotlin.text.StringsKt;
import kotlinx.coroutines.AwaitKt;
import kotlinx.coroutines.BuildersKt;
import kotlinx.coroutines.BuildersKt__Builders_commonKt;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.Dispatchers;
import kotlinx.coroutines.GlobalScope;
import miui.cloud.CloudPushConstants;
import net.lingala.zip4j.util.InternalZipConstants;
import org.apache.commons.lang3.StringUtils;
import org.json.JSONArray;
import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.Node;
import org.jsoup.nodes.TextNode;
import org.jsoup.select.NodeTraversor;
import org.jsoup.select.NodeVisitor;

/* JADX INFO: compiled from: NoteClipManager.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000²\u0001\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\t\n\u0002\b\u0002\n\u0002\u0010!\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0002\n\u0002\b\f\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0006\bÆ\u0002\u0018\u00002\u00020\u0001:\u0003fghB\t\b\u0002¢\u0006\u0004\b\u0002\u0010\u0003J\u000e\u0010\u0018\u001a\u00020\u00192\u0006\u0010\u0018\u001a\u00020\u000bJ\u000e\u0010\u001a\u001a\u00020\u00192\u0006\u0010\u001a\u001a\u00020\u0005J\u000e\u0010\u001b\u001a\u00020\u00192\u0006\u0010\u001c\u001a\u00020\u0010J\n\u0010\u001d\u001a\u0004\u0018\u00010\u000bH\u0002J\b\u0010\u001e\u001a\u0004\u0018\u00010\u0005J\u0006\u0010\u001f\u001a\u00020\u0019J\u0006\u0010 \u001a\u00020\u0019J\u0006\u0010!\u001a\u00020\u0019J\u0014\u0010\"\u001a\u0004\u0018\u00010\u000b2\b\u0010#\u001a\u0004\u0018\u00010\u000bH\u0002J\u001e\u0010$\u001a\u00020\u00172\u0006\u0010%\u001a\u00020&2\u0006\u0010#\u001a\u00020'2\u0006\u0010(\u001a\u00020)J\u0018\u0010*\u001a\u00020\u00192\b\u0010+\u001a\u0004\u0018\u00010\u00052\u0006\u0010%\u001a\u00020&J\u0010\u0010,\u001a\u00020\u00192\u0006\u0010%\u001a\u00020&H\u0002J \u0010-\u001a\u00020\u00192\u0006\u0010#\u001a\u00020'2\u0006\u0010(\u001a\u00020)2\u0006\u0010%\u001a\u00020&H\u0002J\u0010\u0010.\u001a\u00020\u00052\u0006\u0010\u001a\u001a\u00020\u0005H\u0002J\u0018\u0010/\u001a\u00020\u00192\u0006\u0010+\u001a\u00020\u00052\u0006\u0010%\u001a\u00020&H\u0002J\u0016\u00100\u001a\u00020\u00192\u0006\u00101\u001a\u0002022\u0006\u0010%\u001a\u00020&J\u0016\u00103\u001a\u00020\u00192\u0006\u00104\u001a\u0002052\u0006\u0010%\u001a\u00020&J\u001a\u00106\u001a\u0004\u0018\u0001072\u0006\u00101\u001a\u0002022\u0006\u0010%\u001a\u00020&H\u0002J\u001a\u00106\u001a\u0004\u0018\u0001072\u0006\u00104\u001a\u0002052\u0006\u0010%\u001a\u00020&H\u0002J\u0016\u00109\u001a\u00020\u00192\u0006\u0010%\u001a\u00020&2\u0006\u0010:\u001a\u00020\u0017J\u0006\u0010;\u001a\u00020\u0017J\b\u0010<\u001a\u00020\u0017H\u0002J\u0010\u0010=\u001a\u00020\u00172\u0006\u0010>\u001a\u00020?H\u0002J\u000e\u0010=\u001a\u00020\u00172\u0006\u0010%\u001a\u00020&J\u0010\u0010@\u001a\u00020\u00172\u0006\u0010>\u001a\u00020?H\u0002J\u000e\u0010@\u001a\u00020\u00172\u0006\u0010%\u001a\u00020&J\u000e\u0010A\u001a\u00020\u00172\u0006\u0010%\u001a\u00020&J \u0010B\u001a\u00020\u00192\u0006\u0010C\u001a\u00020D2\u0006\u0010%\u001a\u00020&2\u0006\u0010:\u001a\u00020\u0017H\u0002J\u001e\u0010E\u001a\u00020\u00192\u0006\u0010F\u001a\u00020\u00052\f\u0010G\u001a\b\u0012\u0004\u0012\u00020\u00140\u0013H\u0002J\u001e\u0010H\u001a\u00020\u00192\u0006\u0010F\u001a\u00020\u00052\f\u0010G\u001a\b\u0012\u0004\u0012\u00020\u00140\u0013H\u0002J\u001e\u0010I\u001a\u00020\u00192\u0006\u0010F\u001a\u00020\u00052\f\u0010G\u001a\b\u0012\u0004\u0012\u00020\u00140\u0013H\u0002J$\u0010J\u001a\u00020\u00192\f\u0010K\u001a\b\u0012\u0004\u0012\u00020M0L2\f\u0010G\u001a\b\u0012\u0004\u0012\u00020\u00140\u0013H\u0002J8\u0010N\u001a\u00020\u00192\u0006\u0010O\u001a\u00020&2\n\b\u0002\u0010P\u001a\u0004\u0018\u00010Q2\n\b\u0002\u0010R\u001a\u0004\u0018\u00010S2\u0006\u0010:\u001a\u00020\u00172\b\b\u0002\u0010T\u001a\u00020\u0017J\"\u0010U\u001a\u00020\u00192\u0006\u0010%\u001a\u00020&2\b\u0010R\u001a\u0004\u0018\u00010S2\b\b\u0002\u0010T\u001a\u00020\u0017J\u0018\u0010V\u001a\u00020\u00192\u0006\u0010%\u001a\u00020&2\b\u0010P\u001a\u0004\u0018\u00010QJ\u0018\u0010W\u001a\u00020\u00192\u0006\u0010%\u001a\u00020&2\b\u0010P\u001a\u0004\u0018\u00010QJ\u0016\u0010X\u001a\u00020\u00192\u0006\u0010%\u001a\u00020&2\u0006\u0010(\u001a\u00020)J\u000e\u0010Y\u001a\u00020\u00192\u0006\u0010%\u001a\u00020&J\u001e\u0010Z\u001a\u00020\u00192\u0006\u0010O\u001a\u00020&2\u0006\u0010[\u001a\u00020\\2\u0006\u0010]\u001a\u00020^J*\u0010_\u001a\u00020\u00192\u0006\u0010`\u001a\u00020a2\u0006\u0010b\u001a\u00020c2\b\u0010(\u001a\u0004\u0018\u00010)2\u0006\u0010%\u001a\u00020&H\u0002J\u0010\u0010d\u001a\u00020\u00192\u0006\u0010P\u001a\u00020QH\u0002J\u0016\u0010e\u001a\u00020\u00192\u0006\u0010C\u001a\u00020D2\u0006\u0010R\u001a\u00020SJ\u0016\u0010e\u001a\u00020\u00192\u0006\u0010C\u001a\u00020D2\u0006\u0010P\u001a\u00020QR\u000e\u0010\u0004\u001a\u00020\u0005X\u0082T¢\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0005X\u0082T¢\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\u0005X\u0082T¢\u0006\u0002\n\u0000R\u000e\u0010\b\u001a\u00020\u0005X\u0082T¢\u0006\u0002\n\u0000R\u000e\u0010\t\u001a\u00020\u0005X\u0082T¢\u0006\u0002\n\u0000R\u0010\u0010\n\u001a\u0004\u0018\u00010\u000bX\u0082\u000e¢\u0006\u0002\n\u0000R\u0010\u0010\f\u001a\u0004\u0018\u00010\u0005X\u0082\u000e¢\u0006\u0002\n\u0000R\u0010\u0010\r\u001a\u0004\u0018\u00010\u000eX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u000f\u001a\u00020\u0010X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0011\u001a\u00020\u0010X\u0082\u000e¢\u0006\u0002\n\u0000R\u0014\u0010\u0012\u001a\b\u0012\u0004\u0012\u00020\u00140\u0013X\u0082\u000e¢\u0006\u0002\n\u0000R\u0014\u0010\u0015\u001a\b\u0012\u0004\u0012\u00020\u00140\u0013X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0016\u001a\u00020\u0017X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u00108\u001a\u00020\u0017X\u0082\u000e¢\u0006\u0002\n\u0000¨\u0006i"}, d2 = {"Lcom/miui/richeditor/util/NoteClipManager;", "", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "()V", "TAG", "", "KEY_TYPE", "KEY_CONTENT", "KEY_TEXT", "KEY_IMAGE", "mClipContent", "Landroid/text/Spannable;", "mClipString", "mClipJsonArray", "Lorg/json/JSONArray;", "mTimeStamp", "", "mHybridTimeStamp", "mClipDataList", "", "Lcom/miui/richeditor/util/NoteClipManager$IClipData;", "mHybridCopyList", "isItemDeleted", "", "clipContent", "", "clipString", "setTimeStamp", "timestamp", "getClipContent", "getClipString", "clearClipContent", "clearClipString", "clearClipData", "newContent", "originContent", "startCopy", CoreConstants.CONTEXT_SCOPE_VALUE, "Landroid/content/Context;", "Landroid/text/Editable;", "editorContext", "Lcom/miui/richeditor/IEditorContext;", "hybridCopy", "htmlString", "syncHybridTimeStamp", "spanParse", "processHtmlVoice", "htmlUpdate", "copyImageSpan", "imageSpan", "Lcom/miui/richeditor/style/BaseImageSpan;", "copyImage", "embedElementInfo", "Lcom/miui/hybirdeditor/jsbridge/EmbedElementInfoBean;", "createImageClipDataItem", "Landroid/content/ClipData$Item;", "isNeedRebuild", "getClipData", "isHybridEditor", "checkNeedNetwork", "checkImageNeedDownload", "isNeedSynchronize", "clipDescription", "Landroid/content/ClipDescription;", "isHybridNeedSync", "isHybridExternalPaste", "syncClipboard", "clipData", "Landroid/content/ClipData;", "getHtmlTextAndImageList", "htmlText", "mList", "htmlParseExternal", "htmlParseInner", "htmlParse", "nodes", "", "Lorg/jsoup/nodes/Node;", "imageDownloadAndPaste", "mContext", "webView", "Lcom/miui/hybirdeditor/RichEditorWebView;", "textView", "Lcom/miui/richeditor/RichEditTextView;", "shouldFilterImage", "buildSpanStringAndPaste", "pasteToTitle", "buildJsonAndPaste", "buildClipSpannableString", "buildJsonObject", "updateExcerptByClipData", "builder", "Lcom/miui/richeditor/schema/NoteSchema$SimpleBuilder;", "note", "Lcom/miui/notes/model/WorkingNote;", "appendImage", CloudPushConstants.WATERMARK_TYPE.SUBSCRIPTION, "Landroid/text/SpannableStringBuilder;", CallMethod.ARG_URI, "Landroid/net/Uri;", "doWebViewPaste", "showClipData", "IClipData", "TextElement", "ImageElement", "app_PhoneGlobalRelease"}, k = 1, mv = {2, 2, 0}, xi = 48)
public final class NoteClipManager {
    private static final String KEY_CONTENT = "content";
    private static final String KEY_IMAGE = "img";
    private static final String KEY_TEXT = "text";
    private static final String KEY_TYPE = "type";
    private static final String TAG = "NoteClipManager";
    private static boolean isItemDeleted;
    private static boolean isNeedRebuild;
    private static Spannable mClipContent;
    private static JSONArray mClipJsonArray;
    private static String mClipString;
    private static long mHybridTimeStamp;
    private static long mTimeStamp;
    public static final NoteClipManager INSTANCE = new NoteClipManager();
    private static List<IClipData> mClipDataList = new ArrayList();
    private static List<IClipData> mHybridCopyList = new ArrayList();

    /* JADX INFO: compiled from: NoteClipManager.kt */
    @Metadata(d1 = {"\u0000\n\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\bf\u0018\u00002\u00020\u0001¨\u0006\u0002À\u0006\u0003"}, d2 = {"Lcom/miui/richeditor/util/NoteClipManager$IClipData;", "", "app_PhoneGlobalRelease"}, k = 1, mv = {2, 2, 0}, xi = 48)
    public interface IClipData {
    }

    private NoteClipManager() {
    }

    public final void clipContent(Spannable clipContent) {
        Intrinsics.checkNotNullParameter(clipContent, "clipContent");
        Logger.INSTANCE.d(TAG, "clipContent");
        mClipContent = clipContent;
        mTimeStamp = System.currentTimeMillis();
    }

    public final void clipString(String clipString) {
        Intrinsics.checkNotNullParameter(clipString, "clipString");
        Logger.INSTANCE.d(TAG, "clipString");
        mClipString = clipString;
        mHybridTimeStamp = System.currentTimeMillis();
    }

    public final void setTimeStamp(long timestamp) {
        Logger.INSTANCE.d(TAG, "setTimeStamp: " + timestamp);
        mTimeStamp = timestamp;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final Spannable getClipContent() {
        return newContent(mClipContent);
    }

    public final String getClipString() {
        return mClipString;
    }

    public final void clearClipContent() {
        mClipContent = null;
    }

    public final void clearClipString() {
        mClipString = null;
    }

    public final void clearClipData() {
        clearClipContent();
        clearClipString();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final Spannable newContent(Spannable originContent) {
        if (originContent == null) {
            return null;
        }
        return Spannable.Factory.getInstance().newSpannable(originContent);
    }

    public final boolean startCopy(Context context, Editable originContent, IEditorContext editorContext) {
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(originContent, "originContent");
        Intrinsics.checkNotNullParameter(editorContext, "editorContext");
        Logger.INSTANCE.d(TAG, "startCopy");
        spanParse(originContent, editorContext, context);
        return true;
    }

    public final void hybridCopy(String htmlString, Context context) {
        Intrinsics.checkNotNullParameter(context, "context");
        Logger.INSTANCE.d(TAG, "hybridCopy: ");
        if (htmlString == null) {
            return;
        }
        htmlUpdate(htmlString, context);
        syncHybridTimeStamp(context);
    }

    private final void syncHybridTimeStamp(Context context) {
        ClipDescription description;
        Object systemService = context.getSystemService("clipboard");
        Intrinsics.checkNotNull(systemService, "null cannot be cast to non-null type android.content.ClipboardManager");
        ClipData primaryClip = EditorUtils.getPrimaryClip((ClipboardManager) systemService);
        if (primaryClip == null || (description = primaryClip.getDescription()) == null) {
            return;
        }
        mHybridTimeStamp = description.getTimestamp();
    }

    private final void spanParse(Editable originContent, IEditorContext editorContext, Context context) {
        Logger.INSTANCE.d(TAG, "spanParse");
        int length = originContent.length();
        ArrayList arrayList = new ArrayList();
        mClipDataList.clear();
        Object[] spans = originContent.getSpans(0, length, AudioSpan.class);
        Intrinsics.checkNotNullExpressionValue(spans, "getSpans(...)");
        for (Object obj : (AudioSpan[]) spans) {
            int spanStart = originContent.getSpanStart(obj);
            int spanEnd = originContent.getSpanEnd(obj);
            originContent.removeSpan(obj);
            originContent.delete(spanStart, spanEnd);
        }
        int length2 = originContent.length();
        Object[] spans2 = originContent.getSpans(0, length2, LinkCardSpan.class);
        Intrinsics.checkNotNullExpressionValue(spans2, "getSpans(...)");
        for (LinkCardSpan linkCardSpan : (LinkCardSpan[]) spans2) {
            int iMax = Math.max(0, originContent.getSpanStart(linkCardSpan));
            int iMin = Math.min(length2, originContent.getSpanEnd(linkCardSpan));
            Object objCreateLinkCardSpan = editorContext.getStyleFactory().createLinkCardSpan(linkCardSpan.getElement());
            Intrinsics.checkNotNullExpressionValue(objCreateLinkCardSpan, "createLinkCardSpan(...)");
            originContent.removeSpan(linkCardSpan);
            originContent.setSpan(objCreateLinkCardSpan, iMax, iMin, 33);
        }
        int length3 = originContent.length();
        Object[] spans3 = originContent.getSpans(0, length3, CheckboxSpan.class);
        Intrinsics.checkNotNullExpressionValue(spans3, "getSpans(...)");
        for (CheckboxSpan checkboxSpan : (CheckboxSpan[]) spans3) {
            if (checkboxSpan.getElement().isChecked()) {
                Object[] spans4 = originContent.getSpans(Math.max(0, originContent.getSpanStart(checkboxSpan)), Math.min(length3, originContent.getSpanEnd(checkboxSpan)), StrikethroughSpan.class);
                Intrinsics.checkNotNullExpressionValue(spans4, "getSpans(...)");
                for (Object obj2 : (StrikethroughSpan[]) spans4) {
                    originContent.removeSpan(obj2);
                }
            }
        }
        int length4 = originContent.length();
        LinkedHashMap linkedHashMap = new LinkedHashMap();
        Object[] spans5 = originContent.getSpans(0, length4, BaseImageSpan.class);
        Intrinsics.checkNotNullExpressionValue(spans5, "getSpans(...)");
        for (BaseImageSpan baseImageSpan : (BaseImageSpan[]) spans5) {
            ClipData.Item itemCreateImageClipDataItem = INSTANCE.createImageClipDataItem(baseImageSpan, context);
            if (itemCreateImageClipDataItem != null) {
                linkedHashMap.put(Integer.valueOf(originContent.getSpanStart(baseImageSpan)), itemCreateImageClipDataItem);
            }
        }
        SortedMap sortedMap = MapsKt.toSortedMap(linkedHashMap);
        Object systemService = context.getSystemService("clipboard");
        if (systemService == null) {
            return;
        }
        boolean z = true;
        boolean z2 = sortedMap.size() > 0;
        int iIntValue = 0;
        boolean z3 = false;
        for (Map.Entry entry : sortedMap.entrySet()) {
            Integer num = (Integer) entry.getKey();
            if ((num != null && num.intValue() == 0) || sortedMap.keySet().contains(Integer.valueOf(iIntValue))) {
                Object value = entry.getValue();
                Intrinsics.checkNotNullExpressionValue(value, "<get-value>(...)");
                arrayList.add(value);
                iIntValue = ((Number) entry.getKey()).intValue() + 2;
            } else {
                arrayList.add(new ClipData.Item(originContent.subSequence(iIntValue, ((Number) entry.getKey()).intValue() - 1).toString()));
                Object value2 = entry.getValue();
                Intrinsics.checkNotNullExpressionValue(value2, "<get-value>(...)");
                arrayList.add(value2);
                iIntValue = ((Number) entry.getKey()).intValue() + 2;
                z3 = true;
            }
        }
        if (iIntValue < length4) {
            arrayList.add(new ClipData.Item(originContent.subSequence(iIntValue, length4).toString()));
        } else {
            z = z3;
        }
        if (arrayList.isEmpty()) {
            return;
        }
        ArrayList arrayList2 = new ArrayList();
        if (z) {
            arrayList2.add("text/plain");
        }
        if (z2) {
            arrayList2.add("image/jpeg");
            arrayList2.add("image/png");
        }
        ClipData clipData = new ClipData(new ClipDescription(IRichEditor.CLIP_LABEL_RICH_EDITOR, (String[]) arrayList2.toArray(new String[0])), (ClipData.Item) arrayList.get(0));
        arrayList.remove(0);
        Iterator it = arrayList.iterator();
        while (it.hasNext()) {
            clipData.addItem((ClipData.Item) it.next());
        }
        Logger.INSTANCE.d(TAG, "parse clip data from span ,build clip data to clipboard");
        ((ClipboardManager) systemService).setPrimaryClip(clipData);
    }

    private final String processHtmlVoice(String clipString) {
        Document document = Jsoup.parse(clipString);
        Intrinsics.checkNotNullExpressionValue(document, "parse(...)");
        document.select(".pm-voice-container").remove();
        String strHtml = document.body().html();
        Intrinsics.checkNotNullExpressionValue(strHtml, "html(...)");
        return StringsKt.replace$default(strHtml, ">\n<", "><", false, 4, (Object) null);
    }

    private final void htmlUpdate(String htmlString, Context context) {
        Logger.INSTANCE.d(TAG, "htmlUpdate");
        mHybridCopyList.clear();
        mClipJsonArray = null;
        htmlParseInner(StringsKt.replace$default(htmlString, "<p class=\"\" data-indentation=\"1\"> </p>", "", false, 4, (Object) null), mHybridCopyList);
        Object systemService = context.getSystemService("clipboard");
        if (systemService == null) {
            return;
        }
        ArrayList arrayList = new ArrayList();
        boolean z = false;
        boolean z2 = false;
        for (IClipData iClipData : mHybridCopyList) {
            if (iClipData instanceof TextElement) {
                arrayList.add(new ClipData.Item(((TextElement) iClipData).getMText()));
                z = true;
            } else if (iClipData instanceof ImageElement) {
                ImageElement imageElement = (ImageElement) iClipData;
                String onlineUri = imageElement.getOnlineUri();
                String attachmentPath = AttachmentUtils.getAttachmentPath(context, onlineUri != null ? StringsKt.replace$default(onlineUri, "https://com.miui.notes/note_image/", "", false, 4, (Object) null) : null);
                if (attachmentPath != null) {
                    File file = new File(attachmentPath);
                    if (file.exists()) {
                        imageElement.setLocalValue(ImageUtils.INSTANCE.getImageContentUri(context, file));
                        arrayList.add(new ClipData.Item(imageElement.getLocalUri()));
                        z2 = true;
                    }
                }
            }
        }
        ArrayList arrayList2 = new ArrayList();
        if (z) {
            arrayList2.add("text/plain");
        }
        if (z2) {
            arrayList2.add("image/jpeg");
            arrayList2.add("image/png");
        }
        ClipDescription clipDescription = new ClipDescription(IRichEditor.CLIP_LABEL_RICH_EDITOR, (String[]) arrayList2.toArray(new String[0]));
        if (!arrayList.isEmpty()) {
            Logger.INSTANCE.d(TAG, "build clip data form data parsed from html");
            ClipData clipData = new ClipData(clipDescription, (ClipData.Item) arrayList.get(0));
            arrayList.remove(0);
            Iterator it = arrayList.iterator();
            while (it.hasNext()) {
                clipData.addItem((ClipData.Item) it.next());
            }
            Logger.INSTANCE.d(TAG, "insert clip data to clipboardMgr");
            ((ClipboardManager) systemService).setPrimaryClip(clipData);
            return;
        }
        ((ClipboardManager) systemService).setPrimaryClip(new ClipData(clipDescription, new ClipData.Item("")));
    }

    public final void copyImageSpan(BaseImageSpan imageSpan, Context context) {
        Intrinsics.checkNotNullParameter(imageSpan, "imageSpan");
        Intrinsics.checkNotNullParameter(context, "context");
        Object systemService = context.getSystemService("clipboard");
        Intrinsics.checkNotNull(systemService, "null cannot be cast to non-null type android.content.ClipboardManager");
        ClipboardManager clipboardManager = (ClipboardManager) systemService;
        ClipDescription clipDescription = new ClipDescription("notes_image", new String[]{"image/png", "image/jpg"});
        ClipData.Item itemCreateImageClipDataItem = createImageClipDataItem(imageSpan, context);
        if (itemCreateImageClipDataItem != null) {
            clipboardManager.setPrimaryClip(new ClipData(clipDescription, itemCreateImageClipDataItem));
            Toast.makeText(context, R.string.added_to_clipboard, 0).show();
        }
    }

    public final void copyImage(EmbedElementInfoBean embedElementInfo, Context context) {
        Intrinsics.checkNotNullParameter(embedElementInfo, "embedElementInfo");
        Intrinsics.checkNotNullParameter(context, "context");
        Logger.INSTANCE.d(TAG, "copyImage");
        mClipString = null;
        Object systemService = context.getSystemService("clipboard");
        Intrinsics.checkNotNull(systemService, "null cannot be cast to non-null type android.content.ClipboardManager");
        ClipboardManager clipboardManager = (ClipboardManager) systemService;
        ClipDescription clipDescription = new ClipDescription("notes_image", new String[]{"image/png", "image/jpg"});
        ClipData.Item itemCreateImageClipDataItem = createImageClipDataItem(embedElementInfo, context);
        if (itemCreateImageClipDataItem != null) {
            clipboardManager.setPrimaryClip(new ClipData(clipDescription, itemCreateImageClipDataItem));
            Toast.makeText(context, R.string.added_to_clipboard, 0).show();
        }
    }

    private final ClipData.Item createImageClipDataItem(BaseImageSpan imageSpan, Context context) {
        String attachmentPath = AttachmentUtils.getAttachmentPath(context, imageSpan.getSrcFileId());
        if (attachmentPath == null) {
            return null;
        }
        File file = new File(attachmentPath);
        if (!file.exists()) {
            Logger.INSTANCE.e(TAG, "clip data target image file not exist!!!!");
            return null;
        }
        return new ClipData.Item(ImageUtils.INSTANCE.getImageContentUri(context, file));
    }

    private final ClipData.Item createImageClipDataItem(EmbedElementInfoBean embedElementInfo, Context context) {
        String attachmentPath = AttachmentUtils.getAttachmentPath(context, embedElementInfo.fileName);
        String fileName = embedElementInfo.fileName;
        Intrinsics.checkNotNullExpressionValue(fileName, "fileName");
        if (fileName.length() == 0 || attachmentPath == null) {
            return null;
        }
        File file = new File(attachmentPath);
        if (!file.exists()) {
            Logger.INSTANCE.e(TAG, "clip data target image file not exist!!!!");
            return null;
        }
        return new ClipData.Item(ImageUtils.INSTANCE.getImageContentUri(context, file));
    }

    public final void getClipData(Context context, boolean isHybridEditor) {
        ClipDescription description;
        Intrinsics.checkNotNullParameter(context, "context");
        Object systemService = context.getSystemService("clipboard");
        Intrinsics.checkNotNull(systemService, "null cannot be cast to non-null type android.content.ClipboardManager");
        ClipData primaryClip = EditorUtils.getPrimaryClip((ClipboardManager) systemService);
        if (primaryClip == null || (description = primaryClip.getDescription()) == null) {
            return;
        }
        NoteClipManager noteClipManager = INSTANCE;
        if (isHybridEditor ? noteClipManager.isHybridNeedSync(description) : noteClipManager.isNeedSynchronize(description)) {
            INSTANCE.syncClipboard(primaryClip, context, isHybridEditor);
            isNeedRebuild = true;
        } else {
            isNeedRebuild = false;
        }
    }

    public final boolean checkNeedNetwork() {
        if (!mClipDataList.isEmpty() && !LiteUtils.isSuperLite()) {
            List<IClipData> list = mClipDataList;
            if (!(list instanceof Collection) || !list.isEmpty()) {
                Iterator<T> it = list.iterator();
                while (it.hasNext()) {
                    if (((IClipData) it.next()) instanceof ImageElement) {
                        if (checkImageNeedDownload()) {
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }

    private final boolean checkImageNeedDownload() {
        for (IClipData iClipData : mClipDataList) {
            if ((iClipData instanceof ImageElement) && !((ImageElement) iClipData).getIsLocalUri()) {
                return true;
            }
        }
        return false;
    }

    private final boolean isNeedSynchronize(ClipDescription clipDescription) {
        Logger.INSTANCE.d(TAG, "isNeedSynchronize:" + ((Object) mClipContent) + StringUtils.SPACE + mTimeStamp);
        if (mClipContent == null || mClipDataList.isEmpty()) {
            Logger.INSTANCE.d(TAG, "isNeedSynchronize1 mClipContent is null,need sync");
            return true;
        }
        if (mTimeStamp == 0 || clipDescription.getTimestamp() > mTimeStamp) {
            Logger.INSTANCE.d(TAG, "isNeedSynchronize1 mTimeStamp: " + mTimeStamp + ", clipTimeStamp: " + clipDescription.getTimestamp() + ",need sync");
            return true;
        }
        Logger.INSTANCE.d(TAG, "isNeedSynchronize:false");
        return false;
    }

    public final boolean isNeedSynchronize(Context context) {
        ClipDescription description;
        Intrinsics.checkNotNullParameter(context, "context");
        Object systemService = context.getSystemService("clipboard");
        Intrinsics.checkNotNull(systemService, "null cannot be cast to non-null type android.content.ClipboardManager");
        ClipData primaryClip = EditorUtils.getPrimaryClip((ClipboardManager) systemService);
        if (primaryClip == null || (description = primaryClip.getDescription()) == null) {
            return false;
        }
        if (mClipContent == null) {
            Logger.INSTANCE.d(TAG, "isNeedSynchronize2 mClipContent is null");
            return true;
        }
        if (mTimeStamp != 0 && description.getTimestamp() <= mTimeStamp) {
            return false;
        }
        Logger.INSTANCE.d(TAG, "isNeedSynchronize2 mTimeStamp: " + mTimeStamp + ", clipTimeStamp: " + description.getTimestamp());
        return true;
    }

    private final boolean isHybridNeedSync(ClipDescription clipDescription) {
        Logger.INSTANCE.d(TAG, "isHybridNeedSync:" + ((Object) mClipContent) + StringUtils.SPACE + mTimeStamp);
        if (mClipJsonArray == null) {
            Logger.INSTANCE.d(TAG, "isHybridNeedSync1 mClipJsonArray is null");
            return true;
        }
        if (mHybridTimeStamp != 0 && clipDescription.getTimestamp() <= mHybridTimeStamp) {
            return false;
        }
        Logger.INSTANCE.d(TAG, "isHybridNeedSync1 mHybridTimeStamp: " + mHybridTimeStamp + ", clipTimeStamp: " + clipDescription.getTimestamp());
        return true;
    }

    public final boolean isHybridNeedSync(Context context) {
        ClipDescription description;
        Intrinsics.checkNotNullParameter(context, "context");
        Object systemService = context.getSystemService("clipboard");
        Intrinsics.checkNotNull(systemService, "null cannot be cast to non-null type android.content.ClipboardManager");
        ClipData primaryClip = EditorUtils.getPrimaryClip((ClipboardManager) systemService);
        if (primaryClip == null || (description = primaryClip.getDescription()) == null) {
            return false;
        }
        if (mClipJsonArray == null) {
            Logger.INSTANCE.d(TAG, "isHybridNeedSync2 mClipJsonArray is null");
            return true;
        }
        if (mHybridTimeStamp != 0 && description.getTimestamp() <= mHybridTimeStamp) {
            return false;
        }
        Logger.INSTANCE.d(TAG, "isHybridNeedSync2 mHybridTimeStamp: " + mHybridTimeStamp + ", clipTimeStamp: " + description.getTimestamp());
        return true;
    }

    public final boolean isHybridExternalPaste(Context context) {
        ClipDescription description;
        Intrinsics.checkNotNullParameter(context, "context");
        Object systemService = context.getSystemService("clipboard");
        Intrinsics.checkNotNull(systemService, "null cannot be cast to non-null type android.content.ClipboardManager");
        ClipData primaryClip = EditorUtils.getPrimaryClip((ClipboardManager) systemService);
        if (primaryClip == null || (description = primaryClip.getDescription()) == null) {
            return false;
        }
        if (mClipString == null) {
            Logger.INSTANCE.d(TAG, "isHybridExternalPaste mClipString is null");
            return true;
        }
        if (mHybridTimeStamp != 0 && description.getTimestamp() <= mHybridTimeStamp) {
            return false;
        }
        Logger.INSTANCE.d(TAG, "isHybridExternalPaste mHybridTimeStamp: " + mHybridTimeStamp + ", clipTimeStamp: " + description.getTimestamp());
        return true;
    }

    private final void syncClipboard(ClipData clipData, Context context, boolean isHybridEditor) {
        Logger.INSTANCE.d(TAG, "syncClipboard " + isHybridEditor);
        mClipContent = null;
        mClipJsonArray = null;
        mClipString = null;
        mClipDataList.clear();
        if (isHybridEditor) {
            mHybridTimeStamp = clipData.getDescription().getTimestamp();
        } else {
            mTimeStamp = clipData.getDescription().getTimestamp();
        }
        int itemCount = clipData.getItemCount();
        boolean z = true;
        for (int i = 0; i < itemCount; i++) {
            ClipData.Item itemAt = clipData.getItemAt(i);
            Intrinsics.checkNotNullExpressionValue(itemAt, "getItemAt(...)");
            if (!TextUtils.isEmpty(itemAt.getHtmlText())) {
                String htmlText = itemAt.getHtmlText();
                Intrinsics.checkNotNullExpressionValue(htmlText, "getHtmlText(...)");
                getHtmlTextAndImageList(htmlText, mClipDataList);
            } else if (!TextUtils.isEmpty(itemAt.getText())) {
                List<IClipData> list = mClipDataList;
                SpannableStringBuilder spannableStringBuilderValueOf = SpannableStringBuilder.valueOf(itemAt.getText());
                Intrinsics.checkNotNullExpressionValue(spannableStringBuilderValueOf, "valueOf(...)");
                list.add(new TextElement(spannableStringBuilderValueOf));
            } else {
                if (itemAt.getUri() != null && itemAt.getUri() != null) {
                    String strValueOf = String.valueOf(context.getContentResolver().getType(itemAt.getUri()));
                    if (StringsKt.contains$default((CharSequence) strValueOf, (CharSequence) "image/", false, 2, (Object) null) && !LiteUtils.isSuperLite()) {
                        mClipDataList.add(new ImageElement(true, itemAt.getUri(), null, 4, null));
                    } else if (Intrinsics.areEqual("text/plain", strValueOf)) {
                        List<IClipData> list2 = mClipDataList;
                        SpannableStringBuilder spannableStringBuilderValueOf2 = SpannableStringBuilder.valueOf(itemAt.coerceToText(context).toString());
                        Intrinsics.checkNotNullExpressionValue(spannableStringBuilderValueOf2, "valueOf(...)");
                        list2.add(new TextElement(spannableStringBuilderValueOf2));
                    } else if (Intrinsics.areEqual(SDKConstants.MimeType.HTML, strValueOf)) {
                        NoteClipManager noteClipManager = INSTANCE;
                        String strHtml = Jsoup.parse(itemAt.coerceToHtmlText(context).toString()).body().html();
                        Intrinsics.checkNotNullExpressionValue(strHtml, "html(...)");
                        noteClipManager.getHtmlTextAndImageList(strHtml, mClipDataList);
                    } else if (Intrinsics.areEqual(strValueOf, "*/*")) {
                        Toast.makeText(context, R.string.clip_resource_deleted, 0).show();
                    }
                }
            }
            z = false;
        }
        if (z || (LiteUtils.isSuperLite() && mClipDataList.isEmpty())) {
            Logger.INSTANCE.d(TAG, "onlyHasUnSupportData" + z + ", mClipDataList.size: " + mClipDataList.size() + ",isSuperLite:" + LiteUtils.isSuperLite());
            Toast.makeText(context, R.string.paste_unsupported_data, 0).show();
        }
    }

    private final void getHtmlTextAndImageList(String htmlText, List<IClipData> mList) {
        htmlParseExternal(htmlText, mList);
    }

    private final void htmlParseExternal(String htmlText, final List<IClipData> mList) {
        final StringBuilder sb = new StringBuilder();
        Document document = Jsoup.parse(htmlText);
        Intrinsics.checkNotNullExpressionValue(document, "parse(...)");
        NodeTraversor.traverse(new NodeVisitor() { // from class: com.miui.richeditor.util.NoteClipManager.htmlParseExternal.1
            @Override // org.jsoup.select.NodeVisitor
            public void head(Node node, int depth) {
                Intrinsics.checkNotNullParameter(node, "node");
                if (node instanceof TextNode) {
                    sb.append(((TextNode) node).getWholeText());
                    return;
                }
                if (node instanceof Element) {
                    String strAttr = node.attr("src");
                    Intrinsics.checkNotNullExpressionValue(strAttr, "attr(...)");
                    if (!Intrinsics.areEqual("img", ((Element) node).tagName()) || TextUtils.isEmpty(strAttr)) {
                        return;
                    }
                    mList.add(new TextElement(new SafeSpannableStringBuilder(sb.toString())));
                    StringBuilder sb2 = sb;
                    sb2.delete(0, sb2.length());
                    if (LiteUtils.isSuperLite()) {
                        return;
                    }
                    mList.add(new ImageElement(false, null, strAttr));
                }
            }

            @Override // org.jsoup.select.NodeVisitor
            public void tail(Node node, int depth) {
                Intrinsics.checkNotNullParameter(node, "node");
                if (node instanceof Element) {
                    Node nodeNextSibling = node.nextSibling();
                    Element element = (Element) node;
                    if (element.isBlock()) {
                        if (((nodeNextSibling instanceof TextNode) || (nodeNextSibling instanceof Element)) && !Intrinsics.areEqual("head", element.tagName())) {
                            sb.append(StringUtils.LF);
                        }
                    }
                }
            }
        }, document);
        if (sb.length() > 0) {
            mList.add(new TextElement(new SafeSpannableStringBuilder(sb.toString())));
        }
    }

    private final void htmlParseInner(String htmlText, final List<IClipData> mList) {
        Logger.INSTANCE.d(TAG, "htmlParseInner");
        final StringBuilder sb = new StringBuilder();
        Document document = Jsoup.parse(htmlText);
        Intrinsics.checkNotNullExpressionValue(document, "parse(...)");
        NodeTraversor.traverse(new NodeVisitor() { // from class: com.miui.richeditor.util.NoteClipManager.htmlParseInner.1
            @Override // org.jsoup.select.NodeVisitor
            public void head(Node node, int depth) {
                Intrinsics.checkNotNullParameter(node, "node");
                if (node instanceof TextNode) {
                    sb.append(((TextNode) node).getWholeText());
                    Node nodeParentNode = node.parentNode();
                    if (nodeParentNode == null || !nodeParentNode.hasAttr(HtmlParser.LinkCardElement.KEY_LINK)) {
                        return;
                    }
                    sb.append(StringUtils.SPACE + nodeParentNode.attr(HtmlParser.LinkCardElement.KEY_LINK));
                    return;
                }
                if (node instanceof Element) {
                    String strAttr = node.attr("src");
                    Intrinsics.checkNotNullExpressionValue(strAttr, "attr(...)");
                    if (!Intrinsics.areEqual("img", ((Element) node).tagName()) || TextUtils.isEmpty(strAttr)) {
                        return;
                    }
                    mList.add(new TextElement(new SafeSpannableStringBuilder(sb.toString())));
                    StringBuilder sb2 = sb;
                    sb2.delete(0, sb2.length());
                    if (Utils.isSupportSuperClipboard()) {
                        mList.add(new ImageElement(false, null, strAttr));
                    }
                    Node nodeParentNode2 = node.parentNode();
                    if (nodeParentNode2 == null || !nodeParentNode2.hasAttr("data-desc")) {
                        return;
                    }
                    String strAttr2 = nodeParentNode2.attr("data-desc");
                    Intrinsics.checkNotNullExpressionValue(strAttr2, "attr(...)");
                    if (TextUtils.isEmpty(strAttr2)) {
                        return;
                    }
                    sb.append(nodeParentNode2.attr("data-desc"));
                }
            }

            @Override // org.jsoup.select.NodeVisitor
            public void tail(Node node, int depth) {
                Intrinsics.checkNotNullParameter(node, "node");
                if (node instanceof Element) {
                    Node nodeNextSibling = node.nextSibling();
                    Element element = (Element) node;
                    if (element.isBlock()) {
                        if ((!(nodeNextSibling instanceof TextNode) && !(nodeNextSibling instanceof Element)) || Intrinsics.areEqual("head", element.tagName()) || Intrinsics.areEqual("pm-block bloc pm-img-container", element.className())) {
                            return;
                        }
                        sb.append(StringUtils.LF);
                    }
                }
            }
        }, document);
        if (sb.length() > 0) {
            mList.add(new TextElement(new SafeSpannableStringBuilder(sb.toString())));
        }
    }

    private final void htmlParse(List<? extends Node> nodes, List<IClipData> mList) {
        for (Node node : nodes) {
            if (node instanceof TextNode) {
                TextNode textNode = (TextNode) node;
                if (!Intrinsics.areEqual(textNode.text(), StringUtils.SPACE)) {
                    mList.add(new TextElement(new SafeSpannableStringBuilder(textNode.text())));
                }
            } else if (node instanceof Element) {
                Element element = (Element) node;
                if (element.childNodeSize() != 0) {
                    List<Node> listChildNodes = node.childNodes();
                    Intrinsics.checkNotNullExpressionValue(listChildNodes, "childNodes(...)");
                    htmlParse(listChildNodes, mList);
                } else {
                    String strTagName = element.tagName();
                    if (strTagName.hashCode() == 104387 && strTagName.equals("img")) {
                        mList.add(new ImageElement(false, null, node.attr("src")));
                    }
                }
            }
        }
    }

    public static /* synthetic */ void imageDownloadAndPaste$default(NoteClipManager noteClipManager, Context context, RichEditorWebView richEditorWebView, RichEditTextView richEditTextView, boolean z, boolean z2, int i, Object obj) {
        if ((i & 2) != 0) {
            richEditorWebView = null;
        }
        if ((i & 4) != 0) {
            richEditTextView = null;
        }
        if ((i & 16) != 0) {
            z2 = false;
        }
        noteClipManager.imageDownloadAndPaste(context, richEditorWebView, richEditTextView, z, z2);
    }

    /* JADX INFO: renamed from: com.miui.richeditor.util.NoteClipManager$imageDownloadAndPaste$1, reason: invalid class name and case insensitive filesystem */
    /* JADX INFO: compiled from: NoteClipManager.kt */
    @Metadata(d1 = {"\u0000\n\n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\u0010\u0000\u001a\u00020\u0001*\u00020\u0002H\n"}, d2 = {"<anonymous>", "", "Lkotlinx/coroutines/CoroutineScope;"}, k = 3, mv = {2, 2, 0}, xi = 48)
    @DebugMetadata(c = "com.miui.richeditor.util.NoteClipManager$imageDownloadAndPaste$1", f = "NoteClipManager.kt", i = {0, 1, 1, 2, 2, 3, 3, 3, 3}, l = {R2.attr.clockHandColor, R2.attr.colorAccent, R2.attr.colorCaution, R2.attr.colorOnCaution}, m = "invokeSuspend", n = {"$this$launch", "$this$launch", "downloadJobs", "$this$launch", "downloadJobs", "$this$launch", "downloadJobs", "textView", "$i$a$-let-NoteClipManager$imageDownloadAndPaste$1$4"}, s = {"L$0", "L$0", "L$1", "L$0", "L$1", "L$0", "L$1", "L$2", "I$0"})
    static final class C03641 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
        final /* synthetic */ boolean $isHybridEditor;
        final /* synthetic */ Context $mContext;
        final /* synthetic */ boolean $shouldFilterImage;
        final /* synthetic */ RichEditTextView $textView;
        final /* synthetic */ RichEditorWebView $webView;
        int I$0;
        private /* synthetic */ Object L$0;
        Object L$1;
        Object L$2;
        int label;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        C03641(boolean z, Context context, RichEditTextView richEditTextView, RichEditorWebView richEditorWebView, boolean z2, Continuation<? super C03641> continuation) {
            super(2, continuation);
            this.$isHybridEditor = z;
            this.$mContext = context;
            this.$textView = richEditTextView;
            this.$webView = richEditorWebView;
            this.$shouldFilterImage = z2;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
            C03641 c03641 = new C03641(this.$isHybridEditor, this.$mContext, this.$textView, this.$webView, this.$shouldFilterImage, continuation);
            c03641.L$0 = obj;
            return c03641;
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(CoroutineScope coroutineScope, Continuation<? super Unit> continuation) {
            return ((C03641) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
        }

        /* JADX INFO: renamed from: com.miui.richeditor.util.NoteClipManager$imageDownloadAndPaste$1$1, reason: invalid class name and collision with other inner class name */
        /* JADX INFO: compiled from: NoteClipManager.kt */
        @Metadata(d1 = {"\u0000\n\n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\u0010\u0000\u001a\u0004\u0018\u00010\u0001*\u00020\u0002H\n"}, d2 = {"<anonymous>", "", "Lkotlinx/coroutines/CoroutineScope;"}, k = 3, mv = {2, 2, 0}, xi = 48)
        @DebugMetadata(c = "com.miui.richeditor.util.NoteClipManager$imageDownloadAndPaste$1$1", f = "NoteClipManager.kt", i = {}, l = {}, m = "invokeSuspend", n = {}, s = {})
        static final class C01131 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
            final /* synthetic */ boolean $isHybridEditor;
            final /* synthetic */ RichEditTextView $textView;
            final /* synthetic */ RichEditorWebView $webView;
            int label;

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            C01131(boolean z, RichEditorWebView richEditorWebView, RichEditTextView richEditTextView, Continuation<? super C01131> continuation) {
                super(2, continuation);
                this.$isHybridEditor = z;
                this.$webView = richEditorWebView;
                this.$textView = richEditTextView;
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
                return new C01131(this.$isHybridEditor, this.$webView, this.$textView, continuation);
            }

            @Override // kotlin.jvm.functions.Function2
            public final Object invoke(CoroutineScope coroutineScope, Continuation<? super Unit> continuation) {
                return ((C01131) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Object invokeSuspend(Object obj) {
                IntrinsicsKt.getCOROUTINE_SUSPENDED();
                if (this.label != 0) {
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                }
                ResultKt.throwOnFailure(obj);
                if (this.$isHybridEditor) {
                    RichEditorWebView richEditorWebView = this.$webView;
                    if (richEditorWebView == null) {
                        return null;
                    }
                    richEditorWebView.showLoadingView();
                    return Unit.INSTANCE;
                }
                RichEditTextView richEditTextView = this.$textView;
                if (richEditTextView == null) {
                    return null;
                }
                richEditTextView.showLoadingView();
                return Unit.INSTANCE;
            }
        }

        /* JADX WARN: Code duplicated, block: B:32:0x00ce  */
        /* JADX WARN: Code duplicated, block: B:35:0x00fe  */
        /* JADX WARN: Code duplicated, block: B:37:0x0102  */
        /* JADX WARN: Code restructure failed: missing block: B:33:0x00fb, code lost:
        
            if (kotlinx.coroutines.BuildersKt.withContext(kotlinx.coroutines.Dispatchers.getMain(), new com.miui.richeditor.util.NoteClipManager.C03641.AnonymousClass3(r14.$webView, r14.$mContext, null), r14) == r0) goto L39;
         */
        /* JADX WARN: Code restructure failed: missing block: B:38:0x0134, code lost:
        
            if (kotlinx.coroutines.BuildersKt.withContext(r4, r5, r14) == r0) goto L39;
         */
        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        public final java.lang.Object invokeSuspend(java.lang.Object r15) {
            /*
                Method dump skipped, instruction units count: 314
                To view this dump change 'Code comments level' option to 'DEBUG'
            */
            throw new UnsupportedOperationException("Method not decompiled: com.miui.richeditor.util.NoteClipManager.C03641.invokeSuspend(java.lang.Object):java.lang.Object");
        }

        /* JADX INFO: renamed from: com.miui.richeditor.util.NoteClipManager$imageDownloadAndPaste$1$3, reason: invalid class name */
        /* JADX INFO: compiled from: NoteClipManager.kt */
        @Metadata(d1 = {"\u0000\n\n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\u0010\u0000\u001a\u00020\u0001*\u00020\u0002H\n"}, d2 = {"<anonymous>", "", "Lkotlinx/coroutines/CoroutineScope;"}, k = 3, mv = {2, 2, 0}, xi = 48)
        @DebugMetadata(c = "com.miui.richeditor.util.NoteClipManager$imageDownloadAndPaste$1$3", f = "NoteClipManager.kt", i = {}, l = {}, m = "invokeSuspend", n = {}, s = {})
        static final class AnonymousClass3 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
            final /* synthetic */ Context $mContext;
            final /* synthetic */ RichEditorWebView $webView;
            int label;

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            AnonymousClass3(RichEditorWebView richEditorWebView, Context context, Continuation<? super AnonymousClass3> continuation) {
                super(2, continuation);
                this.$webView = richEditorWebView;
                this.$mContext = context;
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
                return new AnonymousClass3(this.$webView, this.$mContext, continuation);
            }

            @Override // kotlin.jvm.functions.Function2
            public final Object invoke(CoroutineScope coroutineScope, Continuation<? super Unit> continuation) {
                return ((AnonymousClass3) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Object invokeSuspend(Object obj) {
                IntrinsicsKt.getCOROUTINE_SUSPENDED();
                if (this.label != 0) {
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                }
                ResultKt.throwOnFailure(obj);
                RichEditorWebView richEditorWebView = this.$webView;
                if (richEditorWebView != null) {
                    NoteClipManager.INSTANCE.doWebViewPaste(richEditorWebView);
                    richEditorWebView.hideLoadingView();
                }
                if (NoteClipManager.isItemDeleted) {
                    Toast.makeText(this.$mContext, R.string.clip_resource_deleted, 0).show();
                    NoteClipManager noteClipManager = NoteClipManager.INSTANCE;
                    NoteClipManager.isItemDeleted = false;
                }
                return Unit.INSTANCE;
            }
        }
    }

    public final void imageDownloadAndPaste(Context mContext, RichEditorWebView webView, RichEditTextView textView, boolean isHybridEditor, boolean shouldFilterImage) {
        Intrinsics.checkNotNullParameter(mContext, "mContext");
        Logger.INSTANCE.d(TAG, "imageDownloadAndPaste");
        BuildersKt__Builders_commonKt.launch$default(GlobalScope.INSTANCE, Dispatchers.getDefault(), null, new C03641(isHybridEditor, mContext, textView, webView, shouldFilterImage, null), 2, null);
    }

    public static /* synthetic */ void buildSpanStringAndPaste$default(NoteClipManager noteClipManager, Context context, RichEditTextView richEditTextView, boolean z, int i, Object obj) {
        if ((i & 4) != 0) {
            z = false;
        }
        noteClipManager.buildSpanStringAndPaste(context, richEditTextView, z);
    }

    /* JADX INFO: renamed from: com.miui.richeditor.util.NoteClipManager$buildSpanStringAndPaste$1, reason: invalid class name and case insensitive filesystem */
    /* JADX INFO: compiled from: NoteClipManager.kt */
    @Metadata(d1 = {"\u0000\n\n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\u0010\u0000\u001a\u00020\u0001*\u00020\u0002H\n"}, d2 = {"<anonymous>", "", "Lkotlinx/coroutines/CoroutineScope;"}, k = 3, mv = {2, 2, 0}, xi = 48)
    @DebugMetadata(c = "com.miui.richeditor.util.NoteClipManager$buildSpanStringAndPaste$1", f = "NoteClipManager.kt", i = {0, 1, 1, 1, 1, 1, 1, 1, 1, 2}, l = {R2.attr.colorOnSurfaceSecondary, R2.attr.colorSurfaceDim, R2.attr.colorSurfacePopWindow}, m = "invokeSuspend", n = {"$this$launch", "$this$launch", "ssb", "buildSpanJob", "$this$forEach$iv", "element$iv", ba.e, "$i$f$forEach", "$i$a$-forEach-NoteClipManager$buildSpanStringAndPaste$1$3", "$this$launch"}, s = {"L$0", "L$0", "L$1", "L$2", "L$3", "L$5", "L$6", "I$0", "I$1", "L$0"})
    static final class C03611 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
        final /* synthetic */ Context $context;
        final /* synthetic */ boolean $shouldFilterImage;
        final /* synthetic */ RichEditTextView $textView;
        int I$0;
        int I$1;
        private /* synthetic */ Object L$0;
        Object L$1;
        Object L$2;
        Object L$3;
        Object L$4;
        Object L$5;
        Object L$6;
        int label;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        C03611(RichEditTextView richEditTextView, boolean z, Context context, Continuation<? super C03611> continuation) {
            super(2, continuation);
            this.$textView = richEditTextView;
            this.$shouldFilterImage = z;
            this.$context = context;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
            C03611 c03611 = new C03611(this.$textView, this.$shouldFilterImage, this.$context, continuation);
            c03611.L$0 = obj;
            return c03611;
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(CoroutineScope coroutineScope, Continuation<? super Unit> continuation) {
            return ((C03611) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
        }

        /* JADX WARN: Code duplicated, block: B:34:0x00fd  */
        /* JADX WARN: Code duplicated, block: B:40:0x013e  */
        /* JADX WARN: Code restructure failed: missing block: B:13:0x0066, code lost:
        
            if (kotlinx.coroutines.BuildersKt.withContext(kotlinx.coroutines.Dispatchers.getMain(), new com.miui.richeditor.util.NoteClipManager.C03611.C01121(r19.$textView, null), r19) == r1) goto L43;
         */
        /* JADX WARN: Code restructure failed: missing block: B:35:0x0131, code lost:
        
            if (r10 == r1) goto L43;
         */
        /* JADX WARN: Code restructure failed: missing block: B:42:0x0172, code lost:
        
            if (kotlinx.coroutines.BuildersKt.withContext(kotlinx.coroutines.Dispatchers.getMain(), new com.miui.richeditor.util.NoteClipManager.C03611.AnonymousClass4(r19.$textView, r19.$context, null), r19) == r1) goto L43;
         */
        /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:35:0x0131 -> B:37:0x0134). Please report as a decompilation issue!!! */
        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        public final java.lang.Object invokeSuspend(java.lang.Object r20) {
            /*
                Method dump skipped, instruction units count: 376
                To view this dump change 'Code comments level' option to 'DEBUG'
            */
            throw new UnsupportedOperationException("Method not decompiled: com.miui.richeditor.util.NoteClipManager.C03611.invokeSuspend(java.lang.Object):java.lang.Object");
        }

        /* JADX INFO: renamed from: com.miui.richeditor.util.NoteClipManager$buildSpanStringAndPaste$1$1, reason: invalid class name and collision with other inner class name */
        /* JADX INFO: compiled from: NoteClipManager.kt */
        @Metadata(d1 = {"\u0000\n\n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\u0010\u0000\u001a\u0004\u0018\u00010\u0001*\u00020\u0002H\n"}, d2 = {"<anonymous>", "", "Lkotlinx/coroutines/CoroutineScope;"}, k = 3, mv = {2, 2, 0}, xi = 48)
        @DebugMetadata(c = "com.miui.richeditor.util.NoteClipManager$buildSpanStringAndPaste$1$1", f = "NoteClipManager.kt", i = {}, l = {}, m = "invokeSuspend", n = {}, s = {})
        static final class C01121 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
            final /* synthetic */ RichEditTextView $textView;
            int label;

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            C01121(RichEditTextView richEditTextView, Continuation<? super C01121> continuation) {
                super(2, continuation);
                this.$textView = richEditTextView;
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
                return new C01121(this.$textView, continuation);
            }

            @Override // kotlin.jvm.functions.Function2
            public final Object invoke(CoroutineScope coroutineScope, Continuation<? super Unit> continuation) {
                return ((C01121) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Object invokeSuspend(Object obj) {
                IntrinsicsKt.getCOROUTINE_SUSPENDED();
                if (this.label != 0) {
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                }
                ResultKt.throwOnFailure(obj);
                RichEditTextView richEditTextView = this.$textView;
                if (richEditTextView == null) {
                    return null;
                }
                richEditTextView.showLoadingView();
                return Unit.INSTANCE;
            }
        }

        /* JADX INFO: renamed from: com.miui.richeditor.util.NoteClipManager$buildSpanStringAndPaste$1$4, reason: invalid class name */
        /* JADX INFO: compiled from: NoteClipManager.kt */
        @Metadata(d1 = {"\u0000\n\n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\u0010\u0000\u001a\u00020\u0001*\u00020\u0002H\n"}, d2 = {"<anonymous>", "", "Lkotlinx/coroutines/CoroutineScope;"}, k = 3, mv = {2, 2, 0}, xi = 48)
        @DebugMetadata(c = "com.miui.richeditor.util.NoteClipManager$buildSpanStringAndPaste$1$4", f = "NoteClipManager.kt", i = {}, l = {}, m = "invokeSuspend", n = {}, s = {})
        static final class AnonymousClass4 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
            final /* synthetic */ Context $context;
            final /* synthetic */ RichEditTextView $textView;
            int label;

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            AnonymousClass4(RichEditTextView richEditTextView, Context context, Continuation<? super AnonymousClass4> continuation) {
                super(2, continuation);
                this.$textView = richEditTextView;
                this.$context = context;
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
                return new AnonymousClass4(this.$textView, this.$context, continuation);
            }

            @Override // kotlin.jvm.functions.Function2
            public final Object invoke(CoroutineScope coroutineScope, Continuation<? super Unit> continuation) {
                return ((AnonymousClass4) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Object invokeSuspend(Object obj) {
                IntrinsicsKt.getCOROUTINE_SUSPENDED();
                if (this.label != 0) {
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                }
                ResultKt.throwOnFailure(obj);
                RichEditTextView richEditTextView = this.$textView;
                if (richEditTextView != null) {
                    Boxing.boxBoolean(richEditTextView.doPaste(NoteClipManager.INSTANCE.getClipContent()));
                }
                RichEditTextView richEditTextView2 = this.$textView;
                if (richEditTextView2 != null) {
                    richEditTextView2.hideLoadingView();
                }
                if (NoteClipManager.isItemDeleted) {
                    Toast.makeText(this.$context, R.string.clip_resource_deleted, 0).show();
                    NoteClipManager noteClipManager = NoteClipManager.INSTANCE;
                    NoteClipManager.isItemDeleted = false;
                }
                return Unit.INSTANCE;
            }
        }
    }

    public final void buildSpanStringAndPaste(Context context, RichEditTextView textView, boolean shouldFilterImage) {
        Intrinsics.checkNotNullParameter(context, "context");
        Logger.INSTANCE.d(TAG, "buildSpanStringAndPaste");
        BuildersKt__Builders_commonKt.launch$default(GlobalScope.INSTANCE, Dispatchers.getDefault(), null, new C03611(textView, shouldFilterImage, context, null), 2, null);
    }

    public final void pasteToTitle(Context context, RichEditorWebView webView) {
        Intrinsics.checkNotNullParameter(context, "context");
        Object systemService = context.getSystemService("clipboard");
        Intrinsics.checkNotNull(systemService, "null cannot be cast to non-null type android.content.ClipboardManager");
        ClipData primaryClip = EditorUtils.getPrimaryClip((ClipboardManager) systemService);
        ArrayList<IClipData> arrayList = new ArrayList();
        if (primaryClip == null) {
            return;
        }
        int itemCount = primaryClip.getItemCount();
        for (int i = 0; i < itemCount; i++) {
            ClipData.Item itemAt = primaryClip.getItemAt(i);
            Intrinsics.checkNotNullExpressionValue(itemAt, "getItemAt(...)");
            if (!TextUtils.isEmpty(itemAt.getHtmlText())) {
                String htmlText = itemAt.getHtmlText();
                Intrinsics.checkNotNullExpressionValue(htmlText, "getHtmlText(...)");
                getHtmlTextAndImageList(htmlText, arrayList);
            } else if (!TextUtils.isEmpty(itemAt.getText())) {
                SpannableStringBuilder spannableStringBuilderValueOf = SpannableStringBuilder.valueOf(itemAt.getText());
                Intrinsics.checkNotNullExpressionValue(spannableStringBuilderValueOf, "valueOf(...)");
                arrayList.add(new TextElement(spannableStringBuilderValueOf));
            } else if (itemAt.getUri() != null && itemAt.getUri() != null) {
                String strValueOf = String.valueOf(context.getContentResolver().getType(itemAt.getUri()));
                if ("text/plain".equals(strValueOf)) {
                    SpannableStringBuilder spannableStringBuilderValueOf2 = SpannableStringBuilder.valueOf(itemAt.coerceToText(context).toString());
                    Intrinsics.checkNotNullExpressionValue(spannableStringBuilderValueOf2, "valueOf(...)");
                    arrayList.add(new TextElement(spannableStringBuilderValueOf2));
                } else if (SDKConstants.MimeType.HTML.equals(strValueOf)) {
                    NoteClipManager noteClipManager = INSTANCE;
                    String strHtml = Jsoup.parse(itemAt.coerceToHtmlText(context).toString()).body().html();
                    Intrinsics.checkNotNullExpressionValue(strHtml, "html(...)");
                    noteClipManager.getHtmlTextAndImageList(strHtml, arrayList);
                }
            }
        }
        StringBuilder sb = new StringBuilder();
        for (IClipData iClipData : arrayList) {
            if (iClipData instanceof TextElement) {
                TextElement textElement = (TextElement) iClipData;
                if (!TextUtils.isEmpty(textElement.getMText())) {
                    sb.append((CharSequence) textElement.getMText());
                }
            }
        }
        String string = sb.toString();
        Intrinsics.checkNotNullExpressionValue(string, "toString(...)");
        StringsKt.replace$default(string, StringUtils.LF, StringUtils.SPACE, false, 4, (Object) null);
        if (webView != null) {
            Gson gson = new Gson();
            String string2 = sb.toString();
            Intrinsics.checkNotNullExpressionValue(string2, "toString(...)");
            webView.evaluateJavascript("javascript:pasteToTitle(" + gson.toJson(StringsKt.replace$default(string2, StringUtils.LF, StringUtils.SPACE, false, 4, (Object) null)) + ")", null);
        }
    }

    public final void buildJsonAndPaste(Context context, RichEditorWebView webView) {
        Intrinsics.checkNotNullParameter(context, "context");
        Logger.INSTANCE.d(TAG, "buildJsonAndPaste");
        if (mClipDataList.isEmpty()) {
            return;
        }
        BuildersKt__Builders_commonKt.launch$default(GlobalScope.INSTANCE, Dispatchers.getDefault(), null, new AnonymousClass1(webView, context, null), 2, null);
    }

    /* JADX INFO: renamed from: com.miui.richeditor.util.NoteClipManager$buildJsonAndPaste$1, reason: invalid class name */
    /* JADX INFO: compiled from: NoteClipManager.kt */
    @Metadata(d1 = {"\u0000\n\n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\u0010\u0000\u001a\u00020\u0001*\u00020\u0002H\n"}, d2 = {"<anonymous>", "", "Lkotlinx/coroutines/CoroutineScope;"}, k = 3, mv = {2, 2, 0}, xi = 48)
    @DebugMetadata(c = "com.miui.richeditor.util.NoteClipManager$buildJsonAndPaste$1", f = "NoteClipManager.kt", i = {0, 1, 1, 1, 1, 1, 1, 1, 1, 2, 2, 2}, l = {R2.attr.counterOverflowTextAppearance, R2.attr.dependencyType, R2.attr.dialogCheckBoxStyle}, m = "invokeSuspend", n = {"$this$launch", "$this$launch", "jsonList", "buildJsonJob", "$this$forEach$iv", "element$iv", ba.e, "$i$f$forEach", "$i$a$-forEach-NoteClipManager$buildJsonAndPaste$1$3", "$this$launch", "jsonList", "buildJsonJob"}, s = {"L$0", "L$0", "L$1", "L$2", "L$3", "L$5", "L$6", "I$0", "I$1", "L$0", "L$1", "L$2"})
    static final class AnonymousClass1 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
        final /* synthetic */ Context $context;
        final /* synthetic */ RichEditorWebView $webView;
        int I$0;
        int I$1;
        private /* synthetic */ Object L$0;
        Object L$1;
        Object L$2;
        Object L$3;
        Object L$4;
        Object L$5;
        Object L$6;
        int label;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        AnonymousClass1(RichEditorWebView richEditorWebView, Context context, Continuation<? super AnonymousClass1> continuation) {
            super(2, continuation);
            this.$webView = richEditorWebView;
            this.$context = context;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
            AnonymousClass1 anonymousClass1 = new AnonymousClass1(this.$webView, this.$context, continuation);
            anonymousClass1.L$0 = obj;
            return anonymousClass1;
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(CoroutineScope coroutineScope, Continuation<? super Unit> continuation) {
            return ((AnonymousClass1) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
        }

        /* JADX INFO: renamed from: com.miui.richeditor.util.NoteClipManager$buildJsonAndPaste$1$1, reason: invalid class name and collision with other inner class name */
        /* JADX INFO: compiled from: NoteClipManager.kt */
        @Metadata(d1 = {"\u0000\n\n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\u0010\u0000\u001a\u0004\u0018\u00010\u0001*\u00020\u0002H\n"}, d2 = {"<anonymous>", "", "Lkotlinx/coroutines/CoroutineScope;"}, k = 3, mv = {2, 2, 0}, xi = 48)
        @DebugMetadata(c = "com.miui.richeditor.util.NoteClipManager$buildJsonAndPaste$1$1", f = "NoteClipManager.kt", i = {}, l = {}, m = "invokeSuspend", n = {}, s = {})
        static final class C01111 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
            final /* synthetic */ RichEditorWebView $webView;
            int label;

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            C01111(RichEditorWebView richEditorWebView, Continuation<? super C01111> continuation) {
                super(2, continuation);
                this.$webView = richEditorWebView;
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
                return new C01111(this.$webView, continuation);
            }

            @Override // kotlin.jvm.functions.Function2
            public final Object invoke(CoroutineScope coroutineScope, Continuation<? super Unit> continuation) {
                return ((C01111) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Object invokeSuspend(Object obj) {
                IntrinsicsKt.getCOROUTINE_SUSPENDED();
                if (this.label != 0) {
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                }
                ResultKt.throwOnFailure(obj);
                RichEditorWebView richEditorWebView = this.$webView;
                if (richEditorWebView == null) {
                    return null;
                }
                richEditorWebView.showLoadingView();
                return Unit.INSTANCE;
            }
        }

        /* JADX WARN: Code duplicated, block: B:34:0x0114  */
        /* JADX WARN: Code duplicated, block: B:40:0x0152  */
        /* JADX WARN: Code duplicated, block: B:42:0x015b  */
        /* JADX WARN: Code duplicated, block: B:46:0x0198  */
        /* JADX WARN: Code restructure failed: missing block: B:13:0x006e, code lost:
        
            if (kotlinx.coroutines.BuildersKt.withContext(kotlinx.coroutines.Dispatchers.getMain(), new com.miui.richeditor.util.NoteClipManager.AnonymousClass1.C01111(r16.$webView, null), r16) == r1) goto L45;
         */
        /* JADX WARN: Code restructure failed: missing block: B:35:0x0147, code lost:
        
            if (r7 == r1) goto L45;
         */
        /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:35:0x0147 -> B:37:0x014a). Please report as a decompilation issue!!! */
        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        public final java.lang.Object invokeSuspend(java.lang.Object r17) {
            /*
                Method dump skipped, instruction units count: 415
                To view this dump change 'Code comments level' option to 'DEBUG'
            */
            throw new UnsupportedOperationException("Method not decompiled: com.miui.richeditor.util.NoteClipManager.AnonymousClass1.invokeSuspend(java.lang.Object):java.lang.Object");
        }

        /* JADX INFO: renamed from: com.miui.richeditor.util.NoteClipManager$buildJsonAndPaste$1$4, reason: invalid class name */
        /* JADX INFO: compiled from: NoteClipManager.kt */
        @Metadata(d1 = {"\u0000\n\n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\u0010\u0000\u001a\u0004\u0018\u00010\u0001*\u00020\u0002H\n"}, d2 = {"<anonymous>", "", "Lkotlinx/coroutines/CoroutineScope;"}, k = 3, mv = {2, 2, 0}, xi = 48)
        @DebugMetadata(c = "com.miui.richeditor.util.NoteClipManager$buildJsonAndPaste$1$4", f = "NoteClipManager.kt", i = {}, l = {}, m = "invokeSuspend", n = {}, s = {})
        static final class AnonymousClass4 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
            final /* synthetic */ Context $context;
            final /* synthetic */ RichEditorWebView $webView;
            int label;

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            AnonymousClass4(RichEditorWebView richEditorWebView, Context context, Continuation<? super AnonymousClass4> continuation) {
                super(2, continuation);
                this.$webView = richEditorWebView;
                this.$context = context;
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
                return new AnonymousClass4(this.$webView, this.$context, continuation);
            }

            @Override // kotlin.jvm.functions.Function2
            public final Object invoke(CoroutineScope coroutineScope, Continuation<? super Unit> continuation) {
                return ((AnonymousClass4) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Object invokeSuspend(Object obj) {
                IntrinsicsKt.getCOROUTINE_SUSPENDED();
                if (this.label != 0) {
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                }
                ResultKt.throwOnFailure(obj);
                RichEditorWebView richEditorWebView = this.$webView;
                if (richEditorWebView == null) {
                    return null;
                }
                Context context = this.$context;
                NoteClipManager.INSTANCE.doWebViewPaste(richEditorWebView);
                richEditorWebView.hideLoadingView();
                if (NoteClipManager.isItemDeleted) {
                    Toast.makeText(context, R.string.clip_resource_deleted, 0).show();
                    NoteClipManager noteClipManager = NoteClipManager.INSTANCE;
                    NoteClipManager.isItemDeleted = false;
                }
                return Unit.INSTANCE;
            }
        }
    }

    public final void buildClipSpannableString(Context context, IEditorContext editorContext) {
        Uri localUri;
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(editorContext, "editorContext");
        Logger.INSTANCE.d(TAG, "buildClipSpannableString " + isNeedRebuild + StringUtils.SPACE);
        if (isNeedRebuild) {
            SafeSpannableStringBuilder safeSpannableStringBuilder = new SafeSpannableStringBuilder("");
            for (IClipData iClipData : mClipDataList) {
                if (iClipData instanceof TextElement) {
                    String string = ((TextElement) iClipData).getMText().toString();
                    Intrinsics.checkNotNullExpressionValue(string, "toString(...)");
                    String str = string;
                    if (!TextUtils.isEmpty(str) && StringsKt.trim((CharSequence) str).toString().length() != 0) {
                        safeSpannableStringBuilder.append((CharSequence) str);
                    }
                }
                if ((iClipData instanceof ImageElement) && (localUri = ((ImageElement) iClipData).getLocalUri()) != null) {
                    INSTANCE.appendImage(safeSpannableStringBuilder, localUri, editorContext, context);
                }
            }
            mClipContent = safeSpannableStringBuilder;
        }
    }

    public final void buildJsonObject(Context context) {
        Uri localUri;
        Intrinsics.checkNotNullParameter(context, "context");
        Logger.INSTANCE.d(TAG, "buildJsonObject");
        if (mClipDataList.isEmpty()) {
            return;
        }
        mClipJsonArray = null;
        ArrayList arrayList = new ArrayList();
        for (IClipData iClipData : mClipDataList) {
            if (iClipData instanceof TextElement) {
                String string = ((TextElement) iClipData).getMText().toString();
                Intrinsics.checkNotNullExpressionValue(string, "toString(...)");
                String str = string;
                if (!TextUtils.isEmpty(str) && StringsKt.trim((CharSequence) str).toString().length() != 0) {
                    JSONObject jSONObject = new JSONObject();
                    jSONObject.put("type", "text");
                    jSONObject.put("content", string);
                    arrayList.add(jSONObject);
                }
            } else if ((iClipData instanceof ImageElement) && (localUri = ((ImageElement) iClipData).getLocalUri()) != null) {
                JSONObject jSONObject2 = new JSONObject();
                String strSaveImageFile = AttachmentUtils.saveImageFile(context, localUri, true);
                if (strSaveImageFile != null) {
                    String strSubstringAfterLast$default = StringsKt.substringAfterLast$default(strSaveImageFile, InternalZipConstants.ZIP_FILE_SEPARATOR, (String) null, 2, (Object) null);
                    if (!Intrinsics.areEqual(strSubstringAfterLast$default, "")) {
                        jSONObject2.put("type", "img");
                        jSONObject2.put("content", strSubstringAfterLast$default);
                        arrayList.add(jSONObject2);
                    }
                }
                if (strSaveImageFile == null) {
                    isItemDeleted = true;
                }
            }
        }
        ArrayList arrayList2 = arrayList;
        if (!arrayList2.isEmpty()) {
            mClipJsonArray = new JSONArray((Collection) arrayList2);
        }
        arrayList.clear();
    }

    public final void updateExcerptByClipData(Context mContext, NoteSchema.SimpleBuilder builder, WorkingNote note) {
        Intrinsics.checkNotNullParameter(mContext, "mContext");
        Intrinsics.checkNotNullParameter(builder, "builder");
        Intrinsics.checkNotNullParameter(note, "note");
        Logger.INSTANCE.d(TAG, "updateExcerptByClipData with note");
        List list = CollectionsKt.toList(mClipDataList);
        if (!list.isEmpty()) {
            BuildersKt__Builders_commonKt.launch$default(GlobalScope.INSTANCE, Dispatchers.getDefault(), null, new C03661(list, builder, note, mContext, null), 2, null);
        } else {
            Logger.INSTANCE.e(TAG, "mClipDataList is null!!");
        }
    }

    /* JADX INFO: renamed from: com.miui.richeditor.util.NoteClipManager$updateExcerptByClipData$1, reason: invalid class name and case insensitive filesystem */
    /* JADX INFO: compiled from: NoteClipManager.kt */
    @Metadata(d1 = {"\u0000\n\n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\u0010\u0000\u001a\u00020\u0001*\u00020\u0002H\n"}, d2 = {"<anonymous>", "", "Lkotlinx/coroutines/CoroutineScope;"}, k = 3, mv = {2, 2, 0}, xi = 48)
    @DebugMetadata(c = "com.miui.richeditor.util.NoteClipManager$updateExcerptByClipData$1", f = "NoteClipManager.kt", i = {0, 0}, l = {R2.attr.endIconDrawable}, m = "invokeSuspend", n = {"$this$launch", "downloadJobs"}, s = {"L$0", "L$1"})
    static final class C03661 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
        final /* synthetic */ NoteSchema.SimpleBuilder $builder;
        final /* synthetic */ List<IClipData> $clipDataList;
        final /* synthetic */ Context $mContext;
        final /* synthetic */ WorkingNote $note;
        private /* synthetic */ Object L$0;
        Object L$1;
        int label;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        /* JADX WARN: Multi-variable type inference failed */
        C03661(List<? extends IClipData> list, NoteSchema.SimpleBuilder simpleBuilder, WorkingNote workingNote, Context context, Continuation<? super C03661> continuation) {
            super(2, continuation);
            this.$clipDataList = list;
            this.$builder = simpleBuilder;
            this.$note = workingNote;
            this.$mContext = context;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
            C03661 c03661 = new C03661(this.$clipDataList, this.$builder, this.$note, this.$mContext, continuation);
            c03661.L$0 = obj;
            return c03661;
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(CoroutineScope coroutineScope, Continuation<? super Unit> continuation) {
            return ((C03661) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) throws Throwable {
            IClipData iClipData;
            CoroutineScope coroutineScope = (CoroutineScope) this.L$0;
            Object coroutine_suspended = IntrinsicsKt.getCOROUTINE_SUSPENDED();
            int i = this.label;
            if (i == 0) {
                ResultKt.throwOnFailure(obj);
                ArrayList arrayList = new ArrayList();
                arrayList.clear();
                List<IClipData> list = this.$clipDataList;
                Context context = this.$mContext;
                for (IClipData iClipData2 : list) {
                    if ((iClipData2 instanceof ImageElement) && !((ImageElement) iClipData2).getIsLocalUri()) {
                        arrayList.add(BuildersKt__Builders_commonKt.async$default(coroutineScope, Dispatchers.getIO(), null, new NoteClipManager$updateExcerptByClipData$1$1$1(context, iClipData2, null), 2, null));
                    }
                }
                this.L$0 = SpillingKt.nullOutSpilledVariable(coroutineScope);
                this.L$1 = SpillingKt.nullOutSpilledVariable(arrayList);
                this.label = 1;
                if (AwaitKt.awaitAll(arrayList, this) == coroutine_suspended) {
                    return coroutine_suspended;
                }
            } else {
                if (i != 1) {
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                }
                ResultKt.throwOnFailure(obj);
            }
            List<IClipData> list2 = this.$clipDataList;
            NoteSchema.SimpleBuilder simpleBuilder = this.$builder;
            Iterator<T> it = list2.iterator();
            boolean z = false;
            loop0: while (true) {
                boolean z2 = z;
                while (true) {
                    if (!it.hasNext()) {
                        break loop0;
                    }
                    iClipData = (IClipData) it.next();
                    if (iClipData instanceof TextElement) {
                        TextElement textElement = (TextElement) iClipData;
                        String string = textElement.getMText().toString();
                        Intrinsics.checkNotNullExpressionValue(string, "toString(...)");
                        String str = string;
                        if (!TextUtils.isEmpty(str) && StringsKt.trim((CharSequence) str).toString().length() != 0) {
                            Logger.INSTANCE.d(NoteClipManager.TAG, "textElement,append txt");
                            if (z && z2) {
                                simpleBuilder.appendNewLine();
                            }
                            String string2 = textElement.getMText().toString();
                            Intrinsics.checkNotNullExpressionValue(string2, "toString(...)");
                            simpleBuilder.appendText(new Regex("\n \n").replace(string2, StringUtils.LF));
                            z = true;
                        }
                    } else if (iClipData instanceof ImageElement) {
                        break;
                    }
                }
                ImageElement imageElement = (ImageElement) iClipData;
                if (imageElement.getOnlineUri() != null) {
                    Logger.INSTANCE.d(NoteClipManager.TAG, "textElement,append online uri: " + imageElement.getOnlineUri());
                    ExcerptDataUtils.addImage(String.valueOf(imageElement.getOnlineUri()), simpleBuilder);
                } else {
                    Logger.INSTANCE.d(NoteClipManager.TAG, "textElement,append local uri: " + imageElement.getLocalUri());
                    ExcerptDataUtils.addLocalImage(imageElement.getLocalUri(), simpleBuilder);
                }
                z = true;
            }
            if (z) {
                this.$builder.appendNewLine();
            }
            ExcerptDataUtils.updateExcerpt(this.$builder, this.$note);
            return Unit.INSTANCE;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void appendImage(SpannableStringBuilder s, Uri uri, IEditorContext editorContext, Context context) throws Throwable {
        if (editorContext != null) {
            String strSaveImageFile = AttachmentUtils.saveImageFile(context, uri, true);
            if (strSaveImageFile == null) {
                isItemDeleted = true;
            }
            int length = s.length();
            s.append(HtmlParser.MEDIA_DUMMY_CHAR).append("");
            int i = length + 1;
            editorContext.getStyleFactory().setNormalImageSpan(s, length, i, strSaveImageFile);
            s.setSpan(new EditLineHeightSpan(editorContext, 0), i, length + 2, 33);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void doWebViewPaste(RichEditorWebView webView) {
        Logger.INSTANCE.d(TAG, "doWebviewPast,build mClipJsonArray");
        JSONArray jSONArray = mClipJsonArray;
        if (jSONArray != null) {
            StringCompanionObject stringCompanionObject = StringCompanionObject.INSTANCE;
            String str = String.format("javascript:appendRichTextManually(%s)", Arrays.copyOf(new Object[]{jSONArray.toString()}, 1));
            Intrinsics.checkNotNullExpressionValue(str, "format(...)");
            webView.evaluateJavascript(str, null);
        }
    }

    /* JADX INFO: renamed from: com.miui.richeditor.util.NoteClipManager$showClipData$1, reason: invalid class name and case insensitive filesystem */
    /* JADX INFO: compiled from: NoteClipManager.kt */
    @Metadata(d1 = {"\u0000\n\n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\u0010\u0000\u001a\u00020\u0001*\u00020\u0002H\n"}, d2 = {"<anonymous>", "", "Lkotlinx/coroutines/CoroutineScope;"}, k = 3, mv = {2, 2, 0}, xi = 48)
    @DebugMetadata(c = "com.miui.richeditor.util.NoteClipManager$showClipData$1", f = "NoteClipManager.kt", i = {0, 0, 0}, l = {1105}, m = "invokeSuspend", n = {"ssb", "itemCount", "supportImg"}, s = {"L$0", "I$0", "I$1"})
    static final class C03651 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
        final /* synthetic */ ClipData $clipData;
        final /* synthetic */ RichEditTextView $textView;
        int I$0;
        int I$1;
        Object L$0;
        int label;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        C03651(ClipData clipData, RichEditTextView richEditTextView, Continuation<? super C03651> continuation) {
            super(2, continuation);
            this.$clipData = clipData;
            this.$textView = richEditTextView;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
            return new C03651(this.$clipData, this.$textView, continuation);
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(CoroutineScope coroutineScope, Continuation<? super Unit> continuation) {
            return ((C03651) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) throws Throwable {
            Object coroutine_suspended = IntrinsicsKt.getCOROUTINE_SUSPENDED();
            int i = this.label;
            if (i == 0) {
                ResultKt.throwOnFailure(obj);
                SafeSpannableStringBuilder safeSpannableStringBuilder = new SafeSpannableStringBuilder("");
                int itemCount = this.$clipData.getItemCount();
                boolean zIsSuperLite = LiteUtils.isSuperLite();
                int i2 = !zIsSuperLite ? 1 : 0;
                for (int i3 = 0; i3 < itemCount; i3++) {
                    ClipData.Item itemAt = this.$clipData.getItemAt(i3);
                    if (!TextUtils.isEmpty(itemAt.getText())) {
                        safeSpannableStringBuilder.append(itemAt.getText());
                    } else if (itemAt.getUri() != null) {
                        String type = BaseNoteApp.INSTANCE.getInstance().getContentResolver().getType(itemAt.getUri());
                        if (type != null && StringsKt.contains$default((CharSequence) type, (CharSequence) "image/", false, 2, (Object) null) && !zIsSuperLite) {
                            NoteClipManager noteClipManager = NoteClipManager.INSTANCE;
                            Uri uri = itemAt.getUri();
                            Intrinsics.checkNotNullExpressionValue(uri, "getUri(...)");
                            noteClipManager.appendImage(safeSpannableStringBuilder, uri, this.$textView, BaseNoteApp.INSTANCE.getInstance());
                        } else {
                            Logger.INSTANCE.i(NoteClipManager.TAG, "showClipData, getUriType:null");
                        }
                    }
                }
                this.L$0 = SpillingKt.nullOutSpilledVariable(safeSpannableStringBuilder);
                this.I$0 = itemCount;
                this.I$1 = i2;
                this.label = 1;
                if (BuildersKt.withContext(Dispatchers.getMain(), new C01141(this.$textView, safeSpannableStringBuilder, null), this) == coroutine_suspended) {
                    return coroutine_suspended;
                }
            } else {
                if (i != 1) {
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                }
                ResultKt.throwOnFailure(obj);
            }
            return Unit.INSTANCE;
        }

        /* JADX INFO: renamed from: com.miui.richeditor.util.NoteClipManager$showClipData$1$1, reason: invalid class name and collision with other inner class name */
        /* JADX INFO: compiled from: NoteClipManager.kt */
        @Metadata(d1 = {"\u0000\n\n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\u0010\u0000\u001a\u00020\u0001*\u00020\u0002H\n"}, d2 = {"<anonymous>", "", "Lkotlinx/coroutines/CoroutineScope;"}, k = 3, mv = {2, 2, 0}, xi = 48)
        @DebugMetadata(c = "com.miui.richeditor.util.NoteClipManager$showClipData$1$1", f = "NoteClipManager.kt", i = {}, l = {}, m = "invokeSuspend", n = {}, s = {})
        static final class C01141 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
            final /* synthetic */ SpannableStringBuilder $ssb;
            final /* synthetic */ RichEditTextView $textView;
            int label;

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            C01141(RichEditTextView richEditTextView, SpannableStringBuilder spannableStringBuilder, Continuation<? super C01141> continuation) {
                super(2, continuation);
                this.$textView = richEditTextView;
                this.$ssb = spannableStringBuilder;
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
                return new C01141(this.$textView, this.$ssb, continuation);
            }

            @Override // kotlin.jvm.functions.Function2
            public final Object invoke(CoroutineScope coroutineScope, Continuation<? super Unit> continuation) {
                return ((C01141) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Object invokeSuspend(Object obj) {
                IntrinsicsKt.getCOROUTINE_SUSPENDED();
                if (this.label == 0) {
                    ResultKt.throwOnFailure(obj);
                    this.$textView.doPaste(NoteClipManager.INSTANCE.newContent(this.$ssb));
                    return Unit.INSTANCE;
                }
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
        }
    }

    public final void showClipData(ClipData clipData, RichEditTextView textView) {
        Intrinsics.checkNotNullParameter(clipData, "clipData");
        Intrinsics.checkNotNullParameter(textView, "textView");
        BuildersKt__Builders_commonKt.launch$default(GlobalScope.INSTANCE, Dispatchers.getDefault(), null, new C03651(clipData, textView, null), 2, null);
    }

    /* JADX INFO: renamed from: com.miui.richeditor.util.NoteClipManager$showClipData$2, reason: invalid class name */
    /* JADX INFO: compiled from: NoteClipManager.kt */
    @Metadata(d1 = {"\u0000\n\n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\u0010\u0000\u001a\u00020\u0001*\u00020\u0002H\n"}, d2 = {"<anonymous>", "", "Lkotlinx/coroutines/CoroutineScope;"}, k = 3, mv = {2, 2, 0}, xi = 48)
    @DebugMetadata(c = "com.miui.richeditor.util.NoteClipManager$showClipData$2", f = "NoteClipManager.kt", i = {0, 0}, l = {R2.attr.flow_horizontalBias}, m = "invokeSuspend", n = {"jsonArray", "itemCount"}, s = {"L$0", "I$0"})
    static final class AnonymousClass2 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
        final /* synthetic */ ClipData $clipData;
        final /* synthetic */ RichEditorWebView $webView;
        int I$0;
        Object L$0;
        int label;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        AnonymousClass2(ClipData clipData, RichEditorWebView richEditorWebView, Continuation<? super AnonymousClass2> continuation) {
            super(2, continuation);
            this.$clipData = clipData;
            this.$webView = richEditorWebView;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
            return new AnonymousClass2(this.$clipData, this.$webView, continuation);
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(CoroutineScope coroutineScope, Continuation<? super Unit> continuation) {
            return ((AnonymousClass2) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) throws Throwable {
            String type;
            Object coroutine_suspended = IntrinsicsKt.getCOROUTINE_SUSPENDED();
            int i = this.label;
            if (i == 0) {
                ResultKt.throwOnFailure(obj);
                int itemCount = this.$clipData.getItemCount();
                JSONArray jSONArray = new JSONArray();
                for (int i2 = 0; i2 < itemCount; i2++) {
                    ClipData.Item itemAt = this.$clipData.getItemAt(i2);
                    if (!TextUtils.isEmpty(itemAt.getText())) {
                        JSONObject jSONObject = new JSONObject();
                        try {
                            jSONObject.put("type", "text");
                            jSONObject.put("content", itemAt.getText());
                            jSONArray.put(jSONObject);
                        } catch (Exception unused) {
                            Unit unit = Unit.INSTANCE;
                        }
                    }
                    if (itemAt.getUri() != null && (type = BaseNoteApp.INSTANCE.getInstance().getContentResolver().getType(itemAt.getUri())) != null && StringsKt.contains$default((CharSequence) type, (CharSequence) "image/", false, 2, (Object) null)) {
                        JSONObject jSONObject2 = new JSONObject();
                        String strSaveImageFile = AttachmentUtils.saveImageFile((Context) BaseNoteApp.INSTANCE.getInstance(), itemAt.getUri(), true);
                        try {
                            jSONObject2.put("type", "img");
                            jSONObject2.put("content", strSaveImageFile);
                        } catch (Exception unused2) {
                            Logger.INSTANCE.i(NoteClipManager.TAG, "showClipData, getUriType:null");
                        }
                        if (strSaveImageFile != null) {
                            jSONArray.put(jSONObject2);
                        }
                    }
                    TextUtils.isEmpty(itemAt.getHtmlText());
                }
                this.L$0 = SpillingKt.nullOutSpilledVariable(jSONArray);
                this.I$0 = itemCount;
                this.label = 1;
                if (BuildersKt.withContext(Dispatchers.getMain(), new AnonymousClass1(this.$webView, jSONArray, null), this) == coroutine_suspended) {
                    return coroutine_suspended;
                }
            } else {
                if (i != 1) {
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                }
                ResultKt.throwOnFailure(obj);
            }
            return Unit.INSTANCE;
        }

        /* JADX INFO: renamed from: com.miui.richeditor.util.NoteClipManager$showClipData$2$1, reason: invalid class name */
        /* JADX INFO: compiled from: NoteClipManager.kt */
        @Metadata(d1 = {"\u0000\n\n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\u0010\u0000\u001a\u00020\u0001*\u00020\u0002H\n"}, d2 = {"<anonymous>", "", "Lkotlinx/coroutines/CoroutineScope;"}, k = 3, mv = {2, 2, 0}, xi = 48)
        @DebugMetadata(c = "com.miui.richeditor.util.NoteClipManager$showClipData$2$1", f = "NoteClipManager.kt", i = {}, l = {}, m = "invokeSuspend", n = {}, s = {})
        static final class AnonymousClass1 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
            final /* synthetic */ JSONArray $jsonArray;
            final /* synthetic */ RichEditorWebView $webView;
            int label;

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            AnonymousClass1(RichEditorWebView richEditorWebView, JSONArray jSONArray, Continuation<? super AnonymousClass1> continuation) {
                super(2, continuation);
                this.$webView = richEditorWebView;
                this.$jsonArray = jSONArray;
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
                return new AnonymousClass1(this.$webView, this.$jsonArray, continuation);
            }

            @Override // kotlin.jvm.functions.Function2
            public final Object invoke(CoroutineScope coroutineScope, Continuation<? super Unit> continuation) {
                return ((AnonymousClass1) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Object invokeSuspend(Object obj) {
                IntrinsicsKt.getCOROUTINE_SUSPENDED();
                if (this.label != 0) {
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                }
                ResultKt.throwOnFailure(obj);
                RichEditorWebView richEditorWebView = this.$webView;
                StringCompanionObject stringCompanionObject = StringCompanionObject.INSTANCE;
                String str = String.format("javascript:appendRichTextManually(%s)", Arrays.copyOf(new Object[]{this.$jsonArray.toString()}, 1));
                Intrinsics.checkNotNullExpressionValue(str, "format(...)");
                richEditorWebView.evaluateJavascript(str, null);
                return Unit.INSTANCE;
            }
        }
    }

    public final void showClipData(ClipData clipData, RichEditorWebView webView) {
        Intrinsics.checkNotNullParameter(clipData, "clipData");
        Intrinsics.checkNotNullParameter(webView, "webView");
        BuildersKt__Builders_commonKt.launch$default(GlobalScope.INSTANCE, Dispatchers.getDefault(), null, new AnonymousClass2(clipData, webView, null), 2, null);
    }

    /* JADX INFO: compiled from: NoteClipManager.kt */
    @Metadata(d1 = {"\u0000\u0012\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0007\u0018\u00002\u00020\u0001B\u000f\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0004\b\u0004\u0010\u0005R\u001a\u0010\u0006\u001a\u00020\u0003X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0007\u0010\b\"\u0004\b\t\u0010\u0005¨\u0006\n"}, d2 = {"Lcom/miui/richeditor/util/NoteClipManager$TextElement;", "Lcom/miui/richeditor/util/NoteClipManager$IClipData;", "text", "Landroid/text/SpannableStringBuilder;", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "(Landroid/text/SpannableStringBuilder;)V", "mText", "getMText", "()Landroid/text/SpannableStringBuilder;", "setMText", "app_PhoneGlobalRelease"}, k = 1, mv = {2, 2, 0}, xi = 48)
    public static final class TextElement implements IClipData {
        private SpannableStringBuilder mText;

        public TextElement(SpannableStringBuilder text) {
            Intrinsics.checkNotNullParameter(text, "text");
            this.mText = text;
        }

        public final SpannableStringBuilder getMText() {
            return this.mText;
        }

        public final void setMText(SpannableStringBuilder spannableStringBuilder) {
            Intrinsics.checkNotNullParameter(spannableStringBuilder, "<set-?>");
            this.mText = spannableStringBuilder;
        }
    }

    /* JADX INFO: compiled from: NoteClipManager.kt */
    @Metadata(d1 = {"\u0000$\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u000f\n\u0002\u0010\u0002\n\u0000\u0018\u00002\u00020\u0001B'\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\n\b\u0002\u0010\u0004\u001a\u0004\u0018\u00010\u0005\u0012\n\b\u0002\u0010\u0006\u001a\u0004\u0018\u00010\u0007¢\u0006\u0004\b\b\u0010\tJ\u000e\u0010\u0016\u001a\u00020\u00172\u0006\u0010\u0004\u001a\u00020\u0005R\u001c\u0010\n\u001a\u0004\u0018\u00010\u0005X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u000b\u0010\f\"\u0004\b\r\u0010\u000eR\u001c\u0010\u000f\u001a\u0004\u0018\u00010\u0007X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0010\u0010\u0011\"\u0004\b\u0012\u0010\u0013R\u001a\u0010\u0002\u001a\u00020\u0003X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0002\u0010\u0014\"\u0004\b\r\u0010\u0015¨\u0006\u0018"}, d2 = {"Lcom/miui/richeditor/util/NoteClipManager$ImageElement;", "Lcom/miui/richeditor/util/NoteClipManager$IClipData;", "isLocalUri", "", CallMethod.ARG_URI, "Landroid/net/Uri;", "online", "", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "(ZLandroid/net/Uri;Ljava/lang/String;)V", "localUri", "getLocalUri", "()Landroid/net/Uri;", "setLocalUri", "(Landroid/net/Uri;)V", "onlineUri", "getOnlineUri", "()Ljava/lang/String;", "setOnlineUri", "(Ljava/lang/String;)V", "()Z", "(Z)V", "setLocalValue", "", "app_PhoneGlobalRelease"}, k = 1, mv = {2, 2, 0}, xi = 48)
    public static final class ImageElement implements IClipData {
        private boolean isLocalUri;
        private Uri localUri;
        private String onlineUri;

        public ImageElement(boolean z, Uri uri, String str) {
            this.localUri = uri;
            this.onlineUri = str;
            this.isLocalUri = z;
        }

        public /* synthetic */ ImageElement(boolean z, Uri uri, String str, int i, DefaultConstructorMarker defaultConstructorMarker) {
            this(z, (i & 2) != 0 ? null : uri, (i & 4) != 0 ? null : str);
        }

        public final Uri getLocalUri() {
            return this.localUri;
        }

        public final void setLocalUri(Uri uri) {
            this.localUri = uri;
        }

        public final String getOnlineUri() {
            return this.onlineUri;
        }

        public final void setOnlineUri(String str) {
            this.onlineUri = str;
        }

        /* JADX INFO: renamed from: isLocalUri, reason: from getter */
        public final boolean getIsLocalUri() {
            return this.isLocalUri;
        }

        public final void setLocalUri(boolean z) {
            this.isLocalUri = z;
        }

        public final void setLocalValue(Uri uri) {
            Intrinsics.checkNotNullParameter(uri, "uri");
            this.localUri = uri;
            this.isLocalUri = true;
        }
    }
}
