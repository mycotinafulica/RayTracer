package org.aicl.raytracerchallenge.primitives.pattern;

import org.aicl.raytracerchallenge.primitives.Color;
import org.aicl.raytracerchallenge.primitives.Point;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class CheckerPatternTest {
    private static final Color white = new Color(1, 1, 1);
    private static final Color black = new Color(0, 0, 0);

    @Test
    public void checkerPatternXAxisTest(){
        BasePattern pattern = new CheckerPattern(white, black);
        assertTrue(pattern.patternAt(new Point(0, 0, 0)).isIdentical(white));
        assertTrue(pattern.patternAt(new Point(0.99, 0, 0)).isIdentical(white));
        assertTrue(pattern.patternAt(new Point(1.01, 0, 0)).isIdentical(black));
    }

    @Test
    public void checkerPatternYAxisTest(){
        BasePattern pattern = new CheckerPattern(white, black);
        assertTrue(pattern.patternAt(new Point(0, 0, 0)).isIdentical(white));
        assertTrue(pattern.patternAt(new Point(0, 0.99, 0)).isIdentical(white));
        assertTrue(pattern.patternAt(new Point(0, 1.01, 0)).isIdentical(black));
    }

    @Test
    public void checkerPatternZAxisTest(){
        BasePattern pattern = new CheckerPattern(white, black);
        assertTrue(pattern.patternAt(new Point(0, 0, 0)).isIdentical(white));
        assertTrue(pattern.patternAt(new Point(0, 0, 0.99)).isIdentical(white));
        assertTrue(pattern.patternAt(new Point(0, 0, 1.01)).isIdentical(black));
    }
}
