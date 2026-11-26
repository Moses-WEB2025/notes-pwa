package com.miui.libhandwritingpreview.model;

import android.app.Activity;
import android.content.Context;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.graphics.PointF;
import android.graphics.RectF;
import android.text.StaticLayout;
import android.text.TextUtils;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import ch.qos.logback.core.CoreConstants;
import ch.qos.logback.core.rolling.helper.IntegerTokenConverter;
import com.market.sdk.reflect.ReflectUtilsForMiui;
import com.miui.libhandwritingpage.R;
import com.miui.libhandwritingpage.listener.PDFExportListener;
import com.miui.libhandwritingpage.listener.PDFImportListener;
import com.miui.libhandwritingpage.listener.PDFLoadingListener;
import com.miui.libhandwritingpage.listener.PageLoadListener;
import com.miui.libhandwritingpage.listener.PageSaveListener;
import com.miui.libhandwritingpage.listener.PageSizeListener;
import com.miui.libhandwritingpage.listener.PageStateListener;
import com.miui.libhandwritingpage.listener.PageStateView;
import com.miui.libhandwritingpage.listener.PdfThumbnailLoadListener;
import com.miui.libhandwritingpage.newbean.BgGridConfiguration;
import com.miui.libhandwritingpage.newbean.SearchWrapper;
import com.miui.libhandwritingpage.newbean.beanPageInfo.PageBean;
import com.miui.libhandwritingpage.newbean.beanPageInfo.PageItemData;
import com.miui.libhandwritingpage.newbean.beanPageInfo.PagePreviewData;
import com.miui.libhandwritingpage.task.ImportPDFTask;
import com.miui.libhandwritingpage.utils.PageEntHelper;
import com.miui.libhandwritingpage.utils.UIUtils;
import com.miui.libhandwritingpage.utils.Utils;
import com.miui.libhandwritingpage.view.ImportPdfBitmap;
import com.miui.libhandwritingpreview.adapter.PagePreAdapter;
import com.miui.libhandwritingpreview.engine.PreMiCurveEngine;
import com.miui.libhandwritingpreview.interfaces.PreICreationEngine;
import com.miui.libhandwritingpreview.manager.PagePreLayoutManager;
import com.miui.libhandwritingpreview.system.PreBgSystem;
import com.miui.libhandwritingpreview.system.PreHighLightSystem;
import com.miui.libhandwritingpreview.system.PrePageSystem;
import com.miui.libhandwritingpreview.util.PagePreEntHelper;
import com.miui.libhandwritingpreview.view.CreationShieldPrePageInkView;
import com.miui.libhandwritingpreview.view.PagePreDrawView;
import com.miui.libhandwritingpreview.view.PagePreItemView;
import com.miui.maml.folme.AnimatedProperty;
import java.io.File;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.ListIterator;
import java.util.concurrent.Callable;
import java.util.concurrent.FutureTask;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.StringCompanionObject;
import kotlin.text.Regex;
import xc.engine.api.CreationPageSDK;
import xc.engine.core.algorithm.AlgorithmNative;
import xc.engine.core.graphics.engine.core.Color;
import xc.engine.operate.canvas.ICanvasOperator;
import xc.engine.operate.step.IStepManager;
import xc.engine.storage.ent.PageEntInfo;
import xc.engine.storage.ent.file_io.InkEntFileIOHelper;
import xc.engine.storage.ent.tools.PageEntTools;
import xc.engine.utils.EngineConfig;
import xc.engine.utils.FileUtil;
import xc.engine.utils.PageSizeConfig;
import xc.richtext.api.ItemSpannedData;
import xc.richtext.utils.TextLayoutUtil;
import xc.richtext.view.SpannedLayoutModel;

/* JADX INFO: compiled from: PageInkPreModel.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u0000ì\u0001\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010!\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0010\u0007\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0010 \n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0002\b\u000e\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0012\n\u0002\u0010\t\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u001b\n\u0002\u0018\u0002\n\u0002\b\f\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0010\u0014\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0002\b\f\u0018\u0000 Æ\u00012\u00020\u0001:\u0006Ä\u0001Å\u0001Æ\u0001B\u000f\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0004\b\u0004\u0010\u0005J\u0006\u0010*\u001a\u00020+J\b\u00109\u001a\u00020+H\u0002J\u0006\u0010:\u001a\u00020+J\b\u0010C\u001a\u0004\u0018\u00010\nJ\u0006\u0010D\u001a\u00020\nJ\u000e\u0010E\u001a\u00020+2\u0006\u0010F\u001a\u00020\fJ\u000e\u0010G\u001a\u00020+2\u0006\u0010F\u001a\u00020\fJ\u0006\u0010H\u001a\u00020+J\u0006\u0010I\u001a\u00020\u0011J\u0006\u0010J\u001a\u00020\fJ\u000e\u0010K\u001a\u00020\u00112\u0006\u0010L\u001a\u00020MJ\u0010\u0010N\u001a\u00020\u00112\u0006\u0010L\u001a\u00020MH\u0002J\u0010\u0010O\u001a\u00020+2\u0006\u0010L\u001a\u00020MH\u0002J\u0010\u0010P\u001a\u00020+2\u0006\u0010L\u001a\u00020MH\u0002J\b\u0010Q\u001a\u00020+H\u0002J\u001a\u0010R\u001a\u00020+2\b\u0010S\u001a\u0004\u0018\u00010\n2\b\u0010T\u001a\u0004\u0018\u00010UJ\b\u0010V\u001a\u00020+H\u0002J\u0006\u0010W\u001a\u00020+J\u0018\u0010X\u001a\u00020+2\u000e\u0010Y\u001a\n\u0012\u0004\u0012\u00020\b\u0018\u000106H\u0002J2\u0010Z\u001a\u001e\u0012\u0004\u0012\u00020\f\u0012\u0004\u0012\u00020\f0[j\u000e\u0012\u0004\u0012\u00020\f\u0012\u0004\u0012\u00020\f`\\2\f\u0010Y\u001a\b\u0012\u0004\u0012\u00020\b06H\u0002J \u0010]\u001a\u00020+2\u0006\u0010^\u001a\u00020_2\b\u0010T\u001a\u0004\u0018\u00010U2\u0006\u0010`\u001a\u00020\u0011J\u0010\u0010a\u001a\u00020b2\u0006\u0010c\u001a\u00020\fH\u0002J\b\u0010d\u001a\u00020+H\u0002J\u0018\u0010e\u001a\u00020+2\u0006\u0010f\u001a\u00020\b2\u0006\u0010g\u001a\u00020\u0011H\u0002J\u0018\u0010h\u001a\u00020+2\u0006\u0010i\u001a\u00020\f2\u0006\u0010j\u001a\u00020\fH\u0002J\u0018\u0010k\u001a\u00020+2\u0006\u0010i\u001a\u00020\f2\u0006\u0010l\u001a\u00020\fH\u0002J\u0010\u0010m\u001a\u00020+2\b\u0010n\u001a\u0004\u0018\u00010\nJ\u0016\u0010o\u001a\u00020+2\u0006\u0010p\u001a\u00020\u000f2\u0006\u0010q\u001a\u00020\u000fJ\u0006\u0010r\u001a\u00020\u000fJ\u0006\u0010s\u001a\u00020\u000fJ\u0006\u0010t\u001a\u00020uJ\u000e\u0010v\u001a\u00020+2\u0006\u0010w\u001a\u00020\bJ\u000e\u0010x\u001a\u00020+2\u0006\u0010y\u001a\u00020\u0011J\u0010\u0010z\u001a\u00020+2\b\u0010{\u001a\u0004\u0018\u00010|J\u0006\u0010}\u001a\u00020~J$\u0010\u007f\u001a\u00020+2\n\u0010\u0080\u0001\u001a\u0005\u0018\u00010\u0081\u00012\u0007\u0010\u0082\u0001\u001a\u00020\f2\u0007\u0010\u0083\u0001\u001a\u00020\u0011J\u0019\u0010\u0084\u0001\u001a\u00020+2\u0007\u0010\u0085\u0001\u001a\u00020-2\u0007\u0010\u0086\u0001\u001a\u00020uJ'\u0010\u0087\u0001\u001a\u00020+2\t\u0010\u0088\u0001\u001a\u0004\u0018\u00010\n2\u0007\u0010\u0089\u0001\u001a\u00020\n2\b\u0010T\u001a\u0004\u0018\u00010UH\u0002J\u0017\u0010\u008a\u0001\u001a\u00020+2\u000e\u0010;\u001a\n\u0012\u0004\u0012\u00020\b\u0018\u000106J\u0018\u0010\u008b\u0001\u001a\u00020+2\u0006\u0010F\u001a\u00020\f2\u0007\u0010\u008c\u0001\u001a\u00020\u0011J\u0019\u0010\u008d\u0001\u001a\u00020+2\u0007\u0010\u008e\u0001\u001a\u00020\u00112\u0007\u0010\u008f\u0001\u001a\u00020\u000fJ\u0007\u0010\u0090\u0001\u001a\u00020\u0011J\u0010\u0010\u0090\u0001\u001a\u00020\u00112\u0007\u0010\u0091\u0001\u001a\u00020\fJ\t\u0010\u0092\u0001\u001a\u00020+H\u0002J*\u0010\u0093\u0001\u001a\u00020+2\u0007\u0010\u0091\u0001\u001a\u00020\f2\u0006\u0010j\u001a\u00020\f2\u0007\u0010\u0094\u0001\u001a\u00020\f2\u0007\u0010\u0095\u0001\u001a\u00020\u0016J+\u0010\u0096\u0001\u001a\u00020+2\u0007\u0010\u0097\u0001\u001a\u00020\f2\u0007\u0010\u0098\u0001\u001a\u00020\f2\u0007\u0010\u0099\u0001\u001a\u00020\f2\u0007\u0010\u009a\u0001\u001a\u00020\fJ\u0013\u0010\u009b\u0001\u001a\u00020+2\b\u0010\u009c\u0001\u001a\u00030\u009d\u0001H\u0016J\t\u0010\u009e\u0001\u001a\u00020+H\u0002J\t\u0010\u009f\u0001\u001a\u00020+H\u0002J\u0019\u0010 \u0001\u001a\u00020+2\u0007\u0010¡\u0001\u001a\u00020\f2\u0007\u0010¢\u0001\u001a\u00020\fJ\u0019\u0010 \u0001\u001a\u00020+2\u0007\u0010¡\u0001\u001a\u00020\u000f2\u0007\u0010¢\u0001\u001a\u00020\u000fJ\u0007\u0010£\u0001\u001a\u00020+J\u0010\u0010¤\u0001\u001a\u00020+2\u0007\u0010¥\u0001\u001a\u00020\fJ\u0012\u0010¦\u0001\u001a\u00020+2\t\u0010§\u0001\u001a\u0004\u0018\u00010\u0014J\u001a\u0010¨\u0001\u001a\u00020+2\b\u0010©\u0001\u001a\u00030ª\u00012\u0007\u0010«\u0001\u001a\u00020\u0011J\u0011\u0010¬\u0001\u001a\u00020+2\b\u0010\u00ad\u0001\u001a\u00030ª\u0001J\u001a\u0010®\u0001\u001a\u00020+2\b\u0010\u00ad\u0001\u001a\u00030ª\u00012\u0007\u0010«\u0001\u001a\u00020\u0011J\u001c\u0010¯\u0001\u001a\u00020+2\b\u0010©\u0001\u001a\u00030ª\u00012\u0007\u0010«\u0001\u001a\u00020\u0011H\u0002J\u000f\u0010°\u0001\u001a\u00020\u00112\u0006\u0010F\u001a\u00020\fJ\u0007\u0010±\u0001\u001a\u00020\u0011J\b\u0010²\u0001\u001a\u00030³\u0001J\b\u0010´\u0001\u001a\u00030³\u0001J\u0007\u0010µ\u0001\u001a\u00020+J\u0007\u0010¶\u0001\u001a\u00020+J\u0013\u0010·\u0001\u001a\u0004\u0018\u00010\n2\u0006\u0010f\u001a\u00020\bH\u0002J\u0016\u0010·\u0001\u001a\u0004\u0018\u00010\n2\t\u0010¸\u0001\u001a\u0004\u0018\u00010\nH\u0002J\u0011\u0010¹\u0001\u001a\u00020+2\b\u0010T\u001a\u0004\u0018\u00010\u001fJ\"\u0010º\u0001\u001a\u00020+2\u000e\u0010Y\u001a\n\u0012\u0004\u0012\u00020\f\u0018\u0001062\t\u0010T\u001a\u0005\u0018\u00010»\u0001J\u0007\u0010¼\u0001\u001a\u00020\fJ\u0011\u0010½\u0001\u001a\u00020+2\b\u0010T\u001a\u0004\u0018\u00010'J\u0007\u0010¾\u0001\u001a\u00020+J\u0007\u0010¿\u0001\u001a\u00020+J\u000f\u0010À\u0001\u001a\u00020+2\u0006\u0010i\u001a\u00020\fJ\t\u0010Á\u0001\u001a\u00020\u0011H\u0002J\t\u0010Â\u0001\u001a\u00020+H\u0002J\u0007\u0010Ã\u0001\u001a\u00020+R\u0014\u0010\u0006\u001a\b\u0012\u0004\u0012\u00020\b0\u0007X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\t\u001a\u00020\nX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u000b\u001a\u00020\fX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\r\u001a\u00020\fX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u000e\u001a\u00020\u000fX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0010\u001a\u00020\u0011X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0012\u001a\u00020\fX\u0082\u000e¢\u0006\u0002\n\u0000R\u0010\u0010\u0013\u001a\u0004\u0018\u00010\u0014X\u0082\u000e¢\u0006\u0002\n\u0000R\u0011\u0010\u0015\u001a\u00020\u0016¢\u0006\b\n\u0000\u001a\u0004\b\u0017\u0010\u0018R\u000e\u0010\u0019\u001a\u00020\fX\u0082D¢\u0006\u0002\n\u0000R\u001a\u0010\u001a\u001a\u00020\u0011X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u001a\u0010\u001b\"\u0004\b\u001c\u0010\u001dR\u0010\u0010\u001e\u001a\u0004\u0018\u00010\u001fX\u0082\u000e¢\u0006\u0002\n\u0000R\u0010\u0010 \u001a\u0004\u0018\u00010!X\u0082\u000e¢\u0006\u0002\n\u0000R\u0010\u0010\"\u001a\u0004\u0018\u00010!X\u0082\u000e¢\u0006\u0002\n\u0000R\u0012\u0010#\u001a\u00060$R\u00020\u0000X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010%\u001a\u00020\u0011X\u0082\u000e¢\u0006\u0002\n\u0000R\u0010\u0010&\u001a\u0004\u0018\u00010'X\u0082\u000e¢\u0006\u0002\n\u0000R\u001a\u0010(\u001a\u00020\u0011X\u0096\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b(\u0010\u001b\"\u0004\b)\u0010\u001dR\u001a\u0010,\u001a\u00020-X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b.\u0010/\"\u0004\b0\u00101R\u001a\u00102\u001a\u00020-X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b3\u0010/\"\u0004\b4\u00101R\u001a\u00105\u001a\b\u0012\u0004\u0012\u00020\f068BX\u0082\u0004¢\u0006\u0006\u001a\u0004\b7\u00108R\u0017\u0010;\u001a\b\u0012\u0004\u0012\u00020\b068F¢\u0006\u0006\u001a\u0004\b<\u00108R&\u0010=\u001a\u00020>2\u0006\u0010=\u001a\u00020>8V@VX\u0096\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b?\u0010@\"\u0004\bA\u0010B¨\u0006Ç\u0001"}, d2 = {"Lcom/miui/libhandwritingpreview/model/PageInkPreModel;", "Lcom/miui/libhandwritingpreview/model/IPagePreInkModel;", "view", "Lcom/miui/libhandwritingpreview/view/CreationShieldPrePageInkView;", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "(Lcom/miui/libhandwritingpreview/view/CreationShieldPrePageInkView;)V", "mList", "", "Lcom/miui/libhandwritingpage/newbean/beanPageInfo/PageItemData;", "pageDataTempDir", "", "searchTop", "", "mKeyBoardState", "mCursorHeight", "", "isSearchScrollBy", "", "totalScrollDy", "mStateView", "Lcom/miui/libhandwritingpage/listener/PageStateView;", "searchWrapper", "Lcom/miui/libhandwritingpage/newbean/SearchWrapper;", "getSearchWrapper", "()Lcom/miui/libhandwritingpage/newbean/SearchWrapper;", "UNINTENDED_TOUCH_HEIGHT", "isUnLimitToNormal", "()Z", "setUnLimitToNormal", "(Z)V", "mPageSizeListener", "Lcom/miui/libhandwritingpage/listener/PageSizeListener;", "mImportPdfBitmap", "Lcom/miui/libhandwritingpage/view/ImportPdfBitmap;", "mExportPdfBitmap", "windowInfo", "Lcom/miui/libhandwritingpreview/model/PageInkPreModel$WindowInfo;", "mExportLoaded", "mPdfThumbnailLoadListener", "Lcom/miui/libhandwritingpage/listener/PdfThumbnailLoadListener;", "isUnlimited", "setUnlimited", "waitForIdle", "", "fullScreenRunnable", "Ljava/lang/Runnable;", "getFullScreenRunnable", "()Ljava/lang/Runnable;", "setFullScreenRunnable", "(Ljava/lang/Runnable;)V", "fenestraRunnable", "getFenestraRunnable", "setFenestraRunnable", "itemLoadList", "", "getItemLoadList", "()Ljava/util/List;", "bindItemData", "bindView", "itemDataList", "getItemDataList", "engineConfig", "Lxc/engine/utils/EngineConfig;", "getEngineConfig", "()Lxc/engine/utils/EngineConfig;", "setEngineConfig", "(Lxc/engine/utils/EngineConfig;)V", "getDirPath", "getPdfId", "onCurrentPageChanged", "position", "onPageSizeChanged", "onFooterViewMaxHeight", "isMaxPageCount", "getPageCount", "dispatchTouchEvent", "event", "Landroid/view/MotionEvent;", "isTouchEventOutsidePageArea", "processGesture", "processEndAction", "initEntPages", "load", "filePath", "listener", "Lcom/miui/libhandwritingpage/listener/PageLoadListener;", "initLoadData", "createItemView", "loadPdfBitmap", "list", "getPageIndexTable", "Ljava/util/LinkedHashMap;", "Lkotlin/collections/LinkedHashMap;", "create", "bean", "Lcom/miui/libhandwritingpage/newbean/beanPageInfo/PageBean;", "isUnLimit", "getBgStyle", "Lcom/miui/libhandwritingpreview/system/PreBgSystem$EBgStyle;", "type", "addSystemPages", "addSystemPage", "itemData", "isInit", "setPageBGColor", "index", "color", "setPageBGStyle", "bgGrid", "initData", "entFilePath", "updateCurrentViewItem", AnimatedProperty.PROPERTY_NAME_X, AnimatedProperty.PROPERTY_NAME_Y, "getScale", "getFactoryScale", "getPageTotalSavePoint", "", "addPage", "lastData", "removePageChangeStep", "isUnlimitedOnly", "saveEntFile", "saveListener", "Lcom/miui/libhandwritingpage/listener/PageSaveListener;", "getItemRectF", "Landroid/graphics/RectF;", "scrollOnKeyBoardHeightChanged", "activity", "Landroid/app/Activity;", "keyBoardHeight", "isPut", "scrollByKeyboard", "runnable", "delay", "parsePdfFile", "pdfId", "password", "addItemList", "jumpPage", "needAnim", "resetScale", "isNeedReset", "scale", "isContentEmpty", "pageIndex", "updateContentEmpty", "setSearchResult", "alpha", "wrapper", "onSizeChanged", AnimatedProperty.PROPERTY_NAME_W, AnimatedProperty.PROPERTY_NAME_H, "oldw", "oldh", "onConfigurationChanged", "newConfig", "Landroid/content/res/Configuration;", "onPageChanged", "onPreviewChanged", "scrollBy", "offX", "offY", "destroy", "updateStateView", "currentPosition", "setStateView", "stateView", "setPageSizeConfig", "pageSizeConfig", "Lxc/engine/utils/PageSizeConfig;", "isFromCancel", "normalToUnLimit", "config", "unLimitToNormal", "changedPageSizeConfig", "canChangeSizeConfig", "invalidPosition", "calcAllCurveBound", "", "calcAllSpanBound", "resume", "pageLoadingFinish", "getBgImagePath", "bgImage", "setPageSizeListener", "getPdfBitmapForExport", "Lcom/miui/libhandwritingpage/listener/PDFExportListener;", "getWindowWidth", "getMultiPageThumbnailStatus", "bindItemDataForExport", "bindExportFinish", "bindPageImage", "checkLoadFinish", "loadFinish", "clearHighLight", "IUnBindListener", "WindowInfo", "Companion", "libHandWritingPreview_release"}, k = 1, mv = {2, 2, 0}, xi = 48)
public final class PageInkPreModel extends IPagePreInkModel {
    private static final String TAG;
    private final int UNINTENDED_TOUCH_HEIGHT;
    private EngineConfig engineConfig;
    private Runnable fenestraRunnable;
    private Runnable fullScreenRunnable;
    private boolean isSearchScrollBy;
    private boolean isUnLimitToNormal;
    private boolean isUnlimited;
    private float mCursorHeight;
    private boolean mExportLoaded;
    private ImportPdfBitmap mExportPdfBitmap;
    private ImportPdfBitmap mImportPdfBitmap;
    private int mKeyBoardState;
    private final List<PageItemData> mList;
    private PageSizeListener mPageSizeListener;
    private PdfThumbnailLoadListener mPdfThumbnailLoadListener;
    private PageStateView mStateView;
    private final String pageDataTempDir;
    private int searchTop;
    private final SearchWrapper searchWrapper;
    private int totalScrollDy;
    private final WindowInfo windowInfo;

    /* JADX INFO: compiled from: PageInkPreModel.kt */
    @Metadata(d1 = {"\u0000\u001c\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0000\bf\u0018\u00002\u00020\u0001J\u001a\u0010\u0002\u001a\u00020\u00032\b\u0010\u0004\u001a\u0004\u0018\u00010\u00052\u0006\u0010\u0006\u001a\u00020\u0007H&¨\u0006\bÀ\u0006\u0003"}, d2 = {"Lcom/miui/libhandwritingpreview/model/PageInkPreModel$IUnBindListener;", "", "unBindSuc", "", "itemView", "Lcom/miui/libhandwritingpreview/view/PagePreItemView;", IntegerTokenConverter.CONVERTER_KEY, "", "libHandWritingPreview_release"}, k = 1, mv = {2, 2, 0}, xi = 48)
    public interface IUnBindListener {
        void unBindSuc(PagePreItemView itemView, int i);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Object waitForIdle$lambda$0() {
        return null;
    }

    public final void resume() {
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public PageInkPreModel(CreationShieldPrePageInkView view) {
        super(view);
        Intrinsics.checkNotNullParameter(view, "view");
        this.mList = new ArrayList();
        this.mKeyBoardState = 2;
        this.isSearchScrollBy = true;
        this.searchWrapper = new SearchWrapper();
        this.UNINTENDED_TOUCH_HEIGHT = 70;
        this.fullScreenRunnable = new Runnable() { // from class: com.miui.libhandwritingpreview.model.PageInkPreModel$$ExternalSyntheticLambda6
            @Override // java.lang.Runnable
            public final void run() {
                PageInkPreModel.fullScreenRunnable$lambda$1(this.f$0);
            }
        };
        this.fenestraRunnable = new Runnable() { // from class: com.miui.libhandwritingpreview.model.PageInkPreModel$$ExternalSyntheticLambda7
            @Override // java.lang.Runnable
            public final void run() {
                PageInkPreModel.fenestraRunnable$lambda$2(this.f$0);
            }
        };
        this.mKeyBoardState = 1;
        String dir = FileUtil.getDir(getMContext(), FileUtil.TMP_PAGE);
        Intrinsics.checkNotNullExpressionValue(dir, "getDir(...)");
        this.pageDataTempDir = dir;
        this.windowInfo = new WindowInfo();
        setMDrawView(new PagePreDrawView(getMContext()));
        setAdapter(new PagePreAdapter(getMContext(), this, getMStateListener()));
        setLayoutManager(new PagePreLayoutManager(view, this));
        CreationPageSDK.setToken(getMContext());
        this.engineConfig = new EngineConfig();
    }

    public final SearchWrapper getSearchWrapper() {
        return this.searchWrapper;
    }

    /* JADX INFO: renamed from: isUnLimitToNormal, reason: from getter */
    public final boolean getIsUnLimitToNormal() {
        return this.isUnLimitToNormal;
    }

    public final void setUnLimitToNormal(boolean z) {
        this.isUnLimitToNormal = z;
    }

    @Override // com.miui.libhandwritingpreview.model.IPagePreInkModel
    /* JADX INFO: renamed from: isUnlimited, reason: from getter */
    public boolean getIsUnlimited() {
        return this.isUnlimited;
    }

    @Override // com.miui.libhandwritingpreview.model.IPagePreInkModel
    public void setUnlimited(boolean z) {
        this.isUnlimited = z;
    }

    public final void waitForIdle() {
        if (getAdapter() != null) {
            FutureTask futureTask = new FutureTask(new Callable() { // from class: com.miui.libhandwritingpreview.model.PageInkPreModel$$ExternalSyntheticLambda8
                @Override // java.util.concurrent.Callable
                public final Object call() {
                    return PageInkPreModel.waitForIdle$lambda$0();
                }
            });
            PagePreAdapter adapter = getAdapter();
            Intrinsics.checkNotNull(adapter);
            adapter.waitForIdle(futureTask);
            try {
                futureTask.get();
            } catch (Exception unused) {
                Unit unit = Unit.INSTANCE;
            }
        }
    }

    public final Runnable getFullScreenRunnable() {
        return this.fullScreenRunnable;
    }

    public final void setFullScreenRunnable(Runnable runnable) {
        Intrinsics.checkNotNullParameter(runnable, "<set-?>");
        this.fullScreenRunnable = runnable;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void fullScreenRunnable$lambda$1(PageInkPreModel pageInkPreModel) {
        pageInkPreModel.setMIsScrollByTouch(false);
        int i = pageInkPreModel.mKeyBoardState;
        if (i == 1) {
            pageInkPreModel.mKeyBoardState = 2;
            PagePreLayoutManager layoutManager = pageInkPreModel.getLayoutManager();
            Intrinsics.checkNotNull(layoutManager);
            layoutManager.setKeyboardLive(true);
            pageInkPreModel.scrollBy(0, -pageInkPreModel.totalScrollDy);
            return;
        }
        if (i == 3) {
            pageInkPreModel.mKeyBoardState = 1;
            pageInkPreModel.scrollBy(0, pageInkPreModel.totalScrollDy);
            PagePreLayoutManager layoutManager2 = pageInkPreModel.getLayoutManager();
            Intrinsics.checkNotNull(layoutManager2);
            layoutManager2.setKeyboardLive(false);
            pageInkPreModel.totalScrollDy = 0;
        }
    }

    public final Runnable getFenestraRunnable() {
        return this.fenestraRunnable;
    }

    public final void setFenestraRunnable(Runnable runnable) {
        Intrinsics.checkNotNullParameter(runnable, "<set-?>");
        this.fenestraRunnable = runnable;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void fenestraRunnable$lambda$2(PageInkPreModel pageInkPreModel) {
        pageInkPreModel.setMIsScrollByTouch(false);
        int i = pageInkPreModel.mKeyBoardState;
        if (i == 1) {
            if (pageInkPreModel.totalScrollDy < 0) {
                pageInkPreModel.totalScrollDy = 0;
                return;
            }
            pageInkPreModel.mKeyBoardState = 2;
            PagePreLayoutManager layoutManager = pageInkPreModel.getLayoutManager();
            Intrinsics.checkNotNull(layoutManager);
            layoutManager.setKeyboardLive(true);
            pageInkPreModel.scrollBy(0, -pageInkPreModel.totalScrollDy);
            return;
        }
        if (i == 3) {
            pageInkPreModel.mKeyBoardState = 1;
            pageInkPreModel.scrollBy(0, pageInkPreModel.totalScrollDy);
            PagePreLayoutManager layoutManager2 = pageInkPreModel.getLayoutManager();
            Intrinsics.checkNotNull(layoutManager2);
            layoutManager2.setKeyboardLive(false);
            pageInkPreModel.totalScrollDy = 0;
        }
    }

    private final List<Integer> getItemLoadList() {
        ArrayList arrayList = new ArrayList();
        if (getCurrentPosition() >= 0 && getCurrentPosition() < this.mList.size()) {
            arrayList.add(Integer.valueOf(getCurrentPosition()));
        }
        int currentPosition = getCurrentPosition() - 1;
        int currentPosition2 = getCurrentPosition() + 1;
        for (int i = 1; i < 3; i++) {
            if (currentPosition >= 0) {
                arrayList.add(Integer.valueOf(currentPosition));
            }
            if (currentPosition2 < this.mList.size()) {
                arrayList.add(Integer.valueOf(currentPosition2));
            }
            currentPosition--;
            currentPosition2++;
        }
        Log.d(TAG, "getItemLoadList: " + arrayList);
        return arrayList;
    }

    private final void bindItemData() {
        PagePreAdapter adapter = getAdapter();
        if (adapter == null || getCurrentItemView() == null) {
            return;
        }
        List<Integer> itemLoadList = getItemLoadList();
        PagePreItemView currentItemView = getCurrentItemView();
        Intrinsics.checkNotNull(currentItemView);
        adapter.onBindItemView(currentItemView, itemLoadList);
    }

    public final void bindView() {
        int mWindowWidth;
        int mWindowWidth2;
        if (this.mList.isEmpty()) {
            Log.e(TAG, "[bindView] init failed, mList is empty");
            return;
        }
        if (getCurrentItemView() == null) {
            Log.e(TAG, "bindView: surfaceView is not ready");
            return;
        }
        PageItemData pageItemData = this.mList.get(0);
        PagePreAdapter adapter = getAdapter();
        Intrinsics.checkNotNull(adapter);
        adapter.updateItemView(getMVisibleWidth(), getMVisibleHeight(), pageItemData.maxWidth);
        if (pageItemData.unLimitToNormal || pageItemData.isUnLimit) {
            PagePreItemView currentItemView = getCurrentItemView();
            Intrinsics.checkNotNull(currentItemView);
            currentItemView.setUnLimit(pageItemData.isUnLimit);
            pageItemData.isFixSize = true;
            if (pageItemData.unLimitToNormal) {
                this.isUnLimitToNormal = true;
            }
        } else {
            PagePreItemView currentItemView2 = getCurrentItemView();
            Intrinsics.checkNotNull(currentItemView2);
            currentItemView2.setUnLimit(false);
        }
        if (pageItemData.isFixSize) {
            PagePreItemView currentItemView3 = getCurrentItemView();
            Intrinsics.checkNotNull(currentItemView3);
            currentItemView3.setSpannedLayoutFixWidth((int) pageItemData.fixWidth);
        }
        PagePreItemView currentItemView4 = getCurrentItemView();
        Intrinsics.checkNotNull(currentItemView4);
        if (currentItemView4.getCreationEngine() == null) {
            Log.e(TAG, "bindView: creationEngine is not ready");
            return;
        }
        PagePreItemView currentItemView5 = getCurrentItemView();
        Intrinsics.checkNotNull(currentItemView5);
        PreICreationEngine creationEngine = currentItemView5.getCreationEngine();
        Intrinsics.checkNotNull(creationEngine);
        creationEngine.getCanvasOperator().setCanvasDrawWidth(this.windowInfo.getMWindowWidth());
        PagePreItemView currentItemView6 = getCurrentItemView();
        Intrinsics.checkNotNull(currentItemView6);
        currentItemView6.setSpannedLayoutFixMode(pageItemData.isFixSize);
        PagePreItemView currentItemView7 = getCurrentItemView();
        Intrinsics.checkNotNull(currentItemView7);
        PreICreationEngine creationEngine2 = currentItemView7.getCreationEngine();
        Intrinsics.checkNotNull(creationEngine2);
        ICanvasOperator canvasOperator = creationEngine2.getCanvasOperator();
        float f = pageItemData.maxWidth;
        PagePreLayoutManager layoutManager = getLayoutManager();
        Intrinsics.checkNotNull(layoutManager);
        canvasOperator.setVisibleFixedWidth(f / layoutManager.getPageScaleRatio());
        PagePreItemView currentItemView8 = getCurrentItemView();
        Intrinsics.checkNotNull(currentItemView8);
        currentItemView8.setId(pageItemData.viewId);
        PagePreItemView currentItemView9 = getCurrentItemView();
        Intrinsics.checkNotNull(currentItemView9);
        PageBean mPageBean = getMPageBean();
        Intrinsics.checkNotNull(mPageBean);
        currentItemView9.updateBackground(mPageBean.path, pageItemData);
        PagePreItemView currentItemView10 = getCurrentItemView();
        Intrinsics.checkNotNull(currentItemView10);
        PageBean mPageBean2 = getMPageBean();
        Intrinsics.checkNotNull(mPageBean2);
        currentItemView10.setRadius(mPageBean2.radius);
        PagePreItemView currentItemView11 = getCurrentItemView();
        Intrinsics.checkNotNull(currentItemView11);
        currentItemView11.isUnLimitToNormal = pageItemData.unLimitToNormal;
        float itemHeight = pageItemData.getItemHeight(getMVisibleWidth(), 1.0f);
        PagePreItemView currentItemView12 = getCurrentItemView();
        Intrinsics.checkNotNull(currentItemView12);
        currentItemView12.setCanvasSize(getMVisibleWidth(), (int) Math.max(getMVisibleHeight(), itemHeight));
        bindItemData();
        int i = pageItemData.sizeUnit;
        if (i != 0) {
            if (i == 1) {
                mWindowWidth2 = pageItemData.width;
                mWindowWidth = pageItemData.height;
            } else if (i != 2) {
                mWindowWidth2 = 0;
                mWindowWidth = 0;
            } else {
                mWindowWidth2 = (int) UIUtils.mm2px(getMContext(), pageItemData.width);
                mWindowWidth = (int) UIUtils.mm2px(getMContext(), pageItemData.height);
            }
        } else if (pageItemData.isFixSize) {
            mWindowWidth = pageItemData.height;
            mWindowWidth2 = pageItemData.width;
        } else {
            mWindowWidth = (pageItemData.height * this.windowInfo.getMWindowWidth()) / pageItemData.width;
            mWindowWidth2 = this.windowInfo.getMWindowWidth();
        }
        boolean z = TextUtils.isEmpty(pageItemData.bgImage) && TextUtils.isEmpty(pageItemData.pdfId);
        pageItemData.maxHeight = (int) Math.max(mWindowWidth, pageItemData.maxHeight);
        pageItemData.maxWidth = (int) Math.max(mWindowWidth2, pageItemData.maxWidth);
        PagePreItemView currentItemView13 = getCurrentItemView();
        Intrinsics.checkNotNull(currentItemView13);
        currentItemView13.setShieldWidth(z ? (pageItemData.maxWidth - mWindowWidth2) / 2 : 0);
        PagePreItemView currentItemView14 = getCurrentItemView();
        Intrinsics.checkNotNull(currentItemView14);
        currentItemView14.setBottomShieldHeight(z ? pageItemData.maxHeight - mWindowWidth : 0);
        PagePreItemView currentItemView15 = getCurrentItemView();
        Intrinsics.checkNotNull(currentItemView15);
        currentItemView15.setUnlimitedSize(getMVisibleWidth(), getMVisibleHeight());
        PagePreItemView currentItemView16 = getCurrentItemView();
        Intrinsics.checkNotNull(currentItemView16);
        currentItemView16.update(0, pageItemData.layoutRectF, 0.0f, 0.0f);
    }

    public final List<PageItemData> getItemDataList() {
        return this.mList;
    }

    @Override // com.miui.libhandwritingpreview.model.IPagePreInkModel
    public EngineConfig getEngineConfig() {
        return this.engineConfig;
    }

    @Override // com.miui.libhandwritingpreview.model.IPagePreInkModel
    public void setEngineConfig(EngineConfig engineConfig) {
        Intrinsics.checkNotNullParameter(engineConfig, "engineConfig");
        super.setEngineConfig(engineConfig);
    }

    public final String getDirPath() {
        if (getMPageBean() == null) {
            return null;
        }
        PageBean mPageBean = getMPageBean();
        Intrinsics.checkNotNull(mPageBean);
        return mPageBean.path;
    }

    public final String getPdfId() {
        String pdfId = this.mList.get(0).pdfId;
        Intrinsics.checkNotNullExpressionValue(pdfId, "pdfId");
        return pdfId;
    }

    public final void onCurrentPageChanged(int position) {
        if (getCurrentPosition() != position) {
            String str = TAG;
            StringCompanionObject stringCompanionObject = StringCompanionObject.INSTANCE;
            String str2 = String.format("--debug-- onCurrentPageChanged change currentPosition [%d] to [%d]", Arrays.copyOf(new Object[]{Integer.valueOf(getCurrentPosition()), Integer.valueOf(position)}, 2));
            Intrinsics.checkNotNullExpressionValue(str2, "format(...)");
            Log.i(str, str2);
            setCurrentPosition(position);
            bindItemData();
            loadPdfBitmap(this.mList);
        }
        if (this.mStateView != null) {
            updateStateView(getCurrentPosition());
        }
        AlgorithmNative algorithmNative = AlgorithmNative.getInstance();
        PagePreLayoutManager layoutManager = getLayoutManager();
        Intrinsics.checkNotNull(layoutManager);
        algorithmNative.setScaleRate(layoutManager.getScale());
        if (getMStateListener() == null || getCurrentItemView() == null) {
            return;
        }
        PageStateListener mStateListener = getMStateListener();
        Intrinsics.checkNotNull(mStateListener);
        PagePreItemView currentItemView = getCurrentItemView();
        Intrinsics.checkNotNull(currentItemView);
        mStateListener.onPageScale(currentItemView.getScale());
    }

    public final void onPageSizeChanged(int position) {
        if (getCurrentPosition() != position) {
            String str = TAG;
            StringCompanionObject stringCompanionObject = StringCompanionObject.INSTANCE;
            String str2 = String.format("--debug-- onPageSizeChanged change currentPosition [%d] to [%d]", Arrays.copyOf(new Object[]{Integer.valueOf(getCurrentPosition()), Integer.valueOf(position)}, 2));
            Intrinsics.checkNotNullExpressionValue(str2, "format(...)");
            Log.i(str, str2);
            setCurrentPosition(position);
        }
        if (this.mStateView != null) {
            updateStateView(getCurrentPosition());
        }
        AlgorithmNative algorithmNative = AlgorithmNative.getInstance();
        PagePreLayoutManager layoutManager = getLayoutManager();
        Intrinsics.checkNotNull(layoutManager);
        algorithmNative.setScaleRate(layoutManager.getScale());
        this.mPageInkView.requestLayout();
        if (this.isSearchScrollBy) {
            setSearchResult(position, this.searchWrapper.searchColor, this.searchWrapper.searchAlpha, this.searchWrapper);
        }
        if (getMStateListener() == null || getCurrentItemView() == null) {
            return;
        }
        PageStateListener mStateListener = getMStateListener();
        Intrinsics.checkNotNull(mStateListener);
        PagePreItemView currentItemView = getCurrentItemView();
        Intrinsics.checkNotNull(currentItemView);
        mStateListener.onPageScale(currentItemView.getScale());
    }

    public final void onFooterViewMaxHeight() {
        CreationPageSDK.with(getMContext()).post(new Runnable() { // from class: com.miui.libhandwritingpreview.model.PageInkPreModel$$ExternalSyntheticLambda5
            @Override // java.lang.Runnable
            public final void run() {
                PageInkPreModel.onFooterViewMaxHeight$lambda$3(this.f$0);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void onFooterViewMaxHeight$lambda$3(PageInkPreModel pageInkPreModel) {
        if (pageInkPreModel.mList.size() >= 500) {
            return;
        }
        pageInkPreModel.addPage(pageInkPreModel.mList.get(pageInkPreModel.getPageCount() - 1));
    }

    public final boolean isMaxPageCount() {
        return this.mList.size() >= 500;
    }

    public final int getPageCount() {
        return this.mList.size();
    }

    public final boolean dispatchTouchEvent(MotionEvent event) {
        Intrinsics.checkNotNullParameter(event, "event");
        int actionMasked = event.getActionMasked();
        setDrawing((actionMasked == 1 || actionMasked == 3) ? false : true);
        if (actionMasked != 0 && getIsDownWithNavigationBar()) {
            return true;
        }
        if (actionMasked == 0) {
            setScaling(false);
            setDownWithNavigationBar(false);
            if ((event.getToolType(0) & 2) != 0) {
                if (Math.ceil(event.getY()) + ((double) UIUtils.getNavigationBarHeight(getMContext())) >= getMVisibleHeight()) {
                    setDownWithNavigationBar(true);
                }
            } else if (Math.ceil(event.getY()) + ((double) this.UNINTENDED_TOUCH_HEIGHT) >= getMVisibleHeight()) {
                setDownWithNavigationBar(true);
            }
            PagePreLayoutManager layoutManager = getLayoutManager();
            Intrinsics.checkNotNull(layoutManager);
            layoutManager.onTouchDown();
            setDownPos(isTouchEventOutsidePageArea(event) ? -1 : 0);
            if (getIsDownWithNavigationBar()) {
                return true;
            }
            processGesture(event);
            if (getDownPos() == -1) {
                return true;
            }
            if (getCurrentItemView() != null) {
                PagePreItemView currentItemView = getCurrentItemView();
                Intrinsics.checkNotNull(currentItemView);
                if (currentItemView.getCreationEngine() != null) {
                    PagePreItemView currentItemView2 = getCurrentItemView();
                    Intrinsics.checkNotNull(currentItemView2);
                    PreICreationEngine creationEngine = currentItemView2.getCreationEngine();
                    Intrinsics.checkNotNull(creationEngine);
                    setDownFlinging(creationEngine.getCurveEngine().getCamera().getBIsFlinging());
                    if (getIsDownFlinging()) {
                        PagePreDrawView mDrawView = getMDrawView();
                        Intrinsics.checkNotNull(mDrawView);
                        PreICreationEngine creationEngine2 = mDrawView.getCreationEngine();
                        Intrinsics.checkNotNull(creationEngine2);
                        setDownFlinging(creationEngine2.getCurveEngine().getCamera().stopFling());
                    }
                }
            }
            setCancelTouch(false);
            updateCurrentViewItem(event.getX(), event.getY());
            if (!isTouchEventOutsidePageArea(event) && getIsCancelTouch()) {
                setCancelTouch(false);
            }
        } else if (actionMasked == 1) {
            processGesture(event);
            processEndAction(event);
        } else if (actionMasked == 2) {
            processGesture(event);
        } else if (actionMasked == 3) {
            processGesture(event);
            processEndAction(event);
        } else if (actionMasked == 5) {
            PagePreLayoutManager layoutManager2 = getLayoutManager();
            Intrinsics.checkNotNull(layoutManager2);
            layoutManager2.resetExpanse();
            processGesture(event);
            if (event.getPointerCount() > 1 && !getIsCancelTouch()) {
                setCancelTouch(true);
            }
        }
        return true;
    }

    private final boolean isTouchEventOutsidePageArea(MotionEvent event) {
        if (!getIsUnlimited() && getMDrawView() != null) {
            PagePreDrawView mDrawView = getMDrawView();
            Intrinsics.checkNotNull(mDrawView);
            if (mDrawView.getCreationEngine() != null) {
                PointF pointF = new PointF(event.getX(), event.getY());
                PagePreDrawView mDrawView2 = getMDrawView();
                Intrinsics.checkNotNull(mDrawView2);
                PreICreationEngine creationEngine = mDrawView2.getCreationEngine();
                Intrinsics.checkNotNull(creationEngine);
                int pageIndexByScreenSpacePoint = creationEngine.getCurveEngine().getPageSystem().getPageIndexByScreenSpacePoint(pointF);
                PagePreDrawView mDrawView3 = getMDrawView();
                Intrinsics.checkNotNull(mDrawView3);
                PreICreationEngine creationEngine2 = mDrawView3.getCreationEngine();
                Intrinsics.checkNotNull(creationEngine2);
                RectF area = creationEngine2.getCurveEngine().getPageSystem().getPageData(pageIndexByScreenSpacePoint).getArea();
                PagePreDrawView mDrawView4 = getMDrawView();
                Intrinsics.checkNotNull(mDrawView4);
                PreICreationEngine creationEngine3 = mDrawView4.getCreationEngine();
                Intrinsics.checkNotNull(creationEngine3);
                PointF worldSpacePointFromScreenSpacePoint = creationEngine3.getCurveEngine().getWorldSpacePointFromScreenSpacePoint(pointF);
                if (!area.contains(worldSpacePointFromScreenSpacePoint.x, worldSpacePointFromScreenSpacePoint.y) && event.getPointerCount() == 1) {
                    Log.d(TAG, "ignore !! outside draw area, action = " + event.getAction());
                    return true;
                }
            }
        }
        return false;
    }

    private final void processGesture(MotionEvent event) {
        setPointCount(event.getPointerCount());
        setHasDownTime(event.getEventTime() - event.getDownTime());
        getMGestureDetector().onTouchEvent(event);
        getMScaleGestureDetector().onTouchEvent(event);
    }

    private final void processEndAction(MotionEvent event) {
        PagePreLayoutManager layoutManager = getLayoutManager();
        Intrinsics.checkNotNull(layoutManager);
        layoutManager.onScrollEndNew();
        if (getMDrawView() != null) {
            PagePreDrawView mDrawView = getMDrawView();
            Intrinsics.checkNotNull(mDrawView);
            if (mDrawView.getCreationEngine() != null) {
                PagePreDrawView mDrawView2 = getMDrawView();
                Intrinsics.checkNotNull(mDrawView2);
                PreICreationEngine creationEngine = mDrawView2.getCreationEngine();
                Intrinsics.checkNotNull(creationEngine);
                if (creationEngine.getCurveEngine() != null) {
                    PagePreDrawView mDrawView3 = getMDrawView();
                    Intrinsics.checkNotNull(mDrawView3);
                    PreICreationEngine creationEngine2 = mDrawView3.getCreationEngine();
                    Intrinsics.checkNotNull(creationEngine2);
                    creationEngine2.getCurveEngine().getCamera().onScrollEnd();
                }
            }
        }
        if (getDownPos() == -1 || (event.getToolType(0) & 1) != 0) {
            PagePreLayoutManager layoutManager2 = getLayoutManager();
            Intrinsics.checkNotNull(layoutManager2);
            layoutManager2.onTouchEnd();
            setCancelTouch(false);
        }
        if (getIsCancelTouch()) {
            PagePreLayoutManager layoutManager3 = getLayoutManager();
            Intrinsics.checkNotNull(layoutManager3);
            layoutManager3.onTouchEnd();
            setCancelTouch(false);
        }
    }

    private final void initEntPages() {
        int i;
        InkEntFileIOHelper.INSTANCE.clear();
        int mWindowWidth = this.windowInfo.getMWindowWidth();
        String str = TAG;
        StringCompanionObject stringCompanionObject = StringCompanionObject.INSTANCE;
        String str2 = String.format("initEntPages: getWindowWidth[%d]", Arrays.copyOf(new Object[]{Integer.valueOf(mWindowWidth)}, 1));
        Intrinsics.checkNotNullExpressionValue(str2, "format(...)");
        Log.i(str, str2);
        InkEntFileIOHelper.INSTANCE.setScreenWidth(mWindowWidth);
        int i2 = 0;
        for (PageItemData pageItemData : this.mList) {
            if (!pageItemData.unLimitToNormal || pageItemData.maxWidth <= pageItemData.width) {
                i = mWindowWidth;
            } else {
                int dimension = ((int) getMContext().getResources().getDimension(R.dimen.creation_paint_view_margin)) * 2;
                if (pageItemData.width + dimension >= mWindowWidth) {
                    i = pageItemData.maxWidth;
                } else {
                    i = (pageItemData.maxWidth * mWindowWidth) / (pageItemData.width + dimension);
                }
            }
            float f = (pageItemData.height * mWindowWidth) / pageItemData.width;
            pageItemData.width = mWindowWidth;
            int i3 = (int) f;
            pageItemData.height = i3;
            pageItemData.maxWidth = (int) Math.max(pageItemData.maxWidth, mWindowWidth);
            pageItemData.maxHeight = i3;
            Log.i(TAG, "initEntPages: setPageSize w-h[" + i + ", " + f + ']');
            InkEntFileIOHelper.INSTANCE.addPage(i2, i, i3, pageItemData.isUnLimit);
            i2++;
        }
    }

    public final void load(String filePath, final PageLoadListener listener) {
        PageEntInfo pageEnt;
        boolean z;
        setMEntFilePath(filePath);
        setMLoadListener(listener);
        if (TextUtils.isEmpty(filePath)) {
            if (listener != null) {
                listener.onLoadCompleted(false, false, "");
                return;
            }
            return;
        }
        Intrinsics.checkNotNull(filePath);
        File file = new File(filePath);
        if (!file.exists()) {
            PageBean pageBean = new PageBean();
            File parentFile = file.getParentFile();
            Intrinsics.checkNotNull(parentFile);
            pageBean.path = parentFile.getAbsolutePath();
            pageBean.width = 755;
            pageBean.height = 1156;
            pageBean.orientation = (short) 0;
            create(pageBean, listener, false);
            return;
        }
        PagePreAdapter adapter = getAdapter();
        Intrinsics.checkNotNull(adapter);
        adapter.setEntFilePath(filePath);
        if (PageEntTools.verifyFormat(filePath)) {
            try {
                pageEnt = PageEntTools.readPageEnt(filePath, this.pageDataTempDir);
            } catch (Exception e) {
                e.printStackTrace();
                pageEnt = null;
            }
        } else {
            pageEnt = null;
        }
        if (PageEntTools.verifySinglePageFormat(filePath)) {
            try {
                pageEnt = PageEntTools.readSinglePageEnt(filePath, this.pageDataTempDir);
                z = true;
            } catch (Exception e2) {
                e2.printStackTrace();
                z = false;
            }
            Log.d(TAG, "ww-- load singlepage");
        } else {
            z = false;
        }
        if (pageEnt != null) {
            PageEntHelper pageEntHelper = new PageEntHelper();
            setMPageBean(pageEntHelper.loadEnt(filePath, pageEnt));
            this.mList.clear();
            if (z) {
                Log.d(TAG, "canvasType: " + (listener != null ? listener.getSuniaSinglePageBgType() : null));
                List<PageItemData> list = this.mList;
                PageItemData singlePageItemData = pageEntHelper.getSinglePageItemData(pageEnt, listener != null ? listener.getSuniaSinglePageBgType() : null);
                Intrinsics.checkNotNullExpressionValue(singlePageItemData, "getSinglePageItemData(...)");
                list.add(singlePageItemData);
            } else {
                List<PageItemData> itemDataList = pageEntHelper.getItemDataList(pageEnt);
                if (itemDataList != null && itemDataList.size() > 0) {
                    this.mList.addAll(itemDataList);
                }
            }
            PageLoadListener mLoadListener = getMLoadListener();
            if (mLoadListener != null) {
                mLoadListener.onPageCount(this.mList.size());
            }
            if (!this.mList.isEmpty()) {
                List<PageItemData> list2 = this.mList;
                setUnlimited(list2.get(list2.size() - 1).isUnLimit);
            }
            PageSizeListener pageSizeListener = this.mPageSizeListener;
            if (pageSizeListener != null) {
                pageSizeListener.onPageSizeChanged(getIsUnlimited());
            }
        }
        final boolean z2 = pageEnt != null;
        createItemView();
        PagePreItemView currentItemView = getCurrentItemView();
        Intrinsics.checkNotNull(currentItemView);
        PreICreationEngine creationEngine = currentItemView.getCreationEngine();
        Intrinsics.checkNotNull(creationEngine);
        creationEngine.getCurveEngine().getCamera().setRealtimeDrawRatio(this.windowInfo.getMWindowWidth() / 1000.0f);
        PagePreItemView currentItemView2 = getCurrentItemView();
        Intrinsics.checkNotNull(currentItemView2);
        PreICreationEngine creationEngine2 = currentItemView2.getCreationEngine();
        Intrinsics.checkNotNull(creationEngine2);
        creationEngine2.getCurveEngine().getCamera().setScreenSize(getMVisibleWidth(), getMVisibleHeight());
        PagePreItemView currentItemView3 = getCurrentItemView();
        Intrinsics.checkNotNull(currentItemView3);
        currentItemView3.post(new Runnable() { // from class: com.miui.libhandwritingpreview.model.PageInkPreModel$$ExternalSyntheticLambda4
            @Override // java.lang.Runnable
            public final void run() {
                PageInkPreModel.load$lambda$4(z2, this, listener);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void load$lambda$4(boolean z, PageInkPreModel pageInkPreModel, PageLoadListener pageLoadListener) {
        PagePreDrawView mDrawView;
        PreICreationEngine creationEngine;
        PreMiCurveEngine curveEngine;
        PrePageSystem pageSystem;
        Log.d(TAG, "load: Complete:" + z);
        if (!pageInkPreModel.mList.isEmpty()) {
            String pdfId = pageInkPreModel.mList.get(0).pdfId;
            Intrinsics.checkNotNullExpressionValue(pdfId, "pdfId");
            if (pdfId.length() > 0 && (mDrawView = pageInkPreModel.getMDrawView()) != null && (creationEngine = mDrawView.getCreationEngine()) != null && (curveEngine = creationEngine.getCurveEngine()) != null && (pageSystem = curveEngine.getPageSystem()) != null) {
                pageSystem.setPdfMode(true);
            }
        }
        if (z) {
            pageInkPreModel.initLoadData();
            pageInkPreModel.mPageInkView.requestLayout();
        }
        if (pageInkPreModel.getMLoadListener() != null) {
            if (pageInkPreModel.getMPageBean() == null) {
                Intrinsics.checkNotNull(pageLoadListener);
                pageLoadListener.onLoadCompleted(false, false, "");
                return;
            }
            PageLoadListener mLoadListener = pageInkPreModel.getMLoadListener();
            Intrinsics.checkNotNull(mLoadListener);
            PageBean mPageBean = pageInkPreModel.getMPageBean();
            Intrinsics.checkNotNull(mPageBean);
            mLoadListener.onLoadCompleted(z, !TextUtils.isEmpty(mPageBean.pdfPath), pageInkPreModel.getMEntFilePath());
        }
    }

    private final void initLoadData() {
        initEntPages();
        bindView();
        onPageChanged();
        addSystemPages();
        Activity activity = Utils.getActivity(getMContext());
        if (activity != null) {
            PagePreLayoutManager layoutManager = getLayoutManager();
            Intrinsics.checkNotNull(layoutManager);
            layoutManager.initByWindowMode(activity.isInMultiWindowMode());
        }
        loadPdfBitmap(this.mList);
    }

    public final void createItemView() {
        Log.i(TAG, "createItemView");
        if (getCurrentItemView() == null) {
            PagePreAdapter adapter = getAdapter();
            Intrinsics.checkNotNull(adapter);
            setCurrentItemView(adapter.onCreateItemView(getMDrawView()));
            if (getCurrentItemView() != null) {
                this.mPageInkView.addView(getCurrentItemView());
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void loadPdfBitmap(List<? extends PageItemData> list) {
        List<? extends PageItemData> list2 = list;
        if (list2 == null || list2.isEmpty()) {
            return;
        }
        String pdfId = list.get(0).pdfId;
        Intrinsics.checkNotNullExpressionValue(pdfId, "pdfId");
        if (pdfId.length() == 0) {
            return;
        }
        if (this.mImportPdfBitmap == null) {
            ImportPdfBitmap importPdfBitmap = new ImportPdfBitmap();
            this.mImportPdfBitmap = importPdfBitmap;
            Intrinsics.checkNotNull(importPdfBitmap);
            importPdfBitmap.setLoadListener(new PDFLoadingListener() { // from class: com.miui.libhandwritingpreview.model.PageInkPreModel.loadPdfBitmap.1
                @Override // com.miui.libhandwritingpage.listener.PDFLoadingListener
                public void onLoaded() {
                }

                @Override // com.miui.libhandwritingpage.listener.PDFLoadingListener
                public void onLoaded(int pageIndex) {
                    PreICreationEngine creationEngine;
                    PreMiCurveEngine curveEngine;
                    PagePreDrawView mDrawView = PageInkPreModel.this.getMDrawView();
                    PrePageSystem pageSystem = (mDrawView == null || (creationEngine = mDrawView.getCreationEngine()) == null || (curveEngine = creationEngine.getCurveEngine()) == null) ? null : curveEngine.getPageSystem();
                    if (pageSystem == null || !pageSystem.pageHasBgImage(pageIndex)) {
                        ImportPdfBitmap importPdfBitmap2 = PageInkPreModel.this.mImportPdfBitmap;
                        Intrinsics.checkNotNull(importPdfBitmap2);
                        Bitmap bitmap = importPdfBitmap2.getBitmap(pageIndex);
                        if (bitmap != null) {
                            String str = PageInkPreModel.TAG;
                            StringCompanionObject stringCompanionObject = StringCompanionObject.INSTANCE;
                            String str2 = String.format("onLoaded :%d %d %d", Arrays.copyOf(new Object[]{Integer.valueOf(pageIndex), Integer.valueOf(bitmap.getWidth()), Integer.valueOf(bitmap.getHeight())}, 3));
                            Intrinsics.checkNotNullExpressionValue(str2, "format(...)");
                            Log.d(str, str2);
                            if (pageSystem != null) {
                                pageSystem.setPageBgImage(pageIndex, bitmap, false);
                                return;
                            }
                            return;
                        }
                        return;
                    }
                    Log.d(PageInkPreModel.TAG, "onLoaded: pageHasBgImage:" + pageIndex);
                }

                @Override // com.miui.libhandwritingpage.listener.PDFLoadingListener
                public void onFinish() {
                    Log.d(PageInkPreModel.TAG, "onFinish");
                }

                @Override // com.miui.libhandwritingpage.listener.PDFLoadingListener
                public void onRemove(int pageIndex) {
                    PreICreationEngine creationEngine;
                    PreMiCurveEngine curveEngine;
                    Log.d(PageInkPreModel.TAG, "onRemove:" + pageIndex);
                    if (pageIndex != -1) {
                        PagePreDrawView mDrawView = PageInkPreModel.this.getMDrawView();
                        PrePageSystem pageSystem = (mDrawView == null || (creationEngine = mDrawView.getCreationEngine()) == null || (curveEngine = creationEngine.getCurveEngine()) == null) ? null : curveEngine.getPageSystem();
                        if (pageSystem != null) {
                            pageSystem.releasePageBgImage(pageIndex);
                        }
                    }
                }
            });
        }
        LinkedHashMap<Integer, Integer> pageIndexTable = getPageIndexTable(list);
        String str = getDirPath() + File.separator + list.get(0).pdfId;
        ImportPdfBitmap importPdfBitmap2 = this.mImportPdfBitmap;
        Intrinsics.checkNotNull(importPdfBitmap2);
        importPdfBitmap2.setVisibleRect(getMVisibleWidth());
        ImportPdfBitmap importPdfBitmap3 = this.mImportPdfBitmap;
        Intrinsics.checkNotNull(importPdfBitmap3);
        importPdfBitmap3.setData(str, pageIndexTable);
    }

    private final LinkedHashMap<Integer, Integer> getPageIndexTable(List<? extends PageItemData> list) {
        boolean z;
        int currentPosition = getCurrentPosition();
        if (!getMIsFirstjump() || getMFirstJumpPosition() == -1) {
            z = false;
        } else {
            currentPosition = getMFirstJumpPosition();
            setMIsFirstjump(false);
            z = true;
        }
        String str = TAG;
        StringCompanionObject stringCompanionObject = StringCompanionObject.INSTANCE;
        String str2 = String.format("getPageIndexTable: %d %d %d %b", Arrays.copyOf(new Object[]{Integer.valueOf(getCurrentPosition()), Integer.valueOf(getMFirstJumpPosition()), Integer.valueOf(currentPosition), Boolean.valueOf(getMIsFirstjump())}, 4));
        Intrinsics.checkNotNullExpressionValue(str2, "format(...)");
        Log.d(str, str2);
        LinkedHashMap<Integer, Integer> linkedHashMap = new LinkedHashMap<>();
        if (currentPosition >= 0 && currentPosition < list.size()) {
            linkedHashMap.put(Integer.valueOf(list.get(currentPosition).getPageIndex()), Integer.valueOf(list.get(currentPosition).pdfIndex));
        }
        if (currentPosition != 0 || !getMIsFirstjump()) {
            int i = currentPosition - 1;
            int i2 = currentPosition + 1;
            for (int i3 = 1; i3 < 5; i3++) {
                if (i >= 0) {
                    linkedHashMap.put(Integer.valueOf(list.get(i).getPageIndex()), Integer.valueOf(list.get(i).pdfIndex));
                }
                if (i2 < list.size()) {
                    linkedHashMap.put(Integer.valueOf(list.get(i2).getPageIndex()), Integer.valueOf(list.get(i2).pdfIndex));
                }
                i--;
                i2++;
            }
            if (z && !list.isEmpty()) {
                int pageIndex = list.get(0).getPageIndex();
                int i4 = list.get(0).pdfIndex;
                if (!linkedHashMap.containsKey(Integer.valueOf(pageIndex))) {
                    linkedHashMap.put(Integer.valueOf(pageIndex), Integer.valueOf(i4));
                }
            }
        }
        return linkedHashMap;
    }

    public final void create(PageBean bean, PageLoadListener listener, boolean isUnLimit) {
        Intrinsics.checkNotNullParameter(bean, "bean");
        setMPageBean(bean);
        setUnlimited(isUnLimit);
        PageSizeListener pageSizeListener = this.mPageSizeListener;
        if (pageSizeListener != null) {
            Intrinsics.checkNotNull(pageSizeListener);
            pageSizeListener.onPageSizeChanged(getIsUnlimited());
        }
        if (getMPageBean() != null) {
            PageBean mPageBean = getMPageBean();
            Intrinsics.checkNotNull(mPageBean);
            if (!TextUtils.isEmpty(mPageBean.path)) {
                PageBean mPageBean2 = getMPageBean();
                Intrinsics.checkNotNull(mPageBean2);
                if (mPageBean2.width != 0) {
                    PageBean mPageBean3 = getMPageBean();
                    Intrinsics.checkNotNull(mPageBean3);
                    if (mPageBean3.height != 0) {
                        setMLoadListener(listener);
                        StringBuilder sb = new StringBuilder();
                        PageBean mPageBean4 = getMPageBean();
                        Intrinsics.checkNotNull(mPageBean4);
                        setMEntFilePath(sb.append(mPageBean4.path).append(File.separator).append("multi_page_ent.ent").toString());
                        PagePreAdapter adapter = getAdapter();
                        Intrinsics.checkNotNull(adapter);
                        PageBean mPageBean5 = getMPageBean();
                        Intrinsics.checkNotNull(mPageBean5);
                        String path = mPageBean5.path;
                        Intrinsics.checkNotNullExpressionValue(path, "path");
                        adapter.setEntFilePath(path);
                        if (!TextUtils.isEmpty(bean.pdfPath)) {
                            String str = bean.pdfPath;
                            PageBean mPageBean6 = getMPageBean();
                            Intrinsics.checkNotNull(mPageBean6);
                            String strCopyPdfFileToMultiDir = FileUtil.copyPdfFileToMultiDir(str, mPageBean6.path);
                            if (!TextUtils.isEmpty(strCopyPdfFileToMultiDir)) {
                                String pdfPassword = bean.pdfPassword;
                                Intrinsics.checkNotNullExpressionValue(pdfPassword, "pdfPassword");
                                parsePdfFile(strCopyPdfFileToMultiDir, pdfPassword, getMLoadListener());
                                return;
                            }
                        }
                        CreationPageSDK.with(getMContext()).post(new Runnable() { // from class: com.miui.libhandwritingpreview.model.PageInkPreModel$$ExternalSyntheticLambda10
                            @Override // java.lang.Runnable
                            public final void run() {
                                PageInkPreModel.create$lambda$5(this.f$0);
                            }
                        });
                        return;
                    }
                }
            }
        }
        if (listener != null) {
            listener.onLoadCompleted(false, false, "");
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void create$lambda$5(PageInkPreModel pageInkPreModel) {
        pageInkPreModel.initData(pageInkPreModel.getMEntFilePath());
    }

    private final PreBgSystem.EBgStyle getBgStyle(int type) {
        if (type == 1) {
            return PreBgSystem.EBgStyle.HorizontalLine;
        }
        if (type == 3) {
            return PreBgSystem.EBgStyle.Grid;
        }
        if (type == 4) {
            return PreBgSystem.EBgStyle.Dot;
        }
        return PreBgSystem.EBgStyle.None;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void addSystemPages() {
        PrePageSystem.PageInfo pageInfo;
        if (getIsUnlimited()) {
            return;
        }
        if (this.mList.isEmpty()) {
            Log.i(TAG, "addSystemPages list isEmpty");
            return;
        }
        Log.i(TAG, "addSystemPages");
        ArrayList<PrePageSystem.PageInfo> arrayList = new ArrayList<>();
        for (PageItemData pageItemData : this.mList) {
            Color color = new Color(pageItemData.bgColor);
            if (this.isUnLimitToNormal) {
                pageInfo = new PrePageSystem.PageInfo(pageItemData.width, pageItemData.height, pageItemData.maxWidth, pageItemData.maxHeight, color);
            } else {
                pageInfo = new PrePageSystem.PageInfo(pageItemData.width, pageItemData.height, pageItemData.maxWidth, pageItemData.maxHeight, color);
            }
            pageInfo.setBgStyle(getBgStyle(pageItemData.bgGrid));
            pageInfo.setBgImagePath(getBgImagePath(pageItemData));
            arrayList.add(pageInfo);
        }
        PagePreDrawView mDrawView = getMDrawView();
        Intrinsics.checkNotNull(mDrawView);
        PreICreationEngine creationEngine = mDrawView.getCreationEngine();
        Intrinsics.checkNotNull(creationEngine);
        creationEngine.getCurveEngine().getPageSystem().setAllPageInfo(arrayList);
    }

    private final void addSystemPage(PageItemData itemData, boolean isInit) {
        PrePageSystem.PageInfo pageInfo;
        if (getIsUnlimited()) {
            return;
        }
        Log.i(TAG, "addSystemPage");
        Color color = new Color(itemData.bgColor);
        if (this.isUnLimitToNormal) {
            pageInfo = new PrePageSystem.PageInfo(itemData.width, itemData.height, itemData.maxWidth, itemData.maxHeight, color);
        } else {
            pageInfo = new PrePageSystem.PageInfo(itemData.maxWidth, itemData.maxHeight, itemData.maxWidth, itemData.maxHeight, color);
        }
        String bgImagePath = getBgImagePath(itemData);
        if (bgImagePath != null) {
            pageInfo.setBgImagePath(bgImagePath);
        } else if (isInit) {
            PageBean mPageBean = getMPageBean();
            Intrinsics.checkNotNull(mPageBean);
            pageInfo.setBgImagePath(getBgImagePath(mPageBean.bgImage));
        }
        pageInfo.setBgStyle(getBgStyle(itemData.bgGrid));
        PagePreDrawView mDrawView = getMDrawView();
        Intrinsics.checkNotNull(mDrawView);
        PreICreationEngine creationEngine = mDrawView.getCreationEngine();
        Intrinsics.checkNotNull(creationEngine);
        creationEngine.getCurveEngine().getPageSystem().addPage(pageInfo);
    }

    private final void setPageBGColor(int index, int color) {
        if (getIsUnlimited()) {
            return;
        }
        Color color2 = new Color(color);
        PagePreDrawView mDrawView = getMDrawView();
        Intrinsics.checkNotNull(mDrawView);
        PreICreationEngine creationEngine = mDrawView.getCreationEngine();
        Intrinsics.checkNotNull(creationEngine);
        creationEngine.getCurveEngine().getPageSystem().setPageBGColor(index, color2);
    }

    private final void setPageBGStyle(int index, int bgGrid) {
        if (getIsUnlimited()) {
            return;
        }
        PagePreDrawView mDrawView = getMDrawView();
        Intrinsics.checkNotNull(mDrawView);
        PreICreationEngine creationEngine = mDrawView.getCreationEngine();
        Intrinsics.checkNotNull(creationEngine);
        creationEngine.getCurveEngine().getPageSystem().setPageBGStyle(index, getBgStyle(bgGrid));
    }

    /* JADX WARN: Code duplicated, block: B:19:0x00b5  */
    public final void initData(String entFilePath) {
        PageInkPreModel pageInkPreModel;
        PageBean mPageBean = getMPageBean();
        Intrinsics.checkNotNull(mPageBean);
        mPageBean.createTime = System.currentTimeMillis();
        PageBean mPageBean2 = getMPageBean();
        Intrinsics.checkNotNull(mPageBean2);
        int i = mPageBean2.width;
        PageBean mPageBean3 = getMPageBean();
        Intrinsics.checkNotNull(mPageBean3);
        int i2 = mPageBean3.height;
        BgGridConfiguration bgGridConfiguration = new BgGridConfiguration();
        String str = "" + (this.mList.size() - 1);
        PageBean mPageBean4 = getMPageBean();
        Intrinsics.checkNotNull(mPageBean4);
        short s = mPageBean4.orientation;
        PageBean mPageBean5 = getMPageBean();
        Intrinsics.checkNotNull(mPageBean5);
        int i3 = mPageBean5.orientation == 0 ? i : i2;
        PageBean mPageBean6 = getMPageBean();
        Intrinsics.checkNotNull(mPageBean6);
        PageItemData pageItemData = new PageItemData(bgGridConfiguration, "", str, 0, "", 0, s, -1, i3, mPageBean6.orientation == 0 ? i2 : i, (int) System.currentTimeMillis(), "");
        if (getIsUnlimited()) {
            pageItemData.isFixSize = true;
            pageItemData.fixWidth = UIUtils.getRealWidth(getMContext());
        }
        PageBean mPageBean7 = getMPageBean();
        Intrinsics.checkNotNull(mPageBean7);
        if (TextUtils.isEmpty(mPageBean7.pdfPath)) {
            PageBean mPageBean8 = getMPageBean();
            Intrinsics.checkNotNull(mPageBean8);
            if (TextUtils.isEmpty(mPageBean8.bgImage)) {
                pageInkPreModel = this;
            } else {
                pageItemData.isFixSize = true;
                pageItemData.fixWidth = UIUtils.getRealWidth(getMContext());
                pageItemData.useNewSize = true;
                float f = i2 / i;
                pageInkPreModel = this;
                pageItemData.maxWidth = pageInkPreModel.windowInfo.getMWindowWidth();
                pageItemData.maxHeight = (int) (pageInkPreModel.windowInfo.getMWindowWidth() * f);
                String str2 = TAG;
                StringCompanionObject stringCompanionObject = StringCompanionObject.INSTANCE;
                String str3 = String.format("initData %d %d %d %d %f", Arrays.copyOf(new Object[]{Integer.valueOf(i), Integer.valueOf(i2), Integer.valueOf(pageItemData.maxWidth), Integer.valueOf(pageItemData.maxHeight), Float.valueOf(f)}, 5));
                Intrinsics.checkNotNullExpressionValue(str3, "format(...)");
                Log.d(str2, str3);
            }
        } else {
            pageItemData.isFixSize = true;
            pageItemData.fixWidth = UIUtils.getRealWidth(getMContext());
            pageItemData.useNewSize = true;
            float f2 = i2 / i;
            pageInkPreModel = this;
            pageItemData.maxWidth = pageInkPreModel.windowInfo.getMWindowWidth();
            pageItemData.maxHeight = (int) (pageInkPreModel.windowInfo.getMWindowWidth() * f2);
            String str4 = TAG;
            StringCompanionObject stringCompanionObject2 = StringCompanionObject.INSTANCE;
            String str5 = String.format("initData %d %d %d %d %f", Arrays.copyOf(new Object[]{Integer.valueOf(i), Integer.valueOf(i2), Integer.valueOf(pageItemData.maxWidth), Integer.valueOf(pageItemData.maxHeight), Float.valueOf(f2)}, 5));
            Intrinsics.checkNotNullExpressionValue(str5, "format(...)");
            Log.d(str4, str5);
        }
        pageInkPreModel.mList.add(pageItemData);
        pageItemData.setPageIndex(pageInkPreModel.mList.size() - 1);
        pageInkPreModel.onPageChanged();
        pageInkPreModel.initEntPages();
        pageInkPreModel.addSystemPage(pageItemData, true);
        Activity activity = Utils.getActivity(pageInkPreModel.getMContext());
        if (activity != null) {
            PagePreLayoutManager layoutManager = pageInkPreModel.getLayoutManager();
            Intrinsics.checkNotNull(layoutManager);
            layoutManager.initByWindowMode(activity.isInMultiWindowMode());
        }
        if (pageInkPreModel.getMLoadListener() != null) {
            PageLoadListener mLoadListener = pageInkPreModel.getMLoadListener();
            Intrinsics.checkNotNull(mLoadListener);
            PageBean mPageBean9 = pageInkPreModel.getMPageBean();
            Intrinsics.checkNotNull(mPageBean9);
            mLoadListener.onLoadCompleted(true, !TextUtils.isEmpty(mPageBean9.pdfPath), entFilePath);
        }
        pageInkPreModel.mPageInkView.requestLayout();
        if (pageInkPreModel.getMStateListener() != null) {
            PageStateListener mStateListener = pageInkPreModel.getMStateListener();
            Intrinsics.checkNotNull(mStateListener);
            mStateListener.onContentChanged(-1);
        }
    }

    public final void updateCurrentViewItem(float x, float y) {
        setDownPos(-1);
        if (getCurrentItemView() == null) {
            Log.e(TAG, "updateCurrentViewItem currentItemView is null");
            return;
        }
        PagePreItemView currentItemView = getCurrentItemView();
        Intrinsics.checkNotNull(currentItemView);
        RectF rectF = new RectF(currentItemView.getLayout());
        if (y < rectF.top || y > rectF.bottom) {
            Log.e(TAG, "updateCurrentViewItem invalid touch point");
            return;
        }
        PagePreItemView currentItemView2 = getCurrentItemView();
        Intrinsics.checkNotNull(currentItemView2);
        PreICreationEngine creationEngine = currentItemView2.getCreationEngine();
        Intrinsics.checkNotNull(creationEngine);
        creationEngine.setGlobalRulerOperator(null);
        PagePreItemView currentItemView3 = getCurrentItemView();
        Intrinsics.checkNotNull(currentItemView3);
        PreICreationEngine creationEngine2 = currentItemView3.getCreationEngine();
        Intrinsics.checkNotNull(creationEngine2);
        setCurrentPosition(creationEngine2.getCurveEngine().getPageSystem().getPageIndexByScreenSpacePoint(new PointF(x, y)));
        if (rectF.contains(x, y)) {
            setDownPos(getCurrentPosition());
        }
        bindItemData();
        loadPdfBitmap(this.mList);
        this.mPageInkView.requestLayout();
        long pageTotalSavePoint = getPageTotalSavePoint();
        PagePreItemView currentItemView4 = getCurrentItemView();
        Intrinsics.checkNotNull(currentItemView4);
        PreICreationEngine creationEngine3 = currentItemView4.getCreationEngine();
        Intrinsics.checkNotNull(creationEngine3);
        creationEngine3.getDataAccess().setExtraTotalPoint(pageTotalSavePoint);
        if (this.mStateView == null) {
            return;
        }
        updateStateView(getCurrentPosition());
    }

    public final float getScale() {
        PagePreLayoutManager layoutManager = getLayoutManager();
        if (layoutManager != null) {
            return layoutManager.getScale();
        }
        return 1.0f;
    }

    public final float getFactoryScale() {
        PagePreLayoutManager layoutManager = getLayoutManager();
        if (layoutManager != null) {
            return layoutManager.getFactorScale();
        }
        return 1.0f;
    }

    public final long getPageTotalSavePoint() {
        Iterator<PageItemData> it = this.mList.iterator();
        long j = 0;
        while (it.hasNext()) {
            j += it.next().curvTotalSavePoint;
        }
        return j;
    }

    public final void addPage(PageItemData lastData) {
        Intrinsics.checkNotNullParameter(lastData, "lastData");
        if (getCurrentItemView() == null) {
            Log.e(TAG, "addPage currentItemView is null");
            return;
        }
        PageItemData pageItemData = new PageItemData(new BgGridConfiguration(), "", "" + (this.mList.size() - 1), -1, "", 0, getOrientation(), -1, getOrientation() == 0 ? lastData.width : lastData.height, getOrientation() == 0 ? lastData.height : lastData.width, (int) System.currentTimeMillis(), "");
        pageItemData.sizeUnit = lastData.sizeUnit;
        pageItemData.width = lastData.width;
        pageItemData.height = lastData.height;
        pageItemData.pageSizeType = lastData.pageSizeType;
        if (lastData.sizeUnit == 2) {
            pageItemData.maxWidth = (int) UIUtils.mm2px(getMContext(), lastData.width);
            pageItemData.maxHeight = (int) UIUtils.mm2px(getMContext(), lastData.height);
        } else if (lastData.sizeUnit == 1) {
            pageItemData.maxWidth = lastData.width;
            pageItemData.maxHeight = lastData.height;
        } else if (lastData.isFixSize) {
            pageItemData.maxWidth = lastData.width;
            pageItemData.maxHeight = lastData.height;
            if (!TextUtils.isEmpty(lastData.bgImage) || !TextUtils.isEmpty(lastData.pdfId)) {
                pageItemData.width = lastData.maxWidth;
                pageItemData.height = lastData.maxHeight;
                pageItemData.maxWidth = lastData.maxWidth;
                pageItemData.maxHeight = lastData.maxHeight;
            }
        } else {
            float f = lastData.maxHeight / lastData.maxWidth;
            pageItemData.maxWidth = this.windowInfo.getMWindowWidth();
            pageItemData.maxHeight = (int) (this.windowInfo.getMWindowWidth() * f);
        }
        pageItemData.isAdd = true;
        if (lastData.sizeUnit != 0 || lastData.isFixSize) {
            pageItemData.isFixSize = true;
            pageItemData.fixWidth = lastData.fixWidth;
            pageItemData.useNewSize = true;
        }
        this.mList.add(pageItemData);
        pageItemData.setPageIndex(this.mList.size() - 1);
        onPageChanged();
        addSystemPage(pageItemData, false);
        removePageChangeStep(true);
    }

    public final void removePageChangeStep(boolean isUnlimitedOnly) {
        String str = TAG;
        Log.d(str, "removePageChangeStep PageCount " + getPageCount());
        if ((!isUnlimitedOnly || getPageCount() == 2) && getCurrentItemView() != null) {
            PagePreItemView currentItemView = getCurrentItemView();
            Intrinsics.checkNotNull(currentItemView);
            PreICreationEngine creationEngine = currentItemView.getCreationEngine();
            Intrinsics.checkNotNull(creationEngine);
            IStepManager stepManager = creationEngine.getStepManager();
            if (stepManager != null) {
                stepManager.removeChangePageSizeStep(isUnlimitedOnly);
            }
            Log.d(str, "removePageChangeStep clearStep");
        }
    }

    public final void saveEntFile(final PageSaveListener saveListener) {
        if (getMPageBean() != null) {
            PageBean mPageBean = getMPageBean();
            Intrinsics.checkNotNull(mPageBean);
            if (!TextUtils.isEmpty(mPageBean.path)) {
                final PagePreEntHelper pagePreEntHelper = new PagePreEntHelper();
                CreationPageSDK.sExecutorService.execute(new Runnable() { // from class: com.miui.libhandwritingpreview.model.PageInkPreModel$$ExternalSyntheticLambda9
                    @Override // java.lang.Runnable
                    public final void run() {
                        PageInkPreModel.saveEntFile$lambda$6(this.f$0, pagePreEntHelper, saveListener);
                    }
                });
                return;
            }
        }
        if (saveListener != null) {
            saveListener.onDataSaved(false, null, false);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void saveEntFile$lambda$6(PageInkPreModel pageInkPreModel, PagePreEntHelper pagePreEntHelper, PageSaveListener pageSaveListener) {
        pageInkPreModel.updateContentEmpty();
        pageInkPreModel.initEntPages();
        pagePreEntHelper.saveEnt(pageInkPreModel.mList, pageInkPreModel.getCurrentItemView(), pageInkPreModel.getMPageBean(), pageSaveListener);
        pageInkPreModel.onPreviewChanged();
    }

    public final RectF getItemRectF() {
        if (invalidPosition()) {
            return new RectF();
        }
        PagePreItemView currentItemView = getCurrentItemView();
        Intrinsics.checkNotNull(currentItemView);
        return currentItemView.getLayout();
    }

    public final void scrollOnKeyBoardHeightChanged(Activity activity, int keyBoardHeight, boolean isPut) {
        if (getCurrentItemView() == null) {
            return;
        }
        WeakReference weakReference = new WeakReference(activity);
        if (weakReference.get() == null) {
            return;
        }
        if (UIUtils.isInMultiWindowMode((Activity) weakReference.get())) {
            if (UIUtils.isSmallWindowMode((Context) weakReference.get())) {
                if (isPut && this.totalScrollDy == 0) {
                    PagePreItemView currentItemView = getCurrentItemView();
                    Intrinsics.checkNotNull(currentItemView);
                    double cursorHeight = currentItemView.getCursorHeight();
                    PagePreItemView currentItemView2 = getCurrentItemView();
                    Intrinsics.checkNotNull(currentItemView2);
                    float fMin = (float) Math.min(cursorHeight + Math.max(currentItemView2.getLayout().top, 0.0d), getItemRectF().bottom);
                    this.mCursorHeight = fMin;
                    float f = fMin + keyBoardHeight;
                    Object obj = weakReference.get();
                    Intrinsics.checkNotNull(obj);
                    this.totalScrollDy = (int) Math.ceil(f - UIUtils.getAppHeight(((Activity) obj).getWindow()));
                    scrollByKeyboard(this.fenestraRunnable, 300L);
                    return;
                }
                if (isPut || this.mKeyBoardState != 2 || this.totalScrollDy <= 0) {
                    return;
                }
                this.mKeyBoardState = 3;
                this.mCursorHeight = 0.0f;
                scrollByKeyboard(this.fenestraRunnable, 300L);
                return;
            }
            if (this.mKeyBoardState == 1) {
                PagePreItemView currentItemView3 = getCurrentItemView();
                Intrinsics.checkNotNull(currentItemView3);
                double cursorHeight2 = currentItemView3.getCursorHeight();
                PagePreItemView currentItemView4 = getCurrentItemView();
                Intrinsics.checkNotNull(currentItemView4);
                this.mCursorHeight = (float) (cursorHeight2 + Math.max(currentItemView4.getLayout().top, 0.0d));
            }
            int iMin = (int) Math.min((this.mCursorHeight + keyBoardHeight) - getMVisibleHeight(), keyBoardHeight);
            if (iMin != 0 && iMin >= this.totalScrollDy && this.mKeyBoardState == 1) {
                this.totalScrollDy = iMin;
                scrollByKeyboard(this.fullScreenRunnable, 20L);
            } else {
                int i = this.totalScrollDy;
                if (iMin >= i || i == 0 || this.mKeyBoardState != 2) {
                    return;
                }
                this.mKeyBoardState = 3;
                this.mCursorHeight = 0.0f;
                scrollByKeyboard(this.fullScreenRunnable, 20L);
            }
        }
        if (this.mKeyBoardState == 1 && keyBoardHeight == 0) {
            PagePreItemView currentItemView5 = getCurrentItemView();
            Intrinsics.checkNotNull(currentItemView5);
            double cursorHeight3 = currentItemView5.getCursorHeight();
            PagePreItemView currentItemView6 = getCurrentItemView();
            Intrinsics.checkNotNull(currentItemView6);
            this.mCursorHeight = (float) (cursorHeight3 + Math.max(currentItemView6.getLayout().top, 0.0d));
        }
        float fMin2 = (float) Math.min(getItemRectF().height(), this.mCursorHeight);
        this.mCursorHeight = fMin2;
        int iMin2 = (int) Math.min((fMin2 + keyBoardHeight) - getMVisibleHeight(), keyBoardHeight);
        if (iMin2 != 0 && iMin2 >= this.totalScrollDy && this.mKeyBoardState == 1) {
            this.totalScrollDy = iMin2;
            scrollByKeyboard(this.fullScreenRunnable, 20L);
            return;
        }
        int i2 = this.totalScrollDy;
        if (iMin2 >= i2 || i2 == 0 || this.mKeyBoardState != 2) {
            return;
        }
        this.mKeyBoardState = 3;
        this.mCursorHeight = 0.0f;
        scrollByKeyboard(this.fullScreenRunnable, 20L);
    }

    public final void scrollByKeyboard(Runnable runnable, long delay) {
        Intrinsics.checkNotNullParameter(runnable, "runnable");
        CreationPageSDK.sHandler.removeCallbacks(runnable);
        CreationPageSDK.sHandler.postDelayed(runnable, delay);
    }

    private final void parsePdfFile(String pdfId, String password, final PageLoadListener listener) {
        int iMin = (int) Math.min(500.0d, 500 - this.mList.size());
        PageBean mPageBean = getMPageBean();
        Intrinsics.checkNotNull(mPageBean);
        new ImportPDFTask(mPageBean.path, pdfId, iMin, password, new PDFImportListener() { // from class: com.miui.libhandwritingpreview.model.PageInkPreModel$parsePdfFile$task$1
            @Override // com.miui.libhandwritingpage.listener.PDFImportListener
            public void onImportCompleted(List<? extends PageItemData> itemDataList) {
                Intrinsics.checkNotNullParameter(itemDataList, "itemDataList");
                List<? extends PageItemData> list = itemDataList;
                if (!list.isEmpty()) {
                    PageBean mPageBean2 = this.this$0.getMPageBean();
                    Intrinsics.checkNotNull(mPageBean2);
                    mPageBean2.width = itemDataList.get(0).width;
                    PageBean mPageBean3 = this.this$0.getMPageBean();
                    Intrinsics.checkNotNull(mPageBean3);
                    mPageBean3.height = itemDataList.get(0).height;
                }
                for (PageItemData pageItemData : itemDataList) {
                    pageItemData.isFixSize = true;
                    pageItemData.fixWidth = UIUtils.getRealWidth(this.this$0.getMContext());
                }
                this.this$0.addItemList(itemDataList);
                if (!list.isEmpty() && this.this$0.getMDrawView() != null) {
                    PagePreDrawView mDrawView = this.this$0.getMDrawView();
                    Intrinsics.checkNotNull(mDrawView);
                    if (mDrawView.getCreationEngine() != null) {
                        PagePreDrawView mDrawView2 = this.this$0.getMDrawView();
                        Intrinsics.checkNotNull(mDrawView2);
                        PreICreationEngine creationEngine = mDrawView2.getCreationEngine();
                        if ((creationEngine != null ? creationEngine.getCurveEngine() : null) != null) {
                            this.this$0.addSystemPages();
                            PagePreDrawView mDrawView3 = this.this$0.getMDrawView();
                            Intrinsics.checkNotNull(mDrawView3);
                            PreICreationEngine creationEngine2 = mDrawView3.getCreationEngine();
                            Intrinsics.checkNotNull(creationEngine2);
                            creationEngine2.getCurveEngine().getPageSystem().setPdfMode(true);
                            this.this$0.setMIsFirstjump(false);
                            this.this$0.loadPdfBitmap(itemDataList);
                        }
                    }
                }
                PageLoadListener pageLoadListener = listener;
                if (pageLoadListener != null) {
                    boolean z = !list.isEmpty();
                    PageBean mPageBean4 = this.this$0.getMPageBean();
                    Intrinsics.checkNotNull(mPageBean4);
                    pageLoadListener.onLoadCompleted(z, !TextUtils.isEmpty(mPageBean4.pdfPath), this.this$0.getMEntFilePath());
                }
                if (this.this$0.getMStateListener() != null) {
                    PageStateListener mStateListener = this.this$0.getMStateListener();
                    Intrinsics.checkNotNull(mStateListener);
                    mStateListener.onContentChanged(-1);
                }
            }

            @Override // com.miui.libhandwritingpage.listener.PDFImportListener
            public void onPdfPageCount(int count) {
                PageLoadListener pageLoadListener = listener;
                if (pageLoadListener != null) {
                    pageLoadListener.onPageCount(count);
                }
            }

            @Override // com.miui.libhandwritingpage.listener.PDFImportListener
            public void onPdfError(int type) {
                Log.d(PageInkPreModel.TAG, "onPdfError: type:" + type);
                PageLoadListener pageLoadListener = listener;
                if (pageLoadListener != null) {
                    pageLoadListener.onPdfError(type);
                }
            }
        }).execute(new Void[0]);
    }

    public final void addItemList(List<? extends PageItemData> itemDataList) {
        List<? extends PageItemData> list = itemDataList;
        if (list == null || list.isEmpty()) {
            return;
        }
        this.mList.addAll(list);
        onPageChanged();
    }

    public final void jumpPage(int position, boolean needAnim) {
        Log.d(TAG, "jumpPage: position:" + position);
        if (getMIsFirstjump()) {
            setMFirstJumpPosition(position);
        }
        PagePreLayoutManager layoutManager = getLayoutManager();
        Intrinsics.checkNotNull(layoutManager);
        layoutManager.jumpTo(position, needAnim);
    }

    public final void resetScale(boolean isNeedReset, float scale) {
        PagePreDrawView mDrawView = getMDrawView();
        Intrinsics.checkNotNull(mDrawView);
        PreICreationEngine creationEngine = mDrawView.getCreationEngine();
        Intrinsics.checkNotNull(creationEngine);
        creationEngine.getCurveEngine().getCamera().resetScale(isNeedReset, scale);
    }

    public final boolean isContentEmpty() {
        int size = this.mList.size();
        boolean zIsContentEmpty = true;
        for (int i = 0; i < size; i++) {
            zIsContentEmpty = isContentEmpty(i);
            if (!zIsContentEmpty) {
                return false;
            }
        }
        return zIsContentEmpty;
    }

    public final boolean isContentEmpty(int pageIndex) {
        if (pageIndex >= this.mList.size()) {
            return true;
        }
        PageItemData pageItemData = this.mList.get(pageIndex);
        if (TextUtils.isEmpty(pageItemData.pdfId) && TextUtils.isEmpty(pageItemData.bgImage)) {
            if (getCurrentItemView() != null) {
                PagePreItemView currentItemView = getCurrentItemView();
                Intrinsics.checkNotNull(currentItemView);
                return currentItemView.isContentEmpty(pageIndex);
            }
            if (pageItemData.isEngineDataEmpty()) {
                return false;
            }
            ItemSpannedData itemSpannedData = pageItemData.spannedData;
            if ((itemSpannedData == null || itemSpannedData.getSpannedInfo() == null || itemSpannedData.getSpannedInfo().isContentEmpty()) && pageItemData.isContentEmpty) {
                return true;
            }
        }
        return false;
    }

    private final void updateContentEmpty() {
        int size = this.mList.size();
        for (int i = 0; i < size; i++) {
            PageItemData pageItemData = this.mList.get(i);
            if (!TextUtils.isEmpty(pageItemData.pdfId) || !TextUtils.isEmpty(pageItemData.bgImage)) {
                pageItemData.isContentEmpty = false;
            } else if (getCurrentItemView() != null) {
                PagePreItemView currentItemView = getCurrentItemView();
                Intrinsics.checkNotNull(currentItemView);
                if (!currentItemView.isContentEmpty(i)) {
                    pageItemData.isContentEmpty = false;
                }
            } else if (pageItemData.isEngineDataEmpty()) {
                pageItemData.isContentEmpty = false;
            } else {
                ItemSpannedData itemSpannedData = pageItemData.spannedData;
                if (itemSpannedData != null && itemSpannedData.getSpannedInfo() != null && !itemSpannedData.getSpannedInfo().isContentEmpty()) {
                    pageItemData.isContentEmpty = false;
                }
            }
        }
    }

    public final void setSearchResult(int pageIndex, int color, int alpha, SearchWrapper wrapper) {
        int iMax;
        int iCenterX;
        List listEmptyList;
        List listEmptyList2;
        int iCenterX2;
        Intrinsics.checkNotNullParameter(wrapper, "wrapper");
        if (wrapper.searchRectMap != null) {
            this.searchWrapper.searchRectMap = new HashMap(wrapper.searchRectMap);
        }
        this.searchWrapper.searchPageIndex = pageIndex;
        this.searchWrapper.searchColor = color;
        this.searchWrapper.searchAlpha = alpha;
        this.searchWrapper.searchText = wrapper.searchText;
        this.searchWrapper.searchSpannedRect = wrapper.searchSpannedRect;
        this.searchWrapper.searchSpannedRectContent = wrapper.searchSpannedRectContent;
        this.searchWrapper.searchSpannedTextSize = wrapper.searchSpannedTextSize;
        if (!this.mList.isEmpty()) {
            if (pageIndex != getCurrentPosition() || getCurrentItemView() == null) {
                return;
            }
            PagePreItemView currentItemView = getCurrentItemView();
            Intrinsics.checkNotNull(currentItemView);
            RectF layout = currentItemView.getLayout();
            if (this.searchTop == 0) {
                if (wrapper.searchSpannedRect != null && !Intrinsics.areEqual("", this.searchWrapper.searchText)) {
                    RectF rectF = new RectF(wrapper.searchSpannedRect);
                    if (!rectF.isEmpty()) {
                        PagePreItemView currentItemView2 = getCurrentItemView();
                        Intrinsics.checkNotNull(currentItemView2);
                        if (currentItemView2.isUnLimitToNormal) {
                            float fWidth = layout.width();
                            PagePreItemView currentItemView3 = getCurrentItemView();
                            Intrinsics.checkNotNull(currentItemView3);
                            float shieldWidth = fWidth - (currentItemView3.getShieldWidth() * 2);
                            PagePreItemView currentItemView4 = getCurrentItemView();
                            Intrinsics.checkNotNull(currentItemView4);
                            double visWidth = currentItemView4.getVisWidth();
                            PagePreItemView currentItemView5 = getCurrentItemView();
                            Intrinsics.checkNotNull(currentItemView5);
                            float fMin = (float) Math.min(shieldWidth, (float) Math.min(visWidth, currentItemView5.getVisHeight()));
                            Matrix matrix = new Matrix();
                            float f = fMin / 1000.0f;
                            matrix.setScale(f, f);
                            matrix.mapRect(rectF);
                            float fWidth2 = layout.width();
                            PagePreItemView currentItemView6 = getCurrentItemView();
                            Intrinsics.checkNotNull(currentItemView6);
                            float shieldWidth2 = fWidth2 - (currentItemView6.getShieldWidth() * 2);
                            PagePreItemView currentItemView7 = getCurrentItemView();
                            Intrinsics.checkNotNull(currentItemView7);
                            if (shieldWidth2 > currentItemView7.getVisWidth()) {
                                float fWidth3 = layout.width() / 2.0f;
                                iCenterX2 = (int) (fWidth3 - rectF.centerX());
                                PagePreItemView currentItemView8 = getCurrentItemView();
                                Intrinsics.checkNotNull(currentItemView8);
                                float shieldWidth3 = fWidth3 - currentItemView8.getShieldWidth();
                                PagePreItemView currentItemView9 = getCurrentItemView();
                                Intrinsics.checkNotNull(currentItemView9);
                                float fAbs = (float) Math.abs(shieldWidth3 - (currentItemView9.getVisWidth() / 2.0f));
                                double d = iCenterX2;
                                double d2 = fAbs;
                                if (Math.abs(d) > d2) {
                                    iCenterX2 = (int) ((d / Math.abs(d)) * d2);
                                }
                            } else {
                                iCenterX2 = 0;
                            }
                            int iHeight = (int) (layout.top + rectF.top);
                            StaticLayout.Builder builderObtainBuilder = new TextLayoutUtil().obtainBuilder(wrapper.searchSpannedRectContent, wrapper.searchSpannedRect, wrapper.searchSpannedTextSize);
                            if (builderObtainBuilder != null) {
                                StaticLayout staticLayoutBuild = builderObtainBuilder.build();
                                Intrinsics.checkNotNullExpressionValue(staticLayoutBuild, "build(...)");
                                List<RectF> listGenSearchTextPath = TextLayoutUtil.genSearchTextPath(staticLayoutBuild, wrapper.searchText);
                                if (listGenSearchTextPath.size() > 0) {
                                    iHeight = (int) (iHeight + ((listGenSearchTextPath.get(0).top * rectF.height()) / staticLayoutBuild.getHeight()));
                                }
                            }
                            if (iHeight > 0) {
                                this.isSearchScrollBy = false;
                                scrollBy(iCenterX2, -iHeight);
                                return;
                            }
                            return;
                        }
                        Matrix matrix2 = new Matrix();
                        matrix2.setScale(layout.width() / 1000.0f, layout.width() / 1000.0f);
                        matrix2.mapRect(rectF);
                        int iHeight2 = (int) (layout.top + rectF.top);
                        StaticLayout.Builder builderObtainBuilder2 = new TextLayoutUtil().obtainBuilder(wrapper.searchSpannedRectContent, wrapper.searchSpannedRect, wrapper.searchSpannedTextSize);
                        if (builderObtainBuilder2 != null) {
                            StaticLayout staticLayoutBuild2 = builderObtainBuilder2.build();
                            Intrinsics.checkNotNullExpressionValue(staticLayoutBuild2, "build(...)");
                            List<RectF> listGenSearchTextPath2 = TextLayoutUtil.genSearchTextPath(staticLayoutBuild2, wrapper.searchText);
                            if (listGenSearchTextPath2.size() > 0) {
                                iHeight2 = (int) (iHeight2 + ((listGenSearchTextPath2.get(0).top * rectF.height()) / staticLayoutBuild2.getHeight()));
                            }
                        }
                        PagePreItemView currentItemView10 = getCurrentItemView();
                        Intrinsics.checkNotNull(currentItemView10);
                        if (!currentItemView10.getIsUnLimit()) {
                            if (iHeight2 > 0) {
                                this.isSearchScrollBy = false;
                                scrollBy(0, -iHeight2);
                                return;
                            }
                            return;
                        }
                        PagePreItemView currentItemView11 = getCurrentItemView();
                        Intrinsics.checkNotNull(currentItemView11);
                        int visWidth2 = currentItemView11.getVisWidth();
                        PagePreItemView currentItemView12 = getCurrentItemView();
                        Intrinsics.checkNotNull(currentItemView12);
                        int visHeight = currentItemView12.getVisHeight();
                        int iCenterX3 = (int) ((layout.left + rectF.centerX()) - (visWidth2 / 2.0f));
                        int iCenterY = (int) ((layout.top + rectF.centerY()) - (visHeight / 2.0f));
                        this.isSearchScrollBy = false;
                        scrollBy(-iCenterX3, -iCenterY);
                        return;
                    }
                    this.isSearchScrollBy = false;
                    return;
                }
                if (this.searchWrapper.searchRectMap == null || this.searchWrapper.searchRectMap.isEmpty()) {
                    return;
                }
                int i = -1;
                int i2 = -1;
                for (String str : this.searchWrapper.searchRectMap.keySet()) {
                    Intrinsics.checkNotNull(str);
                    String str2 = str;
                    List<String> listSplit = new Regex("-").split(str2, 0);
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
                    int i3 = Integer.parseInt(((String[]) listEmptyList.toArray(new String[0]))[0]);
                    List<String> listSplit2 = new Regex("-").split(str2, 0);
                    if (!listSplit2.isEmpty()) {
                        ListIterator<String> listIterator2 = listSplit2.listIterator(listSplit2.size());
                        while (true) {
                            if (listIterator2.hasPrevious()) {
                                if (listIterator2.previous().length() != 0) {
                                    listEmptyList2 = CollectionsKt.take(listSplit2, listIterator2.nextIndex() + 1);
                                    break;
                                }
                            } else {
                                listEmptyList2 = CollectionsKt.emptyList();
                                break;
                            }
                        }
                    } else {
                        listEmptyList2 = CollectionsKt.emptyList();
                        break;
                    }
                    int i4 = Integer.parseInt(((String[]) listEmptyList2.toArray(new String[0]))[1]);
                    if (i == -1 || i > i3) {
                        i2 = i4;
                        i = i3;
                    } else if (i == i3 && i2 > i4) {
                        i2 = i4;
                    }
                }
                RectF rectF2 = new RectF(this.searchWrapper.searchRectMap.get(new StringBuilder().append(i).append(CoreConstants.DASH_CHAR).append(i2).toString()));
                if (!rectF2.isEmpty()) {
                    PagePreItemView currentItemView13 = getCurrentItemView();
                    Intrinsics.checkNotNull(currentItemView13);
                    int visHeight2 = currentItemView13.getVisHeight();
                    PagePreItemView currentItemView14 = getCurrentItemView();
                    Intrinsics.checkNotNull(currentItemView14);
                    if (currentItemView14.isUnLimitToNormal) {
                        float fWidth4 = layout.width();
                        PagePreItemView currentItemView15 = getCurrentItemView();
                        Intrinsics.checkNotNull(currentItemView15);
                        float shieldWidth4 = fWidth4 - (currentItemView15.getShieldWidth() * 2);
                        PagePreItemView currentItemView16 = getCurrentItemView();
                        Intrinsics.checkNotNull(currentItemView16);
                        double visWidth3 = currentItemView16.getVisWidth();
                        PagePreItemView currentItemView17 = getCurrentItemView();
                        Intrinsics.checkNotNull(currentItemView17);
                        float fMin2 = (float) Math.min(shieldWidth4, (float) Math.min(visWidth3, currentItemView17.getVisHeight()));
                        Matrix matrix3 = new Matrix();
                        float f2 = fMin2 / 1000.0f;
                        matrix3.setScale(f2, f2);
                        matrix3.mapRect(rectF2);
                        float fWidth5 = layout.width();
                        PagePreItemView currentItemView18 = getCurrentItemView();
                        Intrinsics.checkNotNull(currentItemView18);
                        float shieldWidth5 = fWidth5 - (currentItemView18.getShieldWidth() * 2);
                        PagePreItemView currentItemView19 = getCurrentItemView();
                        Intrinsics.checkNotNull(currentItemView19);
                        if (shieldWidth5 > currentItemView19.getVisWidth()) {
                            float fWidth6 = layout.width() / 2.0f;
                            iCenterX = (int) (fWidth6 - rectF2.centerX());
                            PagePreItemView currentItemView20 = getCurrentItemView();
                            Intrinsics.checkNotNull(currentItemView20);
                            float shieldWidth6 = fWidth6 - currentItemView20.getShieldWidth();
                            PagePreItemView currentItemView21 = getCurrentItemView();
                            Intrinsics.checkNotNull(currentItemView21);
                            float fAbs2 = (float) Math.abs(shieldWidth6 - (currentItemView21.getVisWidth() / 2.0f));
                            double d3 = iCenterX;
                            double d4 = fAbs2;
                            if (Math.abs(d3) > d4) {
                                iCenterX = (int) ((d3 / Math.abs(d3)) * d4);
                            }
                        } else {
                            iCenterX = 0;
                        }
                        int iMax2 = (int) Math.max(0.0d, rectF2.centerY() - (visHeight2 / 2.0f));
                        this.searchTop = iMax2;
                        int i5 = (int) (iMax2 + layout.top);
                        if (i5 > 0) {
                            this.isSearchScrollBy = false;
                            scrollBy(iCenterX, -i5);
                            return;
                        }
                        return;
                    }
                    PagePreItemView currentItemView22 = getCurrentItemView();
                    Intrinsics.checkNotNull(currentItemView22);
                    if (currentItemView22.getIsUnLimit()) {
                        Matrix matrix4 = new Matrix();
                        matrix4.setScale(layout.width() / 1000.0f, layout.width() / 1000.0f);
                        matrix4.mapRect(rectF2);
                        int iCenterY2 = (int) (rectF2.centerY() - (visHeight2 / 2.0f));
                        this.searchTop = iCenterY2;
                        int i6 = (int) (iCenterY2 + layout.top);
                        PagePreItemView currentItemView23 = getCurrentItemView();
                        Intrinsics.checkNotNull(currentItemView23);
                        int iCenterX4 = (int) (rectF2.centerX() - (currentItemView23.getVisWidth() / 2.0f));
                        this.isSearchScrollBy = false;
                        scrollBy(-iCenterX4, -i6);
                        return;
                    }
                    Matrix matrix5 = new Matrix();
                    matrix5.setScale(layout.width() / 1000.0f, layout.width() / 1000.0f);
                    matrix5.mapRect(rectF2);
                    float f3 = visHeight2 / 2.0f;
                    if (layout.bottom - rectF2.centerY() < f3) {
                        iMax = (int) (rectF2.centerY() - f3);
                    } else {
                        iMax = (int) Math.max(0.0d, rectF2.centerY() - f3);
                    }
                    this.searchTop = iMax;
                    int i7 = (int) (iMax + layout.top);
                    if (i7 > 0) {
                        this.isSearchScrollBy = false;
                        scrollBy(0, -i7);
                        return;
                    }
                    return;
                }
                this.isSearchScrollBy = false;
                return;
            }
            return;
        }
        jumpPage(pageIndex, false);
    }

    public final void onSizeChanged(int w, int h, int oldw, int oldh) {
        View view;
        if (w == 0 || h == 0) {
            return;
        }
        if (getMVisibleWidth() == w && getMVisibleHeight() == h) {
            return;
        }
        setMVisibleWidth(w);
        setMVisibleHeight(h);
        this.windowInfo.onSizeChanged(w, h);
        PagePreLayoutManager layoutManager = getLayoutManager();
        Intrinsics.checkNotNull(layoutManager);
        layoutManager.onSizeChanged(w, h);
        if (!this.mList.isEmpty()) {
            PageItemData pageItemData = this.mList.get(0);
            if (pageItemData.isUnLimit) {
                PagePreItemView currentItemView = getCurrentItemView();
                Intrinsics.checkNotNull(currentItemView);
                currentItemView.setUnlimitedSize(w, h);
            }
            PagePreItemView currentItemView2 = getCurrentItemView();
            Intrinsics.checkNotNull(currentItemView2);
            currentItemView2.setVisibleSize(w, h);
            PagePreItemView currentItemView3 = getCurrentItemView();
            Intrinsics.checkNotNull(currentItemView3);
            PreICreationEngine creationEngine = currentItemView3.getCreationEngine();
            Intrinsics.checkNotNull(creationEngine);
            ICanvasOperator canvasOperator = creationEngine.getCanvasOperator();
            if (canvasOperator != null) {
                canvasOperator.setCanvasDrawWidth(this.windowInfo.getMWindowWidth());
                canvasOperator.setCanvasSize(w, Math.max(h, pageItemData.getItemHeight(w, 1.0f)));
            }
            if (pageItemData.sizeUnit == 0 && !pageItemData.isFixSize) {
                PagePreItemView currentItemView4 = getCurrentItemView();
                Intrinsics.checkNotNull(currentItemView4);
                PreICreationEngine creationEngine2 = currentItemView4.getCreationEngine();
                Intrinsics.checkNotNull(creationEngine2);
                creationEngine2.getCanvasOperator().setVisibleFixedWidth(w);
            } else {
                PagePreItemView currentItemView5 = getCurrentItemView();
                Intrinsics.checkNotNull(currentItemView5);
                PreICreationEngine creationEngine3 = currentItemView5.getCreationEngine();
                Intrinsics.checkNotNull(creationEngine3);
                ICanvasOperator canvasOperator2 = creationEngine3.getCanvasOperator();
                float f = pageItemData.maxWidth;
                PagePreLayoutManager layoutManager2 = getLayoutManager();
                Intrinsics.checkNotNull(layoutManager2);
                canvasOperator2.setVisibleFixedWidth(Math.max(f / layoutManager2.getPageScaleRatio(), w));
            }
            initEntPages();
            PagePreDrawView mDrawView = getMDrawView();
            Intrinsics.checkNotNull(mDrawView);
            PreICreationEngine creationEngine4 = mDrawView.getCreationEngine();
            Intrinsics.checkNotNull(creationEngine4);
            PrePageSystem pageSystem = creationEngine4.getCurveEngine().getPageSystem();
            Stream<PageItemData> stream = this.mList.stream();
            final Function1 function1 = new Function1() { // from class: com.miui.libhandwritingpreview.model.PageInkPreModel$$ExternalSyntheticLambda16
                @Override // kotlin.jvm.functions.Function1
                public final Object invoke(Object obj) {
                    return PageInkPreModel.onSizeChanged$lambda$10((PageItemData) obj);
                }
            };
            Object objCollect = stream.map(new Function() { // from class: com.miui.libhandwritingpreview.model.PageInkPreModel$$ExternalSyntheticLambda1
                @Override // java.util.function.Function
                public final Object apply(Object obj) {
                    return PageInkPreModel.onSizeChanged$lambda$11(function1, obj);
                }
            }).collect(Collectors.toList());
            Intrinsics.checkNotNullExpressionValue(objCollect, "collect(...)");
            pageSystem.adjustAllPageSize((List) objCollect);
            PagePreItemView currentItemView6 = getCurrentItemView();
            Intrinsics.checkNotNull(currentItemView6);
            PreICreationEngine creationEngine5 = currentItemView6.getCreationEngine();
            Intrinsics.checkNotNull(creationEngine5);
            creationEngine5.getCurveEngine().getCamera().setRealtimeDrawRatio(this.windowInfo.getMWindowWidth() / 1000.0f);
        }
        PagePreLayoutManager layoutManager3 = getLayoutManager();
        Intrinsics.checkNotNull(layoutManager3);
        layoutManager3.onDataChanged();
        PagePreDrawView mDrawView2 = getMDrawView();
        if (mDrawView2 == null || (view = mDrawView2.getView()) == null) {
            return;
        }
        view.post(new Runnable() { // from class: com.miui.libhandwritingpreview.model.PageInkPreModel$$ExternalSyntheticLambda2
            @Override // java.lang.Runnable
            public final void run() {
                PageInkPreModel.onSizeChanged$lambda$12(this.f$0);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final List onSizeChanged$lambda$11(Function1 function1, Object obj) {
        return (List) function1.invoke(obj);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final List onSizeChanged$lambda$10(PageItemData item) {
        Intrinsics.checkNotNullParameter(item, "item");
        return CollectionsKt.listOf((Object[]) new Integer[]{Integer.valueOf(item.width), Integer.valueOf(item.height), Integer.valueOf(item.maxWidth), Integer.valueOf(item.maxHeight)});
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void onSizeChanged$lambda$12(PageInkPreModel pageInkPreModel) {
        pageInkPreModel.updateStateView(pageInkPreModel.getCurrentPosition());
    }

    @Override // com.miui.libhandwritingpreview.model.IPagePreInkModel
    public void onConfigurationChanged(Configuration newConfig) {
        Intrinsics.checkNotNullParameter(newConfig, "newConfig");
        if (getOrientation() == getMContext().getResources().getConfiguration().orientation) {
            return;
        }
        super.onConfigurationChanged(newConfig);
        PagePreDrawView mDrawView = getMDrawView();
        Intrinsics.checkNotNull(mDrawView);
        if (mDrawView.getCreationEngine() != null) {
            PagePreDrawView mDrawView2 = getMDrawView();
            Intrinsics.checkNotNull(mDrawView2);
            PreICreationEngine creationEngine = mDrawView2.getCreationEngine();
            Intrinsics.checkNotNull(creationEngine);
            creationEngine.getCurveEngine().getPenEngine().getEngineHandler().sendMessageDelayed(new Runnable() { // from class: com.miui.libhandwritingpreview.model.PageInkPreModel$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    PageInkPreModel.onConfigurationChanged$lambda$13(this.f$0);
                }
            }, 0, 150L);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void onConfigurationChanged$lambda$13(PageInkPreModel pageInkPreModel) {
        PagePreDrawView mDrawView = pageInkPreModel.getMDrawView();
        Intrinsics.checkNotNull(mDrawView);
        PreICreationEngine creationEngine = mDrawView.getCreationEngine();
        Intrinsics.checkNotNull(creationEngine);
        if (creationEngine.getCurveEngine().getCamera().isNeedToScrollBackToBottom()) {
            pageInkPreModel.jumpPage(pageInkPreModel.getPageCount() - 1, false);
        }
    }

    private final void onPageChanged() {
        PagePreLayoutManager layoutManager = getLayoutManager();
        Intrinsics.checkNotNull(layoutManager);
        layoutManager.onDataChanged();
        onPreviewChanged();
    }

    private final void onPreviewChanged() {
        int size = this.mList.size();
        for (int i = 0; i < size; i++) {
            PageItemData pageItemData = this.mList.get(i);
            String str = pageItemData.pdfId;
            BgGridConfiguration bgGridConfiguration = pageItemData.bgConfig;
            String str2 = pageItemData.bookmark;
            int i2 = pageItemData.bgImageMode;
            StringBuilder sb = new StringBuilder();
            PageBean mPageBean = getMPageBean();
            Intrinsics.checkNotNull(mPageBean);
            new PagePreviewData(str, bgGridConfiguration, str2, i2, sb.append(mPageBean.path).append(File.separator).append(pageItemData.bgImage).toString(), pageItemData.bgColor, pageItemData.viewId, pageItemData.covePath, pageItemData.bgGrid);
        }
    }

    public final void scrollBy(int offX, int offY) {
        PagePreLayoutManager layoutManager = getLayoutManager();
        Intrinsics.checkNotNull(layoutManager);
        layoutManager.scrollBy(offX, offY);
        PagePreLayoutManager layoutManager2 = getLayoutManager();
        Intrinsics.checkNotNull(layoutManager2);
        layoutManager2.onScrollEnd();
    }

    public final void scrollBy(float offX, float offY) {
        PagePreLayoutManager layoutManager = getLayoutManager();
        Intrinsics.checkNotNull(layoutManager);
        layoutManager.scrollBy(offX, offY);
        PagePreLayoutManager layoutManager2 = getLayoutManager();
        Intrinsics.checkNotNull(layoutManager2);
        layoutManager2.onScrollEnd();
    }

    public final void destroy() {
        CreationPageSDK.removeToken(getMContext());
        CreationPageSDK.clearHandler(CreationPageSDK.getToken(getMContext()));
        ImportPdfBitmap importPdfBitmap = this.mImportPdfBitmap;
        if (importPdfBitmap != null) {
            importPdfBitmap.release();
        }
        this.mImportPdfBitmap = null;
        ImportPdfBitmap importPdfBitmap2 = this.mExportPdfBitmap;
        if (importPdfBitmap2 != null) {
            importPdfBitmap2.release();
        }
        this.mExportPdfBitmap = null;
        PagePreItemView currentItemView = getCurrentItemView();
        if (currentItemView != null) {
            currentItemView.release();
        }
        setCurrentItemView(null);
        Iterator<T> it = this.mList.iterator();
        while (it.hasNext()) {
            ((PageItemData) it.next()).release();
        }
        PagePreAdapter adapter = getAdapter();
        Intrinsics.checkNotNull(adapter);
        adapter.release();
        FileUtil.deleteDir(new File(this.pageDataTempDir));
        this.searchWrapper.searchRectMap = null;
        this.searchWrapper.searchText = null;
        setMIsFirstjump(true);
        setMFirstJumpPosition(-1);
        setMLoadListener(null);
        setPreViewClickListener(null);
    }

    public final void updateStateView(int currentPosition) {
        if (this.mStateView == null) {
            return;
        }
        PagePreLayoutManager layoutManager = getLayoutManager();
        Intrinsics.checkNotNull(layoutManager);
        float[] location = layoutManager.getLocation();
        PageStateView pageStateView = this.mStateView;
        Intrinsics.checkNotNull(pageStateView);
        int pageCount = getPageCount();
        PagePreLayoutManager layoutManager2 = getLayoutManager();
        Intrinsics.checkNotNull(layoutManager2);
        pageStateView.update(currentPosition, pageCount, layoutManager2.getFactorScale(), location[0], location[1]);
    }

    public final void setStateView(PageStateView stateView) {
        this.mStateView = stateView;
    }

    public final void setPageSizeConfig(PageSizeConfig pageSizeConfig, boolean isFromCancel) {
        Intrinsics.checkNotNullParameter(pageSizeConfig, "pageSizeConfig");
        Log.d(TAG, "setPageSizeConfig   " + pageSizeConfig + "; isFromCancel " + isFromCancel);
        if (canChangeSizeConfig(pageSizeConfig.position)) {
            if (pageSizeConfig.isUnLimit) {
                normalToUnLimit(pageSizeConfig);
                PagePreDrawView mDrawView = getMDrawView();
                Intrinsics.checkNotNull(mDrawView);
                PreICreationEngine creationEngine = mDrawView.getCreationEngine();
                Intrinsics.checkNotNull(creationEngine);
                creationEngine.getCurveEngine().getCamera().resetViewPortCenter();
                return;
            }
            if (getIsUnlimited()) {
                unLimitToNormal(pageSizeConfig, isFromCancel);
                PagePreDrawView mDrawView2 = getMDrawView();
                Intrinsics.checkNotNull(mDrawView2);
                PreICreationEngine creationEngine2 = mDrawView2.getCreationEngine();
                Intrinsics.checkNotNull(creationEngine2);
                creationEngine2.getCurveEngine().getCamera().resetViewPortCenter();
                return;
            }
            final int i = pageSizeConfig.position;
            PageItemData pageItemData = this.mList.get(i);
            int i2 = pageItemData.maxWidth;
            int i3 = pageItemData.maxHeight;
            changedPageSizeConfig(pageSizeConfig, isFromCancel);
            int i4 = i2 - pageItemData.maxWidth;
            int i5 = pageItemData.maxHeight - i3;
            onPageChanged();
            CreationPageSDK.with(getMContext()).post(new Runnable() { // from class: com.miui.libhandwritingpreview.model.PageInkPreModel$$ExternalSyntheticLambda3
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.onPageSizeChanged(i);
                }
            });
            PageBean mPageBean = getMPageBean();
            Intrinsics.checkNotNull(mPageBean);
            mPageBean.width = pageSizeConfig.width;
            PageBean mPageBean2 = getMPageBean();
            Intrinsics.checkNotNull(mPageBean2);
            mPageBean2.height = pageSizeConfig.height;
            PageBean mPageBean3 = getMPageBean();
            Intrinsics.checkNotNull(mPageBean3);
            mPageBean3.orientation = (short) pageSizeConfig.orientation;
            PageBean mPageBean4 = getMPageBean();
            Intrinsics.checkNotNull(mPageBean4);
            mPageBean4.sizeType = pageSizeConfig.type;
            PageItemData pageItemData2 = this.mList.get(i);
            PagePreItemView currentItemView = getCurrentItemView();
            Intrinsics.checkNotNull(currentItemView);
            currentItemView.moveAllSpanAfterPage(i, i4, i5);
            PagePreDrawView mDrawView3 = getMDrawView();
            Intrinsics.checkNotNull(mDrawView3);
            PreICreationEngine creationEngine3 = mDrawView3.getCreationEngine();
            Intrinsics.checkNotNull(creationEngine3);
            creationEngine3.moveAllCurveAfterPage(i, i4, i5);
            PagePreDrawView mDrawView4 = getMDrawView();
            Intrinsics.checkNotNull(mDrawView4);
            PreICreationEngine creationEngine4 = mDrawView4.getCreationEngine();
            Intrinsics.checkNotNull(creationEngine4);
            creationEngine4.getCurveEngine().getPageSystem().setPageSize(i, pageItemData2.width, pageItemData2.height, pageItemData2.maxWidth, pageItemData2.maxHeight);
            PagePreDrawView mDrawView5 = getMDrawView();
            Intrinsics.checkNotNull(mDrawView5);
            PreICreationEngine creationEngine5 = mDrawView5.getCreationEngine();
            Intrinsics.checkNotNull(creationEngine5);
            if (creationEngine5.getCurveEngine().getCamera().isNeedToScrollBackToBottom()) {
                jumpPage(getPageCount() - 1, true);
            }
            PageSizeListener pageSizeListener = this.mPageSizeListener;
            if (pageSizeListener != null) {
                Intrinsics.checkNotNull(pageSizeListener);
                pageSizeListener.onPageSizeChanged(getIsUnlimited());
            }
        }
    }

    public final void normalToUnLimit(PageSizeConfig config) {
        Intrinsics.checkNotNullParameter(config, "config");
        setUnlimited(true);
        this.windowInfo.onSizeChanged(getMVisibleWidth(), getMVisibleHeight());
        PagePreItemView currentItemView = getCurrentItemView();
        Intrinsics.checkNotNull(currentItemView);
        PreICreationEngine creationEngine = currentItemView.getCreationEngine();
        Intrinsics.checkNotNull(creationEngine);
        creationEngine.getCanvasOperator().resetInfiniteCanvas();
        PageItemData pageItemData = this.mList.get(config.position);
        if (pageItemData.unLimitToNormal) {
            PagePreLayoutManager layoutManager = getLayoutManager();
            Intrinsics.checkNotNull(layoutManager);
            int[] unLimitBound = layoutManager.getUnLimitBound();
            PagePreItemView currentItemView2 = getCurrentItemView();
            Intrinsics.checkNotNull(currentItemView2);
            currentItemView2.moveAllSpan(-unLimitBound[0], -unLimitBound[1]);
            PagePreItemView currentItemView3 = getCurrentItemView();
            Intrinsics.checkNotNull(currentItemView3);
            currentItemView3.moveAllCurve(-unLimitBound[0], -unLimitBound[1]);
        }
        pageItemData.unLimitToNormal = false;
        pageItemData.isUnLimit = true;
        if (pageItemData.isFixSize) {
            PagePreItemView currentItemView4 = getCurrentItemView();
            Intrinsics.checkNotNull(currentItemView4);
            PreICreationEngine creationEngine2 = currentItemView4.getCreationEngine();
            Intrinsics.checkNotNull(creationEngine2);
            pageItemData.fixWidth = creationEngine2.getCanvasOperator().getCanvasDrawWidth();
        } else {
            PagePreItemView currentItemView5 = getCurrentItemView();
            Intrinsics.checkNotNull(currentItemView5);
            PreICreationEngine creationEngine3 = currentItemView5.getCreationEngine();
            Intrinsics.checkNotNull(creationEngine3);
            pageItemData.fixWidth = creationEngine3.getCanvasOperator().getVisibleWidth();
        }
        pageItemData.isFixSize = true;
        pageItemData.pageSizeType = config.pageType;
        PagePreItemView currentItemView6 = getCurrentItemView();
        Intrinsics.checkNotNull(currentItemView6);
        currentItemView6.setSpannedLayoutFixMode(true);
        PagePreItemView currentItemView7 = getCurrentItemView();
        Intrinsics.checkNotNull(currentItemView7);
        currentItemView7.setSpannedLayoutFixWidth((int) pageItemData.fixWidth);
        PagePreItemView currentItemView8 = getCurrentItemView();
        Intrinsics.checkNotNull(currentItemView8);
        currentItemView8.setSpanVisibleSize((int) pageItemData.fixWidth, getMVisibleHeight());
        PagePreItemView currentItemView9 = getCurrentItemView();
        Intrinsics.checkNotNull(currentItemView9);
        currentItemView9.setUnLimit(true);
        PagePreItemView currentItemView10 = getCurrentItemView();
        Intrinsics.checkNotNull(currentItemView10);
        currentItemView10.isUnLimitToNormal = false;
        PagePreItemView currentItemView11 = getCurrentItemView();
        Intrinsics.checkNotNull(currentItemView11);
        currentItemView11.setSearchRectList(true, 0);
        PagePreItemView currentItemView12 = getCurrentItemView();
        Intrinsics.checkNotNull(currentItemView12);
        PreICreationEngine creationEngine4 = currentItemView12.getCreationEngine();
        Intrinsics.checkNotNull(creationEngine4);
        creationEngine4.getCurveEngine().getCurveSystem().clearAllTiles();
        onPageChanged();
        PagePreItemView currentItemView13 = getCurrentItemView();
        Intrinsics.checkNotNull(currentItemView13);
        PreICreationEngine creationEngine5 = currentItemView13.getCreationEngine();
        Intrinsics.checkNotNull(creationEngine5);
        creationEngine5.getCurveEngine().setPageMode(PreMiCurveEngine.EPageMode.Infinite);
        PagePreItemView currentItemView14 = getCurrentItemView();
        Intrinsics.checkNotNull(currentItemView14);
        PreICreationEngine creationEngine6 = currentItemView14.getCreationEngine();
        Intrinsics.checkNotNull(creationEngine6);
        creationEngine6.getCurveEngine().getBgSystem().markDirty();
        PagePreItemView currentItemView15 = getCurrentItemView();
        Intrinsics.checkNotNull(currentItemView15);
        PreICreationEngine creationEngine7 = currentItemView15.getCreationEngine();
        Intrinsics.checkNotNull(creationEngine7);
        creationEngine7.getCurveEngine().requestMultiBufferRender(PreMiCurveEngine.ERefreshReason.Force);
        PageSizeListener pageSizeListener = this.mPageSizeListener;
        if (pageSizeListener != null) {
            Intrinsics.checkNotNull(pageSizeListener);
            pageSizeListener.onPageSizeChanged(getIsUnlimited());
        }
    }

    public final void unLimitToNormal(PageSizeConfig config, boolean isFromCancel) {
        int mWindowWidth;
        int iMm2px;
        Intrinsics.checkNotNullParameter(config, "config");
        this.isUnLimitToNormal = true;
        this.windowInfo.onSizeChanged(getMVisibleWidth(), getMVisibleHeight());
        PagePreLayoutManager layoutManager = getLayoutManager();
        Intrinsics.checkNotNull(layoutManager);
        int[] centerPoint = layoutManager.getCenterPoint();
        PagePreLayoutManager layoutManager2 = getLayoutManager();
        Intrinsics.checkNotNull(layoutManager2);
        int[] unLimitBound = layoutManager2.getUnLimitBound();
        if (!config.isUnLimitToNormal && isFromCancel && !config.isFixSize) {
            PagePreLayoutManager layoutManager3 = getLayoutManager();
            Intrinsics.checkNotNull(layoutManager3);
            layoutManager3.scrollTo(0.0f, 0.0f);
        } else {
            PagePreLayoutManager layoutManager4 = getLayoutManager();
            Intrinsics.checkNotNull(layoutManager4);
            layoutManager4.scrollTo(centerPoint[0], centerPoint[1]);
            PagePreItemView currentItemView = getCurrentItemView();
            Intrinsics.checkNotNull(currentItemView);
            currentItemView.moveAllSpan(unLimitBound[0], unLimitBound[1]);
            PagePreItemView currentItemView2 = getCurrentItemView();
            Intrinsics.checkNotNull(currentItemView2);
            currentItemView2.moveAllCurve(unLimitBound[0], unLimitBound[1]);
        }
        PagePreItemView currentItemView3 = getCurrentItemView();
        Intrinsics.checkNotNull(currentItemView3);
        PreICreationEngine creationEngine = currentItemView3.getCreationEngine();
        Intrinsics.checkNotNull(creationEngine);
        creationEngine.getCanvasOperator().resetInfiniteCanvas();
        PageItemData pageItemData = this.mList.get(config.position);
        pageItemData.orientation = config.orientation;
        if (config.type == 0) {
            pageItemData.width = config.orientation == 0 ? config.width : config.height;
            pageItemData.height = config.orientation == 0 ? config.height : config.width;
        } else {
            pageItemData.width = config.width;
            pageItemData.height = config.height;
        }
        pageItemData.isUnLimit = false;
        pageItemData.unLimitToNormal = true;
        pageItemData.pageSizeType = config.pageType;
        pageItemData.maxHeight = unLimitBound[1] - unLimitBound[3];
        pageItemData.maxWidth = unLimitBound[0] - unLimitBound[2];
        pageItemData.sizeUnit = config.type;
        pageItemData.useNewSize = true;
        pageItemData.isFixSize = true;
        PagePreItemView currentItemView4 = getCurrentItemView();
        Intrinsics.checkNotNull(currentItemView4);
        PreICreationEngine creationEngine2 = currentItemView4.getCreationEngine();
        Intrinsics.checkNotNull(creationEngine2);
        pageItemData.fixWidth = creationEngine2.getCanvasOperator().getCanvasDrawWidth();
        if (isFromCancel && !config.isFixSize) {
            pageItemData.isUnLimit = false;
            pageItemData.unLimitToNormal = config.isUnLimitToNormal;
            pageItemData.pageSizeType = config.pageType;
            pageItemData.maxHeight = config.maxHeight;
            pageItemData.maxWidth = config.maxWidth;
            pageItemData.sizeUnit = config.type;
            pageItemData.isFixSize = config.isFixSize;
            if (!pageItemData.unLimitToNormal) {
                PagePreItemView currentItemView5 = getCurrentItemView();
                Intrinsics.checkNotNull(currentItemView5);
                currentItemView5.isUnLimitToNormal = false;
                PagePreItemView currentItemView6 = getCurrentItemView();
                Intrinsics.checkNotNull(currentItemView6);
                currentItemView6.setUnLimit(false);
            }
        }
        setUnlimited(false);
        int i = config.type;
        if (i == 0) {
            mWindowWidth = (pageItemData.height * this.windowInfo.getMWindowWidth()) / pageItemData.width;
            int mWindowWidth2 = this.windowInfo.getMWindowWidth();
            if (!isFromCancel || config.isFixSize) {
                pageItemData.height = mWindowWidth;
                pageItemData.width = mWindowWidth2;
            }
            iMm2px = mWindowWidth2;
        } else if (i == 1) {
            iMm2px = pageItemData.width;
            mWindowWidth = pageItemData.height;
        } else if (i != 2) {
            iMm2px = 0;
            mWindowWidth = 0;
        } else {
            iMm2px = (int) UIUtils.mm2px(getMContext(), pageItemData.width);
            mWindowWidth = (int) UIUtils.mm2px(getMContext(), pageItemData.height);
        }
        if (!config.isUnLimit) {
            pageItemData.maxWidth = iMm2px;
            pageItemData.maxHeight = mWindowWidth;
            PagePreItemView currentItemView7 = getCurrentItemView();
            Intrinsics.checkNotNull(currentItemView7);
            PreICreationEngine creationEngine3 = currentItemView7.getCreationEngine();
            Intrinsics.checkNotNull(creationEngine3);
            creationEngine3.getCanvasOperator().setCanvasDrawWidth(this.windowInfo.getMWindowWidth());
            PagePreItemView currentItemView8 = getCurrentItemView();
            Intrinsics.checkNotNull(currentItemView8);
            currentItemView8.setSpannedLayoutFixMode(false);
            PagePreItemView currentItemView9 = getCurrentItemView();
            Intrinsics.checkNotNull(currentItemView9);
            currentItemView9.setSpanVisibleSize(getMVisibleWidth(), getMVisibleHeight());
        }
        PagePreItemView currentItemView10 = getCurrentItemView();
        Intrinsics.checkNotNull(currentItemView10);
        currentItemView10.isUnLimitToNormal = true;
        PagePreItemView currentItemView11 = getCurrentItemView();
        Intrinsics.checkNotNull(currentItemView11);
        currentItemView11.setBottomShieldHeight(0.0f);
        PagePreItemView currentItemView12 = getCurrentItemView();
        Intrinsics.checkNotNull(currentItemView12);
        currentItemView12.setShieldWidth((pageItemData.maxWidth - iMm2px) / 2);
        PagePreItemView currentItemView13 = getCurrentItemView();
        Intrinsics.checkNotNull(currentItemView13);
        currentItemView13.setSearchRectList(true, 0);
        PagePreLayoutManager layoutManager5 = getLayoutManager();
        Intrinsics.checkNotNull(layoutManager5);
        double mVisibleWidth = getMVisibleWidth();
        PagePreLayoutManager layoutManager6 = getLayoutManager();
        Intrinsics.checkNotNull(layoutManager6);
        layoutManager5.setContentWidth((float) Math.max(mVisibleWidth, layoutManager6.getStandardWidth()));
        PagePreItemView currentItemView14 = getCurrentItemView();
        Intrinsics.checkNotNull(currentItemView14);
        PreICreationEngine creationEngine4 = currentItemView14.getCreationEngine();
        Intrinsics.checkNotNull(creationEngine4);
        creationEngine4.getCurveEngine().getCurveSystem().clearAllTiles();
        onPageChanged();
        PagePreDrawView mDrawView = getMDrawView();
        Intrinsics.checkNotNull(mDrawView);
        PreICreationEngine creationEngine5 = mDrawView.getCreationEngine();
        Intrinsics.checkNotNull(creationEngine5);
        if (creationEngine5.getCurveEngine().getPageSystem().getPageCount() > 0) {
            PagePreItemView currentItemView15 = getCurrentItemView();
            Intrinsics.checkNotNull(currentItemView15);
            PreICreationEngine creationEngine6 = currentItemView15.getCreationEngine();
            Intrinsics.checkNotNull(creationEngine6);
            creationEngine6.getCurveEngine().setPageMode(PreMiCurveEngine.EPageMode.Paginated2);
            PagePreDrawView mDrawView2 = getMDrawView();
            Intrinsics.checkNotNull(mDrawView2);
            PreICreationEngine creationEngine7 = mDrawView2.getCreationEngine();
            Intrinsics.checkNotNull(creationEngine7);
            creationEngine7.getCurveEngine().getPageSystem().setPageSize(0, pageItemData.width, pageItemData.height, pageItemData.maxWidth, pageItemData.maxHeight);
            setPageBGStyle(0, pageItemData.bgGrid);
            setPageBGColor(0, pageItemData.bgColor);
        } else {
            addSystemPage(pageItemData, false);
        }
        PageSizeListener pageSizeListener = this.mPageSizeListener;
        if (pageSizeListener != null) {
            Intrinsics.checkNotNull(pageSizeListener);
            pageSizeListener.onPageSizeChanged(getIsUnlimited());
        }
    }

    private final void changedPageSizeConfig(PageSizeConfig pageSizeConfig, boolean isFromCancel) {
        int mWindowWidth;
        int mWindowWidth2;
        double dMax;
        Log.d(TAG, "changedPageSizeConfig   " + pageSizeConfig + "; isFromCancel " + isFromCancel);
        PageItemData pageItemData = this.mList.get(pageSizeConfig.position);
        pageItemData.orientation = pageSizeConfig.orientation;
        if (pageSizeConfig.type == 0) {
            pageItemData.width = pageSizeConfig.orientation == 0 ? pageSizeConfig.width : pageSizeConfig.height;
            pageItemData.height = pageSizeConfig.orientation == 0 ? pageSizeConfig.height : pageSizeConfig.width;
        } else {
            pageItemData.width = pageSizeConfig.width;
            pageItemData.height = pageSizeConfig.height;
        }
        pageItemData.sizeUnit = pageSizeConfig.type;
        pageItemData.pageSizeType = pageSizeConfig.pageType;
        int i = pageSizeConfig.type;
        if (i == 0) {
            mWindowWidth = (pageItemData.height * this.windowInfo.getMWindowWidth()) / pageItemData.width;
            mWindowWidth2 = this.windowInfo.getMWindowWidth();
            pageItemData.height = mWindowWidth;
            pageItemData.width = mWindowWidth2;
        } else if (i == 1) {
            mWindowWidth2 = pageItemData.width;
            mWindowWidth = pageItemData.height;
            pageItemData.isFixSize = true;
        } else if (i != 2) {
            mWindowWidth2 = 0;
            mWindowWidth = 0;
        } else {
            mWindowWidth2 = (int) UIUtils.mm2px(getMContext(), pageItemData.width);
            mWindowWidth = (int) UIUtils.mm2px(getMContext(), pageItemData.height);
            pageItemData.isFixSize = true;
        }
        pageItemData.useNewSize = (getIsUnlimited() && pageSizeConfig.type == 0) ? false : true;
        setUnlimited(false);
        int i2 = pageItemData.maxWidth;
        if (isFromCancel && pageSizeConfig.isChangeFromUndo) {
            if (pageSizeConfig.isFixSize) {
                pageItemData.maxWidth = (int) Math.max(pageSizeConfig.maxWidth, pageItemData.maxWidth);
                pageItemData.maxHeight = pageSizeConfig.maxHeight;
            } else {
                float f = pageSizeConfig.maxHeight / pageSizeConfig.maxWidth;
                pageItemData.maxWidth = (int) Math.max(this.windowInfo.getMWindowWidth(), pageItemData.maxWidth);
                pageItemData.maxHeight = (int) (this.windowInfo.getMWindowWidth() * f);
                PagePreItemView currentItemView = getCurrentItemView();
                Intrinsics.checkNotNull(currentItemView);
                PreICreationEngine creationEngine = currentItemView.getCreationEngine();
                Intrinsics.checkNotNull(creationEngine);
                creationEngine.getCanvasOperator().setCanvasDrawWidth(this.windowInfo.getMWindowWidth());
                PagePreItemView currentItemView2 = getCurrentItemView();
                Intrinsics.checkNotNull(currentItemView2);
                currentItemView2.setSpannedLayoutFixMode(false);
                PagePreItemView currentItemView3 = getCurrentItemView();
                Intrinsics.checkNotNull(currentItemView3);
                currentItemView3.setSpanVisibleSize(getMVisibleWidth(), getMVisibleHeight());
            }
            pageItemData.isFixSize = pageSizeConfig.isFixSize;
            pageItemData.fixWidth = pageSizeConfig.fixWidth;
        } else {
            pageItemData.maxWidth = (int) Math.max(mWindowWidth2, pageItemData.maxWidth);
            pageItemData.maxHeight = mWindowWidth;
        }
        if (pageSizeConfig.type == 0 && pageItemData.maxWidth != mWindowWidth2 && (!isFromCancel || !pageSizeConfig.isChangeFromUndo)) {
            pageItemData.isFixSize = true;
        }
        pageItemData.isAdd = false;
        if (getCurrentItemView() != null) {
            if (pageItemData.isFixSize) {
                PagePreItemView currentItemView4 = getCurrentItemView();
                Intrinsics.checkNotNull(currentItemView4);
                PreICreationEngine creationEngine2 = currentItemView4.getCreationEngine();
                Intrinsics.checkNotNull(creationEngine2);
                pageItemData.fixWidth = creationEngine2.getCanvasOperator().getCanvasDrawWidth();
            } else {
                PagePreItemView currentItemView5 = getCurrentItemView();
                Intrinsics.checkNotNull(currentItemView5);
                currentItemView5.setVisibleSize(getMVisibleWidth(), getMVisibleHeight());
            }
            PagePreItemView currentItemView6 = getCurrentItemView();
            Intrinsics.checkNotNull(currentItemView6);
            currentItemView6.setSpannedLayoutFixMode(pageItemData.isFixSize);
            PagePreItemView currentItemView7 = getCurrentItemView();
            Intrinsics.checkNotNull(currentItemView7);
            currentItemView7.setSpannedLayoutFixWidth((int) pageItemData.fixWidth);
            PagePreItemView currentItemView8 = getCurrentItemView();
            Intrinsics.checkNotNull(currentItemView8);
            currentItemView8.setSpanVisibleSize(pageItemData.isFixSize ? (int) pageItemData.fixWidth : getMVisibleWidth(), getMVisibleHeight());
            PagePreItemView currentItemView9 = getCurrentItemView();
            Intrinsics.checkNotNull(currentItemView9);
            PreICreationEngine creationEngine3 = currentItemView9.getCreationEngine();
            Intrinsics.checkNotNull(creationEngine3);
            creationEngine3.getCanvasOperator().resetInfiniteCanvas();
            if (pageItemData.maxWidth > this.windowInfo.getMWindowWidth() || i2 > this.windowInfo.getMWindowWidth()) {
                PagePreItemView currentItemView10 = getCurrentItemView();
                Intrinsics.checkNotNull(currentItemView10);
                currentItemView10.moveAllCurve((pageItemData.maxWidth - i2) / 2.0f, 0.0f);
                PagePreItemView currentItemView11 = getCurrentItemView();
                Intrinsics.checkNotNull(currentItemView11);
                currentItemView11.moveAllSpan((pageItemData.maxWidth - i2) / 2.0f, 0.0f);
                PagePreItemView currentItemView12 = getCurrentItemView();
                Intrinsics.checkNotNull(currentItemView12);
                PreICreationEngine creationEngine4 = currentItemView12.getCreationEngine();
                Intrinsics.checkNotNull(creationEngine4);
                creationEngine4.getCanvasOperator().resetInfiniteCanvas();
                PagePreItemView currentItemView13 = getCurrentItemView();
                Intrinsics.checkNotNull(currentItemView13);
                PreICreationEngine creationEngine5 = currentItemView13.getCreationEngine();
                Intrinsics.checkNotNull(creationEngine5);
                ICanvasOperator canvasOperator = creationEngine5.getCanvasOperator();
                float f2 = pageItemData.maxWidth;
                PagePreLayoutManager layoutManager = getLayoutManager();
                Intrinsics.checkNotNull(layoutManager);
                canvasOperator.setVisibleFixedWidth(f2 / layoutManager.getPageScaleRatio());
            }
            float itemHeight = pageItemData.getItemHeight(getMVisibleWidth(), 1.0f);
            PagePreItemView currentItemView14 = getCurrentItemView();
            Intrinsics.checkNotNull(currentItemView14);
            currentItemView14.setBottomShieldHeight(pageItemData.maxHeight - mWindowWidth);
            PagePreItemView currentItemView15 = getCurrentItemView();
            Intrinsics.checkNotNull(currentItemView15);
            currentItemView15.setShieldWidth((pageItemData.maxWidth - mWindowWidth2) / 2);
            if (pageItemData.sizeUnit == 0) {
                dMax = Math.max(Math.max(getMVisibleHeight(), itemHeight), (((double) (getMVisibleWidth() * pageItemData.height)) * 1.0d) / ((double) pageItemData.width));
            } else {
                dMax = Math.max(getMVisibleHeight(), itemHeight);
            }
            float f3 = (int) dMax;
            PagePreItemView currentItemView16 = getCurrentItemView();
            Intrinsics.checkNotNull(currentItemView16);
            currentItemView16.setCanvasSize(getMVisibleWidth(), f3);
        }
        if (getCurrentPosition() == pageSizeConfig.position) {
            PagePreLayoutManager layoutManager2 = getLayoutManager();
            Intrinsics.checkNotNull(layoutManager2);
            float fComputeItemTopByPosition = layoutManager2.computeItemTopByPosition(getCurrentPosition()) + ((mWindowWidth - UIUtils.dip2px(getMContext(), 200.0f)) * getScale());
            PagePreLayoutManager layoutManager3 = getLayoutManager();
            Intrinsics.checkNotNull(layoutManager3);
            float[] location = layoutManager3.getLocation();
            float f4 = -fComputeItemTopByPosition;
            if (location[1] < f4) {
                PagePreLayoutManager layoutManager4 = getLayoutManager();
                Intrinsics.checkNotNull(layoutManager4);
                layoutManager4.scrollTo(location[0], f4);
            }
        }
        int size = this.mList.size();
        for (int i3 = pageSizeConfig.position; i3 < size; i3++) {
            this.mList.get(i3).isModified = true;
        }
    }

    public final boolean canChangeSizeConfig(int position) {
        if (!this.mList.isEmpty() && position >= 0 && position < this.mList.size()) {
            PageItemData pageItemData = this.mList.get(position);
            boolean zIsEmpty = TextUtils.isEmpty(pageItemData.pdfId);
            boolean zIsEmpty2 = TextUtils.isEmpty(pageItemData.bgImage);
            if (zIsEmpty && zIsEmpty2) {
                return true;
            }
        }
        return false;
    }

    public final boolean invalidPosition() {
        return getCurrentPosition() == -1 || getCurrentItemView() == null;
    }

    public final float[] calcAllCurveBound() {
        if (getCurrentItemView() != null) {
            PagePreItemView currentItemView = getCurrentItemView();
            Intrinsics.checkNotNull(currentItemView);
            if (currentItemView.getCreationEngine() != null) {
                PagePreItemView currentItemView2 = getCurrentItemView();
                Intrinsics.checkNotNull(currentItemView2);
                PreICreationEngine creationEngine = currentItemView2.getCreationEngine();
                Intrinsics.checkNotNull(creationEngine);
                float[] fArrCalcAllCurveBound = creationEngine.calcAllCurveBound();
                Intrinsics.checkNotNullExpressionValue(fArrCalcAllCurveBound, "calcAllCurveBound(...)");
                return fArrCalcAllCurveBound;
            }
        }
        return new float[]{-2.1474836E9f, -2.1474836E9f, 2.1474836E9f, 2.1474836E9f};
    }

    public final float[] calcAllSpanBound() {
        if (getCurrentItemView() != null) {
            PagePreItemView currentItemView = getCurrentItemView();
            Intrinsics.checkNotNull(currentItemView);
            RectF allSpanBound = currentItemView.getAllSpanBound();
            if (allSpanBound != null) {
                return new float[]{-allSpanBound.left, -allSpanBound.top, -allSpanBound.right, -allSpanBound.bottom};
            }
        }
        return new float[]{-2.1474836E9f, -2.1474836E9f, 2.1474836E9f, 2.1474836E9f};
    }

    public final void pageLoadingFinish() {
        Log.d(TAG, "pageLoadingFinish: ");
        if (getMStateListener() != null) {
            PageStateListener mStateListener = getMStateListener();
            Intrinsics.checkNotNull(mStateListener);
            mStateListener.onPageLoadingFinish();
        }
    }

    private final String getBgImagePath(PageItemData itemData) {
        if (itemData.bgImage != null) {
            String bgImage = itemData.bgImage;
            Intrinsics.checkNotNullExpressionValue(bgImage, "bgImage");
            if (bgImage.length() != 0) {
                File file = new File(itemData.bgImage);
                if (file.exists()) {
                    return file.getPath();
                }
                PageBean mPageBean = getMPageBean();
                Intrinsics.checkNotNull(mPageBean);
                File file2 = new File(mPageBean.path, itemData.bgImage);
                if (file2.exists()) {
                    return file2.getPath();
                }
            }
        }
        return null;
    }

    private final String getBgImagePath(String bgImage) {
        if (bgImage == null || bgImage.length() == 0) {
            return null;
        }
        File file = new File(bgImage);
        if (file.exists()) {
            return file.getPath();
        }
        return null;
    }

    public final void setPageSizeListener(PageSizeListener listener) {
        this.mPageSizeListener = listener;
    }

    public final void getPdfBitmapForExport(List<Integer> list, final PDFExportListener listener) {
        List<Integer> list2 = list;
        if (list2 == null || list2.isEmpty() || this.mList.isEmpty()) {
            return;
        }
        String pdfId = this.mList.get(0).pdfId;
        Intrinsics.checkNotNullExpressionValue(pdfId, "pdfId");
        if (pdfId.length() == 0) {
            return;
        }
        if (this.mExportPdfBitmap == null) {
            ImportPdfBitmap importPdfBitmap = new ImportPdfBitmap();
            this.mExportPdfBitmap = importPdfBitmap;
            Intrinsics.checkNotNull(importPdfBitmap);
            importPdfBitmap.setLoadListener(new PDFLoadingListener() { // from class: com.miui.libhandwritingpreview.model.PageInkPreModel.getPdfBitmapForExport.1
                @Override // com.miui.libhandwritingpage.listener.PDFLoadingListener
                public void onLoaded() {
                }

                @Override // com.miui.libhandwritingpage.listener.PDFLoadingListener
                public void onRemove(int pageIndex) {
                }

                @Override // com.miui.libhandwritingpage.listener.PDFLoadingListener
                public void onLoaded(int pageIndex) {
                    PreICreationEngine creationEngine;
                    PreMiCurveEngine curveEngine;
                    PrePageSystem pageSystem;
                    ImportPdfBitmap importPdfBitmap2 = PageInkPreModel.this.mExportPdfBitmap;
                    Intrinsics.checkNotNull(importPdfBitmap2);
                    Bitmap bitmap = importPdfBitmap2.getBitmap(pageIndex);
                    if (bitmap != null) {
                        Log.d(PageInkPreModel.TAG, "exportPdfBitmap onLoaded:" + pageIndex);
                        PagePreDrawView mDrawView = PageInkPreModel.this.getMDrawView();
                        if (mDrawView == null || (creationEngine = mDrawView.getCreationEngine()) == null || (curveEngine = creationEngine.getCurveEngine()) == null || (pageSystem = curveEngine.getPageSystem()) == null) {
                            return;
                        }
                        pageSystem.setPageBgImage(pageIndex, bitmap, false);
                    }
                }

                @Override // com.miui.libhandwritingpage.listener.PDFLoadingListener
                public void onFinish() {
                    Log.d(PageInkPreModel.TAG, "onFinish");
                    PDFExportListener pDFExportListener = listener;
                    if (pDFExportListener != null) {
                        pDFExportListener.onFinish();
                    }
                    ImportPdfBitmap importPdfBitmap2 = PageInkPreModel.this.mExportPdfBitmap;
                    Intrinsics.checkNotNull(importPdfBitmap2);
                    importPdfBitmap2.release();
                    PageInkPreModel.this.mExportPdfBitmap = null;
                }
            });
        }
        ImportPdfBitmap importPdfBitmap2 = this.mExportPdfBitmap;
        Intrinsics.checkNotNull(importPdfBitmap2);
        if (importPdfBitmap2.isLoading()) {
            if (listener != null) {
                listener.onLoading();
                return;
            }
            return;
        }
        LinkedHashMap<Integer, Integer> linkedHashMap = new LinkedHashMap<>();
        Iterator<Integer> it = list.iterator();
        while (it.hasNext()) {
            int iIntValue = it.next().intValue();
            linkedHashMap.put(Integer.valueOf(iIntValue), Integer.valueOf(this.mList.get(iIntValue).pdfIndex));
        }
        ImportPdfBitmap importPdfBitmap3 = this.mExportPdfBitmap;
        Intrinsics.checkNotNull(importPdfBitmap3);
        importPdfBitmap3.setVisibleRect(getMVisibleWidth());
        ImportPdfBitmap importPdfBitmap4 = this.mExportPdfBitmap;
        Intrinsics.checkNotNull(importPdfBitmap4);
        importPdfBitmap4.setData(getDirPath() + File.separator + this.mList.get(0).pdfId, linkedHashMap);
    }

    public final int getWindowWidth() {
        return this.windowInfo.getMWindowWidth();
    }

    public final void getMultiPageThumbnailStatus(PdfThumbnailLoadListener listener) {
        this.mPdfThumbnailLoadListener = listener;
        if (getMExportLoaded()) {
            loadFinish();
        } else {
            bindItemDataForExport();
        }
    }

    public final void bindItemDataForExport() {
        if (getAdapter() != null) {
            String str = TAG;
            StringCompanionObject stringCompanionObject = StringCompanionObject.INSTANCE;
            String str2 = String.format("bindItemData: %d %d", Arrays.copyOf(new Object[]{Integer.valueOf(getCurrentPosition()), Integer.valueOf(this.mList.size())}, 2));
            Intrinsics.checkNotNullExpressionValue(str2, "format(...)");
            Log.d(str, str2);
            Stream<PageItemData> stream = this.mList.stream();
            final Function1 function1 = new Function1() { // from class: com.miui.libhandwritingpreview.model.PageInkPreModel$$ExternalSyntheticLambda11
                @Override // kotlin.jvm.functions.Function1
                public final Object invoke(Object obj) {
                    return Boolean.valueOf(PageInkPreModel.bindItemDataForExport$lambda$16((PageItemData) obj));
                }
            };
            Stream<PageItemData> streamFilter = stream.filter(new Predicate() { // from class: com.miui.libhandwritingpreview.model.PageInkPreModel$$ExternalSyntheticLambda12
                @Override // java.util.function.Predicate
                public final boolean test(Object obj) {
                    return PageInkPreModel.bindItemDataForExport$lambda$17(function1, obj);
                }
            });
            final Function1 function2 = new Function1() { // from class: com.miui.libhandwritingpreview.model.PageInkPreModel$$ExternalSyntheticLambda13
                @Override // kotlin.jvm.functions.Function1
                public final Object invoke(Object obj) {
                    return PageInkPreModel.bindItemDataForExport$lambda$18((PageItemData) obj);
                }
            };
            List<Integer> list = (List) streamFilter.map(new Function() { // from class: com.miui.libhandwritingpreview.model.PageInkPreModel$$ExternalSyntheticLambda14
                @Override // java.util.function.Function
                public final Object apply(Object obj) {
                    return PageInkPreModel.bindItemDataForExport$lambda$19(function2, obj);
                }
            }).collect(Collectors.toList());
            if (list.isEmpty()) {
                bindExportFinish();
                return;
            }
            PagePreAdapter adapter = getAdapter();
            Intrinsics.checkNotNull(adapter);
            PagePreItemView currentItemView = getCurrentItemView();
            Intrinsics.checkNotNull(currentItemView);
            Intrinsics.checkNotNull(list);
            adapter.bindItemForExport(currentItemView, list);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final boolean bindItemDataForExport$lambda$16(PageItemData itemData) {
        Intrinsics.checkNotNullParameter(itemData, "itemData");
        return !itemData.hadLoad;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final boolean bindItemDataForExport$lambda$17(Function1 function1, Object obj) {
        return ((Boolean) function1.invoke(obj)).booleanValue();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Integer bindItemDataForExport$lambda$18(PageItemData obj) {
        Intrinsics.checkNotNullParameter(obj, "obj");
        return Integer.valueOf(obj.getPageIndex());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Integer bindItemDataForExport$lambda$19(Function1 function1, Object obj) {
        return (Integer) function1.invoke(obj);
    }

    public final void bindExportFinish() {
        this.mExportLoaded = true;
        loadFinish();
    }

    public final void bindPageImage(int index) {
        PagePreItemView currentItemView;
        PreICreationEngine creationEngine;
        PreMiCurveEngine curveEngine;
        SpannedLayoutModel spannedLayoutModel;
        PreICreationEngine creationEngine2;
        PreMiCurveEngine curveEngine2;
        if (getIsUnlimited()) {
            return;
        }
        if (getCurrentPosition() >= 3 || index >= 3) {
            PagePreItemView currentItemView2 = getCurrentItemView();
            if (((currentItemView2 == null || (creationEngine2 = currentItemView2.getCreationEngine()) == null || (curveEngine2 = creationEngine2.getCurveEngine()) == null) ? null : curveEngine2.getSpannedLayoutModel()) == null || (currentItemView = getCurrentItemView()) == null || (creationEngine = currentItemView.getCreationEngine()) == null || (curveEngine = creationEngine.getCurveEngine()) == null || (spannedLayoutModel = curveEngine.getSpannedLayoutModel()) == null) {
                return;
            }
            spannedLayoutModel.bindExportImage(index);
        }
    }

    /* JADX INFO: renamed from: checkLoadFinish, reason: from getter */
    private final boolean getMExportLoaded() {
        return this.mExportLoaded;
    }

    private final void loadFinish() {
        if (getMExportLoaded()) {
            PagePreItemView currentItemView = getCurrentItemView();
            Intrinsics.checkNotNull(currentItemView);
            currentItemView.postDelayed(new Runnable() { // from class: com.miui.libhandwritingpreview.model.PageInkPreModel$$ExternalSyntheticLambda15
                @Override // java.lang.Runnable
                public final void run() {
                    PageInkPreModel.loadFinish$lambda$20(this.f$0);
                }
            }, 2000L);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void loadFinish$lambda$20(PageInkPreModel pageInkPreModel) {
        PdfThumbnailLoadListener pdfThumbnailLoadListener = pageInkPreModel.mPdfThumbnailLoadListener;
        if (pdfThumbnailLoadListener != null) {
            Intrinsics.checkNotNull(pdfThumbnailLoadListener);
            pdfThumbnailLoadListener.onFinish();
        }
    }

    public final void clearHighLight() {
        PreICreationEngine creationEngine;
        PreMiCurveEngine curveEngine;
        PreHighLightSystem highLightSystem;
        PagePreItemView currentItemView = getCurrentItemView();
        if (currentItemView == null || (creationEngine = currentItemView.getCreationEngine()) == null || (curveEngine = creationEngine.getCurveEngine()) == null || (highLightSystem = curveEngine.getHighLightSystem()) == null) {
            return;
        }
        highLightSystem.shutdownHighlight();
    }

    /* JADX INFO: compiled from: PageInkPreModel.kt */
    @Metadata(d1 = {"\u0000\u001c\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0003\n\u0002\u0010\b\n\u0002\b\u0005\n\u0002\u0010\u0002\n\u0002\b\u0003\b\u0082\u0004\u0018\u00002\u00020\u0001B\u0007¢\u0006\u0004\b\u0002\u0010\u0003J\u0016\u0010\n\u001a\u00020\u000b2\u0006\u0010\f\u001a\u00020\u00052\u0006\u0010\r\u001a\u00020\u0005R\u001a\u0010\u0004\u001a\u00020\u0005X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0006\u0010\u0007\"\u0004\b\b\u0010\t¨\u0006\u000e"}, d2 = {"Lcom/miui/libhandwritingpreview/model/PageInkPreModel$WindowInfo;", "", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "(Lcom/miui/libhandwritingpreview/model/PageInkPreModel;)V", "mWindowWidth", "", "getMWindowWidth", "()I", "setMWindowWidth", "(I)V", "onSizeChanged", "", AnimatedProperty.PROPERTY_NAME_W, AnimatedProperty.PROPERTY_NAME_H, "libHandWritingPreview_release"}, k = 1, mv = {2, 2, 0}, xi = 48)
    private final class WindowInfo {
        private int mWindowWidth = -1;

        public WindowInfo() {
            int realWidth = UIUtils.getRealWidth(PageInkPreModel.this.getMContext());
            int realHeight = UIUtils.getRealHeight(PageInkPreModel.this.getMContext());
            if (PageInkPreModel.this.getCurrentItemView() != null) {
                PagePreItemView currentItemView = PageInkPreModel.this.getCurrentItemView();
                Intrinsics.checkNotNull(currentItemView);
                if (currentItemView.getWidth() != 0) {
                    PagePreItemView currentItemView2 = PageInkPreModel.this.getCurrentItemView();
                    Intrinsics.checkNotNull(currentItemView2);
                    if (currentItemView2.getHeight() != 0) {
                        PagePreItemView currentItemView3 = PageInkPreModel.this.getCurrentItemView();
                        Intrinsics.checkNotNull(currentItemView3);
                        realWidth = currentItemView3.getWidth();
                        PagePreItemView currentItemView4 = PageInkPreModel.this.getCurrentItemView();
                        Intrinsics.checkNotNull(currentItemView4);
                        realHeight = currentItemView4.getHeight();
                    }
                }
            }
            onSizeChanged(realWidth, realHeight);
        }

        public final int getMWindowWidth() {
            return this.mWindowWidth;
        }

        public final void setMWindowWidth(int i) {
            this.mWindowWidth = i;
        }

        public final void onSizeChanged(int w, int h) {
            this.mWindowWidth = (int) Math.max(w, h + PageInkPreModel.this.mPageInkView.getToolViewHeight());
            if (PageInkPreModel.this.getIsUnlimited()) {
                this.mWindowWidth = w;
            }
        }
    }

    static {
        Intrinsics.checkNotNullExpressionValue("PageInkPreModel", "getSimpleName(...)");
        TAG = "PageInkPreModel";
    }
}
