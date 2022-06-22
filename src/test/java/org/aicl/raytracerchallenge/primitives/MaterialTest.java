package org.aicl.raytracerchallenge.primitives;

import static org.junit.jupiter.api.Assertions.*;
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
}
