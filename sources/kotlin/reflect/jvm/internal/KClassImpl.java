package kotlin.reflect.jvm.internal;

import androidx.exifinterface.media.ExifInterface;
import ch.qos.logback.core.CoreConstants;
import com.market.sdk.reflect.ReflectUtilsForMiui;
import com.xiaomi.mirror.RemoteDeviceInfo;
import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import kotlin.Lazy;
import kotlin.LazyKt;
import kotlin.LazyThreadSafetyMode;
import kotlin.Metadata;
import kotlin.NoWhenBranchMatchedException;
import kotlin.collections.ArraysKt;
import kotlin.collections.CollectionsKt;
import kotlin.collections.SetsKt;
import kotlin.jvm.JvmClassMappingKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.PropertyReference1Impl;
import kotlin.jvm.internal.Reflection;
import kotlin.jvm.internal.TypeIntrinsics;
import kotlin.reflect.KCallable;
import kotlin.reflect.KClass;
import kotlin.reflect.KFunction;
import kotlin.reflect.KProperty;
import kotlin.reflect.KType;
import kotlin.reflect.KTypeParameter;
import kotlin.reflect.KVisibility;
import kotlin.reflect.jvm.internal.impl.SpecialJvmAnnotations;
import kotlin.reflect.jvm.internal.impl.builtins.CompanionObjectMapping;
import kotlin.reflect.jvm.internal.impl.builtins.KotlinBuiltIns;
import kotlin.reflect.jvm.internal.impl.descriptors.ClassConstructorDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.ClassDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.ClassifierDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.ConstructorDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.DescriptorVisibility;
import kotlin.reflect.jvm.internal.impl.descriptors.FindClassInModuleKt;
import kotlin.reflect.jvm.internal.impl.descriptors.FunctionDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.ModuleDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.PropertyDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.SourceElement;
import kotlin.reflect.jvm.internal.impl.descriptors.TypeParameterDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.impl.ClassDescriptorImpl;
import kotlin.reflect.jvm.internal.impl.descriptors.impl.EmptyPackageFragmentDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.runtime.components.ReflectKotlinClass;
import kotlin.reflect.jvm.internal.impl.descriptors.runtime.components.RuntimeModuleData;
import kotlin.reflect.jvm.internal.impl.descriptors.runtime.structure.Java16SealedRecordLoader;
import kotlin.reflect.jvm.internal.impl.descriptors.runtime.structure.ReflectClassUtilKt;
import kotlin.reflect.jvm.internal.impl.incremental.components.NoLookupLocation;
import kotlin.reflect.jvm.internal.impl.km.Attributes;
import kotlin.reflect.jvm.internal.impl.km.ClassKind;
import kotlin.reflect.jvm.internal.impl.km.KmClass;
import kotlin.reflect.jvm.internal.impl.km.Modality;
import kotlin.reflect.jvm.internal.impl.km.internal.ReadersKt;
import kotlin.reflect.jvm.internal.impl.load.kotlin.header.KotlinClassHeader;
import kotlin.reflect.jvm.internal.impl.metadata.ProtoBuf;
import kotlin.reflect.jvm.internal.impl.metadata.deserialization.ProtoBufUtilKt;
import kotlin.reflect.jvm.internal.impl.metadata.jvm.JvmProtoBuf;
import kotlin.reflect.jvm.internal.impl.name.ClassId;
import kotlin.reflect.jvm.internal.impl.name.FqName;
import kotlin.reflect.jvm.internal.impl.name.Name;
import kotlin.reflect.jvm.internal.impl.protobuf.GeneratedMessageLite;
import kotlin.reflect.jvm.internal.impl.resolve.DescriptorUtils;
import kotlin.reflect.jvm.internal.impl.resolve.descriptorUtil.DescriptorUtilsKt;
import kotlin.reflect.jvm.internal.impl.resolve.scopes.GivenFunctionsMemberScope;
import kotlin.reflect.jvm.internal.impl.resolve.scopes.MemberScope;
import kotlin.reflect.jvm.internal.impl.serialization.deserialization.MemberDeserializer;
import kotlin.reflect.jvm.internal.impl.serialization.deserialization.descriptors.DeserializedClassDescriptor;
import kotlin.reflect.jvm.internal.impl.storage.StorageManager;
import kotlin.reflect.jvm.internal.impl.types.KotlinType;
import kotlin.reflect.jvm.internal.impl.types.SimpleType;
import kotlin.text.StringsKt;

/* JADX INFO: compiled from: KClassImpl.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000Ê\u0001\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010 \n\u0002\u0010\u001b\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u001e\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0012\n\u0002\u0018\u0002\n\u0002\b\u0004\b\u0000\u0018\u0000 n*\b\b\u0000\u0010\u0001*\u00020\u00022\u00020\u00032\b\u0012\u0004\u0012\u0002H\u00010\u00042\u00020\u00052\u00020\u0006:\u0002mnB\u0015\u0012\f\u0010\u0007\u001a\b\u0012\u0004\u0012\u00028\u00000\b¢\u0006\u0004\b\t\u0010\nJ\u0016\u00101\u001a\b\u0012\u0004\u0012\u0002020*2\u0006\u00103\u001a\u000204H\u0016J\u0016\u00105\u001a\b\u0012\u0004\u0012\u0002060*2\u0006\u00103\u001a\u000204H\u0016J\u0012\u00107\u001a\u0004\u0018\u0001022\u0006\u00108\u001a\u000209H\u0016J\u0012\u0010H\u001a\u00020I2\b\u0010J\u001a\u0004\u0018\u00010\u0002H\u0016J\u0013\u0010e\u001a\u00020I2\b\u0010f\u001a\u0004\u0018\u00010\u0002H\u0096\u0002J\b\u0010g\u001a\u000209H\u0016J\b\u0010h\u001a\u00020;H\u0016J\u0018\u0010i\u001a\u00020\u00132\u0006\u0010\u001f\u001a\u00020 2\u0006\u0010j\u001a\u00020kH\u0002J\u0018\u0010l\u001a\u00020\u00132\u0006\u0010\u001f\u001a\u00020 2\u0006\u0010j\u001a\u00020kH\u0002R\u001a\u0010\u0007\u001a\b\u0012\u0004\u0012\u00028\u00000\bX\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\fR!\u0010\r\u001a\u0012\u0012\u000e\u0012\f0\u000fR\b\u0012\u0004\u0012\u00028\u00000\u00000\u000e¢\u0006\b\n\u0000\u001a\u0004\b\u0010\u0010\u0011R\u0014\u0010\u0012\u001a\u00020\u00138VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u0014\u0010\u0015R\u0016\u0010\u0016\u001a\u0004\u0018\u00010\u00178BX\u0082\u0004¢\u0006\u0006\u001a\u0004\b\u0018\u0010\u0019R\u001a\u0010\u001a\u001a\b\u0012\u0004\u0012\u00020\u001c0\u001b8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u001d\u0010\u001eR\u0014\u0010\u001f\u001a\u00020 8BX\u0082\u0004¢\u0006\u0006\u001a\u0004\b!\u0010\"R\u0014\u0010#\u001a\u00020$8@X\u0080\u0004¢\u0006\u0006\u001a\u0004\b%\u0010&R\u0014\u0010'\u001a\u00020$8@X\u0080\u0004¢\u0006\u0006\u001a\u0004\b(\u0010&R\u001e\u0010)\u001a\f\u0012\b\u0012\u0006\u0012\u0002\b\u00030+0*8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b,\u0010-R\u001a\u0010.\u001a\b\u0012\u0004\u0012\u00020/0*8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b0\u0010-R\u0016\u0010:\u001a\u0004\u0018\u00010;8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b<\u0010=R\u0016\u0010>\u001a\u0004\u0018\u00010;8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b?\u0010=R \u0010@\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00028\u00000A0*8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\bB\u0010-R\u001e\u0010C\u001a\f\u0012\b\u0012\u0006\u0012\u0002\b\u00030\u00040*8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\bD\u0010-R\u0016\u0010E\u001a\u0004\u0018\u00018\u00008VX\u0096\u0004¢\u0006\u0006\u001a\u0004\bF\u0010GR\u001a\u0010K\u001a\b\u0012\u0004\u0012\u00020L0\u001b8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\bM\u0010\u001eR\u001a\u0010N\u001a\b\u0012\u0004\u0012\u00020O0\u001b8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\bP\u0010\u001eR\"\u0010Q\u001a\u0010\u0012\f\u0012\n\u0012\u0006\b\u0001\u0012\u00028\u00000\u00040\u001b8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\bR\u0010\u001eR\u0016\u0010S\u001a\u0004\u0018\u00010T8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\bU\u0010VR\u0014\u0010W\u001a\u00020X8BX\u0082\u0004¢\u0006\u0006\u001a\u0004\bY\u0010ZR\u0014\u0010[\u001a\u00020I8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b[\u0010\\R\u0014\u0010]\u001a\u00020I8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b]\u0010\\R\u0014\u0010^\u001a\u00020I8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b^\u0010\\R\u0014\u0010_\u001a\u00020I8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b_\u0010\\R\u0014\u0010`\u001a\u00020I8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b`\u0010\\R\u0014\u0010a\u001a\u00020I8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\ba\u0010\\R\u0014\u0010b\u001a\u00020I8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\bb\u0010\\R\u0014\u0010c\u001a\u00020I8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\bc\u0010\\R\u0014\u0010d\u001a\u00020I8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\bd\u0010\\¨\u0006o"}, d2 = {"Lkotlin/reflect/jvm/internal/KClassImpl;", ExifInterface.GPS_DIRECTION_TRUE, "", "Lkotlin/reflect/jvm/internal/KDeclarationContainerImpl;", "Lkotlin/reflect/KClass;", "Lkotlin/reflect/jvm/internal/KClassifierImpl;", "Lkotlin/reflect/jvm/internal/KTypeParameterOwnerImpl;", "jClass", "Ljava/lang/Class;", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "(Ljava/lang/Class;)V", "getJClass", "()Ljava/lang/Class;", "data", "Lkotlin/Lazy;", "Lkotlin/reflect/jvm/internal/KClassImpl$Data;", "getData", "()Lkotlin/Lazy;", "descriptor", "Lkotlin/reflect/jvm/internal/impl/descriptors/ClassDescriptor;", "getDescriptor", "()Lorg/jetbrains/kotlin/descriptors/ClassDescriptor;", "kmClass", "Lkotlin/reflect/jvm/internal/impl/km/KmClass;", "getKmClass", "()Lkotlin/metadata/KmClass;", "annotations", "", "", "getAnnotations", "()Ljava/util/List;", "classId", "Lkotlin/reflect/jvm/internal/impl/name/ClassId;", "getClassId", "()Lorg/jetbrains/kotlin/name/ClassId;", "memberScope", "Lkotlin/reflect/jvm/internal/impl/resolve/scopes/MemberScope;", "getMemberScope$kotlin_reflection", "()Lorg/jetbrains/kotlin/resolve/scopes/MemberScope;", "staticScope", "getStaticScope$kotlin_reflection", "members", "", "Lkotlin/reflect/KCallable;", "getMembers", "()Ljava/util/Collection;", "constructorDescriptors", "Lkotlin/reflect/jvm/internal/impl/descriptors/ConstructorDescriptor;", "getConstructorDescriptors", "getProperties", "Lkotlin/reflect/jvm/internal/impl/descriptors/PropertyDescriptor;", "name", "Lkotlin/reflect/jvm/internal/impl/name/Name;", "getFunctions", "Lkotlin/reflect/jvm/internal/impl/descriptors/FunctionDescriptor;", "getLocalProperty", "index", "", "simpleName", "", "getSimpleName", "()Ljava/lang/String;", "qualifiedName", "getQualifiedName", "constructors", "Lkotlin/reflect/KFunction;", "getConstructors", "nestedClasses", "getNestedClasses", "objectInstance", "getObjectInstance", "()Ljava/lang/Object;", "isInstance", "", "value", "typeParameters", "Lkotlin/reflect/KTypeParameter;", "getTypeParameters", "supertypes", "Lkotlin/reflect/KType;", "getSupertypes", "sealedSubclasses", "getSealedSubclasses", "visibility", "Lkotlin/reflect/KVisibility;", "getVisibility", "()Lkotlin/reflect/KVisibility;", "modality", "Lkotlin/reflect/jvm/internal/impl/km/Modality;", "getModality", "()Lkotlin/metadata/Modality;", "isFinal", "()Z", "isOpen", "isAbstract", "isSealed", "isData", "isInner", "isCompanion", "isFun", "isValue", "equals", RemoteDeviceInfo.MANUFACTURER_OTHER, "hashCode", "toString", "createSyntheticClassOrFail", "moduleData", "Lkotlin/reflect/jvm/internal/impl/descriptors/runtime/components/RuntimeModuleData;", "createSyntheticClass", "Data", "Companion", "kotlin-reflection"}, k = 1, mv = {2, 2, 0}, xi = 48)
public final class KClassImpl<T> extends KDeclarationContainerImpl implements KClass<T>, KClassifierImpl, KTypeParameterOwnerImpl {
    private static final Set<String> SPECIAL_JVM_ANNOTATION_NAMES;
    private final Lazy<KClassImpl<T>.Data> data;
    private final Class<T> jClass;

    /* JADX INFO: compiled from: KClassImpl.kt */
    @Metadata(k = 3, mv = {2, 2, 0}, xi = 48)
    public static final /* synthetic */ class WhenMappings {
        public static final /* synthetic */ int[] $EnumSwitchMapping$0;

        static {
            int[] iArr = new int[KotlinClassHeader.Kind.values().length];
            try {
                iArr[KotlinClassHeader.Kind.FILE_FACADE.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                iArr[KotlinClassHeader.Kind.MULTIFILE_CLASS.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                iArr[KotlinClassHeader.Kind.MULTIFILE_CLASS_PART.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                iArr[KotlinClassHeader.Kind.SYNTHETIC_CLASS.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
            try {
                iArr[KotlinClassHeader.Kind.UNKNOWN.ordinal()] = 5;
            } catch (NoSuchFieldError unused5) {
            }
            try {
                iArr[KotlinClassHeader.Kind.CLASS.ordinal()] = 6;
            } catch (NoSuchFieldError unused6) {
            }
            $EnumSwitchMapping$0 = iArr;
        }
    }

    @Override // kotlin.jvm.internal.ClassBasedDeclarationContainer
    public Class<T> getJClass() {
        return this.jClass;
    }

    /* JADX INFO: compiled from: KClassImpl.kt */
    @Metadata(d1 = {"\u0000f\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010 \n\u0002\u0010\u001b\n\u0002\b\u0004\n\u0002\u0010\u000e\n\u0002\b\b\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u001e\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0018\b\u0086\u0004\u0018\u00002\u00060\u0001R\u00020\u0002B\u0007¢\u0006\u0004\b\u0003\u0010\u0004J\u0014\u0010\u001f\u001a\u00020\u00182\n\u0010 \u001a\u0006\u0012\u0002\b\u00030!H\u0002R\u001d\u0010\u0005\u001a\u0004\u0018\u00010\u00068FX\u0086\u0084\u0002¢\u0006\f\n\u0004\b\t\u0010\n\u001a\u0004\b\u0007\u0010\bR\u001b\u0010\u000b\u001a\u00020\f8FX\u0086\u0084\u0002¢\u0006\f\n\u0004\b\u000f\u0010\u0010\u001a\u0004\b\r\u0010\u000eR!\u0010\u0011\u001a\b\u0012\u0004\u0012\u00020\u00130\u00128FX\u0086\u0084\u0002¢\u0006\f\n\u0004\b\u0016\u0010\u0010\u001a\u0004\b\u0014\u0010\u0015R\u001d\u0010\u0017\u001a\u0004\u0018\u00010\u00188FX\u0086\u0084\u0002¢\u0006\f\n\u0004\b\u001b\u0010\u0010\u001a\u0004\b\u0019\u0010\u001aR\u001d\u0010\u001c\u001a\u0004\u0018\u00010\u00188FX\u0086\u0084\u0002¢\u0006\f\n\u0004\b\u001e\u0010\u0010\u001a\u0004\b\u001d\u0010\u001aR-\u0010\"\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00028\u00000$0#8FX\u0086\u0084\u0002¢\u0006\u0012\n\u0004\b)\u0010\u0010\u0012\u0004\b%\u0010&\u001a\u0004\b'\u0010(R%\u0010*\u001a\f\u0012\b\u0012\u0006\u0012\u0002\b\u00030+0#8FX\u0086\u0084\u0002¢\u0006\f\n\u0004\b-\u0010\u0010\u001a\u0004\b,\u0010(R#\u0010.\u001a\u0004\u0018\u00018\u00008FX\u0086\u0084\u0002¢\u0006\u0012\n\u0004\b2\u0010\n\u0012\u0004\b/\u0010&\u001a\u0004\b0\u00101R!\u00103\u001a\b\u0012\u0004\u0012\u0002040\u00128FX\u0086\u0084\u0002¢\u0006\f\n\u0004\b6\u0010\u0010\u001a\u0004\b5\u0010\u0015R!\u00107\u001a\b\u0012\u0004\u0012\u0002080\u00128FX\u0086\u0084\u0002¢\u0006\f\n\u0004\b:\u0010\u0010\u001a\u0004\b9\u0010\u0015R)\u0010;\u001a\u0010\u0012\f\u0012\n\u0012\u0006\b\u0001\u0012\u00028\u00000+0\u00128FX\u0086\u0084\u0002¢\u0006\f\n\u0004\b=\u0010\u0010\u001a\u0004\b<\u0010\u0015R%\u0010>\u001a\f\u0012\b\u0012\u0006\u0012\u0002\b\u00030?0#8FX\u0086\u0084\u0002¢\u0006\f\n\u0004\bA\u0010\u0010\u001a\u0004\b@\u0010(R%\u0010B\u001a\f\u0012\b\u0012\u0006\u0012\u0002\b\u00030?0#8BX\u0082\u0084\u0002¢\u0006\f\n\u0004\bD\u0010\u0010\u001a\u0004\bC\u0010(R%\u0010E\u001a\f\u0012\b\u0012\u0006\u0012\u0002\b\u00030?0#8BX\u0082\u0084\u0002¢\u0006\f\n\u0004\bG\u0010\u0010\u001a\u0004\bF\u0010(R%\u0010H\u001a\f\u0012\b\u0012\u0006\u0012\u0002\b\u00030?0#8BX\u0082\u0084\u0002¢\u0006\f\n\u0004\bJ\u0010\u0010\u001a\u0004\bI\u0010(R%\u0010K\u001a\f\u0012\b\u0012\u0006\u0012\u0002\b\u00030?0#8FX\u0086\u0084\u0002¢\u0006\f\n\u0004\bM\u0010\u0010\u001a\u0004\bL\u0010(R%\u0010N\u001a\f\u0012\b\u0012\u0006\u0012\u0002\b\u00030?0#8FX\u0086\u0084\u0002¢\u0006\f\n\u0004\bP\u0010\u0010\u001a\u0004\bO\u0010(R%\u0010Q\u001a\f\u0012\b\u0012\u0006\u0012\u0002\b\u00030?0#8FX\u0086\u0084\u0002¢\u0006\f\n\u0004\bS\u0010\u0010\u001a\u0004\bR\u0010(R%\u0010T\u001a\f\u0012\b\u0012\u0006\u0012\u0002\b\u00030?0#8FX\u0086\u0084\u0002¢\u0006\f\n\u0004\bV\u0010\u0010\u001a\u0004\bU\u0010(¨\u0006W"}, d2 = {"Lkotlin/reflect/jvm/internal/KClassImpl$Data;", "Lkotlin/reflect/jvm/internal/KDeclarationContainerImpl$Data;", "Lkotlin/reflect/jvm/internal/KDeclarationContainerImpl;", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "(Lkotlin/reflect/jvm/internal/KClassImpl;)V", "kmClass", "Lkotlin/reflect/jvm/internal/impl/km/KmClass;", "getKmClass", "()Lkotlin/metadata/KmClass;", "kmClass$delegate", "Lkotlin/Lazy;", "descriptor", "Lkotlin/reflect/jvm/internal/impl/descriptors/ClassDescriptor;", "getDescriptor", "()Lorg/jetbrains/kotlin/descriptors/ClassDescriptor;", "descriptor$delegate", "Lkotlin/reflect/jvm/internal/ReflectProperties$LazySoftVal;", "annotations", "", "", "getAnnotations", "()Ljava/util/List;", "annotations$delegate", "simpleName", "", "getSimpleName", "()Ljava/lang/String;", "simpleName$delegate", "qualifiedName", "getQualifiedName", "qualifiedName$delegate", "calculateLocalClassName", "jClass", "Ljava/lang/Class;", "constructors", "", "Lkotlin/reflect/KFunction;", "getConstructors$annotations", "()V", "getConstructors", "()Ljava/util/Collection;", "constructors$delegate", "nestedClasses", "Lkotlin/reflect/KClass;", "getNestedClasses", "nestedClasses$delegate", "objectInstance", "getObjectInstance$annotations", "getObjectInstance", "()Ljava/lang/Object;", "objectInstance$delegate", "typeParameters", "Lkotlin/reflect/KTypeParameter;", "getTypeParameters", "typeParameters$delegate", "supertypes", "Lkotlin/reflect/KType;", "getSupertypes", "supertypes$delegate", "sealedSubclasses", "getSealedSubclasses", "sealedSubclasses$delegate", "declaredNonStaticMembers", "Lkotlin/reflect/jvm/internal/KCallableImpl;", "getDeclaredNonStaticMembers", "declaredNonStaticMembers$delegate", "declaredStaticMembers", "getDeclaredStaticMembers", "declaredStaticMembers$delegate", "inheritedNonStaticMembers", "getInheritedNonStaticMembers", "inheritedNonStaticMembers$delegate", "inheritedStaticMembers", "getInheritedStaticMembers", "inheritedStaticMembers$delegate", "allNonStaticMembers", "getAllNonStaticMembers", "allNonStaticMembers$delegate", "allStaticMembers", "getAllStaticMembers", "allStaticMembers$delegate", "declaredMembers", "getDeclaredMembers", "declaredMembers$delegate", "allMembers", "getAllMembers", "allMembers$delegate", "kotlin-reflection"}, k = 1, mv = {2, 2, 0}, xi = 48)
    public final class Data extends KDeclarationContainerImpl.Data {
        static final /* synthetic */ KProperty<Object>[] $$delegatedProperties = {Reflection.property1(new PropertyReference1Impl(Data.class, "descriptor", "getDescriptor()Lorg/jetbrains/kotlin/descriptors/ClassDescriptor;", 0)), Reflection.property1(new PropertyReference1Impl(Data.class, "annotations", "getAnnotations()Ljava/util/List;", 0)), Reflection.property1(new PropertyReference1Impl(Data.class, "simpleName", "getSimpleName()Ljava/lang/String;", 0)), Reflection.property1(new PropertyReference1Impl(Data.class, "qualifiedName", "getQualifiedName()Ljava/lang/String;", 0)), Reflection.property1(new PropertyReference1Impl(Data.class, "constructors", "getConstructors()Ljava/util/Collection;", 0)), Reflection.property1(new PropertyReference1Impl(Data.class, "nestedClasses", "getNestedClasses()Ljava/util/Collection;", 0)), Reflection.property1(new PropertyReference1Impl(Data.class, "typeParameters", "getTypeParameters()Ljava/util/List;", 0)), Reflection.property1(new PropertyReference1Impl(Data.class, "supertypes", "getSupertypes()Ljava/util/List;", 0)), Reflection.property1(new PropertyReference1Impl(Data.class, "sealedSubclasses", "getSealedSubclasses()Ljava/util/List;", 0)), Reflection.property1(new PropertyReference1Impl(Data.class, "declaredNonStaticMembers", "getDeclaredNonStaticMembers()Ljava/util/Collection;", 0)), Reflection.property1(new PropertyReference1Impl(Data.class, "declaredStaticMembers", "getDeclaredStaticMembers()Ljava/util/Collection;", 0)), Reflection.property1(new PropertyReference1Impl(Data.class, "inheritedNonStaticMembers", "getInheritedNonStaticMembers()Ljava/util/Collection;", 0)), Reflection.property1(new PropertyReference1Impl(Data.class, "inheritedStaticMembers", "getInheritedStaticMembers()Ljava/util/Collection;", 0)), Reflection.property1(new PropertyReference1Impl(Data.class, "allNonStaticMembers", "getAllNonStaticMembers()Ljava/util/Collection;", 0)), Reflection.property1(new PropertyReference1Impl(Data.class, "allStaticMembers", "getAllStaticMembers()Ljava/util/Collection;", 0)), Reflection.property1(new PropertyReference1Impl(Data.class, "declaredMembers", "getDeclaredMembers()Ljava/util/Collection;", 0)), Reflection.property1(new PropertyReference1Impl(Data.class, "allMembers", "getAllMembers()Ljava/util/Collection;", 0))};

        /* JADX INFO: renamed from: allMembers$delegate, reason: from kotlin metadata */
        private final ReflectProperties.LazySoftVal allMembers;

        /* JADX INFO: renamed from: allNonStaticMembers$delegate, reason: from kotlin metadata */
        private final ReflectProperties.LazySoftVal allNonStaticMembers;

        /* JADX INFO: renamed from: allStaticMembers$delegate, reason: from kotlin metadata */
        private final ReflectProperties.LazySoftVal allStaticMembers;

        /* JADX INFO: renamed from: annotations$delegate, reason: from kotlin metadata */
        private final ReflectProperties.LazySoftVal annotations;

        /* JADX INFO: renamed from: constructors$delegate, reason: from kotlin metadata */
        private final ReflectProperties.LazySoftVal constructors;

        /* JADX INFO: renamed from: declaredMembers$delegate, reason: from kotlin metadata */
        private final ReflectProperties.LazySoftVal declaredMembers;

        /* JADX INFO: renamed from: declaredNonStaticMembers$delegate, reason: from kotlin metadata */
        private final ReflectProperties.LazySoftVal declaredNonStaticMembers;

        /* JADX INFO: renamed from: declaredStaticMembers$delegate, reason: from kotlin metadata */
        private final ReflectProperties.LazySoftVal declaredStaticMembers;

        /* JADX INFO: renamed from: descriptor$delegate, reason: from kotlin metadata */
        private final ReflectProperties.LazySoftVal descriptor;

        /* JADX INFO: renamed from: inheritedNonStaticMembers$delegate, reason: from kotlin metadata */
        private final ReflectProperties.LazySoftVal inheritedNonStaticMembers;

        /* JADX INFO: renamed from: inheritedStaticMembers$delegate, reason: from kotlin metadata */
        private final ReflectProperties.LazySoftVal inheritedStaticMembers;

        /* JADX INFO: renamed from: kmClass$delegate, reason: from kotlin metadata */
        private final Lazy kmClass;

        /* JADX INFO: renamed from: nestedClasses$delegate, reason: from kotlin metadata */
        private final ReflectProperties.LazySoftVal nestedClasses;

        /* JADX INFO: renamed from: objectInstance$delegate, reason: from kotlin metadata */
        private final Lazy objectInstance;

        /* JADX INFO: renamed from: qualifiedName$delegate, reason: from kotlin metadata */
        private final ReflectProperties.LazySoftVal qualifiedName;

        /* JADX INFO: renamed from: sealedSubclasses$delegate, reason: from kotlin metadata */
        private final ReflectProperties.LazySoftVal sealedSubclasses;

        /* JADX INFO: renamed from: simpleName$delegate, reason: from kotlin metadata */
        private final ReflectProperties.LazySoftVal simpleName;

        /* JADX INFO: renamed from: supertypes$delegate, reason: from kotlin metadata */
        private final ReflectProperties.LazySoftVal supertypes;

        /* JADX INFO: renamed from: typeParameters$delegate, reason: from kotlin metadata */
        private final ReflectProperties.LazySoftVal typeParameters;

        public Data() {
            super();
            this.kmClass = LazyKt.lazy(LazyThreadSafetyMode.PUBLICATION, new Function0(this) { // from class: kotlin.reflect.jvm.internal.KClassImpl$Data$$Lambda$0
                private final KClassImpl.Data arg$0;

                {
                    this.arg$0 = this;
                }

                @Override // kotlin.jvm.functions.Function0
                public Object invoke() {
                    return KClassImpl.Data.kmClass_delegate$lambda$1(this.arg$0);
                }
            });
            this.descriptor = ReflectProperties.lazySoft(new Function0(KClassImpl.this) { // from class: kotlin.reflect.jvm.internal.KClassImpl$Data$$Lambda$1
                private final KClassImpl arg$0;

                {
                    this.arg$0 = kClassImpl;
                }

                @Override // kotlin.jvm.functions.Function0
                public Object invoke() {
                    return KClassImpl.Data.descriptor_delegate$lambda$2(this.arg$0);
                }
            });
            this.annotations = ReflectProperties.lazySoft(new Function0(KClassImpl.this) { // from class: kotlin.reflect.jvm.internal.KClassImpl$Data$$Lambda$2
                private final KClassImpl arg$0;

                {
                    this.arg$0 = kClassImpl;
                }

                @Override // kotlin.jvm.functions.Function0
                public Object invoke() {
                    return KClassImpl.Data.annotations_delegate$lambda$4(this.arg$0);
                }
            });
            this.simpleName = ReflectProperties.lazySoft(new Function0(KClassImpl.this, this) { // from class: kotlin.reflect.jvm.internal.KClassImpl$Data$$Lambda$3
                private final KClassImpl arg$0;
                private final KClassImpl.Data arg$1;

                {
                    this.arg$0 = kClassImpl;
                    this.arg$1 = this;
                }

                @Override // kotlin.jvm.functions.Function0
                public Object invoke() {
                    return KClassImpl.Data.simpleName_delegate$lambda$5(this.arg$0, this.arg$1);
                }
            });
            this.qualifiedName = ReflectProperties.lazySoft(new Function0(KClassImpl.this) { // from class: kotlin.reflect.jvm.internal.KClassImpl$Data$$Lambda$4
                private final KClassImpl arg$0;

                {
                    this.arg$0 = kClassImpl;
                }

                @Override // kotlin.jvm.functions.Function0
                public Object invoke() {
                    return KClassImpl.Data.qualifiedName_delegate$lambda$6(this.arg$0);
                }
            });
            this.constructors = ReflectProperties.lazySoft(new Function0(KClassImpl.this) { // from class: kotlin.reflect.jvm.internal.KClassImpl$Data$$Lambda$5
                private final KClassImpl arg$0;

                {
                    this.arg$0 = kClassImpl;
                }

                @Override // kotlin.jvm.functions.Function0
                public Object invoke() {
                    return KClassImpl.Data.constructors_delegate$lambda$10(this.arg$0);
                }
            });
            this.nestedClasses = ReflectProperties.lazySoft(new Function0(this, KClassImpl.this) { // from class: kotlin.reflect.jvm.internal.KClassImpl$Data$$Lambda$6
                private final KClassImpl.Data arg$0;
                private final KClassImpl arg$1;

                {
                    this.arg$0 = this;
                    this.arg$1 = kClassImpl;
                }

                @Override // kotlin.jvm.functions.Function0
                public Object invoke() {
                    return KClassImpl.Data.nestedClasses_delegate$lambda$13(this.arg$0, this.arg$1);
                }
            });
            this.objectInstance = LazyKt.lazy(LazyThreadSafetyMode.PUBLICATION, new Function0(this, KClassImpl.this) { // from class: kotlin.reflect.jvm.internal.KClassImpl$Data$$Lambda$7
                private final KClassImpl.Data arg$0;
                private final KClassImpl arg$1;

                {
                    this.arg$0 = this;
                    this.arg$1 = kClassImpl;
                }

                @Override // kotlin.jvm.functions.Function0
                public Object invoke() {
                    return KClassImpl.Data.objectInstance_delegate$lambda$14(this.arg$0, this.arg$1);
                }
            });
            this.typeParameters = ReflectProperties.lazySoft(new Function0(this, KClassImpl.this) { // from class: kotlin.reflect.jvm.internal.KClassImpl$Data$$Lambda$8
                private final KClassImpl.Data arg$0;
                private final KClassImpl arg$1;

                {
                    this.arg$0 = this;
                    this.arg$1 = kClassImpl;
                }

                @Override // kotlin.jvm.functions.Function0
                public Object invoke() {
                    return KClassImpl.Data.typeParameters_delegate$lambda$16(this.arg$0, this.arg$1);
                }
            });
            this.supertypes = ReflectProperties.lazySoft(new Function0(this, KClassImpl.this) { // from class: kotlin.reflect.jvm.internal.KClassImpl$Data$$Lambda$9
                private final KClassImpl.Data arg$0;
                private final KClassImpl arg$1;

                {
                    this.arg$0 = this;
                    this.arg$1 = kClassImpl;
                }

                @Override // kotlin.jvm.functions.Function0
                public Object invoke() {
                    return KClassImpl.Data.supertypes_delegate$lambda$21(this.arg$0, this.arg$1);
                }
            });
            this.sealedSubclasses = ReflectProperties.lazySoft(new Function0(KClassImpl.this, this) { // from class: kotlin.reflect.jvm.internal.KClassImpl$Data$$Lambda$10
                private final KClassImpl arg$0;
                private final KClassImpl.Data arg$1;

                {
                    this.arg$0 = kClassImpl;
                    this.arg$1 = this;
                }

                @Override // kotlin.jvm.functions.Function0
                public Object invoke() {
                    return KClassImpl.Data.sealedSubclasses_delegate$lambda$23(this.arg$0, this.arg$1);
                }
            });
            this.declaredNonStaticMembers = ReflectProperties.lazySoft(new Function0(KClassImpl.this) { // from class: kotlin.reflect.jvm.internal.KClassImpl$Data$$Lambda$11
                private final KClassImpl arg$0;

                {
                    this.arg$0 = kClassImpl;
                }

                @Override // kotlin.jvm.functions.Function0
                public Object invoke() {
                    return KClassImpl.Data.declaredNonStaticMembers_delegate$lambda$24(this.arg$0);
                }
            });
            this.declaredStaticMembers = ReflectProperties.lazySoft(new Function0(KClassImpl.this) { // from class: kotlin.reflect.jvm.internal.KClassImpl$Data$$Lambda$12
                private final KClassImpl arg$0;

                {
                    this.arg$0 = kClassImpl;
                }

                @Override // kotlin.jvm.functions.Function0
                public Object invoke() {
                    return KClassImpl.Data.declaredStaticMembers_delegate$lambda$25(this.arg$0);
                }
            });
            this.inheritedNonStaticMembers = ReflectProperties.lazySoft(new Function0(KClassImpl.this) { // from class: kotlin.reflect.jvm.internal.KClassImpl$Data$$Lambda$13
                private final KClassImpl arg$0;

                {
                    this.arg$0 = kClassImpl;
                }

                @Override // kotlin.jvm.functions.Function0
                public Object invoke() {
                    return KClassImpl.Data.inheritedNonStaticMembers_delegate$lambda$26(this.arg$0);
                }
            });
            this.inheritedStaticMembers = ReflectProperties.lazySoft(new Function0(KClassImpl.this) { // from class: kotlin.reflect.jvm.internal.KClassImpl$Data$$Lambda$14
                private final KClassImpl arg$0;

                {
                    this.arg$0 = kClassImpl;
                }

                @Override // kotlin.jvm.functions.Function0
                public Object invoke() {
                    return KClassImpl.Data.inheritedStaticMembers_delegate$lambda$27(this.arg$0);
                }
            });
            this.allNonStaticMembers = ReflectProperties.lazySoft(new Function0(this) { // from class: kotlin.reflect.jvm.internal.KClassImpl$Data$$Lambda$15
                private final KClassImpl.Data arg$0;

                {
                    this.arg$0 = this;
                }

                @Override // kotlin.jvm.functions.Function0
                public Object invoke() {
                    return KClassImpl.Data.allNonStaticMembers_delegate$lambda$28(this.arg$0);
                }
            });
            this.allStaticMembers = ReflectProperties.lazySoft(new Function0(this) { // from class: kotlin.reflect.jvm.internal.KClassImpl$Data$$Lambda$16
                private final KClassImpl.Data arg$0;

                {
                    this.arg$0 = this;
                }

                @Override // kotlin.jvm.functions.Function0
                public Object invoke() {
                    return KClassImpl.Data.allStaticMembers_delegate$lambda$29(this.arg$0);
                }
            });
            this.declaredMembers = ReflectProperties.lazySoft(new Function0(this) { // from class: kotlin.reflect.jvm.internal.KClassImpl$Data$$Lambda$17
                private final KClassImpl.Data arg$0;

                {
                    this.arg$0 = this;
                }

                @Override // kotlin.jvm.functions.Function0
                public Object invoke() {
                    return KClassImpl.Data.declaredMembers_delegate$lambda$30(this.arg$0);
                }
            });
            this.allMembers = ReflectProperties.lazySoft(new Function0(this) { // from class: kotlin.reflect.jvm.internal.KClassImpl$Data$$Lambda$18
                private final KClassImpl.Data arg$0;

                {
                    this.arg$0 = this;
                }

                @Override // kotlin.jvm.functions.Function0
                public Object invoke() {
                    return KClassImpl.Data.allMembers_delegate$lambda$31(this.arg$0);
                }
            });
        }

        public final KmClass getKmClass() {
            return (KmClass) this.kmClass.getValue();
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static final KmClass kmClass_delegate$lambda$1(Data data) {
            ClassDescriptor descriptor = data.getDescriptor();
            DeserializedClassDescriptor deserializedClassDescriptor = descriptor instanceof DeserializedClassDescriptor ? (DeserializedClassDescriptor) descriptor : null;
            if (deserializedClassDescriptor != null) {
                return ReadersKt.toKmClass$default(deserializedClassDescriptor.getClassProto(), deserializedClassDescriptor.getC().getNameResolver(), false, null, 6, null);
            }
            return null;
        }

        public final ClassDescriptor getDescriptor() {
            T value = this.descriptor.getValue(this, $$delegatedProperties[0]);
            Intrinsics.checkNotNullExpressionValue(value, "getValue(...)");
            return (ClassDescriptor) value;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static final ClassDescriptor descriptor_delegate$lambda$2(KClassImpl kClassImpl) {
            ClassDescriptor classDescriptorFindClassAcrossModuleDependencies;
            ClassId classId = kClassImpl.getClassId();
            RuntimeModuleData moduleData = kClassImpl.getData().getValue().getModuleData();
            ModuleDescriptor module = moduleData.getModule();
            if (classId.isLocal() && kClassImpl.getJClass().isAnnotationPresent(Metadata.class)) {
                classDescriptorFindClassAcrossModuleDependencies = moduleData.getDeserialization().deserializeClass(classId);
            } else {
                classDescriptorFindClassAcrossModuleDependencies = FindClassInModuleKt.findClassAcrossModuleDependencies(module, classId);
            }
            return classDescriptorFindClassAcrossModuleDependencies == null ? kClassImpl.createSyntheticClassOrFail(classId, moduleData) : classDescriptorFindClassAcrossModuleDependencies;
        }

        public final List<Annotation> getAnnotations() {
            T value = this.annotations.getValue(this, $$delegatedProperties[1]);
            Intrinsics.checkNotNullExpressionValue(value, "getValue(...)");
            return (List) value;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static final List annotations_delegate$lambda$4(KClassImpl kClassImpl) {
            Annotation[] annotations = kClassImpl.getJClass().getAnnotations();
            Intrinsics.checkNotNullExpressionValue(annotations, "getAnnotations(...)");
            ArrayList arrayList = new ArrayList();
            for (Annotation annotation : annotations) {
                if (!KClassImpl.SPECIAL_JVM_ANNOTATION_NAMES.contains(JvmClassMappingKt.getJavaClass(JvmClassMappingKt.getAnnotationClass(annotation)).getName())) {
                    arrayList.add(annotation);
                }
            }
            return UtilKt.unwrapRepeatableAnnotations(arrayList);
        }

        public final String getSimpleName() {
            return (String) this.simpleName.getValue(this, $$delegatedProperties[2]);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static final String simpleName_delegate$lambda$5(KClassImpl kClassImpl, Data data) {
            if (kClassImpl.getJClass().isAnonymousClass()) {
                return null;
            }
            ClassId classId = kClassImpl.getClassId();
            if (classId.isLocal()) {
                return data.calculateLocalClassName(kClassImpl.getJClass());
            }
            String strAsString = classId.getShortClassName().asString();
            Intrinsics.checkNotNullExpressionValue(strAsString, "asString(...)");
            return strAsString;
        }

        public final String getQualifiedName() {
            return (String) this.qualifiedName.getValue(this, $$delegatedProperties[3]);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static final String qualifiedName_delegate$lambda$6(KClassImpl kClassImpl) {
            if (kClassImpl.getJClass().isAnonymousClass()) {
                return null;
            }
            ClassId classId = kClassImpl.getClassId();
            if (classId.isLocal()) {
                return null;
            }
            return classId.asSingleFqName().asString();
        }

        private final String calculateLocalClassName(Class<?> jClass) {
            String simpleName = jClass.getSimpleName();
            Method enclosingMethod = jClass.getEnclosingMethod();
            if (enclosingMethod != null) {
                Intrinsics.checkNotNull(simpleName);
                return StringsKt.substringAfter$default(simpleName, enclosingMethod.getName() + '$', (String) null, 2, (Object) null);
            }
            Constructor<?> enclosingConstructor = jClass.getEnclosingConstructor();
            if (enclosingConstructor != null) {
                Intrinsics.checkNotNull(simpleName);
                return StringsKt.substringAfter$default(simpleName, enclosingConstructor.getName() + '$', (String) null, 2, (Object) null);
            }
            Intrinsics.checkNotNull(simpleName);
            return StringsKt.substringAfter$default(simpleName, '$', (String) null, 2, (Object) null);
        }

        public final Collection<KFunction<T>> getConstructors() {
            T value = this.constructors.getValue(this, $$delegatedProperties[4]);
            Intrinsics.checkNotNullExpressionValue(value, "getValue(...)");
            return (Collection) value;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static final List constructors_delegate$lambda$10(KClassImpl kClassImpl) {
            Collection<ConstructorDescriptor> constructorDescriptors = kClassImpl.getConstructorDescriptors();
            ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(constructorDescriptors, 10));
            Iterator<T> it = constructorDescriptors.iterator();
            while (it.hasNext()) {
                arrayList.add(new KFunctionImpl(kClassImpl, (ConstructorDescriptor) it.next()));
            }
            return arrayList;
        }

        public final Collection<KClass<?>> getNestedClasses() {
            T value = this.nestedClasses.getValue(this, $$delegatedProperties[5]);
            Intrinsics.checkNotNullExpressionValue(value, "getValue(...)");
            return (Collection) value;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static final List nestedClasses_delegate$lambda$13(Data data, KClassImpl kClassImpl) {
            KmClass kmClass = data.getKmClass();
            if (kmClass != null) {
                ClassId classId = MetadataUtilKt.toClassId(kmClass.getName());
                ClassLoader safeClassLoader = ReflectClassUtilKt.getSafeClassLoader(kClassImpl.getJClass());
                List<String> nestedClasses = kmClass.getNestedClasses();
                ArrayList arrayList = new ArrayList();
                Iterator<T> it = nestedClasses.iterator();
                while (it.hasNext()) {
                    Name nameIdentifier = Name.identifier((String) it.next());
                    Intrinsics.checkNotNullExpressionValue(nameIdentifier, "identifier(...)");
                    Class clsLoadClass$default = UtilKt.loadClass$default(safeClassLoader, classId.createNestedClassId(nameIdentifier), 0, 2, null);
                    KClass kotlinClass = clsLoadClass$default != null ? JvmClassMappingKt.getKotlinClass(clsLoadClass$default) : null;
                    if (kotlinClass != null) {
                        arrayList.add(kotlinClass);
                    }
                }
                return arrayList;
            }
            Class<?>[] declaredClasses = kClassImpl.getJClass().getDeclaredClasses();
            Intrinsics.checkNotNullExpressionValue(declaredClasses, "getDeclaredClasses(...)");
            ArrayList arrayList2 = new ArrayList();
            for (Class<?> cls : declaredClasses) {
                Intrinsics.checkNotNull(cls);
                KClass kotlinClass2 = JvmClassMappingKt.getKotlinClass(cls);
                if (kotlinClass2 != null) {
                    arrayList2.add(kotlinClass2);
                }
            }
            return arrayList2;
        }

        public final T getObjectInstance() {
            return (T) this.objectInstance.getValue();
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static final Object objectInstance_delegate$lambda$14(Data data, KClassImpl kClassImpl) throws IllegalAccessException, NoSuchFieldException {
            Field declaredField;
            KmClass kmClass = data.getKmClass();
            if (kmClass == null || !(Attributes.getKind(kmClass) == ClassKind.OBJECT || Attributes.getKind(kmClass) == ClassKind.COMPANION_OBJECT)) {
                return null;
            }
            if (Attributes.getKind(kmClass) == ClassKind.COMPANION_OBJECT && !CollectionsKt.contains(CompanionObjectMapping.INSTANCE.getClassIds(), MetadataUtilKt.toClassId(kmClass.getName()).getOuterClassId())) {
                declaredField = kClassImpl.getJClass().getEnclosingClass().getDeclaredField(MetadataUtilKt.toNonLocalSimpleName(kmClass.getName()));
            } else {
                declaredField = kClassImpl.getJClass().getDeclaredField("INSTANCE");
            }
            Object obj = declaredField.get(null);
            Intrinsics.checkNotNull(obj, "null cannot be cast to non-null type T of kotlin.reflect.jvm.internal.KClassImpl");
            return obj;
        }

        public final List<KTypeParameter> getTypeParameters() {
            T value = this.typeParameters.getValue(this, $$delegatedProperties[6]);
            Intrinsics.checkNotNullExpressionValue(value, "getValue(...)");
            return (List) value;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static final List typeParameters_delegate$lambda$16(Data data, KClassImpl kClassImpl) {
            List<TypeParameterDescriptor> declaredTypeParameters = data.getDescriptor().getDeclaredTypeParameters();
            Intrinsics.checkNotNullExpressionValue(declaredTypeParameters, "getDeclaredTypeParameters(...)");
            List<TypeParameterDescriptor> list = declaredTypeParameters;
            ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(list, 10));
            for (TypeParameterDescriptor typeParameterDescriptor : list) {
                Intrinsics.checkNotNull(typeParameterDescriptor);
                arrayList.add(new KTypeParameterImpl(kClassImpl, typeParameterDescriptor));
            }
            return arrayList;
        }

        public final List<KType> getSupertypes() {
            T value = this.supertypes.getValue(this, $$delegatedProperties[7]);
            Intrinsics.checkNotNullExpressionValue(value, "getValue(...)");
            return (List) value;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static final List supertypes_delegate$lambda$21(final Data data, final KClassImpl kClassImpl) {
            Collection<KotlinType> collectionMo3824getSupertypes = data.getDescriptor().getTypeConstructor().mo3824getSupertypes();
            Intrinsics.checkNotNullExpressionValue(collectionMo3824getSupertypes, "getSupertypes(...)");
            ArrayList arrayList = new ArrayList(collectionMo3824getSupertypes.size());
            for (final KotlinType kotlinType : collectionMo3824getSupertypes) {
                Intrinsics.checkNotNull(kotlinType);
                arrayList.add(new KTypeImpl(kotlinType, new Function0(kotlinType, data, kClassImpl) { // from class: kotlin.reflect.jvm.internal.KClassImpl$Data$$Lambda$19
                    private final KotlinType arg$0;
                    private final KClassImpl.Data arg$1;
                    private final KClassImpl arg$2;

                    {
                        this.arg$0 = kotlinType;
                        this.arg$1 = data;
                        this.arg$2 = kClassImpl;
                    }

                    @Override // kotlin.jvm.functions.Function0
                    public Object invoke() {
                        return KClassImpl.Data.supertypes_delegate$lambda$21$lambda$18$lambda$17(this.arg$0, this.arg$1, this.arg$2);
                    }
                }));
            }
            ArrayList arrayList2 = arrayList;
            if (!KotlinBuiltIns.isSpecialClassWithNoSupertypes(data.getDescriptor())) {
                ArrayList arrayList3 = arrayList;
                if ((arrayList3 instanceof Collection) && arrayList3.isEmpty()) {
                    SimpleType anyType = DescriptorUtilsKt.getBuiltIns(data.getDescriptor()).getAnyType();
                    Intrinsics.checkNotNullExpressionValue(anyType, "getAnyType(...)");
                    arrayList2.add(new KTypeImpl(anyType, new Function0() { // from class: kotlin.reflect.jvm.internal.KClassImpl$Data$$Lambda$20
                        @Override // kotlin.jvm.functions.Function0
                        public Object invoke() {
                            return KClassImpl.Data.supertypes_delegate$lambda$21$lambda$20();
                        }
                    }));
                } else {
                    Iterator<T> it = arrayList3.iterator();
                    while (it.hasNext()) {
                        kotlin.reflect.jvm.internal.impl.descriptors.ClassKind kind = DescriptorUtils.getClassDescriptorForType(((KTypeImpl) it.next()).getType()).getKind();
                        Intrinsics.checkNotNullExpressionValue(kind, "getKind(...)");
                        if (kind == kotlin.reflect.jvm.internal.impl.descriptors.ClassKind.INTERFACE || kind == kotlin.reflect.jvm.internal.impl.descriptors.ClassKind.ANNOTATION_CLASS) {
                        }
                    }
                    SimpleType anyType2 = DescriptorUtilsKt.getBuiltIns(data.getDescriptor()).getAnyType();
                    Intrinsics.checkNotNullExpressionValue(anyType2, "getAnyType(...)");
                    arrayList2.add(new KTypeImpl(anyType2, new Function0() { // from class: kotlin.reflect.jvm.internal.KClassImpl$Data$$Lambda$20
                        @Override // kotlin.jvm.functions.Function0
                        public Object invoke() {
                            return KClassImpl.Data.supertypes_delegate$lambda$21$lambda$20();
                        }
                    }));
                }
            }
            return kotlin.reflect.jvm.internal.impl.utils.CollectionsKt.compact(arrayList);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static final Type supertypes_delegate$lambda$21$lambda$18$lambda$17(KotlinType kotlinType, Data data, KClassImpl kClassImpl) {
            ClassifierDescriptor classifierDescriptorMo3823getDeclarationDescriptor = kotlinType.getConstructor().mo3823getDeclarationDescriptor();
            if (!(classifierDescriptorMo3823getDeclarationDescriptor instanceof ClassDescriptor)) {
                throw new KotlinReflectionInternalError("Supertype not a class: " + classifierDescriptorMo3823getDeclarationDescriptor);
            }
            Class<?> javaClass = UtilKt.toJavaClass((ClassDescriptor) classifierDescriptorMo3823getDeclarationDescriptor);
            if (javaClass == null) {
                throw new KotlinReflectionInternalError("Unsupported superclass of " + data + ": " + classifierDescriptorMo3823getDeclarationDescriptor);
            }
            if (Intrinsics.areEqual(kClassImpl.getJClass().getSuperclass(), javaClass)) {
                Type genericSuperclass = kClassImpl.getJClass().getGenericSuperclass();
                Intrinsics.checkNotNull(genericSuperclass);
                return genericSuperclass;
            }
            Class<?>[] interfaces = kClassImpl.getJClass().getInterfaces();
            Intrinsics.checkNotNullExpressionValue(interfaces, "getInterfaces(...)");
            int iIndexOf = ArraysKt.indexOf(interfaces, javaClass);
            if (iIndexOf < 0) {
                throw new KotlinReflectionInternalError("No superclass of " + data + " in Java reflection for " + classifierDescriptorMo3823getDeclarationDescriptor);
            }
            Type type = kClassImpl.getJClass().getGenericInterfaces()[iIndexOf];
            Intrinsics.checkNotNull(type);
            return type;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static final Type supertypes_delegate$lambda$21$lambda$20() {
            return Object.class;
        }

        public final List<KClass<? extends T>> getSealedSubclasses() {
            T value = this.sealedSubclasses.getValue(this, $$delegatedProperties[8]);
            Intrinsics.checkNotNullExpressionValue(value, "getValue(...)");
            return (List) value;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static final List sealedSubclasses_delegate$lambda$23(KClassImpl kClassImpl, Data data) throws IllegalAccessException, InvocationTargetException {
            ArrayList arrayListEmptyList;
            ClassLoader safeClassLoader = ReflectClassUtilKt.getSafeClassLoader(kClassImpl.getJClass());
            KmClass kmClass = data.getKmClass();
            if (kmClass != null) {
                List<String> sealedSubclasses = kmClass.getSealedSubclasses();
                ArrayList arrayList = new ArrayList();
                Iterator<T> it = sealedSubclasses.iterator();
                while (it.hasNext()) {
                    KClass<?> kClassLoadKClass = MetadataUtilKt.loadKClass(safeClassLoader, (String) it.next());
                    if (kClassLoadKClass != null) {
                        arrayList.add(kClassLoadKClass);
                    }
                }
                arrayListEmptyList = arrayList;
            } else if (Intrinsics.areEqual((Object) Java16SealedRecordLoader.INSTANCE.loadIsSealed(kClassImpl.getJClass()), (Object) true)) {
                Class<?>[] clsArrLoadGetPermittedSubclasses = Java16SealedRecordLoader.INSTANCE.loadGetPermittedSubclasses(kClassImpl.getJClass());
                if (clsArrLoadGetPermittedSubclasses != null) {
                    ArrayList arrayList2 = new ArrayList(clsArrLoadGetPermittedSubclasses.length);
                    for (Class<?> cls : clsArrLoadGetPermittedSubclasses) {
                        arrayList2.add(JvmClassMappingKt.getKotlinClass(cls));
                    }
                    arrayListEmptyList = arrayList2;
                } else {
                    arrayListEmptyList = null;
                }
                if (arrayListEmptyList == null) {
                    arrayListEmptyList = CollectionsKt.emptyList();
                }
            } else {
                arrayListEmptyList = CollectionsKt.emptyList();
            }
            Intrinsics.checkNotNull(arrayListEmptyList, "null cannot be cast to non-null type kotlin.collections.List<kotlin.reflect.KClass<out T of kotlin.reflect.jvm.internal.KClassImpl>>");
            return arrayListEmptyList;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static final Collection declaredNonStaticMembers_delegate$lambda$24(KClassImpl kClassImpl) {
            return kClassImpl.getMembers(kClassImpl.getMemberScope$kotlin_reflection(), KDeclarationContainerImpl.MemberBelonginess.DECLARED);
        }

        public final Collection<KCallableImpl<?>> getDeclaredNonStaticMembers() {
            T value = this.declaredNonStaticMembers.getValue(this, $$delegatedProperties[9]);
            Intrinsics.checkNotNullExpressionValue(value, "getValue(...)");
            return (Collection) value;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static final Collection declaredStaticMembers_delegate$lambda$25(KClassImpl kClassImpl) {
            return kClassImpl.getMembers(kClassImpl.getStaticScope$kotlin_reflection(), KDeclarationContainerImpl.MemberBelonginess.DECLARED);
        }

        private final Collection<KCallableImpl<?>> getDeclaredStaticMembers() {
            T value = this.declaredStaticMembers.getValue(this, $$delegatedProperties[10]);
            Intrinsics.checkNotNullExpressionValue(value, "getValue(...)");
            return (Collection) value;
        }

        private final Collection<KCallableImpl<?>> getInheritedNonStaticMembers() {
            T value = this.inheritedNonStaticMembers.getValue(this, $$delegatedProperties[11]);
            Intrinsics.checkNotNullExpressionValue(value, "getValue(...)");
            return (Collection) value;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static final Collection inheritedNonStaticMembers_delegate$lambda$26(KClassImpl kClassImpl) {
            return kClassImpl.getMembers(kClassImpl.getMemberScope$kotlin_reflection(), KDeclarationContainerImpl.MemberBelonginess.INHERITED);
        }

        private final Collection<KCallableImpl<?>> getInheritedStaticMembers() {
            T value = this.inheritedStaticMembers.getValue(this, $$delegatedProperties[12]);
            Intrinsics.checkNotNullExpressionValue(value, "getValue(...)");
            return (Collection) value;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static final Collection inheritedStaticMembers_delegate$lambda$27(KClassImpl kClassImpl) {
            return kClassImpl.getMembers(kClassImpl.getStaticScope$kotlin_reflection(), KDeclarationContainerImpl.MemberBelonginess.INHERITED);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static final List allNonStaticMembers_delegate$lambda$28(Data data) {
            return CollectionsKt.plus((Collection) data.getDeclaredNonStaticMembers(), (Iterable) data.getInheritedNonStaticMembers());
        }

        public final Collection<KCallableImpl<?>> getAllNonStaticMembers() {
            T value = this.allNonStaticMembers.getValue(this, $$delegatedProperties[13]);
            Intrinsics.checkNotNullExpressionValue(value, "getValue(...)");
            return (Collection) value;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static final List allStaticMembers_delegate$lambda$29(Data data) {
            return CollectionsKt.plus((Collection) data.getDeclaredStaticMembers(), (Iterable) data.getInheritedStaticMembers());
        }

        public final Collection<KCallableImpl<?>> getAllStaticMembers() {
            T value = this.allStaticMembers.getValue(this, $$delegatedProperties[14]);
            Intrinsics.checkNotNullExpressionValue(value, "getValue(...)");
            return (Collection) value;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static final List declaredMembers_delegate$lambda$30(Data data) {
            return CollectionsKt.plus((Collection) data.getDeclaredNonStaticMembers(), (Iterable) data.getDeclaredStaticMembers());
        }

        public final Collection<KCallableImpl<?>> getDeclaredMembers() {
            T value = this.declaredMembers.getValue(this, $$delegatedProperties[15]);
            Intrinsics.checkNotNullExpressionValue(value, "getValue(...)");
            return (Collection) value;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static final List allMembers_delegate$lambda$31(Data data) {
            return CollectionsKt.plus((Collection) data.getAllNonStaticMembers(), (Iterable) data.getAllStaticMembers());
        }

        public final Collection<KCallableImpl<?>> getAllMembers() {
            T value = this.allMembers.getValue(this, $$delegatedProperties[16]);
            Intrinsics.checkNotNullExpressionValue(value, "getValue(...)");
            return (Collection) value;
        }
    }

    public KClassImpl(Class<T> jClass) {
        Intrinsics.checkNotNullParameter(jClass, "jClass");
        this.jClass = jClass;
        this.data = LazyKt.lazy(LazyThreadSafetyMode.PUBLICATION, new Function0(this) { // from class: kotlin.reflect.jvm.internal.KClassImpl$$Lambda$0
            private final KClassImpl arg$0;

            {
                this.arg$0 = this;
            }

            @Override // kotlin.jvm.functions.Function0
            public Object invoke() {
                return KClassImpl.data$lambda$0(this.arg$0);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Data data$lambda$0(KClassImpl kClassImpl) {
        return new Data();
    }

    public final Lazy<KClassImpl<T>.Data> getData() {
        return this.data;
    }

    @Override // kotlin.reflect.jvm.internal.KClassifierImpl
    public ClassDescriptor getDescriptor() {
        return this.data.getValue().getDescriptor();
    }

    private final KmClass getKmClass() {
        return this.data.getValue().getKmClass();
    }

    @Override // kotlin.reflect.KAnnotatedElement
    public List<Annotation> getAnnotations() {
        return this.data.getValue().getAnnotations();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final ClassId getClassId() {
        return RuntimeTypeMapper.INSTANCE.mapJvmClassToKotlinClassId(getJClass());
    }

    public final MemberScope getMemberScope$kotlin_reflection() {
        return getDescriptor().getDefaultType().getMemberScope();
    }

    public final MemberScope getStaticScope$kotlin_reflection() {
        MemberScope staticScope = getDescriptor().getStaticScope();
        Intrinsics.checkNotNullExpressionValue(staticScope, "getStaticScope(...)");
        return staticScope;
    }

    @Override // kotlin.reflect.KDeclarationContainer
    public Collection<KCallable<?>> getMembers() {
        return this.data.getValue().getAllMembers();
    }

    @Override // kotlin.reflect.jvm.internal.KDeclarationContainerImpl
    public Collection<ConstructorDescriptor> getConstructorDescriptors() {
        ClassDescriptor descriptor = getDescriptor();
        if (descriptor.getKind() == kotlin.reflect.jvm.internal.impl.descriptors.ClassKind.INTERFACE || descriptor.getKind() == kotlin.reflect.jvm.internal.impl.descriptors.ClassKind.OBJECT) {
            return CollectionsKt.emptyList();
        }
        Collection<ClassConstructorDescriptor> constructors = descriptor.getConstructors();
        Intrinsics.checkNotNullExpressionValue(constructors, "getConstructors(...)");
        return constructors;
    }

    @Override // kotlin.reflect.jvm.internal.KDeclarationContainerImpl
    public Collection<PropertyDescriptor> getProperties(Name name) {
        Intrinsics.checkNotNullParameter(name, "name");
        return CollectionsKt.plus((Collection) getMemberScope$kotlin_reflection().getContributedVariables(name, NoLookupLocation.FROM_REFLECTION), (Iterable) getStaticScope$kotlin_reflection().getContributedVariables(name, NoLookupLocation.FROM_REFLECTION));
    }

    @Override // kotlin.reflect.jvm.internal.KDeclarationContainerImpl
    public Collection<FunctionDescriptor> getFunctions(Name name) {
        Intrinsics.checkNotNullParameter(name, "name");
        return CollectionsKt.plus((Collection) getMemberScope$kotlin_reflection().getContributedFunctions(name, NoLookupLocation.FROM_REFLECTION), (Iterable) getStaticScope$kotlin_reflection().getContributedFunctions(name, NoLookupLocation.FROM_REFLECTION));
    }

    @Override // kotlin.reflect.jvm.internal.KDeclarationContainerImpl
    public PropertyDescriptor getLocalProperty(int index) {
        Class<?> declaringClass;
        if (Intrinsics.areEqual(getJClass().getSimpleName(), "DefaultImpls") && (declaringClass = getJClass().getDeclaringClass()) != null && declaringClass.isInterface()) {
            KClass kotlinClass = JvmClassMappingKt.getKotlinClass(declaringClass);
            Intrinsics.checkNotNull(kotlinClass, "null cannot be cast to non-null type kotlin.reflect.jvm.internal.KClassImpl<*>");
            return ((KClassImpl) kotlinClass).getLocalProperty(index);
        }
        ClassDescriptor descriptor = getDescriptor();
        DeserializedClassDescriptor deserializedClassDescriptor = descriptor instanceof DeserializedClassDescriptor ? (DeserializedClassDescriptor) descriptor : null;
        if (deserializedClassDescriptor != null) {
            ProtoBuf.Class classProto = deserializedClassDescriptor.getClassProto();
            GeneratedMessageLite.GeneratedExtension<ProtoBuf.Class, List<ProtoBuf.Property>> classLocalVariable = JvmProtoBuf.classLocalVariable;
            Intrinsics.checkNotNullExpressionValue(classLocalVariable, "classLocalVariable");
            ProtoBuf.Property property = (ProtoBuf.Property) ProtoBufUtilKt.getExtensionOrNull(classProto, classLocalVariable, index);
            if (property != null) {
                return (PropertyDescriptor) UtilKt.deserializeToDescriptor(getJClass(), property, deserializedClassDescriptor.getC().getNameResolver(), deserializedClassDescriptor.getC().getTypeTable(), deserializedClassDescriptor.getMetadataVersion(), new Function2() { // from class: kotlin.reflect.jvm.internal.KClassImpl$$Lambda$1
                    @Override // kotlin.jvm.functions.Function2
                    public Object invoke(Object obj, Object obj2) {
                        return KClassImpl.getLocalProperty$lambda$4$lambda$3$lambda$2((MemberDeserializer) obj, (ProtoBuf.Property) obj2);
                    }
                });
            }
        }
        return null;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final PropertyDescriptor getLocalProperty$lambda$4$lambda$3$lambda$2(MemberDeserializer deserializeToDescriptor, ProtoBuf.Property proto) {
        Intrinsics.checkNotNullParameter(deserializeToDescriptor, "$this$deserializeToDescriptor");
        Intrinsics.checkNotNullParameter(proto, "proto");
        return deserializeToDescriptor.loadProperty(proto, true);
    }

    @Override // kotlin.reflect.KClass
    public String getSimpleName() {
        return this.data.getValue().getSimpleName();
    }

    @Override // kotlin.reflect.KClass
    public String getQualifiedName() {
        return this.data.getValue().getQualifiedName();
    }

    @Override // kotlin.reflect.KClass
    public Collection<KFunction<T>> getConstructors() {
        return this.data.getValue().getConstructors();
    }

    @Override // kotlin.reflect.KClass
    public Collection<KClass<?>> getNestedClasses() {
        return this.data.getValue().getNestedClasses();
    }

    @Override // kotlin.reflect.KClass
    public T getObjectInstance() {
        return this.data.getValue().getObjectInstance();
    }

    @Override // kotlin.reflect.KClass
    public boolean isInstance(Object value) {
        Integer functionClassArity = ReflectClassUtilKt.getFunctionClassArity(getJClass());
        if (functionClassArity != null) {
            return TypeIntrinsics.isFunctionOfArity(value, functionClassArity.intValue());
        }
        Class wrapperByPrimitive = ReflectClassUtilKt.getWrapperByPrimitive(getJClass());
        if (wrapperByPrimitive == null) {
            wrapperByPrimitive = getJClass();
        }
        return wrapperByPrimitive.isInstance(value);
    }

    @Override // kotlin.reflect.KClass
    public List<KTypeParameter> getTypeParameters() {
        return this.data.getValue().getTypeParameters();
    }

    @Override // kotlin.reflect.KClass
    public List<KType> getSupertypes() {
        return this.data.getValue().getSupertypes();
    }

    @Override // kotlin.reflect.KClass
    public List<KClass<? extends T>> getSealedSubclasses() {
        return this.data.getValue().getSealedSubclasses();
    }

    @Override // kotlin.reflect.KClass
    public KVisibility getVisibility() {
        DescriptorVisibility visibility = getDescriptor().getVisibility();
        Intrinsics.checkNotNullExpressionValue(visibility, "getVisibility(...)");
        return UtilKt.toKVisibility(visibility);
    }

    private final Modality getModality() {
        Modality modality;
        KmClass kmClass = getKmClass();
        if (kmClass != null && (modality = Attributes.getModality(kmClass)) != null) {
            return modality;
        }
        if (getJClass().isAnnotation() || getJClass().isEnum()) {
            return Modality.FINAL;
        }
        if (Intrinsics.areEqual((Object) Java16SealedRecordLoader.INSTANCE.loadIsSealed(getJClass()), (Object) true)) {
            return Modality.SEALED;
        }
        if (Modifier.isAbstract(getJClass().getModifiers())) {
            return Modality.ABSTRACT;
        }
        return !Modifier.isFinal(getJClass().getModifiers()) ? Modality.OPEN : Modality.FINAL;
    }

    @Override // kotlin.reflect.KClass
    public boolean isFinal() {
        return getModality() == Modality.FINAL;
    }

    @Override // kotlin.reflect.KClass
    public boolean isOpen() {
        return getModality() == Modality.OPEN;
    }

    @Override // kotlin.reflect.KClass
    public boolean isAbstract() {
        return getModality() == Modality.ABSTRACT;
    }

    @Override // kotlin.reflect.KClass
    public boolean isSealed() {
        return getModality() == Modality.SEALED;
    }

    @Override // kotlin.reflect.KClass
    public boolean isData() {
        KmClass kmClass = getKmClass();
        return kmClass != null && Attributes.isData(kmClass);
    }

    @Override // kotlin.reflect.KClass
    public boolean isInner() {
        KmClass kmClass = getKmClass();
        if (kmClass == null) {
            return (getJClass().getDeclaringClass() == null || Modifier.isStatic(getJClass().getModifiers())) ? false : true;
        }
        return Attributes.isInner(kmClass);
    }

    @Override // kotlin.reflect.KClass
    public boolean isCompanion() {
        KmClass kmClass = getKmClass();
        return (kmClass != null ? Attributes.getKind(kmClass) : null) == ClassKind.COMPANION_OBJECT;
    }

    @Override // kotlin.reflect.KClass
    public boolean isFun() {
        KmClass kmClass = getKmClass();
        return kmClass != null && Attributes.isFunInterface(kmClass);
    }

    @Override // kotlin.reflect.KClass
    public boolean isValue() {
        KmClass kmClass = getKmClass();
        return kmClass != null && Attributes.isValue(kmClass);
    }

    @Override // kotlin.reflect.KClass
    public boolean equals(Object other) {
        return (other instanceof KClassImpl) && Intrinsics.areEqual(JvmClassMappingKt.getJavaObjectType(this), JvmClassMappingKt.getJavaObjectType((KClass) other));
    }

    @Override // kotlin.reflect.KClass
    public int hashCode() {
        return JvmClassMappingKt.getJavaObjectType(this).hashCode();
    }

    public String toString() {
        StringBuilder sb = new StringBuilder("class ");
        ClassId classId = getClassId();
        FqName packageFqName = classId.getPackageFqName();
        return sb.append((packageFqName.isRoot() ? "" : packageFqName.asString() + '.') + StringsKt.replace$default(classId.getRelativeClassName().asString(), '.', '$', false, 4, (Object) null)).toString();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final ClassDescriptor createSyntheticClassOrFail(ClassId classId, RuntimeModuleData moduleData) throws InvocationTargetException {
        KotlinClassHeader classHeader;
        if (getJClass().isSynthetic()) {
            return createSyntheticClass(classId, moduleData);
        }
        ReflectKotlinClass reflectKotlinClassCreate = ReflectKotlinClass.Factory.create(getJClass());
        KotlinClassHeader.Kind kind = (reflectKotlinClassCreate == null || (classHeader = reflectKotlinClassCreate.getClassHeader()) == null) ? null : classHeader.getKind();
        switch (kind == null ? -1 : WhenMappings.$EnumSwitchMapping$0[kind.ordinal()]) {
            case -1:
            case 6:
                throw new KotlinReflectionInternalError("Unresolved class: " + getJClass() + " (kind = " + kind + CoreConstants.RIGHT_PARENTHESIS_CHAR);
            case 0:
            default:
                throw new NoWhenBranchMatchedException();
            case 1:
            case 2:
            case 3:
            case 4:
                return createSyntheticClass(classId, moduleData);
            case 5:
                throw new KotlinReflectionInternalError("Unknown class: " + getJClass() + " (kind = " + kind + CoreConstants.RIGHT_PARENTHESIS_CHAR);
        }
    }

    private final ClassDescriptor createSyntheticClass(ClassId classId, RuntimeModuleData moduleData) {
        final ClassDescriptorImpl classDescriptorImpl = new ClassDescriptorImpl(new EmptyPackageFragmentDescriptor(moduleData.getModule(), classId.getPackageFqName()), classId.getShortClassName(), kotlin.reflect.jvm.internal.impl.descriptors.Modality.FINAL, kotlin.reflect.jvm.internal.impl.descriptors.ClassKind.CLASS, CollectionsKt.listOf(moduleData.getModule().getBuiltIns().getAny().getDefaultType()), SourceElement.NO_SOURCE, false, moduleData.getDeserialization().getStorageManager());
        final StorageManager storageManager = moduleData.getDeserialization().getStorageManager();
        classDescriptorImpl.initialize(new GivenFunctionsMemberScope(classDescriptorImpl, storageManager) { // from class: kotlin.reflect.jvm.internal.KClassImpl$createSyntheticClass$1$1
            {
                super(storageManager, classDescriptorImpl);
            }

            @Override // kotlin.reflect.jvm.internal.impl.resolve.scopes.GivenFunctionsMemberScope
            protected List<FunctionDescriptor> computeDeclaredFunctions() {
                return CollectionsKt.emptyList();
            }
        }, SetsKt.emptySet(), null);
        return classDescriptorImpl;
    }

    static {
        Set<ClassId> special_annotations = SpecialJvmAnnotations.INSTANCE.getSPECIAL_ANNOTATIONS();
        HashSet hashSet = new HashSet();
        Iterator<T> it = special_annotations.iterator();
        while (it.hasNext()) {
            hashSet.add(((ClassId) it.next()).asSingleFqName().toString());
        }
        SPECIAL_JVM_ANNOTATION_NAMES = hashSet;
    }
}
