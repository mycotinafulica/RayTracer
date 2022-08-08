package org.aicl.raytracerchallenge.primitives.shape;

import org.aicl.raytracerchallenge.primitives.*;
import org.aicl.raytracerchallenge.primitives.ray.Intersection;
import org.aicl.raytracerchallenge.primitives.ray.Ray;
import org.aicl.raytracerchallenge.primitives.ray.RayIntersection;

import java.util.List;
import java.util.UUID;

public class Triangle extends Shape{
    private String id;

    public Point p1;
    public Point p2;
    public Point p3;

    public Vector e1;
    public Vector e2;
    public Vector normal;

    public Triangle(){
        super();
        id = UUID.randomUUID().toString();
    }

    public Triangle(Point p1, Point p2, Point p3){
        this();
        this.p1 = p1;
        this.p2 = p2;
        this.p3 = p3;

        e1 = new Vector(p2.subtract(p1));
        e2 = new Vector(p3.subtract(p1));
        normal = new Vector(TupleOperation.cross(e2, e1).normalize());
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
        return new RayIntersection(1, List.of(new Intersection( t, this)));
    }

    @Override
    public Tuple localNormal(Tuple objectPoint) {
        return normal;
    }

    @Override
    public boolean isSame(Shape input) {
        return (input instanceof Triangle) && id.equals(input.getId());
    }

    @Override
    public boolean isSameCharacteristics(Shape shape) {
        return isSame(shape);
    }

    @Override
    public String getId() {
        return null;
    }
}
