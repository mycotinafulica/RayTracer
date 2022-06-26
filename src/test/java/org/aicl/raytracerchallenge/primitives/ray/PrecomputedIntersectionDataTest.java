package org.aicl.raytracerchallenge.primitives.ray;

import static org.junit.jupiter.api.Assertions.*;

import org.aicl.raytracerchallenge.primitives.Point;
import org.aicl.raytracerchallenge.primitives.Vector;
import org.aicl.raytracerchallenge.primitives.shape.Sphere;
import org.junit.jupiter.api.Test;

public class PrecomputedIntersectionDataTest {
    @Test
    public void testComputation(){
        Ray ray = new Ray(new Point(0, 0, -5), new Vector(0, 0, 1));
        Sphere s = new Sphere();
        Intersection i = new Intersection(4.0, s);

        PrecomputedIntersectionData data = new PrecomputedIntersectionData();
        data.compute(i, ray);
        assertEquals(data.time, i.time, 0.00001);
        assertTrue(data.intersectedObj.isSame(i.intersectedShape));
        assertTrue(data.point.isIdentical(new Point(0, 0, -1)));
        assertTrue(data.eyev.isIdentical(new Vector(0, 0, -1)));
        assertTrue(data.normal.isIdentical(new Vector(0, 0, -1)));
    }

    @Test
    public void testComputationIntersectionInside(){
        Ray ray = new Ray(new Point(0, 0, -5), new Vector(0, 0, 1));
        Sphere s = new Sphere();
        Intersection i = new Intersection(4.0, s);

        PrecomputedIntersectionData data = new PrecomputedIntersectionData();
        data.compute(i, ray);
        assertFalse(data.inside);


        ray = new Ray(new Point(0, 0, 0), new Vector(0, 0, 1));
        s = new Sphere();
        i = new Intersection(1.0, s);

        data = new PrecomputedIntersectionData();
        data.compute(i, ray);
        assertTrue(data.point.isIdentical(new Point(0, 0, 1)));
        assertTrue(data.eyev.isIdentical(new Vector(0, 0, -1)));
        assertTrue(data.inside);
        assertTrue(data.normal.isIdentical(new Vector(0, 0, -1)));
    }
}
