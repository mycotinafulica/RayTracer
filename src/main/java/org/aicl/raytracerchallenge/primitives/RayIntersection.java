package org.aicl.raytracerchallenge.primitives;

import org.aicl.raytracerchallenge.primitives.shape.Shape;
import org.aicl.raytracerchallenge.utilities.FloatEquality;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class RayIntersection {
    public int count;
    public ArrayList<Double> time = new ArrayList<>();
    public ArrayList<Shape> intersectedShape = new ArrayList<>();

    private int smallestNonNegIdx  = -1;
    private double smallestNonNegTime = 900000000;

    public RayIntersection(int count, List<Double> time, List<Shape> intersectedShape){
        this.count = count;
        for(int i = 0 ; i < time.size() ; i++){
            this.time.add(time.get(i));
            this.intersectedShape.add(intersectedShape.get(i));
            checkIfSmallest(time.get(i), this.time.size());
        }
    }

    public RayIntersection(double time, Shape intersectedShape){
        this.count = 1;
        this.time.add(time);
        this.intersectedShape.add(intersectedShape);
        checkIfSmallest(time, this.time.size());
    }

    public RayIntersection concat(RayIntersection rayIntersection){
        this.count += rayIntersection.count;
        for(int i = 0 ; i < rayIntersection.count; i++){
            this.time.add(rayIntersection.time.get(i));
            this.intersectedShape.add(rayIntersection.intersectedShape.get(i));

            checkIfSmallest(rayIntersection.time.get(i), this.time.size());
        }

        return this;
    }

    public RayIntersection hit(){
        if(smallestNonNegIdx == -1)
            return null;
        else
            return new RayIntersection(time.get(smallestNonNegIdx), intersectedShape.get(smallestNonNegIdx));
    }

    public RayIntersection copy(){
        RayIntersection result = new RayIntersection(this.count, this.time, this.intersectedShape);
        result.setSmallestNonNegIdx(smallestNonNegIdx);
        result.setSmallestNonNegTime(smallestNonNegTime);
        return  result;
    }

    public boolean isEqual(RayIntersection input){
        if(this.count != input.count)
            return false;
        for(int i = 0 ; i < count ; i++){
            if(!FloatEquality.isEqual(this.time.get(i), input.time.get(i))
                || !this.intersectedShape.get(i).isSame(input.intersectedShape.get(i))){
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

    public void setSmallestNonNegIdx(int smallestNonNegIdx) {
        this.smallestNonNegIdx = smallestNonNegIdx;
    }

    public void setSmallestNonNegTime(double smallestNonNegTime) {
        this.smallestNonNegTime = smallestNonNegTime;
    }
}
