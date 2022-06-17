package org.aicl.raytracerchallenge.primitives;

import org.aicl.raytracerchallenge.primitives.shape.Shape;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class RayIntersection {
    public int count;
    public ArrayList<Double> time = new ArrayList<>();
    public ArrayList<Shape> intersectedShape = new ArrayList<>();

    public RayIntersection(int count, List<Double> time, List<Shape> intersectedShape){
        this.count = count;
        this.time.addAll(time);
        this.intersectedShape.addAll(intersectedShape);
    }

    public RayIntersection(double time, Shape intersectedShape){
        this.count = 1;
        this.time.add(time);
        this.intersectedShape.add(intersectedShape);
    }
}
