package org.aicl.raytracerchallenge.primitives;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;


public class TupleTest {
    @Test
    public void testTupleIsAPoint(){
        Tuple t = new Tuple(4.3, -4.2, 3.1, 1.0);
        assertEquals(4.3 , t.x, 0.00001);
        assertEquals(-4.2, t.y,  0.00001);
        assertEquals(3.1 , t.z,  0.00001);
        assertEquals(1.0 , t.w,  0.00001);
        assertTrue(t.isAPoint());
        assertFalse(t.isAVector());
    }

    @Test
    public void testTupleIsAVector(){
        Tuple t = new Tuple(4.3, -4.2, 3.1, 0.0);
        assertEquals(4.3 , t.x, 0.00001);
        assertEquals(-4.2, t.y,  0.00001);
        assertEquals(3.1 , t.z,  0.00001);
        assertEquals(0.0 , t.w,  0.00001);
        assertFalse(t.isAPoint());
        assertTrue(t.isAVector());
    }

    @Test
    public void testPointProducePointTuple(){
        Point p = new Point(4, -4, 3);
        assertTrue(p.isAPoint());
    }

    @Test
    public void testVectorProduceVectorTuple(){
        Vector v = new Vector(4, -4, 3);
        assertTrue(v.isAVector());
    }

    @Test
    public void testAddVectorToPointYieldPoint(){
//        Point p  = new Point(3, -2, 5);
//        Vector v = new Vector(-2, 3, 1);
//        Point target = new Point(1, 1, 6);
//        p.addToSelf(v);
//        assertTrue(p.isIdentical(target));

        Point p  = new Point(3, -2, 5);
        Vector v = new Vector(-2, 3, 1);
        Point target = new Point(1, 1, 6);
        Tuple newP = p.add(v);
        assertTrue(newP.isIdentical(target));

        p  = new Point(3, -2, 5);
        v = new Vector(-2, 3, 1);
        newP = TupleOperation.add(p, v);
        assertTrue(newP.isIdentical(target));
    }

    @Test
    public void testSubtractTwoPointYieldVector(){
//        Point p  = new Point(3, 2, 1);
//        Point p2 = new Point(5, 6, 7);
//        p.subtractSelf(p2);
//        assertTrue(p.isIdentical(target));
        Vector target = new Vector(-2, -4, -6);

        Point p  = new Point(3, 2, 1);
        Point p2 = new Point(5, 6, 7);
        Tuple newP = p.subtract(p2);
        assertTrue(newP.isIdentical(target));
    }

    @Test
    public void testSubtractPointWithVectorYieldsPoint(){
        Point p      = new Point(3, 2, 1);
        Vector v     = new Vector(5, 6, 7);
        Point target = new Point(-2, -4, -6);

        Tuple result = p.subtract(v);
        assertTrue(result.isIdentical(target));

        p = new Point(3, 2, 1);
        v = new Vector(5, 6, 7);
        result = TupleOperation.subtract(p, v);
        assertTrue(result.isIdentical(target));
    }

    @Test
    public void testSubtractVectorWithVectorYieldVector(){
        Vector v1     = new Vector(3, 2, 1);
        Vector v2     = new Vector(5, 6, 7);
        Vector target = new Vector(-2, -4, -6);

        Tuple result = v1.subtract(v2);
        assertTrue(result.isIdentical(target));

        v1     = new Vector(3, 2, 1);
        v2     = new Vector(5, 6, 7);
        result = TupleOperation.subtract(v1, v2);
        assertTrue(result.isIdentical(target));
    }

    @Test
    public void testNegation(){
        Tuple a = new Tuple(1, -2, 3, -4);
        Tuple target = new Tuple(-1, 2, -3, 4);

        assertTrue(target.isIdentical(a.negates()));
//        a.negatesSelf();
//        assertTrue(target.isIdentical(a));
    }

    @Test
    public void testMultiplication(){
        Tuple a = new Tuple(1, -2, 3, -4);
        Tuple target = new Tuple(3.5f, -7, 10.5, -14);

        assertTrue(target.isIdentical(a.multiply(3.5)));
//        a.multiplySelf(3.5);
//        assertTrue(target.isIdentical(a.multiply(3.5)));
    }

    @Test
    public void testDivision(){
        Tuple a = new Tuple(1, -2, 3, -4);
        Tuple target = new Tuple(0.5, -1, 1.5, -2);

        assertTrue(target.isIdentical(a.divide(2)));
//        a.divideSelf(2);
//        assertTrue(target.isIdentical(a));
    }

    @Test
    public void computeMagnitude(){
        Vector v1 = new Vector(1, 0, 0);
        Vector v2 = new Vector(0, 1, 0);
        Vector v3 = new Vector(0, 0, 1);
        Vector v4 = new Vector(1, 2, 3);
        Vector v5 = new Vector(-1, -2, -3);
        assertEquals(1, v1.magnitude(), 0.00001);
        assertEquals(1, v2.magnitude(), 0.00001);
        assertEquals(1, v3.magnitude(), 0.00001);
        assertEquals(Math.sqrt(14), v4.magnitude(), 0.00001);
        assertEquals(Math.sqrt(14), v5.magnitude(), 0.00001);
    }

    @Test
    public void normalizeTest(){
        Vector v1 = new Vector(4, 0, 0);
        Vector v2 = new Vector(1, 2, 3);

        Vector target1 = new Vector(1, 0, 0);
        assertTrue(target1.isIdentical(v1.normalize()));

        Vector target2 = new Vector(1.0/Math.sqrt(14), 2.0/Math.sqrt(14), 3.0/Math.sqrt(14));
        assertTrue(target2.isIdentical(v2.normalize()));
        assertEquals(1.0, v2.normalize().magnitude(), 0.00001);
    }

    @Test
    public void dotProductTest(){
        Vector a = new Vector(1, 2, 3);
        Vector b = new Vector(2, 3,4);
        assertEquals(20, TupleOperation.dot(a, b));
    }

    @Test
    public void crossProductTest(){
        Vector a = new Vector(1, 2, 3);
        Vector b = new Vector(2, 3,4);
        Vector result = new Vector(-1, 2, -1);
        assertTrue(result.isIdentical(TupleOperation.cross(a, b)));
        assertTrue(result.negates().isIdentical(TupleOperation.cross(b, a)));
    }

    @Test
    public void testReflection(){
        Vector v = new Vector(1, -1, 0);
        Vector n = new Vector(0, 1, 0);
        Vector r = TupleOperation.reflect(v, n);
        Vector expect = new Vector(1, 1, 0);
        assertTrue(expect.isIdentical(r));
    }

    @Test
    public void testReflection2(){
        Vector v = new Vector(0, -1, 0);
        Vector n = new Vector(Math.sqrt(2)/2.0, Math.sqrt(2)/2.0, 0);
        Vector r = TupleOperation.reflect(v, n);
        Vector expect = new Vector(1, 0, 0);
        assertTrue(expect.isIdentical(r));
    }
}
