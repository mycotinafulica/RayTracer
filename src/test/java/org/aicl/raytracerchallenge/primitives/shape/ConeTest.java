package org.aicl.raytracerchallenge.primitives.shape;

import org.aicl.raytracerchallenge.primitives.Point;
import org.aicl.raytracerchallenge.primitives.Vector;
import org.aicl.raytracerchallenge.primitives.ray.Ray;
import org.aicl.raytracerchallenge.primitives.ray.RayIntersection;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ConeTest {
    @Test
    public void coneIntersectionTest() {
        Cone cone = new Cone();

        ArrayList<Point> origins     = new ArrayList<>();
        ArrayList<Vector> directions = new ArrayList<>();
        ArrayList<Double> t1 = new ArrayList<>();
        ArrayList<Double> t2 = new ArrayList<>();

        origins.add(new Point(0, 0, -5)); directions.add(new Vector(0, 0, 1)); t1.add(5.); t2.add(5.);
        origins.add(new Point(0, 0, -5)); directions.add(new Vector(1, 1, 1)); t1.add(8.66025); t2.add(8.66025);
        origins.add(new Point(1, 1, -5)); directions.add(new Vector(-0.5, -1, 1)); t1.add(4.55006); t2.add(49.44994);

        for(int i = 0; i < origins.size() ; i++){
            Vector direction = new Vector(directions.get(i).normalize());
            Ray r = new Ray(origins.get(i), direction);
            RayIntersection intersections = cone.localIntersect(r);
            assertEquals(2, intersections.count);
            assertEquals(t1.get(i), intersections.getIntersection(0).time, 0.00001);
            assertEquals(t2.get(i), intersections.getIntersection(1).time, 0.00001);
        }
    }

    @Test
    public void coneIntersectRayParallelToOneOfItsHalves(){
        Cone cone = new Cone();

        Vector direction = new Vector(new Vector(0,1, 1).normalize());
        Ray r = new Ray(new Point(0, 0, -1), direction);
        RayIntersection intersections = cone.localIntersect(r);
        assertEquals(1, intersections.count);
        assertEquals(0.35355, intersections.getIntersection(0).time, 0.00001);
    }

    @Test
    public void coneWithCapsTest(){
        Cone cone = new Cone();
        cone.setMinimum(-0.5);
        cone.setMaximum(0.5);
        cone.closed = true;

        ArrayList<Point> origins     = new ArrayList<>();
        ArrayList<Vector> directions = new ArrayList<>();
        ArrayList<Integer> counts    = new ArrayList<>();

        origins.add(new Point(0, 0, -5)); directions.add(new Vector(0, 1, 0)); counts.add(0);
        origins.add(new Point(0, 0, -0.25)); directions.add(new Vector(0, 1, 1)); counts.add(2);
        origins.add(new Point(0, 0, -0.25)); directions.add(new Vector(0, 1, 0)); counts.add(4);

        for(int i = 0 ; i < origins.size(); i++){
            Vector direction = new Vector(directions.get(i).normalize());
            Ray r = new Ray(origins.get(i), direction);
            RayIntersection xs = cone.localIntersect(r);
            assertEquals(counts.get(i), xs.count);
        }
    }

    @Test
    public void coneNormalsTest() {
        Cone cone = new Cone();
        cone.setMinimum(-0.5);
        cone.setMaximum(0.5);
        cone.closed = true;

        ArrayList<Point>  points = new ArrayList<>();
        ArrayList<Vector> normals = new ArrayList<>();

        points.add(new Point(0, 0, 0)); normals.add(new Vector(0, 0, 0));
        points.add(new Point(1, 1, 1)); normals.add(new Vector(1, -Math.sqrt(2), 1));
        points.add(new Point(-1, -1, 0)); normals.add(new Vector(-1, 1, 0));

        for(int i = 0; i < points.size(); i++){
            Vector n = new Vector(cone.localNormal(points.get(i), null));
            System.out.println(n.toString());
            assertTrue(n.isIdentical(normals.get(i)));
        }
    }
}
