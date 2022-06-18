package org.aicl.raytracerchallenge.transformation;

import org.aicl.raytracerchallenge.primitives.*;

public class RayTransformer {
    public enum Type {
        TRANSLATION,
        SCALING,
        ROTATION,
        IDENTITY
    }
    public static Ray transform(Ray ray, Matrix matrix, RayTransformer.Type transformType){
        //for rotation and scale, it also affects the direction
        if(transformType != Type.TRANSLATION){
            Tuple newOrigin    = matrix.multiply(ray.origin);
            Tuple newDirection = matrix.multiply(ray.direction);
            return new Ray(new Point(newOrigin), new Vector(newDirection));
        }
        else {
            Tuple newOrigin = matrix.multiply(ray.origin);
            return new Ray(new Point(newOrigin), new Vector(ray.direction));
        }
    }
}
