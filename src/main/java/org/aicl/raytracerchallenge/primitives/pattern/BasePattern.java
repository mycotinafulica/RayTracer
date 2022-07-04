package org.aicl.raytracerchallenge.primitives.pattern;

import org.aicl.raytracerchallenge.primitives.Color;
import org.aicl.raytracerchallenge.primitives.Matrix;
import org.aicl.raytracerchallenge.primitives.Point;

import java.util.ArrayList;

public abstract class BasePattern {
    protected Matrix transform = Matrix.identity();
    protected ArrayList<Color> colorPalette = new ArrayList<>();

    public Matrix getTransform(){
        return transform;
    }

    public void setTransform(Matrix transform) {
        this.transform = transform;
    }

    public Color getColorChoice(int idx){
        return colorPalette.get(idx);
    }

    public abstract Color patternAt(Point p);
}
