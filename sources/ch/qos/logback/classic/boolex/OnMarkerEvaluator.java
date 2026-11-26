package ch.qos.logback.classic.boolex;

import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.boolex.EvaluationException;
import ch.qos.logback.core.boolex.EventEvaluatorBase;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.slf4j.Marker;

/* JADX INFO: loaded from: classes.dex */
public class OnMarkerEvaluator extends EventEvaluatorBase<ILoggingEvent> {
    List<String> markerList = new ArrayList();

    public void addMarker(String str) {
        this.markerList.add(str);
    }

    @Override // ch.qos.logback.core.boolex.EventEvaluator
    public boolean evaluate(ILoggingEvent iLoggingEvent) throws NullPointerException, EvaluationException {
        Marker marker = iLoggingEvent.getMarker();
        if (marker == null) {
            return false;
        }
        Iterator<String> it = this.markerList.iterator();
        while (it.hasNext()) {
            if (marker.contains(it.next())) {
                return true;
            }
        }
        return false;
    }
}
