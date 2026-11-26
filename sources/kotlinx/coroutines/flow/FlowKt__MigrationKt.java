package kotlinx.coroutines.flow;

import androidx.exifinterface.media.ExifInterface;
import ch.qos.logback.core.CoreConstants;
import com.xiaomi.mirror.RemoteDeviceInfo;
import kotlin.Deprecated;
import kotlin.DeprecationLevel;
import kotlin.KotlinNothingValueException;
import kotlin.Metadata;
import kotlin.ReplaceWith;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.CoroutineContext;
import kotlin.coroutines.intrinsics.IntrinsicsKt;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.functions.Function3;
import kotlin.jvm.functions.Function4;
import kotlin.jvm.functions.Function5;
import kotlin.jvm.functions.Function6;
import kotlinx.coroutines.DelayKt;

/* JADX INFO: compiled from: Migration.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000\u0086\u0001\n\u0000\n\u0002\u0010\u0001\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u0003\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u000b\n\u0002\b\u000b\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\t\n\u0002\b\t\u001a\b\u0010\u0000\u001a\u00020\u0001H\u0000\u001a&\u0010\u0002\u001a\b\u0012\u0004\u0012\u0002H\u00040\u0003\"\u0004\b\u0000\u0010\u0004*\b\u0012\u0004\u0012\u0002H\u00040\u00032\u0006\u0010\u0005\u001a\u00020\u0006H\u0007\u001a&\u0010\u0007\u001a\b\u0012\u0004\u0012\u0002H\u00040\u0003\"\u0004\b\u0000\u0010\u0004*\b\u0012\u0004\u0012\u0002H\u00040\u00032\u0006\u0010\u0005\u001a\u00020\u0006H\u0007\u001a&\u0010\b\u001a\b\u0012\u0004\u0012\u0002H\u00040\u0003\"\u0004\b\u0000\u0010\u0004*\b\u0012\u0004\u0012\u0002H\u00040\u00032\u0006\u0010\u0005\u001a\u00020\u0006H\u0007\u001a,\u0010\t\u001a\b\u0012\u0004\u0012\u0002H\u00040\u0003\"\u0004\b\u0000\u0010\u0004*\b\u0012\u0004\u0012\u0002H\u00040\u00032\f\u0010\n\u001a\b\u0012\u0004\u0012\u0002H\u00040\u0003H\u0007\u001a,\u0010\u000b\u001a\b\u0012\u0004\u0012\u0002H\u00040\u0003\"\u0004\b\u0000\u0010\u0004*\b\u0012\u0004\u0012\u0002H\u00040\u00032\f\u0010\n\u001a\b\u0012\u0004\u0012\u0002H\u00040\u0003H\u0007\u001a\u0018\u0010\f\u001a\u00020\r\"\u0004\b\u0000\u0010\u0004*\b\u0012\u0004\u0012\u0002H\u00040\u0003H\u0007\u001aA\u0010\f\u001a\u00020\r\"\u0004\b\u0000\u0010\u0004*\b\u0012\u0004\u0012\u0002H\u00040\u00032\"\u0010\u000e\u001a\u001e\b\u0001\u0012\u0004\u0012\u0002H\u0004\u0012\n\u0012\b\u0012\u0004\u0012\u00020\r0\u0010\u0012\u0006\u0012\u0004\u0018\u00010\u00110\u000fH\u0007¢\u0006\u0002\u0010\u0012\u001ae\u0010\f\u001a\u00020\r\"\u0004\b\u0000\u0010\u0004*\b\u0012\u0004\u0012\u0002H\u00040\u00032\"\u0010\u000e\u001a\u001e\b\u0001\u0012\u0004\u0012\u0002H\u0004\u0012\n\u0012\b\u0012\u0004\u0012\u00020\r0\u0010\u0012\u0006\u0012\u0004\u0018\u00010\u00110\u000f2\"\u0010\u0013\u001a\u001e\b\u0001\u0012\u0004\u0012\u00020\u0014\u0012\n\u0012\b\u0012\u0004\u0012\u00020\r0\u0010\u0012\u0006\u0012\u0004\u0018\u00010\u00110\u000fH\u0007¢\u0006\u0002\u0010\u0015\u001aS\u0010\u0016\u001a\b\u0012\u0004\u0012\u0002H\u00170\u0003\"\u0004\b\u0000\u0010\u0004\"\u0004\b\u0001\u0010\u0017*\b\u0012\u0004\u0012\u0002H\u00040\u00032(\u0010\u0018\u001a$\b\u0001\u0012\u0004\u0012\u0002H\u0004\u0012\u0010\u0012\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u0002H\u00170\u00030\u0010\u0012\u0006\u0012\u0004\u0018\u00010\u00110\u000fH\u0007¢\u0006\u0002\u0010\u0019\u001a>\u0010\u001a\u001a\b\u0012\u0004\u0012\u0002H\u00170\u0003\"\u0004\b\u0000\u0010\u0004\"\u0004\b\u0001\u0010\u0017*\b\u0012\u0004\u0012\u0002H\u00040\u00032\u0018\u0010\u0018\u001a\u0014\u0012\u0004\u0012\u0002H\u0004\u0012\n\u0012\b\u0012\u0004\u0012\u0002H\u00170\u00030\u001bH\u0007\u001a$\u0010\u001c\u001a\b\u0012\u0004\u0012\u0002H\u00040\u0003\"\u0004\b\u0000\u0010\u0004*\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u0002H\u00040\u00030\u0003H\u0007\u001a$\u0010\u001d\u001a\b\u0012\u0004\u0012\u0002H\u00040\u0003\"\u0004\b\u0000\u0010\u0004*\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u0002H\u00040\u00030\u0003H\u0007\u001aI\u0010\u001e\u001a\b\u0012\u0004\u0012\u0002H\u00170\u0003\"\u0004\b\u0000\u0010\u0004\"\u0004\b\u0001\u0010\u0017*\b\u0012\u0004\u0012\u0002H\u00040\u00032#\u0010\u001f\u001a\u001f\u0012\n\u0012\b\u0012\u0004\u0012\u0002H\u00040\u0003\u0012\n\u0012\b\u0012\u0004\u0012\u0002H\u00170\u00030\u001b¢\u0006\u0002\b H\u0007\u001a&\u0010!\u001a\b\u0012\u0004\u0012\u0002H\u00040\u0003\"\u0004\b\u0000\u0010\u0004*\b\u0012\u0004\u0012\u0002H\u00040\u00032\u0006\u0010\"\u001a\u00020#H\u0007\u001aP\u0010$\u001a\u00020\r\"\u0004\b\u0000\u0010\u0004*\b\u0012\u0004\u0012\u0002H\u00040\u000321\u0010%\u001a-\b\u0001\u0012\u0013\u0012\u0011H\u0004¢\u0006\f\b&\u0012\b\b'\u0012\u0004\b\b((\u0012\n\u0012\b\u0012\u0004\u0012\u00020\r0\u0010\u0012\u0006\u0012\u0004\u0018\u00010\u00110\u000fH\u0007¢\u0006\u0002\u0010\u0012\u001a{\u0010)\u001a\b\u0012\u0004\u0012\u0002H\u00170\u0003\"\u0004\b\u0000\u0010\u0004\"\u0004\b\u0001\u0010\u0017*\b\u0012\u0004\u0012\u0002H\u00040\u00032\u0006\u0010*\u001a\u0002H\u00172H\b\u0001\u0010+\u001aB\b\u0001\u0012\u0013\u0012\u0011H\u0017¢\u0006\f\b&\u0012\b\b'\u0012\u0004\b\b(-\u0012\u0013\u0012\u0011H\u0004¢\u0006\f\b&\u0012\b\b'\u0012\u0004\b\b((\u0012\n\u0012\b\u0012\u0004\u0012\u0002H\u00170\u0010\u0012\u0006\u0012\u0004\u0018\u00010\u00110,H\u0007¢\u0006\u0002\u0010.\u001a+\u0010/\u001a\b\u0012\u0004\u0012\u0002H\u00040\u0003\"\u0004\b\u0000\u0010\u0004*\b\u0012\u0004\u0012\u0002H\u00040\u00032\u0006\u0010\n\u001a\u0002H\u0004H\u0007¢\u0006\u0002\u00100\u001aA\u0010/\u001a\b\u0012\u0004\u0012\u0002H\u00040\u0003\"\u0004\b\u0000\u0010\u0004*\b\u0012\u0004\u0012\u0002H\u00040\u00032\u0006\u0010\n\u001a\u0002H\u00042\u0014\b\u0002\u00101\u001a\u000e\u0012\u0004\u0012\u00020\u0014\u0012\u0004\u0012\u0002020\u001bH\u0007¢\u0006\u0002\u00103\u001a+\u00104\u001a\b\u0012\u0004\u0012\u0002H\u00040\u0003\"\u0004\b\u0000\u0010\u0004*\b\u0012\u0004\u0012\u0002H\u00040\u00032\u0006\u0010(\u001a\u0002H\u0004H\u0007¢\u0006\u0002\u00100\u001a,\u00104\u001a\b\u0012\u0004\u0012\u0002H\u00040\u0003\"\u0004\b\u0000\u0010\u0004*\b\u0012\u0004\u0012\u0002H\u00040\u00032\f\u00105\u001a\b\u0012\u0004\u0012\u0002H\u00040\u0003H\u0007\u001a+\u00106\u001a\b\u0012\u0004\u0012\u0002H\u00040\u0003\"\u0004\b\u0000\u0010\u0004*\b\u0012\u0004\u0012\u0002H\u00040\u00032\u0006\u0010(\u001a\u0002H\u0004H\u0007¢\u0006\u0002\u00100\u001a,\u00106\u001a\b\u0012\u0004\u0012\u0002H\u00040\u0003\"\u0004\b\u0000\u0010\u0004*\b\u0012\u0004\u0012\u0002H\u00040\u00032\f\u00105\u001a\b\u0012\u0004\u0012\u0002H\u00040\u0003H\u0007\u001ag\u00107\u001a\b\u0012\u0004\u0012\u0002H\u00170\u0003\"\u0004\b\u0000\u00108\"\u0004\b\u0001\u00109\"\u0004\b\u0002\u0010\u0017*\b\u0012\u0004\u0012\u0002H80\u00032\f\u00105\u001a\b\u0012\u0004\u0012\u0002H90\u00032(\u0010:\u001a$\b\u0001\u0012\u0004\u0012\u0002H8\u0012\u0004\u0012\u0002H9\u0012\n\u0012\b\u0012\u0004\u0012\u0002H\u00170\u0010\u0012\u0006\u0012\u0004\u0018\u00010\u00110,H\u0007¢\u0006\u0002\u0010;\u001a\u0081\u0001\u00107\u001a\b\u0012\u0004\u0012\u0002H\u00170\u0003\"\u0004\b\u0000\u00108\"\u0004\b\u0001\u00109\"\u0004\b\u0002\u0010<\"\u0004\b\u0003\u0010\u0017*\b\u0012\u0004\u0012\u0002H80\u00032\f\u00105\u001a\b\u0012\u0004\u0012\u0002H90\u00032\f\u0010=\u001a\b\u0012\u0004\u0012\u0002H<0\u00032.\u0010:\u001a*\b\u0001\u0012\u0004\u0012\u0002H8\u0012\u0004\u0012\u0002H9\u0012\u0004\u0012\u0002H<\u0012\n\u0012\b\u0012\u0004\u0012\u0002H\u00170\u0010\u0012\u0006\u0012\u0004\u0018\u00010\u00110>H\u0007¢\u0006\u0002\u0010?\u001a\u009b\u0001\u00107\u001a\b\u0012\u0004\u0012\u0002H\u00170\u0003\"\u0004\b\u0000\u00108\"\u0004\b\u0001\u00109\"\u0004\b\u0002\u0010<\"\u0004\b\u0003\u0010@\"\u0004\b\u0004\u0010\u0017*\b\u0012\u0004\u0012\u0002H80\u00032\f\u00105\u001a\b\u0012\u0004\u0012\u0002H90\u00032\f\u0010=\u001a\b\u0012\u0004\u0012\u0002H<0\u00032\f\u0010A\u001a\b\u0012\u0004\u0012\u0002H@0\u000324\u0010:\u001a0\b\u0001\u0012\u0004\u0012\u0002H8\u0012\u0004\u0012\u0002H9\u0012\u0004\u0012\u0002H<\u0012\u0004\u0012\u0002H@\u0012\n\u0012\b\u0012\u0004\u0012\u0002H\u00170\u0010\u0012\u0006\u0012\u0004\u0018\u00010\u00110BH\u0007¢\u0006\u0002\u0010C\u001aµ\u0001\u00107\u001a\b\u0012\u0004\u0012\u0002H\u00170\u0003\"\u0004\b\u0000\u00108\"\u0004\b\u0001\u00109\"\u0004\b\u0002\u0010<\"\u0004\b\u0003\u0010@\"\u0004\b\u0004\u0010D\"\u0004\b\u0005\u0010\u0017*\b\u0012\u0004\u0012\u0002H80\u00032\f\u00105\u001a\b\u0012\u0004\u0012\u0002H90\u00032\f\u0010=\u001a\b\u0012\u0004\u0012\u0002H<0\u00032\f\u0010A\u001a\b\u0012\u0004\u0012\u0002H@0\u00032\f\u0010E\u001a\b\u0012\u0004\u0012\u0002HD0\u00032:\u0010:\u001a6\b\u0001\u0012\u0004\u0012\u0002H8\u0012\u0004\u0012\u0002H9\u0012\u0004\u0012\u0002H<\u0012\u0004\u0012\u0002H@\u0012\u0004\u0012\u0002HD\u0012\n\u0012\b\u0012\u0004\u0012\u0002H\u00170\u0010\u0012\u0006\u0012\u0004\u0018\u00010\u00110FH\u0007¢\u0006\u0002\u0010G\u001a&\u0010H\u001a\b\u0012\u0004\u0012\u0002H\u00040\u0003\"\u0004\b\u0000\u0010\u0004*\b\u0012\u0004\u0012\u0002H\u00040\u00032\u0006\u0010I\u001a\u00020JH\u0007\u001a&\u0010K\u001a\b\u0012\u0004\u0012\u0002H\u00040\u0003\"\u0004\b\u0000\u0010\u0004*\b\u0012\u0004\u0012\u0002H\u00040\u00032\u0006\u0010I\u001a\u00020JH\u0007\u001ab\u0010L\u001a\b\u0012\u0004\u0012\u0002H\u00170\u0003\"\u0004\b\u0000\u0010\u0004\"\u0004\b\u0001\u0010\u0017*\b\u0012\u0004\u0012\u0002H\u00040\u000327\u0010:\u001a3\b\u0001\u0012\u0013\u0012\u0011H\u0004¢\u0006\f\b&\u0012\b\b'\u0012\u0004\b\b((\u0012\u0010\u0012\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u0002H\u00170\u00030\u0010\u0012\u0006\u0012\u0004\u0018\u00010\u00110\u000fH\u0007¢\u0006\u0002\u0010\u0019\u001ak\u0010M\u001a\b\u0012\u0004\u0012\u0002H\u00040\u0003\"\u0004\b\u0000\u0010\u0004*\b\u0012\u0004\u0012\u0002H\u00040\u00032F\u0010+\u001aB\b\u0001\u0012\u0013\u0012\u0011H\u0004¢\u0006\f\b&\u0012\b\b'\u0012\u0004\b\b(-\u0012\u0013\u0012\u0011H\u0004¢\u0006\f\b&\u0012\b\b'\u0012\u0004\b\b((\u0012\n\u0012\b\u0012\u0004\u0012\u0002H\u00040\u0010\u0012\u0006\u0012\u0004\u0018\u00010\u00110,H\u0007¢\u0006\u0002\u0010N\u001a\u001e\u0010O\u001a\b\u0012\u0004\u0012\u0002H\u00040\u0003\"\u0004\b\u0000\u0010\u0004*\b\u0012\u0004\u0012\u0002H\u00040\u0003H\u0007\u001a&\u0010O\u001a\b\u0012\u0004\u0012\u0002H\u00040\u0003\"\u0004\b\u0000\u0010\u0004*\b\u0012\u0004\u0012\u0002H\u00040\u00032\u0006\u0010P\u001a\u00020#H\u0007\u001a\u001e\u0010Q\u001a\b\u0012\u0004\u0012\u0002H\u00040\u0003\"\u0004\b\u0000\u0010\u0004*\b\u0012\u0004\u0012\u0002H\u00040\u0003H\u0007\u001a&\u0010Q\u001a\b\u0012\u0004\u0012\u0002H\u00040\u0003\"\u0004\b\u0000\u0010\u0004*\b\u0012\u0004\u0012\u0002H\u00040\u00032\u0006\u0010P\u001a\u00020#H\u0007\u001a\u001e\u0010R\u001a\b\u0012\u0004\u0012\u0002H\u00040\u0003\"\u0004\b\u0000\u0010\u0004*\b\u0012\u0004\u0012\u0002H\u00040\u0003H\u0007¨\u0006S"}, d2 = {"noImpl", "", "observeOn", "Lkotlinx/coroutines/flow/Flow;", ExifInterface.GPS_DIRECTION_TRUE, CoreConstants.CONTEXT_SCOPE_VALUE, "Lkotlin/coroutines/CoroutineContext;", "publishOn", "subscribeOn", "onErrorResume", "fallback", "onErrorResumeNext", "subscribe", "", "onEach", "Lkotlin/Function2;", "Lkotlin/coroutines/Continuation;", "", "(Lkotlinx/coroutines/flow/Flow;Lkotlin/jvm/functions/Function2;)V", "onError", "", "(Lkotlinx/coroutines/flow/Flow;Lkotlin/jvm/functions/Function2;Lkotlin/jvm/functions/Function2;)V", "flatMap", "R", "mapper", "(Lkotlinx/coroutines/flow/Flow;Lkotlin/jvm/functions/Function2;)Lkotlinx/coroutines/flow/Flow;", "concatMap", "Lkotlin/Function1;", "merge", "flatten", "compose", "transformer", "Lkotlin/ExtensionFunctionType;", "skip", "count", "", "forEach", "action", "Lkotlin/ParameterName;", "name", "value", "scanFold", "initial", "operation", "Lkotlin/Function3;", "accumulator", "(Lkotlinx/coroutines/flow/Flow;Ljava/lang/Object;Lkotlin/jvm/functions/Function3;)Lkotlinx/coroutines/flow/Flow;", "onErrorReturn", "(Lkotlinx/coroutines/flow/Flow;Ljava/lang/Object;)Lkotlinx/coroutines/flow/Flow;", "predicate", "", "(Lkotlinx/coroutines/flow/Flow;Ljava/lang/Object;Lkotlin/jvm/functions/Function1;)Lkotlinx/coroutines/flow/Flow;", "startWith", RemoteDeviceInfo.MANUFACTURER_OTHER, "concatWith", "combineLatest", "T1", "T2", "transform", "(Lkotlinx/coroutines/flow/Flow;Lkotlinx/coroutines/flow/Flow;Lkotlin/jvm/functions/Function3;)Lkotlinx/coroutines/flow/Flow;", "T3", "other2", "Lkotlin/Function4;", "(Lkotlinx/coroutines/flow/Flow;Lkotlinx/coroutines/flow/Flow;Lkotlinx/coroutines/flow/Flow;Lkotlin/jvm/functions/Function4;)Lkotlinx/coroutines/flow/Flow;", "T4", "other3", "Lkotlin/Function5;", "(Lkotlinx/coroutines/flow/Flow;Lkotlinx/coroutines/flow/Flow;Lkotlinx/coroutines/flow/Flow;Lkotlinx/coroutines/flow/Flow;Lkotlin/jvm/functions/Function5;)Lkotlinx/coroutines/flow/Flow;", "T5", "other4", "Lkotlin/Function6;", "(Lkotlinx/coroutines/flow/Flow;Lkotlinx/coroutines/flow/Flow;Lkotlinx/coroutines/flow/Flow;Lkotlinx/coroutines/flow/Flow;Lkotlinx/coroutines/flow/Flow;Lkotlin/jvm/functions/Function6;)Lkotlinx/coroutines/flow/Flow;", "delayFlow", "timeMillis", "", "delayEach", "switchMap", "scanReduce", "(Lkotlinx/coroutines/flow/Flow;Lkotlin/jvm/functions/Function3;)Lkotlinx/coroutines/flow/Flow;", "publish", "bufferSize", "replay", "cache", "kotlinx-coroutines-core"}, k = 5, mv = {2, 1, 0}, xi = 48, xs = "kotlinx/coroutines/flow/FlowKt")
final /* synthetic */ class FlowKt__MigrationKt {
    /* JADX INFO: Access modifiers changed from: private */
    public static final boolean onErrorReturn$lambda$0$FlowKt__MigrationKt(Throwable th) {
        return true;
    }

    public static final Void noImpl() {
        throw new UnsupportedOperationException("Not implemented, should not be called");
    }

    @Deprecated(level = DeprecationLevel.ERROR, message = "Collect flow in the desired context instead")
    public static final <T> Flow<T> observeOn(Flow<? extends T> flow, CoroutineContext coroutineContext) {
        FlowKt.noImpl();
        throw new KotlinNothingValueException();
    }

    @Deprecated(level = DeprecationLevel.ERROR, message = "Collect flow in the desired context instead")
    public static final <T> Flow<T> publishOn(Flow<? extends T> flow, CoroutineContext coroutineContext) {
        FlowKt.noImpl();
        throw new KotlinNothingValueException();
    }

    @Deprecated(level = DeprecationLevel.ERROR, message = "Use 'flowOn' instead")
    public static final <T> Flow<T> subscribeOn(Flow<? extends T> flow, CoroutineContext coroutineContext) {
        FlowKt.noImpl();
        throw new KotlinNothingValueException();
    }

    @Deprecated(level = DeprecationLevel.ERROR, message = "Flow analogue of 'onErrorXxx' is 'catch'. Use 'catch { emitAll(fallback) }'", replaceWith = @ReplaceWith(expression = "catch { emitAll(fallback) }", imports = {}))
    public static final <T> Flow<T> onErrorResume(Flow<? extends T> flow, Flow<? extends T> flow2) {
        FlowKt.noImpl();
        throw new KotlinNothingValueException();
    }

    @Deprecated(level = DeprecationLevel.ERROR, message = "Flow analogue of 'onErrorXxx' is 'catch'. Use 'catch { emitAll(fallback) }'", replaceWith = @ReplaceWith(expression = "catch { emitAll(fallback) }", imports = {}))
    public static final <T> Flow<T> onErrorResumeNext(Flow<? extends T> flow, Flow<? extends T> flow2) {
        FlowKt.noImpl();
        throw new KotlinNothingValueException();
    }

    @Deprecated(level = DeprecationLevel.ERROR, message = "Use 'launchIn' with 'onEach', 'onCompletion' and 'catch' instead")
    public static final <T> void subscribe(Flow<? extends T> flow) {
        FlowKt.noImpl();
        throw new KotlinNothingValueException();
    }

    @Deprecated(level = DeprecationLevel.ERROR, message = "Use 'launchIn' with 'onEach', 'onCompletion' and 'catch' instead")
    public static final <T> void subscribe(Flow<? extends T> flow, Function2<? super T, ? super Continuation<? super Unit>, ? extends Object> function2) {
        FlowKt.noImpl();
        throw new KotlinNothingValueException();
    }

    @Deprecated(level = DeprecationLevel.ERROR, message = "Use 'launchIn' with 'onEach', 'onCompletion' and 'catch' instead")
    public static final <T> void subscribe(Flow<? extends T> flow, Function2<? super T, ? super Continuation<? super Unit>, ? extends Object> function2, Function2<? super Throwable, ? super Continuation<? super Unit>, ? extends Object> function3) {
        FlowKt.noImpl();
        throw new KotlinNothingValueException();
    }

    @Deprecated(level = DeprecationLevel.ERROR, message = "Flow analogue is 'flatMapConcat'", replaceWith = @ReplaceWith(expression = "flatMapConcat(mapper)", imports = {}))
    public static final <T, R> Flow<R> flatMap(Flow<? extends T> flow, Function2<? super T, ? super Continuation<? super Flow<? extends R>>, ? extends Object> function2) {
        FlowKt.noImpl();
        throw new KotlinNothingValueException();
    }

    @Deprecated(level = DeprecationLevel.ERROR, message = "Flow analogue of 'concatMap' is 'flatMapConcat'", replaceWith = @ReplaceWith(expression = "flatMapConcat(mapper)", imports = {}))
    public static final <T, R> Flow<R> concatMap(Flow<? extends T> flow, Function1<? super T, ? extends Flow<? extends R>> function1) {
        FlowKt.noImpl();
        throw new KotlinNothingValueException();
    }

    @Deprecated(level = DeprecationLevel.ERROR, message = "Flow analogue of 'merge' is 'flattenConcat'", replaceWith = @ReplaceWith(expression = "flattenConcat()", imports = {}))
    public static final <T> Flow<T> merge(Flow<? extends Flow<? extends T>> flow) {
        FlowKt.noImpl();
        throw new KotlinNothingValueException();
    }

    @Deprecated(level = DeprecationLevel.ERROR, message = "Flow analogue of 'flatten' is 'flattenConcat'", replaceWith = @ReplaceWith(expression = "flattenConcat()", imports = {}))
    public static final <T> Flow<T> flatten(Flow<? extends Flow<? extends T>> flow) {
        FlowKt.noImpl();
        throw new KotlinNothingValueException();
    }

    @Deprecated(level = DeprecationLevel.ERROR, message = "Flow analogue of 'compose' is 'let'", replaceWith = @ReplaceWith(expression = "let(transformer)", imports = {}))
    public static final <T, R> Flow<R> compose(Flow<? extends T> flow, Function1<? super Flow<? extends T>, ? extends Flow<? extends R>> function1) {
        FlowKt.noImpl();
        throw new KotlinNothingValueException();
    }

    @Deprecated(level = DeprecationLevel.ERROR, message = "Flow analogue of 'skip' is 'drop'", replaceWith = @ReplaceWith(expression = "drop(count)", imports = {}))
    public static final <T> Flow<T> skip(Flow<? extends T> flow, int i) {
        FlowKt.noImpl();
        throw new KotlinNothingValueException();
    }

    @Deprecated(level = DeprecationLevel.ERROR, message = "Flow analogue of 'forEach' is 'collect'", replaceWith = @ReplaceWith(expression = "collect(action)", imports = {}))
    public static final <T> void forEach(Flow<? extends T> flow, Function2<? super T, ? super Continuation<? super Unit>, ? extends Object> function2) {
        FlowKt.noImpl();
        throw new KotlinNothingValueException();
    }

    @Deprecated(level = DeprecationLevel.ERROR, message = "Flow has less verbose 'scan' shortcut", replaceWith = @ReplaceWith(expression = "scan(initial, operation)", imports = {}))
    public static final <T, R> Flow<R> scanFold(Flow<? extends T> flow, R r, Function3<? super R, ? super T, ? super Continuation<? super R>, ? extends Object> function3) {
        FlowKt.noImpl();
        throw new KotlinNothingValueException();
    }

    @Deprecated(level = DeprecationLevel.ERROR, message = "Flow analogue of 'onErrorXxx' is 'catch'. Use 'catch { emit(fallback) }'", replaceWith = @ReplaceWith(expression = "catch { emit(fallback) }", imports = {}))
    public static final <T> Flow<T> onErrorReturn(Flow<? extends T> flow, T t) {
        FlowKt.noImpl();
        throw new KotlinNothingValueException();
    }

    /* JADX INFO: Add missing generic type declarations: [T] */
    /* JADX INFO: renamed from: kotlinx.coroutines.flow.FlowKt__MigrationKt$onErrorReturn$2, reason: invalid class name */
    /* JADX INFO: compiled from: Migration.kt */
    @Metadata(d1 = {"\u0000\u0012\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0003\u0010\u0000\u001a\u00020\u0001\"\u0004\b\u0000\u0010\u0002*\b\u0012\u0004\u0012\u0002H\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u0005H\n"}, d2 = {"<anonymous>", "", ExifInterface.GPS_DIRECTION_TRUE, "Lkotlinx/coroutines/flow/FlowCollector;", "e", ""}, k = 3, mv = {2, 1, 0}, xi = 48)
    @DebugMetadata(c = "kotlinx.coroutines.flow.FlowKt__MigrationKt$onErrorReturn$2", f = "Migration.kt", i = {}, l = {302}, m = "invokeSuspend", n = {}, s = {})
    static final class AnonymousClass2<T> extends SuspendLambda implements Function3<FlowCollector<? super T>, Throwable, Continuation<? super Unit>, Object> {
        final /* synthetic */ T $fallback;
        final /* synthetic */ Function1<Throwable, Boolean> $predicate;
        private /* synthetic */ Object L$0;
        /* synthetic */ Object L$1;
        int label;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        /* JADX WARN: Multi-variable type inference failed */
        AnonymousClass2(Function1<? super Throwable, Boolean> function1, T t, Continuation<? super AnonymousClass2> continuation) {
            super(3, continuation);
            this.$predicate = function1;
            this.$fallback = t;
        }

        @Override // kotlin.jvm.functions.Function3
        public final Object invoke(FlowCollector<? super T> flowCollector, Throwable th, Continuation<? super Unit> continuation) {
            AnonymousClass2 anonymousClass2 = new AnonymousClass2(this.$predicate, this.$fallback, continuation);
            anonymousClass2.L$0 = flowCollector;
            anonymousClass2.L$1 = th;
            return anonymousClass2.invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) throws Throwable {
            Object coroutine_suspended = IntrinsicsKt.getCOROUTINE_SUSPENDED();
            int i = this.label;
            if (i == 0) {
                ResultKt.throwOnFailure(obj);
                FlowCollector flowCollector = (FlowCollector) this.L$0;
                Throwable th = (Throwable) this.L$1;
                if (!this.$predicate.invoke(th).booleanValue()) {
                    throw th;
                }
                this.L$0 = null;
                this.label = 1;
                if (flowCollector.emit(this.$fallback, this) == coroutine_suspended) {
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

    public static /* synthetic */ Flow onErrorReturn$default(Flow flow, Object obj, Function1 function1, int i, Object obj2) {
        if ((i & 2) != 0) {
            function1 = new Function1() { // from class: kotlinx.coroutines.flow.FlowKt__MigrationKt$$ExternalSyntheticLambda0
                @Override // kotlin.jvm.functions.Function1
                public final Object invoke(Object obj3) {
                    return Boolean.valueOf(FlowKt__MigrationKt.onErrorReturn$lambda$0$FlowKt__MigrationKt((Throwable) obj3));
                }
            };
        }
        return FlowKt.onErrorReturn(flow, obj, function1);
    }

    @Deprecated(level = DeprecationLevel.ERROR, message = "Flow analogue of 'onErrorXxx' is 'catch'. Use 'catch { e -> if (predicate(e)) emit(fallback) else throw e }'", replaceWith = @ReplaceWith(expression = "catch { e -> if (predicate(e)) emit(fallback) else throw e }", imports = {}))
    public static final <T> Flow<T> onErrorReturn(Flow<? extends T> flow, T t, Function1<? super Throwable, Boolean> function1) {
        return FlowKt.m4091catch(flow, new AnonymousClass2(function1, t, null));
    }

    @Deprecated(level = DeprecationLevel.ERROR, message = "Flow analogue of 'startWith' is 'onStart'. Use 'onStart { emit(value) }'", replaceWith = @ReplaceWith(expression = "onStart { emit(value) }", imports = {}))
    public static final <T> Flow<T> startWith(Flow<? extends T> flow, T t) {
        FlowKt.noImpl();
        throw new KotlinNothingValueException();
    }

    @Deprecated(level = DeprecationLevel.ERROR, message = "Flow analogue of 'startWith' is 'onStart'. Use 'onStart { emitAll(other) }'", replaceWith = @ReplaceWith(expression = "onStart { emitAll(other) }", imports = {}))
    public static final <T> Flow<T> startWith(Flow<? extends T> flow, Flow<? extends T> flow2) {
        FlowKt.noImpl();
        throw new KotlinNothingValueException();
    }

    @Deprecated(level = DeprecationLevel.ERROR, message = "Flow analogue of 'concatWith' is 'onCompletion'. Use 'onCompletion { emit(value) }'", replaceWith = @ReplaceWith(expression = "onCompletion { emit(value) }", imports = {}))
    public static final <T> Flow<T> concatWith(Flow<? extends T> flow, T t) {
        FlowKt.noImpl();
        throw new KotlinNothingValueException();
    }

    @Deprecated(level = DeprecationLevel.ERROR, message = "Flow analogue of 'concatWith' is 'onCompletion'. Use 'onCompletion { if (it == null) emitAll(other) }'", replaceWith = @ReplaceWith(expression = "onCompletion { if (it == null) emitAll(other) }", imports = {}))
    public static final <T> Flow<T> concatWith(Flow<? extends T> flow, Flow<? extends T> flow2) {
        FlowKt.noImpl();
        throw new KotlinNothingValueException();
    }

    @Deprecated(level = DeprecationLevel.ERROR, message = "Flow analogue of 'combineLatest' is 'combine'", replaceWith = @ReplaceWith(expression = "this.combine(other, transform)", imports = {}))
    public static final <T1, T2, R> Flow<R> combineLatest(Flow<? extends T1> flow, Flow<? extends T2> flow2, Function3<? super T1, ? super T2, ? super Continuation<? super R>, ? extends Object> function3) {
        return FlowKt.combine(flow, flow2, function3);
    }

    @Deprecated(level = DeprecationLevel.ERROR, message = "Flow analogue of 'combineLatest' is 'combine'", replaceWith = @ReplaceWith(expression = "combine(this, other, other2, transform)", imports = {}))
    public static final <T1, T2, T3, R> Flow<R> combineLatest(Flow<? extends T1> flow, Flow<? extends T2> flow2, Flow<? extends T3> flow3, Function4<? super T1, ? super T2, ? super T3, ? super Continuation<? super R>, ? extends Object> function4) {
        return FlowKt.combine(flow, flow2, flow3, function4);
    }

    @Deprecated(level = DeprecationLevel.ERROR, message = "Flow analogue of 'combineLatest' is 'combine'", replaceWith = @ReplaceWith(expression = "combine(this, other, other2, other3, transform)", imports = {}))
    public static final <T1, T2, T3, T4, R> Flow<R> combineLatest(Flow<? extends T1> flow, Flow<? extends T2> flow2, Flow<? extends T3> flow3, Flow<? extends T4> flow4, Function5<? super T1, ? super T2, ? super T3, ? super T4, ? super Continuation<? super R>, ? extends Object> function5) {
        return FlowKt.combine(flow, flow2, flow3, flow4, function5);
    }

    @Deprecated(level = DeprecationLevel.ERROR, message = "Flow analogue of 'combineLatest' is 'combine'", replaceWith = @ReplaceWith(expression = "combine(this, other, other2, other3, transform)", imports = {}))
    public static final <T1, T2, T3, T4, T5, R> Flow<R> combineLatest(Flow<? extends T1> flow, Flow<? extends T2> flow2, Flow<? extends T3> flow3, Flow<? extends T4> flow4, Flow<? extends T5> flow5, Function6<? super T1, ? super T2, ? super T3, ? super T4, ? super T5, ? super Continuation<? super R>, ? extends Object> function6) {
        return FlowKt.combine(flow, flow2, flow3, flow4, flow5, function6);
    }

    /* JADX INFO: Add missing generic type declarations: [T] */
    /* JADX INFO: renamed from: kotlinx.coroutines.flow.FlowKt__MigrationKt$delayFlow$1, reason: invalid class name and case insensitive filesystem */
    /* JADX INFO: compiled from: Migration.kt */
    @Metadata(d1 = {"\u0000\f\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\u0010\u0000\u001a\u00020\u0001\"\u0004\b\u0000\u0010\u0002*\b\u0012\u0004\u0012\u0002H\u00020\u0003H\n"}, d2 = {"<anonymous>", "", ExifInterface.GPS_DIRECTION_TRUE, "Lkotlinx/coroutines/flow/FlowCollector;"}, k = 3, mv = {2, 1, 0}, xi = 48)
    @DebugMetadata(c = "kotlinx.coroutines.flow.FlowKt__MigrationKt$delayFlow$1", f = "Migration.kt", i = {}, l = {411}, m = "invokeSuspend", n = {}, s = {})
    static final class C04491<T> extends SuspendLambda implements Function2<FlowCollector<? super T>, Continuation<? super Unit>, Object> {
        final /* synthetic */ long $timeMillis;
        int label;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        C04491(long j, Continuation<? super C04491> continuation) {
            super(2, continuation);
            this.$timeMillis = j;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
            return new C04491(this.$timeMillis, continuation);
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(FlowCollector<? super T> flowCollector, Continuation<? super Unit> continuation) {
            return ((C04491) create(flowCollector, continuation)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            Object coroutine_suspended = IntrinsicsKt.getCOROUTINE_SUSPENDED();
            int i = this.label;
            if (i == 0) {
                ResultKt.throwOnFailure(obj);
                this.label = 1;
                if (DelayKt.delay(this.$timeMillis, this) == coroutine_suspended) {
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

    @Deprecated(level = DeprecationLevel.ERROR, message = "Use 'onStart { delay(timeMillis) }'", replaceWith = @ReplaceWith(expression = "onStart { delay(timeMillis) }", imports = {}))
    public static final <T> Flow<T> delayFlow(Flow<? extends T> flow, long j) {
        return FlowKt.onStart(flow, new C04491(j, null));
    }

    /* JADX INFO: Add missing generic type declarations: [T] */
    /* JADX INFO: renamed from: kotlinx.coroutines.flow.FlowKt__MigrationKt$delayEach$1, reason: invalid class name */
    /* JADX INFO: compiled from: Migration.kt */
    @Metadata(d1 = {"\u0000\n\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\u0010\u0000\u001a\u00020\u0001\"\u0004\b\u0000\u0010\u00022\u0006\u0010\u0003\u001a\u0002H\u0002H\n"}, d2 = {"<anonymous>", "", ExifInterface.GPS_DIRECTION_TRUE, "it"}, k = 3, mv = {2, 1, 0}, xi = 48)
    @DebugMetadata(c = "kotlinx.coroutines.flow.FlowKt__MigrationKt$delayEach$1", f = "Migration.kt", i = {}, l = {423}, m = "invokeSuspend", n = {}, s = {})
    static final class AnonymousClass1<T> extends SuspendLambda implements Function2<T, Continuation<? super Unit>, Object> {
        final /* synthetic */ long $timeMillis;
        int label;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        AnonymousClass1(long j, Continuation<? super AnonymousClass1> continuation) {
            super(2, continuation);
            this.$timeMillis = j;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
            return new AnonymousClass1(this.$timeMillis, continuation);
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(T t, Continuation<? super Unit> continuation) {
            return ((AnonymousClass1) create(t, continuation)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            Object coroutine_suspended = IntrinsicsKt.getCOROUTINE_SUSPENDED();
            int i = this.label;
            if (i == 0) {
                ResultKt.throwOnFailure(obj);
                this.label = 1;
                if (DelayKt.delay(this.$timeMillis, this) == coroutine_suspended) {
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

    @Deprecated(level = DeprecationLevel.ERROR, message = "Use 'onEach { delay(timeMillis) }'", replaceWith = @ReplaceWith(expression = "onEach { delay(timeMillis) }", imports = {}))
    public static final <T> Flow<T> delayEach(Flow<? extends T> flow, long j) {
        return FlowKt.onEach(flow, new AnonymousClass1(j, null));
    }

    @Deprecated(level = DeprecationLevel.ERROR, message = "'scanReduce' was renamed to 'runningReduce' to be consistent with Kotlin standard library", replaceWith = @ReplaceWith(expression = "runningReduce(operation)", imports = {}))
    public static final <T> Flow<T> scanReduce(Flow<? extends T> flow, Function3<? super T, ? super T, ? super Continuation<? super T>, ? extends Object> function3) {
        return FlowKt.runningReduce(flow, function3);
    }

    @Deprecated(level = DeprecationLevel.ERROR, message = "Flow analogue of 'publish()' is 'shareIn'. \npublish().connect() is the default strategy (no extra call is needed), \npublish().autoConnect() translates to 'started = SharingStarted.Lazily' argument, \npublish().refCount() translates to 'started = SharingStarted.WhileSubscribed()' argument.", replaceWith = @ReplaceWith(expression = "this.shareIn(scope, 0)", imports = {}))
    public static final <T> Flow<T> publish(Flow<? extends T> flow) {
        FlowKt.noImpl();
        throw new KotlinNothingValueException();
    }

    @Deprecated(level = DeprecationLevel.ERROR, message = "Flow analogue of 'publish(bufferSize)' is 'buffer' followed by 'shareIn'. \npublish().connect() is the default strategy (no extra call is needed), \npublish().autoConnect() translates to 'started = SharingStarted.Lazily' argument, \npublish().refCount() translates to 'started = SharingStarted.WhileSubscribed()' argument.", replaceWith = @ReplaceWith(expression = "this.buffer(bufferSize).shareIn(scope, 0)", imports = {}))
    public static final <T> Flow<T> publish(Flow<? extends T> flow, int i) {
        FlowKt.noImpl();
        throw new KotlinNothingValueException();
    }

    @Deprecated(level = DeprecationLevel.ERROR, message = "Flow analogue of 'replay()' is 'shareIn' with unlimited replay. \nreplay().connect() is the default strategy (no extra call is needed), \nreplay().autoConnect() translates to 'started = SharingStarted.Lazily' argument, \nreplay().refCount() translates to 'started = SharingStarted.WhileSubscribed()' argument.", replaceWith = @ReplaceWith(expression = "this.shareIn(scope, Int.MAX_VALUE)", imports = {}))
    public static final <T> Flow<T> replay(Flow<? extends T> flow) {
        FlowKt.noImpl();
        throw new KotlinNothingValueException();
    }

    @Deprecated(level = DeprecationLevel.ERROR, message = "Flow analogue of 'replay(bufferSize)' is 'shareIn' with the specified replay parameter. \nreplay().connect() is the default strategy (no extra call is needed), \nreplay().autoConnect() translates to 'started = SharingStarted.Lazily' argument, \nreplay().refCount() translates to 'started = SharingStarted.WhileSubscribed()' argument.", replaceWith = @ReplaceWith(expression = "this.shareIn(scope, bufferSize)", imports = {}))
    public static final <T> Flow<T> replay(Flow<? extends T> flow, int i) {
        FlowKt.noImpl();
        throw new KotlinNothingValueException();
    }

    @Deprecated(level = DeprecationLevel.ERROR, message = "Flow analogue of 'cache()' is 'shareIn' with unlimited replay and 'started = SharingStarted.Lazily' argument'", replaceWith = @ReplaceWith(expression = "this.shareIn(scope, started = SharingStarted.Lazily, replay = Int.MAX_VALUE)", imports = {}))
    public static final <T> Flow<T> cache(Flow<? extends T> flow) {
        FlowKt.noImpl();
        throw new KotlinNothingValueException();
    }

    @Deprecated(level = DeprecationLevel.ERROR, message = "Flow analogues of 'switchMap' are 'transformLatest', 'flatMapLatest' and 'mapLatest'", replaceWith = @ReplaceWith(expression = "this.flatMapLatest(transform)", imports = {}))
    public static final <T, R> Flow<R> switchMap(Flow<? extends T> flow, Function2<? super T, ? super Continuation<? super Flow<? extends R>>, ? extends Object> function2) {
        return FlowKt.transformLatest(flow, new FlowKt__MigrationKt$switchMap$$inlined$flatMapLatest$1(function2, null));
    }
}
