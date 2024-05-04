package org.aicl.raytracerchallenge.puttogether.chapter14;

import org.aicl.raytracerchallenge.Camera;
import org.aicl.raytracerchallenge.World;
import org.aicl.raytracerchallenge.display.Canvas;
import org.aicl.raytracerchallenge.primitives.Color;
import org.aicl.raytracerchallenge.primitives.Point;
import org.aicl.raytracerchallenge.primitives.Vector;
import org.aicl.raytracerchallenge.primitives.light.PointLight;
import org.aicl.raytracerchallenge.primitives.pattern.CheckerPattern;
import org.aicl.raytracerchallenge.primitives.pattern.StripePattern;
import org.aicl.raytracerchallenge.primitives.shape.*;
import org.aicl.raytracerchallenge.transformation.TransformMatrixGenerator;
import org.junit.jupiter.api.Test;

import java.io.IOException;

public class PutTogetherChapter14 {
    @Test
    public void letsPutTogether() throws IOException {
        TransformMatrixGenerator generator = new TransformMatrixGenerator();
        Group hex = new Group();

        for(int i = 0; i < 6; i++){
            Shape side = hexagonSide();
            side.setTransform(generator.rotateY(i * Math.PI/3.0));
            hex.addChild(side);
        }

        World world = new World();
        world.addObject(hex);
        world.addLight(new PointLight(new Point(0, 1.3, 1.25), new Color(1, 1, 1)));
        Camera camera = new Camera(200, 400, Math.PI/3.0);
        camera.transform = TransformMatrixGenerator.viewTransform(new Point(0, 1.5, -5),
                new Point(0, 1, 0), new Vector(0, 1, 0));

        Canvas c  = camera.render(world);
        c.writeToPpmFile("E:\\William\\HighImportance\\GIT_ROOT\\RayTracerChallenge\\RayTracerChallenge\\test\\put_together_chapter14.ppm");
    }

    private Shape hexagonCorner(){
        TransformMatrixGenerator generator = new TransformMatrixGenerator();
        Sphere corner = new Sphere();
        corner.setTransform(generator.translate(0, 0, -1).multiply(generator.scale(0.25, 0.25, 0.25)));
        return corner;
    }

    private Shape hexagonEdge(){
        TransformMatrixGenerator generator = new TransformMatrixGenerator();
        Cylinder edge = new Cylinder();
        edge.setMinimum(0);
        edge.setMaximum(1);
        edge.setTransform(generator.translate(0, 0, -1)
                .multiply(generator.rotateY(-Math.PI/6.0)
                        .multiply(generator.rotateZ(-Math.PI/2.0)
                                .multiply(generator.scale(0.25, 1, 0.25)))));

        return edge;
    }

    public Shape hexagonSide(){
        Group group = new Group();
        group.addChild(hexagonCorner());
        group.addChild(hexagonEdge());

        return group;
    }
}
