package org.aicl.raytracerchallenge;

import static org.junit.jupiter.api.Assertions.*;

import org.aicl.raytracerchallenge.primitives.*;
import org.aicl.raytracerchallenge.primitives.light.PointLight;
import org.aicl.raytracerchallenge.primitives.ray.Ray;
import org.aicl.raytracerchallenge.primitives.ray.RayIntersection;
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
}
