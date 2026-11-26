package ch.qos.logback.classic.joran;

import ch.qos.logback.classic.LoggerContext;
import ch.qos.logback.core.joran.event.SaxEvent;
import ch.qos.logback.core.joran.spi.ConfigurationWatchList;
import ch.qos.logback.core.joran.spi.JoranException;
import ch.qos.logback.core.joran.util.ConfigurationWatchListUtil;
import ch.qos.logback.core.spi.ContextAwareBase;
import ch.qos.logback.core.status.StatusUtil;
import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.apache.commons.text.lookup.StringLookupFactory;

/* JADX INFO: loaded from: classes.dex */
public class ReconfigureOnChangeTask extends ContextAwareBase implements Runnable {
    public static final String DETECTED_CHANGE_IN_CONFIGURATION_FILES = "Detected change in configuration files.";
    static final String FALLING_BACK_TO_SAFE_CONFIGURATION = "Given previous errors, falling back to previously registered safe configuration.";
    static final String RE_REGISTERING_PREVIOUS_SAFE_CONFIGURATION = "Re-registering previous fallback configuration once more as a fallback configuration point";
    long birthdate = System.currentTimeMillis();
    List<ReconfigureOnChangeTaskListener> listeners;

    private void fallbackConfiguration(LoggerContext loggerContext, List<SaxEvent> list, URL url) {
        List<SaxEvent> listRemoveIncludeEvents = removeIncludeEvents(list);
        JoranConfigurator joranConfigurator = new JoranConfigurator();
        joranConfigurator.setContext(this.context);
        ConfigurationWatchList configurationWatchListBuildClone = ConfigurationWatchListUtil.getConfigurationWatchList(this.context).buildClone();
        if (listRemoveIncludeEvents == null || listRemoveIncludeEvents.isEmpty()) {
            addWarn("No previous configuration to fall back on.");
            return;
        }
        addWarn(FALLING_BACK_TO_SAFE_CONFIGURATION);
        try {
            loggerContext.reset();
            ConfigurationWatchListUtil.registerConfigurationWatchList(this.context, configurationWatchListBuildClone);
            joranConfigurator.doConfigure(listRemoveIncludeEvents);
            addInfo(RE_REGISTERING_PREVIOUS_SAFE_CONFIGURATION);
            joranConfigurator.registerSafeConfiguration(list);
            addInfo("after registerSafeConfiguration: " + list);
        } catch (JoranException e) {
            addError("Unexpected exception thrown by a configuration considered safe.", e);
        }
    }

    private void fireChangeDetected() {
        List<ReconfigureOnChangeTaskListener> list = this.listeners;
        if (list == null) {
            return;
        }
        Iterator<ReconfigureOnChangeTaskListener> it = list.iterator();
        while (it.hasNext()) {
            it.next().changeDetected();
        }
    }

    private void fireDoneReconfiguring() {
        List<ReconfigureOnChangeTaskListener> list = this.listeners;
        if (list == null) {
            return;
        }
        Iterator<ReconfigureOnChangeTaskListener> it = list.iterator();
        while (it.hasNext()) {
            it.next().doneReconfiguring();
        }
    }

    private void fireEnteredRunMethod() {
        List<ReconfigureOnChangeTaskListener> list = this.listeners;
        if (list == null) {
            return;
        }
        Iterator<ReconfigureOnChangeTaskListener> it = list.iterator();
        while (it.hasNext()) {
            it.next().enteredRunMethod();
        }
    }

    private void performXMLConfiguration(LoggerContext loggerContext, URL url) {
        JoranConfigurator joranConfigurator = new JoranConfigurator();
        joranConfigurator.setContext(this.context);
        StatusUtil statusUtil = new StatusUtil(this.context);
        List<SaxEvent> listRecallSafeConfiguration = joranConfigurator.recallSafeConfiguration();
        URL mainWatchURL = ConfigurationWatchListUtil.getMainWatchURL(this.context);
        loggerContext.reset();
        long jCurrentTimeMillis = System.currentTimeMillis();
        try {
            joranConfigurator.doConfigure(url);
            if (statusUtil.hasXMLParsingErrors(jCurrentTimeMillis)) {
                fallbackConfiguration(loggerContext, listRecallSafeConfiguration, mainWatchURL);
            }
        } catch (JoranException unused) {
            fallbackConfiguration(loggerContext, listRecallSafeConfiguration, mainWatchURL);
        }
    }

    private List<SaxEvent> removeIncludeEvents(List<SaxEvent> list) {
        ArrayList arrayList = new ArrayList();
        if (list != null) {
            for (SaxEvent saxEvent : list) {
                if (!"include".equalsIgnoreCase(saxEvent.getLocalName())) {
                    arrayList.add(saxEvent);
                }
            }
        }
        return arrayList;
    }

    void addListener(ReconfigureOnChangeTaskListener reconfigureOnChangeTaskListener) {
        if (this.listeners == null) {
            this.listeners = new ArrayList();
        }
        this.listeners.add(reconfigureOnChangeTaskListener);
    }

    @Override // java.lang.Runnable
    public void run() {
        fireEnteredRunMethod();
        ConfigurationWatchList configurationWatchList = ConfigurationWatchListUtil.getConfigurationWatchList(this.context);
        if (configurationWatchList == null) {
            addWarn("Empty ConfigurationWatchList in context");
            return;
        }
        List<File> copyOfFileWatchList = configurationWatchList.getCopyOfFileWatchList();
        if (copyOfFileWatchList == null || copyOfFileWatchList.isEmpty()) {
            addInfo("Empty watch file list. Disabling ");
            return;
        }
        if (configurationWatchList.changeDetected()) {
            fireChangeDetected();
            URL mainURL = configurationWatchList.getMainURL();
            addInfo(DETECTED_CHANGE_IN_CONFIGURATION_FILES);
            addInfo("Will reset and reconfigure context named [" + this.context.getName() + "]");
            LoggerContext loggerContext = (LoggerContext) this.context;
            if (mainURL.toString().endsWith(StringLookupFactory.KEY_XML)) {
                performXMLConfiguration(loggerContext, mainURL);
            } else if (mainURL.toString().endsWith("groovy")) {
                addError("Groovy classes are not available on the class path. ABORTING INITIALIZATION.");
            }
            fireDoneReconfiguring();
        }
    }

    public String toString() {
        return "ReconfigureOnChangeTask(born:" + this.birthdate + ")";
    }
}
