package org.aicl.raytracerchallenge.primitives.pattern;

import org.aicl.raytracerchallenge.primitives.Color;
import org.aicl.raytracerchallenge.primitives.Matrix;
import org.aicl.raytracerchallenge.primitives.Point;
import org.aicl.raytracerchallenge.primitives.Tuple;
import org.aicl.raytracerchallenge.primitives.shape.Shape;

import java.util.ArrayList;

public abstract class BasePattern {
    protected Matrix transform = Matrix.identity();

    protected Matrix inverseTransform = Matrix.identity();
    protected ArrayList<Color> colorPalette = new ArrayList<>();

    public Matrix getTransform(){
        return transform;
    }

    public void setTransform(Matrix transform) {
        this.transform = transform;
        inverseTransform = transform.inverse();
    }

    public Color getColorChoice(int idx){
        return colorPalette.get(idx);
    }

    public abstract Color patternAt(Point p);

    public Color patternAtObject(Shape shape, Point p){
        Tuple objectPoint  = shape.worldPointToObjectPoint(p);
        Tuple patternPoint = inverseTransform.multiply(objectPoint);

        return patternAt(new Point(patternPoint));
    }
}
