package com.miui.handwriting.common.data.bean;

import xc.engine.operate.canvas.track.delete.DeleteProp;
import xc.engine.operate.canvas.track.delete.DeleteType;

/* JADX INFO: loaded from: classes3.dex */
public class MiuiDeleteProp {
    private float deleteRadius;
    private DeleteType deleteType;

    public MiuiDeleteProp(DeleteType deleteType, float f) {
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

    public DeleteProp covert() {
        return new DeleteProp(this.deleteType, this.deleteRadius);
    }
}
