package xc.engine.core.algorithm.bean;

import java.io.Serializable;

/* JADX INFO: loaded from: classes6.dex */
public class SBean implements Serializable {
    private static final long serialVersionUID = 9189425552866968482L;
    private long id;
    private String result;
    private int shape;
    private String type;
    private int valid;
    private int version;

    public String getType() {
        return this.type;
    }

    public void setType(String str) {
        this.type = str;
    }

    public int getVersion() {
        return this.version;
    }

    public void setVersion(int i) {
        this.version = i;
    }

    public long getId() {
        return this.id;
    }

    public void setId(long j) {
        this.id = j;
    }

    public int getValid() {
        return this.valid;
    }

    public void setValid(int i) {
        this.valid = i;
    }

    public int getShape() {
        return this.shape;
    }

    public void setShape(int i) {
        this.shape = i;
    }

    public String getResult() {
        return this.result;
    }

    public void setResult(String str) {
        this.result = str;
    }
}
