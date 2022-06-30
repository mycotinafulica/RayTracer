package org.aicl.raytracerchallenge;

import static org.junit.jupiter.api.Assertions.*;

import org.aicl.raytracerchallenge.primitives.*;
import org.aicl.raytracerchallenge.primitives.light.PointLight;
import org.aicl.raytracerchallenge.primitives.ray.Intersection;
import org.aicl.raytracerchallenge.primitives.ray.PrecomputedIntersectionData;
import org.aicl.raytracerchallenge.primitives.ray.Ray;
import org.aicl.raytracerchallenge.primitives.ray.RayIntersection;
import org.aicl.raytracerchallenge.primitives.shape.Shape;
import org.aicl.raytracerchallenge.primitives.shape.Sphere;
import org.aicl.raytracerchallenge.transformation.TransformMatrixGenerator;
import org.junit.jupiter.api.Test;

public class WorldTest {
    @Test
    public void testEmptyWorld(){
        World world = new World();
        assertEquals(0, world.lights.size());
        assertEquals(0, world.shapes.size());
    }

    @Test
    public void testCreateDefaultWorld(){
        World world = World.createDefault();
        PointLight light = new PointLight(new Point(-10, 10, -10), new Color(1, 1, 1));
        Sphere s1  = new Sphere();
        Material m = new Material();
        m.color    = new Color(0.8, 1.0, 0.6);
        m.diffuse  = 0.7;
        m.specular = 0.2;
        s1.setMaterial(m);

        TransformMatrixGenerator generator = new TransformMatrixGenerator();
        Sphere s2 = new Sphere();
        s2.setTransform(generator.scale(0.5, 0.5, 0.5));

        assertTrue(world.containsLight(light));
        assertTrue(world.containsObject(s1));
        assertTrue(world.containsObject(s2));
    }

    @Test
    public void intersectionRayOnDefaultWorldTest(){
        Ray r   = new Ray(new Point(0, 0, -5), new Vector(0, 0, 1));
        World w = World.createDefault();
        RayIntersection intersection = w.intersect(r, true);
//        intersection.sort();
        assertEquals(4, intersection.count);
        assertEquals(4, intersection.getTime(0), 0.00001);
        assertEquals(4.5, intersection.getTime(1), 0.00001);
        assertEquals(5.5, intersection.getTime(2), 0.00001);
        assertEquals(6, intersection.getTime(3), 0.00001);
    }

    @Test
    public void testShadingIntersection(){
        World w = World.createDefault();
        Ray ray = new Ray(new Point(0, 0, -5), new Vector(0, 0, 1));
        Shape obj1 = w.getObject(0);
        Intersection i = new Intersection(4.0, obj1);
        PrecomputedIntersectionData data = new PrecomputedIntersectionData();
        data.compute(i, ray);
        Color result = w.shadeHit(data);
        Color expected = new Color(0.38066, 0.47583, 0.2855);
        assertTrue(result.isIdentical(expected));
    }

    @Test
    public void testShadingIntersectionFromInside(){
        World w = World.createDefault();
        w.setLight(new PointLight(new Point(0, 0.25, 0), new Color(1, 1, 1)), 0);
        Ray ray = new Ray(new Point(0, 0, 0), new Vector(0, 0, 1));
        Shape obj1 = w.getObject(1);
        Intersection i = new Intersection(0.5, obj1);
        PrecomputedIntersectionData data = new PrecomputedIntersectionData();
        data.compute(i, ray);
        Color result = w.shadeHit(data);
        Color expected = new Color(0.90498, 0.90498, 0.90498);
        assertTrue(result.isIdentical(expected));
    }

    @Test
    public void testWordColorAtCase1(){
        World w = World.createDefault();
        Ray ray = new Ray(new Point(0, 0, -5), new Vector(0, 1, 0));
        Color c = w.worldColorAtRay(ray);
        Color expected = new Color(0, 0, 0);
        assertTrue(expected.isIdentical(c));
    }

    @Test
    public void testWordColorAtCase2(){
        World w = World.createDefault();
        Ray ray = new Ray(new Point(0, 0, -5), new Vector(0, 0, 1));
        Color c = w.worldColorAtRay(ray);
        Color expected = new Color(0.38066, 0.47583, 0.2855);
        assertTrue(expected.isIdentical(c));
    }

    @Test
    public void testWordColorAtCase3(){
        World w = World.createDefault();
        Shape outer = w.getObject(0);
        outer.getMaterial().ambient = 1;
        Shape inner = w.getObject(1);
        inner.getMaterial().ambient = 1;

        Ray ray = new Ray(new Point(0, 0, 0.75), new Vector(0, 0, -1));
        Color c = w.worldColorAtRay(ray);
        assertTrue(inner.getMaterial().color.isIdentical(c));
    }

    @Test
    public void isInShadowTest1(){
        World w = World.createDefault();
        Point p = new Point(0, 10, 0);
        assertFalse(w.isInShadow(p));
    }

    @Test
    public void isInShadowTest2(){
        World w = World.createDefault();
        Point p = new Point(10, -10, 10);
        assertTrue(w.isInShadow(p));
    }

    @Test
    public void isInShadowTest3(){
        World w = World.createDefault();
        Point p = new Point(-20, 20, -20);
        assertFalse(w.isInShadow(p));
    }

    @Test
    public void isInShadowTest4(){
        World w = World.createDefault();
        Point p = new Point(-2, 2, -2);
        assertFalse(w.isInShadow(p));
    }

    @Test
    public void shadeHitGivenIntersection(){
        TransformMatrixGenerator generator = new TransformMatrixGenerator();
        World w = new World();
        w.addLight(new PointLight(new Point(0, 0, -10), new Color(1, 1, 1)));
        Sphere s1 = new Sphere();
        w.addObject(s1);
        Sphere s2 = new Sphere();
        s2.setTransform(generator.translate(0, 0, 10));
        w.addObject(s2);
        Ray r = new Ray(new Point(0, 0, 5), new Vector(0, 0, 1));
        Intersection i = new Intersection(4.0, s2);
        PrecomputedIntersectionData data = new PrecomputedIntersectionData();
        data.compute(i, r);
        Color c = w.shadeHit(data);
        assertTrue(c.isIdentical(new Color(0.1, 0.1, 0.1)));
    }
}
