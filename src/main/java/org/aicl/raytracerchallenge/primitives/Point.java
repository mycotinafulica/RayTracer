package org.aicl.raytracerchallenge.primitives;

import org.aicl.raytracerchallenge.utilities.FloatEquality;

public class Point extends Tuple {
    public Point(double x, double y, double z){
        super(x, y, z, 1);
    }

    public Point(Tuple p){
        super(p);
        if(!FloatEquality.isEqual(p.w, 1)){
            throw new IllegalArgumentException("Unexpected w value for Point");
        }
    }
}
