package com.miui.handwriting.common.ai.viewmodel;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.util.Base64;
import android.util.Log;
import android.widget.Toast;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelKt;
import ch.qos.logback.core.CoreConstants;
import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestBuilder;
import com.bumptech.glide.request.target.CustomTarget;
import com.bumptech.glide.request.transition.Transition;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.market.sdk.reflect.ReflectUtilsForMiui;
import com.miui.aiengine.common.request.Request;
import com.miui.common.stat.ToDoStat;
import com.miui.creation.common.R;
import com.miui.handwriting.common.HandWritingViewApplication;
import com.miui.handwriting.common.ai.AESUtil;
import com.miui.handwriting.common.ai.CreationAIRequest2;
import com.miui.handwriting.common.ai.NetConfig;
import com.miui.handwriting.common.ai.anno.CreationType;
import com.miui.handwriting.common.ai.data.CreationData;
import com.miui.handwriting.common.ai.data.CreationImage;
import com.miui.handwriting.common.ai.data.ReferenceData;
import com.miui.handwriting.common.ai.data.ReferenceItem;
import com.miui.handwriting.common.ai.data.Result;
import com.miui.handwriting.common.ai.data.ThemeData;
import com.miui.handwriting.common.ai.data.ThemeItem;
import com.miui.handwriting.common.ai.data.UploadMetaData;
import com.miui.handwriting.common.ai.data.requestparam.ParamImageInfo;
import com.miui.handwriting.common.ai.data.requestparam.ParamUploadMetaData;
import com.miui.handwriting.common.ai.data.state.BallStateLiveData;
import com.miui.handwriting.common.ai.data.state.RequestStateLiveData;
import com.miui.handwriting.common.ai.data.state.SingleLiveData;
import com.miui.handwriting.common.ai.viewmodel.cache.RecreateInfo;
import com.miui.handwriting.common.ai.viewmodel.error.CreationException;
import com.miui.handwriting.common.data.bean.CreationBean;
import com.miui.handwriting.common.tools.DeviceUtils;
import com.miui.handwriting.common.tools.Utils;
import com.miui.handwriting.common.tools.constants.Constants;
import com.miui.notes.ai.stat.AITextWidgetStat;
import com.xiaomi.mirror.synergy.CallMethod;
import com.xiaomi.onetrack.api.ah;
import defpackage.R2;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.CancellationException;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicBoolean;
import javax.crypto.SecretKey;
import javax.crypto.spec.GCMParameterSpec;
import kotlin.Metadata;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.collections.ArraysKt;
import kotlin.collections.CollectionsKt;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.IntrinsicsKt;
import kotlin.coroutines.jvm.internal.Boxing;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.SpillingKt;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;
import kotlinx.coroutines.AwaitKt;
import kotlinx.coroutines.BuildersKt;
import kotlinx.coroutines.BuildersKt__Builders_commonKt;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.Deferred;
import kotlinx.coroutines.Dispatchers;
import kotlinx.coroutines.Job;
import org.ccil.cowan.tagsoup.XMLWriter;

/* JADX INFO: compiled from: AIViewModel2.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u0000æ\u0001\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0002\b\u0005\n\u0002\u0010\b\n\u0002\b\u0005\n\u0002\u0010\t\n\u0002\b\u0007\n\u0002\u0010\u000b\n\u0002\b\u0014\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u0007\n\u0002\b\u0017\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\f\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u000e\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u000b\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\r\n\u0002\u0010 \n\u0002\b\u0011\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0002\b\b\n\u0002\u0018\u0002\n\u0002\b\r\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b$\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u000f\u0018\u0000 \u009a\u00022\u00020\u0001:\u0002\u009a\u0002B\u0007¢\u0006\u0004\b\u0002\u0010\u0003J\u0011\u0010°\u0001\u001a\u00030±\u00012\u0007\u0010²\u0001\u001a\u00020\u0005J\b\u0010à\u0001\u001a\u00030±\u0001J\n\u0010á\u0001\u001a\u00030±\u0001H\u0002J\n\u0010â\u0001\u001a\u00030±\u0001H\u0002J4\u0010ã\u0001\u001a\u00030±\u00012\u0007\u0010ä\u0001\u001a\u00020\u00052\t\u0010å\u0001\u001a\u0004\u0018\u00010\u00052\u000b\b\u0002\u0010æ\u0001\u001a\u0004\u0018\u00010d2\t\u0010ç\u0001\u001a\u0004\u0018\u00010^JD\u0010è\u0001\u001a\u00030±\u00012\u0007\u0010ä\u0001\u001a\u00020\u00052\b\u0010é\u0001\u001a\u00030Ý\u00012\u000b\b\u0002\u0010æ\u0001\u001a\u0004\u0018\u00010d2\u000b\b\u0002\u0010å\u0001\u001a\u0004\u0018\u00010\u00052\u000b\b\u0002\u0010ç\u0001\u001a\u0004\u0018\u00010^H\u0002J4\u0010ê\u0001\u001a\u00030±\u00012\u0007\u0010ä\u0001\u001a\u00020\u00052\u0007\u0010ë\u0001\u001a\u00020\u00052\u000b\b\u0002\u0010æ\u0001\u001a\u0004\u0018\u00010d2\u000b\b\u0002\u0010å\u0001\u001a\u0004\u0018\u00010\u0005JA\u0010ì\u0001\u001a\u00030±\u00012\u0007\u0010ä\u0001\u001a\u00020\u00052\u0007\u0010í\u0001\u001a\u00020.2\u000b\b\u0002\u0010æ\u0001\u001a\u0004\u0018\u00010d2\u000b\b\u0002\u0010å\u0001\u001a\u0004\u0018\u00010\u00052\u000b\b\u0002\u0010ç\u0001\u001a\u0004\u0018\u00010^J=\u0010î\u0001\u001a\u00030±\u00012\u0007\u0010ä\u0001\u001a\u00020\u00052\u0007\u0010ë\u0001\u001a\u00020\u00052\u0007\u0010ç\u0001\u001a\u00020^2\u000b\b\u0002\u0010æ\u0001\u001a\u0004\u0018\u00010d2\u000b\b\u0002\u0010å\u0001\u001a\u0004\u0018\u00010\u0005J=\u0010ï\u0001\u001a\u00030±\u00012\u0007\u0010ä\u0001\u001a\u00020\u00052\u0007\u0010ë\u0001\u001a\u00020\u00052\u0007\u0010ð\u0001\u001a\u00020\u00052\u000b\b\u0002\u0010æ\u0001\u001a\u0004\u0018\u00010d2\u000b\b\u0002\u0010å\u0001\u001a\u0004\u0018\u00010\u0005J=\u0010ñ\u0001\u001a\u00030±\u00012\u0007\u0010ä\u0001\u001a\u00020\u00052\u0007\u0010ë\u0001\u001a\u00020\u00052\u0007\u0010ð\u0001\u001a\u00020\u00052\u000b\b\u0002\u0010æ\u0001\u001a\u0004\u0018\u00010d2\u000b\b\u0002\u0010å\u0001\u001a\u0004\u0018\u00010\u0005J\b\u0010ò\u0001\u001a\u00030±\u0001Jd\u0010ó\u0001\u001a\u00030±\u00012\u0007\u0010ä\u0001\u001a\u00020\u00052\u0007\u0010ë\u0001\u001a\u00020\u00052\u0007\u0010ô\u0001\u001a\u00020\u000b2\u000b\b\u0002\u0010æ\u0001\u001a\u0004\u0018\u00010d2\u000b\b\u0002\u0010å\u0001\u001a\u0004\u0018\u00010\u00052\u000b\b\u0002\u0010ç\u0001\u001a\u0004\u0018\u00010^2\u000b\b\u0002\u0010ð\u0001\u001a\u0004\u0018\u00010\u00052\t\b\u0002\u0010õ\u0001\u001a\u00020\u0005H\u0002J\n\u0010ö\u0001\u001a\u00030±\u0001H\u0014J\b\u0010÷\u0001\u001a\u00030±\u0001J\u0089\u0001\u0010ø\u0001\u001a\u00020\u00052\u0007\u0010ä\u0001\u001a\u00020\u00052\u0007\u0010ë\u0001\u001a\u00020\u00052\u0007\u0010ù\u0001\u001a\u00020\u00052\u0007\u0010ú\u0001\u001a\u00020\u00052\u0007\u0010û\u0001\u001a\u00020\u00052\u000b\b\u0002\u0010ü\u0001\u001a\u0004\u0018\u00010\u000b2\u000b\b\u0002\u0010æ\u0001\u001a\u0004\u0018\u00010d2\u000b\b\u0002\u0010å\u0001\u001a\u0004\u0018\u00010\u00052\u000b\b\u0002\u0010ç\u0001\u001a\u0004\u0018\u00010^2\u000b\b\u0002\u0010ð\u0001\u001a\u0004\u0018\u00010\u00052\t\b\u0002\u0010õ\u0001\u001a\u00020\u0005H\u0082@¢\u0006\u0003\u0010ý\u0001J\u0086\u0001\u0010þ\u0001\u001a\u00030±\u00012\u0007\u0010ä\u0001\u001a\u00020\u00052\u0007\u0010ë\u0001\u001a\u00020\u00052\u0007\u0010ù\u0001\u001a\u00020\u00052\u0007\u0010ú\u0001\u001a\u00020\u00052\u0007\u0010û\u0001\u001a\u00020\u00052\u0007\u0010ô\u0001\u001a\u00020\u000b2\u000b\b\u0002\u0010æ\u0001\u001a\u0004\u0018\u00010d2\u000b\b\u0002\u0010å\u0001\u001a\u0004\u0018\u00010\u00052\u000b\b\u0002\u0010ç\u0001\u001a\u0004\u0018\u00010^2\u000b\b\u0002\u0010ð\u0001\u001a\u0004\u0018\u00010\u00052\t\b\u0002\u0010õ\u0001\u001a\u00020\u0005H\u0082@¢\u0006\u0003\u0010ÿ\u0001JH\u0010\u0080\u0002\u001a\u00030±\u00012\u0011\u0010\u0081\u0002\u001a\f\u0012\u0005\u0012\u00030\u0083\u0002\u0018\u00010\u0082\u00022\t\u0010ç\u0001\u001a\u0004\u0018\u00010^2\t\u0010æ\u0001\u001a\u0004\u0018\u00010d2\b\u0010é\u0001\u001a\u00030Ý\u00012\t\u0010å\u0001\u001a\u0004\u0018\u00010\u0005H\u0002J;\u0010\u0084\u0002\u001a\u00030±\u00012\u0007\u0010ä\u0001\u001a\u00020\u00052\u0007\u0010\u0085\u0002\u001a\u00020\u00052\t\u0010æ\u0001\u001a\u0004\u0018\u00010d2\t\u0010å\u0001\u001a\u0004\u0018\u00010\u00052\u0007\u0010í\u0001\u001a\u00020.H\u0002J`\u0010\u0086\u0002\u001a\u00020\u00052\u0007\u0010ä\u0001\u001a\u00020\u00052\u0007\u0010û\u0001\u001a\u00020\u00052\u0007\u0010ù\u0001\u001a\u00020\u00052\u0007\u0010ú\u0001\u001a\u00020\u00052\t\u0010æ\u0001\u001a\u0004\u0018\u00010d2\t\u0010å\u0001\u001a\u0004\u0018\u00010\u00052\t\u0010ç\u0001\u001a\u0004\u0018\u00010^2\t\b\u0002\u0010õ\u0001\u001a\u00020\u0005H\u0082@¢\u0006\u0003\u0010\u0087\u0002J3\u0010\u0088\u0002\u001a\u00020\u000b2!\u0010\u0089\u0002\u001a\u001c\b\u0001\u0012\f\u0012\n\u0012\u0005\u0012\u00030±\u00010\u008b\u0002\u0012\u0007\u0012\u0005\u0018\u00010\u008c\u00020\u008a\u0002H\u0082@¢\u0006\u0003\u0010\u008d\u0002JM\u0010\u008e\u0002\u001a\u00030±\u00012\u0007\u0010ä\u0001\u001a\u00020\u00052\u0007\u0010í\u0001\u001a\u00020.2\b\u0010é\u0001\u001a\u00030Ý\u00012\u000b\b\u0002\u0010æ\u0001\u001a\u0004\u0018\u00010d2\u000b\b\u0002\u0010å\u0001\u001a\u0004\u0018\u00010\u00052\u000b\b\u0002\u0010ç\u0001\u001a\u0004\u0018\u00010^H\u0002J'\u0010\u008f\u0002\u001a\u00030±\u00012\t\u0010ë\u0001\u001a\u0004\u0018\u00010\u00052\u0007\u0010û\u0001\u001a\u00020\u00052\u0007\u0010ä\u0001\u001a\u00020\u0005H\u0002J.\u0010\u0090\u0002\u001a\u00020\u00192\t\u0010\u0091\u0002\u001a\u0004\u0018\u00010\u00052\t\u0010\u0092\u0002\u001a\u0004\u0018\u00010\u000b2\u0007\u0010\u0093\u0002\u001a\u00020\u0005H\u0002¢\u0006\u0003\u0010\u0094\u0002J\u0014\u0010\u0095\u0002\u001a\u00020\u00192\t\u0010\u0096\u0002\u001a\u0004\u0018\u00010\u0005H\u0002J#\u0010\u0097\u0002\u001a\u00030±\u00012\u0007\u0010ä\u0001\u001a\u00020\u00052\u0007\u0010ë\u0001\u001a\u00020\u00052\u0007\u0010å\u0001\u001a\u00020\u0005J`\u0010\u0098\u0002\u001a\u00020\u00052\u0007\u0010ä\u0001\u001a\u00020\u00052\u0007\u0010ù\u0001\u001a\u00020\u00052\u0007\u0010ú\u0001\u001a\u00020\u00052\u0007\u0010û\u0001\u001a\u00020\u00052\t\u0010æ\u0001\u001a\u0004\u0018\u00010d2\t\u0010å\u0001\u001a\u0004\u0018\u00010\u00052\t\u0010ç\u0001\u001a\u0004\u0018\u00010^2\t\b\u0002\u0010õ\u0001\u001a\u00020\u0005H\u0082@¢\u0006\u0003\u0010\u0087\u0002J%\u0010\u0099\u0002\u001a\u00030±\u00012\u0007\u0010å\u0001\u001a\u00020\u00052\u0007\u0010û\u0001\u001a\u00020\u00052\u0007\u0010ä\u0001\u001a\u00020\u0005H\u0002R\u001a\u0010\u0004\u001a\u00020\u0005X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0006\u0010\u0007\"\u0004\b\b\u0010\tR\u001a\u0010\n\u001a\u00020\u000bX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\f\u0010\r\"\u0004\b\u000e\u0010\u000fR\u001a\u0010\u0010\u001a\u00020\u0011X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0012\u0010\u0013\"\u0004\b\u0014\u0010\u0015R\u001a\u0010\u0016\u001a\u00020\u000bX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0016\u0010\r\"\u0004\b\u0017\u0010\u000fR\u001a\u0010\u0018\u001a\u00020\u0019X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u001a\u0010\u001b\"\u0004\b\u0017\u0010\u001cR\u001a\u0010\u001d\u001a\u00020\u0019X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u001d\u0010\u001b\"\u0004\b\u001e\u0010\u001cR\u001a\u0010\u001f\u001a\u00020\u0005X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b \u0010\u0007\"\u0004\b!\u0010\tR\u001a\u0010\"\u001a\u00020\u000bX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b#\u0010\r\"\u0004\b$\u0010\u000fR\u001a\u0010%\u001a\u00020\u000bX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b&\u0010\r\"\u0004\b'\u0010\u000fR\u001a\u0010(\u001a\u00020\u000bX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b)\u0010\r\"\u0004\b*\u0010\u000fR\u001a\u0010+\u001a\u00020\u0019X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b+\u0010\u001b\"\u0004\b,\u0010\u001cR\u001c\u0010-\u001a\u0004\u0018\u00010.X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b/\u00100\"\u0004\b1\u00102R\u001a\u00103\u001a\u000204X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b5\u00106\"\u0004\b7\u00108R\u001a\u00109\u001a\u000204X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b:\u00106\"\u0004\b;\u00108R\u001a\u0010<\u001a\u00020\u000bX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b=\u0010\r\"\u0004\b>\u0010\u000fR\u001a\u0010?\u001a\u00020\u000bX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b@\u0010\r\"\u0004\bA\u0010\u000fR\u001a\u0010B\u001a\u000204X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\bC\u00106\"\u0004\bD\u00108R\u001a\u0010E\u001a\u000204X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\bF\u00106\"\u0004\bG\u00108R\u001a\u0010H\u001a\u000204X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\bI\u00106\"\u0004\bJ\u00108R\u0011\u0010K\u001a\u00020L¢\u0006\b\n\u0000\u001a\u0004\bM\u0010NR*\u0010O\u001a\u0012\u0012\u0004\u0012\u00020L0Pj\b\u0012\u0004\u0012\u00020L`QX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\bR\u0010S\"\u0004\bT\u0010UR\u001c\u0010V\u001a\u0004\u0018\u00010LX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\bW\u0010N\"\u0004\bX\u0010YR\u001a\u0010Z\u001a\u00020\u0005X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b[\u0010\u0007\"\u0004\b\\\u0010\tR\u001c\u0010]\u001a\u0004\u0018\u00010^X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b_\u0010`\"\u0004\ba\u0010bR\u001c\u0010c\u001a\u0004\u0018\u00010dX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\be\u0010f\"\u0004\bg\u0010hR\u001a\u0010i\u001a\u00020\u0005X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\bj\u0010\u0007\"\u0004\bk\u0010\tR\u001a\u0010l\u001a\u00020\u0005X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\bm\u0010\u0007\"\u0004\bn\u0010\tR\u001a\u0010o\u001a\u00020\u0005X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\bp\u0010\u0007\"\u0004\bq\u0010\tR\u001c\u0010r\u001a\u0004\u0018\u00010sX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\bt\u0010u\"\u0004\bv\u0010wR\u001a\u0010x\u001a\u00020yX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\bx\u0010z\"\u0004\b{\u0010|R\u001a\u0010}\u001a\u00020yX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b}\u0010z\"\u0004\b~\u0010|R\u001b\u0010\u007f\u001a\u00020\u0019X\u0086\u000e¢\u0006\u000f\n\u0000\u001a\u0004\b\u007f\u0010\u001b\"\u0005\b\u0080\u0001\u0010\u001cR\u001d\u0010\u0081\u0001\u001a\u00020\u0005X\u0086\u000e¢\u0006\u0010\n\u0000\u001a\u0005\b\u0082\u0001\u0010\u0007\"\u0005\b\u0083\u0001\u0010\tR\"\u0010\u0084\u0001\u001a\u0005\u0018\u00010\u0085\u0001X\u0086\u000e¢\u0006\u0012\n\u0000\u001a\u0006\b\u0086\u0001\u0010\u0087\u0001\"\u0006\b\u0088\u0001\u0010\u0089\u0001R\"\u0010\u008a\u0001\u001a\u0005\u0018\u00010\u008b\u0001X\u0086\u000e¢\u0006\u0012\n\u0000\u001a\u0006\b\u008c\u0001\u0010\u008d\u0001\"\u0006\b\u008e\u0001\u0010\u008f\u0001R\u001d\u0010\u0090\u0001\u001a\u00020\u0019X\u0086\u000e¢\u0006\u0010\n\u0000\u001a\u0005\b\u0090\u0001\u0010\u001b\"\u0005\b\u0091\u0001\u0010\u001cR\u001f\u0010\u0092\u0001\u001a\u0004\u0018\u00010\u0005X\u0086\u000e¢\u0006\u0010\n\u0000\u001a\u0005\b\u0093\u0001\u0010\u0007\"\u0005\b\u0094\u0001\u0010\tR\u001d\u0010\u0095\u0001\u001a\u00020\u0005X\u0086\u000e¢\u0006\u0010\n\u0000\u001a\u0005\b\u0096\u0001\u0010\u0007\"\u0005\b\u0097\u0001\u0010\tR&\u0010\u0098\u0001\u001a\t\u0012\u0004\u0012\u00020.0\u0099\u0001X\u0086\u000e¢\u0006\u0012\n\u0000\u001a\u0006\b\u009a\u0001\u0010\u009b\u0001\"\u0006\b\u009c\u0001\u0010\u009d\u0001R\u001d\u0010\u009e\u0001\u001a\u00020\u000bX\u0086\u000e¢\u0006\u0010\n\u0000\u001a\u0005\b\u009f\u0001\u0010\r\"\u0005\b \u0001\u0010\u000fR\u001d\u0010¡\u0001\u001a\u00020\u000bX\u0086\u000e¢\u0006\u0010\n\u0000\u001a\u0005\b¢\u0001\u0010\r\"\u0005\b£\u0001\u0010\u000fR\u001d\u0010¤\u0001\u001a\u00020\u000bX\u0086\u000e¢\u0006\u0010\n\u0000\u001a\u0005\b¥\u0001\u0010\r\"\u0005\b¦\u0001\u0010\u000fR\u001d\u0010§\u0001\u001a\u00020\u0019X\u0086\u000e¢\u0006\u0010\n\u0000\u001a\u0005\b¨\u0001\u0010\u001b\"\u0005\b©\u0001\u0010\u001cR\u0016\u0010ª\u0001\u001a\t\u0012\u0004\u0012\u00020\u00050«\u0001X\u0082\u0004¢\u0006\u0002\n\u0000R\u001b\u0010¬\u0001\u001a\t\u0012\u0004\u0012\u00020\u00050\u00ad\u00018F¢\u0006\b\u001a\u0006\b®\u0001\u0010¯\u0001R\u0019\u0010³\u0001\u001a\f\u0012\u0007\u0012\u0005\u0018\u00010\u0085\u00010«\u0001X\u0082\u0004¢\u0006\u0002\n\u0000R\u001e\u0010´\u0001\u001a\f\u0012\u0007\u0012\u0005\u0018\u00010\u0085\u00010\u00ad\u00018F¢\u0006\b\u001a\u0006\bµ\u0001\u0010¯\u0001R\u0019\u0010¶\u0001\u001a\f\u0012\u0007\u0012\u0005\u0018\u00010\u008b\u00010«\u0001X\u0082\u0004¢\u0006\u0002\n\u0000R\u001e\u0010·\u0001\u001a\f\u0012\u0007\u0012\u0005\u0018\u00010\u008b\u00010\u00ad\u00018F¢\u0006\b\u001a\u0006\b¸\u0001\u0010¯\u0001R\u0016\u0010¹\u0001\u001a\t\u0012\u0004\u0012\u00020.0º\u0001X\u0082\u0004¢\u0006\u0002\n\u0000R\u001b\u0010»\u0001\u001a\t\u0012\u0004\u0012\u00020.0º\u00018F¢\u0006\b\u001a\u0006\b¼\u0001\u0010½\u0001R\u0016\u0010¾\u0001\u001a\t\u0012\u0004\u0012\u00020\u000b0º\u0001X\u0082\u0004¢\u0006\u0002\n\u0000R\u001b\u0010¿\u0001\u001a\t\u0012\u0004\u0012\u00020\u000b0\u00ad\u00018F¢\u0006\b\u001a\u0006\bÀ\u0001\u0010¯\u0001R\u0018\u0010Á\u0001\u001a\u000b\u0012\u0006\u0012\u0004\u0018\u00010.0«\u0001X\u0082\u0004¢\u0006\u0002\n\u0000R\u001d\u0010Â\u0001\u001a\u000b\u0012\u0006\u0012\u0004\u0018\u00010.0\u00ad\u00018F¢\u0006\b\u001a\u0006\bÃ\u0001\u0010¯\u0001R\u0018\u0010Ä\u0001\u001a\u000b\u0012\u0006\u0012\u0004\u0018\u00010d0«\u0001X\u0082\u0004¢\u0006\u0002\n\u0000R\u001d\u0010Å\u0001\u001a\u000b\u0012\u0006\u0012\u0004\u0018\u00010d0\u00ad\u00018F¢\u0006\b\u001a\u0006\bÆ\u0001\u0010¯\u0001R\"\u0010Ç\u0001\u001a\u0005\u0018\u00010È\u0001X\u0086\u000e¢\u0006\u0012\n\u0000\u001a\u0006\bÉ\u0001\u0010Ê\u0001\"\u0006\bË\u0001\u0010Ì\u0001R\"\u0010Í\u0001\u001a\u0005\u0018\u00010È\u0001X\u0086\u000e¢\u0006\u0012\n\u0000\u001a\u0006\bÎ\u0001\u0010Ê\u0001\"\u0006\bÏ\u0001\u0010Ì\u0001R\u001b\u0010Ð\u0001\u001a\t\u0012\u0004\u0012\u00020.0Ñ\u0001¢\u0006\n\n\u0000\u001a\u0006\bÒ\u0001\u0010Ó\u0001R\u001b\u0010Ô\u0001\u001a\t\u0012\u0004\u0012\u00020.0Õ\u0001¢\u0006\n\n\u0000\u001a\u0006\bÖ\u0001\u0010×\u0001R\u0016\u0010Ø\u0001\u001a\t\u0012\u0004\u0012\u00020\u00190«\u0001X\u0082\u0004¢\u0006\u0002\n\u0000R\u001b\u0010Ù\u0001\u001a\t\u0012\u0004\u0012\u00020\u00190\u00ad\u00018F¢\u0006\b\u001a\u0006\bÚ\u0001\u0010¯\u0001R\u001d\u0010Û\u0001\u001a\u0010\u0012\u0004\u0012\u00020\u0005\u0012\u0005\u0012\u00030Ý\u00010Ü\u0001X\u0082\u0004¢\u0006\u0002\n\u0000R\u001d\u0010Þ\u0001\u001a\u00020\u0019X\u0086\u000e¢\u0006\u0010\n\u0000\u001a\u0005\bÞ\u0001\u0010\u001b\"\u0005\bß\u0001\u0010\u001c¨\u0006\u009b\u0002"}, d2 = {"Lcom/miui/handwriting/common/ai/viewmodel/AIViewModel2;", "Landroidx/lifecycle/ViewModel;", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "()V", "partialSelectQuitType", "", "getPartialSelectQuitType", "()Ljava/lang/String;", "setPartialSelectQuitType", "(Ljava/lang/String;)V", "currentBallState", "", "getCurrentBallState", "()I", "setCurrentBallState", "(I)V", "requestWay", "", "getRequestWay", "()J", "setRequestWay", "(J)V", "isAICreateState", "setAICreateState", "aICreateState", "", "getAICreateState", "()Z", "(Z)V", "isSelectPic", "setSelectPic", "aiFailReason", "getAiFailReason", "setAiFailReason", "aiFailCode", "getAiFailCode", "setAiFailCode", "aiStartTime", "getAiStartTime", "setAiStartTime", "aiEndTime", "getAiEndTime", "setAiEndTime", "isFirstTimeRequest", "setFirstTimeRequest", "recreateImage", "Lcom/miui/handwriting/common/ai/data/CreationImage;", "getRecreateImage", "()Lcom/miui/handwriting/common/ai/data/CreationImage;", "setRecreateImage", "(Lcom/miui/handwriting/common/ai/data/CreationImage;)V", "topTagX", "", "getTopTagX", "()F", "setTopTagX", "(F)V", "middleTagX", "getMiddleTagX", "setMiddleTagX", "currentSelectPosition", "getCurrentSelectPosition", "setCurrentSelectPosition", "endWay", "getEndWay", "setEndWay", "scaleY", "getScaleY", "setScaleY", "scaleX", "getScaleX", "setScaleX", "clFooterViewMaskX", "getClFooterViewMaskX", "setClFooterViewMaskX", "thinkingType", "Lcom/miui/handwriting/common/data/bean/CreationBean;", "getThinkingType", "()Lcom/miui/handwriting/common/data/bean/CreationBean;", "listData", "Ljava/util/ArrayList;", "Lkotlin/collections/ArrayList;", "getListData", "()Ljava/util/ArrayList;", "setListData", "(Ljava/util/ArrayList;)V", "currentSelectBean", "getCurrentSelectBean", "setCurrentSelectBean", "(Lcom/miui/handwriting/common/data/bean/CreationBean;)V", "currentDesc", "getCurrentDesc", "setCurrentDesc", "currentItemReferce", "Lcom/miui/handwriting/common/ai/data/ReferenceItem;", "getCurrentItemReferce", "()Lcom/miui/handwriting/common/ai/data/ReferenceItem;", "setCurrentItemReferce", "(Lcom/miui/handwriting/common/ai/data/ReferenceItem;)V", "curretThemeItem", "Lcom/miui/handwriting/common/ai/data/ThemeItem;", "getCurretThemeItem", "()Lcom/miui/handwriting/common/ai/data/ThemeItem;", "setCurretThemeItem", "(Lcom/miui/handwriting/common/ai/data/ThemeItem;)V", "currentPath", "getCurrentPath", "setCurrentPath", "currentPromote", "getCurrentPromote", "setCurrentPromote", "resultUrl", "getResultUrl", "setResultUrl", "placeholder", "Landroid/graphics/drawable/Drawable;", "getPlaceholder", "()Landroid/graphics/drawable/Drawable;", "setPlaceholder", "(Landroid/graphics/drawable/Drawable;)V", "isLoading", "Ljava/util/concurrent/atomic/AtomicBoolean;", "()Ljava/util/concurrent/atomic/AtomicBoolean;", "setLoading", "(Ljava/util/concurrent/atomic/AtomicBoolean;)V", "isRequestLoading", "setRequestLoading", "isButtonEnterIn", "setButtonEnterIn", "currentUrl", "getCurrentUrl", "setCurrentUrl", "currentThemeData", "Lcom/miui/handwriting/common/ai/data/ThemeData;", "getCurrentThemeData", "()Lcom/miui/handwriting/common/ai/data/ThemeData;", "setCurrentThemeData", "(Lcom/miui/handwriting/common/ai/data/ThemeData;)V", "currentReferenceData", "Lcom/miui/handwriting/common/ai/data/ReferenceData;", "getCurrentReferenceData", "()Lcom/miui/handwriting/common/ai/data/ReferenceData;", "setCurrentReferenceData", "(Lcom/miui/handwriting/common/ai/data/ReferenceData;)V", "isRecreate", "setRecreate", "recreateKey", "getRecreateKey", "setRecreateKey", "currentCreationText", "getCurrentCreationText", "setCurrentCreationText", "previewItemList", "", "getPreviewItemList", "()Ljava/util/List;", "setPreviewItemList", "(Ljava/util/List;)V", "currentPage", "getCurrentPage", "setCurrentPage", "imageWidth", "getImageWidth", "setImageWidth", "imageHeight", "getImageHeight", "setImageHeight", "showLoading", "getShowLoading", "setShowLoading", "_imageUrlLiveData", "Landroidx/lifecycle/MutableLiveData;", "imageUrlLiveData", "Landroidx/lifecycle/LiveData;", "getImageUrlLiveData", "()Landroidx/lifecycle/LiveData;", "setImageUrl", "", "str", "_themeLiveData", "themeLiveData", "getThemeLiveData", "_referenceLiveData", "referenceLiveData", "getReferenceLiveData", "_creationSmallImageLiveData", "Lcom/miui/handwriting/common/ai/data/state/SingleLiveData;", "creationSmallImageLiveData", "getCreationSmallImageLiveData", "()Lcom/miui/handwriting/common/ai/data/state/SingleLiveData;", "_errorCreation", "errorCreation", "getErrorCreation", "_creationBigImageLiveData", "creationBigImageLiveData", "getCreationBigImageLiveData", "_aiTheme", "aiTheme", "getAiTheme", "uploadJob", "Lkotlinx/coroutines/Job;", "getUploadJob", "()Lkotlinx/coroutines/Job;", "setUploadJob", "(Lkotlinx/coroutines/Job;)V", "requestEnhanceJob", "getRequestEnhanceJob", "setRequestEnhanceJob", "creationRequestLiveData", "Lcom/miui/handwriting/common/ai/data/state/RequestStateLiveData;", "getCreationRequestLiveData", "()Lcom/miui/handwriting/common/ai/data/state/RequestStateLiveData;", "ballStateLiveData", "Lcom/miui/handwriting/common/ai/data/state/BallStateLiveData;", "getBallStateLiveData", "()Lcom/miui/handwriting/common/ai/data/state/BallStateLiveData;", "_requestReady", "requestReady", "getRequestReady", "creationMap", "Ljava/util/concurrent/ConcurrentHashMap;", "Lcom/miui/handwriting/common/ai/viewmodel/cache/RecreateInfo;", "isUseEdit", "setUseEdit", "requestData", "requestListTheme", "requestReferenceItem", "textCreation", "mid", "prompt", "theme", "referenceItem", "textCreationByRequestId", "recreateInfo", "creationForAllImage", AITextWidgetStat.TYPE_IMAGE, "recreationByImageUrl", "creationImage", "creationByReference", "creationPartRepaint", NetConfig.PARAM_MASK_COORDINATES, "creationPartEliminate", "cancelCreation", "requestUploadMeta", "requestTimes", "creationType", "onCleared", ToDoStat.CANCEL, "requestCreation", "serverKey", "iv", "requestId", NetConfig.PARAM_CREATION_NUM, "(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/Integer;Lcom/miui/handwriting/common/ai/data/ThemeItem;Ljava/lang/String;Lcom/miui/handwriting/common/ai/data/ReferenceItem;Ljava/lang/String;Ljava/lang/String;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "asyncCreation", "(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;ILcom/miui/handwriting/common/ai/data/ThemeItem;Ljava/lang/String;Lcom/miui/handwriting/common/ai/data/ReferenceItem;Ljava/lang/String;Ljava/lang/String;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "postSmallImageResult", "result", "Lcom/miui/handwriting/common/ai/data/Result;", "Lcom/miui/handwriting/common/ai/data/CreationData;", "requestEnhance", "imageUrl", "reCreation", "(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Lcom/miui/handwriting/common/ai/data/ThemeItem;Ljava/lang/String;Lcom/miui/handwriting/common/ai/data/ReferenceItem;Ljava/lang/String;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "getResult", XMLWriter.METHOD, "Lkotlin/Function1;", "Lkotlin/coroutines/Continuation;", "", "(Lkotlin/jvm/functions/Function1;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "asyncRecreation", "getImageTheme", "isSameRequestParam", "requestPrompt", "requestTheme", CallMethod.ARG_URI, "(Ljava/lang/String;Ljava/lang/Integer;Ljava/lang/String;)Z", "istPart", "type", "getImageRequestId", "requestTextCreation", "getPromptTheme", "Companion", "libHandWritingCommon_release"}, k = 1, mv = {2, 2, 0}, xi = 48)
public final class AIViewModel2 extends ViewModel {
    private static final int DEFAULT_NUM = 3;
    public static final int END_WAY_AI_FAIL = 3;
    public static final int END_WAY_EXIT = 4;
    public static final int END_WAY_OK = 1;
    public static final int END_WAY_RETURN = 2;
    public static final String PARTIAL_REDRAW = "局部重绘";
    private static final int REQUEST_ONCE = 1;
    private static final String TAG = "AIViewModel2";
    public static final int THINKING_TYPE = 3;
    private final MutableLiveData<ThemeItem> _aiTheme;
    private final MutableLiveData<CreationImage> _creationBigImageLiveData;
    private final SingleLiveData<CreationImage> _creationSmallImageLiveData;
    private final SingleLiveData<Integer> _errorCreation;
    private final MutableLiveData<String> _imageUrlLiveData;
    private final MutableLiveData<ReferenceData> _referenceLiveData;
    private final MutableLiveData<Boolean> _requestReady;
    private final MutableLiveData<ThemeData> _themeLiveData;
    private boolean aICreateState;
    private final BallStateLiveData<CreationImage> ballStateLiveData;
    private float clFooterViewMaskX;
    private final ConcurrentHashMap<String, RecreateInfo> creationMap;
    private final RequestStateLiveData<CreationImage> creationRequestLiveData;
    private ReferenceItem currentItemReferce;
    private int currentPage;
    private ReferenceData currentReferenceData;
    private CreationBean currentSelectBean;
    private ThemeData currentThemeData;
    private ThemeItem curretThemeItem;
    private int imageHeight;
    private int imageWidth;
    private int isAICreateState;
    private boolean isButtonEnterIn;
    private boolean isRecreate;
    private boolean isSelectPic;
    private boolean isUseEdit;
    private float middleTagX;
    private Drawable placeholder;
    private List<CreationImage> previewItemList;
    private CreationImage recreateImage;
    private String recreateKey;
    private Job requestEnhanceJob;
    private long requestWay;
    private boolean showLoading;
    private float topTagX;
    private Job uploadJob;
    private String partialSelectQuitType = PARTIAL_REDRAW;
    private int currentBallState = -1;
    private String aiFailReason = "";
    private int aiFailCode = 200;
    private int aiStartTime = -1;
    private int aiEndTime = -1;
    private boolean isFirstTimeRequest = true;
    private int currentSelectPosition = -1;
    private int endWay = 4;
    private float scaleY = 1.0f;
    private float scaleX = 1.0f;
    private final CreationBean thinkingType = new CreationBean(false, "", 3, 0, null, null, null, 120, null);
    private ArrayList<CreationBean> listData = new ArrayList<>();
    private String currentDesc = "";
    private String currentPath = "";
    private String currentPromote = "";
    private String resultUrl = "";
    private AtomicBoolean isLoading = new AtomicBoolean(false);
    private AtomicBoolean isRequestLoading = new AtomicBoolean(false);
    private String currentUrl = "";
    private String currentCreationText = "";

    /* JADX INFO: renamed from: com.miui.handwriting.common.ai.viewmodel.AIViewModel2$getResult$1, reason: invalid class name and case insensitive filesystem */
    /* JADX INFO: compiled from: AIViewModel2.kt */
    @Metadata(k = 3, mv = {2, 2, 0}, xi = 48)
    @DebugMetadata(c = "com.miui.handwriting.common.ai.viewmodel.AIViewModel2", f = "AIViewModel2.kt", i = {0}, l = {R2.attr.guidePopupViewStyle}, m = "getResult", n = {XMLWriter.METHOD}, s = {"L$0"})
    static final class C02321 extends ContinuationImpl {
        Object L$0;
        int label;
        /* synthetic */ Object result;

        C02321(Continuation<? super C02321> continuation) {
            super(continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            this.result = obj;
            this.label |= Integer.MIN_VALUE;
            return AIViewModel2.this.getResult(null, this);
        }
    }

    public AIViewModel2() {
        String EMPTY_PIC = Constants.EMPTY_PIC;
        Intrinsics.checkNotNullExpressionValue(EMPTY_PIC, "EMPTY_PIC");
        String EMPTY_PIC2 = Constants.EMPTY_PIC;
        Intrinsics.checkNotNullExpressionValue(EMPTY_PIC2, "EMPTY_PIC");
        String EMPTY_PIC3 = Constants.EMPTY_PIC;
        Intrinsics.checkNotNullExpressionValue(EMPTY_PIC3, "EMPTY_PIC");
        String EMPTY_PIC4 = Constants.EMPTY_PIC;
        Intrinsics.checkNotNullExpressionValue(EMPTY_PIC4, "EMPTY_PIC");
        this.previewItemList = CollectionsKt.listOf((Object[]) new CreationImage[]{new CreationImage(EMPTY_PIC, null, null, null, null, null, null, null, null, null, R2.attr.endActionButtonStyle, null), new CreationImage(EMPTY_PIC2, null, null, null, null, null, null, null, null, null, R2.attr.endActionButtonStyle, null), new CreationImage(EMPTY_PIC3, null, null, null, null, null, null, null, null, null, R2.attr.endActionButtonStyle, null), new CreationImage(EMPTY_PIC4, null, null, null, null, null, null, null, null, null, R2.attr.endActionButtonStyle, null)});
        this._imageUrlLiveData = new MutableLiveData<>();
        this._themeLiveData = new MutableLiveData<>();
        this._referenceLiveData = new MutableLiveData<>();
        this._creationSmallImageLiveData = new SingleLiveData<>();
        this._errorCreation = new SingleLiveData<>();
        this._creationBigImageLiveData = new MutableLiveData<>();
        this._aiTheme = new MutableLiveData<>();
        this.creationRequestLiveData = new RequestStateLiveData<>();
        this.ballStateLiveData = new BallStateLiveData<>();
        this._requestReady = new MutableLiveData<>();
        this.creationMap = new ConcurrentHashMap<>();
    }

    public final String getPartialSelectQuitType() {
        return this.partialSelectQuitType;
    }

    public final void setPartialSelectQuitType(String str) {
        Intrinsics.checkNotNullParameter(str, "<set-?>");
        this.partialSelectQuitType = str;
    }

    public final int getCurrentBallState() {
        return this.currentBallState;
    }

    public final void setCurrentBallState(int i) {
        this.currentBallState = i;
    }

    public final long getRequestWay() {
        return this.requestWay;
    }

    public final void setRequestWay(long j) {
        this.requestWay = j;
    }

    /* JADX INFO: renamed from: isAICreateState, reason: from getter */
    public final int getIsAICreateState() {
        return this.isAICreateState;
    }

    public final void setAICreateState(int i) {
        this.isAICreateState = i;
    }

    public final boolean getAICreateState() {
        return this.aICreateState;
    }

    public final void setAICreateState(boolean z) {
        this.aICreateState = z;
    }

    /* JADX INFO: renamed from: isSelectPic, reason: from getter */
    public final boolean getIsSelectPic() {
        return this.isSelectPic;
    }

    public final void setSelectPic(boolean z) {
        this.isSelectPic = z;
    }

    public final String getAiFailReason() {
        return this.aiFailReason;
    }

    public final void setAiFailReason(String str) {
        Intrinsics.checkNotNullParameter(str, "<set-?>");
        this.aiFailReason = str;
    }

    public final int getAiFailCode() {
        return this.aiFailCode;
    }

    public final void setAiFailCode(int i) {
        this.aiFailCode = i;
    }

    public final int getAiStartTime() {
        return this.aiStartTime;
    }

    public final void setAiStartTime(int i) {
        this.aiStartTime = i;
    }

    public final int getAiEndTime() {
        return this.aiEndTime;
    }

    public final void setAiEndTime(int i) {
        this.aiEndTime = i;
    }

    /* JADX INFO: renamed from: isFirstTimeRequest, reason: from getter */
    public final boolean getIsFirstTimeRequest() {
        return this.isFirstTimeRequest;
    }

    public final void setFirstTimeRequest(boolean z) {
        this.isFirstTimeRequest = z;
    }

    public final CreationImage getRecreateImage() {
        return this.recreateImage;
    }

    public final void setRecreateImage(CreationImage creationImage) {
        this.recreateImage = creationImage;
    }

    public final float getTopTagX() {
        return this.topTagX;
    }

    public final void setTopTagX(float f) {
        this.topTagX = f;
    }

    public final float getMiddleTagX() {
        return this.middleTagX;
    }

    public final void setMiddleTagX(float f) {
        this.middleTagX = f;
    }

    public final int getCurrentSelectPosition() {
        return this.currentSelectPosition;
    }

    public final void setCurrentSelectPosition(int i) {
        this.currentSelectPosition = i;
    }

    public final int getEndWay() {
        return this.endWay;
    }

    public final void setEndWay(int i) {
        this.endWay = i;
    }

    public final float getScaleY() {
        return this.scaleY;
    }

    public final void setScaleY(float f) {
        this.scaleY = f;
    }

    public final float getScaleX() {
        return this.scaleX;
    }

    public final void setScaleX(float f) {
        this.scaleX = f;
    }

    public final float getClFooterViewMaskX() {
        return this.clFooterViewMaskX;
    }

    public final void setClFooterViewMaskX(float f) {
        this.clFooterViewMaskX = f;
    }

    public final CreationBean getThinkingType() {
        return this.thinkingType;
    }

    public final ArrayList<CreationBean> getListData() {
        return this.listData;
    }

    public final void setListData(ArrayList<CreationBean> arrayList) {
        Intrinsics.checkNotNullParameter(arrayList, "<set-?>");
        this.listData = arrayList;
    }

    public final CreationBean getCurrentSelectBean() {
        return this.currentSelectBean;
    }

    public final void setCurrentSelectBean(CreationBean creationBean) {
        this.currentSelectBean = creationBean;
    }

    public final String getCurrentDesc() {
        return this.currentDesc;
    }

    public final void setCurrentDesc(String str) {
        Intrinsics.checkNotNullParameter(str, "<set-?>");
        this.currentDesc = str;
    }

    public final ReferenceItem getCurrentItemReferce() {
        return this.currentItemReferce;
    }

    public final void setCurrentItemReferce(ReferenceItem referenceItem) {
        this.currentItemReferce = referenceItem;
    }

    public final ThemeItem getCurretThemeItem() {
        return this.curretThemeItem;
    }

    public final void setCurretThemeItem(ThemeItem themeItem) {
        this.curretThemeItem = themeItem;
    }

    public final String getCurrentPath() {
        return this.currentPath;
    }

    public final void setCurrentPath(String str) {
        Intrinsics.checkNotNullParameter(str, "<set-?>");
        this.currentPath = str;
    }

    public final String getCurrentPromote() {
        return this.currentPromote;
    }

    public final void setCurrentPromote(String str) {
        Intrinsics.checkNotNullParameter(str, "<set-?>");
        this.currentPromote = str;
    }

    public final String getResultUrl() {
        return this.resultUrl;
    }

    public final void setResultUrl(String str) {
        Intrinsics.checkNotNullParameter(str, "<set-?>");
        this.resultUrl = str;
    }

    public final Drawable getPlaceholder() {
        return this.placeholder;
    }

    public final void setPlaceholder(Drawable drawable) {
        this.placeholder = drawable;
    }

    /* JADX INFO: renamed from: isLoading, reason: from getter */
    public final AtomicBoolean getIsLoading() {
        return this.isLoading;
    }

    public final void setLoading(AtomicBoolean atomicBoolean) {
        Intrinsics.checkNotNullParameter(atomicBoolean, "<set-?>");
        this.isLoading = atomicBoolean;
    }

    /* JADX INFO: renamed from: isRequestLoading, reason: from getter */
    public final AtomicBoolean getIsRequestLoading() {
        return this.isRequestLoading;
    }

    public final void setRequestLoading(AtomicBoolean atomicBoolean) {
        Intrinsics.checkNotNullParameter(atomicBoolean, "<set-?>");
        this.isRequestLoading = atomicBoolean;
    }

    /* JADX INFO: renamed from: isButtonEnterIn, reason: from getter */
    public final boolean getIsButtonEnterIn() {
        return this.isButtonEnterIn;
    }

    public final void setButtonEnterIn(boolean z) {
        this.isButtonEnterIn = z;
    }

    public final String getCurrentUrl() {
        return this.currentUrl;
    }

    public final void setCurrentUrl(String str) {
        Intrinsics.checkNotNullParameter(str, "<set-?>");
        this.currentUrl = str;
    }

    public final ThemeData getCurrentThemeData() {
        return this.currentThemeData;
    }

    public final void setCurrentThemeData(ThemeData themeData) {
        this.currentThemeData = themeData;
    }

    public final ReferenceData getCurrentReferenceData() {
        return this.currentReferenceData;
    }

    public final void setCurrentReferenceData(ReferenceData referenceData) {
        this.currentReferenceData = referenceData;
    }

    /* JADX INFO: renamed from: isRecreate, reason: from getter */
    public final boolean getIsRecreate() {
        return this.isRecreate;
    }

    public final void setRecreate(boolean z) {
        this.isRecreate = z;
    }

    public final String getRecreateKey() {
        return this.recreateKey;
    }

    public final void setRecreateKey(String str) {
        this.recreateKey = str;
    }

    public final String getCurrentCreationText() {
        return this.currentCreationText;
    }

    public final void setCurrentCreationText(String str) {
        Intrinsics.checkNotNullParameter(str, "<set-?>");
        this.currentCreationText = str;
    }

    public final List<CreationImage> getPreviewItemList() {
        return this.previewItemList;
    }

    public final void setPreviewItemList(List<CreationImage> list) {
        Intrinsics.checkNotNullParameter(list, "<set-?>");
        this.previewItemList = list;
    }

    public final int getCurrentPage() {
        return this.currentPage;
    }

    public final void setCurrentPage(int i) {
        this.currentPage = i;
    }

    public final int getImageWidth() {
        return this.imageWidth;
    }

    public final void setImageWidth(int i) {
        this.imageWidth = i;
    }

    public final int getImageHeight() {
        return this.imageHeight;
    }

    public final void setImageHeight(int i) {
        this.imageHeight = i;
    }

    public final boolean getShowLoading() {
        return this.showLoading;
    }

    public final void setShowLoading(boolean z) {
        this.showLoading = z;
    }

    public final LiveData<String> getImageUrlLiveData() {
        return this._imageUrlLiveData;
    }

    public final void setImageUrl(String str) {
        Intrinsics.checkNotNullParameter(str, "str");
        this._imageUrlLiveData.setValue(str);
    }

    public final LiveData<ThemeData> getThemeLiveData() {
        return this._themeLiveData;
    }

    public final LiveData<ReferenceData> getReferenceLiveData() {
        return this._referenceLiveData;
    }

    public final SingleLiveData<CreationImage> getCreationSmallImageLiveData() {
        return this._creationSmallImageLiveData;
    }

    public final LiveData<Integer> getErrorCreation() {
        return this._errorCreation;
    }

    public final LiveData<CreationImage> getCreationBigImageLiveData() {
        return this._creationBigImageLiveData;
    }

    public final LiveData<ThemeItem> getAiTheme() {
        return this._aiTheme;
    }

    public final Job getUploadJob() {
        return this.uploadJob;
    }

    public final void setUploadJob(Job job) {
        this.uploadJob = job;
    }

    public final Job getRequestEnhanceJob() {
        return this.requestEnhanceJob;
    }

    public final void setRequestEnhanceJob(Job job) {
        this.requestEnhanceJob = job;
    }

    public final RequestStateLiveData<CreationImage> getCreationRequestLiveData() {
        return this.creationRequestLiveData;
    }

    public final BallStateLiveData<CreationImage> getBallStateLiveData() {
        return this.ballStateLiveData;
    }

    public final LiveData<Boolean> getRequestReady() {
        return this._requestReady;
    }

    /* JADX INFO: renamed from: isUseEdit, reason: from getter */
    public final boolean getIsUseEdit() {
        return this.isUseEdit;
    }

    public final void setUseEdit(boolean z) {
        this.isUseEdit = z;
    }

    /* JADX INFO: renamed from: com.miui.handwriting.common.ai.viewmodel.AIViewModel2$requestData$1, reason: invalid class name and case insensitive filesystem */
    /* JADX INFO: compiled from: AIViewModel2.kt */
    @Metadata(d1 = {"\u0000\n\n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\u0010\u0000\u001a\u00020\u0001*\u00020\u0002H\n"}, d2 = {"<anonymous>", "", "Lkotlinx/coroutines/CoroutineScope;"}, k = 3, mv = {2, 2, 0}, xi = 48)
    @DebugMetadata(c = "com.miui.handwriting.common.ai.viewmodel.AIViewModel2$requestData$1", f = "AIViewModel2.kt", i = {0}, l = {R2.array.support_ai_devices_special}, m = "invokeSuspend", n = {"$this$launch"}, s = {"L$0"})
    static final class C02331 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
        private /* synthetic */ Object L$0;
        int label;

        C02331(Continuation<? super C02331> continuation) {
            super(2, continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
            C02331 c02331 = AIViewModel2.this.new C02331(continuation);
            c02331.L$0 = obj;
            return c02331;
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(CoroutineScope coroutineScope, Continuation<? super Unit> continuation) {
            return ((C02331) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            CoroutineScope coroutineScope = (CoroutineScope) this.L$0;
            Object coroutine_suspended = IntrinsicsKt.getCOROUTINE_SUSPENDED();
            int i = this.label;
            if (i == 0) {
                ResultKt.throwOnFailure(obj);
                Deferred[] deferredArr = {BuildersKt__Builders_commonKt.async$default(coroutineScope, Dispatchers.getIO(), null, new C00591(AIViewModel2.this, null), 2, null), BuildersKt__Builders_commonKt.async$default(coroutineScope, Dispatchers.getIO(), null, new AnonymousClass2(AIViewModel2.this, null), 2, null)};
                this.L$0 = SpillingKt.nullOutSpilledVariable(coroutineScope);
                this.label = 1;
                if (AwaitKt.awaitAll(deferredArr, this) == coroutine_suspended) {
                    return coroutine_suspended;
                }
            } else {
                if (i != 1) {
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                }
                ResultKt.throwOnFailure(obj);
            }
            AIViewModel2.this._requestReady.postValue(Boxing.boxBoolean(true));
            return Unit.INSTANCE;
        }

        /* JADX INFO: renamed from: com.miui.handwriting.common.ai.viewmodel.AIViewModel2$requestData$1$1, reason: invalid class name and collision with other inner class name */
        /* JADX INFO: compiled from: AIViewModel2.kt */
        @Metadata(d1 = {"\u0000\n\n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\u0010\u0000\u001a\u00020\u0001*\u00020\u0002H\n"}, d2 = {"<anonymous>", "", "Lkotlinx/coroutines/CoroutineScope;"}, k = 3, mv = {2, 2, 0}, xi = 48)
        @DebugMetadata(c = "com.miui.handwriting.common.ai.viewmodel.AIViewModel2$requestData$1$1", f = "AIViewModel2.kt", i = {}, l = {}, m = "invokeSuspend", n = {}, s = {})
        static final class C00591 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
            int label;
            final /* synthetic */ AIViewModel2 this$0;

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            C00591(AIViewModel2 aIViewModel2, Continuation<? super C00591> continuation) {
                super(2, continuation);
                this.this$0 = aIViewModel2;
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
                return new C00591(this.this$0, continuation);
            }

            @Override // kotlin.jvm.functions.Function2
            public final Object invoke(CoroutineScope coroutineScope, Continuation<? super Unit> continuation) {
                return ((C00591) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Object invokeSuspend(Object obj) {
                IntrinsicsKt.getCOROUTINE_SUSPENDED();
                if (this.label != 0) {
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                }
                ResultKt.throwOnFailure(obj);
                this.this$0.requestListTheme();
                return Unit.INSTANCE;
            }
        }

        /* JADX INFO: renamed from: com.miui.handwriting.common.ai.viewmodel.AIViewModel2$requestData$1$2, reason: invalid class name */
        /* JADX INFO: compiled from: AIViewModel2.kt */
        @Metadata(d1 = {"\u0000\n\n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\u0010\u0000\u001a\u00020\u0001*\u00020\u0002H\n"}, d2 = {"<anonymous>", "", "Lkotlinx/coroutines/CoroutineScope;"}, k = 3, mv = {2, 2, 0}, xi = 48)
        @DebugMetadata(c = "com.miui.handwriting.common.ai.viewmodel.AIViewModel2$requestData$1$2", f = "AIViewModel2.kt", i = {}, l = {}, m = "invokeSuspend", n = {}, s = {})
        static final class AnonymousClass2 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
            int label;
            final /* synthetic */ AIViewModel2 this$0;

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            AnonymousClass2(AIViewModel2 aIViewModel2, Continuation<? super AnonymousClass2> continuation) {
                super(2, continuation);
                this.this$0 = aIViewModel2;
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
                return new AnonymousClass2(this.this$0, continuation);
            }

            @Override // kotlin.jvm.functions.Function2
            public final Object invoke(CoroutineScope coroutineScope, Continuation<? super Unit> continuation) {
                return ((AnonymousClass2) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Object invokeSuspend(Object obj) {
                IntrinsicsKt.getCOROUTINE_SUSPENDED();
                if (this.label != 0) {
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                }
                ResultKt.throwOnFailure(obj);
                this.this$0.requestReferenceItem();
                return Unit.INSTANCE;
            }
        }
    }

    public final void requestData() {
        BuildersKt__Builders_commonKt.launch$default(ViewModelKt.getViewModelScope(this), null, null, new C02331(null), 3, null);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void requestListTheme() {
        try {
            HashMap map = new HashMap();
            map.put("accept-language", Locale.getDefault().toString());
            Result result = (Result) new Gson().fromJson(CreationAIRequest2.Companion.get$default(CreationAIRequest2.INSTANCE, NetConfig.INSTANCE.getURL_THEME(), map, null, false, 8, null), new TypeToken<Result<ThemeData>>() { // from class: com.miui.handwriting.common.ai.viewmodel.AIViewModel2$requestListTheme$aiThemeBeanResult$1
            }.getType());
            Log.d(TAG, "listTheme:" + result);
            this._themeLiveData.postValue((ThemeData) result.getData());
        } catch (Exception e) {
            Log.e(TAG, "requestListTheme e:" + e);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void requestReferenceItem() {
        try {
            HashMap map = new HashMap();
            map.put("accept-language", Locale.getDefault().toString());
            Result result = (Result) new Gson().fromJson(CreationAIRequest2.Companion.get$default(CreationAIRequest2.INSTANCE, NetConfig.INSTANCE.getURL_GET_REFERENCE(), map, null, false, 8, null), new TypeToken<Result<ReferenceData>>() { // from class: com.miui.handwriting.common.ai.viewmodel.AIViewModel2$requestReferenceItem$aiReferenceResult$1
            }.getType());
            Log.d(TAG, "aiReferenceResult:" + result);
            this._referenceLiveData.postValue((ReferenceData) result.getData());
        } catch (Exception e) {
            Log.e(TAG, "requestReferenceItem e:" + e);
        }
    }

    public static /* synthetic */ void textCreation$default(AIViewModel2 aIViewModel2, String str, String str2, ThemeItem themeItem, ReferenceItem referenceItem, int i, Object obj) {
        if ((i & 4) != 0) {
            themeItem = null;
        }
        aIViewModel2.textCreation(str, str2, themeItem, referenceItem);
    }

    public final void textCreation(String mid, String prompt, ThemeItem theme, ReferenceItem referenceItem) {
        String string;
        Intrinsics.checkNotNullParameter(mid, "mid");
        String str = prompt;
        if (str == null || str.length() == 0) {
            this._errorCreation.postValue(-1);
            this.creationRequestLiveData.postError("text creation error:no prompt");
            this.ballStateLiveData.postError("text creation error:no prompt");
            return;
        }
        RecreateInfo recreateInfo = this.creationMap.get(prompt);
        Log.i(TAG, "textCreation:" + recreateInfo);
        if (recreateInfo == null) {
            BuildersKt__Builders_commonKt.launch$default(ViewModelKt.getViewModelScope(this), null, null, new C02362(mid, this, prompt, referenceItem, theme, null), 3, null);
            return;
        }
        if (referenceItem == null || (string = Integer.valueOf(referenceItem.getType()).toString()) == null) {
            string = CreationType.CREATION_TEXT;
        }
        recreateInfo.setCreationType(string);
        textCreationByRequestId(mid, recreateInfo, theme, prompt, referenceItem);
    }

    /* JADX INFO: renamed from: com.miui.handwriting.common.ai.viewmodel.AIViewModel2$textCreation$2, reason: invalid class name and case insensitive filesystem */
    /* JADX INFO: compiled from: AIViewModel2.kt */
    @Metadata(d1 = {"\u0000\n\n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\u0010\u0000\u001a\u00020\u0001*\u00020\u0002H\n"}, d2 = {"<anonymous>", "", "Lkotlinx/coroutines/CoroutineScope;"}, k = 3, mv = {2, 2, 0}, xi = 48)
    @DebugMetadata(c = "com.miui.handwriting.common.ai.viewmodel.AIViewModel2$textCreation$2", f = "AIViewModel2.kt", i = {}, l = {306}, m = "invokeSuspend", n = {}, s = {})
    static final class C02362 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
        final /* synthetic */ String $mid;
        final /* synthetic */ String $prompt;
        final /* synthetic */ ReferenceItem $referenceItem;
        final /* synthetic */ ThemeItem $theme;
        int label;
        final /* synthetic */ AIViewModel2 this$0;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        C02362(String str, AIViewModel2 aIViewModel2, String str2, ReferenceItem referenceItem, ThemeItem themeItem, Continuation<? super C02362> continuation) {
            super(2, continuation);
            this.$mid = str;
            this.this$0 = aIViewModel2;
            this.$prompt = str2;
            this.$referenceItem = referenceItem;
            this.$theme = themeItem;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
            return new C02362(this.$mid, this.this$0, this.$prompt, this.$referenceItem, this.$theme, continuation);
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(CoroutineScope coroutineScope, Continuation<? super Unit> continuation) {
            return ((C02362) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
        }

        /* JADX INFO: renamed from: com.miui.handwriting.common.ai.viewmodel.AIViewModel2$textCreation$2$1, reason: invalid class name */
        /* JADX INFO: compiled from: AIViewModel2.kt */
        @Metadata(d1 = {"\u0000\n\n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\u0010\u0000\u001a\u00020\u0001*\u00020\u0002H\n"}, d2 = {"<anonymous>", "", "Lkotlinx/coroutines/CoroutineScope;"}, k = 3, mv = {2, 2, 0}, xi = 48)
        @DebugMetadata(c = "com.miui.handwriting.common.ai.viewmodel.AIViewModel2$textCreation$2$1", f = "AIViewModel2.kt", i = {}, l = {}, m = "invokeSuspend", n = {}, s = {})
        static final class AnonymousClass1 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
            final /* synthetic */ String $mid;
            final /* synthetic */ String $prompt;
            final /* synthetic */ ReferenceItem $referenceItem;
            final /* synthetic */ ThemeItem $theme;
            int label;
            final /* synthetic */ AIViewModel2 this$0;

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            AnonymousClass1(String str, AIViewModel2 aIViewModel2, String str2, ReferenceItem referenceItem, ThemeItem themeItem, Continuation<? super AnonymousClass1> continuation) {
                super(2, continuation);
                this.$mid = str;
                this.this$0 = aIViewModel2;
                this.$prompt = str2;
                this.$referenceItem = referenceItem;
                this.$theme = themeItem;
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
                return new AnonymousClass1(this.$mid, this.this$0, this.$prompt, this.$referenceItem, this.$theme, continuation);
            }

            @Override // kotlin.jvm.functions.Function2
            public final Object invoke(CoroutineScope coroutineScope, Continuation<? super Unit> continuation) {
                return ((AnonymousClass1) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Object invokeSuspend(Object obj) {
                String string;
                IntrinsicsKt.getCOROUTINE_SUSPENDED();
                if (this.label != 0) {
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                }
                ResultKt.throwOnFailure(obj);
                try {
                    HashMap map = new HashMap();
                    map.put("miId", this.$mid);
                    map.put("oaid", DeviceUtils.getOAId());
                    map.put("timestamp", String.valueOf(System.currentTimeMillis()));
                    ArrayList arrayList = new ArrayList();
                    arrayList.add("AES-GCM-256");
                    map.put("data", new Gson().toJson(new ParamUploadMetaData(arrayList, new ArrayList(), null)));
                    Log.d(AIViewModel2.TAG, "textCreation requestUploadMeta param:" + map);
                    Object objFromJson = new Gson().fromJson(CreationAIRequest2.Companion.post$default(CreationAIRequest2.INSTANCE, NetConfig.INSTANCE.getURL_UPLOAD_META(), null, map, null, false, 24, null), new TypeToken<Result<UploadMetaData>>() { // from class: com.miui.handwriting.common.ai.viewmodel.AIViewModel2$textCreation$2$1$uploadMetaResult$1
                    }.getType());
                    Intrinsics.checkNotNullExpressionValue(objFromJson, "fromJson(...)");
                    Result result = (Result) objFromJson;
                    Log.d(AIViewModel2.TAG, "textCreation requestUploadMeta uploadMetaResult:" + result);
                    if (result.getCode() == 0) {
                        this.this$0.creationMap.clear();
                        if (this.$prompt.length() > 0) {
                            String str = this.$mid;
                            String requestId = ((UploadMetaData) result.getData()).getRequestId();
                            String key = ((UploadMetaData) result.getData()).getKey();
                            String iv = ((UploadMetaData) result.getData()).getOptions().getIv();
                            ReferenceItem referenceItem = this.$referenceItem;
                            if (referenceItem == null || (string = Boxing.boxInt(referenceItem.getType()).toString()) == null) {
                                string = CreationType.CREATION_TEXT;
                            }
                            RecreateInfo recreateInfo = new RecreateInfo(str, requestId, key, iv, string);
                            this.this$0.creationMap.put(this.$prompt, recreateInfo);
                            this.this$0.textCreationByRequestId(this.$mid, recreateInfo, this.$theme, this.$prompt, this.$referenceItem);
                        }
                    } else {
                        Log.e(AIViewModel2.TAG, "textCreation requestUploadMeta uploadMetaResult,code:" + result + ".code");
                        this.this$0._errorCreation.postValue(Boxing.boxInt(result.getCode()));
                        this.this$0.getCreationRequestLiveData().postError("textCreation requestUploadMeta fail fail");
                        this.this$0.getBallStateLiveData().postError("textCreation requestUploadMeta fail");
                    }
                } catch (Exception e) {
                    Log.e(AIViewModel2.TAG, "textCreation getImageRequestId requestUploadMeta e:" + e);
                    if (!(e instanceof CancellationException)) {
                        this.this$0._errorCreation.postValue(Boxing.boxInt(-1));
                        this.this$0.getCreationRequestLiveData().postError("textCreation error:" + e.getMessage());
                        this.this$0.getBallStateLiveData().postError("textCreation error:" + e.getMessage());
                    } else {
                        return Unit.INSTANCE;
                    }
                }
                return Unit.INSTANCE;
            }
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            Object coroutine_suspended = IntrinsicsKt.getCOROUTINE_SUSPENDED();
            int i = this.label;
            if (i == 0) {
                ResultKt.throwOnFailure(obj);
                this.label = 1;
                if (BuildersKt.withContext(Dispatchers.getIO(), new AnonymousClass1(this.$mid, this.this$0, this.$prompt, this.$referenceItem, this.$theme, null), this) == coroutine_suspended) {
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
    }

    static /* synthetic */ void textCreationByRequestId$default(AIViewModel2 aIViewModel2, String str, RecreateInfo recreateInfo, ThemeItem themeItem, String str2, ReferenceItem referenceItem, int i, Object obj) {
        if ((i & 4) != 0) {
            themeItem = null;
        }
        if ((i & 8) != 0) {
            str2 = null;
        }
        if ((i & 16) != 0) {
            referenceItem = null;
        }
        aIViewModel2.textCreationByRequestId(str, recreateInfo, themeItem, str2, referenceItem);
    }

    /* JADX INFO: renamed from: com.miui.handwriting.common.ai.viewmodel.AIViewModel2$textCreationByRequestId$1, reason: invalid class name and case insensitive filesystem */
    /* JADX INFO: compiled from: AIViewModel2.kt */
    @Metadata(d1 = {"\u0000\n\n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\u0010\u0000\u001a\u00020\u0001*\u00020\u0002H\n"}, d2 = {"<anonymous>", "", "Lkotlinx/coroutines/CoroutineScope;"}, k = 3, mv = {2, 2, 0}, xi = 48)
    @DebugMetadata(c = "com.miui.handwriting.common.ai.viewmodel.AIViewModel2$textCreationByRequestId$1", f = "AIViewModel2.kt", i = {0, 0, 0, 1, 1, 1, 1, 1, 1, 1, 1}, l = {402, 408}, m = "invokeSuspend", n = {"$this$launch", "creationList", "startRequestTime", "$this$launch", "creationList", "resultList", "startRequestTime", "endRequestTime", "successCount", "errorCode", "index"}, s = {"L$0", "L$1", "J$0", "L$0", "L$1", "L$2", "J$0", "J$1", "I$0", "I$1", "I$2"})
    static final class C02371 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
        final /* synthetic */ String $mid;
        final /* synthetic */ String $prompt;
        final /* synthetic */ RecreateInfo $recreateInfo;
        final /* synthetic */ ReferenceItem $referenceItem;
        final /* synthetic */ ThemeItem $theme;
        int I$0;
        int I$1;
        int I$2;
        int I$3;
        long J$0;
        long J$1;
        private /* synthetic */ Object L$0;
        Object L$1;
        Object L$2;
        int label;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        C02371(String str, ThemeItem themeItem, String str2, RecreateInfo recreateInfo, ReferenceItem referenceItem, Continuation<? super C02371> continuation) {
            super(2, continuation);
            this.$prompt = str;
            this.$theme = themeItem;
            this.$mid = str2;
            this.$recreateInfo = recreateInfo;
            this.$referenceItem = referenceItem;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
            C02371 c02371 = AIViewModel2.this.new C02371(this.$prompt, this.$theme, this.$mid, this.$recreateInfo, this.$referenceItem, continuation);
            c02371.L$0 = obj;
            return c02371;
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(CoroutineScope coroutineScope, Continuation<? super Unit> continuation) {
            return ((C02371) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
        }

        /* JADX WARN: Code duplicated, block: B:19:0x00e1  */
        /* JADX WARN: Code duplicated, block: B:22:0x0138  */
        /* JADX WARN: Code duplicated, block: B:25:0x0143  */
        /* JADX WARN: Code duplicated, block: B:27:0x0148  */
        /* JADX WARN: Code duplicated, block: B:28:0x0152  */
        /* JADX WARN: Code duplicated, block: B:45:? A[RETURN, SYNTHETIC] */
        /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:22:0x0138 -> B:23:0x013b). Please report as a decompilation issue!!! */
        /*  JADX ERROR: JadxOverflowException in pass: RegionMakerVisitor
            jadx.core.utils.exceptions.JadxOverflowException: Regions stack size limit reached
            	at jadx.core.utils.ErrorsCounter.addError(ErrorsCounter.java:59)
            	at jadx.core.utils.ErrorsCounter.error(ErrorsCounter.java:31)
            	at jadx.core.dex.attributes.nodes.NotificationAttrNode.addError(NotificationAttrNode.java:19)
            */
        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final java.lang.Object invokeSuspend(java.lang.Object r26) {
            /*
                Method dump skipped, instruction units count: 452
                To view this dump change 'Code comments level' option to 'DEBUG'
            */
            throw new UnsupportedOperationException("Method not decompiled: com.miui.handwriting.common.ai.viewmodel.AIViewModel2.C02371.invokeSuspend(java.lang.Object):java.lang.Object");
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void textCreationByRequestId(String mid, RecreateInfo recreateInfo, ThemeItem theme, String prompt, ReferenceItem referenceItem) {
        BuildersKt__Builders_commonKt.launch$default(ViewModelKt.getViewModelScope(this), null, null, new C02371(prompt, theme, mid, recreateInfo, referenceItem, null), 3, null);
    }

    public static /* synthetic */ void creationForAllImage$default(AIViewModel2 aIViewModel2, String str, String str2, ThemeItem themeItem, String str3, int i, Object obj) {
        if ((i & 4) != 0) {
            themeItem = null;
        }
        if ((i & 8) != 0) {
            str3 = null;
        }
        aIViewModel2.creationForAllImage(str, str2, themeItem, str3);
    }

    public final void creationForAllImage(String mid, String image, ThemeItem theme, String prompt) {
        Intrinsics.checkNotNullParameter(mid, "mid");
        Intrinsics.checkNotNullParameter(image, "image");
        RecreateInfo recreateInfo = this.creationMap.get(image);
        Log.i(TAG, "creationForAllImage:" + recreateInfo);
        if (recreateInfo != null) {
            CreationImage creationImage = new CreationImage(image, null, null, null, null, null, null, null, null, null, R2.attr.endActionButtonStyle, null);
            recreateInfo.setCreationType("1");
            creationImage.setThemeItem(theme);
            creationImage.setRecreateInfo(recreateInfo);
            recreationByImageUrl$default(this, mid, creationImage, theme, prompt, null, 16, null);
            return;
        }
        requestUploadMeta$default(this, mid, image, 3, theme, prompt, null, null, "1", 96, null);
    }

    public static /* synthetic */ void recreationByImageUrl$default(AIViewModel2 aIViewModel2, String str, CreationImage creationImage, ThemeItem themeItem, String str2, ReferenceItem referenceItem, int i, Object obj) {
        if ((i & 4) != 0) {
            themeItem = null;
        }
        if ((i & 8) != 0) {
            str2 = null;
        }
        if ((i & 16) != 0) {
            referenceItem = null;
        }
        aIViewModel2.recreationByImageUrl(str, creationImage, themeItem, str2, referenceItem);
    }

    /* JADX WARN: Code duplicated, block: B:21:0x0069 A[RETURN] */
    /* JADX WARN: Code duplicated, block: B:22:0x006a  */
    /* JADX WARN: Code duplicated, block: B:24:0x0078  */
    public final void recreationByImageUrl(String mid, CreationImage creationImage, ThemeItem theme, String prompt, ReferenceItem referenceItem) {
        AIViewModel2 aIViewModel2;
        Exception exc;
        Intrinsics.checkNotNullParameter(mid, "mid");
        Intrinsics.checkNotNullParameter(creationImage, "creationImage");
        Log.d(TAG, "start request recreationByImageUrl");
        this.creationRequestLiveData.postLoading();
        try {
            RecreateInfo recreateInfo = creationImage.getRecreateInfo();
            try {
                if (recreateInfo == null) {
                    this._errorCreation.postValue(-1);
                    this.creationRequestLiveData.postError("creation error,params error");
                    this.ballStateLiveData.postError("creation error,params error");
                    return;
                }
                if (referenceItem != null) {
                    recreateInfo.setCreationType(String.valueOf(referenceItem.getType()));
                }
                aIViewModel2 = this;
                try {
                    aIViewModel2.asyncRecreation(mid, creationImage, recreateInfo, theme, prompt, referenceItem);
                } catch (Exception e) {
                    e = e;
                    exc = e;
                    Log.e(TAG, "asyncRecreation e:" + exc);
                    if (exc instanceof CancellationException) {
                        return;
                    }
                    if (!Utils.isNetworkAvailable(HandWritingViewApplication.INSTANCE.getApplication())) {
                        Toast.makeText(HandWritingViewApplication.INSTANCE.getApplication(), HandWritingViewApplication.INSTANCE.getApplication().getString(R.string.creation_ai_network_error_message), 0).show();
                    }
                    aIViewModel2._errorCreation.postValue(-1);
                    aIViewModel2.creationRequestLiveData.postError("creation error:" + exc.getMessage());
                    aIViewModel2.ballStateLiveData.postError("creation error:" + exc.getMessage());
                }
            } catch (Exception e2) {
                exc = e2;
                aIViewModel2 = this;
                Log.e(TAG, "asyncRecreation e:" + exc);
                if (exc instanceof CancellationException) {
                    return;
                }
                if (!Utils.isNetworkAvailable(HandWritingViewApplication.INSTANCE.getApplication())) {
                    Toast.makeText(HandWritingViewApplication.INSTANCE.getApplication(), HandWritingViewApplication.INSTANCE.getApplication().getString(R.string.creation_ai_network_error_message), 0).show();
                }
                aIViewModel2._errorCreation.postValue(-1);
                aIViewModel2.creationRequestLiveData.postError("creation error:" + exc.getMessage());
                aIViewModel2.ballStateLiveData.postError("creation error:" + exc.getMessage());
            }
        } catch (Exception e3) {
            e = e3;
            aIViewModel2 = this;
        }
    }

    public static /* synthetic */ void creationByReference$default(AIViewModel2 aIViewModel2, String str, String str2, ReferenceItem referenceItem, ThemeItem themeItem, String str3, int i, Object obj) {
        if ((i & 8) != 0) {
            themeItem = null;
        }
        if ((i & 16) != 0) {
            str3 = null;
        }
        aIViewModel2.creationByReference(str, str2, referenceItem, themeItem, str3);
    }

    public final void creationByReference(String mid, String image, ReferenceItem referenceItem, ThemeItem theme, String prompt) {
        Intrinsics.checkNotNullParameter(mid, "mid");
        Intrinsics.checkNotNullParameter(image, "image");
        Intrinsics.checkNotNullParameter(referenceItem, "referenceItem");
        RecreateInfo recreateInfo = this.creationMap.get(image);
        Log.i(TAG, "creationByReference:" + recreateInfo);
        if (recreateInfo != null) {
            CreationImage creationImage = new CreationImage(image, null, null, null, null, null, null, null, null, null, R2.attr.endActionButtonStyle, null);
            recreateInfo.setCreationType(String.valueOf(referenceItem.getType()));
            creationImage.setThemeItem(theme);
            creationImage.setRecreateInfo(recreateInfo);
            recreationByImageUrl(mid, creationImage, theme, prompt, referenceItem);
            return;
        }
        requestUploadMeta$default(this, mid, image, 3, theme, prompt, referenceItem, null, String.valueOf(referenceItem.getType()), 64, null);
    }

    public static /* synthetic */ void creationPartRepaint$default(AIViewModel2 aIViewModel2, String str, String str2, String str3, ThemeItem themeItem, String str4, int i, Object obj) {
        if ((i & 8) != 0) {
            themeItem = null;
        }
        if ((i & 16) != 0) {
            str4 = null;
        }
        aIViewModel2.creationPartRepaint(str, str2, str3, themeItem, str4);
    }

    public final void creationPartRepaint(String mid, String image, String maskCoordinates, ThemeItem theme, String prompt) {
        Intrinsics.checkNotNullParameter(mid, "mid");
        Intrinsics.checkNotNullParameter(image, "image");
        Intrinsics.checkNotNullParameter(maskCoordinates, "maskCoordinates");
        requestUploadMeta$default(this, mid, image, 1, theme, prompt, null, StringsKt.trim((CharSequence) maskCoordinates).toString(), CreationType.CREATION_PART_REPAINT, 32, null);
    }

    public static /* synthetic */ void creationPartEliminate$default(AIViewModel2 aIViewModel2, String str, String str2, String str3, ThemeItem themeItem, String str4, int i, Object obj) {
        if ((i & 8) != 0) {
            themeItem = null;
        }
        if ((i & 16) != 0) {
            str4 = null;
        }
        aIViewModel2.creationPartEliminate(str, str2, str3, themeItem, str4);
    }

    public final void creationPartEliminate(String mid, String image, String maskCoordinates, ThemeItem theme, String prompt) {
        Intrinsics.checkNotNullParameter(mid, "mid");
        Intrinsics.checkNotNullParameter(image, "image");
        Intrinsics.checkNotNullParameter(maskCoordinates, "maskCoordinates");
        requestUploadMeta$default(this, mid, image, 1, theme, prompt, null, StringsKt.trim((CharSequence) maskCoordinates).toString(), CreationType.CREATION_PART_ELIMINATE, 32, null);
    }

    public final void cancelCreation() {
        Job job;
        try {
            Job job2 = this.uploadJob;
            if (job2 == null || !job2.isActive() || (job = this.uploadJob) == null) {
                return;
            }
            Job.DefaultImpls.cancel$default(job, (CancellationException) null, 1, (Object) null);
        } catch (Exception e) {
            Log.e(TAG, "cancelCreation fail ignore:" + e);
        }
    }

    static /* synthetic */ void requestUploadMeta$default(AIViewModel2 aIViewModel2, String str, String str2, int i, ThemeItem themeItem, String str3, ReferenceItem referenceItem, String str4, String str5, int i2, Object obj) {
        if ((i2 & 8) != 0) {
            themeItem = null;
        }
        if ((i2 & 16) != 0) {
            str3 = null;
        }
        if ((i2 & 32) != 0) {
            referenceItem = null;
        }
        if ((i2 & 64) != 0) {
            str4 = null;
        }
        if ((i2 & 128) != 0) {
            str5 = "1";
        }
        aIViewModel2.requestUploadMeta(str, str2, i, themeItem, str3, referenceItem, str4, str5);
    }

    private final void requestUploadMeta(String mid, String image, int requestTimes, ThemeItem theme, String prompt, ReferenceItem referenceItem, String maskCoordinates, String creationType) {
        Log.e(TAG, "requestUploadMeta: theme:" + theme + ",prompt:" + prompt + ",referenceItem:" + referenceItem + ",maskCoordinates:" + maskCoordinates + ",image:" + image + ",type:" + creationType);
        this.creationRequestLiveData.postLoading();
        this.uploadJob = BuildersKt__Builders_commonKt.launch$default(ViewModelKt.getViewModelScope(this), null, null, new C02351(mid, image, this, creationType, requestTimes, theme, prompt, referenceItem, maskCoordinates, null), 3, null);
    }

    /* JADX INFO: renamed from: com.miui.handwriting.common.ai.viewmodel.AIViewModel2$requestUploadMeta$1, reason: invalid class name and case insensitive filesystem */
    /* JADX INFO: compiled from: AIViewModel2.kt */
    @Metadata(d1 = {"\u0000\n\n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\u0010\u0000\u001a\u00020\u0001*\u00020\u0002H\n"}, d2 = {"<anonymous>", "", "Lkotlinx/coroutines/CoroutineScope;"}, k = 3, mv = {2, 2, 0}, xi = 48)
    @DebugMetadata(c = "com.miui.handwriting.common.ai.viewmodel.AIViewModel2$requestUploadMeta$1", f = "AIViewModel2.kt", i = {}, l = {R2.attr.choiceCategoryPreferenceStyle}, m = "invokeSuspend", n = {}, s = {})
    static final class C02351 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
        final /* synthetic */ String $creationType;
        final /* synthetic */ String $image;
        final /* synthetic */ String $maskCoordinates;
        final /* synthetic */ String $mid;
        final /* synthetic */ String $prompt;
        final /* synthetic */ ReferenceItem $referenceItem;
        final /* synthetic */ int $requestTimes;
        final /* synthetic */ ThemeItem $theme;
        int label;
        final /* synthetic */ AIViewModel2 this$0;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        C02351(String str, String str2, AIViewModel2 aIViewModel2, String str3, int i, ThemeItem themeItem, String str4, ReferenceItem referenceItem, String str5, Continuation<? super C02351> continuation) {
            super(2, continuation);
            this.$mid = str;
            this.$image = str2;
            this.this$0 = aIViewModel2;
            this.$creationType = str3;
            this.$requestTimes = i;
            this.$theme = themeItem;
            this.$prompt = str4;
            this.$referenceItem = referenceItem;
            this.$maskCoordinates = str5;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
            return new C02351(this.$mid, this.$image, this.this$0, this.$creationType, this.$requestTimes, this.$theme, this.$prompt, this.$referenceItem, this.$maskCoordinates, continuation);
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(CoroutineScope coroutineScope, Continuation<? super Unit> continuation) {
            return ((C02351) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
        }

        /* JADX INFO: renamed from: com.miui.handwriting.common.ai.viewmodel.AIViewModel2$requestUploadMeta$1$1, reason: invalid class name and collision with other inner class name */
        /* JADX INFO: compiled from: AIViewModel2.kt */
        @Metadata(d1 = {"\u0000\n\n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\u0010\u0000\u001a\u00020\u0001*\u00020\u0002H\n"}, d2 = {"<anonymous>", "", "Lkotlinx/coroutines/CoroutineScope;"}, k = 3, mv = {2, 2, 0}, xi = 48)
        @DebugMetadata(c = "com.miui.handwriting.common.ai.viewmodel.AIViewModel2$requestUploadMeta$1$1", f = "AIViewModel2.kt", i = {0, 0, 0, 0, 0, 0, 0, 0, 0, 1}, l = {R2.attr.collapsingToolbarLayoutStyle, R2.attr.colorOnPrimaryContainer}, m = "invokeSuspend", n = {"params", "algorithm", "options", "paramImageInfo", "fileSha256Digest", "file", "bodyData", ah.H, "uploadMetaResult", "e"}, s = {"L$0", "L$1", "L$2", "L$3", "L$4", "L$5", "L$6", "L$7", "L$8", "L$0"})
        static final class C00611 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
            final /* synthetic */ String $creationType;
            final /* synthetic */ String $image;
            final /* synthetic */ String $maskCoordinates;
            final /* synthetic */ String $mid;
            final /* synthetic */ String $prompt;
            final /* synthetic */ ReferenceItem $referenceItem;
            final /* synthetic */ int $requestTimes;
            final /* synthetic */ ThemeItem $theme;
            Object L$0;
            Object L$1;
            Object L$2;
            Object L$3;
            Object L$4;
            Object L$5;
            Object L$6;
            Object L$7;
            Object L$8;
            int label;
            final /* synthetic */ AIViewModel2 this$0;

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            C00611(String str, String str2, AIViewModel2 aIViewModel2, String str3, int i, ThemeItem themeItem, String str4, ReferenceItem referenceItem, String str5, Continuation<? super C00611> continuation) {
                super(2, continuation);
                this.$mid = str;
                this.$image = str2;
                this.this$0 = aIViewModel2;
                this.$creationType = str3;
                this.$requestTimes = i;
                this.$theme = themeItem;
                this.$prompt = str4;
                this.$referenceItem = referenceItem;
                this.$maskCoordinates = str5;
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
                return new C00611(this.$mid, this.$image, this.this$0, this.$creationType, this.$requestTimes, this.$theme, this.$prompt, this.$referenceItem, this.$maskCoordinates, continuation);
            }

            @Override // kotlin.jvm.functions.Function2
            public final Object invoke(CoroutineScope coroutineScope, Continuation<? super Unit> continuation) {
                return ((C00611) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
            }

            /* JADX WARN: Code restructure failed: missing block: B:26:0x029a, code lost:
            
                if (kotlinx.coroutines.BuildersKt.withContext(kotlinx.coroutines.Dispatchers.getMain(), new com.miui.handwriting.common.ai.viewmodel.AIViewModel2.C02351.C00611.C00621(r33.this$0, r0, null), r33) == r4) goto L27;
             */
            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            /*
                Code decompiled incorrectly, please refer to instructions dump.
                To view partially-correct code enable 'Show inconsistent code' option in preferences
            */
            public final java.lang.Object invokeSuspend(java.lang.Object r34) throws java.lang.Throwable {
                /*
                    Method dump skipped, instruction units count: 672
                    To view this dump change 'Code comments level' option to 'DEBUG'
                */
                throw new UnsupportedOperationException("Method not decompiled: com.miui.handwriting.common.ai.viewmodel.AIViewModel2.C02351.C00611.invokeSuspend(java.lang.Object):java.lang.Object");
            }

            /* JADX INFO: renamed from: com.miui.handwriting.common.ai.viewmodel.AIViewModel2$requestUploadMeta$1$1$1, reason: invalid class name and collision with other inner class name */
            /* JADX INFO: compiled from: AIViewModel2.kt */
            @Metadata(d1 = {"\u0000\n\n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\u0010\u0000\u001a\u00020\u0001*\u00020\u0002H\n"}, d2 = {"<anonymous>", "", "Lkotlinx/coroutines/CoroutineScope;"}, k = 3, mv = {2, 2, 0}, xi = 48)
            @DebugMetadata(c = "com.miui.handwriting.common.ai.viewmodel.AIViewModel2$requestUploadMeta$1$1$1", f = "AIViewModel2.kt", i = {}, l = {}, m = "invokeSuspend", n = {}, s = {})
            static final class C00621 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
                final /* synthetic */ Exception $e;
                int label;
                final /* synthetic */ AIViewModel2 this$0;

                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                C00621(AIViewModel2 aIViewModel2, Exception exc, Continuation<? super C00621> continuation) {
                    super(2, continuation);
                    this.this$0 = aIViewModel2;
                    this.$e = exc;
                }

                @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
                public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
                    return new C00621(this.this$0, this.$e, continuation);
                }

                @Override // kotlin.jvm.functions.Function2
                public final Object invoke(CoroutineScope coroutineScope, Continuation<? super Unit> continuation) {
                    return ((C00621) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
                }

                @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
                public final Object invokeSuspend(Object obj) {
                    IntrinsicsKt.getCOROUTINE_SUSPENDED();
                    if (this.label != 0) {
                        throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                    }
                    ResultKt.throwOnFailure(obj);
                    if (!Utils.isNetworkAvailable(HandWritingViewApplication.INSTANCE.getApplication())) {
                        Toast.makeText(HandWritingViewApplication.INSTANCE.getApplication(), HandWritingViewApplication.INSTANCE.getApplication().getString(R.string.creation_ai_network_error_message), 0).show();
                    }
                    this.this$0._errorCreation.postValue(Boxing.boxInt(-1));
                    this.this$0.getCreationRequestLiveData().postError("creation error:" + this.$e.getMessage());
                    this.this$0.getBallStateLiveData().postError("creation error:" + this.$e.getMessage());
                    return Unit.INSTANCE;
                }
            }
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            Object coroutine_suspended = IntrinsicsKt.getCOROUTINE_SUSPENDED();
            int i = this.label;
            if (i == 0) {
                ResultKt.throwOnFailure(obj);
                this.label = 1;
                if (BuildersKt.withContext(Dispatchers.getIO(), new C00611(this.$mid, this.$image, this.this$0, this.$creationType, this.$requestTimes, this.$theme, this.$prompt, this.$referenceItem, this.$maskCoordinates, null), this) == coroutine_suspended) {
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
    }

    @Override // androidx.lifecycle.ViewModel
    protected void onCleared() {
        super.onCleared();
        cancel();
    }

    public final void cancel() {
        Job job = this.uploadJob;
        if (job != null) {
            Job.DefaultImpls.cancel$default(job, (CancellationException) null, 1, (Object) null);
        }
        Job job2 = this.requestEnhanceJob;
        if (job2 != null) {
            Job.DefaultImpls.cancel$default(job2, (CancellationException) null, 1, (Object) null);
        }
        this.imageWidth = 0;
        this.imageHeight = 0;
        Request.INSTANCE.cancelTask(null);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final Object requestCreation(String str, String str2, String str3, String str4, String str5, Integer num, ThemeItem themeItem, String str6, ReferenceItem referenceItem, String str7, String str8, Continuation<? super String> continuation) throws Exception {
        String path;
        byte[] imageByteArray = Utils.getImageByteArray(str2);
        Log.d(TAG, "imageByte size:" + imageByteArray.length + ' ');
        byte[] bArrDecode = Base64.decode(str3, 0);
        SecretKey secretKeyLoadKey = AESUtil.loadKey(bArrDecode);
        StringBuilder sbAppend = new StringBuilder("serverKey :").append(str3).append("  decode:");
        Intrinsics.checkNotNull(bArrDecode);
        Log.d(TAG, sbAppend.append(ArraysKt.asList(bArrDecode)).toString());
        byte[] bArrDecode2 = Base64.decode(str4, 0);
        GCMParameterSpec gCMParameterSpecLoadGCMParameterSpec = AESUtil.loadGCMParameterSpec(bArrDecode2);
        StringBuilder sbAppend2 = new StringBuilder("iv:").append(str4).append("  toByte:");
        Intrinsics.checkNotNull(bArrDecode2);
        Log.d(TAG, sbAppend2.append(ArraysKt.asList(bArrDecode2)).toString());
        byte[] bArrEncrypt = AESUtil.encrypt(imageByteArray, secretKeyLoadKey, gCMParameterSpecLoadGCMParameterSpec);
        HashMap map = new HashMap();
        map.put("miId", str);
        map.put("oaid", DeviceUtils.getOAId());
        map.put("timestamp", String.valueOf(System.currentTimeMillis()));
        map.put("requestId", str5);
        map.put("type", str8.toString());
        map.put(NetConfig.PARAM_CREATION_NUM, String.valueOf(num));
        if (themeItem != null) {
            map.put("theme", themeItem.getTheme());
        }
        String str9 = str6;
        if (str9 != null && str9.length() != 0) {
            map.put("prompt", str6);
        }
        if (str7 != null) {
        }
        File fileSaveFile = Utils.saveFile(bArrEncrypt);
        Intrinsics.checkNotNullExpressionValue(fileSaveFile, "saveFile(...)");
        HashMap map2 = new HashMap();
        map2.put("file", fileSaveFile.getPath());
        if (referenceItem != null && (path = referenceItem.getPath()) != null) {
            byte[] imageByteArray2 = Utils.getImageByteArray(path);
            Log.d(TAG, "guildByte size:" + imageByteArray2.length + ' ');
            byte[] bArrDecode3 = Base64.decode(str3, 0);
            SecretKey secretKeyLoadKey2 = AESUtil.loadKey(bArrDecode3);
            StringBuilder sbAppend3 = new StringBuilder("serverKey :").append(str3).append("  decode:");
            Intrinsics.checkNotNull(bArrDecode3);
            Log.d(TAG, sbAppend3.append(ArraysKt.asList(bArrDecode3)).toString());
            byte[] bArrDecode4 = Base64.decode(str4, 0);
            GCMParameterSpec gCMParameterSpecLoadGCMParameterSpec2 = AESUtil.loadGCMParameterSpec(bArrDecode4);
            StringBuilder sbAppend4 = new StringBuilder("iv:").append(str4).append("  toByte:");
            Intrinsics.checkNotNull(bArrDecode4);
            Log.d(TAG, sbAppend4.append(ArraysKt.asList(bArrDecode4)).toString());
            map2.put(NetConfig.PARAM_GUILD_FILE, Utils.saveFile(AESUtil.encrypt(imageByteArray2, secretKeyLoadKey2, gCMParameterSpecLoadGCMParameterSpec2)).getPath());
        }
        Log.d(TAG, "multipartBody=={mid:" + str + ",oid:" + DeviceUtils.getOAId() + ",theme:" + themeItem + ",prompt:" + str6 + ",requestId:" + str5 + ",fileName:" + fileSaveFile.getName() + ",type:" + str8 + CoreConstants.CURLY_RIGHT);
        Log.e(TAG, "multipartBody==request:" + str5);
        return CreationAIRequest2.Companion.upload$default(CreationAIRequest2.INSTANCE, NetConfig.INSTANCE.getURL_CREATION(), map, map2, false, 8, null);
    }

    static /* synthetic */ Object asyncCreation$default(AIViewModel2 aIViewModel2, String str, String str2, String str3, String str4, String str5, int i, ThemeItem themeItem, String str6, ReferenceItem referenceItem, String str7, String str8, Continuation continuation, int i2, Object obj) {
        if ((i2 & 64) != 0) {
            themeItem = null;
        }
        if ((i2 & 128) != 0) {
            str6 = null;
        }
        if ((i2 & 256) != 0) {
            referenceItem = null;
        }
        if ((i2 & 512) != 0) {
            str7 = null;
        }
        if ((i2 & 1024) != 0) {
            str8 = "1";
        }
        return aIViewModel2.asyncCreation(str, str2, str3, str4, str5, i, themeItem, str6, referenceItem, str7, str8, continuation);
    }

    /* JADX INFO: renamed from: com.miui.handwriting.common.ai.viewmodel.AIViewModel2$asyncCreation$2, reason: invalid class name */
    /* JADX INFO: compiled from: AIViewModel2.kt */
    @Metadata(d1 = {"\u0000\n\n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\u0010\u0000\u001a\u00020\u0001*\u00020\u0002H\n"}, d2 = {"<anonymous>", "", "Lkotlinx/coroutines/CoroutineScope;"}, k = 3, mv = {2, 2, 0}, xi = 48)
    @DebugMetadata(c = "com.miui.handwriting.common.ai.viewmodel.AIViewModel2$asyncCreation$2", f = "AIViewModel2.kt", i = {0, 0, 0, 1, 1, 1, 1, 1, 1, 1, 1}, l = {R2.attr.dividerDrawable, R2.attr.dividerPadding}, m = "invokeSuspend", n = {"$this$launch", "creationList", "startRequestTime", "$this$launch", "creationList", "resultList", "startRequestTime", "endRequestTime", "successCount", "errorCode", "index"}, s = {"L$0", "L$1", "J$0", "L$0", "L$1", "L$2", "J$0", "J$1", "I$0", "I$1", "I$2"})
    static final class AnonymousClass2 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
        final /* synthetic */ String $creationType;
        final /* synthetic */ String $image;
        final /* synthetic */ String $iv;
        final /* synthetic */ String $maskCoordinates;
        final /* synthetic */ String $mid;
        final /* synthetic */ String $prompt;
        final /* synthetic */ ReferenceItem $referenceItem;
        final /* synthetic */ String $requestId;
        final /* synthetic */ int $requestTimes;
        final /* synthetic */ String $serverKey;
        final /* synthetic */ ThemeItem $theme;
        int I$0;
        int I$1;
        int I$2;
        int I$3;
        long J$0;
        long J$1;
        private /* synthetic */ Object L$0;
        Object L$1;
        Object L$2;
        int label;
        final /* synthetic */ AIViewModel2 this$0;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        AnonymousClass2(int i, AIViewModel2 aIViewModel2, String str, ThemeItem themeItem, ReferenceItem referenceItem, String str2, String str3, String str4, String str5, String str6, String str7, String str8, Continuation<? super AnonymousClass2> continuation) {
            super(2, continuation);
            this.$requestTimes = i;
            this.this$0 = aIViewModel2;
            this.$prompt = str;
            this.$theme = themeItem;
            this.$referenceItem = referenceItem;
            this.$creationType = str2;
            this.$mid = str3;
            this.$image = str4;
            this.$serverKey = str5;
            this.$iv = str6;
            this.$requestId = str7;
            this.$maskCoordinates = str8;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
            AnonymousClass2 anonymousClass2 = new AnonymousClass2(this.$requestTimes, this.this$0, this.$prompt, this.$theme, this.$referenceItem, this.$creationType, this.$mid, this.$image, this.$serverKey, this.$iv, this.$requestId, this.$maskCoordinates, continuation);
            anonymousClass2.L$0 = obj;
            return anonymousClass2;
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(CoroutineScope coroutineScope, Continuation<? super Unit> continuation) {
            return ((AnonymousClass2) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
        }

        /* JADX WARN: Code duplicated, block: B:19:0x0102  */
        /* JADX WARN: Code duplicated, block: B:22:0x016b  */
        /* JADX WARN: Code duplicated, block: B:25:0x017f  */
        /* JADX WARN: Code duplicated, block: B:27:0x0184  */
        /* JADX WARN: Code duplicated, block: B:29:0x0190  */
        /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:22:0x016b -> B:23:0x0177). Please report as a decompilation issue!!! */
        /*  JADX ERROR: JadxOverflowException in pass: RegionMakerVisitor
            jadx.core.utils.exceptions.JadxOverflowException: Regions stack size limit reached
            	at jadx.core.utils.ErrorsCounter.addError(ErrorsCounter.java:59)
            	at jadx.core.utils.ErrorsCounter.error(ErrorsCounter.java:31)
            	at jadx.core.dex.attributes.nodes.NotificationAttrNode.addError(NotificationAttrNode.java:19)
            */
        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final java.lang.Object invokeSuspend(java.lang.Object r35) {
            /*
                Method dump skipped, instruction units count: 558
                To view this dump change 'Code comments level' option to 'DEBUG'
            */
            throw new UnsupportedOperationException("Method not decompiled: com.miui.handwriting.common.ai.viewmodel.AIViewModel2.AnonymousClass2.invokeSuspend(java.lang.Object):java.lang.Object");
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final Object asyncCreation(String str, String str2, String str3, String str4, String str5, int i, ThemeItem themeItem, String str6, ReferenceItem referenceItem, String str7, String str8, Continuation<? super Unit> continuation) {
        BuildersKt__Builders_commonKt.launch$default(ViewModelKt.getViewModelScope(this), null, null, new AnonymousClass2(i, this, str6, themeItem, referenceItem, str8, str, str2, str3, str4, str5, str7, null), 3, null);
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Code duplicated, block: B:30:0x0094  */
    /* JADX WARN: Code duplicated, block: B:34:0x00bd  */
    public final void postSmallImageResult(Result<CreationData> result, ReferenceItem referenceItem, ThemeItem theme, RecreateInfo recreateInfo, String prompt) {
        String uri;
        if (result == null || result.getCode() != 0 || result.getData().getResult().isEmpty()) {
            Log.e(TAG, "requestCreation fail:" + result);
            throw new CreationException(result != null ? result.getCode() : -1, "image create error");
        }
        CreationImage creationImage = result.getData().getResult().get(0);
        creationImage.setReferenceItem(referenceItem);
        creationImage.setPrompt(prompt);
        if (theme != null) {
            creationImage.setThemeItem(theme);
        } else if (creationImage.getTheme() != null && creationImage.getThemeId() != null && creationImage.getIcon() != null) {
            String theme2 = creationImage.getTheme();
            Intrinsics.checkNotNull(theme2);
            Integer themeId = creationImage.getThemeId();
            Intrinsics.checkNotNull(themeId);
            int iIntValue = themeId.intValue();
            String icon = creationImage.getIcon();
            Intrinsics.checkNotNull(icon);
            creationImage.setThemeItem(new ThemeItem(theme2, iIntValue, icon, false));
        }
        creationImage.setRecreateInfo(recreateInfo);
        if (!this.isUseEdit) {
            ThemeItem themeItem = creationImage.getThemeItem();
            Integer numValueOf = themeItem != null ? Integer.valueOf(themeItem.getThemeId()) : null;
            if (referenceItem == null || (uri = referenceItem.getUri()) == null) {
                uri = "";
            }
            if (!isSameRequestParam(prompt, numValueOf, uri)) {
                if (!istPart(recreateInfo.getCreationType())) {
                    Log.e(TAG, "is user in edit or select other");
                    throw new CreationException(-2, "is user in edit or select other");
                }
            }
        } else if (!istPart(recreateInfo.getCreationType())) {
            Log.e(TAG, "is user in edit or select other");
            throw new CreationException(-2, "is user in edit or select other");
        }
        Log.e(TAG, "is user not in edit");
        this._creationSmallImageLiveData.postValue(creationImage);
        requestEnhance(recreateInfo.getMId(), creationImage.getImageUrl(), creationImage.getThemeItem(), creationImage.getPrompt(), creationImage);
    }

    /* JADX INFO: renamed from: com.miui.handwriting.common.ai.viewmodel.AIViewModel2$requestEnhance$1, reason: invalid class name and case insensitive filesystem */
    /* JADX INFO: compiled from: AIViewModel2.kt */
    @Metadata(d1 = {"\u0000\n\n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\u0010\u0000\u001a\u00020\u0001*\u00020\u0002H\n"}, d2 = {"<anonymous>", "", "Lkotlinx/coroutines/CoroutineScope;"}, k = 3, mv = {2, 2, 0}, xi = 48)
    @DebugMetadata(c = "com.miui.handwriting.common.ai.viewmodel.AIViewModel2$requestEnhance$1", f = "AIViewModel2.kt", i = {}, l = {}, m = "invokeSuspend", n = {}, s = {})
    static final class C02341 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
        final /* synthetic */ CreationImage $creationImage;
        final /* synthetic */ String $imageUrl;
        final /* synthetic */ String $mid;
        final /* synthetic */ String $prompt;
        final /* synthetic */ ThemeItem $theme;
        int label;
        final /* synthetic */ AIViewModel2 this$0;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        C02341(String str, String str2, CreationImage creationImage, AIViewModel2 aIViewModel2, ThemeItem themeItem, String str3, Continuation<? super C02341> continuation) {
            super(2, continuation);
            this.$mid = str;
            this.$imageUrl = str2;
            this.$creationImage = creationImage;
            this.this$0 = aIViewModel2;
            this.$theme = themeItem;
            this.$prompt = str3;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
            return new C02341(this.$mid, this.$imageUrl, this.$creationImage, this.this$0, this.$theme, this.$prompt, continuation);
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(CoroutineScope coroutineScope, Continuation<? super Unit> continuation) {
            return ((C02341) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            IntrinsicsKt.getCOROUTINE_SUSPENDED();
            if (this.label != 0) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            ResultKt.throwOnFailure(obj);
            try {
                final HashMap map = new HashMap();
                map.put("miId", this.$mid);
                map.put("oaid", DeviceUtils.getOAId());
                map.put("timestamp", String.valueOf(System.currentTimeMillis()));
                RequestBuilder<Bitmap> requestBuilderLoad = Glide.with(HandWritingViewApplication.INSTANCE.getApplication()).asBitmap().load(this.$imageUrl);
                final AIViewModel2 aIViewModel2 = this.this$0;
                final ThemeItem themeItem = this.$theme;
                final String str = this.$prompt;
                final CreationImage creationImage = this.$creationImage;
                Intrinsics.checkNotNull(requestBuilderLoad.into(new CustomTarget<Bitmap>() { // from class: com.miui.handwriting.common.ai.viewmodel.AIViewModel2.requestEnhance.1.1
                    @Override // com.bumptech.glide.request.target.Target
                    public void onLoadCleared(Drawable placeholder) {
                    }

                    @Override // com.bumptech.glide.request.target.Target
                    public /* bridge */ /* synthetic */ void onResourceReady(Object obj2, Transition transition) {
                        onResourceReady((Bitmap) obj2, (Transition<? super Bitmap>) transition);
                    }

                    public void onResourceReady(Bitmap resource, Transition<? super Bitmap> transition) {
                        Intrinsics.checkNotNullParameter(resource, "resource");
                        BuildersKt__Builders_commonKt.launch$default(ViewModelKt.getViewModelScope(aIViewModel2), Dispatchers.getIO(), null, new AIViewModel2$requestEnhance$1$1$onResourceReady$1(aIViewModel2, resource, themeItem, str, map, creationImage, null), 2, null);
                    }
                }));
            } catch (Exception e) {
                Log.e(AIViewModel2.TAG, "requestEnhance fail e:" + e);
                this.$creationImage.setBigImage(Boxing.boxBoolean(false));
                if (!this.this$0.getIsUseEdit()) {
                    Log.e(AIViewModel2.TAG, "requestEnhance is user not in edit");
                    this.this$0._creationBigImageLiveData.postValue(this.$creationImage);
                }
            }
            return Unit.INSTANCE;
        }
    }

    private final void requestEnhance(String mid, String imageUrl, ThemeItem theme, String prompt, CreationImage creationImage) {
        this.requestEnhanceJob = BuildersKt__Builders_commonKt.launch$default(ViewModelKt.getViewModelScope(this), Dispatchers.getIO(), null, new C02341(mid, imageUrl, creationImage, this, theme, prompt, null), 2, null);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final Object reCreation(String str, String str2, String str3, String str4, ThemeItem themeItem, String str5, ReferenceItem referenceItem, String str6, Continuation<? super String> continuation) {
        String path;
        Log.e(TAG, "reCreation: theme:" + themeItem + ",prompt:" + str5 + ",referenceItem:" + referenceItem + ",creationType:" + str6);
        HashMap map = new HashMap();
        map.put("miId", str);
        map.put("oaid", DeviceUtils.getOAId());
        map.put("timestamp", String.valueOf(System.currentTimeMillis()));
        map.put("requestId", str2);
        map.put("type", str6);
        map.put(NetConfig.PARAM_CREATION_NUM, "3");
        if (themeItem != null) {
            themeItem.getTheme();
            map.put("theme", themeItem.getTheme());
        }
        String str7 = str5;
        if (str7 != null && str7.length() != 0) {
            map.put("prompt", str5);
        }
        HashMap map2 = new HashMap();
        if (referenceItem != null && (path = referenceItem.getPath()) != null) {
            byte[] imageByteArray = Utils.getImageByteArray(path);
            Log.d(TAG, "guildByte size:" + imageByteArray.length + ' ');
            byte[] bArrDecode = Base64.decode(str3, 0);
            SecretKey secretKeyLoadKey = AESUtil.loadKey(bArrDecode);
            StringBuilder sbAppend = new StringBuilder("serverKey :").append(str3).append("  decode:");
            Intrinsics.checkNotNull(bArrDecode);
            Log.d(TAG, sbAppend.append(ArraysKt.asList(bArrDecode)).toString());
            byte[] bArrDecode2 = Base64.decode(str4, 0);
            GCMParameterSpec gCMParameterSpecLoadGCMParameterSpec = AESUtil.loadGCMParameterSpec(bArrDecode2);
            StringBuilder sbAppend2 = new StringBuilder("iv:").append(str4).append("  toByte:");
            Intrinsics.checkNotNull(bArrDecode2);
            Log.d(TAG, sbAppend2.append(ArraysKt.asList(bArrDecode2)).toString());
            map2.put(NetConfig.PARAM_GUILD_FILE, Utils.saveFile(AESUtil.encrypt(imageByteArray, secretKeyLoadKey, gCMParameterSpecLoadGCMParameterSpec)).getPath());
        }
        Log.d(TAG, "multipartBody=={mid:" + str + ",oid:" + DeviceUtils.getOAId() + ",type:" + str6 + CoreConstants.CURLY_RIGHT);
        Log.e(TAG, "multipartBody request:" + str2);
        return CreationAIRequest2.Companion.upload$default(CreationAIRequest2.INSTANCE, NetConfig.INSTANCE.getURL_CREATION(), map, map2, false, 8, null);
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Code duplicated, block: B:7:0x0014  */
    public final Object getResult(Function1<? super Continuation<? super Unit>, ? extends Object> function1, Continuation<? super Integer> continuation) {
        C02321 c02321;
        if (continuation instanceof C02321) {
            c02321 = (C02321) continuation;
            if ((c02321.label & Integer.MIN_VALUE) != 0) {
                c02321.label -= Integer.MIN_VALUE;
            } else {
                c02321 = new C02321(continuation);
            }
        } else {
            c02321 = new C02321(continuation);
        }
        Object obj = c02321.result;
        Object coroutine_suspended = IntrinsicsKt.getCOROUTINE_SUSPENDED();
        int i = c02321.label;
        try {
            if (i == 0) {
                ResultKt.throwOnFailure(obj);
                c02321.L$0 = SpillingKt.nullOutSpilledVariable(function1);
                c02321.label = 1;
                if (function1.invoke(c02321) == coroutine_suspended) {
                    return coroutine_suspended;
                }
            } else {
                if (i != 1) {
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                }
                ResultKt.throwOnFailure(obj);
            }
            return Boxing.boxInt(0);
        } catch (CreationException e) {
            Log.e(TAG, "get  creation result fail:" + e.getMessage());
            return Boxing.boxInt(e.getCode());
        } catch (Exception e2) {
            Log.e(TAG, "get result fail:" + e2.getMessage());
            return Boxing.boxInt(-1);
        }
    }

    static /* synthetic */ void asyncRecreation$default(AIViewModel2 aIViewModel2, String str, CreationImage creationImage, RecreateInfo recreateInfo, ThemeItem themeItem, String str2, ReferenceItem referenceItem, int i, Object obj) {
        if ((i & 8) != 0) {
            themeItem = null;
        }
        if ((i & 16) != 0) {
            str2 = null;
        }
        if ((i & 32) != 0) {
            referenceItem = null;
        }
        aIViewModel2.asyncRecreation(str, creationImage, recreateInfo, themeItem, str2, referenceItem);
    }

    /* JADX INFO: renamed from: com.miui.handwriting.common.ai.viewmodel.AIViewModel2$asyncRecreation$1, reason: invalid class name */
    /* JADX INFO: compiled from: AIViewModel2.kt */
    @Metadata(d1 = {"\u0000\n\n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\u0010\u0000\u001a\u00020\u0001*\u00020\u0002H\n"}, d2 = {"<anonymous>", "", "Lkotlinx/coroutines/CoroutineScope;"}, k = 3, mv = {2, 2, 0}, xi = 48)
    @DebugMetadata(c = "com.miui.handwriting.common.ai.viewmodel.AIViewModel2$asyncRecreation$1", f = "AIViewModel2.kt", i = {0, 0, 0, 1, 1, 1, 1, 1, 1, 1, 1}, l = {R2.attr.hyperMenuActionColor, R2.attr.hyperSplitMenuEnabled}, m = "invokeSuspend", n = {"$this$launch", "creationList", "startRequestTime", "$this$launch", "creationList", "resultList", "startRequestTime", "endRequestTime", "successCount", "errorCode", "index"}, s = {"L$0", "L$1", "J$0", "L$0", "L$1", "L$2", "J$0", "J$1", "I$0", "I$1", "I$2"})
    static final class AnonymousClass1 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
        final /* synthetic */ CreationImage $creationImage;
        final /* synthetic */ String $mid;
        final /* synthetic */ String $prompt;
        final /* synthetic */ RecreateInfo $recreateInfo;
        final /* synthetic */ ReferenceItem $referenceItem;
        final /* synthetic */ ThemeItem $theme;
        int I$0;
        int I$1;
        int I$2;
        int I$3;
        long J$0;
        long J$1;
        private /* synthetic */ Object L$0;
        Object L$1;
        Object L$2;
        int label;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        AnonymousClass1(String str, ThemeItem themeItem, ReferenceItem referenceItem, RecreateInfo recreateInfo, String str2, CreationImage creationImage, Continuation<? super AnonymousClass1> continuation) {
            super(2, continuation);
            this.$prompt = str;
            this.$theme = themeItem;
            this.$referenceItem = referenceItem;
            this.$recreateInfo = recreateInfo;
            this.$mid = str2;
            this.$creationImage = creationImage;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
            AnonymousClass1 anonymousClass1 = AIViewModel2.this.new AnonymousClass1(this.$prompt, this.$theme, this.$referenceItem, this.$recreateInfo, this.$mid, this.$creationImage, continuation);
            anonymousClass1.L$0 = obj;
            return anonymousClass1;
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(CoroutineScope coroutineScope, Continuation<? super Unit> continuation) {
            return ((AnonymousClass1) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
        }

        /* JADX WARN: Code duplicated, block: B:19:0x00f2  */
        /* JADX WARN: Code duplicated, block: B:22:0x014e  */
        /* JADX WARN: Code duplicated, block: B:25:0x015a  */
        /* JADX WARN: Code duplicated, block: B:27:0x015f  */
        /* JADX WARN: Code duplicated, block: B:28:0x0169  */
        /* JADX WARN: Code duplicated, block: B:52:? A[RETURN, SYNTHETIC] */
        /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:22:0x014e -> B:23:0x0152). Please report as a decompilation issue!!! */
        /*  JADX ERROR: JadxOverflowException in pass: RegionMakerVisitor
            jadx.core.utils.exceptions.JadxOverflowException: Regions stack size limit reached
            	at jadx.core.utils.ErrorsCounter.addError(ErrorsCounter.java:59)
            	at jadx.core.utils.ErrorsCounter.error(ErrorsCounter.java:31)
            	at jadx.core.dex.attributes.nodes.NotificationAttrNode.addError(NotificationAttrNode.java:19)
            */
        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final java.lang.Object invokeSuspend(java.lang.Object r30) {
            /*
                Method dump skipped, instruction units count: 521
                To view this dump change 'Code comments level' option to 'DEBUG'
            */
            throw new UnsupportedOperationException("Method not decompiled: com.miui.handwriting.common.ai.viewmodel.AIViewModel2.AnonymousClass1.invokeSuspend(java.lang.Object):java.lang.Object");
        }
    }

    private final void asyncRecreation(String mid, CreationImage creationImage, RecreateInfo recreateInfo, ThemeItem theme, String prompt, ReferenceItem referenceItem) {
        BuildersKt__Builders_commonKt.launch$default(ViewModelKt.getViewModelScope(this), null, null, new AnonymousClass1(prompt, theme, referenceItem, recreateInfo, mid, creationImage, null), 3, null);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void getImageTheme(String image, String requestId, String mid) {
        String str = image;
        if (str == null || str.length() == 0) {
            return;
        }
        BuildersKt__Builders_commonKt.launch$default(ViewModelKt.getViewModelScope(this), null, null, new C02301(requestId, mid, this, image, null), 3, null);
    }

    /* JADX INFO: renamed from: com.miui.handwriting.common.ai.viewmodel.AIViewModel2$getImageTheme$1, reason: invalid class name and case insensitive filesystem */
    /* JADX INFO: compiled from: AIViewModel2.kt */
    @Metadata(d1 = {"\u0000\n\n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\u0010\u0000\u001a\u00020\u0001*\u00020\u0002H\n"}, d2 = {"<anonymous>", "", "Lkotlinx/coroutines/CoroutineScope;"}, k = 3, mv = {2, 2, 0}, xi = 48)
    @DebugMetadata(c = "com.miui.handwriting.common.ai.viewmodel.AIViewModel2$getImageTheme$1", f = "AIViewModel2.kt", i = {}, l = {R2.attr.isNavigatorSecondaryContentTheme}, m = "invokeSuspend", n = {}, s = {})
    static final class C02301 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
        final /* synthetic */ String $image;
        final /* synthetic */ String $mid;
        final /* synthetic */ String $requestId;
        int label;
        final /* synthetic */ AIViewModel2 this$0;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        C02301(String str, String str2, AIViewModel2 aIViewModel2, String str3, Continuation<? super C02301> continuation) {
            super(2, continuation);
            this.$requestId = str;
            this.$mid = str2;
            this.this$0 = aIViewModel2;
            this.$image = str3;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
            return new C02301(this.$requestId, this.$mid, this.this$0, this.$image, continuation);
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(CoroutineScope coroutineScope, Continuation<? super Unit> continuation) {
            return ((C02301) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
        }

        /* JADX INFO: renamed from: com.miui.handwriting.common.ai.viewmodel.AIViewModel2$getImageTheme$1$1, reason: invalid class name and collision with other inner class name */
        /* JADX INFO: compiled from: AIViewModel2.kt */
        @Metadata(d1 = {"\u0000\n\n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\u0010\u0000\u001a\u00020\u0001*\u00020\u0002H\n"}, d2 = {"<anonymous>", "", "Lkotlinx/coroutines/CoroutineScope;"}, k = 3, mv = {2, 2, 0}, xi = 48)
        @DebugMetadata(c = "com.miui.handwriting.common.ai.viewmodel.AIViewModel2$getImageTheme$1$1", f = "AIViewModel2.kt", i = {}, l = {}, m = "invokeSuspend", n = {}, s = {})
        static final class C00571 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
            final /* synthetic */ String $image;
            final /* synthetic */ String $mid;
            final /* synthetic */ String $requestId;
            int label;
            final /* synthetic */ AIViewModel2 this$0;

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            C00571(String str, String str2, AIViewModel2 aIViewModel2, String str3, Continuation<? super C00571> continuation) {
                super(2, continuation);
                this.$requestId = str;
                this.$mid = str2;
                this.this$0 = aIViewModel2;
                this.$image = str3;
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
                return new C00571(this.$requestId, this.$mid, this.this$0, this.$image, continuation);
            }

            @Override // kotlin.jvm.functions.Function2
            public final Object invoke(CoroutineScope coroutineScope, Continuation<? super Unit> continuation) {
                return ((C00571) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Object invokeSuspend(Object obj) {
                IntrinsicsKt.getCOROUTINE_SUSPENDED();
                if (this.label != 0) {
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                }
                ResultKt.throwOnFailure(obj);
                try {
                    HashMap map = new HashMap();
                    map.put("oaid", DeviceUtils.getOAId());
                    map.put("timestamp", String.valueOf(System.currentTimeMillis()));
                    map.put("requestId", this.$requestId);
                    map.put("miId", this.$mid);
                    HashMap map2 = new HashMap();
                    map2.put("file", this.$image);
                    Result result = (Result) new Gson().fromJson(CreationAIRequest2.Companion.upload$default(CreationAIRequest2.INSTANCE, NetConfig.INSTANCE.getURL_GET_THEME_BY_FILE(), map, map2, false, 8, null), new TypeToken<Result<ThemeItem>>() { // from class: com.miui.handwriting.common.ai.viewmodel.AIViewModel2$getImageTheme$1$1$response$1
                    }.getType());
                    if (result != null && result.getCode() == 0) {
                        this.this$0._aiTheme.postValue(result.getData());
                    } else {
                        Log.e(AIViewModel2.TAG, "getImageTheme e:" + result);
                        this.this$0._aiTheme.postValue(null);
                        this.this$0._errorCreation.postValue(Boxing.boxInt(result.getCode()));
                        this.this$0.getCreationRequestLiveData().postError("getImageTheme requestUploadMeta error");
                    }
                } catch (Exception e) {
                    Log.e(AIViewModel2.TAG, "getImageTheme e:" + e);
                    if (!(e instanceof CancellationException)) {
                        this.this$0._aiTheme.postValue(null);
                        this.this$0._errorCreation.postValue(Boxing.boxInt(-1));
                        this.this$0.getCreationRequestLiveData().postError("getImageTheme requestUploadMeta error");
                    } else {
                        return Unit.INSTANCE;
                    }
                }
                return Unit.INSTANCE;
            }
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            Object coroutine_suspended = IntrinsicsKt.getCOROUTINE_SUSPENDED();
            int i = this.label;
            if (i == 0) {
                ResultKt.throwOnFailure(obj);
                this.label = 1;
                if (BuildersKt.withContext(Dispatchers.getIO(), new C00571(this.$requestId, this.$mid, this.this$0, this.$image, null), this) == coroutine_suspended) {
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
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Code duplicated, block: B:12:0x0055  */
    /* JADX WARN: Code duplicated, block: B:22:0x0073  */
    /* JADX WARN: Code duplicated, block: B:24:0x0077  */
    /* JADX WARN: Code duplicated, block: B:26:0x007d  */
    /* JADX WARN: Code duplicated, block: B:29:0x0085  */
    /* JADX WARN: Code duplicated, block: B:30:0x0087  */
    public final boolean isSameRequestParam(String requestPrompt, Integer requestTheme, String uri) {
        ReferenceItem referenceItem;
        String uri2;
        boolean z;
        String str;
        String str2;
        StringBuilder sbAppend = new StringBuilder("newImage:creationImageItem:").append(requestTheme).append(",prompt:").append(requestPrompt).append(",oldImage,currentThemeItem:");
        ThemeItem themeItem = this.curretThemeItem;
        Log.i(TAG, sbAppend.append(themeItem != null ? Integer.valueOf(themeItem.getThemeId()) : null).append(",prompt:").append(this.currentDesc).toString());
        ThemeItem themeItem2 = this.curretThemeItem;
        if (themeItem2 != null) {
            if (!Intrinsics.areEqual(themeItem2 != null ? Integer.valueOf(themeItem2.getThemeId()) : null, requestTheme)) {
                z = false;
            } else if (Intrinsics.areEqual(requestPrompt, this.currentDesc)) {
                referenceItem = this.currentItemReferce;
                if (referenceItem != null) {
                    uri2 = "";
                } else {
                    uri2 = "";
                }
                if (Intrinsics.areEqual(uri, uri2)) {
                    z = true;
                } else {
                    z = false;
                }
            } else {
                referenceItem = this.currentItemReferce;
                if (referenceItem != null) {
                    uri2 = "";
                } else {
                    uri2 = "";
                }
                if (Intrinsics.areEqual(uri, uri2)) {
                    z = true;
                } else {
                    z = false;
                }
            }
        } else if (!Intrinsics.areEqual(requestPrompt, this.currentDesc) || (((str = requestPrompt) == null || str.length() == 0) && ((str2 = this.currentDesc) == null || str2.length() == 0))) {
            referenceItem = this.currentItemReferce;
            if (referenceItem != null || (uri2 = referenceItem.getUri()) == null) {
                uri2 = "";
            }
            if (Intrinsics.areEqual(uri, uri2)) {
                z = true;
            } else {
                z = false;
            }
        } else {
            z = false;
        }
        Log.i(TAG, "isSameRequestParam:" + z);
        return z;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final boolean istPart(String type) {
        return Intrinsics.areEqual(type, CreationType.CREATION_PART_REPAINT) || Intrinsics.areEqual(type, CreationType.CREATION_PART_ELIMINATE);
    }

    /* JADX INFO: renamed from: com.miui.handwriting.common.ai.viewmodel.AIViewModel2$getImageRequestId$1, reason: invalid class name and case insensitive filesystem */
    /* JADX INFO: compiled from: AIViewModel2.kt */
    @Metadata(d1 = {"\u0000\n\n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\u0010\u0000\u001a\u00020\u0001*\u00020\u0002H\n"}, d2 = {"<anonymous>", "", "Lkotlinx/coroutines/CoroutineScope;"}, k = 3, mv = {2, 2, 0}, xi = 48)
    @DebugMetadata(c = "com.miui.handwriting.common.ai.viewmodel.AIViewModel2$getImageRequestId$1", f = "AIViewModel2.kt", i = {}, l = {R2.attr.layerMaskedShader}, m = "invokeSuspend", n = {}, s = {})
    static final class C02291 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
        final /* synthetic */ String $image;
        final /* synthetic */ String $mid;
        final /* synthetic */ String $prompt;
        int label;
        final /* synthetic */ AIViewModel2 this$0;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        C02291(String str, String str2, AIViewModel2 aIViewModel2, String str3, Continuation<? super C02291> continuation) {
            super(2, continuation);
            this.$mid = str;
            this.$image = str2;
            this.this$0 = aIViewModel2;
            this.$prompt = str3;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
            return new C02291(this.$mid, this.$image, this.this$0, this.$prompt, continuation);
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(CoroutineScope coroutineScope, Continuation<? super Unit> continuation) {
            return ((C02291) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
        }

        /* JADX INFO: renamed from: com.miui.handwriting.common.ai.viewmodel.AIViewModel2$getImageRequestId$1$1, reason: invalid class name and collision with other inner class name */
        /* JADX INFO: compiled from: AIViewModel2.kt */
        @Metadata(d1 = {"\u0000\n\n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\u0010\u0000\u001a\u00020\u0001*\u00020\u0002H\n"}, d2 = {"<anonymous>", "", "Lkotlinx/coroutines/CoroutineScope;"}, k = 3, mv = {2, 2, 0}, xi = 48)
        @DebugMetadata(c = "com.miui.handwriting.common.ai.viewmodel.AIViewModel2$getImageRequestId$1$1", f = "AIViewModel2.kt", i = {}, l = {}, m = "invokeSuspend", n = {}, s = {})
        static final class C00561 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
            final /* synthetic */ String $image;
            final /* synthetic */ String $mid;
            final /* synthetic */ String $prompt;
            int label;
            final /* synthetic */ AIViewModel2 this$0;

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            C00561(String str, String str2, AIViewModel2 aIViewModel2, String str3, Continuation<? super C00561> continuation) {
                super(2, continuation);
                this.$mid = str;
                this.$image = str2;
                this.this$0 = aIViewModel2;
                this.$prompt = str3;
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
                return new C00561(this.$mid, this.$image, this.this$0, this.$prompt, continuation);
            }

            @Override // kotlin.jvm.functions.Function2
            public final Object invoke(CoroutineScope coroutineScope, Continuation<? super Unit> continuation) {
                return ((C00561) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Object invokeSuspend(Object obj) throws Throwable {
                IntrinsicsKt.getCOROUTINE_SUSPENDED();
                if (this.label != 0) {
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                }
                ResultKt.throwOnFailure(obj);
                try {
                    HashMap map = new HashMap();
                    map.put("miId", this.$mid);
                    map.put("oaid", DeviceUtils.getOAId());
                    map.put("timestamp", String.valueOf(System.currentTimeMillis()));
                    ArrayList arrayList = new ArrayList();
                    arrayList.add("AES-GCM-256");
                    ArrayList arrayList2 = new ArrayList();
                    if (this.$image.length() > 0) {
                        BitmapFactory.Options options = new BitmapFactory.Options();
                        options.inJustDecodeBounds = true;
                        BitmapFactory.decodeFile(this.$image, options);
                        this.this$0.setImageWidth(options.outWidth);
                        this.this$0.setImageHeight(options.outHeight);
                        String fileSha1Digest = Utils.getFileSha1Digest(this.$image);
                        File file = new File(this.$image);
                        Intrinsics.checkNotNull(fileSha1Digest);
                        arrayList2.add(new ParamImageInfo(fileSha1Digest, String.valueOf(file.length())));
                    }
                    map.put("data", new Gson().toJson(new ParamUploadMetaData(arrayList, arrayList2, null)));
                    Log.d(AIViewModel2.TAG, "getImageRequestId requestUploadMeta param:" + map);
                    Object objFromJson = new Gson().fromJson(CreationAIRequest2.Companion.post$default(CreationAIRequest2.INSTANCE, NetConfig.INSTANCE.getURL_UPLOAD_META(), null, map, null, false, 24, null), new TypeToken<Result<UploadMetaData>>() { // from class: com.miui.handwriting.common.ai.viewmodel.AIViewModel2$getImageRequestId$1$1$uploadMetaResult$1
                    }.getType());
                    Intrinsics.checkNotNullExpressionValue(objFromJson, "fromJson(...)");
                    Result result = (Result) objFromJson;
                    Log.d(AIViewModel2.TAG, "getImageRequestId requestUploadMeta uploadMetaResult:" + result);
                    if (result.getCode() == 0) {
                        this.this$0.creationMap.clear();
                        if (this.$prompt.length() > 0) {
                            this.this$0.creationMap.put(this.$prompt, new RecreateInfo(this.$mid, ((UploadMetaData) result.getData()).getRequestId(), ((UploadMetaData) result.getData()).getKey(), ((UploadMetaData) result.getData()).getOptions().getIv(), CreationType.CREATION_TEXT));
                            this.this$0.getPromptTheme(this.$prompt, ((UploadMetaData) result.getData()).getRequestId(), this.$mid);
                        }
                        if (this.$image.length() > 0) {
                            this.this$0.creationMap.put(this.$image, new RecreateInfo(this.$mid, ((UploadMetaData) result.getData()).getRequestId(), ((UploadMetaData) result.getData()).getKey(), ((UploadMetaData) result.getData()).getOptions().getIv(), null, 16, null));
                            this.this$0.getImageTheme(this.$image, ((UploadMetaData) result.getData()).getRequestId(), this.$mid);
                        }
                    } else {
                        Log.e(AIViewModel2.TAG, "getImageRequestId requestUploadMeta uploadMetaResult,code:" + result.getCode());
                        this.this$0._errorCreation.postValue(Boxing.boxInt(result.getCode()));
                        this.this$0.getCreationRequestLiveData().postError("getImageRequestId requestUploadMeta fail");
                    }
                } catch (Exception e) {
                    Log.e(AIViewModel2.TAG, "getImageRequestId requestUploadMeta e:" + e);
                    if (!(e instanceof CancellationException)) {
                        this.this$0._errorCreation.postValue(Boxing.boxInt(-1));
                        this.this$0.getCreationRequestLiveData().postError("getImageRequestId requestUploadMeta error");
                    } else {
                        return Unit.INSTANCE;
                    }
                }
                return Unit.INSTANCE;
            }
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            Object coroutine_suspended = IntrinsicsKt.getCOROUTINE_SUSPENDED();
            int i = this.label;
            if (i == 0) {
                ResultKt.throwOnFailure(obj);
                this.label = 1;
                if (BuildersKt.withContext(Dispatchers.getIO(), new C00561(this.$mid, this.$image, this.this$0, this.$prompt, null), this) == coroutine_suspended) {
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
    }

    public final void getImageRequestId(String mid, String image, String prompt) {
        Intrinsics.checkNotNullParameter(mid, "mid");
        Intrinsics.checkNotNullParameter(image, "image");
        Intrinsics.checkNotNullParameter(prompt, "prompt");
        BuildersKt__Builders_commonKt.launch$default(ViewModelKt.getViewModelScope(this), null, null, new C02291(mid, image, this, prompt, null), 3, null);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final Object requestTextCreation(String str, String str2, String str3, String str4, ThemeItem themeItem, String str5, ReferenceItem referenceItem, String str6, Continuation<? super String> continuation) {
        String path;
        HashMap map = new HashMap();
        map.put("miId", str);
        map.put("oaid", DeviceUtils.getOAId());
        map.put("requestId", str4);
        map.put("timestamp", String.valueOf(System.currentTimeMillis()));
        map.put("type", str6);
        map.put(NetConfig.PARAM_CREATION_NUM, "3");
        String str7 = str5;
        if (str7 != null && str7.length() != 0) {
            map.put("prompt", str5);
        }
        if (themeItem != null) {
            themeItem.getTheme();
            map.put("theme", themeItem.getTheme());
        }
        HashMap map2 = new HashMap();
        if (referenceItem != null && (path = referenceItem.getPath()) != null) {
            byte[] imageByteArray = Utils.getImageByteArray(path);
            Log.d(TAG, "guildByte size:" + imageByteArray.length + ' ');
            byte[] bArrDecode = Base64.decode(str2, 0);
            SecretKey secretKeyLoadKey = AESUtil.loadKey(bArrDecode);
            StringBuilder sbAppend = new StringBuilder("serverKey :").append(str2).append("  decode:");
            Intrinsics.checkNotNull(bArrDecode);
            Log.d(TAG, sbAppend.append(ArraysKt.asList(bArrDecode)).toString());
            byte[] bArrDecode2 = Base64.decode(str3, 0);
            GCMParameterSpec gCMParameterSpecLoadGCMParameterSpec = AESUtil.loadGCMParameterSpec(bArrDecode2);
            StringBuilder sbAppend2 = new StringBuilder("iv:").append(str3).append("  toByte:");
            Intrinsics.checkNotNull(bArrDecode2);
            Log.d(TAG, sbAppend2.append(ArraysKt.asList(bArrDecode2)).toString());
            map2.put(NetConfig.PARAM_GUILD_FILE, Utils.saveFile(AESUtil.encrypt(imageByteArray, secretKeyLoadKey, gCMParameterSpecLoadGCMParameterSpec)).getPath());
        }
        Log.e(TAG, "multipartBody=={mid:" + str + ",oid:" + DeviceUtils.getOAId() + ",prompt:" + str5 + ",type:" + str6 + CoreConstants.CURLY_RIGHT);
        return CreationAIRequest2.Companion.upload$default(CreationAIRequest2.INSTANCE, NetConfig.INSTANCE.getURL_CREATION(), map, map2, false, 8, null);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void getPromptTheme(String prompt, String requestId, String mid) {
        if (prompt.length() == 0) {
            return;
        }
        BuildersKt__Builders_commonKt.launch$default(ViewModelKt.getViewModelScope(this), null, null, new C02311(requestId, mid, prompt, this, null), 3, null);
    }

    /* JADX INFO: renamed from: com.miui.handwriting.common.ai.viewmodel.AIViewModel2$getPromptTheme$1, reason: invalid class name and case insensitive filesystem */
    /* JADX INFO: compiled from: AIViewModel2.kt */
    @Metadata(d1 = {"\u0000\n\n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\u0010\u0000\u001a\u00020\u0001*\u00020\u0002H\n"}, d2 = {"<anonymous>", "", "Lkotlinx/coroutines/CoroutineScope;"}, k = 3, mv = {2, 2, 0}, xi = 48)
    @DebugMetadata(c = "com.miui.handwriting.common.ai.viewmodel.AIViewModel2$getPromptTheme$1", f = "AIViewModel2.kt", i = {}, l = {R2.attr.lottie_renderMode}, m = "invokeSuspend", n = {}, s = {})
    static final class C02311 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
        final /* synthetic */ String $mid;
        final /* synthetic */ String $prompt;
        final /* synthetic */ String $requestId;
        int label;
        final /* synthetic */ AIViewModel2 this$0;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        C02311(String str, String str2, String str3, AIViewModel2 aIViewModel2, Continuation<? super C02311> continuation) {
            super(2, continuation);
            this.$requestId = str;
            this.$mid = str2;
            this.$prompt = str3;
            this.this$0 = aIViewModel2;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
            return new C02311(this.$requestId, this.$mid, this.$prompt, this.this$0, continuation);
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(CoroutineScope coroutineScope, Continuation<? super Unit> continuation) {
            return ((C02311) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
        }

        /* JADX INFO: renamed from: com.miui.handwriting.common.ai.viewmodel.AIViewModel2$getPromptTheme$1$1, reason: invalid class name and collision with other inner class name */
        /* JADX INFO: compiled from: AIViewModel2.kt */
        @Metadata(d1 = {"\u0000\n\n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\u0010\u0000\u001a\u00020\u0001*\u00020\u0002H\n"}, d2 = {"<anonymous>", "", "Lkotlinx/coroutines/CoroutineScope;"}, k = 3, mv = {2, 2, 0}, xi = 48)
        @DebugMetadata(c = "com.miui.handwriting.common.ai.viewmodel.AIViewModel2$getPromptTheme$1$1", f = "AIViewModel2.kt", i = {}, l = {}, m = "invokeSuspend", n = {}, s = {})
        static final class C00581 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
            final /* synthetic */ String $mid;
            final /* synthetic */ String $prompt;
            final /* synthetic */ String $requestId;
            int label;
            final /* synthetic */ AIViewModel2 this$0;

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            C00581(String str, String str2, String str3, AIViewModel2 aIViewModel2, Continuation<? super C00581> continuation) {
                super(2, continuation);
                this.$requestId = str;
                this.$mid = str2;
                this.$prompt = str3;
                this.this$0 = aIViewModel2;
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
                return new C00581(this.$requestId, this.$mid, this.$prompt, this.this$0, continuation);
            }

            @Override // kotlin.jvm.functions.Function2
            public final Object invoke(CoroutineScope coroutineScope, Continuation<? super Unit> continuation) {
                return ((C00581) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Object invokeSuspend(Object obj) {
                IntrinsicsKt.getCOROUTINE_SUSPENDED();
                if (this.label != 0) {
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                }
                ResultKt.throwOnFailure(obj);
                try {
                    HashMap map = new HashMap();
                    map.put("oaid", DeviceUtils.getOAId());
                    map.put("timestamp", String.valueOf(System.currentTimeMillis()));
                    map.put("requestId", this.$requestId);
                    map.put("miId", this.$mid);
                    map.put("prompt", this.$prompt);
                    HashMap map2 = new HashMap();
                    map2.put("accept-language", Locale.getDefault().toString());
                    String str = CreationAIRequest2.Companion.get$default(CreationAIRequest2.INSTANCE, NetConfig.INSTANCE.getURL_GET_THEME_BY_PROMPT(), map2, map, false, 8, null);
                    Log.e(AIViewModel2.TAG, "getPromptTheme result:" + str);
                    Result result = (Result) new Gson().fromJson(str, new TypeToken<Result<ThemeItem>>() { // from class: com.miui.handwriting.common.ai.viewmodel.AIViewModel2$getPromptTheme$1$1$response$1
                    }.getType());
                    if (result == null || result.getCode() != 0) {
                        this.this$0._aiTheme.postValue(null);
                        this.this$0._errorCreation.postValue(Boxing.boxInt(result.getCode()));
                        this.this$0.getCreationRequestLiveData().postError("getPromptTheme requestUploadMeta error");
                    } else {
                        this.this$0._aiTheme.postValue(result.getData());
                    }
                } catch (Exception e) {
                    Log.e(AIViewModel2.TAG, "getPromptTheme e:" + e);
                    if (!(e instanceof CancellationException)) {
                        this.this$0._aiTheme.postValue(null);
                        this.this$0._errorCreation.postValue(Boxing.boxInt(-1));
                        this.this$0.getCreationRequestLiveData().postError("getPromptTheme requestUploadMeta error");
                    } else {
                        return Unit.INSTANCE;
                    }
                }
                return Unit.INSTANCE;
            }
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            Object coroutine_suspended = IntrinsicsKt.getCOROUTINE_SUSPENDED();
            int i = this.label;
            if (i == 0) {
                ResultKt.throwOnFailure(obj);
                this.label = 1;
                if (BuildersKt.withContext(Dispatchers.getIO(), new C00581(this.$requestId, this.$mid, this.$prompt, this.this$0, null), this) == coroutine_suspended) {
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
    }
}
