package org.aicl.raytracerchallenge.puttogether.chapter2;

import org.aicl.raytracerchallenge.display.Canvas;
import org.aicl.raytracerchallenge.primitives.Color;
import org.aicl.raytracerchallenge.primitives.Point;
import org.aicl.raytracerchallenge.primitives.Vector;
import org.aicl.raytracerchallenge.puttogether.chapter1.Environment;
import org.aicl.raytracerchallenge.puttogether.chapter1.Projectile;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.ArrayList;

public class Chapter2PutTogether {
    @Test
    public void letsPutTogether(){
        Vector initialVelocity = (Vector) (new Vector(0.74, 1.3, 0)).normalize().multiplySelf(11.25);
        Point startPoint = new Point(0, 1, 0);
        Projectile p  = new Projectile(startPoint, initialVelocity);

        Vector gravity = new Vector(0, -0.1, 0);
        Vector winds = new Vector(-0.01, 0,0);
        Environment e = new Environment(gravity, winds);

        ArrayList<Point> tracks = new ArrayList<>();
        while(p.position.y > 0){
            tracks.add(p.position);
            p = tick(e, p);
        }

        Canvas c = new Canvas(900, 550);
        for(int i = 0 ; i< tracks.size(); i++){
            System.out.println("position : " + "[" + tracks.get(i).x + ", " + tracks.get(i).y + "]");
            int xRounded = (int) tracks.get(i).x;
            int yRounded = (int) tracks.get(i).y;
            if(xRounded < 900 && yRounded < 550){
                c.writePixel(xRounded, 550 - yRounded, new Color(1, 0, 0));
            }
        }
        try {
            c.writeToPpmFile("E:\\William\\HighImportance\\GIT_ROOT\\RayTracerChallenge\\RayTracerChallenge\\test\\put_together_chap2.ppm");
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }

    private Projectile tick(Environment e, Projectile p){
        Point position  = new Point(p.position.add(p.velocity));
        Vector velocity = new Vector(p.velocity.add(e.gravity).add(e.wind));
        return new Projectile(position, velocity);
    }
}
