package org.aicl.raytracerchallenge.computation;

import org.aicl.raytracerchallenge.primitives.Point;
import org.aicl.raytracerchallenge.primitives.Vector;
import org.aicl.raytracerchallenge.primitives.ray.Intersection;
import org.aicl.raytracerchallenge.primitives.ray.PrecomputedIntersectionData;
import org.aicl.raytracerchallenge.primitives.ray.Ray;
import org.aicl.raytracerchallenge.primitives.ray.RayIntersection;
import org.aicl.raytracerchallenge.primitives.shape.Shape;
import org.aicl.raytracerchallenge.primitives.shape.Sphere;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

import java.util.List;

public class FresnelEffectTest {
    @Test
    public void schlickWhenTotalInternalReflectionTest(){
        Shape shape = Sphere.createGlassSphere();
        Ray ray = new Ray(new Point(0, 0, Math.sqrt(2.)/2.0), new Vector(0, 1, 0));
        RayIntersection intersections = new RayIntersection(2, List.of(
                new Intersection(-Math.sqrt(2.)/2.0, shape),
                new Intersection(Math.sqrt(2.)/2.0, shape)
        ));
        PrecomputedIntersectionData comps = new PrecomputedIntersectionData();
        comps.compute(intersections.hit(), ray, intersections);
        double reflectance = FresnelEffect.schlick(comps);
        assertEquals(1.0, reflectance, 0.00001);
    }

    @Test
    public void schlickWithPerpendicularAngle(){
        Shape shape = Sphere.createGlassSphere();
        Ray ray = new Ray(new Point(0, 0, 0), new Vector(0, 1, 0));
        RayIntersection intersections = new RayIntersection(2, List.of(
                new Intersection(-1, shape),
                new Intersection(1, shape)
        ));
        PrecomputedIntersectionData comps = new PrecomputedIntersectionData();
        comps.compute(intersections.hit(), ray, intersections);
        double reflectance = FresnelEffect.schlick(comps);
        assertEquals(0.04, reflectance, 0.00001);
    }

    @Test
    public void schlickOnSmallAngleAndN2GreaterN1Test(){
        Shape shape = Sphere.createGlassSphere();
        Ray ray = new Ray(new Point(0, 0.99, -2), new Vector(0, 0, 1));
        RayIntersection intersections = new RayIntersection(1, List.of(
                new Intersection(1.8589, shape)
        ));
        PrecomputedIntersectionData comps = new PrecomputedIntersectionData();
        comps.compute(intersections.hit(), ray, intersections);
        double reflectance = FresnelEffect.schlick(comps);
        assertEquals(0.48873, reflectance, 0.00001);
    }
}
