package org.aicl.raytracerchallenge.primitives.pattern;

import org.aicl.raytracerchallenge.primitives.Color;
import org.aicl.raytracerchallenge.primitives.Point;

public class RingPattern extends BasePattern {

    public RingPattern(Color c1, Color c2){
        colorPalette.add(c1);
        colorPalette.add(c2);
    }

    @Override
    public Color patternAt(Point p) {
        if(Math.floor(Math.sqrt(p.x * p.x + p.z * p.z)) % 2 == 0)
            return colorPalette.get(0);
        else
            return colorPalette.get(1);
    }
}
