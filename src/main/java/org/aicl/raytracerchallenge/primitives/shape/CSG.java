package org.aicl.raytracerchallenge.primitives.shape;

import org.aicl.raytracerchallenge.primitives.Tuple;
import org.aicl.raytracerchallenge.primitives.ray.Intersection;
import org.aicl.raytracerchallenge.primitives.ray.Ray;
import org.aicl.raytracerchallenge.primitives.ray.RayIntersection;

import java.util.List;
import java.util.UUID;

public class CSG extends Shape{

    private String id;

    public Operation operation;
    public Shape left;
    public Shape right;

    public enum Operation{
        UNION,
        INTERSECT,
        DIFFERENCE
    }

    public CSG() {
        super();
        id = UUID.randomUUID().toString();
    }

    public CSG(Operation operation, Shape s1, Shape s2){
        this();
        this.operation = operation;
        this.left = s1;
        this.right = s2;

        left.setParent(this);
        right.setParent(this);
    }

    @Override
    public RayIntersection localIntersect(Ray transformedRay) {
        return null;
    }

    @Override
    public Tuple localNormal(Tuple objectPoint, Intersection hit) {
        return null;
    }

    public RayIntersection filterIntersections(RayIntersection xs){

        boolean inl = false;
        boolean inr = false;

        RayIntersection result = new RayIntersection(0, List.of());

        xs.sort();
        for(int i = 0; i < xs.count; i++){
            boolean lhit = left.isSame(xs.getIntersectedShape(i));
            if(intersectionAllowed(operation, lhit, inl, inr)){
                result.addIntersection(xs.getIntersection(i));
            }

            if(lhit)
                inl = !inl;
            else
                inr = !inr;
        }

        return result;
    }

    public boolean intersectionAllowed(Operation op, boolean lhit, boolean inl, boolean inr){
        if(op == Operation.UNION){
            return (lhit && !inr) || (!lhit && !inl);
        }
        else if(op == Operation.INTERSECT){
            return (lhit && inr) || (!lhit && inl);
        }
        else if(op == Operation.DIFFERENCE){
            return (lhit && !inr) || (!lhit && inl);
        }
        return false;
    }

    @Override
    public boolean isSame(Shape input) {
        return (input instanceof CSG) && id.equals(input.getId());
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
