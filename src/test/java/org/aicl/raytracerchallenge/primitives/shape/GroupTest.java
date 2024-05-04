package org.aicl.raytracerchallenge.primitives.shape;

import static org.junit.jupiter.api.Assertions.*;

import org.aicl.raytracerchallenge.primitives.Matrix;
import org.aicl.raytracerchallenge.primitives.Point;
import org.aicl.raytracerchallenge.primitives.Tuple;
import org.aicl.raytracerchallenge.primitives.Vector;
import org.aicl.raytracerchallenge.primitives.ray.Ray;
import org.aicl.raytracerchallenge.primitives.ray.RayIntersection;
import org.aicl.raytracerchallenge.transformation.TransformMatrixGenerator;
import org.junit.jupiter.api.Test;

import java.util.zip.GZIPOutputStream;

public class GroupTest {
    @Test
    public void groupCreationTest(){
        Group group = new Group();
        assertTrue(group.transform.isEqual(Matrix.identity()));
        assertEquals(0, group.getChildren().size());
    }

    @Test
    public void testShapeHasParent(){
        Shape shape = new Sphere();
        assertNull(shape.getParent());
    }

    @Test
    public void testAddingChildToGroup(){
        Group g = new Group();
        Shape s = new Sphere();
        g.addChild(s);
        assertTrue(g.hashChild(s));
        assertTrue(s.getParent().isSame(g));
    }

    @Test
    public void groupIntersectionTestTrivialCase(){
        Group g = new Group();
        Ray r = new Ray(new Point(0, 0, 0), new Vector(0, 0, 1));
        RayIntersection xs = g.localIntersect(r);
        assertEquals(0, xs.count);
    }

    @Test
    public void groupIntersectionTest(){
        TransformMatrixGenerator generator = new TransformMatrixGenerator();
        Group g = new Group();
        Shape s1 = new Sphere();
        Shape s2 = new Sphere();
        s2.setTransform(generator.translate(0, 0, -3));
        Shape s3 = new Sphere();
        s3.setTransform(generator.translate(5, 0, 0));
        Ray r = new Ray(new Point(0, 0, -5), new Vector(0, 0, 1));

        g.addChild(s1);
        g.addChild(s2);
        g.addChild(s3);

        RayIntersection xs = g.localIntersect(r);
        xs.sort();
        assertEquals(4, xs.count);
        assertTrue(xs.getIntersection(0).intersectedShape.isSame(s2));
        assertTrue(xs.getIntersection(1).intersectedShape.isSame(s2));
        assertTrue(xs.getIntersection(2).intersectedShape.isSame(s1));
        assertTrue(xs.getIntersection(3).intersectedShape.isSame(s1));
    }

    @Test
    public void groupTransformationTest(){
        TransformMatrixGenerator generator = new TransformMatrixGenerator();
        Group g = new Group();
        g.setTransform(generator.scale(2, 2, 2));
        Sphere s = new Sphere();
        s.setTransform(generator.translate(5, 0, 0));
        g.addChild(s);
        Ray r = new Ray(new Point(10, 0, -10), new Vector(0, 0, 1));
        RayIntersection xs = g.intersect(r);
        assertEquals(2, xs.count);
    }

    @Test
    public void testWorldToObjectOnGroups(){
        TransformMatrixGenerator generator = new TransformMatrixGenerator();
        Group g1 = new Group();
        g1.setTransform(generator.rotateY(Math.PI/2.0));
        Group g2 = new Group();
        g2.setTransform(generator.scale(2, 2, 2));
        g1.addChild(g2);

        Sphere s = new Sphere();
        s.setTransform(generator.translate(5, 0, 0));
        g2.addChild(s);

        Point p = new Point(s.worldPointToObjectPoint(new Point(-2, 0, -10)));
        assertTrue(p.isIdentical(new Point(0, 0, -1)));
    }

    @Test
    public void testLocalNormalToWorldNormal(){
        TransformMatrixGenerator generator = new TransformMatrixGenerator();
        Group g1 = new Group();
        g1.setTransform(generator.rotateY(Math.PI/2.0));
        Group g2 = new Group();
        g2.setTransform(generator.scale(1, 2, 3));
        g1.addChild(g2);

        Sphere s = new Sphere();
        s.setTransform(generator.translate(5, 0, 0));
        g2.addChild(s);

        Tuple normal = s.objectNormalToWorldNormal(new Vector(Math.sqrt(3)/3.0, Math.sqrt(3)/3.0, Math.sqrt(3)/3.0));
        assertTrue(normal.isIdentical(new Vector(0.28571, 0.42857, -0.85714)));
    }

    @Test
    public void findingChildsNormal(){
        TransformMatrixGenerator generator = new TransformMatrixGenerator();
        Group g1 = new Group();
        g1.setTransform(generator.rotateY(Math.PI/2.0));
        Group g2 = new Group();
        g2.setTransform(generator.scale(1, 2, 3));
        g1.addChild(g2);

        Sphere s = new Sphere();
        s.setTransform(generator.translate(5, 0, 0));
        g2.addChild(s);

        Tuple normal = s.normalAt(new Point(1.7321, 1.1547, -5.5774), null);
        assertTrue(normal.isIdentical(new Vector(0.285703, 0.42854, -0.85716)));
    }
}
