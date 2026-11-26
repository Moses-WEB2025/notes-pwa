package kotlin.reflect.jvm.internal.impl.renderer;

import ch.qos.logback.classic.pattern.CallerDataConverter;
import ch.qos.logback.classic.spi.CallerData;
import ch.qos.logback.core.CoreConstants;
import com.miui.maml.elements.ScreenElementArray;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import kotlin.Lazy;
import kotlin.LazyKt;
import kotlin.NoWhenBranchMatchedException;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.collections.SetsKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.reflect.jvm.internal.impl.builtins.FunctionTypesKt;
import kotlin.reflect.jvm.internal.impl.builtins.KotlinBuiltIns;
import kotlin.reflect.jvm.internal.impl.builtins.StandardNames;
import kotlin.reflect.jvm.internal.impl.descriptors.CallableDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.CallableMemberDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.ClassConstructorDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.ClassDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.ClassKind;
import kotlin.reflect.jvm.internal.impl.descriptors.ClassifierDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.ClassifierDescriptorWithTypeParameters;
import kotlin.reflect.jvm.internal.impl.descriptors.ConstructorDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.DeclarationDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.DeclarationDescriptorVisitor;
import kotlin.reflect.jvm.internal.impl.descriptors.DeclarationDescriptorWithSource;
import kotlin.reflect.jvm.internal.impl.descriptors.DescriptorVisibilities;
import kotlin.reflect.jvm.internal.impl.descriptors.DescriptorVisibility;
import kotlin.reflect.jvm.internal.impl.descriptors.FieldDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.FunctionDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.MemberDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.Modality;
import kotlin.reflect.jvm.internal.impl.descriptors.ModuleDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.NotFoundClasses;
import kotlin.reflect.jvm.internal.impl.descriptors.PackageFragmentDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.PackageViewDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.PossiblyInnerType;
import kotlin.reflect.jvm.internal.impl.descriptors.PropertyAccessorDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.PropertyDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.PropertyGetterDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.PropertySetterDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.ReceiverParameterDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.TypeAliasDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.TypeParameterDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.TypeParameterUtilsKt;
import kotlin.reflect.jvm.internal.impl.descriptors.ValueParameterDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.VariableDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.annotations.Annotated;
import kotlin.reflect.jvm.internal.impl.descriptors.annotations.AnnotationDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.annotations.AnnotationUseSiteTarget;
import kotlin.reflect.jvm.internal.impl.name.FqName;
import kotlin.reflect.jvm.internal.impl.name.FqNameUnsafe;
import kotlin.reflect.jvm.internal.impl.name.Name;
import kotlin.reflect.jvm.internal.impl.name.SpecialNames;
import kotlin.reflect.jvm.internal.impl.resolve.DescriptorUtils;
import kotlin.reflect.jvm.internal.impl.resolve.constants.AnnotationValue;
import kotlin.reflect.jvm.internal.impl.resolve.constants.ArrayValue;
import kotlin.reflect.jvm.internal.impl.resolve.constants.ConstantValue;
import kotlin.reflect.jvm.internal.impl.resolve.constants.KClassValue;
import kotlin.reflect.jvm.internal.impl.resolve.descriptorUtil.DescriptorUtilsKt;
import kotlin.reflect.jvm.internal.impl.types.AbbreviatedType;
import kotlin.reflect.jvm.internal.impl.types.DefinitelyNotNullType;
import kotlin.reflect.jvm.internal.impl.types.FlexibleType;
import kotlin.reflect.jvm.internal.impl.types.IntersectionTypeConstructor;
import kotlin.reflect.jvm.internal.impl.types.KotlinType;
import kotlin.reflect.jvm.internal.impl.types.KotlinTypeKt;
import kotlin.reflect.jvm.internal.impl.types.SimpleType;
import kotlin.reflect.jvm.internal.impl.types.SpecialTypesKt;
import kotlin.reflect.jvm.internal.impl.types.StubTypeForBuilderInference;
import kotlin.reflect.jvm.internal.impl.types.TypeConstructor;
import kotlin.reflect.jvm.internal.impl.types.TypeProjection;
import kotlin.reflect.jvm.internal.impl.types.TypeUtils;
import kotlin.reflect.jvm.internal.impl.types.UnwrappedType;
import kotlin.reflect.jvm.internal.impl.types.Variance;
import kotlin.reflect.jvm.internal.impl.types.WrappedType;
import kotlin.reflect.jvm.internal.impl.types.error.ErrorType;
import kotlin.reflect.jvm.internal.impl.types.error.ErrorTypeConstructor;
import kotlin.reflect.jvm.internal.impl.types.error.ErrorUtils;
import kotlin.reflect.jvm.internal.impl.types.typeUtil.TypeUtilsKt;
import kotlin.reflect.jvm.internal.impl.util.capitalizeDecapitalize.CapitalizeDecapitalizeKt;
import kotlin.text.CharsKt;
import kotlin.text.StringsKt;
import kotlin.text.Typography;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.text.StringSubstitutor;
import org.apache.commons.text.lookup.StringLookupFactory;

/* JADX INFO: compiled from: DescriptorRendererImpl.kt */
/* JADX INFO: loaded from: classes5.dex */
public final class DescriptorRendererImpl extends DescriptorRenderer implements DescriptorRendererOptions {
    private final Lazy functionTypeAnnotationsRenderer$delegate;
    private final DescriptorRendererOptionsImpl options;

    /* JADX INFO: compiled from: DescriptorRendererImpl.kt */
    public static final /* synthetic */ class WhenMappings {
        public static final /* synthetic */ int[] $EnumSwitchMapping$0;
        public static final /* synthetic */ int[] $EnumSwitchMapping$1;

        static {
            int[] iArr = new int[RenderingFormat.values().length];
            try {
                iArr[RenderingFormat.PLAIN.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                iArr[RenderingFormat.HTML.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            $EnumSwitchMapping$0 = iArr;
            int[] iArr2 = new int[ParameterNameRenderingPolicy.values().length];
            try {
                iArr2[ParameterNameRenderingPolicy.ALL.ordinal()] = 1;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                iArr2[ParameterNameRenderingPolicy.ONLY_NON_SYNTHESIZED.ordinal()] = 2;
            } catch (NoSuchFieldError unused4) {
            }
            try {
                iArr2[ParameterNameRenderingPolicy.NONE.ordinal()] = 3;
            } catch (NoSuchFieldError unused5) {
            }
            $EnumSwitchMapping$1 = iArr2;
        }
    }

    public boolean getActualPropertiesInPrimaryConstructor() {
        return this.options.getActualPropertiesInPrimaryConstructor();
    }

    public boolean getAlwaysRenderModifiers() {
        return this.options.getAlwaysRenderModifiers();
    }

    @Override // kotlin.reflect.jvm.internal.impl.renderer.DescriptorRendererOptions
    public AnnotationArgumentsRenderingPolicy getAnnotationArgumentsRenderingPolicy() {
        return this.options.getAnnotationArgumentsRenderingPolicy();
    }

    public Function1<AnnotationDescriptor, Boolean> getAnnotationFilter() {
        return this.options.getAnnotationFilter();
    }

    public boolean getBoldOnlyForNamesInHtml() {
        return this.options.getBoldOnlyForNamesInHtml();
    }

    public boolean getClassWithPrimaryConstructor() {
        return this.options.getClassWithPrimaryConstructor();
    }

    public ClassifierNamePolicy getClassifierNamePolicy() {
        return this.options.getClassifierNamePolicy();
    }

    @Override // kotlin.reflect.jvm.internal.impl.renderer.DescriptorRendererOptions
    public boolean getDebugMode() {
        return this.options.getDebugMode();
    }

    public Function1<ValueParameterDescriptor, String> getDefaultParameterValueRenderer() {
        return this.options.getDefaultParameterValueRenderer();
    }

    public boolean getEachAnnotationOnNewLine() {
        return this.options.getEachAnnotationOnNewLine();
    }

    @Override // kotlin.reflect.jvm.internal.impl.renderer.DescriptorRendererOptions
    public boolean getEnhancedTypes() {
        return this.options.getEnhancedTypes();
    }

    public Set<FqName> getExcludedAnnotationClasses() {
        return this.options.getExcludedAnnotationClasses();
    }

    @Override // kotlin.reflect.jvm.internal.impl.renderer.DescriptorRendererOptions
    public Set<FqName> getExcludedTypeAnnotationClasses() {
        return this.options.getExcludedTypeAnnotationClasses();
    }

    public boolean getIncludeAdditionalModifiers() {
        return this.options.getIncludeAdditionalModifiers();
    }

    public boolean getIncludeAnnotationArguments() {
        return this.options.getIncludeAnnotationArguments();
    }

    public boolean getIncludeEmptyAnnotationArguments() {
        return this.options.getIncludeEmptyAnnotationArguments();
    }

    public boolean getIncludePropertyConstant() {
        return this.options.getIncludePropertyConstant();
    }

    public boolean getInformativeErrorType() {
        return this.options.getInformativeErrorType();
    }

    public Set<DescriptorRendererModifier> getModifiers() {
        return this.options.getModifiers();
    }

    public boolean getNormalizedVisibilities() {
        return this.options.getNormalizedVisibilities();
    }

    public OverrideRenderingPolicy getOverrideRenderingPolicy() {
        return this.options.getOverrideRenderingPolicy();
    }

    public ParameterNameRenderingPolicy getParameterNameRenderingPolicy() {
        return this.options.getParameterNameRenderingPolicy();
    }

    public boolean getParameterNamesInFunctionalTypes() {
        return this.options.getParameterNamesInFunctionalTypes();
    }

    public boolean getPresentableUnresolvedTypes() {
        return this.options.getPresentableUnresolvedTypes();
    }

    public PropertyAccessorRenderingPolicy getPropertyAccessorRenderingPolicy() {
        return this.options.getPropertyAccessorRenderingPolicy();
    }

    public boolean getReceiverAfterName() {
        return this.options.getReceiverAfterName();
    }

    public boolean getRenderAbbreviatedTypeComments() {
        return this.options.getRenderAbbreviatedTypeComments();
    }

    public boolean getRenderCompanionObjectName() {
        return this.options.getRenderCompanionObjectName();
    }

    public boolean getRenderConstructorDelegation() {
        return this.options.getRenderConstructorDelegation();
    }

    public boolean getRenderConstructorKeyword() {
        return this.options.getRenderConstructorKeyword();
    }

    public boolean getRenderDefaultAnnotationArguments() {
        return this.options.getRenderDefaultAnnotationArguments();
    }

    public boolean getRenderDefaultModality() {
        return this.options.getRenderDefaultModality();
    }

    public boolean getRenderDefaultVisibility() {
        return this.options.getRenderDefaultVisibility();
    }

    public boolean getRenderPrimaryConstructorParametersAsProperties() {
        return this.options.getRenderPrimaryConstructorParametersAsProperties();
    }

    public boolean getRenderTypeExpansions() {
        return this.options.getRenderTypeExpansions();
    }

    public boolean getRenderUnabbreviatedType() {
        return this.options.getRenderUnabbreviatedType();
    }

    public boolean getSecondaryConstructorsAsPrimary() {
        return this.options.getSecondaryConstructorsAsPrimary();
    }

    public boolean getStartFromDeclarationKeyword() {
        return this.options.getStartFromDeclarationKeyword();
    }

    public boolean getStartFromName() {
        return this.options.getStartFromName();
    }

    public RenderingFormat getTextFormat() {
        return this.options.getTextFormat();
    }

    public Function1<KotlinType, KotlinType> getTypeNormalizer() {
        return this.options.getTypeNormalizer();
    }

    public boolean getUninferredTypeParameterAsName() {
        return this.options.getUninferredTypeParameterAsName();
    }

    public boolean getUnitReturnType() {
        return this.options.getUnitReturnType();
    }

    public DescriptorRenderer.ValueParametersHandler getValueParametersHandler() {
        return this.options.getValueParametersHandler();
    }

    public boolean getVerbose() {
        return this.options.getVerbose();
    }

    public boolean getWithDefinedIn() {
        return this.options.getWithDefinedIn();
    }

    public boolean getWithSourceFileForTopLevel() {
        return this.options.getWithSourceFileForTopLevel();
    }

    public boolean getWithoutReturnType() {
        return this.options.getWithoutReturnType();
    }

    public boolean getWithoutSuperTypes() {
        return this.options.getWithoutSuperTypes();
    }

    public boolean getWithoutTypeParameters() {
        return this.options.getWithoutTypeParameters();
    }

    @Override // kotlin.reflect.jvm.internal.impl.renderer.DescriptorRendererOptions
    public void setAnnotationArgumentsRenderingPolicy(AnnotationArgumentsRenderingPolicy annotationArgumentsRenderingPolicy) {
        Intrinsics.checkNotNullParameter(annotationArgumentsRenderingPolicy, "<set-?>");
        this.options.setAnnotationArgumentsRenderingPolicy(annotationArgumentsRenderingPolicy);
    }

    @Override // kotlin.reflect.jvm.internal.impl.renderer.DescriptorRendererOptions
    public void setClassifierNamePolicy(ClassifierNamePolicy classifierNamePolicy) {
        Intrinsics.checkNotNullParameter(classifierNamePolicy, "<set-?>");
        this.options.setClassifierNamePolicy(classifierNamePolicy);
    }

    @Override // kotlin.reflect.jvm.internal.impl.renderer.DescriptorRendererOptions
    public void setDebugMode(boolean z) {
        this.options.setDebugMode(z);
    }

    @Override // kotlin.reflect.jvm.internal.impl.renderer.DescriptorRendererOptions
    public void setExcludedTypeAnnotationClasses(Set<FqName> set) {
        Intrinsics.checkNotNullParameter(set, "<set-?>");
        this.options.setExcludedTypeAnnotationClasses(set);
    }

    @Override // kotlin.reflect.jvm.internal.impl.renderer.DescriptorRendererOptions
    public void setModifiers(Set<? extends DescriptorRendererModifier> set) {
        Intrinsics.checkNotNullParameter(set, "<set-?>");
        this.options.setModifiers(set);
    }

    @Override // kotlin.reflect.jvm.internal.impl.renderer.DescriptorRendererOptions
    public void setParameterNameRenderingPolicy(ParameterNameRenderingPolicy parameterNameRenderingPolicy) {
        Intrinsics.checkNotNullParameter(parameterNameRenderingPolicy, "<set-?>");
        this.options.setParameterNameRenderingPolicy(parameterNameRenderingPolicy);
    }

    @Override // kotlin.reflect.jvm.internal.impl.renderer.DescriptorRendererOptions
    public void setReceiverAfterName(boolean z) {
        this.options.setReceiverAfterName(z);
    }

    @Override // kotlin.reflect.jvm.internal.impl.renderer.DescriptorRendererOptions
    public void setRenderCompanionObjectName(boolean z) {
        this.options.setRenderCompanionObjectName(z);
    }

    @Override // kotlin.reflect.jvm.internal.impl.renderer.DescriptorRendererOptions
    public void setStartFromName(boolean z) {
        this.options.setStartFromName(z);
    }

    @Override // kotlin.reflect.jvm.internal.impl.renderer.DescriptorRendererOptions
    public void setTextFormat(RenderingFormat renderingFormat) {
        Intrinsics.checkNotNullParameter(renderingFormat, "<set-?>");
        this.options.setTextFormat(renderingFormat);
    }

    @Override // kotlin.reflect.jvm.internal.impl.renderer.DescriptorRendererOptions
    public void setVerbose(boolean z) {
        this.options.setVerbose(z);
    }

    @Override // kotlin.reflect.jvm.internal.impl.renderer.DescriptorRendererOptions
    public void setWithDefinedIn(boolean z) {
        this.options.setWithDefinedIn(z);
    }

    @Override // kotlin.reflect.jvm.internal.impl.renderer.DescriptorRendererOptions
    public void setWithoutSuperTypes(boolean z) {
        this.options.setWithoutSuperTypes(z);
    }

    @Override // kotlin.reflect.jvm.internal.impl.renderer.DescriptorRendererOptions
    public void setWithoutTypeParameters(boolean z) {
        this.options.setWithoutTypeParameters(z);
    }

    public final DescriptorRendererOptionsImpl getOptions() {
        return this.options;
    }

    public DescriptorRendererImpl(DescriptorRendererOptionsImpl options) {
        Intrinsics.checkNotNullParameter(options, "options");
        this.options = options;
        options.isLocked();
        this.functionTypeAnnotationsRenderer$delegate = LazyKt.lazy(new Function0(this) { // from class: kotlin.reflect.jvm.internal.impl.renderer.DescriptorRendererImpl$$Lambda$0
            private final DescriptorRendererImpl arg$0;

            {
                this.arg$0 = this;
            }

            @Override // kotlin.jvm.functions.Function0
            public Object invoke() {
                return DescriptorRendererImpl.functionTypeAnnotationsRenderer_delegate$lambda$1(this.arg$0);
            }
        });
    }

    private final DescriptorRendererImpl getFunctionTypeAnnotationsRenderer() {
        return (DescriptorRendererImpl) this.functionTypeAnnotationsRenderer$delegate.getValue();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final DescriptorRendererImpl functionTypeAnnotationsRenderer_delegate$lambda$1(DescriptorRendererImpl descriptorRendererImpl) {
        DescriptorRenderer descriptorRendererWithOptions = descriptorRendererImpl.withOptions(new Function1() { // from class: kotlin.reflect.jvm.internal.impl.renderer.DescriptorRendererImpl$$Lambda$5
            @Override // kotlin.jvm.functions.Function1
            public Object invoke(Object obj) {
                return DescriptorRendererImpl.functionTypeAnnotationsRenderer_delegate$lambda$1$lambda$0((DescriptorRendererOptions) obj);
            }
        });
        Intrinsics.checkNotNull(descriptorRendererWithOptions, "null cannot be cast to non-null type org.jetbrains.kotlin.renderer.DescriptorRendererImpl");
        return (DescriptorRendererImpl) descriptorRendererWithOptions;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit functionTypeAnnotationsRenderer_delegate$lambda$1$lambda$0(DescriptorRendererOptions withOptions) {
        Intrinsics.checkNotNullParameter(withOptions, "$this$withOptions");
        withOptions.setExcludedTypeAnnotationClasses(SetsKt.plus((Set) withOptions.getExcludedTypeAnnotationClasses(), (Iterable) CollectionsKt.listOf((Object[]) new FqName[]{StandardNames.FqNames.extensionFunctionType, StandardNames.FqNames.contextFunctionTypeParams})));
        return Unit.INSTANCE;
    }

    private final String renderKeyword(String str) {
        int i = WhenMappings.$EnumSwitchMapping$0[getTextFormat().ordinal()];
        if (i != 1) {
            if (i != 2) {
                throw new NoWhenBranchMatchedException();
            }
            if (!getBoldOnlyForNamesInHtml()) {
                return "<b>" + str + "</b>";
            }
        }
        return str;
    }

    private final String renderError(String str) {
        int i = WhenMappings.$EnumSwitchMapping$0[getTextFormat().ordinal()];
        if (i == 1) {
            return str;
        }
        if (i != 2) {
            throw new NoWhenBranchMatchedException();
        }
        return "<font color=red><b>" + str + "</b></font>";
    }

    private final String escape(String str) {
        return getTextFormat().escape(str);
    }

    private final String lt() {
        return escape("<");
    }

    private final String gt() {
        return escape(">");
    }

    private final String arrow() {
        int i = WhenMappings.$EnumSwitchMapping$0[getTextFormat().ordinal()];
        if (i == 1) {
            return escape("->");
        }
        if (i != 2) {
            throw new NoWhenBranchMatchedException();
        }
        return "&rarr;";
    }

    public String renderMessage(String message) {
        Intrinsics.checkNotNullParameter(message, "message");
        int i = WhenMappings.$EnumSwitchMapping$0[getTextFormat().ordinal()];
        if (i == 1) {
            return message;
        }
        if (i != 2) {
            throw new NoWhenBranchMatchedException();
        }
        return "<i>" + message + "</i>";
    }

    @Override // kotlin.reflect.jvm.internal.impl.renderer.DescriptorRenderer
    public String renderName(Name name, boolean z) {
        Intrinsics.checkNotNullParameter(name, "name");
        String strEscape = escape(RenderingUtilsKt.render(name));
        return (getBoldOnlyForNamesInHtml() && getTextFormat() == RenderingFormat.HTML && z) ? "<b>" + strEscape + "</b>" : strEscape;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void renderName(DeclarationDescriptor declarationDescriptor, StringBuilder sb, boolean z) {
        Name name = declarationDescriptor.getName();
        Intrinsics.checkNotNullExpressionValue(name, "getName(...)");
        sb.append(renderName(name, z));
    }

    private final void renderCompanionObjectName(DeclarationDescriptor declarationDescriptor, StringBuilder sb) {
        if (getRenderCompanionObjectName()) {
            if (getStartFromName()) {
                sb.append("companion object");
            }
            renderSpaceIfNeeded(sb);
            DeclarationDescriptor containingDeclaration = declarationDescriptor.getContainingDeclaration();
            if (containingDeclaration != null) {
                sb.append("of ");
                Name name = containingDeclaration.getName();
                Intrinsics.checkNotNullExpressionValue(name, "getName(...)");
                sb.append(renderName(name, false));
            }
        }
        if (getVerbose() || !Intrinsics.areEqual(declarationDescriptor.getName(), SpecialNames.DEFAULT_NAME_FOR_COMPANION_OBJECT)) {
            if (!getStartFromName()) {
                renderSpaceIfNeeded(sb);
            }
            Name name2 = declarationDescriptor.getName();
            Intrinsics.checkNotNullExpressionValue(name2, "getName(...)");
            sb.append(renderName(name2, true));
        }
    }

    @Override // kotlin.reflect.jvm.internal.impl.renderer.DescriptorRenderer
    public String renderFqName(FqNameUnsafe fqName) {
        Intrinsics.checkNotNullParameter(fqName, "fqName");
        return renderFqName(fqName.pathSegments());
    }

    private final String renderFqName(List<Name> list) {
        return escape(RenderingUtilsKt.renderFqName(list));
    }

    public String renderClassifierName(ClassifierDescriptor klass) {
        Intrinsics.checkNotNullParameter(klass, "klass");
        if (ErrorUtils.isError(klass)) {
            return klass.getTypeConstructor().toString();
        }
        return getClassifierNamePolicy().renderClassifier(klass, this);
    }

    @Override // kotlin.reflect.jvm.internal.impl.renderer.DescriptorRenderer
    public String renderType(KotlinType type) {
        Intrinsics.checkNotNullParameter(type, "type");
        StringBuilder sb = new StringBuilder();
        renderNormalizedType(sb, getTypeNormalizer().invoke(type));
        return sb.toString();
    }

    private final void renderNormalizedType(StringBuilder sb, KotlinType kotlinType) {
        UnwrappedType unwrappedTypeUnwrap = kotlinType.unwrap();
        AbbreviatedType abbreviatedType = unwrappedTypeUnwrap instanceof AbbreviatedType ? (AbbreviatedType) unwrappedTypeUnwrap : null;
        if (abbreviatedType != null) {
            if (getRenderTypeExpansions()) {
                renderNormalizedTypeAsIs(sb, abbreviatedType.getExpandedType());
                if (getRenderAbbreviatedTypeComments()) {
                    renderAbbreviatedTypeComment(sb, abbreviatedType);
                    return;
                }
                return;
            }
            renderNormalizedTypeAsIs(sb, abbreviatedType.getAbbreviation());
            if (getRenderUnabbreviatedType()) {
                renderExpandedTypeComment(sb, abbreviatedType);
                return;
            }
            return;
        }
        renderNormalizedTypeAsIs(sb, kotlinType);
    }

    private final void renderNormalizedTypeAsIs(StringBuilder sb, KotlinType kotlinType) {
        if ((kotlinType instanceof WrappedType) && getDebugMode() && !((WrappedType) kotlinType).isComputed()) {
            sb.append("<Not computed yet>");
            return;
        }
        UnwrappedType unwrappedTypeUnwrap = kotlinType.unwrap();
        if (unwrappedTypeUnwrap instanceof FlexibleType) {
            sb.append(((FlexibleType) unwrappedTypeUnwrap).render(this, this));
        } else {
            if (!(unwrappedTypeUnwrap instanceof SimpleType)) {
                throw new NoWhenBranchMatchedException();
            }
            renderSimpleType(sb, (SimpleType) unwrappedTypeUnwrap);
        }
    }

    private final void renderSimpleType(StringBuilder sb, SimpleType simpleType) {
        if (!Intrinsics.areEqual(simpleType, TypeUtils.CANNOT_INFER_FUNCTION_PARAM_TYPE)) {
            SimpleType simpleType2 = simpleType;
            if (!TypeUtils.isDontCarePlaceholder(simpleType2)) {
                if (ErrorUtils.isUninferredTypeVariable(simpleType2)) {
                    if (getUninferredTypeParameterAsName()) {
                        TypeConstructor constructor = simpleType.getConstructor();
                        Intrinsics.checkNotNull(constructor, "null cannot be cast to non-null type org.jetbrains.kotlin.types.error.ErrorTypeConstructor");
                        sb.append(renderError(((ErrorTypeConstructor) constructor).getParam(0)));
                        return;
                    }
                    sb.append("???");
                    return;
                }
                if (KotlinTypeKt.isError(simpleType2)) {
                    renderDefaultType(sb, simpleType2);
                    return;
                } else if (shouldRenderAsPrettyFunctionType(simpleType2)) {
                    renderFunctionType(sb, simpleType2);
                    return;
                } else {
                    renderDefaultType(sb, simpleType2);
                    return;
                }
            }
        }
        sb.append("???");
    }

    private final boolean shouldRenderAsPrettyFunctionType(KotlinType kotlinType) {
        if (!FunctionTypesKt.isBuiltinFunctionalType(kotlinType)) {
            return false;
        }
        List<TypeProjection> arguments = kotlinType.getArguments();
        if ((arguments instanceof Collection) && arguments.isEmpty()) {
            return true;
        }
        Iterator<T> it = arguments.iterator();
        while (it.hasNext()) {
            if (((TypeProjection) it.next()).isStarProjection()) {
                return false;
            }
        }
        return true;
    }

    @Override // kotlin.reflect.jvm.internal.impl.renderer.DescriptorRenderer
    public String renderFlexibleType(String lowerRendered, String upperRendered, KotlinBuiltIns builtIns) {
        Intrinsics.checkNotNullParameter(lowerRendered, "lowerRendered");
        Intrinsics.checkNotNullParameter(upperRendered, "upperRendered");
        Intrinsics.checkNotNullParameter(builtIns, "builtIns");
        if (RenderingUtilsKt.typeStringsDifferOnlyInNullability(lowerRendered, upperRendered)) {
            if (StringsKt.startsWith$default(upperRendered, "(", false, 2, (Object) null)) {
                return "(" + lowerRendered + ")!";
            }
            return lowerRendered + '!';
        }
        ClassifierNamePolicy classifierNamePolicy = getClassifierNamePolicy();
        ClassDescriptor collection = builtIns.getCollection();
        Intrinsics.checkNotNullExpressionValue(collection, "getCollection(...)");
        DescriptorRendererImpl descriptorRendererImpl = this;
        String strSubstringBefore$default = StringsKt.substringBefore$default(classifierNamePolicy.renderClassifier(collection, descriptorRendererImpl), "Collection", (String) null, 2, (Object) null);
        String strReplacePrefixesInTypeRepresentations = RenderingUtilsKt.replacePrefixesInTypeRepresentations(lowerRendered, strSubstringBefore$default + "Mutable", upperRendered, strSubstringBefore$default, strSubstringBefore$default + "(Mutable)");
        if (strReplacePrefixesInTypeRepresentations != null) {
            return strReplacePrefixesInTypeRepresentations;
        }
        String strReplacePrefixesInTypeRepresentations2 = RenderingUtilsKt.replacePrefixesInTypeRepresentations(lowerRendered, strSubstringBefore$default + "MutableMap.MutableEntry", upperRendered, strSubstringBefore$default + "Map.Entry", strSubstringBefore$default + "(Mutable)Map.(Mutable)Entry");
        if (strReplacePrefixesInTypeRepresentations2 != null) {
            return strReplacePrefixesInTypeRepresentations2;
        }
        ClassifierNamePolicy classifierNamePolicy2 = getClassifierNamePolicy();
        ClassDescriptor array = builtIns.getArray();
        Intrinsics.checkNotNullExpressionValue(array, "getArray(...)");
        String strSubstringBefore$default2 = StringsKt.substringBefore$default(classifierNamePolicy2.renderClassifier(array, descriptorRendererImpl), ScreenElementArray.TAG_NAME, (String) null, 2, (Object) null);
        String strReplacePrefixesInTypeRepresentations3 = RenderingUtilsKt.replacePrefixesInTypeRepresentations(lowerRendered, strSubstringBefore$default2 + escape("Array<"), upperRendered, strSubstringBefore$default2 + escape("Array<out "), strSubstringBefore$default2 + escape("Array<(out) "));
        return strReplacePrefixesInTypeRepresentations3 != null ? strReplacePrefixesInTypeRepresentations3 : "(" + lowerRendered + CallerDataConverter.DEFAULT_RANGE_DELIMITER + upperRendered + CoreConstants.RIGHT_PARENTHESIS_CHAR;
    }

    public String renderTypeArguments(List<? extends TypeProjection> typeArguments) {
        Intrinsics.checkNotNullParameter(typeArguments, "typeArguments");
        if (typeArguments.isEmpty()) {
            return "";
        }
        StringBuilder sb = new StringBuilder();
        sb.append(lt());
        appendTypeProjections(sb, typeArguments);
        sb.append(gt());
        return sb.toString();
    }

    /* JADX WARN: Code duplicated, block: B:32:0x009e  */
    /* JADX WARN: Code duplicated, block: B:35:0x00a9  */
    /* JADX WARN: Code duplicated, block: B:37:? A[RETURN, SYNTHETIC] */
    private final void renderDefaultType(StringBuilder sb, KotlinType kotlinType) {
        KotlinType kotlinType2;
        renderAnnotations$default(this, sb, kotlinType, null, 2, null);
        DefinitelyNotNullType definitelyNotNullType = kotlinType instanceof DefinitelyNotNullType ? (DefinitelyNotNullType) kotlinType : null;
        SimpleType original = definitelyNotNullType != null ? definitelyNotNullType.getOriginal() : null;
        if (KotlinTypeKt.isError(kotlinType)) {
            if (TypeUtilsKt.isUnresolvedType(kotlinType) && getPresentableUnresolvedTypes()) {
                sb.append(renderError(ErrorUtils.INSTANCE.unresolvedTypeAsItIs(kotlinType)));
            } else {
                if ((kotlinType instanceof ErrorType) && !getInformativeErrorType()) {
                    sb.append(((ErrorType) kotlinType).getDebugMessage());
                } else {
                    sb.append(kotlinType.getConstructor().toString());
                }
                sb.append(renderTypeArguments(kotlinType.getArguments()));
            }
        } else if (kotlinType instanceof StubTypeForBuilderInference) {
            sb.append(((StubTypeForBuilderInference) kotlinType).getOriginalTypeVariable().toString());
        } else {
            if (original instanceof StubTypeForBuilderInference) {
                sb.append(((StubTypeForBuilderInference) original).getOriginalTypeVariable().toString());
            } else {
                kotlinType2 = kotlinType;
                renderTypeConstructorAndArguments$default(this, sb, kotlinType2, null, 2, null);
                Unit unit = Unit.INSTANCE;
            }
            if (kotlinType2.isMarkedNullable()) {
                sb.append(CallerData.NA);
            }
            if (SpecialTypesKt.isDefinitelyNotNullType(kotlinType2)) {
                sb.append(" & Any");
            }
        }
        kotlinType2 = kotlinType;
        if (kotlinType2.isMarkedNullable()) {
            sb.append(CallerData.NA);
        }
        if (SpecialTypesKt.isDefinitelyNotNullType(kotlinType2)) {
            sb.append(" & Any");
        }
    }

    static /* synthetic */ void renderTypeConstructorAndArguments$default(DescriptorRendererImpl descriptorRendererImpl, StringBuilder sb, KotlinType kotlinType, TypeConstructor typeConstructor, int i, Object obj) {
        if ((i & 2) != 0) {
            typeConstructor = kotlinType.getConstructor();
        }
        descriptorRendererImpl.renderTypeConstructorAndArguments(sb, kotlinType, typeConstructor);
    }

    private final void renderTypeConstructorAndArguments(StringBuilder sb, KotlinType kotlinType, TypeConstructor typeConstructor) {
        PossiblyInnerType possiblyInnerTypeBuildPossiblyInnerType = TypeParameterUtilsKt.buildPossiblyInnerType(kotlinType);
        if (possiblyInnerTypeBuildPossiblyInnerType == null) {
            sb.append(renderTypeConstructor(typeConstructor));
            sb.append(renderTypeArguments(kotlinType.getArguments()));
        } else {
            renderPossiblyInnerType(sb, possiblyInnerTypeBuildPossiblyInnerType);
        }
    }

    /* JADX WARN: Code duplicated, block: B:6:0x0026  */
    private final void renderPossiblyInnerType(StringBuilder sb, PossiblyInnerType possiblyInnerType) {
        PossiblyInnerType outerType = possiblyInnerType.getOuterType();
        if (outerType != null) {
            renderPossiblyInnerType(sb, outerType);
            sb.append('.');
            Name name = possiblyInnerType.getClassifierDescriptor().getName();
            Intrinsics.checkNotNullExpressionValue(name, "getName(...)");
            if (sb.append(renderName(name, false)) == null) {
                TypeConstructor typeConstructor = possiblyInnerType.getClassifierDescriptor().getTypeConstructor();
                Intrinsics.checkNotNullExpressionValue(typeConstructor, "getTypeConstructor(...)");
                sb.append(renderTypeConstructor(typeConstructor));
            }
        } else {
            TypeConstructor typeConstructor2 = possiblyInnerType.getClassifierDescriptor().getTypeConstructor();
            Intrinsics.checkNotNullExpressionValue(typeConstructor2, "getTypeConstructor(...)");
            sb.append(renderTypeConstructor(typeConstructor2));
        }
        sb.append(renderTypeArguments(possiblyInnerType.getArguments()));
    }

    public String renderTypeConstructor(TypeConstructor typeConstructor) {
        Intrinsics.checkNotNullParameter(typeConstructor, "typeConstructor");
        ClassifierDescriptor classifierDescriptorMo3823getDeclarationDescriptor = typeConstructor.mo3823getDeclarationDescriptor();
        if ((classifierDescriptorMo3823getDeclarationDescriptor instanceof TypeParameterDescriptor) || (classifierDescriptorMo3823getDeclarationDescriptor instanceof ClassDescriptor) || (classifierDescriptorMo3823getDeclarationDescriptor instanceof TypeAliasDescriptor)) {
            return renderClassifierName(classifierDescriptorMo3823getDeclarationDescriptor);
        }
        if (classifierDescriptorMo3823getDeclarationDescriptor == null) {
            if (typeConstructor instanceof IntersectionTypeConstructor) {
                return ((IntersectionTypeConstructor) typeConstructor).makeDebugNameForIntersectionType(new Function1() { // from class: kotlin.reflect.jvm.internal.impl.renderer.DescriptorRendererImpl$$Lambda$1
                    @Override // kotlin.jvm.functions.Function1
                    public Object invoke(Object obj) {
                        return DescriptorRendererImpl.renderTypeConstructor$lambda$8((KotlinType) obj);
                    }
                });
            }
            return typeConstructor.toString();
        }
        throw new IllegalStateException(("Unexpected classifier: " + classifierDescriptorMo3823getDeclarationDescriptor.getClass()).toString());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Object renderTypeConstructor$lambda$8(KotlinType it) {
        Intrinsics.checkNotNullParameter(it, "it");
        return it instanceof StubTypeForBuilderInference ? ((StubTypeForBuilderInference) it).getOriginalTypeVariable() : it;
    }

    @Override // kotlin.reflect.jvm.internal.impl.renderer.DescriptorRenderer
    public String renderTypeProjection(TypeProjection typeProjection) {
        Intrinsics.checkNotNullParameter(typeProjection, "typeProjection");
        StringBuilder sb = new StringBuilder();
        appendTypeProjections(sb, CollectionsKt.listOf(typeProjection));
        return sb.toString();
    }

    private final void appendTypeProjections(StringBuilder sb, List<? extends TypeProjection> list) {
        CollectionsKt.joinTo(list, sb, (112 & 2) != 0 ? ", " : ", ", (112 & 4) != 0 ? "" : null, (112 & 8) != 0 ? "" : null, (112 & 16) != 0 ? -1 : 0, (112 & 32) != 0 ? "..." : null, (112 & 64) != 0 ? null : new Function1(this) { // from class: kotlin.reflect.jvm.internal.impl.renderer.DescriptorRendererImpl$$Lambda$2
            private final DescriptorRendererImpl arg$0;

            {
                this.arg$0 = this;
            }

            @Override // kotlin.jvm.functions.Function1
            public Object invoke(Object obj) {
                return DescriptorRendererImpl.appendTypeProjections$lambda$10(this.arg$0, (TypeProjection) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final CharSequence appendTypeProjections$lambda$10(DescriptorRendererImpl descriptorRendererImpl, TypeProjection it) {
        Intrinsics.checkNotNullParameter(it, "it");
        if (it.isStarProjection()) {
            return "*";
        }
        KotlinType type = it.getType();
        Intrinsics.checkNotNullExpressionValue(type, "getType(...)");
        String strRenderType = descriptorRendererImpl.renderType(type);
        if (it.getProjectionKind() != Variance.INVARIANT) {
            strRenderType = it.getProjectionKind() + ' ' + strRenderType;
        }
        return strRenderType;
    }

    private final void renderFunctionType(StringBuilder sb, KotlinType kotlinType) {
        Name nameExtractParameterNameFromFunctionTypeArgument;
        int length = sb.length();
        renderAnnotations$default(getFunctionTypeAnnotationsRenderer(), sb, kotlinType, null, 2, null);
        boolean z = sb.length() != length;
        KotlinType receiverTypeFromFunctionType = FunctionTypesKt.getReceiverTypeFromFunctionType(kotlinType);
        List<KotlinType> contextReceiverTypesFromFunctionType = FunctionTypesKt.getContextReceiverTypesFromFunctionType(kotlinType);
        boolean zIsSuspendFunctionType = FunctionTypesKt.isSuspendFunctionType(kotlinType);
        boolean zIsMarkedNullable = kotlinType.isMarkedNullable();
        boolean z2 = zIsMarkedNullable || (z && receiverTypeFromFunctionType != null);
        if (z2) {
            if (zIsSuspendFunctionType) {
                sb.insert(length, CoreConstants.LEFT_PARENTHESIS_CHAR);
            } else {
                if (z) {
                    StringBuilder sb2 = sb;
                    CharsKt.isWhitespace(StringsKt.last(sb2));
                    if (sb.charAt(StringsKt.getLastIndex(sb2) - 1) != ')') {
                        sb.insert(StringsKt.getLastIndex(sb2), "()");
                    }
                }
                sb.append("(");
            }
        }
        renderModifier(sb, zIsSuspendFunctionType, "suspend");
        if (!contextReceiverTypesFromFunctionType.isEmpty()) {
            sb.append("context(");
            Iterator<KotlinType> it = contextReceiverTypesFromFunctionType.subList(0, CollectionsKt.getLastIndex(contextReceiverTypesFromFunctionType)).iterator();
            while (it.hasNext()) {
                renderNormalizedType(sb, it.next());
                sb.append(", ");
            }
            renderNormalizedType(sb, (KotlinType) CollectionsKt.last((List) contextReceiverTypesFromFunctionType));
            sb.append(") ");
        }
        if (receiverTypeFromFunctionType != null) {
            boolean z3 = (shouldRenderAsPrettyFunctionType(receiverTypeFromFunctionType) && !receiverTypeFromFunctionType.isMarkedNullable()) || hasModifiersOrAnnotations(receiverTypeFromFunctionType) || (receiverTypeFromFunctionType instanceof DefinitelyNotNullType);
            if (z3) {
                sb.append("(");
            }
            renderNormalizedType(sb, receiverTypeFromFunctionType);
            if (z3) {
                sb.append(")");
            }
            sb.append(".");
        }
        sb.append("(");
        if (FunctionTypesKt.isBuiltinExtensionFunctionalType(kotlinType) && kotlinType.getArguments().size() <= 1) {
            sb.append("???");
        } else {
            int i = 0;
            for (TypeProjection typeProjection : FunctionTypesKt.getValueParameterTypesFromFunctionType(kotlinType)) {
                int i2 = i + 1;
                if (i > 0) {
                    sb.append(", ");
                }
                if (getParameterNamesInFunctionalTypes()) {
                    KotlinType type = typeProjection.getType();
                    Intrinsics.checkNotNullExpressionValue(type, "getType(...)");
                    nameExtractParameterNameFromFunctionTypeArgument = FunctionTypesKt.extractParameterNameFromFunctionTypeArgument(type);
                } else {
                    nameExtractParameterNameFromFunctionTypeArgument = null;
                }
                if (nameExtractParameterNameFromFunctionTypeArgument != null) {
                    sb.append(renderName(nameExtractParameterNameFromFunctionTypeArgument, false));
                    sb.append(": ");
                }
                sb.append(renderTypeProjection(typeProjection));
                i = i2;
            }
        }
        sb.append(") ").append(arrow()).append(StringUtils.SPACE);
        renderNormalizedType(sb, FunctionTypesKt.getReturnTypeFromFunctionType(kotlinType));
        if (z2) {
            sb.append(")");
        }
        if (zIsMarkedNullable) {
            sb.append(CallerData.NA);
        }
    }

    private final boolean hasModifiersOrAnnotations(KotlinType kotlinType) {
        return FunctionTypesKt.isSuspendFunctionType(kotlinType) || !kotlinType.getAnnotations().isEmpty();
    }

    private final void appendDefinedIn(StringBuilder sb, DeclarationDescriptor declarationDescriptor) {
        DeclarationDescriptor containingDeclaration;
        String name;
        if ((declarationDescriptor instanceof PackageFragmentDescriptor) || (declarationDescriptor instanceof PackageViewDescriptor) || (containingDeclaration = declarationDescriptor.getContainingDeclaration()) == null || (containingDeclaration instanceof ModuleDescriptor)) {
            return;
        }
        sb.append(StringUtils.SPACE).append(renderMessage("defined in")).append(StringUtils.SPACE);
        FqNameUnsafe fqName = DescriptorUtils.getFqName(containingDeclaration);
        Intrinsics.checkNotNullExpressionValue(fqName, "getFqName(...)");
        sb.append(fqName.isRoot() ? "root package" : renderFqName(fqName));
        if (getWithSourceFileForTopLevel() && (containingDeclaration instanceof PackageFragmentDescriptor) && (declarationDescriptor instanceof DeclarationDescriptorWithSource) && (name = ((DeclarationDescriptorWithSource) declarationDescriptor).getSource().getContainingFile().getName()) != null) {
            sb.append(StringUtils.SPACE).append(renderMessage("in file")).append(StringUtils.SPACE).append(name);
        }
    }

    static /* synthetic */ void renderAnnotations$default(DescriptorRendererImpl descriptorRendererImpl, StringBuilder sb, Annotated annotated, AnnotationUseSiteTarget annotationUseSiteTarget, int i, Object obj) {
        if ((i & 2) != 0) {
            annotationUseSiteTarget = null;
        }
        descriptorRendererImpl.renderAnnotations(sb, annotated, annotationUseSiteTarget);
    }

    private final void renderAnnotations(StringBuilder sb, Annotated annotated, AnnotationUseSiteTarget annotationUseSiteTarget) {
        if (getModifiers().contains(DescriptorRendererModifier.ANNOTATIONS)) {
            Set<FqName> excludedTypeAnnotationClasses = annotated instanceof KotlinType ? getExcludedTypeAnnotationClasses() : getExcludedAnnotationClasses();
            Function1<AnnotationDescriptor, Boolean> annotationFilter = getAnnotationFilter();
            for (AnnotationDescriptor annotationDescriptor : annotated.getAnnotations()) {
                if (!CollectionsKt.contains(excludedTypeAnnotationClasses, annotationDescriptor.getFqName()) && !isParameterName(annotationDescriptor) && (annotationFilter == null || annotationFilter.invoke(annotationDescriptor).booleanValue())) {
                    sb.append(renderAnnotation(annotationDescriptor, annotationUseSiteTarget));
                    if (getEachAnnotationOnNewLine()) {
                        sb.append('\n');
                    } else {
                        sb.append(StringUtils.SPACE);
                    }
                }
            }
        }
    }

    private final boolean isParameterName(AnnotationDescriptor annotationDescriptor) {
        return Intrinsics.areEqual(annotationDescriptor.getFqName(), StandardNames.FqNames.parameterName);
    }

    @Override // kotlin.reflect.jvm.internal.impl.renderer.DescriptorRenderer
    public String renderAnnotation(AnnotationDescriptor annotation, AnnotationUseSiteTarget annotationUseSiteTarget) {
        Intrinsics.checkNotNullParameter(annotation, "annotation");
        StringBuilder sb = new StringBuilder();
        sb.append('@');
        if (annotationUseSiteTarget != null) {
            sb.append(annotationUseSiteTarget.getRenderName() + CoreConstants.COLON_CHAR);
        }
        KotlinType type = annotation.getType();
        sb.append(renderType(type));
        if (getIncludeAnnotationArguments()) {
            List<String> listRenderAndSortAnnotationArguments = renderAndSortAnnotationArguments(annotation);
            if (getIncludeEmptyAnnotationArguments() || !listRenderAndSortAnnotationArguments.isEmpty()) {
                CollectionsKt.joinTo(listRenderAndSortAnnotationArguments, sb, (112 & 2) != 0 ? ", " : ", ", (112 & 4) != 0 ? "" : "(", (112 & 8) != 0 ? "" : ")", (112 & 16) != 0 ? -1 : 0, (112 & 32) != 0 ? "..." : null, (112 & 64) != 0 ? null : null);
            }
        }
        if (getVerbose() && (KotlinTypeKt.isError(type) || (type.getConstructor().mo3823getDeclarationDescriptor() instanceof NotFoundClasses.MockClassDescriptor))) {
            sb.append(" /* annotation class not found */");
        }
        return sb.toString();
    }

    private final List<String> renderAndSortAnnotationArguments(AnnotationDescriptor annotationDescriptor) {
        ClassConstructorDescriptor classConstructorDescriptorMo3813getUnsubstitutedPrimaryConstructor;
        List<ValueParameterDescriptor> valueParameters;
        Map<Name, ConstantValue<?>> allValueArguments = annotationDescriptor.getAllValueArguments();
        ArrayList arrayListEmptyList = null;
        ClassDescriptor annotationClass = getRenderDefaultAnnotationArguments() ? DescriptorUtilsKt.getAnnotationClass(annotationDescriptor) : null;
        if (annotationClass != null && (classConstructorDescriptorMo3813getUnsubstitutedPrimaryConstructor = annotationClass.mo3813getUnsubstitutedPrimaryConstructor()) != null && (valueParameters = classConstructorDescriptorMo3813getUnsubstitutedPrimaryConstructor.getValueParameters()) != null) {
            ArrayList arrayList = new ArrayList();
            for (Object obj : valueParameters) {
                if (((ValueParameterDescriptor) obj).declaresDefaultValue()) {
                    arrayList.add(obj);
                }
            }
            ArrayList arrayList2 = arrayList;
            ArrayList arrayList3 = new ArrayList(CollectionsKt.collectionSizeOrDefault(arrayList2, 10));
            Iterator it = arrayList2.iterator();
            while (it.hasNext()) {
                arrayList3.add(((ValueParameterDescriptor) it.next()).getName());
            }
            arrayListEmptyList = arrayList3;
        }
        if (arrayListEmptyList == null) {
            arrayListEmptyList = CollectionsKt.emptyList();
        }
        ArrayList arrayList4 = new ArrayList();
        for (Object obj2 : arrayListEmptyList) {
            if (!allValueArguments.containsKey((Name) obj2)) {
                arrayList4.add(obj2);
            }
        }
        ArrayList arrayList5 = arrayList4;
        ArrayList arrayList6 = new ArrayList(CollectionsKt.collectionSizeOrDefault(arrayList5, 10));
        Iterator it2 = arrayList5.iterator();
        while (it2.hasNext()) {
            arrayList6.add(((Name) it2.next()).asString() + " = ...");
        }
        ArrayList arrayList7 = arrayList6;
        Set<Map.Entry<Name, ConstantValue<?>>> setEntrySet = allValueArguments.entrySet();
        ArrayList arrayList8 = new ArrayList(CollectionsKt.collectionSizeOrDefault(setEntrySet, 10));
        Iterator<T> it3 = setEntrySet.iterator();
        while (it3.hasNext()) {
            Map.Entry entry = (Map.Entry) it3.next();
            Name name = (Name) entry.getKey();
            arrayList8.add(name.asString() + " = " + (!arrayListEmptyList.contains(name) ? renderConstant((ConstantValue) entry.getValue()) : "..."));
        }
        return CollectionsKt.sorted(CollectionsKt.plus((Collection) arrayList7, (Iterable) arrayList8));
    }

    private final String renderConstant(ConstantValue<?> constantValue) {
        Function1<ConstantValue<?>, String> propertyConstantRenderer = this.options.getPropertyConstantRenderer();
        if (propertyConstantRenderer != null) {
            return propertyConstantRenderer.invoke(constantValue);
        }
        if (!(constantValue instanceof ArrayValue)) {
            if (constantValue instanceof AnnotationValue) {
                return StringsKt.removePrefix(DescriptorRenderer.renderAnnotation$default(this, ((AnnotationValue) constantValue).getValue(), null, 2, null), (CharSequence) "@");
            }
            if (constantValue instanceof KClassValue) {
                KClassValue.Value value = ((KClassValue) constantValue).getValue();
                if (value instanceof KClassValue.Value.LocalClass) {
                    return ((KClassValue.Value.LocalClass) value).getType() + "::class";
                }
                if (!(value instanceof KClassValue.Value.NormalClass)) {
                    throw new NoWhenBranchMatchedException();
                }
                KClassValue.Value.NormalClass normalClass = (KClassValue.Value.NormalClass) value;
                String strAsString = normalClass.getClassId().asSingleFqName().asString();
                int arrayDimensions = normalClass.getArrayDimensions();
                for (int i = 0; i < arrayDimensions; i++) {
                    strAsString = "kotlin.Array<" + strAsString + Typography.greater;
                }
                return strAsString + "::class";
            }
            return constantValue.toString();
        }
        List<? extends ConstantValue<?>> value2 = ((ArrayValue) constantValue).getValue();
        ArrayList arrayList = new ArrayList();
        Iterator<T> it = value2.iterator();
        while (it.hasNext()) {
            String strRenderConstant = renderConstant((ConstantValue) it.next());
            if (strRenderConstant != null) {
                arrayList.add(strRenderConstant);
            }
        }
        return CollectionsKt.joinToString$default(arrayList, ", ", "{", StringSubstitutor.DEFAULT_VAR_END, 0, null, null, 56, null);
    }

    private final boolean renderVisibility(DescriptorVisibility descriptorVisibility, StringBuilder sb) {
        if (!getModifiers().contains(DescriptorRendererModifier.VISIBILITY)) {
            return false;
        }
        if (getNormalizedVisibilities()) {
            descriptorVisibility = descriptorVisibility.normalize();
        }
        if (!getRenderDefaultVisibility() && Intrinsics.areEqual(descriptorVisibility, DescriptorVisibilities.DEFAULT_VISIBILITY)) {
            return false;
        }
        sb.append(renderKeyword(descriptorVisibility.getInternalDisplayName())).append(StringUtils.SPACE);
        return true;
    }

    private final void renderModality(Modality modality, StringBuilder sb, Modality modality2) {
        if (getRenderDefaultModality() || modality != modality2) {
            renderModifier(sb, getModifiers().contains(DescriptorRendererModifier.MODALITY), CapitalizeDecapitalizeKt.toLowerCaseAsciiOnly(modality.name()));
        }
    }

    private final Modality implicitModalityWithoutExtensions(MemberDescriptor memberDescriptor) {
        if (memberDescriptor instanceof ClassDescriptor) {
            return ((ClassDescriptor) memberDescriptor).getKind() == ClassKind.INTERFACE ? Modality.ABSTRACT : Modality.FINAL;
        }
        DeclarationDescriptor containingDeclaration = memberDescriptor.getContainingDeclaration();
        ClassDescriptor classDescriptor = containingDeclaration instanceof ClassDescriptor ? (ClassDescriptor) containingDeclaration : null;
        if (classDescriptor != null && (memberDescriptor instanceof CallableMemberDescriptor)) {
            CallableMemberDescriptor callableMemberDescriptor = (CallableMemberDescriptor) memberDescriptor;
            Collection<? extends CallableMemberDescriptor> overriddenDescriptors = callableMemberDescriptor.getOverriddenDescriptors();
            Intrinsics.checkNotNullExpressionValue(overriddenDescriptors, "getOverriddenDescriptors(...)");
            if (!overriddenDescriptors.isEmpty() && classDescriptor.getModality() != Modality.FINAL) {
                return Modality.OPEN;
            }
            if (classDescriptor.getKind() != ClassKind.INTERFACE || Intrinsics.areEqual(callableMemberDescriptor.getVisibility(), DescriptorVisibilities.PRIVATE)) {
                return Modality.FINAL;
            }
            return callableMemberDescriptor.getModality() == Modality.ABSTRACT ? Modality.ABSTRACT : Modality.OPEN;
        }
        return Modality.FINAL;
    }

    private final void renderModalityForCallable(CallableMemberDescriptor callableMemberDescriptor, StringBuilder sb) {
        if (DescriptorUtils.isTopLevelDeclaration(callableMemberDescriptor) && callableMemberDescriptor.getModality() == Modality.FINAL) {
            return;
        }
        if (getOverrideRenderingPolicy() == OverrideRenderingPolicy.RENDER_OVERRIDE && callableMemberDescriptor.getModality() == Modality.OPEN && overridesSomething(callableMemberDescriptor)) {
            return;
        }
        Modality modality = callableMemberDescriptor.getModality();
        Intrinsics.checkNotNullExpressionValue(modality, "getModality(...)");
        renderModality(modality, sb, implicitModalityWithoutExtensions(callableMemberDescriptor));
    }

    private final void renderOverride(CallableMemberDescriptor callableMemberDescriptor, StringBuilder sb) {
        if (getModifiers().contains(DescriptorRendererModifier.OVERRIDE) && overridesSomething(callableMemberDescriptor) && getOverrideRenderingPolicy() != OverrideRenderingPolicy.RENDER_OPEN) {
            renderModifier(sb, true, "override");
            if (getVerbose()) {
                sb.append("/*").append(callableMemberDescriptor.getOverriddenDescriptors().size()).append("*/ ");
            }
        }
    }

    private final void renderMemberKind(CallableMemberDescriptor callableMemberDescriptor, StringBuilder sb) {
        if (getModifiers().contains(DescriptorRendererModifier.MEMBER_KIND) && getVerbose() && callableMemberDescriptor.getKind() != CallableMemberDescriptor.Kind.DECLARATION) {
            sb.append("/*").append(CapitalizeDecapitalizeKt.toLowerCaseAsciiOnly(callableMemberDescriptor.getKind().name())).append("*/ ");
        }
    }

    private final void renderModifier(StringBuilder sb, boolean z, String str) {
        if (z) {
            sb.append(renderKeyword(str));
            sb.append(StringUtils.SPACE);
        }
    }

    private final void renderMemberModifiers(MemberDescriptor memberDescriptor, StringBuilder sb) {
        renderModifier(sb, memberDescriptor.isExternal(), "external");
        renderModifier(sb, getModifiers().contains(DescriptorRendererModifier.EXPECT) && memberDescriptor.isExpect(), "expect");
        renderModifier(sb, getModifiers().contains(DescriptorRendererModifier.ACTUAL) && memberDescriptor.isActual(), "actual");
    }

    /* JADX WARN: Code duplicated, block: B:15:0x003b  */
    /* JADX WARN: Code duplicated, block: B:29:0x0072 A[EDGE_INSN: B:29:0x0072->B:30:0x0073 BREAK  A[LOOP:1: B:22:0x0059->B:37:?]] */
    private final void renderAdditionalModifiers(FunctionDescriptor functionDescriptor, StringBuilder sb) {
        boolean z;
        boolean z2 = true;
        if (functionDescriptor.isOperator()) {
            Collection<? extends FunctionDescriptor> overriddenDescriptors = functionDescriptor.getOverriddenDescriptors();
            Intrinsics.checkNotNullExpressionValue(overriddenDescriptors, "getOverriddenDescriptors(...)");
            Collection<? extends FunctionDescriptor> collection = overriddenDescriptors;
            if (!collection.isEmpty()) {
                Iterator<T> it = collection.iterator();
                while (true) {
                    if (it.hasNext()) {
                        if (((FunctionDescriptor) it.next()).isOperator()) {
                            if (!getAlwaysRenderModifiers()) {
                                z = false;
                            }
                        }
                    }
                }
            }
            z = true;
        } else {
            z = false;
        }
        if (!functionDescriptor.isInfix()) {
            z2 = false;
            break;
        }
        Collection<? extends FunctionDescriptor> overriddenDescriptors2 = functionDescriptor.getOverriddenDescriptors();
        Intrinsics.checkNotNullExpressionValue(overriddenDescriptors2, "getOverriddenDescriptors(...)");
        Collection<? extends FunctionDescriptor> collection2 = overriddenDescriptors2;
        if (!collection2.isEmpty()) {
            Iterator<T> it2 = collection2.iterator();
            while (it2.hasNext()) {
                if (((FunctionDescriptor) it2.next()).isInfix()) {
                    if (!getAlwaysRenderModifiers()) {
                        z2 = false;
                        break;
                    }
                    break;
                }
            }
        }
        renderModifier(sb, functionDescriptor.isTailrec(), "tailrec");
        renderSuspendModifier(functionDescriptor, sb);
        renderModifier(sb, functionDescriptor.isInline(), "inline");
        renderModifier(sb, z2, "infix");
        renderModifier(sb, z, "operator");
    }

    private final void renderSuspendModifier(FunctionDescriptor functionDescriptor, StringBuilder sb) {
        renderModifier(sb, functionDescriptor.isSuspend(), "suspend");
    }

    @Override // kotlin.reflect.jvm.internal.impl.renderer.DescriptorRenderer
    public String render(DeclarationDescriptor declarationDescriptor) {
        Intrinsics.checkNotNullParameter(declarationDescriptor, "declarationDescriptor");
        StringBuilder sb = new StringBuilder();
        declarationDescriptor.accept(new RenderDeclarationDescriptorVisitor(), sb);
        if (getWithDefinedIn()) {
            appendDefinedIn(sb, declarationDescriptor);
        }
        return sb.toString();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void renderTypeParameter(TypeParameterDescriptor typeParameterDescriptor, StringBuilder sb, boolean z) {
        if (z) {
            sb.append(lt());
        }
        if (getVerbose()) {
            sb.append("/*").append(typeParameterDescriptor.getIndex()).append("*/ ");
        }
        renderModifier(sb, typeParameterDescriptor.isReified(), "reified");
        String label = typeParameterDescriptor.getVariance().getLabel();
        boolean z2 = true;
        renderModifier(sb, label.length() > 0, label);
        renderAnnotations$default(this, sb, typeParameterDescriptor, null, 2, null);
        renderName(typeParameterDescriptor, sb, z);
        int size = typeParameterDescriptor.getUpperBounds().size();
        if ((size > 1 && !z) || size == 1) {
            KotlinType next = typeParameterDescriptor.getUpperBounds().iterator().next();
            if (!KotlinBuiltIns.isDefaultBound(next)) {
                StringBuilder sbAppend = sb.append(" : ");
                Intrinsics.checkNotNull(next);
                sbAppend.append(renderType(next));
            }
        } else if (z) {
            for (KotlinType kotlinType : typeParameterDescriptor.getUpperBounds()) {
                if (!KotlinBuiltIns.isDefaultBound(kotlinType)) {
                    if (z2) {
                        sb.append(" : ");
                    } else {
                        sb.append(" & ");
                    }
                    Intrinsics.checkNotNull(kotlinType);
                    sb.append(renderType(kotlinType));
                    z2 = false;
                }
            }
        }
        if (z) {
            sb.append(gt());
        }
    }

    private final void renderTypeParameters(List<? extends TypeParameterDescriptor> list, StringBuilder sb, boolean z) {
        if (getWithoutTypeParameters() || list.isEmpty()) {
            return;
        }
        sb.append(lt());
        renderTypeParameterList(sb, list);
        sb.append(gt());
        if (z) {
            sb.append(StringUtils.SPACE);
        }
    }

    private final void renderTypeParameterList(StringBuilder sb, List<? extends TypeParameterDescriptor> list) {
        Iterator<? extends TypeParameterDescriptor> it = list.iterator();
        while (it.hasNext()) {
            renderTypeParameter(it.next(), sb, false);
            if (it.hasNext()) {
                sb.append(", ");
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void renderFunction(FunctionDescriptor functionDescriptor, StringBuilder sb) {
        DescriptorRendererImpl descriptorRendererImpl;
        StringBuilder sb2;
        if (getStartFromName()) {
            descriptorRendererImpl = this;
            sb2 = sb;
        } else {
            if (getStartFromDeclarationKeyword()) {
                descriptorRendererImpl = this;
                sb2 = sb;
            } else {
                List<ReceiverParameterDescriptor> contextReceiverParameters = functionDescriptor.getContextReceiverParameters();
                Intrinsics.checkNotNullExpressionValue(contextReceiverParameters, "getContextReceiverParameters(...)");
                renderContextReceivers(contextReceiverParameters, sb);
                descriptorRendererImpl = this;
                sb2 = sb;
                renderAnnotations$default(descriptorRendererImpl, sb2, functionDescriptor, null, 2, null);
                DescriptorVisibility visibility = functionDescriptor.getVisibility();
                Intrinsics.checkNotNullExpressionValue(visibility, "getVisibility(...)");
                descriptorRendererImpl.renderVisibility(visibility, sb2);
                FunctionDescriptor functionDescriptor2 = functionDescriptor;
                descriptorRendererImpl.renderModalityForCallable(functionDescriptor2, sb2);
                if (descriptorRendererImpl.getIncludeAdditionalModifiers()) {
                    descriptorRendererImpl.renderMemberModifiers(functionDescriptor, sb2);
                }
                descriptorRendererImpl.renderOverride(functionDescriptor2, sb2);
                if (descriptorRendererImpl.getIncludeAdditionalModifiers()) {
                    descriptorRendererImpl.renderAdditionalModifiers(functionDescriptor, sb2);
                } else {
                    descriptorRendererImpl.renderSuspendModifier(functionDescriptor, sb2);
                }
                descriptorRendererImpl.renderMemberKind(functionDescriptor2, sb2);
                if (descriptorRendererImpl.getVerbose()) {
                    if (functionDescriptor.isHiddenToOvercomeSignatureClash()) {
                        sb2.append("/*isHiddenToOvercomeSignatureClash*/ ");
                    }
                    if (functionDescriptor.isHiddenForResolutionEverywhereBesideSupercalls()) {
                        sb2.append("/*isHiddenForResolutionEverywhereBesideSupercalls*/ ");
                    }
                }
            }
            sb2.append(descriptorRendererImpl.renderKeyword("fun")).append(StringUtils.SPACE);
            List<TypeParameterDescriptor> typeParameters = functionDescriptor.getTypeParameters();
            Intrinsics.checkNotNullExpressionValue(typeParameters, "getTypeParameters(...)");
            descriptorRendererImpl.renderTypeParameters(typeParameters, sb2, true);
            descriptorRendererImpl.renderReceiver(functionDescriptor, sb2);
        }
        descriptorRendererImpl.renderName(functionDescriptor, sb2, true);
        List<ValueParameterDescriptor> valueParameters = functionDescriptor.getValueParameters();
        Intrinsics.checkNotNullExpressionValue(valueParameters, "getValueParameters(...)");
        descriptorRendererImpl.renderValueParameters(valueParameters, functionDescriptor.hasSynthesizedParameterNames(), sb2);
        descriptorRendererImpl.renderReceiverAfterName(functionDescriptor, sb2);
        KotlinType returnType = functionDescriptor.getReturnType();
        if (!descriptorRendererImpl.getWithoutReturnType() && (descriptorRendererImpl.getUnitReturnType() || returnType == null || !KotlinBuiltIns.isUnit(returnType))) {
            sb2.append(": ").append(returnType == null ? "[NULL]" : descriptorRendererImpl.renderType(returnType));
        }
        List<TypeParameterDescriptor> typeParameters2 = functionDescriptor.getTypeParameters();
        Intrinsics.checkNotNullExpressionValue(typeParameters2, "getTypeParameters(...)");
        descriptorRendererImpl.renderWhereSuffix(typeParameters2, sb2);
    }

    private final void renderReceiverAfterName(CallableDescriptor callableDescriptor, StringBuilder sb) {
        ReceiverParameterDescriptor extensionReceiverParameter;
        if (getReceiverAfterName() && (extensionReceiverParameter = callableDescriptor.getExtensionReceiverParameter()) != null) {
            StringBuilder sbAppend = sb.append(" on ");
            KotlinType type = extensionReceiverParameter.getType();
            Intrinsics.checkNotNullExpressionValue(type, "getType(...)");
            sbAppend.append(renderType(type));
        }
    }

    private final String renderForReceiver(KotlinType kotlinType) {
        String strRenderType = renderType(kotlinType);
        return ((!shouldRenderAsPrettyFunctionType(kotlinType) || TypeUtils.isNullableType(kotlinType)) && !(kotlinType instanceof DefinitelyNotNullType)) ? strRenderType : "(" + strRenderType + CoreConstants.RIGHT_PARENTHESIS_CHAR;
    }

    private final void renderContextReceivers(List<? extends ReceiverParameterDescriptor> list, StringBuilder sb) {
        if (list.isEmpty()) {
            return;
        }
        sb.append("context(");
        int i = 0;
        for (ReceiverParameterDescriptor receiverParameterDescriptor : list) {
            int i2 = i + 1;
            renderAnnotations(sb, receiverParameterDescriptor, AnnotationUseSiteTarget.RECEIVER);
            KotlinType type = receiverParameterDescriptor.getType();
            Intrinsics.checkNotNullExpressionValue(type, "getType(...)");
            sb.append(renderForReceiver(type));
            if (i == CollectionsKt.getLastIndex(list)) {
                sb.append(") ");
            } else {
                sb.append(", ");
            }
            i = i2;
        }
    }

    private final void renderReceiver(CallableDescriptor callableDescriptor, StringBuilder sb) {
        ReceiverParameterDescriptor extensionReceiverParameter = callableDescriptor.getExtensionReceiverParameter();
        if (extensionReceiverParameter != null) {
            renderAnnotations(sb, extensionReceiverParameter, AnnotationUseSiteTarget.RECEIVER);
            KotlinType type = extensionReceiverParameter.getType();
            Intrinsics.checkNotNullExpressionValue(type, "getType(...)");
            sb.append(renderForReceiver(type)).append(".");
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Code duplicated, block: B:9:0x0035  */
    public final void renderConstructor(ConstructorDescriptor constructorDescriptor, StringBuilder sb) {
        boolean z;
        ClassConstructorDescriptor classConstructorDescriptorMo3813getUnsubstitutedPrimaryConstructor;
        renderAnnotations$default(this, sb, constructorDescriptor, null, 2, null);
        if (this.options.getRenderDefaultVisibility() || constructorDescriptor.getConstructedClass().getModality() != Modality.SEALED) {
            DescriptorVisibility visibility = constructorDescriptor.getVisibility();
            Intrinsics.checkNotNullExpressionValue(visibility, "getVisibility(...)");
            if (renderVisibility(visibility, sb)) {
                z = true;
            } else {
                z = false;
            }
        } else {
            z = false;
        }
        renderMemberKind(constructorDescriptor, sb);
        boolean z2 = getRenderConstructorKeyword() || !constructorDescriptor.isPrimary() || z;
        if (z2) {
            sb.append(renderKeyword("constructor"));
        }
        ClassifierDescriptorWithTypeParameters containingDeclaration = constructorDescriptor.getContainingDeclaration();
        Intrinsics.checkNotNullExpressionValue(containingDeclaration, "getContainingDeclaration(...)");
        if (getSecondaryConstructorsAsPrimary()) {
            if (z2) {
                sb.append(StringUtils.SPACE);
            }
            renderName(containingDeclaration, sb, true);
            List<TypeParameterDescriptor> typeParameters = constructorDescriptor.getTypeParameters();
            Intrinsics.checkNotNullExpressionValue(typeParameters, "getTypeParameters(...)");
            renderTypeParameters(typeParameters, sb, false);
        }
        List<ValueParameterDescriptor> valueParameters = constructorDescriptor.getValueParameters();
        Intrinsics.checkNotNullExpressionValue(valueParameters, "getValueParameters(...)");
        renderValueParameters(valueParameters, constructorDescriptor.hasSynthesizedParameterNames(), sb);
        if (getRenderConstructorDelegation() && !constructorDescriptor.isPrimary() && (containingDeclaration instanceof ClassDescriptor) && (classConstructorDescriptorMo3813getUnsubstitutedPrimaryConstructor = ((ClassDescriptor) containingDeclaration).mo3813getUnsubstitutedPrimaryConstructor()) != null) {
            List<ValueParameterDescriptor> valueParameters2 = classConstructorDescriptorMo3813getUnsubstitutedPrimaryConstructor.getValueParameters();
            Intrinsics.checkNotNullExpressionValue(valueParameters2, "getValueParameters(...)");
            ArrayList arrayList = new ArrayList();
            for (Object obj : valueParameters2) {
                ValueParameterDescriptor valueParameterDescriptor = (ValueParameterDescriptor) obj;
                if (!valueParameterDescriptor.declaresDefaultValue() && valueParameterDescriptor.getVarargElementType() == null) {
                    arrayList.add(obj);
                }
            }
            ArrayList arrayList2 = arrayList;
            if (!arrayList2.isEmpty()) {
                sb.append(" : ").append(renderKeyword("this"));
                sb.append(CollectionsKt.joinToString$default(arrayList2, ", ", "(", ")", 0, null, new Function1() { // from class: kotlin.reflect.jvm.internal.impl.renderer.DescriptorRendererImpl$$Lambda$3
                    @Override // kotlin.jvm.functions.Function1
                    public Object invoke(Object obj2) {
                        return DescriptorRendererImpl.renderConstructor$lambda$26((ValueParameterDescriptor) obj2);
                    }
                }, 24, null));
            }
        }
        if (getSecondaryConstructorsAsPrimary()) {
            List<TypeParameterDescriptor> typeParameters2 = constructorDescriptor.getTypeParameters();
            Intrinsics.checkNotNullExpressionValue(typeParameters2, "getTypeParameters(...)");
            renderWhereSuffix(typeParameters2, sb);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final CharSequence renderConstructor$lambda$26(ValueParameterDescriptor valueParameterDescriptor) {
        return "";
    }

    private final void renderWhereSuffix(List<? extends TypeParameterDescriptor> list, StringBuilder sb) {
        if (getWithoutTypeParameters()) {
            return;
        }
        ArrayList arrayList = new ArrayList(0);
        for (TypeParameterDescriptor typeParameterDescriptor : list) {
            List<KotlinType> upperBounds = typeParameterDescriptor.getUpperBounds();
            Intrinsics.checkNotNullExpressionValue(upperBounds, "getUpperBounds(...)");
            for (KotlinType kotlinType : CollectionsKt.drop(upperBounds, 1)) {
                StringBuilder sb2 = new StringBuilder();
                Name name = typeParameterDescriptor.getName();
                Intrinsics.checkNotNullExpressionValue(name, "getName(...)");
                StringBuilder sbAppend = sb2.append(renderName(name, false)).append(" : ");
                Intrinsics.checkNotNull(kotlinType);
                arrayList.add(sbAppend.append(renderType(kotlinType)).toString());
            }
        }
        if (arrayList.isEmpty()) {
            return;
        }
        sb.append(StringUtils.SPACE).append(renderKeyword("where")).append(StringUtils.SPACE);
        CollectionsKt.joinTo(arrayList, sb, (112 & 2) != 0 ? ", " : ", ", (112 & 4) != 0 ? "" : null, (112 & 8) != 0 ? "" : null, (112 & 16) != 0 ? -1 : 0, (112 & 32) != 0 ? "..." : null, (112 & 64) != 0 ? null : null);
    }

    private final void renderValueParameters(Collection<? extends ValueParameterDescriptor> collection, boolean z, StringBuilder sb) {
        boolean zShouldRenderParameterNames = shouldRenderParameterNames(z);
        int size = collection.size();
        getValueParametersHandler().appendBeforeValueParameters(size, sb);
        int i = 0;
        for (ValueParameterDescriptor valueParameterDescriptor : collection) {
            getValueParametersHandler().appendBeforeValueParameter(valueParameterDescriptor, i, size, sb);
            renderValueParameter(valueParameterDescriptor, zShouldRenderParameterNames, sb, false);
            getValueParametersHandler().appendAfterValueParameter(valueParameterDescriptor, i, size, sb);
            i++;
        }
        getValueParametersHandler().appendAfterValueParameters(size, sb);
    }

    private final boolean shouldRenderParameterNames(boolean z) {
        int i = WhenMappings.$EnumSwitchMapping$1[getParameterNameRenderingPolicy().ordinal()];
        if (i == 1) {
            return true;
        }
        if (i == 2) {
            return !z;
        }
        if (i == 3) {
            return false;
        }
        throw new NoWhenBranchMatchedException();
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Code duplicated, block: B:17:0x0064  */
    public final void renderValueParameter(ValueParameterDescriptor valueParameterDescriptor, boolean z, StringBuilder sb, boolean z2) {
        boolean z3;
        if (z2) {
            sb.append(renderKeyword("value-parameter")).append(StringUtils.SPACE);
        }
        if (getVerbose()) {
            sb.append("/*").append(valueParameterDescriptor.getIndex()).append("*/ ");
        }
        renderAnnotations$default(this, sb, valueParameterDescriptor, null, 2, null);
        renderModifier(sb, valueParameterDescriptor.isCrossinline(), "crossinline");
        renderModifier(sb, valueParameterDescriptor.isNoinline(), "noinline");
        if (getRenderPrimaryConstructorParametersAsProperties()) {
            CallableDescriptor containingDeclaration = valueParameterDescriptor.getContainingDeclaration();
            ClassConstructorDescriptor classConstructorDescriptor = containingDeclaration instanceof ClassConstructorDescriptor ? (ClassConstructorDescriptor) containingDeclaration : null;
            if (classConstructorDescriptor != null) {
                z3 = classConstructorDescriptor.isPrimary();
            }
        }
        boolean z4 = z3;
        if (z4) {
            renderModifier(sb, getActualPropertiesInPrimaryConstructor(), "actual");
        }
        renderVariable(valueParameterDescriptor, z, sb, z2, z4);
        if (getDefaultParameterValueRenderer() != null) {
            if (getDebugMode() ? valueParameterDescriptor.declaresDefaultValue() : DescriptorUtilsKt.declaresOrInheritsDefaultValue(valueParameterDescriptor)) {
                StringBuilder sb2 = new StringBuilder(" = ");
                Function1<ValueParameterDescriptor, String> defaultParameterValueRenderer = getDefaultParameterValueRenderer();
                Intrinsics.checkNotNull(defaultParameterValueRenderer);
                sb.append(sb2.append(defaultParameterValueRenderer.invoke(valueParameterDescriptor)).toString());
            }
        }
    }

    static /* synthetic */ void renderValVarPrefix$default(DescriptorRendererImpl descriptorRendererImpl, VariableDescriptor variableDescriptor, StringBuilder sb, boolean z, int i, Object obj) {
        if ((i & 4) != 0) {
            z = false;
        }
        descriptorRendererImpl.renderValVarPrefix(variableDescriptor, sb, z);
    }

    private final void renderValVarPrefix(VariableDescriptor variableDescriptor, StringBuilder sb, boolean z) {
        if (z || !(variableDescriptor instanceof ValueParameterDescriptor)) {
            sb.append(renderKeyword(variableDescriptor.isVar() ? "var" : "val")).append(StringUtils.SPACE);
        }
    }

    private final void renderVariable(VariableDescriptor variableDescriptor, boolean z, StringBuilder sb, boolean z2, boolean z3) {
        KotlinType type = variableDescriptor.getType();
        Intrinsics.checkNotNullExpressionValue(type, "getType(...)");
        ValueParameterDescriptor valueParameterDescriptor = variableDescriptor instanceof ValueParameterDescriptor ? (ValueParameterDescriptor) variableDescriptor : null;
        KotlinType varargElementType = valueParameterDescriptor != null ? valueParameterDescriptor.getVarargElementType() : null;
        KotlinType kotlinType = varargElementType == null ? type : varargElementType;
        renderModifier(sb, varargElementType != null, "vararg");
        if (z3 || (z2 && !getStartFromName())) {
            renderValVarPrefix(variableDescriptor, sb, z3);
        }
        if (z) {
            renderName(variableDescriptor, sb, z2);
            sb.append(": ");
        }
        sb.append(renderType(kotlinType));
        renderInitializer(variableDescriptor, sb);
        if (!getVerbose() || varargElementType == null) {
            return;
        }
        sb.append(" /*").append(renderType(type)).append("*/");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void renderProperty(PropertyDescriptor propertyDescriptor, StringBuilder sb) {
        DescriptorRendererImpl descriptorRendererImpl;
        StringBuilder sb2;
        if (getStartFromName()) {
            descriptorRendererImpl = this;
            sb2 = sb;
        } else {
            if (!getStartFromDeclarationKeyword()) {
                List<ReceiverParameterDescriptor> contextReceiverParameters = propertyDescriptor.getContextReceiverParameters();
                Intrinsics.checkNotNullExpressionValue(contextReceiverParameters, "getContextReceiverParameters(...)");
                renderContextReceivers(contextReceiverParameters, sb);
                renderPropertyAnnotations(propertyDescriptor, sb);
                DescriptorVisibility visibility = propertyDescriptor.getVisibility();
                Intrinsics.checkNotNullExpressionValue(visibility, "getVisibility(...)");
                renderVisibility(visibility, sb);
                boolean z = false;
                renderModifier(sb, getModifiers().contains(DescriptorRendererModifier.CONST) && propertyDescriptor.isConst(), StringLookupFactory.KEY_CONST);
                renderMemberModifiers(propertyDescriptor, sb);
                PropertyDescriptor propertyDescriptor2 = propertyDescriptor;
                renderModalityForCallable(propertyDescriptor2, sb);
                renderOverride(propertyDescriptor2, sb);
                if (getModifiers().contains(DescriptorRendererModifier.LATEINIT) && propertyDescriptor.isLateInit()) {
                    z = true;
                }
                renderModifier(sb, z, "lateinit");
                renderMemberKind(propertyDescriptor2, sb);
            }
            descriptorRendererImpl = this;
            sb2 = sb;
            renderValVarPrefix$default(descriptorRendererImpl, propertyDescriptor, sb2, false, 4, null);
            List<TypeParameterDescriptor> typeParameters = propertyDescriptor.getTypeParameters();
            Intrinsics.checkNotNullExpressionValue(typeParameters, "getTypeParameters(...)");
            descriptorRendererImpl.renderTypeParameters(typeParameters, sb2, true);
            descriptorRendererImpl.renderReceiver(propertyDescriptor, sb2);
        }
        descriptorRendererImpl.renderName(propertyDescriptor, sb2, true);
        StringBuilder sbAppend = sb2.append(": ");
        KotlinType type = propertyDescriptor.getType();
        Intrinsics.checkNotNullExpressionValue(type, "getType(...)");
        sbAppend.append(descriptorRendererImpl.renderType(type));
        descriptorRendererImpl.renderReceiverAfterName(propertyDescriptor, sb2);
        descriptorRendererImpl.renderInitializer(propertyDescriptor, sb2);
        List<TypeParameterDescriptor> typeParameters2 = propertyDescriptor.getTypeParameters();
        Intrinsics.checkNotNullExpressionValue(typeParameters2, "getTypeParameters(...)");
        descriptorRendererImpl.renderWhereSuffix(typeParameters2, sb2);
    }

    private final void renderPropertyAnnotations(PropertyDescriptor propertyDescriptor, StringBuilder sb) {
        if (getModifiers().contains(DescriptorRendererModifier.ANNOTATIONS)) {
            renderAnnotations$default(this, sb, propertyDescriptor, null, 2, null);
            FieldDescriptor backingField = propertyDescriptor.getBackingField();
            if (backingField != null) {
                renderAnnotations(sb, backingField, AnnotationUseSiteTarget.FIELD);
            }
            FieldDescriptor delegateField = propertyDescriptor.getDelegateField();
            if (delegateField != null) {
                renderAnnotations(sb, delegateField, AnnotationUseSiteTarget.PROPERTY_DELEGATE_FIELD);
            }
            if (getPropertyAccessorRenderingPolicy() == PropertyAccessorRenderingPolicy.NONE) {
                PropertyGetterDescriptor getter = propertyDescriptor.getGetter();
                if (getter != null) {
                    renderAnnotations(sb, getter, AnnotationUseSiteTarget.PROPERTY_GETTER);
                }
                PropertySetterDescriptor setter = propertyDescriptor.getSetter();
                if (setter != null) {
                    renderAnnotations(sb, setter, AnnotationUseSiteTarget.PROPERTY_SETTER);
                    List<ValueParameterDescriptor> valueParameters = setter.getValueParameters();
                    Intrinsics.checkNotNullExpressionValue(valueParameters, "getValueParameters(...)");
                    ValueParameterDescriptor valueParameterDescriptor = (ValueParameterDescriptor) CollectionsKt.single((List) valueParameters);
                    Intrinsics.checkNotNull(valueParameterDescriptor);
                    renderAnnotations(sb, valueParameterDescriptor, AnnotationUseSiteTarget.SETTER_PARAMETER);
                }
            }
        }
    }

    private final void renderInitializer(VariableDescriptor variableDescriptor, StringBuilder sb) {
        ConstantValue<?> constantValueMo3815getCompileTimeInitializer;
        String strRenderConstant;
        if (!getIncludePropertyConstant() || (constantValueMo3815getCompileTimeInitializer = variableDescriptor.mo3815getCompileTimeInitializer()) == null || (strRenderConstant = renderConstant(constantValueMo3815getCompileTimeInitializer)) == null) {
            return;
        }
        sb.append(" = ").append(escape(strRenderConstant));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void renderTypeAlias(TypeAliasDescriptor typeAliasDescriptor, StringBuilder sb) {
        renderAnnotations$default(this, sb, typeAliasDescriptor, null, 2, null);
        DescriptorVisibility visibility = typeAliasDescriptor.getVisibility();
        Intrinsics.checkNotNullExpressionValue(visibility, "getVisibility(...)");
        renderVisibility(visibility, sb);
        renderMemberModifiers(typeAliasDescriptor, sb);
        sb.append(renderKeyword("typealias")).append(StringUtils.SPACE);
        renderName(typeAliasDescriptor, sb, true);
        List<TypeParameterDescriptor> declaredTypeParameters = typeAliasDescriptor.getDeclaredTypeParameters();
        Intrinsics.checkNotNullExpressionValue(declaredTypeParameters, "getDeclaredTypeParameters(...)");
        renderTypeParameters(declaredTypeParameters, sb, false);
        renderCapturedTypeParametersIfRequired(typeAliasDescriptor, sb);
        sb.append(" = ").append(renderType(typeAliasDescriptor.getUnderlyingType()));
    }

    private final void renderCapturedTypeParametersIfRequired(ClassifierDescriptorWithTypeParameters classifierDescriptorWithTypeParameters, StringBuilder sb) {
        List<TypeParameterDescriptor> declaredTypeParameters = classifierDescriptorWithTypeParameters.getDeclaredTypeParameters();
        Intrinsics.checkNotNullExpressionValue(declaredTypeParameters, "getDeclaredTypeParameters(...)");
        List<TypeParameterDescriptor> parameters = classifierDescriptorWithTypeParameters.getTypeConstructor().getParameters();
        Intrinsics.checkNotNullExpressionValue(parameters, "getParameters(...)");
        if (getVerbose() && classifierDescriptorWithTypeParameters.isInner() && parameters.size() > declaredTypeParameters.size()) {
            sb.append(" /*captured type parameters: ");
            renderTypeParameterList(sb, parameters.subList(declaredTypeParameters.size(), parameters.size()));
            sb.append("*/");
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void renderClass(ClassDescriptor classDescriptor, StringBuilder sb) {
        ClassConstructorDescriptor classConstructorDescriptorMo3813getUnsubstitutedPrimaryConstructor;
        boolean z = classDescriptor.getKind() == ClassKind.ENUM_ENTRY;
        if (!getStartFromName()) {
            List<ReceiverParameterDescriptor> contextReceivers = classDescriptor.getContextReceivers();
            Intrinsics.checkNotNullExpressionValue(contextReceivers, "getContextReceivers(...)");
            renderContextReceivers(contextReceivers, sb);
            renderAnnotations$default(this, sb, classDescriptor, null, 2, null);
            if (!z) {
                DescriptorVisibility visibility = classDescriptor.getVisibility();
                Intrinsics.checkNotNullExpressionValue(visibility, "getVisibility(...)");
                renderVisibility(visibility, sb);
            }
            if ((classDescriptor.getKind() != ClassKind.INTERFACE || classDescriptor.getModality() != Modality.ABSTRACT) && (!classDescriptor.getKind().isSingleton() || classDescriptor.getModality() != Modality.FINAL)) {
                Modality modality = classDescriptor.getModality();
                Intrinsics.checkNotNullExpressionValue(modality, "getModality(...)");
                renderModality(modality, sb, implicitModalityWithoutExtensions(classDescriptor));
            }
            renderMemberModifiers(classDescriptor, sb);
            renderModifier(sb, getModifiers().contains(DescriptorRendererModifier.INNER) && classDescriptor.isInner(), "inner");
            renderModifier(sb, getModifiers().contains(DescriptorRendererModifier.DATA) && classDescriptor.isData(), "data");
            renderModifier(sb, getModifiers().contains(DescriptorRendererModifier.INLINE) && classDescriptor.isInline(), "inline");
            renderModifier(sb, getModifiers().contains(DescriptorRendererModifier.VALUE) && classDescriptor.isValue(), "value");
            renderModifier(sb, getModifiers().contains(DescriptorRendererModifier.FUN) && classDescriptor.isFun(), "fun");
            renderClassKindPrefix(classDescriptor, sb);
        }
        ClassDescriptor classDescriptor2 = classDescriptor;
        if (!DescriptorUtils.isCompanionObject(classDescriptor2)) {
            if (!getStartFromName()) {
                renderSpaceIfNeeded(sb);
            }
            renderName(classDescriptor2, sb, true);
        } else {
            renderCompanionObjectName(classDescriptor2, sb);
        }
        if (z) {
            return;
        }
        List<TypeParameterDescriptor> declaredTypeParameters = classDescriptor.getDeclaredTypeParameters();
        Intrinsics.checkNotNullExpressionValue(declaredTypeParameters, "getDeclaredTypeParameters(...)");
        renderTypeParameters(declaredTypeParameters, sb, false);
        renderCapturedTypeParametersIfRequired(classDescriptor, sb);
        if (!classDescriptor.getKind().isSingleton() && getClassWithPrimaryConstructor() && (classConstructorDescriptorMo3813getUnsubstitutedPrimaryConstructor = classDescriptor.mo3813getUnsubstitutedPrimaryConstructor()) != null) {
            sb.append(StringUtils.SPACE);
            renderAnnotations$default(this, sb, classConstructorDescriptorMo3813getUnsubstitutedPrimaryConstructor, null, 2, null);
            DescriptorVisibility visibility2 = classConstructorDescriptorMo3813getUnsubstitutedPrimaryConstructor.getVisibility();
            Intrinsics.checkNotNullExpressionValue(visibility2, "getVisibility(...)");
            renderVisibility(visibility2, sb);
            sb.append(renderKeyword("constructor"));
            List<ValueParameterDescriptor> valueParameters = classConstructorDescriptorMo3813getUnsubstitutedPrimaryConstructor.getValueParameters();
            Intrinsics.checkNotNullExpressionValue(valueParameters, "getValueParameters(...)");
            renderValueParameters(valueParameters, classConstructorDescriptorMo3813getUnsubstitutedPrimaryConstructor.hasSynthesizedParameterNames(), sb);
        }
        renderSuperTypes(classDescriptor, sb);
        renderWhereSuffix(declaredTypeParameters, sb);
    }

    private final void renderSuperTypes(ClassDescriptor classDescriptor, StringBuilder sb) {
        if (getWithoutSuperTypes() || KotlinBuiltIns.isNothing(classDescriptor.getDefaultType())) {
            return;
        }
        Collection<KotlinType> collectionMo3824getSupertypes = classDescriptor.getTypeConstructor().mo3824getSupertypes();
        Intrinsics.checkNotNullExpressionValue(collectionMo3824getSupertypes, "getSupertypes(...)");
        if (collectionMo3824getSupertypes.isEmpty()) {
            return;
        }
        if (collectionMo3824getSupertypes.size() == 1 && KotlinBuiltIns.isAnyOrNullableAny(collectionMo3824getSupertypes.iterator().next())) {
            return;
        }
        renderSpaceIfNeeded(sb);
        sb.append(": ");
        CollectionsKt.joinTo(collectionMo3824getSupertypes, sb, (112 & 2) != 0 ? ", " : ", ", (112 & 4) != 0 ? "" : null, (112 & 8) != 0 ? "" : null, (112 & 16) != 0 ? -1 : 0, (112 & 32) != 0 ? "..." : null, (112 & 64) != 0 ? null : new Function1(this) { // from class: kotlin.reflect.jvm.internal.impl.renderer.DescriptorRendererImpl$$Lambda$4
            private final DescriptorRendererImpl arg$0;

            {
                this.arg$0 = this;
            }

            @Override // kotlin.jvm.functions.Function1
            public Object invoke(Object obj) {
                return DescriptorRendererImpl.renderSuperTypes$lambda$36(this.arg$0, (KotlinType) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final CharSequence renderSuperTypes$lambda$36(DescriptorRendererImpl descriptorRendererImpl, KotlinType kotlinType) {
        Intrinsics.checkNotNull(kotlinType);
        return descriptorRendererImpl.renderType(kotlinType);
    }

    private final void renderClassKindPrefix(ClassDescriptor classDescriptor, StringBuilder sb) {
        sb.append(renderKeyword(DescriptorRenderer.Companion.getClassifierKindPrefix(classDescriptor)));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void renderPackageView(PackageViewDescriptor packageViewDescriptor, StringBuilder sb) {
        renderPackageHeader(packageViewDescriptor.getFqName(), "package", sb);
        if (getDebugMode()) {
            sb.append(" in context of ");
            renderName(packageViewDescriptor.getModule(), sb, false);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void renderPackageFragment(PackageFragmentDescriptor packageFragmentDescriptor, StringBuilder sb) {
        renderPackageHeader(packageFragmentDescriptor.getFqName(), "package-fragment", sb);
        if (getDebugMode()) {
            sb.append(" in ");
            renderName(packageFragmentDescriptor.getContainingDeclaration(), sb, false);
        }
    }

    private final void renderPackageHeader(FqName fqName, String str, StringBuilder sb) {
        sb.append(renderKeyword(str));
        String strRenderFqName = renderFqName(fqName.toUnsafe());
        if (strRenderFqName.length() > 0) {
            sb.append(StringUtils.SPACE);
            sb.append(strRenderFqName);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void renderAccessorModifiers(PropertyAccessorDescriptor propertyAccessorDescriptor, StringBuilder sb) {
        renderMemberModifiers(propertyAccessorDescriptor, sb);
    }

    /* JADX INFO: compiled from: DescriptorRendererImpl.kt */
    private final class RenderDeclarationDescriptorVisitor implements DeclarationDescriptorVisitor<Unit, StringBuilder> {

        /* JADX INFO: compiled from: DescriptorRendererImpl.kt */
        public static final /* synthetic */ class WhenMappings {
            public static final /* synthetic */ int[] $EnumSwitchMapping$0;

            static {
                int[] iArr = new int[PropertyAccessorRenderingPolicy.values().length];
                try {
                    iArr[PropertyAccessorRenderingPolicy.PRETTY.ordinal()] = 1;
                } catch (NoSuchFieldError unused) {
                }
                try {
                    iArr[PropertyAccessorRenderingPolicy.DEBUG.ordinal()] = 2;
                } catch (NoSuchFieldError unused2) {
                }
                try {
                    iArr[PropertyAccessorRenderingPolicy.NONE.ordinal()] = 3;
                } catch (NoSuchFieldError unused3) {
                }
                $EnumSwitchMapping$0 = iArr;
            }
        }

        public RenderDeclarationDescriptorVisitor() {
        }

        @Override // kotlin.reflect.jvm.internal.impl.descriptors.DeclarationDescriptorVisitor
        public /* bridge */ /* synthetic */ Unit visitClassDescriptor(ClassDescriptor classDescriptor, StringBuilder sb) {
            visitClassDescriptor2(classDescriptor, sb);
            return Unit.INSTANCE;
        }

        @Override // kotlin.reflect.jvm.internal.impl.descriptors.DeclarationDescriptorVisitor
        public /* bridge */ /* synthetic */ Unit visitConstructorDescriptor(ConstructorDescriptor constructorDescriptor, StringBuilder sb) {
            visitConstructorDescriptor2(constructorDescriptor, sb);
            return Unit.INSTANCE;
        }

        @Override // kotlin.reflect.jvm.internal.impl.descriptors.DeclarationDescriptorVisitor
        public /* bridge */ /* synthetic */ Unit visitFunctionDescriptor(FunctionDescriptor functionDescriptor, StringBuilder sb) {
            visitFunctionDescriptor2(functionDescriptor, sb);
            return Unit.INSTANCE;
        }

        @Override // kotlin.reflect.jvm.internal.impl.descriptors.DeclarationDescriptorVisitor
        public /* bridge */ /* synthetic */ Unit visitModuleDeclaration(ModuleDescriptor moduleDescriptor, StringBuilder sb) {
            visitModuleDeclaration2(moduleDescriptor, sb);
            return Unit.INSTANCE;
        }

        @Override // kotlin.reflect.jvm.internal.impl.descriptors.DeclarationDescriptorVisitor
        public /* bridge */ /* synthetic */ Unit visitPackageFragmentDescriptor(PackageFragmentDescriptor packageFragmentDescriptor, StringBuilder sb) {
            visitPackageFragmentDescriptor2(packageFragmentDescriptor, sb);
            return Unit.INSTANCE;
        }

        @Override // kotlin.reflect.jvm.internal.impl.descriptors.DeclarationDescriptorVisitor
        public /* bridge */ /* synthetic */ Unit visitPackageViewDescriptor(PackageViewDescriptor packageViewDescriptor, StringBuilder sb) {
            visitPackageViewDescriptor2(packageViewDescriptor, sb);
            return Unit.INSTANCE;
        }

        @Override // kotlin.reflect.jvm.internal.impl.descriptors.DeclarationDescriptorVisitor
        public /* bridge */ /* synthetic */ Unit visitPropertyDescriptor(PropertyDescriptor propertyDescriptor, StringBuilder sb) {
            visitPropertyDescriptor2(propertyDescriptor, sb);
            return Unit.INSTANCE;
        }

        @Override // kotlin.reflect.jvm.internal.impl.descriptors.DeclarationDescriptorVisitor
        public /* bridge */ /* synthetic */ Unit visitPropertyGetterDescriptor(PropertyGetterDescriptor propertyGetterDescriptor, StringBuilder sb) {
            visitPropertyGetterDescriptor2(propertyGetterDescriptor, sb);
            return Unit.INSTANCE;
        }

        @Override // kotlin.reflect.jvm.internal.impl.descriptors.DeclarationDescriptorVisitor
        public /* bridge */ /* synthetic */ Unit visitPropertySetterDescriptor(PropertySetterDescriptor propertySetterDescriptor, StringBuilder sb) {
            visitPropertySetterDescriptor2(propertySetterDescriptor, sb);
            return Unit.INSTANCE;
        }

        @Override // kotlin.reflect.jvm.internal.impl.descriptors.DeclarationDescriptorVisitor
        public /* bridge */ /* synthetic */ Unit visitReceiverParameterDescriptor(ReceiverParameterDescriptor receiverParameterDescriptor, StringBuilder sb) {
            visitReceiverParameterDescriptor2(receiverParameterDescriptor, sb);
            return Unit.INSTANCE;
        }

        @Override // kotlin.reflect.jvm.internal.impl.descriptors.DeclarationDescriptorVisitor
        public /* bridge */ /* synthetic */ Unit visitTypeAliasDescriptor(TypeAliasDescriptor typeAliasDescriptor, StringBuilder sb) {
            visitTypeAliasDescriptor2(typeAliasDescriptor, sb);
            return Unit.INSTANCE;
        }

        @Override // kotlin.reflect.jvm.internal.impl.descriptors.DeclarationDescriptorVisitor
        public /* bridge */ /* synthetic */ Unit visitTypeParameterDescriptor(TypeParameterDescriptor typeParameterDescriptor, StringBuilder sb) {
            visitTypeParameterDescriptor2(typeParameterDescriptor, sb);
            return Unit.INSTANCE;
        }

        @Override // kotlin.reflect.jvm.internal.impl.descriptors.DeclarationDescriptorVisitor
        public /* bridge */ /* synthetic */ Unit visitValueParameterDescriptor(ValueParameterDescriptor valueParameterDescriptor, StringBuilder sb) {
            visitValueParameterDescriptor2(valueParameterDescriptor, sb);
            return Unit.INSTANCE;
        }

        /* JADX INFO: renamed from: visitValueParameterDescriptor, reason: avoid collision after fix types in other method */
        public void visitValueParameterDescriptor2(ValueParameterDescriptor descriptor, StringBuilder builder) {
            Intrinsics.checkNotNullParameter(descriptor, "descriptor");
            Intrinsics.checkNotNullParameter(builder, "builder");
            DescriptorRendererImpl.this.renderValueParameter(descriptor, true, builder, true);
        }

        /* JADX INFO: renamed from: visitPropertyDescriptor, reason: avoid collision after fix types in other method */
        public void visitPropertyDescriptor2(PropertyDescriptor descriptor, StringBuilder builder) {
            Intrinsics.checkNotNullParameter(descriptor, "descriptor");
            Intrinsics.checkNotNullParameter(builder, "builder");
            DescriptorRendererImpl.this.renderProperty(descriptor, builder);
        }

        /* JADX INFO: renamed from: visitPropertyGetterDescriptor, reason: avoid collision after fix types in other method */
        public void visitPropertyGetterDescriptor2(PropertyGetterDescriptor descriptor, StringBuilder builder) {
            Intrinsics.checkNotNullParameter(descriptor, "descriptor");
            Intrinsics.checkNotNullParameter(builder, "builder");
            visitPropertyAccessorDescriptor(descriptor, builder, "getter");
        }

        /* JADX INFO: renamed from: visitPropertySetterDescriptor, reason: avoid collision after fix types in other method */
        public void visitPropertySetterDescriptor2(PropertySetterDescriptor descriptor, StringBuilder builder) {
            Intrinsics.checkNotNullParameter(descriptor, "descriptor");
            Intrinsics.checkNotNullParameter(builder, "builder");
            visitPropertyAccessorDescriptor(descriptor, builder, "setter");
        }

        private final void visitPropertyAccessorDescriptor(PropertyAccessorDescriptor propertyAccessorDescriptor, StringBuilder sb, String str) {
            int i = WhenMappings.$EnumSwitchMapping$0[DescriptorRendererImpl.this.getPropertyAccessorRenderingPolicy().ordinal()];
            if (i != 1) {
                if (i == 2) {
                    visitFunctionDescriptor2((FunctionDescriptor) propertyAccessorDescriptor, sb);
                    return;
                } else {
                    if (i != 3) {
                        throw new NoWhenBranchMatchedException();
                    }
                    return;
                }
            }
            DescriptorRendererImpl.this.renderAccessorModifiers(propertyAccessorDescriptor, sb);
            sb.append(str + " for ");
            DescriptorRendererImpl descriptorRendererImpl = DescriptorRendererImpl.this;
            PropertyDescriptor correspondingProperty = propertyAccessorDescriptor.getCorrespondingProperty();
            Intrinsics.checkNotNullExpressionValue(correspondingProperty, "getCorrespondingProperty(...)");
            descriptorRendererImpl.renderProperty(correspondingProperty, sb);
        }

        /* JADX INFO: renamed from: visitFunctionDescriptor, reason: avoid collision after fix types in other method */
        public void visitFunctionDescriptor2(FunctionDescriptor descriptor, StringBuilder builder) {
            Intrinsics.checkNotNullParameter(descriptor, "descriptor");
            Intrinsics.checkNotNullParameter(builder, "builder");
            DescriptorRendererImpl.this.renderFunction(descriptor, builder);
        }

        /* JADX INFO: renamed from: visitReceiverParameterDescriptor, reason: avoid collision after fix types in other method */
        public void visitReceiverParameterDescriptor2(ReceiverParameterDescriptor descriptor, StringBuilder builder) {
            Intrinsics.checkNotNullParameter(descriptor, "descriptor");
            Intrinsics.checkNotNullParameter(builder, "builder");
            builder.append(descriptor.getName());
        }

        /* JADX INFO: renamed from: visitConstructorDescriptor, reason: avoid collision after fix types in other method */
        public void visitConstructorDescriptor2(ConstructorDescriptor constructorDescriptor, StringBuilder builder) {
            Intrinsics.checkNotNullParameter(constructorDescriptor, "constructorDescriptor");
            Intrinsics.checkNotNullParameter(builder, "builder");
            DescriptorRendererImpl.this.renderConstructor(constructorDescriptor, builder);
        }

        /* JADX INFO: renamed from: visitTypeParameterDescriptor, reason: avoid collision after fix types in other method */
        public void visitTypeParameterDescriptor2(TypeParameterDescriptor descriptor, StringBuilder builder) {
            Intrinsics.checkNotNullParameter(descriptor, "descriptor");
            Intrinsics.checkNotNullParameter(builder, "builder");
            DescriptorRendererImpl.this.renderTypeParameter(descriptor, builder, true);
        }

        /* JADX INFO: renamed from: visitPackageFragmentDescriptor, reason: avoid collision after fix types in other method */
        public void visitPackageFragmentDescriptor2(PackageFragmentDescriptor descriptor, StringBuilder builder) {
            Intrinsics.checkNotNullParameter(descriptor, "descriptor");
            Intrinsics.checkNotNullParameter(builder, "builder");
            DescriptorRendererImpl.this.renderPackageFragment(descriptor, builder);
        }

        /* JADX INFO: renamed from: visitPackageViewDescriptor, reason: avoid collision after fix types in other method */
        public void visitPackageViewDescriptor2(PackageViewDescriptor descriptor, StringBuilder builder) {
            Intrinsics.checkNotNullParameter(descriptor, "descriptor");
            Intrinsics.checkNotNullParameter(builder, "builder");
            DescriptorRendererImpl.this.renderPackageView(descriptor, builder);
        }

        /* JADX INFO: renamed from: visitModuleDeclaration, reason: avoid collision after fix types in other method */
        public void visitModuleDeclaration2(ModuleDescriptor descriptor, StringBuilder builder) {
            Intrinsics.checkNotNullParameter(descriptor, "descriptor");
            Intrinsics.checkNotNullParameter(builder, "builder");
            DescriptorRendererImpl.this.renderName(descriptor, builder, true);
        }

        /* JADX INFO: renamed from: visitClassDescriptor, reason: avoid collision after fix types in other method */
        public void visitClassDescriptor2(ClassDescriptor descriptor, StringBuilder builder) {
            Intrinsics.checkNotNullParameter(descriptor, "descriptor");
            Intrinsics.checkNotNullParameter(builder, "builder");
            DescriptorRendererImpl.this.renderClass(descriptor, builder);
        }

        /* JADX INFO: renamed from: visitTypeAliasDescriptor, reason: avoid collision after fix types in other method */
        public void visitTypeAliasDescriptor2(TypeAliasDescriptor descriptor, StringBuilder builder) {
            Intrinsics.checkNotNullParameter(descriptor, "descriptor");
            Intrinsics.checkNotNullParameter(builder, "builder");
            DescriptorRendererImpl.this.renderTypeAlias(descriptor, builder);
        }
    }

    private final void renderSpaceIfNeeded(StringBuilder sb) {
        int length = sb.length();
        if (length == 0 || sb.charAt(length - 1) != ' ') {
            sb.append(' ');
        }
    }

    private final boolean overridesSomething(CallableMemberDescriptor callableMemberDescriptor) {
        return !callableMemberDescriptor.getOverriddenDescriptors().isEmpty();
    }

    private final void renderAbbreviatedTypeComment(StringBuilder sb, AbbreviatedType abbreviatedType) {
        if (getTextFormat() == RenderingFormat.HTML) {
            sb.append("<font color=\"808080\"><i>");
        }
        sb.append(" /* ");
        sb.append("from: ");
        renderNormalizedTypeAsIs(sb, abbreviatedType.getAbbreviation());
        sb.append(" */");
        if (getTextFormat() == RenderingFormat.HTML) {
            sb.append("</i></font>");
        }
    }

    private final void renderExpandedTypeComment(StringBuilder sb, AbbreviatedType abbreviatedType) {
        if (getTextFormat() == RenderingFormat.HTML) {
            sb.append("<font color=\"808080\"><i>");
        }
        sb.append(" /* ");
        sb.append("= ");
        renderNormalizedTypeAsIs(sb, abbreviatedType.getExpandedType());
        sb.append(" */");
        if (getTextFormat() == RenderingFormat.HTML) {
            sb.append("</i></font>");
        }
    }
}
