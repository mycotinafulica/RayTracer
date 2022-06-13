package org.aicl.raytracerchallenge.transformation;

import org.aicl.raytracerchallenge.primitives.Matrix;

public class TransformMatrixGenerator {
    public Matrix translate(double x, double y, double z){
        return new Matrix(
                new double[][]{
                        new double[]{1, 0, 0, x},
                        new double[]{0, 1, 0, y},
                        new double[]{0, 0, 1, z},
                        new double[]{0, 0, 0, 1}
                }
        );
    }

    public Matrix scale(double x, double y, double z){
        return new Matrix(
                new double[][]{
                        new double[]{x, 0, 0, 0},
                        new double[]{0, y, 0, 0},
                        new double[]{0, 0, z, 0},
                        new double[]{0, 0, 0, 1}
                }
        );
    }

    public Matrix rotateX(double rad){
        return new Matrix(
                new double[][]{
                        new double[]{1, 0, 0, 0},
                        new double[]{0, Math.cos(rad), -Math.sin(rad), 0},
                        new double[]{0, Math.sin(rad), Math.cos(rad), 0},
                        new double[]{0, 0, 0, 1}
                }
        );
    }

    public Matrix rotateY(double rad){
        return new Matrix(
                new double[][]{
                        new double[]{Math.cos(rad), 0, Math.sin(rad) , 0},
                        new double[]{0, 1, 0, 0},
                        new double[]{-Math.sin(rad), 0, Math.cos(rad), 0},
                        new double[]{0, 0, 0, 1}
                }
        );
    }

    public Matrix rotateZ(double rad){
        return new Matrix(
                new double[][]{
                        new double[]{Math.cos(rad), -Math.sin(rad), 0, 0},
                        new double[]{Math.sin(rad), Math.cos(rad), 0, 0},
                        new double[]{0, 0, 1, 0},
                        new double[]{0, 0, 0, 1}
                }
        );
    }
}
