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

    @Test
    public void testReflection(){
        TransformMatrixGenerator generator = new TransformMatrixGenerator();
        //reflect on the x axis
        Matrix reflect  = generator.scale(-1, 1, 1);
        Point point     = new Point(2, 3, 4);
        Point expected  = new Point(-2, 3, 4);
        assertTrue(expected.isIdentical(reflect.multiply(point)));
    }

    @Test
    public void testXAxisRotation(){
        TransformMatrixGenerator generator = new TransformMatrixGenerator();
        Matrix halfQuarter = generator.rotateX(Math.PI/4.0);
        Matrix fullQuarter = generator.rotateX(Math.PI/2.0);
        Point p = new Point(0, 1, 0);

        Point expectedHalf = new Point(0, Math.sqrt(2)/2.0, Math.sqrt(2)/2.0);
        Point expectedFull = new Point(0, 0, 1);
        assertTrue(expectedHalf.isIdentical(halfQuarter.multiply(p)));
        assertTrue(expectedFull.isIdentical(fullQuarter.multiply(p)));

        p = new Point(0, 1, 0);
        Matrix inverseHalf = halfQuarter.inverse();
        Point expectedHalfInverse = new Point(0, Math.sqrt(2)/2.0, -Math.sqrt(2)/2.0);
        assertTrue(expectedHalfInverse.isIdentical(inverseHalf.multiply(p)));
    }

    @Test
    public void testYAxisRotation(){
        TransformMatrixGenerator generator = new TransformMatrixGenerator();
        Matrix halfQuarter = generator.rotateY(Math.PI/4.0);
        Matrix fullQuarter = generator.rotateY(Math.PI/2.0);
        Point p = new Point(0, 0, 1);


        Point expectedHalf = new Point(Math.sqrt(2)/2.0, 0, Math.sqrt(2)/2.0);
        Point expectedFull = new Point(1, 0, 0);
        assertTrue(expectedHalf.isIdentical(halfQuarter.multiply(p)));
        assertTrue(expectedFull.isIdentical(fullQuarter.multiply(p)));
    }

    @Test
    public void testZAxisRotation(){
        TransformMatrixGenerator generator = new TransformMatrixGenerator();
        Matrix halfQuarter = generator.rotateZ(Math.PI/4.0);
        Matrix fullQuarter = generator.rotateZ(Math.PI/2.0);
        Point p = new Point(0, 1, 0);


        Point expectedHalf = new Point(-Math.sqrt(2)/2.0, Math.sqrt(2)/2.0, 0);
        Point expectedFull = new Point(-1, 0, 0);
        assertTrue(expectedHalf.isIdentical(halfQuarter.multiply(p)));
        assertTrue(expectedFull.isIdentical(fullQuarter.multiply(p)));
    }

    @Test
    public void shearingTest(){
        TransformMatrixGenerator generator = new TransformMatrixGenerator();
        Point p = new Point(2, 3, 4);
        Matrix shear = generator.shearing(1, 0, 0, 0, 0, 0);
        Point expected = new Point(5, 3, 4);
        assertTrue(expected.isIdentical(shear.multiply(p)));

        shear = generator.shearing(0, 1, 0, 0, 0, 0);
        expected = new Point(6, 3, 4);
        assertTrue(expected.isIdentical(shear.multiply(p)));

        shear = generator.shearing(0, 0, 1, 0, 0, 0);
        expected = new Point(2, 5, 4);
        assertTrue(expected.isIdentical(shear.multiply(p)));

        shear = generator.shearing(0, 0, 0, 1, 0, 0);
        expected = new Point(2, 7, 4);
        assertTrue(expected.isIdentical(shear.multiply(p)));

        shear = generator.shearing(0, 0, 0, 0, 1, 0);
        expected = new Point(2, 3, 6);
        assertTrue(expected.isIdentical(shear.multiply(p)));

        shear = generator.shearing(0, 0, 0, 0, 0, 1);
        expected = new Point(2, 3, 7);
        assertTrue(expected.isIdentical(shear.multiply(p)));
    }

    @Test
    public void testChainingTransformation(){
        TransformMatrixGenerator generator = new TransformMatrixGenerator();
        Point p = new Point(1, 0, 1);
        Matrix rotation  = generator.rotateX(Math.PI/2);
        Matrix scaling   = generator.scale(5, 5,5);
        Matrix translate = generator.translate(10, 5, 7);

        Tuple p2 = rotation.multiply(p);
        Point expected = new Point(1, -1, 0);
        assertTrue(expected.isIdentical(p2));

        Tuple p3 = scaling.multiply(p2);
        expected = new Point(5, -5, 0);
        assertTrue(expected.isIdentical(p3));

        Tuple p4 = translate.multiply(p3);
        expected = new Point(15, 0, 7);
        assertTrue(expected.isIdentical(p4));

        Matrix chainedTransform = translate.multiply(scaling.multiply(rotation));
        Tuple chained  = chainedTransform.multiply(p);
        expected = new Point(15, 0, 7);
        assertTrue(expected.isIdentical(chained));
    }

    @Test
    public void viewTransformCase1(){
        Point from = new Point(0, 0, 0);
        Point to = new Point(0, 0, -1);
        Vector up = new Vector(0, 1, 0);
        Matrix t = TransformMatrixGenerator.viewTransform(from, to, up);
        t.print();
        assertTrue(Matrix.identity().isEqual(t));
    }

    @Test
    public void viewTransformCase2(){
        Point from = new Point(0, 0, 0);
        Point to = new Point(0, 0, 1);
        Vector up = new Vector(0, 1, 0);
        Matrix t = TransformMatrixGenerator.viewTransform(from, to, up);
        t.print();
        TransformMatrixGenerator generator = new TransformMatrixGenerator();
        assertTrue(generator.scale(-1, 1, -1).isEqual(t));
    }

    @Test
    public void viewTransformCase3(){
        Point from = new Point(0, 0, 8);
        Point to = new Point(0, 0, 0);
        Vector up = new Vector(0, 1, 0);
        Matrix t = TransformMatrixGenerator.viewTransform(from, to, up);
        t.print();
        TransformMatrixGenerator generator = new TransformMatrixGenerator();
        assertTrue(generator.translate(0, 0, -8).isEqual(t));
    }

    @Test
    public void viewTransformCase4(){
        Point from = new Point(1, 3, 2);
        Point to = new Point(4, -2, 8);
        Vector up = new Vector(1, 1, 0);
        Matrix t = TransformMatrixGenerator.viewTransform(from, to, up);
        t.print();
        Matrix expected = new Matrix(new double[][]{
                new double[]{-0.50709, 0.50709, 0.67612, -2.36643},
                new double[]{0.76772, 0.60609, 0.12122, -2.82843},
                new double[]{-0.35857, 0.59761, -0.71714, 0.00000},
                new double[]{0.00000, 0.00000, 0.00000, 1.0000}
        });
        assertTrue(expected.isEqual(t));
    }
}
