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
    }
}
