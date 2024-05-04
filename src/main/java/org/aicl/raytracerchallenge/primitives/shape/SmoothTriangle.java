package org.aicl.raytracerchallenge.primitives.shape;

import org.aicl.raytracerchallenge.primitives.*;
import org.aicl.raytracerchallenge.primitives.ray.Intersection;
import org.aicl.raytracerchallenge.primitives.ray.Ray;
import org.aicl.raytracerchallenge.primitives.ray.RayIntersection;

import java.util.List;
import java.util.UUID;

public class SmoothTriangle extends Triangle{
    public Vector n1;
    public Vector n2;
    public Vector n3;

    public SmoothTriangle(Point p1, Point p2, Point p3, Vector n1, Vector n2, Vector n3){
        super(p1, p2, p3);

        this.n1 = n1;
        this.n2 = n2;
        this.n3 = n3;
    }

    @Override
    public RayIntersection localIntersect(Ray transformedRay) {
        Vector directionCrossE2 = TupleOperation.cross(transformedRay.direction, e2);
        double det = TupleOperation.dot(e1, directionCrossE2);
        if(Math.abs(det) <= Constant.epsilon)
            return new RayIntersection(0, List.of());

        double f = 1.0/det;
        Tuple p1ToRayOrigin = transformedRay.origin.subtract(p1);
        double u = f * TupleOperation.dot(p1ToRayOrigin, directionCrossE2);
        if(u < 0 || u > 1)
            return new RayIntersection(0, List.of());

        Vector originCrossE1 = TupleOperation.cross(p1ToRayOrigin, e1);
        double v = f* TupleOperation.dot(transformedRay.direction, originCrossE1);
        if(v < 0 || (u + v) > 1)
            return new RayIntersection(0, List.of());

        double t = f* TupleOperation.dot(e2, originCrossE1);
        return new RayIntersection(1, List.of(new Intersection( t, this, u, v)));
    }

    @Override
    public Tuple localNormal(Tuple objectPoint, Intersection hit) {
        Tuple part1 = n2.multiply(hit.u);
        Tuple part2 = n3.multiply(hit.v);
        Tuple part3 = n1.multiply(1 - hit.u - hit.v);

        return part1.add(part2).add(part3);
    }

    public Intersection intersectionWithUV(double t, Shape intersectedObj, double u, double v){
        return new Intersection(t, intersectedObj, u, v);
    }

    @Override
    public boolean isSame(Shape input) {
        return (input instanceof SmoothTriangle) && id.equals(input.getId());
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
