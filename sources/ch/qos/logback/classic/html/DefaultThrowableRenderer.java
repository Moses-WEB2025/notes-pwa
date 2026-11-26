package ch.qos.logback.classic.html;

import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.classic.spi.IThrowableProxy;
import ch.qos.logback.classic.spi.StackTraceElementProxy;
import ch.qos.logback.core.CoreConstants;
import ch.qos.logback.core.helpers.Transform;
import ch.qos.logback.core.html.IThrowableRenderer;

/* JADX INFO: loaded from: classes.dex */
public class DefaultThrowableRenderer implements IThrowableRenderer<ILoggingEvent> {
    static final String TRACE_PREFIX = "<br />&nbsp;&nbsp;&nbsp;&nbsp;";

    public void printFirstLine(StringBuilder sb, IThrowableProxy iThrowableProxy) {
        if (iThrowableProxy.getCommonFrames() > 0) {
            sb.append("<br />").append(CoreConstants.CAUSED_BY);
        }
        sb.append(iThrowableProxy.getClassName()).append(": ").append(Transform.escapeTags(iThrowableProxy.getMessage()));
        sb.append(CoreConstants.LINE_SEPARATOR);
    }

    @Override // ch.qos.logback.core.html.IThrowableRenderer
    public void render(StringBuilder sb, ILoggingEvent iLoggingEvent) {
        sb.append("<tr><td class=\"Exception\" colspan=\"6\">");
        for (IThrowableProxy throwableProxy = iLoggingEvent.getThrowableProxy(); throwableProxy != null; throwableProxy = throwableProxy.getCause()) {
            render(sb, throwableProxy);
        }
        sb.append("</td></tr>");
    }

    void render(StringBuilder sb, IThrowableProxy iThrowableProxy) {
        printFirstLine(sb, iThrowableProxy);
        int commonFrames = iThrowableProxy.getCommonFrames();
        StackTraceElementProxy[] stackTraceElementProxyArray = iThrowableProxy.getStackTraceElementProxyArray();
        for (int i = 0; i < stackTraceElementProxyArray.length - commonFrames; i++) {
            StackTraceElementProxy stackTraceElementProxy = stackTraceElementProxyArray[i];
            sb.append(TRACE_PREFIX);
            sb.append(Transform.escapeTags(stackTraceElementProxy.toString()));
            sb.append(CoreConstants.LINE_SEPARATOR);
        }
        if (commonFrames > 0) {
            sb.append(TRACE_PREFIX);
            sb.append("\t... ").append(commonFrames).append(" common frames omitted").append(CoreConstants.LINE_SEPARATOR);
        }
    }
}
