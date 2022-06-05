package org.aicl.raytracerchallenge.primitives;

public class TupleOperation {
    public static double dot(Tuple a, Tuple b){
        return a.x * b.x + a.y * b.y + a.z * b.z;
    }

    public static Vector cross(Tuple a, Tuple b){
        return new Vector(
            a.y * b.z - a.z * b.y,
            a.z * b.x - a.x * b.z,
            a.x * b.y - a.y * b.x
        );
    }

    public static Tuple add(Tuple a, Tuple b){
        return a.add(b);
    }

    public static Tuple subtract(Tuple a, Tuple b){
        return a.subtract(b);
    }
}
