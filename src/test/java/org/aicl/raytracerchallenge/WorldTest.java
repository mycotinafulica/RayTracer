package org.aicl.raytracerchallenge;

import static org.junit.jupiter.api.Assertions.*;

import org.aicl.raytracerchallenge.primitives.*;
import org.aicl.raytracerchallenge.primitives.light.PointLight;
import org.aicl.raytracerchallenge.primitives.pattern.TestPattern;
import org.aicl.raytracerchallenge.primitives.ray.Intersection;
import org.aicl.raytracerchallenge.primitives.ray.PrecomputedIntersectionData;
import org.aicl.raytracerchallenge.primitives.ray.Ray;
import org.aicl.raytracerchallenge.primitives.ray.RayIntersection;
import org.aicl.raytracerchallenge.primitives.shape.Plane;
import org.aicl.raytracerchallenge.primitives.shape.Shape;
import org.aicl.raytracerchallenge.primitives.shape.Sphere;
import org.aicl.raytracerchallenge.transformation.TransformMatrixGenerator;
import org.junit.jupiter.api.Test;

import java.util.List;

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
        RayIntersection intersection = w.intersect(r);
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
        data.compute(i, ray, new RayIntersection(0, List.of()));
        Color result = w.shadeHit(data, 0);
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
        data.compute(i, ray, new RayIntersection(0, List.of()));
        Color result = w.shadeHit(data,0);
        Color expected = new Color(0.90498, 0.90498, 0.90498);
        System.out.println(result.toTuple().toString());
        assertTrue(result.isIdentical(expected));
    }

    @Test
    public void testWordColorAtCase1(){
        World w = World.createDefault();
        Ray ray = new Ray(new Point(0, 0, -5), new Vector(0, 1, 0));
        Color c = w.worldColorAtRay(ray, 0);
        Color expected = new Color(0, 0, 0);
        assertTrue(expected.isIdentical(c));
    }

    @Test
    public void testWordColorAtCase2(){
        World w = World.createDefault();
        Ray ray = new Ray(new Point(0, 0, -5), new Vector(0, 0, 1));
        Color c = w.worldColorAtRay(ray, 0);
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
        Color c = w.worldColorAtRay(ray, 0);
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
        data.compute(i, r, new RayIntersection(0, List.of()));
        Color c = w.shadeHit(data, 0);
        assertTrue(c.isIdentical(new Color(0.1, 0.1, 0.1)));
    }

    @Test
    public void testNonReflectiveObject(){
        World w = World.createDefault();
        Ray ray = new Ray(new Point(0, 0, 0), new Vector(0, 0, 1));
        Shape shape = w.getObject(1);
        shape.getMaterial().ambient = 1;
        Intersection i = new Intersection(1., shape);
        PrecomputedIntersectionData comps = new PrecomputedIntersectionData();
        comps.compute(i, ray, new RayIntersection(0, List.of()));
        assertTrue(new Color(0, 0, 0).isIdentical(w.reflectedColor(comps, 5)));
    }

    @Test
    public void testReflectiveObject(){
        TransformMatrixGenerator generator = new TransformMatrixGenerator();
        World w = World.createDefault();
        Shape shape = new Plane();
        shape.getMaterial().reflective = 0.5;
        shape.setTransform(generator.translate(0, -1, 0));

        w.addObject(shape);
        Ray ray = new Ray(new Point(0, 0, -3), new Vector(0, -Math.sqrt(2)/2.0, Math.sqrt(2)/2.0));
        Intersection i = new Intersection(Math.sqrt(2), shape);
        PrecomputedIntersectionData comps = new PrecomputedIntersectionData();
        comps.compute(i, ray, new RayIntersection(0, List.of()));
        Color color = w.reflectedColor(comps, 5);
        assertTrue(new Color(0.19033, 0.23791, 0.14274).isIdentical(color));
    }

    @Test
    public void shadeHitWithReflectiveObject(){
        TransformMatrixGenerator generator = new TransformMatrixGenerator();
        World w = World.createDefault();
        Shape shape = new Plane();
        shape.getMaterial().reflective = 0.5;
        shape.setTransform(generator.translate(0, -1, 0));

        Ray ray = new Ray(new Point(0, 0, -3), new Vector(0, -Math.sqrt(2)/2.0, Math.sqrt(2)/2.0));
        Intersection i = new Intersection(Math.sqrt(2), shape);
        PrecomputedIntersectionData comps = new PrecomputedIntersectionData();
        comps.compute(i, ray, new RayIntersection(0, List.of()));
        Color color = w.shadeHit(comps, 5);
        assertTrue(new Color(0.87675, 0.92434, 0.82918).isIdentical(color));
    }

    @Test
    public void infiniteBounces(){
        TransformMatrixGenerator generator = new TransformMatrixGenerator();
        World w = new World();
        w.addLight(new PointLight(new Point(0, 0, 0), new Color(1, 1, 1)));
        Shape lowerPlane = new Plane();
        lowerPlane.getMaterial().reflective = 1;
        lowerPlane.setTransform(generator.translate(0, -1, 0));

        Shape upperPlane = new Plane();
        upperPlane.getMaterial().reflective = 1;
        upperPlane.setTransform(generator.translate(0, 1, 0));

        w.addObject(lowerPlane);
        w.addObject(upperPlane);

        Ray ray = new Ray(new Point(0, 0, 0), new Vector(0, 1, 0));
        w.worldColorAtRay(ray, 5);
    }

    @Test
    public void testReflectiveObjectButRemainingZero(){
        TransformMatrixGenerator generator = new TransformMatrixGenerator();
        World w = World.createDefault();
        Shape shape = new Plane();
        shape.getMaterial().reflective = 0.5;
        shape.setTransform(generator.translate(0, -1, 0));

        w.addObject(shape);
        Ray ray = new Ray(new Point(0, 0, -3), new Vector(0, -Math.sqrt(2)/2.0, Math.sqrt(2)/2.0));
        Intersection i = new Intersection(Math.sqrt(2), shape);
        PrecomputedIntersectionData comps = new PrecomputedIntersectionData();
        comps.compute(i, ray, new RayIntersection(0, List.of()));
        Color color = w.reflectedColor(comps, 0);
        assertTrue(new Color(0, 0, 0).isIdentical(color));
    }

    @Test
    public void refractedColorOnOpaqueSurface(){
        World w = World.createDefault();
        Shape obj = w.getObject(0);
        Ray ray = new Ray(new Point(0, 0, -5), new Vector(0, 0, 1));
        RayIntersection intersections = new RayIntersection(2,
                List.of(
                        new Intersection(4., obj),
                        new Intersection(6., obj)
                ));

        PrecomputedIntersectionData comps = new PrecomputedIntersectionData();
        comps.compute(intersections.getIntersection(0), ray, intersections);
        Color c = w.refractedColor(comps, 5);
        assertTrue(new Color(0, 0,0).isIdentical(c));
    }

    @Test
    public void refractedColorAtMaxDepthTest(){
        World w = World.createDefault();
        Shape obj = w.getObject(0);
        obj.getMaterial().transparency = 1.0;
        obj.getMaterial().refractiveIndex = 1.5;
        Ray ray = new Ray(new Point(0, 0, -5), new Vector(0, 0, 1));
        RayIntersection intersections = new RayIntersection(2,
                List.of(
                        new Intersection(4., obj),
                        new Intersection(6., obj)
                ));

        PrecomputedIntersectionData comps = new PrecomputedIntersectionData();
        comps.compute(intersections.getIntersection(0), ray, intersections);
        Color c = w.refractedColor(comps, 0);
        assertTrue(new Color(0, 0,0).isIdentical(c));
    }

    @Test
    public void totalInternalReflectionTest(){
        World w = World.createDefault();
        Shape shape = w.getObject(0);
        shape.getMaterial().transparency = 1.0;
        shape.getMaterial().refractiveIndex = 1.5;

        Ray ray = new Ray(new Point(0, 0, Math.sqrt(2)/2.0), new Vector(0, 1, 0));
        RayIntersection intersections = new RayIntersection(2,
                List.of(
                        new Intersection(-Math.sqrt(2)/2.0, shape),
                        new Intersection(Math.sqrt(2)/2.0, shape)
                ));
        PrecomputedIntersectionData comps = new PrecomputedIntersectionData();
        comps.compute(intersections.hit(), ray, intersections);
        Color c = w.refractedColor(comps, 5);
        assertTrue(new Color(0, 0, 0).isIdentical(c));
    }

    @Test
    public void nonBlackRefractedColorTest(){
        World w = World.createDefault();
        Shape a = w.getObject(0);

        a.getMaterial().ambient = 1.0;
        a.getMaterial().pattern = new TestPattern();

        Shape b = w.getObject(1);
        b.getMaterial().transparency = 1.0;
        b.getMaterial().refractiveIndex = 1.5;

        Ray r = new Ray(new Point(0, 0, 0.1), new Vector(0, 1, 0));
        RayIntersection intersections = new RayIntersection(4, List.of(
           new Intersection(-0.9899, a),
           new Intersection(-0.4899, b),
           new Intersection(0.4899, b),
           new Intersection(0.9899, a)
        ));

        PrecomputedIntersectionData comps = new PrecomputedIntersectionData();
        comps.compute(intersections.hit(), r, intersections);
        Color c = w.refractedColor(comps, 5);
        assertTrue(c.isIdentical(new Color(0, 0.99888, 0.04721)));
    }

    @Test
    public void shadeHitOnRefractionTest() {
        TransformMatrixGenerator generator = new TransformMatrixGenerator();
        World w = World.createDefault();
        Shape floor = new Plane();
        floor.setTransform(generator.translate(0, -1, 0));
        floor.getMaterial().transparency = 0.5;
        floor.getMaterial().refractiveIndex = 1.5;
        w.addObject(floor);

        Shape ball = new Sphere();
        ball.getMaterial().color = new Color(1, 0, 0);
        ball.getMaterial().ambient = 0.5;
        ball.setTransform(generator.translate(0, -3.5, -0.5));
        w.addObject(ball);

        Ray ray = new Ray(new Point(0, 0, -3), new Vector(0, -Math.sqrt(2.0)/2.0, Math.sqrt(2.0)/2.0));
        RayIntersection intersections = new RayIntersection(1, List.of(new Intersection(Math.sqrt(2.0), floor)));
        PrecomputedIntersectionData comps = new PrecomputedIntersectionData();
        comps.compute(intersections.hit(), ray, intersections);
        Color c = w.shadeHit(comps,5);
        assertTrue(c.isIdentical(new Color(0.93642, 0.68642, 0.68642)));
    }

    @Test
    public void shadeHitWithReflectiveTransparentMaterial() {
        TransformMatrixGenerator generator = new TransformMatrixGenerator();
        World w = World.createDefault();
        Shape floor = new Plane();
        floor.setTransform(generator.translate(0, -1, 0));
        floor.getMaterial().reflective = 0.5;
        floor.getMaterial().transparency = 0.5;
        floor.getMaterial().refractiveIndex = 1.5;
        w.addObject(floor);

        Shape ball = new Sphere();
        ball.getMaterial().color = new Color(1, 0, 0);
        ball.getMaterial().ambient = 0.5;
        ball.setTransform(generator.translate(0, -3.5, -0.5));
        w.addObject(ball);

        Ray ray = new Ray(new Point(0, 0, -3), new Vector(0, -Math.sqrt(2.0)/2.0, Math.sqrt(2.0)/2.0));
        RayIntersection intersections = new RayIntersection(1, List.of(new Intersection(Math.sqrt(2.0), floor)));
        PrecomputedIntersectionData comps = new PrecomputedIntersectionData();
        comps.compute(intersections.hit(), ray, intersections);
        Color c = w.shadeHit(comps,5);
        assertTrue(c.isIdentical(new Color(0.93391, 0.69643, 0.69243)));
    }
}
