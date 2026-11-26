package xc.engine.data.pagedata;

import android.graphics.RectF;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

/* JADX INFO: loaded from: classes6.dex */
public interface IData {
    void clearPointsData();

    int getDataSize();

    int getDataState();

    RectF getDrawRect();

    RectF getSaveRect();

    long getTime();

    short getType();

    IData readData(DataInputStream dataInputStream, int i) throws IOException;

    void writeData(DataOutputStream dataOutputStream) throws IOException;
}
