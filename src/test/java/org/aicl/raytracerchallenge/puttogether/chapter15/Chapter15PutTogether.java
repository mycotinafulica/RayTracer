package org.aicl.raytracerchallenge.puttogether.chapter15;

import org.aicl.raytracerchallenge.Camera;
import org.aicl.raytracerchallenge.World;
import org.aicl.raytracerchallenge.display.Canvas;
import org.aicl.raytracerchallenge.parser.WavefrontObjParser;
import org.aicl.raytracerchallenge.parser.WavefrontScene;
import org.aicl.raytracerchallenge.primitives.Color;
import org.aicl.raytracerchallenge.primitives.Material;
import org.aicl.raytracerchallenge.primitives.Point;
import org.aicl.raytracerchallenge.primitives.Vector;
import org.aicl.raytracerchallenge.primitives.light.PointLight;
import org.aicl.raytracerchallenge.primitives.pattern.CheckerPattern;
import org.aicl.raytracerchallenge.primitives.pattern.StripePattern;
import org.aicl.raytracerchallenge.primitives.shape.Group;
import org.aicl.raytracerchallenge.primitives.shape.Plane;
import org.aicl.raytracerchallenge.primitives.shape.Sphere;
import org.aicl.raytracerchallenge.transformation.TransformMatrixGenerator;
import org.junit.jupiter.api.Test;

import java.io.IOException;

public class Chapter15PutTogether {
    @Test
    public void letsPutTogether() throws IOException {
        TransformMatrixGenerator generator = new TransformMatrixGenerator();
        Plane floor = new Plane();
        floor.getMaterial().pattern = new CheckerPattern(new Color(133./255., 133./255., 133./255.)
                , new Color(209./255., 209./255., 209./255.));

        Plane wall = new Plane();
        wall.setTransform(generator.translate(0, 0, 10).multiply(generator.rotateX(Math.PI/2.0)));
        wall.getMaterial().pattern = new CheckerPattern(new Color(133./255., 133./255., 133./255.)
                , new Color(209./255., 209./255., 209./255.));

        WavefrontObjParser parser = new WavefrontObjParser();
        WavefrontScene result = parser.parse("E:\\William\\HighImportance\\GIT_ROOT\\RayTracerChallenge\\RayTracerChallenge\\data\\teapot-low.obj");

        Group g = result.convertToSingleGroup();
        g.setTransform(generator.translate(0, 0.5, -8)
                .multiply(generator.scale(0.2, 0.2, 0.2)
                        .multiply(generator.rotateX(-Math.PI/2)))

        );

        World world = new World();
        world.addObject(floor);
        world.addObject(wall);
        world.addObject(g);
        world.addLight(new PointLight(new Point(-10, 10, -20), new Color(1, 1, 1)));
        Camera camera = new Camera(1920, 1080, Math.PI/3.0);
        camera.transform = TransformMatrixGenerator.viewTransform(new Point(0, 1.5, -20),
                new Point(0, 1, 0), new Vector(0, 1, 0));

        Canvas c  = camera.render(world);
        c.writeToPpmFile("E:\\William\\HighImportance\\GIT_ROOT\\RayTracerChallenge\\RayTracerChallenge\\test\\put_together_chapter15.ppm");
    }
}
