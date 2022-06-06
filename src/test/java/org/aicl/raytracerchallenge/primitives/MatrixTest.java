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
}
