package org.aicl.raytracerchallenge.primitives;

import static org.aicl.raytracerchallenge.utilities.FloatEquality.isEqual;

public class Tuple {
    public double x, y, z, w;

    public Tuple(double x, double y, double z, double w){
        this.x = x; this.y = y;
        this.z = z; this.w = w;
    }

    public Tuple(Tuple p){
        this(p.x, p.y, p.z, p.w);
    }

    public Tuple addToSelf(Tuple t){
        this.x += t.x;  this.y += t.y;
        this.z += t.z;  this.w += t.w;
        return this;
    }

    public Tuple add(Tuple t){
        return new Tuple(x + t.x, y + t.y, z + t.z, w + t.w);
    }

    public Tuple subtractSelf(Tuple t){
        this.x -= t.x; this.y -= t.y;
        this.z -= t.z; this.w -= t.w;
        return this;
    }

    public Tuple subtract(Tuple t){
        return new Tuple(x - t.x, y - t.y, z - t.z, w - t.w);
    }

    public boolean isIdentical(Tuple t){
        return isEqual(x, t.x) && isEqual(y, t.y) && isEqual(z, t.z) && isEqual(w, t.w);
    }

    public Tuple negatesSelf(){
        x = -x; y = -y; z = -z; w = -w;
        return this;
    }

    public Tuple negates(){
        return new Tuple(-x, -y, -z, -w);
    }

    public Tuple multiplySelf(double multiplier){
        this.x *= multiplier; this.y *= multiplier;
        this.z *= multiplier; this.w *= multiplier;
        return this;
    }

    public Tuple multiply(double multiplier){
        return new Tuple(x * multiplier, y * multiplier,
                z *  multiplier, w * multiplier);
    }

    public Tuple divideSelf(double divisor){
        double multiplier = 1.0f/divisor;
        multiplySelf(multiplier);

        return this;
    }

    public Tuple divide(double divisor){
        double multiplier = 1.0/divisor;
        return multiply(multiplier);
    }

    public double magnitude(){
        return Math.sqrt(x*x + y*y + z*z + w*w);
    }

    public Tuple normalize(){
        divideSelf(magnitude());
        return this;
    }

    public boolean isAPoint(){
        return isEqual(w, 1);
    }

    public boolean isAVector(){
        return isEqual(w, 0);
    }
}
