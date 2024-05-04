package org.aicl.raytracerchallenge.primitives.shape;

import org.aicl.raytracerchallenge.primitives.Point;
import static org.junit.jupiter.api.Assertions.*;

import org.aicl.raytracerchallenge.primitives.Vector;
import org.aicl.raytracerchallenge.primitives.ray.Ray;
import org.aicl.raytracerchallenge.primitives.ray.RayIntersection;
import org.junit.jupiter.api.Test;

public class TriangleTest {
    @Test
    public void testConstruction() {
        Point p1 = new Point(0, 1, 0);
        Point p2 = new Point(-1, 0, 0);
        Point p3 = new Point(1, 0, 0);

        Triangle t = new Triangle(p1, p2, p3);
        assertTrue(t.p1.isIdentical(p1));
        assertTrue(t.p2.isIdentical(p2));
        assertTrue(t.p3.isIdentical(p3));
        assertTrue(t.e1.isIdentical(new Vector(-1, -1, 0)));
        assertTrue(t.e2.isIdentical(new Vector(1, -1, 0)));
        assertTrue(t.normal.isIdentical(new Vector(0, 0, -1)));
    }

    @Test
    public void triangleNormalTest(){
        Point p1 = new Point(0, 1, 0);
        Point p2 = new Point(-1, 0, 0);
        Point p3 = new Point(1, 0, 0);

        Triangle t = new Triangle(p1, p2, p3);
        Vector n1 = new Vector(t.localNormal(new Point(0, 0.5, 0), null));
        Vector n2 = new Vector(t.localNormal(new Point(-0.5, 0.75, 0), null));
        Vector n3 = new Vector(t.localNormal(new Point(0.5, 0.25, 0), null));

        assertTrue(n1.isIdentical(t.normal));
        assertTrue(n2.isIdentical(t.normal));
        assertTrue(n3.isIdentical(t.normal));
    }

    @Test
    public void triangleIntersectionParallelRay(){
        Point p1 = new Point(0, 1, 0);
        Point p2 = new Point(-1, 0, 0);
        Point p3 = new Point(1, 0, 0);

        Triangle t = new Triangle(p1, p2, p3);
        Ray r = new Ray(new Point(0, -1, -2), new Vector(0, 1, 0));
        RayIntersection xs = t.localIntersect(r);
        assertEquals(0, xs.count);
    }

    @Test
    public void triangleIntersectionMissOverEdges(){
        Point p1 = new Point(0, 1, 0);
        Point p2 = new Point(-1, 0, 0);
        Point p3 = new Point(1, 0, 0);

        Triangle t = new Triangle(p1, p2, p3);
        Ray r = new Ray(new Point(1, 1, -2), new Vector(0, 0, 1));
        RayIntersection xs = t.localIntersect(r);
        assertEquals(0, xs.count);

        t = new Triangle(p1, p2, p3);
        r = new Ray(new Point(-1, 1, -2), new Vector(0, 0, 1));
        xs = t.localIntersect(r);
        assertEquals(0, xs.count);

        t = new Triangle(p1, p2, p3);
        r = new Ray(new Point(0, -1, -2), new Vector(0, 0, 1));
        xs = t.localIntersect(r);
        assertEquals(0, xs.count);
    }

    @Test
    public void testIntersectionHitTriangle(){
        Point p1 = new Point(0, 1, 0);
        Point p2 = new Point(-1, 0, 0);
        Point p3 = new Point(1, 0, 0);

        Triangle t = new Triangle(p1, p2, p3);
        Ray r = new Ray(new Point(0, 0.5, -2), new Vector(0, 0, 1));
        RayIntersection xs = t.localIntersect(r);
        assertEquals(1, xs.count);
        assertEquals(2, xs.getIntersection(0).time);
    }
}
