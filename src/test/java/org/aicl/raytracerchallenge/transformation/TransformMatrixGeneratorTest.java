package org.aicl.raytracerchallenge.transformation;

import org.aicl.raytracerchallenge.primitives.Matrix;
import org.aicl.raytracerchallenge.primitives.Point;
import org.aicl.raytracerchallenge.primitives.Tuple;
import static org.junit.jupiter.api.Assertions.*;

import org.aicl.raytracerchallenge.primitives.Vector;
import org.junit.jupiter.api.Test;

public class TransformMatrixGeneratorTest {
    @Test
    public void testTranslation(){
        TransformMatrixGenerator generator = new TransformMatrixGenerator();
        Matrix transform = generator.translate(5, -3, 2);
        Point point      = new Point(-3, 4, 5);
        Tuple expected   = new Point(2, 1, 7);

        Tuple transformResult = transform.multiply(point);
        assertTrue(expected.isIdentical(transformResult));
        assertTrue(point.isIdentical(transform.inverse().multiply(transformResult)));
    }

    @Test
    public void testTranslationOnVector(){
        //translating vector doesn't change it
        TransformMatrixGenerator generator = new TransformMatrixGenerator();
        Matrix transform = generator.translate(5, -3, 2);
        Vector vector    = new Vector(-3, 4, 5);

        Tuple result     = transform.multiply(vector);
        assertTrue(vector.isIdentical(result));
    }

    @Test
    public void testScaling(){
        TransformMatrixGenerator generator = new TransformMatrixGenerator();
        Matrix scale    = generator.scale(2, 3, 4);
        Point expected1  = new Point(-8, 18, 32);
        Vector expected2 = new Vector(-8, 18, 32);

        Point p = new Point(-4, 6, 8);
        assertTrue(expected1.isIdentical(scale.multiply(p)));

        Vector v = new Vector(-4, 6, 8);
        Tuple result = scale.multiply(v);
        assertTrue(expected2.isIdentical(result));
        assertTrue(v.isIdentical(scale.inverse().multiply(result)));
    }
}
