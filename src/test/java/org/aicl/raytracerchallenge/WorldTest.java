package org.aicl.raytracerchallenge;

import static org.junit.jupiter.api.Assertions.*;

import org.aicl.raytracerchallenge.primitives.Color;
import org.aicl.raytracerchallenge.primitives.Material;
import org.aicl.raytracerchallenge.primitives.Point;
import org.aicl.raytracerchallenge.primitives.light.PointLight;
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
}
