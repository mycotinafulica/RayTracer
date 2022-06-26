package org.aicl.raytracerchallenge.primitives.ray;

import org.aicl.raytracerchallenge.primitives.Constant;
import org.aicl.raytracerchallenge.primitives.shape.Shape;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class RayIntersection {
    public int count;
    /*public ArrayList<Double> time = new ArrayList<>();
    public ArrayList<Shape> intersectedShape = new ArrayList<>();*/

    private ArrayList<Intersection> intersections = new ArrayList<>();

    private int smallestNonNegIdx  = -1;
    private double smallestNonNegTime = 900000000;

    public RayIntersection(int count, List<Intersection> inputIntersection){
        this.count = count;
        for(int i = 0 ; i < inputIntersection.size() ; i++){
            intersections.add(inputIntersection.get(i));
            checkIfSmallest(intersections.get(i).time, intersections.size());
        }
    }

    public RayIntersection(double time, Shape intersectedShape){
        this.count = 1;
        intersections.add(new Intersection(time, intersectedShape));
        checkIfSmallest(time, intersections.size());
    }

    public RayIntersection concat(RayIntersection rayIntersection){
        this.count += rayIntersection.count;
        for(int i = 0 ; i < rayIntersection.count; i++){
            Intersection intersectionAtIdx = rayIntersection.intersections.get(i);
            intersections.add(new Intersection(intersectionAtIdx.time, intersectionAtIdx.intersectedShape));

            checkIfSmallest(intersectionAtIdx.time, this.intersections.size());
        }

        return this;
    }

    public double getTime(int index){
        return intersections.get(index).time;
    }

    public Shape getIntersectedShape(int index){
        return intersections.get(index).intersectedShape;
    }

    public Intersection getIntersection(int idx){
        return intersections.get(idx);
    }

    public Intersection hit(){
        if(smallestNonNegIdx == -1)
            return null;
        else
            return intersections.get(smallestNonNegIdx);
    }

    public RayIntersection copy(){
        RayIntersection result = new RayIntersection(this.count, intersections);
        result.setSmallestNonNegIdx(smallestNonNegIdx);
        result.setSmallestNonNegTime(smallestNonNegTime);
        return  result;
    }

    public boolean isEqual(RayIntersection input){
        if(this.count != input.count)
            return false;
        for(int i = 0 ; i < count ; i++){
            if(!intersections.get(i).isEqual(input.intersections.get(i))){
                return false;
            }
        }
        return true;
    }

    private void checkIfSmallest(double time, int currentSize){
        if(time < smallestNonNegTime && time > Constant.epsilon){
            smallestNonNegTime = time;
            smallestNonNegIdx  = currentSize - 1;
        }
    }

    public void sort(){
        Collections.sort(intersections);
    }

    public void setSmallestNonNegIdx(int smallestNonNegIdx) {
        this.smallestNonNegIdx = smallestNonNegIdx;
    }

    public void setSmallestNonNegTime(double smallestNonNegTime) {
        this.smallestNonNegTime = smallestNonNegTime;
    }
}
