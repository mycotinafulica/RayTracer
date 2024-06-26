package org.aicl.raytracerchallenge.transformation;

import org.aicl.raytracerchallenge.primitives.*;

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

    public Matrix shearing(double xy, double xz, double yx, double yz, double zx, double zy){
        return new Matrix(
                new double[][]{
                        new double[]{1, xy, xz, 0},
                        new double[]{yx, 1, yz, 0},
                        new double[]{zx, zy, 1, 0},
                        new double[]{0, 0, 0, 1}
                }
        );
    }

    public static Matrix viewTransform(Point from, Point to, Vector up){
        Tuple forward  = to.subtract(from).normalize();
        Tuple upNormal = up.normalize();
        Tuple left     = TupleOperation.cross(forward, upNormal);
        Tuple trueUp   = TupleOperation.cross(left, forward);
        Matrix orientation = new Matrix(new double[][]{
                new double[]{left.x, left.y, left.z, 0},
                new double[]{trueUp.x, trueUp.y, trueUp.z, 0},
                new double[]{-forward.x, -forward.y, -forward.z, 0},
                new double[]{0, 0, 0, 1}
        });
        TransformMatrixGenerator generator = new TransformMatrixGenerator();
        return orientation.multiply(generator.translate(-from.x, -from.y, -from.z));
    }
}
