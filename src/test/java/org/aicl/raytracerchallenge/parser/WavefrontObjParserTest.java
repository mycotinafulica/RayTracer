package org.aicl.raytracerchallenge.parser;

import static org.junit.jupiter.api.Assertions.*;

import org.aicl.raytracerchallenge.primitives.Point;
import org.aicl.raytracerchallenge.primitives.Vector;
import org.aicl.raytracerchallenge.primitives.shape.Group;
import org.aicl.raytracerchallenge.primitives.shape.Shape;
import org.aicl.raytracerchallenge.primitives.shape.SmoothTriangle;
import org.aicl.raytracerchallenge.primitives.shape.Triangle;
import org.junit.jupiter.api.Test;

import java.io.IOException;

public class WavefrontObjParserTest {
    @Test
    public void gibberishTest() throws IOException {
        WavefrontObjParser parser = new WavefrontObjParser();
        WavefrontScene result = parser.parse("E:\\William\\HighImportance\\GIT_ROOT\\RayTracerChallenge\\RayTracerChallenge\\data\\gibberish.obj");
        assertEquals(5, result.ignoredLines);
    }

    @Test
    public void parseVertices() throws IOException {
        WavefrontObjParser parser = new WavefrontObjParser();
        WavefrontScene result = parser.parse("E:\\William\\HighImportance\\GIT_ROOT\\RayTracerChallenge\\RayTracerChallenge\\data\\obj_vertices.obj");
        assertEquals(1, result.ignoredLines);
        assertTrue(new Point(-1, 1, 0).isIdentical(result.vertices.get(0)));
        assertTrue(new Point(-1, 0.5, 0).isIdentical(result.vertices.get(1)));
        assertTrue(new Point(1, 0, 0).isIdentical(result.vertices.get(2)));
        assertTrue(new Point(1, 1, 0).isIdentical(result.vertices.get(3)));
    }

    @Test
    public void parseFaces() throws IOException{
        WavefrontObjParser parser = new WavefrontObjParser();
        WavefrontScene result = parser.parse("E:\\William\\HighImportance\\GIT_ROOT\\RayTracerChallenge\\RayTracerChallenge\\data\\obj_with_faces.obj");

        Group dg = result.defaultGroup;
        Triangle t1 = (Triangle) dg.getChildren().get(0);
        Triangle t2 = (Triangle) dg.getChildren().get(1);

        assertTrue(result.vertices.get(0).isIdentical(t1.p1));
        assertTrue(result.vertices.get(1).isIdentical(t1.p2));
        assertTrue(result.vertices.get(2).isIdentical(t1.p3));
        assertTrue(result.vertices.get(0).isIdentical(t2.p1));
        assertTrue(result.vertices.get(2).isIdentical(t2.p2));
        assertTrue(result.vertices.get(3).isIdentical(t2.p3));
    }

    @Test
    public void testTriangulation() throws IOException{
        WavefrontObjParser parser = new WavefrontObjParser();
        WavefrontScene result = parser.parse("E:\\William\\HighImportance\\GIT_ROOT\\RayTracerChallenge\\RayTracerChallenge\\data\\triangulation_test.obj");

        Group dg = result.defaultGroup;
        Triangle t1 = (Triangle) dg.getChildren().get(0);
        Triangle t2 = (Triangle) dg.getChildren().get(1);
        Triangle t3 = (Triangle) dg.getChildren().get(2);

        assertTrue(t1.p1.isIdentical(result.vertices.get(0)));
        assertTrue(t1.p2.isIdentical(result.vertices.get(1)));
        assertTrue(t1.p3.isIdentical(result.vertices.get(2)));

        assertTrue(t2.p1.isIdentical(result.vertices.get(0)));
        assertTrue(t2.p2.isIdentical(result.vertices.get(2)));
        assertTrue(t2.p3.isIdentical(result.vertices.get(3)));

        assertTrue(t3.p1.isIdentical(result.vertices.get(0)));
        assertTrue(t3.p2.isIdentical(result.vertices.get(3)));
        assertTrue(t3.p3.isIdentical(result.vertices.get(4)));
    }

    @Test
    public void testGroups() throws IOException{
        WavefrontObjParser parser = new WavefrontObjParser();
        WavefrontScene result = parser.parse("E:\\William\\HighImportance\\GIT_ROOT\\RayTracerChallenge\\RayTracerChallenge\\data\\named_groups.obj");

        Group g1 = result.groups.get("FirstGroup");
        Group g2 = result.groups.get("SecondGroup");
        Triangle t1 = (Triangle) g1.getChildren().get(0);
        Triangle t2 = (Triangle) g2.getChildren().get(0);

        assertTrue(result.vertices.get(0).isIdentical(t1.p1));
        assertTrue(result.vertices.get(1).isIdentical(t1.p2));
        assertTrue(result.vertices.get(2).isIdentical(t1.p3));
        assertTrue(result.vertices.get(0).isIdentical(t2.p1));
        assertTrue(result.vertices.get(2).isIdentical(t2.p2));
        assertTrue(result.vertices.get(3).isIdentical(t2.p3));
    }

    @Test
    public void testAggregateToGroup() throws IOException{
        WavefrontObjParser parser = new WavefrontObjParser();
        WavefrontScene result = parser.parse("E:\\William\\HighImportance\\GIT_ROOT\\RayTracerChallenge\\RayTracerChallenge\\data\\named_groups.obj");

        Group g1 = result.groups.get("FirstGroup");
        Group g2 = result.groups.get("SecondGroup");

        Group endGroup = result.convertToSingleGroup();
        assertTrue(endGroup.hashChild(g1));
        assertTrue(endGroup.hashChild(g2));
    }

    @Test
    public void vectorNormalsTest() throws IOException{
        WavefrontObjParser parser = new WavefrontObjParser();
        WavefrontScene result = parser.parse("E:\\William\\HighImportance\\GIT_ROOT\\RayTracerChallenge\\RayTracerChallenge\\data\\with_normals.obj");

        Vector n1 = new Vector(0, 0, 1);
        Vector n2 = new Vector(0.707, 0, -0.707);
        Vector n3 = new Vector(1, 2, 3);

        assertTrue(n1.isIdentical(result.normals.get(0)));
        assertTrue(n2.isIdentical(result.normals.get(1)));
        assertTrue(n3.isIdentical(result.normals.get(2)));
    }

    @Test
    public void facesWithNormals() throws IOException{
        WavefrontObjParser parser = new WavefrontObjParser();
        WavefrontScene result = parser.parse("E:\\William\\HighImportance\\GIT_ROOT\\RayTracerChallenge\\RayTracerChallenge\\data\\face_with_normals.obj");

        Group g = result.defaultGroup;
        SmoothTriangle t1 = (SmoothTriangle) g.getChildren().get(0);
        SmoothTriangle t2 = (SmoothTriangle) g.getChildren().get(1);

        assertTrue(t1.p1.isIdentical(result.vertices.get(0)));
        assertTrue(t1.p2.isIdentical(result.vertices.get(1)));
        assertTrue(t1.p3.isIdentical(result.vertices.get(2)));

        assertTrue(t1.n1.isIdentical(result.normals.get(2)));
        assertTrue(t1.n2.isIdentical(result.normals.get(0)));
        assertTrue(t1.n3.isIdentical(result.normals.get(1)));

        assertTrue(t1.p1.isIdentical(t2.p1));
        assertTrue(t1.p2.isIdentical(t2.p2));
        assertTrue(t1.p3.isIdentical(t2.p3));

        assertTrue(t1.n1.isIdentical(t2.n1));
        assertTrue(t1.n2.isIdentical(t2.n2));
        assertTrue(t1.n3.isIdentical(t2.n3));
    }
}
