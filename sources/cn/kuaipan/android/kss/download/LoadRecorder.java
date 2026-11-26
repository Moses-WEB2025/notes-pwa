package cn.kuaipan.android.kss.download;

import java.io.IOException;

/* JADX INFO: loaded from: classes.dex */
public class LoadRecorder {
    private LoadMap map;
    private final LoadMap.Space space;

    LoadRecorder(LoadMap loadMap, LoadMap.Space space) {
        this.map = loadMap;
        this.space = space;
    }

    public void add(int i) throws IOException {
        if (this.map == null) {
            throw new IOException("The recoder has been recycled");
        }
        this.space.remove(i);
        this.map.onSpaceRemoved(i);
    }

    public boolean isCompleted() {
        return this.space.size() <= 0;
    }

    public void recycle() {
        LoadMap loadMap = this.map;
        if (loadMap != null) {
            loadMap.recycleRecorder(this);
            this.map = null;
        }
    }

    long getStart() {
        return this.space.getStart();
    }

    public long size() {
        return this.space.size();
    }

    LoadMap.Space getSpace() {
        return this.space;
    }

    protected void finalize() throws Throwable {
        recycle();
        super.finalize();
    }
}
