package org.aicl.raytracerchallenge.primitives;

import org.aicl.raytracerchallenge.primitives.shape.Shape;
import org.aicl.raytracerchallenge.utilities.FloatEquality;
import org.jetbrains.annotations.NotNull;

public class Intersection implements Comparable<Intersection>{
    public double time;
    public Shape intersectedShape;

    public Intersection(Double time, Shape shape) {
        this.time = time;
        this.intersectedShape = shape;
    }

    public boolean isEqual(Intersection intersection){
        return FloatEquality.isEqual(time, intersection.time) && intersectedShape.isSame(intersection.intersectedShape);
    }

    @Override
    public int compareTo(@NotNull Intersection o) {
        return Double.compare(time, o.time);
    }
}
