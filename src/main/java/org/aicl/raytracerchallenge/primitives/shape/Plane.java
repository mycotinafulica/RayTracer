package org.aicl.raytracerchallenge.primitives.shape;

import org.aicl.raytracerchallenge.primitives.Constant;
import org.aicl.raytracerchallenge.primitives.Tuple;
import org.aicl.raytracerchallenge.primitives.Vector;
import org.aicl.raytracerchallenge.primitives.ray.Ray;
import org.aicl.raytracerchallenge.primitives.ray.RayIntersection;

import java.util.List;
import java.util.UUID;

public class Plane extends Shape{
    private String id;

    public Plane(){
        super();
        id = UUID.randomUUID().toString();
    }

    @Override
    public RayIntersection localIntersect(Ray transformedRay) {
        if(Math.abs(transformedRay.direction.y) < Constant.epsilon){
            return new RayIntersection(0, List.of());
        }

        double time = -transformedRay.origin.y/transformedRay.direction.y;
        return new RayIntersection(time, this);
    }

    @Override
    public Tuple localNormal(Tuple objectPoint) {
        return new Vector(0, 1, 0);
    }

    @Override
    public boolean isSame(Shape input) {
        return (input instanceof Plane) && id.equals(input.getId());
    }

    @Override
    public boolean isSameCharacteristics(Shape shape) {
        if (shape instanceof Plane) {
            if (transform.isEqual(shape.getTransform())) {
                if (material.isIdentical(shape.getMaterial()))
                    return true;
                else
                    return false;
            }
            return false;
        }
        return false;
    }

    @Override
    public String getId() {
        return id;
    }
}
