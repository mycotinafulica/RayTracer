package org.aicl.raytracerchallenge.primitives;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

public class MatrixTest {
    @Test
    public void testMatrixConstruction(){
        Matrix m = new Matrix(new double[][]{
                new double[]{1, 2, 3, 4},
                new double[]{5.5, 6.5, 7.5, 8.5},
                new double[]{9, 10, 11, 12},
                new double[]{13.5, 14.5, 15.5, 16.5}
        });

        assertEquals(1, m.elementAt(0, 0), 0.00001);
        assertEquals(4, m.elementAt(0, 3), 0.00001);
        assertEquals(5.5, m.elementAt(1, 0), 0.00001);
        assertEquals(7.5, m.elementAt(1, 2), 0.00001);
        assertEquals(11, m.elementAt(2, 2), 0.00001);
        assertEquals(13.5, m.elementAt(3, 0), 0.00001);
        assertEquals(15.5, m.elementAt(3, 2), 0.00001);

        m = new Matrix(new double[][]{
                new double[]{-3, 5},
                new double[]{1, -2}
        });

        assertEquals(-3, m.elementAt(0, 0), 0.00001);
        assertEquals(5, m.elementAt(0, 1), 0.00001);
        assertEquals(1, m.elementAt(1, 0), 0.00001);
        assertEquals(-2, m.elementAt(1, 1), 0.00001);

        m = new Matrix(new double[][]{
                new double[]{-3, 5, 0},
                new double[]{1, -2, -7},
                new double[]{0, 1, 1}
        });

        assertEquals(-3, m.elementAt(0, 0), 0.00001);
        assertEquals(-2, m.elementAt(1, 1), 0.00001);
        assertEquals(1, m.elementAt(2, 2), 0.00001);
    }

    @Test
    public void equalityTest(){
        Matrix m1 = new Matrix(new double[][]{
                new double[]{1, 2, 3, 4},
                new double[]{5, 6, 7, 8},
                new double[]{9, 8, 7, 6},
                new double[]{5, 4, 3, 2}
        });
        Matrix m2 = new Matrix(new double[][]{
                new double[]{1, 2, 3, 4},
                new double[]{5, 6, 7, 8},
                new double[]{9, 8, 7, 6},
                new double[]{5, 4, 3, 2}
        });

        assertTrue(m1.isEqual(m2));

        Matrix m3 = new Matrix(new double[][]{
                new double[]{1, 2, 3, 4},
                new double[]{5, 6, 7, 8},
                new double[]{9, 8, 7, 6},
                new double[]{5, 1, 3, 2}
        });

        assertFalse(m1.isEqual(m3));
    }

    @Test
    public void multiplicationTest(){
        Matrix m1 = new Matrix(new double[][]{
                new double[]{1, 2, 3, 4},
                new double[]{5, 6, 7, 8},
                new double[]{9, 8, 7, 6},
                new double[]{5, 4, 3, 2}
        });
        Matrix m2 = new Matrix(new double[][]{
                new double[]{-2, 1, 2, 3},
                new double[]{3, 2, 1, -1},
                new double[]{4, 3, 6, 5},
                new double[]{1, 2, 7, 8}
        });
        Matrix expected = new Matrix(new double[][]{
                new double[]{20, 22, 50, 48},
                new double[]{44, 54, 114, 108},
                new double[]{40, 58, 110, 102},
                new double[]{16, 26, 46, 42}
        });

        Matrix result = m1.multiply(m2);
        assertTrue(expected.isEqual(result));
    }

    @Test
    public void matrixTupleMultiply(){
        Matrix m = new Matrix(new double[][]{
                new double[]{1, 2, 3, 4},
                new double[]{2, 4, 4, 2},
                new double[]{8, 6, 4, 1},
                new double[]{0, 0, 0, 1}
        });
        Tuple t = new Tuple(1, 2, 3, 1);
        Tuple expected = new Tuple(18, 24, 33, 1);
        Tuple result = m.multiply(t);
        assertTrue(expected.isIdentical(result));
    }

    @Test
    public void identityMatrixTest(){
        Matrix m = new Matrix(new double[][]{
                new double[]{0, 1, 2, 4},
                new double[]{1, 2, 4, 8},
                new double[]{2, 4, 8, 16},
                new double[]{4, 8, 16 ,32}
        });
        Matrix I = new Matrix(new double[][]{
                new double[]{1, 0, 0, 0},
                new double[]{0, 1, 0, 0},
                new double[]{0, 0, 1, 0},
                new double[]{0, 0, 0, 1}
        });
        Matrix result = m.multiply(I);
        assertTrue(result.isEqual(m));

        Matrix result2 = I.multiply(m);
        assertTrue(result2.isEqual(m));
    }

    @Test
    public void testTranspose(){
        Matrix m = new Matrix(new double[][]{
                new double[]{0, 9, 3, 0},
                new double[]{9, 8, 0, 8},
                new double[]{1, 8, 5, 3},
                new double[]{0, 0, 5, 8}
        });
        Matrix expected = new Matrix(new double[][]{
                new double[]{0, 9,1 , 0},
                new double[]{9, 8, 8, 0},
                new double[]{3, 0, 5, 5},
                new double[]{0, 8, 3, 8}
        });
        assertTrue(expected.isEqual(m.transpose()));

        m = new Matrix(new double[][]{
                new double[]{1, 0, 0, 0},
                new double[]{0, 1, 0, 0},
                new double[]{0, 0, 1, 0},
                new double[]{0, 0, 0, 1}
        });
        expected = new Matrix(new double[][]{
                new double[]{1, 0, 0, 0},
                new double[]{0, 1, 0, 0},
                new double[]{0, 0, 1, 0},
                new double[]{0, 0, 0, 1}
        });
        assertTrue(expected.isEqual(m.transpose()));
    }

    @Test
    public void testSubMatrix(){
        Matrix m = new Matrix(new double[][]{
                new double[]{1, 5, 0},
                new double[]{-3, 2, 7},
                new double[]{0, 6, -3}
        });
        Matrix expected = new Matrix(new double[][]{
                new double[]{-3, 2},
                new double[]{0, 6}
        });
        assertTrue(expected.isEqual(m.subMatrix(0, 2)));

        m = new Matrix(new double[][]{
                new double[]{-6, 1, 1, 6},
                new double[]{-8, 5, 8, 6},
                new double[]{-1, 0, 8, 2},
                new double[]{-7, 1, -1, 1}
        });
        expected = new Matrix(new double[][]{
                new double[]{-6, 1, 6},
                new double[]{-8, 8, 6},
                new double[]{-7, -1, 1}
        });
        Matrix subMat = m.subMatrix(2, 1);
        assertTrue(expected.isEqual(subMat));
    }

    @Test
    public void testDeterminant(){
        Matrix m = new Matrix(new double[][]{
                new double[]{1, 5},
                new double[]{-3, 2}
        });
        assertEquals(17.0, m.determinant(), 0.00001);
    }

    @Test
    public void testMinorCalculation(){
        Matrix m = new Matrix(new double[][]{
                new double[]{3, 5, 0},
                new double[]{2, -1, -7},
                new double[]{6, -1, 5}
        });
        Matrix subMat = m.subMatrix(1, 0);
        double determinant = subMat.determinant();
        assertEquals(determinant, m.minor(1, 0), 0.00001);
    }

    @Test
    public void testCofactor(){
        Matrix m = new Matrix(new double[][]{
                new double[]{3, 5, 0},
                new double[]{2, -1, -7},
                new double[]{6, -1, 5}
        });
        assertEquals(m.minor(0, 0), m.cofactor(0, 0), 0.0001);
        assertEquals(-m.minor(1, 0), m.cofactor(1, 0), 0.0001);
    }

    @Test
    public void testDeterminantLargeMatrix(){
        Matrix m = new Matrix(new double[][]{
                new double[]{1, 2, 6},
                new double[]{-5, 8, -4},
                new double[]{2, 6, 4}
        });
        assertEquals(56, m.cofactor(0, 0), 0.00001);
        assertEquals(12, m.cofactor(0, 1), 0.00001);
        assertEquals(-46, m.cofactor(0, 2), 0.00001);
        assertEquals(-196, m.determinant(), 0.00001);

        m = new Matrix(new double[][]{
                new double[]{-2, -8, 3, 5},
                new double[]{-3, 1, 7, 3},
                new double[]{1, 2, -9, 6},
                new double[]{-6, 7, 7, -9}
        });

        assertEquals(690, m.cofactor(0, 0), 0.00001);
        assertEquals(447, m.cofactor(0, 1), 0.00001);
        assertEquals(210, m.cofactor(0, 2), 0.00001);
        assertEquals(51, m.cofactor(0, 3), 0.00001);
        assertEquals(-4071, m.determinant(), 0.00001);
    }

    @Test
    public void invertibleTest(){
        Matrix m = new Matrix(new double[][]{
                new double[]{6, 4, 4, 4},
                new double[]{5, 5, 7, 6},
                new double[]{4, -9, 2, -7},
                new double[]{9, 1, 7, -6}
        });
        assertTrue(m.isInvertible());
        m = new Matrix(new double[][]{
                new double[]{4, 2, -2, -3},
                new double[]{9, 6, 2, 6},
                new double[]{0, -5, 1, -5},
                new double[]{0, 0, 0, 0}
        });

        assertFalse(m.isInvertible());
    }

    @Test
    public void matrixInverseTest(){
        Matrix m = new Matrix(new double[][]{
                new double[]{-5, 2, 6, -8},
                new double[]{1, -5, 1, 8},
                new double[]{7, 7, -6, -7},
                new double[]{1, -3, 7, 4}
        });

        Matrix inverted = m.inverse();
        assertEquals(532, m.determinant(), 0.00001);
        assertEquals(-160, m.cofactor(2, 3), 0.00001);
        assertEquals((-160.0/532.0), inverted.elementAt(3, 2), 0.00001);
        assertEquals(105, m.cofactor(3, 2), 0.00001);
        assertEquals((105/532.0), inverted.elementAt(2, 3), 0.00001);

        Matrix expected = new Matrix(new double[][]{
                new double[]{0.21805, 0.45113, 0.24060, -0.04511},
                new double[]{-0.80827, -1.45677, -0.44361, 0.52068},
                new double[]{-.07895, -0.22368, -0.05263, 0.19737},
                new double[]{-0.52256, -0.81391, -0.30075, 0.30639}
        });

        inverted.print();

        assertTrue(expected.isEqual(inverted));
    }
}
