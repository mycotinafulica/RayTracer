package org.aicl.raytracerchallenge.geometry;

import static org.aicl.raytracerchallenge.utilities.FloatEquality.isEqual;

public class Tuple {
    public double x, y, z, w;

    public Tuple(double x, double y, double z, double w){
        this.x = x; this.y = y;
        this.z = z; this.w = w;
    }

    public void addToSelf(Tuple t){
        this.x += t.x;  this.y += t.y;
        this.z += t.z;  this.w += t.w;
    }

    public Tuple add(Tuple t){
        return new Tuple(x + t.x, y + t.y, z + t.z, w + t.w);
    }

    public void subtractSelf(Tuple t){
        this.x -= t.x; this.y -= t.y;
        this.z -= t.z; this.w -= t.w;
    }

    public Tuple subtract(Tuple t){
        return new Tuple(x - t.x, y - t.y, z - t.z, w - t.w);
    }

    public boolean isIdentical(Tuple t){
        return isEqual(x, t.x) && isEqual(y, t.y) && isEqual(z, t.z) && isEqual(w, t.w);
    }

    public void negatesSelf(){
        x = -x; y = -y; z = -z; w = -w;
    }

    public Tuple negates(){
        return new Tuple(-x, -y, -z, -w);
    }

    public void multiplySelf(double multiplier){
        this.x *= multiplier; this.y *= multiplier;
        this.z *= multiplier; this.w *= multiplier;
    }

    public Tuple multiply(double multiplier){
        return new Tuple(x * multiplier, y * multiplier,
                z *  multiplier, w * multiplier);
    }

    public void divideSelf(double divisor){
        double multiplier = 1.0f/divisor;
        multiplySelf(multiplier);
    }

    public Tuple divide(double divisor){
        double multiplier = 1.0/divisor;
        return multiply(multiplier);
    }

    public double magnitude(){
        return Math.sqrt(x*x + y*y + z*z + w*w);
    }

    public void normalize(){
        divideSelf(magnitude());
    }

    public boolean isAPoint(){
        return isEqual(w, 1);
    }

    public boolean isAVector(){
        return isEqual(w, 0);
    }
}
