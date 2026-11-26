package xc.engine.operate.touch;

import android.view.MotionEvent;
import xc.engine.data.CurveProp;
import xc.engine.operate.canvas.track.delete.DeleteProp;

/* JADX INFO: loaded from: classes6.dex */
public interface ITouchOperator {
    int getOperatorMode();

    void onCancelEvent();

    boolean onHandleEvent(MotionEvent motionEvent);

    void setDeleteProp(DeleteProp deleteProp);

    void setOperatorMode(int i);

    void setPenProp(CurveProp curveProp);
}
