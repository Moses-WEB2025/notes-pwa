package org.junit.validator;

import java.util.concurrent.ConcurrentHashMap;

/* JADX INFO: loaded from: classes6.dex */
public class AnnotationValidatorFactory {
    private static final ConcurrentHashMap<ValidateWith, AnnotationValidator> VALIDATORS_FOR_ANNOTATION_TYPES = new ConcurrentHashMap<>();

    public AnnotationValidator createAnnotationValidator(ValidateWith validateWith) {
        ConcurrentHashMap<ValidateWith, AnnotationValidator> concurrentHashMap = VALIDATORS_FOR_ANNOTATION_TYPES;
        AnnotationValidator annotationValidator = concurrentHashMap.get(validateWith);
        if (annotationValidator != null) {
            return annotationValidator;
        }
        Class<? extends AnnotationValidator> clsValue = validateWith.value();
        if (clsValue == null) {
            throw new IllegalArgumentException("Can't create validator, value is null in annotation " + validateWith.getClass().getName());
        }
        try {
            concurrentHashMap.putIfAbsent(validateWith, clsValue.newInstance());
            return concurrentHashMap.get(validateWith);
        } catch (Exception e) {
            throw new RuntimeException("Exception received when creating AnnotationValidator class " + clsValue.getName(), e);
        }
    }
}
