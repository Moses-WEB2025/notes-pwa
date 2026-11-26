package ch.qos.logback.classic.joran.action;

import ch.qos.logback.classic.LoggerContext;
import ch.qos.logback.classic.joran.ReconfigureOnChangeTask;
import ch.qos.logback.core.CoreConstants;
import ch.qos.logback.core.joran.action.Action;
import ch.qos.logback.core.joran.spi.InterpretationContext;
import ch.qos.logback.core.joran.util.ConfigurationWatchListUtil;
import ch.qos.logback.core.status.OnConsoleStatusListener;
import ch.qos.logback.core.util.ContextUtil;
import ch.qos.logback.core.util.Duration;
import ch.qos.logback.core.util.OptionHelper;
import ch.qos.logback.core.util.StatusListenerConfigHelper;
import java.net.URL;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import org.xml.sax.Attributes;

/* JADX INFO: loaded from: classes.dex */
public class ConfigurationAction extends Action {
    static final String DEBUG_SYSTEM_PROPERTY_KEY = "logback.debug";
    static final String INTERNAL_DEBUG_ATTR = "debug";
    static final String PACKAGING_DATA_ATTR = "packagingData";
    static final String SCAN_ATTR = "scan";
    static final String SCAN_PERIOD_ATTR = "scanPeriod";
    static final Duration SCAN_PERIOD_DEFAULT = Duration.buildByMinutes(1.0d);

    private Duration getDurationOfScanPeriodAttribute(String str, Duration duration) {
        Duration durationValueOf;
        Duration duration2 = null;
        Throwable th = null;
        if (!OptionHelper.isEmpty(str)) {
            try {
                durationValueOf = Duration.valueOf(str);
            } catch (IllegalArgumentException | IllegalStateException e) {
                th = e;
                durationValueOf = null;
            }
            if (th != null) {
                addWarn("Failed to parse 'scanPeriod' attribute [" + str + "]", th);
            }
            duration2 = durationValueOf;
        }
        if (duration2 != null) {
            return duration2;
        }
        addInfo("No 'scanPeriod' specified. Defaulting to " + duration.toString());
        return duration;
    }

    @Override // ch.qos.logback.core.joran.action.Action
    public void begin(InterpretationContext interpretationContext, String str, Attributes attributes) {
        String systemProperty = OptionHelper.getSystemProperty(DEBUG_SYSTEM_PROPERTY_KEY);
        if (systemProperty == null) {
            systemProperty = interpretationContext.subst(attributes.getValue("debug"));
        }
        if (OptionHelper.isEmpty(systemProperty) || systemProperty.equalsIgnoreCase("false") || systemProperty.equalsIgnoreCase("null")) {
            addInfo("debug attribute not set");
        } else {
            StatusListenerConfigHelper.addOnConsoleListenerInstance(this.context, new OnConsoleStatusListener());
        }
        processScanAttrib(interpretationContext, attributes);
        new ContextUtil(this.context).addHostNameAsProperty();
        interpretationContext.pushObject(getContext());
        ((LoggerContext) this.context).setPackagingDataEnabled(OptionHelper.toBoolean(interpretationContext.subst(attributes.getValue(PACKAGING_DATA_ATTR)), false));
    }

    @Override // ch.qos.logback.core.joran.action.Action
    public void end(InterpretationContext interpretationContext, String str) {
        addInfo("End of configuration.");
        interpretationContext.popObject();
    }

    String getSystemProperty(String str) {
        try {
            return System.getProperty(str);
        } catch (SecurityException unused) {
            return null;
        }
    }

    void processScanAttrib(InterpretationContext interpretationContext, Attributes attributes) {
        String strSubst = interpretationContext.subst(attributes.getValue(SCAN_ATTR));
        if (OptionHelper.isEmpty(strSubst) || "false".equalsIgnoreCase(strSubst)) {
            return;
        }
        ScheduledExecutorService scheduledExecutorService = this.context.getScheduledExecutorService();
        URL mainWatchURL = ConfigurationWatchListUtil.getMainWatchURL(this.context);
        if (mainWatchURL == null) {
            addWarn("Due to missing top level configuration file, reconfiguration on change (configuration file scanning) cannot be done.");
            return;
        }
        ReconfigureOnChangeTask reconfigureOnChangeTask = new ReconfigureOnChangeTask();
        reconfigureOnChangeTask.setContext(this.context);
        this.context.putObject(CoreConstants.RECONFIGURE_ON_CHANGE_TASK, reconfigureOnChangeTask);
        Duration durationOfScanPeriodAttribute = getDurationOfScanPeriodAttribute(interpretationContext.subst(attributes.getValue(SCAN_PERIOD_ATTR)), SCAN_PERIOD_DEFAULT);
        addInfo("Will scan for changes in [" + mainWatchURL + "] ");
        addInfo("Setting ReconfigureOnChangeTask scanning period to " + durationOfScanPeriodAttribute);
        this.context.addScheduledFuture(scheduledExecutorService.scheduleAtFixedRate(reconfigureOnChangeTask, durationOfScanPeriodAttribute.getMilliseconds(), durationOfScanPeriodAttribute.getMilliseconds(), TimeUnit.MILLISECONDS));
    }
}
