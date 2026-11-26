package kotlin.reflect.jvm.internal.impl.km.internal.extensions;

import java.util.List;
import java.util.ServiceLoader;
import kotlin.Lazy;
import kotlin.LazyKt;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;
import kotlin.reflect.jvm.internal.impl.km.KmClass;
import kotlin.reflect.jvm.internal.impl.km.KmConstructor;
import kotlin.reflect.jvm.internal.impl.km.KmEnumEntry;
import kotlin.reflect.jvm.internal.impl.km.KmFunction;
import kotlin.reflect.jvm.internal.impl.km.KmProperty;
import kotlin.reflect.jvm.internal.impl.km.KmType;
import kotlin.reflect.jvm.internal.impl.km.KmTypeAlias;
import kotlin.reflect.jvm.internal.impl.km.KmTypeParameter;
import kotlin.reflect.jvm.internal.impl.km.KmValueParameter;
import kotlin.reflect.jvm.internal.impl.km.internal.ReadContext;
import kotlin.reflect.jvm.internal.impl.km.internal.WriteContext;
import kotlin.reflect.jvm.internal.impl.metadata.ProtoBuf;

/* JADX INFO: compiled from: MetadataExtensions.kt */
/* JADX INFO: loaded from: classes5.dex */
public interface MetadataExtensions {
    public static final Companion Companion = Companion.$$INSTANCE;

    KmClassExtension createClassExtension();

    KmConstructorExtension createConstructorExtension();

    KmEnumEntryExtension createEnumEntryExtension();

    KmFunctionExtension createFunctionExtension();

    KmPropertyExtension createPropertyExtension();

    KmTypeAliasExtension createTypeAliasExtension();

    KmTypeExtension createTypeExtension();

    KmTypeParameterExtension createTypeParameterExtension();

    KmValueParameterExtension createValueParameterExtension();

    void readClassExtensions(KmClass kmClass, ProtoBuf.Class r2, ReadContext readContext);

    void readConstructorExtensions(KmConstructor kmConstructor, ProtoBuf.Constructor constructor, ReadContext readContext);

    void readEnumEntryExtensions(KmEnumEntry kmEnumEntry, ProtoBuf.EnumEntry enumEntry, ReadContext readContext);

    void readFunctionExtensions(KmFunction kmFunction, ProtoBuf.Function function, ReadContext readContext);

    void readPropertyExtensions(KmProperty kmProperty, ProtoBuf.Property property, ReadContext readContext);

    void readTypeAliasExtensions(KmTypeAlias kmTypeAlias, ProtoBuf.TypeAlias typeAlias, ReadContext readContext);

    void readTypeExtensions(KmType kmType, ProtoBuf.Type type, ReadContext readContext);

    void readTypeParameterExtensions(KmTypeParameter kmTypeParameter, ProtoBuf.TypeParameter typeParameter, ReadContext readContext);

    void readValueParameterExtensions(KmValueParameter kmValueParameter, ProtoBuf.ValueParameter valueParameter, ReadContext readContext);

    void writePropertyExtensions(KmProperty kmProperty, ProtoBuf.Property.Builder builder, WriteContext writeContext);

    void writeTypeExtensions(KmType kmType, ProtoBuf.Type.Builder builder, WriteContext writeContext);

    void writeTypeParameterExtensions(KmTypeParameter kmTypeParameter, ProtoBuf.TypeParameter.Builder builder, WriteContext writeContext);

    void writeValueParameterExtensions(KmValueParameter kmValueParameter, ProtoBuf.ValueParameter.Builder builder, WriteContext writeContext);

    /* JADX INFO: compiled from: MetadataExtensions.kt */
    public static final class Companion {
        static final /* synthetic */ Companion $$INSTANCE = new Companion();
        private static final Lazy<List<MetadataExtensions>> INSTANCES$delegate = LazyKt.lazy(new Function0() { // from class: kotlin.reflect.jvm.internal.impl.km.internal.extensions.MetadataExtensions$Companion$$Lambda$0
            @Override // kotlin.jvm.functions.Function0
            public Object invoke() {
                return MetadataExtensions.Companion.INSTANCES_delegate$lambda$1();
            }
        });

        private Companion() {
        }

        public final List<MetadataExtensions> getINSTANCES$kotlin_metadata() {
            return INSTANCES$delegate.getValue();
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static final List INSTANCES_delegate$lambda$1() {
            ServiceLoader serviceLoaderLoad = ServiceLoader.load(MetadataExtensions.class, MetadataExtensions.class.getClassLoader());
            Intrinsics.checkNotNullExpressionValue(serviceLoaderLoad, "load(...)");
            List list = CollectionsKt.toList(serviceLoaderLoad);
            if (list.isEmpty()) {
                throw new IllegalStateException("No MetadataExtensions instances found in the classpath. Please ensure that the META-INF/services/ is not stripped from your application and that the Java virtual machine is not running under a security manager".toString());
            }
            return list;
        }
    }
}
