package org.aicl.raytracerchallenge.primitives;

import static org.junit.jupiter.api.Assertions.*;

import org.aicl.raytracerchallenge.primitives.light.LightSampler;
import org.aicl.raytracerchallenge.primitives.light.PointLight;
import org.aicl.raytracerchallenge.primitives.pattern.StripePattern;
import org.aicl.raytracerchallenge.primitives.shape.Sphere;
import org.junit.jupiter.api.Test;

public class MaterialTest {
    @Test
    public void testMaterialCreation(){
        Material m = new Material();
        assertTrue(m.color.isIdentical(new Color(1, 1, 1)));
        assertEquals(0.1, m.ambient, 0.00001);
        assertEquals(0.9, m.diffuse, 0.00001);
        assertEquals(0.9, m.specular, 0.00001);
        assertEquals(200, m.shininess, 0.00001);
    }

    @Test
    public void materialWithPatternTest(){
        Material m = new Material();
        m.pattern = new StripePattern(new Color(1, 1, 1), new Color(0, 0, 0));
        m.ambient = 1;
        m.diffuse = 0;
        m.specular = 0;
        Vector eyev = new Vector(0, 0, -1);
        Vector normal = new Vector(0, 0, -1);
        PointLight light = new PointLight(new Point(0, 0,-10), new Color(1, 1, 1));
        Color c1 = LightSampler.lighting(m, new Sphere(), light, new Point(0.9, 0, 0), eyev, normal, false);
        Color c2 = LightSampler.lighting(m, new Sphere() , light, new Point(1.1, 0, 0), eyev, normal, false);
        assertTrue(c1.isIdentical(new Color(1, 1, 1)));
        assertTrue(c2.isIdentical(new Color(0, 0, 0)));
    }
}
