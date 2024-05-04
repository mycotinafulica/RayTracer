package org.aicl.raytracerchallenge.primitives.shape;

import org.aicl.raytracerchallenge.primitives.Point;
import org.aicl.raytracerchallenge.primitives.Vector;
import org.aicl.raytracerchallenge.primitives.ray.Ray;
import org.aicl.raytracerchallenge.primitives.ray.RayIntersection;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

public class CubeTest {
    @Test
    public void testRayIntersectCube(){
        Cube cube = new Cube();
        ArrayList<Point>  origins = new ArrayList<>();
        ArrayList<Vector> directions = new ArrayList<>();
        ArrayList<Double> t1s = new ArrayList<>();
        ArrayList<Double> t2s = new ArrayList<>();

        origins.add(new Point(5, 0.5, 0)); directions.add(new Vector(-1, 0, 0)); t1s.add(4.); t2s.add(6.); // +x
        origins.add(new Point(-5, 0.5, 0)); directions.add(new Vector(1, 0, 0)); t1s.add(4.); t2s.add(6.); // -x
        origins.add(new Point(0.5, 5, 0)); directions.add(new Vector(0, -1, 0)); t1s.add(4.); t2s.add(6.); // +y
        origins.add(new Point(0.5, -5, 0)); directions.add(new Vector(0, 1, 0)); t1s.add(4.); t2s.add(6.); // -y
        origins.add(new Point(0.5, 0, 5)); directions.add(new Vector(0, 0, -1)); t1s.add(4.); t2s.add(6.); // +z
        origins.add(new Point(0.5, 0, -5)); directions.add(new Vector(0, 0, 1)); t1s.add(4.); t2s.add(6.); // -z
        origins.add(new Point(0., 0.5, 0)); directions.add(new Vector(0, 0, 1)); t1s.add(-1.); t2s.add(1.); // inside


        for(int i = 0; i < origins.size(); i++){
            Ray ray = new Ray(origins.get(i), directions.get(i));
            RayIntersection intersections = cube.localIntersect(ray);
            assertEquals(2, intersections.count);
            assertEquals(t1s.get(i), intersections.getIntersection(0).time, 0.00001);
            assertEquals(t2s.get(i), intersections.getIntersection(1).time, 0.00001);
        }
    }

    @Test
    public void testRayMissesTheCube(){
        Cube c = new Cube();

        ArrayList<Point>  origins    = new ArrayList<>();
        ArrayList<Vector> directions = new ArrayList<>();

        origins.add(new Point(-2, 0, 0)); directions.add(new Vector(0.2673, 0.5345, 0.8018));
        origins.add(new Point(0, -2, 0)); directions.add(new Vector(0.8018, 0.2673, 0.5345));
        origins.add(new Point(0, 0, -2)); directions.add(new Vector(0.5345, 0.8018, 0.2673));
        origins.add(new Point(2, 0, 2)); directions.add(new Vector(0, 0, -1));
        origins.add(new Point(0, 2, 2)); directions.add(new Vector(0, -1, 0));
        origins.add(new Point(2, 2, 0)); directions.add(new Vector(-1, 0, 0));

        for(int i = 0; i < origins.size(); i++){
            Ray ray = new Ray(origins.get(i), directions.get(i));
            RayIntersection intersections = c.localIntersect(ray);
            assertEquals(0, intersections.count);
        }
    }

    @Test
    public void testNormals(){
        Cube c = new Cube();

        ArrayList<Point> points   = new ArrayList<>();
        ArrayList<Vector> normals = new ArrayList<>();

        points.add(new Point(1, 0.5, -0.8)); normals.add(new Vector(1, 0, 0));
        points.add(new Point(-1, -0.2, 0.9)); normals.add(new Vector(-1, 0, 0));
        points.add(new Point(-0.4, 1, -0.1)); normals.add(new Vector(0, 1, 0));
        points.add(new Point(0.3, -1, -0.7)); normals.add(new Vector(0, -1, 0));
        points.add(new Point(-0.6, 0.3, 1)); normals.add(new Vector(0, 0, 1));
        points.add(new Point(0.4, 0.4, -1)); normals.add(new Vector(0, 0, -1));
        points.add(new Point(1, 1, 1)); normals.add(new Vector(1, 0, 0));
        points.add(new Point(-1, -1, -1)); normals.add(new Vector(-1, 0, 0));

        for(int i = 0; i < points.size(); i++){
            Point p       = points.get(i);
            Vector norm   = new Vector(c.localNormal(p, null));
            assertTrue(norm.isIdentical(normals.get(i)));
        }
    }
}
