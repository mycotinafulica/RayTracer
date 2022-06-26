package org.aicl.raytracerchallenge.primitives.shape;

import org.aicl.raytracerchallenge.primitives.*;
import org.aicl.raytracerchallenge.primitives.ray.Intersection;
import org.aicl.raytracerchallenge.primitives.ray.Ray;
import org.aicl.raytracerchallenge.primitives.ray.RayIntersection;
import org.aicl.raytracerchallenge.transformation.RayTransformer;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

public class Sphere implements Shape {
    public double radius = 1.0;
    public Point origin = new Point(0, 0, 0);
    private final String id;
    private Matrix transform = Matrix.identity();
    private Material material;

    public Sphere() {
        id       = UUID.randomUUID().toString();
        material = new Material();
    }

    /*public Sphere(double radius, Point origin){
        this();
        this.radius = radius;
        this.origin = origin;
    }*/

    @Override
    public RayIntersection intersect(Ray r) {
        Ray transformedRay = RayTransformer.transform(r, transform.inverse());
        //normalize so that we play where the center of the coordinate is (0, 0)
        //simplifying (x-x0)^2 + (y-y0)^2 + (z-z0)^2 = R^2 to x^2 + y^2 + z^2 = R^2
        Tuple sphereToRay = transformedRay.origin.subtract(this.origin);
        //a = D^2, b = 2OD, c = O^2 - R^2 -- R = radius, O = ray origin, D = ray direction
        double a = TupleOperation.dot(transformedRay.direction, transformedRay.direction);
        double b = 2 * TupleOperation.dot(transformedRay.direction, sphereToRay);
        double c = TupleOperation.dot(sphereToRay, sphereToRay) - 1; //-1 because radius is always one

        double discriminant = b*b - 4*a*c;
        if(discriminant < 0){
            return new RayIntersection(0, List.of());
        }
        else{
            double t1 = (-b - Math.sqrt(discriminant))/(2*a);
            double t2 = (-b + Math.sqrt(discriminant))/(2*a);
            return new RayIntersection(2, Arrays.asList(new Intersection(t1, this)
                    , new Intersection(t2, this)));
        }
    }

    @Override
    public boolean isSame(Shape input) {
        return id.equals(input.getId());
    }

    @Override
    public boolean isSameCharacteristics(Shape shape) {
        if(shape instanceof Sphere){
            if(transform.isEqual(shape.getTransform())){
                if(material.isIdentical(shape.getMaterial()))
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

    @Override
    public Matrix getTransform() {
        return transform;
    }

    @Override
    public void setTransform(Matrix m) {
        transform = m;
    }

    @Override
    public Vector normalAt(Point p) {
        Tuple objectPoint  = transform.inverse().multiply(p);
        Tuple objectNormal = objectPoint.subtract(origin);
        Tuple worldNormal  = transform.inverse().transpose().multiply(objectNormal);
        worldNormal.w = 0;
        return new Vector(worldNormal.normalize());
    }

    @Override
    public void setMaterial(Material m) {
        material = m;
    }

    @Override
    public Material getMaterial() {
        return material;
    }
}
