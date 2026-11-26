package com.miui.note.algorithm.common;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/* JADX INFO: loaded from: classes3.dex */
public class Graph {
    private ArrayList<Point> fittedPoints = new ArrayList<>();
    private Map<String, Float> params = new HashMap();
    private ArrayList<Point> points;
    private GraphType type;

    public Graph(GraphType graphType, ArrayList<Point> arrayList) {
        if (graphType == null) {
            throw new RuntimeException("graph type is not null");
        }
        this.type = graphType;
        if (arrayList != null) {
            this.points = new ArrayList<>(arrayList);
        } else {
            this.points = new ArrayList<>();
        }
    }

    public void setGraphParam(String str, Float f) {
        this.params.put(str, f);
    }

    public Map<String, Float> getParams() {
        return this.params;
    }

    public GraphType getType() {
        return this.type;
    }

    public ArrayList<Point> getPoints() {
        return new ArrayList<>(this.points);
    }

    public void setFittedPoints(ArrayList<Point> arrayList) {
        this.fittedPoints = new ArrayList<>(arrayList);
    }

    public ArrayList<Point> getFittedPoints() {
        return this.fittedPoints;
    }
}
