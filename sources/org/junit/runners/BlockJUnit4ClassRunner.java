package org.junit.runners;

import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;
import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Rule;
import org.junit.Test;
import org.junit.internal.runners.model.ReflectiveCallable;
import org.junit.internal.runners.rules.RuleMemberValidator;
import org.junit.internal.runners.statements.ExpectException;
import org.junit.internal.runners.statements.Fail;
import org.junit.internal.runners.statements.FailOnTimeout;
import org.junit.internal.runners.statements.InvokeMethod;
import org.junit.internal.runners.statements.RunAfters;
import org.junit.internal.runners.statements.RunBefores;
import org.junit.rules.MethodRule;
import org.junit.rules.RunRules;
import org.junit.rules.TestRule;
import org.junit.runner.Description;
import org.junit.runner.notification.RunNotifier;
import org.junit.runners.model.FrameworkMethod;
import org.junit.runners.model.InitializationError;
import org.junit.runners.model.Statement;

/* JADX INFO: loaded from: classes6.dex */
public class BlockJUnit4ClassRunner extends ParentRunner<FrameworkMethod> {
    private final ConcurrentHashMap<FrameworkMethod, Description> methodDescriptions;

    public BlockJUnit4ClassRunner(Class<?> cls) throws InitializationError {
        super(cls);
        this.methodDescriptions = new ConcurrentHashMap<>();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // org.junit.runners.ParentRunner
    public void runChild(FrameworkMethod frameworkMethod, RunNotifier runNotifier) {
        Description descriptionDescribeChild = describeChild(frameworkMethod);
        if (isIgnored(frameworkMethod)) {
            runNotifier.fireTestIgnored(descriptionDescribeChild);
        } else {
            runLeaf(methodBlock(frameworkMethod), descriptionDescribeChild, runNotifier);
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // org.junit.runners.ParentRunner
    public boolean isIgnored(FrameworkMethod frameworkMethod) {
        return frameworkMethod.getAnnotation(Ignore.class) != null;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // org.junit.runners.ParentRunner
    public Description describeChild(FrameworkMethod frameworkMethod) {
        Description description = this.methodDescriptions.get(frameworkMethod);
        if (description != null) {
            return description;
        }
        Description descriptionCreateTestDescription = Description.createTestDescription(getTestClass().getJavaClass(), testName(frameworkMethod), frameworkMethod.getAnnotations());
        this.methodDescriptions.putIfAbsent(frameworkMethod, descriptionCreateTestDescription);
        return descriptionCreateTestDescription;
    }

    @Override // org.junit.runners.ParentRunner
    protected List<FrameworkMethod> getChildren() {
        return computeTestMethods();
    }

    protected List<FrameworkMethod> computeTestMethods() {
        return getTestClass().getAnnotatedMethods(Test.class);
    }

    @Override // org.junit.runners.ParentRunner
    protected void collectInitializationErrors(List<Throwable> list) {
        super.collectInitializationErrors(list);
        validateNoNonStaticInnerClass(list);
        validateConstructor(list);
        validateInstanceMethods(list);
        validateFields(list);
        validateMethods(list);
    }

    protected void validateNoNonStaticInnerClass(List<Throwable> list) {
        if (getTestClass().isANonStaticInnerClass()) {
            list.add(new Exception("The inner class " + getTestClass().getName() + " is not static."));
        }
    }

    protected void validateConstructor(List<Throwable> list) {
        validateOnlyOneConstructor(list);
        validateZeroArgConstructor(list);
    }

    protected void validateOnlyOneConstructor(List<Throwable> list) {
        if (hasOneConstructor()) {
            return;
        }
        list.add(new Exception("Test class should have exactly one public constructor"));
    }

    protected void validateZeroArgConstructor(List<Throwable> list) {
        if (getTestClass().isANonStaticInnerClass() || !hasOneConstructor() || getTestClass().getOnlyConstructor().getParameterTypes().length == 0) {
            return;
        }
        list.add(new Exception("Test class should have exactly one public zero-argument constructor"));
    }

    private boolean hasOneConstructor() {
        return getTestClass().getJavaClass().getConstructors().length == 1;
    }

    @Deprecated
    protected void validateInstanceMethods(List<Throwable> list) {
        validatePublicVoidNoArgMethods(After.class, false, list);
        validatePublicVoidNoArgMethods(Before.class, false, list);
        validateTestMethods(list);
        if (computeTestMethods().size() == 0) {
            list.add(new Exception("No runnable methods"));
        }
    }

    protected void validateFields(List<Throwable> list) {
        RuleMemberValidator.RULE_VALIDATOR.validate(getTestClass(), list);
    }

    private void validateMethods(List<Throwable> list) {
        RuleMemberValidator.RULE_METHOD_VALIDATOR.validate(getTestClass(), list);
    }

    protected void validateTestMethods(List<Throwable> list) {
        validatePublicVoidNoArgMethods(Test.class, false, list);
    }

    protected Object createTest() throws Exception {
        return getTestClass().getOnlyConstructor().newInstance(new Object[0]);
    }

    protected String testName(FrameworkMethod frameworkMethod) {
        return frameworkMethod.getName();
    }

    protected Statement methodBlock(FrameworkMethod frameworkMethod) {
        try {
            Object objRun = new ReflectiveCallable() { // from class: org.junit.runners.BlockJUnit4ClassRunner.1
                @Override // org.junit.internal.runners.model.ReflectiveCallable
                protected Object runReflectiveCall() throws Throwable {
                    return BlockJUnit4ClassRunner.this.createTest();
                }
            }.run();
            return withRules(frameworkMethod, objRun, withAfters(frameworkMethod, objRun, withBefores(frameworkMethod, objRun, withPotentialTimeout(frameworkMethod, objRun, possiblyExpectingExceptions(frameworkMethod, objRun, methodInvoker(frameworkMethod, objRun))))));
        } catch (Throwable th) {
            return new Fail(th);
        }
    }

    protected Statement methodInvoker(FrameworkMethod frameworkMethod, Object obj) {
        return new InvokeMethod(frameworkMethod, obj);
    }

    protected Statement possiblyExpectingExceptions(FrameworkMethod frameworkMethod, Object obj, Statement statement) {
        Test test = (Test) frameworkMethod.getAnnotation(Test.class);
        return expectsException(test) ? new ExpectException(statement, getExpectedException(test)) : statement;
    }

    @Deprecated
    protected Statement withPotentialTimeout(FrameworkMethod frameworkMethod, Object obj, Statement statement) {
        long timeout = getTimeout((Test) frameworkMethod.getAnnotation(Test.class));
        return timeout <= 0 ? statement : FailOnTimeout.builder().withTimeout(timeout, TimeUnit.MILLISECONDS).build(statement);
    }

    protected Statement withBefores(FrameworkMethod frameworkMethod, Object obj, Statement statement) {
        List<FrameworkMethod> annotatedMethods = getTestClass().getAnnotatedMethods(Before.class);
        return annotatedMethods.isEmpty() ? statement : new RunBefores(statement, annotatedMethods, obj);
    }

    protected Statement withAfters(FrameworkMethod frameworkMethod, Object obj, Statement statement) {
        List<FrameworkMethod> annotatedMethods = getTestClass().getAnnotatedMethods(After.class);
        return annotatedMethods.isEmpty() ? statement : new RunAfters(statement, annotatedMethods, obj);
    }

    private Statement withRules(FrameworkMethod frameworkMethod, Object obj, Statement statement) {
        List<TestRule> testRules = getTestRules(obj);
        return withTestRules(frameworkMethod, testRules, withMethodRules(frameworkMethod, testRules, obj, statement));
    }

    private Statement withMethodRules(FrameworkMethod frameworkMethod, List<TestRule> list, Object obj, Statement statement) {
        for (MethodRule methodRule : getMethodRules(obj)) {
            if (!list.contains(methodRule)) {
                statement = methodRule.apply(statement, frameworkMethod, obj);
            }
        }
        return statement;
    }

    private List<MethodRule> getMethodRules(Object obj) {
        return rules(obj);
    }

    protected List<MethodRule> rules(Object obj) {
        List<MethodRule> annotatedMethodValues = getTestClass().getAnnotatedMethodValues(obj, Rule.class, MethodRule.class);
        annotatedMethodValues.addAll(getTestClass().getAnnotatedFieldValues(obj, Rule.class, MethodRule.class));
        return annotatedMethodValues;
    }

    private Statement withTestRules(FrameworkMethod frameworkMethod, List<TestRule> list, Statement statement) {
        return list.isEmpty() ? statement : new RunRules(statement, list, describeChild(frameworkMethod));
    }

    protected List<TestRule> getTestRules(Object obj) {
        List<TestRule> annotatedMethodValues = getTestClass().getAnnotatedMethodValues(obj, Rule.class, TestRule.class);
        annotatedMethodValues.addAll(getTestClass().getAnnotatedFieldValues(obj, Rule.class, TestRule.class));
        return annotatedMethodValues;
    }

    private Class<? extends Throwable> getExpectedException(Test test) {
        if (test == null || test.expected() == Test.None.class) {
            return null;
        }
        return test.expected();
    }

    private boolean expectsException(Test test) {
        return getExpectedException(test) != null;
    }

    private long getTimeout(Test test) {
        if (test == null) {
            return 0L;
        }
        return test.timeout();
    }
}
