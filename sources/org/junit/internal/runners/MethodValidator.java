package org.junit.internal.runners;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.List;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/* JADX INFO: loaded from: classes6.dex */
@Deprecated
public class MethodValidator {
    private final List<Throwable> errors = new ArrayList();
    private TestClass testClass;

    public MethodValidator(TestClass testClass) {
        this.testClass = testClass;
    }

    public void validateInstanceMethods() {
        validateTestMethods(After.class, false);
        validateTestMethods(Before.class, false);
        validateTestMethods(Test.class, false);
        if (this.testClass.getAnnotatedMethods(Test.class).size() == 0) {
            this.errors.add(new Exception("No runnable methods"));
        }
    }

    public void validateStaticMethods() {
        validateTestMethods(BeforeClass.class, true);
        validateTestMethods(AfterClass.class, true);
    }

    public List<Throwable> validateMethodsForDefaultRunner() {
        validateNoArgConstructor();
        validateStaticMethods();
        validateInstanceMethods();
        return this.errors;
    }

    public void assertValid() throws InitializationError {
        if (!this.errors.isEmpty()) {
            throw new InitializationError(this.errors);
        }
    }

    public void validateNoArgConstructor() {
        try {
            this.testClass.getConstructor();
        } catch (Exception e) {
            this.errors.add(new Exception("Test class should have public zero-argument constructor", e));
        }
    }

    private void validateTestMethods(Class<? extends Annotation> cls, boolean z) {
        for (Method method : this.testClass.getAnnotatedMethods(cls)) {
            if (Modifier.isStatic(method.getModifiers()) != z) {
                this.errors.add(new Exception("Method " + method.getName() + "() " + (z ? "should" : "should not") + " be static"));
            }
            if (!Modifier.isPublic(method.getDeclaringClass().getModifiers())) {
                this.errors.add(new Exception("Class " + method.getDeclaringClass().getName() + " should be public"));
            }
            if (!Modifier.isPublic(method.getModifiers())) {
                this.errors.add(new Exception("Method " + method.getName() + " should be public"));
            }
            if (method.getReturnType() != Void.TYPE) {
                this.errors.add(new Exception("Method " + method.getName() + " should be void"));
            }
            if (method.getParameterTypes().length != 0) {
                this.errors.add(new Exception("Method " + method.getName() + " should have no parameters"));
            }
        }
    }
}
