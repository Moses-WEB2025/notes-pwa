package androidx.appsearch.annotation;

import androidx.appsearch.app.LongSerializer;
import androidx.appsearch.app.StringSerializer;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/* JADX INFO: loaded from: classes.dex */
@Target({ElementType.TYPE})
@Documented
@Retention(RetentionPolicy.CLASS)
public @interface Document {

    @Target({ElementType.FIELD, ElementType.METHOD})
    @Documented
    @Retention(RetentionPolicy.CLASS)
    public @interface BlobHandleProperty {
        String name() default "";

        boolean required() default false;
    }

    @Target({ElementType.FIELD, ElementType.METHOD})
    @Documented
    @Retention(RetentionPolicy.CLASS)
    public @interface BooleanProperty {
        String name() default "";

        boolean required() default false;
    }

    @Target({ElementType.METHOD, ElementType.TYPE})
    @Documented
    @Retention(RetentionPolicy.CLASS)
    public @interface BuilderProducer {
    }

    @Target({ElementType.FIELD, ElementType.METHOD})
    @Documented
    @Retention(RetentionPolicy.CLASS)
    public @interface BytesProperty {
        String name() default "";

        boolean required() default false;
    }

    @Target({ElementType.FIELD, ElementType.METHOD})
    @Documented
    @Retention(RetentionPolicy.CLASS)
    public @interface CreationTimestampMillis {
    }

    @Target({ElementType.FIELD, ElementType.METHOD})
    @Documented
    @Retention(RetentionPolicy.CLASS)
    public @interface DocumentProperty {
        boolean indexNestedProperties() default false;

        String[] indexableNestedPropertiesList() default {};

        boolean inheritIndexableNestedPropertiesFromSuperclass() default false;

        String name() default "";

        boolean required() default false;
    }

    @Target({ElementType.FIELD, ElementType.METHOD})
    @Documented
    @Retention(RetentionPolicy.CLASS)
    public @interface DoubleProperty {
        String name() default "";

        boolean required() default false;
    }

    @Target({ElementType.FIELD, ElementType.METHOD})
    @Documented
    @Retention(RetentionPolicy.CLASS)
    public @interface EmbeddingProperty {
        int indexingType() default 0;

        String name() default "";

        int quantizationType() default 0;

        boolean required() default false;
    }

    @Target({ElementType.FIELD, ElementType.METHOD})
    @Documented
    @Retention(RetentionPolicy.CLASS)
    public @interface Id {
    }

    @Target({ElementType.FIELD, ElementType.METHOD})
    @Documented
    @Retention(RetentionPolicy.CLASS)
    public @interface Namespace {
    }

    @Target({ElementType.FIELD, ElementType.METHOD})
    @Documented
    @Retention(RetentionPolicy.CLASS)
    public @interface Score {
    }

    @Target({ElementType.FIELD, ElementType.METHOD})
    @Documented
    @Retention(RetentionPolicy.CLASS)
    public @interface StringProperty {

        public static final class DefaultSerializer implements StringSerializer<String> {
            @Override // androidx.appsearch.app.StringSerializer
            public String deserialize(String str) {
                return str;
            }

            @Override // androidx.appsearch.app.StringSerializer
            public String serialize(String str) {
                return str;
            }
        }

        int indexingType() default 0;

        int joinableValueType() default 0;

        String name() default "";

        boolean required() default false;

        Class<? extends StringSerializer<?>> serializer() default DefaultSerializer.class;

        int tokenizerType() default 1;
    }

    @Target({ElementType.FIELD, ElementType.METHOD})
    @Documented
    @Retention(RetentionPolicy.CLASS)
    public @interface TtlMillis {
    }

    String name() default "";

    Class<?>[] parent() default {};

    @Target({ElementType.FIELD, ElementType.METHOD})
    @Documented
    @Retention(RetentionPolicy.CLASS)
    public @interface LongProperty {
        int indexingType() default 0;

        String name() default "";

        boolean required() default false;

        Class<? extends LongSerializer<?>> serializer() default DefaultSerializer.class;

        public static final class DefaultSerializer implements LongSerializer<Long> {
            @Override // androidx.appsearch.app.LongSerializer
            public long serialize(Long l) {
                return l.longValue();
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // androidx.appsearch.app.LongSerializer
            public Long deserialize(long j) {
                return Long.valueOf(j);
            }
        }
    }
}
