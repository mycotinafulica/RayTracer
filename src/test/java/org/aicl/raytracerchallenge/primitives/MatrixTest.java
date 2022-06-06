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
}
