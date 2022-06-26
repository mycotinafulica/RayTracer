package org.aicl.raytracerchallenge.transformation;

import org.aicl.raytracerchallenge.primitives.*;
import org.aicl.raytracerchallenge.primitives.ray.Ray;

public class RayTransformer {
    public static Ray transform(Ray ray, Matrix matrix){
        Tuple newOrigin    = matrix.multiply(ray.origin);
        Tuple newDirection = matrix.multiply(ray.direction);
        return new Ray(new Point(newOrigin), new Vector(newDirection));
    }
}
