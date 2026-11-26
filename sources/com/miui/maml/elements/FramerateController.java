package com.miui.maml.elements;

import android.graphics.Canvas;
import android.text.TextUtils;
import android.util.Log;
import com.miui.maml.ScreenElementRoot;
import com.miui.maml.util.Utils;
import java.util.ArrayList;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

/* JADX INFO: loaded from: classes3.dex */
public class FramerateController extends ScreenElement {
    public static final String INNER_TAG = "ControlPoint";
    public static final String TAG_NAME = "FramerateController";
    private ArrayList<ControlPoint> mControlPoints;
    private long mDelay;
    private long mLastUpdateTime;
    private Object mLock;
    private boolean mLoop;
    private long mNextUpdateInterval;
    private long mStartTime;
    private boolean mStopped;
    private String mTag;
    private long mTimeRange;

    @Override // com.miui.maml.elements.ScreenElement
    protected void doRender(Canvas canvas) {
    }

    public static class ControlPoint {
        public int mFramerate;
        public long mTime;

        public ControlPoint(Element element) {
            this.mTime = Utils.getAttrAsLongThrows(element, "time");
            this.mFramerate = Utils.getAttrAsInt(element, "frameRate", -1);
        }
    }

    public FramerateController(Element element, ScreenElementRoot screenElementRoot) {
        super(element, screenElementRoot);
        this.mControlPoints = new ArrayList<>();
        this.mLock = new Object();
        this.mLoop = Boolean.parseBoolean(element.getAttribute("loop"));
        this.mTag = element.getAttribute("tag");
        String attribute = element.getAttribute("delay");
        if (!TextUtils.isEmpty(attribute)) {
            try {
                this.mDelay = Long.parseLong(attribute);
            } catch (NumberFormatException unused) {
                Log.w(TAG_NAME, "invalid delay attribute");
            }
        }
        NodeList elementsByTagName = element.getElementsByTagName(INNER_TAG);
        boolean z = false;
        for (int i = 0; i < elementsByTagName.getLength(); i++) {
            this.mControlPoints.add(new ControlPoint((Element) elementsByTagName.item(i)));
        }
        ArrayList<ControlPoint> arrayList = this.mControlPoints;
        long j = arrayList.get(arrayList.size() - 1).mTime;
        this.mTimeRange = j;
        if (this.mLoop && j != 0) {
            z = true;
        }
        this.mLoop = z;
    }

    private void restart(long j) {
        synchronized (this.mLock) {
            this.mStartTime = j + this.mDelay;
            this.mStopped = false;
            this.mLastUpdateTime = 0L;
            this.mNextUpdateInterval = 0L;
            requestUpdate();
        }
    }

    @Override // com.miui.maml.elements.ScreenElement
    public void reset(long j) {
        super.reset(j);
        restart(j);
    }

    @Override // com.miui.maml.elements.ScreenElement
    protected void playAnim(long j, long j2, long j3, boolean z, boolean z2) {
        if (isVisible()) {
            super.playAnim(j, j2, j3, z, z2);
            restart(j - j2);
        }
    }

    @Override // com.miui.maml.elements.ScreenElement
    public void setAnim(String[] strArr) {
        show(isTagEnable(strArr, this.mTag));
    }

    public long updateFramerate(long j) {
        updateVisibility();
        long j2 = Long.MAX_VALUE;
        if (!isVisible()) {
            return Long.MAX_VALUE;
        }
        synchronized (this.mLock) {
            if (this.mStopped) {
                return Long.MAX_VALUE;
            }
            long j3 = this.mLastUpdateTime;
            long j4 = 0;
            if (j3 > 0) {
                long j5 = j - j3;
                if (j5 >= 0) {
                    long j6 = this.mNextUpdateInterval;
                    if (j5 < j6) {
                        long j7 = j6 - j5;
                        this.mNextUpdateInterval = j7;
                        this.mLastUpdateTime = j;
                        return j7;
                    }
                }
            }
            long j8 = j - this.mStartTime;
            if (j8 < 0) {
                j8 = 0;
            }
            if (this.mLoop) {
                j8 %= this.mTimeRange + 1;
            }
            for (int size = this.mControlPoints.size() - 1; size >= 0; size--) {
                ControlPoint controlPoint = this.mControlPoints.get(size);
                if (j8 >= controlPoint.mTime) {
                    requestFramerate(controlPoint.mFramerate);
                    if (!this.mLoop && size == this.mControlPoints.size() - 1) {
                        this.mStopped = true;
                    }
                    this.mLastUpdateTime = j;
                    if (!this.mStopped) {
                        j2 = j4 - j8;
                    }
                    this.mNextUpdateInterval = j2;
                    return j2;
                }
                j4 = controlPoint.mTime;
            }
            return Long.MAX_VALUE;
        }
    }
}
