package org.aicl.raytracerchallenge.puttogether.chapter1;

import org.aicl.raytracerchallenge.primitives.Point;
import org.aicl.raytracerchallenge.primitives.Vector;
import org.junit.jupiter.api.Test;

public class Chapter1PutTogether {
    @Test
    public void putTogether(){
        Vector initialVelocity = new Vector ((new Vector(1, 1, 0)).normalize().multiply(10));
        Projectile p  = new Projectile(new Point(0, 1, 0), initialVelocity);
        Environment e = new Environment(new Vector(0, -0.1, 0), new Vector(-0.01, 0,0));

        while(p.position.y > 0){
            p = tick(e, p);
            System.out.println("Current position : " + "[" + p.position.x + ", " + p.position.y + "]");
        }
    }

    private Projectile tick(Environment e, Projectile p){
        Point position  = new Point(p.position.add(p.velocity));
        Vector velocity = new Vector(p.velocity.add(e.gravity).add(e.wind));
        return new Projectile(position, velocity);
    }
}
