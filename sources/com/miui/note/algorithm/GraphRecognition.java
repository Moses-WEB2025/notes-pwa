package com.miui.note.algorithm;

import com.miui.note.algorithm.common.Graph;
import com.miui.note.algorithm.common.GraphType;
import com.miui.note.algorithm.common.Point;
import com.miui.note.algorithm.service.GraphService;
import com.miui.note.algorithm.utils.GeometryUtil;
import java.util.ArrayList;
import java.util.List;

/* JADX INFO: loaded from: classes3.dex */
public class GraphRecognition {
    public static Graph curveTypeRecognition(List<Point> list) {
        Graph graphIsPolygon;
        if (list.size() < 2) {
            return new Graph(GraphType.OTHER, null);
        }
        if (!GraphService.isCloseCurve(list)) {
            if (GeometryUtil.isStraightLine2(list, 0.97d)) {
                graphIsPolygon = new Graph(GraphType.STRAIGHT_LINE, new ArrayList<Point>(list.get(list.size() - 1)) { // from class: com.miui.note.algorithm.GraphRecognition.1
                    final /* synthetic */ Point val$endPoint;

                    {
                        this.val$endPoint = point;
                        add(this.val$startPoint);
                        add(point);
                    }
                });
            } else {
                ArrayList<Point> arrayListIs90brokenLine = GraphService.is90brokenLine(list);
                if (arrayListIs90brokenLine != null) {
                    graphIsPolygon = new Graph(GraphType.BROKEN_LINE, arrayListIs90brokenLine);
                } else {
                    ArrayList<Point> arrayListIsConic = GraphService.isConic(list);
                    graphIsPolygon = arrayListIsConic != null ? new Graph(GraphType.ARC_LINE, arrayListIsConic) : null;
                }
            }
        } else {
            graphIsPolygon = GraphService.isPolygon(list);
        }
        if (graphIsPolygon != null) {
            GraphFitting.curveShapeFitting(graphIsPolygon, list);
        }
        if (graphIsPolygon == null || graphIsPolygon.getType() == null || GraphType.OTHER.equals(graphIsPolygon.getType())) {
            return null;
        }
        for (Point point : graphIsPolygon.getFittedPoints()) {
            if (Float.isNaN(point.getX()) || Float.isNaN(point.getY()) || Float.isInfinite(point.getX()) || Float.isInfinite(point.getY())) {
                return null;
            }
        }
        return graphIsPolygon;
    }
}
