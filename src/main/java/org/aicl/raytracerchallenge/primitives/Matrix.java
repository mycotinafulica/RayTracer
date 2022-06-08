package org.aicl.raytracerchallenge.primitives;

import org.aicl.raytracerchallenge.utilities.FloatEquality;

import java.util.ArrayList;
import java.util.Arrays;

public class Matrix {
    public int row;
    public int col;

//    private ArrayList<ArrayList<Double>> matrix = new ArrayList<>();
    private double[][] matrix;

    public Matrix(int row, int col){
        this.row = row;
        this.col = col;

        matrix = new double[row][col];
        for(int r = 0 ; r < row; r++){
            for(int c = 0 ; c < col ; c++){
                matrix[r][c] = 0.0;
            }
        }
    }

    public Matrix(double[][] initMtx){
        this.row = initMtx.length;
        this.col = initMtx[0].length;

        matrix = initMtx;
    }

    public double elementAt(int row, int col){
        double result = 0;
        try {
            result = matrix[row][col];
            return result;
        } catch (IndexOutOfBoundsException e){
            throw new IllegalArgumentException("Trying to access element at [" + row + "," + col + "]" + "" +
                    "while the matrix is " + "[" + this.row + "," + this.col + "]");
        }
    }

    public boolean isEqual(Matrix m){
        if(row != m.row || col != m.col)
            return false;

        for(int r = 0 ; r<row ; r++){
            for(int c = 0 ; c < col ; c++){
                if(!FloatEquality.isEqual(elementAt(r, c), m.elementAt(r, c)))
                    return false;
            }
        }

        return true;
    }

    public Matrix multiply(Matrix m){
        if(this.col != m.row) {
            throw new IllegalArgumentException("Cannot multiply [" + row + "," + col + "] " +
                    "with [" + m.row + "," + m.col + "] matrix");
        }

        int newMatrixRow = row;
        int newMatrixCol = m.col;
        double[][] result = new double[newMatrixRow][newMatrixCol];
        for(int r = 0 ;  r < newMatrixRow ; r++){
            for(int c = 0; c < newMatrixCol ; c++){
                double elementVal = 0;
                for(int i = 0; i < col ; i++){
                    elementVal += this.elementAt(r, i) * m.elementAt(i, c);
                    result[r][c] = elementVal;
                }
            }
        }
        return new Matrix(result);
    }

    public Tuple multiply(Tuple t){
        Matrix tupleMat = new Matrix(new double[][]{
           new double[]{t.x}, new double[]{t.y},
                new double[]{t.z}, new double[]{t.w}
        });
        Matrix result = multiply(tupleMat);
        return new Tuple(result.elementAt(0, 0),
                result.elementAt(1, 0),
                result.elementAt(2, 0),
                result.elementAt(3, 0));
    }

    public Matrix transpose(){
        double[][] result = new double[col][row];
        for(int r = 0 ; r<row ; r++){
            for(int c = 0; c<col ; c++){
                result[c][r] = elementAt(r, c);
            }
        }
        return new Matrix(result);
    }

    public double determinant(){
        if(row != col)
            throw new IllegalArgumentException("Only square matrices supported!");
        if(row != 2){
            double determinant = 0;
            for(int i = 0 ; i < col ; i++){
                determinant += elementAt(0, i) * cofactor(0, i);
            }
            return determinant;
        }else{
            return elementAt(0, 0)*elementAt(1,1) - elementAt(0, 1)*elementAt(1, 0);
        }
    }

    public Matrix subMatrix(int discardRow, int discardCol){
        double[][] result = new double[row-1][col-1];
        int rowOffset = 0;
        for(int r = 0 ; r<row ; r++){
            int colOffset = 0;
            if(r == discardRow){
                rowOffset = 1;
                continue;
            }
            for(int c = 0; c<col ; c++){
                if(c == discardCol){
                    colOffset = 1;
                    continue;
                }
                result[r - rowOffset][c - colOffset] = elementAt(r, c);
            }
        }
        return new Matrix(result);
    }

    public double minor(int discardRow, int discardCol){
        Matrix subMat = subMatrix(discardRow, discardCol);
        return subMat.determinant();
    }

    public double cofactor(int discardRow, int discardCol){
        double minor = minor(discardRow, discardCol);
        if((discardRow + discardCol) % 2 == 0){
            return minor;
        }
        else{
            return -minor;
        }
    }

    public boolean isInvertible(){
        if(determinant() == 0)
            return false;

        return true;
    }

    public Matrix inverse(){
        double[][] result  = new double[row][col];
        double determinant = determinant();
        for(int r = 0; r<row ; r++){
            for(int c = 0; c<col ; c++){
                double cofactor = cofactor(r, c);
                result[c][r] = cofactor/determinant;
            }
        }

        return new Matrix(result);
    }

    public void print(){
        System.out.println(Arrays.deepToString(matrix));
    }
}
