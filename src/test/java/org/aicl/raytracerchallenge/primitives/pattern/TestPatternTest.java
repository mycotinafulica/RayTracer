package org.aicl.raytracerchallenge.primitives.pattern;

import org.aicl.raytracerchallenge.primitives.Color;
import org.aicl.raytracerchallenge.primitives.Point;
import org.aicl.raytracerchallenge.primitives.shape.Shape;
import org.aicl.raytracerchallenge.primitives.shape.Sphere;
import org.aicl.raytracerchallenge.transformation.TransformMatrixGenerator;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

public class TestPatternTest {
    @Test
    public void testPattern1(){
        TransformMatrixGenerator generator = new TransformMatrixGenerator();
        Shape shape = new Sphere();
        shape.setTransform(generator.scale(2, 2, 2));
        BasePattern testPattern = new TestPattern();
        Color c = testPattern.patternAtObject(shape, new Point(2, 3,4));
        assertTrue(new Color(1, 1.5, 2).isIdentical(c));
    }

    @Test
    public void testPattern2(){
        TransformMatrixGenerator generator = new TransformMatrixGenerator();
        Shape shape = new Sphere();
        BasePattern testPattern = new TestPattern();
        testPattern.setTransform(generator.scale(2, 2, 2));
        Color c = testPattern.patternAtObject(shape, new Point(2, 3,4));
        assertTrue(new Color(1, 1.5, 2).isIdentical(c));
    }

    @Test
    public void testPattern3(){
        TransformMatrixGenerator generator = new TransformMatrixGenerator();
        Shape shape = new Sphere();
        shape.setTransform(generator.scale(2, 2, 2));
        BasePattern testPattern = new TestPattern();
        testPattern.setTransform(generator.translate(0.5, 1, 1.5));
        Color c = testPattern.patternAtObject(shape, new Point(2.5, 3, 3.5));
        assertTrue(new Color(0.75, 0.5, 0.25).isIdentical(c));
    }
}
