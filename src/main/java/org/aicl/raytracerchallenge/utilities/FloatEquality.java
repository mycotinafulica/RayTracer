package org.aicl.raytracerchallenge.utilities;

import org.aicl.raytracerchallenge.geometry.Constant;

public class FloatEquality {
    public static boolean isEqual(float a, float b){
        return Math.abs(a - b) < Constant.epsilon;
    }
}
