package org.aicl.raytracerchallenge.puttogether.chapter6;

import org.aicl.raytracerchallenge.display.Canvas;
import org.aicl.raytracerchallenge.primitives.*;
import org.aicl.raytracerchallenge.primitives.light.LightSampler;
import org.aicl.raytracerchallenge.primitives.light.PointLight;
import org.aicl.raytracerchallenge.primitives.ray.Ray;
import org.aicl.raytracerchallenge.primitives.ray.RayIntersection;
import org.aicl.raytracerchallenge.primitives.shape.Sphere;
import org.junit.jupiter.api.Test;

import java.io.IOException;

public class PutTogetherChapter6 {
    @Test
    public void letsPutTogether() throws IOException {
        Canvas c        = new Canvas(100, 100);
        Point rayOrigin = new Point(0, 0, -5);
        double wallZ     = 10;
        double wallSize  = 7;
        double pixelSize = wallSize/100;
        double half      = wallSize/2;

        Sphere sphere = new Sphere();
        Material material = new Material();
        material.color = new Color(1, 0.2, 1);
        sphere.setMaterial(material);

        PointLight light = new PointLight(new Point(-10, 10, -10), new Color(1, 1, 1));
        for(int y = 0; y < 100 ; y++){
            double worldY = half - y * pixelSize;
            for(int x = 0; x < 100 ; x++){
                double worldX = -half + x * pixelSize;
                Point targetPos = new Point(worldX, worldY, wallZ);
                Vector direction = new Vector(targetPos.subtract(rayOrigin));

                Ray ray = new Ray(rayOrigin, new Vector(direction.normalize()));
                RayIntersection xs = sphere.intersect(ray);
                if(xs.count != 0){
                    Tuple pointAtHit = ray.position(xs.hit().time);
                    Vector normal    = sphere.normalAt(new Point(pointAtHit), xs.hit());
                    System.out.println("direction :" + direction.toString());
                    System.out.println("normal :" + normal.toString());
                    Vector eyev      = new Vector(direction.normalize().negates()); //new Vector(new Vector(10, 10, -10).normalize()); //specular would be when the light -- eye -- spehere in one straight line
                    Color color      = LightSampler.lighting(xs.hit().intersectedShape.getMaterial(), xs.hit().intersectedShape,
                            light, new Point(pointAtHit), eyev, normal, false);
                    c.writePixel(x, y, color);
                }
            }
        }

        c.writeToPpmFile("E:\\William\\HighImportance\\GIT_ROOT\\RayTracerChallenge\\RayTracerChallenge\\test\\put_together_chapter6.ppm");
    }
}
