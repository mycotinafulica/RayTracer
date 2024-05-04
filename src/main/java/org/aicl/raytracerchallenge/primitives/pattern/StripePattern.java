package org.aicl.raytracerchallenge.primitives.pattern;

import org.aicl.raytracerchallenge.primitives.Color;
import org.aicl.raytracerchallenge.primitives.Point;
import org.aicl.raytracerchallenge.primitives.shape.Shape;

public class StripePattern extends BasePattern {

    public StripePattern(Color a, Color b){
        colorPalette.add(a);
        colorPalette.add(b);
    }

    @Override
    public Color patternAt(Point p) {
        if(Math.floor(p.x) % 2 == 0){
            return colorPalette.get(0);
        }
        else{
            return colorPalette.get(1);
        }
    }
}
