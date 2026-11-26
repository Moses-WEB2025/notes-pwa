package kotlin.reflect.jvm.internal.calls;

import com.market.sdk.reflect.ReflectUtilsForMiui;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Member;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import kotlin.Metadata;
import kotlin.Pair;
import kotlin.collections.ArraysKt;
import kotlin.collections.CollectionsKt;
import kotlin.collections.IntIterator;
import kotlin.coroutines.intrinsics.IntrinsicsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.ranges.IntRange;
import kotlin.ranges.RangesKt;
import kotlin.reflect.jvm.internal.KDeclarationContainerImpl;
import kotlin.reflect.jvm.internal.UtilKt;
import kotlin.reflect.jvm.internal.impl.builtins.KotlinBuiltIns;
import kotlin.reflect.jvm.internal.impl.descriptors.CallableMemberDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.ClassDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.ClassifierDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.ConstructorDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.DeclarationDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.FunctionDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.ParameterDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.ReceiverParameterDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.ValueParameterDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.runtime.structure.ReflectClassUtilKt;
import kotlin.reflect.jvm.internal.impl.resolve.InlineClassesUtilsKt;
import kotlin.reflect.jvm.internal.impl.types.KotlinType;
import kotlin.reflect.jvm.internal.impl.types.TypeSubstitutionKt;
import kotlin.text.StringsKt;

/* JADX INFO: compiled from: ValueClassAwareCaller.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000R\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\b\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010 \n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0011\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0010\u0000\n\u0002\b\u0005\b\u0000\u0018\u0000*\f\b\u0000\u0010\u0001 \u0001*\u0004\u0018\u00010\u00022\b\u0012\u0004\u0012\u0002H\u00010\u0003:\u0002()B%\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\f\u0010\u0006\u001a\b\u0012\u0004\u0012\u00028\u00000\u0003\u0012\u0006\u0010\u0007\u001a\u00020\b¢\u0006\u0004\b\t\u0010\nJ\u000e\u0010 \u001a\u00020\u001e2\u0006\u0010!\u001a\u00020\"J\u001b\u0010$\u001a\u0004\u0018\u00010%2\n\u0010&\u001a\u0006\u0012\u0002\b\u00030\u001dH\u0016¢\u0006\u0002\u0010'R\u000e\u0010\u0007\u001a\u00020\bX\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010\u000b\u001a\b\u0012\u0004\u0012\u00028\u00000\u0003X\u0082\u0004¢\u0006\u0002\n\u0000R\u0016\u0010\f\u001a\u00028\u0000X\u0096\u0004¢\u0006\n\n\u0002\u0010\u000f\u001a\u0004\b\r\u0010\u000eR\u0014\u0010\u0010\u001a\u00020\u00118VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u0012\u0010\u0013R\u001a\u0010\u0014\u001a\b\u0012\u0004\u0012\u00020\u00110\u00158VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u0016\u0010\u0017R\u0014\u0010\u0018\u001a\u00020\b8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u0018\u0010\u0019R\u000e\u0010\u001a\u001a\u00020\u001bX\u0082\u0004¢\u0006\u0002\n\u0000R\u0016\u0010\u001c\u001a\b\u0012\u0004\u0012\u00020\u001e0\u001dX\u0082\u0004¢\u0006\u0004\n\u0002\u0010\u001fR\u000e\u0010#\u001a\u00020\bX\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006*"}, d2 = {"Lkotlin/reflect/jvm/internal/calls/ValueClassAwareCaller;", "M", "Ljava/lang/reflect/Member;", "Lkotlin/reflect/jvm/internal/calls/Caller;", "descriptor", "Lkotlin/reflect/jvm/internal/impl/descriptors/CallableMemberDescriptor;", "oldCaller", "isDefault", "", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "(Lorg/jetbrains/kotlin/descriptors/CallableMemberDescriptor;Lkotlin/reflect/jvm/internal/calls/Caller;Z)V", "caller", "member", "getMember", "()Ljava/lang/reflect/Member;", "Ljava/lang/reflect/Member;", "returnType", "Ljava/lang/reflect/Type;", "getReturnType", "()Ljava/lang/reflect/Type;", "parameterTypes", "", "getParameterTypes", "()Ljava/util/List;", "isBoundInstanceCallWithValueClasses", "()Z", "data", "Lkotlin/reflect/jvm/internal/calls/ValueClassAwareCaller$BoxUnboxData;", "slices", "", "Lkotlin/ranges/IntRange;", "[Lkotlin/ranges/IntRange;", "getRealSlicesOfParameters", "index", "", "hasMfvcParameters", "call", "", "args", "([Ljava/lang/Object;)Ljava/lang/Object;", "BoxUnboxData", "MultiFieldValueClassPrimaryConstructorCaller", "kotlin-reflection"}, k = 1, mv = {2, 2, 0}, xi = 48)
public final class ValueClassAwareCaller<M extends Member> implements Caller<M> {
    private final Caller<M> caller;
    private final BoxUnboxData data;
    private final boolean hasMfvcParameters;
    private final boolean isDefault;
    private final M member;
    private final IntRange[] slices;

    /* JADX WARN: Code duplicated, block: B:27:0x0084 A[LOOP:1: B:25:0x007e->B:27:0x0084, LOOP_END] */
    /* JADX WARN: Code duplicated, block: B:66:0x0144  */
    public ValueClassAwareCaller(CallableMemberDescriptor descriptor, Caller<? extends M> oldCaller, boolean z) {
        Class inlineClass;
        BoxUnboxData boxUnboxData;
        int length;
        KotlinType kotlinTypeSubstitutedUnderlyingType;
        ArrayList arrayList;
        Iterator<T> it;
        Intrinsics.checkNotNullParameter(descriptor, "descriptor");
        Intrinsics.checkNotNullParameter(oldCaller, "oldCaller");
        this.isDefault = z;
        boolean z2 = false;
        if (oldCaller instanceof CallerImpl.Method.BoundStatic) {
            ReceiverParameterDescriptor extensionReceiverParameter = descriptor.getExtensionReceiverParameter();
            extensionReceiverParameter = extensionReceiverParameter == null ? descriptor.getDispatchReceiverParameter() : extensionReceiverParameter;
            KotlinType type = extensionReceiverParameter != null ? extensionReceiverParameter.getType() : null;
            if (type != null && InlineClassesUtilsKt.needsMfvcFlattening(type)) {
                if (z) {
                    List<ValueParameterDescriptor> valueParameters = descriptor.getValueParameters();
                    Intrinsics.checkNotNullExpressionValue(valueParameters, "getValueParameters(...)");
                    List<ValueParameterDescriptor> list = valueParameters;
                    if (!(list instanceof Collection) || !list.isEmpty()) {
                        Iterator<T> it2 = list.iterator();
                        while (true) {
                            if (it2.hasNext()) {
                                if (((ValueParameterDescriptor) it2.next()).declaresDefaultValue()) {
                                    List<Method> mfvcUnboxMethods = ValueClassAwareCallerKt.getMfvcUnboxMethods(TypeSubstitutionKt.asSimpleType(type));
                                    Intrinsics.checkNotNull(mfvcUnboxMethods);
                                    List<Method> list2 = mfvcUnboxMethods;
                                    arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(list2, 10));
                                    it = list2.iterator();
                                    while (it.hasNext()) {
                                        arrayList.add(((Method) it.next()).invoke(((CallerImpl.Method.BoundStatic) oldCaller).getBoundReceiver(), new Object[0]));
                                    }
                                    oldCaller = new CallerImpl.Method.BoundStaticMultiFieldValueClass(((CallerImpl.Method) oldCaller).mo3811getMember(), arrayList.toArray(new Object[0]));
                                }
                            }
                        }
                    }
                } else {
                    List<Method> mfvcUnboxMethods2 = ValueClassAwareCallerKt.getMfvcUnboxMethods(TypeSubstitutionKt.asSimpleType(type));
                    Intrinsics.checkNotNull(mfvcUnboxMethods2);
                    List<Method> list3 = mfvcUnboxMethods2;
                    arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(list3, 10));
                    it = list3.iterator();
                    while (it.hasNext()) {
                        arrayList.add(((Method) it.next()).invoke(((CallerImpl.Method.BoundStatic) oldCaller).getBoundReceiver(), new Object[0]));
                    }
                    oldCaller = new CallerImpl.Method.BoundStaticMultiFieldValueClass(((CallerImpl.Method) oldCaller).mo3811getMember(), arrayList.toArray(new Object[0]));
                }
            }
        }
        this.caller = (Caller<M>) oldCaller;
        this.member = (M) oldCaller.mo3811getMember();
        KotlinType returnType = descriptor.getReturnType();
        Intrinsics.checkNotNull(returnType);
        boolean z3 = descriptor instanceof FunctionDescriptor;
        Method boxMethod = ((z3 && ((FunctionDescriptor) descriptor).isSuspend() && (kotlinTypeSubstitutedUnderlyingType = InlineClassesUtilsKt.substitutedUnderlyingType(returnType)) != null && KotlinBuiltIns.isPrimitiveType(kotlinTypeSubstitutedUnderlyingType)) || (inlineClass = ValueClassAwareCallerKt.toInlineClass(returnType)) == null) ? null : ValueClassAwareCallerKt.getBoxMethod(inlineClass, descriptor);
        if (InlineClassesUtilsKt.isGetterOfUnderlyingPropertyOfValueClass(descriptor)) {
            boxUnboxData = new BoxUnboxData(IntRange.INSTANCE.getEMPTY(), new List[0], boxMethod);
        } else {
            Caller<M> caller = this.caller;
            int i = -1;
            if (!(caller instanceof CallerImpl.Method.BoundStatic) || ((CallerImpl.Method.BoundStatic) caller).getIsCallByToValueClassMangledMethod()) {
                Caller<M> caller2 = this.caller;
                if (!(caller2 instanceof CallerImpl.Method.BoundStaticMultiFieldValueClass)) {
                    if (descriptor instanceof ConstructorDescriptor) {
                        if (!(caller2 instanceof BoundCaller)) {
                            i = 0;
                        }
                    } else if (descriptor.getDispatchReceiverParameter() == null || (this.caller instanceof BoundCaller)) {
                        i = 0;
                    } else {
                        DeclarationDescriptor containingDeclaration = descriptor.getContainingDeclaration();
                        Intrinsics.checkNotNullExpressionValue(containingDeclaration, "getContainingDeclaration(...)");
                        if (InlineClassesUtilsKt.isValueClass(containingDeclaration)) {
                            i = 0;
                        } else {
                            i = 1;
                        }
                    }
                }
            }
            Caller<M> caller3 = this.caller;
            int i2 = caller3 instanceof CallerImpl.Method.BoundStaticMultiFieldValueClass ? -((CallerImpl.Method.BoundStaticMultiFieldValueClass) caller3).getReceiverComponentsCount() : i;
            List listMakeKotlinParameterTypes = ValueClassAwareCallerKt.makeKotlinParameterTypes(descriptor, this.caller.mo3811getMember(), new Function1() { // from class: kotlin.reflect.jvm.internal.calls.ValueClassAwareCaller$$Lambda$0
                @Override // kotlin.jvm.functions.Function1
                public Object invoke(Object obj) {
                    return Boolean.valueOf(ValueClassAwareCaller.data$lambda$5$lambda$3((ClassDescriptor) obj));
                }
            });
            Iterator it3 = listMakeKotlinParameterTypes.iterator();
            int size = 0;
            while (it3.hasNext()) {
                List<Method> mfvcUnboxMethods3 = ValueClassAwareCallerKt.getMfvcUnboxMethods(TypeSubstitutionKt.asSimpleType((KotlinType) it3.next()));
                size += mfvcUnboxMethods3 != null ? mfvcUnboxMethods3.size() : 1;
            }
            int i3 = size + i2 + (this.isDefault ? ((size + 31) / 32) + 1 : 0) + ((z3 && ((FunctionDescriptor) descriptor).isSuspend()) ? 1 : 0);
            ValueClassAwareCallerKt.checkParametersSize(this, i3, descriptor, this.isDefault);
            IntRange intRangeUntil = RangesKt.until(Math.max(i, 0), listMakeKotlinParameterTypes.size() + i);
            List[] listArr = new List[i3];
            int i4 = 0;
            while (i4 < i3) {
                listArr[i4] = (i4 > intRangeUntil.getLast() || intRangeUntil.getFirst() > i4) ? null : ValueClassAwareCallerKt.getValueClassUnboxMethods(TypeSubstitutionKt.asSimpleType((KotlinType) listMakeKotlinParameterTypes.get(i4 - i)), descriptor);
                i4++;
            }
            boxUnboxData = new BoxUnboxData(intRangeUntil, listArr, boxMethod);
        }
        this.data = boxUnboxData;
        List listCreateListBuilder = CollectionsKt.createListBuilder();
        Caller<M> caller4 = this.caller;
        if (caller4 instanceof CallerImpl.Method.BoundStaticMultiFieldValueClass) {
            length = ((CallerImpl.Method.BoundStaticMultiFieldValueClass) caller4).getBoundReceiverComponents().length;
        } else {
            length = caller4 instanceof CallerImpl.Method.BoundStatic ? 1 : 0;
        }
        if (length > 0) {
            listCreateListBuilder.add(RangesKt.until(0, length));
        }
        List<Method>[] unboxParameters = boxUnboxData.getUnboxParameters();
        int length2 = unboxParameters.length;
        int i5 = 0;
        while (i5 < length2) {
            List<Method> list4 = unboxParameters[i5];
            int size2 = (list4 != null ? list4.size() : 1) + length;
            listCreateListBuilder.add(RangesKt.until(length, size2));
            i5++;
            length = size2;
        }
        this.slices = (IntRange[]) CollectionsKt.build(listCreateListBuilder).toArray(new IntRange[0]);
        Iterable argumentRange = this.data.getArgumentRange();
        if (!(argumentRange instanceof Collection) || !((Collection) argumentRange).isEmpty()) {
            Iterator it4 = argumentRange.iterator();
            while (it4.hasNext()) {
                List<Method> list5 = this.data.getUnboxParameters()[((IntIterator) it4).nextInt()];
                if (list5 != null && list5.size() > 1) {
                    z2 = true;
                    break;
                }
            }
        }
        this.hasMfvcParameters = z2;
    }

    @Override // kotlin.reflect.jvm.internal.calls.Caller
    /* JADX INFO: renamed from: getMember */
    public M mo3811getMember() {
        return this.member;
    }

    @Override // kotlin.reflect.jvm.internal.calls.Caller
    public Type getReturnType() {
        return this.caller.getReturnType();
    }

    @Override // kotlin.reflect.jvm.internal.calls.Caller
    public List<Type> getParameterTypes() {
        return this.caller.getParameterTypes();
    }

    @Override // kotlin.reflect.jvm.internal.calls.Caller
    public boolean isBoundInstanceCallWithValueClasses() {
        return this.caller instanceof CallerImpl.Method.BoundInstance;
    }

    /* JADX INFO: compiled from: ValueClassAwareCaller.kt */
    @Metadata(d1 = {"\u0000 \n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0011\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u000b\b\u0002\u0018\u00002\u00020\u0001B/\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0014\u0010\u0004\u001a\u0010\u0012\f\u0012\n\u0012\u0004\u0012\u00020\u0007\u0018\u00010\u00060\u0005\u0012\b\u0010\b\u001a\u0004\u0018\u00010\u0007¢\u0006\u0004\b\t\u0010\nR\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\fR!\u0010\u0004\u001a\u0010\u0012\f\u0012\n\u0012\u0004\u0012\u00020\u0007\u0018\u00010\u00060\u0005¢\u0006\n\n\u0002\u0010\u000f\u001a\u0004\b\r\u0010\u000eR\u0013\u0010\b\u001a\u0004\u0018\u00010\u0007¢\u0006\b\n\u0000\u001a\u0004\b\u0010\u0010\u0011¨\u0006\u0012"}, d2 = {"Lkotlin/reflect/jvm/internal/calls/ValueClassAwareCaller$BoxUnboxData;", "", "argumentRange", "Lkotlin/ranges/IntRange;", "unboxParameters", "", "", "Ljava/lang/reflect/Method;", "box", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "(Lkotlin/ranges/IntRange;[Ljava/util/List;Ljava/lang/reflect/Method;)V", "getArgumentRange", "()Lkotlin/ranges/IntRange;", "getUnboxParameters", "()[Ljava/util/List;", "[Ljava/util/List;", "getBox", "()Ljava/lang/reflect/Method;", "kotlin-reflection"}, k = 1, mv = {2, 2, 0}, xi = 48)
    private static final class BoxUnboxData {
        private final IntRange argumentRange;
        private final Method box;
        private final List<Method>[] unboxParameters;

        public BoxUnboxData(IntRange argumentRange, List<Method>[] unboxParameters, Method method) {
            Intrinsics.checkNotNullParameter(argumentRange, "argumentRange");
            Intrinsics.checkNotNullParameter(unboxParameters, "unboxParameters");
            this.argumentRange = argumentRange;
            this.unboxParameters = unboxParameters;
            this.box = method;
        }

        public final IntRange getArgumentRange() {
            return this.argumentRange;
        }

        public final Method getBox() {
            return this.box;
        }

        public final List<Method>[] getUnboxParameters() {
            return this.unboxParameters;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final boolean data$lambda$5$lambda$3(ClassDescriptor makeKotlinParameterTypes) {
        Intrinsics.checkNotNullParameter(makeKotlinParameterTypes, "$this$makeKotlinParameterTypes");
        return InlineClassesUtilsKt.isValueClass(makeKotlinParameterTypes);
    }

    public final IntRange getRealSlicesOfParameters(int index) {
        if (index >= 0) {
            IntRange[] intRangeArr = this.slices;
            if (index < intRangeArr.length) {
                return intRangeArr[index];
            }
        }
        IntRange[] intRangeArr2 = this.slices;
        if (intRangeArr2.length == 0) {
            return new IntRange(index, index);
        }
        int length = (index - intRangeArr2.length) + ((IntRange) ArraysKt.last(intRangeArr2)).getLast() + 1;
        return new IntRange(length, length);
    }

    @Override // kotlin.reflect.jvm.internal.calls.Caller
    public Object call(Object[] args) throws IllegalAccessException, InvocationTargetException {
        Object objInvoke;
        Object objDefaultPrimitiveValue;
        Object objDefaultPrimitiveValue2;
        Intrinsics.checkNotNullParameter(args, "args");
        IntRange argumentRange = this.data.getArgumentRange();
        List<Method>[] unboxParameters = this.data.getUnboxParameters();
        Method box = this.data.getBox();
        if (!argumentRange.isEmpty()) {
            if (this.hasMfvcParameters) {
                List listCreateListBuilder = CollectionsKt.createListBuilder(args.length);
                int first = argumentRange.getFirst();
                for (int i = 0; i < first; i++) {
                    listCreateListBuilder.add(args[i]);
                }
                int first2 = argumentRange.getFirst();
                int last = argumentRange.getLast();
                if (first2 <= last) {
                    while (true) {
                        List<Method> list = unboxParameters[first2];
                        Object obj = args[first2];
                        if (list == null) {
                            Boolean.valueOf(listCreateListBuilder.add(obj));
                        } else {
                            for (Method method : list) {
                                List list2 = listCreateListBuilder;
                                if (obj != null) {
                                    objDefaultPrimitiveValue2 = method.invoke(obj, new Object[0]);
                                } else {
                                    Class<?> returnType = method.getReturnType();
                                    Intrinsics.checkNotNullExpressionValue(returnType, "getReturnType(...)");
                                    objDefaultPrimitiveValue2 = UtilKt.defaultPrimitiveValue(returnType);
                                }
                                list2.add(objDefaultPrimitiveValue2);
                            }
                        }
                        if (first2 == last) {
                            break;
                        }
                        first2++;
                    }
                }
                int last2 = argumentRange.getLast() + 1;
                int lastIndex = ArraysKt.getLastIndex(args);
                if (last2 <= lastIndex) {
                    while (true) {
                        listCreateListBuilder.add(args[last2]);
                        if (last2 == lastIndex) {
                            break;
                        }
                        last2++;
                    }
                }
                args = CollectionsKt.build(listCreateListBuilder).toArray(new Object[0]);
            } else {
                int length = args.length;
                Object[] objArr = new Object[length];
                for (int i2 = 0; i2 < length; i2++) {
                    int first3 = argumentRange.getFirst();
                    if (i2 <= argumentRange.getLast() && first3 <= i2) {
                        List<Method> list3 = unboxParameters[i2];
                        Method method2 = list3 != null ? (Method) CollectionsKt.single((List) list3) : null;
                        objDefaultPrimitiveValue = args[i2];
                        if (method2 != null) {
                            if (objDefaultPrimitiveValue != null) {
                                objDefaultPrimitiveValue = method2.invoke(objDefaultPrimitiveValue, new Object[0]);
                            } else {
                                Class<?> returnType2 = method2.getReturnType();
                                Intrinsics.checkNotNullExpressionValue(returnType2, "getReturnType(...)");
                                objDefaultPrimitiveValue = UtilKt.defaultPrimitiveValue(returnType2);
                            }
                        }
                    } else {
                        objDefaultPrimitiveValue = args[i2];
                    }
                    objArr[i2] = objDefaultPrimitiveValue;
                }
                args = objArr;
            }
        }
        Object objCall = this.caller.call(args);
        return (objCall == IntrinsicsKt.getCOROUTINE_SUSPENDED() || box == null || (objInvoke = box.invoke(null, objCall)) == null) ? objCall : objInvoke;
    }

    /* JADX INFO: compiled from: ValueClassAwareCaller.kt */
    @Metadata(d1 = {"\u0000R\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0001\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u0011\n\u0002\b\u0002\u0018\u00002\n\u0012\u0006\u0012\u0004\u0018\u00010\u00020\u0001B-\u0012\u0006\u0010\u0003\u001a\u00020\u0004\u0012\u0006\u0010\u0005\u001a\u00020\u0006\u0012\u0006\u0010\u0007\u001a\u00020\b\u0012\f\u0010\t\u001a\b\u0012\u0004\u0012\u00020\u000b0\n¢\u0006\u0004\b\f\u0010\rJ\u001b\u0010\u001f\u001a\u0004\u0018\u00010 2\n\u0010!\u001a\u0006\u0012\u0002\b\u00030\"H\u0016¢\u0006\u0002\u0010#R\u000e\u0010\u000e\u001a\u00020\u000fX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0010\u001a\u00020\u000fX\u0082\u0004¢\u0006\u0002\n\u0000R\u001c\u0010\u0011\u001a\u0010\u0012\f\u0012\n\u0012\u0004\u0012\u00020\u000f\u0018\u00010\n0\nX\u0082\u0004¢\u0006\u0002\n\u0000R\u0016\u0010\u0012\u001a\u0004\u0018\u00010\u00028VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u0013\u0010\u0014R\u0014\u0010\u0015\u001a\u00020\u00168VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u0017\u0010\u0018R!\u0010\u0019\u001a\u0012\u0012\u000e\u0012\f\u0012\b\u0012\u0006\u0012\u0002\b\u00030\u001a0\n0\n¢\u0006\b\n\u0000\u001a\u0004\b\u001b\u0010\u001cR\u001a\u0010\u001d\u001a\b\u0012\u0004\u0012\u00020\u00160\nX\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u001e\u0010\u001c¨\u0006$"}, d2 = {"Lkotlin/reflect/jvm/internal/calls/ValueClassAwareCaller$MultiFieldValueClassPrimaryConstructorCaller;", "Lkotlin/reflect/jvm/internal/calls/Caller;", "", "descriptor", "Lkotlin/reflect/jvm/internal/impl/descriptors/FunctionDescriptor;", "container", "Lkotlin/reflect/jvm/internal/KDeclarationContainerImpl;", "constructorDesc", "", "originalParameters", "", "Lkotlin/reflect/jvm/internal/impl/descriptors/ParameterDescriptor;", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "(Lorg/jetbrains/kotlin/descriptors/FunctionDescriptor;Lkotlin/reflect/jvm/internal/KDeclarationContainerImpl;Ljava/lang/String;Ljava/util/List;)V", "constructorImpl", "Ljava/lang/reflect/Method;", "boxMethod", "parameterUnboxMethods", "member", "getMember", "()Ljava/lang/Void;", "returnType", "Ljava/lang/reflect/Type;", "getReturnType", "()Ljava/lang/reflect/Type;", "originalParametersGroups", "Ljava/lang/Class;", "getOriginalParametersGroups", "()Ljava/util/List;", "parameterTypes", "getParameterTypes", "call", "", "args", "", "([Ljava/lang/Object;)Ljava/lang/Object;", "kotlin-reflection"}, k = 1, mv = {2, 2, 0}, xi = 48)
    public static final class MultiFieldValueClassPrimaryConstructorCaller implements Caller {
        private final Method boxMethod;
        private final Method constructorImpl;
        private final List<List<Class<?>>> originalParametersGroups;
        private final List<Type> parameterTypes;
        private final List<List<Method>> parameterUnboxMethods;

        public boolean default$isBoundInstanceCallWithValueClasses() {
            return false;
        }

        public Void getMember() {
            return null;
        }

        public MultiFieldValueClassPrimaryConstructorCaller(FunctionDescriptor descriptor, KDeclarationContainerImpl container, String constructorDesc, List<? extends ParameterDescriptor> originalParameters) {
            ArrayList arrayListListOf;
            Intrinsics.checkNotNullParameter(descriptor, "descriptor");
            Intrinsics.checkNotNullParameter(container, "container");
            Intrinsics.checkNotNullParameter(constructorDesc, "constructorDesc");
            Intrinsics.checkNotNullParameter(originalParameters, "originalParameters");
            Method methodFindMethodBySignature = container.findMethodBySignature("constructor-impl", constructorDesc);
            Intrinsics.checkNotNull(methodFindMethodBySignature);
            this.constructorImpl = methodFindMethodBySignature;
            Method methodFindMethodBySignature2 = container.findMethodBySignature("box-impl", StringsKt.removeSuffix(constructorDesc, (CharSequence) "V") + ReflectClassUtilKt.getDesc(container.getJClass()));
            Intrinsics.checkNotNull(methodFindMethodBySignature2);
            this.boxMethod = methodFindMethodBySignature2;
            List<? extends ParameterDescriptor> list = originalParameters;
            ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(list, 10));
            Iterator<T> it = list.iterator();
            while (it.hasNext()) {
                KotlinType type = ((ParameterDescriptor) it.next()).getType();
                Intrinsics.checkNotNullExpressionValue(type, "getType(...)");
                arrayList.add(ValueClassAwareCallerKt.getValueClassUnboxMethods(TypeSubstitutionKt.asSimpleType(type), descriptor));
            }
            this.parameterUnboxMethods = arrayList;
            ArrayList arrayList2 = new ArrayList(CollectionsKt.collectionSizeOrDefault(list, 10));
            int i = 0;
            for (Object obj : list) {
                int i2 = i + 1;
                if (i < 0) {
                    CollectionsKt.throwIndexOverflow();
                }
                ClassifierDescriptor classifierDescriptorMo3823getDeclarationDescriptor = ((ParameterDescriptor) obj).getType().getConstructor().mo3823getDeclarationDescriptor();
                Intrinsics.checkNotNull(classifierDescriptorMo3823getDeclarationDescriptor, "null cannot be cast to non-null type org.jetbrains.kotlin.descriptors.ClassDescriptor");
                ClassDescriptor classDescriptor = (ClassDescriptor) classifierDescriptorMo3823getDeclarationDescriptor;
                List<Method> list2 = this.parameterUnboxMethods.get(i);
                if (list2 == null) {
                    Class<?> javaClass = UtilKt.toJavaClass(classDescriptor);
                    Intrinsics.checkNotNull(javaClass);
                    arrayListListOf = CollectionsKt.listOf(javaClass);
                } else {
                    List<Method> list3 = list2;
                    ArrayList arrayList3 = new ArrayList(CollectionsKt.collectionSizeOrDefault(list3, 10));
                    Iterator<T> it2 = list3.iterator();
                    while (it2.hasNext()) {
                        arrayList3.add(((Method) it2.next()).getReturnType());
                    }
                    arrayListListOf = arrayList3;
                }
                arrayList2.add(arrayListListOf);
                i = i2;
            }
            ArrayList arrayList4 = arrayList2;
            this.originalParametersGroups = arrayList4;
            this.parameterTypes = CollectionsKt.flatten(arrayList4);
        }

        @Override // kotlin.reflect.jvm.internal.calls.Caller
        /* JADX INFO: renamed from: getMember */
        public /* bridge */ /* synthetic */ Member mo3811getMember() {
            return (Member) getMember();
        }

        @Override // kotlin.reflect.jvm.internal.calls.Caller
        public boolean isBoundInstanceCallWithValueClasses() {
            return default$isBoundInstanceCallWithValueClasses();
        }

        @Override // kotlin.reflect.jvm.internal.calls.Caller
        public Type getReturnType() {
            Class<?> returnType = this.boxMethod.getReturnType();
            Intrinsics.checkNotNullExpressionValue(returnType, "getReturnType(...)");
            return returnType;
        }

        public final List<List<Class<?>>> getOriginalParametersGroups() {
            return this.originalParametersGroups;
        }

        @Override // kotlin.reflect.jvm.internal.calls.Caller
        public List<Type> getParameterTypes() {
            return this.parameterTypes;
        }

        @Override // kotlin.reflect.jvm.internal.calls.Caller
        public Object call(Object[] args) throws IllegalAccessException, InvocationTargetException {
            ArrayList arrayListListOf;
            Intrinsics.checkNotNullParameter(args, "args");
            List<Pair> listZip = ArraysKt.zip(args, this.parameterUnboxMethods);
            ArrayList arrayList = new ArrayList();
            for (Pair pair : listZip) {
                Object objComponent1 = pair.component1();
                List list = (List) pair.component2();
                if (list != null) {
                    List list2 = list;
                    ArrayList arrayList2 = new ArrayList(CollectionsKt.collectionSizeOrDefault(list2, 10));
                    Iterator it = list2.iterator();
                    while (it.hasNext()) {
                        arrayList2.add(((Method) it.next()).invoke(objComponent1, new Object[0]));
                    }
                    arrayListListOf = arrayList2;
                } else {
                    arrayListListOf = CollectionsKt.listOf(objComponent1);
                }
                CollectionsKt.addAll(arrayList, arrayListListOf);
            }
            Object[] array = arrayList.toArray(new Object[0]);
            this.constructorImpl.invoke(null, Arrays.copyOf(array, array.length));
            return this.boxMethod.invoke(null, Arrays.copyOf(array, array.length));
        }
    }
}
