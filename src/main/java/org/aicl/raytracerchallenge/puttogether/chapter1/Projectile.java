package org.aicl.raytracerchallenge.puttogether.chapter1;

import org.aicl.raytracerchallenge.geometry.Point;
import org.aicl.raytracerchallenge.geometry.Vector;

public class Projectile {
    public Point position;
    public Vector velocity;

    public Projectile(Point position, Vector velocity){
        this.position = position;
        this.velocity = velocity;
    }
}
