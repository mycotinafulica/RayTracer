package org.aicl.raytracerchallenge;

import static org.junit.jupiter.api.Assertions.*;

import org.aicl.raytracerchallenge.display.Canvas;
import org.aicl.raytracerchallenge.primitives.Color;
import org.aicl.raytracerchallenge.primitives.Matrix;
import org.aicl.raytracerchallenge.primitives.Point;
import org.aicl.raytracerchallenge.primitives.Vector;
import org.aicl.raytracerchallenge.primitives.ray.Ray;
import org.aicl.raytracerchallenge.transformation.TransformMatrixGenerator;
import org.junit.jupiter.api.Test;

public class CameraTest {
    @Test
    public void cameraConstructionTest(){
        Camera c = new Camera(160, 120, Math.PI/2.0);
        assertEquals(160, c.hsize, 0.00001);
        assertEquals(120, c.vsize, 0.00001);
        assertEquals(Math.PI/2.0, c.fov, 0.00001);
        assertTrue(c.transform.isEqual(Matrix.identity()));
    }

    @Test
    public void pixelSizeCalculationTest(){
        Camera c = new Camera(200, 125, Math.PI/2.0);
        assertEquals(0.01, c.pixelSize, 0.00001);

        c = new Camera(125, 200, Math.PI/2.0);
        assertEquals(0.01, c.pixelSize, 0.00001);
    }

    @Test
    public void generatingRayForPixelTest1(){
        Camera c = new Camera(201, 101, Math.PI/2.0);
        Ray r = c.rayForPixel(100, 50);
        assertTrue(r.origin.isIdentical(new Point(0, 0, 0)));
        assertTrue(r.direction.isIdentical(new Vector(0, 0, -1)));
    }

    @Test
    public void generatingRayForPixelTest2(){
        Camera c = new Camera(201, 101, Math.PI/2.0);
        Ray r = c.rayForPixel(0, 0);
        assertTrue(r.origin.isIdentical(new Point(0, 0, 0)));
        assertTrue(r.direction.isIdentical(new Vector(0.66519, 0.33259, -0.66851)));
    }

    @Test
    public void generatingRayForPixelTest3(){
        TransformMatrixGenerator generator = new TransformMatrixGenerator();
        Camera c = new Camera(201, 101, Math.PI/2.0);
        c.transform = generator.rotateY(Math.PI/4.0).multiply(generator.translate(0, -2, 5));
        Ray r = c.rayForPixel(100, 50);
        assertTrue(r.origin.isIdentical(new Point(0, 2, -5)));
        assertTrue(r.direction.isIdentical(new Vector(Math.sqrt(2)/2.0, 0, -Math.sqrt(2)/2.0)));
    }

    @Test
    public void renderTest(){
        World w  = World.createDefault();
        Camera c = new Camera(11, 11, Math.PI/2);
        Point from = new Point(0, 0, -5);
        Point to = new Point(0, 0, 0);
        Vector up = new Vector(0, 1, 0);
        c.transform = TransformMatrixGenerator.viewTransform(from, to, up);
        Canvas image = c.render(w);
        assertTrue(image.pixelAt(5, 5).isIdentical(new Color(0.38066, 0.47583, 0.2855)));
    }
}
