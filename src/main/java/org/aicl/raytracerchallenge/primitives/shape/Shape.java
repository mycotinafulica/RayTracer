package org.aicl.raytracerchallenge.primitives.shape;

import org.aicl.raytracerchallenge.primitives.*;
import org.aicl.raytracerchallenge.transformation.RayTransformer;

public interface Shape {
    RayIntersection intersect(Ray r);
    boolean isSame(Shape input);
    String getId();
    Matrix getTransform();
    void setTransform(Matrix m);
    Vector normalAt(Point p);

    void setMaterial(Material m);

    Material getMaterial();
}
