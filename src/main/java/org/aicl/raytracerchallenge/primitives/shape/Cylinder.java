package org.aicl.raytracerchallenge.primitives.shape;

import org.aicl.raytracerchallenge.primitives.Tuple;
import org.aicl.raytracerchallenge.primitives.ray.Intersection;
import org.aicl.raytracerchallenge.primitives.ray.Ray;
import org.aicl.raytracerchallenge.primitives.ray.RayIntersection;
import org.aicl.raytracerchallenge.utilities.FloatEquality;

import java.util.List;
import java.util.UUID;

public class Cylinder extends Shape{
    private String id;

    public Cylinder(){
        super();
        id = UUID.randomUUID().toString();
    }

    @Override
    public RayIntersection localIntersect(Ray transformedRay) {
        double a = transformedRay.direction.x*transformedRay.direction.x + transformedRay.direction.z*transformedRay.direction.z;
        if(FloatEquality.isEqual(a, 0))
            return new RayIntersection(0, List.of());

        double b = 2 * transformedRay.origin.x * transformedRay.direction.x
                + 2 * transformedRay.origin.z * transformedRay.direction.z;
        double c = transformedRay.origin.x*transformedRay.origin.x + transformedRay.origin.z*transformedRay.origin.z - 1;

        double disc = (b*b) - (4 * a * c);

        if(disc < 0)
            return new RayIntersection(0, List.of());

        double t0 = (-b - Math.sqrt(disc))/(2* a);
        double t1 = (-b + Math.sqrt(disc))/(2* a);
        return new RayIntersection(2, List.of(
                new Intersection(t0, this),
                new Intersection(t1, this)
        ));
    }

    @Override
    public Tuple localNormal(Tuple objectPoint) {
        return null;
    }

    @Override
    public boolean isSame(Shape input) {
        return (input instanceof Cylinder) && id.equals(input.getId());
    }

    @Override
    public boolean isSameCharacteristics(Shape shape) {
        return isSame(shape);
    }

    @Override
    public String getId() {
        return id;
    }
}
