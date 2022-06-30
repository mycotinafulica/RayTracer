package org.aicl.raytracerchallenge.primitives.ray;

import org.aicl.raytracerchallenge.primitives.Constant;
import org.aicl.raytracerchallenge.primitives.Point;
import org.aicl.raytracerchallenge.primitives.TupleOperation;
import org.aicl.raytracerchallenge.primitives.Vector;
import org.aicl.raytracerchallenge.primitives.shape.Shape;

public class PrecomputedIntersectionData {
    public double time = 0;
    public Shape intersectedObj;
    //intersection point
    public Point point;
    public Vector eyev;
    public Vector normal;
    public boolean inside = false;

    public Point overPoint;

    public PrecomputedIntersectionData compute(Intersection i, Ray ray){
        this.time = i.time;
        this.intersectedObj = i.intersectedShape;

        this.point  = new Point(ray.position(i.time));
        this.eyev   = new Vector(ray.direction.negates());
        this.normal = new Vector(intersectedObj.normalAt(point));

        //if inside object
        if(TupleOperation.dot(normal, eyev) < 0){
            inside = true;
            normal = new Vector(normal.negates());
        }

        overPoint = new Point(point.add(normal.multiply(Constant.epsilon)));

        return this;
    }
}
