package org.aicl.raytracerchallenge.primitives;

public class Ray {
    public Point origin;
    public Vector direction;

    public Ray(Point origin, Vector direction){
        this.origin    = origin;
        this.direction = direction;
    }

    public Tuple position(double time){
        return origin.add(direction.multiply(time));
    }
}
