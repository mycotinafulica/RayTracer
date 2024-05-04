package org.aicl.raytracerchallenge.primitives.shape;

import org.aicl.raytracerchallenge.primitives.Tuple;
import org.aicl.raytracerchallenge.primitives.ray.Intersection;
import org.aicl.raytracerchallenge.primitives.ray.Ray;
import org.aicl.raytracerchallenge.primitives.ray.RayIntersection;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Group extends Shape{
    private String id;

    private ArrayList<Shape> children = new ArrayList<>();

    public Group(){
        super();
        id = UUID.randomUUID().toString();
    }

    @Override
    public RayIntersection localIntersect(Ray transformedRay) {
        if(children.size() == 0){
            return new RayIntersection(0, List.of());
        }

        RayIntersection xs = new RayIntersection(0, List.of());
        for(int i = 0; i < children.size(); i++){
            RayIntersection childrenIntersection = children.get(i).intersect(transformedRay);
            xs.concat(childrenIntersection);
        }

        xs.sort();

        return xs;
    }

    @Override
    public Tuple localNormal(Tuple objectPoint, Intersection hit) {
        throw new IllegalStateException("Shouldn't be called");
    }

    @Override
    public boolean isSame(Shape input) {
        return (input instanceof Group) && id.equals(input.getId());
    }

    @Override
    public boolean isSameCharacteristics(Shape shape) {
        return isSame(shape);
    }

    @Override
    public String getId() {
        return id;
    }

    public ArrayList<Shape> getChildren(){
        return children;
    }

    public void addChild(Shape child){
        child.setParent(this);
        children.add(child);
    }

    public boolean hashChild(Shape child){ //TODO how about group in group? not handled yet!!
        for(int i = 0; i < children.size() ; i++){
            if(child.isSame(children.get(i))){
                return true;
            }
        }
        return false;
    }
}
