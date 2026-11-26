package org.junit.runners;

import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.ClassRule;
import org.junit.internal.AssumptionViolatedException;
import org.junit.internal.runners.model.EachTestNotifier;
import org.junit.internal.runners.rules.RuleMemberValidator;
import org.junit.internal.runners.statements.RunAfters;
import org.junit.internal.runners.statements.RunBefores;
import org.junit.rules.RunRules;
import org.junit.rules.TestRule;
import org.junit.runner.Description;
import org.junit.runner.Runner;
import org.junit.runner.manipulation.Filter;
import org.junit.runner.manipulation.Filterable;
import org.junit.runner.manipulation.NoTestsRemainException;
import org.junit.runner.manipulation.Sortable;
import org.junit.runner.manipulation.Sorter;
import org.junit.runner.notification.RunNotifier;
import org.junit.runner.notification.StoppedByUserException;
import org.junit.runners.model.FrameworkMethod;
import org.junit.runners.model.InitializationError;
import org.junit.runners.model.RunnerScheduler;
import org.junit.runners.model.Statement;
import org.junit.runners.model.TestClass;
import org.junit.validator.AnnotationsValidator;
import org.junit.validator.PublicClassValidator;
import org.junit.validator.TestClassValidator;

/* JADX INFO: loaded from: classes6.dex */
public abstract class ParentRunner<T> extends Runner implements Filterable, Sortable {
    private static final List<TestClassValidator> VALIDATORS = Arrays.asList(new AnnotationsValidator(), new PublicClassValidator());
    private final Object childrenLock = new Object();
    private volatile Collection<T> filteredChildren = null;
    private volatile RunnerScheduler scheduler = new RunnerScheduler() { // from class: org.junit.runners.ParentRunner.1
        @Override // org.junit.runners.model.RunnerScheduler
        public void finished() {
        }

        @Override // org.junit.runners.model.RunnerScheduler
        public void schedule(Runnable runnable) {
            runnable.run();
        }
    };
    private final TestClass testClass;

    protected abstract Description describeChild(T t);

    protected abstract List<T> getChildren();

    protected boolean isIgnored(T t) {
        return false;
    }

    protected abstract void runChild(T t, RunNotifier runNotifier);

    protected ParentRunner(Class<?> cls) throws InitializationError {
        this.testClass = createTestClass(cls);
        validate();
    }

    protected TestClass createTestClass(Class<?> cls) {
        return new TestClass(cls);
    }

    protected void collectInitializationErrors(List<Throwable> list) {
        validatePublicVoidNoArgMethods(BeforeClass.class, true, list);
        validatePublicVoidNoArgMethods(AfterClass.class, true, list);
        validateClassRules(list);
        applyValidators(list);
    }

    private void applyValidators(List<Throwable> list) {
        if (getTestClass().getJavaClass() != null) {
            Iterator<TestClassValidator> it = VALIDATORS.iterator();
            while (it.hasNext()) {
                list.addAll(it.next().validateTestClass(getTestClass()));
            }
        }
    }

    protected void validatePublicVoidNoArgMethods(Class<? extends Annotation> cls, boolean z, List<Throwable> list) {
        Iterator<FrameworkMethod> it = getTestClass().getAnnotatedMethods(cls).iterator();
        while (it.hasNext()) {
            it.next().validatePublicVoidNoArg(z, list);
        }
    }

    private void validateClassRules(List<Throwable> list) {
        RuleMemberValidator.CLASS_RULE_VALIDATOR.validate(getTestClass(), list);
        RuleMemberValidator.CLASS_RULE_METHOD_VALIDATOR.validate(getTestClass(), list);
    }

    protected Statement classBlock(RunNotifier runNotifier) {
        Statement statementChildrenInvoker = childrenInvoker(runNotifier);
        return !areAllChildrenIgnored() ? withClassRules(withAfterClasses(withBeforeClasses(statementChildrenInvoker))) : statementChildrenInvoker;
    }

    private boolean areAllChildrenIgnored() {
        Iterator<T> it = getFilteredChildren().iterator();
        while (it.hasNext()) {
            if (!isIgnored(it.next())) {
                return false;
            }
        }
        return true;
    }

    protected Statement withBeforeClasses(Statement statement) {
        List<FrameworkMethod> annotatedMethods = this.testClass.getAnnotatedMethods(BeforeClass.class);
        return annotatedMethods.isEmpty() ? statement : new RunBefores(statement, annotatedMethods, null);
    }

    protected Statement withAfterClasses(Statement statement) {
        List<FrameworkMethod> annotatedMethods = this.testClass.getAnnotatedMethods(AfterClass.class);
        return annotatedMethods.isEmpty() ? statement : new RunAfters(statement, annotatedMethods, null);
    }

    private Statement withClassRules(Statement statement) {
        List<TestRule> listClassRules = classRules();
        return listClassRules.isEmpty() ? statement : new RunRules(statement, listClassRules, getDescription());
    }

    protected List<TestRule> classRules() {
        List<TestRule> annotatedMethodValues = this.testClass.getAnnotatedMethodValues(null, ClassRule.class, TestRule.class);
        annotatedMethodValues.addAll(this.testClass.getAnnotatedFieldValues(null, ClassRule.class, TestRule.class));
        return annotatedMethodValues;
    }

    protected Statement childrenInvoker(final RunNotifier runNotifier) {
        return new Statement() { // from class: org.junit.runners.ParentRunner.2
            @Override // org.junit.runners.model.Statement
            public void evaluate() {
                ParentRunner.this.runChildren(runNotifier);
            }
        };
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void runChildren(final RunNotifier runNotifier) {
        RunnerScheduler runnerScheduler = this.scheduler;
        try {
            for (final T t : getFilteredChildren()) {
                runnerScheduler.schedule(new Runnable() { // from class: org.junit.runners.ParentRunner.3
                    /* JADX WARN: Multi-variable type inference failed */
                    @Override // java.lang.Runnable
                    public void run() {
                        ParentRunner.this.runChild(t, runNotifier);
                    }
                });
            }
            runnerScheduler.finished();
        } catch (Throwable th) {
            runnerScheduler.finished();
            throw th;
        }
    }

    protected String getName() {
        return this.testClass.getName();
    }

    public final TestClass getTestClass() {
        return this.testClass;
    }

    protected final void runLeaf(Statement statement, Description description, RunNotifier runNotifier) {
        EachTestNotifier eachTestNotifier = new EachTestNotifier(runNotifier, description);
        eachTestNotifier.fireTestStarted();
        try {
            try {
                statement.evaluate();
                eachTestNotifier.fireTestFinished();
            } catch (AssumptionViolatedException e) {
                eachTestNotifier.addFailedAssumption(e);
                eachTestNotifier.fireTestFinished();
            } catch (Throwable th) {
                eachTestNotifier.addFailure(th);
                eachTestNotifier.fireTestFinished();
            }
        } catch (Throwable th2) {
            eachTestNotifier.fireTestFinished();
            throw th2;
        }
    }

    protected Annotation[] getRunnerAnnotations() {
        return this.testClass.getAnnotations();
    }

    @Override // org.junit.runner.Runner, org.junit.runner.Describable
    public Description getDescription() {
        Description descriptionCreateSuiteDescription = Description.createSuiteDescription(getName(), getRunnerAnnotations());
        Iterator<T> it = getFilteredChildren().iterator();
        while (it.hasNext()) {
            descriptionCreateSuiteDescription.addChild(describeChild(it.next()));
        }
        return descriptionCreateSuiteDescription;
    }

    @Override // org.junit.runner.Runner
    public void run(RunNotifier runNotifier) {
        EachTestNotifier eachTestNotifier = new EachTestNotifier(runNotifier, getDescription());
        try {
            classBlock(runNotifier).evaluate();
        } catch (AssumptionViolatedException e) {
            eachTestNotifier.addFailedAssumption(e);
        } catch (StoppedByUserException e2) {
            throw e2;
        } catch (Throwable th) {
            eachTestNotifier.addFailure(th);
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // org.junit.runner.manipulation.Filterable
    public void filter(Filter filter) throws NoTestsRemainException {
        synchronized (this.childrenLock) {
            ArrayList arrayList = new ArrayList(getFilteredChildren());
            Iterator it = arrayList.iterator();
            while (it.hasNext()) {
                Object next = it.next();
                if (shouldRun(filter, next)) {
                    try {
                        filter.apply(next);
                    } catch (NoTestsRemainException unused) {
                        it.remove();
                    }
                } else {
                    it.remove();
                }
            }
            this.filteredChildren = Collections.unmodifiableCollection(arrayList);
            if (this.filteredChildren.isEmpty()) {
                throw new NoTestsRemainException();
            }
        }
    }

    @Override // org.junit.runner.manipulation.Sortable
    public void sort(Sorter sorter) {
        synchronized (this.childrenLock) {
            Iterator<T> it = getFilteredChildren().iterator();
            while (it.hasNext()) {
                sorter.apply(it.next());
            }
            ArrayList arrayList = new ArrayList(getFilteredChildren());
            Collections.sort(arrayList, comparator(sorter));
            this.filteredChildren = Collections.unmodifiableCollection(arrayList);
        }
    }

    private void validate() throws InitializationError {
        ArrayList arrayList = new ArrayList();
        collectInitializationErrors(arrayList);
        if (!arrayList.isEmpty()) {
            throw new InitializationError(arrayList);
        }
    }

    private Collection<T> getFilteredChildren() {
        if (this.filteredChildren == null) {
            synchronized (this.childrenLock) {
                if (this.filteredChildren == null) {
                    this.filteredChildren = Collections.unmodifiableCollection(getChildren());
                }
            }
        }
        return this.filteredChildren;
    }

    private boolean shouldRun(Filter filter, T t) {
        return filter.shouldRun(describeChild(t));
    }

    private Comparator<? super T> comparator(final Sorter sorter) {
        return new Comparator<T>() { // from class: org.junit.runners.ParentRunner.4
            @Override // java.util.Comparator
            public int compare(T t, T t2) {
                return sorter.compare(ParentRunner.this.describeChild(t), ParentRunner.this.describeChild(t2));
            }
        };
    }

    public void setScheduler(RunnerScheduler runnerScheduler) {
        this.scheduler = runnerScheduler;
    }
}
