package org.aicl.raytracerchallenge.primitives.shape;

import org.aicl.raytracerchallenge.primitives.Matrix;
import org.aicl.raytracerchallenge.primitives.Ray;
import org.aicl.raytracerchallenge.primitives.RayIntersection;
import org.aicl.raytracerchallenge.transformation.RayTransformer;

public interface Shape {
    RayIntersection intersect(Ray r);
    boolean isSame(Shape input);
    String getId();
    Matrix getTransform();

    RayTransformer.Type getTransformType();
    void setTransform(Matrix t, RayTransformer.Type transformType);
}
