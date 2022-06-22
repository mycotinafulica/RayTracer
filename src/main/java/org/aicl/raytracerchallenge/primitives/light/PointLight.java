package org.aicl.raytracerchallenge.primitives.light;

import org.aicl.raytracerchallenge.primitives.Color;
import org.aicl.raytracerchallenge.primitives.Point;

public class PointLight {
    public Point position;
    public Color intensity;

    public PointLight(Point position, Color intensity){
        this.position  = position;
        this.intensity = intensity;
    }
}
