package org.aicl.raytracerchallenge.primitives.shape;

import org.aicl.raytracerchallenge.primitives.Point;
import org.aicl.raytracerchallenge.primitives.Tuple;
import org.aicl.raytracerchallenge.primitives.TupleOperation;
import org.aicl.raytracerchallenge.primitives.Vector;
import org.aicl.raytracerchallenge.primitives.ray.Ray;
import org.aicl.raytracerchallenge.primitives.ray.RayIntersection;

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
        return null;
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
