package kotlin.reflect.jvm.internal.impl.km.jvm.internal;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Reflection;
import kotlin.reflect.jvm.internal.impl.km.KmAnnotation;
import kotlin.reflect.jvm.internal.impl.km.KmClass;
import kotlin.reflect.jvm.internal.impl.km.KmConstructor;
import kotlin.reflect.jvm.internal.impl.km.KmEnumEntry;
import kotlin.reflect.jvm.internal.impl.km.KmFunction;
import kotlin.reflect.jvm.internal.impl.km.KmPackage;
import kotlin.reflect.jvm.internal.impl.km.KmProperty;
import kotlin.reflect.jvm.internal.impl.km.KmPropertyAccessorAttributes;
import kotlin.reflect.jvm.internal.impl.km.KmType;
import kotlin.reflect.jvm.internal.impl.km.KmTypeAlias;
import kotlin.reflect.jvm.internal.impl.km.KmTypeParameter;
import kotlin.reflect.jvm.internal.impl.km.KmValueParameter;
import kotlin.reflect.jvm.internal.impl.km.internal.ReadContext;
import kotlin.reflect.jvm.internal.impl.km.internal.ReadUtilsKt;
import kotlin.reflect.jvm.internal.impl.km.internal.ReadersKt;
import kotlin.reflect.jvm.internal.impl.km.internal.WriteContext;
import kotlin.reflect.jvm.internal.impl.km.internal.WriteUtilsKt;
import kotlin.reflect.jvm.internal.impl.km.internal.WritersKt;
import kotlin.reflect.jvm.internal.impl.km.internal.common.KmModuleFragment;
import kotlin.reflect.jvm.internal.impl.km.internal.extensions.KmClassExtension;
import kotlin.reflect.jvm.internal.impl.km.internal.extensions.KmConstructorExtension;
import kotlin.reflect.jvm.internal.impl.km.internal.extensions.KmEnumEntryExtension;
import kotlin.reflect.jvm.internal.impl.km.internal.extensions.KmExtensionType;
import kotlin.reflect.jvm.internal.impl.km.internal.extensions.KmFunctionExtension;
import kotlin.reflect.jvm.internal.impl.km.internal.extensions.KmModuleFragmentExtension;
import kotlin.reflect.jvm.internal.impl.km.internal.extensions.KmPackageExtension;
import kotlin.reflect.jvm.internal.impl.km.internal.extensions.KmPropertyExtension;
import kotlin.reflect.jvm.internal.impl.km.internal.extensions.KmTypeAliasExtension;
import kotlin.reflect.jvm.internal.impl.km.internal.extensions.KmTypeExtension;
import kotlin.reflect.jvm.internal.impl.km.internal.extensions.KmTypeParameterExtension;
import kotlin.reflect.jvm.internal.impl.km.internal.extensions.KmValueParameterExtension;
import kotlin.reflect.jvm.internal.impl.km.internal.extensions.MetadataExtensions;
import kotlin.reflect.jvm.internal.impl.km.jvm.JvmFieldSignature;
import kotlin.reflect.jvm.internal.impl.km.jvm.JvmMemberSignatureKt;
import kotlin.reflect.jvm.internal.impl.km.jvm.JvmMethodSignature;
import kotlin.reflect.jvm.internal.impl.metadata.ProtoBuf;
import kotlin.reflect.jvm.internal.impl.metadata.deserialization.ProtoBufUtilKt;
import kotlin.reflect.jvm.internal.impl.metadata.jvm.JvmProtoBuf;
import kotlin.reflect.jvm.internal.impl.metadata.jvm.deserialization.JvmMemberSignature;
import kotlin.reflect.jvm.internal.impl.metadata.jvm.deserialization.JvmProtoBufUtil;
import kotlin.reflect.jvm.internal.impl.protobuf.GeneratedMessageLite;

/* JADX INFO: compiled from: JvmMetadataExtensions.kt */
/* JADX INFO: loaded from: classes5.dex */
public final class JvmMetadataExtensions implements MetadataExtensions {
    @Override // kotlin.reflect.jvm.internal.impl.km.internal.extensions.MetadataExtensions
    public KmEnumEntryExtension createEnumEntryExtension() {
        return null;
    }

    @Override // kotlin.reflect.jvm.internal.impl.km.internal.extensions.MetadataExtensions
    public KmTypeAliasExtension createTypeAliasExtension() {
        return null;
    }

    @Override // kotlin.reflect.jvm.internal.impl.km.internal.extensions.MetadataExtensions
    public KmValueParameterExtension createValueParameterExtension() {
        return null;
    }

    public void readModuleFragmentExtensions(KmModuleFragment kmModuleFragment, ProtoBuf.PackageFragment proto, ReadContext c) {
        Intrinsics.checkNotNullParameter(kmModuleFragment, "kmModuleFragment");
        Intrinsics.checkNotNullParameter(proto, "proto");
        Intrinsics.checkNotNullParameter(c, "c");
    }

    @Override // kotlin.reflect.jvm.internal.impl.km.internal.extensions.MetadataExtensions
    public void readTypeAliasExtensions(KmTypeAlias kmTypeAlias, ProtoBuf.TypeAlias proto, ReadContext c) {
        Intrinsics.checkNotNullParameter(kmTypeAlias, "kmTypeAlias");
        Intrinsics.checkNotNullParameter(proto, "proto");
        Intrinsics.checkNotNullParameter(c, "c");
    }

    public void writeModuleFragmentExtensions(KmModuleFragment kmModuleFragment, ProtoBuf.PackageFragment.Builder proto, WriteContext c) {
        Intrinsics.checkNotNullParameter(kmModuleFragment, "kmModuleFragment");
        Intrinsics.checkNotNullParameter(proto, "proto");
        Intrinsics.checkNotNullParameter(c, "c");
    }

    public void writeTypeAliasExtensions(KmTypeAlias typeAlias, ProtoBuf.TypeAlias.Builder proto, WriteContext c) {
        Intrinsics.checkNotNullParameter(typeAlias, "typeAlias");
        Intrinsics.checkNotNullParameter(proto, "proto");
        Intrinsics.checkNotNullParameter(c, "c");
    }

    @Override // kotlin.reflect.jvm.internal.impl.km.internal.extensions.MetadataExtensions
    public void readClassExtensions(KmClass kmClass, ProtoBuf.Class proto, ReadContext c) {
        String str;
        Intrinsics.checkNotNullParameter(kmClass, "kmClass");
        Intrinsics.checkNotNullParameter(proto, "proto");
        Intrinsics.checkNotNullParameter(c, "c");
        JvmClassExtension jvm = JvmExtensionNodesKt.getJvm(kmClass);
        List<ProtoBuf.Annotation> annotationList = proto.getAnnotationList();
        Intrinsics.checkNotNullExpressionValue(annotationList, "getAnnotationList(...)");
        List<KmAnnotation> annotations = kmClass.getAnnotations();
        for (ProtoBuf.Annotation annotation : annotationList) {
            Intrinsics.checkNotNull(annotation);
            annotations.add(ReadUtilsKt.readAnnotation(annotation, c.getStrings()));
        }
        ProtoBuf.Class r4 = proto;
        GeneratedMessageLite.GeneratedExtension<ProtoBuf.Class, Integer> anonymousObjectOriginName = JvmProtoBuf.anonymousObjectOriginName;
        Intrinsics.checkNotNullExpressionValue(anonymousObjectOriginName, "anonymousObjectOriginName");
        Integer num = (Integer) ProtoBufUtilKt.getExtensionOrNull(r4, anonymousObjectOriginName);
        if (num != null) {
            jvm.setAnonymousObjectOriginName(c.get(num.intValue()));
        }
        for (ProtoBuf.Property property : (List) proto.getExtension(JvmProtoBuf.classLocalVariable)) {
            List<KmProperty> localDelegatedProperties = jvm.getLocalDelegatedProperties();
            Intrinsics.checkNotNull(property);
            localDelegatedProperties.add(ReadersKt.toKmProperty(property, c));
        }
        GeneratedMessageLite.GeneratedExtension<ProtoBuf.Class, Integer> classModuleName = JvmProtoBuf.classModuleName;
        Intrinsics.checkNotNullExpressionValue(classModuleName, "classModuleName");
        Integer num2 = (Integer) ProtoBufUtilKt.getExtensionOrNull(r4, classModuleName);
        if (num2 == null || (str = c.get(num2.intValue())) == null) {
            str = "main";
        }
        jvm.setModuleName(str);
        GeneratedMessageLite.GeneratedExtension<ProtoBuf.Class, Integer> jvmClassFlags = JvmProtoBuf.jvmClassFlags;
        Intrinsics.checkNotNullExpressionValue(jvmClassFlags, "jvmClassFlags");
        Integer num3 = (Integer) ProtoBufUtilKt.getExtensionOrNull(r4, jvmClassFlags);
        if (num3 != null) {
            jvm.setJvmFlags(num3.intValue());
        }
    }

    public void readPackageExtensions(KmPackage kmPackage, ProtoBuf.Package proto, ReadContext c) {
        String str;
        Intrinsics.checkNotNullParameter(kmPackage, "kmPackage");
        Intrinsics.checkNotNullParameter(proto, "proto");
        Intrinsics.checkNotNullParameter(c, "c");
        JvmPackageExtension jvm = JvmExtensionNodesKt.getJvm(kmPackage);
        for (ProtoBuf.Property property : (List) proto.getExtension(JvmProtoBuf.packageLocalVariable)) {
            List<KmProperty> localDelegatedProperties = jvm.getLocalDelegatedProperties();
            Intrinsics.checkNotNull(property);
            localDelegatedProperties.add(ReadersKt.toKmProperty(property, c));
        }
        GeneratedMessageLite.GeneratedExtension<ProtoBuf.Package, Integer> packageModuleName = JvmProtoBuf.packageModuleName;
        Intrinsics.checkNotNullExpressionValue(packageModuleName, "packageModuleName");
        Integer num = (Integer) ProtoBufUtilKt.getExtensionOrNull(proto, packageModuleName);
        if (num == null || (str = c.get(num.intValue())) == null) {
            str = "main";
        }
        jvm.setModuleName(str);
    }

    @Override // kotlin.reflect.jvm.internal.impl.km.internal.extensions.MetadataExtensions
    public void readFunctionExtensions(KmFunction kmFunction, ProtoBuf.Function proto, ReadContext c) {
        Intrinsics.checkNotNullParameter(kmFunction, "kmFunction");
        Intrinsics.checkNotNullParameter(proto, "proto");
        Intrinsics.checkNotNullParameter(c, "c");
        JvmFunctionExtension jvm = JvmExtensionNodesKt.getJvm(kmFunction);
        List<ProtoBuf.Annotation> annotationList = proto.getAnnotationList();
        Intrinsics.checkNotNullExpressionValue(annotationList, "getAnnotationList(...)");
        List<KmAnnotation> annotations = kmFunction.getAnnotations();
        for (ProtoBuf.Annotation annotation : annotationList) {
            Intrinsics.checkNotNull(annotation);
            annotations.add(ReadUtilsKt.readAnnotation(annotation, c.getStrings()));
        }
        List<ProtoBuf.Annotation> extensionReceiverAnnotationList = proto.getExtensionReceiverAnnotationList();
        Intrinsics.checkNotNullExpressionValue(extensionReceiverAnnotationList, "getExtensionReceiverAnnotationList(...)");
        List<KmAnnotation> extensionReceiverParameterAnnotations = kmFunction.getExtensionReceiverParameterAnnotations();
        for (ProtoBuf.Annotation annotation2 : extensionReceiverAnnotationList) {
            Intrinsics.checkNotNull(annotation2);
            extensionReceiverParameterAnnotations.add(ReadUtilsKt.readAnnotation(annotation2, c.getStrings()));
        }
        JvmMemberSignature.Method jvmMethodSignature = JvmProtoBufUtil.INSTANCE.getJvmMethodSignature(proto, c.getStrings(), c.getTypes());
        jvm.setSignature(jvmMethodSignature != null ? JvmMemberSignatureKt.wrapAsPublic(jvmMethodSignature) : null);
        GeneratedMessageLite.GeneratedExtension<ProtoBuf.Function, Integer> lambdaClassOriginName = JvmProtoBuf.lambdaClassOriginName;
        Intrinsics.checkNotNullExpressionValue(lambdaClassOriginName, "lambdaClassOriginName");
        Integer num = (Integer) ProtoBufUtilKt.getExtensionOrNull(proto, lambdaClassOriginName);
        if (num != null) {
            jvm.setLambdaClassOriginName(c.get(num.intValue()));
        }
    }

    @Override // kotlin.reflect.jvm.internal.impl.km.internal.extensions.MetadataExtensions
    public void readPropertyExtensions(KmProperty kmProperty, ProtoBuf.Property proto, ReadContext c) {
        Intrinsics.checkNotNullParameter(kmProperty, "kmProperty");
        Intrinsics.checkNotNullParameter(proto, "proto");
        Intrinsics.checkNotNullParameter(c, "c");
        JvmPropertyExtension jvm = JvmExtensionNodesKt.getJvm(kmProperty);
        List<ProtoBuf.Annotation> annotationList = proto.getAnnotationList();
        Intrinsics.checkNotNullExpressionValue(annotationList, "getAnnotationList(...)");
        List<KmAnnotation> annotations = kmProperty.getAnnotations();
        for (ProtoBuf.Annotation annotation : annotationList) {
            Intrinsics.checkNotNull(annotation);
            annotations.add(ReadUtilsKt.readAnnotation(annotation, c.getStrings()));
        }
        List<ProtoBuf.Annotation> getterAnnotationList = proto.getGetterAnnotationList();
        Intrinsics.checkNotNullExpressionValue(getterAnnotationList, "getGetterAnnotationList(...)");
        List<KmAnnotation> annotations2 = kmProperty.getGetter().getAnnotations();
        for (ProtoBuf.Annotation annotation2 : getterAnnotationList) {
            Intrinsics.checkNotNull(annotation2);
            annotations2.add(ReadUtilsKt.readAnnotation(annotation2, c.getStrings()));
        }
        KmPropertyAccessorAttributes setter = kmProperty.getSetter();
        if (setter != null) {
            List<ProtoBuf.Annotation> setterAnnotationList = proto.getSetterAnnotationList();
            Intrinsics.checkNotNullExpressionValue(setterAnnotationList, "getSetterAnnotationList(...)");
            List<KmAnnotation> annotations3 = setter.getAnnotations();
            for (ProtoBuf.Annotation annotation3 : setterAnnotationList) {
                Intrinsics.checkNotNull(annotation3);
                annotations3.add(ReadUtilsKt.readAnnotation(annotation3, c.getStrings()));
            }
        }
        List<ProtoBuf.Annotation> extensionReceiverAnnotationList = proto.getExtensionReceiverAnnotationList();
        Intrinsics.checkNotNullExpressionValue(extensionReceiverAnnotationList, "getExtensionReceiverAnnotationList(...)");
        List<KmAnnotation> extensionReceiverParameterAnnotations = kmProperty.getExtensionReceiverParameterAnnotations();
        for (ProtoBuf.Annotation annotation4 : extensionReceiverAnnotationList) {
            Intrinsics.checkNotNull(annotation4);
            extensionReceiverParameterAnnotations.add(ReadUtilsKt.readAnnotation(annotation4, c.getStrings()));
        }
        List<ProtoBuf.Annotation> backingFieldAnnotationList = proto.getBackingFieldAnnotationList();
        Intrinsics.checkNotNullExpressionValue(backingFieldAnnotationList, "getBackingFieldAnnotationList(...)");
        List<KmAnnotation> backingFieldAnnotations = kmProperty.getBackingFieldAnnotations();
        for (ProtoBuf.Annotation annotation5 : backingFieldAnnotationList) {
            Intrinsics.checkNotNull(annotation5);
            backingFieldAnnotations.add(ReadUtilsKt.readAnnotation(annotation5, c.getStrings()));
        }
        List<ProtoBuf.Annotation> delegateFieldAnnotationList = proto.getDelegateFieldAnnotationList();
        Intrinsics.checkNotNullExpressionValue(delegateFieldAnnotationList, "getDelegateFieldAnnotationList(...)");
        List<KmAnnotation> delegateFieldAnnotations = kmProperty.getDelegateFieldAnnotations();
        for (ProtoBuf.Annotation annotation6 : delegateFieldAnnotationList) {
            Intrinsics.checkNotNull(annotation6);
            delegateFieldAnnotations.add(ReadUtilsKt.readAnnotation(annotation6, c.getStrings()));
        }
        JvmMemberSignature.Field jvmFieldSignature$default = JvmProtoBufUtil.getJvmFieldSignature$default(JvmProtoBufUtil.INSTANCE, proto, c.getStrings(), c.getTypes(), false, 8, null);
        GeneratedMessageLite.GeneratedExtension<ProtoBuf.Property, JvmProtoBuf.JvmPropertySignature> propertySignature = JvmProtoBuf.propertySignature;
        Intrinsics.checkNotNullExpressionValue(propertySignature, "propertySignature");
        JvmProtoBuf.JvmPropertySignature jvmPropertySignature = (JvmProtoBuf.JvmPropertySignature) ProtoBufUtilKt.getExtensionOrNull(proto, propertySignature);
        JvmProtoBuf.JvmMethodSignature getter = (jvmPropertySignature == null || !jvmPropertySignature.hasGetter()) ? null : jvmPropertySignature.getGetter();
        JvmProtoBuf.JvmMethodSignature setter2 = (jvmPropertySignature == null || !jvmPropertySignature.hasSetter()) ? null : jvmPropertySignature.getSetter();
        Object extension = proto.getExtension(JvmProtoBuf.flags);
        Intrinsics.checkNotNullExpressionValue(extension, "getExtension(...)");
        jvm.setJvmFlags(((Number) extension).intValue());
        jvm.setFieldSignature(jvmFieldSignature$default != null ? JvmMemberSignatureKt.wrapAsPublic(jvmFieldSignature$default) : null);
        jvm.setGetterSignature(getter != null ? new JvmMethodSignature(c.get(getter.getName()), c.get(getter.getDesc())) : null);
        jvm.setSetterSignature(setter2 != null ? new JvmMethodSignature(c.get(setter2.getName()), c.get(setter2.getDesc())) : null);
        JvmProtoBuf.JvmMethodSignature syntheticMethod = (jvmPropertySignature == null || !jvmPropertySignature.hasSyntheticMethod()) ? null : jvmPropertySignature.getSyntheticMethod();
        jvm.setSyntheticMethodForAnnotations(syntheticMethod != null ? new JvmMethodSignature(c.get(syntheticMethod.getName()), c.get(syntheticMethod.getDesc())) : null);
        JvmProtoBuf.JvmMethodSignature delegateMethod = (jvmPropertySignature == null || !jvmPropertySignature.hasDelegateMethod()) ? null : jvmPropertySignature.getDelegateMethod();
        jvm.setSyntheticMethodForDelegate(delegateMethod != null ? new JvmMethodSignature(c.get(delegateMethod.getName()), c.get(delegateMethod.getDesc())) : null);
    }

    @Override // kotlin.reflect.jvm.internal.impl.km.internal.extensions.MetadataExtensions
    public void readConstructorExtensions(KmConstructor kmConstructor, ProtoBuf.Constructor proto, ReadContext c) {
        Intrinsics.checkNotNullParameter(kmConstructor, "kmConstructor");
        Intrinsics.checkNotNullParameter(proto, "proto");
        Intrinsics.checkNotNullParameter(c, "c");
        JvmConstructorExtension jvm = JvmExtensionNodesKt.getJvm(kmConstructor);
        List<ProtoBuf.Annotation> annotationList = proto.getAnnotationList();
        Intrinsics.checkNotNullExpressionValue(annotationList, "getAnnotationList(...)");
        List<KmAnnotation> annotations = kmConstructor.getAnnotations();
        for (ProtoBuf.Annotation annotation : annotationList) {
            Intrinsics.checkNotNull(annotation);
            annotations.add(ReadUtilsKt.readAnnotation(annotation, c.getStrings()));
        }
        JvmMemberSignature.Method jvmConstructorSignature = JvmProtoBufUtil.INSTANCE.getJvmConstructorSignature(proto, c.getStrings(), c.getTypes());
        jvm.setSignature(jvmConstructorSignature != null ? JvmMemberSignatureKt.wrapAsPublic(jvmConstructorSignature) : null);
    }

    @Override // kotlin.reflect.jvm.internal.impl.km.internal.extensions.MetadataExtensions
    public void readTypeParameterExtensions(KmTypeParameter kmTypeParameter, ProtoBuf.TypeParameter proto, ReadContext c) {
        Intrinsics.checkNotNullParameter(kmTypeParameter, "kmTypeParameter");
        Intrinsics.checkNotNullParameter(proto, "proto");
        Intrinsics.checkNotNullParameter(c, "c");
        JvmTypeParameterExtension jvm = JvmExtensionNodesKt.getJvm(kmTypeParameter);
        for (ProtoBuf.Annotation annotation : (List) proto.getExtension(JvmProtoBuf.typeParameterAnnotation)) {
            List<KmAnnotation> annotations = jvm.getAnnotations();
            Intrinsics.checkNotNull(annotation);
            annotations.add(ReadUtilsKt.readAnnotation(annotation, c.getStrings()));
        }
    }

    @Override // kotlin.reflect.jvm.internal.impl.km.internal.extensions.MetadataExtensions
    public void readEnumEntryExtensions(KmEnumEntry kmEnumEntry, ProtoBuf.EnumEntry proto, ReadContext c) {
        Intrinsics.checkNotNullParameter(kmEnumEntry, "kmEnumEntry");
        Intrinsics.checkNotNullParameter(proto, "proto");
        Intrinsics.checkNotNullParameter(c, "c");
        for (ProtoBuf.Annotation annotation : proto.getAnnotationList()) {
            List<KmAnnotation> annotations = kmEnumEntry.getAnnotations();
            Intrinsics.checkNotNull(annotation);
            annotations.add(ReadUtilsKt.readAnnotation(annotation, c.getStrings()));
        }
    }

    @Override // kotlin.reflect.jvm.internal.impl.km.internal.extensions.MetadataExtensions
    public void readTypeExtensions(KmType kmType, ProtoBuf.Type proto, ReadContext c) {
        Intrinsics.checkNotNullParameter(kmType, "kmType");
        Intrinsics.checkNotNullParameter(proto, "proto");
        Intrinsics.checkNotNullParameter(c, "c");
        JvmTypeExtension jvm = JvmExtensionNodesKt.getJvm(kmType);
        Object extension = proto.getExtension(JvmProtoBuf.isRaw);
        Intrinsics.checkNotNullExpressionValue(extension, "getExtension(...)");
        jvm.setRaw(((Boolean) extension).booleanValue());
        for (ProtoBuf.Annotation annotation : (List) proto.getExtension(JvmProtoBuf.typeAnnotation)) {
            List<KmAnnotation> annotations = jvm.getAnnotations();
            Intrinsics.checkNotNull(annotation);
            annotations.add(ReadUtilsKt.readAnnotation(annotation, c.getStrings()));
        }
    }

    @Override // kotlin.reflect.jvm.internal.impl.km.internal.extensions.MetadataExtensions
    public void readValueParameterExtensions(KmValueParameter kmValueParameter, ProtoBuf.ValueParameter proto, ReadContext c) {
        Intrinsics.checkNotNullParameter(kmValueParameter, "kmValueParameter");
        Intrinsics.checkNotNullParameter(proto, "proto");
        Intrinsics.checkNotNullParameter(c, "c");
        List<ProtoBuf.Annotation> annotationList = proto.getAnnotationList();
        Intrinsics.checkNotNullExpressionValue(annotationList, "getAnnotationList(...)");
        List<KmAnnotation> annotations = kmValueParameter.getAnnotations();
        for (ProtoBuf.Annotation annotation : annotationList) {
            Intrinsics.checkNotNull(annotation);
            annotations.add(ReadUtilsKt.readAnnotation(annotation, c.getStrings()));
        }
    }

    public void writeClassExtensions(KmClass kmClass, ProtoBuf.Class.Builder proto, WriteContext c) {
        Intrinsics.checkNotNullParameter(kmClass, "kmClass");
        Intrinsics.checkNotNullParameter(proto, "proto");
        Intrinsics.checkNotNullParameter(c, "c");
        JvmClassExtension jvm = JvmExtensionNodesKt.getJvm(kmClass);
        List<KmAnnotation> annotations = kmClass.getAnnotations();
        ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(annotations, 10));
        Iterator<T> it = annotations.iterator();
        while (it.hasNext()) {
            arrayList.add(WriteUtilsKt.writeAnnotation((KmAnnotation) it.next(), c.getStrings()).build());
        }
        proto.addAllAnnotation(arrayList);
        String anonymousObjectOriginName = jvm.getAnonymousObjectOriginName();
        if (anonymousObjectOriginName != null) {
            proto.setExtension(JvmProtoBuf.anonymousObjectOriginName, Integer.valueOf(c.get(anonymousObjectOriginName)));
        }
        Iterator<T> it2 = jvm.getLocalDelegatedProperties().iterator();
        while (it2.hasNext()) {
            proto.addExtension(JvmProtoBuf.classLocalVariable, WritersKt.writeProperty(c, (KmProperty) it2.next()).build());
        }
        String moduleName = jvm.getModuleName();
        if (moduleName != null && !Intrinsics.areEqual(moduleName, "main")) {
            proto.setExtension(JvmProtoBuf.classModuleName, Integer.valueOf(c.get(moduleName)));
        }
        if (jvm.getJvmFlags() != 0) {
            proto.setExtension(JvmProtoBuf.jvmClassFlags, Integer.valueOf(jvm.getJvmFlags()));
        }
    }

    public void writePackageExtensions(KmPackage kmPackage, ProtoBuf.Package.Builder proto, WriteContext c) {
        Intrinsics.checkNotNullParameter(kmPackage, "kmPackage");
        Intrinsics.checkNotNullParameter(proto, "proto");
        Intrinsics.checkNotNullParameter(c, "c");
        JvmPackageExtension jvm = JvmExtensionNodesKt.getJvm(kmPackage);
        Iterator<T> it = jvm.getLocalDelegatedProperties().iterator();
        while (it.hasNext()) {
            proto.addExtension(JvmProtoBuf.packageLocalVariable, WritersKt.writeProperty(c, (KmProperty) it.next()).build());
        }
        String moduleName = jvm.getModuleName();
        if (moduleName == null || Intrinsics.areEqual(moduleName, "main")) {
            return;
        }
        proto.setExtension(JvmProtoBuf.packageModuleName, Integer.valueOf(c.get(moduleName)));
    }

    public void writeFunctionExtensions(KmFunction kmFunction, ProtoBuf.Function.Builder proto, WriteContext c) {
        Intrinsics.checkNotNullParameter(kmFunction, "kmFunction");
        Intrinsics.checkNotNullParameter(proto, "proto");
        Intrinsics.checkNotNullParameter(c, "c");
        JvmFunctionExtension jvm = JvmExtensionNodesKt.getJvm(kmFunction);
        List<KmAnnotation> annotations = kmFunction.getAnnotations();
        ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(annotations, 10));
        Iterator<T> it = annotations.iterator();
        while (it.hasNext()) {
            arrayList.add(WriteUtilsKt.writeAnnotation((KmAnnotation) it.next(), c.getStrings()).build());
        }
        proto.addAllAnnotation(arrayList);
        List<KmAnnotation> extensionReceiverParameterAnnotations = kmFunction.getExtensionReceiverParameterAnnotations();
        ArrayList arrayList2 = new ArrayList(CollectionsKt.collectionSizeOrDefault(extensionReceiverParameterAnnotations, 10));
        Iterator<T> it2 = extensionReceiverParameterAnnotations.iterator();
        while (it2.hasNext()) {
            arrayList2.add(WriteUtilsKt.writeAnnotation((KmAnnotation) it2.next(), c.getStrings()).build());
        }
        proto.addAllExtensionReceiverAnnotation(arrayList2);
        JvmMethodSignature signature = jvm.getSignature();
        if (signature != null) {
            proto.setExtension(JvmProtoBuf.methodSignature, toJvmMethodSignature(signature, c));
        }
        String lambdaClassOriginName = jvm.getLambdaClassOriginName();
        if (lambdaClassOriginName != null) {
            proto.setExtension(JvmProtoBuf.lambdaClassOriginName, Integer.valueOf(c.get(lambdaClassOriginName)));
        }
    }

    @Override // kotlin.reflect.jvm.internal.impl.km.internal.extensions.MetadataExtensions
    public void writePropertyExtensions(KmProperty kmProperty, ProtoBuf.Property.Builder proto, WriteContext c) {
        boolean z;
        Intrinsics.checkNotNullParameter(kmProperty, "kmProperty");
        Intrinsics.checkNotNullParameter(proto, "proto");
        Intrinsics.checkNotNullParameter(c, "c");
        JvmPropertyExtension jvm = JvmExtensionNodesKt.getJvm(kmProperty);
        List<KmAnnotation> annotations = kmProperty.getAnnotations();
        ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(annotations, 10));
        Iterator<T> it = annotations.iterator();
        while (it.hasNext()) {
            arrayList.add(WriteUtilsKt.writeAnnotation((KmAnnotation) it.next(), c.getStrings()).build());
        }
        proto.addAllAnnotation(arrayList);
        List<KmAnnotation> annotations2 = kmProperty.getGetter().getAnnotations();
        ArrayList arrayList2 = new ArrayList(CollectionsKt.collectionSizeOrDefault(annotations2, 10));
        Iterator<T> it2 = annotations2.iterator();
        while (it2.hasNext()) {
            arrayList2.add(WriteUtilsKt.writeAnnotation((KmAnnotation) it2.next(), c.getStrings()).build());
        }
        proto.addAllGetterAnnotation(arrayList2);
        KmPropertyAccessorAttributes setter = kmProperty.getSetter();
        if (setter != null) {
            List<KmAnnotation> annotations3 = setter.getAnnotations();
            ArrayList arrayList3 = new ArrayList(CollectionsKt.collectionSizeOrDefault(annotations3, 10));
            Iterator<T> it3 = annotations3.iterator();
            while (it3.hasNext()) {
                arrayList3.add(WriteUtilsKt.writeAnnotation((KmAnnotation) it3.next(), c.getStrings()).build());
            }
            proto.addAllSetterAnnotation(arrayList3);
        }
        List<KmAnnotation> extensionReceiverParameterAnnotations = kmProperty.getExtensionReceiverParameterAnnotations();
        ArrayList arrayList4 = new ArrayList(CollectionsKt.collectionSizeOrDefault(extensionReceiverParameterAnnotations, 10));
        Iterator<T> it4 = extensionReceiverParameterAnnotations.iterator();
        while (it4.hasNext()) {
            arrayList4.add(WriteUtilsKt.writeAnnotation((KmAnnotation) it4.next(), c.getStrings()).build());
        }
        proto.addAllExtensionReceiverAnnotation(arrayList4);
        List<KmAnnotation> backingFieldAnnotations = kmProperty.getBackingFieldAnnotations();
        ArrayList arrayList5 = new ArrayList(CollectionsKt.collectionSizeOrDefault(backingFieldAnnotations, 10));
        Iterator<T> it5 = backingFieldAnnotations.iterator();
        while (it5.hasNext()) {
            arrayList5.add(WriteUtilsKt.writeAnnotation((KmAnnotation) it5.next(), c.getStrings()).build());
        }
        proto.addAllBackingFieldAnnotation(arrayList5);
        List<KmAnnotation> delegateFieldAnnotations = kmProperty.getDelegateFieldAnnotations();
        ArrayList arrayList6 = new ArrayList(CollectionsKt.collectionSizeOrDefault(delegateFieldAnnotations, 10));
        Iterator<T> it6 = delegateFieldAnnotations.iterator();
        while (it6.hasNext()) {
            arrayList6.add(WriteUtilsKt.writeAnnotation((KmAnnotation) it6.next(), c.getStrings()).build());
        }
        proto.addAllDelegateFieldAnnotation(arrayList6);
        JvmProtoBuf.JvmPropertySignature.Builder builderNewBuilder = JvmProtoBuf.JvmPropertySignature.newBuilder();
        Intrinsics.checkNotNullExpressionValue(builderNewBuilder, "newBuilder(...)");
        boolean z2 = true;
        if (jvm.getFieldSignature() != null) {
            JvmProtoBuf.JvmFieldSignature.Builder builderNewBuilder2 = JvmProtoBuf.JvmFieldSignature.newBuilder();
            JvmFieldSignature fieldSignature = jvm.getFieldSignature();
            Intrinsics.checkNotNull(fieldSignature);
            builderNewBuilder2.setName(c.get(fieldSignature.getName()));
            JvmFieldSignature fieldSignature2 = jvm.getFieldSignature();
            Intrinsics.checkNotNull(fieldSignature2);
            builderNewBuilder2.setDesc(c.get(fieldSignature2.getDescriptor()));
            builderNewBuilder.setField(builderNewBuilder2.build());
            z = true;
        } else {
            z = false;
        }
        if (jvm.getGetterSignature() != null) {
            JvmMethodSignature getterSignature = jvm.getGetterSignature();
            Intrinsics.checkNotNull(getterSignature);
            builderNewBuilder.setGetter(toJvmMethodSignature(getterSignature, c));
            z = true;
        }
        if (jvm.getSetterSignature() != null) {
            JvmMethodSignature setterSignature = jvm.getSetterSignature();
            Intrinsics.checkNotNull(setterSignature);
            builderNewBuilder.setSetter(toJvmMethodSignature(setterSignature, c));
        } else {
            z2 = z;
        }
        if (z2 && jvm.getSyntheticMethodForAnnotations() != null) {
            JvmMethodSignature syntheticMethodForAnnotations = jvm.getSyntheticMethodForAnnotations();
            Intrinsics.checkNotNull(syntheticMethodForAnnotations);
            builderNewBuilder.setSyntheticMethod(toJvmMethodSignature(syntheticMethodForAnnotations, c));
        }
        if (z2 && jvm.getSyntheticMethodForDelegate() != null) {
            JvmMethodSignature syntheticMethodForDelegate = jvm.getSyntheticMethodForDelegate();
            Intrinsics.checkNotNull(syntheticMethodForDelegate);
            builderNewBuilder.setDelegateMethod(toJvmMethodSignature(syntheticMethodForDelegate, c));
        }
        int jvmFlags = jvm.getJvmFlags();
        Integer num = (Integer) ProtoBuf.Property.getDefaultInstance().getExtension(JvmProtoBuf.flags);
        if (num == null || jvmFlags != num.intValue()) {
            proto.setExtension(JvmProtoBuf.flags, Integer.valueOf(jvm.getJvmFlags()));
        }
        if (z2) {
            proto.setExtension(JvmProtoBuf.propertySignature, builderNewBuilder.build());
        }
    }

    public void writeConstructorExtensions(KmConstructor kmConstructor, ProtoBuf.Constructor.Builder proto, WriteContext c) {
        Intrinsics.checkNotNullParameter(kmConstructor, "kmConstructor");
        Intrinsics.checkNotNullParameter(proto, "proto");
        Intrinsics.checkNotNullParameter(c, "c");
        JvmConstructorExtension jvm = JvmExtensionNodesKt.getJvm(kmConstructor);
        List<KmAnnotation> annotations = kmConstructor.getAnnotations();
        ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(annotations, 10));
        Iterator<T> it = annotations.iterator();
        while (it.hasNext()) {
            arrayList.add(WriteUtilsKt.writeAnnotation((KmAnnotation) it.next(), c.getStrings()).build());
        }
        proto.addAllAnnotation(arrayList);
        JvmMethodSignature signature = jvm.getSignature();
        if (signature != null) {
            proto.setExtension(JvmProtoBuf.constructorSignature, toJvmMethodSignature(signature, c));
        }
    }

    @Override // kotlin.reflect.jvm.internal.impl.km.internal.extensions.MetadataExtensions
    public void writeTypeParameterExtensions(KmTypeParameter kmTypeParameter, ProtoBuf.TypeParameter.Builder proto, WriteContext c) {
        Intrinsics.checkNotNullParameter(kmTypeParameter, "kmTypeParameter");
        Intrinsics.checkNotNullParameter(proto, "proto");
        Intrinsics.checkNotNullParameter(c, "c");
        Iterator<T> it = JvmExtensionNodesKt.getJvm(kmTypeParameter).getAnnotations().iterator();
        while (it.hasNext()) {
            proto.addExtension(JvmProtoBuf.typeParameterAnnotation, WriteUtilsKt.writeAnnotation((KmAnnotation) it.next(), c.getStrings()).build());
        }
    }

    public void writeEnumEntryExtensions(KmEnumEntry enumEntry, ProtoBuf.EnumEntry.Builder proto, WriteContext c) {
        Intrinsics.checkNotNullParameter(enumEntry, "enumEntry");
        Intrinsics.checkNotNullParameter(proto, "proto");
        Intrinsics.checkNotNullParameter(c, "c");
        Iterator<T> it = enumEntry.getAnnotations().iterator();
        while (it.hasNext()) {
            proto.addAnnotation(WriteUtilsKt.writeAnnotation((KmAnnotation) it.next(), c.getStrings()).build());
        }
    }

    @Override // kotlin.reflect.jvm.internal.impl.km.internal.extensions.MetadataExtensions
    public void writeTypeExtensions(KmType type, ProtoBuf.Type.Builder proto, WriteContext c) {
        Intrinsics.checkNotNullParameter(type, "type");
        Intrinsics.checkNotNullParameter(proto, "proto");
        Intrinsics.checkNotNullParameter(c, "c");
        JvmTypeExtension jvm = JvmExtensionNodesKt.getJvm(type);
        if (jvm.isRaw()) {
            proto.setExtension(JvmProtoBuf.isRaw, true);
        }
        Iterator<T> it = jvm.getAnnotations().iterator();
        while (it.hasNext()) {
            proto.addExtension(JvmProtoBuf.typeAnnotation, WriteUtilsKt.writeAnnotation((KmAnnotation) it.next(), c.getStrings()).build());
        }
    }

    @Override // kotlin.reflect.jvm.internal.impl.km.internal.extensions.MetadataExtensions
    public void writeValueParameterExtensions(KmValueParameter valueParameter, ProtoBuf.ValueParameter.Builder proto, WriteContext c) {
        Intrinsics.checkNotNullParameter(valueParameter, "valueParameter");
        Intrinsics.checkNotNullParameter(proto, "proto");
        Intrinsics.checkNotNullParameter(c, "c");
        List<KmAnnotation> annotations = valueParameter.getAnnotations();
        ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(annotations, 10));
        Iterator<T> it = annotations.iterator();
        while (it.hasNext()) {
            arrayList.add(WriteUtilsKt.writeAnnotation((KmAnnotation) it.next(), c.getStrings()).build());
        }
        proto.addAllAnnotation(arrayList);
    }

    @Override // kotlin.reflect.jvm.internal.impl.km.internal.extensions.MetadataExtensions
    public KmClassExtension createClassExtension() {
        return new JvmClassExtension();
    }

    public KmPackageExtension createPackageExtension() {
        return new JvmPackageExtension();
    }

    public KmModuleFragmentExtension createModuleFragmentExtensions() {
        return new KmModuleFragmentExtension() { // from class: kotlin.reflect.jvm.internal.impl.km.jvm.internal.JvmMetadataExtensions.createModuleFragmentExtensions.1
            private final KmExtensionType type = new KmExtensionType(Reflection.getOrCreateKotlinClass(KmModuleFragmentExtension.class));

            @Override // kotlin.reflect.jvm.internal.impl.km.internal.extensions.KmExtension
            public KmExtensionType getType() {
                return this.type;
            }
        };
    }

    @Override // kotlin.reflect.jvm.internal.impl.km.internal.extensions.MetadataExtensions
    public KmFunctionExtension createFunctionExtension() {
        return new JvmFunctionExtension();
    }

    @Override // kotlin.reflect.jvm.internal.impl.km.internal.extensions.MetadataExtensions
    public KmPropertyExtension createPropertyExtension() {
        return new JvmPropertyExtension();
    }

    @Override // kotlin.reflect.jvm.internal.impl.km.internal.extensions.MetadataExtensions
    public KmConstructorExtension createConstructorExtension() {
        return new JvmConstructorExtension();
    }

    @Override // kotlin.reflect.jvm.internal.impl.km.internal.extensions.MetadataExtensions
    public KmTypeParameterExtension createTypeParameterExtension() {
        return new JvmTypeParameterExtension();
    }

    @Override // kotlin.reflect.jvm.internal.impl.km.internal.extensions.MetadataExtensions
    public KmTypeExtension createTypeExtension() {
        return new JvmTypeExtension();
    }

    private final JvmProtoBuf.JvmMethodSignature toJvmMethodSignature(kotlin.reflect.jvm.internal.impl.km.jvm.JvmMemberSignature jvmMemberSignature, WriteContext writeContext) {
        JvmProtoBuf.JvmMethodSignature.Builder builderNewBuilder = JvmProtoBuf.JvmMethodSignature.newBuilder();
        builderNewBuilder.setName(writeContext.get(jvmMemberSignature.getName()));
        builderNewBuilder.setDesc(writeContext.get(jvmMemberSignature.getDescriptor()));
        JvmProtoBuf.JvmMethodSignature jvmMethodSignatureBuild = builderNewBuilder.build();
        Intrinsics.checkNotNullExpressionValue(jvmMethodSignatureBuild, "build(...)");
        return jvmMethodSignatureBuild;
    }
}
