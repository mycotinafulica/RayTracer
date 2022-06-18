package org.aicl.raytracerchallenge.primitives.shape;

import org.aicl.raytracerchallenge.primitives.*;
import org.aicl.raytracerchallenge.transformation.RayTransformer;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

public class Sphere implements Shape {
    public double radius = 1.0;
    public Point origin = new Point(0, 0, 0);

    private final String id;

    private Matrix transform                  = Matrix.identity();
    private RayTransformer.Type transformType = RayTransformer.Type.IDENTITY;

    public Sphere(){
        id = UUID.randomUUID().toString();
    }

    public Sphere(double radius, Point origin){
        this();
        this.radius = radius;
        this.origin = origin;
    }

    @Override
    public RayIntersection intersect(Ray r) {
        Ray transformedRay = RayTransformer.transform(r, transform.inverse(), transformType);
        //normalize so that we play where the center of the coordinate is (0, 0)
        //simplifying (x-x0)^2 + (y-y0)^2 + (z-z0)^2 = R^2 to x^2 + y^2 + z^2 = R^2
        Tuple sphereToRay = transformedRay.origin.subtract(this.origin);
        //a = D^2, b = 2OD, c = O^2 - R^2 -- R = radius, O = ray origin, D = ray direction
        double a = TupleOperation.dot(transformedRay.direction, transformedRay.direction);
        double b = 2 * TupleOperation.dot(transformedRay.direction, sphereToRay);
        double c = TupleOperation.dot(sphereToRay, sphereToRay) - 1; //-1 because radius is always one

        double discriminant = b*b - 4*a*c;
        if(discriminant < 0){
            return new RayIntersection(0, List.of(), List.of());
        }
        else{
            double t1 = (-b - Math.sqrt(discriminant))/(2*a);
            double t2 = (-b + Math.sqrt(discriminant))/(2*a);
            return new RayIntersection(2, Arrays.asList(t1, t2)
                    , Arrays.asList(this, this));
        }
    }

    @Override
    public boolean isSame(Shape input) {
        return id.equals(input.getId());
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public Matrix getTransform() {
        return transform;
    }

    @Override
    public RayTransformer.Type getTransformType() {
        return transformType;
    }

    @Override
    public void setTransform(Matrix t, RayTransformer.Type transformType) {
        this.transform = t;
        this.transformType = transformType;
    }
}
