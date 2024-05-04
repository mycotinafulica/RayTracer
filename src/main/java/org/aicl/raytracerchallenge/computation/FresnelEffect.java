package org.aicl.raytracerchallenge.computation;

import org.aicl.raytracerchallenge.primitives.Tuple;
import org.aicl.raytracerchallenge.primitives.TupleOperation;
import org.aicl.raytracerchallenge.primitives.ray.PrecomputedIntersectionData;

public class FresnelEffect {
    public static double schlick(PrecomputedIntersectionData comps){
        double cos = TupleOperation.dot(comps.eyev, comps.normal);
        if(comps.n1 > comps.n2){
            double n = comps.n1/comps.n2;
            double sin2t = n*n * (1.0 - cos * cos);
            if(sin2t > 1.0)
                return 1;

            double cost = Math.sqrt(1.0 - sin2t);
            cos = cost;
        }

        double r0 = ((comps.n1 - comps.n2)/(comps.n1 + comps.n2))*((comps.n1 - comps.n2)/(comps.n1 + comps.n2));

        return r0 + (1 - r0) * Math.pow(1 - cos, 5);
    }
}
