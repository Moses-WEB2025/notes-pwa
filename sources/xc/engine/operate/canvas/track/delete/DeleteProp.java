package xc.engine.operate.canvas.track.delete;

/* JADX INFO: loaded from: classes6.dex */
public class DeleteProp {
    private float deleteRadius;
    private DeleteType deleteType;

    public DeleteProp(DeleteType deleteType, float f) {
        this.deleteType = deleteType;
        this.deleteRadius = f;
    }

    public DeleteType getDeleteType() {
        return this.deleteType;
    }

    public void setDeleteType(DeleteType deleteType) {
        this.deleteType = deleteType;
    }

    public float getDeleteRadius() {
        return this.deleteRadius;
    }

    public void setDeleteRadius(float f) {
        this.deleteRadius = f;
    }
}
