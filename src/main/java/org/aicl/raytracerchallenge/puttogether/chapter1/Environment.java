package org.aicl.raytracerchallenge.puttogether.chapter1;

import org.aicl.raytracerchallenge.primitives.Vector;

public class Environment {
    public Vector gravity;
    public Vector wind;

    public Environment(Vector gravity, Vector wind){
        this.gravity = gravity;
        this.wind = wind;
    }
}
