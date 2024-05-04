package org.aicl.raytracerchallenge.primitives.pattern;

import org.aicl.raytracerchallenge.primitives.Color;
import org.aicl.raytracerchallenge.primitives.Point;

public class CheckerPattern extends BasePattern {

    public CheckerPattern(Color c1, Color c2){
        colorPalette.add(c1);
        colorPalette.add(c2);
    }

    @Override
    public Color patternAt(Point p) {
        Color c1 = colorPalette.get(0);
        Color c2 = colorPalette.get(1);
        double determinant = Math.floor(p.x) + Math.floor(p.y) + Math.floor(p.z);
        if(determinant % 2 == 0)
            return c1;
        else
            return c2;
    }
}
