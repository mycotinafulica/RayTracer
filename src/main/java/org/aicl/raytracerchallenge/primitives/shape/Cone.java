package org.aicl.raytracerchallenge.primitives.shape;

import org.aicl.raytracerchallenge.primitives.Tuple;
import org.aicl.raytracerchallenge.primitives.ray.Intersection;
import org.aicl.raytracerchallenge.primitives.ray.Ray;
import org.aicl.raytracerchallenge.primitives.ray.RayIntersection;
import org.aicl.raytracerchallenge.utilities.FloatEquality;

import java.util.List;
import java.util.UUID;

public class Cone extends Shape{

    private String id;

    private double maximum = 999999;
    private double minimum = -999999;

    public Cone(){
        super();
        id = UUID.randomUUID().toString();
    }

    @Override
    public RayIntersection localIntersect(Ray transformedRay) {
        double a = Math.pow(transformedRay.direction.x, 2) - Math.pow(transformedRay.direction.y, 2) + Math.pow(transformedRay.direction.z, 2);

        double b = 2 * transformedRay.origin.x * transformedRay.direction.x
                - 2 * transformedRay.origin.y * transformedRay.direction.y
                + 2 * transformedRay.origin.z * transformedRay.direction.z;
        double c = Math.pow(transformedRay.origin.x, 2) -  Math.pow(transformedRay.origin.y, 2) +  Math.pow(transformedRay.origin.z, 2);

        if(FloatEquality.isEqual(a, 0) && FloatEquality.isEqual(b, 0)){
            return null;
            //TODO
//            return intersectCaps(transformedRay, new RayIntersection(0, List.of()));
        }

        if(FloatEquality.isEqual(a, 0) && !FloatEquality.isEqual(b, 0)){
            double t = -c/(2*b);
            return new RayIntersection(1, List.of(new Intersection(t, this)));
        }

        double disc = (b*b) - (4 * a * c);

        if(disc < 0)
            return new RayIntersection(0, List.of());

        double t0 = (-b - Math.sqrt(disc))/(2* a);
        double t1 = (-b + Math.sqrt(disc))/(2* a);

        if(t0 > t1) {
            double temp = t0;
            t0 = t1;
            t1 = temp;
        }

        RayIntersection intersections = new RayIntersection(0, List.of());
        double y0 = transformedRay.origin.y + t0 * transformedRay.direction.y;
        if(minimum < y0 && y0 < maximum) {
            intersections.addIntersection(new Intersection(t0, this));
        }

        double y1 = transformedRay.origin.y + t1 * transformedRay.direction.y;
        if(minimum < y1 && y1 < maximum) {
            intersections.addIntersection(new Intersection(t1, this));
        }

        intersections.sort();

        return intersections;
//        return intersectCaps(transformedRay, intersections);
    }

    @Override
    public Tuple localNormal(Tuple objectPoint) {
        return null;
    }

    @Override
    public boolean isSame(Shape input) {
        return (input instanceof Cone) && id.equals(input.getId());
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
