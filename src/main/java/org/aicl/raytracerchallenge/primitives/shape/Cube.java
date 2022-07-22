package org.aicl.raytracerchallenge.primitives.shape;

import kotlin.Pair;
import org.aicl.raytracerchallenge.primitives.Constant;
import org.aicl.raytracerchallenge.primitives.Tuple;
import org.aicl.raytracerchallenge.primitives.Vector;
import org.aicl.raytracerchallenge.primitives.ray.Intersection;
import org.aicl.raytracerchallenge.primitives.ray.Ray;
import org.aicl.raytracerchallenge.primitives.ray.RayIntersection;

import java.util.List;
import java.util.UUID;

public class Cube extends Shape {
    private String id;

    public Cube() {
        super();
        id = UUID.randomUUID().toString();
    }

    @Override
    public RayIntersection localIntersect(Ray transformedRay) {
        Pair<Double, Double> xPair = checkAxis(transformedRay.origin.x, transformedRay.direction.x);
        Pair<Double, Double> yPair = checkAxis(transformedRay.origin.y, transformedRay.direction.y);
        Pair<Double, Double> zPair = checkAxis(transformedRay.origin.z, transformedRay.direction.z);

        double tmin = Math.max(xPair.component1(), Math.max(yPair.component1(), zPair.component1()));
        double tmax = Math.min(xPair.component2(), Math.min(yPair.component2(), zPair.component2()));

        return new RayIntersection(2, List.of(
                new Intersection(tmin, this),
                new Intersection(tmax, this)
        ));
    }

    @Override
    public Tuple localNormal(Tuple objectPoint) {
        return null;
    }

    @Override
    public boolean isSame(Shape input) {
        return (input instanceof Cube) && id.equals(input.getId());
    }

    @Override
    public boolean isSameCharacteristics(Shape shape) {
        return isSame(shape);
    }

    @Override
    public String getId() {
        return id;
    }

    private Pair<Double, Double> checkAxis(double origin, double direction){
        double tMinNumerator = -1 - origin;
        double tMaxNumerator = 1 - origin;

        double tmin, tmax;
        if(Math.abs(direction) > Constant.epsilon){
            tmin = tMinNumerator/direction;
            tmax = tMaxNumerator/direction;
        }
        else{
            tmin = tMinNumerator * 999999;
            tmax = tMaxNumerator * 999999;
        }
        if(tmin > tmax){
            return new Pair<>(tmax, tmin);
        }
        else{
            return new Pair<>(tmin, tmax);
        }
    }
}
