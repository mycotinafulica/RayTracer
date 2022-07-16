package org.aicl.raytracerchallenge.primitives.ray;

import static org.junit.jupiter.api.Assertions.*;

import org.aicl.raytracerchallenge.primitives.Constant;
import org.aicl.raytracerchallenge.primitives.Point;
import org.aicl.raytracerchallenge.primitives.Vector;
import org.aicl.raytracerchallenge.primitives.shape.Plane;
import org.aicl.raytracerchallenge.primitives.shape.Shape;
import org.aicl.raytracerchallenge.primitives.shape.Sphere;
import org.aicl.raytracerchallenge.transformation.TransformMatrixGenerator;
import org.junit.jupiter.api.Test;

import java.util.List;

public class PrecomputedIntersectionDataTest {
    @Test
    public void testComputation(){
        Ray ray = new Ray(new Point(0, 0, -5), new Vector(0, 0, 1));
        Sphere s = new Sphere();
        Intersection i = new Intersection(4.0, s);

        PrecomputedIntersectionData data = new PrecomputedIntersectionData();
        data.compute(i, ray, new RayIntersection(0, List.of()));
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
        data.compute(i, ray, new RayIntersection(0, List.of()));
        assertFalse(data.inside);


        ray = new Ray(new Point(0, 0, 0), new Vector(0, 0, 1));
        s = new Sphere();
        i = new Intersection(1.0, s);

        data = new PrecomputedIntersectionData();
        data.compute(i, ray, new RayIntersection(0, List.of()));
        assertTrue(data.point.isIdentical(new Point(0, 0, 1)));
        assertTrue(data.eyev.isIdentical(new Vector(0, 0, -1)));
        assertTrue(data.inside);
        assertTrue(data.normal.isIdentical(new Vector(0, 0, -1)));
    }

    @Test
    public void testPrepcomputeWithOffset(){
        TransformMatrixGenerator generator = new TransformMatrixGenerator();
        Ray ray = new Ray(new Point(0, 0, -5), new Vector(0, 0, 1));
        Shape s = new Sphere();
        s.setTransform(generator.translate(0, 0, 1));
        Intersection i = new Intersection(5., s);
        PrecomputedIntersectionData data = new PrecomputedIntersectionData();
        data.compute(i, ray, new RayIntersection(0, List.of()));
        assertTrue(data.overPoint.z < -Constant.epsilon/2.0);
        assertTrue(data.point.z > data.overPoint.z);
    }

    @Test
    public void testReflectVector(){
        Shape shape = new Plane();
        Ray ray = new Ray(new Point(0, 1, -1), new Vector(0, -Math.sqrt(2.0)/2.0, Math.sqrt(2.0)/2.0));
        Intersection i = new Intersection(Math.sqrt(2), shape);
        Vector expected = new Vector(0, Math.sqrt(2.0)/2.0, Math.sqrt(2.0)/2.0);
        PrecomputedIntersectionData comps = new PrecomputedIntersectionData();
        comps.compute(i, ray, new RayIntersection(0, List.of()));
        assertTrue(expected.isIdentical(comps.reflectv));
    }

    @Test
    public void computingRefractiveIndexAtIntersectionTest(){
        TransformMatrixGenerator generator = new TransformMatrixGenerator();
        Sphere a = Sphere.createGlassSphere();
        Sphere b = Sphere.createGlassSphere();
        Sphere c = Sphere.createGlassSphere();

        a.setTransform(generator.scale(2, 2, 2));
        a.getMaterial().refractiveIndex = 1.5;
        b.setTransform(generator.translate(0, 0, -0.25));
        b.getMaterial().refractiveIndex = 2.0;
        c.setTransform(generator.translate(0, 0 , 0.25));
        c.getMaterial().refractiveIndex = 2.5;

        Ray ray = new Ray(new Point(0, 0, -4), new Vector(0, 0, 1));
        RayIntersection intersections = new RayIntersection(
                6, List.of(
                new Intersection(2.0, a),
                new Intersection(2.75, b),
                new Intersection(3.25, c),
                new Intersection(4.75, b),
                new Intersection(5.25, c),
                new Intersection(6.0, a)
        ));

        List<Double> expectedN1 = List.of(1.0, 1.5, 2.0, 2.5, 2.5, 1.5);
        List<Double> expectedN2 = List.of(1.5, 2.0, 2.5, 2.5, 1.5, 1.0);
        for(int i = 0; i < intersections.count; i++){
            PrecomputedIntersectionData comps = new PrecomputedIntersectionData();
            comps.compute(intersections.getIntersection(i), ray, intersections);
            assertEquals(expectedN1.get((i)), comps.n1, 0.00001);
            assertEquals(expectedN2.get((i)), comps.n2, 0.00001);
        }
    }
}
