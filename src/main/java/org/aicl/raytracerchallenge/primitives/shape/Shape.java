package org.aicl.raytracerchallenge.primitives.shape;

import org.aicl.raytracerchallenge.primitives.Ray;
import org.aicl.raytracerchallenge.primitives.RayIntersection;

public interface Shape {
    RayIntersection intersect(Ray r);

    boolean isSame(Shape input);

    String getId();
}
