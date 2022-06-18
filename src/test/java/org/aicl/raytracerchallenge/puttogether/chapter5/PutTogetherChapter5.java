package org.aicl.raytracerchallenge.puttogether.chapter5;

import org.aicl.raytracerchallenge.display.Canvas;
import org.aicl.raytracerchallenge.primitives.*;
import org.aicl.raytracerchallenge.primitives.shape.Sphere;
import org.junit.jupiter.api.Test;

import java.io.IOException;

public class PutTogetherChapter5 {
    @Test
    public void letsPutTogether() throws IOException {
        Canvas c        = new Canvas(100, 100);
        Point rayOrigin = new Point(0, 0, -5);
        double wallZ     = 10;
        double wallSize  = 7;
        double pixelSize = wallSize/100;
        double half      = wallSize/2;

        Sphere sphere = new Sphere();
        for(int y = 0; y < 100 ; y++){
            double worldY = half - y * pixelSize;
            for(int x = 0; x < 100 ; x++){
                double worldX = -half + x * pixelSize;
                Point targetPos = new Point(worldX, worldY, wallZ);
                Vector direction = new Vector(targetPos.subtract(rayOrigin));

                Ray ray = new Ray(rayOrigin, direction);
                RayIntersection xs = sphere.intersect(ray);
                if(xs.count != 0){
                    c.writePixel(x, y, new Color(1, 0 ,0 ));
                }
            }
        }

        c.writeToPpmFile("E:\\William\\HighImportance\\GIT_ROOT\\RayTracerChallenge\\RayTracerChallenge\\test\\put_together_chapter5.ppm");
    }
}
