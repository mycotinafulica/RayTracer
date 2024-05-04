package org.aicl.raytracerchallenge.utilities;

import org.aicl.raytracerchallenge.primitives.Constant;

public class FloatEquality {
    public static boolean isEqual(double a, double b){
        return Math.abs(a - b) < Constant.epsilon;
    }
}
