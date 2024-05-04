package org.aicl.raytracerchallenge.puttogether.chapter3;

import org.aicl.raytracerchallenge.primitives.Matrix;
import org.aicl.raytracerchallenge.primitives.Tuple;

public class PutTogetherChap3 {
    public PutTogetherChap3(){
        Tuple t = new Tuple(1, 2, 3, 4);
        System.out.println("Tuple 1 : " + t.toString());
        Matrix identityAlter = new Matrix(new double[][]{
                new double[]{1, 0, 0, 0},
                new double[]{0, 2, 0, 0},
                new double[]{0, 0, 1, 0},
                new double[]{0, 0, 0, 1}
        });

        Tuple t2 = identityAlter.multiply(t);
        System.out.println("Tuple 2 : " + t2.toString());
        identityAlter = new Matrix(new double[][]{
                new double[]{1, 0, 0, 2},
                new double[]{0, 1, 0, 0},
                new double[]{0, 0, 1, 0},
                new double[]{0, 0, 0, 1}
        });

        Tuple t3 = identityAlter.multiply(t);
        System.out.println("Tuple 2 : " + t3.toString());
    }
}
