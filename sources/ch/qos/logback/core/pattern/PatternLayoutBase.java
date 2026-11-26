package ch.qos.logback.core.pattern;

import ch.qos.logback.core.Context;
import ch.qos.logback.core.CoreConstants;
import ch.qos.logback.core.LayoutBase;
import ch.qos.logback.core.pattern.parser.Parser;
import ch.qos.logback.core.spi.ScanException;
import ch.qos.logback.core.status.ErrorStatus;
import java.util.HashMap;
import java.util.Map;

/* JADX INFO: loaded from: classes.dex */
public abstract class PatternLayoutBase<E> extends LayoutBase<E> {
    static final int INTIAL_STRING_BUILDER_SIZE = 256;
    Converter<E> head;
    Map<String, String> instanceConverterMap = new HashMap();
    protected boolean outputPatternAsHeader = false;
    String pattern;
    protected PostCompileProcessor<E> postCompileProcessor;

    public abstract Map<String, String> getDefaultConverterMap();

    public Map<String, String> getEffectiveConverterMap() {
        Map map;
        HashMap map2 = new HashMap();
        Map<String, String> defaultConverterMap = getDefaultConverterMap();
        if (defaultConverterMap != null) {
            map2.putAll(defaultConverterMap);
        }
        Context context = getContext();
        if (context != null && (map = (Map) context.getObject(CoreConstants.PATTERN_RULE_REGISTRY)) != null) {
            map2.putAll(map);
        }
        map2.putAll(this.instanceConverterMap);
        return map2;
    }

    public Map<String, String> getInstanceConverterMap() {
        return this.instanceConverterMap;
    }

    public String getPattern() {
        return this.pattern;
    }

    @Override // ch.qos.logback.core.LayoutBase, ch.qos.logback.core.Layout
    public String getPresentationHeader() {
        return this.outputPatternAsHeader ? getPresentationHeaderPrefix() + this.pattern : super.getPresentationHeader();
    }

    protected String getPresentationHeaderPrefix() {
        return "";
    }

    public boolean isOutputPatternAsHeader() {
        return this.outputPatternAsHeader;
    }

    protected void setContextForConverters(Converter<E> converter) {
        ConverterUtil.setContextForConverters(getContext(), converter);
    }

    public void setOutputPatternAsHeader(boolean z) {
        this.outputPatternAsHeader = z;
    }

    public void setPattern(String str) {
        this.pattern = str;
    }

    public void setPostCompileProcessor(PostCompileProcessor<E> postCompileProcessor) {
        this.postCompileProcessor = postCompileProcessor;
    }

    @Override // ch.qos.logback.core.LayoutBase, ch.qos.logback.core.spi.LifeCycle
    public void start() {
        String str = this.pattern;
        if (str == null || str.length() == 0) {
            addError("Empty or null pattern.");
            return;
        }
        try {
            Parser parser = new Parser(this.pattern);
            if (getContext() != null) {
                parser.setContext(getContext());
            }
            this.head = parser.compile(parser.parse(), getEffectiveConverterMap());
            PostCompileProcessor<E> postCompileProcessor = this.postCompileProcessor;
            if (postCompileProcessor != null) {
                postCompileProcessor.process(this.context, this.head);
            }
            ConverterUtil.setContextForConverters(getContext(), this.head);
            ConverterUtil.startConverters(this.head);
            super.start();
        } catch (ScanException e) {
            getContext().getStatusManager().add(new ErrorStatus("Failed to parse pattern \"" + getPattern() + "\".", this, e));
        }
    }

    public String toString() {
        return getClass().getName() + "(\"" + getPattern() + "\")";
    }

    protected String writeLoopOnConverters(E e) {
        StringBuilder sb = new StringBuilder(256);
        for (Converter<E> next = this.head; next != null; next = next.getNext()) {
            next.write(sb, e);
        }
        return sb.toString();
    }
}
