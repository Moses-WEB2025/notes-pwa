package ch.qos.logback.core.joran.action;

import ch.qos.logback.core.joran.event.SaxEvent;
import ch.qos.logback.core.joran.event.SaxEventRecorder;
import ch.qos.logback.core.joran.spi.InterpretationContext;
import ch.qos.logback.core.joran.spi.JoranException;
import ch.qos.logback.core.joran.util.ConfigurationWatchListUtil;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.List;

/* JADX INFO: loaded from: classes.dex */
public class IncludeAction extends AbstractIncludeAction {
    private static final String CONFIG_TAG = "configuration";
    private static final String INCLUDED_TAG = "included";
    private int eventOffset = 2;

    private String getEventName(SaxEvent saxEvent) {
        return saxEvent.qName.length() > 0 ? saxEvent.qName : saxEvent.localName;
    }

    private InputStream openURL(URL url) {
        try {
            return url.openStream();
        } catch (IOException e) {
            optionalWarning("Failed to open [" + url.toString() + "]", e);
            return null;
        }
    }

    private void trimHeadAndTail(SaxEventRecorder saxEventRecorder) {
        boolean zEqualsIgnoreCase;
        boolean zEqualsIgnoreCase2;
        int i;
        SaxEvent saxEvent;
        List<SaxEvent> saxEventList = saxEventRecorder.getSaxEventList();
        if (saxEventList.size() == 0) {
            return;
        }
        SaxEvent saxEvent2 = saxEventList.get(0);
        if (saxEvent2 != null) {
            String eventName = getEventName(saxEvent2);
            zEqualsIgnoreCase2 = INCLUDED_TAG.equalsIgnoreCase(eventName);
            zEqualsIgnoreCase = CONFIG_TAG.equalsIgnoreCase(eventName);
        } else {
            zEqualsIgnoreCase = false;
            zEqualsIgnoreCase2 = false;
        }
        if (zEqualsIgnoreCase2 || zEqualsIgnoreCase) {
            saxEventList.remove(0);
            int size = saxEventList.size();
            if (size == 0 || (saxEvent = saxEventList.get((i = size - 1))) == null) {
                return;
            }
            String eventName2 = getEventName(saxEvent);
            if ((zEqualsIgnoreCase2 && INCLUDED_TAG.equalsIgnoreCase(eventName2)) || (zEqualsIgnoreCase && CONFIG_TAG.equalsIgnoreCase(eventName2))) {
                saxEventList.remove(i);
            }
        }
    }

    protected SaxEventRecorder createRecorder(InputStream inputStream, URL url) {
        return new SaxEventRecorder(getContext());
    }

    @Override // ch.qos.logback.core.joran.action.AbstractIncludeAction
    protected void processInclude(InterpretationContext interpretationContext, URL url) throws JoranException {
        InputStream inputStreamOpenURL = openURL(url);
        try {
            if (inputStreamOpenURL != null) {
                ConfigurationWatchListUtil.addToWatchList(getContext(), url);
                SaxEventRecorder saxEventRecorderCreateRecorder = createRecorder(inputStreamOpenURL, url);
                saxEventRecorderCreateRecorder.setContext(getContext());
                saxEventRecorderCreateRecorder.recordEvents(inputStreamOpenURL);
                trimHeadAndTail(saxEventRecorderCreateRecorder);
                interpretationContext.getJoranInterpreter().getEventPlayer().addEventsDynamically(saxEventRecorderCreateRecorder.getSaxEventList(), this.eventOffset);
            }
        } catch (JoranException e) {
            optionalWarning("Failed processing [" + url.toString() + "]", e);
        } finally {
            close(inputStreamOpenURL);
        }
    }

    protected void setEventOffset(int i) {
        this.eventOffset = i;
    }
}
