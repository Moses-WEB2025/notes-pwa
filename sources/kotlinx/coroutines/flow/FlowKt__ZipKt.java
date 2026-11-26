package kotlinx.coroutines.flow;

import androidx.exifinterface.media.ExifInterface;
import com.miui.richeditor.schema.HtmlParser;
import com.xiaomi.mirror.RemoteDeviceInfo;
import defpackage.R2;
import kotlin.Metadata;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.IntrinsicsKt;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.functions.Function3;
import kotlin.jvm.functions.Function4;
import kotlin.jvm.functions.Function5;
import kotlin.jvm.functions.Function6;
import kotlin.jvm.functions.Function7;
import kotlin.jvm.internal.Intrinsics;
import kotlinx.coroutines.flow.internal.CombineKt;

/* JADX INFO: compiled from: Zip.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000h\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0011\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u001c\n\u0002\b\u0005\u001a\u0087\u0001\u0010\u0000\u001a\b\u0012\u0004\u0012\u0002H\u00020\u0001\"\u0004\b\u0000\u0010\u0003\"\u0004\b\u0001\u0010\u0004\"\u0004\b\u0002\u0010\u0002*\b\u0012\u0004\u0012\u0002H\u00030\u00012\f\u0010\u0005\u001a\b\u0012\u0004\u0012\u0002H\u00040\u00012F\u0010\u0006\u001aB\b\u0001\u0012\u0013\u0012\u0011H\u0003¢\u0006\f\b\b\u0012\b\b\t\u0012\u0004\b\b(\n\u0012\u0013\u0012\u0011H\u0004¢\u0006\f\b\b\u0012\b\b\t\u0012\u0004\b\b(\u000b\u0012\n\u0012\b\u0012\u0004\u0012\u0002H\u00020\f\u0012\u0006\u0012\u0004\u0018\u00010\r0\u0007H\u0007¢\u0006\u0004\b\u000e\u0010\u000f\u001a\u0087\u0001\u0010\u0000\u001a\b\u0012\u0004\u0012\u0002H\u00020\u0001\"\u0004\b\u0000\u0010\u0003\"\u0004\b\u0001\u0010\u0004\"\u0004\b\u0002\u0010\u00022\f\u0010\u0005\u001a\b\u0012\u0004\u0012\u0002H\u00030\u00012\f\u0010\u0010\u001a\b\u0012\u0004\u0012\u0002H\u00040\u00012F\u0010\u0006\u001aB\b\u0001\u0012\u0013\u0012\u0011H\u0003¢\u0006\f\b\b\u0012\b\b\t\u0012\u0004\b\b(\n\u0012\u0013\u0012\u0011H\u0004¢\u0006\f\b\b\u0012\b\b\t\u0012\u0004\b\b(\u000b\u0012\n\u0012\b\u0012\u0004\u0012\u0002H\u00020\f\u0012\u0006\u0012\u0004\u0018\u00010\r0\u0007¢\u0006\u0002\u0010\u000f\u001a\u009a\u0001\u0010\u0011\u001a\b\u0012\u0004\u0012\u0002H\u00020\u0001\"\u0004\b\u0000\u0010\u0003\"\u0004\b\u0001\u0010\u0004\"\u0004\b\u0002\u0010\u0002*\b\u0012\u0004\u0012\u0002H\u00030\u00012\f\u0010\u0005\u001a\b\u0012\u0004\u0012\u0002H\u00040\u00012Y\b\u0001\u0010\u0006\u001aS\b\u0001\u0012\n\u0012\b\u0012\u0004\u0012\u0002H\u00020\u0013\u0012\u0013\u0012\u0011H\u0003¢\u0006\f\b\b\u0012\b\b\t\u0012\u0004\b\b(\n\u0012\u0013\u0012\u0011H\u0004¢\u0006\f\b\b\u0012\b\b\t\u0012\u0004\b\b(\u000b\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00140\f\u0012\u0006\u0012\u0004\u0018\u00010\r0\u0012¢\u0006\u0002\b\u0015H\u0007¢\u0006\u0004\b\u0016\u0010\u0017\u001a\u009a\u0001\u0010\u0011\u001a\b\u0012\u0004\u0012\u0002H\u00020\u0001\"\u0004\b\u0000\u0010\u0003\"\u0004\b\u0001\u0010\u0004\"\u0004\b\u0002\u0010\u00022\f\u0010\u0005\u001a\b\u0012\u0004\u0012\u0002H\u00030\u00012\f\u0010\u0010\u001a\b\u0012\u0004\u0012\u0002H\u00040\u00012Y\b\u0001\u0010\u0006\u001aS\b\u0001\u0012\n\u0012\b\u0012\u0004\u0012\u0002H\u00020\u0013\u0012\u0013\u0012\u0011H\u0003¢\u0006\f\b\b\u0012\b\b\t\u0012\u0004\b\b(\n\u0012\u0013\u0012\u0011H\u0004¢\u0006\f\b\b\u0012\b\b\t\u0012\u0004\b\b(\u000b\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00140\f\u0012\u0006\u0012\u0004\u0018\u00010\r0\u0012¢\u0006\u0002\b\u0015¢\u0006\u0002\u0010\u0017\u001a\u0085\u0001\u0010\u0000\u001a\b\u0012\u0004\u0012\u0002H\u00020\u0001\"\u0004\b\u0000\u0010\u0003\"\u0004\b\u0001\u0010\u0004\"\u0004\b\u0002\u0010\u0018\"\u0004\b\u0003\u0010\u00022\f\u0010\u0005\u001a\b\u0012\u0004\u0012\u0002H\u00030\u00012\f\u0010\u0010\u001a\b\u0012\u0004\u0012\u0002H\u00040\u00012\f\u0010\u0019\u001a\b\u0012\u0004\u0012\u0002H\u00180\u000120\b\u0001\u0010\u0006\u001a*\b\u0001\u0012\u0004\u0012\u0002H\u0003\u0012\u0004\u0012\u0002H\u0004\u0012\u0004\u0012\u0002H\u0018\u0012\n\u0012\b\u0012\u0004\u0012\u0002H\u00020\f\u0012\u0006\u0012\u0004\u0018\u00010\r0\u0012¢\u0006\u0002\u0010\u001a\u001a\u0096\u0001\u0010\u0011\u001a\b\u0012\u0004\u0012\u0002H\u00020\u0001\"\u0004\b\u0000\u0010\u0003\"\u0004\b\u0001\u0010\u0004\"\u0004\b\u0002\u0010\u0018\"\u0004\b\u0003\u0010\u00022\f\u0010\u0005\u001a\b\u0012\u0004\u0012\u0002H\u00030\u00012\f\u0010\u0010\u001a\b\u0012\u0004\u0012\u0002H\u00040\u00012\f\u0010\u0019\u001a\b\u0012\u0004\u0012\u0002H\u00180\u00012A\b\u0001\u0010\u0006\u001a;\b\u0001\u0012\n\u0012\b\u0012\u0004\u0012\u0002H\u00020\u0013\u0012\u0004\u0012\u0002H\u0003\u0012\u0004\u0012\u0002H\u0004\u0012\u0004\u0012\u0002H\u0018\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00140\f\u0012\u0006\u0012\u0004\u0018\u00010\r0\u001b¢\u0006\u0002\b\u0015¢\u0006\u0002\u0010\u001c\u001a\u009d\u0001\u0010\u0000\u001a\b\u0012\u0004\u0012\u0002H\u00020\u0001\"\u0004\b\u0000\u0010\u0003\"\u0004\b\u0001\u0010\u0004\"\u0004\b\u0002\u0010\u0018\"\u0004\b\u0003\u0010\u001d\"\u0004\b\u0004\u0010\u00022\f\u0010\u0005\u001a\b\u0012\u0004\u0012\u0002H\u00030\u00012\f\u0010\u0010\u001a\b\u0012\u0004\u0012\u0002H\u00040\u00012\f\u0010\u0019\u001a\b\u0012\u0004\u0012\u0002H\u00180\u00012\f\u0010\u001e\u001a\b\u0012\u0004\u0012\u0002H\u001d0\u000124\u0010\u0006\u001a0\b\u0001\u0012\u0004\u0012\u0002H\u0003\u0012\u0004\u0012\u0002H\u0004\u0012\u0004\u0012\u0002H\u0018\u0012\u0004\u0012\u0002H\u001d\u0012\n\u0012\b\u0012\u0004\u0012\u0002H\u00020\f\u0012\u0006\u0012\u0004\u0018\u00010\r0\u001b¢\u0006\u0002\u0010\u001f\u001a°\u0001\u0010\u0011\u001a\b\u0012\u0004\u0012\u0002H\u00020\u0001\"\u0004\b\u0000\u0010\u0003\"\u0004\b\u0001\u0010\u0004\"\u0004\b\u0002\u0010\u0018\"\u0004\b\u0003\u0010\u001d\"\u0004\b\u0004\u0010\u00022\f\u0010\u0005\u001a\b\u0012\u0004\u0012\u0002H\u00030\u00012\f\u0010\u0010\u001a\b\u0012\u0004\u0012\u0002H\u00040\u00012\f\u0010\u0019\u001a\b\u0012\u0004\u0012\u0002H\u00180\u00012\f\u0010\u001e\u001a\b\u0012\u0004\u0012\u0002H\u001d0\u00012G\b\u0001\u0010\u0006\u001aA\b\u0001\u0012\n\u0012\b\u0012\u0004\u0012\u0002H\u00020\u0013\u0012\u0004\u0012\u0002H\u0003\u0012\u0004\u0012\u0002H\u0004\u0012\u0004\u0012\u0002H\u0018\u0012\u0004\u0012\u0002H\u001d\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00140\f\u0012\u0006\u0012\u0004\u0018\u00010\r0 ¢\u0006\u0002\b\u0015¢\u0006\u0002\u0010!\u001a·\u0001\u0010\u0000\u001a\b\u0012\u0004\u0012\u0002H\u00020\u0001\"\u0004\b\u0000\u0010\u0003\"\u0004\b\u0001\u0010\u0004\"\u0004\b\u0002\u0010\u0018\"\u0004\b\u0003\u0010\u001d\"\u0004\b\u0004\u0010\"\"\u0004\b\u0005\u0010\u00022\f\u0010\u0005\u001a\b\u0012\u0004\u0012\u0002H\u00030\u00012\f\u0010\u0010\u001a\b\u0012\u0004\u0012\u0002H\u00040\u00012\f\u0010\u0019\u001a\b\u0012\u0004\u0012\u0002H\u00180\u00012\f\u0010\u001e\u001a\b\u0012\u0004\u0012\u0002H\u001d0\u00012\f\u0010#\u001a\b\u0012\u0004\u0012\u0002H\"0\u00012:\u0010\u0006\u001a6\b\u0001\u0012\u0004\u0012\u0002H\u0003\u0012\u0004\u0012\u0002H\u0004\u0012\u0004\u0012\u0002H\u0018\u0012\u0004\u0012\u0002H\u001d\u0012\u0004\u0012\u0002H\"\u0012\n\u0012\b\u0012\u0004\u0012\u0002H\u00020\f\u0012\u0006\u0012\u0004\u0018\u00010\r0 ¢\u0006\u0002\u0010$\u001aÊ\u0001\u0010\u0011\u001a\b\u0012\u0004\u0012\u0002H\u00020\u0001\"\u0004\b\u0000\u0010\u0003\"\u0004\b\u0001\u0010\u0004\"\u0004\b\u0002\u0010\u0018\"\u0004\b\u0003\u0010\u001d\"\u0004\b\u0004\u0010\"\"\u0004\b\u0005\u0010\u00022\f\u0010\u0005\u001a\b\u0012\u0004\u0012\u0002H\u00030\u00012\f\u0010\u0010\u001a\b\u0012\u0004\u0012\u0002H\u00040\u00012\f\u0010\u0019\u001a\b\u0012\u0004\u0012\u0002H\u00180\u00012\f\u0010\u001e\u001a\b\u0012\u0004\u0012\u0002H\u001d0\u00012\f\u0010#\u001a\b\u0012\u0004\u0012\u0002H\"0\u00012M\b\u0001\u0010\u0006\u001aG\b\u0001\u0012\n\u0012\b\u0012\u0004\u0012\u0002H\u00020\u0013\u0012\u0004\u0012\u0002H\u0003\u0012\u0004\u0012\u0002H\u0004\u0012\u0004\u0012\u0002H\u0018\u0012\u0004\u0012\u0002H\u001d\u0012\u0004\u0012\u0002H\"\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00140\f\u0012\u0006\u0012\u0004\u0018\u00010\r0%¢\u0006\u0002\b\u0015¢\u0006\u0002\u0010&\u001an\u0010\u0000\u001a\b\u0012\u0004\u0012\u0002H\u00020\u0001\"\u0006\b\u0000\u0010'\u0018\u0001\"\u0004\b\u0001\u0010\u00022\u001e\u0010(\u001a\u0010\u0012\f\b\u0001\u0012\b\u0012\u0004\u0012\u0002H'0\u00010)\"\b\u0012\u0004\u0012\u0002H'0\u00012*\b\u0004\u0010\u0006\u001a$\b\u0001\u0012\n\u0012\b\u0012\u0004\u0012\u0002H'0)\u0012\n\u0012\b\u0012\u0004\u0012\u0002H\u00020\f\u0012\u0006\u0012\u0004\u0018\u00010\r0*H\u0086\b¢\u0006\u0002\u0010+\u001a\u007f\u0010\u0011\u001a\b\u0012\u0004\u0012\u0002H\u00020\u0001\"\u0006\b\u0000\u0010'\u0018\u0001\"\u0004\b\u0001\u0010\u00022\u001e\u0010(\u001a\u0010\u0012\f\b\u0001\u0012\b\u0012\u0004\u0012\u0002H'0\u00010)\"\b\u0012\u0004\u0012\u0002H'0\u00012;\b\u0005\u0010\u0006\u001a5\b\u0001\u0012\n\u0012\b\u0012\u0004\u0012\u0002H\u00020\u0013\u0012\n\u0012\b\u0012\u0004\u0012\u0002H'0)\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00140\f\u0012\u0006\u0012\u0004\u0018\u00010\r0\u0007¢\u0006\u0002\b\u0015H\u0086\b¢\u0006\u0002\u0010,\u001ap\u0010-\u001a\b\u0012\u0004\u0012\u0002H\u00020\u0001\"\u0006\b\u0000\u0010'\u0018\u0001\"\u0004\b\u0001\u0010\u00022\u001e\u0010(\u001a\u0010\u0012\f\b\u0001\u0012\b\u0012\u0004\u0012\u0002H'0\u00010)\"\b\u0012\u0004\u0012\u0002H'0\u00012*\b\u0004\u0010\u0006\u001a$\b\u0001\u0012\n\u0012\b\u0012\u0004\u0012\u0002H'0)\u0012\n\u0012\b\u0012\u0004\u0012\u0002H\u00020\f\u0012\u0006\u0012\u0004\u0018\u00010\r0*H\u0082\b¢\u0006\u0004\b.\u0010+\u001a\u0081\u0001\u0010/\u001a\b\u0012\u0004\u0012\u0002H\u00020\u0001\"\u0006\b\u0000\u0010'\u0018\u0001\"\u0004\b\u0001\u0010\u00022\u001e\u0010(\u001a\u0010\u0012\f\b\u0001\u0012\b\u0012\u0004\u0012\u0002H'0\u00010)\"\b\u0012\u0004\u0012\u0002H'0\u00012;\b\u0005\u0010\u0006\u001a5\b\u0001\u0012\n\u0012\b\u0012\u0004\u0012\u0002H\u00020\u0013\u0012\n\u0012\b\u0012\u0004\u0012\u0002H'0)\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00140\f\u0012\u0006\u0012\u0004\u0018\u00010\r0\u0007¢\u0006\u0002\b\u0015H\u0082\b¢\u0006\u0004\b0\u0010,\u001a!\u00101\u001a\u0010\u0012\f\u0012\n\u0012\u0004\u0012\u0002H'\u0018\u00010)02\"\u0004\b\u0000\u0010'H\u0002¢\u0006\u0002\b3\u001ab\u0010\u0000\u001a\b\u0012\u0004\u0012\u0002H\u00020\u0001\"\u0006\b\u0000\u0010'\u0018\u0001\"\u0004\b\u0001\u0010\u00022\u0012\u0010(\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u0002H'0\u0001042*\b\u0004\u0010\u0006\u001a$\b\u0001\u0012\n\u0012\b\u0012\u0004\u0012\u0002H'0)\u0012\n\u0012\b\u0012\u0004\u0012\u0002H\u00020\f\u0012\u0006\u0012\u0004\u0018\u00010\r0*H\u0086\b¢\u0006\u0002\u00105\u001as\u0010\u0011\u001a\b\u0012\u0004\u0012\u0002H\u00020\u0001\"\u0006\b\u0000\u0010'\u0018\u0001\"\u0004\b\u0001\u0010\u00022\u0012\u0010(\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u0002H'0\u0001042;\b\u0005\u0010\u0006\u001a5\b\u0001\u0012\n\u0012\b\u0012\u0004\u0012\u0002H\u00020\u0013\u0012\n\u0012\b\u0012\u0004\u0012\u0002H'0)\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00140\f\u0012\u0006\u0012\u0004\u0018\u00010\r0\u0007¢\u0006\u0002\b\u0015H\u0086\b¢\u0006\u0002\u00106\u001ae\u00107\u001a\b\u0012\u0004\u0012\u0002H\u00020\u0001\"\u0004\b\u0000\u0010\u0003\"\u0004\b\u0001\u0010\u0004\"\u0004\b\u0002\u0010\u0002*\b\u0012\u0004\u0012\u0002H\u00030\u00012\f\u00108\u001a\b\u0012\u0004\u0012\u0002H\u00040\u00012(\u0010\u0006\u001a$\b\u0001\u0012\u0004\u0012\u0002H\u0003\u0012\u0004\u0012\u0002H\u0004\u0012\n\u0012\b\u0012\u0004\u0012\u0002H\u00020\f\u0012\u0006\u0012\u0004\u0018\u00010\r0\u0007¢\u0006\u0002\u0010\u000f¨\u00069"}, d2 = {"combine", "Lkotlinx/coroutines/flow/Flow;", "R", "T1", "T2", "flow", "transform", "Lkotlin/Function3;", "Lkotlin/ParameterName;", "name", HtmlParser.LinkCardElement.TAG, "b", "Lkotlin/coroutines/Continuation;", "", "flowCombine", "(Lkotlinx/coroutines/flow/Flow;Lkotlinx/coroutines/flow/Flow;Lkotlin/jvm/functions/Function3;)Lkotlinx/coroutines/flow/Flow;", "flow2", "combineTransform", "Lkotlin/Function4;", "Lkotlinx/coroutines/flow/FlowCollector;", "", "Lkotlin/ExtensionFunctionType;", "flowCombineTransform", "(Lkotlinx/coroutines/flow/Flow;Lkotlinx/coroutines/flow/Flow;Lkotlin/jvm/functions/Function4;)Lkotlinx/coroutines/flow/Flow;", "T3", "flow3", "(Lkotlinx/coroutines/flow/Flow;Lkotlinx/coroutines/flow/Flow;Lkotlinx/coroutines/flow/Flow;Lkotlin/jvm/functions/Function4;)Lkotlinx/coroutines/flow/Flow;", "Lkotlin/Function5;", "(Lkotlinx/coroutines/flow/Flow;Lkotlinx/coroutines/flow/Flow;Lkotlinx/coroutines/flow/Flow;Lkotlin/jvm/functions/Function5;)Lkotlinx/coroutines/flow/Flow;", "T4", "flow4", "(Lkotlinx/coroutines/flow/Flow;Lkotlinx/coroutines/flow/Flow;Lkotlinx/coroutines/flow/Flow;Lkotlinx/coroutines/flow/Flow;Lkotlin/jvm/functions/Function5;)Lkotlinx/coroutines/flow/Flow;", "Lkotlin/Function6;", "(Lkotlinx/coroutines/flow/Flow;Lkotlinx/coroutines/flow/Flow;Lkotlinx/coroutines/flow/Flow;Lkotlinx/coroutines/flow/Flow;Lkotlin/jvm/functions/Function6;)Lkotlinx/coroutines/flow/Flow;", "T5", "flow5", "(Lkotlinx/coroutines/flow/Flow;Lkotlinx/coroutines/flow/Flow;Lkotlinx/coroutines/flow/Flow;Lkotlinx/coroutines/flow/Flow;Lkotlinx/coroutines/flow/Flow;Lkotlin/jvm/functions/Function6;)Lkotlinx/coroutines/flow/Flow;", "Lkotlin/Function7;", "(Lkotlinx/coroutines/flow/Flow;Lkotlinx/coroutines/flow/Flow;Lkotlinx/coroutines/flow/Flow;Lkotlinx/coroutines/flow/Flow;Lkotlinx/coroutines/flow/Flow;Lkotlin/jvm/functions/Function7;)Lkotlinx/coroutines/flow/Flow;", ExifInterface.GPS_DIRECTION_TRUE, "flows", "", "Lkotlin/Function2;", "([Lkotlinx/coroutines/flow/Flow;Lkotlin/jvm/functions/Function2;)Lkotlinx/coroutines/flow/Flow;", "([Lkotlinx/coroutines/flow/Flow;Lkotlin/jvm/functions/Function3;)Lkotlinx/coroutines/flow/Flow;", "combineUnsafe", "combineUnsafe$FlowKt__ZipKt", "combineTransformUnsafe", "combineTransformUnsafe$FlowKt__ZipKt", "nullArrayFactory", "Lkotlin/Function0;", "nullArrayFactory$FlowKt__ZipKt", "", "(Ljava/lang/Iterable;Lkotlin/jvm/functions/Function2;)Lkotlinx/coroutines/flow/Flow;", "(Ljava/lang/Iterable;Lkotlin/jvm/functions/Function3;)Lkotlinx/coroutines/flow/Flow;", "zip", RemoteDeviceInfo.MANUFACTURER_OTHER, "kotlinx-coroutines-core"}, k = 5, mv = {2, 1, 0}, xi = 48, xs = "kotlinx/coroutines/flow/FlowKt")
final /* synthetic */ class FlowKt__ZipKt {
    public static final /* synthetic */ Function0 access$nullArrayFactory() {
        return nullArrayFactory$FlowKt__ZipKt();
    }

    public static final <T1, T2, R> Flow<R> combine(Flow<? extends T1> flow, Flow<? extends T2> flow2, Function3<? super T1, ? super T2, ? super Continuation<? super R>, ? extends Object> function3) {
        return FlowKt.flowCombine(flow, flow2, function3);
    }

    public static final <T1, T2, R> Flow<R> flowCombineTransform(Flow<? extends T1> flow, Flow<? extends T2> flow2, Function4<? super FlowCollector<? super R>, ? super T1, ? super T2, ? super Continuation<? super Unit>, ? extends Object> function4) {
        return FlowKt.flow(new FlowKt__ZipKt$combineTransform$$inlined$combineTransformUnsafe$FlowKt__ZipKt$1(new Flow[]{flow, flow2}, null, function4));
    }

    public static final <T1, T2, R> Flow<R> combineTransform(Flow<? extends T1> flow, Flow<? extends T2> flow2, Function4<? super FlowCollector<? super R>, ? super T1, ? super T2, ? super Continuation<? super Unit>, ? extends Object> function4) {
        return FlowKt.flow(new FlowKt__ZipKt$combineTransform$$inlined$combineTransformUnsafe$FlowKt__ZipKt$2(new Flow[]{flow, flow2}, null, function4));
    }

    public static final <T1, T2, T3, R> Flow<R> combine(Flow<? extends T1> flow, Flow<? extends T2> flow2, Flow<? extends T3> flow3, final Function4<? super T1, ? super T2, ? super T3, ? super Continuation<? super R>, ? extends Object> function4) {
        final Flow[] flowArr = {flow, flow2, flow3};
        return new Flow<R>() { // from class: kotlinx.coroutines.flow.FlowKt__ZipKt$combine$$inlined$combineUnsafe$FlowKt__ZipKt$1
            @Override // kotlinx.coroutines.flow.Flow
            public Object collect(FlowCollector flowCollector, Continuation continuation) {
                Object objCombineInternal = CombineKt.combineInternal(flowCollector, flowArr, FlowKt__ZipKt.nullArrayFactory$FlowKt__ZipKt(), new AnonymousClass2(null, function4), continuation);
                return objCombineInternal == IntrinsicsKt.getCOROUTINE_SUSPENDED() ? objCombineInternal : Unit.INSTANCE;
            }

            /* JADX INFO: renamed from: kotlinx.coroutines.flow.FlowKt__ZipKt$combine$$inlined$combineUnsafe$FlowKt__ZipKt$1$2, reason: invalid class name */
            /* JADX INFO: compiled from: Zip.kt */
            @Metadata(d1 = {"\u0000\u0016\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0011\n\u0000\u0010\u0000\u001a\u00020\u0001\"\u0004\b\u0000\u0010\u0002\"\u0006\b\u0001\u0010\u0003\u0018\u0001*\b\u0012\u0004\u0012\u0002H\u00020\u00042\f\u0010\u0005\u001a\b\u0012\u0004\u0012\u0002H\u00030\u0006H\n¨\u0006\u0007"}, d2 = {"<anonymous>", "", "R", ExifInterface.GPS_DIRECTION_TRUE, "Lkotlinx/coroutines/flow/FlowCollector;", "it", "", "kotlinx/coroutines/flow/FlowKt__ZipKt$combineUnsafe$1$1"}, k = 3, mv = {2, 1, 0}, xi = 48)
            @DebugMetadata(c = "kotlinx.coroutines.flow.FlowKt__ZipKt$combine$$inlined$combineUnsafe$FlowKt__ZipKt$1$2", f = "Zip.kt", i = {}, l = {R2.attr.actionBarFavoriteIcon, R2.attr.actionBarExpandTabBarStyle}, m = "invokeSuspend", n = {}, s = {})
            public static final class AnonymousClass2 extends SuspendLambda implements Function3<FlowCollector<? super R>, Object[], Continuation<? super Unit>, Object> {
                final /* synthetic */ Function4 $transform$inlined;
                private /* synthetic */ Object L$0;
                /* synthetic */ Object L$1;
                int label;

                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                public AnonymousClass2(Continuation continuation, Function4 function4) {
                    super(3, continuation);
                    this.$transform$inlined = function4;
                }

                @Override // kotlin.jvm.functions.Function3
                public final Object invoke(FlowCollector<? super R> flowCollector, Object[] objArr, Continuation<? super Unit> continuation) {
                    AnonymousClass2 anonymousClass2 = new AnonymousClass2(continuation, this.$transform$inlined);
                    anonymousClass2.L$0 = flowCollector;
                    anonymousClass2.L$1 = objArr;
                    return anonymousClass2.invokeSuspend(Unit.INSTANCE);
                }

                /* JADX WARN: Code restructure failed: missing block: B:14:0x0051, code lost:
                
                    if (r1.emit(r8, r7) == r0) goto L15;
                 */
                @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
                /*
                    Code decompiled incorrectly, please refer to instructions dump.
                    To view partially-correct code enable 'Show inconsistent code' option in preferences
                */
                public final java.lang.Object invokeSuspend(java.lang.Object r8) {
                    /*
                        r7 = this;
                        java.lang.Object r0 = kotlin.coroutines.intrinsics.IntrinsicsKt.getCOROUTINE_SUSPENDED()
                        int r1 = r7.label
                        r2 = 2
                        r3 = 1
                        if (r1 == 0) goto L22
                        if (r1 == r3) goto L1a
                        if (r1 != r2) goto L12
                        kotlin.ResultKt.throwOnFailure(r8)
                        goto L54
                    L12:
                        java.lang.IllegalStateException r7 = new java.lang.IllegalStateException
                        java.lang.String r8 = "call to 'resume' before 'invoke' with coroutine"
                        r7.<init>(r8)
                        throw r7
                    L1a:
                        java.lang.Object r1 = r7.L$0
                        kotlinx.coroutines.flow.FlowCollector r1 = (kotlinx.coroutines.flow.FlowCollector) r1
                        kotlin.ResultKt.throwOnFailure(r8)
                        goto L45
                    L22:
                        kotlin.ResultKt.throwOnFailure(r8)
                        java.lang.Object r8 = r7.L$0
                        r1 = r8
                        kotlinx.coroutines.flow.FlowCollector r1 = (kotlinx.coroutines.flow.FlowCollector) r1
                        java.lang.Object r8 = r7.L$1
                        java.lang.Object[] r8 = (java.lang.Object[]) r8
                        r4 = r7
                        kotlin.coroutines.Continuation r4 = (kotlin.coroutines.Continuation) r4
                        kotlin.jvm.functions.Function4 r4 = r7.$transform$inlined
                        r5 = 0
                        r5 = r8[r5]
                        r6 = r8[r3]
                        r8 = r8[r2]
                        r7.L$0 = r1
                        r7.label = r3
                        java.lang.Object r8 = r4.invoke(r5, r6, r8, r7)
                        if (r8 != r0) goto L45
                        goto L53
                    L45:
                        r3 = r7
                        kotlin.coroutines.Continuation r3 = (kotlin.coroutines.Continuation) r3
                        r4 = 0
                        r7.L$0 = r4
                        r7.label = r2
                        java.lang.Object r7 = r1.emit(r8, r3)
                        if (r7 != r0) goto L54
                    L53:
                        return r0
                    L54:
                        kotlin.Unit r7 = kotlin.Unit.INSTANCE
                        return r7
                    */
                    throw new UnsupportedOperationException("Method not decompiled: kotlinx.coroutines.flow.FlowKt__ZipKt$combine$$inlined$combineUnsafe$FlowKt__ZipKt$1.AnonymousClass2.invokeSuspend(java.lang.Object):java.lang.Object");
                }
            }
        };
    }

    public static final <T1, T2, T3, R> Flow<R> combineTransform(Flow<? extends T1> flow, Flow<? extends T2> flow2, Flow<? extends T3> flow3, Function5<? super FlowCollector<? super R>, ? super T1, ? super T2, ? super T3, ? super Continuation<? super Unit>, ? extends Object> function5) {
        return FlowKt.flow(new FlowKt__ZipKt$combineTransform$$inlined$combineTransformUnsafe$FlowKt__ZipKt$3(new Flow[]{flow, flow2, flow3}, null, function5));
    }

    public static final <T1, T2, T3, T4, R> Flow<R> combine(Flow<? extends T1> flow, Flow<? extends T2> flow2, Flow<? extends T3> flow3, Flow<? extends T4> flow4, final Function5<? super T1, ? super T2, ? super T3, ? super T4, ? super Continuation<? super R>, ? extends Object> function5) {
        final Flow[] flowArr = {flow, flow2, flow3, flow4};
        return new Flow<R>() { // from class: kotlinx.coroutines.flow.FlowKt__ZipKt$combine$$inlined$combineUnsafe$FlowKt__ZipKt$2
            @Override // kotlinx.coroutines.flow.Flow
            public Object collect(FlowCollector flowCollector, Continuation continuation) {
                Object objCombineInternal = CombineKt.combineInternal(flowCollector, flowArr, FlowKt__ZipKt.nullArrayFactory$FlowKt__ZipKt(), new AnonymousClass2(null, function5), continuation);
                return objCombineInternal == IntrinsicsKt.getCOROUTINE_SUSPENDED() ? objCombineInternal : Unit.INSTANCE;
            }

            /* JADX INFO: renamed from: kotlinx.coroutines.flow.FlowKt__ZipKt$combine$$inlined$combineUnsafe$FlowKt__ZipKt$2$2, reason: invalid class name */
            /* JADX INFO: compiled from: Zip.kt */
            @Metadata(d1 = {"\u0000\u0016\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0011\n\u0000\u0010\u0000\u001a\u00020\u0001\"\u0004\b\u0000\u0010\u0002\"\u0006\b\u0001\u0010\u0003\u0018\u0001*\b\u0012\u0004\u0012\u0002H\u00020\u00042\f\u0010\u0005\u001a\b\u0012\u0004\u0012\u0002H\u00030\u0006H\n¨\u0006\u0007"}, d2 = {"<anonymous>", "", "R", ExifInterface.GPS_DIRECTION_TRUE, "Lkotlinx/coroutines/flow/FlowCollector;", "it", "", "kotlinx/coroutines/flow/FlowKt__ZipKt$combineUnsafe$1$1"}, k = 3, mv = {2, 1, 0}, xi = 48)
            @DebugMetadata(c = "kotlinx.coroutines.flow.FlowKt__ZipKt$combine$$inlined$combineUnsafe$FlowKt__ZipKt$2$2", f = "Zip.kt", i = {}, l = {R2.attr.actionBarFavoriteIcon, R2.attr.actionBarExpandTabBarStyle}, m = "invokeSuspend", n = {}, s = {})
            public static final class AnonymousClass2 extends SuspendLambda implements Function3<FlowCollector<? super R>, Object[], Continuation<? super Unit>, Object> {
                final /* synthetic */ Function5 $transform$inlined;
                private /* synthetic */ Object L$0;
                /* synthetic */ Object L$1;
                int label;

                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                public AnonymousClass2(Continuation continuation, Function5 function5) {
                    super(3, continuation);
                    this.$transform$inlined = function5;
                }

                @Override // kotlin.jvm.functions.Function3
                public final Object invoke(FlowCollector<? super R> flowCollector, Object[] objArr, Continuation<? super Unit> continuation) {
                    AnonymousClass2 anonymousClass2 = new AnonymousClass2(continuation, this.$transform$inlined);
                    anonymousClass2.L$0 = flowCollector;
                    anonymousClass2.L$1 = objArr;
                    return anonymousClass2.invokeSuspend(Unit.INSTANCE);
                }

                /* JADX WARN: Code restructure failed: missing block: B:14:0x0056, code lost:
                
                    if (r1.emit(r12, r10) == r0) goto L15;
                 */
                @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
                /*
                    Code decompiled incorrectly, please refer to instructions dump.
                    To view partially-correct code enable 'Show inconsistent code' option in preferences
                */
                public final java.lang.Object invokeSuspend(java.lang.Object r12) {
                    /*
                        r11 = this;
                        java.lang.Object r0 = kotlin.coroutines.intrinsics.IntrinsicsKt.getCOROUTINE_SUSPENDED()
                        int r1 = r11.label
                        r2 = 2
                        r3 = 1
                        if (r1 == 0) goto L23
                        if (r1 == r3) goto L1a
                        if (r1 != r2) goto L12
                        kotlin.ResultKt.throwOnFailure(r12)
                        goto L59
                    L12:
                        java.lang.IllegalStateException r11 = new java.lang.IllegalStateException
                        java.lang.String r12 = "call to 'resume' before 'invoke' with coroutine"
                        r11.<init>(r12)
                        throw r11
                    L1a:
                        java.lang.Object r1 = r11.L$0
                        kotlinx.coroutines.flow.FlowCollector r1 = (kotlinx.coroutines.flow.FlowCollector) r1
                        kotlin.ResultKt.throwOnFailure(r12)
                        r10 = r11
                        goto L4a
                    L23:
                        kotlin.ResultKt.throwOnFailure(r12)
                        java.lang.Object r12 = r11.L$0
                        r1 = r12
                        kotlinx.coroutines.flow.FlowCollector r1 = (kotlinx.coroutines.flow.FlowCollector) r1
                        java.lang.Object r12 = r11.L$1
                        java.lang.Object[] r12 = (java.lang.Object[]) r12
                        r4 = r11
                        kotlin.coroutines.Continuation r4 = (kotlin.coroutines.Continuation) r4
                        kotlin.jvm.functions.Function5 r5 = r11.$transform$inlined
                        r4 = 0
                        r6 = r12[r4]
                        r7 = r12[r3]
                        r8 = r12[r2]
                        r4 = 3
                        r9 = r12[r4]
                        r11.L$0 = r1
                        r11.label = r3
                        r10 = r11
                        java.lang.Object r12 = r5.invoke(r6, r7, r8, r9, r10)
                        if (r12 != r0) goto L4a
                        goto L58
                    L4a:
                        r11 = r10
                        kotlin.coroutines.Continuation r11 = (kotlin.coroutines.Continuation) r11
                        r3 = 0
                        r10.L$0 = r3
                        r10.label = r2
                        java.lang.Object r11 = r1.emit(r12, r11)
                        if (r11 != r0) goto L59
                    L58:
                        return r0
                    L59:
                        kotlin.Unit r11 = kotlin.Unit.INSTANCE
                        return r11
                    */
                    throw new UnsupportedOperationException("Method not decompiled: kotlinx.coroutines.flow.FlowKt__ZipKt$combine$$inlined$combineUnsafe$FlowKt__ZipKt$2.AnonymousClass2.invokeSuspend(java.lang.Object):java.lang.Object");
                }
            }
        };
    }

    public static final <T1, T2, T3, T4, R> Flow<R> combineTransform(Flow<? extends T1> flow, Flow<? extends T2> flow2, Flow<? extends T3> flow3, Flow<? extends T4> flow4, Function6<? super FlowCollector<? super R>, ? super T1, ? super T2, ? super T3, ? super T4, ? super Continuation<? super Unit>, ? extends Object> function6) {
        return FlowKt.flow(new FlowKt__ZipKt$combineTransform$$inlined$combineTransformUnsafe$FlowKt__ZipKt$4(new Flow[]{flow, flow2, flow3, flow4}, null, function6));
    }

    public static final <T1, T2, T3, T4, T5, R> Flow<R> combine(Flow<? extends T1> flow, Flow<? extends T2> flow2, Flow<? extends T3> flow3, Flow<? extends T4> flow4, Flow<? extends T5> flow5, final Function6<? super T1, ? super T2, ? super T3, ? super T4, ? super T5, ? super Continuation<? super R>, ? extends Object> function6) {
        final Flow[] flowArr = {flow, flow2, flow3, flow4, flow5};
        return new Flow<R>() { // from class: kotlinx.coroutines.flow.FlowKt__ZipKt$combine$$inlined$combineUnsafe$FlowKt__ZipKt$3
            @Override // kotlinx.coroutines.flow.Flow
            public Object collect(FlowCollector flowCollector, Continuation continuation) {
                Object objCombineInternal = CombineKt.combineInternal(flowCollector, flowArr, FlowKt__ZipKt.nullArrayFactory$FlowKt__ZipKt(), new AnonymousClass2(null, function6), continuation);
                return objCombineInternal == IntrinsicsKt.getCOROUTINE_SUSPENDED() ? objCombineInternal : Unit.INSTANCE;
            }

            /* JADX INFO: renamed from: kotlinx.coroutines.flow.FlowKt__ZipKt$combine$$inlined$combineUnsafe$FlowKt__ZipKt$3$2, reason: invalid class name */
            /* JADX INFO: compiled from: Zip.kt */
            @Metadata(d1 = {"\u0000\u0016\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0011\n\u0000\u0010\u0000\u001a\u00020\u0001\"\u0004\b\u0000\u0010\u0002\"\u0006\b\u0001\u0010\u0003\u0018\u0001*\b\u0012\u0004\u0012\u0002H\u00020\u00042\f\u0010\u0005\u001a\b\u0012\u0004\u0012\u0002H\u00030\u0006H\n¨\u0006\u0007"}, d2 = {"<anonymous>", "", "R", ExifInterface.GPS_DIRECTION_TRUE, "Lkotlinx/coroutines/flow/FlowCollector;", "it", "", "kotlinx/coroutines/flow/FlowKt__ZipKt$combineUnsafe$1$1"}, k = 3, mv = {2, 1, 0}, xi = 48)
            @DebugMetadata(c = "kotlinx.coroutines.flow.FlowKt__ZipKt$combine$$inlined$combineUnsafe$FlowKt__ZipKt$3$2", f = "Zip.kt", i = {}, l = {R2.attr.actionBarFavoriteIcon, R2.attr.actionBarExpandTabBarStyle}, m = "invokeSuspend", n = {}, s = {})
            public static final class AnonymousClass2 extends SuspendLambda implements Function3<FlowCollector<? super R>, Object[], Continuation<? super Unit>, Object> {
                final /* synthetic */ Function6 $transform$inlined;
                private /* synthetic */ Object L$0;
                /* synthetic */ Object L$1;
                int label;

                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                public AnonymousClass2(Continuation continuation, Function6 function6) {
                    super(3, continuation);
                    this.$transform$inlined = function6;
                }

                @Override // kotlin.jvm.functions.Function3
                public final Object invoke(FlowCollector<? super R> flowCollector, Object[] objArr, Continuation<? super Unit> continuation) {
                    AnonymousClass2 anonymousClass2 = new AnonymousClass2(continuation, this.$transform$inlined);
                    anonymousClass2.L$0 = flowCollector;
                    anonymousClass2.L$1 = objArr;
                    return anonymousClass2.invokeSuspend(Unit.INSTANCE);
                }

                /* JADX WARN: Code restructure failed: missing block: B:14:0x0059, code lost:
                
                    if (r1.emit(r13, r11) == r0) goto L15;
                 */
                @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
                /*
                    Code decompiled incorrectly, please refer to instructions dump.
                    To view partially-correct code enable 'Show inconsistent code' option in preferences
                */
                public final java.lang.Object invokeSuspend(java.lang.Object r13) {
                    /*
                        r12 = this;
                        java.lang.Object r0 = kotlin.coroutines.intrinsics.IntrinsicsKt.getCOROUTINE_SUSPENDED()
                        int r1 = r12.label
                        r2 = 2
                        r3 = 1
                        if (r1 == 0) goto L23
                        if (r1 == r3) goto L1a
                        if (r1 != r2) goto L12
                        kotlin.ResultKt.throwOnFailure(r13)
                        goto L5c
                    L12:
                        java.lang.IllegalStateException r12 = new java.lang.IllegalStateException
                        java.lang.String r13 = "call to 'resume' before 'invoke' with coroutine"
                        r12.<init>(r13)
                        throw r12
                    L1a:
                        java.lang.Object r1 = r12.L$0
                        kotlinx.coroutines.flow.FlowCollector r1 = (kotlinx.coroutines.flow.FlowCollector) r1
                        kotlin.ResultKt.throwOnFailure(r13)
                        r11 = r12
                        goto L4d
                    L23:
                        kotlin.ResultKt.throwOnFailure(r13)
                        java.lang.Object r13 = r12.L$0
                        r1 = r13
                        kotlinx.coroutines.flow.FlowCollector r1 = (kotlinx.coroutines.flow.FlowCollector) r1
                        java.lang.Object r13 = r12.L$1
                        java.lang.Object[] r13 = (java.lang.Object[]) r13
                        r4 = r12
                        kotlin.coroutines.Continuation r4 = (kotlin.coroutines.Continuation) r4
                        kotlin.jvm.functions.Function6 r5 = r12.$transform$inlined
                        r4 = 0
                        r6 = r13[r4]
                        r7 = r13[r3]
                        r8 = r13[r2]
                        r4 = 3
                        r9 = r13[r4]
                        r4 = 4
                        r10 = r13[r4]
                        r12.L$0 = r1
                        r12.label = r3
                        r11 = r12
                        java.lang.Object r13 = r5.invoke(r6, r7, r8, r9, r10, r11)
                        if (r13 != r0) goto L4d
                        goto L5b
                    L4d:
                        r12 = r11
                        kotlin.coroutines.Continuation r12 = (kotlin.coroutines.Continuation) r12
                        r3 = 0
                        r11.L$0 = r3
                        r11.label = r2
                        java.lang.Object r12 = r1.emit(r13, r12)
                        if (r12 != r0) goto L5c
                    L5b:
                        return r0
                    L5c:
                        kotlin.Unit r12 = kotlin.Unit.INSTANCE
                        return r12
                    */
                    throw new UnsupportedOperationException("Method not decompiled: kotlinx.coroutines.flow.FlowKt__ZipKt$combine$$inlined$combineUnsafe$FlowKt__ZipKt$3.AnonymousClass2.invokeSuspend(java.lang.Object):java.lang.Object");
                }
            }
        };
    }

    public static final <T1, T2, T3, T4, T5, R> Flow<R> combineTransform(Flow<? extends T1> flow, Flow<? extends T2> flow2, Flow<? extends T3> flow3, Flow<? extends T4> flow4, Flow<? extends T5> flow5, Function7<? super FlowCollector<? super R>, ? super T1, ? super T2, ? super T3, ? super T4, ? super T5, ? super Continuation<? super Unit>, ? extends Object> function7) {
        return FlowKt.flow(new FlowKt__ZipKt$combineTransform$$inlined$combineTransformUnsafe$FlowKt__ZipKt$5(new Flow[]{flow, flow2, flow3, flow4, flow5}, null, function7));
    }

    /* JADX INFO: Add missing generic type declarations: [R] */
    /* JADX INFO: renamed from: kotlinx.coroutines.flow.FlowKt__ZipKt$combineTransform$6, reason: invalid class name */
    /* JADX INFO: compiled from: Zip.kt */
    @Metadata(d1 = {"\u0000\f\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\u0010\u0000\u001a\u00020\u0001\"\u0004\b\u0000\u0010\u0002*\b\u0012\u0004\u0012\u0002H\u00020\u0003H\n"}, d2 = {"<anonymous>", "", "R", "Lkotlinx/coroutines/flow/FlowCollector;"}, k = 3, mv = {2, 1, 0}, xi = 176)
    @DebugMetadata(c = "kotlinx.coroutines.flow.FlowKt__ZipKt$combineTransform$6", f = "Zip.kt", i = {}, l = {R2.attr.actionBarCopyIcon}, m = "invokeSuspend", n = {}, s = {})
    public static final class AnonymousClass6<R> extends SuspendLambda implements Function2<FlowCollector<? super R>, Continuation<? super Unit>, Object> {
        final /* synthetic */ Flow<T>[] $flows;
        final /* synthetic */ Function3<FlowCollector<? super R>, T[], Continuation<? super Unit>, Object> $transform;
        private /* synthetic */ Object L$0;
        int label;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        /* JADX WARN: Multi-variable type inference failed */
        public AnonymousClass6(Flow<? extends T>[] flowArr, Function3<? super FlowCollector<? super R>, ? super T[], ? super Continuation<? super Unit>, ? extends Object> function3, Continuation<? super AnonymousClass6> continuation) {
            super(2, continuation);
            this.$flows = flowArr;
            this.$transform = function3;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
            AnonymousClass6 anonymousClass6 = new AnonymousClass6(this.$flows, this.$transform, continuation);
            anonymousClass6.L$0 = obj;
            return anonymousClass6;
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(FlowCollector<? super R> flowCollector, Continuation<? super Unit> continuation) {
            return ((AnonymousClass6) create(flowCollector, continuation)).invokeSuspend(Unit.INSTANCE);
        }

        /* JADX INFO: Add missing generic type declarations: [T] */
        /* JADX INFO: renamed from: kotlinx.coroutines.flow.FlowKt__ZipKt$combineTransform$6$1, reason: invalid class name */
        /* JADX INFO: compiled from: Zip.kt */
        @Metadata(k = 3, mv = {2, 1, 0}, xi = 176)
        public static final class AnonymousClass1<T> implements Function0<T[]> {
            final /* synthetic */ Flow<T>[] $flows;

            /* JADX WARN: Multi-variable type inference failed */
            public AnonymousClass1(Flow<? extends T>[] flowArr) {
                this.$flows = flowArr;
            }

            @Override // kotlin.jvm.functions.Function0
            public final T[] invoke() {
                int length = this.$flows.length;
                Intrinsics.reifiedOperationMarker(0, "T?");
                return (T[]) new Object[length];
            }
        }

        /* JADX INFO: Add missing generic type declarations: [T] */
        /* JADX INFO: renamed from: kotlinx.coroutines.flow.FlowKt__ZipKt$combineTransform$6$2, reason: invalid class name */
        /* JADX INFO: compiled from: Zip.kt */
        @Metadata(d1 = {"\u0000\u0014\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0011\u0010\u0000\u001a\u00020\u0001\"\u0004\b\u0000\u0010\u0002\"\u0006\b\u0001\u0010\u0003\u0018\u0001*\b\u0012\u0004\u0012\u0002H\u00020\u00042\f\u0010\u0005\u001a\b\u0012\u0004\u0012\u0002H\u00030\u0006H\n"}, d2 = {"<anonymous>", "", "R", ExifInterface.GPS_DIRECTION_TRUE, "Lkotlinx/coroutines/flow/FlowCollector;", "it", ""}, k = 3, mv = {2, 1, 0}, xi = 176)
        @DebugMetadata(c = "kotlinx.coroutines.flow.FlowKt__ZipKt$combineTransform$6$2", f = "Zip.kt", i = {}, l = {R2.attr.actionBarCopyIcon}, m = "invokeSuspend", n = {}, s = {})
        public static final class AnonymousClass2<T> extends SuspendLambda implements Function3<FlowCollector<? super R>, T[], Continuation<? super Unit>, Object> {
            final /* synthetic */ Function3<FlowCollector<? super R>, T[], Continuation<? super Unit>, Object> $transform;
            private /* synthetic */ Object L$0;
            /* synthetic */ Object L$1;
            int label;

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            /* JADX WARN: Multi-variable type inference failed */
            public AnonymousClass2(Function3<? super FlowCollector<? super R>, ? super T[], ? super Continuation<? super Unit>, ? extends Object> function3, Continuation<? super AnonymousClass2> continuation) {
                super(3, continuation);
                this.$transform = function3;
            }

            @Override // kotlin.jvm.functions.Function3
            public final Object invoke(FlowCollector<? super R> flowCollector, T[] tArr, Continuation<? super Unit> continuation) {
                Intrinsics.needClassReification();
                AnonymousClass2 anonymousClass2 = new AnonymousClass2(this.$transform, continuation);
                anonymousClass2.L$0 = flowCollector;
                anonymousClass2.L$1 = tArr;
                return anonymousClass2.invokeSuspend(Unit.INSTANCE);
            }

            /* JADX WARN: Type inference fix 'apply assigned field type' failed
            java.lang.UnsupportedOperationException: ArgType.getObject(), call class: class jadx.core.dex.instructions.args.ArgType$ArrayArg
            	at jadx.core.dex.instructions.args.ArgType.getObject(ArgType.java:596)
            	at jadx.core.dex.attributes.nodes.ClassTypeVarsAttr.getTypeVarsMapFor(ClassTypeVarsAttr.java:35)
            	at jadx.core.dex.nodes.utils.TypeUtils.replaceClassGenerics(TypeUtils.java:177)
            	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.insertExplicitUseCast(FixTypesVisitor.java:397)
            	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.tryFieldTypeWithNewCasts(FixTypesVisitor.java:359)
            	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.applyFieldType(FixTypesVisitor.java:309)
            	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.visit(FixTypesVisitor.java:94)
             */
            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Object invokeSuspend(Object obj) {
                Object coroutine_suspended = IntrinsicsKt.getCOROUTINE_SUSPENDED();
                int i = this.label;
                if (i == 0) {
                    ResultKt.throwOnFailure(obj);
                    FlowCollector<? super R> flowCollector = (FlowCollector) this.L$0;
                    Object[] objArr = (Object[]) this.L$1;
                    Function3<FlowCollector<? super R>, T[], Continuation<? super Unit>, Object> function3 = this.$transform;
                    this.L$0 = null;
                    this.label = 1;
                    if (function3.invoke(flowCollector, objArr, this) == coroutine_suspended) {
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

            /* JADX WARN: Type inference fix 'apply assigned field type' failed
            java.lang.UnsupportedOperationException: ArgType.getObject(), call class: class jadx.core.dex.instructions.args.ArgType$ArrayArg
            	at jadx.core.dex.instructions.args.ArgType.getObject(ArgType.java:596)
            	at jadx.core.dex.attributes.nodes.ClassTypeVarsAttr.getTypeVarsMapFor(ClassTypeVarsAttr.java:35)
            	at jadx.core.dex.nodes.utils.TypeUtils.replaceClassGenerics(TypeUtils.java:177)
            	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.insertExplicitUseCast(FixTypesVisitor.java:397)
            	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.tryFieldTypeWithNewCasts(FixTypesVisitor.java:359)
            	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.applyFieldType(FixTypesVisitor.java:309)
            	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.visit(FixTypesVisitor.java:94)
             */
            public final Object invokeSuspend$$forInline(Object obj) {
                this.$transform.invoke((FlowCollector) this.L$0, (Object[]) this.L$1, this);
                return Unit.INSTANCE;
            }
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            Object coroutine_suspended = IntrinsicsKt.getCOROUTINE_SUSPENDED();
            int i = this.label;
            if (i == 0) {
                ResultKt.throwOnFailure(obj);
                FlowCollector flowCollector = (FlowCollector) this.L$0;
                Flow<T>[] flowArr = this.$flows;
                Intrinsics.needClassReification();
                AnonymousClass1 anonymousClass1 = new AnonymousClass1(this.$flows);
                Intrinsics.needClassReification();
                this.label = 1;
                if (CombineKt.combineInternal(flowCollector, flowArr, anonymousClass1, new AnonymousClass2(this.$transform, null), this) == coroutine_suspended) {
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

        public final Object invokeSuspend$$forInline(Object obj) {
            FlowCollector flowCollector = (FlowCollector) this.L$0;
            Flow<T>[] flowArr = this.$flows;
            Intrinsics.needClassReification();
            AnonymousClass1 anonymousClass1 = new AnonymousClass1(this.$flows);
            Intrinsics.needClassReification();
            CombineKt.combineInternal(flowCollector, flowArr, anonymousClass1, new AnonymousClass2(this.$transform, null), this);
            return Unit.INSTANCE;
        }
    }

    public static final /* synthetic */ <T, R> Flow<R> combineTransform(Flow<? extends T>[] flowArr, Function3<? super FlowCollector<? super R>, ? super T[], ? super Continuation<? super Unit>, ? extends Object> function3) {
        Intrinsics.needClassReification();
        return FlowKt.flow(new AnonymousClass6(flowArr, function3, null));
    }

    private static final /* synthetic */ <T, R> Flow<R> combineTransformUnsafe$FlowKt__ZipKt(Flow<? extends T>[] flowArr, Function3<? super FlowCollector<? super R>, ? super T[], ? super Continuation<? super Unit>, ? extends Object> function3) {
        Intrinsics.needClassReification();
        return FlowKt.flow(new FlowKt__ZipKt$combineTransformUnsafe$1(flowArr, function3, null));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final <T> Function0<T[]> nullArrayFactory$FlowKt__ZipKt() {
        return new Function0() { // from class: kotlinx.coroutines.flow.FlowKt__ZipKt$nullArrayFactory$1
            @Override // kotlin.jvm.functions.Function0
            public final Void invoke() {
                return null;
            }
        };
    }

    public static final /* synthetic */ <T, R> Flow<R> combine(Iterable<? extends Flow<? extends T>> iterable, Function2<? super T[], ? super Continuation<? super R>, ? extends Object> function2) {
        Flow[] flowArr = (Flow[]) CollectionsKt.toList(iterable).toArray(new Flow[0]);
        Intrinsics.needClassReification();
        return new FlowKt__ZipKt$combine$$inlined$unsafeFlow$3(flowArr, function2);
    }

    /* JADX INFO: Add missing generic type declarations: [R] */
    /* JADX INFO: renamed from: kotlinx.coroutines.flow.FlowKt__ZipKt$combineTransform$7, reason: invalid class name */
    /* JADX INFO: compiled from: Zip.kt */
    @Metadata(d1 = {"\u0000\f\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\u0010\u0000\u001a\u00020\u0001\"\u0004\b\u0000\u0010\u0002*\b\u0012\u0004\u0012\u0002H\u00020\u0003H\n"}, d2 = {"<anonymous>", "", "R", "Lkotlinx/coroutines/flow/FlowCollector;"}, k = 3, mv = {2, 1, 0}, xi = 176)
    @DebugMetadata(c = "kotlinx.coroutines.flow.FlowKt__ZipKt$combineTransform$7", f = "Zip.kt", i = {}, l = {304}, m = "invokeSuspend", n = {}, s = {})
    public static final class AnonymousClass7<R> extends SuspendLambda implements Function2<FlowCollector<? super R>, Continuation<? super Unit>, Object> {
        final /* synthetic */ Flow<T>[] $flowArray;
        final /* synthetic */ Function3<FlowCollector<? super R>, T[], Continuation<? super Unit>, Object> $transform;
        private /* synthetic */ Object L$0;
        int label;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        /* JADX WARN: Multi-variable type inference failed */
        public AnonymousClass7(Flow<T>[] flowArr, Function3<? super FlowCollector<? super R>, ? super T[], ? super Continuation<? super Unit>, ? extends Object> function3, Continuation<? super AnonymousClass7> continuation) {
            super(2, continuation);
            this.$flowArray = flowArr;
            this.$transform = function3;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
            AnonymousClass7 anonymousClass7 = new AnonymousClass7(this.$flowArray, this.$transform, continuation);
            anonymousClass7.L$0 = obj;
            return anonymousClass7;
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(FlowCollector<? super R> flowCollector, Continuation<? super Unit> continuation) {
            return ((AnonymousClass7) create(flowCollector, continuation)).invokeSuspend(Unit.INSTANCE);
        }

        /* JADX INFO: Add missing generic type declarations: [T] */
        /* JADX INFO: renamed from: kotlinx.coroutines.flow.FlowKt__ZipKt$combineTransform$7$1, reason: invalid class name */
        /* JADX INFO: compiled from: Zip.kt */
        @Metadata(k = 3, mv = {2, 1, 0}, xi = 176)
        public static final class AnonymousClass1<T> implements Function0<T[]> {
            final /* synthetic */ Flow<T>[] $flowArray;

            public AnonymousClass1(Flow<T>[] flowArr) {
                this.$flowArray = flowArr;
            }

            @Override // kotlin.jvm.functions.Function0
            public final T[] invoke() {
                int length = this.$flowArray.length;
                Intrinsics.reifiedOperationMarker(0, "T?");
                return (T[]) new Object[length];
            }
        }

        /* JADX INFO: Add missing generic type declarations: [T] */
        /* JADX INFO: renamed from: kotlinx.coroutines.flow.FlowKt__ZipKt$combineTransform$7$2, reason: invalid class name */
        /* JADX INFO: compiled from: Zip.kt */
        @Metadata(d1 = {"\u0000\u0014\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0011\u0010\u0000\u001a\u00020\u0001\"\u0004\b\u0000\u0010\u0002\"\u0006\b\u0001\u0010\u0003\u0018\u0001*\b\u0012\u0004\u0012\u0002H\u00020\u00042\f\u0010\u0005\u001a\b\u0012\u0004\u0012\u0002H\u00030\u0006H\n"}, d2 = {"<anonymous>", "", "R", ExifInterface.GPS_DIRECTION_TRUE, "Lkotlinx/coroutines/flow/FlowCollector;", "it", ""}, k = 3, mv = {2, 1, 0}, xi = 176)
        @DebugMetadata(c = "kotlinx.coroutines.flow.FlowKt__ZipKt$combineTransform$7$2", f = "Zip.kt", i = {}, l = {304}, m = "invokeSuspend", n = {}, s = {})
        public static final class AnonymousClass2<T> extends SuspendLambda implements Function3<FlowCollector<? super R>, T[], Continuation<? super Unit>, Object> {
            final /* synthetic */ Function3<FlowCollector<? super R>, T[], Continuation<? super Unit>, Object> $transform;
            private /* synthetic */ Object L$0;
            /* synthetic */ Object L$1;
            int label;

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            /* JADX WARN: Multi-variable type inference failed */
            public AnonymousClass2(Function3<? super FlowCollector<? super R>, ? super T[], ? super Continuation<? super Unit>, ? extends Object> function3, Continuation<? super AnonymousClass2> continuation) {
                super(3, continuation);
                this.$transform = function3;
            }

            @Override // kotlin.jvm.functions.Function3
            public final Object invoke(FlowCollector<? super R> flowCollector, T[] tArr, Continuation<? super Unit> continuation) {
                Intrinsics.needClassReification();
                AnonymousClass2 anonymousClass2 = new AnonymousClass2(this.$transform, continuation);
                anonymousClass2.L$0 = flowCollector;
                anonymousClass2.L$1 = tArr;
                return anonymousClass2.invokeSuspend(Unit.INSTANCE);
            }

            /* JADX WARN: Type inference fix 'apply assigned field type' failed
            java.lang.UnsupportedOperationException: ArgType.getObject(), call class: class jadx.core.dex.instructions.args.ArgType$ArrayArg
            	at jadx.core.dex.instructions.args.ArgType.getObject(ArgType.java:596)
            	at jadx.core.dex.attributes.nodes.ClassTypeVarsAttr.getTypeVarsMapFor(ClassTypeVarsAttr.java:35)
            	at jadx.core.dex.nodes.utils.TypeUtils.replaceClassGenerics(TypeUtils.java:177)
            	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.insertExplicitUseCast(FixTypesVisitor.java:397)
            	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.tryFieldTypeWithNewCasts(FixTypesVisitor.java:359)
            	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.applyFieldType(FixTypesVisitor.java:309)
            	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.visit(FixTypesVisitor.java:94)
             */
            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Object invokeSuspend(Object obj) {
                Object coroutine_suspended = IntrinsicsKt.getCOROUTINE_SUSPENDED();
                int i = this.label;
                if (i == 0) {
                    ResultKt.throwOnFailure(obj);
                    FlowCollector<? super R> flowCollector = (FlowCollector) this.L$0;
                    Object[] objArr = (Object[]) this.L$1;
                    Function3<FlowCollector<? super R>, T[], Continuation<? super Unit>, Object> function3 = this.$transform;
                    this.L$0 = null;
                    this.label = 1;
                    if (function3.invoke(flowCollector, objArr, this) == coroutine_suspended) {
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

            /* JADX WARN: Type inference fix 'apply assigned field type' failed
            java.lang.UnsupportedOperationException: ArgType.getObject(), call class: class jadx.core.dex.instructions.args.ArgType$ArrayArg
            	at jadx.core.dex.instructions.args.ArgType.getObject(ArgType.java:596)
            	at jadx.core.dex.attributes.nodes.ClassTypeVarsAttr.getTypeVarsMapFor(ClassTypeVarsAttr.java:35)
            	at jadx.core.dex.nodes.utils.TypeUtils.replaceClassGenerics(TypeUtils.java:177)
            	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.insertExplicitUseCast(FixTypesVisitor.java:397)
            	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.tryFieldTypeWithNewCasts(FixTypesVisitor.java:359)
            	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.applyFieldType(FixTypesVisitor.java:309)
            	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.visit(FixTypesVisitor.java:94)
             */
            public final Object invokeSuspend$$forInline(Object obj) {
                this.$transform.invoke((FlowCollector) this.L$0, (Object[]) this.L$1, this);
                return Unit.INSTANCE;
            }
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            Object coroutine_suspended = IntrinsicsKt.getCOROUTINE_SUSPENDED();
            int i = this.label;
            if (i == 0) {
                ResultKt.throwOnFailure(obj);
                FlowCollector flowCollector = (FlowCollector) this.L$0;
                Flow<T>[] flowArr = this.$flowArray;
                Intrinsics.needClassReification();
                AnonymousClass1 anonymousClass1 = new AnonymousClass1(this.$flowArray);
                Intrinsics.needClassReification();
                this.label = 1;
                if (CombineKt.combineInternal(flowCollector, flowArr, anonymousClass1, new AnonymousClass2(this.$transform, null), this) == coroutine_suspended) {
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

        public final Object invokeSuspend$$forInline(Object obj) {
            FlowCollector flowCollector = (FlowCollector) this.L$0;
            Flow<T>[] flowArr = this.$flowArray;
            Intrinsics.needClassReification();
            AnonymousClass1 anonymousClass1 = new AnonymousClass1(this.$flowArray);
            Intrinsics.needClassReification();
            CombineKt.combineInternal(flowCollector, flowArr, anonymousClass1, new AnonymousClass2(this.$transform, null), this);
            return Unit.INSTANCE;
        }
    }

    public static final /* synthetic */ <T, R> Flow<R> combineTransform(Iterable<? extends Flow<? extends T>> iterable, Function3<? super FlowCollector<? super R>, ? super T[], ? super Continuation<? super Unit>, ? extends Object> function3) {
        Flow[] flowArr = (Flow[]) CollectionsKt.toList(iterable).toArray(new Flow[0]);
        Intrinsics.needClassReification();
        return FlowKt.flow(new AnonymousClass7(flowArr, function3, null));
    }

    public static final <T1, T2, R> Flow<R> zip(Flow<? extends T1> flow, Flow<? extends T2> flow2, Function3<? super T1, ? super T2, ? super Continuation<? super R>, ? extends Object> function3) {
        return CombineKt.zipImpl(flow, flow2, function3);
    }

    public static final <T1, T2, R> Flow<R> flowCombine(final Flow<? extends T1> flow, final Flow<? extends T2> flow2, final Function3<? super T1, ? super T2, ? super Continuation<? super R>, ? extends Object> function3) {
        return new Flow<R>() { // from class: kotlinx.coroutines.flow.FlowKt__ZipKt$combine$$inlined$unsafeFlow$1
            @Override // kotlinx.coroutines.flow.Flow
            public Object collect(FlowCollector<? super R> flowCollector, Continuation<? super Unit> continuation) {
                Object objCombineInternal = CombineKt.combineInternal(flowCollector, new Flow[]{flow, flow2}, FlowKt__ZipKt.nullArrayFactory$FlowKt__ZipKt(), new FlowKt__ZipKt$combine$1$1(function3, null), continuation);
                return objCombineInternal == IntrinsicsKt.getCOROUTINE_SUSPENDED() ? objCombineInternal : Unit.INSTANCE;
            }
        };
    }

    public static final /* synthetic */ <T, R> Flow<R> combine(Flow<? extends T>[] flowArr, Function2<? super T[], ? super Continuation<? super R>, ? extends Object> function2) {
        Intrinsics.needClassReification();
        return new FlowKt__ZipKt$combine$$inlined$unsafeFlow$2(flowArr, function2);
    }

    private static final /* synthetic */ <T, R> Flow<R> combineUnsafe$FlowKt__ZipKt(Flow<? extends T>[] flowArr, Function2<? super T[], ? super Continuation<? super R>, ? extends Object> function2) {
        Intrinsics.needClassReification();
        return new FlowKt__ZipKt$combineUnsafe$$inlined$unsafeFlow$1(flowArr, function2);
    }
}
