package org.junit.experimental.categories;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import org.junit.runner.Description;
import org.junit.runner.manipulation.Filter;
import org.junit.runner.manipulation.NoTestsRemainException;
import org.junit.runners.Suite;
import org.junit.runners.model.InitializationError;
import org.junit.runners.model.RunnerBuilder;

/* JADX INFO: loaded from: classes6.dex */
public class Categories extends Suite {

    @Retention(RetentionPolicy.RUNTIME)
    public @interface ExcludeCategory {
        boolean matchAny() default true;

        Class<?>[] value() default {};
    }

    @Retention(RetentionPolicy.RUNTIME)
    public @interface IncludeCategory {
        boolean matchAny() default true;

        Class<?>[] value() default {};
    }

    public static class CategoryFilter extends Filter {
        private final Set<Class<?>> excluded;
        private final boolean excludedAny;
        private final Set<Class<?>> included;
        private final boolean includedAny;

        public static CategoryFilter include(boolean z, Class<?>... clsArr) {
            if (!hasNull(clsArr)) {
                return categoryFilter(z, Categories.createSet(clsArr), true, null);
            }
            throw new NullPointerException("has null category");
        }

        public static CategoryFilter include(Class<?> cls) {
            return include(true, cls);
        }

        public static CategoryFilter include(Class<?>... clsArr) {
            return include(true, clsArr);
        }

        public static CategoryFilter exclude(boolean z, Class<?>... clsArr) {
            if (!hasNull(clsArr)) {
                return categoryFilter(true, null, z, Categories.createSet(clsArr));
            }
            throw new NullPointerException("has null category");
        }

        public static CategoryFilter exclude(Class<?> cls) {
            return exclude(true, cls);
        }

        public static CategoryFilter exclude(Class<?>... clsArr) {
            return exclude(true, clsArr);
        }

        public static CategoryFilter categoryFilter(boolean z, Set<Class<?>> set, boolean z2, Set<Class<?>> set2) {
            return new CategoryFilter(z, set, z2, set2);
        }

        protected CategoryFilter(boolean z, Set<Class<?>> set, boolean z2, Set<Class<?>> set2) {
            this.includedAny = z;
            this.excludedAny = z2;
            this.included = copyAndRefine(set);
            this.excluded = copyAndRefine(set2);
        }

        @Override // org.junit.runner.manipulation.Filter
        public String describe() {
            return toString();
        }

        public String toString() {
            StringBuilder sbAppend = new StringBuilder("categories ").append(this.included.isEmpty() ? "[all]" : this.included);
            if (!this.excluded.isEmpty()) {
                sbAppend.append(" - ").append(this.excluded);
            }
            return sbAppend.toString();
        }

        @Override // org.junit.runner.manipulation.Filter
        public boolean shouldRun(Description description) {
            if (hasCorrectCategoryAnnotation(description)) {
                return true;
            }
            Iterator<Description> it = description.getChildren().iterator();
            while (it.hasNext()) {
                if (shouldRun(it.next())) {
                    return true;
                }
            }
            return false;
        }

        private boolean hasCorrectCategoryAnnotation(Description description) {
            Set<Class<?>> setCategories = categories(description);
            if (setCategories.isEmpty()) {
                return this.included.isEmpty();
            }
            if (!this.excluded.isEmpty()) {
                if (this.excludedAny) {
                    if (matchesAnyParentCategories(setCategories, this.excluded)) {
                        return false;
                    }
                } else if (matchesAllParentCategories(setCategories, this.excluded)) {
                    return false;
                }
            }
            if (this.included.isEmpty()) {
                return true;
            }
            if (this.includedAny) {
                return matchesAnyParentCategories(setCategories, this.included);
            }
            return matchesAllParentCategories(setCategories, this.included);
        }

        private boolean matchesAnyParentCategories(Set<Class<?>> set, Set<Class<?>> set2) {
            Iterator<Class<?>> it = set2.iterator();
            while (it.hasNext()) {
                if (Categories.hasAssignableTo(set, it.next())) {
                    return true;
                }
            }
            return false;
        }

        private boolean matchesAllParentCategories(Set<Class<?>> set, Set<Class<?>> set2) {
            Iterator<Class<?>> it = set2.iterator();
            while (it.hasNext()) {
                if (!Categories.hasAssignableTo(set, it.next())) {
                    return false;
                }
            }
            return true;
        }

        private static Set<Class<?>> categories(Description description) {
            HashSet hashSet = new HashSet();
            Collections.addAll(hashSet, directCategories(description));
            Collections.addAll(hashSet, directCategories(parentDescription(description)));
            return hashSet;
        }

        private static Description parentDescription(Description description) {
            Class<?> testClass = description.getTestClass();
            if (testClass == null) {
                return null;
            }
            return Description.createSuiteDescription(testClass);
        }

        private static Class<?>[] directCategories(Description description) {
            if (description == null) {
                return new Class[0];
            }
            Category category = (Category) description.getAnnotation(Category.class);
            return category == null ? new Class[0] : category.value();
        }

        private static Set<Class<?>> copyAndRefine(Set<Class<?>> set) {
            HashSet hashSet = new HashSet();
            if (set != null) {
                hashSet.addAll(set);
            }
            hashSet.remove(null);
            return hashSet;
        }

        private static boolean hasNull(Class<?>... clsArr) {
            if (clsArr == null) {
                return false;
            }
            for (Class<?> cls : clsArr) {
                if (cls == null) {
                    return true;
                }
            }
            return false;
        }
    }

    public Categories(Class<?> cls, RunnerBuilder runnerBuilder) throws InitializationError {
        super(cls, runnerBuilder);
        try {
            filter(CategoryFilter.categoryFilter(isAnyIncluded(cls), getIncludedCategory(cls), isAnyExcluded(cls), getExcludedCategory(cls)));
            assertNoCategorizedDescendentsOfUncategorizeableParents(getDescription());
        } catch (NoTestsRemainException e) {
            throw new InitializationError(e);
        }
    }

    private static Set<Class<?>> getIncludedCategory(Class<?> cls) {
        IncludeCategory includeCategory = (IncludeCategory) cls.getAnnotation(IncludeCategory.class);
        return createSet(includeCategory == null ? null : includeCategory.value());
    }

    private static boolean isAnyIncluded(Class<?> cls) {
        IncludeCategory includeCategory = (IncludeCategory) cls.getAnnotation(IncludeCategory.class);
        return includeCategory == null || includeCategory.matchAny();
    }

    private static Set<Class<?>> getExcludedCategory(Class<?> cls) {
        ExcludeCategory excludeCategory = (ExcludeCategory) cls.getAnnotation(ExcludeCategory.class);
        return createSet(excludeCategory == null ? null : excludeCategory.value());
    }

    private static boolean isAnyExcluded(Class<?> cls) {
        ExcludeCategory excludeCategory = (ExcludeCategory) cls.getAnnotation(ExcludeCategory.class);
        return excludeCategory == null || excludeCategory.matchAny();
    }

    private static void assertNoCategorizedDescendentsOfUncategorizeableParents(Description description) throws InitializationError {
        if (!canHaveCategorizedChildren(description)) {
            assertNoDescendantsHaveCategoryAnnotations(description);
        }
        Iterator<Description> it = description.getChildren().iterator();
        while (it.hasNext()) {
            assertNoCategorizedDescendentsOfUncategorizeableParents(it.next());
        }
    }

    private static void assertNoDescendantsHaveCategoryAnnotations(Description description) throws InitializationError {
        for (Description description2 : description.getChildren()) {
            if (description2.getAnnotation(Category.class) != null) {
                throw new InitializationError("Category annotations on Parameterized classes are not supported on individual methods.");
            }
            assertNoDescendantsHaveCategoryAnnotations(description2);
        }
    }

    private static boolean canHaveCategorizedChildren(Description description) {
        Iterator<Description> it = description.getChildren().iterator();
        while (it.hasNext()) {
            if (it.next().getTestClass() == null) {
                return false;
            }
        }
        return true;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static boolean hasAssignableTo(Set<Class<?>> set, Class<?> cls) {
        Iterator<Class<?>> it = set.iterator();
        while (it.hasNext()) {
            if (cls.isAssignableFrom(it.next())) {
                return true;
            }
        }
        return false;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static Set<Class<?>> createSet(Class<?>... clsArr) {
        HashSet hashSet = new HashSet();
        if (clsArr != null) {
            Collections.addAll(hashSet, clsArr);
        }
        return hashSet;
    }
}
