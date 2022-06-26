package org.aicl.raytracerchallenge.primitives.ray;

import org.aicl.raytracerchallenge.primitives.Point;
import org.aicl.raytracerchallenge.primitives.TupleOperation;
import org.aicl.raytracerchallenge.primitives.Vector;
import org.aicl.raytracerchallenge.primitives.shape.Shape;

public class PrecomputedIntersectionData {
    double time = 0;
    Shape intersectedObj;
    //intersection point
    Point point;
    Vector eyev;
    Vector normal;
    boolean inside = false;

    public PrecomputedIntersectionData compute(Intersection i, Ray ray){
        this.time = i.time;
        this.intersectedObj = i.intersectedShape;

        this.point  = new Point(ray.position(i.time));
        this.eyev   = new Vector(ray.direction.negates());
        this.normal = new Vector(intersectedObj.normalAt(point));
        if(TupleOperation.dot(normal, eyev) < 0){
            inside = true;
            normal = new Vector(normal.negates());
        }

        return this;
    }
}
