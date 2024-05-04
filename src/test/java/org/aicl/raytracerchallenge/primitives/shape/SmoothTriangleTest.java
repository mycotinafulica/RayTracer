package org.aicl.raytracerchallenge.primitives.shape;

import org.aicl.raytracerchallenge.primitives.Point;
import org.aicl.raytracerchallenge.primitives.Vector;
import org.aicl.raytracerchallenge.primitives.ray.Intersection;
import org.aicl.raytracerchallenge.primitives.ray.PrecomputedIntersectionData;
import org.aicl.raytracerchallenge.primitives.ray.Ray;
import org.aicl.raytracerchallenge.primitives.ray.RayIntersection;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class SmoothTriangleTest {

    @Test
    public void constructionTest(){
        Point p1 = new Point(0, 1, 0);
        Point p2 = new Point(-1, 0, 0);
        Point p3 = new Point(1, 0, 0);
        Vector n1 = new Vector(0, 1, 0);
        Vector n2 = new Vector(-1, 0, 0);
        Vector n3 = new Vector(1, 0, 0);

        SmoothTriangle tri = create();
        assertTrue(p1.isIdentical(tri.p1));
        assertTrue(p2.isIdentical(tri.p2));
        assertTrue(p3.isIdentical(tri.p3));
        assertTrue(n1.isIdentical(tri.n1));
        assertTrue(n2.isIdentical(tri.n2));
        assertTrue(n3.isIdentical(tri.n3));
    }

    @Test
    public void intersectionWithUvTest(){
        SmoothTriangle tri = create();
        Intersection intersection = tri.intersectionWithUV(3.5, tri, 0.2, 0.4);
        assertEquals(0.2, intersection.u, 0.0001);
        assertEquals(0.4, intersection.v, 0.0001);
    }

    @Test
    public void localIntersectTest(){
        SmoothTriangle tri = create();
        Ray r = new Ray(new Point(-0.2, 0.3, -2), new Vector(0, 0, 1));
        RayIntersection xs = tri.localIntersect(r);
        assertEquals(0.45, xs.getIntersection(0).u, 0.0001);
        assertEquals(0.25, xs.getIntersection(0).v, 0.0001);
    }

    @Test
    public void normalTest(){
        SmoothTriangle tri = create();
        Intersection i =  tri.intersectionWithUV(1, tri, 0.45, 0.25);
        Vector n = tri.normalAt(new Point(0, 0, 0), i);
        assertTrue(new Vector(-0.5547, 0.83205, 0).isIdentical(n));
    }

    @Test
    public void prepareComps() {
        SmoothTriangle tri = create();
        Intersection i = tri.intersectionWithUV(1, tri, 0.45, 0.25);
        Ray r = new Ray(new Point(-0.2, 0.3, -2), new Vector(0, 0, 1));
        RayIntersection xs = new RayIntersection(1, List.of(i));
        PrecomputedIntersectionData comps = new PrecomputedIntersectionData();
        comps.compute(xs.hit(), r, xs);
        assertTrue(new Vector(-0.5547, 0.83205, 0).isIdentical(comps.normal));
    }

    private SmoothTriangle create(){
        Point p1 = new Point(0, 1, 0);
        Point p2 = new Point(-1, 0, 0);
        Point p3 = new Point(1, 0, 0);
        Vector n1 = new Vector(0, 1, 0);
        Vector n2 = new Vector(-1, 0, 0);
        Vector n3 = new Vector(1, 0, 0);

        return new SmoothTriangle(p1, p2, p3, n1, n2, n3);
    }
}
