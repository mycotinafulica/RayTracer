package org.aicl.raytracerchallenge.primitives.shape;

import static org.junit.jupiter.api.Assertions.*;

import org.aicl.raytracerchallenge.primitives.Point;
import org.aicl.raytracerchallenge.primitives.Vector;
import org.aicl.raytracerchallenge.primitives.ray.Ray;
import org.aicl.raytracerchallenge.primitives.ray.RayIntersection;
import org.junit.jupiter.api.Test;

public class PlaneTest {
    @Test
    public void testPlaneNormals(){
        Plane p   = new Plane();
        Vector n1 = new Vector(p.localNormal(new Point(0, 0, 0)));
        Vector n2 = new Vector(p.localNormal(new Point(10, 0, -10)));
        Vector n3 = new Vector(p.localNormal(new Point(-5, 0, 150)));
        Vector expected = new Vector(0, 1, 0);
        assertTrue(expected.isIdentical(n1));
        assertTrue(expected.isIdentical(n2));
        assertTrue(expected.isIdentical(n3));
    }

    @Test
    public void testRayParallelToPlane(){
        Plane p   = new Plane();
        Ray ray   = new Ray(new Point(0, 10, 0), new Vector(0, 0, 1));
        RayIntersection intersection = p.localIntersect(ray);
        assertEquals(0, intersection.count);

        ray = new Ray(new Point(0, 0, 0), new Vector(0, 0, 1));
        intersection = p.localIntersect(ray);
        assertEquals(0, intersection.count);
    }

    @Test
    public void testRayPlaneIntersection(){
        Plane p   = new Plane();
        Ray ray   = new Ray(new Point(0, 1, 0), new Vector(0, -1, 0));
        RayIntersection intersection = p.localIntersect(ray);
        assertEquals(1, intersection.count);
        assertEquals(1.0, intersection.getIntersection(0).time);
        assertTrue(intersection.getIntersection(0).intersectedShape.isSame(p));

        ray = new Ray(new Point(0, -1, 0), new Vector(0, 1, 0));
        intersection = p.localIntersect(ray);
        assertEquals(1, intersection.count);
        assertEquals(1.0, intersection.getIntersection(0).time);
        assertTrue(intersection.getIntersection(0).intersectedShape.isSame(p));
    }
}
