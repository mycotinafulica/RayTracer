package org.aicl.raytracerchallenge.geometry;

import static org.aicl.raytracerchallenge.utilities.FloatEquality.isEqual;

public class Tuple {
    public float x, y, z, w;

    public Tuple(float x, float y, float z, float w){
        this.x = x;
        this.y = y;
        this.z = z;
        this.w = w;
    }

    public boolean isIdentical(Tuple t){
        return isEqual(x, t.x) && isEqual(y, t.y) && isEqual(z, t.z) && isEqual(w, t.w);
    }

    public boolean isAPoint(){
        return isEqual(w, 1);
    }

    public boolean isAVector(){
        return isEqual(w, 0);
    }
}
