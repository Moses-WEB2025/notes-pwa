package miuix.internal.hybrid.webkit;

import miuix.hybrid.HybridBackForwardList;
import miuix.hybrid.HybridHistoryItem;

/* JADX INFO: loaded from: classes5.dex */
public class WebBackForwardList extends HybridBackForwardList {
    private android.webkit.WebBackForwardList mWebBackForwardList;

    public WebBackForwardList(android.webkit.WebBackForwardList webBackForwardList) {
        this.mWebBackForwardList = webBackForwardList;
    }

    @Override // miuix.hybrid.HybridBackForwardList
    public HybridHistoryItem getCurrentItem() {
        return new WebHistoryItem(this.mWebBackForwardList.getCurrentItem());
    }

    @Override // miuix.hybrid.HybridBackForwardList
    public int getCurrentIndex() {
        return this.mWebBackForwardList.getCurrentIndex();
    }

    @Override // miuix.hybrid.HybridBackForwardList
    public HybridHistoryItem getItemAtIndex(int i) {
        return new WebHistoryItem(this.mWebBackForwardList.getItemAtIndex(i));
    }

    @Override // miuix.hybrid.HybridBackForwardList
    public int getSize() {
        return this.mWebBackForwardList.getSize();
    }
}
