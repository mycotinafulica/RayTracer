package org.aicl.raytracerchallenge.primitives.pattern;

import org.aicl.raytracerchallenge.primitives.Color;
import static org.junit.jupiter.api.Assertions.*;

import org.aicl.raytracerchallenge.primitives.Point;
import org.junit.jupiter.api.Test;

public class GradientPatternTest {
    private static final Color white = new Color(1, 1, 1);
    private static final Color black = new Color(0, 0, 0);

    @Test
    public void testGradientPattern(){
        BasePattern pattern = new GradientPattern(white, black);
        assertTrue(pattern.patternAt(new Point(0.25, 0, 0)).isIdentical(new Color(0.75, 0.75, 0.75)));
        assertTrue(pattern.patternAt(new Point(0.5, 0, 0)).isIdentical(new Color(0.5, 0.5, 0.5)));
        assertTrue(pattern.patternAt(new Point(0.75, 0, 0)).isIdentical(new Color(0.25, 0.25, 0.25)));
    }
}
