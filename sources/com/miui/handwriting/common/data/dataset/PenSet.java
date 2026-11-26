package com.miui.handwriting.common.data.dataset;

import com.miui.handwriting.common.data.bean.PenBean;
import java.util.ArrayList;
import java.util.List;
import xc.engine.core.graphics.pen.PenType;

/* JADX INFO: loaded from: classes3.dex */
public class PenSet {
    public static final int NO_NEED_CHANGE = -1;
    private List<Integer> bgColors;
    private PenBean curPenBean;
    private List<PenBean> penList;
    public boolean isWrite = true;
    private int curPenPosition = 0;
    private List<PenSetChangeListener> listeners = new ArrayList();

    public interface PenSetChangeListener {
        void onPenBeanChanged(PenBean penBean);
    }

    public void restorePen() {
    }

    public PenSet() {
        init();
    }

    private void init() {
        this.penList = new ArrayList();
        PenBean penBean = new PenBean(PenType.INK);
        this.penList.add(penBean);
        penBean.setTipSize(PenBean.getInkPenTipSize());
        this.penList.add(new PenBean(PenType.MARK));
        this.penList.add(new PenBean(PenType.WATER_COLOR));
        this.penList.add(new PenBean(PenType.PENCIL));
        this.penList.add(new PenBean(PenType.BALL));
    }

    public List<PenBean> getPenList() {
        if (this.penList == null) {
            init();
        }
        return this.penList;
    }

    public PenBean getCurPenBean() {
        if (this.curPenBean == null) {
            this.curPenBean = getPenList().get(this.curPenPosition);
        }
        return this.curPenBean;
    }

    public PenBean getPenBeanByType(PenType penType) {
        for (int i = 0; i < getPenList().size(); i++) {
            if (penType == getPenList().get(i).getPenType()) {
                return getPenList().get(i);
            }
        }
        return getPenList().get(0);
    }

    public void updatePenBean(PenBean penBean) {
        for (int i = 0; i < getPenList().size(); i++) {
            if (penBean.getPenType() == getPenList().get(i).getPenType()) {
                getPenList().set(i, penBean);
                notifyPenBeanChanged(penBean);
                return;
            }
        }
    }

    public void setCurPenBean(PenBean penBean) {
        this.curPenBean = penBean;
        notifyPenBeanChanged(penBean);
    }

    public int getCurPenPosition() {
        for (int i = 0; i < getPenList().size(); i++) {
            if (this.curPenBean.getPenType() == getPenList().get(i).getPenType()) {
                this.curPenPosition = i;
                break;
            }
        }
        return this.curPenPosition;
    }

    public List<Integer> getColors() {
        if (this.bgColors == null) {
            ArrayList arrayList = new ArrayList();
            this.bgColors = arrayList;
            arrayList.add(Integer.valueOf(Color.LIGHT_PURPLE));
            this.bgColors.add(Integer.valueOf(Color.RED));
            this.bgColors.add(Integer.valueOf(Color.ORAGNE));
            this.bgColors.add(Integer.valueOf(Color.GREEN));
            this.bgColors.add(Integer.valueOf(Color.BLUE));
            this.bgColors.add(Integer.valueOf(Color.BABY_BLUE));
            this.bgColors.add(Integer.valueOf(Color.DEEP_PURPLE));
            this.bgColors.add(-16777216);
            this.bgColors.add(-1);
            this.bgColors.add(Integer.valueOf(Color.PINK));
            this.bgColors.add(Integer.valueOf(Color.FAINT_YELLOW));
            this.bgColors.add(Integer.valueOf(Color.LIGHT_GREEN));
        }
        return this.bgColors;
    }

    public void reset() {
        this.isWrite = true;
    }

    public void addListener(PenSetChangeListener penSetChangeListener) {
        if (penSetChangeListener == null || this.listeners.contains(penSetChangeListener)) {
            return;
        }
        this.listeners.add(penSetChangeListener);
    }

    public void removeListener(PenSetChangeListener penSetChangeListener) {
        if (isContainsListener(penSetChangeListener)) {
            this.listeners.remove(penSetChangeListener);
        }
    }

    public boolean isContainsListener(PenSetChangeListener penSetChangeListener) {
        return this.listeners.contains(penSetChangeListener);
    }

    private void notifyPenBeanChanged(PenBean penBean) {
        List<PenSetChangeListener> list = this.listeners;
        if (list == null || list.isEmpty()) {
            return;
        }
        for (PenSetChangeListener penSetChangeListener : this.listeners) {
            if (penSetChangeListener != null) {
                try {
                    penSetChangeListener.onPenBeanChanged(penBean);
                } catch (Exception unused) {
                }
            }
        }
    }

    public class Color {
        public static final int BABY_BLUE = -16711681;
        public static final int BLUE = -16602385;
        public static final int DEEP_PURPLE = -7722015;
        public static final int FAINT_YELLOW = -6961;
        public static final int GREEN = -12006585;
        public static final int LIGHT_GREEN = -2230819;
        public static final int LIGHT_PURPLE = -4143924;
        public static final int ORAGNE = -16896;
        public static final int PINK = -10280;
        public static final int RED = -316883;

        public Color() {
        }
    }
}
