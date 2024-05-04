package org.aicl.raytracerchallenge.primitives.pattern;

import org.aicl.raytracerchallenge.primitives.Color;
import org.aicl.raytracerchallenge.primitives.Point;

public class TestPattern extends BasePattern {
    @Override
    public Color patternAt(Point p) {
        return new Color(p.x, p.y, p.z);
    }
}
