package org.aicl.raytracerchallenge.primitives.shape;

import static org.junit.jupiter.api.Assertions.*;

import org.aicl.raytracerchallenge.primitives.Point;
import org.aicl.raytracerchallenge.primitives.Vector;
import org.aicl.raytracerchallenge.primitives.ray.Intersection;
import org.aicl.raytracerchallenge.primitives.ray.Ray;
import org.aicl.raytracerchallenge.primitives.ray.RayIntersection;
import org.aicl.raytracerchallenge.transformation.TransformMatrixGenerator;
import org.junit.jupiter.api.Test;

import java.util.List;

public class CSGTest {
    @Test
    public void testConstruction(){
        Shape s1 = new Sphere();
        Shape s2 = new Cube();
        CSG c = new CSG(CSG.Operation.UNION, s1, s2);

        assertEquals(CSG.Operation.UNION, c.operation);
        assertTrue(c.left.isSame(s1));
        assertTrue(c.right.isSame(s2));
        assertTrue(s1.getParent().isSame(c));
        assertTrue(s2.getParent().isSame(c));
    }

    @Test
    public void operationTest(){
        Shape s1 = new Sphere();
        Shape s2 = new Cube();
        CSG c = new CSG(CSG.Operation.UNION, s1, s2);

        assertFalse(c.intersectionAllowed(CSG.Operation.UNION, true, true, true));
        assertTrue(c.intersectionAllowed(CSG.Operation.UNION, true, true, false));
        assertFalse(c.intersectionAllowed(CSG.Operation.UNION, true, false, true));
        assertTrue(c.intersectionAllowed(CSG.Operation.UNION, true, false, false));
        assertFalse(c.intersectionAllowed(CSG.Operation.UNION, false, true, true));
        assertFalse(c.intersectionAllowed(CSG.Operation.UNION, false, true, false));
        assertTrue(c.intersectionAllowed(CSG.Operation.UNION, false, false, true));
        assertTrue(c.intersectionAllowed(CSG.Operation.UNION, false, false, false));

        assertTrue(c.intersectionAllowed(CSG.Operation.INTERSECT, true, true, true));
        assertFalse(c.intersectionAllowed(CSG.Operation.INTERSECT, true, true, false));
        assertTrue(c.intersectionAllowed(CSG.Operation.INTERSECT, true, false, true));
        assertFalse(c.intersectionAllowed(CSG.Operation.INTERSECT, true, false, false));
        assertTrue(c.intersectionAllowed(CSG.Operation.INTERSECT, false, true, true));
        assertTrue(c.intersectionAllowed(CSG.Operation.INTERSECT, false, true, false));
        assertFalse(c.intersectionAllowed(CSG.Operation.INTERSECT, false, false, true));
        assertFalse(c.intersectionAllowed(CSG.Operation.INTERSECT, false, false, false));

        assertFalse(c.intersectionAllowed(CSG.Operation.DIFFERENCE, true, true, true));
        assertTrue(c.intersectionAllowed(CSG.Operation.DIFFERENCE, true, true, false));
        assertFalse(c.intersectionAllowed(CSG.Operation.DIFFERENCE, true, false, true));
        assertTrue(c.intersectionAllowed(CSG.Operation.DIFFERENCE, true, false, false));
        assertTrue(c.intersectionAllowed(CSG.Operation.DIFFERENCE, false, true, true));
        assertTrue(c.intersectionAllowed(CSG.Operation.DIFFERENCE, false, true, false));
        assertFalse(c.intersectionAllowed(CSG.Operation.DIFFERENCE, false, false, true));
        assertFalse(c.intersectionAllowed(CSG.Operation.DIFFERENCE, false, false, false));
    }

    @Test
    public void intersectionFilteringTest(){
        Shape s1 = new Sphere();
        Shape s2 = new Cube();
        CSG c = new CSG(CSG.Operation.UNION, s1, s2);
        RayIntersection xs = new RayIntersection(4, List.of(
                new Intersection(1, s1),
                new Intersection(2, s2),
                new Intersection(3, s1),
                new Intersection(4, s2)
        ));

        RayIntersection filtered = c.filterIntersections(xs);
        assertEquals(2, filtered.count);
        assertTrue(filtered.getIntersection(0).isEqual(xs.getIntersection(0)));
        assertTrue(filtered.getIntersection(1).isEqual(xs.getIntersection(3)));

        c = new CSG(CSG.Operation.INTERSECT, s1, s2);
        filtered = c.filterIntersections(xs);
        assertEquals(2, filtered.count);
        assertTrue(filtered.getIntersection(0).isEqual(xs.getIntersection(1)));
        assertTrue(filtered.getIntersection(1).isEqual(xs.getIntersection(2)));

        c = new CSG(CSG.Operation.DIFFERENCE, s1, s2);
        filtered = c.filterIntersections(xs);
        assertEquals(2, filtered.count);
        assertTrue(filtered.getIntersection(0).isEqual(xs.getIntersection(0)));
        assertTrue(filtered.getIntersection(1).isEqual(xs.getIntersection(1)));
    }

    @Test
    public void rayMissCSGTest(){
        CSG c = new CSG(CSG.Operation.UNION, new Sphere(), new Cube());
        Ray r = new Ray(new Point(0, 2, -5), new Vector(0, 0, 1));
        RayIntersection xs = c.localIntersect(r);
        assertEquals(0, xs.count);
    }

    @Test
    public void rayHitCSGTest(){
        TransformMatrixGenerator generator = new TransformMatrixGenerator();
        Shape s1 = new Sphere();
        Shape s2 = new Sphere();
        s2.setTransform(generator.translate(0, 0, 0.5));
        CSG c = new CSG(CSG.Operation.UNION, s1, s2);
        Ray r = new Ray(new Point(0, 0, -5), new Vector(0, 0, 1));
        RayIntersection xs = c.localIntersect(r);
        assertEquals(2, xs.count);
        assertEquals(4., xs.getIntersection(0).time, 0.00001);
        assertEquals(6.5, xs.getIntersection(1).time, 0.00001);
        assertTrue(xs.getIntersection(0).intersectedShape.isSame(s1));
        assertTrue(xs.getIntersection(1).intersectedShape.isSame(s2));
    }
}
