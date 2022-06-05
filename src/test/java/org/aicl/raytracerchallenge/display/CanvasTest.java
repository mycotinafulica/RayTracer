package org.aicl.raytracerchallenge.display;

import org.aicl.raytracerchallenge.primitives.Color;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

public class CanvasTest {
    @Test
    public void testInitialization(){
        Canvas c = new Canvas(10, 20);
        Color initialColor = new Color(0, 0, 0);
        List<Color> pixelsInCanvas = c.getAllPixelsAsList();
        for(int i = 0 ; i < pixelsInCanvas.size() ; i++){
            assertTrue(pixelsInCanvas.get(i).isIdentical(initialColor));
        }
    }

    @Test
    public void testSetColor(){
        Canvas c = new Canvas(10, 20);
        Color red = new Color(1, 0, 0);
        c.writePixel(2, 3, red);
        assertTrue(c.pixelAt(2, 3).isIdentical(red));
    }
}
