package org.aicl.raytracerchallenge.puttogether.chapter11;

import org.aicl.raytracerchallenge.Camera;
import org.aicl.raytracerchallenge.World;
import org.aicl.raytracerchallenge.display.Canvas;
import org.aicl.raytracerchallenge.primitives.Color;
import org.aicl.raytracerchallenge.primitives.Material;
import org.aicl.raytracerchallenge.primitives.Point;
import org.aicl.raytracerchallenge.primitives.Vector;
import org.aicl.raytracerchallenge.primitives.light.PointLight;
import org.aicl.raytracerchallenge.primitives.pattern.CheckerPattern;
import org.aicl.raytracerchallenge.primitives.pattern.GradientPattern;
import org.aicl.raytracerchallenge.primitives.pattern.StripePattern;
import org.aicl.raytracerchallenge.primitives.shape.Plane;
import org.aicl.raytracerchallenge.primitives.shape.Sphere;
import org.aicl.raytracerchallenge.transformation.TransformMatrixGenerator;
import org.junit.jupiter.api.Test;

import java.io.IOException;

public class PutTogetherChapter11 {
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
        rightWall.getMaterial().diffuse = 0.1;


        Sphere middle = new Sphere();
        middle.setTransform(generator.translate(0.5, 1, 0.5));

        middle.getMaterial().color = new Color(1, 0, 0);
        middle.getMaterial().diffuse = 0.1;
        middle.getMaterial().specular = 1;
        middle.getMaterial().shininess = 300;
        middle.getMaterial().transparency = 0.4;
        middle.getMaterial().reflective = 0.9;
        middle.getMaterial().refractiveIndex = 0.4;

        Sphere sphere3 = new Sphere();
        sphere3.setTransform(generator.translate(-1.5,1.5, 1.5).multiply(generator.scale(1.3, 1.3, 1.3)));
        sphere3.getMaterial().color = new Color(0.5, 1, 0.1);
        sphere3.getMaterial().diffuse = 0.1;
        sphere3.getMaterial().specular = 1;
        sphere3.getMaterial().shininess = 300;
        sphere3.getMaterial().reflective = 0.9;

        Sphere left = new Sphere();
        left.setTransform(generator.translate(-0.2, 0.33, -0.75).multiply(generator.scale(0.33, 0.33, 0.33)));
        left.getMaterial().color = new Color(0.5, 1, 0.1);
        left.getMaterial().diffuse = 0.1;
        left.getMaterial().specular = 1;
        left.getMaterial().shininess = 300;
        left.getMaterial().transparency = 0.4;
        left.getMaterial().reflective = 0.9;
        left.getMaterial().refractiveIndex = 1.5;

        Sphere sphere4 = new Sphere();
        sphere4.setTransform(generator.translate(1.5,0.5, 1.5).multiply(generator.scale(0.5, 0.5, 0.53)));
        sphere4.getMaterial().color = new Color(1, 0, 0);
        sphere4.getMaterial().specular = 1;
        sphere4.getMaterial().shininess = 300;

        World world = new World();
        world.addObject(floor);
        world.addObject(leftWall);
        world.addObject(rightWall);
        world.addObject(middle);
        world.addObject(left);
        world.addObject(sphere3);
        world.addObject(sphere4);
        world.addLight(new PointLight(new Point(20, 40, -40), new Color(1, 1, 1)));
        Camera camera = new Camera(192, 108, Math.PI/3.0);
        camera.transform = TransformMatrixGenerator.viewTransform(new Point(0, 1.5, -5),
                new Point(0, 1, 0), new Vector(0, 1, 0));

        Canvas c  = camera.render(world);
        c.writeToPpmFile("E:\\William\\HighImportance\\GIT_ROOT\\RayTracerChallenge\\RayTracerChallenge\\test\\put_together_chapter11.ppm");
    }
}
