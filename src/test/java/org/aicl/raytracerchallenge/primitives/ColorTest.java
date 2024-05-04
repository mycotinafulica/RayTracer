package org.aicl.raytracerchallenge.primitives;

import static org.junit.jupiter.api.Assertions.*;

import com.sun.source.tree.AssertTree;
import org.junit.jupiter.api.Test;

public class ColorTest {
    @Test
    public void testColorConstruction(){
        Color color = new Color(-0.5, 0.4, 1.7);
        assertEquals(-0.5, color.red(), 0.00001);
        assertEquals(0.4, color.green(), 0.00001);
        assertEquals(1.7, color.blue(), 0.00001);
    }

    @Test
    public void colorAddition(){
        Color c1 = new Color(0.9, 0.6, 0.75);
        Color c2 = new Color(0.7, 0.1, 0.25);
        Color target = new Color(1.6, 0.7, 1.0);

        Color newColor = c1.add(c2);
        assertTrue(target.isIdentical(newColor));
    }

    @Test
    public void colorSubtraction(){
        Color c1 = new Color(0.9, 0.6, 0.75);
        Color c2 = new Color(0.7, 0.1, 0.25);
        Color target = new Color(0.2, 0.5, 0.5);

        Color newColor = c1.subtract(c2);
        assertTrue(target.isIdentical(newColor));
    }

    @Test
    public void colorMultiplyWithScalar(){
        Color c = new Color(0.2, 0.3, 0.4);
        Color target = new Color(0.4, 0.6, 0.8);
        assertTrue(target.isIdentical(c.multiply(2)));
    }

    @Test
    public void colorMultiplyWithColor(){
        Color c1 = new Color(1, 0.2, 0.4);
        Color c2 = new Color(0.9, 1, 0.1);
        Color target = new Color(0.9, 0.2, 0.04);
        assertTrue(target.isIdentical(c1.multiply(c2)));
    }
}
