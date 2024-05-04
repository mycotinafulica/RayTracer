package org.aicl.raytracerchallenge.puttogether.chapter4;

import org.aicl.raytracerchallenge.display.Canvas;
import org.aicl.raytracerchallenge.primitives.Color;
import org.aicl.raytracerchallenge.primitives.Matrix;
import org.aicl.raytracerchallenge.primitives.Point;
import org.aicl.raytracerchallenge.transformation.TransformMatrixGenerator;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.ArrayList;

public class Chapter4PutTogether {
    @Test
    public void letsPutTogether() throws IOException {
        Canvas c = new Canvas(300, 300);
        ArrayList<Point> points = new ArrayList<>();

        for(int i = 0; i < 12 ; i++){
            points.add(new Point(0, 0, 0));
        }
        TransformMatrixGenerator generator = new TransformMatrixGenerator();
        Matrix translate = generator.translate(125, 0, 0);

        for(int i = 0; i < 12 ; i++){
            Point p = points.get(i);
            Matrix rotate  = generator.rotateZ(0 + i * 2*Math.PI/12.0);
            Point newPoint = new Point(rotate.multiply(translate.multiply(p)));
            c.writePixel((int) newPoint.x + 150, (int) newPoint.y + 150, new Color(1, 1, 1));
        }

        c.writeToPpmFile("E:\\William\\HighImportance\\GIT_ROOT\\RayTracerChallenge\\RayTracerChallenge\\test\\put_together_chap3.ppm");
    }
}
