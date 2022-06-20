package org.aicl.raytracerchallenge.primitives;

import org.aicl.raytracerchallenge.primitives.shape.Sphere;
import org.aicl.raytracerchallenge.transformation.RayTransformer;
import org.aicl.raytracerchallenge.transformation.TransformMatrixGenerator;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class RayTest {
    @Test
    public void testRayCreation(){
        Point origin     = new Point(1, 2, 3);
        Vector direction = new Vector(4, 5, 6);

        Ray ray = new Ray(origin, direction);
        assertTrue(ray.origin.isIdentical(origin));
        assertTrue(ray.direction.isIdentical(direction));
    }

    @Test
    public void testPosition(){
        Ray ray = new Ray(new Point(2, 3, 4), new Vector(1, 0, 0));
        Point expected1 = new Point(2, 3, 4);
        Point expected2 = new Point(3, 3, 4);
        Point expected3 = new Point(1, 3, 4);
        Point expected4 = new Point(4.5, 3, 4);
        assertTrue(expected1.isIdentical(ray.position(0)));
        assertTrue(expected2.isIdentical(ray.position(1)));
        assertTrue(expected3.isIdentical(ray.position(-1)));
        assertTrue(expected4.isIdentical(ray.position(2.5)));
    }

    @Test
    public void rayIntersectionConcatenation(){
        Sphere sphere = new Sphere();
        RayIntersection i = new RayIntersection(3.5, sphere);
        assertEquals(3.5, i.time.get(0), 0.00001);
        assertTrue(i.intersectedShape.get(0).isSame(sphere));

        RayIntersection i2 = new RayIntersection(2, sphere);
        RayIntersection concatenated = i.concat(i2);
        assertEquals(2, concatenated.count);
        assertEquals(3.5, concatenated.time.get(0), 0.00001f);
        assertEquals(2, concatenated.time.get(1), 0.00001f);
    }

    @Test
    public void storingObjectInRayIntersectionTest(){
        Ray ray = new Ray(new Point(0, 0, -5), new Vector(0, 0, 1));
        Sphere sphere = new Sphere();
        RayIntersection xs = sphere.intersect(ray);
        assertEquals(2, xs.count);
        assertTrue(xs.intersectedShape.get(0).isSame(sphere));
        assertTrue(xs.intersectedShape.get(1).isSame(sphere));
    }

    @Test
    public void testHit1(){
        Sphere s = new Sphere();
        RayIntersection i1 = new RayIntersection(1, s);
        RayIntersection i2 = new RayIntersection(2, s);
        RayIntersection xs = i1.copy().concat(i2);
        assertEquals(1.0, xs.hit().time.get(0), 0.00001);
        assertTrue(i1.isEqual(xs.hit()));
    }

    @Test
    public void testHit2(){
        Sphere s = new Sphere();
        RayIntersection i1 = new RayIntersection(-1, s);
        RayIntersection i2 = new RayIntersection(1, s);
        RayIntersection xs = i1.copy().concat(i2);
        assertEquals(1.0, xs.hit().time.get(0), 0.00001);
        assertTrue(i2.isEqual(xs.hit()));
    }
    @Test
    public void testHit3(){
        Sphere s = new Sphere();
        RayIntersection i1 = new RayIntersection(-2, s);
        RayIntersection i2 = new RayIntersection(-1, s);
        RayIntersection xs = i1.copy().concat(i2);
        assertNull(xs.hit());
    }

    @Test
    public void testHit4(){
        Sphere s = new Sphere();
        RayIntersection i1 = new RayIntersection(5, s);
        RayIntersection i2 = new RayIntersection(7, s);
        RayIntersection i3 = new RayIntersection(-3, s);
        RayIntersection i4 = new RayIntersection(2, s);
        RayIntersection xs = i1.copy().concat(i2).concat(i3).concat(i4);
        assertEquals(2.0, xs.hit().time.get(0), 0.00001);
        assertTrue(i4.isEqual(xs.hit()));
    }

    @Test
    public void rayTransformTranslate(){
        TransformMatrixGenerator generator = new TransformMatrixGenerator();
        Ray ray = new Ray(new Point(1, 2, 3), new Vector(0, 1, 0));
        Matrix m = generator.translate(3, 4, 5);

        Point  expectedNewOrigin    = new Point(4, 6, 8);
        Vector expectedNewDirection = new Vector(0, 1, 0);
        Ray newRay = RayTransformer.transform(ray, m, RayTransformer.Type.TRANSLATION);
        assertTrue(expectedNewOrigin.isIdentical(newRay.origin));
        assertTrue(expectedNewDirection.isIdentical(newRay.direction));
    }

    @Test
    public void rayTransformScale(){
        TransformMatrixGenerator generator = new TransformMatrixGenerator();
        Ray ray = new Ray(new Point(1, 2, 3), new Vector(0, 1, 0));
        Matrix m = generator.scale(2, 3, 4);

        Point  expectedNewOrigin    = new Point(2, 6, 12);
        Vector expectedNewDirection = new Vector(0, 3, 0);
        Ray newRay = RayTransformer.transform(ray, m, RayTransformer.Type.SCALING);
        assertTrue(expectedNewOrigin.isIdentical(newRay.origin));
        assertTrue(expectedNewDirection.isIdentical(newRay.direction));
    }
}
