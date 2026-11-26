package com.miui.handwriting.common.ai.event;

import com.miui.handwriting.common.ai.data.CreationImage;

/* JADX INFO: loaded from: classes3.dex */
public class TransitionData {
    private CreationImage creationImage;
    private int outputHeight;
    private String outputPath;
    private int outputWidth;

    public int getOutputWidth() {
        return this.outputWidth;
    }

    public void setOutputWidth(int i) {
        this.outputWidth = i;
    }

    public int getOutputHeight() {
        return this.outputHeight;
    }

    public void setOutputHeight(int i) {
        this.outputHeight = i;
    }

    public String getOutputPath() {
        return this.outputPath;
    }

    public void setOutputPath(String str) {
        this.outputPath = str;
    }

    public CreationImage getCreationImage() {
        return this.creationImage;
    }

    public void setCreationImage(CreationImage creationImage) {
        this.creationImage = creationImage;
    }
}
