package org.aicl.raytracerchallenge.primitives.pattern;

import org.aicl.raytracerchallenge.primitives.Color;
import org.aicl.raytracerchallenge.primitives.Point;

public class GradientPattern extends BasePattern{

    public GradientPattern(Color color1, Color color2){
        colorPalette.add(color1);
        colorPalette.add(color2);
    }

    @Override
    public Color patternAt(Point p) {
        Color color1 = colorPalette.get(0);
        Color color2 = colorPalette.get(1);
        Color newColor = color1.add(color2.subtract(color1).multiply(p.x - Math.floor(p.x)));
        return newColor;
    }
}
