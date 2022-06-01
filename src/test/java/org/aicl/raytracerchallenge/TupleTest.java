package org.aicl.raytracerchallenge;

import org.aicl.raytracerchallenge.geometry.Point;
import org.aicl.raytracerchallenge.geometry.Tuple;
import org.aicl.raytracerchallenge.geometry.Vector;
import org.aicl.raytracerchallenge.utilities.FloatEquality;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;


public class TupleTest {
    @Test
    public void testTupleIsAPoint(){
        Tuple t = new Tuple(4.3f, -4.2f, 3.1f, 1.0f);
        assertEquals(4.3f , t.x, 0.00001f);
        assertEquals(-4.2f, t.y,  0.00001f);
        assertEquals(3.1f , t.z,  0.00001f);
        assertTrue(t.isAPoint());
        assertFalse(t.isAVector());
    }

    @Test
    public void testTupleIsAVector(){
        Tuple t = new Tuple(4.3f, -4.2f, 3.1f, 0.0f);
        assertEquals(4.3f , t.x, 0.00001f);
        assertEquals(-4.2f, t.y,  0.00001f);
        assertEquals(3.1f , t.z,  0.00001f);
        assertFalse(t.isAPoint());
        assertTrue(t.isAVector());
    }

    @Test
    public void testPointProducePointTuple(){
        Point p = new Point(4f, -4f, 3f);
        assertTrue(p.isAPoint());
    }

    @Test
    public void testVectorProduceVectorTuple(){
        Vector v = new Vector(4f, -4f, 3f);
        assertTrue(v.isAVector());
    }
}
