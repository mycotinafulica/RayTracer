package org.aicl.raytracerchallenge.primitives;

import java.util.ArrayList;

public class Matrix {
    private int row;
    private int col;

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
}
