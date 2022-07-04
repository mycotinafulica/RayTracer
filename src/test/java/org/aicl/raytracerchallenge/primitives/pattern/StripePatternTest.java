package org.aicl.raytracerchallenge.primitives.pattern;

import org.aicl.raytracerchallenge.primitives.Color;
import static org.junit.jupiter.api.Assertions.*;

import org.aicl.raytracerchallenge.primitives.Point;
import org.junit.jupiter.api.Test;

public class StripePatternTest {
    private static final Color black = new Color(0, 0, 0);
    private static final Color white = new Color(1, 1, 1);

    @Test
    public void stripeCreationTest(){
        BasePattern stripe = new StripePattern(white, black);
        assertTrue(stripe.colorPalette.get(0).isIdentical(white));
        assertTrue(stripe.colorPalette.get(1).isIdentical(black));
    }

    @Test
    public void stripePatternAtPointCase1(){
        BasePattern p = new StripePattern(white, black);

        assertTrue(p.patternAt(new Point(0, 0, 0)).isIdentical(white));
        assertTrue(p.patternAt(new Point(0, 1, 0)).isIdentical(white));
        assertTrue(p.patternAt(new Point(0, 2, 0)).isIdentical(white));
    }

    @Test
    public void stripePatternAtPointCase2(){
        BasePattern p = new StripePattern(white, black);

        assertTrue(p.patternAt(new Point(0, 0, 0)).isIdentical(white));
        assertTrue(p.patternAt(new Point(0, 0, 1)).isIdentical(white));
        assertTrue(p.patternAt(new Point(0, 0, 2)).isIdentical(white));
    }

    @Test
    public void stripePatternAtPointCase3(){
        BasePattern p = new StripePattern(white, black);

        assertTrue(p.patternAt(new Point(0, 0, 0)).isIdentical(white));
        assertTrue(p.patternAt(new Point(0.9, 0, 0)).isIdentical(white));
        assertTrue(p.patternAt(new Point(1, 0, 0)).isIdentical(black));
        assertTrue(p.patternAt(new Point(-0.1, 0, 0)).isIdentical(black));
        assertTrue(p.patternAt(new Point(-1, 0, 0)).isIdentical(black));
        assertTrue(p.patternAt(new Point(-1.1, 0, 0)).isIdentical(white));
    }
}
