package org.aicl.raytracerchallenge.primitives.shape;

import org.aicl.raytracerchallenge.primitives.Point;
import org.aicl.raytracerchallenge.primitives.Vector;
import org.aicl.raytracerchallenge.primitives.ray.Ray;
import org.aicl.raytracerchallenge.primitives.ray.RayIntersection;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

public class CylinderTest {
    @Test
    public void rayMissedCylinderTest(){
        Cylinder cyl = new Cylinder();

        ArrayList<Point> origins     = new ArrayList<>();
        ArrayList<Vector> directions = new ArrayList<>();

        origins.add(new Point(1, 0, 0)); directions.add(new Vector(0, 1, 0));
        origins.add(new Point(0, 0, 0)); directions.add(new Vector(0, 1, 0));
        origins.add(new Point(0, 0, -5)); directions.add(new Vector(1, 1, 1));

        for(int i = 0; i < origins.size() ; i++){
            Vector direction = new Vector(directions.get(i).normalize());
            Ray r = new Ray(origins.get(i), direction);
            RayIntersection intersections = cyl.localIntersect(r);
            assertEquals(0, intersections.count);
        }
    }

    @Test
    public void rayHitCylinder(){
        Cylinder cyl = new Cylinder();

        ArrayList<Point> origins     = new ArrayList<>();
        ArrayList<Vector> directions = new ArrayList<>();
        ArrayList<Double> t1 = new ArrayList<>();
        ArrayList<Double> t2 = new ArrayList<>();

        origins.add(new Point(1, 0, -5)); directions.add(new Vector(0, 0, 1)); t1.add(5.); t2.add(5.);
        origins.add(new Point(0, 0, -5)); directions.add(new Vector(0, 0, 1)); t1.add(4.); t2.add(6.);
        origins.add(new Point(0.5, 0, -5)); directions.add(new Vector(0.1, 1, 1)); t1.add(6.807981); t2.add(7.08872);

        for(int i = 0; i < origins.size() ; i++){
            Vector direction = new Vector(directions.get(i).normalize());
            Ray r = new Ray(origins.get(i), direction);
            RayIntersection intersections = cyl.localIntersect(r);
            assertEquals(2, intersections.count);
            assertEquals(t1.get(i), intersections.getIntersection(0).time, 0.00001);
            assertEquals(t2.get(i), intersections.getIntersection(1).time, 0.00001);
        }
    }

    @Test
    public void cylinderNormalTest(){
        Cylinder cyl = new Cylinder();

        ArrayList<Point> points   = new ArrayList<>();
        ArrayList<Vector> normals = new ArrayList<>();

        points.add(new Point(1, 0, 0)); normals.add(new Vector(1, 0, 0));
        points.add(new Point(0, 5, -1)); normals.add(new Vector(0, 0, -1));
        points.add(new Point(0, -2, 1)); normals.add(new Vector(0, 0, 1));
        points.add(new Point(-1, 1, 0)); normals.add(new Vector(-1, 0, 0));

        for(int i = 0; i < points.size(); i++){
            Vector n = new Vector(cyl.localNormal(points.get(i), null));
            assertTrue(n.isIdentical(normals.get(i)));
        }
    }

    @Test
    public void defaultMaxAndMinCyl(){
        Cylinder cyl = new Cylinder();
        assertEquals(cyl.maximum(), 999999, 0.00001);
        assertEquals(cyl.minimum(), -999999, 0.00001);
    }

    @Test
    public void testTruncatedCylinder(){
        Cylinder cyl = new Cylinder();
        cyl.setMinimum(1);
        cyl.setMaximum(2);

        ArrayList<Point> points      = new ArrayList<>();
        ArrayList<Vector> directions = new ArrayList<>();
        ArrayList<Integer> count     = new ArrayList<>();

        points.add(new Point(0, 1.5, 0)); directions.add(new Vector(0.1, 1, 0)); count.add(0);
        points.add(new Point(0, 3, -5)); directions.add(new Vector(0, 0, 1)); count.add(0);
        points.add(new Point(0, 0, -5)); directions.add(new Vector(0, 0, 1)); count.add(0);
        points.add(new Point(0, 2, -5)); directions.add(new Vector(0, 0 ,1)); count.add(0);
        points.add(new Point(0, 1, -5)); directions.add(new Vector(0, 0 ,1)); count.add(0);
        points.add(new Point(0, 1.5, -2)); directions.add(new Vector(0, 0 ,1)); count.add(2);
        for(int i = 0; i < points.size(); i++){
            Ray r = new Ray(points.get(i), new Vector(directions.get(i).normalize()));
            RayIntersection intersections = cyl.localIntersect(r);
            assertEquals(count.get(i), intersections.count);
        }
    }

    @Test
    public void testCapAttribute(){
        Cylinder cyl = new Cylinder();
        assertFalse(cyl.closed);
    }

    @Test
    public void intersectionsOnCappedCylinder(){
        Cylinder cyl = new Cylinder();

        cyl.setMinimum(1);
        cyl.setMaximum(2);
        cyl.closed = true;

        ArrayList<Point> points      = new ArrayList<>();
        ArrayList<Vector> directions = new ArrayList<>();
        ArrayList<Integer> count     = new ArrayList<>();

        points.add(new Point(0, 3, 0)); directions.add(new Vector(0, -1, 0)); count.add(2);
        points.add(new Point(0, 3, -2)); directions.add(new Vector(0, -1, 2)); count.add(2);
        points.add(new Point(0, 4, -2)); directions.add(new Vector(0, -1, 1)); count.add(2);
        points.add(new Point(0, 0, -2)); directions.add(new Vector(0, 1, 2)); count.add(2);
        points.add(new Point(0, -1, -2)); directions.add(new Vector(0, 1, 1)); count.add(2);

        for(int i = 0; i < points.size(); i++){
            Ray r = new Ray(points.get(i), new Vector(directions.get(i).normalize()));
            RayIntersection intersections = cyl.localIntersect(r);
            assertEquals(count.get(i), intersections.count);
        }
    }

    @Test
    public void normalsOnCappedCylinder(){
        Cylinder cyl = new Cylinder();
        cyl.setMinimum(1);
        cyl.setMaximum(2);
        cyl.closed = true;

        ArrayList<Point>  points   = new ArrayList<>();
        ArrayList<Vector> normals  = new ArrayList<>();

        points.add(new Point(0, 1, 0)); normals.add(new Vector(0, -1, 0));
        points.add(new Point(0.5, 1, 0)); normals.add(new Vector(0, -1, 0));
        points.add(new Point(0, 1, 0.5)); normals.add(new Vector(0, -1, 0));
        points.add(new Point(0, 2, 0)); normals.add(new Vector(0, 1, 0));
        points.add(new Point(0.5, 2, 0)); normals.add(new Vector(0, 1, 0));
        points.add(new Point(0, 2, 0.5)); normals.add(new Vector(0, 1, 0));

        for(int i = 0; i < points.size(); i++){
            Vector n = new Vector(cyl.localNormal(points.get(i), null));
            assertTrue(n.isIdentical(normals.get(i)));
        }
    }
}
