package org.aicl.raytracerchallenge.puttogether.chapter7;

import org.aicl.raytracerchallenge.Camera;
import org.aicl.raytracerchallenge.World;
import org.aicl.raytracerchallenge.display.Canvas;
import org.aicl.raytracerchallenge.primitives.Color;
import org.aicl.raytracerchallenge.primitives.Material;
import org.aicl.raytracerchallenge.primitives.Point;
import org.aicl.raytracerchallenge.primitives.Vector;
import org.aicl.raytracerchallenge.primitives.light.PointLight;
import org.aicl.raytracerchallenge.primitives.shape.Sphere;
import org.aicl.raytracerchallenge.transformation.TransformMatrixGenerator;
import org.junit.jupiter.api.Test;

import java.io.IOException;

public class Chapter7PutTogether {
    @Test
    public void letsPutTogether() throws IOException {
        TransformMatrixGenerator generator = new TransformMatrixGenerator();
        Sphere floor = new Sphere();
        floor.setTransform(generator.scale(10, 0.1, 10));
        Material floorMaterial = new Material();
        floorMaterial.color = new Color(1, 0.9, 0.9);
        floorMaterial.specular = 0;
        floor.setMaterial(floorMaterial);

        Sphere leftWall = new Sphere();
        leftWall.setTransform(generator.translate(0, 0, 5)
                .multiply(generator.rotateY(-Math.PI/4.0)
                        .multiply(generator.rotateX(Math.PI/2.0)
                                .multiply(generator.scale(10, 0.01, 10)))));
        leftWall.setMaterial(floorMaterial);

        Sphere rightWall = new Sphere();
        rightWall.setTransform(generator.translate(0, 0, 5)
                .multiply(generator.rotateY(Math.PI/4.0)
                        .multiply(generator.rotateX(Math.PI/2.0)
                                .multiply(generator.scale(10, 0.01, 10)))));
        rightWall.setMaterial(floorMaterial);

        Sphere middle = new Sphere();
        middle.setTransform(generator.translate(-0.5, 1, 0.5));
        middle.setMaterial(new Material());
        middle.getMaterial().color = new Color(0.1, 1, 0.5);
        middle.getMaterial().diffuse = 0.7;
        middle.getMaterial().specular = 0.3;

        Sphere right = new Sphere();
        right.setTransform(generator.translate(1.5,1.5, -0.5).multiply(generator.scale(0.5, 0.5, 0.5)));
        right.setMaterial(new Material());
        right.getMaterial().color = new Color(0.5, 1, 0.1);
        right.getMaterial().diffuse = 0.7;
        right.getMaterial().specular = 0.3;

        Sphere left = new Sphere();
        left.setTransform(generator.translate(-1.5, 0.33, -0.75).multiply(generator.scale(0.33, 0.33, 0.33)));
        left.setMaterial(new Material());
        left.getMaterial().color = new Color(1, 0.8, 0.1);
        left.getMaterial().diffuse = 0.7;
        left.getMaterial().specular = 0.3;

        World world = new World();
        world.addObject(floor);
        world.addObject(leftWall);
        world.addObject(rightWall);
        world.addObject(middle);
        world.addObject(left);
        world.addObject(right);
        world.addLight(new PointLight(new Point(-10, 10, -10), new Color(3, 3, 3)));
        Camera camera = new Camera(400, 200, Math.PI/3.0);
        camera.transform = TransformMatrixGenerator.viewTransform(new Point(0, 1.5, -5),
                new Point(0, 1, 0), new Vector(0, 1, 0));

        Canvas c  = camera.render(world);
        c.writeToPpmFile("E:\\William\\HighImportance\\GIT_ROOT\\RayTracerChallenge\\RayTracerChallenge\\test\\put_together_chapter7.ppm");
    }
}
