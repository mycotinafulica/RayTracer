package org.aicl.raytracerchallenge.primitives.shape;

import org.aicl.raytracerchallenge.primitives.*;
import org.aicl.raytracerchallenge.primitives.ray.Intersection;
import org.aicl.raytracerchallenge.primitives.ray.Ray;
import org.aicl.raytracerchallenge.primitives.ray.RayIntersection;
import org.aicl.raytracerchallenge.transformation.RayTransformer;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

public class Sphere extends Shape {
    public double radius = 1.0;
    public Point origin = new Point(0, 0, 0);
    private final String id;

    public Sphere() {
        super();
        id = UUID.randomUUID().toString();
    }

    @Override
    public RayIntersection localIntersect(Ray transformedRay) {
        Tuple sphereToRay = transformedRay.origin.subtract(this.origin);
        //a = D^2, b = 2OD, c = O^2 - R^2 -- R = radius, O = ray origin, D = ray direction
        double a = TupleOperation.dot(transformedRay.direction, transformedRay.direction);
        double b = 2 * TupleOperation.dot(transformedRay.direction, sphereToRay);
        double c = TupleOperation.dot(sphereToRay, sphereToRay) - 1; //-1 because radius is always one

        double discriminant = b * b - 4 * a * c;
        if (discriminant < 0) {
            return new RayIntersection(0, List.of());
        } else {
            double t1 = (-b - Math.sqrt(discriminant)) / (2 * a);
            double t2 = (-b + Math.sqrt(discriminant)) / (2 * a);
            return new RayIntersection(2, Arrays.asList(new Intersection(t1, this)
                    , new Intersection(t2, this)));
        }
    }

    @Override
    public Tuple localNormal(Tuple objectPoint, Intersection hit) {
        return objectPoint.subtract(origin);
    }

    @Override
    public boolean isSame(Shape input) {
        if (!(input instanceof Sphere))
            return false;

        return id.equals(input.getId());
    }

    @Override
    public boolean isSameCharacteristics(Shape shape) {
        if (shape instanceof Sphere) {
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

    public static Sphere createGlassSphere(){
        Sphere sphere = new Sphere();
        sphere.getMaterial().transparency = 1.0;
        sphere.getMaterial().refractiveIndex = 1.5;
        return sphere;
    }
}

