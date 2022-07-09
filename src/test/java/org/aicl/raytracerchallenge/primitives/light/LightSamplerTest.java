package org.aicl.raytracerchallenge.primitives.light;

import org.aicl.raytracerchallenge.primitives.Color;
import org.aicl.raytracerchallenge.primitives.Material;
import org.aicl.raytracerchallenge.primitives.Point;
import org.aicl.raytracerchallenge.primitives.Vector;
import static org.junit.jupiter.api.Assertions.*;

import org.aicl.raytracerchallenge.primitives.shape.Sphere;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class LightSamplerTest {
    private static Material m;
    private static Point position;

    @BeforeAll
    static void init(){
        m = new Material();
        position = new Point(0, 0 ,0);
    }

    //illustrations on page 86 - 87
    @Test
    public void testEyeDirectlyBetweenLightAndSurface(){
        Vector eyev = new Vector(0, 0, -1);
        Vector normal = new Vector(0, 0, -1);
        PointLight light = new PointLight(new Point(0, 0, -10), new Color(1, 1, 1));

        Color result = LightSampler.lighting(m, new Sphere(), light, position, eyev, normal, false);
        assertTrue(result.isIdentical(new Color(1.9, 1.9, 1.9)));
    }

    @Test
    public void testEyeRotated45Degree(){
        Vector eyev = new Vector(0, Math.sqrt(2)/2.0, -Math.sqrt(2)/2.0);
        Vector normal = new Vector(0, 0, -1);
        PointLight light = new PointLight(new Point(0, 0, -10), new Color(1, 1, 1));

        Color result = LightSampler.lighting(m, new Sphere(), light, position, eyev, normal, false);
        assertTrue(result.isIdentical(new Color(1.0, 1.0, 1.0)));
    }

    @Test
    public void testLightRotated45Degree(){
        Vector eyev = new Vector(0, 0, -1);
        Vector normal = new Vector(0, 0, -1);
        PointLight light = new PointLight(new Point(0, 10, -10), new Color(1, 1, 1));

        Color result = LightSampler.lighting(m, new Sphere(), light, position, eyev, normal, false);
        assertTrue(result.isIdentical(new Color(0.7364, 0.7364, 0.7364)));
    }

    @Test
    public void testEyeDirectlyOnReflection(){
        Vector eyev = new Vector(0, -Math.sqrt(2)/2.0, -Math.sqrt(2)/2.0);
        Vector normal = new Vector(0, 0, -1);
        PointLight light = new PointLight(new Point(0, 10, -10), new Color(1, 1, 1));

        Color result = LightSampler.lighting(m, new Sphere(), light, position, eyev, normal, false);
        assertTrue(result.isIdentical(new Color(1.6364, 1.6364, 1.6364)));
    }

    @Test
    public void testLightBehindSurface(){
        Vector eyev = new Vector(0, 0, -1);
        Vector normal = new Vector(0, 0, -1);
        PointLight light = new PointLight(new Point(0, 10, 10), new Color(1, 1, 1));

        Color result = LightSampler.lighting(m, new Sphere(), light, position, eyev, normal, false);
        assertTrue(result.isIdentical(new Color(0.1, 0.1, 0.1)));
    }

    @Test
    public void shadowedRegionTest(){
        Vector eyev = new Vector(0, 0, -1);
        Vector normal = new Vector(0, 0, -1);
        PointLight light = new PointLight(new Point(0, 0, -10), new Color(1, 1, 1));
        Color result = LightSampler.lighting(m, new Sphere(), light, position, eyev, normal, true);
        assertTrue(result.isIdentical(new Color(0.1, 0.1, 0.1)));
    }
}
