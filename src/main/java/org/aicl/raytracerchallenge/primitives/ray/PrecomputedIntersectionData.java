package org.aicl.raytracerchallenge.primitives.ray;

import org.aicl.raytracerchallenge.primitives.Constant;
import org.aicl.raytracerchallenge.primitives.Point;
import org.aicl.raytracerchallenge.primitives.TupleOperation;
import org.aicl.raytracerchallenge.primitives.Vector;
import org.aicl.raytracerchallenge.primitives.shape.Shape;

import java.util.ArrayList;

public class PrecomputedIntersectionData {
    public double time = 0;
    public Shape intersectedObj;
    //intersection point
    public Point point;
    public Vector eyev;
    public Vector normal;

    public Vector reflectv;
    public boolean inside = false;

    double n1 = 1.0;
    double n2 = 1.0;

    public Point overPoint;

    public PrecomputedIntersectionData compute(Intersection hit, Ray ray, RayIntersection intersections){
        this.time = hit.time;
        this.intersectedObj = hit.intersectedShape;

        this.point  = new Point(ray.position(hit.time));
        this.eyev   = new Vector(ray.direction.negates());
        this.normal = new Vector(intersectedObj.normalAt(point));

        //if inside object
        if(TupleOperation.dot(normal, eyev) < 0){
            inside = true;
            normal = new Vector(normal.negates());
        }

        reflectv = TupleOperation.reflect(ray.direction, normal);
        overPoint = new Point(point.add(normal.multiply(Constant.epsilon)));

        ArrayList<Shape> objectsContainer = new ArrayList<>();

        for(int i = 0; i < intersections.count; i++){
            if(intersections.getIntersection(i).isEqual(hit)){
                if(objectsContainer.isEmpty()){
                    n1 = 1.0;
                }
                else{
                    n1 = objectsContainer.get(objectsContainer.size() - 1).getMaterial().refractiveIndex;
                }
            }

            if(objectsContainer.contains(intersections.getIntersectedShape(i))){
                objectsContainer.remove(intersections.getIntersectedShape(i));
            }
            else{
                objectsContainer.add(intersections.getIntersectedShape(i));
            }

            if(intersections.getIntersection(i).isEqual(hit)){
                if(objectsContainer.isEmpty()){
                    n2 = 1.0;
                }
                else{
                    n2 = objectsContainer.get(objectsContainer.size() - 1).getMaterial().refractiveIndex;
                }
                return this;
            }
        }

        return this;
    }
}
