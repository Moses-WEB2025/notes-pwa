package ch.qos.logback.classic.html;

import ch.qos.logback.core.CoreConstants;
import ch.qos.logback.core.html.CssBuilder;

/* JADX INFO: loaded from: classes.dex */
public class DefaultCssBuilder implements CssBuilder {
    @Override // ch.qos.logback.core.html.CssBuilder
    public void addCss(StringBuilder sb) {
        sb.append("<style  type=\"text/css\">");
        sb.append(CoreConstants.LINE_SEPARATOR);
        sb.append("table { margin-left: 2em; margin-right: 2em; border-left: 2px solid #AAA; }");
        sb.append(CoreConstants.LINE_SEPARATOR);
        sb.append("TR.even { background: #FFFFFF; }");
        sb.append(CoreConstants.LINE_SEPARATOR);
        sb.append("TR.odd { background: #EAEAEA; }");
        sb.append(CoreConstants.LINE_SEPARATOR);
        sb.append("TR.warn TD.Level, TR.error TD.Level, TR.fatal TD.Level {font-weight: bold; color: #FF4040 }");
        sb.append(CoreConstants.LINE_SEPARATOR);
        sb.append("TD { padding-right: 1ex; padding-left: 1ex; border-right: 2px solid #AAA; }");
        sb.append(CoreConstants.LINE_SEPARATOR);
        sb.append("TD.Time, TD.Date { text-align: right; font-family: courier, monospace; font-size: smaller; }");
        sb.append(CoreConstants.LINE_SEPARATOR);
        sb.append("TD.Thread { text-align: left; }");
        sb.append(CoreConstants.LINE_SEPARATOR);
        sb.append("TD.Level { text-align: right; }");
        sb.append(CoreConstants.LINE_SEPARATOR);
        sb.append("TD.Logger { text-align: left; }");
        sb.append(CoreConstants.LINE_SEPARATOR);
        sb.append("TR.header { background: #596ED5; color: #FFF; font-weight: bold; font-size: larger; }");
        sb.append(CoreConstants.LINE_SEPARATOR);
        sb.append("TD.Exception { background: #A2AEE8; font-family: courier, monospace;}");
        sb.append(CoreConstants.LINE_SEPARATOR);
        sb.append("</style>");
        sb.append(CoreConstants.LINE_SEPARATOR);
    }
}
