package org.aicl.raytracerchallenge.primitives;

import org.aicl.raytracerchallenge.utilities.FloatEquality;

import java.util.ArrayList;

public class Matrix {
    public int row;
    public int col;

    private ArrayList<ArrayList<Double>> matrix = new ArrayList<>();

    public Matrix(int row, int col){
        this.row = row;
        this.col = col;

        for(int r = 0 ; r < row; r++){
            ArrayList<Double> rowElements = new ArrayList<>();
            for(int c = 0 ; c < col ; c++){
                rowElements.add(0.0);
            }
            matrix.add(rowElements);
        }
    }

    public Matrix(double[][] initMtx){
        this.row = initMtx.length;
        this.col = initMtx[0].length;

        for(int r = 0 ; r < row; r++){
            ArrayList<Double> rowElements = new ArrayList<>();
            for(int c = 0 ; c < col ; c++){
                rowElements.add(initMtx[r][c]);
            }
            matrix.add(rowElements);
        }
    }

    public double elementAt(int row, int col){
        double result = 0;
        try {
            result = matrix.get(row).get(col);
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
//            ArrayList<Double> rowElements = new ArrayList<>();
            for(int c = 0; c<newMatrixCol ; c++){
                double elementVal = 0;
                for(int i = 0; i < col ; i++){
                    elementVal += this.elementAt(r, i) * m.elementAt(i, c);
                    result[r][c] = elementVal;
                }
            }
        }
        return new Matrix(result);
    }

    public void print(){
        System.out.println(matrix);
    }
}
