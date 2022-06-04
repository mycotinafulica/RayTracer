package org.aicl.raytracerchallenge.geometry;

import org.aicl.raytracerchallenge.utilities.FloatEquality;

public class Vector extends Tuple{
    public Vector(double x, double y, double z){
        super(x, y, z, 0);
    }

    public Vector(Tuple p){
        super(p);
        if(!FloatEquality.isEqual(p.w, 0)){
            throw new IllegalArgumentException("Unexpected w value for Vector");
        }
    }
}
