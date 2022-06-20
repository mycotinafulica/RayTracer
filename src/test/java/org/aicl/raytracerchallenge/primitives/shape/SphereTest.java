package org.aicl.raytracerchallenge.primitives.shape;

import org.aicl.raytracerchallenge.primitives.*;
import org.aicl.raytracerchallenge.transformation.RayTransformer;
import org.aicl.raytracerchallenge.transformation.TransformMatrixGenerator;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class SphereTest {
    @Test
    public void testAssigningTransformToSphere(){
        TransformMatrixGenerator generator = new TransformMatrixGenerator();
        Sphere sphere = new Sphere();
        assertTrue(Matrix.identity().isEqual(sphere.getTransform()));
        Matrix translation = generator.translate(2, 3, 4);
        sphere.setTransform(translation, RayTransformer.Type.TRANSLATION);

        assertTrue(translation.isEqual(sphere.getTransform()));
    }

    @Test
    public void testScaledIntersection(){
        TransformMatrixGenerator generator = new TransformMatrixGenerator();
        Ray ray = new Ray(new Point(0, 0, -5), new Vector(0, 0, 1));
        Sphere sphere = new Sphere();
        Matrix scaleMtx = generator.scale(2, 2, 2);
        sphere.setTransform(scaleMtx, RayTransformer.Type.SCALING);

        RayIntersection intersection = sphere.intersect(ray);
        assertEquals(2, intersection.count);
        assertEquals(3, intersection.time.get(0), .000001);
        assertEquals(7, intersection.time.get(1), .000001);
    }

    @Test
    public void testTranslatedIntersection(){
        TransformMatrixGenerator generator = new TransformMatrixGenerator();
        Ray ray = new Ray(new Point(0, 0, -5), new Vector(0, 0, 1));
        Sphere sphere = new Sphere();
        Matrix scaleMtx = generator.translate(5, 0, 0);
        sphere.setTransform(scaleMtx, RayTransformer.Type.TRANSLATION);

        RayIntersection intersection = sphere.intersect(ray);
        assertEquals(0, intersection.count);
    }

    @Test
    public void raySphereIntersectionAtTwoPointTest(){
        Ray ray = new Ray(new Point(0, 0, -5), new Vector(0, 0, 1));
        Sphere sphere = new Sphere();
        RayIntersection xs = sphere.intersect(ray);
        assertEquals(2, xs.count);
        assertEquals(4.0, xs.time.get(0), 0.0001);
        assertEquals(6.0, xs.time.get(1), 0.0001);
    }

    @Test
    public void raySphereIntersectionAtTangentTest(){
        Ray ray = new Ray(new Point(0, 1, -5), new Vector(0, 0, 1));
        Sphere sphere = new Sphere();
        RayIntersection xs = sphere.intersect(ray);
        assertEquals(2, xs.count);
        assertEquals(5.0, xs.time.get(0), 0.0001);
        assertEquals(5.0, xs.time.get(1), 0.0001);
    }

    @Test
    public void raySphereIntersectionMissCaseTest(){
        Ray ray = new Ray(new Point(0, 2, -5), new Vector(0, 0, 1));
        Sphere sphere = new Sphere();
        RayIntersection xs = sphere.intersect(ray);
        assertEquals(0, xs.count);
    }

    @Test
    public void raySphereIntersectionRayOriginOnSphereTest(){
        Ray ray = new Ray(new Point(0, 0, 0), new Vector(0, 0, 1));
        Sphere sphere = new Sphere();
        RayIntersection xs = sphere.intersect(ray);
        assertEquals(2, xs.count);
        assertEquals(-1.0, xs.time.get(0), 0.0001);
        assertEquals(1.0, xs.time.get(1), 0.0001);
    }

    @Test
    public void raySphereIntersectionSphereBehindRayTest(){
        Ray ray = new Ray(new Point(0, 0, 5), new Vector(0, 0, 1));
        Sphere sphere = new Sphere();
        RayIntersection xs = sphere.intersect(ray);
        assertEquals(2, xs.count);
        assertEquals(-6.0, xs.time.get(0), 0.0001);
        assertEquals(-4.0, xs.time.get(1), 0.0001);
    }
}
