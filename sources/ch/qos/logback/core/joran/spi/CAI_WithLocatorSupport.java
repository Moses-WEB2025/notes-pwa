package ch.qos.logback.core.joran.spi;

import ch.qos.logback.core.Context;
import ch.qos.logback.core.spi.ContextAwareImpl;
import org.xml.sax.Locator;

/* JADX INFO: loaded from: classes.dex */
class CAI_WithLocatorSupport extends ContextAwareImpl {
    CAI_WithLocatorSupport(Context context, Interpreter interpreter) {
        super(context, interpreter);
    }

    @Override // ch.qos.logback.core.spi.ContextAwareImpl
    protected Object getOrigin() {
        Locator locator = ((Interpreter) super.getOrigin()).locator;
        return (locator != null ? new StringBuilder().append(Interpreter.class.getName()).append("@").append(locator.getLineNumber()).append(":").append(locator.getColumnNumber()) : new StringBuilder().append(Interpreter.class.getName()).append("@NA:NA")).toString();
    }
}
