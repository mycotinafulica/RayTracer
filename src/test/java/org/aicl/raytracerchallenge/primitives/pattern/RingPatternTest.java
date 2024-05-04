package org.aicl.raytracerchallenge.primitives.pattern;

import org.aicl.raytracerchallenge.primitives.Color;
import static org.junit.jupiter.api.Assertions.*;

import org.aicl.raytracerchallenge.primitives.Point;
import org.junit.jupiter.api.Test;

public class RingPatternTest {
    private static final Color white = new Color(1, 1, 1);
    private static final Color black = new Color(0, 0, 0);

    @Test
    public void testRingPattern(){
        BasePattern pattern = new RingPattern(white, black);
        assertTrue(pattern.patternAt(new Point(0, 0, 0)).isIdentical(white));
        assertTrue(pattern.patternAt(new Point(1, 0, 0)).isIdentical(black));
        assertTrue(pattern.patternAt(new Point(0, 0, 1)).isIdentical(black));
        assertTrue(pattern.patternAt(new Point(0.708, 0, 0.708)).isIdentical(black));
    }
}
