package org.aicl.raytracerchallenge.puttogether.chapter12;

import org.aicl.raytracerchallenge.Camera;
import org.aicl.raytracerchallenge.World;
import org.aicl.raytracerchallenge.display.Canvas;
import org.aicl.raytracerchallenge.primitives.Color;
import org.aicl.raytracerchallenge.primitives.Point;
import org.aicl.raytracerchallenge.primitives.Vector;
import org.aicl.raytracerchallenge.primitives.light.PointLight;
import org.aicl.raytracerchallenge.primitives.pattern.CheckerPattern;
import org.aicl.raytracerchallenge.primitives.pattern.StripePattern;
import org.aicl.raytracerchallenge.primitives.shape.Cube;
import org.aicl.raytracerchallenge.primitives.shape.Plane;
import org.aicl.raytracerchallenge.primitives.shape.Sphere;
import org.aicl.raytracerchallenge.transformation.TransformMatrixGenerator;
import org.junit.jupiter.api.Test;

import java.io.IOException;

public class PutTogetherChapter12 {
    @Test
    public void letsPutTogether() throws IOException {
        TransformMatrixGenerator generator = new TransformMatrixGenerator();
        Plane floor = new Plane();
        floor.getMaterial().pattern = new CheckerPattern(new Color(235./255, 233./255, 230./255), new Color(84./255, 84./255, 84./255));
        floor.getMaterial().pattern.setTransform(generator.rotateY(Math.PI/3.0));
        floor.getMaterial().reflective = 0.7;
        floor.getMaterial().diffuse = 0.1;

        Plane leftWall = new Plane();
        leftWall.setTransform(generator.translate(0, 0, 10).multiply(generator.rotateX(Math.PI/2.0)
                .multiply(generator.rotateZ(Math.PI/4.0))));
        leftWall.getMaterial().pattern = new StripePattern(new Color(235./255, 233./255, 230./255), new Color(84./255, 84./255, 84./255));
        leftWall.getMaterial().pattern.setTransform(generator.scale(0.5,0.5, 0.5)
                .multiply(generator.rotateY(Math.PI/2.0)));
//        leftWall.getMaterial().reflective = 0.7;
        leftWall.getMaterial().diffuse = 0.1;

        Plane rightWall = new Plane();
        rightWall.setTransform(generator.translate(0, 0, 10).multiply(generator.rotateX(Math.PI/2.0)
                .multiply(generator.rotateZ(-Math.PI/4.0))));
        rightWall.getMaterial().pattern = new StripePattern(new Color(235./255, 233./255, 230./255), new Color(84./255, 84./255, 84./255));
        rightWall.getMaterial().pattern.setTransform(generator.scale(0.5,0.5, 0.5)
                .multiply(generator.rotateY(Math.PI/2.0)));
//        rightWall.getMaterial().reflective = 0.7;
        rightWall.getMaterial().diffuse = 0.1;


        Cube middle = new Cube();

        middle.setTransform(generator.translate(0, 1, 1.25).multiply(generator.rotateY(Math.PI/4).multiply(generator.scale(1.5, 1.5, 1.5))));

        middle.getMaterial().color = new Color(71./255, 255./255, 120./255);
        middle.getMaterial().diffuse = 0.3;
        middle.getMaterial().specular = 1;
        middle.getMaterial().shininess = 300;
        middle.getMaterial().transparency = 0.7;
        middle.getMaterial().reflective = 0.9;
        middle.getMaterial().refractiveIndex = 0.4;

        Sphere sphere = new Sphere();
        sphere.setTransform(generator.translate(0, 1.3, 1.25).multiply(generator.scale(0.9, 0.9, 0.9)));

        sphere.getMaterial().color = new Color(355./255, 87./255, 87./255);
        sphere.getMaterial().diffuse = 0.4;
        sphere.getMaterial().specular = 1;
        sphere.getMaterial().shininess = 300;
        sphere.getMaterial().transparency = 0.4;
        sphere.getMaterial().reflective = 0.9;
        sphere.getMaterial().refractiveIndex = 0.4;

        World world = new World();
        world.addObject(floor);
        world.addObject(leftWall);
        world.addObject(rightWall);
        world.addObject(middle);
        world.addObject(sphere);
        world.addLight(new PointLight(new Point(0, 1.3, 1.25), new Color(1, 1, 1)));
        Camera camera = new Camera(192, 108, Math.PI/3.0);
        camera.transform = TransformMatrixGenerator.viewTransform(new Point(0, 1.5, -5),
                new Point(0, 1, 0), new Vector(0, 1, 0));

        Canvas c  = camera.render(world);
        c.writeToPpmFile("E:\\William\\HighImportance\\GIT_ROOT\\RayTracerChallenge\\RayTracerChallenge\\test\\put_together_chapter12.ppm");
    }
}
