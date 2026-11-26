package miuix.miuixbasewidget.widget;

/* JADX INFO: loaded from: classes5.dex */
public interface ScrollableView {

    public interface OnActionDownListener {
        void onActionDown();
    }

    public interface OnActionUpListener {
        void onActionUp();
    }

    public interface OnScrollListener {
        void onScroll(int i, int i2);
    }

    void actionDownEventCallBack();

    void actionUpEventCallBack();

    int getScrollRange();

    int getScrollY();

    void scrollTo(int i);

    void setOnScrollListener(OnScrollListener onScrollListener);
}
