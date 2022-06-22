package org.aicl.raytracerchallenge.primitives;

import org.aicl.raytracerchallenge.utilities.FloatEquality;

public class Material {
    public Color color = new Color(1, 1, 1);
    public double ambient   = 0.1;
    public double diffuse   = 0.9;
    public double specular  = 0.9;
    public double shininess = 200.;

    public boolean isIdentical(Material m){
        return color.isIdentical(m.color)
                && FloatEquality.isEqual(ambient, m.ambient)
                && FloatEquality.isEqual(diffuse, m.diffuse)
                && FloatEquality.isEqual(specular, m.specular)
                && FloatEquality.isEqual(shininess, m.shininess);
    }
}
