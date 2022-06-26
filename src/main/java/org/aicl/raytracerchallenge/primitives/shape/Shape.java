package org.aicl.raytracerchallenge.primitives.shape;

import org.aicl.raytracerchallenge.primitives.*;
import org.aicl.raytracerchallenge.primitives.ray.Ray;
import org.aicl.raytracerchallenge.primitives.ray.RayIntersection;

public interface Shape {
    RayIntersection intersect(Ray r);
    boolean isSame(Shape input);

    boolean isSameCharacteristics(Shape shape);
    String getId();
    Matrix getTransform();
    void setTransform(Matrix m);
    Vector normalAt(Point p);

    void setMaterial(Material m);

    Material getMaterial();
}
