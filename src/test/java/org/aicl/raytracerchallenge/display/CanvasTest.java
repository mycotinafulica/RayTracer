package org.aicl.raytracerchallenge.display;

import org.aicl.raytracerchallenge.primitives.Color;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
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

    @Test
    public void testWriteToPpmFile(){
        Canvas c = new Canvas(5, 3);
        Color c1 = new Color(1.5, 0, 0);
        Color c2 = new Color(0, 0.5, 0);
        Color c3 = new Color(-0.5, 0, 1);

        c.writePixel(0, 0, c1);
        c.writePixel(2, 1, c2);
        c.writePixel(4, 2, c3);

        try {
            c.writeToPpmFile("E:\\William\\HighImportance\\GIT_ROOT\\RayTracerChallenge\\RayTracerChallenge\\test\\test_ppm_write.ppm");
            ArrayList<String> expected = new ArrayList<>();
            //header
            expected.add("P3");
            expected.add("5 3");
            expected.add("255");
            expected.add("255 0 0 0 0 0 0 0 0 0 0 0 0 0 0");
            expected.add("0 0 0 0 0 0 0 128 0 0 0 0 0 0 0");
            expected.add("0 0 0 0 0 0 0 0 0 0 0 0 0 0 255");
            BufferedReader br = new BufferedReader(new FileReader("E:\\William\\HighImportance" +
                    "\\GIT_ROOT\\RayTracerChallenge\\RayTracerChallenge\\test\\test_ppm_write.ppm"));
            String line;
            int lineNumber = 0;
            while ((line = br.readLine()) != null) {
                assertEquals(expected.get(lineNumber), line);
                lineNumber++;
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void splittingLongLinesInPpm(){
        Canvas c = new Canvas(10, 2);
        c.setAllPixelColors(new Color(1, 0.8, 0.6));

        try {
            c.writeToPpmFile("E:\\William\\HighImportance\\GIT_ROOT\\RayTracerChallenge\\RayTracerChallenge\\test\\test_ppm_write2.ppm");
            ArrayList<String> expected = new ArrayList<>();
            //header
            expected.add("P3");
            expected.add("10 2");
            expected.add("255");
            expected.add("255 204 153 255 204 153 255 204 153 255 204 153 255 204 153 255 204");
            expected.add("153 255 204 153 255 204 153 255 204 153 255 204 153");
            expected.add("255 204 153 255 204 153 255 204 153 255 204 153 255 204 153 255 204");
            expected.add("153 255 204 153 255 204 153 255 204 153 255 204 153");
            BufferedReader br = new BufferedReader(new FileReader("E:\\William\\HighImportance" +
                    "\\GIT_ROOT\\RayTracerChallenge\\RayTracerChallenge\\test\\test_ppm_write2.ppm"));
            String line;
            int lineNumber = 0;
            while ((line = br.readLine()) != null) {
                assertEquals(expected.get(lineNumber), line);
                lineNumber++;
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
