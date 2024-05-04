package org.aicl.raytracerchallenge.primitives.light;

import org.aicl.raytracerchallenge.primitives.Color;
import org.aicl.raytracerchallenge.primitives.Point;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

public class PointLightTest {
    @Test
    public void testLightConstruction(){
        Point position  = new Point(0, 0,0 );
        Color intensity = new Color(1, 1, 1);

        PointLight light = new PointLight(position, intensity);
        assertTrue(position.isIdentical(light.position));
        assertTrue(intensity.isIdentical(light.intensity));
    }
}
