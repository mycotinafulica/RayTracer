package org.aicl.raytracerchallenge.primitives.light;

import org.aicl.raytracerchallenge.primitives.*;

public class LightSampler {
    public static Color lighting(Material m, PointLight light, Point point, Vector eyev, Vector normal, boolean isInShadow){
        Color effectiveColor = m.color.multiply(light.intensity);

        Tuple lightv = light.position.subtract(point).normalize();
        Color ambient = effectiveColor.multiply(m.ambient);

        if(isInShadow)
            return ambient;

        double lightDotNormal = TupleOperation.dot(lightv, normal);
        Color diffuse;
        Color specular;
        if(lightDotNormal < 0){
            diffuse = new Color(0, 0, 0);
            specular = new Color(0, 0, 0);
        }
        else{
            diffuse = effectiveColor.multiply(m.diffuse).multiply(lightDotNormal);
            Tuple reflecv = TupleOperation.reflect(new Vector(lightv.negates()), normal);
            double reflectDotEye = TupleOperation.dot(reflecv, eyev);
            if(reflectDotEye < 0){
                specular =  new Color(0, 0, 0);
            }
            else{
                double factor = Math.pow(reflectDotEye, m.shininess);
                specular = light.intensity.multiply(m.specular).multiply(factor);
            }
        }

        return ambient.add(diffuse).add(specular);
    }
}
